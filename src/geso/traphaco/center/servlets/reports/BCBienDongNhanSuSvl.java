package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

@WebServlet("/BCBienDongNhanSuSvl")
public class BCBienDongNhanSuSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BCBienDongNhanSuSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		obj.setuserId(userId);
		obj.init();
		
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/BCBienDongNhanSu.jsp";
		response.sendRedirect(nextJSP);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		if (userId == null)
			userId = "";
		
		obj.setuserId(userId);
		
		Utility util = new Utility();
		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);
		
		obj.setuserTen(userTen);
		String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);
		
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		
		if (dvkdId == null)
			dvkdId = "";
		obj.setdvkdId(dvkdId);
		
		obj.setMonth(util.antiSQLInspection(request.getParameter("tuthang")));
		obj.setYear(util.antiSQLInspection(request.getParameter("tunam")));
		
		String vungId = util.antiSQLInspection(request.getParameter("vungId"));
		if (vungId == null)
			vungId = "";
		
		obj.setvungId(vungId);
		
		String khuvucId = util.antiSQLInspection(request.getParameter("khuvucId"));
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);
		
		String dvdlId = util.antiSQLInspection(request.getParameter("dvdlId"));
		if (dvdlId == null)
			dvdlId = "";
		obj.setdvdlId(dvdlId);
		
		String tuthang = request.getParameter("tuthang");
		if (tuthang == null)
			tuthang = "";
		
		obj.setFromMonth((tuthang.length() > 1 ? tuthang : "0" + tuthang));
		System.out.println("Tu thang: " + tuthang);
		
		String denthang = request.getParameter("denthang");
		if (denthang == null)
			denthang = "";
		obj.setToMonth((denthang.length() > 1 ? denthang : "0" + denthang));
		System.out.println("Den thang: " + denthang);
		
		String tunam = request.getParameter("tunam");
		if (tunam == null)
			tunam = "";
		obj.setFromYear(tunam);
		
		String dennam = request.getParameter("dennam");
		if (dennam == null)
			dennam = "";
		obj.setToYear(dennam);
		System.out.println("Den thang: " + denthang);
		
		String sql = "";
		
		String action = util.antiSQLInspection(request.getParameter("action"));
		
		if (action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCBienDongNhanSu_" + util.setTieuDe(obj) + ".xlsm");
				OutputStream out = response.getOutputStream();
				String query = setQuery(sql, obj, userId);
				CreatePivotTable(out, obj, query);
				
			} catch (Exception ex)
			{
				obj.setMsg(ex.getMessage());
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Center/BCBienDongNhanSu.jsp";
				response.sendRedirect(nextJSP);
			}
		} else
		{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/BCBienDongNhanSu.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	private String setQuery(String sql, IStockintransit obj, String userId)
	{
		geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
		
		String tungay = obj.getYear() + "-" + (obj.getMonth().length() > 1 ? obj.getMonth() : "0" + obj.getMonth()) + "-" + "01";
		
		String denngay = obj.getYear() + "-" + (obj.getMonth().length() > 1 ? obj.getMonth() : "0" + obj.getMonth()) + "-" + "31";
		
		
		String query=
		"		declare @TuThang varchar(10),@DenThang varchar(10),@SoThang int,@ThoiGian varchar(10),@i int \n"+
		"		SET @TuThang='"+tungay+"' \n"+
		"		SET @DenThang='"+tungay+"' \n"+
		"		set @SoThang=(SELECT DATEDIFF(month,@TuThang,@DenThang)) \n"+
		"		set @i=0 \n"+
		"		while(@i<=@SoThang) \n"+
		"		BEGIN  \n"+
		"			set @ThoiGian=(select convert(varchar(7),DATEADD(month,@i,@TuThang),20) ) \n"+
		"			select kbh.TEN as Kenh,v.TEN as Vung,kv.TEN as KhuVuc,tt.TEN as Tinh,qh.TEN as QuanHuyen, \n"+
		"			npp.MA as nppMa,npp.TEN as nppTen,nv.soNhanVien,nv.SoNvMoi,nv.soNvNghi,ThoiGian \n"+
		"			from \n"+
		"			(	\n"+
		"				select \n"+
		"					( \n"+
		"						select COUNT(*) \n"+
		"						from DAIDIENKINHDOANH  \n"+
		"						where  NPP_FK=ddkd.NPP_FK \n"+
		"						and (  substring(NGAYKETTHUCHD,0,7)>substring(@ThoiGian,0,7) or NGAYKETTHUCHD ='') \n"+
		"					)as soNhanVien, \n"+
		"					( \n"+
		"						select COUNT(*) \n"+
		"						from DAIDIENKINHDOANH  \n"+
		"						where  NPP_FK=ddkd.NPP_FK \n"+
		"						and NGAYKYHD LIKE  @ThoiGian+'%' and NGAYKETTHUCHD  > @ThoiGian+'-31' \n"+
		"					)as SoNvMoi, \n"+
		"					( \n"+
		"						select COUNT(*) \n"+
		"						from DAIDIENKINHDOANH  \n"+
		"						where NPP_FK=ddkd.NPP_FK \n"+
		"						and NGAYKETTHUCHD LIKE  @ThoiGian+'%' \n"+
		"					)as soNvNghi,NPP_FK,@ThoiGian as ThoiGian \n"+
		"				from DAIDIENKINHDOANH ddkd \n"+
		"				group by NPP_FK \n"+
		"			)as nv "+
		"			inner join NHAPHANPHOI npp on npp.PK_SEQ=nv.NPP_FK \n"+
		"			left join NHAPP_KBH on NHAPP_KBH.NPP_FK=npp.PK_SEQ \n"+
		"			left join KENHBANHANG kbh on kbh.PK_SEQ=NHAPP_KBH.KBH_FK  \n"+
		"			left join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK \n"+
		"			left join VUNG  v on v.PK_SEQ=kv.VUNG_FK \n"+
		"			left join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK \n"+
		"			left join QUANHUYEN qh on qh.PK_SEQ=npp.QUANHUYEN_FK "+
		"			 where 1=1 "+
		"		 set @i=@i+1 "+
		"		 END ";
		return query;
	}
	
	private void CreatePivotTable(OutputStream out, IStockintransit obj, String query) throws Exception
	{
		try
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\BCBienDongNhanSu.xlsm";
			FileInputStream fstream = new FileInputStream(chuoi);
			Workbook workbook = new Workbook();
			
			workbook.open(fstream);
			
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			CreateStaticHeader(workbook, obj);
			
			FillData(workbook, obj.getFieldShow(), query);
			
			workbook.save(out);
			
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) throws Exception
	{
		try
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
			
			ReportAPI.getCellStyle(cell, Color.RED, true, 14, "BÁO CÁO BIẾN ĐỘNG NHÂN SỰ");
			cells.setRowHeight(3, 18.0f);
			cell = cells.getCell("A3");
			
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Từ ngày : " + obj.gettungay() + "");
			
			cells.setRowHeight(3, 18.0f);
			cell = cells.getCell("C3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Đến ngày : " + obj.getdenngay() + "");
			
			cells.setRowHeight(4, 18.0f);
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + obj.getDateTime());
			
			cells.setRowHeight(5, 18.0f);
			cell = cells.getCell("A5");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
			
			cell = cells.getCell("DA1");cell.setValue("KenhBanHang");
			cell = cells.getCell("DB1");cell.setValue("Vung");
			cell = cells.getCell("DC1");cell.setValue("KhuVuc");
			cell = cells.getCell("DD1");cell.setValue("TinhThanh");
			cell = cells.getCell("DE1");cell.setValue("QuanHuyen");
			cell = cells.getCell("DF1");cell.setValue("MaCN/DT");
			cell = cells.getCell("DG1");cell.setValue("ChiNhanh/DoiTac");
			cell = cells.getCell("DH1");cell.setValue("SoNhanVien");
			cell = cells.getCell("DI1");cell.setValue("SoNhanVienMoi");
			cell = cells.getCell("DJ1");cell.setValue("SoNhanVienNghi");
			cell = cells.getCell("DK1");cell.setValue("ThoiGian");;
			
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Khong the tao duoc Header cho bao cao...");
		}
	}
	
	private void FillData(Workbook workbook, String[] fieldShow, String query) throws Exception
	{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			int index = 2;
			Cell cell = null;
			rs=db.get(query);
			System.out.println("[Query]"+query);
			while (rs.next())
			{
				cell = cells.getCell("DA" + Integer.toString(index));cell.setValue(rs.getString("Kenh"));
				cell = cells.getCell("DB" + Integer.toString(index));cell.setValue(rs.getString("Vung"));
				cell = cells.getCell("DC" + Integer.toString(index));cell.setValue(rs.getString("KhuVuc"));
				cell = cells.getCell("DD" + Integer.toString(index));cell.setValue(rs.getString("Tinh"));
				cell = cells.getCell("DE" + Integer.toString(index));cell.setValue(rs.getString("QuanHuyen"));
				cell = cells.getCell("DF" + Integer.toString(index));cell.setValue(rs.getString("nppMa"));
				cell = cells.getCell("DG" + Integer.toString(index));cell.setValue(rs.getString("nppTen"));
				cell = cells.getCell("DH" + Integer.toString(index));cell.setValue(rs.getInt("soNhanVien"));
				cell = cells.getCell("DI" + Integer.toString(index));cell.setValue(rs.getInt("sonvMoi"));
				cell = cells.getCell("DJ" + Integer.toString(index));cell.setValue(rs.getInt("soNvNghi"));
				cell = cells.getCell("DK" + Integer.toString(index));cell.setValue(rs.getString("ThoiGian"));
				index++;
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
			
		}
		
	}
}
