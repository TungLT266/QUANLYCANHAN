package geso.traphaco.distributor.servlets.khachhangtt;

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.distributor.beans.khachhangtt.*;
import geso.traphaco.distributor.beans.khachhangtt.imp.*;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhachHangTraTruocUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	PrintWriter out;
	dbutils db;
	
    public KhachHangTraTruocUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
		
			this.out = response.getWriter();
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    String ctyId = (String)session.getAttribute("congtyId");
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    String id = util.getId(querystring);  	
			IKhachhangTT tthdBean = new KhachhangTT(id);
	        tthdBean.setCtyId(ctyId);
			tthdBean.setUserId(userId);
	        
	        String nextJSP;
	        
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	tthdBean.initDisplay();
	        	//tthdBean.init();
	        	nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangTTDisplay.jsp";
	        }
	        else
	        {
	        	tthdBean.init();
	        	nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangTTUpdate.jsp";
	        }
	 
	        session.setAttribute("tthdBean", tthdBean);
	        response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			String ctyId = (String)session.getAttribute("congtyId");
			IKhachhangTT tthdBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
	       if(id == null)
		    {  	
		    	tthdBean = new KhachhangTT("");
		    }
		    else
		    {
		    	tthdBean = new KhachhangTT(id);
		    }

			//tthdBean = new ErpThutien(id);
			tthdBean.setCtyId(ctyId);
		    tthdBean.setUserId(userId);
			
		    String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
			if (ngaychungtu == null || ngaychungtu == "")
				ngaychungtu = this.getDateTime();				
	    	tthdBean.setNgaychungtu(ngaychungtu);
	    	
	    	
	    	String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";				
	    	tthdBean.setNoidungtt(ghichu);
	    	
	    	String doituongId = util.antiSQLInspection(request.getParameter("doituongId"));
			if (doituongId == null)
				doituongId = "";				
	    	tthdBean.setDoituongId(doituongId);
	    	
			String[] khIds = request.getParameterValues("khSearchId");
			if (khIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < khIds.length; i++)
				{
					_scheme += khIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					tthdBean.setKhIds(_scheme);
				}
			}
			
			String[] nhanvienGNIds = request.getParameterValues("nvgnId");
			if (nhanvienGNIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < nhanvienGNIds.length; i++)
				{
					_scheme += nhanvienGNIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					tthdBean.setNhanvienGNIds(_scheme);
				}
			}
			
			String[] nhanvienBHIds = request.getParameterValues("nvbhId");
			if (nhanvienBHIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < nhanvienBHIds.length; i++)
				{
					_scheme += nhanvienBHIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					tthdBean.setNhanvienBHIds(_scheme);
				}
			}
	    	
	    	
	    	
	    	String thuduoc = util.antiSQLInspection(request.getParameter("thuduoc"));
	    	if(thuduoc == null)
	    		thuduoc = "0";
	    	tthdBean.setThuduoc(thuduoc.replaceAll(",", ""));


	    	String sotienthanhtoan = util.antiSQLInspection(request.getParameter("sotienthanhtoan"));
	    	if(sotienthanhtoan == null)
	    		sotienthanhtoan = "0";
	    	tthdBean.setSotientt(sotienthanhtoan.replaceAll(",", ""));


	    	//Luu lai hoa don
	    	String[] idHd = request.getParameterValues("idHd");
	    	String[] mahd = request.getParameterValues("mahd");
	    	String[] kyhieuhd = request.getParameterValues("kyhieuhd");
			String[] sohd = request.getParameterValues("sohd");
			String[] ngayhd = request.getParameterValues("ngayhd");
			String[] avat = request.getParameterValues("avat");
			String[] thanhtoan = request.getParameterValues("thanhtoan");
			String[] nhappId = request.getParameterValues("nppId");
			String[] nppMa = request.getParameterValues("nppMa");
			String[] khId = request.getParameterValues("khId");
			String[] khMa = request.getParameterValues("khMa");
			List<IHoadonKHTT> hdList =  new ArrayList<IHoadonKHTT>();
			
			boolean error = false;					 
				if(kyhieuhd != null)
				{		
					IHoadonKHTT hoadon = null;
					int m = 0;
					while(m < kyhieuhd.length)
					{							
							if(avat != null){
									hoadon = new HoadonKHTT(idHd[m], "", kyhieuhd[m], sohd[m], ngayhd[m], avat[m], "0", thanhtoan[m], "", "", "");
								    hoadon.setNppId(nhappId[m]);
								    hoadon.setNppMa(nppMa[m]);
								    hoadon.setKhId(khId[m]);
								    hoadon.setKhMa(khMa[m]);
							}	
						hdList.add(hoadon);
					
						m++;
					}	
				}
				tthdBean.setHoadonRs(hdList);
		
			
			String action = request.getParameter("action");
			System.out.println("Action la: " + action);
			
			if(action.equals("save") & error == false)
			{	
				if(id == null) //tao moi
				{
					System.out.println("I am here");
					if(!tthdBean.createTTHD())
					{
	    		    	tthdBean.createRs();
	    		    
	    		    	session.setAttribute("tthdBean", tthdBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangTTNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						//tthdBean.updateDonmuahang(tthdBean.getDonmuahangId());
						
						IKhachhangTTList obj = new KhachhangTTList();
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangTT.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					System.out.println("Da vao day...");
					if(!tthdBean.updateTTHD())
					{
						tthdBean.createRs();
		    		    
						session.setAttribute("tthdBean", tthdBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangTTUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						//tthdBean.updateDonmuahang(tthdBean.getDonmuahangId());
						
						IKhachhangTTList obj = new KhachhangTTList();
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangTT.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else if(action.equals("changeTT")){
				tthdBean.setSotientt("0");
		    	tthdBean.setThuduoc("0");		    			    	
				hdList.clear();
				tthdBean.setHoadonRs(hdList);
				tthdBean.createRs();

				String nextJSP;
				System.out.println("So tien:"+ id);
				if (id== null){
					nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangTTNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangTTUpdate.jsp";   						
				}
				
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
				
			}else
			{

				tthdBean.createRs();
				String nextJSP;
				if ( id== null){
					nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangTTNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangTTUpdate.jsp";   						
				}
				
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
}
