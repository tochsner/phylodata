---
title: Dealing with large files - PhyloData Python Library
description: Learn more about the PhyloData Python library.
---

# Dealing with large files

Bayesian phylogenetic analyses can produce very large files. While working with such files is a general annoyance, we focus on three common consequences:

- Your program takes a long time to run. This makes it more difficult to iterate quickly. We've all waited hours for a program to finish, just to discover that there was a bug in our code.

- You have to run your program on a remote server or HPC. If you write your program locally, there are no guarantees that it will run on a remote cluster.

- Large files take up a lot of space on your local hard drive.

One exciting feature of PhyloData that deals with these issues is **preview files**.

Every **posterior trees file (.trees)** and **logs file (.log)** on PhyloData has a full and a preview version. The full version is the file produced by the phylogenetic experiment. The preview version is a smaller version of the file, containing only a subsample of the records.

Let's look at an example to see how this can be useful:

```python
from phylodata import load_experiment, get_file_of_type, FileType, prefer_preview

prefer_preview()

experiment = load_experiment("munro-2019-climate-6tvf", version=1)
trees_file_path = get_file_of_type(experiment, FileType.POSTERIOR_TREES).local_path

# this is some heavy processing that takes a long time for large files
with open(trees_file_path) as f:
    data = f.readlines()
    print("Number of lines:", len(data))
```

In this snippet, we load an experiment, retrieve the posterior trees file, and perform some operations.

The interesting part is `prefer_preview()`. This tells the library to always use the preview versions of files. This means that `load_experiment` only downloads the smaller preview files, and that `get_file_of_type` automatically returns the preview version of the file. The output of this program is `Number of lines: 358`, a small fraction of the actual number of trees in this experiment (4501).

!> After the `prefer_preview()` call, all other PhyloData functions will magically work with preview files. There is no need to change anything else. If you want to run the full analysis, simply remove the `prefer_preview()` call.

## Using env variables

`prefer_preview()` still requires you to manually change the code whenever you want to switch between preview and full files. As an alternative, you can speciy the `PHYLODATA_PREFER_PREVIEW` environment variable when running the program:

```bash
PHYLODATA_PREFER_PREVIEW=true python my_program.py
```

or

```bash
PHYLODATA_PREFER_PREVIEW=false python my_program.py
```

!> If you're using an IDE like PyCharm, you can set the environment variable by modifying the run configuration.
