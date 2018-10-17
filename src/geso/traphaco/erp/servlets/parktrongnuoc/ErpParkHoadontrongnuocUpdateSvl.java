package geso.traphaco.erp.servlets.parktrongnuoc;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhapkhoNK.ISpDetail;
import geso.traphaco.erp.beans.nhapkhoNK.imp.SpDetail;
import geso.traphaco.erp.beans.parktrongnuoc.IErpPark;
import geso.traphaco.erp.beans.parktrongnuoc.IErpParkList;
import geso.traphaco.erp.beans.parktrongnuoc.imp.ErpPark;
import geso.traphaco.erp.beans.parktrongnuoc.imp.ErpParkList;
import geso.traphaco.erp.beans.shiphang.ISanpham;
import geso.traphaco.erp.beans.shiphang.imp.Sanpham;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.servlets.park.DocTien;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.DomUtil.NullResolver;

import com.cete.dynamicpdf.merger.pb;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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

public class ErpParkHoadontrongnuocUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public ErpParkHoadontrongnuocUpdateSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			IErpPark pBean = new ErpPark(id);
			pBean.setUserId(userId); // phai co UserId truoc khi Init
			pBean.setCtyId((String) session.getAttribute("congtyId"));
			pBean.setnppdangnhap(util.getIdNhapp(userId));
			String action = util.getAction(querystring);
			
		
			String nextJSP;
			if (request.getQueryString().indexOf("duyet") >= 0 ) {
        		pBean.setDuyet(1);
        	}
			if (request.getQueryString().indexOf("display") >= 0) {
				pBean.setIsDisplay(1);
				pBean.init();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuocDisplay.jsp";
			} else {
				pBean.init();
				//pBean.init_Update();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuocUpdate.jsp";
			}
			 
			 
			session.setAttribute("pBean", pBean);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");String userTen = (String) session.getAttribute("userTen");

		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(30000);

			//this.out = response.getWriter();
			IErpPark pBean;

			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));

			if (id == null) {
				pBean = new ErpPark("");
			} else {
				pBean = new ErpPark(id);
			}
			pBean.setRequest(request);

			pBean.setUserId(userId);

			pBean.setCtyId((String) session.getAttribute("congtyId"));
			

			pBean.setnppdangnhap(util.getIdNhapp(userId));

			String nccId = util.antiSQLInspection(request.getParameter("nccId"));
			if (nccId == null)
				nccId = "";
			pBean.setNccId(nccId);
			
			String khobiettruId = request.getParameter("khobiettruId");
		    if(khobiettruId == null){
		    	khobiettruId = "";
		    }
		    pBean.setIdKhoBietTru(khobiettruId);
		    
			String khotontruId = request.getParameter("khotontruId");
			
			if(khotontruId == null){
				khotontruId = "";
			}
			pBean.setIdKhoTonTru(khotontruId);
			

			String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
			if (ngayghinhan == null || ngayghinhan == "")
				ngayghinhan = getDateTime();
			pBean.setNgayghinhan(ngayghinhan);

			String ttId = util.antiSQLInspection(request.getParameter("ttId"));
			if (ttId == null || ttId == "") {
				ttId = "100000";
			}
			pBean.setTtId(ttId);

			String tongsoluong = util.antiSQLInspection(request.getParameter("tongsoluong"));
			if (tongsoluong == null || tongsoluong == "")
				tongsoluong = "0";
			pBean.setTongsoluong(tongsoluong.replaceAll(",", ""));

			String tigia = util.antiSQLInspection(request.getParameter("tigia"));
			if (tigia == null)
				tigia = "";
			pBean.setTigia(tigia.replaceAll(",", ""));

			String nccTTTen = util.antiSQLInspection(request.getParameter("nccTTTen"));
			if (nccTTTen == null)
				nccTTTen = "";
			pBean.setNCCThayTheTen(nccTTTen);

			String nccTTMST = util.antiSQLInspection(request.getParameter("nccTTMST"));
			if (nccTTMST == null)
				nccTTMST = "";
			pBean.setNCCThayTheMST(nccTTMST);

			String nccTTDiachi = util.antiSQLInspection(request.getParameter("nccTTDiachi"));
			if (nccTTDiachi == null)
				nccTTDiachi = "";
			pBean.setNCCThayTheDiaChi(nccTTDiachi);

			String loaidonmuahangId = util.antiSQLInspection(request.getParameter("loaidonmuahangId"));
			if (loaidonmuahangId == null)
				loaidonmuahangId = "";
			pBean.setLoaidonmh(loaidonmuahangId);
			
			String dienGiaiCT = util.antiSQLInspection(request.getParameter("dienGiaiCT"));
			if(dienGiaiCT == null)
				dienGiaiCT = "";
			pBean.setDienGiaiCT(dienGiaiCT);
			
			String duyet = util.antiSQLInspection(request.getParameter("duyet"));
			if(duyet == null)
				duyet = "";
		

			/* Lấy thông tin phu khác */
			/*
			 * Ký hiệu hoá đơn Số hoá đơn Ngày hoá đơn Số tiền chưa thuế Thuế
			 * giá trị gia tăng Tổng
			 */

			String mauSoHoaDon = request.getParameter("mausohoadon");
			pBean.setMauSoHoaDon(mauSoHoaDon);
			String kyHieuHoaDon = request.getParameter("kyhieuhoadon");
			pBean.setKyHieuHoaDon(kyHieuHoaDon);
			String soHoaDon = request.getParameter("sohoadon");
			pBean.setSoHoaDon(soHoaDon);
			String ngayHoaDon = request.getParameter("ngayhoadon");
			pBean.setNgayHoaDon(ngayHoaDon);
			String soTienChuaThue = request.getParameter("sotienchuathue");
			pBean.setSoTienChuaThue(soTienChuaThue.replaceAll(",", ""));
			String thuegtgt = request.getParameter("thuegtgt");
			pBean.setThuegtgt(thuegtgt.replaceAll(",", ""));
			String tong = request.getParameter("tong");

			// chỗ này được get nhiều
			String[] tempj = request.getParameterValues("poId");
			String poId = "";
			if (tempj != null) {
				for (int i = 0; i < tempj.length; i++)
					poId += tempj[i] + ",";

				if (poId.trim().length() > 0) {
					poId = poId.substring(0, poId.length() - 1);
				}
			}
			pBean.setPoId(poId);

			double temp = 0;
			try {
				if (tong.trim().length() > 0) {
					temp = Double.parseDouble(tong.replaceAll(",", ""));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			pBean.setTong(temp);
			String action = request.getParameter("action");
			
			// cho in truoc khi kiem tra du lieu
			if (action.equals("inPhieuNhapKho")){
				 try {
					 System.out.println(" da vao inPhieuNhapKho ");
					 create_PNK_PDF(response, request, pBean);
					 
				} catch (Exception e) {
					pBean.setIsDisplay(1);
					pBean.init();
					pBean.setMsg("Lỗi trong quá trình in file" );
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuocDisplay.jsp";
					response.sendRedirect(nextJSP);
					return;
				}
				
			}
			 
			
			
			
			
			
			
			String chenhlechhd = request.getParameter("chenhlechhd");
		    double chenhlech_hd=0;
		    try{
		    	if(chenhlechhd!= null && chenhlechhd.trim().length()>0 ){
		    		chenhlech_hd= Double.parseDouble(chenhlechhd.replaceAll(",", ""));
		    	}
		    }catch(Exception er){
		    	er.printStackTrace();
		    }
		    NumberFormat formatter1 = new DecimalFormat("#######.####");
		    pBean.setChenhlechHd(formatter1.format(chenhlech_hd));
			
			
			

			// chỗ này lấy danh sách sản phẩm
			// Luu lai san pham
			String[] spId = request.getParameterValues("idhangmua");
			String[] spMa = request.getParameterValues("mahangmua");
			String[] spTen = request.getParameterValues("tenhangmua");
			String[] spDonvi = request.getParameterValues("dvdl");
			String[] soluongdat = request.getParameterValues("soluongdat");
			String[] soluongnhan = request.getParameterValues("soluongnhan");
			String[] soluongMAXnhan = request.getParameterValues("soluongMAXnhan");
			// String[] ngaynhap = request.getParameterValues("ngaynhap");
			
			String[] vat = request.getParameterValues("vat");
			String[] hansudung = request.getParameterValues("hansudung");
			String[] dongia = request.getParameterValues("dongiaMua");
			String[] thanhTien = request.getParameterValues("thanhtien");
			String[] thanhTienVAT = request.getParameterValues("thanhtienVAT");
			
			String[] muahang_fk = request.getParameterValues("muahang_fk");

			String[] idmarquette = request.getParameterValues("idmarquette");

			// chỗ này được get nhiều mua hàng_fk
			String muahang = "";
			if (muahang_fk != null) {
				for (int i = 0; i < muahang_fk.length; i++)
					muahang += muahang_fk[i] + ",";

				if (muahang.trim().length() > 0) {
					muahang = muahang.substring(0, muahang.length() - 1);
				}
			}
			pBean.setMuahang_fk(muahang);

			List<ISanpham> spList = new ArrayList<ISanpham>();

			boolean error = false;
			if (spMa != null) {
				ISanpham sanpham = null;
				int m = 0;
				String slnhan = "0";
				String snluukho = "0";
				while (m < spMa.length) {

					if (soluongnhan[m].trim().length() > 0
							&& Double.parseDouble(soluongnhan[m].replaceAll(",", "")) > 0) {
						if (soluongnhan[m].trim().length() > 0)
							slnhan = soluongnhan[m].replaceAll(",", "");

						sanpham = new Sanpham(spId[m], spMa[m], spTen[m], "", "", "", soluongdat[m].replaceAll(",", ""),
								spDonvi[m]);

						sanpham.setSoluongnhan(slnhan);

						sanpham.setSoluongMaxNhan(soluongMAXnhan[m].replaceAll(",", ""));
						sanpham.setMuahang_fk(muahang_fk[m]);
						// kiểm tra chỗ này
						if (Double.parseDouble(slnhan) > Double.parseDouble(soluongMAXnhan[m].replaceAll(",", ""))) {
							error = true;
							pBean.setMsg("Không được nhập số lượng nhận quá số lượng cho phép, dòng thứ" + (m + 1));
						}

						sanpham.setSongayluukho(snluukho);
						sanpham.setVat(vat[m]);
						sanpham.setDongia(dongia[m]);
						sanpham.setHansudung(hansudung[m]);
						sanpham.setthanhtien(thanhTien[m].replaceAll(",", ""));
						// thêm VATVND
						sanpham.setThanhtienVATVND(thanhTienVAT[m].replaceAll(",", ""));

						// thêm marquette
						sanpham.setIdmarquette(idmarquette[m]);

						String chuoi = spId[m] + "." + muahang_fk[m]+m;

						String[] solo = request.getParameterValues(chuoi + ".solo");
						String[] soluong = request.getParameterValues(chuoi + ".soluong");
						String[] nsx = request.getParameterValues(chuoi + ".nsx");
						String[] nsxid = request.getParameterValues(chuoi + ".nsxid");
						String[] marrquet = request.getParameterValues(chuoi + ".marrquet");
						String[] ngaysanxuat = request.getParameterValues(chuoi + ".ngaysanxuat");
						String[] ngayhethan = request.getParameterValues(chuoi + ".ngayhethan");
						List<ISpDetail> lstspdt = new ArrayList<ISpDetail>();
						for (int i = 0; i < solo.length; i++) {
							if (soluong[i] != "" && solo[i] != ""  && ngayhethan[i] != "") {
								ISpDetail spdt = new SpDetail();
								spdt.setSolo(solo[i]);
								spdt.setSoluong(soluong[i]);
								spdt.setNSXTen(nsx[i]);
								spdt.setNSXid(nsxid[i]);
								spdt.setMarrquet(marrquet[i]);
								spdt.setNgaySx(ngaysanxuat[i]);
								spdt.setNgayHethan(ngayhethan[i]);
								lstspdt.add(spdt);
							}
							System.out.println("solo " + solo[i]);
						}
						sanpham.setSpDetail(lstspdt);
						spList.add(sanpham);
					}

					m++;
				}
			}

			pBean.setSpList(spList);
			// nếu xảy ra lỗi.
			if (error == true) {
				pBean.createRs();

				session.setAttribute("pBean", pBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuocNew.jsp";

				if (id != null){
					/*pBean.createRs_update();
					session.setAttribute("pBean", pBean);*/
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuocUpdate.jsp";
				}

				response.sendRedirect(nextJSP);
			}

			if (action.equals("change")) {

				if (id == null) // tao moi
				{
					pBean.createPark();
				} else {
					pBean.updatePark();
				}

				pBean.clearHdlist();
				pBean.init();
				//pBean.init_Update();
				session.setAttribute("pBean", pBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuocUpdate.jsp";
				response.sendRedirect(nextJSP);
				return;

			} else if (action.equals("save")) {
				if (id == null) // tao moi
				{

					if (!pBean.createPark()) {
						pBean.createRs();
						session.setAttribute("pBean", pBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuocNew.jsp";
						response.sendRedirect(nextJSP);
					} else {
						// Thêm thành công
						IErpParkList obj = new ErpParkList();
						obj.setUserId(userId);
						obj.setCongtyId((String) session.getAttribute("congtyId"));
						obj.setnppdangnhap(util.getIdNhapp(userId));
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.init("");
						session.setAttribute("obj", obj);
						session.setAttribute("userId", userId);

						response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuoc.jsp");
					}
				} else {
					
					if (!pBean.updatePark()){
						pBean.createRs();
						//pBean.createRs_update();
						session.setAttribute("pBean", pBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuocUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else {
						System.out.println("aaaaaaaaaa"+(duyet.equals("1")));
							if(duyet.equals("1"))
							{
						IErpParkList obj = new ErpParkList();
						obj.setUserId(userId);
						obj.setCongtyId((String) session.getAttribute("congtyId"));
						obj.setnppdangnhap(util.getIdNhapp(userId));
						obj.setDuyet(1);
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp";
						response.sendRedirect(nextJSP);
							}else
							{
								
								IErpParkList obj = new ErpParkList();
								obj.setUserId(userId);
								obj.setCongtyId((String) session.getAttribute("congtyId"));
								obj.setnppdangnhap(util.getIdNhapp(userId));
								GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
								obj.init("");
								session.setAttribute("obj", obj);
								String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuoc.jsp";
								response.sendRedirect(nextJSP);
								
							}
					}
				}
			} 
			 else  if(action.equals("chot"))
		    {
				 
					pBean.setUserId(userId);
					pBean.setCtyId((String) session.getAttribute("congtyId"));
					pBean.setnppdangnhap(util.getIdNhapp(userId));
					pBean.setId(id);
				
					geso.traphaco.erp.beans.park.IErpPark pBeanDuyet = new geso.traphaco.erp.beans.park.imp.ErpPark(id);
					
					
					pBeanDuyet.setCtyId((String)session.getAttribute("congtyId"));
					pBeanDuyet.setUserId(userId);
					pBeanDuyet.setnppdangnhap(util.getIdNhapp(userId));
				
		    		pBeanDuyet.Duyet();	
		    		
		    		pBean.setMsg(pBeanDuyet.getMsg());
		    		pBean.init();
		    		pBean.setDuyet(1);
		    		session.setAttribute("pBean", pBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuocDisplay.jsp";
					response.sendRedirect(nextJSP);
					return;
		    }
			else {
				
				if(action.equals("chonkhotontru"))
				{
					pBean.setIdKhoTonTru(pBean.getIdKhoBietTru());
				}
				
				pBean.createRs();
				session.setAttribute("pBean", pBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuocNew.jsp";

				if (id != null){
					/*pBean.createRs_update();
					session.setAttribute("pBean", pBean);*/
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuocUpdate.jsp";
				}

				response.sendRedirect(nextJSP);
			}
	
			
			

		}
		
		
		
		
		
		
		
	}

	private void create_PNK_PDF(HttpServletResponse response,
			HttpServletRequest request, IErpPark pBean) {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition",
		" inline; filename=Traphaco_PhieuNhapThanhPham2.pdf");

		float CONVERT = 28.346457f;// 1 cm
		float PAGE_LEFT = 2.0f * CONVERT, PAGE_RIGHT = 1.5f * CONVERT, PAGE_TOP = 0.5f * CONVERT, PAGE_BOTTOM = 0.0f * CONVERT;
		Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT,
				PAGE_TOP, PAGE_BOTTOM);
		ServletOutputStream outstream;

		try {
			outstream = response.getOutputStream();
			dbutils db = new dbutils();
			pBean.init();
			CreatePO_Training(document, outstream, response, request, db, pBean);
			
			document.close();
			db.shutDown();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception PO Print: " + e.getMessage());
		}
		
	}
	
	private void CreatePO_Training(Document document,
			ServletOutputStream outstream, HttpServletResponse response,
			HttpServletRequest request, dbutils db, IErpPark pBean) {
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String congTyId = (String) session.getAttribute("congtyId");
		System.out.println("congTyId" + congTyId);
		String userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		System.out.println("userId" + userId);
		String id = util.getId(querystring);
		if (id.length() == 0)
			id = util.antiSQLInspection(request.getParameter("id"));
		System.out.println("id" + id);

		//________________________________________________

		NumberFormat formatter_2sole = new DecimalFormat("#,###,###.##");
		NumberFormat formatter = new DecimalFormat("#,###,###");
		NumberFormat formater = new DecimalFormat("##,###,###.##");
		NumberFormat formatersl = new DecimalFormat("##,###,###.####");

		try {
			
			PdfWriter.getInstance(document, outstream);
			document.open();

			float CONVERT = 28.346457f; // = 1cm
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font10_bold = new Font(bf, 10, Font.BOLD);
			Font font10_ita = new Font(bf, 10, Font.ITALIC);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font11_bold = new Font(bf, 11, Font.BOLD);
			Font font11_ita = new Font(bf, 11, Font.ITALIC);
			Font font12_bold = new Font(bf, 12, Font.BOLD);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font12v1 = new Font(bf, 9, Font.NORMAL);
			Font font14 = new Font(bf, 14, Font.NORMAL);
			Font font14_bold = new Font(bf, 14, Font.BOLD);

			//Header 2
			Paragraph pr;

			PdfPTable table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setSpacingBefore(3.0f);
			table.setSpacingAfter(4.0f);
			
			// insert logo
			Image img = Image.getInstance(getServletContext().getInitParameter("pathPrint")+ "\\logo.gif");
			img.scalePercent(10);
	//		img.setAbsolutePosition(3f * CONVERT, document.getPageSize().getHeight() - 1.2f * CONVERT);
			img.setAbsolutePosition(3.5f * CONVERT, document.getPageSize().getHeight() - 1.2f * CONVERT);
			document.add(img);

			PdfPCell cell;
			cell = new PdfPCell();
			Paragraph p = new Paragraph();
			p = new Paragraph();
			p.add("\n");
			cell.setBorder(0);

//			p.add(new Chunk("CÔNG TY TNHH TRAPHACO HƯNG YÊN", font12)); 
			p.add(new Chunk("CÔNG TY TNHH TRAPHACO HƯNG YÊN", font12v1)); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table.addCell(cell);

			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("", font11_ita)); 
			p.setAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table.addCell(cell);

			document.add(table);

			pr = new Paragraph("PHIẾU NHẬP KHO", new Font(bf, 16, Font.BOLD));
			pr.setSpacingBefore(10);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_CENTER);
			document.add(pr);

			String[] thongTin = pBean.initPNK_StringArray();
			pr = new Paragraph("                   Ngày "+thongTin[0].substring(8,10)+" tháng "+thongTin[0].substring(5,7)+"   năm   "+thongTin[0].substring(0,4)+"          Số: "+(thongTin[1].equals("0") ? "" : thongTin[1]) , new Font(bf, 13, Font.BOLD));
			pr.setSpacingBefore(0);
			/*pr.setSpacingAfter(10);*/
			pr.setAlignment(Element.ALIGN_CENTER);
			document.add(pr);
			

			pr = new Paragraph("Họ và tên người mua hàng: "+thongTin[2], font12);
			pr.setSpacingBefore(10);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);

			pr = new Paragraph("Tên NCC: "+thongTin[3], font12);
			pr.setSpacingBefore(3);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);
			
			pr = new Paragraph("Địa chỉ: "+thongTin[4], font12);
			pr.setSpacingBefore(3);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);
			String ngayHoaDon = thongTin[7].substring(8,10)+"-"+thongTin[7].substring(5,7)+"-"+thongTin[7].substring(0,4);
			pr = new Paragraph("Số hóa đơn: "+thongTin[5]+"              Seri: "+thongTin[6]+"          Ngày: "+ngayHoaDon, font12);
			pr.setSpacingBefore(3);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);

			pr = new Paragraph("Nội dung: " +thongTin[8], font12);
			pr.setSpacingBefore(3);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);

			pr = new Paragraph("Tài khoản: " +thongTin[9], font12);
			pr.setSpacingBefore(3);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);
			thongTin[10] = thongTin[10].trim().length() > 0 ? thongTin[10] : ""; 
			pr = new Paragraph("Nhập tại kho: " +thongTin[10], font12);
			pr.setSpacingBefore(3);
			pr.setSpacingAfter(20);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);
		
					// Data table => Dựa vào loại kho mà chọn cặp crPhieuNhap-spTitles phù hợp

					// 1. Kho phụ liệu cấp 1

					float[] crPhieuNhap1 = { 1.75f * CONVERT, 3.75f * CONVERT, 1.25f * CONVERT,
					1.25f * CONVERT, 1.5f * CONVERT, 1.5f * CONVERT, 2.0f * CONVERT};

					int socot = 0;
					socot = crPhieuNhap1.length;


					table = new PdfPTable(socot);
					table.setWidthPercentage(100);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);

					table.getDefaultCell().setBorder(0);
					table.setSpacingAfter(8.0f);



					String[] spTitles1 = { "Mã vật tư", "Tên vật tư","TK", "ĐVT", "Số lượng", "Đơn giá",
					"Thành tiền"};

					table.setWidths(crPhieuNhap1);
					//in tieu de
					for (int z = 0; z < spTitles1.length; z++) {
						cell = new PdfPCell(new Paragraph(spTitles1[z], font11_bold));
						cell.setPadding(3.0f);
						cell.setPaddingBottom(7);
						//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
					}
					Double tongTienHang = 0.0;
					Double tongVAT = 0.0;
					Double tongTienThanhToan = 0.0;
					NumberFormat formatter1 = new DecimalFormat("#,###,###.###");
					List<ISanpham> spList = pBean.initPNK_HD_SP();	
					if (spList.size() > 0) {
						for (ISanpham sp : spList) {
							String maKho=sp.getMa();
							String spTen=sp.getDiengiai();
							String donVi=sp.getDvdl();
							String taiKhoan=sp.getTaiKhoan();
							double soLuong=sp.getSoLuong2();
							Double donGia = Double.parseDouble(sp.getDongiaViet());
							Double thanhTien= Double.parseDouble(sp.getThanhtienVND());
							tongTienHang += thanhTien;
							tongVAT += Double.parseDouble(sp.getThanhtienVATVND());
							String[] sp_data1={maKho, spTen, taiKhoan, donVi, formatersl.format(soLuong), formatter1.format(donGia), formatter1.format(thanhTien)};
					
							for (int z = 0; z < sp_data1.length; z++) {
								cell = new PdfPCell(new Paragraph(sp_data1[z],new Font(bf, 10,Font.NORMAL)));
								cell.setPadding(3.0f);
								cell.setPaddingBottom(7);

								if(z==0 || z==1){
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								}
								else
									if(z==5 || z==6 ){
										cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									}
									else
										cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorderWidthBottom(0.5f);
								table.addCell(cell);
							}
							
						}
						Double soTien = 0.0;
						//Tong cong
						String[] spTongCong = {"Tổng cộng tiền hàng", "Chi phí", "Thuế giá trị gia tăng"};
						for (int z = 0; z < spTongCong.length; z++) {
							if (z == 0) {
								soTien = tongTienHang;
								tongTienThanhToan += soTien;
							}else if (z == 2){
								soTien = tongVAT;
								tongTienThanhToan += soTien;
							} else {
								soTien = 0.0;
								tongTienThanhToan += soTien;
							}
							cell.setBorderWidthTop(1.0f);
							cell = new PdfPCell(new Paragraph(spTongCong[z], font11));
							cell.setPadding(3.0f);
							cell.setPaddingBottom(7);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					        cell.setColspan(6);
							cell.setBorderWidthBottom(0.5f);
							cell.setBorderColorBottom(BaseColor.GRAY);
							table.addCell(cell);
							cell = new PdfPCell(new Paragraph(formatter1.format(soTien), font11));
							cell.setPadding(3.0f);
							cell.setPaddingBottom(7);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setBorderWidthBottom(0.5f);
							cell.setBorderColorBottom(BaseColor.GRAY);
							table.addCell(cell);
						}
						cell.setBorderColorTop(BaseColor.BLACK);
						cell = new PdfPCell(new Paragraph("Tổng cộng tiền thanh toán", font11_bold));
						cell.setPadding(3.0f);
						cell.setPaddingBottom(7);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				        cell.setColspan(6);
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph(formatter1.format(tongTienThanhToan), font11_bold));
						cell.setPadding(3.0f);
						cell.setPaddingBottom(7);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell);

					document.add(table);
					DocTien docTien = new DocTien();
					String tienchu = docTien.docTien((long)tongTienThanhToan.doubleValue());
					pr = new Paragraph("Bằng chữ: "+tienchu, font11_bold);
					document.add(pr);
					/*pr = new Paragraph("PHÓ TỔNG GIÁM ĐỐC                     			"+ "T/L KẾ TOÁN TRƯỞNG                         "+
							"NGƯỜI LẬP PHIẾU", font11_bold);
					pr.setSpacingBefore(5);*/
					/*document.add(pr);*/

					pr = new Paragraph("T/L GIÁM ĐỐC                     "+ "KẾ TOÁN TRƯỞNG                 "+
							"NGƯỜI LẬP PHIẾU"+"        				     " + "THỦ KHO", font11_bold);
					pr.setSpacingBefore(5);
					document.add(pr);
					//pr = new Paragraph("                                        THỦ KHO                                      " + "PHỤ TRÁCH CUNG TIÊU", font11_bold);
					//pr.setSpacingBefore(70);
					//pr.setAlignment(Element.ALIGN_LEFT);
					//document.add(pr);
					document.newPage();
					
				}
			


		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception print PDF: " + e.getMessage());
		}
	}
		
	/*private String demTien(Double tien) {
		NumberFormat formatter = new DecimalFormat("#,###,###");
		String chuoi = formatter.format(tien);
		String[] tachChuoi = chuoi.trim().split(",");
		int count = tachChuoi.length;
		if (count >= 4) {
			if ()
		}
		switch (count) {
			case  
		}
	}*/
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static void main(String[] args) {
		DocTien docTien = new DocTien();
		Double value = 96798240.0;
		String tienchu = docTien.docTien((long)value.doubleValue());
		System.out.println(tienchu);
		
	}

}
