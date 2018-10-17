package geso.traphaco.center.servlets.khoasokinhdoanh;

import geso.traphaco.center.beans.khoasokd.IKhoasokinhdoanh;
import geso.traphaco.center.beans.khoasokd.imp.khoasokd;
import geso.traphaco.center.beans.khoasongay.IKhoasotudong;
import geso.traphaco.center.beans.khoasongay.imp.Khoasotudong;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;




import java.sql.*;


public class khoasokinhdoanhSvl extends HttpServlet  {
	
	private static final long serialVersionUID = 1L;
	  public khoasokinhdoanhSvl() 
	    {
	        super();
	    }
	  PrintWriter out; 
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			
			IKhoasokinhdoanh obj;
			
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    this.out  = response.getWriter();
		    	    
		    HttpSession session = request.getSession();	    

		    Utility util = new Utility();
		    out = response.getWriter();
		    	    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    obj = new khoasokd();
		    obj.setUserId(userId);
		    obj.init();
			session.setAttribute("obj", obj);
					
			String nextJSP = "/TraphacoHYERP/pages/Center/KhoaSoKinhDoanh.jsp";
			response.sendRedirect(nextJSP);
		}
		  public static boolean isNumericOnlyNumber(String str)
		    {
		        return str.matches("\\d+");
		    }  
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			System.out.println("DOPOST");
			IKhoasokinhdoanh obj = new khoasokd();
			dbutils db = new dbutils();
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    this.out  = response.getWriter();
		    	    
		    HttpSession session = request.getSession();	    
		    
		    Utility util = new Utility();
		    
		    String userId = util.antiSQLInspection(request.getParameter("userId"));
			obj = new khoasokd();
		    obj.setUserId(userId);
		    String action = request.getParameter("action");
		  
		    String ngaykskdtt = request.getParameter("ngaykskdtt");
		    obj.SetNgayKSKDTT(ngaykskdtt.trim());
		    String phutkskdtt = request.getParameter("phutkskdtt");
		    obj.SetPhutTT(phutkskdtt.trim());
		    String giokskdtt = request.getParameter("giokskdtt");
		    obj.SetGioTT(giokskdtt);
		    
		    String ngaykskdnpp = request.getParameter("ngaykskdnpp");
		    obj.SetNgayKSKDNPP(ngaykskdnpp.trim());
		    String giokskdnpp = request.getParameter("giokskdnpp");
		    obj.SetGioNPP(giokskdnpp.trim());
		    String phutkskdnpp = request.getParameter("phutkskdnpp");
		    obj.SetPhutNPP(phutkskdnpp.trim());
		    
		    String ngaykskdctv = request.getParameter("ngaykskdctv");
		    obj.SetNgayKSKDCTV(ngaykskdctv.trim());
		    String giokskdctv = request.getParameter("giokskdctv");
		    obj.SetGioCTV(giokskdctv.trim());
		    String phutkskdctv = request.getParameter("phutkskdctv");
		    obj.SetPhutCTV(phutkskdctv.trim());
		    
		    String trangthai1 = request.getParameter("trangthai1");
		    System.out.println("trangthai1 "+trangthai1);
		    if(trangthai1 == null)
		    	trangthai1 = "0";
		    else
		    	trangthai1 = "1";
		    obj.SetTrangthai1(trangthai1);
		    
		    String trangthai2 = request.getParameter("trangthai2");
		    System.out.println("trangthai2 "+trangthai2);
		    if(trangthai2 == null)
		    	trangthai2 = "0";
		    else
		    	trangthai2 = "1";
		    obj.SetTrangthai2(trangthai2);
		    
		    String trangthai3 = request.getParameter("trangthai3");
		    System.out.println("trangthai3 "+trangthai3);
		    if(trangthai3 == null)
		    	trangthai3 = "0";
		    else 
		    	trangthai3 = "1";
		    obj.SetTrangthai3(trangthai3);
		    ngaykskdctv = ngaykskdctv.trim();
		    ngaykskdnpp = ngaykskdnpp.trim();
		    ngaykskdtt = ngaykskdtt.trim();
		    
		    giokskdtt = giokskdtt.trim();
		    giokskdnpp = giokskdnpp.trim();
		    giokskdctv =   giokskdctv.trim();
		    
		    
			if(action.equals("save"))
			{ 
				// thiet lap TT
				String sql1 = "select * from TLKSKDTT";
				ResultSet rs =   db.get(sql1);
				try {
					if(!rs.next())
					{
						String sql = "Insert into TLKSKDTT(ngay,gio,nguoitao,ngaytao,nguoisua,ngaysua,trangthai,phut) "+
										"values("+ngaykskdtt+","+giokskdtt+","+userId+", '" +getDateTime()+"' ,"+userId+", '"+getDateTime()+"','"+trangthai1+"',"+phutkskdtt+")";
						if(!db.update(sql))
						{
							obj.setMsg("Lỗi "+sql);
							session.setAttribute("obj", obj);
							String nextJSP = "/TraphacoHYERP/pages/Center/KhoaSoKinhDoanh.jsp";
							response.sendRedirect(nextJSP);
						}
					}
					else
					{
						String sql = "update TLKSKDTT set ngay ="+ngaykskdtt+",gio = "+giokskdtt+", nguoisua = "+userId+", ngaysua = '"+getDateTime()+"', trangthai ='"+trangthai1+"', phut ="+phutkskdtt+"";
						if(!db.update(sql))
						{
							obj.setMsg("Lỗi "+sql);
							session.setAttribute("obj", obj);
							String nextJSP = "/TraphacoHYERP/pages/Center/KhoaSoKinhDoanh.jsp";
							response.sendRedirect(nextJSP);
						}
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					// Thiet lap NPP
					String sql2 = "select * from TLKSKDNPP";
					ResultSet rs1 =   db.get(sql2);
					try {
						if(!rs1.next())
						{
							String sql = "Insert into TLKSKDNPP(ngay,gio,phut,nguoitao,ngaytao,nguoisua,ngaysua,trangthai) "+
											"values("+ngaykskdnpp+","+giokskdnpp+",'" + phutkskdnpp + "',"+userId+", '" +getDateTime()+"' ,"+userId+", '"+getDateTime()+"','"+trangthai2+"')";
							if(!db.update(sql))
							{
								obj.setMsg("Lỗi "+sql);
								session.setAttribute("obj", obj);
								String nextJSP = "/TraphacoHYERP/pages/Center/KhoaSoKinhDoanh.jsp";
								response.sendRedirect(nextJSP);
							}
						}
						else
						{
							String sql = "update TLKSKDNPP set ngay ="+ngaykskdnpp+", gio = "+giokskdnpp+", phut = '" + phutkskdnpp + "', nguoisua = "+userId+", ngaysua = '"+getDateTime()+"',trangthai = '"+trangthai2+"'";
							if(!db.update(sql))
							{
								obj.setMsg("Lỗi "+sql);
								session.setAttribute("obj", obj);
								String nextJSP = "/TraphacoHYERP/pages/Center/KhoaSoKinhDoanh.jsp";
								response.sendRedirect(nextJSP);
							}
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
				}
					
				// thiet lap ctv
					String sql3 = "select * from TLKSKDCTV";
					ResultSet rs2 =   db.get(sql3);
					try {
						if(!rs2.next())
						{
							String sql = "Insert into TLKSKDCTV(ngay,gio,phut,nguoitao,ngaytao,nguoisua,ngaysua,trangthai) "+
											"values("+ngaykskdctv+","+giokskdctv+",'" + phutkskdctv + "',"+userId+",'" +getDateTime()+"',"+userId+", '"+getDateTime()+"','"+trangthai3+"')";
							if(!db.update(sql))
							{
								obj.setMsg("Lỗi "+sql);
								session.setAttribute("obj", obj);
								String nextJSP = "/TraphacoHYERP/pages/Center/KhoaSoKinhDoanh.jsp";
								response.sendRedirect(nextJSP);
							}
						}
						else
						{
							String sql = "update TLKSKDCTV set ngay ="+ngaykskdctv+", gio = "+giokskdctv+", phut = '" + phutkskdctv + "', nguoisua = "+userId+", ngaysua = '"+getDateTime()+"', trangthai = '"+trangthai3+"'";
							if(!db.update(sql))
							{
								obj.setMsg("Lỗi "+sql);
								session.setAttribute("obj", obj);
								String nextJSP = "/TraphacoHYERP/pages/Center/KhoaSoKinhDoanh.jsp";
								response.sendRedirect(nextJSP);
							}
							
						}
					} catch (SQLException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					obj.setMsg("Thiết lập khóa sổ kinh doanh thành công");
					obj.init();
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Center/KhoaSoKinhDoanh.jsp";
					response.sendRedirect(nextJSP);	
				
			}

			System.out.println("Ngay gio hien tai "+getDateTime());
			 
		}
		public String getDateTime()
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			return dateFormat.format(date);
		}
}
