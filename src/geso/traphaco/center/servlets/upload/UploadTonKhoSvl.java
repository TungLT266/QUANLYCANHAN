package geso.traphaco.center.servlets.upload;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.center.beans.upload.IUpload;
import geso.traphaco.center.beans.upload.imp.Upload;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import com.oreilly.servlet.MultipartRequest;

@WebServlet("/UploadTonKhoSvl")
public class UploadTonKhoSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "c:\\upload\\excel\\";
	PrintWriter out;

	public UploadTonKhoSvl()
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
		
		String id = util.getId(querystring);
		 String action = util.getAction(querystring);
		 System.out.println("_____"+action);
		if (action.equals("excel"))
		{
			obj.setMsg(TonKho_Details(response,id));
		}else if(action.equals("chot"))
		{
			dbutils db=new dbutils();
			String query="update tonkho set trangthai=1 where pk_seq='"+id+"'";
			if(!db.update(query))
			{
				System.out.println("Error"+query);
			}
		}else if(action.equals("unchot"))
		{
			dbutils db=new dbutils();
			String query="update tonkho set trangthai=0 where pk_seq='"+id+"' and trangthai=1";
			db.update(query);
		}else if(action.equals("delete"))
		{
			dbutils db=new dbutils();
			String query="delete from  tonkho_npp  where tonkho_fk='"+id+"' ";
			db.update(query);
			query="delete from tonkho where pk_seq='"+id+"'";
			db.update(query);
		}
		obj.createRs_Tonkho();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/UploadTonKho.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IUpload obj =null;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		if(userId == null) { userId = ""; }
		System.out.println("[UploadTonKhoSvl.doPost] userId = " + userId);

		String action=request.getParameter("action");
		if(action==null) { action=""; }
		System.out.println("[UploadTonKhoSvl.doPost] action = " + action);
		
		OutputStream out = response.getOutputStream();
		obj = new Upload();
		String thang=request.getParameter("thang");
		if(thang==null)
			thang="";
		obj.setThang(thang);

		String nam=request.getParameter("nam");
		if(nam==null)
			nam="";
		obj.setNam(nam);
		
		if (action.equals("Tao moi"))
		{
			
			try {
				System.out.println("parseInt = " + ((int)Float.parseFloat("0.0")));
			} catch(Exception e) {
				System.out.println("parseInt = " + e.getMessage());
			}
			
			IUpload bean = (IUpload) new Upload("");
			bean.setUserId(userId);
			
 			session.setAttribute("spnppStr", "");
			session.setAttribute("uploadBean", bean);
			
			String nextJSP = "/TraphacoHYERP/pages/Center/UploadTonKhoNew.jsp";
			response.sendRedirect(nextJSP);
			return;
		} else if(action.equals("excel"))
		{
			obj.setMsg(XuatFileExcel(response,obj));
		}
		else if(action.equals("excel"))
		{
			String search=
					"select a.pk_seq as tonkhoId,a.thang,a.nam,b.ten as nguoitao,a.ngaytao,a.trangthai "+ 
					"from tonkho a inner join nhanvien b "+
					"on b.pk_seq =a.nguoitao ";
			ToExcel(response, search);
		}		
		else if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			IUpload dmhBean = new Upload();
			dmhBean.setUserId(userId);
			dmhBean.setThang(multi.getParameter("thang"));
			dmhBean.setNam(multi.getParameter("nam"));
			Enumeration files = multi.getFileNames();
			String filename = "";
			System.out.println("__userId"+userId);
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			if (filename != null && filename.length() > 0)
				dmhBean.setMsg(	this.readExcel(UPLOAD_DIRECTORY + filename, dmhBean));
			obj = new Upload();
			obj.setUserId(userId);
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
		obj.createRs_Tonkho();
		session.setAttribute("userId", userId);
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/UploadTonKho.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String readExcel(String fileName, IUpload upload)
	{
		dbutils db = new dbutils();
		String query = "delete from TonKho_Tmp";
		db.update(query);

		query="select TrangThai from TonKho where thang='"+upload.getThang()+"' and nam='"+upload.getNam()+"' ";
		ResultSet rs=db.get(query);
		int trangthai=0;
		if(rs!=null)
		{
			try
			{
				while(rs.next())
				{
					trangthai=rs.getInt(1);
				}
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		if(trangthai==1)
		{
			return "Tồn kho tháng "+upload.getThang() +  " đã chốt !Vui lòng chọn thời gian khác ";
		}
		try 
		{
			InputStream ExcelFileToRead = new FileInputStream(fileName);
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
			int sosheet=wb.getNumberOfSheets();
			for(int ii=0;ii<sosheet;ii++)
			{
				XSSFSheet sheet = wb.getSheetAt(ii);
				Row row;
				Row rowFixed;
				Iterator rows = sheet.rowIterator();
				int rowIndex=0;
				rowFixed=sheet.getRow(0);
				int socot = rowFixed.getLastCellNum()+2;
				int soNpp = (socot - 4) / 5;
				System.out.println("[rows]" +socot+"[soNpp]"+soNpp+"[]"+rowFixed.getLastCellNum());
				while (rows.hasNext()) 
				{
					row =(Row)rows.next();
					for(int i=0; i<soNpp; i++) 
					{
						System.out.println("[npp]" +i);
						String spMa=getContent(row,1);
						String nppMa=getContent(rowFixed, 5+i*5);
						String tondau=getContent(row, 5+i*5).trim().replace(",", "");
						String nhap=getContent(row, 6+i*5).trim().replace(",", "");
						String toncuoi=getContent(row, 8+i*5).trim().replace(",", "");
						
						if(tondau.trim().length()<=0)
							tondau="0";
						if(nhap.length()<=0)
							nhap="0";
						if(toncuoi.length()<=0)
							toncuoi="0";
						
						if (spMa.length()>0&&nppMa.length()>0&&rowIndex>=2)
						{
							query = " insert into tonkho_tmp(nppMa,spMa,TonDau,TonCuoi,nhap)" +
									" select '" + nppMa + "',N'" + spMa + "','" + tondau + "','"+ toncuoi + "','"+nhap+"'";
							if (!db.update(query))
							{
								System.out.println("tmp" + query);
							}
						}
						System.out.println("[nppMa]"+nppMa+"[spMa]"+spMa+"[TonDau]"+tondau);
					}
					rowIndex++;
				}
			}
			upload.Stock();
		return upload.getMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String getContent(org.apache.poi.ss.usermodel.Row row, int column)
	{
		org.apache.poi.ss.usermodel.Cell cell = row.getCell(column,Row.CREATE_NULL_AS_BLANK);
		return cell.toString();
	}

	public static void main(String[] arg)
	{
		
	}
	
	
	
	
	private String TonKho_Details(HttpServletResponse response,String  id) throws IOException 
	{
		NumberFormat formatter = new DecimalFormat("#,###,###");
		try 
		{
		   response.setContentType("application/vnd.ms-excel");
		   response.setHeader("Content-Disposition", "attachment; filename=TonKho_" +getDateTime()+"");
		   
		   dbutils db=new dbutils();
		   String sql=
			"	   select   tt.PK_SEQ as TongThauId,tt.MA as TongThauMa,tt.TEN as TongThau,a.PK_SEQ as TonKhoId,d.MA as spMa,d.pk_seq as spId,d.TEN as spTen,c.pk_seq as nppId,c.MA as nppMa,c.TEN as nppTen,a.ThoiGian,b.TonDau,b.Nhap,b.Ban,b.TonCuoi "+ 
			"		from TonKho a inner join TonKho_NPP b on b.TonKho_FK=a.PK_SEQ "+ 
			"		inner join NHAPHANPHOI c  on c.PK_SEQ=b.NPP_FK  "+
			"		inner join NHAPHANPHOI tt on tt.PK_SEQ=c.TongThau_FK "+
			"		inner join SANPHAM d on d.PK_SEQ=b.SanPham_FK "+ 
			"	where a.PK_SEQ='"+id+"'  "+
			"	order by tt.MA,d.MA,c.MA " ; 
		  System.out.println("TonKho_Details : "+sql);
		  XSSFWorkbook workbook = new XSSFWorkbook();
		   ResultSet rs=db.get(sql);
		   int rowIndex=1;
		   int col=0;
		   int stt=0;
		   String nppIdPrev="";
		   String spPrev="";
		   String tongthauPrev="";
		   XSSFSheet sheet=null;
		   Row  row0 =null ;
		   Row  row1=null;
		   
		   Font f = workbook.createFont();
		   f.setFontHeightInPoints((short) 12);
		   f.setColor( (short)Font.COLOR_NORMAL );
		   f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		   
		   Font f2 = workbook.createFont();
		   f2.setFontHeightInPoints((short) 12);
		   f2.setColor( (short)Font.COLOR_RED );
		   f2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		   
		   Font f3 = workbook.createFont();
		   f.setFontHeightInPoints((short) 12);
		   f.setColor( (short)Font.COLOR_NORMAL );
		   f.setBoldweight(Font.COLOR_NORMAL);
		   
		   		   
		   
		   CellStyle cs = workbook.createCellStyle();
		   cs.setFont(f);
		   cs.setBorderBottom((short) 1);
		   cs.setBorderLeft((short) 1);
		   cs.setBorderRight((short) 1);
		   cs.setBorderTop((short) 1);
		   

		   CellStyle cs2 = workbook.createCellStyle();
		   cs2.setBorderBottom(cs2.BORDER_THIN);
		   cs2.setFillPattern((short) CellStyle.SOLID_FOREGROUND);
		   cs2.setFont(f2);
		   
		   
		   CellStyle cs3 = workbook.createCellStyle();
		   cs3.setBorderBottom((short) 1);
		   cs3.setBorderLeft((short) 1);
		   cs3.setBorderRight((short) 1);
		   cs3.setBorderTop((short) 1);
		   cs3.setFont(f3);
		   Cell cell;
		   Row   row =null;
		   if(rs!=null)
		   {
			   while (rs.next())
			   {
				   	String tongthauId=rs.getString("tongthauId");
				   
				   if(!tongthauPrev.equals(tongthauId))
				   {
					   sheet = workbook.createSheet(rs.getString("tongthauMa"));
					   tongthauPrev=tongthauId;
					   rowIndex=1;
					   col=0;
					   stt=0;
					   nppIdPrev="";
					   spPrev="";
					   
					   sheet.createRow(0);
					   sheet.createRow(1);					   
					   cell=sheet.getRow(1).createCell(0);cell.setCellStyle(cs);cell.setCellValue("STT");
					   cell=sheet.getRow(1).createCell(1);cell.setCellStyle(cs);cell.setCellValue("Mã Sản phẩm");
					   cell=sheet.getRow(1).createCell(2);cell.setCellStyle(cs);cell.setCellValue("Tên sản phẩm");
					   cell=sheet.getRow(1).createCell(3);cell.setCellStyle(cs);cell.setCellValue("Tên chuẩn");
				   }
				  
				   String nppId=rs.getString("nppId");
				   String nppMa=rs.getString("nppMa");
				   String nppTen=rs.getString("nppTen");
				   String spId=rs.getString("spId");
				   String spMa=rs.getString("spMa");
				   String spTen=rs.getString("spTen");
				   
				   String tondau=rs.getString("tondau")==null?"": formatter.format(rs.getDouble("tondau"));
				   String nhap=rs.getString("nhap")==null?"":formatter.format(rs.getDouble("nhap"));
				   String ban=rs.getString("ban")==null?"":formatter.format(rs.getDouble("ban"));
				   String toncuoi=rs.getString("toncuoi")==null?"":formatter.format(rs.getDouble("toncuoi"));
				   
				   if(!spPrev.equals(spId))
				   {
					   rowIndex++;
					   row = sheet.createRow(rowIndex);
					   col=0;
					   stt++;
					   spPrev=spId;			
				   }
				 
				   
				   cell=row.createCell(0);cell.setCellValue(stt);cell.setCellStyle(cs3);
				   cell=row.createCell(1);cell.setCellValue(spMa);cell.setCellStyle(cs3);
				   cell=row.createCell(2);cell.setCellValue(spTen);cell.setCellStyle(cs3);
				   
				   
				   cell=row.createCell(5+col*5);cell.setCellValue(tondau);cell.setCellStyle(cs3);
				   cell=row.createCell(6+col*5);cell.setCellValue(nhap);cell.setCellStyle(cs3);
				   cell=row.createCell(7+col*5);cell.setCellValue(ban);cell.setCellStyle(cs3);
				   cell=row.createCell(8+col*5);cell.setCellValue(toncuoi);cell.setCellStyle(cs3);
				 
				   if(!nppIdPrev.equals(nppId))
				   {
					   nppIdPrev=nppId;
					   
					   cell=sheet.getRow(0).createCell(5+col*5);cell.setCellStyle(cs2);cell.setCellValue(nppMa);
					   sheet.getRow(0).createCell(6+col*5).setCellValue(nppTen);
					 
					   sheet.getRow(1).createCell(5+col*5).setCellValue("Tồn đầu");
					   sheet.getRow(1).createCell(6+col*5).setCellValue("Nhập");
					   sheet.getRow(1).createCell(7+col*5).setCellValue("Bán");
					   sheet.getRow(1).createCell(8+col*5).setCellValue("Tồn cuối");
					   col++;
				   }
				}
		   }
	   	   rs.close();
	   	   OutputStream out = response.getOutputStream();
	       workbook.write(out);
	       out.close();
		  }
		  catch (Exception e) 
		  {
			  e.printStackTrace();
			  return "Không có báo cáo trong thời gian đã chọn !";
		  }
		return "";
	}

	
	
	private String XuatFileExcel(HttpServletResponse response,IUpload obj) throws IOException 
	{
		String thoigian=obj.getNam()+'-'+obj.getThang()+"-01";
		if(obj.getThang().length()<=1)
		{
			thoigian=obj.getNam()+"-0"+obj.getThang()+"-01";
		}
		NumberFormat formatter = new DecimalFormat("#,###,###");
		try {
		   response.setContentType("application/vnd.ms-excel");
		   response.setHeader
		     ("Content-Disposition", "attachment; filename=TonKho_" +getDateTime()+".xlsx");
		   dbutils db=new dbutils();
		   String sql=
		   "	select  dulieu.tongthauId,dulieu.tongthauMa,dulieu.TongThau,dulieu.nppId,dulieu.nppMa,dulieu.nppTen,dulieu.spMa,dulieu.spTen,tk.TonDau,tk.Nhap,tk.Ban,tk.TonCuoi,dulieu.spId,dulieu.spMa,dulieu.spTen \n  "  + 
		   "	from \n  "  + 
		   "	(\n  "  + 
		   "		select npp.*,sp.* \n  "  + 
		   "		from \n  "  + 
		   "		(\n  "  + 
		   "			select tt.PK_SEQ as tongthauId,tt.MA as tongthauMa,tt.TEN as TongThau,npp.MA as nppMa,npp.PK_SEQ as nppId,npp.TEN as nppTen \n  "  + 
		   "			from NHAPHANPHOI npp\n  "  + 
		   "				inner join NhaPhanPhoi tt on npp.TongThau_FK=tt.PK_SEQ\n  "  + 
		   "			where npp.PK_SEQ in ( select npp_fk from NHAPP_KBH where KBH_FK=100052 )and npp.TongThau_FK is not null and npp.trangthai=1 and tt.trangthai=1 " +
		   "    \n  "  + 
		   "		) as npp,\n  "  + 
		   "		(\n  "  + 
		   "			select PK_SEQ as spId,MA as spMa,ten as spTen  from SANPHAM where trangthai=1 \n  "  + 
		   "		)as sp \n  "  + 
		   "	) as dulieu\n  "  + 
		   "	left join \n  "  + 
		   "	(\n  "  + 
		   "		select b.NPP_FK,b.SanPham_FK,null as TonCuoi, null as Nhap, null as  Ban,TonCuoi as TonDau  \n  "  + 
		   "		from TonKho a inner join TonKho_NPP b on a.PK_SEQ=b.TonKho_FK     \n" +
		   "	where ThoiGian=(select convert(varchar(10),DATEADD(month,-1,'"+thoigian+"'),20) ) \n" +
		   "	)as tk on dulieu.nppId=tk.NPP_FK and tk.SanPham_FK=dulieu.spId\n  "  + 
		   "	order by dulieu.tongthauMa,dulieu.spMa,dulieu.nppMa\n  "     ; 
		   System.out.println("TonKho_Details : "+sql);
			  XSSFWorkbook workbook = new XSSFWorkbook();
			   ResultSet rs=db.get(sql);
			   int rowIndex=1;
			   int col=0;
			   int stt=0;
			   String nppIdPrev="";
			   String spPrev="";
			   String tongthauPrev="";
			   XSSFSheet sheet=null;
			   Row  row0 =null ;
			   Row  row1=null;
			   
			   Font f = workbook.createFont();
			   f.setFontHeightInPoints((short) 12);
			   f.setColor( (short)Font.COLOR_NORMAL );
			   f.setBoldweight(Font.BOLDWEIGHT_BOLD);
			   
			   Font f2 = workbook.createFont();
			   f2.setFontHeightInPoints((short) 12);
			   f2.setColor( (short)Font.COLOR_RED );
			   f2.setBoldweight(Font.BOLDWEIGHT_BOLD);
			   
			   Font f3 = workbook.createFont();
			   f.setFontHeightInPoints((short) 12);
			   f.setColor( (short)Font.COLOR_NORMAL );
			   f.setBoldweight(Font.COLOR_NORMAL);
			   
			   		   
			   
			   CellStyle cs = workbook.createCellStyle();
			   cs.setFont(f);
			   cs.setBorderBottom((short) 1);
			   cs.setBorderLeft((short) 1);
			   cs.setBorderRight((short) 1);
			   cs.setBorderTop((short) 1);
			   

			   CellStyle cs2 = workbook.createCellStyle();
			   cs2.setBorderBottom(cs2.BORDER_THIN);
			   cs2.setFillPattern((short) CellStyle.SOLID_FOREGROUND);
			   cs2.setFont(f2);
			   
			   
			   CellStyle cs3 = workbook.createCellStyle();
			   cs3.setBorderBottom((short) 1);
			   cs3.setBorderLeft((short) 1);
			   cs3.setBorderRight((short) 1);
			   cs3.setBorderTop((short) 1);
			   cs3.setFont(f3);
			   Cell cell;
			   Row   row =null;
			   if(rs!=null)
			   {
				   while (rs.next())
				   {
					   	String tongthauId=rs.getString("tongthauId");
					   
					   if(!tongthauPrev.equals(tongthauId))
					   {
						   sheet = workbook.createSheet(rs.getString("tongthauMa"));
						   tongthauPrev=tongthauId;
						   rowIndex=1;
						   col=0;
						   stt=0;
						   nppIdPrev="";
						   spPrev="";
						   
						   sheet.createRow(0);
						   sheet.createRow(1);					   
						   cell=sheet.getRow(1).createCell(0);cell.setCellStyle(cs);cell.setCellValue("STT");
						   cell=sheet.getRow(1).createCell(1);cell.setCellStyle(cs);cell.setCellValue("Mã Sản phẩm");
						   cell=sheet.getRow(1).createCell(2);cell.setCellStyle(cs);cell.setCellValue("Tên sản phẩm");
						   cell=sheet.getRow(1).createCell(3);cell.setCellStyle(cs);cell.setCellValue("Tên chuẩn");
					   }
					  
					   String nppId=rs.getString("nppId");
					   String nppMa=rs.getString("nppMa");
					   String nppTen=rs.getString("nppTen");
					   String spId=rs.getString("spId");
					   String spMa=rs.getString("spMa");
					   String spTen=rs.getString("spTen");
					   
					   String tondau=rs.getString("tondau")==null?"": formatter.format(rs.getDouble("tondau"));
					   String nhap=rs.getString("nhap")==null?"":formatter.format(rs.getDouble("nhap"));
					   String ban=rs.getString("ban")==null?"":formatter.format(rs.getDouble("ban"));
					   String toncuoi=rs.getString("toncuoi")==null?"":formatter.format(rs.getDouble("toncuoi"));
					   
					   if(!spPrev.equals(spId))
					   {
						   rowIndex++;
						   row = sheet.createRow(rowIndex);
						   col=0;
						   stt++;
						   spPrev=spId;			
					   }
					 
					   
					   cell=row.createCell(0);cell.setCellValue(stt);cell.setCellStyle(cs3);
					   cell=row.createCell(1);cell.setCellValue(spMa);cell.setCellStyle(cs3);
					   cell=row.createCell(2);cell.setCellValue(spTen);cell.setCellStyle(cs3);
					   
					   
					   cell=row.createCell(5+col*5);cell.setCellValue(tondau);cell.setCellStyle(cs3);
					   cell=row.createCell(6+col*5);cell.setCellValue(nhap);cell.setCellStyle(cs3);
					   cell=row.createCell(7+col*5);cell.setCellValue(ban);cell.setCellStyle(cs3);
					   cell=row.createCell(8+col*5);cell.setCellValue(toncuoi);cell.setCellStyle(cs3);
					 
					   if(!nppIdPrev.equals(nppId))
					   {
						   nppIdPrev=nppId;
						   
						   cell=sheet.getRow(0).createCell(5+col*5);cell.setCellStyle(cs2);cell.setCellValue(nppMa);
						   sheet.getRow(0).createCell(6+col*5).setCellValue(nppTen);
						 
						   sheet.getRow(1).createCell(5+col*5).setCellValue("Tồn đầu");
						   sheet.getRow(1).createCell(6+col*5).setCellValue("Nhập");
						   sheet.getRow(1).createCell(7+col*5).setCellValue("Bán");
						   sheet.getRow(1).createCell(8+col*5).setCellValue("Tồn cuối");
						   col++;
					   }
					}
			   }
		   	   rs.close();
		   	   OutputStream out = response.getOutputStream();
		       workbook.write(out);
		       out.close();
		   }
		  catch (Exception e) 
		  {
			  e.printStackTrace();
		  }
		return "";
	}
	
	private void ToExcel(HttpServletResponse response, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=UploadTonKhoStore.xlsx");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("UploadTonKhoStore", k);
			sheet.addCell(new Label(0, 1, "Upload Tồn kho Store : ", new WritableCellFormat(cellTitle)));

			sheet.addCell(new Label(0, 2, "Ngày tạo: "));
			sheet.addCell(new Label(1, 2, "" + getDateTime()));

			sheet.addCell(new Label(2, 4, "Đơn vị tiền tệ:VND"));

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.LIME);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.LIGHT_ORANGE);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "THÁNG", cellFormatSpecical));
			sheet.addCell(new Label(2, 4, "NĂM", cellFormat));
			sheet.addCell(new Label(3, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(4, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(5, 4, "TRẠNG THÁI", cellFormat));
			sheet.setRowView(100, 4);

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 30);
			sheet.setColumnView(3, 25);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 15);
			sheet.setColumnView(7, 35);
			sheet.setColumnView(8, 15);
			sheet.setColumnView(9, 15);
			sheet.setColumnView(10, 15);
			sheet.setColumnView(11, 15);
			sheet.setColumnView(12, 15);
			sheet.setColumnView(13, 15);
			sheet.setColumnView(14, 60);
			dbutils db = new dbutils();

			ResultSet rs = db.get(query);

			WritableCellFormat cellFormat2 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormat3 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cformat = null;
			Label label;
			Number number;
			int stt=0;
			while (rs.next())
			{
		
				cformat = cellFormat2 ;
				stt++;
				number = new Number(0, j, stt, cformat);sheet.addCell(number);
				label = new Label(1, j, rs.getString("thang"), cformat);sheet.addCell(label);
				label = new Label(2, j, rs.getString("nam"), cformat);sheet.addCell(label);				
				label = new Label(8, j, rs.getString("NguoiTao"), cformat);sheet.addCell(label);
				label = new Label(9, j, rs.getString("NgayTao"), cformat);sheet.addCell(label);
				label = new Label(7, j, rs.getInt("trangthai") == 0 ? "Chờ duyệt" : "Đã duyệt", cformat);sheet.addCell(label);				
				j++;
			}
			w.write();
			w.close();
			rs.close();
			db.shutDown();
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out != null)
				out.close();

		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}