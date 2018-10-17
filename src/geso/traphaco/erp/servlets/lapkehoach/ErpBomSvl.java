package geso.traphaco.erp.servlets.lapkehoach;
import geso.traphaco.erp.beans.lapkehoach.*;
import geso.traphaco.erp.beans.lapkehoach.imp.*;
import geso.dms.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpBomSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpBomSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	String query = "select a.DanhMucVattu_FK from Erp_KichBanSanXuat_CongDoanSanXuat_Giay a"
	    			+ " inner join Erp_KichBanSanXuat_Giay b on b.PK_SEQ = a.KichBanSanXuat_FK"
	    			+ " where b.TrangThai = 1 and a.DanhMucVattu_FK is not null group by a.DanhMucVattu_FK";
	    	String idnot = "";
			ResultSet rs = db.get(query);
			try {
				if(rs != null){
					while(rs.next()){
						idnot += rs.getString("DanhMucVattu_FK") + ",";
					}
					rs.close();
				}
			} catch (Exception e) {}
			
			if(idnot.indexOf(khlId) >= 0){
				msg = "BOM này đang được sử dụng trong kịch bản sản xuất.";
				query = "update ERP_DANHMUCVATTU set trangthai = '0',ishoatdong =0 where pk_seq = " + khlId;
				
			} else {
				query = " delete ERP_DANHMUCVATTU_VATTU_THAYTHE where DANHMUCVT_FK = " + khlId +
						" delete ERP_DANHMUCVATTU_VATTU where DANHMUCVT_FK = " + khlId +
						" delete ERP_DANHMUCVATTU_DINHMUCCHIPHI where DANHMUCVT_FK = " + khlId +
						" delete ERP_DANHMUCVATTU_NHAMAY where DANHMUCVATTU_FK = " + khlId +
						" delete ERP_DANHMUCVATTU_KBSX where DANHMUCVATTU_FK = " + khlId +
						" delete ERP_DANHMUCVATTU where pk_seq = " + khlId;
			}
	    	
	    	if(!db.update(query)) {
	    		msg = "Xãy ra lỗi hệ thống. Vui lòng đăng nhập lại.";
	    	}
	    	db.shutDown();
	    }

	    IErpBomList obj = new ErpBomList();
	    obj.setCtyId(ctyId);
	    obj.setUserId(userId);
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBom.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	   // this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	  //  out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));   
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    IErpBomList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpBom bomBean = new ErpBom();
    		bomBean.setCtyId(ctyId);
    		bomBean.setUserId(userId);
    		
    		bomBean.createRs();
    		

	    	session.setAttribute("bomBean", bomBean);  	
    		session.setAttribute("userId", userId);
    		
    		IErpDanhMucVatTuThanhPhan bomThanhPhan = new ErpDanhMucVatTuThanhPhan();
    		session.setAttribute("bomThanhPhan", bomThanhPhan);
    		
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpBomNew.jsp");
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
    		System.out.println("toi day");
    		obj = (IErpBomList) new ErpBomList();
    		obj.setCtyId((String)session.getAttribute("congtyId"));
    		
	    	String search = getSearchQuery(request, obj);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	/*obj.setUserId(userId);*/
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpBom.jsp");	
	    }
	    else if(action.equals("excel")) 
		{	
	    	obj = (IErpBomList) new ErpBomList();
		    OutputStream out = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=DanhSachBom.xls");
	        
	        Workbook workbook = new Workbook();
	    	
		    CreateStaticHeader(workbook);
		    CreateStaticData(workbook, obj);
		
		    //Saving the Excel file
		    workbook.save(out);
		}	    
	    else
	    {
	    	obj = new ErpBomList();
	    	obj.setCtyId(ctyId);
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpBom.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpBomList obj) 
	{
		Utility util = new Utility();
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
			
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String tenbom = util.antiSQLInspection(request.getParameter("tenbom"));
		if(tenbom == null)
			tenbom = "";
		obj.setTenBOM(tenbom);
		
		String vanbanhuongdan = util.antiSQLInspection(request.getParameter("vanbanhuongdan"));
		if(vanbanhuongdan == null)
			vanbanhuongdan = "";
		obj.setVanBanHuongDan(vanbanhuongdan);
		
		
		String sanpham = util.antiSQLInspection(request.getParameter("sanpham"));
		if(sanpham == null)
			sanpham = "";
		obj.setSanpham(sanpham);

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if(dvkdId == null) dvkdId = "";
		obj.setDvkdId(dvkdId);
		
		String lspId = util.antiSQLInspection(request.getParameter("lspId"));
		if(lspId == null) lspId = "";
		obj.setLspId(lspId);
		
		String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		if(ghichu == null) ghichu = "";
		obj.getGhichu();
		
		String manguyenlieu = util.antiSQLInspection(request.getParameter("manguyenlieu"));
		if(manguyenlieu == null)
			manguyenlieu = "";
		obj.setManguyenlieu(manguyenlieu);
		
		String sql = " select distinct ISNULL(ISHOATDONG,0) AS ISHOATDONG,ISNULL(ISDADUYET,0) AS ISDADUYET, " +
		  "a.pk_seq, isnull(a.tenbom,'') as tenbom, isnull(a.vanbanhuongdan,'') as vanbanhuongdan,isnull( (select isnull(nh.tennhamay,'') from erp_nhamay nh inner join ERP_DANHMUCVATTU_NHAMAY nhdm on nh.pk_seq=nhdm.NHAMAY_FK where nhdm.DANHMUCVATTU_FK=a.PK_SEQ),'') as tennhamay, " +
		  " a.diengiai, isnull( (d.ma + ' - ' + d.ten),'')  as sp, (a.hieuluctu + ' - ' + a.hieulucden) as hieuluc , " +
		  " a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua, " 
		  + " CASE WHEN ISNULL((select sum(a.DMVT_FK) from ( \n "
		  + " SELECT DISTINCT 1 AS DMVT_FK FROM ERP_MUAHANG_SP WHERE DMVT_FK =a.PK_SEQ and MUAHANG_FK IN (SELECT PK_SEQ FROM ERP_MUAHANG WHERE TRANGTHAI not in (2,3,4))	\n "
		  + " UNION ALL	 \n "
		  + " SELECT DISTINCT 1 AS DMVT_FK FROM ERP_MUAHANG_BOM  WHERE  DANHMUCVATTU_FK =a.PK_SEQ AND MUAHANG_FK IN (SELECT PK_SEQ FROM ERP_MUAHANG WHERE TRANGTHAI not in (2,3,4)) \n "
		  + " UNION ALL \n "
		  + " SELECT DISTINCT 1 AS DMVT_FK FROM ERP_LENHSANXUAT_SANPHAM WHERE  DANHMUCVT_FK =a.PK_SEQ AND LENHSANXUAT_FK  IN (SELECT PK_SEQ FROM ERP_LENHSANXUAT_GIAY WHERE TRANGTHAI not in (6,7))	 )a ),0) =0 THEN 0 ELSE 1 END AS CHECKTONTAI \n " +
		  " from ERP_DANHMUCVATTU a " +
		  " inner join NhanVien b on a.nguoitao = b.pk_seq    " +
		  " inner join nhanvien c on a.nguoisua = c.pk_seq " +
		  " left join erp_sanpham d on a.masanpham = d.ma  " +
		  " where a.congty_fk = " + obj.getCtyId() + " ";
		
		
		if(diengiai.length() > 0)
			sql += " and dbo.ftBoDau(a.diengiai) like N'%" + util.replaceAEIOU(diengiai) + "%' ";
		
		if(vanbanhuongdan.length() > 0)
			sql += " and  a.tenbom  like N'%" + vanbanhuongdan  + "%' ";
		
		 
		/*if(trangthai.length() > 0)
		sql += " and a.trangthai = '" + trangthai + "' ";*/
	if(trangthai.length() > 0)
		sql += " and ISNULL(ISHOATDONG,0) = '" + trangthai + "' ";
		
		if(sanpham.length() >0 ){
			sql+=" and ( d.pk_seq= "+sanpham+" )" ;
		}
		
		if(dvkdId.length() >0 ){
			sql+=" and d.dvkd_fk = " + dvkdId ;
		}
		
		if(lspId.length() >0 ){
			sql+=" and d.LOAISANPHAM_FK = " + lspId ;
		}
		if(ghichu.trim().length()>0)
		{
			sql+=" and isnull(a.vanbanhuongdan,'') LIKE N'%" + ghichu+"%'" ;
		}
		
		if(manguyenlieu.trim().length()>0){
			sql+=" and a.pk_seq in (select DANHMUCVT_FK from ERP_DANHMUCVATTU_VATTU where VATTU_FK='"+manguyenlieu+"')";
		}
		
		return sql;
	}
	
	// HÀM XUẤT EXCEL
	
	private void CreateStaticHeader(Workbook workbook) {
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("DANH MỤC VẬT TƯ"); SetStyleForTitleCell(cell);
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Ngày tạo: " + this.getDateTime()); SetStyleForTitleCell(cell);
	 
	    
	    cell = cells.getCell("A8");
	    cell.setValue("Tên Bom"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("B8");
	    cell.setValue("Văn bản hướng dẫn"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("C8");
	    cell.setValue("Mã sản phẩm"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("D8");
	    cell.setValue("Diễn giải"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("E8");
	    cell.setValue("Mã nguyên liệu"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("F8");
	    cell.setValue("Tên nguyên liệu"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("G8");
	    cell.setValue("Số lượng"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("H8");
	    cell.setValue("Đơn vị tính"); SetStyleForHeaderCell(cell);
	    
	    worksheet.setName("DanhMucVatTu");
	}
	
	private void SetStyleForHeaderCell(Cell cell) {
		SetStyleForCell(cell, 1, Color.BLUE, Color.BLUE, Color.WHITE, true);
	}
	
	private void SetStyleForDataCell(Cell cell) {
		SetStyleForCell(cell, 1, Color.GRAY, Color.WHITE, Color.BLACK, false);
	}
	
	
	private void SetStyleForCell(Cell cell, int borderWidth, Color borderColor, Color backgroundColor, Color textColor, boolean bold) {
		Style style = cell.getStyle();
		style.setBorderColor(BorderType.TOP, borderColor);
		style.setBorderColor(BorderType.BOTTOM, borderColor);
		style.setBorderColor(BorderType.LEFT, borderColor);
		style.setBorderColor(BorderType.RIGHT, borderColor);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setColor(backgroundColor);
		Font font = new Font();
		font.setColor(textColor);
		font.setBold(bold);
		style.setFont(font);
		cell.setStyle(style);
	}
	
	private void SetStyleForTitleCell(Cell cell) {
		Style style = cell.getStyle();
		Font font = new Font();
		font.setColor(Color.RED);
		font.setBold(true);
		style.setFont(font);
		cell.setStyle(style);
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private void CreateStaticData(Workbook workbook, IErpBomList obj) {
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    NumberFormat formatter = new DecimalFormat("#,###,###.######");
		dbutils db = new dbutils();
		
		
		
		
		
		String query =
						"	select  VATTU.PK_SEQ,	isnull(VATTU.TENBOM,'') as TENBOM,isnull(VATTU.vanbanhuongdan,'') as VANBANHUONGDAN, \n" +
						"	VATTU.MAVATTU as MaSP,VATTU.DIENGIAI as DienGiai,sp.ma as MaNL,SP.TEN as TenNL,VATTUSP.SOLUONG,DONVIDOLUONG.DIENGIAI as DVT \n " +
						"	from ERP_DANHMUCVATTU VATTU \n "+
						"	inner join ERP_DANHMUCVATTU_VATTU VATTUSP on VATTU.PK_SEQ = VATTUSP.DANHMUCVT_FK \n "+
						"	inner join ERP_SANPHAM SP on VATTUSP.VATTU_FK = SP.PK_SEQ \n "+
						"	inner join DONVIDOLUONG on SP.DVDL_FK = DONVIDOLUONG.PK_SEQ \n "+
						"	WHERE VATTU.TRANGTHAI='1' \n "+
						"	ORDER  by  VATTU.PK_SEQ,VATTU.MAVATTU,sp.ma \n ";
		
		
		ResultSet rs = db.get(query);
		//System.out.println("[ErpBomSvl.CreateStaticData] query = " + query);
		int i = 9;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 45.0f);
				cells.setColumnWidth(3, 60.0f);
				cells.setColumnWidth(4, 30.0f);
				cells.setColumnWidth(5, 30.0f);
				cells.setColumnWidth(6, 30.0f);
				cells.setColumnWidth(7, 25.0f);
				for(int j = 0; j < 11; j++)
					cells.setRowHeight(j, 13.0f);
				
				Cell cell = null;
				while(rs.next())
				{
					cell = cells.getCell("A" + Integer.toString(i)); SetStyleForDataCell(cell);	cell.setValue(rs.getString("TENBOM"));
					cell = cells.getCell("B" + Integer.toString(i)); SetStyleForDataCell(cell); cell.setValue(rs.getString("VANBANHUONGDAN"));
					cell = cells.getCell("C" + Integer.toString(i)); SetStyleForDataCell(cell); cell.setValue(rs.getString("MaSP"));
					cell = cells.getCell("D" + Integer.toString(i)); SetStyleForDataCell(cell);	cell.setValue(rs.getString("DienGiai"));
					cell = cells.getCell("E" + Integer.toString(i)); SetStyleForDataCell(cell); cell.setValue(rs.getString("MaNL"));
					cell = cells.getCell("F" + Integer.toString(i)); SetStyleForDataCell(cell); cell.setValue(rs.getString("TenNL"));
					cell = cells.getCell("G" + Integer.toString(i)); SetStyleForDataCell(cell); cell.setValue(formatter.format(rs.getDouble("SOLUONG"))); 
					cell = cells.getCell("H" + Integer.toString(i)); SetStyleForDataCell(cell); cell.setValue(rs.getString("DVT"));
					i++;
				}
				rs.close();
			}
			catch (Exception e){ e.printStackTrace(); }
		}
	}
}
