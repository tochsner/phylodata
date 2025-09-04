---
title: Dealing with large files - PhyloData Java Library
description: Learn more about the PhyloData Java library.
---

# Dealing with large files

Bayesian phylogenetic analyses can produce very large files. While working with such files is a general annoyance, we focus on three common consequences:

- Your program takes a long time to run. This makes it more difficult to iterate quickly. We've all waited hours for a program to finish, just to discover that there was a bug in our code.

- You have to run your program on a remote server or HPC. If you write your program locally, there are no guarantees that it will run on a remote cluster.

- Large files take up a lot of space on your local hard drive.

One exciting feature of PhyloData that deals with these issues is **preview files**.

Every **posterior trees file (.trees)** and **logs file (.log)** on PhyloData has a full and a preview version. The full version is the file produced by the phylogenetic experiment. The preview version is a smaller version of the file, containing only a subsample of the records.

Let's look at an example to see how this can be useful:

```java
PhyloDataConfig.preferPreview();

PaperWithExperiment experiment = new ExperimentLoader(
    "munro-2019-climate-6tvf", 1
).load();

File treesFile = experiment.getFileOfType(File.FileType.POSTERIOR_TREES);
Path treesFilePath = treesFile.getLocalPath();

// count and output the number of lines
try (BufferedReader reader = new BufferedReader(new FileReader(treesFilePath.toString()))) {
    int numLines = 0;
    while (reader.readLine() != null) numLines++;

    System.out.println("Number of lines: " + numLines);
}
```

In this snippet, we load an experiment, retrieve the posterior trees file, and perform some operations.

The interesting part is `PhyloDataConfig.preferPreview();`. This tells the library to always use the preview versions of files. This means that `loadExperiment` only downloads the smaller preview files, and that `getFileOfType` automatically returns the preview version of the file. The output of this program is `Number of lines: 358`, a small fraction of the actual number of trees in this experiment (4501).

!> After the `preferPreview()` call, all other PhyloData functions will magically work with preview files. There is no need to change anything else. If you want to run the full analysis, simply remove the `preferPreview()` call.

## Using env variables

`preferPreview` still requires you to manually change the code whenever you want to switch between preview and full files. As an alternative, you can specify the `PHYLODATA_PREFER_PREVIEW` environment variable when running the program:

```bash
PHYLODATA_PREFER_PREVIEW=true java -jar program.jar
```

or

```bash
PHYLODATA_PREFER_PREVIEW=true java -jar program.jar
```

!> If you're using an IDE like IntelliJ, you can set the environment variable by modifying the run configuration.
