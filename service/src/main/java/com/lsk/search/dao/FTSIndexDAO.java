package com.lsk.search.dao;

/**
 * Created by LinShunkang on 6/27/16.
 */

import com.lsk.database.DataSource;
import com.lsk.search.model.FTSIndex;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

/**
 * CREATE TABLE fts_index (
 * id bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT 'id自增列',
 * word_id bigint(20) NOT NULL COMMENT '检索词ID',
 * doc_id bigint unsigned NOT NULL COMMENT '文档ID',
 * add_time datetime NOT NULL COMMENT '添加时间',
 * update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 * PRIMARY KEY(id),
 * UNIQUE KEY wordid_docid_index(word_id, doc_id)
 * )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='全文检索索引表';
 */
@DAO(catalog = DataSource.CATALOG_SEARCH)
public interface FTSIndexDAO {

    String TABLE_NAME = "fts_index";

    String INSERT_COLS = "word_id, doc_id, add_time";

    String ALL_COLS = " id, " + INSERT_COLS + ", update_time";


    @ReturnGeneratedKeys
    @SQL("INSERT IGNORE INTO " + TABLE_NAME +
            "(" + INSERT_COLS + ")" +
            "VALUES(:1.wordId, :1.docId, now())")
    long save(FTSIndex ftsIndex);


    @SQL("SELECT " + ALL_COLS +
            " FROM " + TABLE_NAME +
            " WHERE id = :1")
    FTSIndex getById(long id);

    @SQL("SELECT " + ALL_COLS +
            " FROM " + TABLE_NAME +
            " WHERE doc_id = :1 AND word_id = :2")
    FTSIndex getByDocIdAndWordId(long docId, long wordId);

    @SQL("SELECT " + ALL_COLS + " FROM " + TABLE_NAME)
    List<FTSIndex> getAll();
}
