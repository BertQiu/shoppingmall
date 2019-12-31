package com.shoppingmall.web.controller;

import com.shoppingmall.util.CaptchaUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bert Q
 * ClassName : CaptachaServlet
 * Description TODO
 */
@WebServlet("/captcha/login")
public class CaptchaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/jpeg");//等同于response.setHeader("Content-Type", "image/jpeg");
        resp.setDateHeader("expries", -1);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");

        CaptchaUtils.setResponse(resp);
        String captcha = CaptchaUtils.getDynamicsVerificationCode();
        //将验证码存入session
        req.getSession().setAttribute("login_captcha",captcha.toLowerCase());
    }

}
