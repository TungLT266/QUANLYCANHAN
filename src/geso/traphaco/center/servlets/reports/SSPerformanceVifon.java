/*package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.aspose.cells.Style;


import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class SSPerformanceOneOne extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    public SSPerformanceOneOne() {
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
		String nextJSP = "/TraphacoHYERP/pages/Center/SSPerformanceOneOne.jsp";
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
		 obj.setgsbhId(util.antiSQLInspection(request.getParameter("gsbhId"))==null? "":util.antiSQLInspection(request.getParameter("gsbhId")));
		 
		 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien);		 
	 
		 
		String nextJSP = "/TraphacoHYERP/pages/Center/SSPerformanceOneOne.jsp";		 
		
			String action=util.antiSQLInspection(request.getParameter("action"));
			if(action.equals("Taomoi")){
				try{
					response.setContentType("application/xlsm");
			        response.setHeader("Content-Disposition", 
			        		"attachment; filename=ThucHienChiTieuSS_"+util.setTieuDe(obj)+".xlsm");
			        OutputStream out = response.getOutputStream();
			        
			        if(!obj.getYear().equals("2014"))
			        {
				        String query = setQuery(obj);
				        CreatePivotTable(out,obj,query);
			        }else
			        {
			        	String query = setQuery_2014(obj);
				        CreatePivotTable_2014(out,obj,query);
			        }
				}catch(Exception ex)
				{
					obj.init();	    
					session.setAttribute("obj", obj);
					session.setAttribute("userId", obj.getuserId());		
					response.sendRedirect(nextJSP);
				}
			}else{
					obj.init();	    
					session.setAttribute("obj", obj);
					session.setAttribute("userId", obj.getuserId());		
					response.sendRedirect(nextJSP);
			}
		
		
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	private String setQuery_2014(IStockintransit obj)
	{
		String 
		query =
				"select v.TEN as vungTen,kv.TEN as kvTen ,gsbh.MANHANVIEN as gsbhMa,gsbh.TEN as gsbhTen, " +
				" 	ct.soNgayLamViec,ct.chitieu as ctSec, " +
				" 		dssec.doanhso as tdSec, dssec.SoDonHang,dssec.SoNhanVien,dssec.soSKU,ctpri.chitieu as ctPri,ds_pri as tdPri " +
				" from        " +
				" (       " +
				" 	select	ctnpp.kenh_fk as kbh_fk, ctnpp.dvkd_fk, kv.khuvuc_fk as kvid, gsbh.pk_seq as gsbhid,sum(ct_gs.chitieu) as chitieu , " +
				" 		sum(ctnpp.SONGAYLAMVIEC) as soNgayLamViec " +
				" 		from chitieu_sec ctnpp inner join chitieusec_gsbh ct_gs on ctnpp.pk_seq =     ct_gs.chitieusec_fk   	       " +
				" 		inner join giamsatbanhang gsbh on gsbh.pk_seq = ct_gs.gsbh_fk   and gsbh.dvkd_fk = ctnpp.dvkd_fk  " +
				" 		inner join gsbh_khuvuc kv on    kv.gsbh_fk = gsbh.pk_seq 	   " +
				" 	where ctnpp.trangthai=1  and ctnpp.thang ="+obj.getMonth()+" and ctnpp.nam ="+obj.getYear()+"   " +
				" 	group by  ctnpp.kenh_fk , ctnpp.dvkd_fk, kv.khuvuc_fk, gsbh.pk_seq    " +
				" ) as ct        " +
				" full outer join        " +
				" (       " +
				" 	select	ct.kenh_fk as kbh_fk, ct.dvkd_fk,  " +
				" 		gsbh.pk_seq as gsbhid,sum(ctnpp.chitieu) as chitieu,kv.KHUVUC_FK	       " +
				" 	from chitieu ct						       " +
				" 		inner join chitieu_gsbh ctnpp on ct.pk_seq = ctnpp.chitieu_fk         " +
				" 		inner join giamsatbanhang gsbh on gsbh.pk_seq = ctnpp.gsbh_fk     " +
				" 		inner join gsbh_khuvuc kv on    kv.gsbh_fk = gsbh.pk_seq 	     " +
				" 	where ct.trangthai=1 and  ct.thang ='"+obj.getMonth()+"' and ct.nam ="+obj.getYear()+" 		 " +
				" 	GROUP BY  CT.KENH_FK , CT.DVKD_FK, GSBH.PK_SEQ,kv.KHUVUC_FK					       " +
				" ) ctpri on ct.dvkd_fk=ctpri.dvkd_fk and ct.gsbhid=ctpri.gsbhid and ct.kbh_fk=ctpri.kbh_fk   and ct.kvid=ctpri.KHUVUC_FK       " +
				" left join       " +
				" (       " +
				"    select npp.khuvuc_fk, dh.kbh_fk, sp2.dvkd_fk as dvkd_fk, dh.gsbh_fk as gsbhid,         " +
				" 		sum(dh_sp.soluong * dh_sp.giamua/1.1*0.965) as doanhso ,COUNT(dh_sp.sanpham_fk) as soSKU, " +
				"  		COUNT(distinct dh_sp.DONHANG_FK) as SoDonHang,COUNT(distinct dh.ddkd_fk)as SoNhanVien " +
				"    from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq = dh_sp.donhang_fk  	      " +
				" 	 inner join sanpham sp2 on sp2.pk_seq = dh_sp.sanpham_fk	      " +
				" 	 inner join nhaphanphoi npp on npp.pk_seq=dh.npp_fk      " +
				"    where substring(dh.ngaynhap, 1 , 7)  = '"+obj.getYear()+"-"+obj.getMonth()+"' and dh.trangthai ='1'  " +
				" 	and dh.pk_seq not in(select donhang_fk from donhangtrave where trangthai=3 and donhang_fk is not null)      " +
				"    group by dh.kbh_fk, sp2.dvkd_fk, dh.gsbh_fk,npp.khuvuc_fk  " +
				" ) as dssec on dssec.dvkd_fk=  isnull(ct.dvkd_fk,ctPri.dvkd_fk)   " +
				" 	and dssec.gsbhid= isnull(ct.gsbhid,ctPri.gsbhid) and isnull(ct.kbh_fk,ctpri.kbh_fk)=dssec.kbh_fk   " +
				" 	and dssec.khuvuc_fk =isnull(ct.kvid,ctpri.KHUVUC_FK)      " +
				" left join   " +
				" (      " +
				"   select npp.khuvuc_fk,  nh.kbh_fk, sp2.dvkd_fk as dvkd_fk, nh.gsbh_fk as gsbhid,       " +
				" 		sum(nh_sp.soluong * nh_sp.gianet-isnull(nh_sp.cktt,0)) as ds_pri       				" +
				"   from nhaphang nh inner join nhaphang_sp nh_sp on nh.pk_seq = nh_sp.nhaphang_fk				      " +
				" 	inner join sanpham sp2 on sp2.ma = nh_sp.sanpham_fk	      " +
				" left join nhaphanphoi npp on npp.pk_seq=nh.npp_fk       " +
				"   where substring(nh.ngaychungtu,1,7)='"+obj.getYear()+"-"+obj.getMonth()+"' and nh.trangthai !='2'  " +
				" 	and nh.loaihoadon=0 and nh.pk_seq not in(select nhaphang_fk from huynhaphang where trangthai=1 )      " +
				"   group by nh.kbh_fk, sp2.dvkd_fk, nh.gsbh_fk ,npp.khuvuc_fk      " +
				" ) dspri on dspri.dvkd_fk=isnull(ct.dvkd_fk,ctpri.DVKD_FK)  and dspri.gsbhid=isnull(ct.gsbhid,ctpri.gsbhid)  " +
				" 	and dspri.kbh_fk=isnull(ct.kbh_fk,ctpri.kbh_fk)      " +
				" and ct.kvid=dspri.khuvuc_fk       " +
				" left join khuvuc kv on kv.pk_seq= isnull(ct.kvid ,ctpri.KHUVUC_FK)        " +
				" left join VUNG v on v.PK_SEQ=kv.VUNG_FK " +
				" left join giamsatbanhang gsbh on gsbh.pk_seq=isnull(ct.gsbhid,ctpri.gsbhid) " +
				" where 1=1 " ;
				  
		if(obj.getkenhId().length() > 0)
			query += " and ct.kbh_fk ='"+obj.getkenhId()+"'";

		if(obj.getvungId().length() > 0)
			query += " and kv.vung_fk = '"+obj.getvungId()+"'";
		if(obj.getdvkdId().length() > 0)
			query += " and ct.dvkd_fk = '"+obj.getdvkdId()+"'";
		if(obj.getkhuvucId().length() > 0)
			query += " and kv.pk_seq = '"+obj.getkhuvucId()+"'";
		
		if(obj.getgsbhId().length() > 0)
			query += " and GSBH.pk_seq = '"+obj.getgsbhId()+"'";
		
		query=query+ query;

		System.out.println("[Query]"+query);
		return query;
	}

	private void CreatePivotTable_2014(OutputStream out, IStockintransit obj, String query) throws Exception
	{
		try
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\SSPerformanceOneOne_2014.xlsm";
			FileInputStream fstream = new FileInputStream(chuoi);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			CreateStaticHeader_2014(workbook, obj);
			FillData_2014(workbook, obj.getFieldShow(), query, obj);
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	
	private void CreateStaticHeader_2014(Workbook workbook, IStockintransit obj)
	{
		Hashtable<Integer, String> htb = this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		Style style;
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("N1");
		style = cell.getStyle();

		cell.setStyle(style);

		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("A3");

		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tháng : " + obj.getMonth() + "");

		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("B3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Năm : " + obj.getYear() + "");

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));

		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A5");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());

		int i = 1;
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Vung\\Mien");
		cell.setStyle(style);
		i++;
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Khu Vuc");
		cell.setStyle(style);
		i++;
		
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Ma Nhan Vien");
		cell.setStyle(style);
		i++;
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Ten Nhan Vien");
		cell.setStyle(style);
		i++;
		
		
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Tong Thuong(1 + 2 + 3 + 4 )");
		cell.setStyle(style);
		i++;
		
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Thuong Sales Out (1)");
		cell.setStyle(style);
		i = i + 1;
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Thuong Sales In (2)");
		cell.setStyle(style);
		i = i + 1;
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Thuong KPI Don Hang (3)");
		cell.setStyle(style);
		i = i + 1;
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Thuong KPI So SKU (4)");
		cell.setStyle(style);
		i = i + 1;
		

		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Chi Tieu Sales Out");
		cell.setStyle(style);
		i = i + 1;
		
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Thuc Dat Sales Out");
		cell.setStyle(style);
		i = i + 1;
		
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("%Thuc Dat Sales Out");
		cell.setStyle(style);
		i = i + 1;
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Chi Tieu Sales In");
		cell.setStyle(style);
		i = i + 1;
		
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Thuc Dat Sales In");
		cell.setStyle(style);
		i = i + 1;
		
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("%Thuc Dat Sales In");
		cell.setStyle(style);
		i = i + 1;
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Binh Quan Don Hang");
		cell.setStyle(style);
		i = i + 1;
		
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("Binh Quan So SKU");
		cell.setStyle(style);
		i = i + 1;
		
		cell = cells.getCell(htb.get(i) + "2");
		cell.setValue("So Nhan Vien Quan Ly");
		cell.setStyle(style);
		i = i + 1;
		
	
		cell = cells.getCell("M1");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, (26 * 4 + i - 1) + "");
	}
	
	
	private void FillData_2014(Workbook workbook, String[] fieldShow, String query, IStockintransit obj) throws Exception
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		dbutils db = new dbutils();

		Cell cell1 = cells.getCell("M1");
		Style style;
		style = cell1.getStyle();
		
		
		 * Tu Dong Chen Giam Sat Cho nhung nhap hang ma chua co gs,de tinh ra thuc 
		 * dat PRI chinh xac
		 
		String sql =
			"update NHAPHANG set GSBH_FK= "+ 
			"	( "+
			"		SELECT TOP(1) A.GSBH_FK "+ 
			"		FROM NHAPP_GIAMSATBH A INNER JOIN GIAMSATBANHANG B ON B.PK_SEQ=A.GSBH_FK "+
			"		WHERE NPP_FK=NH.NPP_FK AND  "+
			"		A.NGAYBATDAU <='"+getDateTime()+"' AND A.NGAYKETTHUC >='"+getDateTime()+"' AND  B.TRANGTHAI=1 "+ 
			"		ORDER BY A.NGAYBATDAU DESC "+ 
			"	)  "+
			"	FROM NHAPHANG NH "+
			"	WHERE GSBH_FK IS NULL AND TRANGTHAI=0 ";
			db.update(sql);

		ResultSet rs = db.get(query);

		int indexRow = 3;
		try
		{
			Cell cell = null;
			double thuongSKU = 0;
			double thuongsec = 0;
			double thuongPri=0;
			double thuongvuot=0;
			double Tu =0;
			double thuongSoDonHang = 0;
			while (rs.next())
			{
				thuongsec=0;
				thuongPri=0;
				thuongSKU=0;
				thuongSoDonHang=0;
							
				
				cell = cells.getCell("DA" + Integer.toString(indexRow)); cell.setValue(rs.getString("VungTen")); cell.setStyle(style);
				cell = cells.getCell("DB" + Integer.toString(indexRow)); cell.setValue(rs.getString("kvTen")); cell.setStyle(style);
				cell = cells.getCell("DC" + Integer.toString(indexRow)); cell.setValue(rs.getString("gsbhMa")); cell.setStyle(style);
				cell = cells.getCell("DD" + Integer.toString(indexRow)); cell.setValue(rs.getString("gsbhTen")); cell.setStyle(style);
				
				
				double tongthuong = thuongPri+thuongsec+thuongSKU+thuongSoDonHang;
				cell = cells.getCell("DE" + Integer.toString(indexRow)); cell.setValue(tongthuong); cell.setStyle(style);
				cell = cells.getCell("DF" + Integer.toString(indexRow)); cell.setValue(thuongsec); cell.setStyle(style);
				cell = cells.getCell("DG" + Integer.toString(indexRow)); cell.setValue(thuongPri); cell.setStyle(style);
				cell = cells.getCell("DH" + Integer.toString(indexRow)); cell.setValue(thuongSoDonHang); cell.setStyle(style);
				cell = cells.getCell("DI" + Integer.toString(indexRow)); cell.setValue(thuongSKU); cell.setStyle(style);
				
				
				
				*//**************************************************Thuong Sales Out (Second)*************************************//*
				cell = cells.getCell("DJ" + Integer.toString(indexRow)); cell.setValue(rs.getDouble("ctSec")); cell.setStyle(style);
				cell = cells.getCell("DK" + Integer.toString(indexRow)); cell.setValue(rs.getDouble("tdSec")); cell.setStyle(style);
				float PhanTramDoanhSo = 0;
				if (rs.getFloat("ctSec") > 0 && rs.getFloat("tdSec")>0)
				{
					PhanTramDoanhSo = 100 * rs.getFloat("tdSec") / (rs.getFloat("ctSec"));
				}
				cell = cells.getCell("DL" + Integer.toString(indexRow));cell.setValue(PhanTramDoanhSo);cell.setStyle(style);
				
				sql = " SELECT B.Thuong,b.ThuongVuot,B.Tu,B.Den  FROM TIEUCHITINHTHUONG A INNER JOIN TIEUCHITINHTHUONG_CHITIET B  " +
						" ON A.PK_SEQ=B.TIEUCHITINHTHUONG_FK " + 
						" WHERE A.LOAI=2 AND THANG=" + obj.getMonth()+ " AND NAM=" + obj.getYear() + " AND B.TIEUCHI=2 AND B.Tu <= " + PhanTramDoanhSo + " AND B.Den >" + PhanTramDoanhSo;

				ResultSet rsthuong = db.get(sql);
				 thuongsec = 0;
				 thuongvuot=0;
				 Tu =0;
				 
				// System.out.println("[Thuong Sales Out (Second)]"+sql);
				 
				if (rsthuong != null)
				{
					while (rsthuong.next())
					{
						thuongsec =  rsthuong.getDouble("thuong");
						thuongvuot = rsthuong.getDouble("ThuongVuot");
						Tu = rsthuong.getDouble("Tu");
					}
					rsthuong.close();
				}
				
				if(PhanTramDoanhSo>0&&PhanTramDoanhSo>=Tu)
				{
					double Vuot =(PhanTramDoanhSo-Tu)<0?0:(PhanTramDoanhSo-Tu);
				//	System.out.println("[PhanTramDoanhSo]"+PhanTramDoanhSo+"[Tu]"+Tu+"[Vuot]"+Vuot+"[thuongvuot]"+thuongvuot);
					thuongsec = thuongsec + Vuot*thuongvuot;
				}
				*//**************************************************Thuong Sales Out (Second)*************************************//*
				
				
				
				
				*//**************************************************Thuong Sales In (Primary)*************************************//*
				cell = cells.getCell("DM" + Integer.toString(indexRow)); cell.setValue(rs.getDouble("ctPri")); cell.setStyle(style);
				cell = cells.getCell("DN" + Integer.toString(indexRow)); cell.setValue(rs.getDouble("tdPri")); cell.setStyle(style);
				float ptPr = 0;
				if (rs.getFloat("ctPri") > 0 && rs.getFloat("tdPri")>0)
				{
					ptPr = 100 * rs.getFloat("tdPri") / (rs.getFloat("ctPri"));
				}
				cell = cells.getCell("DO" + Integer.toString(indexRow));cell.setValue(ptPr);cell.setStyle(style);
				
				sql = " SELECT B.Thuong,b.ThuongVuot,B.Tu,B.Den  FROM TIEUCHITINHTHUONG A INNER JOIN TIEUCHITINHTHUONG_CHITIET B  " +
						" ON A.PK_SEQ=B.TIEUCHITINHTHUONG_FK " + 
						" WHERE A.LOAI=2 AND THANG=" + obj.getMonth()+ " AND NAM=" + obj.getYear() + " AND B.TIEUCHI=1 AND B.Tu <= " + ptPr + " AND B.Den >" + ptPr;

				 rsthuong = db.get(sql);
				 thuongPri = 0;
				 thuongvuot=0;
				 Tu =0;
				 
				// System.out.println("[Thuong Sales In (Primary)]"+sql);
				 
				if (rsthuong != null)
				{
					while (rsthuong.next())
					{
						thuongPri =  rsthuong.getDouble("thuong");
						thuongvuot = rsthuong.getDouble("ThuongVuot");
						Tu = rsthuong.getDouble("Tu");
					}
					rsthuong.close();
				}
				
				if(ptPr>0&&ptPr>=Tu)
				{
					double Vuot =(ptPr-Tu)<0?0:(ptPr-Tu);
					//System.out.println("[PhanTramDoanhSo]"+ptPr+"[Tu]"+Tu+"[Vuot]"+Vuot+"[thuongvuot]"+thuongvuot);
					thuongPri = thuongPri + Vuot*thuongvuot;
				}
				*//**************************************************Thuong Sales In (Primary)*************************************//*
				
				
				
				
				*//*************************************************Thuong KPI Thuc dat Don Hang**************************************//*
				
				
				 * Lấy tổng số đơn hàng trong tháng / số ngày trong tháng (khai báo lúc chia chỉ tiêu) 
				 * 
				 
				double SoDonHang = rs.getDouble("SoDonHang");
				double SoNgayLamViec = rs.getDouble("SoNgayLamViec");
				double SoNhanVien =rs.getDouble("SoNhanVien");
				
				double soDonHangAVG=0;
				if(SoNgayLamViec>0&&SoNhanVien>0)
				soDonHangAVG=SoDonHang/SoNgayLamViec/SoNhanVien;
				
				sql = " SELECT B.Thuong,b.ThuongVuot,B.Tu,B.Den  FROM TIEUCHITINHTHUONG A INNER JOIN TIEUCHITINHTHUONG_CHITIET B  " +
						" ON A.PK_SEQ=B.TIEUCHITINHTHUONG_FK " + " WHERE A.LOAI=2 AND THANG=" + obj.getMonth()+ 
						" AND NAM=" + obj.getYear() + " AND B.TIEUCHI=3 AND B.Tu <= " + soDonHangAVG + " AND B.Den >" + soDonHangAVG;

//				System.out.println("[Thuong KPI Thuc dat Don Hang]"+sql);
				 rsthuong = db.get(sql);
				 thuongSoDonHang = 0;
				if (rsthuong != null)
				{
					while (rsthuong.next())
					{
						thuongSoDonHang =  rsthuong.getDouble("thuong");
					}
					rsthuong.close();
				}
				if(PhanTramDoanhSo<70)
				{
					thuongSoDonHang=0;
				}
				cell = cells.getCell("DP" + Integer.toString(indexRow)); cell.setValue(soDonHangAVG); cell.setStyle(style);
				*//*************************************************Thuong KPI Thuc dat Don Hang**************************************//*
				
				*//*************************************************Thuong KPI Thuc dat SKU**************************************//*
				
				 *Tổng mặt hàng của các đơn hàng / tổng đơn hàng 
				 
				double SoSKU = rs.getDouble("SoSKU");
				double SoSKU_AVG=0;
				if(SoDonHang>0&&SoNhanVien>0)
					SoSKU_AVG=SoSKU/SoDonHang/SoNhanVien; 
				
				sql = " SELECT B.Thuong,b.ThuongVuot,B.Tu,B.Den  FROM TIEUCHITINHTHUONG A INNER JOIN TIEUCHITINHTHUONG_CHITIET B  " +
						" ON A.PK_SEQ=B.TIEUCHITINHTHUONG_FK " + " WHERE A.LOAI=2 AND THANG=" + obj.getMonth()+ 
						" AND NAM=" + obj.getYear() + " AND B.TIEUCHI=4 AND B.Tu <= " + SoSKU_AVG + " AND B.Den >" + SoSKU_AVG;
				
			//	System.out.println("[Thuong KPI Thuc dat SKU]"+sql);
				rsthuong = db.get(sql);
				thuongSKU = 0;
				if (rsthuong != null)
				{
					while (rsthuong.next())
					{
						thuongSKU =  rsthuong.getDouble("thuong");
					//	System.out.println("[thuongSKU]"+thuongSKU+"[PhanTramDoanhSo]"+PhanTramDoanhSo+"[thuongSKU]"+thuongSKU);
					}
					rsthuong.close();
				}
				
				if(PhanTramDoanhSo<70)
				{
					thuongSKU=0;
				}
				cell = cells.getCell("DQ" + Integer.toString(indexRow)); cell.setValue(SoSKU_AVG); cell.setStyle(style);
				
				cell = cells.getCell("DR" + Integer.toString(indexRow)); cell.setValue(SoNhanVien); cell.setStyle(style);
				
				*//*************************************************Thuong KPI Thuc dat SKU**************************************//*
				
				tongthuong = thuongPri+thuongsec+thuongSKU+thuongSoDonHang;
				cell = cells.getCell("DE" + Integer.toString(indexRow)); cell.setValue(tongthuong); cell.setStyle(style);
				cell = cells.getCell("DF" + Integer.toString(indexRow)); cell.setValue(thuongsec); cell.setStyle(style);
				cell = cells.getCell("DG" + Integer.toString(indexRow)); cell.setValue(thuongPri); cell.setStyle(style);
				cell = cells.getCell("DH" + Integer.toString(indexRow)); cell.setValue(thuongSoDonHang); cell.setStyle(style);
				cell = cells.getCell("DI" + Integer.toString(indexRow)); cell.setValue(thuongSKU); cell.setStyle(style);
				
				indexRow++;
			}
			if (indexRow == 3)
			{
				throw new Exception("Không có báo cáo với điều kiện đã chọn !");
			}
			if (rs != null)
				rs.close();
			if (db != null)
			{
				db.shutDown();
			}
		} catch (Exception err)
		{
			err.printStackTrace();
			throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :" + err.toString());
		}
	}
	
	
	
	private String setQuery( IStockintransit obj) {
		
		
		String query="";
		geso.traphaco.center.db.sql.dbutils db=new geso.traphaco.center.db.sql.dbutils();
		String sql= " select distinct b.NHOMSKU_FK as nhomsanpham_fk,nhom.TEN from CHITIEUNHOMSKUIN  a "+
		" inner join CHITIEUNHOMSKUIN_NHOMSKU b  on a.PK_SEQ=b.CHITIEUNHOMSKU_FK "+
		" inner join NHOMSANPHAM nhom on nhom.PK_SEQ=b.NHOMSKU_FK "+
		" where a.THANG="+obj.getMonth()+" and a.NAM= "+obj.getYear();
		
		 if(obj.getdvkdId().length()>0){
			 sql=sql+ " and a.dvkd_fk= "+ obj.getdvkdId();
			 
		 }
		 if(obj.getkenhId().length()>0){
			 sql=sql+ " and a.KBH_FK= "+ obj.getkenhId();
			 
		 }
		 sql =sql+" order by b.NHOMSKU_FK ";		
		 
		 ResultSet rs=db.get(sql);
		 String chuoi="1";
		 String[] arraychuoi= new String[10];
		 String chuoiselct="1";
		 String chuoingoac="[1]";//co dau []
		 
		 if(rs!=null){
			 int i=0;
			 try {
				while (rs.next()){
					
					 if(i==0){
						 chuoingoac="["+rs.getString("nhomsanpham_fk")+"]";
						 chuoi=rs.getString("nhomsanpham_fk");
						 chuoiselct="isnull(NHOMSKUIN.["+rs.getString("nhomsanpham_fk")+"],0) AS NHOMSKUIN"+rs.getString("nhomsanpham_fk")+"," +"isnull(THUCDATSKUIN.["+rs.getString("nhomsanpham_fk")+"],0) AS THUCDATSKUIN"+rs.getString("nhomsanpham_fk");
					 }else{
						 chuoi=chuoi+","+rs.getString("nhomsanpham_fk");
						 chuoiselct=chuoiselct+",isnull(NHOMSKUIN.["+rs.getString("nhomsanpham_fk")+"],0) AS NHOMSKUIN"+rs.getString("nhomsanpham_fk")+"," +"isnull(THUCDATSKUIN.["+rs.getString("nhomsanpham_fk")+"],0) AS THUCDATSKUIN"+rs.getString("nhomsanpham_fk");
						 chuoingoac=chuoingoac+",["+rs.getString("nhomsanpham_fk")+"]";
					 }
					 arraychuoi[i]=rs.getString("nhomsanpham_fk");
					 i++;
					 
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		
		  sql=" select NV.PK_SEQ,UPPER(NV.TIEUCHI) AS TIEUCHI from NHIEMVUNHANVIEN A  "+
		  " inner join NHIEMVUNHANVIEN_CHITIET B ON A.PK_SEQ=B.NHIEMVUNHANVIEN_FK "+
		  " INNER JOIN NHIEMVU NV ON NV.PK_SEQ=B.NHIEMVU_FK "+
		  " WHERE A.THANG="+obj.getMonth()+" AND A.NAM="+obj.getYear()+" AND A.DOITUONG='SS'";

		  	 if(obj.getdvkdId().length()>0){
				 sql=sql+ " and a.dvkd_fk= "+ obj.getdvkdId();
				 
			 }
			 if(obj.getkenhId().length()>0){
				 sql=sql+ " and a.KBH_FK= "+ obj.getkenhId();
				 
			 }
			 rs=db.get(sql);
			// String chuoinv="0";
			 String[] arraychuoinv= new String[10];
			 String chuoiselctnv="0";
			 String chuoingoacnv="[0]";//co dau []
			 if(rs!=null){
				 int i=0;
				 try {
					while (rs.next()){
						
						 if(i==0){
							 chuoingoacnv="["+rs.getString("PK_SEQ")+"]";
							// chuoinv=rs.getString("PK_SEQ");
							 chuoiselctnv="isnull(NHIEMVU1.["+rs.getString("PK_SEQ")+"],0) AS NHIEMVU"+rs.getString("PK_SEQ");
						 }else{
							 chuoingoacnv=chuoingoacnv+",["+rs.getString("PK_SEQ")+"]";
							// chuoinv=chuoinv+","+rs.getString("PK_SEQ");
							 chuoiselctnv= chuoiselctnv + ",isnull(NHIEMVU1.["+rs.getString("PK_SEQ")+"],0) AS NHIEMVU"+rs.getString("PK_SEQ");

						 }
						 arraychuoinv[i]=rs.getString("PK_SEQ");
						 i++;
						 
					 }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 
		obj.setFieldShow(arraychuoi);
		obj.setFieldHidden(arraychuoinv);
	
		int thang=Integer.parseInt(obj.getMonth());
		int nam=Integer.parseInt(obj.getYear());
		
		String thoigian = nam + "-" + ((thang + "").length() > 1 ? ("" + thang) : ("0" + thang)) + "%";
		
		int thangtruoc=0;
		int namtruoc=0;
		

		if(thang==1){
			thangtruoc=12;
			namtruoc=nam-1;
			
		}else{
			namtruoc=nam;
			thangtruoc=thang-1;
		}
		
		if(thang==12){
				thang=1;
				nam=nam+1;
				
		}else{
			thang=thang+1;
			
		}
		String ngaydauthang=nam+"-"+ ((thang+"").length() >1? (""+thang):("0"+thang) )+"-01";
		
		
		
		  sql="select distinct nhomsanpham_fk,dbo.ftBoDau(nsp.ten) as ten  from  chitieunpp_ddkd_nhomsp "+  
			" inner join chitieunpp b on b.pk_Seq=chitieunpp_fk  "+
			" inner join nhomsanpham nsp on nsp.pk_seq=nhomsanpham_fk "+
			" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;

			if(obj.getdvkdId().length()>0){
				 sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
				 
			}
			if(obj.getkenhId().length()>0){
				 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
				 
			}
			
			rs=db.get(sql);
			String chuoiskuout="0";
			String[] arraychuoiskuout= new String[10];
			String chuoiselctskuout="0";
			String chuoingoacskuout="[0]";//co dau []
			
			if(rs!=null){
				int  i=0;
				 try {
					while (rs.next()){
						
						 if(i==0){
							 chuoingoacskuout="[0],["+rs.getString("nhomsanpham_fk")+"]";
							 chuoiskuout=rs.getString("nhomsanpham_fk");
							 chuoiselctskuout=" ,isnull(CTNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS CTNHOM"+rs.getString("nhomsanpham_fk")+",ISNULL(DS1.["+rs.getString("nhomsanpham_fk")+"],0) AS DS1"+rs.getString("nhomsanpham_fk");
						 }else{
							 chuoiskuout=chuoiskuout+","+rs.getString("nhomsanpham_fk");
							 chuoiselctskuout=chuoiselctskuout+", isnull(CTNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS CTNHOM"+rs.getString("nhomsanpham_fk")+",ISNULL(DS1.["+rs.getString("nhomsanpham_fk")+"],0) AS DS1"+rs.getString("nhomsanpham_fk");
							 chuoingoacskuout=chuoingoacskuout+",["+rs.getString("nhomsanpham_fk")+"]";
						 }
						 arraychuoiskuout[i]=rs.getString("nhomsanpham_fk");
						 i++;
						 
					 }
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			
			int i=0;
			
			// NHÓM THƯỞNG SALESOUT ISNHOMTHUONGSALESOUT=1
			 sql=" select pk_seq  as nhomsanpham_fk, dbo.ftBoDau(a.DIENGIAI) as ten ,isnull(ISNHOMTHUONGSALESOUT,'0') as ISNHOMTHUONGSALESOUT  ,tienthuong  " +
				" from nhomfocus a where a.NAM="+ obj.getYear()+" and THANG="+obj.getMonth()+" and DOITUONG=1 AND ISNHOMTHUONGSALESOUT=1 and trangthai=1 ";
			 
				 if(obj.getdvkdId().length()>0)
				 {
					 sql=sql+ " and a.dvkd_fk= "+ obj.getdvkdId();
				 }
				 if(obj.getkenhId().length()>0)
				 {
					 sql=sql+ " and a.KBH_FK= "+ obj.getkenhId();
				 }
				 if(obj.getkhuvucId().length()>0)
					 sql+=" and a.pk_seq in (select nhomfocus_fk from nhomfocus_vung where vung_fk='"+obj.getkhuvucId()+"'  )";
				 System.out.println(sql);
			
				 String chuoithuongsalesout="0";
				 String[] arraythuongsalesout= new String[10];
				 String chuoiselctthuongsalesout="";
				 String chuoingoacthuongsalesout="[0]";//co dau []
				 rs=db.get(sql);
				 try {
						while (rs.next()){
							
							 if(i==0){
								 chuoingoacthuongsalesout="[0],["+rs.getString("nhomsanpham_fk")+"]";
								 chuoithuongsalesout=rs.getString("nhomsanpham_fk");
								 chuoiselctthuongsalesout=" ,isnull(ctSalesOut.["+rs.getString("nhomsanpham_fk")+"],0) AS ctSalesOut"+rs.getString("nhomsanpham_fk")+",ISNULL(tdSalesOut.["+rs.getString("nhomsanpham_fk")+"],0) AS tdSalesOut"+rs.getString("nhomsanpham_fk");
							 }else{
								 chuoithuongsalesout=chuoithuongsalesout+","+rs.getString("nhomsanpham_fk");
								 chuoiselctthuongsalesout=chuoiselctthuongsalesout+", isnull(ctSalesOut.["+rs.getString("nhomsanpham_fk")+"],0) AS ctSalesOut"+rs.getString("nhomsanpham_fk")+",ISNULL(tdSalesOut.["+rs.getString("nhomsanpham_fk")+"],0) AS tdSalesOut"+rs.getString("nhomsanpham_fk");
								 chuoingoacthuongsalesout=chuoingoacthuongsalesout+",["+rs.getString("nhomsanpham_fk")+"]";
							 }
							 arraythuongsalesout[i]=rs.getString("nhomsanpham_fk");
							 i++;
							 
						 }
					} catch (Exception e) 
					{
						e.printStackTrace();
					}
			
					
					String SoLuong="SoLuong";
					if(obj.getYear().equals("2013")&&obj.getMonth().equals("12"))
					{
						SoLuong="SoLuongLe";
					}
			
					Utility util = new Utility();
					
					
sql=
  " SELECT KV.TEN,GSBH.MANHANVIEN AS GSBHID,GSBH.TEN AS GSBHTEN ,CT.DVKD_FK,CT.KBH_FK,CT.KVID ,CTPRI.CHITIEU AS CHITIEUPRI,   \n" +   
  "   CT.CHITIEU AS CHITIEUSEC,DSSEC.DOANHSO AS DSSEC,DSSEC_pre.doanhso as DSSEC_pre,isnull(tk.doanhsoton,0) as  doanhsoton  \n" +
  "   ,DSPRI.DS_PRI ,  " +
  "  " +chuoiselctnv +    
  "  ," +chuoiselct + 
  "  ,"+chuoiselctskuout+ 
  "  "+chuoiselctthuongsalesout+
  "   FROM   \n" +   
  "  (  \n" +   
  " 	SELECT	CTNPP.KENH_FK AS KBH_FK, CTNPP.DVKD_FK, kv.KHUVUC_FK AS KVID, GSBH.PK_SEQ AS GSBHID \n" +      
  " 		,SUM(CT_GS.CHITIEU) AS CHITIEU			        FROM CHITIEU_SEC CTNPP					 \n" +	     
  " 		INNER JOIN CHITIEUSEC_GSBH CT_GS ON CTNPP.PK_SEQ =     CT_GS.CHITIEUSEC_FK   	     \n" +
  " 		INNER JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = CT_GS.GSBH_FK    AND GSBH.DVKD_FK = CTNPP.DVKD_FK AND GSBH.TRANGTHAI='1' \n" +  
  " 		INNER JOIN GSBH_KHUVUC kv ON    kv.GSBH_FK = GSBH.PK_SEQ 	         \n" +
  " 	WHERE CTNPP.TRANGTHAI=1 AND CT_GS.CHITIEU >0   AND CTNPP.THANG ="+obj.getMonth()+" AND CTNPP.NAM ="+obj.getYear()+  
  "		GROUP BY  CTNPP.KENH_FK , CTNPP.DVKD_FK, kv.KHUVUC_FK, GSBH.PK_SEQ \n" + 
  " UNION  \n" + 
  "	SELECT GSBH.KBH_FK,GSBH.DVKD_FK,KV.KHUVUC_FK,GSBH.PK_SEQ,0 AS CHITIEU  \n" +
  "	FROM GIAMSATBANHANG GSBH  INNER JOIN GSBH_KHUVUC KV ON KV.GSBH_FK=GSBH.PK_SEQ \n" +
	" WHERE NOT EXISTS \n" +
	"( \n" +
	"	SELECT * FROM CHITIEUSEC_GSBH WHERE GSBH_FK=GSBH.PK_SEQ \n" +
	"	AND CHITIEUSEC_FK IN (SELECT PK_SEQ FROM CHITIEU_SEC WHERE THANG="+obj.getMonth()+" AND NAM="+obj.getYear()+" )  \n" +
	")AND  \n" +
	" (  \n" +
	" EXISTS \n" +
	"	(\n" +
	"		SELECT * FROM NHAPHANG A  \n" +
	"		INNER JOIN NHAPP_GIAMSATBH B ON B.NPP_FK=A.NPP_FK \n" +
	"		WHERE B.GSBH_FK=GSBH.PK_SEQ AND NGAYCHUNGTU LIKE '"+thoigian+"' AND  \n" +
	"		TRANGTHAI!=2 AND B.NGAYBATDAU<='"+thoigian+"' AND NGAYKETTHUC >= '"+thoigian+"'  \n" + 
	"	) OR  \n" +
	" EXISTS \n" +
	" (  \n" +
	"	SELECT * FROM DONHANG WHERE GSBH_FK=GSBH.PK_SEQ \n" +
	"	AND NGAYNHAP LIKE '"+thoigian+"' AND TRANGTHAI=1 \n" +
	"   ) \n" +  
	" ) \n" +
  "  ) AS CT   \n" +   
  "  LEFT JOIN   \n" +   
  "  (  \n" +   
  "  	SELECT	CT.KENH_FK AS KBH_FK, CT.DVKD_FK, GSBH.PK_SEQ AS GSBHID   \n" +   
  "  		, SUM(CTNPP.CHITIEU) AS CHITIEU			  \n" +   
  "  	FROM CHITIEU CT						  \n" +   
  "  		INNER JOIN CHITIEU_GSBH CTNPP ON CT.PK_SEQ = CTNPP.CHITIEU_FK   " +    
  "  		INNER JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ = CTNPP.GSBH_FK  " +   
  "  	WHERE CT.TRANGTHAI=1 AND  CT.THANG ="+obj.getMonth()+" AND CT.NAM ="+obj.getYear()+" 								  " +   
  "  	GROUP BY  CT.KENH_FK , CT.DVKD_FK, GSBH.PK_SEQ  " +   
  "	UNION "+ 
  "	SELECT GSBH.KBH_FK,GSBH.DVKD_FK,GSBH.PK_SEQ,0 AS CHITIEU  "+
  "	FROM GIAMSATBANHANG GSBH  "+
	"	WHERE NOT EXISTS  "+
	"	( "+
	"		SELECT * FROM CHITIEU_GSBH WHERE GSBH_FK=GSBH.PK_SEQ "+
	"		AND CHITIEU_FK IN (SELECT PK_SEQ FROM CHITIEU WHERE THANG="+obj.getMonth()+" AND NAM="+obj.getYear()+" 	) "+
	"	)AND" +
	" (  " +
	" EXISTS \n" +
	"	( \n" +
	"		SELECT * FROM NHAPHANG A  \n" +
	"		INNER JOIN NHAPP_GIAMSATBH B ON B.NPP_FK=A.NPP_FK \n" +
	"		WHERE B.GSBH_FK=GSBH.PK_SEQ AND NGAYCHUNGTU LIKE '"+thoigian+"' AND  "+
	"		TRANGTHAI!=2 AND B.NGAYBATDAU<='"+thoigian+"' AND NGAYKETTHUC >= '"+thoigian+"' "+ 
	"	) OR  " +
	" EXISTS "+
	" (  "+
	"	SELECT * FROM DONHANG WHERE GSBH_FK=GSBH.PK_SEQ       \n"+
	"	AND NGAYNHAP LIKE '"+thoigian+"' AND TRANGTHAI=1  \n"+
	"   )  \n"+  
	" )  \n"+
  "  ) CTPRI ON CT.DVKD_FK=CTPRI.DVKD_FK AND CT.GSBHID=CTPRI.GSBHID AND CT.KBH_FK=CTPRI.KBH_FK     \n"+    
  "  LEFT JOIN   \n"+   
  "  (   \n"+   
  "   SELECT NPP.KHUVUC_FK, DH.KBH_FK, SP2.DVKD_FK AS DVKD_FK, DH.GSBH_FK AS GSBHID,     \n"+   
  "   	SUM(DH_SP.SOLUONG * DH_SP.GIAMUA/1.1*"+util.ChietKhau(nam+"")+") AS DOANHSO   \n"+   
  "   FROM DONHANG DH INNER JOIN DONHANG_SANPHAM DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK  	  \n"+   
  "  	 INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = DH_SP.SANPHAM_FK	  \n"+   
  "  	 inner JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=DH.NPP_FK  \n"+   
  "   WHERE SUBSTRING(DH.NGAYNHAP, 1 , 7)  = '"+obj.getYear()+"-"+obj.getMonth()+"' AND DH.TRANGTHAI ='1' AND DH.PK_SEQ NOT IN(SELECT DONHANG_FK FROM DONHANGTRAVE WHERE TRANGTHAI=3 AND DONHANG_FK IS NOT NULL)  \n"+   
  "   GROUP BY DH.KBH_FK, SP2.DVKD_FK, DH.GSBH_FK,NPP.KHUVUC_FK   \n"+   
  "  ) AS DSSEC ON DSSEC.DVKD_FK=CT.DVKD_FK AND DSSEC.GSBHID=CT.GSBHID AND CT.KBH_FK=DSSEC.KBH_FK   \n"+   
  "  AND DSSEC.KHUVUC_FK =CT.KVID  \n"+   
  "  LEFT JOIN   \n"+   
  "  (   \n"+   
  "   SELECT NPP.KHUVUC_FK, DH.KBH_FK, SP2.DVKD_FK AS DVKD_FK, DH.GSBH_FK AS GSBHID,   \n"+   
  "   	SUM(DH_SP.SOLUONG * DH_SP.GIAMUA/1.1*0.97) AS DOANHSO  \n"+   
  "   FROM DONHANG DH INNER JOIN DONHANG_SANPHAM DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK  	  " +   
  "   	INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = DH_SP.SANPHAM_FK	  \n"+   
  "  	LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=DH.NPP_FK  \n"+   
  "   WHERE SUBSTRING(DH.NGAYNHAP, 1 , 7)  = '"+namtruoc+"-"+((thangtruoc+"").length() >1?thangtruoc:"0"+thangtruoc)+"' AND DH.TRANGTHAI ='1'  \n"+   
  "   GROUP BY DH.KBH_FK, SP2.DVKD_FK, DH.GSBH_FK,NPP.KHUVUC_FK   \n"+   
  "  ) AS DSSEC_pre ON DSSEC_pre.DVKD_FK=CT.DVKD_FK AND DSSEC_pre.GSBHID=CT.GSBHID AND CT.KBH_FK=DSSEC_pre.KBH_FK   \n"+   
  "  AND DSSEC_pre.KHUVUC_FK =CT.KVID  \n"+   
  "  LEFT JOIN  (  \n"+   
  "  SELECT NPP.KHUVUC_FK,  NH.KBH_FK, SP2.DVKD_FK AS DVKD_FK, NPP_GS.GSBH_FK AS GSBHID,   \n"+   
  "  	SUM(NH_SP."+SoLuong+" * NH_SP.GIANET-ISNULL(NH_SP.CKTT,0)) AS DS_PRI   \n"+   
  "  FROM NHAPHANG NH INNER JOIN NHAPHANG_SP NH_SP ON NH.PK_SEQ = NH_SP.NHAPHANG_FK				  \n"+   
  "  	INNER JOIN SANPHAM SP2 ON SP2.MA = NH_SP.SANPHAM_FK	  \n"+   
  "  	INNER JOIN NHAPP_GIAMSATBH NPP_GS ON NPP_GS.NPP_FK = NH.NPP_FK   \n"+   
  "  	LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=NH.NPP_FK   \n"+   
  "  WHERE SUBSTRING(NH.NGAYCHUNGTU,1,7)  =  '"+obj.getYear()+"-"+obj.getMonth()+"' AND NH.TRANGTHAI !='2' AND NH.LOAIHOADON=0 AND NH.PK_SEQ NOT IN(SELECT NHAPHANG_FK FROM HUYNHAPHANG WHERE TRANGTHAI=1 )  \n"+   
  "  GROUP BY NH.KBH_FK, SP2.DVKD_FK, NPP_GS.GSBH_FK ,NPP.KHUVUC_FK  \n"+   
  "  ) DSPRI ON DSPRI.DVKD_FK=CT.DVKD_FK  AND DSPRI.GSBHID=CT.GSBHID AND DSPRI.KBH_FK=CT.KBH_FK  \n"+   
  "  AND CT.KVID=DSPRI.KHUVUC_FK   \n"+   
  "  LEFT JOIN  \n"+   
  "   (  \n"+   
  "  SELECT NPP.KHUVUC_FK,A.DVKD_FK,A.KBH_FK,NPP_GS.GSBH_FK  ,B.NHOMSKU_FK, SUM(B.CHITIEU) AS CHITIEU  \n"+   
  "  FROM CHITIEUNHOMSKUIN A   \n"+   
  "  	INNER JOIN CHITIEUNHOMSKUIN_NHOMSKU B ON A.PK_SEQ=B.CHITIEUNHOMSKU_FK  \n"+   
  "  	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=B.NPP_FK  \n"+   
  "  	INNER JOIN NHAPP_GIAMSATBH NPP_GS ON NPP_GS.NPP_FK = B.NPP_FK  \n"+   
  "  WHERE A.THANG="+obj.getMonth()+" AND A.NAM="+obj.getYear()+"  " +   
  "  GROUP BY  NPP.KHUVUC_FK,A.DVKD_FK,A.KBH_FK,NPP_GS.GSBH_FK ,B.NHOMSKU_FK  \n"+   
  "  ) P   \n"+   
  "  PIVOT   \n"+   
  "  (   \n"+   
  "  SUM(CHITIEU)   \n"+   
  "  FOR NHOMSKU_FK IN   \n"+   
  "  ("+chuoingoac+" )   \n"+   
  "  ) AS NHOMSKUIN ON NHOMSKUIN.DVKD_FK=CT.DVKD_FK AND NHOMSKUIN.KBH_FK=CT.KBH_FK   \n"+   
  "  AND CT.GSBHID=NHOMSKUIN.GSBH_FK AND CT.KVID=NHOMSKUIN.KHUVUC_FK   \n"+   
  "  LEFT JOIN   \n"+   
  "  (  \n"+   
  "  SELECT NPP.KHUVUC_FK,  NH.KBH_FK, SP2.DVKD_FK AS DVKD_FK, NPP_GS.GSBH_FK,   \n"+   
  "  	SUM(NH_SP.SOLUONG) AS THUCDAT,C.NHOMSKU_FK  \n"+   
  "  FROM NHAPHANG NH INNER JOIN NHAPHANG_SP NH_SP ON NH.PK_SEQ = NH_SP.NHAPHANG_FK	AND NH.LOAIHOADON=0 AND NH.TRANGTHAI!='2'  AND NH.PK_SEQ NOT IN(SELECT NHAPHANG_FK FROM HUYNHAPHANG WHERE TRANGTHAI=1)	  \n"+   
  "  	INNER JOIN SANPHAM SP2 ON SP2.MA = NH_SP.SANPHAM_FK	  \n"+   
  "  	INNER JOIN CHITIEUNHOMSKUIN_NHOMSKU_SP C ON C.SANPHAM_FK=SP2.PK_SEQ  \n"+   
  "  	AND C.CHITIEUNHOMSKU_FK IN (SELECT PK_SEQ FROM CHITIEUNHOMSKUIN CTIN WHERE CTIN.THANG="+obj.getMonth()+" AND CTIN.NAM="+obj.getYear()+" )  \n"+   
  "  	INNER JOIN NHAPP_GIAMSATBH NPP_GS ON NPP_GS.NPP_FK = NH.NPP_FK   \n"+   
  "  	LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=NH.NPP_FK  \n"+   
  "  WHERE   C.NHOMSKU_FK  IN  \n"+   
  "  (SELECT D.NHOMSKU_FK FROM CHITIEUNHOMSKUIN_NHOMSKU D WHERE D.NPP_FK=NPP.PK_SEQ  \n"+   
  "  ) AND   \n"+   
  "  SUBSTRING(NH.NGAYCHUNGTU,1,7)  =  '"+obj.getYear()+"-"+obj.getMonth()+"' AND NH.TRANGTHAI <>'2' 			  \n"+   
  "  GROUP BY NH.KBH_FK, SP2.DVKD_FK, NPP_GS.GSBH_FK ,NPP.KHUVUC_FK,C.NHOMSKU_FK  \n"+   
  "  ) P  \n"+   
  "  PIVOT   \n"+   
  "  (   \n"+   
  "  SUM(THUCDAT)   \n"+   
  "  FOR NHOMSKU_FK IN   \n" +   
  "  ("+chuoingoac+" )   \n" +   
  "  ) AS THUCDATSKUIN ON THUCDATSKUIN.DVKD_FK=CT.DVKD_FK AND THUCDATSKUIN.KBH_FK=CT.KBH_FK   \n" +   
  "  AND CT.GSBHID=THUCDATSKUIN.GSBH_FK AND CT.KVID=THUCDATSKUIN.KHUVUC_FK  \n" +   
  "  LEFT JOIN  \n" +   
  "    \n" +   
  "   (    \n" +   
  "    select NVNV.DVKD_FK,NVNV.KBH_FK,thnv.NHANVIEN_FK,NHIEMVU_FK, CAST(thnv.DAT AS INT)  AS DAT   \n" +   
  "    from THUCHIENNHIEMVUTHANG thnv      \n" +   
  "    		inner join NHIEMVUNHANVIEN NVNV on thnv.NHIEMVUNHANVIEN_FK=NVNV.PK_SEQ        \n" +   
  "    where NVNV.DOITUONG='SS' and NVNV.THANG="+obj.getMonth()+"  and NVNV.NAM="+obj.getYear()+"  \n" +   
  "    ) P      \n" +   
  "    PIVOT      \n" +   
  "    (        \n" +   
  "    SUM(DAT)      \n" +   
  "    FOR NHIEMVU_FK IN       \n" +   
  "    ("+chuoingoacnv+" )      \n" +   
  "    ) AS NHIEMVU1   ON NHIEMVU1.NHANVIEN_FK=CT.GSBHID AND NHIEMVU1.KBH_FK=CT.KBH_FK AND CT.DVKD_FK=NHIEMVU1.DVKD_FK  \n" +   
  "  LEFT JOIN     \n" +   
  "  ( SELECT SP.DVKD_FK, TKN.KBH_FK,B.GSBH_FK,SUM(SOLUONG* NPK.GIAMUA/1.1*0.97 ) as doanhsoton,NPP.KHUVUC_FK  FROM TONKHONGAY TKN   \n" + 
 "  LEFT JOIN (  \n" + 
 "  SELECT BGBLC_SP.SANPHAM_FK,BGBLC_SP.GIABANLECHUAN AS GIAMUA,BGBLC.DVKD_FK  \n" + 
 "  FROM  \n" + 
 "  BANGGIABANLECHUAN BGBLC  \n" + 
 "  INNER JOIN BANGGIABLC_SANPHAM BGBLC_SP ON BGBLC_SP.BGBLC_FK=BGBLC.PK_SEQ   \n" + 
 "  WHERE BGBLC.TRANGTHAI=1 )NPK ON  TKN.SANPHAM_FK=NPK.SANPHAM_FK  \n" + 
 "  INNER JOIN SANPHAM SP ON SP.PK_SEQ=TKN.SANPHAM_FK  \n" + 
 "  INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=TKN.NPP_FK  \n" + 
 "  INNER JOIN NHAPP_GIAMSATBH B ON B.NPP_FK=TKN.NPP_FK   \n" +
 " where tkn.ngay=Replace(convert(char(10), DATEADD(day, -1, cast('"+ngaydauthang+"' as datetime)) , 102) , '.', '-' ) \n" + 
 "  GROUP BY SP.DVKD_FK, TKN.KBH_FK,B.GSBH_FK,NPP.KHUVUC_FK  \n" +   
  "   ) TK ON CT.DVKD_FK=TK.DVKD_FK AND CT.GSBHID=TK.GSBH_FK AND CT.KBH_FK=TK.KBH_FK    \n" +   
  "  AND CT.KVID=TK.KHUVUC_FK \n" +
  " left  JOIN  ( \n" +
  " 	 SELECT  D_G.GSBH_FK, CTNPP.KENH_FK as KBH_FK  ,CTNPP.DVKD_FK  ,npp.KHUVUC_FK   \n" +
  " 	 , B.CHITIEU AS CHITIEUNHOM ,B.NHOMSANPHAM_FK  \n" +
				 " 	 FROM CHITIEUNPP_DDKD_NHOMSP B INNER JOIN CHITIEUNPP_DDKD A ON   \n" +
  " 	 A.CHITIEUNPP_FK=B.CHITIEUNPP_FK AND A.DDKD_FK=B.DDKD_FK  \n" +
  " 	 INNER JOIN CHITIEUNPP CTNPP ON CTNPP.PK_SEQ=B.CHITIEUNPP_FK   \n" +
  " 	 inner join NHAPHANPHOI npp on npp.PK_SEQ=CTNPP.NHAPP_FK  \n" +
  " 	 inner join DDKD_GSBH D_G on D_G.DDKD_FK=A.DDKD_FK  \n" +
  " 	 WHERE CTNPP.THANG="+obj.getMonth()+" AND CTNPP.NAM="+obj.getYear()+" and ctnpp.TRANGTHAI<>'2'   \n" +
  " 		  ) P \n" +
  " 		 PIVOT   \n" +
  " 		 (  \n" +
  " 			 SUM(CHITIEUNHOM)   \n" +
  " 			 FOR NHOMSANPHAM_FK IN  \n" +
  " 			 (	 "+chuoingoacskuout+"  ) \n" +
  " 		 ) as CTNHOM ON CT.DVKD_FK=CTNHOM.DVKD_FK AND CT.GSBHID=CTNHOM.GSBH_FK AND CT.KBH_FK=CTNHOM.KBH_FK    \n" +   
  "  AND CT.KVID=CTNHOM.KHUVUC_FK   \n" +
	" LEFT JOIN  \n" +
	" (  \n" +
	" SELECT KHUVUC_FK,DVKD_FK, GSBH_FK,KBH_FK , "+chuoingoacskuout +
	" FROM (  \n" +
	" SELECT NPP.KHUVUC_FK , SP.DVKD_FK,DH.KBH_FK,GSBH_FK,NSP.NHOMSP_FK, SUM(SOLUONG /  CAST (QC.SOLUONG1/QC.SOLUONG2 AS INT) * isnull(QUYDOITHUNGTHUONG,1)) AS SANLUONG  \n" +
	" FROM DONHANG DH INNER JOIN   \n" +
	" DONHANG_SANPHAM DH_SP ON DH.PK_SEQ=DH_SP.DONHANG_FK   \n" +
	" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=DH.NPP_FK  \n" +
	"   INNER JOIN SANPHAM SP ON SP.PK_SEQ=DH_SP.SANPHAM_FK  \n" +
	"  INNER JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ    \n" +   
	"  AND SP.DVDL_FK=QC.DVDL1_FK AND DVDL2_FK=100018    \n" +   
	" INNER JOIN (  \n" +
	" SELECT MAX(NSP_FK) AS NHOMSP_FK,SP_FK AS SANPHAM_FK FROM NHOMSANPHAM_SANPHAM \n" +
	" WHERE NSP_FK IN ("+chuoiskuout+") \n" +
	" GROUP BY SP_FK  \n" +
	" ) AS NSP ON NSP.SANPHAM_FK=SP.PK_SEQ \n" +
	" WHERE  DH.TRANGTHAI='1' AND DH.NGAYNHAP LIKE '"+obj.getYear()+"-"+obj.getMonth()+"%' \n" +
	" GROUP BY SP.DVKD_FK,DH.KBH_FK,GSBH_FK,NSP.NHOMSP_FK ,NPP.KHUVUC_FK \n" +
	" ) A  \n" +
	" PIVOT \n" +
	" (  \n" +
	" SUM( SANLUONG)  \n" +
	" FOR NHOMSP_FK IN  \n" +
	" ( "+chuoingoacskuout+" ) \n" +
	" ) AS T  \n" +
	" ) AS DS1 ON   CT.DVKD_FK=DS1.DVKD_FK AND CT.GSBHID=DS1.GSBH_FK AND CT.KBH_FK=DS1.KBH_FK    \n" +   
    "  AND CT.KVID=DS1.KHUVUC_FK  \n" + 
    "  left join  \n" + 
	"	(  \n" +
	"		select kenh_fk,dvkd_fk,gsbh_fk,khuvuc_fk ,"+chuoingoacthuongsalesout+" \n" +
	"		from  \n" +
	"		(  \n" +
	"			select a.kenh_fk,a.dvkd_fk,b.gsbh_fk,d.khuvuc_fk,b.chitieu as ctnhom,b.nhomsanpham_fk  \n" +
	"			from chitieu_sec a inner join chitieusec_gsbh_nhomsp b  on a.pk_seq=b.chitieusec_fk  \n" +
	"				inner join giamsatbanhang c on c.pk_seq=b.gsbh_fk  \n" +
	"				inner join gsbh_khuvuc d on d.gsbh_fk=c.pk_seq " +
	"           group by a.kenh_fk,a.dvkd_fk,b.gsbh_fk,d.khuvuc_fk,b.nhomsanpham_fk ,b.chitieu                                                    " +
	"		)p pivot (sum(ctnhom) for nhomsanpham_fk in  ( "+chuoingoacthuongsalesout+" )  )    as t  \n" +
	"	)as ctSalesOut on ctSalesOut.KENH_FK=ct.kbh_fk and ctSalesOut.GSBH_FK=ct.gsbhid  and ctSalesOut.KHUVUC_FK=ct.kvid and ctSalesOut.DVKD_FK=ct.DVKD_FK  \n" +
	"	left join  \n" + 
	"	( \n" +
	"		select * from  \n" +
	"		(  \n" +
	"				select npp.khuvuc_fk,dh.kbh_fk,sp.dvkd_fk,dh.gsbh_fk as gsbhid,sum(dhsp.soluong/qc.soluong1/qc.soluong2)as saleout,nhom.pk_seq as nhomid \n" +
	"			from  \n" +
	"			(  \n" +
	"				select dh.pk_seq,dh.npp_fk,dh.gsbh_fk,dh.kbh_fk from donhang dh  \n" +
	"				where dh.trangthai=1 and dh.ngaynhap>='2013-11-28' and dh.ngaynhap<='2013-12-31'  \n" +
	"			)dh  \n" +
	"				inner join donhang_sanpham dhsp on dhsp.donhang_fk=dh.pk_seq  \n" +
	"				inner join nhaphanphoi npp on npp.pk_seq=dh.npp_fk  \n" +
	"				inner join sanpham sp on sp.pk_seq=dhsp.sanpham_fk " +
	"               inner join quycach qc on qc.sanpham_fk=dhsp.sanpham_fk and qc.dvdl2_fk=100018 and qc.dvdl1_fk=sp.dvdl_fk  \n" +
	"				inner join  \n" +
	"			(   \n" +
	"				select c.sanpham_fk,b.vung_fk,a.pk_seq \n" +
	"				from nhomfocus a inner join nhomfocus_vung b on a.pk_seq=b.nhomfocus_fk  \n" +
	"				inner join nhomfocus_sp c on c.nhomfocus_fk=b.nhomfocus_fk \n" +
	"			 where a.doituong=1 and thang=12 and nam=2013  \n" +
	"			)as nhom on nhom.sanpham_fk=dhsp.sanpham_fk and nhom.vung_fk=npp.khuvuc_fk \n" +
	"			group by npp.khuvuc_fk,dh.kbh_fk,sp.dvkd_fk,dh.gsbh_fk,nhom.pk_seq  \n" +
	"		)as tdSalesOut pivot (sum(saleout) for nhomid in ( "+chuoingoacthuongsalesout+" ) ) as t  \n" +
	"	)as tdSalesOut on tdSalesOut.DVKD_FK=ct.DVKD_FK and tdSalesOut.gsbhid=ct.gsbhid  \n" +
	"	and tdSalesOut.KBH_FK=ct.kbh_fk and tdSalesOut.KHUVUC_FK=ct.kvid  \n" +
	" LEFT JOIN KHUVUC KV ON KV.PK_SEQ=CT.KVID    \n" +   
	"  LEFT JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ=CT.GSBHID "; 
	query += " where  1=1 ";
	if(obj.getkenhId().length() > 0)
		query += " and ct.kbh_fk ='"+obj.getkenhId()+"'";

	if(obj.getvungId().length() > 0)
		query += " and kv.vung_fk = '"+obj.getvungId()+"'";
	if(obj.getdvkdId().length() > 0)
		query += " and ct.dvkd_fk = '"+obj.getdvkdId()+"'";
	if(obj.getkhuvucId().length() > 0)
		query += " and kv.pk_seq = '"+obj.getkhuvucId()+"'";
	
	if(obj.getgsbhId().length() > 0)
		query += " and GSBH.pk_seq = '"+obj.getgsbhId()+"'";
	
	sql=sql+ query;
	System.out.println("1.Query SalesRep : " + sql);
		return sql;
	}

			private void CreatePivotTable(OutputStream out,IStockintransit obj,String query) throws Exception
		    {   
				try{
					String chuoi=getServletContext().getInitParameter("path") + "\\SSPerformanceOneOne.xlsm";
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
		Cell cell = cells.getCell("N1");
		style = cell.getStyle();
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
	   
 
	   
	    		dbutils db=new dbutils();
				
	    		this.ThucHienChamDiemTuDong(db ,obj);
			
				String  sql=
				"select NV.PK_SEQ,UPPER(NV.TIEUCHI ) AS TIEUCHI,THUONG from NHIEMVUNHANVIEN A  "+
				  " inner join NHIEMVUNHANVIEN_CHITIET B ON A.PK_SEQ=B.NHIEMVUNHANVIEN_FK "+
				  " INNER JOIN NHIEMVU NV ON NV.PK_SEQ=B.NHIEMVU_FK "+
				  " WHERE A.THANG="+obj.getMonth()+" AND A.NAM="+obj.getYear()+" AND A.DOITUONG='SS'";
				  int i=9;
					 //System.out.println(sql);
					 
					
				  if(obj.getdvkdId().length()>0){
						 sql=sql+ " and a.dvkd_fk= "+ obj.getdvkdId();
						 
					 }
					 if(obj.getkenhId().length()>0){
						 sql=sql+ " and a.KBH_FK= "+ obj.getkenhId();
						 
					 }
					// nhiem vu phan thuong
					 	ResultSet 	 rs_nhiemvu=db.getScrol(sql);
					 try {
							while (rs_nhiemvu.next()){
								cell = cells.getCell(htb.get(i)+"1"); cell.setValue(rs_nhiemvu.getDouble("THUONG"));
								cell.setStyle(style);
								 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("THUONG : "+rs_nhiemvu.getString("TIEUCHI"));
									cell.setStyle(style);
								 i=i+1;
							 }
							
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						
						 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("CHITIEU(Sales Out) ");
							cell.setStyle(style);
							i=i+1;
							 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("THUCDAT(Sales Out) ");
								cell.setStyle(style);
								i=i+1;
								 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("PHANTRAM(Sales Out) ");
									cell.setStyle(style);
									i=i+1;
									cell = cells.getCell(htb.get(i)+"2"); cell.setValue("CHITIEU( Sales in)");
								cell.setStyle(style);
								i=i+1;
								
								cell = cells.getCell(htb.get(i)+"2"); cell.setValue("DOANHSO( Sales in)");
								cell.setStyle(style);
								i=i+1;
								cell = cells.getCell(htb.get(i)+"2"); cell.setValue("PHANTRAM( Sales in)");
								cell.setStyle(style);
								i=i+1;
								
								  sql=  " select distinct b.NHOMSKU_FK,nhom.TEN from CHITIEUNHOMSKUIN  a "+
										" inner join CHITIEUNHOMSKUIN_NHOMSKU b  on a.PK_SEQ=b.CHITIEUNHOMSKU_FK "+
										" inner join NHOMSANPHAM nhom on nhom.PK_SEQ=b.NHOMSKU_FK "+
										" where a.THANG="+obj.getMonth()+" and a.NAM= "+obj.getYear()+ 
										" order by b.NHOMSKU_FK ";							 
							 if(obj.getdvkdId().length()>0){
								 sql=sql+ " and a.dvkd_fk= "+ obj.getdvkdId();
								 
							 }
							 if(obj.getkenhId().length()>0){
								 sql=sql+ " and a.KBH_FK= "+ obj.getkenhId();
								 
							 }
							 ResultSet rs=db.get(sql);
						if(rs!=null){
							
							 try {
								while (rs.next()){
									 
									 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("ChiTieu-"+rs.getString("ten"));	
									 i=i+1;
									 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("ThucDat-"+rs.getString("ten"));
									 i=i+1;
									 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("PhanTram-"+rs.getString("ten"));
									 i=i+1;
								 }
								
								rs.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						// nhiem vu phan thuc dat
						try {
							rs_nhiemvu.beforeFirst();
								while (rs_nhiemvu.next()){
									 
									 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("THUC DAT :"+rs_nhiemvu.getString("TIEUCHI"));
										cell.setStyle(style);
									 i=i+1;
								 }
								
								
							} catch (Exception e) {
								e.printStackTrace();
							}
				 
				
							 sql="select distinct nhomsanpham_fk,dbo.ftBoDau(nsp.ten) as ten  from  chitieunpp_ddkd_nhomsp "+  
								" inner join chitieunpp b on b.pk_Seq=chitieunpp_fk  "+
								" inner join nhomsanpham nsp on nsp.pk_seq=nhomsanpham_fk "+
								" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
							    
										if(obj.getdvkdId().length()>0){
										 sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
										 
										}
										if(obj.getkenhId().length()>0){
										 sql=sql+ " and b.KENH_FK= "+ obj.getkenhId();
										 
										}
										
										 rs=db.get(sql);
									
										if(rs!=null){
										
										 try {
											while (rs.next()){
												 
												 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("ChiTieu-"+rs.getString("ten"));	
												 i=i+1;
												 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("ThucDat-"+rs.getString("ten"));
												 i=i+1;
												 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("PhanTram-"+rs.getString("ten"));
												 i=i+1;
											 }
											rs.close();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										}
										
  sql=
  	" select distinct b.nhomsanpham_fk,dbo.ftBoDau(e.DIENGIAI) as ten,e.TienThuong "+
  	" from  chitieusec_gsbh_nhomsp b "+
  	"   inner join nhomfocus e on e.PK_SEQ=b.NHOMSANPHAM_FK " +
  	"  where e.thang="+obj.getMonth()+" and e.nam="+obj.getYear()+" and e.doituong=1 and  ISNHOMTHUONGSALESOUT=1  and  nhomsanpham_fk>=200000 and e.trangthai=1 ";
  
			if(obj.getdvkdId().length()>0)
			{
			 sql=sql+ " and e.dvkd_fk= "+ obj.getdvkdId();
			}
			if(obj.getkenhId().length()>0)
			{
			 sql=sql+ " and e.kbh_fk= "+ obj.getkenhId();
			 
			}
			System.out.println("sql : "+sql);
	rs=db.get(sql);		
		   try 
		   {
			while (rs.next())
			{
				 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("ChiTieuNhomThuongSelesOut-"+rs.getString("ten"));	
				 i=i+1;
				 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("ThucDatNhomThuongSelesOut-"+rs.getString("ten"));
				 i=i+1;
				 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("PhanTramNhomThuongSelesOut-"+rs.getString("ten"));
				 i=i+1;
				 cell = cells.getCell(htb.get(i)+"1"); cell.setValue(rs.getDouble("TIENTHUONG"));
				 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("ThuongNhomThuongSelesOut-"+rs.getString("ten"));
				 i=i+1;
			 }
			rs.close();
			} catch (Exception e) 
			{
	
				e.printStackTrace();
			}
				cell = cells.getCell(htb.get(i)+"2"); cell.setValue("TONG THUONG NHIEM VU(3)");	
				i=i+1;
				db.shutDown();
				
				cell = cells.getCell("M1"); 
				ReportAPI.getCellStyle(cell,Color.NAVY,true, 9,(26*4+i-1)+"");
	   
	}

	private void ThucHienChamDiemTuDong(dbutils db, IStockintransit obj) 
	{
		try
		{
			
		int thang=Integer.parseInt(obj.getMonth());
		int nam=Integer.parseInt(obj.getYear());
		if(thang==12){
				thang=1;
				nam=nam+1;
				
		}else{
			thang=thang+1;
			
		}
		
		String ngaydauthang=nam+"-"+ ((thang+"").length() >1? (""+thang):("0"+thang) )+"-01";
		
		String sql=  "  SELECT NVNV.PK_SEQ AS NVID,GSBH.GSBH_FK ,NV.ISTUDONG ,NV.TIEUCHI ,NV.LOAITIEUCHI    "+ 
					 "  , NV_CT.NHIEMVU_FK,NV_CT.THUONG,ISNULL(NV_CT.SOLUONG,0) AS SOLUONG ,   "+ 
					 "  ISNULL(KHOASO.KHOASO,0) AS dat   "+ 
					 "    FROM   "+ 
					 "  NHIEMVUNHANVIEN NVNV   INNER JOIN NHIEMVUNHANVIEN_CHITIET  NV_CT ON NV_CT.NHIEMVUNHANVIEN_FK=NVNV.PK_SEQ  "+ 
					 "  INNER JOIN NHIEMVU NV ON NV.PK_SEQ=NV_CT.NHIEMVU_FK         "+ 
					 "  INNER JOIN   "+ 
					 "  (   "+ 
					 "  SELECT DISTINCT DH.GSBH_FK AS GSBH_FK  "+ 
					 "   FROM DONHANG DH WHERE DH.NGAYNHAP LIKE '"+obj.getYear()+"-"+(obj.getMonth().length() >1 ? obj.getMonth():"0"+obj.getMonth() )+"%'  "+ 
					 "  )  GSBH    "+ 
					 "  ON 1=1    "+ 
					 "  inner JOIN   "+ 
					 "  (  "+ 
					 "  	SELECT A.GSBH_FK,1 AS KHOASO ,0 AS TYPE  FROM KHOASONGAY KSN   "+ 
					 "  	INNER JOIN NHAPP_GIAMSATBH  A ON A.NPP_FK=KSN.NPP_FK  "+ 
					 "  	WHERE KSN.NGAYKS=  Replace(convert(char(10), DATEADD(day, -1, cast('"+ngaydauthang+"' as datetime)) , 102) , '.', '-' )   "+ 
					 "  	and Replace(convert(char(10), DATEADD(day, -1, cast('"+ngaydauthang+"' as datetime)) , 102) , '.', '-' )   "+ 
					 "  	= Replace(convert(char(10), DATEADD(day, -2, KSN.NGAYGIO ) , 102) , '.', '-' )    "+ 
					 "  	union all  "+ 
					 "  	  "+ 
					 "  	SELECT DISTINCT  A.GSBH_FK AS GSBH_FK  ,1 AS DAT ,1 AS TYPE  FROM NHAPHANPHOI NPP   "+ 
					 "  	INNER JOIN NHAPP_GIAMSATBH  A ON A.NPP_FK=NPP.PK_SEQ  "+ 
					 "  	WHERE NPP.PK_SEQ NOT IN   "+ 
					 "  	(  "+ 
					 "  			SELECT NPP_FK FROM NHAPP_KHO_CHITIET KHO    "+ 
					 "  			WHERE KHO.NGAYHETHAN <REPLACE(CONVERT(CHAR(10), DATEADD(MONTH, -2, CAST('"+ngaydauthang+"' AS DATETIME)) , 102) , '.', '-' )  "+ 
					 "  	)    "+ 
					 "     "+ 
					 "  ) KHOASO ON KHOASO.GSBH_FK=GSBH.GSBH_FK AND NV.LOAITIEUCHI=KHOASO.TYPE  "+ 
					 "  WHERE NVNV.DOITUONG='SS' AND NVNV.THANG= "+obj.getMonth()+" AND NVNV.NAM= "+obj.getYear();
		ResultSet rs=db.get(sql);
		System.out.println("Tu dong Tinh Nhiem Vu : "+sql);
		
			while (rs.next()){
				
				 sql=" INSERT INTO THUCHIENNHIEMVUTHANG  (NHANVIEN_FK,NHIEMVU_FK,NHIEMVUNHANVIEN_FK,DAT) VALUES " +
						" ("+rs.getString("GSBH_FK")+","+rs.getString("NHIEMVU_FK")+","+rs.getString("NVID")+",'"+rs.getString("dat")+"')";
				 System.out.println("sql : "+sql);
				if(! db.update(sql)){
					sql="update THUCHIENNHIEMVUTHANG set dat='"+rs.getString("dat")+"' where NHIEMVUNHANVIEN_FK="+rs.getString("NVID")+" and nhiemvu_fk="+rs.getString("NHIEMVU_FK")+" and NHANVIEN_FK="+rs.getString("GSBH_FK") ;
					System.out.println("sql : "+sql);
					db.update(sql);
					
				} 
			}
			rs.close();
			
			
		
			sql=" select distinct b.NHOMSKU_FK as nhomsanpham_fk,nhom.TEN from CHITIEUNHOMSKUIN  a "+
				" inner join CHITIEUNHOMSKUIN_NHOMSKU b  on a.PK_SEQ=b.CHITIEUNHOMSKU_FK "+
				" inner join NHOMSANPHAM nhom on nhom.PK_SEQ=b.NHOMSKU_FK "+
				" where a.THANG="+obj.getMonth()+" and a.NAM= "+obj.getYear();
			
			 if(obj.getdvkdId().length()>0){
				 sql=sql+ " and a.dvkd_fk= "+ obj.getdvkdId();
				 
			 }
			 if(obj.getkenhId().length()>0){
				 sql=sql+ " and a.KBH_FK= "+ obj.getkenhId();
				 
			 }
			 sql =sql+" order by b.NHOMSKU_FK ";		
			 
			 rs=db.get(sql);
			 
			 String[] arraychuoi= new String[10];
			 String chuoiselct="1";
			 String chuoingoac="[1]";//co dau []
			 if(rs!=null){
				 int i=0;
			
					while (rs.next()){
						
						 if(i==0){
							 chuoingoac="["+rs.getString("nhomsanpham_fk")+"]";
							// chuoi=rs.getString("nhomsanpham_fk");
							 chuoiselct="isnull(NHOMSKUIN.["+rs.getString("nhomsanpham_fk")+"],0) AS NHOMSKUIN"+rs.getString("nhomsanpham_fk")+"," +"isnull(THUCDATSKUIN.["+rs.getString("nhomsanpham_fk")+"],0) AS THUCDATSKUIN"+rs.getString("nhomsanpham_fk");
						 }else{
							 //chuoi=chuoi+","+rs.getString("nhomsanpham_fk");
							 chuoiselct=chuoiselct+",isnull(NHOMSKUIN.["+rs.getString("nhomsanpham_fk")+"],0) AS NHOMSKUIN"+rs.getString("nhomsanpham_fk")+"," +"isnull(THUCDATSKUIN.["+rs.getString("nhomsanpham_fk")+"],0) AS THUCDATSKUIN"+rs.getString("nhomsanpham_fk");
							 chuoingoac=chuoingoac+",["+rs.getString("nhomsanpham_fk")+"]";
						 }
						 arraychuoi[i]=rs.getString("nhomsanpham_fk");
						 i++;
						 
					 }
				
			 }
			 
			 sql=" SELECT NHOMSKUIN.DVKD_FK,NHOMSKUIN.GSBH_FK,NHOMSKUIN.KBH_FK ,"+chuoiselct+" FROM        "+ 
				 "  (       "+ 
				 "  SELECT NPP.KHUVUC_FK,A.DVKD_FK,A.KBH_FK,NPP_GS.GSBH_FK  ,B.NHOMSKU_FK, SUM(B.CHITIEU) AS CHITIEU       "+ 
				 "  FROM CHITIEUNHOMSKUIN A        "+ 
				 "  INNER JOIN CHITIEUNHOMSKUIN_NHOMSKU B ON A.PK_SEQ=B.CHITIEUNHOMSKU_FK       "+ 
				 "  INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=B.NPP_FK       "+ 
				 "  INNER JOIN NHAPP_GIAMSATBH NPP_GS ON NPP_GS.NPP_FK = B.NPP_FK       "+ 
				 "  WHERE A.THANG="+obj.getMonth()+" AND A.NAM= "+obj.getYear()+"      "+ 
				 "  GROUP BY  NPP.KHUVUC_FK,A.DVKD_FK,A.KBH_FK,NPP_GS.GSBH_FK ,B.NHOMSKU_FK       "+ 
				 "    "+ 
				 "  ) P       "+ 
				 "  PIVOT        "+ 
				 "  (        "+ 
				 "  SUM(CHITIEU)        "+ 
				 "  FOR NHOMSKU_FK IN        "+ 
				 "  ("+chuoingoac+" )        "+ 
				 "  ) AS NHOMSKUIN   "+ 
				 "  LEFT JOIN        "+ 
				 "  (       "+ 
				 "  SELECT NPP.KHUVUC_FK,  NH.KBH_FK, SP2.DVKD_FK AS DVKD_FK, NPP_GS.GSBH_FK,        "+ 
				 "  SUM(NH_SP.SOLUONG) AS THUCDAT,C.NHOMSKU_FK       "+ 
				 "  FROM NHAPHANG NH INNER JOIN NHAPHANG_SP NH_SP ON NH.PK_SEQ = NH_SP.NHAPHANG_FK				       "+ 
				 "  INNER JOIN SANPHAM SP2 ON SP2.MA = NH_SP.SANPHAM_FK	       "+ 
				 "  INNER JOIN CHITIEUNHOMSKUIN_NHOMSKU_SP C ON C.SANPHAM_FK=SP2.PK_SEQ       "+ 
				 "  AND C.CHITIEUNHOMSKU_FK IN (SELECT PK_SEQ FROM CHITIEUNHOMSKUIN CTIN WHERE CTIN.THANG="+obj.getMonth()+"  AND CTIN.NAM="+obj.getYear()+"  )  "+ 
				 "    "+ 
				 "  INNER JOIN NHAPP_GIAMSATBH NPP_GS ON NPP_GS.NPP_FK = NH.NPP_FK        "+ 
				 "  LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=NH.NPP_FK       "+ 
				 "  WHERE   C.NHOMSKU_FK  IN       "+ 
				 "  (SELECT D.NHOMSKU_FK FROM CHITIEUNHOMSKUIN_NHOMSKU D WHERE D.NPP_FK=NPP.PK_SEQ       "+ 
				 "  ) AND        "+ 
				 "  SUBSTRING(NH.NGAYCHUNGTU,1,7)  =  '"+obj.getYear()+"-"+(obj.getMonth().length() >1 ? obj.getMonth():"0"+obj.getMonth() )+" ' AND NH.TRANGTHAI <>'2' 			       "+ 
				 "  GROUP BY NH.KBH_FK, SP2.DVKD_FK, NPP_GS.GSBH_FK ,NPP.KHUVUC_FK,C.NHOMSKU_FK       "+ 
				 "  ) P       "+ 
				 "  PIVOT        "+ 
				 "  (        "+ 
				 "  SUM(THUCDAT)        "+ 
				 "  FOR NHOMSKU_FK IN        "+ 
				 "  ("+chuoingoac+")        "+ 
				 "  ) AS THUCDATSKUIN ON THUCDATSKUIN.DVKD_FK=NHOMSKUIN.DVKD_FK AND THUCDATSKUIN.KBH_FK=NHOMSKUIN.KBH_FK        "+ 
				 "  AND NHOMSKUIN.GSBH_FK=THUCDATSKUIN.GSBH_FK AND NHOMSKUIN.KHUVUC_FK=THUCDATSKUIN.KHUVUC_FK  ";
			
			 System.out.println("GET TINT THUONG SKU IN: "+sql);
			 rs=db.get(sql);
			 
			 String[] chuoi=arraychuoi;
			 
			
			 while (rs.next()){
				 
				 String dat="1";
				 
				 for (int i=0;i<chuoi.length ;i++){
						if(chuoi[i]!=null){
							
						
							
							
						
							double PHANTRAM=0;
							if(rs.getFloat("NHOMSKUIN"+chuoi[i]) >0 ){
								PHANTRAM=rs.getFloat("THUCDATSKUIN"+chuoi[i])*100 /rs.getFloat("NHOMSKUIN"+chuoi[i]);
							}
							if(PHANTRAM <70){
								dat="0";
								break;
							}
						}
					}
				 
				
				 
			 	sql= " select "+rs.getString("GSBH_FK")+" ,NV_CT.NHIEMVU_FK,NV_CT.NHIEMVUNHANVIEN_FK ,"+dat+" "+
			 		" FROM NHIEMVUNHANVIEN NVNV     "+
					" INNER JOIN NHIEMVUNHANVIEN_CHITIET  NV_CT ON NV_CT.NHIEMVUNHANVIEN_FK=NVNV.PK_SEQ  "+  
					" INNER JOIN NHIEMVU NV ON NV.PK_SEQ=NV_CT.NHIEMVU_FK           "+
					" WHERE NVNV.DOITUONG='SS' AND NVNV.THANG="+obj.getMonth()+" AND NVNV.NAM= "+obj.getYear()+" AND NV.LOAITIEUCHI=2  "+ 
					 " and NVNV.KBH_FK="+rs.getString("kbh_fk")+" and NVNV.DVKD_FK="+rs.getString("DVKD_FK") ;
			 	ResultSet rscheck=db.get(sql);
			 	if(rscheck.next()){
			 		 sql="INSERT INTO THUCHIENNHIEMVUTHANG  (NHANVIEN_FK,NHIEMVU_FK,NHIEMVUNHANVIEN_FK,DAT) " +
			 		 		" values ("+rs.getString("DVKD_FK")+","+rscheck.getString("NHIEMVU_FK")+" ,"+rscheck.getString("NHIEMVUNHANVIEN_FK")+","+dat+")";
			 		 if(!db.update(sql)){
			 			sql="update THUCHIENNHIEMVUTHANG set dat="+dat+" where NHIEMVUNHANVIEN_FK="+rscheck.getString("NHIEMVUNHANVIEN_FK")+" and nhiemvu_fk="+rscheck.getString("NHIEMVU_FK")+" and NHANVIEN_FK="+rs.getString("GSBH_FK") ;
						System.out.println("sql : "+sql);
						db.update(sql);
			 			 
			 		 }
			 		 
			 	}
			 	rscheck.close();
				 db.update(sql);
				 
			 }
			
			 
			 
		
		}catch(Exception er){
			er.printStackTrace();
		}
		
		
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
		
		Cell cell1=cells.getCell("M1");
		Cell cell2=cells.getCell("N1");
		Style style;
		style = cell1.getStyle();
		Style style1;
		style1 = cell2.getStyle();

		
		
		
		
		String	sql="select distinct nhomsanpham_fk,dbo.ftBoDau(nsp.ten) as ten  from  chitieunpp_ddkd_nhomsp "+  
		" inner join chitieunpp b on b.pk_Seq=chitieunpp_fk  "+
		" inner join nhomsanpham nsp on nsp.pk_seq=nhomsanpham_fk "+
		" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;

	if(obj.getdvkdId().length()>0){
	 sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
	 
	}
	if(obj.getkenhId().length()>0){
	 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
	 
	}
		ResultSet  rs=db.get(sql);
			
			 String[] arraychuoiskuout= new String[20];
			
			 
			 if(rs!=null){
				 int i=0;
				 try {
					while (rs.next()){
						
						 arraychuoiskuout[i]=rs.getString("nhomsanpham_fk");
						 i++;
						 
					 }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		rs = db.get(query);
		
		// NHÓM THƯỞNG SALESOUT ISNHOMTHUONGSALESOUT=1
		sql=
	  	" select distinct b.nhomsanpham_fk,dbo.ftBoDau(e.DIENGIAI) as ten,e.TienThuong "+
	  	"  from  chitieusec_gsbh_nhomsp b "+
	  	"   inner join nhomfocus e on e.PK_SEQ=b.NHOMSANPHAM_FK " +
	  	"  where e.thang="+obj.getMonth()+" and e.nam="+obj.getYear()+"   and e.doituong=1 and  ISNHOMTHUONGSALESOUT=1  and  nhomsanpham_fk >=200000 and e.trangthai=1 ";
		if(obj.getdvkdId().length()>0)
		{
		 sql=sql+ " and e.dvkd_fk= "+ obj.getdvkdId();
		}
		if(obj.getkenhId().length()>0)
		{
		 sql=sql+ " and e.KBH_FK= "+ obj.getkenhId();
		 
		}
		System.out.println("Get thuong ss : "+sql);
		rs=db.get(sql);
		String[] thuongsalesout= new String[20];
	 if(rs!=null)
	 {
		 int i=0;
		 try 
		 {
			while (rs.next())
			{
				thuongsalesout[i]=rs.getString("nhomsanpham_fk");i++;				 
			 }
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	 }
		
		rs=db.get(query);
		int indexRow = 3;
		int dem=1;
		try 
			{				
				Cell cell = null;
				float phantramMTD = 0;
				
				double tongthuog=0;
				double thuongSLBanRa=0;
				double thuongnhiemvu=0;
				while(rs.next())
				{ 				
					thuongnhiemvu=0;
					thuongSLBanRa=0;
					tongthuog=0;
				    cell = cells.getCell("DA" + Integer.toString(indexRow)); cell.setValue(dem);
				    cell.setStyle(style);
				    cell = cells.getCell("DB" + Integer.toString(indexRow)); cell.setValue(rs.getString("GSBHID"));
				    cell.setStyle(style);
				    cell = cells.getCell("DC" + Integer.toString(indexRow)); cell.setValue(rs.getString("GSBHTEN"));
				    cell.setStyle(style);
					cell = cells.getCell("DD" + Integer.toString(indexRow)); cell.setValue("SS");		
					cell.setStyle(style);
					cell = cells.getCell("DE" + Integer.toString(indexRow));cell.setValue(rs.getString("TEN"));				
					cell.setStyle(style);
					
					
					String []chuoi =obj.getFieldShow();
					
					String []chuoinv =obj.getFieldHidden();
					int j=9;
					
					for (int i=0;i<chuoinv.length ;i++){
						if(chuoinv[i]!=null){
							
							
							cell = cells.getCell(htb.get(j)+"7");
							
							double thuong=0;
							try{
								thuong=(Double)cell.getValue();
							}catch(Exception er){
								er.printStackTrace();
							}
							thuongnhiemvu=thuongnhiemvu + rs.getFloat("NHIEMVU"+chuoinv[i])*thuong;
							
							cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue( rs.getFloat("NHIEMVU"+chuoinv[i])*thuong );
							cell.setStyle(style);
							j=j+1;
						}
						
						
					}
					
					
					
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("CHITIEUSEC"));
					cell.setStyle(style);
					j=j+1;
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("DSSEC"));
					cell.setStyle(style);
					j=j+1;
					
					//System.out.println("[DoanhSoSalesOut]"+rs.getDouble("dssec"));
					
					double phantramsec=0;
					if(rs.getDouble("CHITIEUSEC") >0 ){
						phantramsec=rs.getDouble("DSSEC")*100 /rs.getDouble("CHITIEUSEC");
					}
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(phantramsec);
					cell.setStyle(style);
					j=j+1;
					
					 sql=" SELECT B.Thuong  FROM TIEUCHITINHTHUONG A INNER JOIN TIEUCHITINHTHUONG_CHITIET B  "+
					" ON A.PK_SEQ=B.TIEUCHITINHTHUONG_FK "+
					" WHERE A.LOAI=2 AND THANG="+obj.getMonth()+" AND NAM="+obj.getYear()+" AND B.TIEUCHI=2 AND B.Tu < " +phantramsec+" AND B.Den >"+phantramsec;
					ResultSet rsthuong =db.get(sql);
					double thuongsec=0;
					if(rsthuong!=null){
					if(rsthuong.next()){
						
							thuongsec=rsthuong.getDouble("thuong");
						
					}
					rsthuong.close();
					}
					System.out.println(htb.get(7));
					
					cell = cells.getCell(htb.get(7)+ Integer.toString(indexRow)); cell.setValue(thuongsec);
					cell.setStyle(style);
				
					
					// thuc dat Pri
					
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("CHITIEUPRI"));
					cell.setStyle(style);
					j=j+1;
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("DS_PRI"));
					cell.setStyle(style);
					j=j+1;
					
					double phantramPRI=0;
					if(rs.getDouble("CHITIEUPRI") >0 ){
						phantramPRI=rs.getDouble("DS_PRI")*100 /rs.getDouble("CHITIEUPRI");
					}
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(phantramPRI);
					cell.setStyle(style);
					j=j+1;
					Double phantramtk_dspre=0.0;
					if(rs.getDouble("DSSEC_PRE") >0){
						phantramtk_dspre= rs.getDouble("doanhsoton")*100 / rs.getDouble("DSSEC_PRE");
					}
					
					 sql=
					"SELECT B.Thuong  FROM TIEUCHITINHTHUONG A INNER JOIN TIEUCHITINHTHUONG_CHITIET B  "+
					" ON A.PK_SEQ=B.TIEUCHITINHTHUONG_FK "+
					" WHERE A.LOAI=2 AND THANG="+obj.getMonth()+" AND NAM="+obj.getYear()+" AND B.TIEUCHI=1 AND B.Tu < " +phantramPRI+" AND B.Den >"+phantramPRI;
					 rsthuong =db.get(sql);
					 
					// System.out.println("[ThuongPri(SalesIn)]"+sql);
					 
					double thuongpri=0;
					if(rsthuong!=null)
					{
						if(rsthuong.next())
						{
							thuongpri=rsthuong.getDouble("thuong");
						//	System.out.println("[ThuongPri(SalesIn)]"+thuongpri);
						}
						rsthuong.close();
					}
					cell = cells.getCell(htb.get(8)+ Integer.toString(indexRow)); cell.setValue(thuongpri);
					
					cell.setStyle(style);
					
					
					for (int i=0;i<chuoi.length ;i++)
					{
						if(chuoi[i]!=null)
						{
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("NHOMSKUIN"+chuoi[i]));cell.setStyle(style);j=j+1;
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("THUCDATSKUIN"+chuoi[i]));cell.setStyle(style);j=j+1;
							double PHANTRAM=0;
							if(rs.getDouble("NHOMSKUIN"+chuoi[i]) >0 ){
								PHANTRAM=rs.getDouble("THUCDATSKUIN"+chuoi[i])*100 /rs.getDouble("NHOMSKUIN"+chuoi[i]);
							}
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(PHANTRAM);
							cell.setStyle(style);
							
							j=j+1;
						}
						
						
					}
					
					
					for (int i=0;i<chuoinv.length ;i++)
					{
						if(chuoinv[i]!=null)
						{
							String duyetdat="";
							if(rs.getString("NHIEMVU"+chuoinv[i]).trim().equals("1"))
							{
								duyetdat="Đạt";
							}
							cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue(duyetdat);
							cell.setStyle(style);
							j=j+1;
							
						}
					}
					
					
					
					float SumThucDatCTDDKD=0;
					for (int i=0;i<arraychuoiskuout.length ;i++)
					{
						if(arraychuoiskuout[i]!=null)
						{
							cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue( rs.getFloat("CTNHOM"+arraychuoiskuout[i]));	
							SumThucDatCTDDKD= SumThucDatCTDDKD+rs.getFloat("DS1"+arraychuoiskuout[i]);
							j=j+1;
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("DS1"+arraychuoiskuout[i]));
							j=j+1;
							phantramMTD =0;
							
							if(rs.getFloat("CTNHOM"+arraychuoiskuout[i]) >0)
							{
								phantramMTD=rs.getFloat("DS1"+arraychuoiskuout[i])*100/rs.getFloat("CTNHOM"+arraychuoiskuout[i]);
							}
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(phantramMTD);
							j=j+1;
						}
					}
					double totalthuongctSalesOut=0;
					for (int i=0;i<thuongsalesout.length ;i++)
					{
						if(thuongsalesout[i]!=null)
						{
							cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue( rs.getFloat("ctSalesOut"+thuongsalesout[i]));	
							j=j+1;
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("tdSalesOut"+thuongsalesout[i]));
							j=j+1;
							phantramMTD =0;
							if(rs.getFloat("ctSalesOut"+thuongsalesout[i]) >0)
							{
								phantramMTD=rs.getFloat("tdSalesOut"+thuongsalesout[i])*100/rs.getFloat("ctSalesOut"+thuongsalesout[i]);
							}
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(phantramMTD);
							j=j+1;							
							cell = cells.getCell(htb.get(j)+"1");
							
							double thuong=0;
							try
							{					 
								thuong=(Double)cell.getValue();
							}catch(Exception er)
							{
							} 
							if(phantramMTD>=100)
							{
								cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(thuong);
								totalthuongctSalesOut=thuong;
							}else
							{
								cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(0);
							}
							j=j+1;
						}
					}
					
					cell = cells.getCell("DF" + Integer.toString(indexRow));cell.setValue(thuongsec+thuongnhiemvu+thuongpri+totalthuongctSalesOut);
					cell.setStyle(style);
					
					cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue(thuongnhiemvu);
					cell.setStyle(style);
				 
					indexRow=indexRow+1;
					dem=dem+1;
				
				}
				if(rs != null) rs.close();
				if(db!=null){db.shutDown();}
			}catch(Exception err)
			{
				err.printStackTrace();
				throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :"+err.toString());
			}
	}
}
*/