package com.ruoyi.yh.service;

import com.ruoyi.yh.domain.YhBlog;
import com.ruoyi.yh.domain.YhBlogContent;

import java.util.List;

/**
 * 博客文章Service接口
 * 
 * @author dyh
 * @date 2020-08-19
 */
public interface IYhBlogContentService
{
    /**
     * 查询博客文章内容
     * @param blogContentId
     * @return
     */
    public YhBlogContent selectYhBlogContentById(Long blogContentId);

    /**
     * 通过博客id查询博客文章内容
     * @param blogId
     * @return
     */
    public YhBlogContent selectYhBlogContentByBlogId(Long blogId);

    /**
     * 新增博客文章内容
     * @param yhBlogContent
     * @return
     */
    public int insertYhBlogContent(YhBlogContent yhBlogContent);

    /**
     * 修改博客文章内容
     * @param yhBlogContent
     * @return
     */
    public int updateYhBlogContent(YhBlogContent yhBlogContent);

    /**
     * 删除博客文章内容
     * @param contentId
     * @return
     */
    public int deleteYhBlogContent(Long contentId);

    /**
     * 通过博客id删除博客文章内容
     * @param blogId
     * @return
     */
    public int deleteYhBlogContentByBlogId(Long blogId);
}
