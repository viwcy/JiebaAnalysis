package com.viwcy.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.viwcy.domain.dto.SystemKeyWordDTO;
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
@TableName("system_key_word")
@Builder
public class SystemKeyWord extends Model<SystemKeyWord> implements Serializable {
    private static final long serialVersionUID = -652446268856362971L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String keyword;
    private Long wordFrequency;
    private Date createTime;
    @TableField(update = "now()")
    private Date updateTime;

    //新增
    @TableField(exist = false)
    private Set<SystemKeyWordDTO> keywords;
    //名称集合删除
    @TableField(exist = false)
    private Set<String> words;
    //主键ID集合删除
    @TableField(exist = false)
    private Set<Long> ids;
}
