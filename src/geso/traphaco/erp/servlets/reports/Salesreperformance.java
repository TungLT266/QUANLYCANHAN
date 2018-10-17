package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.erp.db.sql.dbutils;
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
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;

import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
//import com.aspose.cells.a.a.a.q;

public class Salesreperformance extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    public Salesreperformance() {
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
		String nextJSP = "/TraphacoHYERP/pages/Center/Salesreperfomancenew.jsp";
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
	 
		 
		String nextJSP = "/TraphacoHYERP/pages/Center/Salesreperfomancenew.jsp";		 
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
		String query="";
		long restWD = 0;
		long numofDay = 0;
		geso.traphaco.erp.db.sql.dbutils db=new geso.traphaco.erp.db.sql.dbutils();
		try{
		query = "SELECT CONVERT(VARCHAR(10), DATEADD( s, -1, DATEADD( mm, DATEDIFF( m, 0, '" + obj.getYear() + "-" + obj.getMonth() + "-01' ) + 1, 0 ) ),120) AS LASTDAY";
		System.out.println(query);
		
	    ResultSet	rs = db.get(query);
		rs.next();
		numofDay = Integer.parseInt(rs.getString("LASTDAY").substring(8,10));
		System.out.println(numofDay);
		rs.close();
		
		}catch(Exception er){
			
		}
		db.shutDown();
		 query = "select kbh.pk_seq as kbhId, kbh.ten as Channel, dvkd.pk_seq as dvkdId, dvkd.donvikinhdoanh as BU, v.ten as Region, kv.ten as Area, npp.ten as Distributor," +
				"					npp.pk_seq as Distributor_Code,cast(dvkd.pk_seq as nvarchar(10) )+'_'+ ddkd.ten as Sales_Rep, ddkd.pk_seq as ddkdId," +
				"					isnull(ctnpp.songaylamviec,0) as  Working_day, " +
				"					cast(isnull(ctnpp.SODONHANG, 0) as float) as SoDH," +
				"					cast(isnull(ctnpp.SKU, 0) as float) as SoSKU," +
				"					isnull(ctnpp.chitieu,0) as Monthly_Target, convert(float, ds.doanhso)*1.1 as Achieved," +
				"					case when ctnpp.chitieu > 0 then 100*cast(ds.doanhso as float)*1.1/ctnpp.chitieu" +
				"						else 0" +
				"					end as phantramMTD," +
				
				"					case when ctnpp.songaylamviec > 0 then cast(tsdh.sodonhang as float) " +
				"						 else 0" +
				"					end as NumberOfOrder," +
				
				"					case when tsdh.sodonhang > 0 then cast(tssku.tongsku as float) " +
				"						else 0" +
				"					end as NumberOfSKU," +
				
				"					case when tsdh.sodonhang > 0 then cast(cast(ds.doanhso as float)*1.1/tsdh.sodonhang as float)" +
				"						else 0" +
				"					end as AvgValuePerOrder," +
				
				"					case when tssku.tongsku > 0 then cast(cast(ds.doanhso as float)*1.1/tssku.tongsku as float)" +
				"						else 0" +
				"					end as AvgValuePerSKU," +
				
				"					case when ctnpp.sodonhang > 0 then cast(100*cast(tsdh.sodonhang as float)/ctnpp.sodonhang as float)" +
				"						else 0" +
				"					end as phantramOrder, case when ctnpp.sku > 0 then cast(100*cast(tssku.tongsku as float)/ctnpp.sku as float)" +
				"						else 0" +
				"					end as phantramSKU ," +
				" 	CASE WHEN CTNPP.CHITIEU > 0 AND "+ 
				" 0 < (SELECT DATEDIFF (day,(SELECT MAX(NGAYKS) FROM KHOASONGAY WHERE NPP_FK=NPP.PK_SEQ), CONVERT(VARCHAR(10), DATEADD( s, -1, DATEADD( mm, DATEDIFF( m, 0, '"+obj.getYear()+"-"+obj.getMonth()+"-01' ) + 1, 0 ) ),120)) "+
				" )  AND ( SELECT DATEDIFF (day,(SELECT MAX(NGAYKS) FROM KHOASONGAY WHERE NPP_FK=NPP.PK_SEQ), CONVERT(VARCHAR(10), DATEADD( s, -1, DATEADD( mm, DATEDIFF( m, 0, '"+obj.getYear()+"-"+obj.getMonth()+"-01' ) + 1, 0 ) ),120))) < (SELECT DATEDIFF (day,'"+obj.getYear()+"-"+obj.getMonth()+"-01', CONVERT(VARCHAR(10), DATEADD( s, -1, DATEADD( mm, DATEDIFF( m, 0,'"+obj.getYear()+"-"+obj.getMonth()+"-01') + 1, 0 ) ),120)) + 1) "+ 
				" then cast((cast(ctnpp.chitieu as float) - convert(float, ds.doanhso)*1.1)/( "+ 
				" ( SELECT DATEDIFF (day,(SELECT MAX(NGAYKS) FROM KHOASONGAY WHERE NPP_FK=NPP.PK_SEQ), CONVERT(VARCHAR(10), DATEADD( s, -1, DATEADD( mm, DATEDIFF( m, 0,  '"+obj.getYear()+"-"+obj.getMonth()+"-01' ) + 1, 0 ) ),120)))"+
				" * cast(isnull(ctnpp.songaylamviec,0) as float)/"+numofDay+" ) as float)  "+
				" else 0                 end as TBphaiban "+
				
				"			from (" +
				"					select ctnpp.thang, ctnpp.nam , ctnpp.nhapp_fk as npp_fk, ctnpp.kenh_fk as kbh_fk, ctnpp.dvkd_fk," +
				"							ctnpp_ddkd.ddkd_fk, ctnpp.songaylamviec, ctnpp_ddkd.chitieu, ctnpp_ddkd.sodonhang , ctnpp_ddkd.SKU " +
				"					from CHITIEUNPP ctnpp" +
				"						inner join CHITIEUNPP_DDKD ctnpp_ddkd on ctnpp.pk_seq = ctnpp_ddkd.chitieunpp_fk" +
				"					where ctnpp.thang = '" + fromMonth +"' and ctnpp.nam ='" + fromYear +"' and trangthai <>2 " +
				"				) ctnpp" +
				"				inner join(" +
				"					select dh.npp_fk, dh.ddkd_fk, dh.kbh_fk,sp2.dvkd_fk,sum(dh_sp.soluong * dh_sp.giamua) as 'doanhso'" +
				"					from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq = dh_sp.donhang_fk" +
				"					inner join sanpham sp2 on sp2.pk_seq = dh_sp.sanpham_fk" +
				"					where substring(dh.ngaynhap, 1 , 7)  = '" + obj.getYear() + "-" + obj.getMonth() + "' and dh.trangthai ='1'" +
				"					group by dh.ddkd_fk, dh.npp_fk,sp2.dvkd_fk, dh.kbh_fk" +
				"				) ds on ctnpp.npp_fk = ds.npp_fk and ctnpp.kbh_fk = ds.kbh_fk and ctnpp.ddkd_fk = ds.ddkd_fk and ds.dvkd_fk = ctnpp.dvkd_fk " +
				"				inner join (" +
				"					select  dh.npp_fk, dh.ddkd_fk, sp.dvkd_fk, dh.kbh_fk, count(distinct dh.pk_seq) as 'sodonhang'" +
				"					from donhang dh inner join donhang_sanpham dh_sp on dh_sp.donhang_fk=dh.pk_seq  " +
				"					inner join sanpham sp on sp.pk_seq = dh_sp.sanpham_fk" +
				"					where substring(dh.ngaynhap, 1 , 7)  = '" + obj.getYear() + "-" + obj.getMonth() + "' and dh.trangthai ='1'" +
				"					group by dh.ddkd_fk, dh.npp_fk, dh.kbh_fk,sp.dvkd_fk" +
				"				) tsdh on ds.ddkd_fk = tsdh.ddkd_fk and ds.npp_fk = tsdh.npp_fk  and ds.kbh_fk = tsdh.kbh_fk and tsdh.dvkd_fk = ctnpp.dvkd_fk " +
				"				inner join (" +
				"					select dh.npp_fk, dh.ddkd_fk, dh.kbh_fk,sp1.dvkd_fk, count(dh_sp.soluong) as 'tongsku'" +
				"					from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq = dh_sp.donhang_fk" +
				"						inner join sanpham sp1 on sp1.pk_seq = dh_sp.sanpham_fk" +
				"					where substring(dh.ngaynhap, 1 , 7)  = '" + obj.getYear() + "-" + obj.getMonth() + "' and dh.trangthai ='1'" +
				"					group by dh.ddkd_fk,  dh.npp_fk,sp1.dvkd_fk, dh.kbh_fk" +
				"				) tssku on tssku.ddkd_fk = ds.ddkd_fk and tssku.npp_fk = ds.npp_fk and tssku.dvkd_fk = ctnpp.dvkd_fk and tssku.kbh_fk = ds.kbh_fk" +
				"				inner join kenhbanhang kbh on ds.kbh_fk = kbh.pk_seq" +
				"				inner join donvikinhdoanh dvkd on dvkd.pk_seq = tssku.dvkd_fk" +
				"				inner join nhaphanphoi npp on ds.npp_fk = npp.pk_seq" +
				"				inner join daidienkinhdoanh ddkd on ddkd.pk_seq = ds.ddkd_fk" +
				"				left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk" +
				"				left join vung v on v.pk_seq = kv.vung_fk  " ;
				
		
				geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
		
		
			query += " where npp.pk_seq in "+ ut.quyen_npp(obj.getuserId()) + " and kbh.pk_seq in " + ut.quyen_kenh(obj.getuserId());
			if(obj.getkenhId().length() > 0)
				query += " and kbh.pk_seq='"+obj.getkenhId()+"'";
			if(obj.getnppId().length() >0)
				query += " and npp.pk_seq = '"+obj.getnppId()+"'";
			if(obj.getvungId().length() > 0)
				query += " and v.pk_seq = '"+obj.getvungId()+"'";
			if(obj.getdvkdId().length() > 0)
				query += " and dvkd.pk_seq = '"+obj.getdvkdId()+"'";
			if(obj.getkhuvucId().length() > 0)
				query += " and kv.pk_seq = '"+obj.getkhuvucId()+"'";
			if(obj.getDdkd().length() > 0)
				query += " and ddkd.pk_seq = '"+obj.getDdkd()+"'";
			
			query = query + " order by kbh.ten, dvkd.donvikinhdoanh, v.ten, kv.ten, npp.ten, cast(dvkd.pk_seq as nvarchar(10) )+'_'+ ddkd.ten ";
		System.out.println("Query: " + query);
		
		return query;
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
	    	 throw new Exception(ex.getMessage());
	     }	    
   }
	
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		Style style;		
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("TÌNH HÌNH THỰC HIỆN CHỈ TIÊU ĐẠI DIỆN KINH DOANH");

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
	   
	    cell = cells.getCell("DA1"); cell.setValue("KenhBanHang");
	    cell = cells.getCell("DB1"); cell.setValue("DonViKinhDoanh");
	    cell = cells.getCell("DC1"); cell.setValue("ChiNhanh");
	    cell = cells.getCell("DD1"); cell.setValue("KhuVuc");
	    cell = cells.getCell("DE1"); cell.setValue("MaNhaPhanPhoi");	
	    cell = cells.getCell("DF1");cell.setValue("NhaPhanPhoi");     
	    cell = cells.getCell("DG1"); cell.setValue("DaiDienKinhDoanh");	    	    
	    cell = cells.getCell("DH1"); cell.setValue("NgayLamViec");
	    
	    cell = cells.getCell("DI1"); cell.setValue("ChiTieuDoanhSo");
	    cell = cells.getCell("DJ1"); cell.setValue("ThucDatDoanhSo");
	    cell = cells.getCell("DK1"); cell.setValue("%DoanhSo");
	    cell = cells.getCell("DL1"); cell.setValue("ThuongDoanhSo");
	    
	    cell = cells.getCell("DM1"); cell.setValue("ChiTieuSoDonHang");
	    cell = cells.getCell("DN1"); cell.setValue("ThucDatSoDonHang");	
	    cell = cells.getCell("DO1"); cell.setValue("%SoDonHang");	
	    cell = cells.getCell("DP1"); cell.setValue("ThuongSoDonHang");

	    cell = cells.getCell("DQ1"); cell.setValue("ChiTieuSoSKU");	    
	    cell = cells.getCell("DR1"); cell.setValue("ThucDatSoSKU");
	    cell = cells.getCell("DS1"); cell.setValue("%SoSKU");	
	    cell = cells.getCell("DT1"); cell.setValue("ThuongSoSKU");
	    
	    cell = cells.getCell("DU1"); cell.setValue("TongThuong");
	    
	    cell = cells.getCell("DV1"); cell.setValue("TB DoanhSo/DonHang");
	    cell = cells.getCell("DW1"); cell.setValue("TB DoanhSo/SKU");
	    cell = cells.getCell("DX1"); cell.setValue("TB PhaiBan/Ngay");
	}

	private void FillData(Workbook workbook,String[] fieldShow, String query, IStockintransit obj)throws Exception 
	{
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
		System.out.println(query);
		db.update("SET DATEFORMAT ymd");
		ResultSet rs = db.get(query);	
		
		int indexRow = 2;
		try 
			{				
				Cell cell = null;
				float phantramMTD = 0;
				float phantramDH = 0;
				float phantramSKU = 0;
				
				float ThuongDoanhSo = 0;
				
				float ChiTieuSoDonHang = 0;				
				float ThuongSoDonHang = 0;
				float TBPhaiban = 0;
				float ChiTieuSoSKU = 0;
				float ThuongSoSKU = 0;
				int tmp = 0;

				while(rs.next())
				{ 				
					
				    cell = cells.getCell("DA" + Integer.toString(indexRow)); cell.setValue(rs.getString("Channel"));
				    cell = cells.getCell("DB" + Integer.toString(indexRow)); cell.setValue(rs.getString("BU"));
					cell = cells.getCell("DC" + Integer.toString(indexRow)); cell.setValue(rs.getString("Region"));
					cell = cells.getCell("DD" + Integer.toString(indexRow)); cell.setValue(rs.getString("Area"));					
					cell = cells.getCell("DE" + Integer.toString(indexRow));cell.setValue(rs.getString("Distributor_Code"));				
					cell = cells.getCell("DF" + Integer.toString(indexRow));  cell.setValue(rs.getString("Distributor"));
					cell = cells.getCell("DG" + Integer.toString(indexRow)); cell.setValue(rs.getString("Sales_Rep"));
					cell = cells.getCell("DH" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("Working_day"));
					
					cell = cells.getCell("DI" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("Monthly_Target"));
					cell = cells.getCell("DJ" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("Achieved"));
					
					//TB Phaiban/Ngay
					
					phantramMTD = rs.getFloat("phantramMTD");
					
					cell = cells.getCell("DK" + Integer.toString(indexRow)); cell.setValue(phantramMTD);
					
					String kbh_fk = rs.getString("kbhId");					
					System.out.println("KBH: " + kbh_fk);
					
					String dvkd_fk = rs.getString("dvkdId");
					System.out.println("DVKD: " + dvkd_fk);
					
					query = 	"SELECT TCT_CT.* FROM TIEUCHITINHTHUONG TCT INNER JOIN " +
									"TIEUCHITINHTHUONG_CHITIET TCT_CT ON TCT.PK_SEQ = TCT_CT.TIEUCHITINHTHUONG_FK " +
									"WHERE TCT.DVKD_FK='" + dvkd_fk + "' AND TCT.KBH_FK = '" + kbh_fk + "' AND " +
									"TCT.THANG ='" + obj.getMonth() + "' AND  TCT.LOAI=1  and  TCT.NAM ='" + obj.getYear() + "' " +
									"ORDER BY TCT_CT.TIEUCHI, TCT_CT.STT";
					
					System.out.println(query);
					ResultSet tieuchi = db.get(query);

					tmp = 0;
					int level = 0;
					ThuongDoanhSo = 0;
					boolean exit;
					
					if(tieuchi.next()){
						
						exit = false;
						for (int i = 1; i <=5; i++){
							System.out.println(tieuchi.getString("Diengiai") + "_" + tieuchi.getString("Tu") + "_" + tieuchi.getString("Den"));
							if(!exit){
								if(tieuchi.getString("toantu").equals("<")){
									System.out.println("Phan Tram : " +  phantramMTD);
									
									if(phantramMTD > tieuchi.getFloat("Tu") & phantramMTD < tieuchi.getFloat("Den")){
										float tu =tieuchi.getFloat("Tu");
										if(tu==0){
											tu=1;
										}
										if(i != 5 & i != 1){
											//neu tu =0 thi chia bi loi,minh cho tu =1 thi lay nguyen muc % do.
											
											ThuongDoanhSo = (phantramMTD/tu)*tieuchi.getFloat("Thuong");
											exit = true;
										}else{
											ThuongDoanhSo = (phantramMTD/tu)* tieuchi.getFloat("Thuong");
											if(i==5) exit = true;
										}
										level = i;
										
										System.out.println("Gia tri cua level:" + level);
										
									}else if(phantramMTD > tieuchi.getFloat("Tu") & i == 5){
										//khoa sua lai them chia ty le.
										
										ThuongDoanhSo = (phantramMTD/tieuchi.getFloat("Tu"))*tieuchi.getFloat("Thuong");
										System.out.println("Thuong doanh so: " +  ThuongDoanhSo);
										
										exit = true;
										level = i;
									}
								}
							}
							tieuchi.next();
						}
					
						cell = cells.getCell("DL" + Integer.toString(indexRow)); cell.setValue(ThuongDoanhSo);
					
						ChiTieuSoDonHang = rs.getFloat("Working_day")*rs.getFloat("soDH");
						cell = cells.getCell("DM" + Integer.toString(indexRow)); cell.setValue(ChiTieuSoDonHang);					
						cell = cells.getCell("DN" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("NumberOfOrder"));
					
						if (ChiTieuSoDonHang > 0)
							phantramDH = 100*rs.getFloat("NumberOfOrder")/ChiTieuSoDonHang;
						else
							phantramDH = 0;
						
						cell = cells.getCell("DO" + Integer.toString(indexRow)); cell.setValue(phantramDH);
					
						ThuongSoDonHang = 0;
						
						for (int i = 1; i <= 5; i++){
							System.out.println(tieuchi.getString("Diengiai") + "_" + tieuchi.getString("Tu") + "_" + tieuchi.getString("Den"));
							if (exit && i <=level){
								if(tieuchi.getString("toantu").equals("<")){
									if(phantramDH > tieuchi.getFloat("Tu") & phantramDH < tieuchi.getFloat("Den")){
										if(i > 1){  // Neu dat tren 85% thi tinh theo ti le

											ThuongSoDonHang = tieuchi.getFloat("Thuong");
										}else{
											ThuongSoDonHang = tieuchi.getFloat("Thuong");
										}
									}else if(phantramDH > tieuchi.getFloat("Tu") & i == level){
										ThuongSoDonHang = tieuchi.getFloat("Thuong");
									}

									System.out.println("Thuong So Don Hang: " +  ThuongSoDonHang);
								}
							}
							tieuchi.next();						
						}
					
						cell = cells.getCell("DP" + Integer.toString(indexRow)); cell.setValue(ThuongSoDonHang);
										
						ChiTieuSoSKU = rs.getFloat("Working_day")*rs.getFloat("SOSKU")*rs.getFloat("SODH");
						cell = cells.getCell("DQ" + Integer.toString(indexRow)); cell.setValue(ChiTieuSoSKU);					
						cell = cells.getCell("DR" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("NumberOfSKU"));
					
						if(ChiTieuSoSKU > 0){
							phantramSKU = 100*rs.getFloat("NumberOfSKU")/ChiTieuSoSKU;
						}else{
							phantramSKU = 0;
						}
						cell = cells.getCell("DS" + Integer.toString(indexRow)); cell.setValue(phantramSKU);
					
						ThuongSoSKU = 0;
						
						for (int i = 1; i <= 5; i++){
							System.out.println("Level Cua SKU :"+level);
							
							System.out.println(tieuchi.getString("Diengiai") + "_" + tieuchi.getString("Tu") + "_" + tieuchi.getString("Den"));
							if(exit && i <=level){
								if(tieuchi.getString("toantu").equals("<")){
									
									if(phantramSKU > tieuchi.getFloat("Tu") & phantramSKU < tieuchi.getFloat("Den")){
										if(i > 1){ // Neu dat tren 85%, thi tinh theo ti le
											
											ThuongSoSKU = tieuchi.getFloat("Thuong");
										}else{
											ThuongSoSKU = tieuchi.getFloat("Thuong");
										}
										exit = true;
									}else if(phantramSKU > tieuchi.getFloat("Tu") & i == level){  // Truong hop muc dat cao hon muc dat doanh so ban
										ThuongSoSKU = tieuchi.getFloat("Thuong");
									}
								
								System.out.println("Thuong SKU: " +  ThuongSoSKU);
								}								
							}
							if(i<5) tieuchi.next();						
						}
					
						cell = cells.getCell("DT" + Integer.toString(indexRow)); cell.setValue(ThuongSoSKU);
						cell = cells.getCell("DU" + Integer.toString(indexRow)); cell.setValue(ThuongDoanhSo + ThuongSoDonHang + ThuongSoSKU);
					}else{
						cell = cells.getCell("DL" + Integer.toString(indexRow)); cell.setValue("0");
						
						ChiTieuSoDonHang = rs.getFloat("Working_day")*rs.getFloat("soDH");
						cell = cells.getCell("DM" + Integer.toString(indexRow)); cell.setValue(ChiTieuSoDonHang);					
						cell = cells.getCell("DN" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("NumberOfOrder"));
					
						if(ChiTieuSoDonHang > 0){
							phantramDH = 100*rs.getFloat("NumberOfOrder")/ChiTieuSoDonHang;
						}else{
							phantramDH = 0;
						}
						cell = cells.getCell("DO" + Integer.toString(indexRow)); cell.setValue(phantramDH);
						cell = cells.getCell("DP" + Integer.toString(indexRow)); cell.setValue("0");
										
						ChiTieuSoSKU = rs.getFloat("Working_day")*rs.getFloat("SOSKU")*rs.getFloat("SODH");
						cell = cells.getCell("DQ" + Integer.toString(indexRow)); cell.setValue(ChiTieuSoSKU);					
						cell = cells.getCell("DR" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("NumberOfSKU"));
					
						if(ChiTieuSoSKU > 0){
							phantramSKU = 100*rs.getFloat("NumberOfSKU")/ChiTieuSoSKU;
						}else{
							phantramSKU = 0;
						}
						
						cell = cells.getCell("DS" + Integer.toString(indexRow)); cell.setValue(phantramSKU);										
						cell = cells.getCell("DT" + Integer.toString(indexRow)); cell.setValue("0");
						cell = cells.getCell("DU" + Integer.toString(indexRow)); cell.setValue("0");
						
					}
					
					cell = cells.getCell("DV" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("AvgValuePerOrder"));
					
					cell = cells.getCell("DW" + Integer.toString(indexRow)); cell.setValue(rs.getFloat("AvgValuePerSKU"));		
					
					TBPhaiban = rs.getFloat("TBphaiban");
					
					if(TBPhaiban < 0) TBPhaiban = 0;
					
					cell = cells.getCell("DX" + Integer.toString(indexRow)); cell.setValue(TBPhaiban);
					indexRow++;
					if(tieuchi != null) tieuchi.close();
				}
				if(rs != null) rs.close();
				if(db!=null){
					db.shutDown();
				}

		    	
			}catch(java.sql.SQLException err){
				System.out.println(err.toString());
				throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :"+err.toString());
			}
	}
}
