package com.ruoyi.yh.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 博客对象 yh_blog
 * 
 * @author dyh
 * @date 2020-08-18
 */
public class YhBlogContent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 博客内容ID */
    private Long contentId;

    /** 博客文章ID */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long yhBlogId;

    /** 博客文章内容*/
    private String content;

    /** 博客文章Html内容*/
    private String contentHtml;

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getYhBlogId() {
        return yhBlogId;
    }

    public void setYhBlogId(Long yhBlogId) {
        this.yhBlogId = yhBlogId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("contentId", getContentId())
                .append("yhBlogId", getYhBlogId())
                .append("content", getContent())
                .append("contentHtml", getContentHtml())
                .toString();
    }
}
