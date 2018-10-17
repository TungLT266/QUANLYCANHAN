package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.distributor.beans.xuatkho.IErpXuathoadonKM;
import geso.traphaco.distributor.beans.xuatkho.imp.*;
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
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import jxl.CellView;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ErpXuathoadonKMPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpXuathoadonKMPdfSvl()
	{
		super();
	}
	
	float CONVERT = 28.346457f; //
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
		IErpXuathoadonKM hdBean = new ErpXuathoadonKM(id);
		hdBean.setUserId(userId);
		
		if (querystring.indexOf("pdf") > 0)
		{
			//hdBean.initPdf();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=HoadonKMNPP.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			
			//LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI)
			//LOẠI XUẤT HD: 0 - XUẤT CHO ĐỐI TÁC; 1 - XUẤT CHO KHÁCH HÀNG ETC, 2- OTC , 3 - BÁN NỘI BỘ
			
			String INIT_THONGTINKH =
				" select HD.NGAYHOADON NGAYXUATHD, ISNULL(HD.ghichu, '') as ghichu, \n"+
				"  		HD.npp_dat_fk khachhang_fk, ISNULL(kyhieu,'') KYHIEU, ISNULL(sohoadon,'') SOHOADON, '' hinhthuctt , \n"+
				"  		'' nguoimua, 0 innguoimua,  0 as tongtienbvat,  0 as tongtienavat \n" +
				"   	,  0 as vat, 0 as chietkhau,  \n"+
				"		isnull(NPP.TEN,'') as donvi,  \n"+
				"		isnull(NPP.MASOTHUE,'') as MASOTHUE, \n"+
				"		isnull(NPP.DIACHI,'') as DIACHI,  \n"+
				"       isnull((SELECT  top(1)  part  FROM SplitString (isnull(NPP.TENXHD,''), '__') WHERE part!= ' '  order by id desc), '') \n"+
				"from 	ERP_XUATHOADONKM HD INNER JOIN NHAPHANPHOI NPP ON HD.NPP_DAT_FK = NPP.PK_SEQ \n"+
				"where  HD.pk_seq = '"+ hdBean.getId()  +"'";
		   System.out.println("[INIT_THONGTINKH]"+INIT_THONGTINKH);
		  
		   // MẪU MỚI 		   
		   // OTC + NPP: không lẻ
		   // ETC : số lẻ
		   
		  String INIT_SANPHAM =
			   " SELECT distinct xuathoadonkm_fk, B.MA, N''+ isnull((SELECT  top(1)  part  FROM SplitString (isnull(b.TENXUATHOADON,''), '__') WHERE part!= ' '  order by id desc), '') TEN, A.SOLO, DV.DIENGIAI DONVI, A.SOLUONGXUAT, 0 DONGIA, 0 THANHTIEN, 0 THUEVAT, \n"+ 
			   "       	0 TIENVAT, 0 CHIETKHAU, A.NGAYHETHAN, A.SOLUONGXUAT SOLUONG \n"+
			   "FROM ERP_XUATHOADONKM_SANPHAM_CHITIET A INNER JOIN SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ \n"+ 
			   "INNER JOIN DONVIDOLUONG DV ON B.DVDL_FK = DV.PK_SEQ \n"+
			   "WHERE xuathoadonkm_fk = '"+ hdBean.getId()  +"' ";
		  		   
		  System.out.println("[INIT_SANPHAM_KM]"+INIT_SANPHAM);		
		   
		  this.CreateHd_CNHOCHIMINH_NEW(document, outstream, hdBean, INIT_THONGTINKH, INIT_SANPHAM );
						
			String msg = this.CapnhatTT(id);
			hdBean.setMsg(msg);
		} 
		else //IN BANG KE EXCEL
		{
			id = util.antiSQLInspection(request.getParameter("excel"));
			
			String scheme = "";
			String diengiai = "";
			String tungay = "";
			String denngay = "";
			String tendaily = "";
			String diachi = "";
			String masanpham = "";
			
	    	String query =   "select c.SCHEME, c.DIENGIAI, c.TUNGAY, c.DENNGAY, d.TEN, d.DIACHI,  "+
			    			 "	(  "+
			    			 "		select COUNT( distinct SANPHAM_FK )  "+
			    			 "		from DIEUKIENKM_SANPHAM  "+
			    			 "	    where DIEUKIENKHUYENMAI_FK in ( select DKKHUYENMAI_FK from CTKM_DKKM where CTKHUYENMAI_FK in ( select ctkm_fk from ERP_XUATHOADONKM_CTKM where xuathoadonkm_fk = a.PK_SEQ ) ) "+
			    			 "	) as soSPKM, "+
			    			 /*"	STUFF         "+
			    			 "	(         "+
			    			 "		(        "+
			    			 "			select TOP 100 PERCENT ' , ' + bb.MA_FAST  "+
			    			 "			from DIEUKIENKM_SANPHAM aa inner join SANPHAM bb on bb.PK_SEQ = aa.SANPHAM_FK      "+
			    			 "			where DIEUKIENKHUYENMAI_FK in ( select DKKHUYENMAI_FK from CTKM_DKKM where CTKHUYENMAI_FK in ( select ctkm_fk from ERP_XUATHOADONKM_CTKM where xuathoadonkm_fk = a.PK_SEQ ) )      "+
			    			 "			FOR XML PATH('')           "+
			    			 "		 ), 1, 2, ''        "+
			    			 "	) AS maSPKM "+*/
			    			 "	STUFF         "+
			    			 "	(         "+
			    			 "		(        "+
			    			 "			select TOP 100 PERCENT ' , ' + bb.MA_FAST  "+
			    			 "			from TRAKHUYENMAI_SANPHAM aa inner join SANPHAM bb on bb.PK_SEQ = aa.SANPHAM_FK      "+
			    			 "			where TRAKHUYENMAI_FK in ( select TRAKHUYENMAI_FK from CTKM_TRAKM where CTKHUYENMAI_FK in ( select ctkm_fk from ERP_XUATHOADONKM_CTKM where xuathoadonkm_fk = a.PK_SEQ ) )      "+
			    			 "			FOR XML PATH('')           "+
			    			 "		 ), 1, 2, ''        "+
			    			 "	) AS maSPKM "+
			    			 "from ERP_XUATHOADONKM a inner join ERP_XUATHOADONKM_CTKM b on a.PK_SEQ = b.xuathoadonkm_fk "+
			    			 "	inner join CTKHUYENMAI c on b.ctkm_fk = c.PK_SEQ "+
			    			 "	inner join NHAPHANPHOI d on a.NPP_DAT_FK = d.PK_SEQ "+
			    			 "where a.PK_SEQ = '" + id + "' ";
	    	dbutils db = new dbutils();
	    	ResultSet rs = db.get(query);
	    	if( rs != null )
	    	{
	    		try 
	    		{
					if( rs.next() )
					{
						scheme = rs.getString("SCHEME");
						diengiai = rs.getString("DIENGIAI");
						tungay = rs.getString("TUNGAY");
						denngay = rs.getString("DENNGAY");
						tendaily = rs.getString("TEN");
						diachi = rs.getString("DIACHI");
						masanpham = rs.getString("maSPKM");
					}
					rs.close();
				} 
	    		catch (Exception e) 
	    		{
					e.printStackTrace();
				}
	    	}
	    	
	    	System.out.println("::: LAY BC: " + query );
	    	
	    	//TINH SP DUOC HUONG
	    	//CHUYEN SP DUOC HUONG THANH COT
	    	String[] _masanpham = masanpham.split(",");
	    	String strSPKM = "";
	    	for( int i = 0; i < _masanpham.length; i++ )
	    	{
	    		strSPKM += " ( select SUM(SOLUONG) from DONHANG_CTKM_TRAKM a inner join DONHANG b on a.DONHANGID = b.PK_SEQ " +
	    				   "   where a.spMA in ( select ma from SANPHAM where ma_fast = '" + _masanpham[i].trim() + "' ) and b.PK_SEQ in ( select donhang_fk from ERP_XUATHOADONKM_DONHANG where xuathoadonkm_fk = '" + id + "' ) and b.KHACHHANG_FK = DT.PK_SEQ ) as N'" + _masanpham[i].trim() + "' ";
	    		if( i < _masanpham.length - 1 )
	    			strSPKM += " , ";
	    	}
	    	
	    	query =  "select DT.TEN, DT.DIACHI, DT.MASOTHUE, DT.loaikhachhang,  " + strSPKM +
	    			 "from "+
	    			 "( "+
	    			 "	select distinct c.PK_SEQ, c.TEN, c.DIACHI, c.MASOTHUE, ISNULL(d.ten, '') as loaikhachhang "+
	    			 "	from ERP_XUATHOADONKM_DONHANG a inner join DONHANG b on a.donhang_fk = b.PK_SEQ "+
	    			 "		inner join KHACHHANG c on b.KHACHHANG_FK = c.PK_SEQ "+
	    			 "		left join LOAIKHACHHANG d on c.LCH_FK = d.pk_seq "+
	    			 "	where a.xuathoadonkm_fk = '" + id + "' "+
	    			 ") "+
	    			 "DT ";
	    	
	    	System.out.println(" :::: DATA: " + query );
	    	this.XuatExcel(response, query, scheme, diengiai, tungay, denngay, tendaily, diachi, masanpham );
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
	
	private void XuatExcel(HttpServletResponse response, String query, 
				String scheme, String diengiai,  String tungay, String denngay, String tendaily, String diachi, String maSPKM) throws IOException
	{
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BangKeKhuyenMai.xls");
			
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Sheet1", 0);
			
			//TIEU DE 
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 14);
			cellFont.setColour(Colour.BLACK);
			cellFont.setBoldStyle(WritableFont.BOLD);
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			sheet.addCell(new Label(1, 0, "CTY CỔ PHẦN DƯỢC PHA NAM ", cellFormat));
			
			sheet.addCell(new Label(1, 2, "BẢNG TỔNG HỢP QUYẾT TOÁN CHƯƠNG TRÌNH " + scheme, cellFormat));
			//COT BAT DAU, DONG BAT DAU, COT KET THUC, DONG KET THUC
			sheet.mergeCells(1, 2, 7, 2);
			
			sheet.addCell(new Label(1, 3, "Tên đại lý: " + tendaily ));
			sheet.mergeCells(1, 3, 7, 3);
			
			sheet.addCell(new Label(1, 4, "Địa chỉ đại lý: " + diachi));
			sheet.mergeCells(1, 4, 7, 4);
			
			sheet.addCell(new Label(1, 5, "Thời gian từ " + tungay + " đến " + denngay));
			sheet.mergeCells(1, 5, 7, 5);
			
			sheet.addCell(new Label(1, 6, "Nội dung: " + diengiai));
			sheet.mergeCells(1, 6, 7, 6);
			
			cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setBoldStyle(WritableFont.BOLD);

			cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(jxl.format.Colour.GRAY_25);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setVerticalAlignment( VerticalAlignment.CENTRE );
			cellFormat.setAlignment( Alignment.CENTRE );

			sheet.addCell(new Label(0, 8, "STT", cellFormat));
			sheet.mergeCells(0, 8, 0, 9);
			
			sheet.addCell(new Label(1, 8, "Nhà thuốc", cellFormat));
			sheet.mergeCells(1, 8, 1, 9);
			
			sheet.addCell(new Label(2, 8, "Địa chỉ", cellFormat));
			sheet.mergeCells(2, 8, 2, 9);
			
			sheet.addCell(new Label(3, 8, "MST", cellFormat));
			sheet.mergeCells(3, 8, 3, 9);
			
			sheet.addCell(new Label(4, 8, "Phân loại khách hàng", cellFormat));
			sheet.mergeCells(4, 8, 4, 9);
			
			int sospKM = maSPKM.split(",").length;
			
			sheet.addCell(new Label(5, 8, "Sản phẩm khuyến mại", cellFormat));
			sheet.mergeCells(5, 8, 4 + sospKM, 8);
			
			sheet.addCell(new Label(5 + sospKM, 8, "Ký nhận", cellFormat));
			sheet.mergeCells(5 + sospKM, 8, 5 + sospKM, 9);
			
			
			//THEM MA SP KHUYEN MAI THEO COT
			String[] _hangkhuyenmai = maSPKM.split(",");
			int i = 0;
			while( i < sospKM )
			{
				sheet.addCell(new Label( (5 + i) , 9, _hangkhuyenmai[i], cellFormat));
				i++;
			}
			
			
			// set style to cell data
			WritableCellFormat cellFormat2 = new WritableCellFormat();

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cellFormat3 = new WritableCellFormat();

			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setAlignment( Alignment.RIGHT );
			
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			int j = 10;
			int stt = 1;
			while (rs.next())
			{
				Label label;

				label = new Label(0, j, Integer.toString(stt), cellFormat2);
				sheet.addCell(label);

				label = new Label(1, j, rs.getString("TEN"), cellFormat2);
				sheet.addCell(label);

				label = new Label(2, j, rs.getString("DIACHI"), cellFormat2);
				sheet.addCell(label);

				label = new Label(3, j, rs.getString("MASOTHUE"), cellFormat2);
				sheet.addCell(label);

				label = new Label(4, j, rs.getString("LOAIKHACHHANG"), cellFormat2);
				sheet.addCell(label);
				
				for(int k = 0; k < sospKM; k++ )
				{
					label = new Label(5 + k, j, rs.getString( _hangkhuyenmai[k].trim() ), cellFormat3);
					sheet.addCell(label);
				}
				
				label = new Label(5 + sospKM, j, " ", cellFormat2);
				sheet.addCell(label);
				
				j++;
				stt++;
			}
			
			for(int x = 0; x <= 6; x++)
			{
			    CellView cell = sheet.getColumnView(x);
			    
			    if( x == 1 )
			    {
			    	cell.setSize(7000);
			    }
			    else if( x == 2 )
			    {
			    	cell.setSize(12000);
			    }
			    else
			    {
			    	cell.setAutosize(true);
			    }
			    
			    sheet.setColumnView(x, cell);
			}
			
			w.write();
			w.close();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		}
	}
	

	private void CreateHd_CNHOCHIMINH_NEW(Document document,ServletOutputStream outstream, IErpXuathoadonKM hdBean, String INIT_KHACHHANG, String INIT_SANPHAM)
	{
		
		try
		{			
			dbutils db = new dbutils();
				
			String donvi = "";
			String masothue = "";
			String diachi = "";
			String HTTT = "";
			String sotaikhoan = "";
			String ngayxuathd ="";
			
		   ResultSet rsINFO = db.get(INIT_KHACHHANG);
		   if(rsINFO.next())
		   {
			   donvi = rsINFO.getString("donvi");
			   masothue = rsINFO.getString("MASOTHUE");
			   diachi = rsINFO.getString("DIACHI");
			   //sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   HTTT = rsINFO.getString("hinhthuctt");
			   ngayxuathd = rsINFO.getString("NGAYXUATHD");
			   rsINFO.close();
		   }
		   
		   NumberFormat formatter = new DecimalFormat("#,###,###.###");
		   NumberFormat formatter1 = new DecimalFormat("#,###,###.###");
		   NumberFormat formatter2 = new DecimalFormat("#,###,###");
		   //document.setPageSize(PageSize.A4.rotate()); //CANH HÓA ĐƠN THEO CHIỀU DỌC
		   document.setMargins(0.0f*CONVERT, 1.5f*CONVERT, 4.1f*CONVERT, 0.0f*CONVERT); // L,R, T, B
		   PdfWriter writer = PdfWriter.getInstance(document, outstream);
		   document.open() ;
		   
		   BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
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
			
			hd = new Paragraph("Bảng kê KM" , new Font(bf, 12, Font.NORMAL));
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
			cell.setPaddingLeft(1.4f*CONVERT);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);		
	
			tableheader.addCell(cell);
			
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
			//MÃ SỐ THUẾ
			
			//CẮT MÃ SỐ THUẾ
			String strMST_01[] = masothue.split("");
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
			
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_LEFT);			
			
			hd = new Paragraph(diachi , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setPaddingLeft(1.3f*CONVERT);
			cell.setPaddingTop(-0.3f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);		
	
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
			
			float[] withsKM = { 0.7f*CONVERT,6.8f*CONVERT, 1.6f*CONVERT, 2.0f*CONVERT, 3.0f*CONVERT, 3.0f*CONVERT };
			sanpham.setWidths(withsKM);
			
			// PHẦN 
			ResultSet rsSP = db.get(INIT_SANPHAM);
						
			PdfPCell cells = new PdfPCell();
		
			int stt = 0;
			String tensp = "", solo = "", handung = "", DVT = "" ;
			double soluong = 0, dongia = 0, thanhtien = 0, thuevat = 0, tienvat = 0, tienavat = 0, isSpKM = 0, tienck = 0 ;
			double sum_thanhtien = 0, sum_tienvat = 0, sum_tienavat = 0, sum_tienck = 0;
			double sum_thanhtien_5 = 0, sum_thanhtien_10 = 0, sum_tienvat_5 = 0, sum_tienvat_10 = 0, sum_tienavat_5 = 0, sum_tienavat_10 = 0, sum_tienck_5 = 0, sum_tienck_10 = 0 ; 
		
			int count = 0;
			
			if(rsSP!=null){				
				while(rsSP.next()){
					
					stt++;
					tensp = rsSP.getString("TEN");
					solo = rsSP.getString("SOLO");
					handung = rsSP.getString("NGAYHETHAN");
					DVT = rsSP.getString("DONVI");
					soluong = rsSP.getDouble("SOLUONG");
					dongia = rsSP.getDouble("DONGIA");
					thanhtien = rsSP.getDouble("THANHTIEN");
					thuevat = rsSP.getDouble("THUEVAT");					
					tienck = rsSP.getDouble("CHIETKHAU");	
					tienvat = rsSP.getDouble("TIENVAT");
									
					System.out.println(tensp);
					tienavat = Math.round(thanhtien - tienck + tienvat);
					
					//DÒNG TỔNG CỘNG TIỀN HÀNG
					sum_thanhtien += thanhtien;
					sum_tienvat += tienvat;
					sum_tienavat += tienavat;
					sum_tienck += tienck;
					
					//TỔNG CỘNG THUẾ SUẤT 5%
					if(thuevat==5)
					{
						sum_thanhtien_5 += thanhtien;
						sum_tienvat_5 += tienvat;
						sum_tienavat_5 += tienavat;
						sum_tienck_5 += tienck;
					}
					
					if(thuevat==10)
					{
						sum_thanhtien_10 += thanhtien;
						sum_tienvat_10 += tienvat;
						sum_tienavat_10 += tienavat;
						sum_tienck_10 += tienck;
					}
					
					// HÀM CẮT CHUỖI
					
					int vitri1= 0;
	        		String chuoicd= "";
	        		String chuoi1 = "";
	        		String chuoi2 = "";
	        		
	        		String[] arr = null;
	        		        		
	        		arr = new String[] { Integer.toString(stt), tensp ,  DVT,DinhDangTraphacoERP(formatter1.format(soluong)), DinhDangTraphacoERP(formatter2.format(dongia)),DinhDangTraphacoERP(formatter2.format(thanhtien)) };
	        		
					for (int m = 0; m < arr.length; m ++){
						// NẾU LÀ TÊN SẢN PHẨM
						System.out.println(arr[m]);
						if(m==1)
						{
							if(arr[m].length() <= 40)
								cells = new PdfPCell(new Paragraph(arr[m], new Font(bf, 8, Font.NORMAL)));
							else
							{
		                		chuoi1 = arr[m].substring(0, 40);
		                		vitri1 = chuoi1.lastIndexOf(" ");
		                		chuoicd = chuoi1.substring(0, vitri1);
		                		chuoi2 = arr[m].substring(vitri1+1, arr[m].length());
								cells = new PdfPCell(new Paragraph(chuoicd, new Font(bf, 8, Font.NORMAL)));									
							}
						}
						else
							cells = new PdfPCell(new Paragraph(arr[m], new Font(bf, 8, Font.NORMAL)));				
											
										
						if(m==0||m>2)
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					
						else
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);						
						
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						
						if(m==1 || m==2)
							cells.setPaddingLeft(0.2f*CONVERT);
						//else if(m==4)
							//cells.setPaddingRight(0.5f*CONVERT);
						//else if(m==5)
							//cells.setPaddingRight(0.5f*CONVERT);
						
						cells.setBorder(0);
						//cells.setBorderWidth(1);
						cells.setFixedHeight(0.7f * CONVERT);	
						sanpham.addCell(cells);
						
					}
					
					count++;	
									
					if(arr[1].length() > 40)
					{
						int dodaichuoi = chuoi2.length();
						
							while(dodaichuoi > 0)
							{
								int boiso=dodaichuoi/40;
		                		if(boiso > 0)
		                		{
		                      			chuoi1 = chuoi2.substring(0, 40);
		                      			vitri1 = chuoi1.lastIndexOf(" ");
		                      			chuoicd = chuoi1.substring(0, vitri1);
		                      			chuoi2 = chuoi2.substring(vitri1 + 1,dodaichuoi );
		                		}
		                		else
		                		{
		                			chuoicd = chuoi2;
		                			chuoi2 = "";
		                		}
				                	
		                		for(int m = 0; m < arr.length; m++)
		                		{			
		                			if(m==1)
		                				cells = new PdfPCell(new Paragraph(chuoicd, new Font(bf, 8, Font.NORMAL)));
		                			else
		                				cells = new PdfPCell(new Paragraph("", new Font(bf, 8, Font.NORMAL)));
	
		                			if(m==0||m>=3)
		        						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					
		        					else
		        						cells.setHorizontalAlignment(Element.ALIGN_LEFT);						
		        							
		                			cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
		        					
		                			if(m==1 || m==2)
		    							cells.setPaddingLeft(0.2f*CONVERT);
		    						//else if(m==4)
		    							//cells.setPaddingRight(0.5f*CONVERT);
		    						//else if(m==5)
		    							//cells.setPaddingRight(0.5f*CONVERT);
		        					
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
						
					}
					rsSP.close();
					
				}
					
				// DONG TRONG
				int kk=0;
				
				System.out.println("count:"+count);
				while(kk < 13-count)
				{
					String[] arr_bosung = new String[] { " ", " ", " ", " ", " "," " };
		
					for (int i = 0; i < th.length; i++)
					{
						cells = new PdfPCell(new Paragraph(arr_bosung[i], new Font(bf, 8, Font.NORMAL)));
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						cells.setFixedHeight(0.8f*CONVERT);
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
		cell.setPaddingTop(-0.2f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);
		tablefooter.addCell(cell);	
		
		
		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);			
		
		hd = new Paragraph(DinhDangTraphacoERP(formatter1.format(sum_thanhtien)) , new Font(bf, 11, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setFixedHeight(0.8f*CONVERT);		
		cell.setPaddingTop(-0.2f*CONVERT);
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
		cell.setPaddingTop(-0.2f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);
		tablefooter.addCell(cell);	
		
		
		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);			
		
		hd = new Paragraph(DinhDangTraphacoERP(formatter1.format(sum_tienvat)) , new Font(bf, 11, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setFixedHeight(0.8f*CONVERT);		
		cell.setPaddingTop(-0.2f*CONVERT);
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
		cell.setPaddingTop(-0.5f*CONVERT);
		cell.setPaddingRight(0.5f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);
		tablefooter.addCell(cell);	
		
		
		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);			
		
		hd = new Paragraph(DinhDangTraphacoERP(formatter1.format(sum_tienavat)) , new Font(bf, 11, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setFixedHeight(0.8f*CONVERT);	
		cell.setPaddingTop(-0.5f*CONVERT);
		cell.setPaddingRight(0.5f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);
		tablefooter.addCell(cell);	
		
		document.add(tablefooter);
		
		//ĐỌC TIỀN RA CHỮ
		
		// Tien bang chu
		doctienrachu doctien = new doctienrachu();
										
		String tien = doctien.docTien(Math.round(sum_tienavat) );
		
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
		cell.setFixedHeight(0.8f*CONVERT);
		cell.setPaddingLeft(3.8f*CONVERT);
		cell.setPaddingTop(-0.7f*CONVERT);
		cell.addElement(hd);
		cell.setBorder(0);						
		table_tien.addCell(cell);
		
		document.add(table_tien);
		
		document.close();
		} 
		catch (Exception e)
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


	private String DinhDangTraphacoERP(String sotien)
	 {
	  sotien = sotien.replaceAll("\\.", "_");
	  sotien = sotien.replaceAll(",", "\\.");
	  sotien = sotien.replaceAll("_", ",");
	  
	  return sotien;
	 }
	
}
