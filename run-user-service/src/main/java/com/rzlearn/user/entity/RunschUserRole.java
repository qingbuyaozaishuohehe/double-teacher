package com.rzlearn.user.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author JiPeigong
 * @since 2018-06-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("runsch_user_role")
public class RunschUserRole extends Model<RunschUserRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户表主键
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色名称
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 是否为默认（1：是；0：否）
     */
    @TableField("is_default")
    private Integer isDefault;

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
