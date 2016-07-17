package com.lsk.search.service;

import com.lsk.search.model.Word;

import java.util.List;

/**
 * Created by LinShunkang on 7/3/16.
 */
public interface WordService {

    boolean save(Word word);

    Word get(String word);

    long getId(String word);

    List<Word> getAll();
}
