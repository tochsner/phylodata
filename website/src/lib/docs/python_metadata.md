---
title: Accessing metadata - PhyloData Python Library
description: Learn more about the PhyloData Python library.
---

# Accessing metadata

This page explains you everything there is to know about accessing metadata from PhyloData.

## What is metadata?

Every experiment has a bunch of metadata associated with it. You can explore almost everything about an experiment by looking at its page on this website. However, you can also access the metadata using the PhyloData Python library.

Every `load_experiment` function returns a `PaperWithExperiment` object. It has the following structure:

```python
class PaperWithExperiment:
    paper: Paper
    experiment: Experiment
    files: list[File]
    samples: list[Sample]
    trees: Optional[Trees]
    evolutionary_model: list[EvolutionaryModelComponent]
    metadata: Metadata
```

As you can see, the structure closesly matches the experiment pages on the website.

## Paper metadata

```python
from phylodata import load_experiment

experiment = load_experiment("munro-2019-climate-6tvf", version=1)

print(experiment.paper.title)
print(experiment.paper.year)
print(experiment.paper.authors)
print(experiment.paper.abstract)
print(experiment.paper.bibtex)
print(experiment.paper.doi)
```

## Experiment metadata

```python
from phylodata import load_experiment

experiment = load_experiment("munro-2019-climate-6tvf", version=1)

print(experiment.experiment.type)
print(experiment.experiment.human_readable_id)
print(experiment.experiment.upload_date)
print(experiment.experiment.version)
```

## File metadata

```python
from phylodata import load_experiment

experiment = load_experiment("munro-2019-climate-6tvf", version=1)

for file in experiment.files:
    print(file.name)
    print(file.type)
    print(file.size_bytes)
    print(file.md5)
    print(file.is_preview)
    print(file.local_path)
```

?> Use the `get_file` functions to access specific files (see [Accessing files](/docs/python_files)).

## Sample metadata

```python
from phylodata import load_experiment

experiment = load_experiment("munro-2019-climate-6tvf", version=1)

for sample in experiment.samples:
    print(sample.sample_id)
    print(sample.scientific_name)
    print(sample.classification)
    print(sample.sample_data)
    print(sample.common_name)
```

## Trees metadata

```python
from phylodata import load_experiment

experiment = load_experiment("munro-2019-climate-6tvf", version=1)

print(experiment.trees.number_of_trees)
print(experiment.trees.number_of_tips)
print(experiment.trees.ultrametric)
print(experiment.trees.time_tree)
print(experiment.trees.rooted)
print(experiment.trees.average_root_age)
```

## Evolutionary model metadata

```python
from phylodata import load_experiment

experiment = load_experiment("munro-2019-climate-6tvf", version=1)

for model in experiment.evolutionary_model:
    print(model.name)
    print(model.type)
```
