package com.lsk.search.dao;


import com.lsk.database.DataSource;
import com.lsk.search.model.WordPosition;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

/**
 * Created by LinShunkang on 6/27/16.
 */

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
@DAO(catalog = DataSource.CATALOG_SEARCH)
public interface WordPositionDAO {

    String TABLE_NAME = "word_position";

    String INSERT_COLS = "fts_index_id, position, add_time";

    String ALL_COLS = " id, " + INSERT_COLS + ", update_time";


    @ReturnGeneratedKeys
    @SQL("INSERT INTO " + TABLE_NAME +
            "(" + INSERT_COLS + ")" +
            "VALUES(:1.ftsIndexId, :1.position, now())")
    long save(WordPosition wordPosition);

    @SQL("INSERT INTO " + TABLE_NAME +
            "(" + INSERT_COLS + ")" +
            "VALUES(:1.ftsIndexId, :1.position, now())")
    void save(List<WordPosition> wordPosition);

    @SQL("SELECT " + ALL_COLS + " FROM " + TABLE_NAME)
    List<WordPosition> getAll();

}
