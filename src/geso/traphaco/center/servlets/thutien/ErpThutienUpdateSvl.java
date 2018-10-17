package geso.traphaco.center.servlets.thutien;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.thutien.*;
import geso.traphaco.center.beans.thutien.imp.*;
import geso.traphaco.center.db.sql.dbutils;

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

public class ErpThutienUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	PrintWriter out;
	dbutils db;
	
    public ErpThutienUpdateSvl() {
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
			IErpThutien tthdBean = new ErpThutien(id);
	        tthdBean.setCtyId(ctyId);
			tthdBean.setUserId(userId);
	        
	        String nextJSP;
	        
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	tthdBean.initDisplay();
	        	nextJSP = "/TraphacoHYERP/pages/Center/ErpThuTienDisplay.jsp";
	        }
	        else
	        {
	        	tthdBean.init();
	        	nextJSP = "/TraphacoHYERP/pages/Center/ErpThuTienUpdate.jsp";
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
			IErpThutien tthdBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
	       if(id == null)
		    {  	
		    	tthdBean = new ErpThutien("");
		    }
		    else
		    {
		    	tthdBean = new ErpThutien(id);
		    }

			//tthdBean = new ErpThutien(id);
			tthdBean.setCtyId(ctyId);
		    tthdBean.setUserId(userId);
			
		    String phinganhang = util.antiSQLInspection(request.getParameter("phinganhang"));
			if (phinganhang == null )
				phinganhang= "0";				
	    	tthdBean.setChiphinganhang(phinganhang);
		    
		    String chinhanhTen = util.antiSQLInspection(request.getParameter("chinhanh"));
			if (chinhanhTen == null )
				chinhanhTen= "";				
	    	tthdBean.setChinhanhTen(chinhanhTen);
	    	
		    String nganhangTen = util.antiSQLInspection(request.getParameter("nganhang"));
			if (nganhangTen == null )
				nganhangTen= "";				
	    	tthdBean.setNganhangTen(nganhangTen);
		    
		    String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
			if (ngaychungtu == null || ngaychungtu == "")
				ngaychungtu = this.getDateTime();				
	    	tthdBean.setNgaychungtu(ngaychungtu);
	    	
	    	String ngayghiso = util.antiSQLInspection(request.getParameter("ngayghiso"));
			if (ngayghiso == null || ngayghiso == "")
				ngayghiso = this.getDateTime();				
	    	tthdBean.setNgayghiso(ngayghiso);
	    	
/*	    	String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
	    	tthdBean.setNppId(nppId);*/
	    
			String[] nppIds = request.getParameterValues("nppSearchId");
			if (nppIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < nppIds.length; i++)
				{
					_scheme += nppIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					tthdBean.setNppIds(_scheme);
				}
			}

	    	String htttId = util.antiSQLInspection(request.getParameter("htttId"));
			if (htttId == null)
				htttId = "";				
	    	tthdBean.setHtttId(htttId);
	    	
	    	String noidungId = util.antiSQLInspection(request.getParameter("noidungId"));
			if (noidungId == null)
				noidungId = "";				
	    	tthdBean.setNoidungId(noidungId);
	    	
	    	String lydonop = util.antiSQLInspection(request.getParameter("lydonop"));
			if (lydonop == null)
				lydonop = "";				
	    	tthdBean.setLydonop(lydonop);
	    	
	    	String hdId = util.antiSQLInspection(request.getParameter("hdId"));
			if (hdId == null)
				hdId = "";				
	    	tthdBean.setHdId(hdId);
	    	
			String[] hdIds = request.getParameterValues("hdId");
			if (hdIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < hdIds.length; i++)
				{
					_scheme += hdIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					tthdBean.setHdIds(_scheme);
				}
			}
	    	
	    	String nguoinoptien = util.antiSQLInspection(request.getParameter("nguoinoptien"));
	    	if(nguoinoptien == null)
	    		nguoinoptien = "";
	    	tthdBean.setNguoinoptien(nguoinoptien);
	    	
	    	String noidungthanhtoan = util.antiSQLInspection(request.getParameter("ghichu"));
	    	if(noidungthanhtoan == null)
	    		noidungthanhtoan = "";
	    	tthdBean.setNoidungtt(noidungthanhtoan);
	    	
	    	String thuduoc = util.antiSQLInspection(request.getParameter("thuduoc"));
	    	if(thuduoc == null)
	    		thuduoc = "0";
	    	tthdBean.setThuduoc(thuduoc.replaceAll(",", ""));

	    	String thuduocNT = util.antiSQLInspection(request.getParameter("thuduocNT"));
	    	if(thuduocNT == null)
	    		thuduocNT = "0";
	    	tthdBean.setThuduocNT(thuduocNT.replaceAll(",", ""));

	    	String bpkinhdoanh = util.antiSQLInspection(request.getParameter("bpkinhdoanh"));
	    	if(bpkinhdoanh == null)
	    		bpkinhdoanh = "0";
	    	tthdBean.setBpkinhdoanh(bpkinhdoanh);

	    	String sotaikhoan = util.antiSQLInspection(request.getParameter("sotaikhoan"));
	    	if(sotaikhoan == null)
	    		sotaikhoan = "";
	    	tthdBean.setSotaikhoan(sotaikhoan);

	    		String tongVND = "" + (Double.parseDouble(thuduoc.replaceAll(",", "")));
	    		tthdBean.setTongVND(tongVND);

	    	String sotienthanhtoan = util.antiSQLInspection(request.getParameter("sotienthanhtoan"));
	    	if(sotienthanhtoan == null)
	    		sotienthanhtoan = "0";
	    	tthdBean.setSotientt(sotienthanhtoan.replaceAll(",", ""));

	    	String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
	    	if(sochungtu == null)
	    		sochungtu = "";
	    	tthdBean.setSochungtu(sochungtu);
	    	
	    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
	    	if(tungay == null)
	    		tungay = "";
	    	tthdBean.setTungay(tungay);
	    	
	    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
	    	if(denngay == null)
	    		denngay = "";
	    	tthdBean.setDenngay(denngay);
	    		    	
	    	
    		//float sotienthanhtoanNT = Math.round((Float.parseFloat(sotienthanhtoan)/Float.parseFloat(tigia.replaceAll(",", "")))*100)/100;
    		String sotienttNT = util.antiSQLInspection(request.getParameter("sotienthanhtoanNT"));
    		if(sotienttNT == null) sotienttNT = "0";
	    	tthdBean.setSotienttNT(sotienttNT.replaceAll("",""));

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
	//		String[] conlai = request.getParameterValues("conlai");
	//		String[] tygiahd = request.getParameterValues("tygiahd");
			List<IHoadon> hdList =  new ArrayList<IHoadon>();
			
			boolean error = false;					 
				if(kyhieuhd != null)
				{		
					IHoadon hoadon = null;
					int m = 0;
					while(m < kyhieuhd.length)
					{							
							if(avat != null){
									hoadon = new Hoadon(idHd[m], "", kyhieuhd[m], sohd[m], ngayhd[m], avat[m], "0", thanhtoan[m], "", "", "");
								    hoadon.setNppId(nhappId[m]);
								    hoadon.setNppMa(nppMa[m]);
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
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpThuTienNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						//tthdBean.updateDonmuahang(tthdBean.getDonmuahangId());
						
						IErpThutienList obj = new ErpThutienList();
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Center/ErpThuTien.jsp";
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
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpThuTienUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						//tthdBean.updateDonmuahang(tthdBean.getDonmuahangId());
						
						IErpThutienList obj = new ErpThutienList();
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Center/ErpThuTien.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else if(action.equals("changeTT")){
		    	tthdBean.setThuduoc("0");
		    	
		    	tthdBean.setSotientt("0");
				hdList.clear();
				tthdBean.setHoadonRs(hdList);
				tthdBean.createRs();

				String nextJSP;
				System.out.println("So tien:"+ id);
				if (id== null){
					nextJSP = "/TraphacoHYERP/pages/Center/ErpThuTienNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Center/ErpThuTienUpdate.jsp";   						
				}
				
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
				
			}else
			{

				tthdBean.createRs();
				String nextJSP;
				if ( id== null){
					nextJSP = "/TraphacoHYERP/pages/Center/ErpThuTienNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Center/ErpThuTienUpdate.jsp";   						
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
