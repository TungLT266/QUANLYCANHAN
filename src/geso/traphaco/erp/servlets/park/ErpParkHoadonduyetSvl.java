package geso.traphaco.erp.servlets.park;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.park.IErpPark;
import geso.traphaco.erp.beans.park.IErpParkList;
import geso.traphaco.erp.beans.park.imp.ErpPark;
import geso.traphaco.erp.beans.park.imp.ErpParkList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpParkHoadonduyetSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ErpParkHoadonduyetSvl() {
        super();
    }

    int tranghientai=1;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpParkList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();	    
	    Utility util = new Utility();
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    
	    
	    String ServerletName = this.getServletName();
	 // sét query Ở DOGET
	   
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	   
	    obj = new ErpParkList();
	    
	    if(action.equals("chot"))
    	{
	    	String id = util.getId(querystring);  	
			IErpPark pBean = new ErpPark(id);
			pBean.setCtyId((String)session.getAttribute("congtyId"));
			pBean.setUserId(userId);
			pBean.setnppdangnhap(util.getIdNhapp(userId));
    
   
    		obj.setDuyet(1);

    		obj.setCongtyId((String)session.getAttribute("congtyId"));
    		obj.setUserId(userId);
    		GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
    		if(!pBean.Duyet())
       		{
       			obj.setMsg(pBean.getMsg());
       		}
    	   // obj.init("");	  
    	    obj.initDuyetHDNCC("");	
    	 
    		session.setAttribute("obj", obj);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp";
    		response.sendRedirect(nextJSP);
    	
    	}
	    else
		{
		

	    obj.setUserId(userId);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setnppdangnhap(util.getIdNhapp(userId));
	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    System.out.println("loai hang: " + obj.getLoaihang());
		obj.setDuyet(1);
	    //obj.init("");	
	    obj.initDuyetHDNCC("");	
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp";
		response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpParkList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String ServerletName = this.getServletName();
	    
	    String action = request.getParameter("action");
	    
	    
	    if (action == null)
	    {
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpPark pBean = new ErpPark();
	    	pBean.createRs();
	    	session.setAttribute("pBean", pBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyetNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpParkList();
		    	
		    	obj.setDUYET(1);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		     	tranghientai = Integer.parseInt(request.getParameter("nxtApprSplitting"));
		     	
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
			    obj.setnppdangnhap(util.getIdNhapp(userId));
			    
			   this.getSearchQuery(request, obj);
			    
		    	//obj.init("");
			   obj.initDuyetHDNCC("");	
		        //// SET QUERY Ở ĐIỀU KIỆN LỌC ( DOPOST ) + 	if(action.equals("view") || action.equals("next") || action.equals("prev"))
		    	//	LƯU Ý : ĐẶT SAU INIT
				
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);	
		    	obj.setDuyet(1);		    	
		    	session.setAttribute("obj", obj);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp");	
		    }
	    	else if(action.equals("chot"))
	    	{
	    		String id = request.getParameter("parkid");
	        	obj = new ErpParkList();
	        	
				IErpPark pBean = new ErpPark(id);
	    	
	    		if(!pBean.Duyet())
	    		{
	    			obj.setMsg(pBean.getMsg());
	    		}
	    		obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
			    obj.setnppdangnhap(util.getIdNhapp(userId));
			    
	    		this.getSearchQuery(request, obj);		    	
	    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		     	tranghientai = Integer.parseInt(request.getParameter("nxtApprSplitting"));
		    	
			    
		    	//obj.init("");
		     	 obj.initDuyetHDNCC("");	
		    	// GET lại trang
//		    	String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
//		    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);	
		    	obj.setDuyet(1);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp");	
	    	}else if (action.equals("excel"))
		    {
		    	OutputStream out = response.getOutputStream();
	    		obj = new ErpParkList();
		    	this.getSearchQuery(request, obj);
	    		obj.setDuyet(1);
			    String ctyId = (String)session.getAttribute("congtyId");
			    String ctyTen = (String)session.getAttribute("congtyTen");   	
			    obj.setCongtyId(ctyId);
				obj.setUserId(userId);
				obj.initBangKeHoaDon();
		    	
		    	
		    	try {
		    		response.setContentType("application/vnd.ms-excel");
			        response.setHeader("Content-Disposition", "attachment; filename=DanhSachHoaDon.xlsm");
		    		CreateExcel(out, obj.getParkList(), (String) session.getAttribute("userTen")) ;
				} catch (Exception e) {
					e.printStackTrace();
					obj.setMsg("Không thể tạo báo cáo - " + e.getMessage());
				}
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp");	
				return;
		    }
	    	else
	    	{
		    	obj = new ErpParkList();

				obj.setUserId(userId);
				obj.setCongtyId((String)session.getAttribute("congtyId"));
			    obj.setnppdangnhap(util.getIdNhapp(userId));
			    
		    	this.getSearchQuery(request, obj);
		    	System.out.println("aaaaa"+obj.getNgayghinhan());
		    	//System.out.println("cau lenh timkiem: \n" + this.search + "\n-----------------------------------------------");
		    	obj.setDuyet(1);
		    	
		    	
	
		    	System.out.println("loai hang: " + obj.getLoaihang());
		    	//obj.init("");
		    	 obj.initDuyetHDNCC("");	
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp");
	    	}
	    }
	}

private void CreateExcel(OutputStream out, ResultSet rs, String userTen) throws Exception {
		
		try {
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\DanhSachHoaDon.xlsm");

			
			dbutils db = new dbutils();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Font font = new Font();
			
			worksheet.setName("DanhSachHoaDon");
			
			Cells cells = worksheet.getCells();
			
			//dien thong tin 
			cells.setRowHeight(3, 18.0f);
			Cell cell = cells.getCell("B3");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, getDateTime() );
			
			cells.setRowHeight(3, 18.0f);
			cell = cells.getCell("B4");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, userTen  );
			
			ResultSetMetaData rsmd=rs.getMetaData();;
			int socottrongSql=rsmd.getColumnCount();
			System.out.println(" so dong : "+socottrongSql) ;
			
			int countRow = 6;
			int column = 0;			
			try {
				while (rs.next()) {
					cell = null;
					Style style = null;

					for(int i =1;i <=socottrongSql ; i ++)
					{

						cell = cells.getCell(countRow,column + i-1 );
						if(i==11 || i==12 || i==13 ){
							cell.setValue(rs.getDouble(i));
							setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,false);
							style = cell.getStyle();
							style.setNumber(3);
							cell.setStyle(style);
						}else{
							if (i==16) {
								setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT,false);
								
							}
							cell.setValue(rs.getString(i));
							setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false);
						}							
					}
					
					/*for(int i =13;i <=socottrongSql ; i ++)
					{
						cell = cells.getCell(countRow,column + i );
						cell.setValue(rs.getString(i));
						if(i==10){
							setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);
						} else{
							setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false);
						}							
					}*/

					countRow++;
				}
				rs.close();	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//---
			workbook.save(out);
			fstream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}	
	}

	
	private void setCellBorderStyle(Cell cell, short align,boolean kt) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		if(kt)
		{
			Font font2 = new Font(); 
			font2.setName("Calibri");
			font2.setColor(Color.RED);
			style.setFont(font2);
			
		}
		
		style.setColor(Color.WHITE);
		
		cell.setStyle(style);
	}

	
	
	
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("DANH SÁCH HÓA ĐƠN NHÀ CUNG CẤP");
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Ngày: " + this.getDateTime());
	    cell = cells.getCell("A4");
	    cell.setValue("User:  " + UserName);
 
	    cell = cells.getCell("A8");
	    cell.setValue("Số (Park Nr.)");
	    cell = cells.getCell("B8");
	    cell.setValue("Ngày ghi nhận");
	    cell = cells.getCell("C8");
	    cell.setValue("Số hóa đơn");
	    cell = cells.getCell("D8");
	  
	    cell.setValue("Tên nhà cung cấp");
	    cell = cells.getCell("E8");
	    cell.setValue("Số PO");
	    cell = cells.getCell("F8");
	    cell.setValue("Mẫu hoá đơn");
	    
	    cell = cells.getCell("G8");
	    cell.setValue("Số hoá đơn");
	    cell = cells.getCell("H8");
	    cell.setValue("Ký hiệu hoá đơn");
	    cell = cells.getCell("I8");
	    cell.setValue("Ngày hoá đơn");
	    
	    cell = cells.getCell("J8");
	    cell.setValue("Tiền tệ");
	 
 
	    cell = cells.getCell("K8");
	    cell.setValue("Số tiền chưa thuế");
	    
	    cell = cells.getCell("L8");
	    cell.setValue("Thuế giá trị gia tăng");
	    
	    cell = cells.getCell("M8");
	    cell.setValue("Tổng");
	    
	    cell = cells.getCell("N8");
	    cell.setValue("Kho biệt trữ");
	    
	    cell = cells.getCell("O8");
	    cell.setValue("Kho tồn trữ");
	    
	    cell = cells.getCell("P8");
	    cell.setValue("Diễn giải chứng từ");
	    
	    cell = cells.getCell("AI8");
	    cell.setValue("Trạng thái");
	  
	    worksheet.setName("Danh sách hóa đơn");
	}
	
	
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	private void getSearchQuery(HttpServletRequest request, IErpParkList obj)
	{
		
		
		
		Utility util = new Utility();
		String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
		if(ngayghinhan == null)
			ngayghinhan = "";
		obj.setNgayghinhan(ngayghinhan);
		
		String donViThucHienId = util.antiSQLInspection(request.getParameter("donViThucHienId"));
		if(donViThucHienId == null)
			donViThucHienId = "";
		obj.setDonViThucHienId(donViThucHienId);
		
		String denNgay = util.antiSQLInspection(request.getParameter("denNgay"));
		if(denNgay == null)
			denNgay = "";
		obj.setDenNgay(denNgay);
		
		String nhacungcap = util.antiSQLInspection(request.getParameter("nhacungcap"));
		if(nhacungcap == null)
			nhacungcap = "";
		obj.setNcc(nhacungcap);
		
		String loaihang = util.antiSQLInspection(request.getParameter("loaihang"));
		if(loaihang == null)
			loaihang = "";
		obj.setLoaihang(loaihang);
		
		String sopark = util.antiSQLInspection(request.getParameter("sopark"));
		if(sopark == null)
			sopark = "";
		obj.setId(sopark);
		
		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		if(sohoadon == null) sohoadon = ""; else sohoadon = sohoadon.trim();
		obj.setSOHOADON(sohoadon);
		
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null) 
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		
		String nguoiTaoId = util.antiSQLInspection(request.getParameter("nguoiTaoId"));
		if(nguoiTaoId == null) 
			nguoiTaoId = "";
		obj.setNguoiTaoId(nguoiTaoId);
		String sanphamId = util.antiSQLInspection(request.getParameter("sanpham"));
		if(sanphamId == null) 
			sanphamId = "";
		obj.setSanphamId(sanphamId);
		
	
	}
	
	public static void main(String[] args ){
		
		dbutils db = new dbutils();
		String query = "select pk_seq from erp_park";
		ResultSet rs = db.get(query);
		try {
			db.getConnection().setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			if(rs!= null){
				while(rs.next()){
					db.execProceduce2("CapNhatTooltip_HoaDonNCC", new String[] {rs.getString("pk_seq")});
				}
				rs.close();
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			System.out.println("chay xong");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.shutDown();
		}
	}
}