package com.lsk.search.model;

/**
 * Created by LinShunkang on 6/26/16.
 */

import java.util.Date;

/**
 * CREATE TABLE fts_index (
 * id bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT 'id自增列',
 * word_id bigint(20) NOT NULL COMMENT '检索词表ID',
 * doc_id bigint unsigned NOT NULL COMMENT '文档ID',
 * add_time datetime NOT NULL COMMENT '添加时间',
 * update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 * PRIMARY KEY(id)
 * )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='全文检索索引表';
 */
public class FTSIndex {

    private long id;

    private long wordId;

    private long docId;

    private Date addTime;

    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "FTSIndex{" +
                "id=" + id +
                ", wordId=" + wordId +
                ", docId=" + docId +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static FTSIndex getInstance(long wordId, long docId) {
        FTSIndex result = new FTSIndex();
        result.setWordId(wordId);
        result.setDocId(docId);
        return result;
    }
}
