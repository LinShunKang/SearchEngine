package com.lsk.search.model;

/**
 * Created by LinShunkang on 6/26/16.
 */

import java.util.Date;

/**
 * CREATE TABLE document (
 * id bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT 'id自增列',
 * title varchar(128) NOT NULL COMMENT '文档标题',
 * body text NOT NULL '文档内容',
 * add_time datetime NOT NULL COMMENT '添加时间',
 * update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 * PRIMARY KEY(id)
 * )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档记录表';
 */
public class Document {

    private long id;

    private String title;

    private String body;

    private Date addTime;

    private Date updateTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
        return "Document{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static Document getInstance(String title, String body) {
        Document result = new Document();
        result.setTitle(title);
        result.setBody(body);
        return result;
    }
}
