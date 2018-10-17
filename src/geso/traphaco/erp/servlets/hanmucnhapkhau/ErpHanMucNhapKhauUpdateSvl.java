package geso.traphaco.erp.servlets.hanmucnhapkhau;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hanmucnhapkhau.IErpHanMucNhapKhau;
import geso.traphaco.erp.beans.hanmucnhapkhau.IErpHanMucNhapKhauList;
import geso.traphaco.erp.beans.hanmucnhapkhau.imp.ErpHanMucNhapKhau;
import geso.traphaco.erp.beans.hanmucnhapkhau.imp.ErpHanMucNhapKhauList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ErpHanMucNhapKhauUpdateSvl")
public class ErpHanMucNhapKhauUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public ErpHanMucNhapKhauUpdateSvl()
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility util;
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
			String nextJSP = "";
			util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			
			IErpHanMucNhapKhau lnccBean = new ErpHanMucNhapKhau();
			
			lnccBean.setUserId(userId);
			if (request.getQueryString().indexOf("update") >= 0)
			{
				lnccBean.setId(id);
				lnccBean.Create_Sanpham();
				lnccBean.Init();
				nextJSP = "/TraphacoHYERP/pages/Erp/ERPHanMucNhapKhauNew.jsp";
			}
			session.setAttribute("obj", lnccBean);
			response.sendRedirect(nextJSP);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do Post ErpLoaiNhaCungCapUpdateSvl!");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if (!util.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
//			String nextJSP = "";
			IErpHanMucNhapKhau listhmnkBean = new ErpHanMucNhapKhau();
			String Id = util.antiSQLInspection(request.getParameter("id"));
			String listsanpham = util.antiSQLInspection(request.getParameter("listsanpham"));
			String soluong = util.antiSQLInspection(request.getParameter("soluong"));
			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			String soDangKy = util.antiSQLInspection(request.getParameter("soDangKy"));
			
			String action = request.getParameter("action");
			
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			listhmnkBean.setId(Id);
			System.out.println("Id: " + Id);
			if(Id==null){Id="";}
			listhmnkBean.setSanPham_Fk(listsanpham);
			float sl=0;
			try
			{
				sl=Float.parseFloat(soluong.replaceAll(",",""));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			listhmnkBean.setSoluong(sl);
			listhmnkBean.setTuNgay(tungay);
			listhmnkBean.setUserId(userId);
			listhmnkBean.setDenNgay(denngay);
			listhmnkBean.setSoDangKy(soDangKy);
			
			 if(action.equals("save"))
			    {		
				 	IErpHanMucNhapKhauList listhmnk=new ErpHanMucNhapKhauList();
			    	if(Id.length() ==0)	// save cua tao moi
			    	{
			    		if(!listhmnkBean.Save())	// khong thuc hien duoc ham save
			    		{
			    			System.out.println("khong save");
			    			listhmnkBean.Init();
			    			listhmnkBean.Create_Sanpham();
			    			session.setAttribute("obj", listhmnkBean);
			    			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHanMucNhapKhauNew.jsp");
			    		}
			    		else	// save thanh cong
			    		{
			    			
			    			listhmnk.Init("");
			    			listhmnk.Create_Sanpham();
			    			session.setAttribute("obj", listhmnk);
			    			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHanMucNhapKhau.jsp");
			    		}
			    	}
			    	else 
			    	{
			    		if(!listhmnkBean.edit())
		    			{
		    				// luu o thanh cong
			    			listhmnkBean.Init();
			    			listhmnkBean.Create_Sanpham();
			    			session.setAttribute("obj", listhmnkBean);
			    			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHanMucNhapKhauNew.jsp");
		    			}
		    			else
		    			{
		    				listhmnk.Init("");
			    			listhmnk.Create_Sanpham();
			    			session.setAttribute("obj", listhmnk);
			    			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHanMucNhapKhau.jsp");
		    			}
			    	}
			    	
			    }
		}
	}
}
