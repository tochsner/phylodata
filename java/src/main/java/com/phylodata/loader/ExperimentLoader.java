package com.phylodata.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phylodata.config.PhyloDataConfig;
import com.phylodata.types.EditablePaperWithExperiment;
import com.phylodata.types.NonEditablePaperWithExperiment;
import com.phylodata.types.PaperWithExperiment;
import com.phylodata.types.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class ExperimentLoader extends ExperimentLoaderBuilder<PaperWithExperiment> {

    private static final String EDITABLE_METADATA_FILE = "editable_phylodata_metadata.json";
    private static final String NON_EDITABLE_METADATA_FILE = "non_editable_phylodata_metadata";

    private final String experimentId;
    private final Integer version; // null means latest


    public ExperimentLoader(String experimentId, Integer version) {
        this.experimentId = experimentId;
        this.version = version;
    }

    public ExperimentLoader(ExperimentToLoad toLoad) {
        this.experimentId = toLoad.id;
        this.version = toLoad.version;
    }


    /**
     * Executes the download and loading flow using the configured options
     * and returns the assembled experiment metadata.
     *
     * @return loaded PaperWithExperiment instance
     * @throws RuntimeException if any IO error occurs while downloading or reading files
     */
    @Override
    public PaperWithExperiment load() {
        try {
            Path baseDir = (directory == null ? Path.of("data") : directory).resolve(experimentId);
            Files.createDirectories(baseDir);

            Boolean preferPreview;
            if (downloadOnlyPreview == null) {
                preferPreview = PhyloDataConfig.isPreviewPreferred();
            } else {
                preferPreview = downloadOnlyPreview;
            }

            // Download and load metadata files
            Path editableMetadataPath = FileDownloader.downloadFile(
                    baseDir, experimentId, EDITABLE_METADATA_FILE, version, forceDownload
            );
            Path nonEditableMetadataPath = FileDownloader.downloadFile(
                    baseDir, experimentId, NON_EDITABLE_METADATA_FILE, version, forceDownload
            );

            ObjectMapper mapper = new ObjectMapper();
            EditablePaperWithExperiment editable = mapper.readValue(editableMetadataPath.toFile(), EditablePaperWithExperiment.class);
            NonEditablePaperWithExperiment nonEditable = mapper.readValue(nonEditableMetadataPath.toFile(), NonEditablePaperWithExperiment.class);

            PaperWithExperiment metadata = PaperWithExperiment.fromPartial(editable, nonEditable, baseDir);

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
                        && !f.getIsPreview() ) {
                    continue;
                }

                Path downloaded = FileDownloader.downloadFile(
                        baseDir, experimentId, f.getName(), version, forceDownload
                );
                f.setLocalPath(downloaded);
            }

            return metadata;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load experiment: " + e.getMessage(), e);
        }

    }
}