from io import BytesIO

from phylodata.types import Sample


def parse_samples(beast2_config: BytesIO) -> list[Sample]:
    ...
