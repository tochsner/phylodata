"""Check that the 'name' field in models.json matches the 'name' attribute in each
package's version.xml file hosted in its git repository."""

import json
import urllib.request
import xml.etree.ElementTree as ET
from pathlib import Path


MODELS_JSON = (
    Path(__file__).parent.parent
    / "phylodata"
    / "process"
    / "evolutionary_model"
    / "models.json"
)

# branches to try in order when fetching version.xml
BRANCHES = ["HEAD", "master", "main"]


def raw_url_for_version_xml(repo_url: str, branch: str) -> str | None:
    """Return the raw URL for version.xml given a repository URL and branch."""
    repo_url = repo_url.rstrip("/")

    if "github.com" in repo_url:
        # e.g. https://github.com/owner/repo  →  https://raw.githubusercontent.com/owner/repo/<branch>/version.xml
        parts = repo_url.split("github.com/", 1)
        if len(parts) != 2:
            return None
        path = parts[1].rstrip("/")
        # strip trailing sub-paths (e.g. /tree/master)
        segments = path.split("/")
        owner_repo = "/".join(segments[:2])
        return f"https://raw.githubusercontent.com/{owner_repo}/{branch}/version.xml"

    if "bitbucket.org" in repo_url:
        # e.g. https://bitbucket.org/owner/repo  →  https://bitbucket.org/owner/repo/raw/<branch>/version.xml
        parts = repo_url.split("bitbucket.org/", 1)
        if len(parts) != 2:
            return None
        path = parts[1].rstrip("/")
        segments = path.split("/")
        owner_repo = "/".join(segments[:2])
        return f"https://bitbucket.org/{owner_repo}/raw/{branch}/version.xml"

    return None


def fetch_version_xml(repo_url: str) -> tuple[str, str] | None:
    """Fetch version.xml from the repository. Returns (branch, content) or None."""
    for branch in BRANCHES:
        url = raw_url_for_version_xml(repo_url, branch)
        if url is None:
            return None
        try:
            with urllib.request.urlopen(url, timeout=10) as response:
                if response.status == 200:
                    return branch, response.read().decode("utf-8")
        except Exception:
            continue
    return None


def get_package_name_from_xml(xml_content: str) -> str | None:
    """Parse version.xml and return the 'name' attribute of the top-level <package> tag."""
    try:
        root = ET.fromstring(xml_content)
        # the top-level element may be <package> itself or nested inside a wrapper
        if root.tag == "package":
            return root.get("name")
        package_elem = root.find("package")
        if package_elem is not None:
            return package_elem.get("name")
    except ET.ParseError:
        pass
    return None


def main() -> None:
    with open(MODELS_JSON) as f:
        models = json.load(f)

    ok = 0
    mismatches: list[str] = []
    skipped: list[str] = []

    for model in models:
        json_name: str = model["name"]
        code_url: str = model.get("code", "")

        if not code_url:
            skipped.append(f"{json_name}: no 'code' URL")
            continue

        result = fetch_version_xml(code_url)

        if result is None:
            skipped.append(f"{json_name}: could not fetch version.xml from {code_url}")
            continue

        branch, xml_content = result
        xml_name = get_package_name_from_xml(xml_content)

        if xml_name is None:
            skipped.append(
                f"{json_name}: version.xml found on branch '{branch}' but no <package name=...> attribute"
            )
            continue

        if json_name.lower() == xml_name.lower():
            print(f"  OK  {json_name}")
            ok += 1
        else:
            msg = f"MISMATCH  json='{json_name}'  xml='{xml_name}'  ({code_url})"
            print(f"  !! {msg}")
            mismatches.append(msg)

    print()
    print(f"Results: {ok} OK, {len(mismatches)} mismatches, {len(skipped)} skipped")

    if mismatches:
        print("\nMismatches:")
        for m in mismatches:
            print(f"  {m}")

    if skipped:
        print("\nSkipped:")
        for s in skipped:
            print(f"  {s}")


if __name__ == "__main__":
    main()
