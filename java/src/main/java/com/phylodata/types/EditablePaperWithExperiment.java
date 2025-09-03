
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
 * EditablePaperWithExperiment
 * <p>
 * This structure contains all experiment data computed by the pipeline
 *     and may be changed manually.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "paper",
    "experiment",
    "samples"
})
@Generated("jsonschema2pojo")
public class EditablePaperWithExperiment {

    /**
     * EditablePaper
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("paper")
    private EditablePaper paper;
    /**
     * EditableExperiment
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("experiment")
    private EditableExperiment experiment;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("samples")
    private List<Sample> samples = new ArrayList<Sample>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * EditablePaper
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("paper")
    public EditablePaper getPaper() {
        return paper;
    }

    /**
     * EditablePaper
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("paper")
    public void setPaper(EditablePaper paper) {
        this.paper = paper;
    }

    /**
     * EditableExperiment
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("experiment")
    public EditableExperiment getExperiment() {
        return experiment;
    }

    /**
     * EditableExperiment
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("experiment")
    public void setExperiment(EditableExperiment experiment) {
        this.experiment = experiment;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("samples")
    public List<Sample> getSamples() {
        return samples;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("samples")
    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(EditablePaperWithExperiment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("paper");
        sb.append('=');
        sb.append(((this.paper == null)?"<null>":this.paper));
        sb.append(',');
        sb.append("experiment");
        sb.append('=');
        sb.append(((this.experiment == null)?"<null>":this.experiment));
        sb.append(',');
        sb.append("samples");
        sb.append('=');
        sb.append(((this.samples == null)?"<null>":this.samples));
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

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.paper == null)? 0 :this.paper.hashCode()));
        result = ((result* 31)+((this.experiment == null)? 0 :this.experiment.hashCode()));
        result = ((result* 31)+((this.samples == null)? 0 :this.samples.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EditablePaperWithExperiment) == false) {
            return false;
        }
        EditablePaperWithExperiment rhs = ((EditablePaperWithExperiment) other);
        return (((((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties)))&&((this.paper == rhs.paper)||((this.paper!= null)&&this.paper.equals(rhs.paper))))&&((this.experiment == rhs.experiment)||((this.experiment!= null)&&this.experiment.equals(rhs.experiment))))&&((this.samples == rhs.samples)||((this.samples!= null)&&this.samples.equals(rhs.samples))));
    }

}
