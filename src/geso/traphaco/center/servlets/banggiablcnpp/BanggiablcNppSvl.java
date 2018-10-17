package geso.traphaco.center.servlets.banggiablcnpp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.banggiablcnpp.IBanggiablcNpp;
import geso.traphaco.center.beans.banggiablcnpp.IBanggiablcNppList;
import geso.traphaco.center.beans.banggiablcnpp.imp.BanggiablcNpp;
import geso.traphaco.center.beans.banggiablcnpp.imp.BanggiablcNppList;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
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

public class BanggiablcNppSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	public BanggiablcNppSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		System.out.println("User  : " + userId);

		if (userId.length() == 0)
			userId = (String) session.getAttribute("userId");

		String action = util.getAction(querystring);
		out.println(action);

		String bgId = util.getId(querystring);

		IBanggiablcNppList obj = new BanggiablcNppList();

		if (action.equals("delete"))
		{
			obj.setMsg(Delete(bgId,userId));
			obj.setUserId(userId);
			obj.init("");
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Center/BanggiablcNpp.jsp";
			response.sendRedirect(nextJSP);
		} 
		else
		{
			obj.setUserId(userId);
			obj.init("");
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Center/BanggiablcNpp.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		IBanggiablcNppList obj;
		obj = new BanggiablcNppList();
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}
		String bgTen = util.antiSQLInspection(request.getParameter("bgTen"));
		if (bgTen == null)
			bgTen = "";
		obj.setTen(bgTen);

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		obj.setDvkdId(dvkdId);

		String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
		if (kenhId == null)
			kenhId = "";
		obj.setKenhId(kenhId);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		if (action.equals("new"))
		{
			IBanggiablcNpp bgBean = (IBanggiablcNpp) new BanggiablcNpp();
			bgBean.setUserId(userId);
			bgBean.createRS();
			
			session.setAttribute("bgBean", bgBean);
			String nextJSP = "/TraphacoHYERP/pages/Center/BanggiablcNppNew.jsp";
			response.sendRedirect(nextJSP);
		} 
		else if (action.equals("search"))
		{
			String search = getSearchQuery(request, obj);
			obj.setUserId(userId);
			obj.init(search);
			session.setAttribute("obj", obj);
			response.sendRedirect("/TraphacoHYERP/pages/Center/BanggiablcNpp.jsp");
		} 
		else if (action.equals("toExcel"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BangGiaBanChoKhachHang.xlsm");
				OutputStream out = response.getOutputStream();

				ExportToExcel(out, obj);
				String nextJSP = "/TraphacoHYERP/pages/Center/BanggiablcNpp.jsp";
				response.sendRedirect(nextJSP);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}


	private String getSearchQuery(HttpServletRequest request, IBanggiablcNppList obj)
	{
		String query = "select a.pk_seq as id, a.ten as ten, b.donvikinhdoanh as dvkd, a.ngaybatdau, a.trangthai, d.ten as nguoitao, a.ngaytao as ngaytao, a.ngaysua as ngaysua, e.ten as nguoisua, bg.TEN AS BANGGIA " +
						"from BANGGIABANLENPP a inner join BANGGIABANLECHUAN bg on bg.PK_SEQ = a.BANGGIABANLECHUAN_FK " +
						"inner join donvikinhdoanh b on a.dvkd_fk = b.pk_seq " +
						"inner join nhanvien d on a.nguoitao = d.pk_seq " +
						"inner join nhanvien e on a.nguoisua = e.pk_seq  where 1 = 1 ";

		Utility util = new Utility();
		if (obj.getTen().length() > 0)
		{
			query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(obj.getTen()) + "%')";
		}

		if (obj.getDvkdId().length() > 0)
		{
			query = query + " and b.pk_seq = '" + obj.getDvkdId() + "'";
		}

		if (obj.getKenhId().length() > 0)
		{
			query = query + " and c.pk_seq = '" + obj.getKenhId() + "'";
		}

		if (obj.getTrangthai().length() > 0)
		{
			query = query + " and a.trangthai = '" + obj.getTrangthai() + "'";
		}
		
		System.out.println("\nSearch Query: "+query);
		return query;
	}

	private String Delete(String id,String userId)
	{
		dbutils db = new dbutils();
		try
		{
			String 
			query = 
				"	 SELECT COUNT(*) AS SoDong "+    
				"	 FROM DONDATHANG DH "+ 
				"	 WHERE EXISTS "+
				"	 ( "+
				"		SELECT * FROM "+
				"		BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
				"		B ON A.PK_SEQ=B.BANGGIAMUANPP_FK  "+
				"		WHERE  B.NPP_FK=DH.NPP_FK "+
				"		AND A.KENH_FK=DH.KBH_FK AND A.DVKD_FK=DH.DVKD_FK "+
				"		AND A.TUNGAY<=DH.NGAYDAT AND DH.TRANGTHAI!=6 "+
				"		AND A.PK_SEQ='"+id+"' "+
				"	 ) " ;
			System.out.println("[DONDATHANG]" + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			if (rs != null)
				rs.close();
			if (sodong > 0)
			{
				return "Bảng giá đã được sử dụng trong đơn đặt hàng,vui lòng kiểm tra lại !";
			}
			query = 
			" SELECT COUNT(*) AS SoDong "+    
			"  FROM DONHANG DH "+
			" WHERE EXISTS "+
			"  ( "+
			"	SELECT * FROM  "+
			"	BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
			"	B ON A.PK_SEQ=B.BANGGIAMUANPP_FK "+
			"	WHERE  B.NPP_FK=DH.NPP_FK "+
			"	AND A.KENH_FK=DH.KBH_FK "+
			"	AND A.TUNGAY<=DH.NGAYNHAP AND DH.TRANGTHAI!=2 "+
			"	AND A.PK_SEQ='"+id+"' "+
			"  ) ";
			
			rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			if (rs != null)
				rs.close();
			if (sodong > 0)
			{
				return "Bảng giá đã được sử dụng trong đơn hàng ,vui lòng kiểm tra lại !";
			}
			
			db.getConnection().setAutoCommit(false);
		
			query=
			"  INSERT INTO BANGGIA_LOG(NGUOISUA,BG_FK,SANPHAM_FK,GIA) "+
			"  SELECT '"+userId+"',BGMUANPP_FK,SANPHAM_FK,GIAMUANPP "+
			" FROM BGMUANPP_SANPHAM "+
			" WHERE BGMUANPP_FK='"+id+"' ";
			if (!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
			}
			query = "delete from bgmuanpp_sanpham where bgmuanpp_fk='" + id + "'";
			if (!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
			}
			query = "delete from BANGGIAMUANPP_NPP where BANGGIAMUANPP_FK='" + id + "'";
			if (!db.update(query))
			{
				System.out.print(query);
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
			}
			query = "delete from banggiamuanpp where pk_seq = '" + id + "'";
			if (!db.update(query))
			{
				System.out.print(query);
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
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

	private void ExportToExcel(OutputStream out, IBanggiablcNppList obj) throws Exception
	{
		try
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\BangGia(TT).xlsm";
			FileInputStream fstream = new FileInputStream(chuoi);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
		    workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		    Worksheets worksheets = workbook.getWorksheets();
		    
		    Worksheet worksheet = worksheets.getSheet("BangGia");		
			CreateHeader(workbook, obj);
			FillData(workbook, obj);
			
			worksheet = worksheets.getSheet("NhaPhanPhoiApDung");
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
	
	private boolean CreatePivot_NhaPhanPhoi(Worksheet worksheet, IBanggiablcNppList obj)throws Exception 
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
	
	private void CreateHeader_NhaPhanPhoi(Worksheet worksheet, IBanggiablcNppList obj) throws Exception
	{
		try
		{
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16, "DANH SÁCH NHÀ PHÂN PHỐI ĐƯỢC ÁP DỤNG BẢNG GIÁ");

			cell = cells.getCell("A3");ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : " + getDateTime()  );
			cell = cells.getCell("A4");ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Người tạo : " +getDateTime()  );

			cell = cells.getCell("EA1");cell.setValue("DonViKinhDoanh");
			cell = cells.getCell("EB1");cell.setValue("KenhBanHang");
			cell = cells.getCell("EC1");cell.setValue("TenBangGia");
			cell = cells.getCell("ED1");cell.setValue("ThoiGianApDung");
			cell = cells.getCell("EE1");cell.setValue("Vung");
			cell = cells.getCell("EF1");cell.setValue("KhuVuc");
			cell = cells.getCell("EG1");cell.setValue("MaNhaPhanPhoi");
			cell = cells.getCell("EH1");cell.setValue("TenNhaPhanPhoi");
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Khong the tao duoc Header cho bao cao.!!!");
		}
	}

	private void CreateHeader(Workbook workbook, IBanggiablcNppList obj) throws Exception
	{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16, "BẢNG GIÁ SẢN PHẨM ");

			cell = cells.getCell("A3");ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : " + getDateTime() );
			cell = cells.getCell("A4");ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Người tạo : " +getDateTime() );

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

	private void FillData(Workbook workbook, IBanggiablcNppList obj) throws Exception
	{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setGridlinesVisible(false);
			Cells cells = worksheet.getCells();
			dbutils db = new dbutils();

			String query =	 "SELECT DVKD.DONVIKINHDOANH, '' as KENHBANHANG, BG.TEN AS BGTEN,BG.NGAYBATDAU as TUNGAY, NGH.TEN AS NGANHHANG, CL.TEN AS CHUNGLOAI,  "+
							 "	NH.TEN AS NHANHANG,SP.MA AS SPMA ,SP.TEN  AS SPTEN,  "+
							 "	QC.SOLUONG1 ,QC.SOLUONG2, BGSP.DONGIA AS GIA   "+
							 "FROM BANGGIABANLENPP BG   "+
							 "	INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = BG.DVKD_FK   "+
							 "	INNER JOIN BANGGIABANLENPP_SANPHAM BGSP ON BGSP.BANGGIABLNPP_FK =BG.PK_SEQ  "+
							 "	INNER JOIN SANPHAM SP ON SP.PK_SEQ =BGSP.SANPHAM_FK  "+
							 "	LEFT JOIN  QUYCACH QC ON QC.DVDL1_FK =SP.DVDL_FK AND QC.DVDL2_FK=100018 AND QC.SANPHAM_FK = SP.PK_SEQ  "+
							 "	LEFT JOIN  NHANHANG NH ON NH.PK_SEQ =SP.NHANHANG_FK  "+
							 "	LEFT JOIN  CHUNGLOAI CL ON CL.PK_SEQ =SP.CHUNGLOAI_FK  "+
							 "	LEFT JOIN  NGANHHANG NGH ON NGH.PK_SEQ = SP.NGANHHANG_FK  "+
							 "WHERE 1=1 and SP.LOAISANPHAM_FK = '10045' ";
			
			if(obj.getDvkdId() .length()>0  )
			{
				query += " and bg.dvkd_fk = '" + obj.getDvkd() + "' "; 
			}
			if(obj.getTungay().length()>0)
			{
				query += " and bg.NGAYBATDAU >= '"+obj.getTungay()+"' ";
			}
			if(obj.getTen().length()>0)
			{
				query += " and bg.ten like N'%" + obj.getTen() + "%' ";
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
				
				cell = cells.getCell("EJ" + String.valueOf(countRow));cell.setValue(quycach*rs.getDouble("Gia"));
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
	
	private boolean FillData_NhaPhanPhoi(Worksheet worksheet, IBanggiablcNppList obj)throws Exception
	{
		try
		{
			worksheet.setGridlinesVisible(false);
			Cells cells = worksheet.getCells();
			dbutils db = new dbutils();

			String query =   "SELECT  DVKD.DONVIKINHDOANH, '' AS KENHBANHANG, V.TEN AS VUNG, KV.TEN AS KHUVUC, BG.TEN AS BGTEN, BG.NGAYBATDAU as TUNGAY, NPP.MA AS NPPMA, NPP.TEN AS NPPTEN  "+
							 "FROM BANGGIABANLENPP BG  "+
							 "	INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ =BG.DVKD_FK  "+
							 "	INNER JOIN BANGGIABANLENPP_NPP BGNPP ON BG.PK_SEQ = BGNPP.BANGGIABLNPP_FK  "+
							 "	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = BGNPP.NPP_FK  "+
							 "	LEFT JOIN KHUVUC KV ON KV.PK_SEQ =NPP.KHUVUC_FK  "+
							 "	LEFT JOIN VUNG V ON V.PK_SEQ =KV.VUNG_FK  "+
							 "WHERE 1=1          ";

			if(obj.getDvkdId() .length()>0  )
			{
				query += " and bg.dvkd_fk ='" + obj.getDvkd() + "' "; 
			}
			if(obj.getTungay().length()>0)
			{
				query += " and bg.NGAYBATDAU >= '" + obj.getTungay() + "' ";
			}
			if(obj.getTen().length()>0)
			{
				query += " and bg.ten like N'%" + obj.getTen() + "%' ";
			}	
			
			System.out.println("::: LAY NHA PHAN PHOI AP DUNG: " + query);
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
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
		return true;
	}
	
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		return dateFormat.format(date);
	}

}

