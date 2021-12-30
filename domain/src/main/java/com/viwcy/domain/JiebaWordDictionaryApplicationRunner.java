package com.viwcy.domain;

import com.huaban.analysis.jieba.WordDictionary;
import com.viwcy.domain.entity.SystemKeyWord;
import com.viwcy.domain.service.JiebaWordDictionaryManager;
import com.viwcy.domain.service.SystemKeyWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Component
public class JiebaWordDictionaryApplicationRunner implements ApplicationRunner {

    @Autowired
    private SystemKeyWordService keyWordService;
    @Autowired
    private JiebaWordDictionaryManager jiebaWordDictionaryManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Set<SystemKeyWord> set = keyWordService.queryAll();
        HashSet<String> keywords = new HashSet<>();
        set.forEach(s -> {
            keywords.add(s.getKeyword() + " " + s.getWordFrequency());
        });
        //加载结巴词库
        WordDictionary instance = WordDictionary.getInstance();
        instance.load();
        //加载自定义热词
        jiebaWordDictionaryManager.addKeyWords(keywords);
        System.err.println("Jie ba dynamic loading of thesaurus is complete");
    }
}
