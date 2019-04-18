package com.rzlearn.base.util;

import com.rzlearn.base.support.email.EmailSender;

/**
 * <p>ClassName:EmailUtil</p>
 * <p>Description:发送邮件工具类</p>
 *
 * @author JiPeigong
 * @date 2018-07-11 10:07:38
 **/
public final class EmailUtil {
    private EmailUtil() {
    }

    /**
     * 发送邮件
     */
    public static final boolean sendEmail(String host, String user, String password, String from, String to, String copyTo, String port, String topic, String content) {
        // 初始化邮件引擎
        EmailSender sender = new EmailSender(host, user, password, "true", port);
        if (!sender.setFrom(from)) {
            return false;
        }
        if (!sender.setTo(to)) {
            return false;
        }
        if (copyTo != null && !sender.setCopyTo(copyTo)) {
            return false;
        }
        if (!sender.setSubject(topic)) {
            return false;
        }
        if (!sender.setBody(content)) {
            return false;
        }
        return sender.sendOut();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
//        EmailSender emailSender = new EmailSender("smtpdm.aliyun.com", "runsch@sysmail.rzlearn.com", "RZlearn321", "true", "25");
//        emailSender.setFrom("runsch@sysmail.rzlearn.com");
//        emailSender.setBody("lalalala");
//        emailSender.setTo("jipeigong@qq.com");
//        emailSender.sendOut();
        EmailUtil.sendEmail("smtpdm.aliyun.com", "runsch@sysmail.rzlearn.com", "t6p2rSzagaU8XRnWRkEr/M==", "runsch@sysmail.rzlearn.com"
                , "jipeigong@qq.com", null, "25", "盈学网", "恭喜发财");
    }
}
