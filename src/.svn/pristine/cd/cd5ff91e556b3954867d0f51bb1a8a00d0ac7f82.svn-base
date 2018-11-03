package geso.dms.center.util;

import geso.traphaco.erp.db.sql.dbutils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Style;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ImportSoDuDauKy_MuaHang extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ImportSoDuDauKy_MuaHang() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		doPost(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/xlsm");
		response.setHeader("Content-Disposition", "attachment; filename=Sodudau.xlsm");
				
		try{
			ImportData(request, response);
		
		}catch (Exception e) 
        {
			System.out.println(e.toString());
		}

	}
	

	private boolean ImportData(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		OutputStream out = response.getOutputStream();
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\SODUDAUKY_MUAHANG.xlsx");
		workbook.open(fstream);
//		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLTX);
		
		Worksheets worksheets = workbook.getWorksheets();
		try{
			
			Worksheet worksheet1 = worksheets.getSheet("AR & AP Balance");
			
		 	Sodudau(worksheet1); 
			
		}catch(Exception e){System.out.println(e.toString());}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	
	private void Sodudau(Worksheet worksheet){
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();
		Cell cell;
		
		String Id, nccId, ngayghinhan, ngayhoadon, kyhieu, sohoadon;
		double tongtien_VND, tongtien_NT; 
		String ttId="";
		String query;
		int tigia = 1;
		
		try{
			//db.getConnection().setAutoCommit(false);		
			ResultSet rs ;
			for (int row = 16; row <= 293; row ++){
			//System.out.println(row);
				nccId = ""; 
				ngayghinhan = "2014-07-31";
				kyhieu = "BEGIN"; 
				sohoadon = "";
				tongtien_VND = 0;
				tongtien_NT = 0;
				ttId = "100000";
				tigia = 1;
				
				cell = cells.getCell("D" + row);
				nccId = cell.getStringValue();
				
				cell = cells.getCell("J" + row);
				ngayhoadon = cell.getStringValue();
			 
				cell = cells.getCell("N" + row);
				sohoadon = cell.getStringValue();

				cell = cells.getCell("B" + row);
				if(cell.getStringValue().equals("USD")){
					ttId = "100001";
					tigia = 21090;
				}
				
				cell = cells.getCell("P" + row);
				if(cell.getStringValue().length() > 0){
					tongtien_VND = Double.parseDouble(cell.getStringValue().replaceAll(",", ""));
				}else{
					tongtien_VND = 0;
				}
				 
				cell = cells.getCell("Q" + row);
				if(cell.getStringValue().length() > 0){
					tongtien_NT = Double.parseDouble(cell.getStringValue().replaceAll(",", ""));
				}else{
					tongtien_NT = 0;
				}
				System.out.println("Tong tien VND: " + row + ": " + tongtien_VND);
				if(tongtien_VND > 0){
					query = "INSERT INTO ERP_PARK(NGAYGHINHAN, NCC_FK, LOAIHANGHOA_FK, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, " +
							"PREFIX, CONGTY_FK, TINHTHUENHAPKHAU, " +
							"TIENTE_FK,  TIGIA) " +
							"VALUES('" + ngayghinhan + "', " + nccId + ", '0', '1', '2014-08-21', '100382', '2014-08-21', '100382', '170', '100005', '0', " +
							"" + ttId + ", " + tigia + ")";
					System.out.println("ID + " + row + ": " +query);
				
					db.update(query);
				
					//query = "SELECT SCOPE_IDENTITY() AS ID ";
					query = "select IDENT_CURRENT('ERP_PARK') as ID";
					
					rs = db.get(query);
					rs.next();
					Id = rs.getString("ID");
					System.out.print("ID + " + row + ": " + Id);
					rs.close();
				
					cell = cells.getCell("H" + row);
					cell.setValue(Id);
					
					query = "INSERT INTO ERP_HOADONNCC(KYHIEU, SOHOADON, NGAYHOADON, SOTIENAVAT, SOTIENCHIETKHAU, VAT, SOTIENBVAT, PARK_FK, " +
							"TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, THUESUAT, GHICHU, SOTIENVIET) " +
							"VALUES('" + kyhieu + "', '" + sohoadon + "', '" + ngayhoadon + "', " + (ttId.equals("100000")? tongtien_VND:tongtien_NT) + ", " +
							"0, 0, " + (ttId.equals("100000")? tongtien_VND:tongtien_NT) + ", " + Id + ", 0, '2014-08-21', '100382', '2014-08-21', '100382', '0', N'Mua hàng', " + tongtien_VND + ")";
								
					System.out.print("ID + " + row + ": " + query);
					db.update(query);
				}
			}

			//db.getConnection().commit();
			//db.getConnection().setAutoCommit(true);

		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
