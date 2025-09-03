package com.phylodata.loader;

/**
 * <p>ExperimentToLoad class.</p>
 *
 * @author tobiaochsner
 */
public class ExperimentToLoad {
    public final String id;
    public final Integer version; // null means latest

    /**
     * <p>Constructor for ExperimentToLoad.</p>
     *
     * @param id a {@link java.lang.String} object
     */
    public ExperimentToLoad(String id) {
        this(id, null);
    }

    /**
     * <p>Constructor for ExperimentToLoad.</p>
     *
     * @param id a {@link java.lang.String} object
     * @param version a {@link java.lang.Integer} object
     */
    public ExperimentToLoad(String id, Integer version) {
        this.id = id;
        this.version = version;
    }
}
