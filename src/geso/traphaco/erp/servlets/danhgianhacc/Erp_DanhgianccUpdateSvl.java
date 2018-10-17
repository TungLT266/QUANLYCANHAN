package geso.traphaco.erp.servlets.danhgianhacc;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.danhgianhacc.IErp_Danhgiancc;
import geso.traphaco.erp.beans.danhgianhacc.IErp_DanhgianccList;
import geso.traphaco.erp.beans.danhgianhacc.imp.Erp_Danhgiancc;
import geso.traphaco.erp.beans.danhgianhacc.imp.Erp_DanhgianccList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import T.TG;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Erp_DanhgianccUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Erp_DanhgianccUpdateSvl()
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nextJSP = "";
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
			IErp_Danhgiancc dgnccBean = new Erp_Danhgiancc(id);
			dgnccBean.setCongtyId((String)session.getAttribute("congtyId"));
			dgnccBean.setUserId(userId); // phai co UserId truoc khi Init
			
			String task = request.getParameter("task");
			if(task == null)
				task = "";
			if(task.equals("print"))
			{
				System.out.println("AAAAAAAAAAAAAAAAAAAAAAA");
				Create_DGNCC_PDF(response, request);
			}
			dgnccBean.init();
				
			if (request.getQueryString().indexOf("update") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDanhGiaNCCUpdate.jsp";
			}
			
			session.setAttribute("dgnccBean", dgnccBean);
			response.sendRedirect(nextJSP);
			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

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
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			session.setMaxInactiveInterval(30000);
			
			IErp_Danhgiancc dgnccBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			System.out.println("ID" + id);
			if (id == null)
			{
				dgnccBean = new Erp_Danhgiancc("");
			}
			else
			{
				dgnccBean = new Erp_Danhgiancc(id);
			}
			String contyId = (String)session.getAttribute("congtyId");
			
			dgnccBean.setCongtyId(contyId);
			dgnccBean.setUserId(userId);
			
			String dvthId = util.antiSQLInspection(request.getParameter("dvthId"));
			if (dvthId == null)
				dvthId = "";
			session.setAttribute("dvthId", dvthId);
			dgnccBean.setDvthId(dvthId);
			System.out.println("dvthId " + dvthId);
			
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "";
			session.setAttribute("trangthai", trangthai);
			dgnccBean.setTrangthai(trangthai);
			
			String spId = util.antiSQLInspection(request.getParameter("spId"));
			if (spId == null)
				spId = "";
			session.setAttribute("spId", spId);
			dgnccBean.setSpId(spId);
			
			String nccId = util.antiSQLInspection(request.getParameter("nccId"));
			if (nccId == null)
				nccId = "";
			session.setAttribute("nccId", nccId);
			dgnccBean.setNccId(nccId);
			
			String tucach = util.antiSQLInspection(request.getParameter("tucach"));
			if (tucach == null)
				tucach = "0";
			dgnccBean.setTucach(tucach);
			
			String uytin = util.antiSQLInspection(request.getParameter("uytin"));
			if (uytin == null)
				uytin = "0";
			dgnccBean.setUytin(uytin);
			
			String chatluong = util.antiSQLInspection(request.getParameter("chatluongsp"));
			if (chatluong == null)
				chatluong = "0";
			dgnccBean.setChatluong(chatluong);
			
			String tggiaohang = util.antiSQLInspection(request.getParameter("tggiaohang"));
			if (tggiaohang == null)
				tggiaohang = "0";
			dgnccBean.setTggiaohang(tggiaohang);
			
			String giaca = util.antiSQLInspection(request.getParameter("giaca"));
			if (giaca == null)
				giaca = "";
			dgnccBean.setGiaca(giaca);
			
			String ptthanhtoan = util.antiSQLInspection(request.getParameter("ptthanhtoan"));
			if (ptthanhtoan == null)
				ptthanhtoan = "";
			dgnccBean.setPtthanhtoan(ptthanhtoan);
			
			String ptvanchuyen = util.antiSQLInspection(request.getParameter("ptvanchuyen"));
			if (ptvanchuyen == null)
				ptvanchuyen = "";
			dgnccBean.setPtvanchuyen(ptvanchuyen);
			
			String haumai = util.antiSQLInspection(request.getParameter("haumai"));
			if (haumai == null)
				haumai = "";
			dgnccBean.setHaumai(haumai);
			
			String chinhsachkhac = util.antiSQLInspection(request.getParameter("chinhsachkhac"));
			if (chinhsachkhac == null)
				chinhsachkhac = "";
			dgnccBean.setChinhsachkhac(chinhsachkhac);
			
			String sopo = util.antiSQLInspection(request.getParameter("sopo"));
			if (sopo == null)
				sopo = "null";
			dgnccBean.setSoPO(sopo);
			
			String action = request.getParameter("action");
			
			if (action.equals("save") )
			{
				if(dvthId == ""){
					dgnccBean.setMessage("Vui lòng chọn đề đơn vị thực hiện");
					dgnccBean.createRs();
					String nextJSP;
					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpDanhGiaNCCNew.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpDanhGiaNCCUpdate.jsp";
					}
					session.setAttribute("dgnccBean", dgnccBean);
					response.sendRedirect(nextJSP);				
					return;
				}
				
				if(nccId == ""){
					dgnccBean.setMessage("Vui lòng chọn đề nhà cung cấp");
					dgnccBean.createRs();
					String nextJSP;
					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpDanhGiaNCCNew.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpDanhGiaNCCUpdate.jsp";
					}
					session.setAttribute("dgnccBean", dgnccBean);
					response.sendRedirect(nextJSP);				
					return;
				}

				if (id == null) // tao moi
				{
					if (!dgnccBean.createDGNcc())
					{
						dgnccBean.createRs();
						session.setAttribute("dgnccBean", dgnccBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDanhGiaNCCNew.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
					else
					{
						IErp_DanhgianccList obj = new Erp_DanhgianccList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDanhGiaNCC.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				}
				else
				{
					if (!dgnccBean.updateDGNcc())
					{
						dgnccBean.createRs();
						session.setAttribute("dgnccBean", dgnccBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDanhGiaNCCUpdate.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
					else
					{
						IErp_DanhgianccList obj = new Erp_DanhgianccList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDanhGiaNCC.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				}
			}
			else
			{
				String nextJSP;
				dgnccBean.createRs();
				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDanhGiaNCCNew.jsp";
				}
				else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDanhGiaNCCUpdate.jsp";
				}
				session.setAttribute("dgnccBean", dgnccBean);
				response.sendRedirect(nextJSP);
			}
		}
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
	
	private void Create_DGNCC_PDF(HttpServletResponse response, HttpServletRequest request) 
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
			
			this.CreateDGNCC(document, outstream, response, request, db);
			
			//db.update("Update ERP_MuaHang set DaInPdf = 1 where pk_seq = '" + id + "'");
			db.shutDown();
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("___Exception PO Print: " + e.getMessage());
		}
	}
	
	private void CreateDGNCC(Document document, ServletOutputStream outstream, HttpServletResponse response, HttpServletRequest request, dbutils db) 
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
			Font font8bold = new Font(bf, 8, Font.BOLD);
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
			String query = "select a.PK_SEQ, f.ten as tenncc, f.ten_nguoilienhe, f.diachi, f.dienthoai, g.ma as masp, g.ten as tensp, a.NGAYSUA, c.TEN as nguoisua, "
					+ "a.SP_FK, a.dvth_fk, e.pk_seq as tieuchi, d.diem, d.dat, a.dotincay, a.chatluong, a.khanangcongnghe, a.giaca, a.phuongthuctt, a.tggiaohang, a.haumai, a.cacdkkhac  ";
			query = query + "from ERP_DANHGIANCC a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ ";
			query = query + "inner join erp_NHACUNGCAP f on a.NCC_FK = f.pk_seq inner join sanpham g on a.sp_fk = g.pk_seq ";
			query = query + "inner join ERP_DGNCC_TIEUCHIDG d on a.PK_SEQ = d.DG_FK right join ERP_TIEUCHIDANHGIA e on d.TIEUCHI_FK = e.pk_seq "
					+ "where a.PK_SEQ = '"+ id + "'";
			System.out.println("lay thong tin danh gia: " + query);
			ResultSet rs = db.get(query);
			String kyhieu = "", tenncc = "", nglienhe = "", diachi = "", dienthoai = "", sanpham = "";
			String tucach = "", uytin = "", chatluong = "", thoigiangh = "", giaca = "", phuongthuctt = "", phuongthucvc = "";
			String haumai = "", chinhsachkhac = "", khanangcn = "", ngayhieuluc = "", nguoilap = "";
		    while(rs.next())
			{
		    	kyhieu = rs.getString("pk_seq");
		    	ngayhieuluc = rs.getString("ngaysua");
		    	nguoilap = rs.getString("nguoisua");
		    	sanpham = rs.getString("masp") + "-" + rs.getString("tensp");;
		    	tenncc = rs.getString("tenncc");
		    	diachi = rs.getString("diachi");
		    	dienthoai = rs.getString("dienthoai");
		    	nglienhe = rs.getString("ten_nguoilienhe");
		    	String tieuchi = rs.getString("tieuchi");
		    	if(tieuchi.equals("100000"))
		    		tucach = rs.getString("dat");
		    	if(tieuchi.equals("100001"))
		    		uytin = rs.getString("dat");
		    	if(tieuchi.equals("100002"))
		    		chatluong = rs.getString("dat");
		    	if(tieuchi.equals("100003"))
		    		thoigiangh = rs.getString("dat");
		    	if(tieuchi.equals("100004"))
		    		giaca = rs.getString("diem");
		    	if(tieuchi.equals("100005"))
		    		phuongthuctt = rs.getString("diem");
		    	if(tieuchi.equals("100006"))
		    		phuongthucvc = rs.getString("diem");
		    	if(tieuchi.equals("100007"))
		    		haumai = rs.getString("diem");
		    	if(tieuchi.equals("100008"))
		    		chinhsachkhac = rs.getString("diem");
		    	uytin = rs.getString("dotincay");
		    	chatluong = rs.getString("chatluong");
		    	thoigiangh = rs.getString("tggiaohang");
		    	khanangcn = rs.getString("khanangcongnghe");
			}
		    
			PdfPTable tbHeader = new PdfPTable(4);
			tbHeader.setWidthPercentage(100);
			tbHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] crHeader = {70f, 110f, 110f, 110f};
			tbHeader.setWidths(crHeader);
			tbHeader.getDefaultCell().setBorder(0);
			
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/logo.gif");
			hinhanh.scalePercent(100);
			//hinhanh.scaleAbsolute(newWidth, newHeight);
			
			PdfPCell _celllogo = new PdfPCell(hinhanh);
			_celllogo.setBorder(0);
			_celllogo.setHorizontalAlignment(Element.ALIGN_LEFT);
			_celllogo.setVerticalAlignment(Element.ALIGN_CENTER);
			//_celllogo.setRowspan(3);
			tbHeader.addCell(_celllogo);

			PdfPCell cell = new PdfPCell(new Paragraph("Ký hiệu: " + kyhieu, font8));
			cell.setBorderWidth(0);
			cell.setPaddingLeft(5.0f);
			cell.setPaddingTop(5.0f);
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Lần sửa đổi: A", font8));
			cell.setPaddingLeft(5.0f);
			cell.setPaddingTop(5.0f);
			cell.setBorderWidth(0);
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Ngày hiệu lực: " + ngayhieuluc, font8));
			cell.setPaddingLeft(5.0f);
			cell.setPaddingTop(5.0f);
			cell.setBorderWidth(0);
			tbHeader.addCell(cell);
			
			document.add(tbHeader);
			
			/********************** END INFO CONGTY *******************************/
			
			
    
			PdfPTable tbTieude = new PdfPTable(1);
			tbTieude.setWidthPercentage(100);
			tbTieude.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbTieude.getDefaultCell().setBorder(0);
			tbTieude.setSpacingBefore(20f);
			
			cell = new PdfPCell(new Paragraph("PHIẾU ĐÁNH GIÁ NHÀ CUNG CẤP", font20bold));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbTieude.addCell(cell);
			
			document.add(tbTieude);
			
			Image uncheck=Image.getInstance( getServletContext().getInitParameter("path")+"/uncheck.png");
			uncheck.scaleAbsolute(10f, 10f);
			Image check=Image.getInstance( getServletContext().getInitParameter("path")+"/check.png");
			check.scaleAbsolute(10f, 10f);
			
			Paragraph p = new Paragraph();
			p.setAlignment(Element.ALIGN_RIGHT);
			p.add(new Chunk(uncheck, 0, 0));
			p.add(new Phrase(" Đánh giá mới   ", font8));
			p.add(new Chunk(uncheck, 0, 0));
			p.add(new Phrase(" Định kỳ", font8));
			document.add(p);
	
			/********************** INFO THONG TIN CHUNG *******************************/
			
			p = new Paragraph("A. Thông tin chung", font8bold);
			document.add(p);
			
			PdfPTable tbDenghi = new PdfPTable(2);
			tbDenghi.setWidthPercentage(100);
			tbDenghi.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDenghi.getDefaultCell().setBorder(0);
			float[] crDenghi = {20f, 80f};
			tbDenghi.setWidths(crDenghi);
			
			cell = new PdfPCell(new Paragraph("1. Tên nhà cung cấp", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": " + tenncc, font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("2. Loại hàng cung cấp", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": " + sanpham, font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("3. Người liên hệ", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": " + nglienhe, font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("4. Địa chỉ", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": " + diachi, font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("5. Điện thoại/ Fax", font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDenghi.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(": " + dienthoai, font8));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDenghi.addCell(cell);
			
			document.add(tbDenghi);
			
			/********************** END INFO DENGHI *******************************/

			p = new Paragraph("B. Phần đánh giá", font8bold);
			document.add(p);
			
			PdfPTable tbDanhgia = new PdfPTable(6);
			tbDanhgia.setWidthPercentage(100);
			tbDanhgia.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.getDefaultCell().setBorderWidth(1);
			float[] crDanhgia = {10f, 50f, 60f, 10f, 20f, 30f};
			tbDanhgia.setWidths(crDanhgia);
			tbDanhgia.setSpacingBefore(5f);
			
			cell = new PdfPCell(new Paragraph("STT", font8bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Tiêu chí đánh giá", font8bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Ý kiến đánh giá", font8bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Hệ số", font8bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Điểm", font8bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Tổng điểm", font8bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			/*****************************************************/
			cell = new PdfPCell(new Paragraph("1", font8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Độ tin cậy", font8));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));// ý kiến đánh giá
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("2", font8bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(uytin, font8));//điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(uytin, font8));//Tổng điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			/*****************************************************/
			cell = new PdfPCell(new Paragraph("2", font8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Chất lượng", font8));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));// ý kiến đánh giá
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("2", font8bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(chatluong, font8));//điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(chatluong, font8));//Tổng điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			/*****************************************************/
			
			cell = new PdfPCell(new Paragraph("3", font8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Khả năng công nghệ", font8));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));// ý kiến đánh giá
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("2", font8bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(khanangcn, font8));//điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(khanangcn, font8));//Tổng điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			/*****************************************************/
			
			cell = new PdfPCell(new Paragraph("4", font8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Giá cả", font8));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));// ý kiến đánh giá
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("2", font8bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(giaca, font8));//điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(giaca, font8));//Tổng điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			/*****************************************************/
			
			cell = new PdfPCell(new Paragraph("5", font8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Phương thức thanh toán", font8));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));// ý kiến đánh giá
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("1", font8bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(phuongthuctt, font8));//điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(phuongthuctt, font8));//Tổng điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			/*****************************************************/
			
			cell = new PdfPCell(new Paragraph("6", font8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Thời gian giao hàng", font8));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));// ý kiến đánh giá
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("1", font8bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(thoigiangh, font8));//điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(thoigiangh, font8));//Tổng điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			/*****************************************************/
			
			cell = new PdfPCell(new Paragraph("7", font8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Hậu mãi", font8));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));// ý kiến đánh giá
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("2", font8bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(haumai, font8));//điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(haumai, font8));//Tổng điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			/*****************************************************/
			
			cell = new PdfPCell(new Paragraph("8", font8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Các điều kiện khác", font8));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));// ý kiến đánh giá
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(chinhsachkhac, font8));//điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(chinhsachkhac, font8));//Tổng điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			/*****************************************************/
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Tổng số điểm", font8bold));// ý kiến đánh giá
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("12", font8bold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));//điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));//Tổng điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			/*****************************************************/
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Điểm bình quân", font8bold));// ý kiến đánh giá
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));//điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8));//Tổng điểm
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDanhgia.addCell(cell);
			/*****************************************************/
			document.add(tbDanhgia);
			
			p = new Paragraph("* Hệ số:", font8bold);
			document.add(p);
			
			p = new Paragraph("     1-Ít quan trọng", font8);
			document.add(p);
			
			p = new Paragraph("     2-Quan trọng", font8);
			document.add(p);
			
			p = new Paragraph("* Thang điểm:", font8bold);
			document.add(p);
			
			p = new Paragraph("     1-Quá kém không thể chấp nhận", font8);
			document.add(p);
			
			p = new Paragraph("     2-Đáp ứng dưới mức yêu cầu", font8);
			document.add(p);
			
			p = new Paragraph("     3-Có thể chấp nhận được", font8);
			document.add(p);
			
			p = new Paragraph("     4-Khá, thỏa mãn 80% yêu cầu", font8);
			document.add(p);
			
			p = new Paragraph("     5-Thỏa mãn mọi yêu cầu", font8);
			document.add(p);
			
			p = new Paragraph("* Kết luận:", font8bold);
			document.add(p);
			
			p = new Paragraph();
			p.add(new Chunk(uncheck, 0, 0));
			p.add(new Phrase(" Loại A - Tốt: Điểm bình quân ≥ 4.5 ", font8));
			document.add(p);
			
			p = new Paragraph();
			p.add(new Chunk(uncheck, 0, 0));
			p.add(new Phrase(" Loại B - Chấp nhận: Điểm bình quân ≥ 3.5 và không có điểm dưới 3", font8));
			document.add(p);
			
			p = new Paragraph();
			p.add(new Chunk(uncheck, 0, 0));
			p.add(new Phrase(" Loại C - Không đạt yêu cầu: các trường hợp còn lại", font8));
			document.add(p);
			
			p = new Paragraph("Ngày tháng năm", font8bold);
			p.setAlignment(Element.ALIGN_RIGHT);
			p.setIndentationRight(15);
			document.add(p);
			
			PdfPTable tbKyten = new PdfPTable(2);
			tbKyten.setWidthPercentage(100);
			tbKyten.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbKyten.getDefaultCell().setBorder(0);
			
			cell = new PdfPCell(new Paragraph("Duyệt", font8bold));
			cell.setPaddingLeft(25.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbKyten.addCell(cell);
			cell = new PdfPCell(new Paragraph("Người lập", font8bold));
			cell.setPaddingRight(25.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbKyten.addCell(cell);
			
			document.add(tbKyten);
			
			PdfPTable tbKyten2 = new PdfPTable(2);
			tbKyten2.setWidthPercentage(100);
			tbKyten2.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbKyten2.setSpacingBefore(25f);
			tbKyten2.getDefaultCell().setBorder(0);
			
			cell = new PdfPCell(new Paragraph("Họ & Tên", font8bold));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbKyten2.addCell(cell);
			cell = new PdfPCell(new Paragraph("Họ & Tên", font8bold));
			cell.setPaddingRight(25.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbKyten2.addCell(cell);
						
			document.add(tbKyten2);
			
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception In PDF: " + e.getMessage());
		}
		
	}
}
