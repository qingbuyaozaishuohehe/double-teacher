package com.rzlearn.setting.entity;

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
 * WEB应用配置
 * </p>
 *
 * @author JiPeigong
 * @since 2018 -08-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("runsch_web_app_config")
public class RunschWebAppConfig extends Model<RunschWebAppConfig> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 域名
     */
    @TableField("domain_name")
    private String domainName;

    /**
     * 语言
     */
    private String language;

    /**
     * 名称
     */
    private String name;

    @TableField("home_title")
    private String homeTitle;

    @TableField("login_title")
    private String loginTitle;

    @TableField("login_bottom_hint")
    private String loginBottomHint;

    /**
     * 登陆logoid
     */
    @TableField("login_logo_id")
    private String loginLogoId;

    @TableField("powered_by")
    private String poweredBy;

    @TableField("home_logo_id")
    private String homeLogoId;

    @TableField("forget_logo_id")
    private String forgetLogoId;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    /**
     * 是否删除
     */
    @TableField("is_deleted")
    private Integer isDeleted;

    private String agreement;

    @TableField("about_us")
    private String aboutUs;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
