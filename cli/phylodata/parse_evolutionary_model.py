from io import BytesIO
from xml.etree import ElementTree

from phylodata.beast2_package_parser import BEAST2_PACKAGE_PARSERS
from phylodata.errors import ValidationError
from phylodata.types import EvolutionaryModel, EvolutionaryModelComponent


def parse_evolutionary_model(beast2_config: BytesIO) -> EvolutionaryModel:
    try:
        xml = ElementTree.parse(beast2_config)
    except Exception:
        raise ValidationError("BEAST 2 configuration is no valid XML file.")

    models = []

    for parser in BEAST2_PACKAGE_PARSERS:
        if parser.is_used(xml):
            model = EvolutionaryModelComponent(
                name=parser.get_name(),
                description=parser.get_description(),
                parameters=parser.get_parameters(xml),
                documentation_url=parser.get_documentation_url(),
                type=parser.get_type(),
            )
            models.append(model)

    return EvolutionaryModel(models=models)
