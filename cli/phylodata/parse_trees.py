from io import BytesIO

from phylodata.types import Trees


def parse_trees(beast2_trees: BytesIO) -> Trees:
    ...
