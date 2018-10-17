package geso.traphaco.distributor.servlets.hoadontaichinhKM;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.distributor.beans.hoadontaichinhKM.IHoadontaichinhKM;
import geso.traphaco.distributor.beans.hoadontaichinhKM.imp.HoadontaichinhKM;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.servlets.donhang.InDonHangpdfSvl;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.itextpdf.text.Document;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class HoadontaichinhKMPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public HoadontaichinhKMPdfSvl()
	{
		super();
	}
	
	float CONVERT = 28.346457f; // = 1cm
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		// String userTen = (String) session.getAttribute("userTen");

		Utility util = new Utility();
		
		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String querystring = request.getQueryString();
		String id = util.antiSQLInspection(request.getParameter("pdf"));
		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		
		IHoadontaichinhKM pxkBean = new HoadontaichinhKM(id);
		pxkBean.setUserId(userId);

	
		if (querystring.indexOf("pdf") > 0)
		{
			//pxkBean.initPdf();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=PhieuXuatKhoTT.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			
			
			String sql_SANPHAM=
			"	select 	a.SANPHAMMA, b.TEN AS TENSP , a.SOLUONG , a.DONVI ,isnull(a.DONGIA,0) as dongia ,' ' AS solo, ' ' as ngayhethan,'0' AS chietkhau  , a.vat AS thuevat, a.lamtronkieumoi, a.kho_fk, isnull(c.xuatHD_coVAT,1) xuatHD_coVAT " +
			"	from 	HOADON_CTKM_TRAKM a inner join SANPHAM b on a.SANPHAM_FK=b.PK_SEQ		\n"+ 
			"        	left join CTKHUYENMAI c on a.CTKM = c.SCHEME \n"+
			"	where a.HOADONID = '"+ pxkBean.getId() +"' ";
			
			String sql_SANPHAM_CT=
			"	select 	distinct a.SANPHAMMA, b.TEN AS TENSP , ct.SOLUONG , a.DONVI ,isnull(a.DONGIA,0) as dongia ,ISNULL(ct.SOLO,'') AS solo, isnull(ct.NGAYHETHAN,'') as ngayhethan,'0' AS chietkhau  , a.vat AS thuevat , a.lamtronkieumoi,  a.kho_fk, isnull(c.xuatHD_coVAT,1) xuatHD_coVAT  \n"+
			"	from 	HOADON_CTKM_TRAKM a inner join SANPHAM b on a.SANPHAM_FK=b.PK_SEQ	\n"+
		    "   		inner join HOADON_DDH hddh on a.HOADONID = hddh.HOADON_FK \n"+
		    "   		inner join PHIEUXUATKHO_DONHANG xkdh on xkdh.DONHANG_FK = hddh.DDH_FK \n"+
		    "   		left join PHIEUXUATKHO_SANPHAM_CHITIET ct on xkdh.PXK_FK = ct.PXK_FK and b.PK_SEQ = ct.SANPHAM_FK	\n"+
		    "        	left join CTKHUYENMAI c on a.CTKM = c.SCHEME \n"+
			"	where 	a.HOADONID = '"+ pxkBean.getId() +"' AND ct.PXK_FK IN (SELECT PK_sEQ FROM PHIEUXUATKHO WHERE TRANGTHAI=1) " ;
			
			System.out.println("[HoadontaichinhKMPdfSvl]"+sql_SANPHAM);
			if(nppId.equals("106174")) // QUAY HA DONG
			{
				this.CreatePxk_HADONG(document, outstream, pxkBean,sql_SANPHAM);
			}
			else if(nppId.equals("106171")) // CHI NHÁNH CÔNG TY TẠI NGHỆ AN
			{
				this.CreatePxk_NGHEAN(document, outstream, pxkBean,sql_SANPHAM);
			}
			else if(nppId.equals("106170")) // THANH HOA
			{
				this.CreatePxk_THANHHOA(document, outstream, pxkBean,sql_SANPHAM);
			}
			else if(nppId.equals("106172")) //  NAM ĐỊNH
			{
				this.CreatePxk_NAMDINH(document, outstream, pxkBean,sql_SANPHAM);
			}
			else if(nppId.equals("106182")) //  HẢI DƯƠNG
			{
				this.CreatePxk_HAIDUONG(document, outstream, pxkBean,sql_SANPHAM);
			}
			else if(nppId.equals("106179")) //  HẢI PHÒNG  
			{
				this.CreatePxk_HAIPHONG(document, outstream, pxkBean,sql_SANPHAM);
			}
			else if(nppId.equals("106162")) //  QUẢNG NINH
			{
				this.CreatePxk_QUANGNINH(document, outstream, pxkBean,sql_SANPHAM);
			}
			else if(nppId.equals("106210")) //  CN HỒ CHÍ MINH
			{
				this.CreatePxk_CNHOCHIMINH(document, outstream, pxkBean,sql_SANPHAM_CT); 
			}
			else if(nppId.equals("106231")) // ĐÀ NẴNG
			{
				this.CreatePxk_DANANG(document, outstream, pxkBean,sql_SANPHAM);
			}
			else if(nppId.equals("106249")) // QUẢNG NGÃI
			{
				this.CreatePxk_QUANGNGAI(document, outstream, pxkBean,sql_SANPHAM);
			}
			else if(nppId.equals("106250")) //  KHÁNH HÒA   106250
			{
				this.CreatePxk_KHANHHOA(document, outstream, pxkBean,sql_SANPHAM);//
			}
			else
			{
				this.CreatePxk(document, outstream, pxkBean,sql_SANPHAM);
			}
			
			String msg = this.CapnhatTT(id);
			pxkBean.setMsg(msg);
		} 
	}
	
	private void CreatePxk_KHANHHOA(Document document,ServletOutputStream outstream, IHoadontaichinhKM pxkBean, String sql_SANPHAM) 
	{

		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			double Vat= 0;
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			
			String sql ="";
	
		   if(kbh.equals("100052")) // ETC
		   {
			  sql = " select PK_SEQ, TEN ,'75 phố Yên Ninh, Phường Quán Thánh, Quận Ba Đình, Thành phố Hà Nội, Việt Nam' AS DIACHI," +
			  		"       '04.38454813' AS DIENTHOAI,'04.36811542' AS FAX,'0100108656' AS MASOTHUE, '102010000004158 - NH TMCP Công thương VN, CN Ba Đình' as SOTAIKHOAN "+
			        " from NHACUNGCAP ";				  
		   }else{ //OTC
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   }
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   rsINFO.close();
			   
		   }
			   
		 //LAY THONG TIN KHACHHANG 
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuathd = "";
			String chucuahieu = "";
			String donquatang = "";
			String donhangkhac = "";
			String indongia = "";
			
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   
	   
	   System.out.println("Lấy TT KH "+sql);
	   ResultSet rsLayKH= db.get(sql);
	   if(rsLayKH.next())
	   {
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		  
		   rsLayKH.close();
		   
	   }   
			
		  // LẤY KHO XUẤT
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho, case donquatang when 0 then N'Không TT' else N'Hàng KM' end as HTTT, " +
	   		"              (select NGAYXUATHD from HOADON where pk_seq = '"+ pxkBean.getId() +"' )as ngayxuathd,  "+
	   		" 			   ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"                FROM HOADON" +
			"                WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua, donquatang, isnull(donhangkhac,0) donhangkhac, isnull(ingia, 1) ingia "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");	
		   ngayxuathd = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   donquatang = rsKho.getString("donquatang");
		   donhangkhac = rsKho.getString("donhangkhac");
		   indongia = rsKho.getString("ingia");
		   rsKho.close();
	   }
		  
	   if(donquatang.equals("1")){
		   hinhthucTT = "Không thu tiền";
	   }
	   
	   NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(0.2f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 2.0f*CONVERT); // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open();
		//document.setPageSize(new Rectangle(100.0f, 100.0f));
		//document.setPageSize(PageSize.A4.rotate());
	
		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(1.3f * CONVERT); 
		cell.setPaddingLeft(5.5f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuathd.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                        " + ngayHD[0] , new Font(bf, 9, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(pxk);

		tableheader.addCell(cell);
		document.add(tableheader);
		
		// Thông tin Khach Hang
		PdfPTable table1 = new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
		table1.setWidths(withs1);									
		
		/****   DONG 1 ***********/
		PdfPCell cell8 = new PdfPCell();	
		cell.setPaddingTop(-0.15f * CONVERT);
		cell8.setPaddingLeft(4.0f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8.addElement(pxk);
		cell8.setBorder(0);		
		//cell8.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell.setPaddingTop(-0.15f * CONVERT);
		cell8a.setPaddingLeft(5.4f * CONVERT);
		pxk = new Paragraph(chucuahieu, new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);	
		//cell8a.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8a);
		/**** END DONG 1 ************/

		/****   DONG 2 ***********/
		cell8 = new PdfPCell();	
		cell8.setPaddingTop(-0.1f * CONVERT);
		cell8.setPaddingLeft(2.0f * CONVERT);
		pxk = new Paragraph(" " , new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8.addElement(pxk);
		cell8.setBorder(0);		
		//cell8a.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8);	
		
		
		PdfPCell cell10 = new PdfPCell();
		cell10.setPaddingTop(-0.1f * CONVERT);
		cell10.setPaddingLeft(3.3f * CONVERT);	
		pxk = new Paragraph(Donvi, new Font(bf, 12, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell10.addElement(pxk);
		cell10.setBorder(0);	
		//cell10.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell10);
		/**** END DONG 1 ************/
				
		
		/****   DONG 3 ***********/
		PdfPCell cell14 = new PdfPCell();
		cell8a.setPaddingTop(-0.5f * CONVERT);
		cell14.setPaddingLeft(1.6f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);	
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		

		PdfPCell cell17 = new PdfPCell();	
		cell8a.setPaddingTop(-0.5f * CONVERT);
		cell17.setPaddingLeft(3.4f * CONVERT);
		pxk = new Paragraph(kh_MST, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell17.addElement(pxk);
		cell17.setBorder(0);	
		//cell17.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell17);	
		/**** END DONG 3 ************/
		
		/****   DONG 4 ***********/
		cell14 = new PdfPCell();
		cell14.setPaddingTop(-0.1f * CONVERT);//28.
		cell14.setPaddingLeft(1.6f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		

		cell17 = new PdfPCell();
		cell17.setPaddingTop(-0.1f * CONVERT);
		cell17.setPaddingLeft(3.0f * CONVERT);
		pxk = new Paragraph(kh_Diachi, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell17.addElement(pxk);
		cell17.setBorder(0);	
		//cell17.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell17);	
		/**** END DONG 4 ************/
		
		/****   DONG 5 ***********/
		cell14 = new PdfPCell();
		cell14.setPaddingTop(-0.15f * CONVERT);
		cell14.setPaddingLeft(2.4f * CONVERT);
		pxk = new Paragraph(" "+khoxuat, new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		
		
		PdfPCell cell18 = new PdfPCell();
		cell18.setPaddingTop(-0.15f * CONVERT);
		cell18.setPaddingLeft(4.9f * CONVERT);
		pxk = new Paragraph(hinhthucTT, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell18.addElement(pxk);
		cell18.setBorder(0);	
		//cell18.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell18);    
		/**** END DONG 5 ************/
					
		document.add(table1);
		
		
			
		// Table Content
		PdfPTable root = new PdfPTable(2);
		root.setKeepTogether(false);
		root.setSplitLate(false);
		root.setWidthPercentage(100);
		root.setHorizontalAlignment(Element.ALIGN_LEFT);
		root.getDefaultCell().setBorder(0);
		float[] cr = { 95.0f, 100.0f };
		root.setWidths(cr);

		String[] th = new String[]{ " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};
		
		PdfPTable sanpham = new PdfPTable(th.length);
		sanpham.setSpacingBefore(39.4f); //47.8
		sanpham.setWidthPercentage(100);
		sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		float[] withsKM = { 10.0f, 55f, 12.0f, 15.0f, 7.0f, 16.0f, 16f, 26.0f, 12.0f, 26f, 28f };
		sanpham.setWidths(withsKM);
			

		PdfPCell cells = new PdfPCell();
			
		double totalTienTruocVAT=0;
		double totalThueGTGT=0;
		double totalSotienTT=0;
		
		double totalTienCK=0;
		double totalTienCK_ChuaVat=0;
		double totalVatCK=0;
		String kho_fk = "";
		String xuathd_coVAT = "";
			
			String query =sql_SANPHAM;
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG*dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				
				kho_fk = rsSP.getString("kho_fk");
				xuathd_coVAT = rsSP.getString("xuathd_coVAT");
						
				if(rsSP.getInt("lamtronkieumoi")==1)
				{
					thanhtien = Math.round(thanhtien);
					thueGTGT = Math.round(thanhtien * rsSP.getDouble("thuevat") / 100);
					sotientt = thanhtien + thueGTGT;
				}
				
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
			
				String chuoi= "";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi = ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = null;
				if(donquatang.equals("1")){
					arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") + '-' + rsSP.getString("TENSP"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), "0","0","0", "0","0" };
						
				}
				else{
					arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") + '-' + rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(FormatDonGia(dongia, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThanhTien(thanhtien, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThueVat(rsSP.getDouble("thuevat"),indongia ,donhangkhac, kho_fk, xuathd_coVAT)), DinhDangTRAPHACO(FormatThueGTGT(thueGTGT, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSotientt(sotientt, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
				}

				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
				
					if(j <=4 )
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					if(j == 0)
						cells.setPaddingLeft(0.6f*CONVERT);//28.346457f;  // =1cm
					if( j== 1)
						cells.setPaddingLeft(0.2f*CONVERT);
					if( j== 3)
						cells.setPaddingLeft(-0.6f*CONVERT);
					if( j== 4)
						cells.setPaddingLeft(-0.2f*CONVERT);
					if(j == 6)
						cells.setPaddingRight(-0.7f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(-0.6f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(-0.6f*CONVERT);
					if(j == 9)
						cells.setPaddingRight(-0.2f*CONVERT);
					if(j == 10)
						cells.setPaddingRight(-0.2f*CONVERT);
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);
				
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			
		 stt = stt -1 ;
		// DONG TRONG
			int kk=0;
			while(kk < 13-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
		// Tong tien thanh toan	
			String[] arr = null;
			
			if(donquatang.equals("1")){
				arr = new String[] {" "," "," "," "," ", " ", " ", "0", " " , "0", "0" };
			}
			else{
				arr = new String[] {" "," "," "," "," ", " ", " ", DinhDangTRAPHACO(FormatSo(totalTienTruocVAT - totalTienCK_ChuaVat, indongia, donhangkhac, kho_fk, xuathd_coVAT)), " " , DinhDangTRAPHACO(FormatSo(totalThueGTGT - totalVatCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)), DinhDangTRAPHACO(FormatSo(totalSotienTT-totalTienCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
			}
			
			
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if(j <=4 )
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				if(j == 0)
					cells.setPaddingLeft(0.6f*CONVERT);
				if( j== 1)
					cells.setPaddingLeft(0.2f*CONVERT);
				if( j== 3)
					cells.setPaddingLeft(-0.6f*CONVERT);
				if( j== 4)
					cells.setPaddingLeft(-0.2f*CONVERT);
				if(j == 6)
					cells.setPaddingRight(-0.7f*CONVERT);
				if(j == 7)
					cells.setPaddingRight(-0.6f*CONVERT);
				if(j == 8)
					cells.setPaddingRight(-0.6f*CONVERT);
				if(j == 9)
					cells.setPaddingRight(-0.2f*CONVERT);
				if(j == 10)
					cells.setPaddingRight(-0.2f*CONVERT);
				
				cells.setVerticalAlignment(Element.ALIGN_TOP);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(-8.4f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
			String tien = "";
			if(donquatang.equals("1")){
				tien = doctien.docTien(Math.round(0));		   
			}
			else{
				tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));		   
			}
			
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
		    if((donhangkhac.equals("1")&&indongia.equals("0"))||(donhangkhac.equals("0")&&kho_fk.equals("100000")&&xuathd_coVAT.equals("0"))) TienIN = "";
		    
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(11);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(-5.6f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
						
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
		
	}

	private void CreatePxk_HAIPHONG(Document document,ServletOutputStream outstream, IHoadontaichinhKM pxkBean, String sql_SANPHAM) 
	{
		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			double Vat= 0;
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			
			String sql ="";
	
		   if(kbh.equals("100052")) // ETC
		   {
			  sql = " select PK_SEQ, TEN ,'75 phố Yên Ninh, Phường Quán Thánh, Quận Ba Đình, Thành phố Hà Nội, Việt Nam' AS DIACHI," +
			  		"       '04.38454813' AS DIENTHOAI,'04.36811542' AS FAX,'0100108656' AS MASOTHUE, '102010000004158 - NH TMCP Công thương VN, CN Ba Đình' as SOTAIKHOAN "+
			        " from NHACUNGCAP ";				  
		   }else{ //OTC
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   }
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   rsINFO.close();
			   
		   }
			   
		 //LAY THONG TIN KHACHHANG 
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuathd = "";
			String chucuahieu = "";
			String donquatang = "";
			String donhangkhac = "";
			String indongia = "";
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   
	   
	   System.out.println("Lấy TT KH "+sql);
	   ResultSet rsLayKH= db.get(sql);
	   if(rsLayKH.next())
	   {
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		  
		   rsLayKH.close();
		   
	   }   
			
		  // LẤY KHO XUẤT
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho, case donquatang when 0 then N'Không TT' else N'Hàng KM' end as HTTT, " +
	   		"              (select NGAYXUATHD from HOADON where pk_seq = '"+ pxkBean.getId() +"' )as ngayxuathd,  "+
	   		" 			   ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"                FROM HOADON" +
			"                WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua, donquatang, isnull(donhangkhac,0) donhangkhac, isnull(ingia,0) ingia "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");	
		   ngayxuathd = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   donquatang = rsKho.getString("donquatang");
		   indongia = rsKho.getString("ingia");
		   rsKho.close();
	   }
	   
	   if(donquatang.equals("1")){
		   hinhthucTT = "Không thu tiền";
	   }
		   
	   NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(1.0f*CONVERT, 0.1f*CONVERT, 2.4f*CONVERT, 2.0f*CONVERT); // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open() ;
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(1.0f * CONVERT);
		cell.setPaddingLeft(2.7f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuathd.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                             " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		pxk.setSpacingAfter(2);
		cell.addElement(pxk);

		tableheader.addCell(cell);
		document.add(tableheader);
		
		// Thông tin Khach Hang
		PdfPTable table1 =new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
		table1.setWidths(withs1);									
		
		
		// DONG 1-- NGUOI MUA HANG
		PdfPCell cell_nguoimua = new PdfPCell();	
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
		cell_nguoimua.addElement(pxk);
		cell_nguoimua.setBorder(0);						
		table1.addCell(cell_nguoimua);	
		
		PdfPCell cell_nguoimua1 = new PdfPCell();
		cell_nguoimua1.setPaddingLeft(4.9f * CONVERT);
		pxk = new Paragraph(chucuahieu, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		cell_nguoimua1.addElement(pxk);
		cell_nguoimua1.setBorder(0);						
		table1.addCell(cell_nguoimua1);
		
		// DONG 2-- DON VI
		PdfPCell cell8 = new PdfPCell();	
		cell8.setPaddingLeft(2.7f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
		cell8.addElement(pxk);
		cell8.setBorder(0);						
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell8a.setPaddingTop(-0.19f * CONVERT);
		cell8a.setPaddingLeft(4.6f * CONVERT);
		pxk = new Paragraph(Donvi, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);						
		table1.addCell(cell8a);
		

		// DONG 3 ---- DIA CHI
		PdfPCell cell10 = new PdfPCell();
		cell10.setPaddingLeft(3.6f * CONVERT);	
		cell10.setPaddingTop(-0.1f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		//pxk.setSpacingBefore(12.0f);
		cell10.addElement(pxk);
		cell10.setBorder(0);						
		table1.addCell(cell10);
				
		String chuoi_= "";
		String chuoi1= kh_Diachi;
		String chuoi2= "";
		int vitri= 0;
		int dodaichuoi = kh_Diachi.length();
		if(dodaichuoi >= 70)
		{
			chuoi_ = kh_Diachi.substring(0, 70);
			vitri = chuoi_.lastIndexOf(" ");
			chuoi1 = chuoi_.substring(0, vitri);
			chuoi2 = kh_Diachi.substring(vitri + 1,dodaichuoi );
		}
		
		PdfPCell cell14 = new PdfPCell();
		cell14.setPaddingTop(-0.1f * CONVERT);
		cell14.setPaddingLeft(2.5f * CONVERT);
		pxk = new Paragraph(chuoi1, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		//pxk.setSpacingBefore(12.0f);
		cell14.addElement(pxk);
		cell14.setBorder(0);						
		table1.addCell(cell14);		
		
		
		//DONG 4 --- DIA CHI : dai se xuong dong
		PdfPCell cell10a = new PdfPCell();
		cell10a.setPaddingTop(-0.2f * CONVERT);
		cell10a.setPaddingLeft(3.4f * CONVERT);	
		pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell10a.addElement(pxk);
		cell10a.setBorder(0);						
		table1.addCell(cell10a);
					
		
		PdfPCell cell14a = new PdfPCell();
		cell14a.setPaddingTop(-0.2f * CONVERT);
		cell14a.setPaddingLeft(2.5f * CONVERT);
		pxk = new Paragraph(chuoi2, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);		
		cell14a.addElement(pxk);
		cell14a.setBorder(0);						
		table1.addCell(cell14a);	
		
		
		// DONG 5 ----KHO XUAT
		PdfPCell cell17 = new PdfPCell();	
		cell17.setPaddingLeft(3.8f * CONVERT);
		cell17.setPaddingTop(-0.05f * CONVERT);
		pxk = new Paragraph(khoxuat, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell17.addElement(pxk);
		cell17.setBorder(0);						
		table1.addCell(cell17);	
		

		if(kh_MST.trim().length() > 0 && kh_MST.trim().length() <= 10)		
		{
			kh_MST = "                                   "+ kh_MST+ "                                                 ";
		}
		if(kh_MST.trim().length() > 10 && kh_MST.trim().length() <= 15)		
		{
			kh_MST = "                                   "+ kh_MST+ "                                     ";
		}
		if(kh_MST.trim().length() <= 0)
		{
			kh_MST = "                                                                                                      ";
		}
		
		
		
		PdfPCell cell18 = new PdfPCell();
		cell18.setPaddingRight(0.1f * CONVERT);
		cell18.setPaddingTop(-0.05f * CONVERT);
		pxk = new Paragraph( kh_MST +"                                       " +hinhthucTT, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell18.addElement(pxk);
		cell18.setBorder(0);						
		table1.addCell(cell18);       
					
		document.add(table1);
			
		// Table Content
		PdfPTable root = new PdfPTable(2);
		root.setKeepTogether(false);
		root.setSplitLate(false);
		root.setWidthPercentage(100);
		root.setHorizontalAlignment(Element.ALIGN_LEFT);
		root.getDefaultCell().setBorder(0);
		float[] cr = { 95.0f, 100.0f };
		root.setWidths(cr);

		String[] th = new String[]{ " ", " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};
		
		PdfPTable sanpham = new PdfPTable(th.length);
		sanpham.setSpacingBefore(42.2f); 
		sanpham.setWidthPercentage(100);
		sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		float[] withsKM = { 7.0f, 12.0f, 50.0f, 11.0f, 12f, 9.0f, 12.0f, 17f, 22.0f, 7.0f, 19f, 21.0f };
		sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query =sql_SANPHAM;	
			String kho_fk = "";
			String xuathd_coVAT = "";
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG*dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				
				kho_fk = rsSP.getString("kho_fk");
				xuathd_coVAT = rsSP.getString("xuathd_coVAT");
				
				if(rsSP.getInt("lamtronkieumoi")==1)
				{
					thanhtien = Math.round(thanhtien);
					thueGTGT = Math.round(thanhtien * rsSP.getDouble("thuevat") / 100);
					sotientt = thanhtien + thueGTGT;
				}
				
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
			
				String chuoi= "";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi = ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = null;
				
				if(donquatang.equals("1")){
					arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") , rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), "0","0","0", "0","0" };
				}
				else{
					arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") , rsSP.getString("TENSP"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(FormatDonGia(dongia, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThanhTien(thanhtien, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThueVat(rsSP.getDouble("thuevat"), indongia,donhangkhac, kho_fk, xuathd_coVAT)), DinhDangTRAPHACO(FormatThueGTGT(thueGTGT, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSotientt(sotientt, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
				}

				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j == 2 || j == 1 || j == 3 )
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					else if(j == 0 )
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					else if(j ==4 )
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					else if(j == 11)
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					else
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
					if( j == 4 )
						cells.setPaddingLeft(-2.8f);
					
					
					if(j==8)
						cells.setPaddingRight(5.6f);
					
					if ( j == 9)
						cells.setPaddingRight(10.4f);
					
					if ( j == 10 )
						cells.setPaddingRight(18.4f);
					
					if ( j == 11 )
						cells.setPaddingRight(10.6f);
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			
		 stt = stt -1 ;
		// DONG TRONG
			int kk=0;
			while(kk < 15-stt)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
		// Tong tien thanh toan	
			String[] arr = null;
			if(donquatang.equals("1")){
				arr = new String[] {" ", " " , " ", " "," ", " "," "," ", "0"," ","0","0" };
			}
			else{
				arr = new String[] {" ", " " , " ", " "," ", " "," "," ", DinhDangTRAPHACO(FormatSo(totalTienTruocVAT - totalTienCK_ChuaVat, indongia,donhangkhac, kho_fk, xuathd_coVAT))," ",DinhDangTRAPHACO(FormatSo(totalThueGTGT - totalVatCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSo(totalSotienTT-totalTienCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
			}
			
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 2 || j == 1 || j == 3 )
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				else if(j == 0 )
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				else if(j ==4 )
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				else if(j == 11)
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				else
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				
				if( j == 4 )
					cells.setPaddingLeft(-2.8f);
				
				
				if(j==8)
					cells.setPaddingRight(5.6f);
				
				if ( j == 9)
					cells.setPaddingRight(10.4f);
				
				if ( j == 10 )
					cells.setPaddingRight(18.4f);
				
				if ( j == 11 )
					cells.setPaddingRight(10.6f);
				
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				//cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(-11.5f);
				cells.setBorder(0);
				cells.setFixedHeight(0.7f*CONVERT);
				sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
			String tien = "";
			
			if(donquatang.equals("1")){
				tien = doctien.docTien(Math.round(0));
			}
			else{
				tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
			}
			
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
		    if((donhangkhac.equals("1")&&indongia.equals("0"))||(donhangkhac.equals("0")&&kho_fk.equals("100000")&&xuathd_coVAT.equals("0"))) TienIN = "";
		    	
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(12);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(-13.5f);
				cells.setBorder(0);
				cells.setFixedHeight(0.5f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
		
	}


	private void CreatePxk_CNHOCHIMINH(Document document,ServletOutputStream outstream, IHoadontaichinhKM pxkBean, String sql_SANPHAM) 
	{
		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			double Vat= 0;
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			String donquatang = "";
			String donhangkhac = "";
			String indongia = "";
			
			String sql ="";
	
			   sql = 
				" select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN , "+
				"(case when (select isnull(KHO_FK,'0') from KHACHHANG where pk_seq in (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"')) = 100002 then N'Kho sỉ Nhà Bè'" +
			   	" else  isnull(XUATTAIKHO,' ') end ) XUATTAIKHO, ( select donquatang from HOADON where pk_seq = '"+ pxkBean.getId() +"' ) as donquatang " +
		        " from NHAPHANPHOI " +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		  
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   donquatang = rsINFO.getString("donquatang");
			   rsINFO.close();
			   
		   }
			   
		 //LAY THONG TIN KHACHHANG 
		   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "Không thu tiền";
			
			String ngayxuatHD= "";
			String chucuahieu = "";
			
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   
	   
	   System.out.println("Lấy TT KH "+sql);
	   ResultSet rsLayKH= db.get(sql);
	   if(rsLayKH.next())
	   {
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		  
		   rsLayKH.close();
		   
	   } 
			
	// LẤY KHO XUẤT
	   sql = " select top 1 (select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = NPP_FK) as kho," +
	   		"               (select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"               (select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd, " +
	   		" 			   ( SELECT case when isnull(innguoimua, 1) = 1 and nguoimua is null  then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
	   		"							 when isnull(innguoimua, 1) = 1 and nguoimua is not null  then isnull(nguoimua,'') " +
			"                            else '' end " +
			"                FROM HOADON " +
			"                WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua, donquatang, isnull(donhangkhac, 0) donhangkhac, isnull(ingia,1) ingia "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   //hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   donquatang = rsKho.getString("donquatang");
		   donhangkhac = rsKho.getString("donhangkhac");
		   indongia = rsKho.getString("ingia");
		   rsKho.close();
	   }
	   
	   NumberFormat formatter = new DecimalFormat("#,###,###.0000");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(0.5f*CONVERT, 1.1f*CONVERT, 0.5f*CONVERT, 0.7f*CONVERT); // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);		
		document.open() ;
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);			
		
/*			//Chữ Form quay góc 90 độ
		PdfContentByte cb = writer.getDirectContent();
		cb.beginText();
       cb.setFontAndSize(bf, 7);
       cb.setTextMatrix(0, 1, -1, 0, 28.6f*CONVERT, 3.0f * CONVERT);
       cb.showText("In tại Công ty TNHH MTV In Taì Chính - Mã số thuế: 0100111225-001 - ĐT: 08 38113305");
       cb.endText();*/
	
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(3.6f * CONVERT);
		cell.setPaddingLeft(0.6f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuatHD.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                            " + ngayHD[1] +  "                            " + ngayHD[0].substring(2, ngayHD[0].length()) , new Font(bf, 8, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		pxk.setSpacingAfter(2);
		cell.addElement(pxk);

		tableheader.addCell(cell);
		document.add(tableheader);
					
		
		// Thông tin Khach Hang
		PdfPTable table1 =new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
		table1.setWidths(withs1);									
						
		
		// DONG 1-- NGUOI MUA HANG
		PdfPCell cell_nguoimua = new PdfPCell();	
		pxk = new Paragraph(" " +ctyTen  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell_nguoimua.setPaddingTop(0.7f*CONVERT);
		cell_nguoimua.setPaddingLeft(3.5f*CONVERT);
		cell_nguoimua.addElement(pxk);
		cell_nguoimua.setBorder(0);						
		table1.addCell(cell_nguoimua);	
		
		PdfPCell cell_nguoimua1 = new PdfPCell();
		pxk = new Paragraph(" " +chucuahieu , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell_nguoimua1.setPaddingTop(0.7f*CONVERT);
		cell_nguoimua1.setPaddingLeft(6.5f*CONVERT);
		cell_nguoimua1.addElement(pxk);
		cell_nguoimua1.setBorder(0);						
		table1.addCell(cell_nguoimua1);
		
		// DONG 2--TEN DON VI
		PdfPCell cell8 = new PdfPCell();	
		pxk = new Paragraph(" " +cty_Diachi  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		cell8.setPaddingTop(-0.1f*CONVERT);
		cell8.setPaddingLeft(3.1f*CONVERT);
		cell8.addElement(pxk);
		cell8.setBorder(0);		
		pxk.setSpacingAfter(2);
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		pxk = new Paragraph(" " +Donvi, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		cell8a.addElement(pxk);
		cell8a.setPaddingTop(-0.1f*CONVERT);
		cell8a.setPaddingLeft(2.6f*CONVERT);
		cell8a.setBorder(0);
		pxk.setSpacingAfter(2);
		table1.addCell(cell8a);
		

		// DONG 3-- Mã số thuế
		PdfPCell cell_dc = new PdfPCell();	
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setSpacingAfter(2);
		pxk.setAlignment(Element.ALIGN_LEFT);
		cell_dc.addElement(pxk);
		cell_dc.setBorder(0);	
		cell_dc.setPaddingTop(-0.1f*CONVERT);
		table1.addCell(cell_dc);	
		
		PdfPCell cell_dv = new PdfPCell();
		pxk = new Paragraph("" +kh_Diachi , new Font(bf, 10, Font.NORMAL));
		pxk.setSpacingAfter(2);
		pxk.setAlignment(Element.ALIGN_LEFT);
		cell_dv.addElement(pxk);
		cell_dv.setPaddingTop(-0.1f*CONVERT);
		cell_dv.setPaddingLeft(2.0f*CONVERT);
		cell_dv.setBorder(0);						
		table1.addCell(cell_dv);
		
		// DONG 3 ---- Số tài khoản
		PdfPCell cell10 = new PdfPCell();
		pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		//cell10.setPaddingTop(0.2f*CONVERT);
		cell10.addElement(pxk);
		cell10.setBorder(0);						
		table1.addCell(cell10);
				
		
		
		PdfPCell cell14 = new PdfPCell();
		pxk = new Paragraph(" " +kh_MST, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		//cell14.setPaddingTop(0.2f*CONVERT);
		cell14.setPaddingLeft(3.7f*CONVERT);
		cell14.addElement(pxk);
		cell14.setBorder(0);						
		table1.addCell(cell14);		
		
		
		//DONG 4 --- Xuất kho
		PdfPCell cell10a = new PdfPCell();	
		pxk = new Paragraph("" +khoxuat, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell10a.setPaddingTop(-0.05f*CONVERT);
		cell10a.setPaddingLeft(2.7f*CONVERT);
		cell10a.addElement(pxk);
		cell10a.setBorder(0);						
		table1.addCell(cell10a);
					
		
		PdfPCell cell14a = new PdfPCell();
		pxk = new Paragraph(" "+hinhthucTT, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);	
		pxk.setSpacingAfter(2);
		cell14a.setPaddingTop(-0.05f*CONVERT);
		cell14a.setPaddingLeft(6.2f*CONVERT);
		cell14a.addElement(pxk);
		cell14a.setBorder(0);						
		table1.addCell(cell14a);	
		
		
		     
					
		document.add(table1);
			
		// Table Content
		PdfPTable root = new PdfPTable(2);
		root.setKeepTogether(false);
		root.setSplitLate(false);
		root.setWidthPercentage(100);
		root.setHorizontalAlignment(Element.ALIGN_LEFT);
		root.getDefaultCell().setBorder(0);
		float[] cr = { 95.0f, 100.0f };
		root.setWidths(cr);

		//String[] th = new String[]{ "STT", "Mã hàng", "Tên hàng hóa, dịch vụ ", "Số lô", "Hạn dùng","ĐVT" ,"Số lượng", "Đơn giá","Thành tiền", "TS %" ,"Thuế GTGT", "Số tiền thanh toán"};
		String[] th = new String[]{ "", "", "", "", "","" ,"", "","", "" ,"", ""};
		
		PdfPTable sanpham = new PdfPTable(th.length);
		sanpham.setSpacingBefore(1.4f*CONVERT);
		sanpham.setWidthPercentage(100);
		sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		float[] withsKM = { 1.0f*CONVERT, 1.8f*CONVERT, 5.7f*CONVERT, 2.0f*CONVERT, 2.0f*CONVERT, 1.5f*CONVERT,
				2.0f*CONVERT, 2.5f*CONVERT, 3.0f*CONVERT, 1.2f*CONVERT, 3.0f*CONVERT, 3.5f*CONVERT };
		sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			/*String query =
			"	select a.SANPHAMMA, b.TEN AS TENSP , a.SOLUONG , a.DONVI ,isnull(a.DONGIA,0) as dongia ,' ' AS solo, ' ' as ngayhethan,'0' AS chietkhau  , a.vat AS thuevat  " +
			"	from HOADON_CTKM_TRAKM a inner join SANPHAM b on a.SANPHAM_FK=b.PK_SEQ		\n"+ 
			"	where a.HOADON_FK = '"+ pxkBean.getId() +"' ";*/
			
			String query =sql_SANPHAM;
			System.out.println("abc:"+ query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			String kho_fk = "";
			String xuathd_coVAT = "";
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG*dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				kho_fk = rsSP.getString("kho_fk");
				xuathd_coVAT = rsSP.getString("xuathd_coVAT");
				
				if(rsSP.getInt("lamtronkieumoi")==1)
				{
					thanhtien = Math.round(thanhtien);
					thueGTGT = Math.round(thanhtien * rsSP.getDouble("thuevat") / 100);
					sotientt = thanhtien + thueGTGT;
				}
				
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
			
				String chuoi= "";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi = ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr ;
				if(donquatang.equals("1")){
					arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") , rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO( formatter1.format(soLUONG) ), "0", "0","0", "0","0"};
				}
				else{
					arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") , rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO( formatter1.format(soLUONG) ), DinhDangTRAPHACO(FormatDonGia(dongia, indongia, donhangkhac, kho_fk, xuathd_coVAT) ), DinhDangTRAPHACO(FormatThanhTien(thanhtien, indongia, donhangkhac, kho_fk, xuathd_coVAT)), 
							DinhDangTRAPHACO( FormatThueVat(rsSP.getDouble("thuevat"),indongia, donhangkhac, kho_fk, xuathd_coVAT)), DinhDangTRAPHACO( FormatThueGTGT(thueGTGT, indongia, donhangkhac, kho_fk, xuathd_coVAT)),
							DinhDangTRAPHACO( FormatSotientt(sotientt, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
				}

				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j >=0 && j <= 5){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}										
					if(j==0 )
					{
						cells.setPaddingLeft(0.5f*CONVERT);	
					}
					if(j==1)
					{
						cells.setPaddingLeft(0.2f*CONVERT);	
					}
					if(j==3)
					{
						cells.setPaddingLeft(0.2f*CONVERT);	
					}
					if(j==4)
					{
						cells.setPaddingLeft(-0.25f*CONVERT);	
					}
					if( j==5)
					{
						cells.setPaddingLeft(-0.2f*CONVERT);	
					}
					if(j==6)
					{
						cells.setPaddingRight(0.7f*CONVERT);	
					}
					if(j==7)
					{
						cells.setPaddingRight(0.5f*CONVERT);	
					}
					if(j==8 )
					{
						cells.setPaddingRight(0.1f*CONVERT);	
					}
					if(j==9)
					{
						cells.setPaddingRight(0.6f*CONVERT);	
					}
					if(j==10)
					{
						cells.setPaddingRight(0.1f*CONVERT);	
					}
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);					
					cells.setBorder(0);
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			
			stt=stt-1;
				
		// DONG TRONG
			int kk=0;
			while(kk < 13-stt)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
		// Tong tien thanh toan	
			String[] arr ;
			if(donquatang.equals("1")){
				arr = new String[] {"                             ", DinhDangTRAPHACO( "0" ),"",
						DinhDangTRAPHACO( "0" ), DinhDangTRAPHACO( "0" ) };
			}
			else{
				arr = new String[] {"                             ", DinhDangTRAPHACO(FormatSo(totalTienTruocVAT - totalTienCK_ChuaVat, indongia, donhangkhac, kho_fk, xuathd_coVAT) ),"",
						DinhDangTRAPHACO( FormatSo(totalThueGTGT - totalVatCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)), DinhDangTRAPHACO(FormatSo(totalSotienTT-totalTienCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
			}
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(8);
				} else if(j==1)
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
				}else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				if(j==1)
				{
					cells.setPaddingRight(0.25f*CONVERT);
				}
				if(j==3)
				{
					cells.setPaddingRight(0.15f*CONVERT);
				}
				
				
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(-0.3f * CONVERT);
				cells.setFixedHeight(0.6f*CONVERT);
				cells.setBorder(0);
				sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
			 String tien ="";
			if(donquatang.equals("1")){
				tien = doctien.docTien(Math.round(0));
			}
			else{
				tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));		
			}
		   	   
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
		    if((donhangkhac.equals("1")&&indongia.equals("0"))||(donhangkhac.equals("0")&&kho_fk.equals("100000")&&xuathd_coVAT.equals("0"))) TienIN = "";
		    
		    String[] arr1 = new String[] {"            " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(12);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(1.5f * CONVERT);
				cells.setPaddingTop(-0.3f * CONVERT);
				cells.setFixedHeight(0.6f*CONVERT);
				cells.setBorder(0);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}


	private void CreatePxk_QUANGNINH(Document document,ServletOutputStream outstream, IHoadontaichinhKM pxkBean, String sql_SANPHAM)
	{
		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			double Vat= 0;
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			
			String sql ="";
	
		   if(kbh.equals("100052")) // ETC
		   {
			  sql = " select PK_SEQ, TEN ,'75 phố Yên Ninh, Phường Quán Thánh, Quận Ba Đình, Thành phố Hà Nội, Việt Nam' AS DIACHI," +
			  		"       '04.38454813' AS DIENTHOAI,'04.36811542' AS FAX,'0100108656' AS MASOTHUE, '102010000004158 - NH TMCP Công thương VN, CN Ba Đình' as SOTAIKHOAN "+
			        " from NHACUNGCAP ";				  
		   }else{ //OTC
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   }
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   rsINFO.close();
			   
		   }
			   
		 //LAY THONG TIN KHACHHANG 
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuathd = "";
			String chucuahieu ="";
			String donquatang = "";
			String donhangkhac = "";
			String indongia = "";
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE,  isnull(chucuahieu, '') as chucuahieu  "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   
	   
	   System.out.println("Lấy TT KH "+sql);
	   ResultSet rsLayKH= db.get(sql);
	   if(rsLayKH.next())
	   {
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		   chucuahieu = rsLayKH.getString("chucuahieu");
		   rsLayKH.close();
		   
	   }   
			
		  // LẤY KHO XUẤT
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho, 'Không TT' as HTTT, " +
	   		"  (select NGAYXUATHD from HOADON where pk_seq = '"+ pxkBean.getId() +"' )as ngayxuathd, donquatang, isnull(donhangkhac, 0) donhangkhac, isnull(ingia, 0) ingia  "+
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");	
		   ngayxuathd = rsKho.getString("ngayxuathd");
		   donquatang = rsKho.getString("donquatang");
		   donhangkhac = rsKho.getString("donhangkhac");
		   indongia = rsKho.getString("ingia");		   
		   rsKho.close();
	   }
		   
	   if(donquatang.equals("1")){
		   hinhthucTT = "Không thu tiền";
	   }
	   
	   NumberFormat formatter = new DecimalFormat("#,###,###.####");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(1.2f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 1.0f * CONVERT); // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open();
		//document.setPageSize(new Rectangle(100.0f, 100.0f));
		
	
		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(0.75f * CONVERT);
		cell.setPaddingLeft(1.8f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuathd.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                        " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(pxk);

		tableheader.addCell(cell);
		document.add(tableheader);
		
		// Thông tin Khach Hang
		PdfPTable table1 = new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
		table1.setWidths(withs1);									
		
		/****   DONG 1 ***********/
		PdfPCell cell8 = new PdfPCell();	
		cell.setPaddingTop(-0.15f * CONVERT);
		cell8.setPaddingLeft(4.0f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8.addElement(pxk);
		cell8.setBorder(0);		
		//cell8.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell.setPaddingTop(-0.15f * CONVERT);
		cell8a.setPaddingLeft(4.9f * CONVERT);
		pxk = new Paragraph(chucuahieu, new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);	
		//cell8a.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8a);
		/**** END DONG 1 ************/

		/****   DONG 2 ***********/
		cell8 = new PdfPCell();	
		cell8.setPaddingTop(-0.2f * CONVERT);
		cell8.setPaddingLeft(2.0f * CONVERT);
		pxk = new Paragraph(" " , new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8.addElement(pxk);
		cell8.setBorder(0);		
		//cell8a.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8);	
		
		
		PdfPCell cell10 = new PdfPCell();
		cell10.setPaddingTop(-0.2f * CONVERT);
		cell10.setPaddingLeft(2.8f * CONVERT);	
		if(Donvi.length() <= 50)
			pxk = new Paragraph(Donvi, new Font(bf, 12, Font.NORMAL));
		else
			pxk = new Paragraph(Donvi, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell10.addElement(pxk);
		cell10.setBorder(0);	
		//cell10.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell10);
		/**** END DONG 1 ************/
				
		
		/****   DONG 3 ***********/
		PdfPCell cell14 = new PdfPCell();
		cell8a.setPaddingTop(-0.3f * CONVERT);
		cell14.setPaddingLeft(1.6f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);	
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		

		PdfPCell cell17 = new PdfPCell();	
		cell8a.setPaddingTop(-0.3f * CONVERT);
		cell17.setPaddingLeft(2.9f * CONVERT);
		pxk = new Paragraph(kh_MST, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell17.addElement(pxk);
		cell17.setBorder(0);	
		//cell17.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell17);	
		/**** END DONG 3 ************/
		
		/****   DONG 4 ***********/
		cell14 = new PdfPCell();
		cell14.setPaddingTop(-0.2f * CONVERT);
		cell14.setPaddingLeft(1.6f * CONVERT);
		pxk = new Paragraph("  ", new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		

		cell17 = new PdfPCell();
		cell17.setPaddingTop(-0.2f * CONVERT);
		cell17.setPaddingLeft(2.5f * CONVERT);
		pxk = new Paragraph(kh_Diachi, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell17.addElement(pxk);
		cell17.setBorder(0);	
		//cell17.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell17);	
		/**** END DONG 4 ************/
		
		/****   DONG 5 ***********/
		cell14 = new PdfPCell();
		//cell14.setPaddingTop(-0.2f * CONVERT);
		cell14.setPaddingLeft(2.4f * CONVERT);
		pxk = new Paragraph(khoxuat, new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		
		
		PdfPCell cell18 = new PdfPCell();
		//cell18.setPaddingTop(-0.2f * CONVERT);
		cell18.setPaddingLeft(4.9f * CONVERT);
		pxk = new Paragraph(hinhthucTT, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell18.addElement(pxk);
		cell18.setBorder(0);	
		//cell18.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell18);    
		/**** END DONG 5 ************/
					
		document.add(table1);
			
			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(46.0f);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 10.0f, 60f, 7.0f, 13.0f, 7.0f, 16.0f, 16f, 26.0f, 12.0f, 26f, 28f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String kho_fk = "";
			String xuathd_coVAT = "";
			
			String query =sql_SANPHAM;	
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG*dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				
				kho_fk = rsSP.getString("kho_fk");
				xuathd_coVAT = rsSP.getString("xuathd_coVAT");
				
				if(rsSP.getInt("lamtronkieumoi")==1)
				{
					thanhtien = Math.round(thanhtien);
					thueGTGT = Math.round(thanhtien * rsSP.getDouble("thuevat") / 100);
					sotientt = thanhtien + thueGTGT;
				}
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
			
				String chuoi= "";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi = ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = null;
				
				if(donquatang.equals("1")){
					arr = new String[] { Integer.toString(stt),rsSP.getString("SANPHAMMA") + " - " + rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), "0","0","0", "0","0" };
				}
				else{
					arr = new String[] { Integer.toString(stt),rsSP.getString("SANPHAMMA") + " - " + rsSP.getString("TENSP"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(FormatDonGia(dongia, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThanhTien(thanhtien, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThueVat(rsSP.getDouble("thuevat"),indongia, donhangkhac, kho_fk, xuathd_coVAT)), DinhDangTRAPHACO(FormatThueGTGT(thueGTGT, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSotientt(sotientt, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
				}

				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j == 0  ){
						cells.setHorizontalAlignment(Element.ALIGN_CENTER );
					}
					else
					{
						if(j <=5 )
						{
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else
						{
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);					
					
					if(j==0 || j==1 )
					{
						cells.setPaddingLeft(-0.3f *CONVERT);
					}
					if(j==2 )
					{
						cells.setPaddingLeft(-1.4f *CONVERT);
					}
					if(j==3)
					{
						cells.setPaddingLeft(-1.1f *CONVERT);
					}				
					if(j==4  )
					{
						cells.setPaddingLeft(-0.3f *CONVERT);
					}
					if(j==5  )
					{
						cells.setPaddingLeft(0.9f *CONVERT);
					}
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			
		
		// DONG TRONG
			int kk=0;
			while(kk < 15-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
						
		// Tong tien thanh toan
			String[] arr  = null;
			if(donquatang.equals("1")){
				arr = new String[] {"                             ", "0","","0","0" };
			}
			else{
				arr = new String[] {"                             ", DinhDangTRAPHACO(FormatSo(totalTienTruocVAT - totalTienCK_ChuaVat, indongia, donhangkhac, kho_fk, xuathd_coVAT)),"",DinhDangTRAPHACO(FormatSo(totalThueGTGT - totalVatCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSo(totalSotienTT-totalTienCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
			}
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(7);
				} 
				else if(j==1)
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);	
				}
				else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
			String tien = "";
			if(donquatang.equals("1")){
				tien = doctien.docTien(Math.round(0));
			}
			else{
				tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
			}
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
		    if((donhangkhac.equals("1")&&indongia.equals("0"))||(donhangkhac.equals("0")&&kho_fk.equals("100000")&&xuathd_coVAT.equals("0"))) TienIN = "";  	
		    	
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(11);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
		
	
		
	}


	private void CreatePxk_HAIPHONG_OLD(Document document,ServletOutputStream outstream, IHoadontaichinhKM pxkBean) 
	{
		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			double Vat= 0;
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			
			String sql ="";
	
		   if(kbh.equals("100052")) // ETC
		   {
			  sql = " select PK_SEQ, TEN ,'75 phố Yên Ninh, Phường Quán Thánh, Quận Ba Đình, Thành phố Hà Nội, Việt Nam' AS DIACHI," +
			  		"       '04.38454813' AS DIENTHOAI,'04.36811542' AS FAX,'0100108656' AS MASOTHUE, '102010000004158 - NH TMCP Công thương VN, CN Ba Đình' as SOTAIKHOAN "+
			        " from NHACUNGCAP ";				  
		   }else{ //OTC
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   }
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   rsINFO.close();
			   
		   }
			   
		 //LAY THONG TIN KHACHHANG 
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuathd = "";
			String chucuahieu ="";
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE,  isnull(chucuahieu, '') as chucuahieu  "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   
	   
	   System.out.println("Lấy TT KH "+sql);
	   ResultSet rsLayKH= db.get(sql);
	   if(rsLayKH.next())
	   {
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		   chucuahieu = rsLayKH.getString("chucuahieu");
		   rsLayKH.close();
		   
	   }   
			
		  // LẤY KHO XUẤT
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho, 'Không TT' as HTTT, " +
	   		"  (select NGAYXUATHD from HOADON where pk_seq = '"+ pxkBean.getId() +"' )as ngayxuathd  "+
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");	
		   ngayxuathd = rsKho.getString("ngayxuathd");
		   rsKho.close();
	   }
		   
	   NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(1.2f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 1.0f * CONVERT); // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open();
		//document.setPageSize(new Rectangle(100.0f, 100.0f));
		
	
		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(0.75f * CONVERT);
		cell.setPaddingLeft(1.8f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuathd.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                        " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(pxk);

		tableheader.addCell(cell);
		document.add(tableheader);
		
		// Thông tin Khach Hang
		PdfPTable table1 = new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
		table1.setWidths(withs1);									
		
		/****   DONG 1 ***********/
		PdfPCell cell8 = new PdfPCell();	
		cell.setPaddingTop(-0.15f * CONVERT);
		cell8.setPaddingLeft(4.0f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8.addElement(pxk);
		cell8.setBorder(0);		
		//cell8.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell.setPaddingTop(-0.15f * CONVERT);
		cell8a.setPaddingLeft(4.9f * CONVERT);
		pxk = new Paragraph(chucuahieu, new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);	
		//cell8a.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8a);
		/**** END DONG 1 ************/

		/****   DONG 2 ***********/
		cell8 = new PdfPCell();	
		cell8.setPaddingTop(-0.2f * CONVERT);
		cell8.setPaddingLeft(2.0f * CONVERT);
		pxk = new Paragraph(" " , new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8.addElement(pxk);
		cell8.setBorder(0);		
		//cell8a.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8);	
		
		
		PdfPCell cell10 = new PdfPCell();
		cell10.setPaddingTop(-0.2f * CONVERT);
		cell10.setPaddingLeft(2.8f * CONVERT);	
		if(Donvi.length() <= 50)
			pxk = new Paragraph(Donvi, new Font(bf, 12, Font.NORMAL));
		else
			pxk = new Paragraph(Donvi, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell10.addElement(pxk);
		cell10.setBorder(0);	
		//cell10.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell10);
		/**** END DONG 1 ************/
				
		
		/****   DONG 3 ***********/
		PdfPCell cell14 = new PdfPCell();
		cell8a.setPaddingTop(-0.3f * CONVERT);
		cell14.setPaddingLeft(1.6f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);	
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		

		PdfPCell cell17 = new PdfPCell();	
		cell8a.setPaddingTop(-0.3f * CONVERT);
		cell17.setPaddingLeft(2.9f * CONVERT);
		pxk = new Paragraph(kh_MST, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell17.addElement(pxk);
		cell17.setBorder(0);	
		//cell17.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell17);	
		/**** END DONG 3 ************/
		
		/****   DONG 4 ***********/
		cell14 = new PdfPCell();
		cell14.setPaddingTop(-0.2f * CONVERT);
		cell14.setPaddingLeft(1.6f * CONVERT);
		pxk = new Paragraph("  ", new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		

		cell17 = new PdfPCell();
		cell17.setPaddingTop(-0.2f * CONVERT);
		cell17.setPaddingLeft(2.5f * CONVERT);
		pxk = new Paragraph(kh_Diachi, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell17.addElement(pxk);
		cell17.setBorder(0);	
		//cell17.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell17);	
		/**** END DONG 4 ************/
		
		/****   DONG 5 ***********/
		cell14 = new PdfPCell();
		//cell14.setPaddingTop(-0.2f * CONVERT);
		cell14.setPaddingLeft(2.4f * CONVERT);
		pxk = new Paragraph(khoxuat, new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		
		
		PdfPCell cell18 = new PdfPCell();
		//cell18.setPaddingTop(-0.2f * CONVERT);
		cell18.setPaddingLeft(4.9f * CONVERT);
		pxk = new Paragraph(hinhthucTT, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell18.addElement(pxk);
		cell18.setBorder(0);	
		//cell18.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell18);    
		/**** END DONG 5 ************/
					
		document.add(table1);
			
			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(46.0f);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 10.0f, 60f, 7.0f, 13.0f, 7.0f, 16.0f, 16f, 26.0f, 12.0f, 26f, 28f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query =
			"	select a.SANPHAMMA, b.TEN AS TENSP , a.SOLUONG , a.DONVI ,isnull(a.DONGIA,0) as dongia ,' ' AS solo, ' ' as ngayhethan,'0' AS chietkhau  , a.vat AS thuevat  " +
			"	from HOADON_CTKM_TRAKM a inner join SANPHAM b on a.SANPHAM_FK=b.PK_SEQ		\n"+ 
			"	where a.HOADON_FK = '"+ pxkBean.getId() +"' ";
			
			
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG*dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
			
				String chuoi= "";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi = ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = new String[] { Integer.toString(stt),rsSP.getString("SANPHAMMA") + " - " + rsSP.getString("TENSP"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						formatter1.format(soLUONG), formatter.format(dongia),formatter1.format(thanhtien),formatter1.format(rsSP.getDouble("thuevat")), formatter1.format(thueGTGT),formatter1.format(sotientt) };


				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j == 0  ){
						cells.setHorizontalAlignment(Element.ALIGN_CENTER );
					}
					else
					{
						if(j <=5 )
						{
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else
						{
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);					
					
					if(j==0 || j==1 )
					{
						cells.setPaddingLeft(-0.3f *CONVERT);
					}
					if(j==2 )
					{
						cells.setPaddingLeft(-1.4f *CONVERT);
					}
					if(j==3)
					{
						cells.setPaddingLeft(-1.1f *CONVERT);
					}				
					if(j==4  )
					{
						cells.setPaddingLeft(-0.3f *CONVERT);
					}
					if(j==5  )
					{
						cells.setPaddingLeft(0.9f *CONVERT);
					}
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			
		
		// DONG TRONG
			int kk=0;
			while(kk < 15-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
		// Tong tien thanh toan	
		String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(totalSotienTT-totalTienCK) };
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(7);
				} 
				else if(j==1)
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);	
				}
				else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
		    String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));		   
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(11);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
		
	
		
	
		
	}


	private void CreatePxk_HAIDUONG(Document document,ServletOutputStream outstream, IHoadontaichinhKM pxkBean, String sql_SANPHAM) 
	{
		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			double Vat= 0;
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			
			String sql ="";
	
		   if(kbh.equals("100052")) // ETC
		   {
			  sql = " select PK_SEQ, TEN ,'75 phố Yên Ninh, Phường Quán Thánh, Quận Ba Đình, Thành phố Hà Nội, Việt Nam' AS DIACHI," +
			  		"       '04.38454813' AS DIENTHOAI,'04.36811542' AS FAX,'0100108656' AS MASOTHUE, '102010000004158 - NH TMCP Công thương VN, CN Ba Đình' as SOTAIKHOAN "+
			        " from NHACUNGCAP ";				  
		   }else{ //OTC
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   }
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   rsINFO.close();
			   
		   }
			   
		 //LAY THONG TIN KHACHHANG 
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuathd = "";
			String chucuahieu= "";
			String donquatang = "";
			String donhangkhac = "";
			String indongia = "";
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE, isnull(chucuahieu, '') as chucuahieu "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   
	   
	   System.out.println("Lấy TT KH "+sql);
	   ResultSet rsLayKH= db.get(sql);
	   if(rsLayKH.next())
	   {
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		   chucuahieu = rsLayKH.getString("chucuahieu"); 
		  
		   rsLayKH.close();
		   
	   }   
			
		  // LẤY KHO XUẤT
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho, 'Không TT' as HTTT, " +
	   		"  (select NGAYXUATHD from HOADON where pk_seq = '"+ pxkBean.getId() +"' )as ngayxuathd, donquatang, isnull(donhangkhac,0) donhangkhac, isnull(ingia, 1) ingia  "+
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");	
		   ngayxuathd = rsKho.getString("ngayxuathd");
		   donquatang = rsKho.getString("donquatang");
		   donhangkhac = rsKho.getString("donhangkhac");
		   indongia = rsKho.getString("ingia");
		   rsKho.close();
	   }
	   
	   if(donquatang.equals("1")){
		   hinhthucTT = "Không thu tiền";
	   }
		   
	    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(2.0f*CONVERT, 0.1f*CONVERT, 1.7f*CONVERT, 2.0f*CONVERT); // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open() ;
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(0.67f * CONVERT);
		cell.setPaddingLeft(2.7f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuathd.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                             " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		pxk.setSpacingAfter(2);
		cell.addElement(pxk);

		tableheader.addCell(cell);
		document.add(tableheader);
		
		// Thông tin Khach Hang
		PdfPTable table1 =new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
		table1.setWidths(withs1);									
		
		
		// DONG 1-- NGUOI MUA HANG
		PdfPCell cell_nguoimua = new PdfPCell();	
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
		cell_nguoimua.addElement(pxk);
		cell_nguoimua.setBorder(0);						
		table1.addCell(cell_nguoimua);	
		
		PdfPCell cell_nguoimua1 = new PdfPCell();
		//cell_nguoimua1.setPaddingTop(0.5f * CONVERT);
		cell_nguoimua1.setPaddingLeft(4.9f * CONVERT);
		pxk = new Paragraph(chucuahieu, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		cell_nguoimua1.addElement(pxk);
		cell_nguoimua1.setBorder(0);						
		table1.addCell(cell_nguoimua1);
		
		// DONG 2-- DON VI
		PdfPCell cell8 = new PdfPCell();	
		cell8.setPaddingLeft(2.7f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
		cell8.addElement(pxk);
		cell8.setBorder(0);						
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell8a.setPaddingTop(-0.19f * CONVERT);
		cell8a.setPaddingLeft(4.6f * CONVERT);
		pxk = new Paragraph(Donvi, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);						
		table1.addCell(cell8a);
		

		// DONG 3 ---- DIA CHI
		PdfPCell cell10 = new PdfPCell();
		cell10.setPaddingLeft(3.6f * CONVERT);	
		//cell10.setPaddingTop(-0.1f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		//pxk.setSpacingBefore(12.0f);
		cell10.addElement(pxk);
		cell10.setBorder(0);						
		table1.addCell(cell10);
				
		String chuoi_= "";
		String chuoi1= kh_Diachi;
		String chuoi2= "";
		int vitri= 0;
		int dodaichuoi = kh_Diachi.length();
		if(dodaichuoi >= 70)
		{
			chuoi_ = kh_Diachi.substring(0, 70);
			vitri = chuoi_.lastIndexOf(" ");
			chuoi1 = chuoi_.substring(0, vitri);
			chuoi2 = kh_Diachi.substring(vitri + 1,dodaichuoi );
		}
		
		PdfPCell cell14 = new PdfPCell();
		//cell14.setPaddingTop(-0.1f * CONVERT);
		cell14.setPaddingLeft(2.5f * CONVERT);
		pxk = new Paragraph(chuoi1, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		//pxk.setSpacingBefore(12.0f);
		cell14.addElement(pxk);
		cell14.setBorder(0);						
		table1.addCell(cell14);		
		
		
		//DONG 4 --- DIA CHI : dai se xuong dong
		PdfPCell cell10a = new PdfPCell();
		cell10a.setPaddingTop(-0.2f * CONVERT);
		cell10a.setPaddingLeft(3.4f * CONVERT);	
		pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell10a.addElement(pxk);
		cell10a.setBorder(0);						
		table1.addCell(cell10a);
					
		
		PdfPCell cell14a = new PdfPCell();
		cell14a.setPaddingTop(-0.2f * CONVERT);
		cell14a.setPaddingLeft(2.5f * CONVERT);
		pxk = new Paragraph(chuoi2, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);		
		cell14a.addElement(pxk);
		cell14a.setBorder(0);						
		table1.addCell(cell14a);	
		
		
		// DONG 5 ----KHO XUAT
		PdfPCell cell17 = new PdfPCell();	
		cell17.setPaddingLeft(3.8f * CONVERT);
		cell17.setPaddingTop(-0.05f * CONVERT);
		pxk = new Paragraph(khoxuat, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell17.addElement(pxk);
		cell17.setBorder(0);						
		table1.addCell(cell17);	
		
		
		if(kh_MST.trim().length() > 0 && kh_MST.trim().length() <= 10)		
		{
			kh_MST = "                                   "+ kh_MST+ "                                           ";
		}
		if(kh_MST.trim().length() > 10 && kh_MST.trim().length() <= 15)		
		{
			kh_MST = "                                   "+ kh_MST+ "                                     ";
		}
		if(kh_MST.trim().length() <= 0)
		{
			kh_MST = "                                                                                                ";
		}
		
		
		
		PdfPCell cell18 = new PdfPCell();
		cell18.setPaddingRight(0.1f * CONVERT);
		cell18.setPaddingTop(-0.05f * CONVERT);
		pxk = new Paragraph( kh_MST +"                                       " +hinhthucTT, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell18.addElement(pxk);
		cell18.setBorder(0);						
		table1.addCell(cell18);       
					
		document.add(table1);
			
		// Table Content
		PdfPTable root = new PdfPTable(2);
		root.setKeepTogether(false);
		root.setSplitLate(false);
		root.setWidthPercentage(100);
		root.setHorizontalAlignment(Element.ALIGN_LEFT);
		root.getDefaultCell().setBorder(0);
		float[] cr = { 95.0f, 100.0f };
		root.setWidths(cr);

		String[] th = new String[]{ " ", " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};
		
		PdfPTable sanpham = new PdfPTable(th.length);
		sanpham.setSpacingBefore(46.0f); 
		sanpham.setWidthPercentage(100);
		sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		float[] withsKM = { 7.0f, 15.0f, 60f, 17f, 15f, 9.0f, 14.0f, 20f, 24.0f, 8.0f, 23f, 25f };
		sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String kho_fk = "";
			String xuathd_coVAT = "";
			
			String query =sql_SANPHAM;
		/*	"	select a.SANPHAMMA, b.TEN AS TENSP , a.SOLUONG , a.DONVI ,isnull(a.DONGIA,0) as dongia ,' ' AS solo, ' ' as ngayhethan,'0' AS chietkhau  , a.vat AS thuevat  " +
			"	from HOADON_CTKM_TRAKM a inner join SANPHAM b on a.SANPHAM_FK=b.PK_SEQ		\n"+ 
			"	where a.HOADONID = '"+ pxkBean.getId() +"' ";
			*/
			
			System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG*dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				
				kho_fk = rsSP.getString("kho_fk");
				xuathd_coVAT = rsSP.getString("xuathd_coVAT");
				
				if(rsSP.getInt("lamtronkieumoi")==1)
				{
					thanhtien = Math.round(thanhtien);
					thueGTGT = Math.round(thanhtien * rsSP.getDouble("thuevat") / 100);
					sotientt = thanhtien + thueGTGT;
				}
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
			
				String chuoi= "";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi = ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr  = null;
				if(donquatang.equals("1")){
					arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") , rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), "0","0","0", "0","0" };
				}
				else{
					arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") , rsSP.getString("TENSP"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(FormatDonGia(dongia, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThanhTien(thanhtien, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThueVat(rsSP.getDouble("thuevat"), indongia,donhangkhac, kho_fk, xuathd_coVAT)), DinhDangTRAPHACO(FormatThueGTGT(thueGTGT, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSotientt(sotientt, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
				}

				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j == 2 || j==1 || j==0 ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						if(j <=4 )
						{
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);
					
					if(j >=5)
					{
						if(j==5)
						{
							cells.setPaddingLeft(0.20f *CONVERT);
						}else
						{
							if(j==9)
							{
								cells.setPaddingLeft(0.4f *CONVERT);
							}
							else
							{
								cells.setPaddingLeft(0.5f *CONVERT);
							}
							
						}
					}
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			
		
		// DONG TRONG
			int kk=0;
			while(kk < 16-stt)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
		// Tong tien thanh toan
			String[] arr = null;
			
			if(donquatang.equals("1")){
				arr = new String[] {"                             ", "0","","0","0" };
			}
			else{
				arr = new String[] {"                             ", DinhDangTRAPHACO(FormatSo(totalTienTruocVAT - totalTienCK_ChuaVat, indongia, donhangkhac, kho_fk, xuathd_coVAT)),"",DinhDangTRAPHACO(FormatSo(totalThueGTGT - totalVatCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSo(totalSotienTT-totalTienCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
			}
			
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(8);
				} else if(j==1)
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
				}else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(2.8f);
				cells.setBorder(0);
				cells.setFixedHeight(0.7f*CONVERT);
				sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
			String tien = "";
			if(donquatang.equals("1")){
				tien = doctien.docTien(Math.round(0));
			}
			else{
				tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));	
			}
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
		    if((donhangkhac.equals("1")&&indongia.equals("0"))||(donhangkhac.equals("0")&&kho_fk.equals("100000")&&xuathd_coVAT.equals("0"))) TienIN = "";
		    
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(12);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(2.8f);
				cells.setBorder(0);
				cells.setFixedHeight(0.7f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
		
	}


	private void CreatePxk_NAMDINH(Document document,ServletOutputStream outstream, IHoadontaichinhKM pxkBean, String sql_SANPHAM)
	{
		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			double Vat= 0;
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			
			String sql ="";
	
		   if(kbh.equals("100052")) // ETC
		   {
			  sql = " select PK_SEQ, TEN ,'75 phố Yên Ninh, Phường Quán Thánh, Quận Ba Đình, Thành phố Hà Nội, Việt Nam' AS DIACHI," +
			  		"       '04.38454813' AS DIENTHOAI,'04.36811542' AS FAX,'0100108656' AS MASOTHUE, '102010000004158 - NH TMCP Công thương VN, CN Ba Đình' as SOTAIKHOAN "+
			        " from NHACUNGCAP ";				  
		   }else{ //OTC
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   }
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   rsINFO.close();
			   
		   }
			   
		 //LAY THONG TIN KHACHHANG 
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuathd = "";
			String chucuahieu ="";
			String donquatang = "";
			String donhangkhac = "";
			String indongia = "";
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE,  isnull(chucuahieu, '') as chucuahieu  "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   
	   
	   System.out.println("Lấy TT KH "+sql);
	   ResultSet rsLayKH= db.get(sql);
	   if(rsLayKH.next())
	   {
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		   chucuahieu = rsLayKH.getString("chucuahieu");
		   rsLayKH.close();
		   
	   }   
			
		  // LẤY KHO XUẤT
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho, 'Không TT' as HTTT, " +
	   		"  (select NGAYXUATHD from HOADON where pk_seq = '"+ pxkBean.getId() +"' )as ngayxuathd, donquatang, isnull(donhangkhac, 0) donhangkhac, isnull(ingia, 0) ingia  "+
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");	
		   ngayxuathd = rsKho.getString("ngayxuathd");
		   donquatang = rsKho.getString("donquatang");
		   donhangkhac = rsKho.getString("donhangkhac");
		   indongia = rsKho.getString("ingia");
		   rsKho.close();
	   }
	   
	   if(donquatang.equals("1")){
		   hinhthucTT = "Không thu tiền";
	   }
		   
	    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(1.2f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 1.0f * CONVERT); // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open();
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(0.75f * CONVERT);
		cell.setPaddingLeft(1.8f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuathd.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                        " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(pxk);

		tableheader.addCell(cell);
		document.add(tableheader);
		
		// Thông tin Khach Hang
		PdfPTable table1 = new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
		table1.setWidths(withs1);									
		
		/****   DONG 1 ***********/
		PdfPCell cell8 = new PdfPCell();	
		cell.setPaddingTop(-0.15f * CONVERT);
		cell8.setPaddingLeft(4.0f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8.addElement(pxk);
		cell8.setBorder(0);		
		//cell8.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell.setPaddingTop(-0.15f * CONVERT);
		cell8a.setPaddingLeft(4.9f * CONVERT);
		pxk = new Paragraph(chucuahieu, new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);	
		//cell8a.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8a);
		/**** END DONG 1 ************/

		/****   DONG 2 ***********/
		cell8 = new PdfPCell();	
		cell8.setPaddingTop(-0.2f * CONVERT);
		cell8.setPaddingLeft(2.0f * CONVERT);
		pxk = new Paragraph(" " , new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell8.addElement(pxk);
		cell8.setBorder(0);		
		//cell8a.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell8);	
		
		
		PdfPCell cell10 = new PdfPCell();
		cell10.setPaddingTop(-0.2f * CONVERT);
		cell10.setPaddingLeft(2.8f * CONVERT);	
		if(Donvi.length() <= 50)
			pxk = new Paragraph(Donvi, new Font(bf, 12, Font.NORMAL));
		else
			pxk = new Paragraph(Donvi, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell10.addElement(pxk);
		cell10.setBorder(0);	
		//cell10.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell10);
		/**** END DONG 1 ************/
				
		
		/****   DONG 3 ***********/
		PdfPCell cell14 = new PdfPCell();
		cell8a.setPaddingTop(-0.3f * CONVERT);
		cell14.setPaddingLeft(1.6f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);	
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		

		PdfPCell cell17 = new PdfPCell();	
		cell8a.setPaddingTop(-0.3f * CONVERT);
		cell17.setPaddingLeft(2.9f * CONVERT);
		pxk = new Paragraph(kh_MST, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell17.addElement(pxk);
		cell17.setBorder(0);	
		//cell17.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell17);	
		/**** END DONG 3 ************/
		
		/****   DONG 4 ***********/
		cell14 = new PdfPCell();
		cell14.setPaddingTop(-0.2f * CONVERT);
		cell14.setPaddingLeft(1.6f * CONVERT);
		pxk = new Paragraph("  ", new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		

		cell17 = new PdfPCell();
		cell17.setPaddingTop(-0.2f * CONVERT);
		cell17.setPaddingLeft(2.5f * CONVERT);
		pxk = new Paragraph(kh_Diachi, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell17.addElement(pxk);
		cell17.setBorder(0);	
		//cell17.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell17);	
		/**** END DONG 4 ************/
		
		/****   DONG 5 ***********/
		cell14 = new PdfPCell();
		//cell14.setPaddingTop(-0.2f * CONVERT);
		cell14.setPaddingLeft(2.4f * CONVERT);
		pxk = new Paragraph(khoxuat, new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell14.addElement(pxk);
		cell14.setBorder(0);
		//cell14.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell14);		
		
		PdfPCell cell18 = new PdfPCell();
		//cell18.setPaddingTop(-0.2f * CONVERT);
		cell18.setPaddingLeft(4.9f * CONVERT);
		pxk = new Paragraph(hinhthucTT, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell18.addElement(pxk);
		cell18.setBorder(0);	
		//cell18.setFixedHeight(0.6f * CONVERT);
		table1.addCell(cell18);    
		/**** END DONG 5 ************/
					
		document.add(table1);
			
			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(46.0f);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 10.0f, 60f, 7.0f, 10.0f, 7.0f, 16.0f, 16f, 26.0f, 12.0f, 26f, 28f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String kho_fk = "";
			String xuathd_coVAT = "";
			
			String query =sql_SANPHAM;
		/*	"	select a.SANPHAMMA, b.TEN AS TENSP , a.SOLUONG , a.DONVI ,isnull(a.DONGIA,0) as dongia ,' ' AS solo, ' ' as ngayhethan,'0' AS chietkhau  , a.vat AS thuevat  " +
			"	from HOADON_CTKM_TRAKM a inner join SANPHAM b on a.SANPHAM_FK=b.PK_SEQ		\n"+ 
			"	where a.HOADONID = '"+ pxkBean.getId() +"' ";*/
			
			
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG*dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				
				kho_fk = rsSP.getString("kho_fk");
				xuathd_coVAT = rsSP.getString("xuathd_coVAT");
				
				if(rsSP.getInt("lamtronkieumoi")==1)
				{
					thanhtien = Math.round(thanhtien);
					thueGTGT = Math.round(thanhtien * rsSP.getDouble("thuevat") / 100);
					sotientt = thanhtien + thueGTGT;
				}
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
			
				String chuoi= "";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi = ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr  = null;
				if(donquatang.equals("1")){
					arr = new String[] { Integer.toString(stt),rsSP.getString("SANPHAMMA") + " - " + rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), "0","0","0", "0","0" };
				}
				else{
					arr = new String[] { Integer.toString(stt),rsSP.getString("SANPHAMMA") + " - " + rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(FormatDonGia(dongia, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThanhTien(thanhtien, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThueVat(rsSP.getDouble("thuevat"),indongia, donhangkhac, kho_fk, xuathd_coVAT)), DinhDangTRAPHACO(FormatThueGTGT(thueGTGT, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSotientt(sotientt, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
				}

				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j == 0 || j == 3 ){
						cells.setHorizontalAlignment(Element.ALIGN_CENTER );
					}
					else
					{
						if(j <=4 )
						{
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else
						{
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);

					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			
		
		// DONG TRONG
			int kk=0;
			while(kk < 15-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
		// Tong tien thanh toan	
			String[] arr  = null;
			if(donquatang.equals("1")){
				arr = new String[] {"                             ", "0","","0","0" };
			}
			else{
				arr = new String[] {"                             ", DinhDangTRAPHACO(FormatSo(totalTienTruocVAT - totalTienCK_ChuaVat, indongia, donhangkhac, kho_fk, xuathd_coVAT)),"",DinhDangTRAPHACO(FormatSo(totalThueGTGT - totalVatCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSo(totalSotienTT-totalTienCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
			}
		
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(7);
				} 
				else if(j==1)
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);	
				}
				else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
			String tien = "";
			if(donquatang.equals("1")){
				tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
			}
			else{
				 tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
			}
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
		    if((donhangkhac.equals("1")&&indongia.equals("0"))||(donhangkhac.equals("0")&&kho_fk.equals("100000")&&xuathd_coVAT.equals("0"))) TienIN = "";
		    	
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(11);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
						
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
		
	
		
	}


	private void CreatePxk_THANHHOA(Document document,ServletOutputStream outstream, IHoadontaichinhKM pxkBean, String sql_SANPHAM) 
	{

		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			double Vat= 0;
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			
			String sql ="";
	
		   if(kbh.equals("100052")) // ETC
		   {
			  sql = " select PK_SEQ, TEN ,'75 phố Yên Ninh, Phường Quán Thánh, Quận Ba Đình, Thành phố Hà Nội, Việt Nam' AS DIACHI," +
			  		"       '04.38454813' AS DIENTHOAI,'04.36811542' AS FAX,'0100108656' AS MASOTHUE, '102010000004158 - NH TMCP Công thương VN, CN Ba Đình' as SOTAIKHOAN "+
			        " from NHACUNGCAP ";				  
		   }else{ //OTC
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   }
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   rsINFO.close();
			   
		   }
			   
		 //LAY THONG TIN KHACHHANG 
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuathd = "";
			String chucuahieu ="";
			String donquatang = "";
			String donhangkhac = "";
			String indongia = "";
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE,  isnull(chucuahieu, '') as chucuahieu  "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   
	   
	   System.out.println("Lấy TT KH "+sql);
	   ResultSet rsLayKH= db.get(sql);
	   if(rsLayKH.next())
	   {
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		   chucuahieu = rsLayKH.getString("chucuahieu");
		   rsLayKH.close();
		   
	   }   
			
		  // LẤY KHO XUẤT
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho, 'Không TT' as HTTT, " +
	   		"  (select NGAYXUATHD from HOADON where pk_seq = '"+ pxkBean.getId() +"' )as ngayxuathd, donquatang, isnull(donhangkhac,0) donhangkhac, isnull(ingia, 0)  ingia "+
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");	
		   ngayxuathd = rsKho.getString("ngayxuathd");
		   donquatang = rsKho.getString("donquatang");
		   donhangkhac = rsKho.getString("donhangkhac");
		   indongia = rsKho.getString("ingia");
		   rsKho.close();
	   }
	   if(donquatang.equals("1")){
		   hinhthucTT = "Không thu tiền";
	   }
		   
	   NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(1.5f*CONVERT, 0.6f*CONVERT, 1.0f*CONVERT, 2.0f*CONVERT);  // R :1.2f
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open();
		//document.setPageSize(new Rectangle(100.0f, 100.0f));
		//document.setPageSize(PageSize.A4.rotate());
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(1.0f * CONVERT);
		cell.setPaddingLeft(2.5f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuathd.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                             " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		pxk.setSpacingAfter(2);
		cell.addElement(pxk);

		tableheader.addCell(cell);
		document.add(tableheader);
		
		// Thông tin Khach Hang
		PdfPTable table1 =new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
		table1.setWidths(withs1);									
		
		//DONG 1
		PdfPCell cell88 = new PdfPCell();
		cell88.setPaddingTop(-0.15f * CONVERT);
		cell88.setPaddingLeft(2.7f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
		cell88.addElement(pxk);
		cell88.setBorder(0);						
		table1.addCell(cell88);	
		
		PdfPCell cell88a = new PdfPCell();
		cell88a.setPaddingTop(-0.15f * CONVERT);
		cell88a.setPaddingLeft(4.7f * CONVERT);
		pxk = new Paragraph(chucuahieu, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell88a.addElement(pxk);
		cell88a.setBorder(0);						
		table1.addCell(cell88a);
		//
		
		//DONG 2
		PdfPCell cell8 = new PdfPCell();	
		cell8.setPaddingLeft(2.7f * CONVERT);
		cell8.setPaddingTop(-0.2f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
		cell8.addElement(pxk);
		cell8.setBorder(0);						
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell8a.setPaddingLeft(3.73f * CONVERT);
		cell8a.setPaddingTop(-0.2f * CONVERT);
		pxk = new Paragraph(Donvi, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);						
		table1.addCell(cell8a);
		

		//DONG 3
		PdfPCell cell10 = new PdfPCell();
		cell10.setPaddingLeft(2.5f * CONVERT);	
		cell10.setPaddingTop(10.0f);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(3);
		//pxk.setSpacingBefore(12.0f);
		cell10.addElement(pxk);
		cell10.setBorder(0);						
		table1.addCell(cell10);
					
		
		PdfPCell cell14 = new PdfPCell();
		cell14.setPaddingLeft(2.6f * CONVERT);
		//cell14.setPaddingTop(10.0f);
		pxk = new Paragraph(kh_Diachi, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(3);
		//pxk.setSpacingBefore(12.0f);
		cell14.addElement(pxk);
		cell14.setBorder(0);						
		table1.addCell(cell14);						
		
		
		//DOng 4
		PdfPCell cell17 = new PdfPCell();	
		cell17.setPaddingLeft(2.9f * CONVERT);
		cell17.setPaddingTop(6.6f);
		pxk = new Paragraph(khoxuat, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell17.addElement(pxk);
		cell17.setBorder(0);						
		table1.addCell(cell17);	
		
		/*if(kh_MST.trim().length() <= 0)
		{
			kh_MST = " MST KH ";
		}*/
		if(kh_MST.trim().length() <= 0)
		{
			kh_MST = "                            ";
		}

		while(kh_MST.length() < 14 )
			kh_MST = kh_MST + "         ";
		
		PdfPCell cell18 = new PdfPCell();		
		cell18.setPaddingLeft(5.3f * CONVERT); //4.8
		cell17.setPaddingTop(6.6f);
		pxk = new Paragraph( kh_MST + "                                                 " + hinhthucTT, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell18.addElement(pxk);
		cell18.setBorder(0);						
		table1.addCell(cell18);       
					
		document.add(table1);
			
		// Table Content
		PdfPTable root = new PdfPTable(2);
		root.setKeepTogether(false);
		root.setSplitLate(false);
		root.setWidthPercentage(100);
		root.setHorizontalAlignment(Element.ALIGN_LEFT);
		root.getDefaultCell().setBorder(0);
		float[] cr = { 95.0f, 100.0f };
		root.setWidths(cr);

		String[] th = new String[]{ " ", " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};
		
		PdfPTable sanpham = new PdfPTable(th.length);
		sanpham.setSpacingBefore(41.4f); //50,13,5
		sanpham.setWidthPercentage(100);
		sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
		float[] withsKM = { 7.0f, 15.0f, 58f, 15f, 15f, 9.0f, 14.0f, 24f, 26.0f, 6.0f, 25f, 23f }; //ô 10:22f
		sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query =sql_SANPHAM;			
			ResultSet rsSP = db.get(query);
			
			String kho_fk = "";
			String xuathd_coVAT = "";
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG*dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				
				kho_fk = rsSP.getString("kho_fk");
				xuathd_coVAT = rsSP.getString("xuathd_coVAT");
				
				if(rsSP.getInt("lamtronkieumoi")==1)
				{
					thanhtien = Math.round(thanhtien);
					thueGTGT = Math.round(thanhtien * rsSP.getDouble("thuevat") / 100);
					sotientt = thanhtien + thueGTGT;
				}
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
			
				String chuoi= "";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi = ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = null;
				if(donquatang.equals("1")){
					arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") , rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), "0","0","0", "0","0" };
				}
				else{
					arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") , rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(FormatDonGia(dongia, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThanhTien(thanhtien, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThueVat(rsSP.getDouble("thuevat"),indongia, donhangkhac, kho_fk, xuathd_coVAT)), DinhDangTRAPHACO(FormatThueGTGT(thueGTGT, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSotientt(sotientt, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
				}
				


				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j == 2 || j==1 || j==0 ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						//if(j==9|| j==6 || j==0 || j==4 || j==3 || j==5 )
						if(j <=4 )
						{
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.0f);
					
					if(j==3)
					{
						cells.setPaddingLeft(-9.4f);
					}else
					{
						if(j==10 )
						{
							cells.setPaddingRight(18.0f); //6
						}
						else if (j==11) 
						{
							cells.setPaddingRight(20.2f); //9
						}
						else if ( j >= 5 && j<=9 )
						{
							 cells.setPaddingLeft(-18.4f);
						}
						else cells.setPaddingLeft(-10.0f);
					}
															
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			
		
		// DONG TRONG
			int kk=0;
			while(kk < 15-stt)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
		// Tong tien thanh toan		
			String[] arr = null;
		if(donquatang.equals("1")){
			arr = new String[] {"                             ", "0","","0","0" };
		}
		else{
			arr = new String[] {"                             ", DinhDangTRAPHACO(FormatSo(totalTienTruocVAT - totalTienCK_ChuaVat, indongia, donhangkhac, kho_fk, xuathd_coVAT)),"",DinhDangTRAPHACO(FormatSo(totalThueGTGT - totalVatCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSo(totalSotienTT-totalTienCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
		}
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(8);
				} else if(j==1)
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
				}else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (j== 3)
				{
					cells.setPaddingRight(0.7f * CONVERT);
				}else
				{
					if (j==4 ) 	cells.setPaddingRight(0.7f * CONVERT);
					else cells.setPaddingLeft(0.8f * CONVERT);
				}
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
		    String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));	
		    
		    if(donquatang.equals("1")){
		    	tien = doctien.docTien(Math.round(0));
		    }
		    else{
		    	 tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));	
		    }
		    
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
		    if((donhangkhac.equals("1")&&indongia.equals("0"))||(donhangkhac.equals("0")&&kho_fk.equals("100000")&&xuathd_coVAT.equals("0"))) TienIN = "";
		    
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(12);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
		
	}


	private void CreatePxk_NGHEAN(Document document,ServletOutputStream outstream, IHoadontaichinhKM pxkBean, String sql_SANPHAM) 
	{

		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			double Vat= 0;
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			
			String sql ="";
	
			 sql = " select PK_SEQ, TEN ,DIACHI," +
		  		"       DIENTHOAI, FAX, '0100108656-019' AS MASOTHUE, '102010001014275 - NH TMCP Công thương VN, CN Bến Thủy' as SOTAIKHOAN, isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	
		  
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   rsINFO.close();
			   
		   }
			   
		 //LAY THONG TIN KHACHHANG 
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuathd = "";
			String chucuahieu = "";
			String donquatang = "";
			String donhangkhac = "";
			String indongia = "";
			
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE, isnull(chucuahieu, '') as chucuahieu "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   
	   
	   System.out.println("Lấy TT KH "+sql);
	   ResultSet rsLayKH= db.get(sql);
	   if(rsLayKH.next())
	   {
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		   chucuahieu = rsLayKH.getString("chucuahieu");
		   rsLayKH.close();
		   
	   }   
			
		  // LẤY KHO XUẤT
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho, 'Không TT' as HTTT, " +
	   		"  (select NGAYXUATHD from HOADON where pk_seq = '"+ pxkBean.getId() +"' )as ngayxuathd, donquatang, isnull(DONHANGKHAC,0) DONHANGKHAC, isnull(ingia, 1) ingia  "+
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
		   ResultSet rsKho= db.get(sql);
		   if(rsKho.next())
		   {
			   hinhthucTT = rsKho.getString("HTTT");	
			   ngayxuathd = rsKho.getString("ngayxuathd");
			   donquatang = rsKho.getString("donquatang");
			   donhangkhac = rsKho.getString("DONHANGKHAC");
			   indongia = rsKho.getString("ingia");
			   rsKho.close();
		   }
		   
		   if(donquatang.equals("1")){
			   hinhthucTT = "Không thu tiền";
		   }
		   
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.2f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 2.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			
	
			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(1.1f * CONVERT);
			cell.setPaddingLeft(1.8f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = ngayxuathd.split("-");
			Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                        " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(pxk);
	
			tableheader.addCell(cell);
			document.add(tableheader);
			
			// Thông tin Khach Hang
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
			table1.setWidths(withs1);									
			
			/****   DONG 1 ***********/
			PdfPCell cell8 = new PdfPCell();	
			cell.setPaddingTop(-0.15f * CONVERT);
			cell8.setPaddingLeft(4.0f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell8.addElement(pxk);
			cell8.setBorder(0);		
			//cell8.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell8);	
			
			PdfPCell cell8a = new PdfPCell();
			cell.setPaddingTop(-0.15f * CONVERT);
			cell8a.setPaddingLeft(4.9f * CONVERT);
			pxk = new Paragraph(chucuahieu, new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell8a.addElement(pxk);
			cell8a.setBorder(0);	
			//cell8a.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell8a);
			/**** END DONG 1 ************/
	
			/****   DONG 2 ***********/
			cell8 = new PdfPCell();	
			cell8.setPaddingTop(-0.2f * CONVERT);
			cell8.setPaddingLeft(2.0f * CONVERT);
			pxk = new Paragraph(" " , new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell8.addElement(pxk);
			cell8.setBorder(0);		
			//cell8a.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell8);	
			
			
			PdfPCell cell10 = new PdfPCell();
			cell10.setPaddingTop(-0.2f * CONVERT);
			cell10.setPaddingLeft(2.8f * CONVERT);	
			pxk = new Paragraph(Donvi, new Font(bf, 12, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell10.addElement(pxk);
			cell10.setBorder(0);	
			//cell10.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell10);
			/**** END DONG 1 ************/
					
			
			/****   DONG 3 ***********/
			PdfPCell cell14 = new PdfPCell();
			cell8a.setPaddingTop(-0.3f * CONVERT);
			cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell14.addElement(pxk);
			cell14.setBorder(0);	
			//cell14.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell14);		
	
			PdfPCell cell17 = new PdfPCell();	
			cell8a.setPaddingTop(-0.3f * CONVERT);
			cell17.setPaddingLeft(2.9f * CONVERT);
			pxk = new Paragraph(kh_MST, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell17.addElement(pxk);
			cell17.setBorder(0);	
			//cell17.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell17);	
			/**** END DONG 3 ************/
			
			/****   DONG 4 ***********/
			cell14 = new PdfPCell();
			cell14.setPaddingTop(-0.2f * CONVERT);
			cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell14.addElement(pxk);
			cell14.setBorder(0);
			//cell14.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell14);		
	
			cell17 = new PdfPCell();
			cell17.setPaddingTop(-0.2f * CONVERT);
			cell17.setPaddingLeft(2.5f * CONVERT);
			pxk = new Paragraph(kh_Diachi, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell17.addElement(pxk);
			cell17.setBorder(0);	
			//cell17.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell17);	
			/**** END DONG 4 ************/
			
			/****   DONG 5 ***********/
			cell14 = new PdfPCell();
			//cell14.setPaddingTop(-0.2f * CONVERT);
			cell14.setPaddingLeft(2.4f * CONVERT);
			pxk = new Paragraph(khoxuat, new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell14.addElement(pxk);
			cell14.setBorder(0);
			//cell14.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell14);		
			
			PdfPCell cell18 = new PdfPCell();
			//cell18.setPaddingTop(-0.2f * CONVERT);
			cell18.setPaddingLeft(4.9f * CONVERT);
			pxk = new Paragraph(hinhthucTT, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell18.addElement(pxk);
			cell18.setBorder(0);	
			//cell18.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell18);    
			/**** END DONG 5 ************/
						
			document.add(table1);
			
			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(49.0f);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 10.0f, 60f, 7.0f, 10.0f, 7.0f, 16.0f, 16f, 26.0f, 12.0f, 26f, 28f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String kho_fk = "";
			String xuathd_coVAT = "";
			
			String query =sql_SANPHAM;
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG*dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				
				kho_fk = rsSP.getString("kho_fk");
				xuathd_coVAT = rsSP.getString("xuathd_coVAT");
				
				if(rsSP.getInt("lamtronkieumoi")==1)
				{
					thanhtien = Math.round(thanhtien);
					thueGTGT = Math.round(thanhtien * rsSP.getDouble("thuevat") / 100);
					sotientt = thanhtien + thueGTGT;
				}
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
			
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = null;
				if(donquatang.equals("1")){
					arr = new String[] { Integer.toString(stt), rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)),"0","0","0", "0","0" };
				}
				else{
					arr = new String[] { Integer.toString(stt), rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(FormatDonGia(dongia, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThanhTien(thanhtien, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThueVat(rsSP.getDouble("thuevat"),indongia, donhangkhac, kho_fk, xuathd_coVAT)), DinhDangTRAPHACO(FormatThueGTGT(thueGTGT, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSotientt(sotientt, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
				}
			


				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j == 0 || j == 3 ){
						cells.setHorizontalAlignment(Element.ALIGN_CENTER );
					}
					else
					{
						if(j <=4 )
						{
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else
						{
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);
					
					sanpham.addCell(cells);
				}
							
				stt++;
			}
			stt= stt-1;
		
			// DONG TRONG
			int kk=0;
			while(kk < 14-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
			// Tong tien thanh toan	
			String[] arr = null;
			if(donquatang.equals("1")){
			   arr = new String[] {"                             ", "0","","0","0" };
			}
			else{
				arr = new String[] {"                             ", DinhDangTRAPHACO(FormatSo(totalTienTruocVAT - totalTienCK_ChuaVat, indongia, donhangkhac, kho_fk, xuathd_coVAT)),"",DinhDangTRAPHACO(FormatSo(totalThueGTGT - totalVatCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSo(Math.round(totalSotienTT-totalTienCK), indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
			}
				for (int j = 0; j < arr.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
					if (j == 0)
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						cells.setColspan(7);
					} 
					else if(j==1)
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);	
					}
					else
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingLeft(0.8f * CONVERT);
					cells.setPaddingTop(5.0f);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
					sanpham.addCell(cells);
				}
				
				
				// Tien bang chu
				doctienrachu doctien = new doctienrachu();
				
				String tien = "";
				if(donquatang.equals("1")){
					tien = doctien.docTien(Math.round(0));
				}
				else{
					tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
				}
				
			  //Viết hoa ký tự đầu tiên
			    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			    
			    if((donhangkhac.equals("1")&&indongia.equals("0"))||(donhangkhac.equals("0")&&kho_fk.equals("100000")&&xuathd_coVAT.equals("0"))) TienIN = "";
			    
				String[] arr1 = new String[] {"                                           " + TienIN};
				for (int j = 0; j < arr1.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
					if (j == 0)
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						cells.setColspan(11);
					} 
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingLeft(0.8f * CONVERT);
					cells.setPaddingTop(5.0f);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
					sanpham.addCell(cells);
				}
			
																			
			document.add(sanpham);
			

			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
		
	}


	private void CreatePxk_HADONG(Document document,ServletOutputStream outstream, IHoadontaichinhKM pxkBean, String sql_SANPHAM) 
	{

		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			double Vat= 0;
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			
			String sql ="";
	
		   if(kbh.equals("100052")) // ETC
		   {
			  sql = " select PK_SEQ, TEN ,'75 phố Yên Ninh, Phường Quán Thánh, Quận Ba Đình, Thành phố Hà Nội, Việt Nam' AS DIACHI," +
			  		"       '04.38454813' AS DIENTHOAI,'04.36811542' AS FAX,'0100108656' AS MASOTHUE, '102010000004158 - NH TMCP Công thương VN, CN Ba Đình' as SOTAIKHOAN "+
			        " from NHACUNGCAP ";				  
		   }else{ //OTC
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   }
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   rsINFO.close();
			   
		   }
			   
		 //LAY THONG TIN KHACHHANG 
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuathd= "";
			String donquatang = "";
			String donhangkhac = "";
			String indongia = "";
			
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   
	   
	   System.out.println("Lấy TT KH "+sql);
	   ResultSet rsLayKH= db.get(sql);
	   if(rsLayKH.next())
	   {
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		  
		   rsLayKH.close();
		   
	   }   
			
		  // LẤY KHO XUẤT
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho, 'Không TT' as HTTT," +
	   		"  (select NGAYXUATHD from HOADON where pk_seq = '"+ pxkBean.getId() +"' ) as NGAYXUATHD, donquatang, isnull(DONHANGKHAC, 0) DONHANGKHAC, isnull(ingia,1) ingia  "+
	        " from DONHANG " +
	        " where PK_SEQ = (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");
		   ngayxuathd = rsKho.getString("NGAYXUATHD");
		   donquatang = rsKho.getString("donquatang");
		   donhangkhac = rsKho.getString("donhangkhac");
		   indongia = rsKho.getString("ingia");
		   rsKho.close();
	   }
		   
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(2.0f*CONVERT, 1.5f*CONVERT, 2.0f*CONVERT, 2.0f*CONVERT);
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open();
		//document.setPageSize(new Rectangle(100.0f, 100.0f));
		//document.setPageSize(PageSize.A4.rotate());
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(0.2f * CONVERT);
		cell.setPaddingLeft(3.0f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuathd.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                             " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		pxk.setSpacingAfter(2);
		cell.addElement(pxk);

		tableheader.addCell(cell);
		document.add(tableheader);
		
		// Thông tin Khach Hang
		PdfPTable table1 =new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
		table1.setWidths(withs1);									
		
		PdfPCell cell8 = new PdfPCell();	
		cell8.setPaddingLeft(2.7f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
		cell8.addElement(pxk);
		cell8.setBorder(0);						
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell8a.setPaddingTop(0.2f * CONVERT);
		cell8a.setPaddingLeft(3.73f * CONVERT);
		pxk = new Paragraph(Donvi, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);						
		table1.addCell(cell8a);
		
		/*PdfPCell cell9 = new PdfPCell();	
		cell9.setPaddingLeft(2.7f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell9.addElement(pxk);
		cell9.setBorder(0);						
		table1.addCell(cell9);	*/
		
		PdfPCell cell10 = new PdfPCell();
		cell10.setPaddingLeft(2.5f * CONVERT);	
		cell10.setPaddingTop(10.0f);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		//pxk.setSpacingBefore(12.0f);
		cell10.addElement(pxk);
		cell10.setBorder(0);						
		table1.addCell(cell10);
					
		
		PdfPCell cell14 = new PdfPCell();
		cell14.setPaddingLeft(2.6f * CONVERT);
		cell14.setPaddingTop(10.0f);
		pxk = new Paragraph(kh_Diachi, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		//pxk.setSpacingBefore(12.0f);
		cell14.addElement(pxk);
		cell14.setBorder(0);						
		table1.addCell(cell14);						
		
		
		PdfPCell cell17 = new PdfPCell();	
		cell17.setPaddingLeft(2.9f * CONVERT);
		cell17.setPaddingTop(-2.0f);
		pxk = new Paragraph(khoxuat, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell17.addElement(pxk);
		cell17.setBorder(0);						
		table1.addCell(cell17);	
		
		if(kh_MST.trim().length() <= 0)
		{
			kh_MST = "               ";
		}
		
		if(donquatang.equals("1")){
			hinhthucTT = "Không thu tiền";
		}
		
		PdfPCell cell18 = new PdfPCell();		
		cell18.setPaddingLeft(5.0f * CONVERT);
		cell18.setPaddingTop(2.5f);
		pxk = new Paragraph( kh_MST + "                                                            " +hinhthucTT, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell18.addElement(pxk);
		cell18.setBorder(0);						
		table1.addCell(cell18);       
					
		document.add(table1);
			
			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(44.5f); //50,13,5
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.5f * CONVERT,2.0f * CONVERT, 2.0f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT,3.5f * CONVERT };
			
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.7f * CONVERT, 2.5f * CONVERT, 2.5f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT, 3.5f * CONVERT };
			
			float[] withsKM = { 7.0f, 15.0f, 60f, 17f, 15f, 9.0f, 14.0f, 20f, 24.0f, 8.0f, 23f, 25f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query =sql_SANPHAM;			
			ResultSet rsSP = db.get(query);
			
			String kho_fk = "";
			String xuathd_coVAT = "";
			
			int stt = 1;
			while(rsSP.next())
			{
				
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				double thanhtien = soLUONG*dongia - chietkhau;	
				double thueGTGT = thanhtien*rsSP.getDouble("thuevat")/100;
				double sotientt = thanhtien + thueGTGT;
				
				kho_fk = rsSP.getString("kho_fk");
				xuathd_coVAT = rsSP.getString("xuathd_coVAT");
				
				if(rsSP.getInt("lamtronkieumoi")==1)
				{
					thanhtien = Math.round(thanhtien);
					thueGTGT = Math.round(thanhtien * rsSP.getDouble("thuevat") / 100);
					sotientt = thanhtien + thueGTGT;
				}
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
			
				String chuoi= "";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi = ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = null;
				if(donquatang.equals("1")){
					arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") , rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), "0", "0","0","0","0" };
				}
				else{
					arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") , rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)),DinhDangTRAPHACO(FormatDonGia(dongia, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThanhTien(thanhtien, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThueVat(rsSP.getDouble("thuevat"),indongia,donhangkhac, kho_fk, xuathd_coVAT)), DinhDangTRAPHACO(FormatThanhTien(thueGTGT, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThanhTien(sotientt, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
				}
				


				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j == 2 || j==1 || j==0 ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						if(j <=4 )
						{
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.0f);
					if(j==3)
					{
						cells.setPaddingLeft(-8.4f);
					}else
					{
						cells.setPaddingLeft(-9.0f);
					}
															
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			
		
		// DONG TRONG
			int kk=0;
			while(kk < 14-stt)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
		// Tong tien thanh toan	
			String[] arr = null;
			if(donquatang.equals("1")){
				arr = new String[] {"                             ","0","","0","0" };
			}
			else{
				arr = new String[] {"                             ",DinhDangTRAPHACO(FormatSo(totalTienTruocVAT - totalTienCK_ChuaVat, indongia, donhangkhac, kho_fk, xuathd_coVAT)),"",DinhDangTRAPHACO(FormatSo(totalThueGTGT - totalVatCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSo(totalSotienTT-totalTienCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
			}
		
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(8);
				} else if(j==1)
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
				}else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
		    String tien = "";	
		    
		    if(donquatang.equals("1")){
		    	tien = doctien.docTien(Math.round(0));	
		    }
		    else{
		    	tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));		    	
		    }
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
		    if((donhangkhac.equals("1")&&indongia.equals("0"))||(donhangkhac.equals("0")&&kho_fk.equals("100000")&&xuathd_coVAT.equals("0"))) TienIN = "";
		    	
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(12);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
		
	}


	private String CapnhatTT(String id) {
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			String trangthai="";
			// Kiem tra trạng thái hiện tại của Hóa đơn
			
			query = " select trangthai from HOADON where pk_seq = "+ id +" ";
			ResultSet rs = db.get(query);
			if(rs!= null)
			{
				while(rs.next())
				{
					trangthai = rs.getString("trangthai");
				}rs.close();
			}
			
			if(!trangthai.equals("3") && !trangthai.equals("5") )
			{
				// Cập nhật trạng thái HOADON Đã in
				query = "update HOADON set trangthai = '4' where pk_seq = '" + id + "' and TrangThai=2 ";
				if(db.updateReturnInt(query)!=1)
				{
					msg = "Hóa đơn đã cập nhật trạng thái hoặc phát sinh lỗi" + query;
					db.getConnection().rollback();
					return msg;
				}
				
				// Cập nhật trạng thái DONHANG Đã in
				query = "update DONHANG set DAXUATHOADON = '1' where pk_seq in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + id + "') ";
				if(!db.update(query))
				{
					msg = "Không thể cập nhật HOADON " + query;
					db.getConnection().rollback();
					return msg;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	private void CreatePxk(Document document, ServletOutputStream outstream, IHoadontaichinhKM pxkBean,String sql_SANPHAM) throws IOException
	{
		System.out.println("Da vao day");
		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			double Vat= 0;
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			
			String sql ="";
	
		   if(kbh.equals("100052")) // ETC
		   {
			  sql = " select PK_SEQ, TEN ,'75 phố Yên Ninh, Phường Quán Thánh, Quận Ba Đình, Thành phố Hà Nội, Việt Nam' AS DIACHI," +
			  		"       '04.38454813' AS DIENTHOAI,'04.36811542' AS FAX,'0100108656' AS MASOTHUE, '102010000004158 - NH TMCP Công thương VN, CN Ba Đình' as SOTAIKHOAN "+
			        " from NHACUNGCAP ";				  
		   }else{ //OTC
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   }
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   rsINFO.close();
			   
		   }
			   
		 //LAY THONG TIN KHACHHANG 
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuathd = "";
			String donquatang = "";
			String donhangkhac = "";
			String indongia = "";
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   
	   
	   System.out.println("Lấy TT KH "+sql);
	   ResultSet rsLayKH= db.get(sql);
	   if(rsLayKH.next())
	   {
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		  
		   rsLayKH.close();
		   
	   }   
			
		  // LẤY KHO XUẤT
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho, 'Không TT' as HTTT, " +
	   		"  (select NGAYXUATHD from HOADON where pk_seq = '"+ pxkBean.getId() +"' )as ngayxuathd, donquatang, isnull(donhangkhac,0) donhangkhac, isnull(ingia, 1) ingia "+
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");	
		   ngayxuathd = rsKho.getString("ngayxuathd");
		   donquatang = rsKho.getString("donquatang");
		   donhangkhac = rsKho.getString("donhangkhac");
		   indongia = rsKho.getString("ingia");
		   rsKho.close();
	   }
	   
	   if(donquatang.equals("1")){
		   hinhthucTT = "Không thu tiền";
	   }
		   
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(2.0f*CONVERT, 1.5f*CONVERT, 2.0f*CONVERT, 2.0f*CONVERT);
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open();
		//document.setPageSize(new Rectangle(100.0f, 100.0f));
		//document.setPageSize(PageSize.A4.rotate());
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(0.5f * CONVERT);
		cell.setPaddingLeft(3.0f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuathd.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                             " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		pxk.setSpacingAfter(2);
		cell.addElement(pxk);

		tableheader.addCell(cell);
		document.add(tableheader);
		
		// Thông tin Khach Hang
		PdfPTable table1 =new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
		table1.setWidths(withs1);									
		
		PdfPCell cell8 = new PdfPCell();	
		cell8.setPaddingLeft(2.7f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
		cell8.addElement(pxk);
		cell8.setBorder(0);						
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell8a.setPaddingTop(0.1f * CONVERT);
		cell8a.setPaddingLeft(3.7f * CONVERT);
		pxk = new Paragraph(Donvi, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);						
		table1.addCell(cell8a);
		
		/*PdfPCell cell9 = new PdfPCell();	
		cell9.setPaddingLeft(2.7f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell9.addElement(pxk);
		cell9.setBorder(0);						
		table1.addCell(cell9);	*/
		
		PdfPCell cell10 = new PdfPCell();
		cell10.setPaddingLeft(2.5f * CONVERT);	
		cell10.setPaddingTop(10.0f);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		//pxk.setSpacingBefore(12.0f);
		cell10.addElement(pxk);
		cell10.setBorder(0);						
		table1.addCell(cell10);
					
		
		String chuoi_= "";
		String chuoi1= kh_Diachi;
		String chuoi2= "";
		int vitri= 0;
		int dodaichuoi = kh_Diachi.length();
		if(dodaichuoi >= 70)
		{
			chuoi_ = kh_Diachi.substring(0, 70);
			vitri = chuoi_.lastIndexOf(" ");
			chuoi1 = chuoi_.substring(0, vitri);
			chuoi2 = kh_Diachi.substring(vitri + 1,dodaichuoi );
		}
		
			PdfPCell cell14 = new PdfPCell();
			cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(chuoi1, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell14.addElement(pxk);
			cell14.setBorder(0);						
			table1.addCell(cell14);		
			
			//
			PdfPCell cell10a = new PdfPCell();
			cell10a.setPaddingTop(-0.5f * CONVERT);
			cell10a.setPaddingLeft(2.5f * CONVERT);	
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell10a.addElement(pxk);
			cell10a.setBorder(0);						
			table1.addCell(cell10a);
						
			
			PdfPCell cell14a = new PdfPCell();
			cell14a.setPaddingTop(-0.5f * CONVERT);
			cell14a.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(chuoi2, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell14a.addElement(pxk);
			cell14a.setBorder(0);						
			table1.addCell(cell14a);	
			//					
		
		
		PdfPCell cell17 = new PdfPCell();	
		cell17.setPaddingLeft(2.9f * CONVERT);
		cell17.setPaddingTop(-0.2f * CONVERT);
		pxk = new Paragraph(khoxuat, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell17.addElement(pxk);
		cell17.setBorder(0);						
		table1.addCell(cell17);	
		
		if(kh_MST.trim().length() <= 0)
		{
			kh_MST = "                             ";
		}
		
		PdfPCell cell18 = new PdfPCell();
		cell18.setPaddingLeft(4.9f * CONVERT);
		cell18.setPaddingTop(-0.2f * CONVERT);
		pxk = new Paragraph( kh_MST +"                             " +hinhthucTT, new Font(bf, 10, Font.NORMAL));
		/*pxk = new Paragraph( kh_MST +"                                          " +hinhthucTT, new Font(bf, 10, Font.NORMAL));*/
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell18.addElement(pxk);
		cell18.setBorder(0);						
		table1.addCell(cell18);         
					
		document.add(table1);
			
			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(44.0f);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.5f * CONVERT,2.0f * CONVERT, 2.0f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT,3.5f * CONVERT };
			
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.7f * CONVERT, 2.5f * CONVERT, 2.5f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT, 3.5f * CONVERT };
			
			float[] withsKM = { 7.0f, 15.0f, 60f, 17f, 15f, 12.0f, 15.0f, 17f, 24.0f, 8.0f, 23f, 25f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			//a.DONVI
			String query =sql_SANPHAM;
			
			String kho_fk = "";
			String xuathd_coVAT = "";
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG*dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				
				kho_fk = rsSP.getString("kho_fk");
				xuathd_coVAT = rsSP.getString("xuathd_coVAT");
						
				if(rsSP.getInt("lamtronkieumoi")==1)
				{
					thanhtien = Math.round(thanhtien);
					thueGTGT = Math.round(thanhtien * rsSP.getDouble("thuevat") / 100);
					sotientt = thanhtien + thueGTGT;
				}
				
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
							
				String chuoi= "";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi = ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = null;
				if(donquatang.equals("0")){
				       arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") , rsSP.getString("TENSP"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(FormatDonGia(dongia, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThanhTien(thanhtien, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThueVat(rsSP.getDouble("thuevat"),indongia, donhangkhac, kho_fk, xuathd_coVAT)), DinhDangTRAPHACO(FormatThueGTGT(thueGTGT, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSotientt(sotientt, indongia,donhangkhac, kho_fk, xuathd_coVAT)) };
				}
				if(donquatang.equals("1")){
				       arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") , rsSP.getString("TENSP"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), "0","0","0", "0","0" };
				}

				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j == 2 || j==1 || j==0 ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						if(j <=4 )
						{
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					//cells.setBorderWidth(1);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);
					
					if(j >=5)
					{
						if(j==5)
						{
							cells.setPaddingRight(0.1f *CONVERT);
						}else
						{
							if(j==6)
							{
								cells.setPaddingRight(0.4f *CONVERT);
							}
							else if(j==9)
							{
								cells.setPaddingLeft(0.4f *CONVERT);
							}else
							{
								cells.setPaddingLeft(0.5f *CONVERT);
							}
						}
					}
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			
		
		// DONG TRONG
			int kk=0;
			while(kk < 15-stt)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
		// Tong tien thanh toan	
			String[] arr = null ;
			if(donquatang.equals("0")){
				arr = new String[] {"                             ", DinhDangTRAPHACO(FormatSo(totalTienTruocVAT - totalTienCK_ChuaVat, indongia, donhangkhac, kho_fk, xuathd_coVAT)),"",DinhDangTRAPHACO(FormatSo(totalThueGTGT - totalVatCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSo(totalSotienTT-totalTienCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
			}
			
			if(donquatang.equals("1")){
				arr = new String[] {"                             ", "0","","0","0" };
			}
			
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(8);
				} else if(j==1)
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
				}else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
			 String tien = "";
			if(donquatang.equals("0")){
				tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
			}
			else{
				tien = doctien.docTien(Math.round(0));
			}
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
		    if((donhangkhac.equals("1")&&indongia.equals("0"))||(donhangkhac.equals("0")&&kho_fk.equals("100000")&&xuathd_coVAT.equals("0"))) TienIN = "";
		    	
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(12);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void CreatePxk_DANANG(Document document,ServletOutputStream outstream, IHoadontaichinhKM pxkBean, String sql_SANPHAM) 
	{

		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			double Vat= 0;
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			
			String sql ="";
	
			 sql = " select PK_SEQ, TEN ,DIACHI," +
		  		"       DIENTHOAI, FAX, '0100108656-019' AS MASOTHUE, '102010001014275 - NH TMCP Công thương VN, CN Bến Thủy' as SOTAIKHOAN, isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	
		  
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   rsINFO.close();
			   
		   }
			   
		 //LAY THONG TIN KHACHHANG 
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuathd = "";
			String chucuahieu = "";
			String donquatang = "";
			String donhangkhac = "";
			String indongia = "";
			
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE, isnull(chucuahieu, '') as chucuahieu "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   
	   
	   System.out.println("Lấy TT KH "+sql);
	   ResultSet rsLayKH= db.get(sql);
	   if(rsLayKH.next())
	   {
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		   chucuahieu = rsLayKH.getString("chucuahieu");
		   rsLayKH.close();
		   
	   }   
			
		  // LẤY KHO XUẤT
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho, 'Không TT' as HTTT, " +
	   		"  (select NGAYXUATHD from HOADON where pk_seq = '"+ pxkBean.getId() +"' )as ngayxuathd, donquatang, isnull(donhangkhac,0) donhangkhac, isnull(ingia, 1) ingia "+
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");	
		   ngayxuathd = rsKho.getString("ngayxuathd");
		   donquatang = rsKho.getString("donquatang");
		   donhangkhac = rsKho.getString("donhangkhac");
		   indongia = rsKho.getString("ingia");
		   rsKho.close();
	   }
		   if(donquatang.equals("1")){
			   hinhthucTT = "Không thu tiền";
		   }
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.2f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 2.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(1.0f * CONVERT);
			cell.setPaddingLeft(1.9f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = ngayxuathd.split("-");
			Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                        " + ngayHD[0] , new Font(bf, 9, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);
			
			// Thông tin Khach Hang
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
			table1.setWidths(withs1);									
			
			/****   DONG 1 ***********/
			PdfPCell cell8 = new PdfPCell();	
			cell.setPaddingTop(-0.15f * CONVERT);
			cell8.setPaddingLeft(4.0f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 11, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell8.addElement(pxk);
			cell8.setBorder(0);		
			//cell8.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell8);	
			
			PdfPCell cell8a = new PdfPCell();
			cell.setPaddingTop(-0.15f * CONVERT);
			cell8a.setPaddingLeft(5.4f * CONVERT);
			pxk = new Paragraph(chucuahieu, new Font(bf, 11, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell8a.addElement(pxk);
			cell8a.setBorder(0);	
			//cell8a.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell8a);
			/**** END DONG 1 ************/

			/****   DONG 2 ***********/
			cell8 = new PdfPCell();	
			cell8.setPaddingTop(-0.1f * CONVERT);
			cell8.setPaddingLeft(2.0f * CONVERT);
			pxk = new Paragraph(" " , new Font(bf, 11, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell8.addElement(pxk);
			cell8.setBorder(0);		
			//cell8a.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell8);	
			
			
			PdfPCell cell10 = new PdfPCell();
			cell10.setPaddingTop(-0.1f * CONVERT);
			cell10.setPaddingLeft(3.3f * CONVERT);	
			pxk = new Paragraph(Donvi, new Font(bf, 12, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell10.addElement(pxk);
			cell10.setBorder(0);	
			//cell10.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell10);
			/**** END DONG 1 ************/
					
			
			/****   DONG 3 ***********/
			PdfPCell cell14 = new PdfPCell();
			cell8a.setPaddingTop(-0.5f * CONVERT);
			cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 11, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell14.addElement(pxk);
			cell14.setBorder(0);	
			//cell14.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell14);		

			PdfPCell cell17 = new PdfPCell();	
			cell8a.setPaddingTop(-0.5f * CONVERT);
			cell17.setPaddingLeft(3.4f * CONVERT);
			pxk = new Paragraph(kh_MST, new Font(bf, 11, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell17.addElement(pxk);
			cell17.setBorder(0);	
			//cell17.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell17);	
			/**** END DONG 3 ************/
			
			/****   DONG 4 ***********/
			cell14 = new PdfPCell();
			cell14.setPaddingTop(-0.1f * CONVERT);
			cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 11, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell14.addElement(pxk);
			cell14.setBorder(0);
			//cell14.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell14);		

			cell17 = new PdfPCell();
			cell17.setPaddingTop(-0.1f * CONVERT);
			cell17.setPaddingLeft(3.0f * CONVERT);
			pxk = new Paragraph(kh_Diachi, new Font(bf, 11, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell17.addElement(pxk);
			cell17.setBorder(0);	
			//cell17.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell17);	
			/**** END DONG 4 ************/
			
			/****   DONG 5 ***********/
			cell14 = new PdfPCell();
			cell14.setPaddingTop(-0.15f * CONVERT);
			cell14.setPaddingLeft(2.4f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 11, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell14.addElement(pxk);
			cell14.setBorder(0);
			//cell14.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell14);		
			
			PdfPCell cell18 = new PdfPCell();
			cell18.setPaddingTop(-0.15f * CONVERT);
			cell18.setPaddingLeft(4.9f * CONVERT);
			pxk = new Paragraph(hinhthucTT, new Font(bf, 11, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell18.addElement(pxk);
			cell18.setBorder(0);	
			//cell18.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell18);    
			/**** END DONG 5 ************/
						
			document.add(table1);
			
			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(47.8f); //45
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 10.0f, 55f, 12.0f, 15.0f, 7.0f, 16.0f, 16f, 26.0f, 12.0f, 26f, 28f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String kho_fk = "";
			String xuathd_coVAT = "";
			
			String query =sql_SANPHAM;			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG*dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				
				kho_fk = rsSP.getString("kho_fk");
				xuathd_coVAT = rsSP.getString("xuathd_coVAT");
				
				if(rsSP.getInt("lamtronkieumoi")==1)
				{
					thanhtien = Math.round(thanhtien);
					thueGTGT = Math.round(thanhtien * rsSP.getDouble("thuevat") / 100);
					sotientt = thanhtien + thueGTGT;
				}
				
				
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
			
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = null;
				if(donquatang.equals("1")){
					arr = new String[] { Integer.toString(stt), rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), "0","0","0", "0","0" };
				}
				else{
					arr = new String[] { Integer.toString(stt), rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(FormatDonGia(dongia, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThanhTien(thanhtien, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThueVat(rsSP.getDouble("thuevat"),indongia,donhangkhac, kho_fk, xuathd_coVAT)), DinhDangTRAPHACO(FormatThueGTGT(thueGTGT, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSotientt(sotientt, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
				}
			

				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if(j <=4 )
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					if(j == 0)
						cells.setPaddingLeft(0.6f*CONVERT);
					if( j== 1)
						cells.setPaddingLeft(0.2f*CONVERT);
					if( j== 3)
						cells.setPaddingLeft(-0.6f*CONVERT);
					if( j== 4)
						cells.setPaddingLeft(-0.2f*CONVERT);
					if(j == 6)
						cells.setPaddingRight(-0.7f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(-0.6f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(-0.6f*CONVERT);
					if(j == 9)
						cells.setPaddingRight(-0.2f*CONVERT);
					if(j == 10)
						cells.setPaddingRight(-0.2f*CONVERT);
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);
					
					sanpham.addCell(cells);
				}
							
				stt++;
			}
			stt= stt-1;
		
			// DONG TRONG
			int kk=0;
			while(kk < 13-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
			// Tong tien thanh toan	
			String[] arr = null;
			if(donquatang.equals("1")){
				 arr = new String[] {" "," "," "," "," ", " ", " ", "0","","0",formatter1.format(Math.round(0)) };
			}
			else{
				 arr = new String[] {" "," "," "," "," ", " ", " ", formatter1.format(FormatSo(totalTienTruocVAT - totalTienCK_ChuaVat, indongia, donhangkhac, kho_fk, xuathd_coVAT)),"",formatter1.format(FormatSo(totalThueGTGT - totalVatCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)),formatter1.format(FormatSo(Math.round(totalSotienTT-totalTienCK), indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
			}
			  
				for (int j = 0; j < arr.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
					if(j <=4 )
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					if(j == 0)
						cells.setPaddingLeft(0.6f*CONVERT);
					if( j== 1)
						cells.setPaddingLeft(0.2f*CONVERT);
					if( j== 3)
						cells.setPaddingLeft(-0.6f*CONVERT);
					if( j== 4)
						cells.setPaddingLeft(-0.2f*CONVERT);
					if(j == 6)
						cells.setPaddingRight(-0.7f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(-0.6f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(-0.6f*CONVERT);
					if(j == 9)
						cells.setPaddingRight(-0.2f*CONVERT);
					if(j == 10)
						cells.setPaddingRight(-0.2f*CONVERT);
					
					cells.setVerticalAlignment(Element.ALIGN_TOP);
					cells.setPaddingLeft(0.8f * CONVERT);
					cells.setPaddingTop(-8.4f);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
					sanpham.addCell(cells);
				}
				
				
				// Tien bang chu
				doctienrachu doctien = new doctienrachu();
			    String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));		   
			  //Viết hoa ký tự đầu tiên
			    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			    
			    if((donhangkhac.equals("1")&&indongia.equals("0"))||(donhangkhac.equals("0")&&kho_fk.equals("100000")&&xuathd_coVAT.equals("0"))) TienIN = "";
			    
				String[] arr1 = new String[] {"                                           " + TienIN};
				for (int j = 0; j < arr1.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
					if (j == 0)
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						cells.setColspan(11);
					} 
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingLeft(0.8f * CONVERT);
					cells.setPaddingTop(-5.6f);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
					sanpham.addCell(cells);
				}
			
																			
			document.add(sanpham);
			

			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
		
	}

	private void CreatePxk_QUANGNGAI(Document document,ServletOutputStream outstream, IHoadontaichinhKM pxkBean, String sql_SANPHAM) 
	{

		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			double Vat= 0;
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			
			String sql ="";
	
			 sql = " select PK_SEQ, TEN ,DIACHI," +
		  		"       DIENTHOAI, FAX, '0100108656-019' AS MASOTHUE, '102010001014275 - NH TMCP Công thương VN, CN Bến Thủy' as SOTAIKHOAN, isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	
		  
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   rsINFO.close();
			   
		   }
			   
		 //LAY THONG TIN KHACHHANG 
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuathd = "";
			String chucuahieu = "";
			String donquatang = "";
			String donhangkhac = "";
			String indongia = "";
			
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE, isnull(chucuahieu, '') as chucuahieu "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   
	   
	   System.out.println("Lấy TT KH "+sql);
	   ResultSet rsLayKH= db.get(sql);
	   if(rsLayKH.next())
	   {
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		   chucuahieu = rsLayKH.getString("chucuahieu");
		   rsLayKH.close();
		   
	   }   
			
		  // LẤY KHO XUẤT
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho, 'Không TT' as HTTT, " +
	   		"  (select NGAYXUATHD from HOADON where pk_seq = '"+ pxkBean.getId() +"' )as ngayxuathd, donquatang, isnull(donhangkhac,0) donhangkhac, isnull(ingia, 1) ingia "+
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");	
		   ngayxuathd = rsKho.getString("ngayxuathd");
		   donquatang = rsKho.getString("donquatang");
		   donhangkhac = rsKho.getString("donhangkhac");
		   indongia = rsKho.getString("ingia");
		   rsKho.close();
	   }
		   if(donquatang.equals("1")){
			   hinhthucTT = "Không thu tiền";
		   }
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.2f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 2.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(1.0f * CONVERT);
			cell.setPaddingLeft(1.9f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = ngayxuathd.split("-");
			Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                        " + ngayHD[0] , new Font(bf, 9, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);
			
			// Thông tin Khach Hang
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
			table1.setWidths(withs1);									
			
			/****   DONG 1 ***********/
			PdfPCell cell8 = new PdfPCell();	
			cell.setPaddingTop(-0.15f * CONVERT);
			cell8.setPaddingLeft(4.0f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 11, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell8.addElement(pxk);
			cell8.setBorder(0);		
			//cell8.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell8);	
			
			PdfPCell cell8a = new PdfPCell();
			cell.setPaddingTop(-0.15f * CONVERT);
			cell8a.setPaddingLeft(5.4f * CONVERT);
			pxk = new Paragraph(chucuahieu, new Font(bf, 11, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell8a.addElement(pxk);
			cell8a.setBorder(0);	
			//cell8a.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell8a);
			/**** END DONG 1 ************/

			/****   DONG 2 ***********/
			cell8 = new PdfPCell();	
			cell8.setPaddingTop(-0.1f * CONVERT);
			cell8.setPaddingLeft(2.0f * CONVERT);
			pxk = new Paragraph(" " , new Font(bf, 11, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell8.addElement(pxk);
			cell8.setBorder(0);		
			//cell8a.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell8);	
			
			
			PdfPCell cell10 = new PdfPCell();
			cell10.setPaddingTop(-0.1f * CONVERT);
			cell10.setPaddingLeft(3.3f * CONVERT);	
			pxk = new Paragraph(Donvi, new Font(bf, 12, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell10.addElement(pxk);
			cell10.setBorder(0);	
			//cell10.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell10);
			/**** END DONG 1 ************/
					
			
			/****   DONG 3 ***********/
			PdfPCell cell14 = new PdfPCell();
			cell8a.setPaddingTop(-0.5f * CONVERT);
			cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 11, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell14.addElement(pxk);
			cell14.setBorder(0);	
			//cell14.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell14);		

			PdfPCell cell17 = new PdfPCell();	
			cell8a.setPaddingTop(-0.5f * CONVERT);
			cell17.setPaddingLeft(3.4f * CONVERT);
			pxk = new Paragraph(kh_MST, new Font(bf, 11, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell17.addElement(pxk);
			cell17.setBorder(0);	
			//cell17.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell17);	
			/**** END DONG 3 ************/
			
			/****   DONG 4 ***********/
			cell14 = new PdfPCell();
			cell14.setPaddingTop(-0.1f * CONVERT);
			cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 11, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell14.addElement(pxk);
			cell14.setBorder(0);
			//cell14.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell14);		

			cell17 = new PdfPCell();
			cell17.setPaddingTop(-0.1f * CONVERT);
			cell17.setPaddingLeft(3.0f * CONVERT);
			pxk = new Paragraph(kh_Diachi, new Font(bf, 11, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell17.addElement(pxk);
			cell17.setBorder(0);	
			//cell17.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell17);	
			/**** END DONG 4 ************/
			
			/****   DONG 5 ***********/
			cell14 = new PdfPCell();
			cell14.setPaddingTop(-0.15f * CONVERT);
			cell14.setPaddingLeft(2.4f * CONVERT);
			pxk = new Paragraph(" "+khoxuat, new Font(bf, 11, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell14.addElement(pxk);
			cell14.setBorder(0);
			//cell14.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell14);		
			
			PdfPCell cell18 = new PdfPCell();
			cell18.setPaddingTop(-0.15f * CONVERT);
			cell18.setPaddingLeft(4.9f * CONVERT);
			pxk = new Paragraph(hinhthucTT, new Font(bf, 11, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(0);
			cell18.addElement(pxk);
			cell18.setBorder(0);	
			//cell18.setFixedHeight(0.6f * CONVERT);
			table1.addCell(cell18);    
			/**** END DONG 5 ************/
						
			document.add(table1);
			
			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(47.8f); //45
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 10.0f, 55f, 12.0f, 15.0f, 7.0f, 16.0f, 16f, 26.0f, 12.0f, 26f, 28f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String kho_fk = "";
			String xuathd_coVAT = "";
			
			String query =sql_SANPHAM;
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG*dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				
				kho_fk = rsSP.getString("kho_fk");
				xuathd_coVAT = rsSP.getString("xuathd_coVAT");
				
				if(rsSP.getInt("lamtronkieumoi")==1)
				{
					thanhtien = Math.round(thanhtien);
					thueGTGT = Math.round(thanhtien * rsSP.getDouble("thuevat") / 100);
					sotientt = thanhtien + thueGTGT;
				}
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
			
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				
				String[] arr = null;
				if(donquatang.equals("1")){
					arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") + '-' +rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), "0","0","0", "0","0" };
				}
				else{
					arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") + '-' +rsSP.getString("TENSP"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(FormatDonGia(dongia, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThanhTien(thanhtien, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThueVat(rsSP.getDouble("thuevat"),indongia, donhangkhac, kho_fk, xuathd_coVAT)), DinhDangTRAPHACO(FormatThueGTGT(thueGTGT, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatThanhTien(thanhtien, indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
				}
				


				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if(j <=4 )
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					if(j == 0)
						cells.setPaddingLeft(0.6f*CONVERT);
					if( j== 1)
						cells.setPaddingLeft(0.2f*CONVERT);
					if( j== 3)
						cells.setPaddingLeft(-0.6f*CONVERT);
					if( j== 4)
						cells.setPaddingLeft(-0.2f*CONVERT);
					if(j == 6)
						cells.setPaddingRight(-0.7f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(-0.6f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(-0.6f*CONVERT);
					if(j == 9)
						cells.setPaddingRight(-0.2f*CONVERT);
					if(j == 10)
						cells.setPaddingRight(-0.2f*CONVERT);
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);
					
					sanpham.addCell(cells);
				}
							
				stt++;
			}
			stt= stt-1;
		
			// DONG TRONG
			int kk=0;
			while(kk < 13-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
			// Tong tien thanh toan	
			String[] arr = null;
			if(donquatang.equals("1")){
				arr = new String[] {" "," "," "," "," ", " ", " ", "0","","0","0" };
			}
			else{
				arr = new String[] {" "," "," "," "," ", " ", " ", DinhDangTRAPHACO(FormatSo(totalTienTruocVAT - totalTienCK_ChuaVat, indongia, donhangkhac, kho_fk, xuathd_coVAT)),"",DinhDangTRAPHACO(FormatSo(totalThueGTGT - totalVatCK, indongia, donhangkhac, kho_fk, xuathd_coVAT)),DinhDangTRAPHACO(FormatSo(Math.round(totalSotienTT-totalTienCK), indongia, donhangkhac, kho_fk, xuathd_coVAT)) };
			}
			   
				for (int j = 0; j < arr.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
					if(j <=4 )
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					if(j == 0)
						cells.setPaddingLeft(0.6f*CONVERT);
					if( j== 1)
						cells.setPaddingLeft(0.2f*CONVERT);
					if( j== 3)
						cells.setPaddingLeft(-0.6f*CONVERT);
					if( j== 4)
						cells.setPaddingLeft(-0.2f*CONVERT);
					if(j == 6)
						cells.setPaddingRight(-0.7f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(-0.6f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(-0.6f*CONVERT);
					if(j == 9)
						cells.setPaddingRight(-0.2f*CONVERT);
					if(j == 10)
						cells.setPaddingRight(-0.2f*CONVERT);
					
					cells.setVerticalAlignment(Element.ALIGN_TOP);
					cells.setPaddingLeft(0.8f * CONVERT);
					cells.setPaddingTop(-8.4f);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
					sanpham.addCell(cells);
				}
				
				
				// Tien bang chu
				doctienrachu doctien = new doctienrachu();
				String tien = "";
				if(donquatang.equals("1")){
					tien = doctien.docTien(Math.round(0));
				}
				else{
					tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
				}
			  //Viết hoa ký tự đầu tiên
			    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			    
			    if((donhangkhac.equals("1")&&indongia.equals("0"))||(donhangkhac.equals("0")&&kho_fk.equals("100000")&&xuathd_coVAT.equals("0"))) TienIN = "";
			    	
				String[] arr1 = new String[] {"                                           " + TienIN};
				for (int j = 0; j < arr1.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
					if (j == 0)
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						cells.setColspan(11);
					} 
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingLeft(0.8f * CONVERT);
					cells.setPaddingTop(-5.6f);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
					sanpham.addCell(cells);
				}
			
																			
			document.add(sanpham);
			

			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
		
	}
	
	private String getSTT(int stt)
	{
		if (stt < 10)
			return "000" + Integer.toString(stt);
		if (stt < 100)
			return "00" + Integer.toString(stt);
		if (stt < 1000)
			return "0" + Integer.toString(stt);
		return Integer.toString(stt);
	}
	
	private String DinhDangTRAPHACO(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}
	
	private void CreatePxk_HANOI(Document document, ServletOutputStream outstream, IHoadontaichinhKM pxkBean,String sql_SANPHAM) throws IOException
	{
		System.out.println("Da vao day");
		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			double Vat= 0;
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			
			String sql ="";
	
		   if(kbh.equals("100052")) // ETC
		   {
			  sql = " select PK_SEQ, TEN ,'75 phố Yên Ninh, Phường Quán Thánh, Quận Ba Đình, Thành phố Hà Nội, Việt Nam' AS DIACHI," +
			  		"       '04.38454813' AS DIENTHOAI,'04.36811542' AS FAX,'0100108656' AS MASOTHUE, '102010000004158 - NH TMCP Công thương VN, CN Ba Đình' as SOTAIKHOAN "+
			        " from NHACUNGCAP ";				  
		   }else{ //OTC
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   }
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   khoxuat = rsINFO.getString("XUATTAIKHO");
			   ctyTen = rsINFO.getString("TEN");
			   cty_MST = rsINFO.getString("MASOTHUE");
			   cty_Diachi = rsINFO.getString("DIACHI");
			   cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   cty_Dienthoai = rsINFO.getString("DIENTHOAI");
			   cty_Fax = rsINFO.getString("FAX");
			   rsINFO.close();
			   
		   }
			   
		 //LAY THONG TIN KHACHHANG 
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuathd = "";
			String donquatang = "";
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   
	   
	   System.out.println("Lấy TT KH "+sql);
	   ResultSet rsLayKH= db.get(sql);
	   if(rsLayKH.next())
	   {
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		  
		   rsLayKH.close();
		   
	   }   
			
		  // LẤY KHO XUẤT
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho, 'Không TT' as HTTT, " +
	   		"  (select NGAYXUATHD from HOADON where pk_seq = '"+ pxkBean.getId() +"' )as ngayxuathd, donquatang  "+
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");	
		   ngayxuathd = rsKho.getString("ngayxuathd");
		   donquatang = rsKho.getString("donquatang");
		   rsKho.close();
	   }
	   
	   if(donquatang.equals("1")){
		   hinhthucTT = "HKM không thu tiền";
	   }
		   
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(2.0f*CONVERT, 1.5f*CONVERT, 2.0f*CONVERT, 2.0f*CONVERT);
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open();
		//document.setPageSize(new Rectangle(100.0f, 100.0f));
		//document.setPageSize(PageSize.A4.rotate());
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(0.5f * CONVERT);
		cell.setPaddingLeft(3.0f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuathd.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                             " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		pxk.setSpacingAfter(2);
		cell.addElement(pxk);

		tableheader.addCell(cell);
		document.add(tableheader);
		
		// Thông tin Khach Hang
		PdfPTable table1 =new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
		table1.setWidths(withs1);									
		
		PdfPCell cell8 = new PdfPCell();	
		cell8.setPaddingLeft(2.7f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
		cell8.addElement(pxk);
		cell8.setBorder(0);						
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell8a.setPaddingTop(0.1f * CONVERT);
		cell8a.setPaddingLeft(3.7f * CONVERT);
		pxk = new Paragraph(Donvi, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);						
		table1.addCell(cell8a);
		
		/*PdfPCell cell9 = new PdfPCell();	
		cell9.setPaddingLeft(2.7f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell9.addElement(pxk);
		cell9.setBorder(0);						
		table1.addCell(cell9);	*/
		
		PdfPCell cell10 = new PdfPCell();
		cell10.setPaddingLeft(2.5f * CONVERT);	
		cell10.setPaddingTop(10.0f);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		//pxk.setSpacingBefore(12.0f);
		cell10.addElement(pxk);
		cell10.setBorder(0);						
		table1.addCell(cell10);
					
		
		String chuoi_= "";
		String chuoi1= kh_Diachi;
		String chuoi2= "";
		int vitri= 0;
		int dodaichuoi = kh_Diachi.length();
		if(dodaichuoi >= 70)
		{
			chuoi_ = kh_Diachi.substring(0, 70);
			vitri = chuoi_.lastIndexOf(" ");
			chuoi1 = chuoi_.substring(0, vitri);
			chuoi2 = kh_Diachi.substring(vitri + 1,dodaichuoi );
		}
		
			PdfPCell cell14 = new PdfPCell();
			cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(chuoi1, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell14.addElement(pxk);
			cell14.setBorder(0);						
			table1.addCell(cell14);		
			
			//
			PdfPCell cell10a = new PdfPCell();
			cell10a.setPaddingTop(-0.5f * CONVERT);
			cell10a.setPaddingLeft(2.5f * CONVERT);	
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell10a.addElement(pxk);
			cell10a.setBorder(0);						
			table1.addCell(cell10a);
						
			
			PdfPCell cell14a = new PdfPCell();
			cell14a.setPaddingTop(-0.5f * CONVERT);
			cell14a.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(chuoi2, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell14a.addElement(pxk);
			cell14a.setBorder(0);						
			table1.addCell(cell14a);	
			//					
		
		
		PdfPCell cell17 = new PdfPCell();	
		cell17.setPaddingLeft(2.9f * CONVERT);
		cell17.setPaddingTop(-0.2f * CONVERT);
		pxk = new Paragraph(khoxuat, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell17.addElement(pxk);
		cell17.setBorder(0);						
		table1.addCell(cell17);	
		
		if(kh_MST.trim().length() <= 0)
		{
			kh_MST = "                             ";
		}
		
		PdfPCell cell18 = new PdfPCell();
		cell18.setPaddingLeft(4.9f * CONVERT);
		cell18.setPaddingTop(-0.2f * CONVERT);
		pxk = new Paragraph( kh_MST +"                             " +hinhthucTT, new Font(bf, 10, Font.NORMAL));
		/*pxk = new Paragraph( kh_MST +"                                          " +hinhthucTT, new Font(bf, 10, Font.NORMAL));*/
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell18.addElement(pxk);
		cell18.setBorder(0);						
		table1.addCell(cell18);         
					
		document.add(table1);
			
			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(44.0f);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.5f * CONVERT,2.0f * CONVERT, 2.0f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT,3.5f * CONVERT };
			
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.7f * CONVERT, 2.5f * CONVERT, 2.5f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT, 3.5f * CONVERT };
			
			float[] withsKM = { 7.0f, 15.0f, 60f, 17f, 15f, 12.0f, 12.0f, 20f, 24.0f, 8.0f, 23f, 25f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			//a.DONVI
			String query =sql_SANPHAM;
	/*		"	select a.SANPHAMMA, b.TEN AS TENSP , a.SOLUONG ,a.DONVI ,isnull(a.DONGIA,0) as dongia ,' ' AS solo, ' ' as ngayhethan,'0' AS chietkhau  , a.vat AS thuevat  " +
			"	from HOADON_CTKM_TRAKM a inner join SANPHAM b on a.SANPHAM_FK=b.PK_SEQ		\n"+ 
			"	where a.HOADON_FK = '"+ pxkBean.getId() +"' ";*/
					
			
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG*dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				
				if(rsSP.getInt("lamtronkieumoi")==1)
				{
					thanhtien = Math.round(thanhtien);
					thueGTGT = Math.round(thanhtien * rsSP.getDouble("thuevat") / 100);
					sotientt = thanhtien + thueGTGT;
				}
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
			
				String chuoi= "";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi = ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = null;
				if(donquatang.equals("0")){
				       arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") , rsSP.getString("TENSP"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };
				}
				if(donquatang.equals("1")){
				       arr = new String[] { Integer.toString(stt), rsSP.getString("SANPHAMMA") , rsSP.getString("TENSP"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO("0"),DinhDangTRAPHACO("0"),DinhDangTRAPHACO("0"), DinhDangTRAPHACO("0"),DinhDangTRAPHACO("0") };
				}

				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j == 2 || j==1 || j==0 ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						if(j <=4 )
						{
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);
					
					if(j >=5)
					{
						if(j==5)
						{
							cells.setPaddingLeft(0.25f *CONVERT);
						}else
						{
							if(j==9)
							{
								cells.setPaddingLeft(0.4f *CONVERT);
							}else
							{
								cells.setPaddingLeft(0.5f *CONVERT);
							}
						}
					}
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			
		
		// DONG TRONG
			int kk=0;
			while(kk < 15-stt)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " "," " };
	
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
		// Tong tien thanh toan	
			String[] arr = null ;
			if(donquatang.equals("0")){
				arr = new String[] {"                             ", DinhDangTRAPHACO(formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat)),"",DinhDangTRAPHACO(formatter1.format(totalThueGTGT - totalVatCK)),DinhDangTRAPHACO(formatter1.format(totalSotienTT-totalTienCK)) };
			}
			
			if(donquatang.equals("1")){
				arr = new String[] {"                             ", DinhDangTRAPHACO("0"),"",DinhDangTRAPHACO("0"),DinhDangTRAPHACO("0") };
			}
			
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(8);
				} else if(j==1)
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
				}else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
			 String tien = "";
			if(donquatang.equals("0")){
				tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
			}
			else{
				tien = doctien.docTien(Math.round(0));
			}
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(12);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String FormatDonGia(double dongia, String indongia, String donhangkhac, String kho, String xuathd_coVAT)
	{
		String kq = "";
		
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
		
		if(indongia.equals("0")&&donhangkhac.equals("1")) // LÀ ĐƠN HÀNG KHÁC NHƯNG K IN ĐƠN GIÁ
		{
			if(dongia <= 0)
				return "";
		}
		
		if(donhangkhac.equals("0")&&kho.equals("100000")&&xuathd_coVAT.equals("0")) // ĐƠN HÀNG CÓ ÁP KHUYẾN MÃI: CTKM PHẢI LÀ XUẤT TỪ KHO HÀNG BÁN VÀ CHỌN K XUẤT HÓA ĐƠN CÓ VAT
		{
			return "";
		}
		
		kq = formatter.format(dongia);
		
		return kq;
	}
	
	
	public String FormatSoLuong(double soluong)
	{
		String kq = "";
		
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		if(soluong <= 0)
			return "";
		
		kq = formatter1.format(soluong);
		
		return kq;
	}
	
	public String FormatThanhTien(double thanhtien, String indongia, String donhangkhac, String kho, String xuathd_coVAT)
	{
		String kq = "";
		
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		
		if(indongia.equals("0")&&donhangkhac.equals("1")){
			if(thanhtien <= 0)
				return "";
		}
		
		if(donhangkhac.equals("0")&&kho.equals("100000")&&xuathd_coVAT.equals("0")) // ĐƠN HÀNG CÓ ÁP KHUYẾN MÃI: CTKM PHẢI LÀ XUẤT TỪ KHO HÀNG BÁN VÀ CHỌN K XUẤT HÓA ĐƠN CÓ VAT
		{
			return "";
		}
		
		kq = formatter1.format(thanhtien);
		
		return kq;
	}
	
	public String FormatThueVat(double thuevat, String indongia, String donhangkhac, String kho, String xuathd_coVAT)
	{
		String kq = "";		
		
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		
		if(indongia.equals("0")&&donhangkhac.equals("1")){
			thuevat = 0;
		}	
				
		if(donhangkhac.equals("0")&&kho.equals("100000")&&xuathd_coVAT.equals("0")) // ĐƠN HÀNG CÓ ÁP KHUYẾN MÃI: CTKM PHẢI LÀ XUẤT TỪ KHO HÀNG BÁN VÀ CHỌN K XUẤT HÓA ĐƠN CÓ VAT
		{
			return "";
		}
		
		if(thuevat <= 0)
			return "";
		
		kq = formatter1.format(thuevat);
		
		System.out.println("KQ:"+kq);
		return kq;
		
	}
	
	public String FormatThueGTGT(double gtgt, String indongia, String donhangkhac, String kho, String xuathd_coVAT)
	{
		String kq = "";
		
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		
		if(indongia.equals("0")&&donhangkhac.equals("1")){
			if(gtgt <= 0)
				return "";
		}
				
		if(donhangkhac.equals("0")&&kho.equals("100000")&&xuathd_coVAT.equals("0")) // ĐƠN HÀNG CÓ ÁP KHUYẾN MÃI: CTKM PHẢI LÀ XUẤT TỪ KHO HÀNG BÁN VÀ CHỌN K XUẤT HÓA ĐƠN CÓ VAT
		{
			return "";
		}
		
		kq = formatter1.format(gtgt);
		
		return kq;
	}
	
	public String FormatSotientt(double sotientt, String indongia, String donhangkhac, String kho, String xuathd_coVAT)
	{
		String kq = "";
		
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		
		if(indongia.equals("0")&&donhangkhac.equals("1")){
			if(sotientt <= 0)
				return "";
		}
		
		if(donhangkhac.equals("0")&&kho.equals("100000")&&xuathd_coVAT.equals("0")) // ĐƠN HÀNG CÓ ÁP KHUYẾN MÃI: CTKM PHẢI LÀ XUẤT TỪ KHO HÀNG BÁN VÀ CHỌN K XUẤT HÓA ĐƠN CÓ VAT
		{
			return "";
		}
		
		kq = formatter1.format(sotientt);
		
		return kq;
	}
	
	public String FormatSo(double so, String indongia, String donhangkhac, String kho, String xuathd_coVAT)
	{
		String kq = "";
		
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		
		if(indongia.equals("0")&&donhangkhac.equals("1")){
			if(so <= 0)
				return "";
		}
		
		if(donhangkhac.equals("0")&&kho.equals("100000")&&xuathd_coVAT.equals("0")) // ĐƠN HÀNG CÓ ÁP KHUYẾN MÃI: CTKM PHẢI LÀ XUẤT TỪ KHO HÀNG BÁN VÀ CHỌN K XUẤT HÓA ĐƠN CÓ VAT
		{
			return "";
		}
		
		kq = formatter1.format(so);
		
		return kq;
	}

}
