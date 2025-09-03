
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
 * Metadata
 * <p>
 *
 * @author tobiaochsner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "evoDataPipelineVersion"
})
@Generated("jsonschema2pojo")
public class Metadata {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("evoDataPipelineVersion")
    private String evoDataPipelineVersion;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     *
     * (Required)
     *
     * @return a {@link java.lang.String} object
     */
    @JsonProperty("evoDataPipelineVersion")
    public String getEvoDataPipelineVersion() {
        return evoDataPipelineVersion;
    }

    /**
     *
     * (Required)
     *
     * @param evoDataPipelineVersion a {@link java.lang.String} object
     */
    @JsonProperty("evoDataPipelineVersion")
    public void setEvoDataPipelineVersion(String evoDataPipelineVersion) {
        this.evoDataPipelineVersion = evoDataPipelineVersion;
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
        sb.append(Metadata.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("evoDataPipelineVersion");
        sb.append('=');
        sb.append(((this.evoDataPipelineVersion == null)?"<null>":this.evoDataPipelineVersion));
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
        result = ((result* 31)+((this.evoDataPipelineVersion == null)? 0 :this.evoDataPipelineVersion.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Metadata) == false) {
            return false;
        }
        Metadata rhs = ((Metadata) other);
        return (((this.evoDataPipelineVersion == rhs.evoDataPipelineVersion)||((this.evoDataPipelineVersion!= null)&&this.evoDataPipelineVersion.equals(rhs.evoDataPipelineVersion)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
