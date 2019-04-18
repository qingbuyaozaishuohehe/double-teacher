package com.rzlearn.user.constant;

/**
 * <p>ClassName:Constants</p>
 * <p>Description:常量</p>
 *
 * @author JiPeigong
 * @date 2018年5月5日
 * @see
 * @since
 */
public interface BusinessConst {

    /**
     * 权限根菜单 parentId 标识
     */
    Long ROOT_MENU_PARENT_ID = 0L;

    /**
     * 验证码类型.
     */
    String VERIFY_TYPE = "image/png";

    /**
     * 验证码宽度.
     */
    int WIDTH = 100;

    /**
     * 验证码高度.
     */
    int HEIGHT = 50;

    /**
     * 验证码生成数字源.
     */
    String VERIFY_CODES = "0123456789";

    /**
     * 验证码缓存前缀.
     */
    String VERIFY_PREFIX = "VERIFY_";

    Long VERIFY_TIME_OUT = 30 * 60L;

    String DEFAULT_LANGUAGE = "zh";

    String ROLE_CODE_PREFIX = ",";

    /**
     * 超级管理员
     */
    String ROLE_SUPER_ADMIN = "role_super_admin";

    /**
     * 默认角色
     */
    Integer DEFAULT_ROLE = 1;

    String USER_STATE_STOP = "0";

    String PHONE_REGEXP = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

}
