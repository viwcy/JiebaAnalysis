package com.viwcy.domain.dto;

import com.huaban.analysis.jieba.JiebaSegmenter;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class JiebaReqDTO {

    @NotBlank(message = "内容不能为空")
    private String content;
    private JiebaSegmenter.SegMode segMode = JiebaSegmenter.SegMode.INDEX;
    private Integer topN = 10;
}
