package com.viwcy.domain.dto;

import com.huaban.analysis.jieba.JiebaSegmenter;
import lombok.Data;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class JiebaReqDTO {

    private String content;
    private JiebaSegmenter.SegMode segMode = JiebaSegmenter.SegMode.INDEX;
    private Integer topN = 10;
}
