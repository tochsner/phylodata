package com.phylodata;

import com.phylodata.loader.ExperimentLoader;
import com.phylodata.loader.Files;
import com.phylodata.types.File;
import com.phylodata.types.PaperWithExperiment;

import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class Tests {

    public static void main(String[] args) {
        PaperWithExperiment experiment = ExperimentLoader.loadExperiment(
                "munro-2019-climate-6tvf", 1
        );
        List<File> summaryTreesFiles = Files.getFilesOfType(experiment, File.FileType.SUMMARY_TREE);
    }

}
