package com.luiscamara.pdfformfiller.models;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;

public class Field {
    private String fullyQualifiedName;
    private String name;
    private FieldType type;
    private String description;
    private int page;

    public Field(PDField field, int page) {
        this.fullyQualifiedName = field.getFullyQualifiedName();
        this.name = field.getPartialName();

        switch(field.getFieldType()) {
            case "Tx":
                this.type = FieldType.TEXT;
                break;
            case "Btn":
                if(field instanceof PDRadioButton) {
                    this.type = FieldType.RADIOBUTTON;
                } else {
                    this.type = FieldType.BUTTON;
                }
                break;
            default:
                this.type = FieldType.UNKNOWN;
                break;
        }

        this.description = field.getAlternateFieldName();
        this.page = page;
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

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
               "Type: " + type + "\n" +
               "Description: " + description + "\n" +
               "Document Page: " + page;
    }
}
