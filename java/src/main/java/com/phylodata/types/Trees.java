
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
 * Trees
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "numberOfTrees",
    "numberOfTips",
    "ultrametric",
    "timeTree",
    "rooted",
    "ccd1Entropy",
    "treeEss",
    "ccd0MapTree",
    "hipstrTree",
    "leafToSampleMap",
    "averageRootAge"
})
@Generated("jsonschema2pojo")
public class Trees {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("numberOfTrees")
    private Integer numberOfTrees;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("numberOfTips")
    private Integer numberOfTips;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("ultrametric")
    private Boolean ultrametric;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("timeTree")
    private Boolean timeTree;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("rooted")
    private Boolean rooted;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("ccd1Entropy")
    private Double ccd1Entropy;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("treeEss")
    private Integer treeEss;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("ccd0MapTree")
    private String ccd0MapTree;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("hipstrTree")
    private String hipstrTree;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("leafToSampleMap")
    private LeafToSampleMap leafToSampleMap;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("averageRootAge")
    private Double averageRootAge;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("numberOfTrees")
    public Integer getNumberOfTrees() {
        return numberOfTrees;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("numberOfTrees")
    public void setNumberOfTrees(Integer numberOfTrees) {
        this.numberOfTrees = numberOfTrees;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("numberOfTips")
    public Integer getNumberOfTips() {
        return numberOfTips;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("numberOfTips")
    public void setNumberOfTips(Integer numberOfTips) {
        this.numberOfTips = numberOfTips;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("ultrametric")
    public Boolean getUltrametric() {
        return ultrametric;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("ultrametric")
    public void setUltrametric(Boolean ultrametric) {
        this.ultrametric = ultrametric;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("timeTree")
    public Boolean getTimeTree() {
        return timeTree;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("timeTree")
    public void setTimeTree(Boolean timeTree) {
        this.timeTree = timeTree;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("rooted")
    public Boolean getRooted() {
        return rooted;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("rooted")
    public void setRooted(Boolean rooted) {
        this.rooted = rooted;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("ccd1Entropy")
    public Double getCcd1Entropy() {
        return ccd1Entropy;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("ccd1Entropy")
    public void setCcd1Entropy(Double ccd1Entropy) {
        this.ccd1Entropy = ccd1Entropy;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("treeEss")
    public Integer getTreeEss() {
        return treeEss;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("treeEss")
    public void setTreeEss(Integer treeEss) {
        this.treeEss = treeEss;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("ccd0MapTree")
    public String getCcd0MapTree() {
        return ccd0MapTree;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("ccd0MapTree")
    public void setCcd0MapTree(String ccd0MapTree) {
        this.ccd0MapTree = ccd0MapTree;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("hipstrTree")
    public String getHipstrTree() {
        return hipstrTree;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("hipstrTree")
    public void setHipstrTree(String hipstrTree) {
        this.hipstrTree = hipstrTree;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("leafToSampleMap")
    public LeafToSampleMap getLeafToSampleMap() {
        return leafToSampleMap;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("leafToSampleMap")
    public void setLeafToSampleMap(LeafToSampleMap leafToSampleMap) {
        this.leafToSampleMap = leafToSampleMap;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("averageRootAge")
    public Double getAverageRootAge() {
        return averageRootAge;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("averageRootAge")
    public void setAverageRootAge(Double averageRootAge) {
        this.averageRootAge = averageRootAge;
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
        sb.append(Trees.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("numberOfTrees");
        sb.append('=');
        sb.append(((this.numberOfTrees == null)?"<null>":this.numberOfTrees));
        sb.append(',');
        sb.append("numberOfTips");
        sb.append('=');
        sb.append(((this.numberOfTips == null)?"<null>":this.numberOfTips));
        sb.append(',');
        sb.append("ultrametric");
        sb.append('=');
        sb.append(((this.ultrametric == null)?"<null>":this.ultrametric));
        sb.append(',');
        sb.append("timeTree");
        sb.append('=');
        sb.append(((this.timeTree == null)?"<null>":this.timeTree));
        sb.append(',');
        sb.append("rooted");
        sb.append('=');
        sb.append(((this.rooted == null)?"<null>":this.rooted));
        sb.append(',');
        sb.append("ccd1Entropy");
        sb.append('=');
        sb.append(((this.ccd1Entropy == null)?"<null>":this.ccd1Entropy));
        sb.append(',');
        sb.append("treeEss");
        sb.append('=');
        sb.append(((this.treeEss == null)?"<null>":this.treeEss));
        sb.append(',');
        sb.append("ccd0MapTree");
        sb.append('=');
        sb.append(((this.ccd0MapTree == null)?"<null>":this.ccd0MapTree));
        sb.append(',');
        sb.append("hipstrTree");
        sb.append('=');
        sb.append(((this.hipstrTree == null)?"<null>":this.hipstrTree));
        sb.append(',');
        sb.append("leafToSampleMap");
        sb.append('=');
        sb.append(((this.leafToSampleMap == null)?"<null>":this.leafToSampleMap));
        sb.append(',');
        sb.append("averageRootAge");
        sb.append('=');
        sb.append(((this.averageRootAge == null)?"<null>":this.averageRootAge));
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
        result = ((result* 31)+((this.numberOfTrees == null)? 0 :this.numberOfTrees.hashCode()));
        result = ((result* 31)+((this.timeTree == null)? 0 :this.timeTree.hashCode()));
        result = ((result* 31)+((this.averageRootAge == null)? 0 :this.averageRootAge.hashCode()));
        result = ((result* 31)+((this.ccd1Entropy == null)? 0 :this.ccd1Entropy.hashCode()));
        result = ((result* 31)+((this.ccd0MapTree == null)? 0 :this.ccd0MapTree.hashCode()));
        result = ((result* 31)+((this.ultrametric == null)? 0 :this.ultrametric.hashCode()));
        result = ((result* 31)+((this.leafToSampleMap == null)? 0 :this.leafToSampleMap.hashCode()));
        result = ((result* 31)+((this.hipstrTree == null)? 0 :this.hipstrTree.hashCode()));
        result = ((result* 31)+((this.rooted == null)? 0 :this.rooted.hashCode()));
        result = ((result* 31)+((this.numberOfTips == null)? 0 :this.numberOfTips.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.treeEss == null)? 0 :this.treeEss.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Trees) == false) {
            return false;
        }
        Trees rhs = ((Trees) other);
        return (((((((((((((this.numberOfTrees == rhs.numberOfTrees)||((this.numberOfTrees!= null)&&this.numberOfTrees.equals(rhs.numberOfTrees)))&&((this.timeTree == rhs.timeTree)||((this.timeTree!= null)&&this.timeTree.equals(rhs.timeTree))))&&((this.averageRootAge == rhs.averageRootAge)||((this.averageRootAge!= null)&&this.averageRootAge.equals(rhs.averageRootAge))))&&((this.ccd1Entropy == rhs.ccd1Entropy)||((this.ccd1Entropy!= null)&&this.ccd1Entropy.equals(rhs.ccd1Entropy))))&&((this.ccd0MapTree == rhs.ccd0MapTree)||((this.ccd0MapTree!= null)&&this.ccd0MapTree.equals(rhs.ccd0MapTree))))&&((this.ultrametric == rhs.ultrametric)||((this.ultrametric!= null)&&this.ultrametric.equals(rhs.ultrametric))))&&((this.leafToSampleMap == rhs.leafToSampleMap)||((this.leafToSampleMap!= null)&&this.leafToSampleMap.equals(rhs.leafToSampleMap))))&&((this.hipstrTree == rhs.hipstrTree)||((this.hipstrTree!= null)&&this.hipstrTree.equals(rhs.hipstrTree))))&&((this.rooted == rhs.rooted)||((this.rooted!= null)&&this.rooted.equals(rhs.rooted))))&&((this.numberOfTips == rhs.numberOfTips)||((this.numberOfTips!= null)&&this.numberOfTips.equals(rhs.numberOfTips))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.treeEss == rhs.treeEss)||((this.treeEss!= null)&&this.treeEss.equals(rhs.treeEss))));
    }

}
