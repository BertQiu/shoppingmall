package com.shoppingmall.web.controller.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoppingmall.entity.Member;
import com.shoppingmall.dao.AddressDao;

/**
 * 会员：设置默认地址的操作
 */
@WebServlet("/member/address/default")
public class AddressDefaultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//
		String id = request.getParameter("id");
		Member mbr = (Member)request.getSession().getAttribute("curr_mbr");
		
		//业务逻辑处理
		AddressDao service = new AddressDao();
		service.updateDefault(mbr.getId(), Integer.parseInt(id));
		
		//
		response.sendRedirect(request.getContextPath() + "/member/address/list");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}

}
