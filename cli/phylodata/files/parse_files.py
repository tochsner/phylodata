import csv
from io import BytesIO, TextIOWrapper
from typing import Optional

from phylodata.data_types import File, FileType
from phylodata.errors import ValidationError
from phylodata.utils.bytesio_utils import get_nexus_from_bytesio, get_xml_from_bytesio

MIN_NUM_SNAPSHOTS = 50
"""The minimum number of snapshots required for a valid BEAST 2 posterior log file.
When the number of snapshots is less than this value, the log file is considered invalid."""


def parse_file(file: BytesIO, file_type: Optional[FileType] = FileType.UNKNOWN) -> File:
    match file_type:
        case FileType.BEAST2_CONFIGURATION:
            return parse_beast2_config(file)
        case FileType.BEAST2_POSTERIOR_LOGS:
            return parse_beast2_logs(file)
        case FileType.BEAST2_POSTERIOR_TREES:
            return parse_beast2_trees(file)
        case _:
            return parse_other_file(file)


def parse_beast2_config(file: BytesIO) -> File:
    """Parses a BEAST 2 config file. If the file does not seem to be a valid
    BEAST config, a ValidationError is raised."""
    xml = get_xml_from_bytesio(file)
    root = xml.getroot()

    if root is None or root.tag.lower() != "beast":
        raise ValidationError("BEAST 2 configuration has no root BEAST tag.")

    if not any(child.tag.lower() == "data" for child in root):
        raise ValidationError("BEAST 2 has no <data> tag.")

    if not any(child.tag.lower() == "run" for child in root):
        raise ValidationError("BEAST 2 has no <run> tag.")

    return File.from_bytes(
        file,
        name=file.name,
        type=FileType.BEAST2_CONFIGURATION,
        version=1,
    )


def parse_beast2_logs(file: BytesIO) -> File:
    """Parses a BEAST 2 log file. If the file does not seem to be a valid
    BEAST log, a ValidationError is raised."""
    try:
        wrapper = TextIOWrapper(file)
        tsv_file = csv.DictReader(wrapper, delimiter="\t")
    except Exception:
        raise ValidationError("BEAST 2 log file is not a tab-separated file.")

    num_rows = sum(1 for _ in tsv_file)
    if num_rows < MIN_NUM_SNAPSHOTS:
        raise ValidationError(
            f"BEAST 2 log file should contain at least {MIN_NUM_SNAPSHOTS} entries."
        )

    wrapper.detach()

    return File.from_bytes(
        file,
        name=file.name,
        type=FileType.BEAST2_POSTERIOR_LOGS,
        version=1,
    )


def parse_beast2_trees(file: BytesIO) -> File:
    """Parses a BEAST 2 trees file. If the file does not seem to be a valid
    BEAST trees file, a ValidationError is raised."""
    nexus = get_nexus_from_bytesio(file)

    if not nexus.TREES or not nexus.TREES.trees:
        raise ValidationError("BEAST 2 trees contains no actual trees.")

    if len(nexus.TREES.trees) < MIN_NUM_SNAPSHOTS:
        raise ValidationError(
            f"BEAST 2 trees should contain at least {MIN_NUM_SNAPSHOTS} trees."
        )

    return File.from_bytes(
        file,
        name=file.name,
        type=FileType.BEAST2_POSTERIOR_TREES,
        version=1,
    )


def parse_other_file(file: BytesIO) -> File:
    # if the file contains a single tree => summary tree

    try:
        nexus = get_nexus_from_bytesio(file)
        if nexus.TREES and nexus.TREES.trees and len(nexus.TREES.trees) == 1:
            return File.from_bytes(
                file,
                name=file.name,
                type=FileType.SUMMARY_TREE,
                version=1,
            )
    except ValidationError:
        # file is not a valid trees file
        ...

    # otherwise we return an UNKNOWN file

    return File.from_bytes(
        file,
        name=file.name,
        type=FileType.UNKNOWN,
        version=1,
    )
