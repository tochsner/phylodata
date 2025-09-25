---
title: Citing Experiments - PhyloData Java Library
description: Learn more about the PhyloData Java library.
---

# Citing experiments

If you use the PhyloData Java library, please cite this website:

_Ochsner, T. S. (2025). PhyloData. https://phylodata.com_

Additionally, you should cite all experiments you use. Calling `ExperimentsLoader(...).load()` (or `ExperimentLoader(...).load()`) will automatically create a bibtex file with all relevant citations. By default, this file will be stored in the same folder as the experiments.

Calling `ExperimentsLoader(...).load()` will overwrite any existing bibtex file. If you load experiments multiple times and want to get a bibtex file for every call, you can use the `citationsInto` method to specify the location of the bibtex file:

```java
List<PaperWithExperiment> experiments = new ExperimentsLoader(
	new ExperimentToLoad("nen-2019-postglacial-qh0e", 1),
	new ExperimentToLoad("nen-2019-postglacial-n1bf", 1)
).citationsInto(Paths.get("some/folder/citations.bib")).load();
```
