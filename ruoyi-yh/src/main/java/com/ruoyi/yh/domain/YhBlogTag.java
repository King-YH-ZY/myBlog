package com.ruoyi.yh.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * 博客文章分类标签 yh_blog_tag
 *
 * @author dyh
 * @date 2020-08-26
 */
@TableName(value="yh_blog_tag",resultMap="YhBlogTagResult")
public class YhBlogTag extends BaseEntity
{

    /** 文章分类标签id */
    @TableId(value = "tag_id",type = IdType.AUTO)
    private Long tagId;

    /** 文章分类标签名称 */
    @TableField("tag_name")
    private String name;

    /** 创建者id */
    @TableField("create_by_id")
    private Long createById;

    /** 请求参数 */
    @TableField(exist=false)
    private Map<String, Object> params;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @NotBlank(message = "标签名称不能为空")
    @Size(min = 0, max = 14, message = "标签名称不能超过14个字符")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreateById() {
        return createById;
    }

    public void setCreateById(Long createById) {
        this.createById = createById;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tagId", getTagId())
            .append("name", getName())
            .append("createById", getCreateById())
             .append("createBy",getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
