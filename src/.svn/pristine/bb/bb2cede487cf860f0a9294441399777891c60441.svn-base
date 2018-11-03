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

public class ImportTTCP_Phanbo extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ImportTTCP_Phanbo() {
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
		response.setHeader("Content-Disposition", "attachment; filename=CHIPHI_PHANBO.xlsm");
				
		try{
			ImportData(request, response);
		
		}catch (Exception e) 
        {
			System.out.println(e.toString());
		}

	}
	

	private boolean ImportData(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		dbutils db = new dbutils();
		OutputStream out = response.getOutputStream();
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\CHIPHI_PHANBO.xlsm");
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		Worksheets worksheets = workbook.getWorksheets();
		try{
			
			Worksheet worksheet1 = worksheets.getSheet("trungtamchiphi (2)");
			TTCP(db, worksheet1);		
			
			Worksheet worksheet2 = worksheets.getSheet("khoanmucchiphi - DC");
			KhoanmucCP(db, worksheet2);
			
		}catch(Exception e){System.out.println(e.toString());}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	
	private void TTCP(dbutils db, Worksheet worksheet){
		Cells cells = worksheet.getCells();
		
		Cell cell;

		String ttcp = "";
		String ngansach = "0";
		String chophanbo = "0";
		String nhanphanbo = "0";
		String ttcpnhan = "";
		String ptphanbo = "";
		String phanbo = "";
		String ttcpnhanId = "";
		String Id = "";
		String query;
		
		try{
		//db.getConnection().setAutoCommit(false);		
		 ResultSet rs ;
		 db.update("DELETE ERP_TRUNGTAMCHIPHI_PHANBO");
		 for (int row = 3; row <= 424; row ++){
			//for (int row = 243; row <= 243; row ++){
			//System.out.println(row);

				ttcp = "";
				ngansach = "";
				nhanphanbo = "";
				chophanbo = "";
				
				cell = cells.getCell("B" + row);
				ttcp = cell.getStringValue();
				
				cell = cells.getCell("D" + row);
				ngansach = cell.getStringValue().trim();
				
				cell = cells.getCell("E" + row);
				nhanphanbo = cell.getStringValue().trim();

				cell = cells.getCell("F" + row);
				chophanbo = cell.getStringValue().trim();
			
				if(ttcp.trim().length() > 0){
					query = "SELECT COUNT(*) AS NUM FROM ERP_TRUNGTAMCHIPHI WHERE MA = N'" + ttcp + "'";
					System.out.println(query);
			
					rs = db.get(query);
			 
					if(rs != null){
						rs.next();
						if(rs.getString("NUM").equals("0"))	{
							query = "INSERT INTO ERP_TRUNGTAMCHIPHI(MA, DIENGIAI, CONGTY_FK, NHANPHANBO, CHOPHANBO, CONGANSACH, " +
									"NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI) " +
									"VALUES(N'" + ttcp + "', N'" + ttcp + "', 100005, '" + nhanphanbo + "', '" + chophanbo + "', '" + ngansach + "', " +
									"'100379', '100379', '2014-09-09', '2014-09-09', '1')";
							System.out.println(query);
							db.update(query);
						
							query = "SELECT SCOPE_IDENTITY() AS ID";
							ResultSet rs2 = db.get(query);
							rs2.next();
							Id = rs2.getString("ID");
							rs2.close();
						
							cell = cells.getCell("A" + row);
							cell.setValue(Id);
							
							if(chophanbo.equals("1")){
								
								cell = cells.getCell("I" + row);
								ptphanbo = cell.getStringValue();
								 
								cell = cells.getCell("J" + row);
								phanbo = cell.getStringValue();
								
								cell = cells.getCell("H" + row);
								ttcpnhan = cell.getStringValue();
								
								query = "SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = N'" + ttcpnhan + "'";
								
								ResultSet rs3 = db.get(query);
								if(rs3 != null){
									if(rs3.next()){
										cell = cells.getCell("G" + row);
										ttcpnhanId = rs3.getString("PK_SEQ");
										cell.setValue(ttcpnhanId);
										rs3.close();
									}else{
										cell = cells.getCell("G" + row);
										cell.setValue("N/A");
									}
								}
								
								String result = ptphanbo.trim().length() > 0 ?  ptphanbo.trim():phanbo.trim();
								query = "INSERT INTO ERP_TRUNGTAMCHIPHI_PHANBO (TTCPCHO_FK, TTCPNHAN_FK, PHANTRAM) VALUES(" + Id + ", " + ttcpnhanId + ", " + result + ")";
								System.out.println(query);
								db.update(query);
							}							
							
						}else{
							query = "SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = N'" + ttcp + "'";
							
							ResultSet rs2 = db.get(query);
							if(rs2 != null){
								rs2.next();
								cell = cells.getCell("A" + row);
								Id = rs2.getString("PK_SEQ");
								cell.setValue(Id);
								rs2.close();
								
								query = "UPDATE ERP_TRUNGTAMCHIPHI SET NHANPHANBO = '" + nhanphanbo + "', CHOPHANBO = '" + chophanbo + "', " +
										"MA = N'" + ttcp + "', DIENGIAI = N'"  + ttcp + "', NGUOISUA = '100379', NGAYSUA = '2014-09-09' " +
										"WHERE PK_SEQ = " + Id + "";
								System.out.println("Update:" + query);
								db.update(query);
														
								if(chophanbo.equals("1")){
									
									cell = cells.getCell("I" + row);
									ptphanbo = cell.getStringValue();
									 
									cell = cells.getCell("J" + row);
									phanbo = cell.getStringValue();
									
									cell = cells.getCell("H" + row);
									ttcpnhan = cell.getStringValue();
									
									query = "SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = N'" + ttcpnhan + "'";
									
									ResultSet rs3 = db.get(query);
									if(rs3 != null){
										if(rs3.next()){
											cell = cells.getCell("G" + row);
											ttcpnhanId = rs3.getString("PK_SEQ");
											cell.setValue(ttcpnhanId);
											rs3.close();
										}else{
											cell = cells.getCell("G" + row);
											cell.setValue("N/A");
										}
									}
									
									String result = ptphanbo.trim().length() > 0 ?  ptphanbo.trim():phanbo.trim();
									query = "INSERT INTO ERP_TRUNGTAMCHIPHI_PHANBO (TTCPCHO_FK, TTCPNHAN_FK, PHANTRAM) VALUES(" + Id + ", " + ttcpnhanId + ", " + result + ")";
									System.out.println(query);
									db.update(query);
								}

							}
							
						
						}
					
						rs.close();
					}
						
				}
			}
			
			 
		/*db.getConnection().commit();
		db.getConnection().setAutoCommit(true);*/

		}catch(Exception e){
			e.printStackTrace();
		}

	}
		
	private void KhoanmucCP(dbutils db, Worksheet worksheet){
		Cells cells = worksheet.getCells();
		
		Cell cell;
		String kmcp = "";
		String diengiai = "";
		String kmcpId = "";
		String ttcp = "";
		String ttcpId = "";
		String sohieutk = "";
		String tkId = "";
		String query;
		
		try{
		//db.getConnection().setAutoCommit(false);		
		 ResultSet rs ;
		 
		 for (int row = 2; row <= 138; row ++){
			//for (int row = 15; row <= 15; row ++){
			//System.out.println(row);

			 	kmcp = "";
				kmcpId = "";
				diengiai = "";
				ttcp = "";
				ttcpId = "";
				sohieutk = "";
				tkId = "";
				
				cell = cells.getCell("B" + row);
				kmcp = cell.getStringValue();
				
				cell = cells.getCell("C" + row);
				diengiai = cell.getStringValue();
				
				cell = cells.getCell("D" + row);
				sohieutk = cell.getStringValue();

				query = "SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '" + sohieutk + "'";
				rs = db.get(query);
				
				if(rs.next()){
					tkId = rs.getString("PK_SEQ");
					rs.close();
					cell = cells.getCell("I" + row);
					cell.setValue(tkId);
					
				}
				
				cell = cells.getCell("K" + row);
				ttcp = cell.getStringValue();
				query = "SELECT PK_SEQ FROM ERP_TRUNGTAMCHIPHI WHERE MA = '" + ttcp + "'";
				rs = db.get(query);
				
				if(rs.next()){
					ttcpId = rs.getString("PK_SEQ");
					rs.close();
					cell = cells.getCell("J" + row);
					cell.setValue(ttcpId);
				}
				
				
				if(kmcp.trim().length() > 0){
					query = "SELECT COUNT(*) AS NUM FROM ERP_NHOMCHIPHI WHERE TEN = N'" + kmcp + "'";
					System.out.println(query);
			
					rs = db.get(query);
			 
					if(rs != null){
						rs.next();
						if(rs.getString("NUM").equals("0"))	{
							query = "INSERT INTO ERP_NHOMCHIPHI(TEN, DIENGIAI, LOAI, CONGTY_FK, DONVITHUCHIEN_FK, TAIKHOAN_FK, TTCHIPHI_FK, " +
									"TRANGTHAI, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA) " +
									"VALUES(N'" + kmcp + "', N'" + diengiai + "', '2', '100005', '100000', " + tkId + ", " + ttcpId + ", " +
									"'1', '100379', '100379', '2014-09-09', '2014-09-09') ";
							System.out.println("row: " + row + " - " + query);
							db.update(query);
						
							query = "SELECT SCOPE_IDENTITY() AS ID";
							ResultSet rs2 = db.get(query);
							rs2.next();
							kmcpId = rs2.getString("ID");
							rs2.close();
						
							cell = cells.getCell("A" + row);
							cell.setValue(kmcpId);
							
						}else{
							query = "SELECT PK_SEQ FROM ERP_NHOMCHIPHI WHERE TEN = N'" + kmcp + "'";
							
							ResultSet rs2 = db.get(query);
							if(rs2 != null){
								rs2.next();
								cell = cells.getCell("A" + row);
								kmcpId = rs2.getString("PK_SEQ");
								cell.setValue(kmcpId);
								rs2.close();
							}
							
							query = "UPDATE ERP_NHOMCHIPHI SET TEN = N'" + kmcp + "', DIENGIAI = N'" + diengiai + "', " +
									"TAIKHOAN_FK = " + tkId + ", TTCHIPHI_FK = " + ttcpId + ", NGAYSUA = '2014-09-08', NGUOISUA = '100379' " +
									"WHERE PK_SEQ = " + kmcpId + "";
							System.out.println("row: " + row + " - " + query);
							db.update(query);
						
						}
					
						rs.close();
					}
						
				}
			}
			
			 
		/*db.getConnection().commit();
		db.getConnection().setAutoCommit(true);*/

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
