package geso.traphaco.erp.servlets.dutoanvattu;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.dutoanvattu.IErpDutoanvattu;
import geso.traphaco.erp.beans.dutoanvattu.IErpDutoanvattuList;
import geso.traphaco.erp.beans.dutoanvattu.IKho;
import geso.traphaco.erp.beans.dutoanvattu.INgaynhan;
import geso.traphaco.erp.beans.dutoanvattu.INhacungcap;
import geso.traphaco.erp.beans.dutoanvattu.ISanpham;
import geso.traphaco.erp.beans.dutoanvattu.imp.ErpDutoanvattuList;
import geso.traphaco.erp.beans.dutoanvattu.imp.ErpDutoanvattu;
import geso.traphaco.erp.beans.dutoanvattu.imp.Ngaynhan;
import geso.traphaco.erp.beans.dutoanvattu.imp.Sanpham;
import geso.traphaco.erp.beans.dutoanvattu.imp.Nhacungcap;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class ErpDutoanvattuUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpDutoanvattuUpdateSvl()
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
			IErpDutoanvattu dmhBean = new ErpDutoanvattu(id);
			dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
			dmhBean.setUserId(userId); // phai co UserId truoc khi Init
			
			String task = request.getParameter("task");
			if(task == null)
				task = "";
			if(task.equals("print"))
			{
				Create_DT_PDF(response, request);
			}
			
			else{
			
			
						dmhBean.init(false);
						System.out.println("ghi chu display:" +dmhBean.getGhiChu() +"\n");
						
						if (request.getQueryString().indexOf("display") >= 0)
						{
							nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuDisplay.jsp";
						}
						else
						{
							nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuUpdate.jsp";
						}
						
						System.out.println("lhhId:"+dmhBean.getLoaihanghoa());
						session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
						session.setAttribute("lspId", dmhBean.getLoaispId());
						session.setAttribute("dmhBean", dmhBean);
						response.sendRedirect(nextJSP);
			
			}
			
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
			
			ErpDutoanvattu dmhBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			System.out.println("ID" + id);
			if (id == null)
			{
				dmhBean = new ErpDutoanvattu("");
			}
			else
			{
				dmhBean = new ErpDutoanvattu(id);
			}
			String contyId = (String)session.getAttribute("congtyId");
			session.setAttribute("ctyId", contyId);
			dmhBean.setCongtyId(contyId);
			dmhBean.setUserId(userId);
			
			String action = util.antiSQLInspection(request.getParameter("action"));

			
			String ngaydutoan = util.antiSQLInspection(request.getParameter("ngaymuahang"));
			if (ngaydutoan == null || ngaydutoan == "")
				ngaydutoan = this.getDateTime();
			dmhBean.setNgaydutoan(ngaydutoan);			
			
			String dvth = util.antiSQLInspection(request.getParameter("dvthId"));
			if (dvth == null)
				dvth = "";
			dmhBean.setDvthId(dvth);
			
			String dnmhId = util.antiSQLInspection(request.getParameter("dnmhId"));
			if (dnmhId == null)
				dnmhId = "";
			dmhBean.setDnmhId(dnmhId);
			
			String timnccId = util.antiSQLInspection(request.getParameter("timnccId"));
			if (timnccId == null)
				timnccId = "";
			dmhBean.setTimNCCId(timnccId);

			String loaihh = util.antiSQLInspection(request.getParameter("loaihh"));
			if (loaihh == null)
				loaihh = "";

			System.out.println("de nghe mua hang:"+dnmhId);
			System.out.println("tim ncc :"+timnccId);
			System.out.println("lhhId::"+loaihh);
			session.setAttribute("lhhId", loaihh);
			dmhBean.setLoaihanghoa(loaihh);
			 
			
			String loaisp = util.antiSQLInspection(request.getParameter("loaisp"));
			if (loaisp == null)
				loaisp = "";
			session.setAttribute("lspId", loaisp);
			dmhBean.setLoaispId(loaisp);
			
			String NguonGocHH = util.antiSQLInspection(request.getParameter("nguongoc"));
			if (NguonGocHH == null)
				NguonGocHH = "";
			dmhBean.setNguonGocHH(NguonGocHH);
						
			
			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";
			System.out.println(" ghi chu dtvt : "+ ghichu +"\n");
			dmhBean.setGhiChu(ghichu);
			
			String tiente_fk = util.antiSQLInspection(request.getParameter("tiente_fk"));
			if (tiente_fk == null) tiente_fk = "";
			dmhBean.setTienTe_FK(tiente_fk);
			
			String tigia = util.antiSQLInspection(request.getParameter("tygiaid"));
			if (tigia == null) tigia = "1";
			dmhBean.setTyGiaQuyDoi(Float.parseFloat(tigia));
			
									
			String[] nccid = request.getParameterValues("nccid");
			String[] bvat = request.getParameterValues("bvat");
			String[] avat = request.getParameterValues("avat");
			String[] vat = request.getParameterValues("vat");
			List<INhacungcap> nccList = new ArrayList<INhacungcap>();
			
			INhacungcap ncc = null;
			
			if(nccid != null)
			{
				for(int j = 0; j < nccid.length; j ++)
				{
					if(nccid[j].trim().length() > 0)
					{
						ncc = new Nhacungcap();
						ncc.setId(nccid[j]);
						ncc.setTongtienAvat(avat[j].replaceAll(",", ""));
						ncc.setTongtienBvat(bvat[j].replaceAll(",", ""));
						ncc.setVat(vat[j].replaceAll(",", ""));					
						
						String[] idsp = request.getParameterValues("idsp"+ j);
						String[] masp = request.getParameterValues("masp"+ j);
						String[] tensp = request.getParameterValues("tensp"+ j);
						String[] sl = request.getParameterValues("soluong"+ j);
						String[] donvitinh = request.getParameterValues("donvitinh"+ j);
						String[] dongia = request.getParameterValues("dongia"+ j);
						String[] thanhtien = request.getParameterValues("thanhtien"+ j);
						
//						System.out.println(" don gia : "+dongia[j]);
						List<ISanpham> spList = new ArrayList<ISanpham>();;
						
						ISanpham sp = null; 
						if(masp!=null){
							for(int i = 0; i < masp.length; i++)
							{
								//Loại hàng hóa chi phí dịch vụ (2) -> không cần nhập mã nên chỉ cần kiểm tra tên
								if(((loaihh.equals("2") ||loaihh.equals("3")||loaihh.equals("1")) && tensp[i].trim().length() > 0) || masp[i].trim().length() > 0)
								{					
									sp = new Sanpham();
									sp.setId(idsp[i]);
									sp.setMasanpham(masp[i]);
									sp.setTensanpham(tensp[i]);
									sp.setSoluong(sl[i].replaceAll(",", ""));
									sp.setDonvitinh(donvitinh[i]);
									sp.setDongia(dongia[i].replaceAll(",", ""));
									
									sp.setThanhtien(thanhtien[i].replaceAll(",", ""));
									
									spList.add(sp);
								}
							}
							
							ncc.setSanPham(spList);
							nccList.add(ncc);
							
							dmhBean.setNhacungcapList(nccList);
						}
					}
				}
			
			}
			
			
			if (action.equals("save") )
			{				
				if (id == null) // tao moi
				{
					if (!dmhBean.createDmh())
					{
						dmhBean.createRs(false);
						session.setAttribute("dmhBean", dmhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuNew.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
					else
					{
						IErpDutoanvattuList obj = new ErpDutoanvattuList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
						geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuList.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				}
				else
				{
					if (!dmhBean.updateDmh())
					{
						dmhBean.createRs(false);
						session.setAttribute("dmhBean", dmhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuUpdate.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
					else
					{
						IErpDutoanvattuList obj = new ErpDutoanvattuList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
						geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuList.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				}
			}
			else
			{				
				dmhBean.createRs(false);
								
				System.out.println("lhhId::"+loaihh);
				session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
				
				String nextJSP;
				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuNew.jsp";
				}
				else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuUpdate.jsp";
				}
				session.setAttribute("dmhBean", dmhBean);
				response.sendRedirect(nextJSP);
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
	
	private void Create_DT_PDF(HttpServletResponse response, HttpServletRequest request) 
	{
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," inline; filename=DuToanVatTu.pdf");
		
		float CONVERT = 28.346457f;
		float PAGE_LEFT = 2.0f*CONVERT, PAGE_RIGHT = 1.5f*CONVERT, PAGE_TOP = 0.5f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
		Document document = new Document(PageSize.A4.rotate(), PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		ServletOutputStream outstream;
		try 
		{
			outstream = response.getOutputStream();
			
			String id = request.getParameter("id");
			dbutils db = new dbutils();
			
			this.CreateDT(document, outstream, response, request, db);
			
			//db.update("Update ERP_MuaHang set DaInPdf = 1 where pk_seq = '" + id + "'");
			db.shutDown();
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("___Exception PO Print: " + e.getMessage());
		}
	}
	
	private void CreateDT(Document document, ServletOutputStream outstream, HttpServletResponse response, HttpServletRequest request, dbutils db) 
	{
		HttpSession session = request.getSession();
		
		String id = request.getParameter("id");
		String ctyId = (String)session.getAttribute("congtyId");
		
		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter_2sole = new DecimalFormat("#,###,###.##"); 
			NumberFormat formatter_4sole = new DecimalFormat("#,###,###.####");
			
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
			Font font8green = new Font(bf, 8, Font.NORMAL, BaseColor.GREEN);
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
			
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("pathPrint")+"/logo.gif");
			hinhanh.scalePercent(10);
			
			PdfPCell _celllogo = new PdfPCell(hinhanh);
			_celllogo.setBorder(0);
			_celllogo.setHorizontalAlignment(Element.ALIGN_LEFT);
			_celllogo.setVerticalAlignment(Element.ALIGN_CENTER);
			_celllogo.setPaddingTop(5.0f);
			_celllogo.setRowspan(3);
			tbHeader.addCell(_celllogo);

			PdfPCell cell = new PdfPCell(new Paragraph("", font8green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			
			tbHeader.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font8green));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			
			tbHeader.addCell(cell);
			
			document.add(tbHeader);
			
			/********************** END INFO CONGTY *******************************/
			
			PdfPTable tbTieude = new PdfPTable(2);
			tbTieude.setWidthPercentage(100);
			float[] crTieude = {80f, 20f};
			tbTieude.setWidths(crTieude);
			tbTieude.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbTieude.getDefaultCell().setBorder(0);
			tbTieude.setSpacingBefore(20f);
			
			cell = new PdfPCell(new Paragraph("DỰ TOÁN VẬT TƯ", font20bold));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbTieude.addCell(cell);
			
			PdfPTable tbPO = new PdfPTable(2);
			//tbPO.setWidthPercentage(10);
			float[] crPO = {20f, 50f};
			tbPO.setWidths(crPO);
			tbPO.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbPO.getDefaultCell().setBorder(0);
			tbPO.setSpacingBefore(25f);
			
			cell = new PdfPCell(new Paragraph("P.O", font10));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbPO.addCell(cell);
			
			String query = "select denghi_fk from erp_dutoanvattu where pk_seq = '"+id+"'"; 
			ResultSet rs = db.get(query);
			String po = "";
			if(rs != null)
			{
				while(rs.next())
				{
					po = rs.getString("denghi_fk")==null?"":rs.getString("denghi_fk");
				}
			}
			
			cell = new PdfPCell(new Paragraph(po, font10));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbPO.addCell(cell);
			
			tbTieude.addCell(tbPO);
			document.add(tbTieude);
			
			/********************** INFO DENGHI *******************************/
			
			query = "select COUNT(*) as soluong from ERP_DUTOANVATTU_NCC a where DTVT_FK = '"+id+"'";
			rs = db.get(query);
			int soluongncc = 0;
			String slncc = "0";
			if(rs != null)
			{
				while(rs.next())
				{
					slncc = rs.getString("soluong")==null?"0":rs.getString("soluong");
					System.out.println("sl ncc " + slncc);
				}
			}
			soluongncc = Integer.parseInt(slncc);
			System.out.println("soluong ncc " + soluongncc);
			PdfPTable tbDutoan = new PdfPTable(soluongncc + 2);
			tbDutoan.setWidthPercentage(100);
			tbDutoan.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbDutoan.getDefaultCell().setBorderWidth(1);
			float[] crDutoan = new float[soluongncc+2];
			for(int i = 0; i< soluongncc+1; i++)
			{
				crDutoan[i] = 50f;
			}
			crDutoan[soluongncc+1] = 15f;
			tbDutoan.setWidths(crDutoan);
			tbDutoan.setSpacingBefore(20f);
			
			cell = new PdfPCell(new Paragraph("", font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbDutoan.addCell(cell);
			
			query = "select a.NCC_FK, b.TEN from ERP_DUTOANVATTU_NCC a inner join ERP_NHACUNGCAP b on a.NCC_FK = b.PK_SEQ where DTVT_FK = '"+id+"'";
			rs = db.get(query);
			String tenncc = "";
			List<String> idnccs = new ArrayList<String>();
			String idncc = "";
			if(rs != null)
			{
				while(rs.next())
				{
					tenncc = rs.getString("ten");
					idncc = rs.getString("ncc_fk");
					idnccs.add(idncc);
					cell = new PdfPCell(new Paragraph(tenncc, font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					tbDutoan.addCell(cell);
				}
			}
			
			cell = new PdfPCell(new Paragraph("GHI CHÚ", font10));
			cell.setPaddingLeft(5.0f);
			//cell.setBorderWidth(1);
			cell.setRowspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbDutoan.addCell(cell);
			
			PdfPTable tbSanpham = new PdfPTable(3);
			tbSanpham.setWidthPercentage(100);
			tbSanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbSanpham.getDefaultCell().setBorderWidth(0);
			float[] crSanpham = {20f, 60f, 20f};
			tbSanpham.setSpacingBefore(0f);
			tbSanpham.setWidths(crSanpham);
			
			cell = new PdfPCell(new Paragraph("STT", font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbSanpham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("TÊN HÀNG", font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbSanpham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("ĐVT", font10));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbSanpham.addCell(cell);
			
			tbDutoan.addCell(tbSanpham);
			
			for (int i = 0; i < soluongncc; i++)
			{
				PdfPTable tbGia = new PdfPTable(3);
				tbGia.setWidthPercentage(100);
				tbGia.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbGia.getDefaultCell().setBorder(0);
				float[] crGia = {30f, 30f, 40f};
				tbGia.setWidths(crGia);
				
				cell = new PdfPCell(new Paragraph("SỐ LƯỢNG", font10));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				tbGia.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("ĐƠN GIÁ", font10));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				tbGia.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("THÀNH TIỀN", font10));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				tbGia.addCell(cell);
				
				tbDutoan.addCell(tbGia);
			}
			
			/********************** INFO CHITIET **********************************/
			
			query = "select b.DENGHI_FK, b.SANPHAM_FK, c.TEN, d.DONVI  " + 
					"from ERP_DUTOANVATTU a inner join ERP_DENGHIMUAHANG_SP b on a.DENGHI_FK = b.DENGHI_FK " + 
					"inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
					"inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ " +
					"where a.PK_SEQ = '"+id+"'";
			System.out.println("danh sach san pham " + query);
			rs = db.get(query);
			String idsp = "", tensp = "", donvi = "";
			int stt = 1;
			if(rs != null)
			{
				while(rs.next())
				{
					idsp = rs.getString("sanpham_fk");
					tensp = rs.getString("ten");
					donvi = rs.getString("donvi");
						
					PdfPTable tbSanphamct = new PdfPTable(3);
					tbSanphamct.setWidthPercentage(100);
					tbSanphamct.setHorizontalAlignment(Element.ALIGN_LEFT);
					tbSanphamct.getDefaultCell().setBorderWidth(0);
					tbSanphamct.setSpacingBefore(0f);
					tbSanphamct.setWidths(crSanpham);
					
					cell = new PdfPCell(new Paragraph(""+stt, font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					tbSanphamct.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(tensp, font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					tbSanphamct.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(donvi, font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					tbSanphamct.addCell(cell);
					
					tbDutoan.addCell(tbSanphamct);
					stt++;
					
					for(int i = 0; i < idnccs.size(); i++)
					{
						idncc = idnccs.get(i);
						query = "select SOLUONG, DONGIA, THANHTIEN from ERP_DUTOANVATTU_SANPHAM where DTVT_FK = '"+id+"' and (SANPHAM_FK = '"+idsp+"' or TAISAN_FK = 0 or CCDC_FK = 0 or CHIPHI_FK = 0) and NCC_FK = '"+idncc+"' ";
						ResultSet rsncc = db.get(query);
						String soluong = "", dongia = "", thanhtien = "";
						int kt = 0;
						if(rsncc != null)
						{
							while(rsncc.next())
							{
								kt++;
								soluong = formatter.format(rsncc.getDouble("soluong"));
								dongia = formatter_4sole.format(rsncc.getDouble("dongia"));
								thanhtien = formatter.format(rsncc.getDouble("thanhtien"));
								
								PdfPTable tbGia = new PdfPTable(3);
								tbGia.setWidthPercentage(100);
								tbGia.setHorizontalAlignment(Element.ALIGN_LEFT);
								tbGia.getDefaultCell().setBorder(0);
								float[] crGia = {30f, 30f, 40f};
								tbGia.setWidths(crGia);
								
								cell = new PdfPCell(new Paragraph(soluong, font10));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_CENTER);
								tbGia.addCell(cell);
								
								cell = new PdfPCell(new Paragraph(dongia, font10));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_CENTER);
								tbGia.addCell(cell);
								
								cell = new PdfPCell(new Paragraph(thanhtien, font10));
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setVerticalAlignment(Element.ALIGN_CENTER);
								tbGia.addCell(cell);
								
								tbDutoan.addCell(tbGia);
							}
						}
						if(kt == 0)
						{
							PdfPTable tbGia = new PdfPTable(3);
							tbGia.setWidthPercentage(100);
							tbGia.setHorizontalAlignment(Element.ALIGN_LEFT);
							tbGia.getDefaultCell().setBorder(0);
							float[] crGia = {30f, 30f, 40f};
							tbGia.setWidths(crGia);
							
							cell = new PdfPCell(new Paragraph("", font10));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							tbGia.addCell(cell);
							
							cell = new PdfPCell(new Paragraph("", font10));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							tbGia.addCell(cell);
							
							cell = new PdfPCell(new Paragraph("", font10));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							tbGia.addCell(cell);
							
							tbDutoan.addCell(tbGia);
						}
					}
					
					cell = new PdfPCell(new Paragraph("", font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					tbDutoan.addCell(cell);
				}
			}
			
			/********************** INFO END CHITIET **********************************/
			
			/********************** INFO TONG TIEN **********************************/
			
			cell = new PdfPCell(new Paragraph("TỔNG", font10));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbDutoan.addCell(cell);
			
			query = "select TONGTIENBVAT, VAT, TONGTIENAVAT from ERP_DUTOANVATTU_NCC a where DTVT_FK = '"+id+"'";
			rs = db.getScrol(query);
			String x = "";
			if(rs != null)
			{
				while(rs.next())
				{
					x = formatter.format(rs.getDouble("tongtienbvat"));
					cell = new PdfPCell(new Paragraph(x, font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					tbDutoan.addCell(cell);
				}
			}
			
			/*for(int i = 0; i < soluongncc; i++)
			{
				cell = new PdfPCell(new Paragraph("", font8));
				cell.setPaddingLeft(5.0f);
				cell.setBorderWidth(1);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				tbDutoan.addCell(cell);
			}*/
			
			cell = new PdfPCell(new Paragraph("", font10));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setRowspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbDutoan.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("VAT 10%", font10));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbDutoan.addCell(cell);
			
			x = "";
			if(rs != null)
			{
				rs.beforeFirst();
				while(rs.next())
				{
					x = formatter.format(rs.getDouble("vat"));
					cell = new PdfPCell(new Paragraph(x, font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					tbDutoan.addCell(cell);
				}
			}
			
			cell = new PdfPCell(new Paragraph("TỔNG GIÁ TRỊ SAU VAT", font10));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbDutoan.addCell(cell);
			
			x = "";
			if(rs != null)
			{
				rs.beforeFirst();
				while(rs.next())
				{
					x = formatter.format(rs.getDouble("TONGTIENAVAT"));
					cell = new PdfPCell(new Paragraph(x, font10));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					tbDutoan.addCell(cell);
				}
			}
			document.add(tbDutoan);
			
			/********************** END TONG TIEN *******************************/
			
			
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception In PDF: " + e.getMessage());
		}
		
	}
	
}
