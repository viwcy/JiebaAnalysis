package com.viwcy.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.viwcy.domain.entity.SystemStopWord;

import java.util.Collection;
import java.util.Set;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
public interface SystemStopWordService extends IService<SystemStopWord> {

    Set<SystemStopWord> queryAll();

    void addStopWords(Collection<String> stopwords);

    void deleteByNames(Collection<String> stopwords);

    void deleteByIds(Collection<Long> ids);
}
