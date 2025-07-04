import re

DOI_REGEX = r"^https?:\/\/(?:www\.)?(?:dx\.)?doi\.org\/10\.\d{4,}\/[^\s]+$"

def is_doi(doi: str) -> bool:
    pattern = re.compile(DOI_REGEX)
    return bool(pattern.match(doi))
