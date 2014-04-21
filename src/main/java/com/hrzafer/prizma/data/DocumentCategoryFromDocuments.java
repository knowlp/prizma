package com.hrzafer.prizma.data;

import java.util.Collections;
import java.util.List;

/**
 * @author hrzafer
 */
public class DocumentCategoryFromDocuments extends DocumentCategory {

    private final List<Document> documents;

    public DocumentCategoryFromDocuments(List<Document> documents, String name) {
        super(name);
        this.documents = documents;
    }

    @Override
    public List<Document> getTrainInstances() {
        return documents.subList(0, getTrainInstanceCount());
    }

    @Override
    public List<Document> getTestInstances() {
        return documents.subList(getTrainInstanceCount(), documents.size());
    }

    @Override
    public List<Document> getAllInstances() {
        return documents;
    }

    @Override
    public void shuffle() {
        Collections.shuffle(documents);
    }

    @Override
    public int getInstanceCount() {
        return documents.size();
    }
}
