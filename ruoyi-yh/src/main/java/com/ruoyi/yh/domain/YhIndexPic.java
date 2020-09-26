package com.ruoyi.yh.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * 轮播图对象 yh_index_pic
 * 
 * @author dyh
 * @date 2020-09-21
 */
@TableName(value="yh_index_pic",resultMap="YhIndexPicResult")
public class YhIndexPic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图片ID */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /** 图片名称 */
    @Excel(name = "图片名称")
    @TableField("file_name")
    private String fileName;

    @Excel(name = "图片路径")
    @TableField("file_url")
    private String fileUrl;

    /** 启用状态（0否 1是） */
    @Excel(name = "图片启用状态", readConverterExp = "0=否,1=是")
    private String status;

    /** 请求参数 */
    @TableField(exist=false)
    private Map<String, Object> params;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotBlank(message = "图片名称不能为空")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @NotBlank(message = "图片路径不能为空")
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fileUrl", getFileUrl())
            .append("status", getStatus())
            .append("fileName", getFileName())
            .append("createTime", getCreateTime())
            .toString();
    }
}
