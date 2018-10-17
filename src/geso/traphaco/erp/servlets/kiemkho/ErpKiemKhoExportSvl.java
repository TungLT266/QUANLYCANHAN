package geso.traphaco.erp.servlets.kiemkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kiemkho.IErpKiemKho;
import geso.traphaco.erp.beans.kiemkho.imp.ErpKiemKho;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/ErpKiemKhoExportSvl") 
public class ErpKiemKhoExportSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	
	public ErpKiemKhoExportSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		
		
			
			String nextJSP = "";
			util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			IErpKiemKho bean = new ErpKiemKho();
			bean.setNguoiSua(userId);
			bean.setNguoiTao(userId);
			bean.CreateRsKho();
			bean.CreateRsLoaiSanPham();
			bean.CreateRsMaLonSanPham();
			bean.setNguoiSua(userId);
		    nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemKhoExport.jsp";
			session.setAttribute("bean", bean);
			response.sendRedirect(nextJSP);
		
	}
	
	private void XuatFileExcel(HttpServletResponse response,IErpKiemKho bean ) throws IOException {
		// TODO Auto-generated method stub
		OutputStream out1 = null;
		  try {
			   response.setContentType("application/vnd.ms-excel");
			   response.setHeader("Content-Disposition", "attachment; filename=KiemKho_"+this.getDateTime()+".xls");
			   WritableWorkbook w = 
			   Workbook.createWorkbook(response.getOutputStream());
			   WritableSheet sheet = w.createSheet("KiemKho", 0);
			   dbutils db=new dbutils();
			   
				sheet.setColumnView(0, 10);
				sheet.setColumnView(1, 30);
				sheet.setColumnView(2, 100);
				sheet.setColumnView(3, 30);
		
				WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
				cellTitle.setColour(Colour.BLACK);
				cellTitle.setBoldStyle(WritableFont.BOLD);
		 
			   //***** LẤY SỐ LƯỢNG TỒN RA ****//
			   String[] param = new String[3];
			    param[0] =bean.getKhoTT_FK();
			    param[1] =bean.getTungay();
			    param[2] =bean.getDenngay();
			     String query=" delete sanpham_tmp   ";
			     db.update(query);
			    
			     query=" insert into sanpham_tmp " +
			     	   " select sanpham_fk from erp_khott_sanpham kho inner join erp_sanpham sp on sp.pk_seq=kho.sanpham_fk " +
			     	   " where  khott_fk= "+bean.getKhoTT_FK();
			     if(bean.getLoaisanpham().length() > 0){
			    	 query+=" and  sp.loaisanpham_fk= "+bean.getLoaisanpham();
			     }
			     		
			     db.update(query);
			     
			    ResultSet   tongHopNXT = db.getRsByPro("REPORT_XUATNHAPTON_TT", param);
			     
			    sheet.addCell(new Label(0, 0, "BẢNG KÊ TỒN KHO", new WritableCellFormat(cellTitle)));
			 
			    cellTitle = new WritableFont(WritableFont.TIMES, 12);
			    cellTitle.setColour(Colour.BLACK);
			    sheet.addCell(new Label(0, 1, "Từ ngày: " + bean.getTungay() + "    Đến ngày : "+ bean.getDenngay(), new WritableCellFormat(cellTitle)));
			   
			    WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
				cellFont.setColour(Colour.BLACK); 

				WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

				cellFormat.setBackground(jxl.format.Colour.GRAY_25);
				cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			   
			   
			   sheet.addCell(new Label(0, 4, "MÃ HÀNG", cellFormat));
			   sheet.addCell(new Label(1, 4, "TÊN SẢN PHẨM", cellFormat));
			   sheet.addCell(new Label(2, 4, "LOẠI SP", cellFormat));
			   sheet.addCell(new Label(3, 4, "NHÃN HÀNG", cellFormat));
			   sheet.addCell(new Label(4, 4, "CHỦNG LOẠI", cellFormat));
			   sheet.addCell(new Label(5, 4, "ĐVT", cellFormat));
			   sheet.addCell(new Label(6, 4, "ID SẢN PHẨM", cellFormat));
			   sheet.addCell(new Label(7, 4, "SỐ LƯỢN TỒN", cellFormat));
			   sheet.addCell(new Label(8, 4, "SỐ LƯỢNG MỚI", cellFormat));
 
			   if(tongHopNXT!=null){				   

				   
				   int i=5;
				   while(tongHopNXT.next()){
					   
					   
					   double tondau = tongHopNXT.getDouble("TONDAUKY");
					   double soluongnhap = tongHopNXT.getDouble("TONGNHAP");
					   double soluongxuat = tongHopNXT.getDouble("TONGXUAT");
					   
					   double soluongton = tondau + soluongnhap - soluongxuat;
					   
					   
					   sheet.addCell(new Label(0, i, tongHopNXT.getString("SPMA")));
					   sheet.addCell(new Label(1, i, tongHopNXT.getString("SPTEN")));
					   sheet.addCell(new Label(2, i, tongHopNXT.getString("LSP")));
					   sheet.addCell(new Label(3, i, tongHopNXT.getString("NH")));
					   sheet.addCell(new Label(4, i, tongHopNXT.getString("CL")));
					   sheet.addCell(new Label(5, i, tongHopNXT.getString("DONVI")));
					   
					   sheet.addCell(new Label(6, i, tongHopNXT.getString("PK_SEQ")));
					   sheet.addCell(new Number(7, i, soluongton));
					   sheet.addCell(new Number(8, i, 0));

					   i++;
					   
				   }
				   tongHopNXT.close();
				   db.shutDown();
				   w.write();
				   w.close();
			   }
		  } 
		  catch (Exception e){
			  e.printStackTrace();
			 
			  System.out.println("Error Cac Ban : "+e.toString());
		  } 
		  finally{
			  
		   if (out1 != null)
			   out1.close();
		  }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		
		IErpKiemKho bean = new ErpKiemKho();
		bean.setNguoiTao(userId);
		
		bean.CreateRsKho();
		bean.CreateRsLoaiSanPham();
		bean.setNguoiSua(userId);
				
		String action = request.getParameter("action");
		
		String khott_fk =  request.getParameter("khott_fk");
		bean.setKhoTT_FK(khott_fk);
		
		String loaisanpham =  request.getParameter("loaisanpham");
		bean.setLoaisanpham(loaisanpham);
		
		String malonsanpham =  request.getParameter("malonsanpham");
		bean.setMalonsanpham(malonsanpham);
	 
		String tungay = request.getParameter("tungay");
		if(tungay==null){
			tungay="";
		}
		bean.setTungay(tungay);

		
		String denngay = request.getParameter("denngay");
		if(denngay==null){
			denngay="";
		}
		bean.setDenngay(denngay);
		
		
		
		String ticksoluong =  request.getParameter("ticksoluong");
		if(ticksoluong == null)
			ticksoluong = "0";
		else
			ticksoluong = "1";
		bean.setTicksoluong(ticksoluong);
		
		bean.CreateRsMaLonSanPham();
		
/*		System.out.println("khott_fk : " + khott_fk);
		System.out.println("loaisanpham : " + loaisanpham);
		System.out.println("malonsanpham : " + malonsanpham);
		System.out.println("ticksoluong : " + ticksoluong);*/
		
		if(action.equals("excel")){
			XuatFileExcel(response,bean);
		}else if(action.equals("ExportPdf")){
			response.setContentType("application/pdf");
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			XuatFilePdf(document,outstream,bean);
		}else{
			String	nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemKhoExport.jsp";
			
			session.setAttribute("bean", bean);
			response.sendRedirect(nextJSP);
		}
		
	}

	
	private void XuatFilePdf(Document document, ServletOutputStream outstream,IErpKiemKho dhBean ) throws IOException
	{
		try
		{		
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			PdfWriter.getInstance(document, outstream);
			document.open();
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\COUR.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
//			Font font3 = new Font(bf, 8, Font.UNDERLINE);
			Font fontnomar=new Font(bf,10,Font.NORMAL);
//			Font font8normal=new Font(bf,8,Font.NORMAL);
			//font2.setColor(BaseColor.GREEN);
			 //KHAI BAO 1 BANG CO BAO NHIEU COT
			String str_tieude="";
			str_tieude="BẢNG KÊ TỒN KHO";
			Paragraph tieude=new Paragraph(str_tieude, font);
			tieude.setAlignment(Element.ALIGN_CENTER);
			tieude.setSpacingAfter(50);
			document.add(tieude);
		 
			dbutils db=new dbutils();
			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {3.0f,11.0f,11.0f,24.0f, 10.0f, 8.0f}; 			
	        table.setWidths(withs);
			String[] th = new String[]{"STT","ID Sản phẩm", "Mã sản phẩm", "Tên Hàng ", "Số lượng tồn", 
											"Số lượng kiểm kê"};
			PdfPCell[] cell = new PdfPCell[9];
			for(int i=0; i < 6 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell[i]);			
			}
			
			
			float size= 8.5f;
			Font font4 = new Font(bf,size );
			PdfPCell cells_detail = new PdfPCell();
		
			  
			  String[] param = new String[3];
			    param[0] =dhBean.getKhoTT_FK();
			    param[1] =dhBean.getTungay();
			    param[2] =dhBean.getDenngay();
			    String query="delete sanpham_tmp   ";
			     db.update(query);
			    
			     query=" insert into sanpham_tmp " +
			     	   " select sanpham_fk from erp_khott_sanpham kho inner join erp_sanpham sp on sp.pk_seq=kho.sanpham_fk " +
			     	   " where  khott_fk= "+dhBean.getKhoTT_FK();
			     if(dhBean.getLoaisanpham().length() > 0){
			    	 query+=" and  sp.loaisanpham_fk= "+dhBean.getLoaisanpham();
			     }
			     		
			     db.update(query);
			     
			    ResultSet   rs = db.getRsByPro("REPORT_XUATNHAPTON_TT", param);
			    
				int i=0;
				if(rs!=null){
				while(rs.next()){
				   double tondau = rs.getDouble("TONDAUKY");
				   double soluongnhap = rs.getDouble("TONGNHAP");
				   double soluongxuat = rs.getDouble("TONGXUAT");
				   double soluongton = tondau + soluongnhap - soluongxuat;
					String[] arr = new String[]{Integer.toString(i+1),rs.getString("PK_SEQ"),
							rs.getString("SPMA"),  rs.getString("SPTEN"), 
							 ""+ formatter.format(soluongton),
												""};
					for(int j=0; j < 6; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if(j<=4)
						{
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
							cells_detail.setMinimumHeight(20);
						}
						else 
						{
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						
						
						table.addCell(cells_detail);
					}
				i++;
				
			}
				rs.close();
			}	
			document.add(table);
			Paragraph ngaytao=new Paragraph("Ngày In: " + this.getDateTime(),fontnomar);
			ngaytao.setSpacingAfter(10);
			ngaytao.setAlignment(Element.ALIGN_RIGHT);
			document.add(ngaytao);
			
			db.shutDown();
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}
