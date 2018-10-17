package geso.erp.servlets.hoadonphelieu;

import geso.dms.center.beans.doctien.DocTien;
import geso.dms.center.beans.doctien.DocTienEN;
import geso.dms.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.erp.beans.cauhinhinhoadon.IErpCauHinhInHoaDon;
import geso.erp.beans.cauhinhinhoadon.imp.ErpCauHinhInHoaDon;
import geso.erp.beans.hoadon.IErpHoaDon_SP;
import geso.erp.beans.hoadonphelieu.*;
import geso.erp.beans.hoadonphelieu.imp.*;

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
import javax.swing.text.TabExpander;

import sun.nio.cs.ext.PCK;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Utilities;

public class ErpHoaDonPheLieuPdfSvlTT extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final float CONVERT = 28.3464f;

	public ErpHoaDonPheLieuPdfSvlTT()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpHoadonphelieu obj;
		String userId;
		Utility util = new Utility();

		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String ddhId = util.antiSQLInspection(request.getParameter("ddhId"));
		if(ddhId == null) ddhId = "";
		
		String lsinId = util.antiSQLInspection(request.getParameter("lsinId"));
		if(lsinId == null) 
			lsinId = "";
		
		// Loại hóa đơn trong LỊCH SỬ IN : 0 - Hd tài chính, 1 - Hóa đơn trả về ncc, 2 - Hóa đơn khác, 3 - Giảm/tăng giá hàng bán 
		String loaihd = util.antiSQLInspection(request.getParameter("loaihd"));
		if(loaihd == null) 
			loaihd = "";
		
		
		obj = new ErpHoadonphelieu(ddhId);
		obj.setUserId(userId) ;
		if(lsinId.trim().length() <= 0 )
		{
			obj.initExcel();
		}
		else
		{
			obj.initInLS(lsinId);
		}
		

		//obj.init();
		//obj.DbClose();
		//IN PDF CŨ
	/*	response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," inline; filename=HoaDonPheLieu"+ddhId+".pdf");

		// document=new Document(pageSize, marginLeft,
		// marginRight,marginTop,marginBottom)
		Rectangle pageSize = PageSize.A4;

		//Xét kênh để in bảng in phù hợp
		//IN HÓA ĐƠN TRONG NƯỚC
		ErpCauHinhInHoaDon config = new ErpCauHinhInHoaDon();
		config.initWithName("PAGE");
		config.dbClose();
		float CONVERT = 28.346457f; // =1cm
		float PAGE_LEFT = config.getMarginLeft()*CONVERT, PAGE_RIGHT = config.getMarginRight()*CONVERT, PAGE_TOP = config.getMarginTop()*CONVERT, PAGE_BOTTOM = config.getMarginBottom()*CONVERT; //cm
		//float PAGE_LEFT = 1.7f*CONVERT, PAGE_RIGHT = 1.4f*CONVERT, PAGE_TOP = 5.0f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
		
		
		//page_config.init_PageConfig();
		//Document document = new Document(pageSize, page_config.getMarginLeft() * CONVERT, page_config.getMarginRight()
		//		* CONVERT, page_config.getMarginTop() * CONVERT, page_config.getMarginBottom() * CONVERT);
		Document document = new Document(pageSize, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		ServletOutputStream outstream = response.getOutputStream();
		
		this.HoaDonTrongNuocPdf(document, outstream, obj);*/
		
		response.setContentType("application/xlsm");
		response.setHeader("Content-Disposition", "attachment; filename=HoaDonKhac" + obj.getSohoadon() + ".xlsm");
		try 
		{
			OutputStream outstream = response.getOutputStream();
			this.HoaDonTrongNuocExcel(outstream, obj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// Nếu in bình thường(in trong trang Cập nhật/ Hiển thị) --> Lưu vào lịch sử in
		if(lsinId.trim().length() <= 0 )
		{
			String msg = obj.CreateLSIN(ddhId, loaihd ) ;
			if(msg.trim().length() > 0)
			{
				obj.setMsg(msg);
			}
			System.out.println("MSG : "+msg);
		}

	}

	private void HoaDonTrongNuocExcel(OutputStream out,IErpHoadonphelieu obj) 
	{


		NumberFormat formatter = new DecimalFormat("###,###,###,###,###.###");

		int TABLE_NUM_ROWS = 27 ;// 6
		int S2_START_INDEX = 20;

		try {
			dbutils db = new dbutils();
			String sql = "select * from Erp_KhachHang where pk_seq = '" + obj.getNccId() + "'";
			System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] sql = " + sql);
			String address = "";
			String taxCode = "";
			String name_of_buyer = "";
			String name_of_company = "";

			try {
				ResultSet rs = db.get(sql);
				if (rs.next()) {
					if (rs.getString("MST") != null) taxCode = rs.getString("MST");
					address = rs.getString("DiaChi");
					if (rs.getString("NguoiLienhe") != null) name_of_buyer = rs.getString("NguoiLienhe");
					name_of_company = rs.getString("Ten");
				}
				rs.close();
				db.shutDown();
			} catch (Exception ex) {
				ex.printStackTrace();
			}


			String ghichu = " ";

			String thue = "" + obj.getVat();
			System.out.println("Thue la: " + thue);

			db.shutDown();

			FileInputStream fstream;
			Cell cell = null;

			fstream = new FileInputStream(getServletContext().getInitParameter( "path") + "\\HoaDonTaiChinhTrongNuoc.xlsm");
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			workbook.open(fstream);

			Worksheets worksheets = workbook.getWorksheets();

			// Sheet 1
			Worksheet worksheet1 = worksheets.getSheet("HOA DON CANH GIUA");
			Cells cells1 = worksheet1.getCells();

			cell = cells1.getCell("E13");				
			cell.setValue(name_of_company); // Họ tên khách hàng
			
			cell = cells1.getCell("E14");
			cell.setValue(address); // Địa chỉ
			cell = cells1.getCell("E15");
			cell.setValue(taxCode); // Mã số thuế
			cell = cells1.getCell("E16");
			cell.setValue(""); // Hình thức thanh toán

			// Sản phẩm
			List<IErpHoaDonPL_SP> spList = obj.GetSanPhamList();

			// Bỏ những sản phẩm không in (trong trường hợp hóa đơn chưa chốt -
			// chưa được bỏ những sản phẩm ko in)
			for (int i = 0; i < spList.size(); i++) {
				if (!spList.get(i).getIn().equals("1")) {
					spList.remove(i);
					i--;
				}
			}

			int spIndex = 0;
			int rowIndex = 0;
			int soDongSp = 0;
			double total_amount = 0;

			int sokytu1sp = 48 ;
			if (sokytu1sp <= 0)
				sokytu1sp = 40;

			String tensp = "";
			boolean changeSpCore = false;
			int stt = 0;
		
			while (spIndex < spList.size() && rowIndex < TABLE_NUM_ROWS) 
			{
				IErpHoaDonPL_SP sanpham = (IErpHoaDonPL_SP) spList.get(spIndex);
				tensp = sanpham.getTenSanPham();

				String dongia = "";
				String soluong = "";
				String thanhtien = "";
				
				try {
					
					dongia = sanpham.getDonGia();
					soluong = sanpham.getSoLuong() ;
					thanhtien = sanpham.getThanhTien();
					
					if(obj.getKhoanmucDTId().equals("400000")) // HOA DON CHIET KHAU
					{												
						if ( Double.parseDouble(dongia) < 0) 
							dongia =  Double.toString(Double.parseDouble(dongia)*(-1));
																	
						if ( Double.parseDouble(thanhtien) < 0) 
							thanhtien = Double.toString(Double.parseDouble(thanhtien)*(-1));
						
						total_amount += Double.parseDouble(sanpham.getThanhTien()) ;
						if (total_amount <  0) total_amount = total_amount*(-1);
					}
					else
					{						
							total_amount += Double.parseDouble(thanhtien);
					}
					
				} catch (Exception ex) { }
				
								
				// Xu ly ten san pham
				List<String> _tenList = new ArrayList<String>();
				String[] words;
				int beginIndex = 0;
				boolean changeSp = false;
				
				xuLyTenList(obj, _tenList, tensp, sokytu1sp, changeSp);
				

				
			// IN CÁC SẢN PHẨM
					stt++;
	
					// In dong 1
					// Cột stt sản phẩm
					cell = cells1.getCell("A" + (S2_START_INDEX + rowIndex));
					
					//System.out.println(S2_START_INDEX + rowIndex);
					cell.setValue("" + (stt));
					// Cột tên sản phẩm
					cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
					cell.setValue(_tenList.size() > 0 ? _tenList.get(0) : "");
					// Cột Đơn vị tính
					cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
					cell.setValue(sanpham.getDonViTinh());
					
					// Cột Số lượng
					cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
					if(Double.parseDouble(soluong) <= 0 && Double.parseDouble(thanhtien) <= 0) // TH: chỉ nhập cột thành tiền
					{
						cell.setValue(" ");
					}else{
						cell.setValue(formatVN(formatter.format(Double.parseDouble(soluong))));
					}
					
					// Cột Đơn giá
					cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
					if(Double.parseDouble(dongia) <= 0 && Double.parseDouble(thanhtien) <= 0) // TH: chỉ nhập cột thành tiền
					{
						cell.setValue(" ");
					}else
					{
					    cell.setValue(formatVN(formatter.format(Double.parseDouble(dongia))));
					}
					// Cột Thành tiền
					cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
					if(Double.parseDouble(dongia) <= 0 && Double.parseDouble(soluong) <= 0 && Double.parseDouble(thanhtien) <= 0) // TH: chỉ nhập Diễn giải
					{
						cell.setValue(" ");
					}else
					{
					    cell.setValue(formatVN(formatter.format(Double.parseDouble(thanhtien))));
					}
	
					rowIndex++;
					beginIndex++;
					
	
					// In cac dong con lai
					for (int z = beginIndex; z < _tenList.size(); z++) {
						// Cột tên sản phẩm
						cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
						cell.setValue("" + _tenList.get(z));
						// Cột Đơn vị tính
						cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cột Số lượng
						cell = cells1.getCell("I" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cột Đơn giá
						cell = cells1.getCell("K" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						rowIndex++;
					}	
									
					
					// In ghi chú
					List<String> _ghichuList = new ArrayList<String>();
					String ghichusp = sanpham.getGhiChu1();
					xuLyTenList(obj, _ghichuList, ghichusp, sokytu1sp, changeSp);
					
					for (int z = 0; z < _ghichuList.size(); z++) {
						// Cột tên sản phẩm
						cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
						cell.setValue("" + _ghichuList.get(z));
						// Cột Đơn vị tính
						cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cột Số lượng
						cell = cells1.getCell("I" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cột Đơn giá
						cell = cells1.getCell("K" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						rowIndex++;
					}
					

				spIndex++;
			}


/*			if (obj.getTienChietKhau() > 0) {
				cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
				cell.setValue("Chiết khấu hoa hồng ");
				cell = cells1.getCell("N" + (S2_START_INDEX + rowIndex));
				cell.setValue(formatVN(formatter.format(obj.getTienChietKhau())));
				rowIndex++;
			}

			if (obj.getTienkhuyenmai() > 0) {
				cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
				cell.setValue("Tiền giảm giá ");
				cell = cells1.getCell("N" + (S2_START_INDEX + rowIndex));
				cell.setValue(formatVN(formatter.format(obj.getTienkhuyenmai())));
				rowIndex++;
			}

			if (obj.getTienVanChuyen() > 0) {
				cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
				cell.setValue("Tiền vận chuyển ");
				cell = cells1.getCell("N" + (S2_START_INDEX + rowIndex));
				cell.setValue(formatVN(formatter.format(obj
						.getTienVanChuyen())));
				rowIndex++;
			}*/

			double tienCK = 0 ;
			double tienKM = 0 ;
			double tienBH = 0 ;
			double tienVC = 0 ;
			double tienSauCKKM = total_amount - tienCK - tienKM + tienVC;
			double vat = Math.round(Double.parseDouble(obj.getVat()));
			double tienVAT = Math.round(tienSauCKKM * vat / 100);
			double tienSauVAT = Math.round(tienSauCKKM + tienVAT);
			String tienBangChu = DocTien.docTien(Math.round(tienSauVAT))
					+ "./.";
			String ngaythangnam = obj.getNgayghinhan();
			String ngay = ngaythangnam.substring(8, 10);
			String thang = ngaythangnam.substring(5, 7);
			String nam = ngaythangnam.substring(0, 4);


			
			if(total_amount > 0 && vat > 0)
			{
				cell = cells1.getCell("D42");
				cell.setValue(formatVN(formatter.format(vat)));			
				cell = cells1.getCell("L41");
				cell.setValue(formatVN(formatter.format(total_amount)));
				cell = cells1.getCell("L42");
				cell.setValue(formatVN(formatter.format(tienVAT)));
				cell = cells1.getCell("L43");
				cell.setValue(formatVN(formatter.format(tienSauVAT)));
			}
			else
			{
				cell = cells1.getCell("D42");
				cell.setValue("");			
				cell = cells1.getCell("L41");
				cell.setValue("");
				cell = cells1.getCell("L42");
				cell.setValue("");
				cell = cells1.getCell("L43");
				cell.setValue("");
			
			}
			
			cell = cells1.getCell("E45");
			cell.setValue(tienBangChu);
			cell = cells1.getCell("K48");
			cell.setValue(ngay);
			cell = cells1.getCell("M48");
			cell.setValue(thang);
			cell = cells1.getCell("N48");
			cell.setValue(nam);

			workbook.save(out);
			fstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	
		
	}
	protected boolean xuLyTenList(IErpHoadonphelieu hdBean, List<String> _tenList, String tenSp, int sokytu1sp, boolean changeSp) 
	{		

		String[] words = new String[0];
		String _ten = "", _ten2 = "";

			words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong ten san pham
			_ten = "";
			_ten2 = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xử lý khi 1 từ > số ký tự 1 dòng
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // Thêm dòng cũ
					_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0) {
				_tenList.add(_ten);
			}
			
			
			
	
		
		return changeSp;
	}
	public static String formatVN(String so) {

		String result = so.replaceAll(",", "@");
		result = result.replaceAll("[.]", ",");
		result = result.replaceAll("@", ".");

		return result;

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	private void HoaDonTrongNuocPdf(Document document, ServletOutputStream outstream, IErpHoadonphelieu pxkBean) throws IOException
	{
		try
		{
			/*IErpCauHinhInHoaDon soNumber = new ErpCauHinhInHoaDon();
			soNumber.initWithName("SO");*/

			IErpCauHinhInHoaDon khachhang_config = new ErpCauHinhInHoaDon();
			khachhang_config.initWithName("CUSTOMMER");
			khachhang_config.dbClose();

			IErpCauHinhInHoaDon dh_config = new ErpCauHinhInHoaDon();
			dh_config.initWithName("DETAILS");
			dh_config.dbClose();
			
			IErpCauHinhInHoaDon footer_config = new ErpCauHinhInHoaDon();
			footer_config.initWithName("FOOTER");
			footer_config.dbClose();
			
			//Kích thước theo đơn vị cm
			float CONVERT = 28.346457f; // = 1cm
			//Header
			float[] TABLE_HEADER_WIDTHS = {18.0f * CONVERT};
			float TABLE_HEADER_LEFT = khachhang_config.getMarginLeft()*CONVERT, TABLE_HEADER_TOP = khachhang_config.getMarginTop()*CONVERT, TABLE_HEADER_BOTTOM = khachhang_config.getMarginBottom()*CONVERT;
			//float TABLE_HEADER_LEFT = 7.0f*CONVERT, TABLE_HEADER_TOP = 0.0f*CONVERT, TABLE_HEADER_BOTTOM = 1.6f*CONVERT;
			int BORDER_WIDTH = Rectangle.NO_BORDER;
			
			//Products
			int TABLE_NUM_ROWS = 16;//dh_config.getNumberOfRow(); //6
			float TABLE_LEFT = 0.0f*CONVERT, TABLE_RIGHT = 0.0f*CONVERT, TABLE_TOP = 0.0f*CONVERT, TABLE_BOTTOM = 0.0f*CONVERT;
			float TABLE_HEIGHT = dh_config.getTableHeight()*CONVERT, TABLE_ROW_HEIGHT = TABLE_HEIGHT/TABLE_NUM_ROWS;
			//float TABLE_HEIGHT = 9.0f*CONVERT, TABLE_ROW_HEIGHT = TABLE_HEIGHT/TABLE_NUM_ROWS;
			float[] TABLE_COLUMN_WIDTHS = {dh_config.getNoColumn()*CONVERT, dh_config.getProductColumn()*CONVERT, dh_config.getUnitColumn()*CONVERT, dh_config.getQuantityColumn()*CONVERT, dh_config.getUniPriceColumn()*CONVERT, dh_config.getAmoutColumn()*CONVERT};
			//float[] TABLE_COLUMN_WIDTHS = {1.0f*CONVERT, 6.7f*CONVERT, 2.3f*CONVERT, 2.1f*CONVERT, 2.1f*CONVERT, 3.8f*CONVERT};
			float[] TABLE_COLUMN_PADDING_LEFTS = {0.1f*CONVERT, 0.0f*CONVERT, 0.0f*CONVERT, 0.0f*CONVERT, 0.0f*CONVERT, 0.0f*CONVERT};
			int[] TABLE_COLUMN_ALIGNS = {Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_CENTER, Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, Element.ALIGN_RIGHT};
			
			//Amount
			float TABLE_AMOUNT_LEFT_WIDTH = 7.0f * CONVERT;
			float TABLE_AMOUNT_RIGHT_WIDTH = 11.0f * CONVERT;
			float[] TABLE_AMOUNT_ROW_HEIGHTS = {footer_config.getMarginLeft() * CONVERT, footer_config.getMarginRight() * CONVERT, footer_config.getMarginTop() * CONVERT, footer_config.getMarginBottom() * CONVERT, footer_config.getPaddingLeft() * CONVERT};
			float[] TABLE_AMOUNT_LEFT_PADDING_LEFTS = {0.0f * CONVERT, 4.8f * CONVERT, 0.0f * CONVERT, 0.0f * CONVERT };
			float[] TABLE_AMOUNT_RIGHT_PADDING_RIGHTS = {0.5f * CONVERT, 0.5f * CONVERT, 0.5f * CONVERT, 0.0f * CONVERT, 0.0f * CONVERT };			
			
			NumberFormat formatter = new DecimalFormat("#,###,###.00");
			NumberFormat formatter2 = new DecimalFormat("#,###,###.##");
			NumberFormat formatter3 = new DecimalFormat("#,###,###");
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font headerFont = new Font(bf, khachhang_config.getFontSize(), Font.BOLD);
			Font donhangFont = new Font(bf, 11, Font.NORMAL);
			Font productFont = new Font(bf, dh_config.getLineSpacing(), Font.NORMAL);
			//Font font10bold = new Font(bf, 10, Font.BOLD);
			
			/*Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			Font font13bold = new Font(bf, 13, Font.BOLD);
			Font font14bold = new Font(bf, 14, Font.BOLD);
			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font16bold = new Font(bf, 16, Font.BOLD);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font13 = new Font(bf, 13, Font.NORMAL);
			Font font14 = new Font(bf, 14, Font.NORMAL);*/
			
			dbutils db = new dbutils();
			String sql = "select * from Erp_KhachHang where pk_seq = '" + pxkBean.getNccId() + "'";
			System.out.println("[ErpHoadonPheLieuPdfSvl.HoaDonTrongNuocPdf] sql = " + sql);
			String address = "";
			String taxCode = "";
			String name_of_buyer = "";
			String name_of_company = "";

			try
			{
				ResultSet rs = db.get(sql);
				if (rs.next())
				{
					if (rs.getString("MST") != null)
						taxCode = rs.getString("MST");
					address = rs.getString("DiaChi");
					if (rs.getString("NguoiLienhe") != null)
						name_of_buyer = rs.getString("NguoiLienhe");
					name_of_company = rs.getString("Ten");
				}
				rs.close();
				db.shutDown();
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
			
			String ghichu = "";

			/*String ghichu = pxkBean.getGhiChu();
			if(ghichu == null || ghichu.trim().length() == 0) {
				ResultSet rs_hopdong = pxkBean.getHopdongRs();
				if(rs_hopdong != null) {
					try { 
						while(rs_hopdong.next()) { 				
							if(rs_hopdong.getString("pk_seq").trim().equals(pxkBean.getHopdongId())) {
								ghichu = rs_hopdong.getString("mahopdong");
							}
						}
					} catch (Exception e) {}
				}
				
				if(ghichu == null || ghichu.trim().length() == 0) {
					ghichu = pxkBean.gethinhthuctt();
				}
				
				if(ghichu == null) ghichu = " ";
			}*/
			
			String thue = "" + pxkBean.getVat();
			System.out.println("Thue la: " + thue);

			//Vẽ HEADER
			PdfPTable table = new PdfPTable(TABLE_HEADER_WIDTHS.length);	
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidths(TABLE_HEADER_WIDTHS);
			table.setWidthPercentage(100.0f);
			
			//Họ và tên người mua hàng
			PdfPCell cell;
			cell = new PdfPCell(new Paragraph(" ", headerFont));
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f*CONVERT);
			table.addCell(cell);
			
			//Tên đơn vị
			float _fontSize = khachhang_config.getFontSize() - (name_of_company.length() > 50 ? (float)(name_of_company.length() - 50)/10 : 0) ;
			Font headerFontKh = new Font(bf, _fontSize, Font.BOLD);
			
			cell = new PdfPCell(new Paragraph(name_of_company.toUpperCase(), headerFontKh)); 
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f*CONVERT);
			table.addCell(cell);
			
			//Địa chỉ
			cell = new PdfPCell(new Paragraph(address, headerFont)); 
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f*CONVERT);
			table.addCell(cell);
			
			//Mã số thuế
			cell = new PdfPCell(new Paragraph(taxCode, headerFont)); 
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f*CONVERT);
			table.addCell(cell);
			
			//Hình thức thanh toán
			cell = new PdfPCell(new Paragraph(ghichu, headerFont)); 
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f*CONVERT);
			table.addCell(cell);
			
			//Khoảng trống 
			cell = new PdfPCell(new Paragraph("    ", headerFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_HEADER_BOTTOM);
			table.addCell(cell);

			document.add(table);
			
			//IN SẢN PHẨM
			table = new PdfPTable(TABLE_COLUMN_WIDTHS.length);	
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidths(TABLE_COLUMN_WIDTHS);
			table.setWidthPercentage(100.0f);
			
			double total_amount = 0;
			int spIndex = 0;
			int rowIndex = 0;
			int soDongSp = 0;
			
			System.out.println("[ErpHoadonPheLieuPdfSvl.HoaDonTrongNuocPdf] TABLE_NUM_ROWS = " + TABLE_NUM_ROWS);
			System.out.println("[ErpHoadonPheLieuPdfSvl.HoaDonTrongNuocPdf] TABLE_ROW_HEIGHT = " + TABLE_ROW_HEIGHT);
			
			int sokytu1sp = 30;
			if(sokytu1sp <= 0) sokytu1sp = 30;
			
			int len = pxkBean.getTensansham().length;
			
			while(spIndex < len && rowIndex < TABLE_NUM_ROWS) {
				
				String tenSp = pxkBean.getTensansham()[spIndex];
				
				//Xu ly ten san pham
				List<String> _tenList = new ArrayList<String>();
				String[] words = tenSp.trim().replaceAll("  ", " ").split(" "); //Tat ca cac tu trong ten san pham
				String _ten = "", _ten2 = "";
				for(int _i = 0; _i < words.length; _i++) {
					//Xử lý khi 1 từ > 30 ký tự
					if(words[_i].length() > sokytu1sp) {
						if(_ten.trim().length() > 0) _tenList.add(_ten); //Thêm dòng cũ
						_tenList.add(words[_i]); //Thêm từ dài đó vào 1 dòng mới
						_ten = ""; //reset _ten
					} else {						
						_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
						if(_ten2.length() > sokytu1sp) {
							_tenList.add(_ten);
							_ten = words[_i];
						} else {
							_ten = _ten2;
						}
					}
				}
				if(_ten.trim().length() > 0) _tenList.add(_ten);
				
				soDongSp += _tenList.size();
            	
            	/*for(int _i = 0; _i < _tenList.size(); _i++) {
            		System.out.println("ErpHoadonphelieuPdf.in Dòng [" + _i + "] " + _tenList.get(_i));
            	}*/
				
            	double thanhtien = 0;
            	double dongia = 0;
            	double soluong = 0;
				try {
					if(pxkBean.getKhoanmucDTId().equals("400000")) // HOA DON CHIET KHAU
					{
						
						if(pxkBean.getDongia()[spIndex].length() > 0 && pxkBean.getSoluong()[spIndex].length() > 0 )
						{
							dongia = Double.parseDouble(pxkBean.getDongia()[spIndex].replaceAll(",", ""));
							soluong = Double.parseDouble(pxkBean.getSoluong()[spIndex].replaceAll(",", "") );
							if (dongia < 0) dongia = dongia*(-1);
						}
						thanhtien = Double.parseDouble(pxkBean.getTongtien()[spIndex].replaceAll(",", "") );
						System.out.println("Thành tiền "+thanhtien);
						if (thanhtien <  0) thanhtien = thanhtien*(-1);
						total_amount += Double.parseDouble(pxkBean.getTongtien()[spIndex].replaceAll(",", "") );
						if (total_amount <  0) total_amount = total_amount*(-1);
					}
					else
					{
						dongia = Double.parseDouble(pxkBean.getDongia()[spIndex].replaceAll(",", ""));
						soluong = Double.parseDouble(pxkBean.getSoluong()[spIndex].replaceAll(",", "") );
						thanhtien = Math.round( dongia * soluong);
						total_amount += dongia * soluong;
					}
				} catch (Exception ex) { System.out.println("Lỗi ["+spIndex+"] " + ex.getMessage());	}
				
				//System.out.println("[ErpHoadonPheLieuPdfSvl.HoaDonTrongNuocPdf] Thanh tien[" + spIndex + "] = " + total_amount);
				//System.out.println("[ErpHoadonPheLieuPdfSvl.HoaDonTrongNuocPdf] Quy doi[" + spIndex + "] = " + sanpham.getQuyDoiStr());

				String dg = " ";
				if (dongia > 0)
					dg = formatter.format(dongia);

				String tt = " ";
				if (thanhtien > 0)
					tt = formatter.format(thanhtien);
				
				String sl = " ";
				if (soluong > 0)
					sl = formatter2.format(soluong);
				//In khi có thể hiện toàn bộ thông tin của sản phẩm (1 dòng tên sp, 1 dòng quy cách, các dòng ghi chú)
				
				//In dong 1
				//System.out.println("[ErpHoadonPheLieuPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+ rowIndex +", In dòng 1 sp " + spIndex);
        		String[] arr = new String[]
				{ 
						Integer.toString(spIndex+1), 
						_tenList.size() > 0 ? _tenList.get(0) : "", 
						pxkBean.getDvt()[spIndex],
						sl, 
						dg,
						tt 
				};
        		
        		for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++)
				{
					cell = new PdfPCell(new Paragraph(arr[j], donhangFont));
					cell.setFixedHeight(TABLE_ROW_HEIGHT);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
					cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
					cell.setBorder(BORDER_WIDTH);
					table.addCell(cell);
				}
        		rowIndex++;
        		
        		//In cac dong ghi chu con lai
        		for(int z = 1; z < _tenList.size(); z++) {
        			arr = new String[]
  					{ 
						" ",
						_tenList.get(z), 
						" ", 
						" ",
						" ",
						" " 
  					};
        			for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++)
					{
						cell = new PdfPCell(new Paragraph(arr[j], donhangFont));
						cell.setFixedHeight(TABLE_ROW_HEIGHT);
						cell.setVerticalAlignment(Element.ALIGN_TOP);
						cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
						cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
						cell.setBorder(BORDER_WIDTH);
						table.addCell(cell);
					}
        			rowIndex++;
        		}
            	
            	spIndex++;
			}
			
			//In dong rong
			
			for(int i = rowIndex ; i < TABLE_NUM_ROWS; i++) {
				System.out.println("[ErpHoadonPheLieuPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+ i +", In dòng rỗng");
				String[] arr = new String[]
				{ 
					" ",
					" ", 
					" ", 
					" ",
					" ",
					" " 
				};
				for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++)
				{
					cell = new PdfPCell(new Paragraph(arr[j], donhangFont));
					cell.setFixedHeight(TABLE_ROW_HEIGHT);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
					cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
					cell.setBorder(BORDER_WIDTH);
					table.addCell(cell);
				}
			}
			
			
			/*for (int i = 0; i < TABLE_NUM_ROWS; i++)
			{
				if(i < len) {
					IErpHoadonphelieu_SP sanpham = (IErpHoadonphelieu_SP) spList.get(i);
					long thanhtien = 0;
	
					try {
						thanhtien = Math.round(sanpham.getDonGia() * sanpham.getSoLuong());
					} catch (Exception ex){	}
	
					total_amount = total_amount + thanhtien;
					System.out.println("[ErpHoadonPheLieuPdfSvl.HoaDonTrongNuocPdf] Thanh tien[" + count + "] = " + total_amount);
					System.out.println("[ErpHoadonPheLieuPdfSvl.HoaDonTrongNuocPdf] Quy doi[" + count + "] = " + sanpham.getQuyDoiStr());
	
					String dg = " ";
					if (sanpham.getDonGia() > 0)
						dg = FormatNumber(sanpham.getDonGia(), 0);
	
					String tt = " ";
					if (thanhtien > 0)
						tt = FormatNumber(thanhtien, 0);
	
					String[] arr = new String[]
					{ 
							Integer.toString(count), 
							sanpham.getTenXuatHoaDon(), 
							sanpham.getDonViTinh() + "\n(" + sanpham.getQuyDoiStr() + ")",
							FormatNumber((double) sanpham.getSoLuong(), 0), 
							dg, 
							tt 
					};
	
					for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++)
					{
						cell = new PdfPCell(new Paragraph(arr[j], donhangFont));
						cell.setFixedHeight(TABLE_ROW_HEIGHT);
						cell.setVerticalAlignment(Element.ALIGN_TOP);
						cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
						cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
						cell.setBorder(BORDER_WIDTH);
						table.addCell(cell);
					}
					count++;
				} else {
					for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++)
					{
						cell = new PdfPCell(new Paragraph("  ", donhangFont));
						cell.setBorder(BORDER_WIDTH);
						cell.setFixedHeight(TABLE_ROW_HEIGHT);
						table.addCell(cell);
					}
				}
			}*/
			document.add(table);
			
			double tienCK = 0;//pxkBean.getTienChietKhau() > 0 ? pxkBean.getTienChietKhau() : total_amount * pxkBean.getChietkhau() / 100;
			double tienKM = 0;//pxkBean.getTienkhuyenmai();
			double tienSauCKKM = total_amount - tienCK - tienKM;
			
			//AMOUNTS TABLE
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new float[] {TABLE_AMOUNT_LEFT_WIDTH, TABLE_AMOUNT_RIGHT_WIDTH});
			
			//Left0: Trống
			cell = new PdfPCell(new Paragraph(" ", donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(TABLE_AMOUNT_LEFT_PADDING_LEFTS[0]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[0]);
			table.addCell(cell);
			//Right0: Cộng tiền hàng
			cell = new PdfPCell(new Paragraph(formatter3.format(tienSauCKKM), donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(TABLE_AMOUNT_RIGHT_PADDING_RIGHTS[0]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[0]);
			table.addCell(cell);
			
			//Left1: VAT
			double vat = 0;
			try { vat = Math.round(Double.parseDouble(pxkBean.getVat())); } catch(Exception e) { }
			double tienVAT = Math.round(tienSauCKKM * vat / 100);
			double tienSauVAT = Math.round(tienSauCKKM + tienVAT);
			if(tienVAT < 0) tienVAT = tienVAT*(-1);
			if(tienSauVAT < 0) tienSauVAT = tienSauVAT*(-1);
			String tienBangChu = DocTien.docTien(Math.round(tienSauVAT)) + "./.";
			
			cell = new PdfPCell(new Paragraph(formatter3.format(vat), donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(TABLE_AMOUNT_LEFT_PADDING_LEFTS[1]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[1]);
			table.addCell(cell);
			//Right1: Tiền thuế GTGT
			cell = new PdfPCell(new Paragraph(formatter3.format(tienVAT), donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(TABLE_AMOUNT_RIGHT_PADDING_RIGHTS[1]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[1]);
			table.addCell(cell);
			
			//Left2: Trống
			cell = new PdfPCell(new Paragraph(" ", donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(TABLE_AMOUNT_LEFT_PADDING_LEFTS[2]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[2]);
			table.addCell(cell);
			//Right2: Tổng cộng tiền thanh toán
			cell = new PdfPCell(new Paragraph(formatter3.format(tienSauVAT), donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(TABLE_AMOUNT_RIGHT_PADDING_RIGHTS[2]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[2]);
			table.addCell(cell);
			
			//Left3: Trống
			cell = new PdfPCell(new Paragraph(" ", donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(TABLE_AMOUNT_LEFT_PADDING_LEFTS[3]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[3]);
			table.addCell(cell);
			//Right3: Số tiền viết bằng chữ
			cell = new PdfPCell(new Paragraph(tienBangChu, donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(TABLE_AMOUNT_RIGHT_PADDING_RIGHTS[3]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[3]);
			table.addCell(cell);

			document.add(table);
			
			String ngaythangnam = pxkBean.getNgayghinhan();
			String ngay = ngaythangnam.substring(8, 10);
			String thang = ngaythangnam.substring(5, 7);
			String nam = ngaythangnam.substring(0, 4);
			
			Paragraph p = new Paragraph(ngay + "                  " + thang + "               " + nam, donhangFont);
			p.setAlignment(Element.ALIGN_RIGHT);
			p.setSpacingBefore(TABLE_AMOUNT_ROW_HEIGHTS[4]);
			document.add(p);

			
			//CLOSE DOCUMENT
			document.close();
			
			
			
			/*List<IErpHoadonphelieu_SP> spList = pxkBean.GetListSanPham();
			float lineSpacing = 0;

			String ghichu = "";
			String noidungck = "";
			String thue = "";

			ghichu = pxkBean.getGhiChu();
			noidungck = pxkBean.getNoidungCK();
			thue = "" + pxkBean.getVAT();
			System.out.println("Thue la: " + thue);

			// Table Content
			table = new PdfPTable(6);
			//table.setSpacingBefore(1.78f * CONVERT);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);
			table.setWidths(TABLE_COLUMN_WIDTHS);

			Font font4 = new Font(bf, 9, Font.BOLD);
			Font font5 = new Font(bf, 9, Font.NORMAL);
			PdfPCell cells = new PdfPCell();
			cells.setBorder(0);

			double total_amount = 0;
			double total_quantity = 0;
			int count = 0;
			for (int i = 0; i < TABLE_NUM_ROWS; i++)
			{
				if(i < len) {
					++count;
					IErpHoadonphelieu_SP sanpham = (IErpHoadonphelieu_SP) spList.get(i);
					long thanhtien = 0;
					try {
						thanhtien = Math.round(sanpham.getDonGia() * sanpham.getSoLuong());
					} catch (Exception ex) {}
	
					total_amount = total_amount + sanpham.getDonGia() * sanpham.getSoLuong();
					System.out.println("Thanh tien: " + total_amount);
					total_quantity += sanpham.getSoLuong();
	
					String dg = " ";
					if (sanpham.getDonGia() > 0)
						dg = FormatNumber(sanpham.getDonGia(), 0);
	
					String tt = " ";
					if (thanhtien > 0)
						tt = FormatNumber(thanhtien, 0);
					String[] arr = new String[]
					{ 
							Integer.toString(count), //0 
							sanpham.getMaSanPham(), //1 
							sanpham.getDonViTinhEng(), //2
							FormatNumber((double) sanpham.getSoLuong(), 0), //3 
							dg, //4
							tt //5
					};
	
					for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++)
					{
						cells = new PdfPCell();
						Paragraph para = new Paragraph(arr[j], font4);
						para.setAlignment(Element.ALIGN_CENTER);
						cells.addElement(para);
						if(j==1) {
							para.setAlignment(Element.ALIGN_LEFT);
							Paragraph pTen = new Paragraph(sanpham.getTenXuatHoaDon(), font4);
							pTen.setAlignment(Element.ALIGN_LEFT);
							cells.setPaddingLeft(0.5f*CONVERT);
							cells.addElement(pTen);
						}
						
						cells.setFixedHeight(TABLE_ROW_HEIGHT);
						cells.setPadding(0);
						cells.setBorder(Rectangle.NO_BORDER);
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);						
						
						table.addCell(cells);
					}
				} else {
					for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++)
					{
						cells = new PdfPCell();
						Paragraph para = new Paragraph(" ", font4);
						cells.addElement(para);
						cells.setFixedHeight(TABLE_ROW_HEIGHT);
						cells.setPadding(0);
						cells.setBorder(Rectangle.NO_BORDER);
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						
						table.addCell(cells);
					}
				}
			}
			
			System.out.println("[ErpHoadonPheLieuPdfSvl.HoaDonNuocNgoaiPdf] count = " + count);
			
			//TOTAL
			for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++)
			{
				if (j == 3)
				{
					cells = new PdfPCell(new Paragraph("" + total_quantity, font4));
				} else if (j == 5)
				{
					cells = new PdfPCell(new Paragraph(FormatNumber(total_amount, 0), font4));
				} else
				{
					cells = new PdfPCell(new Paragraph("  ", font4));
				}
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setBorder(Rectangle.NO_BORDER);
				cells.setFixedHeight(TOTAL_HEIGHT);
				table.addCell(cells);
			}
			document.add(table);
		
			//TOTAL IN WORDS
			String tien = DocTienEN.convert(Math.round(pxkBean.getTongtiensauVAT()));
			System.out.println("[ErpHoadonPheLieuPdfSvl.HoaDonNuocNgoaiPdf] Total in words :" + tien);
			
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);
			float[] totalInWordsWidths = {18.0f*CONVERT};
			table.setWidths(totalInWordsWidths);
			
			cells = new PdfPCell(new Paragraph("US Dollars    " + tien, new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			cells.setPaddingLeft(3.5f * CONVERT);
			cells.setFixedHeight(TOTAL_IN_WORDS_HEIGHT);
			cells.setBorder(Rectangle.NO_BORDER);
			
			table.addCell(cells);

			document.add(table);*/
			
			
			//REMARKS (3 ghi chú)			
			

			
			
			
			/*
			NumberFormat formatter = new DecimalFormat("#,###,###");
			PdfWriter.getInstance(document, outstream);
			document.open();
			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\tahoma.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			IErpCauHinhInHoaDon soNumber = new ErpCauHinhInHoaDon();
			soNumber.init_SoConfig();

			Paragraph sohd = new Paragraph(pxkBean.getId(), new Font(bf, 9, Font.BOLD));
			sohd.setIndentationLeft(soNumber.getMarginLeft() * CONVERT);
			sohd.setSpacingBefore(soNumber.getMarginTop() * CONVERT);
			document.add(sohd);

			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font3 = new Font(bf, 10, Font.BOLDITALIC);
			List<IErpHoadonphelieu_SP> spList = pxkBean.GetListSanPham();

			IErpCauHinhInHoaDon khachhang_config = new ErpCauHinhInHoaDon();
			khachhang_config.init_KhachHang_Config();
			float lineSpacing = khachhang_config.getLineSpacing() * CONVERT;

			/************************************************
			 * Ho Ten nguoi mua hang Ten don vi Dia Chi Ma So Thue Hinh thuc TT
			 **********************************************/
			/*dbutils db = new dbutils();
			String sql = "select * from Erp_KhachHang where pk_seq = '" + pxkBean.getNppId() + "'";
			String address = "";
			String taxCode = "";
			String name_of_buyer = "";
			String name_of_company = "";

			try
			{
				ResultSet rs = db.get(sql);
				if (rs.next())
				{
					if (rs.getString("MST") != null)
						taxCode = rs.getString("MST");
					address = rs.getString("DiaChi");
					if (rs.getString("NguoiLienhe") != null)
						name_of_buyer = rs.getString("NguoiLienhe");
					name_of_company = rs.getString("Ten");
				}
				rs.close();
				db.shutDown();
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}

			String ghichu = "";
			String noidungck = "";
			String thue = "";

			ghichu = pxkBean.getGhiChu();
			noidungck = pxkBean.getNoidungCK();
			thue = "" + pxkBean.getVAT();
			System.out.println("Thue la: " + thue);

			Paragraph tendonvi = new Paragraph(name_of_buyer, font3);
			tendonvi.setIndentationLeft(khachhang_config.getMarginLeft() * CONVERT);
			tendonvi.setSpacingAfter(4.0f);
			document.add(tendonvi);

			Paragraph nguoimuahang = new Paragraph(name_of_company.toUpperCase(), new Font(bf, 9, Font.BOLD));
			nguoimuahang.setSpacingAfter(6.0f);//
			nguoimuahang.setIndentationLeft(khachhang_config.getMarginLeft() * CONVERT);
			document.add(nguoimuahang);

			Paragraph masothue = new Paragraph(taxCode.toUpperCase() + "  ", new Font(bf, 9, Font.BOLD));
			masothue.setIndentationLeft(khachhang_config.getMarginLeft() * CONVERT);
			masothue.setSpacingAfter(6.0f);
			document.add(masothue);

			Paragraph diachi = new Paragraph(address, new Font(bf, 9, Font.BOLD));
			diachi.setIndentationLeft(khachhang_config.getMarginLeft() * CONVERT);
			diachi.setSpacingAfter(5.55f);
			document.add(diachi);

			Paragraph httt = new Paragraph(" " + pxkBean.gethinhthuctt(), new Font(bf, 9, Font.BOLD));
			httt.setIndentationLeft(khachhang_config.getMarginLeft() * CONVERT);
			document.add(httt);

			// Table Content
			PdfPTable table = new PdfPTable(6);
			table.setSpacingBefore(1.8f * CONVERT);

			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);

			IErpCauHinhInHoaDon tbDonHang = new ErpCauHinhInHoaDon();
			tbDonHang.init_DonHang_Config();

			float[] withs =
			{ tbDonHang.getNoColumn() * CONVERT, tbDonHang.getProductColumn() * CONVERT,
					tbDonHang.getUnitColumn() * CONVERT, tbDonHang.getQuantityColumn() * CONVERT,
					tbDonHang.getUniPriceColumn() * CONVERT, tbDonHang.getAmoutColumn() * CONVERT };
			table.setWidths(withs);

			Font font4 = new Font(bf, 9, Font.BOLD);
			PdfPCell cells = new PdfPCell();

			double total_amount = 0;
			// Chieu dai table don hang
			float tbHeight = tbDonHang.getTableHeight();
			// So dong don hang
			int numberOfLine = tbDonHang.getNumberOfRow();
			// Do rong cua moi dong
			lineSpacing = (tbHeight / numberOfLine) * CONVERT;
			int count = 1;
			for (int i = 0; i < len; i++)
			{
				IErpHoadonphelieu_SP sanpham = (IErpHoadonphelieu_SP) spList.get(i);
				long thanhtien = 0;

				try
				{
					thanhtien = Math.round(sanpham.getDonGia() * sanpham.getSoLuong());

				} catch (Exception ex)
				{
				}

				total_amount = total_amount + thanhtien;
				System.out.println("Thanh tien: " + total_amount);

				String dg = " ";
				if (sanpham.getDonGia() > 0)
					dg = FormatNumber(sanpham.getDonGia(), 0);

				String tt = " ";
				if (thanhtien > 0)
					tt = FormatNumber(thanhtien, 0);

				String[] arr = new String[]
				{ Integer.toString(count), sanpham.getTenXuatHoaDon(), sanpham.getDonViTinh(),
						FormatNumber((double) sanpham.getSoLuong(), 0), dg, tt };

				for (int j = 0; j < 6; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], font4));
					cells.setFixedHeight(lineSpacing);
					if (j == 1 && arr[j].length() > 45)
					{
						cells.setFixedHeight(lineSpacing * 2f);
					}

					if (j == 2)
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					else
					{
						if (j < 3)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					cells.setBorder(Rectangle.NO_BORDER);
					table.addCell(cells);
				}
				count++;
			}
			System.out.println("Count1: " + count);
			if (count < 13)
			{
				for (int k = 0; k < (13 - count); k++)
				{
					for (int j = 0; j < 6; j++)
					{
						cells = new PdfPCell(new Paragraph("  ", font4));
						cells.setBorder(Rectangle.NO_BORDER);
						cells.setFixedHeight(lineSpacing);
						table.addCell(cells);
					}
				}
			}
			document.add(table);
			PdfPTable tbFooter = new PdfPTable(4);
			double vat = Math.round(pxkBean.getVAT() / pxkBean.getTienSauCK_KM() * 100);

			/*****************************************************************************************
			 * | |CONG TIEN HANG | %VAT | |TIEN THUE GTDT | | |TONG CONG TIEN
			 * THANH TOAN |
			 *****************************************************************************************/
			/*tbFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbFooter.setWidthPercentage(100);
			float[] ftwiths =
			{ 36f * 4.5f, 36f, 8.5f * 36f, 4.0f * 36f };
			tbFooter.setWidths(ftwiths);
			for (int j = 0; j < 4; j++)
			{
				if (j == 3)
				{
					cells = new PdfPCell(new Paragraph(new Paragraph(FormatNumber(total_amount, 0), new Font(bf, 10,
							Font.BOLD))));
				} else
				{
					cells = new PdfPCell(new Paragraph("", font4));
				}
				cells.setBorder(Rectangle.NO_BORDER);
				cells.setFixedHeight(lineSpacing);
				tbFooter.addCell(cells);
			}

			for (int j = 0; j < 4; j++)
			{
				if (j == 1)
				{
					cells = new PdfPCell(new Paragraph("" + vat, font4));
				} else if (j == 3)
				{
					cells = new PdfPCell(new Paragraph(new Paragraph(FormatNumber(pxkBean.getVAT(), 0), new Font(bf, 10,
							Font.BOLD))));
				} else
				{
					cells = new PdfPCell(new Paragraph("", font4));
				}
				cells.setBorder(Rectangle.NO_BORDER);
				cells.setFixedHeight(lineSpacing);
				tbFooter.addCell(cells);
			}
			for (int j = 0; j < 4; j++)
			{

				if (j == 3)
				{
					String ttcv = FormatNumber(pxkBean.getTongtiensauVAT(), 0);
					cells = new PdfPCell(new Paragraph(ttcv, font4));
				} else
				{
					cells = new PdfPCell(new Paragraph("", font4));
				}
				cells.setBorder(Rectangle.NO_BORDER);
				cells.setFixedHeight(lineSpacing);
				tbFooter.addCell(cells);
			}
			document.add(tbFooter);
			DocTien doctien = new DocTien();

			String tien = doctien.docTien(Math.round(pxkBean.getTongtiensauVAT()));

			tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
			if (tien.equals("Ðồnng"))
				tien = "Không Ðồng";
			System.out.println(" Tien là :" + tien);
			Paragraph paradoctien = new Paragraph("            " + tien, new Font(bf, 10, Font.BOLD));
			paradoctien.setSpacingBefore(12.0f);
			paradoctien.setIndentationLeft(140.575f);

			document.add(paradoctien);

			document.close();*/
		}
		catch (Exception e)
		{
			System.out.println("[ErpHoadonPheLieuPdfSvl.HoaDonTrongNuocPdf] Loi Trong Qua Trinh In: " + e.getMessage());
		}
	}

	private static String FormatNumber(double number, int round)
	{
		// System.out.println("Truoc kho Format: " + number);
		String format = "#,###,###";
		if (round >= 1)
			format += ".";

		for (int i = 0; i < round; i++)
			format += "0";

		// System.out.println("Chuoi Format: " + format);

		DecimalFormat df = new DecimalFormat(format);
		String result = df.format(number);

		if (number > 999)
		{
			// result = result.replaceAll(".", "+");
			result = result.replaceAll(",", ".");
			if (round > 0)
				result = result.substring(0, result.length() - (round + 1)) + ","
						+ result.substring(result.length() - round);
			// result = result.replaceFirst("-", ",");
		} else
			result = result.replaceAll(",", ".");

		// System.out.println("ket qua: " + result);
		return result;
	}

	public static void main(String[] arg) throws DocumentException, IOException
	{

	}
	
	private String getEnDateTime(String date) {
		if(date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			
			thang = thang.equals("01") ? "Jan" : thang.equals("02") ? "Feb" : thang.equals("03") ? "Mar" : thang.equals("04") ? "Apr" : thang.equals("05") ? "May" : thang.equals("06") ? "Jun" : thang.equals("07") ? "Jul" : thang.equals("08") ? "Aug" : thang.equals("09") ? "Sep" : thang.equals("10") ? "Oct" : thang.equals("11") ? "Nov" : thang.equals("12") ? "Dec" : " ";
			return thang + " " + ngay + ", " + nam;
		} else {
			return "";
		} 
	}
	
	private String getVnDateTime(String date) {
		if(date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			return ngay + "-" + thang + "-" + nam;
		} else {
			return "";
		}
	}
	

	
}
