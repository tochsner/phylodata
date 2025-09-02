import os
from typing import Optional

from phylodata.data_types import File, FileType, PaperWithExperiment
from phylodata.loader.consts import PREFER_PREVIEW_ENV


def get_file(
    experiment: PaperWithExperiment,
    file_type: FileType,
    prefer_preview: Optional[bool] = None,
) -> Optional[File]:
    """
    Retrieves a file of a specified type from a PaperWithExperiment object.

    Args:
        experiment:
            The PaperWithExperiment object containing the files.
        file_type:
            The FileType to retrieve.
        prefer_preview:
            If None, prefers a full file but falls back to a preview file if no full file exists.
            If True, prefers and returns a preview file if available.
            If False, always retrieves a full file.
            This can also be controlled by setting the environment variable PHYLODATA_PREFER_PREVIEW
            to true or false.

    Returns:
        The first matching File object, or None if no such file exists.
    """
    if prefer_preview is None and os.environ.get(PREFER_PREVIEW_ENV) is not None:
        prefer_preview = os.environ.get(PREFER_PREVIEW_ENV) == "true"

    relevant_preview_files = [
        file
        for file in experiment.files
        if file.local_path and file.type == file_type and file.is_preview
    ]
    relevant_full_files = [
        file
        for file in experiment.files
        if file.local_path and file.type == file_type and not file.is_preview
    ]

    if prefer_preview is None:
        # we prefer the full version, but can fall back to the preview
        if relevant_full_files:
            return relevant_full_files[0]
        elif relevant_preview_files:
            return relevant_preview_files[0]
        else:
            return None

    elif prefer_preview:
        # we prefer the preview version, but can fall back to the full
        if relevant_preview_files:
            return relevant_preview_files[0]
        elif relevant_full_files:
            return relevant_full_files[0]
        else:
            return None

    else:  # preview == False
        # we never return a preview
        if relevant_full_files:
            return relevant_full_files[0]
        else:
            return None
