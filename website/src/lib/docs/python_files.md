---
title: Accessing files - PhyloData Python Library
description: Learn more about the PhyloData Python library.
---

# Accessing files

This page explains everything there is to know about accessing files from PhyloData.

!> Generally, you should use one of the functions described on this page to access files, instead of manually constructing their paths. Working with file paths can easily lead to errors and inconsistencies.

!> Using these functions also ensures that everything works nicely with **preview files**. See [Dealing with large files](/docs/python_large_files) for more information.

## What is a file?

The File object returned by the library looks something like this:

```python
class File:
    name: str
    type: FileType # see below for a list of all possible file types
    local_path: Path
    size_bytes: int
    md5: str
    is_preview: bool = False
```

The `local_path` is the path to the file on your local computer. The file type is one of `FileType.BEAST2_CONFIGURATION`, `FileType.BEAST2_POSTERIOR_LOGS`, `FileType.POSTERIOR_TREES`, `FileType.SUMMARY_TREE`, or `FileType.UNKNOWN`.

!> `local_path` is a Python `Path` object, which allows things like `local_path.parent`, `local_path.stem`, or `local_path.name`. [Check it out!](https://docs.python.org/3/library/pathlib.html)

## Accessing a file of a given type

Most experiments have at most one file of a given type. If you want to access a file of a specific type, you can use the `get_file_of_type` function:

```python
from phylodata import load_experiment, FileType

experiment = load_experiment("munro-2019-climate-6tvf", version=1)
posterior_trees_file = experiment.get_file_of_type(FileType.POSTERIOR_TREES)

print(posterior_trees_file.local_path) # prints the path to the file
```

This will return the first file of the given type. Note that you do not even have to know the name of the file!

?> These functions return `None` if no matching file exists.

## Accessing all files of a given type

If you want to access all files of a specific type, you can use the `get_files_of_type` function:

```python
from phylodata import load_experiment, FileType

experiment = load_experiment("munro-2019-climate-6tvf", version=1)
posterior_trees_files = experiment.get_files_of_type(FileType.POSTERIOR_TREES)
```

## Accessing all files

If you want to access all files of an experiment, you can use the `get_files` function:

```python
from phylodata import load_experiment

experiment = load_experiment("munro-2019-climate-6tvf", version=1)
files = experiment.get_files()

for file in files:
    print(file.name)
    print(file.type)
    print(file.local_path)
```

## Accessing a file of a given name

If you want to access a file of a specific name, you can use the `get_file` function:

```python
from phylodata import load_experiment, FileType

experiment = load_experiment("munro-2019-climate-6tvf", version=1)
posterior_trees_file = experiment.get_file("Meta.subset1.trim1.ingroup.B.xml")
```
