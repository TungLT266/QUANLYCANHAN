package geso.traphaco.distributor.servlets.hoadontaichinhKM;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.distributor.beans.hoadontaichinhKM.IHoadontaichinhKM;
import geso.traphaco.distributor.beans.hoadontaichinhKM.IHoadontaichinhKMList;
import geso.traphaco.distributor.beans.hoadontaichinhKM.imp.HoadontaichinhKM;
import geso.traphaco.distributor.beans.hoadontaichinhKM.imp.HoadontaichinhKMList;
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


public class HoadontaichinhKMNPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public HoadontaichinhKMNPdfSvl()
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
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		String ddkd = util.antiSQLInspection(request.getParameter("ddkd"));
		String congtyId = (String)session.getAttribute("congtyId");
		
		IHoadontaichinhKM pxkBean = new HoadontaichinhKM(id);
		pxkBean.setUserId(userId);
		pxkBean.setNppId(nppId);
		System.out.println("nppId:"+nppId);

	
		if (!id.equals(""))
		{
			//pxkBean.initPdf();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=HoaDonKhuyenMai.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
				   
			
			String msg =CreateHoaDonKM(document,outstream,pxkBean, tungay,denngay,id, ddkd, congtyId, nppId);
			if(!msg.equals(""))
			{
				IHoadontaichinhKMList   obj = new HoadontaichinhKMList();
				 obj.setUserId(userId);
				obj.init("");
				obj.setMsg(msg);		    
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKMN.jsp";
				response.sendRedirect(nextJSP);
			}
			//.String msg = this.CapnhatTT(id);
		//	pxkBean.setMsg(msg);
		} 
	}
	
	
	

	
	private String CreateHoaDonKM(Document document,ServletOutputStream outstream, IHoadontaichinhKM pxkBean, String tungay,String denngay,String id, String ddkd, String congtyId, String nppId) 
	{
		String msg="";
		try
		{			
		dbutils db = new dbutils();
		NumberFormat formatter = new DecimalFormat("#,###,###.###");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setMargins(1.0f*CONVERT, 0.0f*CONVERT, 3.0f*CONVERT, 2.0f*CONVERT); // L,R, T, B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open();
		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		String hoadon =

		"	SELECT distinct A.PK_SEQ "+
		"	FROM ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_SP_CHITIET B ON A.PK_sEQ = B.HOADON_FK \n"+
		"	WHERE LEN(ISNULL(B.SCHEME,'')) >0 AND A.TRANGTHAI IN (2,4) AND A.CONGTY_FK = "+congtyId+" AND A.NPP_FK = "+nppId;
		if(ddkd.trim().length()>0)
			hoadon+=" AND A.KHACHHANG_FK IN (SELECT KHACHHANG_FK FROM ddkd_khachhang WHERE ddkd_fk = "+ddkd+") \n";
		if(tungay.trim().length()>0)
			hoadon+= " AND A.NGAYXUATHD >= '"+tungay+"'";
		if(denngay.trim().length()>0)
			hoadon+= " AND A.NGAYXUATHD <= '"+denngay+"'";
		
		ResultSet rs=db.get(hoadon);
		
		while(rs.next())
		{							
			String nguoimua = "";
			String donvi = "";
			String masothue = "";
			String diachi = "";
			String HTTT = "";
			String sotaikhoan = "";
			
			String INIT_THONGTINKH =
				"select isnull(c.ChietKhau,0) as ptchietkhau,a.kho_fk,dondathangnpp_fk, a.nhomkenh_fk, a.npp_fk, ngayxuatHD, ISNULL(a.ghichu, '') as ghichu, "+
				"  		a.khachhang_fk, a.npp_dat_fk, a.trangthai, kyhieu, sohoadon, hinhthuctt ,  isnull(a.chietkhau,0) as chietkhau,"+
				"  		(case when nguoimua is null and a.khachhang_fk is not null then isnull(d.chucuahieu,'')  " +   
				"  		else  isnull(nguoimua,'') end) as nguoimua, isnull(innguoimua,1) as innguoimua,  isnull(tongtienbvat,0) as tongtienbvat,  isnull(tongtienavat,0)" +
				"   	as tongtienavat,  isnull(a.vat, 0) as vat, isnull(a.chietkhau, 0) as chietkhau, loaixuatHD,isnull(mavv,'') as mavv, "+
				"		(case a.loaixuatHD when 0 then e.TEN else (case when LEN(isnull(a.TENXUATHD,''))>0 then a.TENXUATHD else isnull(d.TEN,'') end) end ) as donvi,  "+
				"		(case a.loaixuatHD when 0 then isnull(e.MASOTHUE,'') else isnull(d.MASOTHUE,'') end ) as MASOTHUE, "+
				"		(case a.loaixuatHD when 0 then isnull(e.DIACHI,'') else isnull(d.DIACHI,'') end ) as DIACHI "+
				"from 	ERP_HOADONNPP a"+
				"   	inner join ERP_HOADONNPP_DDH b on b.HOADONNPP_FK=a.PK_SEQ "+
				"   	inner join ERP_DONDATHANGNPP c on c.PK_SEQ=b.DDH_FK"+
				"		left join KHACHHANG d on a.khachhang_fk = d.pk_seq "+
				"		left join NHAPHANPHOI e on a.npp_dat_fk = e.pk_seq "+
				"where a.pk_seq = '"+ rs.getString("PK_SEQ")  +"'";
			
							
		   ResultSet rsINFO = db.get(INIT_THONGTINKH);
		   if(rsINFO.next())
		   {
			   nguoimua = rsINFO.getString("nguoimua");
			   donvi = rsINFO.getString("donvi");
			   masothue = rsINFO.getString("MASOTHUE");
			   diachi = rsINFO.getString("DIACHI");
			   //sotaikhoan = rsINFO.getString("SOTAIKHOAN");
			   HTTT = rsINFO.getString("hinhthuctt");
			   rsINFO.close();
			   
		   }
				
	
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		//HỌ VÀ TÊN NGƯỜI MUA HÀNG
		PdfPCell cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);			
		
		Paragraph hd = new Paragraph(nguoimua , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setFixedHeight(0.8f*CONVERT);
		cell.setPaddingLeft(1.5f*CONVERT);
		cell.setPaddingTop(0.85f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);		

		tableheader.addCell(cell);
		
		//TÊN ĐƠN VỊ
		
		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);			
		
		hd = new Paragraph(donvi , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setFixedHeight(0.8f*CONVERT);
		cell.setPaddingLeft(1.8f*CONVERT);
		//cell.setPaddingTop(0.85f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);		

		tableheader.addCell(cell);
		
		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		//MÃ SỐ THUẾ
		
		
		//CẮT MÃ SỐ THUẾ
		String strMST_01[] = masothue.split("");		
		String [] MST = masothue.split("");
		int j = 0;
		
        for (int i = 0; i<masothue.trim().length();i++) {
           
            if (strMST_01[i].endsWith(" ")) {
            	
            } else if (strMST_01[i].isEmpty()) {
            	
            } else {
            	MST[j] = strMST_01[i];
            	j++;
            }
           
        }
		
        String mst = "";
        for(int k = 0; k < j;k++){
        	if(k==0)
        		mst += MST[k];
        	else
        		mst += "    "+ MST[k];
        }
        
        hd = new Paragraph(mst , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setFixedHeight(0.8f*CONVERT);
		cell.setPaddingLeft(1.8f*CONVERT);
		//cell.setPaddingTop(0.85f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);		

		tableheader.addCell(cell);
      
        
        //ĐỊA CHỈ 
		
		cell = new PdfPCell();	
		cell.setVerticalAlignment(Element.ALIGN_LEFT);			
		
		hd = new Paragraph(diachi , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setFixedHeight(0.8f*CONVERT);
		cell.setPaddingLeft(1.8f*CONVERT);
		//cell.setPaddingTop(0.85f*CONVERT);
		cell.setBorder(0);
		cell.addElement(hd);		

		tableheader.addCell(cell);
        
		document.add(tableheader);
					
		
		// Thông tin Khach Hang
		PdfPTable table1 =new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
		table1.setWidths(withs1);									
						
		
		// HÌNH THỨC THANH TOÁN
		cell = new PdfPCell();	
		hd = new Paragraph(HTTT  , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		//cell.setPaddingTop(0.7f*CONVERT);
		cell.setFixedHeight(0.8f*CONVERT);
		cell.setPaddingLeft(1.8f*CONVERT);
		cell.addElement(hd);
		cell.setBorder(0);						
		table1.addCell(cell);
		

		// SỐ TÀI KHOẢN
		cell = new PdfPCell();	
		hd = new Paragraph(sotaikhoan  , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		//cell.setPaddingTop(0.7f*CONVERT);
		cell.setPaddingLeft(1.8f*CONVERT);
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

		String[] th = new String[]{ " ", " ", " ", " ", " "," " ," ","","","",""};
		
		PdfPTable sanpham = new PdfPTable(th.length);
		sanpham.setSpacingBefore(1.5f*CONVERT);
		sanpham.setWidthPercentage(100);
		sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		float[] withsKM = { 0.7f*CONVERT,7.3f*CONVERT, 2.5f*CONVERT, 3.0f*CONVERT, 1.5f*CONVERT, 1.5f*CONVERT, 2.0f*CONVERT,2.5f*CONVERT, 1.0f*CONVERT,2.5f*CONVERT, 2.5f*CONVERT };
		sanpham.setWidths(withsKM);
		
		String INIT_SANPHAM = 
		"	SELECT  hoadon_fk, MA, TEN,SOLO, NGAYHETHAN, DONVI, SOLUONG, DONGIA, round(SOLUONG*DONGIA,0) THANHTIEN , THUEVAT, \n"+ 
		"			ISNULL(TIENTHUE, ( ( round( soluong * dongia, 0 ) - chietkhau ) * isnull(THUEVAT, 0) / 100 )) as TIENVAT \n"+ 
		"	FROM 	ERP_HOADONNPP_SP_CHITIET \n"+ 
		"	WHERE 	LEN(ISNULL(SCHEME,'')) >0 AND HOADON_FK = "+rs.getString("PK_SEQ");
		
		ResultSet rsSP= db.get(INIT_SANPHAM);
		
		PdfPCell cells = new PdfPCell();
		
		int stt = 0;
		String tensp = "", solo = "", handung = "", DVT = "";
		double soluong = 0, dongia = 0, thanhtien = 0, thuevat = 0, tienvat = 0, tienavat = 0;
		double sum_thanhtien = 0, sum_tienvat = 0, sum_tienavat = 0;
		double sum_thanhtien_5 = 0, sum_thanhtien_10 = 0, sum_tienvat_5 = 0, sum_tienvat_10 = 0, sum_tienavat_5 = 0, sum_tienavat_10 = 0 ; 
		
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
				tienvat = rsSP.getDouble("TIENVAT");
				tienavat = thanhtien + tienvat;
				
				//DÒNG TỔNG CỘNG TIỀN HÀNG
				sum_thanhtien += thanhtien;
				sum_tienvat += tienvat;
				sum_tienavat += tienavat;
				
				//TỔNG CỘNG THUẾ SUẤT 5%
				if(thuevat==5)
				{
					sum_thanhtien_5 += thanhtien;
					sum_tienvat_5 += tienvat;
					sum_tienavat_5 += tienavat;
				}
				
				if(thuevat==10)
				{
					sum_thanhtien_10 += thanhtien;
					sum_tienvat_10 += tienvat;
					sum_tienavat_10 += tienavat;
				}
				
				// HÀM CẮT CHUỖI
				
				int vitri= 0;
				int dodaichuoi = tensp.length();
				int sodong_nguyen = dodaichuoi/15;
				int sodong_du = dodaichuoi%15;
				
				if(sodong_du>0) sodong_nguyen++;
				
				String chuoibd = tensp;
				String chuoithu = "";
				String chuoitiep = "";
				String chuoiin = "";
								
				for (int m = 0; m<sodong_nguyen; m ++){
					
					dodaichuoi = chuoibd.length();
					
					if(dodaichuoi>=15)
					{
						System.out.println("ABC:"+chuoibd.substring(0, 14));
						chuoithu = chuoibd.substring(0, 14);
						vitri = chuoithu.lastIndexOf(" ");
						chuoiin = chuoithu.substring(0,vitri);
						chuoitiep = chuoibd.substring(vitri + 1,dodaichuoi );	
						//chuoiin = chuoitiep;
					}
					
					else{
						chuoiin = chuoibd;
					}
					
					
					String[] arr = null;
					if(sodong_nguyen<=0){
						arr = new String[] { Integer.toString(stt), tensp , solo,handung, DVT,formatter1.format(soluong), formatter1.format(dongia),
								formatter1.format(thanhtien), formatter1.format(thuevat) ,formatter1.format(tienvat), formatter1.format(tienavat)  };
					}
					else if(sodong_nguyen >=1){
						if(m == 0){
							arr = new String[] { Integer.toString(stt), chuoiin , solo,handung, DVT,formatter1.format(soluong), formatter1.format(dongia),
									formatter1.format(thanhtien), formatter1.format(thuevat) ,formatter1.format(tienvat), formatter1.format(tienavat)  };
						}
						else{									
							arr = new String[] { "", chuoiin , "" , "" , "","" , "","","","","" };
						}
					}
					
									
					for (int i = 0; i < th.length; i++)
					{
						cells = new PdfPCell(new Paragraph(arr[i], new Font(bf, 11, Font.NORMAL)));
						
						
						if(i==0||i>=5)
						{
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						else{
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							
						}	
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						
						if(i==1)
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						
						cells.setBorder(0);
						//cells.setBorderWidth(1);
						cells.setFixedHeight(0.8f * CONVERT);	
						sanpham.addCell(cells);
						chuoibd = chuoitiep;
					}
				}
			}
		}
		rsSP.close();
				
		// DONG TRONG
		int kk=0;
		while(kk < 12-stt)
		{
			String[] arr_bosung = new String[] { " ", " ", " ", " ", " "," " ," ","","","",""};

			for (int i = 0; i < th.length; i++)
			{
				cells = new PdfPCell(new Paragraph(arr_bosung[i], new Font(bf, 11, Font.NORMAL)));
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setFixedHeight(0.8f*CONVERT);
				cells.setBorder(0);
									
				sanpham.addCell(cells);
			}
			
			kk++;
		}
		
		
		String [] arr_tienhang = new String[] {"","" ,"" ,"","","", "",formatter1.format(sum_thanhtien),"",formatter1.format(sum_tienvat),formatter1.format(sum_tienavat) };
		for (int i = 0; i < arr_tienhang.length; i++)
		{
			cells = new PdfPCell(new Paragraph(arr_tienhang[i], new Font(bf, 11 , Font.BOLD)));
						
			if(i==0||i>=5)
			{
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			}
			else{
				cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				
			}	
			cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
			
			if(i==1)
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			cells.setBorder(0);
			//cells.setBorderWidth(1);
			cells.setFixedHeight(0.8f * CONVERT);	
			sanpham.addCell(cells);
		}
		
		document.add(sanpham);
		
		
		//ĐỌC TIỀN RA CHỮ
		
		// Tien bang chu
		doctienrachu doctien = new doctienrachu();
		
		String sotien = formatter1.format(sum_tienavat);
		System.out.println(sotien);
		
		String tien = doctien.docTien( Long.parseLong(sotien.replaceAll(",", "")) );
		
	  //Viết hoa ký tự đầu tiên
	    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
	    
	    
				    //String tien = doctien.tranlate(tongtiencovat + "");
		tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
		if(tien.equals("Đồng"))
			 tien="Không Đồng";
		
		PdfPTable table_tien =new PdfPTable(1);
		table_tien.setWidthPercentage(100);
		float[] withstien = {15.0f * CONVERT};
		table_tien.setWidths(withstien);									
						
		
		// HÌNH THỨC THANH TOÁN
		cell = new PdfPCell();	
		hd = new Paragraph(tien +"." , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_LEFT);
		cell.setFixedHeight(0.8f*CONVERT);
		cell.setPaddingLeft(1.8f*CONVERT);
		cell.addElement(hd);
		cell.setBorder(0);						
		table_tien.addCell(cell);
		
		document.add(table_tien);
		
		PdfPTable footer = new PdfPTable(th.length);
		footer.setSpacingBefore(1.5f*CONVERT);
		footer.setWidthPercentage(100);
		footer.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		footer.setWidths(withsKM);
		
		String [] arr_footer_5 = new String[] {"","" ,"" ,"","","", "",formatter1.format(sum_thanhtien_5),"5",formatter1.format(sum_tienavat_5),formatter1.format(sum_tienavat_5) };
		String [] arr_footer_10 = new String[] {"","" ,"" ,"","","", "",formatter1.format(sum_thanhtien_10),"10",formatter1.format(sum_tienavat_10),formatter1.format(sum_tienavat_10) };
		String [] array = null;
		
		for(int k =0; k<3; k++){
			if(k==0) array = arr_footer_5;
			else if(k==1) array = arr_footer_10;
			else array = arr_tienhang;
						
			for (int i = 0; i < array.length; i++)
			{
				cells = new PdfPCell(new Paragraph(array[i], new Font(bf, 11 , Font.BOLD)));
							
				if(i==0||i>=5)
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				else{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					
				}	
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				
				if(i==1)
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				cells.setBorder(0);
				//cells.setBorderWidth(1);
				cells.setFixedHeight(0.8f * CONVERT);	
				footer.addCell(cells);
			}
		}
		
		document.add(footer);
		
		document.newPage();
		}
	
		document.close();
		
		rs.close();
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	return msg;
		
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
