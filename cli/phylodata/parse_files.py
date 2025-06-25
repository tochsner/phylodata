import csv
import itertools
from hashlib import md5
from io import BytesIO, TextIOWrapper
from typing import Optional
from xml.etree import ElementTree

from commonnexus import Nexus

from phylodata.errors import ValidationError
from phylodata.types import File, FileType

MIN_NUM_SNAPSHOTS = 50


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
    except Exception:
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


def parse_beast2_logs(file: BytesIO) -> File:
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

    buffer = file.getbuffer()
    return File(
        name=file.name,
        type=FileType.BEAST2_POSTERIOR_LOGS,
        version=1,
        size_bytes=buffer.nbytes,
        md5=md5(buffer).hexdigest(),
    )


def parse_beast2_trees(file: BytesIO) -> File:
    try:
        lines = itertools.chain.from_iterable(
            (string_line.decode("utf-8") for string_line in file)
        )
        nexus = Nexus(lines)
    except Exception:
        raise ValidationError("BEAST 2 trees file is no valid NEXUS file.")

    if not nexus.TREES:
        raise ValidationError("BEAST 2 trees contains no actual trees.")

    if len(nexus.TREES) < MIN_NUM_SNAPSHOTS:
        raise ValidationError(
            f"BEAST 2 trees should contain at least {MIN_NUM_SNAPSHOTS} trees."
        )

    buffer = file.getbuffer()
    return File(
        name=file.name,
        type=FileType.BEAST2_POSTERIOR_TREES,
        version=1,
        size_bytes=buffer.nbytes,
        md5=md5(buffer).hexdigest(),
    )


def parse_other_file(file: BytesIO) -> File: ...
