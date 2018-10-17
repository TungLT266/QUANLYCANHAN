package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

@WebServlet("/BaoCaoKhachHangTT")
public class BaoCaoKhachHangTT extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BaoCaoKhachHangTT() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
		
	   String view = request.getParameter("view");
	    if(view == null)
	    	view = "";
	    
		 String nppId ="";
			if(view.equals("TT"))
			{
				 nppId = util.antiSQLInspection(request.getParameter("nppId"));
				if (nppId == null)
						nppId = "";
					obj.setnppId(nppId);
			}else
			{
				nppId=util.getIdNhapp(userId);
				obj.setnppId(nppId);
			}
		
		obj.init();
		obj.CreaterRsKh();
		
		session.setAttribute("obj", obj);	
		session.setAttribute("userTen", userTen);
		
		if(!view.equals("TT"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Distributor/BaoCaoKhachHangTT.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoKhachHangTT.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();	 		
 		IStockintransit obj = new Stockintransit();	
 		OutputStream out = response.getOutputStream();
 		Utility util =new Utility();
 		

		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");	
		obj.setuserId(userId==null? "":userId);
		obj.setuserTen(userTen==null? "":userTen);
		
		
		String view = request.getParameter("view");
    if(view == null)
    	view = "";
		
  	String nppId ="";
		if(view.equals("TT")){
			 nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			obj.setnppId(nppId);
		}else
		{
			nppId=util.getIdNhapp(userId);
			obj.setnppId(nppId);
		}
		
		
		obj.setkenhId(request.getParameter("kenhid")==null? "":request.getParameter("kenhid"));				
		obj.setvungId(request.getParameter("vungid")==null? "":request.getParameter("vungid"));
		obj.setkhuvucId(request.getParameter("khuvucid")==null? "":request.getParameter("khuvucid"));
		obj.SetIdLoaicuahang(request.getParameter("lchid")==null? "":request.getParameter("lchid"));
		
		obj.SetIdHangCuahang(request.getParameter("hchid")==null? "":request.getParameter("hchid"));
		
		obj.SetIdVitriCuahang( request.getParameter("vtchid")==null? "":request.getParameter("vtchid"));
		
		obj.SetIdTinhThanh(  request.getParameter("tinhthanhid")==null? "":request.getParameter("tinhthanhid"));
		
		obj.SetIdQuanHuyen(  request.getParameter("quanhuyenid")==null? "":request.getParameter("quanhuyenid"));
		
		String condition = "";
		if(obj.getvungId().length()>0)
			condition +=" and v.pk_seq = '"+ obj.getvungId() +"'";
		
		if(obj.getkhuvucId().length()>0)
			condition +=" and kv.pk_seq = '"+ obj.getkhuvucId() +"'";
		
		if(obj.getnppId().length()>0)
			condition +=" and npp.pk_seq = '"+ obj.getnppId() +"'";
		
		if(obj.getkenhId().length()>0)
			condition +=" and kbh.pk_seq ='" + obj.getkenhId() +"'";
		
		if(obj.GetIdTinhThanh().length() >0 ){
			condition +=" and tt.pk_seq ='" + obj.GetIdTinhThanh() +"'";
		}
		if(obj.GetIdQuanHuyen().length() >0 ){
			condition +=" and qh.pk_seq ='" + obj.GetIdQuanHuyen() +"'";
		}
		if(obj.GetIdHangCuahang().length() >0 ){
			condition +=" and hch.pk_seq ='" + obj.GetIdHangCuahang() +"'";
		}
		if(obj.GetIdLoaicuahang().length() >0 ){
			condition +=" and lch.pk_seq ='" + obj.GetIdLoaicuahang() +"'";
		}
		
		if(obj.GetIdVitriCuahang().length() >0 ){
			condition +=" and vtch.pk_seq ='" + obj.GetIdVitriCuahang() +"'";
		}
		
		if(obj.getnppId().length() >0 ){
			condition +=" and kh.npp_Fk ='" + obj.getnppId() +"'";
		}
		
		obj.settype(util.antiSQLInspection(request.getParameter("type")) != null ? util.antiSQLInspection(request.getParameter("type")) : "");
		
		String phanquyen="";
		if(view.equals("TT"))
		{
			condition += " and kh.npp_fk in " + util .quyen_npp(obj.getuserId()) + " and kh.kbh_fk in  " + util.quyen_kenh(obj.getuserId());
			phanquyen += " and a.npp_fk in " + util .quyen_npp(obj.getuserId()) + " and a.kbh_fk in  " + util.quyen_kenh(obj.getuserId());
			
		}

		try
		{			
					String action = request.getParameter("action");				
					if (action.equals("create")) 
					{
						response.setContentType("application/xlsm");
						response.setHeader("Content-Disposition","attachment; filename=BaoCaoKhachHangNpp_"+util.setTieuDe(obj)+".xlsm");
					
						
						if(obj.gettype().equals("1"))
						{
							CreatePivotTable(out,obj,condition);
						}else 
						{
							CreatePivotTable_Details(out,obj,phanquyen);
						}
					}	
					
		 }
		    catch (Exception ex)
		    {
		       obj.setMsg(ex.getMessage());
		    }
		    obj.init();
		    obj.CreaterRsKh();
			session.setAttribute("obj", obj);	
			session.setAttribute("userTen", obj.getuserTen());
			
	
	    
	    
			if(!view.equals("TT"))
			{
				String nextJSP = "/TraphacoHYERP/pages/Distributor/BaoCaoKhachHangTT.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoKhachHangTT.jsp";
				response.sendRedirect(nextJSP);
			}
			
			
	}
	
	private void CreatePivotTable(OutputStream out,IStockintransit obj, String condition) throws Exception
    {       
 		String strfstream = getServletContext().getInitParameter("path")+"\\BaoCaoKhachHang.xlsm";
 		FileInputStream fstream = new FileInputStream(strfstream);
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook,obj);	     
	    CreateStaticData(workbook, obj, condition);
	    workbook.save(out);
	    fstream.close();
	}
	
	private void CreatePivotTable_Details(OutputStream out,IStockintransit obj, String condition) throws Exception
  {       
		String strfstream = getServletContext().getInitParameter("path")+"\\BaoCaoKhachHang_Details.xlsm";
		FileInputStream fstream = new FileInputStream(strfstream);
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		CreateStaticHeader_Details(workbook,obj);	     
	  CreateStaticData_Details(workbook, obj, condition);
	  workbook.save(out);
	  fstream.close();
}
	
 	
 	private void CreateStaticData_Details(Workbook workbook, IStockintransit obj, String condition) throws Exception
  {
 		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    dbutils db = new dbutils();	  

	    String sql = 
	    		 "	select isnull(a.mafast,'') as mafast , a.pk_seq as khId, "+   
	    		 "   		a.smartid, a.ten as khTen, a.trangthai,   "+   
	    		 "   		e.diengiai as kbhTen, e.pk_seq as kbhId, h.ten as nppTen, h.pk_seq as nppId,  "+   
	    		 "   		a.dienthoai, a.diachi,  isnull(a.MASOTHUE,'') as masothue , ISNULL(a.MaHD,'') as maHD, n.ten as loaKH , "+   
	    		 "   		STUFF      "+   
	    		 "   			(    "+   
	    		 "   				(   "+   
	    		 "   					select DISTINCT TOP 100 PERCENT ' , ' + tbh.DIENGIAI   "+   
	    		 "   					from KHACHHANG_TUYENBH khtbh inner join TUYENBANHANG tbh on tbh.PK_SEQ=khtbh.TBH_FK    "+   
	    		 "   					where khtbh.KHACHHANG_FK=a.PK_SEQ and tbh.NPP_FK=a.NPP_FK   "+   
	    		 "   					ORDER BY ' , ' + tbh.DIENGIAI      "+   
	    		 "   					FOR XML PATH('')      "+   
	    		 "   				), 1, 2, ''   "+   
	    		 "   			) + ' '  AS tbhTen,a.CHUCUAHIEU,MaHD,XuatKhau,THANHTOAN,a.NgaySinh,THANHTOANQUY,PT_CHIETKHAU,THOIHANNO,MAUHOADON,KHO_FK,tt.TEN as ttTen, 	qh.TEN as qhTen,kho.TEN as khoTen "+
	    		 "  ,loaiTT.TEN as ttThang,loaiQ.ten as ttQuy,  "+
	    		
				   " 			STUFF  "+      
					 "			(      "+
					 "				(    "+
					 "					select DISTINCT TOP 100 PERCENT ' , ' + tbh.TEN "+   
					 "					from NVGN_KH khtbh inner join NHANVIENGIAONHAN tbh on tbh.PK_SEQ=khtbh.NVGN_FK  "+    
					 "				where khtbh.KHACHHANG_FK=a.PK_SEQ and tbh.NPP_FK=a.NPP_FK     "+
					 "				ORDER BY ' , ' + tbh.TEN        "+
					 "					FOR XML PATH('')  "+
					 "			), 1, 2, ''     "+
					 "			) + ' '  AS nvgnTen  "+
	    		 "   from    khachhang a "+   
	    		 "   	left join mucchietkhau d on a.chietkhau_fk = d.pk_seq   "+   
	    		 "   	left join kenhbanhang e on a.kbh_fk = e.pk_seq    "+   
	    		 "   	inner join nhaphanphoi h on a.npp_fk = h.pk_seq    "+   
	    		 "   	left join LOAIKHACHHANG n on a.XuatKhau= n.pk_seq  "+   
	    		 "   	left join TINHTHANH tt on tt.PK_SEQ=a.TINHTHANH_FK"+   
	    		 "   	left join QUANHUYEN qh on qh.PK_SEQ=a.QUANHUYEN_FK"+   
	    		 "   	left join KHO kho on kho.PK_SEQ=a.KHO_FK " +
	    		 " left join loaiTT ON loaiTT.PK_sEQ=a.THANHTOAN AND loaiTT.LoaiTT=0  "+
	    		 "	left join loaiTT  loaiQ ON loaiQ.PK_sEQ=a.THANHTOANQUY AND loaiQ.LoaiTT=1 " +
	    		 " where 1=1 "+condition;
	    
	  	Utility  util = new Utility();
			    if(obj.getphanloai().equals("2")&& !obj.getLoaiNv().equals("3"))
					{
						sql+= " and a.npp_fk in " + util.quyen_npp(obj.getuserId())+"";
					}
			    
			    if(obj.getnppId().length()>0)
			    {
			    	sql+= "and a.npp_fk='"+obj.getnppId()+"'" ;
			    }
  
	    	System.out.println("Get Sql : "+sql);
	
	
	 	   	ResultSet rs = db.get(sql); 	   
	 	    int i = 9;
	 	    int Stt=1;
	 		if(rs!=null)
	 		{
	 			try 
	 			{
	 				Cell cell = null;
	 				while(rs.next())
	 				{  					
	 					
	 					cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(Stt   );
						cell = cells.getCell("B" + Integer.toString(i)); cell.setValue( rs.getString("tbhTen")==null?"":rs.getString("tbhTen")   );
						cell = cells.getCell("C" + Integer.toString(i)); cell.setValue( rs.getString("mafast")==null?"":rs.getString("mafast")   );
						cell = cells.getCell("D" + Integer.toString(i)); cell.setValue( rs.getString("khTen")==null?"":rs.getString("khTen")   );
						cell = cells.getCell("E" + Integer.toString(i)); cell.setValue( rs.getString("chucuahieu")==null?"":rs.getString("chucuahieu")   );
						cell = cells.getCell("F" + Integer.toString(i)); cell.setValue( rs.getString("diachi")==null?"":rs.getString("diachi")   );
						cell = cells.getCell("G" + Integer.toString(i)); cell.setValue( rs.getString("ttTen")==null?"":rs.getString("ttTen")   );
						cell = cells.getCell("H" + Integer.toString(i)); cell.setValue( rs.getString("qhTen")==null?"":rs.getString("qhTen")   );
						cell = cells.getCell("I" + Integer.toString(i)); cell.setValue( rs.getString("dienthoai")==null?"":rs.getString("dienthoai")   );
						cell = cells.getCell("J" + Integer.toString(i)); cell.setValue( rs.getString("masothue")==null?"":rs.getString("masothue")   );
						cell = cells.getCell("K" + Integer.toString(i)); cell.setValue( rs.getString("kbhTen")==null?"":rs.getString("kbhTen")   );
						cell = cells.getCell("L" + Integer.toString(i)); cell.setValue( rs.getString("ttThang")==null?"":rs.getString("ttThang")   );
						cell = cells.getCell("M" + Integer.toString(i)); cell.setValue( rs.getString("ttQuy")==null?"":rs.getString("ttQuy")   );
						cell = cells.getCell("N" + Integer.toString(i)); cell.setValue( rs.getString("loaKH")==null?"":rs.getString("loaKH")   );
						cell = cells.getCell("O" + Integer.toString(i)); cell.setValue( rs.getString("mahd")==null?"":rs.getString("mahd")   );
						cell = cells.getCell("P" + Integer.toString(i)); cell.setValue( rs.getString("nvgnTen")==null?"":rs.getString("nvgnTen")   );
						cell = cells.getCell("Q" + Integer.toString(i)); cell.setValue( rs.getString("PT_CHIETKHAU")==null?"":rs.getString("PT_CHIETKHAU")   );
						cell = cells.getCell("R" + Integer.toString(i)); cell.setValue( rs.getString("khoTen")==null?"":rs.getString("khoTen")   );
						i++;
						Stt++;
	 				}
	 		}catch (Exception e){ 	
	 			System.out.println("LOI : "+e.toString());
	 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay. Loi : "+e.toString());
	 		}
	 		finally{
	 			if(rs != null)
	 			rs.close();
	 			if(db!=null){
	 				db.shutDown();
	 			}
	 		}
	 		}else{
	 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay...");
	 		}
	
	  
  }
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private void setCellBorderStyle(Cell cell) {
		Style style = cell.getStyle();
		style.setHAlignment(HorizontalAlignmentType.LEFT);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}

	private void CreateStaticHeader_Details(Workbook workbook, IStockintransit obj)
  {
		Worksheets worksheets = workbook.getWorksheets();
    Worksheet worksheet = worksheets.getSheet(0);
   	   
    Cells cells = worksheet.getCells();
    Style style;
   	    
    Cell cell = cells.getCell("A1"); 
    cell.setValue("DANH SÁCH KHÁCH HÀNG");	    
    style = cell.getStyle();
		Font font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setColor(Color.RED);
		font2.setSize(18);
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);					
		cell.setStyle(style);
		
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setBold(true);
		font2.setSize(11);
   
    cell = cells.getCell("A3");
    cell.setValue("Ngày tạo : " + this.getDateTime());
    style = cell.getStyle();
    style.setFont(font2);
	cell.setStyle(style);
    
	/* cell = cells.getCell("A8");cell.setValue("STT");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell); 
    cell = cells.getCell("A8");cell.setValue("Nhân viên bán hàng");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);    
    cell = cells.getCell("B8");cell.setValue("Mã KH");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("C8");cell.setValue("Tên Khách hàng");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("D8");cell.setValue("Người mua hàng");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("E8");cell.setValue("Địa xuất HĐ");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("F8");cell.setValue("Tỉnh");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("G8");cell.setValue("Thành phố");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("H8");cell.setValue("Điện thoại");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("I8");cell.setValue("Mã số thuế");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("J8");cell.setValue("Kênh bán hàng");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("K8");cell.setValue("Thanh toán tháng");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("L8");cell.setValue("Thanh toán quý");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("M8");cell.setValue("Loại khách hàng");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("N8");cell.setValue("Hợp đồng");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("O8");cell.setValue("Nhân viên giao nhận");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("P8");cell.setValue("Phần trăm CK");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
    cell = cells.getCell("Q8");cell.setValue("Kho");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);*/
    
    worksheet.setName("Danh sách Khách Hàng");
	  
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
	    
	    cell.setValue("BÁO CÁO KHÁCH HÀNG");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	
	    
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
	    cell.setValue("Được tạo bởi:  " + obj.getuserTen());
			  
	    cell = cells.getCell("AA1"); cell.setValue("KENH");
		cell = cells.getCell("AB1"); cell.setValue("MIEN");
		cell = cells.getCell("AC1"); cell.setValue("VUNG");
 	    cell = cells.getCell("AD1"); cell.setValue("IDNPP");  	
 	   cell = cells.getCell("AE1"); cell.setValue("MANPP(SITECODE)");
	    cell = cells.getCell("AF1"); cell.setValue("NHAPHANPHOI");
	    cell = cells.getCell("AG1"); cell.setValue("TRANGTHAINPP");
	    cell = cells.getCell("AH1"); cell.setValue("IDKHACHHANG");
	    cell = cells.getCell("AI1"); cell.setValue("MAKH");
	    cell = cells.getCell("AJ1"); cell.setValue("TENKH");
	    cell = cells.getCell("AK1"); cell.setValue("DIACHI");
	    cell = cells.getCell("AL1"); cell.setValue("DIENTHOAI");
	    cell = cells.getCell("AM1"); cell.setValue("TINHTHANH");
	    cell = cells.getCell("AN1"); cell.setValue("QUANHUYEN");
	    cell = cells.getCell("AO1"); cell.setValue("HANGCUAHANG");
	    cell = cells.getCell("AP1"); cell.setValue("LOAICUAHANG");
	    cell = cells.getCell("AQ1"); cell.setValue("VITRICUAHANG");
	    cell = cells.getCell("AR1"); cell.setValue("TRANGTHAIKH");
 	}
 	
	private void CreateStaticData(Workbook workbook, IStockintransit obj, String condition) throws Exception
 	{
 		Worksheets worksheets = workbook.getWorksheets();
 	    Worksheet worksheet = worksheets.getSheet(0);
 	    Cells cells = worksheet.getCells();
 	    dbutils db = new dbutils();	  
	
 	    String sql = " select KBH.TEN AS KENHBANHANG,  v.ten as vung,kv.ten as khuvuc,NPP.PK_SEQ AS IDNPP ,NPP.SITECODE , "+
 	   " CASE WHEN NPP.TRANGTHAI=1 THEN N'HOẠT ĐỘNG' ELSE N'NGƯNG HOẠT ĐỘNG' END AS TRANGTHAINPP ,NPP.TEN AS NHAPHANPHOI , "+
			" kh.pk_seq AS IDKH, isnull(kh.smartid,'N/A')  as MAKH, kh.ten AS TENKH ,kh.diachi,ISNULL( kh.dienthoai,'N/A') AS DIENTHOAI, "+
			 " CASE WHEN KH.TRANGTHAI=1 THEN N'HOẠT ĐỘNG' ELSE N'NGƯNG HOẠT ĐỘNG' END AS TRANGTHAIKH,isnull( tt.ten,'N/A') as tinhthanh,  "+
			" isnull(qh.ten,'N/A') as quanhuyen,ISNULL(hch.diengiai,'N/A') as hangcuahang,ISNULL(lch.diengiai,'N/A') as loaicuahang, "+
			" ISNULL( vtch.diengiai,'N/A') as vitricuahang from khachhang kh inner join  "+
			" nhaphanphoi npp on npp.pk_seq=kh.npp_fk "+
			" inner join khuvuc kv on kv.pk_seq=npp.khuvuc_fk "+
			" inner join vung v on v.pk_seq=kv.vung_fk "+
			" left join tinhthanh tt on tt.pk_seq=kh.tinhthanh_fk "+
			" left join quanhuyen qh on qh.pk_seq=kh.quanhuyen_fk "+
			" left join loaicuahang lch on lch.pk_seq=kh.lch_fk "+
			" left join hangcuahang hch on hch.pk_seq=kh.hch_fk "+
			 " left join vitricuahang vtch on vtch.pk_seq=kh.vtch_fk  " +
			 " LEFT JOIN KENHBANHANG KBH ON KBH.PK_SEQ=KH.KBH_FK "+
 	    	" WHERE 1=1 "+condition;
 	 
		System.out.println("Get Sql : "+sql);
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
 				cells.setColumnWidth(6, 20.0f);
 				cells.setColumnWidth(7, 20.0f);
 				cells.setColumnWidth(8, 20.0f);
 				cells.setColumnWidth(9, 20.0f);
 				cells.setColumnWidth(10, 20.0f);
 				
 				Cell cell = null;
 				Style style;
 				//String khoKM="";
 				while(rs.next())
 				{  					
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(rs.getString("KENHBANHANG"));
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(rs.getString("vung"));			
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(rs.getString("KHUVUC"));
 					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(rs.getString("IDNPP"));
 					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(rs.getString("SITECODE"));
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(rs.getString("NHAPHANPHOI"));
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(rs.getString("TRANGTHAINPP"));
					
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(rs.getString("IDKH"));
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(rs.getString("MAKH"));
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(rs.getString("TENKH"));
					
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(rs.getString("DIACHI"));
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(rs.getString("DIENTHOAI"));
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(rs.getString("TINHTHANH"));
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(rs.getString("QUANHUYEN"));

					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(rs.getString("HANGCUAHANG"));
					cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(rs.getString("LOAICUAHANG"));
					
					cell = cells.getCell("AQ" + Integer.toString(i)); cell.setValue(rs.getString("VITRICUAHANG"));
					
					cell = cells.getCell("AR" + Integer.toString(i)); cell.setValue(rs.getString("TRANGTHAIKH"));
					
					i++;
 				}

				//ReportAPI.setHidden(workbook, obj.getFieldShow().length+1);
				
			    setAn(workbook, 50);
 		}catch (Exception e){ 	
 			System.out.println("LOI : "+e.toString());
 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay. Loi : "+e.toString());
 		}
 		finally{
 			if(rs != null)
 			rs.close();
 			if(db!=null){
 				db.shutDown();
 			}
 		}
 		}else{
 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay...");
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
