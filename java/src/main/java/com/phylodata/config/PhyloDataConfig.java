package com.phylodata.config;

/**
 * Configuration utility for PhyloData package preferences.
 *
 * @author tobiaochsner
 */
public class PhyloDataConfig {

    private static final String PREFER_PREVIEW_ENV = "PHYLODATA_PREFER_PREVIEW";

    /**
     * Sets the PHYLODATA_PREFER_PREVIEW environment variable to true.
     * Note: This only affects the current JVM process and child processes.
     */
    public static void preferPreview() {
        System.setProperty(PREFER_PREVIEW_ENV, "true");
    }

    /**
     * Sets the PHYLODATA_PREFER_PREVIEW environment variable to false.
     * Note: This only affects the current JVM process and child processes.
     */
    public static void preferFull() {
        System.setProperty(PREFER_PREVIEW_ENV, "false");
    }

    /**
     * Gets the current preference setting.
     *
     * @return true if preview is preferred, false if not, null if not specified.
     */
    public static Boolean isPreviewPreferred() {
        String value = System.getProperty(PREFER_PREVIEW_ENV,
                System.getenv(PREFER_PREVIEW_ENV));

        if (value == null) {
            return null;
        } else {
            return "true".equalsIgnoreCase(value);
        }
    }

}
