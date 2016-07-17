package com.lsk.search.model;

/**
 * Created by LinShunkang on 6/29/16.
 */

import java.util.Date;

/**
 * CREATE TABLE word (
 * id bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT 'id自增列',
 * word varchar(12) NOT NULL COMMENT '检索词',
 * add_time datetime NOT NULL COMMENT '添加时间',
 * update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 * PRIMARY KEY(id)
 * )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='检索词表';
 */
public class Word {

    private long id;

    private String word;

    private Date addTime;

    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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
        return "Word{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static Word getInstance(String word) {
        Word result = new Word();
        result.setWord(word);
        return result;
    }
}
