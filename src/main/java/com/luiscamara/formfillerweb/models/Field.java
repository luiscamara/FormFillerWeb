package com.luiscamara.formfillerweb.models;

import com.luiscamara.PDFFormFiller.models.FieldType;

public class Field {
    private String fullyQualifiedName;
    private String name;
    private FieldType type;
    private String description;
    private int page;
    private String possibleInputs;

    public Field() {}

    public Field(com.luiscamara.PDFFormFiller.models.Field field) {
        this.fullyQualifiedName = field.getFullyQualifiedName();
        this.name = field.getName();
        this.type = field.getType();
        this.description = field.getDescription();
        this.page = field.getPage();
        switch(type) {
            case BUTTON:
                this.possibleInputs = "(1 or Off)";
                break;
            case RADIOBUTTON:
                String response = "(Available choices: ";
                for(String key : ((com.luiscamara.PDFFormFiller.models.RadioButtonField)field).getMapValueToString().keySet()) {
                    response += "Choice: " + key + " Value: " + ((com.luiscamara.PDFFormFiller.models.RadioButtonField)field).getMapValueToString().get(key) + ", ";
                }
                this.possibleInputs = response.substring(0, response.length()-2) + ")";
                break;
            case TEXT:
                this.possibleInputs = "(Any text)";
                break;
            default:
                this.possibleInputs = "(Invalid/Unsupported field)";
                break;
        }
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public String getName() {
        return name;
    }

    public FieldType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getPage() {
        return page;
    }

    public String getPossibleInputs() {
        return possibleInputs;
    }
}

