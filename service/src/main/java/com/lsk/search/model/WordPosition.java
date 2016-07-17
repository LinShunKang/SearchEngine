package com.lsk.search.model;

/**
 * Created by LinShunkang on 6/26/16.
 */

import java.util.Date;

/**
 * CREATE TABLE word_position (
 * id bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id自增列',
 * fts_index_id bigint(20) unsigned NOT NULL COMMENT 'fts_index表id',
 * position smallint(5) unsigned NOT NULL COMMENT '检索词在文档中的位置',
 * add_time datetime NOT NULL COMMENT '添加时间',
 * update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 * PRIMARY KEY(id),
 * KEY fts_index_id(fts_index_id)
 * )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='检索词位置记录表';
 */
public class WordPosition {

    private long id;

    private long ftsIndexId;

    private int position;

    private Date addTime;

    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFtsIndexId() {
        return ftsIndexId;
    }

    public void setFtsIndexId(long ftsIndexId) {
        this.ftsIndexId = ftsIndexId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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
        return "WordPosition{" +
                "id=" + id +
                ", ftsIndexId=" + ftsIndexId +
                ", position=" + position +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static WordPosition getInstance(long ftsIndexId, int position) {
        WordPosition result = new WordPosition();
        result.setFtsIndexId(ftsIndexId);
        result.setPosition(position);
        return result;
    }
}
