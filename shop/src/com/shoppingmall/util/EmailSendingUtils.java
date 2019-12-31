package com.shoppingmall.util;

import com.shoppingmall.common.CommonProperties;
import com.shoppingmall.common.EmailInfo;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bert Q
 * ClassName : EmailSendingUtils
 * Description TODO
 */
public class EmailSendingUtils {
    private static String defaultEmailText = "<p><span style=\"font-size: 32px;\"><span style=\"font-size: 24px;\">${time}！${username}!</span></span></p><hr/><p style=\"line-height: 23.8px; margin-top: 20px; margin-bottom: 20px; padding: 0px; font-family: Verdana, sans-serif; font-size: 14px; white-space: normal;\">欢迎您注册！<br/>你的登录邮箱为：${email.addr}。请点击以下网址完成绑定：</p><a style=\"display:block; line-height: 37.4px; margin-top: 20px; margin-bottom: 20px; padding: 0px; font-family: Verdana, sans-serif; white-space: normal;\">${binding_url}</a><p style=\"line-height: 23.8px; margin-top: 20px; margin-bottom: 20px; padding: 0px; font-family: Verdana, sans-serif; font-size: 14px; white-space: normal;\">链接在30分钟内有效，30分钟后需要重新激活邮箱，如不能直接点击打开，尝试复制到地址栏后打开！</p><p style=\"text-align: right; line-height: 23.8px; margin-top: 20px; margin-bottom: 20px; padding: 0px; font-family: Verdana, sans-serif; font-size: 14px; white-space: normal;\">---&nbsp; Bert Q&nbsp;&nbsp;</p>";

    private static Properties mailProperties = new Properties();

    static {
        try {
            mailProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendEmail(String username, EmailInfo email,String bindingUUID) {
        Session session = Session.getInstance(mailProperties);
        String bindingUrl = CommonProperties.WEBSITE_URL + "/member/email_binding?token=" + bindingUUID;
        try {
            String text = replaceEmailText(email.getText(), username,bindingUrl , email.getAddress());
            Message msg = new MimeMessage(session);
            msg.setSubject(email.getSubject());
            msg.setFrom(new InternetAddress(mailProperties.getProperty("mail.addresser")));//发件人邮箱
            msg.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(email.getAddress())); //收件人邮箱
            msg.setContent(text, mailProperties.getProperty("mail.contentType"));
            msg.saveChanges();

            Transport transport = session.getTransport();
            transport.connect(mailProperties.getProperty("mail.addresser"), mailProperties.getProperty("mail.key"));//发件人邮箱,授权码(可以在邮箱设置中获取到授权码的信息)

            transport.sendMessage(msg, msg.getAllRecipients());

            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendBindingEmail(String email,String username,String token) {
        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setAddress(email);
        emailInfo.setSubject("账号注册");
        emailInfo.setText(defaultEmailText);
        sendEmail(username, emailInfo, token);
    }

    private static String replaceEmailText(String text, String username, String captcha, String emailAddr) {
        int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (nowHour >= 6 && nowHour <= 11) {
            text = text.replace("${time}", "早上好");
        } else if (nowHour > 11 && nowHour <= 13) {
            text = text.replace("${time}", "中午好");
        } else if (nowHour >= 14 && nowHour <= 17) {
            text = text.replace("${time}", "下午好");
        } else {
            text = text.replace("${time}", "晚上好");
        }
        text = text.replace("${username}", username);
        text = text.replace("${email.addr}", emailAddr);
        text = text.replace("${binding_url}", captcha);

        return text;
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            EmailInfo emailInfo = new EmailInfo();
            emailInfo.setAddress("2803214629@qq.com");
            emailInfo.setSubject("知网账号注册");
            emailInfo.setText(defaultEmailText);
            String token = UUID.randomUUID().toString().substring(2, 8);
            System.out.println(token);
            EmailSendingUtils.sendEmail("刘jj", emailInfo, token);
            Thread.sleep((long) (500 + new Random().nextDouble() * 100));
        }
    }

    public static boolean isEmail(String email){
        if (null==email || "".equals(email)){
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }
}
