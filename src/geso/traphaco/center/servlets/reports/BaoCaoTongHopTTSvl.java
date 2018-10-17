package geso.traphaco.center.servlets.reports;
import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.util.Utility;

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
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
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
		obj.setuserId(userId);
		
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
		
		obj.init();
		
		if(action.equals("Taomoi"))
		{
			try
			{   
				response.setCharacterEncoding("utf-8");			
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", "attachment; filename=BCTongHop_"+util.setTieuDe(obj)+".xlsm");
		        
		        
		        String strfstream = getServletContext().getInitParameter("path") + "\\BaoCaoTongHop(TT).xlsm";
		        FileInputStream fstream = new FileInputStream(strfstream);
		        
		        Workbook workbook = new Workbook();
				workbook.open(fstream);
						        
		        workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		        Worksheets worksheets = workbook.getWorksheets();
		        
		        Worksheet worksheet = worksheets.getSheet("BanHangHangNgay");		
		        
		        CreatePivotBanHangHangNgay(worksheet, obj, level, sql);		        
		        
		        worksheet = worksheets.addSheet("DoanhSoMuaHang");

		        CreatePivotMuaHangHangNgay(worksheet, obj);
		        		        		        
		        worksheet = worksheets.addSheet("TonKhoNgay");
		        
		        CreatePivotDailyStock(worksheet, obj);
		        
		        worksheet = worksheets.addSheet("KhoaSoNgay");

		        CreatePivotKhoaSo(worksheet, obj);
		        
		        workbook.save(out);
		        fstream.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				System.out.println("Xay ra exception roi..." + ex.toString());
				session.setAttribute("obj", obj);
				obj.setMsg(ex.getMessage());
				response.sendRedirect(nextJSP);
			}
		}else{
			
			session.setAttribute("obj", obj);
			 nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoTongHopTT.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	
	//Khoa so ngay
	private boolean CreatePivotKhoaSo(Worksheet worksheet, IStockintransit obj)throws Exception
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
				"	WHERE k.NGAYKS >= '" + obj.gettungay() + "' AND k.NGAYKS <='" + obj.getdenngay() + "' " + sql + 
				" 	ORDER BY V.TEN, KV.TEN, N.TEN, K.NGAYKS" ;
		
		System.out.println(query);
		
		CreateHeaderKS(worksheet, obj);
		FillDataKSNgay(worksheet, obj, query);
		
		return true;	
	}

	private void CreateHeaderKS(Worksheet worksheet, IStockintransit obj)throws Exception 
	{
		try
		{
			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
			getCellStyle(worksheet,"A1",Color.RED,true,14);	    
			cell.setValue("THEO DÕI KHÓA SỔ NGÀY");   	
//			cells.merge(0, 0, 0, 2);
			
		    cells.setRowHeight(2, 18.0f);
		    cell = cells.getCell("A3"); 
		    getCellStyle(worksheet,"A3",Color.NAVY,true,10);	    
		    cell.setValue("Từ ngày: " + obj.gettungay());
		    
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B3"); getCellStyle(worksheet,"B3",Color.NAVY,true,9);	        
		    cell.setValue("Đến ngày: " + obj.getdenngay());    
		    
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");getCellStyle(worksheet,"A4",Color.NAVY,true,9);
		    cell.setValue("Ngày báo cáo: " + obj.getDateTime());
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");getCellStyle(worksheet,"A5",Color.NAVY,true,9);
		    cell.setValue("Được tạo bởi:  " + obj.getuserTen());
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A6");getCellStyle(worksheet,"A6",Color.NAVY,true,9);
		    cell.setValue("Đơn vị tiền tệ VND");

			
			cell = cells.getCell("EA1");		cell.setValue("ChiNhanh");
			cell = cells.getCell("EB1");		cell.setValue("KhuVuc");			
			cell = cells.getCell("EC1");		cell.setValue("MaCN/DT");
			cell = cells.getCell("ED1");		cell.setValue("SiteCode");	
			cell = cells.getCell("EE1");		cell.setValue("ChiNhanh/DoiTac");
			cell = cells.getCell("EF1");		cell.setValue("Ngay");		
			cell = cells.getCell("EG1");		cell.setValue("KhoaSo");			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println(ex.toString());
			throw new Exception("Khong tao duoc Header cho bao cao khoa so ngay");
		}
	}
	
	private boolean FillDataKSNgay(Worksheet worksheet, IStockintransit obj, String query) throws Exception 
	{
		dbutils db = new dbutils();
		Cells cells = worksheet.getCells();		
		
		ResultSet rs = db.get(query);
		int i = 2;
		if(rs!=null){
			Cell cell = null;
	    	cells.setColumnWidth(0, 15.0f);
	    	cells.setColumnWidth(1, 15.0f);
	    	cells.setColumnWidth(2, 15.0f);
	    	cells.setColumnWidth(3, 15.0f);
	    	cells.setColumnWidth(4, 25.0f);

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

				}
				if (rs != null)
					rs.close();
				
				if(db != null) db.shutDown();
				
				if(i == 2){					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
				
//				setHidden(workbook, 170);
				PivotTables pivotTables = worksheet.getPivotTables();
				String pos = Integer.toString(i - 1);
				int index = pivotTables.add("=KhoaSoNgay!EA1:EG" + pos, "A10","PivotTable1");
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
								
				pivotTable.addFieldToArea(PivotFieldType.COLUMN, 5);
				pivotTable.getColumnFields().get(0).setDisplayName("Ngày");
				
				pivotTable.addFieldToArea(PivotFieldType.DATA, 6);
				pivotTable.getDataFields().get(0).setDisplayName("KhoaSoNgay");
				
//				worksheet.autoFitColumns();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}
		return true;
	}

	//Khoa so ngay
	private boolean CreatePivotMuaHangHangNgay(Worksheet worksheet, IStockintransit obj)throws Exception
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
		 if(obj.getdvkdId().length() > 0) 
			 sql = sql + " and sp.dvkd_fk ='"+ obj.getdvkdId() + "'";
		 if(obj.getnppId().length() > 0)
			 sql =sql +" and npp.pk_seq ='"+ obj.getnppId() + "'";
		 if(obj.getnhanhangId().length() > 0) 
			 sql = sql +" and nhan.pk_seq ='"+ obj.getnhanhangId() + "'";
		 if(obj.getchungloaiId().length() > 0)
			 sql = sql +" and cl.pk_seq ='"+ obj.getchungloaiId() + "'";
		 if(obj.getchungloaiId().length() > 0) 
			 sql = sql + " and sp.dvdl_fk ='"+ obj.getdvdlId() +"'";
		 
		 if(Integer.parseInt(obj.getpromotion()) == 0) // khng lay khuyen mai + trung bay
		 {
			 sql = sql + " and nhsp.gianet >0  ";
		 }
		 
		 String chietkhau="0";
		 String  vat="0";
		 if(obj.getdiscount().equals("1"))
			 chietkhau= "(CKDH+CKTT+CKTM)  ";
		 if(obj.getvat().equals("1"))
			 vat=" nhsp.VAT";
		 

		 String gia ="nhsp.gianet";
		 if(Integer.parseInt(obj.getdiscount()) > 0)
			 gia ="nhsp.GIAGROSS";
		String userId = obj.getuserId();
		
		
		
		String  query = "select " +
  		"kbh.ten as Channel,v.ten as Region, kv.ten as Area,nhan.ten as Brands,cl.ten as Category,nh.ngaychungtu as Shipdate," +
  		"npp.sitecode as Distributor_code, npp.ten as Distributor,tt.ten as Province,nh.chungtu as Docno,nh.pk_seq as Distcode," +
  		"nhsp.sanpham_fk as SKU_code,sp.ten as SKU, DVKD.DONVIKINHDOANH, nhsp.soluong as Piece, ( nhsp.soluong * " + gia +")-"+chietkhau+"+"+vat+"   as Amount," +
  		"	qc.soluong1,qc.soluong2 		" +
		" from nhaphang nh " +
		"inner join nhaphang_sp nhsp on nh.pk_seq = nhsp.nhaphang_fk " +
		"inner join sanpham sp on sp.ma = nhsp.sanpham_fk " +
		"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK " +
		"inner join nhaphanphoi npp on npp.pk_seq = nh.npp_fk " +
		"left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk " +
		"left join vung v on v.pk_seq = kv.vung_fk " +
		"left join nhanhang nhan on nhan.pk_seq = sp.nhanhang_fk " +
		"left join chungloai cl on cl.pk_seq = sp.chungloai_fk " +
		"left join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk " +
		"left join  quycach qc on qc.sanpham_fk=sp.pk_Seq and qc.dvdl2_fk=100018 " +
		"left join kenhbanhang kbh on kbh.pk_seq = nh.kbh_fk where nh.trangthai <> '2' " +
		" and nh.pk_Seq not in(select nhaphang_fk from huynhaphang where trangthai=1 ) " ;
					
  	//phanquyen
		geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
		query += " and npp.pk_seq in "+ ut.quyen_npp(userId) + " and kbh.pk_seq in " + ut.quyen_kenh(userId)
							+" and sp.pk_seq in "+ ut.quyen_sanpham(userId);

  if(query.length()>0)
  	query = query + sql;
  System.out.print("Doanh So Mua Hang: " + query);

			if(query.length() > 0)
				query = query + sql + " order by kbh.ten, v.ten, kv.ten, npp.ten, nh.ngaychungtu, nhsp.sanpham_fk, sp.ten ";
				
		System.out.println(query);
		
		CreateHeaderMuaHangHangNgay(worksheet, obj);
		FillDataMuahanghangngay(worksheet, obj, query);
		
		return true;	
	}
	
	//Bao cao mua hang hang ngay
	private void CreateHeaderMuaHangHangNgay(Worksheet worksheet, IStockintransit obj) 
	{    
	    Cells cells = worksheet.getCells();	    
	    cells.setRowHeight(0, 20.0f);	  
	    
	    Cell cell = cells.getCell("A1");	
	    getCellStyle(worksheet, "A1" , Color.RED, true, 14 );
	    cell.setValue("BÁO CÁO MUA HÀNG");

	    String message = "";
		if(obj.getdiscount().equals("0"))
		{
			message += "Không trừ chiết khấu";

			if(obj.getvat().equals("1"))
			{
				message += ",có VAT";
			}
			else
			{
				message += ",không VAT";
			}			
		}
		else
		{
			message += "Trừ chiết khấu";
			if(obj.getvat().equals("1"))
			{
				message += ", có VAT";
			}
			else
			{
				message +=", không VAT";
			}
		}
		
		
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   
	    
	    
	    
	    cells.setRowHeight(2, 18.0f);
	    cell = cells.getCell("A3");
	    getCellStyle(worksheet, "A3", Color.NAVY,true,9);
	    cell.setValue("Từ ngày: " + obj.gettungay());
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3");
	    getCellStyle(worksheet, "B3", Color.NAVY,true,9);
	    cell.setValue("Đến ngày: " + obj.getdenngay());
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");
	    getCellStyle(worksheet, "A4" , Color.NAVY,true, 9);	    
	    cell.setValue("Ngày báo cáo: " +obj.getDateTime());
	    
	    
	    
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");
	    getCellStyle(worksheet, "A5" , Color.NAVY,true, 9);
	    cell.setValue("Được tạo bởi: " + obj.getuserTen());
	    
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    getCellStyle(worksheet, "A6" , Color.NAVY,true, 9);
	    cell.setValue("Đơn vị tiền tệ VND");
	    
	    
	    
	    cell = cells.getCell("AA1");		cell.setValue("KenhBanHang");		ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AB1");		cell.setValue("DonViKinhDoanh");	ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("ChiNhanh");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("KhuVuc");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");		cell.setValue("Tinh/Thanh");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("MaCN/DT");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1");		cell.setValue("ChiNhanh/DoiTac");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("NhanHang");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("ChungLoai");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");		cell.setValue("SoChungTu");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");		cell.setValue("Ngay");				ReportAPI.setCellHeader(cell);		
		cell = cells.getCell("AL1");		cell.setValue("MaSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");		cell.setValue("SanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1");		cell.setValue("SoLuong");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO1");		cell.setValue("SoLuong(Kien)");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AP1");		cell.setValue("SoTien");			ReportAPI.setCellHeader(cell);
	}
	
	private void FillDataMuahanghangngay(Worksheet worksheet, IStockintransit obj, String query) throws Exception 
	{
		Cells cells = worksheet.getCells();
		
    	cells.setColumnWidth(0, 15.0f);
    	cells.setColumnWidth(1, 15.0f);
    	cells.setColumnWidth(2, 15.0f);
    	cells.setColumnWidth(3, 25.0f);
    	cells.setColumnWidth(4, 15.0f);
    	cells.setColumnWidth(5, 25.0f);
    	cells.setColumnWidth(6, 15.0f);
    	cells.setColumnWidth(7, 15.0f);
    	cells.setColumnWidth(5, 25.0f);
    	cells.setColumnWidth(6, 15.0f);
    	cells.setColumnWidth(7, 15.0f);
    	
		dbutils db = new dbutils();

		ResultSet rs = db.get(query);
		int index = 2;
	    Cell cell = null;	    
	    try{
	    	while (rs.next()) {
	    		
	    		double soluong=rs.getDouble("Piece");
				int qc1=rs.getInt("soluong1");
				int qc2=rs.getInt("soluong2");
				double so_luong_thung=soluong/qc1/qc2;
	    		
	    		cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("Channel"));	//Kenh ban hang  0	
	    		cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("DONVIKINHDOANH"));	// don vi kinh doanh 1
	    		cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("Region"));	//Vung/Mien  	2
	    		cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("Area"));		//khu vuc    3
	    		cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("Province"));	//Tinh thanh 4
	    		cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("Distributor_code"));	//Ma NPP 5
	    		cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("Distributor"));	//Ten NPP   6
	    		cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("Brands"));	//Nhan hang   	7
	    		cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("Category"));	//Chung loai 8
	    		cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(rs.getString("Docno"));	//So luong quy le san pham 9
	    		cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(rs.getString("Shipdate"));	//So Tien    10
	    		cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue(rs.getString("SKU_code"));	//Ma san pham   11
	    		cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(rs.getString("SKU"));				//Ten san pham 12
	    		cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(rs.getDouble("Piece"));	//Ngay xuat hoa don tu cong ty 13
	    		cell = cells.getCell("AO" + String.valueOf(index));		cell.setValue( so_luong_thung );	//Ngay xuat hoa don tu cong ty 14
	    		cell = cells.getCell("AP" + String.valueOf(index));		cell.setValue(rs.getDouble("Amount")); //15
	    		index++;
	    	}
	    }catch(Exception err){
	    	err.printStackTrace();
	    	System.out.println(err.toString());	  
	    }
	    
	    if(rs!=null) rs.close();
	    if(db != null) db.shutDown();
//	    ReportAPI.setHidden(workbook,13);
		
	    PivotTables pivotTables = worksheet.getPivotTables();
		String pos = Integer.toString(index-1);	
	    int j = pivotTables.add("=DoanhSoMuaHang!AA1:AP" + pos,"A10","PivotTable2");	   
	    
	    PivotTable pivotTable = pivotTables.get(j);	    
	    pivotTable.setRowGrand(false);
	    pivotTable.setColumnGrand(false);
	    pivotTable.setAutoFormat(false);
	    
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);
	    
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 10);
	    pivotTable.getRowFields().get(4).setAutoSubtotals(false);
	    
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 11);	    
	    pivotTable.getRowFields().get(5).setAutoSubtotals(false);
	    
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 13);
	    pivotTable.getFields(PivotFieldType.DATA).get(0).setDisplayName("SoLuong");
	    pivotTable.getDataFields().get(0).setNumber(3);
	    
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 14);
	    pivotTable.getFields(PivotFieldType.DATA).get(1).setDisplayName("SoLuong(Kien)");
	    pivotTable.getDataFields().get(1).setNumber(3);
	    
	    pivotTable.addFieldToArea(PivotFieldType.DATA, 15);
	    pivotTable.getFields(PivotFieldType.DATA).get(2).setDisplayName("SoTien");
	    pivotTable.getDataFields().get(2).setNumber(3);
	    
	    
	    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
	    pivotTable.getColumnFields().get(0).setDisplayName("Data");
   	
	    
	}

	private boolean CreatePivotBanHangHangNgay(Worksheet worksheet, IStockintransit obj, String level, String sql)throws Exception 
	{
		String query = this.createQueryBCDoanhSo(sql, obj, level);
		boolean isFillData = false;
		
		switch (Integer.parseInt(level)) {
		case 0:			
			CreateHeaderLevelOne(worksheet , obj);
			isFillData = FillDataLevelOne(worksheet, obj, query);
			break;
		case 1:			
			CreateHeaderLevelTwo(worksheet, obj);
			isFillData = FillDataLevelTwo(worksheet, obj, query);
			break;
		case 2:
			CreateHeaderLevelThree(worksheet,obj);
			isFillData = FillDataLevelThree(worksheet, obj, query);			
			break;
		}	   
		if (!isFillData){
			return false;
		}
		
		return true;	
	}
	
	private void CreateHeaderLevelOne(Worksheet worksheet, IStockintransit obj)throws Exception
	{	
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
	    
	    String tieude = "BÁO CÁO DOANH SỐ BÁN HÀNG THEO NGÀY";
	    if(obj.getFromMonth().length() > 0)
	    	tieude = "BÁO CÁO DOANH SỐ BÁN HÀNG THEO THÁNG";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	            
	    
	    Utility  util = new  Utility();
		 cells.setRowHeight(3, 18.0f);
		 cell = cells.getCell("A2");
		 ReportAPI.getCellStyle(cell,Color.RED,true, 9, util.setTieuDe(obj));

		 String message = "";
			if(obj.getdiscount().equals("0")){
				message += "Không trừ chiết khấu";

				if(obj.getvat().equals("1")){
					message += ", không VAT";
				}else{
					message += ", có VAT";
				}			
			}else{
				message += "Trừ chiết khấu";
				if(obj.getvat().equals("1")){
					message += ", không VAT";
				}else{
					message +=", có VAT";
				}
			}
			
			cells.setRowHeight(2, 18.0f);
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   
			
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    
	    if(obj.getFromMonth() == "")
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
	    else
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ tháng : " + obj.getFromMonth() + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B4"); 
	    if(obj.getFromMonth() == "")
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
	    else
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến tháng : " + obj.getToMonth() + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    		
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A7");
	    getCellStyle(worksheet, "A7" , Color.NAVY,true, 9);
	    cell.setValue("Đơn vị tiền tệ VND");
	    
	    
		
		cell = cells.getCell("DA1");		cell.setValue("KenhBanHang");
		cell = cells.getCell("DB1");		cell.setValue("DonViKinhDoanh");
		cell = cells.getCell("DC1");		cell.setValue("ChiNhanh");
		cell = cells.getCell("DD1");		cell.setValue("KhuVuc");
		cell = cells.getCell("DE1");		cell.setValue("ChiNhanh/DoiTac");
		cell = cells.getCell("DF1");		cell.setValue("NhanHang");
		cell = cells.getCell("DG1");		cell.setValue("ChungLoai");
		cell = cells.getCell("DH1");		cell.setValue("MaCN/DT");
		cell = cells.getCell("DI1");		cell.setValue("TenSanPham");
		cell = cells.getCell("DJ1");		cell.setValue("MaSanPham");
		cell = cells.getCell("DK1");
		if(obj.getFromMonth() != "")
			cell.setValue("Thang");
		else	
			cell.setValue("Ngay");
		cell = cells.getCell("DL1");		cell.setValue("SoTien");
		cell = cells.getCell("DM1");		cell.setValue("SoLuong");
		cell = cells.getCell("DN1");		cell.setValue("SoLuong(Kien)");
	}
	
	private boolean FillDataLevelOne(Worksheet worksheet, IStockintransit obj, String query) throws Exception{
		dbutils db = new dbutils();

		Cells cells = worksheet.getCells();		
		ResultSet rs = db.get(query);
		int i = 2;
		
		if (rs != null) {
			try {
				Cell cell = null;
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);			
				cells.setColumnWidth(5, 15.0f);	
				cells.setColumnWidth(6, 15.0f);	
				cells.setColumnWidth(7, 15.0f);	
				cells.setColumnWidth(8, 20.0f);
				cells.setColumnWidth(9, 20.0f);
				cells.setColumnWidth(10, 20.0f);
				cells.setColumnWidth(11, 20.0f);
				cells.setColumnWidth(12, 20.0f);
				cells.setColumnWidth(13, 20.0f);
				
				cell = cells.getCell("Z1"); cell.setValue("1");
				while (rs.next()) 
				{					
					String chanel = rs.getString("chanel");
					String bu = rs.getString("bu");
					String region = rs.getString("region");
					String area = rs.getString("area");
					String distributor = rs.getString("distributor");
					String brand = rs.getString("brand");
					String category = rs.getString("category");
					String discode = rs.getString("distcode");
					String sku = rs.getString("SKU");
					String skuCode = rs.getString("SKUcode");
					String ngay = rs.getString("ngay");
					float amount = rs.getFloat("amount");
					float piece = rs.getFloat("piece");
					//float q = rs.getFloat("soluong2")*piece/rs.getFloat("soluong1");
					float q = rs.getFloat("quantity");
					
					
					cell = cells.getCell("DA" + Integer.toString(i));	cell.setValue(chanel);	//0
					cell = cells.getCell("DB" + Integer.toString(i));	cell.setValue(bu); 		//1
					cell = cells.getCell("DC" + Integer.toString(i));	cell.setValue(region); 	//2
					cell = cells.getCell("DD" + Integer.toString(i));	cell.setValue(area);	//3
					cell = cells.getCell("DE" + Integer.toString(i));	cell.setValue(distributor); //4					
					cell = cells.getCell("DF" + Integer.toString(i));	cell.setValue(brand);	//5
					cell = cells.getCell("DG" + Integer.toString(i));	cell.setValue(category);//6
					cell = cells.getCell("DH" + Integer.toString(i));	cell.setValue(discode);	//7
					cell = cells.getCell("DI" + Integer.toString(i));	cell.setValue(sku);		//8
					cell = cells.getCell("DJ" + Integer.toString(i));	cell.setValue(skuCode);	//9
					cell = cells.getCell("DK" + Integer.toString(i));	cell.setValue(ngay);	//10
					cell = cells.getCell("DL" + Integer.toString(i));	cell.setValue(amount);	//11
					cell = cells.getCell("DM" + Integer.toString(i));	cell.setValue(piece);	//12
					cell = cells.getCell("DN" + Integer.toString(i));	cell.setValue(q);		//13			
					++i;					
				}

				if (rs != null) rs.close();
				
				if(db != null) db.shutDown();
				
				if(i==2){					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
				
			    
			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}		
		return true;
	}	
	
	//Level Dai dien kinh doanh
	private void CreateHeaderLevelTwo(Worksheet worksheet, IStockintransit obj)throws Exception{	
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
	    
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, "BÁO CÁO DOANH SỐ BÁN HÀNG THEO NGÀY");
	            
	    Utility  util = new  Utility();
		 cells.setRowHeight(3, 18.0f);
		 cell = cells.getCell("A2");
		 ReportAPI.getCellStyle(cell,Color.RED,true, 9, util.setTieuDe(obj));

	    
		 String message = "";
			if(obj.getdiscount().equals("0")){
				message += "Không trừ chiết khấu";

				if(obj.getvat().equals("1")){
					message += ", không VAT";
				}else{
					message += ", có VAT";
				}			
			}else{
				message += "Trừ chiết khấu";
				if(obj.getvat().equals("1")){
					message += ", không VAT";
				}else{
					message +=", có VAT";
				}
			}
			
			cells.setRowHeight(2, 18.0f);
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B4"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());

	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A7");
	    getCellStyle(worksheet, "A7" , Color.NAVY,true, 9);
	    cell.setValue("Đơn vị tiền tệ VND");
	    
	    
		
		cell = cells.getCell("DA1");		cell.setValue("KenhBanHang");
		cell = cells.getCell("DB1");		cell.setValue("DonViKinhDoanh");
		cell = cells.getCell("DC1");		cell.setValue("ChiNhanh");
		cell = cells.getCell("DD1");		cell.setValue("KhuVuc");
		cell = cells.getCell("DE1");		cell.setValue("GiamSat");			
		cell = cells.getCell("DF1");		cell.setValue("ChiNhanh/DoiTac");
		cell = cells.getCell("DG1");		cell.setValue("DaiDienKinhDoanh");	
		cell = cells.getCell("DH1");		cell.setValue("NhanHang");
		cell = cells.getCell("DI1");		cell.setValue("ChungLoai");
		cell = cells.getCell("DJ1");		cell.setValue("MaCN/DT");
		cell = cells.getCell("DK1");		cell.setValue("MaDaiDienKinhDoanh");
		cell = cells.getCell("DL1");		cell.setValue("TenSanPham");
		cell = cells.getCell("DM1");		cell.setValue("MaSanPham");		
		cell = cells.getCell("DN1");		
		if(obj.getFromMonth() != "")
			cell.setValue("Thang");
		else	
			cell.setValue("Ngay");
		cell = cells.getCell("DO1");		cell.setValue("SoTien");
		cell = cells.getCell("DP1");		cell.setValue("SoLuong");
		cell = cells.getCell("DQ1");		cell.setValue("SoLuong(Kien)");
	}
	
	private boolean FillDataLevelTwo(Worksheet worksheet, IStockintransit obj, String query) throws Exception{
		dbutils db = new dbutils();
		Cells cells = worksheet.getCells();		
		ResultSet rs = db.get(query);
		int i = 2;
		
		if (rs != null) {
			try {
				Cell cell = null;
				
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);			
				cells.setColumnWidth(5, 15.0f);	
				cells.setColumnWidth(6, 15.0f);	
				cells.setColumnWidth(7, 20.0f);
				cells.setColumnWidth(8, 20.0f);
				cells.setColumnWidth(9, 20.0f);
				cells.setColumnWidth(10, 20.0f);
				cells.setColumnWidth(11, 20.0f);
				cells.setColumnWidth(12, 20.0f);
				cells.setColumnWidth(13, 20.0f);
				cells.setColumnWidth(14, 20.0f);
				cells.setColumnWidth(15, 20.0f);
				
				cell = cells.getCell("Z1"); cell.setValue(2);
				
				while (rs.next()) {					
					String chanel = rs.getString("chanel");
					String bu = rs.getString("bu");
					String region = rs.getString("region");
					String area = rs.getString("area");
					String salesuper = rs.getString("salesuper");
					String distributor = rs.getString("distributor");					
					String salesRep = rs.getString("SalesRep");
					String salesRepId = rs.getString("SalesRepId");
					String brand = rs.getString("brand");
					String category = rs.getString("category");
					String discode = rs.getString("distcode");
					String sku = rs.getString("SKU");
					String skuCode = rs.getString("SKUcode");
					String ngay = rs.getString("ngay");
					float amount = rs.getFloat("amount");
					float piece = rs.getFloat("piece");
					float q = rs.getFloat("quantity");
										
					cell = cells.getCell("DA" + Integer.toString(i));	cell.setValue(chanel); //0
					cell = cells.getCell("DB" + Integer.toString(i));	cell.setValue(bu);	   //1
					cell = cells.getCell("DC" + Integer.toString(i));	cell.setValue(region); //2
					cell = cells.getCell("DD" + Integer.toString(i));	cell.setValue(area);   //3
					cell = cells.getCell("DE" + Integer.toString(i));	cell.setValue(salesuper); //4					
					cell = cells.getCell("DF" + Integer.toString(i));	cell.setValue(distributor);	//5				
					cell = cells.getCell("DG" + Integer.toString(i));	cell.setValue(salesRep); //6
					cell = cells.getCell("DH" + Integer.toString(i));	cell.setValue(brand); //7
					cell = cells.getCell("DI" + Integer.toString(i));	cell.setValue(category); //8
					cell = cells.getCell("DJ"+ Integer.toString(i));	cell.setValue(discode);	//9			
					cell = cells.getCell("DK" + Integer.toString(i));	cell.setValue(salesRepId);//10
					cell = cells.getCell("DL" + Integer.toString(i));	cell.setValue(sku);//11
					cell = cells.getCell("DM" + Integer.toString(i));	cell.setValue(skuCode);//12
					cell = cells.getCell("DN" + Integer.toString(i));	cell.setValue(ngay);//13
					cell = cells.getCell("DO" + Integer.toString(i));	cell.setValue(amount);//14
					cell = cells.getCell("DP" + Integer.toString(i));	cell.setValue(piece);//15
					cell = cells.getCell("DQ" + Integer.toString(i));	cell.setValue(q);	//16				
					++i;
					
				}

				if (rs != null)
					rs.close();
				
				if(db != null) db.shutDown();
				
				if(i==2){					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}

			   		
			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}		
		return true;
	}
	
	private void CreateHeaderLevelThree(Worksheet worksheet, IStockintransit obj)throws Exception{
		try{
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
		    
		    ReportAPI.getCellStyle(cell,Color.RED, true, 14, "BÁO CÁO DOANH SỐ BÁN HÀNG THEO NGÀY");
		            
		    Utility  util = new  Utility();
			 cells.setRowHeight(3, 18.0f);
			 cell = cells.getCell("A2");
			 ReportAPI.getCellStyle(cell,Color.RED,true, 9, util.setTieuDe(obj));

		    
		 String message = "";
			if(obj.getdiscount().equals("0")){
				message += "Không trừ chiết khấu";

				if(obj.getvat().equals("1")){
					message += ", không VAT";
				}else{
					message += ", có VAT";
				}			
			}else{
				message += "Trừ chiết khấu";
				if(obj.getvat().equals("1")){
					message += ", không VAT";
				}else{
					message +=", có VAT";
				}
			}
			
			cells.setRowHeight(2, 18.0f);
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A4");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("C4"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
			
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A5");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A6");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());

		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A7");
		    getCellStyle(worksheet, "A7" , Color.NAVY,true, 9);
		    cell.setValue("Đơn vị tiền tệ VND");
		    
		    
			
			cell = cells.getCell("DA1");		cell.setValue("KenhBanHang");
			cell = cells.getCell("DB1");		cell.setValue("DonViKinhDoanh");
			cell = cells.getCell("DC1");		cell.setValue("ChiNhanh");
			cell = cells.getCell("DD1");		cell.setValue("KhuVuc");
			cell = cells.getCell("DE1");		cell.setValue("GiamSat");			
			cell = cells.getCell("DF1");		cell.setValue("ChiNhanh/DoiTac");		
			cell = cells.getCell("DG1");		cell.setValue("NhanHang");
			cell = cells.getCell("DH1");		cell.setValue("ChungLoai");
			cell = cells.getCell("DI1");		cell.setValue("MaCN/DT"); //cell.setValue("Smart Id"); 
			cell = cells.getCell("DJ1");		cell.setValue("MaDaiDienKinhDoanh");
			cell = cells.getCell("DK1");		cell.setValue("DaiDienKinhDoanh");
			cell = cells.getCell("DL1");		cell.setValue("TenSanPham");
			cell = cells.getCell("DM1");		cell.setValue("MaSanPham");
			cell = cells.getCell("DN1");		cell.setValue("KhachHang");	
			cell = cells.getCell("DO1");		cell.setValue("ViTriCuaHang");
			cell = cells.getCell("DP1");		cell.setValue("LoaiCuaHang");
			cell = cells.getCell("DQ1");
			if(obj.getFromMonth() != "")
				cell.setValue("Thang");
			else	
				cell.setValue("Ngay");
			cell = cells.getCell("DR1");		cell.setValue("NhomSanPham");
			cell = cells.getCell("DS1");		cell.setValue("NhomKhachHang");
			cell = cells.getCell("DT1");		cell.setValue("SoTien");
			cell = cells.getCell("DU1");		cell.setValue("SoLuong");
			cell = cells.getCell("DV1");		cell.setValue("SoLuong(Kien)");
			cell = cells.getCell("DW1");		cell.setValue("SmartId");
			cell = cells.getCell("DX1");		cell.setValue("MaKhachHang");
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("Xin loi,Khong tao duoc header cho bao cao");
		}
		
	}
	
	private boolean FillDataLevelThree(Worksheet worksheet, IStockintransit obj, String query) throws Exception{
		
		dbutils db = new dbutils();
		Cells cells = worksheet.getCells();		
		ResultSet rs = db.get(query);
		int i = 2;		
		if (rs != null) {
			try {
				Cell cell = null;
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);			
				cells.setColumnWidth(5, 15.0f);	
				cells.setColumnWidth(6, 15.0f);	
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 20.0f);
				cells.setColumnWidth(11, 20.0f);
				cells.setColumnWidth(12, 20.0f);
				cells.setColumnWidth(13, 20.0f);
				cells.setColumnWidth(14, 20.0f);
				cells.setColumnWidth(15, 20.0f);
				cells.setColumnWidth(16, 20.0f);
				cells.setColumnWidth(17, 20.0f);
				cells.setColumnWidth(18, 20.0f);
				cells.setColumnWidth(19, 20.0f);
				cells.setColumnWidth(20, 20.0f);
				cells.setColumnWidth(21, 20.0f);
				cells.setColumnWidth(22, 20.0f);
			
				cell = cells.getCell("Z1"); cell.setValue(3);
				
				while (rs.next()) {					
					String chanel = rs.getString("chanel");
					String bu = rs.getString("bu");
					String region = rs.getString("region");
					String area = rs.getString("area");
					String salesuper = rs.getString("salesuper");
					String distributor = rs.getString("distributor");				
					String salesRep = rs.getString("SalesRep");
					String salesRepId = rs.getString("SalesRepId");					
					String brand = rs.getString("brand");
					String category = rs.getString("category");
					String discode = rs.getString("distcode");
					String sku = rs.getString("SKU");
					String skuCode = rs.getString("SKUcode");					
					String customer = rs.getString("customer");	
					String customer_code = rs.getString("customercode");
					String outlessClass = rs.getString("OutletClass");
					String outlettype = rs.getString("outlettype");
					String offdate =  rs.getString("offdate");
					String groupCustomer = rs.getString("nhomkhachhang");
					String groupSKU = rs.getString("nhomsanpham");
					float amount = rs.getFloat("amount");
					float piece = rs.getFloat("piece");
					float quantity = rs.getFloat("quantity");	
					
					//String smartId = rs.getString("SmartId");
					String smartId = "123456";
					
					cell = cells.getCell("DA" + Integer.toString(i));	cell.setValue(chanel);
					cell = cells.getCell("DB" + Integer.toString(i));	cell.setValue(bu);
					cell = cells.getCell("DC" + Integer.toString(i));	cell.setValue(region);
					cell = cells.getCell("DD" + Integer.toString(i));	cell.setValue(area);
					cell = cells.getCell("DE" + Integer.toString(i));	cell.setValue(salesuper);					
					cell = cells.getCell("DF" + Integer.toString(i));	cell.setValue(distributor);					
					cell = cells.getCell("DG" + Integer.toString(i));	cell.setValue(brand);
					cell = cells.getCell("DH" + Integer.toString(i));	cell.setValue(category);
					cell = cells.getCell("DI" + Integer.toString(i));	cell.setValue(discode);					
					cell = cells.getCell("DJ" + Integer.toString(i));	cell.setValue(salesRepId);
					cell = cells.getCell("DK" + Integer.toString(i));	cell.setValue(salesRep);					
					cell = cells.getCell("DL" + Integer.toString(i));	cell.setValue(sku);
					cell = cells.getCell("DM" + Integer.toString(i));	cell.setValue(skuCode);
					cell = cells.getCell("DN" + Integer.toString(i));	cell.setValue(customer);
					cell = cells.getCell("DO" + Integer.toString(i));	cell.setValue(outlessClass);
					cell = cells.getCell("DP" + Integer.toString(i));	cell.setValue(outlettype);
					cell = cells.getCell("DQ" + Integer.toString(i));	cell.setValue(offdate);
					cell = cells.getCell("DR" + Integer.toString(i));	cell.setValue(groupSKU);
					cell = cells.getCell("DS" + Integer.toString(i));	cell.setValue(groupCustomer);
					cell = cells.getCell("DT" + Integer.toString(i));	cell.setValue(amount);
					cell = cells.getCell("DU" + Integer.toString(i));	cell.setValue(piece);
					cell = cells.getCell("DV" + Integer.toString(i));	cell.setValue(quantity);	
					cell = cells.getCell("DW" + Integer.toString(i));	cell.setValue(smartId);
					cell = cells.getCell("DX" + Integer.toString(i));	cell.setValue(customer_code);	
					++i;					
				}
				if (rs != null)
					rs.close();
				
				if(db != null) 
					db.shutDown();
				
				if(i==2){					
					throw new Exception("Chưa có dữ liệu báo cáo trong thời gian này.");
				}
				
			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}		
		return true;
	}		
	
	private String createQueryBCDoanhSo(String sql, IStockintransit obj,String level) {
		String Distributor = "";
		String	Customer = "";
		/*
		 * Kiem tra ngay bat dau tao bao cao,neu ngay bat dau >2012-04-01 thi lay co giam sat
		 */
		
		 String SalesRep= "";
		 
		 int nam = 0;
		 int thang = 0;
		 if(obj.getFromMonth() != "")
		 {
			 nam = Integer.parseInt(obj.getYear());
			 thang = Integer.parseInt(obj.getFromMonth());
		 }
		 else
		 {
			 nam = Integer.valueOf(obj.gettungay().substring(0,4));
			 thang = Integer.valueOf(obj.gettungay().substring(6,7));
		 }

		 if(obj.getFromMonth().length() <= 0) //xem theo ngay
		 {
					SalesRep =  "SELECT	dvkd.donvikinhdoanh as BU, V.TEN AS REGION, KV.TEN AS AREA, NPP.TEN AS DISTRIBUTOR, NPP.SITECODE AS DISTCODE," +
					  		"NH.TEN AS BRAND,  CL.TEN AS CATEGORY, SP.MA + '_' + SP.TEN AS SKU, SP.MA AS SKUCODE, DDKD.TEN AS SALESREP, DDKD.pk_seq as SALESREPID, KBH.DIENGIAI AS CHANEL," +
					  		"DH.NGAYNHAP AS NGAY, ISNULL(GSBH.TEN, 'Chua xac dinh') AS SALESUPER, sum(DH.SOLUONG) AS PIECE, ";
					if(obj.getdiscount().equals("1"))
						SalesRep+=	" sum(dh.giamua * dh.soluong  / 1.1 *  0.97 ) as amount, ";
					else
						SalesRep+=	" sum(dh.giamua * dh.soluong /1.1  ) as amount, ";
				
					SalesRep += "sum(ISNULL(ROUND((CAST((QC.SOLUONG2 * DH.SOLUONG) AS FLOAT) / CAST(QC.SOLUONG1 AS FLOAT)),3), 0)) AS QUANTITY	FROM( " +
					"SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, " +
					"ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) AS GIAMUA , (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG, " +
					"(-1)*ISNULL(DH_SP1.CHIETKHAU, 0) AS CHIETKHAU  FROM  DONHANGTRAVE DH LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ " +
					"LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  " +
					"WHERE DH.TRANGTHAI = 3 AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' " +
					"UNION ALL SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, DH_SP.SANPHAM_FK, DH_SP.GIAMUA, " +
					"DH_SP.SOLUONG, DH_SP.CHIETKHAU  FROM DONHANG DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK WHERE DH.TRANGTHAI = 1 " +
					"AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' ) DH " +
					"INNER JOIN SANPHAM SP ON DH.SANPHAM_FK = SP.PK_SEQ INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ " +
					"INNER JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ INNER JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ " +
					"INNER JOIN NHANHANG NH ON SP.NHANHANG_FK = NH.PK_SEQ LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ " +
					"INNER JOIN DAIDIENKINHDOANH DDKD ON DH.DDKD_FK = DDKD.PK_SEQ INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK " +
					"inner join kenhbanhang KBH on DH.kbh_fk = KBH.pk_seq " +
					"LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ LEFT JOIN GIAMSATBANHANG GSBH ON DH.GSBH_FK = GSBH.PK_SEQ " + sql +
					"group by dvkd.donvikinhdoanh, V.TEN, KV.TEN,  NPP.TEN, NPP.SITECODE, NH.TEN, CL.TEN, SP.MA + '_' + SP.TEN, SP.MA, DDKD.TEN, DDKD.pk_seq, KBH.DIENGIAI, DH.NGAYNHAP, GSBH.TEN ORDER BY DH.NGAYNHAP";
					
					
					Distributor = "SELECT	dvkd.donvikinhdoanh as BU, V.TEN AS REGION, KV.TEN AS AREA, NPP.TEN AS DISTRIBUTOR, NPP.SITECODE AS DISTCODE, " +
							"NH.TEN AS BRAND, CL.TEN AS CATEGORY, SP.MA + '_' + SP.TEN AS SKU, SP.MA AS SKUCODE, KBH.DIENGIAI AS CHANEL, DH.NGAYNHAP as ngay, SUM(DH.SOLUONG) AS PIECE, ";
					
					if(obj.getdiscount().equals("1"))
						   Distributor += " sum(dh.giamua * dh.soluong  / 1.1 *  0.97  ) as amount, ";
					else
						Distributor += " sum(dh.giamua * dh.soluong /1.1  ) as amount, ";
							
					Distributor += "sum(ISNULL(ROUND((CAST((QC.SOLUONG2 * DH.SOLUONG) AS FLOAT) / CAST(QC.SOLUONG1 AS FLOAT)),3), 0)) AS QUANTITY " +
							"FROM (SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, " +
							"ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) AS GIAMUA , (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG, " +
							"(-1)* ISNULL(DH_SP1.CHIETKHAU, 0) AS CHIETKHAU  FROM  DONHANGTRAVE DH LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ  " +
							"LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  " +
							"WHERE DH.TRANGTHAI = 3 AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' " +
							"UNION ALL SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, DH_SP.SANPHAM_FK, DH_SP.GIAMUA, " +
							"DH_SP.SOLUONG, DH_SP.CHIETKHAU  FROM DONHANG DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK WHERE DH.TRANGTHAI = 1 " +
							"AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' ) DH " +
							"INNER JOIN SANPHAM SP ON DH.SANPHAM_FK = SP.PK_SEQ " +
							"INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ INNER JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ INNER JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ " +
							"INNER JOIN NHANHANG NH ON SP.NHANHANG_FK = NH.PK_SEQ LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ " +
							"inner join kenhbanhang KBH on DH.kbh_fk = KBH.pk_seq " +
							"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ " + sql + 
							"group by dvkd.donvikinhdoanh, V.TEN, KV.TEN, NPP.TEN, NPP.SITECODE, NH.TEN, CL.TEN, SP.MA + '_' + SP.TEN, SP.MA, KBH.DIENGIAI, DH.NGAYNHAP ORDER BY DH.NGAYNHAP";
			
					
					Customer = "SELECT	dvkd.donvikinhdoanh as BU, V.TEN AS REGION, V.pk_seq as regionId, KV.TEN AS AREA, KV.PK_seq as areaId, NPP.TEN AS DISTRIBUTOR, NPP.SITECODE AS DISTCODE, NPP.pk_seq as nppId, " +
							"NH.TEN AS BRAND, NH.PK_SEQ as BRANDID, CL.TEN AS CATEGORY, CL.pk_seq as CATEGORYID, SP.MA + '_' + SP.TEN AS SKU, SP.MA AS SKUCODE, DDKD.TEN AS SALESREP, DDKD.pk_seq as SALESREPID, " +
							"NPP.SITECODE + '_' + KH.TEN + '( ' + KH.DIACHI +  ' )' AS CUSTOMER, KH.PK_SEQ AS CUSTOMERCODE, case when CHARINDEX('_', kh.smartid) > 0 then rtrim(substring(kh.smartid, CHARINDEX('_', kh.smartid)+1, 10)) + npp.sitecode " +
							"when CHARINDEX('_', kh.smartid) <= 0 then kh.smartid + '-' + npp.sitecode end as smartid, " +
							"VTCH.VITRI AS OUTLETPOSITION, ISNULL(M.DIENGIAI, 'Chua Xac Dinh') AS OUTLETTYPE, KBH.DIENGIAI AS CHANEL, KBH.pk_seq as CHANELID, " +
							"DH.NGAYNHAP AS OFFDATE, DH.SOLUONG AS PIECE, ";
					
					 if(obj.getdiscount().equals("1"))
				    	 Customer += " dh.giamua * dh.soluong  / 1.1 *  0.97  as amount, ";
				     else
				    	 Customer += "  dh.giamua * dh.soluong /1.1   as amount, ";
					 
	
					Customer += "ISNULL(GSBH.TEN, 'Chua xac dinh') AS SALESUPER, ISNULL(ROUND((CAST((QC.SOLUONG2 * DH.SOLUONG) AS FLOAT) / CAST(QC.SOLUONG1 AS FLOAT)),3), 0) AS QUANTITY, " +
					"ISNULL(QH.TEN, 'Chua xac dinh') AS TOWN, ISNULL(TT.TEN, 'Chua xac dinh') AS PROVINCE, HCH.DIENGIAI AS OUTLETCLASS, NKH.DIENGIAI AS NHOMKHACHHANG, NSP.DIENGIAI AS NHOMSANPHAM " +
					"FROM( SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, " +
					"ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) AS GIAMUA , (-1)*ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) AS SOLUONG, " +
					"(-1)*ISNULL(DH_SP1.CHIETKHAU, 0) AS CHIETKHAU  FROM  DONHANGTRAVE DH LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ " +
					"LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  " +
					"WHERE DH.TRANGTHAI = 3 AND  DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' " +
					"UNION ALL SELECT  DH.NGAYNHAP, DH.DDKD_FK, DH.KHACHHANG_FK, DH.NPP_FK, DH.GSBH_FK, DH.KHO_FK, DH.KBH_FK, DH_SP.SANPHAM_FK, DH_SP.GIAMUA, " +
					"DH_SP.SOLUONG, DH_SP.CHIETKHAU  FROM DONHANG DH  INNER JOIN DONHANG_SANPHAM  DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK WHERE DH.TRANGTHAI = 1 " +
					"AND DH.NGAYNHAP >='" + obj.gettungay() + "' AND DH.NGAYNHAP <= '" + obj.getdenngay() + "' ) DH INNER JOIN SANPHAM SP ON DH.SANPHAM_FK = SP.PK_SEQ " +
					"INNER JOIN NHAPHANPHOI NPP ON DH.NPP_FK = NPP.PK_SEQ INNER JOIN KHUVUC KV ON NPP.KHUVUC_FK = KV.PK_SEQ INNER JOIN VUNG V ON KV.VUNG_FK = V.PK_SEQ " +
					"INNER JOIN NHANHANG NH ON SP.NHANHANG_FK = NH.PK_SEQ LEFT JOIN CHUNGLOAI CL ON SP.CHUNGLOAI_FK = CL.PK_SEQ " +
					"INNER JOIN DAIDIENKINHDOANH DDKD ON DH.DDKD_FK = DDKD.PK_SEQ INNER JOIN KHACHHANG KH ON DH.KHACHHANG_FK = KH.PK_SEQ " +
					"LEFT JOIN VITRICUAHANG VTCH ON KH.VTCH_FK = VTCH.PK_SEQ INNER JOIN KENHBANHANG KBH ON DH.KBH_FK = KBH.PK_SEQ " +
					"LEFT JOIN LOAICUAHANG M ON KH.LCH_FK = M.PK_SEQ LEFT JOIN GIAMSATBANHANG GSBH ON DH.GSBH_FK = GSBH.PK_SEQ " +
					"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = SP.PK_SEQ " +
					"LEFT JOIN QUANHUYEN QH ON KH.QUANHUYEN_FK = QH.PK_SEQ LEFT JOIN TINHTHANH TT ON KH.TINHTHANH_FK = TT.PK_SEQ " +
					"LEFT JOIN HANGCUAHANG HCH ON KH.HCH_FK = HCH.PK_SEQ LEFT JOIN NHOMKHACHHANG_KHACHHANG NKHKH ON NKHKH.KH_FK = KH.PK_SEQ " +
					"LEFT JOIN NHOMKHACHHANG NKH ON NKH.PK_SEQ = NKHKH.NKH_FK LEFT JOIN ( SELECT SP_FK, MAX(NSP_FK) AS NHOMSP FROM NHOMSANPHAM_SANPHAM  GROUP BY SP_FK ) NSPSP " +
					"ON NSPSP.SP_FK = SP.PK_SEQ LEFT JOIN NHOMSANPHAM NSP ON NSP.PK_SEQ = NSPSP.NHOMSP " + sql + " ORDER BY DH.NGAYNHAP";
					
			  }
			  else //xem theo thang
			  {
				  System.out.println("Xem theo thang...");
				  
				  	geso.traphaco.center.util.Utility utilcenter = new geso.traphaco.center.util.Utility();
					sql = "";
					if (obj.getkenhId().length() > 0)
						sql = sql + " and chanelId ='" + obj.getkenhId() + "'";
					else
						sql = sql + " and chanelId in  " + utilcenter.quyen_kenh(obj.getuserId());
					
					if (obj.getvungId().length() > 0)
						sql = sql + " and regionId ='" + obj.getvungId() + "'";
					if (obj.getkhuvucId().length() > 0)
						sql = sql + " and areaId ='" + obj.getkhuvucId() + "'";			
					if (obj.getnppId().length() > 0)
						sql = sql + " and nppId ='" + obj.getnppId() + "'";
					else
						sql = sql + " and nppId in " + utilcenter.quyen_npp(obj.getuserId());
					
					if (obj.getnhanhangId().length() > 0)
						sql = sql + " and brandId ='" + obj.getnhanhangId() + "'";
					
					if (obj.getchungloaiId().length() > 0)
						sql = sql + " and categoryId ='" + obj.getchungloaiId()	+ "' ";
					
				  
				  SalesRep =  "select BU, region, area, isnull(salesuper, 'Chua xac dinh') as salesuper, SalesRepId, SalesRep, distcode, brand, " +
			  		"category, distributor, SKU, SKUcode, chanel, sum(piece) as piece, sum(quantity) as quantity, thang as offdate, ";
			  
				  if(obj.getdiscount().equals("1")) //co ck
				  {
						if(obj.getvat().equals("1")) //khong VAT
							SalesRep += " sum(amount_CK_YES_VAT_NO) as amount ";
						else
							SalesRep += " sum(amount_CK_YES_VAT_YES) as amount ";
				  }
				  else
				  {
					  if(obj.getvat().equals("1")) //khong VAT
						  SalesRep += " sum(amount_CK_NO_VAT_NO) as amount ";
					  else
						  SalesRep += " sum(amount_CK_NO_VAT_YES) as amount ";
				  }
				  
				  SalesRep += "from doanhsobanhang where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' " + sql + 
					 " group by BU, region, area, salesuper, SalesRepId, SalesRep, distcode, brand, category, distributor, SKU, SKUcode, chanel, thang order by thang";
				
				Distributor = "select BU, region, area, brand, category, distcode, distributor, SKU, SKUcode, chanel, sum(quantity) as quantity, sum(piece) as piece, thang as ngay, ";
				if(obj.getdiscount().equals("1")) //co ck
				{
					if(obj.getvat().equals("1")) //khong VAT
						Distributor += " sum(amount_CK_YES_VAT_NO) as amount ";
					else
						Distributor += " sum(amount_CK_YES_VAT_YES) as amount ";
				}
				else
				{
					if(obj.getvat().equals("1")) //khong VAT
						Distributor += " sum(amount_CK_NO_VAT_NO) as amount ";
					else
						Distributor += " sum(amount_CK_NO_VAT_YES) as amount ";
				}
						
				Distributor += "from doanhsobanhang where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' " + sql + 
					" group by BU, region, area, brand, category, distcode, distributor, SKU, SKUcode, chanel, thang order by thang";
				
				System.out.println("Distributor: " + Distributor);
				
				Customer = "select bu, region, area, distributor, distcode, brand, category, sku, skuCode, salesRep, salesRepId, customer, customercode, smartId, " +
						"outletposition, outlettype, chanel, salesuper, town, province, outletclass, nhomkhachhang, nhomsanpham, thang as offdate, " +
						"sum(piece) as piece, sum(quantity) as quantity, ";
				if(obj.getdiscount().equals("1")) //co ck
				{
					if(obj.getvat().equals("1")) //khong VAT
						Customer += " sum(amount_CK_YES_VAT_NO) as amount from doanhsobanhang where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' " + sql;
					else
						Customer += " sum(amount_CK_YES_VAT_YES) as amount from doanhsobanhang where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' " + sql;
				}
				else
				{
					//Distributor+= "  sum(a.giamua * a.soluong *  " + obj.getvat() +" - a.CHIETKHAU) as amount";
					if(obj.getvat().equals("1")) //khong VAT
						Customer += " sum(amount_CK_NO_VAT_NO) as amount from doanhsobanhang where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' " + sql;
					else
						Customer += " sum(amount_CK_NO_VAT_YES) as amount from doanhsobanhang where thang >= '" + obj.getFromMonth() + "' and thang <= '" + obj.getToMonth() + "' and nam = '" + obj.getYear() + "' " + sql;
				}
				Customer += " group by  bu, region, area, distributor, distcode, brand, category, sku, skuCode, salesRep, salesRepId, customer, customercode, smartId," +
							" outletposition, outlettype, chanel, salesuper, town, province, outletclass, nhomkhachhang, nhomsanpham, thang order by thang";
			  }  
	     
	     String query = "";
	     switch (Integer.parseInt(level)) 
	     {
			case 0:		
				query = Distributor;
				System.out.println("QUERY Doanh So Distributor la: " + query);
				break;
			case 1:	
				query = SalesRep;
				System.out.println("QUERY Doanh So SalesRep la: " + query);
				break;
			case 2:
				query = Customer;
				System.out.println("QUERY Doanh So Customer la: " + query);
				break;
	     }
	    
	     return query;			
		
	}	
	
	private void CreatePivotDailyStock(Worksheet worksheet, IStockintransit obj) throws IOException
    {         
	    CreateHeaderDailyStock(worksheet, obj);
 
	    FillDataDailyStock(worksheet,obj);
   }

	private void CreateHeaderDailyStock(Worksheet worksheet, IStockintransit obj) 
	{

		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		getCellStyle(worksheet,"A1",Color.RED,true,14);
	    cell.setValue("TỒN KHO HÀNG NGÀY");   	
	    
	    
	    Utility  util = new  Utility();
		 cells.setRowHeight(3, 18.0f);
		 cell = cells.getCell("A2");
		 ReportAPI.getCellStyle(cell,Color.RED,true, 9, util.setTieuDe(obj));

	    
	    cells.setRowHeight(2, 20.0f);
	    cell = cells.getCell("A3"); 
	    getCellStyle(worksheet,"A3",Color.NAVY,true,9);	    
	    cell.setValue("Từ ngày: " + obj.gettungay());
	    
	    
	    cells.setRowHeight(3, 20.0f);
	    cell = cells.getCell("B3"); getCellStyle(worksheet,"B3",Color.NAVY,true,9);	        
	    cell.setValue("Đến ngày: " + obj.getdenngay());    
	    
	    cells.setRowHeight(4, 20.0f);
	    cell = cells.getCell("A4");getCellStyle(worksheet,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + obj.getDateTime());
	    
	    cells.setRowHeight(5, 20.0f);
	    cell = cells.getCell("A5");getCellStyle(worksheet,"A5",Color.NAVY,true,9);
	    cell.setValue("Được tạo bởi:  " + obj.getuserTen());
			    
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    getCellStyle(worksheet, "A6" , Color.NAVY,true, 9);
	    cell.setValue("Đơn vị tiền tệ VND");
	    
	    
	    
	    cell = cells.getCell("EA1"); cell.setValue("KenhBanHang");
	    cell = cells.getCell("EB1"); cell.setValue("DonViKinhDoanh");
	    cell = cells.getCell("EC1"); cell.setValue("ChiNhanh");
	    cell = cells.getCell("ED1"); cell.setValue("Vung");
	    cell = cells.getCell("EE1"); cell.setValue("MaCN/DT");
	    cell = cells.getCell("EF1"); cell.setValue("ChiNhanh/DoiTac"); //6
	    cell = cells.getCell("EG1"); cell.setValue("NhanHang");
	    cell = cells.getCell("EH1"); cell.setValue("ChungLoai");
	    cell = cells.getCell("EI1"); cell.setValue("MaSanPham");
	    cell = cells.getCell("EJ1"); cell.setValue("TenSanPham"); //9
	    cell = cells.getCell("EK1"); cell.setValue("Kho");  //10
	    cell = cells.getCell("EL1"); cell.setValue("SoLuong");	//11  
	    cell = cells.getCell("EM1"); cell.setValue("SoLuongKien");	//11
	    cell = cells.getCell("EN1"); cell.setValue("SoTien");
	    cell = cells.getCell("EO1"); cell.setValue("Date");
	   
	}
	
	private void FillDataDailyStock(Worksheet worksheet, IStockintransit obj) 
	{
	    Cells cells = worksheet.getCells();
	    dbutils db = new dbutils();

	    String sql = " select isnull(d.ten, 'Chua xac dinh') as Chanel, h.ten as Region," +
	    		" f.ten as Area, e.ten as Distributor, e.sitecode as Distcode, "+
		   " b.ma  +'_'+  b.ten as SKU, b.ma as SKUcode, a.ngay as Date, c.ten as Warehouse, g.ten as Province, "+
		   " i.donvikinhdoanh as BusinessUnit, k.ten as Brands, j.ten as Category, a.soluong as Piece, "+
		   " case when a.soluong is null then 0 else a.soluong/isnull(qc.soluong1,1) end as Quatity,"+   
		   " case when a.soluong * nppk.giamua is null then 0 else a.soluong * nppk.giamua end as Amount" +
		   " from (" +
		   "select * from tonkhongay " +
			" where ngay >='" + obj.gettungay() + "' and ngay <= '" + obj.getdenngay() + "' and soluong > 0" +
		   ") a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join kho c on a.kho_fk = c.pk_seq " + 
		   " inner join kenhbanhang d on a.kbh_fk = d.pk_seq	inner join nhaphanphoi e on a.npp_fk = e.pk_seq " +
		   " inner join khuvuc f on e.khuvuc_fk = f.pk_seq inner join tinhthanh g on e.tinhthanh_fk = g.pk_seq " +
		   " inner join vung h on f.vung_fk = h.pk_seq inner join donvikinhdoanh i on b.dvkd_fk = i.pk_seq " +
		   " inner join chungloai j on b.chungloai_fk = j.pk_seq inner join nhanhang k on b.nhanhang_fk = k.pk_seq" +
	       " left join ( "+
		   " select distinct bgm.kenh_fk as kbh_fk,bgm_sp.sanpham_fk,bgmnpp.npp_fk,bgm_sp.giamuanpp as giamua from banggiamuanpp_npp bgmnpp "+ 
		   " inner join banggiamuanpp bgm on bgm.pk_seq = bgmnpp.banggiamuanpp_fk "+
		   " inner join bgmuanpp_sanpham bgm_sp on bgm_sp.bgmuanpp_fk = bgm.pk_seq ";
		
	    	if(obj.getnppId().length() > 0)
	    		sql = sql + " where bgmnpp.npp_fk ='"+ obj.getnppId() +"'";
	    
		sql = sql + " ) nppk on " +
        " nppk.npp_fk = a.npp_fk " + 
        " and nppk.sanpham_fk = a.sanpham_fk and nppk.kbh_fk = a.kbh_fk " + 
	    " left join quycach qc on qc.dvdl1_fk = b.dvdl_fk and a.sanpham_fk = qc.sanpham_fk and qc.dvdl2_fk=100018 where 1=1  ";
	    
		geso.traphaco.center.util.Utility Uti_center = new geso.traphaco.center.util.Utility();
		
		if (obj.getkenhId().length() > 0)
			sql = sql + " and d.pk_seq ='" + obj.getkenhId() + "'";
		else
			sql = sql + " and d.pk_seq in " + Uti_center.quyen_kenh(obj.getuserId());
		
		if (obj.getvungId().length() > 0)
			sql = sql + " and h.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and f.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and i.PK_SEQ ='" + obj.getdvkdId() + "'";
		
		if(obj.getnppId().length() > 0)
			sql = sql + " and e.pk_seq ='" + obj.getnppId() + "'";
		else
			sql = sql + " and e.pk_seq in " + Uti_center.quyen_npp(obj.getuserId());
		
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and k.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and j.pk_seq ='" + obj.getchungloaiId() + "'";
		
		sql = sql + " and b.pk_seq in " + Uti_center.quyen_sanpham(obj.getuserId());
	    
	    //String sql = "select * from BCTonKhoNgay";
		System.out.print("Ton kho ngay: " + sql + "\n");
		ResultSet rs = db.get(sql);
		int i = 2;
		 
		Utility util = new Utility();
		if(rs != null)
		{
			try 
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 15.0f);
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
				
				//set do rong cho dong
				for(int j = 1; j <= 7; j++)
					cells.setRowHeight(j, 14.0f);
				
				Cell cell = null;
				long Piece = 0;
				float Amount = 0;
				while(rs.next())
				{ 
					String Channel = rs.getString("Chanel");
					String Region = rs.getString("Region");
					String Area = rs.getString("Area");				
					String Distributor = rs.getString("Distributor");
					String DistributorCode = rs.getString("Distcode");			
					String Sku = rs.getString("SKU");
					String SkuCode = rs.getString("SKUcode");
					String Warehouse = rs.getString("Warehouse");
					String Date= rs.getString("Date");
					
					if(rs.getString("Amount") != null);
					 	Amount = rs.getLong("Amount");
					 	
					if(rs.getString("Piece") != null)
						Piece = rs.getLong("Piece");
					
					String BusinessUnit = "HPC";
					if(rs.getString("BusinessUnit") != null)
						BusinessUnit = rs.getString("BusinessUnit");
					
					String Brands = rs.getString("Brands");
					String Category = rs.getString("Category");
					
					
					
					double Quatity=rs.getDouble("Quatity");
					
					
					cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(Channel); //0
					cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(BusinessUnit); //1
					cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(Region); //2
					cell = cells.getCell("ED" + Integer.toString(i)); cell.setValue(Area);   ///3
					cell = cells.getCell("EE" + Integer.toString(i)); cell.setValue(DistributorCode); //4
					cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(Distributor); //5
					cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(Brands); //6
					cell = cells.getCell("EH" + Integer.toString(i)); cell.setValue(Category); //7
					cell = cells.getCell("EI" + Integer.toString(i)); cell.setValue(SkuCode); //8
					cell = cells.getCell("EJ" + Integer.toString(i)); cell.setValue(Sku); //9
					cell = cells.getCell("EK" + Integer.toString(i)); cell.setValue(Warehouse); //10
					cell = cells.getCell("EL" + Integer.toString(i)); cell.setValue(Piece); //11
					cell = cells.getCell("EM" + Integer.toString(i)); cell.setValue(Quatity); //12
					cell = cells.getCell("EN" + Integer.toString(i)); cell.setValue(Amount); //13
					cell = cells.getCell("EO" + Integer.toString(i)); cell.setValue(Date);	//14
					i++;
				}
			
			if(rs != null) rs.close();
			if(db != null) db.shutDown();
			PivotTables pivotTables = worksheet.getPivotTables();
			
		    //Adding a PivotTable to the worksheet
			String pos = Integer.toString(i-1);	
		    int index = pivotTables.add("=EA1:EO" + pos,"A10","PivotTable4");
		    PivotTable pivotTable = pivotTables.get(index);//truyen index
		    
		    pivotTable.setRowGrand(false);
		    pivotTable.setColumnGrand(true);
		    pivotTable.setAutoFormat(true);
		    
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 0); pivotTable.getRowFields().get(0).setAutoSubtotals(true); 
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 1); pivotTable.getRowFields().get(1).setAutoSubtotals(true);
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 2); pivotTable.getRowFields().get(2).setAutoSubtotals(true);  
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 3); pivotTable.getRowFields().get(3).setAutoSubtotals(true);
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 8); pivotTable.getRowFields().get(4).setAutoSubtotals(false);  
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 9); pivotTable.getRowFields().get(5).setAutoSubtotals(false);  
		    
		    pivotTable.addFieldToArea(PivotFieldType.DATA, 11);   pivotTable.getDataFields().get(0).setDisplayName("SoLuong");pivotTable.getDataFields().get(0).setNumber(3);
		    pivotTable.addFieldToArea(PivotFieldType.DATA, 12);   pivotTable.getDataFields().get(1).setDisplayName("SoLuongKien");pivotTable.getDataFields().get(1).setNumber(3);
		    pivotTable.addFieldToArea(PivotFieldType.DATA, 13);   pivotTable.getDataFields().get(2).setDisplayName("SoTien");pivotTable.getDataFields().get(2).setNumber(3);
		    
		    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
		    pivotTable.getColumnFields().get(0).setDisplayName("Data");
		    
		
			}
			catch (Exception e)
			{
				e.printStackTrace(); 
				System.out.println("Error : loi daily stock : " +e.toString());
			}
		}
	}
	
	private void getCellStyle(Worksheet worksheet, String a, Color mau, Boolean dam, int size)
	{	   	   
	    Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a); 
		 style = cell.getStyle();
	        Font font1 = new Font();
	        font1.setColor(mau);
	        font1.setBold(dam);
	        font1.setSize(size);
	        style.setFont(font1);
	        cell.setStyle(style);
	}
	
}
