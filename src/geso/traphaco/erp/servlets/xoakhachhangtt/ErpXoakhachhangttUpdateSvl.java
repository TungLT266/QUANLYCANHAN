package geso.traphaco.erp.servlets.xoakhachhangtt;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.xoakhachhangtt.*;
import geso.traphaco.erp.beans.xoakhachhangtt.imp.*;

import java.io.IOException;
import java.io.PrintWriter;
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

public class ErpXoakhachhangttUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	PrintWriter out;
	
    public ErpXoakhachhangttUpdateSvl() {
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
		    
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    String id = util.getId(querystring);  	
		    
		    String tungay = request.getParameter("tungaytk"); 
        	String sotien = request.getParameter("sotientk");
        	String denngay = request.getParameter("denngaytk");
        	String nccId = request.getParameter("khachhangtk");
        	String maphieu = request.getParameter("maphieutk");
        	String kbhId = request.getParameter("kenhbanhangtk");
        	String nhomkhId = request.getParameter("nhomkhachhangtk");
        	String trangthai = request.getParameter("trangthaitk");
        	if(tungay == null) tungay = "";
        	if(denngay == null) denngay = "";
        	if(sotien == null) sotien = "";
        	if(nccId == null) nccId = "";
        	if(maphieu == null) maphieu = "";
        	if(kbhId == null) kbhId = "";
        	if(nhomkhId == null) nhomkhId = "";
        	if(trangthai == null) trangthai = "";
        	
        	
			IErpXoakhachhangtt tthdBean = new ErpXoakhachhangtt(id, tungay, denngay,maphieu, nccId, kbhId, nhomkhId, sotien, trangthai );
	        tthdBean.setUserId(userId);
	        tthdBean.setCongtyId((String)session.getAttribute("congtyId"));
	        tthdBean.setnppdangnhap(util.getIdNhapp(userId));
	        tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
			tthdBean.setDoituongId(session.getAttribute("doituongId"));
	        
	        String nextJSP = "";
	        
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	tthdBean.initDisplay();
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangDisplay.jsp";
	        }
	        else if(request.getQueryString().indexOf("update") >= 0 )
	        {
	        	tthdBean.init();
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangUpdate.jsp";
	        }
	        else // Tạo mới
	        {
	        	
	        	
	        	System.out.println(tungay+ ","+sotien+","+ denngay +","+ nccId+","+maphieu);
	        	
	        	tthdBean = new ErpXoakhachhangtt(tungay, denngay,maphieu, nccId, kbhId, nhomkhId, sotien, trangthai );
	        	tthdBean.setUserId(userId);
	  	        tthdBean.setCongtyId((String)session.getAttribute("congtyId"));
	  	        tthdBean.setnppdangnhap(util.getIdNhapp(userId));
	  	        
	  	        tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
				tthdBean.setDoituongId(session.getAttribute("doituongId"));
	  	        
		    	tthdBean.createRs();
	        	String khId = util.antiSQLInspection(request.getParameter("khId"));
	        	String loaixtt = util.antiSQLInspection(request.getParameter("loai"));
	        	
	        	
	        	tthdBean.setNppId(khId);
	        	tthdBean.setLoaixnId(loaixtt);
	        	tthdBean.setTienteId("100000");
		    	tthdBean.createRs();
		    	
	    		nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangNew.jsp";

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
			IErpXoakhachhangtt tthdBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));

			String tungaytk = request.getParameter("tungaytk");
        	String sotientk = request.getParameter("sotientk");
        	String denngaytk = request.getParameter("denngaytk");
        	String nccIdtk = request.getParameter("khachhangtk");
        	String maphieu = request.getParameter("maphieutk");
        	String kbhId = request.getParameter("kenhbanhangtk");
        	String nhomkhId = request.getParameter("nhomkhtk");
        	String trangthai = request.getParameter("trangthaitk");
        	
        	if(tungaytk == null) tungaytk = "";
        	if(sotientk == null) sotientk = "";
        	if(denngaytk == null) denngaytk = "";
        	if(nccIdtk == null) nccIdtk = "";
        	if(maphieu == null) maphieu = "";
        	if(kbhId == null) kbhId = "";
        	if(nhomkhId == null) nhomkhId = "";
        	if(trangthai == null) trangthai = "";
        	
        	
		    if(id == null)
		    {  	
		    	tthdBean = new ErpXoakhachhangtt(tungaytk, denngaytk,maphieu, nccIdtk, kbhId, nhomkhId, sotientk, trangthai );
		    }
		    else
		    {
		    	tthdBean = new ErpXoakhachhangtt(id, tungaytk, denngaytk,maphieu, nccIdtk, kbhId, nhomkhId, sotientk, trangthai );
		    }
	
		    tthdBean.setUserId(userId);

			tthdBean.setCongtyId((String)session.getAttribute("congtyId"));
			
  	        tthdBean.setnppdangnhap(util.getIdNhapp(userId));
  	        
  	        tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
  	        
  	        tthdBean.setDoituongId(session.getAttribute("doituongId"));		    
			
		    String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
			if (ngaychungtu == null || ngaychungtu == "")
				ngaychungtu = this.getDateTime();				
	    	tthdBean.setNgaychungtu(ngaychungtu);
	    	
	    	 String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			 if (tungay == null )
				 tungay = "";
		     tthdBean.setTungay(tungay);
		    	
		     String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			 if (denngay == null )
				 denngay = "";
		     tthdBean.setDenngay(denngay);
		     
	    	String ngayghiso = util.antiSQLInspection(request.getParameter("ngayghiso"));
			if (ngayghiso == null || ngayghiso == "")
				ngayghiso = this.getDateTime();				
	    	tthdBean.setNgayghiso(ngayghiso);
	    	
	    	String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";				
	    	tthdBean.setNoidungtt(ghichu);
	    		    	
	    	String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
	    	tthdBean.setNppId(nppId);
	    		    	
	    	String loaixnId = util.antiSQLInspection(request.getParameter("loaiId"));
			if (loaixnId == null)
				loaixnId = "";				
	    	tthdBean.setLoaixnId(loaixnId);
	    	
	    	String tienteId = util.antiSQLInspection(request.getParameter("tienteId"));
			if (tienteId == null)
				tienteId = "";				
	    	tthdBean.setTienteId(tienteId);

	    	String doituongtamung = util.antiSQLInspection(request.getParameter("doituongtamung"));
			if (doituongtamung == null)
				doituongtamung = "0";				
	    	tthdBean.setDoiTuongTamUng(doituongtamung);
	    	
	    	String nccId = util.antiSQLInspection(request.getParameter("nccId"));
			if (nccId == null)
				nccId = "";				
	    	tthdBean.setNccId(nccId);
	    	
	    	String nvtuId = util.antiSQLInspection(request.getParameter("nvtuId"));
			if (nvtuId == null)
				nvtuId = "";				
	    	tthdBean.setNvtuId(nvtuId);
	    	
	    	String tongthanhtoan = util.antiSQLInspection(request.getParameter("tongthanhtoan").replaceAll(",", ""));
			if (tongthanhtoan == null)
				tongthanhtoan = "";				
	    	tthdBean.setTongthanhtoan(tongthanhtoan);

	    	String chenhlech = util.antiSQLInspection(request.getParameter("chenhlech").replaceAll(",", ""));
			if (chenhlech == null)
				chenhlech = "";				
	    	tthdBean.setChenhlech(chenhlech);
	    	
	    	String sotienungtruoc = util.antiSQLInspection(request.getParameter("sotienungtruoc").replaceAll(",", ""));
	    	if(sotienungtruoc == null)
	    		sotienungtruoc = "";
	    	tthdBean.setSotientt(sotienungtruoc);
	    	
	    	String tongthanhtoanVND = util.antiSQLInspection(request.getParameter("tongthanhtoanVND").replaceAll(",", ""));
			if (tongthanhtoanVND == null)
				tongthanhtoanVND = "0";				
	    	tthdBean.setTongthanhtoanVND(tongthanhtoanVND);

	    	String chenhlechVND = util.antiSQLInspection(request.getParameter("chenhlechVND").replaceAll(",", ""));
			if (chenhlechVND == null)
				chenhlechVND = "0";				
	    	tthdBean.setChenhlechVND(chenhlechVND);
	    	
	    	String sotienungtruocVND = util.antiSQLInspection(request.getParameter("sotienungtruocVND").replaceAll(",", ""));
	    	if(sotienungtruocVND == null)
	    		sotienungtruocVND = "0";
	    	tthdBean.setSotienttVND(sotienungtruocVND);
	    	
	        System.out.println("tungay:"+tungaytk);

	    	//Luu lai hoa don
	    	String[] idchungtu = request.getParameterValues("idchungtu");
	    	String[] khchungtu = request.getParameterValues("khchungtu");
			String[] sochungtutt = request.getParameterValues("khchungtu");
			String[] ngaychungtutt = request.getParameterValues("ngaychungtu");
			String[] tienchungtu = request.getParameterValues("tienchungtu");
			String[] tienthanhtoan = request.getParameterValues("tienthanhtoan");
			String[] tienconlai = request.getParameterValues("tienconlai");
			String[] tigiachungtu = request.getParameterValues("tigiachungtu");
			String[] loaict = request.getParameterValues("loaict");

			
			List<IHoadon> cttList =  new ArrayList<IHoadon>();
			
			if(idchungtu != null)
			{		
				IHoadon hoadon = null;
				int m = 0;
				while(m < idchungtu.length)
				{
					hoadon = new Hoadon(idchungtu[m], khchungtu[m], sochungtutt[m], ngaychungtutt[m], tienchungtu[m], tienthanhtoan[m], tienconlai[m]);
					hoadon.setTigiaHD(tigiachungtu[m]);
					hoadon.setLoaict(loaict[m]);
					cttList.add(hoadon);
					
					m++;
				}	
			}
			tthdBean.setCtttList(cttList);
			
				    	
	    	
	    	//Luu lai hoa don
	    	String[] idHd = request.getParameterValues("idHd");
	    	String[] kyhieuhd = request.getParameterValues("kyhieuhd");
			String[] sohd = request.getParameterValues("sohd");
			String[] ngayhd = request.getParameterValues("ngayhd");
			String[] avat = request.getParameterValues("avat");
			String[] thanhtoan = request.getParameterValues("thanhtoan");
			String[] conlai = request.getParameterValues("conlai");
			String[] loaiHd = request.getParameterValues("loaiHd");
			String[] tigiahd = request.getParameterValues("tigiaHD");
			
			List<IHoadon> hdList =  new ArrayList<IHoadon>();
			
			if(kyhieuhd != null)
			{		
				IHoadon hoadon = null;
				int m = 0;
				while(m < kyhieuhd.length)
				{
					hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m], thanhtoan[m], conlai[m]);
					hoadon.setLoaihd(loaiHd[m]);
					hoadon.setTigiaHD(tigiahd[m]);					
					hdList.add(hoadon);
					
					m++;
				}	
			}
			tthdBean.setHoadonRs(hdList);
			
			String action = request.getParameter("action");
			System.out.println("Action la: " + action);
			
			if(action.equals("save"))
			{	
				if(id == null) //tao moi
				{
					if(!tthdBean.createTTHD())
					{
	    		    	tthdBean.createRs();
	    		    
	    		    	session.setAttribute("tthdBean", tthdBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
			        	
						IErpXoakhachhangttList obj = new ErpXoakhachhangttList(tungaytk, denngaytk,maphieu, nccIdtk, kbhId, nhomkhId, sotientk, trangthai );
					    obj.setUserId(userId);
					    obj.setCongtyId((String)session.getAttribute("congtyId"));
					    obj.setnppdangnhap(util.getIdNhapp(userId));
					    
					    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
					    obj.setDoituongId(session.getAttribute("doituongId"));
					    
					    String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
						GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
						
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangTT.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!tthdBean.updateTTHD())
					{
						tthdBean.createRs();
		    		    
						session.setAttribute("tthdBean", tthdBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						//tthdBean.updateDonmuahang(tthdBean.getDonmuahangId());
			        	
						IErpXoakhachhangttList obj = new ErpXoakhachhangttList(tungaytk, denngaytk,maphieu, nccIdtk, kbhId, nhomkhId, sotientk, trangthai );
					    obj.setUserId(userId);
					    obj.setCongtyId((String)session.getAttribute("congtyId"));
					    obj.setnppdangnhap(util.getIdNhapp(userId));
					    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
					    obj.setDoituongId(session.getAttribute("doituongId"));
					    
					    String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
						GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
					    
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangTT.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				String nextJSP;
				
				tthdBean.setTongthanhtoan("0");
				tthdBean.setTongthanhtoanVND("0");
				tthdBean.setChenhlech("0");
				tthdBean.setChenhlechVND("0");
				tthdBean.setSotientt("0");
				tthdBean.setSotienttVND("0");
				
				cttList.clear();
				tthdBean.setCtttList(cttList);
				hdList.clear();
				tthdBean.setHoadonRs(hdList);
				
				tthdBean.createRs();
				
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpXoaKhachHangUpdate.jsp";   						
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
