package com.lsk.search.service.impl;

import com.lsk.log.LoggerManager;
import com.lsk.search.service.ParticipleService;
import com.lsk.search.util.StopWords;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by LinShunkang on 7/3/16.
 */
public class ParticipleServiceImpl implements ParticipleService {

    private static final List<String> EMPTY_WORDS_LIST = Collections.unmodifiableList(new ArrayList<String>(0));

    private static final Set<String> EMPTY_WORDS_SET = Collections.unmodifiableSet(new HashSet<String>(0));

    private static final Map<String, List<Integer>> EMPTY_WORD_OFFSET_MAP = Collections.unmodifiableMap(new HashMap<String, List<Integer>>(0));

    private Set<String> stopWords = StopWords.getStopWordSet();

    @Override
    public Set<String> getWordSet4Index(String text) {
        List<Term> termList = getTermList(text);
        if (CollectionUtils.isEmpty(termList)) {
            return EMPTY_WORDS_SET;
        }

        Set<String> wordsSet = new HashSet<String>(termList.size());
        for (Term term : termList) {
            String word = term.getName();
            if (StringUtils.isNotBlank(word) && !stopWords.contains(word)) {
                wordsSet.add(word);
            }
        }
        return wordsSet;
    }

    private List<Term> getTermList(String text) {
        Result parseResult = IndexAnalysis.parse(text);
        return parseResult.getTerms();
    }

    @Override
    public List<String> getWordList4Index(String text) {
//        List<Term> termList = getTermList(text);
//        if (CollectionUtils.isEmpty(termList)) {
//            return EMPTY_WORDS_LIST;
//        }
//
//        List<String> result = new ArrayList<String>((int) (termList.size() * 0.75));
//        for (Term term : termList) {
//            String word = term.getName();
//            if (StringUtils.isNotBlank(word) && !stopWords.contains(word)) {
//                result.add(word);
//            }
//        }
        return new ArrayList<String>(getWordSet4Index(text));
    }

    @Override
    public Map<String, Integer> getWordCountMap(String text) {
        List<String> words = getWordList4Index(text);
        Map<String, Integer> result = new HashedMap<String, Integer>(words.size());
        for (int i = 0; i < words.size(); ++i) {
            String word = words.get(i);
            Integer count = result.get(word);
            if (count == null) {
                result.put(word, 1);
            } else {
                result.put(word, 1 + count);
            }
        }
        return result;
    }

    @Override
    public Map<String, List<Integer>> getWordOffsetMap(String text) {
        try {
            List<Term> termList = getTermList(text);
            if (CollectionUtils.isEmpty(termList)) {
                return EMPTY_WORD_OFFSET_MAP;
            }

            Map<String, List<Integer>> wordOffsetMap = new HashedMap<String, List<Integer>>(termList.size());
            for (Term term : termList) {
                String word = term.getName();
                if (StringUtils.isNotBlank(word) && !stopWords.contains(word)) {
                    List<Integer> offsetList = wordOffsetMap.get(word);
                    if (CollectionUtils.isEmpty(offsetList)) {
                        offsetList = new LinkedList<Integer>();
                        wordOffsetMap.put(word, offsetList);
                    }
                    offsetList.add(term.getOffe());
                }
            }
            return wordOffsetMap;
        } catch (Exception e) {
            LoggerManager.error("ParticipleServiceImpl.getWordOffsetMap({})", text,  e);
        }
        return EMPTY_WORD_OFFSET_MAP;
    }
}
