
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
 * Sample
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sampleId",
    "scientificName",
    "classification",
    "sampleData",
    "commonName"
})
@Generated("jsonschema2pojo")
public class Sample {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("sampleId")
    private String sampleId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("scientificName")
    private String scientificName;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("classification")
    private List<ClassificationEntry> classification = new ArrayList<ClassificationEntry>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("sampleData")
    private List<SampleData> sampleData = new ArrayList<SampleData>();
    @JsonProperty("commonName")
    private Object commonName = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("sampleId")
    public String getSampleId() {
        return sampleId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("sampleId")
    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("scientificName")
    public String getScientificName() {
        return scientificName;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("scientificName")
    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("classification")
    public List<ClassificationEntry> getClassification() {
        return classification;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("classification")
    public void setClassification(List<ClassificationEntry> classification) {
        this.classification = classification;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("sampleData")
    public List<SampleData> getSampleData() {
        return sampleData;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("sampleData")
    public void setSampleData(List<SampleData> sampleData) {
        this.sampleData = sampleData;
    }

    @JsonProperty("commonName")
    public Object getCommonName() {
        return commonName;
    }

    @JsonProperty("commonName")
    public void setCommonName(Object commonName) {
        this.commonName = commonName;
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
        sb.append(Sample.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("sampleId");
        sb.append('=');
        sb.append(((this.sampleId == null)?"<null>":this.sampleId));
        sb.append(',');
        sb.append("scientificName");
        sb.append('=');
        sb.append(((this.scientificName == null)?"<null>":this.scientificName));
        sb.append(',');
        sb.append("classification");
        sb.append('=');
        sb.append(((this.classification == null)?"<null>":this.classification));
        sb.append(',');
        sb.append("sampleData");
        sb.append('=');
        sb.append(((this.sampleData == null)?"<null>":this.sampleData));
        sb.append(',');
        sb.append("commonName");
        sb.append('=');
        sb.append(((this.commonName == null)?"<null>":this.commonName));
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
        result = ((result* 31)+((this.commonName == null)? 0 :this.commonName.hashCode()));
        result = ((result* 31)+((this.sampleData == null)? 0 :this.sampleData.hashCode()));
        result = ((result* 31)+((this.sampleId == null)? 0 :this.sampleId.hashCode()));
        result = ((result* 31)+((this.scientificName == null)? 0 :this.scientificName.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.classification == null)? 0 :this.classification.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Sample) == false) {
            return false;
        }
        Sample rhs = ((Sample) other);
        return (((((((this.commonName == rhs.commonName)||((this.commonName!= null)&&this.commonName.equals(rhs.commonName)))&&((this.sampleData == rhs.sampleData)||((this.sampleData!= null)&&this.sampleData.equals(rhs.sampleData))))&&((this.sampleId == rhs.sampleId)||((this.sampleId!= null)&&this.sampleId.equals(rhs.sampleId))))&&((this.scientificName == rhs.scientificName)||((this.scientificName!= null)&&this.scientificName.equals(rhs.scientificName))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.classification == rhs.classification)||((this.classification!= null)&&this.classification.equals(rhs.classification))));
    }

}
