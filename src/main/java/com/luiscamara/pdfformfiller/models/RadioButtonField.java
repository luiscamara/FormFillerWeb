package com.luiscamara.pdfformfiller.models;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.util.Map;

public class RadioButtonField extends Field {
    private Map<String, String> mapValueToString;
    public RadioButtonField(PDField field, int page, Map<String, String> mapValueToString) {
        super(field, page);
        this.mapValueToString = mapValueToString;
    }

    public Map<String, String> getMapValueToString() {
        return mapValueToString;
    }

    public String toString() {
        String options = super.toString() + "\n";
        options += "Options: ";
        for(String key : mapValueToString.keySet()) {
            options += key + ",";
        }

        return options.substring(0, options.length() - 1);
    }
}

