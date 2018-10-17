package geso.traphaco.distributor.servlets.phieugiaohangchinhanhdoitac;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.distributor.beans.phieugiaohangchinhanhdoitac.IPhieugiaohangchinhanhdoitac;
import geso.traphaco.distributor.beans.phieugiaohangchinhanhdoitac.imp.Phieugiaohangchinhanhdoitac;
import geso.traphaco.distributor.beans.noptien.INoptien;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
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
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.PageSize;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhieugiaohangchinhanhdoitacPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public PhieugiaohangchinhanhdoitacPdfSvl()
	{
		super();
	}
	float CONVERT = 28.346457f;  // =1cm
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
		System.out.println("vay day: id:"+ id);
		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));

		IPhieugiaohangchinhanhdoitac pxkBean = new Phieugiaohangchinhanhdoitac(id);
		pxkBean.setUserId(userId);
			
			dbutils db = new dbutils();
			
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", " inline; filename=PhieuGiaoHang.pdf");

				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				
				try{
					this.CreatePhieuGiaoHang(document, outstream, id);
						
					db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
					db.update("SET LOCK_TIMEOUT 60000;");
					db.getConnection().setAutoCommit(false);
					
					String query ="UPDATE ERP_HOADONNPP SET DaInPGH = 1 where PK_SEQ = "+id;
					if(!db.update(query))
					{
						db.getConnection().rollback();
					}
					db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
				}
				catch(Exception e) { e.printStackTrace();}			
				finally
				{
					db.shutDown();
				}
		}



	
	private String getDate()
	{			
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        dateFormat.format(date);
	        
/*		String arr[] = date.split("-");
		String nam = arr[0];
		String thang = arr[1];
		String ngay = arr[2];*/
		
		return dateFormat.format(date);
	}

	


	private void CreatePhieuGiaoHang(Document document, ServletOutputStream outstream, String id)
	{
		try
		{	
			dbutils db = new dbutils();
			document.setPageSize(PageSize.A4);
			
			
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//lay doi tuong khach hang
			
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			//BaseFont bfTimes = BaseFont.CreateFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, false);
			
			/*Font font = new Font(FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD, BaseColor.BLACK);
			Font font2 = new Font(FontFamily.TIMES_ROMAN, 8.0f, Font.BOLD, BaseColor.BLACK);
			Font fontnomar = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.NORMAL, BaseColor.BLACK);*/
			
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 12, Font.BOLD);
			Font fontnomar = new Font(bf, 12,Font.NORMAL);

			//font2.setColor(BaseColor.GREEN);
			 //KHAI BAO 1 BANG CO BAO NHIEU COT
			
			String tenkh = "";
			String diachikh = "";
			String masothuekh = "";
			
			//LAY THONG TIN NCC
			String npp_fk="";
			String khId="";
			String ngayxuathd = "";
			String sohoadon = "";
			
			String nppTen = "";
			
			String sql_getinfokh =
				" SELECT a.ngayxuathd, a.NPP_DAT_FK NPP_FK,  a.KHACHHANG_FK, a.SOHOADON, b.TEN  " +
				" FROM ERP_HOADONNPP a INNER JOIN NHAPHANPHOI b ON a.NPP_FK = b.PK_SEQ" +
				" WHERE a.PK_SEQ ='"+id+"'";
							
			System.out.println("[INIT_HOADON: ] "+sql_getinfokh);
			
			ResultSet rsCheck = db.get(sql_getinfokh);
			try
			{
				if(rsCheck.next())
				{
					npp_fk = rsCheck.getString("NPP_FK")== null ? "" :rsCheck.getString("NPP_FK") ;
					khId = rsCheck.getString("KHACHHANG_FK")== null ? "" :rsCheck.getString("KHACHHANG_FK");					
					ngayxuathd = rsCheck.getString("ngayxuathd");
					sohoadon = rsCheck.getString("SOHOADON");
					nppTen = rsCheck.getString("TEN");
					rsCheck.close();
				}
				rsCheck.close();
			}
			catch(Exception er ){
				er.printStackTrace();
			}
			
			String[] ngayDH = ngayxuathd.split("-");
			
			
			if(khId.length() > 0){
				sql_getinfokh = " select  TEN ,isnull(DIACHI,'') as DIACHI, isnull(MASOTHUE,'') as MASOTHUE  "+
				        " from KHACHHANG " +
				        " where PK_SEQ = '"+ khId +"' and kbh_fk='100052' ";
			}else{
				sql_getinfokh = " select  TEN ,DIACHI, MASOTHUE  "+
			    " from NHAPHANPHOI " +
			    " where PK_SEQ = '"+ npp_fk +"'  ";
			}
			
			 System.out.println("Lấy TT KH1 "+sql_getinfokh);
			 
			 ResultSet rsLayKH= db.get(sql_getinfokh);
			   if(rsLayKH.next())
			   {
				   tenkh = rsLayKH.getString("TEN");
				   masothuekh = rsLayKH.getString("MASOTHUE");
				   diachikh = rsLayKH.getString("DIACHI");
				  
				   rsLayKH.close();
				   
			   }   
			   
			   
			PdfPTable tableheader = new PdfPTable(1);
			tableheader.setWidthPercentage(100);
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {300.0f};
			tableheader.setWidths(withsheader); 
			
			Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path") + "/logo.gif");	
			hinhanh.scalePercent(70);
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			
			PdfPCell cellslogo = new PdfPCell(hinhanh);
			cellslogo.setPadding(10);
			cellslogo.setBorder(0);
			tableheader.addCell(cellslogo);
			
			cellslogo = new PdfPCell( new Paragraph(nppTen , fontnomar) );
			cellslogo.setPadding(5);
			cellslogo.setBorder(0);
			tableheader.addCell(cellslogo);
			
			
			
			
			Paragraph pxk = new Paragraph("PHIẾU GIAO HÀNG", font);
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_CENTER);
			
			PdfPCell celltieude = new PdfPCell();
			celltieude.addElement(pxk);
			Paragraph dvbh = new Paragraph("( Kèm theo Hoá đơn GTGT số "+sohoadon.trim()+" Ngày " +ngayDH[2]+ " tháng " + ngayDH[1]+ " năm  "  + ngayDH[0] +  " )" , fontnomar);
			dvbh.setSpacingAfter(3);
			dvbh.setAlignment(Element.ALIGN_CENTER);
			celltieude.addElement(dvbh);
			celltieude.setBorder(0);
			
			tableheader.addCell(celltieude);
			document.add(tableheader);						
			
			
			
			PdfPTable table_info = new PdfPTable(1);
			float[] with3 = {300.0f};
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111= new PdfPCell();
			
			   
			cell111.addElement(new Paragraph("Tên khách hàng : " + tenkh, fontnomar));
			cell111.setBorder(0);
			
			cell111.addElement(new Paragraph("Địa chỉ : " + diachikh, fontnomar));
			cell111.setBorder(0);
			
			cell111.addElement(new Paragraph("Mã số thuế : " + masothuekh, fontnomar));
			cell111.setBorder(0);

			cell111.setPaddingBottom(10);
			table_info.addCell(cell111);
			
			
			document.add(table_info);
			//Table Content
			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {5.0f, 30.0f, 7.0f, 9.0f, 12.0f, 15.0f}; 	

	        table.setWidths(withs);
			String[] th = new String[]{ "STT", "Tên hàng hoá, dịch vụ", "ĐVT", "Số lượng", "Đơn giá ", "Thành tiền"};
			PdfPCell[] cell = new PdfPCell[6];
			for(int i = 0; i < 6 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setBackgroundColor(BaseColor.LIGHT_GRAY);	
				cell111.setPadding(2);
				table.addCell(cell[i]);			
			}
			
		/*	String query = */
/*			"	SELECT dh.PK_SEQ, dh_km.SPMA MA, sp.TEN, dh_km.SOLUONG,dvdl.DONVI, 0 DONGIA, 0 THANHTIEN \n"+
			"	FROM DONHANG dh INNER JOIN DONHANG_CTKM_TRAKM dh_km on dh.PK_SEQ = dh_km.DONHANGID \n"+
			"			INNER JOIN SANPHAM sp on dh_km.SPMA = sp.MA \n"+
			"	     INNER JOIN CTKHUYENMAI km on km.PK_SEQ = dh_km.CTKMID \n"+
			"	     LEFT JOIN DONVIDOLUONG dvdl on dvdl.PK_SEQ = sp.DVDL_FK \n"+
			"	WHERE dh.TRANGTHAI != 2 and km.KHO_FK = '100001' and  dh.PK_SEQ='"+id+"' \n";*/
				
			String	query = 
			"SELECT distinct c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,  \n" +
			"		case when d.pk_seq = dhsp.dvdl_fk then b.soluong \n " +
			"			 else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong, \n " +
			"		'' as solo, \n " +
			"		'' as NGAYHETHAN, dhsp.thuevat, \n " +
			"		( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + id + "' and sanpham_fk = b.sanpham_fk ) as dongia  \n	 " +
			"		,case when	ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) !=1 then 0 else dhsp.chietkhau end as chietkhau  \n "+
			"FROM ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk	\n " +
			"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ	\n " +
			"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk   \n " +
			"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk= dh.PK_SEQ 	and dhsp.sanpham_fk= b.sanpham_fk	\n " +
			"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  \n " +
			"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	\n  " +
			"WHERE a.PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk IN (select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + id + "'  )) and b.soluong > 0 and a.TRANGTHAI != '3' \n ";
			
			System.out.println(query);
			
			int sl =0;
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			double totalCK = 0;
			
			double vatCK = 0;
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				PdfPCell cellSP = null;
				
				int stt = 1;
				 NumberFormat formatter = new DecimalFormat("#,###,###");
				 NumberFormat formatter1 = new DecimalFormat("#,###,###");
				 NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
				 
				while(rs.next())
				{
					double soluong = rs.getDouble("soluong");
					double chietkhau = rs.getDouble("chietkhau");
					double dongia = rs.getDouble("dongia");
					double vat = rs.getDouble("thuevat");
					double thanhtien = Math.round(soluong*dongia);
					double thueGTGT = Math.round(thanhtien *vat/100);
					double sotientt = thanhtien + thueGTGT ;
					
					vatCK = rs.getDouble("thuevat");	
					
					thanhtien = Math.round(thanhtien);
					
					cellSP = new PdfPCell(new Paragraph(Integer.toString(stt), font2));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);		
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("TEN"), font2));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("donvi"), font2));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(DinhDangTRAPHACO(formatter.format(soluong)) , font2));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(DinhDangTRAPHACO(formatter4.format(dongia)) , font2));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(DinhDangTRAPHACO(formatter.format(thanhtien)) , font2));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					stt++;
					
					
					sl+= soluong;				
					
					
					totalThueGTGT += thueGTGT;
					totalTienTruocVAT+= thanhtien ;
					totalCK += chietkhau;
				}
				rs.close();
				
				totalSotienTT = Math.round(totalTienTruocVAT) + Math.round(totalThueGTGT);
				
				cellSP = new PdfPCell(new Paragraph("", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("Chiết khấu bán hàng ", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("" , font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("" , font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph(DinhDangTRAPHACO(formatter.format(totalCK)) , font2));
				cellSP.setPadding(3.0f);
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellSP);	
				
				////
				
				cellSP = new PdfPCell(new Paragraph("", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("Tiền vat ", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("" , font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("" , font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph(DinhDangTRAPHACO( formatter1.format(totalThueGTGT- Math.round(totalCK*vatCK/100) )) , font2));
				cellSP.setPadding(3.0f);
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellSP);	
				
				//
				
				cellSP = new PdfPCell(new Paragraph("", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("CỘNG", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph(DinhDangTRAPHACO(Integer.toString(sl)) , font2));
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("" , font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph(DinhDangTRAPHACO(formatter1.format(totalSotienTT - ( totalCK + Math.round(totalCK*vatCK/100) ))) , font2));
				cellSP.setPadding(3.0f);
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellSP);	
				
			}
			
			document.add(table);
			
			//Thêm
			PdfPTable tablethongtinD =new PdfPTable(2);//số cột
			tablethongtinD.setWidthPercentage(100);//độ giãn của cột
			float[] withthongtinKT = { 100.0f, 100.0f};
			tablethongtinD.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tablethongtinD.setWidths(withthongtinKT); //khai báo	
						
			PdfPCell cell25 =  new PdfPCell();
			cell25.addElement(new Paragraph(" ", fontnomar));
			cell25.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell25.setBorder(0);
			tablethongtinD.addCell(cell25);
			
			PdfPCell cell26 =  new PdfPCell();
			cell26 =  new PdfPCell();
			cell26.addElement(new Paragraph(" ", fontnomar));
			cell26.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell26.setBorder(0);
			tablethongtinD.addCell(cell26);
			
			PdfPCell cell27 =  new PdfPCell();
			Paragraph para = new Paragraph("KHÁCH HÀNG", fontnomar);;
			/*td.setAlignment(Element.ALIGN_CENTER);*/
			cell27.setPaddingLeft(2.5f*CONVERT);
			cell27.addElement(para);
			cell27.setBorder(0);
			tablethongtinD.addCell(cell27);
			
			PdfPCell cell28 =  new PdfPCell();
			Paragraph para2 = new Paragraph("NGƯỜI LẬP BẢNG KÊ", fontnomar);;
			/*td.setAlignment(Element.ALIGN_CENTER);*/
			cell28.setPaddingLeft(2.5f*CONVERT);
			cell28.addElement(para2);
			cell28.setBorder(0);
			tablethongtinD.addCell(cell28);
			
			//hết
			/*PdfPTable tablefooter = new PdfPTable(2);
			tablefooter.setWidthPercentage(100);
			tablefooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] withfooterder = {150.0f,300.0f};
			tablefooter.setWidths(withfooterder); 
			
			//nguoimua hang 
			
			PdfPCell cell11 = new PdfPCell();
			cell11.addElement(new Paragraph(" ", fontnomar));
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11.setBorder(0);
			tablefooter.addCell(cell11);
			
			cell11 = new PdfPCell();
			cell11.addElement(new Paragraph(" ", fontnomar));
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11.setBorder(0);
			tablefooter.addCell(cell11);
			
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11.setBorder(0);
			Paragraph para = new Paragraph("NGƯỜI LẬP BẢNG KÊ", fontnomar);
			para.setAlignment(Element.ALIGN_RIGHT);

			
			para.add("\n");
			para.add("\n");
			
			cell11.addElement(para);*/
			/*tablefooter.addCell(cell11);
			
			
			PdfPCell cell12 = new PdfPCell();
			cell12.addElement(new Paragraph(" ", fontnomar));
			cell12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell12.setBorder(0);
			tablefooter.addCell(cell12);
			
			cell12 = new PdfPCell();
			cell12.addElement(new Paragraph(" ", fontnomar));
			cell12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell12.setBorder(0);
			tablefooter.addCell(cell11);
			
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11.setBorder(0);
			Paragraph para = new Paragraph("NGƯỜI LẬP BẢNG KÊ", fontnomar);
			para.setAlignment(Element.ALIGN_RIGHT);*/
			
			document.add(tablethongtinD);
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
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
	
	public static void main(String[] arg)
	{
		//CHAY LAI HOA DON   --HA DONG: 106174,  HA NOI: 100002 --> BEN THAT CHAY 2 NHA TRUOC, 3 THANG 7, 8, 9
		/*String nppID = "106182, 100010";
		int thang = 8;
		int thang_to = 9;
		int nam = 2014;
		
		dbutils db = new dbutils();
		String query = "select pk_seq from HOADON " +
					   "where NPP_FK in ( " + nppID + " ) and month(ngayxuatHD) >= '" + thang + "' and month(ngayxuatHD) <= '" + thang_to + "' " +
					   		" and year(ngayxuatHD) = '" + nam + "'  and trangthai not in ( 3, 5 ) and THOIDIEMTAO_HD is NULL ";
		System.out.println("---LAY HOA DON: " + query );
		ResultSet rsHD = db.get(query);
		
		try 
		{
			//db.getConnection().setAutoCommit(false);
			
			if(rsHD != null)
			{
				while(rsHD.next())
				{
					String hoadonID = rsHD.getString("pk_seq");
					
					CapNhatHoaDon(hoadonID, db);
				}
				rsHD.close();
			}
			
			db.getConnection().setAutoCommit(true);
			
			//CAP NHAT LAI
			query = "update hd set tongtienavat = a.AVAT, tongtienbvat = a.BVAT, vat = a.VAT, chietkhau = a.chietkhau   " +
					"from  " +
					"(  " +
					"	select hd.PK_SEQ AS HDID, HOADON_CT.tongtien as BVAT, HOADON_CT.Chietkhau as chietkhau,  " +
					"		 ( HOADON_CT.VAT - HOADON_CT. VAT_KM) as VAT,  " +
					"		  round((HOADON_CT.tongtien + HOADON_CT.VAT ) - ( HOADON_CT.Chietkhau  + HOADON_CT.VAT_KM), 0) AS AVAT  " +
					"	from HOADON hd inner join  " +
					"	(      " +
					"		select AA.HOADON_FK, AA.tongtien, AA .VAT, BB.Chietkhau, BB.VAT as VAT_KM  " +
					"		from  " +
					"		(  " +
					"		   select HOADON_fk ,  SUM( SOLUONG * DONGIA ) as tongtien , SUM (SOLUONG * DONGIA * VAT /100) as VAT   " +
					"		   from HOADON_SP  " +
					"		   group by HOADON_FK  " +
					"		 )   " +
					"		 AA inner join  " +
					"		 (  " +
					"			select hoadon_fk, sum(chietkhau) as chietkhau, sum(chietkhau * thueVAT / 100) as VAT  " +
					"			from HOADON_CHIETKHAU    " +
					"			group by hoadon_fk  " +
					"		)   " +
					"		BB  ON AA. HOADON_FK=BB .HOADON_FK   " +
					"	)   " +
					"	HOADON_CT on hd.PK_SEQ = HOADON_CT. HOADON_FK  " +
					"       left join KHACHHANG kh on kh .PK_SEQ= hd.KHACHHANG_FK  " +
					"	where  LOAIHOADON = 0 and month(ngayxuatHD) >= '" + thang + "' and month(ngayxuatHD) <= '" + thang_to + "' " +
							"	and year(ngayxuatHD) = '" + nam + "' and THOIDIEMTAO_HD is NULL  " +
					")  " +
					"as a inner join HOADON hd on hd .PK_SEQ = a.HDID  " +
					"where  hd.NPP_FK in (  " + nppID + "  ) and hd.THOIDIEMTAO_HD is NULL  " ;
			
			db.update(query);
			
			System.out.println("-------------CHAY XONG ROI.............");
			//db.getConnection().commit();
			
		} 
		catch (Exception e) 
		{
			try {
				db.getConnection().rollback();
			} 
			catch (SQLException e1) { }
			
			e.printStackTrace();
			System.out.println("---LOI ROI...........");
		}*/
		
	}


	
	
	private String DinhDangTRAPHACO(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}
	
	private int Songaytrongthang(int month, int year) 
    {
        int ngay = 0;
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                {
                    ngay = 31;
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                {
                    ngay = 30;
                    break;
                }
            case 2:
                {
                    if (year % 4 == 0)
                        ngay = 29;
                    else
                        ngay = 28;
                    break;
                }
        }

        return ngay;
    }
	
}
