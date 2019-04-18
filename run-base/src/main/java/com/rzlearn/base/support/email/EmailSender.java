package com.rzlearn.base.support.email;

import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

/**
 * <p>ClassName:EmailSender</p>
 * <p>Description:邮件引擎</p>
 *
 * @author JiPeigong
 * @date 2018 -07-11 10:09:06
 */
@Slf4j
public final class EmailSender {

    /**
     * MIME邮件对象
     */
    private MimeMessage mimeMsg;

    /**
     * 邮件会话对象
     */
    private Session session;

    /**
     * 系统属性
     */
    private Properties props;

    /**
     *
     */
    private Multipart mp;

    /**
     * Instantiates a new Email sender.
     *
     * @param host     the host
     * @param user     the user
     * @param password the password
     * @param auth     the auth
     * @param port     the port
     */
    public EmailSender(String host, String user, String password, String auth, String port) {
        try {
            if (props == null) {
                props = System.getProperties();
            }
            props.put("mail.smtp.host", host);
            props.put("mail.user", user);
            props.put("mail.password", password);
            props.put("mail.smtp.auth", auth);
            props.put("mail.smtp.port", port);
            createMimeMessage();
        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    /**
     * 创建MIME邮件对象
     *
     * @return boolean boolean
     */
    public boolean createMimeMessage() {
        try {
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 获得邮件会话对象
            session = Session.getInstance(props, authenticator);
        } catch (Exception e) {
            return false;
        }
        try {
            // 创建MIME邮件对象
            mimeMsg = new MimeMessage(session);
            mp = new MimeMultipart();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置主题
     *
     * @param mailSubject String
     * @return boolean subject
     */
    public boolean setSubject(String mailSubject) {
        try {
            mimeMsg.setSubject(mailSubject, "UTF-8");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置内容
     *
     * @param mailBody String
     * @return the body
     */
    public boolean setBody(String mailBody) {
        try {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("" + mailBody, "text/html;charset=UTF-8");
            mp.addBodyPart(bp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置附件
     *
     * @param filename the filename
     * @return boolean boolean
     */
    public boolean addFileAffix(String filename) {
        try {
            BodyPart bp = new MimeBodyPart();
            FileDataSource fileds = new FileDataSource(filename);
            bp.setDataHandler(new DataHandler(fileds));
            bp.setFileName(MimeUtility.encodeText(fileds.getName()));
            mp.addBodyPart(bp);
            return true;
        } catch (Exception e) {
            log.error(filename, e);
            return false;
        }
    }

    /**
     * 设置发信人
     *
     * @param from the from
     * @return from from
     */
    public boolean setFrom(String from) {
        try {
            // 设置发件人
            mimeMsg.setFrom(new InternetAddress(from));
            Address[] replayArr = new Address[1];
            replayArr[0] = new InternetAddress(from);
            mimeMsg.setReplyTo(replayArr);
            return true;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return false;
        }
    }

    /**
     * 设置收信人
     *
     * @param to the to
     * @return to to
     */
    public boolean setTo(String to) {
        if (to == null) {
            return false;
        }
        try {
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            return true;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return false;
        }
    }

    /**
     * 设置抄送人
     *
     * @param copyto the copyto
     * @return copy to
     */
    public boolean setCopyTo(String copyto) {
        if (copyto == null) {
            return false;
        }
        try {
            mimeMsg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(copyto));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 发送邮件
     *
     * @return the boolean
     */
    public Boolean sendOut() {
        Boolean result = true;
        try {
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            Transport.send(mimeMsg);
        } catch (MessagingException e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

}
