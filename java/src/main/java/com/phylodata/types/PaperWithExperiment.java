package com.phylodata.types;

import com.phylodata.config.PhyloDataConfig;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>PaperWithExperiment class.</p>
 *
 * @author tobiaochsner
 */
public class PaperWithExperiment {

    private Paper paper;
    private Experiment experiment;
    private List<File> files = new ArrayList<File>();
    private List<Sample> samples = new ArrayList<Sample>();
    private Trees trees;
    private List<EvolutionaryModelComponent> evolutionaryModel = new ArrayList<EvolutionaryModelComponent>();
    private Metadata metadata;
    private Path localFolder;

    /**
     * <p>fromPartial.</p>
     *
     * @param editable a {@link com.phylodata.types.EditablePaperWithExperiment} object
     * @param nonEditable a {@link com.phylodata.types.NonEditablePaperWithExperiment} object
     * @param localFolder a {@link java.nio.file.Path} object
     * @return a {@link com.phylodata.types.PaperWithExperiment} object
     */
    public static PaperWithExperiment fromPartial(
            EditablePaperWithExperiment editable, NonEditablePaperWithExperiment nonEditable, Path localFolder
    ) {
        PaperWithExperiment p = new PaperWithExperiment();
        p.setPaper(Paper.fromPartial(editable.getPaper(), nonEditable.getPaper()));
        p.setExperiment(Experiment.fromPartial(editable.getExperiment(), nonEditable.getExperiment()));
        p.setFiles(nonEditable.getFiles());
        p.setSamples(editable.getSamples());
        p.setTrees(nonEditable.getTrees());
        p.setEvolutionaryModel(nonEditable.getEvolutionaryModel());
        p.setMetadata(nonEditable.getMetadata());
        p.localFolder = localFolder;
        return p;
    }

    /**
     * <p>Getter for the field <code>paper</code>.</p>
     *
     * @return a {@link com.phylodata.types.Paper} object
     */
    public Paper getPaper() {
        return paper;
    }

    /**
     * <p>Setter for the field <code>paper</code>.</p>
     *
     * @param paper a {@link com.phylodata.types.Paper} object
     */
    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    /**
     * <p>Getter for the field <code>experiment</code>.</p>
     *
     * @return a {@link com.phylodata.types.Experiment} object
     */
    public Experiment getExperiment() {
        return experiment;
    }

    /**
     * <p>Setter for the field <code>experiment</code>.</p>
     *
     * @param experiment a {@link com.phylodata.types.Experiment} object
     */
    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    /**
     * <p>getAllFiles.</p>
     *
     * @return a {@link java.util.List} object
     */
    public List<File> getAllFiles() {
        return files;
    }

    /**
     * <p>Setter for the field <code>files</code>.</p>
     *
     * @param files a {@link java.util.List} object
     */
    public void setFiles(List<File> files) {
        this.files = files;
    }

    /**
     * <p>Getter for the field <code>samples</code>.</p>
     *
     * @return a {@link java.util.List} object
     */
    public List<Sample> getSamples() {
        return samples;
    }

    /**
     * <p>Setter for the field <code>samples</code>.</p>
     *
     * @param samples a {@link java.util.List} object
     */
    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    /**
     * <p>Getter for the field <code>trees</code>.</p>
     *
     * @return a {@link com.phylodata.types.Trees} object
     */
    public Trees getTrees() {
        return trees;
    }

    /**
     * <p>Setter for the field <code>trees</code>.</p>
     *
     * @param trees a {@link com.phylodata.types.Trees} object
     */
    public void setTrees(Trees trees) {
        this.trees = trees;
    }

    /**
     * <p>Getter for the field <code>evolutionaryModel</code>.</p>
     *
     * @return a {@link java.util.List} object
     */
    public List<EvolutionaryModelComponent> getEvolutionaryModel() {
        return evolutionaryModel;
    }

    /**
     * <p>Setter for the field <code>evolutionaryModel</code>.</p>
     *
     * @param evolutionaryModel a {@link java.util.List} object
     */
    public void setEvolutionaryModel(List<EvolutionaryModelComponent> evolutionaryModel) {
        this.evolutionaryModel = evolutionaryModel;
    }

    /**
     * <p>Getter for the field <code>metadata</code>.</p>
     *
     * @return a {@link com.phylodata.types.Metadata} object
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * <p>Setter for the field <code>metadata</code>.</p>
     *
     * @param metadata a {@link com.phylodata.types.Metadata} object
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }


    /**
     * Returns the folder containing the local experiment files.
     *
     * @return Path of the directory where files are stored
     */
    public Path getLocalFolder() {
        return this.localFolder;
    }

    /* ----- getFiles methods -----
     * The following methods allows to access downloaded files while adhering to the
     * set preview preference.
     *
     * The getAllFiles method above simply returns all files of the experiment,
     * regardless if they have been downloaded or not.
     * */

    /**
     * Retrieves a file by its name (preview suffix ignored). Depending on your settings
     * and preferPreview, either the full or preview variant is returned.
     *
     * @param name Logical file name to retrieve (without preview suffix)
     * @param preferPreview Nullable preference override
     * @return The first matching File or null if none exists
     * @throws java.io.FileNotFoundException if any.
     */
    public File getFile(String name, Boolean preferPreview) throws FileNotFoundException {
        List<File> matches = getFiles(preferPreview).stream()
                .filter(f -> Objects.equals(stripPreviewSuffix(f), name))
                .collect(Collectors.toList());
        if (matches.isEmpty()) {
            throw new FileNotFoundException();
        } else {
            return matches.get(0);
        }
    }

    /**
     * Retrieves a file by its name (preview suffix ignored). Depending on your settings,
     * either the full or preview variant is returned.
     *
     * @param name Logical file name to retrieve (without preview suffix)
     * @return The first matching File or null if none exists
     * @throws java.io.FileNotFoundException if any.
     */
    public File getFile(String name) throws FileNotFoundException {
        return getFile(name, null);
    }

    /**
     * Retrieves all downloaded files of the experiment. For each logical file, either the
     * full or preview variant is returned.
     *
     * - preferPreview == null: prefer full, fall back to preview
     * - preferPreview == true: prefer preview, fall back to full
     * - preferPreview == false: only return full files
     *
     * preferPreview can also be controlled via PHYLODATA_PREFER_PREVIEW and {@link com.phylodata.config.PhyloDataConfig}.
     *
     * @param preferPreview Nullable preference override; see rules above
     * @return List of selected File variants, at most one per logical file
     */
    public List<File> getFiles(Boolean preferPreview) {
        Boolean effectivePrefer = preferPreview;
        if (effectivePrefer == null) {
            effectivePrefer = PhyloDataConfig.isPreviewPreferred();
        }

        List<File> downloaded = this.getAllFiles().stream()
                .filter(f -> f.getLocalPath() != null)
                .toList();

        Map<String, List<File>> variantsByBaseName = new HashMap<>();
        for (File f : downloaded) {
            String baseName = stripPreviewSuffix(f);
            variantsByBaseName.computeIfAbsent(baseName, k -> new ArrayList<>()).add(f);
        }

        List<File> result = new ArrayList<>();
        for (List<File> variants : variantsByBaseName.values()) {
            List<File> preview = variants.stream().filter(v -> Boolean.TRUE.equals(v.getIsPreview())).toList();
            List<File> full = variants.stream().filter(v -> !Boolean.TRUE.equals(v.getIsPreview())).toList();

            if (effectivePrefer == null) {
                // Prefer full, fall back to preview
                if (!full.isEmpty()) result.addAll(full);
                else if (!preview.isEmpty()) result.addAll(preview);
            } else if (effectivePrefer) {
                // Prefer preview, fall back to full
                if (!preview.isEmpty()) result.addAll(preview);
                else if (!full.isEmpty()) result.addAll(full);
            } else {
                // Only full
                if (!full.isEmpty()) result.addAll(full);
            }
        }
        return result;
    }

    /**
     * Retrieves the first file of a specified type. Depending on your settings
     * and preferPreview, either the full or preview variant is returned.
     *
     * @param type File type to search for
     * @param preferPreview Nullable preference override
     * @return The first File of the given type or null if none exists
     * @throws java.io.FileNotFoundException if any.
     */
    public File getFileOfType(File.FileType type, Boolean preferPreview) throws FileNotFoundException {
        List<File> matches = getFilesOfType(type, preferPreview);
        if (matches.isEmpty()) {
            throw new FileNotFoundException();
        } else {
            return matches.get(0);
        }
    }

    /**
     * Retrieves the first file of a specified type.
     * Depending on your settings, either the full or preview variant is returned.
     *
     * @param type File type to search for
     * @return The first File of the given type or null if none exists
     * @throws java.io.FileNotFoundException if any.
     */
    public File getFileOfType(File.FileType type) throws FileNotFoundException {
        return getFileOfType(type, null);
    }

    /**
     * Retrieves all downloaded files of the experiment. For each logical file, either the
     * full or preview variant is returned.
     *
     * - preferPreview == null: prefer full, fall back to preview
     * - preferPreview == true: prefer preview, fall back to full
     * - preferPreview == false: only return full files
     *
     * preferPreview can also be controlled via PHYLODATA_PREFER_PREVIEW and {@link com.phylodata.config.PhyloDataConfig}.
     *
     * @return List of selected File variants, at most one per logical file
     */
    public List<File> getFiles() {
        return this.getFiles(null);
    }

    /**
     * Retrieves all downloaded files of the experiment of a specific type.
     * For each logical file, either the full or preview variant is returned.
     *
     * - preferPreview == null: prefer full, fall back to preview
     * - preferPreview == true: prefer preview, fall back to full
     * - preferPreview == false: only return full files
     *
     * preferPreview can also be controlled via PHYLODATA_PREFER_PREVIEW and {@link com.phylodata.config.PhyloDataConfig}.
     *
     * @param type File type to filter
     * @param preferPreview Nullable preference override
     * @return List of files matching the type after variant selection
     */
    public List<File> getFilesOfType(File.FileType type, Boolean preferPreview) {
        return getFiles(preferPreview).stream()
                .filter(f -> f.getType() == type)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves all downloaded files of the experiment of a specific type.
     * For each logical file, either the full or preview variant is returned.
     *
     * - preferPreview == null: prefer full, fall back to preview
     * - preferPreview == true: prefer preview, fall back to full
     * - preferPreview == false: only return full files
     *
     * preferPreview can also be controlled via PHYLODATA_PREFER_PREVIEW and {@link com.phylodata.config.PhyloDataConfig}.
     *
     * @param type File type to filter
     * @return List of files matching the type after variant selection
     */
    public List<File> getFilesOfType(File.FileType type) {
        return getFilesOfType(type, null);
    }

    /**
     * Returns the logical name by removing the " (preview)" suffix if present.
     * For example, "beast2 (preview).xml" -> "beast2.xml".
     */
    private static String stripPreviewSuffix(File file) {
        String name = file.getName();
        if (name == null) return null;
        int lastDot = name.lastIndexOf('.');
        if (lastDot < 0) return name;
        String extension = name.substring(lastDot + 1);
        String suffix = " (preview)." + extension;
        if (name.endsWith(suffix)) {
            return name.substring(0, name.length() - suffix.length()) + "." + extension;
        }
        return name;
    }
}


