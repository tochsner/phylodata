# Core principles of PhyloData

This document outlines the ideas and vision behind PhyloData. It is a work in progress and simply a place to collect and organize my thoughts on the project.

> [!NOTE]
> I'm still very much new to the world of Bayesian phylogenetics. I will try to reinvent the wheel. Any ideas, feedback, or pointers to existing solutions and resources are very much welcome! Simply open up an issue on GitHub or [send me an email](mailto:tobia.ochsner@hotmail.com).

## Origin

I noticed two things when working on Bayesian phylogenetics methods during the last year:

1. As a method developer, it is not easy to find real-world experiments to test your methods on. While data is usually hosted on platforms like Dryad, Figshare, or Zenodo, the lack of standardized metadata makes it cumbersome to find and use these datasets.

2. Even though there is an active ecosystem, (when developing methods,) standardized workflows and tooling do not seem to be as prevalent compared to the machine learning or web development communities I'm used to. This might very well due to my own ignorance. However, in that case it would still be sign of the lack of *documentation* and *education*.

The idea for PhyloData was born out of those two observations.

## MVP

- PhyloData is a data repository focused on *Bayesian Phylogenetics Experiments*.
- It provides a standardized way to store and share data from these experiments.
- Each experiment is automatically decorated with metadata, allowing for easy discovery.

These three points form the basic requirements of a first MVP of PhyloData. However, my vision for the platform goes beyond these.

## Core principles

**The platform supports method developers when validating and comparing new methods.**

- There should be a large variety of published experiments available to enable the validation a wide range of methods.
- The filtering and downloading of experiments should be easy and reproducible (potentially using high-quality libraries for data loading).
- The metadata should be standardized, consistent, and easily accessible (e.g. using standardized JSON schemas or specific libraries).
- It may be useful to track and visualize performance of developed methods on a set of benchmarks.

**The platform supports researchers when conducting Bayesian Phylogenetics experiments.**

- It should be easy to find experiments on specific subjects (e.g. species, genes, or languages).
- It should be easy to find experiments using specific methods or models.
- It should be easy to upload your own experiments to the platform.
- There may be resources on how to select and use models.

**The platform documents and creates opinionated workflows and tooling related to the creation, organization, usage, and reproducability of Bayesian Phylogenetics data.**

- There may be a opinionated guide for method developers on how to organize data on disk, load them into memory, process them using reproducible pipelines, and store results.
- There may be library recommendations and custom-built libraries supporting method developers using the opinionated workflows. Examples are correct and fast NEXUS parsers in common languages and libraries to programatically load PhyloData experiments.
- There may be guides for researchers on how to conduct experiments, make them reproducible, and upload them to PhyloData.

**The platform is community driven.**

- The platform code as well as the data published should be open-source under a suitable license (tbd).
- There should be an active exchange of ideas and feedback between researchers, method developers, and the platform developers.
- Researchers should be able to publish their experiments on the platform.
- Method developers should be able to publish their methods on the platform.
- There may be a possibility for community members to publish their own articles and recommendations on the platform.
