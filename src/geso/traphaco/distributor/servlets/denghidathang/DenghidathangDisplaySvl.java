package geso.traphaco.distributor.servlets.denghidathang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.denghidathang.IDenghidathang;
import geso.traphaco.distributor.beans.denghidathang.imp.Denghidathang;
import geso.traphaco.distributor.beans.dondathang.IDondathang;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

 public class DenghidathangDisplaySvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
	public DenghidathangDisplaySvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if(!util.check(userId, userTen, sum)){
			response.sendRedirect("/index.jsp");
		}else{
			session.setMaxInactiveInterval(1200);
//		request.getRemoteUser()
			String querystring = request.getQueryString();
			out.println(userId);
	    
			String id = util.getId(querystring);  	
			out.println(id);
	   
			IDenghidathang dndhBean = (IDenghidathang) new Denghidathang();
			dndhBean.setId(id);
			dndhBean.setUserId(userId);
			dndhBean.init2();
			session.setAttribute("dndhBean", dndhBean);
			out.print(dndhBean.getMessage());
			String nextJSP = "/TraphacoHYERP/pages/Distributor/DeNghiDatHangDisplay.jsp";
			response.sendRedirect(nextJSP);
		}
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if(!util.check(userId, userTen, sum)){
			response.sendRedirect("/index.jsp");
		}else{
			session.setMaxInactiveInterval(1200);
			IDenghidathang dndhBean = (IDenghidathang) new Denghidathang();
	    
			dndhBean.setUserId(userId);
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null){
				nppId = "";
			}
	    
			if (nppId.length()==0){
				response.sendRedirect("/TraphacoHYERP/index.jsp");
			}
			else{
				dndhBean.setNppId(nppId);
			}

	    
			boolean error = false;
	    
			String kbhId = util.ValidateParam(util.antiSQLInspection(request.getParameter("kbhId")));
	    
			if (kbhId.length()==0){
				dndhBean.setMessage("Vui lòng chọn kênh bán hàng");
				error = true;
			}else{
				dndhBean.setKbhId(kbhId);
			}

			String dvkdId = util.ValidateParam(util.antiSQLInspection(request.getParameter("dvkdId")));
		
	    
			if (dvkdId.length()==0){
				dndhBean.setMessage("Vui lòng chọn đơn vị kinh doanh");
				error = true;
			}else{
				dndhBean.setDvkdId(dvkdId);
			}

			String nccId = util.ValidateParam(util.antiSQLInspection(request.getParameter("nccId")));
	    
			if (nccId.length()==0){
				dndhBean.setMessage("Vui lòng chọn nhà cung cấp");	    	
				error = true;
			}else{
				dndhBean.setNccId(nccId);
			}
	    
			String ngaydn = util.ValidateParam(util.antiSQLInspection(request.getParameter("ngaydn")));
		
			if(ngaydn.length()==0){
				dndhBean.setMessage("Vui lòng chọn ngày đặt hàng");
				error = true;
			}else{
				dndhBean.setNgaydn(ngaydn);
			}
	    
			String tanso = util.ValidateParam(util.antiSQLInspection(request.getParameter("tanso")));
			
			if(tanso.length()==0){
				dndhBean.setMessage("Vui lòng nhập tần suất đặt hàng");
				error = true;
			}else{
				dndhBean.setTanso(tanso);
			}
	    
			


			String action = request.getParameter("action");
			if (action.equals("print")){
				String id = util.antiSQLInspection(request.getParameter("id"));
				dndhBean.setId(id);

				response.setContentType("application/pdf");
				dndhBean.init2();
				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				this.CreateDondathangHPC(document, outstream, dndhBean);
	    	
			}else{
	    
				if (error){
					dndhBean.init0();
					session.setAttribute("dndhBean", dndhBean);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DeNghiDatHangParam.jsp";
					response.sendRedirect(nextJSP);    		
	    	
				}else{
	    	
					dndhBean.init1();
					session.setAttribute("dndhBean", dndhBean);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DeNghiDatHangDisplay.jsp";
					response.sendRedirect(nextJSP);    		
				}
			}
		}
	}
	private void CreateDondathangHPC(Document document, ServletOutputStream outstream, IDenghidathang dndhBean) throws IOException
	{
		try{			
			
		    PdfWriter.getInstance(document, outstream);
			document.open();
			
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 9, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font3 = new Font(bf, 8, Font.UNDERLINE);
			 
			Paragraph ddh = new Paragraph("Đề Nghị Đặt Hàng", font);
			ddh.setSpacingAfter(10);
			ddh.setAlignment(Element.ALIGN_CENTER);
			document.add(ddh);
			
			PdfPTable tableHead = new PdfPTable(4);
			tableHead.setWidthPercentage(100);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(10);
			float[] with = {15.0f, 50.0f, 20f, 18.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("Tên NPP: ", font3));
			PdfPCell cell2 = new PdfPCell(new Paragraph(dndhBean.getNppTen(), font2));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Số Fax OneOne: ", font3));
			PdfPCell cell4 = new PdfPCell(new Paragraph("(08)3.. ... ...", font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			cell3.setBorder(0);
			cell4.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);
			tableHead.addCell(cell4);
			
			PdfPCell cell5 = new PdfPCell(new Paragraph("Tỉnh/ TP: ", font3));
			PdfPCell cell6 = new PdfPCell(new Paragraph("", font2));
			PdfPCell cell7 = new PdfPCell(new Paragraph("ĐT/ Fax NPP: ", font3));
			PdfPCell cell8 = new PdfPCell(new Paragraph("", font2));
			cell5.setBorder(0);
			cell6.setBorder(0);
			cell7.setBorder(0);
			cell8.setBorder(0);
			tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			tableHead.addCell(cell7);
			tableHead.addCell(cell8);
			
			PdfPCell cell9 = new PdfPCell(new Paragraph("Ngày Đề Nghị: ", font3));
			PdfPCell cell10 = new PdfPCell(new Paragraph(dndhBean.getNgaydn(), font2));
			cell9.setBorder(0);
			cell10.setBorder(0);
			cell10.setColspan(3);
			tableHead.addCell(cell9);
			tableHead.addCell(cell10);
							
			document.add(tableHead);
			
			//Table Content
			PdfPTable table = new PdfPTable(11);
			table.setWidthPercentage(110);
			float[] withs = {18.0f, 35.0f, 9.0f, 9.0f, 11.0f, 12.0f, 9.0f, 10.0f, 10.0f, 11.0f, 11.0f};
	        table.setWidths(withs);
	        
			String[] th = new String[]{"Mã", "Tên", "Tồn đầu", "Tồn cuối","Bán/tuần","TB Bán/ngày", "Tồn ngày", "Dự kiến", "Đề nghị", "Đ. giá","T.Tiền"};
			
			PdfPCell[] cell = new PdfPCell[11];
			for(int i=0; i <= 10 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setPadding(5);
				cell[i].setBackgroundColor(BaseColor.WHITE);
							
				table.addCell(cell[i]);
				
			}
			
		    String[] masp = dndhBean.getMasp() ; 
		    String[] tensp = dndhBean.getTensp(); 		    
		    String[] td  = dndhBean.getTondau();
		    String[] tc  = dndhBean.getToncuoi();
		    String[] ban = dndhBean.getBan();
		    String[] tbban = dndhBean.getTbban();
		    String[] tonngay = dndhBean.getTonngay();
		    String[] dukien = dndhBean.getDukien();
		    String[] dn = dndhBean.getSl();
		    String[] dg = dndhBean.getDongia() ;
		    String[] tt = dndhBean.getTienbVAT() ; 
		    NumberFormat formatter = new DecimalFormat("#,###,###.#");
		    
			for(int j=0; j < Integer.valueOf(dndhBean.getSize()).intValue(); j++)
			{
				Font font4 = new Font(bf, 7);
//				if(j % 2 != 0) 	font4.setColor(BaseColor.GREEN);
					
				cell[0] = new PdfPCell(new Paragraph(masp[j], font4));		
				cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[0].setPadding(4);
				table.addCell(cell[0]);				
				
				cell[1] = new PdfPCell(new Paragraph(tensp[j], font4));		
				cell[1].setHorizontalAlignment(Element.ALIGN_LEFT);
				cell[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[1].setPadding(4);
				table.addCell(cell[1]);				
				
				cell[2] = new PdfPCell(new Paragraph(formatter.format(Float.parseFloat(td[j].replace(",", ""))), font4));		
				cell[2].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[2].setPadding(4);
				table.addCell(cell[2]);				
				
				cell[3] = new PdfPCell(new Paragraph(formatter.format(Float.parseFloat(tc[j].replace(",", ""))), font4));		
				cell[3].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[3].setPadding(4);
				table.addCell(cell[3]);				

				cell[4] = new PdfPCell(new Paragraph(formatter.format(Float.parseFloat(ban[j].replace(",", ""))) , font4));		
				cell[4].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[4].setPadding(4);
				table.addCell(cell[4]);				

				cell[5] = new PdfPCell(new Paragraph(formatter.format(Float.parseFloat(tbban[j].replace(",", ""))), font4));		
				cell[5].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[5].setPadding(4);
				table.addCell(cell[5]);				

				cell[6] = new PdfPCell(new Paragraph(formatter.format(Float.parseFloat(tonngay[j].replace(",", ""))), font4));		
				cell[6].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[6].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[6].setPadding(4);
				table.addCell(cell[6]);				

				cell[7] = new PdfPCell(new Paragraph(formatter.format(Float.parseFloat(dukien[j].replace(",", ""))), font4));		
				cell[7].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[7].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[7].setPadding(4);
				table.addCell(cell[7]);

				cell[8] = new PdfPCell(new Paragraph(dn[j].replace(",", ""), font4));		
				cell[8].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[8].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[8].setPadding(4);
				table.addCell(cell[8]);
				
				cell[9] = new PdfPCell(new Paragraph(formatter.format(Float.parseFloat(dg[j].replace(",", ""))), font4));		
				cell[9].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[9].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[9].setPadding(4);
				table.addCell(cell[9]);

				cell[10] = new PdfPCell(new Paragraph(formatter.format(Float.parseFloat(tt[j].replace(",", ""))), font4));		
				cell[10].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[10].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[10].setPadding(4);
				table.addCell(cell[10]);

			}		
			document.add(table);
			
			//Table Footer
			PdfPTable tableFooter = new PdfPTable(2);
			tableFooter.setWidthPercentage(40);
			tableFooter.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableFooter.setSpacingBefore(10);
			float[] with3 = {25.0f, 25.0f}; 
			tableFooter.setWidths(with3);
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("Tổng Tiền: ", new Font(bf, 9, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cell12 = new PdfPCell(new Paragraph(formatter.format(Long.parseLong(dndhBean.getTongtienbVAT())), new Font(bf, 9, Font.ITALIC)));
			cell11.setBorder(0);
			cell12.setBorder(0);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			
			cell11 = new PdfPCell(new Paragraph("Thuế VAT (10%): ", new Font(bf, 9, Font.BOLDITALIC)));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell12 = new PdfPCell(new Paragraph(formatter.format(Long.parseLong(dndhBean.getVat())), new Font(bf, 9, Font.ITALIC)));
			cell11.setBorder(0);
			cell12.setBorder(0);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			
			cell11 = new PdfPCell(new Paragraph("Tổng Giá Trị: ", new Font(bf, 9, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell12 = new PdfPCell(new Paragraph(formatter.format(Long.parseLong(dndhBean.getTongtienaVAT())), new Font(bf, 9, Font.ITALIC)));
			cell11.setBorder(0);
			cell12.setBorder(0);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			
			document.add(tableFooter);
			
			document.close(); 
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
		dndhBean.DBclose();
	}
	
}