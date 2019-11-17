package com.luiscamara.formfillerweb.services;

import com.luiscamara.pdfformfiller.PDFFormFiller;
import com.luiscamara.formfillerweb.models.Document;
import com.luiscamara.formfillerweb.models.DocumentSummaryResponse;
import com.luiscamara.formfillerweb.models.Field;
import com.luiscamara.formfillerweb.repositories.DocumentRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepo;

    public String addDocument(MultipartFile file) throws IOException {
        Document document = new Document(file.getOriginalFilename());
        document.setCreatedAt(LocalDateTime.now());
        document.setContent(new Binary(BsonBinarySubType.BINARY, file.getBytes()));

        // Document processing
        PDFFormFiller formFiller = new PDFFormFiller(document.getContent().getData());
        for(com.luiscamara.pdfformfiller.models.Field formField : formFiller.getFields()) {
            Field newField = new Field(formField);
            document.getFields().add(newField);
        }

        document = documentRepo.insert(document);
        formFiller.close();
        return document.getId();
    }

    public Document getDocument(String id) {
        return documentRepo.findById(id).get();
    }

    public Document findByFilename(String filename) {
        return documentRepo.findByFileName(filename);
    }

    public List<DocumentSummaryResponse> listDocuments() {
        List<Document> documents = documentRepo.findAll();
        List<DocumentSummaryResponse> results = new ArrayList<>();
        for(Document doc : documents) {
            DocumentSummaryResponse dsr = new DocumentSummaryResponse(doc.getId(), doc.getFileName(), doc.getCreatedAt());
            results.add(dsr);
        }

        return results;
    }

    public void removeDocument(String id) {
        documentRepo.deleteById(id);
    }
}
