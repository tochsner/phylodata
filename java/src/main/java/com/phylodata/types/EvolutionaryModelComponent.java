
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
 * EvolutionaryModelComponent
 * <p>
 *
 * @author tobiaochsner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "type",
    "parameters"
})
@Generated("jsonschema2pojo")
public class EvolutionaryModelComponent {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    private String name;
    /**
     * ModelType
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    private EvolutionaryModelComponent.ModelType type;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("parameters")
    private Parameters parameters;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     *
     * (Required)
     *
     * @return a {@link java.lang.String} object
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * (Required)
     *
     * @param name a {@link java.lang.String} object
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * ModelType
     * <p>
     *
     * (Required)
     *
     * @return a {@link com.phylodata.types.EvolutionaryModelComponent.ModelType} object
     */
    @JsonProperty("type")
    public EvolutionaryModelComponent.ModelType getType() {
        return type;
    }

    /**
     * ModelType
     * <p>
     *
     * (Required)
     *
     * @param type a {@link com.phylodata.types.EvolutionaryModelComponent.ModelType} object
     */
    @JsonProperty("type")
    public void setType(EvolutionaryModelComponent.ModelType type) {
        this.type = type;
    }

    /**
     *
     * (Required)
     *
     * @return a {@link com.phylodata.types.Parameters} object
     */
    @JsonProperty("parameters")
    public Parameters getParameters() {
        return parameters;
    }

    /**
     *
     * (Required)
     *
     * @param parameters a {@link com.phylodata.types.Parameters} object
     */
    @JsonProperty("parameters")
    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
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
        sb.append(EvolutionaryModelComponent.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("parameters");
        sb.append('=');
        sb.append(((this.parameters == null)?"<null>":this.parameters));
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
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.parameters == null)? 0 :this.parameters.hashCode()));
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EvolutionaryModelComponent) == false) {
            return false;
        }
        EvolutionaryModelComponent rhs = ((EvolutionaryModelComponent) other);
        return (((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.parameters == rhs.parameters)||((this.parameters!= null)&&this.parameters.equals(rhs.parameters))));
    }


    /**
     * ModelType
     * <p>
     * 
     * 
     */
    @Generated("jsonschema2pojo")
    public enum ModelType {

        CLOCK_MODEL("clockModel"),
        OTHER("other"),
        SUBSTITUTION_MODEL("substitutionModel"),
        TREE_LIKELIHOOD("treeLikelihood"),
        TREE_PRIOR("treePrior");
        private final String value;
        private final static Map<String, EvolutionaryModelComponent.ModelType> CONSTANTS = new HashMap<String, EvolutionaryModelComponent.ModelType>();

        static {
            for (EvolutionaryModelComponent.ModelType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModelType(String value) {
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
        public static EvolutionaryModelComponent.ModelType fromValue(String value) {
            EvolutionaryModelComponent.ModelType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
