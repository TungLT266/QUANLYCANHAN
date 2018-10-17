package geso.traphaco.erp.servlets.hoadon;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.center.beans.hoadontaichinh.IErpHoadontaichinh;
import geso.traphaco.center.beans.hoadontaichinh.imp.SanphamHoadontaichinhObj;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadontravencc.IErpHoaDon;
import geso.traphaco.erp.beans.hoadontravencc.imp.ErpHoaDon;
import geso.traphaco.erp.beans.hoadontravencc.IErpHoaDon_SP;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.DinhDang;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.org.apache.regexp.internal.RESyntaxException;

public class ErpXuatHoaDonTraVeNccPdf_Svl  extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final float CONVERT = 28.3464f;
	public ErpXuatHoaDonTraVeNccPdf_Svl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		String ddhId = util.getId(querystring);
		
		IErpHoaDon dhBean = new ErpHoaDon(ddhId);
		
		System.out.println("ddhID:"+ddhId);
		String userId = util.getUserId(querystring);
		dhBean.setUserId(userId);
		
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", " inline; filename=HoaDonTraVeNCC.pdf");
		
		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();	
		
		dhBean.initdisplay(ddhId);
		//CreateHoaDonTraVe_Pdf( document,  outstream,  dhBean,ddhId);
		CreatePxk_2017_jasper( document,  outstream, request,  response ,  dhBean,ddhId) ;
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

	private void CreateHoaDonTraVe_Pdf(Document document, ServletOutputStream outstream, IErpHoaDon pxkBean,String hdId) throws IOException
	{
		try
		{

			dbutils db = new dbutils();
			
			//LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI)
			String query =
				" SELECT isnull(hd.nguoimua,'') as nguoimua, HD.SOHOADON, HD.KYHIEU, HD.NGAYXUATHD, HD.HINHTHUCTT, ISNULL(KH.MASOTHUE,'') AS MASOTHUE, KH.DIACHI, \n"+ 
				"        KH.TEN, ISNULL(HD.TIENCKTHUONGMAI,0) TIENCKTHUONGMAI,  HD.VAT, HD.TONGTIENAVAT,KH.TEN_NGUOIMUAHANG AS TENGNUOIMUA \n"+ 
				" FROM ERP_HOADON HD INNER JOIN ERP_NHACUNGCAP KH ON KH.PK_SEQ=HD.NCC_FK \n"+ 
				" WHERE HD.PK_SEQ ='"+hdId+"' \n";
			
			System.out.println(query);
			
			String khoxuat="";
			String SOHOADON="";
			String KYHIEU="";
			String NGAYXUATHD ="";
			String HINHTHUCTT="";
			String MASOTHUE="";
			String DIACHI="";
			String NGUOIMUA="";
			String TENKH="";
			double TIENCK=0;
			double VAT=0;
			double TONGTIENAVAT=0;
			
			System.out.println("THONGTINNPP__:"+query);
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					SOHOADON = rs.getString("SOHOADON");
					KYHIEU = rs.getString("KYHIEU");
					NGAYXUATHD = rs.getString("NGAYXUATHD");
					HINHTHUCTT = rs.getString("HINHTHUCTT");
					MASOTHUE = rs.getString("MASOTHUE");
					DIACHI = rs.getString("DIACHI");
					NGUOIMUA = rs.getString("NGUOIMUA");
					//NGUOIMUA = rs.getString("TENGNUOIMUA");
					TENKH = rs.getString("TEN");
					//TIENCK = rs.getDouble("TIENCK");
					VAT = rs.getDouble("VAT");
					TONGTIENAVAT = rs.getDouble("TONGTIENAVAT");
					
				}
				rs.close();
			}
			
			
			query=" select isnull(TEN,'') as ten from( " 
					 +   "\n select isnull(mh.KHOTT_FK,0) as KHOTT_FK " 
					 +   "\n from ERP_HOADON_DDH hddh left join ERP_HOADON hd on hd.PK_SEQ=hddh.HOADON_FK " 
					 +   "\n left join ERP_MUAHANG_SP mh on hddh.ddh_fk=mh.muahang_fk  " 
					 +   "\n where hd.PK_SEQ ='"+hdId+"'" 
					 +   "\n group by  mh.KHOTT_FK) kho_fk left join ERP_KHOTT kho on kho_fk.KHOTT_FK=kho.PK_SEQ ";
			System.out.println("THONGTINNPP__1:"+query);
			ResultSet rskho = db.get(query);
			if(rskho!=null){
				while(rskho.next()){
					khoxuat=rskho.getString("TEN");
				}
				rskho.close();
			}
			
			//------------------------------------
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
			
			NumberFormat formatter2=new DecimalFormat("#,###,###.##"); 
			
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.0f*CONVERT, 1.0f*CONVERT, 1.6f*CONVERT, 0.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);

			document.open() ;



			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);


			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(1.7f * CONVERT);//1.5
			cell.setPaddingLeft(13.5f * CONVERT);//2.6//13.0
			cell.setVerticalAlignment(Element.ALIGN_TOP);

			String [] ngayHD;
			Paragraph pxk;
			if(NGAYXUATHD.length()>=10)
			{
			ngayHD =  NGAYXUATHD.split("-");
			pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                        " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
			}
			else
			pxk = new Paragraph("", new Font(bf, 8, Font.BOLDITALIC));
			
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);

			// Thông tin Khach Hang
			PdfPTable table1 =new PdfPTable(2);
			table1.setWidthPercentage(104);
			float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
			table1.setWidths(withs1);									


			// DONG 1-- NGUOI MUA HANG
			PdfPCell cell_nguoimua = new PdfPCell();	
			pxk = new Paragraph("" , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(4);
			cell_nguoimua.setFixedHeight(0.6f*CONVERT);
			cell_nguoimua.addElement(pxk);
			cell_nguoimua.setBorder(0);		
			table1.addCell(cell_nguoimua);	

			System.out.println("Nguoi mua hang: "+ NGUOIMUA );
			PdfPCell cell_nguoimua1 = new PdfPCell();
			cell_nguoimua1.setPaddingTop(0.5f * CONVERT);
			cell_nguoimua1.setPaddingTop(-0.1f * CONVERT);
			cell_nguoimua1.setVerticalAlignment(Element.ALIGN_TOP);
			cell_nguoimua1.setPaddingLeft(6.0f * CONVERT);//4.5 5.4
			pxk = new Paragraph(NGUOIMUA, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_nguoimua1.setFixedHeight(0.7f*CONVERT);
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
		//	cell8a.setPaddingTop(-0.19f * CONVERT);
			cell8a.setPaddingTop(-0.1f * CONVERT);
			cell8a.setPaddingLeft(3.9f * CONVERT);
			pxk = new Paragraph(TENKH, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell8a.addElement(pxk);
			cell8a.setBorder(0);						
			table1.addCell(cell8a);


			// DONG 3 ---- DIA CHI
			PdfPCell cell10 = new PdfPCell();
			cell10.setPaddingLeft(2.8f * CONVERT);	
			cell10.setPaddingTop(-0.1f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell10.addElement(pxk);
			cell10.setBorder(0);						
			table1.addCell(cell10);

			String chuoi_= "";
			String chuoi1= DIACHI;
			String chuoi2= "";
			int vitri= 0;
			int dodaichuoi = DIACHI.length();
			if(dodaichuoi >= 70)
			{
				chuoi_ = DIACHI.substring(0, 70);
				vitri = chuoi_.lastIndexOf(" ");
				chuoi1 = chuoi_.substring(0, vitri);
				chuoi2 = DIACHI.substring(vitri + 1,dodaichuoi );
			}

			PdfPCell cell14 = new PdfPCell();
			cell14.setPaddingTop(0.0f * CONVERT);//-0.1
			cell14.setPaddingLeft(3.5f * CONVERT);//2.3
			pxk = new Paragraph(chuoi1, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell14.addElement(pxk);
			cell14.setBorder(0);						
			table1.addCell(cell14);		


			//DONG 4 --- DIA CHI : dai se xuong dong
			PdfPCell cell10a = new PdfPCell();
			cell10a.setPaddingTop(-0.3f * CONVERT);
			cell10a.setPaddingLeft(2.3f * CONVERT);	
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell10a.addElement(pxk);
			cell10a.setBorder(0);						
			table1.addCell(cell10a);


			PdfPCell cell14a = new PdfPCell();
			cell14a.setPaddingTop(-0.3f * CONVERT);//-0.5
			cell14a.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(chuoi2, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);		
			cell14a.addElement(pxk);
			cell14a.setBorder(0);						
			table1.addCell(cell14a);	


			// DONG 5 ----KHO XUAT
			PdfPCell cell17 = new PdfPCell();	
			cell17.setPaddingLeft(3.2f * CONVERT);//2.9
			cell17.setPaddingTop(0.2f * CONVERT);//-0.1
			pxk = new Paragraph(khoxuat, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell17.addElement(pxk);
			cell17.setBorder(0);						
			table1.addCell(cell17);	

			System.out.println("fdf : "+MASOTHUE.trim().length());
			if(MASOTHUE.trim().length() <= 0)
			{
				MASOTHUE = "                             ";
			}

			PdfPTable table12 =new PdfPTable(2);
			table12.setWidthPercentage(100);
			float[] withs12 = {10.0f * CONVERT, 10.0f * CONVERT};
			table12.setWidths(withs12);	
			
			PdfPCell cell181 = new PdfPCell();
			cell181.setPaddingRight(0.8f * CONVERT);
			cell181.setPaddingTop(-0.1f * CONVERT);
			pxk = new Paragraph( MASOTHUE , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_RIGHT);
			pxk.setSpacingAfter(2);
			cell181.addElement(pxk);
			cell181.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell181.setBorder(0);		
			table12.addCell(cell181); 
			
			cell181 = new PdfPCell();
			//cell181.setPaddingRight(0.2f * CONVERT);
			cell181.setPaddingTop(-0.3f * CONVERT);
			pxk = new Paragraph( HINHTHUCTT, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_RIGHT);
			pxk.setSpacingAfter(2);
			cell181.addElement(pxk);
			cell181.setPaddingRight(-0.1f*CONVERT);
			cell181.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell181.setBorder(0);		
			table12.addCell(cell181); 
			
			PdfPCell cell18 = new PdfPCell();
			cell18.setBorder(0);	
			cell18.addElement(table12);
			
						
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

			sanpham.setWidthPercentage(104);//100
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
		//	sanpham.setSpacingBefore(46.8f - (float)(0.5*CONVERT));
			sanpham.setSpacingBefore(1.6f*CONVERT);//1.4
			//float[] withsKM = { 7.0f, 15.0f, 57f,  20.0f-(float)(0.3*CONVERT), 15f, 9.0f, 14.0f, 20f, 24.0f, 8.0f, 23f, 25f };
			
			float[] withsKM = { 7.2f, 15.0f, 57f,  18.0f, 18f, 13.0f, 13.0f, 23f, 25.0f, 10.0f, 18f, 25f };
			sanpham.setWidths(withsKM);

			
			/*String query_pt=" select (isnull(Vat,0) *100)/TIENBVAT as ptVAT  from ERP_HOADON_SP where HOADON_FK='"+hdId+"'";
			ResultSet rsPT= db.get(query_pt);
			if(rsPT!=null){
				while(rsPT.next()){
					VAT = rsPT.getDouble("ptVAT");

				}
				rsPT.close();
			}
			System.out.println("pt: "+query_pt);*/

			PdfPCell cells = new PdfPCell();
			int stt = 1;
			int m=0;
			int numMax=14;//15
			/*String qrsp=  " SELECT CT.MA, CT.TEN, DV.DONVI, CT.SOLO, CT.NGAYHETHAN, \n" + 
						  " SUM(CT.SOLUONG) AS SOLUONG,  CT.DONGIA, hd.thuesuat,  ROUND( (SUM(CT.SOLUONG)  *  CT.DONGIA),0) AS THANHTIEN ,  \n" + 
						  " ROUND (  ROUND( (SUM(CT.SOLUONG)  *  CT.DONGIA),0) * hd.thuesuat /100  ,0)  AS TIENTHUE, \n" + 
						  " ROUND( (SUM(CT.SOLUONG)  *  CT.DONGIA),0) + ROUND (  ROUND( (SUM(CT.SOLUONG)  *  CT.DONGIA),0) * hd.thuesuat /100  ,0)  AS THANHTIENSAUVAT \n" + 
						  " FROM \n" + 
						  " 	ERP_HOADON_SP_CHITIET ct  inner join ERP_SANPHAM sp on sp.MA= ct.MA \n" + 
						  " 	INNER JOIN erp_hoadon_sp hd   on ct.hoadon_fk= hd.HOADON_FK   and hd.SANPHAM_FK= SP.PK_SEQ \n" + 
						  " 	LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ= CT.DONVI \n" + 
						  " 	where ct.hoadon_fk='"+ hdId + "' \n" + 
						  " GROUP BY CT.MA, CT.TEN, DV.DONVI, CT.SOLO, CT.NGAYHETHAN, CT.DONGIA,hd.thuesuat ";*/
			
			
			
//			String qrsp=  " SELECT CT.MA, CT.TEN, DV.DONVI, CT.SOLO, CT.NGAYHETHAN, \n" + 
//					  " SUM(CT.SOLUONG) AS SOLUONG,  CT.DONGIA, hd.thuesuat,  SUM (ROUND( CT.SOLUONG  *  CT.DONGIA,0)) AS THANHTIEN ,  \n" + 
//					  " SUM(  ROUND( ROUND( CT.SOLUONG  *  CT.DONGIA,0)* hd.thuesuat /100 ,0) )  AS TIENTHUE, \n" + 
//					  " SUM( ROUND( CT.SOLUONG  *  CT.DONGIA,0) +  ROUND( ROUND( CT.SOLUONG  *  CT.DONGIA,0)* hd.thuesuat /100 ,0) )  AS THANHTIENSAUVAT \n" + 
//					  " FROM \n" + 
//					  " 	ERP_HOADON_SP_CHITIET ct  inner join ERP_SANPHAM sp on sp.MA= ct.MA \n" + 
//					  " 	INNER JOIN erp_hoadon_sp hd   on ct.hoadon_fk= hd.HOADON_FK   and hd.SANPHAM_FK= SP.PK_SEQ \n" + 
//					  " 	LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ= CT.DONVI \n" + 
//					  " 	where ct.hoadon_fk='"+ hdId + "' \n" + 
//					  " GROUP BY CT.MA, CT.TEN, DV.DONVI, CT.SOLO, CT.NGAYHETHAN, CT.DONGIA,hd.thuesuat ";
			
			String qrsp = "SELECT CT.MA, CT.TEN, DV.DONVI, CT.SOLO, CT.NGAYHETHAN, \r\n" + 
					" SUM(CT.SOLUONG) AS SOLUONG,  CT.DONGIA, hd.thuesuat,  round(SUM( ROUND( CONVERT(NUMERIC(18,6),CT.SOLUONG)  * CONVERT(NUMERIC(18,6),CT.DONGIA),0)),0) AS THANHTIEN ,  \r\n" + 
					" SUM(  ROUND( ROUND( CT.SOLUONG  *  CT.DONGIA,0)* hd.thuesuat /100 ,0) )  AS TIENTHUE, \r\n" + 
					" SUM( ROUND( CONVERT(NUMERIC(18,6),CT.SOLUONG)  * CONVERT(NUMERIC(18,6),CT.DONGIA),0) +  ROUND( ROUND( CT.SOLUONG  *  CT.DONGIA,0)* hd.thuesuat /100 ,0) )  AS THANHTIENSAUVAT \r\n" + 
					" FROM \r\n" + 
					" 	ERP_HOADON_SP_CHITIET ct  inner join ERP_SANPHAM sp on sp.MA= ct.MA \r\n" + 
					" 	INNER JOIN erp_hoadon_sp hd   on ct.hoadon_fk= hd.HOADON_FK   and hd.SANPHAM_FK= SP.PK_SEQ \r\n" + 
					" 	LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ= CT.DONVI \r\n" + 
					" 	where ct.hoadon_fk='"+ hdId + "' \n" + 
					" GROUP BY CT.MA, CT.TEN, DV.DONVI, CT.SOLO, CT.NGAYHETHAN, CT.DONGIA,hd.thuesuat ";
			System.out.println(" lay danh sach sp: " + qrsp);
			ResultSet rssp= db.get(qrsp);
			if( rssp!= null){
				try {
					while( rssp.next()){
					String ma=rssp.getString("MA");
					String ten= rssp.getString("TEN");
					String tendiengiai= rssp.getString("MA");
					String donvi= rssp.getString("DONVI");
					double thuesuat=rssp.getDouble("thuesuat");
					double soluong= rssp.getDouble("SOLUONG");
					double dongia=  rssp.getDouble("DONGIA");
					double tiendong =rssp.getDouble("THANHTIEN");					
					double tienvat=rssp.getDouble("TIENTHUE");
					double tienthanhtoan=rssp.getDouble("THANHTIENSAUVAT");
					String solo=rssp.getString("SOLO");
					String hansudung=rssp.getString("NGAYHETHAN");
					
					
					List <String> tenlist=xuLyChuoi(50,45,27, ten);
					List <String> sololist=xuLyChuoi(10,10,6,solo);
					System.out.println("xu ly chuoi :"+ tenlist.size());
					System.out.println("xu ly chuoi solo  :" +solo+"/"+ sololist.size() + "/ "+ sololist.get(0));
					if(tenlist!= null)
					{
						
						
							System.out.println(" solo < ten");
							for (int n = 0; n < tenlist.size(); n++) 
							{
	
								String chuoi=tenlist.get(n);
								
								
								// in dong đầu
								if(n==0)
								{
									String[] arr = new String[] { Integer.toString(stt), ma, chuoi,solo,hansudung,donvi,
											formatter4.format(soluong), formatter4.format(dongia),
											formatter.format( tiendong),formatter.format( thuesuat), 
											formatter.format(tienvat), formatter.format(tienthanhtoan)};
									
									for (int j = 0; j < th.length; j++)
									{
										cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
										if (j == 2 || j==1 || j==0 ){
											
												cells.setPaddingLeft(0.2f*CONVERT);
											
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
	
										if(j>10)
										{
											cells.setPaddingRight(0.3f*CONVERT);
										}
	
										sanpham.addCell(cells);
									}
	
									numMax-=1;
									stt++;
								}
	
								else
								{
									String[] arr = new String[] { "", "", chuoi,"","","","", "", "", "","", ""};
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
										if(j>=10)
										{
											cells.setPaddingRight(0.3f*CONVERT);
										}
	
										sanpham.addCell(cells);
									}
	
									numMax-=1;
								}
	
							}
						
						
					}
				}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			/*cells = new PdfPCell(new Paragraph("Xuất trả lại hàng cho nhà cung cấp có biên bản kèm theo", new Font(bf, 10, Font.BOLD)));
			cells.setColspan(12);
			cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cells.setBorder(0);
			cells.setPaddingLeft(2.5f*CONVERT);
			cells.setFixedHeight(0.6f * CONVERT);
			cells.setPaddingTop(2.5f);
			sanpham.addCell(cells);
			numMax-=1;*/




			// DONG TRONG
			while(numMax>0)
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

				numMax--;
			}


			// Tong tien thanh toan	
			
			double totalvat=pxkBean.getTongtiensauVAT() - pxkBean.getTongtientruocVAT();
			System.out.println( " VAT : "+totalvat );

			String[] arr = new String[] {" ", " " , " ", " "," ", " "," "," ", formatter.format(pxkBean.getTongtientruocVAT()),
					"" ,formatter.format( totalvat),
					formatter.format(pxkBean.getTongtiensauVAT())};
			
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

				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);//bottom
				cells.setBorder(0);

				cells.setFixedHeight(1.0f * CONVERT);

				cells.setPaddingTop(2.6f);//2.5

				if(j==4 ) cells.setPaddingLeft(-0.10f *CONVERT);
				if(j>=10)
				{
					cells.setPaddingRight(0.3f*CONVERT);
				}
				sanpham.addCell(cells);
			}

						
			// Tien bang chu
						doctienrachu doctien = new doctienrachu();
						String tien = doctien.docTien(Math.round(pxkBean.getTongtiensauVAT()));		   
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
							cells.setPaddingBottom(-0.1f);
						//	cells.setPaddingTop(-0.5f);
							cells.setBorder(0);
							cells.setFixedHeight(0.8f*CONVERT);
							sanpham.addCell(cells);
						}

						document.add(sanpham);

						String thukho="";


						// Thông tin Khach Hang
						PdfPTable table_footer =new PdfPTable(3);
						table_footer.setWidthPercentage(100);
						float[] withsfooter = { 20.0f * CONVERT ,  13.0f * CONVERT,13.0f * CONVERT};
						table_footer.setWidths(withsfooter);		
						
						PdfPCell cellfooter = new PdfPCell(new Paragraph(thukho  , new Font(bf, 13, Font.NORMAL)));	
						//cellfooter.setBorder(0);
						table_footer.addCell(cellfooter);

						// DONG 1-- NGUOI MUA HANG
						cellfooter = new PdfPCell();	
						pxk = new Paragraph(thukho  , new Font(bf, 13, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_CENTER);
						cellfooter.setPaddingTop(2.2f*CONVERT);//2.5
						cellfooter.setPaddingRight(2.5f*CONVERT);
						cellfooter.addElement(pxk);
						//cellfooter.setBorder(0);
						table_footer.addCell(cellfooter);	
						
						/*System.out.println("ola: "+ "select ten from nhanvien where pk_seq="+pxkBean.getUserid());
						ResultSet rsuser=db.get("select ten from nhanvien where pk_seq="+pxkBean.getUserid());
						rsuser.next();
						String userten=rsuser.getString("ten");
						rsuser.close();
						
						PdfPCell cellfooter2 = new PdfPCell();
						pxk = new Paragraph(userten , new Font(bf, 13, Font.NORMAL));
						pxk.setAlignment(Element.ALIGN_CENTER);
						cellfooter2.setPaddingTop(2.2f*CONVERT);//2.5
						cellfooter2.setPaddingLeft(2.0f*CONVERT);
						cellfooter2.setVerticalAlignment(Element.ALIGN_CENTER);
						cellfooter2.addElement(pxk);
						cellfooter2.setBorder(0);	
						cellfooter2.setBorderWidth(1);
						table_footer.addCell(cellfooter2);*/
						
						
						
						


						document.add(table_footer);

						document.close();

		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// độ rộng cột, số lượng chữ thường tối đa cột chứa, max chữ hoa , chuỗi		
		public List<String> xuLyChuoi (float chieudai,int sochuthuong,int sochuhoa,  String chuoi ) 
		{  
			float chieudaimotchuthuong = chieudai/sochuthuong;
			float chieudaimotchuhoa = chieudai/sochuhoa;
			float sokytu1sp = 0;  
			List<String> _ghichuList =new ArrayList<String>();	  
			String[] words = new String[0];
			String _ten = "", _ten2 = "";
			words = chuoi.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong quy cach
			_ten = "";
			for (int _i = 0; _i < words.length; _i++) {
				char []temp = words[_i].toCharArray();
				if(sokytu1sp !=0 && sokytu1sp > chieudai)
					sokytu1sp = dodaichuoi(temp,chieudaimotchuthuong,chieudaimotchuhoa);
				else
					sokytu1sp += dodaichuoi(temp,chieudaimotchuthuong,chieudaimotchuhoa);
				if (sokytu1sp > chieudai)
				{
					if (_ten.trim().length() > 0) {

						_ghichuList.add(_ten); // Thêm dòng cũ
					}
					_ten = words[_i]; 
					sokytu1sp = 0;
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					float tam = (dodaichuoi(_ten2.toCharArray(),chieudaimotchuthuong,chieudaimotchuhoa));
					if (tam > chieudai) {

						_ghichuList.add(_ten);						
						_ten = words[_i];
						sokytu1sp = 0;
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0) {
				_ghichuList.add(_ten);	
			}
			return _ghichuList;				
		}
		
		public static float dodaichuoi(char[] chuoi, float chieudaimotchuthuong, float chieudaimotchuhoa)
		{
			float dodai = 0;
			for(int i=0;i<chuoi.length;i++)
			{
				if(Character.isUpperCase(chuoi[i]))
				{
					dodai+=chieudaimotchuhoa;
				}
				else
				{
					dodai+=chieudaimotchuthuong;
				}
			}
			return dodai;
		}
		
		//Jasper chung
				private void CreatePxk_2017_jasper(Document document, ServletOutputStream outstream,HttpServletRequest request, HttpServletResponse response , IErpHoaDon pxkBean,String hdId) throws IOException
				{
					try
					{
						dbutils db = new dbutils();
						
						//LẤY THÔNG TIN KHÁCH HÀNG (NHÀ PHÂN PHỐI)
						String query =
							" SELECT isnull(hd.nguoimua,'') as nguoimua, HD.SOHOADON, HD.KYHIEU, HD.NGAYXUATHD, HD.HINHTHUCTT, ISNULL(KH.MASOTHUE,'') AS MASOTHUE, KH.DIACHI, \n"+ 
							"        KH.TEN, ISNULL(HD.TIENCKTHUONGMAI,0) TIENCKTHUONGMAI,  HD.VAT, HD.TONGTIENAVAT,KH.TEN_NGUOIMUAHANG AS TENGNUOIMUA \n"+ 
							" FROM ERP_HOADON HD INNER JOIN ERP_NHACUNGCAP KH ON KH.PK_SEQ=HD.NCC_FK \n"+ 
							" WHERE HD.PK_SEQ ='"+hdId+"' \n";
						
						System.out.println(query);
						
						String khoxuat="";
						String SOHOADON="";
						String KYHIEU="";
						String NGAYXUATHD ="";
						String HINHTHUCTT="";
						String MASOTHUE="";
						String DIACHI="";
						String NGUOIMUA="";
						String TENKH="";
						double TIENCK=0;
						double VAT=0;
						double TONGTIENAVAT=0;
						
						System.out.println("THONGTINNPP__:"+query);
						ResultSet rs = db.get(query);
						if(rs!=null){
							while(rs.next()){
								SOHOADON = rs.getString("SOHOADON");
								KYHIEU = rs.getString("KYHIEU");
								NGAYXUATHD = rs.getString("NGAYXUATHD");
								HINHTHUCTT = rs.getString("HINHTHUCTT");
								MASOTHUE = rs.getString("MASOTHUE");
								DIACHI = rs.getString("DIACHI");
								NGUOIMUA = rs.getString("NGUOIMUA");
								//NGUOIMUA = rs.getString("TENGNUOIMUA");
								TENKH = rs.getString("TEN");
								//TIENCK = rs.getDouble("TIENCK");
								VAT = rs.getDouble("VAT");
								TONGTIENAVAT = rs.getDouble("TONGTIENAVAT");
								
							}
							rs.close();
						}
						
						
						query=" select isnull(TEN,'') as ten from( " 
								 +   "\n select isnull(mh.KHOTT_FK,0) as KHOTT_FK " 
								 +   "\n from ERP_HOADON_DDH hddh left join ERP_HOADON hd on hd.PK_SEQ=hddh.HOADON_FK " 
								 +   "\n left join ERP_MUAHANG_SP mh on hddh.ddh_fk=mh.muahang_fk  " 
								 +   "\n where hd.PK_SEQ ='"+hdId+"'" 
								 +   "\n group by  mh.KHOTT_FK) kho_fk left join ERP_KHOTT kho on kho_fk.KHOTT_FK=kho.PK_SEQ ";
						System.out.println("THONGTINNPP__1:"+query);
						ResultSet rskho = db.get(query);
						if(rskho!=null){
							while(rskho.next()){
								khoxuat=rskho.getString("TEN");
							}
							rskho.close();
						}


						NumberFormat formatter = new DecimalFormat("#,###,###");
						NumberFormat formatter1 = new DecimalFormat("#,###,###");
						NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
						
						NumberFormat formatter2=new DecimalFormat("#,###,###.##"); 
						////------------------------------------------------------
						try {

							///---------KHAI BÁO
							DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance(); 
							JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.awt.igno‌​re.missing.font","true"); 
							ServletOutputStream servletOutputStream = response.getOutputStream();
							JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.embedded", "true");
							JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.font.name", "arial.tff");

							response.setContentType("application/pdf");
							response.setHeader("Content-Disposition", " inline; filename=Bangkehoadon.pdf");
							
							String [] ngayHD={"","",""};
							if(NGAYXUATHD.length()>=10)
							{
							ngayHD =  NGAYXUATHD.split("-");
							}
						
							if(MASOTHUE.trim().length() <= 0)
							{
								MASOTHUE = "                             ";
							}

							// gán dũ liệu vao hashmap
							HashMap<String, Object> parameterMap = new HashMap<String, Object>();
							parameterMap.put("ngay", ngayHD[2] ); 
							parameterMap.put("thang", ngayHD[1]  );
							parameterMap.put("nam",  ngayHD[0] );
							
							parameterMap.put("chucuahieu", NGUOIMUA); 
							parameterMap.put("donvi", TENKH );
							parameterMap.put("kh_MST",MASOTHUE); 
							
							parameterMap.put("kh_Diachi", DIACHI); 
							parameterMap.put("hinhthucTT", HINHTHUCTT );	
							parameterMap.put("khoxuat", khoxuat );
							
							DinhDang dd =new DinhDang();	
							double totalvat=pxkBean.getTongtiensauVAT() - pxkBean.getTongtientruocVAT();
							parameterMap.put("truocVat",dd.DINHDANGSOCHUANVN(formatter.format(pxkBean.getTongtientruocVAT())));
							parameterMap.put("tienVat", dd.DINHDANGSOCHUANVN(formatter.format( totalvat))); 
							parameterMap.put("sauVat", dd.DINHDANGSOCHUANVN( formatter.format(pxkBean.getTongtiensauVAT())));
							
							// Tien bang chu
							doctienrachu doctien = new doctienrachu();
							//String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
							String tien =  doctien.docTien(Math.round(pxkBean.getTongtiensauVAT()));		   
							//Viết hoa ký tự đầu tiên
							String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
							
							parameterMap.put("TienIN", TienIN );

							try
							{		
								response.setContentType("application/pdf");
								InputStream reportStream =  new FileInputStream (getServletContext().getInitParameter("pathjasper") + "\\HoaDonTraVeNCC-mauchung.jasper");
								JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameterMap, getDataSource_2017(  pxkBean, db, hdId));
								JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
								servletOutputStream.flush();
								servletOutputStream.close();

							} catch (Exception e)
							{
								// display stack trace in the browser
								StringWriter stringWriter = new StringWriter();
								PrintWriter printWriter = new PrintWriter(stringWriter);
								e.printStackTrace(printWriter);
								response.setContentType("text/plain");
								response.getOutputStream().print(stringWriter.toString());
								e.printStackTrace();
							}
							db.shutDown();
							return;


						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Exception print PDF: " + e.getMessage());
						}


					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Exception print PDF: " + e.getMessage());
					}
				}
				
				//
				private  JRDataSource getDataSource_2017(  IErpHoaDon pxkBean,dbutils db,String hdId) throws SQLException 
				{
			
					Collection<SanphamHoadontaichinhObj> splist1 = new ArrayList<SanphamHoadontaichinhObj>(); // DANH SANH SP CHI TIET
					// Lay danh sp dang list

					List<SanphamHoadontaichinhObj> splist= new ArrayList<SanphamHoadontaichinhObj>();
					NumberFormat formatter = new DecimalFormat("#,###,###");
					NumberFormat formatter1 = new DecimalFormat("#,###,###");
					NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
					
					NumberFormat formatter2=new DecimalFormat("#,###,###.##"); 
							DinhDang dd=new DinhDang();
					int stt = 1;
					//
					//LAY THONG TIN NCC
					String qrsp = "SELECT CT.MA, CT.TEN, DV.DONVI, CT.SOLO, CT.NGAYHETHAN, \r\n" + 
							" SUM(CT.SOLUONG) AS SOLUONG,  CT.DONGIA, hd.thuesuat,  round(SUM( ROUND( CONVERT(NUMERIC(18,6),CT.SOLUONG)  * CONVERT(NUMERIC(18,6),CT.DONGIA),0)),0) AS THANHTIEN ,  \r\n" + 
							" SUM(  ROUND( ROUND( CT.SOLUONG  *  CT.DONGIA,0)* hd.thuesuat /100 ,0) )  AS TIENTHUE, \r\n" + 
							" SUM( ROUND( CONVERT(NUMERIC(18,6),CT.SOLUONG)  * CONVERT(NUMERIC(18,6),CT.DONGIA),0) +  ROUND( ROUND( CT.SOLUONG  *  CT.DONGIA,0)* hd.thuesuat /100 ,0) )  AS THANHTIENSAUVAT \r\n" + 
							" FROM \r\n" + 
							" 	ERP_HOADON_SP_CHITIET ct  inner join ERP_SANPHAM sp on sp.MA= ct.MA \r\n" + 
							" 	INNER JOIN erp_hoadon_sp hd   on ct.hoadon_fk= hd.HOADON_FK   and hd.SANPHAM_FK= SP.PK_SEQ \r\n" + 
							" 	LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ= CT.DONVI \r\n" + 
							" 	where ct.hoadon_fk='"+ hdId + "' \n" + 
							" GROUP BY CT.MA, CT.TEN, DV.DONVI, CT.SOLO, CT.NGAYHETHAN, CT.DONGIA,hd.thuesuat ";
					System.out.println(" lay danh sach sp: " + qrsp);
					ResultSet rssp= db.get(qrsp);
					if( rssp!= null){
							while( rssp.next()){
							String ma=rssp.getString("MA");
							String ten= rssp.getString("TEN");
							String tendiengiai= rssp.getString("MA");
							String donvi= rssp.getString("DONVI");
							double thuesuat=rssp.getDouble("thuesuat");
							double soluong= rssp.getDouble("SOLUONG");
							double dongia=  rssp.getDouble("DONGIA");
							double tiendong =rssp.getDouble("THANHTIEN");					
							double tienvat=rssp.getDouble("TIENTHUE");
							double tienthanhtoan=rssp.getDouble("THANHTIENSAUVAT");
							String solo=rssp.getString("SOLO");
							String hansudung=rssp.getString("NGAYHETHAN");
							
							String ngayhansudung="";
							if(hansudung.indexOf("-")>0){
								ngayhansudung=hansudung.split("-")[2]+"/"+hansudung.split("-")[1]+"/"+hansudung.split("-")[0];
							}
						SanphamHoadontaichinhObj obj = new SanphamHoadontaichinhObj( Integer.toString(stt),  ma,ten,solo ,
								ngayhansudung,donvi,
								dd.DINHDANGSOCHUANVN(formatter4.format(soluong)), dd.DINHDANGSOCHUANVN(formatter4.format(dongia)),
								dd.DINHDANGSOCHUANVN(formatter.format( tiendong)),
								formatter.format( thuesuat), 
								dd.DINHDANGSOCHUANVN(formatter.format(tienvat)),
								dd.DINHDANGSOCHUANVN(formatter.format(tienthanhtoan)));
						stt++;
						splist1.add(obj);					
							}
					}	
					
					return new JRBeanCollectionDataSource(splist1);
				}
}
