
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
 * ClassificationEntry
 * <p>
 *
 * @author tobiaochsner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "classificationId",
    "scientificName",
    "idType",
    "commonName"
})
@Generated("jsonschema2pojo")
public class ClassificationEntry {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("classificationId")
    private String classificationId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("scientificName")
    private String scientificName;
    /**
     * ClassificationEntryType
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("idType")
    private ClassificationEntry.ClassificationEntryType idType;
    @JsonProperty("commonName")
    private Object commonName = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     *
     * (Required)
     *
     * @return a {@link java.lang.String} object
     */
    @JsonProperty("classificationId")
    public String getClassificationId() {
        return classificationId;
    }

    /**
     *
     * (Required)
     *
     * @param classificationId a {@link java.lang.String} object
     */
    @JsonProperty("classificationId")
    public void setClassificationId(String classificationId) {
        this.classificationId = classificationId;
    }

    /**
     *
     * (Required)
     *
     * @return a {@link java.lang.String} object
     */
    @JsonProperty("scientificName")
    public String getScientificName() {
        return scientificName;
    }

    /**
     *
     * (Required)
     *
     * @param scientificName a {@link java.lang.String} object
     */
    @JsonProperty("scientificName")
    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    /**
     * ClassificationEntryType
     * <p>
     *
     * (Required)
     *
     * @return a {@link com.phylodata.types.ClassificationEntry.ClassificationEntryType} object
     */
    @JsonProperty("idType")
    public ClassificationEntry.ClassificationEntryType getIdType() {
        return idType;
    }

    /**
     * ClassificationEntryType
     * <p>
     *
     * (Required)
     *
     * @param idType a {@link com.phylodata.types.ClassificationEntry.ClassificationEntryType} object
     */
    @JsonProperty("idType")
    public void setIdType(ClassificationEntry.ClassificationEntryType idType) {
        this.idType = idType;
    }

    /**
     * <p>Getter for the field <code>commonName</code>.</p>
     *
     * @return a {@link java.lang.Object} object
     */
    @JsonProperty("commonName")
    public Object getCommonName() {
        return commonName;
    }

    /**
     * <p>Setter for the field <code>commonName</code>.</p>
     *
     * @param commonName a {@link java.lang.Object} object
     */
    @JsonProperty("commonName")
    public void setCommonName(Object commonName) {
        this.commonName = commonName;
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
        sb.append(ClassificationEntry.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("classificationId");
        sb.append('=');
        sb.append(((this.classificationId == null)?"<null>":this.classificationId));
        sb.append(',');
        sb.append("scientificName");
        sb.append('=');
        sb.append(((this.scientificName == null)?"<null>":this.scientificName));
        sb.append(',');
        sb.append("idType");
        sb.append('=');
        sb.append(((this.idType == null)?"<null>":this.idType));
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

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.commonName == null)? 0 :this.commonName.hashCode()));
        result = ((result* 31)+((this.idType == null)? 0 :this.idType.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.classificationId == null)? 0 :this.classificationId.hashCode()));
        result = ((result* 31)+((this.scientificName == null)? 0 :this.scientificName.hashCode()));
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ClassificationEntry) == false) {
            return false;
        }
        ClassificationEntry rhs = ((ClassificationEntry) other);
        return ((((((this.commonName == rhs.commonName)||((this.commonName!= null)&&this.commonName.equals(rhs.commonName)))&&((this.idType == rhs.idType)||((this.idType!= null)&&this.idType.equals(rhs.idType))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.classificationId == rhs.classificationId)||((this.classificationId!= null)&&this.classificationId.equals(rhs.classificationId))))&&((this.scientificName == rhs.scientificName)||((this.scientificName!= null)&&this.scientificName.equals(rhs.scientificName))));
    }


    /**
     * ClassificationEntryType
     * <p>
     * 
     * 
     */
    @Generated("jsonschema2pojo")
    public enum ClassificationEntryType {

        GLOTTOLOG_ID("glottologId"),
        NCBI_TAXONOMY_ID("ncbiTaxonomyId");
        private final String value;
        private final static Map<String, ClassificationEntry.ClassificationEntryType> CONSTANTS = new HashMap<String, ClassificationEntry.ClassificationEntryType>();

        static {
            for (ClassificationEntry.ClassificationEntryType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ClassificationEntryType(String value) {
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
        public static ClassificationEntry.ClassificationEntryType fromValue(String value) {
            ClassificationEntry.ClassificationEntryType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
