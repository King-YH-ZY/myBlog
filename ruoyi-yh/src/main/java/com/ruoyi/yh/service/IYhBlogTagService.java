package com.ruoyi.yh.service;

import com.ruoyi.yh.domain.YhBlogTag;

import java.util.List;
import java.util.Map;


public interface IYhBlogTagService
{

    /**
     * 查询标签列表数据
     * @param yhBlogTag
     * @return
     */
     List<YhBlogTag> selectYhBlogTagList(YhBlogTag yhBlogTag);


    /**
     * 通过id查找标签
     * @param id
     * @return
     */
    YhBlogTag selectYhBlogTagById(Long id);

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
     * 新增标签
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
     * 删除标签
     * @param ids
     * @return
     */
     int deleteYhBlogTagByIds(Long[] ids);

    /**
     * 获取所有tag
     * @return
     */
    List<String> selectAllTagName();

}
