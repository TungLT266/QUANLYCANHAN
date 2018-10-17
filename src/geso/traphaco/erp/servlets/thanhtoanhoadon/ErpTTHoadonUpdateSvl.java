package geso.traphaco.erp.servlets.thanhtoanhoadon;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.erp.beans.thanhtoanhoadon.IDuyetthanhtoanhoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.IErpThanhtoanhoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.IErpThanhtoanhoadonList;
import geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.Duyetthanhtoanhoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.ErpThanhtoanhoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.ErpThanhtoanhoadonList;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.Hoadon;

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

public class ErpTTHoadonUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	PrintWriter out;
	dbutils db;
	
    public ErpTTHoadonUpdateSvl() {
        super();
    }

	/*//tthdBean.updateDonmuahang(tthdBean.getDonmuahangId());
	
	IErpThanhtoanhoadonList obj = new ErpThanhtoanhoadonList();
	obj.setUserId(userId);
	obj.setCongtyId(ctyId);
	obj.setnppdangnhap(util.getIdNhapp(userId));
    
	obj.init("");
    
	session.setAttribute("obj", obj);
			
	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDon.jsp";
	response.sendRedirect(nextJSP);*/

    

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
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    String id = util.getId(querystring);  	
			IErpThanhtoanhoadon tthdBean = new ErpThanhtoanhoadon(id);
			String ctyId = (String)session.getAttribute("congtyId");
			tthdBean.setCtyId(ctyId);
	        tthdBean.setUserId(userId);
	        tthdBean.setnppdangnhap(util.getIdNhapp(userId));
	    	String duyetchi = request.getParameter("duyetchi");
	    	System.out.println("request.getParameter(duyetchi); doget" +duyetchi);
			
	    	if(duyetchi == null)
				duyetchi = "";
	        tthdBean.setDuyetchi(duyetchi);  

	 

	        String nextJSP;
	        
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	tthdBean.init_display();
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDonDisplay.jsp";
	        }
	        else
	        {
	        	tthdBean.init();
//	        	if (tthdBean.getIsDNTT().trim().equals("1"))
//	        	{
//	        		nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDonDisplay.jsp";
//	        		session.setAttribute("action", "save");
//	        	}
//	        	else 
	        		nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDonUpdate.jsp";
	        }

	        session.setAttribute("tthdBean", tthdBean);
	        response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
    	
    	
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		String ctyId = (String)session.getAttribute("congtyId");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		
		String duyetchi = request.getParameter("duyetchi");
		if(duyetchi == null)
			duyetchi = "";
		
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			/*request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");*/
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			String action = request.getParameter("action");
			System.out.println("Action la: " + action);
			
	    	
		
			IErpThanhtoanhoadon tthdBean;
			
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("Id"));
			
			System.out.println("Id: " + id);
		    if(id == null)
		    {  	
		    	tthdBean = new ErpThanhtoanhoadon("");
		    }
		    else
		    {
		    	tthdBean = new ErpThanhtoanhoadon(id);
		    }
		    tthdBean.setDuyetchi(duyetchi);
			System.out.println("duyetdn1:"+duyetchi);

			tthdBean.setCtyId(ctyId);
		    tthdBean.setUserId(userId);
		    tthdBean.setnppdangnhap(util.getIdNhapp(userId));
			
		    boolean error = false; 
		    error = setParameters(tthdBean, request, session, util);
			
		
		    List<IHoadon> hdList = tthdBean.getHoadonRs();
		    
			if(action.equals("changncc") || action.equals("loaithanhtoan") )
			{
				//Thay doi loai thanh toan, ncc
				tthdBean.setHoadonRs(new  ArrayList<IHoadon>());
			}
			
			if(action.equals("save")  & error == false)
			{	
				if(id == null) //tao moi
				{
					if(!tthdBean.createTTHD())
					{
						System.out.println("[ErpTTHoadonUpdateSvl.doPost] Saved fail. msg = " + tthdBean.getMsg());
	    	    		tthdBean.createRs();
	    		    
	    	    		session.setAttribute("tthdBean", tthdBean);
	    	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDonNew.jsp";
	    	    		response.sendRedirect(nextJSP);
					}else
					{
						IErpThanhtoanhoadonList obj;
						obj = new ErpThanhtoanhoadonList();
			    		obj.setCongtyId(ctyId);
				    	obj.setUserId(userId);
				    	
				    	String ServerletName = "ErpThanhtoanhoadonSvl";
				    	
				    	String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
				    	geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
				    		
				    	obj.init("");
				    	
				    	session.setAttribute("obj", obj);
				    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDon.jsp");	
						session.setAttribute("doituong", obj.getDoiTuongChiPhiKhac());	  
					}
				
				}
				else
				{
					if(!tthdBean.updateTTHD())
					{	
						tthdBean.createRs();
		    		    
						session.setAttribute("tthdBean", tthdBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDonUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else 
					{	System.out.println("aaaaaaaaaaaaaaaaaaaa"+tthdBean.getDuyetchi());
						if(tthdBean.getDuyetchi().equals("1"))
						{ 
//						IErpThanhtoanhoadonList obj;
//						obj = new ErpThanhtoanhoadonList();
//			    		obj.setCongtyId(ctyId);
//				    	obj.setUserId(userId);
//				    	
//				    	String ServerletName = "ErpThanhtoanhoadonUpdateSvl";
//				    	String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
//				    	geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
//				    		
//				    	obj.init("");
//				    	
//				    	session.setAttribute("obj", obj);
//				    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDon.jsp");	
//						session.setAttribute("doituong", obj.getDoiTuongChiPhiKhac());	  
//						

					    IDuyetthanhtoanhoadon ddmhBean = new Duyetthanhtoanhoadon();
				   	    ddmhBean.setCtyId((String)session.getAttribute("congtyId"));
				   	    ddmhBean.setUserId(userId);
				   	    ddmhBean.setnppdangnhap(util.getIdNhapp(userId));
				   		
				    	String ServerletName = "ErpDuyetThanhToanHoaDonSvl";
				    	String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
				   	 
				    	geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(ddmhBean, searchQuery);
				   	    ddmhBean.init();
						// Data is saved into session
						session.setAttribute("dtthdBean", ddmhBean);

						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetThanhToanHoaDon.jsp";
				   		response.sendRedirect(nextJSP);		    	
						}	
						else
						{
				    	
						IErpThanhtoanhoadonList obj;
						obj = new ErpThanhtoanhoadonList();
			    		obj.setCongtyId(ctyId);
				    	obj.setUserId(userId);
				    	
				    	String searchQuery=util.getSearchFromHM(userId,"ErpThanhtoanhoadonUpdateSvl", session);
				    	geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
				    		
				    	obj.init("");
				    	
				    	session.setAttribute("obj", obj);
				    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDon.jsp");	
						session.setAttribute("doituong", obj.getDoiTuongChiPhiKhac());	  
						}
					
					}
				}
			}
			else if(action.equals("changeTT")){
				//tthdBean.setSotaikhoan("");
				/*tthdBean.setTigia("");
				tthdBean.setSotienHDNT("0");
				tthdBean.setSotienHD("0");
				tthdBean.setPhinganhangNT("0");
				tthdBean.setPhinganhang("0");
				tthdBean.setThueVATNT("0");
				tthdBean.setThueVAT("0");
				tthdBean.setSotienttNT("0");
				tthdBean.setSotientt("0");
				tthdBean.setChenhlech("0");*/
				tthdBean.taoMoiSoChungTu();
				
				hdList.clear();
				tthdBean.setHoadonRs(hdList);

				String nextJSP;
				tthdBean.createRs();

				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDonNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDonUpdate.jsp";   						
				}
			
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
				
			}else if(action.equals("changeTP") && tthdBean.getIsDNTT().trim().equals("1")){
		    	tthdBean.setPhinganhangNT("0");
		    	tthdBean.setPhinganhang("0");
		    	tthdBean.setThueVATNT("0");
		    	tthdBean.setThueVAT("0");
		    	tthdBean.setSotienttNT(tthdBean.getSotienHDNT());
		    	tthdBean.setSotientt(tthdBean.getSotienHD());
		    	
		    	double chenhlech = Double.parseDouble(tthdBean.getSotienHDNT().replaceAll(",", ""))*Double.parseDouble(tthdBean.getTigia().replaceAll(",", "")) - Double.parseDouble(tthdBean.getSotienHD().replaceAll(",", ""));
		    	tthdBean.setChenhlech("" + chenhlech);
				hdList.clear();
				tthdBean.setHoadonRs(hdList);

				tthdBean.createRs();
				String nextJSP;
				
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDonNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDonUpdate.jsp";   						
				}
				
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
				
			}else if(action.equals("changeHTTT") && tthdBean.getIsDNTT().trim().equals("1")){
				tthdBean.setSotaikhoan("");

				String nextJSP;
				hdList.clear();
				tthdBean.setHoadonRs(hdList);

				tthdBean.createRs();
				
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDonNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDonUpdate.jsp";   						
				}
				
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
			}else
			{
				String nextJSP;
				if(!error) {
					hdList.clear();
					tthdBean.setHoadonRs(hdList);
				}
				
				tthdBean.createRs();
				
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDonNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDonUpdate.jsp";   						
				}
				
				
				
				
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private boolean setParameters(IErpThanhtoanhoadon tthdBean, HttpServletRequest request, HttpSession session, Utility util) 
	{
		boolean error = false; 
		String isDNTT = util.antiSQLInspection(request.getParameter("isDNTT"));
		if (isDNTT == null || isDNTT == "")
			isDNTT = "0";				
    	tthdBean.setIsDNTT(isDNTT);
    	if (isDNTT.trim().equals("1"))
    	{
    		tthdBean.init();
    		session.setAttribute("nhomncccn", tthdBean.getNhomNCCCN());			
	    	session.setAttribute("doituong",tthdBean.getDoiTuongTamUng());
	    	session.setAttribute("loaithanhtoan", tthdBean.getLoaiThanhToan());	    	
	    	session.setAttribute("doituongkhac",tthdBean.getDoiTuongChiPhiKhac());	    	
	    	session.setAttribute("doituongdinhkhoan",tthdBean.getDoiTuongDinhKhoan());
    	}
    	
    	String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
		if (ngayghinhan == null || ngayghinhan == "")
			ngayghinhan = this.getDateTime();				
    	tthdBean.setNgayghinhan(ngayghinhan);
    	
    	String soChungTu = util.antiSQLInspection(request.getParameter("soChungTu"));
		if (soChungTu == null || soChungTu == "")
			soChungTu = "";				
    	tthdBean.setSoChungTu(soChungTu);
    	
    	String soChungTu_Chu = util.antiSQLInspection(request.getParameter("soChungTu_Chu"));
    	if(soChungTu_Chu == null)
    		soChungTu_Chu = "";
    	tthdBean.setSoChungTu_Chu(soChungTu_Chu);
    	
    	String soChungTu_So = util.antiSQLInspection(request.getParameter("soChungTu_So"));
    	if(soChungTu_So == null)
    		soChungTu_So = "";
    	tthdBean.setSoChungTu_So(soChungTu_So);
    	
    	String tigia = util.antiSQLInspection(request.getParameter("tigia"));
    	if(tigia == null)
    		tigia = "1";
    	tthdBean.setTigia(tigia);
    	
    	String noidungthanhtoan = util.antiSQLInspection(request.getParameter("noidungthanhtoan"));
    	if(noidungthanhtoan == null)
    		noidungthanhtoan = "";
    	System.out.println("noidungthanhtoan" +request.getParameter("noidungthanhtoan"));
    	tthdBean.setNoidungtt(noidungthanhtoan);
    	System.out.println("isDNTT :"+isDNTT);
    	if (isDNTT.trim().equals("0"))
    	{
	    	String doituongtamung = util.antiSQLInspection(request.getParameter("doituongtamung"));
	    	if(doituongtamung == null)
	    		doituongtamung = "1";
	    	tthdBean.setDoiTuongTamUng(doituongtamung);
	    	
	    	String doituongthanhtoan = util.antiSQLInspection(request.getParameter("doituongthanhtoan"));
	    	System.out.println("av" +doituongthanhtoan);
	    	if(doituongthanhtoan == null)
	    		doituongthanhtoan = "1";
	    	tthdBean.setDoiTuongChiPhiKhac(doituongthanhtoan);
	    	
	    	String Nhomncccn = util.antiSQLInspection(request.getParameter("nhomncccn"));
			if(Nhomncccn == null)
				Nhomncccn = "";
	    	tthdBean.setNhomNCCCN(Nhomncccn);
	    	
	    	String nccId = request.getParameter("nccId");
			if (nccId == null){
				nccId = "";		
			}
			tthdBean.setNccId(nccId);			
	
			String nhomnccId = request.getParameter("nhomnccId");
			if (nhomnccId == null){
				nhomnccId = "";		
			}
			tthdBean.setNhomNCCCNId(nhomnccId);	    	
			
			String doiTuongKhacId = request.getParameter("doiTuongKhacId");
			tthdBean.setDoiTuongKhacId(doiTuongKhacId);	  
			
			String khachHang_NPP_FK = request.getParameter("khachHang_NPP_FK");
			if (khachHang_NPP_FK == null){
				khachHang_NPP_FK = "";		
			}
			tthdBean.setKhachHang_NPP_FK(khachHang_NPP_FK);
			
			String dinhkhoanno = util.antiSQLInspection(request.getParameter("dinhkhoanno"));
			if(dinhkhoanno == null)
				dinhkhoanno = "";
			tthdBean.setDinhkhoanno(dinhkhoanno);
		
	    	dinhkhoanno = dinhkhoanno.split(" --")[0];
	    	
	    	tthdBean.setDinhkhoannoId(dinhkhoanno);
	    	
	    	String check = "";
	    	if(dinhkhoanno.length() > 1){
	    		check = CheckDoiTuongDinhKhoan(dinhkhoanno, tthdBean.getCtyId());
	    		tthdBean.setDoiTuongDinhKhoan(check);
	    	}
	    	
	    	String matendoituongdinhkhoan = util.antiSQLInspection(request.getParameter("doituongdinhkhoan"));
	    	if (matendoituongdinhkhoan == null)
	    		matendoituongdinhkhoan = "";
	    	tthdBean.setMaTenDoiTuongDinhKhoan(matendoituongdinhkhoan);
	    	
	    	matendoituongdinhkhoan = matendoituongdinhkhoan.split("--")[0];
	    	
	    	tthdBean.setDoituongdinhkhoanId(matendoituongdinhkhoan);
	    	
	    	String htttId = util.antiSQLInspection(request.getParameter("htttId"));
			if (htttId == null)
				htttId = "";				
	    	tthdBean.setHtttId(htttId);
	    	
	    	String thanhtoantuTV = util.antiSQLInspection(request.getParameter("thanhtoantuTV"));
	    	if(thanhtoantuTV == null)
	    		thanhtoantuTV = "0";
	    	tthdBean.setCheckThanhtoantuTV(thanhtoantuTV);
	    	
	    	String sotaikhoan = util.antiSQLInspection(request.getParameter("sotaikhoan"));
	    	if(sotaikhoan == null)
	    		sotaikhoan = "";
	    	tthdBean.setSotaikhoan(sotaikhoan);
	    	
	    	String sotaikhoan_tp = util.antiSQLInspection(request.getParameter("sotaikhoan_tp"));
	    	if(sotaikhoan_tp == null)
	    		sotaikhoan_tp = "";
	    	tthdBean.setSotaikhoan_tp(sotaikhoan_tp);
	
	    	String ttId = util.antiSQLInspection(request.getParameter("tienteId"));
	    	if(ttId == null)
	    		ttId = "";
	    	tthdBean.setTienteId(ttId);
	
	    	String sotienHDNT = util.antiSQLInspection(request.getParameter("sotienHDNT"));
	    	if(sotienHDNT == null)
	    		sotienHDNT = "0";
	    	tthdBean.setSotienHDNT(sotienHDNT);
	
	    	String sotienHDVND = util.antiSQLInspection(request.getParameter("sotienHDVND"));
	    	if(sotienHDVND == null)
	    		sotienHDVND = "0";
	    	tthdBean.setSotienHD(sotienHDVND);
	
	    	String trichphi = util.antiSQLInspection(request.getParameter("trichphi"));
	    	if(trichphi == null)
	    		trichphi = "0";
	    	tthdBean.setTrichphi(trichphi);
	
	    	String pnganhangNT = util.antiSQLInspection(request.getParameter("pnganhangNT"));
	    	if(pnganhangNT == null || pnganhangNT =="")
	    		pnganhangNT = "0";
	    	tthdBean.setPhinganhangNT(pnganhangNT);
	
	    	String pnganhangVND = util.antiSQLInspection(request.getParameter("pnganhangVND"));
	    	if(pnganhangVND == null || pnganhangVND == "")
	    		pnganhangVND = "0";
	    	tthdBean.setPhinganhang(pnganhangVND);
	
	    	String maHD_VAT = util.antiSQLInspection(request.getParameter("maHD_VAT"));
	    	if(maHD_VAT == null)
	    		maHD_VAT = "";
	    	tthdBean.setMahoadon(maHD_VAT);
	
	    	String mauHD_VAT = util.antiSQLInspection(request.getParameter("mauHD_VAT"));
	    	if(mauHD_VAT == null)
	    		mauHD_VAT = "";
	    	tthdBean.setMauhoadon(mauHD_VAT);
	    	
	    	String kyhieu_VAT = util.antiSQLInspection(request.getParameter("kyhieu_VAT"));
	    	if(kyhieu_VAT == null)
	    		kyhieu_VAT = "";
	    	tthdBean.setKyhieu(kyhieu_VAT);
	
	    	String sohd_VAT = util.antiSQLInspection(request.getParameter("sohd_VAT"));
	    	if(sohd_VAT == null)
	    		sohd_VAT = "";
	    	tthdBean.setSohoadon(sohd_VAT);
	    	
	    	String ngayhd_VAT = util.antiSQLInspection(request.getParameter("ngayhd_VAT"));
	    	if(ngayhd_VAT == null)
	    		ngayhd_VAT = "";
	    	tthdBean.setNgayhoadon(ngayhd_VAT);	    	
	
	    	String nghangTen = util.antiSQLInspection(request.getParameter("nghangTen"));
	    	if(nghangTen == null)
	    		nghangTen = "";
	    	tthdBean.setTenNCC_VAT(nghangTen);
	    	
	    	String TenNCC = util.antiSQLInspection(request.getParameter("TenNCC"));
	    	if(TenNCC == null)
	    		TenNCC = "";
	    	tthdBean.setTenNCC(TenNCC);
	    	
	    	String nhId_VAT =(request.getParameter("nhId_VAT"));
	    	if(nhId_VAT == null)
	    		nhId_VAT = "";
	    	tthdBean.setNhId_VAT(nhId_VAT);	    	
	    	
	    	String cnId_VAT = (request.getParameter("cnId_VAT"));
	    	if(cnId_VAT == null)
	    		cnId_VAT = "";
	    	tthdBean.setCnId_VAT(cnId_VAT);
	    	
	    	String mst_VAT = util.antiSQLInspection(request.getParameter("mst_VAT"));
	    	if(mst_VAT == null)
	    		mst_VAT = "";
	    	tthdBean.setMST(mst_VAT);
	
	    	String tienhang_VAT = util.antiSQLInspection(request.getParameter("tienhang_VAT"));
	    	if(tienhang_VAT == null)
	    		tienhang_VAT = "0";
	    	tthdBean.setTienhang(tienhang_VAT);
	    	
	    	String thuesuat_VAT = util.antiSQLInspection(request.getParameter("thuesuat_VAT"));
	    	if(thuesuat_VAT == null)
	    		thuesuat_VAT = "0";
	    	tthdBean.setThuesuat(thuesuat_VAT);
	    	
	    	String tienthue_VAT = util.antiSQLInspection(request.getParameter("tienthue_VAT"));
	    	if(tienthue_VAT == null)
	    		tienthue_VAT = "0";
	    	tthdBean.setTienthue(tienthue_VAT);
    	
	    	String vatNT = util.antiSQLInspection(request.getParameter("vatNT"));
	    	if(vatNT == null)
	    		vatNT = "0";
	    	tthdBean.setThueVATNT(vatNT);
	
	    	String vatVND = util.antiSQLInspection(request.getParameter("vatVND"));
	    	if(vatVND == null)
	    		vatVND = "0";
	    	tthdBean.setThueVAT(vatVND);
	    	
	    	String tongtienNT = util.antiSQLInspection(request.getParameter("tongtienNT"));
	    	if(tongtienNT == null)
	    		tongtienNT = "0";
	    	tthdBean.setSotienttNT(tongtienNT);
	
	    	String tongtienVND = util.antiSQLInspection(request.getParameter("tongtienVND"));
	    	if(tongtienVND == null)
	    		tongtienVND = "0";
	    	tthdBean.setSotientt(tongtienVND);
	    	
	    	String chenhlechVND = util.antiSQLInspection(request.getParameter("chenhlechVND"));
	    	if(chenhlechVND == null)
	    		chenhlechVND = "0";
	    	tthdBean.setChenhlech(chenhlechVND);
	    	
	    	String chungtukemtheo = util.antiSQLInspection(request.getParameter("ctkemtheo"));
	    	if(chungtukemtheo == null)
	    		chungtukemtheo = "";
	    	tthdBean.setChungtukemtheo(chungtukemtheo);
	    	
	    	
	    	String NhanVienId = util.antiSQLInspection(request.getParameter("NhanvienId"));
	    	if(NhanVienId == null){
	    		NhanVienId = "";
	    	}			
			tthdBean.setNhanVienId(NhanVienId);
			
			String bpId = util.antiSQLInspection(request.getParameter("bpId"));
			if(bpId == null)
				bpId = "";
			tthdBean.setBophanId(bpId);
			
			String bpTen = util.antiSQLInspection(request.getParameter("bpTen"));
			if(bpTen == null)
				bpTen = "";
			tthdBean.setBophanTen(bpTen);
			
			String khId = util.antiSQLInspection(request.getParameter("khid"));
	    	if(khId == null){
	    		khId = "";
	    	}		
			tthdBean.setKhachhangId(khId);
    	
	    	
			session.setAttribute("nhomncccn", tthdBean.getNhomNCCCN());			
	    	session.setAttribute("doituong",tthdBean.getDoiTuongTamUng());
	    	session.setAttribute("loaithanhtoan", tthdBean.getLoaiThanhToan());	    	
	    	session.setAttribute("doituongkhac",tthdBean.getDoiTuongChiPhiKhac());	    	
	    	session.setAttribute("doituongdinhkhoan",tthdBean.getDoiTuongDinhKhoan());
	    	//N?u là cp Khác - doituongthanhtoan = 3
    	
	    	if(doituongthanhtoan.equals("3"))
	    	{
				
				String nguoiNhanTien = util.antiSQLInspection(request.getParameter("nguoiNhanTien"));
				if(nguoiNhanTien == null)
					nguoiNhanTien = "";
				tthdBean.setNguoiNhanTien(nguoiNhanTien);
	    		String[] TaiKhoanIds = request.getParameterValues("TaiKhoanId");
		    	String[] PhongbanIds = request.getParameterValues("PhongbanId");
		    	String[] KenhIds = request.getParameterValues("KenhId");
		    	String[] TinhthanhIds = request.getParameterValues("TinhthanhId");
		    	String[] SanphamIds = request.getParameterValues("SanphamId");
		    	String[] MavvIds = request.getParameterValues("mavvId");
		    	String[] DiabanIds = request.getParameterValues("diabanId");
		    	String[] TinhThanhIds = request.getParameterValues("TinhthanhId");
		    	String[] BenhvienIds = request.getParameterValues("BenhvienId");
		    	
		    	if(TaiKhoanIds!=null){
					String[] dcIds = new String[TaiKhoanIds.length];
					String[] loais = new String[TaiKhoanIds.length];
					String[] ttcps = new String[TaiKhoanIds.length];
					
					tthdBean.setCount(TaiKhoanIds.length);
					tthdBean.setTaiKhoanIds(TaiKhoanIds);				
					
					for(int i = 0; i < tthdBean.getCount(); i++)
					{
						if(request.getParameter("dcIds_" + i) != null){
							if(request.getParameter("dcIds_" + i).trim().length() > 0)
							{
								String[] tam = request.getParameter("dcIds_" + i).split("_");
								dcIds[i] = tam[0];
								loais[i] = tam[1];
							}else
							{
								dcIds[i] = "";
								loais[i] = "";
							}
						}else{
							dcIds[i] = "";
							loais[i] = "";
						}	
						
						if(request.getParameter("ttcps_" + i) != null){
							if(request.getParameter("ttcps_" + i).trim().length() > 0)
							{
								ttcps[i] = request.getParameter("ttcps_" + i).trim();
							} else {
								ttcps[i] = "";
							}
						}else{
							ttcps[i] = "";
						}
						
	
					}
					tthdBean.setTtcpIds(ttcps);
					tthdBean.setDcIds(dcIds);
					tthdBean.setLoais(loais);
		    	}
		    	else
		    	{
		    		tthdBean.setCount(0);
		    	}
		    	
		    	for(int i = 0; i < tthdBean.getCount(); i++)
				{	    		
					if(PhongbanIds[i] == null) PhongbanIds[i] = "";
					if(KenhIds[i] == null) KenhIds[i] = "";
					if(TinhthanhIds[i] == null) TinhthanhIds[i] = "";
					if(SanphamIds[i] == null) SanphamIds[i] = "";
					if(MavvIds[i] == null) MavvIds[i] = "";
					if(DiabanIds[i] == null) DiabanIds[i] = "";
					if(TinhThanhIds[i] == null) TinhThanhIds[i] = "";
					if(BenhvienIds[i] == null) BenhvienIds[i] = "";
				}
		    	
		    	String[] Kyhieuhd = new String[10];
		    	String[] Mauhd = new String[10];
		    	String[] Sohd = new String[10];
	    		String[] Ngayhd = new String[10];
	    		String[] TenNCChd = new String[10];
	    		String[] diaChiHd = new String[10];
	    		String[] MSThd = new String[10];
	    		String[] Thuesuathd = new String[10];
	    		String[] Tienthuehd = new String[10];
	    		String[] Tienhanghd = new String[10];
	    		String[] Diengiaihd = new String[10];
	    		
		    	for(int i = 0; i < 10; i++)
				{		    		
					if(request.getParameter("mauHoaDon_VAT_" + i) != null) 		
						Mauhd[i] = request.getParameter("mauHoaDon_VAT_" + i);
					else
						Mauhd[i] = "";
					
					if(request.getParameter("kyhieu_VAT_" + i) != null) 		
						Kyhieuhd[i] = request.getParameter("kyhieu_VAT_" + i);
					else
						Kyhieuhd[i] = "";
					
					if(request.getParameter("sohd_VAT_" + i) != null) 		
						Sohd[i] = request.getParameter("sohd_VAT_" + i);
					else
						Sohd[i] = "";
					
					if(request.getParameter("ngayhd_VAT_" + i) != null) 		
						Ngayhd[i] = request.getParameter("ngayhd_VAT_" + i);
					else
						Ngayhd[i] = "";
					
					if(request.getParameter("tenNCC_VAT_" + i) != null) 		
						TenNCChd[i] = request.getParameter("tenNCC_VAT_" + i);
					else
						TenNCChd[i] = "";
					
	
					if(request.getParameter("diaChi_" + i) != null) 		
						diaChiHd[i] = request.getParameter("diaChi_" + i);
					else
						diaChiHd[i] = "";
										
					if(request.getParameter("mst_VAT_" + i) != null) 		
						MSThd[i] = request.getParameter("mst_VAT_" + i);
					else
						MSThd[i] = "";
					
					if(request.getParameter("thuesuat_VAT_" + i) != null) 		
						Thuesuathd[i] = request.getParameter("thuesuat_VAT_" + i).replaceAll(",","");
					else
						Thuesuathd[i] = "";
					
					if(request.getParameter("tienthue_VAT_" + i) != null) 		
						Tienthuehd[i] = request.getParameter("tienthue_VAT_" + i).replaceAll(",","");
					else
						Tienthuehd[i] = "";
					
					if(request.getParameter("tienhang_VAT_" + i) != null) 		
						Tienhanghd[i] = request.getParameter("tienhang_VAT_" + i).replaceAll(",","");
					else
						Tienhanghd[i] = "";
					
					if(request.getParameter("diengiai_VAT_" + i) != null) 		
						Diengiaihd[i] = request.getParameter("diengiai_VAT_" + i);
					else
						Diengiaihd[i] = "";
				}
		    	
		    	tthdBean.setPhongBanIds(PhongbanIds);
		    	tthdBean.setMavvIds(MavvIds);
		    	tthdBean.setDiaBanIds(DiabanIds);
		    	tthdBean.setBenhVienIds(BenhvienIds);
		    	tthdBean.setKenhIds(KenhIds);
		    	tthdBean.setTinhThanhIds(TinhthanhIds);
		    	tthdBean.setSanPhamIds(SanphamIds);
		    	tthdBean.setMauhd(Mauhd);
		    	tthdBean.setKyhieuhd(Kyhieuhd);
		    	tthdBean.setSohd(Sohd);
		    	tthdBean.setNgayhd(Ngayhd);
		    	tthdBean.setTenNCChd(TenNCChd);
		    	tthdBean.setMSThd(MSThd);
		    	tthdBean.setThuesuathd(Thuesuathd);
		    	tthdBean.setTienthuehd(Tienthuehd);
		    	tthdBean.setTienhanghd(Tienhanghd);
		    	tthdBean.setDiengiaihd(Diengiaihd);
		    	tthdBean.setDiaChiHd(diaChiHd);
	    	}
    	
    	
	    	//Luu lai hoa don
	    	String[] idHd = request.getParameterValues("idHd");
	    	String[] tienteId = request.getParameterValues("ttId");
	    	String[] kyhieuhd = request.getParameterValues("kyhieuhd");
			String[] sohd = request.getParameterValues("sohd");
			String[] ngayhd = request.getParameterValues("ngayhd");			
			String[] avat = request.getParameterValues("avat");
			String[] sotienNT = request.getParameterValues("sotienNT");
			String[] thanhtoan = request.getParameterValues("thanhtoan");
			String[] conlai = request.getParameterValues("conlai");
			String[] mancc = request.getParameterValues("mancc");
	//			String[] loaihd = request.getParameterValues("loaihd");
			String[] tigiahd = request.getParameterValues("tigiaHD");
			String[] loaihd_ = request.getParameterValues("loaihdId"); // Lo?i hóa don vs  ngày ghi nh?n : >= 8/2014
			
			String[] doituong = request.getParameterValues("doituong");
			String[] doituongId = request.getParameterValues("doituongId");
			String[] diaChi = request.getParameterValues("diaChi");
			
			List<IHoadon> hdList =  new ArrayList<IHoadon>();
			
			error = false; 

			if(kyhieuhd != null & tienteId != null)
			{		
				IHoadon hoadon = null;
				int m = 0;
				while(m < kyhieuhd.length)
				{				
					
						if(Nhomncccn.equals("1")){
							if(!ttId.equals("100000")){ //Là ngo?i t?
								if(sotienNT == null){ // truong hop moi tao moi va chuyen doi tien te
									hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), "0", thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);										
									hoadon.setMancc(mancc[m]);
									hoadon.setConlai(conlai[m]);
								}else{
									hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), sotienNT[m].replaceAll(",", ""), thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);
									hoadon.setTigia(tigiahd[m]);
									hoadon.setMancc(mancc[m]);
									hoadon.setConlai(conlai[m]);
								}								
							}else{
								if(avat == null){
									hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], "0", "0", thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);
									hoadon.setMancc(mancc[m]);
									hoadon.setConlai(conlai[m]);
								}else{
									hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), "0", thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);
									hoadon.setMancc(mancc[m]);
									hoadon.setConlai(conlai[m]);
								}
							}
						}
						else{
							if(!ttId.equals("100000")){ //Là ngo?i t?
							
								if(sotienNT == null){ // truong hop moi tao moi va chuyen doi tien te
									hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), "0", thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);										

								}else{
									hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), sotienNT[m].replaceAll(",", ""), thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);
									hoadon.setTigia(tigiahd[m]);
								}
							
							}else{
								if(avat == null){
									hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], "0", "0", thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);
							
								}else{
									hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), "0", thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);
									
								}
							}
							
							if(tthdBean.getBophanId().trim().length() > 0)
							{
								hoadon.setDoituong(doituong[m]);
								hoadon.setDoituongId(doituongId[m]);
								hoadon.setConlai(conlai[m]);
							}
							
						}
					
					hoadon.setLoaihd1(loaihd_[m]);
					hdList.add(hoadon);
				
					m++;
				}	
			}
			tthdBean.setHoadonRs(hdList);
		}
//    	error = true;
    	return error;
	}

	private String CheckDoiTuongDinhKhoan(String dinhkhoanno, String congtyId) {
		db = new dbutils();
		
		String doituongdinhkhoan= "";
		String dungchokho = "";
		String dungchonganhang = "";
		String dungchoncc = "";
		String dungchotaisan = "";
		String dungchokhachhang = "";
		String dungchonhanvien = "";
		
		String query= "";
		query = "Select isnull(dungchokho,0) dungchokho, isnull(dungchonganhang, 0)dungchonganhang, " +
				" isnull(dungchoncc,0) dungchoncc, isnull(dungchotaisan,0) dungchotaisan, isnull(dungchokhachhang,0) dungchokhachhang , isnull(dungchonhanvien, 0) dungchonhanvien "+
				"from ERP_TAIKHOANKT where  SOHIEUTAIKHOAN = '" + dinhkhoanno +"' and CONGTY_FK = "+congtyId;
		System.out.println(query);
		ResultSet dtrs = this.db.get(query);
		try {
			while(dtrs.next())
			{
				dungchokho = dtrs.getString("dungchokho");
				dungchonganhang = dtrs.getString("dungchonganhang");
				dungchoncc = dtrs.getString("dungchoncc");
				dungchotaisan = dtrs.getString("dungchotaisan");
				dungchokhachhang = dtrs.getString("dungchokhachhang");
				dungchonhanvien = dtrs.getString("dungchonhanvien");				
			}
			dtrs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(dungchokho.equals("1"))
		{
			doituongdinhkhoan = "1";
		}
		else if(dungchonganhang.equals("1"))
		{
			doituongdinhkhoan = "2";
		}
		else if(dungchoncc.equals("1"))
		{
			doituongdinhkhoan = "3";
		}
		else if(dungchotaisan.equals("1"))
		{
			doituongdinhkhoan = "4";
		}
		else if(dungchokhachhang.equals("1"))
		{
			doituongdinhkhoan = "5";
		}
		else if(dungchonhanvien.equals("1"))
		{
			doituongdinhkhoan = "6";
		}
		
		return doituongdinhkhoan;
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
