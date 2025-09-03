---
title: Uploading Experiments - PhyloData Python Library
description: Learn more about the PhyloData Python library.
---

# Uploading experiments

?> Please don't upload experiments to PhyloData. I'm still working on the uploading pipeline and the quality control process.

You can upload your published Bayesian phylogenetic analysis by running the following command in your terminal:

```bash
phylodata process
```

This will open an interactive tool that will guide you through the process of uploading your experiment.

## Modifying the metadata

There are multiple ways to modify the automatically generated metadata of your experiment:

```bash
# remove all classifications
phylodata remove-classification editable_phylodata_metadata.json

# remove the classification with the given ID
phylodata remove-classification editable_phylodata_metadata.json --sample_id someId

# remove all classification of the given type (ncbi_taxonomy_id or glottolog_id)
phylodata remove-classification editable_phylodata_metadata.json --classification_type ncbi_taxonomy_id

# set the NCBI taxonomy ID for a sample with the given ID
phylodata change-ncbi editable_phylodata_metadata.json someId 8823

# set the glottolog language for a sample with the given ID
phylodata change-glottolog editable_phylodata_metadata.json someId "St. Gallen"
```
