package geso.traphaco.distributor.servlets.thutienNPP;

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.distributor.beans.thutienNPP.*;
import geso.traphaco.distributor.beans.thutienNPP.imp.*;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpThutienNPPUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	PrintWriter out;
	dbutils db;
	
    public ErpThutienNPPUpdateSvl() {
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
			IErpThutienNPP tthdBean = new ErpThutienNPP(id);
	        tthdBean.setCtyId(ctyId);
			tthdBean.setUserId(userId);
	        
	        String nextJSP;
	        
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	//tthdBean.createRsLoc();
	        	tthdBean.init();
	        	nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThuTienNPPDisplay.jsp";
	        }
	        else
	        {
	        	//tthdBean.createRsLoc();
	        	tthdBean.init();
	        	nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThuTienNPPUpdate.jsp";
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
			IErpThutienNPP tthdBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
	       if(id == null)
		    {  	
		    	tthdBean = new ErpThutienNPP("");
		    }
		    else
		    {
		    	tthdBean = new ErpThutienNPP(id);
		    }

			//tthdBean = new ErpThutien(id);
			tthdBean.setCtyId(ctyId);
		    tthdBean.setUserId(userId);
			
		    String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null)
				tungay = ""	;		
	    	tthdBean.setTungay(tungay);
	    	
	    	  String denngay = util.antiSQLInspection(request.getParameter("denngay"));
				if (denngay == null)
					denngay = ""	;		
		    	tthdBean.setDenngay(denngay);
	    	
		    
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
			
			System.out.println("[ErpThutienUpdateSvl.doPost] nppId = " +tthdBean.getNppIds());
	    	String htttId = util.antiSQLInspection(request.getParameter("htttId"));
			if (htttId == null)
				htttId = "";				
	    	tthdBean.setHtttId(htttId);
	    	
	    	String noidungId = util.antiSQLInspection(request.getParameter("noidungId"));
			if (noidungId == null)
				noidungId = "";				
	    	tthdBean.setNoidungId(noidungId);
	    	
	    	String nganhangId = util.antiSQLInspection(request.getParameter("nganhangId"));
			if (nganhangId == null)
				nganhangId = "";				
	    	tthdBean.setNganhangId(nganhangId);
	    	
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
					System.out.println("hóa đơn: "+hdIds[i]);
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					tthdBean.setHdIds(_scheme);
				}
			}
			
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
			
			String[] noptienIds = request.getParameterValues("noptienId");
			if (noptienIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < noptienIds.length; i++)
				{
					_scheme += noptienIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					tthdBean.setNoptienIds(_scheme);
				}
			}
			
			
	    	String nguoinoptien = util.antiSQLInspection(request.getParameter("nguoinoptien"));
	    	if(nguoinoptien == null)
	    		nguoinoptien = "";
	    	tthdBean.setNguoinoptien(nguoinoptien); 
	    	
	    	String hinhthuctt = util.antiSQLInspection(request.getParameter("hinhthuctt"));
	    	if(hinhthuctt == null)
	    		hinhthuctt = "";
	    	tthdBean.setHinhthucTT(hinhthuctt);
	    	
	    	String noidungthanhtoan = util.antiSQLInspection(request.getParameter("ghichu"));
	    	if(noidungthanhtoan == null)
	    		noidungthanhtoan = "";
	    	tthdBean.setNoidungtt(noidungthanhtoan);
	    	
	    	String thuduoc = util.antiSQLInspection(request.getParameter("sotienhd"));
	    	if(thuduoc == null)
	    		thuduoc = "0";
	    	tthdBean.setThuduoc(thuduoc.replaceAll(",", ""));


	    	String bpkinhdoanh = util.antiSQLInspection(request.getParameter("bpkinhdoanh"));
	    	if(bpkinhdoanh == null)
	    		bpkinhdoanh = "0";
	    	tthdBean.setBpkinhdoanh(bpkinhdoanh);

	    	String chenhlech = util.antiSQLInspection(request.getParameter("chenhlech"));
	    	if(chenhlech == null)
	    		chenhlech = "0";
	    	tthdBean.setChenhlech(chenhlech.replaceAll(",", ""));

	    		String tongVND = "" + (Double.parseDouble(thuduoc.replaceAll(",", "")));
	    		tthdBean.setTongVND(tongVND);

	    	String sotienthanhtoan = util.antiSQLInspection(request.getParameter("tongtien"));
	    	if(sotienthanhtoan == null)
	    		sotienthanhtoan = "0";
	    	tthdBean.setSotientt(sotienthanhtoan.replaceAll(",", ""));

	    	String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
	    	if(sochungtu == null)
	    		sochungtu = "";
	    	tthdBean.setSochungtu(sochungtu);
	    	
	    	String sotiendanop = util.antiSQLInspection(request.getParameter("sotiendanop"));
	    	if(sotiendanop == null)
	    		sotiendanop = "0";
	    	tthdBean.setSotiendanop(sotiendanop);
	    	    	
	    	String sotienthuthem = util.antiSQLInspection(request.getParameter("sotienthuthem"));
	    	if(sotienthuthem == null)
	    		sotienthuthem = "0";
	    	tthdBean.setSotienthuthem(sotienthuthem);
	    	
	    	String chonthutuHD = util.antiSQLInspection(request.getParameter("chonthuHD"));
	    	if(chonthutuHD == null)
	    		chonthutuHD = "0";
	    	else
	    		chonthutuHD = "1";
	    	tthdBean.setChonthutuHD(chonthutuHD);
	    	
	    	//Thu tiền hóa đơn thêm
	    	String add_makh = util.antiSQLInspection(request.getParameter("add_makh"));
	    	if(add_makh==null)
	    		add_makh="";
	    	tthdBean.set_add_makh(add_makh);
	    	
	    	String add_madt = util.antiSQLInspection(request.getParameter("add_madt"));
	    	if(add_madt==null)
	    		add_madt="";
	    	tthdBean.set_add_madt(add_madt);
	    	
	    	String add_idhd = util.antiSQLInspection(request.getParameter("add_idhd"));
	    	if(add_idhd==null)
	    		add_idhd="";
	    	tthdBean.set_add_idhd(add_idhd);
	    	
	    	String add_sohoadon = util.antiSQLInspection(request.getParameter("add_sohoadon"));
	    	if(add_sohoadon==null)
	    		add_sohoadon="";
	    	tthdBean.set_add_sohoadon(add_sohoadon);
	    	
	    	String add_ngayhd = util.antiSQLInspection(request.getParameter("add_ngayhd"));
	    	if(add_ngayhd==null)
	    		add_ngayhd="";
	    	tthdBean.set_add_ngayhd(add_ngayhd);	    	
	    	
	    	String add_tongsotien = util.antiSQLInspection(request.getParameter("add_tongsotien"));
	    	if(add_tongsotien==null)
	    		add_tongsotien="";
	    	tthdBean.set_add_tongsotien(add_tongsotien);
	    	
	    	String add_thanhtoan = util.antiSQLInspection(request.getParameter("add_thanhtoan"));
	    	if(add_thanhtoan==null)
	    		add_thanhtoan="0";
	    	tthdBean.set_add_thanhtoan(add_thanhtoan);
	    	
	    	String add_conlai = util.antiSQLInspection(request.getParameter("add_conlai"));
	    	if(add_conlai==null)
	    		add_conlai="";
	    	tthdBean.set_add_conlai(add_conlai);
	    	
	    	String add_check = util.antiSQLInspection(request.getParameter("add_check"));
	    	System.out.println("check"+add_check);
	    	if(add_check==null)
	    		add_check="";
	    	tthdBean.set_add_check(add_check);
	    	
	    	//Hết thu tiền hóa đơn thêm
	    	
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
			String[] khId = request.getParameterValues("khId");
			String[] khMa = request.getParameterValues("khMa");
			String[] loaihd = request.getParameterValues("loaihd");
			
			List<IHoadonNPP> hdList =  new ArrayList<IHoadonNPP>();
			
			
			Hashtable<String,Double> hsTHANHTOAN = new Hashtable<String, Double>();
			boolean error = false;					 
				if(idHd != null)
				{	
					System.out.println("vào đây 1: ");
					IHoadonNPP hoadon = null;
					int m = 0;
					while(m < idHd.length)
					{							
							if(avat != null){
									hoadon = new HoadonNPP(idHd[m], "","", sohd[m], ngayhd[m], avat[m], "0", thanhtoan[m], "", "", "");
									System.out.println("aaaa"+ idHd[m]);
								    hoadon.setNppId(nhappId[m]);
								    hoadon.setNppMa(nppMa[m]);
								    hoadon.setKhId(khId[m]);
								    hoadon.setKhMa(khMa[m]);
								    hoadon.setLoaihd(loaihd[m]);
								    
								    if(thanhtoan[m].trim().length() > 0)
								    	hsTHANHTOAN.put(idHd[m], Double.parseDouble(thanhtoan[m].replaceAll(",", "")));
							}	
						hdList.add(hoadon);
					
						m++;
					}	
				}
				tthdBean.setHoadonRs(hdList);
				tthdBean.setNo_KhachHangList(hsTHANHTOAN);
		
			
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
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThuTienNPPNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						//tthdBean.updateDonmuahang(tthdBean.getDonmuahangId());
						
						IErpThutienNPPList obj = new ErpThutienNPPList();
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThuTienNPP.jsp";
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
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThuTienNPPUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						//tthdBean.updateDonmuahang(tthdBean.getDonmuahangId());
						
						IErpThutienNPPList obj = new ErpThutienNPPList();
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThuTienNPP.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else if(action.equals("changeTT")){
		    	tthdBean.setThuduoc("0");
		    	tthdBean.setSotiendanop("0");
		    	tthdBean.setSotienthuthem("0");
		    	tthdBean.setSotientt("0");
				hdList.clear();
				tthdBean.setHoadonRs(hdList);
				tthdBean.createRsLoc();
				tthdBean.createRs();

				String nextJSP;
				
				if (id== null){
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThuTienNPPNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThuTienNPPUpdate.jsp";   						
				}
				
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
				
			}
			else if(action.equals("locsearch")){
				tthdBean.createRsLoc();
				String nextJSP;
				if ( id== null){
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThuTienNPPNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThuTienNPPUpdate.jsp";   						
				}
				
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
			}
			else
			{	
				tthdBean.createRs();
				tthdBean.createRsLoc();
				String nextJSP;
				if ( id== null){
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThuTienNPPNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThuTienNPPUpdate.jsp";   						
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
