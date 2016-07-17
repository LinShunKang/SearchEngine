package com.lsk.search.service;

import com.lsk.search.model.FTSIndex;

import java.util.List;

/**
 * Created by LinShunkang on 6/27/16.
 */
public interface FTSIndexService {

    boolean save(FTSIndex ftsIndex);

    List<FTSIndex> getAllFTSIndex();
}
