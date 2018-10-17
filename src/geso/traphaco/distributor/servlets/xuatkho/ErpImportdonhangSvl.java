package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.distributor.beans.xuatkho.IErpImportdonhangList;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpImportdonhangList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Toll;
import geso.traphaco.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.oreilly.servlet.MultipartRequest;

import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ErpImportdonhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "c:\\upload\\excel\\";
       
    public ErpImportdonhangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpImportdonhangList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
    	String lsxId = util.getId(querystring);
	    obj = new ErpImportdonhangList();
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpImportDonHang.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
		IErpImportdonhangList obj = new ErpImportdonhangList();
		
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    obj.setUserId(userId);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    System.out.println("---NPP ID: " + nppId);
	    
	    String contentType = request.getContentType();
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			session.setAttribute("userId", userId);
			obj.setUserId(userId);
			
			Enumeration files = multi.getFileNames();
			String filename = "";
			System.out.println("__userId" + userId);
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			if (filename != null && filename.length() > 0)
			{
				String msg = this.readExcel(UPLOAD_DIRECTORY + filename, obj); 
				obj.setMsg(msg);
				
				String ngayIMPORT = this.getDateTime();
				String query = "select count(*) as sodong from ERP_DONDATHANGNPP_LOG where ngayIMPORT = '" + ngayIMPORT + "' ";
				dbutils db = new dbutils();
				ResultSet rs = db.get(query);
				int sodong = 0;
				if( rs != null )
				{
					try
					{
						if( rs.next() )
						{
							sodong = rs.getInt("sodong");
						}
						rs.close();
					}
					catch(Exception ex){ }
				}
				
				if( sodong > 0 )
				{
					//Xuất EXCEL lỗi
					this.XuatExcel(response, db, ngayIMPORT);
					db.shutDown();
				}
				
			}

			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpImportDonHang.jsp";
			response.sendRedirect(nextJSP);
		} 
	}
	
	private String readExcel(String fileName, IErpImportdonhangList obj) 
	{
		String ngayIMPORT = this.getDateTime();
		
		try
		{
			File inputWorkbook = new File(fileName);
			jxl.Workbook w = null;
			w = jxl.Workbook.getWorkbook(inputWorkbook);
			jxl.Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();
			
			dbutils db = new dbutils();
			String query = "";
			
			//XÓA DỮ LIỆU CŨ
			Toll toll = new Toll();
			String msg = toll.ResetData(db, "106313", ngayIMPORT, "");
			if( msg.trim().length() > 0 )
				return msg;
			
			for (int i = 1; i < sodong; i++)
			{
				String stt = getValue(sheet, 0, i).trim();
				String machungtu = getValue(sheet, 1, i).trim();
				String ngaylap = getValue(sheet, 2, i).trim();
				String khohanghoa = getValue(sheet, 4, i).trim();
				String sohoadon = getValue(sheet, 5, i).trim();
				String ngayhoadon = getValue(sheet, 6, i).trim();
				
				String kenhIMPORT = getValue(sheet, 7, i).trim();
				String phanloai = getValue(sheet, 8, i).trim();
				String maTDV = getValue(sheet, 9, i).trim();
				String tenTDV = getValue(sheet, 10, i).trim();
				String maKHACHHANG = getValue(sheet, 11, i).trim();
				String tenKHACHHANG = getValue(sheet, 12, i).trim();
				String maHOPDONG = getValue(sheet, 13, i).trim();
				String maSANPHAM = getValue(sheet, 14, i).trim();
				String tenSANPHAM = getValue(sheet, 15, i).trim();
				String SOLO = getValue(sheet, 16, i).trim();
				
				String thueVAT = getValue(sheet, 17, i).trim();
				String donvitinh = getValue(sheet, 18, i).trim();
				
				String soluong = getValue(sheet, 19, i).trim();
				String dongiatruocCK = getValue(sheet, 20, i).trim();
				String chietkhau = getValue(sheet, 21, i).trim();
				String thanhtienCHUATHUE = getValue(sheet, 22, i).trim();
				String tienTHUE = getValue(sheet, 23, i).trim();
				String tienSAUTHUE = getValue(sheet, 24, i).trim();
				String thanhtienLAMTRON = getValue(sheet, 25, i).trim();
				String dongiasauCK = "0";
				
				String ghichu = getValue(sheet, 26, i).trim();
				
				System.out.println("::: MA CHUNG TU:  " + machungtu + " -- MA SAN PHAM: " + maSANPHAM + " -- MA TDV: " + maTDV);
				if( machungtu.length() > 0 && maSANPHAM.length() > 0 && maTDV.length() > 0 )
				{
					query = " insert ERP_DONDATHANGNPP_TEMP( stt, ngaylap, ngayhoadon, khohanghoa, sohoadon, ghichu, kenhIMPORT, phanloai, maTDV, tenTDV, maKHACHHANG, tenKHACHHANG, " + 
								" maHOPDONG, maSANPHAM, tenSANPHAM, donvi, soluong, machungtu, dongiatruocCK, chietkhau, dongiasauCK, thueVAT, SOLO, thanhtien_truocVAT, thueGTGT_thanhtien, ngayIMPORT ) " + 
							" values ( '" + stt + "', '" + ngaylap + "', '" + ngayhoadon + "', N'" + khohanghoa + "', '" + sohoadon + "', N'" + ghichu + "', N'" + kenhIMPORT + "', " + 
							" 	N'" + phanloai + "', '" + maTDV + "', N'" + tenTDV + "', N'" + maKHACHHANG + "', N'" + tenKHACHHANG + "', N'" + maHOPDONG + "', N'" + maSANPHAM + "', N'" + tenSANPHAM + "', N'" + donvitinh + "', '" + soluong + "', N'" + machungtu + "', " + 
							"    '" + dongiatruocCK + "', '" + chietkhau + "', '" + dongiasauCK + "', '" + thueVAT + "', '" + SOLO + "' , '" + thanhtienCHUATHUE + "', '" + tienTHUE + "', '" + ngayIMPORT + "' )  ";
					
					System.out.println("::: CAP NHAT:  " + query);
					db.update(query);
				}
			}
			
			//CHẠY STORE IMPORT
			System.out.println(":::: CALL STORE XU LY DON HANG...");
			db.execProceduce2("XuLy_DonHang_Import", new String[] { "106313", ngayIMPORT } );
			
			//DÙNG TOOL IMPORT ĐƠN HÀNG CHI TIẾT VÀ HÓA ĐƠN
			toll.XuLy_HoaDonIMPORT(db, "106313", ngayIMPORT, "");
			
			//XÓA NHỮNG MÃ CHỨNG TỪ BỊ LỖI IMPORT
			query = "delete  ERP_DONDATHANGNPP where LoaiDonHang in ( 0, 1, 2 ) and NOTE = N'Import ngay " + ngayIMPORT + "' " + 
						" and machungtu in ( select machungtu from ERP_DONDATHANGNPP_LOG where machungtu is not null and ngayIMPORT = '" + ngayIMPORT + "' ) ";
			System.out.println(":::: XOA DON IMPORT KHONG DUOC: " + query);
			db.update(query);
			
			System.out.println("/************* IMPORT HOAN TAT **************/");
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return "Vui lòng coi lại file " + e.getMessage();
		}
		
		return "Import thành công";
	}

	private String getValue(Sheet sheet, int column, int row)
	{
		Cell cell;
		cell = sheet.getCell(column, row);
		try
		{
			return cell.getContents();
		} 
		catch (Exception er)
		{
			return "0";
		}
	}
	
	private void XuatExcel(HttpServletResponse response, dbutils db, String ngayIMPORT) throws IOException
	{
		try
		{
			String query = "";
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=csm_import_loi.xls");
			
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("csm_import", 0);
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.GRAY_25);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			sheet.addCell(new Label(0, 0, "STT", cellFormat));
			sheet.addCell(new Label(1, 0, "Mã chứng từ", cellFormat));
			sheet.addCell(new Label(2, 0, "Ngày lập đơn hàng", cellFormat));
			sheet.addCell(new Label(3, 0, "Kho", cellFormat));
			sheet.addCell(new Label(4, 0, "Kho hàng hóa", cellFormat));
			sheet.addCell(new Label(5, 0, "Số hóa đơn", cellFormat));
			sheet.addCell(new Label(6, 0, "Ngày hóa đơn", cellFormat));
			sheet.addCell(new Label(7, 0, "Hệ thống bán hàng", cellFormat));
			sheet.addCell(new Label(8, 0, "Kênh bán hàng", cellFormat));
			
			sheet.addCell(new Label(9, 0, "Mã nhân sự TDV", cellFormat));
			sheet.addCell(new Label(10, 0, "Nhận viên bán hàng", cellFormat));
			sheet.addCell(new Label(11, 0, "Mã khách hàng", cellFormat));
			sheet.addCell(new Label(12, 0, "Tên khách hàng", cellFormat));
			sheet.addCell(new Label(13, 0, "Mã hợp đồng", cellFormat));
			
			sheet.addCell(new Label(14, 0, "Mã sản phẩm", cellFormat));
			sheet.addCell(new Label(15, 0, "Tên sản phẩm", cellFormat));
			sheet.addCell(new Label(16, 0, "Số lô", cellFormat));
			sheet.addCell(new Label(17, 0, "Thuế GTGT", cellFormat));
			sheet.addCell(new Label(18, 0, "Đơn vị tính", cellFormat));
			
			sheet.addCell(new Label(19, 0, "Số lượng", cellFormat));
			sheet.addCell(new Label(20, 0, "Đơn giá", cellFormat));
			sheet.addCell(new Label(21, 0, "Chiết khấu", cellFormat));
			sheet.addCell(new Label(22, 0, "Thành tiền chưa thuế", cellFormat));
			sheet.addCell(new Label(23, 0, "Tiền thuế", cellFormat));
			sheet.addCell(new Label(24, 0, "Tiền sau thuế", cellFormat));
			sheet.addCell(new Label(25, 0, "Thành tiền làm tròn", cellFormat));
			
			sheet.addCell(new Label(26, 0, "Ghi chú", cellFormat));
			sheet.addCell(new Label(27, 0, "Lỗi", cellFormat));
			
			// set style to cell data
			WritableCellFormat cellFormat2 = new WritableCellFormat();

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			query = "  select a.stt, a.machungtu, a.ngaylap, '' as kho, a.khohanghoa, a.sohoadon, a.ngayhoadon, a.kenhIMPORT, a.phanloai, " + 
					"  			a.maTDV, a.tenTDV, a.maKHACHHANG, a.tenKHACHHANG, a.maHOPDONG, a.maSANPHAM, a.tenSANPHAM, a.solo,  " + 
					"  			a.thueVAT, a.donvi, a.soluong, a.dongiaTRUOCCK, a.chietkhau, a.thanhtien_TRUOCVAT, a.thueGTGT_THANHTIEN, '' as thanhtienSAUTHUE, '' as thanhtienLAMTRON, " + 
					"  			a.ghichu, ISNULL( b.msg, '' ) as loiImport  " + 
					"  from ERP_DONDATHANGNPP_TEMP a inner join ERP_DONDATHANGNPP_LOG b on a.machungtu = b.machungtu " + 
					"  where a.ngayIMPORT = '" + ngayIMPORT + "' " + 
					"  order by a.stt asc " ;
			
			System.out.println(":::XUẤT EXCEL LỖI: " + query);
			ResultSet rs = db.get(query);
			
			int j = 1;
			if( rs != null )
			{
				while (rs.next())
				{
					Label label;
	
					for( int i = 0; i <= 27; i++ )
					{
						label = new Label(i , j, rs.getString(i + 1), cellFormat2);
						sheet.addCell(label);
					}
	
					j++;	
				}
				rs.close();
			}
			
			for(int x = 0; x <= 28; x++)
			{
			    CellView cell = sheet.getColumnView(x);
			    cell.setAutosize(true);
			    sheet.setColumnView(x, cell);
			}
			
			
			WritableSheet sheet2 = w.createSheet("Khach_Hang_Moi", 1);
			sheet2.addCell(new Label(0, 0, "STT", cellFormat));
			sheet2.addCell(new Label(1, 0, "Mã khách hàng", cellFormat));
			sheet2.addCell(new Label(2, 0, "Tên khách hàng", cellFormat));
			
			query = "  select ROW_NUMBER() OVER(ORDER BY PK_SEQ ASC) AS STT, maFAST, ten  " + 
					"  from KHACHHANG a  " + 
					"  where a.ngayIMPORT = '" + ngayIMPORT + "' ";
			
			System.out.println(":::XUẤT EXCEL KHACH HANG MOI: " + query);
			rs = db.get(query);
			
			j = 1;
			if( rs != null )
			{
				while (rs.next())
				{
					Label label;
	
					for( int i = 0; i <= 2; i++ )
					{
						label = new Label(i , j, rs.getString(i + 1), cellFormat2);
						sheet2.addCell(label);
					}
	
					j++;	
				}
				rs.close();
			}
			
			for(int x = 0; x <= 2; x++)
			{
			    CellView cell = sheet2.getColumnView(x);
			    cell.setAutosize(true);
			    sheet2.setColumnView(x, cell);
			}
			
			w.write();
			w.close();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
