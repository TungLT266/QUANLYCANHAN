package geso.traphaco.center.servlets.reports;

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


public class DailySalesSvl extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    public DailySalesSvl() {
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
		String nextJSP = "/TraphacoHYERP/pages/Center/DailySalesReport.jsp";
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
	 
		 
		String nextJSP = "/TraphacoHYERP/pages/Center/DailySalesReport.jsp";		 
		try{
			String action=util.antiSQLInspection(request.getParameter("action"));
			if(action.equals("Taomoi")){
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", 
		        		"attachment; filename=ThucHienChiTieuTT.xlsm");
		        OutputStream out = response.getOutputStream();
		        String query = setQuery(obj);
		        CreatePivotTable(out,obj,query);
			}			
		}catch(Exception ex){
			obj.setMsg(ex.getMessage());
		}
		obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", obj.getuserId());		
		response.sendRedirect(nextJSP);
	}
	private String setQuery( IStockintransit obj) {
		
		String fromYear = obj.getYear();
		String fromMonth = obj.getMonth();
		
		String fromDate=fromYear+'-'+fromMonth;
		String query="";
		//long restWD = 0;
		long numofDay = 0;
		 dbutils db=new   dbutils();
		 String sql=" SELECT DISTINCT NHOMSANPHAM_FK,DBO.FTBODAU(NSP.TEN) AS TEN  FROM  ERP_CHITIEU_KHACHHANG_NHOMSP  CT  "+
		 			" INNER JOIN CHITIEUTHANG B ON B.PK_SEQ=   CT.CHITIEUTHANG_FK  "+
		 			" INNER JOIN NHOMSANPHAM NSP ON NSP.PK_SEQ=NHOMSANPHAM_FK  "+
					" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
		 
	 
		 if(obj.getkenhId().length()>0){
			 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
			 
		 }
		 
		 ResultSet rs=db.get(sql);
		 String chuoi="";
		 String[] arraychuoi= new String[30];
		 String chuoiselct="";
		 String chuoingoac="[0]";//co dau []
		 if(rs!=null){
			 int i=0;
			 try {
				while (rs.next()){
					
					 if(i==0){
						 chuoingoac="["+rs.getString("nhomsanpham_fk")+"]";
						 chuoi=rs.getString("nhomsanpham_fk");
						 chuoiselct=" ,isnull(CTNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS CT"+rs.getString("nhomsanpham_fk")+",ISNULL(DSNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS DSNHOM"+rs.getString("nhomsanpham_fk");
					 }else{
						 chuoi=chuoi+","+rs.getString("nhomsanpham_fk");
						 chuoiselct=chuoiselct+", isnull(CTNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS CT"+rs.getString("nhomsanpham_fk")+",ISNULL(DSNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS DSNHOM"+rs.getString("nhomsanpham_fk");
						 chuoingoac=chuoingoac+",["+rs.getString("nhomsanpham_fk")+"]";
					 }
					 arraychuoi[i]=rs.getString("nhomsanpham_fk");
					 i++;
					 
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		 //dung file show de luu chuoi;
	 
		 
		 
		obj.setFieldShow(arraychuoi);
 
		sql=" SELECT  KBH.TEN AS KENH, VUNG.TEN AS VUNG,KV.TEN AS KHUVUC, KH.MA as MAKH, KH.TEN AS TENKH   " +  
		   chuoiselct+
		   "  FROM  " +  
		   "  (  " +  
		   "  SELECT   CT.KBH_FK ,CTNSP.KHACHHANG_FK ,CTNSP.NHOMSANPHAM_FK,CTNSP.CHITIET as CHITIEU " +  
		   "    " +  
		   "  FROM ERP_CHITIEU_KHACHHANG_NHOMSP CTNSP INNER JOIN CHITIEUTHANG CT ON  " +  
		   "  CT.PK_SEQ=CTNSP.CHITIEUTHANG_FK    " +  
		   "  WHERE CT.THANG ="+obj.getMonth()+" AND CT.NAM="+obj.getYear()+" and trangthai<>'2'  " +  
		   "   ) P  " +  
		   "  PIVOT  " +  
		   "  (  " +  
		   "  SUM(CHITIEU)  " +  
		   "  FOR NHOMSANPHAM_FK IN  " +  
		   "  ( "+chuoingoac+" ) " +  
		   "  ) AS CTNHOM  " +  
		   "  INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=CTNHOM.KHACHHANG_FK " +  
		   "  INNER JOIN KHUVUC KV ON KV.PK_SEQ=KH.KHUVUC_FK  " +  
		   "  INNER JOIN VUNG  ON VUNG.PK_SEQ=KV.VUNG_FK  " +  
		   "  INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ=CTNHOM.KBH_FK  " +  
		   "   left join " +  
		   "  ( " +  
		   "  	SELECT  HD.KBH_FK ,HD.KHACHHANG_FK ,nsp_sp.NSP_FK ,HDSP.SOLUONG* HDSP.DONGIA  as THANHTIEN FROM ERP_HOADON HD   " +  
		   "  INNER JOIN ERP_HOADON_SP HDSP ON HD.PK_SEQ=HDSP.HOADON_FK " +  
		   "  inner join NHOMSANPHAM_SANPHAM nsp_sp on nsp_sp.SP_FK=HDSP.SANPHAM_FK " +  
		   "  WHERE HD.NGAYXUATHD like '"+fromDate+"%' " +  
		   "  ) P  " +  
		   "  PIVOT  " +  
		   "  (  " +  
		   "  SUM(THANHTIEN)  " +  
		   "  FOR NSP_FK IN  " +  
		   "  ( "+chuoingoac+" ) " +  
		   "  ) AS DSNHOM on DSNHOM.KHACHHANG_FK=CTNHOM.KHACHHANG_FK and CTNHOM.KBH_FK=DSNHOM.KBH_FK  " +
		   " where 1 = 1  " ;
		
		
		    if(obj.getkenhId().length() > 0)
				query += " and kbh.pk_seq='"+obj.getkenhId()+"'";
			if(obj.getnppId().length() >0)
				query += " and npp.pk_seq = '"+obj.getnppId()+"'";
			if(obj.getvungId().length() > 0)
				query += " and vung.pk_seq = '"+obj.getvungId()+"'";
			 
			if(obj.getkhuvucId().length() > 0)
				query += " and kv.pk_seq = '"+obj.getkhuvucId()+"'";
			 
		 
		sql=sql+ query;
		 System.out.println("1.Query DAILY CUS  : " + sql);
		return sql;
	}

			private void CreatePivotTable(OutputStream out,IStockintransit obj,String query) throws Exception
		    {   
				try{
					String chuoi=getServletContext().getInitParameter("path") + "\\ThucHienChiTieuTT.xlsm";
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
		cell.setValue("TÌNH HÌNH THỰC ĐẠT CHỈ TIÊU KHÁCH HÀNG");

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
  
	    String sql=" SELECT DISTINCT NHOMSANPHAM_FK,DBO.FTBODAU(NSP.TEN) AS TEN  FROM  ERP_CHITIEU_KHACHHANG_NHOMSP  CT "+
	    		   " INNER JOIN CHITIEUTHANG B ON B.PK_SEQ=   CT.CHITIEUTHANG_FK "+
	    		   " INNER JOIN NHOMSANPHAM NSP ON NSP.PK_SEQ=NHOMSANPHAM_FK "+
	    		   " where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
	    
	    		dbutils db=new dbutils();
	    	
				 
				if(obj.getkenhId().length()>0){
				 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
				 
				}
				 int i=6;
				ResultSet rs=db.get(sql);
			
				if(rs!=null){
				
				 try {
					while (rs.next()){
						 
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ChiTieu-"+rs.getString("ten"));	
						 i=i+1;
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ThucDat-"+rs.getString("ten"));
						 i=i+1;
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("PhanTram-"+rs.getString("ten"));
						 i=i+1;
					 }
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
			 
				db.shutDown();
				 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("TongChiTieu");
				 i=i+1;
				 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("TongThucDat");
				 i=i+1;
				 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("PhanTram");
			  
				cell = cells.getCell("M1"); 
			    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9,(26*4+i)+"");
	   
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
				float phantramMTD = 0;
				while(rs.next())
				{ 				
				    cell = cells.getCell(htb.get(1) + Integer.toString(indexRow)); cell.setValue(rs.getString("KENH"));
 
					cell = cells.getCell(htb.get(2) + Integer.toString(indexRow)); cell.setValue(rs.getString("VUNG"));
					cell = cells.getCell(htb.get(3) + Integer.toString(indexRow)); cell.setValue(rs.getString("KHUVUC"));					
					cell = cells.getCell(htb.get(4) + Integer.toString(indexRow));cell.setValue(rs.getString("MAKH"));				
					cell = cells.getCell(htb.get(5) + Integer.toString(indexRow));  cell.setValue(rs.getString("TENKH"));					
					
					String []chuoi =obj.getFieldShow();
					int j=6;
					float SumChiTieuDDKD=0;
					float SumThucDatCTDDKD=0;
					for (int i=0;i<chuoi.length ;i++){
						if(chuoi[i]!=null){
							cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue( rs.getFloat("CT"+chuoi[i]));	
							SumChiTieuDDKD= SumChiTieuDDKD+rs.getFloat("CT"+chuoi[i]);
							SumThucDatCTDDKD= SumThucDatCTDDKD+rs.getFloat("DSNHOM"+chuoi[i]);
							j=j+1;
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("DSNHOM"+chuoi[i]));
							j=j+1;
							phantramMTD =0;
							if(rs.getFloat("CT"+chuoi[i]) >0){
								phantramMTD=rs.getFloat("DSNHOM"+chuoi[i])*100/rs.getFloat("CT"+chuoi[i]);
							}
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(phantramMTD);
							j=j+1;
						}
					}
					cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue(SumChiTieuDDKD);					
					j=j+1;
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(SumThucDatCTDDKD);
					j=j+1;
					float SumphantramMTD =0;
					if(SumChiTieuDDKD >0){
						SumphantramMTD=SumThucDatCTDDKD*100/SumChiTieuDDKD;
					}
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(SumphantramMTD);
					j=j+1;
					 
					
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
