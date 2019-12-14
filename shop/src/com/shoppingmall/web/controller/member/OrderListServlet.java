package com.shoppingmall.web.controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoppingmall.entity.Member;
import com.shoppingmall.entity.Orders;
import com.shoppingmall.entity.common.Page;
import com.shoppingmall.dao.OrdersDao;

/**
 * 会员中心-->订单列表
 * @author qiujy
 */
@WebServlet("/member/orders")
public class OrderListServlet extends HttpServlet {
	private static final long serialVersionUID = 2964566478709855605L;
	private OrdersDao ordersDao = new OrdersDao();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int number = 1;
		int size = 10;
		String n = req.getParameter("number");
		if(n != null && !"".equals(n)){
			number = Integer.parseInt(n);
		}
		if(number < 1){
			number = 1;
		}
		String s = req.getParameter("size");
		if(s != null && !"".equals(s)){
			size = Integer.parseInt(s);
		}
		if(size <= 0){
			size = 10;
		}
		
		Member curr_mbr = (Member)req.getSession().getAttribute("curr_mbr");
		
		Page<Orders> page = ordersDao.findByBuyer(curr_mbr.getId(), number, size);
		req.setAttribute("page", page);
		req.getRequestDispatcher("/member/index.jsp").forward(req, resp);
	}
}
