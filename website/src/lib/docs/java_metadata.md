---
title: Accessing metadata - PhyloData Java Library
description: Learn more about the PhyloData Java library.
---

# Accessing metadata

This page explains everything there is to know about accessing metadata from PhyloData.

## What is metadata?

Every experiment has a bunch of metadata associated with it. You can explore almost everything about an experiment by looking at its page on this website. However, you can also access the metadata using the PhyloData Java library.

Every `loadExperiment` method returns a `PaperWithExperiment` object. It has the following structure:

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

As you can see, the structure closely matches the experiment pages on the website.

## Paper metadata

```java
PaperWithExperiment experiment = new ExperimentLoader(
    "munro-2019-climate-6tvf", 1
).load();

System.out.println(experiment.getPaper().getTitle());
System.out.println(experiment.getPaper().getYear());
System.out.println(experiment.getPaper().getAbstract());
System.out.println(experiment.getPaper().getAuthors());
System.out.println(experiment.getPaper().getDoi());
System.out.println(experiment.getPaper().getBibtex());
```

## Experiment metadata

```java
PaperWithExperiment experiment = new ExperimentLoader(
    "munro-2019-climate-6tvf", 1
).load();

System.out.println(experiment.getExperiment().getType());
System.out.println(experiment.getExperiment().getHumanReadableId());
System.out.println(experiment.getExperiment().getUploadDate());
System.out.println(experiment.getExperiment().getVersion());
```

## File metadata

```java
PaperWithExperiment experiment = new ExperimentLoader(
    "munro-2019-climate-6tvf", 1
).load();

for (File file : experiment.getAllFiles()) {
    System.out.println(file.getName());
    System.out.println(file.getType());
    System.out.println(file.getLocalPath());
    System.out.println(file.getSizeBytes());
    System.out.println(file.getMd5());
    System.out.println(file.getIsPreview());
}
```

?> `getAllFiles` returns all files, including preview files and files that haven't been downloaded. `getFiles` and alike only return the files that have been downloaded and respect the preview preferences.

## Sample metadata

```java
PaperWithExperiment experiment = new ExperimentLoader(
    "munro-2019-climate-6tvf", 1
).load();

for (Sample sample : experiment.getSamples()) {
    System.out.println(sample.getSampleId());
    System.out.println(sample.getScientificName());
    System.out.println(sample.getClassification());
    System.out.println(sample.getSampleData());
}
```

## Trees metadata

```java
PaperWithExperiment experiment = new ExperimentLoader(
        "munro-2019-climate-6tvf", 1
).load();

System.out.println(experiment.getTrees().getNumberOfTrees());
System.out.println(experiment.getTrees().getNumberOfTips());
System.out.println(experiment.getTrees().getRooted());
System.out.println(experiment.getTrees().getUltrametric());
System.out.println(experiment.getTrees().getAverageRootAge());
```

## Evolutionary model metadata

```java
PaperWithExperiment experiment = new ExperimentLoader(
    "munro-2019-climate-6tvf", 1
).load();

for (EvolutionaryModelComponent model : experiment.getEvolutionaryModel()) {
    System.out.println(model.getName());
    System.out.println(model.getType());
}
```
