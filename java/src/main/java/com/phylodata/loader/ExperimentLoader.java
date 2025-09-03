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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Utility class for loading PhyloData experiments.
 */
public class ExperimentLoader {

    private static final String EDITABLE_METADATA_FILE = "editable_phylodata_metadata.json";
    private static final String NON_EDITABLE_METADATA_FILE = "non_editable_phylodata_metadata";

    /**
     * Loads a PhyloData experiment.
     *
     * @param experimentId The human-readable ID of the experiment to load (e.g. felsenstein-1992-estimating)
     * @param version The version of the experiment to load. Defaults to latest if null
     * @param directory Path where the experiment files are stored. Uses default if null
     * @param downloadOnlyPreview Whether to only download preview files. This is useful for testing environments.
     *                           This can also be controlled by setting the environment variable
     *                           PHYLODATA_PREFER_PREVIEW to true or false. Defaults to False if null
     * @param filesToDownload Optional set of filenames to download. Downloads all if both filters null/empty
     * @param fileTypesToDownload Optional set of file types to download. Downloads all if both filters null/empty
     * @param forceDownload Whether to re-download files even if they exist locally. Defaults to False
     * @return A PaperWithExperiment object containing the experiment data
     */
    public static PaperWithExperiment loadExperiment(
            String experimentId,
            Integer version,
            Path directory,
            Boolean downloadOnlyPreview,
            Set<String> filesToDownload,
            Set<File.FileType> fileTypesToDownload,
            boolean forceDownload
    ) {
        try {
            Path baseDir = (directory == null ? Path.of("data") : directory).resolve(experimentId);
            Files.createDirectories(baseDir);

            boolean preferPreview;
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

            PaperWithExperiment metadata = PaperWithExperiment.fromPartial(editable, nonEditable);

            // Download remaining files if needed
            for (File f : nonEditable.getFiles()) {
                if (filesToDownload != null && !filesToDownload.contains(f.getName())) {
                    continue;
                }
                if (fileTypesToDownload != null && !fileTypesToDownload.contains(f.getType())) {
                    continue;
                }

                if (f.getType() == File.FileType.PHYLO_DATA_EXPERIMENT) {
                    continue;
                }

                if (preferPreview
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

    /**
     * Loads a PhyloData experiment with version specified.
     *
     * @param experimentId The human-readable ID of the experiment to load
     * @param version The version of the experiment to load
     * @return A PaperWithExperiment object containing the experiment data
     */
    public static PaperWithExperiment loadExperiment(String experimentId, Integer version) {
        return loadExperiment(experimentId, version, null, null, null, null, false);
    }

    /**
     * Loads a PhyloData experiment with version and directory specified.
     *
     * @param experimentId The human-readable ID of the experiment to load
     * @param version The version of the experiment to load
     * @param directory Path where the experiment files are stored
     * @return A PaperWithExperiment object containing the experiment data
     */
    public static PaperWithExperiment loadExperiment(String experimentId, Integer version, Path directory) {
        return loadExperiment(experimentId, version, directory, null, null, null, false);
    }

    /**
     * Helper type representing an experiment to load with optional version.
     * <p>
     * Use this type when you want to specify a particular version of an experiment.
     * If {@code version} is {@code null}, the latest version will be loaded.
     */
    public static class ExperimentToLoad {
        public final String id;
        public final Integer version; // null means latest

        public ExperimentToLoad(String id) {
            this(id, null);
        }

        public ExperimentToLoad(String id, Integer version) {
            this.id = id;
            this.version = version;
        }
    }

    /**
     * Loads multiple PhyloData experiments.
     *
     * @param experimentsToLoad List of experiments to load (IDs with optional versions). If version is null, latest will be used.
     * @param directory Path where the experiment files should be stored. If null, defaults to data/<experiment>.
     * @param downloadOnlyPreview Whether to only download preview files. Useful for testing; can also be controlled by the environment variable PHYLODATA_PREFER_PREVIEW.
     * @param filesToDownload Optional set restricting downloads to specific filenames. If null/empty and {@code fileTypesToDownload} is also null/empty, all files are downloaded.
     * @param fileTypesToDownload Optional set restricting downloads to specific file types. If null/empty and {@code filesToDownload} is also null/empty, all files are downloaded.
     * @param forceDownload Whether to re-download files even if they exist locally.
     * @return A list of PaperWithExperiment objects containing the loaded experiment data.
     */
    public static List<PaperWithExperiment> loadExperiments(
            List<ExperimentToLoad> experimentsToLoad,
            Path directory,
            Boolean downloadOnlyPreview,
            Set<String> filesToDownload,
            Set<File.FileType> fileTypesToDownload,
            boolean forceDownload
    ) {
        List<PaperWithExperiment> experiments = new ArrayList<>();
        for (ExperimentToLoad exp : experimentsToLoad) {
            PaperWithExperiment loaded = loadExperiment(
                    exp.id,
                    exp.version,
                    directory,
                    downloadOnlyPreview,
                    filesToDownload,
                    fileTypesToDownload,
                    forceDownload
            );
            experiments.add(loaded);
        }
        return experiments;
    }

    /**
     * Loads multiple experiments when only IDs (latest versions) are needed, using defaults for other options.
     *
     * @param experimentsToLoad List of experiments to load (IDs with optional versions).
     * @return A list of PaperWithExperiment objects containing the loaded experiment data.
     */
    public static List<PaperWithExperiment> loadExperiments(List<ExperimentToLoad> experimentsToLoad) {
        return loadExperiments(experimentsToLoad, null, null, null, null, false);
    }

    /**
     * Loads multiple experiments with a specified directory and defaults for remaining options.
     *
     * @param experimentsToLoad List of experiments to load (IDs with optional versions).
     * @param directory Path where the experiment files should be stored.
     * @return A list of PaperWithExperiment objects containing the loaded experiment data.
     */
    public static List<PaperWithExperiment> loadExperiments(List<ExperimentToLoad> experimentsToLoad, Path directory) {
        return loadExperiments(experimentsToLoad, directory, null, null, null, false);
    }
}