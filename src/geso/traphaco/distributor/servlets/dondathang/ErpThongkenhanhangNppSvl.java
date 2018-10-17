package geso.traphaco.distributor.servlets.dondathang;

import geso.traphaco.distributor.beans.xuatkho.IErpGuiSMSTDVList;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpGuiSMSTDVList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpThongkenhanhangNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	public ErpThongkenhanhangNppSvl() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpGuiSMSTDVList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	    
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		
	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new ErpGuiSMSTDVList();
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    obj.setUserId(userId);
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
	    obj.initTHONGKENHANHANG("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThongKeNhanHang.jsp";
		response.sendRedirect(nextJSP);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}	    
		
		IErpGuiSMSTDVList obj = new ErpGuiSMSTDVList();
		
		Utility util = new Utility();
		
		HttpSession session = request.getSession();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
			
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		obj.setUserId(userId);
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
		
		if(action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			String search = getSearchQuery(request, obj);
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			obj.setUserId(userId);
			obj.init(search);
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThongKeNhanHang.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String msg = "";
			if( action.equals("changeTRANGTHAI") )
			{
	    		String trangthaiCAPNHAT = request.getParameter("trangthaiCAPNHAT");
		    	if( trangthaiCAPNHAT.trim().length() > 0 )
		    	{
		    		trangthaiCAPNHAT = trangthaiCAPNHAT.substring(0, trangthaiCAPNHAT.length() - 1);		
		    		msg = ThayDoiTrangThai(trangthaiCAPNHAT, userId);
		    	}
			}
			else if( action.equals("changeSMSTRANGTHAI") )
			{
	    		String trangthaiCAPNHAT = request.getParameter("trangthaiSMSCAPNHAT");
		    	if( trangthaiCAPNHAT.trim().length() > 0 )
		    	{
		    		trangthaiCAPNHAT = trangthaiCAPNHAT.substring(0, trangthaiCAPNHAT.length() - 1);		
		    		msg = ThayDoiTrangThaiSMS(trangthaiCAPNHAT, userId);
		    	}
			}
			else if( action.equals("taobaocao") )
			{
				OutputStream out = response.getOutputStream();
				
				try
			    {
			    	/*response.setContentType("application/vnd.ms-excel");
			        response.setHeader("Content-Disposition", "attachment; filename=BCGuiHangTinh.xls");*/
			        
			        response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BCGuiHangTinh.xlsm");
			        
			        Workbook workbook = new Workbook();
			    	
					FileInputStream fstream = null;
					String chuoi = getServletContext().getInitParameter("path") + "\\BCGuiHangTinh.xlsm";
					
					fstream = new FileInputStream(chuoi);		
					workbook.open(fstream);
					workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			        
			 
				    CreateStaticHeader(workbook, obj);
				    
				    String query = getSearchQuery_BaoCao(request, obj);
				    CreateStaticData(workbook, query);
				
				    //Saving the Excel file
				    workbook.save(out);
				    fstream.close();
			    }
			    catch (Exception ex){ ex.printStackTrace(); }
				
			}
			
			String search = getSearchQuery(request, obj);
			obj.setUserId(userId);
			obj.initTHONGKENHANHANG(search);
			obj.setMsg(msg);
			
			session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThongKeNhanHang.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}
	
	private String ThayDoiTrangThai(String trangthaiCAPNHAT, String userId) 
	{
		String[] ttARR = trangthaiCAPNHAT.split(",");
		String strTRANGTHAI = "";
		for( int i = 0; i < ttARR.length; i++ )
		{
			String[] ttARR2 = ttARR[i].split("-");
			strTRANGTHAI += "select " + ttARR2[0] + " as dhId, " + ttARR2[1] + " as trangthai ";
			strTRANGTHAI += " union ALL ";
		}
		
		if( strTRANGTHAI.trim().length() > 0 )
		{
			strTRANGTHAI = strTRANGTHAI.substring(0, strTRANGTHAI.length() - 10);
			
			dbutils db = new dbutils();
			String query = " Update a set a.trangthaiFAX = b.trangthai, ngayNVBH_XACNHAN = getdate() , nguoisua = '" + userId + "', ngaysua = '" + getDateTime() + "', " +
						   "	a.ngaynhanFAX = case b.trangthai when 0 then NULL else getdate() end	" +
						   " from ERP_GUISMSTDV a inner join ( " + strTRANGTHAI + " ) b on a.pk_seq = b.dhId ";
			
			System.out.println(":::: CAP NHAT TRANG THAI: " + query );
			if( !db.update(query) )
			{
				db.shutDown();
				return "Lỗi khi cập nhật trạng thái: " + query;
			}
			db.shutDown();
		}
		
		return "";
	
	}
	

	private String ThayDoiTrangThaiSMS(String trangthaiCAPNHAT, String userId) 
	{
		String[] ttARR = trangthaiCAPNHAT.split(",");
		String strTRANGTHAI = "";
		for( int i = 0; i < ttARR.length; i++ )
		{
			String[] ttARR2 = ttARR[i].split("-");
			strTRANGTHAI += "select " + ttARR2[0] + " as dhId, " + ttARR2[1] + " as trangthai ";
			strTRANGTHAI += " union ALL ";
		}
		
		if( strTRANGTHAI.trim().length() > 0 )
		{
			strTRANGTHAI = strTRANGTHAI.substring(0, strTRANGTHAI.length() - 10);
			
			dbutils db = new dbutils();
			String query = " Update a set a.trangthaiSMS = b.trangthai, ngayNVBH_XACNHAN = getdate(), nguoisua = '" + userId + "', ngaysua = '" + getDateTime() + "', " +
						   "	a.ngaynhanSMS = case b.trangthai when 0 then NULL else getdate() end	" +
						   " from ERP_GUISMSTDV a inner join ( " + strTRANGTHAI + " ) b on a.pk_seq = b.dhId ";
			
			System.out.println(":::: CAP NHAT TRANG THAI SMS: " + query );
			if( !db.update(query) )
			{
				db.shutDown();
				return "Lỗi khi cập nhật trạng thái: " + query;
			}
			db.shutDown();
		}
		
		return "";
	
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpGuiSMSTDVList obj)
	{
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		String nvbanhang=request.getParameter("nvbanhang");
		if(nvbanhang==null)
			nvbanhang="";
		obj.setNvbanhang(nvbanhang);
		
		String nguoigiao=request.getParameter("nguoigiao");
		if(nguoigiao==null)
			nguoigiao="";
		obj.setNguoigiao(nguoigiao);
		
		String nguoitao=request.getParameter("nguoitao");
		if(nguoitao==null)
			nguoitao="";
		obj.setNguoitao(nguoitao);
		
		// thêm các trường lọc
		// mã số SMS là mã chứng từ hả???
		String masoSMS = request.getParameter("masoSMS");
		if(masoSMS == null){
			masoSMS = "";
		}
		obj.setMasoSMS(masoSMS);
		
		// Tên khu vực- tỉnh thành
		String khuvuc = request.getParameter("khuvuc");
		if( khuvuc == null){
			khuvuc = "";
		}
		obj.setKhuvuc(khuvuc);
		// Mã chứng từ
		String machungtu = request.getParameter("machungtu");
		if( machungtu == null){
			machungtu = "";
		}
		obj.setMachungtu(machungtu);
		// Mã KH
		String khachhang = request.getParameter("khachhang");
		if( khachhang == null){
			khachhang = "";
		}
		obj.setKhachhang(khachhang);
		// Ngày giao
		String ngaygiao = request.getParameter("ngaygiao");
		if( ngaygiao == null){
			ngaygiao = "";
		}
		obj.setNgaygiao(ngaygiao);
		// Ngày giao dự kién
		String ngaygiaodukien = request.getParameter("ngaygiaodukien");
		if(ngaygiaodukien == null){
			ngaygiaodukien = "";
		}
		obj.setNgaygiaodukien(ngaygiaodukien);
		// Số lượng
		String soluong = request.getParameter("soluong");
		if( soluong == null){
			soluong = "";
		}
		obj.setSoluong(soluong);
		// Ghi chú
		String ghichu = request.getParameter("ghichu");
		if(ghichu == null){
			ghichu = "";
		}
		obj.setGhichu(ghichu);
		// Chành xe
		String chanhxe = request.getParameter("chanhxe");
		if(chanhxe == null){
			chanhxe = "";
		}
		obj.setChanhxe(chanhxe);
		// NV bán hàng-trình dược viên
		String nvbanhangId = request.getParameter("nvbanhangId");
		if( nvbanhangId == null){
			nvbanhangId = "";
		}
		obj.setNvbanhangId(nvbanhangId);
		// Ngày xác nhân SMS
		String ngayxacnhanSMS = request.getParameter("ngayxacnhanSMS");
		if(ngayxacnhanSMS == null){
			ngayxacnhanSMS = "";
		}
		obj.setNgayxacnhanSMS(ngayxacnhanSMS);
		// Ngày xác nhận Fax
		String ngayxacnhanFax = request.getParameter("ngayxacnhanFax");
		if( ngayxacnhanFax == null){
			ngayxacnhanFax  = "";
		}
		obj.setNgayxacnhanFax(ngayxacnhanFax);
		// Xác nhận bởi SMS
		String xacnhanSMS = request.getParameter("xacnhanSMS");
		if(xacnhanSMS == null){
			xacnhanSMS = "";
		}
		obj.setXacnhanSMS(xacnhanSMS);
		// Xác nhận bởi Fax
		String xacnhanFax = request.getParameter("xacnhanFax");
		if(xacnhanFax == null){
			xacnhanFax  = "";
		}
		obj.setXacnhanFax(xacnhanFax);
		
		String query =   " select a.PK_SEQ, a.machungtu, a.ngaygiaohang, ( convert( varchar(10), DATEADD(dd, cast( ISNULL(a.ngayvanchuyen, 0) " +
				         " as int), a.ngaygiaohang ), 120 ) )" +
				         " as ngaydukienHANGDEN, a.ngayNVBH_XACNHAN, a.trangthaiSMS, a.ngaynhanSMS, a.trangthaiFAX, a.ngaynhanFAX, a.chanhxe, a.soluong + a.donvitinh as soluong,   "+
						 " NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.ghichu, "+
						 "	 DATEDIFF( dd, getdate(), DATEADD(dd, cast( isnull(a.ngayvanchuyen, 0) as int ), a.ngaygiaohang) ) as SOS, "+
						 "	 ( select ten from DAIDIENKINHDOANH where PK_SEQ = ( select ddkd_fk from ERP_DONDATHANGNPP where PK_SEQ = ( select top(1) DDH_FK from ERP_GUISMSTDV_DDH order by stt asc ) ) ) as tdv, "+
						 "	 ( select ten from GIAMSATBANHANG where PK_SEQ = ( select GSBH_FK from ERP_DONDATHANGNPP where PK_SEQ = ( select top(1) DDH_FK from ERP_GUISMSTDV_DDH order by stt asc ) ) ) as ss "+
						 "from ERP_GUISMSTDV a  inner join ERP_GUISMSTDV_DDH b on a.pk_seq = b.guisms_fk inner join ERP_DONDATHANGNPP c on b.ddh_fk = c.pk_seq "+
						 "	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ    "+
						 "	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ   "+
						 " where a.npp_fk = '" + nppId + "' and a.trangthai = '1'  ";
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setKhTen(khId);
		
		// mã số SMS
		 if(masoSMS.length() >0){
			 query += " and a.machungtu = '" + masoSMS + "'";
		 }
		
		// Tên khu vực- tỉnh thành
		if(khuvuc.length() >0){
			query += " and c.KHACHHANG_FK in ( select kh.PK_SEQ from KHACHHANG kh left join DIABAN db" +
					 " on db.PK_SEQ = kh.diaban where db.KHUVUC_FK ='" + khuvuc + "')";
		}
		// Mã chứng từ
	    if(machungtu.length() >0){
	    	query += " and c.machungtu like '%" + machungtu + "%'";
	    }
		// Mã KH
	    if(khachhang.length() >0){
	    	query += " and c.KHACHHANG_FK = '" + khachhang + "'";
	    }
		// Ngày giao
	    if(ngaygiao.length() >0){
	    	query += " and a.ngaygiaohang = '" + ngaygiao + "'";
	    }
		// Ngày giao dự kién
	    if(ngaygiaodukien.length() >0){
	    	query+="and ( convert( varchar(10), DATEADD(dd, cast( ISNULL(a.ngayvanchuyen, 0) " +
			         " as int), a.ngaygiaohang ), 120 ) )='" + ngaygiaodukien+"'";
	    	/*query += " and a.ngaydukienHANGDEN = '" + ngaygiaodukien + "'";*/
	    }
		// Số lượng
	    if(soluong.length() >0){
	    	query += " and a.soluong = '" + soluong + "'";
	    }
		// Ghi chú
	    if(ghichu.length() >0){
	    	query += " and a.GHICHU like '%" + ghichu + "%'";
	    }
		// Chành xe
	    if(chanhxe.length() >0){
	    	query += " and a.chanhxe like N'%" + chanhxe + "%'";
	    }
		// NV bán hàng-trình dược viên
		if(nvbanhangId.length()>0){
			query += " and c.KHACHHANG_FK in (select KhachHang_FK " +
					 " from KhachHang_DaiDienKinhDoanh where DDKD_FK ='"+nvbanhangId +"')";
		}
		System.out.println("nhân viên bán hàng: "+ nvbanhangId);
		// Ngày xác nhân SMS
		if(ngayxacnhanSMS.length() >0){
			query += " and convert(varchar(10),a.ngaynhanSMS,126) = '" + ngayxacnhanSMS + "'";
		}
		// Ngày xác nhận Fax
		if(ngayxacnhanFax.length() >0){
			query += " and  convert(varchar(10),a.ngaynhanFAX,126) = '" + ngayxacnhanFax + "'";
		}
		// Xác nhận bởi SMS
		if(xacnhanSMS.length() >0){
			query += " and a.trangthaiSMS = '" + xacnhanSMS + "'";
		}
		// Xác nhận bởi Fax
		if(xacnhanFax.length() >0){
			query += " and a.trangthaiFax = '" + xacnhanFax + "'";
		}
		
		
		if(tungay.length() > 0)
			query += " and a.ngaygiaohang >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaygiaohang <= '" + denngay + "'";
	
		if(nguoitao.length()>0)
		{
			query+="  and nv.ten like N'%" + nguoitao + "%'";
		}

		System.out.print("cau tim kiem: " + query);
		return query;
	}
	
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	
	private void CreateStaticHeader(Workbook workbook, IErpGuiSMSTDVList obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    Style style;
	   	    
		Font font2 = new Font();	
		font2.setColor(Color.NAVY);
		font2.setSize(18);
		font2.setBold(true);
		
	    
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);	
		//style.setColor(new Color(204, 204, 204));
	    
	    cell.setValue("BÁO CÁO GỬI HÀNG TỈNH");	    
		cell.setStyle(style);
		
		font2 = new Font();	
		font2.setBold(true);
		font2.setSize(11);
	   
	    //tieu de
	    cell = cells.getCell("A2");cell.setValue("STT");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);    
	    cell = cells.getCell("B2");cell.setValue("Mã chứng từ");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("C2");cell.setValue("Số hóa đơn");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("D2");cell.setValue("Mã số SMS");  					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("E2");cell.setValue("Mã khách hàng"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("F2");cell.setValue("Khách hàng");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("G2");cell.setValue("Công ty");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("H2");cell.setValue("Ngày lập"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("I2");cell.setValue("Ngày gủi");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("J2");cell.setValue("Ngày gửi SMS - EMAIL");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("K2");cell.setValue("Ngày dự kiến hàng đến"); 			style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("L2");cell.setValue("Ngày Nhân viên bán hàng Xác nhận"); 	 		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("M2");cell.setValue("Xác nhận Bởi SMS");	      		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("N2");cell.setValue("Xác nhận Bởi FAX"); 	 				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("O2");cell.setValue("SOS");  			style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("P2");cell.setValue("TDV");	style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("Q2");cell.setValue("SUP");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("R2");cell.setValue("Tỉnh/Thành");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("S2");cell.setValue("Chành xe");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("T2");cell.setValue("Số lượng");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    
	    //worksheet.setName("BC GỬI HÀNG TỈNH");
	}

	private void CreateStaticData(Workbook workbook, String query) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		
		ResultSet rs = db.get(query);

		int i = 3;
		if(rs != null)
		{
			try 
			{
				Cell cell = null;
				
				Style style;
				Font font2 = new Font();
				//font2.setName("Calibri");				
				font2.setSize(11);
				
				int stt = 1;
				while(rs.next())
				{
					String xacnhanSMS = "Chưa xác nhận";
					if( rs.getString("trangthaiSMS").equals("1") )
						xacnhanSMS = rs.getString("ngaynhanSMS");
					
					String xacnhanFAX = "Chưa xác nhận";
					if( rs.getString("trangthaiFAX").equals("1") )
						xacnhanFAX = rs.getString("ngaynhanFAX");
					
					String sos = "";
					if( rs.getInt("sos") > 0 )
						sos = rs.getString("sos");
					
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue( stt ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue( rs.getString("machungtu") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue( rs.getString("sohoadon") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue( rs.getString("masoSMS") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue( rs.getString("makhachhang") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue( rs.getString("tenkhachhang") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue( rs.getString("tencongty") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( rs.getString("ngaylap") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( rs.getString("ngaygui") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue( rs.getString("ngayguiSMS") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue( rs.getString("ngaydukienHANGDEN") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("L" + Integer.toString(i));	cell.setValue( rs.getString("ngayNVBH_XACNHAN") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("M" + Integer.toString(i));	cell.setValue( xacnhanSMS ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("N" + Integer.toString(i));	cell.setValue( xacnhanFAX );	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("O" + Integer.toString(i));	cell.setValue( sos ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("P" + Integer.toString(i));	cell.setValue( rs.getString("tdv") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("Q" + Integer.toString(i));	cell.setValue( rs.getString("ss") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("R" + Integer.toString(i));	cell.setValue( rs.getString("tinhthanh") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("S" + Integer.toString(i));	cell.setValue( rs.getString("chanhxe") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("T" + Integer.toString(i));	cell.setValue( rs.getString("soluong") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					i++;
					stt ++;
				}
				rs.close();
			}
			catch (Exception e){ e.printStackTrace(); }
			finally
			{
				db.shutDown();
			}
		}
	}
	
	private void setCellBorderStyle(Cell cell) 
	{
		Style style = cell.getStyle();
		style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	
	
	private String getSearchQuery_BaoCao(HttpServletRequest request, IErpGuiSMSTDVList obj)
	{
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		String nvbanhang = request.getParameter("nvbanhang");
		if(nvbanhang == null)
			nvbanhang = "";
		obj.setNvbanhang(nvbanhang);
		
		String nguoigiao = request.getParameter("nguoigiao");
		if(nguoigiao == null)
			nguoigiao = "";
		obj.setNguoigiao(nguoigiao);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setKhTen(khId);
		
		/// bổ sung tìm kiếm
		// thêm các trường lọc
		// mã số SMS là mã chứng từ hả???
		String masoSMS = request.getParameter("masoSMS");
		if(masoSMS == null){
			masoSMS = "";
		}
		obj.setMasoSMS(masoSMS);
		
		// Tên khu vực- tỉnh thành
		String khuvuc = request.getParameter("khuvuc");
		if( khuvuc == null){
			khuvuc = "";
		}
		obj.setKhuvuc(khuvuc);
		// Mã chứng từ
		String machungtu = request.getParameter("machungtu");
		if( machungtu == null){
			machungtu = "";
		}
		obj.setMachungtu(machungtu);
		// Mã KH
		String khachhang = request.getParameter("khachhang");
		if( khachhang == null){
			khachhang = "";
		}
		obj.setKhachhang(khachhang);
		// Ngày giao
		String ngaygiao = request.getParameter("ngaygiao");
		if( ngaygiao == null){
			ngaygiao = "";
		}
		obj.setNgaygiao(ngaygiao);
		// Ngày giao dự kién
		String ngaygiaodukien = request.getParameter("ngaygiaodukien");
		if(ngaygiaodukien == null){
			ngaygiaodukien = "";
		}
		obj.setNgaygiaodukien(ngaygiaodukien);
		// Số lượng
		String soluong = request.getParameter("soluong");
		if( soluong == null){
			soluong = "";
		}
		obj.setSoluong(soluong);
		// Ghi chú
		String ghichu = request.getParameter("ghichu");
		if(ghichu == null){
			ghichu = "";
		}
		obj.setGhichu(ghichu);
		// Chành xe
		String chanhxe = request.getParameter("chanhxe");
		if(chanhxe == null){
			chanhxe = "";
		}
		obj.setChanhxe(chanhxe);
		// NV bán hàng-trình dược viên
		String nvbanhangId = request.getParameter("nvbanhangId");
		if( nvbanhangId == null){
			nvbanhangId = "";
		}
		obj.setNvbanhangId(nvbanhangId);
		// Ngày xác nhân SMS
		String ngayxacnhanSMS = request.getParameter("ngayxacnhanSMS");
		if(ngayxacnhanSMS == null){
			ngayxacnhanSMS = "";
		}
		obj.setNgayxacnhanSMS(ngayxacnhanSMS);
		// Ngày xác nhận Fax
		String ngayxacnhanFax = request.getParameter("ngayxacnhanFax");
		if( ngayxacnhanFax == null){
			ngayxacnhanFax  = "";
		}
		obj.setNgayxacnhanFax(ngayxacnhanFax);
		// Xác nhận bởi SMS
		String xacnhanSMS = request.getParameter("xacnhanSMS");
		if(xacnhanSMS == null){
			xacnhanSMS = "";
		}
		obj.setXacnhanSMS(xacnhanSMS);
		// Xác nhận bởi Fax
		String xacnhanFax = request.getParameter("xacnhanFax");
		if(xacnhanFax == null){
			xacnhanFax  = "";
		}
		obj.setXacnhanFax(xacnhanFax);
		
		
		String query =   "select c.machungtu, a.machungtu as masoSMS, ISNULL( d.MaFAST, e.maFAST ) as makhachhang, ISNULL( d.TEN, e.TEN ) as tenkhachhang, f.TEN as tencongty, "+
						 "		  a.NGAYTAO as ngaylap, a.ngaygiaohang as ngaygui, a.ngayguiSMS, ( convert( varchar(10), DATEADD(dd, cast( ISNULL(ngayvanchuyen, 0) as int), ngaygiaohang ), 120 ) ) as ngaydukienHANGDEN, a.ngayNVBH_XACNHAN,  "+
						 "		a.trangthaiSMS, a.ngaynhanSMS, a.trangthaiFAX, a.ngaynhanFAX, ISNULL( g.ten, h.TEN ) as tinhthanh, a.chanhxe, a.soluong + ' ' + a.donvitinh as soluong, "+
						 "	 DATEDIFF( dd, getdate(), DATEADD(dd, cast( isnull(a.ngayvanchuyen, 0) as int ), a.ngaygiaohang) ) as SOS, "+
						 "	 ( select ten from DAIDIENKINHDOANH where PK_SEQ = ( select ddkd_fk from ERP_DONDATHANGNPP where PK_SEQ = c.pk_seq ) ) as tdv, "+
						 "	 ( select ten from GIAMSATBANHANG where PK_SEQ = ( select GSBH_FK from ERP_DONDATHANGNPP where PK_SEQ = c.pk_seq ) ) as ss, "+
						 " 	  ISNULL( (	Select hdnpp.sohoadon + ',' AS [text()]  "+
						 " 				From ERP_HOADONNPP_DDH hd_dh inner join ERP_HOADONNPP hdnpp on hd_dh.HOADONNPP_FK = hdnpp.PK_SEQ   "+
						 " 				Where  hd_dh.DDH_FK  = c.PK_SEQ and hdnpp.TRANGTHAI not in ( 3, 5 )"+
						 " 				For XML PATH ('') ), '' )  as sohoadon "+
						 "from ERP_GUISMSTDV a   "+
						 "	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ    "+
						 "	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ  "+
						 "	inner join ERP_GUISMSTDV_DDH b on a.PK_SEQ = b.guisms_fk "+
						 "	inner join ERP_DONDATHANGNPP c on b.DDH_FK = c.PK_SEQ "+
						 "	left join KHACHHANG d on c.KHACHHANG_FK = d.PK_SEQ "+
						 "	left join NHAPHANPHOI e on c.NPP_DAT_FK = e.PK_SEQ "+
						 "	inner join NHAPHANPHOI f on a.NPP_FK = f.PK_SEQ "+
						 "	left join TINHTHANH g on d.TINHTHANH_FK = g.PK_SEQ "+
						 "	left join TINHTHANH h on e.TINHTHANH_FK = h.PK_SEQ "+
						 "where a.npp_fk = '" + nppId + "' and a.trangthai = '1' ";

		
		// tiêu chí tìm kiếm lọ
		// mã số SMS
		 if(masoSMS.length() >0){
			 query += " and a.machungtu = '" + masoSMS + "'";
		 }
		
		// Tên khu vực- tỉnh thành
		if(khuvuc.length() >0){
			query += " and c.KHACHHANG_FK in ( select kh.PK_SEQ from KHACHHANG kh left join DIABAN db" +
					 " on db.PK_SEQ = kh.diaban where db.KHUVUC_FK ='" + khuvuc + "')";
		}
		// Mã chứng từ
	    if(machungtu.length() >0){
	    	query += " and a.machungtu = '" + machungtu + "'";
	    }
		// Mã KH
	    if(khachhang.length() >0){
	    	query += " and c.KHACHHANG_FK = '" + khachhang + "'";
	    }
		// Ngày giao
	    if(ngaygiao.length() >0){
	    	query += " and a.ngaygiaohang = '" + ngaygiao + "'";
	    }
		// Ngày giao dự kién
	    if(ngaygiaodukien.length() >0){
	    	query+=" and ( convert( varchar(10), DATEADD(dd, cast( ISNULL(a.ngayvanchuyen, 0) " +
	         " as int), a.ngaygiaohang ), 120 ) )='" + ngaygiaodukien+"'";
	    	//query += " and a.ngaydukienHANGDEN = '" + ngaygiaodukien + "'";
	    }
		// Số lượng
	    if(soluong.length() >0){
	    	query += " and a.soluong = '" + soluong + "'";
	    }
		// Ghi chú
	    if(ghichu.length() >0){
	    	query += " and a.GHICHU like '%" + ghichu + "%'";
	    }
		// Chành xe
	    if(chanhxe.length() >0){
	    	query += " and a.chanhxe like '%" + chanhxe + "%'";
	    }
		// NV bán hàng-trình dược viên
		if(nvbanhangId.length()>0){
			query += " and c.DDKD_FK ='"+nvbanhangId + "'";
		}
		// Ngày xác nhân SMS
		if(ngayxacnhanSMS.length() >0){
			query += " and convert(varchar(10),a.ngaynhanSMS,126) = '" + ngayxacnhanSMS + "'";
		}
		// Ngày xác nhận Fax
		if(ngayxacnhanFax.length() >0){
			query += " and  convert(varchar(10),a.ngaynhanFAX,126) = '" + ngayxacnhanFax + "'";
		}
		// Xác nhận bởi SMS
		if(xacnhanSMS.length() >0){
			query += " and a.trangthaiSMS = '" + xacnhanSMS + "'";
		}
		// Xác nhận bởi Fax
		if(xacnhanFax.length() >0){
			query += " and a.trangthaiFax = '" + xacnhanFax + "'";
		}
		
		if(tungay.length() > 0)
			query += " and a.ngaygiaohang >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaygiaohang <= '" + denngay + "'";
	
		if(nguoitao.length() > 0)
			query += "  and nv.ten like N'%" + nguoitao + "%'";

		Utility util = new Utility();
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "c", "khachhang_fk", obj.getLoainhanvien(), obj.getDoituongId() );
		
		query += " order by a.Created_Date asc "; 
		
		System.out.print("::: LAY BAO CAO: " + query);
		return query;
	}
	
	
}
