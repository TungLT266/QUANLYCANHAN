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
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ThucDatVaChiTieuSKUIn extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    
    public ThucDatVaChiTieuSKUIn() {
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
		obj.setnppId(util.getIdNhapp(obj.getuserId()));
		
		obj.init();
		obj.settype("1");
		session.setAttribute("obj", obj);		
		session.setAttribute("userId", obj.getuserId());
		session.setAttribute("userTen", obj.getuserTen());
		String nextJSP = "/TraphacoHYERP/pages/Center/ThucDatVaChiTieuSKUIn.jsp";
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
		Utility util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		obj.settungay(util.antiSQLInspection(request.getParameter("Sdays")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("Edays")));
		
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(request.getParameter("kenhId")!=null?
				util.antiSQLInspection(request.getParameter("kenhId")):"");
		
		obj.setvungId(request.getParameter("vungId")!=null?
				util.antiSQLInspection(request.getParameter("vungId")):"");
			
		obj.setkhuvucId(request.getParameter("khuvucId")!=null?
				util.antiSQLInspection(request.getParameter("khuvucId")):"");
		
		obj.setgsbhId(request.getParameter("gsbhs")!=null?
				util.antiSQLInspection(request.getParameter("gsbhs")):"");
		
		obj.setnppId(request.getParameter("nppId")!=null?
				util.antiSQLInspection(request.getParameter("nppId")):"");
		
		obj.setdvkdId(request.getParameter("dvkdId")!=null?
				util.antiSQLInspection(request.getParameter("dvkdId")):"");
		
		obj.setDdkd(request.getParameter("ddkdId")!=null?	
				util.antiSQLInspection(request.getParameter("ddkdId")):"");
		
		String nspId = request.getParameter("nspId");
		if(nspId == null)
			nspId = "";
		obj.setNspId(nspId);
		
		System.out.println("Tu thang la :"+request.getParameter("tuthang"));
		String tuthang=request.getParameter("tuthang").length()< 2 ? ("0"+request.getParameter("tuthang")) :request.getParameter("tuthang") ;
		
		String toithang=request.getParameter("denthang").length()< 2 ? ("0"+request.getParameter("denthang")) :request.getParameter("denthang") ;
		obj.setFromMonth(tuthang);
		
		obj.setToMonth(toithang);
			obj.setToYear(request.getParameter("dennam"));
			obj.setFromYear(request.getParameter("tunam"));
		
		 obj.settype(request.getParameter("typeid"));
		
		System.out.println(obj.gettype());
		 //truong hop bao cao thang type la 1.
		String action = request.getParameter("action")!=null? util.antiSQLInspection(request.getParameter("action")):"";
		String nextJSP = "/TraphacoHYERP/pages/Center/ThucDatVaChiTieuSKUIn.jsp";
		
		if(action.equals("Taomoi")){
			try{
		
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ChiTieuSKUIn.xlsm");
		        if(!CreatePivotTable(out,obj)){
		        
		        	System.out.println("Không thể xuất ra báo cáo");
		        	obj.setMsg("Không thể xuất ra báo cáo");
		        }
			}catch(Exception ex){
				obj.setMsg(ex.getMessage());
			}
		}
		obj.init();
		session.setAttribute("obj", obj);	
		response.sendRedirect(nextJSP);
	}
	
	private boolean CreatePivotTable(OutputStream out, IStockintransit obj) throws Exception 
	{
		String chuoi = "";
		
		if(obj.gettype().equals("1"))
	    {
			chuoi = getServletContext().getInitParameter("path") + "\\ChiTieuSKUIn_Thang.xlsm";
	    }
	    else
	    {
	    	if(obj.gettype().equals("0"))
	    	{
	    		chuoi = getServletContext().getInitParameter("path") + "\\ChiTieuSKUIn.xlsm";
	    	}
	    	else
	    	{
	    		chuoi = getServletContext().getInitParameter("path") + "\\ChiTieuSKUIn_Tuan.xlsm";
	    	}	
	    }
		
		FileInputStream fstream = new FileInputStream(chuoi);
			
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj);

		boolean isFill = CreateStaticData(workbook, obj);
		
		if (!isFill){
			fstream.close();
			return false;
		}else {
			workbook.save(out);
			fstream.close();
			return true;
		}
	}

	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) throws Exception 
	{
		try{
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
		    cell.setValue("THỰC ĐẠT VÀ CHỈ TIÊU SKU IN");   	
		    
		    cells.setRowHeight(2, 18.0f);
		    cell = cells.getCell("A3"); 
		    getCellStyle(workbook,"A3",Color.NAVY,true,10);
		    if(obj.gettype().equals("1"))
		    {
		    	 cell.setValue("Từ tháng: " +obj.getFromYear()+"-"+ obj.getFromMonth());  
		    }
		    else
		    {
		    	//if(obj.gettype().equals("0"))
		    		cell.setValue("Từ ngày: " + obj.gettungay());	
		    	//else
		    		//cell.setValue("Từ tuần: " + obj.gettungay());	
		    }
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B3"); 
		    getCellStyle(workbook,"B3",Color.NAVY,true,9);
		    
		   if(obj.gettype().equals("1"))
		   {
				cell.setValue("Đến tháng: " + obj.getToYear() + "-" + obj.getToMonth());
		   }
		   else
		   {
			   //if(obj.gettype().equals("0"))
				   cell.setValue("Đến ngày: " + obj.getdenngay());  
			  // else
				   //cell.setValue("Đến tuần: " + obj.getdenngay());  
		   }
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A6");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
					
		 	String str = "";
		    if(obj.gettype().equals("1"))
		    {
		    	str = "Thang";
		    }
		    else
		    {
		    	if(obj.gettype().equals("0"))
		    		str = "Ngay";
		    	else
		    		str = "Tuan";
		    }
			
		    cell = cells.getCell("DA1"); 	cell.setValue("BM");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DB1"); 	cell.setValue("Mien");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DC1"); 	cell.setValue("ASM");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DD1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DE1"); 	cell.setValue("GiamSatBanHang");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DF1"); 	cell.setValue("MaNhaPhanPhoi");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DG1"); 	cell.setValue("TenNhaPhanPhoi");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DH1"); 	cell.setValue("MaSanPham");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DI1"); 	cell.setValue("TenSanPham");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DJ1"); 	cell.setValue(str);  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DK1"); 	cell.setValue("ChiTieu"); ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DL1"); 	cell.setValue("ThucDat");   ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DM1"); 	cell.setValue("%");	   ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DN1"); 	cell.setValue("Nam");	   ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DO1"); 	cell.setValue("TongGiaTri");	   ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DP1");	cell.setValue("MaGSBH");	ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DQ1");	cell.setValue("MaASM");		ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DR1");	cell.setValue("MaBM");		ReportAPI.setCellHeader(cell);
		}catch(Exception ex){
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
		
	      
	}
	
	private boolean CreateStaticData(Workbook workbook, IStockintransit obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    String thang = obj.getMonth();
	    if(thang.trim().length() < 2)
	    	thang = "0" + thang;
	    
	    String query = "";
	    if(obj.gettype().equals("1"))
	    {
	    	System.out.println("1.Chi tieu SKU Thang : " + query);
	    	query = getQuery_Thang(obj);
	    }
	    else
	    {
	    	
	    	if(obj.gettype().equals("0"))
	    	{
	    		query = getQuery_Ngay(obj);
	    		System.out.println("1.Chi tieu SKU Ngay: " + query);
	    	}
	    	else
	    	{
	    		query = getQuery_Tuan(obj);
	    		System.out.println("1.Chi tieu SKU  Tuan:  " + query);
	    	}
	    }
	    
		String sql =" where 1=1 ";
		if (obj.getkenhId().length() > 0)
			sql = sql + "   and kbh.pk_seq ='" + obj.getkenhId() + "'";
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
		if(obj.getDdkd().length()>0)
			sql+=" and ddkd.pk_Seq ='"+obj.getDdkd()+"'";
	    query+=sql;
	    System.out.println("113.Query "+query);
		ResultSet rs = db.get(query);
		
		int i = 2;
		if(rs!=null)
		{
			try 
			{
				for(int j = 0; j < 15; j++)
					cells.setColumnWidth(i, 15.0f);
				
				Cell cell = null;											       
			       
				while(rs.next())
				{
					String BM = rs.getString("BM");
					String vung = rs.getString("vung");
					String ASM = rs.getString("ASM");
					String khuvuc = rs.getString("khuvuc");
					String gsbh = rs.getString("GSBH");
					String maNPP = rs.getString("ma");
					String tenNPP = rs.getString("ten");
					String maSP = rs.getString("SKUCODE");
					String tenSP = rs.getString("spTen");
					double chitieu = rs.getDouble("chitieu");										
					double thucdat = rs.getDouble("thucdat");
					double phantram = rs.getDouble("phantram");	
            		String ngay = rs.getString("NgayThang");
					String nam = rs.getString("nam");
            		
					cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(BM);
					cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(vung);
					cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(ASM);
					cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(khuvuc);
					cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(gsbh);
					cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(maNPP);				
					cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(tenNPP);
					cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(maSP);
					cell = cells.getCell("DI" + Integer.toString(i)); 	cell.setValue(tenSP);
					cell = cells.getCell("DJ" + Integer.toString(i)); 	cell.setValue(ngay);
					cell = cells.getCell("DK" + Integer.toString(i)); 	cell.setValue(chitieu); 
					cell = cells.getCell("DL" + Integer.toString(i)); 	cell.setValue(thucdat); 
					cell = cells.getCell("DM" + Integer.toString(i)); 	cell.setValue(phantram); 
					cell = cells.getCell("DN" + Integer.toString(i)); 	cell.setValue(nam);
					cell = cells.getCell("DO" + Integer.toString(i)); 	cell.setValue(rs.getDouble("TongGiaTri"));
					cell = cells.getCell("DP" + Integer.toString(i));	cell.setValue(rs.getString("MaGSBH"));
					cell = cells.getCell("DQ" + Integer.toString(i));	cell.setValue(rs.getString("MaASM"));
					cell = cells.getCell("DR" + Integer.toString(i));	cell.setValue(rs.getString("MaBM"));										
					i++;
					/*
					Style style;
				       for(int k=1;k<=18;k++)
				       {
				        cell=cells.getCell(i+13, k);
				        style=cell.getStyle();
				        style.setNumber(3);
				        cell.setStyle(style);				        				      
				       }				   */    				     
				}
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
			
			} 
			catch (Exception e) {
				System.out.println("Errrovvoidid :"+e.toString());
				
			}
		} 
		else 
		{
			if(db != null) db.shutDown();
			return false;
		}
		
		if(db != null) 
			db.shutDown();
		return true;
	}	
	
	private String getQuery_Ngay(IStockintransit obj)
	{
		String fromMonth = obj.gettungay().substring(5, 7);
		String fromYear = obj.gettungay().substring(0, 4);
		String toMonth = obj.getdenngay().substring(5, 7);
		String toYear = obj.getdenngay().substring(0, 4);
		
		String query = "select CHITIEU.*, isnull(SKUIN.soluongNhap, 0) as THUCDAT , isnull(SKUIN.tongtien,0) as TongGiaTri , cast( (isnull(SKUIN.soluongNhap, 0) * 100 / CHITIEU.chitieu) as numeric(18, 0) )  as PhanTram, " +
						"ISNULL('5'+cast(GIAMSATBANHANG.PK_SEQ as nvarchar(10)),'') AS MaGSBH,isnull(GIAMSATBANHANG.TEN, 'na') as GSBH,ISNULL('7'+cast(ASM.PK_SEQ as nvarchar(10)),'') AS MaASM ,isnull(ASM.ten, 'na') as ASM,ISNULL('9'+cast(BM.PK_SEQ as nvarchar(10)),'') as MaBM ,isnull(BM.ten, 'na') as BM, " +
						"VUNG.TEN as vung, KHUVUC.ten as khuvuc, SKUIN.NgayThang as NgayThang " +
				"from " +
					"( " +
					"  select a.pk_seq as nppId, a.ma, a.ten, a.diachi, a.khuvuc_fk,  "+
					"  b.chitieu, d.ma as SKUCode, d.ten as spTen, d.pk_seq as spId, c.thang, c.nam " +
					"  from nhaphanphoi a inner join CHITIEUSKUIN_SKU b on a.pk_seq = b.npp_fk " +
					"  inner join CHITIEUSKUIN c on b.chitieuskuin_fk = c.pk_seq inner join sanpham d on b.sku = d.pk_seq "+ 
					" 	where '"+fromMonth+"' <= c.thang and c.thang <= '"+toMonth+"' and '"+fromYear+"' <= c.nam and c.nam <= '"+toYear+"' ";			
						if(obj.getnppId().length() > 0)		
							query +=	" and a.pk_seq in (" + obj.getnppId() + ") ";
						
				query += ") CHITIEU " +
				"inner join " +
					"( " +
					"select a.npp_fk, b.sanpham_fk as SKUCode, a.ngaychungtu as NgayThang, substring(a.ngaychungtu, 0, 5) as nam, substring(ngaychungtu, 6, 2) as thang, "+ 
					"sum(b.soluong)  as soluongNhap,sum(b.soluong* BG.GIAMUANPP) as tongtien "+   
					"from nhaphang a    inner join nhaphang_sp b on a.pk_seq = b.nhaphang_fk "+
					"inner join sanpham c on b.sanpham_fk = c.ma "+ 
					"	LEFT JOIN ( "+ 
					"	select BGSP.SANPHAM_FK,BGSP.GIAMUANPP  from  BANGGIAMUANPP bg "+ 
					"	inner join BGMUANPP_SANPHAM BGSP on bg.PK_SEQ=BGSP.BGMUANPP_FK "+ 
					"	) BG ON BG.SANPHAM_FK=C.PK_SEQ "+ 
					" where '" + obj.gettungay() + "' <= a.ngaychungtu and a.ngaychungtu <= '" + obj.getdenngay() + "' and a.trangthai != '2'   ";
				
				if(obj.getnppId().length() > 0)		
					query +=	" and a.npp_fk in (" + obj.getnppId() + ") ";
				
				if(obj.getNspId().length() > 0)
					query += " and b.sanpham_fk in " +
							"( select ma from sanpham " +
							" where pk_seq in (select sp_fk from nhomsanpham_sanpham where nsp_fk = '" + obj.getNspId() + "' ) ) ";
				
				query += " group by a.npp_fk, b.sanpham_fk, substring(a.ngaychungtu, 0, 5), substring(ngaychungtu, 6, 2), a.ngaychungtu" +
					") SKUIN on CHITIEU.nppId = SKUIN.npp_fk and CHITIEU.SKUCode = SKUIN.SKUCode  and CHITIEU.nam = SKUIN.nam and CHITIEU.thang = SKUIN.thang " +
				"inner join nhapp_giamsatbh on nhapp_giamsatbh.npp_fk = CHITIEU.nppId " +
				"inner join  giamsatbanhang on nhapp_giamsatbh.gsbh_fk = giamsatbanhang.pk_seq ";
				
				if(obj.getgsbhId().length() > 0)
				query += " and GIAMSATBANHANG.PK_SEQ = '"+ obj.getgsbhId() +"' ";
				
				query +="inner join KHUVUC on CHITIEU.khuvuc_fk = KHUVUC.pk_seq " +
				"left join ASM_KHUVUC on ASM_KHUVUC.khuvuc_fk = KHUVUC.pk_seq " +
				"left join ASM on ASM_KHUVUC.asm_fk = ASM.pk_seq " +
				"inner join VUNG on khuvuc.vung_fk = VUNG.pk_seq " +
				"left join BM_CHINHANH on BM_CHINHANH.VUNG_FK = VUNG.pk_seq " +
				"left join BM on BM_CHINHANH.BM_fk = BM.PK_SEQ " ;
			return query;
	}

	private String getQuery_Thang(IStockintransit obj)
	{
		String query = "select CHITIEU.*, isnull(SKUIN.soluongNhap, 0) as THUCDAT,isnull(SKUIN.tongtien,0) as TongGiaTri , cast( (isnull(SKUIN.soluongNhap, 0) * 100 / CHITIEU.chitieu) as numeric(18, 0) )  as PhanTram, " +
						"ISNULL('5'+cast(GIAMSATBANHANG.PK_SEQ as nvarchar(10)),'') AS MaGSBH ,isnull(GIAMSATBANHANG.TEN, 'na') as GSBH, ISNULL('7'+cast(ASM.PK_SEQ as nvarchar(10)),'') AS MaASM ,isnull(ASM.ten, 'na') as ASM,ISNULL('9'+cast(BM.PK_SEQ as nvarchar(10)),'') as MaBM ,isnull(BM.ten, 'na') as BM, " +
						"VUNG.TEN as vung, KHUVUC.ten as khuvuc " +
				"from " +
					"( " +
					" select a.pk_seq as nppId, a.ma, a.ten, a.diachi, a.khuvuc_fk,  "+
					"  b.chitieu, d.ma as SKUCode, d.ten as spTen, d.pk_seq as spId, c.thang as ngaythang, c.nam from nhaphanphoi a inner join CHITIEUSKUIN_SKU b on a.pk_seq "+
					" = b.npp_fk  inner join CHITIEUSKUIN c on b.chitieuskuin_fk = c.pk_seq inner join sanpham d on b.sku = d.pk_seq "+ 
					" 	where '"+obj.getFromMonth()+"' <= c.thang and c.thang <= '"+obj.getToMonth()+"' and '"+obj.getFromYear()+"' <= c.nam and c.nam <= '"+obj.getToYear()+"' ";			
		
						if(obj.getnppId().length() > 0)		
							query +=	" and a.pk_seq in (" + obj.getnppId() + ") ";
						
			query += ") CHITIEU " +
				"inner join " +
					"( " +
						"select a.npp_fk, b.sanpham_fk as SKUCode,sum(b.soluong) as soluongNhap ,sum(b.soluong*BG.GIAMUANPP) as tongtien " +
						"from nhaphang a inner join nhaphang_sp b on a.pk_seq = b.nhaphang_fk " +
										"inner join sanpham c on b.sanpham_fk = c.ma " +
										"	LEFT JOIN ( "+ 
										"	select BGSP.SANPHAM_FK,BGSP.GIAMUANPP  from  BANGGIAMUANPP bg "+ 
										"	inner join BGMUANPP_SANPHAM BGSP on bg.PK_SEQ=BGSP.BGMUANPP_FK "+ 
										"	) BG ON BG.SANPHAM_FK=C.PK_SEQ "+ 
						"where '" + obj.getFromYear() + "' <= substring(a.ngaychungtu, 0, 5) and substring(a.ngaychungtu, 0, 5) <= '" + obj.getToYear() + "' and " +
								" '" + obj.getFromMonth() + "' <= substring(ngaychungtu, 6, 2) and substring(ngaychungtu, 6, 2) <= '" + obj.getToMonth() + "' and a.trangthai != '2'   ";
				
				if(obj.getnppId().length() > 0)		
					query +=	" and a.npp_fk in (" + obj.getnppId() + ") ";
				
				if(obj.getNspId().length() > 0)
					query += " and b.sanpham_fk in " +
							"( select ma from sanpham " +
							" where pk_seq in (select sp_fk from nhomsanpham_sanpham where nsp_fk = '" + obj.getNspId() + "' ) ) ";
				
				query += " group by a.npp_fk, b.sanpham_fk  " +
					") SKUIN on CHITIEU.nppId = SKUIN.npp_fk and CHITIEU.SKUCode = SKUIN.SKUCode " +
				"inner join nhapp_giamsatbh on nhapp_giamsatbh.npp_fk = CHITIEU.nppId " +
				"inner join  giamsatbanhang on nhapp_giamsatbh.gsbh_fk = giamsatbanhang.pk_seq ";
				
				if(obj.getgsbhId().length() > 0)
					query += " and GIAMSATBANHANG.PK_SEQ = '"+ obj.getgsbhId() +"' ";
				
				query += "inner join KHUVUC on CHITIEU.khuvuc_fk = KHUVUC.pk_seq " +
				"left join ASM_KHUVUC on ASM_KHUVUC.khuvuc_fk = KHUVUC.pk_seq " +
				"left join ASM on ASM_KHUVUC.asm_fk = ASM.pk_seq " +
				"inner join VUNG on khuvuc.vung_fk = VUNG.pk_seq " +
				"left join BM_CHINHANH on BM_CHINHANH.VUNG_FK = VUNG.pk_seq " +
				"left join BM on BM_CHINHANH.BM_fk = BM.PK_SEQ " ;
				System.out.println("SKU theo tháng: " + query);
		return query;
	}
	
	private String getQuery_Tuan(IStockintransit obj)
	{
		String fromMonth = obj.gettungay().substring(5, 7);
		String fromYear = obj.gettungay().substring(0, 4);
		String toMonth = obj.getdenngay().substring(5, 7);
		String toYear = obj.getdenngay().substring(0, 4);
		
		String query = "select CHITIEU.*, isnull(SKUIN.soluongNhap, 0) as THUCDAT,isnull(SKUIN.tongtien,0) as TongGiaTri, cast( (isnull(SKUIN.soluongNhap, 0) * 100 / CHITIEU.chitieu) as numeric(18, 0) )  as PhanTram, " +
						"ISNULL('7'+cast(ASM.PK_SEQ as nvarchar(10)),'') AS MaASM, ISNULL('5'+cast(GIAMSATBANHANG.PK_SEQ as nvarchar(10)),'') AS MaGSBH, isnull(GIAMSATBANHANG.TEN, 'na') as GSBH,ISNULL('7'+cast(ASM.PK_SEQ as nvarchar(10)),'') AS MaASM ,isnull(ASM.ten, 'na') as ASM,ISNULL('9'+cast(BM.PK_SEQ as nvarchar(10)),'') as MaBM ,isnull(BM.ten, 'na') as BM, " +
						"VUNG.TEN as vung, KHUVUC.ten as khuvuc, SKUIN.NgayThang as NgayThang " +
				"from " +
					"( " +
					
					
					" select a.pk_seq as nppId, a.ma, a.ten, a.diachi, a.khuvuc_fk,  "+
					"  b.chitieu, d.ma as SKUCode, d.ten as spTen, d.pk_seq as spId, c.thang, c.nam from nhaphanphoi a inner join CHITIEUSKUIN_SKU b on a.pk_seq "+
					" = b.npp_fk  inner join CHITIEUSKUIN c on b.chitieuskuin_fk = c.pk_seq inner join sanpham d on b.sku = d.pk_seq "+ 
					" 	where '"+fromMonth+"' <= c.thang and c.thang <= '"+toMonth+"' and '"+fromYear+"' <= c.nam and c.nam <= '"+toYear+"' ";			

									
						if(obj.getnppId().length() > 0)		
							query +=	" and a.pk_seq in (" + obj.getnppId() + ") ";
						
				query += ") CHITIEU " +
				"inner join " +
					"( " +
						"select a.npp_fk, b.sanpham_fk as SKUCode, DATEPART(wk, a.ngaychungtu) as NgayThang, substring(a.ngaychungtu, 0, 5) as nam, substring(ngaychungtu, 6, 2) as thang, " +
						"	(sum(b.soluong) * d.soluong1) / d.soluong2  as soluongNhap ,sum(b.soluong* bG.GIAMUANPP) as tongtien   " +
						" from nhaphang a inner join nhaphang_sp b on a.pk_seq = b.nhaphang_fk " +
										" inner join sanpham c on b.sanpham_fk = c.ma inner join quycach d on c.pk_seq = d.sanpham_fk " +
										"	LEFT JOIN ( "+ 
										"	select BGSP.SANPHAM_FK,BGSP.GIAMUANPP  from  BANGGIAMUANPP bg "+ 
										"	inner join BGMUANPP_SANPHAM BGSP on bg.PK_SEQ=BGSP.BGMUANPP_FK "+ 
										"	) BG ON BG.SANPHAM_FK=C.PK_SEQ "+ 
						" where '" + obj.gettungay() + "' <= a.ngaychungtu and a.ngaychungtu <= '" + obj.getdenngay() + "' and a.trangthai != '2'   ";
				
				if(obj.getnppId().length() > 0)		
					query +=	" and a.npp_fk in (" + obj.getnppId() + ") ";
				
				if(obj.getNspId().length() > 0)
					query += " and b.sanpham_fk in " +
							"( select ma from sanpham " +
							" where pk_seq in (select sp_fk from nhomsanpham_sanpham where nsp_fk = '" + obj.getNspId() + "' ) ) ";
				
				query += " group by a.npp_fk, b.sanpham_fk, substring(a.ngaychungtu, 0, 5), substring(ngaychungtu, 6, 2), DATEPART(wk, a.ngaychungtu), d.soluong1, d.soluong2 " +
					") SKUIN on CHITIEU.nppId = SKUIN.npp_fk and CHITIEU.SKUCode = SKUIN.SKUCode  and CHITIEU.nam = SKUIN.nam and CHITIEU.thang = SKUIN.thang " +
				"inner join nhapp_giamsatbh on nhapp_giamsatbh.npp_fk = CHITIEU.nppId " +
				"inner join  giamsatbanhang on nhapp_giamsatbh.gsbh_fk = giamsatbanhang.pk_seq ";
				
				if(obj.getgsbhId().length() > 0)
					query += " and GIAMSATBANHANG.PK_SEQ = '"+ obj.getgsbhId() +"' ";
				
				query += "inner join KHUVUC on CHITIEU.khuvuc_fk = KHUVUC.pk_seq " +
				"left join ASM_KHUVUC on ASM_KHUVUC.khuvuc_fk = KHUVUC.pk_seq " +
				"left join ASM on ASM_KHUVUC.asm_fk = ASM.pk_seq " +
				"inner join VUNG on khuvuc.vung_fk = VUNG.pk_seq " +
				"left join BM_CHINHANH on BM_CHINHANH.VUNG_FK = VUNG.pk_seq " +
				"left join BM on BM_CHINHANH.BM_fk = BM.PK_SEQ " ;
				System.out.println("SKU theo tuần: "+query);
			return query;
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
	        cell.setStyle(style);
	}

}
