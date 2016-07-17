package com.lsk.search.service;

import com.lsk.search.model.Document;

import java.util.List;

/**
 * Created by LinShunkang on 6/27/16.
 */
public interface DocumentService {

    boolean save(Document document);

    Document getById(long id);

    List<Document> getByIdList(List<Long> idList);

}
