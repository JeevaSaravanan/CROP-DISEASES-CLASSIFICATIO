package com.insight.farmlenz.Model;

public class dismodel {

    String Biological_Control,Cause,Chemical_Control,Disease,Symptoms,image;

    public dismodel() {
    }

    public dismodel(String biological_Control, String cause, String chemical_Control, String disease, String symptoms, String image) {
        Biological_Control = biological_Control;
        Cause = cause;
        Chemical_Control = chemical_Control;
        Disease = disease;
        Symptoms = symptoms;
        this.image = image;
    }

    public String getBiological_Control() {
        return Biological_Control;
    }

    public void setBiological_Control(String biological_Control) {
        Biological_Control = biological_Control;
    }

    public String getCause() {
        return Cause;
    }

    public void setCause(String cause) {
        Cause = cause;
    }

    public String getChemical_Control() {
        return Chemical_Control;
    }

    public void setChemical_Control(String chemical_Control) {
        Chemical_Control = chemical_Control;
    }

    public String getDisease() {
        return Disease;
    }

    public void setDisease(String disease) {
        Disease = disease;
    }

    public String getSymptoms() {
        return Symptoms;
    }

    public void setSymptoms(String symptoms) {
        Symptoms = symptoms;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
