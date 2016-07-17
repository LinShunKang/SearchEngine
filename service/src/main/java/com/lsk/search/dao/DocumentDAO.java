package com.lsk.search.dao;

import com.lsk.database.DataSource;
import com.lsk.search.model.Document;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

/**
 * Created by LinShunkang on 6/26/16.
 */

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
@DAO(catalog = DataSource.CATALOG_SEARCH)
public interface DocumentDAO {

    String TABLE_NAME = "document";

    String INSERT_COLS = "title, body, add_time";

    String ALL_COLS = " id, " + INSERT_COLS + ", update_time";


    @ReturnGeneratedKeys
    @SQL("INSERT INTO " + TABLE_NAME +
            "(" + INSERT_COLS + ")" +
            "VALUES(:1.title, :1.body, now())")
    long save(Document document);


    @SQL("SELECT " + ALL_COLS +
            " FROM " + TABLE_NAME +
            " WHERE id = :1")
    Document getById(long id);

    @SQL("SELECT " + ALL_COLS +
            " FROM " + TABLE_NAME +
            " WHERE id in (:1)")
    List<Document> getByIdList(List<Long> idList);
}
