package com.phylodata.loader;

import com.phylodata.types.File;

import java.nio.file.Path;

/**
 * Builder-style loader for PhyloData experiments.
 *
 * @author tobiaochsner
 */
abstract public class ExperimentLoaderBuilder<T> {

    Path directory;
    Boolean downloadOnlyPreview;
    String[] filesToDownload;
    File.FileType[] fileTypesToDownload;
    boolean forceDownload;

    /**
     * Sets the base directory where the experiment files will be stored.
     * If not set, defaults to "data/experimentId".
     *
     * @param directory Path to the directory
     * @return this builder for chaining
     */
    public ExperimentLoaderBuilder<T> intoDirectory(Path directory) {
        this.directory = directory;
        return this;
    }

    /**
     * Prefers to download preview files.
     *
     * @return this builder for chaining
     */
    public ExperimentLoaderBuilder<T> preferPreview() {
        return this.preferPreview(true);
    }

    /**
     * Prefers to download preview files.
     *
     * @return this builder for chaining
     */
    public ExperimentLoaderBuilder<T> preferFull() {
        return this.preferPreview(false);
    }

    /**
     * Controls whether only preview files should be downloaded.
     * If null, full files are preferred.
     *
     * @param downloadOnlyPreview true to prefer preview files, false to prefer full datasets, null to use default
     * @return this builder for chaining
     */
    public ExperimentLoaderBuilder<T> preferPreview(Boolean downloadOnlyPreview) {
        this.downloadOnlyPreview = downloadOnlyPreview;
        return this;
    }

    /**
     * Restricts downloads to a specific set of filenames.
     * If null or empty, no restriction by filename is applied.
     *
     * @param filesToDownload set of filenames to download
     * @return this builder for chaining
     */
    public ExperimentLoaderBuilder<T> restrictFileNames(String... filesToDownload) {
        this.filesToDownload = filesToDownload;
        return this;
    }

    /**
     * Restricts downloads to a specific set of file types.
     * If null or empty, no restriction by type is applied.
     *
     * @param fileTypesToDownload set of file types to download
     * @return this builder for chaining
     */
    public ExperimentLoaderBuilder<T> restrictFileTypes(File.FileType... fileTypesToDownload) {
        this.fileTypesToDownload = fileTypesToDownload;
        return this;
    }

    /**
     * Forces re-downloading of files even if they already exist locally.
     *
     * @param forceDownload true to always re-download, false to reuse existing files
     * @return this builder for chaining
     */
    public ExperimentLoaderBuilder<T> forceDownload(boolean forceDownload) {
        this.forceDownload = forceDownload;
        return this;
    }

    /**
     * Forces re-downloading of files even if they already exist locally.
     *
     * @return this builder for chaining
     */
    public ExperimentLoaderBuilder<T> forceDownload() {
        return this.forceDownload(true);
    }

    /**
     * <p>load.</p>
     *
     * @return a T object
     */
    public abstract T load();

}
