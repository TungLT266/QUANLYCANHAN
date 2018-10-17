package geso.traphaco.erp.servlets.xoakhachhangtt;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.xoakhachhangtt.IHoadon;
import geso.traphaco.erp.beans.xoakhachhangtt.IHoadonct;
import geso.traphaco.erp.beans.xoakhachhangtt.imp.Hoadon;
import geso.traphaco.erp.beans.xoakhachhangtt.imp.Hoadonct;

import geso.traphaco.erp.beans.xoakhachhangtt.*;
import geso.traphaco.erp.beans.xoakhachhangtt.imp.*;

import java.io.IOException;
import java.io.PrintWriter;
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

public class ErpBangkethutienUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	PrintWriter out;
	
    public ErpBangkethutienUpdateSvl() {
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
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    String id = util.getId(querystring);  	
			IErpBangkethutien tthdBean = new ErpBangkethutien(id);
	        tthdBean.setUserId(userId);
	        tthdBean.setCongtyId((String)session.getAttribute("congtyId"));
	        tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
			tthdBean.setDoituongId(session.getAttribute("doituongId"));
	        
	        String nextJSP = "";
	        
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	tthdBean.initDisplay();
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangKeThuTienDisplay.jsp";
	        }
	        else if(request.getQueryString().indexOf("update") >= 0 )
	        {
	        	tthdBean.init();
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangKeThuTienUpdate.jsp";
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
			IErpBangkethutien tthdBean;

			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));

			if(id == null)
			{  	
				tthdBean = new ErpBangkethutien("");
			}
			else
			{
				tthdBean = new ErpBangkethutien(id);
			}

			tthdBean.setUserId(userId);
			tthdBean.setCongtyId((String)session.getAttribute("congtyId"));
			tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
			tthdBean.setDoituongId(session.getAttribute("doituongId"));

			String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
			if (ngaychungtu == null || ngaychungtu == "")
				ngaychungtu = this.getDateTime();				
			tthdBean.setNgaychungtu(ngaychungtu);

			String ngayghiso = util.antiSQLInspection(request.getParameter("ngayghiso"));
			if (ngayghiso == null || ngayghiso == "")
				ngayghiso = ngaychungtu;				
			tthdBean.setNgayghiso(ngayghiso);

			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";				
			tthdBean.setGhichu(ghichu);
			
			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null)
				tungay = "";				
			tthdBean.setTungay(tungay);
			
			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null)
				denngay = "";				
			tthdBean.setDenngay(denngay);

			String nvgnId = util.antiSQLInspection(request.getParameter("nvgnId"));
			if (nvgnId == null)
				nvgnId = "";				
			tthdBean.setNvgnId(nvgnId);
			
			String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
			if (ddkdId == null)
				ddkdId = "";				
			tthdBean.setDdkdId(ddkdId);
			
			String makhachhang = util.antiSQLInspection(request.getParameter("makhachhang"));
			if (makhachhang == null)
				makhachhang = "";				
			tthdBean.setMakhachhang(makhachhang);
			
			String khuvucId = util.antiSQLInspection(request.getParameter("khuvucId"));
			if (khuvucId == null)
				khuvucId = "";				
			tthdBean.setKhuvucId(khuvucId);
			
			String quanhuyenId = util.antiSQLInspection(request.getParameter("quanhuyenId"));
			if (quanhuyenId == null)
				quanhuyenId = "";				
			tthdBean.setQuanhuyenId(quanhuyenId);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
			tthdBean.setNppId(nppId);
			
			String tongthanhtoan = util.antiSQLInspection(request.getParameter("tongthanhtoan"));
			if (tongthanhtoan == null)
				tongthanhtoan = "0";				
			tthdBean.settongtientt(tongthanhtoan.replaceAll(",", ""));
			
			String htttId = util.antiSQLInspection(request.getParameter("htttId"));
			if (htttId == null)
				htttId = "";				
	    	tthdBean.setHtttId(htttId);
			
			tthdBean.setCongtyId(session.getAttribute("congtyId").toString());
			
			
			//Luu lai hoa don
	    	String[] idHd = request.getParameterValues("idHd");
	    	String[] khId = request.getParameterValues("khId");
	    	String[] isNPP = request.getParameterValues("isNPP");
			String[] sohd = request.getParameterValues("sohd");
			String[] kbhid = request.getParameterValues("kbhid");	
			String[] avat = request.getParameterValues("avat");
			String[] thanhtoan = request.getParameterValues("thanhtoan");
			String[] ptck = request.getParameterValues("ptck");
			String[] tienck = request.getParameterValues("tienck");
			String[] thucthu = request.getParameterValues("thucthu");
			String[] ghichuhd = request.getParameterValues("ghichuhd");
						
			List<IHoadon> hdList =  new ArrayList<IHoadon>();
			
			if(idHd != null)
			{
				IHoadon hoadon = null;
				IHoadonct hoadonct = null;
				int m = 0;
				while(m < idHd.length)
				{
					if(thanhtoan != null){
						hoadon = new Hoadon();
						
						hoadon.setId(idHd[m]);
						hoadon.setKhId(khId[m]);
						hoadon.setKenhId(kbhid[m]);
						hoadon.setTiengocHD(avat[m]);
						hoadon.setThanhtoan(thanhtoan[m]);
						hoadon.setptck(ptck[m]);
						hoadon.settienck(tienck[m]);
						hoadon.setthucthu(thucthu[m]);
						hoadon.setghichu(ghichuhd[m]);
						hoadon.setisNPP(isNPP[m]);
												
						String[] maspct = request.getParameterValues("maspct_"+idHd[m]);
						String[] hdIdct = request.getParameterValues("hdIdct_"+idHd[m]);
				    	String[] thanhtienhdct = request.getParameterValues("thanhtienhdct_"+idHd[m]);
				    	
						String[] thanhtienttct = request.getParameterValues("thanhtienttct_"+idHd[m]);				
						String[] ptckct = request.getParameterValues("ptckct_"+idHd[m]);
						String[] tienckct = request.getParameterValues("tienckct_"+idHd[m]);
						String[] thucthuct = request.getParameterValues("thucthuct_"+idHd[m]); 
						
						String[] dongiact = request.getParameterValues("dongiact_"+idHd[m]);
						String[] sotienckct = request.getParameterValues("sotienckct_"+idHd[m]);
						String[] vatct = request.getParameterValues("vatct_"+idHd[m]);
						String[] isdathanhtoanct = request.getParameterValues("isdathanhtoanct_"+idHd[m]);
						
						int k = 0;
						List<IHoadonct> HdctList = new ArrayList<IHoadonct>();
						System.out.println("m:"+idHd[m]);
						while (k < maspct.length)
						 {													 
							 hoadonct = new Hoadonct();
							 hoadonct.sethdId(hdIdct[k]);
							 hoadonct.setspId(maspct[k]);
							 hoadonct.setThanhtienhd(thanhtienhdct[k]);
							 hoadonct.setThanhtoan(thanhtienttct[k]);
							 hoadonct.setptchietkhau(ptckct[k]);
							 hoadonct.setsotienck(tienckct[k]);
							 hoadonct.setThucthu(thucthuct[k]);
							 
							 hoadonct.setDongiahd(dongiact[k]);
							 hoadonct.setsotienckhd(sotienckct[k]);
							 hoadonct.setvat(vatct[k]);
							 hoadonct.setIsdathanhtoan(isdathanhtoanct[k]);
							 							 
							 HdctList.add(hoadonct);
							 
							 k++;
						 }
						hoadon.setHoadonList(HdctList);
						
					}
					hdList.add(hoadon);
					m++;
				}
			}
			
			tthdBean.setHoadonRs(hdList);

			String action = request.getParameter("action");
			if(action.equals("save"))
			{	
				if(id == null) //tao moi
				{
					if(!tthdBean.createTTHD())
					{
						tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
						tthdBean.setDoituongId(session.getAttribute("doituongId"));
						tthdBean.createRs();

						session.setAttribute("tthdBean", tthdBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangKeThuTienNew.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						IErpBangkethutienList obj = new ErpBangkethutienList();
						obj.setUserId(userId);
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setLoainhanvien(session.getAttribute("loainhanvien"));
						obj.setDoituongId(session.getAttribute("doituongId"));
						obj.init("");

						session.setAttribute("obj", obj);

						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangKeThuTien.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!tthdBean.updateTTHD())
					{
						tthdBean.createRs();

						session.setAttribute("tthdBean", tthdBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangKeThuTienUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						//tthdBean.updateDonmuahang(tthdBean.getDonmuahangId());

						IErpBangkethutienList obj = new ErpBangkethutienList();
						obj.setUserId(userId);
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setLoainhanvien(session.getAttribute("loainhanvien"));
						obj.setDoituongId(session.getAttribute("doituongId"));
						obj.init("");

						session.setAttribute("obj", obj);

						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangKeThuTien.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				hdList.clear();
				tthdBean.createRs();

				String nextJSP;
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangKeThuTienNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangKeThuTienUpdate.jsp";   						
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
