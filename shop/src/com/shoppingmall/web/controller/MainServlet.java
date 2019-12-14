package com.shoppingmall.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoppingmall.entity.News;
import com.shoppingmall.entity.Product;
import com.shoppingmall.entity.common.Page;
import com.shoppingmall.dao.NewsDao;
import com.shoppingmall.dao.ProductDao;

/**
 * 前台首页数据展示的Servlet
 */
@WebServlet("/main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//取出轮播图，3条
		NewsDao newsDao = new NewsDao();
		Page<News> pageTop = newsDao.findTopByPublic(1, 3);
		request.setAttribute("tops", pageTop.getItems());
		
		//取最新公告，7条
		Page<News> pageNews = newsDao.findByPublic(1, 7);
		request.setAttribute("news", pageNews.getItems());
		
		//取热门产品，6条
		ProductDao productDao = new ProductDao();
		Page<Product> pageHot = productDao.findHot(1, 6);
		request.setAttribute("hots", pageHot.getItems());
		
		//取一级类目“手机”下的商品列表，6条
		Page<Product> pagePhone = productDao.findByTopCategory(Integer.valueOf(1), 1, 6);
		request.setAttribute("phones", pagePhone.getItems());
		
		//取一级类目“电脑”下的商品列表，6条
		Page<Product> pageComputer = productDao.findByTopCategory(Integer.valueOf(2), 1, 6);
		request.setAttribute("computers", pageComputer.getItems());
		
		request.getRequestDispatcher("/main.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
