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
 
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
  
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.util.*;

public class ErpReportCongNoChitiet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ErpReportCongNoChitiet()
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
		
		obj.setuserId(userId);
		obj.setdiscount("1");
		obj.setvat("0");
		obj.setdvdlId("");
		obj.settype("1");
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
		String ctyId = (String)session.getAttribute("congtyId");
		obj.setErpCongtyId(ctyId);
		obj.InitErp();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportCongnoChitiet.jsp";
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
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    		
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
		
		String diabanId = request.getParameter("diabanId"); // <!---
		if (diabanId == null)
			diabanId = "";
		obj.setDiabanId(diabanId);
		 
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
		 
		 
		String[] khidchon = request.getParameterValues("khIds");
		String khstr="0";
		String idkhstr="0";
		String idnppstr="0";
		String idnvstr="0";
		  i=0;
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
		 System.out.println(idkhstr);
		 System.out.println(idnppstr);
			
		 
		obj.setKhachhangIds(khstr);
		dbutils db = new dbutils();
						
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
			response.setHeader("Content-Disposition", "Attachment; filename=ReportCongNo" + this.getPiVotName() + ".xlsm");
			OutputStream out = response.getOutputStream();
			
			String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "KH", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId() );
			
			try
			{
				String query=" delete KHACHHANG_TIMKIEM  ";
				
				 db.update(query);
				 query = " INSERT INTO KHACHHANG_TIMKIEM (MA_TIMKIEM)  " +
				 	   	 " SELECT  'KH'+ CAST(pk_seq AS VARCHAR(10))  FROM KHACHHANG KH  WHERE 1 = 1  "+ strQUYEN ;
				 
				 if(i>0){
					 query += " and PK_SEQ IN ("+idkhstr+") ";
				 }
				 
				 if(obj.getkenhId().length() > 1){	
					 query = query +"AND KH.PK_SEQ  in ( SELECT KHACHHANG_FK FROM KHACHHANG_KENHBANHANG WHERE KBH_FK IN(" + obj.getkenhId() + ")  ) ";
				 }
				 
				if(obj.getvungId().length() >1 ){
					query += " and KH.DIABAN IN ( "+ 
						 	 " SELECT DB.PK_SEQ FROM DIABAN DB INNER JOIN KHUVUC KV On  KV.PK_SEQ=DB.KHUVUC_FK  "+ 
						 	 " WHERE KV.VUNG_FK IN ("+obj.getvungId()+")  " +
						 	 "" +(obj.getkhuvucId().length() >1? " AND KV.PK_SEQ IN ("+obj.getkhuvucId()+") " : "") +" )";
					  	
				}else{
					if(obj.getkhuvucId().length() >1){
						query+=" and KH.DIABAN IN ( "+ 
							   " SELECT DB.PK_SEQ FROM DIABAN DB INNER JOIN KHUVUC KV On  KV.PK_SEQ=DB.KHUVUC_FK  "+ 
							   " WHERE    KV.PK_SEQ IN ("+obj.getkhuvucId()+") "   +" )";
					}
					
					if(obj.getDiabanId().length() > 1)
					{
						query += " and KH.DIABAN IN ( " +obj.getDiabanId()+ ") ";
					}
					
				}						
				System.out.println(query); 
				db.update(query);
				
				query = " INSERT INTO KHACHHANG_TIMKIEM (MA_TIMKIEM) SELECT  'NPP'+ CAST(pk_seq AS VARCHAR(10))  FROM NHAPHANPHOI  " +
					    " where 1 = 1 AND PK_SEQ in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + obj.getuserId() + "' ) " ;
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
				System.out.println(query);
				db.update(query);
				
				query = "INSERT INTO KHACHHANG_TIMKIEM (MA_TIMKIEM) SELECT  'NV'+ CAST(pk_seq AS VARCHAR(10))  FROM ERP_NHANVIEN where 1=1  " ;
				
				if(i>0){
					query +=" and  PK_SEQ IN ("+idnvstr+") ";
				}
				db.update(query);
				
				System.out.println(query);
				
				CreatePivotTable(out, response, request, fieldsHien, obj,db); 
				
				db.shutDown();
				
			} catch (Exception ex)
			{
				obj.setMsg(ex.getMessage());
				request.getSession().setAttribute("errors", ex.getMessage());
				obj.InitErp();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportCongnoChitiet.jsp";
				response.sendRedirect(nextJSP);
				
			}
		
			 
		}else{
			obj.setErpCongtyId(ctyId);
			
			obj.InitErp();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportCongnoChitiet.jsp";
			response.sendRedirect(nextJSP);
		}
		
	
		
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request,  
			String[] fieldsHien, IStockintransit obj, dbutils db) throws Exception
	{
		try
		{
			
				String strfstream = getServletContext().getInitParameter("path") + "\\ReportCongNoChiTiet.xlsm";
			 
				FileInputStream fstream = new FileInputStream(strfstream);
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				Worksheets worksheets = workbook.getWorksheets();
		  		Worksheet worksheet_2 = worksheets.getSheet("sheet1");
		  		
	  		 	Cells cells = worksheet_2.getCells();
			   
	  		 	Cell	cell = cells.getCell("P1");
				Style style1=cell.getStyle();
				  
		  		worksheets = workbook.getWorksheets();
		  		Worksheet worksheet_REPORT_HOADON = worksheets.getSheet("sheet1");
		  		worksheet_REPORT_HOADON.setName("Report_Chitiet_Congno");
		  		Dodulieu_worksheet_tonghop(worksheet_REPORT_HOADON,obj,db);
		  		
		  		  
				workbook.save(out);
				fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
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
			
			Utility util = new Utility();
		// TODO Auto-generated method stub
		Cells cells = worksheet_2.getCells();
			cells.setRowHeight(0, 50.0f);
	    Cell cell = cells.getCell("C1");
	    ReportAPI.getCellStyle(cell, Color.RED, true, 14, "BÁO CÁO CHI TIẾT CÔNG NỢ KHÁCH HÀNG THEO SẢN PHẨM");
	    
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
			/*param[3] = obj.getuserId();
			param[4] = obj.getLoainhanvien();
			param[5] = obj.getDoituongId();*/
			//param[3] = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "KH", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId());
			
			ResultSet	rs = db.getRsByPro("GET_REPORT_SP_CHITIET_CONGNOKH", param);
			  
	  		int i=9;
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
		  		 
		  		 
	  		   
	  		 vitri_cot=GetExcelColumnName(dongcuoisp+1);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex-1));
			 cell.setStyle(style1_header);
			 cell.setValue("Thành tiền");
			 
			 vitri_cot=GetExcelColumnName(dongcuoisp+1);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex));
			 cell.setStyle(style1_header);
			 cell.setValue("");
	  		 i++;
	  		 
	  		 vitri_cot=GetExcelColumnName(dongcuoisp+2);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex-1));
			 cell.setStyle(style1_header);
			 cell.setValue("Hạn TT");
			 
			 vitri_cot=GetExcelColumnName(dongcuoisp+2);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex));
			 cell.setStyle(style1_header);
			 cell.setValue("");
			 
			 
	  		 i++;
	  		   
	  		 vitri_cot=GetExcelColumnName(dongcuoisp+3);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex-1));
			 cell.setStyle(style1_header);
			 cell.setValue("Số ngày QH");
			 
			
			 
			 
			 vitri_cot=GetExcelColumnName(dongcuoisp+3);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex));
			 cell.setStyle(style1_header);
			 cell.setValue("");
			 
			 
	  		 i++;
	  		 
	  		 vitri_cot=GetExcelColumnName(dongcuoisp+4);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex-1));
			 cell.setStyle(style1_header);
			 cell.setValue("Nợ Quá Hạn");
			 
			 vitri_cot=GetExcelColumnName(dongcuoisp+4);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex));
			 cell.setStyle(style1_header);
			 cell.setValue("");
			 
			 
	  		 i++;
	  		 
	  		 vitri_cot=GetExcelColumnName(dongcuoisp+5);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex-1));
			 cell.setStyle(style1_header);
			 cell.setValue("Nợ Trong Hạn");
			 
			 vitri_cot=GetExcelColumnName(dongcuoisp+5);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex));
			 cell.setStyle(style1_header);
			 cell.setValue("");
			 
			 
	  		 i++;
	  		 vitri_cot=GetExcelColumnName(dongcuoisp+6);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex-1));
			 cell.setStyle(style1_header);
			 cell.setValue("Nợ Quá Hạn <=30");
			 
			 vitri_cot=GetExcelColumnName(dongcuoisp+6);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex));
			 cell.setStyle(style1_header);
			 cell.setValue("");
			 
			 
	  		 i++;
	  		 
	  		 vitri_cot=GetExcelColumnName(dongcuoisp+7);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex-1));
			 cell.setStyle(style1_header);
			 cell.setValue(" Nợ Xấu 31-90 Ngày");
			 
			 vitri_cot=GetExcelColumnName(dongcuoisp+7);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex));
			 cell.setStyle(style1_header);
			 cell.setValue("");
			 
			 
	  		 i++;
	  		 
	  		 vitri_cot=GetExcelColumnName(dongcuoisp+8);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex-1));
			 cell.setStyle(style1_header);
			 cell.setValue(" Nợ Quá Xấu >90 Ngày");
			 
			 vitri_cot=GetExcelColumnName(dongcuoisp+8);
	  		 cell = cells.getCell(vitri_cot+""+(rowindex));
			 cell.setStyle(style1_header);
			 cell.setValue("");
			 
	  		 i++;
	  		 
	  		 
			  rowindex++;
			   
			  int vitribatdau_sum_kh=rowindex;
			  
			  rs = db.getRsByPro("GET_REPORT_CHITIET_CONGNOKH", param);
			  String hoadonid_bk="";
			 
			  double songayquahan_bk=0;
			  String thoihangtt_bk="";
			  String ddh_bk="";
			  String ngaydathang_bk="";
			  String sohoadon_bk="";
			  String ngayhoadon_bk="";
			  
			  String tenkhtotal_bk="";
			  
			  String khid_bk="";
			  String tenkh_bk="";
			  int count=0;
			  double thanhtientung_hd=0; 
			  
			  String khachhangma_bk="";
			  // mảng lưu các vị trí trển nhửng row tổng
			   
			  String mang_cac_thutu_tong="";
			  while(rs.next()){
				  
				  if(count==0){
					    khid_bk=rs.getString("masosanh"); 
					    tenkh_bk=rs.getString("tenkh"); 
					    songayquahan_bk=rs.getDouble("songayqh");
					    thoihangtt_bk=rs.getString("thoihantt");
					    if(rs.getString("DONDATHANGID").length()>0){
					    	ddh_bk=rs.getString("DONDATHANGID").substring(0, rs.getString("DONDATHANGID").length()-2);
					    }else{
					    	ddh_bk="";
					    }
					    if(rs.getString("NGAYDONHANG").length()>0){
					    	ngaydathang_bk=rs.getString("NGAYDONHANG").substring(0, rs.getString("NGAYDONHANG").length()-2);
					    }else{
					    	ngaydathang_bk="";
					    }
					    sohoadon_bk=rs.getString("sohoadon");
					    ngayhoadon_bk=rs.getString("ngay");
					    hoadonid_bk=rs.getString("hoadonid");
					    khachhangma_bk=rs.getString("masosanh"); 
					    tenkhtotal_bk=rs.getString("tenkh");
					    
						 for(int j=9;j<dongcuoisp+1  ;j++ ){
								vitri_cot=GetExcelColumnName(j);
								cell = cells.getCell(vitri_cot+""+rowindex);
								cell.setStyle(style1_number); 
								cell.setValue(0);
						}
					 
					    
				  }
				   
				
				 
				  
				  
				  
				  
				  if(!hoadonid_bk.equals(rs.getString("hoadonid"))){
					  // in ra 1 dòng chung : 
					  	 cell = cells.getCell("C"+(rowindex));
					  	 cell.setStyle(style_text_vien);
						 cell.setValue(khid_bk);
						 
						 //
						 cell = cells.getCell("D"+(rowindex));
						 cell.setStyle(style_text_vien);
						 cell.setValue(tenkh_bk);
						 
						 //
						 cell = cells.getCell("E"+(rowindex));
						 cell.setStyle(style_text_vien);
						 cell.setValue(ddh_bk);
						 
						 //
						 cell = cells.getCell("F"+(rowindex));
						 cell.setStyle(style_text_vien);
						 cell.setValue(ngaydathang_bk);
						 
						 //
						 cell = cells.getCell("G"+(rowindex));
						 cell.setStyle(style_text_vien);
						 cell.setValue(sohoadon_bk);
						 
						 //
						 cell = cells.getCell("H"+(rowindex));
						 cell.setStyle(style_text_vien);
						 cell.setValue(ngayhoadon_bk);
						 
						 //nhưng dòng cuối cùng 
						 
						 vitri_cot=GetExcelColumnName(dongcuoisp+1);
						 
				  		 cell = cells.getCell(vitri_cot+""+(rowindex));
						 cell.setStyle(style1_number);
						 cell.setValue(thanhtientung_hd);
						 
					 
				  		 
				  		 vitri_cot=GetExcelColumnName(dongcuoisp+2);
				  		 cell = cells.getCell(vitri_cot+""+(rowindex));
						 cell.setStyle(style_text_vien);
						 cell.setValue(thoihangtt_bk);
						 
				  		   
				  		 vitri_cot=GetExcelColumnName(dongcuoisp+3);
				  		 cell = cells.getCell(vitri_cot+""+(rowindex));
						 cell.setStyle(style1_number);
						 cell.setValue(songayquahan_bk);
						 
						 double noquahan=0;
						 if(songayquahan_bk >0){
							 noquahan=thanhtientung_hd;
						 }
				  		 vitri_cot=GetExcelColumnName(dongcuoisp+4);
				  		 cell = cells.getCell(vitri_cot+""+(rowindex));
						 cell.setStyle(style1_number);
						 cell.setValue(noquahan);
						 
						  double notronghan=0;
						 if(songayquahan_bk <=0){
							 notronghan=thanhtientung_hd;
						 }
				  		 vitri_cot=GetExcelColumnName(dongcuoisp+5);
				  		 cell = cells.getCell(vitri_cot+""+(rowindex));
						 cell.setStyle(style1_number);
						 cell.setValue(notronghan);
						 double noquahang_nhohon30=0;
						 double  noquahang_lonhon30=0;
						 double noquahang_noxau90=0;
						 if(songayquahan_bk <= 30 && songayquahan_bk >0){
							 noquahang_nhohon30= thanhtientung_hd;
						 }else if(songayquahan_bk > 90 ){
							 noquahang_noxau90=thanhtientung_hd;
						 }else if(songayquahan_bk <=90 && songayquahan_bk>30){
							 noquahang_lonhon30=thanhtientung_hd;
						 }
						 
				  		 vitri_cot=GetExcelColumnName(dongcuoisp+6);
				  		 cell = cells.getCell(vitri_cot+""+(rowindex));
						 cell.setStyle(style1_number);
						 cell.setValue(noquahang_nhohon30);
					 
						 
				  		 
				  		 vitri_cot=GetExcelColumnName(dongcuoisp+7);
				  		 cell = cells.getCell(vitri_cot+""+(rowindex));
						 cell.setStyle(style1_number);
						 cell.setValue(noquahang_lonhon30);
						  
				  		 
				  		 vitri_cot=GetExcelColumnName(dongcuoisp+8);
				  		 cell = cells.getCell(vitri_cot+""+(rowindex));
						 cell.setStyle(style1_number);
						 cell.setValue(noquahang_noxau90);
						 
						 
						 khid_bk=rs.getString("masosanh"); 
					    tenkh_bk=rs.getString("tenkh"); 
					    songayquahan_bk=rs.getDouble("songayqh");
					    thoihangtt_bk=rs.getString("thoihantt");
					    if(rs.getString("DONDATHANGID").length()>2){
					    	ddh_bk=rs.getString("DONDATHANGID").substring(0, rs.getString("DONDATHANGID").length()-2);
					    }else{
					    	ddh_bk="";
					    }
					    if(rs.getString("NGAYDONHANG").length()>2){
					    ngaydathang_bk=rs.getString("NGAYDONHANG").substring(0, rs.getString("NGAYDONHANG").length()-2);
					    }else{
					    	ngaydathang_bk="";
					    }
					    sohoadon_bk=rs.getString("sohoadon");
					    ngayhoadon_bk=rs.getString("ngay");
					    hoadonid_bk=rs.getString("hoadonid");
						 rowindex++;
						  
						 for(int j=9;j<dongcuoisp  ;j++ ){
								vitri_cot=GetExcelColumnName(j);
								cell = cells.getCell(vitri_cot+""+rowindex);
								cell.setStyle(style1_number); 
								cell.setValue(0);
							 }
						 thanhtientung_hd=0;
				  }
				  
				  if(!khachhangma_bk.equals(rs.getString("masosanh"))){
					  
				     	cell = cells.getCell("C"+(rowindex));
				     	cell.setStyle(style1_chudam);
					    cell.setValue(khachhangma_bk);
					    
					    cell = cells.getCell("D"+(rowindex));
					    cell.setStyle(style1_chudam);
					    cell.setValue(tenkhtotal_bk);
					    
					    cell = cells.getCell("E"+(rowindex));
					    cell.setStyle(style1_chudam);
					    cell.setValue("TOTAL");
					    
					    cell = cells.getCell("F"+(rowindex));
					    cell.setStyle(style1_chudam);
					    cell.setValue(obj.getdenngay());
					    
					    cell = cells.getCell("G"+(rowindex));
					    cell.setStyle(style1_chudam);
					    cell.setValue("");
					    cell = cells.getCell("H"+(rowindex));
					    cell.setStyle(style1_chudam);
					    cell.setValue("");
 
					    cell = cells.getCell("E"+(rowindex));
					    cell.setStyle(style1_chudam);
					    cell.setValue("");
					    
					    khachhangma_bk= rs.getString("masosanh"); 
						tenkhtotal_bk=rs.getString("tenkh");
						// set công thức nợ tổng cho các dòng
						
						
						 
						 for(int j=9;j<dongcuoisp  ;j++ ){
								vitri_cot=GetExcelColumnName(j);
								cell = cells.getCell(vitri_cot+""+rowindex);
								cell.setStyle(style1_number); 
								cell.setValue(0);
								  
							 }
							for(int j=9;j<dongcuoisp+9 ;j++ ){
								vitri_cot=GetExcelColumnName(j);
								cell = cells.getCell(vitri_cot+""+rowindex);
								cell.setStyle(style1_header);
								cell.setFormula("=SUM("+vitri_cot+""+(vitribatdau_sum_kh)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
							 }
							
							
						mang_cac_thutu_tong =mang_cac_thutu_tong+ (mang_cac_thutu_tong.length()>0? ";"+(rowindex):(rowindex)) ;
						
						vitribatdau_sum_kh=rowindex+1;
						
						rowindex++;
						
						 for(int j=9;j<dongcuoisp+1  ;j++ ){
								vitri_cot=GetExcelColumnName(j);
								cell = cells.getCell(vitri_cot+""+rowindex);
								cell.setStyle(style1_number); 
								cell.setValue(0);
								  
							 }
				  }
				  thanhtientung_hd+= rs.getDouble("CONNO");
				  
				  int thutu=htb_sp.get(rs.getString("sanphamid"));
				  vitri_cot=GetExcelColumnName(thutu);
		  		  cell = cells.getCell(vitri_cot+""+(rowindex));
		  		  cell.setStyle(style1_number);
				  cell.setValue(rs.getDouble("SOLUONG"));
				  /*if(rs.getString("hoadonid").equals("168115")){
					  System.out.println("rowindex: "+rowindex+" .thutu : "+thutu+ " . sản pham id: "+rs.getString("sanphamid")+". soluong: " + rs.getDouble("SOLUONG"));
				  }*/
				  
			  
				  count++; 	 				  
			  }
			  
			   // khi ra ngoài thì thực hiện sum
			  
			  
				  // in ra 1 dòng chung : 
				  	 cell = cells.getCell("C"+(rowindex));
				  	 cell.setStyle(style_text_vien);
					 cell.setValue(khid_bk);
					 
					 //
					 cell = cells.getCell("D"+(rowindex));
					 cell.setStyle(style_text_vien);
					 cell.setValue(tenkh_bk);
					 
					 //
					 cell = cells.getCell("E"+(rowindex));
					 cell.setStyle(style_text_vien);
					 cell.setValue(ddh_bk);
					 
					 //
					 cell = cells.getCell("F"+(rowindex));
					 cell.setStyle(style_text_vien);
					 cell.setValue(ngaydathang_bk);
					 
					 //
					 cell = cells.getCell("G"+(rowindex));
					 cell.setStyle(style_text_vien);
					 cell.setValue(sohoadon_bk);
					 
					 //
					 cell = cells.getCell("H"+(rowindex));
					 cell.setStyle(style_text_vien);
					 cell.setValue(ngayhoadon_bk);
					 
					 //nhưng dòng cuối cùng 
					 
					 vitri_cot=GetExcelColumnName(dongcuoisp+1);
					 
			  		 cell = cells.getCell(vitri_cot+""+(rowindex));
					 cell.setStyle(style1_number);
					 cell.setValue(thanhtientung_hd);
					 
				 
			  		 
			  		 vitri_cot=GetExcelColumnName(dongcuoisp+2);
			  		 cell = cells.getCell(vitri_cot+""+(rowindex));
					 cell.setStyle(style_text_vien);
					 cell.setValue(thoihangtt_bk);
					 
			  		   
			  		 vitri_cot=GetExcelColumnName(dongcuoisp+3);
			  		 cell = cells.getCell(vitri_cot+""+(rowindex));
					 cell.setStyle(style1_number);
					 cell.setValue(songayquahan_bk);
					 
					 double noquahan=0;
					 if(songayquahan_bk >0){
						 noquahan=thanhtientung_hd;
					 }
			  		 vitri_cot=GetExcelColumnName(dongcuoisp+4);
			  		 cell = cells.getCell(vitri_cot+""+(rowindex));
					 cell.setStyle(style1_number);
					 cell.setValue(noquahan);
					 
					  double notronghan=0;
					 if(songayquahan_bk <=0){
						 notronghan=thanhtientung_hd;
					 }
			  		 vitri_cot=GetExcelColumnName(dongcuoisp+5);
			  		 cell = cells.getCell(vitri_cot+""+(rowindex));
					 cell.setStyle(style1_number);
					 cell.setValue(notronghan);
					 double noquahang_nhohon30=0;
					 double  noquahang_lonhon30=0;
					 double noquahang_noxau90=0;
					 if(songayquahan_bk <= 30 && songayquahan_bk >0){
						 noquahang_nhohon30= thanhtientung_hd;
					 }else if(songayquahan_bk > 90 ){
						 noquahang_noxau90=thanhtientung_hd;
					 }else if(songayquahan_bk <=90 && songayquahan_bk>30){
						 noquahang_lonhon30=thanhtientung_hd;
					 }
					 
			  		 vitri_cot=GetExcelColumnName(dongcuoisp+6);
			  		 cell = cells.getCell(vitri_cot+""+(rowindex));
					 cell.setStyle(style1_number);
					 cell.setValue(noquahang_nhohon30);
				 
					 
			  		 
			  		 vitri_cot=GetExcelColumnName(dongcuoisp+7);
			  		 cell = cells.getCell(vitri_cot+""+(rowindex));
					 cell.setStyle(style1_number);
					 cell.setValue(noquahang_lonhon30);
					  
			  		 
			  		 vitri_cot=GetExcelColumnName(dongcuoisp+8);
			  		 cell = cells.getCell(vitri_cot+""+(rowindex));
					 cell.setStyle(style1_number);
					 cell.setValue(noquahang_noxau90);
				 
					 rowindex++;
				  
			 
			  
					 // thực hiện in dòng total khách hàng cuối cùng
					 
				  
			     	cell = cells.getCell("C"+(rowindex));
			     	cell.setStyle(style1_chudam);
				    cell.setValue(khachhangma_bk);
				    
				    cell = cells.getCell("D"+(rowindex));
				    cell.setStyle(style1_chudam);
				    cell.setValue(tenkhtotal_bk);
				    
				    cell = cells.getCell("E"+(rowindex));
				    cell.setStyle(style1_chudam);
				    cell.setValue("TOTAL");
				    
				    cell = cells.getCell("E"+(rowindex));
				    cell.setStyle(style1_chudam);
				    cell.setValue(obj.getdenngay());
				    
				    cell = cells.getCell("F"+(rowindex));
				    cell.setStyle(style1_chudam);
				    cell.setValue("");
				    
				    cell = cells.getCell("G"+(rowindex));
				    cell.setStyle(style1_chudam);
				    cell.setValue("");
				    cell = cells.getCell("H"+(rowindex));
				    cell.setStyle(style1_chudam);
				    cell.setValue("");
				    
				     
					// set công thức nợ tổng cho các dòng
					
					
					 
					 for(int j=9;j<dongcuoisp  ;j++ ){
							vitri_cot=GetExcelColumnName(j);
							cell = cells.getCell(vitri_cot+""+rowindex);
							cell.setStyle(style1_number); 
							cell.setValue(0);
							  
						 }
						for(int j=9;j<dongcuoisp+9 ;j++ ){
							vitri_cot=GetExcelColumnName(j);
							cell = cells.getCell(vitri_cot+""+rowindex);
							cell.setStyle(style1_header);
							cell.setFormula("=SUM("+vitri_cot+""+(vitribatdau_sum_kh)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
						 }
					 
						vitribatdau_sum_kh=rowindex;
						
						 
						mang_cac_thutu_tong =mang_cac_thutu_tong+ (mang_cac_thutu_tong.length()>0? ";"+rowindex:rowindex) ;
						
						rowindex++;
					 
						cell = cells.getCell("C"+(rowindex));
				     	cell.setStyle(style1_chudam);
					    cell.setValue("TOTAL ");
					    
					    cell = cells.getCell("D"+(rowindex));
					    cell.setStyle(style1_chudam);
					    cell.setValue("");
					    
					    cell = cells.getCell("E"+(rowindex));
					    cell.setStyle(style1_chudam);
					    cell.setValue("");
					    
					    cell = cells.getCell("E"+(rowindex));
					    cell.setStyle(style1_chudam);
					    cell.setValue("");
					    
					    cell = cells.getCell("F"+(rowindex));
					    cell.setStyle(style1_chudam);
					    cell.setValue("");
					    
					    cell = cells.getCell("G"+(rowindex));
					    cell.setStyle(style1_chudam);
					    cell.setValue("");
					    cell = cells.getCell("H"+(rowindex));
					    cell.setStyle(style1_chudam);
					    cell.setValue("");
					
					// System.out.println(mang_cac_thutu_tong);
					  
					  String[] mangcacvitri= mang_cac_thutu_tong.split(";");
					
					  for(int j=9;j<dongcuoisp +9 ;j++ ){
							vitri_cot=GetExcelColumnName(j);
							cell = cells.getCell(vitri_cot+""+rowindex);
							cell.setStyle(style1_header);
							 String chuoi="";
								for(int k=0;k<mangcacvitri.length;k++){
									  if(k==0){
									    chuoi=vitri_cot+""+mangcacvitri[k];
									  }else{
										  chuoi=chuoi+" + " +vitri_cot+""+mangcacvitri[k];
										  
									  }
						  		}
								if( j!= dongcuoisp+ 2) {
									cell.setFormula("= "+chuoi);
								}
					  		 
						 }
					  
					  
					  
					  
			  rs.close();
			 
			 //total tổng cuối cùng
			   
	 
		}catch(Exception err){
			err.printStackTrace();
		}
	}

}

