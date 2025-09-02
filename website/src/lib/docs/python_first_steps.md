---
title: First Steps - PhyloData Python Library
description: Learn more about the PhyloData Python library.
---

# First steps

## What is the PhyloData Python library?

The library allows you to access experiments on PhyloData using Python. It is designed to make handling data as easy as possible, so that you can focus on more interesting things.

## Installation

Install the library using pip:

```bash
pip install phylodata --upgrade
```

## Download an experiment

[Go find an interesting experiment](/) and click on _Use Experiment_, or simply try the following snippet:

```python
from phylodata import load_experiment

experiment = load_experiment("munro-2019-climate-6tvf", version=1)
print("Paper title:", experiment.paper.title)
```

PhyloData downloads all relevant files into the `data/savelyev-2020-bayesian-88zc` folder. However, you don't have to juggle with file paths to access the files:

```python
from phylodata import load_experiment, get_file_of_type, FileType

experiment = load_experiment("munro-2019-climate-6tvf", version=1)
trees_file = get_file_of_type(experiment, FileType.POSTERIOR_TREES)

print("Trees file path:", trees_file.local_path)
```

This means that you will never have to worry about where and how the files are stored. You can always run the same script and it just works - no matter if it is on your local computer, a remote server, or on the computer of a friend.

## Next steps

Now that you've seen the basic way to download an experiment, you should explore the main concepts of the library:

- [Loading experiments](/docs/python_downloading_experiments)
- [Accessing the files of experiments](/docs/python_files)
- [Dealing with large files](/docs/python_large_files)
- [Accessing metadata of experiments](/docs/python_metadata)
- [Uploading your own experiments](/docs/python_uploading_experiments)
