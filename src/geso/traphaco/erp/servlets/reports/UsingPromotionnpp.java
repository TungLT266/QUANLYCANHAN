package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;

public class UsingPromotionnpp extends HttpServlet {
	private static final long serialVersionUID = 1L;	
  
    public UsingPromotionnpp() {
        super();
     
    }

   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util=new Utility();
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();	  
		
		String userTen = (String)session.getAttribute("userTen");
		obj.setuserTen(userTen==null? "":userTen);
		
		String querystring=request.getQueryString();
		String userId=	util.getUserId(querystring);
		obj.setuserId(userId==null? "":userId);
		
		obj.init();
		session.setAttribute("obj", obj);	
		session.setAttribute("userTen", userTen);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/UsingPromotionAllocationReportDis.jsp";
		response.sendRedirect(nextJSP);		
 	}

 	
 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 		
 		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();	 		
 		IStockintransit obj = new Stockintransit();	
 		OutputStream out = response.getOutputStream();
		try
		    {
				String userId = (String) session.getAttribute("userId");
				String userTen = (String) session.getAttribute("userTen");			
				
				obj.setuserId(userId==null? "":userId);
				obj.setuserTen(userTen==null? "":userTen);
				obj.settungay(request.getParameter("Sdays")==null? "":request.getParameter("Sdays"));			
				obj.setdenngay(request.getParameter("Edays")==null? "":request.getParameter("Edays"));
				
				
				obj.setkenhId(request.getParameter("kenhId")==null? "":request.getParameter("kenhId"));
				obj.setdvkdId(request.getParameter("dvkdId")==null? "":request.getParameter("dvkdId"));
				obj.setnhanhangId(request.getParameter("nhanhangId")==null? "":request.getParameter("nhanhangId"));
				obj.setchungloaiId(request.getParameter("chungloaiId")==null? "":request.getParameter("chungloaiId"));
				obj.setUnit(request.getParameter("donviTinh")==null? "":request.getParameter("donviTinh"));
				obj.setPrograms(request.getParameter("programs")==null? "":request.getParameter("programs"));
				obj.setFieldShow(request.getParameterValues("fieldsHien")!=null? request.getParameterValues("fieldsHien"):null);
				
				
				Utility Ult = new Utility();
				obj.setnppId(Ult.getIdNhapp(userId)) ;
				obj.setnppTen(Ult.getTenNhaPP());
				
				String condition = "";
				if(obj.getkenhId().length()>0)
					condition +=" and k.pk_seq ='" + obj.getkenhId() +"'";
				if(obj.getPrograms().length()>0)
					condition +=" and km.scheme = '" + obj.getPrograms() +"'";
				String action = request.getParameter("action");				
				if (action.equals("create")) {
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition",
							"attachment; filename=SuDungPhanBoKhuyenMai(NPP).xlsm");
					CreatePivotTable(out,obj,condition);
				}		        
		     }
		    catch (Exception ex)
		    {
		       obj.setMsg(ex.getMessage());
		    }
		    obj.init();
			session.setAttribute("obj", obj);	
			session.setAttribute("userTen", obj.getuserTen());
			String nextJSP = "/TraphacoHYERP/pages/Distributor/UsingPromotionAllocationReportDis.jsp";
			response.sendRedirect(nextJSP);		
 	}

 	private void CreatePivotTable(OutputStream out,IStockintransit obj, String condition) throws Exception
    {       
 		FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\SuDungPhanBoKhuyenMai.xlsm");
// 		FileInputStream fstream = new FileInputStream("D:\\DMS\\SalesUp\\WebContent\\pages\\Templates\\SuDungPhanBoKhuyenMai.xlsm");
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook,obj);	     
	    CreateStaticData(workbook, obj, condition);
	    workbook.save(out);
	    fstream.close();
   }
 	
 	private void CreateStaticHeader(Workbook workbook, IStockintransit obj)throws Exception 
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
	    
	    cell.setValue("BÁO CÁO SỬ DỤNG PHÂN BỔ KHUYẾN MÃI");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	
	    
	    cells.setRowHeight(2, 18.0f);
	    cell = cells.getCell("A3"); 
	    getCellStyle(workbook,"A3",Color.NAVY,true,10);	    
	    cell.setValue("Từ ngày: " + obj.gettungay());
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); getCellStyle(workbook,"B3",Color.NAVY,true,9);	        
	    cell.setValue("Đến ngày: " + obj.getdenngay());    
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + this.getDate());
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
	    cell.setValue("Được tạo bởi Nhà phân phối:  " + obj.getuserTen());
			  
		cell = cells.getCell("AA1"); cell.setValue("Kenh");
		cell = cells.getCell("AB1"); cell.setValue("Ma Chuong Trinh");
 	    cell = cells.getCell("AC1"); cell.setValue("Ten Chuong Trinh");  	    
	    cell = cells.getCell("AD1"); cell.setValue("Tu Ngay");
	    cell = cells.getCell("AE1"); cell.setValue("Den Ngay");
	    cell = cells.getCell("AF1"); cell.setValue("Ngan Sach Phan Bo");
	    cell = cells.getCell("AG1"); cell.setValue("Đa Su Dung");
	    cell = cells.getCell("AH1"); cell.setValue("Ngan Sach Con Lai");
	    cell = cells.getCell("AI1"); cell.setValue("Suat Con Lai");
	    cell = cells.getCell("AJ1"); cell.setValue("%Su Dung");
	    cell = cells.getCell("AK1"); cell.setValue("Hinh Thuc");
 	}
 	
 	private void CreateStaticData(Workbook workbook, IStockintransit obj, String condition) throws Exception
 	{
 		Worksheets worksheets = workbook.getWorksheets();
 	    Worksheet worksheet = worksheets.getSheet(0);
 	    Cells cells = worksheet.getCells();
 	    dbutils db = new dbutils();	  
		String sql  ="select distinct k.ten as Kenh,km.scheme as MaCT,km.scheme, v.ten as Region,kv.ten as Area," +
					 "ctnpp.npp_fk as Distributor_code,npp.pk_seq as Distributor_code,npp.ten as Distributor,km.scheme as Programs_code," +
					 "km.diengiai as Programs_Name,km.tungay as From_Date,km.denngay as To_Date,"+
					 "case when pbkm.ngansach >0 and pbkm.ngansach < 1000000000 then pbkm.ngansach else '-1' end as sophanbo," +
					 "pbkm.dasudung as dasudung, " +
					 "case when pbkm.ngansach >0 and pbkm.ngansach < 1000000000 " +
					 "then pbkm.ngansach - pbkm.dasudung else 0 end as conlai," +

					 "case when pbkm.ngansach >0 and pbkm.ngansach < 1000000000 " +
					 "then convert(float,(pbkm.ngansach - pbkm.dasudung))/bgmuanpp_sp.giamuanpp  else 0 end as suatconlai," +
					 "case when pbkm.ngansach >0 and pbkm.ngansach < 1000000000 then cast(100*(cast(pbkm.dasudung as float) /abs(pbkm.ngansach)) as float) " +
					 "else 0 end as phantramsudung, "+
					 "km.kho_fk as khoKM " +
					 " from ctkhuyenmai km"+
					 " inner join ctkm_npp ctnpp on ctnpp.ctkm_fk = km.pk_seq"+
					 " inner join phanbokhuyenmai pbkm on pbkm.ctkm_fk = km.pk_seq and ctnpp.npp_fk = pbkm.npp_fk"+
					 " inner join nhaphanphoi npp on npp.pk_seq = ctnpp.npp_fk"+
					 " inner join nhapp_kbh nppK on nppK.npp_fk = npp.pk_seq"+		
					 " inner join kenhbanhang k on k.pk_seq = nppK.kbh_fk"+
					 " left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk"+
					 " left join vung v on v.pk_seq = kv.vung_fk"+
					 " inner join DONHANG_CTKM_TRAKM tr on tr.ctkmid = km.pk_seq "+
					 " inner join donhang dh on dh.pk_seq = tr.donhangid and dh.npp_fk = pbkm.npp_fk "+
					 " inner join banggiamuanpp_npp bgmuanpp on bgmuanpp.npp_fk=ctnpp.npp_fk " +
					 " inner join bgmuanpp_sanpham bgmuanpp_sp on bgmuanpp_sp.BGMUANPP_FK = bgmuanpp.BANGGIAMUANPP_FK " +
					 " inner join ctkm_trakm ctkm_trakm on ctkm_trakm.ctkhuyenmai_fk=km.pk_seq " +
					 " inner join trakhuyenmai_sanpham trakm on trakm.trakhuyenmai_fk = ctkm_trakm.TRAKHUYENMAI_FK and trakm.sanpham_fk=bgmuanpp_sp.sanpham_fk " +
					 " where ctnpp.npp_fk = '"+ obj.getnppId() +"' " +
	   				 "and dh.ngaynhap >='"+obj.gettungay()+"'" +
	   				 "and dh.ngaynhap <= '"+obj.getdenngay()+"'" + condition;
 	 
		System.out.println(sql);
 	   	ResultSet rs = db.get(sql); 	   
 	    int i = 2;
 		if(rs!=null)
 		{
 			try 
 			{
 				cells.setColumnWidth(0, 19.0f);
 				cells.setColumnWidth(1, 50.0f);
 				cells.setColumnWidth(2, 12.0f);
 				cells.setColumnWidth(3, 12.0f);
 				cells.setColumnWidth(4, 20.0f);
 				cells.setColumnWidth(5, 20.0f);
 				cells.setColumnWidth(6, 20.0f);
 				cells.setColumnWidth(7, 20.0f);
 				cells.setColumnWidth(8, 20.0f);
 				cells.setColumnWidth(9, 20.0f);
 				cells.setColumnWidth(10, 20.0f);
 				
 				Cell cell = null;
 				Style style;
 				String khoKM="";
 				while(rs.next())
 				{  					
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(rs.getString("Kenh"));
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(rs.getString("MaCT"));
 					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(rs.getString("Programs_Name"));					
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(rs.getString("From_Date"));
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(rs.getString("To_Date"));

					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(rs.getFloat("sophanbo"));
					style = cell.getStyle();
					style.setNumber(2);
					cell.setStyle(style);

					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(rs.getFloat("dasudung"));
					style = cell.getStyle();
					style.setNumber(2);
					cell.setStyle(style);

					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(rs.getFloat("conlai"));
					style = cell.getStyle();
					style.setNumber(2);
					cell.setStyle(style);
					
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(rs.getFloat("suatconlai"));
					style = cell.getStyle();
					style.setNumber(2);
					cell.setStyle(style);

					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(rs.getFloat("phantramsudung"));
					style = cell.getStyle();
					style.setNumber(2);
					cell.setStyle(style);

					khoKM = rs.getString("khoKM");
					if(khoKM.equals("100000")){
						cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue("NPP ứng hàng");
					}else{
						if (khoKM.equals("100001")){
							cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue("ICP ứng hàng");
						}else{
							cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue("Không xác định");
						}
					}
					i++;
 				}

				ReportAPI.setHidden(workbook, obj.getFieldShow().length+1);
				PivotTables pivotTables = worksheet.getPivotTables();
				String pos = Integer.toString(i-1);				
				
			    int j = pivotTables.add("=AA1:AK" + pos,"A8","SuDungPhanBoKM");	   
			    
			    PivotTable pivotTable = pivotTables.get(j);
			    pivotTable.setRowGrand(false);
			    pivotTable.setColumnGrand(false);
			    pivotTable.setAutoFormat(true); 
			    pivotTable.setAutoFormatType(PivotTableAutoFormatType.TABLE10);
			    
			    Hashtable<String,Integer> selected = new Hashtable<String, Integer>();
			    selected.put("Kenh", 0);
			    selected.put("MaChuongTrinh", 1);
			    selected.put("TenChuongTrinh", 2);
			    selected.put("TuNgay", 3);
			    selected.put("DenNgay", 4);
			    selected.put("SoPhanBo", 5);		   
			    selected.put("DaSuDung", 6);
			    selected.put("ConLai", 7);	
			    selected.put("SuatConLai", 8);
			    selected.put("%SuDung", 9);		   
			    selected.put("HinhThuc", 10);
			    int dataIndex = 0;			   
			    
			    for(i=0;i<obj.getFieldShow().length;++i){
			    	int value = selected.get(obj.getFieldShow()[i]);
			    	if((value==5)||(value==6)||(value==7)||(value==8)||(value==9)){
			    		pivotTable.addFieldToArea(PivotFieldType.DATA, value);
			    		pivotTable.getDataFields().get(dataIndex).setDisplayName(obj.getFieldShow()[i]);
			    		++dataIndex;
			    	}else{
			    		pivotTable.addFieldToArea(PivotFieldType.ROW, value);			    		
			    	}			    	
			    	
			    }
	    		pivotTable.getRowFields().get(0).setAutoSubtotals(false);
	    		pivotTable.getRowFields().get(1).setAutoSubtotals(false);
	    		pivotTable.getRowFields().get(2).setAutoSubtotals(false);
	    		pivotTable.getRowFields().get(3).setAutoSubtotals(false);

			    pivotTable.getDataFields().get(0).setNumber(2);
			    pivotTable.getDataFields().get(1).setNumber(2);
			    pivotTable.getDataFields().get(2).setNumber(2);
			    pivotTable.getDataFields().get(3).setNumber(3);
			    pivotTable.getDataFields().get(4).setNumber(2);
			    if(dataIndex>1){
			    	 pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
			    	 pivotTable.getColumnFields().get(0).setDisplayName("Data");		
			    }				
			    
			    setAn(workbook, 50);
 		}catch (Exception e){ 		
 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay");
 		}
 		finally{
 			if(rs != null)
 			rs.close();
 		}
 		}else{
 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay");
 		}
		 
 	}

 	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a); 
		style = cell.getStyle();
	    Font font1 = new Font();
	    font1.setColor(mau);
	    font1.setBold(dam);
	    font1.setSize(size);
	    style.setFont(font1);
	    
		//Setting the horizontal alignment of the text in the cell 
	    style.setHAlignment(TextAlignmentType.LEFT);
	    cell.setStyle(style);
	}

	private String getDate() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy: hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	private void setAn(Workbook workbook,int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 26; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}

}
	
