package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkho;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpInPhieuxuatvattuBaobi1Svl extends HttpServlet{
	private static final long serialVersionUID = 1L;
	List<IYeucau> danhsach_sp;
	
	public ErpInPhieuxuatvattuBaobi1Svl() {
        super(); 
    }
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		System.out.println("Action = " + action);
		Create_PO_PDF(response, request);
		}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void Create_PO_PDF(HttpServletResponse response,
			HttpServletRequest request) throws UnsupportedEncodingException {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition",
				" inline; filename=Traphaco_InPhieuxuatvattuBaobi1.pdf");

		float CONVERT = 28.346457f;// 1 cm
		float PAGE_LEFT = 2.0f * CONVERT, PAGE_RIGHT = 1.5f * CONVERT, PAGE_TOP = 1.0f * CONVERT, PAGE_BOTTOM = 1.0f * CONVERT;
		Document document = new Document(PageSize.A4.rotate(), PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		ServletOutputStream outstream;

		try {
			
			HttpSession session = request.getSession();
			Utility util = new Utility();
			String querystring = request.getQueryString();
			
			String userId = util.getUserId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			System.out.println("userId: " + userId);
			String id = util.getId(querystring);
			if (id.length() == 0)
				id = util.antiSQLInspection(request.getParameter("id"));
			System.out.println("\n id:" + id);
			
			String loaikho=util.antiSQLInspection(request.getParameter("loai"));
			System.out.println("\n loai:" + loaikho);
			
			
			String type=util.antiSQLInspection(request.getParameter("type"));
			System.out.println("\n type: " + type);
			
			
			
			
			outstream = response.getOutputStream();
			dbutils db = new dbutils();
			
			if(type.equals("1")){
				Create_Phieuxuat(document, outstream, response, request, db, id, loaikho, userId);
				
			}
			else if(type.equals("2")) {
			Create_Phieugiaonhan(document, outstream, response, request, db, id, loaikho,userId);
			}
			db.shutDown();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception PO Print: " + e.getMessage());
		}
	}
	
	
	private void Create_Phieuxuat(Document document,
			ServletOutputStream outstream, HttpServletResponse response,
			HttpServletRequest request, dbutils db,String id,String loaikho, String  userId)
			throws UnsupportedEncodingException {
		
		//lay phieu chuyen kho
		IErpChuyenkho lsxBean = new ErpChuyenkho(id);
	    lsxBean.setUserId(userId);
	    lsxBean.init();
		
		//lay danh sach vat tu
	    danhsach_sp = (List<IYeucau>)lsxBean.getSpList(); 
		Hashtable<String, String> sanpham_soluong = lsxBean.getSanpham_Soluong();
	    //lay thong tin tung vat tu
		
		String lydoxuat=lsxBean.getLydoyeucau();
		//lay kho xuat
		String tenkhoxuat="";
		ResultSet khoxuatRs=lsxBean.getKhoXuatRs();
		if(khoxuatRs != null)
		{
			try {
				while(khoxuatRs.next()){
					if( khoxuatRs.getString("pk_seq").equals(lsxBean.getKhoXuatId())){
						tenkhoxuat=khoxuatRs.getString("ten");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//noi dung form
		try {
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 
			NumberFormat formater = new DecimalFormat("##,###,###.##########");
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			//dinh dang font chu
			Font font9_italic = new Font(bf,10,Font.ITALIC);
			font9_italic.setColor(79, 79, 79);
			Font font11_bold = new Font(bf, 11, Font.BOLD);
			Font font11_normal = new Font(bf, 12, Font.BOLD);
			Font font11_italic = new Font(bf, 11, Font.ITALIC);
			Font font13_bold = new Font(bf, 13, Font.BOLD);
			Font font13_normal = new Font(bf, 13, Font.NORMAL);
			//Font font13_bold_underline = new Font(bf, 13, Font.UNDERLINE);
			Font font13_bold_italic = new Font(bf, 13, Font.BOLDITALIC);
			Font font13_italic = new Font(bf, 13, Font.ITALIC);
			
			Font font12_normal = new Font(bf, 12, Font.NORMAL);
			Font font12_bold = new Font(bf, 12, Font.BOLD);
			Font font12_italic = new Font(bf, 12, Font.ITALIC);
			
			float CONVERT = 28.346457f;// 1 cm
		//	float[] TABLE_HEADER_WIDTHS = new float[] {9.25f * CONVERT ,11.25f * CONVERT };
			PdfPCell cell;
			Paragraph p;
			
			
			//=---------------------------tao header1
			PdfPTable table2 = new PdfPTable(2);	
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			//table2.setWidths(TABLE_HEADER_WIDTHS);
			table2.setWidthPercentage(100);
			
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("pathPrint")+"\\logo.gif");
			hinhanh.scalePercent(10);
			hinhanh.setAbsolutePosition(4.5f * CONVERT, document.getPageSize().getHeight() - 1.4f * CONVERT);
			
			cell = new PdfPCell(new Paragraph(" ",font12_normal));
			cell.setBorder(0);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("BM 51/05",font11_italic));
			cell.setBorder(0);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("CÔNG TY TNHH TRAPHACO HƯNG YÊN",font12_normal));
			cell.setBorder(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("BH/SĐ: 03/07/2017",font11_italic));
			cell.setBorder(0);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			table2.addCell(cell);
			
			
			
			
			//----------------------tua de
			cell = new PdfPCell();
			p = new Paragraph();
			p.setSpacingBefore(15);
			p.add(new Chunk("\n  PHIẾU XUẤT VẬT TƯ\n", new Font(bf, 18, Font.BOLD)));
			p.add(new Chunk("Số: " +lsxBean.getId(), font12_normal)); 
			cell.setBorder(0);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(2);
			cell.addElement(p);
			table2.addCell(cell);
			
			// ----------------------thong tin
			
			/*PdfPTable table3 = new PdfPTable(1);	
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			//table2.setWidths(TABLE_HEADER_WIDTHS);
			table2.setWidthPercentage(100.0f);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.setSpacingBefore(10);
			p.add(new Chunk("Họ và tên người nhận hàng:\n", font12_normal));
			p.add(new Chunk("Địa chỉ:\n", font12_normal));
			p.add(new Chunk("Lý do xuất:\n", font12_normal));
			p.add(new Chunk("Xuất tại kho: Kho phụ liệu cấp 2\n", font12_normal));
			cell.setBorder(0);
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
		
			cell.addElement(p);
			table3.addCell(cell);*/
			
			PdfPTable tbl = new PdfPTable(1);
			tbl.setWidthPercentage(100);
			tbl.setSpacingBefore(0);
			tbl.getDefaultCell().setBorder(0);
			
			cell = new PdfPCell(new Paragraph("Họ và tên người nhận hàng: " + lsxBean.getNguoinhan(), font12_normal));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tbl.addCell(cell);
			
			//TIM DIA CHI THEO DIA CHI CUA KHO NHAN
			String diachi="";
			if(lsxBean.getKhoNhapId() != null){
				
				if(lsxBean.getKhoNhapId().length()>0){
					String sql= "select ten as ten from ERP_KHOTT where pk_seq=" +lsxBean.getKhoNhapId();
					ResultSet rs= db.get(sql);
					if(rs!= null)
					{
						while(rs.next()){
							diachi=rs.getString("ten");
						}
					}
				}
			}
			
			
			
			cell = new PdfPCell(new Paragraph("Địa chỉ: "+diachi, font12_normal));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tbl.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Lý do xuất: "+lydoxuat, font12_normal));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tbl.addCell(cell);
			
			

			// lay so luong xuat cua so lo san xuat cua lenh san xuat
			
			String sql_lsx=" select  LSX.SOLUONG AS SOLUONG , ISNULL( (SELECT DV.DONVI FROM DONVIDOLUONG DV WHERE DV.PK_SEQ= BOM.DVDL_FK),'') AS DONVI " + 
							  "\n from ERP_LENHSANXUAT_SANPHAM  LSX INNER JOIN ERP_DANHMUCVATTU BOM ON BOM.PK_SEQ= LSX.DanhMucVT_FK" + 
							  "\n WHERE LSX.LENHSANXUAT_FK= "+ 	lsxBean.getLsxIds()	;
			
			System.out.println(" so luong san xuat:" + sql_lsx);
			String soluongsx="";
			
			ResultSet rs_lsx= db.get(sql_lsx);
			if(rs_lsx!=null){
				try {
					while(rs_lsx.next()){
						soluongsx= formatter.format(  rs_lsx.getDouble("SOLUONG")  ) +  " " +  rs_lsx.getString("DONVI") ;
					}
				} catch (Exception e) {
				e.printStackTrace();
				}
			}
			
			
			// so luong lo sx
			
			if( lsxBean.getLsxIds()!=null && lsxBean.getLsxIds()!="") {
			cell = new PdfPCell(new Paragraph("Số lượng lô sản xuất : "+soluongsx, font12_normal));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tbl.addCell(cell);
			}
			
			
			
			cell = new PdfPCell(new Paragraph("Xuất tại kho: " +tenkhoxuat, font12_normal));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tbl.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("Ngày xuất kho: " +  formatngay(lsxBean.getNgayyeucau()) , font12_normal));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tbl.addCell(cell);
			
			//bang du lieu
			//xet loai xet tieu de
			/*float[] vattu_width1 = { 0.5f * CONVERT, 1.0f * CONVERT,
					2.0f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.0f * CONVERT, 1.2f * CONVERT, 0.7f * CONVERT,
					1.0f * CONVERT, 1.3f * CONVERT, 1.0f * CONVERT};
			float[] vattu_width2 = { 0.5f * CONVERT, 1.0f * CONVERT,
					2.0f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.0f * CONVERT, 1.2f * CONVERT, 1.0f * CONVERT,
					1.0f * CONVERT, 1.0f * CONVERT};
			float[] vattu_width3 = { 0.5f * CONVERT, 1.0f * CONVERT,
					2.0f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.3f * CONVERT, 1.0f * CONVERT, 1.2f * CONVERT,
					1.5f * CONVERT, 0.8f * CONVERT,1.0f * CONVERT,1.0f * CONVERT};
			float[] vattu_width4 = { 0.5f * CONVERT, 1.0f * CONVERT,
					2.0f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.5f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.0f * CONVERT, 1.0f * CONVERT,1.0f * CONVERT,
					1.0f * CONVERT};*/
			
			
			float[] vattu_width1 = { 0.5f * CONVERT, 1.0f * CONVERT,
					2.5f * CONVERT, 0.8f * CONVERT, 0.8f * CONVERT,
					1.2f * CONVERT, 0.8f * CONVERT, 0.6f * CONVERT,
					0.6f * CONVERT, 1.3f * CONVERT,0.9f * CONVERT, 1.0f * CONVERT};
			
			float[] vattu_width2 = { 0.5f * CONVERT, 1.0f * CONVERT,
					2.2f * CONVERT, 0.8f * CONVERT, 0.8f * CONVERT,
					1.2f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.0f * CONVERT,0.9f * CONVERT, 1.0f * CONVERT};
			
			float[] vattu_width3 = { 0.5f * CONVERT, 1.0f * CONVERT,
					2.5f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.5f * CONVERT, 0.8f * CONVERT, 0.8f * CONVERT,
					1.2f * CONVERT, 0.8f * CONVERT,1.3f * CONVERT, 1.1f * CONVERT,1.0f * CONVERT};
			
			float[] vattu_width4 = { 0.5f * CONVERT, 1.0f * CONVERT,
					2.3f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.5f * CONVERT, 0.8f * CONVERT, 0.8f * CONVERT,
					0.8f * CONVERT, 0.8f * CONVERT,1.5f * CONVERT,
					1.0f * CONVERT,1.0f * CONVERT};
			
			String[] spTitles1 = { "TT","Mã VT","Tên hàng", "Đơn vị ", "Số lượng", "Số PKN", "Số lô EO","Mẻ","Thùng", "Số lô NSX", "Ngày HH","Vị trí" };
			String[] spTitles2 = { "TT","Mã VT","Tên hàng", "Đơn vị ", "Số lượng", "Số PKN", "Maquette","Số đăng ký", "Số lô NSX", "Ngày HH","Vị trí" };
			String[] spTitles3 = { "TT","Mã VT","Tên hàng", "Đơn vị ", "Số lượng", "Số PKN", "Hàm ẩm","Hàm lượng","Phiếu định tính","Thùng ", "Số lô NSX", "Ngày HH","Vị trí" };
			String[] spTitles4 = { "TT","Mã VT","Tên hàng", "Đơn vị ", "Số lượng", "Số PKN", "Hàm ẩm","Hàm lượng","Mẻ","Thùng", "Số lô NSX", "Ngày HH","Vị trí" };
			int socot;
			if(loaikho.equals("1")){
				socot=vattu_width1.length;
			}
			else if(loaikho.equals("2")){
				socot=vattu_width2.length;
			}
			else if(loaikho.equals("3")){
				socot=vattu_width3.length;
			}
			else {
				socot=vattu_width4.length;
			}
			
			PdfPTable tbl_vattu=new PdfPTable(socot);
			tbl_vattu.setWidthPercentage(100);
			tbl_vattu.setSpacingBefore(10);
			tbl_vattu.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbl_vattu.getDefaultCell().setBorder(0);
			tbl_vattu.setSpacingAfter(8.0f);
			if(loaikho.equals("1")){
				
				tbl_vattu.setWidths(vattu_width1);
				//in tieu de
				for (int z = 0; z < spTitles1.length; z++) {
					cell = new PdfPCell(new Paragraph(spTitles1[z], font11_bold));
					cell.setPadding(2.0f);
					cell.setPaddingBottom(7);
					cell.setBackgroundColor(BaseColor.WHITE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tbl_vattu.addCell(cell);
				}
				
			}
			else
			if(loaikho.equals("2")){
				
				tbl_vattu.setWidths(vattu_width2);
				//in tieu de
				for (int z = 0; z < spTitles2.length; z++) {
					cell = new PdfPCell(new Paragraph(spTitles2[z], font11_bold));
					cell.setPadding(3.0f);
					cell.setPaddingBottom(7);
					cell.setBackgroundColor(BaseColor.WHITE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbl_vattu.addCell(cell);
				}
				
			}
			else
			if(loaikho.equals("3")){
				
				tbl_vattu.setWidths(vattu_width3);
				for (int z = 0; z < spTitles3.length; z++) {
					cell = new PdfPCell(new Paragraph(spTitles3[z], font11_bold));
					cell.setPadding(2.0f);
					cell.setPaddingBottom(7);
					cell.setBackgroundColor(BaseColor.WHITE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbl_vattu.addCell(cell);
				}
				
			}
			else{
				
				tbl_vattu.setWidths(vattu_width4);
				for (int z = 0; z < spTitles4.length; z++) {
					cell = new PdfPCell(new Paragraph(spTitles4[z], font11_bold));
					cell.setPadding(2.0f);
					cell.setPaddingBottom(7);
					cell.setBackgroundColor(BaseColor.WHITE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbl_vattu.addCell(cell);
				}
			}
			
			
//in danh sach san pham 
			
			int  j =0;  
			int sott = 1;
			if(danhsach_sp!=null)
	       		while (j< danhsach_sp.size()) { 

	       			IYeucau sanpham=danhsach_sp.get(j);
	       			String Masp=sanpham.getMa();
	       			String Tensp=sanpham.getTen();
	       			String Donvi=sanpham.getDonViTinh();
	       			
	       			System.out.println("ma san pham:" + Masp);
	       			
	       			//duyet trong thong tin cua sanpham_soluong
	       			
	       			System.out.println("Before Sorting:");
	       			Set set = sanpham_soluong.entrySet();
	                Iterator iterator = set.iterator();
	                while(iterator.hasNext()) {
	                      Map.Entry me = (Map.Entry)iterator.next();
	                      System.out.print(me.getKey() + ": ");
	                      System.out.println(me.getValue());
	                }
	                Map<String, String> map = new TreeMap<String, String>(sanpham_soluong); 
	                System.out.println("After Sorting:");
	                Set set2 = map.entrySet();
	                Iterator iterator2 = set2.iterator();
	                /*while(iterator2.hasNext()) {
	                     Map.Entry me2 = (Map.Entry)iterator2.next();
	                     System.out.print(me2.getKey() + ": ");
	                     System.out.println(me2.getValue());
	                }*/
	       			
	       			
	       			/*Enumeration<String> keys = sanpham_soluong.keys();
	            
	       			
	       			while( keys.hasMoreElements() )
                	{
                		String key = keys.nextElement();
                	
                		if(key.startsWith(sanpham.getMa()))
                		{*/
	                
	                
	                while(iterator2.hasNext()) {
	                	 Map.Entry me2 = (Map.Entry)iterator2.next();
	                	 
	                	 System.out.print(me2.getKey() + ": ");
	                     System.out.println(me2.getValue());
	                     
	                     
	                	 if(String.valueOf(  me2.getKey()).startsWith(sanpham.getMa()))
	                	 {
	                		String[] arr=String.valueOf(me2.getKey()).split("__");
	                		 
                			/*String[] arr = key.split("__");*/
                			String temID = sanpham.getMa() + "__ ";
                			
                		/*	System.out.println("KEY CHI TIET: "+ key);*/
                			String soluongXUAT =String.valueOf(  me2.getValue());
                			/*String soluongXUAT = sanpham_soluong.get(key);*/
                			String solo=arr[2];
                			String ngayhh=arr[3];
                			String ngaynk=arr[4];
                			String me=arr[5];
                			String thung=arr[6];
                			String vitri=arr[7];
                			String maphieu=arr[8];
                			String phieudt=arr[9];
                			String phieueo=arr[10];
                			String marquete=arr[11];
                			String hamluong=arr[12].equals("100")?" ":arr[12] ;
                			String hamam=arr[13].equals("0")?" ":arr[13];
                			String pkn=maphieu;
                			String phieudangki="";
                			//System.out.println("thong tin san pham so lo:" + solo);
                			
                			//day la thong cua san pham
                			//xet loai
                			String[] sp_data1={sott+" ",Masp,Tensp,Donvi, formater.format(Double.parseDouble( soluongXUAT )),pkn,phieueo,me,thung,solo,ngayhh, vitri};
                			String[] sp_data2={sott+" ",Masp,Tensp,Donvi, formater.format(Double.parseDouble( soluongXUAT )),pkn,marquete,phieudangki,solo,ngayhh,vitri};
                			String[] sp_data3={sott+" ",Masp,Tensp,Donvi, formater.format(Double.parseDouble( soluongXUAT )),pkn,hamam,hamluong,phieudt,thung,solo,ngayhh,vitri};
                			String[] sp_data4={sott+" ",Masp,Tensp,Donvi, formater.format(Double.parseDouble( soluongXUAT )),pkn,hamam,hamluong,me,thung,solo,ngayhh,vitri};
                			
                			if(loaikho.equals("1")){
                				for (int z = 0; z < sp_data1.length; z++) {
            						cell = new PdfPCell(new Paragraph(sp_data1[z],new Font(bf, 10,Font.NORMAL)));
            						cell.setPadding(2.0f);
            						cell.setPaddingBottom(7);
            						if(z==1 || z==2||z==10||z==5){
            							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            						}
            						else
            							if(z==4 ){
            								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            							}
            							else
            							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            						tbl_vattu.addCell(cell);
            						}
            						sott++;
                			}
                			if(loaikho.equals("2")){
                				
                				for (int z = 0; z < sp_data2.length; z++) {
            						cell = new PdfPCell(new Paragraph(sp_data2[z],new Font(bf, 10,Font.NORMAL)));
            						cell.setPadding(3.0f);
            						cell.setPaddingBottom(7);
            						if(z==1 || z==2||z==9||z==5){
            							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            						}
            						else
            							if(z==4){
            								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            							}
            							else
            							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            						tbl_vattu.addCell(cell);
            						}
            						sott++;
                			}
                			if(loaikho.equals("3")){
                				
                				for (int z = 0; z < sp_data3.length; z++) {
            						cell = new PdfPCell(new Paragraph(sp_data3[z],new Font(bf, 10,Font.NORMAL)));
            						cell.setPadding(3.0f);
            						cell.setPaddingBottom(7);
            						if(z==1 || z==2||z==11||z==5){
            							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            						}
            						else
            							if(z==4){
            								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            							}
            							else
            							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            						tbl_vattu.addCell(cell);
            						}
            						sott++;
                			}
                			if(loaikho.equals("4")){
                				for (int z = 0; z < sp_data4.length; z++) {
            						cell = new PdfPCell(new Paragraph(sp_data4[z],new Font(bf, 10,Font.NORMAL)));
            						cell.setPadding(3.0f);
            						cell.setPaddingBottom(7);
            						if(z==1 || z==2||z==11){
            							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            						}
            						else
            							if(z==4||z==5){
            								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            							}
            							else
            							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            						tbl_vattu.addCell(cell);
            						}
            						sott++;
                			}
                			
                			
                			
                		}
                	}	
	       			j++;
	       		}
			

			
			
			
			//--------------------- footer
			float[] footer_width = { 3.5f * CONVERT, 3.5f * CONVERT,3.5f * CONVERT,3.5f * CONVERT, 3.5f * CONVERT};
			PdfPTable footer = new PdfPTable(footer_width.length);
			footer.setWidthPercentage(100);
			footer.setHorizontalAlignment(Element.ALIGN_CENTER);
			footer.setWidths(footer_width);
			footer.getDefaultCell().setBorder(0);
			footer.setSpacingAfter(15.0f);
			
			
			
			String ngaychungtu=lsxBean.getNgayyeucau();
			
			
			cell=new PdfPCell();
			
			if(ngaychungtu.length()>8){
				
				cell.addElement(new Paragraph("Hưng Yên, ngày "+ngaychungtu.substring(8)+"  tháng  "+ ngaychungtu.substring(5,7)  + " năm "+ ngaychungtu.substring(0,4) ,font12_normal));		
			}
			else
			cell.addElement(new Paragraph("Hưng Yên, ngày  ...  tháng   ...   năm	                         ",font12_normal));
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setColspan(6);
			cell.setPaddingLeft(19f*CONVERT);
			cell.setBorder(0);
			footer.addCell(cell);
			
			// dong 2
			if(loaikho.equals("1")||loaikho.equals("2")||loaikho.equals("3")){
				cell=new PdfPCell(new Paragraph("T/L. GIÁM ĐỐC",font12_bold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorder(0);
				footer.addCell(cell);
				
				cell=new PdfPCell(new Paragraph("PP.KH-CƯVT",font12_bold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorder(0);
				footer.addCell(cell);
				
				cell=new PdfPCell(new Paragraph("NGƯỜI NHẬN",font12_bold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorder(0);
				footer.addCell(cell);
				
				cell=new PdfPCell(new Paragraph("THỦ KHO",font12_bold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorder(0);
				footer.addCell(cell);
				
				cell=new PdfPCell(new Paragraph("NGƯỜI LẬP PHIẾU",font12_bold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorder(0);
				footer.addCell(cell);
				
				//
				cell=new PdfPCell(new Paragraph("TP.KH-CƯVT",font12_bold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorder(0);
				footer.addCell(cell);
				
				cell=new PdfPCell(new Paragraph("",font12_bold));
				cell.setColspan(4);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorder(0);
				footer.addCell(cell);
				// dong 3---------------------------------
				cell=new PdfPCell(new Paragraph("\n \n \nTrần Công Vĩnh",font12_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(1.5f*CONVERT);
				cell.setBorder(0);
				footer.addCell(cell);
				
				cell=new PdfPCell(new Paragraph("\n \n \nĐỗ Thị Thanh Tâm",font12_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(1.5f*CONVERT);
				cell.setBorder(0);
				footer.addCell(cell);
				
				cell=new PdfPCell(new Paragraph("\n \n \n"+lsxBean.getNguoinhan(),font12_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(1.5f*CONVERT);
				cell.setBorder(0);
				footer.addCell(cell);
				
				
				if(loaikho.equals("3"))	{	
					
					if(lsxBean.getNgaytao().trim().compareTo("2018-06-13")>=0){
						// tu ngay 2018-06-13:lay ten moi
						cell=new PdfPCell(new Paragraph("\n \n \nNguyễn Thị Hải Yến",font12_normal));
						}
						else
						{
							// chưng tu  < 2018-06-13
							cell=new PdfPCell(new Paragraph("\n \n \nHy Thị Thanh",font12_normal));
						}
					
				}
				if(loaikho.equals("1"))		
					cell=new PdfPCell(new Paragraph("\n \n \n Trương Thị Hồng Nhung",font12_normal));
				if(loaikho.equals("2"))
					cell=new PdfPCell(new Paragraph("\n \n \n Nguyễn Thu Hà",font12_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(1.5f*CONVERT);
				cell.setBorder(0);
				footer.addCell(cell);
				
				
				cell=new PdfPCell(new Paragraph("\n \n \n Đoàn Xuân Thông",font12_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPaddingTop(1.5f*CONVERT);
				cell.setBorder(0);
				footer.addCell(cell);
			}
			else{
			cell=new PdfPCell();
			cell.addElement(new Paragraph("      GIÁM ĐỐC",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(0);
			footer.addCell(cell);
			
			cell=new PdfPCell();
			cell.addElement(new Paragraph("PP.KH-CƯVT",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footer.addCell(cell);
			
			cell=new PdfPCell();
			cell.addElement(new Paragraph("NGƯỜI NHẬN",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footer.addCell(cell);
			
			cell=new PdfPCell();
			cell.addElement(new Paragraph("THỦ KHO",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footer.addCell(cell);
			
			cell=new PdfPCell();
			cell.addElement(new Paragraph("NGƯỜI LẬP PHIẾU",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(0);
			footer.addCell(cell);
			
			
			// dong 3
			cell=new PdfPCell();
			cell.addElement(new Paragraph("\n \n \n Phạm Thị Thanh Duyên",font12_normal));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(0);
			footer.addCell(cell);
			
			
			cell=new PdfPCell();
			cell.addElement(new Paragraph(" ",font12_bold));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setColspan(5);
			cell.setBorder(0);
			footer.addCell(cell);
			}
			
			//--------------------
			
			
			
			
			document.add(hinhanh);
			document.add(table2); //header + tieu de
			/*document.add(table3);*/
			document.add(tbl);	 //thong tin
			document.add(tbl_vattu); //bang du lieu
			document.add(footer);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception print PDF: " + e.getMessage());
		}
		
	}
	

	private void Create_Phieugiaonhan(Document document,
			ServletOutputStream outstream, HttpServletResponse response,
			HttpServletRequest request, dbutils db,String id,String loaikho, String  userId)
			throws UnsupportedEncodingException {
		
		
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 
		NumberFormat formater = new DecimalFormat("##,###,###.##########");
		//lay phieu chuyen kho
		IErpChuyenkho lsxBean = new ErpChuyenkho(id);
	    lsxBean.setUserId(userId);
	    lsxBean.init();
		
		//lay danh sach vat tu
	    danhsach_sp = (List<IYeucau>)lsxBean.getSpList(); 
		Hashtable<String, String> sanpham_soluong = lsxBean.getSanpham_Soluong();
	    //lay thong tin tung vat tu
		
		String lydoxuat=lsxBean.getLydoyeucau();
		//lay kho xuat
		String tenkhoxuat="";
		ResultSet khoxuatRs=lsxBean.getKhoXuatRs();
		if(khoxuatRs != null)
		{
			try {
				while(khoxuatRs.next()){
					if( khoxuatRs.getString("pk_seq").equals(lsxBean.getKhoXuatId())){
						tenkhoxuat=khoxuatRs.getString("ten");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		//noi dung form
		try {
			
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			//dinh dang font chu
			Font font9_italic = new Font(bf,10,Font.ITALIC);
			font9_italic.setColor(79, 79, 79);
			Font font11_bold = new Font(bf, 11, Font.BOLD);
			Font font11_normal = new Font(bf, 12, Font.BOLD);
			Font font11_italic = new Font(bf, 11, Font.ITALIC);
			Font font13_bold = new Font(bf, 13, Font.BOLD);
			Font font13_normal = new Font(bf, 13, Font.NORMAL);
			//Font font13_bold_underline = new Font(bf, 13, Font.UNDERLINE);
			Font font13_bold_italic = new Font(bf, 13, Font.BOLDITALIC);
			Font font13_italic = new Font(bf, 13, Font.ITALIC);
			
			Font font12_normal = new Font(bf, 12, Font.NORMAL);
			Font font12_bold = new Font(bf, 12, Font.BOLD);
			Font font12_italic = new Font(bf, 12, Font.ITALIC);
			
			float CONVERT = 28.346457f;// 1 cm
		//	float[] TABLE_HEADER_WIDTHS = new float[] {9.25f * CONVERT ,11.25f * CONVERT };
			PdfPCell cell;
			Paragraph p;
			
			
			//=---------------------------tao header1
			PdfPTable table2 = new PdfPTable(2);	
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			//table2.setWidths(TABLE_HEADER_WIDTHS);
			table2.setWidthPercentage(100);
			
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("pathPrint")+"\\logo.gif");
			hinhanh.scalePercent(10);
			hinhanh.setAbsolutePosition(4.5f * CONVERT, document.getPageSize().getHeight() - 1.4f * CONVERT);
			
			cell = new PdfPCell(new Paragraph(" ",font12_normal));
			cell.setBorder(0);
			table2.addCell(cell);
			
			
			if( lsxBean.getNgayyeucau().compareTo("2017-08-11") >0){
				cell = new PdfPCell(new Paragraph("BM 28/07",font11_italic));
			}
			else
				cell = new PdfPCell(new Paragraph("BM 53/12",font11_italic));
			
			cell.setBorder(0);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("CÔNG TY TNHH TRAPHACO HƯNG YÊN",font12_normal));
			cell.setBorder(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("BH/SĐ:03/07/17",font11_italic));
			cell.setBorder(0);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			table2.addCell(cell);
			
			//----------------------tua de
			cell = new PdfPCell();
			p = new Paragraph();
			p.setSpacingBefore(15);
			p.add(new Chunk("\n  PHIẾU GIAO NHẬN VẬT TƯ\n", new Font(bf, 18, Font.BOLD)));
			p.add(new Chunk("Số: " +lsxBean.getId(), font12_normal)); 
			cell.setBorder(0);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(2);
			cell.addElement(p);
			table2.addCell(cell);
			
			// ----------------------thong tin
			
			/*PdfPTable table3 = new PdfPTable(1);	
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			//table2.setWidths(TABLE_HEADER_WIDTHS);
			table2.setWidthPercentage(100.0f);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.setSpacingBefore(10);
			p.add(new Chunk("Họ và tên người nhận hàng:\n", font12_normal));
			p.add(new Chunk("Địa chỉ:\n", font12_normal));
			p.add(new Chunk("Lý do xuất:\n", font12_normal));
			p.add(new Chunk("Xuất tại kho: Kho phụ liệu cấp 2\n", font12_normal));
			cell.setBorder(0);
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
		
			cell.addElement(p);
			table3.addCell(cell);*/
			
			PdfPTable tbl = new PdfPTable(1);
			tbl.setWidthPercentage(100);
			tbl.setSpacingBefore(0);
			tbl.getDefaultCell().setBorder(0);
			
			cell = new PdfPCell(new Paragraph("Họ và tên người nhận hàng: " + lsxBean.getNguoinhan(), font12_normal));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tbl.addCell(cell);
			
			//TIM DIA CHI THEO DIA CHI CUA KHO NHAN
			String diachi="";
			if (lsxBean.getKhoNhapId()!= null )
			if(lsxBean.getKhoNhapId().length()>0){
				String sql= "select ten as ten from ERP_KHOTT where pk_seq=" +lsxBean.getKhoNhapId();
				ResultSet rs= db.get(sql);
				if(rs!= null)
				{
					while(rs.next()){
						diachi=rs.getString("ten");
					}
				}
			}
			
			cell = new PdfPCell(new Paragraph("Địa chỉ: "+diachi, font12_normal));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tbl.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Lý do xuất: "+lydoxuat, font12_normal));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tbl.addCell(cell);
			
			
			
			
			cell = new PdfPCell(new Paragraph("Xuất tại kho: " +tenkhoxuat, font12_normal));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			tbl.addCell(cell);
			
			
			//bang du lieu
			//xet loai xet tieu de
			float[] vattu_width1 = { 0.5f * CONVERT, 1.0f * CONVERT,
					2.5f * CONVERT, 0.8f * CONVERT, 0.8f * CONVERT,
					1.2f * CONVERT, 0.8f * CONVERT, 0.6f * CONVERT,
					0.6f * CONVERT, 1.3f * CONVERT,0.9f * CONVERT, 1.0f * CONVERT};
			
			float[] vattu_width2 = { 0.5f * CONVERT, 1.0f * CONVERT,
					2.2f * CONVERT, 0.8f * CONVERT, 0.8f * CONVERT,
					1.2f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.0f * CONVERT, 0.8f * CONVERT,1.0f * CONVERT};
			
			float[] vattu_width3 = { 0.5f * CONVERT, 1.0f * CONVERT,
					2.5f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.5f * CONVERT, 0.8f * CONVERT, 0.8f * CONVERT,
					1.2f * CONVERT, 0.8f * CONVERT,1.3f * CONVERT,1.0f * CONVERT, 1.0f * CONVERT};
			
			float[] vattu_width4 = { 0.5f * CONVERT, 1.0f * CONVERT,
					2.3f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					1.5f * CONVERT, 0.8f * CONVERT, 0.8f * CONVERT,
					0.8f * CONVERT, 0.8f * CONVERT,1.5f * CONVERT,
					1.0f * CONVERT,1.0f * CONVERT};
			
			String[] spTitles1 = { "TT","Mã VT","Tên hàng", "Đơn vị ", "Số lượng", "Số PKN", "Số lô EO","Mẻ","Thùng", "Số lô NSX","Ngày HH",  "Vị trí" };
			String[] spTitles2 = { "TT","Mã VT","Tên hàng", "Đơn vị ", "Số lượng", "Số PKN", "Maquette","Số đăng ký", "Số lô NSX","Ngày HH",  "Vị trí" };
			String[] spTitles3 = { "TT","Mã VT","Tên hàng", "Đơn vị ", "Số lượng", "Số PKN", "Hàm ẩm","Hàm lượng","Phiếu định tính","Thùng ", "Số lô NSX","Ngày HH",  "Vị trí" };
			String[] spTitles4 = { "TT","Mã VT","Tên hàng", "Đơn vị ", "Số lượng", "Số PKN", "Hàm ẩm","Hàm lượng","Mẻ","Thùng", "Số lô NSX","Ngày HH",  "Vị trí" };
			int socot;
			if(loaikho.equals("1")){
				socot=vattu_width1.length;
			}
			else if(loaikho.equals("2")){
				socot=vattu_width2.length;
			}
			else if(loaikho.equals("3")){
				socot=vattu_width3.length;
			}
			else {
				socot=vattu_width4.length;
			}
			
			PdfPTable tbl_vattu=new PdfPTable(socot);
			tbl_vattu.setWidthPercentage(100);
			tbl_vattu.setSpacingBefore(10);
			tbl_vattu.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbl_vattu.getDefaultCell().setBorder(0);
			tbl_vattu.setSpacingAfter(8.0f);
			if(loaikho.equals("1")){
				
				tbl_vattu.setWidths(vattu_width1);
				//in tieu de
				for (int z = 0; z < spTitles1.length; z++) {
					cell = new PdfPCell(new Paragraph(spTitles1[z], font11_bold));
					cell.setPadding(3.0f);
					cell.setPaddingBottom(7);
					cell.setBackgroundColor(BaseColor.WHITE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tbl_vattu.addCell(cell);
				}
				
			}
			else
			if(loaikho.equals("2")){
				
				tbl_vattu.setWidths(vattu_width2);
				//in tieu de
				for (int z = 0; z < spTitles2.length; z++) {
					cell = new PdfPCell(new Paragraph(spTitles2[z], font11_bold));
					cell.setPadding(3.0f);
					cell.setPaddingBottom(7);
					cell.setBackgroundColor(BaseColor.WHITE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tbl_vattu.addCell(cell);
				}
				
			}
			else
			if(loaikho.equals("3")){
				
				tbl_vattu.setWidths(vattu_width3);
				for (int z = 0; z < spTitles3.length; z++) {
					cell = new PdfPCell(new Paragraph(spTitles3[z], font11_bold));
					cell.setPadding(3.0f);
					cell.setPaddingBottom(7);
					cell.setBackgroundColor(BaseColor.WHITE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tbl_vattu.addCell(cell);
				}
				
			}
			else{
				
				tbl_vattu.setWidths(vattu_width4);
				for (int z = 0; z < spTitles4.length; z++) {
					cell = new PdfPCell(new Paragraph(spTitles4[z], font11_bold));
					cell.setPadding(3.0f);
					cell.setPaddingBottom(7);
					cell.setBackgroundColor(BaseColor.WHITE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tbl_vattu.addCell(cell);
				}
			}
			
			
//in danh sach san pham 
			
			int  j =0;  
			int sott = 1;
			if(danhsach_sp!=null)
	       		while (j< danhsach_sp.size()) { 

	       			IYeucau sanpham=danhsach_sp.get(j);
	       			String Masp=sanpham.getMa();
	       			String Tensp=sanpham.getTen();
	       			String Donvi=sanpham.getDonViTinh();
	       			
	       			
	       			

	       			System.out.println("Before Sorting:");
	       			Set set = sanpham_soluong.entrySet();
	                Iterator iterator = set.iterator();
	                while(iterator.hasNext()) {
	                      Map.Entry me = (Map.Entry)iterator.next();
	                      System.out.print(me.getKey() + ": ");
	                      System.out.println(me.getValue());
	                }
	                Map<String, String> map = new TreeMap<String, String>(sanpham_soluong); 
	                System.out.println("After Sorting:");
	                Set set2 = map.entrySet();
	                Iterator iterator2 = set2.iterator();
	               
	                
	                
	                while(iterator2.hasNext()) {
	                	 Map.Entry me2 = (Map.Entry)iterator2.next();
	                	 
	                	 System.out.print(me2.getKey() + ": ");
	                     System.out.println(me2.getValue());
	                     
	                     
	                	 if(String.valueOf(  me2.getKey()).startsWith(sanpham.getMa()))
	                	 {
	                		String[] arr=String.valueOf(me2.getKey()).split("__");
	                		 
                			/*String[] arr = key.split("__");*/
                			String temID = sanpham.getMa() + "__ ";
                			
                		/*	System.out.println("KEY CHI TIET: "+ key);*/
                			String soluongXUAT =String.valueOf(  me2.getValue());
                			/*String soluongXUAT = sanpham_soluong.get(key);*/
	       			
	       			
	       			
                			String solo=arr[2];
                			String ngayhh=arr[3];
                			String ngaynk=arr[4];
                			String me=arr[5];
                			String thung=arr[6];
                			String vitri=arr[7];
                			String maphieu=arr[8];
                			String phieudt=arr[9];
                			String phieueo=arr[10];
                			String marquete=arr[11];
                			/*String hamluong=arr[12];
                			String hamam=arr[13];*/
                			String hamluong=arr[12].equals("100")?" ":arr[12] ;
                			String hamam=arr[13].equals("0")?" ":arr[13];
                			String pkn=maphieu;
                			String phieudangki="";
                			
                			System.out.println("thong tin san pham so lo:" + solo);
                			
                			//day la thong cua san pham
                			//xet loai
                			String[] sp_data1={sott+" ",Masp,Tensp,Donvi, formater.format(Double.parseDouble( soluongXUAT )),pkn,phieueo,me,thung,solo,ngayhh, vitri};
                			String[] sp_data2={sott+" ",Masp,Tensp,Donvi, formater.format(Double.parseDouble( soluongXUAT )),pkn,marquete,phieudangki,solo,ngayhh, vitri};
                			String[] sp_data3={sott+" ",Masp,Tensp,Donvi, formater.format(Double.parseDouble( soluongXUAT )),pkn,hamam,hamluong,phieudt,thung,solo,ngayhh,vitri};
                			String[] sp_data4={sott+" ",Masp,Tensp,Donvi, formater.format(Double.parseDouble( soluongXUAT )),pkn,hamam,hamluong,me,thung,solo,ngayhh,vitri};
                			
                			if(loaikho.equals("1")){
                				for (int z = 0; z < sp_data1.length; z++) {
            						cell = new PdfPCell(new Paragraph(sp_data1[z],new Font(bf, 10,Font.NORMAL)));
            						cell.setPadding(2.0f);
            						cell.setPaddingBottom(7);
            						if(z==1 || z==2||z==10||z==5){
            							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            						}
            						else
            							if(z==4 ){
            								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            							}
            							else
            							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            						tbl_vattu.addCell(cell);
            						}
            						sott++;
                			}
                			if(loaikho.equals("2")){
                				
                				for (int z = 0; z < sp_data2.length; z++) {
            						cell = new PdfPCell(new Paragraph(sp_data2[z],new Font(bf, 10,Font.NORMAL)));
            						cell.setPadding(2.0f);
            						cell.setPaddingBottom(7);
            						if(z==1 || z==2||z==9||z==5){
            							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            						}
            						else
            							if(z==4){
            								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            							}
            							else
            							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            						tbl_vattu.addCell(cell);
            						}
            						sott++;
                			}
                			if(loaikho.equals("3")){
                				
                				for (int z = 0; z < sp_data3.length; z++) {
            						cell = new PdfPCell(new Paragraph(sp_data3[z],new Font(bf, 10,Font.NORMAL)));
            						cell.setPadding(2.0f);
            						cell.setPaddingBottom(7);
            						if(z==1 || z==2||z==11||z==5){
            							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            						}
            						else
            							if(z==4){
            								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            							}
            							else
            							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            						tbl_vattu.addCell(cell);
            						}
            						sott++;
                			}
                			if(loaikho.equals("4")){
                				for (int z = 0; z < sp_data4.length; z++) {
            						cell = new PdfPCell(new Paragraph(sp_data4[z],new Font(bf, 10,Font.NORMAL)));
            						cell.setPadding(2.0f);
            						cell.setPaddingBottom(7);
            						if(z==1 || z==2||z==11){
            							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            						}
            						else
            							if(z==4||z==5){
            								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            							}
            							else
            							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            						tbl_vattu.addCell(cell);
            						}
            						sott++;
                			}
                			
                			
                			
                		}
                	}	
	       			j++;
	       		}
			

			
			//--------------------- footer
			float[] footer_width = { 5.0f * CONVERT,5.0f * CONVERT, 5.0f * CONVERT};
			PdfPTable footer = new PdfPTable(3);
			footer.setWidthPercentage(100);
			footer.setHorizontalAlignment(Element.ALIGN_CENTER);
			footer.setWidths(footer_width);
			footer.getDefaultCell().setBorder(0);
			footer.setSpacingAfter(15.0f);
			
			
			
			String ngaychungtu=lsxBean.getNgayyeucau();
			
			
			/*cell=new PdfPCell();
			
			if(ngaychungtu.length()>8){
				
				cell.addElement(new Paragraph("Hà Nội, ngày "+ngaychungtu.substring(8)+"  tháng "+ ngaychungtu.substring(5,7)  + " năm "+ ngaychungtu.substring(0,4) ,font12_normal));		
			}
			else
			cell.addElement(new Paragraph("Hà Nội, ngày  ...  tháng   ...   năm	                         ",font12_normal));
			
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setColspan(5);
			cell.setPaddingLeft(19f*CONVERT);
			cell.setBorder(0);
			footer.addCell(cell);*/
			
			Paragraph hd=new Paragraph();
			
			cell=new PdfPCell();
			p=new Paragraph("Ngày  ...  tháng  ...  năm       ",font12_normal);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footer.addCell(cell);
			
			cell=new PdfPCell();
			p=new Paragraph("Ngày  ...  tháng  ...  năm       ",font12_normal);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footer.addCell(cell);
			
			cell=new PdfPCell();
			p=new Paragraph("Ngày  ...  tháng  ...  năm       ",font12_normal);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footer.addCell(cell);
			
			
			
			cell=new PdfPCell();
			p=new Paragraph("Người nhận",new Font(bf, 14, Font.BOLD));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footer.addCell(cell);
			
			cell=new PdfPCell();
			p=new Paragraph("Đảm bảo chất lượng",new Font(bf, 14, Font.BOLD));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footer.addCell(cell);
			
			
			cell=new PdfPCell();
			p=new Paragraph("Thủ kho",new Font(bf, 14, Font.BOLD));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			footer.addCell(cell);
			
			//--------------------
			
			
			
			document.add(hinhanh);
			document.add(table2); //header + tieu de
			/*document.add(table3);*/
			document.add(tbl);	 //thong tin
			document.add(tbl_vattu); //bang du lieu
			document.add(footer);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception print PDF: " + e.getMessage());
		}
		
	}
	
	
String formatngay(String ngay){
		
		if(ngay!=null){
		if(ngay.length()>0)
		return ngay.substring(8)+"/"+ngay.substring(5,7)+"/"+ngay.substring(0,4);
		else
			return " ";
		}
		else
			return " ";
	}
	

		public static void main ( String args [  ]  )   {
			String s1= "2017-08-10";
			String s2= "2017-08-11";
			System.out.println( "gia tri so sanh : "+ s1.compareTo(s2));
			  
		}
	
}
