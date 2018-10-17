package geso.traphaco.erp.servlets.kiemnghiemchitiet;

import geso.traphaco.distributor.util.Utility;
 
import geso.traphaco.erp.beans.kiemnghiemchitiet.IErpKiemNghiemChiTiet;
import geso.traphaco.erp.beans.kiemnghiemchitiet.IErpKiemNghiemChiTietList;
import geso.traphaco.erp.beans.kiemnghiemchitiet.imp.ErpKiemNghiemChiTiet;
import geso.traphaco.erp.beans.kiemnghiemchitiet.imp.ErpKiemNghiemChiTietList;
import geso.traphaco.erp.beans.kiemnghiemchitiet.imp.PhieuKiemDinh;
import geso.traphaco.erp.beans.kiemnghiemchitiet.imp.PhuongPhap;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class YCLayMauKiemNghiemSvl
 */
@WebServlet("/ErpKiemNghiemChiTietUpdateSvl")
public class ErpKiemNghiemChiTietUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpKiemNghiemChiTietUpdateSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		String querystring = request.getQueryString();
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session
				.getAttribute("util");
		PrintWriter out;
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} 
		
		else {
			session.setMaxInactiveInterval(30000);

			out = response.getWriter();
			Utility util = new Utility(); 
			userId = util.getUserId(querystring);
			String ctyId = (String) session.getAttribute("congtyId");
			String nppId = (String) session.getAttribute("nppId");
			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			if (id == null)
				id = "";

			IErpKiemNghiemChiTiet obj = new ErpKiemNghiemChiTiet(id);
			obj.setCtyId(ctyId);
			obj.setUserId(userId);
			obj.setNppId(nppId);
			obj.init();
			obj.createRs();
			String nextJSP;
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemNghiemChiTietUpdate.jsp";

			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session
				.getAttribute("util");
		PrintWriter out;
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(30000);
			out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			String ctyId = (String) session.getAttribute("congtyId");
			String nppId = (String) session.getAttribute("nppId");
			out.println(userId);
			
			String action = util.antiSQLInspection(request
					.getParameter("action"));

			if (action == null)
				action = "";

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null)
				id = ""; 
		 
			IErpKiemNghiemChiTiet obj = new ErpKiemNghiemChiTiet(id);
			obj.setCtyId(ctyId);
			obj.setUserId(userId);
			obj.setNppId(nppId);
			filterData(obj, request);
			
 
			String nextJSP = "";
			 
			if (action.equals("save")) {
				System.out.println("VAO DAY");
				if (id.trim().length() == 0) {
					if (!obj.create()) {
						
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemNghiemChiTietNew.jsp";
						session.setAttribute("obj", obj);
						response.sendRedirect(nextJSP);
					} else {
						IErpKiemNghiemChiTietList obj1 = new ErpKiemNghiemChiTietList(id);
				        obj1.setCtyId(ctyId);
						obj1.setUserId(userId);			
						obj1.setNppId(nppId); 
						
						obj1.init();
				        nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemNghiemChiTiet.jsp"; 
				        session.setAttribute("obj", obj1);
				        response.sendRedirect(nextJSP);
					}
				} else {
					if (!obj.update()) {
						obj.createRs();
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemNghiemChiTietUpdate.jsp";
						session.setAttribute("obj", obj);
						response.sendRedirect(nextJSP);
					} else {
						IErpKiemNghiemChiTietList obj1 = new ErpKiemNghiemChiTietList(id);
				        obj1.setCtyId(ctyId);
						obj1.setUserId(userId);			
						obj1.setNppId(nppId); 
						obj1.createRs(); 
						obj1.init();
				        nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemNghiemChiTiet.jsp";
				 
				        session.setAttribute("obj", obj1);
				        response.sendRedirect(nextJSP);
					}
				}
			} else if(action.equals("filter_yckt")){
				obj.getDsHoaChatKiemNghiem().clear();
				obj.getDsPhieuKiemDinhs().clear();
				obj.createRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemNghiemChiTietNew.jsp";
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
			}
			
			else if (action.equals("filter")) {
				obj.createRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemNghiemChiTietNew.jsp";
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
			}
		}
	}

	private void filterData(IErpKiemNghiemChiTiet obj, HttpServletRequest request) {
		Utility util = new Utility();
 
		String phongbanthId = util.antiSQLInspection(request
				.getParameter("phongbanId"));
		if (phongbanthId == null)
			phongbanthId = "";
		obj.setPhongBanId(phongbanthId);

		String diengiai = util.antiSQLInspection(request
				.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		obj.setDienGiai(diengiai);
 
		String hosoid = util.antiSQLInspection(request.getParameter("hosokiemnghiem"));
		if(hosoid == null)
			hosoid = "";
		obj.setHoSoId(hosoid);
		
		String yeucauid = util.antiSQLInspection(request.getParameter("yckythuat"));
		if(yeucauid == null)
			yeucauid = "";
		obj.setYeuCauId(yeucauid);
 
		String danhgia = util.antiSQLInspection(request.getParameter("danhgia"));
		if(danhgia == null)
			danhgia = "";
		obj.setDanhGia(danhgia);
		
		String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
		if(ngaychungtu == null)
			ngaychungtu = "";
		obj.setNgayChungTu(ngaychungtu);
		
		String thoigianbd = util.antiSQLInspection(request.getParameter("thoigianbd"));
		if(thoigianbd == null)
			thoigianbd = "";
		obj.setThoiGianBD(thoigianbd);
		
		String thoigiankt = util.antiSQLInspection(request.getParameter("thoigiankt"));
		if(thoigiankt == null)
			thoigiankt = "";
		obj.setThoiGianKT(thoigiankt);
		
		String tongthoiluong = util.antiSQLInspection(request.getParameter("tongthoiluongtn"));
		if(tongthoiluong == null)
			tongthoiluong = "";
		obj.setTongThoiLuong(tongthoiluong);
		
		String sanphamId = util.antiSQLInspection(request.getParameter("sanphamkn"));
		if(sanphamId == null)
			sanphamId = "";
		obj.setSanphamId(sanphamId);
		
		String sanphamTen = util.antiSQLInspection(request.getParameter("sanphamkn"));
		if(sanphamTen == null)
			sanphamTen = "";
		obj.setSanphamTen(sanphamTen);
		
		String tieuchuanId = util.antiSQLInspection(request.getParameter("tieuchuankn"));
		if(tieuchuanId == null)
			tieuchuanId = "";
		obj.setTieuChuanId(tieuchuanId);
		
		String tieuchuanTen = util.antiSQLInspection(request.getParameter("tieuchuankn"));
		if(tieuchuanTen == null)
			tieuchuanTen = "";
		obj.setTieuChuan(tieuchuanTen);
		
		String[] stt = request.getParameterValues("stt");
		String[] hoachat = request.getParameterValues("hoachat");
		String[] soluongdm = request.getParameterValues("soluongdm");
		String[] soluongtt = request.getParameterValues("soluongtt");
		String[] maso = request.getParameterValues("maso");
		String[] cachpha = request.getParameterValues("cachpha");
		System.out.println(" ___ sp ___" + stt);
		if (stt != null) {
			obj.getDsHoaChatKiemNghiem().clear();
			System.out.println("SO THU TU: " + stt.length);
			for (int i = 0; i < stt.length; i++) {
				PhieuKiemDinh pkd = new PhieuKiemDinh();
				pkd.setStt(stt[i]);
				pkd.setHoaChat(hoachat[i]);
				pkd.setSoLuongDinhMuc(soluongdm[i]);
				pkd.setSoLuongThucTe(soluongtt[i]);
				pkd.setMaSo(maso[i]);
				pkd.setCachPha(cachpha[i]);
				obj.getDsHoaChatKiemNghiem().add(pkd);
			}

		}
		
		
		// Lấy phương pháp
		
		String []pp_size =  request.getParameterValues("pp_size");
		String []ketquapp_ = request.getParameterValues("ketquapp_");
		String []pp_pkseq = request.getParameterValues("pp_pkseq");
		String [] pp_ten = request.getParameterValues("pp_ten");
		if(pp_size != null){
			obj.getDsPhieuKiemDinhs().clear();
			for(int i  = 0; i < pp_size.length; i++){
				PhuongPhap pp = new PhuongPhap();
				pp.set__sizePhuongPhap(Integer.parseInt(pp_size[i]));
				pp.setDienGiai(PhuongPhap._null(ketquapp_[i]));
				pp.setSott(i);
				pp.setId(pp_pkseq[i]);
				pp.setTenPhuongPhap(pp_ten[i]);
				filter_PhuongPhap(i + 1 , pp, request);
				obj.getDsPhieuKiemDinhs().add(pp);
				
			}
		} 
	}

	private void filter_PhuongPhap(int i, PhuongPhap pp, HttpServletRequest request) {
		String [] sott1 = request.getParameterValues("sott" + i);
		String [] pp_loai1 = request.getParameterValues("pp_loai" + i);
		String [] pp_kyhieu1 = request.getParameterValues("pp_kyhieu" + i);
		String [] pp_tenthamso1 = request.getParameterValues("pp_tenthamso" + i);
		String [] pp_donvitinh1 = request.getParameterValues("pp_donvitinh" + i);
		String [] pp_isdanhgia = request.getParameterValues("pp_isdanhgia" + i);
		String [] pp_iscongthuc = request.getParameterValues("pp_iscongthuc" + i);
		
		if(sott1 != null){
			for(int k = 0; k < sott1.length; k++){
				PhieuKiemDinh pkd = new PhieuKiemDinh();
				pkd.setStt(sott1[k]);
				pkd.setLoai(Integer.parseInt(pp_loai1[k]));
				pkd.setKyHieu(pp_kyhieu1[k]);
				pkd.setTenThamSo( pp_tenthamso1[k]);
				pkd.setDonViTinh(pp_donvitinh1[k]);
				pkd.setCongThuc(Boolean.parseBoolean(pp_iscongthuc[k]));
				pkd.setDanhGia(Boolean.parseBoolean(pp_isdanhgia[k]));
				for(int j = 1; j <= pp.get__sizePhuongPhap(); j++ ){
					String []pp_mau1_1 = 	request.getParameterValues("pp_mau" + i + "_" + j);
					pkd.getThamSoMau().add(PhuongPhap._isNumeric(pp_mau1_1[k]));
				}
				pp.getDsPhieuKiemDinhs().add(pkd);
				
			}
		}
		
		
	}

}
