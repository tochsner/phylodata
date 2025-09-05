---
title: Downloading Experiments - PhyloData Python Library
description: Learn more about the PhyloData Python library.
---

# Loading experiments

This page explains you everything there is to know about loading experiments from PhyloData.

## What is an experiment?

An experiment is a Bayesian phylogenetic analysis that has been conducted as part of a publication. It consists of a bunch of files (think BEAST2 XML files, BEAST2 log files, trees, etc.) and metadata (like the title of the paper, the authors, the species investigated, etc.). Check out [an example experiment](/experiments/https%3A%2F%2Fdoi.org%2F10.1098%2Frspb.2019.0234) to get an idea of what an experiment looks like.

For now, it is important to know that each experiment has a unique ID (like `munro-2019-climate-6tvf`) and a version number (like `1`). The version number is incremented every time the experiment is updated.

## Loading an experiment

We use the `load_experiment` function to load an experiment from PhyloData. Its definition looks like this:

```python
def load_experiment(
    experiment_id: str,
    version: Optional[int] = None,
    directory: Optional[str | Path] = None,
    download_only_preview: Optional[bool] = None,
    files_to_download: Optional[list[str | FileType]] = None,
    force_download: bool = False,
    citations_file: Optional[str | Path] = None,
) -> PaperWithExperiment:
    ...
```

This is a lot to unpack, so let's go through it step by step.

## Specifying the experiment to load

The most important argument is `experiment_id`. You can also specify the version of the experiment to load. If no version is specified, the most recent version will be loaded.

```python
from phylodata import load_experiment
experiment = load_experiment("munro-2019-climate-6tvf", version=1)
```

!> You should always specify the version of the experiment to load. This ensures that you get the same results even if the experiment is updated.

## Where are the files stored?

By default, the experiment files are stored in a folder called `data` in the current working directory. If you want to specify a different directory, you can do so by passing the `directory` argument:

```python
from phylodata import load_experiment
experiment = load_experiment(
    "munro-2019-climate-6tvf",
    version=1,
    directory="folder/to/my_experiments",
)
```

!> PhyloData will only download the files if they don't already exist. Simply put the `load_experiment` function at the beginning of your program, it won't download the files more than once.

If you really want to download the files again, you can use the `force_download` argument.

## Only download some files

In some cases, you might only be interested in a subset of the files.

You can only download files with certain file names:

```python
from phylodata import load_experiment
experiment = load_experiment(
    "munro-2019-climate-6tvf",
    version=1,
    files_to_download=["Meta.subset1.trim1.ingroup.B.xml"],
)
```

Alternatively, you can only download files of a certain type:

```python
from phylodata import load_experiment, FileType
experiment = load_experiment(
    "munro-2019-climate-6tvf",
    version=1,
    files_to_download=[FileType.POSTERIOR_TREES],
)
```

You can choose from the following file types: `FileType.BEAST2_CONFIGURATION`, `FileType.BEAST2_POSTERIOR_LOGS`, `FileType.POSTERIOR_TREES`, `FileType.SUMMARY_TREE`, `FileType.UNKNOWN`.

## Loading multiple experiments

You can also load multiple experiments at once:

```python
from phylodata import load_experiments, ExperimentToLoad
experiments = load_experiments(
	[
		ExperimentToLoad("nen-2019-postglacial-qh0e", version=1),
		ExperimentToLoad("nen-2019-postglacial-n1bf", version=1)
	],
)
```

This will return a list of `PaperWithExperiment` objects. The `load_experiments` function supports the same options as `load_experiment`.
