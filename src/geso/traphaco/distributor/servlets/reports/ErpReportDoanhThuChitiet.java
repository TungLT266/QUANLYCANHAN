package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
 
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
  
import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.util.*;

public class ErpReportDoanhThuChitiet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ErpReportDoanhThuChitiet()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		String ctyId = (String)session.getAttribute("congtyId");
		
		obj.setErpCongtyId(ctyId);
		obj.setuserId(userId);
		obj.setdiscount("1");
		obj.setvat("0");
		obj.setdvdlId("");
		obj.settype("1");
		obj.InitErp();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportDoanhThuChitiet.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String name = sdf.format(cal.getTime());
		name = name.replaceAll("-", "");
		name = name.replaceAll(" ", "_");
		name = name.replaceAll(":", "");
		return "_" + name;
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		Utility util = new Utility();
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
		geso.traphaco.distributor.util.Utility Ult = new geso.traphaco.distributor.util.Utility();
		
		String nppId = request.getParameter("nppId");
		nppId = Ult.getIdNhapp(userId);
		obj.setnppId(nppId);
		
		String nppTen = Ult.getTenNhaPP();
		obj.setuserTen(userTen);
		
		String ctyId = (String)session.getAttribute("congtyId");
		
		obj.setErpCongtyId(ctyId);
		
		
		String tungay = request.getParameter("Sdays"); // <!---
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);
		
		String denngay = request.getParameter("Edays");// <!---
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);
		
	 
		int i=0;
		String[] kbhidchon = request.getParameterValues("kbhids");
		String strkbhid="0";
		 if(kbhidchon!=null){
			 for(  i=0;i<kbhidchon.length;i++){
				 strkbhid+=","+kbhidchon[i];
			 }
		 }
		 obj.setkenhId(strkbhid);
			//vùng
		 String[] vungids = request.getParameterValues("vungids");
			String strvungids="0";
			 if(vungids!=null){
				 for(  i=0;i<vungids.length;i++){
					 strvungids+=","+vungids[i];
				 }
			 }
		 obj.setvungId(strvungids);
			 
			//khu vực 
		 String[] khuvucids = request.getParameterValues("khuvucids");
			String strkhuvucids="0";
			 if(khuvucids!=null){
				 for(  i=0;i<khuvucids.length;i++){
					 strkhuvucids+=","+khuvucids[i];
				 }
			 }
		 obj.setkhuvucId(strkhuvucids);
		 
		
		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);
		
		String[] fieldsAn = request.getParameterValues("fieldsAn");
		obj.setFieldHidden(fieldsAn);
		String action = request.getParameter("action");
		
		if (action.equals("tao"))
		{
 
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=ReportDoanhThu_Chitiet" + this.getPiVotName() + ".xlsm");
				OutputStream out = response.getOutputStream();
				try
				{
					CreatePivotTable(out, response, request, fieldsHien, obj); 
					
				} catch (Exception ex)
				{
					obj.setMsg(ex.getMessage());
					request.getSession().setAttribute("errors", ex.getMessage());
					obj.InitErp();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportDoanhThuChitiet.jsp";
					response.sendRedirect(nextJSP);
					
				}
			 
		}else{
			obj.InitErp();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportDoanhThuChitiet.jsp";
			response.sendRedirect(nextJSP);
		}
		
	
		
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request,  
			String[] fieldsHien, IStockintransit obj) throws Exception
	{
		try
		{
			
				String strfstream = getServletContext().getInitParameter("path") + "\\ReportDoanhThuChiTiet.xlsm";
			 
				FileInputStream fstream = new FileInputStream(strfstream);
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				Worksheets worksheets = workbook.getWorksheets();
		  		Worksheet worksheet_2 = worksheets.getSheet("sheet1");
		  		
	  		 	Cells cells = worksheet_2.getCells();
			   
	  		 	Cell	cell = cells.getCell("AV1");
				Style style1=cell.getStyle();
				 
				dbutils db = new dbutils();
			 
				 // khách hàng
				 
				
				String[] khidchon = request.getParameterValues("khIds");
				String khstr="0";
				String idkhstr="0";
				String idnppstr="0";
				String idnvstr="0";
				int i=0;
				 if(khidchon!=null){
					  
						 if(khidchon[0].length()>2){
							
							 khstr+=","+ khidchon[i];
							 if(khidchon[i].substring(0,2).equals("KH")){
								 idkhstr+=","+khidchon[i].substring(2);
							 }else  if(khidchon[i].substring(0,2).equals("NP")){
								 
								 idnppstr+=","+khidchon[i].substring(3);
							 }else{
								 idnvstr+=","+khidchon[i].substring(2);
							 }
							 i++;
						 }else{
							 i=0;
						 }
					 
				 }
					 
					System.out.println("i nke :"+i); 
			 obj.setKhachhangIds(khstr);
		 
			String query=" delete KHACHHANG_TIMKIEM  ";
			 db.update(query);
			 query=" INSERT INTO KHACHHANG_TIMKIEM (MA_TIMKIEM)  " +
			 		" SELECT  'KH'+ CAST(pk_seq AS VARCHAR(10))  FROM KHACHHANG where 1=1  " ;
			 if(i>0){
				 query +=" and PK_SEQ IN ("+idkhstr+") ";
			 }
			 
			 if(obj.getkenhId().length() > 1){	
				 query = query +	"AND KHACHHANG.PK_SEQ  in ( SELECT KHACHHANG_FK FROM KHACHHANG_KENHBANHANG WHERE KBH_FK IN(" + obj.getkenhId() + ")  ) ";
				}
				if(obj.getvungId().length() >1 ){
					query+=" and KHACHHANG.DIABAN IN ( "+ 
						 " SELECT DB.PK_SEQ FROM DIABAN DB INNER JOIN KHUVUC KV On  KV.PK_SEQ=DB.KHUVUC_FK  "+ 
						 " WHERE KV.VUNG_FK IN ("+obj.getvungId()+")  " +
						 		"" +(obj.getkhuvucId().length() >1? " AND KV.PK_SEQ IN ("+obj.getkhuvucId()+") " : "") +
						 		" )";
					  	
				}else{
					if(obj.getkhuvucId().length() >1){
						query+=" and KHACHHANG.DIABAN IN ( "+ 
						 " SELECT DB.PK_SEQ FROM DIABAN DB INNER JOIN KHUVUC KV On  KV.PK_SEQ=DB.KHUVUC_FK  "+ 
						 " WHERE    KV.PK_SEQ IN ("+obj.getkhuvucId()+") "   +
						 		" )";
					}
					
				}
				
			 
			db.update(query);
			
			query=" INSERT INTO KHACHHANG_TIMKIEM (MA_TIMKIEM) SELECT  'NPP'+ CAST(pk_seq AS VARCHAR(10))  FROM NHAPHANPHOI  where 1 =1 " ;
			if(i>0){
				query +="  and  PK_SEQ IN ("+idnppstr+") ";
			} 
			
			if(obj.getvungId().length() >1){
				query+=" and PK_SEQ IN (SELECT NPP_FK FROM NHAPHANPHOI_VUNG where VUNG_FK IN ("+obj.getvungId()+"))";
			}
			if(obj.getkhuvucId().length()>1){
				query+=" and PK_SEQ IN (SELECT NPP_FK FROM NHAPHANPHOI_KHUVUC where KHUVUC_FK IN ("+obj.getkhuvucId()+"))";
			}
			if(obj.getkenhId().length()>1){
				query+=" and PK_SEQ IN (SELECT NPP_FK FROM NHAPP_KBH where kbh_fk IN ("+obj.getkenhId()+"))";
			}
			
			db.update(query);
			
			query="INSERT INTO KHACHHANG_TIMKIEM (MA_TIMKIEM) SELECT  'NV'+ CAST(pk_seq AS VARCHAR(10))  FROM ERP_NHANVIEN where 1=1  " ;
			if(i>0){
				query +=" and  PK_SEQ IN ("+idnvstr+") ";
			}
			db.update(query);
	  
	  		worksheets = workbook.getWorksheets();
	  		Worksheet worksheet_REPORT_HOADON = worksheets.getSheet("sheet1");
	  		worksheet_REPORT_HOADON.setName("Report_Chitiet_DoanhThu");
	  		Dodulieu_worksheet_tonghop(worksheet_REPORT_HOADON,obj,db);
	  		
	  		String[] param = new String[3];
	  		param[0] = obj.gettungay().equals("") ? null : obj.gettungay();
			param[1] = obj.getdenngay().equals("") ? null : obj.getdenngay();
			param[2] = obj.getErpCongtyId();
		
			ResultSet	rs = db.getRsByPro("GET_REPORT_DOANHTHU_CHITIET_HOADON", param);
			  
	  		Worksheet worksheet_REPORT_DoanhThu = worksheets.addSheet("DoanhThu_Hoadon");
	  		worksheet_REPORT_DoanhThu.setName("Report_DoanhThu_hoadon");
	  		this.TaoBaoCao(db,rs,worksheet_REPORT_DoanhThu, obj,"DOANH THU BÁN HÀNG THEO K.HÀNG - M.HÀNG",style1);
	 
			workbook.save(out);
			
			
			fstream.close();
			db.shutDown();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}

	private void TaoBaoCao(dbutils db,ResultSet rs,Worksheet worksheet, IStockintransit obj, String diengiai, Style style12) 
	{
		  try{
			  
			   Cells cells = worksheet.getCells();
			   
			   for(int i=0;i<30;i++){
				   cells.setColumnWidth(i, 20.0f);   
			   }
			
			    cells.setRowHeight(0, 50.0f);
			    Cell cell = cells.getCell("A1");
			    ReportAPI.getCellStyle(cell, Color.RED, true, 14, diengiai);
			    
			    cell = cells.getCell("A3");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + obj.gettungay() + "   Đến ngày: " + obj.getdenngay());
				cell = cells.getCell("A4");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + obj.getDateTime());
				
				cell = cells.getCell("B4");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getuserTen());
				
				 
				

			   worksheet.setGridlinesVisible(false);
			   
			 
			   
			   ResultSetMetaData rsmd = rs.getMetaData();
			   int socottrongSql = rsmd.getColumnCount();
			   
			   int countRow = 4;

			   for( int i =1 ; i <=socottrongSql ; i ++  )
			   {
			    cell = cells.getCell(countRow,i-1 );
			    
		    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
		    	
		    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, rsmd.getColumnName(i));
		    	
			   }
			   countRow ++;
			   
			  
			   while(rs.next())
			   {
			    for(int i = 1; i <= socottrongSql; i ++)
			    {
			     cell = cells.getCell(countRow,i-1 );
			     if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
			     {
			    	 cell.setStyle(style12);
			    	 ReportAPI.getCellStyle_double(cell, "Arial", Color.BLACK, false, 9,  rs.getDouble(i));
			     // ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
			     }
			     else
			     {
			    	 ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString(i));
			    //  ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
			     }
			    }
			    ++countRow;
			   }
			   
			   if(rs!=null)rs.close();
			    

			 
			  }catch(Exception ex){
				  ex.printStackTrace();
				    
			  }
	}
 
	
	public static String GetExcelColumnName(int columnNumber)
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
	
	private void Dodulieu_worksheet_tonghop(Worksheet worksheet_2,
			IStockintransit obj, dbutils db) {
		try{
		// TODO Auto-generated method stub
		Cells cells = worksheet_2.getCells();
			cells.setRowHeight(0, 50.0f);
	    Cell cell = cells.getCell("C1");
	    ReportAPI.getCellStyle(cell, Color.RED, true, 14, "BÁO CÁO CHI TIẾT DOANH THU KHÁCH HÀNG THEO SẢN PHẨM");
	    
	    cell = cells.getCell("C3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + obj.gettungay() + "   Đến ngày: " + obj.getdenngay());
		cell = cells.getCell("C4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + obj.getDateTime());
		
		cell = cells.getCell("D4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getuserTen());

		String[] param = new String[3];
  		 
		cell = cells.getCell("AX1");
		 Style style1_number=cell.getStyle();
		 
		 
		cell = cells.getCell("AW1");
		 Style style1_chudam=cell.getStyle();
		 
	 	 	 cell = cells.getCell("BA1");
		 Style style1_header=cell.getStyle();
	 	 cell = cells.getCell("AY1");
		 Style style_footer=cell.getStyle();
		 
	 
		 cell = cells.getCell("AU1");
		 Style style_text_vien=cell.getStyle();
		 
		 
		 
		 
		 
		 	param[0] = obj.gettungay().equals("") ? null : obj.gettungay();
			param[1] = obj.getdenngay().equals("") ? null : obj.getdenngay();
			param[2] = obj.getErpCongtyId();
			
			ResultSet	rs = db.getRsByPro("GET_REPORT_DOANHTHU_CHITIET_SP", param);
			  
	  		int i=7;
	  			Hashtable<String, Integer> htb_sp =   new Hashtable<String, Integer>();
	  			String vitri_cot="";
	  			int rowindex=11;
	  			
		  		while (rs.next()){
		  			
		  			 htb_sp.put(rs.getString("SANPHAMID"), i);
		  			 vitri_cot=GetExcelColumnName(i);
			  		 cell = cells.getCell(vitri_cot+""+rowindex);
					 cell.setStyle(style1_header);
					 cell.setValue(rs.getString("tensp"));
		  			 i++;
		  		}
		  		rs.close();
		  		int dongcuoisp=i-1;
		  	
		  		for(int k=dongcuoisp; k< dongcuoisp+9 ;k++ ){
		  			cells.setColumnWidth(k, 20.0f);
		  		}
		  		 
		  		 
			  rowindex++;
			   
			 
			  
			  rs = db.getRsByPro("GET_REPORT_DOANHTHU_CHITIET", param);
		 
			  
			  String khid_bk="";
			  String tenkh_bk="";
			  double doanhthu=0;
			  double khoancantru=0;
			  String MAKH_BK="";
 
			  int count=0;
		 
			  // mảng lưu các vị trí trển nhửng row tổng
		 
			  while(rs.next()){
				  
				  if(count==0){
					    khid_bk=rs.getString("Isnpp")+"_"+rs.getString("IDKH"); 
					    tenkh_bk=rs.getString("TENKHACHHANG");
					    MAKH_BK=rs.getString("MAKHACHHANG");
					      doanhthu=rs.getDouble("DOANHTHU");
						    khoancantru=rs.getDouble("THUKHAC");
						    for(int j=7;j<dongcuoisp+1  ;j++ ){
								vitri_cot=GetExcelColumnName(j);
								cell = cells.getCell(vitri_cot+""+rowindex);
								cell.setStyle(style1_number); 
								cell.setValue(0);
								  
							 }
						    
						    
				  }
				   
				  
				  if(!khid_bk.equals(rs.getString("Isnpp")+"_"+rs.getString("IDKH"))){
					  // in ra 1 dòng chung : 
					  	 cell = cells.getCell("C"+(rowindex));
					  	 cell.setStyle(style_text_vien);
						 cell.setValue(MAKH_BK);
						 
						 //
						 cell = cells.getCell("D"+(rowindex));
						 cell.setStyle(style_text_vien);
						 cell.setValue(tenkh_bk);
						 
						 //
						 cell = cells.getCell("E"+(rowindex));
						 cell.setStyle(style1_number);
						 cell.setValue(doanhthu);
						 
						 //
						 cell = cells.getCell("F"+(rowindex));
						 cell.setStyle(style1_number);
						 cell.setValue(khoancantru);
						 
					 
					 	khid_bk=rs.getString("Isnpp")+"_"+rs.getString("IDKH"); 
					    tenkh_bk=rs.getString("TENKHACHHANG");
					    MAKH_BK=rs.getString("MAKHACHHANG");
					    doanhthu=rs.getDouble("DOANHTHU");
					    khoancantru=rs.getDouble("THUKHAC");
						  rowindex++;
						  for(int j=7;j<dongcuoisp+1  ;j++ ){
								vitri_cot=GetExcelColumnName(j);
								cell = cells.getCell(vitri_cot+""+rowindex);
								cell.setStyle(style1_number); 
								cell.setValue(0);
								  
							 }
					 
				  }
				  
				   	 try{
					  int thutu=htb_sp.get(rs.getString("sanphamid"));
					  vitri_cot=GetExcelColumnName(thutu);
			  		  cell = cells.getCell(vitri_cot+""+(rowindex));
			  		  cell.setStyle(style1_number);
					  cell.setValue(rs.getDouble("SOLUONG"));
				   	 }catch(Exception er){}
				   	  
 
				  count++; 	 				  
			  }
			  
			   // khi ra ngoài thì thực hiện sum
			  
			  
				  // in ra 1 dòng chung : 
			  		 cell = cells.getCell("C"+(rowindex));
				  	 cell.setStyle(style_text_vien);
					 cell.setValue(MAKH_BK);
					 
					 //
					 cell = cells.getCell("D"+(rowindex));
					 cell.setStyle(style_text_vien);
					 cell.setValue(tenkh_bk);
					 
					 //
					 cell = cells.getCell("E"+(rowindex));
					 cell.setStyle(style1_number);
					 cell.setValue(doanhthu);
					 
					 //
					 cell = cells.getCell("F"+(rowindex));
					 cell.setStyle(style1_number);
					 cell.setValue(khoancantru);
					 
					  
					 // thực hiện in dòng total khách hàng cuối cùng
					 rowindex++;
					  cell = cells.getCell("C"+(rowindex));
					    cell.setStyle(style1_chudam);
					    cell.setValue("");
					  
				    cell = cells.getCell("D"+(rowindex));
				    cell.setStyle(style1_chudam);
				    cell.setValue("TOTAL");
				    
				    
						for(int j=5;j<dongcuoisp +1;j++ ){
							vitri_cot=GetExcelColumnName(j);
							cell = cells.getCell(vitri_cot+""+rowindex);
							cell.setStyle(style1_header);
							cell.setFormula("=SUM("+vitri_cot+""+(12)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
						 }
					 
					  
					  
			  rs.close();
			 
			 //total tổng cuối cùng
			   
	 
		}catch(Exception err){
			err.printStackTrace();
		}
	}

}

