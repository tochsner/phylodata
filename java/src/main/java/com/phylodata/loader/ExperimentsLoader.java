package com.phylodata.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phylodata.config.PhyloDataConfig;
import com.phylodata.types.EditablePaperWithExperiment;
import com.phylodata.types.File;
import com.phylodata.types.NonEditablePaperWithExperiment;
import com.phylodata.types.PaperWithExperiment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>ExperimentsLoader class.</p>
 *
 * @author tobiaochsner
 */
public class ExperimentsLoader extends ExperimentLoaderBuilder<List<PaperWithExperiment>> {

    private static final String EDITABLE_METADATA_FILE = "editable_phylodata_metadata.json";
    private static final String NON_EDITABLE_METADATA_FILE = "non_editable_phylodata_metadata";

    private final ExperimentToLoad[] experimentsToLoad;

    /**
     * <p>Constructor for ExperimentsLoader.</p>
     *
     * @param toLoad a {@link com.phylodata.loader.ExperimentToLoad} object
     */
    public ExperimentsLoader(ExperimentToLoad... toLoad) {
        this.experimentsToLoad = toLoad;
    }

    /**
     * Executes the download and loading flow using the configured options
     * and returns the assembled experiments metadata.
     *
     * @return loaded PaperWithExperiment instance
     * @throws java.lang.RuntimeException if any IO error occurs while downloading or reading files
     */
    public List<PaperWithExperiment> load() {
        List<PaperWithExperiment> loadedExperiments = new ArrayList<>();
        Path baseDir = (directory == null ? Path.of("data") : directory);

        for (ExperimentToLoad exp : experimentsToLoad) {

            try {
                Path experimentDir = baseDir.resolve(exp.id);
                Files.createDirectories(experimentDir);

                Boolean preferPreview;
                if (downloadOnlyPreview == null) {
                    preferPreview = PhyloDataConfig.isPreviewPreferred();
                } else {
                    preferPreview = downloadOnlyPreview;
                }

                // Download and load metadata files
                Path editableMetadataPath = FileDownloader.downloadFile(
                        experimentDir, exp.id, EDITABLE_METADATA_FILE, exp.version, forceDownload
                );
                Path nonEditableMetadataPath = FileDownloader.downloadFile(
                        experimentDir, exp.id, NON_EDITABLE_METADATA_FILE, exp.version, forceDownload
                );

                ObjectMapper mapper = new ObjectMapper();
                EditablePaperWithExperiment editable = mapper.readValue(editableMetadataPath.toFile(), EditablePaperWithExperiment.class);
                NonEditablePaperWithExperiment nonEditable = mapper.readValue(nonEditableMetadataPath.toFile(), NonEditablePaperWithExperiment.class);

                PaperWithExperiment metadata = PaperWithExperiment.fromPartial(editable, nonEditable, experimentDir);

                // Download remaining files if needed

                Set<String> filesToDownloadSet = filesToDownload != null ? Set.of(filesToDownload) : null;
                Set<File.FileType> fileTypesToDownloadSet = fileTypesToDownload != null ? Set.of(fileTypesToDownload) : null;
                for (File f : nonEditable.getFiles()) {
                    if (filesToDownloadSet != null && !filesToDownloadSet.contains(f.getName())) {
                        continue;
                    }
                    if (fileTypesToDownloadSet != null && !fileTypesToDownloadSet.contains(f.getType())) {
                        continue;
                    }

                    if (f.getType() == File.FileType.PHYLO_DATA_EXPERIMENT) {
                        continue;
                    }

                    if (preferPreview != null && preferPreview
                            && (f.getType() == File.FileType.BEAST_2_POSTERIOR_LOGS
                            || f.getType() == File.FileType.POSTERIOR_TREES)
                            && !f.getIsPreview()) {
                        continue;
                    }

                    Path downloaded = FileDownloader.downloadFile(
                            experimentDir, exp.id, f.getName(), exp.version, forceDownload
                    );
                    f.setLocalPath(downloaded);
                }

                loadedExperiments.add(metadata);

            } catch (IOException e) {
                throw new RuntimeException("Failed to load experiment: " + e.getMessage(), e);
            }
        }

        if (citationsFile != null) {
            Citations.storeCitations(loadedExperiments, citationsFile);
        } else {
            Citations.storeCitations(loadedExperiments, baseDir.resolve("citations.bib"));
        }

        return loadedExperiments;
    }
}
