package com.viwcy.domain.service;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.qianxinyao.analysis.jieba.keyword.Keyword;
import com.qianxinyao.analysis.jieba.keyword.TFIDFAnalyzer;
import com.viwcy.domain.dto.JiebaReqDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Component
public class JiebaAnalysisHandle implements InitializingBean {

    private JiebaSegmenter jiebaSegmenter;

    /**
     * 提取关键词
     */
    public Set<String> analysisKeyWord(JiebaReqDTO dto) {

        if (StringUtils.isBlank(dto.getContent())) {
            return new HashSet<>();
        }

        TFIDFAnalyzer tfidfAnalyzer = new TFIDFAnalyzer();
        List<Keyword> list = tfidfAnalyzer.analyze(dto.getContent(), dto.getTopN());

        return list.stream().map(Keyword::getName).collect(Collectors.toSet());
    }

    public List<String> analysis(JiebaReqDTO dto) {

        if (StringUtils.isBlank(dto.getContent())) {
            return new ArrayList<>();
        }

        if (Objects.isNull(dto.getSegMode())) {
            return new ArrayList<>();
        }

        List<SegToken> process = jiebaSegmenter.process(dto.getContent(), dto.getSegMode());

        return process.stream()
                //过滤停词
//                .filter(s -> !stopWordHandle._stopWords.contains(s.word) && s.word.length() > 1 && !this.isNum(s.word))
                .map(s -> s.word)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.jiebaSegmenter = new JiebaSegmenter();
    }

    /**
     * 判断是否为纯数字
     */
    private boolean isNum(String word) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }
}
