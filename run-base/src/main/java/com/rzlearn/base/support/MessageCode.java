package com.rzlearn.base.support;

import com.rzlearn.base.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * <p>ClassName:MessageCode</p>
 * <p>Description:针对i18n提示信息code</p>
 *
 * @author JiPeigong
 * @date 2018年5月10日
 */
public enum MessageCode {
    /**
     * 成功
     */
    SUCCESS(200, "SUCCESS"),
    /**
     * 失败
     */
    FAILED(500, "FAILED"),
    /**
     * 系统正在升级维护
     */
    SYSTEM_MAINTENANCE_UPGRADE(503, "SYSTEM_MAINTENANCE_UPGRADE"),
    /**
     * 用户不存在
     */
    USER_IS_NULL(500, "USER_IS_NULL"),
    /**
     * 用户不存在
     */
    USER_IS_NOT_EXIST(500, "USER_IS_NOT_EXIST"),
    /**
     * 用户已被停用
     */
    USER_IS_DEACTIVATE(500, "USER_IS_DEACTIVATE"),
    /**
     * 学生账号无法登录
     */
    STUDENT_ACCOUNT_CAN_NOT_LOGIN(500, "STUDENT_ACCOUNT_CAN_NOT_LOGIN"),
    /**
     * 用户名已存在
     */
    USER_NAME_IS_EXIST(500, "USER_NAME_IS_EXIST"),
    /**
     * 用户手机号码已存在
     */
    USER_PHONE_IS_EXIST(500, "USER_PHONE_IS_EXIST"),
    /**
     * 创建成功
     */
    CRAETE_SUCCESS(200, "CRAETE_SUCCESS"),
    /**
     * 创建失败
     */
    CRAETE_FAILED(500, "CRAETE_FAILED"),
    /**
     * 更新成功
     */
    UPDATE_SUCCESS(200, "UPDATE_SUCCESS"),
    /**
     * 更新失败
     */
    UPDATE_FAILED(500, "UPDATE_FAILED"),
    /**
     * 删除成功
     */
    DELETE_SUCCESS(200, "DELETE_SUCCESS"),
    /**
     * 登录已过期，请重新登陆
     */
    INVALID_TOKEN(401, "INVALID_TOKEN"),
    /**
     * 当前未登录
     */
    NO_AUTHENTICATION(401, "NO_AUTHENTICATION"),
    /**
     * 删除失败
     */
    DELETE_FAILED(500, "DELETE_FAILED"),
    /**
     * 没有权限
     */
    NO_ACCESS(500, "NO_ACCESS"),
    /**
     * 操作成功
     */
    OPERATE_SUCCESS(200, "OPERATE_SUCCESS"),
    /**
     * 操作失败
     */
    OPERATE_FAILED(500, "OPERATE_FAILED"),
    /**
     *  所删内容中，包含了已发布课时，无法删除
     */
    CONTAIN_PUBLISHED_TASK_CAN_NOT_DELETE(500, "CONTAIN_PUBLISHED_TASK_CAN_NOT_DELETE"),
    /**
     *  该 测验已发布，无法删除
     */
    PUBLISHED_TEST_CAN_NOT_DELETE(500, "PUBLISHED_TEST_CAN_NOT_DELETE"),
    /**
     *  该测验已过期，无法调整截止日期
     */
    THE_TEST_IS_EXPIRED_CAN_NOT_UPDATE_END_TIME(500, "THE_TEST_IS_EXPIRED_CAN_NOT_UPDATE_END_TIME"),
    /**
     *  专业代码已存在，请重新输入
     */
    MAJOR_CODE_EXIST(500, "MAJOR_CODE_EXIST"),
    /**
     *  班级名称已存在，请重新输入
     */
    CLASS_NAME_EXIST(500, "CLASS_NAME_EXIST"),
    /**
     *  专业名称已存在，请重新输入
     */
    MAJOR_NAME_EXIST(500, "MAJOR_NAME_EXIST"),
    /**
     *  该课程已发布，无法删除
     */
    PUBLISHED_CURRICULUM_CRITERION_CAN_NOT_DELETE(500, "PUBLISHED_CURRICULUM_CRITERION_NOT_DELETE"),
    /**
     *  当前缺少课程标准，无法发布。请先录入课程标准后，再进行发布
     */
    LACK_CURRICULUM_CRITERION_CAN_NOT_PUBLISHED(500, "LACK_CURRICULUM_CRITERION_CAN_NOT_PUBLISHED"),
    /**
     *  本学期该课程名称已存在
     */
    THE_CURRICULUM_NAME_ALREADY_EXISTS_IN_THIS_SEMESTER(500, "THE_CURRICULUM_NAME_ALREADY_EXISTS_IN_THIS_SEMESTER"),
    /**
     * 当前无数据
     */
    NO_DATA(500, "NO_DATA"),
    /**
     * 无法激活或停用自己
     */
    CAN_NOT_OPERATE_SELF(500, "CAN_NOT_OPERATE_SELF"),
    /**
     * 用户名或者密码不正常
     */
    USER_OR_PASSWORD_INCORRECT(500, "USER_OR_PASSWORD_INCORRECT"),
    /**
     * 该课件已存在
     */
    COURSEWARE_ALREADY_EXIST(500, "COURSEWARE_ALREADY_EXIST"),
    /**
     * 密码错误
     */
    PASSWORD_INCORRECT(500, "PASSWORD_INCORRECT"),
    /**
     * 用户名为空
     */
    NO_LOGIN(500, "NO_LOGIN"),
    /**
     * 组织名为空
     */
    ORGANIZATION_IS_NULL(500, "ORGANIZATION_IS_NULL"),
    /**
     * 老师数量超过了授权数量
     */
    TEACHER_COUNT_OVER_AUTH_LIMIT(500, "TEACHER_COUNT_OVER_AUTH_LIMIT"),
    /**
     * 学生数量超过了授权数量
     */
    STUDENT_COUNT_OVER_AUTH_LIMIT(500, "STUDENT_COUNT_OVER_AUTH_LIMIT"),
    /**
     * 远程调用超时
     */
    FEIGN_TIMEOUT(500, "FEIGN_TIMEOUT"),
    /**
     * 客户端服务不存在
     */
    CLIENT_SERVER_NOT_EXIT(500, "CLIENT_SERVER_NOT_EXIT"),
    /**
     * 客户名称已存在
     */
    CUSTOMER_NAME_EXIST(500, "CUSTOMER_NAME_EXIST"),
    /**
     * 编号已存在
     */
    NUM_IS_EXIST(500, "NUM_IS_EXIST"),
    /**
     * 学期代号已经存在
     */
    SEMESTER_CODE_IS_EXIST(500, "SEMESTER_CODE_IS_EXIST"),

    /**
     * 学期起始日期有重合
     */
    SEMESTER_DATE_IS_CROSS(500, "SEMESTER_DATE_IS_CROSS"),
    /**
     * 客户简称已存在
     */
    CUSTOMER_SHORT_NAME_EXIST(500, "CUSTOMER_SHORT_NAME_EXIST"),
    /**
     * 参数异常
     */
    PARAM_ILLEGAL(500, "PARAM_ILLEGAL"),

     /** JSON 参数格式异常
     */
    JSON_PARAM_FORMAT_EXCEPTION(500, "JSON_PARAM_FORMAT_EXCEPTION"),
    /**
     * 授权日期不在范围内
     */
    AUTH_END_DATE_NOT_IN_RANGE(500, "AUTH_END_DATE_NOT_IN_RANGE"),
    /**
     * 授权学生数超出范围
     */
    AUTH_STUDENT_UPPER_LIMIT(500, "AUTH_STUDENT_UPPER_LIMIT"),
    /**
     * 授权教师数超出范围
     */
    AUTH_TEACHER_UPPER_LIMIT(500, "AUTH_TEACHER_UPPER_LIMIT"),
    /**
     * 验证码错误
     */
    VERIFICATION_CODE_ERROR(500, "VERIFICATION_CODE_ERROR"),
    /**
     * 验证码错误
     */
    VERIFICATION_CODE_TIME_OUT(408, "VERIFICATION_CODE_TIME_OUT"),
    /**
     * 评价标题已存在
     */
    VALUATION_TITLE_IS_EXIST(500, "VALUATION_TITLE_IS_EXIST"),
    /**
     * 互动标题已存在
     */
    INTERACTION_TITLE_IS_EXIST(500, "INTERACTION_TITLE_IS_EXIST"),
    /**
     * 验证码已过期
     */
    VERIFICATION_CODE_EXPIRE(500, "VERIFICATION_CODE_EXPIRE"),
    /**
     * 手机号码相同
     */
    PHONE_NUM_IS_SAME(500, "PHONE_NUM_IS_SAME"),
    /**
     * 教学引擎数据不同步
     */
    TEACHING_ENGINE_DATA_ASYNCHRONY(500, "TEACHING_ENGINE_DATA_ASYNCHRONY"),
    /**
     * 教学引擎异常
     */
    TEACHING_ENGINE_EXCEPTION(500, "TEACHING_ENGINE_EXCEPTION"),
    /**
     * 设备已签到
     */
    DEVICE_IS_SIGNED(500, "DEVICE_IS_SIGNED"),
    /**
     * 账号已签到
     */
    ACCOUNT_IS_SIGNED(500, "ACCOUNT_IS_SIGNED"),
    /**
     * 二维码已过期
     */
    QR_CODE_HAS_EXPIRED(500, "QR_CODE_HAS_EXPIRED"),
    /**
     * 该学生不属于该课程，不能签到
     */
    STUDENT_NOT_BELONG_TO_COURSE(500, "STUDENT_NOT_BELONG_TO_COURSE"),
    /**
     * 课时已点赞
     */
    TASK_IS_THUMBS_UP(500, "TASK_IS_THUMBS_UP"),
    /**
     * 未同意协议 条款
     */
    NO_AGREE_AGREEMENT(500, "NO_AGREE_AGREEMENT"),
    /**
     * 组织已被冻结
     */
    ORG_IS_FROZEN(500, "ORG_IS_FROZEN"),
    /**
     * 所属组织非当前应用下数据
     */
    ORG_IS_NOT_IN_CURRENT_DOMAIN(500, "ORG_IS_NOT_IN_CURRENT_DOMAIN"),
    /**
     * 没有权限操作操作其他组织信息
     */
    NO_ACCESS_OPERATE_OTHER_ORG(500, "NO_ACCESS_OPERATE_OTHER_ORG"),
    /**
     * 该课程暂未统计
     */
    COURSE_IS_NOT_YET_AVAILABLE(500, "COURSE_IS_NOT_YET_AVAILABLE"),
    /**
     * 资源超出了最大可使用空间
     */
    RES_OVER_TOTAL_SPACE(500, "RES_OVER_TOTAL_SPACE"),
    /**
     * 无法获取当前应用域名
     */
    CAN_NOT_GET_CURRENT_DOMAIN(500, "CAN_NOT_GET_CURRENT_DOMAIN"),
    /**
     * 领取任务奖励成功
     */
    RECEIVE_TASK_REWARD_SUCCESS(200, "RECEIVE_TASK_REWARD_SUCCESS"),
    /**
     * 领取任务奖励失败
     */
    RECEIVE_TASK_REWARD_FAILED(500, "RECEIVE_TASK_REWARD_FAILED"),
    /**
     * 该课程标准下已有课程内容，无法删除
     */
    EXIST_CONTENT_IN_THE_CURRICULUM_STANDARD_CAN_NOT_DELETE(500,
            "EXIST_CONTENT_IN_THE_CURRICULUM_STANDARD_CAN_NOT_DELETE"),

    /**
     * 课程内容为空，请先备课
     */
    CURRICULUM_CONTENT_IS_NULL_PLEASE_PREPARE_CLASS(500, "CURRICULUM_CONTENT_IS_NULL_PLEASE_PREPARE_CLASS"),

    /**
     * 所选课程内容下已有关联资源，无法删除
     */
    THERE_ARE_RELATED_RESOURCES_CAN_NOT_DELETE(500, "THERE_ARE_RELATED_RESOURCES_CAN_NOT_DELETE"),

    /**
     * 您已经加入课程啦
     */
    YOU_HAVE_JOINED_THE_COURSE(500, "YOU_HAVE_JOINED_THE_COURSE"),

    /**
     * 本年级学制下班级名称重复
     */
    GRADE_EDU_SYSTEM_CLASS_NAME_EXIST(500, "GRADE_EDU_SYSTEM_CLASS_NAME_EXIST"),
    /**
     * 班级下有学生，无法删除
     */
    CLASS_CONTAINS_STUDENT_CAN_NOT_DELETE(500, "CLASS_CONTAINS_STUDENT_CAN_NOT_DELETE"),
    /**
     * 专业下有班级，无法删除
     */
    MAJOR_CONTAINS_CLASS_CAN_NOT_DELETE(500, "MAJOR_CONTAINS_CLASS_CAN_NOT_DELETE"),
    /**
     * 数据异常
     */
    DATA_EXCEPTION(500, "DATA_EXCEPTION");



    private final int code;
    private final String value;

    private MessageCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int code() {
        return code;
    }

    public String val() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    /**
     * 获取i18n的提示信息
     *
     * @return
     */
    public String msg() {
        String userLanguage = WebUtils.getCurrentUserLanguage();
        if (StringUtils.isEmpty(userLanguage)) {
            userLanguage = LocaleContextHolder.getLocale().getLanguage();
        }
        return Resources.getMessage(this.val(), userLanguage);
    }
}
