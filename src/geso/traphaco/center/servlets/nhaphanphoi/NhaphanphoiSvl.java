package geso.traphaco.center.servlets.nhaphanphoi;

import geso.traphaco.center.beans.nhaphanphoi.*;
import geso.traphaco.center.beans.nhaphanphoi.imp.*;
import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class NhaphanphoiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;   
    
    public NhaphanphoiSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		INhaphanphoiList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();

	    Utility util = new Utility();
	    out = response.getWriter();
	    obj = new NhaphanphoiList(); 

	   /* obj.setRequestObj(request);*/
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String nppId = util.getId(querystring);
	    
	    System.out.print("action"+ action);
	   
	    if (action.equals("delete"))
	    {	   		  
	    	Delete(nppId, obj);
	    	obj.getMsg();
	    	

	    }
	   	
	    obj.setUserId(userId);
	    
	    obj.setIsKhachhang(request.getParameter("isKH"));
	  
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Center/NhaPhanPhoi.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		

		INhaphanphoiList obj = new NhaphanphoiList();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    OutputStream out = response.getOutputStream();
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
		String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
    	if (maFAST == null)
    		maFAST = "";    	
    	obj.setMaFAST(maFAST);
	   
		String ten = util.antiSQLInspection(request.getParameter("nppTen"));
    	if ( ten == null)
    		ten = "";
    	obj.setTen(ten);
    	
    	String sodienthoai = util.antiSQLInspection(request.getParameter("DienThoai"));
    	if (sodienthoai == null)
    		sodienthoai = "";    	
    	obj.setSodienthoai(sodienthoai);
    	    	
    	String kvId = util.antiSQLInspection(request.getParameter("kvId"));
    	if (kvId == null)
    		kvId = "";    	
    	obj.setKvId(kvId);
    	
    	String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
    	if (kenhId == null)
    		kenhId = "";    	
    	obj.setKenhId(kenhId);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String isKH = util.antiSQLInspection(request.getParameter("isKH"));
    	if (isKH == null)
    		isKH = "";    	
    	obj.setIsKhachhang(isKH);
    	
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	   
	    String diaban = util.antiSQLInspection(request.getParameter("diaban"));   	
    	if (diaban == null)
    		diaban = "";    	
    	obj.setDiaban(diaban);
    	String vung = util.antiSQLInspection(request.getParameter("vung"));   	
    	if (vung == null)
    		vung = "";   
	    obj.setVung(vung);
	    String nextJSP = "";
	    if (action.equals("new")){
	    	
	    	// Empty Bean for distributor
	    	INhaphanphoi nppBean = (INhaphanphoi) new Nhaphanphoi("", request.getParameter("isKH"));
	    	nppBean.setUserId(userId);
	    	nppBean.createRS();
	    	// Save Data into session
	    	session.setAttribute("nppBean", nppBean);
    		
    		nextJSP = "/TraphacoHYERP/pages/Center/NhaPhanPhoiNew.jsp";
    		session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    	    response.sendRedirect(nextJSP);
    		
	    } 
	    else if (action.equals("search"))
	    {
	    	obj.setRequestObj(request);	    	
	    
	    	obj.setUserId(userId);
	    	obj.setIsKhachhang(isKH);
	    	String search = getSearchQuery(request);
	    	obj.init(search);
	    	nextJSP = "/TraphacoHYERP/pages/Center/NhaPhanPhoi.jsp"; 
    		session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    	    response.sendRedirect(nextJSP);
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	
	    	obj.setUserId(userId);
	    	obj.setIsKhachhang(isKH);
	    	obj.setNxtApprSplitting(Integer.parseInt(util.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
	    	String search = getSearchQuery(request);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
			nextJSP = "/TraphacoHYERP/pages/Center/NhaPhanPhoi.jsp";
			response.sendRedirect(nextJSP);
	    }
	    
	    else if(action.equals("excel")){
	    	try
	    	{
	    		IStockintransit obj1 = new Stockintransit();
	    		response.setContentType("application/xlsm");
	    		response.setHeader("Content-Disposition", "attachment; filename=DanhSachNhaPhanPhoi.xlsm");
	    		OutputStream out1 = response.getOutputStream();
				String query=
			" 		SELECT A.PK_SEQ as nppID,a.MA as nppMẠ,a.MaFAST,a.TEN as nppTEN,a.XUATTAIKHO,a.DIACHI,d.ten as kvTEN,V.TEN AS Vten,b.TEN as ttTEN,c.TEN as qhTEN  "+
			"		,a.DIACHIXHD,a.TENNGUOIDAIDIEN,a.MASOTHUE,a.DIENTHOAI,a.FAX,a.EMAIL,a.HINHTHUCTHANHTOAN,a.NGANHANG,a.SOTAIKHOAN,a.KYHIEUHOADON,a.SOHOADONTU,a.SOHOADONDEN,a.MaKho,a.MaNX,a.TenKyHd,a.tuxuatETC,a.DUNGCHUNGKENH,a.TUTAOHOADON,g.TEN as TrucThuoc "+
			"	FROM NHAPHANPHOI a left join TINHTHANH b on b.PK_SEQ=a.TINHTHANH_FK "+
			"		LEFT JOIN QUANHUYEN c on c.PK_SEQ=a.QUANHUYEN_FK "+  
			"		left join KHUVUC d on d.PK_SEQ=a.KHUVUC_FK  "+
			"		left join VUNG V on V.PK_SEQ=D.VUNG_FK  "+
			"		left join LOAINHAPHANPHOI e on e.maloai=a.loaiNPP "+
			"		left join NHAPHANPHOI g on g.PK_SEQ=a.TRUCTHUOC_FK " +
			" where 1=1  ";
				if (ten.length()>0)
	    	{
				query = query + " and a.TIMKIEM like upper(dbo.ftBoDau(N'%" +ten.trim() + "%'))";				
	    	}
	    	
	    	if (sodienthoai.length()>0)
	    	{
				query = query + " and upper(a.dienthoai) like upper('%" + sodienthoai + "%')";
	    	}
	    	if (kvId.length()>0)
	    	{
				query = query + " and d.pk_seq = '" + kvId + "'";
	    	}
	    	
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = '" + trangthai + "'";
	    		
	    	}
	    	if(kenhId.length()>0)
	    	{
	    		query+=" and a.pk_seq in (select npp_fk from NHAPP_KBH where kbh_fk='"+kenhId+"'   )";
	    	}
	    	
	    	if(maFAST.length()>0)
	    	{
	    		query+= " and a.maFAST like '%"+maFAST+"%' ";
	    	}

	    	query+= " order by vTEN,kvTEN,ttTEN";
				
				ExportToExcel(out1,obj1,query);
				nextJSP = "/TraphacoHYERP/pages/Center/NhaPhanPhoi.jsp";
				response.sendRedirect(nextJSP);
	    	}
	    	catch (Exception e) 
	    	{
				e.printStackTrace();
			}
	    }
	
	    
	}

	 private void ExportToExcel(OutputStream out,IStockintransit obj, String query)throws Exception
	 {
			try{ 

				String chuoi=getServletContext().getInitParameter("path") + "\\DanhSachNhaPhanPhoi.xlsm";
				
				FileInputStream fstream = new FileInputStream(chuoi);
				
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				
			/*	CreateHeader(workbook,obj);*/
				FillData(workbook,obj,query);
				
				workbook.save(out);	
				fstream.close();
			}catch(Exception ex){
				throw new Exception(ex.getMessage());
			}
	}
	 
	 private void CreateHeader(Workbook workbook,IStockintransit obj) throws Exception 
	 {
			try 
			{
				Worksheets worksheets = workbook.getWorksheets();
				Worksheet worksheet = worksheets.getSheet(0);
				worksheet.setName("Sheet1");

				Cells cells = worksheet.getCells();
				cells.setRowHeight(0, 50.0f);
				Cell cell = cells.getCell("A1");
				ReportAPI.getCellStyle(cell, Color.RED, true, 16,"DANH SÁCH NHÀ PHÂN PHỐI");
				cell = cells.getCell("A2");
				
			

		    Style style;
		    Font font = new Font();
		    font.setColor(Color.RED);//mau chu
		    font.setSize(16);// size chu
		   	font.setBold(true);
		   	
		    String message = "";
				cells.setRowHeight(2, 18.0f);
				cell = cells.getCell("A3");
				ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   
		    		
			cell = cells.getCell("A8");	cell.setValue("STT");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("B8");	cell.setValue("VÙNG\\MIỀN");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("C8");	cell.setValue("KHU VỰC");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("D8");	cell.setValue("TỈNH THÀNH");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("E8");	cell.setValue("QUẬN HUYỆN");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);

			cell = cells.getCell("F8");	cell.setValue("MÃ CN/ĐT");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("G8");	cell.setValue("MÃ FAST");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("H8");	cell.setValue("CN/ĐT");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("I8");	cell.setValue("TÊN NGƯỜI ĐẠI DIỆN");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("J8");	cell.setValue("TRỰC THUỘC");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("K8");	cell.setValue("ĐỊA CHỈ");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("L8");	cell.setValue("ĐỊA CHỈ XUẤT HĐ");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
		
			cell = cells.getCell("M8");	cell.setValue("MÃ SỐ THUẾ");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("N8");	cell.setValue("MÃ KHO");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("O8");	cell.setValue("MÃ NHẬP XUẤT");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("P8");	cell.setValue("FAX");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("Q8");	cell.setValue("TÊN KÝ HĐ");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			
			cell = cells.getCell("R8");	cell.setValue("KÝ HIỆU HĐ");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);	
			
			
			cell = cells.getCell("S8");	cell.setValue("SỐ HĐ TỪ");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("T8");	cell.setValue("SỐ HĐ ĐẾN");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("U8");	cell.setValue("XUẤT TẠI KHO");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			
			cell = cells.getCell("V8");	cell.setValue("SỐ ĐIỆN THOẠI");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("W8");	cell.setValue("SỐ FAX");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("X8");	cell.setValue("EMAIL");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			
			cell = cells.getCell("Y8");	cell.setValue("HÌNH THỨC CHUYỂN KHOẢN");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("Z8");	cell.setValue("NGÂN HÀNG");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
			
			cell = cells.getCell("AA8");	cell.setValue("SỐ TÀI KHOẢN");
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
			} catch (Exception ex) {
				System.out.println(ex.toString());
				throw new Exception("Khong the tao duoc Header cho bao cao.!!!");
			}
		}	
	
	 private void FillData(Workbook workbook,IStockintransit obj, String query)throws Exception
	 {
			try
			{
				Worksheets worksheets = workbook.getWorksheets();
				Worksheet worksheet = worksheets.getSheet(0);
				worksheet.setGridlinesVisible(false);
				Cells cells = worksheet.getCells();
				dbutils db = new dbutils();
				
				ResultSet rs = db.get(query);
				Cell cell = null;
				int countRow = 9;
				int SoTT=0;
				while(rs.next())
				{
					cell = cells.getCell("A" + String.valueOf(countRow));		cell.setValue(++SoTT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					cell = cells.getCell("B" + String.valueOf(countRow));		cell.setValue(rs.getString("vTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("C" + String.valueOf(countRow));		cell.setValue(rs.getString("kvTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("D" + String.valueOf(countRow));		cell.setValue(rs.getString("ttTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("E" + String.valueOf(countRow));		cell.setValue(rs.getString("qhTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("F" + String.valueOf(countRow));		cell.setValue(rs.getString("nppMẠ"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("G" + String.valueOf(countRow));		cell.setValue(rs.getString("MAFAST"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("H" + String.valueOf(countRow));		cell.setValue(rs.getString("nppTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("I" + String.valueOf(countRow));		cell.setValue(rs.getString("TenNGUOIDAIDIEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("J" + String.valueOf(countRow));		cell.setValue(rs.getString("tructhuoc"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("K" + String.valueOf(countRow));		cell.setValue(rs.getString("diachi"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("L" + String.valueOf(countRow));		cell.setValue(rs.getString("diachixHD"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("M" + String.valueOf(countRow));		cell.setValue(rs.getString("MASOTHUE"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("N" + String.valueOf(countRow));		cell.setValue(rs.getString("MAKHO"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("O" + String.valueOf(countRow));		cell.setValue(rs.getString("mANX"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("P" + String.valueOf(countRow));		cell.setValue(rs.getString("FAX"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("Q" + String.valueOf(countRow));		cell.setValue(rs.getString("TENKYHD"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("R" + String.valueOf(countRow));		cell.setValue(rs.getString("KYHIEUHOADON"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("S" + String.valueOf(countRow));		cell.setValue(rs.getString("SOHOADONTU"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("T" + String.valueOf(countRow));		cell.setValue(rs.getString("SOHOADONDEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				
					cell = cells.getCell("U" + String.valueOf(countRow));		cell.setValue(rs.getString("XUATTAIKHO"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("V" + String.valueOf(countRow));		cell.setValue(rs.getString("DIENTHOAI"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("W" + String.valueOf(countRow));		cell.setValue(rs.getString("FAX"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("X" + String.valueOf(countRow));		cell.setValue(rs.getString("EMAIL"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("Y" + String.valueOf(countRow));		cell.setValue(rs.getString("HINHTHUCThanhToan"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("Z" + String.valueOf(countRow));		cell.setValue(rs.getString("NganHang"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("AA" + String.valueOf(countRow));		cell.setValue(rs.getString("SOTAIKHOAN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					
					
					
					++countRow;
				}
				if(rs!=null)rs.close();
				if(db!=null){
					db.shutDown();
				}
				//ReportAPI.setHidden(workbook,28);
		
			}catch(Exception ex){
				
				System.out.println("Errrorr : "+ex.toString());
				throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
			}
		}
	 
	 private String getSearchQuery(HttpServletRequest request)
	 {		
		INhaphanphoiList obj = new NhaphanphoiList();
		
		Utility util = new Utility();
		
		String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
    	if (maFAST == null)
    		maFAST = "";    	
    	obj.setMaFAST(maFAST);
		
		String ten = util.antiSQLInspection(request.getParameter("nppTen"));
    	if ( ten == null)
    		ten = "";
    	obj.setTen(ten);
    	
    	String sodienthoai = util.antiSQLInspection(request.getParameter("DienThoai"));
    	if (sodienthoai == null)
    		sodienthoai = "";    	
    	obj.setSodienthoai(sodienthoai);
    	    	
    	String kvId = util.antiSQLInspection(request.getParameter("kvId"));
    	if (kvId == null)
    		kvId = "";    	
    	obj.setKvId(kvId);
    	
    	String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
    	if (kenhId == null)
    		kenhId = "";    	
    	obj.setKenhId(kenhId);
    	
    	String isKH = util.antiSQLInspection(request.getParameter("isKH"));
    	if (isKH == null)
    		isKH = "";    	
    	obj.setIsKhachhang(isKH);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));   	
    	if (trangthai == null)
    		trangthai = "";    	
    	
    	String diaban = util.antiSQLInspection(request.getParameter("diaban"));   	
    	if (diaban == null)
    		diaban = "";    
    	obj.setDiaban(diaban);
    	
    	String vung = util.antiSQLInspection(request.getParameter("vung"));   	
    	if (vung == null)
    		vung = "";    	
    	obj.setVung(vung);
    	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	
    	
    	obj.setTrangthai(trangthai);
    	String query =  "	select isnull(a.maFast,'')as maFAST,  a.ma as nppMa, '' as KenhBanHang, a.pk_seq as id, a.ten as nppTen, isnull(a.diachi,'') as diachi, isnull(a.dienthoai,'') as dienthoai , isnull(d.ten,'') as khuvuc, a.trangthai, a.ngaytao, "+  
						"			a.ngaysua, b.ten as nguoitao, c.ten as nguoisua ,a.TongThau_FK,isnull((select TEN from NHAPHANPHOI where PK_SEQ=a.TongThau_FK),'')  as TongThau,isnull(sitecode_conv.tennpptn,npptn.ten) as tennpptn," +
						"  			case a.loaiNPP when 0 then N'Chi nhánh cấp 1' when 1 then N'Chi nhánh cấp 2' when 2 then N'Quầy bán buôn' when 3 then N'Quầy Traphaco' when 4 then N'Đối tác' when 5 then N'Chi nhánh đối tác' else N'Khách hàng ETC' end as loaiNPP," +
						"	ISNULL( ( select ten from TINHTHANH where pk_seq = tt.pk_seq ) , '' ) as tinhthanh		"+ 
						"	from nhaphanphoi a    "+
						//"		left join NHAPP_KBH on NHAPP_KBH.NPP_FK=a.PK_SEQ "+
						//"		left join KENHBANHANG kbh on kbh.PK_SEQ=NHAPP_KBH.KBH_FK "+
						"		inner join nhanvien b on b.pk_seq=a.nguoitao  "+
						"		inner join  nhanvien c on c.pk_seq=a.nguoisua   "+
						"		left join  khuvuc d  on a.khuvuc_fk=d.pk_seq " +
						"		left join vung v on v.pk_seq=d.vung_fk "+
						"		left join tinhthanh tt on tt.pk_seq=a.TINHTHANH_FK"+
						"       left join sitecode_conv on sitecode_conv.convsitecode=a.sitecode                                              " +
						"       left join nhaphanphoi npptn on npptn.pk_Seq=a.npptn_fk          "+   
						"	 where 1=1 and a.isKHACHHANG = '" + isKH + "'  ";
    	
    	if (ten.length()>0)
    	{
			query = query + " and ( a.ma like N'%" +  ten + "%' or a.ten like N'%" +  ten + "%') ";				
    	}
    	
    	if (sodienthoai.length()>0)
    	{
			query = query + " and upper(a.dienthoai) like upper('%" + sodienthoai + "%')";
    	}
    	if (kvId.length()>0)
    	{
			query = query + " and d.pk_seq = '" + kvId + "'";
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		
    	}
    	if(kenhId.length()>0)
    	{
    		query+=" and a.pk_seq in (select npp_fk from NHAPP_KBH where kbh_fk='"+kenhId+"'   )";
    	}
    	
    	if(vung.length()>0)
    	{
    		query+= " and v.pk_seq like '%"+vung+"%' ";
    	}
    	if(diaban.length()>0)
    	{
    		query+= " and tt.pk_seq like '%"+diaban+"%' ";
    	}
    	
    	if (maFAST.length()>0)
    	{
			query = query + " and a.maFAST like N'%" + maFAST + "%' ";				
    	}
    	
	    return query;
	}	
	
	 String query = "";
	private void setQuery(IStockintransit obj,HttpServletRequest request)
	{	
		Utility util = new Utility();
		String query =  "select distinct kbh.diengiai as kenh, npp.ma as pk_seq,npp.sitecode,isnull(npp.ten,N'Chưa xác định') as ten,isnull(npp.TENNGUOIDAIDIEN,N'Chưa xác định') AS tennguoidaidien,isnull(npp.diachi,N'Chưa xác định') as diachi,isnull(npp.dienthoai,N'Chưa xác định') as dienthoai ,npp.khosap,case when npp.trangthai='1' then N'Hoạt động' else N'Ngưng hoạt động' end as trangthai, "+ 
				    	" ISNULL(npp.FAX,N'Chưa xác định') AS fax,ISNULL(npp.EMAIL,N'Chưa xác định') AS email, " +
				    	" isnull(npp.masothue,N'Chưa xác đinh') as masothue,isnull(npp.diachixhd,N'Chưa xác định') as diachixhd,isnull(tinhthanh.ten,N'Chưa xác đinh') as tinhthanh, isnull(quanhuyen.ten,N'Chưa xác định') as quanhuyen, "+
				    	" isnull(vung.ten,N'Chưa xác định') as tenvung,isnull(khuvuc.ten,N'Chưa xác đinh') as tenkhuvuc,gsbh.pk_seq as gsid,isnull(gsbh.ten,N'Chưa xác định') as tengs,isnull(gsbh.dienthoai,N'Chưa xác định') as dienthoaigs,isnull(gsbh.diachi,N'Chưa xác định') as diachigs,  "+
				    	" ISNULL(npp.HINHTHUCTHANHTOAN,N'Chưa xác định') AS hinhthucthanhtoan , ISNULL(npp.NGANHANG,N'Chưa xác định') AS nganhang ,ISNULL(npp.SOTAIKHOAN,N'Chưa xác định') AS sotk ,ISNULL(npp.ngaybatdau,N'Chưa xác định') AS ngaybatdau,ISNULL(npp.ngayketthuc,N'Chưa xác định') AS ngayketthuc, " +
				    	" ISNULL(SITECODE_CONV.TENNPPTN,N'Chưa xác định') AS tennpptn,ISNULL(qh.TEN,N'Chưa xác định') as quanly,ISNULL(npp.GHICHU,N'Chưa xác định') AS ghichu,isnull(npp.chietkhau,0) as ChietKhau,b.NgayBatDau as NgaybdQl,b.NgayKetThuc as NgayKtQL " +
				    	" from nhaphanphoi npp "+
				    	" left join  NHAPP_GIAMSATBH b on npp.pk_seq=b.npp_fk "+
				    	" left join giamsatbanhang gsbh on gsbh.pk_Seq=b.gsbh_fk" +
				    	" left join nhapp_kbh npp_kbh on npp_kbh.npp_fk=npp.pk_seq " +
				    	" left join kenhbanhang kbh on kbh.pk_seq=npp_kbh.kbh_fk  "+
				    	" left join khuvuc on khuvuc.pk_seq=npp.khuvuc_fk  "+
				    	" left  join SITECODE_CONV on npp.SITECODE =SITECODE_CONV.CONVSITECODE " +
				    	" left join vung on vung.pk_seq=khuvuc.vung_fk "+
				    	" left join tinhthanh on tinhthanh.pk_Seq=npp.tinhthanh_fk "+
				    	" left join quanhuyen on quanhuyen.pk_Seq=npp.quanhuyen_fk " +
				    	" left join (" +
				    	" select tt.TEN,NPP_FK from NHAPHANPHOI_QUANHUYEN npp_tt " +
				    	" inner join QUANHUYEN tt ON tt.PK_SEQ=npp_tt.QUANHUYEN_FK " +
				    	" ) As qh on qh.NPP_FK=npp.PK_SEQ " +
				    	" where 1=1";
	    	
	    	String ten = util.antiSQLInspection(request.getParameter("nppTen"));
	    	if (ten.length()>0){
	    		//Utility util = new Utility();
				query = query + " and upper(npp.ten) like upper(N'%" + util.replaceAEIOU(ten) + "%')";
				
	    	}
	    	String sodienthoai = util.antiSQLInspection(request.getParameter("DienThoai"));
	    	if (sodienthoai.length()>0){
				query = query + " and upper(npp.dienthoai) like upper('%" + sodienthoai + "%')";
				
	    	}
	    	
	    	String kvId = util.antiSQLInspection(request.getParameter("kvId"));
	    	if (kvId.length()>0){
				query = query + " and npp.khuvuc_fk = '" + kvId + "'";
				
	    	}
	    	
	    	String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));
	    	if(!trangthai.equals("2")){
	    		query = query + " and npp.trangthai = '" + trangthai + "'";
	    		
	    	}
	    	String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
	    	if (kenhId == null)
	    		kenhId = "";    	
	    	if(kenhId.length()>0)
	    	{
	    		query+=" and npp.pk_seq in (select npp_fk from NHAPP_KBH where kbh_fk='"+kenhId+"'   )";
	    	}
	    	obj.setuserTen(util.antiSQLInspection(request.getParameter("userTen")));
	    	System.out.println("1/Xuất excel nhà phân phối :"+query);
	    	
		}	

	boolean kiemtra(String sql)
	{
		dbutils db =new dbutils();
    	ResultSet rs = db.get(sql);
		try {//kiem tra ben san pham
			while(rs.next())
			{ 
				if(rs.getString("num").equals("1"))
			   return true;
			}
		} 
		catch(Exception e) {	e.printStackTrace();}
		return false;
	}

	private String  Delete(String id, INhaphanphoiList obj)
	{
		dbutils db = new dbutils();
		
		String sql="select count(*) as num from donhang where npp_fk='"+id+"'";
		System.out.println("cau lenh xoa: "+ sql);
		
		if(kiemtra(sql))
		{
			obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! ");
		}
		System.out.println("1.1"+sql);
		
		sql=" select count(*) as num from dieuchinhtonkho where npp_fk='"+id+"' ";
		if(kiemtra(sql))
		{
			obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! ");
		}
		System.out.println("1.2"+sql);
		
		sql=" select count(*) as num from DonTraHang where npp_fk='"+id+"' ";
		if(kiemtra(sql))
		{
			obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! ");
		}
		System.out.println("1.3"+sql);
		
		sql=" select count(*) as num from NhapHang where npp_fk='"+id+"' ";
		if(kiemtra(sql))
		{
			obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! ");
		}
		System.out.println("1.4"+sql);
		
		sql=" select count(*) as num from DonDatHang where npp_fk='"+id+"' ";
		if(kiemtra(sql))
		{
			obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! ");
		}
		System.out.println("1.6"+sql);
		try
		{
			db.getConnection().setAutoCommit(false);
			
			sql = "delete from CHITIEU_NHAPP_SEC where NHAPP_FK= '" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
				
			}
			System.out.println("----1---"+sql);
			
			sql = "delete from CHITIEUNPP where NHAPP_FK= '" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
				
			}
			System.out.println("----2---"+sql);
			
			
			sql = "delete from nhapp_kbh where npp_fk='" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}
			System.out.println("1.7"+sql);
			
			sql = "delete from nhapp_nhacc_donvikd where npp_fk='" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql); 
			}
			System.out.println("1.8"+sql);
			
			sql = "delete from nhapp_giamsatbh where npp_fk='" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}
			System.out.println("1.9"+sql);
			
			sql = "delete from nhapp_kho where npp_fk='" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql); 
			}
			System.out.println("1.10"+sql);
			
			

			
			sql = "delete from banggiabanlenpp where npp_fk='" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql); 
			}
			System.out.println("1.12"+sql);
			
			sql = "delete from nhapp_kho where npp_fk='" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}
			System.out.println("1.13"+sql);
			
			
			sql = "delete from nhapp_kho_chitiet where npp_fk='" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}
			System.out.println("1.13"+sql);
			
			sql = "delete from BANGGIAMUANPP_NPP where npp_fk='" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}
			System.out.println("1.14"+sql);
			sql ="delete from nhapp_diaban where npp_fk = "+id+"";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}
			
			sql ="delete from nhaphanphoi_khuvuc where npp_fk = "+id+"";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}
			sql ="delete from nhapp_NhomNPP where npp_fk = "+id+"";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}
			sql ="delete from nhapp_Hethongbanhang where npp_fk = "+id+"";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}
			sql ="delete from NHAPP_GIAMSATBH where npp_fk = "+id+"";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}
			sql ="delete from NHAPP_NHOMKENH where npp_fk = "+id+"";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}
			
			sql ="delete from NHAPP_NHACC_DONVIKD where npp_fk = "+id+"";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}
			
			
			sql ="delete from NHAPP_KBH where npp_fk = "+id+"";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}
			sql ="delete from NHAPHANPHOI_VUNG where npp_fk = "+id+"";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}
			
			
			/*sql = "delete from nhaphanphoi where pk_seq = " + id + "";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+sql);
			}*/
			
			
			sql = "update  nhaphanphoi set TRANGTHAI=0 where pk_seq =  " + id + "";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Lỗi trong quá trình chuyển trạng thái ngưng hoạt động! "+sql);
			}
			
			
			
			System.out.println("1.14"+sql);
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			e.printStackTrace();
			db.update("rollback");
			return "Không thể xóa Nhà phân phối khi đã phát sinh dữ liệu ! "+e.getMessage();
		}
		db.shutDown();
		return "";			
	}
	
	private String getDateTime() 
	{
	    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
	    Date date = new Date();
	    return dateFormat.format(date);	
	}

}
