---
title: Downloading Experiments - PhyloData Java Library
description: Learn more about the PhyloData Java library.
---

# Loading experiments

This page explains you everything there is to know about loading experiments from PhyloData.

## What is an experiment?

An experiment is a Bayesian phylogenetic analysis that has been conducted as part of a publication. It consists of a bunch of files (think BEAST2 XML files, BEAST2 log files, trees, etc.) and metadata (like the title of the paper, the authors, the species investigated, etc.). Check out [an example experiment](/experiments/https%3A%2F%2Fdoi.org%2F10.1098%2Frspb.2019.0234) to get an idea of what an experiment looks like.

For now, it is important to know that each experiment has a unique ID (like `munro-2019-climate-6tvf`) and a version number (like `1`). The version number is incremented every time the experiment is updated.

## Loading an experiment

We use the `ExperimentLoader` class to load an experiment from PhyloData.

There are multiple possibilities when it comes to loading an experiment, let's go through them one by one.

## Specifying the experiment to load

The simplest way to load an experiment is to specify the ID and version:

```java
PaperWithExperiment experiment = ExperimentLoader.loadExperiment(
    "munro-2019-climate-6tvf", 1
);
```

!> You always have to specify the version of the experiment to load. This ensures that you get the same results even if the experiment is updated.

## Where are the files stored?

By default, the experiment files are stored in a folder called `data` in the current working directory. If you want to specify a different directory, you can do so by passing an additional argument:

```java
PaperWithExperiment experiment = ExperimentLoader.loadExperiment(
    "munro-2019-climate-6tvf", 1, Paths.get("some/other/folder")
);
```

!> PhyloData will only download the files if they don't already exist. Simply put the `loadExperiment` method call at the beginning of your program, it won't download the files more than once.

## Only download some files

In some cases, you might only be interested in a subset of the files.

Alternatively, you can only download files of a certain type:

```java
// download only BEAST2 configuration files
PaperWithExperiment experiment = ExperimentLoader.loadExperiment(
    "munro-2019-climate-6tvf", 1, Set.of(File.FileType.BEAST_2_CONFIGURATION)
);

// download tree files
PaperWithExperiment experiment = ExperimentLoader.loadExperiment(
    "munro-2019-climate-6tvf", 1,
    Set.of(File.FileType.POSTERIOR_TREES, File.FileType.SUMMARY_TREE)
);
```

You can choose from the following file types: `File.FileType.BEAST2_CONFIGURATION`, `File.FileType.BEAST2_POSTERIOR_LOGS`, `File.FileType.POSTERIOR_TREES`, `File.FileType.SUMMARY_TREE`, `File.FileType.UNKNOWN`.

!> For full control, check out the most general `ExperimentLoader.loadExperiment` method. It allows you to combine all of the above options.

## Loading multiple experiments

You can also load multiple experiments at once:

```java
List<PaperWithExperiment> experiments = ExperimentLoader.loadExperiments(
    List.of(
        new ExperimentLoader.ExperimentToLoad("nen-2019-postglacial-qh0e", 1),
        new ExperimentLoader.ExperimentToLoad("nen-2019-postglacial-n1bf", 1)
    )
);
```

This will return a list of `PaperWithExperiment` objects. The `loadExperiments` method supports the same options as `loadExperiment`.
