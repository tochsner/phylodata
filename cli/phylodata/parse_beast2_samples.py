from collections import defaultdict
from io import BytesIO
from typing import Generator
from xml.etree import ElementTree

from phylodata.types import DataType, EvolutionaryModel, Sample, SampleData
from phylodata.utils import get_xml_from_bytesio


def parse_beast2_samples(
    beast2_config: BytesIO, evolutionary_model: EvolutionaryModel
) -> list[Sample]:
    """Collects all sample data found in the BEAST 2 XML.

    All top-level <data> tags are traversed in order to find all
    alignments in the file.

    Note that this only works with basic BEAST XML files and will not
    work for every possible configuration."""
    xml = get_xml_from_bytesio(beast2_config)
    xml_root = xml.getroot()

    # we first look at all <data> tags to find any sequence

    sample_data: list[tuple[str, SampleData]] = []
    for root_child in xml_root:
        if root_child.tag.lower() == "data":
            sample_data += collect_sample_data_from_data_tag(root_child)

    # group the samples by their id (taxon)

    sample_data_per_id: dict[str, list[SampleData]] = defaultdict(list)
    for sample_id, data in sample_data:
        sample_data_per_id[sample_id].append(data)

    # construct the samples by adding additional metadata

    samples_with_metadata = []
    for sample_id, data in sample_data_per_id.items():
        samples_with_metadata.append(
            construct_sample(sample_id, data, evolutionary_model)
        )

    return samples_with_metadata


def collect_sample_data_from_data_tag(
    data_element: ElementTree.Element,
) -> Generator[tuple[str, SampleData], None, None]:
    """Yields all sample data found in BEAST 2 XML <sequence> tags within
    a  given <data> tag."""
    data_type = data_element.attrib.get("dataType", "standard")

    match data_type:
        case "aminoacid":
            data_type = DataType.AMINO_ACIDS
        case "binary" | "integer":
            data_type = DataType.TRAITS
        case _:  # "nucleotide" | "standard" | "twoStateCovarion" | "user defined"
            data_type = DataType.UNKNOWN

    for sequence in data_element:
        if sequence.tag.lower() != "sequence":
            continue

        sample_id = sequence.attrib["taxon"]
        data = sequence.attrib.get("value") or sequence.text

        if not data:
            continue

        # ignore whitespace
        data = "".join(data.split())

        # data can be comma-delimited or just a joined string
        if "," in data:
            data = data.split(",")
        else:
            data = list(data)

        # if dataType was not given in the <data> tag, we try to infer it from
        # the sequence itself
        if data_type == DataType.UNKNOWN:
            characters = {c.lower() for c in data}

            if characters.issubset(DNA_CHARACTERS):
                data_type = DataType.DNA
            elif characters.issubset(RNA_CHARACTERS):
                data_type = DataType.RNA
            elif characters.issubset(AA_CHARACTERS):
                data_type = DataType.AMINO_ACIDS

        yield sample_id, SampleData(type=data_type, length=len(data), data=data)


def construct_sample(
    sample_id: str, data: list[SampleData], evolutionary_model: EvolutionaryModel
) -> Sample:
    ...

    # id: str
    # scientific_name: str
    # type: SampleType
    # classification: dict[str, str]
    # data: list[SampleData]
    #
    # SPECIES   (name, all seq from different species)
    # CELL      (name, all seq from same species, same genes)
    # GENE      (name, all seq from same species, different genes)
    # LANGUAGE  (name, package)
    # UNKNOWN


DNA_CHARACTERS = {"a", "t", "c", "g"}
RNA_CHARACTERS = {"a", "u", "c", "g"}
AA_CHARACTERS = {
    "a",
    "r",
    "n",
    "d",
    "c",
    "e",
    "q",
    "g",
    "h",
    "i",
    "l",
    "k",
    "m",
    "f",
    "p",
    "s",
    "t",
    "w",
    "y",
    "v",
    "u",
    "o",
}
