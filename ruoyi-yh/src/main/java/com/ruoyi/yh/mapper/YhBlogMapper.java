package com.ruoyi.yh.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.yh.domain.YhBlog;

/**
 * 博客Mapper接口
 * 
 * @author dyh
 * @date 2020-08-18
 */
public interface YhBlogMapper extends BaseMapper<YhBlog>
{
    /**
     * 查询博客
     * 
     * @param blogId 博客ID
     * @return 博客
     */
     YhBlog selectYhBlogById(Long blogId);

    /**
     * 查询博客列表
     * 
     * @param yhBlog 博客
     * @return 博客集合
     */
     List<YhBlog> selectYhBlogList(YhBlog yhBlog);

    /**
     * 查询博客列表
     *
     * @param yhBlog 博客
     * @return 博客集合
     */
    List<YhBlog> selectYhBlogListByUser(YhBlog yhBlog);

    /**
     * 新增博客
     * 
     * @param yhBlog 博客
     * @return 结果
     */
     int insertYhBlog(YhBlog yhBlog);

    /**
     * 修改博客
     * 
     * @param yhBlog 博客
     * @return 结果
     */
     int updateYhBlog(YhBlog yhBlog);

    /**
     * 删除博客
     * 
     * @param blogId 博客ID
     * @return 结果
     */
     int deleteYhBlogById(Long blogId);

    /**
     * 批量删除博客
     * 
     * @param blogIds 需要删除的数据ID
     * @return 结果
     */
     int deleteYhBlogByIds(Long[] blogIds);

    /**
     * 查询包括博客内容的博客信息
     * @param temp
     * @return
     */
    List<Map<String,Object>> selectBlogList(Map<String,Object> temp);

    /**
     * 获取归档博客时间线
     * @return
     */
    List<Map<String,Object>> selectBlogListTimeLine();
}
