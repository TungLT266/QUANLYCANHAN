package geso.traphaco.erp.servlets.donmuahangtrongnuoc;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IErpDonmuahangList_Giay;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IErpDonmuahang_Giay;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IKho;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.INgaynhan;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.ISanPhamPhanBo;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.ISanpham;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.ISanphamBom;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.ErpDonmuahangList_Giay;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.ErpDonmuahang_Giay;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.Ngaynhan;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.SanPhamPhanBo;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.Sanpham;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.SanphamBom;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ISpSanxuatChitiet;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.SpSanxuatChitiet;
import geso.traphaco.erp.servlets.donmuahang.ErpDonmuahangUpdate_GiaySvl;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.ListBox;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpDonmuahangtrongnuocUpdate_GiaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpDonmuahangtrongnuocUpdate_GiaySvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nextJSP;
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session
				.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			IErpDonmuahang_Giay dmhBean = new ErpDonmuahang_Giay(id);
			dmhBean.setCongtyId((String) session.getAttribute("congtyId"));
			dmhBean.setUserId(userId); // phai co UserId truoc khi Init

			String task = request.getParameter("task");
			if (task == null)
				task = "";

			String canduyet = request.getParameter("duyet");
			if (canduyet == null)
				canduyet = "1";
			dmhBean.setCanDuyet(canduyet);

			if (task.equals("print")) {
				Create_PO_PDF(response, request);
			} else {
				
				dmhBean.init();
				session.setAttribute("isGiaCong", dmhBean.getIsGiaCong());

				if (request.getQueryString().indexOf("display") >= 0) {
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocDisplay_Giay.jsp";
				} else {
					if (request.getQueryString().indexOf("hoantat") >= 0)
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpHTDonMuaHangtrongnuocDisplay.jsp";
					else {
						session.setAttribute("nhacungcapNK",
								dmhBean.getNhacungcapNK());
						session.setAttribute("ngaymuahang",
								dmhBean.getNgaymuahang());
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocUpdate_Giay.jsp";
					}
				}

				session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
				session.setAttribute("lspId", dmhBean.getLoaispId());
				session.setAttribute("dmhBean", dmhBean);
				session.setAttribute("loaimh", dmhBean.getLoai());
				session.setAttribute("nccId", dmhBean.getNCC());
				response.sendRedirect(nextJSP);
			}
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
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			session.setMaxInactiveInterval(30000);

			IErpDonmuahang_Giay dmhBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			System.out.println("ID" + id);
			if (id == null) {
				dmhBean = new ErpDonmuahang_Giay("");
			} else {
				dmhBean = new ErpDonmuahang_Giay(id);
			}
			String contyId = (String) session.getAttribute("congtyId");

			session.setAttribute("ctyId", contyId);
			dmhBean.setCongtyId(contyId);
			dmhBean.setUserId(userId);

			String nhacungcapNK = request.getParameter("nhacungcapNK");
			if (nhacungcapNK == null) {
				nhacungcapNK = "";
			}
			session.setAttribute("nhacungcapNK", nhacungcapNK);
			dmhBean.setNhacungcapNK(nhacungcapNK);

			String loai = request.getParameter("loai");
			if (loai == null)
				loai = "";
			session.setAttribute("loaimh", loai);
			dmhBean.setLoai(loai);

			String isphanbo = request.getParameter("isphanbo");
			if (isphanbo == null)
				isphanbo = "0";
			dmhBean.setIsDuocPhanBo(isphanbo);

			String loaiDMH_NK = request.getParameter("loaiDMH_NK");
			if (loaiDMH_NK == null)
				loaiDMH_NK = "";
			dmhBean.setLoaiDMH_NK(loaiDMH_NK);

			String canduyet = request.getParameter("duyet");
			if (canduyet == null)
				canduyet = "1";
			dmhBean.setCanDuyet(canduyet);

			String ngaygd = util.antiSQLInspection(request
					.getParameter("ngaymuahang"));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();
			session.setAttribute("ngaymuahang", ngaygd);
			dmhBean.setNgaymuahang(ngaygd);
			
			String ngaybd = util.antiSQLInspection(request.getParameter("ngaybatdau"));
			if (ngaybd == null)
				ngaybd = "";
			dmhBean.setNgaybatdau(ngaybd);

			String ETD = util.antiSQLInspection(request.getParameter("ETD"));
			if (ETD == null)
				ETD = "";
			dmhBean.setETD(ETD);
			String ETA = util.antiSQLInspection(request.getParameter("ETA"));
			if (ETA == null)
				ETA = "";
			dmhBean.setETA(ETA);

			String dvth = util
					.antiSQLInspection(request.getParameter("dvthId"));
			if (dvth == null)
				dvth = "";
			dmhBean.setDvthId(dvth);

			String loaihh = util.antiSQLInspection(request
					.getParameter("loaihh"));
			if (loaihh == null)
				loaihh = "";
			session.setAttribute("lhhId", loaihh);
			dmhBean.setLoaihanghoa(loaihh);

			String loaisp = util.antiSQLInspection(request
					.getParameter("loaisp"));
			if (loaisp == null)
				loaisp = "";
			session.setAttribute("lspId", loaisp);
			dmhBean.setLoaispId(loaisp);

			String NguonGocHH = util.antiSQLInspection(request
					.getParameter("nguongoc"));
			if (NguonGocHH == null)
				NguonGocHH = "";
			if (dmhBean.getLoai().equals("0"))
				NguonGocHH = "NN";
			if (dmhBean.getLoai().equals("1"))
				NguonGocHH = "TN";
			dmhBean.setNguonGocHH(NguonGocHH);

			String nccId = util
					.antiSQLInspection(request.getParameter("nccId"));
			if (nccId == null)
				nccId = "";
			System.out.println("NCC " + nccId);
			session.setAttribute("nccId", nccId);
			dmhBean.setNCC(nccId);

			String nccLoai = util.antiSQLInspection(request
					.getParameter("nccLoai"));
			if (nccLoai == null)
				nccLoai = "";
			session.setAttribute("nccLoai", nccLoai);
			dmhBean.setNccLOai(nccLoai);

			String nccTen = util.antiSQLInspection(request
					.getParameter("nccTen"));
			if (nccTen == null)
				nccTen = "";
			dmhBean.setNccTen(nccTen);

			String tiente_fk = util.antiSQLInspection(request
					.getParameter("tiente_fk"));
			if (tiente_fk == null) {
				tiente_fk = "";
			}

			if (tiente_fk.trim().length() > 0) {
				String[] arr = tiente_fk.split(" - ");

				dmhBean.setTyGiaQuyDoi(Float.parseFloat(arr[1]));
				dmhBean.setTienTe_FK(arr[0]);
			}

			String tongtientruocVAT = request.getParameter("BVAT");
			if (tongtientruocVAT == null)
				tongtientruocVAT = "0";
			else
				tongtientruocVAT = tongtientruocVAT.replaceAll(",", "");
			dmhBean.setTongtienchuaVat(tongtientruocVAT);

			String VAT = util.antiSQLInspection(request.getParameter("VAT"));
			if (VAT == null) {
				VAT = "0";
			} else if (VAT.trim().length() == 0) {
				VAT = "0";
			}
			System.out.println("VAT " + VAT);
			dmhBean.setVat(VAT.replaceAll(",", ""));

			/*
			 * if(NguonGocHH.equals("NN")) dmhBean.setVat("0");
			 */

			String tongtiensauVAT = request.getParameter("AVAT");
			if (tongtiensauVAT == null)
				tongtiensauVAT = "0";
			else
				tongtiensauVAT = tongtiensauVAT.replaceAll(",", "");
			dmhBean.setTongtiensauVat(tongtiensauVAT);

			String dungsaiTong = util.antiSQLInspection(request
					.getParameter("dungsai"));
			if (dungsaiTong == null)
				dungsaiTong = "0";
			dmhBean.setDungsai(dungsaiTong);

			String[] ghichu = request.getParameterValues("ghichu");
			dmhBean.setGhiChuArr(ghichu);

			String[] ngayThanhToanArr = request
					.getParameterValues("ngayThanhToan");
			dmhBean.setNgayThanhToanArr(ngayThanhToanArr);

			String[] soTienThanhToanArr = request
					.getParameterValues("soTienThanhToan");
			dmhBean.setSoTienThanhToanArr(soTienThanhToanArr);

			String[] phanTramThanhToanArr = request
					.getParameterValues("phanTramThanhToan");
			dmhBean.setPhanTramThanhToanArr(phanTramThanhToanArr);

			String thamchieu = util.antiSQLInspection(request
					.getParameter("sothamchieu"));
			if (thamchieu == null)
				thamchieu = "";
			dmhBean.setSoThamChieu(thamchieu);

			String httt = util.antiSQLInspection(request
					.getParameter("hinhthucthanhtoan"));
			if (httt == null)
				httt = "";
			dmhBean.setHinhThucTT(httt);

			String ddgh = util.antiSQLInspection(request
					.getParameter("diadiemgiaohang"));
			if (ddgh == null)
				ddgh = "";
			else
				ddgh = ddgh.trim();
			dmhBean.setDiaDiemGiaoHang(ddgh);

			String qlcongno = util.antiSQLInspection(request
					.getParameter("qlcongno"));
			if (qlcongno == null)
				qlcongno = "0";
			dmhBean.setQuanlycongno(qlcongno);

			String thoihanno = util.antiSQLInspection(request
					.getParameter("thoihanno"));
			if (thoihanno == null)
				thoihanno = "0";
			dmhBean.setThoihanno(thoihanno);

			String noibo = util
					.antiSQLInspection(request.getParameter("noibo"));
			if (noibo == null)
				noibo = "0";
			dmhBean.setCheckedNoiBo(noibo);

			String sohopdong = util.antiSQLInspection(request
					.getParameter("sohopdong"));
			if (sohopdong == null)
				sohopdong = "";
			dmhBean.setSohopdong(sohopdong);

			String soluonghd = util.antiSQLInspection(request
					.getParameter("soluonghd"));
			if (soluonghd == null)
				soluonghd = "";
			dmhBean.setSoluong(soluonghd);

			String tennhank = util.antiSQLInspection(request
					.getParameter("tennhank"));
			if (tennhank == null)
				tennhank = "";
			dmhBean.setTennhanhapkhau(tennhank);

			String tennhasx = util.antiSQLInspection(request
					.getParameter("tennhasx"));
			if (tennhasx == null)
				tennhasx = "";
			dmhBean.setTennhasanxuat(tennhasx);

			String ngayship = util.antiSQLInspection(request
					.getParameter("ngayship"));
			if (ngayship == null)
				ngayship = "";
			dmhBean.setNgayship(ngayship);

			String ngaynhapkho = util.antiSQLInspection(request
					.getParameter("ngaynhapkho"));
			if (ngaynhapkho == null)
				ngaynhapkho = "";
			dmhBean.setNgaynhapkho(ngaynhapkho);

			String dvchiutrachnhiem = util.antiSQLInspection(request
					.getParameter("dvchiutrachnhiem"));
			if (dvchiutrachnhiem == null)
				dvchiutrachnhiem = "";
			dmhBean.setDvChiuTrachNhiem(dvchiutrachnhiem);

			if (noibo.equalsIgnoreCase("1")) {
				loaihh = "2";
				dmhBean.setLoaihanghoa(loaihh);
				session.setAttribute("lhhId", loaihh);
			}

			String kenhId = util.antiSQLInspection(request
					.getParameter("kenhId"));
			if (kenhId == null)
				kenhId = "";
			dmhBean.setKenhId(kenhId);

			String[] cpkDiengiai = request.getParameterValues("diengiaiCPK");
			dmhBean.setCpkDiengiai(cpkDiengiai);

			String[] cpkSotien = request.getParameterValues("sotienCPK");
			dmhBean.setCpkSoTien(cpkSotien);

			String[] duyetIds = request.getParameterValues("duyetIds");
			dmhBean.setDuyetIds(duyetIds);

			// Xem loại đó có phải là gia công không?
			String isGiaCong = request.getParameter("isgiacong");
			System.out.println("giacong" + isGiaCong);
			if (isGiaCong == null) {
				isGiaCong = "0";
			}
			System.out.println("giacong" + isGiaCong);
			dmhBean.setIsGiaCong(isGiaCong);
			session.setAttribute("isGiaCong", isGiaCong);

			// Ghi chú thêm cho đơn gia công
			String ghichuGC = request.getParameter("ghichuGC");
			if (ghichuGC == null) {
				ghichuGC = "";
			}
			dmhBean.setGhiChuGC(ghichuGC);
			
			
			String ngayycthem = util.antiSQLInspection(request.getParameter("ngayycthem"));
			/*if(ngayycthem==null)
				ngayycthem="";*/  // luu ý:  ko gan rong vi ham doi tuong dang su dung
 			dmhBean.setNgayYCThemNL(ngayycthem);

			String[] idsp = request.getParameterValues("idsp");
			String[] mnlId = request.getParameterValues("mnlId");
			String[] masp = request.getParameterValues("masp");
			String[] tensp = request.getParameterValues("tensp");
			String[] sl = request.getParameterValues("soluong");
			String[] slold = request.getParameterValues("soluongOld");
			String[] donvitinh = request.getParameterValues("donvitinh");
			String[] dongia = request.getParameterValues("dongia");
			String[] dongiaold = request.getParameterValues("dongiaOld");
			String[] thuexuat = request.getParameterValues("thuexuat");
			String[] mienthue= request.getParameterValues("mienthue");
			String[] thanhtien = request.getParameterValues("thanhtien");
			String[] thuenhapkhau = request.getParameterValues("thuenhapkhau");
			String[] phantramthue = request.getParameterValues("phantramthue");
			// Thêm idmarquette
			String[] idmarquette = request.getParameterValues("idmarquette");
			String[] ngaynhandukien = request
					.getParameterValues("ngaynhandukien");

			String[] khonhan = request.getParameterValues("khonhan");
			String[] nhomhang = request.getParameterValues("nhomhang");
			String[] tenhoadon = request.getParameterValues("tenhoadon");
			String[] bomchon = request.getParameterValues("bomchon");
			String [] ismienthue=new String[idsp.length];
			if(mienthue!=null)
			{
				
				for(int i=0;i<idsp.length;i++)
				{
					for(int j=0;j< mienthue.length ;j++)
					{
						if(idsp[i].equals(mienthue[j]))
						{
							ismienthue[i]="1";
							break;
						}
						else
						{
							ismienthue[i]="0";
						}
					}
					
				}
			}
			String action = request.getParameter("action");
			List<ISanpham> spList = new ArrayList<ISanpham>();
			boolean ktDonvi = true;

			ISanpham sp = null;
			String tenhd = "";
			for (int i = 0; i < masp.length; i++) {
				// Loại hàng hóa chi phí dịch vụ (2) -> không cần nhập mã nên
				// chỉ cần kiểm tra tên
				if ((loaihh.equals("2") && tensp[i].trim().length() > 0)
						|| masp[i].trim().length() > 0) {
					String[] ngaynhan = request
							.getParameterValues("sub_ngaynhan_" + i);
					String[] soluong = request
							.getParameterValues("sub_soluong_" + i);
					List<INgaynhan> nn = new ArrayList<INgaynhan>();
					if (ngaynhan != null)
						for (int j = 0; j < ngaynhan.length; j++) {
							if ((loai.equals("2") && ngaynhan[j].trim()
									.length() > 0) || !loai.equals("2")) {
								nn.add(new Ngaynhan(ngaynhan[j].trim(),
										soluong[j].trim()));
							}
						}

					sp = new Sanpham();

					sp.setPK_SEQ(idsp[i]);
					sp.setMNLId(mnlId[i]);
					sp.setMasanpham(masp[i]);
					sp.setTensanpham(tensp[i]);
					sp.setSoluong(sl[i].replaceAll(",", ""));
					if (slold != null) {
						String slcu = slold[i].replaceAll(",", "");
						if (slcu.trim().length() > 0)
							sp.setSoluongOLD(slcu);
						else
							sp.setSoluongOLD(sl[i].replaceAll(",", ""));
					} else
						sp.setSoluongOLD(sl[i].replaceAll(",", ""));

					sp.setDonvitinh(donvitinh[i]);
					sp.setDongia(dongia[i].replaceAll(",", ""));

					if (dongiaold != null) {
						String dongiacu = dongiaold[i].replaceAll(",", "");
						if (dongiacu.trim().length() > 0)
							sp.setDongiaOLD(dongiacu);
						else
							sp.setDongiaOLD(dongia[i].replaceAll(",", ""));
					} else
						sp.setDongiaOLD(dongia[i].replaceAll(",", ""));

					sp.setThuexuat(thuexuat[i].replaceAll(",", ""));
					sp.setThanhtien(thanhtien[i].replaceAll(",", ""));
					sp.setThueNhapKhau(thuenhapkhau[i].replaceAll(",", ""));
					sp.setPhanTramThue(phantramthue[i].replaceAll(",", ""));
					sp.setIsmienthue(ismienthue[i]==null?"0":ismienthue[i]);
					sp.setNgaynhan(nn);

					// thêm idmarquette
					sp.setIdmarquette(idmarquette[i]);
					sp.setNgaynhandukien(ngaynhandukien[i]);

					List<ISanPhamPhanBo> sppb = new ArrayList<ISanPhamPhanBo>();

					if (dmhBean.getLoaihanghoa().equals("2")) {
						String[] spId = request.getParameterValues("sub_spId_"
								+ i);
						String[] spMa = request.getParameterValues("sub_spMa_"
								+ i);
						String[] spTen = request
								.getParameterValues("sub_spTen_" + i);
						String[] chon = request
								.getParameterValues("sub_spChon_" + i);

						int m = 0;
						if (sppb != null)
							for (int k = 0; k < spId.length; k++) {
								ISanPhamPhanBo pb = new SanPhamPhanBo();

								pb.setSpId(spId[k]);
								pb.setSpMa(spMa[k]);
								pb.setSpTen(spTen[k]);

								if (chon != null) {
									if (m < chon.length) {
										if (chon[m].trim().equals(
												spId[k].trim())) {
											System.out.println("Len..."
													+ chon[m]);
											pb.setChon(chon[m].trim());
											m++;
										}
									}
								}
								sppb.add(pb);

							}

						sp.setSanphamPB(sppb);
					}

					// Tùy chọn tên sản phẩm in hóa đơn
					tenhd = "";
					try {
						tenhd = tenhoadon[i].trim();
					} catch (Exception e) {
					}
					sp.setTenHD(tenhd);

					if (khonhan != null) {
						sp.setKhonhan(khonhan[i]);
					} else {
						sp.setKhonhan("");
					}

					if (donvitinh[i].trim().length() == 0
							|| donvitinh[i].equals("NA"))
						ktDonvi = false;
					sp.setNhomhang(nhomhang[i]);

					// phần lấy BOM cho sản phẩm

					List<ISanphamBom> listspbom = new ArrayList<ISanphamBom>();

					String[] bomid_bom = request
							.getParameterValues(i + "bomid");
					String[] tensp_bom = request
							.getParameterValues(i + "tensp");
					String[] masp_bom = request.getParameterValues(i + "masp");

					String[] idsp_bom = request.getParameterValues(i + "idsp");

					String[] hamam_bom = request
							.getParameterValues(i + "hamam");
					String[] hamluong_bom = request.getParameterValues(i
							+ "hamluong");
					String[] ishamam_bom = request.getParameterValues(i
							+ "ishamam");
					String[] ishamluong_bom = request.getParameterValues(i
							+ "ishamluong");

					String[] donvi_bom = request
							.getParameterValues(i + "donvi");
					String[] soluongdenghi_bom = request.getParameterValues(i
							+ "soluongdenghi");
					String[] soluongnhap_bom = request.getParameterValues(i
							+ "soluongnhap");
					NumberFormat formatter = new DecimalFormat("#######.####");
					sp.setRsBom(dmhBean.getBomRs(sp.getMasanpham()));
					if (bomchon != null) {
						sp.setBomId(bomchon[i]);
					}

					ISanphamBom spbom;
					if (!action.equals("reload_Danhmucvattu")) {
						if (masp_bom != null) {
							for (int k = 0; k < masp_bom.length; k++) {
								if (masp_bom[k].trim().length() > 0) {

									spbom = new SanphamBom();
									spbom.setIdMuahang(id);
									spbom.setSpId(idsp_bom[k]);
									System.out.println("IDSP: " + idsp_bom[k]);
									spbom.setDMVTId(bomid_bom[k]);
									spbom.setHamam(hamam_bom[k]);
									spbom.setHamluong(hamluong_bom[k]);

									spbom.setIsHamam(ishamam_bom[k]);
									spbom.setIsHamluong(ishamluong_bom[k]);

									/* spbom.setSoTT(sp.getSoTT()); */
									spbom.setDonvi(donvi_bom[k]);
									spbom.setMasanpham(masp_bom[k]);
									spbom.settensanpham(tensp_bom[k]);

									double soluongdenghi = 0;
									try {
										soluongdenghi = Double
												.parseDouble(soluongdenghi_bom[k]
														.replace(",", ""));
									} catch (Exception err) {
										// err.printStackTrace();
									}
									spbom.setSoluongdenghi(formatter
											.format(soluongdenghi));

									double soluongnhap1 = 0;
									try {
										soluongnhap1 = Double
												.parseDouble(soluongnhap_bom[k]
														.replace(",", ""));
									} catch (Exception err) {
										// err.printStackTrace();
									}

									spbom.setSoluongnhap(formatter
											.format(soluongnhap1));
									spbom.setSpGiaCongId(idsp[i]);
									// i+"_"+stt+""+sp.getPK_SEQ()
									String[] _khoid = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_khoid");
									String[] _spId = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_spId");
									String[] _spIdThayThe = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_spIdThayThe");
									String[] _KhuvucId = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_KhuvucId");
									String[] _MARQUETTE = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_MARQUETTE");
									String[] _khoten = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_khoten");
									String[] _MARQUETTE_FK = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_MARQUETTE_FK");
									String[] _MASP = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_MASP");
									String[] _SOLO = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_SOLO");
									String[] _MATHUNG = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_MATHUNG");
									String[] _MAME = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_MAME");
									String[] _HALUONG = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_HALUONG");
									String[] _HAMAM = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_HAMAM");
									String[] _NGAYHETHAN = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_NGAYHETHAN");
									String[] _NGAYNHAPKHO = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_NGAYNHAPKHO");

									String[] _MAPHIEU = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_MAPHIEU");
									String[] _MAPHIEU_TINHTINH = request
											.getParameterValues(i + "_" + k
													+ idsp[i]
													+ "_MAPHIEU_TINHTINH");
									String[] _MAPHIEU_EO = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_MAPHIEU_EO");

									String[] _khuvuckhoTen = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_khuvuckhoTen");

									String[] _bin_dayc = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_bin_dayc");
									String[] _binid_dayc = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_binid_dayc");

									String[] _Soluongton = request
											.getParameterValues(i + "_" + k
													+ idsp[i] + "_Soluongton");
									String[] _Soluongtonthute = request
											.getParameterValues(i + "_" + k
													+ idsp[i]
													+ "_Soluongtonthute");
									String[] _Soluongdexuat = request
											.getParameterValues(i + "_" + k
													+ idsp[i]
													+ "_Soluongdexuat");

									List<ISpSanxuatChitiet> listct = new ArrayList<ISpSanxuatChitiet>();

									if (_spId != null) {
										for (int t = 0; t < _spId.length; t++) {
											ISpSanxuatChitiet sp1 = new SpSanxuatChitiet();

											sp1.setKhoId(_khoid[t]);
											sp1.setIdSp(_spId[t]);
											sp1.setBin(_bin_dayc[t]);
											sp1.setBinId(_binid_dayc[t]);

											sp1.setIdSpThayThe(_spIdThayThe[t]);
											sp1.setkhuvuckhoId(_KhuvucId[t]);
											sp1.setMARQUETTE(_MARQUETTE[t]);
											sp1.setKhoTen(_khoten[t]);
											sp1.setMARQUETTE_FK(_MARQUETTE_FK[t]);
											sp1.setMaSp(_MASP[t]);
											sp1.setSolo(_SOLO[t]);
											sp1.setMATHUNG(_MATHUNG[t]);
											sp1.setMAME(_MAME[t]);
											sp1.setHAMLUONG(_HALUONG[t]);
											sp1.setHAMAM(_HAMAM[t]);

											sp1.setNGAYHETHAN(_NGAYHETHAN[t]);
											sp1.setNGAYNHAPKHO(_NGAYNHAPKHO[t]);

											sp1.setkhuvuckhoTen(_khuvuckhoTen[t]);
											sp1.setSoluongton(_Soluongton[t]);

											sp1.setSoluongtonthute(_Soluongtonthute[t]);
											sp1.setSoluong(_Soluongdexuat[t]);
											sp1.setMAPHIEU(_MAPHIEU[t]);
											sp1.setMAPHIEU_DINHTINH(_MAPHIEU_TINHTINH[t]);
											sp1.setMAPHIEU_EO(_MAPHIEU_EO[t]);

											listct.add(sp1);

										}
									}
									spbom.setListCtSP(listct);

									listspbom.add(spbom);

								}
							}
						}
					} else {

						double soluong_ct = 0;
						try {
							soluong_ct = Double.parseDouble(sp.getSoluong());
						} catch (Exception er) {
							er.printStackTrace();
						}
						listspbom = dmhBean.getListBom(sp.getBomId(),
								sp.getMasanpham(), soluong_ct, sp.getPK_SEQ(),sp.getDonvitinh());
					}
					sp.setSpListBom(listspbom);

					spList.add(sp);
				}
			}
			dmhBean.setSpList(spList);

			if (action.equals("capnhatBom") || action.equals("reload_Danhmucvattu")) {
				if (action.equals("capnhatBom")) {
					boolean bien = dmhBean.CapnhatBom();
					String msg = dmhBean.getMsg();
					dmhBean.setMsg(msg);
				}
				dmhBean.createRs();
				
			    dmhBean.setIsReloadBom("1");
			    dmhBean.init();
			    
			    
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocDisplay_Giay.jsp";
				session.setAttribute("dmhBean", dmhBean);
				session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
				session.setAttribute("lspId", dmhBean.getLoaispId());
				response.sendRedirect(nextJSP);

			} else if (action.equals("Yeucaunguyenlieu")) {
				dmhBean.setIsReloadBom("0");
				 
				
				 
				boolean	bien = dmhBean.TaoYeucauNguyenLieu_GiaCong();
			 
				if (bien == true) {
					dmhBean.setDaYeucauNguyenlieu("1");
					dmhBean.init();
				}
				String msg = dmhBean.getMsg();
				dmhBean.createRs();
				dmhBean.setMsg(msg);
				
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocDisplay_Giay.jsp";
				session.setAttribute("dmhBean", dmhBean);
				session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
				session.setAttribute("lspId", dmhBean.getLoaispId());
				response.sendRedirect(nextJSP);

			}  else if (action.equals("Yeucauthemnguyenlieu")) {
				dmhBean.setIsReloadBom("0");
				boolean bien = dmhBean.CapnhatBom_Them();
				if (bien) {
					bien = dmhBean.TaoYeucauNguyenLieu_GiaCong();
				}
				if (bien == true) {
					dmhBean.setDaYeucauNguyenlieu("1");
				}
				String msg = dmhBean.getMsg();
				dmhBean.createRs();
				dmhBean.init();
				dmhBean.setMsg(msg);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocDisplay_Giay.jsp";
				session.setAttribute("dmhBean", dmhBean);
				session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
				session.setAttribute("lspId", dmhBean.getLoaispId());
				response.sendRedirect(nextJSP);

			} 
			else if (action.equals("thaydoidvt")) {
				String thaydoidvt = request.getParameter("thaydoidvtinh");
				// co thay doi dvt
				if (!thaydoidvt.equals("-1")) {

					List<ISanpham> list_temp = dmhBean.getSpList();
					if (Integer.parseInt(thaydoidvt) < list_temp.size()) {
						ISanpham sp_temp = list_temp.get(Integer
								.parseInt(thaydoidvt));
						float tile = dmhBean.TinhLaiDonGiaQuyDoi(
								sp_temp.getPK_SEQ(), sp_temp.getDonvitinh());
						float dongia_temp = Float.parseFloat(sp_temp
								.getDongia().replaceAll(",", "")) / tile;
						dongia_temp = Math.round(dongia_temp);
						sp_temp.setDongia(String.valueOf(dongia_temp));
						list_temp.set(Integer.parseInt(thaydoidvt), sp_temp);
					}
					dmhBean.setSpList(list_temp);
					dmhBean.createRs();

					session.setAttribute("dmhBean", dmhBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocNew_Giay.jsp";
					response.sendRedirect(nextJSP);

				}

			} else if (action.equals("save")) {
				if (ktDonvi == false) {
					dmhBean.setMsg("Vui lòng nhập Đơn vị tính");
					dmhBean.createRs();
					String nextJSP;
					if (id == null) {
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocNew_Giay.jsp";
					} else {
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocUpdate_Giay.jsp";
					}
					session.setAttribute("dmhBean", dmhBean);
					response.sendRedirect(nextJSP);
					return;
				}

				if (id == null) // tao moi
				{
					if (!dmhBean.createDmh()) {
						dmhBean.createRs();
						session.setAttribute("dmhBean", dmhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocNew_Giay.jsp";
						response.sendRedirect(nextJSP);
						return;
					} else {
						IErpDonmuahangList_Giay obj = new ErpDonmuahangList_Giay();
						obj.setCongtyId((String) session
								.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.setLoai(loai);
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocList.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				} else {
					if (!dmhBean.updateDmh()) {
						dmhBean.createRs();
						session.setAttribute("dmhBean", dmhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocUpdate_Giay.jsp";
						response.sendRedirect(nextJSP);
						return;
					} else {
						IErpDonmuahangList_Giay obj = new ErpDonmuahangList_Giay();
						obj.setCongtyId((String) session
								.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.setLoai(loai);
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocList.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				}
			}  else {
				if (action.equals("changeSP")) // || action.equals("changeNCC")
												// )
				{
					if (nccId.trim().length() > 0) {
						// Nếu NCC có tài khoản ghi nhận công nợ là 331120 thì
						// mặc định là Nhập khẩu, còn lại là Nội địa
						dbutils db = new dbutils();
						String query = "select SOHIEUTAIKHOAN from ERP_TAIKHOANKT where PK_SEQ = ( select taikhoan_fk from ERP_NHACUNGCAP where PK_SEQ = '"
								+ nccId + "' )";
						ResultSet rs = db.get(query);

						if (rs != null) {
							String sohieu = "";
							try {
								if (rs.next()) {
									sohieu = rs.getString("SOHIEUTAIKHOAN");
								}
								rs.close();
							} catch (Exception e) {
								dmhBean.setKhoId("");
								System.out.println("Exception: "
										+ e.getMessage());
							}

							if (sohieu.equals("331120"))
								dmhBean.setNguonGocHH("NN");
							else
								dmhBean.setNguonGocHH("TN");

						}

						db.shutDown();

					}

					// Thay doi loai hang hoa, phai xoa het SP da chon
					dmhBean.setSpList(new ArrayList<ISanpham>());
				}

				System.out.println("___Loai SP: " + dmhBean.getLoaispId());
				if (nccId.trim().length() > 0
						&& dmhBean.getLoaispId().trim().length() > 0) {
					if (dmhBean.getLoaispId().equals("100009")) {
						String[] ncc_arr = nccId.split(" - ");

						dbutils db = new dbutils();

						String query = "select LOAINHACUNGCAP_FK, KhoNL_Nhan_GC from ERP_NHACUNGCAP where PK_SEQ = '"
								+ ncc_arr[0].trim() + "'";

						System.out.println("Check NCC: " + query);
						ResultSet rs = db.get(query);
						if (rs != null) {
							String khoIds = "";
							String loainccId = "";
							try {
								if (rs.next()) {
									loainccId = rs
											.getString("LOAINHACUNGCAP_FK");
									if (loainccId.equals("100003")) {
										khoIds = rs.getString("KhoNL_Nhan_GC") == null ? ""
												: rs.getString("KhoNL_Nhan_GC");
									}

									if (khoIds.trim().length() <= 0) {
										dmhBean.setKhoId("");
										dmhBean.setKhoList(new ArrayList<IKho>());
										dmhBean.setMsg("Nhà cung cấp chưa thiết lập kho nguyên liệu gia công");
									} else {
										dmhBean.setKhoId(khoIds);
									}
								}
								rs.close();
							} catch (Exception e) {
								dmhBean.setKhoId("");
								System.out.println("Exception: "
										+ e.getMessage());
							}

						}

						db.shutDown();
					} else {
						dmhBean.setKhoId("");
					}
				}

				dmhBean.createRs();

				if (action.equals("changeNCC")) {
					if (nccId.trim().length() > 0) {
						dbutils db = new dbutils();
						String query = "select isnull(thoihanno, 0) as thoihanno, isnull(quanlycongno, 0) as quanlycongno from ERP_NhaCungCap where pk_seq = '"
								+ nccId + "' ";
						ResultSet rsNcc = db.get(query);
						try {
							if (rsNcc.next()) {
								dmhBean.setQuanlycongno(rsNcc
										.getString("quanlycongno"));
								if (dmhBean.getNguonGocHH().equals("TN")) {
									dmhBean.setHinhThucTT(rsNcc
											.getString("thoihanno")
											+ " ngày kể từ ngày nhận hóa đơn");
								}
							}
							rsNcc.close();
							db.shutDown();
						} catch (SQLException e) {
							db.shutDown();
						}
					}
				}

				String nextJSP;
				if (id == null) {
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocNew_Giay.jsp";
				} else {
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangtrongnuocUpdate_Giay.jsp";
				}
				session.setAttribute("dmhBean", dmhBean);
				response.sendRedirect(nextJSP);
			}
		}

	}

	private void Create_PO_PDF(HttpServletResponse response,
			HttpServletRequest request) {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition",
				" inline; filename=DonDatHang.pdf");

		float CONVERT = 28.346457f;
		float PAGE_LEFT = 2.0f * CONVERT, PAGE_RIGHT = 1.5f * CONVERT, PAGE_TOP = 0.5f * CONVERT, PAGE_BOTTOM = 0.0f * CONVERT; // cm
		Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT,
				PAGE_TOP, PAGE_BOTTOM);
		ServletOutputStream outstream;
		try {
			outstream = response.getOutputStream();

			String id = request.getParameter("dmhId");

			String nguongocHH = "TN";
			dbutils db = new dbutils();
			ResultSet rsCheck = db
					.get("select ISNULL(nguongocHH, 'TN') as nguongoc from ERP_MUAHANG where PK_SEQ = '"
							+ id + "'");
			if (rsCheck != null) {
				try {
					if (rsCheck.next()) {
						nguongocHH = rsCheck.getString("nguongoc");
					}
					rsCheck.close();
				} catch (Exception e) {
				}
			}

			if (nguongocHH.equals("TN")) {
				this.CreatePO_VietNam(document, outstream, response, request,
						db);
			} else {
				this.CreatePO_English(document, outstream, response, request,
						db);
			}

			db.update("Update ERP_MuaHang set DaInPdf = 1 where pk_seq = '"
					+ id + "'");
			db.shutDown();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("___Exception PO Print: " + e.getMessage());
		}
	}

	private void CreatePO_VietNam(Document document,
			ServletOutputStream outstream, HttpServletResponse response,
			HttpServletRequest request, dbutils db) {
		HttpSession session = request.getSession();

		String id = request.getParameter("dmhId");
		String ctyId = (String) session.getAttribute("congtyId");

		try {
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter_2sole = new DecimalFormat("#,###,###.##");

			PdfWriter.getInstance(document, outstream);
			document.open();
			// document.setPageSize(new Rectangle(210.0f, 297.0f));

			float CONVERT = 28.346457f; // = 1cm
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			Font font13bold = new Font(bf, 13, Font.BOLD);
			Font font14bold = new Font(bf, 14, Font.BOLD);
			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font16bold = new Font(bf, 16, Font.BOLD);
			Font font8 = new Font(bf, 8, Font.NORMAL);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font13 = new Font(bf, 13, Font.NORMAL);
			Font font14 = new Font(bf, 14, Font.NORMAL);
			Font font11italic = new Font(bf, 11, Font.ITALIC);
			Font font11boldItalic = new Font(bf, 11, Font.BOLDITALIC);
			Font font12boldItalic = new Font(bf, 12, Font.BOLDITALIC);
			Font font11underline = new Font(bf, 11, Font.UNDERLINE);

			/********************** INFO CONGTY *******************************/

			PdfPTable tbHeader = new PdfPTable(1);
			tbHeader.setWidthPercentage(100);
			tbHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] crHeader = { 400f };
			tbHeader.setWidths(crHeader);
			tbHeader.getDefaultCell().setBorder(0);

			Image hinhanh = Image.getInstance(getServletContext()
					.getInitParameter("path") + "/images/logoTraphacoERP.png");
			hinhanh.scalePercent(70);
			hinhanh.setAlignment(Element.ALIGN_CENTER);

			PdfPCell _celllogo = new PdfPCell(hinhanh);
			_celllogo.setBorder(0);
			_celllogo.setHorizontalAlignment(Element.ALIGN_RIGHT);
			// _cell.setPadding(10.0f);
			tbHeader.addCell(_celllogo);

			PdfPCell cell = null;
			/*
			 * PdfPCell _cell = new PdfPCell(new Paragraph("BM-CƯ-003/02/01",
			 * font12)); _cell.setBorder(0); _cell.setColspan(3);
			 * _cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * _cell.setPadding(10.0f); tbHeader.addCell(_cell);
			 * 
			 * PdfPCell cell = null;
			 * 
			 * try { String[] imageSources = {
			 * "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png"
			 * ,
			 * "C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png"
			 * ,
			 * "D:\\project\\SalesUp_Traning\\TraphacoERP\\WebContent\\pages\\images\\logoNewToYo.png"
			 * }; Image logoImage = null;
			 * 
			 * for(int i = 0; i < imageSources.length; i++) { try { if(logoImage
			 * == null) { logoImage = Image.getInstance(imageSources[i]);
			 * System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] imgSrc = "
			 * + imageSources[i]); //break; } } catch (Exception e) { } }
			 * 
			 * //Image pic = Image.getInstance(
			 * "C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png"
			 * ); if(logoImage != null) { System.out.println(
			 * "______Load Images Thanh Cong...."); logoImage.setBorder(0);
			 * logoImage.setAlignment(Element.ALIGN_CENTER);
			 * tbHeader.addCell(logoImage); } else { cell = new PdfPCell(new
			 * Paragraph(" ", font11)); cell.setBorder(0);
			 * tbHeader.addCell(cell); } } catch (Exception e) {
			 * System.out.println("Exception load Images: " + e.getMessage());
			 * cell = new PdfPCell(new Paragraph(" ", font11));
			 * cell.setBorder(0); tbHeader.addCell(cell);
			 * 
			 * }
			 * 
			 * 
			 * String query =
			 * " select ma, isnull(ten, 'NA') as ten, isnull(diachi, 'NA') as diachi, isnull(masothue, 'NA') as masothue, "
			 * +
			 * " isnull(dienthoai, 'NA') as dienthoai, isnull(fax, 'NA') as fax "
			 * + " from erp_congty where pk_seq = '" + ctyId + "'";
			 * 
			 * ResultSet infoCty = db.get(query); if(infoCty.next()) { PdfPTable
			 * tbSubHeader = new PdfPTable(1);
			 * tbSubHeader.setWidthPercentage(100);
			 * tbSubHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tbSubHeader.getDefaultCell().setBorder(0); float[] crSubHeader =
			 * {200.0f}; tbSubHeader.setWidths(crSubHeader);
			 * 
			 * cell = new PdfPCell(new
			 * Paragraph(infoCty.getString("ten").toUpperCase(), font12));
			 * cell.setBorder(0); tbSubHeader.addCell(cell);
			 * 
			 * cell = new PdfPCell(new Paragraph(infoCty.getString("diachi"),
			 * font10)); cell.setBorder(0); tbSubHeader.addCell(cell);
			 * 
			 * cell = new PdfPCell(new Paragraph("MST: " +
			 * infoCty.getString("masothue"), font10)); cell.setBorder(0);
			 * tbSubHeader.addCell(cell);
			 * 
			 * tbHeader.addCell(tbSubHeader); PdfPTable tbSubHeader2 = new
			 * PdfPTable(1); tbSubHeader2.setWidthPercentage(100);
			 * tbSubHeader2.setHorizontalAlignment(Element.ALIGN_LEFT); float[]
			 * crSubHeader2 = {100.0f}; tbSubHeader2.setWidths(crSubHeader2);
			 * 
			 * 
			 * cell = new PdfPCell(new Paragraph("[T]: " +
			 * infoCty.getString("dienthoai"), font10)); cell.setBorderWidth(0);
			 * cell.setBorderWidthLeft(1); tbSubHeader2.addCell(cell);
			 * 
			 * cell = new PdfPCell(new Paragraph("[F]: " +
			 * infoCty.getString("fax"), font10)); cell.setBorderWidth(0);
			 * cell.setBorderWidthLeft(1); tbSubHeader2.addCell(cell);
			 * 
			 * cell = new PdfPCell(new Paragraph("[E]: purchasing@newtoyovn.com"
			 * , font10)); cell.setBorderWidth(0); cell.setBorderWidthLeft(1);
			 * tbSubHeader2.addCell(cell);
			 * 
			 * cell = new PdfPCell(new Paragraph("[W]: www.newtoyovn.com",
			 * font10)); cell.setBorderWidth(0); cell.setBorderWidthLeft(1);
			 * tbSubHeader2.addCell(cell);
			 * 
			 * tbHeader.addCell(tbSubHeader2);
			 * 
			 * } infoCty.close();
			 */

			document.add(tbHeader);

			/**********************
			 * END INFO CONGTY
			 *******************************/

			/**********************
			 * INFO NHA CUNG CAP
			 *******************************/

			PdfPTable tbNCC = new PdfPTable(2);
			tbNCC.setWidthPercentage(100);
			tbNCC.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] crNcc = { 200.0f, 150.0f };
			tbNCC.setWidths(crNcc);
			tbNCC.getDefaultCell().setBorder(0);
			tbNCC.setSpacingBefore(10.0f);

			String query = " select "
					+ "	a.sopo as poId, a.ngaymua, isnull(a.dungsai, 0) as dungsai, a.NGUOITAO AS nguoilapId, ISNULL(d.TEN, ' ') AS nguoilapTen, ISNULL(A.VAT,0) AS VAT, tt.MA as tiente,  "
					+ "     isnull(b.ten, 'NA') as nccTen, isnull(b.diachi, 'NA') as diachi, isnull(b.dienthoai, 'NA') as dienthoai, isnull(b.fax, 'NA') as fax, isnull(b.ten_nguoilienhe, 'NA') as nguoilienhe, b.thoihanno, "
					+ "     isnull(a.sothamchieu, '') as sothamchieu, isnull(a.ghichu, '') as ghichu,"
					+ "     isnull((select top(1) ngaynhan from ERP_MUAHANG_SP where muahang_fk = a.pk_seq ), '') as ngaynhan,"
					+ "     isnull(a.loaihanghoa_fk, 0) as loaihanghoa_fk, "
					+ "	  c.ten as donvithuchien, isnull(a.httt, '') as httt, a.tiente_fk,"
					+ "     isnull(a.diadiemgiaohang, '') as diadiemgiaohang "
					+ " 		from erp_muahang a inner join ERP_NhaCungCap b on a.nhacungcap_fk = b.pk_seq "
					+ "     inner join ERP_DonViThucHien c on a.donvithuchien_fk = c.pk_seq  "
					+ "     left join NHANVIEN d on a.NGUOITAO = d.PK_SEQ left join ERP_TIENTE tt on a.TIENTE_FK = tt.PK_SEQ"
					+ " where a.pk_seq = '" + id + "'";

			System.out.println("___Init NCC: " + query);

			ResultSet infoNcc = db.get(query);
			String loaihanghoa_fk = "0";
			String dungsai = "0";
			String ghichu = "";
			String nguoilapId = "";
			String nguoilapTen = "";
			String tiente = "";
			String ngaynhan = "";
			String donvithuchien = "";
			String httt = "";
			String tiente_fk = "";
			String diadiemgiaohang = "";
			int vat = 0;
			if (infoNcc.next()) {
				nguoilapId = infoNcc.getString("nguoilapId");
				nguoilapTen = infoNcc.getString("nguoilapTen");
				vat = infoNcc.getInt("VAT");
				tiente = infoNcc.getString("tiente");
				ghichu = infoNcc.getString("ghichu");
				ngaynhan = infoNcc.getString("ngaynhan");
				loaihanghoa_fk = infoNcc.getString("loaihanghoa_fk");
				donvithuchien = infoNcc.getString("donvithuchien");
				httt = infoNcc.getString("httt");
				tiente_fk = infoNcc.getString("tiente_fk");
				diadiemgiaohang = infoNcc.getString("diadiemgiaohang");

				PdfPTable tbSubNcc = new PdfPTable(1);
				tbSubNcc.setWidthPercentage(100);
				tbSubNcc.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNcc.getDefaultCell().setBorder(1);
				float[] crSubNcc = { 200.0f };
				tbSubNcc.setWidths(crSubNcc);

				cell = new PdfPCell(new Paragraph("SUPPLIER", font11underline));
				cell.setBorderWidth(1);
				cell.setBorderWidthBottom(0);
				tbSubNcc.addCell(cell);

				dungsai = infoNcc.getString("dungsai");
				cell.setPaddingLeft(5.0f);
				cell = new PdfPCell(new Paragraph(infoNcc.getString("nccTen"),
						font13bold));
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				tbSubNcc.addCell(cell);

				cell = new PdfPCell(new Paragraph(infoNcc.getString("diachi"),
						font10));
				cell.setPaddingLeft(5.0f);
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				tbSubNcc.addCell(cell);

				cell = new PdfPCell(new Paragraph("Fax: "
						+ infoNcc.getString("fax") + "         Tel: "
						+ infoNcc.getString("dienthoai"), font10));
				cell.setPaddingLeft(5.0f);
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				tbSubNcc.addCell(cell);

				cell = new PdfPCell(new Paragraph("Người liên hệ: "
						+ infoNcc.getString("nguoilienhe"), font10));
				cell.setPaddingLeft(5.0f);
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				tbSubNcc.addCell(cell);

				tbNCC.addCell(tbSubNcc);

				PdfPTable tbSubNcc2 = new PdfPTable(1);
				tbSubNcc2.setWidthPercentage(100);
				tbSubNcc2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNcc2.getDefaultCell().setBorder(0);
				float[] crSubNcc2 = { 200.0f };
				tbSubNcc2.setWidths(crSubNcc2);

				cell = new PdfPCell(new Paragraph("ĐƠN ĐẶT HÀNG", font14bold));
				cell.setBorder(0);
				cell.setPadding(3.0f);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tbSubNcc2.addCell(cell);

				cell = new PdfPCell(new Paragraph("Số đơn hàng     : "
						+ infoNcc.getString("poId"), font13bold));
				cell.setBorder(0);
				tbSubNcc2.addCell(cell);

				cell = new PdfPCell(new Paragraph("Chiếu theo số   : "
						+ infoNcc.getString("sothamchieu"), font13bold));
				cell.setBorder(0);
				tbSubNcc2.addCell(cell);

				cell = new PdfPCell(new Paragraph("Ngày                 : "
						+ getVnDateTime(infoNcc.getString("ngaymua")),
						font13bold));
				cell.setBorder(0);
				tbSubNcc2.addCell(cell);
				cell = new PdfPCell(new Paragraph("Ngày giao         : "
						+ getVnDateTime(ngaynhan), font13bold));
				cell.setBorder(0);
				tbSubNcc2.addCell(cell);

				tbNCC.addCell(tbSubNcc2);

				document.add(tbNCC);

				/******************** INFO NCC **********************/

				PdfPTable tbNCCInfo = new PdfPTable(2);
				tbNCCInfo.setWidthPercentage(100);
				tbNCCInfo.setHorizontalAlignment(Element.ALIGN_LEFT);
				float[] crNccInfo = { 200.0f, 150.0f };
				tbNCCInfo.setWidths(crNccInfo);
				tbNCCInfo.getDefaultCell().setBorder(0);
				tbNCCInfo.setSpacingBefore(4.0f);
				tbNCCInfo.setSpacingAfter(4.0f);

				PdfPTable tbSubNccInfo = new PdfPTable(1);
				tbSubNccInfo.setWidthPercentage(100);
				tbSubNccInfo.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNccInfo.getDefaultCell().setBorder(0);
				float[] crSubNccInfo = { 200.0f };
				tbSubNccInfo.setWidths(crSubNccInfo);

				cell = new PdfPCell(new Paragraph("Hình thức thanh toán  : "
						+ httt, font11));
				cell.setPaddingLeft(9);
				cell.setBorder(0);
				tbSubNccInfo.addCell(cell);

				diadiemgiaohang = diadiemgiaohang.trim().length() <= 0 ? "Kho Công ty TraphacoERP"
						: diadiemgiaohang.trim();

				cell = new PdfPCell(new Paragraph("Địa điểm giao hàng    : "
						+ diadiemgiaohang, font11));
				cell.setBorder(0);
				cell.setPaddingLeft(9);
				cell.setColspan(2);
				tbSubNccInfo.addCell(cell);

				tbNCCInfo.addCell(tbSubNccInfo);

				PdfPTable tbSubNccInfo2 = new PdfPTable(1);
				tbSubNccInfo2.setWidthPercentage(100);
				tbSubNccInfo2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNccInfo2.getDefaultCell().setBorder(0);
				float[] crSubNccInfo2 = { 150.0f };
				tbSubNccInfo2.setWidths(crSubNccInfo2);

				cell = new PdfPCell(new Paragraph("Bộ phận            : "
						+ donvithuchien, font13bold));
				cell.setBorder(0);
				tbSubNccInfo2.addCell(cell);

				/*
				 * cell = new PdfPCell(new Paragraph("Ngày giao hàng: " +
				 * infoNcc.getString("ngaymua"), font11)); cell.setBorder(0);
				 * tbSubNccInfo2.addCell(cell);
				 */

				cell = new PdfPCell(new Paragraph(" ", font11));
				cell.setBorder(0);
				tbSubNccInfo2.addCell(cell);

				tbNCCInfo.addCell(tbSubNccInfo2);

				document.add(tbNCCInfo);
			}
			infoNcc.close();

			/******************** END INFO NCC ***********************/

			/******************** INFO SAN PHAM **********************/

			float[] crSanpham = { 1.2f * CONVERT, 9.0f * CONVERT,
					1.9f * CONVERT, 1.8f * CONVERT, 2.4f * CONVERT,
					2.8f * CONVERT };// , 2.2f * CONVERT};

			PdfPTable tbSanPham = new PdfPTable(crSanpham.length);
			tbSanPham.setWidthPercentage(100);
			tbSanPham.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbSanPham.setWidths(crSanpham);
			tbSanPham.getDefaultCell().setBorder(0);
			tbSanPham.setSpacingAfter(8.0f);

			String[] spTitles = { "STT", "Tên hàng hóa", "Đơn vị", "Số lượng",
					"Đơn giá", "Thành tiền\n(VNĐ)" };// ,
														// "Ngày
														// giao"
														// };

			for (int z = 0; z < spTitles.length; z++) {
				cell = new PdfPCell(new Paragraph(spTitles[z], font11bold));
				cell.setPadding(3.0f);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tbSanPham.addCell(cell);
			}

			double tongtien = 0;
			// San Pham
			query = " select isnull(a.tenhd, '') as tenhd, isnull(b.ten2, b.ten) as spTen, isnull(b.DAI, 0) AS DAI, ISNULL(B.RONG, 0) AS RONG, ISNULL(B.DINHLUONG,0) AS DINHLUONG, "
					+ "			isnull(B.DVDL_DAI, '') as DVDL_DAI, isnull(B.DVDL_RONG, '') as DVDL_RONG, isnull(B.DVDL_DINHLUONG, '') as DVDL_DINHLUONG, "
					+ "			ISNULL(a.donvi, isnull(c.donvi, 'NA')) as donvi, isnull(b.mau, '') as mau, isnull(b.QuyCach_NguonGoc, '') as nguongoc, a.SOLUONG, a.NGAYNHAN, a.DONGIA, 0 as type,  "
					+ " 			isnull(tscd.pk_seq,0) as tscdid, isnull(tscd.ma, '') as tscdMa, isnull(a.diengiai, tscd.ten) as tscdTen, isnull(nts.ma, 'NA') as nstNh,  "
					+ " 			isnull(ccdc.pk_seq,0) as ccdcid, isnull(ccdc.ma, '') as ccdcMa, isnull(a.diengiai, ccdc.DIENGIAI) as ccdcTen,  "
					+ " 			isnull(ncp.pk_seq,0) as ncpid,isnull(ncp.ten, '') as ncpMa, isnull(a.diengiai, ncp.diengiai) as ncpTen, isnull(ttcp.diengiai, 'NA') as ncpNh "
					+ " from ERP_MUAHANG_SP a left join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ    "
					+ " left join	erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq  "
					+ " left join erp_nhomtaisan nts on tscd.NhomTaiSan_fk = nts.pk_seq   "
					+ " left join	erp_congcudungcu ccdc on a.ccdc_fk = ccdc.pk_seq  "
					+ " left join erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq "
					+ " left join  erp_trungtamchiphi ttcp on ncp.ttchiphi_fk = ttcp.pk_seq  "
					+ " left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  "
					+ " where a.MUAHANG_FK = '" + id + "'";

			System.out.println("rsSOSP = " + query);

			ResultSet rsSOSP = db.get(query);
			int soSPCount = 0;
			double value;
			String sValue;
			String spTen = "";
			while (rsSOSP.next()) {
				// soSPCount = soSPCount +1;

				String qc = "";

				value = rsSOSP.getDouble("DINHLUONG");
				if (value != 0) {
					sValue = formatter_2sole.format(value);
					qc += sValue + rsSOSP.getString("DVDL_DINHLUONG");
				}

				value = rsSOSP.getDouble("RONG");
				if (value != 0) {
					if (qc.length() > 0) {
						qc += " x ";
					}
					sValue = formatter_2sole.format(value);
					qc += sValue + rsSOSP.getString("DVDL_RONG");
				}

				value = rsSOSP.getDouble("DAI");
				if (value != 0) {
					if (qc.length() > 0) {
						qc += " x ";
					}
					sValue = formatter_2sole.format(value);
					qc += sValue + rsSOSP.getString("DVDL_DAI");
				}

				if (rsSOSP.getString("tenhd").trim().length() > 0) {
					spTen = rsSOSP.getString("tenhd").trim();
				} else if (loaihanghoa_fk.equals("0")) {
					spTen = rsSOSP.getString("spTen");
				} else if (loaihanghoa_fk.equals("1")) {
					spTen = rsSOSP.getString("tscdTen").trim();
				} else if (loaihanghoa_fk.equals("2")) {
					spTen = rsSOSP.getString("ccdcTen").trim();
				} else if (loaihanghoa_fk.equals("3")) {
					spTen = rsSOSP.getString("ncpTen").trim();
				} else {
					spTen = rsSOSP.getString("spTen");
				}

				String chuoi = spTen;

				if (rsSOSP.getString("tenhd").trim().length() <= 0) {
					String mau = rsSOSP.getString("mau");
					if (mau == null
							|| mau.trim().toLowerCase().indexOf("không") >= 0)
						mau = "";
					else
						mau = mau.trim();

					String nguongoc = rsSOSP.getString("nguongoc");
					if (nguongoc == null
							|| nguongoc.trim().toLowerCase().equals("no")
							|| nguongoc.trim().toLowerCase().indexOf("không") >= 0)
						nguongoc = "";
					else
						nguongoc = nguongoc.trim();

					chuoi += (mau.length() > 0 ? " " + mau : "")
							+ (nguongoc.length() > 0 ? " (" + nguongoc + ")"
									: "");

					int bien = (chuoi.length() / 45)
							+ ((chuoi.trim().length() % 45) > 0 ? 1 : 0);
					soSPCount = soSPCount + bien;

				} else {
					int bien = (chuoi.length() / 45)
							+ ((chuoi.trim().length() % 45) > 0 ? 1 : 0);
					soSPCount = soSPCount + bien;

				}

				if (qc.length() > 0) {
					chuoi += " \n" + qc;
					int bien = (qc.length() / 45)
							+ ((qc.trim().length() % 45) > 0 ? 1 : 0);
					soSPCount = soSPCount + bien;
				}
			}

			query = " select a.pk_seq as sott,isnull(a.tenhd, '') as tenhd, isnull(b.ten2, b.ten) as spTen, isnull(b.DAI, 0) AS DAI, ISNULL(B.RONG, 0) AS RONG, ISNULL(B.DINHLUONG,0) AS DINHLUONG, "
					+ "			isnull(B.DVDL_DAI, '') as DVDL_DAI, isnull(B.DVDL_RONG, '') as DVDL_RONG, isnull(B.DVDL_DINHLUONG, '') as DVDL_DINHLUONG, "
					+ "			ISNULL(a.donvi, isnull(c.donvi, 'NA')) as donvi, isnull(b.mau, '') as mau, isnull(b.QuyCach_NguonGoc, '') as nguongoc, a.SOLUONG, a.NGAYNHAN, a.DONGIA, 0 as type,  "
					+ " 			isnull(tscd.pk_seq,0) as tscdid, isnull(tscd.ma, '') as tscdMa, isnull(a.diengiai, tscd.ten) as tscdTen, isnull(nts.ma, 'NA') as nstNh,  "
					+ " 			isnull(ccdc.pk_seq,0) as ccdcid, isnull(ccdc.ma, '') as ccdcMa, isnull(a.diengiai, ccdc.DIENGIAI) as ccdcTen,  "
					+ " 			isnull(ncp.pk_seq,0) as ncpid,isnull(ncp.ten, '') as ncpMa, isnull(a.diengiai, ncp.diengiai) as ncpTen, isnull(ttcp.diengiai, 'NA') as ncpNh "
					+ " from ERP_MUAHANG_SP a left join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ    "
					+ " left join	erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq  "
					+ " left join erp_nhomtaisan nts on tscd.NhomTaiSan_fk = nts.pk_seq   "
					+ " left join	erp_congcudungcu ccdc on a.ccdc_fk = ccdc.pk_seq  "
					+ " left join erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq "
					+ " left join  erp_trungtamchiphi ttcp on ncp.ttchiphi_fk = ttcp.pk_seq  "
					+ " left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  "
					+ " where a.MUAHANG_FK = '" + id + "'";
			// System.out.println("ghi chu : "+ghichu);
			if (ghichu.trim().length() > 0) {
				query += " union all ";
				query += " select 1000000000,' ', ' ', '0', '0', '0', '', '', '', '', '', '', '0', '', '0', 1 as type, 0,'','','',0,'','',0,'','','' ";

				String[] ghichuArr = ghichu.trim().split("__");

				// Tính số dòng thực tế ghi chú
				int _count = 0;
				for (int _i = 0; _i < ghichuArr.length; _i++) {
					if (ghichuArr[_i].trim().length() > 0) {

						int bien = (ghichuArr[_i].trim().length() / 45)
								+ ((ghichuArr[_i].trim().length() % 45) > 0 ? 1
										: 0);
						// _count +=
						// Math.ceil((double)ghichuArr[_i].trim().length()/35);
						_count = _count + bien;
					}
				}
				System.out.println(_count);

				while (soSPCount <= (13 - _count)) {
					query += " union all ";
					query += " select 2000000000,' ', '  ', '0', '0', '0', '', '', '', '', '', '', '0', '', '0', 1 as type, 0,'','','',0,'','',0,'','','' ";

					soSPCount++;
				}

				for (int i = 0; i < ghichuArr.length; i++) {
					if (ghichuArr[i].trim().length() > 0) {
						query += " union all ";
						query += " select 3000000000,' ', N'"
								+ ghichuArr[i].trim()
								+ "', '0', '0', '0', '', '', '', '', '', '', '0', '', '0', 1 as type, 0,'','','',0,'','',0,'','','' ";
					}
				}
				query += " order by type,sott asc ";

			} else {
				while (soSPCount <= 13) {
					query += " union all ";
					query += " select 4000000000,' ', '  ', '0', '0', '0', '', '', '', '', '', '', '0', '', '0', 1 as type, 0,'','','',0,'','',0,'','','' ";

					soSPCount++;
				}

				query += " order by type,sott asc ";

			}

			System.out.println("Get du lieu In : " + query);

			int soSP = 0;
			ResultSet infoSanPham = db.getScrol(query);

			if (infoSanPham != null) {
				infoSanPham.beforeFirst();
				while (infoSanPham.next()) {
					soSP++;
				}
				System.out.println("So San Pham = " + soSP);

				int stt = 0;
				infoSanPham.beforeFirst();
				while (infoSanPham.next()) {
					// Setup quy cach
					// Quy cach

					if (infoSanPham.getString("tenhd").trim().length() > 0) {
						spTen = infoSanPham.getString("tenhd").trim();
					} else if (loaihanghoa_fk.equals("0")) {
						spTen = infoSanPham.getString("spTen");
					} else if (loaihanghoa_fk.equals("1")) {
						spTen = infoSanPham.getString("tscdTen").trim();
					} else if (loaihanghoa_fk.equals("2")) {
						spTen = infoSanPham.getString("ccdcTen").trim();
					} else if (loaihanghoa_fk.equals("3")) {
						spTen = infoSanPham.getString("ncpTen").trim();
					} else {
						spTen = infoSanPham.getString("spTen");
					}

					if (infoSanPham.getString("type").equals("0")) {
						String qc = "";

						if (infoSanPham.getDouble("DINHLUONG") != 0) {
							qc += formatter_2sole.format(infoSanPham
									.getDouble("DINHLUONG"))
									+ infoSanPham.getString("DVDL_DINHLUONG");
						}
						if (infoSanPham.getDouble("RONG") != 0) {
							if (qc.length() > 0) {
								qc += " x ";
							}
							qc += formatter_2sole.format(infoSanPham
									.getDouble("RONG"))
									+ infoSanPham.getString("DVDL_RONG");
						}
						if (infoSanPham.getDouble("DAI") != 0) {
							if (qc.length() > 0) {
								qc += " x ";
							}
							qc += formatter_2sole.format(infoSanPham
									.getDouble("dai"))
									+ infoSanPham.getString("DVDL_DAI");
						}

						cell = new PdfPCell(new Paragraph(
								Integer.toString(stt + 1), font11));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						String mau = infoSanPham.getString("mau");
						if (mau == null
								|| mau.trim().toLowerCase().indexOf("không") >= 0)
							mau = "";
						else
							mau = mau.trim();
						String nguongoc = infoSanPham.getString("nguongoc");
						if (nguongoc == null
								|| nguongoc.trim().toLowerCase().equals("no")
								|| nguongoc.trim().toLowerCase()
										.indexOf("không") >= 0)
							nguongoc = "";
						else
							nguongoc = nguongoc.trim();

						cell = new PdfPCell(
								new Paragraph(
										infoSanPham.getString("tenhd").trim()
												.length() > 0 ? (spTen + " \n" + qc)
												: (spTen
														+ (mau.length() > 0 ? " "
																+ mau
																: "")
														+ (nguongoc.length() > 0 ? " ("
																+ nguongoc
																+ ")"
																: "") + " \n" + qc),
										font11));

						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(
								infoSanPham.getString("donvi"), font11));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(
								formatter_2sole.format(infoSanPham
										.getDouble("SoLuong")), font11));
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(
								formatter_2sole.format(infoSanPham
										.getDouble("DonGia")), font11));
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(
								formatter_2sole.format(infoSanPham
										.getDouble("SoLuong")
										* infoSanPham.getDouble("DonGia")),
								font11));
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);

							cell.setBorderWidthRight(0.5f);
						}
						tbSanPham.addCell(cell);

						tongtien += infoSanPham.getDouble("SoLuong")
								* infoSanPham.getDouble("DonGia");
					} else {
						spTen = infoSanPham.getString("spTen");
						cell = new PdfPCell(new Paragraph("", font11));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(spTen, font11));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph("", font11));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(" ", font11));
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(" ", font11));
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(" ", font11));
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
							cell.setBorderWidthRight(0.5f);
						}
						tbSanPham.addCell(cell);

					}
					stt++;
				}
				infoSanPham.close();

			} else {
				System.out.println(" rs is null ( query = " + query + ")");
			}

			cell = new PdfPCell();
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

			cell.addElement(new Paragraph("Ghi chú: ", font8));
			cell.addElement(new Paragraph(
					"1.Giao hàng đúng thời gian yêu cầu, nếu có thay đổi phải báo trước ... ngày",
					font8));
			cell.addElement(new Paragraph(
					"2.Chất lượng hàng hóa phải đảm bảo đúng các đặc tính kỹ thuật như đã thỏa thuận hoặc theo mẫu (nếu có)",
					font8));
			cell.addElement(new Paragraph("3.Số lượng dao động " + dungsai
					+ "% trên từng mặt hàng", font8));
			cell.addElement(new Paragraph(
					"4.Thể hiện số đơn đặt hàng trên hóa đơn", font8));

			tbSanPham.addCell(cell);

			cell = new PdfPCell(new Paragraph("Cộng tiền hàng",
					font12boldItalic));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);

			cell = new PdfPCell(new Paragraph(formatter_2sole.format(tongtien),
					font12boldItalic));
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbSanPham.addCell(cell);

			if (tiente_fk.equals("100000")) {
				tongtien = Math.round(tongtien);
			}

			double tienVAT = 0;
			if (vat > 0) {
				if (tiente_fk.equals("100000")) {
					tienVAT = Math.round(tongtien * vat / 100);
				} else {
					tienVAT = tongtien * vat / 100;
				}

				cell = new PdfPCell(new Paragraph(
						"Tiền thuế GTGT " + vat + "%", font12boldItalic));
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tbSanPham.addCell(cell);

				cell = new PdfPCell(new Paragraph(
						formatter_2sole.format(tienVAT), font12boldItalic));
				cell.setColspan(6);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tbSanPham.addCell(cell);
			}

			cell = new PdfPCell(new Paragraph("Tổng cộng", font12boldItalic));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);

			cell = new PdfPCell(new Paragraph(formatter_2sole.format(tongtien
					+ tienVAT), font12boldItalic));
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbSanPham.addCell(cell);

			document.add(tbSanPham);

			/******************** END INFO NCC ***********************/

			int numcolumns = 5;
			if (nguoilapId.equals("100320")) {
				numcolumns = 4;
			}

			// Table Footer
			PdfPTable tableFooter = new PdfPTable(numcolumns);

			float[] footerWidths = new float[numcolumns];
			float width = 19.0f * CONVERT / numcolumns;
			for (int i = 0; i < footerWidths.length; i++) {
				footerWidths[i] = width;
			}

			// float[] footerWidths
			tableFooter.setWidthPercentage(100);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.getDefaultCell().setBorder(0);
			tbSanPham.setSpacingBefore(5.0f);
			tableFooter.setWidths(footerWidths);

			PdfPCell cell11 = new PdfPCell(new Paragraph(
					"Xác nhận nhà cung cấp", font11boldItalic));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);

			String phong = loaihanghoa_fk.equals("0") ? "Phòng cung ứng"
					: "Phòng " + donvithuchien;

			PdfPCell cell12 = new PdfPCell(new Paragraph(phong,
					font11boldItalic));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			if (nguoilapId.equals("100320")) {
				cell12.setColspan(1);
			} else {
				cell12.setColspan(2);
			}

			PdfPCell cell13 = new PdfPCell(new Paragraph("Phòng kế toán",
					font11boldItalic));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cell14 = new PdfPCell(new Paragraph("Ban tổng giám đốc",
					font11boldItalic));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell11.setBorderWidth(0);
			cell11.setBorderWidthLeft(1);
			cell11.setBorderWidthRight(1);
			cell11.setBorderWidthTop(1);
			cell11.setBorderWidthBottom(1);

			cell12.setBorderWidth(1);
			cell12.setBorderWidthLeft(0);

			cell13.setBorderWidth(1);
			cell13.setBorderWidthLeft(0);

			cell14.setBorderWidth(1);
			cell14.setBorderWidthLeft(0);

			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);

			cell11 = new PdfPCell(new Paragraph("", font11));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell12 = new PdfPCell(new Paragraph("Người lập", font12));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);

			phong = loaihanghoa_fk.equals("0") ? "Giám đốc cung ứng"
					: "Trưởng bộ phận";

			cell13 = new PdfPCell(new Paragraph(phong, font12));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell14 = new PdfPCell(new Paragraph(" ", font12));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cell15 = new PdfPCell(new Paragraph(" ", font12));
			cell15.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);

			cell11.setBorderWidthLeft(1);
			cell11.setBorderWidthRight(1);

			cell12.setBorderWidthRight(1);
			cell12.setBorderWidthBottom(1);

			cell13.setBorderWidthRight(1);
			cell13.setBorderWidthBottom(1);

			cell14.setBorderWidthRight(1);
			cell15.setBorderWidthRight(1);

			tableFooter.addCell(cell11);
			if (!nguoilapId.equals("100320")) {
				tableFooter.addCell(cell12);
			}
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);

			String[] footerStr = null;

			phong = loaihanghoa_fk.equals("0") ? "Nguyễn Kiêm Đức" : "";

			if (nguoilapId.equals("100320")) {
				footerStr = new String[] { " ", phong, "Nguyễn Hồng Nam",
						"Nguyễn Văn Bình" };
			} else {
				footerStr = new String[] { " ", nguoilapTen, phong,
						"Nguyễn Hồng Nam", "Nguyễn Văn Bình" };
			}
			int numFooterRows = 7;
			if (httt.length() > 32) {
				numFooterRows = 6;
			}
			// Them khoang trang
			for (int i = 0; i <= numFooterRows; i++) {

				for (int j = 0; j < numcolumns; j++) {
					PdfPCell cellFooter;

					if (i == numFooterRows) {
						cellFooter = new PdfPCell(new Paragraph(footerStr[j],
								font10));
					} else if (i == 1 && j == numcolumns - 1) {
						String text = "Chưa Duyệt";
						query = " SELECT CASE WHEN SUM(QUYETDINH) > 0 "
								+ "		THEN  ( "
								+ "			SELECT COUNT(TRANGTHAI) - SUM(TRANGTHAI) "
								+ "			FROM ERP_DUYETMUAHANG  "
								+ "			WHERE MUAHANG_FK = "
								+ id
								+ " AND QUYETDINH = 1 "
								+ "		) 	"
								+ "		ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	END AS DUYET "
								+ " FROM ERP_DUYETMUAHANG "
								+ " WHERE MUAHANG_FK = " + id;
						System.out
								.println("[ErpDonmuahangUpdate_GiaySvl.create] query = "
										+ query);
						ResultSet rs = db.get(query);
						try {
							rs.next();
							if (rs.getInt("DUYET") != 0) {
								text = "";// "CHƯA DUYỆT";
							} else {
								text = "";
							}
							rs.close();
						} catch (Exception e) {
						}

						cellFooter = new PdfPCell(new Paragraph(text, font10));
					} else {
						cellFooter = new PdfPCell(new Paragraph(" ", font10));
					}
					cellFooter.setHorizontalAlignment(Element.ALIGN_CENTER);

					// cellFooter.setColspan(5);
					cellFooter.setBorder(0);

					if (j == 0) {
						cellFooter.setBorderWidthLeft(1);
					}

					if (i == numFooterRows) {
						cellFooter.setBorderWidthBottom(1);
					}

					cellFooter.setBorderWidthRight(1);

					tableFooter.addCell(cellFooter);
				}
			}

			document.add(tableFooter);

			/*
			 * //Add Pragrp PdfPTable tbLast = new PdfPTable(1);
			 * tbLast.setWidthPercentage(95);
			 * tbLast.setHorizontalAlignment(Element.ALIGN_CENTER); float[]
			 * crLast = {600.0f}; tbLast.setWidths(crLast);
			 * tbLast.getDefaultCell().setBorder(0);
			 * 
			 * PdfPCell cellLast = new PdfPCell(new Paragraph(
			 * "Vui lòng ký xác nhận và phúc đáp cho phòng Cung Ứng qua số fax 3729 1767 hoặc điện thoại 3729 1786 (số nội bộ 131,141) trong vòng 1 ngày kể từ ngày nhận đơn hàng"
			 * , new Font(bf, 11, Font.NORMAL))); cellLast.setBorder(0);
			 * tbLast.addCell(cellLast);
			 * 
			 * //tbLast.addCell(
			 * "Vui lòng ký xác nhận và phúc đáp cho phòng Cung Ứng qua số fax 3729 1767 hoặc điện thoại 3729 1786 (số nội bộ 131,141) trong vòng 1 ngày kể từ ngày nhận đơn hàng"
			 * );
			 * 
			 * document.add(tbLast);
			 */

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception In PDF: " + e.getMessage());
		}

	}

	private void CreatePO_English(Document document,
			ServletOutputStream outstream, HttpServletResponse response,
			HttpServletRequest request, dbutils db) {
		HttpSession session = request.getSession();

		String id = request.getParameter("dmhId");
		String ctyId = (String) session.getAttribute("congtyId");

		try {
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter2 = new DecimalFormat("#,###,###.##");
			NumberFormat formatter2_sole = new DecimalFormat("#,###,###.######");
			// NumberFormat formatter3_sole = new
			// DecimalFormat("#,###,###.000");

			PdfWriter.getInstance(document, outstream);
			document.open();
			// document.setPageSize(new Rectangle(210.0f, 297.0f));

			float CONVERT = 28.346457f; // = 1cm
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			Font font13bold = new Font(bf, 13, Font.BOLD);
			Font font14bold = new Font(bf, 14, Font.BOLD);
			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font16bold = new Font(bf, 16, Font.BOLD);
			Font font8 = new Font(bf, 8, Font.NORMAL);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font13 = new Font(bf, 13, Font.NORMAL);
			Font font14 = new Font(bf, 14, Font.NORMAL);
			Font font11italic = new Font(bf, 11, Font.ITALIC);
			Font font11boldItalic = new Font(bf, 11, Font.BOLDITALIC);
			Font font12boldItalic = new Font(bf, 12, Font.BOLDITALIC);
			Font font11underline = new Font(bf, 11, Font.UNDERLINE);

			/********************** INFO CONGTY *******************************/

			PdfPTable tbHeader = new PdfPTable(3);
			tbHeader.setWidthPercentage(100);
			tbHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] crHeader = { 40.0f, 210.0f, 100.0f };
			tbHeader.setWidths(crHeader);
			tbHeader.getDefaultCell().setBorder(0);

			PdfPCell _cell = new PdfPCell(new Paragraph("BM-CƯ-004/02/01",
					font12));
			_cell.setBorder(0);
			_cell.setColspan(3);
			_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			_cell.setPadding(10.0f);
			tbHeader.addCell(_cell);

			PdfPCell cell = null;

			try {
				String[] imageSources = {
						"C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png",
						"C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png",
						"D:\\project\\SalesUp_Traning\\TraphacoERP\\WebContent\\pages\\images\\logoNewToYo.png" };
				Image logoImage = null;

				for (int i = 0; i < imageSources.length; i++) {
					try {
						if (logoImage == null) {
							logoImage = Image.getInstance(imageSources[i]);
							System.out
									.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] imgSrc = "
											+ imageSources[i]);
							// break;
						}
					} catch (Exception e) {
					}
				}

				// Image pic = Image.getInstance("C:\\Program Files
				// (x86)\\Apache Software Foundation\\Tomcat
				// 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png");
				if (logoImage != null) {
					System.out.println("______Load Images Thanh Cong....");
					logoImage.setBorder(0);
					logoImage.setAlignment(Element.ALIGN_CENTER);
					tbHeader.addCell(logoImage);
				} else {
					cell = new PdfPCell(new Paragraph(" ", font11));
					cell.setBorder(0);
					tbHeader.addCell(cell);
				}
			} catch (Exception e) {
				System.out.println("Exception load Images: " + e.getMessage());
				cell = new PdfPCell(new Paragraph(" ", font11));
				cell.setBorder(0);
				tbHeader.addCell(cell);

			}

			String query = "select ma, N'NEW TOYO (VIETNAM) ALUMINIUM PAPER PACKAGING CO., LTD' as ten, N'Lot 15, 17, 19 and 21, The Industrial Zone of Linh Trung Ex-Processing Zone 2' as diachi, "
					+ "N'Thu Duc District, HCMC, Vietnam' as masothue, "
					+ "isnull(dienthoai, 'NA') as dienthoai, isnull(fax, 'NA') as fax "
					+ "from erp_congty where pk_seq = '" + ctyId + "'";
			/*
			 * String query =
			 * "select ma, N' NEW TOYO (VIETNAM) ALUMINIUM PAPER PACKAGING CO., LTD' as ten, diachitienganh as diachi, "
			 * + "N' Thu Duc District, HCMC, Vietnam' as masothue, " +
			 * "isnull(dienthoai, 'NA') as dienthoai, isnull(fax, 'NA') as fax "
			 * + "from erp_congty where pk_seq = '" + ctyId + "'";
			 */

			ResultSet infoCty = db.get(query);

			if (infoCty.next()) {
				PdfPTable tbSubHeader = new PdfPTable(1);
				tbSubHeader.setWidthPercentage(100);
				tbSubHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubHeader.getDefaultCell().setBorder(0);
				float[] crSubHeader = { 210.0f };
				tbSubHeader.setWidths(crSubHeader);

				cell = new PdfPCell(new Paragraph(infoCty.getString("ten")
						.toUpperCase(), font9));
				cell.setBorder(0);
				tbSubHeader.addCell(cell);

				cell = new PdfPCell(new Paragraph(infoCty.getString("diachi"),
						font9));
				cell.setBorder(0);
				tbSubHeader.addCell(cell);

				cell = new PdfPCell(new Paragraph(
						infoCty.getString("masothue"), font9));
				cell.setBorder(0);
				tbSubHeader.addCell(cell);

				tbHeader.addCell(tbSubHeader);

				PdfPTable tbSubHeader2 = new PdfPTable(1);
				tbSubHeader2.setWidthPercentage(100);
				tbSubHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
				float[] crSubHeader2 = { 100.0f };
				tbSubHeader2.setWidths(crSubHeader2);

				cell = new PdfPCell(new Paragraph("[T]: "
						+ infoCty.getString("dienthoai"), font10bold));
				cell.setBorderWidth(0);
				cell.setBorderWidthLeft(1);
				tbSubHeader2.addCell(cell);

				cell = new PdfPCell(new Paragraph("[F]: "
						+ infoCty.getString("fax"), font10bold));
				cell.setBorderWidth(0);
				cell.setBorderWidthLeft(1);
				tbSubHeader2.addCell(cell);

				cell = new PdfPCell(new Paragraph(
						"[E]: purchasing@newtoyovn.com", font10bold));
				cell.setBorderWidth(0);
				cell.setBorderWidthLeft(1);
				tbSubHeader2.addCell(cell);

				cell = new PdfPCell(new Paragraph("[W]: www.newtoyovn.com",
						font10bold));
				cell.setBorderWidth(0);
				cell.setBorderWidthLeft(1);
				tbSubHeader2.addCell(cell);

				tbHeader.addCell(tbSubHeader2);

			}
			infoCty.close();

			document.add(tbHeader);

			/**********************
			 * END INFO CONGTY
			 *******************************/

			/**********************
			 * INFO NHA CUNG CAP
			 *******************************/

			PdfPTable tbNCC = new PdfPTable(2);
			tbNCC.setWidthPercentage(100);
			tbNCC.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] crNcc = { 200.0f, 150.0f };
			tbNCC.setWidths(crNcc);
			tbNCC.getDefaultCell().setBorder(0);
			tbNCC.setSpacingBefore(10.0f);

			query = " select a.sopo as poId, "
					+ " a.ngaymua, isnull(a.dungsai, 0) as dungsai, tt.MA as tiente, a.NGUOITAO AS nguoilapId, ISNULL(d.TEN, ' ') AS nguoilapTen, isnull(a.etd, '') as etd, isnull(a.eta, '') as eta, "
					+ " isnull(b.ten, 'NA') as nccTen, isnull(b.diachi, 'NA') as diachi, isnull(b.dienthoai, 'NA') as dienthoai, "
					+ "	isnull(b.fax, 'NA') as fax, isnull(b.ten_nguoilienhe, 'NA') as nguoilienhe, isnull(b.hanmucno, '') as hanmucno, isnull(b.ThoiHanNo, 0) as thoihanno, "
					+ "   isnull(a.sothamchieu, '') as sothamchieu, isnull(b.loaigiamua, '') as loaigiamua, isnull(ghichu, '') as ghichu, isnull(a.HTTT,'') as HTTT,"
					+ "   isnull(a.diadiemgiaohang, '') as diadiemgiaohang   "
					+ " from erp_muahang a inner join ERP_NhaCungCap b on a.nhacungcap_fk = b.pk_seq  "
					+ " inner join ERP_DonViThucHien c on a.donvithuchien_fk = c.pk_seq "
					+ " left join NHANVIEN d on a.NGUOITAO = d.PK_SEQ  left join ERP_TIENTE tt on a.TIENTE_FK = tt.PK_SEQ "
					+ " where a.pk_seq = '" + id + "'";

			System.out.println("___Init NCC: " + query);

			ResultSet infoNcc = db.get(query);
			String dungsai = "0";
			String tiente = "";
			String nguoilapId = "";
			String nguoilapTen = "";
			String sothamchieu = "";
			String loaigiamua = "";
			String thoihanno = "";
			String ghichu = "";
			String diadiemgiaohang = "";
			String etd = "", eta = "";

			if (infoNcc.next()) {
				nguoilapId = infoNcc.getString("nguoilapId");
				nguoilapTen = infoNcc.getString("nguoilapTen");
				tiente = infoNcc.getString("tiente");
				loaigiamua = infoNcc.getString("loaigiamua");
				ghichu = infoNcc.getString("ghichu");
				thoihanno = infoNcc.getString("thoihanno");
				etd = infoNcc.getString("etd");
				eta = infoNcc.getString("eta");
				diadiemgiaohang = infoNcc.getString("diadiemgiaohang").trim();

				PdfPTable tbSubNcc = new PdfPTable(1);
				tbSubNcc.setWidthPercentage(100);
				tbSubNcc.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNcc.getDefaultCell().setBorder(1);
				float[] crSubNcc = { 200.0f };
				tbSubNcc.setWidths(crSubNcc);

				cell = new PdfPCell(new Paragraph("SUPPLIER", font11underline));
				cell.setBorderWidth(1);
				cell.setBorderWidthBottom(0);
				tbSubNcc.addCell(cell);

				dungsai = infoNcc.getString("dungsai");
				cell = new PdfPCell(new Paragraph(infoNcc.getString("nccTen"),
						font13bold));
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setPaddingLeft(5.0f);
				tbSubNcc.addCell(cell);

				cell = new PdfPCell(new Paragraph(infoNcc.getString("diachi"),
						font10));
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setPaddingLeft(5.0f);
				tbSubNcc.addCell(cell);

				cell = new PdfPCell(new Paragraph("Fax: "
						+ infoNcc.getString("fax") + "         Tel: "
						+ infoNcc.getString("dienthoai"), font10));
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setPaddingLeft(5.0f);
				tbSubNcc.addCell(cell);

				cell = new PdfPCell(new Paragraph("Attn: "
						+ infoNcc.getString("nguoilienhe"), font10));
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				cell.setPaddingLeft(5.0f);
				tbSubNcc.addCell(cell);

				tbNCC.addCell(tbSubNcc);

				PdfPTable tbSubNcc2 = new PdfPTable(1);
				tbSubNcc2.setWidthPercentage(100);
				tbSubNcc2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNcc2.getDefaultCell().setBorder(0);
				float[] crSubNcc2 = { 200.0f };
				tbSubNcc2.setWidths(crSubNcc2);

				cell = new PdfPCell(new Paragraph("PURCHASE ORDER", font13bold));
				cell.setBorder(0);
				cell.setPadding(3.0f);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tbSubNcc2.addCell(cell);

				/**** NHA CUNG CAP *****/
				PdfPTable tbSubNcc3 = new PdfPTable(2);
				tbSubNcc3.setWidthPercentage(100);
				tbSubNcc3.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNcc3.getDefaultCell().setBorder(0);
				float[] crSubNcc3 = { 100.0f, 100.0f };
				tbSubNcc3.setWidths(crSubNcc3);

				cell = new PdfPCell(new Paragraph("P/O No.             : ",
						font11));
				cell.setBorder(0);
				tbSubNcc3.addCell(cell);

				cell = new PdfPCell(new Paragraph(infoNcc.getString("poId"),
						font13bold));
				cell.setBorder(0);
				tbSubNcc3.addCell(cell);

				cell = new PdfPCell(
						new Paragraph("Based on Ref     : ", font11));
				cell.setBorder(0);
				tbSubNcc3.addCell(cell);

				cell = new PdfPCell(new Paragraph(
						infoNcc.getString("sothamchieu"), font13bold));
				cell.setBorder(0);
				tbSubNcc3.addCell(cell);

				cell = new PdfPCell(new Paragraph("Date                   : ",
						font11));
				cell.setBorder(0);
				tbSubNcc3.addCell(cell);

				cell = new PdfPCell(
						new Paragraph(
								getEnDateTime(infoNcc.getString("ngaymua")),
								font13bold));
				cell.setBorder(0);
				tbSubNcc3.addCell(cell);
				tbSubNcc2.addCell(tbSubNcc3);
				/**** END NHA CUNG CAP *****/

				tbNCC.addCell(tbSubNcc2);

				document.add(tbNCC);

				/******************** INFO NCC **********************/

				float[] crNccInfo = { 200.0f, 150.0f };
				PdfPTable tbNCCInfo = new PdfPTable(crNccInfo.length);
				tbNCCInfo.setWidthPercentage(100);
				tbNCCInfo.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbNCCInfo.setWidths(crNccInfo);
				tbNCCInfo.getDefaultCell().setBorder(0);
				tbNCCInfo.setSpacingBefore(8.0f);
				tbNCCInfo.setSpacingAfter(8.0f);

				float[] crSubNccInfo = { crNccInfo[0] };
				PdfPTable tbSubNccInfo = new PdfPTable(1);
				tbSubNccInfo.setWidthPercentage(100);
				tbSubNccInfo.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNccInfo.getDefaultCell().setBorder(0);
				tbSubNccInfo.setWidths(crSubNccInfo);

				// cell = new PdfPCell(new Paragraph(" PAYMENT TERMS : " + "TT "
				// + infoNcc.getString("thoihanno") + " days after B/L date",
				// font11));
				cell = new PdfPCell(new Paragraph("   PAYMENT TERMS   : "
						+ infoNcc.getString("HTTT"), font11));
				cell.setBorder(0);
				tbSubNccInfo.addCell(cell);

				cell = new PdfPCell(
						new Paragraph(
								"   SHIP TO                      : "
										+ (diadiemgiaohang.trim().length() > 0 ? diadiemgiaohang
												: "HoChiMinh City Port"),
								font11));
				cell.setBorder(0);
				tbSubNccInfo.addCell(cell);

				tbNCCInfo.addCell(tbSubNccInfo);

				PdfPTable tbSubNccInfo2 = new PdfPTable(2);
				tbSubNccInfo2.setWidthPercentage(100);
				tbSubNccInfo2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNccInfo2.getDefaultCell().setBorder(0);
				float[] crSubNccInfo2 = { 55.0f, 95.0f };
				tbSubNccInfo2.setWidths(crSubNccInfo2);

				cell = new PdfPCell(new Paragraph(" ETD ", font11));
				cell.setBorder(0);
				tbSubNccInfo2.addCell(cell);
				cell = new PdfPCell(new Paragraph(":        "
						+ getEnDateTime(etd) + "", font13bold));
				cell.setBorder(0);
				tbSubNccInfo2.addCell(cell);

				cell = new PdfPCell(new Paragraph(" ETA  ", font11));
				cell.setBorder(0);
				tbSubNccInfo2.addCell(cell);
				cell = new PdfPCell(new Paragraph(":        "
						+ getEnDateTime(eta) + "", font13bold));
				cell.setBorder(0);
				tbSubNccInfo2.addCell(cell);

				tbNCCInfo.addCell(tbSubNccInfo2);

				document.add(tbNCCInfo);
			}
			infoNcc.close();

			/******************** END INFO NCC ***********************/

			/******************** INFO SAN PHAM **********************/

			float[] crSanpham = { 1.2f * CONVERT, 6.1f * CONVERT,
					1.6f * CONVERT, 2.2f * CONVERT, 2.2f * CONVERT,
					2.6f * CONVERT };// , 1.9f * CONVERT};
			PdfPTable tbSanPham = new PdfPTable(crSanpham.length);
			tbSanPham.setWidthPercentage(100);
			tbSanPham.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbSanPham.setWidths(crSanpham);
			tbSanPham.getDefaultCell().setBorder(0);
			tbSanPham.setSpacingAfter(8.0f);

			String[] spTitles = { "Item No.", "Description", "UoM", "Quantity",
					"Unit Price\n(" + tiente + ")",
					"Amount\n(" + loaigiamua + ")" };// , "ETD"};
			// String[] spTitles = {"STT", "Tên hàng hóa", "Đơn vị", "Số lượng",
			// "Đơn giá", "Tiền tệ", "Thành tiền", "Ngày giao" };

			for (int z = 0; z < spTitles.length; z++) {
				cell = new PdfPCell(new Paragraph(spTitles[z], font11bold));
				cell.setPadding(3.0f);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tbSanPham.addCell(cell);
			}

			double tongtien = 0;
			double tongluong = 0;
			/*
			 * query = "select count(*) as soSP  " +
			 * "from ERP_MUAHANG_SP a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ    "
			 * + "inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  " +
			 * "where a.MUAHANG_FK = '" + id + "'"; ResultSet rsSOSP =
			 * db.get(query); int soSPCount = 0; if(rsSOSP.next()) { soSPCount =
			 * rsSOSP.getInt("soSP"); }
			 */

			query = " select isnull(a.tenhd, '') as tenhd, isnull(b.TEN2, ' ') + isnull(MAU, ' ') + isnull(quycach_nguongoc, ' ') as spTen, cast( ISNULL(B.DAI, '0') as varchar(10) ) + isnull(B.DVDL_DAI, ' ') + "
					+ "		cast( ISNULL(B.RONG, '0') as varchar(10) ) + isnull(B.DVDL_RONG, ' ') + cast( ISNULL(B.DINHLUONG, '0') as varchar(10) ) + isnull(B.DVDL_DINHLUONG, ' ') as spQc , 0 as type   "
					+ " from ERP_MUAHANG_SP a left join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ     "
					+ " left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  "
					+ " where a.MUAHANG_FK = '"
					+ id
					+ "' "
					+ " union all "
					+ " select isnull(diengiai, '') as tenhd, '' as spTen, '' as sqQc, 2 as type "
					+ " from ERP_MUAHANG_CPKHAC "
					+ " where MUAHANG_FK = "
					+ id
					+ " ";

			ResultSet rsSOSP = db.get(query);
			int soSPCount = 0;
			String spTen = "";
			while (rsSOSP.next()) {
				spTen = rsSOSP.getString("tenhd").trim().length() > 0 ? rsSOSP
						.getString("tenhd").trim() : rsSOSP.getString("spTen");
				int dodai = spTen.length() / 30;
				if (dodai < 1)
					dodai = 1;
				soSPCount += dodai;

				if (rsSOSP.getString("spQc").length() > 0) {
					dodai = rsSOSP.getString("spQc").length() / 30;
					if (dodai < 1)
						dodai = 1;
					soSPCount += dodai;
				}
			}

			query = " select isnull(a.tenhd, '') as tenhd, isnull(b.TEN2, '')  as spTen, isnull(MAU, '') as MAU, isnull(quycach_nguongoc, '') as quycach_nguongoc, "
					+ "			isnull(b.DAI, 0) AS DAI, ISNULL(B.RONG, 0) AS RONG, ISNULL(B.DINHLUONG,0) AS DINHLUONG, "
					+ "			isnull(B.DVDL_DAI, '') as DVDL_DAI, isnull(B.DVDL_RONG, '') as DVDL_RONG, isnull(B.DVDL_DINHLUONG, '') as DVDL_DINHLUONG, "
					+ "			ISNULL(c.donvi, 'NA') as donvi, a.SOLUONG, a.NGAYNHAN, a.DONGIA, 0 as type  "
					+ " from ERP_MUAHANG_SP a left join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ    "
					+ " left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  "
					+ " where a.MUAHANG_FK = '"
					+ id
					+ "'  "
					+

					" union all "
					+

					" select isnull(diengiai, '') as tenhd, ' ', ' ', ' ', '0', '0', '0', '', '', '', '', 1, '', isnull(sotien, 0), 1 as type "
					+ " from ERP_MUAHANG_CPKHAC "
					+ " where MUAHANG_FK = "
					+ id
					+ " ";

			if (ghichu.trim().length() > 0) // Do dai 34
			{
				query += " union all ";
				query += " select ' ', ' ', ' ', ' ', '0', '0', '0', '', '', '', '', '0', '', '0', 5 as type ";

				String[] ghichuArr = ghichu.split("__");
				// DEM SO GHI CHU
				int soGC = 0;
				for (int i = 0; i < ghichuArr.length; i++) {
					if (ghichuArr[i].trim().length() > 0) {
						soGC++;
					}
				}

				while (soSPCount <= (13 - soGC)) {
					query += " union all ";
					query += " select ' ', ' ', ' ', ' ', '0', '0', '0', '', '', '', '', '0', '', '0', 5 as type ";

					soSPCount++;
				}

				query += " union all ";
				query += " select ' ', N'Note:', ' ', ' ', '0', '0', '0', '', '', '', '', '0', '', '0', 5 as type ";
				soSPCount++;

				for (int i = 0; i < ghichuArr.length; i++) {
					query += " union all ";
					query += " select ' ', '"
							+ ghichuArr[i].replace("'", "''")
							+ "', ' ', ' ', '0', '0', '0', '', '', '', '', '0', '', '0', 5 as type ";
				}

				/*
				 * query += " union all "; query +=
				 * " select ' ', ' ', '0', '0', '0', '', '', '', '', '0', '', '0', 1 as type "
				 * ;
				 */

				query += " order by type asc ";
			}

			System.out.println("__: " + query);

			int soSP = 0;
			ResultSet infoSanPham = db.getScrol(query);
			if (infoSanPham != null) {
				infoSanPham.beforeFirst();
				while (infoSanPham.next()) {
					soSP++;
				}

				int stt = 0;
				infoSanPham.beforeFirst();
				while (infoSanPham.next()) {

					// Setup quy cach
					// Quy cach

					spTen = infoSanPham.getString("tenhd").trim().length() > 0 ? infoSanPham
							.getString("tenhd").trim() : infoSanPham
							.getString("spTen");

					if (infoSanPham.getString("type").equals("0")) {
						// Sản phẩm bình thường
						String qc = "";
						double _rong = 0, _dai = 0, _dinhluong = 0;
						try {
							_rong = Double.parseDouble(infoSanPham
									.getString("RONG"));
						} catch (Exception e) {
						}
						;
						try {
							_dai = Double.parseDouble(infoSanPham
									.getString("DAI"));
						} catch (Exception e) {
						}
						;
						try {
							_dinhluong = Double.parseDouble(infoSanPham
									.getString("DINHLUONG"));
						} catch (Exception e) {
						}
						;

						if (_dinhluong > 0) {
							qc += formatter2.format(_dinhluong)
									+ infoSanPham.getString("DVDL_DINHLUONG");
						}

						if (_rong > 0) {
							if (qc.length() > 0) {
								qc += " x ";
							}
							qc += formatter2.format(_rong)
									+ infoSanPham.getString("DVDL_RONG");
						}

						if (_dai > 0) {
							if (qc.length() > 0) {
								qc += " x ";
							}
							qc += formatter2.format(_dai)
									+ infoSanPham.getString("DVDL_DAI");
						}

						if (infoSanPham.getString("tenhd").trim().length() <= 0) {
							if (!infoSanPham.getString("QUYCACH_NGUONGOC")
									.equals("NO")
									&& !infoSanPham.getString(
											"QUYCACH_NGUONGOC").equals("Không")) {
								spTen = spTen
										+ " "
										+ infoSanPham
												.getString("QUYCACH_NGUONGOC");
							}

							if (!infoSanPham.getString("MAU").equals("NO")
									&& !infoSanPham.getString("MAU").equals(
											"Không màu")) {
								spTen = spTen + " "
										+ infoSanPham.getString("MAU");
							}
						}
						System.out.println("Ten SP: " + spTen);
						System.out.println("QC : " + qc);
						spTen = spTen + "\n" + qc;

						System.out.println("Ten SP: " + spTen);

						cell = new PdfPCell(new Paragraph(
								Integer.toString(stt + 1), font11));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(spTen, font11));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(
								infoSanPham.getString("donvi"), font11));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(
								formatter.format(infoSanPham
										.getDouble("SoLuong")), font11));
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(
								formatter2_sole.format(infoSanPham
										.getDouble("DonGia")), font11));
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						/*
						 * cell = new PdfPCell(new Paragraph(tiente, font11));
						 * if(stt == soSP - 1) { cell.setRowspan(2); }
						 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						 * cell.setBorder(0); if(stt < soSP) {
						 * cell.setBorderWidthLeft(0.5f); }
						 * tbSanPham.addCell(cell);
						 */

						cell = new PdfPCell(new Paragraph(
								formatter2_sole.format(infoSanPham
										.getDouble("SoLuong")
										* infoSanPham.getDouble("DonGia")),
								font11));
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
							cell.setBorderWidthRight(0.5f);
						}
						tbSanPham.addCell(cell);

						/*
						 * cell = new PdfPCell(new
						 * Paragraph(getVnDateTime(infoSanPham.getString(
						 * "NgayNhan")), font11)); if(stt == soSP - 1) {
						 * cell.setRowspan(2); }
						 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						 * cell.setBorder(0); if(stt < soSP) {
						 * cell.setBorderWidthLeft(0.5f);
						 * cell.setBorderWidthRight(0.5f); }
						 * tbSanPham.addCell(cell);
						 */

						tongluong += infoSanPham.getDouble("SoLuong");
						tongtien += infoSanPham.getDouble("SoLuong")
								* infoSanPham.getDouble("DonGia");
					} else if (infoSanPham.getString("type").equals("1")) {
						// Chi phí khác
						cell = new PdfPCell(new Paragraph(
								Integer.toString(stt + 1), font11));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(spTen, font11));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph("", font11));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph("", font11));
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph("", font11));
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(
								formatter2_sole.format(infoSanPham
										.getDouble("SoLuong")
										* infoSanPham.getDouble("DonGia")),
								font11));
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
							cell.setBorderWidthRight(0.5f);
						}
						tbSanPham.addCell(cell);

						tongtien += infoSanPham.getDouble("SoLuong")
								* infoSanPham.getDouble("DonGia");
					} else {
						// Dòng trống / ghi chú
						cell = new PdfPCell(new Paragraph("", font11));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(spTen, font11));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph("", font11));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(" ", font11));
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(" ", font11));
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
						}
						tbSanPham.addCell(cell);

						cell = new PdfPCell(new Paragraph(" ", font11));
						if (stt == soSP - 1) {
							cell.setRowspan(2);
						}
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorder(0);
						if (stt < soSP) {
							cell.setBorderWidthLeft(0.5f);
							cell.setBorderWidthRight(0.5f);
						}
						tbSanPham.addCell(cell);

						/*
						 * cell = new PdfPCell(new Paragraph(" ", font11));
						 * if(stt == soSP - 1) { cell.setRowspan(2); }
						 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						 * cell.setBorder(0); if(stt < soSP) {
						 * cell.setBorderWidthLeft(0.5f); }
						 * tbSanPham.addCell(cell);
						 */

						/*
						 * cell = new PdfPCell(new Paragraph(" ", font11));
						 * if(stt == soSP - 1) { cell.setRowspan(2); }
						 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						 * cell.setBorder(0); if(stt < soSP) {
						 * cell.setBorderWidthLeft(0.5f);
						 * cell.setBorderWidthRight(0.5f); }
						 * tbSanPham.addCell(cell);
						 */
					}
					stt++;
				}
				infoSanPham.close();

			}

			cell = new PdfPCell();
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell.addElement(new Paragraph("Remarks: ", new Font(bf, 8,
					Font.UNDERLINE)));
			cell.addElement(new Paragraph(
					"1.PLEASE SHIP AS OUR REQUIRED SCHEDULE.", font8));
			cell.addElement(new Paragraph(
					"2.ADVISE IF UNABLE TO SHIP ON SCHEDULE.", font8));
			cell.addElement(new Paragraph("3.  " + dungsai
					+ "%  TOLERANT OF QUANTITIES EACH ITEM IS ACCEPTABLE.",
					font8));
			cell.addElement(new Paragraph(
					"4.SHOW ORDER NO. ON ALL PACKING LIST & INVOICE.", font8));
			cell.addElement(new Paragraph("5.ENCLOSE DETAILS PACKING LIST.",
					font8));
			cell.addElement(new Paragraph("6.INVOICE IN TRIPLICATE.", font8));
			cell.addElement(new Paragraph(
					"  Pls confirm receiving to us by fax/email. Thank you!",
					font8));
			cell.addElement(new Paragraph("  ", font8));

			tbSanPham.addCell(cell);

			cell = new PdfPCell(new Paragraph("TOTAL", font12boldItalic));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);

			cell = new PdfPCell(new Paragraph(formatter.format(tongluong),
					font12boldItalic));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbSanPham.addCell(cell);

			cell = new PdfPCell(new Paragraph(tiente + "     "
					+ formatter2_sole.format(tongtien), font12boldItalic));
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbSanPham.addCell(cell);

			document.add(tbSanPham);

			/******************** END INFO NCC ***********************/

			// Table Footer
			PdfPTable tableFooter = new PdfPTable(4);
			float[] footerWidths = new float[4];
			float width = 19.0f * CONVERT / 4;
			for (int i = 0; i < footerWidths.length; i++) {
				footerWidths[i] = width;
			}

			tableFooter.setWidthPercentage(100);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.getDefaultCell().setBorder(0);
			tbSanPham.setSpacingBefore(5.0f);
			tableFooter.setWidths(footerWidths);

			PdfPCell cell11 = new PdfPCell(new Paragraph(
					"Supplier's Confirmation", font11boldItalic));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cell12 = new PdfPCell(new Paragraph("Prepared by",
					font11boldItalic));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cell13 = new PdfPCell(new Paragraph("Financial Control",
					font11boldItalic));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cell14 = new PdfPCell(new Paragraph("Approved by",
					font11boldItalic));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell11.setBorderWidth(0);
			cell11.setBorderWidthLeft(1);
			cell11.setBorderWidthRight(1);
			cell11.setBorderWidthTop(1);
			cell11.setBorderWidthBottom(1);

			cell12.setBorderWidth(1);
			cell12.setBorderWidthLeft(0);

			cell13.setBorderWidth(1);
			cell13.setBorderWidthLeft(0);

			cell14.setBorderWidth(1);
			cell14.setBorderWidthLeft(0);

			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);

			String[] footerStr = new String[] { " ", nguoilapTen,
					"Trần Phú Tâm", "Nhan Húc Quân" };

			// Them khoang trang
			int numFooterRows = 6;
			for (int i = 0; i <= numFooterRows; i++) {
				for (int j = 0; j < footerStr.length; j++) {
					PdfPCell cellFooter;
					if (i == numFooterRows) {
						cellFooter = new PdfPCell(new Paragraph(footerStr[j],
								font10));
						cellFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
					} else {
						cellFooter = new PdfPCell(new Paragraph(" ", font10));
					}

					cellFooter.setBorder(0);

					if (j == 0) {
						cellFooter.setBorderWidthLeft(1);
					}

					cellFooter.setBorderWidthRight(1);

					if (i == numFooterRows) {
						cellFooter.setBorderWidthBottom(1);
					}

					tableFooter.addCell(cellFooter);
				}
			}

			document.add(tableFooter);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception In PDF: " + e.getMessage());
		}

	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private String getEnDateTime(String date) {
		if (date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);

			thang = thang.equals("01") ? "Jan" : thang.equals("02") ? "Feb"
					: thang.equals("03") ? "Mar" : thang.equals("04") ? "Apr"
							: thang.equals("05") ? "May"
									: thang.equals("06") ? "Jun" : thang
											.equals("07") ? "Jul" : thang
											.equals("08") ? "Aug" : thang
											.equals("09") ? "Sep" : thang
											.equals("10") ? "Oct" : thang
											.equals("11") ? "Nov" : thang
											.equals("12") ? "Dec" : " ";
			return thang + " " + ngay + ", " + nam;
		} else {
			return date;
		}
	}

	private String getVnDateTime(String date) {
		if (date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			return ngay + "-" + thang + "-" + nam;
		} else {
			return date;
		}
	}

	public static void main(String[] arg) {
		ErpDonmuahangUpdate_GiaySvl dmh = new ErpDonmuahangUpdate_GiaySvl();
		dmh.removeFONT("");
	}

	public String removeFONT(String text) {
		String kq = "<font size=\"2\">Pha 2-GĐ TKTD kết thúc ngày 21/8/2014.<br></font>awdakd askjndfka<font size=\"2\" style='color:red' >TEST DONG THU 2</font>";

		String[] arr = kq.split("</font>");
		for (int pos = 0; pos < arr.length; pos++) {
			// TIM VI TRI BAT DAU CUA FONT
			int index = arr[pos].indexOf("<font ");

			// TIM VI TRI KET THUC CUA FONT
			if (index >= 0) {
				int index_END = 0;
				for (int i = index; i < arr[pos].length() - 1; i++) {
					if (arr[pos].substring(i, i + 1).equals(">")) {
						index_END = i;
						break;
					}
				}

				// TIM KY TU </font>
				// int index_END2 = kq.indexOf("</font>");
				// kq = kq.substring(index_END + 1, index_END2).trim();

				System.out.println("---KQ TRUOC: " + arr[pos]);

				if (index > 0)
					arr[pos] = arr[pos].substring(index_END + 1,
							arr[pos].length());
				else
					arr[pos] = arr[pos].substring(0, index)
							+ arr[pos].substring(index_END + 1,
									arr[pos].length());

				System.out.println("---KQ CHO NAY: " + arr[pos]);

			}
		}

		kq = "";
		for (int pos = 0; pos < arr.length; pos++) {
			kq += arr[pos];
		}

		System.out.println("Ket qua la: " + kq);
		return kq;
	}

}
