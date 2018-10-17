package geso.traphaco.center.servlets.upload;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.center.beans.upload.IUpload;
import geso.traphaco.center.beans.upload.imp.Upload;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.oreilly.servlet.MultipartRequest;

@WebServlet("/UploadForeCastSvl")
public class UploadForeCastSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "C:\\upload\\excel\\";
	PrintWriter out;

	public UploadForeCastSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IUpload obj = new Upload();
		obj.createRs_ForeCast();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		obj.setUserId(userId);
		session.setAttribute("obj", obj);
		String id = util.getId(querystring);
		String action = util.getAction(querystring);
		System.out.println("_____" + action);
		String nextJSP = "/TraphacoHYERP/pages/Center/UploadForeCast.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IUpload dmhBean = new Upload();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();
		Utility util = new Utility();
		String userId;
		this.out = response.getWriter();
		String action = request.getParameter("action");
		if (action == null)
			action = "";
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			dmhBean.setUserId(userId);
			dmhBean.setThang(multi.getParameter("thang"));
			dmhBean.setNam(multi.getParameter("nam"));
			Enumeration files = multi.getFileNames();
			String filename = "";
			String type = util.antiSQLInspection(multi.getParameter("type"));
			dmhBean.setType(type);
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			String msg = "";
			if (filename != null && filename.length() > 0)
				msg = this.readExcel(UPLOAD_DIRECTORY + filename, dmhBean);

			if (msg.trim().length() <= 0 && filename != null)
			{
				dmhBean.setMsg("Đọc file thành công ");
			} else if (filename == null)
			{
				dmhBean.setMsg("Vui lòng chọn file ");
			} else
			{
				dmhBean.setMsg(msg);
			}
			dmhBean.createRs_ForeCast();
			session.setAttribute("obj", dmhBean);
			String nextJSP = "/TraphacoHYERP/pages/Center/UploadForeCast.jsp";
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("excel"))
		{
			
		}
	}

	private String readExcel(String fileName, IUpload dhBean)
	{
		File inputWorkbook = new File(fileName);
		jxl.Workbook w = null;
		dbutils db = new dbutils();
		try
		{
			w = jxl.Workbook.getWorkbook(inputWorkbook);
			jxl.Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();
			int socot = sheet.getColumns();
			int soNpp = (socot - 16) / 4;
			String query = "delete from SalesForeCast_TMP";
			db.update(query);

			Hashtable<String, Integer> Dong_Npp = new Hashtable<String, Integer>();
			for (int i = 0; i < sodong; i++)
			{
				String value = getValue(sheet, 0, i).trim();
				String nppMa = getValue(sheet, 0, i).trim();
				if (i == 0)
				{
					Dong_Npp.put(nppMa, 0);
				} else
				{
					if (value.equals("Total") && i + 1 < sodong)
					{
						i = i + 1;
						nppMa = getValue(sheet, 0, i).trim();
						Dong_Npp.put(nppMa, i);
						// System.out.println("[nppMa]" + nppMa + "[dong]" + i);
					}
				}
			}
			for (int i = 0; i < sodong; i++)
			{
				for (int j = 0; j < soNpp; j++)
				{
					String spTen = getValue(sheet, 1, i).trim();
					String nppMa = getValue(sheet, 16 + 4 * j, 0).trim();
					String quy1 = getValue(sheet, 16 + 4 * j, i).trim().replace(",", "");
					String quy2 = getValue(sheet, 17 + 4 * j, i).trim().replace(",", "");
					String quy3 = getValue(sheet, 18 + 4 * j, i).trim().replace(",", "");
					String quy4 = getValue(sheet, 19 + 4 * j, i).trim().replace(",", "");
					if (spTen.contains("'"))
					{
						spTen = spTen.replace("'", "''");
					}
					if (quy1.length() <= 0)
						quy1 = "0";
					if (quy2.length() <= 0)
						quy2 = "0";
					if (quy3.length() <= 0)
						quy3 = "0";
					if (quy4.length() <= 0)
						quy4 = "0";
					if (!spTen.equals("Total") && spTen.length() > 0 && !spTen.equals("SKU"))
					{
						query = " insert into SalesForeCast_TMP(nppMa,spTen,Q1,Q2,Q3,Q4,Nam)" + " select '" + nppMa + "',N'" + spTen + "','" + quy1 + "','" + quy2 + "','" + quy3 + "','" + quy4
								+ "','" + dhBean.getNam() + "'";
						if (!db.update(query))
						{
							System.out.println("tmp" + query);
						}
					}
				}
			}
			dhBean.SalesForcast();
			System.out.print(dhBean.getMsg());
			return dhBean.getMsg();
		} catch (BiffException e)
		{
			e.printStackTrace();
			return "Vui lòng coi lại định dạng file";
		} catch (IOException e)
		{
			e.printStackTrace();
			return "Vui lòng coi lại định dạng file";
		} catch (Exception e)
		{
			return "Vui lòng coi lại định dạng file";
		}

	}

	private String getValue(Sheet sheet, int column, int row)
	{
		Cell cell;
		cell = sheet.getCell(column, row);
		return cell.getContents();
	}

	public static void main(String[] arg)
	{
		UploadForeCastSvl p = new UploadForeCastSvl();
		IUpload obj = new Upload();
		p.readExcel("", obj);
	}

	private String Excel(HttpServletResponse response, String id) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=TonKho" + ".xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("IssueLock", 0);
			dbutils db = new dbutils();
			String sql = 
					  "  select a.pk_seq as forecastId,sp.ten + ' '+cast( sp.thetich as varchar(10))+ sp.dvthetich as spTen,sp.thetich,sp.dvthetich, "+
					  " (select soluong1 from quycach where sanpham_fk =sp.pk_Seq and dvdl2_fk=100018) as QuyCach, "+
					  "(select soluong from doidonvi where dv2='L' and dv1=sp.dvthetich)as Lit, "+
					  " npp.ma as nppMa,npp.matat as nppMaTat,npp.ten as nppTen,b.Q1,b.Q2,b.Q3,b.Q4 "+
					  " from saleSFORECAST a "+
					  " inner join saleSFORECAST_NPP_QUY b on b.saleSFORECAST_fk=a.pk_seq "+
					  " inner join nhaphanphoi npp on npp.pk_seq=b.npp_fk "+
					  " inner join sanpham sp on sp.pk_seq=b.sanpham_fk "+
					  " where a.pk_seq='"+id+"' "+
					  " order by sp.ten,sp.dvthetich,sp.thetich,npp.ma ";

			System.out.println("1.Khoi tao TonKho_Details : " + sql);

			WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
			cellFont.setColour(Colour.BLACK);
			cellFont.setBoldStyle(jxl.write.WritableFont.BOLD);
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(Colour.WHITE);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

			WritableFont fBold = new WritableFont(WritableFont.ARIAL, 10);
			fBold.setColour(Colour.RED);
			fBold.setBoldStyle(jxl.write.WritableFont.BOLD);
			WritableCellFormat fmBold = new WritableCellFormat(fBold);
			fmBold.setBorder(Border.ALL, BorderLineStyle.THIN);

			sheet.addCell(new Label(1, 0, "SKU", cellFormat));
			sheet.mergeCells(1, 0, 1, 1);
			sheet.addCell(new Label(2, 0, "Factor", cellFormat));
			sheet.mergeCells(2, 0, 2, 1);
			sheet.addCell(new Label(3, 0, "P1", cellFormat));
			sheet.mergeCells(3, 0, 3, 1);
			sheet.addCell(new Label(4, 0, "P2", cellFormat));
			sheet.mergeCells(4, 0, 4, 1);
			sheet.addCell(new Label(5, 0, "P3", cellFormat));
			sheet.mergeCells(5, 0, 5, 1);
			sheet.addCell(new Label(6, 0, "P4", cellFormat));
			sheet.mergeCells(6, 0, 6, 1);
			sheet.addCell(new Label(7, 0, "P5", cellFormat));
			sheet.mergeCells(7, 0, 7, 1);
			sheet.addCell(new Label(8, 0, "P6", cellFormat));
			sheet.mergeCells(8, 0, 8, 1);
			sheet.addCell(new Label(9, 0, "P7", cellFormat));
			sheet.mergeCells(9, 0, 9, 1);
			sheet.addCell(new Label(10, 0, "P8", cellFormat));
			sheet.mergeCells(10, 0, 10, 1);
			sheet.addCell(new Label(11, 0, "P9", cellFormat));
			sheet.mergeCells(11, 0, 11, 1);
			sheet.addCell(new Label(12, 0, "P10", cellFormat));
			sheet.mergeCells(12, 0, 12, 1);
			sheet.addCell(new Label(13, 0, "P11", cellFormat));
			sheet.mergeCells(13, 0, 13, 1);
			sheet.addCell(new Label(14, 0, "P12", cellFormat));
			sheet.mergeCells(14, 0, 14, 1);

			ResultSet rs = db.get(sql);
			Label label;
			jxl.write.Number number;
			int row = 1;
			int col = 0;
			int stt = 0;
			String nppMaPrev = "";
			String spPrev = "";
			if (rs != null)
			{
				while (rs.next())
				{
					String nppMa = rs.getString("nppMa");
					String nppTen = rs.getString("nppMaTat");
					String spTen = rs.getString("spTen");
					double thetich = Math.round(rs.getDouble("thetich"));
					String dvthetich = rs.getString("dvthetich");
					float quycach = rs.getFloat("quycach");
					float lit=rs.getFloat("lit");
					double factor=thetich*lit*quycach;
					double q1 = rs.getDouble("q1");
					double q2 = rs.getDouble("q2");
					double q3 = rs.getDouble("q3");
					double q4 = rs.getDouble("q4");

					if (!spPrev.equals(spTen))
					{
						col = 0;
						row++;
						stt++;
						spPrev = spTen;
					}
					sheet.addCell(new Label(0, row, spTen));
					number = new jxl.write.Number(1, row, factor);
					sheet.addCell(number);
					number = new jxl.write.Number(16 + col * 4, row, q1);
					sheet.addCell(number);
					number = new jxl.write.Number(17 + col * 4, row, q2);
					sheet.addCell(number);
					number = new jxl.write.Number(18 + col * 4, row, q3);
					sheet.addCell(number);
					number = new jxl.write.Number(19 + col * 4, row, q4);
					sheet.addCell(number);

					if (!nppMaPrev.equals(nppMa))
					{
						nppMaPrev = nppMa;
						sheet.addCell(new Label(16 + col * 4, 0, nppMa, fmBold));
						sheet.addCell(new Label(17 + col * 4, 0, nppTen, fmBold));
						sheet.addCell(new Label(16 + col * 4, 1, "Q1", format(cellFont, Colour.BLACK, Colour.WHITE, Border.ALL, BorderLineStyle.THIN)));
						sheet.addCell(new Label(17 + col * 4, 1, "Q2", format(cellFont, Colour.BLACK, Colour.WHITE, Border.ALL, BorderLineStyle.THIN)));
						sheet.addCell(new Label(18 + col * 4, 1, "Q3", format(cellFont, Colour.BLACK, Colour.WHITE, Border.ALL, BorderLineStyle.THIN)));
						sheet.addCell(new Label(19 + col * 4, 1, "Q4", format(cellFont, Colour.BLACK, Colour.WHITE, Border.ALL, BorderLineStyle.THIN)));
						col++;
					}
				}
				sheet.addCell(new Label(1, ++row, "Total"));
			}
			sheetAutoFitColumns(sheet);
			w.write();
			w.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			return "Không có báo cáo trong thời gian đã chọn ";
		}
		return "";
	}

	private WritableCellFormat format(WritableFont font, Colour color, Colour background, Border boder, BorderLineStyle lineStyle)
	{
		WritableFont cellFont = new WritableFont(font);
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		try
		{
			cellFont.setColour(color);
			cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(background);
			cellFormat.setBorder(boder, lineStyle);
		} catch (WriteException e)
		{
			e.printStackTrace();
		}
		return cellFormat;

	}

	private void sheetAutoFitColumns(WritableSheet sheet)
	{
		for (int i = 0; i < sheet.getColumns(); i++)
		{
			Cell[] cells = sheet.getColumn(i);
			int longestStrLen = -1;

			if (cells.length == 0)
				continue;

			/* Find the widest cell in the column. */
			for (int j = 0; j < cells.length; j++)
			{
				if (cells[j].getContents().length() > longestStrLen)
				{
					String str = cells[j].getContents();
					if (str == null || str.isEmpty())
						continue;
					longestStrLen = str.trim().length();
				}
			}

			/* If not found, skip the column. */
			if (longestStrLen == -1)
				continue;

			/* If wider than the max width, crop width */
			if (longestStrLen > 255)
				longestStrLen = 255;

			CellView cv = sheet.getColumnView(i);
			cv.setSize(longestStrLen * 256 + 100); /*
													 * Every character is 256
													 * units wide, so scale it.
													 */
			sheet.setColumnView(i, cv);
		}
	}

}
