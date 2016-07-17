package com.lsk.search.model;

/**
 * Created by LinShunkang on 7/3/16.
 */
public class WordSortObj implements Comparable {

    private String word;

    private int docCount;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getDocCount() {
        return docCount;
    }

    public void setDocCount(int docCount) {
        this.docCount = docCount;
    }

    public static WordSortObj  getInstance(String word, int docCount) {
        WordSortObj result = new WordSortObj();
        result.setDocCount(docCount);
        result.setWord(word);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        WordSortObj sortObj = (WordSortObj) o;
        if (docCount > sortObj.docCount) {
            return 1;
        } else if (docCount < sortObj.docCount) {
            return -1;
        }
        return 0;
    }
}
