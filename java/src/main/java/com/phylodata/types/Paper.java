package com.phylodata.types;

import java.util.ArrayList;
import java.util.List;

public class Paper {

    private String doi;
    private String title;
    private Integer year;
    private List<String> authors = new ArrayList<String>();
    private String abstractText;
    private String bibtex;
    private String url;

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

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getAbstract() {
        return abstractText;
    }

    public void setAbstract(String _abstract) {
        this.abstractText = _abstract;
    }

    public String getBibtex() {
        return bibtex;
    }

    public void setBibtex(String bibtex) {
        this.bibtex = bibtex;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


