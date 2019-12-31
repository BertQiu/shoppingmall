package com.shoppingmall.util;


import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

/**
 * @author Bert Q
 * ClassName : VerifiyCodeUtils
 * Description 生成验证码图片
 */
public class CaptchaUtils {
    private static HttpServletResponse response;

    public static void setResponse(HttpServletResponse response) {
        CaptchaUtils.response = response;
    }

    /**
     * 注意：无参默认生成5个字符长度的验证码
     *
     * @return 返回String类型的5位验证码
     */
    public static String getStaticVerificationCode() {
        return getStaticVerificationCode(5);
    }

    /**
     * @param size 表示生成验证码的长度
     * @return 返回String类型的n位验证码
     */
    public static String getStaticVerificationCode(int size) {
        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置

        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);

        // 生成的验证码
        String code = specCaptcha.text();

        // 输出图片流
        try {
            specCaptcha.out(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String getDynamicsVerificationCode() {
        return getDynamicsVerificationCode(5);
    }

    public static String getDynamicsVerificationCode(int size) {

        // 三个参数分别为宽、高、位数
        GifCaptcha gifCaptcha = new GifCaptcha(130, 48, size);

        // 设置字体
        gifCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置

        // 设置类型，纯数字、纯字母、字母数字混合
        gifCaptcha.setCharType(Captcha.TYPE_DEFAULT);

        // 生成的验证码
        String code = gifCaptcha.text();

        // 输出图片流
        try {
            gifCaptcha.out(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }
}
