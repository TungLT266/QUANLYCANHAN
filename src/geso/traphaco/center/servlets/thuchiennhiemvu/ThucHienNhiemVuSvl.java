package geso.traphaco.center.servlets.thuchiennhiemvu;

import geso.traphaco.center.beans.thuchiennhiemvu.IThucHienNhiemVu;
import geso.traphaco.center.beans.thuchiennhiemvu.imp.ThucHienNhiemVu;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ExcelAPI;
import geso.traphaco.center.util.Utility;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Enumeration;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;

import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


import com.oreilly.servlet.MultipartRequest;

@WebServlet("/ThucHienNhiemVuSvl")
public class ThucHienNhiemVuSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "C:\\upload\\excel\\";

	public ThucHienNhiemVuSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out = response.getWriter();

		HttpSession session = request.getSession();
		IThucHienNhiemVu obj = new ThucHienNhiemVu();
		obj.createRs();
		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);
		out.println(action);

		String bgId = util.getId(querystring);

		if (action.equals("delete"))
		{
			// Delete(bgId, obj);
			out.print(bgId);
		}
		obj.setUserId(userId);
		session.setAttribute("thnvBean", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/ThucHienNhiemVu.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		IThucHienNhiemVu obj = new ThucHienNhiemVu();
		HttpSession session = request.getSession();
		String contentType = request.getContentType();
		Utility util = new Utility();

		String userId = request.getParameter("userId");
		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}

		String doituong = util.antiSQLInspection(request.getParameter("doituong"));
		if (doituong == null)
			doituong = "";
		obj.setDoituong(doituong);

		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			obj.setThang(thang);

		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			obj.setNam(nam);
		
		

		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		obj.setKbhId(kbhId);

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		obj.setDvkdId(dvkdId);

		String nvId = util.antiSQLInspection(request.getParameter("nvId"));
		if (nvId == null)
			nvId = "";
		obj.setNvId(nvId);

		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			Enumeration files = multi.getFileNames();
			String filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			obj.setUserId(userId);

			 doituong = util.antiSQLInspection(multi.getParameter("doituong"));
			if (doituong == null)
				doituong = "";
			obj.setDoituong(doituong);

			 thang = util.antiSQLInspection(multi.getParameter("thang"));
			if (thang == null)
				thang=getDateTime().split("-")[1];
				obj.setThang(thang);

			 nam = util.antiSQLInspection(multi.getParameter("nam"));
			if (nam == null)
				thang=getDateTime().split("-")[0];
				obj.setNam(nam);

			 kbhId = util.antiSQLInspection(multi.getParameter("kbhId"));
			if (kbhId == null)
				kbhId = "";
			obj.setKbhId(kbhId);

			 dvkdId = util.antiSQLInspection(multi.getParameter("dvkdId"));
			if (dvkdId == null)
				dvkdId = "";
			obj.setDvkdId(dvkdId);

			 nvId = util.antiSQLInspection(multi.getParameter("nvId"));
			if (nvId == null)
				nvId = "";
			obj.setNvId(nvId);
			if (doituong.trim().length() <= 0)
			{
				obj.setMsg("Vui lòng chọn đối tượng");
			}
			if (nvId.trim().length() <= 0)
			{
				obj.setMsg("Vui lòng chọn nhiệm vụ nhân viên");
			}

			if (filename != null && filename.length() > 0)
				obj.setMsg(this.readExcel(UPLOAD_DIRECTORY + filename, obj));
			if (obj.getMsg().trim().length() <= 0 && filename != null)
			{
				obj.setMsg("Đọc file thành công ");
			} else if (filename == null)
			{
				obj.setMsg("Vui lòng chọn file ");
			}
			obj.createRs();
		} else if (action.equals("excel"))
		{
			
			if (doituong.trim().length() <= 0)
			{
				obj.setMsg("Vui lòng chọn đối tượng");
			}
			if (nvId.trim().length() <= 0)
			{
				obj.setMsg("Vui lòng chọn nhiệm vụ nhân viên");
			}
			obj.setMsg(Excel(response, obj));
			obj.createRs();
		} else
		{
			obj.createRs();
		}
		String nextJSP = "/TraphacoHYERP/pages/Center/ThucHienNhiemVu.jsp";
		session.setAttribute("thnvBean", obj);
		session.setAttribute("userId", userId);
		response.sendRedirect(nextJSP);
	}

	private String Excel(HttpServletResponse response, IThucHienNhiemVu obj) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=DanhGiaThucHienNhiemVu_" +obj.getDoituong()+"_"+obj.getNam()+"_"+obj.getThang()+".xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("DanhGiaThucHienNhiemVu", 0);
			dbutils db = new dbutils();
			String doituong="";
			String query = "select count(*) from THUCHIENNHIEMVUTHANG  where NhiemVuNhanVien_fk ='" + obj.getNvId()+ "'";
			ResultSet rs = db.get(query);
			int sodong = 0;
			if (rs != null)
			{
				while (rs.next())
				{
					sodong = rs.getInt(1);
				}
				rs.close();
			}
			System.out.println("Query check____"+query+"[sodong]"+sodong);
			if (sodong > 0)
			{
				query =
				"select b.pk_seq as NhiemVuNhanVienId,d.pk_seq as NhiemVuId,d.TieuChi as NhiemVu,b.Thang,b.Nam,a.NhanVien_FK as NhanVienId, \n"+
				"	case \n"+
				"		when b.DOITUONG='SR' then (select ten from DAIDIENKINHDOANH WHERE PK_SEQ=A.NHANVIEN_FK)  \n"+
				"		when b.DOITUONG='SS' then (select ten from GIAMSATBANHANG WHERE PK_SEQ=A.NHANVIEN_FK) \n"+ 
				"		when b.DOITUONG='BM' then (select ten from BM WHERE PK_SEQ=A.NHANVIEN_FK)  \n"+
				"		when b.DOITUONG='ASM' then (select ten from ASM WHERE PK_SEQ=A.NHANVIEN_FK) end AS NhanVien,a.DAT \n"+
				"from THUCHIENNHIEMVUTHANG a inner join \n"+
				"NHIEMVUNHANVIEN b on a.NHIEMVUNHANVIEN_FK=b.PK_SEQ \n"+
				"inner join NHIEMVUNHANVIEN_CHITIET c on c.NHIEMVUNHANVIEN_FK=b.PK_SEQ \n"+
				"inner join NHIEMVU d on d.PK_SEQ=a.NHIEMVU_FK and d.PK_SEQ=c.NHIEMVU_FK  " +
				" where d.istudong=0  " +
				" order by a.NhanVien_fk,d.TIEUCHI" ;
			}
			else 
			{
				if(obj.getDoituong().equals("SR"))
				{
					doituong=" DaiDienKinhDoanh ";
				}
				else if(obj.getDoituong().equals("SS"))
				{
					doituong=" GiamSatBanHang ";
				}
				else if(obj.getDoituong().equals("ASM"))
				{
					doituong=" ASM ";
				}
				
				query = 
					"select a.PK_SEQ as NhiemVuNhanVienId,c.PK_SEQ as NhiemVuId,c.TIEUCHI as NhiemVu,a.THANG,a.DOITUONG,a.NAM,d.PK_SEQ as NhanVienId,d.TEN as NhanVien,'' as Dat \n"+
					"	from NHIEMVUNHANVIEN a \n"+
					"	inner join NHIEMVUNHANVIEN_CHITIET b on b.NHIEMVUNHANVIEN_FK=a.PK_SEQ \n"+
					"	inner join NHIEMVU c on c.PK_SEQ=b.NHIEMVU_FK,"+doituong+" d \n"+
					" where d.TRANGTHAI=1 and c.ISTUDONG=0 and a.pk_seq='"+obj.getNvId()+"' \n"+
					" order by d.pk_seq,c.TIEUCHI ";
			}
			System.out.println("Excel____"+query);
			WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
			cellFont.setColour(Colour.BLACK);
			cellFont.setBoldStyle(jxl.write.WritableFont.BOLD);
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(Colour.WHITE);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

			WritableFont fBold = new WritableFont(WritableFont.COURIER, 16);
			fBold.setColour(Colour.RED);
			fBold.setBoldStyle(jxl.write.WritableFont.BOLD);
			WritableCellFormat fmBold = new WritableCellFormat(fBold);
			fmBold.setBorder(Border.ALL, BorderLineStyle.THIN);

			sheet.addCell(new Label(4, 0, "Đánh giá tình hình thực hiện nhiệm vụ nhân viên", fmBold));
			sheet.mergeCells(4, 0, 15, 0);

			sheet.addCell(new Label(0, 1, "Tháng "+obj.getThang(), cellFormat));
			sheet.addCell(new Label(0, 2, "Năm "+obj.getNam(), cellFormat));
			sheet.addCell(new Label(0, 3, "Mã "+obj.getNvId(), cellFormat));
			sheet.addCell(new Label(0, 4, "Đánh giá "+obj.getDoituong(), cellFormat));
			
			
			sheet.addCell(new Label(0, 10, "STT", cellFormat));
			sheet.addCell(new Label(1, 10, "Mã nhân viên", cellFormat));
			sheet.addCell(new Label(2, 10, "Tên nhân viên", cellFormat));
			
			sheet.addCell(new Label(2, 8, "Mã nhiệm vụ", cellFormat));
			sheet.addCell(new Label(2, 9, "Tên nhiệm vụ", cellFormat));
			rs = db.get(query);
			Label label;
			jxl.write.Number number;
			int row = 10;
			int col = 3;
			int stt = 0;
			String nhiemvuIdPrev="";
			String nhanvienPrev="";
			if (rs != null)
			{
				while (rs.next())
				{
					String nhanvienId = rs.getString("nhanvienId");
					String nhanvien = rs.getString("nhanvien");
					String nhiemvuId = rs.getString("nhiemvuId");
					String nhiemvu = rs.getString("nhiemvu");
					
					String dat=rs.getString("dat");					
					if(!nhiemvuIdPrev.equals(nhiemvuId))
					{
						nhiemvuIdPrev=nhiemvuId;
						sheet.addCell(new Label(col, 8, nhiemvuId));
						sheet.addCell(new Label(col, 9, nhiemvu));
						sheet.addCell(new Label(col, 10, "Đạt"));
						col++;
					}
					if(!nhanvienPrev.equals(nhanvienId))
					{
						nhanvienPrev=nhanvienId;
						row++;
						col=3;
						stt++;
					}
					//System.out.println("[NhanVien]"+nhanvienId+"[NhiemVuId]"+nhiemvuId+"[col]"+col+"[row]"+row);
					number = new jxl.write.Number(0, row, stt);sheet.addCell(number);
					sheet.addCell(new Label(1, row, nhanvienId));
					sheet.addCell(new Label(2, row, nhanvien));
					sheet.addCell(new Label(col, row, dat));
					
				}
				rs.close();
			}
			ExcelAPI.sheetAutoFitColumns(sheet);
			w.write();
			w.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			return "Không có thể xuất đánh giá tình hình thực hiện nhân viên ";
		}
		return "";
	}

	private String readExcel(String fileName, IThucHienNhiemVu obj)
	{
		File inputWorkbook = new File(fileName);
		jxl.Workbook w = null;
		dbutils db = new dbutils();
		String query = "";
		String doituong="";
		try
		{
			w = jxl.Workbook.getWorkbook(inputWorkbook);
			jxl.Sheet sheet = w.getSheet(0);
			db.getConnection().setAutoCommit(false);
			
			int sodong = sheet.getRows();
			int socot=sheet.getColumns();
			int soNhiemVu=socot-3;
			String nhanvienId="";
			query="Delete from ThucHienNhiemVuThang where NhiemVuNhanVien_FK='"+obj.getNvId()+"'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				System.out.println("115..."+query);
				return "Lỗi đọc file "+query;
			}					
			for (int i = 11; i < sodong; i++)
			{
				for(int j=0;j<soNhiemVu;j++)
				{
					nhanvienId=getValue(sheet, 1, i).trim();
					String nhiemvuId=getValue(sheet,3+j ,8).trim();
					String dat=getValue(sheet, 3+j, i).trim();
					if(dat.length()<=0)
						dat="0";
				//	System.out.println("[nhiemVuId]"+nhiemvuId+"[dong]"+i+"[cot]"+j);
					if(nhiemvuId.length()>0)
					{
						query="insert into ThucHienNhiemVuThang(NhiemVuNhanVien_FK,NhiemVu_FK,NhanVien_FK,Dat)"+
							" select '"+obj.getNvId()+ "','"+nhiemvuId+"', '"+nhanvienId+"','"+dat+"'" ;
						if(db.updateReturnInt(query)<1)
						{
							db.getConnection().rollback();
							System.out.println("115..."+query);
							return "Lỗi đọc file "+query;
						}
					}
				}
			}
			
			if(obj.getDoituong().equals("SR"))
			{
				doituong=" DaiDienKinhDoanh ";
			}
			else if(obj.getDoituong().equals("SS"))
			{
				doituong=" GiamSatBanHang ";
			}
			else if(obj.getDoituong().equals("ASM"))
			{
				doituong=" ASM ";
			}
			query = 
				"insert into ThucHienNhiemVuThang(NhiemVuNhanVien_FK,NhiemVu_FK,NhanVien_FK,Dat)"+
				"select a.PK_SEQ as NhiemVuNhanVienId,c.PK_SEQ as NhiemVuId,d.PK_SEQ as NhanVienId,'0' as Dat \n"+
				"	from NHIEMVUNHANVIEN a \n"+
				"	inner join NHIEMVUNHANVIEN_CHITIET b on b.NHIEMVUNHANVIEN_FK=a.PK_SEQ \n"+
				"	inner join NHIEMVU c on c.PK_SEQ=b.NHIEMVU_FK,"+doituong+" d \n"+
				" where d.TRANGTHAI=1 and c.ISTUDONG=1 and a.pk_seq='"+obj.getNvId()+"' \n";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				System.out.println("115..."+query);
				return "Lỗi đọc file "+query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e )
		{
			e.printStackTrace();
			db.update("rollback");
			return "Lỗi đọc file "+e.getMessage();
		}catch(BiffException e)
		{
			e.printStackTrace();
			db.update("rollback");
			return "Lỗi đọc file "+e.getMessage();
		}
		catch(IOException e )
		{
			e.printStackTrace();
			db.update("rollback");
			return "Lỗi đọc file "+e.getMessage();
		}
		return "";
	}

	private String getValue(jxl.Sheet sheet, int column, int row)
	{
		jxl.Cell cell;
		cell = sheet.getCell(column, row);
		return cell.getContents();
	}
	
	public static void main(String[] arg)
	{
		ThucHienNhiemVuSvl svl=new ThucHienNhiemVuSvl();
		IThucHienNhiemVu obj=new ThucHienNhiemVu();
		svl.readExcel("", obj);
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}

