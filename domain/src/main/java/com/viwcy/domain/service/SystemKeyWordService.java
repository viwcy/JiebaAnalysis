package com.viwcy.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.viwcy.domain.dto.SystemKeyWordDTO;
import com.viwcy.domain.entity.SystemKeyWord;

import java.util.Set;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
public interface SystemKeyWordService extends IService<SystemKeyWord> {

    Set<SystemKeyWord> queryAll();

    void addKeyWords(Set<SystemKeyWordDTO> keyWords);

    SystemKeyWord addKeyWord(SystemKeyWordDTO keyWord);

    void deleteByNames(Set<String> words);

    void deleteByIds(Set<Long> ids);

    void addKeyWordsAndRefresh(Set<SystemKeyWordDTO> keyWords);
}
