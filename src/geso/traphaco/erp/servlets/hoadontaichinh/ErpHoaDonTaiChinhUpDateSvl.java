package geso.traphaco.erp.servlets.hoadontaichinh;

import geso.traphaco.erp.db.sql.dbutils;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadontaichinh.IErpHoaDonTaiChinh;
import geso.traphaco.erp.beans.hoadontaichinh.imp.ErpHoaDonTaiChinh;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

@WebServlet("/ErpHoaDonTaiChinhUpDateSvl")
public class ErpHoaDonTaiChinhUpDateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpHoaDonTaiChinhUpDateSvl() {
		super();

	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		System.out.println("action:"+action);

		String ddhId = util.getId(querystring);

		if (action.equals("update")) {
			IErpHoaDonTaiChinh dhBean = new ErpHoaDonTaiChinh(ddhId);
			dhBean.init();
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinhUpdate.jsp";
			session.setAttribute("obj", dhBean);
			response.sendRedirect(nextJSP);
		} 
		else if (action.equals("display")) { 
			IErpHoaDonTaiChinh dhBean = new ErpHoaDonTaiChinh(ddhId);
			dhBean.setDisplayHD();
			//dhBean.init();
			session.setAttribute("obj", dhBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinhDisPlay.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		Utility util = new Utility();
		session.setAttribute("util", util);
		String id = request.getParameter("id");
		String userId = request.getParameter("userId");
		
		String userTen = request.getParameter("userTen");
		
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);

		String action = request.getParameter("action");
		
		String tenform = request.getParameter("tenform");
		
		String nextJSP = "";
		if (tenform.equals("newform")) {
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinhNew.jsp";
		} else {
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinhUpdate.jsp";
		}
		
		IErpHoaDonTaiChinh dhBean = null;
		if (id == null)
			dhBean = new ErpHoaDonTaiChinh();
		else
			dhBean = new ErpHoaDonTaiChinh(id);
		
		dhBean.setUserId(userId);

		String ngayxuathd = util.antiSQLInspection(request.getParameter("ngayxuathd"));
		dhBean.setNgayxuathd(ngayxuathd);

		String ngaycongno = util.antiSQLInspection(request.getParameter("ngaycongno"));
		if(ngaycongno == null) ngaycongno = "";
		dhBean.setNgayghino(ngaycongno);
		
		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		dhBean.SetSoHoaDon(sohoadon);
		
		String sohoadon2 = util.antiSQLInspection(request.getParameter("sohoadon2"));
		if(sohoadon2 == null) sohoadon2 = "";
		dhBean.SetSoHoaDon2(sohoadon2);
		
		String khId = util.antiSQLInspection(request.getParameter("nppId"));
		if(khId == null) khId = "";
		dhBean.setkhId(khId);

		String kyhieu = util.antiSQLInspection(request.getParameter("kyhieu"));
		if(kyhieu == null) kyhieu = "";
		dhBean.SetKyHieu(kyhieu);
		
		String hinhthuctt = util.antiSQLInspection(request.getParameter("hinhthuc"));
		if(hinhthuctt == null) hinhthuctt = "";
		dhBean.sethinhthuctt(hinhthuctt);
		
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		dhBean.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		dhBean.setDenNgay(denngay);
		
		String tenhanghoadichvu = util.antiSQLInspection(request.getParameter("tenhanghoadichvu"));
		if(tenhanghoadichvu == null)
			tenhanghoadichvu = "";
		dhBean.SetTenHangHoaDichVu(tenhanghoadichvu);
		
		String diachigiaohang = util.antiSQLInspection(request.getParameter("diachigiaohang"));
		if(diachigiaohang == null)
			diachigiaohang = "";
		dhBean.setDiachigiaohang(diachigiaohang);
		
		String hinhthucxhd = util.antiSQLInspection(request.getParameter("hinhthucxhd"));
		if(hinhthucxhd == null)
			hinhthucxhd = "";
		dhBean.sethinhthucxhd(hinhthucxhd);
	
		String nguoimuahang = util.antiSQLInspection(request.getParameter("nguoimuahang"));
		if (nguoimuahang == null)
			nguoimuahang = "";
		dhBean.setNguoiMuaHang(nguoimuahang);
		
		String donvi = util.antiSQLInspection(request.getParameter("donvinmh"));
		if (donvi == null)
			donvi = "";
		dhBean.setDonVi(donvi);
		
		String diachi = util.antiSQLInspection(request.getParameter("diachi"));
		if (diachi == null)
			diachi = "";
		dhBean.setDiaChi(diachi);
		
		String masothue = util.antiSQLInspection(request.getParameter("masothue"));
		if (masothue == null)
			masothue = "";
		dhBean.setMST(masothue);		

		String diachigiaohangtk=util.antiSQLInspection(request.getParameter("diachigiaohangtk"));
		if(diachigiaohangtk == null)
			diachigiaohangtk = "";
		dhBean.setDiachigiaohangtk(diachigiaohangtk);
		
		String ngaytao = this.getDateTime();
		String ngaysua = ngaytao;
		dhBean.setNgaytao(ngaytao);
		dhBean.setNgaysua(ngaysua);
		dhBean.setNguoitao(userId);
		dhBean.setNguoisua(userId);
		
		String ghichuhd=util.antiSQLInspection(request.getParameter("ghichuhd"));
		if(ghichuhd == null)
			ghichuhd = "";
		dhBean.SetGhiChu(ghichuhd);
		
		String khoId=util.antiSQLInspection(request.getParameter("khoId"));
		if(khoId == null)
			khoId = "";
		dhBean.setKhoId(khoId);
		
		String ghichucktm=util.antiSQLInspection(request.getParameter("ghichucktm"));
		if(ghichucktm == null)
			ghichucktm = "";
		dhBean.setGhiChu1(ghichucktm);
		
		String ttcpId=util.antiSQLInspection(request.getParameter("ttcpId"));
		if(ttcpId == null)
			ttcpId = "";
		dhBean.settcpId(ttcpId);
		
		String tienavat = util.antiSQLInspection(request.getParameter("tienavat"));
		dhBean.setTienAVAT(Double.parseDouble( tienavat.replaceAll(",", "")));
		
		String total_tienvat = util.antiSQLInspection(request.getParameter("total_tienvat"));		
		dhBean.setTienVat(Double.parseDouble(total_tienvat.replaceAll(",", "")));
		
		String ckthuongmaihd = util.antiSQLInspection(request.getParameter("ckthuongmaihd"));
		dhBean.setTienCKThuongMai(Double.parseDouble(ckthuongmaihd.replaceAll(",", "")));
		
		//String[] ddhid = request.getParameterValues("ddhid");
				
		String ddh = "";
		
	/*	if (ddhid != null) 
		{
			for(int i = 0; i < ddhid.length; i++)
			{
				ddh += ddhid[i] + ",";
			}
			if(ddh.trim().length() > 0)
			{
				ddh = ddh.substring(0, ddh.length() - 1);
				dhBean.setDonhangId(ddh);
				System.out.println("ddh"+ddh);
			}
		}*/
		
		String[] dh = request.getParameterValues("pk_seq"); //LƯU TẤT CẢ NHỮNG ĐƠN HÀNG ĐÃ HIỆN
		
		String[] kh_dhid = request.getParameterValues("khId");//LƯU TẤT CẢ NHỮNG KHÁCH HÀNG ĐÃ HIỆN
		
		String[] ddhid_selected = request.getParameterValues("ddhid"); //LƯU NHỮNG ĐƠN HÀNG ĐÃ ĐƯỢC CHỌN
				
		String kh = "";
		
		if (ddhid_selected != null) 
		{
			for(int i = 0; i < ddhid_selected.length; i++)
			{
				ddh += ddhid_selected[i] + ",";
			}
			
			if(ddh.trim().length() > 0)
			{
				ddh = ddh.substring(0, ddh.length() - 1);		
				System.out.println("dhid:"+ddh);
				dhBean.setDonhangId(ddh);				
			}
			
			//String[] kh_hdSelected = new String[ddhid_selected.length];
			
			for(int j=0; j<ddhid_selected.length;j++){
				 for(int m = 0 ; m< dh.length;m++){
					 
					 if(ddhid_selected[j].trim().equals(dh[m].trim())){
						 kh+=kh_dhid[m]+",";
					 }
					 
				 }
			}
			
			if(kh.trim().length() > 0){
				kh = kh.substring(0, kh.length() - 1);				
				dhBean.setKh_dhId(kh);		
			}
			
		}
		
		System.out.println("DH:"+ddh);
		
		String[] spId = request.getParameterValues("spId");
		dhBean.setSpId(spId);
		
		String[] spMa = request.getParameterValues("spMa");
		dhBean.setSpMa(spMa);
		
		String[] spDonvi = request.getParameterValues("donvi");
		dhBean.setSpDonvi(spDonvi);
		
		String[] spMaDonvi = request.getParameterValues("madonvi");
		dhBean.setSpMaDonvi(spMaDonvi);
		
		String[] soluong = request.getParameterValues("soluong");
		dhBean.setSpSoluong(soluong);	
		
		String[] spdongia = request.getParameterValues("dongia");
		dhBean.setSpDongia(spdongia);
		
		String[] spdongiack = request.getParameterValues("dongiack");
		dhBean.setSpDongiagiamgia(spdongiack);
		
		String[] spvat = request.getParameterValues("vat");
		dhBean.setSpVat(spvat);
		
		String[] spck = request.getParameterValues("chietkhau");
		dhBean.setSpChietKhau(spck);
		
		String[] sptienCK = request.getParameterValues("tienchietkhau");
		dhBean.setSpTienCK(sptienCK);
		
		String[] sptienAvat = request.getParameterValues("thanhtien");
		dhBean.setSpThanhTien(sptienAvat);
		
		String[] spkhoTT = request.getParameterValues("khott");
		dhBean.setSpKhoTT(spkhoTT);
		
		String[] spnr = request.getParameterValues("spnr");
		dhBean.setSpnr(spnr);
		
		if (action.equals("save")) {
			if (id==null) {
				if (!(dhBean.Save())) {
					dhBean.loadContents();
					session.setAttribute("obj", dhBean);					
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinhNew.jsp";
					response.sendRedirect(nextJSP);

				} else {
					dhBean = new ErpHoaDonTaiChinh();
					dhBean.setUserId(userId);
					dhBean.setListHoaDon("");
					session.setAttribute("obj", dhBean);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinh.jsp";
					response.sendRedirect(nextJSP);

				}
			} else {
				
				if(!dhBean.Edit())
				{
					dhBean.loadContents();
					session.setAttribute("obj", dhBean);
					
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinhUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					dhBean = new ErpHoaDonTaiChinh();
					dhBean.setUserId(userId);
					dhBean.setListHoaDon("");
					session.setAttribute("obj", dhBean);

					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinh.jsp";
					response.sendRedirect(nextJSP);
				}			
			}
		} 
		else if(action.equals("chot")){
				
			String error = dhBean.ChotHoaDon();
			
			 if(error.trim().length()>0){				 
				 dhBean.loadContents();				
				 session.setAttribute("obj", dhBean);
				 response.sendRedirect(nextJSP);
			 }			 
			 else{
				dhBean = new ErpHoaDonTaiChinh();
				dhBean.setUserId(userId);
				dhBean.setListHoaDon("");
				session.setAttribute("obj", dhBean);

				nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinh.jsp";
				response.sendRedirect(nextJSP);
			 }
		}
		else if(action.equals("reload")) {
			
			dhBean.loadContents();			
			session.setAttribute("obj", dhBean);
			response.sendRedirect(nextJSP);
			
		}
		else if(action.equals("savesohoadon")){
			if(!dhBean.EditSoHoaDon())
			{
				dhBean.setDisplayHD();
				//dhBean.init();
				session.setAttribute("obj", dhBean);
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinhDisPlay.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				dhBean = new ErpHoaDonTaiChinh();
				dhBean.setUserId(userId);
				dhBean.setListHoaDon("");
				session.setAttribute("obj", dhBean);

				nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinh.jsp";
				response.sendRedirect(nextJSP);
			}	
		}
		else{
			if (id.length() > 0)
			{
				dhBean.init();
			} else
			{
				dhBean.loadContents();
			}
			
			session.setAttribute("obj", dhBean);
			
			if (id.length() == 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinhNew.jsp";
			} else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTaiChinhUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}

	}

}
