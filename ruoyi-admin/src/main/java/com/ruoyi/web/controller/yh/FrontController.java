package com.ruoyi.web.controller.yh;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.yh.domain.YhIndexPic;
import com.ruoyi.yh.service.IYhBlogService;
import com.ruoyi.yh.utils.BlogRedisUtils;
import com.ruoyi.yh.utils.IndexPicRedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 前端页面获取信息
 */
@RestController
@RequestMapping("/yh/front")
public class FrontController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(FrontController.class);

    @Autowired
    private IYhBlogService yhBlogService;

    /**
     * 获取博客完整信息列表
     */
    @GetMapping("/getBlogList")
    public TableDataInfo getBlogList(Map<String,Object> temp) {
        temp.put("status","2");
        startPage();
        List<Map<String,Object>> list = yhBlogService.selectBlogList(temp);
        return getDataTable(list);
    }

    /**
     * 获取具体博客
     */
    @GetMapping("/getBlog/{blogId}")
    public AjaxResult getBlogList(@PathVariable String blogId) {
        AjaxResult ajax = AjaxResult.success();
        Map<String,Object> temp = BlogRedisUtils.getBlogCache(blogId.toString());
        if(temp!=null){
            ajax.put("data",temp);
        }else{
            Map<String,Object> obj = new HashMap<>();
            obj.put("blogId",blogId);
            List<Map<String,Object>> map = yhBlogService.selectBlogList(obj);
            if(map.size()>0){
                ajax.put("data",map.get(0));
            }else{
                BlogRedisUtils.setBlogNullCache(blogId.toString());
                ajax.put("data",temp);
            }
        }
        return ajax;
    }

    /**
     * 获取博客归档
     */
    @GetMapping("/getBlogTimeLine")
    public AjaxResult getBlogTimeLine() {
        AjaxResult ajax = AjaxResult.success();
        List<Map<String,Object>> temp = BlogRedisUtils.getBlogTimeLineCache();
        ajax.put("data",temp);
        return ajax;
    }

    /**
     * 获取轮播图
     * @return
     */
    @GetMapping("/getIndexPic")
    public AjaxResult getIndexPic() {
        AjaxResult ajax = AjaxResult.success();
        List<Map<String, Object>> temp = IndexPicRedisUtils.getIndexPicCache();
        ajax.put("data",temp);
        return ajax;
    }

}
