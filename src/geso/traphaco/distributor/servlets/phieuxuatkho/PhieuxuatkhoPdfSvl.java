package geso.traphaco.distributor.servlets.phieuxuatkho;

import geso.traphaco.distributor.beans.phieuxuatkho.IPhieuxuatkho;
import geso.traphaco.distributor.beans.phieuxuatkho.imp.Phieuxuatkho;

import geso.traphaco.distributor.beans.donhang.ISanpham;
import geso.traphaco.distributor.beans.donhang.imp.Sanpham;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class PhieuxuatkhoPdfSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	float CONVERT = 28.346457f; // = 1cm
	
    public PhieuxuatkhoPdfSvl()
    {
        super();      
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
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
			    List<ISanpham> sanphamList = this.createPxk_SpList(id, db);
				List<ISanpham> sanphamKMList = this.createPxk_SpkmList(id, db);
				List<ISanpham> tienKMList = this.createPxk_TienkmList(id, db);
			    
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition"," inline; filename=Phieuxuatkho.pdf");
				
				//hien thi hop thoai dialog save file xuong may Client

				
				Document document = new Document(PageSize.A5.rotate());
				ServletOutputStream outstream = response.getOutputStream();
				document.setMargins(CONVERT, CONVERT, CONVERT*0.5f, CONVERT);
				document.setMarginMirroring(true);
				
				System.out.println("[Init__pdf]");
				
				this.CreatePxk(document, outstream, sanphamList, sanphamKMList, tienKMList, pxkBean);
				
				pxkBean.DBclose();
				pxkBean=null;
				sanphamList=null;
				sanphamKMList=null;
				tienKMList=null;
				util=null;
		    }
		    
		}
		else
		{
			System.out.println("vao day ................xxx");
			pxkBean = (IPhieuxuatkho)session.getAttribute("pxkBean");
			
			db = new dbutils();
			List<ISanpham> sanphamList = pxkBean.getPxk_spList();
			List<ISanpham> sanphamKMList = pxkBean.getPxk_spkmList();
			List<ISanpham> tienKMList = pxkBean.getPxk_tienkmList();
			
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition"," inline; filename=Phieuxuatkho.pdf");
			
			//hien thi hop thoai dialog save file xuong may Client
			//response.setHeader("Content-Disposition"," attachment; filename=\"Phieuxuatkho.pdf\" ");
			
			Document document = new Document(PageSize.A4);
			ServletOutputStream outstream = response.getOutputStream();
			this.CreatePxk(document, outstream, sanphamList, sanphamKMList, tienKMList, pxkBean);
		
			pxkBean.DBclose();
			pxkBean=null;
			sanphamList=null;
			sanphamKMList=null;
			tienKMList=null;
			util=null;
		}
			if(db!=null){
				db.shutDown();
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	
	private void CreatePxk(Document document, ServletOutputStream outstream, List<ISanpham> spList, List<ISanpham> sanphamKMList, List<ISanpham> tienKMList, IPhieuxuatkho pxkBean) throws IOException
	{
		try
		{			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			PdfWriter.getInstance(document, outstream);
			document.open();

			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 12, Font.BOLD);
			Font font2 = new Font(bf, 9, Font.BOLD);
			Font font3 = new Font(bf, 9, Font.UNDERLINE);
			//font2.setColor(BaseColor.GREEN);
			 
			Paragraph pxk = new Paragraph("In ngày: " + getDateTime(), new Font(bf, 6, Font.BOLDITALIC));
			pxk.setSpacingAfter(2);
			pxk.setSpacingBefore(-25);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Phiếu xuất kho", font);
			pxk.setSpacingAfter(3);
			pxk.setSpacingBefore(-15);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph("(Ngày lập phiếu: " + pxkBean.getNgaylap() + "  --   Số phiếu: " + pxkBean.getId() + ")", font2);
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			PdfPTable tableHead = new PdfPTable(2);
			tableHead.setWidthPercentage(100);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(5);
			float[] with = {10.0f, 60.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("NV giao nhận: ", font3));
			PdfPCell cell2 = new PdfPCell(new Paragraph(pxkBean.getNvgnTen(), font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
	
			PdfPCell cell5 = new PdfPCell(new Paragraph("Nhà phân phối: ", font3));
			PdfPCell cell6 = new PdfPCell(new Paragraph(pxkBean.getNppTen(), font2));
			cell5.setBorder(0);
			cell6.setBorder(0);
			tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			
			document.add(tableHead);
			
			//Table Content
		/*	PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = {195.0f, 0};
			root.setWidths(cr);*/
			//phan nay de in ma vach kenh MT
			 boolean bien=false;
			 int dong=8;  
			 if(spList.size() >0){
			  Sanpham sanpham1 = (Sanpham)spList.get(0);
		    	System.out.println("Kenh  : "+sanpham1.getDongia());
		    	if(sanpham1.getDongia().equals("Modern Trade")){
		    		bien=true;
		    		dong=9;
		    	}
			 }
		    	
			
			PdfPTable table = new PdfPTable(dong);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			
			if(bien){
				float[]  withs1 = {6.0f, 22.0f,22.0f, 30.0f, 8.0f, 11.0f,11.0f,11.0f,11.0f};
				  table.setWidths(withs1);
			}else{
				float[] withs = {7.0f, 22.0f, 40.0f, 13.0f, 8.0f,8.0f,8.0f,15.0f};
				 table.setWidths(withs);
			}
			
	      
	        
	        Font font4 = new Font(bf, 7);
			PdfPCell cells = new PdfPCell();
			
			PdfPCell tieude = new PdfPCell(new Paragraph("Hàng bán", font2));
			tieude.setColspan(dong);
			tieude.setBorder(0);
			tieude.setPadding(4);
			table.addCell(tieude);
			
	        if(spList.size() > 0)
	        {
	        	
				String[] th = new String[]{"STT", "Mã sản phẩm", "Tên sản phẩm","Kho / NVBH", "Số lượng","Thùng","Lẻ", "Số lô"};
	        	if(bien)
	        	{
	        		th = new String[]{"STT", "Mã sản phẩm","Mã vạch", "Tên sản phẩm","Kho / NVBH", "Số lượng","Thùng","Lẻ", "Số lô"};	        		
	        	}
				System.out.println(dong);
				PdfPCell[] cell = new PdfPCell[dong];
				for(int i= 0; i < dong ; i++)
				{
					cell[i] = new PdfPCell(new Paragraph(th[i], font4));
					cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[i].setPadding(2);
					//cell[i].setBackgroundColor(BaseColor.CYAN);
					table.addCell(cell[i]);			
				}
				
				double soluong = 0;
				double soluongthung = 0;
				double soluongle = 0;
				for(int i = 0; i < spList.size(); i++)
				{
					Sanpham sanpham = (Sanpham)spList.get(i);
					
					double quicach = Double.parseDouble(sanpham.getQuicach().replaceAll(" ", ""));
					double sl = Double.parseDouble(sanpham.getSoluong().replaceAll(" ", ""));
					String  thung = "0";
					String le = "0";
					if(quicach != 0)
					{
						double thg = Math.round( ( sl / quicach ) - 0.5 );
						if(thg > 0)
							thung = formatter.format(thg);
						double sole = sl % quicach;
						if(sole > 0)
							le = formatter.format(sole);
						
					}
					
					String[] arr = new String[]{Integer.toString(i+1), sanpham.getMasanpham(), sanpham.getTensanpham(), sanpham.getKhoTen(), formatter.format(Double.parseDouble(sanpham.getSoluong())), thung, le, sanpham.getSOLo()};
					if(bien){
						arr = new String[]{Integer.toString(i+1), sanpham.getMasanpham(),sanpham.getBarcode(), sanpham.getTensanpham(), sanpham.getKhoTen(), formatter.format(Double.parseDouble(sanpham.getSoluong())), thung, le, sanpham.getSOLo()};
					}
					//System.out.println(sanpham.getMasanpham() + "\n");
					
					for(int j = 0; j < dong; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font4));
						if( j==2 )
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(2);
						table.addCell(cells);
					}
					soluong += Double.parseDouble(sanpham.getSoluong());
					soluongle+= Double.parseDouble(le);
					soluongthung += Double.parseDouble(thung);
				}	
				cells = new PdfPCell(new Paragraph("Tổng cộng", font4));
				cells.setColspan(4);
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(5);
				table.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(formatter.format(soluong), font4));
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(5);
				table.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(formatter.format(soluongthung), font4));
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(5);
				table.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(formatter.format(soluongle), font4));
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(5);
				table.addCell(cells);
				
				cells = new PdfPCell(new Paragraph("", font4));
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(5);
				table.addCell(cells);
	        }	        
	        document.add(table);	        
	       
			PdfPTable table2 = new PdfPTable(5);
			table2.setWidthPercentage(100);
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] widths = {13.0f, 50.0f, 14.0f, 14.0f, 14.0f};
	        table2.setWidths(widths);

	        tieude = new PdfPCell(new Paragraph("Danh sách khách hàng", font2));
			tieude.setColspan(5);
			tieude.setBorder(0);
			tieude.setPadding(4);
			table2.addCell(tieude);
			
			String[] th2 = new String[]{"Mã", "Tên khách hàng", "Hóa đơn", "Số tiền", "Nợ cũ"};
			 int dongkh=5;
			
		  
		   
			PdfPCell[] c = new PdfPCell[dongkh];
			
			for(int i = 0; i < dongkh ; i++)
			{
				c[i] = new PdfPCell(new Paragraph(th2[i], font4));
				c[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				c[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				c[i].setPadding(2);
				//cell[i].setBackgroundColor(BaseColor.CYAN);
				table2.addCell(c[i]);			
			}

			ResultSet rs = pxkBean.getDhIdsList();
			Hashtable<String, Long> credit = pxkBean.getCredits();
			
			if (rs != null)
			{
				try
				{
					int i = 1;
					float total = 0;
					long nocu=0;
					while(rs.next())
					{								
						c[0] = new PdfPCell(new Paragraph(rs.getString("smartid"), font4));
						c[0].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[0].setPadding(2);
						//	cell[i].setBackgroundColor(BaseColor.CYAN);
						table2.addCell(c[0]);
						
						c[1] = new PdfPCell(new Paragraph(rs.getString("khTen")+ " - " + rs.getString("dc"), font4));
						c[1].setHorizontalAlignment(Element.ALIGN_LEFT);
						c[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[1].setPadding(2);
						//	cell[i].setBackgroundColor(BaseColor.CYAN);
						table2.addCell(c[1]);

						c[2] = new PdfPCell(new Paragraph(rs.getString("dhId"), font4));
						c[2].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[2].setPadding(2);
						//	cell[i].setBackgroundColor(BaseColor.CYAN);
						table2.addCell(c[2]);
						
						float tonggtri = 0;
						if(rs.getString("tonggiatri") != null)
						{
							tonggtri = Float.parseFloat(rs.getString("tonggiatri"));
						}
						total += tonggtri;
						
						c[3] = new PdfPCell(new Paragraph(formatter.format(tonggtri).toString(), font4));
						c[3].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[3].setPadding(2);
						//	cell[i].setBackgroundColor(BaseColor.CYAN);
						table2.addCell(c[3]);
						if(rs.getString("khId") != null)
						{
							if(credit.containsKey(rs.getString("khId")))
							{
								nocu = nocu + (Long)credit.get(rs.getString("khId")).longValue();
								c[4] = new PdfPCell(new Paragraph(formatter.format((Long)credit.get(rs.getString("khId")).longValue()), font4));
							}
							else{
								c[4] = new PdfPCell(new Paragraph("", font4));
							}
						}
						c[4].setHorizontalAlignment(Element.ALIGN_CENTER);
						c[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
						c[4].setPadding(2);
						//	cell[i].setBackgroundColor(BaseColor.CYAN);
						table2.addCell(c[4]);
						i++;
					}
					c[0] = new PdfPCell(new Paragraph("Tổng cộng", font4));
					c[0].setHorizontalAlignment(Element.ALIGN_CENTER);
					c[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
					c[0].setPadding(5);
					//	cell[i].setBackgroundColor(BaseColor.CYAN);
					c[0].setColspan(2);
					table2.addCell(c[0]);
					
					c[1] = new PdfPCell(new Paragraph(Integer.toString(i-1), font4));
					c[1].setHorizontalAlignment(Element.ALIGN_CENTER);
					c[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
					c[1].setPadding(5);
					//	cell[i].setBackgroundColor(BaseColor.CYAN);
					
					table2.addCell(c[1]);

					c[2] = new PdfPCell(new Paragraph(formatter.format(Math.round(total)).toString(), font4));
					c[2].setHorizontalAlignment(Element.ALIGN_CENTER);
					c[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
					c[2].setPadding(2);
					//	cell[i].setBackgroundColor(BaseColor.CYAN);
					
					table2.addCell(c[2]);

					c[3] = new PdfPCell(new Paragraph(formatter.format(nocu).toString(), font4));
					c[3].setHorizontalAlignment(Element.ALIGN_CENTER);
					c[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
					c[3].setPadding(2);
					//	cell[i].setBackgroundColor(BaseColor.CYAN);
					table2.addCell(c[3]);
					
					cells = new PdfPCell(new Paragraph(" ", font4));
					cells.setColspan(5);
					cells.setPadding(1);
					cells.setBorder(0);
					table2.addCell(cells);
				}
				catch(Exception e){}
			}
			
			//root.addCell(table2);

			//document.add(root);
			
			if(sanphamKMList.size() > 0 || tienKMList.size() > 0 )
			{
			int	dongkm=9;
			if(bien){
				dongkm=10;
			}
				PdfPTable tableKM = new PdfPTable(dongkm);
				tableKM.setWidthPercentage(100);
				tableKM.setHorizontalAlignment(Element.ALIGN_LEFT);
				if(bien){
					float[] withsKM = {7.0f, 65.0f, 20.0f,20.0f, 30.0f, 18.0f, 15.0f,15.0f,15.0f,15.0f};
				    tableKM.setWidths(withsKM);
				}else{
					float[] withsKM = {7.0f, 65.0f, 20.0f, 30.0f, 18.0f, 15.0f,15.0f,15.0f,15.0f};
				    tableKM.setWidths(withsKM);
				}
			    tieude = new PdfPCell(new Paragraph("Khuyến mãi", font2));
				tieude.setColspan(dongkm);
				tieude.setBorder(0);
				tieude.setPadding(4);
				tableKM.addCell(tieude);
				String[] thKM = new String[]{"STT", "Chương trình", "Mã sản phẩm", "Tên sản phẩm", "Số lượng / Số tiền","Thùng","Lẻ", "Kho / NVBH","Số lô"};
				
				if(bien){
					 thKM = new String[]{"STT", "Chương trình", "Mã sản phẩm","Mã vạch", "Tên sản phẩm", "Số lượng / Số tiền","Thùng","Lẻ", "Kho / NVBH","Số lô"};
				}
				PdfPCell[] cellKM = new PdfPCell[dongkm];
				for(int i = 0; i < dongkm ; i++)
				{
					cellKM[i] = new PdfPCell(new Paragraph(thKM[i], font4));
					cellKM[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cellKM[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellKM[i].setPadding(2);
					tableKM.addCell(cellKM[i]);			
				}
				
				int m = 0;
				while(m < sanphamKMList.size())
				{
					Sanpham sanpham = (Sanpham)sanphamKMList.get(m);
					String masanpham=sanpham.getMasanpham();
					double quicach=Double.parseDouble(sanpham.getQuicach().replaceAll(" ", ""));
					double sl=Double.parseDouble(sanpham.getSoluong().replaceAll(" ", ""));
					String  thung="0";
					String le="0";
					if(quicach!=0)
					{
						double thg = Math.round( ( sl / quicach ) - 0.5 );
						if(thg > 0)
							thung = formatter.format(thg);
						double sole = sl % quicach;
						if(sole > 0)
							le = formatter.format(sole);
						
					}
					
					String[] arr = new String[]{Integer.toString(m+1), sanpham.getCTKM(), masanpham, sanpham.getTensanpham(), formatter.format(Double.parseDouble(sanpham.getSoluong())),thung,le ,sanpham.getKhoTen(), sanpham.getSOLo()};
					if(bien){
						 arr = new String[]{Integer.toString(m+1), sanpham.getCTKM(), masanpham, sanpham.getBarcode(), sanpham.getTensanpham(), formatter.format(Double.parseDouble(sanpham.getSoluong())), thung, le,  sanpham.getKhoTen(), sanpham.getSOLo()};
					}
					cells = new PdfPCell();
					
					for(int j = 0; j < dongkm; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==3 || j == 1)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(2);
						tableKM.addCell(cells);
					}
					m++;
				}		
				
				int n = 0;			
				while( n < tienKMList.size())
				{
					Sanpham sanpham = (Sanpham)tienKMList.get(n);
					String tienkm=sanpham.getChietkhau();
					if(tienkm==null||tienkm.equals("null")||tienkm.length()==0)
						tienkm="";
					else tienkm =formatter.format(Double.parseDouble(tienkm));
					String[] arr = new String[]{Integer.toString(m), sanpham.getCTKM(), "", "", tienkm, "", "","",""};
					if(bien){
						arr = new String[]{Integer.toString(m), sanpham.getCTKM(), "","", "", tienkm, "", "","",""};

					}
					cells = new PdfPCell();
					
					for(int j = 0; j < dongkm; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==3)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(2);
						tableKM.addCell(cells);
					}
					n++;
					m++;
				}		
					
				document.add(tableKM);
			}
			document.add(table2);
			
			//Table Footer			
			PdfPTable tableFooter = new PdfPTable(4);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setSpacingBefore(15);
			tableFooter.setWidths(new float[]{20.0f, 30.0f, 30.0f, 25.0f});
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("Thủ kho", font2));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell12 = new PdfPCell(new Paragraph("Nhân viên giao nhận", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Kế toán hàng hóa", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Kế toán công nợ", font2));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			document.add(tableFooter);
			document.close(); 
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
			System.out.println("Error Here  : "+e.toString());
		}
	}
	
	private String getDateTime()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return sdf.format(cal.getTime());
	}
	
	private List<ISanpham> createPxk_SpList(String pxkId, dbutils db)
	{
		List<ISanpham> pxk_splist = new ArrayList<ISanpham>();
		String query ="select a.sanpham_fk as spId, b.ma as spMa, sum(splo.soluong) as soluong, " +
				  "b.ten as spTen, case when splo.NVBH_FK is NULL then c.ten else ( select TEN from DaiDienKinhDoanh where pk_seq = splo.NVBH_FK ) end as khoTen, " +
				  "d.ten as kbhTen,isnull(b.barcode,'') " +
				  "as barcode, soluong1 as quicach,splo.SoLo from " +
				  "phieuxuatkho_sanpham a " +
				  "inner join PHIEUXUATKHO_SANPHAM_CHITIET splo on a.SANPHAM_FK=splo.SANPHAM_FK and a.PXK_FK=splo.PXK_FK " +
				  "inner join sanpham b on a.sanpham_fk = b.pk_seq " +
				  "left join kho c on a.kho_fk = c.pk_seq left join kenhbanhang d on a.kbh_fk = d.pk_seq " +
				  "left join quycach q on q.sanpham_fk = a.sanpham_fk and q.dvdl2_fk=100018 " +
				  "left join donvidoluong  d2 on d2.pk_Seq = dvdl2_fk " +
				  "left join donvidoluong d1 on d1.pk_seq = dvdl1_fk where a.pxk_fk = "+pxkId +
				  " group by a.sanpham_fk, b.ma, b.ten , c.ten , d.ten, b.barcode, soluong1,splo.SoLo, splo.NVBH_FK ";
		System.out.print("\nQuery truy van du lieu la:.... " + query + "\n");
		
		ResultSet rsPxk_sp = db.get(query);
		if(rsPxk_sp != null)
		{
			String[] param = new String[10];
			ISanpham sp = null;	
			try 
			{
				while(rsPxk_sp.next())
				{
					  sp=new Sanpham();
					  sp.setMasanpham(rsPxk_sp.getString("spMa"));
					  sp.setTensanpham(rsPxk_sp.getString("spTen"));
					  sp.setSoluong(rsPxk_sp.getString("soluong"));
					  sp.setSPId(rsPxk_sp.getString("SPID"));
					  sp.setQuicach(rsPxk_sp.getInt("quicach")+"");
					  sp.setSolo(rsPxk_sp.getString("SoLo"));
					  sp.setBarcode(rsPxk_sp.getString("barcode"));
					  sp.setKhoTen(rsPxk_sp.getString("khoTen"));
				
					 
					pxk_splist.add(sp);
				}
				rsPxk_sp.close();
			} 
			catch(Exception e) {System.out.println(e.toString());}
			finally{try {
				if(rsPxk_sp != null)
					rsPxk_sp.close();
			} catch (Exception e2) {
				System.out.println(e2.toString());
			}}
		}
		return pxk_splist;
	}
	
	private List<ISanpham> createPxk_SpkmList(String pxkId, dbutils db)
	{
		List<ISanpham> pxk_spkmlist = new ArrayList<ISanpham>();
		String query=
				" select  case when spkmlo.NVBH_FK is NULL then c.ten else ( select TEN from DaiDienKinhDoanh where pk_seq = spkmlo.NVBH_FK ) end as khoTen," +
				"		 d.ten as kbhTen,spkmlo.SOLO,spkmlo.NGAYHETHAN, e.scheme + '-' + e.diengiai as ctkmTen   " +
				  "    , a.sanpham_fk as spId, isnull(b.barcode,'') as barcode  " +
				  "    ,soluong1 as quicach, b.ma as spMa, b.ten as spTen, sum(spkmlo.soluong) as soluong   " +
				  "     from phieuxuatkho_spkm a    " +
				  "     inner join PHIEUXUATKHO_SPKM_CHITIET spkmlo on spkmlo.SANPHAM_FK=a.SANPHAM_FK and spkmlo.PXK_FK=a.PXK_FK and spkmlo.scheme=a.scheme  " +
				  "     inner  join sanpham b   " +
				  "     on a.sanpham_fk = b.pk_seq inner join kho c on a.kho_fk = c.pk_seq inner join kenhbanhang d on a.kbh_fk = d.pk_seq   " +
				  "     inner join ctkhuyenmai e  on a.scheme = e.pk_seq   " +
				  "		left join quycach q on q.sanpham_fk = a.sanpham_fk and q.dvdl2_fk=100018  " +
				  "     left join donvidoluong  d2 on d2.pk_Seq = dvdl2_fk  left join donvidoluong d1 on d1.pk_seq = dvdl1_fk  " +
				  "     where a.pxk_fk ="+ pxkId +
				  " 	group by c.ten, d.ten, e.scheme + '-' + e.diengiai, a.sanpham_fk, b.barcode, soluong1, b.ma, b.ten, spkmlo.SOLO,spkmlo.NGAYHETHAN, spkmlo.NVBH_FK ";
		System.out.println("Get San Pham Khuyen Mai : "+query);
		ResultSet rsPxk_spkm = db.get(query);
		if(rsPxk_spkm != null)
		{	
			try
			{
				while(rsPxk_spkm.next())
				{
					
					ISanpham sp = null;
					
				
					
					 sp=new Sanpham();
					  sp.setMasanpham(rsPxk_spkm.getString("spMa"));
					  sp.setTensanpham(rsPxk_spkm.getString("spTen"));
					  sp.setSoluong(rsPxk_spkm.getString("soluong"));
					  sp.setSPId(rsPxk_spkm.getString("SPID"));
					  sp.setQuicach(rsPxk_spkm.getInt("quicach")+"");
					  sp.setSolo(rsPxk_spkm.getString("SoLo"));
					  
					  sp.setBarcode(rsPxk_spkm.getString("barcode"));
					  sp.setKhoTen(rsPxk_spkm.getString("khoTen"));
					  sp.setCTKM(rsPxk_spkm.getString("ctkmTen"));
					pxk_spkmlist.add(sp);
				}
				rsPxk_spkm.close();
			} 
			catch(Exception e) {}
			finally{try {
				if( rsPxk_spkm != null)
					rsPxk_spkm.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		return pxk_spkmlist;
	}
	
	private List<ISanpham> createPxk_TienkmList(String pxkId, dbutils db) 
	{
		List<ISanpham> pxk_tienkmlist = new ArrayList<ISanpham>();
		String query = "select b.scheme + '-' + b.diengiai as ctkmTen, sum(a.tonggiatri) as tonggiatri from phieuxuatkho_tienkm a inner join ctkhuyenmai b on a.scheme = b.pk_seq ";
		query += " where a.pxk_fk = '" + pxkId + "' group by b.scheme + '-' + b.diengiai";
		
		ResultSet rsPxk_spkm = db.get(query);
		if(rsPxk_spkm != null)
		{	
			try
			{
				while(rsPxk_spkm.next())
				{
					String[] param = new String[8];
					ISanpham sp = null;
					
					param[0] = "";					
					param[1] = "";		
					param[2] = "";
					param[3] = "";		
					param[4] = "";
					param[5] = "";
					
					//luu ten ctkm
					param[6] = "";
					if(rsPxk_spkm.getString("ctkmTen") != null)
						param[6] = rsPxk_spkm.getString("ctkmTen");
					
					param[7] = "";
					if(rsPxk_spkm.getString("tonggiatri") != null)
						param[7] = rsPxk_spkm.getString("tonggiatri");
					
					sp = new Sanpham(param);
					pxk_tienkmlist.add(sp);
				}
				rsPxk_spkm.close();
			} 
			catch(Exception e) {}
			finally{try {
				if(rsPxk_spkm != null)
					rsPxk_spkm.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		return pxk_tienkmlist;
	}

}
