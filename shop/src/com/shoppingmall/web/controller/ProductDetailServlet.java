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
 * 商品的详情展示
 */
@WebServlet("/product_detail")
public class ProductDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//
		String idStr = request.getParameter("id");
		Integer id = Integer.valueOf(idStr);
		
		//
		ProductDao productDao = new ProductDao();
		Product product = productDao.findOne(id);
		request.setAttribute("product", product);
		
		//查询商品所属的类目对象
		Integer cate_id = product.getCate_id();
		CategoryDao categoryDao = new CategoryDao();
		Category cate = categoryDao.findOne(cate_id);
		request.setAttribute("cate", cate);
		
		//当前商品所属类目下的热门商品列表
		Page<Product> hotsPage = productDao.findBySubCategory(cate_id, 1, 3);
		request.setAttribute("hots", hotsPage.getItems());
		
		//
		request.getRequestDispatcher("/product_detail.jsp").forward(request, response);
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
