package com.phylodata.loader;

import com.phylodata.types.PaperWithExperiment;

import java.util.ArrayList;
import java.util.List;

public class ExperimentsLoader extends ExperimentLoaderBuilder<List<PaperWithExperiment>> {

    private final ExperimentToLoad[] experimentsToLoad;

    public ExperimentsLoader(ExperimentToLoad... toLoad) {
        this.experimentsToLoad = toLoad;
    }

    /**
     * Executes the download and loading flow using the configured options
     * and returns the assembled experiments metadata.
     *
     * @return loaded PaperWithExperiment instance
     * @throws RuntimeException if any IO error occurs while downloading or reading files
     */
    public List<PaperWithExperiment> load() {
        List<PaperWithExperiment> experiments = new ArrayList<>();
        for (ExperimentToLoad exp : experimentsToLoad) {
            PaperWithExperiment loaded = new ExperimentLoader(exp)
                    .intoDirectory(directory)
                    .preferPreview(downloadOnlyPreview)
                    .restrictFileName(filesToDownload)
                    .restrictFileTypes(fileTypesToDownload)
                    .forceDownload(forceDownload)
                    .load();
            experiments.add(loaded);
        }
        return experiments;
    }
}