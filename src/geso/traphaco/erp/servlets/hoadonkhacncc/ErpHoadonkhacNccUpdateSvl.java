package geso.traphaco.erp.servlets.hoadonkhacncc;
 
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadonkhacncc.IErpHoadonkhacNccList;
import geso.traphaco.erp.beans.hoadonkhacncc.IErpHoadonkhacNcc;
 
import geso.traphaco.erp.beans.hoadonkhacncc.ISanpham;
import geso.traphaco.erp.beans.hoadonkhacncc.imp.ErpHoadonkhacNccList;
import geso.traphaco.erp.beans.hoadonkhacncc.imp.ErpHoadonkhacNcc;
 
import geso.traphaco.erp.beans.hoadonkhacncc.imp.Sanpham;
import geso.traphaco.erp.beans.parktrongnuoc.IErpPark;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.servlets.park.DocTien;

import java.io.IOException;
 
import java.sql.ResultSet;
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

 

public class ErpHoadonkhacNccUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpHoadonkhacNccUpdateSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nextJSP;
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
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
			System.out.println(" id doget :" + id);
			
			IErpHoadonkhacNcc dmhBean = new ErpHoadonkhacNcc(id);
			//dmhBean.setId(id);
			dmhBean.setCongtyId((String) session.getAttribute("congtyId"));
			dmhBean.setUserId(userId); // phai co UserId truoc khi Init

			String task = request.getParameter("task");
			if (task == null)
				task = "";

			String canduyet = request.getParameter("duyet");
			if (canduyet == null)
				canduyet = "1";
			dmhBean.setCanDuyet(canduyet);
			
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			dmhBean.setNpp_duocchon_id(npp_duocchon_id);
			
			

			if (task.equals("print")) {
				//Create_PO_PDF(response, request);
			} else {
				dmhBean.init();

				if (request.getQueryString().indexOf("display") >= 0) {
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNccDisplay.jsp";
				} else {
					 
						session.setAttribute("nhacungcapNK", dmhBean.getNhacungcapNK());
						session.setAttribute("ngaymuahang", dmhBean.getNgaymuahang());
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNccUpdate.jsp";
					 
				}

				session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
				session.setAttribute("loaihanghoa", dmhBean.getLoaihanghoa());

				session.setAttribute("lspId", dmhBean.getLoaispId());
				session.setAttribute("dmhBean", dmhBean);
				session.setAttribute("loaimh", dmhBean.getLoai());
				session.setAttribute("nccId", dmhBean.getNCC());
				session.setAttribute("isGiaCong", "");
				response.sendRedirect(nextJSP);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			session.setMaxInactiveInterval(30000);

			IErpHoadonkhacNcc dmhBean;
			Utility util = new Utility();
			
		
			String id = util.antiSQLInspection(request.getParameter("id"));
			
			System.out.println("ID" + id);
			
			dmhBean = new ErpHoadonkhacNcc(id);
			dmhBean.setId(id);
			
			if (id == null) {
				dmhBean = new ErpHoadonkhacNcc("");
			} else {
				dmhBean = new ErpHoadonkhacNcc(id);
			}
			String contyId = (String) session.getAttribute("congtyId");

			session.setAttribute("ctyId", contyId);
			dmhBean.setCongtyId(contyId);
			dmhBean.setUserId(userId);

		 

			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			dmhBean.setNpp_duocchon_id(npp_duocchon_id);
 

			String ngaygd = util.antiSQLInspection(request.getParameter("ngayghinhan"));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();
			session.setAttribute("ngaymuahang", ngaygd);
			dmhBean.setNgaymuahang(ngaygd);

			String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if (sochungtu == null){
				sochungtu = "";
			}
			dmhBean.setSochungtu(sochungtu);
			
			String masothue = util.antiSQLInspection(request.getParameter("masothue"));
			if (masothue == null){
				masothue = "";
			}
			dmhBean.setMasothue(masothue);
			
			
			String diachi = util.antiSQLInspection(request.getParameter("diachi"));
			if (diachi == null){
				diachi = "";
			}
			dmhBean.setDiachiNcc(diachi);
			
			
			String mauhoadon = util.antiSQLInspection(request.getParameter("mauhoadon"));
			if (mauhoadon == null){
				mauhoadon = "";
			}
			dmhBean.setMauhoadon(mauhoadon);
			
			String kyhieuhoadon = util.antiSQLInspection(request.getParameter("kyhieuhoadon"));
			if (kyhieuhoadon == null){
				kyhieuhoadon = "";
			}
			dmhBean.setKyhieu(kyhieuhoadon);
			
			
			String loaihanghoa = util.antiSQLInspection(request.getParameter("loaihanghoa"));
			if (loaihanghoa == null){
				loaihanghoa = "";
			}
			dmhBean.setLoaihanghoa(loaihanghoa);
			
			session.setAttribute("loaihanghoa", loaihanghoa);
			
			String ngayhoadon = util.antiSQLInspection(request.getParameter("ngayhoadon"));
			if (ngayhoadon == null){
				ngayhoadon = "";
			}
			dmhBean.setNgayhoadon(ngayhoadon);
			
			String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
			if (sohoadon == null){
				sohoadon = "";
			}
			dmhBean.setSohoadon(sohoadon);
			
			String tiGiaNguyenTe = util.antiSQLInspection(request.getParameter("tiGiaNguyenTe"));
			if (tiGiaNguyenTe == null){
				tiGiaNguyenTe = "1";
			}
			double tigia=0;
			try{
				tigia= Double.parseDouble(tiGiaNguyenTe.replaceAll(",", ""));
				
			
			}catch(Exception er){
				 
			}
			dmhBean.SetTiGiaNguyenTe( tigia);
			

			String nccId = util.antiSQLInspection(request.getParameter("nccId"));
			if (nccId == null)
				nccId = "";
			System.out.println("NCC " + nccId);
			session.setAttribute("nccId", nccId);
			dmhBean.setNCC(nccId);

			String nccLoai = util.antiSQLInspection(request.getParameter("nccLoai"));
			if (nccLoai == null)
				nccLoai = "";
			session.setAttribute("nccLoai", nccLoai);
			dmhBean.setNccLOai(nccLoai);
			
			
			
			String nhaptaikho = util.antiSQLInspection(request.getParameter("nhaptaikho"));
			if (nhaptaikho == null)
				nhaptaikho = "";
			dmhBean.setNhaptaikho(nhaptaikho);
			
			
			String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			dmhBean.setGhiChu(diengiai);
			
			
			String nccTen = util.antiSQLInspection(request.getParameter("nccTen"));
			if (nccTen == null)
				nccTen = "";
			dmhBean.setNccTen(nccTen);

			String tiente_fk = util.antiSQLInspection(request.getParameter("tiente_fk"));
			if (tiente_fk == null) {
				tiente_fk = "";
			}

			if (tiente_fk.trim().length() > 0) {
				String[] arr = tiente_fk.split(" - ");
   
				dmhBean.setTienTe_FK(arr[0]);
			}

			String tongtientruocVAT = request.getParameter("BVAT");
			if (tongtientruocVAT == null)
				tongtientruocVAT = "0";
			else
				tongtientruocVAT = tongtientruocVAT.replaceAll(",", "");
			dmhBean.setTongtienchuaVat(tongtientruocVAT);

		 

			 

			String tongtiensauVAT = request.getParameter("AVAT");
			if (tongtiensauVAT == null)
				tongtiensauVAT = "0";
			else
				tongtiensauVAT = tongtiensauVAT.replaceAll(",", "");
			dmhBean.setTongtiensauVat(tongtiensauVAT);

	 
 
    
 

			String[] idsp = request.getParameterValues("idsp");
			String[] masp = request.getParameterValues("masp");
			String[] tensp = request.getParameterValues("tensp");
			String[] sl = request.getParameterValues("soluong");
		 
			String[] donvitinh = request.getParameterValues("donvitinh");
			String[] dongia = request.getParameterValues("dongia");
		  
			String[] thanhtientruocthue = request.getParameterValues("thanhtientruocthue");
			String[] phantramthue = request.getParameterValues("phantramthue");
			
			String[] tienthue = request.getParameterValues("tienthue");
			String[] thanhtiensauthue = request.getParameterValues("thanhtiensauthue");
			 
			String action = request.getParameter("action");
			
			
			String[] taikhoankt = request.getParameterValues("taikhoankt");
			String[] sohieutaikhoan = request.getParameterValues("sohieutaikhoan");
			
			
			
			
			List<ISanpham> spList = new ArrayList<ISanpham>();
			boolean ktDonvi = true;
			
			DecimalFormat formatter_2le = new DecimalFormat("#########.##");
			DecimalFormat formatter_0 = new DecimalFormat("#########");
			
			ISanpham sp = null;
			String tenhd = "";
			double	total_tiennguyente=0;
			double total_tienbvat=0;
			double total_tienvat =0;
			
			for (int i = 0; i < masp.length; i++) {
				if ( taikhoankt[i].trim().length() > 0 || tensp[i].trim().length() > 0 || masp[i].trim().length() > 0) {
					  
					sp = new Sanpham();
					
					sp.setTaikhoanKTId(taikhoankt[i]);
					sp.setSohieutaikhoan(sohieutaikhoan[i]);
					sp.setPK_SEQ(idsp[i]);
 
					sp.setMasanpham(masp[i]);
					sp.setTensanpham(tensp[i]);
					double soluong =0;
					try{
					  soluong =Double.parseDouble(sl[i].replaceAll(",", ""));
					}catch(Exception er){}
					
					 double dongia_ =0;
					try{
						dongia_ =Double.parseDouble(dongia[i].replaceAll(",", ""));
					}catch(Exception er){}
					
					
					sp.setSoluong(soluong+"");
					
					sp.setDonvitinh(donvitinh[i]);
					sp.setDongia(dongia_+"");
					
					double thanhtiennguyente= Double.parseDouble(formatter_2le.format(soluong*dongia_));
					total_tiennguyente+=thanhtiennguyente;
					
					sp.setThanhtienNguyenTe(thanhtiennguyente) ;
					double thanhtientruocthue_= Math.round(thanhtiennguyente * tigia) ;
					sp.setThanhtien(thanhtientruocthue_);
					total_tienbvat+=thanhtientruocthue_;
					
					
					double phantramthue_=0;
					try{
						phantramthue_ =Double.parseDouble(phantramthue[i].replaceAll(",", ""));
					}catch(Exception er){}
					sp.setPhantramthue(phantramthue_);
					
					double tienthue_=0;
					try{
						tienthue_ = Math.round( Double.parseDouble(tienthue[i].replaceAll(",", "")));
					}catch(Exception er){}
					sp.setTienthue(tienthue_);
					
					total_tienvat+= tienthue_;
					 
					double tiensauthue_= thanhtientruocthue_+ tienthue_;
					 
					sp.setTiensauthue(tiensauthue_);
					 
					spList.add(sp);
				}
			}
			dmhBean.setSpList(spList);
			
			dmhBean.setTongtienchuaVat(formatter_0.format(total_tienbvat));
			dmhBean.setTongtiensauVat(formatter_0.format(total_tienbvat+total_tienvat));
			dmhBean.setVat(formatter_0.format(total_tienvat));
			

			if (action.equals("save")) {
				if (ktDonvi == false) {
					dmhBean.setMsg("Vui lòng nhập Đơn vị tính");
					dmhBean.createRs();
					String nextJSP;
					if (id == null) {
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNccNew.jsp";
					} else {
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNccUpdate.jsp";
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
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNccNew.jsp";
						response.sendRedirect(nextJSP);
						return;
					} else {
						IErpHoadonkhacNccList obj = new ErpHoadonkhacNccList();
						obj.setCongtyId((String) session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.setNpp_duocchon_id(npp_duocchon_id);
						//GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNcc.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				} else {
					if (!dmhBean.updateDmh()) {
						dmhBean.createRs();
						session.setAttribute("dmhBean", dmhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNccUpdate.jsp";
						response.sendRedirect(nextJSP);
						return;
					} else {
						IErpHoadonkhacNccList obj = new ErpHoadonkhacNccList();
						obj.setCongtyId((String) session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.setNpp_duocchon_id(npp_duocchon_id);
						//GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNcc.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				}
			}else 
				if (action.equals("inPhieuNhapKho")){
					create_PNK_PDF(response, request,dmhBean);
				} else {
				if (action.equals("changeSP")) 
				{
					if (nccId.trim().length() > 0) { }

					// Thay doi loai hang hoa, phai xoa het SP da chon
					dmhBean.setSpList(new ArrayList<ISanpham>());
				}

				if (action.equals("changeTiente")) 
				{ 
					// lấy tỉ giá từ tiền tệ
					if (tiente_fk.trim().length() > 0) {
						String[] arr = tiente_fk.split(" - ");
		   
						dmhBean.setTienTe_FK(arr[0]);
						try{
							dmhBean.setTyGiaQuyDoi(Float.parseFloat(arr[1]));
						}catch(Exception er){
							dmhBean.setTyGiaQuyDoi(0);
						}
						
					}
					
				}

				dmhBean.createRs();
 

				String nextJSP;
				if (id == null) {
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNccNew.jsp";
				} else {
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNccUpdate.jsp";
				}
				session.setAttribute("dmhBean", dmhBean);
				response.sendRedirect(nextJSP);
			}
			}
	}
 
	private void create_PNK_PDF(HttpServletResponse response,
			HttpServletRequest request, IErpHoadonkhacNcc pBean) {
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
			
			//pBean.init();
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
			HttpServletRequest request, dbutils db, IErpHoadonkhacNcc pBean) {
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
		
		//---------
		String taikhoan ="select c.SOHIEUTAIKHOAN,a.TAIKHOAN_FK from ERP_NHACUNGCAP a " 
				+	" inner join ERP_TAIKHOANKT c on c.PK_SEQ=a.TAIKHOAN_FK where a.PK_SEQ="+pBean.getNCC();	
		ResultSet rsTk=db.get(taikhoan);
		System.out.println("taikhoan : "+taikhoan + "ncc : "+pBean.getNCC());
		String tk="";
		if(rsTk!=null){
			try{
				while(rsTk.next())
				{
				tk=rsTk.getString("SOHIEUTAIKHOAN");
				}
				rsTk.close();
			}catch (Exception e){ e.printStackTrace(); }		
		}

		String nguoitao ="select b.TEN,nv.ten as tennv from ERP_HOADONKHACNCC a \n" +
				"inner join ERP_NHANVIEN b on a.NHACUNGCAP_FK=b.PK_SEQ \n" +
				"left join nhanvien nv on nv.pk_seq=a.nguoitao\n" +
				" where a.PK_SEQ="+pBean.getId();	
		ResultSet rsnguoitao=db.get(nguoitao);
		System.out.println("taikhoan : "+nguoitao + "ncc : "+pBean.getId());
		String nt="";
		if(rsnguoitao!=null){
			try{
				while(rsnguoitao.next())
				{
					nt=rsnguoitao.getString("tennv");
				}
				rsnguoitao.close();
			}catch (Exception e){ e.printStackTrace(); }		
		}
		//------------------
		NumberFormat formatter_2sole = new DecimalFormat("#,###,###.##");
		NumberFormat formatter = new DecimalFormat("#,###,###");
		NumberFormat formater = new DecimalFormat("##,###,###.##");

		try {
			
			PdfWriter.getInstance(document, outstream);
			document.open();

			float CONVERT = 28.346457f; // = 1cm
			//BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font9=new Font(bf, 9, Font.NORMAL);
			Font font9_bold=new Font(bf, 9, Font.BOLD);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font10_bold = new Font(bf, 10, Font.BOLD);
			Font font10_ita = new Font(bf, 10, Font.ITALIC);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font11_bold = new Font(bf, 11, Font.BOLD);
			Font font11_ita = new Font(bf, 11, Font.ITALIC);
			Font font12_bold = new Font(bf, 12, Font.BOLD);
			Font font12 = new Font(bf, 12, Font.NORMAL);
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
			img.setAbsolutePosition(3f * CONVERT, document.getPageSize().getHeight() - 1.2f * CONVERT);
			document.add(img);

			PdfPCell cell;
			cell = new PdfPCell();
			Paragraph p = new Paragraph();
			p = new Paragraph();
			p.add("\n");
			cell.setBorder(0);

			p.add(new Chunk("CÔNG TY CP TRAPHACO", font10)); 
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

			pr = new Paragraph("PHIẾU NHẬP KHO", new Font(bf, 22, Font.BOLD));
			pr.setSpacingBefore(10);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_CENTER);
			document.add(pr);
			String sott="";
			ResultSet rssott = db.get("select ISNULL(SOTTDUYET,0) AS SOTTDUYET from ERP_HOADONKHACNCC where PK_SEQ = "+pBean.getId());
			if(rssott != null){
				try {
					while(rssott.next()){
						
						sott = rssott.getString("SOTTDUYET");
						
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			String date=pBean.getNgayhoadon();
			//String[] thongTin = pBean.initPNK_StringArray();
			//String ThongTin= util.convertDate(pBean.getNgaymuahang());
			pr = new Paragraph();
			Chunk chunk =new Chunk("              Ngày "+date.substring(8,10)+" tháng "+date.substring(5,7)+" năm "+date.substring(0,4)/*+(thongTin[1].equals("0") ? "" : thongTin[1])*/ , new Font(bf, 9, Font.ITALIC));
			pr.add(chunk);
			chunk=new Chunk("" , new Font(bf, 9, Font.BOLD));
			pr.add(chunk);
			pr.setSpacingBefore(0);
			/*pr.setSpacingAfter(10);*/
			pr.setAlignment(Element.ALIGN_CENTER);
			document.add(pr);
			
			pr = new Paragraph();
			pr.add(new Chunk("Số: ", new Font(bf, 9, Font.BOLD)));
			pr.add(new Chunk(sott,font9));
			pr.setSpacingBefore(4);
			//pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_RIGHT);
			document.add(pr);
			
			
			//pr = new Paragraph(new Chunk("Họ và tên người lập phiếu: " )+nt, font9_bold);
			pr = new Paragraph();
			pr.add(new Chunk("Họ và tên người lập phiếu: " , font9_bold));
			pr.add(new Chunk(nt,font9));
			pr.setSpacingBefore(4);
			//pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);

			
			pr = new Paragraph();
			pr.add(new Chunk("Tên NCC: " , font9_bold));
			pr.add(new Chunk(pBean.getNccTen(),font9));
			pr.setSpacingBefore(3);
			//pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);
			
			pr = new Paragraph();
			pr.add(new Chunk("Địa chỉ: ", font9_bold));
			pr.add(new Chunk(pBean.getDiachiNcc(),font9));
			pr.setSpacingBefore(3);
			//pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);
			
			String thongTin=util.convertDate(pBean.getNgayhoadon());
			String ngayHoaDon = thongTin.substring(8,10)+"/"+thongTin.substring(5,7)+"/"+thongTin.substring(0,4);
			pr = new Paragraph();
			pr.add(new Chunk("Số hóa đơn: ", font9_bold));
			pr.add(new Chunk(pBean.getSohoadon(),font9));
			pr.add(new Chunk("              Seri: ", font9_bold));
			pr.add(new Chunk(pBean.getKyhieu(),font9));
			pr.add(new Chunk("              Ngày: ", font9_bold));
			pr.add(new Chunk(ngayHoaDon,font9));
			pr.setSpacingBefore(3);
			//pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);

			pr = new Paragraph();
			pr.add(new Chunk("Nội dung:", font9_bold));
			pr.add(new Chunk(" "+pBean.getGhiChu(),font9));
			pr.setSpacingBefore(3);
			//pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);

			pr = new Paragraph();
			pr.add(new Chunk("Tài khoản: ", font9_bold));
			pr.add(new Chunk(tk,font9));
			pr.setSpacingBefore(3);
			//pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);
			
			//thongTin[10] = thongTin[10].trim().length() > 0 ? thongTin[10] : ""; 
			pr = new Paragraph();
			pr.add(new Chunk("Nhập tại kho:", font9_bold));
			pr.add(new Chunk(" "+pBean.getNhaptaikho(),font9));
			pr.setSpacingBefore(3);
			pr.setSpacingAfter(5);
			//pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);
		
			//------------------------------------------------------------
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



					String[] spTitles1 = { "Mã vật tư", "Tên vật tư","TK", "ĐVT", "Số lượng", "Đơn giá","Thành tiền"};

					table.setWidths(crPhieuNhap1);
					//in tieu de
					for (int z = 0; z < spTitles1.length; z++) {
						cell = new PdfPCell(new Paragraph(spTitles1[z], font9_bold));
						cell.setPadding(3.0f);
						cell.setPaddingBottom(7);
						//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
					}
					Double tongTienHang = 0.0;
					Double tongVAT = 0.0;
					Double tongTienThanhToan = 0.0;
					tongVAT=Double.parseDouble(pBean.getVat());
					NumberFormat formatter1 = new DecimalFormat("#,###,###.###");
					List<ISanpham> spList = pBean.getSpList();	
					if (spList.size() > 0) {
						for (ISanpham sp : spList) {
							String maKho=sp.getMasanpham();
							String spTen=sp.getTensanpham();
							String donVi=sp.getDonvitinh();
							
							System.out.println("asssssssssssssssssss"+donVi);
						
							Double donGia = Double.parseDouble(sp.getDongia());
							Double thanhTien= sp.getThanhtien();
							tongTienHang += thanhTien;
						
							//---------------------
							String taikhoanvattu ="select Sohieutaikhoan from ERP_TAIKHOANKT where pk_seq= "+sp.getTaikhoanKTId();	
							ResultSet rsTkvt=db.get(taikhoanvattu);
							System.out.println("taikhoan 1: "+taikhoanvattu + "ncc : "+sp.getTaikhoanKTId());
							String tkvt="";
							if(rsTkvt!=null){
								try{
									while(rsTkvt.next())
									{
										tkvt=rsTkvt.getString("Sohieutaikhoan");
									}
									rsTkvt.close();
								}catch (Exception e){ e.printStackTrace(); }		
							}
							//-----------------
							String[] sp_data1={maKho, spTen, tkvt, donVi, /*formater.format(soLuong)*/"", /*formatter1.format(donGia)*/" ", formatter1.format(thanhTien)};
					
							for (int z = 0; z < sp_data1.length; z++) {
								cell = new PdfPCell(new Paragraph(sp_data1[z],font9));
								cell.setPadding(3.0f);
								cell.setPaddingBottom(7);

								if(z==1){
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								}
								else
									if(z==5 || z==6  ){
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
							cell = new PdfPCell(new Paragraph(spTongCong[z], font9));
							cell.setPadding(3.0f);
							cell.setPaddingBottom(7);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					        cell.setColspan(6);
					        cell.setBorder(4|8);
							cell.setBorderColorLeft(BaseColor.BLACK);
							table.addCell(cell);
							
							cell = new PdfPCell(new Paragraph(formatter1.format(soTien), font9));
							cell.setPadding(3.0f);
							cell.setPaddingBottom(7);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setBorder(4);
							cell.setBorder(8);
							cell.setBorderColorLeft(BaseColor.BLACK);
							table.addCell(cell);
						}
						cell.setBorderColorTop(BaseColor.BLACK);
						cell = new PdfPCell(new Paragraph("Tổng cộng tiền thanh toán", font9_bold));
						cell.setPadding(3.0f);
						cell.setPaddingBottom(7);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				        cell.setColspan(6);
						table.addCell(cell);

						cell = new PdfPCell(new Paragraph(formatter1.format(tongTienThanhToan), font9_bold));
						cell.setPadding(3.0f);
						cell.setPaddingBottom(7);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell);

					document.add(table);
					
					DocTien docTien = new DocTien();
					if(tongTienThanhToan<0){
						tongTienThanhToan=Math.abs(tongTienThanhToan);
						String tienchu = docTien.docTien((long)tongTienThanhToan.doubleValue());
						pr = new Paragraph("Bằng chữ: Âm "+tienchu.toLowerCase(), new Font(bf,9,Font.ITALIC));
					}
					else{
					String tienchu = docTien.docTien((long)tongTienThanhToan.doubleValue());
					pr = new Paragraph("Bằng chữ: "+tienchu, new Font(bf,9,Font.ITALIC));
					}
					pr.setAlignment(Element.ALIGN_RIGHT);
					document.add(pr);
				

					pr = new Paragraph("	   T/L Tổng giám đốc	                     			"+ "T/L Kế toán trưởng                         "+
							"Người lập phiếu		                Thủ kho", font10_bold);
					pr.setSpacingBefore(15);
					document.add(pr);
					pr = new Paragraph("Trưởng phòng Kế hoạch", font10_bold);
					pr.setAlignment(Element.ALIGN_LEFT);
					document.add(pr);
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
	
	public static void main(String[] args) {
		DocTien docTien = new DocTien();
		Double value = 96798240.0;
		String tienchu = docTien.docTien((long)value.doubleValue());
		System.out.println(tienchu);
		
	}
	
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private String getDate()
	{			
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		dateFormat.format(date);
		return dateFormat.format(date);
	}

  
}
