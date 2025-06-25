from phylodata.errors import ValidationError
from phylodata.types import File, FileType
from typing import Optional
from io import BytesIO
from hashlib import md5

from xml.etree import ElementTree


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
    try:
        xml = ElementTree.parse(file)
    except:
        raise ValidationError("BEAST 2 configuration is no valid XML file.")

    root = xml.getroot()

    if root.tag.lower() != "beast":
        raise ValidationError("BEAST 2 configuration has no root BEAST tag.")

    if not any(child.tag.lower() == "data" for child in root):
        raise ValidationError("BEAST 2 has no <data> tag.")

    if not any(child.tag.lower() == "run" for child in root):
        raise ValidationError("BEAST 2 has no <run> tag.")

    buffer = file.getbuffer()
    return File(
        name=file.name,
        type=FileType.BEAST2_CONFIGURATION,
        version=1,
        size_bytes=buffer.nbytes,
        md5=md5(buffer).hexdigest(),
    )


def parse_beast2_logs(file: BytesIO) -> File: ...


def parse_beast2_trees(file: BytesIO) -> File: ...


def parse_other_file(file: BytesIO) -> File: ...
