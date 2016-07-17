package com.lsk.search.service.impl;

import com.lsk.log.LoggerManager;
import com.lsk.search.model.*;
import com.lsk.search.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LinShunkang on 7/2/16.
 */
public class SearchEngineImpl implements SearchEngine, InitializingBean {


    private static final List<Long> EMPTY_ID_LIST = Collections.unmodifiableList(new ArrayList<Long>(0));

    private ParticipleService participleService;

    private WordPositionService wordPositionService;

    private WordService wordService;

    private FTSIndexService ftsIndexService;

    private ConcurrentMap<String, PostingsList> invertedIndexMap;

    private Set<Long> indexedDocIdSet;

    private Map<String, Long> wordIdMap;

    private Map<Long, String> wordMap;

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8, 1, TimeUnit.HOURS, new LinkedBlockingDeque<Runnable>(100), new ThreadFactory() {
        AtomicInteger num = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "SearchEngineImpl-thread-" + num.getAndIncrement());
        }
    }, new ThreadPoolExecutor.CallerRunsPolicy());


    @Override
    public boolean addSearchIndex(long docId, String content) {
        try {
            Map<String, List<Integer>> wordOffsetMap = participleService.getWordOffsetMap(content);
            if (MapUtils.isEmpty(wordOffsetMap)) {
                LoggerManager.info("wordOffsetMap isEmpty!");
                return true;
            }

            for (Map.Entry<String, List<Integer>> entry : wordOffsetMap.entrySet()) {
                String word = entry.getKey();
                List<Integer> offsetList = entry.getValue();
                PostingsList postingsList = invertedIndexMap.get(word);
                if (postingsList == null) {
                    postingsList = getEmptyPostingsList();
                    postingsList.setWordId(getWordId(word));
                    invertedIndexMap.put(word, postingsList);
                }
                Posting posting = Posting.getInstance(docId, offsetList);
                postingsList.getPostingList().add(posting);

                //TODO:对postingList进行升序排序

                processDBAsync(word, docId, offsetList);
            }
            return true;
        } catch (Exception e) {
            LoggerManager.error("SearchEngineImpl.addSearchIndex({}, {})", docId, content, e);
        }
        return false;
    }

    private PostingsList getEmptyPostingsList() {
        return PostingsList.getInstance(0, 1, 1, new LinkedList<Posting>());
    }

    private void processDBAsync(final String word, final long docId, final List<Integer> offsetList) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                long wordId = getWordId(word);
                FTSIndex ftsIndex = FTSIndex.getInstance(wordId, docId);
                boolean saveFTSIndex = ftsIndexService.save(ftsIndex);
                if (saveFTSIndex) {
                    saveWordOffset(ftsIndex.getId(), offsetList);
                }
            }
        });
    }

    private boolean saveWordOffset(long ftsIndexId, List<Integer> offsetList) {
        List<WordPosition> wordPositions = new ArrayList<WordPosition>(offsetList.size());
        for (Integer offset : offsetList) {
            WordPosition wordPosition = WordPosition.getInstance(ftsIndexId, offset);
            wordPositions.add(wordPosition);
        }
        return wordPositionService.save(wordPositions);
    }

    private long getWordId(String word) {
        Long wordIdFromMap = wordIdMap.get(word);
        if (wordIdFromMap != null) {
            return wordIdFromMap;
        } else {
            long wordId = wordService.getId(word);
            if (wordId > 0L) {
                wordIdMap.put(word, wordId);
                return wordId;
            }
            return 0;
        }
    }

    @Override
    public int dropIdSearchIndex(long docId) {
        try {

        } catch (Exception e) {
            LoggerManager.error("SearchEngineImpl.dropIdSearchIndex({})", docId, e);
        }
        return 0;
    }

    @Override
    public int dropWordSearchIndex(String word) {
        try {

        } catch (Exception e) {
            LoggerManager.error("SearchEngineImpl.dropWordSearchIndex({})", word, e);
        }
        return 0;
    }

    @Override
    public List<Long> search(String sentence, int count) {
        try {
            List<String> wordList = participleService.getWordList4Index(sentence);
            if (CollectionUtils.isEmpty(wordList)) {
                return EMPTY_ID_LIST;
            }
//            wordList = sortByDocCount(wordList);
            Map<Long, DocSortObj> docIdCountMap = new HashedMap<Long, DocSortObj>();
            for (int i = 0; i < wordList.size(); ++i) {
                String word = wordList.get(i);
                PostingsList postingsList = invertedIndexMap.get(word);
                if (postingsList != null) {
                    List<Posting> postings = postingsList.getPostingList();
                    if (CollectionUtils.isNotEmpty(postings)) {
                        for (Posting posting : postings) {
                            DocSortObj wordCount = docIdCountMap.get(posting.getDocumentId());
                            if (wordCount == null) {
                                docIdCountMap.put(posting.getDocumentId(), DocSortObj.getInstance(posting));
                            } else {
                                wordCount.increaseWeight(posting.getWordCount());
                            }
                        }
                    }
                }
            }

           return getSortedDocIdList(docIdCountMap);
        } catch (Exception e) {
            LoggerManager.error("SearchEngineImpl.search({}, {})", sentence, count, e);
        }
        return EMPTY_ID_LIST;
    }

    private List<Long> getSortedDocIdList(Map<Long, DocSortObj> docIdCountMap) {
        List<DocSortObj> docSortObjs = new ArrayList<DocSortObj>(docIdCountMap.values());
        Collections.sort(docSortObjs);
        List<Long> result = new ArrayList<Long>(docSortObjs.size());
        for (int i = 0; i < docSortObjs.size(); ++i) {
            result.add(docSortObjs.get(i).getDocId());
        }
        return result;
    }

//    private List<String> sortByDocCount(List<String> wordList) {
//        List<WordSortObj> sortObjs = new ArrayList<WordSortObj>(wordList.size());
//        for (int i = 0; i < wordList.size(); ++i) {
//            String word = wordList.get(i);
//            PostingsList postingsList = invertedIndexMap.get(word);
//            if (postingsList != null) {
//                int docCount = postingsList.getDocCount();
//                sortObjs.add(WordSortObj.getInstance(word, docCount));
//            }
//        }
//
//        Collections.sort(sortObjs);
//        List<String> result = new ArrayList<String>(sortObjs.size());
//        for (int i = 0; i < sortObjs.size(); ++i) {
//            result.add(sortObjs.get(i).getWord());
//        }
//        return wordList;
//    }


    public void setParticipleService(ParticipleService participleService) {
        this.participleService = participleService;
    }

    public void setWordPositionService(WordPositionService wordPositionService) {
        this.wordPositionService = wordPositionService;
    }

    public void setWordService(WordService wordService) {
        this.wordService = wordService;
    }

    public void setFtsIndexService(FTSIndexService ftsIndexService) {
        this.ftsIndexService = ftsIndexService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(participleService, "participleService is required!");
        Assert.notNull(wordPositionService, "wordPositionService is required!");
        Assert.notNull(wordService, "wordService is required!");
        Assert.notNull(ftsIndexService, "ftsIndexService is required!");
        buildSearchIndex();
    }

    private void buildSearchIndex() {
        initWordIdMap();
        initInvertedIndexMap();
    }

    private void initWordIdMap() {
        wordIdMap = new ConcurrentHashMap<String, Long>(1024);
        wordMap = new ConcurrentHashMap<Long, String>(1024);
        List<Word> allWordList = wordService.getAll();
        if (CollectionUtils.isEmpty(allWordList)) {
            LoggerManager.info("wordService.getAll() isEmpty!");
            return;
        }

        for (int i = 0; i < allWordList.size(); ++i) {
            Word word = allWordList.get(i);
            wordIdMap.put(word.getWord(), word.getId());
            wordMap.put(word.getId(), word.getWord());
        }
    }

    private void initInvertedIndexMap() {
        invertedIndexMap = new ConcurrentHashMap<String, PostingsList>(1024);

        List<FTSIndex> allFTSIndex = ftsIndexService.getAllFTSIndex();
        if (CollectionUtils.isEmpty(allFTSIndex)) {
            LoggerManager.info("ftsIndexService.getAllFTSIndex() isEmpty!");
            return;
        }

        Map<Long, List<Integer>> wordPositionMap = wordPositionService.getAllWordPosMap();
        for (int i = 0; i < allFTSIndex.size(); ++i) {
            FTSIndex ftsIndex = allFTSIndex.get(i);
            String word = wordMap.get(ftsIndex.getWordId());
            PostingsList postingsList = invertedIndexMap.get(word);
            if (postingsList == null) {
                postingsList = getEmptyPostingsList();
                postingsList.setWordId(getWordId(word));
                invertedIndexMap.put(word, postingsList);
            } else {
                postingsList.increaseDocCount();
            }

            Posting posting = Posting.getInstance(ftsIndex.getDocId(), wordPositionMap.get(ftsIndex.getId()));
            postingsList.addPosting(posting);
        }
    }
}
