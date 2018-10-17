package geso.traphaco.erp.servlets.lapngansach;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lapngansach.ILapngansachList;
import geso.traphaco.erp.beans.lapngansach.imp.LapngansachList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class TheodoitrungtamchiphiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public TheodoitrungtamchiphiSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ILapngansachList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new LapngansachList();	    
	    obj.setUserId(userId);
	    obj.initTTCP();
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTheoDoiTTCP.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream(); 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		ILapngansachList obj = new LapngansachList();		
		
		String userId = request.getParameter("userId");
		obj.setUserId(userId);
		
		String thang = request.getParameter("thang");
		if(thang == null)
			thang = "";
		obj.setThang(thang);
		
		String nam = request.getParameter("nam");
		if(nam == null)
			nam = "";
		obj.setNam(nam);
		
		String ttcpId = request.getParameter("ttcpId");
		if(ttcpId == null)
			ttcpId = "";
		obj.setTtcpId(ttcpId);
		
		response.setContentType("application/xlsm");
		response.setHeader("Content-Disposition", "attachment; filename=BaoCaoTheoDoiTTCP.xlsm");
		
		try 
		{
			CreatePivotTable(out, obj, ttcpId, thang, nam);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			session.setAttribute("obj", obj);
			obj.initTTCP();
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTheoDoiTTCP.jsp";
			obj.setMsg("Khong the tao bao cao..." + e.getMessage());
			
			response.sendRedirect(nextJSP);	
		}
	}
	
	private boolean CreatePivotTable(OutputStream out, ILapngansachList obj, String ttcpId, String thang, String nam) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		 
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpTheoDoiTTCP.xlsm");

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
	     CreateStaticHeader(workbook, obj.getThang(), obj.getNam(), obj.getTtcpId());	     
	     boolean isTrue = CreateStaticData(workbook, obj, ttcpId, thang, nam);
	     if(!isTrue){
	    	 return false;
	     }
	     workbook.save(out);
			
		fstream.close();
	     return true;
   }
	
	private void CreateStaticHeader(Workbook workbook, String thang, String nam, String ttcpId) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    worksheet.setName("Sheet1");
	    
	    Cells cells = worksheet.getCells();
		
	    Style style;
	    Font font = new Font();
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);
	   	
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	  
	    
	    String tieude = "BÁO CÁO THEO DÕI CHI PHÍ THEO TRUNG TÂM CHI PHÍ";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	         
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + thang + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A3"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + nam + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");
	    dbutils db = new dbutils();
	    ResultSet rs = db.get("select diengiai from erp_trungtamchiphi where pk_seq = '"+ ttcpId +"'");
	    try{
	    rs.next();	    
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Của TTCP : " + rs.getString("diengiai"));
	    }catch(Exception ex){}

	    cell = cells.getCell("AA1"); 	cell.setValue("TenKhoanMucChiPhi");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AB1"); 	cell.setValue("TaiKhoanKeToan");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AC1"); 	cell.setValue("ThucChi");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AD1"); 	cell.setValue("NganSach");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AE1"); 	cell.setValue("ChenhLech"); ReportAPI.setCellHeader(cell);	    
	    db.shutDown();
	}
	
	private boolean CreateStaticData(Workbook workbook, ILapngansachList obj, String ttcpId, String thang, String nam) throws Exception
	{		
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		Utility util=new Utility();	 	
	 	String strthang = "";	 	
		if (obj.getThang().length() > 0)
		{	strthang = "00" + obj.getThang();			
			thang = strthang.substring(strthang.length()-2);
		}
		System.out.println("Thang str : "+strthang);
		System.out.println("Thang : "+thang);
		
		/*String query=  "SELECT TKKT.TENTAIKHOAN, TKKT.SOHIEUTAIKHOAN, A.THUCCHI, A.NGANSACH, (A.THUCCHI - A.NGANSACH) AS CHENHLECH " +
				"FROM " +
				"( " +
				"	SELECT CP.TAIKHOAN_FK, SUM(NHSP.DONGIAVIET * NHSP.SOLUONGNHAN) AS THUCCHI, " +
				"	ISNULL(NSCP.DUTOAN, 0) AS NGANSACH, CP.TTCHIPHI_FK, NH.PK_SEQ AS NHANHANGID " +
				"	FROM ERP_NHANHANG NH " +
				"	INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.NHANHANG_FK = NH.PK_SEQ " +
				"	INNER JOIN ERP_NHOMCHIPHI CP ON CP.PK_SEQ = NHSP.CHIPHI_FK " +
				"	LEFT JOIN ERP_LAPNGANSACH_CHIPHI NSCP ON NSCP.CHIPHI_FK = NHSP.CHIPHI_FK " +
				"	INNER JOIN ERP_LAPNGANSACH NS ON NS.PK_SEQ = NSCP.LAPNGANSACH_FK " +
				"	WHERE NH.TRANGTHAI = '1' " +
				"AND NS.TRANGTHAI = '1' "+
				" 	AND SUBSTRING(NH.NGAYNHAN, 1, 7) = '"+ obj.getNam()+"-"+thang +"' "+		
				"	AND CP.TTCHIPHI_FK = '"+ obj.getTtcpId() +"' " +
				"	GROUP BY NH.PK_SEQ, CP.TTCHIPHI_FK, CP.TAIKHOAN_FK, ISNULL(NSCP.DUTOAN, 0) " +
				
				"	UNION ALL " +
				"	SELECT BT_CT.TAIKHOANKT_FK, ISNULL(BT_CT.NO, ((-1)*BT_CT.CO)) AS THUCCHI, " +
				"	0 AS NGANSACH, BT_CT.TTCP_FK, BT.PK_SEQ AS BUTTOANID " +
				"	FROM ERP_BUTTOANTONGHOP BT " +
				"	INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BT_CT ON BT_CT.BUTTOANTONGHOP_FK = BT.PK_SEQ " +
				"	WHERE BT.TRANGTHAI = '1' AND SUBSTRING(BT.NGAYBUTTOAN, 1, 7) = '"+ obj.getNam()+"-"+thang +"' " +				
				"	AND BT_CT.TTCP_FK = '"+ obj.getTtcpId() +"' " +
				
				"	UNION ALL " +
				"	SELECT CP.TAIKHOAN_FK, CPK_CT.TONGTIENCHUATHUE AS THUCCHI, " +
				"	ISNULL(NSCP.DUTOAN, 0) AS NGANSACH, CP.TTCHIPHI_FK, CPK.PK_SEQ AS CHIPHIKHACID " +
				"	FROM ERP_CHIPHIKHAC CPK " +
				"	INNER JOIN ERP_CHIPHIKHAC_CHITIET CPK_CT ON CPK_CT.CHIPHIKHAC_FK = CPK.PK_SEQ " +
				"	INNER JOIN ERP_NHOMCHIPHI CP ON CP.PK_SEQ = CPK_CT.NHOMCHIPHI_FK " +
				"	LEFT JOIN ERP_LAPNGANSACH_CHIPHI NSCP ON NSCP.CHIPHI_FK = CPK_CT.NHOMCHIPHI_FK " +
				"	INNER JOIN ERP_LAPNGANSACH NS ON NS.PK_SEQ = NSCP.LAPNGANSACH_FK " +
				"	WHERE CPK.TRANGTHAI = '1' AND NS.TRANGTHAI = '1' AND SUBSTRING(CPK.NGAY, 1, 7) = '"+ obj.getNam()+"-"+thang +"' " +
				"	AND CP.TTCHIPHI_FK = '"+ obj.getTtcpId() +"' " +
				") A " +
				"INNER JOIN ERP_TAIKHOANKT TKKT ON TKKT.PK_SEQ = A.TAIKHOAN_FK WHERE 1=1 ";*/
		
		String query = 
		" SELECT TKKT.TENTAIKHOAN, TKKT.SOHIEUTAIKHOAN, A.THUCCHI, A.NGANSACH, (A.NGANSACH - A.THUCCHI) AS CHENHLECH " +
		" FROM " +
		" ( " +	
		 " SELECT CP.TAIKHOAN_FK, ISNULL(NH.THUCCHI, 0) AS THUCCHI, ISNULL(NSCP.DUTOAN, 0) AS NGANSACH " + 
		 " from ERP_NHOMCHIPHI CP" + 
		 " INNER JOIN ERP_LAPNGANSACH_CHIPHI NSCP ON NSCP.CHIPHI_FK = cp.PK_SEQ" + 
		 " INNER JOIN ERP_LAPNGANSACH NS ON NS.PK_SEQ = NSCP.LAPNGANSACH_FK" + 
		 " LEFT JOIN" + 
		 " ( " + 
		 "	SELECT NHSP.CHIPHI_FK, SUM(ISNULL(NHSP.DONGIAVIET, 0) * ISNULL(NHSP.SOLUONGNHAN, 0)) AS THUCCHI FROM ERP_NHANHANG ERPNH" + 
		 "	INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.NHANHANG_FK = ERPNH.PK_SEQ" + 
		 "	WHERE ERPNH.TRANGTHAI = '1'" + 
		 "	AND SUBSTRING(ERPNH.NGAYNHAN, 1, 7) = '"+ obj.getNam()+"-"+thang +"'" + 
		 "	GROUP BY NHSP.CHIPHI_FK" + 
		 " )  NH ON NH.CHIPHI_FK = CP.PK_SEQ" + 
		 " WHERE CP.TTCHIPHI_FK = '"+ obj.getTtcpId() +"' " +
		 /*"--AND NS.TRANGTHAI = '1'	" +*/ 

		 " UNION ALL" + 
		 " SELECT CP.TAIKHOAN_FK, ISNULL(BT.THUCCHI, 0) AS THUCCHI, 0 AS NGANSACH " + 
		 " from ERP_NHOMCHIPHI CP" + 
		 " LEFT JOIN" + 
		 " (" + 
		 "	SELECT BT_CT.TAIKHOANKT_FK, ISNULL(BT_CT.NO, ((-1)*BT_CT.CO)) AS THUCCHI" + 
		 "	FROM ERP_BUTTOANTONGHOP BT" + 
		 "	INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BT_CT ON BT_CT.BUTTOANTONGHOP_FK = BT.PK_SEQ " + 
		 "	WHERE BT.TRANGTHAI = '1' AND SUBSTRING(BT.NGAYBUTTOAN, 1, 7) = '"+ obj.getNam()+"-"+thang +"'" + 
		 " ) BT ON BT.TAIKHOANKT_FK = CP.TAIKHOAN_FK" + 
		 " WHERE CP.TTCHIPHI_FK = '"+ obj.getTtcpId() +"'" + 
		 
		 " UNION ALL" + 
		 " SELECT CP.TAIKHOAN_FK, ISNULL(CPK.THUCCHI, 0) AS THUCCHI, ISNULL(NSCP.DUTOAN, 0) AS NGANSACH " + 
		 " FROM ERP_NHOMCHIPHI CP " + 
		 " INNER JOIN ERP_LAPNGANSACH_CHIPHI NSCP ON NSCP.CHIPHI_FK = CP.PK_SEQ" + 
		 " INNER JOIN ERP_LAPNGANSACH NS ON NS.PK_SEQ = NSCP.LAPNGANSACH_FK " + 
		 " LEFT JOIN " + 
		 " (" + 
		 "	SELECT CPK_CT.NHOMCHIPHI_FK, CPK_CT.TONGTIENCHUATHUE AS THUCCHI FROM ERP_CHIPHIKHAC CPK" + 
		 "	INNER JOIN ERP_CHIPHIKHAC_CHITIET CPK_CT ON CPK_CT.CHIPHIKHAC_FK = CPK.PK_SEQ" + 
		 "	WHERE CPK.TRANGTHAI = '1' AND SUBSTRING(CPK.NGAY, 1, 7) = '"+ obj.getNam()+"-"+thang +"' " + 
		 " ) CPK ON CPK.NHOMCHIPHI_FK = CP.PK_SEQ " + 
		 " WHERE CP.TTCHIPHI_FK = '"+ obj.getTtcpId() +"' "+
		 /*"--AND NS.TRANGTHAI = '1'";*/
		" ) A " +
		" INNER JOIN ERP_TAIKHOANKT TKKT ON TKKT.PK_SEQ = A.TAIKHOAN_FK WHERE 1=1 ";
		
		System.out.println("Theo doi TTCP : " + query);
		ResultSet rs = db.get(query);
		
		int i = 2;
		if(rs!=null)
			
		{
			try 
			{
				for(int j = 0; j < 5; j++)
					cells.setColumnWidth(i, 15.0f);				
					Cell cell = null;			
				while(rs.next())
				{					
					String tenkhoanmuccp = rs.getString("TENTAIKHOAN");
					String tkkt = rs.getString("SOHIEUTAIKHOAN");
					
					float thucchi = rs.getFloat("THUCCHI");
					float ngansach = rs.getFloat("NGANSACH");
					float chenhlech = rs.getFloat("CHENHLECH");
										
					cell = cells.getCell("AA" + Integer.toString(i)); 	cell.setValue(tenkhoanmuccp);
					cell = cells.getCell("AB" + Integer.toString(i)); 	cell.setValue(tkkt);
					cell = cells.getCell("AC" + Integer.toString(i)); 	cell.setValue(thucchi);
					cell = cells.getCell("AD" + Integer.toString(i)); 	cell.setValue(ngansach);					
					//cell = cells.getCell("AE" + Integer.toString(i)); 	cell.setValue(chenhlech);					
					if(chenhlech < 0)
					{						
					   	cell = cells.getCell("AE" + Integer.toString(i));	cell.setValue(chenhlech);	
					   	ReportAPI.setCellHeader(cell);	 
					}
					else
					{
						cell = cells.getCell("AE" + Integer.toString(i)); 	cell.setValue(chenhlech);
					}
					i++;
				}
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Không có báo cáo trong thời gian này !");
				}
			
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} else {
			if(db != null) db.shutDown();
			return false;
		}
		
		if(db != null) 
			db.shutDown();
		return true;
		
	}
	
}
