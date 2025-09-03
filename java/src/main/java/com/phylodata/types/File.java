
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
 * File
 * <p>
 *
 * @author tobiaochsner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "type",
    "sizeBytes",
    "md5",
    "isPreview",
    "localPath",
    "remotePath"
})
@Generated("jsonschema2pojo")
public class File {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    private String name;
    /**
     * FileType
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    private File.FileType type;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("sizeBytes")
    private Integer sizeBytes;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("md5")
    private String md5;
    @JsonProperty("isPreview")
    private Boolean isPreview = false;
    @JsonProperty("localPath")
    private Object localPath = null;
    @JsonProperty("remotePath")
    private Object remotePath = null;
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
     * FileType
     * <p>
     *
     * (Required)
     *
     * @return a {@link com.phylodata.types.File.FileType} object
     */
    @JsonProperty("type")
    public File.FileType getType() {
        return type;
    }

    /**
     * FileType
     * <p>
     *
     * (Required)
     *
     * @param type a {@link com.phylodata.types.File.FileType} object
     */
    @JsonProperty("type")
    public void setType(File.FileType type) {
        this.type = type;
    }

    /**
     *
     * (Required)
     *
     * @return a {@link java.lang.Integer} object
     */
    @JsonProperty("sizeBytes")
    public Integer getSizeBytes() {
        return sizeBytes;
    }

    /**
     *
     * (Required)
     *
     * @param sizeBytes a {@link java.lang.Integer} object
     */
    @JsonProperty("sizeBytes")
    public void setSizeBytes(Integer sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    /**
     *
     * (Required)
     *
     * @return a {@link java.lang.String} object
     */
    @JsonProperty("md5")
    public String getMd5() {
        return md5;
    }

    /**
     *
     * (Required)
     *
     * @param md5 a {@link java.lang.String} object
     */
    @JsonProperty("md5")
    public void setMd5(String md5) {
        this.md5 = md5;
    }

    /**
     * <p>Getter for the field <code>isPreview</code>.</p>
     *
     * @return a {@link java.lang.Boolean} object
     */
    @JsonProperty("isPreview")
    public Boolean getIsPreview() {
        return isPreview;
    }

    /**
     * <p>Setter for the field <code>isPreview</code>.</p>
     *
     * @param isPreview a {@link java.lang.Boolean} object
     */
    @JsonProperty("isPreview")
    public void setIsPreview(Boolean isPreview) {
        this.isPreview = isPreview;
    }

    /**
     * <p>Getter for the field <code>localPath</code>.</p>
     *
     * @return a {@link java.lang.Object} object
     */
    @JsonProperty("localPath")
    public Object getLocalPath() {
        return localPath;
    }

    /**
     * <p>Setter for the field <code>localPath</code>.</p>
     *
     * @param localPath a {@link java.lang.Object} object
     */
    @JsonProperty("localPath")
    public void setLocalPath(Object localPath) {
        this.localPath = localPath;
    }

    /**
     * <p>Getter for the field <code>remotePath</code>.</p>
     *
     * @return a {@link java.lang.Object} object
     */
    @JsonProperty("remotePath")
    public Object getRemotePath() {
        return remotePath;
    }

    /**
     * <p>Setter for the field <code>remotePath</code>.</p>
     *
     * @param remotePath a {@link java.lang.Object} object
     */
    @JsonProperty("remotePath")
    public void setRemotePath(Object remotePath) {
        this.remotePath = remotePath;
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
        sb.append(File.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("sizeBytes");
        sb.append('=');
        sb.append(((this.sizeBytes == null)?"<null>":this.sizeBytes));
        sb.append(',');
        sb.append("md5");
        sb.append('=');
        sb.append(((this.md5 == null)?"<null>":this.md5));
        sb.append(',');
        sb.append("isPreview");
        sb.append('=');
        sb.append(((this.isPreview == null)?"<null>":this.isPreview));
        sb.append(',');
        sb.append("localPath");
        sb.append('=');
        sb.append(((this.localPath == null)?"<null>":this.localPath));
        sb.append(',');
        sb.append("remotePath");
        sb.append('=');
        sb.append(((this.remotePath == null)?"<null>":this.remotePath));
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
        result = ((result* 31)+((this.isPreview == null)? 0 :this.isPreview.hashCode()));
        result = ((result* 31)+((this.remotePath == null)? 0 :this.remotePath.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.localPath == null)? 0 :this.localPath.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.sizeBytes == null)? 0 :this.sizeBytes.hashCode()));
        result = ((result* 31)+((this.md5 == null)? 0 :this.md5 .hashCode()));
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof File) == false) {
            return false;
        }
        File rhs = ((File) other);
        return (((((((((this.isPreview == rhs.isPreview)||((this.isPreview!= null)&&this.isPreview.equals(rhs.isPreview)))&&((this.remotePath == rhs.remotePath)||((this.remotePath!= null)&&this.remotePath.equals(rhs.remotePath))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.localPath == rhs.localPath)||((this.localPath!= null)&&this.localPath.equals(rhs.localPath))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.sizeBytes == rhs.sizeBytes)||((this.sizeBytes!= null)&&this.sizeBytes.equals(rhs.sizeBytes))))&&((this.md5 == rhs.md5)||((this.md5 != null)&&this.md5 .equals(rhs.md5))));
    }


    /**
     * FileType
     * <p>
     * 
     * 
     */
    @Generated("jsonschema2pojo")
    public enum FileType {

        BEAST_2_CONFIGURATION("beast2Configuration"),
        BEAST_2_POSTERIOR_LOGS("beast2PosteriorLogs"),
        CODEPHY_MODEL("codephyModel"),
        PHYLO_DATA_EXPERIMENT("phyloDataExperiment"),
        POSTERIOR_TREES("posteriorTrees"),
        SUMMARY_TREE("summaryTree"),
        UNKNOWN("unknown");
        private final String value;
        private final static Map<String, File.FileType> CONSTANTS = new HashMap<String, File.FileType>();

        static {
            for (File.FileType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        FileType(String value) {
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
        public static File.FileType fromValue(String value) {
            File.FileType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
