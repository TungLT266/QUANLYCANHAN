package geso.traphaco.erp.servlets.denghimuahang;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.denghimuahang.IErpDenghimuahang;
import geso.traphaco.erp.beans.denghimuahang.IErpDenghimuahangList;
import geso.traphaco.erp.beans.denghimuahang.IErpDuyetdenghimuahang;
import geso.traphaco.erp.beans.denghimuahang.imp.ErpDenghimuahang;
import geso.traphaco.erp.beans.denghimuahang.imp.ErpDenghimuahangList;
import geso.traphaco.erp.beans.denghimuahang.imp.ErpDuyetdenghimuahang;
import geso.traphaco.erp.beans.donmuahang.INgaynhan;
import geso.traphaco.erp.beans.donmuahang.ISanpham;
import geso.traphaco.erp.beans.donmuahang.imp.Ngaynhan;
import geso.traphaco.erp.beans.donmuahang.imp.Sanpham;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.oreilly.servlet.MultipartRequest;

public class ErpDuyetdenghimuahangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpDuyetdenghimuahangUpdateSvl()
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nextJSP;
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			String id = util.getId(querystring);
			IErpDenghimuahang dmhBean = new ErpDenghimuahang(id);
			dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
			dmhBean.setUserId(userId); // phai co UserId truoc khi Init
			
			String task = request.getParameter("task");
			if(task == null)
				task = "";
			System.out.print("Task " + task + " id " + id);
			String canduyet = request.getParameter("duyet");
			if(canduyet == null)
				canduyet = "1";
			dmhBean.setCanDuyet(canduyet);
			
			if(task.equals("print"))
			{
				System.out.println("AAAAAAAAAAAAAAAAAAAAAAA");
				Create_DN_PDF(response, request);
			}
			else
			{
				dmhBean.init();
				
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiMuaHangUpdate.jsp";
				
				session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
				session.setAttribute("lspId", dmhBean.getLoaispId());
				session.setAttribute("dmhBean", dmhBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
	private boolean deletefile(String file)
	{
		System.out.println(file);
		File f1 = new File(file);
		boolean success = f1.delete();
		if (!success)
		{
			return false;
		} else
		{
			return true;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		String contentType = request.getContentType();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\dinhkem\\", 20000000, "UTF-8");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			session.setMaxInactiveInterval(30000);
			
			IErpDenghimuahang dmhBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(multi.getParameter("id"));
			System.out.println("ID" + id);
			if (id == null)
			{
				dmhBean = new ErpDenghimuahang("");
			}
			else
			{
				dmhBean = new ErpDenghimuahang(id);
			}
			String contyId = (String)session.getAttribute("congtyId");
			
			session.setAttribute("ctyId", contyId);
			dmhBean.setCongtyId(contyId);
			dmhBean.setUserId(userId);
			
			String canduyet = multi.getParameter("duyet");
			if(canduyet == null)
				canduyet = "1";
			dmhBean.setCanDuyet(canduyet);
			
			String ngaygd = util.antiSQLInspection(multi.getParameter("ngaydenghi"));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();
			dmhBean.setNgaydenghi(ngaygd);
			
			String maDMH = util.antiSQLInspection(multi.getParameter("mamuahang"));
			if (maDMH == null)
				maDMH = "";
			dmhBean.setmaDMH(maDMH);
			
			String dvth = util.antiSQLInspection(multi.getParameter("dvthId"));
			if (dvth == null)
				dvth = "";
			dmhBean.setDvthId(dvth);

			String loaihh = util.antiSQLInspection(multi.getParameter("loaihh"));
			if (loaihh == null)
				loaihh = "";
			session.setAttribute("lhhId", loaihh);
			dmhBean.setLoaihanghoa(loaihh);
			 
			System.out.println("LOAI HANG HOA : "+loaihh);
			
			String loaisp = util.antiSQLInspection(multi.getParameter("loaisp"));
			if (loaisp == null)
				loaisp = "";
			session.setAttribute("lspId", loaisp);
			dmhBean.setLoaispId(loaisp);
			
			String NguonGocHH = util.antiSQLInspection(multi.getParameter("nguongoc"));
			if (NguonGocHH == null)
				NguonGocHH = "";
			dmhBean.setNguonGocHH(NguonGocHH);
			
			String ghichu = util.antiSQLInspection(multi.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";
			dmhBean.setGhiChu(ghichu);
			
			String tiente_fk = util.antiSQLInspection(multi.getParameter("tiente_fk"));
			if (tiente_fk == null)
			{
				tiente_fk = "";
			}
			dmhBean.setTienTe_FK(tiente_fk);
			
			String nguongoc = util.antiSQLInspection(multi.getParameter("nguongoc"));
			dmhBean.setNguonGocHH(nguongoc);
			
			String[] idsp = multi.getParameterValues("idsp");
			String[] mnlId = multi.getParameterValues("mnlId");
			String[] masp = multi.getParameterValues("masp");
			String[] tensp = multi.getParameterValues("tensp");
			String[] sl = multi.getParameterValues("soluong");
			String[] slduyet = multi.getParameterValues("soluongduyet");
			String[] donvitinh = multi.getParameterValues("donvitinh");
			String[] tenhoadon = multi.getParameterValues("tenhoadon");
			String action = multi.getParameter("action");
			List<ISanpham> spList = new ArrayList<ISanpham>();
			boolean ktDonvi = true;
			
			ISanpham sp = null; String tenhd = "";
			if(masp != null)
			{
			for(int i = 0; i < masp.length; i++)
			{
				//Loại hàng hóa chi phí dịch vụ (2) -> không cần nhập mã nên chỉ cần kiểm tra tên
				if(((loaihh.equals("2") ||loaihh.equals("3")||loaihh.equals("1")) && tensp[i].trim().length() > 0) || masp[i].trim().length() > 0)
				{
					String[] soluong = multi.getParameterValues("sub_soluong_" + i);
					
					sp = new Sanpham();
					sp.setPK_SEQ(idsp[i]);
					sp.setMNLId(mnlId[i]);
					sp.setMasanpham(masp[i]);
					sp.setTensanpham(tensp[i]);
					sp.setSoluong(sl[i].replaceAll(",", ""));
					sp.setSoluongduyet(slduyet[i].replaceAll(",", ""));
					sp.setDonvitinh(donvitinh[i]);
					
					//Tùy chọn tên sản phẩm in hóa đơn
					tenhd = ""; try { tenhd = tenhoadon[i].trim(); } catch(Exception e) { }
					sp.setTenHD(tenhd);
					
					
					if(donvitinh[i].trim().length() == 0 || donvitinh[i].equals("NA")) ktDonvi = false;
					//sp.setNhomhang(nhomhang[i]);
					
					spList.add(sp);
				}
			}
			}
			dmhBean.setSpList(spList);
			
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
			{
				Enumeration files = multi.getFileNames();
				String filename = "";
				while (files.hasMoreElements())
				{
					String name = (String) files.nextElement();
					filename = multi.getFilesystemName(name);
				}
				
				if (filename == null || filename.trim().length() <= 0)
				{
					filename = multi.getParameter("congvan");
				}
				
				dmhBean.setCongvan(filename);
				System.out.println("1____READ EXCEL TOI DAY, FILE NAME......" + filename);
				
			}
			if (action.equals("save") )
			{
				if(ktDonvi == false){
					dmhBean.setMsg("Vui lòng nhập Đơn vị tính");
					dmhBean.createRs();
					String nextJSP;
					
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiMuaHangUpdate.jsp";
					
					session.setAttribute("dmhBean", dmhBean);
					response.sendRedirect(nextJSP);				
					return;
				}

				
				if (!dmhBean.updateDmh())
				{
					dmhBean.createRs();
					session.setAttribute("dmhBean", dmhBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiMuaHangUpdate.jsp";
					response.sendRedirect(nextJSP);
					return;
				}
				else
				{

				    IErpDuyetdenghimuahang ddmhBean = new ErpDuyetdenghimuahang();
			   	    ddmhBean.setCongtyId((String)session.getAttribute("congtyId"));
			   	    ddmhBean.setUserId(userId);
			   	    System.out.println("Cty "+(String)session.getAttribute("congtyId"));
			   	 
			   	    ddmhBean.init();
			   		session.setAttribute("ddmhBean", ddmhBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiMuaHang.jsp";
			   		response.sendRedirect(nextJSP);

					return;
				}
				
			}
			else
			{
				if (action.equals("download") )
				{
					System.out.println("___Vao DOWNLOAD FILE....");
					String fileName = multi.getParameter("congvan");
					if (fileName == null)
						fileName = "";
					
					if (fileName.trim().length() > 0)
					{
						try
						{
							FileInputStream fileToDownload = new FileInputStream("C:\\java-tomcat\\dinhkem\\" + fileName);
							ServletOutputStream output = response.getOutputStream();
							response.setContentType("application/octet-stream");
							
							System.out.println(fileName);
							response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
							response.setContentLength(fileToDownload.available());
							int c;
							while ((c = fileToDownload.read()) != -1)
							{
								output.write(c);
							}
							output.flush();
							output.close();
							fileToDownload.close();
						} catch (Exception e)
						{
							System.out.println("___Loi DOWNLOAD file: " + e.getMessage());
						}
					} 
				}
				else
				{
				dmhBean.createRs();
				
				String nextJSP;
				
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiMuaHangUpdate.jsp";
				
				session.setAttribute("dmhBean", dmhBean);
				response.sendRedirect(nextJSP);
			}
			}
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private String getEnDateTime(String date) {
		if(date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			
			thang = thang.equals("01") ? "Jan" : thang.equals("02") ? "Feb" : thang.equals("03") ? "Mar" : thang.equals("04") ? "Apr" : thang.equals("05") ? "May" : thang.equals("06") ? "Jun" : thang.equals("07") ? "Jul" : thang.equals("08") ? "Aug" : thang.equals("09") ? "Sep" : thang.equals("10") ? "Oct" : thang.equals("11") ? "Nov" : thang.equals("12") ? "Dec" : " ";
			return thang + " " + ngay + ", " + nam;
		} else {
			return date;
		} 
	}
	
	private String getVnDateTime(String date) {
		if(date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			return ngay + "-" + thang + "-" + nam;
		} else {
			return date;
		}
	}
	
	public static void main(String[] arg)
	{
		ErpDuyetdenghimuahangUpdateSvl dmh = new ErpDuyetdenghimuahangUpdateSvl();
		dmh.removeFONT("");
	}
	
	public String removeFONT(String text)
	{
		String kq = "<font size=\"2\">Pha 2-GĐ TKTD kết thúc ngày 21/8/2014.<br></font>awdakd askjndfka<font size=\"2\" style='color:red' >TEST DONG THU 2</font>";
		
		String[] arr = kq.split("</font>");
		for(int pos = 0; pos < arr.length; pos ++)
		{
			//TIM VI TRI BAT DAU CUA FONT
			int index = arr[pos].indexOf("<font ");
			
			//TIM VI TRI KET THUC CUA FONT
			if(index >= 0)
			{
				int index_END = 0;
				for(int i = index; i < arr[pos].length() - 1; i++)
				{
					if( arr[pos].substring(i, i + 1).equals(">") )
					{
						index_END = i;
						break;
					}
				}
				
				//TIM KY TU </font>
				//int index_END2 = kq.indexOf("</font>");
				//kq = kq.substring(index_END + 1, index_END2).trim();
				
				System.out.println("---KQ TRUOC: " + arr[pos] );
				
				if(index > 0)
					arr[pos] = arr[pos].substring(index_END + 1, arr[pos].length());
				else
					arr[pos] = arr[pos].substring(0, index) +  arr[pos].substring(index_END + 1, arr[pos].length());
				
				System.out.println("---KQ CHO NAY: " + arr[pos] );
				
			}
		}
		
		kq = "";
		for(int pos = 0; pos < arr.length; pos ++)
		{
			kq += arr[pos];
		}
		
		System.out.println("Ket qua la: " + kq);
		return kq;
	}
	
	private void Create_DN_PDF(HttpServletResponse response, HttpServletRequest request) 
	{
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," inline; filename=DeNghiMuaHang.pdf");
		
		float CONVERT = 28.346457f;
		float PAGE_LEFT = 2.0f*CONVERT, PAGE_RIGHT = 1.5f*CONVERT, PAGE_TOP = 0.5f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
		Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		ServletOutputStream outstream;
		try 
		{
			outstream = response.getOutputStream();
			
			String id = request.getParameter("id");
			dbutils db = new dbutils();
			
			this.CreateDN(document, outstream, response, request, db);
			
			//db.update("Update ERP_MuaHang set DaInPdf = 1 where pk_seq = '" + id + "'");
			db.shutDown();
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("___Exception PO Print: " + e.getMessage());
		}
	}
	
	private void CreateDN(Document document, ServletOutputStream outstream, HttpServletResponse response, HttpServletRequest request, dbutils db) 
	{
		HttpSession session = request.getSession();
		
		String id = request.getParameter("id");
		String ctyId = (String)session.getAttribute("congtyId");
		
		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter_2sole = new DecimalFormat("#,###,###.##"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			float CONVERT = 28.346457f; // = 1cm
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			Font font13bold = new Font(bf, 13, Font.BOLD);
			Font font14bold = new Font(bf, 14, Font.BOLD);
			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font16bold = new Font(bf, 16, Font.BOLD);
			Font font20bold = new Font(bf, 20, Font.BOLD);
			Font font6green = new Font(bf, 6, Font.NORMAL, BaseColor.GREEN);
			Font font8 = new Font(bf, 8, Font.NORMAL);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font13 = new Font(bf, 13, Font.NORMAL);
			Font font14 = new Font(bf, 14, Font.NORMAL);
			Font font11italic = new Font(bf, 11, Font.ITALIC);
			Font font11boldItalic = new Font(bf, 11, Font.BOLDITALIC);
			Font font12boldItalic = new Font(bf, 12, Font.BOLDITALIC);
			Font font11underline = new Font(bf, 11, Font.UNDERLINE);
			
			/********************** INFO CONGTY *******************************/
						
			PdfPTable tbHeader = new PdfPTable(4);
			tbHeader.setWidthPercentage(100);
			tbHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] crHeader = {70f, 110f, 110f, 110f};
			tbHeader.setWidths(crHeader);
			tbHeader.getDefaultCell().setBorder(0);
			
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/logomerap.png");
			hinhanh.scalePercent(1);
			
			PdfPCell _celllogo = new PdfPCell(hinhanh);
			_celllogo.setBorder(0);
			_celllogo.setHorizontalAlignment(Element.ALIGN_LEFT);
			_celllogo.setVerticalAlignment(Element.ALIGN_CENTER);
			_celllogo.setPaddingTop(5.0f);
			_celllogo.setRowspan(3);
			tbHeader.addCell(_celllogo);

			PdfPCell cell = new PdfPCell(new Paragraph("HO CHI MINH OFFICE", font6green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("HA NOI OFFICE", font6green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("DA NANG OFFICE", font6green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("No. 436-438, Cao Thang Str., Dist. 10", font6green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("No. 12 H1 Yen Hoa., Cau Giay Dist.", font6green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("No. 416 Nguyen Huu Tho Str., Cam Le Dist.", font6green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Tel: 08.62651638 - Fax: 08.38630394", font6green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Tel: 04.37833848 - Fax : 04.37833846", font6green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Tel : 0511.3688899 - Fax : 0511.3688898", font6green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			
			tbHeader.addCell(cell);
			
			document.add(tbHeader);
			
			/********************** END INFO CONGTY *******************************/
			String query = " select a.PK_SEQ as dmhId, a.LOAIHANGHOA_FK," +
					" a.NGAYdenghi, c.ten, a.sodenghi " +
					"  from ERP_denghiMUAHANG a " +
					"  inner join ERP_DONVITHUCHIEN c on c.PK_SEQ = a.DONVITHUCHIEN_FK  " +
					" where a.pk_seq = '" +id + "' " ;
			
			System.out.println("De nghi Mua Hang : " + query);
			String ma = "", dvth = "", ngaydenghi = "", lhhId = "";
			ResultSet rs = db.get(query);
			if (rs != null)
			{
				try
				{
					while (rs.next())
					{
						ma = rs.getString("sodenghi");
						ngaydenghi = rs.getString("ngaydenghi");
						dvth = rs.getString("ten");
						lhhId = rs.getString("LOAIHANGHOA_FK");
					}
					rs.close();
				}
				catch (Exception e)
				{
					System.out.println("__Exception: " + e.getMessage());
				}
			}
			
			PdfPTable tbTieude = new PdfPTable(1);
			tbTieude.setWidthPercentage(100);
			tbTieude.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbTieude.getDefaultCell().setBorder(0);
			tbTieude.setSpacingBefore(20f);
			
			cell = new PdfPCell(new Paragraph("ĐỀ NGHỊ MUA HÀNG", font20bold));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbTieude.addCell(cell);
			
			document.add(tbTieude);
			
			PdfPTable tbNgaythang = new PdfPTable(2);
			tbNgaythang.setWidthPercentage(40);
			tbNgaythang.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbNgaythang.getDefaultCell().setBorder(0);
			tbNgaythang.setSpacingBefore(20f);
			
			cell = new PdfPCell(new Paragraph("Ngày đề nghị", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbNgaythang.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(ngaydenghi, font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbNgaythang.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Bộ phận đề nghị", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbNgaythang.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(dvth, font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbNgaythang.addCell(cell);
			
			document.add(tbNgaythang);
			
			/********************** INFO DENGHI *******************************/
			
			PdfPTable tbDenghi = new PdfPTable(7);
			tbDenghi.setWidthPercentage(100);
			tbDenghi.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbDenghi.getDefaultCell().setBorder(0);
			tbDenghi.setSpacingBefore(20f);
			float[] crDenghi = {20f, 90f, 90f, 40f, 40f, 40f, 80f};
			tbDenghi.setWidths(crDenghi);
			
			cell = new PdfPCell(new Paragraph("STT", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("DIỄN GIẢI NHU CẦU MUA HÀNG", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("CHI TIẾT KỸ THUẬT - THÔNG SỐ SẢN PHẨM", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("ĐƠN VỊ", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("SỐ LƯỢNG", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("TÌNH TRẠNG TỒN KHO", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("ĐỀ XUẤT KHÁC", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbDenghi.addCell(cell);
			
			query =  "select  isnull(b.pk_seq,0) as spid,  isnull(b.ma, '' ) as spMa,  isnull(a.diengiai, b.ten )   as spTen,   "
					+ "b.ten    as spTen2, 'NA' as spNh,  isnull(tscd.pk_seq,0) as tscdid, isnull(tscd.ma, '') as tscdMa, isnull(a.diengiai, tscd.ten) as tscdTen, "
					+ "isnull(nts.ma, 'NA') as nstNh,   isnull(ccdc.pk_seq,0) as ccdcid, isnull(ccdc.ma, '') as ccdcMa, isnull(a.diengiai, ccdc.DIENGIAI) as ccdcTen,   "
					+ "isnull(ncp.pk_seq,0) as ncpid,isnull(ncp.ten, '') as ncpMa, isnull(a.diengiai, ncp.diengiai) as ncpTen, isnull(ttcp.diengiai, 'NA') as ncpNh,  "
					+ "isnull(a.donvi, '') as donvi, a.soluong from (	select AVG(PK_SEQ) AS PK_SEQ, denghi_FK, SANPHAM_FK, CHIPHI_FK, "
					+ "TAISAN_FK, DIENGIAI, SUM(SOLUONG) AS SOLUONG, DONVI, CCDC_FK	"
					+ "from ERP_denghiMUAHANG_SP WHERE denghi_FK = '"+id+"' 	"
					+ "GROUP BY denghi_FK, SANPHAM_FK, CHIPHI_FK, TAISAN_FK, DIENGIAI, DONVI, CCDC_FK ) a left join  SANPHAM b on a.sanpham_fk = b.pk_seq    "
					+ "left join	erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq   left join erp_nhomtaisan nts on tscd.NhomTaiSan_fk = nts.pk_seq    "
					+ "left join	erp_congcudungcu ccdc on a.ccdc_fk = ccdc.pk_seq   "
					+ "left join erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq  left join  erp_trungtamchiphi ttcp on ncp.ttchiphi_fk = ttcp.pk_seq   "
					+ "where denghi_fk = '"+id+"' order by a.pk_seq asc ";
			
			System.out.println(" San pham init: " + query);
			ResultSet spRs = db.get(query);
			String spten = "", donvi = "", soluong = "";
			NumberFormat formatter2 = new DecimalFormat("#,###,###.###"); 
			if (spRs != null)
			{
				try
				{
					int i = 1;
					while (spRs.next())
					{
						cell = new PdfPCell(new Paragraph(""+i, font8));
						cell.setPaddingLeft(5.0f);
						cell.setBorderWidth(1);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tbDenghi.addCell(cell);
						if(lhhId.equals("0"))
						{
							spten = spRs.getString("spTen2");
							
							cell = new PdfPCell(new Paragraph(spten, font8));
							cell.setPaddingLeft(5.0f);
							cell.setBorderWidth(1);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tbDenghi.addCell(cell);
						}
						else
						{
							if(lhhId.equals("1")) //Tai san co dinh
							{
								spten = spRs.getString("tscdTen");
								cell = new PdfPCell(new Paragraph(spten, font8));
								cell.setPaddingLeft(5.0f);
								cell.setBorderWidth(1);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								tbDenghi.addCell(cell);
							}
							else
							{
								if(lhhId.equals("3")) //Cong cu dung cu
								{
									spten = spRs.getString("ccdcTen");
									cell = new PdfPCell(new Paragraph(spten, font8));
									cell.setPaddingLeft(5.0f);
									cell.setBorderWidth(1);
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									tbDenghi.addCell(cell);
								}
								else
								{
									spten = spRs.getString("ncpTen");
									cell = new PdfPCell(new Paragraph(spten, font8));
									cell.setPaddingLeft(5.0f);
									cell.setBorderWidth(1);
									cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									tbDenghi.addCell(cell);
								}
							}
						}
						
						soluong = formatter2.format(spRs.getDouble("soluong"));
						
						donvi = spRs.getString("donvi");
						cell = new PdfPCell(new Paragraph("", font8));
						cell.setPaddingLeft(5.0f);
						cell.setBorderWidth(1);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tbDenghi.addCell(cell);
						
						cell = new PdfPCell(new Paragraph(donvi, font8));
						cell.setPaddingLeft(5.0f);
						cell.setBorderWidth(1);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tbDenghi.addCell(cell);
						
						cell = new PdfPCell(new Paragraph(soluong, font8));
						cell.setPaddingLeft(5.0f);
						cell.setBorderWidth(1);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tbDenghi.addCell(cell);
						
						cell = new PdfPCell(new Paragraph("", font8));
						cell.setPaddingLeft(5.0f);
						cell.setBorderWidth(1);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tbDenghi.addCell(cell);
						
						cell = new PdfPCell(new Paragraph("", font8));
						cell.setPaddingLeft(5.0f);
						cell.setBorderWidth(1);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tbDenghi.addCell(cell);
						
						i++;
					}
					spRs.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					System.out.println("Khong the tao san Pham " + e.getMessage());
				}
			}
			
			document.add(tbDenghi);
			
			/********************** END INFO DENGHI *******************************/
			
			/********************** INFO NGUOI DENGHI, DUYET *******************************/
			
			PdfPTable tbFooter = new PdfPTable(3);
			tbFooter.setWidthPercentage(100);
			tbFooter.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbFooter.getDefaultCell().setBorder(0);
			tbFooter.setSpacingBefore(20f);
			
			cell = new PdfPCell(new Paragraph("DUYỆT", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbFooter.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("QUẢN LÝ TRỰC TIẾP", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbFooter.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("NGƯỜI ĐỀ NGHỊ", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbFooter.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbFooter.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbFooter.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbFooter.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbFooter.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbFooter.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbFooter.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbFooter.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbFooter.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbFooter.addCell(cell);
			document.add(tbFooter);
			
			/********************** END INFO NGUOI DENGHI, DUYET *******************************/
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception In PDF: " + e.getMessage());
		}
		
	}
}
