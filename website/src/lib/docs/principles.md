---
title: Core Principles
description: Learn more about the core principles behind PhyloData.
---

# Core Principles

This document outlines PhyloData's vision and goals. It is a work in progress, a place to collect and organize my thoughts on the project.

!> I'm new to Bayesian phylogenetics. I will try to reinvent the wheel. Ideas, feedback, or pointers to existing solutions are very much welcome! Start a GitHub issue or [send me an email](mailto:tobia.ochsner@hotmail.com).

## Background

I noticed two things when working on Bayesian phylogenetics methods:

1. Finding real-world test data is difficult due to poor metadata on platforms like Dryad.

2. In the ecosystem, standardized workflows and tooling do not seem to be as prevalent compared to machine learning or web development communities. Everyone does things slightly differently.

PhyloData aims to solve these challenges.

## MVP

- PhyloData is a data repository for _Bayesian Phylogenetics Experiments_.
- It provides a standardized way to store and share data from these experiments.
- For easy discovery, experiments are automatically decorated with metadata.

## Core Principles

**The platform helps method developers validate and compare methods.**

- There should be a large variety of published experiments to enable the validation of a wide range of methods.
- Filtering and downloading of experiments should be easy and reproducible (potentially using custom-made libraries).
- Metadata should be standardized, consistent, and easily accessible (e.g. using standardized JSON schemas or custom-made libraries).
- It may be useful to track performance of methods on a set of benchmarks.

**The platform helps researchers conduct experiments.**

- It should be easy to find experiments by subject (e.g. species, genes, or languages).
- It should be easy to find experiments by methods or models.
- It should be easy to upload experiments.
- There may be resources on how to select and use methods and models.

**The platform documents and creates opinionated workflows and tooling related to the creation, organization, usage, and reproducability of data.**

- There may be opinionated guides for method developers on how to organize data on disk, load it into memory, process it using reproducible pipelines, and store results.
- There may be library recommendations and custom-made libraries to help adoption of the opinionated workflows. Examples are libraries to programatically load PhyloData experiments or correct and fast NEXUS parsers.
- There may be guides for researchers to help conduct experiments and make them reproducible.

**The platform is community driven.**

- The code and the published data should be open-source.
- There should be an active exchange of ideas between researchers, method developers, and platform developers.
- Community members may publish their own articles and recommendations.
