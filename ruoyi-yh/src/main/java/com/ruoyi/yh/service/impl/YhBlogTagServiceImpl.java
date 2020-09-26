package com.ruoyi.yh.service.impl;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.yh.domain.YhBlog;
import com.ruoyi.yh.domain.YhBlogContent;
import com.ruoyi.yh.domain.YhBlogTag;
import com.ruoyi.yh.mapper.YhBlogTagMapper;
import com.ruoyi.yh.service.IYhBlogTagService;
import com.ruoyi.yh.utils.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 博客Service业务层处理
 * 
 * @author dyh
 * @date 2020-08-18
 */
@Service
public class YhBlogTagServiceImpl implements IYhBlogTagService
{
    @Autowired
    private YhBlogTagMapper yhBlogTagMapper;


    @Override
    public List<YhBlogTag> selectYhBlogTagList(YhBlogTag yhBlogTag) {
        return yhBlogTagMapper.selectYhBlogTagList(yhBlogTag);
    }

    @Override
    public YhBlogTag selectYhBlogTagById(Long id) {
        return yhBlogTagMapper.selectById(id);
    }

    @Override
    public int checkTagUnique(YhBlogTag yhBlogTag) {
        return yhBlogTagMapper.checkTagUnique(yhBlogTag);
    }

    @Override
    public int checkTagUniqueById(YhBlogTag yhBlogTag) {
        return yhBlogTagMapper.checkTagUniqueById(yhBlogTag);
    }

    @Override
    @Transactional
    public int insertYhBlogTag(YhBlogTag yhBlogTag) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        yhBlogTag.setCreateBy(loginUser.getUser().getNickName());
        yhBlogTag.setCreateById(loginUser.getUser().getUserId());
        return yhBlogTagMapper.insertYhBlogTag(yhBlogTag);
    }

    @Override
    @Transactional
    public int updateYhBlogTag(YhBlogTag yhBlogTag) {
        return yhBlogTagMapper.updateYhBlogTag(yhBlogTag);
    }

    @Override
    @Transactional
    public int deleteYhBlogTagByIds(Long[] ids) {
        return yhBlogTagMapper.deleteYhBlogTagByIds(ids);
    }

    @Override
    public List<String> selectAllTagName() {
        return yhBlogTagMapper.selectAllTagName();
    }

}
