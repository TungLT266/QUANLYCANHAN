package geso.traphaco.distributor.servlets.phieuxuatkho;

import geso.traphaco.distributor.beans.donhang.ISanpham;
import geso.traphaco.distributor.beans.donhang.imp.Sanpham;
import geso.traphaco.distributor.beans.phieuxuatkho.IPhieuxuatkho;
import geso.traphaco.distributor.beans.phieuxuatkho.imp.Phieuxuatkho;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/InPhieuXuatKhoPdf")
public class InPhieuXuatKhoPdf extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public InPhieuXuatKhoPdf()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		/*HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		IPhieuxuatkho pxkBean;
		dbutils db=null;

		
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		
		if(querystring != null)
		{
			userId = util.getUserId(querystring);
		    
		    if (userId.length() == 0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    if(querystring.indexOf("pdf") > 0)
		    {
		    	System.out.println("1.In tu file PDF....");
		    	
				String id = util.antiSQLInspection(request.getParameter("pdf"));
				pxkBean = new Phieuxuatkho(id);
			    pxkBean.setUserId(userId);
			    
			    pxkBean.init2();
			    db = new dbutils();
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition"," inline; filename=Phieuxuatkho.pdf");
								
				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				FileOutputStream
				createOrderDetails( document,outstream,pxkBean);
				pxkBean.DBclose();
				pxkBean=null;
				util=null;
		    }
		    
		}
		else
		{
			
			System.out.println("vao day ................xxx");
			pxkBean = (IPhieuxuatkho)session.getAttribute("pxkBean");
			
			db = new dbutils();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition"," inline; filename=Phieuxuatkho.pdf");
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			
			createOrderDetails( document,outstream,pxkBean);
			pxkBean.DBclose();
			pxkBean=null;
			util=null;		
			
		}
			if(db!=null){
				db.shutDown();
			}
		}*/
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/*HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		IPhieuxuatkho pxkBean;
		Utility util = new Utility();
		userId = util.antiSQLInspection(request.getParameter("userId"));
		
	
		String id = util.antiSQLInspection(request.getParameter("id"));
		pxkBean = new Phieuxuatkho(id);
		pxkBean.setUserId(userId);
		pxkBean.init2();
				
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," attachment; filename=\"Phieuxuatkho.pdf\" ");
		
		Document document = new Document();
	//ServletOutputStream outstream = response.getOutputStream();
		FileOutputStream	outstream = new FileOutputStream(FILE);
		createOrderDetails( document,outstream,pxkBean);
		pxkBean.DBclose();
		pxkBean=null;
		util=null;		
		}*/
	}	
	private String getDateTime()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return sdf.format(cal.getTime());
	}

	float CONVERT = 28.346457f; // = 1cm
	
	private static String FILE = "D:/temp/FirstPdf.pdf";
	private void createHeader(Document document,String ngaylap,int stt)
	{
		try
		{
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font header = new Font(bf, 11, Font.NORMAL);
			Font boldHeader = new Font(bf, 15, Font.BOLD);
			Font bold = new Font(bf, 11, Font.BOLD);
			Font italic = new Font(bf, 10, Font.ITALIC);
			Font normal = new Font(bf, 11, Font.NORMAL);
			Font underline = new Font(bf, 12, Font.UNDERLINE);
			
			String query="select ten,diachi,masothue from nhacungcap where pk_seq=100021";
			dbutils db=new dbutils();
			ResultSet rs=db.get(query);
			String nccTen="";
			String diachi="";
			String masothue="";
			while(rs.next())
			{
				nccTen=rs.getString("ten");
				diachi=rs.getString("diachi");
				masothue=rs.getString("masothue");
			}
			rs.close();
			//document.open();
			float[] withs ={ 75.0f, 275.0f,300.0f };
			PdfPTable table=new PdfPTable(withs);
			table.setWidthPercentage(100);
			PdfPCell cell = new PdfPCell();
			table.setSpacingBefore(10.0f);
			String[] line1 =
				{ "Đơn vị:", nccTen, "Mẫu số: 01-VT" };
			for (int i = 0; i < line1.length; i++)
			{
				if(i==0)
					cell = new PdfPCell(new Paragraph(line1[i], normal));
				else if(i==1) 
					cell = new PdfPCell(new Paragraph(line1[i], bold));
				else 
					cell = new PdfPCell(new Paragraph(line1[i], italic));
				
				if(i!=2)
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				else 
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(0.1f * CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
			}
			
			String[] line2 =
				{ "Địa chỉ:", diachi, "(Ban hành theo QĐ số 48/2006/QĐ-BTC ngày 14/09/2006" };
			for (int i = 0; i < line1.length; i++)
			{
				if(i==0)
					cell = new PdfPCell(new Paragraph(line2[i], normal));
				else if(i==1) 
					cell = new PdfPCell(new Paragraph(line2[i], normal));
				else 
					cell = new PdfPCell(new Paragraph(line2[i], italic));
				
				if(i!=2)
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				else 
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(0.1f * CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
			}
			
			String[] line3 =
				{ "Mã số thuế:", masothue, "của Bộ trưởng BTC )" };
			for (int i = 0; i < line1.length; i++)
			{
				if(i==0)
					cell = new PdfPCell(new Paragraph(line3[i], normal));
				else if(i==1) 
					cell = new PdfPCell(new Paragraph(line3[i], normal));
				else 
					cell = new PdfPCell(new Paragraph(line3[i], italic));
				
				if(i!=2)
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				else 
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(0.1f * CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
			}
			document.add(table);
			
			float[] with ={ 500.0f };
			table=new PdfPTable(with);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("PHIẾU XUẤT KHO",boldHeader));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(0.1f * CONVERT);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Ngày "+ngaylap.split("-")[2]+ " Tháng "+ngaylap.split("-")[1]+ " Năm "+ngaylap.split("-")[0]+ "" ,italic));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(0.1f * CONVERT);
			cell.setBorder(0);
			table.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("Số:  PXK2000000-"+stt ,bold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(0.1f * CONVERT);
			cell.setBorder(0);
			table.addCell(cell);
			
			document.add(table);
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void createFooter(Document document,String ngaylap)
	{
		try
		{
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font header = new Font(bf, 11, Font.NORMAL);
			Font bold = new Font(bf, 11, Font.BOLD);
			Font italic = new Font(bf, 10, Font.ITALIC);
			Font normal = new Font(bf, 10, Font.NORMAL);
			Font underline = new Font(bf, 12, Font.UNDERLINE);
			
			float[] withs ={ 75.0f, 75.0f,75.0f,75.0f,75.0f };
			PdfPTable table=new PdfPTable(withs);
			table.setWidthPercentage(100);
			PdfPCell cell = new PdfPCell();
			
			table.setSpacingBefore(28.0f);
			
			String[] line1 =
				{ "Ngày "+ngaylap.split("-")[2]+ "   Tháng   "+ngaylap.split("-")[1]+ "   Năm   "+ngaylap.split("-")[0]+ ""  };
			for (int i = 0; i < line1.length; i++)
			{
				cell = new PdfPCell(new Paragraph(line1[i], italic));
				cell.setColspan(5);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(0.1f * CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
			}
			
			
			
			String[] spTitles =
				{ "Người lập phiếu", "Người giao hàng", "Thủ kho", "Kế toán", "Giám đốc" };
			for (int i = 0; i < spTitles.length; i++)
			{
				cell = new PdfPCell(new Paragraph(spTitles[i], bold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPadding(0.1f * CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
			}
			String[] line2 =
				{ "(Ký,họ tên)", "(Ký,họ tên)", "(Ký,họ tên)", "(Ký,họ tên)", "(Ký,họ tên,đóng dấu)" };
			for (int i = 0; i < line2.length; i++)
			{
				cell = new PdfPCell(new Paragraph(line2[i], italic));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPadding(0.1f * CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
			}
			document.add(table);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void createCustomer( Document  document,String khachhang)
	{
		try
		{
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font header = new Font(bf, 11, Font.NORMAL);
			Font bold = new Font(bf, 11, Font.BOLD);
			Font italic = new Font(bf, 10, Font.ITALIC);
			Font normal = new Font(bf, 10, Font.NORMAL);
			Font underline = new Font(bf, 12, Font.UNDERLINE);
			
			/*FileOutputStream outstream = new FileOutputStream(FILE);
			PdfWriter.getInstance(document, outstream);
			document.setMargins(35.0f, 15.0f, 15.0f, 15.0f);
			document.setPageSize(PageSize.A4);*/
			
		
			float[] withs ={ 120.0f, 375.0f };
			PdfPTable table=new PdfPTable(withs);
			table.setWidthPercentage(100);
			PdfPCell cell ;
			table.setSpacingBefore(10.0f);
			
			String[] line1 =
				{ "Họ tên người nhận hàng:", khachhang };
			for (int i = 0; i < line1.length; i++)
			{
				cell = new PdfPCell(new Paragraph(line1[i], header));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPadding(0.1f * CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
			}
			
			String[] line2 =
				{ "Địa chỉ,bộ phận:", "Kho hàng hóa" };
			for (int i = 0; i < line2.length; i++)
			{
				cell = new PdfPCell(new Paragraph(line2[i], header));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPadding(0.1f * CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
			}
			
			String[] line3 =
				{ "Lý do xuất tại kho:", "Xuất bán" };
			for (int i = 0; i < line3.length; i++)
			{
				cell = new PdfPCell(new Paragraph(line3[i], header));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPadding(0.1f * CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
			}
			
			
			String[] line4 =
				{ "Xuất tại kho: ", "CN Cty TNHH TM XNK Việt Tranh Đề  - Địa điểm :276 -278 Võ Văn Tần,P5,Q3 " };
			for (int i = 0; i < line4.length; i++)
			{
				cell = new PdfPCell(new Paragraph(line4[i], header));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPadding(0.1f * CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
			}
			
			document.add(table);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	NumberFormat formatter = new DecimalFormat("#,###,###");
	private void createOrderDetails(Document	document,FileOutputStream outstream,IPhieuxuatkho pxkBean )
	{
		try
		{
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font header = new Font(bf, 11, Font.BOLD);
			
			Font bold = new Font(bf, 11, Font.BOLD);
			Font italic = new Font(bf, 10, Font.ITALIC);
			Font normal = new Font(bf, 10, Font.NORMAL);
			Font underline = new Font(bf, 12, Font.UNDERLINE);
			
			document = new Document();
			outstream = new FileOutputStream(FILE);
			PdfWriter.getInstance(document, outstream);
			document.setMargins(35.0f, 15.0f, 15.0f, 15.0f);
			document.setPageSize(PageSize.A4);
			
			document.open();
			
			String query=
			"	select pxk.pk_seq as pxkid,dh.pk_seq as donhangid,kh.ten as khachhang, "+
			"		kh.diachi as diachi,pxk.ngaylapphieu,sp.ma as spma ,sp.ten as spten ,dvdl.DONVI ,dhsp.soluong,dhsp.giamua, "+
			"		dhsp.soluong*dhsp.giamua as thanhtien "+		 
			"	from  "+
			"	phieuxuatkho pxk inner join phieuxuatkho_donhang pxkdh on pxk.pk_seq=pxkdh.pxk_fk "+
			"		inner join donhang dh on dh.pk_seq=pxkdh.donhang_fk "+
			"		inner join khachhang kh on kh.pk_seq=dh.khachhang_fk "+
			"		inner join donhang_sanpham dhsp on dhsp.donhang_fk=pxkdh.donhang_fk "+
			"		inner join sanpham sp on sp.pk_seq=dhsp.sanpham_fk "+
			"		inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ=sp.DVDL_FK "+
			"where PXK_FK="+pxkBean.getId()+"" +
			"	union all	 "+
			"	select pxk.pk_seq as pxkid,dh.pk_seq as donhangid,kh.ten as khachhang, "+
			"		kh.diachi as diachi,pxk.ngaylapphieu,sp.ma as spma ,sp.ten as spten ,dvdl.DONVI ,dhsp.soluong,0 as giamua,0 as thanhtien "+		 
			"	from  "+
			"	phieuxuatkho pxk inner join phieuxuatkho_donhang pxkdh on pxk.pk_seq=pxkdh.pxk_fk "+
			"		inner join donhang dh on dh.pk_seq=pxkdh.donhang_fk "+
			"		inner join khachhang kh on kh.pk_seq=dh.khachhang_fk "+
			"		inner join donhang_ctkm_trakm dhsp on dhsp.donhangid=pxkdh.donhang_fk "+
			"		inner join sanpham sp on sp.ma=dhsp.spma	 "+
			"		inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ=sp.DVDL_FK " +
			"where PXK_FK="+pxkBean.getId()+"" +
			"	order by donhangid,giamua desc		";
			dbutils db=new dbutils();
			ResultSet rs=db.get(query);
			String dhIdNext="";
			int stt=1;
			int page=1;
			PdfPTable table=null;
			PdfPCell cell;
			double tongtien=0;
			String ngaylap="";
			while(rs.next())
			{
				String dhId=rs.getString("donhangId");
				String donvi=rs.getString("donvi");
				String spTen=rs.getString("spTen");
				String soluong=formatter.format(rs.getDouble("soluong"));
				String giamua=formatter.format(rs.getDouble("giamua"));
				String thanhtien=formatter.format(rs.getDouble("thanhtien"));
				String khachhang=rs.getString("khachhang");
				String diachi=rs.getString("diachi");
				ngaylap=rs.getString("ngaylapphieu");
				if(!dhIdNext.equals(dhId))
				{
					if(dhIdNext.equals(""))
					{
						createHeader(document,ngaylap,rs.getInt("donhangId"));
						createCustomer(document,khachhang+" - "+diachi);
					}
					if(table!=null)
					{
						String[] lineTotal ={ "  ","Tổng cộng", formatter.format(tongtien) };
						for (int i = 0; i < lineTotal.length; i++)
						{
							cell = new PdfPCell(new Paragraph(lineTotal[i], bold));
							if(i==1)
								cell.setColspan(6);
							if(i==0)
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							else  if(i==2)
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPadding(0.1f * CONVERT);				
							table.addCell(cell);
						}
						
						String[] lineDocTien ={ "Tổng số tiền(Viết bằng chữ)"+formatter.format(tongtien) };
						for (int i = 0; i < lineDocTien.length; i++)
						{
							cell = new PdfPCell(new Paragraph(lineDocTien[i], italic));
							if(i==0)
								cell.setColspan(8);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPadding(0.1f * CONVERT);				
							table.addCell(cell);
						}
						
						document.add(table);
						createFooter(document,ngaylap);
						document.newPage();
						createHeader(document,ngaylap,rs.getInt("donhangId"));
						createCustomer(document,khachhang+" - "+diachi);
					}
					float[] withs ={ 18.0f, 100.0f,20.0f,18.0f,40.0f,30.0f,40.0f,45.0f };
					table=new PdfPTable(withs);
					table.setWidthPercentage(100);
					table.setSpacingBefore(10.0f);

					String[] line1 ={ "STT", "Tên nhãn hiệu,quy cách, phẩm chất vật tư,dụng cụ sản phẩm,hàng hóa","Mã số","ĐVT","Số lượng","Đơn giá","Thành tiền" };
					for (int i = 0; i < line1.length; i++)
					{
						cell = new PdfPCell(new Paragraph(line1[i], header));
						if(i<=3||i==5||i==6)
							cell.setRowspan(2);
						if(i==4)
							cell.setColspan(2);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPadding(0.1f * CONVERT);				
						table.addCell(cell);
					}
					
					
					String[] line2 ={ "Theo yêu cầu", "Thực xuất" };
					for (int i = 0; i < line2.length; i++)
					{
						cell = new PdfPCell(new Paragraph(line2[i], header));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPadding(0.1f * CONVERT);				
						table.addCell(cell);
					}
					String[] line3 ={ "A", "B","C","D","1","2","3","4" };
					for (int i = 0; i < line3.length; i++)
					{
						cell = new PdfPCell(new Paragraph(line3[i], header));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPadding(0.1f * CONVERT);				
						table.addCell(cell);
					}
					tongtien=0;
					dhIdNext=dhId;
					page++;
					stt=1;
				}
				String[] sp ={ ""+stt,spTen," ",donvi, soluong  , " " , giamua,thanhtien };
				for (int i = 0; i < sp.length; i++)
				{
					cell = new PdfPCell(new Paragraph(sp[i], normal));
					if(i<4)
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					else 
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPadding(0.1f * CONVERT);				
					table.addCell(cell);
				}
				tongtien+=rs.getDouble("thanhtien");
				stt++;
			}
			if(table!=null)
			{
				String[] lineTotal ={ "  ","Tổng cộng", formatter.format(tongtien) };
				for (int i = 0; i < lineTotal.length; i++)
				{
					cell = new PdfPCell(new Paragraph(lineTotal[i], bold));
					if(i==1)
						cell.setColspan(6);
					if(i==0)
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					else  if(i==2)
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPadding(0.1f * CONVERT);				
					table.addCell(cell);
				}
				
				String[] lineDocTien ={ "Tổng số tiền(Viết bằng chữ)"+formatter.format(tongtien) };
				for (int i = 0; i < lineDocTien.length; i++)
				{
					cell = new PdfPCell(new Paragraph(lineDocTien[i], italic));
					if(i==0)
						cell.setColspan(8);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPadding(0.1f * CONVERT);				
					table.addCell(cell);
				}
				document.add(table);
				createFooter(document,ngaylap);
			}
			rs.close();
			document.close();
		} catch ( Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void main(String[] arg)
	{
		InPhieuXuatKhoPdf svl=new InPhieuXuatKhoPdf();
		Document	document=new Document();
		FileOutputStream outstream;
		IPhieuxuatkho pxkBean=new Phieuxuatkho("138173");
		try
		{
			outstream = new FileOutputStream(FILE);
			svl.createOrderDetails(	document, outstream, pxkBean );
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
}
