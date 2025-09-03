package com.phylodata.types;

public class Experiment {

    private EditableExperiment.ExperimentType type;
    private String humanReadableId;
    private String origin;
    private String uploadDate;
    private Integer version;
    private String license = "CC0";
    private String title;
    private String description;
    private String id;

    public static Experiment fromPartial(EditableExperiment editable, NonEditableExperiment nonEditable) {
        Experiment e = new Experiment();
        e.setType(editable.getType());
        e.setHumanReadableId(nonEditable.getHumanReadableId());
        e.setOrigin(nonEditable.getOrigin());
        e.setUploadDate(nonEditable.getUploadDate());
        e.setVersion(nonEditable.getVersion());
        e.setLicense(nonEditable.getLicense());
        Object titleValue = editable.getTitle();
        e.setTitle(titleValue == null ? null : titleValue.toString());
        Object descValue = editable.getDescription();
        e.setDescription(descValue == null ? null : descValue.toString());
        Object idValue = nonEditable.getId();
        e.setId(idValue == null ? null : idValue.toString());
        return e;
    }

    public EditableExperiment.ExperimentType getType() {
        return type;
    }

    public void setType(EditableExperiment.ExperimentType type) {
        this.type = type;
    }

    public String getHumanReadableId() {
        return humanReadableId;
    }

    public void setHumanReadableId(String humanReadableId) {
        this.humanReadableId = humanReadableId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


