package com.luiscamara.formfillerweb.controllers;

import com.luiscamara.pdfformfiller.PDFFormFiller;
import com.luiscamara.formfillerweb.models.Document;
import com.luiscamara.formfillerweb.models.DocumentSummaryResponse;
import com.luiscamara.formfillerweb.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping("/{docId}")
    public ResponseEntity<?> getDocument(@PathVariable String docId) {
        Document doc = documentService.getDocument(docId);
        if(doc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(doc, HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<DocumentSummaryResponse> listDocuments() {
        return documentService.listDocuments();
    }

    @GetMapping("/{docId}/listFields")
    public ResponseEntity<?> listDocumentFields(@PathVariable String docId) {
        Document doc = documentService.getDocument(docId);
        if(doc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(doc.getFields(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> postDocument(@RequestParam("pdf") MultipartFile pdf) throws IOException {
        if(documentService.findByFilename(pdf.getOriginalFilename()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        String id = documentService.addDocument(pdf);

        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{docId}/fill", consumes = "application/json", produces = "application/pdf")
    public ResponseEntity<?> fillDocument(@PathVariable String docId, @RequestBody Map<String, String> params) throws Exception {
        Document doc = documentService.getDocument(docId);
        if(doc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Document processing
        PDFFormFiller formFiller = new PDFFormFiller(doc.getContent().getData());
        for(String key : params.keySet()) {
            formFiller.setField(key, params.get(key));
        }

        byte[] bytes = formFiller.save();
        formFiller.close();
        return new ResponseEntity(bytes, HttpStatus.OK);
    }

    @DeleteMapping("/{docId}/remove")
    public ResponseEntity removeDocument(@PathVariable String docId) {
        documentService.removeDocument(docId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
