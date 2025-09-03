
package com.phylodata.types;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * SampleData
 * <p>
 *
 * @author tobiaochsner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "length",
    "data"
})
@Generated("jsonschema2pojo")
public class SampleData {

    /**
     * DataType
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    private SampleData.DataType type;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("length")
    private Integer length;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("data")
    private String data;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * DataType
     * <p>
     *
     * (Required)
     *
     * @return a {@link com.phylodata.types.SampleData.DataType} object
     */
    @JsonProperty("type")
    public SampleData.DataType getType() {
        return type;
    }

    /**
     * DataType
     * <p>
     *
     * (Required)
     *
     * @param type a {@link com.phylodata.types.SampleData.DataType} object
     */
    @JsonProperty("type")
    public void setType(SampleData.DataType type) {
        this.type = type;
    }

    /**
     *
     * (Required)
     *
     * @return a {@link java.lang.Integer} object
     */
    @JsonProperty("length")
    public Integer getLength() {
        return length;
    }

    /**
     *
     * (Required)
     *
     * @param length a {@link java.lang.Integer} object
     */
    @JsonProperty("length")
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     *
     * (Required)
     *
     * @return a {@link java.lang.String} object
     */
    @JsonProperty("data")
    public String getData() {
        return data;
    }

    /**
     *
     * (Required)
     *
     * @param data a {@link java.lang.String} object
     */
    @JsonProperty("data")
    public void setData(String data) {
        this.data = data;
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
        sb.append(SampleData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("length");
        sb.append('=');
        sb.append(((this.length == null)?"<null>":this.length));
        sb.append(',');
        sb.append("data");
        sb.append('=');
        sb.append(((this.data == null)?"<null>":this.data));
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
        result = ((result* 31)+((this.length == null)? 0 :this.length.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.data == null)? 0 :this.data.hashCode()));
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SampleData) == false) {
            return false;
        }
        SampleData rhs = ((SampleData) other);
        return (((((this.length == rhs.length)||((this.length!= null)&&this.length.equals(rhs.length)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.data == rhs.data)||((this.data!= null)&&this.data.equals(rhs.data))));
    }


    /**
     * DataType
     * <p>
     * 
     * 
     */
    @Generated("jsonschema2pojo")
    public enum DataType {

        AMINO_ACIDS("aminoAcids"),
        DNA("dna"),
        PHASED_DIPLOID_DNA("phasedDiploidDna"),
        RNA("rna"),
        TRAITS("traits"),
        UNKNOWN("unknown");
        private final String value;
        private final static Map<String, SampleData.DataType> CONSTANTS = new HashMap<String, SampleData.DataType>();

        static {
            for (SampleData.DataType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        DataType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static SampleData.DataType fromValue(String value) {
            SampleData.DataType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
