package com.shoppingmall.web.controller.member;

import com.shoppingmall.common.EmailBindingObject;
import com.shoppingmall.dao.MemberDao;
import com.shoppingmall.entity.Member;
import com.shoppingmall.util.EmailSendingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bert Q
 * ClassName : EmailServlet
 * Description TODO
 */
@WebServlet("/member/email_binding")
public class EmailBindingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        EmailBindingObject emailBindingObject = (EmailBindingObject) req.getSession().getAttribute("emailBindingObject");
        if (token == null || !token.equals(emailBindingObject.getToken())) {
            req.setAttribute("email_binding_msg", "邮箱绑定失败！");
            req.getRequestDispatcher("/member/profile.jsp").forward(req, resp);
            return;
        }

        //更新邮箱
        try {
            MemberDao memberDao = new MemberDao();
            memberDao.updateEmail(emailBindingObject);

            req.getSession().removeAttribute("email_binding_msg");

            Member mbr = (Member) req.getSession().getAttribute("curr_mbr");
            mbr.setEmail(emailBindingObject.getEmailAddr());

            req.setAttribute("email_binding_msg", "邮箱绑定成功！");
            req.getRequestDispatcher("/member/profile.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("email_binding_msg", "邮箱绑定失败！");
            req.getRequestDispatcher("/member/profile.jsp").forward(req, resp);
        }


    }
}
