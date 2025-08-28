import sys
from pathlib import Path

import msgspec

from phylodata.data_types import EditablePaperWithExperiment
from phylodata.process.samples.add_language_metadata import (
    fetch_language_classification,
)


def correct_langauge(metadata_file: Path, sample_id: str, language_label: str):
    with open(metadata_file, "r") as file:
        metadata = msgspec.json.decode(file.read(), type=EditablePaperWithExperiment)

    for sample in metadata.samples:
        if sample.sample_id == sample_id:
            if classification := fetch_language_classification(language_label):
                sample.classification = classification
                sample.scientific_name = classification[0].scientific_name
                break

    with open(metadata_file, "wb") as file:
        file.write(msgspec.json.format(msgspec.json.encode(metadata), indent=2))


correct_langauge(Path(sys.argv[1]), sys.argv[2], sys.argv[3])
