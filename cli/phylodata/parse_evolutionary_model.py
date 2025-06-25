from io import BytesIO

from phylodata.types import EvolutionaryModel


def parse_evolutionary_model(beast2_config: BytesIO) -> EvolutionaryModel:
    ...
