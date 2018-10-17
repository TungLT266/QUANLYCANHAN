package geso.traphaco.erp.servlets.phieuthanhtoan;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.doctien.DocTien;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuat;
import geso.traphaco.erp.beans.phieuthanhtoan.IErpDonmuahang_Giay;
import geso.traphaco.erp.beans.phieuthanhtoan.imp.ErpDonmuahang_Giay;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.VerticalAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabSettings;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import geso.traphaco.erp.beans.phieuthanhtoan.*;

public class ErpInGiayDeNghiThanhToanPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpInGiayDeNghiThanhToanPdfSvl() {
		super();

	}

	NumberFormat formatter = new DecimalFormat("#,###,###");
	NumberFormat formatter2 = new DecimalFormat("#,###,###.00");

	float CONVERT = 28.346457f; // =1cm

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nextJSP;
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			IErpDonmuahang_Giay dmhBean = new ErpDonmuahang_Giay(id);
			dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
			dmhBean.setUserId(userId); // phai co UserId truoc khi Init

			String task = request.getParameter("task");
			if(task == null)
				task = "";

			String duyetdn = request.getParameter("duyetdn");
			if(duyetdn == null)
				duyetdn = "";
			dmhBean.setDuyetdntt(duyetdn);

			String canduyet = request.getParameter("duyet");
			if(canduyet == null)
				canduyet = "1";
			dmhBean.setCanDuyet(canduyet);

			String chucnang = request.getParameter("chucnang");
			if(chucnang == null)
				chucnang = "";
			dmhBean.setChucnang(chucnang);

			String nccLoai = util.antiSQLInspection(request.getParameter("nccLoai"));
			if (nccLoai == null)
				nccLoai = "";
			session.setAttribute("nccLoai", nccLoai);
			dmhBean.setNccLOai(nccLoai);

			String loaihh = util.antiSQLInspection(request.getParameter("loaihh"));
			if (loaihh == null)
				loaihh = "2";
			session.setAttribute("lhhId", loaihh);
			dmhBean.setLoaihanghoa(loaihh);



			dmhBean.init();
			String action=util.getAction(querystring);	
			if(action.equals("excel")){

				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DeNghiThanhToan.xlsm");


				OutputStream out = response.getOutputStream();

				InPhieuDenghithanhtoan_Excel(out,id,dmhBean);

			}

			if(action.equals("exel1")){

				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DeNghiThanhToan.xlsm");


				OutputStream out = response.getOutputStream();

				InPhieuNhapMuaHangHoaDichVu_PDF(out,id,dmhBean);

			}
			else{
				if(task.equals("loai1"))	// xuat exell
				{
					try{
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition",
								" inline; filename=NhapMuaHangHoaDichVu.pdf");
						float PAGE_LEFT = 2.0f*CONVERT;
						float PAGE_RIGHT = 2.0f*CONVERT;
						float PAGE_TOP = 1.5f*CONVERT ;
						float PAGE_BOTTOM = 2.0f*CONVERT; 


						//định dạng khổ giấy
						Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
						ServletOutputStream outstream = response.getOutputStream();

						this.inPDF_MHDV(document, outstream, dmhBean, userId);
						document.close();
					}
					catch (Exception e) {
						e.printStackTrace();

						nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuThanhToanDisplay.jsp";
						session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
						session.setAttribute("lspId", dmhBean.getLoaispId());
						session.setAttribute("dmhBean", dmhBean);
						response.sendRedirect(nextJSP);
					}
				}
				else{
					try {

						System.out.println("Print");
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition",
								" inline; filename=DeNghiThanhToan.pdf");

						float PAGE_LEFT = 2.0f*CONVERT;
						float PAGE_RIGHT = 2.0f*CONVERT;
						float PAGE_TOP = 1.5f*CONVERT ;
						float PAGE_BOTTOM = 2.0f*CONVERT; 

						//định dạng khổ giấy
						Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
						ServletOutputStream outstream = response.getOutputStream();
						//if(dmhBean.getHtttId().equals("100000") || dmhBean.getHtttId().equals("100001")){
						this.inPDF(document, outstream, dmhBean, userId);
						document.close();
						/*}
					else{
						document.close();
						dmhBean.setMsg("Chỉ in khi có hình thức thanh toán là chuyển khoản hoặc tiền mặt");
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuThanhToanDisplay.jsp";
						session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
						session.setAttribute("lspId", dmhBean.getLoaispId());
						session.setAttribute("dmhBean", dmhBean);
						response.sendRedirect(nextJSP);
					}*/



					} catch (Exception e) {
						e.printStackTrace();

						nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuThanhToanDisplay.jsp";
						session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
						session.setAttribute("lspId", dmhBean.getLoaispId());
						session.setAttribute("dmhBean", dmhBean);
						response.sendRedirect(nextJSP);
					}
				}

			}
		}
	}


	public void InPhieuDenghithanhtoan_Excel(OutputStream out ,String id, IErpDonmuahang_Giay obj  )
	{ 
		try
		{
			dbutils db =new dbutils();



			Workbook workbook = new Workbook();
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\Denghithanhtoan6.xlsm");
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);


			Worksheet worksheet = workbook.getWorksheets().getSheet(0);
			Style tableHeaderStyle = worksheet.getCells().getCell("A1").getStyle();
			tableHeaderStyle.setBorderLine(BorderType.BOTTOM, 1);
			tableHeaderStyle.setBorderLine(BorderType.LEFT, 1);
			tableHeaderStyle.setBorderLine(BorderType.TOP, 1);
			tableHeaderStyle.setBorderLine(BorderType.RIGHT, 1);
			com.aspose.cells.Font font = tableHeaderStyle.getFont();
			font.setSize(10);
			font.setName("Times New Roman");
			font.setBold(true);
			tableHeaderStyle.setFont(font);
			tableHeaderStyle.setHAlignment(TextAlignmentType.CENTER);		

			Style tableContainStyle = worksheet.getCells().getCell("A7").getStyle();
			tableContainStyle.setBorderLine(BorderType.BOTTOM, 1);			
			tableContainStyle.setBorderLine(BorderType.LEFT, 1);
			tableContainStyle.setBorderLine(BorderType.TOP, 1);			
			tableContainStyle.setBorderLine(BorderType.RIGHT, 1);
			font = tableContainStyle.getFont();
			font.setSize(10);
			font.setName("Times New Roman");

			font.setBold(false);
			tableContainStyle.setFont(font);

			com.aspose.cells.Font fontBold = tableHeaderStyle.getFont();
			fontBold.setSize(10);
			fontBold.setName("Times New Roman");
			fontBold.setBold(true);



			ResultSet rsdv = obj.getDvthList();
			String donvi = "";
			if (rsdv != null) {
				try {
					while (rsdv.next()) {
						if (rsdv.getString("pk_seq").equals(obj.getDvthId())) {
							donvi = rsdv.getString("ten");
						}

					}
					rsdv.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}



			com.aspose.cells.Cell cell = worksheet.getCells().getCell("B5");
			cell.setValue(donvi);

			cell = worksheet.getCells().getCell("A11");
			Style style_boder = cell.getStyle();
			com.aspose.cells.Font font1 = new com.aspose.cells.Font();
			font1.setName("Times New Roman");
			font1.setSize(10);
			style_boder.setVAlignment(VerticalAlignmentType.CENTRED);
			style_boder.setFont(font1);

			//--------------------
			Style style_boder1 = cell.getStyle();
			com.aspose.cells.Font font11 = new com.aspose.cells.Font();
			font11.setName("Times New Roman");
			font11.setSize(10);
			font11.setBold(true);
			style_boder1.setVAlignment(VerticalAlignmentType.CENTRED);
			style_boder1.setFont(font11);
			//--------------------

			String[] tachNgay = obj.getNgaymuahang().split("-");

			String chuoi = "Ngày  " + tachNgay[2] + "  tháng  "
					+ tachNgay[1] + "  năm  " + tachNgay[0];

			cell = worksheet.getCells().getCell("C3");

			cell.setValue(chuoi);
			String querynhanvien = "";
			if(obj.getNvId() !=null && obj.getNvId().trim().length()>0)
			{
				querynhanvien = "select PK_SEQ,TEN from ERP_NHANVIEN where PK_SEQ ='"
						+ obj.getNvId() + "'";
			}else
			{
				querynhanvien = "select PK_SEQ,TEN from ERP_NHANVIEN where PK_SEQ ='"
						+ obj.getNguoidenghithanhtoan() + "'";
			}
			ResultSet rsnhanvien = db.get(querynhanvien);
			String tenNhanVien = "";
			if (rsnhanvien != null) {
				try {
					if (rsnhanvien.next()) {
						tenNhanVien = rsnhanvien.getString("TEN");
					}
					rsnhanvien.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			Cells  cells = worksheet.getCells();
			cell = worksheet.getCells().getCell("A7");
			cell.setValue("Họ và tên người đề nghị thanh toán: "+tenNhanVien);
			font.setSize(10);
			font.setName("Times New Roman");


			/*	hd = new Paragraph("Bộ phận (hoặc địa chỉ): " + donvi, new Font(bf,
				12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		document.add(hd);

		hd = new Paragraph("Nội dung thanh toán: " + obj.getLydoTT(), new Font(bf, 12,
				Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);*/

			cell = worksheet.getCells().getCell("A8");
			cell.setValue("Bộ phận (hoặc địa chỉ): "+donvi);
			font.setSize(10);
			font.setName("Times New Roman");
			Style style1 = cell.getStyle();
			cell = worksheet.getCells().getCell("A9");
			cell.setValue("Nội dung thanh toán: "+obj.getLydoTT());
			style1.setTextWrapped(true);
			font.setSize(10);
			cell.setStyle(style1);
			font.setName("Times New Roman");




			int stt=1;
			double tongtien=0;
			List<ISanpham> spList = obj.getSpList();
			double avat=0;

			// do qua list moi
			/*List<ISanpham> spListMoi = new ArrayList<ISanpham>();
			Laydanhsachsanpham(spList, spListMoi);*/
			
			
			
			System.out.println("Size nek : "+spList.size());
			for(int i = 0; i < spList.size(); i++) { 
				ISanpham sp = spList.get(i);
				avat = avat + Double.parseDouble(sp.getThanhtien().replaceAll(",", ""));


				Style titleStyle2 = cell.getStyle();
				titleStyle2.setHAlignment(TextAlignmentType.LEFT);
				font  = titleStyle2.getFont();
				titleStyle2.setVAlignment(TextAlignmentType.LEFT);

				font.setSize(10);
				font.setName("Times New Roman");
				//font.setSize(18);	
				titleStyle2.setFont(font);




				cell = worksheet.getCells().getCell("A"+(stt+11));
				cell.setStyle(style_boder);
				cell.setValue(stt);
				cell = worksheet.getCells().getCell("B"+(stt+11));
				cell.setStyle(style_boder);
				cell.setValue(sp.getMasanpham());
				cell = worksheet.getCells().getCell("C"+(stt+11));

				titleStyle2.setTextWrapped(true);
				cell.setValue(sp.getTensanpham());
				//			cell.setStyle(style_boder);
				cell.setStyle(titleStyle2);


				cell = worksheet.getCells().getCell("D"+(stt+11));
				cell.setStyle(style_boder);
				cells.merge(stt+11-1, 2, stt+11-1, 3);


				cell = worksheet.getCells().getCell("E"+(stt+11));
				cell.setStyle(style_boder);
				cell.setValue(sp.getThanhtien());

				cell = worksheet.getCells().getCell("E"+(stt+12));
				cell.setStyle(style_boder);
				cell.setValue(sp.getThanhtien());

				tongtien =tongtien+ Double.parseDouble(sp.getThanhtien().replaceAll(",", ""));
				System.out.println("Tong tien1 : "+tongtien);

				stt++;
			}
			cell = worksheet.getCells().getCell("A"+(stt+11));

			cell.setStyle(style_boder);

			cell = worksheet.getCells().getCell("B"+(stt+11));
			cell.setStyle(style_boder);

			cell = worksheet.getCells().getCell("C"+(stt+11));
			cell.setStyle(style_boder);

			cell = worksheet.getCells().getCell("D"+(stt+11));
			cell.setStyle(style_boder);

			cell.setStyle(style_boder1);
			cell.setValue("Tổng cộng" );
			font.setSize(10);
			font.setName("Times New Roman");
			cells.merge(stt+11-1, 0, stt+11-1, 3);
			cell.setStyle(style_boder);


			cell = worksheet.getCells().getCell("A1");
			cell.setValue("Đơn vị: CÔNG TY TNHH TRAPHACO HƯNG YÊN");
			font.setSize(9);
			font.setName("Times New Roman");

			cell = worksheet.getCells().getCell("A2");
			cell.setValue("Bộ phận: "+donvi);
			font.setSize(9);
			font.setName("Times New Roman");


			cell = worksheet.getCells().getCell("E"+(stt+11));
			cell.setStyle(style_boder1);
			Style tongcong = cell.getStyle();
			com.aspose.cells.Font fontTC = tongcong.getFont();
			fontTC.setBold(true);
			tongcong.setHAlignment(HorizontalAlignmentType.CENTRED);
			//		cells.merge(25, 0, 25, 3);
			cell.setValue(formatter.format(tongtien) );




			stt++;

			cell = worksheet.getCells().getCell("A"+(stt+11));

			ResultSet rshttt = obj.getHtttRs();
			String hinhthuc = "";
			if (rshttt != null) {
				try {
					while (rshttt.next()) {
						if (rshttt.getString("pk_seq").equals(obj.getHtttId())) {
							hinhthuc = rshttt.getString("ten");
						}
					}
					rshttt.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			Style titleStyle1 = cell.getStyle();
			font  = titleStyle1.getFont();
			font.setSize(10);
			font.setName("Times New Roman");
			//font.setSize(18);	
			titleStyle1.setFont(font);
			titleStyle1.setHAlignment(TextAlignmentType.LEFT);



			cells.merge(stt+11-1, 0, stt+11-1, 4);
			cell.setValue("Hình thức thanh toán: " +hinhthuc);
			cell.setStyle(titleStyle1);
			/*chunk = new Chunk(formatter.format(avat), new Font(bf, 12, Font.BOLD));
		chunk.setTextRise(0);
		hd.add(chunk);

			 */
			stt++;
			DocTien doctien = new DocTien();
			String tienchu = doctien.docTien((long)avat);



			cell = worksheet.getCells().getCell("A"+(stt+11));
			cells.merge(stt+11-1, 0, stt+11-1, 4);
			cell.setValue("Số tiền thanh toán: "+ formatter.format(avat) +"  ("+tienchu+").");
			cell.setStyle(titleStyle1);
			stt++;


			cell = worksheet.getCells().getCell("A"+(stt+11));
			cells.merge(stt+11-1, 0, stt+11-1, 4);
			cell.setValue("(Kèm theo ....... chứng từ gốc).");
			cell.setStyle(titleStyle1);
			stt++;

			if (obj.getHtttId().equals("100001")) {
				String chuTaiKhoan = "";
				String maSoThue = "";
				String soTaiKhoan = "";
				String tenNganHang = "";
				String tenChiNhanh = "";
				String queryNH = "";
				if(obj.getLoaidoituong().equals("0")){ //NHÀ CUNG CẤP
					queryNH = "select ncc.TEN, ncc.SOTAIKHOAN, ncc.MASOTHUE, nh.TEN  as TENNGANHANG , isnull(CN.TEN,'') as tenchinhanh "
							+ "from ERP_NHACUNGCAP ncc  \n" +
							"LEFT JOIN ERP_NGANHANG nh on nh.PK_SEQ = ncc.NGANHANG_FK \n" +
							"LEFT JOIN ERP_CHINHANH CN ON CN.PK_SEQ = ncc.ChiNhanh_FK \n" +
							"WHERE ncc.PK_SEQ = " + obj.getNCC();

				}else if(obj.getLoaidoituong().equals("1")){//nhân viên
					queryNH = "select nv.TEN, nv.SOTAIKHOAN, nh.TEN as TENNGANHANG, '' AS MASOTHUE,null as tenchinhanh from erp_nhanvien nv " +
							"left join ERP_NGANHANG nh on nv.NGANHANG_FK = nh.PK_SEQ " +
							"LEFT JOIN ERP_CHINHANH CN ON CN.PK_SEQ = nv.ChiNhanh_FK \n" +
							"WHERE nv.PK_SEQ = '" + obj.getNvId()+ "'" ;

				}
				else if(obj.getLoaidoituong().equals("2")){//khách hàng
					queryNH = "select kh.MASOTHUE, kh.SOTAIKHOAN, kh.TEN, '' AS TENNGANHANG,null as tenchinhanh from KHACHHANG kh " +
							"WHERE kh.PK_SEQ = '" + obj.getKhId()+ "'" ;

				}
				else  if(obj.getLoaidoituong().equals("3")){//CHI NHÁNH
					queryNH = "select npp.TEN, NPP.NGANHANG as TENNGANHANG , npp.MASOTHUE, npp.SOTAIKHOAN,null as tenchinhanh from nhaphanphoi npp where npp.PK_SEQ =  '" + obj.getChiNhanhId()+ "'" ;

				}
				System.out.println("----Query tai khoan: "  + queryNH);
				ResultSet rsncc = db.get(queryNH);
				if(rsncc != null){
					try {
						if(rsncc.next()){
							chuTaiKhoan = rsncc.getString("TEN");
							maSoThue = rsncc.getString("MASOTHUE");
							soTaiKhoan = rsncc.getString("SOTAIKHOAN");
							tenNganHang = rsncc.getString("TENNGANHANG").equals("null")?"":rsncc.getString("TENNGANHANG");
							tenChiNhanh = rsncc.getString("TENCHINHANH").equals("null")?"":rsncc.getString("TENCHINHANH");
						}
						rsncc.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				cell = worksheet.getCells().getCell("A"+(stt+11));
				cells.merge(stt+11-1, 0, stt+11-1, 4);
				cell.setValue("Chủ tài khoản : "+chuTaiKhoan);
				cell.setStyle(titleStyle1);
				stt++;
				cell = worksheet.getCells().getCell("A"+(stt+11));
				cells.merge(stt+11-1, 0, stt+11-1, 4);
				cell.setValue("Mã số thuế : "+maSoThue);
				cell.setStyle(titleStyle1);
				stt++;
				cell = worksheet.getCells().getCell("A"+(stt+11));
				cells.merge(stt+11-1, 0, stt+11-1, 4);
				cell.setValue("Số tài khoản : "+soTaiKhoan);
				cell.setStyle(titleStyle1);
				stt++;
				cell = worksheet.getCells().getCell("A"+(stt+11));
				cells.merge(stt+11-1, 0, stt+11-1, 4);
				cell.setValue("Tại : "+tenNganHang);
				cell.setStyle(titleStyle1);
				stt++;
				cell = worksheet.getCells().getCell("A"+(stt+11));
				cells.merge(stt+11-1, 0, stt+11-1, 4);
				cell.setValue("Chi nhánh : "+tenChiNhanh);
				cell.setStyle(titleStyle1);
				stt++;
			}

			// ===============================FOOTER==============================//
			String querykd = "select isLanhDaoKyDuyet  from ERP_MUAHANG where PK_SEQ =" + obj.getId();
			ResultSet rs = db.get(querykd);
			int n = 0;
			if(rs != null){
				try {
					if(rs.next()){
						n = rs.getInt("isLanhDaoKyDuyet");
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			else n =0;

			stt++;
			cell = worksheet.getCells().getCell("C"+(stt+11));
			cell.setStyle(titleStyle1);
			Style titleStyle = cell.getStyle();
			font  = titleStyle.getFont();
			font.setBold(true);
			font.setSize(13);
			font.setName("Times New Roman");
			//font.setSize(18);	
			titleStyle.setFont(font);
			titleStyle.setHAlignment(TextAlignmentType.CENTER);


			cell.setStyle(titleStyle);
			cells.merge(stt+11-1, 2, stt+11-1, 2);
			cell.setValue("PHỤ TRÁCH BỘ PHẬN");

			String hd=" ";
			if(n == 3)
				hd = "LÃNH ĐẠO CÔNG TY";

			cell = worksheet.getCells().getCell("A"+(stt+11));
			cells.merge(stt+11-1, 0, stt+11-1, 1);
			cell.setStyle(titleStyle);
			cell.setValue(hd);

			cell = worksheet.getCells().getCell("D"+(stt+11));
			cells.merge(stt+11-1, 3, stt+11-1,4);
			cell.setStyle(titleStyle);
			cell.setValue("NGƯỜI ĐỀ NGHỊ");


			cell = worksheet.getCells().getCell("D"+(stt+11+3));
			cells.merge(stt+11+2, 3, stt+11+2,4);
			//cell.setStyle(titleStyle);
			font = titleStyle.getFont();
			font.setSize(12);
			font.setBold(false);
			font.setItalic(true);
			titleStyle.setFont(font);
			cell.setStyle(titleStyle);
			cell.setValue(tenNhanVien);


			workbook.save(out);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{

		}
	}



	/*public void InPhieuNhapMuaHangHoaDichVu_PDF(OutputStream out ,String id, IErpDonmuahang_Giay obj  )
	{ 
		try
		{
			 dbutils db =new dbutils();



			Workbook workbook = new Workbook();
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\DenghithanhtoanPDF2.xlsm");
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);


			Worksheet worksheet = workbook.getWorksheets().getSheet(0);
			Style tableHeaderStyle = worksheet.getCells().getCell("A1").getStyle();
			tableHeaderStyle.setBorderLine(BorderType.BOTTOM, 1);
			tableHeaderStyle.setBorderLine(BorderType.LEFT, 1);
			tableHeaderStyle.setBorderLine(BorderType.TOP, 1);
			tableHeaderStyle.setBorderLine(BorderType.RIGHT, 1);
			com.aspose.cells.Font font = tableHeaderStyle.getFont();
			font.setSize(10);
			font.setName("Times New Roman");
			font.setBold(true);
			tableHeaderStyle.setFont(font);
			tableHeaderStyle.setHAlignment(TextAlignmentType.CENTER);		

			Style tableContainStyle = worksheet.getCells().getCell("A7").getStyle();
			tableContainStyle.setBorderLine(BorderType.BOTTOM, 1);			
			tableContainStyle.setBorderLine(BorderType.LEFT, 1);
			tableContainStyle.setBorderLine(BorderType.TOP, 1);			
			tableContainStyle.setBorderLine(BorderType.RIGHT, 1);
			font = tableContainStyle.getFont();
			font.setSize(10);
			font.setName("Times New Roman");

			font.setBold(false);
			tableContainStyle.setFont(font);

			com.aspose.cells.Font fontBold = tableHeaderStyle.getFont();
			fontBold.setSize(10);
			fontBold.setName("Times New Roman");
			fontBold.setBold(true);



			ResultSet rsdv = obj.getDvthList();
			String donvi = "";
			if (rsdv != null) {
				try {
					while (rsdv.next()) {
						if (rsdv.getString("pk_seq").equals(obj.getDvthId())) {
							donvi = rsdv.getString("ten");
						}

					}
					rsdv.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}



			com.aspose.cells.Cell cell = worksheet.getCells().getCell("B5");
			cell.setValue(donvi);

			cell = worksheet.getCells().getCell("A9");
			Style style_boder = cell.getStyle();
			com.aspose.cells.Font font1 = new com.aspose.cells.Font();
			font1.setName("Times New Roman");
			font1.setSize(10);
			style_boder.setVAlignment(VerticalAlignmentType.CENTRED);
			style_boder.setFont(font1);

			//--------------------
			Style style_boder1 = cell.getStyle();
			com.aspose.cells.Font font11 = new com.aspose.cells.Font();
			font11.setName("Times New Roman");
			font11.setSize(10);
			font11.setBold(true);
			style_boder1.setVAlignment(VerticalAlignmentType.CENTRED);
			style_boder1.setFont(font11);
			//--------------------

			String[] tachNgay = obj.getNgaymuahang().split("-");

			String chuoi = "Ngày  " + tachNgay[2] + "  tháng  "
					+ tachNgay[1] + "  năm  " + tachNgay[0];

			cell = worksheet.getCells().getCell("C3");

			cell.setValue(chuoi);


			String querynhanvien = "select mh.PK_SEQ,nv.ten from erp_muahang mh \n" +
			   "left join nhanvien nv on mh.nguoitao=nv.pk_seq\n" +
			   "where mh.PK_SEQ ='"+ obj.getId() + "'";
System.out.println("sadasdsasadsa"+querynhanvien);
ResultSet rsnhanvien = db.get(querynhanvien);
String tenNhanVien1 = "";
String tenNhanVien 	= "";
if (rsnhanvien != null) {
try {
if (rsnhanvien.next()) {
	tenNhanVien1 = rsnhanvien.getString("TEN")==null?"":rsnhanvien.getString("TEN");
}
rsnhanvien.close();
} catch (Exception e) {
// TODO: handle exception
e.printStackTrace();
}
}

String queryncc = "select PK_SEQ,TEN,Diachi from ERP_NHACUNGCAP where PK_SEQ ='"+ obj.getNCC() + "'";
ResultSet rsncc1 = db.get(queryncc);
String tenncc="";
if (rsncc1 != null) {
try {
if (rsncc1.next()) {
	tenncc = rsncc1.getString("TEN")==null?"":rsncc1.getString("TEN");
}
rsncc1.close();
} catch (Exception e) {
// TODO: handle exception
e.printStackTrace();
}
}


String querykh = "select PK_SEQ,TEN,Diachi from KHACHHANG where PK_SEQ ='"+ obj.getKhId() + "'";
ResultSet rskh = db.get(querykh);
String tenkh="";
if (rskh != null) {
try {
if (rskh.next()) {
	tenkh = rskh.getString("TEN")==null?"":rskh.getString("TEN");
}
rskh.close();
} catch (Exception e) {
// TODO: handle exception
e.printStackTrace();
}
}


String querynv = "select PK_SEQ,TEN,Diachi from erp_nhanvien where PK_SEQ ='"+ obj.getNvId() + "'";
ResultSet rsnv = db.get(querynv);
String tennv="";
if (rsnv != null) {
try {
if (rsnv.next()) {
	tennv = rsnv.getString("TEN")==null?"":rsnv.getString("TEN");
}
rsnv.close();
} catch (Exception e) {
// TODO: handle exception
e.printStackTrace();
}
}

String querydtk = "select PK_SEQ,TENDOITUONG from ERP_DOITUONGKHAC where PK_SEQ ='"+ obj.getDoiTuongKhacId() + "'";
System.out.println("Doi tuong khac"+querydtk);
ResultSet rsdtk = db.get(querydtk);
String tendtk="";
if (rsdtk != null) {
try {
if (rsdtk.next()) {
	tendtk = rsdtk.getString("TENDOITUONG")==null?"":rsdtk.getString("TENDOITUONG");
}
rsdtk.close();
} catch (Exception e) {
// TODO: handle exception
e.printStackTrace();
}
}

tenNhanVien=tenncc.trim()+tenkh.trim()+tendtk.trim()+tennv.trim();
		Cells  cells = worksheet.getCells();
		cell = worksheet.getCells().getCell("A5");
		cell.setValue("Họ và tên người mua hàng : "+tenNhanVien1);
		font.setSize(10);
		font.setName("Times New Roman");




		cell = worksheet.getCells().getCell("A6");
		cell.setValue("Đơn vị cung cấp : "+donvi);
		font.setSize(10);
		font.setName("Times New Roman");

		Style style1 = cell.getStyle();
		cell = worksheet.getCells().getCell("A7");
		cell.setValue("Địa chỉ : "+obj.getDiachiNCC());
		style1.setTextWrapped(true);
		font.setSize(10);
		cell.setStyle(style1);
		font.setName("Times New Roman");

		String tkco="";
		if(obj.getHtttId().equals("100000"))
			tkco="11110000";
		else if(obj.getHtttId().equals("100001"))
			tkco="";

		cells = worksheet.getCells();
		cell = worksheet.getCells().getCell("A8");
		cell.setValue("TK Có : "+tkco);
		font.setSize(10);
		font.setName("Times New Roman");




		int stt=1;
		double tongtien=0;
		List<ISanpham> spList = obj.getSpList();
		double avat=0;
		double bvat=0;
		double vat=0;
		System.out.println("Size nek : "+spList.size());
		for(int i = 0; i < spList.size(); i++) { 
			ISanpham sp = spList.get(i);
			avat = avat + Double.parseDouble(sp.getThanhtien().replaceAll(",", ""));


			String query_TKno="select TAIKHOAN_FK,* from ERP_NHOMCHIPHI where TEN='"+sp.getMasanpham()+"'";
			ResultSet rsTKno = db.get(query_TKno);				
			String tkNo = "";
			if (rsTKno != null) {
				try {
					if (rsTKno.next()) {
						tkNo = rsTKno.getString("TAIKHOAN_FK");
					}

					rsTKno.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			bvat = bvat + Double.parseDouble(sp.getThanhTienTruocThue().replaceAll(",", ""));
			vat = vat + Double.parseDouble(sp.getTienThue().replaceAll(",", ""));


			Style titleStyle2 = cell.getStyle();
			titleStyle2.setHAlignment(TextAlignmentType.LEFT);
			font  = titleStyle2.getFont();
			titleStyle2.setVAlignment(TextAlignmentType.LEFT);

			font.setSize(10);
			font.setName("Times New Roman");
			//font.setSize(18);	
			titleStyle2.setFont(font);




			cell = worksheet.getCells().getCell("A"+(stt+9));
			cell.setStyle(style_boder);
			cell.setValue(stt);
			cell = worksheet.getCells().getCell("B"+(stt+9));
			cell.setStyle(style_boder);
			cell.setValue(sp.getMasanpham());
			cell = worksheet.getCells().getCell("C"+(stt+9));

			titleStyle2.setTextWrapped(true);
			cell.setValue(sp.getTensanpham());
//			cell.setStyle(style_boder);
			cell.setStyle(titleStyle2);


			cell = worksheet.getCells().getCell("D"+(stt+9));
			cell.setStyle(style_boder);
			cells.merge(stt+9-1, 2, stt+9-1, 3);

			cell = worksheet.getCells().getCell("E"+(stt+9));
			cell.setStyle(style_boder);
			cell.setValue(tkNo);


			cell = worksheet.getCells().getCell("F"+(stt+9));
			cell.setStyle(style_boder);
			cell.setValue(sp.getThanhtien());

			cell = worksheet.getCells().getCell("F"+(stt+10));
			cell.setStyle(style_boder);
			cell.setValue(sp.getThanhtien());

			tongtien =tongtien+ Float.parseFloat(sp.getThanhtien().replaceAll(",", ""));


			stt++;
		}
		cell = worksheet.getCells().getCell("A"+(stt+9));

		cell.setStyle(style_boder);

		cell = worksheet.getCells().getCell("B"+(stt+9));
		cell.setStyle(style_boder);

		cell = worksheet.getCells().getCell("C"+(stt+9));
		cell.setStyle(style_boder);

		cell = worksheet.getCells().getCell("D"+(stt+9));
		cell.setStyle(style_boder);





		cell.setStyle(style_boder1);
		cell.setValue("Tổng cộng tiền hàng" );
		font.setSize(10);
		font.setName("Times New Roman");
		cells.merge(stt+9-1, 0, stt+9-1, 4);
		for (int i = 0; i < 5; i++) {
			cell = cells.getCell(stt+9-1,i);
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);	
		}
		cell.setStyle(style_boder);


		cell = worksheet.getCells().getCell("A"+(stt+10));

		cell.setStyle(style_boder);

		cell = worksheet.getCells().getCell("B"+(stt+10));
		cell.setStyle(style_boder);

		cell = worksheet.getCells().getCell("C"+(stt+10));
		cell.setStyle(style_boder);

		cell = worksheet.getCells().getCell("D"+(stt+10));
		cell.setStyle(style_boder);



		cell.setStyle(style_boder1);
		cell.setValue("Tiền thuế giá trị gia tăng" );
		font.setSize(10);
		font.setName("Times New Roman");
		cells.merge(stt+10-1, 0, stt+10-1, 4);

		for (int i = 0; i < 5; i++) {
			cell = cells.getCell(stt+10-1,i);
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);	
		}
		cell.setStyle(style_boder);


		cell = worksheet.getCells().getCell("A"+(stt+11));

		cell.setStyle(style_boder);

		cell = worksheet.getCells().getCell("B"+(stt+11));
		cell.setStyle(style_boder);

		cell = worksheet.getCells().getCell("C"+(stt+11));
		cell.setStyle(style_boder);

		cell = worksheet.getCells().getCell("D"+(stt+11));
		cell.setStyle(style_boder);

		cell.setStyle(style_boder1);
		cell.setValue("Tổng cộng tiền thanh toán" );
		font.setSize(10);
		font.setName("Times New Roman");
		cells.merge(stt+11-1, 0, stt+11-1, 4);
		for (int i = 0; i < 5; i++) {
			cell = cells.getCell(stt+11-1,i);
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);	
		}
		cell.setStyle(style_boder);


		cell = worksheet.getCells().getCell("F"+(stt+9));
		cell.setStyle(style_boder1);
		Style tongcong = cell.getStyle();
		com.aspose.cells.Font fontTC = tongcong.getFont();
		fontTC.setBold(true);
		tongcong.setHAlignment(HorizontalAlignmentType.CENTRED);
//		cells.merge(25, 0, 25, 3);
		cell.setValue(formatter.format(bvat) );

		cell = worksheet.getCells().getCell("F"+(stt+10));
		cell.setStyle(style_boder1);
		fontTC.setBold(true);
		tongcong.setHAlignment(HorizontalAlignmentType.CENTRED);
//		cells.merge(25, 0, 25, 3);
		cell.setValue(formatter.format(vat) );

		cell = worksheet.getCells().getCell("F"+(stt+11));
		cell.setStyle(style_boder1);
		fontTC.setBold(true);
		tongcong.setHAlignment(HorizontalAlignmentType.CENTRED);
//		cells.merge(25, 0, 25, 3);
		cell.setValue(formatter.format(avat) );




		stt++;

		cell = worksheet.getCells().getCell("A"+(stt+11));

		ResultSet rshttt = obj.getHtttRs();
		String hinhthuc = "";
		if (rshttt != null) {
			try {
				while (rshttt.next()) {
					if (rshttt.getString("pk_seq").equals(obj.getHtttId())) {
						hinhthuc = rshttt.getString("ten");
					}
				}
				rshttt.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		Style titleStyle1 = cell.getStyle();
		font  = titleStyle1.getFont();
		font.setSize(10);
		font.setName("Times New Roman");
		//font.setSize(18);	
		titleStyle1.setFont(font);
		titleStyle1.setHAlignment(TextAlignmentType.LEFT);



		cells.merge(stt+11-1, 0, stt+11-1, 4);
		cell.setValue("Hình thức thanh toán: " +hinhthuc);
		cell.setStyle(titleStyle1);
		chunk = new Chunk(formatter.format(avat), new Font(bf, 12, Font.BOLD));
		chunk.setTextRise(0);
		hd.add(chunk);


		stt++;
		DocTien doctien = new DocTien();
		String tienchu = doctien.docTien((long)avat);



		cell = worksheet.getCells().getCell("A"+(stt+11));
		cells.merge(stt+11-1, 0, stt+11-1, 4);
		cell.setValue("Số tiền thanh toán: "+ formatter.format(avat) +"  ("+tienchu+").");
		cell.setStyle(titleStyle1);
		stt++;


		cell = worksheet.getCells().getCell("A"+(stt+11));
		cells.merge(stt+11-1, 0, stt+11-1, 4);
		cell.setValue("(Kèm theo ....... chứng từ gốc).");
		cell.setStyle(titleStyle1);
		stt++;

		if (obj.getHtttId().equals("100001")) {
			String chuTaiKhoan = "";
			String maSoThue = "";
			String soTaiKhoan = "";
			String tenNganHang = "";
			String tenChiNhanh = "";
			String queryNH = "";
			if(obj.getLoaidoituong().equals("0")){ //NHÀ CUNG CẤP
				queryNH = "select ncc.TEN, ncc.SOTAIKHOAN, ncc.MASOTHUE, nh.TEN  as TENNGANHANG , isnull(CN.TEN,'') as tenchinhanh "
						+ "from ERP_NHACUNGCAP ncc  \n" +
				"LEFT JOIN ERP_NGANHANG nh on nh.PK_SEQ = ncc.NGANHANG_FK \n" +
				"LEFT JOIN ERP_CHINHANH CN ON CN.PK_SEQ = ncc.ChiNhanh_FK \n" +
				"WHERE ncc.PK_SEQ = " + obj.getNCC();

			}else if(obj.getLoaidoituong().equals("1")){//nhân viên
				queryNH = "select nv.TEN, nv.SOTAIKHOAN, nh.TEN as TENNGANHANG, '' AS MASOTHUE,null as tenchinhanh from erp_nhanvien nv " +
						"left join ERP_NGANHANG nh on nv.NGANHANG_FK = nh.PK_SEQ " +
						"LEFT JOIN ERP_CHINHANH CN ON CN.PK_SEQ = nv.ChiNhanh_FK \n" +
						"WHERE nv.PK_SEQ = '" + obj.getNvId()+ "'" ;

			}
			else if(obj.getLoaidoituong().equals("2")){//khách hàng
				queryNH = "select kh.MASOTHUE, kh.SOTAIKHOAN, kh.TEN, '' AS TENNGANHANG,null as tenchinhanh from KHACHHANG kh " +
				"WHERE kh.PK_SEQ = '" + obj.getKhId()+ "'" ;

			}
			else  if(obj.getLoaidoituong().equals("3")){//CHI NHÁNH
				queryNH = "select npp.TEN, NPP.NGANHANG as TENNGANHANG , npp.MASOTHUE, npp.SOTAIKHOAN,null as tenchinhanh from nhaphanphoi npp where npp.PK_SEQ =  '" + obj.getChiNhanhId()+ "'" ;

			}
			System.out.println("----Query tai khoan: "  + queryNH);
			ResultSet rsncc = db.get(queryNH);
			if(rsncc != null){
				try {
					if(rsncc.next()){
						chuTaiKhoan = rsncc.getString("TEN");
						maSoThue = rsncc.getString("MASOTHUE");
						soTaiKhoan = rsncc.getString("SOTAIKHOAN");
						tenNganHang = rsncc.getString("TENNGANHANG").equals("null")?"":rsncc.getString("TENNGANHANG");
						tenChiNhanh = rsncc.getString("TENCHINHANH").equals("null")?"":rsncc.getString("TENCHINHANH");
					}
					rsncc.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			cell = worksheet.getCells().getCell("A"+(stt+11));
			cells.merge(stt+11-1, 0, stt+11-1, 4);
			cell.setValue("Chủ tài khoản : "+chuTaiKhoan);
			cell.setStyle(titleStyle1);
			stt++;
			cell = worksheet.getCells().getCell("A"+(stt+11));
			cells.merge(stt+11-1, 0, stt+11-1, 4);
			cell.setValue("Mã số thuế : "+maSoThue);
			cell.setStyle(titleStyle1);
			stt++;
			cell = worksheet.getCells().getCell("A"+(stt+11));
			cells.merge(stt+11-1, 0, stt+11-1, 4);
			cell.setValue("Số tài khoản : "+soTaiKhoan);
			cell.setStyle(titleStyle1);
			stt++;
			cell = worksheet.getCells().getCell("A"+(stt+11));
			cells.merge(stt+11-1, 0, stt+11-1, 4);
			cell.setValue("Tại : "+tenNganHang);
			cell.setStyle(titleStyle1);
			stt++;
			cell = worksheet.getCells().getCell("A"+(stt+11));
			cells.merge(stt+11-1, 0, stt+11-1, 4);
			cell.setValue("Chi nhánh : "+tenChiNhanh);
			cell.setStyle(titleStyle1);
			stt++;
		}

		stt++;
		cell = worksheet.getCells().getCell("C"+(stt+11));
		cell.setStyle(titleStyle1);
		Style titleStyle = cell.getStyle();
		font  = titleStyle.getFont();
		font.setBold(true);
		font.setSize(13);
		font.setName("Times New Roman");
		//font.setSize(18);	
		titleStyle.setFont(font);
		titleStyle.setHAlignment(TextAlignmentType.CENTER);

		cell.setStyle(titleStyle);
		cells.merge(stt+11-1, 2, stt+11-1, 2);
		cell.setValue("PHỤ TRÁCH BỘ PHẬN");


		cell = worksheet.getCells().getCell("A"+(stt+11));
		cells.merge(stt+11-1, 0, stt+11-1, 1);
		cell.setStyle(titleStyle);
		cell.setValue("LÃNH ĐẠO CÔNG TY");

		cell = worksheet.getCells().getCell("D"+(stt+11));
		cells.merge(stt+11-1, 3, stt+11-1,4);
		cell.setStyle(titleStyle);
		cell.setValue("NGƯỜI ĐỀ NGHỊ");


		cell = worksheet.getCells().getCell("D"+(stt+11+3));
		cells.merge(stt+11+2, 3, stt+11+2,4);
		//cell.setStyle(titleStyle);
		font = titleStyle.getFont();
		font.setSize(12);
		font.setBold(false);
		font.setItalic(true);
		titleStyle.setFont(font);
		cell.setStyle(titleStyle);
		cell.setValue(tenNhanVien1);


			workbook.save(out);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{

		}
	}*/

	public void InPhieuNhapMuaHangHoaDichVu_PDF(OutputStream out ,String id, IErpDonmuahang_Giay obj  )
	{ 
		try
		{
			dbutils db =new dbutils();



			Workbook workbook = new Workbook();
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\DenghithanhtoanPDF2.xlsm");
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			Cell cell = null;
			worksheet.setName("PHIEUNHAPMUAHH,DV");
			Style style;

			Font font = new Font();		
			//Lay data--------------------
			//donvi
			ResultSet rsdv = obj.getDvthList();
			String donvi = "";
			if (rsdv != null) {
				try {
					while (rsdv.next()) {
						if (rsdv.getString("pk_seq").equals(obj.getDvthId())) {
							donvi = rsdv.getString("ten");
						}

					}
					rsdv.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			//Ngaymuahang
			String[] tachNgay = obj.getNgaymuahang().split("-");
			String chuoi = "Ngày  " + tachNgay[2] + "  tháng  "
					+ tachNgay[1] + "  năm  " + tachNgay[0];


			String querynhanvien = "select mh.PK_SEQ,nv.ten from erp_muahang mh \n" +
					"left join nhanvien nv on mh.nguoitao=nv.pk_seq\n" +
					"where mh.PK_SEQ ='"+ obj.getId() + "'";
			System.out.println("sadasdsasadsa"+querynhanvien);
			ResultSet rsnhanvien = db.get(querynhanvien);
			String tenNhanVien1 = "";
			String tenNhanVien 	= "";
			if (rsnhanvien != null) {
				try {
					if (rsnhanvien.next()) {
						tenNhanVien1 = rsnhanvien.getString("TEN")==null?"":rsnhanvien.getString("TEN");
					}
					rsnhanvien.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			String queryncc = "select PK_SEQ,TEN,Diachi from ERP_NHACUNGCAP where PK_SEQ ='"+ obj.getNCC() + "'";
			ResultSet rsncc1 = db.get(queryncc);
			String tenncc="";
			if (rsncc1 != null) {
				try {
					if (rsncc1.next()) {
						tenncc = rsncc1.getString("TEN")==null?"":rsncc1.getString("TEN");
					}
					rsncc1.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}


			String querykh = "select PK_SEQ,TEN,Diachi from KHACHHANG where PK_SEQ ='"+ obj.getKhId() + "'";
			ResultSet rskh = db.get(querykh);
			String tenkh="";
			if (rskh != null) {
				try {
					if (rskh.next()) {
						tenkh = rskh.getString("TEN")==null?"":rskh.getString("TEN");
					}
					rskh.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}


			String querynv = "select PK_SEQ,TEN,Diachi from erp_nhanvien where PK_SEQ ='"+ obj.getNvId() + "'";
			ResultSet rsnv = db.get(querynv);
			String tennv="";
			if (rsnv != null) {
				try {
					if (rsnv.next()) {
						tennv = rsnv.getString("TEN")==null?"":rsnv.getString("TEN");
					}
					rsnv.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			String querydtk = "select PK_SEQ,TENDOITUONG from ERP_DOITUONGKHAC where PK_SEQ ='"+ obj.getDoiTuongKhacId() + "'";
			System.out.println("Doi tuong khac"+querydtk);
			ResultSet rsdtk = db.get(querydtk);
			String tendtk="";
			if (rsdtk != null) {
				try {
					if (rsdtk.next()) {
						tendtk = rsdtk.getString("TENDOITUONG")==null?"":rsdtk.getString("TENDOITUONG");
					}
					rsdtk.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			tenNhanVien=tenncc.trim()+tenkh.trim()+tendtk.trim()+tennv.trim();

			String tkco="";
			if(obj.getHtttId().equals("100000"))
				tkco="11110000";
			else if(obj.getHtttId().equals("100001"))
				tkco="";

			//Hinh thuc tt
			ResultSet rshttt = obj.getHtttRs();
			String hinhthuc = "";
			if (rshttt != null) {
				try {
					while (rshttt.next()) {
						if (rshttt.getString("pk_seq").equals(obj.getHtttId())) {
							hinhthuc = rshttt.getString("ten");
						}
					}
					rshttt.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			//Lấy đối tượng
			String query =     "\n SELECT distinct NV.TEN as NGUOITAO, MUAHANG.PK_SEQ AS MHID, NGAYMUA AS NGAY, DVTH.TEN AS DVTH, "+
					"\n CASE WHEN MUAHANG.NHACUNGCAP_FK IS NOT NULL THEN NCC.TEN WHEN MUAHANG.NHANVIEN_FK IS NOT NULL THEN NV1.TEN WHEN MUAHANG.DOITUONGKHAC_FK IS NOT NULL THEN DTK.TENDOITUONG  ELSE KH.TEN END AS NCC, MUAHANG.TONGTIENAVAT, " +
					//							"\n case MUAHANG.LOAIHANGHOA_FK when '0' then SP.MA when '1' then TS.ma else TKKT.SOHIEUTAIKHOAN+' - ' + TKKT.TENTAIKHOAN + '-' + CP.TEN end as MA, " +
					//							"\n case MUAHANG.LOAIHANGHOA_FK when '0' then SP.TEN else MUAHANG_SP.diengiai end AS SP,  " +
					//							"\n MUAHANG_SP.SOLUONG, isnull(MUAHANG_SP.DONGIA, 0) as DONGIA, isnull(MUAHANG_SP.THANHTIEN, 0) as THANHTIEN, " +
					"\nisnull(MUAHANG.VUOTNGANSACH, 0) as vuotNganSach, " +
					"\n MUAHANG.SOPO as SOCHUNGTU, MUAHANG.TRANGTHAI TRANGTHAI, "+
					"\n ISNULL(MUAHANG.ISTP,0) ISTP, ISNULL(MUAHANG.ISKTV, 0) ISKTV, ISNULL(MUAHANG.ISKTT, 0) ISKTT, ISNULL(MUAHANG.ISDACHOT,0) ISDACHOT "+
					"\n FROM ERP_MUAHANG MUAHANG " +
					"\n INNER JOIN NHANVIEN NV ON NV.PK_SEQ = MUAHANG.NGUOITAO " +
					//							"\n INNER JOIN ERP_MUAHANG_SP MUAHANG_SP ON MUAHANG_SP.MUAHANG_FK = MUAHANG.PK_SEQ  " +
					"\n INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = MUAHANG.DONVITHUCHIEN_FK  " +
					"\n LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MUAHANG.NHACUNGCAP_FK " +	
					"\n LEFT JOIN ERP_DOITUONGKHAC DTK ON DTK.PK_SEQ = MUAHANG.DOITUONGKHAC_FK   " +	
					"\n LEFT JOIN ERP_NHANVIEN NV1 ON NV1.PK_SEQ = MUAHANG.NHANVIEN_FK   " +	
					"\n LEFT JOIN NHAPHANPHOI KH ON MUAHANG.KHACHHANG_FK = KH.PK_SEQ AND ISKHACHHANG = 1 AND KH.TRANGTHAI = '1' " +	
					//							"\n LEFT JOIN SANPHAM SP ON SP.PK_SEQ = MUAHANG_SP.SANPHAM_FK  " +
					//							"\n LEFT join ERP_TAISANCODINH TS on TS.pk_seq = MUAHANG_SP.TAISAN_FK " +
					//							"\n LEFT JOIN ERP_NHOMCHIPHI CP on CP.PK_SEQ = MUAHANG_SP.CHIPHI_FK " + 
					//							"\n LEFT JOIN ERP_TAIKHOANKT TKKT ON TKKT.SOHIEUTAIKHOAN = CP.TAIKHOAN_FK "+
					"\n WHERE isnull(MUAHANG.ISDACHOT, 0) = '1' AND MUAHANG.ISDNTT = 1" +
					"\n AND MUAHANG.congty_fk = '" + obj.getCongtyId() + "' " +
					//							"\n AND MUAHANG.LOAIHANGHOA_FK = 2 " +
					"\nand MUAHANG.TYPE = '1' "+ // CHỈ LẤY ĐỀ NGHỊ THANH TOÁN							
					"\n AND MUAHANG.TRANGTHAI IN ( 0 , 1 ) " + // CẤP CUỐI CÙNG CHƯA DUYỆT					

							//Nhân viên là trưởng phòng nào lấy ra những đề nghị thanh toán chưa duyệt của phòng đó
							"\n AND ((DVTH.PK_SEQ IN (SELECT DVTH_FK from ERP_CHUCDANH where TRANGTHAI = 1 and NHANVIEN_FK =  " + obj.getUserId() + " AND ISNULL(isTP, 0) = 1 ) AND MUAHANG.ISTP = 0) " +
							"\n or " +
							//Kế toán gán mã chi phí cho những đề nghị thanh toán chưa gán mã chi phí
							"\n ( " + obj.getUserId()+ " in (select nhanVien_FK from erp_chucDanh where trangThai = 1 and isktv = 1) AND ISNULL(MUAHANG.ISKTV, 0) = 0 AND ISNULL(MUAHANG.ISTP, 0) = 1)) " +
							"\n and MUAHANG.PK_SEQ="+obj.getId();

			System.out.println("quert 12: "+query);
			ResultSet rsdt = db.get(query);
			String doituong = "";
			if (rsdt != null) {
				try {
					while (rsdt.next()) {
						doituong = rsdt.getString("NCC");

					}
					rsdt.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}


			//--Xuat excel		----------------------------		

			cell=cells.getCell("A5");
			this.getCellStyle(cell,true ,false,false, chuoi);
			
			cell=cells.getCell("F5");
			this.getCellStyle(cell,false ,true,false, "Số");

			cell=cells.getCell("C7");
			this.getCellStyle(cell,false ,false,false, tenNhanVien1);

			cell=cells.getCell("C8");
			this.getCellStyle(cell,false ,false,false, doituong);//donvi

			cell=cells.getCell("C9");
			this.getCellStyle(cell,false ,false, true,obj.getDiachiNCC());

			cell=cells.getCell("C10");
			this.getCellStyle(cell,false ,false,false, tkco);

			//Bảng dữ liệu ---------------
			int stt=1;
			int index = 12;
			double tongtien=0;
			List<ISanpham> spList = obj.getSpList();
			
			// do qua list moi
			/*List<ISanpham> spListMoi = new ArrayList<ISanpham>();
			Laydanhsachsanpham(spList, spListMoi);*/
			
			
			double avat=0;
			double bvat=0;
			double vat=0;
			System.out.println("Size nek : "+spList.size());
			for(int i = 0; i < spList.size(); i++) { 
				ISanpham sp = spList.get(i);
				avat = avat + Double.parseDouble(sp.getThanhtien().replaceAll(",", ""));


				String query_TKno="select TAIKHOAN_FK,* from ERP_NHOMCHIPHI where TEN='"+sp.getMasanpham()+"'";
				ResultSet rsTKno = db.get(query_TKno);				
				String tkNo = "";
				if (rsTKno != null) {
					try {
						if (rsTKno.next()) {
							tkNo = rsTKno.getString("TAIKHOAN_FK");
						}

						rsTKno.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				System.out.println("id ma don hang: "+obj.getId());

				bvat = bvat + Double.parseDouble(sp.getThanhTienTruocThue().replaceAll(",", ""));
				vat = vat + Double.parseDouble(sp.getTienThue().replaceAll(",", ""));

				///-----------
				//String []arr={stt+"",sp.getMasanpham(),"",sp.getTensanpham(),tkNo,sp.getThanhtien()};
				String []arr={stt+"",sp.getMasanpham(),"",sp.getTensanpham(),tkNo,sp.getThanhTienTruocThue()};
				for(int j = 0; j < arr.length; j++)
				{
					cell=cells.getCell(index,j);
					style=cell.getStyle();
					cell.setStyle(style);

					// gop dong cho ten
					if(j==2){
						cells.merge(index, 2, index, 3);
						j=j+1;
						for (int k = 2; k < 4; k++) {
							cell = cells.getCell(index,k);
							this.getCellStyle_border(cell, false,false,true, HorizontalAlignmentType.LEFT,arr[j]);	
						}
					}
					else
						if(j==5)
							this.getCellStyle_border(cell,false,false,false, HorizontalAlignmentType.RIGHT,arr[j]);
						else
							this.getCellStyle_border(cell,false,false,false, HorizontalAlignmentType.CENTRED,arr[j]);
				}
				tongtien =tongtien+ Float.parseFloat(sp.getThanhtien().replaceAll(",", ""));

				stt++;
				index++;
			}
			System.out.println("vao cai 2" +index);

			//Doc tien + tong thanh tien -----------------------------
			Double doctienchu = 0.0;
			doctienchu=Double.parseDouble(obj.getTongtiensauVat().replaceAll(",", ""));
			DocTien docTien = new DocTien();
			String tienchu = docTien.docTien((long)doctienchu.doubleValue());
			
			
			
			System.out.println("vao cai 2" +index);
			index++;
			//Tong Tien hang
			cell = cells.getCell("A"+index);
			cells.merge(index-1, 0, index-1, 4);
			for (int j = 0; j < 5; j++) {
				cell = cells.getCell(index-1,j);
				this.getCellStyle_border(cell, false, false, false, HorizontalAlignmentType.LEFT, "Tổng cộng tiền hàng");	
			}
			cell = cells.getCell("F"+index);
			this.getCellStyle_border(cell, false, false, false, HorizontalAlignmentType.RIGHT, formatter.format(Double.parseDouble(obj.getTongtienchuaVat().replaceAll(",",""))) );
			++index;

			//Tien thue gia tri gia tang
			cell = cells.getCell("A"+index);
			cells.merge(index-1, 0, index-1, 4);
			for (int j = 0; j < 5; j++) {
				cell = cells.getCell(index-1,j);
				this.getCellStyle_border(cell, false, false, false, HorizontalAlignmentType.LEFT, "Tiền thuế giá trị gia tăng");	
			}		
			cell = cells.getCell("F"+index);
			this.getCellStyle_border(cell, false, false, false, HorizontalAlignmentType.RIGHT, formatter.format(vat) );
			++index;

			//Tong tien thanh toan
			cell = cells.getCell("A"+index);
			cells.merge(index-1, 0, index-1, 4);
			for (int j = 0; j < 5; j++) {
				cell = cells.getCell(index-1,j);
				this.getCellStyle_border(cell, false, true, false, HorizontalAlignmentType.LEFT, "Tổng cộng tiền thanh toán");	
			}		
			cell = cells.getCell("F"+index);
			this.getCellStyle_border(cell, false, true, false, HorizontalAlignmentType.RIGHT, formatter.format(Double.parseDouble(obj.getTongtiensauVat().replaceAll(",",""))) );
			++index;

			//Bang chu
			cell = cells.getCell("A"+index);
			this.getCellStyle(cell, true, false, false,"Bằng chữ: "+tienchu);
			++index;
			++index;
			System.out.println("Vao 3"+index);

			//FOOTER ------------------------
			if (obj.getHtttId().equals("100001")) {
				String chuTaiKhoan = "";
				String maSoThue = "";
				String soTaiKhoan = "";
				String tenNganHang = "";
				String tenChiNhanh = "";
				String queryNH = "";
				if(obj.getLoaidoituong().equals("0")){ //NHÀ CUNG CẤP
					queryNH = "select ncc.TEN, ncc.SOTAIKHOAN, ncc.MASOTHUE, nh.TEN  as TENNGANHANG , isnull(CN.TEN,'') as tenchinhanh "
							+ "from ERP_NHACUNGCAP ncc  \n" +
							"LEFT JOIN ERP_NGANHANG nh on nh.PK_SEQ = ncc.NGANHANG_FK \n" +
							"LEFT JOIN ERP_CHINHANH CN ON CN.PK_SEQ = ncc.ChiNhanh_FK \n" +
							"WHERE ncc.PK_SEQ = " + obj.getNCC();

				}else if(obj.getLoaidoituong().equals("1")){//nhân viên
					queryNH = "select nv.TEN, nv.SOTAIKHOAN, nh.TEN as TENNGANHANG, '' AS MASOTHUE,null as tenchinhanh from erp_nhanvien nv " +
							"left join ERP_NGANHANG nh on nv.NGANHANG_FK = nh.PK_SEQ " +
							"LEFT JOIN ERP_CHINHANH CN ON CN.PK_SEQ = nv.ChiNhanh_FK \n" +
							"WHERE nv.PK_SEQ = '" + obj.getNvId()+ "'" ;

				}
				else if(obj.getLoaidoituong().equals("2")){//khách hàng
					queryNH = "select kh.MASOTHUE, kh.SOTAIKHOAN, kh.TEN, '' AS TENNGANHANG,null as tenchinhanh from KHACHHANG kh " +
							"WHERE kh.PK_SEQ = '" + obj.getKhId()+ "'" ;

				}
				else  if(obj.getLoaidoituong().equals("3")){//CHI NHÁNH
					queryNH = "select npp.TEN, NPP.NGANHANG as TENNGANHANG , npp.MASOTHUE, npp.SOTAIKHOAN,null as tenchinhanh from nhaphanphoi npp where npp.PK_SEQ =  '" + obj.getChiNhanhId()+ "'" ;

				}
				System.out.println("----Query tai khoan: "  + queryNH);
				ResultSet rsncc = db.get(queryNH);
				if(rsncc != null){
					try {
						if(rsncc.next()){
							chuTaiKhoan = rsncc.getString("TEN");
							maSoThue = rsncc.getString("MASOTHUE");
							soTaiKhoan = rsncc.getString("SOTAIKHOAN");
							tenNganHang = rsncc.getString("TENNGANHANG").equals("null")?"":rsncc.getString("TENNGANHANG");
							tenChiNhanh = rsncc.getString("TENCHINHANH").equals("null")?"":rsncc.getString("TENCHINHANH");
						}
						rsncc.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
			/*cell = worksheet.getCells().getCell("A"+(stt+11));
			cells.merge(stt+11-1, 0, stt+11-1, 4);
			cell.setValue("Chủ tài khoản : "+chuTaiKhoan);
			cell.setStyle(titleStyle1);
			stt++;
			cell = worksheet.getCells().getCell("A"+(stt+11));
			cells.merge(stt+11-1, 0, stt+11-1, 4);
			cell.setValue("Mã số thuế : "+maSoThue);
			cell.setStyle(titleStyle1);
			stt++;
			cell = worksheet.getCells().getCell("A"+(stt+11));
			cells.merge(stt+11-1, 0, stt+11-1, 4);
			cell.setValue("Số tài khoản : "+soTaiKhoan);
			cell.setStyle(titleStyle1);
			stt++;
			cell = worksheet.getCells().getCell("A"+(stt+11));
			cells.merge(stt+11-1, 0, stt+11-1, 4);
			cell.setValue("Tại : "+tenNganHang);
			cell.setStyle(titleStyle1);
			stt++;
			cell = worksheet.getCells().getCell("A"+(stt+11));
			cells.merge(stt+11-1, 0, stt+11-1, 4);
			cell.setValue("Chi nhánh : "+tenChiNhanh);
			cell.setStyle(titleStyle1);
			stt++;
		}


		stt++;
		cell = worksheet.getCells().getCell("C"+(stt+11));
		cell.setStyle(titleStyle1);
		Style titleStyle = cell.getStyle();
		font  = titleStyle.getFont();
		font.setBold(true);
		font.setSize(13);
		font.setName("Times New Roman");
		//font.setSize(18);	
		titleStyle.setFont(font);
		titleStyle.setHAlignment(TextAlignmentType.CENTER);

		cell.setStyle(titleStyle);
		cells.merge(stt+11-1, 2, stt+11-1, 2);
		cell.setValue("PHỤ TRÁCH BỘ PHẬN");


		cell = worksheet.getCells().getCell("A"+(stt+11));
		cells.merge(stt+11-1, 0, stt+11-1, 1);
		cell.setStyle(titleStyle);
		cell.setValue("LÃNH ĐẠO CÔNG TY");

		cell = worksheet.getCells().getCell("D"+(stt+11));
		cells.merge(stt+11-1, 3, stt+11-1,4);
		cell.setStyle(titleStyle);
		cell.setValue("NGƯỜI ĐỀ NGHỊ");


		cell = worksheet.getCells().getCell("D"+(stt+11+3));
		cells.merge(stt+11+2, 3, stt+11+2,4);
		//cell.setStyle(titleStyle);
		font = titleStyle.getFont();
		font.setSize(12);
		font.setBold(false);
		font.setItalic(true);
		titleStyle.setFont(font);
		cell.setStyle(titleStyle);
		cell.setValue(tenNhanVien1);*/

			///-------------------chữ ký
			System.out.println("vao 344: "+index);
			cell = cells.getCell("A"+index);
			cells.merge(index-1, 0, index-1, 1);
			for (int j = 0; j < 2; j++) {
				cell = cells.getCell(index-1,j);
				this.getCellStyle(cell, false, true, false,HorizontalAlignmentType.CENTRED ,"T/L Tổng giám đốc");	
			}

			cell = cells.getCell("C"+index);
			this.getCellStyle(cell, false, true, false, HorizontalAlignmentType.CENTRED,"T/L Kế toán trưởng");	

			cell = cells.getCell("D"+index);
			this.getCellStyle(cell, false, true, false,HorizontalAlignmentType.CENTRED ,"Người lập phiếu");	

			cell = cells.getCell("E"+index);
			this.getCellStyle(cell, false, true, false,HorizontalAlignmentType.CENTRED ,"Người sử dụng");	

			cell = cells.getCell("F"+index);
			this.getCellStyle(cell, false, true, false,HorizontalAlignmentType.CENTRED, "Người mua hàng");	

			++index;
			System.out.println("vao 344: "+index);
			cell = cells.getCell("A"+index);
			cells.merge(index-1, 0, index-1, 1);
			for (int j = 0; j < 2; j++) {
				cell = cells.getCell(index-1,j);
				this.getCellStyle(cell, true, false, false, HorizontalAlignmentType.CENTRED,"(Ký, họ tên)");	
			}

			cell = cells.getCell("C"+index);
			this.getCellStyle(cell, true, false, false, HorizontalAlignmentType.CENTRED,"(Ký, họ tên)");	

			cell = cells.getCell("D"+index);
			this.getCellStyle(cell, true, false, false, HorizontalAlignmentType.CENTRED,"(Ký, họ tên)");	

			cell = cells.getCell("E"+index);
			this.getCellStyle(cell, true, false, false, HorizontalAlignmentType.CENTRED,"(Ký, họ tên)");	

			cell = cells.getCell("F"+index);
			this.getCellStyle(cell, true, false, false, HorizontalAlignmentType.CENTRED,"(Ký, họ tên)");	

			workbook.save(out);

			fstream.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{

		}
	}


	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	private void inPDF_MHDV(Document document, ServletOutputStream outstream,
			IErpDonmuahang_Giay obj, String userID) throws IOException {

		geso.traphaco.erp.db.sql.dbutils db = new geso.traphaco.erp.db.sql.dbutils();
		Utility util = new Utility();

		try {

			String tendtk="";
			String tennv="";
			String tenncc="";
			String tenkh="";
			ResultSet rsdv = obj.getDvthList();
			String donvi = "";
			if (rsdv != null) {
				try {
					while (rsdv.next()) {
						if (rsdv.getString("pk_seq").equals(obj.getDvthId())) {
							donvi = rsdv.getString("ten");
						}

					}
					rsdv.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			String querynhanvien = "select mh.PK_SEQ,nv.ten from erp_muahang mh \n" +
					"left join nhanvien nv on mh.nguoitao=nv.pk_seq\n" +
					"where mh.PK_SEQ ='"+ obj.getId() + "'";
			System.out.println("sadasdsasadsa"+querynhanvien);
			ResultSet rsnhanvien = db.get(querynhanvien);
			String tenNhanVien1 = "";
			String tenNhanVien 	= "";
			if (rsnhanvien != null) {
				try {
					if (rsnhanvien.next()) {
						tenNhanVien1 = rsnhanvien.getString("TEN")==null?"":rsnhanvien.getString("TEN");
					}
					rsnhanvien.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			if(obj.getNCC().trim().length()>0)
			{

			String queryncc = "select PK_SEQ,TEN,Diachi from ERP_NHACUNGCAP where PK_SEQ ='"+ obj.getNCC() + "'";
			ResultSet rsncc = db.get(queryncc);
	
			if (rsncc != null) {
				try {
					if (rsncc.next()) {
						tenncc = rsncc.getString("TEN")==null?"":rsncc.getString("TEN");
					}
					rsncc.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			}

			if(obj.getKhId().trim().length()>0)
			{
			String querykh = "select PK_SEQ,TEN,Diachi from KHACHHANG where PK_SEQ ='"+ obj.getKhId() + "'";
			ResultSet rskh = db.get(querykh);

			if (rskh != null) {
				try {
					if (rskh.next()) {
						tenkh = rskh.getString("TEN")==null?"":rskh.getString("TEN");
					}
					rskh.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			}

			if(obj.getNvId().trim().length()>0)
			{
			String querynv = "select PK_SEQ,TEN,Diachi from erp_nhanvien where PK_SEQ ='"+ obj.getNvId() + "'";
			ResultSet rsnv = db.get(querynv);
	
			if (rsnv != null) {
				try {
					if (rsnv.next()) {
						tennv = rsnv.getString("TEN")==null?"":rsnv.getString("TEN");
					}
					rsnv.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			}
			
			if(obj.getDoiTuongKhacId().trim().length()>0)
			{

			String querydtk = "select PK_SEQ,TENDOITUONG from ERP_DOITUONGKHAC where PK_SEQ ='"+ obj.getDoiTuongKhacId() + "'";
			System.out.println("Doi tuong khac"+querydtk);
			ResultSet rsdtk = db.get(querydtk);
	
			if (rsdtk != null) {
				try {
					if (rsdtk.next()) {
						tendtk = rsdtk.getString("TENDOITUONG")==null?"":rsdtk.getString("TENDOITUONG");
					}
					rsdtk.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			}

			tenNhanVien=tenncc.trim()+tenkh.trim()+tendtk.trim()+tennv.trim();

			//-----------------------------------
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
			img.setAbsolutePosition(3f * CONVERT, document.getPageSize().getHeight() -1.8f * CONVERT);//1.2
			document.add(img);

			PdfPCell cell;
			cell = new PdfPCell();
			Paragraph p = new Paragraph();
			p = new Paragraph("CÔNG TY TNHH TRAPHACO HƯNG YÊN", font12);
			cell.setBorder(0);

			//p.add(new Chunk("CÔNG TY CP TRAPHACO", font12)); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
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

			pr = new Paragraph("PHIẾU NHẬP MUA HÀNG HÓA, DỊCH VỤ", new Font(bf, 16, Font.BOLD));
			pr.setSpacingBefore(10);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_CENTER);
			document.add(pr);

			//String[] thongTin = pBean.initPNK_StringArray();
			String ThongTin= util.convertDate(obj.getNgaymuahang());
			pr = new Paragraph("                   Ngày "+ThongTin.substring(8,10)+" tháng "+ThongTin.substring(5,7)+" năm "+ThongTin.substring(0,4) , new Font(bf, 13, Font.ITALIC));
			pr.setSpacingBefore(0);
			/*pr.setSpacingAfter(10);*/
			pr.setAlignment(Element.ALIGN_CENTER);
			document.add(pr);


			pr = new Paragraph("", new Font(bf, 13, Font.ITALIC));
			pr.setSpacingBefore(0);
			/*pr.setSpacingAfter(10);*/
			pr.setAlignment(Element.ALIGN_CENTER);
			document.add(pr);

			String sott="";
			ResultSet rssott = db.get("select ISNULL(SOTTDUYET,0) AS SOTTDUYET from ERP_MUAHANG where PK_SEQ = "+obj.getId());
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

			pr = new Paragraph("Số: "+sott , new Font(bf, 13, Font.ITALIC));
			pr.setSpacingBefore(0);
			/*pr.setSpacingAfter(10);*/
			pr.setAlignment(Element.ALIGN_RIGHT);
			document.add(pr);




			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			float[] withs1 = {30f,70f };
			table.setWidths(withs1);
			table.setSpacingBefore(3.0f);
			table.setSpacingAfter(4.0f);

			cell = new PdfPCell(new Paragraph("Họ và tên người mua hàng:",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(""+tenNhanVien1,font12));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);

			table.addCell(cell);


			cell = new PdfPCell(new Paragraph("Đơn vị cung cấp: ",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);

			table.addCell(cell);


			cell = new PdfPCell(new Paragraph(""+tenNhanVien,font12));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);

			table.addCell(cell);







			if(obj.getLoaidoituong().equals("0")) 
				donvi=obj.getDiachiNCC();

			System.out.println("diachi: "+donvi);



			cell = new PdfPCell(new Paragraph("Địa chỉ:  : ",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);


			cell = new PdfPCell(new Paragraph(""+donvi,font12));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);


			//-------------
			String tkco="";
			String ID="";
			String ldt="";
			String sohieutaikhoan="";
			if(obj.getHtttId().equals("100000"))
				sohieutaikhoan="11110000";
			else if(obj.getHtttId().equals("100001"))
				sohieutaikhoan="";
			else {
				if(obj.getLoaidoituong().equals("0")){
					ldt="ERP_NHACUNGCAP";
					ID=obj.getNCC();
					
				}
				else if(obj.getLoaidoituong().equals("1")){
					ldt="ERP_NHANVIEN";
					ID=obj.getNCC();
					System.out.println("id: "+obj.getNvId());
				}
				
				else if(obj.getLoaidoituong().equals("3")){
					ldt="NHAPHANPHOI";
					ID=obj.getChiNhanhId();
					System.out.println("id: "+obj.getChiNhanhId());
				}
				else
				{
					ldt="KHACHHANG";
					ID=obj.getNCC();
					System.out.println("id: "+obj.getKhId());
				}

				String query_tkco="select TAIKHOAN_FK from "+ ldt +" where pk_seq='"+ID+"'";
				System.out.println("cau truy van: "+ query_tkco);
				ResultSet rstkco = db.get(query_tkco);
				if (rstkco != null) {
					try {
						if (rstkco.next()) {
							tkco = rstkco.getString("TAIKHOAN_FK");
						}
						rstkco.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				
				
				String query_sohieutaikhoan="select sohieutaikhoan from erp_taikhoankt ";
				if(obj.getLoaidoituong().equals("3"))
				{
					query_sohieutaikhoan+="where sohieutaikhoan='"+tkco+"'";
				}
				else
					
				{
					query_sohieutaikhoan+="where pk_seq='"+tkco+"'";
				}
				System.out.println("cau truy van: "+ query_sohieutaikhoan);
				ResultSet rstksohieu = db.get(query_sohieutaikhoan);
				if (rstksohieu != null) {
					try {
						if (rstksohieu.next()) {
							sohieutaikhoan = rstksohieu.getString("sohieutaikhoan");
						}
						rstksohieu.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

			}
			//------------



			cell = new PdfPCell(new Paragraph("TK Có: ",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);


			cell = new PdfPCell(new Paragraph(""+sohieutaikhoan,font12));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);

			document.add(table);
			//------------------------------------------------------------


			float[] crPhieuNhap1 = { 1.3f * CONVERT, 5f * CONVERT, 8f * CONVERT,4f * CONVERT,4f * CONVERT};

			int socot = 0;
			socot = crPhieuNhap1.length;

			table = new PdfPTable(socot);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.getDefaultCell().setBorder(0);
			table.setSpacingAfter(8.0f);
			table.setWidths(crPhieuNhap1);

			String[] spTitles1 = { "STT", "Khoản mục CP","Nội dung", "TK Nợ", "Số tiền"};
			//in tieu de
			for (int z = 0; z < spTitles1.length; z++) {
				cell = new PdfPCell(new Paragraph(spTitles1[z], font11_bold));
				cell.setPadding(3.0f);
				cell.setPaddingBottom(7);
				//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}


			//----------------------du lieu
			int stt = 0;
			List<ISanpham> spList = obj.getSpList();
			int[] flag = new int[spList.size()]; //mã đánh dấu những sản phẩm giống nhau
			for(int t= 0; t< flag.length; t++){
				flag[t] = 0; //giả sử ban đầu các phần tử trong spList là chưa đánh dấu
			}

			double bvat = 0;//tiền trước khi công thuế tổng
			double vat = 0;//thuế tổng
			double avat = 0; // tiền sau khi cộng thuế tổng

			double bvatsp = 0; //để tính tổng tiền các chi phí có cùng mã
			double vatsp = 0; //tính tổng thuế các chi phí có cùng mã
			double avatsp = 0; //tính tiền sau khi cộng thuế của các sản phẩm có cùng mã.

			/*List<ISanpham> listMoi = new ArrayList<ISanpham>();
			//------------------
			
			// bo sung
			Laydanhsachsanpham(spList, listMoi);*/
			
			for(int i = 0; i < spList.size(); i++) {
				ISanpham sp = spList.get(i);
				String query_TKno="select TAIKHOAN_FK,* from ERP_NHOMCHIPHI where TEN='"+sp.getMasanpham()+"'";
				ResultSet rsTKno = db.get(query_TKno);				
				String tkNo = "";
				if (rsTKno != null) {
					try {
						if (rsTKno.next()) {
							tkNo = rsTKno.getString("TAIKHOAN_FK");
						}
						rsTKno.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				stt++;
				String[] dulieu = {"" + stt, sp.getMasanpham(), sp.getTensanpham(),tkNo  ,sp.getThanhTienTruocThue()};
				bvat = bvat + Double.parseDouble(sp.getThanhTienTruocThue().replaceAll(",", ""));
				vat = vat + Double.parseDouble(sp.getTienThue().replaceAll(",", ""));
				avat = avat + Double.parseDouble(sp.getThanhtien().replaceAll(",", ""));
				for(int j =0; j< spTitles1.length; j++){
					cell = new PdfPCell(new Paragraph(dulieu[j], new Font(bf, 10,
							Font.NORMAL)));
					if(j == 3 || j == 4 || j == 5){
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					else if( j==1 || j==2){
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					}

					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingBottom(5);
					table.addCell(cell);
				}
			}


			Double soTien = 0.0;
			//Tong cong
/*			String[] spTongCong = {"Tổng cộng tiền hàng", "Tiền thuế giá trị gia tăng", "Tổng cộng tiền thanh toán:"};
			for (int z = 0; z < spTongCong.length; z++) {
				if(z==0){
					soTien=bvat;
				}
				else if(z==1){
					soTien=vat;
				}
				else {
					soTien=avat;
				}
				cell.setBorderWidthTop(1.0f);
				if(z==2){
					cell = new PdfPCell(new Paragraph(spTongCong[z], font11_bold));
				}
				else
					cell = new PdfPCell(new Paragraph(spTongCong[z], font11));
				cell.setPadding(3.0f);
				cell.setPaddingBottom(7);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setColspan(4);
				cell.setBorderWidthBottom(0.5f);
				cell.setBorderColorBottom(BaseColor.GRAY);
				table.addCell(cell);

				if(z==2){
					cell = new PdfPCell(new Paragraph(formatter.format(soTien), font11_bold));
				}
				else
					cell = new PdfPCell(new Paragraph(formatter.format(soTien), font11));
				cell.setPadding(3.0f);
				cell.setPaddingBottom(7);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidthBottom(0.5f);
				cell.setBorderColorBottom(BaseColor.GRAY);
				table.addCell(cell);
			}*/
			cell = new PdfPCell(new Paragraph("Tổng cộng tiền hàng ", font11));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			//cell.setBorder(0);
			cell.setPaddingBottom(5);
			cell.setColspan(4);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(""+formatter.format(Double.parseDouble(obj.getTongtienchuaVat().replaceAll(",", ""))), font11));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			//cell.setBorder(0);
			cell.setPaddingBottom(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Tiền thuế giá trị gia tăng ", font11));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			//cell.setBorder(0);
			cell.setColspan(4);
			cell.setPaddingBottom(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(""+  formatter.format( vat), font11));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			//cell.setBorder(0);
			cell.setPaddingBottom(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Tổng cộng tiền thanh toán: ", font11_bold));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			//cell.setBorder(0);
			cell.setColspan(4);
			cell.setPaddingBottom(5);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(""+formatter.format(Double.parseDouble(obj.getTongtiensauVat().replaceAll(",", ""))), font11));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			//cell.setBorder(0);
			cell.setPaddingBottom(5);
			table.addCell(cell);
			
			
			
			
			
			
			
			soTien=Double.parseDouble(obj.getTongtiensauVat().replaceAll(",", ""));
			
			cell = new PdfPCell();
			DocTien docTien = new DocTien();
			String tienchu = docTien.docTien((long)soTien.doubleValue());
			pr = new Paragraph("Số tiền bằng chữ: "+tienchu, font11_bold);
			cell.addElement(pr);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setColspan(5);
			table.addCell(cell);
			document.add(table);


			////Footer
			table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.getDefaultCell().setBorder(0);
			table.setSpacingAfter(8.0f);
			//table.setWidths();
			PdfPCell cell1;

			String[] footer = {"T/L Tổng giám đốc \nGiám đốc XNK","T/L Kế toán trưởng \n","Người lập phiếu \n","Người sử dụng \n","Người mua hàng \n"};
			for (int z = 0; z < footer.length; z++) {
				cell1 = new PdfPCell(new Paragraph(footer[z],new Font(bf, 11,Font.BOLD)));
				cell1.setPadding(3.0f);
				cell1.setBorderWidthBottom(0.5f);
				cell1.setBorder(0);
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell1);
			}
			String ky="(Ký, họ tên) ";
			for (int z = 0; z < footer.length; z++) {
				cell1 = new PdfPCell(new Paragraph(ky,new Font(bf, 12,Font.ITALIC)));
				cell1.setPadding(3.0f);
				cell1.setBorderWidthBottom(0.5f);
				cell1.setBorder(0);
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell1);
			}
			document.add(table);
			document.newPage();

		}catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	private void inPDF(Document document, ServletOutputStream outstream,
			IErpDonmuahang_Giay obj, String userID) throws IOException {

		try {

			System.out.println("Vao day in nek: ");
			geso.traphaco.erp.db.sql.dbutils db = new geso.traphaco.erp.db.sql.dbutils();
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000");
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");

			PdfWriter.getInstance(document, outstream);
			document.open();
			// document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		
			// SIZE
			float CONVERT = 28.346457f; // = 1cm

			PdfPCell cell;

			// ==========================header===================================//

			PdfPTable table1 = new PdfPTable(3);
			table1.setWidthPercentage(100);
			float[] withs1 = {8*CONVERT , 9f*CONVERT, 8*CONVERT };
			table1.setWidths(withs1);
			Paragraph hd = new Paragraph();

			ResultSet rsdv = obj.getDvthList();
			String donvi = "";
			if (rsdv != null) {
				try {
					while (rsdv.next()) {
						if (rsdv.getString("pk_seq").equals(obj.getDvthId())) {
							donvi = rsdv.getString("ten");
						}

					}
					rsdv.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			Chunk chunk = new Chunk("Đơn vị: CÔNG TY TNHH TRAPHACO HƯNG YÊN\nBộ phận: "
					+ donvi, new Font(bf, 9, Font.BOLD));
			chunk.setTextRise(2.0f);
			hd.setAlignment(Element.ALIGN_CENTER);
			hd.add(chunk);

			cell = new PdfPCell();
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph();
			chunk = new Chunk("GIẤY ĐỀ NGHỊ THANH TOÁN", new Font(bf, 12,
					Font.BOLD));
			chunk.setTextRise(2.0f);
			hd.add(chunk);

			String[] tachNgay = obj.getNgaymuahang().split("-");

			chunk = new Chunk("\nNgày  " + tachNgay[2] + "  tháng  "
					+ tachNgay[1] + "  năm  " + tachNgay[0], new Font(bf, 12,
							Font.ITALIC));
			chunk.setTextRise(-20.0f);
			hd.add(chunk);
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph();
			chunk = new Chunk("Mẫu số 05 - TT", new Font(bf, 10, Font.BOLD));
			chunk.setTextRise(2.0f);
			hd.setAlignment(Element.ALIGN_CENTER);
			hd.add(chunk);

			chunk = new Chunk("\nBan hành theo TT200/2014/TT -BTC", new Font(
					bf, 9, Font.ITALIC));
			chunk.setTextRise(5.0f);
			hd.add(chunk);
			hd.setAlignment(Element.ALIGN_CENTER);

			chunk = new Chunk("\nNgày 22/12/2014 của BTC", new Font(bf, 9,
					Font.ITALIC));
			chunk.setTextRise(7.0f);
			hd.add(chunk);
			hd.setAlignment(Element.ALIGN_CENTER);

			cell.addElement(hd);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);

			table1.addCell(cell);
			table1.setSpacingAfter(20);
			document.add(table1);
			// ====================================================================//
			// ========================BODY========================================//
			// ============KÍNH GỬI
			// ================================================//

			table1 = new PdfPTable(2);
			table1.setSpacingBefore(15);
			table1.setWidthPercentage(100);

			float[] withstable = { 20f, 30f };
			table1.setWidths(withstable);

			// cell kính gửi
			cell = new PdfPCell();
			cell.setBorder(0);
			chunk = new Chunk("Kính gửi:", new Font(bf, 12, Font.BOLDITALIC));
			chunk.setTextRise(2.0f);
			chunk.setUnderline(1, (float) -0.2);
			hd = new Paragraph();
			hd.add(chunk);
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.addElement(hd);
			cell.setPaddingTop(0);
			table1.addCell(cell);

			cell = new PdfPCell();
			cell.setBorder(0);
			hd = new Paragraph();
			chunk = new Chunk("     -   Lãnh đạo công ty", new Font(bf, 12,
					Font.NORMAL));
			chunk.setTextRise(2.0f);
			hd.add(chunk);
			chunk = new Chunk("\n     -   Phòng Tổng Hợp", new Font(
					bf, 12, Font.NORMAL));
			chunk.setTextRise(2.0f);
			hd.add(chunk);
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setPaddingTop(0);

			table1.addCell(cell);
			document.add(table1);
			
			String querynhanvien = "";
			if(obj.getNvId() !=null && obj.getNvId().trim().length()>0)
			{
				querynhanvien = "select PK_SEQ,TEN from ERP_NHANVIEN where PK_SEQ ='"
						+ obj.getNvId() + "'";
			}else
			{
				querynhanvien = "select PK_SEQ,TEN from ERP_NHANVIEN where PK_SEQ ='"
						+ obj.getNguoidenghithanhtoan() + "'";
			}
			
			ResultSet rsnhanvien = db.get(querynhanvien);
			String tenNhanVien = "";
			if (rsnhanvien != null) {
				try {
					if (rsnhanvien.next()) {
						tenNhanVien = rsnhanvien.getString("TEN");
					}
					rsnhanvien.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			hd = new Paragraph("Họ và tên người đề nghị thanh toán: "
					+ tenNhanVien, new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			hd.setSpacingBefore(20);
			document.add(hd);

			hd = new Paragraph("Bộ phận (hoặc địa chỉ): " + donvi, new Font(bf,
					12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			document.add(hd);

			hd = new Paragraph("Nội dung thanh toán: " + obj.getLydoTT(), new Font(bf, 12,
					Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			document.add(hd);

			hd = new Paragraph(
					"          Đề nghị thanh toán các chứng từ sau:",
					new Font(bf, 12, Font.NORMAL));
			hd.setSpacingBefore(7);
			hd.setAlignment(Element.ALIGN_LEFT);
			document.add(hd);

			// ======================================cái
			// bảng=========================//
			table1 = new PdfPTable(4);
			table1.setSpacingBefore(10);
			table1.setWidthPercentage(100);
			float[] with = { 1f * CONVERT, 5f * CONVERT, 8f * CONVERT,
					4f * CONVERT };
			table1.setWidths(with);
			String[] tieude = { "STT", "Khoản mục\nchi phí", "Nội dung", "Cộng" };

			for (int i = 0; i < tieude.length; i++) {
				cell = new PdfPCell(new Paragraph(tieude[i], new Font(bf, 12,
						Font.BOLD)));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingBottom(5);
				// cell.setBorder(1);
				table1.addCell(cell);
			}

			//điền dữ liệu
			int stt = 0;
			List<ISanpham> spList = obj.getSpList();
			int[] flag = new int[spList.size()]; //mã đánh dấu những sản phẩm giống nhau
			for(int t= 0; t< flag.length; t++){
				flag[t] = 0; //giả sử ban đầu các phần tử trong spList là chưa đánh dấu
			}

			double bvat = 0;//tiền trước khi công thuế tổng
			double vat = 0;//thuế tổng
			double avat = 0; // tiền sau khi cộng thuế tổng

			double bvatsp = 0; //để tính tổng tiền các chi phí có cùng mã
			double vatsp = 0; //tính tổng thuế các chi phí có cùng mã
			double avatsp = 0; //tính tiền sau khi cộng thuế của các sản phẩm có cùng mã.

			//List<ISanpham> listMoi = new ArrayList<ISanpham>();

			//== b1. lọc list, gôm các chi phí có cùng 1 mã lại 1 dòng
			/*	for(int i=0; i< spList.size() -1; i++){
				ISanpham sp = spList.get(i);
				bvatsp = Double.parseDouble(sp.getThanhTienTruocThue().replaceAll(",", ""));
				vatsp = Double.parseDouble(sp.getTienThue().replaceAll(",", ""));
				avatsp = Double.parseDouble(sp.getThanhtien().replaceAll(",", ""));
				for(int j = i+1; j < spList.size(); j++){
					if(flag[i] == 0){
						ISanpham sp1 = spList.get(j);
						if(sp.getMasanpham().equals(sp1.getMasanpham())){
							bvatsp = bvatsp + Double.parseDouble(sp1.getThanhTienTruocThue().replaceAll(",", ""));
							vatsp = vatsp + Double.parseDouble(sp1.getTienThue().replaceAll(",", ""));
							avatsp = avatsp + Double.parseDouble(sp1.getThanhtien().replaceAll(",", ""));
							flag[j] = 1;
							System.out.println("flag:" + flag[j]);
						}


					}

				}
				//add sp vào list mới
				if(flag[i] == 0){
					sp.setThanhTienTruocThue(String.valueOf(formatter.format(bvatsp)));
					System.out.println("Tiền thuế: " + vatsp);

					sp.setTienThue(String.valueOf(formatter.format(vatsp)));
					sp.setThanhtien(String.valueOf(formatter.format(avatsp)));
					listMoi.add(sp);
				}
			}
			//còn phần tử cuối cùng

			if(flag[spList.size()-1] == 0){

				ISanpham sp = spList.get(spList.size()-1);
				listMoi.add(sp);
			}*/

			
			
			//List<ISanpham> spList1= new ArrayList<ISanpham>();
			
		//	Laydanhsachsanpham(spList, listMoi); // lấy list mới đổ ra
			
			//== b2. đổ dữ liệu
			for(int i = 0; i < spList.size(); i++) { 
				ISanpham sp = spList.get(i);
				stt++;
				String[] dulieu = {"" + stt, sp.getMasanpham(), sp.getTensanpham(),  sp.getThanhtien()};
				bvat = bvat + Double.parseDouble(sp.getThanhTienTruocThue().replaceAll(",", ""));
				vat = vat + Double.parseDouble(sp.getTienThue().replaceAll(",", ""));
				avat = avat + Double.parseDouble(sp.getThanhtien().replaceAll(",", ""));
				for(int j =0; j< tieude.length; j++){
					cell = new PdfPCell(new Paragraph(dulieu[j], new Font(bf, 10,
							Font.NORMAL)));
					if(j == 3 || j == 4 || j == 5){
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					else if( j==1 || j==2){
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					}

					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingBottom(5);
					table1.addCell(cell);
				}

			}

			// tổng cộng bên dưới.
			cell = new PdfPCell(new Paragraph("Tổng cộng", new Font(bf, 12,
					Font.BOLD)));
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingBottom(5);
			table1.addCell(cell);

			/*cell = new PdfPCell(new Paragraph(formatter.format(bvat), new Font(bf, 10,
					Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingBottom(5);
			table1.addCell(cell);

			//thuế
			cell = new PdfPCell(new Paragraph(formatter.format(vat), new Font(bf, 10,
					Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingBottom(5);
			table1.addCell(cell);*/

			//tổng cộng
			cell = new PdfPCell(new Paragraph(formatter.format(avat), new Font(bf, 10,
					Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingBottom(5);
			table1.addCell(cell);

			document.add(table1);

			// =================================phần tiền
			// dưới====================
			ResultSet rshttt = obj.getHtttRs();
			String hinhthuc = "";
			if (rshttt != null) {
				try {
					while (rshttt.next()) {
						if (rshttt.getString("pk_seq").equals(obj.getHtttId())) {
							hinhthuc = rshttt.getString("ten");
						}
					}
					rshttt.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			hd = new Paragraph("Hình thức thanh toán: " + hinhthuc, new Font(
					bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			document.add(hd);

			hd = new Paragraph();
			hd.setAlignment(Element.ALIGN_LEFT);
			chunk = new Chunk("       Số tiền thanh toán: ", new Font(bf, 12,
					Font.NORMAL));
			chunk.setTextRise(0);
			hd.add(chunk);

			chunk = new Chunk(formatter.format(avat), new Font(bf, 12, Font.BOLD));
			chunk.setTextRise(0);
			hd.add(chunk);

			DocTien doctien = new DocTien();
			String tienchu = doctien.docTien((long)avat);
			chunk = new Chunk(" (" + tienchu + ").", new Font(bf, 12,
					Font.ITALIC));
			chunk.setTextRise(0);
			hd.add(chunk);

			document.add(hd);

			hd = new Paragraph("( Kèm theo .......  chứng từ gốc).", new Font(
					bf, 12, Font.NORMAL));
			hd.setSpacingBefore(5);
			hd.setAlignment(Element.ALIGN_LEFT);
			document.add(hd);

			if (obj.getHtttId().equals("100001")) {
				String chuTaiKhoan = "";
				String maSoThue = "";
				String soTaiKhoan = "";
				String tenNganHang = "";
				String tenChiNhanh = "";
				String queryNH = "";
				if(obj.getLoaidoituong().equals("0")){ //NHÀ CUNG CẤP
					queryNH = "select ncc.TEN, ncc.SOTAIKHOAN, ncc.MASOTHUE, nh.TEN  as TENNGANHANG , isnull(CN.TEN,'') as tenchinhanh "
							+ "from ERP_NHACUNGCAP ncc  \n" +
							"LEFT JOIN ERP_NGANHANG nh on nh.PK_SEQ = ncc.NGANHANG_FK \n" +
							"LEFT JOIN ERP_CHINHANH CN ON CN.PK_SEQ = ncc.ChiNhanh_FK \n" +
							"WHERE ncc.PK_SEQ = " + obj.getNCC();

				}else if(obj.getLoaidoituong().equals("1")){//nhân viên
					queryNH = "select nv.TEN, nv.SOTAIKHOAN, nh.TEN as TENNGANHANG, '' AS MASOTHUE,null as tenchinhanh from erp_nhanvien nv " +
							"left join ERP_NGANHANG nh on nv.NGANHANG_FK = nh.PK_SEQ " +
							"LEFT JOIN ERP_CHINHANH CN ON CN.PK_SEQ = nv.ChiNhanh_FK \n" +
							"WHERE nv.PK_SEQ = '" + obj.getNvId()+ "'" ;

				}
				else if(obj.getLoaidoituong().equals("2")){//khách hàng
					queryNH = "select kh.MASOTHUE, kh.SOTAIKHOAN, kh.TEN, '' AS TENNGANHANG,null as tenchinhanh from KHACHHANG kh " +
							"WHERE kh.PK_SEQ = '" + obj.getKhId()+ "'" ;

				}
				else  if(obj.getLoaidoituong().equals("3")){//CHI NHÁNH
					queryNH = "select npp.TEN, NPP.NGANHANG as TENNGANHANG , npp.MASOTHUE, npp.SOTAIKHOAN,null as tenchinhanh from nhaphanphoi npp where npp.PK_SEQ =  '" + obj.getChiNhanhId()+ "'" ;

				}
				System.out.println("----Query tai khoan: "  + queryNH);
				ResultSet rsncc = db.get(queryNH);
				if(rsncc != null){
					try {
						if(rsncc.next()){
							chuTaiKhoan = rsncc.getString("TEN");
							maSoThue = rsncc.getString("MASOTHUE");
							soTaiKhoan = rsncc.getString("SOTAIKHOAN");
							tenNganHang = rsncc.getString("TENNGANHANG").equals("null")?"":rsncc.getString("TENNGANHANG");
							tenChiNhanh = rsncc.getString("TENCHINHANH").equals("null")?"":rsncc.getString("TENCHINHANH");
						}
						rsncc.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				hd = new Paragraph();
				hd.setSpacingBefore(5);
				hd.setAlignment(Element.ALIGN_LEFT);
				chunk = new Chunk("Chủ tài khoản: ", new Font(bf, 12,
						Font.NORMAL));
				chunk.setTextRise(0);
				hd.add(chunk);

				chunk = new Chunk(chuTaiKhoan,
						new Font(bf, 12, Font.BOLD));
				chunk.setTextRise(0);
				hd.add(chunk);
				document.add(hd);

				hd = new Paragraph("Mã số thuế: " + maSoThue, new Font(bf, 12,
						Font.NORMAL));
				hd.setAlignment(Element.ALIGN_LEFT);
				document.add(hd);

				hd = new Paragraph("Tài khoản: " + soTaiKhoan, new Font(bf, 12,
						Font.NORMAL));
				hd.setAlignment(Element.ALIGN_LEFT);
				document.add(hd);

				hd = new Paragraph(
						"Tại: " + tenNganHang,
						new Font(bf, 12, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_LEFT);
				document.add(hd);

				hd = new Paragraph(
						"Chi nhánh : " + tenChiNhanh,
						new Font(bf, 12, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_LEFT);
				document.add(hd);


			}

			// ===============================FOOTER==============================//
			String querykd = "select isLanhDaoKyDuyet  from ERP_MUAHANG where PK_SEQ =" + obj.getId();
			ResultSet rs = db.get(querykd);
			int n = 0;
			if(rs != null){
				try {
					if(rs.next()){
						n = rs.getInt("isLanhDaoKyDuyet");
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			else n =0;

			//tạo footer ký tên
			table1 = new PdfPTable(3);
			table1.setSpacingBefore(50);
			table1.setWidthPercentage(100);
			if(n == 1){

				footer(hd, cell, 3, table1, tenNhanVien, bf);
			}
			else{
				footer(hd, cell, 2, table1, tenNhanVien, bf);
			}

			document.add(table1);

			document.close();

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	public void footer( Paragraph hd, PdfPCell cell, int n, PdfPTable table1, String tenNhanVien, BaseFont bf){
		hd = new Paragraph();

		cell = new PdfPCell();
		if(n == 3){
			hd = new Paragraph("LÃNH ĐẠO CÔNG TY", new Font(bf, 12, Font.BOLD));
		}else{
			hd = new Paragraph("", new Font(bf, 12, Font.BOLD));
		}

		hd.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(hd);
		cell.setBorder(0);

		table1.addCell(cell);

		cell = new PdfPCell();
		hd = new Paragraph("PHỤ TRÁCH BỘ PHẬN", new Font(bf, 12, Font.BOLD));
		hd.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(hd);
		cell.setBorder(0);

		table1.addCell(cell);

		cell = new PdfPCell();
		hd = new Paragraph("NGƯỜI ĐỀ NGHỊ", new Font(bf, 12, Font.BOLD));
		hd.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(hd);
		cell.setBorder(0);

		table1.addCell(cell);

		for (int i = 0; i < 3; i++) {
			cell = new PdfPCell();
			if(n ==2){
				hd = new Paragraph("", new Font(bf, 12, Font.ITALIC));
			}else{
				hd = new Paragraph("(Chữ ký)", new Font(bf, 12, Font.ITALIC));
			}

			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);
			table1.addCell(cell);
		}
		for (int i = 0; i < 3; i++) {
			cell = new PdfPCell();
			if (i < 2) {
				hd = new Paragraph("", new Font(bf, 12, Font.ITALIC));
			} else {
				hd = new Paragraph("\n\n\n" + tenNhanVien, new Font(bf,
						12, Font.ITALIC));
			}
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			hd.setSpacingBefore(30);
			cell.setBorder(0);
			table1.addCell(cell);
		}
	}

	private void setCellBorderStyle(Cell cell, short align,boolean kt) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setTextWrapped(true);
		if(kt)
		{
			com.aspose.cells.Font font2 = new com.aspose.cells.Font(); 
			font2.setName("Calibri");
			font2.setColor(Color.BLACK);
			font2.setSize(11);
			style.setFont(font2);
			style.setColor(Color.SILVER);
		}
		else
			style.setColor(Color.WHITE);

		cell.setStyle(style);
	}

	private String DinhDangTraphacoERP(String sotien) {
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");

		return sotien;
	}

	public static void getCellStyle_border(Cell cell,Boolean italic, Boolean bold, Boolean textwrap,short alig,String cellValue){		   
		Style style;
		style = cell.getStyle();	
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);

		com.aspose.cells.Font font = new com.aspose.cells.Font();
		font.setColor(Color.BLACK);
		font.setBold(bold);
		font.setItalic(italic);
		font.setSize(9);
		font.setName("Arial");
		style.setFont(font);
		style.setVAlignment(VerticalAlignmentType.CENTRED);
		style.setHAlignment(alig);
		style.setTextWrapped(textwrap);

		cell.setStyle(style);
		cell.setValue(cellValue);
	}


	public static void getCellStyle(Cell cell,Boolean italic, Boolean bold, Boolean textwrap,String cellValue){		   
		Style style;
		style = cell.getStyle();		
		com.aspose.cells.Font font = new com.aspose.cells.Font();
		font.setColor(Color.BLACK);
		font.setBold(bold);
		font.setItalic(italic);
		font.setSize(9);
		font.setName("Arial");
		style.setFont(font);
		style.setTextWrapped(textwrap);

		cell.setStyle(style);
		cell.setValue(cellValue);
	}

	public static void getCellStyle(Cell cell,Boolean italic, Boolean bold, Boolean textwrap,short alig,String cellValue){		   
		Style style;
		style = cell.getStyle();		
		com.aspose.cells.Font font = new com.aspose.cells.Font();
		font.setColor(Color.BLACK);
		font.setBold(bold);
		font.setItalic(italic);
		font.setSize(9);
		font.setName("Arial");
		style.setFont(font);
		style.setHAlignment(alig);
		style.setTextWrapped(textwrap);

		cell.setStyle(style);
		cell.setValue(cellValue);
	}
	
	
	
	public void Laydanhsachsanpham  (List<ISanpham> spList, List<ISanpham> spList1)
	{
		/*List<ISanpham> spList1= new ArrayList<ISanpham>();*/
		NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
		for (int i = 0; i < spList.size(); i++) {
			ISanpham sp = spList.get(i);
			String ma=sp.getMasanpham();
			boolean trungma=false;
			// kiem tra neu trung
			for (int j = 0; j < spList1.size(); j++) {
				ISanpham sp1 = spList1.get(j);
				if(sp1.getMasanpham().trim().equals(ma))
				{
					System.out.println(" ma trung  :"+ sp1.getMasanpham());
					System.out.println(" tien trung 1 :"+ Double.parseDouble(sp1.getThanhtien().replaceAll(",", "")));
					System.out.println(" tien trung 2 :"+ Double.parseDouble(sp.getThanhtien().replaceAll(",", "")));
					
					double thanhtientruocthue = Double.parseDouble(sp1.getThanhTienTruocThue().replaceAll(",", ""))  + Double.parseDouble(sp.getThanhTienTruocThue().replaceAll(",", "")) ; 
					sp1.setThanhTienTruocThue(formatter.format(thanhtientruocthue));
					
					double thanhtien1 = Double.parseDouble(sp1.getThanhtien().replaceAll(",", ""))  + Double.parseDouble(sp.getThanhtien().replaceAll(",", "")) ; 
					sp1.setThanhtien(formatter.format(thanhtien1));
					
					double tienthue1 = Double.parseDouble(sp1.getTienThue().replaceAll(",", ""))  + Double.parseDouble(sp.getTienThue().replaceAll(",", "")) ; 
					sp1.setTienThue(formatter.format(tienthue1));
					
					
					
					
					trungma=true;
					System.out.println(" tien trung 3 :"+ thanhtien1);
					System.out.println(" tien trung 3 :"+ sp1.getThanhtien());
				}
			}
			if(trungma==false) // ko trung thì add mới list
			{
				System.out.println("add sp ");
				spList1.add(sp);
			}
			
		}
	}
}
