package com.luiscamara.pdfformfiller.models;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.util.List;
import java.util.Map;

public class RadioButtonField extends Field {
    private Map<String, String> mapChoiceToValue;
    public RadioButtonField(PDField field, int page, Map<String, String> options) {
        super(field, page);
        this.mapChoiceToValue = options;
    }

    public Map<String, String> getMapChoiceToValue() {
        return mapChoiceToValue;
    }

    public String toString() {
        String result = super.toString() + "\n";
        result += "Options: (";
        for(String option : this.mapChoiceToValue.keySet()) {
            result += "Choice: " + option + ", Value: " + this.mapChoiceToValue.get(option) + "; ";
        }

        return result.substring(0, result.length() - 1) + ")";
    }
}

