package com.lsk.search.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by LinShunkang on 7/3/16.
 */
public interface ParticipleService {

    Set<String> getWordSet4Index(String centence);

    List<String> getWordList4Index(String centence);

    Map<String, Integer> getWordCountMap(String text);

    Map<String, List<Integer>> getWordOffsetMap(String text);

}
