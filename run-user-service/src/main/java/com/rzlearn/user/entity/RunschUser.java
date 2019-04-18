package com.rzlearn.user.entity;

import com.baomidou.mybatisplus.enums.FieldStrategy;
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
 * 用户表
 * </p>
 *
 * @author JiPeigong
 * @since 2018-06-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("runsch_user")
public class RunschUser extends Model<RunschUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 默认应用
     */
    @TableField("domain_id")
    private Long domainId;

    @TableField("organization_id")
    private Long organizationId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 状态
     */
    @TableField("state")
    private String state;

    /**
     * 用户密码
     */
    @TableField("password_hash")
    private String passwordHash;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱地址
     */
    @TableField(strategy=FieldStrategy.IGNORED)
    private String email;

    /**
     * 用户头像地址
     */
    private String avatar;

    /**
     * qq号
     */
    private String qq;

    /**
     * 微信号
     */
    @TableField("wei_xin")
    private String weiXin;

    /**
     * 微博号
     */
    @TableField("wei_bo")
    private String weiBo;

    /**
     * 性别(1：男；0：女)
     */
    private String sex;

    /**
     * 住址
     */
    private String address;

    /**
     * 身份证号码
     */
    @TableField("id_card")
    private String idCard;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 是否同意隐私条款（1：同意；0：不同意）
     */
    @TableField("is_agree_privacy")
    private Integer isAgreePrivacy;

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
