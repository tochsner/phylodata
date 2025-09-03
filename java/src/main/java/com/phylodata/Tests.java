package com.phylodata;

import com.phylodata.config.PhyloDataConfig;
import com.phylodata.loader.ExperimentLoader;
import com.phylodata.loader.ExperimentToLoad;
import com.phylodata.loader.ExperimentsLoader;
import com.phylodata.types.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Tests {

    public static void main(String[] args) throws IOException {
        List<PaperWithExperiment> experiments = new ExperimentsLoader(
                new ExperimentToLoad("nen-2019-postglacial-qh0e", 1),
                new ExperimentToLoad("nen-2019-postglacial-n1bf", 1)
        ).load();

        for (PaperWithExperiment experiment : experiments) {
            int numSamples = experiment.getSamples().size();

            // getLocalFolder returns the location of the experiment files
            Path experimentFolder = experiment.getLocalFolder();
            // outputPath will be "data/nen-2019-postglacial-qh0e/num_samples.txt"
            Path outputPath = experimentFolder.resolve("num_samples.txt");

            try(FileWriter writer = new FileWriter(outputPath.toString()) ){
                writer.write("Num samples: ");
                writer.write(numSamples);
            }
        }
    }

}
