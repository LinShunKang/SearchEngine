package com.lsk.search.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LinShunkang on 6/29/16.
 */

//倒排项
public class Posting {

    private long documentId;

    private int wordCount;

    private List<Integer> offsetList;

    private Posting() {
        //empty!
    }

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public List<Integer> getOffsetList() {
        return offsetList;
    }

    public void setOffsetList(List<Integer> offsetList) {
        this.offsetList = offsetList;
    }

    public static Posting getInstance(long documentId) {
        Posting result = new Posting();
        result.setDocumentId(documentId);
        result.setOffsetList(new ArrayList<Integer>(32));
        return result;
    }

    public static Posting getInstance(long documentId, List<Integer> offsetList) {
        if (offsetList == null) {
            return getInstance(documentId);
        }

        Posting result = new Posting();
        result.setDocumentId(documentId);
        result.setWordCount(offsetList.size());
        result.setOffsetList(offsetList);
        return result;
    }

    @Override
    public String toString() {
        return "Posting{" +
                "documentId=" + documentId +
                ", offsetList=" + offsetList +
                '}';
    }
}
