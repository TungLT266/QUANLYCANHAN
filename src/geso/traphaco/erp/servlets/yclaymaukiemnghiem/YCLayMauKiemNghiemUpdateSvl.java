package geso.traphaco.erp.servlets.yclaymaukiemnghiem;

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.kehoachlaymau.IKeHoachLayMauList;
import geso.traphaco.erp.beans.kehoachlaymau.imp.KeHoachLayMauList;
import geso.traphaco.erp.beans.thutien.IErpThutien;
import geso.traphaco.erp.beans.thutien.imp.ErpThutien;
import geso.traphaco.erp.beans.yclaymaukiemnghiem.IYCLayMauKiemNghiem;
import geso.traphaco.erp.beans.yclaymaukiemnghiem.IYCLayMauKiemNghiemList;
import geso.traphaco.erp.beans.yclaymaukiemnghiem.imp.PhieuKiemDinh;
import geso.traphaco.erp.beans.yclaymaukiemnghiem.imp.YCLayMauKiemNghiem;
import geso.traphaco.erp.beans.yclaymaukiemnghiem.imp.YCLayMauKiemNghiemList;

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
@WebServlet("/YCLayMauKiemNghiemUpdateSvl")
public class YCLayMauKiemNghiemUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public YCLayMauKiemNghiemUpdateSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		PrintWriter out;
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);

			out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			String ctyId = (String)session.getAttribute("congtyId");
			String nppId = (String)session.getAttribute("nppId");
			out.println(userId);

			if (userId.length()==0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String action = util.getAction(querystring);
			System.out.println("action "+action);
			String id = util.getId(querystring);
			if(id == null) id = "";

			System.out.println("ididid  "+id);
			IYCLayMauKiemNghiem obj = new YCLayMauKiemNghiem(id);
			obj.setCtyId(ctyId);
			obj.setUserId(userId);			
			obj.setNppId(nppId);
			obj.init();
			obj.createRs();
			String nextJSP;

			nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemUpdate.jsp";
			if(action.equals("display")){
				nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemDisplay.jsp";
			}

			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		PrintWriter out;
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(30000);
			out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			String ctyId = (String)session.getAttribute("congtyId");
			String nppId = (String)session.getAttribute("nppId");
			out.println(userId);

			String action = util.antiSQLInspection(request.getParameter("action"));

			if(action == null) action = "";

			if (userId.length()==0)
				userId = util.antiSQLInspection(request.getParameter("userId"));


			String id = util.antiSQLInspection(request.getParameter("id"));
			if(id == null) id = "";
			IYCLayMauKiemNghiem obj = new YCLayMauKiemNghiem(id);
			obj.setCtyId(ctyId);
			obj.setUserId(userId);			
			obj.setNppId(nppId);
			filterData(obj, request);

			String nextJSP = "";
			System.out.println("ID LLLLLL: " + id);
			if (action.equals("save")) {
				if (id.trim().length() == 0){
					if(!obj.create()){
						obj.createRs();
						nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemNew.jsp"; 
						session.setAttribute("obj", obj);
						response.sendRedirect(nextJSP);
					}
					else {
						IYCLayMauKiemNghiemList obj1 = new YCLayMauKiemNghiemList();
						obj1.setCtyId(ctyId);
						obj1.setUserId(userId);			
						obj1.setNppId(nppId); 
						obj1.createRs(); 
						obj1.init("");
						nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemList.jsp"; 
						session.setAttribute("obj", obj1);
						response.sendRedirect(nextJSP);
					}
				}else{
					if(!obj.update()){
						obj.createRs();
						nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemUpdate.jsp"; 
						session.setAttribute("obj", obj);
						response.sendRedirect(nextJSP);
					}
					else {
						System.out.println("DADSADSADSADSADSADSADSAD");
						IYCLayMauKiemNghiemList obj1 = new YCLayMauKiemNghiemList();
						obj1.setCtyId(ctyId);
						obj1.setUserId(userId);			
						obj1.setNppId(nppId); 
						obj1.createRs(); 
						obj1.init("");
						nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemList.jsp"; 
						session.setAttribute("obj", obj1);
						response.sendRedirect(nextJSP);
					}
				}
			}
			else if(action.equals("filter")){
				obj.createRs();
				filterData(obj, request);
				nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemNew.jsp";
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
			}else if(action.equals("thaydoiLoaiKD")){
				obj.setSoPhieuKiemDinh("");
				obj.setSanphamId("");
				obj.getDsPhieuKiemDinhs().clear();
				obj.createRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/YCLayMauKiemNghiemNew.jsp";
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
			}


		}
	}

	private void filterData(IYCLayMauKiemNghiem obj, HttpServletRequest request) {
		Utility util = new Utility();
		String ngayct = util.antiSQLInspection(request.getParameter("ngayct"));
		if (ngayct == null)
			ngayct = "";				
		obj.setNgayChungTu(ngayct);

		String loaikiemdinh = util.antiSQLInspection(request.getParameter("loaikiemdinh"));
		if (loaikiemdinh == null)
			loaikiemdinh = "";				
		obj.setLoaiKiemDinh(loaikiemdinh);

		String phongbanthId = util.antiSQLInspection(request.getParameter("phongbanthId"));
		if (phongbanthId == null)
			phongbanthId = "";				
		obj.setPhongBanId(phongbanthId);

		String soctmau = util.antiSQLInspection(request.getParameter("soctmau"));
		if (soctmau == null)
			soctmau = "";				
		obj.setSoChungTuNhapMau(soctmau);

		String sophieukd = util.antiSQLInspection(request.getParameter("sophieukd"));
		if (sophieukd == null)
			sophieukd = "";				
		obj.setSoPhieuKiemDinh(sophieukd);

		String loaimaukn = util.antiSQLInspection(request.getParameter("loaimaukn"));
		if (loaimaukn == null || loaimaukn.length()<=0)
			loaimaukn = null;				
		obj.setMauKiemNghiemId(loaimaukn);

		String phieunhanhang = util.antiSQLInspection(request.getParameter("phieunhanhang"));
		if (phieunhanhang == null)
			phieunhanhang = "";				
		obj.setPhieuNhanHangId(phieunhanhang);

		String khoxuatmauId = util.antiSQLInspection(request.getParameter("khoxuatmauId"));
		if (khoxuatmauId == null)
			khoxuatmauId = "";				
		obj.setKhoXuatMauId(khoxuatmauId);

		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		if (sohoadon == null)
			sohoadon = "";				
		obj.setSoHoaDon(sohoadon);
		String tongVND = util.antiSQLInspection(request.getParameter("tongVND"));
		if (tongVND == null || tongVND.trim().length() == 0)
			tongVND = "0";				
		obj.setTongTienVND(Double.parseDouble(tongVND));

		String kyhieuhoadon = util.antiSQLInspection(request.getParameter("kyhieuhoadon"));
		if (kyhieuhoadon == null)
			kyhieuhoadon = "";				
		obj.setKyHieuHoaDon(kyhieuhoadon);

		String ngayhoadon = util.antiSQLInspection(request.getParameter("ngayhoadon"));
		if (ngayhoadon == null)
			ngayhoadon = "";				
		obj.setNgayHoaDon(ngayhoadon);

		String chenhlechhd = util.antiSQLInspection(request.getParameter("chenhlechhd"));
		if (chenhlechhd == null || chenhlechhd.trim().length() == 0)
			chenhlechhd = "0";				
		obj.setTongTienVND(Double.parseDouble(chenhlechhd));

		String khobiettruId = util.antiSQLInspection(request.getParameter("khobiettruId"));
		if (khobiettruId == null)
			khobiettruId = "";				
		obj.setKhoBietTruId(khobiettruId);


		String khotontruId = util.antiSQLInspection(request.getParameter("khotontruId"));
		if (khotontruId == null)
			khotontruId = "";				
		obj.setKhoTonTruId(khotontruId);

		String dienGiai = util.antiSQLInspection(request.getParameter("dienGiai"));
		if (dienGiai == null)
			dienGiai = "";				
		obj.setDienGiai(dienGiai);

		String khoanmuc = util.antiSQLInspection(request.getParameter("khoanmuc"));
		if (khoanmuc == null)
			khoanmuc = "";				
		obj.setKhoanMucId(khoanmuc);

		String hosokemtheo = util.antiSQLInspection(request.getParameter("hosokemtheo"));
		if (hosokemtheo == null)
			hosokemtheo = "";				
		obj.setHosokemtheo(hosokemtheo);


		String sanphamkiemdinh = util.antiSQLInspection(request.getParameter("sanphamkiemdinh"));
		if (sanphamkiemdinh == null)
			sanphamkiemdinh = "";				
		obj.setSanphamId(sanphamkiemdinh);
		String [] masp = request.getParameterValues("masp");
		String [] tensp = request.getParameterValues("tensp");
		String [] donvi = request.getParameterValues("donvi");
		//String [] soluongphieunop = request.getParameterValues("soluongphieunop");

		/*String [] soluonglaymau = request.getParameterValues("soluonglaymau");
		String [] soluongmauluu = request.getParameterValues("soluongmauluu");
		String [] soluongtd = request.getParameterValues("soluongtd");*/
		String [] thoigiandukien = request.getParameterValues("thoigiandukien");


		if(masp != null){
			obj.getDsPhieuKiemDinhs().clear();
			for(int i = 0; i < masp.length; i++){
				if(masp[i].trim().length() > 0){
					String temID = masp[i]+"__ ";


					String [] _spId = request.getParameterValues(temID+"_spId");
					String [] _spSLlaymau = request.getParameterValues(temID+"_spSLlaymau");
					String [] _spSLmauluu = request.getParameterValues(temID+"_spSLmauluu");
					String [] _spSLDoOnDinh = request.getParameterValues(temID+"_spSLDoOnDinh");

					String [] lohang = request.getParameterValues(temID+"_lohang");
					String [] mathung = request.getParameterValues(temID+"_mathung");
					String [] mame = request.getParameterValues(temID+"_mame");
					String [] _spVitri = request.getParameterValues(temID+"_spVitri");
					String [] _spMAPHIEU = request.getParameterValues(temID+"_spMAPHIEU");
					String [] _spPhieudt = request.getParameterValues(temID+"_spPhieudt");
					String [] _spPhieueo = request.getParameterValues(temID+"_spPhieueo");
					String [] _spMarq = request.getParameterValues(temID+"_spMarq");

					String [] ngayhh = request.getParameterValues(temID+"_ngayhh");
					String [] ngaysx = request.getParameterValues(temID+"_ngaysx");
					String [] _spNhasanxuat = request.getParameterValues(temID+"_spNhasanxuatID");
					String [] _spHamluong = request.getParameterValues(temID+"_spHamluong");
					String [] _spHamam = request.getParameterValues(temID+"_spHamam");

					String [] soluongphieunop = request.getParameterValues(temID+"_spSoluong");
					String [] soluongconlai = request.getParameterValues(temID+"soluongconlai");
					System.out.println("_spId "+_spId);
					for(int j=0;j< _spId.length;j++){
						PhieuKiemDinh phieuKiemDinh = new PhieuKiemDinh();
						phieuKiemDinh.setMaSp(masp[i]);
						phieuKiemDinh.setTenSp(tensp[i]);
						phieuKiemDinh.setDvt(donvi[i]);
						//	phieuKiemDinh.setNgaySX(ngaysx[i]);
						//phieuKiemDinh.setSoLuongPhieuNop(soluongphieunop[i].replaceAll(",", ""));
						phieuKiemDinh.setThoiGianHuyMai(thoigiandukien[i]);

						System.out.println("so lan luu "+j);
						phieuKiemDinh.setMaThung(mathung[j]);

						phieuKiemDinh.setSoLuongLayMau(_spSLlaymau[j].replaceAll(",", ""));
						phieuKiemDinh.setSoLuongMauLuu(_spSLmauluu[j].replaceAll(",", ""));
						phieuKiemDinh.setSoLuongTheoDoiDoOnDinh(_spSLDoOnDinh[j].replaceAll(",", ""));
						phieuKiemDinh.setSoLuongConLai(soluongconlai[j].replaceAll(",", ""));

						phieuKiemDinh.setSoLuongPhieuNop(soluongphieunop[j].replaceAll(",", ""));
						phieuKiemDinh.setNgayHetHan(ngayhh[j]);
						phieuKiemDinh.setMaMe(mame[j]);
						phieuKiemDinh.setNgaySX(ngaysx[j]);
						phieuKiemDinh.setLoHang(lohang[j]);
						phieuKiemDinh.setVitri(_spVitri[j]);
						phieuKiemDinh.setMaphieu(_spMAPHIEU[j]);
						phieuKiemDinh.setPhieudt(_spPhieudt[j]);
						phieuKiemDinh.setPhieueo(_spPhieueo[j]);
						phieuKiemDinh.setMarq(_spMarq[j]);
						phieuKiemDinh.setNhasx_Id(_spNhasanxuat[j]);
						phieuKiemDinh.setHamluong(_spHamluong[j]);
						phieuKiemDinh.setHamam(_spHamam[j]);
						phieuKiemDinh.setSott(j);

						obj.getDsPhieuKiemDinhs().add(phieuKiemDinh);
					}
				}
			}
		}


	}

}
