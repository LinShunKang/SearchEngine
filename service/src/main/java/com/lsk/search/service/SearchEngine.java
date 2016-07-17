package com.lsk.search.service;

import java.util.List;

/**
 * Created by LinShunkang on 6/19/16.
 */
public interface SearchEngine {

    boolean addSearchIndex(long docId, String content);

    int dropIdSearchIndex(long docId);

    int dropWordSearchIndex(String word);

    List<Long> search(String sentence, int count);

}
