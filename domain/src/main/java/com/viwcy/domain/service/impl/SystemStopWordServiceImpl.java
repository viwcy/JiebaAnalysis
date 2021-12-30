package com.viwcy.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.viwcy.domain.entity.SystemStopWord;
import com.viwcy.domain.mapper.SystemStopWordMapper;
import com.viwcy.domain.service.SystemStopWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Service
@Slf4j
public class SystemStopWordServiceImpl extends ServiceImpl<SystemStopWordMapper, SystemStopWord> implements SystemStopWordService {

    @Override
    public Set<SystemStopWord> queryAll() {
        return new HashSet<>(this.list());
    }

    @Override
    public void addStopWords(Collection<String> stopwords) {
        if (CollectionUtils.isEmpty(stopwords)) {
            log.error("Add stopwords is empty");
            return;
        }
        HashSet<SystemStopWord> set = new HashSet<>();
        for (String stopword : stopwords) {
            LambdaQueryWrapper<SystemStopWord> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SystemStopWord::getStopword, stopword);
            SystemStopWord one = this.getOne(wrapper);
            if (Optional.ofNullable(one).isPresent()) {
                continue;
            }
            SystemStopWord build = SystemStopWord.builder().stopword(stopword).build();
            set.add(build);
        }
        if (CollectionUtils.isEmpty(set)) {
            return;
        }
        this.saveBatch(set);
    }

    @Override
    public void deleteByNames(Collection<String> stopwords) {
        if (CollectionUtils.isEmpty(stopwords)) {
            log.error("Delete stopwords is empty");
            return;
        }
        HashSet<SystemStopWord> set = new HashSet<>();
        for (String stopword : stopwords) {
            LambdaQueryWrapper<SystemStopWord> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SystemStopWord::getStopword, stopword);
            SystemStopWord one = this.getOne(wrapper);
            if (!Optional.ofNullable(one).isPresent()) {
                continue;
            }
            set.add(one);
        }
        if (CollectionUtils.isEmpty(set)) {
            return;
        }
        this.removeByIds(set.stream().map(SystemStopWord::getId).collect(Collectors.toSet()));
    }

    @Override
    public void deleteByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.error("Delete ids is empty");
            return;
        }
        this.removeByIds(ids);
    }
}
