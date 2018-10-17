package geso.traphaco.center.servlets.importtonkho;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.center.beans.upload.IUpload;
import geso.traphaco.center.beans.upload.imp.Upload;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
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

@WebServlet("/ImportTonKhoSvl")
public class ImportTonKhoSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "c:\\java-tomcat\\upload\\excel\\";
	PrintWriter out;
	NumberFormat formatter = new DecimalFormat("#,###,###");

	public ImportTonKhoSvl()
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

		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		obj.setUserId(userId);

		String nppId = util.getId(querystring);
		String action = util.getAction(querystring);
		System.out.println("_____" + action);
		obj.setNppId(nppId);
		if (action.equals("excel"))
		{
			obj.setMsg(XuatFileExcel(response, obj));
		}
		  else if(action.equals("delete"))
		{
			obj.setMsg(Delete(nppId));
		}
		obj.initImportRs();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/ImportTonKho.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();
		
		IUpload obj = new Upload();
		
		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		if (userId == null)
		{
			userId = "";
		}
		System.out.println("[UploadTonKhoSvl.doPost] userId = " + userId);

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}
		System.out.println("[UploadTonKhoSvl.doPost] action = " + action);

		OutputStream out = response.getOutputStream();
	
		
		String thang = request.getParameter("month");
		if (thang == null)
			thang = "";
		obj.setThang(thang);
	

		String nam = request.getParameter("year");
		if (nam == null)
			nam = "";
		obj.setNam(nam);


		String vungId = request.getParameter("vungId");
		if (vungId == null)
			vungId = "";
		obj.setVungId(vungId);
	
		
		
		String khuvucId = request.getParameter("khuvucId");
		if (khuvucId == null)
			khuvucId = "";
		obj.setKhuvucId(khuvucId);
		
		
		String nppId = request.getParameter("nppId");
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		System.out.println("nppId" + nppId);
		
		if (action.equals("Tao moi"))
		{

			try
			{
				System.out.println("parseInt = " + ((int) Float.parseFloat("0.0")));
			} catch (Exception e)
			{
				System.out.println("parseInt = " + e.getMessage());
			}

			IUpload bean = (IUpload) new Upload("");
			bean.setUserId(userId);

			session.setAttribute("spnppStr", "");
			session.setAttribute("uploadBean", bean);

			String nextJSP = "/TraphacoHYERP/pages/Center/UploadTonKhoNew.jsp";
			response.sendRedirect(nextJSP);
			return;
		} else if (action.equals("excel"))
		{
			obj.setMsg(XuatFileExcel(response, obj));
		} else if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			// ---- CHỖ NÀY LẤY DỮ LIỆU TỪ JSP NÈ -----//
			
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			/*userId = util.antiSQLInspection(multi.getParameter("userId"));
			obj.setId(userId);*/
	
			IUpload dmhBean = new Upload();
			
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			dmhBean.setUserId(userId);
			obj.setUserId(dmhBean.getUserId());
	
			System.out.println("---user 1 :" + userId);
			System.out.println("---user 2 :" + dmhBean.getUserId());
		
			dmhBean.setThang(multi.getParameter("month"));
			obj.setThang(dmhBean.getThang());
			
			dmhBean.setNam(multi.getParameter("year"));
			obj.setNam(dmhBean.getNam());
			
			dmhBean.setNppMa(multi.getParameter("nppMa"));
			dmhBean.setNppId(multi.getParameter("nppId"));
			dmhBean.setKhuvucId(multi.getParameter("khuvucId"));
			dmhBean.setVungId(multi.getParameter("vungId"));
			String ngaykhoaso=multi.getParameter("ngaykhoaso");
			
			
			dmhBean.setNgaykhoaso(ngaykhoaso);
			
			
			Enumeration files = multi.getFileNames();
			String filename = "";
			System.out.println("__userId" + userId);
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			if (filename != null && filename.length() > 0)
				dmhBean.setMsg(this.readExcel(UPLOAD_DIRECTORY + filename, dmhBean));
			
			if (dmhBean.getMsg().trim().length() <= 0 && filename != null)
			{
				obj.setMsg("Đọc file thành công ");
			} else if (filename == null)
			{
				obj.setMsg("Vui lòng chọn file ");
			} else
			{
				obj.setMsg(dmhBean.getMsg());
			}
		}
		obj.initImportRs();
		session.setAttribute("userId", userId);
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/ImportTonKho.jsp";
		response.sendRedirect(nextJSP);
	}

	private String readExcel(String fileName, IUpload dhBean)
	{
		File inputWorkbook = new File(fileName);
		jxl.Workbook w = null;
		dbutils db = new dbutils();
		try
		{
			System.out.println("--user 4 :"  +dhBean.getUserId());
			w = jxl.Workbook.getWorkbook(inputWorkbook);
			
			if(dhBean.getNppMa().trim().length()<=0)
			{
				return "Vui lòng chọn nhà phân phối";
			}
			
			int sosheet = w.getNumberOfSheets();
			String query = "delete from TonKho_Tmp";
			db.update(query);

			for (int ii = 0; ii < sosheet; ii++)
			{
				jxl.Sheet sheet = w.getSheet(ii);
				int sodong = sheet.getRows();
				int socot = sheet.getColumns();
				int soNpp = 1;
				System.out.println("[SoSheet]" + sosheet + "[So dong ]" + sodong + "[socot]" + socot + "[soNpp]" + soNpp);
				for (int i = 3; i < sodong; i++)
				{
					Cell[] cells = sheet.getRow(i);
					if(cells !=null){
							if(cells.length >= 1 && cells[1] != null  ){
								String nppMa = getValue(sheet, 1, i);
								String spMa = getValue(sheet, 3, i);
								String spTen = getValue(sheet, 4, i);
								String kenh = getValue(sheet, 5, i);
								 
								float tondau= 0; 
						/*		try{
									tondau =Float.parseFloat( getValue(sheet, 7, i).trim().replace(",", "") );
								}catch(Exception er){
									 
								}*/
								float soluongle =0;
								try{
									soluongle=Float.parseFloat(getValue(sheet, 7, i).trim().replace(",", ""));
								}
								catch(Exception er)
								{
									
								}
								double	khoId = 0;
									try
									{
										
										khoId= Double.parseDouble(getValue(sheet, 6, i).trim().replace(",", ""));
										
									}catch(Exception er)
									{
										
									}
			
								if (spMa.length() > 0 && nppMa.length() > 0)
								{
									query = " insert into tonkho_tmp(nppMa,spMa,spTen,tondau,khoId,soluongle,KENHBANHANG)" +
											" select '" + nppMa + "',N'" + spMa + "',N'" + spTen + "','" + tondau + "','"+khoId+"','"+soluongle+"','"+kenh+"' "; 
									if (!db.update(query))
									{
										System.out.println("tmp" + query);
									}
								}
							}
					}
				}
			}
		System.out.println("-- user 3: " + dhBean.getUserId());	
		 dhBean.importTonKho();
		 return dhBean.getMsg();
		} catch (BiffException e)
		{
			System.out.println("Loi doc file" + e.getMessage());
			e.printStackTrace();
			return "Vui lòng coi lại định dạng file " + e.getMessage();
		} catch (IOException e)
		{
			e.printStackTrace();
			return "Vui lòng coi lại định dạng file " + e.getMessage();
		}
	}

	
	private String Delete(String nppId)
	{
		dbutils db=new dbutils();
		String query="select count(*) as sodong from donhang where npp_fk="+nppId+"";
		ResultSet rs=db.get(query);
		try
		{
			while(rs.next())
			{
				if(rs.getInt("sodong") >0)
				{
					return "Nhà phân phối này đã có  đơn hàng!";
				}
			}
			rs.close();
			query="select count(*) as sodong from nhaphang where trangthai=1 and npp_fk='"+nppId+"'";
			rs=db.get(query);
			while(rs.next())
			{
				if(rs.getInt("sodong") >0)
				{
					return "Nhà phân phối này đã có  nhập hàng!";
				}
			}
			rs.close();
			
			
			query="select count(*)as sodong from DONTRAHANG where  npp_fk='"+nppId+"'";
			rs=db.get(query);
			while(rs.next())
			{
				if(rs.getInt("sodong") >0)
				{
					return "Nhà phân phối này đã có  đơn trả hàng!";
				}
			}
			rs.close();
			
			db.getConnection().setAutoCommit(false);
			query="delete from nhapp_kho where npp_fk='"+nppId+"'";
			if(!db.update(query))
			{
				db.update("rollback");
				return "Lỗi hệ thống "+query;
			}
			
			query="delete from nhapp_kho_chitiet where npp_fk='"+nppId+"'";
			if(!db.update(query))
			{
				db.update("rollback");
				return "Lỗi hệ thống "+query;
			}
			
			
			query="delete from tonkhongay where npp_fk='"+nppId+"'";
			if(!db.update(query))
			{
				db.update("rollback");
				return "Lỗi hệ thống "+query;
			}
			
			
			query="delete from tonkhongay_chitiet where npp_fk='"+nppId+"'";
			if(!db.update(query))
			{
				db.update("rollback");
				return "Lỗi hệ thống "+query;
			}			
			
			query="delete from khoasongay where npp_fk='"+nppId+"'";
			if(!db.update(query))
			{
				db.update("rollback");
				return "Lỗi hệ thống "+query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(false);
			db.shutDown();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	private String getValue(Sheet sheet, int column, int row)
	{
		Cell cell;
		cell = sheet.getCell(column, row);
		try{
		return cell.getContents();
		}catch(Exception er){
			return	"0";
		}
	}

	private String XuatFileExcel(HttpServletResponse response, IUpload obj) throws IOException
	{		
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=TonKho_" + getDateTime() + ".xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			dbutils db = new dbutils();
			String sql = 
			"select distinct tk.kho_fk as khoId,npp.pk_seq as nppId,npp.MA as nppMa,npp.TEN as nppTen,sp.PK_SEQ as spId,sp.MA as spMa,sp.ten as spTen,tk.SOLUONG,tk.AVAILABLE,tk.BOOKED, "+
/*			"	qc.SOLUONG1 ,qc.SOLUONG2,"*/
			" tk.SOLO,tk.NGAYHETHAN, b.DIENGIAI "+
			"from  "+
			"	NHAPP_KHO_CHITIET tk inner join SANPHAM sp on sp.PK_SEQ=tk.SANPHAM_FK "+
			"	inner join NHAPHANPHOI npp on npp.PK_SEQ=tk.NPP_FK "+
			" inner join KENHBANHANG b on b.PK_SEQ = tk.KBH_FK " +
		/*	"	inner join QUYCACH qc on qc.DVDL2_FK=100018 and qc.SANPHAM_FK=tk.SANPHAM_FK "+*/
			" where tk.npp_fk='"+obj.getNppId()+"' ";
			System.out.println("1.Xuất excel nhapp: " + sql);

			WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
			cellFont.setColour(Colour.BLACK);

			cellFont.setBoldStyle(jxl.write.WritableFont.BOLD);
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(jxl.format.Colour.GRAY_25);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

			WritableFont fBold = new WritableFont(WritableFont.TIMES, 10);
			fBold.setColour(Colour.RED);
			fBold.setBoldStyle(jxl.write.WritableFont.BOLD);
			WritableCellFormat fmBold = new WritableCellFormat(fBold);
			fmBold.setBorder(Border.ALL, BorderLineStyle.THIN);
			ResultSet rs = db.get(sql);
			Label label;
			jxl.write.Number number;
			int row = 3;
			int col = 0;
			int stt = 0;
			String nppIdPrev = "";
			String spPrev = "";
			int sheetIndex = 0;
			String tongthauPrev = "";
			WritableSheet sheet = null;
			sheet = w.createSheet(obj.getNppId(), sheetIndex);
			sheet.addCell(new Label(0, 2, "STT", cellFormat));
			sheet.addCell(new Label(1, 2, "Mã NPP", cellFormat));
			sheet.addCell(new Label(2, 2, "Tên NPP", cellFormat));
			sheet.addCell(new Label(3, 2, "Mã Sản phẩm", cellFormat));
			sheet.addCell(new Label(4, 2, "Tên sản phẩm", cellFormat));
			sheet.addCell(new Label(5, 2, "Kênh", cellFormat));
			sheet.addCell(new Label(6, 2, "Kho", cellFormat));
		
			
	/*		sheet.addCell(new Label(7, 2, "Thùng", cellFormat));*/
			
			sheet.addCell(new Label(7, 2, "Lẻ", cellFormat));
			
	/*		sheet.addCell(new Label(9, 2, "Quy cách", cellFormat));
			sheet.addCell(new Label(10, 2, "Số lô", cellFormat));
			sheet.addCell(new Label(11, 2, "Ngày hết hạn", cellFormat));
			if (rs != null)*/
			{
				while (rs.next())
				{
					String nppId = rs.getString("nppId");
					String nppMa = rs.getString("nppMa");
					String nppTen = rs.getString("nppTen");
					String spId = rs.getString("spId");
					String spMa = rs.getString("spMa");
					String spTen = rs.getString("spTen");
					String khoId = rs.getString("khoId");
				/*	float quycach=rs.getFloat("soluong1")/rs.getFloat("soluong2");*/
					String solo=rs.getString("solo");
					String ngayhethan=rs.getString("ngayhethan");
					String diengiai = rs.getString("DIENGIAI");
					
					int soluong =  rs.getInt("soluong");
			/*		double soluongthung = rs.getDouble("soluong")/quycach;*/
					
					number = new jxl.write.Number(0, row, stt);sheet.addCell(number);
					sheet.addCell(new Label(1, row, nppMa));
					sheet.addCell(new Label(2, row, nppTen));
					sheet.addCell(new Label(3, row, spMa));
					sheet.addCell(new Label(4, row, spTen));
					sheet.addCell(new Label(5, row, diengiai ));
					sheet.addCell(new Label(6, row,khoId ));
				
					
			/*		number = new jxl.write.Number(7, row, soluongthung);sheet.addCell(number);*/
					
					number = new jxl.write.Number(7, row,soluong );sheet.addCell(number);
					
		/*			number = new jxl.write.Number(9, row,quycach );sheet.addCell(number);
					sheet.addCell(new Label(10, row,solo ));
					sheet.addCell(new Label(11, row,ngayhethan ));*/
					row++;
					stt++;
				}
			}
			rs.close();
			w.write();
			w.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			return "Không có báo cáo trong thời gian đã chọn " + e.getMessage();
		}
		return "";
	}

	public static void main(String[] arg)
	{
		ImportTonKhoSvl p = new ImportTonKhoSvl();
		IUpload obj = new Upload();
		p.readExcel("", obj);
		// String str = "R3";
		// System.out.println(" khop " + str.matches("\\W"));
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
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
}

