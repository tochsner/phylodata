package com.phylodata;

import com.phylodata.config.PhyloDataConfig;
import com.phylodata.loader.ExperimentLoader;
import com.phylodata.loader.FileDownloader;
import com.phylodata.types.File;
import com.phylodata.types.PaperWithExperiment;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class Tests {

    public static void main(String[] args) throws IOException {
        PhyloDataConfig.preferFull();

        PaperWithExperiment experiment = ExperimentLoader.loadExperiment(
            "munro-2019-climate-6tvf", 1, Paths.get("Hallo")
        );

        System.out.println(experiment.getPaper().getTitle());
        System.out.println(experiment.getSamples().get(0));
    }

}
