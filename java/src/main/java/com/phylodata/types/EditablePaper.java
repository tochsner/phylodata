
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
 * EditablePaper
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "title",
    "year",
    "authors",
    "email",
    "abstract",
    "bibtex",
    "url"
})
@Generated("jsonschema2pojo")
public class EditablePaper {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("title")
    private String title;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("year")
    private Integer year;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("authors")
    private List<String> authors = new ArrayList<String>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("email")
    private String email;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("abstract")
    private String _abstract;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("bibtex")
    private String bibtex;
    @JsonProperty("url")
    private Object url = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("year")
    public Integer getYear() {
        return year;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("year")
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("authors")
    public List<String> getAuthors() {
        return authors;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("authors")
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("abstract")
    public String getAbstract() {
        return _abstract;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("abstract")
    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("bibtex")
    public String getBibtex() {
        return bibtex;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("bibtex")
    public void setBibtex(String bibtex) {
        this.bibtex = bibtex;
    }

    @JsonProperty("url")
    public Object getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(Object url) {
        this.url = url;
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
        sb.append(EditablePaper.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("year");
        sb.append('=');
        sb.append(((this.year == null)?"<null>":this.year));
        sb.append(',');
        sb.append("authors");
        sb.append('=');
        sb.append(((this.authors == null)?"<null>":this.authors));
        sb.append(',');
        sb.append("email");
        sb.append('=');
        sb.append(((this.email == null)?"<null>":this.email));
        sb.append(',');
        sb.append("_abstract");
        sb.append('=');
        sb.append(((this._abstract == null)?"<null>":this._abstract));
        sb.append(',');
        sb.append("bibtex");
        sb.append('=');
        sb.append(((this.bibtex == null)?"<null>":this.bibtex));
        sb.append(',');
        sb.append("url");
        sb.append('=');
        sb.append(((this.url == null)?"<null>":this.url));
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
        result = ((result* 31)+((this.year == null)? 0 :this.year.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.title == null)? 0 :this.title.hashCode()));
        result = ((result* 31)+((this.bibtex == null)? 0 :this.bibtex.hashCode()));
        result = ((result* 31)+((this.email == null)? 0 :this.email.hashCode()));
        result = ((result* 31)+((this._abstract == null)? 0 :this._abstract.hashCode()));
        result = ((result* 31)+((this.url == null)? 0 :this.url.hashCode()));
        result = ((result* 31)+((this.authors == null)? 0 :this.authors.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EditablePaper) == false) {
            return false;
        }
        EditablePaper rhs = ((EditablePaper) other);
        return (((((((((this.year == rhs.year)||((this.year!= null)&&this.year.equals(rhs.year)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.title == rhs.title)||((this.title!= null)&&this.title.equals(rhs.title))))&&((this.bibtex == rhs.bibtex)||((this.bibtex!= null)&&this.bibtex.equals(rhs.bibtex))))&&((this.email == rhs.email)||((this.email!= null)&&this.email.equals(rhs.email))))&&((this._abstract == rhs._abstract)||((this._abstract!= null)&&this._abstract.equals(rhs._abstract))))&&((this.url == rhs.url)||((this.url!= null)&&this.url.equals(rhs.url))))&&((this.authors == rhs.authors)||((this.authors!= null)&&this.authors.equals(rhs.authors))));
    }

}
