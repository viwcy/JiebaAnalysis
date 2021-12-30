package com.viwcy.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("system_stop_word")
@Builder
public class SystemStopWord extends Model<SystemStopWord> implements Serializable {
    private static final long serialVersionUID = 7770299903392270951L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String stopword;
    private Date createTime;

    @TableField(exist = false)
    private Set<String> stopwords;
    @TableField(exist = false)
    private Set<Long> ids;
}
