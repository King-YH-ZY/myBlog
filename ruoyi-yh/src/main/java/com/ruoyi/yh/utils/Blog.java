package com.ruoyi.yh.utils;

import com.ruoyi.yh.domain.YhBlog;
import com.ruoyi.yh.domain.YhBlogContent;

import javax.validation.Valid;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 博客整体
 */
public class Blog {

    private YhBlog yhBlog;

    private YhBlogContent yhBlogContent;

    public YhBlog getYhBlog() {
        return yhBlog;
    }

    public void setYhBlog(YhBlog yhBlog) {
        this.yhBlog = yhBlog;
    }

    public YhBlogContent getYhBlogContent() {
        return yhBlogContent;
    }

    public void setYhBlogContent(YhBlogContent yhBlogContent) {
        this.yhBlogContent = yhBlogContent;
    }
}