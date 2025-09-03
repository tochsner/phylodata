package com.phylodata.loader;

public class ExperimentToLoad {
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