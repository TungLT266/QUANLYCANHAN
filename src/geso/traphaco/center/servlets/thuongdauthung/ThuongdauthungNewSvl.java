package geso.traphaco.center.servlets.thuongdauthung;

import geso.traphaco.center.beans.thuongdauthung.imp.Sanpham;
import geso.traphaco.center.beans.thuongdauthung.ISanpham;
import geso.traphaco.center.beans.thuongdauthung.IThuongdauthung;
import geso.traphaco.center.beans.thuongdauthung.imp.Thuongdauthung;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ThuongdauthungNewSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ThuongdauthungNewSvl()
	{
		super();
	}
	
	private String gettenuser(String userId_)
	{
		dbutils db = new dbutils();
		String sql_getnam = "select ten from nhanvien where pk_seq=" + userId_;
		ResultSet rs_tenuser = db.get(sql_getnam);
		if (rs_tenuser != null)
		{
			try
			{
				while (rs_tenuser.next())
				{
					return rs_tenuser.getString("ten");
				}
			} catch (Exception er)
			{
				return "";
			}
		}
		return "";
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		String id = util.getId(querystring);
		
		IThuongdauthung obj = new Thuongdauthung(id);
		
		String loai = util.antiSQLInspection(request.getParameter("loai"))==null?"0":util.antiSQLInspection( request.getParameter("loai"));
		obj.setLoai(loai);
		
		obj.setUserId(userId);
		
		session.setAttribute("userId", userId);
		String tenuser = gettenuser(userId);
		session.setAttribute("userTen", tenuser);
		
		String action = util.getAction(querystring);
		if (action.equals("display"))
		{
			obj.setDisplay("1");
		}
		session.setAttribute("check", "0");
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/ThuongdauthungUpdate.jsp";// default
		response.sendRedirect(nextJSP);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    HttpSession session = request.getSession();
		
		Utility util = new Utility();
		
		IThuongdauthung obj = new Thuongdauthung();
		
		String id = util.antiSQLInspection(request.getParameter("id"));
		try
		{
			
			obj.setID(Double.parseDouble(id));
			
		} catch (Exception err)
		{
		}
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		obj.setUserId(userId);
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		obj.setDienGiai(diengiai);
		obj.setNguoiSua(userId);
		obj.setNguoiTao(userId);
		obj.setNgayTao(this.getDateTime());
		obj.setNgaySua(this.getDateTime());
		
		String nsp_fk = util.antiSQLInspection(request.getParameter("nsp_fk"));
		obj.setNsp_fk(nsp_fk);
		
		/*String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		obj.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		obj.setDenngay(denngay);*/
		
		String thangdt = util.antiSQLInspection(request.getParameter("thang"));
		obj.setThangdt(thangdt);
		
		String namdt = util.antiSQLInspection(request.getParameter("nam"));
		obj.setNamdt(namdt);
		
		String isSalesIn = util.antiSQLInspection(request.getParameter("isSalesIn") == null ? "0" : request.getParameter("isSalesIn"));
		obj.setIsSalesIn(isSalesIn);
		
		
		String loaict = util.antiSQLInspection(request.getParameter("loaict") == null ? "0" : request.getParameter("loaict"));
		obj.setLoaict(loaict);
		
		//Thưởng đầu cái hay thưởng sản phẩm mới
		String loai = util.antiSQLInspection(request.getParameter("loai") == null ? "0" : request.getParameter("loai"));obj.setLoai(loai);
		
		String loailay = util.antiSQLInspection(request.getParameter("loailay") == null ? "0" : request.getParameter("loailay"));
		obj.setTieuchilay(loailay);
		
		
		String[] sanpham = request.getParameterValues("sanpham");
		obj.setSanpham(sanpham);
		
		
		
		String[] tumuc = request.getParameterValues("tumuc");
		String[] denmuc = request.getParameterValues("denmuc");
		String[] thuongSS = request.getParameterValues("thuongSS");
		String[] thuongSR = request.getParameterValues("thuongSR");
		String[] donvi_thuong = request.getParameterValues("donvi_thuong");
		String[] thuongASM = request.getParameterValues("thuongASM");
		String[] thuongBM = request.getParameterValues("thuongBM");
		
		obj.setTumuc(tumuc);
		obj.setDenmuc(denmuc);
		obj.setThuongSR(thuongSR);
		obj.setThuongSS(thuongSS);
		obj.setThuongASM(thuongASM);
		obj.setThuongBM(thuongBM);
		obj.setDonvi_thuong(donvi_thuong);
		
		String[] spId = request.getParameterValues("spId");
		String[] spMa = request.getParameterValues("spMa");
		String[] spTen = request.getParameterValues("spTen");
		String[] spTrongso = request.getParameterValues("spTrongso");
		
		String spIds = "";
		List<ISanpham> spList = new ArrayList<ISanpham>();
		
		if (spId != null)
		{
			for (int i = 0; i < spId.length; i++)
			{
				if (spId[i].trim().length() > 0)
				{
					ISanpham sp = new Sanpham();
					sp.setId(spId[i]);
					sp.setMa(spMa[i]);
					sp.setTen(spTen[i]);
					sp.setTrongso(spTrongso[i].replaceAll(",", ""));
					spList.add(sp);
					spIds += spId[i] + ",";
				}
			}
			if (spIds.length() > 0)
			{
				spIds = spIds.substring(0, spIds.length() - 1);
			}
		}
		obj.setSpList(spList);
		
		String action = request.getParameter("action");
		if (action.equals("save"))
		{
			if ((obj.getID() == 0 ? obj.CreateUploadLuongCoBan() : obj.UpdateUploadLuongCoBan()))
			{
				session.setAttribute("nam", 0);
				session.setAttribute("thang", 0);
				obj.setLuongkhacRs("");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Center/Thuongdauthung.jsp";
				response.sendRedirect(nextJSP);
				
				System.out.println(obj.getMessage());
			} else
			{
				
				String nextJSP = "/TraphacoHYERP/pages/Center/ThuongdauthungUpdate.jsp";// default
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				System.out.println(obj.getMessage());
			}
		} else if (action.equals("excel"))
		{
			try
			{
				OutputStream out = response.getOutputStream();
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ThuongDauThung.xlsm");
				FileInputStream fstream = new FileInputStream(
				    getServletContext().getInitParameter("path") + "\\ThuongDauThung.xlsm");
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				CreateStaticHeader(workbook, obj);
				obj.setUserId(userId);
				obj.setID(Double.parseDouble(id));
				obj= new Thuongdauthung(id);
				FillData(workbook, obj);
				workbook.save(out);
				fstream.close();
			} catch (Exception ex)
			{
				ex.printStackTrace();
				obj.setMessage("Khong the tao pivot.");
			}
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "";
			nextJSP = "/TraphacoHYERP/pages/Center/ThuongdauthungUpdate.jsp";
			response.sendRedirect(nextJSP);
		} else
		{
			obj.createSpList();
			String nextJSP = "/TraphacoHYERP/pages/Center/ThuongdauthungUpdate.jsp";// default
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
			System.out.println(obj.getMessage());
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		return dateFormat.format(date);
	}
	
	private boolean FillData(Workbook workbook, IThuongdauthung obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();
		ResultSet hdRs = obj.getDataRs();
		
		double DonGia_AVG = 0;
		double total_BVAT = 0;
		double total_AVAT = 0;
		double total_VAT = 0;
		int i = 6;
		int SoTt = 1;
			try
			{
				Cell cell = null;
				
				while (hdRs.next())
				{
					
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(SoTt++);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(hdRs.getString("vTen"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(hdRs.getString("kvTen"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(hdRs.getString("ttTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(hdRs.getString("gsbhMa"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(hdRs.getString("gsbhTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("G" + Integer.toString(i));
					cell.setValue(hdRs.getString("nppMA"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("H" + Integer.toString(i));
					cell.setValue(hdRs.getString("nppTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					
					cell = cells.getCell("I" + Integer.toString(i));
					cell.setValue(hdRs.getString("ddkdMA"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("J" + Integer.toString(i));
					cell.setValue(hdRs.getString("ddkdTEN"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					cell = cells.getCell("K" + Integer.toString(i));
					cell.setValue(hdRs.getDouble("SoLuongTHUNG"));
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					cell = cells.getCell("L" + Integer.toString(i));
					cell.setValue(hdRs.getDouble("SoLuongTHUNG"))  ;
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					
					++i;
				}
				
				if (hdRs != null)
					hdRs.close();
				
				if (db != null)
					db.shutDown();
				
				if (i == 2)
				{
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
				
			} catch (Exception ex)
			{
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		return true;
		
	}
	
	private void CreateStaticHeader(Workbook workbook, IThuongdauthung obj)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		
		Style style;
		Font font = new Font();
		font.setColor(Color.RED);// mau chu
		font.setSize(16);// size chu
		font.setBold(true);
		
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		style = cell.getStyle();
		style.setFont(font);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		
		/*String tieude = "THƯỞNG ĐẦU THÙNG";
		ReportAPI.getCellStyle(cell, Color.RED, true, 14, tieude);
		
		String message = "";
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);*/
		
		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Từ ngày : " + obj.getTungay() + " -"+"Đến ngày : " + obj.getDenngay() + "");
		
		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		
		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  ");
				
	/**/
		
	/*	cell = cells.getCell("A8");
		cell.setValue("STT");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("B8");
		cell.setValue("MIỀN");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("C8");
		cell.setValue("VÙNG");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("D8");
		cell.setValue("TỈNH THÀNH");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("E8");
		cell.setValue("MÃ GIÁM SÁT");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);

		cell = cells.getCell("F8");
		cell.setValue("TÊN GIÁM SÁT");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		
		cell = cells.getCell("G8");
		cell.setValue("MÃ NHÀ PHÂN PHỐI");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);

		cell = cells.getCell("H8");
		cell.setValue("TÊN NHÀ PHÂN PHỐI");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("I8");
		cell.setValue("MÃ NHÂN VIÊN");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);

		cell = cells.getCell("J8");
		cell.setValue("TÊN NHÂN VIÊN");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("K8");
		cell.setValue("SỐ LƯỢNG THÙNG");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);
		
		cell = cells.getCell("L8");
		cell.setValue("THƯỞNG");
		ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 0);*/
		
	}
	
}
