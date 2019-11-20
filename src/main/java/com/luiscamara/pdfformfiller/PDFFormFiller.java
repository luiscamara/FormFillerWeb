package com.luiscamara.pdfformfiller;

import com.luiscamara.pdfformfiller.models.Field;
import com.luiscamara.pdfformfiller.models.FieldType;
import com.luiscamara.pdfformfiller.models.RadioButtonField;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * PDFFormFiller class
 * Lists and fills PDF form fields
 */
public class PDFFormFiller {
    private PDDocument pdfDocument;
    private List<Field> fields;

    /**
     * Constructor
     * @param bytes Bytes of PDF file content
     * @throws IOException
     */
    public PDFFormFiller(byte[] bytes) throws IOException {
        load(bytes);
    }

    /**
     * Constructor
     * @param filePath Path to PDF Form file
     * @throws IOException
     */
    public PDFFormFiller(String filePath) throws IOException {
        load(filePath);
    }

    /**
     * Load method
     * Loads a PDF form and its associated fields
     * @param bytes Bytes of PDF file content
     * @throws IOException
     */
    public void load(byte[] bytes) throws IOException {
        try {
            pdfDocument = PDDocument.load(bytes);
        } catch(IOException exception) {
            throw new IOException("PDF file path invalid or file not found!", exception);
        }

        PDAcroForm form = pdfDocument.getDocumentCatalog().getAcroForm();

        fields = new ArrayList<>();
        PDFieldTree tree = form.getFieldTree();
        for(PDField f : tree) {
            processField(f);
        }
    }

    /**
     * Load method
     * Loads a PDF form and its associated fields
     * @param filePath Path to the PDF file
     * @throws IOException
     */
    public void load(String filePath) throws IOException {
        try {
            File pdfDocumentFile = new File(filePath);
            pdfDocument = PDDocument.load(pdfDocumentFile);
        } catch(IOException exception) {
            throw new IOException("PDF file path invalid or file not found!", exception);
        }

        PDAcroForm form = pdfDocument.getDocumentCatalog().getAcroForm();

        fields = new ArrayList<>();
        PDFieldTree tree = form.getFieldTree();
        for(PDField f : tree) {
            processField(f);
        }
    }

    /**
     * processField method
     * Recursively processes a form field and its children
     * @param field PDF Form field to be processed
     */
    private void processField(PDField field) {
        // Check if field is final or it has children
        if(field instanceof PDNonTerminalField) {
            // Keep processing children
            for(PDField child : ((PDNonTerminalField)field).getChildren()) {
                processField(child);
            }
        } else {
            // If field is a new page keep track of its number
            String pageFullName = field.getFullyQualifiedName();
            String pagePartialName = pageFullName.split("\\.")[1];
            String pageNumberString = pagePartialName.split("\\[")[0];
            int currentPage = Integer.parseInt(pageNumberString.substring(4));

            if(field instanceof PDRadioButton) {
                // If its a radio button, register choices available
                RadioButtonField newField = new RadioButtonField(field, currentPage, new ArrayList<String>(((PDRadioButton) field).getExportValues()));
                fields.add(newField);
            }

            else {
                // Final field, register it
                Field newField = new Field(field, currentPage);
                fields.add(newField);
            }
        }
    }

    /**
     * save method
     * Saves current document to the destination path
     * @param destinationFilePath Path and name of destination PDF file to be saved
     * @throws IOException
     */
    public void save(String destinationFilePath) throws IOException {
        try {
            File pdfTargetFile = new File(destinationFilePath);
            pdfDocument.setAllSecurityToBeRemoved(true);
            pdfDocument.save(pdfTargetFile);
        } catch(IOException exception) {
            throw new IOException("PDF file path invalid or cannot write file!", exception);
        }
    }

    /**
     * save method
     * Saves current document and generate bytes
     * @return PDF in bytes
     * @throws IOException
     */
    public byte[] save() throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pdfDocument.setAllSecurityToBeRemoved(true);
            pdfDocument.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch(IOException exception) {
            throw new IOException("PDF file path invalid or cannot write file!", exception);
        }
    }

    /**
     * close method
     * Closes the PDF document
     * @throws IOException
     */
    public void close() throws IOException {
        pdfDocument.close();
    }

    /**
     * getFields method
     * Return all fields in PDF form
     * @return Form fields
     */
    public List<Field> getFields() {
        return fields;
    }

    /**
     * setField method
     * Set value of a field
     * @param fullyQualifiedName Full qualified name of the field
     * @param value Value to be written
     * @throws IOException
     */
    public void setField(String fullyQualifiedName, String value) throws IOException {
        if(fullyQualifiedName == null)
            throw new IllegalArgumentException("fullyQualifiedName argument is null!");
        if(value == null)
            throw new IllegalArgumentException("value argument is null!");

        PDAcroForm form = pdfDocument.getDocumentCatalog().getAcroForm();
        PDField formField = form.getField(fullyQualifiedName);
        if(formField == null)
            throw new IOException("Could not find field " + fullyQualifiedName + "! Make sure field exists on the form!");

        formField.setValue(value);
    }

    /**
     * setButtonField method
     * Set a PDF form button field value
     * @param field Field to be modified
     * @param marked Indicate if button should be marked or not
     * @throws Exception
     */
    public void setButtonField(Field field, boolean marked) throws Exception {
        if(field == null)
            throw new IllegalArgumentException("Field argument is null!");

        if(field.getType() != FieldType.BUTTON) {
            throw new IllegalArgumentException("The field provided is not a button!");
        }

        PDAcroForm form = pdfDocument.getDocumentCatalog().getAcroForm();
        PDField formField = form.getField(field.getFullyQualifiedName());
        ((PDButton)formField).setValue(marked ? "1" : "Off");
    }

    /**
     * setRadioButtonField method
     * Check a PDF form radio button
     * @param field Field to be modified
     * @param value Radiobutton to be marked
     * @throws Exception
     */
    public void setRadioButtonField(RadioButtonField field, String value) throws Exception {
        if(field == null)
            throw new IllegalArgumentException("Field argument is null!");

        if(field.getType() != FieldType.RADIOBUTTON) {
            throw new IllegalArgumentException("The field provided is not a radio button!");
        }

        PDAcroForm form = pdfDocument.getDocumentCatalog().getAcroForm();
        PDField formField = form.getField(field.getFullyQualifiedName());
        ((PDRadioButton)formField).setValue(value);
    }

    /**
     * setTextField method
     * Set text field's text message
     * @param field Field to be modified
     * @param text Text to be written to field
     * @throws Exception
     */
    public void setTextField(Field field, String text) throws Exception {
        if(field == null)
            throw new IllegalArgumentException("Field argument is null!");

        if(field.getType() != FieldType.TEXT) {
            throw new IllegalArgumentException("The field provided is not a text!");
        }

        PDAcroForm form = pdfDocument.getDocumentCatalog().getAcroForm();
        PDField formField = form.getField(field.getFullyQualifiedName());
        ((PDTextField)formField).setValue(text);
    }
}
