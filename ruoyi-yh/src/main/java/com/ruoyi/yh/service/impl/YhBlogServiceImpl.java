package com.ruoyi.yh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysDictType;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.yh.domain.YhBlogContent;
import com.ruoyi.yh.mapper.YhBlogContentMapper;
import com.ruoyi.yh.utils.Blog;
import com.ruoyi.yh.utils.BlogRedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.yh.mapper.YhBlogMapper;
import com.ruoyi.yh.domain.YhBlog;
import com.ruoyi.yh.service.IYhBlogService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * 博客Service业务层处理
 * 
 * @author dyh
 * @date 2020-08-18
 */
@Service
public class YhBlogServiceImpl implements IYhBlogService 
{
    @Autowired
    private YhBlogMapper yhBlogMapper;
    @Autowired
    private YhBlogContentMapper yhBlogContentMapper;

    /**
     * 项目启动时，初始化博客到缓存
     */
    @PostConstruct
    public void init()
    {
        this.updateBlogListCache();
        this.updateBlogTimeLineCache();
    }

    /**
     * 查询博客
     * 
     * @param blogId 博客ID
     * @return 博客
     */
    @Override
    public YhBlog selectYhBlogById(Long blogId)
    {
        return yhBlogMapper.selectYhBlogById(blogId);
    }


    /**
     * 查询博客列表
     * 
     * @param yhBlog 博客
     * @return 博客
     */
    @Override
    public List<YhBlog> selectYhBlogList(YhBlog yhBlog)
    {
        return yhBlogMapper.selectYhBlogList(yhBlog);
    }

    /**
     * 查询博客列表
     *
     * @param yhBlog 博客
     * @return 博客
     */
    @Override
    public List<YhBlog> selectYhBlogListByUser(YhBlog yhBlog)
    {
        return yhBlogMapper.selectYhBlogListByUser(yhBlog);
    }

    /**
     * 新增博客
     * @param blog
     * @return
     */
    @Override
    @Transactional
    public int insertYhBlog(Blog blog)
    {
        int readNum =10+(int)(Math.random()*25);
        int likeNum =1+(int)(Math.random()*10);
        LoginUser loginUser = SecurityUtils.getLoginUser();
        YhBlog yhBlog = blog.getYhBlog();
        YhBlogContent yhBlogContent = blog.getYhBlogContent();

        yhBlog.setCreateTime(DateUtils.getNowDate());
        yhBlog.setCreateById(loginUser.getUser().getUserId());
        yhBlog.setCreateBy(loginUser.getUser().getNickName());
        yhBlog.setUpdateTime(DateUtils.getNowDate());
        yhBlog.setUpdateById(loginUser.getUser().getUserId());
        yhBlog.setUpdateBy(loginUser.getUser().getNickName());
        yhBlog.setReadNum(readNum);
        yhBlog.setLikeNum(likeNum);

        int rows = yhBlogMapper.insert(yhBlog);
        yhBlogContent.setYhBlogId(yhBlog.getBlogId());
        rows = yhBlogContentMapper.insertYhBlogContent(yhBlogContent);

        //更新redis数据
        Map<String,Object> temp = new HashMap<>();
        temp.put("blogId",yhBlog.getBlogId());
        List<Map<String,Object>> blogList = selectBlogList(temp);
        for (Map<String,Object> obj : blogList)
        {
            BlogRedisUtils.setBlogCache(obj.get("blogId").toString(), obj);
        }


        return rows;
    }

    /**
     * 修改博客
     *
     * @param blog
     * @return
     */
    @Override
    @Transactional
    public int updateYhBlog(Blog blog)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        YhBlog yhBlog = blog.getYhBlog();
        YhBlogContent yhBlogContent = blog.getYhBlogContent();

        yhBlog.setUpdateById(loginUser.getUser().getUserId());
        yhBlog.setUpdateBy(loginUser.getUser().getNickName());

        yhBlogContentMapper.updateYhBlogContent(yhBlogContent);

        //更新redis数据
        Map<String,Object> temp = new HashMap<>();
        temp.put("blogId",yhBlog.getBlogId());
        List<Map<String,Object>> blogList = selectBlogList(temp);
        for (Map<String,Object> obj : blogList)
        {
            BlogRedisUtils.setBlogCache(obj.get("blogId").toString(), obj);
        }

       this.updateBlogTimeLineCache();

        return yhBlogMapper.updateYhBlog(yhBlog);
    }

    /**
     * 批量删除博客
     * 
     * @param blogIds 需要删除的博客ID
     * @return 结果
     */
    @Override
    public int deleteYhBlogByIds(Long[] blogIds)
    {
        yhBlogContentMapper.deleteYhBlogContentByBlogIds(blogIds);

        for (Long obj : blogIds)
        {
            BlogRedisUtils.clearBlogCacheByKey(obj.toString());
        }
        this.updateBlogTimeLineCache();

        return yhBlogMapper.deleteYhBlogByIds(blogIds);
    }

    /**
     * 删除博客信息
     * 
     * @param blogId 博客ID
     * @return 结果
     */
    @Override
    @Transactional
    public int  deleteYhBlogById(Long blogId)
    {
        yhBlogContentMapper.deleteYhBlogContentByBlogId(blogId);

        BlogRedisUtils.clearBlogCacheByKey(blogId.toString());
        this.updateBlogTimeLineCache();

        return yhBlogMapper.deleteYhBlogById(blogId);
    }

    /**
     * 查询包括博客内容的博客信息
     * @param temp
     * @return
     */
    @Override
    public List<Map<String,Object>> selectBlogList(Map<String,Object> temp)
    {
       return yhBlogMapper.selectBlogList(temp);
    }

    /**
     * 查询博客归档数据
     * @return
     */
    @Override
    public List<Map<String, Object>> selectBlogListTimeLine() {
        return yhBlogMapper.selectBlogListTimeLine();
    }

    public void updateBlogListCache(){
        BlogRedisUtils.clearBlogCache();
        //设置博客文章List缓存
        Map<String,Object> temp = new HashMap<>();
        List<Map<String,Object>> blogList = selectBlogList(temp);
        for (Map<String,Object> obj : blogList)
        {
            BlogRedisUtils.setBlogCache(obj.get("blogId").toString(), obj);
        }
    }

    public void updateBlogTimeLineCache(){
        BlogRedisUtils.clearBlogTimeLineCache();
        List<Map<String,Object>> timeLine = selectBlogListTimeLine();
        if(timeLine.size()>0){
            BlogRedisUtils.setBlogTimeLineCache(timeLine);
        }
    }
}
