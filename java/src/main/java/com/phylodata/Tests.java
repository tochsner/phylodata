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
        PaperWithExperiment experiment = new ExperimentLoader(
                "munro-2019-climate-6tvf", 1
        )
                .restrictFileTypes(
                        File.FileType.POSTERIOR_TREES,
                        File.FileType.SUMMARY_TREE
                ).load();
    }

}
