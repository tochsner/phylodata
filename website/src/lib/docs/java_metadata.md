---
title: Accessing metadata - PhyloData Java Library
description: Learn more about the PhyloData Java library.
---

# Accessing metadata

This page explains you everything there is to know about accessing metadata from PhyloData.

## What is metadata?

Every experiment has a bunch of metadata associated with it. You can explore almost everything about an experiment by looking at its page on this website. However, you can also access the metadata using the PhyloData Java library.

Every `loadExperiment` methods return a `PaperWithExperiment` object. It has the following structure:

```java
public class PaperWithExperiment {
    Paper getPaper();
    Experiment getExperiment();
    List<File> getFiles();
    List<Sample> getSamples();
    Object getTrees();
    List<EvolutionaryModelComponent> getEvolutionaryModel();
    Metadata getMetadata();
}
```

As you can see, the structure closesly matches the experiment pages on the website.

## Paper metadata

```java
PaperWithExperiment experiment = ExperimentLoader.loadExperiment(
    "munro-2019-climate-6tvf", 1
);

System.out.println(experiment.getPaper().getTitle());
System.out.println(experiment.getPaper().getYear());
System.out.println(experiment.getPaper().getAbstract());
System.out.println(experiment.getPaper().getAuthors());
System.out.println(experiment.getPaper().getDoi());
System.out.println(experiment.getPaper().getBibtex());
```

## Experiment metadata

```java
PaperWithExperiment experiment = ExperimentLoader.loadExperiment(
    "munro-2019-climate-6tvf", 1
);

System.out.println(experiment.getExperiment().getType());
System.out.println(experiment.getExperiment().getHumanReadableId());
System.out.println(experiment.getExperiment().getUploadDate());
System.out.println(experiment.getExperiment().getVersion());
```

## File metadata

```java
PaperWithExperiment experiment = ExperimentLoader.loadExperiment(
    "munro-2019-climate-6tvf", 1
);

for (File file : experiment.getFiles()) {
    System.out.println(file.getName());
    System.out.println(file.getType());
    System.out.println(file.getLocalPath());
    System.out.println(file.getSizeBytes());
    System.out.println(file.getMd5());
    System.out.println(file.getIsPreview());
}
```

?> Use the `get_file` functions to access specific files (see [Accessing files](/docs/python_files)).

## Sample metadata

```java
PaperWithExperiment experiment = ExperimentLoader.loadExperiment(
    "munro-2019-climate-6tvf", 1
);

for (Sample sample : experiment.getSamples()) {
    System.out.println(sample.getSampleId());
    System.out.println(sample.getScientificName());
    System.out.println(sample.getClassification());
    System.out.println(sample.getSampleData());
}
```

## Trees metadata

TODO

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

```java
PaperWithExperiment experiment = ExperimentLoader.loadExperiment(
    "munro-2019-climate-6tvf", 1
);

for (EvolutionaryModelComponent model : experiment.getEvolutionaryModel()) {
    System.out.println(model.getName());
    System.out.println(model.getType());
}
```
