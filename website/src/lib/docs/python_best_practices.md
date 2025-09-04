---
title: Best practices - PhyloData Python Library
description: Learn more about the PhyloData Python library.
---

# Best pratices

This page talks about some best practices when working with PhyloData.

## Download as Code

One of the advantages of using PhyloData is that you **download the data using code**. Don't run the `load_experiments` function manually at the very beginning of your project. Instead, call it at the very beginning of your program:

```python
from phylodata import load_experiments, ExperimentToLoad

experiments = load_experiments(
	[
		ExperimentToLoad("nen-2019-postglacial-qh0e", version=1),
		ExperimentToLoad("nen-2019-postglacial-n1bf", version=1)
	],
)

# do the rest of your analysis
```

This allows everyone to reproduce your analysis without having to figure out how and where to store the input data. Note that calling `load_experiments` does not do anything if the experiment has already been downloaded.

If you're not using Python as your main programming language, you should create a `download_experiments.py` script that downloads the experiments. Store this script in your git repository and mention it in the README.

!> Check out the [Java library](/docs/java_first_steps) if you're using Java.

## No manual handling of intermediate files

Quite often, your program will create further files during the analysis. For example, you might create a summary tree from the posterior trees. Your scripts should not require the manual creation of the necessary folders and files.

One pattern that works well is to use the folders already created by PhyloData to store experiment-related intermediate and output files:

```python
from phylodata import load_experiments, ExperimentToLoad, get_folder

experiments = load_experiments(
	[
		ExperimentToLoad("nen-2019-postglacial-qh0e", version=1),
		ExperimentToLoad("nen-2019-postglacial-n1bf", version=1)
	],
)

for experiment in experiments:
    num_samples = len(experiment.samples)

    # get_folder returns the location of the experiment files
    output_file = get_folder(experiment) / "num_samples.txt"
    with open(output_file, "w") as handle:
        handle.write(f"Num samples: {num_samples}")
```

!> `get_folder` returns a Python `Path` object, which allows things like the `get_folder(experiment) / "num_samples.txt"` syntax. [Check it out!](https://docs.python.org/3/library/pathlib.html)

## Avoid version control for data files

Platforms like GitHub have size restrictions for files. Add the `data` folder to your `.gitignore` file to avoid problems.
