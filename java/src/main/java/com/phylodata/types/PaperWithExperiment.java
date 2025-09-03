package com.phylodata.types;

import java.util.ArrayList;
import java.util.List;

public class PaperWithExperiment {

    private Paper paper;
    private Experiment experiment;
    private List<File> files = new ArrayList<File>();
    private List<Sample> samples = new ArrayList<Sample>();
    private Object trees;
    private List<EvolutionaryModelComponent> evolutionaryModel = new ArrayList<EvolutionaryModelComponent>();
    private Metadata metadata;

    public static PaperWithExperiment fromPartial(EditablePaperWithExperiment editable, NonEditablePaperWithExperiment nonEditable) {
        PaperWithExperiment p = new PaperWithExperiment();
        p.setPaper(Paper.fromPartial(editable.getPaper(), nonEditable.getPaper()));
        p.setExperiment(Experiment.fromPartial(editable.getExperiment(), nonEditable.getExperiment()));
        p.setFiles(nonEditable.getFiles());
        p.setSamples(editable.getSamples());
        p.setTrees(nonEditable.getTrees());
        p.setEvolutionaryModel(nonEditable.getEvolutionaryModel());
        p.setMetadata(nonEditable.getMetadata());
        return p;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public Object getTrees() {
        return trees;
    }

    public void setTrees(Object trees) {
        this.trees = trees;
    }

    public List<EvolutionaryModelComponent> getEvolutionaryModel() {
        return evolutionaryModel;
    }

    public void setEvolutionaryModel(List<EvolutionaryModelComponent> evolutionaryModel) {
        this.evolutionaryModel = evolutionaryModel;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}


