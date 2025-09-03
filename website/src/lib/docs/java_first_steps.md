---
title: First Steps - PhyloData Java Library
description: Learn more about the PhyloData Java library.
---

# First steps

## What is the PhyloData Java library?

The library allows you to access experiments on PhyloData using Java. It is designed to make handling data as easy as possible, so that you can focus on more interesting things.

## Installation

You can use Maven to install the library. Add the following to your `pom.xml` file:

TODO

Alternatively, you can download the jars directly from Maven Central.

TODO

## Download an experiment

[Go find an interesting experiment](/) and click on _Use Experiment_, or simply try the following snippet:

```java
import com.phylodata.loader.ExperimentLoader;
import com.phylodata.types.PaperWithExperiment;

public class Tests {

    public static void main(String[] args) {
        PaperWithExperiment experiment = ExperimentLoader.loadExperiment(
            "munro-2019-climate-6tvf", 1
        );

        System.out.println("Title: " + experiment.getPaper().getTitle());
    }

}

```

PhyloData downloads all relevant files into the `data/munro-2019-climate-6tvf` folder. However, you don't have to juggle with file paths to access the files:

```java
PaperWithExperiment experiment = ExperimentLoader.loadExperiment(
    "munro-2019-climate-6tvf", 1
);
File treesFile = Files.getFileOfType(experiment, File.FileType.POSTERIOR_TREES);

System.out.println("Trees file path: " + treesFile.getLocalPath());
```

This means that you will never have to worry about where and how the files are stored. You can always run the same program and it just works - no matter if it is on your local computer, a remote server, or on the computer of a friend.

## Next steps

Now that you've seen the basic way to download an experiment, you should explore the main concepts of the library:

- [Loading experiments](/docs/java_downloading_experiments)
- [Accessing the files of experiments](/docs/java_files)
- [Dealing with large files](/docs/java_large_files)
- [Accessing metadata of experiments](/docs/java_metadata)
