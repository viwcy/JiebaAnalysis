package com.viwcy.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.viwcy.domain.dto.SystemKeyWordDTO;
import com.viwcy.domain.entity.SystemKeyWord;
import com.viwcy.domain.mapper.SystemKeyWordMapper;
import com.viwcy.domain.service.JiebaWordDictionaryManager;
import com.viwcy.domain.service.SystemKeyWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Service
@Slf4j
public class SystemKeyWordServiceImpl extends ServiceImpl<SystemKeyWordMapper, SystemKeyWord> implements SystemKeyWordService {

    @Autowired
    private JiebaWordDictionaryManager jiebaWordDictionaryManager;

    @Override
    public Set<SystemKeyWord> queryAll() {
        return new HashSet<>(this.list());
    }

    @Override
    public void addKeyWords(Set<SystemKeyWordDTO> keyWords) {
        if (CollectionUtils.isEmpty(keyWords)) {
            log.error("Add words is empty");
            return;
        }
        HashSet<SystemKeyWord> set = new HashSet<>();
        for (SystemKeyWordDTO keyWord : keyWords) {
            LambdaQueryWrapper<SystemKeyWord> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SystemKeyWord::getKeyword, keyWord.getKeyword());
            SystemKeyWord one = this.getOne(wrapper);
            if (Optional.ofNullable(one).isPresent()) {
                one.setWordFrequency(keyWord.getWordFrequency());
                this.updateById(one);
                continue;
            }
            SystemKeyWord build = SystemKeyWord.builder().keyword(keyWord.getKeyword()).wordFrequency(keyWord.getWordFrequency()).build();
            set.add(build);
        }
        if (CollectionUtils.isEmpty(set)) {
            return;
        }
        this.saveBatch(set);
    }

    @Override
    public SystemKeyWord addKeyWord(SystemKeyWordDTO keyWord) {
        LambdaQueryWrapper<SystemKeyWord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemKeyWord::getKeyword, keyWord.getKeyword());
        SystemKeyWord one = this.getOne(wrapper);
        if (Objects.nonNull(one)) {
            if (!one.getWordFrequency().equals(keyWord.getWordFrequency())) {
                one.setWordFrequency(keyWord.getWordFrequency());
                this.updateById(one);
            }
        } else {
            SystemKeyWord build = SystemKeyWord.builder().keyword(keyWord.getKeyword()).wordFrequency(keyWord.getWordFrequency()).build();
            this.save(build);
            one = build;
        }
        //刷新热词
        SystemKeyWord finalOne = one;
        HashSet<String> set = new HashSet<String>() {{
            add(finalOne.getKeyword() + " " + finalOne.getWordFrequency());
        }};
        jiebaWordDictionaryManager.addKeyWords(set);
        return one;
    }

    @Override
    public void deleteByNames(Set<String> words) {
        if (CollectionUtils.isEmpty(words)) {
            log.error("Delete words is empty");
            return;
        }
        ArrayList<SystemKeyWord> list = new ArrayList<>();
        for (String word : words) {
            LambdaQueryWrapper<SystemKeyWord> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SystemKeyWord::getKeyword, word);
            SystemKeyWord one = this.getOne(wrapper);
            if (!Optional.ofNullable(one).isPresent()) {
                continue;
            }
            list.add(one);
        }
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        this.removeByIds(list.stream().map(SystemKeyWord::getId).collect(Collectors.toSet()));
    }

    @Override
    public void deleteByIds(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.error("Delete ids is empty");
            return;
        }
        this.removeByIds(ids);
        //TODO  remove SYS_KEY_WORDS
    }

    @Override
    public void addKeyWordsAndRefresh(Set<SystemKeyWordDTO> keyWords) {
        //保存词典
        addKeyWords(keyWords);
        HashSet<String> set = new HashSet<>();
        for (SystemKeyWordDTO keyword : keyWords) {
            set.add(keyword.getKeyword() + " " + keyword.getWordFrequency());
        }
        //刷新热词
        jiebaWordDictionaryManager.addKeyWords(set);
    }
}
