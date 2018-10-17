package geso.traphaco.center.servlets.asm;

import geso.traphaco.center.beans.asm.IAsm;
import geso.traphaco.center.beans.asm.IAsmList;
import geso.traphaco.center.beans.asm.imp.Asm;
import geso.traphaco.center.beans.asm.imp.AsmList;
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

public class ASMSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ASMSvl()
	{
		super();
	}
	String query = "";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();
		IAsmList obj = new AsmList();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		out.println(userId);
		
		if (userId.length() == 0)
			userId = request.getParameter("userId");
		
		String action = util.getAction(querystring);
		out.println(action);
		
		if (action.equals("delete"))
		{
			String bmId = request.getParameter("delete");
			obj.Delete(bmId);
		}
		
		obj.init();
		
		session.setAttribute("obj", obj);
		
		String nextJSP = "/TraphacoHYERP/pages/Center/ASM.jsp";
		response.sendRedirect(nextJSP);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();
		IAsmList obj = new AsmList();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		String querystring = request.getQueryString();
		
		String userId = util.getUserId(querystring);
		
		if (userId.length() == 0)
			userId = request.getParameter("userId");
		
		String userTen = util.antiSQLInspection(request.getParameter("userTen"));
		
		String action = util.antiSQLInspection(request.getParameter("action"));
		
		System.out.println(action);
		
		if (action.equals("new"))
		{
			IAsm asmBean = new Asm();
			
			if (userId.length() == 0)
				userId = request.getParameter("userId");
			
			asmBean.init_New();
			
			session.setAttribute("asmBean", asmBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Center/ASMNew.jsp";
			
			response.sendRedirect(nextJSP);
			
			return;
		}
		
		if (action.equals("excel"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DANHSACHASM.xlsm");
				OutputStream out = response.getOutputStream();
				IStockintransit obj1 = new Stockintransit();
				obj1.setuserId(userId);
				obj1.setuserTen(userTen);
				setQuery(obj1, request);
				ExportToExcel(out, obj1);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		String ten = util.antiSQLInspection(request.getParameter("asmTen"));
		if (ten == null)
			ten = "";
		obj.setTen(ten);
		
		String dienthoai = util.antiSQLInspection(request.getParameter("DienThoai"));
		if (dienthoai == null)
			dienthoai = "";
		obj.setDienthoai(dienthoai);
		
		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		obj.setKbhId(kbhId);
		
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		obj.setDvkdId(dvkdId);
		
		String kvId = util.antiSQLInspection(request.getParameter("kvId"));
		if (kvId == null)
			kvId = "";
		obj.setKvId(kvId);
		
		String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));
		if (trangthai == null)
			trangthai = "2";
		
		obj.setTrangthai(trangthai);
		
		
		String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
    	if (maFAST == null)
    		maFAST = "";    	
    	obj.setMaFAST(maFAST);
		
		
		userId = util.antiSQLInspection(request.getParameter("userId"));
		if (userId == null)
			userId = "";
		
		obj.init();
		
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		
		String nextJSP = "/TraphacoHYERP/pages/Center/ASM.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private void ExportToExcel(OutputStream out, IStockintransit obj) throws Exception
	{
		try
		{
			
			String chuoi = getServletContext().getInitParameter("path") + "\\DANHSACHASM.xlsm";
			
			FileInputStream fstream = new FileInputStream(chuoi);
			
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			CreateHeader(workbook, obj);
			FillData(workbook, obj);
			
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			throw new Exception(ex.getMessage());
		}
	}
	private void CreateHeader(Workbook workbook, IStockintransit obj) throws Exception
	{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16, "DANH SÁCH ASM");
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : " + obj.getDateTime());
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Người tạo : " + obj.getuserTen());
			
			cell = cells.getCell("AA1");
			cell.setValue("MA");
			cell = cells.getCell("AB1");
			cell.setValue("TENASM");
			cell = cells.getCell("AC1");
			cell.setValue("DIENTHOAI");
			cell = cells.getCell("AD1");
			cell.setValue("EMAIL");
			cell = cells.getCell("AE1");
			cell.setValue("TRANGTHAI");
			cell = cells.getCell("AF1");
			cell.setValue("NGAYTAO");
			cell = cells.getCell("AG1");
			cell.setValue("NGUOITAO");
			cell = cells.getCell("AH1");
			cell.setValue("NGAYSUA");
			cell = cells.getCell("AI1");
			cell.setValue("NGUOISUA");
			cell = cells.getCell("AJ1");
			cell.setValue("TENNCC");
			cell = cells.getCell("AK1");
			cell.setValue("TENKBH");
			cell = cells.getCell("AL1");
			cell.setValue("DIACHI");
			cell = cells.getCell("AM1");
			cell.setValue("TENKV");
			cell = cells.getCell("AN1");
			cell.setValue("TENDVKD");
			
			cell = cells.getCell("AO1");
			cell.setValue("MACONGTY");
			cell = cells.getCell("AP1");
			cell.setValue("VITRI");
			cell = cells.getCell("AQ1");
			cell.setValue("VUNGTT");
			cell = cells.getCell("AR1");
			cell.setValue("SOTAIKHOAN");
			cell = cells.getCell("AS1");
			cell.setValue("SONAMLAMVIEC");
			cell = cells.getCell("AT1");
			cell.setValue("SODIENTHOAICONGTY");
			cell = cells.getCell("AU1");
			cell.setValue("DAKYHOPDONG");
			cell = cells.getCell("AV1");
			cell.setValue("LOAIHOPDONG");
			cell = cells.getCell("AW1");
			cell.setValue("NGAYKYHOPDONG");
			cell = cells.getCell("AX1");
			cell.setValue("NGAYVAOCONGTY");
			cell = cells.getCell("AY1");
			cell.setValue("NGAYKETTHUCHOPDONG");
			cell = cells.getCell("AZ1");
			cell.setValue("GHICHU");
			cell = cells.getCell("BA1");
			cell.setValue("NgayBdQuanLyVung");
			cell = cells.getCell("BB1");
			cell.setValue("NgayKtQuanLyVung");
			
		} catch (Exception ex)
		{
			System.out.println(ex.toString());
			throw new Exception("Khong the tao duoc Header cho bao cao.!!!");
		}
	}
	private void FillData(Workbook workbook, IStockintransit obj) throws Exception
	{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setGridlinesVisible(false);
			Cells cells = worksheet.getCells();
			dbutils db = new dbutils();
			ResultSet rs = db.get(getQuery());
			Cell cell = null;
			int countRow = 2;
			while (rs.next())
			{
				cell = cells.getCell("AA" + String.valueOf(countRow));
				cell.setValue(rs.getString("ASMID"));
				cell = cells.getCell("AB" + String.valueOf(countRow));
				cell.setValue(rs.getString("TENASM"));
				cell = cells.getCell("AC" + String.valueOf(countRow));
				cell.setValue(rs.getString("DIENTHOAI"));
				cell = cells.getCell("AD" + String.valueOf(countRow));
				cell.setValue(rs.getString("EMAIL"));
				cell = cells.getCell("AE" + String.valueOf(countRow));
				cell.setValue(rs.getString("TRANGTHAI"));
				cell = cells.getCell("AF" + String.valueOf(countRow));
				cell.setValue(rs.getString("NGAYTAO"));
				cell = cells.getCell("AG" + String.valueOf(countRow));
				cell.setValue(rs.getString("NGUOITAO"));
				cell = cells.getCell("AH" + String.valueOf(countRow));
				cell.setValue(rs.getString("NGAYSUA"));
				cell = cells.getCell("AI" + String.valueOf(countRow));
				cell.setValue(rs.getString("NGUOISUA"));
				cell = cells.getCell("AJ" + String.valueOf(countRow));
				cell.setValue(rs.getString("TENNCC"));
				cell = cells.getCell("AK" + String.valueOf(countRow));
				cell.setValue(rs.getString("TENKBH"));
				cell = cells.getCell("AL" + String.valueOf(countRow));
				cell.setValue(rs.getString("DIACHI"));
				cell = cells.getCell("AM" + String.valueOf(countRow));
				cell.setValue(rs.getString("TENKV"));
				cell = cells.getCell("AN" + String.valueOf(countRow));
				cell.setValue(rs.getString("TENDVKD"));
				
				cell = cells.getCell("AO" + String.valueOf(countRow));
				cell.setValue(rs.getString("ASMID"));
				cell = cells.getCell("AP" + String.valueOf(countRow));
				cell.setValue(rs.getString("VITRI"));
				cell = cells.getCell("AQ" + String.valueOf(countRow));
				cell.setValue(rs.getString("VUNGTT"));
				cell = cells.getCell("AR" + String.valueOf(countRow));
				cell.setValue(rs.getString("SOTAIKHOAN"));
				cell = cells.getCell("AS" + String.valueOf(countRow));
				cell.setValue(rs.getString("SONAMLAMVIEC"));
				cell = cells.getCell("AT" + String.valueOf(countRow));
				cell.setValue(rs.getString("SODTCONGTY"));
				cell = cells.getCell("AU" + String.valueOf(countRow));
				cell.setValue(rs.getString("DAKYHD"));
				cell = cells.getCell("AV" + String.valueOf(countRow));
				cell.setValue(rs.getString("LOAIHD"));
				cell = cells.getCell("AW" + String.valueOf(countRow));
				cell.setValue(rs.getString("NGAYKYHD"));
				cell = cells.getCell("AX" + String.valueOf(countRow));
				cell.setValue(rs.getString("NGAYVAOCONGTY"));
				cell = cells.getCell("AY" + String.valueOf(countRow));
				cell.setValue(rs.getString("NGAYKETTHUCHD"));
				cell = cells.getCell("AZ" + String.valueOf(countRow));
				cell.setValue(rs.getString("GHICHU"));
				cell = cells.getCell("BA" + String.valueOf(countRow));
				cell.setValue(rs.getString("NgayBatDau"));
				cell = cells.getCell("BB" + String.valueOf(countRow));
				cell.setValue(rs.getString("NgayKetThuc"));
				
				++countRow;
			}
			if (rs != null)
				rs.close();
			if (db != null)
			{
				db.shutDown();
			}
			ReportAPI.setHidden(workbook, 26);
			
		} catch (Exception ex)
		{
			
			System.out.println("Errrorr : " + ex.toString());
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	private void setQuery(IStockintransit obj, HttpServletRequest request)
	{
		Utility util = new Utility();
		String asmTen = util.antiSQLInspection(request.getParameter("asmTen"));
		if (asmTen == null)
			asmTen = "";
		String DienThoai = util.antiSQLInspection(request.getParameter("DienThoai"));
		if (DienThoai == null)
			DienThoai = "";
		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		String kvId = util.antiSQLInspection(request.getParameter("kvId"));
		if (kvId == null)
			kvId = "";
		String TrangThai = util.antiSQLInspection(request.getParameter("TrangThai"));
		if (TrangThai == null)
			TrangThai = "";
		String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
    	if (maFAST == null)
    		maFAST = "";    	
		
		query=	
				"		SELECT  isnull(asm.maFast,'') as maFast , asm.PK_SEQ AS ASMID , asm.vitri, asm.vungtt, asm.sotaikhoan, asm.sonamlamviec, asm.sodtcongty, asm.dakyhd, asm.loaihd, asm.ngaykyhd, asm.ngayvaocongty, asm.ngayketthuchd, asm.ghichu, '7'+cast(asm.PK_SEQ as nvarchar(10)) AS PK_SEQ, asm.TEN AS TENASM, ISNULL(asm.DIENTHOAI,'N/A') AS DIENTHOAI, ISNULL(asm.EMAIL,'N/A') AS EMAIL, asm.NGAYTAO AS NGAYTAO, asm.NGAYSUA AS NGAYSUA, ISNULL(asm.DIACHI,'N/A') AS DIACHI ,nt.TEN AS NGUOITAO, ns.TEN AS NGUOISUA, ncc.TEN AS TENNCC, isnull( kv.TEN,'Chua Xac Dinh') AS TENKV,ASM_KV.NGAYBATDAU,ASM_KV.NGAYKETTHUC, dvkd.DIENGIAI AS TENDVKD, "+  
				"		CASE WHEN asm.TRANGTHAI = 1 THEN N'Hoạt động'  ELSE N'Không hoạt động'  END AS TRANGTHAI "+  
				"	FROM ASM asm  "+
				"		INNER JOIN NHANVIEN nt ON asm.NGUOITAO=nt.PK_SEQ  "+  
				"		INNER JOIN NHANVIEN ns ON asm.NGUOISUA=ns.PK_SEQ  "+
				"		INNER JOIN NHACUNGCAP ncc ON asm.NCC_FK=ncc.PK_SEQ  "+
				"		LEFT JOIN ASM_KHUVUC ASM_KV ON ASM_KV.ASM_FK=ASM.PK_SEQ "+
				"		LEFT JOIN KHUVUC kv ON ASM_KV.KHUVUC_FK=kv.PK_SEQ "+ 
				"		INNER JOIN DONVIKINHDOANH dvkd ON asm.DVKD_FK=dvkd.PK_SEQ WHERE 1=1" ;
		if (asmTen.length() > 0)
			query += " AND asm.TEN = '" + asmTen + "'";
		if (DienThoai.length() > 0)
			query += " AND asm.DIENTHOAI = '" + DienThoai + "'";
		/*if (kbhId.length() > 0)
			query += " AND asm.KBH_FK = '" + kbhId + "'";*/
		if (kvId.length() > 0)
			query += " AND asm.KHUVUC_FK = '" + kvId + "'";
		if (dvkdId.length() > 0)
			query += " AND asm.DVKD_FK = '" + dvkdId + "'";
		if (!TrangThai.equals("2"))
			query += " AND asm.TRANGTHAI = '" + TrangThai + "'";
		if(maFAST.length()>0)
    	{
    		query+= " and asm.maFAST like '%"+maFAST+"%' ";
    	}
		System.out.println("1/Câu lệnh excel: " + query);
	}
	private String getQuery()
	{
		return query;
	}
}
