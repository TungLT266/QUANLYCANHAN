package geso.traphaco.erp.servlets.giamgiahangban;

import geso.traphaco.center.beans.doctien.DocTien;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.giamgiahangban.IErpGiamgiahangban;
import geso.traphaco.erp.beans.giamgiahangban.imp.ErpGiamgiahangban;
import geso.traphaco.erp.beans.hoadon.IErpHoaDon_SP;
import geso.traphaco.erp.beans.hoadon.imp.ErpHoaDon;
import geso.traphaco.erp.beans.hoadon.imp.ErpHoanDon_SP;
import geso.traphaco.erp.beans.hoadontaichinh.IErpHoaDonTaiChinh;

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

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpGiamgiahangbanPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final float CM = 28.3464f;

	public ErpGiamgiahangbanPdfSvl() {
		super();
	}

	float CONVERT = 28.346457f;  // =1cm
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpGiamgiahangban obj;
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);

		obj = new ErpGiamgiahangban(id);
		obj.init();
		obj.DbClose();

		/*response.setContentType("application/xlsm");
		response.setHeader("Content-Disposition", "attachment; filename=HoaDonGTGHB" + obj.getSohoadon() + ".xlsm");
		try {
			OutputStream outstream = response.getOutputStream();
			this.HoaDonTrongNuocExcel(outstream, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", " inline; filename=HoaDonTaiChinh.pdf");
		
		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();
		
		if(userId.equals("100472")) //HẢI PHÒNG
		{
			this.CreateHdPdf_HaiPhong(document, outstream,obj, id);
		}
		else if (userId.equals("100503")|| userId.equals("100504")){// HÀ NỘI
			this.CreateHdPdf_HANOI(document, outstream,obj, id);
		}
		else if(userId.equals("100514")){// ĐÀ NẴNG
			this.CreateHdPdf_DANANG(document, outstream,obj, id);
		}
		else{
			this.CreateHdPdf_HCM(document, outstream,obj, id);
		}
		
		document.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}	
	/**
	 * Xử lý để in sản phẩm trong phần in hóa đơn trong nước
	 * @param hdBean
	 * @param _tenList
	 * @param sanpham
	 * @param spIndex
	 * @param tenSp
	 * @param qcSp
	 * @param sokytu1sp
	 * @param prev_tensp
	 * @param temp_tensp
	 * @param changeSpCore
	 * @return
	 */
	protected boolean xuLyTenList(IErpGiamgiahangban hdBean, List<String> _tenList, IErpHoaDon_SP sanpham, int spIndex, String tenSp, String qcSp, int sokytu1sp) 
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
		// int soDongDanhChoTenSanPham = _tenList.size();

		// Xu ly quy cach
		int countSoDongQuyCach = 0;
		words = qcSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong quy cach
		_ten = "";
		for (int _i = 0; _i < words.length; _i++) {
			// Xử lý khi 1 từ > 40 ký tự
			if (words[_i].length() > sokytu1sp) {
				if (_ten.trim().length() > 0) {
					_tenList.add(_ten); // Thêm dòng cũ
					countSoDongQuyCach++;
				}
				_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
				countSoDongQuyCach++;
				_ten = ""; // reset _ten
			} else {
				_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
				if (_ten2.length() > sokytu1sp) {
					_tenList.add(_ten);
					countSoDongQuyCach++;
					_ten = words[_i];
				} else {
					_ten = _ten2;
				}
			}
		}
		if (_ten.trim().length() > 0) {
			_tenList.add(_ten);
			countSoDongQuyCach++;
		}

		String sanpham_ghichu = "";
		int countGhiChu = 0;
		String[] arrGhiChu = new String[] {};
		if (sanpham_ghichu != null && sanpham_ghichu.trim().length() > 0) {
			arrGhiChu = sanpham_ghichu.split("__");
			countGhiChu = arrGhiChu.length;

			for (int j = 0; j < arrGhiChu.length; j++) {
				if (arrGhiChu[j].equals("NA"))
					arrGhiChu[j] = "";
			}

			if (countGhiChu > 3)
				countGhiChu = 3;
		}

		for (int _j = 0; _j < arrGhiChu.length; _j++) {
			words = arrGhiChu[_j].trim().replaceAll("  ", " ") .split(" "); // Tat ca cac tu trong ghi chu
			_ten = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xử lý khi 1 từ > 45 ký tự
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
			if (_ten.trim().length() > 0)
				_tenList.add(_ten);
		}
		
		return true;
	}

	/**
	 * In hóa đơn trong nước excel
	 * @param out
	 * @param hdBean
	 */
	private void HoaDonTrongNuocExcel(OutputStream out, IErpGiamgiahangban hdBean) 
	{

		NumberFormat formatter = new DecimalFormat("###,###,###,###,###.###");

		int TABLE_NUM_ROWS = ErpHoaDon.getSoDongSanPham("100000");
		int S2_START_INDEX = 20;

		try {
			
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

			// cell = cells1.getCell("N8"); cell.setValue(hdBean.getKyHieu());
			// //Ký hiệu (Serial)

			cell = cells1.getCell("E12");
			cell.setValue(""); // Họ tên người mua hàng
			cell = cells1.getCell("E13");
			cell.setValue(hdBean.getNccTen()); // Đơn vị
			cell = cells1.getCell("E14");
			cell.setValue(hdBean.getNccDiaChi()); // Địa chỉ
			cell = cells1.getCell("E15");
			cell.setValue(hdBean.getNccMaSoThue()); // Mã số thuế
			cell = cells1.getCell("E16");
			cell.setValue("TM/CK"); // Hình thức thanh toán			

			// Sản phẩm
			List<IErpHoaDon_SP> spList = new ArrayList<IErpHoaDon_SP>();
			IErpHoaDon_SP sp = new ErpHoanDon_SP();
			sp.setTenXuatHoaDon(hdBean.getDiengiai());
			sp.setSoLuong(1);
			
			double dgia = 0;
			String[] sotientanggiam = hdBean.getSotien();
			if(sotientanggiam != null && sotientanggiam.length > 0) {
				for(int z = 0; z < sotientanggiam.length; z++) {
					System.out.println("sotientanggiam["+z+"] = " + sotientanggiam[z]);
					try { dgia += Math.abs(Double.parseDouble(sotientanggiam[z].replaceAll(",", ""))); } catch(Exception e) { }
				}
			}
			sp.setDonGia(dgia);
			
			spList.add(sp);
			
			int spIndex = 0;
			int rowIndex = 0;
			double total_amount = 0;

			int sokytu1sp = ErpHoaDon.getSoKyTu1DongSanPham("100000");
			if (sokytu1sp <= 0) {
				sokytu1sp = 40;
			}

			int stt = 0;
			while (spIndex < spList.size() && rowIndex < TABLE_NUM_ROWS) 
			{
				IErpHoaDon_SP sanpham = (IErpHoaDon_SP) spList.get(spIndex);

				double thanhtien = 0;
				try {
					thanhtien = Math.round(sanpham.getDonGia() * sanpham.getSoLuong());
					total_amount += sanpham.getDonGia() * sanpham.getSoLuong();
				} catch (Exception ex) { }
				
				//System.out.println("[ErpGiamgiahangbanPdf...] sanpham.getTenXuatHoaDon() = " + sanpham.getTenXuatHoaDon());

				// TÊN XUẤT HÓA ĐƠN } QUY CÁCH
				String[] ttsp = sanpham.getTenXuatHoaDon().split("}");

				String tenSp = ttsp[0].trim();
				String qcSp = ttsp.length > 1 ? ttsp[1].trim() : "";

				// Xu ly ten san pham
				List<String> _tenList = new ArrayList<String>();
				
				//Tạo _tenList
				xuLyTenList(hdBean, _tenList, sanpham, spIndex, tenSp, qcSp, sokytu1sp);

				stt++;

				if(_tenList.size() > 0) {
					int localIndex = 0;
					// In dong 1
					// Cột stt sản phẩm
					cell = cells1.getCell("A" + (S2_START_INDEX + rowIndex));
					cell.setValue("" + (stt));
					// Cột tên sản phẩm
					cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
					cell.setValue(_tenList.size() > 0 ? _tenList.get(0) : "");
					// Cột Đơn vị tính
					cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
					cell.setValue("");
					// Cột Số lượng
					cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
					cell.setValue("");
					// Cột Đơn giá
					cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
					cell.setValue("");
					// Cột Thành tiền
					cell = cells1.getCell("L" + (S2_START_INDEX + rowIndex));
					cell.setValue(formatVN(formatter.format(thanhtien)));
	
					/*// Merge các cột số lượng, đơn giá, thành tiền
					cells1.merge((S2_START_INDEX + rowIndex - 1), 8, (S2_START_INDEX + rowIndex - 1), 9);
					cells1.merge((S2_START_INDEX + rowIndex - 1), 10, (S2_START_INDEX + rowIndex - 1), 11);
					cells1.merge((S2_START_INDEX + rowIndex - 1), 12, (S2_START_INDEX + rowIndex - 1), 13);*/
					
					localIndex++;
					rowIndex++;
	
					// In cac dong ghi chu con lai
					for (int z = localIndex; z < _tenList.size(); z++) {
	
						// Cột tên sản phẩm
						cell = cells1.getCell("B" + (S2_START_INDEX + rowIndex));
						cell.setValue(_tenList.get(z));
						// Cột Đơn vị tính
						cell = cells1.getCell("G" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cột Số lượng
						cell = cells1.getCell("H" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cột Đơn giá
						cell = cells1.getCell("J" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
	
						rowIndex++;
					}
				}

				spIndex++;
			}

			System.out.println("[ErpGiamgiahangbanPdfSvl.in] rowIndex = " + rowIndex);

			double tienSauCKKM = total_amount;
			double vat = 0;
			try { vat = Math.round(Double.parseDouble(hdBean.getVat())); } catch(Exception e) { }			
			double tienVAT = Math.round(tienSauCKKM * vat / 100);
			double tienSauVAT = Math.round(tienSauCKKM + tienVAT);
			String tienBangChu = DocTien.docTien(Math.round(tienSauVAT)) + "./.";
			
			String ngaythangnam = hdBean.getNgayhoadon();
			String ngay = ngaythangnam.substring(8, 10);
			String thang = ngaythangnam.substring(5, 7);
			String nam = ngaythangnam.substring(0, 4);

			cell = cells1.getCell("D42");
			cell.setValue(formatVN(formatter.format(vat)));
			cell = cells1.getCell("L41");
			cell.setValue(formatVN(formatter.format(total_amount)));
			cell = cells1.getCell("L42");
			cell.setValue(formatVN(formatter.format(tienVAT)));
			cell = cells1.getCell("L43");
			cell.setValue(formatVN(formatter.format(tienSauVAT)));
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

	public static String formatVN(String so) 
	{
		String result = so.replaceAll(",", "@");
		result = result.replaceAll("[.]", ",");
		result = result.replaceAll("@", ".");

		return result;
	}

	/*private String getEnDateTime(String date) {
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
			return "";
		}
	}

	private String getEnNewDateTime(String date) {
		if (date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			return ngay + "/" + thang + "/" + nam;
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
			return "";
		}
	}*/
	
	
	private void CreateHdPdf_HaiPhong(Document document, ServletOutputStream outstream, IErpGiamgiahangban pxkBean, String hdId) throws IOException
	{
		try{
			dbutils db = new dbutils();
			
			//LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI)
			String query =
				" select a.ngayghinhan, a.ngayhoadon, a.kyhieuhoadon, a.sohoadon, a.vat, a.bvat, a.avat  \n"+
				" , a.khachhang_fk, b.ten as khTen, a.diengiai, a.tungay, a.denngay, ISNULL(a.TIENTE_FK, 0) AS TIENTE_FK \n"+  
			 	" , isnull(b.MST, '') as KhMST, isnull(b.DiaChi, '') as KhDiaChi, isnull(NguoiLienhe, '') as KhNguoiLienHe \n"+  
			 	"	from erp_giamgiahangban a \n"+  
			 	"	inner join erp_khachhang b on a.khachhang_fk = b.pk_seq \n"+  
			 	" where a.pk_seq = '"+hdId+"' \n";
						
			String SOHOADON="";
			String KYHIEU="";
			String NGAYXUATHD ="";
			String HINHTHUCTT="";
			String MASOTHUE="";
			String DIACHI="";
			String NGUOIMUA="";
			String TENKH="";
			String diengiai ="";
			double TIENCK=0;
			double VAT=0;
			double TONGTIENAVAT=0;
			double Bvat =0;
			String HANGHOADICHVU = "";
			String DIACHIGIAOHANG = "";
			
			System.out.println("THONGTINNPP__:"+query);
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					SOHOADON = rs.getString("sohoadon");
					KYHIEU = rs.getString("kyhieuhoadon");
					NGAYXUATHD = rs.getString("NGAYGHINHAN");
					diengiai = rs.getString("diengiai");
					//HINHTHUCTT = rs.getString("HINHTHUCTT");
					MASOTHUE = rs.getString("KhMST");
					DIACHI = rs.getString("KhDiaChi");
					NGUOIMUA = rs.getString("KhNguoiLienHe");
					TENKH = rs.getString("khTen");
					//TIENCK = rs.getDouble("TIENCK");
					VAT = rs.getDouble("vat");
					TONGTIENAVAT = rs.getDouble("avat");
					Bvat = rs.getDouble("bvat");
					//HANGHOADICHVU = rs.getString("GHICHU");
					//DIACHIGIAOHANG = rs.getString("DIACHIGIAOHANG");
				}
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			//document.setPageSize(PageSize.A4.rotate()); CANH HÓA ĐƠN THEO CHIỀU DỌC
			document.setMargins(0.0f*CONVERT, 1.0f*CONVERT, 4.8f*CONVERT, 2.0f*CONVERT); // L,R, T, B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			//NGÀY THÁNG NĂM
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = NGAYXUATHD.split("-");
			Paragraph hd = new Paragraph(ngayHD[2] + "                 " + ngayHD[1] +  "             " + ngayHD[0] , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setPaddingLeft(1.8f*CONVERT);
			cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			document.add(tableheader);
			
			//NGƯỜI MUA HÀNG
			
			PdfPTable table1 = new PdfPTable(1);
			table1.setWidthPercentage(100);
			float[] withs1 = {100f};
			table1.setWidths(withs1);
			//table1.setSpacingBefore(0.1f*CONVERT);
					
			cell = new PdfPCell();	
			hd = new Paragraph(NGUOIMUA + " " +DIACHIGIAOHANG, new Font(bf, 12, Font.NORMAL));//NGUOI MUA
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(6.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table1.addCell(cell);
			document.add(table1);
			
			//ĐƠN VỊ
			
			PdfPTable table2 = new PdfPTable(1);
			table2.setWidthPercentage(100);
			float[] withs2 = {100f};
			table1.setWidths(withs2);
					
			cell = new PdfPCell();	
			hd = new Paragraph(TENKH , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table2.addCell(cell);
			document.add(table2);
			
			//ĐỊA CHỈ
			
			PdfPTable table3 =new PdfPTable(1);
			table3.setWidthPercentage(100);
			float[] withs3 = {100f};
			table3.setWidths(withs3);
						
			cell = new PdfPCell();	
			hd = new Paragraph( DIACHI , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table3.addCell(cell);
			document.add(table3);
			
			//HÌNH THỨC THANH TOÁN - MÃ SỐ THUẾ
			
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {200f,200f};
			table4.setWidths(withs4);
						
			cell= new PdfPCell();	
			hd = new Paragraph( HINHTHUCTT , new Font(bf, 12, Font.NORMAL)); /* HINH THUC THANH TOAN*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(4.3f*CONVERT);
			cell.setBorder(0);
			
			table4.addCell(cell);
			
			
			cell = new PdfPCell();	
			hd = new Paragraph( MASOTHUE , new Font(bf, 12, Font.NORMAL)); /*	MÃ SỐ THUẾ*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(2.9f*CONVERT);
			cell.setBorder(0);	
			
			table4.addCell(cell);
			
			document.add(table4);
			
			
			//THÔNG TIN SẢN PHẨM TRONG HÓA ĐƠN
			
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", " ", " "," " ," "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(1.5f*CONVERT);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 1.0f*CONVERT,9.0f*CONVERT, 3.0f*CONVERT, 2.0f*CONVERT, 3.2f*CONVERT, 3.2f*CONVERT, 3.0f*CONVERT };
			sanpham.setWidths(withsKM);
			
			PdfPCell cells = new PdfPCell();			
			
			String INIT_SANPHAM = 
				" select SUM(isnull(SOTIENTANGGIAM,0)) AS SOTIENTANGGIAM from ERP_GIAMGIAHANGBAN_HOADON  " +
				" where giamgia_fk = "+hdId+" \n";
			
			System.out.println("INIT_SANPHAM:"+INIT_SANPHAM);
			
			String TENSP="";
			String DONVITINH="";
			int STT=0;
			double SOLUONG=0;
			double DONGIA=0;
			double DONGIAGIAM =0;
			double CHIETKHAU =0;
			
			double THANHTIENGIAM = 0;
			double TONGTIENHANG = 0;
			
			double TIENSAUCK = 0; 
			
			ResultSet rsSP= db.get(INIT_SANPHAM);
					
			if(rsSP!=null){				
				while(rsSP.next()){
					STT++;
					Bvat = Math.round(rsSP.getDouble("SOTIENTANGGIAM"));
					TONGTIENAVAT = Bvat + Bvat*VAT/100;
									
					String[] arr = new String[] { Integer.toString(STT), diengiai , "" ,"" , "","", DinhDangCANFOCO(formatter.format(Bvat)) };
										
					for (int j = 0; j < th.length; j++)
					{
						System.out.println(arr[j]);
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.NORMAL)));
						if (j <= 1 ){ //STT, TÊN HÀNG HÓA DỊCH VỤ
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							//cells.setPaddingLeft(-0.5f*CONVERT);
						}
						else{							
								if(j == 2 )//ĐƠN VỊ TÍNH
								{
									cells.setHorizontalAlignment(Element.ALIGN_LEFT);
									cells.setPaddingLeft(1.4f*CONVERT);
								}								
								else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
									
									if(j == 3 )//SỐ LƯỢNG
									{
										cells.setPaddingRight(0.1f*CONVERT);
									}
									
									if(j == 4 )//ĐƠN GIÁ
									{
										cells.setPaddingRight(0.4f*CONVERT);
									}
									
									if(j == 5 )//ĐƠN GIÁ ĐÃ GIẢM
									{
										cells.setPaddingRight(0.7f*CONVERT);
									}
									if(j == 6 )//THÀNH TIỀN
									{
										//cells.setPaddingRight(-0.25f*CONVERT);
									}
								}
						}
						
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						cells.setBorder(0);
						//cells.setBorderWidth(1);
						cells.setFixedHeight(0.8f * CONVERT);	
						sanpham.addCell(cells);
					}
					
				}
			}
			rsSP.close();
			
			// DONG TRONG
			int kk=0;
			while(kk < 13-STT)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 12, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setFixedHeight(0.8f*CONVERT);
					cells.setBorder(0);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
			document.add(sanpham);
			
			//TỔNG TIỀN HÀNG
						
			PdfPTable footter = new PdfPTable(2);
			//footter.setSpacingBefore(1.2f*CONVERT);
			footter.setWidthPercentage(100);
			footter.setSpacingBefore(0.5f*CONVERT);
			
			float[] withsfooter = { 25.7f*CONVERT, 3.5f*CONVERT };
			footter.setWidths(withsfooter);
			
			cell = new PdfPCell();	
			
			
			hd = new Paragraph( HANGHOADICHVU , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(1.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);		
			
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangCANFOCO(formatter.format(Bvat)) , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			footter.addCell(cell);
			
			// TIỀN CHIẾT KHẤU - CỘNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph("" , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			TIENSAUCK = TONGTIENHANG - TIENCK ;
			
			cell = new PdfPCell();	
			hd = new Paragraph( "" , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//VAT - TIỀN THUẾ
			
			cell = new PdfPCell();	
			hd = new Paragraph( ""+ VAT , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangCANFOCO(formatter.format(Bvat*VAT/100)) , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//TỔNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph( "" , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);			
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangCANFOCO(formatter.format(TONGTIENAVAT)) , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			
			document.add(footter);
									
			
			//ĐỌC TIỀN RA CHỮ
			
			/*DocTien doctien = new DocTien();
					    
			String tien = doctien.docTien(Math.round(TONGTIENAVAT));
						
			//String tien = doctien.tranlate(tongtiencovat + "");
			tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
			if(tien.equals("Đồng"))
				 tien="Không Đồng";
			System.out.println(" Tien là :"+tien);
			
			Paragraph paradoctien = new Paragraph("" + tien, new Font(bf, 12, Font.NORMAL));
					    //paradoctien.setSpacingBefore(12.0f);
		    paradoctien.setSpacingBefore(3.0f);		    
		    paradoctien.setIndentationLeft(140.575f);
			
			document.add(paradoctien);*/
			
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private void CreateHdPdf_HCM(Document document, ServletOutputStream outstream, IErpGiamgiahangban pxkBean, String hdId) throws IOException
	{
		try{
			dbutils db = new dbutils();
			
			//LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI)
			String query =
				/*" SELECT HD.SOHOADON, HD.KYHIEU, HD.NGAYXUATHD, HD.HINHTHUCTT, KH.MST MASOTHUE, KH.DIACHI, ISNULL(HD.NGUOIMUA,'') NGUOIMUA, KH.TEN, ISNULL(HD.TIENCKTHUONGMAI,0) TIENCKTHUONGMAI," +
				"    HD.CHIETKHAU+HD.TIENCKTHUONGMAI TIENCK, HD.VAT, HD.TONGTIENAVAT, HD.HANGHOADICHVU, HD.GHICHU, ISNULL(HD.DIACHIGIAOHANG,'') DIACHIGIAOHANG \n"+
				" FROM ERP_HOADON HD LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=HD.KHACHHANG_FK \n"+
				" WHERE HD.PK_SEQ ='"+hdId+"' \n";*/
				
				" select a.ngayghinhan, a.ngayhoadon, a.kyhieuhoadon, a.sohoadon, a.vat, a.bvat, a.avat  \n"+
				" , a.khachhang_fk, b.ten as khTen, a.diengiai, a.tungay, a.denngay, ISNULL(a.TIENTE_FK, 0) AS TIENTE_FK \n"+  
			 	" , isnull(b.MST, '') as KhMST, isnull(b.DiaChi, '') as KhDiaChi, isnull(NguoiLienhe, '') as KhNguoiLienHe \n"+  
			 	"	from erp_giamgiahangban a \n"+  
			 	"	inner join erp_khachhang b on a.khachhang_fk = b.pk_seq \n"+  
			 	" where a.pk_seq = '"+hdId+"' \n";
						
			String SOHOADON="";
			String KYHIEU="";
			String NGAYXUATHD ="";
			String HINHTHUCTT="";
			String MASOTHUE="";
			String DIACHI="";
			String NGUOIMUA="";
			String TENKH="";
			String diengiai ="";
			double TIENCK=0;
			double VAT=0;
			double TONGTIENAVAT=0;
			double Bvat =0;
			String HANGHOADICHVU = "";
			String DIACHIGIAOHANG = "";
			
			System.out.println("THONGTINNPP__:"+query);
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					SOHOADON = rs.getString("sohoadon");
					KYHIEU = rs.getString("kyhieuhoadon");
					NGAYXUATHD = rs.getString("NGAYGHINHAN");
					diengiai = rs.getString("diengiai");
					//HINHTHUCTT = rs.getString("HINHTHUCTT");
					MASOTHUE = rs.getString("KhMST");
					DIACHI = rs.getString("KhDiaChi");
					NGUOIMUA = rs.getString("KhNguoiLienHe");
					TENKH = rs.getString("khTen");
					//TIENCK = rs.getDouble("TIENCK");
					VAT = rs.getDouble("vat");
					TONGTIENAVAT = rs.getDouble("avat");
					Bvat = rs.getDouble("bvat");
					//HANGHOADICHVU = rs.getString("GHICHU");
					//DIACHIGIAOHANG = rs.getString("DIACHIGIAOHANG");
				}
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			//document.setPageSize(PageSize.A4.rotate()); CANH HÓA ĐƠN THEO CHIỀU DỌC
			document.setMargins(1.8f*CONVERT, 0.0f*CONVERT, 4.5f*CONVERT, 2.0f*CONVERT); // L,R, T, B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			//NGÀY THÁNG NĂM
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = NGAYXUATHD.split("-");
			Paragraph hd = new Paragraph(ngayHD[2] + "               " + ngayHD[1] +  "             " + ngayHD[0] , new Font(bf, 12, Font.ITALIC));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setPaddingLeft(1.5f*CONVERT);
			cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			document.add(tableheader);
			
			//NGƯỜI MUA HÀNG
			
			PdfPTable table1 = new PdfPTable(1);
			table1.setWidthPercentage(100);
			float[] withs1 = {100f};
			table1.setWidths(withs1);
			//table1.setSpacingBefore(0.1f*CONVERT);
					
			cell = new PdfPCell();	
			hd = new Paragraph(NGUOIMUA +" "+ DIACHIGIAOHANG, new Font(bf, 12, Font.NORMAL));//NGUOI MUA
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(6.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table1.addCell(cell);
			document.add(table1);
			
			//ĐƠN VỊ
			
			PdfPTable table2 = new PdfPTable(1);
			table2.setWidthPercentage(100);
			float[] withs2 = {100f};
			table1.setWidths(withs2);
					
			cell = new PdfPCell();	
			hd = new Paragraph(TENKH , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table2.addCell(cell);
			document.add(table2);
			
			//ĐỊA CHỈ
			
			PdfPTable table3 =new PdfPTable(1);
			table3.setWidthPercentage(100);
			float[] withs3 = {100f};
			table3.setWidths(withs3);
						
			cell = new PdfPCell();	
			hd = new Paragraph( DIACHI , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.setPaddingTop(-0.2f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table3.addCell(cell);
			document.add(table3);
			
			//HÌNH THỨC THANH TOÁN - MÃ SỐ THUẾ
			
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {200f,200f};
			table4.setWidths(withs4);
						
			cell= new PdfPCell();	
			hd = new Paragraph( HINHTHUCTT , new Font(bf, 12, Font.NORMAL)); /* HINH THUC THANH TOAN*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(4.3f*CONVERT);
			cell.setPaddingTop(-0.3f*CONVERT);
			cell.setBorder(0);
			
			table4.addCell(cell);
			
			
			cell = new PdfPCell();	
			hd = new Paragraph( MASOTHUE , new Font(bf, 12, Font.NORMAL)); /*	MÃ SỐ THUẾ*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(2.2f*CONVERT);
			cell.setPaddingTop(-0.3f*CONVERT);
			cell.setBorder(0);	
			
			table4.addCell(cell);
			
			document.add(table4);
			
			
			//THÔNG TIN SẢN PHẨM TRONG HÓA ĐƠN
			
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", " ", " "," " ," "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(1.5f*CONVERT);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 1.3f*CONVERT,12.0f*CONVERT, 2.2f*CONVERT, 2.3f*CONVERT, 3.0f*CONVERT, 3.0f*CONVERT, 4.0f*CONVERT };
			sanpham.setWidths(withsKM);
			
			PdfPCell cells = new PdfPCell();			
			
			
			String INIT_SANPHAM = 
				" select SUM(isnull(SOTIENTANGGIAM,0)) AS SOTIENTANGGIAM from ERP_GIAMGIAHANGBAN_HOADON  " +
				" where giamgia_fk = "+hdId+" \n";
			
			System.out.println("INIT_SANPHAM:"+INIT_SANPHAM);
			
			String TENSP="";
			String DONVITINH="";
			int STT=0;
			double SOLUONG=0;
			double DONGIA=0;
			double DONGIAGIAM =0;
			double CHIETKHAU =0;
			
			double THANHTIENGIAM = 0;
			double TONGTIENHANG = 0;
			
			double TIENSAUCK = 0; 
			
			ResultSet rsSP= db.get(INIT_SANPHAM);
					
			if(rsSP!=null){				
				while(rsSP.next()){
					STT++;
					Bvat = Math.round(rsSP.getDouble("SOTIENTANGGIAM"));
					TONGTIENAVAT = Bvat + Bvat*VAT/100;
									
					String[] arr = new String[] { Integer.toString(STT), diengiai , "" ,"" , "","", DinhDangCANFOCO(formatter.format(Bvat)) };
										
					for (int j = 0; j < th.length; j++)
					{
						System.out.println(arr[j]);
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.NORMAL)));
						
						if (j <= 1 ){ //STT, TÊN HÀNG HÓA DỊCH VỤ							
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							
							cells.setPaddingLeft(0.2f*CONVERT);
						}
						else{							
								if(j == 2 )//ĐƠN VỊ TÍNH
								{
									cells.setHorizontalAlignment(Element.ALIGN_LEFT);
									//cells.setPaddingLeft(1.1f*CONVERT);
								}								
								else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
									
									if(j == 3 )//SỐ LƯỢNG
									{
										cells.setPaddingRight(0.5f*CONVERT);
										//cells.setPaddingRight(0.3f*CONVERT); -- khớp với cột - sát mép
									}
									
									if(j == 4 )//ĐƠN GIÁ
									{
										cells.setPaddingRight(0.5f*CONVERT);
										//cells.setPaddingRight(0.3f*CONVERT); -- khớp với cột - sát mép
									}
									
									if(j == 5 )//ĐƠN GIÁ ĐÃ GIẢM
									{
										cells.setPaddingRight(0.7f*CONVERT);
										//cells.setPaddingRight(0.3f*CONVERT); -- khớp với cột - sát mép
									}
									if(j == 6 )//THÀNH TIỀN
									{
										//cells.setPaddingRight(-0.25f*CONVERT);
									}
								}
						}
						
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						cells.setBorder(0);
						//cells.setBorderWidth(1);
						cells.setFixedHeight(0.8f * CONVERT);	
						sanpham.addCell(cells);
					}
					
				}
			}
			rsSP.close();
			
			// DONG TRONG
			int kk=0;
			while(kk < 12-STT)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 12, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setFixedHeight(0.8f*CONVERT);
					cells.setBorder(0);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
			document.add(sanpham);
			
			//TỔNG TIỀN HÀNG
						
			PdfPTable footter = new PdfPTable(2);
			//footter.setSpacingBefore(1.2f*CONVERT);
			footter.setWidthPercentage(100);
			footter.setSpacingBefore(0.5f*CONVERT);
			
			float[] withsfooter = { 25.7f*CONVERT, 3.5f*CONVERT };
			footter.setWidths(withsfooter);
			
			cell = new PdfPCell();	
			
			
			hd = new Paragraph( "" , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(1.5f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);		
			
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangCANFOCO(formatter.format(Bvat)) , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			footter.addCell(cell);
			
			// TIỀN CHIẾT KHẤU - CỘNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph("" , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			TIENSAUCK = TONGTIENHANG - TIENCK ;
			
			cell = new PdfPCell();	
			hd = new Paragraph( "" , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//VAT - TIỀN THUẾ
			
			cell = new PdfPCell();	
			hd = new Paragraph(""+ VAT , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.setPaddingTop(-0.2f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangCANFOCO(formatter.format(Bvat*VAT/100)) , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingTop(-0.2f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//TỔNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph( "" , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);	
			cell.setPaddingTop(-0.4f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangCANFOCO(formatter.format(TONGTIENAVAT)) , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.setPaddingTop(-0.4f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			
			document.add(footter);
									
			
			//ĐỌC TIỀN RA CHỮ
			
			/*DocTien doctien = new DocTien();
					    
			String tien = doctien.docTien(Math.round(TONGTIENAVAT));
						
			//String tien = doctien.tranlate(tongtiencovat + "");
			tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
			if(tien.equals("Đồng"))
				 tien="Không Đồng";
			System.out.println(" Tien là :"+tien);
			
			Paragraph paradoctien = new Paragraph("" + tien, new Font(bf, 12, Font.BOLD));
					    //paradoctien.setSpacingBefore(12.0f);
		    paradoctien.setSpacingBefore(-13.0f);		    
		    paradoctien.setIndentationLeft(140.575f);
			
			document.add(paradoctien);*/
			
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private void CreateHdPdf_HANOI(Document document, ServletOutputStream outstream, IErpGiamgiahangban pxkBean, String hdId) throws IOException
	{
		try{
			dbutils db = new dbutils();
			
			//LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI)
			String query =
				/*" SELECT HD.SOHOADON, HD.KYHIEU, HD.NGAYXUATHD, HD.HINHTHUCTT, KH.MST MASOTHUE, KH.DIACHI, ISNULL(HD.NGUOIMUA,'') NGUOIMUA, KH.TEN, ISNULL(HD.TIENCKTHUONGMAI,0) TIENCKTHUONGMAI," +
				"    HD.CHIETKHAU+HD.TIENCKTHUONGMAI TIENCK, HD.VAT, HD.TONGTIENAVAT, HD.HANGHOADICHVU, HD.GHICHU, ISNULL(HD.DIACHIGIAOHANG,'') DIACHIGIAOHANG \n"+
				" FROM ERP_HOADON HD LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=HD.KHACHHANG_FK \n"+
				" WHERE HD.PK_SEQ ='"+hdId+"' \n";*/
				
				" select a.ngayghinhan, a.ngayhoadon, a.kyhieuhoadon, a.sohoadon, a.vat, a.bvat, a.avat  \n"+
				" , a.khachhang_fk, b.ten as khTen, a.diengiai, a.tungay, a.denngay, ISNULL(a.TIENTE_FK, 0) AS TIENTE_FK \n"+  
			 	" , isnull(b.MST, '') as KhMST, isnull(b.DiaChi, '') as KhDiaChi, isnull(NguoiLienhe, '') as KhNguoiLienHe \n"+  
			 	"	from erp_giamgiahangban a \n"+  
			 	"	inner join erp_khachhang b on a.khachhang_fk = b.pk_seq \n"+  
			 	" where a.pk_seq = '"+hdId+"' \n";
						
			String SOHOADON="";
			String KYHIEU="";
			String NGAYXUATHD ="";
			String HINHTHUCTT="";
			String MASOTHUE="";
			String DIACHI="";
			String NGUOIMUA="";
			String TENKH="";
			String diengiai ="";
			double TIENCK=0;
			double VAT=0;
			double TONGTIENAVAT=0;
			double Bvat =0;
			String HANGHOADICHVU = "";
			String DIACHIGIAOHANG = "";
			
			System.out.println("THONGTINNPP__:"+query);
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					SOHOADON = rs.getString("sohoadon");
					KYHIEU = rs.getString("kyhieuhoadon");
					NGAYXUATHD = rs.getString("NGAYGHINHAN");
					diengiai = rs.getString("diengiai");
					//HINHTHUCTT = rs.getString("HINHTHUCTT");
					MASOTHUE = rs.getString("KhMST");
					DIACHI = rs.getString("KhDiaChi");
					NGUOIMUA = rs.getString("KhNguoiLienHe");
					TENKH = rs.getString("khTen");
					//TIENCK = rs.getDouble("TIENCK");
					VAT = rs.getDouble("vat");
					TONGTIENAVAT = rs.getDouble("avat");
					Bvat = rs.getDouble("bvat");
					//HANGHOADICHVU = rs.getString("GHICHU");
					//DIACHIGIAOHANG = rs.getString("DIACHIGIAOHANG");
				}
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			//document.setPageSize(PageSize.A4.rotate()); CANH HÓA ĐƠN THEO CHIỀU DỌC
			document.setMargins(0.5f*CONVERT, 1.0f*CONVERT, 4.8f*CONVERT, 2.0f*CONVERT); // L,R, T, B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			//NGÀY THÁNG NĂM
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = NGAYXUATHD.split("-");
			Paragraph hd = new Paragraph(ngayHD[2] + "                 " + ngayHD[1] +  "             " + ngayHD[0] , new Font(bf, 12, Font.ITALIC));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setPaddingLeft(2.3f*CONVERT);
			cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			document.add(tableheader);
			
			//NGƯỜI MUA HÀNG
			
			PdfPTable table1 = new PdfPTable(1);
			table1.setWidthPercentage(100);
			float[] withs1 = {100f};
			table1.setWidths(withs1);
			//table1.setSpacingBefore(0.1f*CONVERT);
					
			cell = new PdfPCell();	
			hd = new Paragraph(NGUOIMUA+" "+DIACHIGIAOHANG , new Font(bf, 12, Font.NORMAL));//NGUOI MUA
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(6.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table1.addCell(cell);
			document.add(table1);
			
			//ĐƠN VỊ
			
			PdfPTable table2 = new PdfPTable(1);
			table2.setWidthPercentage(100);
			float[] withs2 = {100f};
			table1.setWidths(withs2);
					
			cell = new PdfPCell();	
			hd = new Paragraph(TENKH , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table2.addCell(cell);
			document.add(table2);
			
			//ĐỊA CHỈ
			
			PdfPTable table3 =new PdfPTable(1);
			table3.setWidthPercentage(100);
			float[] withs3 = {100f};
			table3.setWidths(withs3);
						
			cell = new PdfPCell();	
			hd = new Paragraph( DIACHI , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table3.addCell(cell);
			document.add(table3);
			
			//HÌNH THỨC THANH TOÁN - MÃ SỐ THUẾ
			
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {200f,200f};
			table4.setWidths(withs4);
						
			cell= new PdfPCell();	
			hd = new Paragraph( HINHTHUCTT , new Font(bf, 12, Font.NORMAL)); /* HINH THUC THANH TOAN*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(4.3f*CONVERT);
			cell.setBorder(0);
			
			table4.addCell(cell);
			
			
			cell = new PdfPCell();	
			hd = new Paragraph( MASOTHUE , new Font(bf, 12, Font.NORMAL)); /*	MÃ SỐ THUẾ*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(2.9f*CONVERT);
			cell.setBorder(0);	
			
			table4.addCell(cell);
			
			document.add(table4);
			
			
			//THÔNG TIN SẢN PHẨM TRONG HÓA ĐƠN
			
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", " ", " "," " ," "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(1.5f*CONVERT);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 1.0f*CONVERT,9.5f*CONVERT, 2.5f*CONVERT, 3.0f*CONVERT, 3.0f*CONVERT, 3.0f*CONVERT, 3.0f*CONVERT };
			sanpham.setWidths(withsKM);
			
			PdfPCell cells = new PdfPCell();			
			
			String INIT_SANPHAM = 
				" select SUM(isnull(SOTIENTANGGIAM,0)) AS SOTIENTANGGIAM from ERP_GIAMGIAHANGBAN_HOADON  " +
				" where giamgia_fk = "+hdId+" \n";
			
			System.out.println("INIT_SANPHAM:"+INIT_SANPHAM);
			
			String TENSP="";
			String DONVITINH="";
			int STT=0;
			double SOLUONG=0;
			double DONGIA=0;
			double DONGIAGIAM =0;
			double CHIETKHAU =0;
			
			double THANHTIENGIAM = 0;
			double TONGTIENHANG = 0;
			
			double TIENSAUCK = 0; 
			
			ResultSet rsSP= db.get(INIT_SANPHAM);
					
			if(rsSP!=null){				
				while(rsSP.next()){
					STT++;
					Bvat = Math.round(rsSP.getDouble("SOTIENTANGGIAM"));
					TONGTIENAVAT = Bvat + Bvat*VAT/100;
									
					String[] arr = new String[] { Integer.toString(STT), diengiai , "" ,"" , "","", DinhDangCANFOCO(formatter.format(Bvat)) };
										
					for (int j = 0; j < th.length; j++)
					{
						System.out.println(arr[j]);
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.NORMAL)));
						if (j <= 1 ){ //STT, TÊN HÀNG HÓA DỊCH VỤ
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							//cells.setPaddingLeft(-0.5f*CONVERT);
						}
						else{							
								if(j == 2 )//ĐƠN VỊ TÍNH
								{
									cells.setHorizontalAlignment(Element.ALIGN_LEFT);
									cells.setPaddingLeft(1.1f*CONVERT);
								}								
								else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
									
									if(j == 3 )//SỐ LƯỢNG
									{
										cells.setPaddingRight(0.1f*CONVERT);
									}
									
									if(j == 4 )//ĐƠN GIÁ
									{
										cells.setPaddingRight(0.4f*CONVERT);
									}
									
									if(j == 5 )//ĐƠN GIÁ ĐÃ GIẢM
									{
										cells.setPaddingRight(0.7f*CONVERT);
									}
									if(j == 6 )//THÀNH TIỀN
									{
										//cells.setPaddingRight(-0.25f*CONVERT);
									}
								}
						}
						
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						cells.setBorder(0);
						cells.setFixedHeight(0.8f * CONVERT);	
						sanpham.addCell(cells);
					}
					
				}
			}
			rsSP.close();
			
			// DONG TRONG
			int kk=0;
			while(kk < 13-STT)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 12, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setFixedHeight(0.8f*CONVERT);
					cells.setBorder(0);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
			document.add(sanpham);
			
			//TỔNG TIỀN HÀNG
						
			PdfPTable footter = new PdfPTable(2);
			//footter.setSpacingBefore(1.2f*CONVERT);
			footter.setWidthPercentage(100);
			footter.setSpacingBefore(0.5f*CONVERT);
			
			float[] withsfooter = { 25.7f*CONVERT, 3.5f*CONVERT };
			footter.setWidths(withsfooter);
			
			cell = new PdfPCell();	
			
			
			hd = new Paragraph( HANGHOADICHVU , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(1.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);		
			
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangCANFOCO(formatter.format(Bvat)) , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			footter.addCell(cell);
			
			// TIỀN CHIẾT KHẤU - CỘNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph("" , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			TIENSAUCK = TONGTIENHANG - TIENCK ;
			
			cell = new PdfPCell();	
			hd = new Paragraph( "" , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//VAT - TIỀN THUẾ
			
			cell = new PdfPCell();	
			hd = new Paragraph( ""+VAT , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangCANFOCO(formatter.format(Bvat*VAT/100)) , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//TỔNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph( "" , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);			
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangCANFOCO(formatter.format(TONGTIENAVAT)) , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			
			document.add(footter);
									
			
			//ĐỌC TIỀN RA CHỮ
			
			/*DocTien doctien = new DocTien();
					    
			String tien = doctien.docTien(Math.round(TONGTIENAVAT));
						
			//String tien = doctien.tranlate(tongtiencovat + "");
			tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
			if(tien.equals("Đồng"))
				 tien="Không Đồng";
			System.out.println(" Tien là :"+tien);
			
			Paragraph paradoctien = new Paragraph("" + tien, new Font(bf, 12, Font.BOLD));
					    //paradoctien.setSpacingBefore(12.0f);
		    paradoctien.setSpacingBefore(1.0f);		    
		    paradoctien.setIndentationLeft(140.575f);
			
			document.add(paradoctien);*/
			
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private void CreateHdPdf_DANANG(Document document, ServletOutputStream outstream, IErpGiamgiahangban pxkBean, String hdId) throws IOException
	{
		try{
			dbutils db = new dbutils();
			
			//LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI)
			String query =
				/*" SELECT HD.SOHOADON, HD.KYHIEU, HD.NGAYXUATHD, HD.HINHTHUCTT, KH.MST MASOTHUE, KH.DIACHI, ISNULL(HD.NGUOIMUA,'') NGUOIMUA, KH.TEN, ISNULL(HD.TIENCKTHUONGMAI,0) TIENCKTHUONGMAI," +
				"    HD.CHIETKHAU+HD.TIENCKTHUONGMAI TIENCK, HD.VAT, HD.TONGTIENAVAT, HD.HANGHOADICHVU, HD.GHICHU, ISNULL(HD.DIACHIGIAOHANG,'') DIACHIGIAOHANG \n"+
				" FROM ERP_HOADON HD LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=HD.KHACHHANG_FK \n"+
				" WHERE HD.PK_SEQ ='"+hdId+"' \n";*/
				
				" select a.ngayghinhan, a.ngayhoadon, a.kyhieuhoadon, a.sohoadon, a.vat, a.bvat, a.avat  \n"+
				" , a.khachhang_fk, b.ten as khTen, a.diengiai, a.tungay, a.denngay, ISNULL(a.TIENTE_FK, 0) AS TIENTE_FK \n"+  
			 	" , isnull(b.MST, '') as KhMST, isnull(b.DiaChi, '') as KhDiaChi, isnull(NguoiLienhe, '') as KhNguoiLienHe \n"+  
			 	"	from erp_giamgiahangban a \n"+  
			 	"	inner join erp_khachhang b on a.khachhang_fk = b.pk_seq \n"+  
			 	" where a.pk_seq = '"+hdId+"' \n";
						
			String SOHOADON="";
			String KYHIEU="";
			String NGAYXUATHD ="";
			String HINHTHUCTT="";
			String MASOTHUE="";
			String DIACHI="";
			String NGUOIMUA="";
			String TENKH="";
			String diengiai ="";
			double TIENCK=0;
			double VAT=0;
			double TONGTIENAVAT=0;
			double Bvat =0;
			String HANGHOADICHVU = "";
			String DIACHIGIAOHANG = "";
			
			System.out.println("THONGTINNPP__:"+query);
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					SOHOADON = rs.getString("sohoadon");
					KYHIEU = rs.getString("kyhieuhoadon");
					NGAYXUATHD = rs.getString("NGAYGHINHAN");
					diengiai = rs.getString("diengiai");
					//HINHTHUCTT = rs.getString("HINHTHUCTT");
					MASOTHUE = rs.getString("KhMST");
					DIACHI = rs.getString("KhDiaChi");
					NGUOIMUA = rs.getString("KhNguoiLienHe");
					TENKH = rs.getString("khTen");
					//TIENCK = rs.getDouble("TIENCK");
					VAT = rs.getDouble("vat");
					TONGTIENAVAT = rs.getDouble("avat");
					Bvat = rs.getDouble("bvat");
					//HANGHOADICHVU = rs.getString("GHICHU");
					//DIACHIGIAOHANG = rs.getString("DIACHIGIAOHANG");
				}
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			//document.setPageSize(PageSize.A4.rotate()); CANH HÓA ĐƠN THEO CHIỀU DỌC
			document.setMargins(0.0f*CONVERT, 1.0f*CONVERT, 4.3f*CONVERT, 2.0f*CONVERT); // L,R, T, B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			//NGÀY THÁNG NĂM
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = NGAYXUATHD.split("-");
			Paragraph hd = new Paragraph(ngayHD[2] + "                 " + ngayHD[1] +  "             " + ngayHD[0] , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setPaddingLeft(1.8f*CONVERT);
			cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			document.add(tableheader);
			
			//NGƯỜI MUA HÀNG
			
			PdfPTable table1 = new PdfPTable(1);
			table1.setWidthPercentage(100);
			float[] withs1 = {100f};
			table1.setWidths(withs1);
			//table1.setSpacingBefore(0.1f*CONVERT);
					
			cell = new PdfPCell();	
			hd = new Paragraph(NGUOIMUA + " " +DIACHIGIAOHANG, new Font(bf, 12, Font.NORMAL));//NGUOI MUA
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(6.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table1.addCell(cell);
			document.add(table1);
			
			//ĐƠN VỊ
			
			PdfPTable table2 = new PdfPTable(1);
			table2.setWidthPercentage(100);
			float[] withs2 = {100f};
			table1.setWidths(withs2);
					
			cell = new PdfPCell();	
			hd = new Paragraph(TENKH , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);	//hd.setSpacingAfter(4);			
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table2.addCell(cell);
			document.add(table2);
			
			//ĐỊA CHỈ
			
			PdfPTable table3 =new PdfPTable(1);
			table3.setWidthPercentage(100);
			float[] withs3 = {100f};
			table3.setWidths(withs3);
						
			cell = new PdfPCell();	
			hd = new Paragraph( DIACHI , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table3.addCell(cell);
			document.add(table3);
			
			//HÌNH THỨC THANH TOÁN - MÃ SỐ THUẾ
			
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {200f,200f};
			table4.setWidths(withs4);
						
			cell= new PdfPCell();	
			hd = new Paragraph( HINHTHUCTT , new Font(bf, 12, Font.NORMAL)); /* HINH THUC THANH TOAN*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(4.3f*CONVERT);
			cell.setBorder(0);
			
			table4.addCell(cell);
			
			
			cell = new PdfPCell();	
			hd = new Paragraph( MASOTHUE , new Font(bf, 12, Font.NORMAL)); /*	MÃ SỐ THUẾ*/
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(2.9f*CONVERT);
			cell.setBorder(0);	
			
			table4.addCell(cell);
			
			document.add(table4);
			
			
			//THÔNG TIN SẢN PHẨM TRONG HÓA ĐƠN
			
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", " ", " "," " ," "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(1.5f*CONVERT);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 1.0f*CONVERT,9.0f*CONVERT, 3.0f*CONVERT, 2.0f*CONVERT, 3.2f*CONVERT, 3.2f*CONVERT, 3.0f*CONVERT };
			sanpham.setWidths(withsKM);
			
			PdfPCell cells = new PdfPCell();			
			
			String INIT_SANPHAM = 
				" select SUM(isnull(SOTIENTANGGIAM,0)) AS SOTIENTANGGIAM from ERP_GIAMGIAHANGBAN_HOADON  " +
				" where giamgia_fk = "+hdId+" \n";
			
			System.out.println("INIT_SANPHAM:"+INIT_SANPHAM);
			
			String TENSP="";
			String DONVITINH="";
			int STT=0;
			double SOLUONG=0;
			double DONGIA=0;
			double DONGIAGIAM =0;
			double CHIETKHAU =0;
			
			double THANHTIENGIAM = 0;
			double TONGTIENHANG = 0;
			
			double TIENSAUCK = 0; 
			
			ResultSet rsSP= db.get(INIT_SANPHAM);
					
			if(rsSP!=null){				
				while(rsSP.next()){
					STT++;
					Bvat = Math.round(rsSP.getDouble("SOTIENTANGGIAM"));
					TONGTIENAVAT = Bvat + Bvat*VAT/100;
									
					String[] arr = new String[] { Integer.toString(STT), diengiai , "" ,"" , "","", DinhDangCANFOCO(formatter.format(Bvat)) };
										
					for (int j = 0; j < th.length; j++)
					{
						System.out.println(arr[j]);
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.NORMAL)));
						if (j <= 1 ){ //STT, TÊN HÀNG HÓA DỊCH VỤ
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							//cells.setPaddingLeft(-0.5f*CONVERT);
						}
						else{							
								if(j == 2 )//ĐƠN VỊ TÍNH
								{
									cells.setHorizontalAlignment(Element.ALIGN_LEFT);
									cells.setPaddingLeft(1.4f*CONVERT);
								}								
								else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
									
									if(j == 3 )//SỐ LƯỢNG
									{
										cells.setPaddingRight(0.1f*CONVERT);
									}
									
									if(j == 4 )//ĐƠN GIÁ
									{
										cells.setPaddingRight(0.4f*CONVERT);
									}
									
									if(j == 5 )//ĐƠN GIÁ ĐÃ GIẢM
									{
										cells.setPaddingRight(0.7f*CONVERT);
									}
									if(j == 6 )//THÀNH TIỀN
									{
										//cells.setPaddingRight(-0.25f*CONVERT);
									}
								}
						}
						
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						cells.setBorder(0);
						//cells.setBorderWidth(1);
						cells.setFixedHeight(0.8f * CONVERT);	
						sanpham.addCell(cells);
					}
					
				}
			}
			rsSP.close();
			
			// DONG TRONG
			int kk=0;
			while(kk < 13-STT)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 12, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setFixedHeight(0.8f*CONVERT);
					cells.setBorder(0);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
			document.add(sanpham);
			
			//TỔNG TIỀN HÀNG
						
			PdfPTable footter = new PdfPTable(2);
			//footter.setSpacingBefore(1.2f*CONVERT);
			footter.setWidthPercentage(100);
			footter.setSpacingBefore(0.5f*CONVERT);
			
			float[] withsfooter = { 25.7f*CONVERT, 3.5f*CONVERT };
			footter.setWidths(withsfooter);
			
			cell = new PdfPCell();	
			
			
			hd = new Paragraph( HANGHOADICHVU , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(1.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);		
			
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangCANFOCO(formatter.format(Bvat)) , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			footter.addCell(cell);
			
			// TIỀN CHIẾT KHẤU - CỘNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph("" , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			TIENSAUCK = TONGTIENHANG - TIENCK ;
			
			cell = new PdfPCell();	
			hd = new Paragraph( "" , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//VAT - TIỀN THUẾ
			
			cell = new PdfPCell();	
			hd = new Paragraph( ""+VAT , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(5.0f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangCANFOCO(formatter.format(Bvat*VAT/100)) , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			//TỔNG TIỀN THANH TOÁN
			
			cell = new PdfPCell();	
			hd = new Paragraph( "" , new Font(bf, 12, Font.NORMAL)); 
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);			
			cell.addElement(hd);
			cell.setBorder(0);				
			footter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph( DinhDangCANFOCO(formatter.format(TONGTIENAVAT)) , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);			
			cell.setFixedHeight(0.8f*CONVERT);
			//cell.setPaddingRight(-0.25f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);			
			cell.setBorder(0);	
			footter.addCell(cell);
			
			
			document.add(footter);
									
			
			/*//ĐỌC TIỀN RA CHỮ
			
			DocTien doctien = new DocTien();
					    
			String tien = doctien.docTien(Math.round(TONGTIENAVAT));
						
			//String tien = doctien.tranlate(tongtiencovat + "");
			tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
			if(tien.equals("Đồng"))
				 tien="Không Đồng";
			System.out.println(" Tien là :"+tien);
			
			Paragraph paradoctien = new Paragraph("" + tien, new Font(bf, 12, Font.NORMAL));
					    //paradoctien.setSpacingBefore(12.0f);
		    paradoctien.setSpacingBefore(3.0f);		    
		    paradoctien.setIndentationLeft(140.575f);
			
			document.add(paradoctien);*/
			
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private String DinhDangCANFOCO(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}
}
