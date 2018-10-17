package geso.traphaco.erp.servlets.lenhsanxuat;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lenhsanxuat.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuat.IErpLenhsanxuatList;
import geso.traphaco.erp.beans.lenhsanxuat.imp.ErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuat.imp.ErpLenhsanxuatList;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpLenhsanxuatSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpLenhsanxuatSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpLenhsanxuatList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String lsxId = util.getId(querystring);
	    obj = new ErpLenhsanxuatList();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    
	    if (action.equals("delete"))
	    {	
	    	obj.setMsg(DeleteLsx(lsxId));
	    }
	    else
	    {
	    	if(action.equals("kichhoat"))
	    	{
	    		obj.setMsg(KichHoat(lsxId));
	    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("tensudung", "2");
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuat.jsp";
		response.sendRedirect(nextJSP);
	}

	private String KichHoat(String lsxId) 
	{
		String msg = "";
		
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "select isnull(kichbansanxuat_fk, '-1') as kbsx_fk from ERP_LENHSANXUAT where pk_seq = '" + lsxId + "'";
			ResultSet rs = db.get(query);
			
			String kbsx_fk = "";
			if(rs != null)
			{
				if(rs.next())
				{
					kbsx_fk = rs.getString("kbsx_fk");
				}
				rs.close();
			}
			
			if(kbsx_fk.equals("-1"))
			{
				msg = "Lệnh sản xuất chưa thiết lập kịch bản sản xuất. Vui lòng kiểm tra lại.";
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			query = "update ERP_LENHSANXUAT set trangthai = '1' where pk_seq = '" + lsxId + "'";
			System.out.println("1. cap nhat ERP_LENHSANXUAT: " + query);
			
			if(!db.update(query))
			{
				msg = "1.Không thể kích hoạt lệnh sản xuất. Vui lòng thử lại";
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
		
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
		} 
		catch (Exception e) 
		{
			try
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) {}
			
			msg = "3.Không thể kích hoạt lệnh sản xuất. Vui lòng thử lại " + e.getMessage();
			db.shutDown();
		}

		return msg;
	}

	private String DeleteLsx(String lsxId) 
	{
		String msg = "";
		
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			/*String query = "Update ERP_LENHSANXUATDUKIEN_SP_CHITIET set trangthai = 0 " +
							"where pk_seq in ( select lenhsanxuatdukien_fk from ERP_LENHSANXUAT where pk_seq = '" + lsxId + "'  ) ";
			
			
			if(!db.update(query))
			{
				msg = "1.Không thể xóa lệnh sản xuất " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}*/
			
			String query = "update ERP_LENHSANXUAT set trangthai = '4' where pk_seq = '" + lsxId + "'";
			System.out.println("___Update LSX: " + query);
			
			if(!db.update(query))
			{
				msg = "2.Không thể xóa lệnh sản xuất. Vui lòng thử lại";
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e) 
		{
			msg = "3.Không thể xóa lệnh sản xuất. Vui lòng thử lại";
			db.shutDown();
			
			try
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) {}
		}

		return msg;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpLenhsanxuatList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    OutputStream out = response.getOutputStream();
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    System.out.println("___Action la: " + action);
	    if(action.equals("Tao moi"))
	    {
	    	IErpLenhsanxuat lsxBean = new ErpLenhsanxuat();
	    	lsxBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	
	    	lsxBean.createRs();
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	session.setAttribute("tensudung", "2");
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		System.out.println("toi day");
	    		obj = new ErpLenhsanxuatList();
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpLenhSanXuat.jsp");	
		    }
	    	else
	    	{
	    		String msg = "";
	    		if(action.equals("kichhoat"))
	    		{
	    			String kbsxId = request.getParameter("kbsxId");
	    			String lsxId = request.getParameter("lxsId");
	    			
	    			msg = KichHoatLSX(kbsxId, lsxId, userId);
		      		
	    			obj = new ErpLenhsanxuatList();
	    			obj.setCongtyId((String)session.getAttribute("congtyId"));
	    			
	    			obj.setUserId(userId);
	    		    obj.init("");
	    		    obj.setMsg(msg);
	    		    
	    			session.setAttribute("obj", obj);

	    			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuat.jsp";
	    			response.sendRedirect(nextJSP);
	    			
	    		}
	    		else
	    		{
			    	obj = new ErpLenhsanxuatList();
			    	obj.setCongtyId((String)session.getAttribute("congtyId"));
			    	
			    	if(action.equals("Excel"))
	    			{
	    				try
	    			    {
	    					response.setContentType("application/xlsm");
	    					response.setHeader("Content-Disposition", "attachment; filename=DanhSachLenhSanXuat.xlsm");
	    			        
	    					FileInputStream fstream = null;
	    					Workbook workbook = new Workbook();		
	    					
	    					fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpDSLenhsanxuat.xlsm");

	    					workbook.open(fstream);
	    					workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	    			    	
	    				     CreateStaticHeader(workbook, "");
	    				     CreateStaticData(workbook, getSearchQuery2(request, obj));
	    				
	    				     //Saving the Excel file
	    				     workbook.save(out);
	    				     
	    				     fstream.close();
	    			    }
	    			    catch (Exception ex)
	    			    {
	    			        obj.setMsg("Khong the tao pivot.");
	    			    }
	    			}
			    	
			    	String search = getSearchQuery(request, obj);
			    	obj.init(search);
					obj.setUserId(userId);
					
			    	session.setAttribute("obj", obj);  	
		    		session.setAttribute("userId", userId);
			
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpLenhSanXuat.jsp");
	    		}
	    	}
	    }
	}
	
	private String KichHoatLSX(String kbsxId, String lsxId, String userId) 
	{
		//Lay kich ban
		String msg = "";
		
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query =  "select * from " +
						"(  " +
							"select a.pk_seq as kbsx_fk, c.NHAMAY_FK as nhamayId, '1' as type, " +
								"b.thoigian + ( select isnull(SUM(thoigian), 0) from ERP_ChiPhiThoiGian where nhamay_fk = '100000' )  as tongTG   " +
							"from ERP_KICHBANSANXUAT a inner join ERP_DAYCHUYENSANXUAT b on a.daychuyen_fk = b.pk_seq     " +
								"inner join ERP_CUMSANXUAT c on b.cumsanxuat_fk = c.PK_SEQ    " +
							"where a.pk_seq = '" + kbsxId + "'   " +
						")  " +
						"kichban inner join  " +
						"( " +
							"select NGAYBATDAU, NGAYDUKIENHT, SOLUONG, '1' as type from ERP_LENHSANXUAT where PK_SEQ = '" + lsxId + "' " +
						")  " +
						"lenhsanxuat on kichban.type = lenhsanxuat.type";
			
			System.out.println("___Khoi tao: " + query);
			ResultSet rsKb = db.get(query);
			
			String nhamay_fk = "";
			String ngaydukienHT = "";
			String ngaybatdau = "";
			String soluong = "";
			
			if(rsKb.next())
			{
				nhamay_fk = rsKb.getString("nhamayId");
				ngaydukienHT = rsKb.getString("NGAYDUKIENHT");
				soluong = rsKb.getString("SOLUONG");
				
				ngaybatdau = getNgayBatDau(ngaydukienHT, rsKb.getFloat("tongTG"));
			}
			rsKb.close();
			
			query = "Update ERP_LENHSANXUAT set Kichbansanxuat_fk = '" + kbsxId + "', NgayBatDau = '" + ngaybatdau + "', NgayDuKienHT = '" + ngaydukienHT + "', " +
							"NhaMay_fk = '" + nhamay_fk + "', TrangThai = '1', NgaySua = '" + getDateTime() + "', NguoiSua = '" + userId + "'  " +
					"where pk_seq = '" + lsxId + "' ";
			
			System.out.println("___cap nhat LSX: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				msg = "Không thể cập nhật ERP_LENHSANXUAT: " + query;
				return msg;
			}
			
			query = "insert ERP_LENHSANXUAT_BOM(LENHSANXUAT_FK, SOLUONGCHUAN, CHOPHEPTHAYTHE, VATTU_FK, SOLUONG, VATTUTT_FK, SOLUONGTT, TILE)  " +
					"select '" + lsxId + "', a.SOLUONGCHUAN, a.CHOPHEPTT, b.VATTU_FK, b.SOLUONG * " + soluong + " / a.SOLUONGCHUAN, b.VATTUTT_FK, b.SOLUONGTT, b.TILE   " +
					"from ERP_DANHMUCVATTU a inner join ERP_DANHMUCVATTU_VATTU b on a.PK_SEQ = b.DANHMUCVT_FK  " +
					"where a.PK_SEQ = ( select bom_fk from ERP_KICHBANSANXUAT where pk_seq = '" + kbsxId + "' ) ";
			
			System.out.println("___Chen BOM: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				msg = "Không thể tạo mới ERP_LENHSANXUAT_BOM: " + query;
				return msg;
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return msg;
		} 
		catch (Exception e) 
		{
			msg = "Không thể kích hoạt lệnh sản xuất: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
			return msg;
		}

	}

	private String getSearchQuery(HttpServletRequest request, IErpLenhsanxuatList obj)
	{
		String query = "select a.PK_SEQ ,a.trangthai, a.ngaybatdau, isnull(a.NgayDuKienHT, '') as NgayDuKienHT, sp.pk_seq as spId, sp.TEN as spTen, NV.TEN as nguoitao, " +
						"a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.dondathang_fk, '-1') as dondathang_fk, a.kichbansanxuat_fk, " +
						"( select COUNT(pk_seq) from ERP_KICHBANSANXUAT where sanpham_fk = sp.PK_SEQ and trangthai = '1' ) as sodong  " +
						"from ERP_LENHSANXUAT a  " +
						"inner Join ERP_SanPham sp on a.SANPHAM_FK = sp.PK_SEQ inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
		
		String tungaySX = request.getParameter("tungaySX");
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);
		
		String denngaySX = request.getParameter("denngaySX");
		if(denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);
		
		String tungayDK = request.getParameter("tungayDK");
		if(tungayDK == null)
			tungayDK = "";
		obj.setTungayDk(tungayDK);
		
		String denngayDK = request.getParameter("denngayDK");
		if(denngayDK == null)
			denngayDK = "";
		obj.setDenngayDk(denngayDK);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		if(tungaySX.length() > 0)
			query += " and a.NgayBatDau >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += " and a.NgayBatDau <= '" + denngaySX + "'";
		
		if(tungayDK.length() > 0)
			query += " and a.NgayDuKienHT >= '" + tungayDK + "'";
		
		if(denngayDK.length() > 0)
			query += " and a.NgayDuKienHT <= '" + denngayDK + "'";
		
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getNgayBatDau(String ngayhoanthanh, float sogio) 
	{
		//ngay lam 8h, bat dau tinh tu 8h AM
		int ngay = Math.round( sogio / 8 );
		Calendar c1 = Calendar.getInstance();
		
		String[] arr = ngayhoanthanh.split("-");
		c1.set(Integer.parseInt( arr[0]), Integer.parseInt( arr[1]) - 1, Integer.parseInt( arr[2]) );
		
		c1.add(Calendar.DATE, (-1) * ngay);
		

		Calendar c2 = Calendar.getInstance();
		c2.set(Integer.parseInt( arr[0]), Integer.parseInt( arr[1]) - 1, Integer.parseInt( arr[2]) );
	
		while( c2.getTime().compareTo(c1.getTime()) > 0 )
		{
			//neu la ngay chu nhat thi phai tang 1 len 1 ngay
			if(c2.get(Calendar.DAY_OF_WEEK) == 8 || c2.get(Calendar.DAY_OF_WEEK) == 1)
			{
				c1.add(Calendar.DATE, -1);
			}
			
			c2.add(Calendar.DATE, -1);
		}
		
		String ngaykt = Integer.toString(c1.get(Calendar.DATE));
		if(ngaykt.trim().length() < 2)
			ngaykt = "0" + ngaykt;
		
		String thangkt = Integer.toString(c1.get(Calendar.MONTH) + 1);
		if(thangkt.trim().length() < 2)
			thangkt = "0" + thangkt;
		
		//System.out.println("___Date ngay bat dau: " + c1.get(Calendar.DAY_OF_WEEK) );
		System.out.println("1.Ngay bat dau tinh duoc: " + Integer.toString(c1.get(Calendar.YEAR)) + "-" + thangkt + "-" + ngaykt);
		
		return Integer.toString(c1.get(Calendar.YEAR)) + "-" + thangkt + "-" + ngaykt;
	}
	
	
	
	/***********************EXcel***********************/
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    cells.setRowHeight(0, 20.0f);
	    
	    Cell cell = cells.getCell("A2"); 
	    ReportAPI.getCellStyle(cell,Color.RED, true, 18, "Danh sách lệnh sản xuất");
	   
	    //tieu de
	    cell = cells.getCell("A5");
	    cell.setValue("Mã lệnh");
	    ReportAPI.setCellHeader(cell);
	    
	    cell = cells.getCell("B5");
	    cell.setValue("Ngày bắt đầu");
	    ReportAPI.setCellHeader(cell);
	    
	    cell = cells.getCell("C5");
	    cell.setValue("Ngày dự kiến HT");
	    ReportAPI.setCellHeader(cell);
	    
	    cell = cells.getCell("D5");
	    cell.setValue("Mã sản phẩm");
	    ReportAPI.setCellHeader(cell);
	    
	    cell = cells.getCell("E5");
	    cell.setValue("Tên sản phẩm");
	    ReportAPI.setCellHeader(cell);
	    
	    cell = cells.getCell("F5");
	    cell.setValue("Đơn vị đo lường");
	    ReportAPI.setCellHeader(cell);
	    
	    cell = cells.getCell("G5");
	    cell.setValue("Số lượng");
	    ReportAPI.setCellHeader(cell);
	    
	    
	    //worksheet.setName("Danh sách lệnh sản xuất");
	}
	
	private void CreateStaticData(Workbook workbook, String query) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		System.out.println("Get LSX: " + query);
		int i = 6;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 20.0f);
				cells.setColumnWidth(4, 45.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);
	
				for(int j = 0; j < 6; j++)
					cells.setRowHeight(j, 13.0f);
				
				Cell cell = null;
				while(rs.next())
				{
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(rs.getString("pk_seq"));
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(rs.getString("NgayBatdau"));
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(rs.getString("NgayDuKienHT"));
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(rs.getString("spMa"));
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(rs.getString("spTen"));
					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(rs.getString("dvdlTen"));
					cell = cells.getCell("G" + Integer.toString(i));
					cell.setValue(rs.getDouble("SoLuong"));
					
					i++;
				}
				rs.close();
			}
			catch (SQLException e){ e.printStackTrace(); }
		}
		
	}
	
	private String getSearchQuery2(HttpServletRequest request, IErpLenhsanxuatList obj)
	{
		String query = "select a.PK_SEQ, a.trangthai, isnull(a.ngaybatdau, '') as NgayBatDau, isnull(a.NgayDuKienHT, '') as NgayDuKienHT, a.SoLuong, " +
							"sp.pk_seq as spId, sp.ma as spMa, sp.TEN as spTen, isnull(dvdl.diengiai, '') as dvdlTen " +
						"from ERP_LENHSANXUAT a  " +
							"inner Join ERP_SanPham sp on a.SANPHAM_FK = sp.PK_SEQ left join DonViDoLuong dvdl on sp.dvdl_fk = dvdl.PK_SEQ   " +
						"where a.trangthai = '0' ";
		
		String tungaySX = request.getParameter("tungaySX");
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);
		
		String denngaySX = request.getParameter("denngaySX");
		if(denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);
		
		String tungayDK = request.getParameter("tungayDK");
		if(tungayDK == null)
			tungayDK = "";
		obj.setTungayDk(tungayDK);
		
		String denngayDK = request.getParameter("denngayDK");
		if(denngayDK == null)
			denngayDK = "";
		obj.setDenngayDk(denngayDK);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		if(tungaySX.length() > 0)
			query += " and a.NgayBatDau >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += " and a.NgayBatDau <= '" + denngaySX + "'";
		
		if(tungayDK.length() > 0)
			query += " and a.NgayDuKienHT >= '" + tungayDK + "'";
		
		if(denngayDK.length() > 0)
			query += " and a.NgayDuKienHT <= '" + denngayDK + "'";
		
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		return query;
	}
	
	
	
	
}
