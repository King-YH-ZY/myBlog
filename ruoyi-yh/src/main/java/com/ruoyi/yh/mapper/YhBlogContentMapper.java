package com.ruoyi.yh.mapper;

import com.ruoyi.yh.domain.YhBlog;
import com.ruoyi.yh.domain.YhBlogContent;

import java.util.List;

/**
 * 博客文章内容Mapper接口
 * 
 * @author dyh
 * @date 2020-08-19
 */
public interface YhBlogContentMapper
{

    /**
     * 查询博客文章内容
     * @param blogContentId
     * @return
     */
     YhBlogContent selectYhBlogContentById(Long blogContentId);

    /**
     * 通过博客id查询博客文章内容
     * @param blogId
     * @return
     */
     YhBlogContent selectYhBlogContentByBlogId(Long blogId);

    /**
     * 新增博客文章内容
     * @param yhBlogContent
     * @return
     */
     int insertYhBlogContent(YhBlogContent yhBlogContent);

    /**
     * 修改博客文章内容
     * @param yhBlogContent
     * @return
     */
     int updateYhBlogContent(YhBlogContent yhBlogContent);

    /**
     * 删除博客文章内容
     * @param contentId
     * @return
     */
     int deleteYhBlogContent(Long contentId);

    /**
     * 通过博客id删除博客文章内容
     * @param blogId
     * @return
     */
     int deleteYhBlogContentByBlogId(Long blogId);

    /**
     * 通过博客id删除博客文章内容
     * @param blogId
     * @return
     */
    int deleteYhBlogContentByBlogIds(Long[] blogId);


}
