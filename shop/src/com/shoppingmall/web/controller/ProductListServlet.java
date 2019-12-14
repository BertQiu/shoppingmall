package com.shoppingmall.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoppingmall.entity.Category;
import com.shoppingmall.entity.Product;
import com.shoppingmall.entity.common.Page;
import com.shoppingmall.dao.CategoryDao;
import com.shoppingmall.dao.ProductDao;

/**
 * 分类下的商品列表
 */
@WebServlet("/product/list")
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//////////////分页参数的处理
		int size = 10; //每页要显示的记录条数
		int number = 1; //要查询第number页
		String numberStr = request.getParameter("number");
		if(numberStr != null && !"".equals(numberStr)){
			number = Integer.parseInt(numberStr);
		}
		if(number < 1){
			number = 1;
		}
		//////////////类目编号的处理
		String idStr = request.getParameter("id");
		Integer id = Integer.valueOf(0);
		if(idStr != null && !"".equals(idStr)){
			id = Integer.valueOf(idStr);
			CategoryDao categoryDao = new CategoryDao();
			Category cate = categoryDao.findOne(id);
			request.setAttribute("cate", cate);
		}
		
		
		////////////////类目参数的处理
		ProductDao productDao = new ProductDao();
		Page<Product> page = null;
		String level = request.getParameter("level");
		if("top".equals(level)){ //查找指定一级类目下的产品列表
			page = productDao.findByTopCategory(id, number, size);
		}else if("second".equals(level)){//查找指定二级类目下的产品列表
			page = productDao.findBySubCategory(id, number, size);
		}else if("hots".equals(level)){ //查的热门的产品列表
			page = productDao.findAll(number, size);
		}else{ //查找指定二级类目ID下的产品列表
			page = productDao.findByTopCategory(id, number, size);
		}
		
		request.setAttribute("prodPage", page);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
