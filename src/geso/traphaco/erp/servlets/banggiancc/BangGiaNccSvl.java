package geso.traphaco.erp.servlets.banggiancc;

import geso.traphaco.center.beans.banggiamuanpp.IBanggiamuanppList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.banggiancc.IBangGiaNcc;
import geso.traphaco.erp.beans.banggiancc.IBangGiaNccList;
import geso.traphaco.erp.beans.banggiancc.imp.BangGiaNcc;
import geso.traphaco.erp.beans.banggiancc.imp.BangGiaNccList;
import geso.traphaco.erp.beans.thutien.IErpThutienCKList;

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

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BangGiaNccSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public BangGiaNccSvl()
	{
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
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IBangGiaNccList obj = new BangGiaNccList();
	    obj.setUserId(userId);

	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    setParameters(request, obj);
	    
	    if(action.trim().equals("delete"))
	    {
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    	
	    	msg = Delete(khlId,userId);
	    	
	    	obj.init("");
	  	    obj.setMsg(msg);
	  	    session.setAttribute("obj", obj);
	  	    
	  	    String nextJSP = "/TraphacoHYERP/pages/Erp/BangGiaNcc.jsp";
	  		response.sendRedirect(nextJSP);
	    }
	    else if(action.trim().equals("duyet")){
	    	
	    	
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    	msg = obj.Duyet(khlId);
	    	
	    	obj.init("");
		    obj.setMsg(msg);
		    session.setAttribute("obj", obj);
		    String nextJSP = "/TraphacoHYERP/pages/Erp/BangGiaNcc.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else if(action.trim().equals("boduyet")){
	    	
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    	
	    	
	    	msg = obj.BoDuyet(khlId);
	    	
	    	obj.init("");
	  	    obj.setMsg(msg);
	  	    session.setAttribute("obj", obj);
	  	    
	  	    String nextJSP = "/TraphacoHYERP/pages/Erp/BangGiaNcc.jsp";
	  		response.sendRedirect(nextJSP);
	    }
	    else{
	    	
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    	
	    	obj.init("");
	  	    obj.setMsg(msg);
	  	    session.setAttribute("obj", obj);
	  	    
	  	    String nextJSP = "/TraphacoHYERP/pages/Erp/BangGiaNcc.jsp";
	  		response.sendRedirect(nextJSP);
	    }

	  
	}

	private String Delete(String id,String userId)
	{
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			String daduyet = "";
			String query = "select daduyet from ERP_BANGGIAMUANCC where pk_seq = " + id;
			ResultSet rs = db.get(query);
			if(rs!= null)
			{
				rs.next();
				daduyet = rs.getString("daduyet")==null?"":rs.getString("daduyet");
			}
			
			if(!daduyet.trim().equals("1"))
			{
				query = "delete from ERP_BANGGIAMUANCC_sanpham_log where banggiamua_fk='" + id + "'";
				if (!db.update(query))
				{
					db.getConnection().rollback();
					return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
				}
				query = "delete from ERP_BANGGIAMUANCC_sanpham where banggiamua_fk='" + id + "'";
				if (!db.update(query))
				{
					db.getConnection().rollback();
					return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
				}
				query = "delete from ERP_BANGGIAMUANCC_NCC where banggia_fk='" + id + "'";
				if (!db.update(query))
				{
					System.out.print(query);
					db.getConnection().rollback();
					return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
				}
				query = "delete from ERP_BANGGIAMUANCC where pk_seq='" + id + "'";
				if (!db.update(query))
				{
					System.out.print(query);
					db.getConnection().rollback();
					return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			db.update("rollback");
			return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IBangGiaNccList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IBangGiaNcc khl = new BangGiaNcc();
    		khl.setUserId(userId);
    		khl.createRs();
	    	session.setAttribute("csxBean", khl);  	
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/BangGiaNccNew.jsp");
	    }
	    else if (action.equals("toExcel"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BangGiaMuaNCC.xlsm");
				OutputStream out = response.getOutputStream();

				obj = new BangGiaNccList();
			    obj.setUserId(userId);
		    	getSearchQuery(request, obj);
		    	
				ExportToExcel(out, obj);
				String nextJSP = "/TraphacoHYERP/pages/Center/BangGiaNcc.jsp";
				response.sendRedirect(nextJSP);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	    else
	    {
	    	obj = new BangGiaNccList();
		    obj.setUserId(userId);

	    	//String search = getSearchQuery(request, obj);
	    	setParameters(request,obj);
	    	obj.setUserId(userId);
	    	//obj.init(search);
	    	obj.init("");
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    
	    	
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/BangGiaNcc.jsp");	
	    }
	    
	    
	    
	}
	
	private void ExportToExcel(OutputStream out, IBangGiaNccList obj) throws Exception
	{
		try
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\BangGiaNCC.xlsm";
			FileInputStream fstream = new FileInputStream(chuoi);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
		    workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		    Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet("BangGia");	
		    
			CreateHeader(workbook, obj);
			FillData(workbook, obj);
			
			worksheet = worksheets.getSheet("NhaCungCapApDung");
			CreatePivot_NhaPhanPhoi(worksheet,obj);
			
			workbook.save(out);
			fstream.close();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	
	
	private boolean CreatePivot_NhaPhanPhoi(Worksheet worksheet, IBangGiaNccList obj)throws Exception 
	{
		boolean isFillData = false;
		CreateHeader_NhaPhanPhoi(worksheet, obj);
		isFillData = FillData_NhaPhanPhoi(worksheet, obj);
		if (!isFillData)
		{
			return false;
		}
		return true;
	}
	
	private void CreateHeader_NhaPhanPhoi(Worksheet worksheet, IBangGiaNccList obj) throws Exception
	{
		try
		{
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16, "DANH SÁCH NHÀ CUNG CẤP ĐƯỢC ÁP DỤNG BẢNG GIÁ");

			cell = cells.getCell("A3");ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : " + getDateTime()  );
			cell = cells.getCell("A4");ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Người tạo : " +getDateTime()  );

			cell = cells.getCell("EA1");cell.setValue("DonViKinhDoanh");
			cell = cells.getCell("EB1");cell.setValue("KenhBanHang");
			cell = cells.getCell("EC1");cell.setValue("TenBangGia");
			cell = cells.getCell("ED1");cell.setValue("ThoiGianApDung");
			cell = cells.getCell("EE1");cell.setValue("Vung");
			cell = cells.getCell("EF1");cell.setValue("KhuVuc");
			cell = cells.getCell("EG1");cell.setValue("MaNhaCungCap");
			cell = cells.getCell("EH1");cell.setValue("TenNhaCungCap");
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Khong the tao duoc Header cho bao cao.!!!");
		}
	}

	private void CreateHeader(Workbook workbook, IBangGiaNccList obj) throws Exception
	{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16, "BẢNG GIÁ SẢN PHẨM ");

			cell = cells.getCell("A3");ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : " + getDateTime()  );
			cell = cells.getCell("A4");ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Người tạo : " +getDateTime()  );

			cell = cells.getCell("EA1");cell.setValue("DonViKinhDoanh");
			cell = cells.getCell("EB1");cell.setValue("KenhBanHang");
			cell = cells.getCell("EC1");cell.setValue("TenBangGia");
			cell = cells.getCell("ED1");cell.setValue("ThoiGianApDung");
			cell = cells.getCell("EE1");cell.setValue("NganhHang");
			cell = cells.getCell("EF1");cell.setValue("NhanHang");
			cell = cells.getCell("EG1");cell.setValue("ChungLoai");
			cell = cells.getCell("EH1");cell.setValue("MaSanPham");
			cell = cells.getCell("EI1");cell.setValue("TenSanPham");
			cell = cells.getCell("EJ1");cell.setValue("GiaMua");
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Khong the tao duoc Header cho bao cao.!!!");
		}
	}

	private void FillData(Workbook workbook, IBangGiaNccList obj) throws Exception
	{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setGridlinesVisible(false);
			Cells cells = worksheet.getCells();
			dbutils db = new dbutils();

			String query =   "SELECT DVKD.DONVIKINHDOANH, '' as KENHBANHANG, BG.TEN AS BGTEN, BG.TUNGAY, NGH.TEN AS NGANHHANG, CL.TEN AS CHUNGLOAI, NH.TEN AS NHANHANG, SP.MA AS SPMA, SP.TEN  AS SPTEN,  "+
							 "	QC.SOLUONG1 ,QC.SOLUONG2, BGSP.GIAMUA AS GIA   "+
							 "FROM ERP_BANGGIAMUANCC BG   "+
							 "	INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = BG.DVKD_FK   "+
							 "	INNER JOIN ERP_BANGGIAMUANCC_SANPHAM BGSP ON BGSP.BANGGIAMUA_FK = BG.PK_SEQ  "+
							 "	INNER JOIN SANPHAM SP ON SP.PK_SEQ =BGSP.SANPHAM_FK  "+
							 "	LEFT  JOIN QUYCACH QC ON QC.DVDL1_FK =SP.DVDL_FK AND QC.DVDL2_FK=100018 AND QC.SANPHAM_FK = SP.PK_SEQ  "+
							 "	LEFT JOIN NHANHANG NH ON NH.PK_SEQ =SP.NHANHANG_FK  "+
							 "	LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ =SP.CHUNGLOAI_FK  "+
							 "	LEFT JOIN NGANHHANG NGH ON NGH.PK_SEQ = SP.NGANHHANG_FK  "+
							 "WHERE 1=1  ";
			
			if(obj.getDvkdId() .length()>0  )
			{
				query += " and bg.dvkd_fk ='"+obj.getDvkd()+"' "; 
			}
			
			System.out.println("::: LAY BC: " + query);
			ResultSet rs = db.get(query);
			Cell cell = null;
			int countRow = 2;
			while (rs.next())
			{				
				cell = cells.getCell("EA" + String.valueOf(countRow));cell.setValue(rs.getString("DonViKinhDoanh"));
				cell = cells.getCell("EB" + String.valueOf(countRow));cell.setValue(rs.getString("KenhBanHang"));
				cell = cells.getCell("EC" + String.valueOf(countRow));cell.setValue(rs.getString("BGTEN"));
				cell = cells.getCell("ED" + String.valueOf(countRow));cell.setValue(rs.getString("TuNgay"));
				cell = cells.getCell("EE" + String.valueOf(countRow));cell.setValue(rs.getString("NganhHang"));
				cell = cells.getCell("EF" + String.valueOf(countRow));cell.setValue(rs.getString("NhanHang"));
				cell = cells.getCell("EG" + String.valueOf(countRow));cell.setValue(rs.getString("ChungLoai"));
				cell = cells.getCell("EH" + String.valueOf(countRow));cell.setValue(rs.getString("SPMA"));
				cell = cells.getCell("EI" + String.valueOf(countRow));cell.setValue(rs.getString("SPTEN"));
				
				int soluong1 =rs.getInt("soluong1");
				int soluong2 =rs.getInt("soluong2");
				if(soluong1<=0)soluong1=1;
				if(soluong2<=0)soluong2=1;
				int quycach = soluong1/soluong2;
				
				cell = cells.getCell("EJ" + String.valueOf(countRow)); cell.setValue(quycach*rs.getDouble("GIA"));
				++countRow;
			}
			if (rs != null)
				rs.close();
			if (db != null)
			{
				db.shutDown();
			}
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	
	private boolean FillData_NhaPhanPhoi(Worksheet worksheet, IBangGiaNccList obj)throws Exception
	{
		try
		{
			worksheet.setGridlinesVisible(false);
			Cells cells = worksheet.getCells();
			dbutils db = new dbutils();

			String query =   "SELECT  DVKD.DONVIKINHDOANH, '' AS KENHBANHANG, '' AS VUNG, '' AS KHUVUC, BG.TEN AS BGTEN, BG.TUNGAY, NPP.MA AS NPPMA, NPP.TEN AS NPPTEN  "+
							 "FROM ERP_BANGGIAMUANCC BG  "+
							 "	INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ =BG.DVKD_FK  "+
							 "	INNER JOIN ERP_BANGGIAMUANCC_NCC BGNPP ON BG.PK_SEQ = BGNPP.BANGGIA_FK  "+
							 "	INNER JOIN ERP_NHACUNGCAP NPP ON NPP.PK_SEQ = BGNPP.NCC_FK  "+
							 "WHERE 1 = 1  ";

			if(obj.getDvkdId().length()>0  )
			{
				query += " and bg.dvkd_fk ='" + obj.getDvkd() + "' "; 
			}
			
			System.out.println("::: LAY NHA CUNG CAP AP DUNG: " + query);
			ResultSet rs = db.get(query);
			Cell cell = null;
			int countRow = 2;
			while (rs.next())
			{				
				cell = cells.getCell("EA" + String.valueOf(countRow));cell.setValue(rs.getString("DonViKinhDoanh"));
				cell = cells.getCell("EB" + String.valueOf(countRow));cell.setValue(rs.getString("KenhBanHang"));
				cell = cells.getCell("EC" + String.valueOf(countRow));cell.setValue(rs.getString("BGTEN"));
				cell = cells.getCell("ED" + String.valueOf(countRow));cell.setValue(rs.getString("TUNGAY"));
				cell = cells.getCell("EE" + String.valueOf(countRow));cell.setValue(rs.getString("VUNG"));
				cell = cells.getCell("EF" + String.valueOf(countRow));cell.setValue(rs.getString("KHUVUC"));
				cell = cells.getCell("EG" + String.valueOf(countRow));cell.setValue(rs.getString("NPPMA"));
				cell = cells.getCell("EH" + String.valueOf(countRow));cell.setValue(rs.getString("NPPTEN"));
				++countRow;
			}
			if (rs != null)
				rs.close();
			if (db != null)
			{
				db.shutDown();
			}
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
		return true;
	}
	
	public String getSearchQuery(HttpServletRequest request, IBangGiaNccList obj) 
	{
		Utility util= new Utility();
		
		if(request != null)
		{
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		  	if ( nppId == null)
		  		nppId = "";
		  	obj.setNppId(nppId);
			
			String tenbg = request.getParameter("bgTen");
			if(tenbg == null)
				tenbg = "";
			obj.setMa(tenbg);
			
			String nccId = request.getParameter("nccId");
			if(nccId == null)
				nccId = "";
			obj.setNccId(nccId);
			
			String dvkdId = request.getParameter("dvkdId");
			if(dvkdId == null)
				dvkdId = "";
			obj.setDvkdId(dvkdId);
			
			String kenhId = request.getParameter("kenhId");
			if(kenhId == null)
				kenhId = "";
			obj.setKenhId(kenhId);
			
			String trangthai = request.getParameter("trangthai");
			if(trangthai == null)
				trangthai = "";
			obj.setTrangthai(trangthai);
			
			String diengiai = request.getParameter("diengiai");
			if(diengiai == null)
				diengiai = "";
			obj.setDiengiai(diengiai);
		}
				
		String sql = 
			  "\n select a.pk_seq, a.ten, a.trangthai , d.ten as nguoitao, a.ngaytao as ngaytao,  " +
			  " \n	e.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd, c.DIENGIAI as kenh, c.pk_seq as nhomkenhId," +
			  "\n 	a.TrangThai, a.TuNgay, ISNULL(a.DADUYET,0) AS DADUYET " +
			  "\n from ERP_BANGGIAMUANCC a, donvikinhdoanh b, NHOMKENH c, nhanvien d, nhanvien e   " +
			  "\n where a.dvkd_fk = b.pk_seq and a.NHOMKENH_FK = c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq   ";
		if(obj.getMa().length() > 0)
			sql += "\n and  a.ten like N'%" + obj.getMa().trim() + "%' ";
		
		if(obj.getNccId().length() > 0)
			sql += "\n and a.ncc_fk = '" + obj.getNccId().trim() + "' ";
		
		if(obj.getDvkdId().length() > 0)
			sql += "\n and a.dvkd_fk = '" + obj.getDvkdId().trim() + "' ";
		
		if(obj.getKenhId().length() > 0)
			sql += "\n and a.NHOMKENH_FK = '" + obj.getKenhId().trim() + "' ";
		
		if(obj.getTrangthai().length() > 0)
			sql += "\n and a.DADUYET = '" + obj.getTrangthai().trim() + "' ";
		
		
		System.out.println(" cau tim kiem: \n " + sql);
		return sql;
	}


	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		return dateFormat.format(date);
	}
	
	
	
	
	private void setParameters(HttpServletRequest request, IBangGiaNccList obj)
	{
		Utility util = new Utility();
		if(request != null)
		{
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		  	if ( nppId == null)
		  		nppId = "";
		  	obj.setNppId(nppId);
			
			String tenbg = request.getParameter("bgTen");
			if(tenbg == null)
				tenbg = "";
			obj.setMa(tenbg);
			
			String nccId = request.getParameter("nccId");
			if(nccId == null)
				nccId = "";
			obj.setNccId(nccId);
			
			String dvkdId = request.getParameter("dvkdId");
			if(dvkdId == null)
				dvkdId = "";
			obj.setDvkdId(dvkdId);
			
			String kenhId = request.getParameter("kenhId");
			if(kenhId == null)
				kenhId = "";
			obj.setKenhId(kenhId);
			
			String trangthai = request.getParameter("trangthai");
			if(trangthai == null)
				trangthai = "";
			obj.setTrangthai(trangthai);
			
			String diengiai = request.getParameter("diengiai");
			if(diengiai == null)
				diengiai = "";
			obj.setDiengiai(diengiai);
		}
	}
	


	
}
