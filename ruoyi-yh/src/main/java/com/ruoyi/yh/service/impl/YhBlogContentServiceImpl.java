package com.ruoyi.yh.service.impl;

import com.ruoyi.yh.domain.YhBlogContent;
import com.ruoyi.yh.mapper.YhBlogContentMapper;
import com.ruoyi.yh.service.IYhBlogContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 博客Service业务层处理
 * 
 * @author dyh
 * @date 2020-08-18
 */
@Service
public class YhBlogContentServiceImpl implements IYhBlogContentService
{
    @Autowired
    private YhBlogContentMapper yhBlogContentMapper;


    @Override
    public YhBlogContent selectYhBlogContentById(Long blogContentId) {
        return yhBlogContentMapper.selectYhBlogContentById(blogContentId);
    }

    @Override
    public YhBlogContent selectYhBlogContentByBlogId(Long blogId) {
        return yhBlogContentMapper.selectYhBlogContentByBlogId(blogId);
    }

    @Override
    public int insertYhBlogContent(YhBlogContent yhBlogContent) {
        return yhBlogContentMapper.insertYhBlogContent(yhBlogContent);
    }

    @Override
    public int updateYhBlogContent(YhBlogContent yhBlogContent) {
        return yhBlogContentMapper.updateYhBlogContent(yhBlogContent);
    }

    @Override
    public int deleteYhBlogContent(Long contentId) {
        return yhBlogContentMapper.deleteYhBlogContent(contentId);
    }

    @Override
    public int deleteYhBlogContentByBlogId(Long blogId) {
        return yhBlogContentMapper.deleteYhBlogContentByBlogId(blogId);
    }
}
