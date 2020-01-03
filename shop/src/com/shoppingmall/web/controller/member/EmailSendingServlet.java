package com.shoppingmall.web.controller.member;

import com.shoppingmall.common.EmailBindingObject;
import com.shoppingmall.entity.Member;
import com.shoppingmall.util.EmailSendingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Bert Q
 * ClassName : EmailServlet
 * Description TODO
 */
@WebServlet("/member/email_sending")
public class EmailSendingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        if (!EmailSendingUtils.isEmail(email)) {
            req.setAttribute("email_binding_msg", "邮箱格式非法！");
            req.getRequestDispatcher("/member/profile.jsp").forward(req, resp);
            return;
        }
        Member curr_mbr = (Member) req.getSession().getAttribute("curr_mbr");
        if (curr_mbr.getEmail()!=null && curr_mbr.getEmail().equals(email)) {
            req.setAttribute("email_binding_msg", "邮箱重复绑定！");
            req.getRequestDispatcher("/member/profile.jsp").forward(req, resp);
            return;
        }

        String token = UUID.randomUUID().toString();
        EmailBindingObject emailBindingObject = new EmailBindingObject(curr_mbr.getId(), email, token);
        req.getSession().setAttribute("emailBindingObject", emailBindingObject);

        try {
            EmailSendingUtils.sendBindingEmail(email, username, token);
            req.setAttribute("email_binding_msg", "绑定邮件发送成功！");
            req.getRequestDispatcher("/member/profile.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("email_binding_msg", "绑定邮件发送失败！");
            req.getRequestDispatcher("/member/profile.jsp").forward(req, resp);
        }

    }


}
