package com.ruoyi.yh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.yh.domain.YhBlog;
import com.ruoyi.yh.domain.YhIndexPic;

import java.util.List;
import java.util.Map;

/**
 * 图片Mapper接口
 * 
 * @author dyh
 * @date 2020-09-21
 */
public interface YhIndexPicMapper extends BaseMapper<YhIndexPic>
{

    /**
     * 图片列表
     * @param yhIndexPic
     * @return
     */
     List<YhIndexPic> selectIndexPicList(YhIndexPic yhIndexPic);

    /**
     * 更新图片状态
     * @param temp
     */
    void updateIndexPicStatus(Map<String,Object> temp);


}
