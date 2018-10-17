package geso.traphaco.distributor.servlets.hoadontaichinhNPP;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.distributor.beans.hoadontaichinh.IHoadontaichinh;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.*;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.imp.*;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
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
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfContentByte;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ErphoadontaichinhKMNPPPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErphoadontaichinhKMNPPPdfSvl()
	{
		super();
	}
	float CONVERT = 28.346457f; // = 1cm
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		dbutils db = new dbutils();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		// String userTen = (String) session.getAttribute("userTen");

		Utility util = new Utility();
		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String querystring = request.getQueryString();
		String id = util.antiSQLInspection(request.getParameter("pdf"));
		IErpHoadontaichinhNPP pxkBean = new ErpHoadontaichinhNPP(id);
		pxkBean.setUserId(userId);
		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));

		String task = request.getParameter("task");
		if (querystring.indexOf("pdf") > 0)
		{
			//pxkBean.initPdf();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=PhieuXuatKhoTT.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			
			if(nppId.equals("106174") ) // QUAY HA DONG 
			{
				this.CreatePxk_HADONG(document, outstream, pxkBean);
			}
			else if(nppId.equals("106179")) //  HẢI PHÒNG  : từ 02/10 dùng mẫu mới giống Hà Nội
			{
				this.CreatePxk_HAIPHONG(document, outstream, pxkBean);
			}
			else if(nppId.equals("106162")) //  QUẢNG NINH
			{
				this.CreatePxk_QUANGNINH(document, outstream, pxkBean); 
			}
			else if(nppId.equals("106231")) //  ĐÀ NẴNG
			{
				this.CreatePxk_DANANG(document, outstream, pxkBean); 
			}
			else if(nppId.equals("106249")) //  QUẢNG NGÃI
			{
				this.CreatePxk_QUANGNGAI(document, outstream, pxkBean); 
			}
			else if(nppId.equals("106182")) //  HẢI DƯƠNG
			{
				this.CreatePxk_HAIDUONG(document, outstream, pxkBean);
			}
			else if(nppId.equals("106250")) //  KHÁNH HÒA 106250
			{
				this.CreatePxk_KHANHHOA(document, outstream, pxkBean);
			}
			else if(nppId.equals("106170"))  // THANH HOA
			{
				this.CreatePxk_THANHHOA(document, outstream, pxkBean);
			}
			else if(nppId.equals("106191"))  // PHUTHO
			{
				this.CreatePxk_PHUTHO(document, outstream, pxkBean);
			}
			else if(nppId.equals("106171"))  // NGHE AN
			{
				this.CreatePxk_NGHEAN(document, outstream, pxkBean);
			}
			else if(nppId.equals("106210")) //  CN HỒ CHÍ MINH
			{
				// LẤY MẪU HÓA ĐƠN KHÁCH HÀNG CHỌN TRONG DLN
				
				String query = " SELECT isnull(MAUHOADON,1) AS MAUHD  \n" +
						       " FROM KHACHHANG \n" +
						       " WHERE PK_SEQ =  (select khachhang_fk from ERP_HOADONNPP where pk_seq = "+ id +")";
				ResultSet rs = db.get(query);
				String mauhd = "";
				
				try
				{
					if(rs!= null)
					{
						while(rs.next())
						{
							mauhd = rs.getString("MAUHD");
						}
						rs.close();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				if(mauhd.equals("1")|| mauhd.equals("")) // MẪU CHI NHÁNH (KH ETC KHAI BÁO MẪU 1 / ĐỐI TÁC)
				{
					this.CreatePxk_CNHOCHIMINH_13P(document, outstream, pxkBean);
				}
				else   // MẪU TRÊN HO
				{
					this.CreatePxk_CNHOCHIMINH_14P(document, outstream, pxkBean);
				}
			}
			else
			{
				this.CreatePxk(document, outstream, pxkBean);
			
			}

			
			String msg = this.CapnhatTT(id);
			pxkBean.setMsg(msg);
		} 
		
	}

	private void CreatePxk_NGHEAN(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
				
					//LAY THONG TIN NCC
					String kbh="";
					String ddh="";
					String npp_fk="";
					String khId="";
					
					String ctyTen="";
					String cty_MST ="";
					String cty_Diachi="";
					String cty_Sotaikhoan= "";
					String cty_Dienthoai= "";
					String cty_Fax= "";
					String khoxuat ="";
					String hinhthucTT= "";
					String ngayxuathd ="";
					String nguoimuahang="";
					double chietkhauDH = 0;
					
					
					String sql =
					"SELECT  A.PK_SEQ, A.KBH_FK ,(select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = A.NPP_FK) AS KHO," +
					" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
					" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
					" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS HTTT," +
					" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ngayxuathd," +
					" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
					"               else isnull(nguoimua,'') end " +
					"   FROM ERP_HOADONNPP" +
					"   WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua, isnull(chietkhau,0) as chietkhauDH "  +
				    "FROM ERP_DONDATHANGNPP A " +
				    "WHERE A.PK_SEQ IN  " +
				    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + pxkBean.getId() + "' )";
		
					System.out.println("[INIT_DONHANG]"+sql);
					
					ResultSet rsCheck = db.get(sql);					
										
					if(rsCheck.next())
					{
						npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
						khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
						ddh = rsCheck.getString("PK_SEQ");
						nguoimuahang = rsCheck.getString("nguoimua");
						kbh = rsCheck.getString("KBH_FK");
						khoxuat = rsCheck.getString("KHO");
						hinhthucTT = rsCheck.getString("HTTT");
						ngayxuathd = rsCheck.getString("ngayxuathd");
						chietkhauDH = rsCheck.getDouble("chietkhauDH");
						rsCheck.close();
					}
						   
			 //LAY THONG TIN KHACHHANG
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
				
				
		if(khId.length() > 0){
			 /* sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
			
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
			
		}else{
			/*sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}
			   
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
		   }   
		   
		    NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(2.0f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 2.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open() ;
			
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.17f * CONVERT);
			cell.setPaddingLeft(2.6f * CONVERT);
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
			cell_nguoimua1.setPaddingLeft(4.0f * CONVERT);
			pxk = new Paragraph(nguoimuahang, new Font(bf, 10, Font.NORMAL));
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
			cell8a.setPaddingLeft(3.7f * CONVERT);
			pxk = new Paragraph(Donvi, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell8a.addElement(pxk);
			cell8a.setBorder(0);						
			table1.addCell(cell8a);
			

			// DONG 3 ---- DIA CHI
			PdfPCell cell10 = new PdfPCell();
			cell10.setPaddingLeft(2.5f * CONVERT);	
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
			cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(chuoi1, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell14.addElement(pxk);
			cell14.setBorder(0);						
			table1.addCell(cell14);		
			
			
			//DONG 4 --- DIA CHI : dai se xuong dong
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
			cell14a.addElement(pxk);
			cell14a.setBorder(0);						
			table1.addCell(cell14a);	
			
			
			// DONG 5 ----KHO XUAT
			PdfPCell cell17 = new PdfPCell();	
			cell17.setPaddingLeft(2.9f * CONVERT);
			cell17.setPaddingTop(0.1f * CONVERT);
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
			cell18.setPaddingLeft(5.0f * CONVERT);
			cell18.setPaddingTop(0.1f * CONVERT);
			pxk = new Paragraph( kh_MST +"                                                " +hinhthucTT, new Font(bf, 10, Font.NORMAL));
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
			sanpham.setSpacingBefore(46.8f);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 7.0f, 15.0f, 60f, 17f, 15f, 9.0f, 14.0f, 20f, 24.0f, 8.0f, 23f, 25f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			double totalCK = 0;
			double vatCK = 0;
			
			String query = "";
			
			if(SoNgay(ngayxuathd)){
				query = 
					"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
					"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  \n" +
					"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
					"	case solo when 'NA' then ' ' else b.solo end as solo,  \n" +
					"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, \n" +
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
					"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
				    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
				    "   ELSE 0 END as chenhlech, \n"+
					"   ISNULL((select ISNULL(bgsp.CHIETKHAU,0) \n" +
					"           from BANGGIABANDOITAC bg inner join BANGGIABANDOITAC_DOITAC bgdt on bg.PK_SEQ= bgdt.BANGGIABANDOITAC_FK  \n" +
					"						             inner join BANGGIABANDOITAC_SANPHAM bgsp on bgdt.BANGGIABANDOITAC_FK = bgsp.BGBANDOITAC_FK \n" +
					"						              and bgsp.SANPHAM_FK = dhsp.sanpham_fk  \n" +
					"           where bg.KENH_FK = dh.KBH_FK and bgdt.NPP_FK = dh.NPP_FK ),0) as CHIETKHAU_BG \n" +
					"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 \n" +
					"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ				\n" +					       									
					"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 \n" +
					"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 \n" +
					"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  					\n" +
					"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 \n" +
					"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			else
			{
				query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
							"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
							"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
							"	'' as solo,  " +
							"	'' as NGAYHETHAN, dhsp.thuevat, " +
							"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
							"	,case when	ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) !=1 then 0 else dhsp.chietkhau end as chietkhau "+
							"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
							"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
							"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
							"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
							"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
							"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
							"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double chenhlech = 0;
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt = 0 ;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double vat = rsSP.getDouble("thuevat");
				
				if(SoNgay(ngayxuathd)){
					chenhlech = rsSP.getDouble("chenhlech");
					thanhtien = Math.round(soLUONG * dongia - chietkhau);
					thueGTGT = Math.round(thanhtien*vat/100);
					
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 && khId.trim().length() > 0)
					{
					   thueGTGT = thueGTGT - chenhlech;
					}
										
					sotientt = thanhtien + thueGTGT;
					
					// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
					if(npp_fk.trim().length() > 0)
					{
						dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
						chietkhau = 0;
						thanhtien = Math.round(soLUONG * dongia);
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + thueGTGT;
					}
				}
				else{
					thanhtien = Math.round(soLUONG*dongia);
					thueGTGT = Math.round(thanhtien *vat/100);
					sotientt = thanhtien + thueGTGT ;
					
					vatCK = rsSP.getDouble("thuevat");
					
				}				 

				totalThueGTGT += thueGTGT;
				totalTienTruocVAT+= thanhtien;
				totalCK += chietkhau;
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0 && !rsSP.getString("ngayhethan").equals("null"))
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("solo"), chuoi, rsSP.getString("DONVI"),
						DinhDangTraphaco(formatter.format(soLUONG)), DinhDangTraphaco(formatter4.format(dongia)) , DinhDangTraphaco(formatter.format(thanhtien)), DinhDangTraphaco(formatter.format(vat)), DinhDangTraphaco(formatter.format(thueGTGT)), DinhDangTraphaco(formatter.format(sotientt)) };


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
					
					if(j==4 ) cells.setPaddingLeft(-0.10f *CONVERT);
					
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
							}else
							{	
								if(j==6) 
								  cells.setPaddingLeft(0.3f *CONVERT);
								else
								  cells.setPaddingLeft(0.5f *CONVERT);
							}
							
						}
					}
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
		    
			if(totalCK > 0)
			{
				stt = stt -1 ;
				//CHIẾT KHẤU DÒNG HÀNG (NẾU CÓ) : SUM(CHIETKHAU) CỦA TẤT CẢ CÁC SP
				String [] arr5 = new String[] {Integer.toString(stt+1),"CK" ,"Chiết khấu sản phẩm " ," ","","", DinhDangTraphaco(formatter.format(totalCK)),DinhDangTraphaco(formatter.format(vatCK)),
						DinhDangTraphaco(formatter.format(totalCK*vatCK/100)) ,DinhDangTraphaco(formatter.format( totalCK + totalCK*vatCK/100))};
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
					if(j==2)
					{
						cells.setColspan(3);
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else 
					{
						if(j <= 1)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					
	
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setPaddingTop(2.5f);
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					if(j >=5)
					{
						if(j==5)
						{
							cells.setPaddingLeft(0.25f *CONVERT);
						}else
						{
							if(j==7)
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
			totalSotienTT = Math.round(totalTienTruocVAT) + Math.round(totalThueGTGT);
			
			String[] arr = new String[] {"                             ", DinhDangTraphaco(formatter1.format(totalTienTruocVAT -totalCK )),"",DinhDangTraphaco( formatter1.format(totalThueGTGT- Math.round(totalCK*vatCK/100) )),DinhDangTraphaco(formatter1.format(totalSotienTT - ( totalCK + Math.round(totalCK*vatCK/100) ) )) };
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
			    String tien = doctien.docTien(Math.round(totalSotienTT - (totalCK + Math.round(totalCK*vatCK/100) )));		   
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
	
	private void CreatePxk_PHUTHO(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
				
					//LAY THONG TIN NCC
					String kbh="";
					String ddh="";
					String npp_fk="";
					String khId="";
					
					String ctyTen="";
					String cty_MST ="";
					String cty_Diachi="";
					String cty_Sotaikhoan= "";
					String cty_Dienthoai= "";
					String cty_Fax= "";
					String khoxuat ="";
					String hinhthucTT= "";
					String ngayxuathd ="";
					String nguoimuahang="";
					double chietkhauDH = 0;
					
					String sql =
					"SELECT  A.PK_SEQ, A.KBH_FK ,(select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = A.NPP_FK) AS KHO," +
					" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
					" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
					" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS HTTT," +
					" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ngayxuathd," +
					" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
					"               else isnull(nguoimua,'') end " +
					"   FROM ERP_HOADONNPP" +
					"   WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua, isnull(chietkhau,0) as chietkhauDH "  +
				    "FROM ERP_DONDATHANGNPP A " +
				    "WHERE A.PK_SEQ IN  " +
				    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + pxkBean.getId() + "' )";
		
					System.out.println("[INIT_DONHANG]"+sql);
					
					ResultSet rsCheck = db.get(sql);					
										
					if(rsCheck.next())
					{
						npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
						khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
						ddh = rsCheck.getString("PK_SEQ");
						nguoimuahang = rsCheck.getString("nguoimua");
						kbh = rsCheck.getString("KBH_FK");
						khoxuat = rsCheck.getString("KHO");
						hinhthucTT = rsCheck.getString("HTTT");
						ngayxuathd = rsCheck.getString("ngayxuathd");
						chietkhauDH = rsCheck.getDouble("chietkhauDH");
						rsCheck.close();
					}
			
				
			
			   
			 //LAY THONG TIN KHACHHANG
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
				
				
		if(khId.length() > 0){
			 /* sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
			
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}else{
			/*sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}
			   
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
		   }   
		   
		    NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(2.0f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 2.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open() ;
			
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.17f * CONVERT);
			cell.setPaddingLeft(2.6f * CONVERT);
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
			cell_nguoimua1.setPaddingLeft(4.0f * CONVERT);
			pxk = new Paragraph(nguoimuahang, new Font(bf, 10, Font.NORMAL));
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
			cell8a.setPaddingLeft(3.7f * CONVERT);
			pxk = new Paragraph(Donvi, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell8a.addElement(pxk);
			cell8a.setBorder(0);						
			table1.addCell(cell8a);
			

			// DONG 3 ---- DIA CHI
			PdfPCell cell10 = new PdfPCell();
			cell10.setPaddingLeft(2.5f * CONVERT);	
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
			cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(chuoi1, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell14.addElement(pxk);
			cell14.setBorder(0);						
			table1.addCell(cell14);		
			
			
			//DONG 4 --- DIA CHI : dai se xuong dong
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
			cell14a.addElement(pxk);
			cell14a.setBorder(0);						
			table1.addCell(cell14a);	
			
			
			// DONG 5 ----KHO XUAT
			PdfPCell cell17 = new PdfPCell();	
			cell17.setPaddingLeft(2.9f * CONVERT);
			cell17.setPaddingTop(0.1f * CONVERT);
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
			cell18.setPaddingLeft(5.0f * CONVERT);
			cell18.setPaddingTop(0.1f * CONVERT);
			pxk = new Paragraph( kh_MST +"                                                " +hinhthucTT, new Font(bf, 10, Font.NORMAL));
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
			sanpham.setSpacingBefore(46.8f);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 7.0f, 15.0f, 60f, 17f, 15f, 9.0f, 14.0f, 20f, 24.0f, 8.0f, 23f, 25f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			double totalCK = 0;
			double vatCK = 0;
			
			String query = "";
	if(SoNgay(ngayxuathd)){
		query = 
			"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
			"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  \n" +
			"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
			"	case solo when 'NA' then ' ' else b.solo end as solo,  \n" +
			"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, \n" +
			"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
			"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
		    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
		    "   ELSE 0 END as chenhlech, \n"+
			"   ISNULL((select ISNULL(bgsp.CHIETKHAU,0) \n" +
			"           from BANGGIABANDOITAC bg inner join BANGGIABANDOITAC_DOITAC bgdt on bg.PK_SEQ= bgdt.BANGGIABANDOITAC_FK  \n" +
			"						             inner join BANGGIABANDOITAC_SANPHAM bgsp on bgdt.BANGGIABANDOITAC_FK = bgsp.BGBANDOITAC_FK \n" +
			"						              and bgsp.SANPHAM_FK = dhsp.sanpham_fk  \n" +
			"           where bg.KENH_FK = dh.KBH_FK and bgdt.NPP_FK = dh.NPP_FK ),0) as CHIETKHAU_BG \n" +
			"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 \n" +
			"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ				\n" +					       									
			"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 \n" +
			"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 \n" +
			"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  					\n" +
			"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 \n" +
			"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
	}
	else{
		query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
				"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
				"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
				"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
				"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.thuevat, " +
				"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
			    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
			    "   ELSE 0 END as chenhlech, \n"+
				"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
				"	,case when	ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) !=1 then 0 else dhsp.chietkhau end as chietkhau "+
				"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
				"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
				"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
				"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
				"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
				"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
				"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
	}
			
			
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double vat = 0;
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double chenhlech = rsSP.getDouble("chenhlech");
				
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				vat = rsSP.getDouble("thuevat");
				
				if(SoNgay(ngayxuathd)){
					chenhlech = rsSP.getDouble("chenhlech");
					thanhtien = Math.round(soLUONG * dongia - chietkhau);
					thueGTGT = Math.round(thanhtien*vat/100);
					
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 && khId.trim().length() > 0)
					{
					   thueGTGT = thueGTGT - chenhlech;
					}
										
					sotientt = thanhtien + thueGTGT;
					
					// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
					if(npp_fk.trim().length() > 0)
					{
						dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
						chietkhau = 0;
						thanhtien = Math.round(soLUONG * dongia);
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + thueGTGT;
					}
					
					totalThueGTGT +=thueGTGT;
					totalTienTruocVAT+=thanhtien;
					totalSotienTT +=sotientt;
				}
				else{					
					thanhtien = Math.round(soLUONG*dongia);
					thueGTGT = Math.round(thanhtien *vat/100);
				
				
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 )
					{
					   thueGTGT = thueGTGT - chenhlech ;
					}
					
					sotientt = thanhtien + thueGTGT ;
					
					vatCK = rsSP.getDouble("thuevat");
						
					totalThueGTGT += thueGTGT;
					totalTienTruocVAT+= thanhtien;
					totalCK += chietkhau;
				}
				
			
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0 && !rsSP.getString("ngayhethan").equals("null"))
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("solo"),chuoi, rsSP.getString("DONVI"),
						DinhDangTraphaco(formatter.format(soLUONG)), DinhDangTraphaco(formatter4.format(dongia)) , DinhDangTraphaco(formatter.format(thanhtien)), DinhDangTraphaco(formatter.format(vat)), DinhDangTraphaco(formatter.format(thueGTGT)), DinhDangTraphaco(formatter.format(sotientt)) };


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
					
					if(j==4 ) cells.setPaddingLeft(-0.10f *CONVERT);
					
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
							}else
							{	
								if(j==6) 
								  cells.setPaddingLeft(0.3f *CONVERT);
								else
								  cells.setPaddingLeft(0.5f *CONVERT);
							}
							
						}
					}
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
		    
			if(totalCK > 0)
			{
				stt = stt -1 ;
				//CHIẾT KHẤU DÒNG HÀNG (NẾU CÓ) : SUM(CHIETKHAU) CỦA TẤT CẢ CÁC SP
				String [] arr5 = new String[] {Integer.toString(stt+1),"CK" ,"Chiết khấu sản phẩm " ," ","","", DinhDangTraphaco(formatter.format(totalCK)),DinhDangTraphaco(formatter.format(vatCK)),
						DinhDangTraphaco(formatter.format(totalCK*vatCK/100)) ,DinhDangTraphaco(formatter.format( totalCK + totalCK*vatCK/100))};
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
					if(j==2)
					{
						cells.setColspan(3);
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else 
					{
						if(j <= 1)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					
	
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setPaddingTop(2.5f);
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					if(j >=5)
					{
						if(j==5)
						{
							cells.setPaddingLeft(0.25f *CONVERT);
						}else
						{
							if(j==7)
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
			totalSotienTT = Math.round(totalTienTruocVAT) + Math.round(totalThueGTGT);
			
			String[] arr = new String[] {"                             ", DinhDangTraphaco(formatter1.format(totalTienTruocVAT -totalCK )),"",DinhDangTraphaco( formatter1.format(totalThueGTGT- Math.round(totalCK*vatCK/100) )),DinhDangTraphaco(formatter1.format(totalSotienTT - ( totalCK + Math.round(totalCK*vatCK/100) ) )) };
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
			    String tien = doctien.docTien(Math.round(totalSotienTT - (totalCK + Math.round(totalCK*vatCK/100) )));		   
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
	private void CreatePxk_THANHHOA(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
				
					//LAY THONG TIN NCC
					String kbh="";
					String ddh="";
					String npp_fk="";
					String khId="";
					
					String ctyTen="";
					String cty_MST ="";
					String cty_Diachi="";
					String cty_Sotaikhoan= "";
					String cty_Dienthoai= "";
					String cty_Fax= "";
					String khoxuat ="";
					String hinhthucTT= "";
					String ngayxuathd ="";
					String nguoimuahang="";
					double chietkhauDH = 0;
					
					String sql =
					"SELECT  A.PK_SEQ, A.KBH_FK ,(select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = A.NPP_FK) AS KHO," +
					" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
					" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
					" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS HTTT," +
					" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ngayxuathd," +
					" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
					"               else isnull(nguoimua,'') end " +
					"   FROM ERP_HOADONNPP" +
					"   WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua, isnull(chietkhau,0) as chietkhauDH "  +
				    "FROM ERP_DONDATHANGNPP A " +
				    "WHERE A.PK_SEQ IN  " +
				    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + pxkBean.getId() + "' )";
		
					System.out.println("[INIT_DONHANG]"+sql);
					
					ResultSet rsCheck = db.get(sql);					
										
					if(rsCheck.next())
					{
						npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
						khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
						ddh = rsCheck.getString("PK_SEQ");
						nguoimuahang = rsCheck.getString("nguoimua");
						kbh = rsCheck.getString("KBH_FK");
						khoxuat = rsCheck.getString("KHO");
						hinhthucTT = rsCheck.getString("HTTT");
						ngayxuathd = rsCheck.getString("ngayxuathd");
						chietkhauDH = rsCheck.getDouble("chietkhauDH");
						rsCheck.close();
					}
			
				
			
			   
			 //LAY THONG TIN KHACHHANG
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
				
				
		if(khId.length() > 0){
			 /* sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
			
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}else{
			/*sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}
			   
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
		   }   
		   
		    NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(2.0f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 2.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open() ;
			
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.17f * CONVERT);
			cell.setPaddingLeft(2.6f * CONVERT);
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
			cell_nguoimua1.setPaddingLeft(4.0f * CONVERT);
			pxk = new Paragraph(nguoimuahang, new Font(bf, 10, Font.NORMAL));
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
			cell8a.setPaddingLeft(3.7f * CONVERT);
			pxk = new Paragraph(Donvi, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell8a.addElement(pxk);
			cell8a.setBorder(0);						
			table1.addCell(cell8a);
			

			// DONG 3 ---- DIA CHI
			PdfPCell cell10 = new PdfPCell();
			cell10.setPaddingLeft(2.5f * CONVERT);	
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
			cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(chuoi1, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell14.addElement(pxk);
			cell14.setBorder(0);						
			table1.addCell(cell14);		
			
			
			//DONG 4 --- DIA CHI : dai se xuong dong
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
			cell14a.addElement(pxk);
			cell14a.setBorder(0);						
			table1.addCell(cell14a);	
			
			
			// DONG 5 ----KHO XUAT
			PdfPCell cell17 = new PdfPCell();	
			cell17.setPaddingLeft(2.9f * CONVERT);
			cell17.setPaddingTop(0.1f * CONVERT);
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
			cell18.setPaddingLeft(5.0f * CONVERT);
			cell18.setPaddingTop(0.1f * CONVERT);
			pxk = new Paragraph( kh_MST +"                                                " +hinhthucTT, new Font(bf, 10, Font.NORMAL));
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
			sanpham.setSpacingBefore(46.8f);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 7.0f, 15.0f, 60f, 17f, 15f, 9.0f, 14.0f, 20f, 24.0f, 8.0f, 23f, 25f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			double totalCK = 0;
			double vatCK = 0;
			
			String query = "";
	if(SoNgay(ngayxuathd)){
		query = 
			"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
			"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  \n" +
			"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
			"	case solo when 'NA' then ' ' else b.solo end as solo,  \n" +
			"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, \n" +
			"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
			"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
		    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
		    "   ELSE 0 END as chenhlech, \n"+
			"   ISNULL((select ISNULL(bgsp.CHIETKHAU,0) \n" +
			"           from BANGGIABANDOITAC bg inner join BANGGIABANDOITAC_DOITAC bgdt on bg.PK_SEQ= bgdt.BANGGIABANDOITAC_FK  \n" +
			"						             inner join BANGGIABANDOITAC_SANPHAM bgsp on bgdt.BANGGIABANDOITAC_FK = bgsp.BGBANDOITAC_FK \n" +
			"						              and bgsp.SANPHAM_FK = dhsp.sanpham_fk  \n" +
			"           where bg.KENH_FK = dh.KBH_FK and bgdt.NPP_FK = dh.NPP_FK ),0) as CHIETKHAU_BG \n" +
			"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 \n" +
			"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ				\n" +					       									
			"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 \n" +
			"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 \n" +
			"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  					\n" +
			"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 \n" +
			"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
	}
	else{
		query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
				"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
				"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
				"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
				"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.thuevat, " +
				"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
			    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
			    "   ELSE 0 END as chenhlech, \n"+
				"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
				"	,case when	ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) !=1 then 0 else dhsp.chietkhau end as chietkhau "+
				"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
				"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
				"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
				"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
				"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
				"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
				"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
	}
			
			
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double vat = 0;
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double chenhlech = rsSP.getDouble("chenhlech");
				
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				vat = rsSP.getDouble("thuevat");
				
				if(SoNgay(ngayxuathd)){
					chenhlech = rsSP.getDouble("chenhlech");
					thanhtien = Math.round(soLUONG * dongia - chietkhau);
					thueGTGT = Math.round(thanhtien*vat/100);
					
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 && khId.trim().length() > 0)
					{
					   thueGTGT = thueGTGT - chenhlech;
					}
										
					sotientt = thanhtien + thueGTGT;
					
					// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
					if(npp_fk.trim().length() > 0)
					{
						dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
						chietkhau = 0;
						thanhtien = Math.round(soLUONG * dongia);
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + thueGTGT;
					}
					
					totalThueGTGT +=thueGTGT;
					totalTienTruocVAT+=thanhtien;
					totalSotienTT +=sotientt;
				}
				else{					
					thanhtien = Math.round(soLUONG*dongia);
					thueGTGT = Math.round(thanhtien *vat/100);
				
				
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 )
					{
					   thueGTGT = thueGTGT - chenhlech ;
					}
					
					sotientt = thanhtien + thueGTGT ;
					
					vatCK = rsSP.getDouble("thuevat");
						
					totalThueGTGT += thueGTGT;
					totalTienTruocVAT+= thanhtien;
					totalCK += chietkhau;
				}
				
			
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0 && !rsSP.getString("ngayhethan").equals("null"))
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("solo"),chuoi, rsSP.getString("DONVI"),
						DinhDangTraphaco(formatter.format(soLUONG)), DinhDangTraphaco(formatter4.format(dongia)) , DinhDangTraphaco(formatter.format(thanhtien)), DinhDangTraphaco(formatter.format(vat)), DinhDangTraphaco(formatter.format(thueGTGT)), DinhDangTraphaco(formatter.format(sotientt)) };


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
					
					if(j==4 ) cells.setPaddingLeft(-0.10f *CONVERT);
					
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
							}else
							{	
								if(j==6) 
								  cells.setPaddingLeft(0.3f *CONVERT);
								else
								  cells.setPaddingLeft(0.5f *CONVERT);
							}
							
						}
					}
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
		    
			if(totalCK > 0)
			{
				stt = stt -1 ;
				//CHIẾT KHẤU DÒNG HÀNG (NẾU CÓ) : SUM(CHIETKHAU) CỦA TẤT CẢ CÁC SP
				String [] arr5 = new String[] {Integer.toString(stt+1),"CK" ,"Chiết khấu sản phẩm " ," ","","", DinhDangTraphaco(formatter.format(totalCK)),DinhDangTraphaco(formatter.format(vatCK)),
						DinhDangTraphaco(formatter.format(totalCK*vatCK/100)) ,DinhDangTraphaco(formatter.format( totalCK + totalCK*vatCK/100))};
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
					if(j==2)
					{
						cells.setColspan(3);
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else 
					{
						if(j <= 1)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					
	
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setPaddingTop(2.5f);
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					if(j >=5)
					{
						if(j==5)
						{
							cells.setPaddingLeft(0.25f *CONVERT);
						}else
						{
							if(j==7)
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
			totalSotienTT = Math.round(totalTienTruocVAT) + Math.round(totalThueGTGT);
			
			String[] arr = new String[] {"                             ", DinhDangTraphaco(formatter1.format(totalTienTruocVAT -totalCK )),"",DinhDangTraphaco( formatter1.format(totalThueGTGT- Math.round(totalCK*vatCK/100) )),DinhDangTraphaco(formatter1.format(totalSotienTT - ( totalCK + Math.round(totalCK*vatCK/100) ) )) };
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
			    String tien = doctien.docTien(Math.round(totalSotienTT - (totalCK + Math.round(totalCK*vatCK/100) )));		   
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
	
	private void CreatePxk_HAIPHONG(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean) throws IOException
	{
		System.out.println("----HAI PHONG.......");
		try
		{			
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			String hinhthucTT= "";
			String ngayxuathd ="";
			String nguoimuahang="";
			double chietkhauDH = 0;
			
			String sql =
			"SELECT  A.PK_SEQ, A.KBH_FK ," +
			"	(case when (select isnull(KHO_FK,'0') from KHACHHANG where pk_seq in (select khachhang_fk from ERP_HOADONNPP where pk_seq = '"+ pxkBean.getId() +"')) = 100002 then N'Kho sỉ Nhà Bè'" +
		   	"        else ( select isnull(XUATTAIKHO,' ') from NHAPHANPHOI where pk_seq = A.NPP_FK) end ) XUATTAIKHO, "+ 
			" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
			" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
			" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS HTTT," +
			" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ngayxuathd," +
			" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"               else isnull(nguoimua,'') end " +
			"   FROM ERP_HOADONNPP" +
			"   WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua, isnull(chietkhau,0) as chietkhauDH "  +
		    "FROM ERP_DONDATHANGNPP A " +
		    "WHERE A.PK_SEQ IN  " +
		    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + pxkBean.getId() + "' )";

			System.out.println("[INIT_DONHANG]"+sql);
			
			ResultSet rsCheck = db.get(sql);					
								
			if(rsCheck.next())
			{
				npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
				khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
				ddh = rsCheck.getString("PK_SEQ");
				nguoimuahang = rsCheck.getString("nguoimua");
				kbh = rsCheck.getString("KBH_FK");
				khoxuat = rsCheck.getString("XUATTAIKHO");
				hinhthucTT = rsCheck.getString("HTTT");
				ngayxuathd = rsCheck.getString("ngayxuathd");
				chietkhauDH = rsCheck.getDouble("chietkhauDH");
				rsCheck.close();
			}
				
			   
			 //LAY THONG TIN KHACHHANG
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
			 /* sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
		
				//LẤY THEO DỮ LIỆU MỚI
				sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
		   }   
		
		   
	    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
		
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(1.0f*CONVERT, 0.1f*CONVERT, 2.0f*CONVERT, 2.0f*CONVERT); // L,R,T,B
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
		cell_nguoimua.setPaddingTop(-5.6f);
		pxk.setSpacingAfter(4);
		cell_nguoimua.addElement(pxk);
		cell_nguoimua.setBorder(0);						
		table1.addCell(cell_nguoimua);	
		
		PdfPCell cell_nguoimua1 = new PdfPCell();
		cell_nguoimua1.setPaddingTop(0.5f * CONVERT);
		cell_nguoimua1.setPaddingLeft(4.9f * CONVERT);
		pxk = new Paragraph(nguoimuahang, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		cell_nguoimua1.setPaddingTop(-5.6f);
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
		
		//kh_MST = "";
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
		
		
		System.out.println("----MA SO THUE: " + kh_MST);
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
		sanpham.setSpacingBefore(37.6f); 
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
			
			String query = 		"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
								"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  \n" +
								"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
								"	case solo when 'NA' then ' ' else b.solo end as solo,  \n" +
								"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.thuevat, \n" +
								"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
							    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
							    "   ELSE 0 END as chenhlech, \n"+
								"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 \n" +
								"	,case when	ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) !=1 then 0 else dhsp.chietkhau end as chietkhau \n"+
								"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 \n" +
								"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ	\n" +
								"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    \n" +
								"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 \n" +
								"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 \n" +
								"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	\n " +
								"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' \n";
		System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
	
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			double thanhtien = 0;	
			double thueGTGT = 0;
			double sotientt = 0;
			double chenhlech = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				if(SoNgay(ngayxuathd)){
					chenhlech = rsSP.getDouble("chenhlech");
					
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					System.out.println("thuevat"+rsSP.getDouble("thuevat"));
					
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 && khId.trim().length() > 0)
					{
					   thueGTGT = thueGTGT - chenhlech;
					}
										
					sotientt = thanhtien + thueGTGT;
					
					// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
					if(npp_fk.trim().length() > 0)
					{
						dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
						chietkhau = 0;
						thanhtien = Math.round(soLUONG * dongia);
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + thueGTGT;
					}
					
					totalThueGTGT += thueGTGT;
					totalTienTruocVAT+= thanhtien;
					System.out.println("totalTienTruocVAT: "+totalTienTruocVAT);
					totalSotienTT += sotientt;
				}
				else{
					thanhtien = soLUONG * dongia - chietkhau;	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + (thanhtien*rsSP.getDouble("thuevat")/100);
						
					totalThueGTGT += thanhtien*rsSP.getDouble("thuevat")/100;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr ;
				
				if(SoNgay(ngayxuathd)){
					arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTraphaco(formatter1.format(soLUONG)), DinhDangTraphaco(formatter4.format(dongia)),DinhDangTraphaco(formatter1.format(thanhtien)),DinhDangTraphaco(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTraphaco(formatter1.format(thueGTGT)),DinhDangTraphaco(formatter1.format(sotientt)) };
				}
				else{
					arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTraphaco(formatter1.format(soLUONG)), DinhDangTraphaco(formatter.format(dongia)),DinhDangTraphaco(formatter1.format(thanhtien)),DinhDangTraphaco(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTraphaco(formatter1.format(thueGTGT)),DinhDangTraphaco(formatter1.format(sotientt)) };
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
						
				
			stt= stt-1;
			
						
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
		
		double truocVAT = 0;
		String sauVat = "";
		String tienVat = "";
		
		if(SoNgay(ngayxuathd)){
			truocVAT = totalTienTruocVAT ;
			tienVat = formatter1.format(totalThueGTGT );
			sauVat = formatter1.format(totalSotienTT ) ;
		}
		else{
			System.out.println("vao eosle "+sauVat);
			truocVAT = totalTienTruocVAT ;
			tienVat = formatter1.format(totalThueGTGT );
			sauVat = formatter1.format(totalSotienTT ) ;
			
			/*truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""));
			tienVat = pxkBean.getTongVAT();
			sauVat = pxkBean.getTongtienAVAT();*/
		}	
		
		String[] arr = new String[] { " ", " " , " ", " "," ", " "," "," ", DinhDangTraphaco(formatter1.format(truocVAT)), " " , DinhDangTraphaco(tienVat), DinhDangTraphaco(sauVat) };
		
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
				
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				//cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(-2.8f);
				cells.setBorderWidth(0);
				cells.setFixedHeight(0.7f*CONVERT);
				sanpham.addCell(cells);
			}
						
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
		    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
			String tien = doctien.docTien(Long.parseLong(sauVat.replaceAll(",", "")));
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
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(-4.9f);
				cells.setBorderWidth(0);
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
	
	private void CreatePxk_KHANHHOA(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean) throws IOException
	{
		try
		{			
			dbutils db = new dbutils();
				
					//LAY THONG TIN NCC
					String kbh="";
					String ddh="";
					String npp_fk="";
					String khId="";
					
					String ctyTen="";
					String cty_MST ="";
					String cty_Diachi="";
					String cty_Sotaikhoan= "";
					String cty_Dienthoai= "";
					String cty_Fax= "";
					String khoxuat ="";
					String hinhthucTT= "";
					String ngayxuathd ="";
					String nguoimuahang="";
					double chietkhauDH = 0;
					
					String sql =
					"SELECT  A.PK_SEQ, A.KBH_FK ,(select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = A.NPP_FK) AS KHO," +
					" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
					" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
					" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS HTTT," +
					" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ngayxuathd," +
					" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
					"               else isnull(nguoimua,'') end " +
					"   FROM ERP_HOADONNPP" +
					"   WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua, isnull(chietkhau,0) as chietkhauDH "  +
				    "FROM ERP_DONDATHANGNPP A " +
				    "WHERE A.PK_SEQ IN  " +
				    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + pxkBean.getId() + "' )";
		
					System.out.println("[INIT_DONHANG]"+sql);
					
					ResultSet rsCheck = db.get(sql);					
										
					if(rsCheck.next())
					{
						npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
						khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
						ddh = rsCheck.getString("PK_SEQ");
						nguoimuahang = rsCheck.getString("nguoimua");
						kbh = rsCheck.getString("KBH_FK");
						khoxuat = rsCheck.getString("KHO");
						hinhthucTT = rsCheck.getString("HTTT");
						ngayxuathd = rsCheck.getString("ngayxuathd");
						chietkhauDH = rsCheck.getDouble("chietkhauDH");
						rsCheck.close();
					}
			
				
			
			   
			 //LAY THONG TIN KHACHHANG
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
				
				
		if(khId.length() > 0){
			 /* sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
			
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}else{
			/*sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}
			   
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
		   }   
		   
		    NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			NumberFormat formatter4 = new DecimalFormat("#,###,###.####");			
		
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
			pxk = new Paragraph(nguoimuahang, new Font(bf, 11, Font.BOLD));
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
			double totalCK = 0;
			double vatCK = 0;
			
			/*String 			
			query =
			" select distinct c.MA, c.TEN, d.DONVI, b.soluong, ' ' as solo, ' ' as  NGAYHETHAN, dhsp.chietkhau,dhsp.thuevat, dhsp.dongia  "+	
			" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq=b.ycxk_fk	"+
			"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk= a.PK_SEQ									"+        									
			"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ=e.ddh_fk    									"+
			"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	"+
			"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						"+
			"     left join DONVIDOLUONG d on d.PK_SEQ=c.dvdl_fk 	"+
			" where a.PK_SEQ =( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk= '"+ ddh +"' )";*/
			
			
			/*String query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
							"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
							"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
							"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
							"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, dhsp.dongia  	 " +
							"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
							"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
							"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
							"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
							"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
							"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
							"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";*/
			
			String query = "";
			if(SoNgay(ngayxuathd)){
					query = 
						"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
						"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  \n" +
						"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
						"	case solo when 'NA' then ' ' else b.solo end as solo,  \n" +
						"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, \n" +
						"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
						"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
					    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
					    "   ELSE 0 END as chenhlech, \n"+
						"   ISNULL((select ISNULL(bgsp.CHIETKHAU,0) \n" +
						"           from BANGGIABANDOITAC bg inner join BANGGIABANDOITAC_DOITAC bgdt on bg.PK_SEQ= bgdt.BANGGIABANDOITAC_FK  \n" +
						"						             inner join BANGGIABANDOITAC_SANPHAM bgsp on bgdt.BANGGIABANDOITAC_FK = bgsp.BGBANDOITAC_FK \n" +
						"						              and bgsp.SANPHAM_FK = dhsp.sanpham_fk  \n" +
						"           where bg.KENH_FK = dh.KBH_FK and bgdt.NPP_FK = dh.NPP_FK ),0) as CHIETKHAU_BG \n" +
						"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 \n" +
						"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ				\n" +					       									
						"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 \n" +
						"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 \n" +
						"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  					\n" +
						"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 \n" +
						"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			
			}
			else{
						query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
								"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
								"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
								"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
								"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, " +
								"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
								"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
								"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
								"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
								"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
								"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
								"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
								"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			
			System.out.println("....ERPDONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt = 0 ;
			double chenhlech = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double vat = rsSP.getDouble("thuevat");
				if(SoNgay(ngayxuathd)){
					chenhlech = rsSP.getDouble("chenhlech");
					thanhtien = Math.round(soLUONG * dongia - chietkhau);
					thueGTGT = Math.round(thanhtien*vat/100);
					
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 && khId.trim().length() > 0)
					{
					   thueGTGT = thueGTGT - chenhlech;
					}
										
					sotientt = thanhtien + thueGTGT;
					
					// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
					if(npp_fk.trim().length() > 0)
					{
						dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
						chietkhau = 0;
						thanhtien = Math.round(soLUONG * dongia);
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + thueGTGT;
					}
				}
				else{
					thanhtien = soLUONG*dongia;
					thueGTGT = thanhtien *vat/100;
					sotientt = thanhtien + (thanhtien*vat/100) ;
					
					vatCK = rsSP.getDouble("thuevat");
				}
					
				if(ddh.equals("100526")) // Tam thoi sua Vat cho Nhà QN 
				{
					thueGTGT = 571428 ;
					sotientt = thanhtien + thueGTGT;
					totalThueGTGT += thueGTGT ;
					totalTienTruocVAT+= (soLUONG*dongia);
					totalCK += chietkhau;
					
				}
				else
				{
					if(SoNgay(ngayxuathd)){
						totalThueGTGT +=thueGTGT;
						totalTienTruocVAT+=thanhtien;
						totalSotienTT +=sotientt;
					}
					else{
						totalThueGTGT += (soLUONG*dongia)*vat/100;
						totalTienTruocVAT+= (soLUONG*dongia);
						totalCK += chietkhau;
					}
				}
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0 && !rsSP.getString("ngayhethan").equals("null"))
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				thanhtien = Math.round(thanhtien);
				thueGTGT = Math.round(thueGTGT);
				sotientt = Math.round(sotientt);
				
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("solo"),
							chuoi, rsSP.getString("DONVI"),
						DinhDangTraphaco(formatter.format(soLUONG)), DinhDangTraphaco(formatter4.format(dongia)) , DinhDangTraphaco(formatter.format(thanhtien)), DinhDangTraphaco(formatter.format(rsSP.getDouble("thuevat"))), DinhDangTraphaco(formatter.format(thueGTGT)), DinhDangTraphaco(formatter.format(sotientt)) };
				

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
			if(totalCK > 0)
			{				
				//CHIẾT KHẤU DÒNG HÀNG (NẾU CÓ) : SUM(CHIETKHAU) CỦA TẤT CẢ CÁC SP
				String [] arr5 = new String[] {Integer.toString(stt+1),"Chiết khấu sản phẩm " ," ","","","","", formatter.format(totalCK),formatter.format(vatCK),
						formatter.format(totalCK*vatCK/100) ,formatter.format( totalCK + totalCK*vatCK/100)};				
				
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
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
					
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setPaddingTop(2.5f);
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);

					
					

					sanpham.addCell(cells);
				}
			}
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
			totalSotienTT = Math.round(totalTienTruocVAT) + Math.round(totalThueGTGT);
			
			String[] arr = new String[] {  " " , " ", " "," ", " "," "," ", DinhDangTraphaco(formatter1.format(totalTienTruocVAT -totalCK )),"",DinhDangTraphaco( formatter1.format(totalThueGTGT- (totalCK*vatCK/100) )),DinhDangTraphaco(formatter1.format(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100) ))) };
			
			
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
			    String tien = doctien.docTien(Math.round(totalSotienTT - (totalCK + totalCK*vatCK/100)));		   
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
	
	private void CreatePxk_HAIDUONG(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean) throws IOException
	{

		try
		{
						
			dbutils db = new dbutils();
				
					//LAY THONG TIN NCC
					String kbh="";
					String ddh="";
					String npp_fk="";
					String khId="";
					
					String ctyTen="";
					String cty_MST ="";
					String cty_Diachi="";
					String cty_Sotaikhoan= "";
					String cty_Dienthoai= "";
					String cty_Fax= "";
					String khoxuat ="";
					String hinhthucTT= "";
					String ngayxuathd ="";
					String nguoimuahang="";
					double chietkhauDH = 0;
					
					String sql =
					"SELECT  A.PK_SEQ, A.KBH_FK ,(select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = A.NPP_FK) AS KHO," +
					" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
					" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
					" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS HTTT," +
					" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ngayxuathd," +
					" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
					"               else isnull(nguoimua,'') end " +
					"   FROM ERP_HOADONNPP" +
					"   WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua, isnull(chietkhau,0) as chietkhauDH "  +
				    "FROM ERP_DONDATHANGNPP A " +
				    "WHERE A.PK_SEQ IN  " +
				    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + pxkBean.getId() + "' )";
		
					System.out.println("[INIT_DONHANG]"+sql);
					
					ResultSet rsCheck = db.get(sql);					
										
					if(rsCheck.next())
					{
						npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
						khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
						ddh = rsCheck.getString("PK_SEQ");
						nguoimuahang = rsCheck.getString("nguoimua");
						kbh = rsCheck.getString("KBH_FK");
						khoxuat = rsCheck.getString("KHO");
						hinhthucTT = rsCheck.getString("HTTT");
						ngayxuathd = rsCheck.getString("ngayxuathd");
						chietkhauDH = rsCheck.getDouble("chietkhauDH");
						rsCheck.close();
					}
			
				
			
			   
			 //LAY THONG TIN KHACHHANG
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
				
				
		if(khId.length() > 0){
			 /* sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
			
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}else{
			/*sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}
			   
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
		   }   
		   
		    NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.3f*CONVERT, 0.1f*CONVERT, 1.7f*CONVERT, 2.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open() ;
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.97f * CONVERT);
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
			pxk = new Paragraph(nguoimuahang, new Font(bf, 10, Font.NORMAL));
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
			cell10.setPaddingLeft(3.1f * CONVERT);	
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
			cell14.setPaddingLeft(2.0f * CONVERT);
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
			cell14a.setPaddingLeft(2.0f * CONVERT);
			pxk = new Paragraph(chuoi2, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);		
			cell14a.addElement(pxk);
			cell14a.setBorder(0);						
			table1.addCell(cell14a);	
			
			
			// DONG 5 ----KHO XUAT
			PdfPCell cell17 = new PdfPCell();	
			cell17.setPaddingLeft(3.8f * CONVERT);
			cell17.setPaddingTop(0.05f * CONVERT);
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
			cell18.setPaddingTop(0.05f * CONVERT);
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
			sanpham.setSpacingBefore(40.0f); 
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 7.0f, 15.0f, 60f, 17f, 15f, 9.0f, 14.0f, 20f, 24.0f, 8.0f, 23f, 25f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			double totalCK = 0;
			double vatCK = 0;
			
			String query = "";
			/*String 			
			query =
			" select distinct c.MA, c.TEN, d.DONVI, b.soluong, ' ' as solo, ' ' as  NGAYHETHAN, dhsp.chietkhau,dhsp.thuevat, dhsp.dongia  "+	
			" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq=b.ycxk_fk	"+
			"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk= a.PK_SEQ									"+        									
			"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ=e.ddh_fk    									"+
			"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	"+
			"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						"+
			"     left join DONVIDOLUONG d on d.PK_SEQ=c.dvdl_fk 	"+
			" where a.PK_SEQ =( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk= '"+ ddh +"' )";*/
			
			
			/*String query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
							"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
							"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
							"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
							"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, dhsp.dongia  	 " +
							"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
							"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
							"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
							"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
							"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
							"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
							"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";*/
			
			if(SoNgay(ngayxuathd)){
				query = 
					"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
					"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  \n" +
					"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
					"	case solo when 'NA' then ' ' else b.solo end as solo,  \n" +
					"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, \n" +
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
					"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
				    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
				    "   ELSE 0 END as chenhlech, \n"+
					"   ISNULL((select ISNULL(bgsp.CHIETKHAU,0) \n" +
					"           from BANGGIABANDOITAC bg inner join BANGGIABANDOITAC_DOITAC bgdt on bg.PK_SEQ= bgdt.BANGGIABANDOITAC_FK  \n" +
					"						             inner join BANGGIABANDOITAC_SANPHAM bgsp on bgdt.BANGGIABANDOITAC_FK = bgsp.BGBANDOITAC_FK \n" +
					"						              and bgsp.SANPHAM_FK = dhsp.sanpham_fk  \n" +
					"           where bg.KENH_FK = dh.KBH_FK and bgdt.NPP_FK = dh.NPP_FK ),0) as CHIETKHAU_BG \n" +
					"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 \n" +
					"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ				\n" +					       									
					"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 \n" +
					"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 \n" +
					"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  					\n" +
					"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 \n" +
					"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			else{
				query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
						"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
						"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
						"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
						"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, " +
						"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
						"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
						"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
						"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
						"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
						"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
						"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
						"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			
			System.out.println("....ERPDONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt = 0 ;
			double chenhlech = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double vat = rsSP.getDouble("thuevat");
				
				if(SoNgay(ngayxuathd)){
					chenhlech = rsSP.getDouble("chenhlech");
					thanhtien = Math.round(soLUONG * dongia - chietkhau);
					thueGTGT = Math.round(thanhtien*vat/100);
					
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 && khId.trim().length() > 0)
					{
					   thueGTGT = thueGTGT - chenhlech;
					}
										
					sotientt = thanhtien + thueGTGT;
					
					// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
					if(npp_fk.trim().length() > 0)
					{
						dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
						chietkhau = 0;
						thanhtien = Math.round(soLUONG * dongia);
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + thueGTGT;
					}
					
				}else{	
					thanhtien = soLUONG*dongia;
					thueGTGT = thanhtien *vat/100;
					sotientt = thanhtien + (thanhtien*vat/100) ;
					
					vatCK = rsSP.getDouble("thuevat");
				}
					
				if(ddh.equals("100526")) // Tam thoi sua Vat cho Nhà QN 
				{
					thueGTGT = 571428 ;
					sotientt = thanhtien + thueGTGT;
					totalThueGTGT += thueGTGT ;
					totalTienTruocVAT+= (soLUONG*dongia);
					totalCK += chietkhau;
					
				}
				else
				{
					if(SoNgay(ngayxuathd)){
						totalThueGTGT +=thueGTGT;
						totalTienTruocVAT+=thanhtien;
						totalSotienTT +=sotientt;
					}
					else{
						totalThueGTGT += (soLUONG*dongia)*vat/100;
						totalTienTruocVAT+= (soLUONG*dongia);
						totalCK += chietkhau;
					}
				}
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0 && !rsSP.getString("ngayhethan").equals("null"))
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				thanhtien = Math.round(thanhtien);
				thueGTGT = Math.round(thueGTGT);
				sotientt = Math.round(sotientt);
				
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("solo"),chuoi, rsSP.getString("DONVI"),
						DinhDangTraphaco(formatter.format(soLUONG)), DinhDangTraphaco(formatter4.format(dongia)) , DinhDangTraphaco(formatter.format(thanhtien)), DinhDangTraphaco(formatter.format(vat)), DinhDangTraphaco(formatter.format(thueGTGT)), DinhDangTraphaco(formatter.format(sotientt)) };


				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j <=4 ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);
					
					if(j <= 2)
						cells.setPaddingLeft(0.2f *CONVERT);
					if(j == 5)
						cells.setPaddingRight(0.3f*CONVERT);
					if(j == 6)
						cells.setPaddingRight(0.2f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(0.4f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(0.65f*CONVERT);
					if(j == 9)
						cells.setPaddingRight(0.45f*CONVERT);
					if(j == 10 || j== 11)
						cells.setPaddingRight(0.7f*CONVERT);
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
		    
			stt = stt -1 ;
			if(totalCK > 0)
			{				
				//CHIẾT KHẤU DÒNG HÀNG (NẾU CÓ) : SUM(CHIETKHAU) CỦA TẤT CẢ CÁC SP
				String [] arr5 = new String[] {Integer.toString(stt+1),"CK" ,"Chiết khấu sản phẩm " ," ","","","","", formatter.format(totalCK),formatter.format(vatCK),
						formatter.format(totalCK*vatCK/100) ,formatter.format( totalCK + totalCK*vatCK/100)};
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10, Font.BOLD)));
					if (j <=4 ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);
					
					if(j <= 2)
						cells.setPaddingLeft(0.2f *CONVERT);
					if(j == 5)
						cells.setPaddingRight(0.3f*CONVERT);
					if(j == 6)
						cells.setPaddingRight(0.2f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(0.4f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(0.65f*CONVERT);
					if(j == 9)
						cells.setPaddingRight(0.45f*CONVERT);
					if(j == 10 || j== 11)
						cells.setPaddingRight(0.7f*CONVERT);
					sanpham.addCell(cells);
				}
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
			totalSotienTT = Math.round(totalTienTruocVAT) + Math.round(totalThueGTGT);
			
			String[] arr = new String[] { " ", " " , " ", " "," ", " "," "," ", DinhDangTraphaco(formatter1.format(totalTienTruocVAT -totalCK )),"",DinhDangTraphaco( formatter1.format(totalThueGTGT- (totalCK*vatCK/100) )),DinhDangTraphaco(formatter1.format(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100) ))) };
			
			for (int j = 0; j < arr.length; j++)
				{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j <=4 ){
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				if(j <= 2)
					cells.setPaddingLeft(0.2f *CONVERT);
				if(j == 5)
					cells.setPaddingRight(0.3f*CONVERT);
				if(j == 6)
					cells.setPaddingRight(0.2f*CONVERT);
				if(j == 7)
					cells.setPaddingRight(0.4f*CONVERT);
				if(j == 8)
					cells.setPaddingRight(0.65f*CONVERT);
				if(j == 9)
					cells.setPaddingRight(0.45f*CONVERT);
				if(j == 10 || j== 11)
					cells.setPaddingRight(0.7f*CONVERT);
				
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingTop(2.8f);
				cells.setBorder(0);
				cells.setFixedHeight(0.7f*CONVERT);
				sanpham.addCell(cells);
			}
				
				
				// Tien bang chu
				doctienrachu doctien = new doctienrachu();
			    String tien = doctien.docTien(Math.round(totalSotienTT - (totalCK + totalCK*vatCK/100)));		   
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


	private void CreatePxk_CNHOCHIMINH_14P(Document document,ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean) 
	{
		try
		{
						
			dbutils db = new dbutils();
				
					//LAY THONG TIN NCC
					String kbh="";
					String ddh="";
					String npp_fk="";
					String khId="";
					
					String ctyTen="";
					String cty_MST ="";
					String cty_Diachi="";
					String cty_Sotaikhoan= "";
					String cty_Dienthoai= "";
					String cty_Fax= "";
					String khoxuat ="";
					String hinhthucTT= "";
					String ngayxuathd ="";
					String nguoimuahang="";
					double chietkhauDH = 0;
					int ingia = 0;

					
					String sql =
					"SELECT  A.PK_SEQ, A.KBH_FK ," +
					"	(case when (select isnull(KHO_FK,'0') from KHACHHANG where pk_seq in (select khachhang_fk from ERP_HOADONNPP where pk_seq = '"+ pxkBean.getId() +"')) = 100002 then N'Kho sỉ Nhà Bè'" +
				   	"        else ( select isnull(XUATTAIKHO,' ') from NHAPHANPHOI where pk_seq = A.NPP_FK) end ) XUATTAIKHO, "+ 
					" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
					" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
					" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS HTTT," +
					" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ngayxuathd," +
					" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
					"               else isnull(nguoimua,'') end " +
					"   FROM ERP_HOADONNPP" +
					"   WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua, isnull(chietkhau,0) as chietkhauDH, isnull(isingia, 0) ingia "  +
				    "FROM ERP_DONDATHANGNPP A " +
				    "WHERE A.PK_SEQ IN  " +
				    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + pxkBean.getId() + "' )";
		
					System.out.println("[INIT_DONHANG]"+sql);
					
					ResultSet rsCheck = db.get(sql);					
										
					if(rsCheck.next())
					{
						npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
						khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
						ddh = rsCheck.getString("PK_SEQ");
						nguoimuahang = rsCheck.getString("nguoimua");
						kbh = rsCheck.getString("KBH_FK");
						khoxuat = rsCheck.getString("XUATTAIKHO");
						hinhthucTT = rsCheck.getString("HTTT");
						ngayxuathd = rsCheck.getString("ngayxuathd");
						chietkhauDH = rsCheck.getDouble("chietkhauDH");
						ingia = rsCheck.getInt("ingia");
						rsCheck.close();
					}
			
				if(ingia == 0)
					hinhthucTT = "Không thu tiền";
			
			   
			 //LAY THONG TIN KHACHHANG
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
				
				

		/*	  sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
		
				//LẤY THEO DỮ LIỆU MỚI
				sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
		   }   
		   
		    NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(2.0f*CONVERT, 1.5f*CONVERT, 4.2f*CONVERT, 2.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open() ;
			
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.17f * CONVERT);
			cell.setPaddingLeft(2.6f * CONVERT);
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
			cell_nguoimua1.setPaddingLeft(4.0f * CONVERT);
			pxk = new Paragraph(nguoimuahang, new Font(bf, 10, Font.NORMAL));
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
			cell8a.setPaddingLeft(3.7f * CONVERT);
			pxk = new Paragraph(Donvi, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell8a.addElement(pxk);
			cell8a.setBorder(0);						
			table1.addCell(cell8a);
			

			// DONG 3 ---- DIA CHI
			PdfPCell cell10 = new PdfPCell();
			cell10.setPaddingLeft(2.5f * CONVERT);	
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
			cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(chuoi1, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell14.addElement(pxk);
			cell14.setBorder(0);						
			table1.addCell(cell14);		
			
			
			//DONG 4 --- DIA CHI : dai se xuong dong
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
			cell14a.addElement(pxk);
			cell14a.setBorder(0);						
			table1.addCell(cell14a);	
			
			
			// DONG 5 ----KHO XUAT
			PdfPCell cell17 = new PdfPCell();	
			cell17.setPaddingLeft(2.9f * CONVERT);
			cell17.setPaddingTop(0.1f * CONVERT);
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
			cell18.setPaddingLeft(5.0f * CONVERT);
			cell18.setPaddingTop(0.1f * CONVERT);
			pxk = new Paragraph( kh_MST +"                                                " +hinhthucTT, new Font(bf, 10, Font.NORMAL));
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
			sanpham.setSpacingBefore(46.8f);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 7.0f, 15.0f, 60f, 17f, 15f, 9.0f, 14.0f, 20f, 24.0f, 8.0f, 23f, 25f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			double totalCK = 0;
			double vatCK = 0;
			
			/*String 			
			query =
			" select distinct c.MA, c.TEN, d.DONVI, b.soluong, ' ' as solo, ' ' as  NGAYHETHAN, dhsp.chietkhau,dhsp.thuevat, dhsp.dongia  "+	
			" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq=b.ycxk_fk	"+
			"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk= a.PK_SEQ									"+        									
			"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ=e.ddh_fk    									"+
			"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	"+
			"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						"+
			"     left join DONVIDOLUONG d on d.PK_SEQ=c.dvdl_fk 	"+
			" where a.PK_SEQ =( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk= '"+ ddh +"' )";*/
			
			
			/*String query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
							"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
							"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
							"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
							"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, dhsp.dongia  	 " +
							"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
							"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
							"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
							"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
							"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
							"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
							"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";*/
			
		String	 query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
							"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
							"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
							"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
							"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.thuevat, " +
							"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
						    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
						    "   ELSE 0 END as chenhlech, \n"+
							"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
							"	,case when	ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) !=1 then 0 else dhsp.chietkhau end as chietkhau "+
							"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
							"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
							"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
							"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
							"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
							"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
							"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			
			
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			 double sotientt = 0;
			 double thanhtien = 0;
			 double thueGTGT = 0;
			while(rsSP.next())
			{
				double chenhlech = rsSP.getDouble("chenhlech");
				
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double vat = rsSP.getDouble("thuevat");
				
				
				// NẾU SỬA TIỀN VAT >> +- TIENVAT DÒNG ĐẦU TIÊN 
				if(chenhlech != 0 )
				{
				   thueGTGT = thueGTGT - chenhlech ;
				}
				
				 if(SoNgay(ngayxuathd)){
						thanhtien = Math.round(soLUONG * dongia - chietkhau);
						thueGTGT = Math.round(thanhtien*vat/100);
						
						// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
						if(chenhlech != 0 && khId.trim().length() > 0)
						{
						   thueGTGT = thueGTGT - chenhlech;
						}
											
						sotientt = thanhtien + thueGTGT;
						
						// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
						if(npp_fk.trim().length() > 0)
						{
							dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
							chietkhau = 0;
							thanhtien = Math.round(soLUONG * dongia);
							thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
							sotientt = thanhtien + thueGTGT;
						}
						
					}
				 else{
						 vatCK = rsSP.getDouble("thuevat");
						 sotientt = thanhtien + thueGTGT ;
						 thanhtien = Math.round(soLUONG*dongia);
						 thueGTGT = Math.round(thanhtien *vat/100);
				 }
					
				if(ddh.equals("100526")) // Tam thoi sua Vat cho Nhà QN 
				{
					thueGTGT = 571428 ;
					sotientt = thanhtien + thueGTGT;
					totalThueGTGT += thueGTGT ;
					totalTienTruocVAT+= (soLUONG*dongia);
					totalCK += chietkhau;
					
				}
				else
				{
					totalThueGTGT += thueGTGT;
					totalTienTruocVAT+= thanhtien ;
					totalCK += chietkhau;
				}
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0 && !rsSP.getString("ngayhethan").equals("null"))
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				thanhtien = Math.round(thanhtien);
				thueGTGT = Math.round(thueGTGT);
				sotientt = Math.round(sotientt);
				
				
				String inDONGIA = "";
				String inTHANHTIEN = "";
				String inTHUEVAT = "";
				String inTHUEGTGT = "";
				String inSOTIENTT = "";
				
				if(ingia == 1)
				{
					if(dongia <= 0) inDONGIA = "";
					else  inDONGIA = formatter4.format(dongia);
					
					if(thanhtien <=0 ) inTHANHTIEN = "";
					else  inTHANHTIEN = formatter.format(thanhtien);
					
					if( vat <=0 ) inTHUEVAT = "";
					else  inTHUEVAT = formatter.format(vat);
					
					if(thueGTGT <= 0) inTHUEGTGT = "";
					else  inTHUEGTGT = formatter.format(thueGTGT);
					
					if(sotientt <= 0) inSOTIENTT = "";
					else  inSOTIENTT = formatter.format(sotientt) ;
				
				}
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("solo"),chuoi, rsSP.getString("DONVI"),
						DinhDangTraphaco(formatter.format(soLUONG)), DinhDangTraphaco(inDONGIA) , DinhDangTraphaco(inTHANHTIEN), DinhDangTraphaco(inTHUEVAT), DinhDangTraphaco(inTHUEGTGT), DinhDangTraphaco(inSOTIENTT) };


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
					
					if(j==4 ) cells.setPaddingLeft(-0.10f *CONVERT);
					
					if(j==7) cells.setPaddingRight(-0.1f*CONVERT);
					

					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
		    
			if(totalCK > 0)
			{
				stt = stt -1 ;
				//CHIẾT KHẤU DÒNG HÀNG (NẾU CÓ) : SUM(CHIETKHAU) CỦA TẤT CẢ CÁC SP
				String [] arr5 = new String[] {Integer.toString(stt+1),"CK" ,"Chiết khấu sản phẩm " ," ","","","","" ,DinhDangTraphaco(formatter.format(totalCK)),DinhDangTraphaco(formatter.format(vatCK)),
						DinhDangTraphaco(formatter.format(totalCK*vatCK/100)) ,DinhDangTraphaco(formatter.format( totalCK + totalCK*vatCK/100))};
				for (int j = 0; j < arr5.length; j++)
				{
					
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
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
					
					if(j==4 ) cells.setPaddingLeft(-0.10f *CONVERT);
					
					if(j==7) cells.setPaddingRight(-0.1f*CONVERT);
					
	
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setPaddingTop(2.5f);
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					
					
	
					sanpham.addCell(cells);
				}
			}
			// DONG TRONG
			stt = stt -1 ;
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
			
				String inTIEN1 ="";
				String inTIEN2 ="";
				String inTIEN3 ="";
				
				if(ingia == 1)
				{
					if(totalTienTruocVAT -totalCK  <=0) inTIEN1 = "";
					else 
						inTIEN1 = formatter1.format(totalTienTruocVAT -totalCK );
					
					if((totalThueGTGT- (totalCK*vatCK/100))  <=0) inTIEN2 = "";
					else 
						inTIEN2 = formatter1.format(totalThueGTGT- (totalCK*vatCK/100) );
					
					if(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100)) <= 0 ) inTIEN3 = "";
					else 
						inTIEN3 = formatter1.format(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100) ));
				}
				
			// Tong tien thanh toan	
			totalSotienTT = Math.round(totalTienTruocVAT) + Math.round(totalThueGTGT);
			
			String[] arr = new String[] {"","","","","","","","", DinhDangTraphaco(inTIEN1),"",DinhDangTraphaco( inTIEN2),
					DinhDangTraphaco(inTIEN3) };
				for (int j = 0; j < arr.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
					
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
					
					if(j==4 ) cells.setPaddingLeft(-0.10f *CONVERT);
					
					if(j==7) cells.setPaddingRight(-0.1f*CONVERT);
					
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingLeft(0.8f * CONVERT);
					cells.setPaddingTop(5.0f);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);
					sanpham.addCell(cells);
				}
				
				
				// Tien bang chu
				doctienrachu doctien = new doctienrachu();
			    String tien = doctien.docTien(Math.round(totalSotienTT - (totalCK + totalCK*vatCK/100)));		   
			  //Viết hoa ký tự đầu tiên			    
			    String TienIN = ""; 
			    
			    if(ingia == 1)		    	
			    	TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			    
			    
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


	private void CreatePxk_CNHOCHIMINH_13P(Document document,ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean)
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
			String nhappId = "";
			double chietkhauDH= 0;
			
			String sql ="";
	
		   
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN " +				   
			        " from NHAPHANPHOI" +
			        " where PK_SEQ = (select npp_fk from ERP_HOADONNPP where pk_seq = '"+ pxkBean.getId() +"') ";
				   

			  
		   System.out.println("Lấy TT CTY "+sql);
		   ResultSet rsINFO = db.get(sql);
		   if(rsINFO.next())
		   {
			   nhappId = rsINFO.getString("PK_SEQ");
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
			String ngayxuatHD= "";
			String chucuahieu = "";
			int ingia = 0;
			int ktra_ngay = 0;
					
		  // LẤY KHO XUẤT
	   sql =" select top 1 (select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = NPP_FK) as kho," +
	   		" 			   (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
		    " 			   (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
	   		"              (select hinhthuctt from ERP_HOADONNPP where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"              (select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK= '"+ pxkBean.getId() +"' ) as DDH," +
	   		"              (select ngayxuathd from ERP_HOADONNPP where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd, " +
	   		"              (select DATEDIFF(DAY,ngaytao, '2015-01-29') from ERP_HOADONNPP where pk_seq = '"+ pxkBean.getId() +"') as ktra_ngay, " +
	   		" 			   ISNULL(( SELECT case when isnull(innguoimua, 1) = 1 and nguoimua is null  then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
	   		"							 when isnull(innguoimua, 1) = 1 and nguoimua is not null  then isnull(nguoimua,'') " +
			"                            else '' end " +
			"                FROM ERP_HOADONNPP " +
			"                WHERE PK_SEQ= '"+ pxkBean.getId() +"'  ),'') AS nguoimua ," +
			"  isnull(chietkhau,0) as chietkhauDH, isnull(isingia,0) ingia "  +
	        " from ERP_DONDATHANGNPP " +
	        " where PK_SEQ in (select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   
		   khId =  rsKho.getString("khachhang_fk")== null ? "":rsKho.getString("khachhang_fk");
		   npp_fk =  rsKho.getString("npp_fk")== null ? "":rsKho.getString("npp_fk");
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   ddh = rsKho.getString("DDH");
		   chucuahieu = rsKho.getString("nguoimua");
		   chietkhauDH = rsKho.getDouble("chietkhauDH");
		   ktra_ngay = rsKho.getInt("ktra_ngay");
		   ingia = rsKho.getInt("ingia");
		   
		   rsKho.close();
	   }
		  
	   if(ingia == 0)
			hinhthucTT = "Không thu tiền";
	   
	   if(khId.trim().length() > 0)
	   {
/*		sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE, "+
			  "	(case when (select isnull(KHO_FK,'0') from KHACHHANG where pk_seq in (select khachhang_fk from ERP_HOADONNPP where pk_seq = '"+ pxkBean.getId() +"')) = 100002 then N'Kho sỉ Nhà Bè'" +
		   	  "        else ( select isnull(XUATTAIKHO,' ') from NHAPHANPHOI where pk_seq = "+ nhappId +") end ) XUATTAIKHO " +
			  " from KHACHHANG " +
			  " where PK_SEQ = "+ khId +" ";*/	
		//LẤY THEO DỮ LIỆU MỚI
		sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE,  " +
			  "	(case when (select isnull(KHO_FK,'0') from KHACHHANG where pk_seq in (select khachhang_fk from ERP_HOADONNPP where pk_seq = '"+ pxkBean.getId() +"')) = 100002 then N'Kho sỉ Nhà Bè'" +
			  "        else ( select isnull(XUATTAIKHO,' ') from NHAPHANPHOI where pk_seq = "+ nhappId +") end ) XUATTAIKHO " +
	  	  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		
	   }else
	   {
		/*sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE , isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
			  " from NHAPHANPHOI " +
			  " where PK_SEQ = "+ npp_fk +" ";*/
		   
		   sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE, ISNULL((SELECT isnull(XUATTAIKHO,' ') as XUATTAIKHO FROM NHAPHANPHOI WHERE PK_SEQ ='"+npp_fk+"'  ),' ') as XUATTAIKHO  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+pxkBean.getId()+"'";
	   }

		System.out.println("Lấy TT KH "+sql);
		ResultSet rsLayKH= db.get(sql);
		if(rsLayKH.next())
		{
		   khoxuat = rsLayKH.getString("XUATTAIKHO");
		   Donvi = rsLayKH.getString("TEN");
		   kh_MST = rsLayKH.getString("MASOTHUE");
		   kh_Diachi = rsLayKH.getString("DIACHI");
		   rsLayKH.close();
		   
		} 

	    NumberFormat formatter = new DecimalFormat("#,###,###.####");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(0.5f*CONVERT, 1.1f*CONVERT, 0.5f*CONVERT, 0.7f*CONVERT); // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open() ;
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);			
	
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(3.6f * CONVERT);
		cell.setPaddingLeft(0.6f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuatHD.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                            " + ngayHD[1] +  "                            " + ngayHD[0].substring(2, ngayHD[0].length()) , new Font(bf, 9, Font.BOLDITALIC));
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
				2.0f*CONVERT, 2.5f*CONVERT, 3.2f*CONVERT, 1.2f*CONVERT, 2.8f*CONVERT, 3.5f*CONVERT };
		sanpham.setWidths(withsKM);
									
		

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			
			String query =
			"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
			"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  \n" +
			"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
			"	case solo when 'NA' then ' ' else b.solo end as solo,  \n" +
			"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, \n" +
			"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
			"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
		    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
		    "   ELSE 0 END as chenhlech, \n"+
			"   ISNULL((select ISNULL(bgsp.CHIETKHAU,0) \n" +
			"           from BANGGIABANDOITAC bg inner join BANGGIABANDOITAC_DOITAC bgdt on bg.PK_SEQ= bgdt.BANGGIABANDOITAC_FK  \n" +
			"						             inner join BANGGIABANDOITAC_SANPHAM bgsp on bgdt.BANGGIABANDOITAC_FK = bgsp.BGBANDOITAC_FK \n" +
			"						              and bgsp.SANPHAM_FK = dhsp.sanpham_fk  \n" +
			"           where bg.KENH_FK = dh.KBH_FK and bgdt.NPP_FK = dh.NPP_FK ),0) as CHIETKHAU_BG \n" +
			"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 \n" +
			"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ				\n" +					       									
			"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 \n" +
			"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 \n" +
			"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  					\n" +
			"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 \n" +
			"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";

			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			double thanhtien = 0;
			double thueGTGT = 0;
			
			while(rsSP.next())
			{
				double chenhlech = rsSP.getDouble("chenhlech");
				
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");				
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				}
				else{
					thanhtien = soLUONG * dongia - chietkhau;					
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				}
				
				// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
				if(chenhlech != 0 && khId.trim().length() > 0)
				{
				   thueGTGT = thueGTGT - chenhlech;
				}
				
				double sotientt = thanhtien + thueGTGT;
						
				// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
				if(npp_fk.trim().length() > 0)
				{
					// TỪ NGÀY 29/01/2015 : ĐƠN GIÁ ĐÃ TRỪ CK >> KHÔNG CẦN TÍNH LẠI NỮA
					if(ktra_ngay > 0)
						dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
					
					chietkhau = 0;
					thanhtien = Math.round(soLUONG * dongia);
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
				}
				
					totalThueGTGT +=thueGTGT;
					totalTienTruocVAT+=thanhtien;
					totalSotienTT +=sotientt;

				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().contains("-") )
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String inDONGIA = "";
				String inTHANHTIEN = "";
				String inTHUEVAT = "";
				String inTHUEGTGT = "";
				String inSOTIENTT = "";
				
				if(ingia == 1)
				{
					
					if(dongia <= 0) inDONGIA = "";
					else  inDONGIA = formatter.format(dongia);
					
					if(thanhtien <=0 ) inTHANHTIEN = "";
					else  inTHANHTIEN = formatter1.format(thanhtien);
					
					if( rsSP.getDouble("thuevat") <=0 ) inTHUEVAT = "";
					else  inTHUEVAT = formatter1.format(rsSP.getDouble("thuevat"));
					
					if(thueGTGT <= 0) inTHUEGTGT = "";
					else  inTHUEGTGT = formatter1.format(thueGTGT);
					
					if(sotientt <= 0) inSOTIENTT = "";
					else  inSOTIENTT = formatter1.format(sotientt) ;
				
				}
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTraphaco( formatter1.format(soLUONG) ), DinhDangTraphaco(inDONGIA), DinhDangTraphaco( inTHANHTIEN ), 
						DinhDangTraphaco( inTHUEVAT ), DinhDangTraphaco( inTHUEGTGT ), 
						DinhDangTraphaco( inSOTIENTT ) };


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
						cells.setPaddingRight(0.3f*CONVERT);	
					}
					if(j==9)
					{
						cells.setPaddingRight(0.7f*CONVERT);	
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
			
			stt = stt -1 ;
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
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					sanpham.addCell(cells);
				}
				
				kk++;
			}
			
			String inTIEN1 ="";
			String inTIEN2 ="";
			String inTIEN3 ="";
			
			if(ingia == 1)
			{
				if(totalTienTruocVAT - totalTienCK_ChuaVat <=0) inTIEN1 = "";
				else 
					inTIEN1 = formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat);
				
				if(totalThueGTGT - totalVatCK  <=0) inTIEN2 = "";
				else 
					inTIEN2 = formatter1.format(totalThueGTGT - totalVatCK);
				
				if(Math.round(totalSotienTT-totalTienCK) <= 0 ) inTIEN3 = "";
				else 
					inTIEN3 = formatter1.format(Math.round(totalSotienTT-totalTienCK));
			}
			
		// Tong tien thanh toan	
		String[] arr = new String[] {"","", "", "", "", "", "", "", DinhDangTraphaco( inTIEN1 ), "", 
				DinhDangTraphaco( inTIEN2), 
				DinhDangTraphaco( inTIEN3) };
			
		for (int j = 0; j < arr.length; j++)
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
					cells.setPaddingRight(0.3f*CONVERT);	
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
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
		    String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));		   
		  //Viết hoa ký tự đầu tiên		    
		    String TienIN = ""; 
		    
		    if(ingia == 1)		    	
		    	TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
		    
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
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}


	private void CreatePxk_HAIPHONG_OLD(Document document,ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean) 
	{

		try
		{
						
			dbutils db = new dbutils();
				
					//LAY THONG TIN NCC
					String kbh="";
					String ddh="";
					String npp_fk="";
					String khId="";
					
					String ctyTen="";
					String cty_MST ="";
					String cty_Diachi="";
					String cty_Sotaikhoan= "";
					String cty_Dienthoai= "";
					String cty_Fax= "";
					String khoxuat ="";
					String hinhthucTT= "";
					String ngayxuathd ="";
					String nguoimuahang="";
					
					String sql =
					"SELECT  A.PK_SEQ, A.KBH_FK ,(select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = A.NPP_FK) AS KHO," +
					" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
					" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
					" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS HTTT," +
					" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ngayxuathd," +
					" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
					"               else isnull(nguoimua,'') end " +
					"   FROM ERP_HOADONNPP" +
					"   WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
				    "FROM ERP_DONDATHANGNPP A " +
				    "WHERE A.PK_SEQ IN  " +
				    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + pxkBean.getId() + "' )";
		
					System.out.println("[INIT_DONHANG]"+sql);
					
					ResultSet rsCheck = db.get(sql);					
										
					if(rsCheck.next())
					{
						npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
						khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
						ddh = rsCheck.getString("PK_SEQ");
						nguoimuahang = rsCheck.getString("nguoimua");
						kbh = rsCheck.getString("KBH_FK");
						khoxuat = rsCheck.getString("KHO");
						hinhthucTT = rsCheck.getString("HTTT");
						ngayxuathd = rsCheck.getString("ngayxuathd");
						rsCheck.close();
					}
			
				
			
			   
			 //LAY THONG TIN KHACHHANG
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
				
				
		if(khId.length() > 0){
/*			  sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
			//LẤY THEO DỮ LIỆU MỚI
				sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}else{
			/*sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			 sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}
			   
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
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
			pxk = new Paragraph(nguoimuahang, new Font(bf, 10, Font.BOLD));
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
			double totalCK = 0;
			double vatCK = 0;
			

			
			/*String query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
							"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
							"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
							"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
							"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, dhsp.dongia  	 " +
							"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
							"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
							"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
							"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
							"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
							"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
							"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";*/
			
			String query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
							"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
							"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
							"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
							"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, " +
							"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
							"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
							"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
							"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
							"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
							"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
							"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
							"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			
			
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double vat = rsSP.getDouble("thuevat");
				double thanhtien = (soLUONG*dongia);	
				double thueGTGT = thanhtien*vat/100;
				double sotientt = thanhtien ;
						
				vatCK = rsSP.getDouble("thuevat");
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
				totalCK += rsSP.getDouble("chietkhau");
				
				
				String chuoi ="";
				
				if(rsSP.getString("ngayhethan").trim().length() > 0 && !rsSP.getString("ngayhethan").equals("null") )
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") + " - " + rsSP.getString("TEN"), rsSP.getString("solo"),chuoi, rsSP.getString("DONVI"),
						formatter1.format(soLUONG), formatter.format(dongia) , formatter.format(thanhtien), formatter.format(vat), formatter.format(thueGTGT), formatter.format(sotientt+ thueGTGT) };

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
		
		if(totalCK > 0)
		{
			stt = stt -1 ;
			//CHIẾT KHẤU DÒNG HÀNG (NẾU CÓ) : SUM(CHIETKHAU) CỦA TẤT CẢ CÁC SP
			String [] arr5 = new String[] {Integer.toString(stt+1),"CK" ,"Chiết khấu sản phẩm " ," ","","", formatter1.format(totalCK),formatter1.format(vatCK),
					formatter1.format(totalCK*vatCK/100) ,formatter1.format( totalCK + totalCK*vatCK/100)};
			for (int j = 0; j < arr5.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
				if(j==1)
				{
					cells.setColspan(2);
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else 
				{
					if(j == 2)
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					else if(j == 0)
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					else
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				

				cells.setFixedHeight(0.6f*CONVERT);
				cells.setPaddingTop(2.5f);
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setBorder(0);

				if(j==0 || j==1 )
				{
					cells.setPaddingLeft(-0.3f *CONVERT);
				}
				if(j==2 )
				{
					cells.setPaddingLeft(-0.7f *CONVERT);
				}
				
				
				if (j == 0 || j == 3 ){
					cells.setHorizontalAlignment(Element.ALIGN_CENTER );
				}
				else
				{
					if(j <= 3 )
					{
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
				}
				

				sanpham.addCell(cells);
			}
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
			String[] arr = new String[] {"                             ", formatter.format(totalTienTruocVAT - totalCK  ),"", formatter.format(totalThueGTGT - (totalCK*vatCK/100)),formatter.format(Math.round(totalSotienTT + totalThueGTGT - (totalCK + totalCK*vatCK/100))) };
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
			    String tien = doctien.docTien(Math.round(totalSotienTT + totalThueGTGT -(totalCK + totalCK*vatCK/100)));		   
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


	private void CreatePxk_HADONG(Document document,ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean) 
	{

		try
		{
						
			dbutils db = new dbutils();
				
					//LAY THONG TIN NCC
					String kbh="";
					String ddh="";
					String npp_fk="";
					String khId="";
					
					String ctyTen="";
					String cty_MST ="";
					String cty_Diachi="";
					String cty_Sotaikhoan= "";
					String cty_Dienthoai= "";
					String cty_Fax= "";
					String khoxuat ="";
					String hinhthucTT= "";
					String ngayxuathd ="";
					String nguoimuahang="";
					double chietkhauDH = 0;
					
					String sql =
					"SELECT  A.PK_SEQ, A.KBH_FK ,(select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = A.NPP_FK) AS KHO," +
					" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
					" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
					" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS HTTT," +
					" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ngayxuathd," +
					" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
					"               else isnull(nguoimua,'') end " +
					"   FROM ERP_HOADONNPP" +
					"   WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua, isnull(chietkhau,0) as chietkhauDH "  +
				    "FROM ERP_DONDATHANGNPP A " +
				    "WHERE A.PK_SEQ IN  " +
				    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + pxkBean.getId() + "' )";
		
					System.out.println("[INIT_DONHANG]"+sql);
					
					ResultSet rsCheck = db.get(sql);					
										
					if(rsCheck.next())
					{
						npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
						khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
						ddh = rsCheck.getString("PK_SEQ");
						nguoimuahang = rsCheck.getString("nguoimua");
						kbh = rsCheck.getString("KBH_FK");
						khoxuat = rsCheck.getString("KHO");
						hinhthucTT = rsCheck.getString("HTTT");
						ngayxuathd = rsCheck.getString("ngayxuathd");
						chietkhauDH = rsCheck.getDouble("chietkhauDH");
						rsCheck.close();
					}
			
			
			   
			 //LAY THONG TIN KHACHHANG
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
				
				
		if(khId.length() > 0){
			/*  sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
			  
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}else{
		/*	sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}
			   
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
		   }   
		   
		    NumberFormat formatter = new DecimalFormat("#,###,###.####");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(2.0f*CONVERT, 1.5f*CONVERT, 2.0f*CONVERT, 2.0f*CONVERT);
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(1.2f * CONVERT);
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
			
			//
			PdfPCell cell88 = new PdfPCell();
			cell88.setPaddingTop(-0.22f * CONVERT);
			cell88.setPaddingLeft(2.7f * CONVERT);
			pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(4);
			cell88.addElement(pxk);
			cell88.setBorder(0);						
			table1.addCell(cell88);	
			
			PdfPCell cell88a = new PdfPCell();
			cell88a.setPaddingTop(-0.22f * CONVERT);
			cell88a.setPaddingLeft(4.5f * CONVERT);
			pxk = new Paragraph(nguoimuahang, new Font(bf, 11, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell88a.addElement(pxk);
			cell88a.setBorder(0);						
			table1.addCell(cell88a);
			//
			
			PdfPCell cell8 = new PdfPCell();	
			cell8.setPaddingLeft(2.7f * CONVERT);
			cell8.setPaddingTop(-0.32f * CONVERT);
			pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(4);
			cell8.addElement(pxk);
			cell8.setBorder(0);						
			table1.addCell(cell8);	
			
			PdfPCell cell8a = new PdfPCell();
			cell8a.setPaddingLeft(3.73f * CONVERT);
			cell8a.setPaddingTop(-0.32f * CONVERT);
			pxk = new Paragraph(Donvi, new Font(bf, 11, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell8a.addElement(pxk);
			cell8a.setBorder(0);						
			table1.addCell(cell8a);

			
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
			
			if(kh_MST.trim().length() < 10)
			{
				kh_MST = kh_MST+"                                                            ";
			}
			if(kh_MST.trim().length() >= 10)
			{
				kh_MST = kh_MST+"                                                         ";
			}
			
			if(kh_MST.trim().length() <= 0)
			{
				kh_MST = "                                                                           ";
			}
			
			
			PdfPCell cell18 = new PdfPCell();		
			cell18.setPaddingLeft(5.0f * CONVERT);
			cell18.setPaddingTop(2.5f);
			pxk = new Paragraph( kh_MST + " " +hinhthucTT, new Font(bf, 10, Font.NORMAL));
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
			double totalCK = 0;
			double vatCK= 0;
			
			String query = "";
			if(SoNgay(ngayxuathd)){
				query = 
					"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
					"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  \n" +
					"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
					"	case solo when 'NA' then ' ' else b.solo end as solo,  \n" +
					"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, \n" +
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
					"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
				    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
				    "   ELSE 0 END as chenhlech, \n"+
					"   ISNULL((select ISNULL(bgsp.CHIETKHAU,0) \n" +
					"           from BANGGIABANDOITAC bg inner join BANGGIABANDOITAC_DOITAC bgdt on bg.PK_SEQ= bgdt.BANGGIABANDOITAC_FK  \n" +
					"						             inner join BANGGIABANDOITAC_SANPHAM bgsp on bgdt.BANGGIABANDOITAC_FK = bgsp.BGBANDOITAC_FK \n" +
					"						              and bgsp.SANPHAM_FK = dhsp.sanpham_fk  \n" +
					"           where bg.KENH_FK = dh.KBH_FK and bgdt.NPP_FK = dh.NPP_FK ),0) as CHIETKHAU_BG \n" +
					"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 \n" +
					"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ				\n" +					       									
					"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 \n" +
					"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 \n" +
					"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  					\n" +
					"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 \n" +
					"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			else{
				query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
						"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
						"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
						"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
						"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, " +
						"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
						"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
						"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
						"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
						"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
						"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
						"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
						"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			
			System.out.println("[ERP_DONDATHANG_SANPHAM2]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt = 0;
			double chenhlech = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");				
				double vat = rsSP.getDouble("thuevat");
				
				if(SoNgay(ngayxuathd)){
					chenhlech = rsSP.getDouble("chenhlech");
					thanhtien = Math.round(soLUONG * dongia - chietkhau);
					thueGTGT = Math.round(thanhtien*vat/100);
					
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 && khId.trim().length() > 0)
					{
					   thueGTGT = thueGTGT - chenhlech;
					}
										
					sotientt = thanhtien + thueGTGT;
					
					// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
					if(npp_fk.trim().length() > 0)
					{
						dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
						chietkhau = 0;
						thanhtien = Math.round(soLUONG * dongia);
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + thueGTGT;
					}
					
				}
				else{
					thanhtien = Math.round((soLUONG*dongia));	
					thueGTGT = Math.round(thanhtien*vat/100);
					sotientt = thanhtien ;
				}
						
				vatCK = rsSP.getDouble("thuevat");
				
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				totalCK += chietkhau;
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0 && !rsSP.getString("ngayhethan").equals("null"))
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr;
				if(SoNgay(ngayxuathd)){
					arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("solo"),chuoi, rsSP.getString("DONVI"),
							DinhDangTraphaco(formatter1.format(soLUONG)), DinhDangTraphaco(formatter.format(dongia)) , DinhDangTraphaco(formatter1.format(thanhtien)), DinhDangTraphaco(formatter1.format(vat)), DinhDangTraphaco(formatter1.format(thueGTGT)), DinhDangTraphaco(formatter1.format(sotientt)) };
				}
				else{
					arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("solo"),chuoi, rsSP.getString("DONVI"),
						DinhDangTraphaco(formatter1.format(soLUONG)), DinhDangTraphaco(formatter.format(dongia)) , DinhDangTraphaco(formatter1.format(thanhtien)), DinhDangTraphaco(formatter1.format(vat)), DinhDangTraphaco(formatter1.format(thueGTGT)), DinhDangTraphaco(formatter1.format(sotientt+ thueGTGT)) };
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
			
			
			if(totalCK > 0)
			{
				stt= stt-1;
				//CHIẾT KHẤU DÒNG HÀNG (NẾU CÓ) : SUM(CHIETKHAU) CỦA TẤT CẢ CÁC SP
				String [] arr5 = new String[] {Integer.toString(stt+1),"CK" ,"Chiết khấu sản phẩm " ," ","","", DinhDangTraphaco(formatter1.format(totalCK)),DinhDangTraphaco(formatter1.format(vatCK)),
						DinhDangTraphaco(formatter1.format(totalCK*vatCK/100)) ,DinhDangTraphaco(formatter1.format( totalCK + totalCK*vatCK/100))};
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
					if(j==2)
					{
						cells.setColspan(3);
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else 
					{
						if(j <= 1)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					
	
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setPaddingTop(2.0f);
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setPaddingLeft(-9.0f);
					
	
					sanpham.addCell(cells);
				}
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
			String[] arr = new String[] {"                             ", DinhDangTraphaco(formatter1.format(totalTienTruocVAT - totalCK)),"",DinhDangTraphaco(formatter1.format(totalThueGTGT - (totalCK*vatCK/100) )),DinhDangTraphaco(formatter1.format(totalTienTruocVAT + totalThueGTGT - totalCK  - (totalCK*vatCK/100) )) };
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
			    String tien = doctien.docTien(Math.round(totalTienTruocVAT + totalThueGTGT - totalCK  - (totalCK*vatCK/100) ));		   
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
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
		
	}

	
	private void CreatePxk_QUANGNINH(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean) throws IOException
	{
		try
		{			
			dbutils db = new dbutils();
				
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			String hinhthucTT= "";
			String ngayxuatHD ="";
			String chucuahieu="";
			double chietkhauDH = 0;
			
			String sql =
			"SELECT  A.PK_SEQ, A.KBH_FK ,(select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = A.NPP_FK) AS KHO," +
			" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
			" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
			" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS HTTT," +
			" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ngayxuathd," +
			" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"               else isnull(nguoimua,'') end " +
			"   FROM ERP_HOADONNPP" +
			"   WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua, isnull(chietkhau,0) as chietkhauDH "  +
		    "FROM ERP_DONDATHANGNPP A " +
		    "WHERE A.PK_SEQ IN  " +
		    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + pxkBean.getId() + "' )";

			System.out.println("[INIT_DONHANG]"+sql);
			
			ResultSet rsCheck = db.get(sql);					
								
			if(rsCheck.next())
			{
				npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
				khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
				ddh = rsCheck.getString("PK_SEQ");
				chucuahieu = rsCheck.getString("nguoimua");
				kbh = rsCheck.getString("KBH_FK");
				khoxuat = rsCheck.getString("KHO");
				hinhthucTT = rsCheck.getString("HTTT");
				ngayxuatHD = rsCheck.getString("ngayxuathd");
				chietkhauDH = chietkhauDH = rsCheck.getDouble("chietkhauDH");
				
				rsCheck.close();
			}
	
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from ERP_HOADONNPP where pk_seq = '"+ pxkBean.getId() +"') ";
		   
		   System.out.println("Lấy TT CTY HP "+sql);
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
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
				
				
		if(khId.length() > 0){
			 /* sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}else{
			/*sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			 sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}
			   
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
		   } 
		   
	    NumberFormat formatter = new DecimalFormat("#,###,###.####");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(1.3f * CONVERT, 0.65f * CONVERT, 1.2f * CONVERT, 1.0f * CONVERT); // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open() ;
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(1.17f * CONVERT);
		cell.setPaddingLeft(2.0f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuatHD.split("-");
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
		cell_nguoimua.setPaddingTop(-0.2f * CONVERT);
		cell_nguoimua.addElement(pxk);
		cell_nguoimua.setBorder(0);						
		table1.addCell(cell_nguoimua);	
		
		PdfPCell cell_nguoimua1 = new PdfPCell();
		cell_nguoimua1.setPaddingTop(-0.2f * CONVERT);
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
		cell14.setPaddingLeft(1.5f * CONVERT);
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
		cell14a.setPaddingLeft(1.5f * CONVERT);
		pxk = new Paragraph(chuoi2, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);		
		cell14a.addElement(pxk);
		cell14a.setBorder(0);						
		table1.addCell(cell14a);	
		
		
		// DONG 5 ----KHO XUAT
		PdfPCell cell17 = new PdfPCell();	
		cell17.setPaddingLeft(3.8f * CONVERT);
		cell17.setPaddingTop(-0.15f * CONVERT);
		pxk = new Paragraph(khoxuat, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell17.addElement(pxk);
		cell17.setBorder(0);						
		table1.addCell(cell17);	
		

		if(kh_MST.trim().length() > 0 && kh_MST.trim().length() <= 10)		
		{
			kh_MST = "                                             "+ kh_MST+ "                           ";
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
		cell18.setPaddingRight(0.2f * CONVERT);
		cell18.setPaddingTop(-0.15f * CONVERT);
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
		
		float[] withsKM = { 7.0f, 15.0f, 60f, 13f, 13f, 18.0f, 15.0f, 24.0f, 24.0f, 12.0f, 23f, 27f };
		sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query = "";
			
			if(SoNgay(ngayxuatHD)){
				query = 
					"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
					"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  \n" +
					"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
					"	case solo when 'NA' then ' ' else b.solo end as solo,  \n" +
					"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, \n" +
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
					"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
				    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
				    "   ELSE 0 END as chenhlech, \n"+
					"   ISNULL((select ISNULL(bgsp.CHIETKHAU,0) \n" +
					"           from BANGGIABANDOITAC bg inner join BANGGIABANDOITAC_DOITAC bgdt on bg.PK_SEQ= bgdt.BANGGIABANDOITAC_FK  \n" +
					"						             inner join BANGGIABANDOITAC_SANPHAM bgsp on bgdt.BANGGIABANDOITAC_FK = bgsp.BGBANDOITAC_FK \n" +
					"						              and bgsp.SANPHAM_FK = dhsp.sanpham_fk  \n" +
					"           where bg.KENH_FK = dh.KBH_FK and bgdt.NPP_FK = dh.NPP_FK ),0) as CHIETKHAU_BG \n" +
					"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 \n" +
					"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ				\n" +					       									
					"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 \n" +
					"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 \n" +
					"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  					\n" +
					"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 \n" +
					"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			else{
					query = "select MA, TEN, donvi, '' as solo, '' as ngayhethan, sum(soluong) as soluong, sum(chietkhau) as chietkhau, avg(thuevat) as thuevat, avg(dongia) as dongia, sum(tienvat)  as tienvat " +
							"from  " +
							"( " +
							"	select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,   " +
							"		case when d.pk_seq = dhsp.dvdl_fk then b.soluong   " +
							"				else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,   " +
							"		case solo when 'NA' then ' ' else b.solo end as solo,   " +
							"		case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat,  " +
							"        ( select isnull(tienvat,0) from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk )  as tienvat, "+
							"		( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	  " +
							"	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	  " +
							"		 inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									  " +
							"		 inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									  " +
							"		 inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	  " +
							"		 inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						  " +
							"		 inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	  " +
							"	where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3'  " +
							") " +
							"HD group by MA, TEN, donvi ";
			}
			System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double vatCK=0;
			double totalCK=0;
			double sotientt = 0;
			double chenhlech = 0;
			double tienvat = 0;
			double soLUONG = 0;
			double chietkhau = 0;
			double dongia = 0;
			double thueGTGT = 0 ;
			double thanhtien = 0;
			
			while(rsSP.next())
			{				
				soLUONG = rsSP.getDouble("soluong");
				chietkhau = rsSP.getDouble("chietkhau");
				dongia = rsSP.getDouble("dongia");
				double vat = rsSP.getDouble("thuevat");
				
				if(SoNgay(ngayxuatHD)){
					chenhlech = rsSP.getDouble("chenhlech");
					thanhtien = Math.round(soLUONG * dongia - chietkhau);
					thueGTGT = Math.round(thanhtien*vat/100);
					
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 && khId.trim().length() > 0)
					{
					   thueGTGT = thueGTGT - chenhlech;
					}
					
					sotientt = thanhtien + thueGTGT;
					
					// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
					if(npp_fk.trim().length() > 0)
					{
						dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
						chietkhau = 0;
						thanhtien = Math.round(soLUONG * dongia);
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + thueGTGT;
					}
				}
				else{
					tienvat = rsSP.getDouble("tienvat");
					thanhtien = Math.round(soLUONG*dongia);
					
					/*double sotientt = thanhtien + (thanhtien*vat/100) ;*/
					
					if(tienvat > 0) // Người dùng sửa tiền vat >> Lấy tiền vat này
					{
						thueGTGT = tienvat;
						sotientt = thanhtien + tienvat ;
					}else
					{
						thueGTGT = Math.round(thanhtien *vat/100);
						sotientt = thanhtien + (thanhtien*vat/100);
					}
					
									
					 vatCK = rsSP.getDouble("thuevat");
				}
					
				if(ddh.equals("100526")) // Tam thoi sua Vat cho Nhà QN 
				{
					thueGTGT = 571428 ;
					sotientt = thanhtien + thueGTGT;
					totalThueGTGT += thueGTGT ;
					totalTienTruocVAT+= (soLUONG*dongia);
					totalCK += chietkhau;
					
				}
				else
				{
					if(SoNgay(ngayxuatHD)){
						totalThueGTGT +=thueGTGT;
						totalTienTruocVAT+=thanhtien;
						totalSotienTT +=sotientt;
						
					}
					else{
						totalThueGTGT += (soLUONG*dongia)*vat/100;
						totalTienTruocVAT+= (soLUONG*dongia);
						totalCK += chietkhau;
					}
				}
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0 && !rsSP.getString("ngayhethan").equals("null"))
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				thanhtien = Math.round(thanhtien);
				thueGTGT = Math.round(thueGTGT);
				sotientt = Math.round(sotientt);
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("SoLo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTraphaco(formatter1.format(soLUONG)), DinhDangTraphaco(formatter.format(dongia)),DinhDangTraphaco(formatter1.format(thanhtien)),DinhDangTraphaco(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTraphaco(formatter1.format(thueGTGT)),DinhDangTraphaco(formatter1.format(sotientt)) };


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
					
					if(j== 0)
					    cells.setPaddingLeft(0.2f*CONVERT);
					if(j== 1)
						cells.setPaddingLeft(0.4f*CONVERT);
					if(j== 2)
						cells.setPaddingLeft(0.5f*CONVERT);
					if(j== 3)
						cells.setPaddingLeft(-0.4f*CONVERT);
					if(j== 4)
						cells.setPaddingLeft(-0.4f*CONVERT);
					/*if(j==5)
						cells.setPaddingRight(0.4f *CONVERT);*/
					if(j== 6)
						cells.setPaddingRight(0.2f *CONVERT);
					if(j== 7)
						cells.setPaddingRight(0.4f *CONVERT);
					if(j== 8)
						cells.setPaddingRight(0.7f *CONVERT);
					if(j== 9)
						cells.setPaddingRight(1.2f *CONVERT);
					if(j== 10)
						cells.setPaddingRight(0.7f *CONVERT);
					if(j== 11)
						cells.setPaddingRight(0.5f *CONVERT);
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			stt= stt-1;
		
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
								
					if(j== 0)
					    cells.setPaddingLeft(0.2f*CONVERT);
					if(j== 1)
						cells.setPaddingLeft(0.4f*CONVERT);
					if(j== 2)
						cells.setPaddingLeft(0.5f*CONVERT);
					if(j== 3)
						cells.setPaddingLeft(-0.4f*CONVERT);
					if(j== 4)
						cells.setPaddingLeft(-0.4f*CONVERT);
					/*if(j==5)
						cells.setPaddingRight(0.4f *CONVERT);*/
					if(j== 6)
						cells.setPaddingRight(0.2f *CONVERT);
					if(j== 7)
						cells.setPaddingRight(0.4f *CONVERT);
					if(j== 8)
						cells.setPaddingRight(0.7f *CONVERT);
					if(j== 9)
						cells.setPaddingRight(1.2f *CONVERT);
					if(j== 10)
						cells.setPaddingRight(0.7f *CONVERT);
					if(j== 11)
						cells.setPaddingRight(0.5f *CONVERT);
					
					sanpham.addCell(cells);
				}
				
				kk++;
			}
				totalSotienTT = Math.round(totalTienTruocVAT) + Math.round(totalThueGTGT);
			
			String[] arr = new String[] { " ", " " , " ", " "," ", " "," "," ", DinhDangTraphaco(formatter1.format(totalTienTruocVAT -totalCK )),"",DinhDangTraphaco( formatter1.format(totalThueGTGT- (totalCK*vatCK/100) )),DinhDangTraphaco(formatter1.format(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100) ))) };
			
		
	
		for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
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
				
				if(j== 0)
				    cells.setPaddingLeft(0.2f*CONVERT);
				if(j== 1)
					cells.setPaddingLeft(0.4f*CONVERT);
				if(j== 2)
					cells.setPaddingLeft(0.5f*CONVERT);
				if(j== 3)
					cells.setPaddingLeft(-0.4f*CONVERT);
				if(j== 4)
					cells.setPaddingLeft(-0.4f*CONVERT);
				/*if(j==5)
					cells.setPaddingRight(0.4f *CONVERT);*/
				if(j== 6)
					cells.setPaddingRight(0.2f *CONVERT);
				if(j== 7)
					cells.setPaddingRight(0.4f *CONVERT);
				if(j== 8)
					cells.setPaddingRight(0.7f *CONVERT);
				if(j== 9)
					cells.setPaddingRight(1.2f *CONVERT);
				if(j== 10)
					cells.setPaddingRight(0.7f *CONVERT);
				if(j== 11)
					cells.setPaddingRight(0.5f *CONVERT);
				
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setPaddingTop(-0.3f * CONVERT);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
		    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
			   String tien = doctien.docTien(Math.round(totalSotienTT - (totalCK + totalCK*vatCK/100)));
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
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(-0.3f * CONVERT);
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
	
	private void CreatePxk_DANANG(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean) throws IOException
	{
		try
		{			
			dbutils db = new dbutils();
				
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			String hinhthucTT= "";
			String ngayxuatHD ="";
			String chucuahieu="";
			double chietkhauDH = 0;
			int ktra_ngay = 0;
			int ingia =0;
			
			
			String sql =
			"SELECT  A.PK_SEQ, A.KBH_FK ,(select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = A.NPP_FK) AS KHO," +
			" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
			" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
			" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS HTTT," +
			" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ngayxuathd," +
			" (SELECT DATEDIFF(DAY, ngaytao, '2015-01-29') FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ktra_ngay," +
			" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"               else isnull(nguoimua,'') end " +
			"   FROM ERP_HOADONNPP" +
			"   WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua," +
			" ISNULL(CHIETKHAU,0) as CHIETKHAUDH, isnull(isingia, 0) as ingia "  +
		    "FROM ERP_DONDATHANGNPP A " +
		    "WHERE A.PK_SEQ IN  " +
		    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + pxkBean.getId() + "' )";

			System.out.println("[INIT_DONHANG]"+sql);
			
			ResultSet rsCheck = db.get(sql);					
								
			if(rsCheck.next())
			{
				npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
				khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
				ddh = rsCheck.getString("PK_SEQ");
				chucuahieu = rsCheck.getString("nguoimua");
				kbh = rsCheck.getString("KBH_FK");
				khoxuat = rsCheck.getString("KHO");
				hinhthucTT = rsCheck.getString("HTTT");
				ngayxuatHD = rsCheck.getString("ngayxuathd");
				chietkhauDH = rsCheck.getDouble("chietkhauDH");
				ktra_ngay = rsCheck.getInt("ktra_ngay");
				ingia = rsCheck.getInt("ingia");
				
				rsCheck.close();
			}
	
			if(ingia == 0)
				hinhthucTT = "Không thu tiền";
			
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        	" from NHAPHANPHOI" +
		        	" where PK_SEQ = (select npp_fk from ERP_HOADONNPP where pk_seq = '"+ pxkBean.getId() +"') ";
		   
		   System.out.println("Lấy TT CTY HP "+sql);
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
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
				
				
		if(khId.length() > 0){
			/*  sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
			
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}else{
			/*sql = " select  TEN ,DIACHI, MASOTHUE  "+
				  " from NHAPHANPHOI " +
				  " where PK_SEQ = '"+ npp_fk +"'  ";*/
			 sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}
			   
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
		   } 
		   
	    NumberFormat formatter = new DecimalFormat("#,###,###.####");
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
		
		String [] ngayHD = ngayxuatHD.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                        " + ngayHD[0] , new Font(bf, 9, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(pxk);

		tableheader.addCell(cell);
		document.add(tableheader);
		
		// Thông tin Khach Hang
		PdfPTable table1 = new PdfPTable(2);
		table1.setWidthPercentage(100);
		table1.getDefaultCell().setBorder(0);
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

		String chuoi_= "";
		String chuoi1= kh_Diachi;
		String chuoi2= "";
		int vitri= 0;
		int dodaichuoi = kh_Diachi.length();
		if(dodaichuoi >= 60)
		{
			chuoi_ = kh_Diachi.substring(0, 60);
			vitri = chuoi_.lastIndexOf(" ");
			chuoi1 = chuoi_.substring(0, vitri);
			chuoi2 = kh_Diachi.substring(vitri + 1,dodaichuoi );
		}
		
		
		cell17 = new PdfPCell();
		cell17.setPaddingTop(-0.1f * CONVERT);
		cell17.setPaddingLeft(2.5f * CONVERT);
		pxk = new Paragraph(chuoi1, new Font(bf, 11, Font.NORMAL));
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
		
		
		// BẢNG 2 cột: Cột 1: HTTT, cột 2: phần dư của Địa chỉ (nếu dài quá)
		PdfPTable tableLDD = new PdfPTable(2);
		tableLDD.setWidthPercentage(100);
		tableLDD.getDefaultCell().setBorder(0);
		float[] withs =  { 8.0f*CONVERT, 5.0f*CONVERT};
		tableLDD.setWidths(withs);
		
		PdfPCell cell18a = new PdfPCell();
		cell18a.setPaddingTop(-0.15f * CONVERT);
		cell18a.setPaddingLeft(4.4f * CONVERT);
		pxk = new Paragraph(hinhthucTT, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(0);
		cell18a.addElement(pxk);
		cell18a.setBorder(0);	
		tableLDD.addCell(cell18a);
		
		PdfPCell cell18b = new PdfPCell();
		cell18b.setPaddingRight(0.2f * CONVERT);
		cell18b.setPaddingTop(-0.15f * CONVERT);
		pxk = new Paragraph(chuoi2, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_RIGHT);
		pxk.setSpacingAfter(0);
		cell18b.addElement(pxk);
		cell18b.setBorder(0);		
		tableLDD.addCell(cell18b);
				
		table1.addCell(tableLDD);   
		
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
			
			String query = "";
			if(SoNgay(ngayxuatHD)){
				query = 
					"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
					"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  \n" +
					"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
					"	case solo when 'NA' then ' ' else b.solo end as solo,  \n" +
					"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, \n" +
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
					"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
				    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
				    "   ELSE 0 END as chenhlech, \n"+
					"   ISNULL((select ISNULL(bgsp.CHIETKHAU,0) \n" +
					"           from BANGGIABANDOITAC bg inner join BANGGIABANDOITAC_DOITAC bgdt on bg.PK_SEQ= bgdt.BANGGIABANDOITAC_FK  \n" +
					"						             inner join BANGGIABANDOITAC_SANPHAM bgsp on bgdt.BANGGIABANDOITAC_FK = bgsp.BGBANDOITAC_FK \n" +
					"						              and bgsp.SANPHAM_FK = dhsp.sanpham_fk  \n" +
					"           where bg.KENH_FK = dh.KBH_FK and bgdt.NPP_FK = dh.NPP_FK ),0) as CHIETKHAU_BG \n" +
					"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 \n" +
					"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ				\n" +					       									
					"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 \n" +
					"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 \n" +
					"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  					\n" +
					"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 \n" +
					"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			else{
				query =	"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
						"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  \n" +
						"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
						"	case solo when 'NA' then ' ' else b.solo end as solo,  \n" +
						"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, \n" +
						"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	 \n" +
						"   ISNULL((select ISNULL(bgsp.CHIETKHAU,0) \n" +
						"           from BANGGIABANDOITAC bg inner join BANGGIABANDOITAC_DOITAC bgdt on bg.PK_SEQ= bgdt.BANGGIABANDOITAC_FK  \n" +
						"						             inner join BANGGIABANDOITAC_SANPHAM bgsp on bgdt.BANGGIABANDOITAC_FK = bgsp.BGBANDOITAC_FK \n" +
						"						              and bgsp.SANPHAM_FK = dhsp.sanpham_fk  \n" +
						"           where bg.KENH_FK = dh.KBH_FK and bgdt.NPP_FK = dh.NPP_FK ),0) as CHIETKHAU_BG \n" +
						"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 \n" +
						"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 \n" +
						"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 \n" +
						"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 \n" +
						"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  		\n" +				
						"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 \n" +
						"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			
			
			System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double vatCK=0;
			double totalCK=0;
			double chenhlech = 0;
			double vat = 0;
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt = 0;
			
			
			while(rsSP.next())
			{

				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				vat = rsSP.getDouble("thuevat");
				
				if(SoNgay(ngayxuatHD)){
					chenhlech = rsSP.getDouble("chenhlech");
					thanhtien = Math.round(soLUONG * dongia - chietkhau);
					thueGTGT = Math.round(thanhtien*vat/100);
					
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 && khId.trim().length() > 0)
					{
					   thueGTGT = thueGTGT - chenhlech;
					}
					
					sotientt = thanhtien + thueGTGT;
					
					// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
					if(npp_fk.trim().length() > 0)
					{
						// TỪ NGÀY 29/01/2015 >> ĐƠN GIÁ ĐÃ TRỪ LUÔN TIỀN CK
						if(ktra_ngay > 0)
							dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
						
						chietkhau = 0;
						thanhtien = Math.round(soLUONG * dongia);
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + thueGTGT;
					}
				}
				else{	
					// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
					if(npp_fk.trim().length() > 0)
					{
						// TỪ NGÀY 29/01/2015 >> ĐƠN GIÁ ĐÃ TRỪ LUÔN TIỀN CK
						if(ktra_ngay > 0)
							dongia = roundNumer((dongia * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
					}					
					thanhtien =Math.round(soLUONG*dongia);
					thueGTGT = Math.round(thanhtien *vat/100);
					sotientt = thanhtien + (thanhtien*vat/100) ;
					
					 vatCK = rsSP.getDouble("thuevat");
				}
				
				
				if(ddh.equals("100526")) // Tam thoi sua Vat cho Nhà QN 
				{
					thueGTGT = 571428 ;
					sotientt = thanhtien + thueGTGT;
					totalThueGTGT += thueGTGT ;
					totalTienTruocVAT+= (soLUONG*dongia);
					totalCK += chietkhau;
					
				}
				else
				{	
					if(SoNgay(ngayxuatHD)){
						totalThueGTGT +=thueGTGT;
						totalTienTruocVAT+=thanhtien;
						totalSotienTT +=sotientt;
					}
					else{
					
						if(npp_fk.trim().length() > 0) // XUẤT CHO ĐỐI TÁC
						{
							totalThueGTGT += thueGTGT ;
							totalTienTruocVAT+= thanhtien ;
							totalCK += 0;
						}else
						{
							totalThueGTGT += (soLUONG*dongia)*vat/100;
							totalTienTruocVAT+= (soLUONG*dongia);
							totalCK += chietkhau;
						}
					}
				}
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0 && !rsSP.getString("ngayhethan").equals("null"))
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}				
				thanhtien = Math.round(thanhtien);
				thueGTGT = Math.round(thueGTGT);
				sotientt = Math.round(sotientt);
				
				String inDONGIA = "";
				String inTHANHTIEN = "";
				String inTHUEVAT = "";
				String inTHUEGTGT = "";
				String inSOTIENTT = "";
				
				if(ingia == 1)
				{
					if(dongia <= 0) inDONGIA = "";
					else  inDONGIA = formatter.format(dongia);
					
					if(thanhtien <=0 ) inTHANHTIEN = "";
					else  inTHANHTIEN = formatter1.format(thanhtien);
					
					if( rsSP.getDouble("thuevat") <=0 ) inTHUEVAT = "";
					else  inTHUEVAT = formatter1.format(rsSP.getDouble("thuevat"));
					
					if(thueGTGT <= 0) inTHUEGTGT = "";
					else  inTHUEGTGT = formatter1.format(thueGTGT);
					
					if(sotientt <= 0) inSOTIENTT = "";
					else  inSOTIENTT = formatter1.format(sotientt) ;
				}
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("TEN"), rsSP.getString("SoLo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTraphaco(formatter1.format(soLUONG)), DinhDangTraphaco(inDONGIA),
						DinhDangTraphaco(inTHANHTIEN),DinhDangTraphaco(inTHUEVAT),
						DinhDangTraphaco(inTHUEGTGT),DinhDangTraphaco(inSOTIENTT) };


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
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " " };
	
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
				totalSotienTT = Math.round(totalTienTruocVAT) + Math.round(totalThueGTGT);
			
				
			String inTIEN1 ="";
			String inTIEN2 ="";
			String inTIEN3 = "";
			String inTIEN4= "";
			
			if(ingia == 1)
			{
				if(totalTienTruocVAT -totalCK <=0) inTIEN1 = "";
				else 
					inTIEN1 = formatter1.format(totalTienTruocVAT -totalCK );
				
				if(totalThueGTGT- (totalCK*vatCK/100)  <=0) inTIEN2 = "";
				else 
					inTIEN2 = formatter1.format(totalThueGTGT- (totalCK*vatCK/100) );
				
				if(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100)) <= 0 ) inTIEN3 = "";
				else 
					inTIEN3 = formatter1.format(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100) ));
			}
			
			String[] arr = new String[] { " ", " " , " ", " "," ", " "," ", DinhDangTraphaco(inTIEN1),
					                      " ",DinhDangTraphaco(inTIEN2),DinhDangTraphaco(inTIEN3) };
			
		
	
		for (int j = 0; j < arr.length; j++)
			{
			cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.BOLDITALIC)));
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
			cells.setPaddingTop(-9.1f);
			cells.setBorder(0);
			cells.setFixedHeight(0.6f*CONVERT);
			sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
		    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
			   String tien = doctien.docTien(Math.round(totalSotienTT - (totalCK + totalCK*vatCK/100)));
			 			   
		  //Viết hoa ký tự đầu tiên
		    String TienIN = "";
		    if(ingia == 0)
		    	TienIN = "";
		    else
		    	TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 12, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(11);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.1f * CONVERT);
				cells.setPaddingTop(-5.8f);
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
	
	
	private String CapnhatTT(String id) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			String trangthai="";
			// Kiem tra trạng thái hiện tại của Hóa đơn
			
			query = " select trangthai from ERP_HOADONNPP where pk_seq = "+ id +" ";
			ResultSet rs = db.get(query);
			if(rs!= null)
			{
				while(rs.next())
				{
					trangthai = rs.getString("trangthai");
				}rs.close();
			}
			
			if(!trangthai.equals("3") && !trangthai.equals("5"))
			{
				// Cập nhật trạng thái Đã in
				query = "update ERP_HOADONNPP set trangthai = '4' where pk_seq = '" + id + "' ";
				if(!db.update(query))
				{
					msg = "Không thể cập nhật ERP_HOADONNPP " + query;
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

	private void CreatePxk(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
				
					//LAY THONG TIN NCC
					String kbh="";
					String ddh="";
					String npp_fk="";
					String khId="";
					
					String ctyTen="";
					String cty_MST ="";
					String cty_Diachi="";
					String cty_Sotaikhoan= "";
					String cty_Dienthoai= "";
					String cty_Fax= "";
					String khoxuat ="";
					String hinhthucTT= "";
					String ngayxuathd ="";
					String nguoimuahang="";
					double chietkhauDH = 0;
					
					String sql =
					"SELECT  A.PK_SEQ, A.KBH_FK ,(select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = A.NPP_FK) AS KHO," +
					" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
					" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
					" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS HTTT," +
					" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ngayxuathd," +
					" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
					"               else isnull(nguoimua,'') end " +
					"   FROM ERP_HOADONNPP" +
					"   WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua,isnull(chietkhau,0) as chietkhauDH "  +
				    "FROM ERP_DONDATHANGNPP A " +
				    "WHERE A.PK_SEQ IN  " +
				    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + pxkBean.getId() + "' )";
		
					System.out.println("[INIT_DONHANG]"+sql);
					
					ResultSet rsCheck = db.get(sql);					
										
					if(rsCheck.next())
					{
						npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
						khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
						ddh = rsCheck.getString("PK_SEQ");
						nguoimuahang = rsCheck.getString("nguoimua");
						kbh = rsCheck.getString("KBH_FK");
						khoxuat = rsCheck.getString("KHO");
						hinhthucTT = rsCheck.getString("HTTT");
						ngayxuathd = rsCheck.getString("ngayxuathd");
						chietkhauDH = rsCheck.getDouble("chietkhauDH");
						rsCheck.close();
					}
			
				
			
			   
			 //LAY THONG TIN KHACHHANG
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
				
				
		if(khId.length() > 0){
			/*  sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
			
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}else{
			/*sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			 sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}
			   
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
		   }   
		   
		    NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(2.0f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 2.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open() ;
			
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.17f * CONVERT);
			cell.setPaddingLeft(2.6f * CONVERT);
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
			cell_nguoimua1.setPaddingLeft(4.0f * CONVERT);
			pxk = new Paragraph(nguoimuahang, new Font(bf, 10, Font.NORMAL));
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
			cell8a.setPaddingLeft(3.7f * CONVERT);
			pxk = new Paragraph(Donvi, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell8a.addElement(pxk);
			cell8a.setBorder(0);						
			table1.addCell(cell8a);
			

			// DONG 3 ---- DIA CHI
			PdfPCell cell10 = new PdfPCell();
			cell10.setPaddingLeft(2.5f * CONVERT);	
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
			cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(chuoi1, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell14.addElement(pxk);
			cell14.setBorder(0);						
			table1.addCell(cell14);		
			
			
			//DONG 4 --- DIA CHI : dai se xuong dong
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
			cell14a.addElement(pxk);
			cell14a.setBorder(0);						
			table1.addCell(cell14a);	
			
			
			// DONG 5 ----KHO XUAT
			PdfPCell cell17 = new PdfPCell();	
			cell17.setPaddingLeft(2.9f * CONVERT);
			cell17.setPaddingTop(0.1f * CONVERT);
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
			cell18.setPaddingLeft(5.0f * CONVERT);
			cell18.setPaddingTop(0.1f * CONVERT);
			pxk = new Paragraph( kh_MST +"                                                " +hinhthucTT, new Font(bf, 10, Font.NORMAL));
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
			sanpham.setSpacingBefore(46.8f);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 7.0f, 15.0f, 60f, 17f, 15f, 9.0f, 14.0f, 20f, 24.0f, 8.0f, 23f, 25f };
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			double totalCK = 0;
			double vatCK = 0;
			
			/*String 			
			query =
			" select distinct c.MA, c.TEN, d.DONVI, b.soluong, ' ' as solo, ' ' as  NGAYHETHAN, dhsp.chietkhau,dhsp.thuevat, dhsp.dongia  "+	
			" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq=b.ycxk_fk	"+
			"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk= a.PK_SEQ									"+        									
			"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ=e.ddh_fk    									"+
			"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	"+
			"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						"+
			"     left join DONVIDOLUONG d on d.PK_SEQ=c.dvdl_fk 	"+
			" where a.PK_SEQ =( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk= '"+ ddh +"' )";*/
			
			
			/*String query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
							"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
							"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
							"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
							"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, dhsp.dongia  	 " +
							"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
							"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
							"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
							"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
							"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
							"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
							"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";*/
			
			String	 query = "";
			if(SoNgay(ngayxuathd)){
				query = 
					"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
					"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  \n" +
					"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
					"	case solo when 'NA' then ' ' else b.solo end as solo,  \n" +
					"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, \n" +
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
					"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
				    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
				    "   ELSE 0 END as chenhlech, \n"+
					"   ISNULL((select ISNULL(bgsp.CHIETKHAU,0) \n" +
					"           from BANGGIABANDOITAC bg inner join BANGGIABANDOITAC_DOITAC bgdt on bg.PK_SEQ= bgdt.BANGGIABANDOITAC_FK  \n" +
					"						             inner join BANGGIABANDOITAC_SANPHAM bgsp on bgdt.BANGGIABANDOITAC_FK = bgsp.BGBANDOITAC_FK \n" +
					"						              and bgsp.SANPHAM_FK = dhsp.sanpham_fk  \n" +
					"           where bg.KENH_FK = dh.KBH_FK and bgdt.NPP_FK = dh.NPP_FK ),0) as CHIETKHAU_BG \n" +
					"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 \n" +
					"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ				\n" +					       									
					"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 \n" +
					"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 \n" +
					"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  					\n" +
					"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 \n" +
					"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			else{
				query = "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
							"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
							"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
							"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
							"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.thuevat, " +
							"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
							"	,case when	ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) !=1 then 0 else dhsp.chietkhau end as chietkhau "+
							"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
							"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
							"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
							"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
							"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
							"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
							"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			double chenhlech = 0;
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt =0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double vat = rsSP.getDouble("thuevat");
				
				if(SoNgay(ngayxuathd)){
					chenhlech = rsSP.getDouble("chenhlech");
					thanhtien = Math.round(soLUONG * dongia - chietkhau);
					thueGTGT = Math.round(thanhtien*vat/100);
					
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 && khId.trim().length() > 0)
					{
					   thueGTGT = thueGTGT - chenhlech;
					}
										
					sotientt = thanhtien + thueGTGT;
					
					// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
					if(npp_fk.trim().length() > 0)
					{
						dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
						chietkhau = 0;
						thanhtien = Math.round(soLUONG * dongia);
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + thueGTGT;
					}
				}
				else{
					thanhtien = soLUONG*dongia;
					thueGTGT = thanhtien *vat/100;
					sotientt = thanhtien + (thanhtien*vat/100) ;				
					vatCK = rsSP.getDouble("thuevat");
				}
					
				if(ddh.equals("100526")) // Tam thoi sua Vat cho Nhà QN 
				{
					thueGTGT = 571428 ;
					sotientt = thanhtien + thueGTGT;
					totalThueGTGT += thueGTGT ;
					totalTienTruocVAT+= (soLUONG*dongia);
					totalCK += chietkhau;
					
				}
				else
				{
					if(SoNgay(ngayxuathd)){
						totalThueGTGT +=thueGTGT;
						totalTienTruocVAT+=thanhtien;
						totalSotienTT +=sotientt;
						totalCK += chietkhau;
					}
					else{
						totalThueGTGT += (soLUONG*dongia)*vat/100;
						totalTienTruocVAT+= (soLUONG*dongia);
						totalCK += chietkhau;
					}
				}
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0 && !rsSP.getString("ngayhethan").equals("null"))
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				thanhtien = Math.round(thanhtien);
				thueGTGT = Math.round(thueGTGT);
				sotientt = Math.round(sotientt);
				
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("solo"),chuoi, rsSP.getString("DONVI"),
						DinhDangTraphaco(formatter.format(soLUONG)), DinhDangTraphaco(formatter4.format(dongia)) , DinhDangTraphaco(formatter.format(thanhtien)), DinhDangTraphaco(formatter.format(vat)), DinhDangTraphaco(formatter.format(thueGTGT)), DinhDangTraphaco(formatter.format(sotientt)) };


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
					
					if(j==4 ) cells.setPaddingLeft(-0.10f *CONVERT);
					
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
							}else
							{	
								if(j==6) 
								  cells.setPaddingLeft(0.3f *CONVERT);
								else
								  cells.setPaddingLeft(0.5f *CONVERT);
							}
							
						}
					}
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
		    
			if(totalCK > 0)
			{
				stt = stt -1 ;
				//CHIẾT KHẤU DÒNG HÀNG (NẾU CÓ) : SUM(CHIETKHAU) CỦA TẤT CẢ CÁC SP
				String [] arr5 = new String[] {Integer.toString(stt+1),"CK" ,"Chiết khấu sản phẩm " ," ","","", DinhDangTraphaco(formatter.format(totalCK)),DinhDangTraphaco(formatter.format(vatCK)),
						DinhDangTraphaco(formatter.format(totalCK*vatCK/100)) ,DinhDangTraphaco(formatter.format( totalCK + totalCK*vatCK/100))};
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
					if(j==2)
					{
						cells.setColspan(3);
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else 
					{
						if(j <= 1)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					
	
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setPaddingTop(2.5f);
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					if(j >=5)
					{
						if(j==5)
						{
							cells.setPaddingLeft(0.25f *CONVERT);
						}else
						{
							if(j==7)
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
			totalSotienTT = Math.round(totalTienTruocVAT) + Math.round(totalThueGTGT);
			
			String[] arr = new String[] {"                             ", DinhDangTraphaco(formatter1.format(totalTienTruocVAT -totalCK )),"",DinhDangTraphaco( formatter1.format(totalThueGTGT- (totalCK*vatCK/100) )),DinhDangTraphaco(formatter1.format(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100) ))) };
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
			    String tien = doctien.docTien(Math.round(totalSotienTT - (totalCK + totalCK*vatCK/100)));		   
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

	/**
	 * @param document
	 * @param outstream
	 * @param pxkBean
	 * @throws IOException
	 */
	private void CreatePxk_QUANGNGAI(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP pxkBean) throws IOException
	{
		try
		{			
			dbutils db = new dbutils();
				
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			String hinhthucTT= "";
			String ngayxuatHD ="";
			String chucuahieu="";
			double chietkhauDH = 0;
			
			String sql =
			"SELECT  A.PK_SEQ, A.KBH_FK ,(select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = A.NPP_FK) AS KHO," +
			" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS KHACHHANG_FK," +
			" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS NPP_FK," +
			" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + pxkBean.getId() + "'  ) AS HTTT," +
			" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + pxkBean.getId() + "' ) as ngayxuathd," +
			" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"               else isnull(nguoimua,'') end " +
			"   FROM ERP_HOADONNPP" +
			"   WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua, isnull(chietkhau,0) as chietkhauDH "  +
		    "FROM ERP_DONDATHANGNPP A " +
		    "WHERE A.PK_SEQ IN  " +
		    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + pxkBean.getId() + "' )";

			System.out.println("[INIT_DONHANG]"+sql);
			
			ResultSet rsCheck = db.get(sql);					
								
			if(rsCheck.next())
			{
				npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
				khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");
				ddh = rsCheck.getString("PK_SEQ");
				chucuahieu = rsCheck.getString("nguoimua");
				kbh = rsCheck.getString("KBH_FK");
				khoxuat = rsCheck.getString("KHO");
				hinhthucTT = rsCheck.getString("HTTT");
				ngayxuatHD = rsCheck.getString("ngayxuathd");
				chietkhauDH = rsCheck.getDouble("chietkhauDH");
				rsCheck.close();
			}
	
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from ERP_HOADONNPP where pk_seq = '"+ pxkBean.getId() +"') ";
		   
		   System.out.println("Lấy TT CTY HP "+sql);
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
			   String kyhieu=""; 
				 sql ="SELECT  A.KYHIEU " +
					 "FROM ERP_HOADONNPP A " +
					 "WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";
				
				System.out.println("[INIT_ERP_HOADONNPP 11]"+sql);
				
				ResultSet rsHD = db.get(sql);					
									
				if(rsHD.next())
				{					
					kyhieu = rsHD.getString("KYHIEU");
					rsHD.close();
				} 
				   
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
		
				
				
		if(khId.length() > 0){
		/*	  sql = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
			        " from KHACHHANG " +
			        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";*/
			
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}else{
		/*	sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			 sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+pxkBean.getId()+"'";
		}
			   
		   
		   System.out.println("Lấy TT KH1 "+sql);
		   ResultSet rsLayKH= db.get(sql);
		   if(rsLayKH.next())
		   {
			   Donvi = rsLayKH.getString("TEN");
			   kh_MST = rsLayKH.getString("MASOTHUE");
			   kh_Diachi = rsLayKH.getString("DIACHI");
			  
			   rsLayKH.close();
			   
		   } 
		   
	    NumberFormat formatter = new DecimalFormat("#,###,###.####");
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
		
		String [] ngayHD = ngayxuatHD.split("-");
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
			
			String query = "";
			
			if(SoNgay(ngayxuatHD)){
				query = 
					"select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
					"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  \n" +
					"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  \n" +
					"	case solo when 'NA' then ' ' else b.solo end as solo,  \n" +
					"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, \n" +
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
					"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
				    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
				    "   ELSE 0 END as chenhlech, \n"+
					"   ISNULL((select ISNULL(bgsp.CHIETKHAU,0) \n" +
					"           from BANGGIABANDOITAC bg inner join BANGGIABANDOITAC_DOITAC bgdt on bg.PK_SEQ= bgdt.BANGGIABANDOITAC_FK  \n" +
					"						             inner join BANGGIABANDOITAC_SANPHAM bgsp on bgdt.BANGGIABANDOITAC_FK = bgsp.BGBANDOITAC_FK \n" +
					"						              and bgsp.SANPHAM_FK = dhsp.sanpham_fk  \n" +
					"           where bg.KENH_FK = dh.KBH_FK and bgdt.NPP_FK = dh.NPP_FK ),0) as CHIETKHAU_BG \n" +
					"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 \n" +
					"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ				\n" +					       									
					"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 \n" +
					"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 \n" +
					"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  					\n" +
					"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 \n" +
					"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}
			else{
				query =
					 "select distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  " +
						"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  " +
						"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong,  " +
						"	case solo when 'NA' then ' ' else b.solo end as solo,  " +
						"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat, " +
						"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + pxkBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
						"from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	 " +
						"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       									 " +
						"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									 " +
						"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	 " +
						"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						 " +
						"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	 " +
						"where a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk = '" + ddh + "' ) and b.soluong > 0 and a.TRANGTHAI != '3' ";
			}

			System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double vatCK = 0;
			double totalCK = 0;
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt = 0;
			double chenhlech =0;
			
			while(rsSP.next())
			{

				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double vat = rsSP.getDouble("thuevat");
				
				if(SoNgay(ngayxuatHD)){
					chenhlech = rsSP.getDouble("chenhlech");
					thanhtien = Math.round(soLUONG * dongia - chietkhau);
					thueGTGT = Math.round(thanhtien*vat/100);
					
					// NẾU KH ETC LẤY TIỀN VAT SAU KHI SỬA
					if(chenhlech != 0 && khId.trim().length() > 0)
					{
					   thueGTGT = thueGTGT - chenhlech;
					}
										
					sotientt = thanhtien + thueGTGT;
					
					// NẾU XUẤT CHO ĐỐI TÁC (ĐÀ NẴNG && HCM) ĐƠN GIÁ SAU CHIẾT KHẤU (CK ĐƠN HÀNG + CK BẢNG GIÁ)
					if(npp_fk.trim().length() > 0)
					{
						dongia = roundNumer((rsSP.getDouble("dongia") * (100 - (chietkhauDH + rsSP.getDouble("chietkhau_bg")))/100 ),4);
						chietkhau = 0;
						thanhtien = Math.round(soLUONG * dongia);
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + thueGTGT;
					}
				}
				else{
					thanhtien =Math.round(soLUONG*dongia);
					thueGTGT = Math.round(thanhtien *vat/100);
					sotientt = thanhtien + (thanhtien*vat/100) ;
					
					vatCK = rsSP.getDouble("thuevat");
				}
					
				if(ddh.equals("100526")) // Tam thoi sua Vat cho Nhà QN 
				{
					thueGTGT = 571428 ;
					sotientt = thanhtien + thueGTGT;
					totalThueGTGT += thueGTGT ;
					totalTienTruocVAT+= (soLUONG*dongia);
					totalCK += chietkhau;
					
				}
				else
				{
					if(SoNgay(ngayxuatHD)){
						totalThueGTGT +=thueGTGT;
						totalTienTruocVAT+=thanhtien;
						totalSotienTT +=sotientt;
					}
					else{
						totalThueGTGT += (soLUONG*dongia)*vat/100;
						totalTienTruocVAT+= (soLUONG*dongia);
						totalCK += chietkhau;
					}
				}
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0 && !rsSP.getString("ngayhethan").equals("null"))
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}				
				thanhtien = Math.round(thanhtien);
				thueGTGT = Math.round(thueGTGT);
				sotientt = Math.round(sotientt);
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") + '-' +rsSP.getString("TEN"), rsSP.getString("SoLo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTraphaco(formatter1.format(soLUONG)), DinhDangTraphaco(formatter.format(dongia)),
						DinhDangTraphaco(formatter1.format(thanhtien)),DinhDangTraphaco(formatter1.format(rsSP.getDouble("thuevat"))),
						DinhDangTraphaco(formatter1.format(thueGTGT)),DinhDangTraphaco(formatter1.format(sotientt)) };


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
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " " };
	
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
				totalSotienTT = Math.round(totalTienTruocVAT) + Math.round(totalThueGTGT);
			
			String[] arr = new String[] { " ", " " , " ", " "," ", " "," ", DinhDangTraphaco(formatter1.format(totalTienTruocVAT -totalCK )),
					                      " ",DinhDangTraphaco( formatter1.format(totalThueGTGT- (totalCK*vatCK/100) )),DinhDangTraphaco(formatter1.format(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100) ))) };
			
		
	
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
		    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
			   String tien = doctien.docTien(Math.round(totalSotienTT - (totalCK + totalCK*vatCK/100)));
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
	
	private String DinhDangTraphaco(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}

/*	public static void main(String[] arg)
	{
		ErpHoadontaichinhNPPPdfSvl hd = new ErpHoadontaichinhNPPPdfSvl();
		
		System.out.println(hd.DinhDangTraphaco("12,000.56"));
	}*/
	
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
	
	
	private double roundNumer(double num, int dec)
	{
		double result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
	
	private boolean SoNgay (String ngayxuathd){
		boolean kt = false;
		int songay = 0;
		//NẾU NGÀY XUẤT HÓA ĐƠN > '2014-12-08' THÌ ĐƯA VỀ ĐỊNH DẠNG MỚI
		dbutils db = new dbutils();
		String layngay = "select datediff(DD,'2014-01-09', '"+ngayxuathd+"') songay";
		ResultSet checkngay = db.get(layngay);
		
		try{
			if(checkngay.next())
			{
				songay = checkngay.getInt("songay");
				checkngay.close();
			}
			if(songay >=0 ) kt = true;
		}
		catch (Exception e){
			e.printStackTrace();
			kt = false;
		}
		
		return kt;
		
	}

	public String FormatDonGia(double dongia, String indongia, String donhangkhac, String kho, String xuathd_coVAT)
	{
		String kq = "";
		
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
				
		if(dongia <= 0)
			return "";
					
		kq = formatter.format(dongia);
		
		return kq;
	}
	
}
