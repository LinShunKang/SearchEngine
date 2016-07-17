package com.lsk.search.model;

/**
 * Created by LinShunkang on 7/3/16.
 */
public class DocSortObj implements Comparable {

    private long docId;

    private int weight;

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }

        DocSortObj sortObj = (DocSortObj) o;
        if (weight > sortObj.weight) {
            return -1;
        } else if (weight < sortObj.weight) {
            return 1;
        }
        return 0;
    }

    public static DocSortObj getInstance(long docId, int weight) {
        DocSortObj result = new DocSortObj();
        result.setDocId(docId);
        result.setWeight(weight);
        return result;
    }

    public static DocSortObj getInstance(Posting posting) {
        DocSortObj result = new DocSortObj();
        result.setDocId(posting.getDocumentId());
        result.setWeight(posting.getWordCount());
        return result;
    }

    public void increaseWeight(int weight) {
        this.weight += weight;
    }
}
