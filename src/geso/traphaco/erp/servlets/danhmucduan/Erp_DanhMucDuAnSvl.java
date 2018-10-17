package geso.traphaco.erp.servlets.danhmucduan;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.danhmucduan.*;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_DanhMucDuAnSvl extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	   
	   public Erp_DanhMucDuAnSvl() {
			super();
		}   
	   
	   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	   {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    	    
		    HttpSession session = request.getSession();	    
	      
		    Utility util = new Utility();
		    out = response.getWriter();
		    
		    Erp_DanhMucDuAnList obj = new Erp_DanhMucDuAnList();
		    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    String ctyId = (String)session.getAttribute("congtyId");
		    obj.setCongTyId(ctyId);
		    String id = util.getId(querystring);
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    
		    String action = util.getAction(querystring);
		    out.println(action);
		    

		    if (action.equals("delete")){	   		  	    	
		    	Erp_DanhMucDuAn danhMucDuAn = new Erp_DanhMucDuAn();
		    	danhMucDuAn.setId(id);
		    	danhMucDuAn.setCongTyId(ctyId);
		    	danhMucDuAn.init();
		    	danhMucDuAn.delete();
		    	obj.setMsg(danhMucDuAn.getMsg());
		    	danhMucDuAn.DbClose();
		    }else
	    	if (action.equals("chot"))
	    	{
	    		Erp_DanhMucDuAn danhMucDuAn = new Erp_DanhMucDuAn();
	    		danhMucDuAn.setNguoiSua(userId);
		    	danhMucDuAn.setId(id);
		    	danhMucDuAn.setCongTyId(ctyId);
		    	danhMucDuAn.init();
		    	danhMucDuAn.chot();
		    	obj.setMsg(danhMucDuAn.getMsg());
		    	danhMucDuAn.DbClose();
	    	}
		   	
			obj.init();
			
	    	session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);
	    		
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DanhMucDuAn.jsp");
		}  	
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    Utility util = new Utility();
		    String querystring = request.getQueryString();
		    
			HttpSession session = request.getSession();
		    String userId = request.getParameter("userId");
		    
		    String action = request.getParameter("action");
		    if (action == null){
		    	action = util.getAction(querystring);
		    	if (action == null)
		    		action = "";
		    }
		        
		    if (action.equals("new"))
		    {
		    	Erp_DanhMucDuAn obj = new Erp_DanhMucDuAn();
			    String ctyId = (String)session.getAttribute("congtyId");
			    
			    obj.setCongTyId(ctyId);
			    obj.init();
			    session.setAttribute("action", action);
		    	session.setAttribute("userId", userId);
		    	session.setAttribute("obj", obj);
	    		
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhMucDuAnUpdate.jsp";
	    		response.sendRedirect(nextJSP);
	    		return;
		    }
		    
		    Erp_DanhMucDuAnList obj = new Erp_DanhMucDuAnList();
		    if (action.equals("search"))
		    {
			    String ctyId = (String)session.getAttribute("congtyId");

			    obj.setCongTyId(ctyId);
			    
			    String ma = request.getParameter("ma");
			    obj.setMa(ma);
			    
			    String soChungTu = request.getParameter("soChungTu");
			    obj.setSoChungTu(soChungTu);
			    
			    String ngayBatDau = request.getParameter("ngayBatDau");
			    obj.setNgayBatDau(ngayBatDau);
			    
			    String ngayKetThuc = request.getParameter("ngayKetThuc");
			    obj.setNgayKetThuc(ngayKetThuc);
			    
			    String trangThai = request.getParameter("trangThai");
			    obj.setTrangThai(trangThai);
			    
			    System.out.println("maCCDC: " + ma + "\n soChungTu:" + soChungTu + "\n ngayBatDau:" + ngayBatDau + "\n ngayKetThuc: " + ngayKetThuc);
				obj.init();
				
		    	session.setAttribute("obj", obj);

	    		session.setAttribute("userId", userId);
		    		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DanhMucDuAn.jsp");	    	
		    }
		    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
		    {
			    String ctyId = (String)session.getAttribute("congtyId");

			    obj.setCongTyId(ctyId);

		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.init();
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DanhMucDuAn.jsp");
		    } 
		}   
			
		boolean kiemtra(dbutils db, String sql,String id)
		{
			
			String query = "select count(*) as num from " + sql + " where sanpham_fk ='"+ id +"'";
		
	    	ResultSet rs = db.get(query);
			try 
			{	
				//kiem tra ben san pham
				while(rs.next())
				{ 
					if(rs.getString("num").equals("0"))
						return false;
				}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		}
	}