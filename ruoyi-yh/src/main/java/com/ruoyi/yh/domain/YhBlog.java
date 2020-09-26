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
 * 博客对象 yh_blog
 * 
 * @author dyh
 * @date 2020-08-18
 */
@TableName(value="yh_blog",resultMap="YhBlogResult")
public class YhBlog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 博客文章ID */
    @TableId(value = "blog_id",type = IdType.ASSIGN_ID)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long blogId;

    /** 博客文章名称 */
    @Excel(name = "博客文章名称")
    @TableField("blog_name")
    private String blogName;

    /** 博客状态（0暂存 1个人可见 2公布） */
    @Excel(name = "博客状态", readConverterExp = "0=暂存,1=个人可见,2=公布")
    private String status;

    /** 创建者id */
    @Excel(name = "创建者id")
    @TableField("create_by_id")
    private Long createById;

    /** 创建者id */
    @Excel(name = "更新者id")
    @TableField("update_by_id")
    private Long updateById;

    /** 请求参数 */
    @TableField(exist=false)
    private Map<String, Object> params;

    /** 创建者id */
    @Excel(name = "标签")
    private String tag;

    /** 阅读数 */
    @Excel(name = "阅读数")
    @TableField("read_num")
    private int readNum;

    /** 点赞数 */
    @Excel(name = "点赞数")
    @TableField("like_num")
    private int likeNum;

    public void setBlogId(Long blogId) 
    {
        this.blogId = blogId;
    }

    public Long getBlogId()
    {
        return blogId;
    }

    public void setBlogName(String blogName) 
    {
        this.blogName = blogName;
    }

    @NotBlank(message = "博客文章名称不能为空")
    @Size(min = 0, max = 50, message = "博客文章名称不能超过50个字符")
    public String getBlogName() 
    {
        return blogName;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    @NotBlank(message = "状态不能为空")
    public String getStatus() 
    {
        return status;
    }

    public void setCreateById(Long createById)
    {
        this.createById = createById;
    }

    public Long getCreateById()
    {
        return createById;
    }

    public Long getUpdateById() {
        return updateById;
    }

    public void setUpdateById(Long updateById) {
        this.updateById = updateById;
    }

    @NotBlank(message = "标签不能为空")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("blogId", getBlogId())
            .append("blogName", getBlogName())
            .append("status", getStatus())
            .append("createById", getCreateById())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
             .append("readNum",getReadNum())
             .append("likeNum",getLikeNum())
            .toString();
    }
}
