---
title: Accessing files - PhyloData Java Library
description: Learn more about the PhyloData Java library.
---

# Accessing files

This page explains you everything there is to know about accessing files from PhyloData.

!> Generally, you should use one of the methods described on this page to access files, instead of manually constructing their paths. Working with file paths can easily lead to errors and inconsistencies.

!> Using these methods also ensures that everything works nicely with **preview files**. See [Dealing with large files](/docs/python_large_files) for more information.

## What is a file?

The File object returned by the library looks something like this:

```java
public class File {
    String getName();
    File.FileType getType();
    Path getLocalPath();
    int getSizeBytes();
    String getMd5();
    boolean getIsPreview();
}
```

The `local_path` is the path to the file on your local computer. The file type is one of `FileType.BEAST2_CONFIGURATION`, `FileType.BEAST2_POSTERIOR_LOGS`, `FileType.POSTERIOR_TREES`, `FileType.SUMMARY_TREE`, or `FileType.UNKNOWN`.

!> `getLocalPath` returns a `java.nio.file.Path` object, which allows things like `getLocalPath().getParent()`. [Check it out!](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html)

## Accessing a file of a given type

Most experiments have at most one file of a given type. If you want to access a file of a specific type, you can use the `getFileOfType` function:

```java
PaperWithExperiment experiment = new ExperimentLoader(
    "munro-2019-climate-6tvf", 1
).load();
File posterior_trees_file = experiment.getFileOfType(File.FileType.POSTERIOR_TREES);
System.out.println("Trees path: " + posterior_trees_file.getLocalPath());
```

This will return the first file of the given type. Note that you do not even have to know the name of the file!

?> These functions throw a `FileNotFoundException` if no matching file exists.

## Accessing all files of a given type

If you want to access all files of a specific type, you can use the `getFilesOfType` function:

```java
PaperWithExperiment experiment = new ExperimentLoader(
    "munro-2019-climate-6tvf", 1
).load();
List<File> summaryTreesFiles = experiment.getFilesOfType(
    File.FileType.SUMMARY_TREE
);
```

## Accessing all files

If you want to access all files of an experiment, you can use the `getFiles` function:

```java
PaperWithExperiment experiment = new ExperimentLoader(
    "munro-2019-climate-6tvf", 1
).load();

List<File> files = experiment.getFiles();

for (File file : files) {
    System.out.println("File name: " + file.getName());
    System.out.println("File type: " + file.getType());
    System.out.println("File size: " + file.getSizeBytes());
}
```

## Accessing a file of a given name

If you want to access a file of a specific name, you can use the `getFile` function:

```java
PaperWithExperiment experiment = new ExperimentLoader(
    "munro-2019-climate-6tvf", 1
).load();
File posteriorTreesFile = experiment.getFile("Meta.subset1.trim1.ingroup.B.xml");
```
