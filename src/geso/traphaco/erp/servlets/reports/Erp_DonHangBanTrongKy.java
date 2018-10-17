package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.dms.distributor.servlets.ajax.material;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

public class Erp_DonHangBanTrongKy extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public Erp_DonHangBanTrongKy() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
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
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangBanTK.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		obj.setdiscount("1");
		obj.setvat("1");
		
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
		
		//obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))!=null?
		//		util.antiSQLInspection(request.getParameter("nppId")):"");
		
		obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))!= null?
				util.antiSQLInspection(request.getParameter("dvkdId")):"");
		
		obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nhanhangId"))!= null?
			util.antiSQLInspection(request.getParameter("nhanhangId")):"");
		obj.setchungloaiId(util.antiSQLInspection(request.getParameter("chungloaiId"))!= null?
				util.antiSQLInspection(request.getParameter("chungloaiId")):"");
		
		String[] khachhangIds = request.getParameterValues("khIds");
		String khId = "";
		if (khachhangIds != null)
		{
			for (int i = 0; i < khachhangIds.length; i++)
				khId += "'" + khachhangIds[i] + "',";
			if (khId.length() > 0)
				khId = khId.substring(0, khId.length() - 1);
			obj.setKhachhangIds(khId);
		}
		
		String tuthang=request.getParameter("tuthang").length()< 2 ? ("0"+request.getParameter("tuthang")) :request.getParameter("tuthang") ;
		String toithang=request.getParameter("denthang").length()< 2 ? ("0"+request.getParameter("denthang")) :request.getParameter("denthang") ;
		obj.setFromMonth(tuthang);
		
		obj.setToMonth(toithang);
			obj.setToYear(request.getParameter("dennam"));
			obj.setFromYear(request.getParameter("tunam"));
		String type= request.getParameter("xemtheo");
		
		
		obj.settype(type);
		
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
		
		
		String hangtrave = request.getParameter("hangtrave");
		if(hangtrave == null)
			hangtrave = "0";
	
		obj.sethangtrave(hangtrave);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";

	
		geso.traphaco.center.util.Utility utilcenter = new geso.traphaco.center.util.Utility();
		
		String sql ="";
		//sql += " and npp.pk_seq in " + utilcenter.quyen_npp(obj.getuserId()) + " and kbh.pk_seq in  " + utilcenter.quyen_kenh(obj.getuserId()) + " and sp.pk_seq in " + utilcenter.quyen_sanpham(obj.getuserId()); 
		
		if (obj.getnhanhangId().length() > 0)
			sql += " and sp.nhanhang_fk ='" + obj.getnhanhangId() + "'";
		
		if (obj.getdvkdId().length() > 0)
			sql += sql + " and sp.dvkd_fk = '" + obj.getdvkdId() + "'";
		
		if(obj.getkenhId().length() >0){
			sql += " and dh.KBH_FK="+obj.getkenhId()+"   ";
		}
		
		if(obj.getchungloaiId().length() >0){
			sql += " and sp.chungloai_fk="+obj.getchungloaiId()+"   ";
		}
		
		if(obj.getKhachhangIds().length() > 0){
			sql += " and dh.khachhang_fk in ("+obj.getKhachhangIds()+")  ";
		}
		
		if(trangthai.length() > 0)
			sql += sql + " and dh.trangthai = '" + trangthai + "'";
		
		System.out.println("TRANG THAI la: " + trangthai + "\n");
		System.out.println("SQL la: " + sql + "\n");
		
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangBanTK.jsp";
		
		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=DonHangBanTrongKy.xlsm");
			
			String query = setQuery(obj, sql); 

	        try 
	        {
				if(!CreatePivotTable(out, response, request, obj, query, trangthai))
				{
					response.setContentType("text/html");
				    PrintWriter writer = new PrintWriter(out);
				    writer.print("Xin loi khong co bao cao trong thoi gian nay");
				    writer.close();
				}
			} 
	        catch (Exception e) 
	         { 
	        	obj.init();
	        	
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
			}
		}else{
		
		obj.init();
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		return;
		}
	}
	
	public String setQuery(IStockintransit obj, String sql)
	{
		String query = 	"select  A.* from    \n"+
				"(select kbh.DIENGIAI as kenh, kh.Ma as khma, kh.Ten as khten,dh.NGAYDAT as ngaychungtu, dh.PK_SEQ as sochungtu, sp.MA as spma, sp.TEN as spten,     \n"+
				"dvdl.DIENGIAI as dvdlten, dh_sp.SOLUONG, dh_sp.DONGIA, round((dh_sp.SOLUONG* dh_sp.dongiaviet+0.0001),0) as bvat,    \n"+
				"ISNULL(dh.CKTHUONGMAI,0) as ck, ISNULL(dh.VAT,0) as sovat, cl.PK_SEQ as chungloai_fk, dh.TRANGTHAI as trangthai,    \n"+
				"isnull((select SUM(xksp.SOLUONG)    \n"+
				"from ERP_XUATKHO_SANPHAM xksp    \n"+
				"inner join ERP_XUATKHO xk on xk.PK_SEQ= xksp.XUATKHO_FK    \n"+
				"inner join erp_hoadon hd on hd.PK_SEQ= xk.HOADON_FK    \n"+
				"inner join ERP_HOADON_DDH hd_dh on hd.PK_SEQ= hd_dh.HOADON_FK    \n"+
				"where xk.TRANGTHAI <>2 and hd_dh.DDH_FK=dh.PK_SEQ and xksp.SANPHAM_FK= dh_sp.SANPHAM_FK ),0) as soluongdaxuat,    \n"+
				"dh_sp.SOLUONG - isnull((select SUM(xksp.SOLUONG)    \n"+
				"from ERP_XUATKHO_SANPHAM xksp    \n"+
				"inner join ERP_XUATKHO xk on xk.PK_SEQ= xksp.XUATKHO_FK    \n"+
				"inner join erp_hoadon hd on hd.PK_SEQ= xk.HOADON_FK    \n"+
				"inner join ERP_HOADON_DDH hd_dh on hd.PK_SEQ= hd_dh.HOADON_FK    \n"+
				"where xk.TRANGTHAI <>2 and hd_dh.DDH_FK=dh.PK_SEQ and xksp.SANPHAM_FK= dh_sp.SANPHAM_FK ),0) as soluongconlai,    \n"+
				" kbh.pk_seq as kenh_fk, cl.ten as clten, kh.pk_seq as khId   \n"+
				"from ERP_DONDATHANG dh    \n"+
				"inner join ERP_DONDATHANG_SP dh_sp on dh.PK_SEQ= dh_sp.DONDATHANG_FK    \n"+
				"inner join ERP_KHACHHANG kh on dh.KHACHHANG_FK= kh.PK_SEQ    \n"+
				"inner join ERP_SANPHAM sp on dh_sp.SANPHAM_FK= sp.PK_SEQ    \n"+
				"left join CHUNGLOAI cl on sp.CHUNGLOAI_FK= cl.PK_SEQ    \n"+
				"left join DONVIDOLUONG dvdl on dh_sp.DVDL_FK= dvdl.PK_SEQ    \n"+
				"left join KENHBANHANG kbh on kbh.PK_SEQ= kh.KenhBanHang_FK    \n"+
				"where dh.TRANGTHAI <>7 and dh.ngaydat >='"+obj.gettungay()+"' and dh.ngaydat <='"+obj.getdenngay()+"'  "+sql+") A where 1=1     ";
		
/*
		if(sql.trim().length() > 0)
			query += sql;*/
		
		query += " order by A.ngaychungtu ";
		
		System.out.println(query);
		return query;   
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query, String trangthai) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		if(obj.gettype().trim().equals("0")) //Xem theo Ngay
		{
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ERP_DONHANGBANTK.xlsm");
		}
		else
		{
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ERP_DONHANGBANTK_Thang.xlsm");
		}
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

	/*	CreateHeader(workbook,obj);*/
		isFillData = FillData(workbook, obj, query, trangthai);
   
		if (!isFillData)
		{
			if(fstream != null)
				fstream.close();
			return false;
		}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	private void CreateHeader(Workbook workbook, IStockintransit obj)throws Exception
	{	
 		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		
		String col = "";
		String row = "11";  
		
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
	    
	    String tieude = "BÁO CÁO ĐƠN HÀNG BÁN TRONG KỲ";
	    if(obj.gettype().trim().equals("1")) {
	    	tieude = "BÁO CÁO ĐƠN HÀNG BÁN TRONG KỲ THEO THÁNG";
	    	
	    }
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	            
	    String message = "";
		 if(Integer.parseInt(obj.getpromotion()) != 0) // khng lay khuyen mai + trung bay
		 {
			 message += "Không đơn hàng  khuyến mại";
		 }
		 else
		 {
			 message += " Có đơn hàng khuyến mại";
		 }
		
	    
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    
	    if(obj.gettype().trim().equals("0")){
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
	    }
	    else
	    {
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ tháng : " + obj.getFromMonth() + "" );
	    }
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B4"); 
	    if(obj.gettype().trim().equals("0")){
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
	    }
	    else{
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến tháng : " + obj.getToMonth() + "" );
	    }
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    		
		cell = cells.getCell(col+"A"+row);		cell.setValue("KenhBanHang");			ReportAPI.setCellHeader(cell);//1
	    cell = cells.getCell(col+"B"+row);		cell.setValue("DonViKinhDoanh");		ReportAPI.setCellHeader(cell);///2
		cell = cells.getCell(col+"C"+row);		cell.setValue("NhanHang");				ReportAPI.setCellHeader(cell);//3
		
		cell = cells.getCell(col+"D"+row);		cell.setValue("LoaiHoaDon");			ReportAPI.setCellHeader(cell);//5
		
		cell = cells.getCell(col+"E"+row);		cell.setValue("MaKhachHang");			ReportAPI.setCellHeader(cell);//6
		cell = cells.getCell(col+"F"+row);		cell.setValue("KhachHang");				ReportAPI.setCellHeader(cell);//7
		
		cell = cells.getCell(col+"G"+row);
		if(obj.gettype().trim().equals("0")) //Xem theo Ngay
			cell.setValue("NgayChungTu");
		else
			cell.setValue("Thang");//17
		
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell(col+"H"+row);		cell.setValue("SoChungTu");				ReportAPI.setCellHeader(cell);//8
		cell = cells.getCell(col+"I"+row);		cell.setValue("SoHopDong");				ReportAPI.setCellHeader(cell);//9		
		
		
		cell = cells.getCell(col+"J"+row);		cell.setValue("ChungLoai");			    ReportAPI.setCellHeader(cell);//4
		cell = cells.getCell(col+"K"+row);		cell.setValue("MaSanPham");			    ReportAPI.setCellHeader(cell);//15
		cell = cells.getCell(col+"L"+row);		cell.setValue("TenSanPham");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell(col+"M"+row);		cell.setValue("DonViTinh");				ReportAPI.setCellHeader(cell);//17
										
		cell = cells.getCell(col+"N"+row);		cell.setValue("SoLuong");				ReportAPI.setCellHeader(cell);//18
		cell = cells.getCell(col+"O"+row);		cell.setValue("DonGia");				ReportAPI.setCellHeader(cell);//19
				
		cell = cells.getCell(col+"P"+row);		cell.setValue("SoluongDaGiao");				ReportAPI.setCellHeader(cell);//9
		cell = cells.getCell(col+"Q"+row);		cell.setValue("SoluongConlai");				ReportAPI.setCellHeader(cell);//8
		
		cell = cells.getCell(col+"R"+row);		cell.setValue("TongTienTruocCK");		ReportAPI.setCellHeader(cell);//10
		cell = cells.getCell(col+"S"+row);		cell.setValue("ChietKhau");				ReportAPI.setCellHeader(cell);//11
		cell = cells.getCell(col+"T"+row);		cell.setValue("%Thue");				    ReportAPI.setCellHeader(cell);//12
		cell = cells.getCell(col+"U"+row);		cell.setValue("TienThue");				ReportAPI.setCellHeader(cell);//13
		cell = cells.getCell(col+"V"+row);		cell.setValue("TongTienSauThue");		ReportAPI.setCellHeader(cell);//14
		
		cell = cells.getCell(col+"W"+row);		cell.setValue("NgayDuKien");			ReportAPI.setCellHeader(cell);//21
		cell = cells.getCell(col+"X"+row);		cell.setValue("NgayThucTe");			ReportAPI.setCellHeader(cell);//22
		
	}
	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query, String trangthai) throws Exception
	{
		
		System.out.println(" vao day");
		NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		String col = "";
		
		Cells cells = worksheet.getCells();	
		Cell cell = null;
		for(int i = 0;i < 7; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
	    }	
		
		cell = cells.getCell("A4"); 
		cell.setValue("Từ ngày :"+obj.gettungay());	
		cell = cells.getCell("B4"); 
		cell.setValue("Đến ngày : "+obj.getdenngay());	
		
		query  = "select  A.* from    \n"+
				" ("+
				" select kbh.DIENGIAI as kenh, kh.Ma as khma, kh.Ten as khten,dh.NGAYDAT as ngaychungtu, dh.PK_SEQ as sochungtu, sp.MA as spma, sp.TEN as spten, \n"+     
				" dvdl.DIENGIAI as dvdlten, dh_sp.SOLUONG, dh_sp.DONGIA, dh_sp.dongiaCK as dongiaCK, round((dh_sp.SOLUONG* dh_sp.dongiaCK+0.0001),0) as bvat,    \n"+
				" ISNULL(dh.CKTHUONGMAI,0) as ck, ISNULL(dh.VAT,'0') as sovat, cl.PK_SEQ as chungloai_fk, dh.TRANGTHAI as trangthai, sp.dvkd_fk, sp.nhanhang_fk,    \n"+
				" ISNULL(CASE WHEN XKDAGIAO.DVDL_FK = dh_sp.DVDL_FK THEN SUM(XKDAGIAO.SOLUONG) ELSE SUM(XKDAGIAO.SOLUONG*QC.SOLUONG2/QC.SOLUONG1) END ,0) as soluongdaxuat, \n"+    
				" dh_sp.SOLUONG - ISNULL((  CASE WHEN XKDAGIAO.DVDL_FK = dh_sp.DVDL_FK THEN SUM(XKDAGIAO.SOLUONG) ELSE SUM(XKDAGIAO.SOLUONG*QC.SOLUONG2/QC.SOLUONG1) END),0) as soluongconlai, \n"+    
				" kbh.pk_seq as kenh_fk, cl.ten as clten,  \n"+
				" case  \n"+
				" when dh.LOAIDONHANG=1 then N'Đơn hàng bán' \n"+   
				" else ''  \n"+
				" end as loaidonhang, '' as scheme , kh.pk_seq as khId, " +
				"   ROW_NUMBER() OVER(PARTITION BY dh.PK_SEQ ORDER BY dh.PK_SEQ ASC) AS Row    \n"+   
				" from ERP_DONDATHANG dh    \n"+
				" inner join ERP_DONDATHANG_SP dh_sp on dh.PK_SEQ= dh_sp.DONDATHANG_FK \n"+    
				" inner join ERP_KHACHHANG kh on dh.KHACHHANG_FK= kh.PK_SEQ    \n"+
				" inner join ERP_SANPHAM sp on dh_sp.SANPHAM_FK= sp.PK_SEQ    \n"+
				" left join CHUNGLOAI cl on sp.CHUNGLOAI_FK= cl.PK_SEQ    \n"+
				" left join DONVIDOLUONG dvdl on dh_sp.DVDL_FK= dvdl.PK_SEQ   \n"+ 
				" left join KENHBANHANG kbh on kbh.PK_SEQ= dh.KBH_FK   \n"+
				" LEFT JOIN QUYCACH QC ON QC.DVDL2_FK = dh_sp.DVDL_FK AND dh_sp.SANPHAM_FK = QC.SANPHAM_FK \n"+ 
				" LEFT JOIN (	SELECT	XK_SP.SANPHAM_FK,SUM(XK_SP.SOLUONG) SOLUONG, SP.DVDL_FK, XK.DONDATHANG_FK \n"+ 
				"								FROM	ERP_XUATKHO XK INNER JOIN ERP_XUATKHO_SANPHAM XK_SP ON XK.PK_SEQ = XK_SP.XUATKHO_FK \n"+ 
				"										INNER JOIN ERP_SANPHAM SP ON XK_SP.SANPHAM_FK =  SP.PK_SEQ 	 \n"+
				"								WHERE   1 = 1 AND XK.DONDATHANG_FK IS NOT NULL AND XK.TRANGTHAI IN (1,3,4) and XK.NOIDUNGXUAT = '100002' \n"+
				"								GROUP BY XK_SP.SANPHAM_FK,  SP.DVDL_FK, XK.DONDATHANG_FK ) XKDAGIAO ON dh.PK_SEQ = XKDAGIAO.DONDATHANG_FK and dh_sp.SANPHAM_FK = XKDAGIAO.SANPHAM_FK \n"+

									
				" where dh.TRANGTHAI <>7 and dh.ngaydat >='"+obj.gettungay()+"' and dh.ngaydat <='"+obj.getdenngay()+"' and dh.LOAIDONHANG = 1 \n"+

				" GROUP BY  kbh.PK_SEQ,kbh.DIENGIAI,kh.Ma  ,dh.NGAYDAT , dh.PK_SEQ , sp.MA , sp.TEN ,  cl.PK_SEQ,cl.TEN, dh.LOAIDONHANG,kh.PK_SEQ, \n"+   
				" dvdl.DIENGIAI, dh_sp.SOLUONG, dh_sp.DONGIA, dh_sp.dongiaCK, kh.Ten, \n"+
				" dh.CKTHUONGMAI, dh.VAT, cl.PK_SEQ , dh.TRANGTHAI , sp.dvkd_fk, sp.nhanhang_fk, XKDAGIAO.DVDL_FK,     dh_sp.DVDL_FK \n"+

				" UNION ALL \n"+

				" select kbh.DIENGIAI as kenh, kh.Ma as khma, kh.Ten as khten,dh.NGAYDAT as ngaychungtu, dh.PK_SEQ as sochungtu, sp.MA as spma, sp.TEN as spten, \n"+     
				" dvdl.DIENGIAI as dvdlten, dh_sp.SOLUONG, dh_sp.DONGIA, dh_sp.dongiaCK as dongiaCK, round((dh_sp.SOLUONG* dh_sp.dongiaCK+0.0001),0) as bvat,    \n"+
				" ISNULL(dh.CKTHUONGMAI,0) as ck, ISNULL(dh.VAT,'0') as sovat, cl.PK_SEQ as chungloai_fk, dh.TRANGTHAI as trangthai, sp.dvkd_fk, sp.nhanhang_fk,    \n"+
				" ISNULL(CASE WHEN XKDAGIAO.DVDL_FK = dh_sp.DVDL_FK THEN SUM(XKDAGIAO.SOLUONG) ELSE SUM(XKDAGIAO.SOLUONG*QC.SOLUONG2/QC.SOLUONG1) END ,0) as soluongdaxuat, \n"+    
				" dh_sp.SOLUONG - ISNULL((  CASE WHEN XKDAGIAO.DVDL_FK = dh_sp.DVDL_FK THEN SUM(XKDAGIAO.SOLUONG) ELSE SUM(XKDAGIAO.SOLUONG*QC.SOLUONG2/QC.SOLUONG1) END),0) as soluongconlai, \n"+    
				" kbh.pk_seq as kenh_fk, cl.ten as clten,  \n"+
				" case  \n"+
				" when dh.LOAIDONHANG=1 then N'Đơn hàng km' \n"+   
				" else ''  \n"+
				" end as loaidonhang, '' as scheme , kh.pk_seq as khId, \n"+ 
				"   ROW_NUMBER() OVER(PARTITION BY dh.PK_SEQ ORDER BY dh.PK_SEQ ASC) AS Row    \n"+   
				" from ERP_DONDATHANG dh \n"+   
				" inner join ERP_DONDATHANG_SP dh_sp on dh.PK_SEQ= dh_sp.DONDATHANG_FK \n"+    
				" inner join ERP_KHACHHANG kh on dh.KHACHHANG_FK= kh.PK_SEQ    \n"+
				" inner join ERP_SANPHAM sp on dh_sp.SANPHAM_FK= sp.PK_SEQ \n"+   
				" left join CHUNGLOAI cl on sp.CHUNGLOAI_FK= cl.PK_SEQ \n"+   
				" left join DONVIDOLUONG dvdl on dh_sp.DVDL_FK= dvdl.PK_SEQ \n"+    
				" left join KENHBANHANG kbh on kbh.PK_SEQ= dh.KBH_FK   \n"+
				" LEFT JOIN QUYCACH QC ON QC.DVDL2_FK = dh_sp.DVDL_FK AND dh_sp.SANPHAM_FK = QC.SANPHAM_FK \n"+ 
				" LEFT JOIN (	SELECT	XK_SP.SANPHAM_FK,SUM(XK_SP.SOLUONG) SOLUONG, SP.DVDL_FK, XK.DONDATHANG_FK \n"+ 
				" 								FROM	ERP_XUATKHO XK INNER JOIN ERP_XUATKHO_SANPHAM XK_SP ON XK.PK_SEQ = XK_SP.XUATKHO_FK \n"+ 
				"										INNER JOIN ERP_SANPHAM SP ON XK_SP.SANPHAM_FK =  SP.PK_SEQ 	 \n"+
				"								WHERE   1 = 1 AND XK.DONDATHANG_FK IS NOT NULL AND XK.TRANGTHAI IN (1,3,4) and XK.NOIDUNGXUAT = '100008' \n"+
				"								GROUP BY XK_SP.SANPHAM_FK,  SP.DVDL_FK, XK.DONDATHANG_FK ) XKDAGIAO ON dh.PK_SEQ = XKDAGIAO.DONDATHANG_FK and dh_sp.SANPHAM_FK = XKDAGIAO.SANPHAM_FK \n"+
				
									
				" where dh.TRANGTHAI <>7 and dh.ngaydat >='"+obj.gettungay()+"' and dh.ngaydat <='"+obj.getdenngay()+"' and dh.LOAIDONHANG = 6 \n"+

				" GROUP BY  kbh.PK_SEQ,kbh.DIENGIAI,kh.Ma  ,dh.NGAYDAT , dh.PK_SEQ , sp.MA , sp.TEN ,  cl.PK_SEQ,cl.TEN, dh.LOAIDONHANG,kh.PK_SEQ, \n"+   
				" dvdl.DIENGIAI, dh_sp.SOLUONG, dh_sp.DONGIA, dh_sp.dongiaCK, kh.Ten, \n"+
				" dh.CKTHUONGMAI, dh.VAT, cl.PK_SEQ , dh.TRANGTHAI , sp.dvkd_fk, sp.nhanhang_fk, XKDAGIAO.DVDL_FK,     dh_sp.DVDL_FK \n"+

				" UNION ALL \n"+

				" select kbh.DIENGIAI as kenh, kh.Ma as khma, kh.Ten as khten,dh.NGAYDAT as ngaychungtu, dh.PK_SEQ as sochungtu, sp.MA as spma, sp.TEN as spten, \n"+     
				" dvdl.DIENGIAI as dvdlten, dh_sp.SOLUONG, dh_sp.DONGIA, dh_sp.dongiaCK as dongiaCK, round((dh_sp.SOLUONG* dh_sp.dongiaCK+0.0001),0) as bvat,    \n"+
				" ISNULL(dh.CKTHUONGMAI,0) as ck, ISNULL(dh.VAT,'0') as sovat, cl.PK_SEQ as chungloai_fk, dh.TRANGTHAI as trangthai, sp.dvkd_fk, sp.nhanhang_fk,    \n"+
				" ISNULL(CASE WHEN XKDAGIAO.DVDL_FK = dh_sp.DVDL_FK THEN SUM(XKDAGIAO.SOLUONG) ELSE SUM(XKDAGIAO.SOLUONG*QC.SOLUONG2/QC.SOLUONG1) END ,0) as soluongdaxuat, \n"+    
				" dh_sp.SOLUONG - ISNULL((  CASE WHEN XKDAGIAO.DVDL_FK = dh_sp.DVDL_FK THEN SUM(XKDAGIAO.SOLUONG) ELSE SUM(XKDAGIAO.SOLUONG*QC.SOLUONG2/QC.SOLUONG1) END),0) as soluongconlai, \n"+    
				" kbh.pk_seq as kenh_fk, cl.ten as clten,  \n"+
				" case  \n"+
				" when dh.LOAIDONHANG=1 then N'Đơn hàng km' \n"+   
				" else ''  \n"+
				" end as loaidonhang, '' as scheme , kh.pk_seq as khId, \n"+ 
				"   ROW_NUMBER() OVER(PARTITION BY dh.PK_SEQ ORDER BY dh.PK_SEQ ASC) AS Row    \n"+   
				" from ERP_DONDATHANG dh \n"+   
				" inner join ERP_DONDATHANG_CTKM_TRAKM dh_sp on dh.PK_SEQ= dh_sp.DONDATHANGID    \n"+   
				" inner join ERP_KHACHHANG kh on dh.KHACHHANG_FK= kh.PK_SEQ    \n"+
				" inner join ERP_SANPHAM sp on dh_sp.SPMA= sp.MA \n"+   
				" left join CHUNGLOAI cl on sp.CHUNGLOAI_FK= cl.PK_SEQ \n"+   
				" left join DONVIDOLUONG dvdl on dh_sp.DVDL_FK= dvdl.PK_SEQ \n"+    
				" left join KENHBANHANG kbh on kbh.PK_SEQ= dh.KBH_FK   \n"+
				" LEFT JOIN QUYCACH QC ON QC.DVDL2_FK = dh_sp.DVDL_FK AND sp.PK_SEQ = QC.SANPHAM_FK \n"+ 
				" LEFT JOIN (	SELECT	XK_SP.SANPHAM_FK,SUM(XK_SP.SOLUONG) SOLUONG, SP.DVDL_FK, XK.DONDATHANG_FK \n"+ 
				" 								FROM	ERP_XUATKHO XK INNER JOIN ERP_XUATKHO_SANPHAM XK_SP ON XK.PK_SEQ = XK_SP.XUATKHO_FK \n"+ 
				"										INNER JOIN ERP_SANPHAM SP ON XK_SP.SANPHAM_FK =  SP.PK_SEQ 	 \n"+
				"								WHERE   1 = 1 AND XK.DONDATHANG_FK IS NOT NULL AND XK.TRANGTHAI IN (1,3,4) and XK.NOIDUNGXUAT = '100008' \n"+
				"								GROUP BY XK_SP.SANPHAM_FK,  SP.DVDL_FK, XK.DONDATHANG_FK ) XKDAGIAO ON dh.PK_SEQ = XKDAGIAO.DONDATHANG_FK and sp.PK_SEQ = XKDAGIAO.SANPHAM_FK \n"+
				
									
				" where dh.TRANGTHAI <>7 and dh.ngaydat >='"+obj.gettungay()+"' and dh.ngaydat <='"+obj.getdenngay()+"' and dh.LOAIDONHANG = 6 \n"+

				" GROUP BY  kbh.PK_SEQ,kbh.DIENGIAI,kh.Ma  ,dh.NGAYDAT , dh.PK_SEQ , sp.MA , sp.TEN ,  cl.PK_SEQ,cl.TEN, dh.LOAIDONHANG,kh.PK_SEQ, \n"+   
				" dvdl.DIENGIAI, dh_sp.SOLUONG, dh_sp.DONGIA, dh_sp.dongiaCK, kh.Ten, \n"+
				" dh.CKTHUONGMAI, dh.VAT, cl.PK_SEQ , dh.TRANGTHAI , sp.dvkd_fk, sp.nhanhang_fk, XKDAGIAO.DVDL_FK,     dh_sp.DVDL_FK \n"+
				
				/*"(select kbh.DIENGIAI as kenh, kh.Ma as khma, kh.Ten as khten,dh.NGAYDAT as ngaychungtu, dh.PK_SEQ as sochungtu, sp.MA as spma, sp.TEN as spten,     \n"+
				"dvdl.DIENGIAI as dvdlten, dh_sp.SOLUONG, dh_sp.DONGIA, dh_sp.dongiaCK as dongiaCK, round((dh_sp.SOLUONG* dh_sp.dongiaCK+0.0001),0) as bvat,    \n"+
				"ISNULL(dh.CKTHUONGMAI,0) as ck, ISNULL(dh.VAT,'0') as sovat, cl.PK_SEQ as chungloai_fk, dh.TRANGTHAI as trangthai, sp.dvkd_fk, sp.nhanhang_fk,    \n"+
				"isnull((select SUM(xksp.SOLUONG)    \n"+
				"from ERP_XUATKHO_SANPHAM xksp    \n"+
				"inner join ERP_XUATKHO xk on xk.PK_SEQ= xksp.XUATKHO_FK    \n"+
				"inner join erp_hoadon hd on hd.PK_SEQ= xk.HOADON_FK    \n"+
				"inner join ERP_HOADON_DDH hd_dh on hd.PK_SEQ= hd_dh.HOADON_FK    \n"+
				"where xk.TRANGTHAI <>2 and hd.loaihoadon=0 and hd_dh.DDH_FK=dh.PK_SEQ and xksp.SANPHAM_FK= dh_sp.SANPHAM_FK ),0) as soluongdaxuat,    \n"+
				"dh_sp.SOLUONG - isnull((select SUM(xksp.SOLUONG)    \n"+
				"from ERP_XUATKHO_SANPHAM xksp    \n"+
				"inner join ERP_XUATKHO xk on xk.PK_SEQ= xksp.XUATKHO_FK    \n"+
				"inner join erp_hoadon hd on hd.PK_SEQ= xk.HOADON_FK    \n"+
				"inner join ERP_HOADON_DDH hd_dh on hd.PK_SEQ= hd_dh.HOADON_FK    \n"+
				"where xk.TRANGTHAI <>2  and hd.loaihoadon=0  and hd_dh.DDH_FK=dh.PK_SEQ and xksp.SANPHAM_FK= dh_sp.SANPHAM_FK ),0) as soluongconlai,    \n"+
				" kbh.pk_seq as kenh_fk, cl.ten as clten,  \n"+
				" case  \n"+
					"when dh.LOAIDONHANG=1 then N'Đơn hàng bán'  \n"+
					"when dh.LOAIDONHANG=6 then N'Đơn hàng KM'  \n"+
					"else ''  \n"+
				"end as loaidonhang, '' as scheme , kh.pk_seq as khId   \n"+
				"from ERP_DONDATHANG dh    \n"+
				"inner join ERP_DONDATHANG_SP dh_sp on dh.PK_SEQ= dh_sp.DONDATHANG_FK    \n"+
				"inner join ERP_KHACHHANG kh on dh.KHACHHANG_FK= kh.PK_SEQ    \n"+
				"inner join ERP_SANPHAM sp on dh_sp.SANPHAM_FK= sp.PK_SEQ    \n"+
				"left join CHUNGLOAI cl on sp.CHUNGLOAI_FK= cl.PK_SEQ    \n"+
				"left join DONVIDOLUONG dvdl on dh_sp.DVDL_FK= dvdl.PK_SEQ    \n"+
				"left join KENHBANHANG kbh on kbh.PK_SEQ= dh.KBH_FK    \n"+
				"where dh.TRANGTHAI <>7 and dh.ngaydat >='"+obj.gettungay()+"' and dh.ngaydat <='"+obj.getdenngay()+"' \n"+
				
				" union all  \n"+
				
				"select kbh.DIENGIAI,  kh.Ma, kh.Ten, dh.NGAYDAT, dh.PK_SEQ, sp.MA, sp.TEN, dvdl.DIENGIAI, dhkm.SOLUONG, 0 as dongia, 0 as dongiadagiam, 0 as bvat,  0 as ck, '0' as sovat, cl.PK_SEQ as chungloai_fk, dh.TRANGTHAI as trangthai,  sp.dvkd_fk, sp.nhanhang_fk,   \n"+
				"isnull((select SUM(xksp.SOLUONG)       \n"+
				"from ERP_XUATKHO_SANPHAM xksp       \n"+
				"inner join ERP_XUATKHO xk on xk.PK_SEQ= xksp.XUATKHO_FK       \n"+
				"inner join erp_hoadon hd on hd.PK_SEQ= xk.HOADON_FK       \n"+
				"inner join ERP_HOADON_DDH hd_dh on hd.PK_SEQ= hd_dh.HOADON_FK       \n"+
				"where xk.TRANGTHAI <>2  and hd.loaihoadon=1  and hd_dh.DDH_FK=dh.PK_SEQ and xksp.SANPHAM_FK=sp.PK_SEQ  ), 0) as soluongdaxuat,    \n"+
				"dhkm.SOLUONG - isnull((select SUM(xksp.SOLUONG)      \n"+
				"from ERP_XUATKHO_SANPHAM xksp       \n"+
				"inner join ERP_XUATKHO xk on xk.PK_SEQ= xksp.XUATKHO_FK       \n"+
				"inner join erp_hoadon hd on hd.PK_SEQ= xk.HOADON_FK       \n"+
				"inner join ERP_HOADON_DDH hd_dh on hd.PK_SEQ= hd_dh.HOADON_FK       \n"+
				"where xk.TRANGTHAI <>2  and hd.loaihoadon=1  and hd_dh.DDH_FK=dh.PK_SEQ and xksp.SANPHAM_FK=sp.PK_SEQ  ), 0) as soluongconlai,    \n"+
				"kbh.PK_SEQ as kenh_fk, cl.TEN as clten, N'Đơn hàng KM' , ctkm.DIENGIAI as scheme,  kh.pk_seq as khId     \n"+
				"from ERP_DONDATHANG dh    \n"+
				"inner join ERP_DONDATHANG_CTKM_TRAKM dhkm on dh.PK_SEQ= dhkm.DONDATHANGID    \n"+
				"inner join CTKHUYENMAI ctkm on dhkm.CTKMID= ctkm.PK_SEQ    \n"+
				"inner join ERP_KHACHHANG kh on dh.KHACHHANG_FK=kh.PK_SEQ    \n"+
				"inner join ERP_SANPHAM sp on dhkm.SPMA= sp.MA    \n"+
				"inner join DONVIDOLUONG dvdl on sp.DVDL_FK=dvdl.PK_SEQ    \n"+
				"left join KENHBANHANG kbh on kbh.PK_SEQ= dh.KBH_FK    \n"+
				"left join CHUNGLOAI cl on sp.CHUNGLOAI_FK= cl.PK_SEQ    \n"+
				"where dh.TRANGTHAI <>7 and dh.ngaydat >='"+obj.gettungay()+"' and dh.ngaydat <='"+obj.getdenngay()+"' \n"+ */
				 
				 ") A where 1=1     ";

		if(obj.getkenhId().length() >0){
			query += " and A.kenh_fk="+obj.getkenhId()+"   ";
		}
		
		if(obj.getchungloaiId().length() >0){
			query += " and A.chungloai_fk="+obj.getchungloaiId()+"   ";
		}
		
		if(obj.getKhachhangIds().length() > 0){
			query += " and A.khId in ("+obj.getKhachhangIds()+")  ";
		}

		if(obj.getnhanhangId().length() >0){
			query += " and A.nhanhang_fk="+obj.getnhanhangId()+"   ";
		}
		
		if(obj.getdvkdId().length() >0){
			query += " and A.dvkd_fk="+obj.getchungloaiId()+"   ";
		}
		
		if(trangthai.length() > 0)
			query += " and A.trangthai = '" + trangthai + "'";
		
		query += " order by A.ngaychungtu, A.sochungtu ";
		
		System.out.println("BC don hang : " + query);
		
		ResultSet rs = db.get(query);
		int index = 12;
		
		double total_BC_bvat=0;
		double total_BC_avat=0;
		double total_BC_vat=0;
		double total_BC_soluong=0;
		double total_BC_soluongdagiao=0;
		double total_BC_soluongconlai=0;
		
		double tienck = 0;
		
		double SUM_TONGCK = 0;
		double tienck_cu = 0;
		
		String temp="";
		
		String ngay_cu="";
		String kenh_cu="";
		String makh_cu="";
		String tenkh_cu="";
		String loaidh_cu ="";
		String sochungtu_cu = "";
		
		int row = 0;
		
		if (rs != null) 
		{
			try 
			{
		
				while (rs.next())
				{			
					
					String kenh_fk= rs.getString("kenh_fk");
					double bvat = rs.getDouble("bvat");
					double avat = 0;
					double vat =0;
					row = rs.getInt("row");
					
					if(!kenh_fk.equals("100007")){
						avat = Math.round(bvat*1.1+0.0001);
						vat = avat - bvat;
					}else{
						avat = bvat;
						vat=0;
					}
					
					
					total_BC_bvat +=bvat;
					total_BC_avat +=avat;
					total_BC_vat +=vat;
					total_BC_soluong += rs.getShort("soluong");
					total_BC_soluongdagiao +=rs.getDouble("soluongdaxuat");
					total_BC_soluongconlai +=rs.getDouble("soluongconlai");
					
					tienck = rs.getDouble("ck");
					
					if(temp.length() <0 ){
						temp = rs.getString("sochungtu");
					}
					
					//************* XỬ LÝ KHI KẾT THÚC 1 HOÁ ĐƠN, TÍNH CK CUỐI DÒNG*****************//
					
					if(!rs.getString("sochungtu").equals(temp)){
						if(tienck_cu>0){
							Font font = new Font();
							font.setName("Times New Roman");
							font.setColor(Color.RED);
							font.setSize(11);
							Style style = null;
							
							cell = cells.getCell("A" + String.valueOf(index)); cell.setValue(kenh_cu);	
							style = cell.getStyle();
							style.setFont(font);
							cell.setStyle(style);
							
							cell = cells.getCell("B" + String.valueOf(index)); cell.setValue(loaidh_cu);
							style = cell.getStyle();
							style.setFont(font);
							cell.setStyle(style);
							
							cell = cells.getCell("C" + String.valueOf(index)); cell.setValue(makh_cu);
							style = cell.getStyle();
							style.setFont(font);
							cell.setStyle(style);
							
							cell = cells.getCell("D" + String.valueOf(index)); cell.setValue(tenkh_cu);	
							style = cell.getStyle();
							style.setFont(font);
							cell.setStyle(style);
							
							cell = cells.getCell("E" + String.valueOf(index)); cell.setValue(ngay_cu);
							style = cell.getStyle();
							style.setFont(font);
							cell.setStyle(style);
							
							cell = cells.getCell("F" + String.valueOf(index)); cell.setValue(sochungtu_cu);
							style = cell.getStyle();
							style.setFont(font);
							cell.setStyle(style);
							
							cell = cells.getCell("G" + String.valueOf(index)); cell.setValue("");
							
							cell = cells.getCell("H" + String.valueOf(index)); cell.setValue("");
							
							cell = cells.getCell("I" + String.valueOf(index)); cell.setValue("Tiền chiết khấu thương mại");
							style = cell.getStyle();
							style.setFont(font);
							cell.setStyle(style);
							
							cell = cells.getCell("J" + String.valueOf(index)); cell.setValue("");
							cell = cells.getCell("K" + String.valueOf(index)); cell.setValue("");
							cell = cells.getCell("L" + String.valueOf(index)); cell.setValue("");
							cell = cells.getCell("M" + String.valueOf(index)); cell.setValue("");
							cell = cells.getCell("N" + String.valueOf(index)); cell.setValue("");
							cell = cells.getCell("O" + String.valueOf(index)); cell.setValue("");
							cell = cells.getCell("P" + String.valueOf(index)); cell.setValue("");
							cell = cells.getCell("Q" + String.valueOf(index)); cell.setValue(tienck_cu);
							style = cell.getStyle();
							style.setFont(font);
							cell.setStyle(style);
							
							cell = cells.getCell("R" + String.valueOf(index)); cell.setValue("");
							cell = cells.getCell("S" + String.valueOf(index)); cell.setValue("");
							cell = cells.getCell("T" + String.valueOf(index)); cell.setValue("");
							cell = cells.getCell("U" + String.valueOf(index)); cell.setValue("");					
							
							index++;
						}
					}
					
					//************* XỬ LÝ KHI KẾT THÚC 1 HOÁ ĐƠN, TÍNH CK CUỐI DÒNG*****************//
					
					
					if(row==1&&tienck>0)
						SUM_TONGCK += tienck;
					
					cell = cells.getCell("A" + String.valueOf(index)); cell.setValue(rs.getString("kenh"));	
					cell = cells.getCell("B" + String.valueOf(index)); cell.setValue(rs.getString("loaidonhang"));
					cell = cells.getCell("C" + String.valueOf(index)); cell.setValue(rs.getString("khma"));
					cell = cells.getCell("D" + String.valueOf(index)); cell.setValue(rs.getString("khten"));	
					cell = cells.getCell("E" + String.valueOf(index)); cell.setValue(rs.getString("ngaychungtu"));	
					cell = cells.getCell("F" + String.valueOf(index)); cell.setValue(rs.getString("sochungtu"));	
					cell = cells.getCell("G" + String.valueOf(index)); cell.setValue(rs.getString("clten"));	
					cell = cells.getCell("H" + String.valueOf(index)); cell.setValue(rs.getString("spma"));
					cell = cells.getCell("I" + String.valueOf(index)); cell.setValue(rs.getString("spten"));
					cell = cells.getCell("J" + String.valueOf(index)); cell.setValue(rs.getString("dvdlten"));
					cell = cells.getCell("K" + String.valueOf(index)); cell.setValue(rs.getDouble("soluong"));
					cell = cells.getCell("L" + String.valueOf(index)); cell.setValue(rs.getDouble("dongia"));
					cell = cells.getCell("M" + String.valueOf(index)); cell.setValue(rs.getDouble("dongiaCK"));
					cell = cells.getCell("N" + String.valueOf(index)); cell.setValue(rs.getDouble("soluongdaxuat"));
					cell = cells.getCell("O" + String.valueOf(index)); cell.setValue(rs.getDouble("soluongconlai"));
					cell = cells.getCell("P" + String.valueOf(index)); cell.setValue(bvat);
					cell = cells.getCell("Q" + String.valueOf(index)); cell.setValue(0);
					cell = cells.getCell("R" + String.valueOf(index)); cell.setValue(rs.getDouble("sovat"));
					cell = cells.getCell("S" + String.valueOf(index)); cell.setValue(vat);
					cell = cells.getCell("T" + String.valueOf(index)); cell.setValue(avat);
					cell = cells.getCell("U" + String.valueOf(index)); cell.setValue(rs.getString("scheme"));					
					
				
					
					//************ GÁN LẠI GIÁ TRỊ DÒNG TRƯỚC ĐỂ DÙNG CHO  TÍNH CK *******//
					
					temp= rs.getString("sochungtu");
					ngay_cu =rs.getString("ngaychungtu");
					kenh_cu =rs.getString("kenh");
					makh_cu =rs.getString("khma");
					tenkh_cu=  rs.getString("khten");									
					tienck_cu= rs.getDouble("ck");
					loaidh_cu = rs.getString("loaidonhang");
					sochungtu_cu = rs.getString("sochungtu");
					
					index++;
					
				}
				
				//*********** IN DÒNG TOTAL ********************//
				
				cells.merge(index -1, 0, index -1, 9);
				cell = cells.getCell("A" + String.valueOf(index)); 
				cell.setValue("TỔNG CỘNG");
			
				cell = cells.getCell("K" + String.valueOf(index)); cell.setValue(total_BC_soluong);
				cell = cells.getCell("N" + String.valueOf(index)); cell.setValue(total_BC_soluongdagiao);
				cell = cells.getCell("O" + String.valueOf(index)); cell.setValue(total_BC_soluongconlai);
				cell = cells.getCell("P" + String.valueOf(index)); cell.setValue(total_BC_bvat);
				cell = cells.getCell("Q" + String.valueOf(index)); cell.setValue(SUM_TONGCK);
				cell = cells.getCell("S" + String.valueOf(index)); cell.setValue(total_BC_vat);			
				cell = cells.getCell("T" + String.valueOf(index)); cell.setValue(total_BC_avat);

				if (rs != null)
					rs.close();
				
				if(db != null)
					db.shutDown();
			}
			catch(Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		else
		{
			return false;
		}		
		return true;
	}	
	

}
