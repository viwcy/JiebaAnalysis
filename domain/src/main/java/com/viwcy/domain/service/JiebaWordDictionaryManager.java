package com.viwcy.domain.service;

import com.huaban.analysis.jieba.WordDictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Component
@Slf4j
public class JiebaWordDictionaryManager {

    public void addKeyWords(Set<String> keyWords) {

        if (CollectionUtils.isEmpty(keyWords)) {
            log.warn("keyWords is empty!");
            return;
        }

        WordDictionary wordDictionary = WordDictionary.getInstance();
        keyWords.forEach(wordDictionary::refresh);
        log.info("Add to WordDictionary is complete , keywords size = {} ", keyWords.size());
    }

    public void disableKeyWords(Set<String> words) {

        if (CollectionUtils.isEmpty(words)) {
            log.warn("words is empty!");
            return;
        }
        WordDictionary wordDictionary = WordDictionary.getInstance();
        words.forEach(wordDictionary::disableWord);
        log.info("Disable to WordDictionary is complete , keywords size = {} ", words.size());
    }
}
