package com.phylodata.loader;

import com.phylodata.types.PaperWithExperiment;

/**
 * <p>ExperimentLoader class.</p>
 *
 * @author tobiaochsner
 */
public class ExperimentLoader extends ExperimentLoaderBuilder<PaperWithExperiment> {

    private final ExperimentToLoad experiment;


    /**
     * <p>Constructor for ExperimentLoader.</p>
     *
     * @param experimentId a {@link java.lang.String} object
     * @param version a {@link java.lang.Integer} object
     */
    public ExperimentLoader(String experimentId, Integer version) {
        this.experiment = new ExperimentToLoad(experimentId, version);
    }

    /**
     * <p>Constructor for ExperimentLoader.</p>
     *
     * @param toLoad a {@link com.phylodata.loader.ExperimentToLoad} object
     */
    public ExperimentLoader(ExperimentToLoad toLoad) {
        this.experiment = toLoad;
    }


    /**
     * {@inheritDoc}
     *
     * Executes the download and loading flow using the configured options
     * and returns the assembled experiment metadata.
     */
    @Override
    public PaperWithExperiment load() {
        return new ExperimentsLoader(experiment)
                .intoDirectory(directory)
                .restrictFileNames(filesToDownload)
                .restrictFileTypes(fileTypesToDownload)
                .preferPreview(downloadOnlyPreview)
                .forceDownload(forceDownload)
                .citationsInto(citationsFile)
                .load().get(0);
    }
}
