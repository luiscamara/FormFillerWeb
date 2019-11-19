package com.luiscamara.pdfformfiller.models;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.util.List;
import java.util.Map;

public class RadioButtonField extends Field {
    private List<String> options;
    public RadioButtonField(PDField field, int page, List<String> options) {
        super(field, page);
        this.options = options;
        this.options.add("Off");
    }

    public List<String> getOptions() {
        return options;
    }

    public String toString() {
        String result = super.toString() + "\n";
        result += "Options: ";
        for(String option : this.options) {
            result += option + ",";
        }

        return result.substring(0, result.length() - 1);
    }
}

