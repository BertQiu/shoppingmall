package com.shoppingmall.web.controller.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoppingmall.dao.AddressDao;

/**
 * 会员的地址删除功能
 */
@WebServlet("/member/address/delete")
public class AddressDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//
		String id = request.getParameter("id");
		
		//
		AddressDao service = new AddressDao();
		service.delete(Integer.parseInt(id));
		
		//
		response.sendRedirect(request.getContextPath() + "/member/address/list");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
