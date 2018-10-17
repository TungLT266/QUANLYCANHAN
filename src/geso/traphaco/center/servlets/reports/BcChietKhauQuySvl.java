package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

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

@WebServlet("/BcChietKhauQuySvl")
public class BcChietKhauQuySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public BcChietKhauQuySvl()
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
	    obj.init();
	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/BcChietKhauQuy.jsp";
		response.sendRedirect(nextJSP);	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit(); 
	    String userId = (String) session.getAttribute("userId");  
	    /*String userTen = (String) session.getAttribute("userTen");*/
	    
	    Utility util = new Utility();

	  	obj.setuserId((String) session.getAttribute("userId") == null ? "" : (String) session.getAttribute("userId"));
			
			obj.setuserTen((String) session.getAttribute("userTen") == null ? "" : (String) session.getAttribute("userTen"));
			
			obj.setnppId(util.antiSQLInspection(request.getParameter("nppId")) == null ? "" : util.antiSQLInspection(request.getParameter("nppId")));
			
			obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId")) == null ? "" : util.antiSQLInspection(request.getParameter("kenhId")));
			
			obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId")) == null ? "" : util.antiSQLInspection(request.getParameter("dvkdId")));
			
			obj.setMonth(util.antiSQLInspection(request.getParameter("month")) == null ? "" : util.antiSQLInspection(request.getParameter("month")));
			
			obj.setYear(util.antiSQLInspection(request.getParameter("year")) == null ? "" : util.antiSQLInspection(request.getParameter("year")));
			
			obj.setvungId(util.antiSQLInspection(request.getParameter("vungId")) == null ? "" : util.antiSQLInspection(request.getParameter("vungId")));
			
			obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId")) == null ? "" : util.antiSQLInspection(request.getParameter("khuvucId")));
			
			obj.setdvdlId(util.antiSQLInspection(request.getParameter("dvdlId")) == null ? "" : util.antiSQLInspection(request.getParameter("dvdlId")));
			
			obj.setDdkd(util.antiSQLInspection(request.getParameter("ddkdId")) == null ? "" : util.antiSQLInspection(request.getParameter("ddkdId")));
			
			obj.setgsbhId(util.antiSQLInspection(request.getParameter("gsbhId")) == null ? "" : util.antiSQLInspection(request.getParameter("gsbhId")));
			
			obj.setBMId(util.antiSQLInspection(request.getParameter("bmId")) == null ? "" : util.antiSQLInspection(request.getParameter("bmId")));
			
			obj.setASMId(util.antiSQLInspection(request.getParameter("asmId")) == null ? "" : util.antiSQLInspection(request.getParameter("asmId")));
			
			obj.setFromMonth(util.antiSQLInspection(request.getParameter("tuthang")) == null ? "" : util.antiSQLInspection(request.getParameter("tuthang")));
			obj.setFromYear(util.antiSQLInspection(request.getParameter("tunam")) == null ? "" : util.antiSQLInspection(request.getParameter("tunam")));
			
			obj.setToMonth(util.antiSQLInspection(request.getParameter("denthang")) == null ? "" : util.antiSQLInspection(request.getParameter("denthang")));
			obj.setToYear(util.antiSQLInspection(request.getParameter("dennam")) == null ? "" : util.antiSQLInspection(request.getParameter("dennam")));
			
			obj.settype(util.antiSQLInspection(request.getParameter("type")) == null ? "" : util.antiSQLInspection(request.getParameter("type")));
			
		
		String sql ="";
		
		String action = util.antiSQLInspection(request.getParameter("action"));
		 
		if(action.equals("Taomoi"))
		{
			try{						
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", "attachment; filename=ChietKhauQuy_"+util.setTieuDe(obj)+".xlsm");
		        OutputStream out = response.getOutputStream();
		        String query =  setQuery(sql,obj, userId);
		        CreatePivotTable(out,obj,query);	
		        
			}catch(Exception ex){
				obj.setMsg(ex.getMessage());
				obj.init();	    
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Center/BcChietKhauQuy.jsp";
				response.sendRedirect(nextJSP);
			}
		} else{			 
			 obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/BcChietKhauQuy.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	private String setQuery(String sql, IStockintransit obj, String userId) 
	{
		
		obj.setToYear(obj.getFromYear());
		
		if(obj.gettype().equals("1"))
		{
			obj.setFromMonth("01");
			obj.setToMonth("03");
		}else if(obj.gettype().equals("2"))
		{
			obj.setFromMonth("04");
			obj.setToMonth("06");
		}
		else if(obj.gettype().equals("3"))
		{
			obj.setFromMonth("07");
			obj.setToMonth("09");
		}
		else if(obj.gettype().equals("4"))
		{
			obj.setFromMonth("10");
			obj.setToMonth("12");
		}
	
		String qr =
		"\n			select    "+
		"\n			dvkd.DONVIKINHDOANH,	kbh.ten as kbhten,kv.ten as kvten,v.ten as vten,npp.ma as nppma,npp.ten as nppten,   "+
		"\n			gs.ten as gsten,dd.ten as ddkdten,kh.ten  as khten,kh.mafast as khma,kh.diachi as khdiachi,   "+
		"\n			kh.xuatkhau,kh.khongkyhopdong,kh.thanhtoan, ckTL.cktichluy, DoanhSoSauCK,  ckDonHang,ds.DsNhomxanh,ds.Dsnhomhh,ds.dsnhomkhac,ckbs.ckBoSung,ckkm.ckkm,ckq.ckbg,ckq.ckx "+
		"\n				from   "+ 
		"\n				(    "+
		
		"\n										select dvkd_fk,khachhang_fk,npp_fk,kbh_fk,ddkd_fk,gsbh_fk,   "+
		"\n										sum(ckDonHang) as ckDonHang,sum(DoanhSoSauCK) as DoanhSoSauCK ,    "+
		"\n										sum([100002]) as DsNhomxanh,sum([100003]) as Dsnhomhh,sum([100004]) as dsnhomkhac   "+   
		"\n										from      "+
		"\n										(        "+
		"\n											select dh.khachhang_fk,dh.npp_fk,nsp.nsp_fk,dh.kbh_fk,dh.ddkd_fk,dh.gsbh_fk,sp.dvkd_fk,    "+
		"\n											sum(dhsp.soluong*dhsp.dongiaGOC*(1+dhsp.thuevat/100)) as doanhso,    "+
		"\n												SUM(dhsp.chietkhau*(1+dhsp.THUEVAT/100)) as ckDonHang,       "+
		"\n												cast( sum( (dhsp.soluong * dhsp.dongiaGOC - dhsp.chietkhau ) * ( 1 + dhsp.thueVAT / 100 ) ) as numeric(18, 0) )  as DoanhSoSauCK   "+   
		"\n											from donhang dh inner join donhang_sanpham dhsp on dhsp.donhang_fk=dh.pk_seq       "+
		"\n															inner join sanpham sp on sp.pk_Seq=dhsp.sanpham_fk    "+
		"\n													inner join nhomsanpham_sanpham nsp on nsp.sp_fk=dhsp.sanpham_fk   "+    
		"\n			where dh.trangthai!=2 and  SUBSTRING( dh.NgayNhap,1,7)   >= '"+obj.getFromYear()+"-"+obj.getFromMonth()+"'       "+
		"\n										and   SUBSTRING( dh.NgayNhap,1,7)  <= '"+obj.getFromYear()+"-"+obj.getToMonth()+"'    "+
		"\n											group by dh.khachhang_fk,dh.npp_fk,nsp.nsp_fk,dh.kbh_fk,dh.ddkd_fk,dh.gsbh_fk,sp.dvkd_fk   "+   
		"\n						)as p pivot ( sum(doanhso)         "+
		"\n						for nsp_fk  in  ([100002],[100003],[100004]) )as t   "+   
		"\n						group by dvkd_fk,khachhang_fk,npp_fk,kbh_fk,ddkd_fk,gsbh_fk  "+
		
		
		
		
		
		"\n				)as ds   "+
		"\n				left join    "+
		"\n				(   "+
		"\n					select kbh_fk,npp_fk,khachhang_fk,gsbh_fk,ddkd_fk,[cqb] as ckbg ,[cqx] as ckx   "+
		"\n						from    "+
		"\n					(	select b.kbh_fk,b.npp_fk,a.khachhang_fk,b.ddkd_fk,b.gsbh_fk,sum(thanhtoan) as ckquy,substring(diengiai,0,4) as loaick   "+
		"\n						from duyettrakhuyenmai_donhang a inner join donhang b on b.pk_seq=a.donhang_fk   "+
		"\n						where a.tichluyquy=1 and b.trangthai!=2  and  SUBSTRING( b.NgayNhap,1,7)   >= '"+obj.getFromYear()+"-"+obj.getFromMonth()+"'   "+
		"\n   	and   SUBSTRING( b.NgayNhap,1,7)  <= '"+obj.getFromYear()+"-"+obj.getToMonth()+"' "+ 
		"\n						group by a.khachhang_fk,b.npp_fk,substring(diengiai,0,4),b.gsbh_fk,b.ddkd_fk,b.kbh_fk   "+
		"\n					) as p pivot    "+
		"\n					(    "+
		"\n						sum(ckquy) for loaick in ([cqb],[cqx])   "+
		"\n					)as t   "+
		"\n				)as ckq on ckq.npp_fk=ds.npp_fk and ckq.khachhang_fk=ds.khachhang_fk   "+
		"\n				and ckq.gsbh_fk=ds.gsbh_fk and ckq.ddkd_fk=ds.ddkd_fk and ckq.kbh_fk=ds.kbh_fk   "+
		
		"\n				left join    "+
		"\n				(   "+
		"\n							select b.kbh_fk,b.npp_fk,a.khachhang_fk,b.ddkd_fk,b.gsbh_fk,sum(thanhtoan) as cktichluy   "+
		"\n						from duyettrakhuyenmai_donhang a inner join donhang b on b.pk_seq=a.donhang_fk   "+
		"\n						where  b.trangthai!=2   "+
		"\n    and  SUBSTRING( b.NgayNhap,1,7)   >= '"+obj.getFromYear()+"-"+obj.getFromMonth()+"'   "+
		"\n   	and   SUBSTRING( b.NgayNhap,1,7)  <= '"+obj.getFromYear()+"-"+obj.getToMonth()+"' "+ 
		"\n						group by a.khachhang_fk,b.npp_fk,b.gsbh_fk,b.ddkd_fk,b.kbh_fk   "+
		"\n				)as ckTL on ckTL.npp_fk=ds.npp_fk and ckTL.khachhang_fk=ds.khachhang_fk   "+
		"\n				and ckTL.gsbh_fk=ds.gsbh_fk and ckTL.ddkd_fk=ds.ddkd_fk and ckTL.kbh_fk=ds.kbh_fk   "+
		
		
		
		"\n				left join     "+
		"\n				(    "+
		"\n					select a.kbh_fk,a.gsbh_fk,a.ddkd_fk,a.npp_fk,a.khachhang_fk,sum(b.tonggiatri) as ckkm   "+ 
		"\n					from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq   "+
		"\n					where a.trangthai!=2 and b.spma is null   "+
		"\n    and  SUBSTRING( a.NgayNhap,1,7)   >= '"+obj.getFromYear()+"-"+obj.getFromMonth()+"'   "+
		"\n   	and   SUBSTRING( a.NgayNhap,1,7)  <= '"+obj.getFromYear()+"-"+obj.getToMonth()+"' "+ 
		
		"\n					group by a.npp_fk,a.khachhang_fk,a.kbh_fk,a.gsbh_fk,a.ddkd_fk   "+
		"\n				)as ckkm on ckkm.npp_fk=ds.npp_fk and ckkm.khachhang_fk=ds.khachhang_fk   "+
		"\n				and ckkm.gsbh_fk=ds.gsbh_fk and ckkm.ddkd_fk=ds.ddkd_fk and ckkm.kbh_fk=ds.kbh_fk  "+
		"\n				left join    "+
		"\n				(   "+
		"\n					select a.npp_fk,a.khachhang_fk,a.kbh_fk,a.gsbh_fk,a.ddkd_fk,sum(b.thanhtien) as ckBoSung   "+ 
		"\n					from donhang a inner join donhang_chietkhaubosung b on b.donhang_fk=a.pk_seq   "+
		"\n					where a.trangthai!=2    "+		
		"\n    and  SUBSTRING( a.NgayNhap,1,7)   >= '"+obj.getFromYear()+"-"+obj.getFromMonth()+"'   "+
		"\n   	and   SUBSTRING( a.NgayNhap,1,7)  <= '"+obj.getFromYear()+"-"+obj.getToMonth()+"' "+ 
		"\n					group by a.npp_fk,a.khachhang_fk,a.kbh_fk,a.gsbh_fk,a.ddkd_fk   "+
		"\n				)as ckbs on ckbs.npp_fk=ds.npp_fk and ckbs.khachhang_fk=ds.khachhang_fk  "+
		"\n				and ckbs.gsbh_fk=ds.gsbh_fk and ckbs.ddkd_fk=ds.ddkd_fk and ckbs.kbh_fk=ds.kbh_fk  "+
		"\n				left join khachhang kh on kh.pk_seq=ds.khachhang_fk   "+
		"\n				left join nhaphanphoi npp on npp.pk_seq=ds.npp_fk    "+ 
		"\n				left join giamsatbanhang gs on gs.pk_seq=ds.gsbh_fk    "+
		"\n				left join daidienkinhdoanh dd on dd.pk_seq=ds.ddkd_fk   "+
		"\n				left join khuvuc kv on kv.pk_seq=npp.khuvuc_fk    "+
		"\n				left join vung v on v.pk_seq=kv.vung_fk     "+
		"\n				left join kenhbanhang kbh on kbh.pk_seq=ds.kbh_fk   " +
		"\n				left join  donvikinhdoanh dvkd on dvkd.pk_Seq=ds.dvkd_Fk "+ 
		"         where 1=1  ";				
				
				
			if(obj.getnppId().length() > 0)
				qr += " and npp.pk_seq = '"+obj.getnppId()+"' ";
			
			if(obj.getDdkd().length() > 0)
				qr += " and dd.pk_seq = '"+obj.getDdkd()+"' ";
			
			if(obj.getgsbhId().length() > 0)
				qr += " and gs.pk_seq = '"+obj.getgsbhId()+"' ";
			
			if(obj.getkenhId().length() > 0)
				qr += " and kbh.pk_seq = '"+obj.getkenhId()+"' ";
			
		   if(obj.getvungId().length()>0)
			   qr += " and v.pk_seq ='"+obj.getvungId()+"'";
		   if(obj.getdvkdId().length()>0)
			   qr += " and dvkd.pk_seq='"+obj.getdvkdId() + "'";
		   if(obj.getkhuvucId().length()>0)
			   qr +=" and kv.pk_seq = '"+obj.getkhuvucId()+"'";
		   Utility  util = new Utility();
		  
		   qr += " and npp.PK_sEQ in " + util .quyen_npp(obj.getuserId()) + " and kbh.PK_sEQ in  " + util.quyen_kenh(obj.getuserId());
				
		   
		   
		   	System.out.println("lenh sql: "+qr);
		return qr;
	}
	private void CreatePivotTable(OutputStream out, IStockintransit obj, String query) throws Exception
    {   
	     try{
	    		String chuoi=getServletContext().getInitParameter("path") + "\\ChietKhauQuy.xlsm";
	 		FileInputStream fstream = new FileInputStream(chuoi);	
	 		Workbook workbook = new Workbook();
	 		
	 		workbook.open(fstream);
	 		
	 		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

	 		CreateStaticHeader(workbook,obj); 
			
	 		FillData(workbook,obj.getFieldShow(),query); 
			
	 		workbook.save(out);
	 		
	 		fstream.close();
	     }catch(Exception ex){
	    	 ex.printStackTrace();
	    	 throw new Exception(ex.getMessage());
	     }
   }
	
	private void CreateStaticHeader(Workbook workbook,IStockintransit obj) throws Exception 
	{
		try{
	 		Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			Cells cells = worksheet.getCells();
			
			String fromDate=obj.getFromYear()+'-'+obj.getFromMonth();
			String toDate=obj.getToYear()+'-'+obj.getToMonth();
			
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
		    
		    ReportAPI.getCellStyle(cell,Color.RED, true, 14, "BÁO CÁO CHIẾT KHẤU QUÝ");
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A3");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ tháng : " + fromDate + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("C3"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến tháng : " + toDate + "" );
			
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + obj.getDateTime());
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
					    
		    cell = cells.getCell("FA1"); cell.setValue("KenhBanHang");
		    cell = cells.getCell("FB1"); cell.setValue("DonViKinhDoanh");
		    cell = cells.getCell("FC1"); cell.setValue("Mien");
		    cell = cells.getCell("FD1"); cell.setValue("Vung");
		    cell = cells.getCell("FE1"); cell.setValue("MaCN/DT");
		    cell = cells.getCell("FF1"); cell.setValue("ChiNhanh/DoiTac");
		    cell = cells.getCell("FG1"); cell.setValue("TrinhDuocVien");
		    cell = cells.getCell("FH1"); cell.setValue("MaKhachHang");
		    cell = cells.getCell("FI1"); cell.setValue("TenKhachHang");
		    cell = cells.getCell("FJ1"); cell.setValue("DiaChi");
		    cell = cells.getCell("FK1"); cell.setValue("LoaiKhacHang");
		    cell = cells.getCell("FL1"); cell.setValue("KyHopDong");
		    cell = cells.getCell("FM1"); cell.setValue("LoaiThanhToan");
		    
		    cell = cells.getCell("FN1"); cell.setValue("DoanhSoQuy");
		    cell = cells.getCell("FO1"); cell.setValue("DoanhNhomBGHH");
		    cell = cells.getCell("FP1"); cell.setValue("DoanhNhomSK_Xanh");
		    cell = cells.getCell("FQ1"); cell.setValue("TyLe_DS_BGHH/DoanhSoQuy");
		    cell = cells.getCell("FR1"); cell.setValue("TyLe_DS_SK_Xanh/DoanhSoQuy");
		    cell = cells.getCell("FS1"); cell.setValue("DoanhSoThuTien(DaTruCK)");
		    cell = cells.getCell("FT1"); cell.setValue("TienCK_Nhom_BGHH");
		    cell = cells.getCell("FU1"); cell.setValue("TienCK_Nhom_SK_Xanh");
		  
		 
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Khong the tao duoc Header cho bao cao...");
		}
	}
	private void FillData(Workbook workbook,String[] fieldShow, String query)throws Exception 
	{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    Cells cells = worksheet.getCells();
		    dbutils db = new dbutils();
		    ResultSet rs = db.get(query);
		    int index = 2;
		    Cell cell = null;	    
		    while (rs.next()) 
		    {		    	
		    	cell = cells.getCell("FA" + Integer.toString(index));cell.setValue(rs.getString("kbhTen"));
		    	cell = cells.getCell("FB" + Integer.toString(index));cell.setValue(rs.getString("donvikinhdoanh"));
		    	cell = cells.getCell("FC" + Integer.toString(index));cell.setValue(rs.getString("vten"));
		    	cell = cells.getCell("FD" + Integer.toString(index));cell.setValue(rs.getString("kvTen"));
		    	cell = cells.getCell("FE" + Integer.toString(index));cell.setValue(rs.getString("nppMa"));
		    	cell = cells.getCell("FF" + Integer.toString(index));cell.setValue(rs.getString("nppTen"));
		    	cell = cells.getCell("FG" + Integer.toString(index));cell.setValue(rs.getString("ddkdTen"));
		    	
		    	cell = cells.getCell("FH" + Integer.toString(index));cell.setValue(rs.getString("khMa"));
		    	cell = cells.getCell("FI" + Integer.toString(index));cell.setValue(rs.getString("khTen"));
		    	cell = cells.getCell("FJ" + Integer.toString(index));cell.setValue(rs.getString("khDiaChi"));
		    	
		   
		    	String XuatKhau =rs.getString("XuatKhau")==null?" ":rs.getString("XuatKhau");
					if(XuatKhau.equals("0"))
						XuatKhau = "Bán lẻ";
					else  if(XuatKhau.equals("1"))
							XuatKhau = "Bán buôn";
					else  if(XuatKhau.equals("2"))
						XuatKhau = "Bán buôn và bán lẻ";
					else  if(XuatKhau.equals("3"))
						XuatKhau = "Bán lẻ và bán buôn";
					
					String KhongKyHopDong = rs.getString("KhongKyHopDong")==null?" ":rs.getString("KhongKyHopDong");
					if(KhongKyHopDong.equals("0"))
						KhongKyHopDong = "Ký hợp đồng";
					else  if(KhongKyHopDong.equals("1"))
						KhongKyHopDong = "Không Ký hợp đồng";
					
					cell = cells.getCell("FK" + String.valueOf(index));		cell.setValue(XuatKhau);
					cell = cells.getCell("FL" + String.valueOf(index));		cell.setValue(KhongKyHopDong);
					
		    	
					double DoanhSo= rs.getDouble("DsNhomxanh") +  rs.getDouble("Dsnhomhh") + rs.getDouble("dsnhomkhac") ;
					double DoanhNhomBGHH = rs.getDouble("Dsnhomhh");
					double DoanhNhomSK_Xanh = rs.getDouble("DsNhomxanh");
					
					double ckKm =rs.getDouble("ckKm");
					/*double ckDonHang = rs.getDouble("ckDonHang");*/
					double ckTichLuy =rs.getDouble("ckTichLuy");
					double ckBoSung = rs.getDouble("ckBoSung");
					
					double  ckbg= rs.getDouble("ckbg");
					double  ckx= rs.getDouble("ckx");
					
					double ThucThu = rs.getDouble("DoanhSoSauCK")-ckBoSung-ckKm;
					
					String ThanhToan = rs.getString("ThanhToan")==null?"0":rs.getString("ThanhToan");
					if(ThanhToan.equals("0"))
						ThanhToan = "Tiền mặt";
					else  if(ThanhToan.equals("1"))
					{
						ThanhToan = "Hóa đơn";
						ThucThu -=ckTichLuy;
					}
				
				  cell = cells.getCell("FN" + String.valueOf(index));		cell.setValue(DoanhSo );
					cell = cells.getCell("FO" + String.valueOf(index));		cell.setValue(DoanhNhomBGHH );
					cell = cells.getCell("FP" + String.valueOf(index));		cell.setValue(DoanhNhomSK_Xanh );
					cell = cells.getCell("FQ" + String.valueOf(index)); 	cell.setValue(DoanhNhomBGHH/DoanhSo);
					cell = cells.getCell("FR" + String.valueOf(index)); 	cell.setValue(DoanhNhomSK_Xanh/DoanhSo);
					cell = cells.getCell("FS" + String.valueOf(index));		cell.setValue(ThucThu);
					cell = cells.getCell("FT" + String.valueOf(index)); 	cell.setValue(ckbg);
					cell = cells.getCell("FU" + String.valueOf(index)); 	cell.setValue(ckx);
		    	
				index++;
			
			}
		    if(rs!=null)
		    {
		    	rs.close();
		    }
		    
		    if(db != null) db.shutDown();
		    
		    if(index==2)
		    {
		    	throw new Exception("Xin loi. Khong co bao cao voi dieu kien da chon...!!!");
		    }
		    ReportAPI.setHidden(workbook, 50);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}	
	}	
}
