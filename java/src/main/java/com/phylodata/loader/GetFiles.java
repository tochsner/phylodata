package com.phylodata.loader;

import com.phylodata.config.PhyloDataConfig;
import com.phylodata.types.File;
import com.phylodata.types.PaperWithExperiment;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utility methods to retrieve local files of an experiment.
 */
public final class GetFiles {

    private GetFiles() {}

    /**
     * Returns the folder containing the local experiment files.
     *
     * @param experiment PaperWithExperiment holding files and metadata
     * @return Path of the directory where files are stored
     */
    public static Path getFolder(PaperWithExperiment experiment) {
        for (File f : experiment.getFiles()) {
            if (f.getLocalPath() != null) {
                return f.getLocalPath().getParent();
            }
        }
        throw new AssertionError("Folder could not be found. Did you download the experiment?");
    }

    /**
     * Retrieves all downloaded files of the experiment. For each logical file, either the
     * full or preview variant is returned.
     *
     * - preferPreview == null: prefer full, fall back to preview
     * - preferPreview == true: prefer preview, fall back to full
     * - preferPreview == false: only return full files
     *
     * preferPreview can also be controlled via PHYLODATA_PREFER_PREVIEW and {@link PhyloDataConfig}.
     *
     * @param experiment PaperWithExperiment holding files and metadata
     * @param preferPreview Nullable preference override; see rules above
     * @return List of selected File variants, at most one per logical file
     */
    public static List<File> getFiles(PaperWithExperiment experiment, Boolean preferPreview) {
        Boolean effectivePrefer = preferPreview;
        if (effectivePrefer == null) {
            effectivePrefer = PhyloDataConfig.isPreviewPreferred();
        }

        List<File> downloaded = experiment.getFiles().stream()
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

    public static List<File> getFiles(PaperWithExperiment experiment) {
        return getFiles(experiment, null);
    }

    /**
     * Retrieves all downloaded files of the experiment of a specific type.
     * For each logical file, either the full or preview variant is returned.
     *
     * - preferPreview == null: prefer full, fall back to preview
     * - preferPreview == true: prefer preview, fall back to full
     * - preferPreview == false: only return full files
     *
     * preferPreview can also be controlled via PHYLODATA_PREFER_PREVIEW and {@link PhyloDataConfig}.
     *
     * @param experiment PaperWithExperiment holding files and metadata
     * @param type File type to filter
     * @param preferPreview Nullable preference override
     * @return List of files matching the type after variant selection
     */
    public static List<File> getFilesOfType(PaperWithExperiment experiment, File.FileType type, Boolean preferPreview) {
        return getFiles(experiment, preferPreview).stream()
                .filter(f -> f.getType() == type)
                .collect(Collectors.toList());
    }

    public static List<File> getFilesOfType(PaperWithExperiment experiment, File.FileType type) {
        return getFilesOfType(experiment, type, null);
    }

    /**
     * Retrieves a file by its name (preview suffix ignored). Depending on your settings
     * and preferPreview, either the full or preview variant is returned.
     *
     * @param experiment PaperWithExperiment holding files and metadata
     * @param name Logical file name to retrieve (without preview suffix)
     * @param preferPreview Nullable preference override
     * @return The first matching File or null if none exists
     */
    public static File getFile(PaperWithExperiment experiment, String name, Boolean preferPreview) {
        List<File> matches = getFiles(experiment, preferPreview).stream()
                .filter(f -> Objects.equals(stripPreviewSuffix(f), name))
                .collect(Collectors.toList());
        return matches.isEmpty() ? null : matches.get(0);
    }

    public static File getFile(PaperWithExperiment experiment, String name) {
        return getFile(experiment, name, null);
    }

    /**
     * Retrieves the first file of a specified type after applying variant selection. Depending on your settings
     * and preferPreview, either the full or preview variant is returned.
     *
     * @param experiment PaperWithExperiment holding files and metadata
     * @param type File type to search for
     * @param preferPreview Nullable preference override
     * @return The first File of the given type or null if none exists
     */
    public static File getFileOfType(PaperWithExperiment experiment, File.FileType type, Boolean preferPreview) {
        List<File> matches = getFilesOfType(experiment, type, preferPreview);
        return matches.isEmpty() ? null : matches.get(0);
    }

    /**
     * Retrieves the first file of a specified type after applying variant selection.
     * Either the full or preview variant is returned.
     *
     * @param experiment PaperWithExperiment holding files and metadata
     * @param type File type to search for
     * @return The first File of the given type or null if none exists
     */
    public static File getFileOfType(PaperWithExperiment experiment, File.FileType type) {
        return getFileOfType(experiment, type, null);
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


