
package com.phylodata.types;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * NonEditablePaperWithExperiment
 * <p>
 * This structure contains all experiment data which is
 *     computed by the pipeline and must not be changed manually.
 *
 * @author tobiaochsner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "paper",
    "experiment",
    "files",
    "trees",
    "evolutionaryModel",
    "metadata"
})
@Generated("jsonschema2pojo")
public class NonEditablePaperWithExperiment {

    /**
     * NonEditablePaper
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("paper")
    private NonEditablePaper paper;
    /**
     * NonEditableExperiment
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("experiment")
    private NonEditableExperiment experiment;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("files")
    private List<File> files = new ArrayList<File>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("trees")
    private Object trees;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("evolutionaryModel")
    private List<EvolutionaryModelComponent> evolutionaryModel = new ArrayList<EvolutionaryModelComponent>();
    /**
     * Metadata
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * NonEditablePaper
     * <p>
     *
     * (Required)
     *
     * @return a {@link com.phylodata.types.NonEditablePaper} object
     */
    @JsonProperty("paper")
    public NonEditablePaper getPaper() {
        return paper;
    }

    /**
     * NonEditablePaper
     * <p>
     *
     * (Required)
     *
     * @param paper a {@link com.phylodata.types.NonEditablePaper} object
     */
    @JsonProperty("paper")
    public void setPaper(NonEditablePaper paper) {
        this.paper = paper;
    }

    /**
     * NonEditableExperiment
     * <p>
     *
     * (Required)
     *
     * @return a {@link com.phylodata.types.NonEditableExperiment} object
     */
    @JsonProperty("experiment")
    public NonEditableExperiment getExperiment() {
        return experiment;
    }

    /**
     * NonEditableExperiment
     * <p>
     *
     * (Required)
     *
     * @param experiment a {@link com.phylodata.types.NonEditableExperiment} object
     */
    @JsonProperty("experiment")
    public void setExperiment(NonEditableExperiment experiment) {
        this.experiment = experiment;
    }

    /**
     *
     * (Required)
     *
     * @return a {@link java.util.List} object
     */
    @JsonProperty("files")
    public List<File> getFiles() {
        return files;
    }

    /**
     *
     * (Required)
     *
     * @param files a {@link java.util.List} object
     */
    @JsonProperty("files")
    public void setFiles(List<File> files) {
        this.files = files;
    }

    /**
     *
     * (Required)
     *
     * @return a {@link java.lang.Object} object
     */
    @JsonProperty("trees")
    public Object getTrees() {
        return trees;
    }

    /**
     *
     * (Required)
     *
     * @param trees a {@link java.lang.Object} object
     */
    @JsonProperty("trees")
    public void setTrees(Object trees) {
        this.trees = trees;
    }

    /**
     *
     * (Required)
     *
     * @return a {@link java.util.List} object
     */
    @JsonProperty("evolutionaryModel")
    public List<EvolutionaryModelComponent> getEvolutionaryModel() {
        return evolutionaryModel;
    }

    /**
     *
     * (Required)
     *
     * @param evolutionaryModel a {@link java.util.List} object
     */
    @JsonProperty("evolutionaryModel")
    public void setEvolutionaryModel(List<EvolutionaryModelComponent> evolutionaryModel) {
        this.evolutionaryModel = evolutionaryModel;
    }

    /**
     * Metadata
     * <p>
     *
     * (Required)
     *
     * @return a {@link com.phylodata.types.Metadata} object
     */
    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Metadata
     * <p>
     *
     * (Required)
     *
     * @param metadata a {@link com.phylodata.types.Metadata} object
     */
    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    /**
     * <p>Getter for the field <code>additionalProperties</code>.</p>
     *
     * @return a {@link java.util.Map} object
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * <p>setAdditionalProperty.</p>
     *
     * @param name a {@link java.lang.String} object
     * @param value a {@link java.lang.Object} object
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NonEditablePaperWithExperiment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("paper");
        sb.append('=');
        sb.append(((this.paper == null)?"<null>":this.paper));
        sb.append(',');
        sb.append("experiment");
        sb.append('=');
        sb.append(((this.experiment == null)?"<null>":this.experiment));
        sb.append(',');
        sb.append("files");
        sb.append('=');
        sb.append(((this.files == null)?"<null>":this.files));
        sb.append(',');
        sb.append("trees");
        sb.append('=');
        sb.append(((this.trees == null)?"<null>":this.trees));
        sb.append(',');
        sb.append("evolutionaryModel");
        sb.append('=');
        sb.append(((this.evolutionaryModel == null)?"<null>":this.evolutionaryModel));
        sb.append(',');
        sb.append("metadata");
        sb.append('=');
        sb.append(((this.metadata == null)?"<null>":this.metadata));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.metadata == null)? 0 :this.metadata.hashCode()));
        result = ((result* 31)+((this.paper == null)? 0 :this.paper.hashCode()));
        result = ((result* 31)+((this.experiment == null)? 0 :this.experiment.hashCode()));
        result = ((result* 31)+((this.files == null)? 0 :this.files.hashCode()));
        result = ((result* 31)+((this.evolutionaryModel == null)? 0 :this.evolutionaryModel.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.trees == null)? 0 :this.trees.hashCode()));
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NonEditablePaperWithExperiment) == false) {
            return false;
        }
        NonEditablePaperWithExperiment rhs = ((NonEditablePaperWithExperiment) other);
        return ((((((((this.metadata == rhs.metadata)||((this.metadata!= null)&&this.metadata.equals(rhs.metadata)))&&((this.paper == rhs.paper)||((this.paper!= null)&&this.paper.equals(rhs.paper))))&&((this.experiment == rhs.experiment)||((this.experiment!= null)&&this.experiment.equals(rhs.experiment))))&&((this.files == rhs.files)||((this.files!= null)&&this.files.equals(rhs.files))))&&((this.evolutionaryModel == rhs.evolutionaryModel)||((this.evolutionaryModel!= null)&&this.evolutionaryModel.equals(rhs.evolutionaryModel))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.trees == rhs.trees)||((this.trees!= null)&&this.trees.equals(rhs.trees))));
    }

}
