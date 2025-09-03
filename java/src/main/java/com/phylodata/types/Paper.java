package com.phylodata.types;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Paper class.</p>
 *
 * @author tobiaochsner
 */
public class Paper {

    private String doi;
    private String title;
    private Integer year;
    private List<String> authors = new ArrayList<String>();
    private String abstractText;
    private String bibtex;
    private String url;

    /**
     * <p>fromPartial.</p>
     *
     * @param editable a {@link com.phylodata.types.EditablePaper} object
     * @param nonEditable a {@link com.phylodata.types.NonEditablePaper} object
     * @return a {@link com.phylodata.types.Paper} object
     */
    public static Paper fromPartial(EditablePaper editable, NonEditablePaper nonEditable) {
        Paper p = new Paper();
        p.setDoi(nonEditable.getDoi());
        p.setTitle(editable.getTitle());
        p.setYear(editable.getYear());
        p.setAuthors(editable.getAuthors());
        p.setAbstract(editable.getAbstract());
        p.setBibtex(editable.getBibtex());
        Object urlValue = editable.getUrl();
        p.setUrl(urlValue == null ? null : urlValue.toString());
        return p;
    }

    /**
     * <p>Getter for the field <code>doi</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getDoi() {
        return doi;
    }

    /**
     * <p>Setter for the field <code>doi</code>.</p>
     *
     * @param doi a {@link java.lang.String} object
     */
    public void setDoi(String doi) {
        this.doi = doi;
    }

    /**
     * <p>Getter for the field <code>title</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getTitle() {
        return title;
    }

    /**
     * <p>Setter for the field <code>title</code>.</p>
     *
     * @param title a {@link java.lang.String} object
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * <p>Getter for the field <code>year</code>.</p>
     *
     * @return a {@link java.lang.Integer} object
     */
    public Integer getYear() {
        return year;
    }

    /**
     * <p>Setter for the field <code>year</code>.</p>
     *
     * @param year a {@link java.lang.Integer} object
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * <p>Getter for the field <code>authors</code>.</p>
     *
     * @return a {@link java.util.List} object
     */
    public List<String> getAuthors() {
        return authors;
    }

    /**
     * <p>Setter for the field <code>authors</code>.</p>
     *
     * @param authors a {@link java.util.List} object
     */
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    /**
     * <p>getAbstract.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getAbstract() {
        return abstractText;
    }

    /**
     * <p>setAbstract.</p>
     *
     * @param _abstract a {@link java.lang.String} object
     */
    public void setAbstract(String _abstract) {
        this.abstractText = _abstract;
    }

    /**
     * <p>Getter for the field <code>bibtex</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getBibtex() {
        return bibtex;
    }

    /**
     * <p>Setter for the field <code>bibtex</code>.</p>
     *
     * @param bibtex a {@link java.lang.String} object
     */
    public void setBibtex(String bibtex) {
        this.bibtex = bibtex;
    }

    /**
     * <p>Getter for the field <code>url</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getUrl() {
        return url;
    }

    /**
     * <p>Setter for the field <code>url</code>.</p>
     *
     * @param url a {@link java.lang.String} object
     */
    public void setUrl(String url) {
        this.url = url;
    }
}


