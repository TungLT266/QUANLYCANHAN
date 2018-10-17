package geso.traphaco.erp.servlets.lapkehoach;

import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.danhmucvattu.imp.Danhmucvattu_SP;
import geso.traphaco.erp.beans.lapkehoach.IErpBom;
import geso.traphaco.erp.beans.lapkehoach.IErpBomList;
import geso.traphaco.erp.beans.lapkehoach.IErpDanhMucVatTuThanhPhan;
import geso.traphaco.erp.beans.lapkehoach.IErpDinhmuc;
import geso.traphaco.erp.beans.lapkehoach.imp.ErpBom;
import geso.traphaco.erp.beans.lapkehoach.imp.ErpBomList;
import geso.traphaco.erp.beans.lapkehoach.imp.ErpDanhMucVatTuThanhPhan;
import geso.traphaco.erp.beans.lapkehoach.imp.ErpDinhmuc;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpBomUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public ErpBomUpdateSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		IErpBom bomBean;

		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);

		bomBean = new ErpBom(id);
		bomBean.setId(id);
		bomBean.setUserId(userId);
		String ctyId = (String) session.getAttribute("congtyId");
		bomBean.setCtyId(ctyId);

		bomBean.init();

		String action = util.getAction(querystring);
		if (action == null) {
			action = "";
		}
		String nextJSP = "";
		System.out.println("Actiong nek ; " + action);

		if (action.equals("update")) {
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpBomUpdate.jsp";
		} else if (action.equals("copy")) {
			bomBean.setId(null);
			bomBean.setDiengiai("");
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpBomNew.jsp";
		} else {
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpBomDisplay.jsp";
		}

		IErpDanhMucVatTuThanhPhan bomThanhPhan = new ErpDanhMucVatTuThanhPhan();
		bomThanhPhan.setDanhmucvattu_fk(id);
		bomThanhPhan.initDanhSachVatTu();
		session.setAttribute("bomThanhPhan", bomThanhPhan);
		session.setAttribute("bomBean", bomBean);

		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpBom bomBean;

		Utility util = new Utility();
		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null) {
			bomBean = new ErpBom();
		} else {
			bomBean = new ErpBom(id);
		}
		String ctyId = (String) session.getAttribute("congtyId");
		bomBean.setCtyId(ctyId);

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		bomBean.setUserId(userId);

		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		bomBean.setDiengiai(diengiai);
		
		String ngaybanhanhqtsx = util.antiSQLInspection(request.getParameter("ngaybanhanhqtsx"));
		if (ngaybanhanhqtsx == null)
			ngaybanhanhqtsx = "";
		bomBean.setNgaybanhanhQTSX(ngaybanhanhqtsx);
		
		String daychuyensanxuat = util.antiSQLInspection(request.getParameter("daychuyensanxuat"));
		if (daychuyensanxuat == null)
			daychuyensanxuat = "";
		bomBean.setDaychuyensanxuat(daychuyensanxuat);

		String tenbom = util.antiSQLInspection(request.getParameter("tenbom"));
		if (tenbom == null)
			tenbom = "";
		bomBean.setTenBOM(tenbom);

		String vanbanhuongdan = util.antiSQLInspection(request.getParameter("vanbanhuongdan"));
		if (vanbanhuongdan == null)
			vanbanhuongdan = "";
		bomBean.setVanBanHuongDan(vanbanhuongdan);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		bomBean.setTrangthai(trangthai);

		System.out.println("TRẠNG THÁI: " + trangthai);

		String trongluong = util.antiSQLInspection(request.getParameter("trongluong"));
		if (trongluong == null)
			trongluong = "0";
		try {
			trongluong = "" + Double.parseDouble(trongluong);
		} catch (Exception er) {
			trongluong = "0";
		}
		bomBean.setTrongluong(trongluong);

		String loaisp = util.antiSQLInspection(request.getParameter("loaisp"));
		if (loaisp == null)
			loaisp = "";
		bomBean.setLoaispId(loaisp);

		String pthaohut = util.antiSQLInspection(request.getParameter("pthaohut"));
		if (pthaohut == null)
			pthaohut = "0";
		bomBean.setPTHaoHut(Double.parseDouble(pthaohut));

		String dvkdid = util.antiSQLInspection(request.getParameter("dvkdid"));
		if (dvkdid == null)
			dvkdid = "";
		bomBean.setDvkdId(dvkdid);

		String spId = util.antiSQLInspection(request.getParameter("spId"));
		if (spId == null)
			spId = "";
		bomBean.setSpMa(spId);
		String noisanxuat = util.antiSQLInspection(request.getParameter("noisanxuat"));
		if (noisanxuat == null)
			noisanxuat = "";
		bomBean.setNoisanxuat(noisanxuat);
		String ISKHONGTHOIHAN = util.antiSQLInspection(request.getParameter("ISKHONGTHOIHAN"));
		if (ISKHONGTHOIHAN == null)
			ISKHONGTHOIHAN = "0";
		bomBean.setIskhongthoihan(ISKHONGTHOIHAN);

		String soluongchuan = util.antiSQLInspection(request.getParameter("soluongchuan"));
		if (soluongchuan == null)
			soluongchuan = "";
		bomBean.setSoluongchuan(soluongchuan);

		String hieuluctu = util.antiSQLInspection(request.getParameter("hieuluctu"));
		if (hieuluctu == null)
			hieuluctu = "";
		bomBean.setHieuluctu(hieuluctu);

		String hieulucden = util.antiSQLInspection(request.getParameter("hieulucden"));
		if (hieulucden == null)
			hieulucden = "";
		bomBean.setHieulucden(hieulucden);

		String ngayBH = util.antiSQLInspection(request.getParameter("ngayBH"));
		if (ngayBH == null)
			ngayBH = "";
		bomBean.setNgayBH(ngayBH);

		String quycach = util.antiSQLInspection(request.getParameter("quycach"));
		if (quycach == null)
			quycach = "";
		bomBean.setquycach(quycach);

		String lanBH = util.antiSQLInspection(request.getParameter("lanBH"));
		if (lanBH == null)
			lanBH = "";
		bomBean.setLanBH(lanBH);

		String sudung = util.antiSQLInspection(request.getParameter("sudung"));
		if (sudung == null)
			sudung = "0";
		bomBean.setSudung(sudung);

		String dvtSP = util.antiSQLInspection(request.getParameter("dvtSP"));
		if (dvtSP == null)
			dvtSP = "";
		bomBean.setDonViTinh(dvtSP);

		String[] nhamayid = request.getParameterValues("nhamayid");
		String nhamayidstr = "";
		if (nhamayid != null) {
			for (int i = 0; i < nhamayid.length; i++) {
				nhamayidstr += (nhamayidstr.length() > 0 ? "," : "") + nhamayid[i];
			}
		}
		System.out.println("nhamayidstr: " + nhamayidstr);
		bomBean.setIdNhamay(nhamayidstr);

		String[] kbsxid = request.getParameterValues("kbsxid");
		String kbsxidstr = "";
		if (kbsxid != null) {
			for (int i = 0; i < kbsxid.length; i++) {
				kbsxidstr += (kbsxidstr.length() > 0 ? "," : "") + kbsxid[i];
			}
		}
		bomBean.setKichBanSXId(kbsxidstr);
		
		
		String quytrinhsx = util.antiSQLInspection(request.getParameter("quytrinhsx"));
		if (quytrinhsx == null)
			quytrinhsx = "";
		bomBean.setQuytrinhsx(quytrinhsx);
		

		String[] vtId = request.getParameterValues("vtId");
		String[] mavt = request.getParameterValues("mavattu");
		String[] tenvt = request.getParameterValues("tenvattu");
		String[] soluong = request.getParameterValues("soluong");
		String[] dungsai = request.getParameterValues("dungsai");
		String[] haohutSP = request.getParameterValues("pthaohutsp");
		String[] donvitinh = request.getParameterValues("donvitinh");
		String[] hamam = request.getParameterValues("hamam");
		String[] hamluong = request.getParameterValues("hamluong");

		String action = request.getParameter("action");

		String[] dmId = request.getParameterValues("dmid");
		String[] dmloai = request.getParameterValues("dmloai");
		String[] dmSoluong = request.getParameterValues("dmsoluong");
		String[] dmDongia = request.getParameterValues("dmdongia");
		String[] dmThanhtien = request.getParameterValues("dmthanhtien");

		String[] dongdu = request.getParameterValues("dongdu");
		String[] ghichuct = request.getParameterValues("ghichuct");

		List<IErpDinhmuc> dmlst = new ArrayList<IErpDinhmuc>();
		if (dmThanhtien != null) { // quan tâm đến thành tiền
			for (int i = 0; i < dmThanhtien.length; i++) {
				IErpDinhmuc dm = new ErpDinhmuc();
				if (dmThanhtien[i].replace(",", "") != "") { // &&
																// Double.parseDouble(dmThanhtien[i].replace(",",
																// "") > 0
					dm.setId(dmId[i]);
					dm.setLoai(dmloai[i]);
					if (dmloai[i].equals("0")) {
						dm.setSoluong(Double.parseDouble(dmSoluong[i].replace(",", "")));
						dm.setDongia(Double.parseDouble(dmDongia[i].replace(",", "")));
					}
					dm.setThanhtien(Double.parseDouble(dmThanhtien[i].replace(",", "")));
					dmlst.add(dm);
				}
			}
		}
		bomBean.setDinhmucList(dmlst);

		List<IDanhmucvattu_SP> spList = new ArrayList<IDanhmucvattu_SP>();
		if (mavt != null) {
			for (int m = 0; m < mavt.length; m++) {
				if (mavt[m] != "") {

					String soluong1 = "0";
					try {
						soluong1 = "" + Double.parseDouble(soluong[m].replaceAll(",", ""));

					} catch (Exception er) {

					}

					IDanhmucvattu_SP sanpham = null;
					sanpham = new Danhmucvattu_SP();
					sanpham.setIdVT(vtId[m]);
					sanpham.setMaVatTu(mavt[m]);
					sanpham.setTenVatTu(tenvt[m]);
					String tinhHA = request.getParameter("tinhHA_" + m);
					if (tinhHA == null)
						sanpham.setIsTinhHA("0");
					else
						sanpham.setIsTinhHA("1");

					String tinhHL = request.getParameter("tinhHL_" + m);
					if (tinhHL == null)
						sanpham.setIsTinhHL("0");
					else
						sanpham.setIsTinhHL("1");

					String nltieuhao = request.getParameter("nltieuhao_" + m);
					if (nltieuhao == null)
						sanpham.setIsNLTieuHao("0");
					else
						sanpham.setIsNLTieuHao(nltieuhao);
					sanpham.setSoLuong(soluong1);
					sanpham.setDungsai(dungsai[m]);
					sanpham.setDvtVT(donvitinh[m]);
					sanpham.setHamam(hamam[m]);
					sanpham.setHamluong(hamluong[m]);
					sanpham.setDongdu(dongdu[m]);
					sanpham.setGhichu(ghichuct[m]);

					String[] maNLTT = request.getParameterValues("mavattuTT_" + m);
					sanpham.setMaNLTT(maNLTT);

					String[] soluongNLTT = request.getParameterValues("soluongTT_" + m);
					sanpham.setSoluongNLTT(soluongNLTT);

					if (!action.equals("save")) {
						sanpham.setHaoHut(bomBean.getPTHaoHut());
					} else if (haohutSP[m].length() <= 0 || haohutSP[m] == null)
						sanpham.setHaoHut(bomBean.getPTHaoHut());
					else
						sanpham.setHaoHut(Double.parseDouble(haohutSP[m]));

					spList.add(sanpham);

				}
			}
			bomBean.setListDanhMuc(spList);
		}

		// Lay danh sach vet tu thanh phan
		List<IErpDanhMucVatTuThanhPhan> vttpList = new ArrayList<IErpDanhMucVatTuThanhPhan>();
		String[] sanpham_fk = request.getParameterValues("sanpham_fk");
		String[] soluong_sptp = request.getParameterValues("soluongthem");
		String[] dvdl_fk = request.getParameterValues("dvdl_fk");
		String[] tenVTTP = request.getParameterValues("sanphamthem");
		String[] tenDonVi = request.getParameterValues("donvithem");
		if (sanpham_fk != null) {
			for (int k = 0; k < sanpham_fk.length; k++) {
				if (sanpham_fk[k] != "") {
					IErpDanhMucVatTuThanhPhan vttp = new ErpDanhMucVatTuThanhPhan();
					vttp.setSanpham_fk(sanpham_fk[k].trim());
					vttp.setDvdl_fk(dvdl_fk[k]);
					vttp.setSoluong(soluong_sptp[k]);
					vttp.setTenVTTP(tenVTTP[k]);
					vttp.setTenDonVi(tenDonVi[k]);
					vttpList.add(vttp);
				}
			}
		}

		IErpDanhMucVatTuThanhPhan bomThanhPhan = new ErpDanhMucVatTuThanhPhan();
		bomThanhPhan.setSptpList(vttpList);

		System.out.println("ACTION: " + action);
		if (action.equals("save")) {
			if (id == null)// create
			{
				System.out.println("COPY");
				try {
					if (!(bomBean.createBom(request)) || !((bomThanhPhan.createDanhMucVatTuThanhPhan())) ) {
						bomBean.createRs();
						session.setAttribute("bomBean", bomBean);
						session.setAttribute("userId", userId);
						session.setAttribute("bomThanhPhan", bomThanhPhan);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBomNew.jsp";
						response.sendRedirect(nextJSP);

					} else {

						IErpBomList obj = new ErpBomList();
						obj.setCtyId(ctyId);
						obj.init("");
						bomBean.DbClose();
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBom.jsp";
						response.sendRedirect(nextJSP);

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {// update
				System.out.println("COPY SAI: " + id);
				bomThanhPhan.setDanhmucvattu_fk(id);
				if (!(bomBean.updateBom(request)) || !(bomThanhPhan.updateDanhMucVatTuThanhPhan()) ) {
					bomBean.createRs();
					session.setAttribute("bomBean", bomBean);
					session.setAttribute("userId", userId);
					session.setAttribute("bomThanhPhan", bomThanhPhan);

					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBomUpdate.jsp";
					response.sendRedirect(nextJSP);
				} else {
					IErpBomList obj = new ErpBomList();
					obj.setCtyId(ctyId);
					obj.init("");
					bomBean.DbClose();
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBom.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} else {
			System.out.println("RELOAD FORM");
			bomBean.createRs();

			session.setAttribute("userId", userId);
			session.setAttribute("bomBean", bomBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBomNew.jsp";

			if (id != null) {
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpBomUpdate.jsp";
			}

			response.sendRedirect(nextJSP);
		}
	}

}
