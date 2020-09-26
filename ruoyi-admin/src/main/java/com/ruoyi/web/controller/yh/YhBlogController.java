package com.ruoyi.web.controller.yh;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.yh.domain.YhBlog;
import com.ruoyi.yh.domain.YhBlogTag;
import com.ruoyi.yh.domain.YhIndexPic;
import com.ruoyi.yh.service.IYhBlogContentService;
import com.ruoyi.yh.service.IYhBlogService;
import com.ruoyi.yh.service.IYhBlogTagService;
import com.ruoyi.yh.service.IYhIndexPicService;
import com.ruoyi.yh.utils.ArraysUtil;
import com.ruoyi.yh.utils.Blog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 博客Controller
 *
 * @author dyh
 * @date 2020-08-18
 */
@RestController
@RequestMapping("/yh/blog")
public class YhBlogController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(YhBlogController.class);

    @Autowired
    private IYhBlogService yhBlogService;
    @Autowired
    private IYhBlogContentService yhBlogContentService;
    @Autowired
    private IYhBlogTagService yhBlogTagService;
    @Autowired
    private IYhIndexPicService yhIndexPicService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ServerConfig serverConfig;

    /**
     * 查询博客列表
     */
    @PreAuthorize("@ss.hasPermi('yh:blog:list')")
    @GetMapping("/list")
    public TableDataInfo list(YhBlog yhBlog) {
//        LoginUser loginUser = SecurityUtils.getLoginUser();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Boolean isAdmin = SecurityUtils.isAdmin(loginUser.getUser().getUserId());
        List<YhBlog> list = new ArrayList<>();
        startPage();
        if (isAdmin) {
            list = yhBlogService.selectYhBlogList(yhBlog);
        } else {
            yhBlog.setCreateById(loginUser.getUser().getUserId());
            list = yhBlogService.selectYhBlogListByUser(yhBlog);
        }

        return getDataTable(list);
    }

    /**
     * 导出博客列表
     */
    @PreAuthorize("@ss.hasPermi('yh:blog:export')")
    @Log(title = "博客", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(YhBlog yhBlog) {
        List<YhBlog> list = yhBlogService.selectYhBlogList(yhBlog);
        ExcelUtil<YhBlog> util = new ExcelUtil<YhBlog>(YhBlog.class);
        return util.exportExcel(list, "blog");
    }

    /**
     * 获取博客详细信息
     */
    @PreAuthorize("@ss.hasPermi('yh:blog:query')")
    @GetMapping(value = "/{blogId}")
    public AjaxResult getInfo(@PathVariable("blogId") Long blogId) {
        Map<String, Object> map = new HashMap<>();
        map.put("blog", yhBlogService.selectYhBlogById(blogId));
        map.put("blogContent", yhBlogContentService.selectYhBlogContentByBlogId(blogId));
        return AjaxResult.success(map);
    }

    /**
     * 新增博客
     */
    @PreAuthorize("@ss.hasPermi('yh:blog:add')")
    @Log(title = "博客", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Blog blog) {
        int rows = yhBlogService.insertYhBlog(blog);
        return toAjaxAndResult(rows, blog);
    }

    /**
     * 修改博客
     */
    @PreAuthorize("@ss.hasPermi('yh:blog:edit')")
    @Log(title = "博客", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Blog blog) {
        int rows = yhBlogService.updateYhBlog(blog);
        return toAjaxAndResult(rows, blog);
    }

    /**
     * 删除博客
     */
    @PreAuthorize("@ss.hasPermi('yh:blog:remove')")
    @Log(title = "博客", businessType = BusinessType.DELETE)
    @DeleteMapping("/{blogIds}")
    public AjaxResult remove(@PathVariable Long[] blogIds) {
        return toAjax(yhBlogService.deleteYhBlogByIds(blogIds));
    }

    /**
     * 删除文章图片
     *
     * @param filePath
     * @return
     */
    @PreAuthorize("@ss.hasPermi('yh:blog:remove')")
    @Log(title = "博客文章", businessType = BusinessType.DELETE)
    @DeleteMapping("/delImg")
    public boolean delImg(@RequestParam String filePath) {
        String baseUrl = serverConfig.getUrl();
//        Boolean isHaveBaseUrl = filePath.contains(baseUrl);
//        Boolean isHaveProFile = filePath.contains("/profile");
        filePath = filePath.replaceAll(baseUrl,"");
        filePath = filePath.replaceAll(Constants.RESOURCE_PREFIX, RuoYiConfig.getProfile());

        return FileUtils.deleteFile(filePath);
    }

    /**
     * 查询标签列表
     */
    @PreAuthorize("@ss.hasPermi('yh:blog:tag:list')")
    @GetMapping("/tag/list")
    public TableDataInfo getTagList(YhBlogTag yhBlogTag) {
        List<YhBlogTag> list = yhBlogTagService.selectYhBlogTagList(yhBlogTag);
        return getDataTable(list);
    }

    /**
     * 通过id查询标签
     */
    @PreAuthorize("@ss.hasPermi('yh:blog:tag:query')")
    @GetMapping("/tag/{id}")
    public AjaxResult getTag(@PathVariable("id") Long id) {
        AjaxResult ajax = AjaxResult.success();
        YhBlogTag tag = yhBlogTagService.selectYhBlogTagById(id);
        ajax.put("data", tag);
        return ajax;
    }

    /**
     * 新增标签
     */
    @PreAuthorize("@ss.hasPermi('yh:blog:tag:add')")
    @Log(title = "博客标签", businessType = BusinessType.INSERT)
    @PostMapping("/tag")
    public AjaxResult addTag(@Validated @RequestBody YhBlogTag yhBlogTag) {
        int tags = yhBlogTagService.checkTagUnique(yhBlogTag);
        if (tags > 0) {
            return AjaxResult.error("新增标签'" + yhBlogTag.getName() + "'失败，标签已存在");
        } else {
            int rows = yhBlogTagService.insertYhBlogTag(yhBlogTag);
            return toAjax(rows);
        }

    }

    /**
     * 修改标签
     */
    @PreAuthorize("@ss.hasPermi('yh:blog:tag:edit')")
    @Log(title = "博客标签", businessType = BusinessType.UPDATE)
    @PutMapping("/tag")
    public AjaxResult editTag(@Validated @RequestBody YhBlogTag yhBlogTag) {
        int tags = yhBlogTagService.checkTagUniqueById(yhBlogTag);
        if (tags > 0) {
            return AjaxResult.error("修改标签'" + yhBlogTag.getName() + "'失败，标签已存在");
        } else {
            int rows = yhBlogTagService.updateYhBlogTag(yhBlogTag);
            return toAjax(rows);
        }
    }

    /**
     * 删除标签
     */
    @PreAuthorize("@ss.hasPermi('yh:blog:tag:remove')")
    @Log(title = "博客标签", businessType = BusinessType.DELETE)
    @DeleteMapping("/tag/{ids}")
    public AjaxResult removeTag(@PathVariable Long[] ids) {
        return toAjax(yhBlogTagService.deleteYhBlogTagByIds(ids));
    }

    /**
     * 加载可选标签
     * @return
     */
    @PreAuthorize("@ss.hasPermi('yh:blog:query')")
    @GetMapping(value = "/tag/all")
    public AjaxResult getAllTag() {
        AjaxResult result = AjaxResult.success();
        List<Map<String, Object>> data = new ArrayList<>();
        List<String> tags = yhBlogTagService.selectAllTagName();
        if (tags.size() > 0) {
            for (String tag : tags) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", tag);
                map.put("active", false);
                data.add(map);
            }
        }
        result.put("data", data);
        return result;
    }

    /**
     * 修改文章时可选的标签
     * @param blogId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('yh:blog:query')")
    @GetMapping(value = "/tag/all/{blogId}")
    public AjaxResult getAllTag(@PathVariable Long blogId) {
        AjaxResult result = AjaxResult.success();
        List<Map<String, Object>> data = new ArrayList<>();
        YhBlog blog = yhBlogService.selectYhBlogById(blogId);
        List<String> tags = yhBlogTagService.selectAllTagName();
        if (tags.size() > 0) {
            String[] allTag = tags.toArray(new String[tags.size()]);
            String[] tempTage = blog.getTag().split(",");
            String[] same = ArraysUtil.getJ(allTag,tempTage); //相同的交集
            String[] noSame =  ArraysUtil.getC(same,allTag); //差集
            for (String tag : noSame) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", tag);
                map.put("active", false);
                data.add(map);
            }
            for (String tag : tempTage) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", tag);
                map.put("active", true);
                data.add(map);
            }
        }else{
            String[] temp = blog.getTag().split(",");
            for (String tag : temp) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", tag);
                map.put("active", true);
                data.add(map);
            }
        }
        result.put("data", data);
        return result;
    }

    /**
     * 查询轮播图
     */
    @PreAuthorize("@ss.hasPermi('yh:indexPic:list')")
    @GetMapping("/indexPic/list")
    public TableDataInfo getIndexPicList(YhIndexPic indexPic) {
        startPage();
        List<YhIndexPic> list = yhIndexPicService.selectIndexPicList(indexPic);
        return getDataTable(list);
    }

    /**
     * 获取轮播图
     */
    @PreAuthorize("@ss.hasPermi('yh:indexPic:query')")
    @GetMapping(value = "/indexPic/{id}")
    public AjaxResult getIndexPic(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("indexPic", yhIndexPicService.selectYhIndexPicById(id));
        return AjaxResult.success(map);
    }

    /**
     * 新增轮播图
     */
    @PreAuthorize("@ss.hasPermi('yh:indexPic:add')")
    @Log(title = "轮播图", businessType = BusinessType.INSERT)
    @PostMapping(value = "/indexPic")
    public AjaxResult addIndexPic(@Validated @RequestBody YhIndexPic indexPic) {
        int rows = yhIndexPicService.insertYhIndexPic(indexPic);
        return toAjax(rows);
    }

    /**
     * 修改轮播图
     */
    @PreAuthorize("@ss.hasPermi('yh:indexPic:edit')")
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/indexPic")
    public AjaxResult editIndexPic(@Validated @RequestBody YhIndexPic indexPic) {
        int rows = yhIndexPicService.updateYhIndexPic(indexPic);
        return toAjax(rows);
    }

    /**
     * 删除轮播图
     */
    @PreAuthorize("@ss.hasPermi('yh:indexPic:remove')")
    @Log(title = "轮播图", businessType = BusinessType.DELETE)
    @DeleteMapping("/indexPic/{ids}")
    public AjaxResult removeIndexPic(@PathVariable Long[] ids) {
        return toAjax(yhIndexPicService.deleteYhIndexPicByIds(ids));
    }

    /**
     * 轮播图上传
     */
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PostMapping("/indexPic/upload")
    public AjaxResult avatar(@RequestParam("file") MultipartFile file) throws IOException
    {
        if (!file.isEmpty())
        {
            String fileUrl = FileUploadUtils.upload(RuoYiConfig.getProfile()+"/indexPic", file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileUrl",fileUrl);
            return ajax;
        }

        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 修改轮播图状态
     */
    @PreAuthorize("@ss.hasPermi('yh:indexPic:edit')")
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/indexPic/status")
    public AjaxResult editIndexPicStatus(@RequestBody Map<String,Object> temp) {
        yhIndexPicService.updateIndexPicStatus(temp);
        AjaxResult ajax = AjaxResult.success();
        return ajax;
    }

}
