from abc import ABC, abstractmethod
from typing import Any, Optional
from xml.etree.ElementTree import ElementTree

from phylodata.data_types import ModelType, SampleType


class Beast2PackageParser(ABC):
    """An abstract base class for parsing BEAST 2 packages."""

    def is_used(self, beast2_xml: ElementTree) -> bool:
        """Returns true if the package is detected in the given BEAST2 xml.
        Checks the top-level namespaces and the spec attribute of all elements."""
        root = beast2_xml.getroot()

        # check namespaces

        namespaces = root.attrib.get("namespace", "")
        namespaces = namespaces.split(":")

        package_namespace = self.get_namespace()

        if namespaces and any(
            namespace == package_namespace
            or namespace.startswith(f"{package_namespace}.")
            for namespace in namespaces
        ):
            return True

        # check specs

        elements_with_spec = root.findall(".//*[@spec]")
        spec_values = [elem.get("spec") for elem in elements_with_spec]

        if spec_values and any(
            spec_value.startswith(f"{package_namespace}.")
            for spec_value in spec_values
            if spec_value
        ):
            return True

        return False

    @abstractmethod
    def get_name(self) -> str:
        """Returns a human-readable name of the BEAST 2 package."""
        raise NotImplementedError

    @abstractmethod
    def get_description(self) -> str:
        """Returns a human-readable get_description of the BEAST 2 package."""
        raise NotImplementedError

    @abstractmethod
    def get_type(self) -> ModelType:
        """Returns the type of the BEAST 2 package."""
        raise NotImplementedError

    @abstractmethod
    def get_namespace(self) -> str:
        """Returns the namespace of the BEAST 2 package."""
        raise NotImplementedError

    @abstractmethod
    def get_documentation_url(self) -> str:
        """Returns the URL to the human-readable package website."""
        raise NotImplementedError

    @abstractmethod
    def get_parameters(self, beast2_xml: ElementTree) -> dict[str, Any]:
        """Extracts potential package parameters from the BEAST 2 xml configuration."""
        raise NotImplementedError

    def suggest_sample_type(self) -> Optional[SampleType]:
        """If the package usage suggests a specific sample type, returns it. For example,
        a model for phylogenetic language trees would return SampleType.LANGUAGE."""
        return None


class BabelParser(Beast2PackageParser):
    def get_name(self) -> str:
        return "Babel"

    def get_description(self) -> str:
        return "BABEL = BEAST analysis backing effective linguistics"

    def get_namespace(self) -> str:
        return "babel"

    def get_type(self) -> ModelType:
        return ModelType.OTHER

    def get_documentation_url(self) -> str:
        return "https://github.com/rbouckaert/Babel"

    def get_parameters(self, beast2_xml: ElementTree) -> dict[str, Any]:
        return {}

    def suggest_sample_type(self) -> Optional[SampleType]:
        return SampleType.LANGUAGE


BEAST2_PACKAGE_PARSERS: list[Beast2PackageParser] = [BabelParser()]
BEAST2_PACKAGE_PARSERS_PER_NAME: dict[str, Beast2PackageParser] = {
    parser.get_name(): parser for parser in BEAST2_PACKAGE_PARSERS
}
