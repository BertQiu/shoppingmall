package com.shoppingmall.web.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alipay.api.AlipayApiException;
import com.shoppingmall.alipay.Alipay;
import com.shoppingmall.alipay.AlipayBean;
import com.shoppingmall.common.CommonProperties;
import com.shoppingmall.common.SnowflakeGenerator;
import com.shoppingmall.entity.Address;
import com.shoppingmall.entity.Member;
import com.shoppingmall.entity.Orders;
import com.shoppingmall.dao.AddressDao;
import com.shoppingmall.dao.OrdersDao;

/**
 * 保存订单数据到数据库，并跳转到支付界面
 */
@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //
        String address_id = request.getParameter("address_id");
        String remark = request.getParameter("remark");


        HttpSession session = request.getSession();
        Orders order = (Orders) session.getAttribute("curr_order");

        Member mbr = (Member) session.getAttribute("curr_mbr");
        order.setBuyer_id(mbr.getId());
        order.setRemark(remark);

        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        order.setNumber(df.format(new SnowflakeGenerator().nextId())); //通过雪花算法生成生成一个订单号
        order.setStatus(Orders.STATUS_WAIT_PAYMENT);// 待付款

        AddressDao addressDao = new AddressDao();
        Address address = addressDao.findOne(Integer.valueOf(address_id));
        order.setContact(address.getContact());
        order.setMobile(address.getMobile());
        order.setStreet(address.getStreet());
        order.setZipcode(address.getZipcode());

        //保存订单
        OrdersDao ordersDao = new OrdersDao();
        ordersDao.save(order);

        //----alipay----//
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(order.getNumber());

        //---
        System.out.println(ordersDao.getProductNameByOrder(order.getId()).toString()+"-123123---123");
        alipayBean.setSubject(ordersDao.getProductNameByOrder(order.getId()).toString());
        //---

        alipayBean.setTotal_amount(order.getPayment_price().toString());
        System.out.println(alipayBean);

        Alipay alipay = new Alipay();
        try {
            String result = alipay.pay(alipayBean);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(result);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //----alipay----//

        //

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
