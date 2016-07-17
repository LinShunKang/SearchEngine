package com.lsk.search.service;

import com.lsk.search.model.WordPosition;

import java.util.List;
import java.util.Map;

/**
 * Created by LinShunkang on 6/27/16.
 */
public interface WordPositionService {

    boolean save(WordPosition wordPosition);

    boolean save(List<WordPosition> wordPositionList);

    Map<Long,List<Integer>> getAllWordPosMap();
}
