
package com.phylodata.types;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * NonEditableExperiment
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "humanReadableId",
    "origin",
    "uploadDate",
    "version",
    "license",
    "id"
})
@Generated("jsonschema2pojo")
public class NonEditableExperiment {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("humanReadableId")
    private String humanReadableId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("origin")
    private String origin;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("uploadDate")
    private String uploadDate;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("version")
    private Integer version;
    @JsonProperty("license")
    private String license = "CC0";
    @JsonProperty("id")
    private Object id = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("humanReadableId")
    public String getHumanReadableId() {
        return humanReadableId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("humanReadableId")
    public void setHumanReadableId(String humanReadableId) {
        this.humanReadableId = humanReadableId;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("origin")
    public String getOrigin() {
        return origin;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("origin")
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("uploadDate")
    public String getUploadDate() {
        return uploadDate;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("uploadDate")
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("version")
    public Integer getVersion() {
        return version;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("version")
    public void setVersion(Integer version) {
        this.version = version;
    }

    @JsonProperty("license")
    public String getLicense() {
        return license;
    }

    @JsonProperty("license")
    public void setLicense(String license) {
        this.license = license;
    }

    @JsonProperty("id")
    public Object getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Object id) {
        this.id = id;
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
        sb.append(NonEditableExperiment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("humanReadableId");
        sb.append('=');
        sb.append(((this.humanReadableId == null)?"<null>":this.humanReadableId));
        sb.append(',');
        sb.append("origin");
        sb.append('=');
        sb.append(((this.origin == null)?"<null>":this.origin));
        sb.append(',');
        sb.append("uploadDate");
        sb.append('=');
        sb.append(((this.uploadDate == null)?"<null>":this.uploadDate));
        sb.append(',');
        sb.append("version");
        sb.append('=');
        sb.append(((this.version == null)?"<null>":this.version));
        sb.append(',');
        sb.append("license");
        sb.append('=');
        sb.append(((this.license == null)?"<null>":this.license));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
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
        result = ((result* 31)+((this.license == null)? 0 :this.license.hashCode()));
        result = ((result* 31)+((this.uploadDate == null)? 0 :this.uploadDate.hashCode()));
        result = ((result* 31)+((this.origin == null)? 0 :this.origin.hashCode()));
        result = ((result* 31)+((this.humanReadableId == null)? 0 :this.humanReadableId.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.version == null)? 0 :this.version.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NonEditableExperiment) == false) {
            return false;
        }
        NonEditableExperiment rhs = ((NonEditableExperiment) other);
        return ((((((((this.license == rhs.license)||((this.license!= null)&&this.license.equals(rhs.license)))&&((this.uploadDate == rhs.uploadDate)||((this.uploadDate!= null)&&this.uploadDate.equals(rhs.uploadDate))))&&((this.origin == rhs.origin)||((this.origin!= null)&&this.origin.equals(rhs.origin))))&&((this.humanReadableId == rhs.humanReadableId)||((this.humanReadableId!= null)&&this.humanReadableId.equals(rhs.humanReadableId))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))));
    }

}
