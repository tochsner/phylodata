import os
import shutil
from io import BytesIO

import msgspec
from streamlit.runtime.uploaded_file_manager import UploadedFile

from phylodata.data_types import (
    EditableExperiment,
    EditablePaper,
    EditablePaperWithExperiment,
    EvolutionaryModel,
    File,
    Metadata,
    NonEditableExperiment,
    NonEditablePaper,
    NonEditablePaperWithExperiment,
    Sample,
    Trees,
)

WASABI_BUCKET_NAME = "phylodata-experiments"


def store_output(
    beast2_configuration: BytesIO,
    beast2_logs: BytesIO,
    beast2_trees: BytesIO,
    other_files: list[UploadedFile],
    editable_experiment: EditableExperiment,
    non_editable_experiment: NonEditableExperiment,
    editable_paper: EditablePaper,
    non_editable_paper: NonEditablePaper,
    samples: list[Sample],
    files: list[File],
    evolutionary_model: EvolutionaryModel,
    trees: Trees,
    metadata: Metadata,
) -> str:
    """
    Creates a folder with a name based on the given title and writes the provided
    files and the PhyloData metadata into it.
    """
    output_folder = f"{editable_paper.title}-phylodata"

    if os.path.exists(output_folder):
        shutil.rmtree(output_folder)
    os.mkdir(output_folder)

    editable_metadata = EditablePaperWithExperiment(
        experiment=editable_experiment, paper=editable_paper, samples=samples
    )
    with open(f"{output_folder}/editable_phylodata_metadata.json", "wb") as f:
        f.write(msgspec.json.format(msgspec.json.encode(editable_metadata), indent=2))

    non_editable_metadata = NonEditablePaperWithExperiment(
        experiment=non_editable_experiment,
        paper=non_editable_paper,
        files=files,
        evolutionary_model=evolutionary_model,
        trees=trees,
        metadata=metadata,
    )
    with open(f"{output_folder}/non_editable_phylodata_metadata.json", "wb") as f:
        f.write(
            msgspec.json.format(msgspec.json.encode(non_editable_metadata), indent=2)
        )

    used_filenames = set("phylodata_metadata.json")
    all_files = [beast2_configuration, beast2_logs, beast2_trees] + other_files

    for file in all_files:
        filename = file.name
        base, ext = os.path.splitext(file.name)

        while filename in used_filenames:
            filename = f"{base}_{len(used_filenames)}{ext}"

        used_filenames.add(filename)
        with open(f"{output_folder}/{filename}", "wb") as f:
            f.write(file.getbuffer())

    return output_folder
