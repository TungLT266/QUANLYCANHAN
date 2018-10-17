package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.Router.IDRouter;
import geso.traphaco.center.beans.Router.imp.Router;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
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

public class Routereport extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	Utility util = new Utility();

	public Routereport()
	{
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		session.setAttribute("userId", userId);
		session.setAttribute("tungay", "");
		session.setAttribute("denngay", "");
		session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);

		IDRouter obj = new Router();
		obj.setUserId(userId);
		obj.setStatus("1");
		obj.init();

		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/RouteReport.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		IDRouter obj = new Router();
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));

		String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
		if (kenhId == null)
			kenhId = "";
		obj.setKenhId(kenhId);

		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);

		String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));

		if (ddkdId == null)
			ddkdId = "";
		obj.setddkdId(ddkdId);

		String tuyenId = util.antiSQLInspection(request.getParameter("tuyenId"));
		if (tuyenId == null)
			tuyenId = "";
		obj.settuyenId(tuyenId);
		
		String userId = (String) session.getAttribute("userId");
		
		obj.setUserId(userId);

		obj.init();
		String action = request.getParameter("action");

		if (action.equals("export"))
		{
			OutputStream out = response.getOutputStream();
			String userTen = (String) session.getAttribute("userTen");
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=RouteReport "+util.setTieuDe(obj)+".xlsm");
			
			CreatePivotTable(out, response, request, obj, userId, userTen);
			// userTen, nppId, tuyenId, ddkdId, kenhId, userId );

		} else if (action.equals("exportmcp"))
		{
			XuatFileExcelSR(response, nppId, ddkdId,obj);
		} else
		{

			String khuvucId = util.antiSQLInspection(request.getParameter("khuvucId"));
			if (khuvucId.trim().length() > 0)
				obj.setkhuvucId(khuvucId);
			String status = util.antiSQLInspection(request.getParameter("status"));
			obj.setStatus(status);
			obj.createNPP();
			// System.out.println("status : "+status);
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Center/RouteReport.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, IDRouter obj,
		String userId, String userTen)throws IOException
	{
		Workbook workbook = new Workbook();
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		String sql = "select pk_seq, ten,MaNhanVien  from daidienkinhdoanh a inner join  daidienkinhdoanh_npp b on b.ddkd_fk=a.pk_seq  where b.npp_fk=" + obj.getnppId() + " and a.trangthai=1";

		if (obj.getddkdId().length() > 0)
		{
			sql = sql + " and a.pk_seq=" + obj.getddkdId();

		}
		// System.out.println("lay ddkd : "+sql);
		dbutils db = new dbutils();
		ResultSet rs = db.get(sql);
		if (rs != null)
		{
			try
			{
				int i = 0;
				while (rs.next())
				{
					Worksheets worksheets = workbook.getWorksheets();

					Worksheet worksheet = worksheets.addSheet(rs.getString("pk_seq") + "-" + rs.getString("ten")+"-"+ rs.getString("MaNhanVien"));
					CreateTuyenDDKD(worksheet, obj, userId, userTen, rs.getString("pk_seq"), rs.getString("ten")+'-'+rs.getString("MaNhanVien"));
					// //System.out.println("Lan thu "+i);
					i++;
				}
				rs.close();
			} catch (Exception er)
			{

			}
		}
		db.shutDown();

		workbook.save(out);
	}

	private void CreateTuyenDDKD(Worksheet worksheet, IDRouter obj, String userId, String userTen, String ddkdid,
		String ddkdten)
	{
		CreateStaticHeader(worksheet, userTen, obj, ddkdid, ddkdten);
		CreateStaticData(worksheet, obj, userId, ddkdid, ddkdten);

	}

	private void CreateStaticHeader(Worksheet worksheet, String userTen, IDRouter obj, String ddkdid, String tenddkd)
	{

		Cells cells = worksheet.getCells();

		Style style;
		// cells.setColumnWidth(0, 200.0f);
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("Sales Rep Route Report - Channel: General Trade");
		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(16);// size chu
		font2.setBold(true);

		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);

		cell = cells.getCell("A3");
		getCellStyle(worksheet, "A3", Color.BLUE, true, 10);
		cell.setValue("Reporting Date: " + this.getDateTime());

		cell = cells.getCell("A4");
		getCellStyle(worksheet, "A4", Color.BLUE, true, 10);
		cell.setValue("Created by:  " + userTen);

	}

	private void CreateStaticData(Worksheet worksheet, IDRouter obj, String userId, String ddkd, String ddkdten)
	{
		dbutils db = new dbutils();
		Cells cells = worksheet.getCells();
		String st = "";
		int dem = 0;

		if (obj.getnppId().length() > 0)
		{
			st = st + "tbh.npp_fk ='" + obj.getnppId() + "'";
		}

		if (obj.gettuyenId().length() > 0)
		{
			if (st.length() > 0)
				st = st + " and tbh.ngaylamviec ='" + obj.gettuyenId() + "' ";
			else
				st = "tbh.ngaylamviec ='" + obj.gettuyenId() + "' ";
		}

		if (obj.getddkdId().length() > 0)
		{
			if (st.length() > 0)
				st = st + " and tbh.ddkd_fk ='" + obj.getddkdId() + "' ";
			else
				st = st + " tbh.ddkd_fk ='" + obj.getddkdId() + "' ";
		}

		if (obj.getkenhId().length() > 0)
		{
			if (st.length() > 0)
				st = st + " and kh.kbh_fk ='" + obj.getkenhId() + "' ";
			else
				st = st + " kh.kbh_fk ='" + obj.getkenhId() + "' ";
		}
		if (st.length() > 0)
			st = " where " + st;
		// khoi tao ket noi csdl
		String st1 = "";

		if (obj.getnppId() != "")
		{
			st1 = st1 + " npp_fk = '" + obj.getnppId() + "' ";
		}
		if (obj.gettuyenId() != "")
		{

			st1 = st1 + " and ngaylamviec = '" + obj.gettuyenId() + "' ";
		}

		if (st1 == "")
		{
			st1 = " group by ngayid";
		} else
		{
			st1 = " where " + st1 + " group by ngayid";
		}
		String sql2 = "select ngayid  from tuyenbanhang " + st1;
		// System.out.println("Lay Du Lieu :"+sql2);
		ResultSet rs2 = db.get(sql2);
		int i = 5;

		if (rs2 != null)
		{
			try
			{
				while (rs2.next())
				{
					i = i + 2;

					Integer ngay = Integer.parseInt(rs2.getString("ngayid"));
					if (ngay == 2)
					{
						Cell cell = cells.getCell("A" + i);
						cell.setValue("Monday");
					} else if (ngay == 3)
					{
						Cell cell = cells.getCell("A" + i);
						cell.setValue("Tuesday");
					} else if (ngay == 4)
					{
						Cell cell = cells.getCell("A" + i);
						cell.setValue("Wednesday");
					} else if (ngay == 5)
					{
						Cell cell = cells.getCell("A" + i);
						cell.setValue("Thursday");
					} else if (ngay == 6)
					{
						Cell cell = cells.getCell("A" + i);
						cell.setValue("Friday");
					} else
					{
						Cell cell = cells.getCell("A" + i);
						cell.setValue("Saturday");
					}
					i = i + 1;
					tieude(worksheet, String.valueOf(i));

					geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
					String sql = "select tbh.DIENGIAI as diengiai,v.ten as vung, kv.ten as khuvuc, npp.ten as npp, ddkd.ten as ddkd, tbh.ngaylamviec,kh.SmartId as Customer_Key,kh.ten as Customer_Name,kh.diachi as Address,qh.ten as province,case when ds.tonggiatri is null then 0 else ds.tonggiatri end as Average_Volume, lch.diengiai as Outlet_Type, " +
						"vt.vitri as Outlet_Location,hch.hang as Outlet_Class,kh_tuyen.tanso as Frequency " +
						"from khachhang kh " +
						"inner join nhaphanphoi npp on npp.pk_seq = kh.npp_fk " +
						"inner join khuvuc kv on kv.pk_seq = npp.khuvuc_fk " +
						"inner join vung v on v.pk_seq = kv.vung_fk " +
						"left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq " +
						"left join loaicuahang lch on lch.pk_seq = kh.lch_fk " +
						"left join vitricuahang vt on vt.pk_seq = kh.vtch_fk " +
						"left join hangcuahang hch on hch.pk_seq = kh.hch_fk " +
						"left join KHACHHANG_TUYENBH kh_tuyen on kh_tuyen.khachhang_fk = kh.pk_seq " +
						"left join (select khachhang_fk,cast(sum(tonggiatri)/3 as int) as tonggiatri from donhang where ngaynhap >'2011-10-01' and ngaynhap < '2011-12-31' group by khachhang_fk) as ds " +
						"on ds.khachhang_fk = kh.pk_seq " +
						"left join (select * from tuyenbanhang where ngayid = " +
						ngay +
						") tbh on tbh.pk_seq = kh_tuyen.tbh_fk " +
						"inner join daidienkinhdoanh ddkd on ddkd.pk_seq = tbh.ddkd_fk " + st

						+ " and ddkd.pk_seq = " + ddkd + " and npp.pk_seq in " + ut.quyen_npp(userId) // sua
																										// cho
																										// nay
						+ " order by v.ten, kv.ten, npp.ten ";

					System.out.println("Lay Du Lieu cuoi cung :" + sql);
					ResultSet rs = db.get(sql);
					i = i + 1;

					if (rs != null)
					{

						cells.setColumnWidth(0, 8.0f);
						cells.setColumnWidth(1, 10.0f);
						cells.setColumnWidth(2, 15.0f);
						cells.setColumnWidth(3, 15.0f);
						cells.setColumnWidth(4, 15.0f);
						cells.setColumnWidth(5, 15.0f);
						cells.setColumnWidth(6, 15.0f);
						cells.setColumnWidth(7, 15.0f);
						cells.setColumnWidth(8, 15.0f);
						cells.setColumnWidth(9, 15.0f);
						cells.setColumnWidth(10, 15.0f);
						cells.setColumnWidth(11, 20.0f);
						cells.setColumnWidth(12, 15.0f);
						cells.setColumnWidth(13, 15.0f);
						cells.setColumnWidth(14, 20.0f);
						cells.setColumnWidth(15, 20.0f);

						try
						{

							Cell cell = null;
							int stt = 1;
							while (rs.next())// lap den cuoi bang du lieu
							{

								// lay tu co so du lieu, gan bien

								String Stt = String.valueOf(stt);
								String Region = rs.getString("vung");
								String Area = rs.getString("khuvuc");
								String Distributor = rs.getString("npp");
								String SalesRep = rs.getString("ddkd");
								String Thu = rs.getString("ngaylamviec");
								String CustomerCode = rs.getString("Customer_Key");
								String CustomerName = rs.getString("Customer_Name");
								String Address = rs.getString("Address");
								String Town = rs.getString("province");
								String AverageVolume = rs.getString("Average_Volume");
								String OutletType = rs.getString("Outlet_Type");
								String OutletLocation = rs.getString("Outlet_Location");
								String OutletClass = rs.getString("Outlet_Class");
								String Frequency = rs.getString("Frequency");
								String diengiai = rs.getString("diengiai");

								// cell = cells.getCell("AA" +
								// Integer.toString(i)); cell.setValue(Channel);
								cell = cells.getCell("A" + Integer.toString(i));
								cell.setValue(Stt);
								CreateBorderSetting(worksheet, "A" + Integer.toString(i));
								cell = cells.getCell("B" + Integer.toString(i));
								cell.setValue(Region);
								CreateBorderSetting(worksheet, "B" + Integer.toString(i));
								cell = cells.getCell("C" + Integer.toString(i));
								cell.setValue(Area);
								CreateBorderSetting(worksheet, "C" + Integer.toString(i));
								cell = cells.getCell("D" + Integer.toString(i));
								cell.setValue(Distributor);
								CreateBorderSetting(worksheet, "D" + Integer.toString(i));
								cell = cells.getCell("E" + Integer.toString(i));
								cell.setValue(SalesRep);
								CreateBorderSetting(worksheet, "E" + Integer.toString(i));
								// cell = cells.getCell("F" +
								// Integer.toString(i)); cell.setValue(Thu);
								// CreateBorderSetting(worksheet,"F" +
								// Integer.toString(i));
								cell = cells.getCell("F" + Integer.toString(i));
								cell.setValue(CustomerCode);
								CreateBorderSetting(worksheet, "F" + Integer.toString(i));
								cell = cells.getCell("G" + Integer.toString(i));
								cell.setValue(CustomerName);
								CreateBorderSetting(worksheet, "G" + Integer.toString(i));
								cell = cells.getCell("H" + Integer.toString(i));
								cell.setValue(Address);
								CreateBorderSetting(worksheet, "H" + Integer.toString(i));
								cell = cells.getCell("I" + Integer.toString(i));
								cell.setValue(Town);
								CreateBorderSetting(worksheet, "I" + Integer.toString(i));
								cell = cells.getCell("J" + Integer.toString(i));
								cell.setValue(Float.parseFloat(AverageVolume));
								CreateBorderSetting(worksheet, "J" + Integer.toString(i));
								cell = cells.getCell("K" + Integer.toString(i));
								cell.setValue(OutletType);
								CreateBorderSetting(worksheet, "K" + Integer.toString(i));
								cell = cells.getCell("L" + Integer.toString(i));
								cell.setValue(OutletLocation);
								CreateBorderSetting(worksheet, "L" + Integer.toString(i));
								cell = cells.getCell("M" + Integer.toString(i));
								cell.setValue(OutletClass);
								CreateBorderSetting(worksheet, "M" + Integer.toString(i));
								cell = cells.getCell("N" + Integer.toString(i));
								cell.setValue(Frequency);
								CreateBorderSetting(worksheet, "N" + Integer.toString(i));
								cell = cells.getCell("O" + Integer.toString(i));
								cell.setValue(diengiai);
								CreateBorderSetting(worksheet, "O" + Integer.toString(i));

								i++;
								stt++;
							}// end while

						}// end try
						catch (Exception e)
						{
						} finally
						{
							if (rs != null)
								rs.close();
							/*
							 * if(db!=null){ db.shutDown(); }
							 */
						}
					}// end if
				}// end while
			} catch (Exception e)
			{
				// System.out.println("Errr" + e.toString());
			}// end try

			finally
			{
				if (rs2 != null)
					try
					{
						rs2.close();
					} catch (Exception e)
					{
						// System.out.println("Errr" + e.toString());
						e.printStackTrace();
					}
			}
			if (db != null)
			{
				db.shutDown();
			}
		}// end if

	}

	private void getCellStyle(Worksheet worksheet, String a, Color mau, Boolean dam, int size)
	{

		Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a);
		style = cell.getStyle();
		Font font1 = new Font();
		font1.setColor(mau);
		font1.setBold(dam);
		font1.setSize(size);
		style.setFont(font1);
		cell.setStyle(style);
	}

	
	private void tieude(Worksheet worksheet, String a) throws IOException
	{

		Cells cells = worksheet.getCells();
		Cell cell = cells.getCell("A" + a);
		cell.setValue("STT");
		CreateBorderSetting(worksheet, "A" + a);
		getCellStyle(worksheet, "A" + a, Color.BLUE, true, 10);
		cell = cells.getCell("B" + a);
		cell.setValue("Khu Vực");
		CreateBorderSetting(worksheet, "B" + a);
		getCellStyle(worksheet, "B" + a, Color.BLUE, true, 10);
		cell = cells.getCell("C" + a);
		cell.setValue("Khu vực");
		CreateBorderSetting(worksheet, "C" + a);
		getCellStyle(worksheet, "C" + a, Color.BLUE, true, 10);
		cell = cells.getCell("D" + a);
		cell.setValue("Chi nhánh/Đối tác");
		CreateBorderSetting(worksheet, "D" + a);
		getCellStyle(worksheet, "D" + a, Color.BLUE, true, 10);
		cell = cells.getCell("E" + a);
		cell.setValue("Trình dược viên");
		CreateBorderSetting(worksheet, "E" + a);
		getCellStyle(worksheet, "E" + a, Color.BLUE, true, 10);
		// cell = cells.getCell("F" + a); cell.setValue("Thu");
		// CreateBorderSetting(worksheet,"F"+a);
		// getCellStyle(worksheet,"F"+a,Color.BLUE,true,10);
		cell = cells.getCell("F" + a);
		cell.setValue("Mã khách hàng");
		CreateBorderSetting(worksheet, "F" + a);
		getCellStyle(worksheet, "F" + a, Color.BLUE, true, 10);
		cell = cells.getCell("G" + a);
		cell.setValue("Tên khách hàng");
		CreateBorderSetting(worksheet, "G" + a);
		getCellStyle(worksheet, "G" + a, Color.BLUE, true, 10);
		cell = cells.getCell("H" + a);
		cell.setValue("Địa chỉ");
		CreateBorderSetting(worksheet, "H" + a);
		getCellStyle(worksheet, "H" + a, Color.BLUE, true, 10);
		cell = cells.getCell("I" + a);
		cell.setValue("Quận");
		CreateBorderSetting(worksheet, "I" + a);
		getCellStyle(worksheet, "I" + a, Color.BLUE, true, 10);
		cell = cells.getCell("J" + a);
		cell.setValue("Doanh số trung bình");
		CreateBorderSetting(worksheet, "J" + a);
		getCellStyle(worksheet, "J" + a, Color.BLUE, true, 10);
		cell = cells.getCell("K" + a);
		cell.setValue("Loại cửa hàng");
		CreateBorderSetting(worksheet, "K" + a);
		getCellStyle(worksheet, "K" + a, Color.BLUE, true, 10);
		cell = cells.getCell("L" + a);
		cell.setValue("Vị trí của hàng");
		CreateBorderSetting(worksheet, "L" + a);
		getCellStyle(worksheet, "L" + a, Color.BLUE, true, 10);
		cell = cells.getCell("M" + a);
		cell.setValue("Hạng cửa hàng");
		CreateBorderSetting(worksheet, "M" + a);
		getCellStyle(worksheet, "M" + a, Color.BLUE, true, 10);
		cell = cells.getCell("N" + a);
		cell.setValue("Tần số ");
		CreateBorderSetting(worksheet, "N" + a);
		getCellStyle(worksheet, "N" + a, Color.BLUE, true, 10);
		cell = cells.getCell("O" + a);
		cell.setValue("Diễn giải TBH");
		CreateBorderSetting(worksheet, "O" + a);
		getCellStyle(worksheet, "O" + a, Color.BLUE, true, 10);
	}

	public void CreateBorderSetting(Worksheet worksheet, String fileName) throws IOException
	{
		Cells cells = worksheet.getCells();
		Cell cell;
		Style style;

		cell = cells.getCell(fileName);
		style = cell.getStyle();

		// Set border color
		style.setBorderColor(BorderType.TOP, Color.BLACK);
		style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		style.setBorderColor(BorderType.LEFT, Color.BLACK);
		style.setBorderColor(BorderType.RIGHT, Color.BLACK);
		// style.setBorderColor(BorderType.DIAGONAL_DOWN, Color.BLUE);
		// style.setBorderColor(BorderType.DIAGONAL_UP, Color.BLUE);

		// Set the cell border type
		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
		// style.setBorderLine(BorderType.DIAGONAL_DOWN, BorderLineType.DASHED);
		// style.setBorderLine(BorderType.DIAGONAL_UP, BorderLineType.DASHED);

		cell.setStyle(style);

	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private void XuatFileExcelSR(HttpServletResponse response, String NppId, String DdkdId,IDRouter obj) throws IOException
	{
		OutputStream out1 = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=XuatMCP "+util.setTieuDe(obj)+".xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			dbutils db = new dbutils();
			String sql = "select npp.ten as tennpp ,npp.ma,kv.ten as tenkv,ddkd.pk_seq as ddkdid,ddkd.ten  as ddkdten,ddkd.MaNhanVien " +
				" from nhaphanphoi npp  " + " left join khuvuc kv  on kv.pk_Seq=npp.khuvuc_fk " +
				" inner join daidienkinhdoanh_npp   ddkdnpp on ddkdnpp.npp_fk=npp.pk_seq   " +
				"  inner join daidienkinhdoanh ddkd on ddkd.pk_Seq=ddkdnpp.ddkd_fk "+
				" " + " where npp.pk_seq=" + NppId;
			if (!DdkdId.equals(""))
			{
				sql = sql + " and ddkd.pk_seq=" + DdkdId;
			}

			ResultSet rs = db.get(sql);
			int k = 0;
			System.out.println("Get "+sql);
			while (rs.next())
			{
				WritableSheet sheet = w.createSheet(rs.getString("ddkdid"), k);

				sheet.addCell(new Label(0, 1, "NPP : "));
				sheet.addCell(new Label(1, 1, "" + rs.getString("tennpp")));

				sheet.addCell(new Label(0, 2, "KHU VỰC : "));
				sheet.addCell(new Label(1, 2, "" + rs.getString("tenkv")));

				sheet.addCell(new Label(0, 3, "NVBH : "));
				sheet.addCell(new Label(1, 3, "" + rs.getString("ddkdten") +" - " + rs.getString("MaNhanVien")   ));

				WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
				cellFont.setColour(Colour.BLACK);

				WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

				cellFormat.setBackground(jxl.format.Colour.GRAY_25);
				cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				sheet.addCell(new Label(0, 4, "SỐ TT", cellFormat));
				sheet.addCell(new Label(1, 4, "MÃ KH", cellFormat));
				sheet.addCell(new Label(2, 4, "TÊN ĐƠN VỊ (dùng xuất HĐTC)", cellFormat));
				sheet.addCell(new Label(3, 4, "CHỦ CỬA HIỆU", cellFormat));
				sheet.addCell(new Label(4, 4, "ĐỊA CHỈ", cellFormat));
				sheet.addCell(new Label(5, 4, "LOẠI KHÁCH HÀNG", cellFormat));
				sheet.addCell(new Label(6, 4, "QUẬN/HUYỆN", cellFormat));
				sheet.addCell(new Label(7, 4, "TỈNH/THÀNH PHỐ", cellFormat));
				sheet.addCell(new Label(8, 4, "SỐ ĐIỆN THOẠI", cellFormat));
				sheet.addCell(new Label(9, 4, "NGÀY SINH", cellFormat));
				sheet.addCell(new Label(10, 4, "SỐ HỢP ĐỒNG", cellFormat));
				sheet.addCell(new Label(11, 4, "MÃ SỐ THUẾ", cellFormat));
				sheet.addCell(new Label(12, 4, "HÌNH THỨC THANH TOÁN", cellFormat));
				sheet.addCell(new Label(13, 4, "KÊNH BÁN HÀNG", cellFormat));
				sheet.addCell(new Label(14, 4, "TUYẾN THỨ", cellFormat));
				sheet.addCell(new Label(15, 4, "TẦN SUẤT VIẾNG THĂM", cellFormat));
				sheet.addCell(new Label(16, 4, "THANH TOÁN QUÝ", cellFormat));
				sheet.addCell(new Label(17, 4, "TÊN KÝ HỢP ĐỒNG", cellFormat));
				sheet.addCell(new Label(18, 4, "MÃ SỐ THUẾ CÁ NHÂN", cellFormat));
				
				

				sql = "select kh.MST_CaNhan,kh.TenKyHD,vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuongXa_fk as Phuong,  " +
						" kh.chucuahieu,kh.tinhthanh_fk,kh.quanhuyen_fk, kh.maFAST, " +
						" kh.ten as tencuahieu,kh.smartid,kh.diachi,kh.dienthoai,tbh.ngayid,khtbh.tanso,khtbh.sott,kh.NgaySinh,kh.MaHD,kh.MaSoThue,kh.XuatKhau,KH.THANHTOAN,kbh.Ten as KenhBanHang,kh.ThanhToanQuy from tuyenbanhang tbh " +
						" inner join khachhang_tuyenbh khtbh on tbh.pk_seq=khtbh.tbh_fk " +
						" inner join khachhang kh on kh.pk_seq=khtbh.khachhang_fk " +
						"left join kenhbanhang kbh on kbh.pk_Seq=kh.kbh_fk "+
						" left join hangcuahang hch on hch.pk_seq=kh.hch_fk " +
						" left join loaicuahang lch on lch.pk_seq=kh.lch_fk " +
						" left join vitricuahang vt on vt.pk_seq=kh.vtch_fk " + " where tbh.npp_fk= " + NppId +
						" and tbh.ddkd_fk=" + rs.getString("ddkdid") + " order by ddkd_fk,ngayid,khtbh.sott ";

				System.out.println("Get1:"+ sql);

				ResultSet rs1 = db.get(sql);
				Label label;

				Number number;
				int j = 5;
				// set style to cell data
				WritableCellFormat cellFormat2 = new WritableCellFormat();

				cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				if (rs1 != null)
					while (rs1.next())
					{

						label = new Label(0, j, rs1.getString("sott"), cellFormat2);

						sheet.addCell(label);

						label = new Label(1, j, rs1.getString("maFAST"), cellFormat2);
						sheet.addCell(label);

						label = new Label(2, j, rs1.getString("tencuahieu"), cellFormat2);
						sheet.addCell(label);

						label = new Label(3, j, rs1.getString("chucuahieu"), cellFormat2);
						sheet.addCell(label);

						label = new Label(4, j, rs1.getString("diachi"), cellFormat2);
						sheet.addCell(label);
						
						String phanloai =rs1.getString("xuatkhau")==null?"":rs1.getString("xuatkhau");
						
						if(phanloai.equals("0"))
						{
							phanloai="BL";
						}else if(phanloai.equals("1"))
						{
							phanloai="BB";
						}else if(phanloai.equals("2"))
						{
							phanloai="BB-BL";
						}
						else if(phanloai.equals("3"))
						{
							phanloai="BL-BB";
						}
						
						label = new Label(5, j, phanloai  , cellFormat2);
						sheet.addCell(label);

						label = new Label(6, j, rs1.getString("quanhuyen_fk"), cellFormat2);
						sheet.addCell(label);

						label = new Label(7, j, rs1.getString("tinhthanh_fk"), cellFormat2);
						sheet.addCell(label);

						label = new Label(8, j, rs1.getString("dienthoai"), cellFormat2);
						sheet.addCell(label);

						label = new Label(9, j, rs1.getString("NgaySinh"), cellFormat2);
						sheet.addCell(label);

						label = new Label(10, j, rs1.getString("MaHD"), cellFormat2);
						sheet.addCell(label);

						label = new Label(11, j, rs1.getString("MaSoThue"), cellFormat2);
						sheet.addCell(label);
						
						String thanhtoan = rs1.getString("ThanhToan")==null?"":rs1.getString("ThanhToan");
						
						if(thanhtoan.equals("0"))
						{
							thanhtoan="TM";
						}else if(thanhtoan.equals("1"))
						{
							thanhtoan="HD";
						}
						label = new Label(12, j, thanhtoan, cellFormat2);
						sheet.addCell(label);
						
						label = new Label(13, j, rs1.getString("kenhbanhang"), cellFormat2);
						sheet.addCell(label);

						label = new Label(14, j, rs1.getString("ngayid"), cellFormat2);
						sheet.addCell(label);

						label = new Label(15, j, rs1.getString("tanso"), cellFormat2);
						sheet.addCell(label);
						
						thanhtoan = rs1.getString("ThanhToanQuy")==null?"":rs1.getString("ThanhToanQuy");
						if(thanhtoan.equals("0"))
						{
							thanhtoan="TM";
						}else if(thanhtoan.equals("1"))
						{
							thanhtoan="HD";
						}
						label = new Label(16, j, thanhtoan, cellFormat2);
						sheet.addCell(label);

						String TENKYHD = rs1.getString("TENKYHD")==null?"":rs1.getString("TENKYHD");
						label = new Label(17, j, TENKYHD, cellFormat2);
						sheet.addCell(label);
						
						String MST_CaNhan = rs1.getString("MST_CaNhan")==null?"":rs1.getString("MST_CaNhan");
						label = new Label(18, j, MST_CaNhan, cellFormat2);
						sheet.addCell(label);
						
						
						j++;
					}

				k++;
			}
			w.write();
			w.close();
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out1 != null)
				out1.close();
		}
	}

}
