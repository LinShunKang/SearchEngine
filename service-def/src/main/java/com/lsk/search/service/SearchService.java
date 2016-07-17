package com.lsk.search.service;

import com.lsk.search.model.Document;

import java.util.List;

/**
 * Created by LinShunkang on 7/3/16.
 */
public interface SearchService {

    boolean addSearchIndex(String title, String body);

    List<Document> suggest(String sentence, int count);
}
