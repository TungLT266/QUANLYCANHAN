package geso.traphaco.erp.servlets.uynhiemchi;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.Hoadon;
import geso.traphaco.erp.beans.uynhiemchi.*;
import geso.traphaco.erp.beans.uynhiemchi.imp.*;
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

import org.apache.poi.util.SystemOutLogger;

public class ErpDenghithanhtoanNCCUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	PrintWriter out;
	dbutils db;
	
    public ErpDenghithanhtoanNCCUpdateSvl() {
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
			IErpDenghithanhtoanNCC tthdBean = new ErpDenghithanhtoanNCC(id);
			String ctyId = (String)session.getAttribute("congtyId");
			tthdBean.setCtyId(ctyId);
	        tthdBean.setUserId(userId);
	        tthdBean.setnppdangnhap(util.getIdNhapp(userId));

	        String nextJSP;
	       
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	tthdBean.initDisplay();
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCCDisplay.jsp";
	        }
	        else
	        {
	        	tthdBean.init();
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCCUpdate.jsp";
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
			IErpDenghithanhtoanNCC tthdBean;

			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("Id"));
			
		    if(id == null)
		    {  	
		    	tthdBean = new ErpDenghithanhtoanNCC("");
		    }
		    else
		    {
		    	tthdBean = new ErpDenghithanhtoanNCC(id);
		    }
			String ctyId = (String)session.getAttribute("congtyId");
			tthdBean.setCtyId(ctyId);
		    tthdBean.setUserId(userId);		    
		    tthdBean.setnppdangnhap(util.getIdNhapp(userId));
			
		    String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
			if (ngayghinhan == null || ngayghinhan == "")
				ngayghinhan = this.getDateTime();				
	    	tthdBean.setNgayghinhan(ngayghinhan);
	    		    		    	
	    	String doituongthanhtoan = util.antiSQLInspection(request.getParameter("doituongthanhtoan"));
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
  		    	
	    	
	    	String htttId = util.antiSQLInspection(request.getParameter("htttId"));
			if (htttId == null)
				htttId = "";				
	    	tthdBean.setHtttId(htttId);
	    	
	    	String thanhtoantuTV = util.antiSQLInspection(request.getParameter("thanhtoantuTV"));
	    	if(thanhtoantuTV == null)
	    		thanhtoantuTV = "0";
	    	tthdBean.setCheckThanhtoantuTV(thanhtoantuTV);	    	
		    	
	    	String sotaikhoan_tp = util.antiSQLInspection(request.getParameter("sotaikhoan_tp"));
	    	if(sotaikhoan_tp == null)
	    		sotaikhoan_tp = "";
	    	tthdBean.setSotaikhoan_tp(sotaikhoan_tp);

	    	String ttId = util.antiSQLInspection(request.getParameter("tienteId"));
	    	if(ttId == null)
	    		ttId = "";
	    	tthdBean.setTienteId(ttId);

	    	String tigia = util.antiSQLInspection(request.getParameter("tigia"));
	    	if(tigia == null)
	    		tigia = "1";
	    	tthdBean.setTigia(tigia);
	    	
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
	    	System.out.println("trich phi:" + trichphi);
	    	
	    	tthdBean.setTrichphi(trichphi);

	    	String pnganhangNT = util.antiSQLInspection(request.getParameter("pnganhangNT"));
	    	if(pnganhangNT == null)
	    		pnganhangNT = "0";
	    	tthdBean.setPhinganhangNT(pnganhangNT);

	    	String pnganhangVND = util.antiSQLInspection(request.getParameter("pnganhangVND"));
	    	if(pnganhangVND == null)
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

	    	
	    	String noidungthanhtoan = util.antiSQLInspection(request.getParameter("noidungthanhtoan"));
	    	if(noidungthanhtoan == null)
	    		noidungthanhtoan = "";
	    	tthdBean.setNoidungtt(noidungthanhtoan);	    		    	
	    	
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
	    	System.out.println();
	    	session.setAttribute("doituongkhac",tthdBean.getDoiTuongChiPhiKhac());	    	
	    	session.setAttribute("doituongdinhkhoan",tthdBean.getDoiTuongDinhKhoan());
	    	
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
			String[] loaihd = request.getParameterValues("loaihd");
			String[] tigiahd = request.getParameterValues("tigiaHD");
			String[] loaihd_ = request.getParameterValues("loaihdId"); // Loại hóa đơn vs  ngày ghi nhận : >= 8/2014
			
			String[] doituong = request.getParameterValues("doituong");
			String[] doituongId = request.getParameterValues("doituongId");
			
			List<IHoadon> hdList =  new ArrayList<IHoadon>();
			
			if(thanhtoan != null)
			System.out.println("size cua thanh toan" + thanhtoan.length);
			
			boolean error = false;

				if(kyhieuhd != null & tienteId != null)
				{		
					IHoadon hoadon = null;
					int m = 0;
					while(m < kyhieuhd.length)
					{				
						
							if(Nhomncccn.equals("1")){
								if(!ttId.equals("100000")){ //Là ngoại tệ														
									if(sotienNT == null){ // truong hop moi tao moi va chuyen doi tien te
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), "0", thanhtoan[m].replaceAll(",", ""), tienteId[m]);										
										hoadon.setMancc(mancc[m]);
									}else{
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), sotienNT[m].replaceAll(",", ""), thanhtoan[m].replaceAll(",", ""), tienteId[m]);
										hoadon.setTigia(tigiahd[m]);
										hoadon.setMancc(mancc[m]);
									}
								}else{
									if(avat == null){
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], "0", "0", thanhtoan[m].replaceAll(",", ""), tienteId[m]);
										hoadon.setMancc(mancc[m]);
									}else{
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), "0", thanhtoan[m].replaceAll(",", ""), tienteId[m]);
										hoadon.setMancc(mancc[m]);
									}
								}
							}
							else{
								if(!ttId.equals("100000")){ //Là ngoại tệ
								
									if(sotienNT == null){ // truong hop moi tao moi va chuyen doi tien te
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), "0", thanhtoan[m].replaceAll(",", ""), tienteId[m]);										

									}else{
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), sotienNT[m].replaceAll(",", ""), thanhtoan[m].replaceAll(",", ""), tienteId[m]);
										hoadon.setTigia(tigiahd[m]);
									}
								
								}else{
									if(avat == null){
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], "0", "0", thanhtoan[m].replaceAll(",", ""), tienteId[m]);
									}else{
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), "0", thanhtoan[m].replaceAll(",", ""), tienteId[m]);
									}
								}
								
								if(tthdBean.getBophanId().trim().length() > 0)
								{
									hoadon.setDoituong(doituong[m]);
									hoadon.setDoituongId(doituongId[m]);
								}
							}
						
						hoadon.setLoaihd1(loaihd_[m]);
						hdList.add(hoadon);
					
						m++;
					}	
				}
				tthdBean.setHoadonRs(hdList);
			
		
			String action = request.getParameter("action");
			System.out.println("Action la: " + action);
			
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
	    	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCCNew.jsp";
	    	    		response.sendRedirect(nextJSP);
					}
					else
					{
						//tthdBean.updateDonmuahang(tthdBean.getDonmuahangId());
						
						IErpDenghithanhtoanNCCList obj = new ErpDenghithanhtoanNCCList();
						obj.setUserId(userId);
						obj.setCongtyId(ctyId);
						obj.setnppdangnhap(util.getIdNhapp(userId));
						obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCC.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!tthdBean.updateTTHD())
					{
						System.out.println("[ErpTTHoadonUpdateSvl.doPost] Saved fail. msg = " + tthdBean.getMsg());
						tthdBean.createRs();
		    		    
						session.setAttribute("tthdBean", tthdBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCCUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						//tthdBean.updateDonmuahang(tthdBean.getDonmuahangId());
					
						IErpDenghithanhtoanNCCList obj = new ErpDenghithanhtoanNCCList();
						obj.setUserId(userId);
						obj.setCongtyId(ctyId);
						obj.setnppdangnhap(util.getIdNhapp(userId));
						obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCC.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else if(action.equals("changeTT")){
				//tthdBean.setSotaikhoan("");
				
				tthdBean.setTigia("");
		    	
				tthdBean.setSotienHDNT("0");

				tthdBean.setSotienHD("0");

				tthdBean.setPhinganhangNT("0");

				tthdBean.setPhinganhang("0");

				tthdBean.setThueVATNT("0");

				tthdBean.setThueVAT("0");
		   	
				tthdBean.setSotienttNT("0");

				tthdBean.setSotientt("0");
		   	
				tthdBean.setChenhlech("0");
				
				hdList.clear();
				tthdBean.setHoadonRs(hdList);

				String nextJSP;
				tthdBean.createRs();

				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCCNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCCUpdate.jsp";   						
				}
			
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
				
			}else if(action.equals("changeTP")){
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
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCCNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCCUpdate.jsp";   						
				}
				
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
				
			}else if(action.equals("changeHTTT")){
				tthdBean.setSotaikhoan("");

				String nextJSP;
				hdList.clear();
				tthdBean.setHoadonRs(hdList);

				tthdBean.createRs();
				
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCCNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCCUpdate.jsp";   						
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
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCCNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCCUpdate.jsp";   						
				}
				
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String CheckDoiTuongDinhKhoan(String dinhkhoanno) {
		db = new dbutils();
		
		String doituongdinhkhoan= "";
		String dungchokho = "";
		String dungchonganhang = "";
		String dungchoncc = "";
		String dungchotaisan = "";
		String dungchokhachhang = "";
		String dungchonhanvien = "";
		
		String query= "";
		query = "Select * from ERP_TAIKHOANKT where  SOHIEUTAIKHOAN = '" + dinhkhoanno +"'";
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
