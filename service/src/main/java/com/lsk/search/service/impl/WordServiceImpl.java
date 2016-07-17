package com.lsk.search.service.impl;

import com.lsk.log.LoggerManager;
import com.lsk.search.dao.WordDAO;
import com.lsk.search.model.Word;
import com.lsk.search.service.WordService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by LinShunkang on 7/3/16.
 */
public class WordServiceImpl implements WordService, InitializingBean {

    private WordDAO wordDAO;

    @Override
    public boolean save(Word word) {
        try {
            long id = wordDAO.save(word);
            if (id > 0L) {
                word.setId(id);
                return true;
            }
        } catch (Exception e) {
            LoggerManager.error("save({})", word, e);
        }
        return false;
    }

    @Override
    public Word get(String word) {
        try {
            return wordDAO.get(word);
        } catch (Exception e) {
            LoggerManager.error("get({})", word, e);
        }
        return null;
    }

    @Override
    public long getId(String w) {
        Word wordFromDB = get(w);
        if (wordFromDB != null) {
            return wordFromDB.getId();
        }

        Word word = Word.getInstance(w);
        save(word);
        return word.getId();
    }

    @Override
    public List<Word> getAll() {
        try {
            return wordDAO.getAll();
        } catch (Exception e) {
            LoggerManager.error("getAll({})", e);
        }
        return null;
    }

    public void setWordDAO(WordDAO wordDAO) {
        this.wordDAO = wordDAO;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(wordDAO, "wordDAO is required!");
    }

}
