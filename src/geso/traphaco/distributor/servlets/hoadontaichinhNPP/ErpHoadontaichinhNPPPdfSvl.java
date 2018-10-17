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

public class ErpHoadontaichinhNPPPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpHoadontaichinhNPPPdfSvl()
	{
		super();
	}
	float CONVERT = 28.346457f; //
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
		
		String print = util.antiSQLInspection(request.getParameter("print"));
		
		IErpHoadontaichinhNPP hdBean = new ErpHoadontaichinhNPP(id);
		hdBean.setUserId(userId);
		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));

		String task = request.getParameter("task");
		if (querystring.indexOf("pdf") > 0)
		{
			//hdBean.initPdf();		

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=HoadontaichinhNPP.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			
			//LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI)
			//LOẠI XUẤT HD: 0 - XUẤT CHO ĐỐI TÁC; 1 - XUẤT CHO KHÁCH HÀNG ETC, 2- OTC , 3 - BÁN NỘI BỘ
			
			String INIT_THONGTINKH =
				"select isnull(c.ChietKhau,0) as ptchietkhau,a.kho_fk,dondathangnpp_fk, a.nhomkenh_fk, a.npp_fk, RIGHT(ngayxuatHD, 8) ngayxuatHD, ISNULL(a.ghichu, '') as ghichu, \n"+
				"  		a.khachhang_fk, a.npp_dat_fk, a.trangthai, a.kyhieu, sohoadon, hinhthuctt , \n"+
				"  		isnull(a.nguoimua, '') as nguoimua, isnull(innguoimua,1) as innguoimua,  isnull(tongtienbvat,0) as tongtienbvat,  isnull(tongtienavat,0) \n" +
				"   	as tongtienavat,  isnull(a.vat, 0) as vat, isnull(a.chietkhau, 0) as chietkhau, loaixuatHD,isnull(mavv,'') as mavv, \n"+
				"		isnull(a.TENXUATHD, '') as donvi,  \n"+
				"		isnull(a.MASOTHUE, '') as MASOTHUE, \n"+
				"		isnull(a.DIACHI, '') as DIACHI, (select count(distinct(VAT)) from ERP_HOADONNPP_SP hd where hd.HOADON_FK = a.PK_SEQ and LEN(isnull(hd.scheme,'')) <= 0 ) countVAT, \n"+
				"		(select count(*) from ERP_HOADONNPP_SP hd where hd.HOADON_FK = a.PK_SEQ and LEN(isnull(hd.scheme,'')) <= 0 and hd.VAT = 5 ) countVAT5,  \n"+
				"		(select count(*) from ERP_HOADONNPP_SP hd where hd.HOADON_FK = a.PK_SEQ and LEN(isnull(hd.scheme,'')) <= 0 and hd.VAT = 10 ) countVAT10, isnull(f.SOTAIKHOAN,'') SOTAIKHOAN, \n"+
				"		isnull(f.inBHDT,0) inBHDT, a.NGAYXUATHD, a.LOAIXUATHD, c.KBH_FK , " +
				"		(SELECT COUNT(HOADON_FK) FROM ERP_HOADONNPP_CHIETKHAU PP WHERE PP.HOADON_FK = A.PK_SEQ AND DIENGIAI = N'Trả tích lũy' ) CKTL, "+
				"		(SELECT COUNT(HOADON_FK) FROM ERP_HOADONNPP_CHIETKHAU PP WHERE PP.HOADON_FK = A.PK_SEQ AND DIENGIAI = N'Trả khuyến mại' AND SCHEME NOT IN (N'1.MRTQ1601COMBO',N'2.MRTQ1601COMBO',N'3.MRTQ1601COMBO-PM', N'2.MRTQ1601COMBO+trung bay', N'1.MRTQ1601COMBO+trung bay' )  ) CKKM, "+
				"       (SELECT COUNT(HOADON_FK) FROM ERP_HOADONNPP_CHIETKHAU PP WHERE PP.HOADON_FK = A.PK_SEQ AND SCHEME IN (N'1.MRTQ1601COMBO',N'2.MRTQ1601COMBO',N'3.MRTQ1601COMBO-PM', N'2.MRTQ1601COMBO+trung bay', N'1.MRTQ1601COMBO+trung bay' )  ) SCHEMEKM , \n"+
				//"		(SELECT COUNT(HOADON_FK) FROM ERP_HOADONNPP_CHIETKHAU PP WHERE PP.HOADON_FK = A.PK_SEQ AND PP.VAT = 5 ) CKTL5,  \n"+
				//"       (SELECT COUNT(HOADON_FK) FROM ERP_HOADONNPP_CHIETKHAU PP WHERE PP.HOADON_FK = A.PK_SEQ AND PP.VAT = 10 ) CKTL10, \n"+
				"       (SELECT COUNT(HOADON_FK) FROM ERP_HOADONNPP_SP_CHITIET WHERE hoadon_fk ="+hdBean.getId()+"  and LEN(isnull(scheme,'')) > 0 AND SOLUONG > 0 ) isSpKM, a.TRANGTHAI, nv.TEN NGUOICHOT \n"+
				"from 	ERP_HOADONNPP a \n"+
				"   	left join ERP_HOADONNPP_DDH b on b.HOADONNPP_FK = a.PK_SEQ \n"+
				"   	left join ERP_DONDATHANGNPP c on c.PK_SEQ = b.DDH_FK \n"+
			/*	"		left join ( SELECT top(1) KHACHHANG_FK pk_seq, A.TENNGUOIMUA, A.DONVI, A.DIACHI, A.MASOTHUE, B.CHUCUAHIEU, B.TEN \n"+
				"					FROM KHACHHANG_THONGTINHOADON A INNER JOIN KHACHHANG B ON A.KHACHHANG_FK = B.PK_SEQ \n"+
				"					WHERE B.PK_SEQ = (select KHACHHANG_FK from ERP_HOADONNPP where PK_SEQ = "+hdBean.getId()+"  ) \n"+
				"					ORDER BY A.PK_SEQ desc \n"+
				"				  ) d on a.khachhang_fk = d.pk_seq \n"+*/
				"		left join NHAPHANPHOI e on a.npp_dat_fk = e.pk_seq \n"+
				"		left join KHACHHANG f on a.KHACHHANG_FK = f.PK_SEQ \n"+
				"		left join ERP_HOADONNPP_CHIETKHAU hdck on a.PK_SEQ = hdck.HOADON_FK \n"+
				"       inner join NHANVIEN nv ON a.NGUOISUA = nv.PK_SEQ \n"+
				"where a.pk_seq = '"+ hdBean.getId()  +"'";
		   System.out.println("[INIT_THONGTINKH]"+INIT_THONGTINKH);
		  
		   // MẪU MỚI 		   
		   // OTC + NPP: không lẻ
		   // ETC : số lẻ
		   
/*		  String INIT_SANPHAM =
			   "select  hoadon_fk, MA, TEN,SOLO, DONVI, SOLUONG, ISNULL(DONGIA, DONGIA_CHUAN) DONGIA, round(SOLUONG*ISNULL(DONGIA, DONGIA_CHUAN),0) THANHTIEN, THUEVAT, \n"+ 
			   "       	round( ( round(SOLUONG*ISNULL(DONGIA, DONGIA_CHUAN),0) - round(isnull(CHIETKHAU,0),0) ) * THUEVAT /100,0) TIENVAT, "+
			   "		(SELECT COUNT(HOADON_FK) FROM ERP_HOADONNPP_SP_CHITIET WHERE hoadon_fk ="+ hdBean.getId() +"  and LEN(isnull(scheme,'')) > 0 ) isSpKM, round(isnull(CHIETKHAU,0),0) CHIETKHAU, \n"+
			   "		(case LEN (CAST(DATEPART(DD, NGAYHETHAN ) AS NVARCHAR(50))) \n"+ 
			   "		when 1 then '0'+ CAST(DATEPART(DD, NGAYHETHAN) AS NVARCHAR(50)) \n"+
			   "		when 2 then CAST(DATEPART(DD, NGAYHETHAN) AS NVARCHAR(50)) end) \n"+ 
			   "		+'/'+ "+
			   " 		(case LEN (CAST(DATEPART(MM, NGAYHETHAN ) AS NVARCHAR(50))) \n"+ 
			   "		when 1 then '0'+ CAST(DATEPART(MM, NGAYHETHAN) AS NVARCHAR(50)) \n"+
			   "		when 2 then CAST(DATEPART(MM, NGAYHETHAN) AS NVARCHAR(50)) end) \n"+
			   "		+'/'+ \n"+
			   " 		RIGHT(CAST(DATEPART(YY, NGAYHETHAN ) AS NVARCHAR(50)),2) NGAYHETHAN \n"+
			   "from ERP_HOADONNPP_SP_CHITIET \n"+ 
			   "WHERE hoadon_fk = '"+ hdBean.getId()  +"' AND LEN(isnull(scheme,'')) <= 0 AND isnull(SOLUONG,0) > 0 ";*/
		  
		  String INIT_SANPHAM_KM =
				   "select  hoadon_fk, MA, TEN,SOLO, DONVI, SOLUONG, ROUND(ISNULL(DONGIA, DONGIA_CHUAN),0) DONGIA, THANHTIEN THANHTIEN , THUEVAT, \n"+
				   "		TIENTHUE TIENVAT, "+
				   "		round(isnull(CHIETKHAU,0),0) CHIETKHAU, \n"+
				   "		(case LEN (CAST(DATEPART(DD, NGAYHETHAN ) AS NVARCHAR(50))) \n"+ 
				   "		when 1 then '0'+ CAST(DATEPART(DD, NGAYHETHAN) AS NVARCHAR(50)) \n"+
				   "		when 2 then CAST(DATEPART(DD, NGAYHETHAN) AS NVARCHAR(50)) end) \n"+ 
				   "		+'/'+ "+
				   " 		(case LEN (CAST(DATEPART(MM, NGAYHETHAN ) AS NVARCHAR(50))) \n"+ 
				   "		when 1 then '0'+ CAST(DATEPART(MM, NGAYHETHAN) AS NVARCHAR(50)) \n"+
				   "		when 2 then CAST(DATEPART(MM, NGAYHETHAN) AS NVARCHAR(50)) end) \n"+
				   "		+'/'+ \n"+
				   " 		RIGHT(CAST(DATEPART(YY, NGAYHETHAN ) AS NVARCHAR(50)),2) NGAYHETHAN \n"+
				   "from ERP_HOADONNPP_SP_CHITIET \n"+ 
				   "WHERE hoadon_fk = '"+ hdBean.getId()  +"' AND LEN(isnull(scheme,'')) > 0  AND isnull(SOLUONG,0) > 0 ";
		  		  
		//  System.out.println(INIT_SANPHAM_KM);
/*		  String INIT_CHIETKHAU_TICHLUY =
				  " SELECT A.HOADON_FK, isnull(A.VAT,0) tichluy_vat , round(isnull(A.tienchuaVAT,0),0) tichluy_tienBVAT, isnull(A.GIATRI,0) tichluy_tienAVAT,round(round(isnull(A.tienchuaVAT, 0),0)*isnull(A.VAT,0)/100,0) tichluy_tienvat , isnull(A.SOHOPDONG,0) SOHOPDONG , isnull(A.tungay,'') NGAYHOPDONG , isnull(A.denngay,'') denngay \n"+
				  " FROM ERP_HOADONNPP_CHIETKHAU A \n"+
				  " WHERE A.VAT = 5 AND A.HOADON_FK = "+hdBean.getId();*/
		  
		  // MẪU CŨ
		  
		  String INIT_CHIETKHAU_TICHLUY =
			  " SELECT A.HOADON_FK, isnull(A.VAT,0) tichluy_vat , round(isnull(A.tienchuaVAT,0),0) tichluy_tienBVAT, isnull(A.GIATRI,0) tichluy_tienAVAT,round(round(isnull(A.tienchuaVAT, 0),0)*isnull(A.VAT,0)/100,0) tichluy_tienvat , isnull(A.SOHOPDONG,0) SOHOPDONG , isnull(A.tungay,'') NGAYHOPDONG , isnull(A.denngay,'') denngay, SCHEME \n"+
			  " FROM ERP_HOADONNPP_CHIETKHAU A \n"+
			  " WHERE A.HOADON_FK = "+hdBean.getId() +" AND A.DIENGIAI = N'Trả tích lũy' ";
	  
		  System.out.println("[INIT_CHIETKHAU_TICHLUY]"+INIT_CHIETKHAU_TICHLUY);
		  
		  String INIT_CHIETKHAU_SCHEMEKM =
			  " SELECT A.HOADON_FK, SUM(round(isnull(A.tienchuaVAT,0),0)) tichluy_tienBVAT \n"+
			  " FROM ERP_HOADONNPP_CHIETKHAU A \n"+
			  " WHERE A.HOADON_FK = "+hdBean.getId() +" AND A.DIENGIAI = N'Trả khuyến mại' AND A.SCHEME IN (N'1.MRTQ1601COMBO',N'2.MRTQ1601COMBO',N'3.MRTQ1601COMBO-PM', N'2.MRTQ1601COMBO+trung bay', N'1.MRTQ1601COMBO+trung bay' ) " +
			  " GROUP BY A.HOADON_FK ";
		  
		  String INIT_CHIETKHAU_KM =
			  " SELECT A.HOADON_FK, SUM(round(isnull(A.tienchuaVAT,0),0)) tichluy_tienBVAT \n"+
			  " FROM ERP_HOADONNPP_CHIETKHAU A \n"+
			  " WHERE A.HOADON_FK = "+hdBean.getId() +" AND A.DIENGIAI = N'Trả khuyến mại' AND A.SCHEME NOT IN (N'1.MRTQ1601COMBO',N'2.MRTQ1601COMBO',N'3.MRTQ1601COMBO-PM', N'2.MRTQ1601COMBO+trung bay', N'1.MRTQ1601COMBO+trung bay' ) " +
			  " GROUP BY A.HOADON_FK ";
	  
		  System.out.println("[INIT_CHIETKHAU_KM]"+INIT_CHIETKHAU_KM);
		  
		  	  	  
		   String INIT_SANPHAM_VAT =
			   "select  hoadon_fk, MA, TEN,SOLO, NGAYHETHAN, DONVI, SOLUONG, ISNULL(DONGIA, DONGIA_CHUAN) DONGIA, round( SOLUONG * ISNULL(DONGIA, DONGIA_CHUAN), 0) THANHTIEN , THUEVAT, \n"+ 
			   "		round( ( round( SOLUONG * DONGIA, 0 ) - ISNULL( CHIETKHAU, 0 ) ) * THUEVAT / 100.0, 0 ) as TIENVAT," +
			   "		(SELECT COUNT(HOADON_FK) FROM ERP_HOADONNPP_SP_CHITIET WHERE hoadon_fk ="+ hdBean.getId() +"  and LEN(isnull(scheme,'')) > 0 ) isSpKM, round(ISNULL(CHIETKHAU,0),0) CHIETKHAU	 \n"+
			   "from ERP_HOADONNPP_SP_CHITIET \n"+ 
			   "WHERE hoadon_fk = '"+ hdBean.getId()  +"' AND LEN(isnull(scheme,'')) <= 0 AND isnull(SOLUONG,0) > 0 ";
		   
		    this.CreateHd_CNHOCHIMINH_OLD(document, outstream, hdBean, INIT_THONGTINKH, INIT_SANPHAM_VAT,INIT_SANPHAM_KM, INIT_CHIETKHAU_TICHLUY, INIT_CHIETKHAU_KM, INIT_CHIETKHAU_SCHEMEKM );
						
			String msg = this.CapnhatTT(id, print);
			hdBean.setMsg(msg);
		} 
		
	}

	private void CreateHd_CNHOCHIMINH_OLD(Document document,ServletOutputStream outstream, IErpHoadontaichinhNPP hdBean, String INIT_KHACHHANG, String INIT_SANPHAM_VAT,  String INIT_SANPHAM_KM, String INIT_CHIETKHAU_TICHLUY , String INIT_CHIETKHAU_KM , String INIT_CHIETKHAU_SCHEMEKM)
	{
		try
		{			
			dbutils db = new dbutils();
				
			String nguoimua = "";
			String donvi = "";
			String masothue = "";
			String diachi = "";
			String HTTT = "";
			String sotaikhoan = "";
			String ngayxuathd = "";
			String KBH_FK = "";
			String Trangthai = "";
			String nguoichot = "";
			double cktl5 = 0;
			double cktl10 = 0;
			double total_thue = 0;
			double total_tienavat = 0;
			double total_tienbvat = 0;
			int countVAT = 0;	
			int countVAT5 = 0;
			int countVAT10 = 0;
			int inBHDT = 0;
			int isSpKM = 0;
			int CKKM = 0;
			
			int schemekm = 0;
			
		    ResultSet rsINFO = db.get(INIT_KHACHHANG);
		   
			if(rsINFO.next())
			{
			   nguoimua = rsINFO.getString("nguoimua");
			   donvi = rsINFO.getString("donvi");
			   masothue = rsINFO.getString("MASOTHUE");
			   diachi = rsINFO.getString("DIACHI");
			   sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   HTTT = rsINFO.getString("hinhthuctt");
			   cktl5 = rsINFO.getDouble("CKTL");
			  // cktl10 = rsINFO.getDouble("CKTL10");
			   countVAT =  rsINFO.getInt("countVAT");
			   countVAT5 = rsINFO.getInt("countVAT5");
			   countVAT10 = rsINFO.getInt("countVAT10");
			   inBHDT =  rsINFO.getInt("inBHDT");
			   ngayxuathd = rsINFO.getString("NGAYXUATHD");
			   KBH_FK = rsINFO.getString("KBH_FK");
			   isSpKM = rsINFO.getInt("isSpKM");
			   Trangthai = rsINFO.getString("Trangthai");
			   schemekm = rsINFO.getInt("SCHEMEKM");
			   total_tienbvat = rsINFO.getDouble("tongtienbvat");
			   total_thue = rsINFO.getDouble("vat");
			   total_tienavat = rsINFO.getDouble("tongtienavat");
			   nguoichot = rsINFO.getString("NGUOICHOT");
			   CKKM = rsINFO.getInt("CKKM");
			}
			rsINFO.close();	
		   
		   NumberFormat formatter = new DecimalFormat("#,###,###.###");
		   NumberFormat formatter1 = new DecimalFormat("#,###,###.###");
		   NumberFormat formatter2 = new DecimalFormat("#,###,###");
		   
		   //document.setPageSize(PageSize.A4.rotate()); //CANH HÓA ĐƠN THEO CHIỀU DỌC
		   document.setMargins(0.2f*CONVERT, 1.5f*CONVERT, 4.1f*CONVERT, 0.0f*CONVERT); // L,R, T, B
		   PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		   document.open() ;
		   
   		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);			
	
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		//HỌ VÀ TÊN NGƯỜI MUA HÀNG
		PdfPCell cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);		
			
		String [] ngayHD = ngayxuathd.split("-");
		Paragraph hd = new Paragraph(ngayHD[2]+"              "+ ngayHD[1]+ "             "+ ngayHD[0] , new Font(bf, 12, Font.NORMAL)); // nguoi mua
		hd.setAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setFixedHeight(0.8f*CONVERT);
		cell.setPaddingLeft(7.8f*CONVERT);
		//cell.setPaddingTop(0.85f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);		

		tableheader.addCell(cell);
		
		//TÊN NGUOI MUA
		
		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);			
		
		hd = new Paragraph(nguoimua , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setFixedHeight(0.8f*CONVERT);
		cell.setPaddingLeft(4.1f*CONVERT);
		//cell.setPaddingTop(0.85f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);		

		tableheader.addCell(cell);
		
	
		//TÊN ĐƠN VỊ
		
		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);			
		
		hd = new Paragraph(donvi , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setFixedHeight(0.8f*CONVERT);
		cell.setPaddingLeft(1.6f*CONVERT);
		cell.setPaddingTop(-0.1f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);		

		tableheader.addCell(cell);
		
		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	
		//MÃ SỐ THUẾ
		
		//CẮT MÃ SỐ THUẾ
		String strMST_01[] = masothue.split("");
		int j = 0;
		String mst = "";
		System.out.println("masothue.trim().length():"+masothue.trim().length());
		
        for (int i = 0; i<=masothue.trim().length();i++) 
        {
            mst += strMST_01[i] + "    ";            
        }
            
        System.out.println("mst:"+mst);
        hd = new Paragraph(mst , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setFixedHeight(0.8f*CONVERT);
		cell.setPaddingLeft(1.3f*CONVERT);
		cell.setPaddingTop(-0.1f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);		

		tableheader.addCell(cell);
  
    
        //ĐỊA CHỈ 
		//diachi = "39-40-41 CCLK Mỹ Phước 1, Đường 11, KCN Mỹ Phước 1, Phường Mỹ Phước, Thị xã Bến Cát, Tỉnh Bình Dương 27417 7215467";
		if(diachi.length()<=85)
		{
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_LEFT);			
			
			hd = new Paragraph(diachi , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(1.0f*CONVERT);
			cell.setPaddingTop(-0.3f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
		}
		else
		{
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_LEFT);			
						
			hd = new Paragraph(diachi , new Font(bf, 9, Font.NORMAL));
			
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(1.0f*CONVERT);
			cell.setPaddingTop(-0.3f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
		}

		tableheader.addCell(cell);
        
		document.add(tableheader);
				
					
		PdfPTable table1 =new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
		table1.setWidths(withs1);									
					
	
		// HÌNH THỨC THANH TOÁN
		cell = new PdfPCell();	
		hd = new Paragraph(HTTT  , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		cell.setPaddingTop(-0.5f*CONVERT);
		cell.setFixedHeight(0.8f*CONVERT);
		cell.setPaddingLeft(3.3f*CONVERT);
		cell.addElement(hd);
		cell.setBorder(0);						
		table1.addCell(cell);
	

		// SỐ TÀI KHOẢN
		cell = new PdfPCell();	
		hd = new Paragraph(sotaikhoan  , new Font(bf, 12, Font.NORMAL));// sotaikhoan
		hd.setAlignment(Element.ALIGN_LEFT);
		cell.setPaddingTop(-0.5f*CONVERT);
		//cell.setPaddingLeft(.f*CONVERT);
		cell.setFixedHeight(0.8f*CONVERT);
		cell.addElement(hd);
		cell.setBorder(0);						
		table1.addCell(cell);
		
		document.add(table1);
	
	
		PdfPTable root = new PdfPTable(2);
		root.setKeepTogether(false);
		root.setSplitLate(false);
		root.setWidthPercentage(100);
		root.setHorizontalAlignment(Element.ALIGN_LEFT);
		root.getDefaultCell().setBorder(0);
		float[] cr = { 95.0f, 100.0f };
		root.setWidths(cr);

		String[] th = new String[]{ " ", " ", " ", " ", " "," " };
		
		PdfPTable sanpham = new PdfPTable(th.length);
		sanpham.setSpacingBefore(1.5f*CONVERT);
		sanpham.setWidthPercentage(100);
		sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		float[] withsKM = { 0.7f*CONVERT,7.2f*CONVERT, 1.4f*CONVERT, 1.8f*CONVERT, 3.0f*CONVERT, 3.0f*CONVERT };
		sanpham.setWidths(withsKM);
	
		System.out.println("INIT_SANPHAM_VAT"+INIT_SANPHAM_VAT);
		// PHẦN TIỀN HÀNG
		ResultSet rsSP= db.get(INIT_SANPHAM_VAT);
		
		PdfPCell cells = new PdfPCell();
	
		int stt = 0;
		String tensp = "", solo = "", handung = "", DVT = "" ;
		double soluong = 0, dongia = 0, thanhtien = 0, thuevat = 0, tienvat = 0, tienavat = 0,  tienck = 0 ;
		double sum_thanhtien = 0, sum_tienvat = 0, sum_tienavat = 0, sum_tienck = 0;
		//double sum_thanhtien_5 = 0, sum_thanhtien_10 = 0, sum_tienvat_5 = 0, sum_tienvat_10 = 0, sum_tienavat_5 = 0, sum_tienavat_10 = 0 ; 
	
		int count = 0;
	/*	if(Trangthai.equals("4"))
		{
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/dainhoadon.png");
			hinhanh.scalePercent(60);
			hinhanh.setAbsolutePosition(3.5f * CONVERT, 15.0f * CONVERT);
			document.add(hinhanh);
		}*/
	
		if(rsSP!=null){				
			while(rsSP.next()){				
				stt++;
				tensp = rsSP.getString("TEN");
				DVT = rsSP.getString("DONVI");
				soluong = rsSP.getDouble("SOLUONG");
				dongia = rsSP.getDouble("DONGIA");
				thanhtien = rsSP.getDouble("THANHTIEN");
				thuevat = rsSP.getDouble("THUEVAT");
				tienvat = rsSP.getDouble("TIENVAT");
				tienck = rsSP.getDouble("CHIETKHAU");
			
				tienavat = thanhtien + tienvat;
			
				//DÒNG TỔNG CỘNG TIỀN HÀNG
				sum_thanhtien += thanhtien;
				sum_tienvat += tienvat;
				sum_tienavat += tienavat;
				sum_tienck += tienck;
							
				// HÀM CẮT CHUỖI
				
				int vitri1= 0;
        		String chuoicd= "";
        		String chuoi1 = "";
        		String chuoi2 = "";
    		
        		String[] arr = null;
        		
        		if(KBH_FK.equals("100056")) // KÊNH INS THÌ KHÔNG LÀM TRÒN
        		{
        			arr = new String[] { Integer.toString(stt), tensp ,  DVT,DinhDangCANFOCO(formatter1.format(soluong)), DinhDangCANFOCO(formatter1.format(dongia)), DinhDangCANFOCO(formatter1.format(thanhtien))};
        		}
        		else
        		{
        			arr = new String[] { Integer.toString(stt), tensp ,  DVT,DinhDangCANFOCO(formatter1.format(soluong)), DinhDangCANFOCO(formatter2.format(dongia)), DinhDangCANFOCO(formatter1.format(thanhtien))};
        		}
        		
				for (int m = 0; m < arr.length; m ++){
					// NẾU LÀ TÊN SẢN PHẨM
					if(m==1)
					{
						if(arr[m].length() <= 40)
							cells = new PdfPCell(new Paragraph(arr[m], new Font(bf, 11, Font.NORMAL)));
						else
						{
	                		chuoi1 = arr[m].substring(0, 40);
	                		System.out.println("chuoi 1: " + chuoi1);
                  			System.out.println("vitri 1: " + vitri1);
	                		vitri1 = chuoi1.lastIndexOf(" ");
	                		chuoicd = chuoi1.substring(0, vitri1);
	                		System.out.println("chuoi cd: " + chuoicd);
	                		chuoi2 = arr[m].substring(vitri1+1, arr[m].length());
	                		System.out.println("chuoi 2: " + chuoi2);
							cells = new PdfPCell(new Paragraph(chuoicd, new Font(bf, 11, Font.NORMAL)));									
						}
					}
					else
						cells = new PdfPCell(new Paragraph(arr[m], new Font(bf, 11, Font.NORMAL)));					
										
									
					if(m==0||m>2)
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					
					else
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);						
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					
					if(m==1)
						cells.setPaddingLeft(0.2f*CONVERT);
					else if(m==3)
						cells.setPaddingRight(0.3f*CONVERT);
					else if(m==4)
						cells.setPaddingRight(0.5f*CONVERT);
					else if(m==5)
						cells.setPaddingRight(0.5f*CONVERT);
												
					cells.setBorder(0);
					//cells.setBorderWidth(1);
					cells.setFixedHeight(0.7f * CONVERT);	
					sanpham.addCell(cells);
					
				}
			
				count++;	
								
				if(arr[1].length() > 40)
				{
					int dodaichuoi = chuoi2.length();
					
					System.out.println("chuoi222:"+chuoi2.length());
						while(dodaichuoi > 0)
						{
							int boiso=dodaichuoi/40;
	                		if(boiso > 0)
	                		{
	                      			chuoi1 = chuoi2.substring(0, 40);
	                      			vitri1 = chuoi1.lastIndexOf(" ");
	                      			System.out.println("chuoi 1: " + chuoi1);
	                      			System.out.println("vitri 1: " + vitri1);
	                      			chuoicd = chuoi1.substring(0, vitri1);
	                      			System.out.println("chuoi cd: " + chuoicd);
	                      			chuoi2 = chuoi2.substring(vitri1 + 1,dodaichuoi );
	                      			System.out.println("chuoi 2: " + chuoi2);
	                		}
	                		else
	                		{
	                			chuoicd = chuoi2;
	                			chuoi2 = "";
	                		}
			                	
	                		for(int m = 0; m < arr.length; m++)
	                		{			
	                			if(m==1)
	                				cells = new PdfPCell(new Paragraph(chuoicd, new Font(bf, 11, Font.NORMAL)));
	                			else
	                				cells = new PdfPCell(new Paragraph("", new Font(bf, 11, Font.NORMAL)));

	                			if(m==0||m>=3)
	        						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					
	        					else
	        						cells.setHorizontalAlignment(Element.ALIGN_LEFT);						
	        							
	                			cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
	        					
	                			if(m==1 )
									cells.setPaddingLeft(0.2f*CONVERT);
	                			else if(m==3)
	        						cells.setPaddingRight(0.3f*CONVERT);
								else if(m==4)
									cells.setPaddingRight(0.5f*CONVERT);
								else if(m==5)
									cells.setPaddingRight(0.5f*CONVERT);
	        					
	                			cells.setBorder(0);
	                			//cells.setBorderWidth(1);
	                			cells.setFixedHeight(0.7f * CONVERT);	
								sanpham.addCell(cells);
								
	                		}
	                		dodaichuoi = chuoi2.length();

							count++;
							
							//System.out.println("count2:"+count);
						}
					}
				
			}
			rsSP.close();
			
		}
					
		// HÀNG KHUYẾN MÃI
		if(isSpKM>0 )
		{
			count++;	
			
			ResultSet rsSP_KM= db.get(INIT_SANPHAM_KM);
		
			cells = new PdfPCell();		
			
			if(rsSP_KM!=null){		
				while(rsSP_KM.next()){
					stt++;
					
					tensp = rsSP_KM.getString("TEN");
					DVT = rsSP_KM.getString("DONVI");
					soluong = rsSP_KM.getDouble("SOLUONG");
					dongia = rsSP_KM.getDouble("DONGIA");
					thanhtien = rsSP_KM.getDouble("THANHTIEN");
					//thuevat = rsSP_KM.getDouble("THUEVAT");
					tienvat = rsSP_KM.getDouble("TIENVAT");
					tienavat = thanhtien + tienvat;				
									
					// HÀM CẮT CHUỖI
					
					int vitri1= 0;
	        		String chuoicd= "";
	        		String chuoi1 = "";
	        		String chuoi2 = "";
	        		
	        		String[] arr = new String[] { Integer.toString(stt), tensp , DVT,DinhDangCANFOCO(formatter1.format(soluong)), "-", "-"  };
						
	        		for (int m = 0; m < arr.length; m ++){
						// NẾU LÀ TÊN SẢN PHẨM
						if(m==1)
						{
							if(arr[m].length() <= 40)
								cells = new PdfPCell(new Paragraph(arr[m], new Font(bf, 11, Font.NORMAL)));
							else
							{
		                		chuoi1 = arr[m].substring(0, 40);
		                		//System.out.println("chuoi 1: " + chuoi1);
	                  			//System.out.println("vitri 1: " + vitri1);
		                		vitri1 = chuoi1.lastIndexOf(" ");
		                		chuoicd = chuoi1.substring(0, vitri1);
		                		//System.out.println("chuoi cd: " + chuoicd);
		                		chuoi2 = arr[m].substring(vitri1+1, arr[m].length());
		                		//System.out.println("chuoi 2: " + chuoi2);
								cells = new PdfPCell(new Paragraph(chuoicd, new Font(bf, 11, Font.NORMAL)));
							}
						}
						else
							cells = new PdfPCell(new Paragraph(arr[m], new Font(bf, 11, Font.NORMAL)));					
											
										
						if(m==0||m>2)
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					
						else
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);						
										
						if(m==1)
							cells.setVerticalAlignment(Element.ALIGN_TOP);
						
						if(m==1 )
							cells.setPaddingLeft(0.2f*CONVERT);
            			else if(m==3)
    						cells.setPaddingRight(0.3f*CONVERT);
						else if(m==4)
							cells.setPaddingRight(0.5f*CONVERT);
						else if(m==5)
							cells.setPaddingRight(0.5f*CONVERT);
						
						cells.setBorder(0);
						//cells.setBorderWidth(1);
						cells.setFixedHeight(0.7f * CONVERT);	
						sanpham.addCell(cells);										
						
					}
	        		
	        		count++;
	        		System.out.println("count4:"+count);
	        		
	        		if(arr[1].length() > 40)
					{
						int dodaichuoi = chuoi2.length();
						
						System.out.println("chuoi222:"+chuoi2.length());
							while(dodaichuoi > 0)
							{
								int boiso=dodaichuoi/40;
		                		if(boiso > 0)
		                		{
		                      			chuoi1 = chuoi2.substring(0, 40);
		                      			vitri1 = chuoi1.lastIndexOf(" ");
		                      			//System.out.println("chuoi 1: " + chuoi1);
		                      			//System.out.println("vitri 1: " + vitri1);
		                      			chuoicd = chuoi1.substring(0, vitri1);
		                      			//System.out.println("chuoi cd: " + chuoicd);
		                      			chuoi2 = chuoi2.substring(vitri1 + 1,dodaichuoi );
		                      			//System.out.println("chuoi 2: " + chuoi2);
		                		}
		                		else
		                		{
		                			chuoicd = chuoi2;
		                			chuoi2 = "";
		                		}
				                	
		                		for(int m = 0; m < arr.length; m++)
		                		{			
		                			if(m==1)
		                				cells = new PdfPCell(new Paragraph(chuoicd, new Font(bf, 11, Font.NORMAL)));
		                			else
		                				cells = new PdfPCell(new Paragraph("", new Font(bf, 11, Font.NORMAL)));
	
		                			if(m==0||m>2)
		        						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					
		        					else
		        						cells.setHorizontalAlignment(Element.ALIGN_LEFT);						
		        									
		        					if(m==1)
		        						cells.setVerticalAlignment(Element.ALIGN_TOP);
		        					
		        					if(m==1 )
		    							cells.setPaddingLeft(0.2f*CONVERT);
		                			else if(m==3)
		        						cells.setPaddingRight(0.3f*CONVERT);
		    						else if(m==4)
		    							cells.setPaddingRight(0.5f*CONVERT);
		    						else if(m==5)
		    							cells.setPaddingRight(0.5f*CONVERT);
		        					
		                			cells.setBorder(0);
		        					//cells.setBorderWidth(1);
		                			cells.setFixedHeight(0.7f * CONVERT);	
									sanpham.addCell(cells);
		                		}
		                		dodaichuoi = chuoi2.length();
		                		
		                		count++;
		                		System.out.println("count4:"+count);
							}
						}
	        		
				}
			}
			rsSP_KM.close();			

			String[] arr_bosung = new String[] { " ", "Hàng khuyến mãi không thu tiền", " ", " ", " "," "};

			for (int i = 0; i < th.length; i++)
			{
				cells = new PdfPCell(new Paragraph(arr_bosung[i], new Font(bf, 11, Font.BOLD)));
				cells.setVerticalAlignment(Element.ALIGN_TOP);
				cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				cells.setFixedHeight(0.7f*CONVERT);
				cells.setBorder(0);
				//cells.setBorderWidth(1);
									
				sanpham.addCell(cells);
			}
		}
	
	// IN DÒNG CHIẾT KHẤU
	
	if(sum_tienck > 0) // THEO TỪNG DÒNG SP
	{
		stt++;
		String[] arr = new String[] { "", "Tiền chiết khấu: ", "","", "",DinhDangCANFOCO(formatter1.format(sum_tienck))};
		
		// HÀM CẮT CHUỖI
		
		int vitri1= 0;
		String chuoicd= "";
		String chuoi1 = "";
		String chuoi2 = "";
		
		for (int m = 0; m < arr.length; m ++)
		{
			// NẾU LÀ TÊN SẢN PHẨM
			if(m==1)
			{
				if(arr[m].length() <= 40)
					cells = new PdfPCell(new Paragraph(arr[m], new Font(bf, 11, Font.NORMAL)));
				else
				{
            		chuoi1 = arr[m].substring(0, 40);
            		//System.out.println("chuoi 1: " + chuoi1);
          			//System.out.println("vitri 1: " + vitri1);
            		vitri1 = chuoi1.lastIndexOf(" ");
            		chuoicd = chuoi1.substring(0, vitri1);
            		//System.out.println("chuoi cd: " + chuoicd);
            		chuoi2 = arr[m].substring(vitri1+1, arr[m].length());
            		//System.out.println("chuoi 2: " + chuoi2);
					cells = new PdfPCell(new Paragraph(chuoicd, new Font(bf, 11, Font.NORMAL)));									
				}
			}
			else
				cells = new PdfPCell(new Paragraph(arr[m], new Font(bf, 11, Font.NORMAL)));					
								
							
			if(m==0||m>2)
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					
			else
				cells.setHorizontalAlignment(Element.ALIGN_LEFT);						
			
			cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
			
			if(m==1 )
				cells.setPaddingLeft(0.2f*CONVERT);
			else if(m==3)
				cells.setPaddingRight(0.3f*CONVERT);
			else if(m==4)
				cells.setPaddingRight(0.5f*CONVERT);
			else if(m==5)
				cells.setPaddingRight(0.5f*CONVERT);
										
			cells.setBorder(0);
			//cells.setBorderWidth(1);
			cells.setFixedHeight(0.7f * CONVERT);	
			sanpham.addCell(cells);
			
		}
		count++;
    	System.out.println("count4:"+count);
		
    	if(arr[1].length() > 40)
		{
			int dodaichuoi = chuoi2.length();
			
			//System.out.println("chuoi222:"+chuoi2.length());
				while(dodaichuoi > 0)
				{
					int boiso=dodaichuoi/40;
            		if(boiso > 0)
            		{
                  			chuoi1 = chuoi2.substring(0, 40);
                  			vitri1 = chuoi1.lastIndexOf(" ");
                  			//System.out.println("chuoi 1: " + chuoi1);
                  			//System.out.println("vitri 1: " + vitri1);
                  			chuoicd = chuoi1.substring(0, vitri1);
                  			//System.out.println("chuoi cd: " + chuoicd);
                  			chuoi2 = chuoi2.substring(vitri1 + 1,dodaichuoi );
                  			//System.out.println("chuoi 2: " + chuoi2);
            		}
            		else
            		{
            			chuoicd = chuoi2;
            			chuoi2 = "";
            		}
	                	
            		for(int m = 0; m < arr.length; m++)
            		{			
            			if(m==1)
            				cells = new PdfPCell(new Paragraph(chuoicd, new Font(bf, 11, Font.NORMAL)));
            			else
            				cells = new PdfPCell(new Paragraph("", new Font(bf, 11, Font.NORMAL)));

            			if(m==0||m>=3)
    						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					
    					else
    						cells.setHorizontalAlignment(Element.ALIGN_LEFT);						
    							
            			cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
    					
            			if(m==1 )
							cells.setPaddingLeft(0.2f*CONVERT);
            			else if(m==3)
            				cells.setPaddingRight(0.3f*CONVERT);
						else if(m==4)
							cells.setPaddingRight(0.5f*CONVERT);
						else if(m==5)
							cells.setPaddingRight(0.5f*CONVERT);
    					
            			cells.setBorder(0);
            			//cells.setBorderWidth(1);
            			cells.setFixedHeight(0.7f * CONVERT);	
						sanpham.addCell(cells);
						
            		}
            		dodaichuoi = chuoi2.length();

					count++;
					
					System.out.println("count2:"+count);
				}
			}
    	
    	//DÒNG TỔNG CỘNG TIỀN HÀNG
		sum_tienavat -= sum_tienck;
		
	}
	
	if(schemekm > 0)
	{
		ResultSet rsCKKM = db.get(INIT_CHIETKHAU_SCHEMEKM); 
		
		double tichluy_tienBVAT = 0;
		
		if(rsCKKM!=null)
		{
			while(rsCKKM.next())
			{
				tichluy_tienBVAT = rsCKKM.getDouble("tichluy_tienBVAT");
			}
			rsCKKM.close();
		}
		
		String[] arr = new String[] { "", "Chiết khấu theo chính sách bán hàng", "","", "",DinhDangCANFOCO(formatter1.format(tichluy_tienBVAT))};
		
		// HÀM CẮT CHUỖI
			
		int vitri1= 0;
		String chuoicd= "";
		String chuoi1 = "";
		String chuoi2 = "";
    		
		 for (int m = 0; m < arr.length; m ++){
				// NẾU LÀ TÊN SẢN PHẨM
				if(m==1)
				{
					if(arr[m].length() <= 40)
						cells = new PdfPCell(new Paragraph(arr[m], new Font(bf, 11, Font.NORMAL)));
					else
					{
                		chuoi1 = arr[m].substring(0, 40);
                		//System.out.println("chuoi 1: " + chuoi1);
              			//System.out.println("vitri 1: " + vitri1);
                		vitri1 = chuoi1.lastIndexOf(" ");
                		chuoicd = chuoi1.substring(0, vitri1);
                		//System.out.println("chuoi cd: " + chuoicd);
                		chuoi2 = arr[m].substring(vitri1+1, arr[m].length());
                		//System.out.println("chuoi 2: " + chuoi2);
						cells = new PdfPCell(new Paragraph(chuoicd, new Font(bf, 11, Font.NORMAL)));									
					}
				}
				else
					cells = new PdfPCell(new Paragraph(arr[m], new Font(bf, 11, Font.NORMAL)));					
									
								
				if(m==0||m>2)
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					
				else
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);						
				
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				
				if(m==1 )
					cells.setPaddingLeft(0.2f*CONVERT);
				else if(m==3)
					cells.setPaddingRight(0.3f*CONVERT);
				else if(m==4)
					cells.setPaddingRight(0.5f*CONVERT);
				else if(m==5)
					cells.setPaddingRight(0.5f*CONVERT);
											
				cells.setBorder(0);
				//cells.setBorderWidth(1);
				cells.setFixedHeight(0.7f * CONVERT);	
				sanpham.addCell(cells);
				
		}
		 
		 count++;
    		System.out.println("count4:"+count);
    		
    		if(arr[1].length() > 40)
			{
				int dodaichuoi = chuoi2.length();
				
				System.out.println("chuoi222:"+chuoi2.length());
					while(dodaichuoi > 0)
					{
						int boiso=dodaichuoi/40;
                		if(boiso > 0)
                		{
                      			chuoi1 = chuoi2.substring(0, 40);
                      			vitri1 = chuoi1.lastIndexOf(" ");
                      			//System.out.println("chuoi 1: " + chuoi1);
                      			//System.out.println("vitri 1: " + vitri1);
                      			chuoicd = chuoi1.substring(0, vitri1);
                      			//System.out.println("chuoi cd: " + chuoicd);
                      			chuoi2 = chuoi2.substring(vitri1 + 1,dodaichuoi );
                      			//System.out.println("chuoi 2: " + chuoi2);
                		}
                		else
                		{
                			chuoicd = chuoi2;
                			chuoi2 = "";
                		}
		                	
                		for(int m = 0; m < arr.length; m++)
                		{			
                			if(m==1)
                				cells = new PdfPCell(new Paragraph(chuoicd, new Font(bf, 11, Font.NORMAL)));
                			else
                				cells = new PdfPCell(new Paragraph("", new Font(bf, 11, Font.NORMAL)));

                			if(m==0||m>=3)
        						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					
        					else
        						cells.setHorizontalAlignment(Element.ALIGN_LEFT);						
        							
                			cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
        					
                			if(m==1 )
								cells.setPaddingLeft(0.2f*CONVERT);
                			else if(m==3)
                				cells.setPaddingRight(0.3f*CONVERT);
							else if(m==4)
								cells.setPaddingRight(0.5f*CONVERT);
							else if(m==5)
								cells.setPaddingRight(0.5f*CONVERT);
        					
                			cells.setBorder(0);
                			//cells.setBorderWidth(1);
                			cells.setFixedHeight(0.7f * CONVERT);	
							sanpham.addCell(cells);
							
                		}
                		dodaichuoi = chuoi2.length();

						count++;
						
						System.out.println("count2:"+count);
					}
				}
    		
    		//DÒNG TỔNG CỘNG TIỀN HÀNG
			sum_tienavat -= tichluy_tienBVAT;
	}
	
	if(CKKM >= 1)
	{
		ResultSet rsCKKM = db.get(INIT_CHIETKHAU_KM); 
		String scheme = "";
		
		double tichluy_tienBVAT = 0;
		
		if(rsCKKM!=null)
		{
			while(rsCKKM.next())
			{
				tichluy_tienBVAT = rsCKKM.getDouble("tichluy_tienBVAT");
				//scheme= rsCKKM.getString("SCHEME");
			}
			rsCKKM.close();
		}
		
		String[] arr = new String[] { "","Trả khuyến mại" , "","", "",DinhDangCANFOCO(formatter1.format(tichluy_tienBVAT))};
		
		// HÀM CẮT CHUỖI
			
		int vitri1= 0;
		String chuoicd= "";
		String chuoi1 = "";
		String chuoi2 = "";
    		
		 for (int m = 0; m < arr.length; m ++){
				// NẾU LÀ TÊN SẢN PHẨM
				if(m==1)
				{
					if(arr[m].length() <= 40)
						cells = new PdfPCell(new Paragraph(arr[m], new Font(bf, 11, Font.NORMAL)));
					else
					{
                		chuoi1 = arr[m].substring(0, 40);
                		//System.out.println("chuoi 1: " + chuoi1);
              			//System.out.println("vitri 1: " + vitri1);
                		vitri1 = chuoi1.lastIndexOf(" ");
                		chuoicd = chuoi1.substring(0, vitri1);
                		//System.out.println("chuoi cd: " + chuoicd);
                		chuoi2 = arr[m].substring(vitri1+1, arr[m].length());
                		//System.out.println("chuoi 2: " + chuoi2);
						cells = new PdfPCell(new Paragraph(chuoicd, new Font(bf, 11, Font.NORMAL)));									
					}
				}
				else
					cells = new PdfPCell(new Paragraph(arr[m], new Font(bf, 11, Font.NORMAL)));					
									
								
				if(m==0||m>2)
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					
				else
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);						
				
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				
				if(m==1 )
					cells.setPaddingLeft(0.2f*CONVERT);
				else if(m==3)
					cells.setPaddingRight(0.3f*CONVERT);
				else if(m==4)
					cells.setPaddingRight(0.5f*CONVERT);
				else if(m==5)
					cells.setPaddingRight(0.5f*CONVERT);
											
				cells.setBorder(0);
				//cells.setBorderWidth(1);
				cells.setFixedHeight(0.7f * CONVERT);	
				sanpham.addCell(cells);
				
		}
		 
		 count++;
    		System.out.println("count4:"+count);
    		
    		if(arr[1].length() > 40)
			{
				int dodaichuoi = chuoi2.length();
				
				System.out.println("chuoi222:"+chuoi2.length());
					while(dodaichuoi > 0)
					{
						int boiso=dodaichuoi/40;
                		if(boiso > 0)
                		{
                      			chuoi1 = chuoi2.substring(0, 40);
                      			vitri1 = chuoi1.lastIndexOf(" ");
                      			//System.out.println("chuoi 1: " + chuoi1);
                      			//System.out.println("vitri 1: " + vitri1);
                      			chuoicd = chuoi1.substring(0, vitri1);
                      			//System.out.println("chuoi cd: " + chuoicd);
                      			chuoi2 = chuoi2.substring(vitri1 + 1,dodaichuoi );
                      			//System.out.println("chuoi 2: " + chuoi2);
                		}
                		else
                		{
                			chuoicd = chuoi2;
                			chuoi2 = "";
                		}
		                	
                		for(int m = 0; m < arr.length; m++)
                		{			
                			if(m==1)
                				cells = new PdfPCell(new Paragraph(chuoicd, new Font(bf, 11, Font.NORMAL)));
                			else
                				cells = new PdfPCell(new Paragraph("", new Font(bf, 11, Font.NORMAL)));

                			if(m==0||m>=3)
        						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					
        					else
        						cells.setHorizontalAlignment(Element.ALIGN_LEFT);						
        							
                			cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
        					
                			if(m==1 )
								cells.setPaddingLeft(0.2f*CONVERT);
                			else if(m==3)
                				cells.setPaddingRight(0.3f*CONVERT);
							else if(m==4)
								cells.setPaddingRight(0.5f*CONVERT);
							else if(m==5)
								cells.setPaddingRight(0.5f*CONVERT);
        					
                			cells.setBorder(0);
                			//cells.setBorderWidth(1);
                			cells.setFixedHeight(0.7f * CONVERT);	
							sanpham.addCell(cells);
							
                		}
                		dodaichuoi = chuoi2.length();

						count++;
						
						System.out.println("count2:"+count);
					}
				}
    		
    		//DÒNG TỔNG CỘNG TIỀN HÀNG
			sum_tienavat -= tichluy_tienBVAT;
	}
		
	if(cktl5>0) // CHIẾT KHẤU THEO HỢP ĐỒNG 
	{
		stt++;
		
		ResultSet rsCKTL= db.get(INIT_CHIETKHAU_TICHLUY);
		
		String Sohopdong = "";
		String Ngayhopdong = "";
		String scheme = "";
		double tichluy_vat = 0;
		double tichluy_tienBVAT = 0;
		double tichluy_tienAVAT = 0;
		double tichluy_tienvat = 0;
		
		if(rsCKTL!=null){		
			while(rsCKTL.next()){						
				 tichluy_vat = rsCKTL.getDouble("tichluy_vat");
				 tichluy_tienBVAT = rsCKTL.getDouble("tichluy_tienBVAT");
				 tichluy_tienAVAT = rsCKTL.getDouble("tichluy_tienAVAT");
				 tichluy_tienvat = rsCKTL.getDouble("tichluy_tienvat");
				 Sohopdong = rsCKTL.getString("SOHOPDONG");
				 Ngayhopdong = rsCKTL.getString("NGAYHOPDONG");	
				 scheme = rsCKTL.getString("SCHEME");	
				 
				 System.out.println(""+Ngayhopdong);
				 String nn = "";
				 if(Ngayhopdong.trim().length()>0)
				 {
					 String [] NgayHD1 = Ngayhopdong.split("-");
					 nn = NgayHD1[2] + "/"+NgayHD1[1]+"/"+NgayHD1[0];
						
				 }
				 				 
				 String[] arr = new String[] { "", "Tiền chiết khấu theo HĐTT số: "+Sohopdong+" Ngày: "+nn , "","", "",DinhDangCANFOCO(formatter1.format(tichluy_tienBVAT))};
				
				// HÀM CẮT CHUỖI
					
				int vitri1= 0;
        		String chuoicd= "";
        		String chuoi1 = "";
        		String chuoi2 = "";
	        		
				 for (int m = 0; m < arr.length; m ++){
						// NẾU LÀ TÊN SẢN PHẨM
						if(m==1)
						{
							if(arr[m].length() <= 40)
								cells = new PdfPCell(new Paragraph(arr[m], new Font(bf, 11, Font.NORMAL)));
							else
							{
		                		chuoi1 = arr[m].substring(0, 40);
		                		//System.out.println("chuoi 1: " + chuoi1);
	                  			//System.out.println("vitri 1: " + vitri1);
		                		vitri1 = chuoi1.lastIndexOf(" ");
		                		chuoicd = chuoi1.substring(0, vitri1);
		                		//System.out.println("chuoi cd: " + chuoicd);
		                		chuoi2 = arr[m].substring(vitri1+1, arr[m].length());
		                		//System.out.println("chuoi 2: " + chuoi2);
								cells = new PdfPCell(new Paragraph(chuoicd, new Font(bf, 11, Font.NORMAL)));									
							}
						}
						else
							cells = new PdfPCell(new Paragraph(arr[m], new Font(bf, 11, Font.NORMAL)));					
											
										
						if(m==0||m>2)
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					
						else
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);						
						
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						
						if(m==1 )
							cells.setPaddingLeft(0.2f*CONVERT);
						else if(m==3)
							cells.setPaddingRight(0.3f*CONVERT);
						else if(m==4)
							cells.setPaddingRight(0.5f*CONVERT);
						else if(m==5)
							cells.setPaddingRight(0.5f*CONVERT);
													
						cells.setBorder(0);
						//cells.setBorderWidth(1);
						cells.setFixedHeight(0.7f * CONVERT);	
						sanpham.addCell(cells);
						
				}
				 
				 count++;
	        		System.out.println("count4:"+count);
	        		
	        		if(arr[1].length() > 40)
					{
						int dodaichuoi = chuoi2.length();
						
						System.out.println("chuoi222:"+chuoi2.length());
							while(dodaichuoi > 0)
							{
								int boiso=dodaichuoi/40;
		                		if(boiso > 0)
		                		{
		                      			chuoi1 = chuoi2.substring(0, 40);
		                      			vitri1 = chuoi1.lastIndexOf(" ");
		                      			//System.out.println("chuoi 1: " + chuoi1);
		                      			//System.out.println("vitri 1: " + vitri1);
		                      			chuoicd = chuoi1.substring(0, vitri1);
		                      			//System.out.println("chuoi cd: " + chuoicd);
		                      			chuoi2 = chuoi2.substring(vitri1 + 1,dodaichuoi );
		                      			//System.out.println("chuoi 2: " + chuoi2);
		                		}
		                		else
		                		{
		                			chuoicd = chuoi2;
		                			chuoi2 = "";
		                		}
				                	
		                		for(int m = 0; m < arr.length; m++)
		                		{			
		                			if(m==1)
		                				cells = new PdfPCell(new Paragraph(chuoicd, new Font(bf, 11, Font.NORMAL)));
		                			else
		                				cells = new PdfPCell(new Paragraph("", new Font(bf, 11, Font.NORMAL)));
	
		                			if(m==0||m>=3)
		        						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					
		        					else
		        						cells.setHorizontalAlignment(Element.ALIGN_LEFT);						
		        							
		                			cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
		        					
		                			if(m==1 )
										cells.setPaddingLeft(0.2f*CONVERT);
		                			else if(m==3)
		                				cells.setPaddingRight(0.3f*CONVERT);
									else if(m==4)
										cells.setPaddingRight(0.5f*CONVERT);
									else if(m==5)
										cells.setPaddingRight(0.5f*CONVERT);
		        					
		                			cells.setBorder(0);
		                			//cells.setBorderWidth(1);
		                			cells.setFixedHeight(0.7f * CONVERT);	
									sanpham.addCell(cells);
									
		                		}
		                		dodaichuoi = chuoi2.length();
	
								count++;
								
								System.out.println("count2:"+count);
							}
						}
	        		
        		//DÒNG TỔNG CỘNG TIỀN HÀNG
				sum_thanhtien -= tichluy_tienBVAT;
				sum_tienvat -= tichluy_tienvat;
				sum_tienavat -= tichluy_tienAVAT;
				
			}
		}
				
	}
	
		// DONG TRONG
		int kk=0;
	
		while(kk < 14-count)
		{
			String[] arr_bosung = new String[] { " ", " ", " ", " ", " "," " };

			for (int i = 0; i < th.length; i++)
			{
				cells = new PdfPCell(new Paragraph(arr_bosung[i], new Font(bf, 9, Font.NORMAL)));
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setFixedHeight(0.7f*CONVERT);
				cells.setBorder(0);
				//cells.setBorderWidth(1);
									
				sanpham.addCell(cells);
			}
			
			kk++;
		}
	
		document.add(sanpham);
	
		//TIỀN HÀNG
		
		PdfPTable tablefooter =new PdfPTable(2);
		tablefooter.setWidthPercentage(100);			

		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);			
		
		hd = new Paragraph("" , new Font(bf, 11, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setFixedHeight(0.8f*CONVERT);		
		//cell.setPaddingTop(0.2f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);
		tablefooter.addCell(cell);	
		
		
		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);				
		
		hd = new Paragraph(DinhDangCANFOCO(formatter1.format(total_tienbvat)) , new Font(bf, 11, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setFixedHeight(0.8f*CONVERT);		
		//cell.setPaddingTop(0.2f*CONVERT);
		cell.setPaddingRight(0.5f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);		

		tablefooter.addCell(cell);
		
		//THUẾ SUẤT - TIỀN THUẾ
		
		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);			
		
		hd = new Paragraph(formatter2.format(thuevat) , new Font(bf, 11, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setFixedHeight(0.8f*CONVERT);	
		cell.setPaddingLeft(7.0f*CONVERT);
		cell.setPaddingTop(-0.1f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);
		tablefooter.addCell(cell);	
		
		
		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);			
		
		hd = new Paragraph(DinhDangCANFOCO(formatter1.format(total_thue)) , new Font(bf, 11, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setFixedHeight(0.8f*CONVERT);		
		cell.setPaddingTop(-0.1f*CONVERT);
		cell.setPaddingRight(0.5f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);
		tablefooter.addCell(cell);	
		
		//TỔNG TIỀN THANH TOÁN
		
		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);			
		
		hd = new Paragraph("" , new Font(bf, 11, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setFixedHeight(0.8f*CONVERT);	
		cell.setPaddingTop(-0.3f*CONVERT);
		cell.setPaddingRight(0.5f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);
		tablefooter.addCell(cell);	
		
		
		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);			
		
		hd = new Paragraph(DinhDangCANFOCO(formatter1.format(total_tienavat)) , new Font(bf, 11, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setFixedHeight(0.8f*CONVERT);	
		cell.setPaddingTop(-0.3f*CONVERT);
		cell.setPaddingRight(0.5f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);
		tablefooter.addCell(cell);	
		
		document.add(tablefooter);
		
		//ĐỌC TIỀN RA CHỮ
		
		// Tien bang chu
		doctienrachu doctien = new doctienrachu();
										
		String tien = doctien.docTien(Math.round(total_tienavat) );
		
	  //Viết hoa ký tự đầu tiên
	    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
	    
		tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
		if(tien.equals("Đồng"))
			 tien="Không Đồng";
		
		PdfPTable table_tien =new PdfPTable(1);
		table_tien.setWidthPercentage(100);
		float[] withstien = {15.0f * CONVERT};
		table_tien.setWidths(withstien);									
			
		cell = new PdfPCell();	
		hd = new Paragraph(tien +"." , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		//cell.setFixedHeight(0.8f*CONVERT);
		cell.setPaddingLeft(3.8f*CONVERT);
		cell.setPaddingTop(-0.5f*CONVERT);
		cell.addElement(hd);
		cell.setBorder(0);						
		table_tien.addCell(cell);
		
		document.add(table_tien);
					

		String BHquaDT = "";
		// BÁN HÀNG QUA ĐIỆN THOẠI
		if(inBHDT>=1)
		{
			BHquaDT = "Bán hàng qua điện thoại";
		}
			PdfPTable tablebhdt =new PdfPTable(2);
			tablebhdt.setWidthPercentage(100);	
			float[] withsfoot = { 4.5f*CONVERT,6.8f*CONVERT};
			tablebhdt.setWidths(withsfoot);
	
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_LEFT);			
			
			hd = new Paragraph("" , new Font(bf, 11, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setFixedHeight(2.8f*CONVERT);
			cell.setColspan(2);
			//cell.setPaddingTop(2.0f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			tablebhdt.addCell(cell);	
		
	
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_LEFT);			
			
			hd = new Paragraph(BHquaDT , new Font(bf, 11, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setFixedHeight(3.2f*CONVERT);
			//cell.setPaddingTop(2.0f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			tablebhdt.addCell(cell);	
			
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_LEFT);			
			
			hd = new Paragraph(nguoichot , new Font(bf, 11, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setFixedHeight(3.2f*CONVERT);
			//cell.setPaddingTop(2.0f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			tablebhdt.addCell(cell);	
			
			document.add(tablebhdt);
		
		
		
		document.close();
		
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}


	private void CreateHd_HAIPHONG_OLD(Document document,ServletOutputStream outstream, IErpHoadontaichinhNPP hdBean) 
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
					" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS KHACHHANG_FK," +
					" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS NPP_FK," +
					" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS HTTT," +
					" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + hdBean.getId() + "' ) as ngayxuathd," +
					" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
					"               else isnull(nguoimua,'') end " +
					"   FROM ERP_HOADONNPP" +
					"   WHERE PK_SEQ= '"+ hdBean.getId() +"' ) AS nguoimua "  +
				    "FROM ERP_DONDATHANGNPP A " +
				    "WHERE A.PK_SEQ IN  " +
				    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + hdBean.getId() + "' )";
		
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
					 "WHERE A.PK_SEQ = '" + hdBean.getId() + "' ";
				
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
			  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+hdBean.getId()+"'";
		}else{
			/*sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			 sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+hdBean.getId()+"'";
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
							"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
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
						cells.setPaddingLeft(-1.6f *CONVERT);
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


	private void CreateHd_HADONG(Document document,ServletOutputStream outstream, IErpHoadontaichinhNPP hdBean) 
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
					" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS KHACHHANG_FK," +
					" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS NPP_FK," +
					" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS HTTT," +
					" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + hdBean.getId() + "' ) as ngayxuathd," +
					" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
					"               else isnull(nguoimua,'') end " +
					"   FROM ERP_HOADONNPP" +
					"   WHERE PK_SEQ= '"+ hdBean.getId() +"' ) AS nguoimua, isnull(chietkhau,0) as chietkhauDH "  +
				    "FROM ERP_DONDATHANGNPP A " +
				    "WHERE A.PK_SEQ IN  " +
				    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + hdBean.getId() + "' )";
		
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
					 "WHERE A.PK_SEQ = '" + hdBean.getId() + "' ";
				
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
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+hdBean.getId()+"'";
		}else{
		/*	sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+hdBean.getId()+"'";
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
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
					"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
				    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
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
						"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
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
							DinhDangCANFOCO(formatter1.format(soLUONG)), DinhDangCANFOCO(formatter.format(dongia)) , DinhDangCANFOCO(formatter1.format(thanhtien)), DinhDangCANFOCO(formatter1.format(vat)), DinhDangCANFOCO(formatter1.format(thueGTGT)), DinhDangCANFOCO(formatter1.format(sotientt)) };
				}
				else{
					arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), rsSP.getString("TEN"), rsSP.getString("solo"),chuoi, rsSP.getString("DONVI"),
						DinhDangCANFOCO(formatter1.format(soLUONG)), DinhDangCANFOCO(formatter.format(dongia)) , DinhDangCANFOCO(formatter1.format(thanhtien)), DinhDangCANFOCO(formatter1.format(vat)), DinhDangCANFOCO(formatter1.format(thueGTGT)), DinhDangCANFOCO(formatter1.format(sotientt+ thueGTGT)) };
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
				String [] arr5 = new String[] {Integer.toString(stt+1),"CK" ,"Chiết khấu sản phẩm " ," ","","", DinhDangCANFOCO(formatter1.format(totalCK)),DinhDangCANFOCO(formatter1.format(vatCK)),
						DinhDangCANFOCO(formatter1.format(totalCK*vatCK/100)) ,DinhDangCANFOCO(formatter1.format( totalCK + totalCK*vatCK/100))};
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
			String[] arr = new String[] {"                             ", DinhDangCANFOCO(formatter1.format(totalTienTruocVAT - totalCK)),"",DinhDangCANFOCO(formatter1.format(totalThueGTGT - (totalCK*vatCK/100) )),DinhDangCANFOCO(formatter1.format(totalTienTruocVAT + totalThueGTGT - totalCK  - (totalCK*vatCK/100) )) };
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

	
	private void CreateHd_QUANGNINH(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP hdBean) throws IOException
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
			" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS KHACHHANG_FK," +
			" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS NPP_FK," +
			" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS HTTT," +
			" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + hdBean.getId() + "' ) as ngayxuathd," +
			" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"               else isnull(nguoimua,'') end " +
			"   FROM ERP_HOADONNPP" +
			"   WHERE PK_SEQ= '"+ hdBean.getId() +"' ) AS nguoimua, isnull(chietkhau,0) as chietkhauDH "  +
		    "FROM ERP_DONDATHANGNPP A " +
		    "WHERE A.PK_SEQ IN  " +
		    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + hdBean.getId() + "' )";

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
		        " where PK_SEQ = (select npp_fk from ERP_HOADONNPP where pk_seq = '"+ hdBean.getId() +"') ";
		   
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
					 "WHERE A.PK_SEQ = '" + hdBean.getId() + "' ";
				
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
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+hdBean.getId()+"'";
		}else{
			/*sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			 sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+hdBean.getId()+"'";
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
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
					"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
				    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
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
							"        ( select isnull(tienvat,0) from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = b.sanpham_fk )  as tienvat, "+
							"		( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	  " +
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
						DinhDangCANFOCO(formatter1.format(soLUONG)), DinhDangCANFOCO(formatter.format(dongia)),DinhDangCANFOCO(formatter1.format(thanhtien)),DinhDangCANFOCO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangCANFOCO(formatter1.format(thueGTGT)),DinhDangCANFOCO(formatter1.format(sotientt)) };


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
			
			String[] arr = new String[] { " ", " " , " ", " "," ", " "," "," ", DinhDangCANFOCO(formatter1.format(totalTienTruocVAT -totalCK )),"",DinhDangCANFOCO( formatter1.format(totalThueGTGT- (totalCK*vatCK/100) )),DinhDangCANFOCO(formatter1.format(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100) ))) };
			
		
	
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
	
	private void CreateHd_DANANG(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP hdBean) throws IOException
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
			
			
			String sql =
			"SELECT  A.PK_SEQ, A.KBH_FK ,(select XUATTAIKHO from NHAPHANPHOI where PK_SEQ = A.NPP_FK) AS KHO," +
			" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS KHACHHANG_FK," +
			" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS NPP_FK," +
			" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS HTTT," +
			" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + hdBean.getId() + "' ) as ngayxuathd," +
			" (SELECT DATEDIFF(DAY, ngaytao, '2015-01-29') FROM ERP_HOADONNPP where pk_seq= '" + hdBean.getId() + "' ) as ktra_ngay," +
			" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"               else isnull(nguoimua,'') end " +
			"   FROM ERP_HOADONNPP" +
			"   WHERE PK_SEQ= '"+ hdBean.getId() +"' ) AS nguoimua," +
			" ISNULL(CHIETKHAU,0) as CHIETKHAUDH "  +
		    "FROM ERP_DONDATHANGNPP A " +
		    "WHERE A.PK_SEQ IN  " +
		    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + hdBean.getId() + "' )";

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
				
				rsCheck.close();
			}
	
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        	" from NHAPHANPHOI" +
		        	" where PK_SEQ = (select npp_fk from ERP_HOADONNPP where pk_seq = '"+ hdBean.getId() +"') ";
		   
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
					 "WHERE A.PK_SEQ = '" + hdBean.getId() + "' ";
				
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
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+hdBean.getId()+"'";
		}else{
			/*sql = " select  TEN ,DIACHI, MASOTHUE  "+
				  " from NHAPHANPHOI " +
				  " where PK_SEQ = '"+ npp_fk +"'  ";*/
			 sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+hdBean.getId()+"'";
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
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
					"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
				    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
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
						"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	 \n" +
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
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("TEN"), rsSP.getString("SoLo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangCANFOCO(formatter1.format(soLUONG)), DinhDangCANFOCO(formatter.format(dongia)),
						DinhDangCANFOCO(formatter1.format(thanhtien)),DinhDangCANFOCO(formatter1.format(rsSP.getDouble("thuevat"))),
						DinhDangCANFOCO(formatter1.format(thueGTGT)),DinhDangCANFOCO(formatter1.format(sotientt)) };


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
			
			String[] arr = new String[] { " ", " " , " ", " "," ", " "," ", DinhDangCANFOCO(formatter1.format(totalTienTruocVAT -totalCK )),
					                      " ",DinhDangCANFOCO( formatter1.format(totalThueGTGT- (totalCK*vatCK/100) )),DinhDangCANFOCO(formatter1.format(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100) ))) };
			
		
	
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
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
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
	
	
	private String CapnhatTT(String id, String print) 
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
			System.out.println(query);
			ResultSet rs = db.get(query);
			if(rs!= null)
			{
				while(rs.next())
				{
					trangthai = rs.getString("trangthai");
				}rs.close();
			}
			
			System.out.println("print:"+print);
			if(print.equals("1"))
			{
				if(!trangthai.equals("3") && !trangthai.equals("5"))
				{
					// Cập nhật trạng thái Đã in
					query = "update ERP_HOADONNPP set trangthai = '4' where pk_seq = '" + id + "' ";
					System.out.println(query);
					if(!db.update(query))
					{
						msg = "Không thể cập nhật ERP_HOADONNPP " + query;
						db.getConnection().rollback();
						return msg;
					}
				
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

	private void CreateHd(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP hdBean) throws IOException
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
					" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS KHACHHANG_FK," +
					" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS NPP_FK," +
					" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS HTTT," +
					" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + hdBean.getId() + "' ) as ngayxuathd," +
					" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
					"               else isnull(nguoimua,'') end " +
					"   FROM ERP_HOADONNPP" +
					"   WHERE PK_SEQ= '"+ hdBean.getId() +"' ) AS nguoimua,isnull(chietkhau,0) as chietkhauDH "  +
				    "FROM ERP_DONDATHANGNPP A " +
				    "WHERE A.PK_SEQ IN  " +
				    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + hdBean.getId() + "' )";
		
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
					 "WHERE A.PK_SEQ = '" + hdBean.getId() + "' ";
				
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
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+hdBean.getId()+"'";
		}else{
			/*sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			 sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+hdBean.getId()+"'";
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
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
					"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
				    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
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
							"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
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
						DinhDangCANFOCO(formatter.format(soLUONG)), DinhDangCANFOCO(formatter4.format(dongia)) , DinhDangCANFOCO(formatter.format(thanhtien)), DinhDangCANFOCO(formatter.format(vat)), DinhDangCANFOCO(formatter.format(thueGTGT)), DinhDangCANFOCO(formatter.format(sotientt)) };


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
				String [] arr5 = new String[] {Integer.toString(stt+1),"CK" ,"Chiết khấu sản phẩm " ," ","","", DinhDangCANFOCO(formatter.format(totalCK)),DinhDangCANFOCO(formatter.format(vatCK)),
						DinhDangCANFOCO(formatter.format(totalCK*vatCK/100)) ,DinhDangCANFOCO(formatter.format( totalCK + totalCK*vatCK/100))};
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
			
			String[] arr = new String[] {"                             ", DinhDangCANFOCO(formatter1.format(totalTienTruocVAT -totalCK )),"",DinhDangCANFOCO( formatter1.format(totalThueGTGT- (totalCK*vatCK/100) )),DinhDangCANFOCO(formatter1.format(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100) ))) };
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
	 * @param hdBean
	 * @throws IOException
	 */
	private void CreateHd_QUANGNGAI(Document document, ServletOutputStream outstream, IErpHoadontaichinhNPP hdBean) throws IOException
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
			" (SELECT KHACHHANG_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS KHACHHANG_FK," +
			" (SELECT NPP_DAT_FK FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS NPP_FK," +
			" (SELECT HINHTHUCTT FROM ERP_HOADONNPP WHERE PK_SEQ= '" + hdBean.getId() + "'  ) AS HTTT," +
			" (SELECT ngayxuathd FROM ERP_HOADONNPP where pk_seq= '" + hdBean.getId() + "' ) as ngayxuathd," +
			" ( SELECT case when khachhang_fk is not null and nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"               else isnull(nguoimua,'') end " +
			"   FROM ERP_HOADONNPP" +
			"   WHERE PK_SEQ= '"+ hdBean.getId() +"' ) AS nguoimua, isnull(chietkhau,0) as chietkhauDH "  +
		    "FROM ERP_DONDATHANGNPP A " +
		    "WHERE A.PK_SEQ IN  " +
		    "(select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + hdBean.getId() + "' )";

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
		        " where PK_SEQ = (select npp_fk from ERP_HOADONNPP where pk_seq = '"+ hdBean.getId() +"') ";
		   
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
					 "WHERE A.PK_SEQ = '" + hdBean.getId() + "' ";
				
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
		  	  " FROM   ERP_HOADONNPP WHERE KHACHHANG_FK ='"+khId+"' and PK_SEQ = '"+hdBean.getId()+"'";
		}else{
		/*	sql = " select  TEN ,DIACHI, MASOTHUE  "+
		    " from NHAPHANPHOI " +
		    " where PK_SEQ = '"+ npp_fk +"'  ";*/
			 sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
		  	  " FROM   ERP_HOADONNPP WHERE NPP_DAT_FK ='"+npp_fk+"' and PK_SEQ = '"+hdBean.getId()+"'";
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
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia,  	\n" +
					"	CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN \n"+
				    "  	    ( select  case when  ISNULL(TIENVAT,0) = 0 then 0 else ROUND((round(SOLUONG*DONGIA, 0)*VAT/100), 0) - ISNULL(TIENVAT,0) end from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = c.pk_seq )  \n"+
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
						"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdBean.getId() + "' and sanpham_fk = b.sanpham_fk ) as dongia  	 " +
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
						DinhDangCANFOCO(formatter1.format(soLUONG)), DinhDangCANFOCO(formatter.format(dongia)),
						DinhDangCANFOCO(formatter1.format(thanhtien)),DinhDangCANFOCO(formatter1.format(rsSP.getDouble("thuevat"))),
						DinhDangCANFOCO(formatter1.format(thueGTGT)),DinhDangCANFOCO(formatter1.format(sotientt)) };


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
			
			String[] arr = new String[] { " ", " " , " ", " "," ", " "," ", DinhDangCANFOCO(formatter1.format(totalTienTruocVAT -totalCK )),
					                      " ",DinhDangCANFOCO( formatter1.format(totalThueGTGT- (totalCK*vatCK/100) )),DinhDangCANFOCO(formatter1.format(Math.round(totalSotienTT - ( totalCK + totalCK*vatCK/100) ))) };
			
		
	
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
	

/*	public static void main(String[] arg)
	{
		ErpHoadontaichinhNPPPdfSvl hd = new ErpHoadontaichinhNPPPdfSvl();
		
		System.out.println(hd.DinhDangCANFOCO("12,000.56"));
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
	
	private String DinhDangCANFOCO(String sotien)
	 {
	  sotien = sotien.replaceAll("\\.", "_");
	  sotien = sotien.replaceAll(",", "\\.");
	  sotien = sotien.replaceAll("_", ",");
	  
	  return sotien;
	 }
	
}
