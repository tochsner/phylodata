---
title: Citing Experiments - PhyloData Java Library
description: Learn more about the PhyloData Java library.
---

# Citing experiments

If you use the PhyloData Java library, please cite this website:

_Ochsner, T. S. (2025). PhyloData. https://phylodata.com_

Additionally, you should cite all experiments you use. Calling `load_experiments` (or `load_experiment`) will automatically create a bibtex file with all relevant citations. By default, this file will be stored in the same folder as the experiments.

Calling `load_experiments)` will overwrite any existing bibtex file. If you load experiments multiple times and want to get a bibex file for every call, you can use the `citations_file` argument to specify the location of the bibtex file:

```java
experiments = load_experiments(
	[
		ExperimentToLoad("nen-2019-postglacial-qh0e", version=1),
		ExperimentToLoad("nen-2019-postglacial-n1bf", version=1)
	],
	citations_file=Path("some/folder/citations.bib")
)
```
