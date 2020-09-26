package com.ruoyi.yh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.yh.domain.YhBlog;
import com.ruoyi.yh.domain.YhBlogTag;

import java.util.List;


public interface YhBlogTagMapper extends BaseMapper<YhBlogTag>
{

    /**
     * 查询标签列表
     * @param yhBlogTag
     * @return
     */
     List<YhBlogTag> selectYhBlogTagList(YhBlogTag yhBlogTag);

    /**
     *新增标签
     * @param yhBlogTag
     * @return
     */
    int insertYhBlogTag(YhBlogTag yhBlogTag);

    /**
     * 修改标签
     * @param yhBlogTag
     * @return
     */
    int updateYhBlogTag(YhBlogTag yhBlogTag);


    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteYhBlogTagByIds(Long[] ids);

    /**
     * 检查是否唯一标签名称
     * @param yhBlogTag
     * @return
     */
    int checkTagUnique(YhBlogTag yhBlogTag);

    /**
     * 修改时检查是否唯一标签名称
     * @param yhBlogTag
     * @return
     */
    int checkTagUniqueById(YhBlogTag yhBlogTag);

    /**
     * 获取所有tag
     * @return
     */
    List<String> selectAllTagName();
}
