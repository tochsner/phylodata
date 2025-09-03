
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
 * EditableExperiment
 * <p>
 *
 * @author tobiaochsner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "title",
    "description"
})
@Generated("jsonschema2pojo")
public class EditableExperiment {

    /**
     * ExperimentType
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    private EditableExperiment.ExperimentType type;
    @JsonProperty("title")
    private Object title = null;
    @JsonProperty("description")
    private Object description = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * ExperimentType
     * <p>
     *
     * (Required)
     *
     * @return a {@link com.phylodata.types.EditableExperiment.ExperimentType} object
     */
    @JsonProperty("type")
    public EditableExperiment.ExperimentType getType() {
        return type;
    }

    /**
     * ExperimentType
     * <p>
     *
     * (Required)
     *
     * @param type a {@link com.phylodata.types.EditableExperiment.ExperimentType} object
     */
    @JsonProperty("type")
    public void setType(EditableExperiment.ExperimentType type) {
        this.type = type;
    }

    /**
     * <p>Getter for the field <code>title</code>.</p>
     *
     * @return a {@link java.lang.Object} object
     */
    @JsonProperty("title")
    public Object getTitle() {
        return title;
    }

    /**
     * <p>Setter for the field <code>title</code>.</p>
     *
     * @param title a {@link java.lang.Object} object
     */
    @JsonProperty("title")
    public void setTitle(Object title) {
        this.title = title;
    }

    /**
     * <p>Getter for the field <code>description</code>.</p>
     *
     * @return a {@link java.lang.Object} object
     */
    @JsonProperty("description")
    public Object getDescription() {
        return description;
    }

    /**
     * <p>Setter for the field <code>description</code>.</p>
     *
     * @param description a {@link java.lang.Object} object
     */
    @JsonProperty("description")
    public void setDescription(Object description) {
        this.description = description;
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
        sb.append(EditableExperiment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
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
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.title == null)? 0 :this.title.hashCode()));
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EditableExperiment) == false) {
            return false;
        }
        EditableExperiment rhs = ((EditableExperiment) other);
        return (((((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.title == rhs.title)||((this.title!= null)&&this.title.equals(rhs.title))));
    }


    /**
     * ExperimentType
     * <p>
     * 
     * 
     */
    @Generated("jsonschema2pojo")
    public enum ExperimentType {

        BEAST_2_EXPERIMENT("beast2Experiment");
        private final String value;
        private final static Map<String, EditableExperiment.ExperimentType> CONSTANTS = new HashMap<String, EditableExperiment.ExperimentType>();

        static {
            for (EditableExperiment.ExperimentType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ExperimentType(String value) {
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
        public static EditableExperiment.ExperimentType fromValue(String value) {
            EditableExperiment.ExperimentType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
