package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
 
import geso.dms.distributor.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

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


public class ErpBcThuongCKThuongMai extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    public ErpBcThuongCKThuongMai() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    obj.setuserId(userId);
	    obj.init();
	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBcThuongCKThuongMai.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();	
	    Utility util = new Utility();
	  
	    obj.setuserId((String)session.getAttribute("userId")==null?"":
	    				(String) session.getAttribute("userId"));
	    
	    obj.setuserTen((String)session.getAttribute("userTen")==null? "":
	    					(String) session.getAttribute("userTen"));
	    
   	 	obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))==null?"":
   	 						util.antiSQLInspection(request.getParameter("nppId")));
   	 	
   	   	obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))==null? "":
   	 						util.antiSQLInspection(request.getParameter("kenhId")));
   	 	
	   	 obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("dvkdId")));
	   	 
	   	 obj.setMonth(util.antiSQLInspection(request.getParameter("month"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("month")));
	   	 
	   	 obj.setYear(util.antiSQLInspection(request.getParameter("year"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("year")));	   	 
	 	 
	   	 obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("vungId")));	   	 
	   	 
	   	 obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("khuvucId")));	 
	   	 	   	 
	   	
		 obj.setdvdlId(util.antiSQLInspection(request.getParameter("dvdlId"))==null? "":
			 				util.antiSQLInspection(request.getParameter("dvdlId")));		 
		
		 obj.setDdkd(util.antiSQLInspection(request.getParameter("ddkdId"))==null? "":
			 				util.antiSQLInspection(request.getParameter("ddkdId")));
		 
		 
		 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien);		 
		 
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBcThuongCKThuongMai.jsp";		 
		try{
			String action=util.antiSQLInspection(request.getParameter("action"));
			if(action.equals("Taomoi")){
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", 
		        		"attachment; filename=ThuongChietKhauKhachHang.xlsm");
		        OutputStream out = response.getOutputStream();
		        String query = setQuery(obj);
		        CreatePivotTable(out,obj,query);
			}			
		}catch(Exception ex){
			obj.setMsg(ex.getMessage());
			obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getuserId());		
			response.sendRedirect(nextJSP);
		}
		
	}
	private String setQuery( IStockintransit obj) {
		
		String fromYear = obj.getYear();
		String fromMonth = obj.getMonth();
		
		String fromDate=fromYear+'-'+fromMonth;
		String query="";
	 
		 //dung file show de luu chuoi;
	 
		  if(obj.getkenhId().length() > 0)
				query += " and kbh.pk_seq='"+obj.getkenhId()+"'";
			if(obj.getnppId().length() >0)
				query += " and npp.pk_seq = '"+obj.getnppId()+"'";
			if(obj.getvungId().length() > 0)
				query += " and vung.pk_seq = '"+obj.getvungId()+"'";
			 
			if(obj.getkhuvucId().length() > 0)
				query += " and kv.pk_seq = '"+obj.getkhuvucId()+"'";
			
			
		String sql=" SELECT SP.MA as MASP,SP.TEN as TENSP , KBH.PK_SEQ AS KBH_FK,KBH.TEN AS KENH, VUNG.TEN AS VUNG,KV.TEN AS KHUVUC, KH.MA AS MAKH, KH.TEN AS TENKH      " +  
				   "  ,ISNULL(DSNHOM.THANHTIEN,0) AS THUCDAT , case when  CTNHOM.LOAICK='0' then ISNULL(DSNHOM.THANHTIEN,0) * ISNULL(CTNHOM.THUONG ,0)/100 else ISNULL(CTNHOM.THUONG ,0) end  AS THUONG " +  
				   "  FROM   " +  
				   "  (    " +  
				   "  	SELECT  HD.KBH_FK ,HD.KHACHHANG_FK  , HDSP.SANPHAM_FK ,SUM(HDSP.SOLUONG* HDSP.DONGIA)  AS THANHTIEN FROM ERP_HOADON HD      " +  
				   "  	INNER JOIN ERP_HOADON_SP HDSP ON HD.PK_SEQ=HDSP.HOADON_FK    " +  
				   "  	INNER JOIN NHOMSANPHAM_SANPHAM NSP_SP ON NSP_SP.SP_FK=HDSP.SANPHAM_FK    " +  
				   "  	WHERE HD.NGAYXUATHD LIKE '"+fromDate+"%'    " +  
				   "  	GROUP BY HD.KBH_FK ,HD.KHACHHANG_FK  ,HDSP.SANPHAM_FK " +  
				   "   " +  
				   "  ) AS DSNHOM  " +  
				   "    LEFT JOIN  " +  
				   "  (     " +  
				   "  		SELECT CK.KBH_FK ,CKNPP.NPP_FK,CK_SP.SANPHAM_FK,CK_SP.THUONG,CK_SP.TUMUC, CK_SP.TOIMUC ,CK_SP.LOAICK  FROM  CHIETKHAUTHANG CK INNER JOIN  " +  
				   "  		CHIETKHAUTHANG_SANPHAM CK_SP ON CK.PK_SEQ=CK_SP.CHIETKHAUTHANG_FK " +  
				   "  		INNER JOIN CHIETKHAUTHANG_NPP CKNPP ON CK.PK_SEQ=CKNPP.CHIETKHAUTHANG_FK " +  
				   "  		WHERE CK.THANG= "+obj.getMonth()+" AND CK.NAM="+obj.getYear()+"  " +  
				   "  ) AS CTNHOM   ON CTNHOM.KBH_FK=DSNHOM.KBH_FK AND CTNHOM.NPP_FK=DSNHOM.KHACHHANG_FK AND CTNHOM.SANPHAM_FK=DSNHOM.KHACHHANG_FK  " +
				   "     and DSNHOM.THANHTIEN >=  CTNHOM.TUMUC and DSNHOM.THANHTIEN < = CTNHOM.TOIMUC  and CTNHOM.TUNGAY <= '"+fromDate+"-01' and CTNHOM.DENNGAY >= '"+fromDate+"-01'  " +  
				   "  INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=DSNHOM.KHACHHANG_FK  " +  
				   "  INNER JOIN KHUVUC KV ON KV.PK_SEQ=KH.KHUVUC_FK     " +  
				   "  INNER JOIN VUNG  ON VUNG.PK_SEQ=KV.VUNG_FK     " +  
				   "  INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ=DSNHOM.KBH_FK " +
				   " inner join ERP_SANPHAM sp on sp.PK_SEQ=DSNHOM.SANPHAM_FK " +  
				   "  WHERE 1 = 1  " +query;  
		   
		 
		 System.out.println("1.Query DAILY CUS  : " + sql);
		return sql;
	}

			private void CreatePivotTable(OutputStream out,IStockintransit obj,String query) throws Exception
		    {   
				try{
					String chuoi=getServletContext().getInitParameter("path") + "\\ThuongChietKhauThuongMai.xlsm";
					FileInputStream fstream = new FileInputStream(chuoi);
					Workbook workbook = new Workbook();
					workbook.open(fstream);
					workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
					
					CreateStaticHeader(workbook,obj); 
					FillData(workbook,obj.getFieldShow(), query, obj); 
					workbook.save(out);
					fstream.close();
			     }catch(Exception ex){
			    	 ex.printStackTrace();
			    	 throw new Exception(ex.getMessage());
			     }	    
		   }
			private Hashtable< Integer, String > htbValueCell (){
				Hashtable<Integer, String> htb=new Hashtable<Integer, String>();
				htb.put(1,"DA");htb.put(2,"DB");htb.put(3,"DC");htb.put(4,"DD");htb.put(5,"DE");
				htb.put(6,"DF");htb.put(7,"DG");htb.put(8,"DH");htb.put(9,"DI");htb.put(10,"DJ");
				htb.put(11,"DK");htb.put(12,"DL");htb.put(13,"DM");htb.put(14,"DN");htb.put(15,"DO");
				htb.put(16,"DP");htb.put(17,"DQ");htb.put(18,"DR");htb.put(19,"DS");htb.put(20,"DT");
				htb.put(21,"DU");htb.put(22,"DV");htb.put(23,"DW");htb.put(24,"DX");htb.put(25,"DY");
				htb.put(26,"DZ");htb.put(27,"EA");htb.put(28,"EB");htb.put(29,"EC");htb.put(30,"ED");
				htb.put(31,"EE");htb.put(32,"EF");htb.put(33,"EG");htb.put(34,"EH");htb.put(35,"EI");
				htb.put(36,"EJ");htb.put(37,"EK");htb.put(38,"EL");htb.put(39,"EM");htb.put(40,"EN");
				htb.put(41,"EO");htb.put(42,"EP");htb.put(43,"EQ");htb.put(44,"ER");htb.put(45,"ES");
				htb.put(46,"ET");htb.put(47,"EU");htb.put(48,"EV");htb.put(49,"EW");htb.put(50,"EX");
				htb.put(51,"EY");htb.put(52,"EZ");htb.put(53,"FA");htb.put(54,"FB");htb.put(55,"FC");
				htb.put(56,"FD");htb.put(57,"FE");htb.put(58,"FF");htb.put(59,"FG");htb.put(60,"FH");
				htb.put(61,"FI");htb.put(62,"FJ");htb.put(63,"FK");htb.put(64,"FL");htb.put(65,"FM");
				return htb; 
			}
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) 
	{
		Hashtable<Integer, String> htb=this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		Style style;		
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("BÁO CÁO THƯỞNG KHÁCH HÀNG");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(14);// size chu
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + obj.getMonth() + "" );
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + obj.getYear() + "" );
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    
	    cell = cells.getCell(htb.get(1)+"1"); cell.setValue("KenhBanHang");
	    cell = cells.getCell(htb.get(2)+"1"); cell.setValue("ChiNhanh");
	    cell = cells.getCell(htb.get(3)+"1"); cell.setValue("KhuVuc");
	    cell = cells.getCell(htb.get(4)+"1"); cell.setValue("MaNhaPhanPhoi");	
	    cell = cells.getCell(htb.get(5)+"1");cell.setValue("NhaPhanPhoi");  	    
		cell = cells.getCell(htb.get(6)+"1"); cell.setValue("MaSanPham");
		cell = cells.getCell(htb.get(7)+"1"); cell.setValue("TenSanPham");
		cell = cells.getCell(htb.get(8)+"1"); cell.setValue("ThucDat");
		cell = cells.getCell(htb.get(9)+"1"); cell.setValue("PhanTramChietKhau");
		cell = cells.getCell(htb.get(10)+"1"); cell.setValue("Thuong");
				 
				 
	   
	}

	private void FillData(Workbook workbook,String[] fieldShow, String query, IStockintransit obj)throws Exception 
	{
		
		Hashtable<Integer, String> htb=this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
	    cells.setColumnWidth(0, 10.0f);
		cells.setColumnWidth(1, 15.0f);
		cells.setColumnWidth(2, 15.0f);
		cells.setColumnWidth(3, 15.0f);
		cells.setColumnWidth(4, 15.0f);
		cells.setColumnWidth(5, 15.0f);
		cells.setColumnWidth(6, 15.0f);
		cells.setColumnWidth(7, 15.0f);
		cells.setColumnWidth(8, 15.0f);
		cells.setColumnWidth(9, 15.0f);
		cells.setColumnWidth(10, 15.0f);
		cells.setColumnWidth(11, 15.0f);
		cells.setColumnWidth(12, 15.0f);
		cells.setColumnWidth(13, 15.0f);
		cells.setColumnWidth(14, 15.0f);
		cells.setColumnWidth(15, 15.0f);
		cells.setColumnWidth(16, 15.0f);
		dbutils db = new dbutils();		
		ResultSet rs = db.get(query);	
		int indexRow = 2;
		try 
			{				
				Cell cell = null;
				 
				while(rs.next())
				{ 				
				    cell = cells.getCell(htb.get(1) + Integer.toString(indexRow)); cell.setValue(rs.getString("KENH"));
 
					cell = cells.getCell(htb.get(2) + Integer.toString(indexRow)); cell.setValue(rs.getString("VUNG"));
					cell = cells.getCell(htb.get(3) + Integer.toString(indexRow)); cell.setValue(rs.getString("KHUVUC"));					
					cell = cells.getCell(htb.get(4) + Integer.toString(indexRow));cell.setValue(rs.getString("MAKH"));				
					cell = cells.getCell(htb.get(5) + Integer.toString(indexRow));  cell.setValue(rs.getString("TENKH"));					
					
					 
					cell = cells.getCell(htb.get(6) + Integer.toString(indexRow));  cell.setValue(rs.getString("MASP"));					
					 
					cell = cells.getCell(htb.get(7)+ Integer.toString(indexRow)); cell.setValue(rs.getString("TENSP"));
					 
					cell = cells.getCell(htb.get(8)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("THUCDAT"));
					cell = cells.getCell(htb.get(9)+ Integer.toString(indexRow)); cell.setValue(0);
					
					double thuong= rs.getDouble("Thuong");
					cell = cells.getCell(htb.get(10)+ Integer.toString(indexRow)); cell.setValue(thuong);
					indexRow++;
				}
				if(rs != null) rs.close();
				if(db!=null){
					db.shutDown();
				}
				
				if(indexRow==2){
					throw new Exception("Không có dữ liệu lấy báo cáo,vui lòng chọn lại thời điểm lấy báo cáo");
				}
				
			}catch(java.sql.SQLException err){
				err.printStackTrace();
				System.out.println(err.toString());
				throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :"+err.toString());
			}
		
		
	}
	
	private String GetExcelColumnName(int columnNumber)
	 {
	     int dividend = columnNumber;
	     String columnName = "";
	     int modulo;

	     while (dividend > 0)
	     {
	         modulo = (dividend - 1) % 26;
	         columnName = (char)(65 + modulo) + columnName;
	         dividend = (int)((dividend - modulo) / 26);
	     } 

	     return columnName;
	 }
	
}
