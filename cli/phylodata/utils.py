import itertools
from io import BytesIO
from commonnexus import Nexus
from errors import ValidationError
from xml.etree import ElementTree

def get_nexus_from_bytesio(file: BytesIO) -> Nexus:
    try:
        lines = itertools.chain.from_iterable(
            (string_line.decode("utf-8") for string_line in file)
        )
        return Nexus(lines)
    except Exception:
        raise ValidationError("BEAST 2 trees file is no valid NEXUS file.")

def get_xml_from_bytesio(file: BytesIO) -> ElementTree.ElementTree:
    try:
        return ElementTree.parse(file)
    except Exception:
        raise ValidationError("BEAST 2 configuration is no valid XML file.")
