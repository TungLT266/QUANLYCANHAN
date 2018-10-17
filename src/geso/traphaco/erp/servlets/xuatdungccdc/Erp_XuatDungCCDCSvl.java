package geso.traphaco.erp.servlets.xuatdungccdc;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.xuatdungccdc.Erp_XuatDungCCDC;
import geso.traphaco.erp.beans.xuatdungccdc.Erp_XuatDungCCDCList;
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

public class Erp_XuatDungCCDCSvl extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	   
	   public Erp_XuatDungCCDCSvl() {
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
		    
		    Erp_XuatDungCCDCList obj = new Erp_XuatDungCCDCList();
		    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    String ctyId = (String)session.getAttribute("congtyId");
		    String id = util.getId(querystring);
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    
		    String action = util.getAction(querystring);
		    out.println(action);
		    

		    if (action.equals("delete")){	   		  	    	
		    	Erp_XuatDungCCDC xuatDung = new Erp_XuatDungCCDC();
		    	xuatDung.setId(id);
		    	xuatDung.setCongTyId(ctyId);
		    	xuatDung.init();
		    	xuatDung.deleteXuatDung();
		    	obj.setMsg(xuatDung.getMsg());
		    	xuatDung.DbClose();
		    }else
	    	if (action.equals("chot"))
	    	{
	    		Erp_XuatDungCCDC xuatDung = new Erp_XuatDungCCDC();
	    		xuatDung.setNguoiSua(userId);
		    	xuatDung.setId(id);
		    	xuatDung.setCongTyId(ctyId);
		    	xuatDung.init();
		    	xuatDung.chotXuatDung();
		    	obj.setMsg(xuatDung.getMsg());
		    	xuatDung.DbClose();
	    	}
		   	
			obj.init();
			
	    	session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);
	    		
			response.sendRedirect("/TraphacoHYERP/pages/Erp/XuatDungCCDC.jsp");
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
		    	Erp_XuatDungCCDC obj = new Erp_XuatDungCCDC();
			    String ctyId = (String)session.getAttribute("congtyId");
			    
			    obj.setCongTyId(ctyId);
			    obj.init();
			    session.setAttribute("action", action);
		    	session.setAttribute("userId", userId);
		    	session.setAttribute("obj", obj);
	    		
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/XuatDungCCDCUpdate.jsp";
	    		response.sendRedirect(nextJSP);
	    		return;
		    }
		    
		    Erp_XuatDungCCDCList obj = new Erp_XuatDungCCDCList();
		    if (action.equals("search"))
		    {
			    String ctyId = (String)session.getAttribute("congtyId");

			    obj.setCongTyId(ctyId);
			    
			    String maCCDC = request.getParameter("maCCDC");
			    obj.setMaCCDC(maCCDC);
			    
			    String soChungTu = request.getParameter("soChungTu");
			    obj.setSoChungTu(soChungTu);
			    
			    String ngayBatDau = request.getParameter("ngayBatDau");
			    obj.setNgayBatDau(ngayBatDau);
			    
			    String ngayKetThuc = request.getParameter("ngayKetThuc");
			    obj.setNgayKetThuc(ngayKetThuc);
			    
			    String trangThai = request.getParameter("trangThai");
			    obj.setTrangThai(trangThai);
			    
			    System.out.println("maCCDC: " + maCCDC + "\n soChungTu:" + soChungTu + "\n ngayBatDau:" + ngayBatDau + "\n ngayKetThuc: " + ngayKetThuc);
				obj.init();
				
		    	session.setAttribute("obj", obj);

	    		session.setAttribute("userId", userId);
		    		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/XuatDungCCDC.jsp");	    	
		    	
		    }
		    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
		    {
			    String ctyId = (String)session.getAttribute("congtyId");

			    obj.setCongTyId(ctyId);

		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.init();
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/XuatDungCCDC.jsp");
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