package com.lsk.search.dao;

/**
 * Created by LinShunkang on 6/29/16.
 */

import com.lsk.database.DataSource;
import com.lsk.search.model.Word;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

/**
 * CREATE TABLE word (
 * id bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT 'id自增列',
 * word varchar(12) NOT NULL COMMENT '检索词',
 * add_time datetime NOT NULL COMMENT '添加时间',
 * update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 * PRIMARY KEY(id)
 * )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='检索词表';
 */
@DAO(catalog = DataSource.CATALOG_SEARCH)
public interface WordDAO {

    String TABLE_NAME = "word";

    String INSERT_COLS = "word, add_time";

    String ALL_COLS = " id, " + INSERT_COLS + ", update_time";


    @ReturnGeneratedKeys
    @SQL("INSERT INTO " + TABLE_NAME +
            "(" + INSERT_COLS + ")" +
            "VALUES(:1.word, now())")
    long save(Word word);

    @SQL("SELECT " + ALL_COLS + " FROM " + TABLE_NAME)
    List<Word> getAllWords();

    @SQL("SELECT " + ALL_COLS + " FROM " + TABLE_NAME + " WHERE word = :1")
    Word get(String word);

    @SQL("SELECT " + ALL_COLS + " FROM " + TABLE_NAME)
    List<Word> getAll();
}
