---
title: Best practices - PhyloData Java Library
description: Learn more about the PhyloData Java library.
---

# Best pratices

This page talks about some best practices when working with PhyloData.

## Download as Code

One of the advantages of using PhyloData is that you **download the data using code**. Don't run the `loadExperiments` function manually at the very beginning of your project. Instead, call it at the very beginning of your program:

```java
List<PaperWithExperiment> experiments = ExperimentLoader.loadExperiments(
    List.of(
        new ExperimentLoader.ExperimentToLoad("nen-2019-postglacial-qh0e", 1),
        new ExperimentLoader.ExperimentToLoad("nen-2019-postglacial-n1bf", 1)
    )
);

// do the rest of your analysis
```

This allows everyone to reproduce your analysis without having to figure out how and where to store the input data. Note that calling `loadExperiments` does not do anything if the experiment has already been downloaded.

## No manual handling of intermediate files

Quite often, your program will create further files during the analysis. For example, you might create a summary tree from the posterior trees. Your scripts should not require the manual creation of the necessary folders and files.

One pattern that works well is to use the folders already created by PhyloData to store experiment-related intermediate and output files:

```java
List<PaperWithExperiment> experiments = ExperimentLoader.loadExperiments(
		List.of(
				new ExperimentLoader.ExperimentToLoad("nen-2019-postglacial-qh0e", 1),
				new ExperimentLoader.ExperimentToLoad("nen-2019-postglacial-n1bf", 1)
		)
);

for (PaperWithExperiment experiment : experiments) {
	int numSamples = experiment.getSamples().size();

	// getFolder returns the location of the experiment files
	Path experimentFolder = Files.getFolder(experiment);
	// outputPath will be "data/nen-2019-postglacial-qh0e/num_samples.txt"
	Path outputPath = experimentFolder.resolve("num_samples.txt");

	try(FileWriter writer = new FileWriter(outputPath.toString()) ){
		writer.write("Num samples: ");
		writer.write(numSamples);
	}
}
```

## Avoid version control for data files

If your data files are too big, platforms like GitHub might lead to problems. Add the `data` folder to your `.gitignore` file to avoid problems.
