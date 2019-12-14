package com.shoppingmall.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.shoppingmall.dao.OrdersDao;
import com.shoppingmall.entity.Orders;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author Bert Q
 * ClassName : AlipayCallbackServlet
 * Description TODO
 */
@WebServlet("/alipay/success.do")
public class AlipayCallbackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Map<String, String> params = MapWrapperUtils.change(req.getParameterMap());

        boolean signVerified;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayProperties.PUBLIC_KEY, "UTF-8", AlipayProperties.SIGN_TYPE);
            if (signVerified) {

                req.getSession().removeAttribute("cart");

                // 修改支付状态
                OrdersDao orderDao = new OrdersDao();
                orderDao.updateStatusByNumber(params.get("out_trade_no"), Orders.STATUS_SUCCESS_PAYMENT);
                //
                resp.sendRedirect("/member/orders");
            } else {
                resp.sendRedirect("/payFail.html");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            resp.sendRedirect("/payFail.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
