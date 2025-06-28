from io import BytesIO

from phylodata.parse_files import parse_other_file
from phylodata.data_types import FileType


def to_bytes_io(text: str):
    bytesio = BytesIO(bytes(text, "ascii"))
    bytesio.name = "Test"
    return bytesio


def test_summary_tree_is_ok():
    raw_nexus = """
    #NEXUS
    Begin taxa;
       	Dimensions ntax=3;
  		Taxlabels
 			0
 			1
            2;
    End;
    Begin trees;
        tree STATE_1 = ((0, 1), 2);
    End;
    """

    file = to_bytes_io(raw_nexus)
    parsed = parse_other_file(file)

    assert parsed.type == FileType.SUMMARY_TREE
