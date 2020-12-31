
package com.zoetis.digitalaristotle.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("qno")
    @Expose
    private Integer qno;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("mcOptions")
    @Expose
    private List<String> mcOptions = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("marks")
    @Expose
    private Integer marks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQno() {
        return qno;
    }

    public void setQno(Integer qno) {
        this.qno = qno;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getMcOptions() {
        return mcOptions;
    }

    public void setMcOptions(List<String> mcOptions) {
        this.mcOptions = mcOptions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

}
