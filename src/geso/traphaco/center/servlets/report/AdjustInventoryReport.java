package geso.traphaco.center.servlets.report;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;


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

public class AdjustInventoryReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String query = "";
	int length = 0;
	String userId = "";
	
	public void setQuery(HttpServletRequest request, String st)
	{
		geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
		query  = "SELECT " +
        		"	KBH.PK_SEQ AS KBHID,DCTK.LYDODC AS LYDODC, KBH.TEN AS KENH,VUNG.PK_SEQ AS VUNGID, VUNG.TEN AS VUNG,KV.PK_SEQ AS KVID," +
        		"	KV.TEN AS KHUVUC,ISNULL(QH.PK_SEQ,0) AS QHID, ISNULL(QH.TEN,'') AS QUANHUYEN, NPP.MA AS NPPID," +
        		"	DVKD.DONVIKINHDOANH, NH.TEN AS NHANTEN, CL.TEN AS CLTEN, " +
        		"	NPP.TEN AS NPP, DCTK.NGAYDC AS NGAY, SP.MA, SP.TEN, kho.ten as khoTen, SUM(CONVERT(NUMERIC(18,0),DCTK_SP.TONHIENTAI)) AS TONDAU," +
        		"	SUM(CONVERT(NUMERIC(18,0),DCTK_SP.DIEUCHINH)) AS DIEUCHINH,SUM(CONVERT(NUMERIC(18,0),DCTK_SP.TONMOI)) AS TONCUOI " +
        		" ,qc.soluong1 as qc1,qc.soluong2 as qc2,DCTK_SP.SOLO "+
        		"FROM DIEUCHINHTONKHO DCTK	" +
        		"	INNER JOIN DIEUCHINHTONKHO_SP DCTK_SP ON DCTK_SP.DIEUCHINHTONKHO_FK = DCTK.PK_SEQ" +
        		
        		" 	INNER JOIN kho on kho.pk_seq = DCTK.kho_fk "+
        		"	INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = DCTK.KBH_FK" +
        		"	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DCTK.NPP_FK" +
        		"	LEFT JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK" +
        		"	LEFT JOIN VUNG VUNG ON VUNG.PK_SEQ = KV.VUNG_FK" +
        		"	INNER JOIN SANPHAM SP ON SP.PK_SEQ = DCTK_SP.SANPHAM_FK" +
        		
					" left join quycach qc on DCTK_SP.sanpham_fk = qc.sanpham_fk and qc.dvdl2_fk=100018 AND QC.DVDL1_fK=SP.DVDL_FK "+
        		
        		" 	LEFT JOIN NHANHANG NH ON NH.PK_SEQ = SP.NHANHANG_FK " +
    			" 	LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ = SP.CHUNGLOAI_FK " +
    			" 	INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK " +
        		"	LEFT  JOIN QUANHUYEN QH on QH.PK_SEQ = NPP.QUANHUYEN_FK " +
        		" 	LEFT JOIN NHANHANG NHAN ON NHAN.PK_SEQ = SP.NHANHANG_FK " +
        		"	LEFT JOIN CHUNGLOAI CHUNGLOAI ON CHUNGLOAI.PK_SEQ = SP.CHUNGLOAI_FK "+
        		" WHERE DCTK.TRANGTHAI = '1' "+ st + " and npp.pk_seq in "+ ut.quyen_npp(userId) + " and kbh.pk_seq in " + ut.quyen_kenh(userId) +" "+
        		" GROUP BY qc.soluong1 ,qc.soluong2,KBH.PK_SEQ, KBH.TEN, VUNG.PK_SEQ, VUNG.TEN, KV.PK_SEQ, KV.TEN, DCTK.LYDODC,QH.PK_SEQ, QH.TEN, DVKD.DONVIKINHDOANH, NH.TEN, CL.TEN, NPP.MA, NPP.TEN, SP.MA, SP.TEN, KHO.TEN, DCTK.NGAYDC,DCTK_SP.SOLO";
		//phanquyen
		
		
	 
	 System.out.println("Dieu Chinh Ton Kho: " + query);
	}
	public String getQuery(){
		return query;
	}
       
    
    public AdjustInventoryReport() {
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
	    userId = util.getUserId(querystring);
	    obj.setuserId(userId);
	    obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/AdjustInventoryReport.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();
	 
	    String userId= request.getParameter("userId");
	    if(userId ==null)
	    	userId ="";
	    obj.setuserId(userId);
	    String userTen = (String) session.getAttribute("userTen"); 
        obj.setuserTen(userTen);
   	 	String nppId=request.getParameter("nppId");
   	 	if(nppId ==null) 
   	 		nppId ="";
   	 	obj.setnppId(nppId);
   	   	 
	   	 String kenhId=request.getParameter("kenhId");
	   	 if(kenhId == null)
	   		 kenhId ="";
	   	 obj.setkenhId(kenhId);
	   	 
	   	 String dvkdId=request.getParameter("dvkdId");
	   	 if(dvkdId == null)
	   		 dvkdId ="";
	   	 obj.setdvkdId(dvkdId);
	   	 
	   	 String nhanhangId=request.getParameter("nhanhangId");
	   	 if(nhanhangId ==null)
	   		 nhanhangId = "";
	   	 obj.setnhanhangId(nhanhangId);
	   	 
	   	 String chungloaiId=request.getParameter("chungloaiId");
	   	 if(chungloaiId==null)
	   		chungloaiId = "";
	   	 obj.setchungloaiId(chungloaiId);
	   	 
	   	 String tungay=request.getParameter("Sdays");
	   	 if(tungay ==null)
	   		 tungay ="";
	   	 obj.settungay(tungay);
	   	 
	   	 String denngay=request.getParameter("Edays");
	   	 if(denngay == null)
	   		 denngay ="";
	   	 obj.setdenngay(denngay);
	   	 
	    	 String vungId=request.getParameter("vungId");
	   	 if(vungId ==null)
	   		 vungId = "";
	   	 obj.setvungId(vungId);
	   	 
	   	 String khuvucId=request.getParameter("khuvucId");
	   	 if(khuvucId == null)
	   		 khuvucId ="";
	   	 obj.setkhuvucId(khuvucId);
	   	 
	   	 String sanphamId=request.getParameter("sanphamId");
	   	 if(sanphamId == null)
	   		 sanphamId ="";
	   	 obj.setsanphamId(sanphamId);
	
	   	 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien);
		 
		 String [] fieldsAn =request.getParameterValues("fieldsAn");
			obj.setFieldHidden(fieldsAn)  ;
				 
		 String sql ="";
		 if(tungay.length()>0)
			 sql = sql +" and DCTK.NGAYDC >='"+ tungay +"'";
		 if(denngay.length()>0) 
			 sql = sql +" and  DCTK.NGAYDC <='"+ denngay +"'";
		 if(kenhId.length()>0) 
			 sql = sql +" and kbh.pk_seq ='"+ kenhId +"'";
		 if(vungId.length()>0) 
			 sql =sql +" and vung.pk_seq ='"+ vungId +"'";
		 if(khuvucId.length()>0)
			 sql = sql + " and kv.pk_seq ='"+ khuvucId +"'";
		 if(dvkdId.length()>0) 
			 sql = sql +" and sp.dvkd_fk ='"+ dvkdId +"'";
		 if(nppId.length()>0)
			 sql =sql +" and npp.pk_seq ='"+ nppId +"'";
		 if(nhanhangId.length()>0) 
			 sql = sql +" and nhan.pk_seq ='"+ nhanhangId +"'";
		 if(chungloaiId.length()>0)
			 sql = sql +" and cl.pk_seq ='"+ chungloaiId +"'";
		 if(sanphamId.length()>0) 
			 sql = sql + " and sp.pk_seq = '"+ sanphamId +"'";
		 
		 String action = request.getParameter("action");
		 if(tungay.length()>0 && denngay.length() > 0)
		 {
				 if(action.equals("tao"))
				 {
					try{		

						Utility util= new Utility();
						response.setContentType("application/xlsm");
						response.setHeader("Content-Disposition", "attachment; filename=DieuChinhTonKho_"+util.setTieuDe(obj) +".xlsm");
						OutputStream out = response.getOutputStream();

				        setQuery(request,sql);   
				        
				        CreatePivotTable(out, response, request, obj);	// Create PivotTable 
				        
					}catch(Exception ex){
						 obj.setMsg("Khong co bao cao trong thoi gian nay");
					}
				 }
		 }
		 else
			 obj.setMsg("Khong co bao cao trong thoi gian nay");
	 	obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/AdjustInventoryReport.jsp";
		response.sendRedirect(nextJSP);
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response, HttpServletRequest request,IStockintransit obj) throws Exception
	{
		try 
		{
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();		
			
			String fstreamstr = getServletContext().getInitParameter("path") + "\\DieuChinhTonKhoTT.xlsm";
			fstream = new FileInputStream(fstreamstr);
			//fstream = new FileInputStream("D:\\project\\Best\\WebContent\\pages\\Templates\\DieuChinhTonKhoTT.xlsm");
			//fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\DieuChinhTonKhoTT.xlsm");		
			
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			CreateHeader(workbook,obj); 
			FillData(workbook,obj); 
			
			workbook.save(out);
			
			//fstream.close();
		} 
		catch (Exception ex) {
			obj.setMsg("Khong the tao duoc bao cao " + ex.getMessage());
		}
		
	}
	private void FillData(Workbook workbook,IStockintransit obj)throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		for(int i = 0; i < 5; i++)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
	    }	
		
		dbutils db = new dbutils();
		ResultSet rs = db.get(getQuery());
		int index = 2;
	    Cell cell = null;
	    if(rs !=null)
	    {
	    while (rs.next()) 
	    {
	    	double tondau=rs.getDouble("tondau");
	    	double toncuoi=rs.getDouble("toncuoi");
	    	double dieuchinh=rs.getDouble("dieuchinh");
			int qc1=rs.getInt("qc1");
			int qc2=rs.getInt("qc2");
			double tondau_thg=tondau/qc1/qc2;
			double toncuoi_thg=toncuoi/qc1/qc2;
			double dieuchinh_thg=dieuchinh/qc1/qc2;
			
			
	    	
	    	cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("KENH"));
	    	cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("DONVIKINHDOANH"));
			cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("VUNG"));			
			cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("KHUVUC"));
			cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("NPPID"));			//Giam Sat ban hang
			cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("NPP"));
			cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("NGAY"));
			cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("NHANTEN"));
			cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("CLTEN"));
			cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(rs.getString("MA"));			//Ma San Pham SKUCode
			cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(rs.getString("TEN"));			//Ten San Pham SKU
			cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue(rs.getString("khoTen"));			
			cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(rs.getString("QUANHUYEN"));
			cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(rs.getInt("TONDAU")); //13
			cell = cells.getCell("AO" + String.valueOf(index));		cell.setValue( tondau_thg  ); //13
			
			cell = cells.getCell("AP" + String.valueOf(index));		cell.setValue(rs.getInt("DIEUCHINH"));
			cell = cells.getCell("AQ" + String.valueOf(index));		cell.setValue(  dieuchinh_thg  );
			
			cell = cells.getCell("AR" + String.valueOf(index));		cell.setValue(rs.getInt("TONCUOI"));
			cell = cells.getCell("AS" + String.valueOf(index));		cell.setValue(toncuoi_thg);
			
			cell = cells.getCell("AT" + String.valueOf(index));		cell.setValue(rs.getString("LYDODC"));
			cell = cells.getCell("AU" + String.valueOf(index));		cell.setValue(rs.getString("solo"));
			index++;
		}
		   if(rs!=null) 
			   rs.close();
	    }
	    if(db!=null){
	    	db.shutDown();
	    }
	 /*
		    ReportAPI.setHidden(workbook, 16);
			PivotTables pivotTables = worksheet.getPivotTables();
			String pos = Integer.toString(index-1);	
		    int j = pivotTables.add("=AdjustInventoryReport!AA1:AP" + pos,"A10","PivotTable");	   
		    
		    PivotTable pivotTable = pivotTables.get(j);
		    pivotTable.setRowGrand(true);
		    pivotTable.setColumnGrand(true);
		    pivotTable.setAutoFormat(true);  
		    pivotTable.setAutoFormatType(PivotTableAutoFormatType.TABLE10);
		   
		    
		    Hashtable<String, Integer> selected = new Hashtable<String, Integer>();
		    selected.put("Channel",0);
		    selected.put("Region",1);
		    selected.put("Area",2);
		    selected.put("Distributor_code",3);
		    selected.put("Distributor",4);
		    selected.put("Date",5);
		    selected.put("SKU_code",6);
		    selected.put("SKU",7);
		    selected.put("Province",8);
		    selected.put("Begin_Inventory",9);
		    selected.put("Adjust",10);
		    selected.put("Ending_Inventory",11);
		    
		    System.out.println("mang SKU_code"+ selected.get("SKU_code"));
		    String [] manghien = obj.getFieldShow();
		    for(int i=0;i<manghien.length;i++){
		    	System.out.println("mang:"+manghien[i]+ ":"+selected.get(manghien[i]));
		    } 
		    for(int i=0;i<manghien.length;i++){
		    	
		    	int so = selected.get(manghien[i]);
		    	
		    	if(so != 9 && so != 10 && so != 11)
		    	{
		    		System.out.println("so "+ so +"-"+manghien[i]);
		    		pivotTable.addFieldToArea(PivotFieldType.ROW, so); 		
		    	}
		    	else
		    		pivotTable.addFieldToArea(PivotFieldType.DATA, so); 
		    }	
		    
		    
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	pivotTable.getRowFields().get(0).setAutoSubtotals(true);
		 	pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(true);
		 	pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	pivotTable.getRowFields().get(2).setAutoSubtotals(true);
		 	pivotTable.addFieldToArea(PivotFieldType.ROW, 3);	pivotTable.getRowFields().get(3).setAutoSubtotals(true);
		 	pivotTable.addFieldToArea(PivotFieldType.ROW, 5);	pivotTable.getRowFields().get(4).setAutoSubtotals(true);

		 	pivotTable.addFieldToArea(PivotFieldType.ROW, 10);   pivotTable.getRowFields().get(5).setAutoSubtotals(false);
		 	   
		 	pivotTable.addFieldToArea(PivotFieldType.COLUMN, 11); //kho
		 	
		 	pivotTable.addFieldToArea(PivotFieldType.DATA, 13); 	pivotTable.getDataFields().get(0).setDisplayName("TonDau");
		 	pivotTable.getDataFields().get(0).setNumber(3);
		 	
		 	pivotTable.addFieldToArea(PivotFieldType.DATA, 14); 	pivotTable.getDataFields().get(1).setDisplayName("DieuChinh");
		 	pivotTable.getDataFields().get(1).setNumber(3);
		 	
		 	pivotTable.addFieldToArea(PivotFieldType.DATA, 15); 	pivotTable.getDataFields().get(2).setDisplayName("TonCuoi");
		 	pivotTable.getDataFields().get(2).setNumber(3);
		 	
		 	pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
		    */
	}
	
	private void CreateHeader(Workbook workbook,IStockintransit obj) throws Exception 
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
	    
	    String tieude = "BÁO CÁO Kiểm kho";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	           
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày: " + obj.gettungay() + "" );
	   
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("C3"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày: " + obj.getdenngay() + "" );
	   
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    
	    
	    cell = cells.getCell("AA1");		cell.setValue("KenhBanHang");			ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AB1");		cell.setValue("DonviKinhDoanh");			ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AC1");		cell.setValue("ChiNhanh");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("KhuVuc");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");		cell.setValue("MaCN/DT");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("ChiNhanh/DoiTac");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1");		cell.setValue("Ngay");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("NhanHang");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("ChungLoai");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");		cell.setValue("MaSanPham");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");		cell.setValue("TenSanPham");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1");		cell.setValue("KhoTen");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");		cell.setValue("TinhThanh");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1");		cell.setValue("TonDau");						ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO1");		cell.setValue("TonDau(Thung)");						ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AP1");		cell.setValue("DieuChinh");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AQ1");		cell.setValue("DieuChinh(Thung)");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AR1");		cell.setValue("TonCuoi");						ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AS1");		cell.setValue("TonCuoi(Thung)");						ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AT1");		cell.setValue("LyDoDieuChinh");						ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AU1");		cell.setValue("SoLo");						ReportAPI.setCellHeader(cell);
		
	}

}



