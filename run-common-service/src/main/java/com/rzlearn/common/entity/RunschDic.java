package com.rzlearn.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author JiPeigong
 * @since 2018-05-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("runsch_dic")
public class RunschDic extends Model<RunschDic> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 字典代码
     */
    private String code;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典语言
     */
    private String language;

    /**
     * 父字典类别
     */
    @TableField("parent_type")
    private String parentType;

    /**
     * 父字典代码
     */
    @TableField("parent_code")
    private String parentCode;

    /**
     * 排序
     */
    @TableField("sort_num")
    private Integer sortNum;

    @TableField("description")
    private String description;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 最近修改时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    /**
     * 是否删除（1：删除；0：未删除）
     */
    @TableField("is_deleted")
    private Integer isDeleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
