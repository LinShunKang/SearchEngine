package com.lsk.search.model;

import java.util.List;

/**
 * Created by LinShunkang on 6/29/16.
 */
public class PostingsList {

    private long wordId;

    private int docCount;

    private int positionsCount;

    private List<Posting> postingList;

    private PostingsList() {
        //empty
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public int getDocCount() {
        return docCount;
    }

    public void setDocCount(int docCount) {
        this.docCount = docCount;
    }

    public int getPositionsCount() {
        return positionsCount;
    }

    public void setPositionsCount(int positionsCount) {
        this.positionsCount = positionsCount;
    }

    public List<Posting> getPostingList() {
        return postingList;
    }

    public void setPostingList(List<Posting> postingList) {
        this.postingList = postingList;
    }

    public void increaseDocCount() {
        docCount++;
    }

    public void addPosting(Posting posting) {
        postingList.add(posting);
        positionsCount++;
    }

    public static PostingsList getInstance(long wordId, int docCount, int positionsCount, List<Posting> postingList) {
        PostingsList result = new PostingsList();
        result.setWordId(wordId);
        result.setDocCount(docCount);
        result.setPositionsCount(positionsCount);
        result.setPostingList(postingList);
        return result;
    }

    @Override
    public String toString() {
        return "PostingsList{" +
                "wordId=" + wordId +
                ", docCount=" + docCount +
                ", positionsCount=" + positionsCount +
                ", postingList=" + postingList +
                '}';
    }
}
