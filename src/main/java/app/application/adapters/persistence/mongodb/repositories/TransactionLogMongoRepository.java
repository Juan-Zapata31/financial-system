package app.application.adapters.persistence.mongodb.repositories;

import app.application.adapters.persistence.mongodb.documents.TransactionLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionLogMongoRepository extends MongoRepository<TransactionLogDocument, String> {
    List<TransactionLogDocument> findByAffectedProductId(String affectedProductId);
    List<TransactionLogDocument> findByResponsableUser(String responsableUser);
}
