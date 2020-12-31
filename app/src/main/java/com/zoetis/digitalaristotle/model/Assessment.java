
package com.zoetis.digitalaristotle.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assessment {

    @SerializedName("assessmentId")
    @Expose
    private String assessmentId;
    @SerializedName("assessmentName")
    @Expose
    private String assessmentName;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;
    @SerializedName("totalMarks")
    @Expose
    private Integer totalMarks;

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

}
