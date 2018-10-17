package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

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
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BaoCaoTongHopTTSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public BaoCaoTongHopTTSvl() {
        super();
    }
    
    String query = "";
	int length = 0;
	String userId = "";
	
	Workbook workbook = null;
	Worksheets worksheets = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		
		obj.setdiscount("1");
		obj.setvat("1");
		obj.init();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoTongHopTT.jsp";
		response.sendRedirect(nextJSP);
	}

	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		OutputStream out = response.getOutputStream();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		
		obj.settungay(util.antiSQLInspection(request.getParameter("Sdays")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("Edays")));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))!=null?
				util.antiSQLInspection(request.getParameter("kenhId")):"");
		
		obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))!=null?
				util.antiSQLInspection(request.getParameter("vungId")):"");
			
		obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))!=null?
				util.antiSQLInspection(request.getParameter("khuvucId")):"");
		
		obj.setgsbhId(util.antiSQLInspection(request.getParameter("gsbhs"))!=null?
				util.antiSQLInspection(request.getParameter("gsbhs")):"");
		
		obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))!=null?
				util.antiSQLInspection(request.getParameter("nppId")):"");
		
		obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))!=null?
				util.antiSQLInspection(request.getParameter("dvkdId")):"");
		
		obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nhanhangId"))!=null?
			util.antiSQLInspection(request.getParameter("nhanhangId")):"");
		obj.setchungloaiId(util.antiSQLInspection(request.getParameter("chungloaiId"))!=null?
				util.antiSQLInspection(request.getParameter("chungloaiId")):"");
		
		String vat = util.antiSQLInspection(request.getParameter("vats"));
		if (vat.equals("1"))
			obj.setvat("1.1");
		else
			obj.setvat("1");
		String dsc = util.antiSQLInspection(request.getParameter("discount"));
		if (dsc.equals("1"))
			obj.setdiscount("1");
		else
			obj.setdiscount("0");	
		String promotion = request.getParameter("promotion");
		if(promotion == null)
			promotion = "0";
		obj.setpromotion(promotion);
		
		String xemtheo = request.getParameter("xemtheo");
		System.out.println("Xem theo: " + xemtheo + "\n");
		if(xemtheo.equals("1")) //xem theo thang
		{
			String tuthang = request.getParameter("tuthang");
			if(tuthang == null)
				tuthang = "";
			obj.setFromMonth(tuthang);
			System.out.println("Tu thang: " + tuthang);
			
			String denthang = request.getParameter("denthang");
			if(denthang == null)
				denthang = "";
			obj.setToMonth(denthang);
			System.out.println("Den thang: " + denthang);
		}
		
		//String[] fieldsHien = request.getParameterValues("fieldsHien");
		//obj.setFieldShow(fieldsHien);
		
		geso.traphaco.center.util.Utility utilcenter = new geso.traphaco.center.util.Utility();
		
		String level = util.antiSQLInspection(request.getParameter("level"));
		
		String sql = " where npp.pk_seq in " + utilcenter.quyen_npp(obj.getuserId()) + " and kbh.pk_seq in  " + utilcenter.quyen_kenh(obj.getuserId());
		if (obj.getkenhId().length() > 0)
			sql = sql + " and kbh.pk_seq ='" + obj.getkenhId() + "'";
		if (obj.getvungId().length() > 0)
			sql = sql + " and v.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and kv.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and dvkd.PK_SEQ ='" + obj.getdvkdId() + "'";
		if (obj.getnppId().length() > 0)
			sql = sql + " and npp.pk_seq ='" + obj.getnppId() + "'";
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and kbh.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and cl.pk_seq ='" + obj.getchungloaiId()	+ "'";
		
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoTongHopTT.jsp";
		
		session.setAttribute("level", level);
		
		String gia ="nhsp.GIAGROSS";
		if(Integer.parseInt(dsc) >0) 
			gia ="nhsp.gianet";
		
		if(action.equals("Taomoi"))
		{
			try
			{   
				response.setCharacterEncoding("utf-8");			
				response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=BCTongHop.xls");
		        
		        workbook = new Workbook();
		        worksheets = workbook.getWorksheets();
		        
		        CreatePivotKhoaSo(out, obj);
		        
		        //setQuery(request,sql,vat,gia);
		        
		        //CreatePivotTable(out, response, request,obj);	// Create PivotTable 
		        
		        worksheets.setActiveSheet(0);
		        workbook.save(out);
			}
			catch(Exception ex)
			{
				System.out.println("Xay ra exception roi..." + ex.toString());
				obj.setMsg(ex.getMessage());
				response.sendRedirect(nextJSP);
			}
		}
		obj.init();
		session.setAttribute("obj", obj);
	}
	
	
	//Khoa so ngay
	private boolean CreatePivotKhoaSo(OutputStream out, IStockintransit obj)throws Exception
	{
		String sql = "";
		if(obj.getvungId().length()>0){
			sql += " and v.pk_seq = '" + obj.getvungId() + "'";
		}
		if(obj.getkhuvucId().length()>0){
			sql += " and kv.pk_seq='"+ obj.getkhuvucId() + "'";
		}
		if(obj.getnppId().length()>0){
			sql +=" and n.pk_seq='" + obj.getnppId() +"'";
		}
		
		String query = "SELECT  v.TEN AS Region,kv.TEN AS Area,n.PK_SEQ AS DistributorCode," +
				"		n.SITECODE AS SiteCode,	n.TEN AS Distributor,k.NGAYKS AS Date," +
				"		CASE WHEN n.PK_SEQ NOT IN (SELECT ksn.NPP_FK FROM KHOASONGAY ksn) THEN 0 " +
				"		ELSE 1" +
				"		END AS Data " +
				"    FROM KHOASONGAY k" +
				"		INNER JOIN NHAPHANPHOI n ON n.PK_SEQ = k.NPP_FK" +
				"		INNER JOIN KHUVUC kv ON kv.PK_SEQ = n.KHUVUC_FK" +
				"		INNER JOIN VUNG v ON v.PK_SEQ = kv.VUNG_FK" +
				"	WHERE k.NGAYKS >= '" + obj.gettungay() + "' AND k.NGAYKS <='" + obj.getdenngay() + "' " + sql;
		
		CreateHeaderKS(worksheets, obj);
		FillDataKSNgay(worksheets, obj, query);
		
		worksheets.addSheet("DoanhSoMuaHang");
		return true;	
	}

	private void CreateHeaderKS(Worksheets worksheets, IStockintransit obj)throws Exception 
	{
		try
		{
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("KhoaSoNgay");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16, 
					"THEO DÕI KHÓA SỔ NGÀY ");
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
					"Từ ngày : " + obj.gettungay() + " Đến ngày: " + obj.getdenngay());
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày báo cáo: "
					+ obj.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
					"Được tạo bởi: " + obj.getuserTen());			
			
			cell = cells.getCell("EA1");		cell.setValue("Region");
			cell = cells.getCell("EB1");		cell.setValue("Area");			
			cell = cells.getCell("EC1");		cell.setValue("Distributor Code");
			cell = cells.getCell("ED1");		cell.setValue("SiteCode");	
			cell = cells.getCell("EE1");		cell.setValue("Distributor");
			cell = cells.getCell("EF1");		cell.setValue("Date");		
			cell = cells.getCell("EG1");		cell.setValue("Data");			
		}
		catch(Exception ex)
		{
			throw new Exception("Khong tao duoc Header cho bao cao khoa so ngay");
		}
		//worksheets.addSheet("DoanhSo");
	}
	
	private boolean FillDataKSNgay(Worksheets worksheets, IStockintransit obj, String query) throws Exception 
	{
		dbutils db = new dbutils();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();		
		ResultSet rs = db.get(query);
		int i = 2;
		if(rs!=null){
			Cell cell = null;
			try{
				while(rs.next())
				{					
					String region = rs.getString("Region");
					String area = rs.getString("Area");
					String distributorCode = rs.getString("DistributorCode");
					String siteCode = rs.getString("SiteCode");
					String distributor = rs.getString("Distributor");
					String date = rs.getString("Date");
					String data = rs.getString("Data");
					
					
					cell = cells.getCell("EA" + Integer.toString(i));	cell.setValue(region);
					cell = cells.getCell("EB" + Integer.toString(i));	cell.setValue(area);
					cell = cells.getCell("EC" + Integer.toString(i));	cell.setValue(distributorCode);
					cell = cells.getCell("ED" + Integer.toString(i));	cell.setValue(siteCode);
					cell = cells.getCell("EE" + Integer.toString(i));	cell.setValue(distributor);
					cell = cells.getCell("EF" + Integer.toString(i));	cell.setValue(date);										
					cell = cells.getCell("EG" + Integer.toString(i));	cell.setValue(data);
					++i;					
					if(i > 65000)
					{
						if (rs != null) rs.close();							
						if(db != null) db.shutDown();
						throw new Exception("Du lieu vuot qua gioi han file Excel. Vui long chon dieu kien can lay bao cao");							
					}
				}
				if (rs != null)
					rs.close();
				
				if(db != null) db.shutDown();
				
				if(i == 2){					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
				
				//setHidden(workbook, 170);
				PivotTables pivotTables = worksheet.getPivotTables();
				String pos = Integer.toString(i - 1);
				int index = pivotTables.add("=KhoaSoNgay!EA1:EG" + pos, "A12","PivotTable");
				PivotTable pivotTable = pivotTables.get(index);
				
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	
				pivotTable.getRowFields().get(0).setAutoSubtotals(false);
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
				pivotTable.getRowFields().get(1).setAutoSubtotals(false);
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 2);
				pivotTable.getRowFields().get(2).setAutoSubtotals(false);
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 3);
				pivotTable.getRowFields().get(3).setAutoSubtotals(false);
				
				pivotTable.addFieldToArea(PivotFieldType.ROW, 4);
				pivotTable.getRowFields().get(4).setAutoSubtotals(false);
				
				pivotTable.addFieldToArea(PivotFieldType.DATA, 6);
				pivotTable.getDataFields().get(0).setDisplayName("KSN");
				
				pivotTable.addFieldToArea(PivotFieldType.COLUMN, 5);
				pivotTable.getColumnFields().get(0).setDisplayName("Date");
				
								
				  
			}catch(Exception ex){
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}
		return true;
	}

	//Khoa so ngay
	private boolean CreatePivotMuaHangHangNgay(OutputStream out, IStockintransit obj)throws Exception
	{
		 String sql ="";
		 if(obj.gettungay().length() > 0) 
			 sql = sql +" and nh.ngaychungtu >='"+ obj.gettungay() + "'";
		 if(obj.getdenngay().length() > 0) 
			 sql = sql +" and  nh.ngaychungtu <='"+ obj.getdenngay() + "'";
		 if(obj.getkenhId().length() > 0) 
			 sql = sql +" and kbh.pk_seq ='"+ obj.getkenhId() + "'";
		 if(obj.getvungId().length() > 0) 
			 sql = sql +" and v.pk_seq ='" + obj.getvungId() + "'";
		 if(obj.getkhuvucId().length() > 0)
			 sql = sql + " and kv.pk_seq ='"+ obj.getkhuvucId() + "'";
		 if(obj.getdvdlId().length() > 0) 
			 sql = sql + " and sp.dvkd_fk ='"+ obj.getdvdlId() + "'";
		 if(obj.getnppId().length() > 0)
			 sql =sql +" and npp.pk_seq ='"+ obj.getnppId() + "'";
		 if(obj.getnhanhangId().length() > 0) 
			 sql = sql +" and nhan.pk_seq ='"+ obj.getnhanhangId() + "'";
		 if(obj.getchungloaiId().length() > 0)
			 sql = sql +" and cl.pk_seq ='"+ obj.getchungloaiId() + "'";
		 if(obj.getchungloaiId().length() > 0) 
			 sql = sql + " and sp.dvdl_fk ='"+ obj.getchungloaiId() +"'";
		 
		 if(Integer.parseInt(obj.getpromotion()) == 0) // khng lay khuyen mai + trung bay
		 {
			 sql = sql + " and nhsp.gianet >0  ";
		 }
		 String gia ="nhsp.GIAGROSS";
		 if(Integer.parseInt(obj.getdiscount()) > 0)
			 gia ="nhsp.gianet";
		
		String query = "select " +
				"kbh.ten as Channel,v.ten as Region,kv.ten as Area,nhan.ten as Brands,cl.ten as Category,nh.ngaychungtu as Shipdate," +
				"npp.sitecode as Distributor_code ,npp.ten as Distributor,tt.ten as Province,nh.chungtu as Docno,nh.pk_seq as Distcode," +
				"nhsp.sanpham_fk as SKU_code,sp.ten as SKU,nhsp.soluong as Peice , nhsp.soluong * "+ gia + "*" + obj.getvat() +" as Amount " +
			" from nhaphang nh " +
				"inner join nhaphang_sp nhsp on nh.pk_seq = nhsp.nhaphang_fk " +
				"inner join sanpham sp on sp.ma = nhsp.sanpham_fk " +
				"inner join nhaphanphoi npp on npp.pk_seq = nh.npp_fk " +
				"left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk " +
				"left join vung v on v.pk_seq = kv.vung_fk " +
				"left join nhanhang nhan on nhan.pk_seq = sp.nhanhang_fk " +
				"left join chungloai cl on cl.pk_seq = sp.chungloai_fk " +
				"left join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk " +
				"left join kenhbanhang kbh on kbh.pk_seq =nh.kbh_fk where nh.trangthai <> '2' " ;

			geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
			query += " and npp.pk_seq in "+ ut.quyen_npp(userId) + " and kbh.pk_seq in " + ut.quyen_kenh(userId)
								+" and sp.pk_seq in "+ ut.quyen_sanpham(userId);

			if(query.length() > 0)
				query = query + sql;
				
		CreateHeaderMuaHangHangNgay(worksheets, obj);
		FillDataMuahanghangngay(worksheets, obj, query);
		
		worksheets.addSheet("DoanhSoMuaHang");
		return true;	
	}
	
	//Bao cao mua hang hang ngay
	private void CreateHeaderMuaHangHangNgay(Worksheets worksheets, IStockintransit obj) 
	{
	    Worksheet worksheet = worksheets.getSheet("DoanhSoMuaHang"); 
	    worksheet.setName("DoanhSoMuaHang");
	    
	    Cells cells = worksheet.getCells();	    
	    cells.setRowHeight(0, 50.0f);	    
	    Cell cell = cells.getCell("A1");	
	    ReportAPI.getCellStyle(cell,Color.RED, true, 16, "Daily Primary Sales");
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Từ ngày: " + obj.gettungay()+ " Đến ngày : " + obj.getdenngay());
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Ngày báo cáo: " +obj.getDateTime());	    
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.BLUE,true,10,"Được tạo bởi: " + obj.getuserTen());
	    cell = cells.getCell("AA1");		cell.setValue("Channel");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1");		cell.setValue("Region");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("Brands");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("Category");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");		cell.setValue("Pridate");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("Province");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1");		cell.setValue("Area");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("Distributor");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("Distributor code");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");		cell.setValue("SKU");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");		cell.setValue("SKU Code");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1");		cell.setValue("Billing  number");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");		cell.setValue("Piece");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1");		cell.setValue("Amount");				ReportAPI.setCellHeader(cell);
	}
	
	private void FillDataMuahanghangngay(Worksheets worksheets, IStockintransit obj, String query) throws Exception 
	{
		Worksheet worksheet = worksheets.getSheet("DoanhSoMuaHang");
		Cells cells = worksheet.getCells();
		
		for(int i=0;i < 14; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    }	
		
		dbutils db = new dbutils();

		ResultSet rs = db.get(query);
		int index = 2;
	    Cell cell = null;	    
	    while (rs.next()) {
			cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("Channel"));	//Kenh ban hang  0	
			cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("Region"));	//Vung/Mien  	1
			cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("Brands"));	//Nhan hang   	2
			cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("Category"));	//Chung loai 3
			cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("Shipdate"));	//So Tien    4
			cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("Province"));	//Tinh thanh 5
			cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("Area"));		//khu vuc    6
		//	cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue("");							//Giam sat ban hang 
			cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("Distributor"));	//Ten NPP   7
			cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("Distributor_code"));	//Ma NPP 8
			cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(rs.getString("SKU"));				//Ten san pham 9
			cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(rs.getString("SKU_code"));	//Ma san pham   10
		//	cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue("");						//Nhom san pham			11	
			cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue(rs.getString("Docno"));	//So luong quy le san pham 12
			cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("Peice")));	//Ngay xuat hoa don tu cong ty
			cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("Amount")));
			index++;
		}
	    if(rs!=null)
	    	rs.close();
	    ReportAPI.setHidden(workbook,13);
		PivotTables pivotTables = worksheet.getPivotTables();
		String pos = Integer.toString(index-1);	
	    int j = pivotTables.add("=DailyPrimarySales!AA1:AN" + pos,"B12","PivotTable");	   
	    
	    PivotTable pivotTable = pivotTables.get(j);
	    Hashtable<String, Integer> selected = new Hashtable<String, Integer>();
	    selected.put("Channel",0);
	    selected.put("Region",1);
	    selected.put("Brands",2);
	    selected.put("Category",3);
	    selected.put("Pridate",4);
	   
	    selected.put("Province",5);
	    selected.put("Area",6);
	    selected.put("Distributor",7);
	    selected.put("Distributor_code",8);
	    selected.put("SKU",9);
	    selected.put("SKU_Code",10);
	    selected.put("Billing_number",11);
	    selected.put("Amount",12);
	    selected.put("Piece",13);
	  
	    
	    pivotTable.setRowGrand(true);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);
	    String[] manghien = obj.getFieldShow();
	    int dem =0;
		    for(int i=0; i < manghien.length ; i++){
	    	int so = selected.get(manghien[i]);
	    	System.out.println("so "+ so +" mang" + manghien[i]);
	    	if(so == 12 || so == 13)
	    	{   dem++;
	    		pivotTable.addFieldToArea(PivotFieldType.DATA, so);	 
	    	}
	    	else
	    	pivotTable.addFieldToArea(PivotFieldType.ROW, so); 		
   	     //	pivotTable.getRowFields().get(so).setAutoSubtotals(false);
	    }
		    if(dem >1)  
		    	pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
	}
	
}
