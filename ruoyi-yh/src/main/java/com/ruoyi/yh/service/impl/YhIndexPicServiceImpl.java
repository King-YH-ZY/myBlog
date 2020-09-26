package com.ruoyi.yh.service.impl;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.yh.domain.YhBlog;
import com.ruoyi.yh.domain.YhBlogContent;
import com.ruoyi.yh.domain.YhIndexPic;
import com.ruoyi.yh.mapper.YhBlogContentMapper;
import com.ruoyi.yh.mapper.YhBlogMapper;
import com.ruoyi.yh.mapper.YhIndexPicMapper;
import com.ruoyi.yh.service.IYhIndexPicService;
import com.ruoyi.yh.utils.Blog;
import com.ruoyi.yh.utils.BlogRedisUtils;
import com.ruoyi.yh.utils.IndexPicRedisUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 轮播图Service业务层处理
 * 
 * @author dyh
 * @date 2020-09-21
 */
@Service
public class YhIndexPicServiceImpl implements IYhIndexPicService
{
    @Autowired
    private YhIndexPicMapper yhIndexPicMapper;

    /**
     * 项目启动时，初始化博客到缓存
     */
    @PostConstruct
    public void init()
    {
        this.updateIndexPicCache();
    }

    @Override
    public YhIndexPic selectYhIndexPicById(Long id) {
        return yhIndexPicMapper.selectById(id);
    }

    @Override
    public List<YhIndexPic> selectIndexPicList(YhIndexPic yhIndexPic) {
        return yhIndexPicMapper.selectIndexPicList(yhIndexPic);
    }

    @Override
    @Transactional
    public int insertYhIndexPic(YhIndexPic yhIndexPic) {
        yhIndexPic.setCreateTime(new Date());
        int row = yhIndexPicMapper.insert(yhIndexPic);
        this.updateIndexPicCache();
        return row;
    }

    @Override
    @Transactional
    public int updateYhIndexPic(YhIndexPic yhIndexPic) {
        YhIndexPic pic = yhIndexPicMapper.selectById(yhIndexPic);
        String url = "";
        if(!yhIndexPic.getFileUrl().equals(pic.getFileUrl())){
            url = pic.getFileUrl().replaceAll(Constants.RESOURCE_PREFIX,RuoYiConfig.getProfile());
        }
        int row = yhIndexPicMapper.updateById(yhIndexPic);
        if(Strings.isNotEmpty(url)) {
            FileUtils.deleteFile(url);
        }
        this.updateIndexPicCache();
        return row;
    }

    @Override
    @Transactional
    public int deleteYhIndexPicByIds(Long[] ids) {
        List<YhIndexPic> lists = yhIndexPicMapper.selectBatchIds(Arrays.asList(ids));
        int row = yhIndexPicMapper.deleteBatchIds(Arrays.asList(ids));
        this.updateIndexPicCache();
        int size = lists.size();
        for (YhIndexPic temp:lists){
            String url = temp.getFileUrl().replaceAll(Constants.RESOURCE_PREFIX,RuoYiConfig.getProfile());
            FileUtils.deleteFile(url);
        }
        return row;
    }

    @Override
    @Transactional
    public int deleteYhIndexPicById(Long id) {
        YhIndexPic pic = yhIndexPicMapper.selectById(id);
        int row = yhIndexPicMapper.deleteById(id);
        String url = pic.getFileUrl().replaceAll(Constants.RESOURCE_PREFIX,RuoYiConfig.getProfile());
        FileUtils.deleteFile(url);
        this.updateIndexPicCache();
        return row;
    }

    @Override
    @Transactional
    public void updateIndexPicStatus(Map<String, Object> temp) {
        yhIndexPicMapper.updateIndexPicStatus(temp);
        this.updateIndexPicCache();
    }

    @Transactional
    public void updateIndexPicCache(){
        IndexPicRedisUtils.clearCache();
        YhIndexPic indexPic = new YhIndexPic();
        indexPic.setStatus("1");
        List<YhIndexPic> a = selectIndexPicList(indexPic);
        if(a.size()>0){
            IndexPicRedisUtils.setIndexPicCache(a);
        }
    }
}
