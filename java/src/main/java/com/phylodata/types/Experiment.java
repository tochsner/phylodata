package com.phylodata.types;

/**
 * <p>Experiment class.</p>
 *
 * @author tobiaochsner
 */
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

    /**
     * <p>fromPartial.</p>
     *
     * @param editable a {@link com.phylodata.types.EditableExperiment} object
     * @param nonEditable a {@link com.phylodata.types.NonEditableExperiment} object
     * @return a {@link com.phylodata.types.Experiment} object
     */
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

    /**
     * <p>Getter for the field <code>type</code>.</p>
     *
     * @return a {@link com.phylodata.types.EditableExperiment.ExperimentType} object
     */
    public EditableExperiment.ExperimentType getType() {
        return type;
    }

    /**
     * <p>Setter for the field <code>type</code>.</p>
     *
     * @param type a {@link com.phylodata.types.EditableExperiment.ExperimentType} object
     */
    public void setType(EditableExperiment.ExperimentType type) {
        this.type = type;
    }

    /**
     * <p>Getter for the field <code>humanReadableId</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getHumanReadableId() {
        return humanReadableId;
    }

    /**
     * <p>Setter for the field <code>humanReadableId</code>.</p>
     *
     * @param humanReadableId a {@link java.lang.String} object
     */
    public void setHumanReadableId(String humanReadableId) {
        this.humanReadableId = humanReadableId;
    }

    /**
     * <p>Getter for the field <code>origin</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * <p>Setter for the field <code>origin</code>.</p>
     *
     * @param origin a {@link java.lang.String} object
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * <p>Getter for the field <code>uploadDate</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getUploadDate() {
        return uploadDate;
    }

    /**
     * <p>Setter for the field <code>uploadDate</code>.</p>
     *
     * @param uploadDate a {@link java.lang.String} object
     */
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * <p>Getter for the field <code>version</code>.</p>
     *
     * @return a {@link java.lang.Integer} object
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * <p>Setter for the field <code>version</code>.</p>
     *
     * @param version a {@link java.lang.Integer} object
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * <p>Getter for the field <code>license</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getLicense() {
        return license;
    }

    /**
     * <p>Setter for the field <code>license</code>.</p>
     *
     * @param license a {@link java.lang.String} object
     */
    public void setLicense(String license) {
        this.license = license;
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
     * <p>Getter for the field <code>description</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Setter for the field <code>description</code>.</p>
     *
     * @param description a {@link java.lang.String} object
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getId() {
        return id;
    }

    /**
     * <p>Setter for the field <code>id</code>.</p>
     *
     * @param id a {@link java.lang.String} object
     */
    public void setId(String id) {
        this.id = id;
    }
}


