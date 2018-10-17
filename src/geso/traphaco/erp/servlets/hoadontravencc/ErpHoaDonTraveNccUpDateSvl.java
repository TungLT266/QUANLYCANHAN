package geso.traphaco.erp.servlets.hoadontravencc;
 
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadontravencc.IErpHoaDon;
import geso.traphaco.erp.beans.hoadontravencc.IErpHoaDon_SP;
import geso.traphaco.erp.beans.hoadontravencc.imp.ErpHoaDon;
import geso.traphaco.erp.beans.nhapkhoNK.ISpDetail;
import geso.traphaco.erp.beans.nhapkhoNK.imp.SpDetail;
import geso.traphaco.erp.beans.shiphang.ISanpham;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ErpHoaDonUpDateSvl")
public class ErpHoaDonTraveNccUpDateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpHoaDonTraveNccUpDateSvl() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		out.println(action);

		String ddhId = util.getId(querystring);
		
		System.out.println("ddhID:"+ddhId);
		String userId = util.getUserId(querystring);
		 
		if (action.equals("update")) {
			IErpHoaDon dhBean = new ErpHoaDon(ddhId);
			dhBean.initdisplay(ddhId);
			dhBean.CreateRs(true);
			session.setAttribute("loaihoadon", dhBean.getLoaihoadon());
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTraveNccNew.jsp";
			session.setAttribute("obj", dhBean);
			response.sendRedirect(nextJSP);
		} else if (action.equals("display")) {
			IErpHoaDon dhBean = new ErpHoaDon(ddhId);
			dhBean.initdisplay(ddhId);
			session.setAttribute("obj", dhBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTraveNccDisPlay.jsp";
			response.sendRedirect(nextJSP);
		}  
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		System.out.println(" dav vo");
		int loi = 0;
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
		session.setAttribute("ctyId", (String)session.getAttribute("congtyId"));

		String action = request.getParameter("action");

		String tenform = request.getParameter("tenform");
		String nextJSP = "";
		if (tenform.equals("newform")) {
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTraveNccNew.jsp";
		} else {
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTraveNccNew.jsp";
		}
		IErpHoaDon dhBean = null;
		if (id.equals(""))
			dhBean = new ErpHoaDon();
		else
			dhBean = new ErpHoaDon(id);

		String ngayxuathd = util.antiSQLInspection(request.getParameter("ngayxuathd"));
		dhBean.setNgayxuathd(ngayxuathd);

		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		dhBean.SetSoHoaDon(sohoadon);

		String kyhieu = util.antiSQLInspection(request.getParameter("kyhieu"));
		dhBean.SetKyHieu(kyhieu);
		
		String loaihoadon = "6";
		dhBean.setLoaihoadon(loaihoadon);
		session.setAttribute("loaihoadon", loaihoadon);

		String hinhthuc = util.antiSQLInspection(request.getParameter("hinhthuc"));
		dhBean.sethinhthuctt(hinhthuc);

		String nguoimuahang = util.antiSQLInspection(request.getParameter("nguoimuahang"));
		if(nguoimuahang==null) nguoimuahang="";
		dhBean.SetNguoiMuaHang(nguoimuahang);
		String ngaytao = this.getDateTime();
		String ngaysua = ngaytao;
		dhBean.setNgaytao(ngaytao);
		dhBean.setNgaysua(ngaysua);
		dhBean.setNguoitao(userId);
		dhBean.setNguoisua(userId);

		String tenkhxhd=util.antiSQLInspection(request.getParameter("tenkhxhd"));
		dhBean.setTenKhXhd(tenkhxhd);
		
		String diachikhxhd=util.antiSQLInspection(request.getParameter("diachikhxhd"));
		dhBean.setDiachiXhd(diachikhxhd);
		
		String masothue=util.antiSQLInspection(request.getParameter("masothue"));
		dhBean.setMasothueXhd(masothue);
		
		String nhappid = util.antiSQLInspection(request.getParameter("nhappid"));

		if (nhappid == null) {
			nhappid = "";
		}
		
		dhBean.SetNppId(nhappid);
		
		String tennpp = util.antiSQLInspection(request.getParameter("tennpp"));
		dhBean.setNppTen(tennpp);
		
		String ghichuhd=util.antiSQLInspection(request.getParameter("ghichuhd"));
		dhBean.SetGhiChu(ghichuhd);
	
		String PoMt = util.antiSQLInspection(request.getParameter("POMT"));
		dhBean.SetPOMT(PoMt);

		String[] ddhid = request.getParameterValues("ddhid");
		if (ddhid != null) {
			dhBean.setDonDatHang(ddhid);
		}
		String[] scheme_chietkhau = request.getParameterValues("scheme_chietkhau");
		String[] scheme_ck_thanhtien = request.getParameterValues("scheme_ck_thanhtien");
		dhBean.setScheme_chietkhau(scheme_chietkhau);
		dhBean.setScheme_ck_thanhtien(scheme_ck_thanhtien);
		
		dhBean.setCongtyId((String)session.getAttribute("congtyId"));
		
		
		 if(!action.equals("reload"))
		 {
			// lay list sp app vo lo
				List<IErpHoaDon_SP> splist= dhBean.GetListSanPham();
				List<IErpHoaDon_SP> splistNew= new ArrayList<IErpHoaDon_SP>();
				if(splist!= null){
					IErpHoaDon_SP sanpham;
					int size = splist.size();
					int m = 0;
					while (m < size){
						sanpham = splist.get(m);
						String chuoi=sanpham.getIdSanPham()+"."+ m;
						System.out.println("chuoi");
						String[] solo = request.getParameterValues(chuoi + ".solo");
						String[] soluong = request.getParameterValues(chuoi + ".soluong");
						String[] ngaynhapkho = request.getParameterValues(chuoi + ".ngaynhapkho");
						String[] ngayhethan = request.getParameterValues(chuoi + ".ngayhethan");
						
						String[] mame = request.getParameterValues(chuoi + ".mame");
						String[] mathung = request.getParameterValues(chuoi + ".mathung");
						String[] maphieu = request.getParameterValues(chuoi + ".maphieu");
						String[] khott_fk = request.getParameterValues(chuoi + ".khott_fk");
						String[] bin_fk = request.getParameterValues(chuoi + ".bin_fk");
						String[] hamluong = request.getParameterValues(chuoi + ".hamluong");
						String[] hamam = request.getParameterValues(chuoi + ".hamam");
						String[] marq = request.getParameterValues(chuoi + ".marq");
						String[] nsxid = request.getParameterValues(chuoi + ".nsxid");
						String[] nsxten = request.getParameterValues(chuoi + ".nsxten");
						List<ISpDetail> lstspdt = new ArrayList<ISpDetail>();
						if(solo!= null && soluong!= null && ngayhethan!= null){
							
							System.out.println("solo ko null");
							System.out.println("solo.length " + solo.length);
							System.out.println("soluong.length " + soluong.length);
							System.out.println("ngayhethan.length " + ngayhethan.length);
							System.out.println("ngaysanxuat.length " + ngaynhapkho.length);
						
							for (int i = 0; i < solo.length; i++) {
								if(soluong[i] != null && solo[i] != null  && ngayhethan[i] != null){
									if (soluong[i] != "" && solo[i] != ""  && ngayhethan[i] != "") {
										ISpDetail spdt = new SpDetail();
										spdt.setSolo(solo[i]);
										spdt.setSoluong(soluong[i]);
										//spdt.setNgaySx(ngaysanxuat[i]);
										spdt.setNgayHethan(ngayhethan[i]);
										spdt.setKhott_fk(khott_fk[i]);
										spdt.setMame(mame[i]);
										spdt.setMathung(mathung[i]);
										spdt.setMaphieu(maphieu[i]);
										spdt.setNgaynhapkho(ngaynhapkho[i]);
										spdt.setkhuid(bin_fk[i]);
										spdt.setHamluong(hamluong[i]);
										spdt.setHamam(hamam[i]);
										spdt.setMarrquet(marq[i]);
										spdt.setNSXid(nsxid[i]);
										spdt.setNSXTen(nsxten[i]);
										lstspdt.add(spdt);
									}
								}
								System.out.println("solo " + solo[i]);
							}
							System.out.println("solo size" + lstspdt.size());
							System.out.println("solo size sau:" + sanpham.getSpDetail().size());
						}
						sanpham.setSpDetail(lstspdt);	
						splistNew.add(sanpham);
						m++;
					}
				}
				dhBean.setListsanpham(splistNew);
				
		 }

		 dhBean.CreateRs(action.equals("reload"));
		
		
		if (action.equals("save")) {
			if (id.equals("")) {
				if (!(dhBean.Save())) {
					session.setAttribute("obj", dhBean);
					response.sendRedirect(nextJSP);

				} else {
					dhBean = new ErpHoaDon();
					dhBean.setUserId(userId);
					dhBean.setCongtyId((String)session.getAttribute("congtyId"));
					dhBean.setListHoaDon("");

					session.setAttribute("obj", dhBean);

					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTraveNcc.jsp";
					response.sendRedirect(nextJSP);

				}
			} else {
			
				if (dhBean.getTrangthai().equals("0")) {
					if (!dhBean.Edit()) {
						session.setAttribute("obj", dhBean);
						response.sendRedirect(nextJSP);
					} else {
						dhBean = new ErpHoaDon();
						dhBean.setUserId(userId);
						dhBean.setCongtyId((String)session.getAttribute("congtyId"));
						dhBean.setListHoaDon("");

						session.setAttribute("obj", dhBean);

						nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTraveNcc.jsp";
						response.sendRedirect(nextJSP);
					}
				} else {
					
					if (!dhBean.EditHT()) {
						session.setAttribute("obj", dhBean);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonChuaHT.jsp";
						response.sendRedirect(nextJSP);
					} else {
						dhBean = new ErpHoaDon();
						dhBean.setUserId(userId);
						dhBean.setCongtyId((String)session.getAttribute("congtyId"));
						dhBean.setListHoaDon("");
						session.setAttribute("obj", dhBean);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonTraveNcc.jsp";
						response.sendRedirect(nextJSP);
					}
				}

			}

		} else if (action.equals("reload")) {
			session.setAttribute("obj", dhBean);
			response.sendRedirect(nextJSP);

		}

	}

}
