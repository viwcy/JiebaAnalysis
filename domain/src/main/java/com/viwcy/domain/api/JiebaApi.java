package com.viwcy.domain.api;

import com.viwcy.domain.dto.JiebaReqDTO;
import com.viwcy.domain.service.JiebaAnalysisHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@RestController
@RequestMapping("/jieba")
public class JiebaApi {


    @Autowired
    private JiebaAnalysisHandle jiebaAnalysisHandle;

    @PostMapping("/analysis")
    public Collection<String> analysis(@RequestBody JiebaReqDTO dto) {
        return jiebaAnalysisHandle.analysis(dto);
    }
}
