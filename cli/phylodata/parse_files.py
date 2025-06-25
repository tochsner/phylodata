from phylodata.types import File, FileType
from typing import Optional
from io import BytesIO


def parse_file(
    path: BytesIO, file_type: Optional[FileType] = FileType.UNKNOWN
) -> File:
    match file_type:
        case FileType.BEAST2_CONFIGURATION:
            return parse_beast2_config(path)
        case FileType.BEAST2_POSTERIOR_LOGS:
            return parse_beast2_logs(path)
        case FileType.BEAST2_POSTERIOR_TREES:
            return parse_beast2_trees(path)
        case _:
            return parse_other_file(path)


def parse_beast2_config(path: BytesIO) -> File: ...


def parse_beast2_logs(path: BytesIO) -> File: ...


def parse_beast2_trees(path: BytesIO) -> File: ...


def parse_other_file(path: BytesIO) -> File: ...
