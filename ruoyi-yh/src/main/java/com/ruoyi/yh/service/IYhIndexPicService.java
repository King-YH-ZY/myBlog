package com.ruoyi.yh.service;

import com.ruoyi.yh.domain.YhBlog;
import com.ruoyi.yh.domain.YhIndexPic;
import com.ruoyi.yh.utils.Blog;

import java.util.List;
import java.util.Map;

/**
 * 图片Service接口
 * 
 * @author dyh
 * @date 2020-09-21
 */
public interface IYhIndexPicService
{

    /**
     * 查找图片
     * @param blogId
     * @return
     */
    YhIndexPic selectYhIndexPicById(Long blogId);

    /**
     * 图片列表
     * @param yhIndexPic
     * @return
     */
     List<YhIndexPic> selectIndexPicList(YhIndexPic yhIndexPic);


    /**
     * 新增图片
     * @param yhIndexPic
     * @return
     */
     int insertYhIndexPic(YhIndexPic yhIndexPic);

    /**
     * 修改图片信息
     * @param yhIndexPic
     * @return
     */
     int updateYhIndexPic(YhIndexPic yhIndexPic);


    /**
     * 批量删除图片
     * @param ids
     * @return
     */
     int deleteYhIndexPicByIds(Long[] ids);

    /**
     * 删除图片
     * @param id
     * @return
     */
     int deleteYhIndexPicById(Long id);

    /**
     * 更新图片状态
     * @param temp
     */
    void updateIndexPicStatus(Map<String,Object> temp);

}
