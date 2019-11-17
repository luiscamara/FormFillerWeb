package com.luiscamara.formfillerweb.repositories;

import com.luiscamara.formfillerweb.models.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepository extends MongoRepository<Document, String> {
    Document findByFileName(String fileName);
}
