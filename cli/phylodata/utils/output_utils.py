import os
import shutil
from io import BytesIO
from pathlib import Path

import msgspec

from phylodata.data_types import (
    EditableExperiment,
    EditablePaper,
    EditablePaperWithExperiment,
    EvolutionaryModel,
    File,
    FileType,
    Metadata,
    NonEditableExperiment,
    NonEditablePaper,
    NonEditablePaperWithExperiment,
    Sample,
    Trees,
)

WASABI_BUCKET_NAME = "phylodata-experiments"

EDITABLE_METADATA_FILE = "editable_phylodata_metadata.json"
NON_EDITABLE_METADATA_FILE = "non_editable_phylodata_metadata"


def store_output(
    editable_experiment: EditableExperiment,
    non_editable_experiment: NonEditableExperiment,
    editable_paper: EditablePaper,
    non_editable_paper: NonEditablePaper,
    samples: list[Sample],
    files: list[File],
    evolutionary_model: EvolutionaryModel,
    trees: Trees,
    metadata: Metadata,
) -> Path:
    """
    Creates a folder with a name based on the given title and writes the provided
    files and the PhyloData metadata into it.
    """
    files = clean_up_file_names(files)
    files = add_editable_metadata_file(
        files, editable_paper, editable_experiment, samples
    )
    files = add_non_editable_metadata_file(
        files,
        non_editable_paper,
        non_editable_experiment,
        evolutionary_model,
        trees,
        metadata,
    )

    output_folder = create_output_folder(non_editable_experiment)
    store_files(files, output_folder)

    return output_folder


def clean_up_file_names(files: list[File]) -> list[File]:
    filename_counter = {EDITABLE_METADATA_FILE: 1, NON_EDITABLE_METADATA_FILE: 1}

    for file in files:
        if file.name in filename_counter:
            filename_counter[file.name] += 1

            base, ext = os.path.splitext(file.name)
            file.name = f"{base}_{filename_counter[file.name]}{ext}"
        else:
            filename_counter[file.name] = 1

    return files


def add_editable_metadata_file(
    files: list[File],
    editable_paper: EditablePaper,
    editable_experiment: EditableExperiment,
    samples: list[Sample],
) -> list[File]:
    editable_metadata = EditablePaperWithExperiment(
        editable_paper, editable_experiment, samples
    )

    data_bytes = msgspec.json.format(msgspec.json.encode(editable_metadata), indent=2)
    data_bytes_io = BytesIO()
    data_bytes_io.write(data_bytes)

    editable_metadata_file = File.from_bytes(
        data_bytes_io,
        name=EDITABLE_METADATA_FILE,
        type=FileType.PHYLO_DATA_EXPERIMENT,
        version=1,
    )
    files.append(editable_metadata_file)

    return files


def add_non_editable_metadata_file(
    files: list[File],
    non_editable_paper: NonEditablePaper,
    non_editable_experiment: NonEditableExperiment,
    evolutionary_model: EvolutionaryModel,
    trees: Trees,
    metadata: Metadata,
) -> list[File]:
    non_editable_metadata = NonEditablePaperWithExperiment(
        files=files,
        experiment=non_editable_experiment,
        paper=non_editable_paper,
        evolutionary_model=evolutionary_model,
        trees=trees,
        metadata=metadata,
    )

    data_bytes = msgspec.json.encode(non_editable_metadata)
    data_bytes_io = BytesIO()
    data_bytes_io.write(data_bytes)

    non_editable_metadata_file = File.from_bytes(
        data_bytes_io,
        name=NON_EDITABLE_METADATA_FILE,
        type=FileType.PHYLO_DATA_EXPERIMENT,
        version=1,
    )
    files.append(non_editable_metadata_file)

    return files


def create_output_folder(experiment: NonEditableExperiment) -> Path:
    output_folder = Path(experiment.human_readable_id)

    if os.path.exists(output_folder):
        shutil.rmtree(output_folder)
    os.mkdir(output_folder)

    return output_folder


def store_files(files: list[File], output_folder: Path):
    for file in files:
        if not file.bytes:
            raise ValueError(
                f"Trying to store a file without bytes ({file.name}). This should not happen."
            )

        with open(f"{output_folder}/{file.name}", "wb") as f:
            f.write(file.bytes.getbuffer())
