import datetime
import os
from pathlib import Path
from unittest import mock

import pytest
from pytest import fixture

from phylodata.data_types import (
    Experiment,
    ExperimentType,
    File,
    FileType,
    Metadata,
    Paper,
)
from phylodata.loader.preview_env import PREFER_PREVIEW_ENV
from phylodata.paper_with_experiment import PaperWithExperiment


@fixture()
def set_preview_env_var_to_true(monkeypatch):
    with mock.patch.dict(os.environ, clear=True):
        envvars = {
            PREFER_PREVIEW_ENV: "true",
        }
        for k, v in envvars.items():
            monkeypatch.setenv(k, v)
        yield


@fixture()
def set_preview_env_var_to_false(monkeypatch):
    with mock.patch.dict(os.environ, clear=True):
        envvars = {
            PREFER_PREVIEW_ENV: "false",
        }
        for k, v in envvars.items():
            monkeypatch.setenv(k, v)
        yield


@fixture
def experiment() -> PaperWithExperiment:
    return PaperWithExperiment(
        files=[],
        paper=Paper(
            doi="", title="", year=2018, authors=[], abstract="", bibtex="", email=""
        ),
        experiment=Experiment(
            type=ExperimentType.BEAST2_Experiment,
            human_readable_id="",
            origin="",
            upload_date=datetime.date.today(),
            version=0,
            title="",
            description="",
        ),
        samples=[],
        evolutionary_model=[],
        trees=None,
        metadata=Metadata(evo_data_pipeline_version=""),
        local_path=Path(),
    )


def test_single_possible_file_get_found(experiment: PaperWithExperiment):
    experiment.files = [
        File(
            name="beast.xml",
            type=FileType.BEAST2_CONFIGURATION,
            size_bytes=0,
            md5="",
            local_path=Path(),
        ),
        File(
            name="beast.trees",
            type=FileType.POSTERIOR_TREES,
            size_bytes=0,
            md5="",
            local_path=Path(),
        ),
        File(
            name="summary.trees",
            type=FileType.SUMMARY_TREE,
            size_bytes=0,
            md5="",
            local_path=Path(),
        ),
    ]

    config = experiment.get_file_of_type(FileType.BEAST2_CONFIGURATION)
    assert config
    assert config.name == "beast.xml"

    posterior = experiment.get_file_of_type(FileType.POSTERIOR_TREES)
    assert posterior
    assert posterior.name == "beast.trees"

    summary = experiment.get_file_of_type(FileType.SUMMARY_TREE)
    assert summary
    assert summary.name == "summary.trees"


def test_missing_file_type_raises_error(experiment: PaperWithExperiment):
    experiment.files = [
        File(
            name="beast.xml",
            type=FileType.BEAST2_CONFIGURATION,
            size_bytes=0,
            md5="",
            local_path=Path(),
        ),
    ]

    with pytest.raises(FileNotFoundError):  # noqa: F821
        experiment.get_file_of_type(FileType.SUMMARY_TREE)


def test_multiple_possible_files_returns_first(experiment: PaperWithExperiment):
    experiment.files = [
        File(
            name="beast.xml",
            type=FileType.BEAST2_CONFIGURATION,
            size_bytes=0,
            md5="",
            local_path=Path(),
        ),
        File(
            name="beast2.xml",
            type=FileType.BEAST2_CONFIGURATION,
            size_bytes=0,
            md5="",
            local_path=Path(),
        ),
    ]

    config = experiment.get_file_of_type(FileType.BEAST2_CONFIGURATION)
    assert config
    assert config.name == "beast.xml"


def test_full_files_are_preferred_if_nothing_specified(experiment: PaperWithExperiment):
    experiment.files = [
        File(
            name="beast.xml",
            type=FileType.BEAST2_CONFIGURATION,
            size_bytes=0,
            md5="",
            local_path=Path("beast.xml"),
        ),
        File(
            name="beast2 (preview).xml",
            type=FileType.BEAST2_CONFIGURATION,
            size_bytes=0,
            md5="",
            local_path=Path("beast2 (preview).xml"),
            is_preview=True,
        ),
        File(
            name="trees.xml",
            type=FileType.POSTERIOR_TREES,
            size_bytes=0,
            md5="",
            local_path=Path("trees.xml"),
        ),
        File(
            name="logs (preview).xml",
            type=FileType.BEAST2_POSTERIOR_LOGS,
            size_bytes=0,
            md5="",
            local_path=Path("logs (preview).xml"),
            is_preview=True,
        ),
    ]

    config = experiment.get_file_of_type(FileType.BEAST2_CONFIGURATION)
    assert config
    assert config.name == "beast.xml"

    posterior = experiment.get_file_of_type(FileType.POSTERIOR_TREES)
    assert posterior
    assert posterior.name == "trees.xml"

    logs = experiment.get_file_of_type(FileType.BEAST2_POSTERIOR_LOGS)
    assert logs
    assert logs.name == "logs (preview).xml"


def test_only_full_files_are_returned_if_preview_is_false(
    experiment: PaperWithExperiment,
):
    experiment.files = [
        File(
            name="beast.xml",
            type=FileType.BEAST2_CONFIGURATION,
            size_bytes=0,
            md5="",
            local_path=Path("beast.xml"),
        ),
        File(
            name="beast2 (preview).xml",
            type=FileType.BEAST2_CONFIGURATION,
            size_bytes=0,
            md5="",
            local_path=Path("beast2 (preview).xml"),
            is_preview=True,
        ),
        File(
            name="trees.xml",
            type=FileType.POSTERIOR_TREES,
            size_bytes=0,
            md5="",
            local_path=Path("trees.xml"),
        ),
        File(
            name="logs (preview).xml",
            type=FileType.BEAST2_POSTERIOR_LOGS,
            size_bytes=0,
            md5="",
            local_path=Path("logs (preview).xml"),
            is_preview=True,
        ),
    ]

    config = experiment.get_file_of_type(
        FileType.BEAST2_CONFIGURATION, prefer_preview=False
    )
    assert config
    assert config.name == "beast.xml"

    posterior = experiment.get_file_of_type(
        FileType.POSTERIOR_TREES, prefer_preview=False
    )
    assert posterior
    assert posterior.name == "trees.xml"

    with pytest.raises(FileNotFoundError):
        experiment.get_file_of_type(
            FileType.BEAST2_POSTERIOR_LOGS, prefer_preview=False
        )


def test_previews_are_preferred_if_preview_is_true(
    experiment: PaperWithExperiment,
):
    experiment.files = [
        File(
            name="beast2.xml",
            type=FileType.BEAST2_CONFIGURATION,
            size_bytes=0,
            md5="",
            local_path=Path("beast2.xml"),
        ),
        File(
            name="beast2 (preview).xml",
            type=FileType.BEAST2_CONFIGURATION,
            size_bytes=0,
            md5="",
            local_path=Path("beast2 (preview).xml"),
            is_preview=True,
        ),
        File(
            name="trees.xml",
            type=FileType.POSTERIOR_TREES,
            size_bytes=0,
            md5="",
            local_path=Path("trees.xml"),
        ),
        File(
            name="logs (preview).xml",
            type=FileType.BEAST2_POSTERIOR_LOGS,
            size_bytes=0,
            md5="",
            local_path=Path("logs (preview).xml"),
            is_preview=True,
        ),
    ]

    config = experiment.get_file_of_type(
        FileType.BEAST2_CONFIGURATION, prefer_preview=True
    )
    assert config
    assert config.name == "beast2 (preview).xml"

    posterior = experiment.get_file_of_type(
        FileType.POSTERIOR_TREES, prefer_preview=True
    )
    assert posterior
    assert posterior.name == "trees.xml"

    logs = experiment.get_file_of_type(
        FileType.BEAST2_POSTERIOR_LOGS, prefer_preview=True
    )
    assert logs
    assert logs.name == "logs (preview).xml"


def test_previews_are_preferred_if_preview_env_is_set_true(
    experiment: PaperWithExperiment, set_preview_env_var_to_true
):
    experiment.files = [
        File(
            name="beast2.xml",
            type=FileType.BEAST2_CONFIGURATION,
            size_bytes=0,
            md5="",
            local_path=Path("beast2.xml"),
        ),
        File(
            name="beast2 (preview).xml",
            type=FileType.BEAST2_CONFIGURATION,
            size_bytes=0,
            md5="",
            local_path=Path("beast2 (preview).xml"),
            is_preview=True,
        ),
    ]

    config = experiment.get_file_of_type(FileType.BEAST2_CONFIGURATION)
    assert config
    assert config.name == "beast2 (preview).xml"


def test_previews_are_preferred_if_preview_env_is_set_false(
    experiment: PaperWithExperiment, set_preview_env_var_to_false
):
    experiment.files = [
        File(
            name="beast2.xml",
            type=FileType.BEAST2_CONFIGURATION,
            size_bytes=0,
            md5="",
            local_path=Path("beast2.xml"),
        ),
        File(
            name="beast2 (preview).xml",
            type=FileType.BEAST2_CONFIGURATION,
            size_bytes=0,
            md5="",
            local_path=Path("beast2 (preview).xml"),
            is_preview=True,
        ),
    ]

    config = experiment.get_file_of_type(FileType.BEAST2_CONFIGURATION)
    assert config
    assert config.name == "beast2.xml"
