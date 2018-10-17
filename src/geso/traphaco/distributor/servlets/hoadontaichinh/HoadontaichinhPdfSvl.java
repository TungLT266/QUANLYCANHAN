
package geso.traphaco.distributor.servlets.hoadontaichinh;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.distributor.beans.hoadontaichinh.IHoadontaichinh;
import geso.traphaco.distributor.beans.hoadontaichinh.imp.Hoadontaichinh;
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

public class HoadontaichinhPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public HoadontaichinhPdfSvl()
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
		System.out.println("id:"+ id);
		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));

		IHoadontaichinh pxkBean = new Hoadontaichinh(id);
		pxkBean.setUserId(userId);

		
		//CHUYEN CAU QUYERY SAN PHAM + CHIET KHAU LEN TREN, KHONG DUNG O DUOI NUA
		/*String query =
		"	select c.MA, c.TEN, d.DONVI,dhsp.GIAMUA AS dongia ,' ' as solo, ' ' as ngayhethan,dhsp.thuevat ,   SUM( dhsp.soluong) as soluong, '0' as chietkhau "+
		"	from  DONHANG dh inner join DONHANG_SANPHAM dhsp on dhsp.donhang_fk=dh.PK_SEQ  "+
		"						inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ           "+    
		"						left join DONVIDOLUONG d on d.PK_SEQ=c.dvdl_fk 	"+
		"	where dh.PK_SEQ in (select ddh_fk from HOADON_DDH where hoadon_fk = "+ pxkBean.getId() +")  and  dhsp.soluong > 0   	"+
		"	group by   c.MA, c.TEN, d.DONVI, dhsp.soluong,dhsp.GIAMUA  ,dhsp.thuevat 	";*/
		
		/*String sqlIN_SANPHAM = "select c.MA, c.TEN, d.DONVI, dhsp.GIAMUA AS dongia,  " +
								"		case f.solo when 'NA' then ' ' else f.solo end as solo,  " +
								"		case f.solo when 'NA' then ' ' else isnull(f.ngayhethan,'') end as ngayhethan, dhsp.thuevat,    " +
								"	SUM( f.soluong) as soluong, '0' as chietkhau  " +
								"from  DONHANG dh inner join DONHANG_SANPHAM dhsp on dhsp.donhang_fk=dh.PK_SEQ   " +
								"				 inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ                " +
								"				 left join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk  " +
								"				 left join PHIEUXUATKHO_DONHANG e on dh.pk_seq = e.donhang_fk " +
								"				 left join PHIEUXUATKHO_SANPHAM_CHITIET f on e.pxk_fk = f.pxk_fk and c.pk_seq = f.sanpham_fk " +
								"where dh.PK_SEQ in (select ddh_fk from HOADON_DDH where hoadon_fk =  '" + pxkBean.getId() + "' )  and  dhsp.soluong > 0   	 " +
								"group by   c.MA, c.TEN, d.DONVI, dhsp.GIAMUA, dhsp.thuevat, f.solo, f.ngayhethan ";*/
	
		String sqlIN_SANPHAM = "select MA, TEN, DONVI, AVG(DONGIA) as DONGIA, '' as SOLO, '' as NGAYHETHAN, AVG(THUEVAT) as THUEVAT, sum(SOLUONG) as SOLUONG, sum(CHIETKHAU) as CHIETKHAU " +
							   "from HOADON_SP_CHITIET where hoadon_fk = '" + pxkBean.getId() + "'" +
							   "group by MA, TEN, DONVI ";
		
		// Hải Phòng, Nam Định, HCM, Đồng Nai, Bình Thuận, Vĩnh Long, Cần Thơ, Q207, Đã Nẵng , Quảng Ngãi, Gia Lai  ---CHI NHUNG NHA NAY MOI DUNG SOLO TRONG HOA DON
		if( nppId.equals("106179") || nppId.equals("106172") || nppId.equals("106210") || nppId.equals("106225") || nppId.equals("106226") 
				|| nppId.equals("106224") || nppId.equals("106227") || nppId.equals("100010") || nppId.equals("106211") || nppId.equals("106231") || nppId.equals("106249")||nppId.equals("106251")||nppId.equals("106250")||nppId.equals("106221")||nppId.equals("106275"))
		{
			sqlIN_SANPHAM = "select MA, TEN, DONVI, DONGIA, SOLO, NGAYHETHAN, THUEVAT, SOLUONG, CHIETKHAU " +
			   				"from HOADON_SP_CHITIET where hoadon_fk = '" + pxkBean.getId() + "' ";
		}
		System.out.println("---IN SAN PHAM: " + sqlIN_SANPHAM );
		
		 /*query = "select N'CN5' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 1 as STT, 0 as tichluyQUY from DONHANG_SANPHAM  " +
					"	where donhang_fk in (select ddh_fk from HOADON_DDH where hoadon_fk = "+ pxkBean.getId() +") and thueVAT = '5' group by thueVAT " +
					"union  " +
					"	select N'CN10' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 2 as STT, 0 as tichluyQUY  " +
					"	from DONHANG_SANPHAM  " +
					"	where donhang_fk in (select ddh_fk from HOADON_DDH where hoadon_fk = "+ pxkBean.getId() +") and thueVAT = '10' group by thueVAT " +
					"union " +
					"	select a.diengiai, a.thanhtoan / ( 1 + ptTHUE / 100 ) as chietkhau, a.ptTHUE as thueVAT, 3 as STT, tichluyQUY  " +
					"	from DUYETTRAKHUYENMAI_DONHANG a inner join TIEUCHITHUONGTL b on a.duyetkm_fk = b.PK_SEQ   " +
					"	where a.donhang_fk in (select ddh_fk from HOADON_DDH where hoadon_fk = "+ pxkBean.getId() +")  "+
					"union " +
					"	select a.maloai as diengiai, a.chietkhau, a.ptVAT as thueVAT, 4 as STT, 0 as tichluyQUY  " +
					"	from DONHANG_CHIETKHAUBOSUNG a    " +
					"	where a.donhang_fk in (select ddh_fk from HOADON_DDH where hoadon_fk = "+ pxkBean.getId() +")  order by STT, tichluyQUY  ";*/
		
		String sqlIN_CHIETKHAU = 
			" select diengiai, chietkhau, thueVAT, STT, tichluyQUY, inchietkhauKIEUMOI " +
			" from HOADON_CHIETKHAU where hoadon_fk = '" + pxkBean.getId() + "' and HIENTHI = '1' and chietkhau > 0 order by STT, tichluyQUY ";
		System.out.println("---CHIET KHAU: " + sqlIN_CHIETKHAU);
		
		/*String sqlIN_CHIETKHAU = " select top(1) N'Tiền chiết khấu' diengiai, 50000 chietkhau, 5 thueVAT , 1 STT, 0 tichluyQUY " +
		 " from HOADON_CHIETKHAU ";
		System.out.println("---CHIET KHAU: " + sqlIN_CHIETKHAU);*/

		String type = request.getParameter("type");
		System.out.println("type:"+type);
		if(type == null)
			type = "";
		
		if(!type.equals("PGH"))
		{
			if (querystring.indexOf("pdf") > 0)
			{
				//pxkBean.initPdf();
				//3 COT TONG TIEN BUOC PHAI LAY TRONG BANG HOA DON, DO TRONG QUA KHU CO NHUGN CAI BI SAI
				pxkBean.getTongTienPDF();
				
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", " inline; filename=HoaDonTaiChinh.pdf");
	
				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
	
				if(nppId.equals("106174") ) // QUAY HA DONG 
				{
					this.CreatePxk_HADONG(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106171")) // CHI NHÁNH CÔNG TY TẠI NGHỆ AN 
				{
					this.CreatePxk_NGHEAN(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106170")) // THANH HOA 
				{
					this.CreatePxk_THANHHOA(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106191")) // PHUTHO 
				{
					this.CreatePxk_PHUTHO(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106224")) // VINH LONG GIONG THANH HOA 
				{
					this.CreatePxk_VINHLONG(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106275")) // TIEN GIANG GIONG VINH LONG
				{
					this.CreatePxk_TIENGIANG(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106172")) //  NAM ĐỊNH
				{
					this.CreatePxk_NAMDINH(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106182")) //  HẢI DƯƠNG
				{
					this.CreatePxk_HAIDUONG(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106192")) //  THÁI NGUYÊN  
				{
					this.CreatePxk_THAINGUYEN(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106221")) //  TÂY NINH - CÔNG TY DƯỢC PHẨM - DƯỢC LIỆU TÂY NAM  
				{
					this.CreatePxk_TAYNINH(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106188")) //  PHƯƠNG MINH  
				{
					this.CreatePxk_PHUONGMINH(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106179")) //  HẢI PHÒNG  : 02/10 mẫu mới giống Hà Nội
				{
					this.CreatePxk_HAIPHONG(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106225")) //  ĐỒNG NAI   : Giống Hải phòng
				{
					this.CreatePxk_DONGNAI(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106162")) //  QUẢNG NINH >> 23/10 ĐỔI SANG MẪU GIỐNG HẢI DƯƠNG
				{
					this.CreatePxk_QUANGNINH(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU); 
				}
				else if(nppId.equals("106210")) //  CN HỒ CHÍ MINH
				{
					this.CreatePxk_CNHOCHIMINH(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU); 
				}
				else if(nppId.equals("106211")) //  Cửa hàng số 01 - Chi Nhánh Công ty cổ phần Traphaco
				{
					this.CreatePxk_CUAHANGQ10(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU); 
				}
				else if(nppId.equals("106226")) //  BÌNH THUẬN   : Giống Đồng Nai
				{
					this.CreatePxk_BINHTHUAN(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106227")) //  CẦN THƠ   : Giống VĨNH LONG
				{
					this.CreatePxk_CANTHO(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106231")) //  ĐÀ NẴNG   
				{
					this.CreatePxk_DANANG(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106249")) //  QUẢNG NGÃI   
				{
					this.CreatePxk_QUANGNGAI(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else if(nppId.equals("106250")) //  KHÁNH HÒA   
				{
					this.CreatePxk_KHANHHOA(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);//
				}
				else if(nppId.equals("106251")) //  GIA LAI  
				{
					this.CreatePxk_GIALAI(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				else     // CHI NHÁNH HÀ NỘI 
				{
					this.CreatePxk(document, outstream, pxkBean, sqlIN_SANPHAM, sqlIN_CHIETKHAU);
				}
				
				document.close();
			}
					
			String msg = this.CapnhatTT(id, userId);
			pxkBean.setMsg(msg);
		}
		else
		{
			double chietKHAU = 0;
			String ngayxuatHD = "";
			String nppTEN = "";
			String sohoadon = "";
			
			sqlIN_CHIETKHAU = " select diengiai, chietkhau, thueVAT, STT, tichluyQUY " +
						 	  " from HOADON_CHIETKHAU where hoadon_fk = '" + id + "' and HIENTHI = '1' and chietkhau > 0 order by STT, tichluyQUY ";
			System.out.println("---CHIET KHAU: " + sqlIN_CHIETKHAU);

			String query = "select chietkhau, " +
						   "	( select ngayxuatHD from HOADON where pk_seq = '" + id + "' ) as ngayxuatHD, " +
						   "	( select ten from NHAPHANPHOI where pk_seq = a.npp_fk ) as nppTEN, " +
						   "	( select sohoadon from HOADON where pk_seq = '" + id + "' ) as sohoadon " +
						   "from DONHANG a where pk_seq = ( select ddh_fk from HOADON_DDH where hoadon_fk = '" + id + "' )	" +
								"and ngaynhap >= '2014-10-01'";
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			try 
			{
				if(rs.next())
				{
					chietKHAU = rs.getDouble("chietkhau");
					ngayxuatHD = rs.getString("ngayxuatHD");
					nppTEN = rs.getString("nppTEN");
					sohoadon = rs.getString("sohoadon");
				}
				rs.close();
			} 
			catch (Exception e) {}
			
			
			if(chietKHAU > 0)
			{
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", " inline; filename=PhieuGiaoHang.pdf");

				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				
				this.CreatePhieuGiaoHang(document, outstream, id, chietKHAU, ngayxuatHD, nppTEN, sohoadon, sqlIN_CHIETKHAU );
				
				db.update("update HOADON set DAINPGH = 1 where pk_seq = '" + id + "' ");
				db.shutDown();
			}
		}

	}
	
	private void CreatePxk_CUAHANGQ10(Document document, ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
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
				String ngayxuatHD= "";
				String chucuahieu = "";
				
/*				sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
			        " from KHACHHANG " +
			        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/			  
			   
				//LẤY THEO DỮ LIỆU MỚI
				sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
				  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
		   
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
				"                WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
		        " from DONHANG " +
		        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
		   
	       System.out.println("Kho xuất "+sql);
		   ResultSet rsKho= db.get(sql);
		   if(rsKho.next())
		   {
			   hinhthucTT = rsKho.getString("HTTT");		   
			   ngayxuatHD = rsKho.getString("ngayxuathd");
			   chucuahieu = rsKho.getString("nguoimua");
			   
			   rsKho.close();
		   }
			   
		    NumberFormat formatter = new DecimalFormat("#,###,###.0000");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(0.0f*CONVERT, 0.8f*CONVERT, 0.5f*CONVERT, 0.7f*CONVERT); // L,R,T,B
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
			cell.setPaddingTop(2.8f * CONVERT);//3.6
			cell.setPaddingLeft(0.0f * CONVERT);
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
			sanpham.setSpacingBefore(1.7f*CONVERT);
			
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
				
				
				String query = sqlIN_SANPHAM;
				System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
				
				ResultSet rsSP = db.get(query);
				
				int stt = 1;
				
				double thanhtien = 0;	
				double thueGTGT = 0;
				double sotientt = 0;
				
				while(rsSP.next())
				{
					double soLUONG = rsSP.getDouble("soluong");
					double chietkhau = rsSP.getDouble("chietkhau");
					double dongia = rsSP.getDouble("dongia");
					
					if(SoNgay(ngayxuatHD)){
						thanhtien = Math.round(soLUONG * dongia - chietkhau);	
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + thueGTGT;
						
						totalThueGTGT +=  thueGTGT;
						totalTienTruocVAT+= thanhtien;
						totalSotienTT += sotientt;
					}
					else{
						thanhtien = soLUONG * dongia - chietkhau;	
						thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
						sotientt = thanhtien + (thanhtien*rsSP.getDouble("thuevat")/100);
						
						totalThueGTGT +=  thanhtien*rsSP.getDouble("thuevat")/100 ;
						totalTienTruocVAT+= thanhtien;
						totalSotienTT += sotientt;
					}
					
					
					String chuoi ="";
					if(rsSP.getString("ngayhethan").trim().length() > 0)
					{
						String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
						chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
					}
					
					//NHA HCM TEN HOI DAC BIET CHUT
					String spTEN = rsSP.getString("TEN").replaceAll("bao đường", "bđ");
					String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , spTEN, rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO( formatter1.format(soLUONG) ), DinhDangTRAPHACO( formatter.format(dongia) ), DinhDangTRAPHACO( formatter1.format(thanhtien) ), DinhDangTRAPHACO( formatter1.format(rsSP.getDouble("thuevat")) ), DinhDangTRAPHACO( formatter1.format(thueGTGT) ), DinhDangTRAPHACO( formatter1.format(sotientt) ) };
	
	
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
							cells.setPaddingLeft(-0.01f*CONVERT);
						}
						if(j==1 )
						{
							cells.setPaddingLeft(-0.7f*CONVERT);	
						}
						if( j==2)
						{
							cells.setPaddingLeft(-1.0f*CONVERT);	
						}
						if(j==3)
						{
							cells.setPaddingLeft(-0.8f*CONVERT);	
						}
						if(j==4)
						{
							cells.setPaddingLeft(-1.25f*CONVERT);	
						}
						if( j==5)
						{
							cells.setPaddingLeft(-0.7f*CONVERT);	
						}
						if(j==6)
						{
							cells.setPaddingRight(1.7f*CONVERT);	
						}
						if(j==7)
						{
							cells.setPaddingRight(1.0f*CONVERT);	
						}
						if(j==8 )
						{
							cells.setPaddingRight(1.1f*CONVERT);	
						}
						if(j==9)
						{
							cells.setPaddingRight(0.6f*CONVERT);	
						}
						if(j==10)
						{
							cells.setPaddingRight(1.1f*CONVERT);	
						}
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						cells.setFixedHeight(0.6f * CONVERT);
						cells.setPaddingTop(2.5f);					
						cells.setBorder(0);
						
						
						sanpham.addCell(cells);
					}
								
					
					stt++;
					
				}
				stt= stt-1;
				
				query = sqlIN_CHIETKHAU;
				 System.out.println("---INIT TICH LUY: " + query);
				 
				 ResultSet rs = db.get(query);
				 if(rs != null)
				 {												
					 try 
					 {
						
			        while(rs.next())
				    {
			        	String maCK = rs.getString("DienGiai");
			        	String diengiaiCK ="";
			        	// LAY TEN CHIET KHAU
			        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
			        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
			        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
			        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
			        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
			        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
			        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
			        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
			         System.out.println("Ten dien giai: "+diengiaiCK);
	
			         double tienAVAT = 0;
			         double tienBVAT = 0;
			         double tienVAT  = 0;
			         
			        if(SoNgay(ngayxuatHD)&& SoNgayCKQ(ngayxuatHD)){
				         
				        tienBVAT = Math.round( (Math.round( rs.getDouble("chietkhau"))));
				        
				        tienVAT =  Math.round( tienBVAT * rs.getDouble("thuevat") / 100 );				        

			        	tienAVAT = tienBVAT + tienVAT;
				        			        	
			        }
			        else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
			        	 tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
				         tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
				         tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			        }
			        else{
				        tienAVAT =  Math.round( rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) );
				         
				        tienBVAT = Math.round( tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) );
				        
				        tienVAT =  Math.round( tienBVAT * rs.getDouble("thuevat") / 100 );
			        }
			        
					//double tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
			        
					/*totalTienCK += rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK += rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;*/
					
					totalTienCK += tienAVAT  ;
					totalTienCK_ChuaVat += tienBVAT;
					totalVatCK += tienVAT;
					
					/*String [] arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ,"", "", "1","","", formatter1.format(rs.getDouble("chietkhau")),formatter1.format(rs.getDouble("thuevat")),
							formatter1.format(tienVAT) ,formatter1.format((tienVAT + rs.getDouble("chietkhau")) )};*/
					
					String [] arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ,"", "", "1","","", 
							DinhDangTRAPHACO( formatter1.format(tienBVAT) ), DinhDangTRAPHACO( formatter1.format(rs.getDouble("thuevat")) ),
							DinhDangTRAPHACO( formatter1.format(tienVAT) ), DinhDangTRAPHACO(  formatter1.format( tienAVAT ) ) };
					for (int j = 0; j < arr5.length; j++)
					{
						cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
						if (j >=0 && j <= 5){
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else
						{
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}										
						if(j==0 )
						{
							cells.setPaddingLeft(-0.01f*CONVERT);
						}
						if(j==1 )
						{
							cells.setPaddingLeft(-0.7f*CONVERT);	
						}
						if( j==2)
						{
							cells.setPaddingLeft(-1.0f*CONVERT);	
						}
						if(j==3)
						{
							cells.setPaddingLeft(-0.8f*CONVERT);	
						}
						if(j==4)
						{
							cells.setPaddingLeft(-1.25f*CONVERT);	
						}
						if( j==5)
						{
							cells.setPaddingLeft(-0.7f*CONVERT);	
						}
						if(j==6)
						{
							cells.setPaddingRight(1.7f*CONVERT);	
						}
						if(j==7)
						{
							cells.setPaddingRight(1.0f*CONVERT);	
						}
						if(j==8 )
						{
							cells.setPaddingRight(1.1f*CONVERT);	
						}
						if(j==9)
						{
							cells.setPaddingRight(0.6f*CONVERT);	
						}
						if(j==10)
						{
							cells.setPaddingRight(1.1f*CONVERT);	
						}
						
						cells.setFixedHeight(0.6f*CONVERT);
						cells.setPaddingTop(2.5f);
						cells.setBorder(0);
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						
	
						sanpham.addCell(cells);
					}
					stt ++;
				}
			rs.close();
			
		} 
		catch (Exception e) 
		{
			System.out.println("__EXCEPTION: " + e.getMessage());
		}
	   }
				
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
				
			// Tong tien thanh toan	
			//String[] arr = new String[] {"  ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
				
				double truocVAT = 0;
				String sauVat = "";
				String tienVat = "";				
				
				if(SoNgay(ngayxuatHD)){
					truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
					tienVat = formatter1.format(totalThueGTGT - totalVatCK);
					sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
				}
				else{
					truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
					tienVat = pxkBean.getTongVAT();
					sauVat = pxkBean.getTongtienAVAT();
				}	
				
				//double truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				String[] arr = new String[] {" "," "," "," "," "," "," "," ", DinhDangTRAPHACO( formatter1.format(truocVAT) ), " " , DinhDangTRAPHACO( tienVat ), DinhDangTRAPHACO( sauVat ) };
				for (int j = 0; j < arr.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
					if (j >=0 && j <= 5){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}										
					if(j==0 )
					{
						cells.setPaddingLeft(-0.01f*CONVERT);
					}
					if(j==1 )
					{
						cells.setPaddingLeft(-0.7f*CONVERT);	
					}
					if( j==2)
					{
						cells.setPaddingLeft(-1.0f*CONVERT);	
					}
					if(j==3)
					{
						cells.setPaddingLeft(-0.8f*CONVERT);	
					}
					if(j==4)
					{
						cells.setPaddingLeft(-1.25f*CONVERT);	
					}
					if( j==5)
					{
						cells.setPaddingLeft(-0.7f*CONVERT);	
					}
					if(j==6)
					{
						cells.setPaddingRight(1.7f*CONVERT);	
					}
					if(j==7)
					{
						cells.setPaddingRight(1.0f*CONVERT);	
					}
					if(j==8 )
					{
						cells.setPaddingRight(1.1f*CONVERT);	
					}
					if(j==9)
					{
						cells.setPaddingRight(0.6f*CONVERT);	
					}
					if(j==10)
					{
						cells.setPaddingRight(1.1f*CONVERT);	
					}
					
					
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					//cells.setPaddingLeft(0.8f * CONVERT);
					cells.setPaddingTop(-0.1f * CONVERT); //cells.setPaddingTop(-0.3f * CONVERT);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
					sanpham.addCell(cells);
				}				
				
				// Tien bang chu
				doctienrachu doctien = new doctienrachu();
			    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
				String tien = doctien.docTien(Long.parseLong(pxkBean.getTongtienAVAT().replaceAll(",", "")));
			  //Viết hoa ký tự đầu tiên
			    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			    
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
					cells.setPaddingTop(0.0f * CONVERT);//cells.setPaddingTop(-0.3f * CONVERT);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
					sanpham.addCell(cells);
				}
				
																				
				document.add(sanpham);
								
				
				//document.close();
			
				
			} catch (Exception e)
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
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

	private void CreatePxk_HAIPHONG(Document document, ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) throws IOException
	{
		System.out.println("----HAI PHONG....ABC");
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
	
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   
		   System.out.println("Lấy TT CTY HP: " + sql);
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
			String ngayxuatHD= "";
			String chucuahieu = "";
			
	/*		sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/			  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
	   
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
	   		" 			   ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"                FROM HOADON" +
			"                WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
	   }
		   
	    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		
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
		cell_nguoimua.setPaddingTop(-5.6f);
		pxk.setSpacingAfter(4);
		cell_nguoimua.addElement(pxk);
		cell_nguoimua.setBorder(0);						
		table1.addCell(cell_nguoimua);	
		
		PdfPCell cell_nguoimua1 = new PdfPCell();
		cell_nguoimua1.setPaddingTop(0.5f * CONVERT);
		cell_nguoimua1.setPaddingLeft(4.9f * CONVERT);
		pxk = new Paragraph(chucuahieu, new Font(bf, 10, Font.NORMAL));
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
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			double thanhtien = 0;	
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
						
					totalThueGTGT += thueGTGT;
					totalTienTruocVAT+= thanhtien;
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
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };


				
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
			
			query = sqlIN_CHIETKHAU;
			 System.out.println("---INIT TICH LUY: " + query);
			 double tienVAT = 0;
		     double tienAVAT = 0;
		     double tienBVAT = 0;
		     
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);

				
				if(SoNgay(ngayxuatHD) && SoNgayCKQ(ngayxuatHD)){
					
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + Math.round(rs.getDouble("chietkhau")); 
					
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
					
				}
				else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK += rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK += rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100 ;
				}
				String [] arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ,"","","1","","", DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
						DinhDangTRAPHACO(formatter1.format(tienVAT)), DinhDangTRAPHACO(formatter1.format(tienAVAT)) };
				
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
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

					cells.setFixedHeight(0.6f*CONVERT);
					cells.setPaddingTop(2.5f);
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					
					sanpham.addCell(cells);
				}
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
		
		double truocVAT = 0;
		String sauVat = "";
		String tienVat = "";
		
		if(SoNgay(ngayxuatHD)){
			truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
			tienVat = formatter1.format(totalThueGTGT - totalVatCK);
			sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
		}
		else{
			truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
			tienVat = pxkBean.getTongVAT();
			sauVat = pxkBean.getTongtienAVAT();
		}	
		
		String[] arr = new String[] { " ", " " , " ", " "," ", " "," "," ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };
		
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
			

			
			//document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	
	private void CreatePxk_DONGNAI(Document document, ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) throws IOException
	{
		System.out.println("----DONG NAI.......");
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
	
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   
		   System.out.println("Lấy TT CTY HP: " + sql);
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
			String ngayxuatHD= "";
			String chucuahieu = "";
			
/*			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";*/				  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
	   
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
	   		" 			   ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"                FROM HOADON" +
			"                WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
	   }
		   
	    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(0.8f*CONVERT, 0.1f*CONVERT, 1.6f*CONVERT, 2.0f*CONVERT); // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open() ;
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(0.7f * CONVERT);
		cell.setPaddingLeft(2.0f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuatHD.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                             " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		pxk.setSpacingAfter(4);
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
		//cell_nguoimua.setPaddingTop(-1.2f);
		pxk.setSpacingAfter(4);
		cell_nguoimua.addElement(pxk);
		cell_nguoimua.setBorder(0);						
		table1.addCell(cell_nguoimua);	
		
		PdfPCell cell_nguoimua1 = new PdfPCell();

		cell_nguoimua1.setPaddingLeft(5.5f * CONVERT);
		pxk = new Paragraph(chucuahieu, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		//cell_nguoimua1.setPaddingTop(-1.2f);
		cell_nguoimua1.addElement(pxk);
		cell_nguoimua1.setBorder(0);						
		table1.addCell(cell_nguoimua1);
		
		// DONG 2-- DON VI
		PdfPCell cell8 = new PdfPCell();	
		cell8.setPaddingLeft(2.7f * CONVERT);
		cell8.setPaddingTop(-0.19f * CONVERT);
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
		//pxk.setSpacingAfter(2);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);						
		table1.addCell(cell8a);
		
		// DONG 3-- MA SO THUE
		cell8 = new PdfPCell();	
		cell8.setPaddingLeft(2.7f * CONVERT);
		cell8.setPaddingTop(-0.19f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		//pxk.setSpacingAfter(4);
		cell8.addElement(pxk);
		cell8.setBorder(0);						
		table1.addCell(cell8);	
		
		cell8a = new PdfPCell();
		cell8a.setPaddingTop(-0.19f * CONVERT);
		cell8a.setPaddingLeft(4.6f * CONVERT);
		pxk = new Paragraph(kh_MST, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		//pxk.setSpacingAfter(2);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);						
		table1.addCell(cell8a);
		

		// DONG 4 ---- DIA CHI
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
		

		/*if(kh_MST.trim().length() > 0 && kh_MST.trim().length() <= 10)		
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
		}*/
		
		
		
		PdfPCell cell18 = new PdfPCell();
		cell18.setPaddingLeft(130.0f);
		cell18.setPaddingTop(-0.05f * CONVERT);
		//pxk = new Paragraph( kh_MST +"                                       " +hinhthucTT, new Font(bf, 10, Font.NORMAL));
		pxk = new Paragraph( hinhthucTT, new Font(bf, 10, Font.NORMAL));
		//pxk.setAlignment(Element.ALIGN_LEFT);
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
		sanpham.setSpacingBefore(36.5f); 
		sanpham.setWidthPercentage(100);
		sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		float[] withsKM = { 7.0f, 15.0f, 45.0f, 11.0f, 12f, 9.0f, 12.0f, 17f, 22.0f, 7.0f, 21f, 21.0f };
		sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			double thanhtien = 0;	
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
							
					totalThueGTGT += thueGTGT;
					totalTienTruocVAT+= thanhtien;
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
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };

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
					cells.setPaddingTop(2.4f);
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
						
				
			stt= stt-1;
			
			query = sqlIN_CHIETKHAU;
			 System.out.println("---INIT TICH LUY: " + query);
			 
			 double tienVAT = 0;
		     double tienAVAT = 0;
		     double tienBVAT = 0;
			 
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);
		         
				if(SoNgay(ngayxuatHD) && SoNgayCKQ(ngayxuatHD)){
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + Math.round(rs.getDouble("chietkhau"));
					
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
					
				}
				else if (SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK +=  rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK +=  rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				}
				
				String [] arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ,"","","1","","", DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
							DinhDangTRAPHACO(formatter1.format(tienVAT)), DinhDangTRAPHACO(formatter1.format(tienAVAT))};				
				
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
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

					cells.setFixedHeight(0.6f*CONVERT);
					cells.setPaddingTop(2.4f);
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					
					sanpham.addCell(cells);
				}
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
			
			double truocVAT = 0;
			String sauVat = "";
			String tienVat = "";
			
			if(SoNgay(ngayxuatHD)){
				truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
				tienVat = formatter1.format(totalThueGTGT - totalVatCK);
				sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
			}
			else{
				truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				tienVat = pxkBean.getTongVAT();
				sauVat = pxkBean.getTongtienAVAT();
			}	
			
		
		String[] arr = new String[] { " ", " " , " ", " "," ", " "," "," ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };
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
				cells.setPaddingTop(-19.0f);
				cells.setBorder(0);
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
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(-6.2f);
				cells.setBorder(0);
				cells.setFixedHeight(0.5f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			//document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		
	}


	private void CreatePhieuGiaoHang(Document document, ServletOutputStream outstream, String id, double chietkhau, String ngayxuatHD, String nppTen, String sohoadon, String sqlInCHIETKHAU)
	{
		try
		{	
			dbutils db = new dbutils();
			document.setPageSize(PageSize.A4);
			
			String[] ngayHD = ngayxuatHD.split("-");
			
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
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font fontnomar = new Font(bf, 9,Font.NORMAL);

			//font2.setColor(BaseColor.GREEN);
			 //KHAI BAO 1 BANG CO BAO NHIEU COT
			
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
			Paragraph dvbh = new Paragraph("( Kèm theo Hoá đơn GTGT số " + sohoadon + " Ngày " + ngayHD[2] + " tháng " + ngayHD[1] + " năm " + ngayHD[0] + " )" , fontnomar);
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
			
			String tenkh = "";
			String diachikh = "";
			String masothuekh = "";
			String sql_getinfokh = "select ten,isnull(dienthoai,'Chua xac dinh') as dienthoai,isnull(diachi,'Chua xac dinh') as diachi," +
								   "	isnull(masothue,'Chua xac dinh') as masothue from khachhang " +
								   "where pk_seq = ( select khachhang_fk from HOADON where pk_seq = '" + id + "' ) ";
			
			ResultSet rs_kh = db.get(sql_getinfokh);
			try
			{
				if(rs_kh.next())
				{
					tenkh = rs_kh.getString("ten");
					diachikh = rs_kh.getString("diachi");
					masothuekh = rs_kh.getString("masothue");
				}
				rs_kh.close();
			}
			catch(Exception er ){
				System.out.println("Loi Khach Hang : " + er.toString());
			}

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
			PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {5.0f, 30.0f, 5.0f, 5.0f,6.0f,10.0f ,9.0f, 13.0f}; 	

	        table.setWidths(withs);
			String[] th = new String[]{ "STT", "Tên hàng hoá, dịch vụ", "ĐVT", "Số lượng","Số lô","ngày hết hạn", "Đơn giá (theo giá BB)", "Thành tiền"};
			PdfPCell[] cell = new PdfPCell[8];
			for(int i = 0; i < 8 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setBackgroundColor(BaseColor.LIGHT_GRAY);	
				cell111.setPadding(2);
				table.addCell(cell[i]);			
			}
			
			String query = "select b.MA, b.TEN, c.DONVI, a.SOLUONG,  " +
							"	cast ( ( " +
							"		select  ISNULL ( ( select giabanleNPP from BGBANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BGBANLENPP_FK in ( select pk_seq from BANGGIABANLENPP where npp_fk = ( select npp_fk from HOADON where pk_seq = '4000036558' )  ) ), 0) as dongia  " +
							"		from SANPHAM a     " +
							"		where a.pk_seq > 0 and a.pk_seq = b.pk_seq  " +
							"	 ) * ( 1 + ( select THUEXUAT from NGANHHANG where pk_seq = b.nganhhang_fk  ) / 100.0 ) as numeric(18, 0) ) donGIA,ISNULL(a.SOLO,'') as solo,ISNULL(a.NGAYHETHAN,'') as ngayhethan " +
							"from HOADON_SP_Chitiet a inner join SANPHAM b on (select pk_seq from sanpham where ma=a.ma) = b.pk_seq  " +
							"	inner join DONVIDOLUONG c on b.dvdl_fk = c.pk_seq " +
							"where a.hoadon_fk = '" + id + "' ";
			System.out.println("::::::::::::::::::::::"+query);
			double tongTIEN = 0;
			double totalCK = 0;
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				PdfPCell cellSP = null;
				
				int stt = 1;
				NumberFormat format = new DecimalFormat("#,###,###");
				while(rs.next())
				{
					double soluong = rs.getDouble("SOLUONG");
					double dongia = rs.getDouble("donGIA");
					double tienCK = Math.round(soluong * dongia * chietkhau / 100);
					totalCK += tienCK;
					
					cellSP = new PdfPCell(new Paragraph(Integer.toString(stt), font2));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);		
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("TEN"), font2));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("DONVI"), font2));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
					

					cellSP = new PdfPCell(new Paragraph(format.format(soluong), font2));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("solo"), font2));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
					
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("ngayhethan") , font2));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(format.format(dongia) , font2));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(format.format(soluong * dongia) , font2));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					tongTIEN += ( soluong * dongia );
					stt++;
				}
				rs.close();
				
				
				cellSP = new PdfPCell(new Paragraph(Integer.toString(stt), font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);		
				
				cellSP = new PdfPCell(new Paragraph("GGCĐ (Chiết khấu bán hàng ngay)", font2));
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
				
				cellSP = new PdfPCell(new Paragraph("" , font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("" , font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph(format.format(totalCK) , font2));
				cellSP.setPadding(3.0f);
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellSP);	
				
				stt ++;
				
				
				//Chiet khau QUY
				rs = db.get(sqlInCHIETKHAU);
				double tongtienCKQUY = 0;
				if(rs != null)
				{												
			        while(rs.next())
				    {
			        	String maCK = rs.getString("DienGiai");
			        	String diengiaiCK ="";
			        	// LAY TEN CHIET KHAU
			        	if(maCK.equals("CN5")) diengiaiCK = "Giảm trừ (Chiết khấu bán hàng ngay)";
			        	if(maCK.equals("CN10")) diengiaiCK = "Giảm trừ (Chiết khấu bán hàng ngay)";
			        	if(maCK.equals("CT5")) diengiaiCK = "Giảm trừ (CK Tháng)";
			        	if(maCK.equals("CT10")) diengiaiCK = "Giảm trừ (CK Tháng)";
			        	if(maCK.equals("CQB5")) diengiaiCK = "Giảm trừ (CK Quý nhóm hàng BG-HH)";
			        	if(maCK.equals("CQX5")) diengiaiCK = "Giảm trừ (CK Quý nhóm hàng XANH)";
			        	if(maCK.equals("CQB10")) diengiaiCK = "Giảm trừ (CK Quý nhóm hàng BG-HH)";
			        	if(maCK.equals("CQX10")) diengiaiCK = "Giảm trừ (CK Quý nhóm hàng XANH)";
											
						double totalTienCK = Math.round(rs.getDouble("chietkhau") + Math.round((rs.getDouble("chietkhau") * rs.getDouble("thuevat") / 100 )))  ;
						//double totalTienCK_ChuaVat = Math.round(rs.getDouble("chietkhau"));
						
						//double tienVAT = Math.round(rs.getDouble("chietkhau") * rs.getDouble("thuevat") / 100);
						//double totalVatCK = rs.getDouble("chietkhau")* rs.getDouble("thuevat") / 100 ;
						
						tongtienCKQUY += totalTienCK;
						
						
						cellSP = new PdfPCell(new Paragraph(Integer.toString(stt), font2));
						cellSP.setPadding(3.0f);
						table.addCell(cellSP);		
						
						cellSP = new PdfPCell(new Paragraph(diengiaiCK, font2));
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
						
						cellSP = new PdfPCell(new Paragraph("" , font2));
						cellSP.setPadding(3.0f);
						table.addCell(cellSP);	
						
						cellSP = new PdfPCell(new Paragraph("" , font2));
						cellSP.setPadding(3.0f);
						table.addCell(cellSP);	
						
						cellSP = new PdfPCell(new Paragraph(format.format(totalTienCK) , font2));
						cellSP.setPadding(3.0f);
						cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cellSP);	
						stt ++;
					}
				    rs.close();
				}
				
				
				cellSP = new PdfPCell(new Paragraph("", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);		
				
				cellSP = new PdfPCell(new Paragraph("CỘNG", font2));
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
				
				cellSP = new PdfPCell(new Paragraph("" , font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("" , font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph(format.format(tongTIEN - totalCK - tongtienCKQUY) , font2));
				cellSP.setPadding(3.0f);
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellSP);	
				
				// Tien bang chu
				doctienrachu doctien = new doctienrachu();
			    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
				String tien = doctien.docTien(Long.parseLong(format.format(tongTIEN - totalCK - tongtienCKQUY).replaceAll(",","")));
			  //Viết hoa ký tự đầu tiên
			    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			    
			    System.out.println("___"+TienIN);
			    
				String[] arr1 = new String[] {"            " + TienIN};
				for (int j = 0; j < arr1.length; j++)
				{
					cellSP = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
					if (j == 0)
					{
						cellSP.setHorizontalAlignment(Element.ALIGN_LEFT);
						cellSP.setColspan(8);
					} 
					cellSP.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cellSP.setPaddingLeft(1.5f * CONVERT);
					cellSP.setPaddingTop(-0.3f * CONVERT);
					cellSP.setFixedHeight(0.6f*CONVERT);
					cellSP.setBorder(0);
					table.addCell(cellSP);	
				}
			}			
			document.add(table);
			PdfPTable tablefooter = new PdfPTable(1);
			tablefooter.setWidthPercentage(100);
			tablefooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] withfooterder = {300.0f};
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
			
			cell11.addElement(para);
			tablefooter.addCell(cell11);
			
			document.add(tablefooter);
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

	private void CreatePxk_CNHOCHIMINH(Document document, ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
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
		
				
				   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ," +
				   	"(case when (select isnull(KHO_FK,'0') from KHACHHANG where pk_seq in (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"')) = 100002 then N'Kho sỉ Nhà Bè'" +
				   	" else  isnull(XUATTAIKHO,' ') end ) XUATTAIKHO " +
			        " from NHAPHANPHOI" +
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
				   rsINFO.close();
				   
			   }
				   
			 //LAY THONG TIN KHACHHANG 
				   
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
				String hinhthucTT= "";
				String ngayxuatHD= "";
				String chucuahieu = "";
				
				/*sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
			        " from KHACHHANG " +
			        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";*/	
				
				//LẤY THEO DỮ LIỆU MỚI
				sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
				  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
				
		   System.out.println(" TT KH "+sql);
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
				"                WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
		        " from DONHANG " +
		        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
		   
	       System.out.println("Kho xuất "+sql);
		   ResultSet rsKho= db.get(sql);
		   if(rsKho.next())
		   {
			   hinhthucTT = rsKho.getString("HTTT");		   
			   ngayxuatHD = rsKho.getString("ngayxuathd");
			   chucuahieu = rsKho.getString("nguoimua");
			   
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
			cell.setPaddingTop(3.55f * CONVERT);
			cell.setPaddingLeft(0.7f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = ngayxuatHD.split("-");
			Paragraph pxk = new Paragraph(ngayHD[2] + "                       " + ngayHD[1] +  "                       " + ngayHD[0].substring(2, ngayHD[0].length()) , new Font(bf, 9, Font.BOLDITALIC));
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
	
	
	String query = sqlIN_SANPHAM;
	System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
	
	ResultSet rsSP = db.get(query);
	
	int stt = 1;
	while(rsSP.next())
	{
		double soLUONG = rsSP.getDouble("soluong");
		double chietkhau = rsSP.getDouble("chietkhau");
		double dongia = rsSP.getDouble("dongia");
		double thanhtien = Math.round(soLUONG * dongia - chietkhau);	
		double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
		double sotientt = thanhtien + thueGTGT;
				
		totalThueGTGT += thanhtien*rsSP.getDouble("thuevat")/100;
		totalTienTruocVAT+= thanhtien;
		totalSotienTT += sotientt;
		
		
		String chuoi ="";
		if(rsSP.getString("ngayhethan").trim().length() > 0)
		{
			String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
			chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
		}
		
		//NHA HCM TEN HOI DAC BIET CHUT
		String spTEN = rsSP.getString("TEN").replaceAll("bao đường", "bđ");
		String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , spTEN, rsSP.getString("solo"), 
				chuoi, rsSP.getString("DONVI"),
				DinhDangTRAPHACO( formatter1.format(soLUONG) ), DinhDangTRAPHACO( formatter.format(dongia) ), DinhDangTRAPHACO( formatter1.format(thanhtien) ), DinhDangTRAPHACO( formatter1.format(rsSP.getDouble("thuevat")) ), DinhDangTRAPHACO( formatter1.format(thueGTGT) ), DinhDangTRAPHACO( formatter1.format(sotientt) ) };


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
	stt= stt-1;
	
	query = sqlIN_CHIETKHAU;
	 System.out.println("---INIT TICH LUY: " + query);
	 
	 ResultSet rs = db.get(query);
	 if(rs != null)
	 {												
		 try 
		 {
			
        while(rs.next())
	    {
        	String maCK = rs.getString("DienGiai");
        	String diengiaiCK ="";
        	// LAY TEN CHIET KHAU
        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
         System.out.println("Ten dien giai: "+diengiaiCK);

/*         double _vat = Double.parseDouble(tichluy_vat[i].replaceAll(",", ""));
			double _chietkhau = Double.parseDouble(tichluy_tongtien[i].replaceAll(",", ""));
			
			double _tienthue = _vat * _chietkhau / 100;
			double _thanhtien = _chietkhau + _tienthue;
			System.out.println("Tiền CN5 "+tichluy_tongtien[i]);
			*/
         double tienAVAT = 0;
         double tienBVAT = 0;
         double tienVAT = 0;
         
         if(SoNgayChietKhauQuy(ngayxuatHD)){
	         tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;
	         
	         tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;
	         
	         tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
         }
         else{
        	tienAVAT =  Math.round( rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) );
             
            tienBVAT = Math.round( tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) );
             
            tienVAT =  Math.round( tienBVAT * rs.getDouble("thuevat") / 100 );
         }
         
		//double tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
        
		/*totalTienCK += rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
		totalTienCK_ChuaVat += rs.getDouble("chietkhau");
		totalVatCK += rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;*/
		
		totalTienCK += tienAVAT  ;
		totalTienCK_ChuaVat += tienBVAT;
		totalVatCK += tienVAT;
		
		/*String [] arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ,"", "", "1","","", formatter1.format(rs.getDouble("chietkhau")),formatter1.format(rs.getDouble("thuevat")),
				formatter1.format(tienVAT) ,formatter1.format((tienVAT + rs.getDouble("chietkhau")) )};*/
		
		String [] arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ,"", "", "1","","", 
				DinhDangTRAPHACO( formatter1.format(tienBVAT) ), DinhDangTRAPHACO( formatter1.format(rs.getDouble("thuevat")) ),
				DinhDangTRAPHACO( formatter1.format(tienVAT) ), DinhDangTRAPHACO(  formatter1.format( tienAVAT ) ) };
		for (int j = 0; j < arr5.length; j++)
		{
			cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
				if(j <= 2)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else
				{							
				  cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
												
			if(j== 0)
			{
				cells.setPaddingLeft(0.5f*CONVERT);
			}
			if(j== 1)
			{
				cells.setPaddingLeft(0.2f*CONVERT);
			}
			
			if(j== 5)
			{
				cells.setPaddingRight(1.1f*CONVERT);
			}
			if(j== 8 )
			{
				cells.setPaddingRight(0.1f*CONVERT);
			}
			if( j==9)
			{
				cells.setPaddingRight(0.6f*CONVERT);
			}
			if(j== 10)
			{
				cells.setPaddingRight(0.1f*CONVERT);
			}
			
			cells.setFixedHeight(0.6f*CONVERT);
			cells.setPaddingTop(2.5f);
			cells.setBorder(0);
			cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
			

			sanpham.addCell(cells);
		}
		stt ++;
	}
rs.close();

} 
catch (Exception e) 
{
System.out.println("__EXCEPTION: " + e.getMessage());
}
}
	
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
	
// Tong tien thanh toan	
//String[] arr = new String[] {"  ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
	
	double truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
	String[] arr = new String[] {"                             ", DinhDangTRAPHACO( formatter1.format(truocVAT) ), " " , DinhDangTRAPHACO( pxkBean.getTongVAT() ), DinhDangTRAPHACO( pxkBean.getTongtienAVAT() ) };
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
			    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
				String tien = doctien.docTien(Long.parseLong(pxkBean.getTongtienAVAT().replaceAll(",", "")));
			  //Viết hoa ký tự đầu tiên
			    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			    
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
								
				
				//document.close();
			
				
			} catch (Exception e)
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
			}
		
		}

	
	private void CreatePxk_QUANGNINH_OLD(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
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
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho,(select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"				(select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd,   "+
	   		" 			   ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"                FROM HOADON" +
			"                WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
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
		
		String [] ngayHD = ngayxuatHD.split("-");
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
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG * dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100) ; 
				double sotientt = thanhtien + (thanhtien*rsSP.getDouble("thuevat")/100);
						
				
				totalThueGTGT += thanhtien* (rsSP.getDouble("thuevat")/100);
				totalTienTruocVAT+= thanhtien;
				totalSotienTT += sotientt;				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = new String[] { Integer.toString(stt),rsSP.getString("MA") + " - " + rsSP.getString("TEN"), rsSP.getString("solo"), 
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
			stt= stt-1;
			
			query = sqlIN_CHIETKHAU;
			 System.out.println("---INIT TICH LUY1: " + query);
			 
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";

		        	
				double tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
				
				totalTienCK += rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
				totalTienCK_ChuaVat += rs.getDouble("chietkhau");
				totalVatCK += rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				
				String [] arr5 = new String[] {Integer.toString(stt+1),maCK + " - " + diengiaiCK , " ", "1", " ", " ", formatter1.format(rs.getDouble("chietkhau")),formatter1.format(rs.getDouble("thuevat")),
						formatter1.format(tienVAT) ,formatter1.format((tienVAT + rs.getDouble("chietkhau")) )};
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
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
	}
   }
			
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
			
			double truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
			String[] arr = new String[] {"                             ", formatter1.format(truocVAT), " " , pxkBean.getTongVAT(), pxkBean.getTongtienAVAT() };
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
		    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));	
			String tien = doctien.docTien(Long.parseLong(pxkBean.getTongtienAVAT().replaceAll(",", "")));
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

			//document.close();
		
			
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
	
		
		
	}

	
	private void CreatePxk_HAIPHONG_OLD(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
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
			String ngayxuatHD= "";
			String chucuahieu = "";
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE"+
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
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho,(select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"       		(select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd ,  "+
	   		" 				( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"            	 FROM HOADON" +
			"            	 WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
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
		
		String [] ngayHD = ngayxuatHD.split("-");
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
			
	
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soLUONG * dongia - chietkhau;	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + (thanhtien*rsSP.getDouble("thuevat")/100);
						
				totalThueGTGT += thanhtien*rsSP.getDouble("thuevat")/100;
				totalTienTruocVAT+= thanhtien ;
				totalSotienTT += sotientt;				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = new String[] { Integer.toString(stt),rsSP.getString("MA") + " - " + rsSP.getString("TEN"), rsSP.getString("solo"), 
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
			stt= stt-1;
			
			query = sqlIN_CHIETKHAU;
			 System.out.println("---INIT TICH LUY1: " + query);
			 
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);

		        	
				double tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
				
				totalTienCK += rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
				totalTienCK_ChuaVat += rs.getDouble("chietkhau");
				totalVatCK += rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				
				String [] arr5 = new String[] {Integer.toString(stt+1),maCK + " - " + diengiaiCK , " ", "1", " ", " ", formatter1.format(rs.getDouble("chietkhau")),formatter1.format(rs.getDouble("thuevat")),
						formatter1.format(tienVAT) ,formatter1.format((tienVAT + rs.getDouble("chietkhau")) )};
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
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
	}
   }
			
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
			
			double truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
			String[] arr = new String[] {"                             ", formatter1.format(truocVAT), " " , pxkBean.getTongVAT(), pxkBean.getTongtienAVAT() };
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
		    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
			String tien = doctien.docTien(Long.parseLong(pxkBean.getTongtienAVAT().replaceAll(",", "")));
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

			//document.close();
		
			
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}


	private void CreatePxk_THAINGUYEN(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
	{
		try
		{
			dbutils db = new dbutils();
			
			 //LAY THONG TIN KHACHHANG 		  
			String sql ="";
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuatHD= "";
			String chucuahieu = "";
			String sotaikhoan= "";
			
			/*sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/			  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
			
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
		   		" 				( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
				"                         else isnull(nguoimua,'') end " +
				"           	  FROM HOADON" +
				"           	  WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
		        " from DONHANG " +
		        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
		   
	       System.out.println("Kho xuất "+sql);
		   ResultSet rsKho= db.get(sql);
		   if(rsKho.next())
		   {
			   hinhthucTT = rsKho.getString("HTTT");		   
			   ngayxuatHD = rsKho.getString("ngayxuathd");
			   chucuahieu = rsKho.getString("nguoimua");
			   rsKho.close();
		   }
		   
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 10, Font.NORMAL);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			// Dòng 1: ngày tháng
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(2.6f * CONVERT);
			cell.setPaddingLeft(1.0f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = ngayxuatHD.split("-");
			Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                             " + ngayHD[0].substring(2) , new Font(bf, 8, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			
			// DONG TRONG
			PdfPCell cell0 = new PdfPCell();
			cell0.setBorder(0);
			
			pxk = new Paragraph( " ", font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell0.setFixedHeight(3.7f * CONVERT);
			cell0.addElement(pxk);	
			
			tableheader.addCell(cell0);
			
			// Dòng 2: người mua hàng
			
			PdfPCell cell_mh = new PdfPCell();
			cell_mh.setBorder(0);
			cell_mh.setPaddingLeft(3.5f * CONVERT);
			cell_mh.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph( "                   " + chucuahieu , font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_mh.setFixedHeight(0.7f * CONVERT);
			cell_mh.addElement(pxk);
			
			tableheader.addCell(cell_mh);
			
			// Dòng 3: tên đơn vị
			
			PdfPCell cell_dv = new PdfPCell();
			cell_dv.setBorder(0);
			cell_dv.setPaddingLeft(3.0f * CONVERT);
			cell_dv.setFixedHeight(0.8f * CONVERT);
			cell_dv.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph(Donvi , font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_dv.addElement(pxk);
			
			tableheader.addCell(cell_dv);
			
			// Dòng 4 : Mã số thuế
			
			PdfPCell cell_mst = new PdfPCell();
			cell_mst.setBorder(0);
			cell_mst.setPaddingLeft(3.0f * CONVERT);
			cell_mst.setFixedHeight(0.7f * CONVERT);
			cell_mst.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph(kh_MST, font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_mst.addElement(pxk);
			pxk.setSpacingAfter(2);
							
			tableheader.addCell(cell_mst);
			
			// Dòng 5: Địa chỉ
			
			PdfPCell cell_dc = new PdfPCell();
			cell_dc.setBorder(0);
			cell_dc.setPaddingLeft(3.0f * CONVERT);
			cell_dc.setFixedHeight(0.7f * CONVERT);
			cell_dc.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph(kh_Diachi, font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_dc.addElement(pxk);
			pxk.setSpacingAfter(2);
							
			tableheader.addCell(cell_dc);
			
			// Dòng 6 : HTTT + Số tài khoản
			
			PdfPCell cell_httt = new PdfPCell();
			cell_httt.setBorder(0);
			cell_httt.setPaddingLeft(4.5f * CONVERT);
			cell_httt.setFixedHeight(0.8f * CONVERT);
			cell_httt.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph(hinhthucTT  +  "                                       " + sotaikhoan, font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_httt.addElement(pxk);
			pxk.setSpacingAfter(2);
							
			tableheader.addCell(cell_httt);
			
			document.add(tableheader); 
			
			// BẢNG SẢN PHẨM
			
			// Table Content

			String[] th = new String[]{ " ", " ", " ", "  ", " "," " ," "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(56.0f); 
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 7.0f, 70.0f, 21f, 15f, 20.0f, 20.0f, 30f};
			sanpham.setWidths(withsKM);
				

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			//KHAC CHI NHANH KHAC XIU
			String query = "select MA, TEN, DONVI, DONGIA, SOLO, NGAYHETHAN, THUEVAT, SOLUONG, CHIETKHAU,  " +
								"case when MA ='3KOOL' then 'JAPAN'  when MA = '5TRIB' then 'AUSTRALIA' else 'TRAPHACO' end as  NoiSX   " +
						   "from HOADON_SP_CHITIET where hoadon_fk = '" + pxkBean.getId() + "'";
			System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			double thuesuatGTGT = 0;
			int stt = 1;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				double thanhtien = Math.round(soLUONG * dongia );	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				thuesuatGTGT = rsSP.getDouble("thuevat");
				String noiSX = rsSP.getString("NoiSX");
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") + " - " + rsSP.getString("TEN"), noiSX,  rsSP.getString("DONVI"),
						                      DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)) };


				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					// cho stt ra ngoai
					if(j==0)  cells.setPaddingLeft(-2.0f * CONVERT);
					
					if ( j==1){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						if(j <=3 )
						{
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
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
			
			query = sqlIN_CHIETKHAU;
				 System.out.println("---INIT TICH LUY 2: " + query);
				 double tienVAT = 0;
			     double tienAVAT = 0;
			     double tienBVAT = 0;
				 ResultSet rs = db.get(query);
				 
				 if(rs != null)
				 {												
					try 
					{				
				        while(rs.next())
					    {
				        	String maCK = rs.getString("DienGiai");
				        	String diengiaiCK ="";
				        	// LAY TEN CHIET KHAU
				        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
				        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
				        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
				        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
				        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
				        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
				        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
				        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
								
				        	
				        	
							if(SoNgay(ngayxuatHD)&&SoNgayCKQ(ngayxuatHD)){								
								tienVAT = rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
								tienBVAT = rs.getDouble("chietkhau");
								
								totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
								totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
								totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
							}
							else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
								 tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
						         tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
						         tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
						         
						        totalTienCK += tienAVAT  ;
						 		totalTienCK_ChuaVat += tienBVAT;
						 		totalVatCK += tienVAT;
							}
							else{						
								tienVAT = rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
								tienBVAT = rs.getDouble("chietkhau");
								
								totalTienCK += rs.getDouble("chietkhau") + tienVAT  ;
								totalTienCK_ChuaVat +=rs.getDouble("chietkhau");
								totalVatCK += tienVAT;
							}
							
							String [] arr5;
							if(SoNgay(ngayxuatHD)){
								arr5 = new String[] {Integer.toString(stt+1),maCK + " - "+ diengiaiCK ," ","1"," ", DinhDangTRAPHACO(formatter1.format(tienBVAT)),
									                       DinhDangTRAPHACO(formatter1.format(tienBVAT))};
							}
							else{
								arr5 = new String[] {Integer.toString(stt+1),maCK + " - "+ diengiaiCK ," ","1"," ", DinhDangTRAPHACO(formatter1.format(tienBVAT)),
					                       DinhDangTRAPHACO(formatter1.format(tienBVAT))};
							}
							
							for (int j = 0; j < arr5.length; j++)
							{
								cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
								if(j==0)  cells.setPaddingLeft(-2.0f * CONVERT);
								
									if(j == 1)
									{
										cells.setHorizontalAlignment(Element.ALIGN_LEFT);
									}
									else
									{
										if(j==0 || j==3 ) 
										{
											cells.setHorizontalAlignment(Element.ALIGN_CENTER);	
										}
										else cells.setHorizontalAlignment(Element.ALIGN_RIGHT);								
									}
								cells.setFixedHeight(0.6f*CONVERT);
								cells.setPaddingTop(2.5f);
								cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
								cells.setBorder(0);
								sanpham.addCell(cells);
							
						   }
						stt ++;
					  }
			        rs.close();
			
				  }catch (Exception e) 
				  {
					System.out.println("__EXCEPTION: " + e.getMessage());
				  }
			   }
				 
		   // DONG TRONG
			int kk=0;
			while(kk < 14-stt)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "};
	
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
			
			
			// Cộng tiền hàng
			
			double truocVAT = 0;
			String sauVat = "";
			String tienVat = "";
			
			if(SoNgay(ngayxuatHD)){ // dùng chung với 2 trường hợp
				truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
				tienVat = formatter1.format(totalThueGTGT - totalVatCK);
				sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
			}
			else{
				truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				tienVat = pxkBean.getTongVAT();
				sauVat = pxkBean.getTongtienAVAT();
			}	
			
			/*double truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));*/
				String[] arr = new String[] {" "," "," ", " ", " ", " ", DinhDangTRAPHACO(formatter1.format(truocVAT)) };
				for (int j = 0; j < arr.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
					
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					

					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingTop(0.5f * CONVERT);
					cells.setBorder(0);
					cells.setFixedHeight(1.2f*CONVERT);
					sanpham.addCell(cells);
				}
				
				// Thuế suất + Tiền thuế GTGT
				String[] arr3 = new String[] {" ",DinhDangTRAPHACO(formatter1.format(thuesuatGTGT))," ", " ", " ", " ", DinhDangTRAPHACO(tienVat) };
				for (int j = 0; j < arr3.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr3[j], new Font(bf, 10, Font.BOLDITALIC)));

					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
					if(j==1)  
					{
						cells.setPaddingRight(3.0f * CONVERT);				
					}
					
					if(j==2 )
					{ 
					   cells.setPaddingLeft(0.1f * CONVERT);
					}
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingTop(2.8f);
					cells.setBorder(0);
					cells.setFixedHeight(0.7f*CONVERT);
					sanpham.addCell(cells);
				}
				
				// Tổng cộng tiền thanh toán
				String[] arr4 = new String[] {" "," "," ", " ", " ", " ", DinhDangTRAPHACO(sauVat) };
				for (int j = 0; j < arr4.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr4[j], new Font(bf, 10, Font.BOLDITALIC)));
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);

					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingTop(2.8f);
					cells.setBorder(0);
					cells.setFixedHeight(0.7f*CONVERT);
					sanpham.addCell(cells);
				}
				
				// Tien bang chu
				doctienrachu doctien = new doctienrachu();
			    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
				String tien = doctien.docTien(Long.parseLong(sauVat.replaceAll(",", "")));
			  //Viết hoa ký tự đầu tiên
			    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			    
				String[] arr1 = new String[] {"                                         " +  TienIN};
				for (int j = 0; j < arr1.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
					
					if(j==0)
					{
						cells.setColspan(7);
					}
					cells.setHorizontalAlignment(Element.ALIGN_LEFT); 
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setBorder(0);
					cells.setFixedHeight(0.7f*CONVERT);
					sanpham.addCell(cells);
				}
				
																				
				document.add(sanpham);
			
			//document.close();
			
			
		   
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	private void CreatePxk_PHUONGMINH(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
	{
		try
		{
			dbutils db = new dbutils();
			
			 //LAY THONG TIN KHACHHANG 		  
			String sql ="";
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuatHD= "";
			String chucuahieu = "";
			String sotaikhoan= "";
			
			/*sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/			  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
			
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
		   		" 				( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
				"                         else isnull(nguoimua,'') end " +
				"           	  FROM HOADON" +
				"           	  WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
		        " from DONHANG " +
		        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
		   
	       System.out.println("Kho xuất "+sql);
		   ResultSet rsKho= db.get(sql);
		   if(rsKho.next())
		   {
			   hinhthucTT = rsKho.getString("HTTT");		   
			   ngayxuatHD = rsKho.getString("ngayxuathd");
			   chucuahieu = rsKho.getString("nguoimua");
			   rsKho.close();
		   }
		   
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 11, Font.NORMAL);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			// Dòng 1: ngày tháng
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(2.5f * CONVERT);
			cell.setPaddingLeft(1.9f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = ngayxuatHD.split("-");
			Paragraph pxk = new Paragraph(ngayHD[2] + "                    " + ngayHD[1] +  "                        " + ngayHD[0].substring(2) , new Font(bf, 11, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			
			// DONG TRONG
			PdfPCell cell0 = new PdfPCell();
			cell0.setBorder(0);
			
			pxk = new Paragraph( " ", font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell0.setFixedHeight(3.65f * CONVERT);
			cell0.addElement(pxk);	
			
			tableheader.addCell(cell0);
			
			// Dòng 2: người mua hàng
			
			PdfPCell cell_mh = new PdfPCell();
			cell_mh.setBorder(0);
			cell_mh.setPaddingLeft(3.5f * CONVERT);
			cell_mh.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph( "                   " + chucuahieu , font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_mh.setFixedHeight(0.8f * CONVERT);
			cell_mh.addElement(pxk);
			
			tableheader.addCell(cell_mh);
			
			// Dòng 3: tên đơn vị
			
			PdfPCell cell_dv = new PdfPCell();
			cell_dv.setBorder(0);
			cell_dv.setPaddingLeft(3.0f * CONVERT);
			cell_dv.setPaddingTop(-0.1f * CONVERT);
			cell_dv.setFixedHeight(0.8f * CONVERT);
			cell_dv.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph(Donvi , font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_dv.addElement(pxk);
			
			tableheader.addCell(cell_dv);
			
			// Dòng 5: Địa chỉ
			
			PdfPCell cell_dc = new PdfPCell();
			cell_dc.setBorder(0);
			cell_dc.setPaddingLeft(3.0f * CONVERT);
			cell_dc.setFixedHeight(0.8f * CONVERT);
			cell_dv.setPaddingTop(-0.1f * CONVERT);
			cell_dc.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph(kh_Diachi, font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_dc.addElement(pxk);
			pxk.setSpacingAfter(2);
							
			tableheader.addCell(cell_dc);
			
			// Dòng 4 : Mã số thuế
			
			PdfPCell cell_mst = new PdfPCell();
			cell_mst.setBorder(0);
			cell_mst.setPaddingLeft(3.0f * CONVERT);
			cell_mst.setFixedHeight(0.8f * CONVERT);
			cell_mst.setPaddingTop(-0.1f * CONVERT);
			cell_mst.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph(kh_MST, font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_mst.addElement(pxk);
			pxk.setSpacingAfter(2);
							
			tableheader.addCell(cell_mst);
			
			
			
			// Dòng 6 : HTTT + Số tài khoản
			
			PdfPCell cell_httt = new PdfPCell();
			cell_httt.setBorder(0);
			cell_httt.setPaddingLeft(4.5f * CONVERT);
			cell_httt.setFixedHeight(0.8f * CONVERT);
			cell_httt.setPaddingTop(-0.05f * CONVERT);
			cell_httt.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph(hinhthucTT  +  "                                       " + sotaikhoan, font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_httt.addElement(pxk);
			pxk.setSpacingAfter(2);
							
			tableheader.addCell(cell_httt);
			
			document.add(tableheader); 
			
			// BẢNG SẢN PHẨM
			
			// Table Content

			String[] th = new String[]{ " ", " ", "  ", " "," " ," "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(42.0f); 
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 7.0f, 87.0f, 19.0f, 20.0f, 20.0f, 30f};
			sanpham.setWidths(withsKM);
				

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			//KHAC CHI NHANH KHAC XIU
			String query = "select MA, TEN, DONVI, DONGIA, SOLO, NGAYHETHAN, THUEVAT, SOLUONG, CHIETKHAU,  " +
								"case when MA ='3KOOL' then 'JAPAN'  when MA = '5TRIB' then 'AUSTRALIA' else 'TRAPHACO' end as  NoiSX   " +
						   "from HOADON_SP_CHITIET where hoadon_fk = '" + pxkBean.getId() + "'";
			System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			double thuesuatGTGT = 0;
			int stt = 1;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				double thanhtien = Math.round(soLUONG * dongia );	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				thuesuatGTGT = rsSP.getDouble("thuevat");
				String noiSX = rsSP.getString("NoiSX");
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") + " - " + rsSP.getString("TEN"),  rsSP.getString("DONVI"),
						                      DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)) };


				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 11, Font.BOLD)));
					// cho stt ra ngoai
					if(j==0)  cells.setPaddingLeft(-2.0f * CONVERT);
					
					if ( j==1){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						cells.setPaddingLeft(-0.3f * CONVERT);
					}
					else{
						if(j <=3 )
						{
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else
						{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.8f * CONVERT);
					cells.setPaddingTop(2.5f);
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			
			stt= stt-1;
			
			// DONG TRONG
			int kk=0;
			while(kk < 9-stt)
			{
				String[] arr_bosung = new String[] { " ", " " ,  " "," ", " "," "};
	
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
			
			stt= kk++;
			
			query = sqlIN_CHIETKHAU;
				 System.out.println("---INIT TICH LUY 2: " + query);
				 double tienVAT = 0;
			     double tienAVAT = 0;
			     double tienBVAT = 0;
				 ResultSet rs = db.get(query);
				 
				 if(rs != null)
				 {												
					try 
					{				
				        while(rs.next())
					    {
				        	String maCK = rs.getString("DienGiai");
				        	String diengiaiCK ="";
				        	// LAY TEN CHIET KHAU	
							if(SoNgay(ngayxuatHD)&&SoNgayCKQ(ngayxuatHD)){								
								tienVAT = rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
								tienBVAT = rs.getDouble("chietkhau");
								
								totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
								totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
								totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
							}
							else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
								 tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
						         tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
						         tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
						         
						        totalTienCK += tienAVAT  ;
						 		totalTienCK_ChuaVat += tienBVAT;
						 		totalVatCK += tienVAT;
							}
							else{						
								tienVAT = rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
								tienBVAT = rs.getDouble("chietkhau");
								
								totalTienCK += rs.getDouble("chietkhau") + tienVAT  ;
								totalTienCK_ChuaVat +=rs.getDouble("chietkhau");
								totalVatCK += tienVAT;
							}
							
							String [] arr5;
							if(SoNgay(ngayxuatHD)){
								arr5 = new String[] {"","Tiền chiết khấu" ,""," ", "",
									                       DinhDangTRAPHACO(formatter1.format(tienBVAT))};
							}
							else{
								arr5 = new String[] {"","Tiền chiết khấu" ,""," ", "",
					                       DinhDangTRAPHACO(formatter1.format(tienBVAT))};
							}
							
							for (int j = 0; j < arr5.length; j++)
							{
								cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 11 , Font.BOLD)));
								if(j==0)  cells.setPaddingLeft(-2.0f * CONVERT);
								
									if(j == 1)
									{
										cells.setHorizontalAlignment(Element.ALIGN_LEFT);
										cells.setPaddingLeft(-0.3f * CONVERT);
									}
									else
									{
										if(j==0 || j==3 ) 
										{
											cells.setHorizontalAlignment(Element.ALIGN_CENTER);	
										}
										else cells.setHorizontalAlignment(Element.ALIGN_RIGHT);								
									}
								cells.setFixedHeight(0.8f*CONVERT);
								cells.setPaddingTop(2.5f);
								cells.setVerticalAlignment(Element.ALIGN_TOP);
								cells.setBorder(0);
								sanpham.addCell(cells);
							
						   }
						stt ++;
					  }
			        rs.close();
			
				  }catch (Exception e) 
				  {
					System.out.println("__EXCEPTION: " + e.getMessage());
				  }
			   }
			
				 
				 //DONG TRONG
		   cells = new PdfPCell();
		   cells.setBorder(0);
					
		   pxk = new Paragraph( " ", font);
		   pxk.setAlignment(Element.ALIGN_LEFT);
		  cells.setFixedHeight(0.7f * CONVERT);
		  cells.addElement(pxk);	
		  cells.setColspan(6);
		  sanpham.addCell(cells);
		  
			// Cộng tiền hàng
			
			double truocVAT = 0;
			String sauVat = "";
			String tienVat = "";
			
			if(SoNgay(ngayxuatHD)){ // dùng chung với 2 trường hợp
				truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
				tienVat = formatter1.format(totalThueGTGT - totalVatCK);
				sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
			}
			else{
				truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				tienVat = pxkBean.getTongVAT();
				sauVat = pxkBean.getTongtienAVAT();
			}	
			
			/*double truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));*/
				String[] arr = new String[] {" "," ", " ", " ", " ", DinhDangTRAPHACO(formatter1.format(truocVAT)) };
				for (int j = 0; j < arr.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 11, Font.BOLDITALIC)));
					
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					

					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingTop(0.5f * CONVERT);
					cells.setBorder(0);
					cells.setFixedHeight(1.2f*CONVERT);
					sanpham.addCell(cells);
				}
				
				// Thuế suất + Tiền thuế GTGT
				String[] arr3 = new String[] {" ",DinhDangTRAPHACO(formatter1.format(thuesuatGTGT)), " ", " ", " ", DinhDangTRAPHACO(tienVat) };
				for (int j = 0; j < arr3.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr3[j], new Font(bf, 11, Font.BOLDITALIC)));

					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
					if(j==1)  
					{
						cells.setPaddingRight(6.0f * CONVERT);				
					}
					
					if(j==2 )
					{ 
					   cells.setPaddingLeft(0.1f * CONVERT);
					}
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingTop(2.8f);
					cells.setBorder(0);
					cells.setFixedHeight(0.7f*CONVERT);
					sanpham.addCell(cells);
				}
				
				// Tổng cộng tiền thanh toán
				String[] arr4 = new String[] {" "," ", " ", " ", " ", DinhDangTRAPHACO(sauVat) };
				for (int j = 0; j < arr4.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr4[j], new Font(bf, 11, Font.BOLDITALIC)));
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);

					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingTop(2.8f);
					cells.setBorder(0);
					cells.setFixedHeight(0.7f*CONVERT);
					sanpham.addCell(cells);
				}
				
				 //DONG TRONG
				   cells = new PdfPCell();
				   cells.setBorder(0);
							
				   pxk = new Paragraph( " ", font);
				  pxk.setAlignment(Element.ALIGN_LEFT);
				  cells.setFixedHeight(0.3f * CONVERT);
				  cells.addElement(pxk);	
				  cells.setColspan(6);
				  sanpham.addCell(cells);
				  
				// Tien bang chu
				doctienrachu doctien = new doctienrachu();
			    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
				String tien = doctien.docTien(Long.parseLong(sauVat.replaceAll(",", "")));
			  //Viết hoa ký tự đầu tiên
			    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			    
				String[] arr1 = new String[] {"                                         " +  TienIN};
				for (int j = 0; j < arr1.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 11, Font.BOLDITALIC)));
					
					if(j==0)
					{
						cells.setColspan(6);
					}
					cells.setHorizontalAlignment(Element.ALIGN_LEFT); 
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setBorder(0);
					cells.setFixedHeight(0.7f*CONVERT);
					sanpham.addCell(cells);
				}
				
																				
				document.add(sanpham);
			
			//document.close();
			
			
		   
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private void CreatePxk_HAIDUONG(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
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
	
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   
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
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuatHD= "";
			String chucuahieu = "";
			
		/*	sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/			  
		   
	   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
			
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
	   		" 			   ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"                FROM HOADON" +
			"                WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
	   }
		   
	    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		
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
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;	
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
					
					totalThueGTGT +=  thueGTGT;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
				else{
					thanhtien = soLUONG * dongia - chietkhau;	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + (thanhtien*rsSP.getDouble("thuevat")/100);
					
					totalThueGTGT +=  thanhtien*rsSP.getDouble("thuevat")/100 ;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };


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
			stt= stt-1;
			
			query = sqlIN_CHIETKHAU;
			 System.out.println("---INIT TICH LUY: " + query);
			 
			 double tienVAT = 0;
			 double tienBVAT = 0;
			 double tienAVAT = 0;
			 
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);
				
				
				if(SoNgay(ngayxuatHD)&&SoNgayCKQ(ngayxuatHD)){	
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + Math.round(rs.getDouble("chietkhau"));
					
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
					
				}
				else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
					tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT =  rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
						
					totalTienCK +=  rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK +=  rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				}
				
				String [] arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ,"","","1","","", DinhDangTRAPHACO(formatter1.format(tienBVAT)), DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
							DinhDangTRAPHACO(formatter1.format(tienVAT)) , DinhDangTRAPHACO(formatter1.format(tienAVAT)) };
				
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
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
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
	}
	stt = stt -1;
   }
			
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
		
		double truocVAT = 0;
		String sauVat = "";
		String tienVat = "";
		
		if(SoNgay(ngayxuatHD)){
			truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
			tienVat = formatter1.format(totalThueGTGT - totalVatCK);
			sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
		}
		else{
			truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
			tienVat = pxkBean.getTongVAT();
			sauVat = pxkBean.getTongtienAVAT();
		}	
		
		String[] arr = new String[] {" "," "," "," "," "," "," "," ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };
		for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
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
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(2.8f);
				cells.setBorder(0);
				cells.setFixedHeight(0.7f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			//document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
	}

	private void CreatePxk_NAMDINH(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
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
			String ngayxuatHD= "";
			String chucuahieu= "";
	/*		sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";*/				  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
			
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
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho,(select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"  				(select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd ,  "+
	   		" 			  ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"               FROM HOADON" +
			"               WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ = (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
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
		cell.setPaddingLeft(2.2f * CONVERT);
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
		pxk = new Paragraph( kh_MST + "                                                    " + hinhthucTT, new Font(bf, 10, Font.NORMAL));
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
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.5f * CONVERT,2.0f * CONVERT, 2.0f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT,3.5f * CONVERT };
			
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.7f * CONVERT, 2.5f * CONVERT, 2.5f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT, 3.5f * CONVERT };
			
			float[] withsKM = { 10.0f, 20.0f, 61f, 17f, 18f, 13.0f, 14.0f, 24f, 20.4f, 20.0f, 25.0f, 25.0f }; //
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			

			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;	
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
					
					totalThueGTGT +=  thueGTGT;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
				else{
					thanhtien = soLUONG * dongia - chietkhau;	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + (thanhtien*rsSP.getDouble("thuevat")/100);
					
					totalThueGTGT +=  thanhtien*rsSP.getDouble("thuevat")/100 ;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, 
						rsSP.getString("DONVI"),						
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };


				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j <= 2  ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else
					{
                    	cells.setHorizontalAlignment(Element.ALIGN_RIGHT);						
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.0f);
					
					if(j==0||j==1)
						cells.setPaddingLeft(0.5f*CONVERT);
					
					if(j==5||j==7)
						cells.setPaddingRight(0.3f*CONVERT);
					
					if(j==8)
						cells.setPaddingRight(0.2f*CONVERT);
					
					if(j==9||j==10)
						cells.setPaddingRight(1.0f*CONVERT);
					
					if(j==11)
						cells.setPaddingRight(0.6f*CONVERT);
					
															
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			stt= stt-1;
		
			query = sqlIN_CHIETKHAU;
			 System.out.println("---INIT TICH LUY: " + query);
			 
			 double tienVAT =0;
			 double tienAVAT = 0;
		     double tienBVAT = 0;
		     
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";

		        	
				tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
				
				if(SoNgay(ngayxuatHD)&&SoNgayCKQ(ngayxuatHD)){		
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT =	tienVAT + Math.round(rs.getDouble("chietkhau"));
						
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
					
					System.out.println("TIEN:"+totalTienCK_ChuaVat+"   "+ totalVatCK+ "   "+totalTienCK );
					
				}
				else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK +=  rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK +=  rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				}
					
				
				String [] arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK , "", "", "1","","", DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
						DinhDangTRAPHACO(formatter1.format(tienVAT)) ,DinhDangTRAPHACO(formatter1.format(tienAVAT))};
				
				
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
					
					if (j <= 2  ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else
					{
                    	cells.setHorizontalAlignment(Element.ALIGN_RIGHT);						
					}
					
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setPaddingTop(2.0f);
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					
					if(j==0||j==1)
						cells.setPaddingLeft(0.5f*CONVERT);
					
					if(j==5||j==7)
						cells.setPaddingRight(0.3f*CONVERT);
					
					if(j==8)
						cells.setPaddingRight(0.2f*CONVERT);
					
					if(j==9||j==10)
						cells.setPaddingRight(1.0f*CONVERT);
					
					if(j==11)
						cells.setPaddingRight(0.6f*CONVERT);
					

					sanpham.addCell(cells);
				}
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
	}
   }
			
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
			
			String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " "," " };
			for (int j = 0; j < th.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setBorder(0);
				cells.setFixedHeight(0.3f*CONVERT);
									
				sanpham.addCell(cells);
			}
			
			kk++;
			
			
			
			
		// Tong tien thanh toan	
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
			
			double truocVAT = 0;
			String sauVat = "";
			String tienVat = "";
			
			if(SoNgay(ngayxuatHD)){
				//
				String kthd = "SELECT count(*) dem FROM HOADON WHERE PK_SEQ =  " +pxkBean.getId() +" and PK_SEQ "+
						" IN (4000271885,4000271625,4000271808,4000272213,4000271543,4000271597,"+
							  "	4000272031,4000271637,4000271655,4000271574,4000272265,4000272322,4000272074,4000271731,4000272260,4000272144, "+
							  "	4000271611,	4000271789,	4000272088,	4000272201,	4000271969,4000272014,4000272298,4000272111,4000271738,"+
							  "	4000272281, 4000272308,	4000271947,4000271986) \n";
				
				System.out.println(kthd);
				
				ResultSet kthd_sai = db.get(kthd);
				int count = 0;
				try 
			    {
					if(kthd_sai!= null)
				    {				    	
						while(kthd_sai.next())
						{
							count = kthd_sai.getInt("dem");
						}
				    }
					kthd_sai.close();
					
			    }
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				
				if(count >= 1){
					truocVAT = totalTienTruocVAT ;
					tienVat = formatter1.format(totalThueGTGT);
					sauVat = formatter1.format(totalSotienTT);
				}
				else{
					truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
					tienVat = formatter1.format(totalThueGTGT - totalVatCK);
					sauVat = formatter1.format(totalSotienTT - totalTienCK);
				}
								
			/*	totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
				totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
				totalTienCK = totalTienCK_ChuaVat+totalVatCK ;*/
			}
			else{
				truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				tienVat = pxkBean.getTongVAT();
				sauVat = pxkBean.getTongtienAVAT();
			}	
			
			String[] arr = new String[] {"","","","","","","","", DinhDangTRAPHACO(formatter1.format(truocVAT)) , " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat)};
			
			for (int j = 0; j < th.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
				if (j <= 2  ){
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else
				{
                	cells.setHorizontalAlignment(Element.ALIGN_RIGHT);						
				}
				
				cells.setVerticalAlignment(Element.ALIGN_TOP);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f * CONVERT);
				cells.setPaddingTop(2.0f);
				
				if(j==0||j==1)
					cells.setPaddingLeft(0.5f*CONVERT);
				
				if(j==5||j==7)
					cells.setPaddingRight(0.3f*CONVERT);
				
				if(j==8)
					cells.setPaddingRight(0.2f*CONVERT);
				
				if(j==9||j==10)
					cells.setPaddingRight(1.0f*CONVERT);
				
				if(j==11)
					cells.setPaddingRight(0.6f*CONVERT);
														
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
				cells.setVerticalAlignment(Element.ALIGN_TOP);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);				
				sanpham.addCell(cells);				
			}
			document.add(sanpham);

			//document.close();
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
	
	}

	private void CreatePxk_HADONG(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
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
			  		"        '04.38454813' AS DIENTHOAI,'04.36811542' AS FAX,'0100108656' AS MASOTHUE, '102010000004158 - NH TMCP Công thương VN, CN Ba Đình' as SOTAIKHOAN "+
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
			String ngayxuatHD= "";
			String chucuahieu= "";
			/*sql =   " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE   "+
		        	" from KHACHHANG " +
		        	" where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/		
			
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
	   
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
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho,(select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"  			(select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd,   "+
	   		" 			( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"             FROM HOADON" +
			"             WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ = (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
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
		cell.setPaddingTop(1.2f * CONVERT);
		cell.setPaddingLeft(3.0f * CONVERT);
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
		pxk = new Paragraph(chucuahieu, new Font(bf, 11, Font.NORMAL));
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
			
			String query = sqlIN_SANPHAM;
			
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;	
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
					
					totalThueGTGT +=  thueGTGT;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
				else{
					thanhtien = soLUONG * dongia - chietkhau;	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + (thanhtien*rsSP.getDouble("thuevat")/100);
					
					totalThueGTGT +=  thanhtien*rsSP.getDouble("thuevat")/100 ;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
						
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() >= 10)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				//NẾU NGÀY XUẤT HÓA ĐƠN > '2014-12-08' THÌ ĐƯA VỀ ĐỊNH DẠNG MỚI
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };
			
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
			stt= stt-1;
			
			//PHAI LAY TAI THOI DIEM CHOT
			query = sqlIN_CHIETKHAU;
			
			 System.out.println("---INIT TICH LUY: " + query);
			 ResultSet rs = db.get(query);
			 double tienVAT = 0;
		     double tienAVAT = 0;
		     double tienBVAT = 0;
		     
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);

		        	
				
				
				/* tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;
		         
		         tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;
		         
		         tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;*/
				
				if(SoNgay(ngayxuatHD) && SoNgayCKQ(ngayxuatHD)){				
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = Math.round(rs.getDouble("chietkhau"));
					tienAVAT = tienVAT + Math.round(rs.getDouble("chietkhau"));
					
					totalTienCK_ChuaVat += tienBVAT;	//tienBVAT	
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;//AVAT
				}
				else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = Math.round(rs.getDouble("chietkhau"));
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK +=  rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ; //tienAVAT
					totalTienCK_ChuaVat += rs.getDouble("chietkhau"); //tienBVAT
					totalVatCK +=  rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100; //tienVAT
				}
				
				//NẾU NGÀY XUẤT HÓA ĐƠN > '2014-12-08' THÌ ĐƯA VỀ ĐỊNH DẠNG MỚI
				String [] arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ,"1","","", DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
						DinhDangTRAPHACO(formatter1.format(tienVAT)) ,DinhDangTRAPHACO(formatter1.format(tienAVAT) )};
				
				
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
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
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
			
			double truocVAT = 0;
			String sauVat = "";
			String tienVat = "";
			
			if(SoNgay(ngayxuatHD)){
				truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
				tienVat = formatter1.format(totalThueGTGT - totalVatCK);
				sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
			}
			else{
				truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				tienVat = pxkBean.getTongVAT();
				sauVat = pxkBean.getTongtienAVAT();
			}	
		
			
		String[] arr = new String[] {"                             ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat),DinhDangTRAPHACO(sauVat) };
		
		
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
			
			String tien = doctien.docTien( Long.parseLong(sauVat.replaceAll(",", "")) );
			
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
			
			//document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
		
	}

	private void CreatePxk_NGHEAN(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU)
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
	
		   /*if(kbh.equals("100052")) // ETC
		   {*/
			  sql = " select PK_SEQ, TEN ,DIACHI," +
			  		"       DIENTHOAI, FAX, '0100108656-019' AS MASOTHUE, '102010001014275 - NH TMCP Công thương VN, CN Bến Thủy' as SOTAIKHOAN, isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
			        " from NHAPHANPHOI where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   /*}else{ //OTC
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   }*/
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
			String ngayxuatHD= "";
			String chucuahieu = "";
			/*sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";*/				  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
	   
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
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho,(select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"              (select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd ,  "+
	   		" 			  ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"               FROM HOADON" +
			"               WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
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
		
		String [] ngayHD = ngayxuatHD.split("-");
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
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			double thanhtien = 0;	
			double thueGTGT = 0;
			double sotientt = 0;
			
			int stt = 1;			
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
					
					totalThueGTGT += thueGTGT ;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
				else{
					thanhtien = soLUONG * dongia - chietkhau;	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + (thanhtien*rsSP.getDouble("thuevat")/100);
					
					totalThueGTGT += thanhtien*rsSP.getDouble("thuevat")/100 ;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
						
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("TEN"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };
				
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
					
					/*if(j >=5)
					{
						if(j==5)
						{
							cells.setPaddingLeft(0.25f *CONVERT);
						}else
						{
							cells.setPaddingLeft(0.1f *CONVERT);
						}
					}*/
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			stt= stt-1;
			
			query = sqlIN_CHIETKHAU;
			
			System.out.println("---INIT TICH LUY: " + query);
			
			double tienAVAT =0;
			double tienBVAT =0;
			double tienVAT = 0;
			
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);

				
				if(SoNgay(ngayxuatHD)&&SoNgayCKQ(ngayxuatHD)){
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);					
					tienBVAT = Math.round(rs.getDouble("chietkhau"));
					tienAVAT = tienVAT + tienBVAT;
					
					totalTienCK_ChuaVat += tienBVAT;	//tienBVAT				
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));//VAT
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;//AVAT
					
				/*	totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;*/
				}
				else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					
					tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);					
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + tienBVAT;
					
					totalTienCK += rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK += rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				}
				
				
				String [] arr5  = new String[] {Integer.toString(stt+1), diengiaiCK , " ", "1", " ", " ", DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
							DinhDangTRAPHACO(formatter1.format(tienVAT)) ,DinhDangTRAPHACO(formatter1.format(tienAVAT) )};
				
				
				
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
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
	}
   }
			
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
		
			double truocVAT = 0;
			String sauVat = "";
			String tienVat = "";
			if(SoNgay(ngayxuatHD)){
				truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
				tienVat = formatter1.format(totalThueGTGT - totalVatCK);
				sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
			}
			else{
				truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", "")) - Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				tienVat = pxkBean.getTongVAT();
				sauVat = pxkBean.getTongtienAVAT();
			}
			
			String[] arr = new String[] {"                             ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };
			
			
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

			//document.close();
		
			
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
	}

	private void CreatePxk_PHUTHO(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
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
			String ngayxuatHD= "";
			String chucuahieu= "";
/*			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE  "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/			  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
			
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
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho,(select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"               (select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd,   "+
	   		" 			  ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"               FROM HOADON" +
			"               WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ = (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
	   }
	   
	   
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(1.5f*CONVERT, 0.4f*CONVERT, 1.7f*CONVERT, 2.0f*CONVERT);  // L,R, T, B
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
		cell.setPaddingTop(0.8f * CONVERT);
		cell.setPaddingLeft(2.2f * CONVERT);
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
		cell88a.setPaddingLeft(4.2f * CONVERT);
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
		cell8a.setPaddingLeft(2.73f * CONVERT);
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
		cell10.setPaddingTop(0.2f*CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(3);
		//pxk.setSpacingBefore(12.0f);
		cell10.addElement(pxk);
		cell10.setBorder(0);					
		table1.addCell(cell10);
					
		
		PdfPCell cell14 = new PdfPCell();
		cell14.setPaddingLeft(1.6f * CONVERT);
		cell14.setPaddingTop(0.3f*CONVERT);
		pxk = new Paragraph(kh_Diachi, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(3);
		cell14.addElement(pxk);
		cell14.setBorder(0);						
		table1.addCell(cell14);						
		
		
		//DOng 4
		PdfPCell cell17 = new PdfPCell();	
		cell17.setPaddingLeft(2.9f * CONVERT);
		cell17.setPaddingTop(0.3f*CONVERT);
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
			kh_MST = "                              ";
		}

		while(kh_MST.length() < 14 )
			kh_MST = kh_MST + "         ";
		
		PdfPCell cell18 = new PdfPCell();		
		cell18.setPaddingLeft(4.7f * CONVERT); //4.8
		cell18.setPaddingTop(0.3f*CONVERT);
		pxk = new Paragraph( kh_MST + "                                                    " + hinhthucTT, new Font(bf, 10, Font.NORMAL));
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
			sanpham.setSpacingBefore(39.6850398f); //50,13,5
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.5f * CONVERT,2.0f * CONVERT, 2.0f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT,3.5f * CONVERT };
			
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.7f * CONVERT, 2.5f * CONVERT, 2.5f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT, 3.5f * CONVERT };
			
			float[] withsKM = { 7.0f, 15.0f, 58f, 15f, 15f, 9.0f, 14.0f, 24f, 24.0f, 8.0f, 25f, 23f }; //ô 10:22f
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM1 ]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt =0;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				if(SoNgay(ngayxuatHD)){
					thanhtien =Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
						
					totalThueGTGT +=  thueGTGT;
					totalTienTruocVAT+= thanhtien;
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
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };
				
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
					
					if(j <= 2)
					{
						cells.setPaddingLeft(0.3f*CONVERT);
					}
					if(j==3)
					{
						cells.setPaddingLeft(-9.4f);
					}
					else
					{
						
						if (j==5) 
						{
							cells.setPaddingRight(0.1f*CONVERT); //9
						}
						else if (j==7) 
						{
							cells.setPaddingRight(0.5f*CONVERT); //9
						}
						else if (j==8) 
						{
							cells.setPaddingRight(1.0f*CONVERT); //9
						}
						else if ( j == 9 )
						{
							cells.setPaddingRight(0.5f*CONVERT);
						}
						else if ( j == 10)
						{
							cells.setPaddingRight(1.0f*CONVERT);
						}
						else if ( j == 11 )
						{
							cells.setPaddingRight(0.7f*CONVERT);
						}
						
					}
															
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			stt= stt-1;
			
			query = sqlIN_CHIETKHAU;
			
			 System.out.println("---INIT TICH LUY: " + query);
			 
			 double tienVAT = 0;
		     double tienAVAT = 0;
		     double tienBVAT = 0;
		     
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);

				
				if(SoNgay(ngayxuatHD)&&SoNgayCKQ(ngayxuatHD)){					
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);					
					tienBVAT = Math.round(rs.getDouble("chietkhau"));
					tienAVAT = tienVAT + tienBVAT;
					
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
					
				}
				else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK += rs.getDouble("chietkhau")  + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK += rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				}
				
				String [] arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ," "," ","1"," "," ", DinhDangTRAPHACO(formatter1.format(rs.getDouble("chietkhau"))),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
						DinhDangTRAPHACO(formatter1.format(tienVAT)) ,DinhDangTRAPHACO(formatter1.format((tienVAT + Math.round(rs.getDouble("chietkhau"))) ))};
								
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
					cells.setPaddingTop(2.0f);
					

					if(j <= 2)
					{
						cells.setPaddingLeft(0.3f*CONVERT);
					}
					if(j==3)
					{
						cells.setPaddingLeft(-9.4f);
					}
					else
					{
						
						if (j==5) 
						{
							cells.setPaddingRight(0.1f*CONVERT); //9
						}
						else if (j==7) 
						{
							cells.setPaddingRight(0.5f*CONVERT); //9
						}
						else if (j==8) 
						{
							cells.setPaddingRight(1.0f*CONVERT); //9
						}
						else if ( j == 9 )
						{
							cells.setPaddingRight(0.5f*CONVERT);
						}
						else if ( j == 10)
						{
							cells.setPaddingRight(1.0f*CONVERT);
						}
						else if ( j == 11 )
						{
							cells.setPaddingRight(0.7f*CONVERT);
						}
						
					}
															
					sanpham.addCell(cells);
				}
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
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
		
			String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " "," " };

			for (int j = 0; j < th.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setBorder(0);
				cells.setFixedHeight(0.2f*CONVERT);									
				sanpham.addCell(cells);
			}
			
		// Tong tien thanh toan	
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
		
		double truocVAT = 0;
		String sauVat = "";
		String tienVat = "";
		
		if(SoNgay(ngayxuatHD)){
			truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
			tienVat = formatter1.format(totalThueGTGT - totalVatCK);
			sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
		}
		else {
			truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
			tienVat = pxkBean.getTongVAT();
			sauVat = pxkBean.getTongtienAVAT();
		}
		
		String[] arr = new String[] {" "," ", " "," "," "," "," "," ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };
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
				

				if(j <= 2)
				{
					cells.setPaddingLeft(0.3f*CONVERT);
				}
				if(j==3)
				{
					cells.setPaddingLeft(-9.4f);
				}
				else
				{
					
					if (j==5) 
					{
						cells.setPaddingRight(0.1f*CONVERT); //9
					}
					else if (j==7) 
					{
						cells.setPaddingRight(0.5f*CONVERT); //9
					}
					else if (j==8) 
					{
						cells.setPaddingRight(1.0f*CONVERT); //9
					}
					else if ( j == 9 )
					{
						cells.setPaddingRight(0.5f*CONVERT);
					}
					else if ( j == 10)
					{
						cells.setPaddingRight(1.0f*CONVERT);
					}
					else if ( j == 11 )
					{
						cells.setPaddingRight(0.7f*CONVERT);
					}
					
				}
				
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
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
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}															
			document.add(sanpham);

			//document.close();
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void CreatePxk_THANHHOA(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
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
			String ngayxuatHD= "";
			String chucuahieu= "";
/*			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE  "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/			  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
			
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
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho,(select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"               (select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd,   "+
	   		" 			  ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"               FROM HOADON" +
			"               WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ = (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
	   }
	   
	   
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(1.5f*CONVERT, 0.4f*CONVERT, 1.0f*CONVERT, 2.0f*CONVERT);  // R :1.2f
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
		cell.setPaddingLeft(2.7f * CONVERT);
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
		cell88a.setPaddingLeft(5.2f * CONVERT);
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
			kh_MST = "                              ";
		}

		while(kh_MST.length() < 14 )
			kh_MST = kh_MST + "         ";
		
		PdfPCell cell18 = new PdfPCell();		
		cell18.setPaddingLeft(5.3f * CONVERT); //4.8
		cell17.setPaddingTop(6.6f);
		pxk = new Paragraph( kh_MST + "                                                    " + hinhthucTT, new Font(bf, 10, Font.NORMAL));
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
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.5f * CONVERT,2.0f * CONVERT, 2.0f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT,3.5f * CONVERT };
			
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.7f * CONVERT, 2.5f * CONVERT, 2.5f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT, 3.5f * CONVERT };
			
			float[] withsKM = { 7.0f, 15.0f, 58f, 15f, 15f, 9.0f, 14.0f, 24f, 26.0f, 6.0f, 25f, 23f }; //ô 10:22f
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM1 ]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt =0;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				if(SoNgay(ngayxuatHD)){
					thanhtien =Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
						
					totalThueGTGT +=  thueGTGT;
					totalTienTruocVAT+= thanhtien;
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
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("solo"), 
							chuoi, rsSP.getString("DONVI"),
							DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };
				
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
					
					if(j <= 2)
					{
						cells.setPaddingLeft(0.3f*CONVERT);
					}
					if(j==3)
					{
						cells.setPaddingLeft(-9.4f);
					}
					
					else
					{
						if(j==10 )
						{
							cells.setPaddingRight(-10.0f); //6
						}
						else if (j==11) 
						{
							cells.setPaddingRight(-8.2f); //9
						}
						else if ( j >= 5 & j <9 )
						{
							 cells.setPaddingRight(-14.0f);
						}
						else if ( j == 9 )
						{
							 cells.setPaddingRight(-28.0f);
						}
						
					}
															
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			stt= stt-1;
			
			query = sqlIN_CHIETKHAU;
			
			 System.out.println("---INIT TICH LUY: " + query);
			 
			 double tienVAT = 0;
		     double tienAVAT = 0;
		     double tienBVAT = 0;
		     
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);

				
				if(SoNgay(ngayxuatHD)&&SoNgayCKQ(ngayxuatHD)){					
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);					
					tienBVAT = Math.round(rs.getDouble("chietkhau"));
					tienAVAT = tienVAT + tienBVAT;
					
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
					
				}
				else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK += rs.getDouble("chietkhau")  + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK += rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				}
				
				String [] arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ," "," ","1"," "," ", DinhDangTRAPHACO(formatter1.format(rs.getDouble("chietkhau"))),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
						DinhDangTRAPHACO(formatter1.format(tienVAT)) ,DinhDangTRAPHACO(formatter1.format((tienVAT + Math.round(rs.getDouble("chietkhau"))) ))};
								
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
					cells.setPaddingTop(2.0f);
					
					if(j <= 2)
					{
						cells.setPaddingLeft(0.3f*CONVERT);
					}
					if(j==3)
					{
						cells.setPaddingLeft(-9.4f);
					}
					
					else
					{
						if(j==10 )
						{
							cells.setPaddingRight(-10.0f); //6
						}
						else if (j==11) 
						{
							cells.setPaddingRight(-8.2f); //9
						}
						else if ( j >= 5 & j <9 )
						{
							 cells.setPaddingRight(-14.0f);
						}
						else if ( j == 9 )
						{
							 cells.setPaddingRight(-28.0f);
						}
						
					}
					

					sanpham.addCell(cells);
				}
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
		
		double truocVAT = 0;
		String sauVat = "";
		String tienVat = "";
		
		if(SoNgay(ngayxuatHD)){
			truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
			tienVat = formatter1.format(totalThueGTGT - totalVatCK);
			sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
		}
		else {
			truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
			tienVat = pxkBean.getTongVAT();
			sauVat = pxkBean.getTongtienAVAT();
		}
		
		String[] arr = new String[] {" "," ", " "," "," "," "," "," ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };
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
				
				if(j <= 2)
				{
					cells.setPaddingLeft(0.3f*CONVERT);
				}
				if(j==3)
				{
					cells.setPaddingLeft(-9.4f);
				}
				
				else
				{
					if(j==10 )
					{
						cells.setPaddingRight(-10.0f); //6
					}
					else if (j==11) 
					{
						cells.setPaddingRight(-8.2f); //9
					}
					else if ( j >= 5 & j <9 )
					{
						 cells.setPaddingRight(-14.0f);
					}
					else if ( j == 9 )
					{
						 cells.setPaddingRight(-28.0f);
					}
					
				}
				
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
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
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}															
			document.add(sanpham);

			//document.close();
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void CreatePxk_TIENGIANG(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
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
			String ngayxuatHD= "";
			String chucuahieu= "";
/*			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE  "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/			  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
			
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
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho,(select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"               (select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd,   "+
	   		" 			  ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"               FROM HOADON" +
			"               WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ = (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
	   }
		   
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(1.6f*CONVERT, 0.6f*CONVERT, 1.5f*CONVERT, 2.0f*CONVERT);  // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open();
		//document.setPageSize(new Rectangle(100.0f, 100.0f));
		//document.setPageSize(PageSize.A4.rotate());
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(0.5f * CONVERT);
		cell.setPaddingLeft(0.6f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuatHD.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                                " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		pxk.setSpacingAfter(5);
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
		/*cell88.setPaddingTop(-0.1f * CONVERT);*/
		cell88.setPaddingLeft(2.7f * CONVERT);
		cell88.setPaddingTop(-0.2f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
		cell88.addElement(pxk);
		cell88.setBorder(0);						
		table1.addCell(cell88);	
		
		PdfPCell cell88a = new PdfPCell();
		/*cell88a.setPaddingTop(-0.1f * CONVERT);*/
		cell88a.setPaddingLeft(5.7f * CONVERT);
		cell88a.setPaddingTop(-0.2f * CONVERT);
		pxk = new Paragraph(chucuahieu, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
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
		pxk.setSpacingAfter(9);
		cell8.addElement(pxk);
		cell8.setBorder(0);						
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell8a.setPaddingLeft(3.9f * CONVERT);
		cell8a.setPaddingTop(-0.2f * CONVERT);
		pxk = new Paragraph(Donvi, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(9);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);						
		table1.addCell(cell8a);
		
		//DONG 2
		PdfPCell cell30 = new PdfPCell();	
		cell30.setPaddingLeft(2.7f * CONVERT);
		cell30.setPaddingTop(-0.4f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(9);
		cell30.addElement(pxk);
		cell30.setBorder(0);						
		table1.addCell(cell30);	
		
		PdfPCell cell30a = new PdfPCell();
		cell30a.setPaddingLeft(3.9f * CONVERT);
		cell30a.setPaddingTop(-0.4f * CONVERT);
		pxk = new Paragraph(kh_MST, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(9);
		cell30a.addElement(pxk);
		cell30a.setBorder(0);						
		table1.addCell(cell30a);
		
		//DONG 3
		PdfPCell cell10 = new PdfPCell();
		cell10.setPaddingLeft(2.5f * CONVERT);	
		cell10.setPaddingTop(-0.4f * CONVERT);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(12);
		//pxk.setSpacingBefore(12.0f);
		cell10.addElement(pxk);
		cell10.setBorder(0);						
		table1.addCell(cell10);
					
		
		PdfPCell cell14 = new PdfPCell();
		cell14.setPaddingLeft(3.0f * CONVERT);
		cell14.setPaddingTop(-0.4f * CONVERT);
		pxk = new Paragraph(kh_Diachi, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(12);
		//pxk.setSpacingBefore(12.0f);
		cell14.addElement(pxk);
		cell14.setBorder(0);						
		table1.addCell(cell14);						
		
		
		//DOng 4
		PdfPCell cell17 = new PdfPCell();	
		cell17.setPaddingLeft(2.9f * CONVERT);
		//cell17.setPaddingTop(0.1f * CONVERT);
		pxk = new Paragraph(khoxuat, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell17.addElement(pxk);
		cell17.setBorder(0);						
		table1.addCell(cell17);	
		
		if(kh_MST.trim().length() <= 0)
		{
			kh_MST = "                              ";
		}

		while(kh_MST.length() < 14 )
			kh_MST = kh_MST + "         ";
		
		PdfPCell cell18 = new PdfPCell();		
		cell18.setPaddingLeft(5.3f * CONVERT); //4.8
		//cell18.setPaddingTop(-0.1f * CONVERT);
		pxk = new Paragraph( "" + hinhthucTT, new Font(bf, 11, Font.NORMAL));
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
			sanpham.setSpacingBefore(29.1f); //50,13,5
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.5f * CONVERT,2.0f * CONVERT, 2.0f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT,3.5f * CONVERT };
			
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.7f * CONVERT, 2.5f * CONVERT, 2.5f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT, 3.5f * CONVERT };
			
			float[] withsKM = { 7.0f, 15.0f, 50f, 14f, 18f, 9.0f, 12.0f, 22f, 21.0f, 11.0f, 25f, 27f }; //ô 10:22f
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM1 ]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt = 0;
			
			double sotienck = 0;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					System.out.println("thue: "+thueGTGT);
					sotientt = thanhtien + thueGTGT;
												
					totalThueGTGT += thueGTGT;
					totalTienTruocVAT+= thanhtien;
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
				//chuoi = "2014-10-15";
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };

				/*String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), "AL003", 
						chuoi, rsSP.getString("DONVI"),
						formatter1.format(soLUONG), formatter.format(dongia),formatter1.format(thanhtien),formatter1.format(rsSP.getDouble("thuevat")), formatter1.format(thueGTGT),formatter1.format(sotientt) };
*/
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j == 2 || j==1 || j==0 || j== 4 || j == 5){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{						
						/*if(j <=4 )
						{
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else{*/
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						//}
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					//cells.setBorderWidth(1);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.0f);
					
					if(j <= 1)
					{
						cells.setPaddingLeft(-0.5f*CONVERT);
					}
					
					else if(j==2)
					{
						cells.setPaddingLeft(-0.6f*CONVERT);
					}
					else if(j==3)
					{
						cells.setPaddingRight(1.0f*CONVERT);
					}
					else
					{
						if(j==10 )
						{
							cells.setPaddingRight(1.2f*CONVERT); //6
						}
						else if (j==11) 
						{
							cells.setPaddingRight(1.2f*CONVERT);
						}
						else if (j==4) 
						{
							cells.setPaddingLeft(-0.6f*CONVERT);
						}
						else if (j==5) 
						{
							cells.setPaddingLeft(-0.5f*CONVERT);
						}
						else if (j==6) 
						{
							cells.setPaddingRight(0.7f*CONVERT);
						}
						else if ( j == 7 )
						{
							cells.setPaddingRight(0.8f*CONVERT);
						}
						else if ( j == 8 )
						{
							cells.setPaddingRight(0.5f*CONVERT);
						}
						else if ( j == 9 )
						{
							cells.setPaddingRight(0.8f*CONVERT);
						}
						
					}
															
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			stt= stt-1;
			
			query = sqlIN_CHIETKHAU;
			
			 System.out.println("---INIT TICH LUY: " + query);
			 
			 double tienVAT =0;
			 double tienAVAT = 0;
		     double tienBVAT = 0;
		     			 
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);
	        					
				if(SoNgay(ngayxuatHD)&&SoNgayCKQ(ngayxuatHD)){
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = Math.round (rs.getDouble("chietkhau"));
					tienAVAT = tienVAT + tienBVAT;						
						
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
					
				}
				else if (SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienBVAT =  tienAVAT / ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK += rs.getDouble("chietkhau")  + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK += rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				}
				String [] arr5  = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ," "," ","1"," "," ", DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
							DinhDangTRAPHACO(formatter1.format(tienVAT)) , DinhDangTRAPHACO(formatter1.format(tienAVAT) )};
				
				
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
					if (j == 2 || j==1 || j==0 || j== 4 || j == 5){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{		
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.0f);
					
					if(j <= 1)
					{
						cells.setPaddingLeft(-0.5f*CONVERT);
					}
					
					else if(j==2)
					{
						cells.setPaddingLeft(-0.6f*CONVERT);
					}
					else if(j==3)
					{
						cells.setPaddingRight(1.0f*CONVERT);
					}
					else
					{
						if(j==10 )
						{
							cells.setPaddingRight(1.2f*CONVERT); //6
						}
						else if (j==11) 
						{
							cells.setPaddingRight(1.2f*CONVERT);
						}
						else if (j==4) 
						{
							cells.setPaddingLeft(-0.6f*CONVERT);
						}
						else if (j==5) 
						{
							cells.setPaddingLeft(-0.5f*CONVERT);
						}
						else if (j==6) 
						{
							cells.setPaddingRight(0.7f*CONVERT);
						}
						else if ( j == 7 )
						{
							cells.setPaddingRight(0.8f*CONVERT);
						}
						else if ( j == 8 )
						{
							cells.setPaddingRight(0.5f*CONVERT);
						}
						else if ( j == 9 )
						{
							cells.setPaddingRight(0.8f*CONVERT);
						}
						
					}
					

					sanpham.addCell(cells);
				}
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
	}
   }
			
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
			
		double truocVAT = 0;
		String sauVat = "";
		String tienVat = "";
		if(SoNgay(ngayxuatHD)){
			truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
			tienVat = formatter1.format(totalThueGTGT - totalVatCK);
			sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
		}
		else{
			truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
			tienVat = pxkBean.getTongVAT();
			sauVat = pxkBean.getTongtienAVAT();
		}
		
		String[] arr = new String[] {" "," ", " "," "," "," "," "," ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };
		
		for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 2 || j==1 || j==0 || j== 4 || j == 5){
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else{		
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				
				if(j <= 1)
				{
					cells.setPaddingLeft(-0.5f*CONVERT);
				}
				
				else if(j==2)
				{
					cells.setPaddingLeft(-0.6f*CONVERT);
				}
				else if(j==3)
				{
					cells.setPaddingRight(1.0f*CONVERT);
				}
				else
				{
					if(j==10 )
					{
						cells.setPaddingRight(1.2f*CONVERT); //6
					}
					else if (j==11) 
					{
						cells.setPaddingRight(1.2f*CONVERT);
					}
					else if (j==4) 
					{
						cells.setPaddingLeft(-0.6f*CONVERT);
					}
					else if (j==5) 
					{
						cells.setPaddingLeft(-0.5f*CONVERT);
					}
					else if (j==6) 
					{
						cells.setPaddingRight(0.7f*CONVERT);
					}
					else if ( j == 7 )
					{
						cells.setPaddingRight(0.8f*CONVERT);
					}
					else if ( j == 8 )
					{
						cells.setPaddingRight(0.5f*CONVERT);
					}
					else if ( j == 9 )
					{
						cells.setPaddingRight(0.8f*CONVERT);
					}
					
				}
				
				//cells.setPaddingTop(-0.6f*CONVERT);
				cells.setVerticalAlignment(Element.ALIGN_TOP);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
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
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(-0.3f * CONVERT);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}															
			document.add(sanpham);

			//document.close();
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	private void CreatePxk_VINHLONG(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
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
			String ngayxuatHD= "";
			String chucuahieu= "";
/*			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE  "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/			  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
			
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
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho,(select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"               (select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd,   "+
	   		" 			  ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"               FROM HOADON" +
			"               WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ = (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
	   }
		   
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(1.6f*CONVERT, 0.6f*CONVERT, 0.5f*CONVERT, 2.0f*CONVERT);  // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open();
		//document.setPageSize(new Rectangle(100.0f, 100.0f));
		//document.setPageSize(PageSize.A4.rotate());
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(0.6f * CONVERT);
		cell.setPaddingLeft(2.7f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuatHD.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                             " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		pxk.setSpacingAfter(5);
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
		/*cell88.setPaddingTop(-0.1f * CONVERT);*/
		cell88.setPaddingLeft(2.7f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
		cell88.addElement(pxk);
		cell88.setBorder(0);						
		table1.addCell(cell88);	
		
		PdfPCell cell88a = new PdfPCell();
		/*cell88a.setPaddingTop(-0.1f * CONVERT);*/
		cell88a.setPaddingLeft(5.7f * CONVERT);
		pxk = new Paragraph(chucuahieu, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
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
		pxk.setSpacingAfter(9);
		cell8.addElement(pxk);
		cell8.setBorder(0);						
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell8a.setPaddingLeft(3.9f * CONVERT);
		cell8a.setPaddingTop(-0.2f * CONVERT);
		pxk = new Paragraph(Donvi, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(9);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);						
		table1.addCell(cell8a);
		

		//DONG 3
		PdfPCell cell10 = new PdfPCell();
		cell10.setPaddingLeft(2.5f * CONVERT);	
		//cell10.setPaddingTop(10.0f);
		pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(12);
		//pxk.setSpacingBefore(12.0f);
		cell10.addElement(pxk);
		cell10.setBorder(0);						
		table1.addCell(cell10);
					
		
		PdfPCell cell14 = new PdfPCell();
		cell14.setPaddingLeft(3.0f * CONVERT);
		//cell14.setPaddingTop(10.0f);
		pxk = new Paragraph(kh_Diachi, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(12);
		//pxk.setSpacingBefore(12.0f);
		cell14.addElement(pxk);
		cell14.setBorder(0);						
		table1.addCell(cell14);						
		
		
		//DOng 4
		PdfPCell cell17 = new PdfPCell();	
		cell17.setPaddingLeft(2.9f * CONVERT);
		cell17.setPaddingTop(10.0f);
		pxk = new Paragraph(khoxuat, new Font(bf, 11, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell17.addElement(pxk);
		cell17.setBorder(0);						
		table1.addCell(cell17);	
		
		if(kh_MST.trim().length() <= 0)
		{
			kh_MST = "                              ";
		}

		while(kh_MST.length() < 14 )
			kh_MST = kh_MST + "         ";
		
		PdfPCell cell18 = new PdfPCell();		
		cell18.setPaddingLeft(5.3f * CONVERT); //4.8
		cell17.setPaddingTop(10.0f);
		pxk = new Paragraph( kh_MST + "                                           " + hinhthucTT, new Font(bf, 10, Font.NORMAL));
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
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.5f * CONVERT,2.0f * CONVERT, 2.0f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT,3.5f * CONVERT };
			
			//float[] withsKM = { 1.2f * CONVERT, 2.0f * CONVERT, 7.7f * CONVERT, 2.5f * CONVERT, 2.5f * CONVERT, 1.5f * CONVERT,2.0f * CONVERT, 2.5f * CONVERT, 3.0f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT, 3.5f * CONVERT };
			
			float[] withsKM = { 7.0f, 15.0f, 69f, 17f, 19f, 9.0f, 14.0f, 23f, 23.0f, 6.0f, 25f, 27f }; //ô 10:22f
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM1 ]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt = 0;
			
			double sotienck = 0;
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
												
					totalThueGTGT += thueGTGT;
					totalTienTruocVAT+= thanhtien;
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
				//chuoi = "2014-10-15";
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };

				/*String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), "AL003", 
						chuoi, rsSP.getString("DONVI"),
						formatter1.format(soLUONG), formatter.format(dongia),formatter1.format(thanhtien),formatter1.format(rsSP.getDouble("thuevat")), formatter1.format(thueGTGT),formatter1.format(sotientt) };
*/
				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j == 2 || j==1 || j==0 ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{						
						/*if(j <=4 )
						{
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else{*/
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						//}
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.0f);
					
					if(j <= 2)
					{
						cells.setPaddingLeft(0.3f*CONVERT);
					}
					if(j==3)
					{
						cells.setPaddingLeft(-9.4f);
					}
					
					else
					{
						if(j==10 )
						{
							cells.setPaddingRight(-10.0f); //6
						}
						else if (j==11) 
						{
							cells.setPaddingRight(-8.2f); //9
						}
						else if ( j >= 5 & j <9 )
						{
							 cells.setPaddingRight(-14.0f);
						}
						else if ( j == 9 )
						{
							 cells.setPaddingRight(-28.0f);
						}
						
					}
															
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			stt= stt-1;
			
			query = sqlIN_CHIETKHAU;
			
			 System.out.println("---INIT TICH LUY: " + query);
			 
			 double tienVAT =0;
			 double tienAVAT = 0;
		     double tienBVAT = 0;
		     			 
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	
		        	// SỬ DỤNG CỘT inchietkhauKIEUMOI VÌ TỪ 2015-0 CHƯA CHẠY HÀM LÀM TRÒN MỚI
		        	int inchietkhauKIEUMOI = rs.getInt("inchietkhauKIEUMOI");
		        	
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("inchietkhauKIEUMOI254252: "+inchietkhauKIEUMOI);
	        					
		         if(inchietkhauKIEUMOI <= 0 ){
		        	 	tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
				        tienBVAT =  tienAVAT / ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
				        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
				         
				        totalTienCK += tienAVAT  ;
				 		totalTienCK_ChuaVat += tienBVAT;
				 		totalVatCK += tienVAT;
				 		System.out.println("2. VL");
		         }
		         else{
					if(SoNgay(ngayxuatHD)&&SoNgayCKQ(ngayxuatHD)){
						tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
						tienBVAT = Math.round (rs.getDouble("chietkhau"));
						tienAVAT = tienVAT + tienBVAT;						
							
						totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
						totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
						totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
						
						System.out.println("1.");
						
					}
					else if (SoNgay(ngayxuatHD)&&SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
						tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
				        tienBVAT =  tienAVAT / ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
				        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
				         
				        totalTienCK += tienAVAT  ;
				 		totalTienCK_ChuaVat += tienBVAT;
				 		totalVatCK += tienVAT;
				 		System.out.println("2.");
					}
					else{
						tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
						tienBVAT = rs.getDouble("chietkhau");
						tienAVAT = tienVAT + rs.getDouble("chietkhau");
						
						totalTienCK += rs.getDouble("chietkhau")  + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
						totalTienCK_ChuaVat += rs.getDouble("chietkhau");
						totalVatCK += rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
						
						System.out.println("3.");
					}
		         }
				String [] arr5  = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ," "," ","1"," "," ", DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
							DinhDangTRAPHACO(formatter1.format(tienVAT)) , DinhDangTRAPHACO(formatter1.format(tienAVAT) )};
				
				
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
					cells.setPaddingTop(2.0f);
					
					if(j <= 2)
					{
						cells.setPaddingLeft(0.3f*CONVERT);
					}
					if(j==3)
					{
						cells.setPaddingLeft(-9.4f);
					}
					
					else
					{
						if(j==10 )
						{
							cells.setPaddingRight(-10.0f); //6
						}
						else if (j==11) 
						{
							cells.setPaddingRight(-8.2f); //9
						}
						else if ( j >= 5 & j <9 )
						{
							 cells.setPaddingRight(-14.0f);
						}
						else if ( j == 9 )
						{
							 cells.setPaddingRight(-28.0f);
						}
						
					}
					

					sanpham.addCell(cells);
				}
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
			
		double truocVAT = 0;
		String sauVat = "";
		String tienVat = "";
		if(SoNgay(ngayxuatHD)){
			truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
			tienVat = formatter1.format(totalThueGTGT - totalVatCK);
			sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
		}
		else{
			truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
			tienVat = pxkBean.getTongVAT();
			sauVat = pxkBean.getTongtienAVAT();
		}
		
		String[] arr = new String[] {" "," ", " "," "," "," "," "," ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };
		
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
				
				if(j <= 2)
				{
					cells.setPaddingLeft(0.3f*CONVERT);
				}
				if(j==3)
				{
					cells.setPaddingLeft(-9.4f);
				}
				
				else
				{
					if(j==10 )
					{
						cells.setPaddingRight(-10.0f); //6
					}
					else if (j==11) 
					{
						cells.setPaddingRight(-8.2f); //9
					}
					else if ( j >= 5 & j <9 )
					{
						 cells.setPaddingRight(-14.0f);
					}
					else if ( j == 9 )
					{
						 cells.setPaddingRight(-28.0f);
					}
					
				}
				
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
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
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}															
			document.add(sanpham);

			//document.close();
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	
	private String CapnhatTT(String id, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			String trangthai="";
			// Kiem tra trạng thái hiện tại của Hóa đơn
			
			/*query = " select trangthai from HOADON where pk_seq = "+ id +" ";
			ResultSet rs = db.get(query);
			if(rs!= null)
			{
				while(rs.next())
				{
					trangthai = rs.getString("trangthai");
				}rs.close();
			}
			
			if(!trangthai.equals("3") && !trangthai.equals("5") )
			{*/
				// Cập nhật trạng thái HOADON Đã in đối với HD trạng thái đã xác nhận
				query = "update HOADON set trangthai = '4' where pk_seq = '" + id + "' and TrangThai=2 ";
				if(db.updateReturnInt(query)!=1)
				{
					msg = "Hóa đơn đã cập nhật trạng thái hoặc phát sinh lỗi" + query;
					db.getConnection().rollback();
					return msg;
				}
				
				// Cập nhật trạng thái DONHANG Đã in
				query = "update DONHANG set DAXUATHOADON = '1' where Trangthai=1 and pk_seq in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + id + "') ";
				if(db.updateReturnInt(query)!=1)
				{
					msg = "Hóa đơn đã cập nhật trạng thái hoặc phát sinh lỗi" + query;
					db.getConnection().rollback();
					return msg;
				}
			//}
			
			//LUU LAI LOG IN  --> TAM THOI BO DI DE THEO DOI TINH TRANG TREO
			
			/*query = "insert HOADON_LOG_IN( hoadon_fk, nguoiin ) values ( '" + id + "', '" + userId + "' )";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật HOADON " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "insert HOADON_LOG_IN_CHITIET(log_fk, hoadonId, sohoadon, kyhieu, ngayxuatHD, donhang_fk, ma, ten, solo, ngayhethan, donvi, soluong, dongia, thanhtien,  " +
					"		tienthue, thueVAT, thanhtienSAUVAT, STT, tichluyQUY ) " +
					"select IDENT_CURRENT('HOADON_LOG_IN'), hd.pk_seq as hoadonId, hd.sohoadon, hd.kyhieu, hd.ngayxuatHD, DAIN.*  " +
					"from HOADON hd  " +
					"inner join " +
					"( " +
					"	select hoadon_fk, MA, TEN, SOLO, NGAYHETHAN, DONVI, SOLUONG, cast(DONGIA as numeric(18, 2)) as DONGIA,  " +
					"			cast ( ( SOLUONG * DONGIA ) as numeric(18, 0) ) as thanhtien,  " +
					"			THUEVAT as tienTHUE,  cast( ( SOLUONG * DONGIA * thuevat / 100 ) as numeric(18, 0) ) as thueVAT, " +
					"			cast ( ( SOLUONG * DONGIA * ( 1 + thuevat / 100 ) ) as numeric(18, 0) ) as thanhtienSAUVAT, -1 as STT, 0 as 	tichluyQUY " +
					"	from HOADON_SP_CHITIET where hoadon_fk = '" + id + "'  " +
					"union ALL " +
					"	select hoadon_fk, diengiai as maCK, case diengiai when 'CN5' then N'Giảm trừ (Chiết khấu bán hàng ngay)' " +
					"						 when 'CN10' then N'Giảm trừ (Chiết khấu bán hàng ngay)' " +
					"						 when 'CT5' then N'Giảm trừ (CK Tháng)' " +
					"						 when 'CT10' then N'Giảm trừ (CK Tháng)' " +
					"						 when 'CQB5' then N'Giảm trừ (CK Quý nhóm hàng BG-HH)' " +
					"						 when 'CQX5' then N'Giảm trừ (CK Quý nhóm hàng XANH)' " +
					"						 when 'CQB10' then N'Giảm trừ (CK Quý nhóm hàng BG-HH)' " +
					"						 when 'CQX10' then N'Giảm trừ (CK Quý nhóm hàng XANH)' end as diengiai, ' ' as solo, ' ' as ngayhethan, '1' as donvi, 0 as soluong, 0 as dongia,  " +
					"						 cast(chietkhau as numeric(18, 0)) as chietkhau, thueVAT, " +
					"						 cast( ( chietkhau * thueVAT / 100 ) as numeric(18, 0) )  as tienVAT,  " +
					"						 cast( (chietkhau * ( 1 + thueVAT / 100 ) ) as numeric(18, 0) ) as thanhtien, STT, tichluyQUY " +
					"	from HOADON_CHIETKHAU  " +
					"	where hoadon_fk = '" + id + "' " +
					"union ALL " +
					"	select hoadon_fk, ' ' as MA, ' ' as TEN, ' ' as SOLO, ' ' as NGAYHETHAN, ' ' as DONVI, 0 as soluong, 0 as dongia,  " +
					"			cast( sum(thanhtien) as numeric(18, 0) ) as thanhtien, 0 as VAT,  " +
					"			cast( sum(thueVAT) as numeric(18, 0) ) as thueVAT,  " +
					"			cast( sum(thanhtienSAUVAT) as numeric(18, 0) ) as thanhtienSAUVAT, 10 as STT, 0 as tichluyQUY " +
					"	from " +
					"	( " +
					"			select hoadon_fk,  " +
					"					SOLUONG * DONGIA as thanhtien,  " +
					"					SOLUONG * DONGIA * thuevat / 100  as thueVAT, " +
					"					SOLUONG * DONGIA * ( 1 + thuevat / 100 ) as thanhtienSAUVAT " +
					"			from HOADON_SP_CHITIET where hoadon_fk = '" + id + "'  " +
					"			 " +
					"		union ALL " +
					"			select hoadon_fk, " +
					"					-1 * SUM(  chietkhau  ) as thanhtien,  " +
					"					-1 * SUM(  chietkhau * thueVAT / 100  ) as thueVAT,  " +
					"					-1 * SUM(  chietkhau * ( 1 + thueVAT / 100 ) ) as thanhtienSAUVAT " +
					"			from HOADON_CHIETKHAU  " +
					"			where hoadon_fk = '" + id + "' and hienthi = '1' " +
					"			group by hoadon_fk " +
					"	) " +
					"	TOTAL group by hoadon_fk " +
					") " +
					"DAIN on hd.pk_seq = DAIN.hoadon_fk " +
					"where hd.pk_seq = '" + id + "' " +
					"order by hd.ngayxuatHD, hd.pk_seq, DAIN.STT, DAIN.tichluyQUY " ;
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Không thể cập nhật HOADON " + query;
				db.getConnection().rollback();
				return msg;
			}*/
			
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

	private void CreatePxk(Document document, ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) throws IOException
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
			String ngayxuatHD= "";
			String chucuahieu = "";
			
			/*sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/			  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
	   
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
	   		" 			( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"             FROM HOADON" +
			"             WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
	   }
		   
	    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
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
		cell_nguoimua.addElement(pxk);
		cell_nguoimua.setBorder(0);						
		table1.addCell(cell_nguoimua);	
		
		PdfPCell cell_nguoimua1 = new PdfPCell();
		//cell_nguoimua1.setPaddingTop(0.5f * CONVERT);
		cell_nguoimua1.setPaddingLeft(4.0f * CONVERT);
		pxk = new Paragraph("", new Font(bf, 10, Font.NORMAL));
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
		cell10a.setPaddingTop(-0.2f * CONVERT);
		cell10a.setPaddingLeft(2.5f * CONVERT);	
		pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell10a.addElement(pxk);
		cell10a.setBorder(0);						
		table1.addCell(cell10a);
					
		
		PdfPCell cell14a = new PdfPCell();
		cell14a.setPaddingTop(-0.2f * CONVERT);
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
		cell18.setPaddingLeft(5.0f * CONVERT);
		cell18.setPaddingTop(-0.2f * CONVERT);
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
		sanpham.setSpacingBefore(52.4f);
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
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM_HN]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
						
					totalThueGTGT += thueGTGT;
					totalTienTruocVAT+= thanhtien;
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
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };


				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j <= 2 || j==4 ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						//if(j==9|| j==6 || j==0 || j==4 || j==3 || j==5 )
						if(j ==3 )
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
					
					if(j >=4)
					{
						if(j==4)
						{
							cells.setPaddingLeft(-0.10f *CONVERT);
						}else if(j==5)
						{
							cells.setPaddingLeft(0.20f *CONVERT);
						}
						else
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
			stt= stt-1;
			
			query = sqlIN_CHIETKHAU;
			 System.out.println("---INIT TICH LUY: " + query);
			 
			 double tienVAT = 0;
		     double tienAVAT = 0;
		     double tienBVAT = 0;
			 
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	
		         System.out.println("Ten dien giai: "+diengiaiCK);
				
				if(SoNgay(ngayxuatHD)&& SoNgayCKQ(ngayxuatHD)){
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + Math.round(rs.getDouble("chietkhau"));
					
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
					System.out.println("1.");
				}
				else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					 tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			         tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			         tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
			 		
			 		System.out.println("2.");
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK += rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK += rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100 ;
				}
				
				String [] arr5;
				if(SoNgay(ngayxuatHD)){
					arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ,"1","","", DinhDangTRAPHACO(formatter1.format(rs.getDouble("chietkhau"))),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
						DinhDangTRAPHACO(formatter1.format(tienVAT)) ,DinhDangTRAPHACO(formatter1.format((tienVAT + Math.round(rs.getDouble("chietkhau")))) )};
				}
				else{
					arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ,"1","","", DinhDangTRAPHACO(formatter1.format(rs.getDouble("chietkhau"))),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
							DinhDangTRAPHACO(formatter1.format(tienVAT)) ,DinhDangTRAPHACO(formatter1.format((tienVAT + rs.getDouble("chietkhau"))) )};
				}
				
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
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
		
		double truocVAT = 0;
		String sauVat = "";
		String tienVat = "";
		
		if(SoNgay(ngayxuatHD)){
			truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
			tienVat = formatter1.format(totalThueGTGT - totalVatCK);
			sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
		}
		else{
			truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
			tienVat = pxkBean.getTongVAT();
			sauVat = pxkBean.getTongtienAVAT();
		}
		
		
		String[] arr = new String[] {"                             ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };
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
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			//document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void CreatePxk_BINHTHUAN(Document document, ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) throws IOException
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
	
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   
		   System.out.println("Lấy TT CTY HP: " + sql);
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
			String ngayxuatHD= "";
			String chucuahieu = "";
	/*		
			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";*/				  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
	   
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
	   		" 			   ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"                FROM HOADON" +
			"                WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
	   }
		   
	    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(0.8f*CONVERT, 0.1f*CONVERT, 2.0f*CONVERT, 2.0f*CONVERT); // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open() ;
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(0.7f * CONVERT);
		cell.setPaddingLeft(2.0f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuatHD.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                                " + ngayHD[0].substring(2, 4) , new Font(bf, 8, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		pxk.setSpacingAfter(4);
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
		cell8.setPaddingTop(-0.19f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
		cell8.addElement(pxk);
		cell8.setBorder(0);						
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell8a.setPaddingTop(-0.19f * CONVERT);
		cell8a.setPaddingLeft(2.6f * CONVERT);
		pxk = new Paragraph(Donvi, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		//pxk.setSpacingAfter(2);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);						
		table1.addCell(cell8a);
		
		// DONG 3-- MA SO THUE
		cell8 = new PdfPCell();	
		cell8.setPaddingLeft(2.7f * CONVERT);
		cell8.setPaddingTop(-0.19f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		//pxk.setSpacingAfter(4);
		cell8.addElement(pxk);
		cell8.setBorder(0);						
		table1.addCell(cell8);	
		
		cell8a = new PdfPCell();
		cell8a.setPaddingTop(-0.19f * CONVERT);
		cell8a.setPaddingLeft(4.6f * CONVERT);
		pxk = new Paragraph(kh_MST, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		//pxk.setSpacingAfter(2);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);						
		table1.addCell(cell8a);
		

		// DONG 4 ---- DIA CHI
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
				
		
		PdfPCell cell14 = new PdfPCell();
		cell14.setPaddingLeft(1.7f * CONVERT);
		pxk = new Paragraph(kh_Diachi, new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		//pxk.setSpacingBefore(12.0f);
		cell14.addElement(pxk);
		cell14.setBorder(0);						
		table1.addCell(cell14);		
		
		
		//DONG 4 --- HTTT
		PdfPCell cell10a = new PdfPCell();
		cell10a.setPaddingLeft(3.5f * CONVERT);	
		pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell10a.addElement(pxk);
		cell10a.setBorder(0);						
		table1.addCell(cell10a);
					
		
		PdfPCell cell14a = new PdfPCell();
		cell14a.setPaddingLeft(4.2f * CONVERT);
		pxk = new Paragraph(hinhthucTT, new Font(bf, 10, Font.NORMAL));
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
		
		
		
		
		PdfPCell cell18 = new PdfPCell();
		cell18.setPaddingLeft(130.0f);
		cell18.setPaddingTop(-0.05f * CONVERT);
		pxk = new Paragraph( "", new Font(bf, 10, Font.NORMAL));
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
		sanpham.setSpacingBefore(36.5f); 
		sanpham.setWidthPercentage(100);
		sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		float[] withsKM = { 7.0f, 15.0f, 45.0f, 11.0f, 12f, 9.0f, 12.0f, 17f, 22.0f, 7.0f, 21f, 21.0f };
		sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
							
					totalThueGTGT += thueGTGT;
					totalTienTruocVAT+= thanhtien;
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
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };

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
					
					if( j == 1 )
						cells.setPaddingLeft(14.0f);
					
					if( j == 4 )
						cells.setPaddingLeft(-8.4f);
					
					if( j == 5 || j==6 )
						cells.setPaddingRight(8.4f);
					
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
					cells.setPaddingTop(2.4f);
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
						
				
			stt= stt-1;
			
			query = sqlIN_CHIETKHAU;
			 System.out.println("---INIT TICH LUY: " + query);
			 
			 double tienVAT = 0;
		     double tienAVAT = 0;
		     double tienBVAT = 0;
		     
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);
				
				if(SoNgay(ngayxuatHD) && SoNgayCKQ(ngayxuatHD)){
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + Math.round(rs.getDouble("chietkhau"));
					
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
				}
				else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK += rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK += rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100 ;
				}
				
				String [] arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ,"","","1","","", DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
						DinhDangTRAPHACO(formatter1.format(tienVAT)) , DinhDangTRAPHACO(formatter1.format(tienAVAT))};				
				
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
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
					
					if( j == 1 )
						cells.setPaddingLeft(14.0f);
					
					if( j == 4 )
						cells.setPaddingLeft(-8.4f);
					
					if( j == 5 || j==6 )
						cells.setPaddingRight(8.4f);
					
					if(j==8)
						cells.setPaddingRight(5.6f);
					
					if ( j == 9)
						cells.setPaddingRight(10.4f);
					
					if ( j == 10 )
						cells.setPaddingRight(18.4f);
					
					if ( j == 11 )
						cells.setPaddingRight(10.6f);

					cells.setFixedHeight(0.6f*CONVERT);
					cells.setPaddingTop(2.4f);
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					
					sanpham.addCell(cells);
				}
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
	}
   }
			
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
		
			double truocVAT = 0;
			String sauVat = "";
			String tienVat = "";
			
			if(SoNgay(ngayxuatHD)){
				truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
				tienVat = formatter1.format(totalThueGTGT - totalVatCK);
				sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
			}
			else{
				truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				tienVat = pxkBean.getTongVAT();
				sauVat = pxkBean.getTongtienAVAT();
			}
		
		String[] arr = new String[] { " ", " " , " ", " "," ", " "," "," ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };
		
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
				
				if( j == 1 )
					cells.setPaddingLeft(14.0f);
				
				if( j == 4 )
					cells.setPaddingLeft(-8.4f);
				
				if( j == 5 || j==6 )
					cells.setPaddingRight(8.4f);
				
				if(j==8)
					cells.setPaddingRight(5.6f);
				
				if ( j == 9)
					cells.setPaddingRight(10.4f);
				
				if ( j == 10 )
					cells.setPaddingRight(18.4f);
				
				if ( j == 11 )
					cells.setPaddingRight(10.6f);
				
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingTop(-27.4f);
				cells.setBorder(0);
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
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(-17.4f);
				cells.setBorder(0);
				cells.setFixedHeight(0.5f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			//document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	private void CreatePxk_QUANGNINH(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
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
	
			   sql = " select PK_SEQ, TEN ,DIACHIXHD as DIACHI, MASOTHUE,DIENTHOAI, FAX, '' as SOTAIKHOAN ,isnull(XUATTAIKHO,' ') as XUATTAIKHO "+
		        " from NHAPHANPHOI" +
		        " where PK_SEQ = (select npp_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";
		   
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
			   
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuatHD= "";
			String chucuahieu = "";
			
/*			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";			*/	  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
			
			
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
	   		" 			   ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"                FROM HOADON" +
			"                WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
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
		
		float[] withsKM = { 7.0f, 18.0f, 57f, 13f, 13f, 18.0f, 19.0f, 20.0f, 24.0f, 15.0f, 20f, 27f };
		sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;	
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
							
					totalThueGTGT += thueGTGT;
					totalTienTruocVAT+= thanhtien;
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
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"), rsSP.getString("SoLo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };


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
						cells.setPaddingLeft(0.2f*CONVERT);
					if(j== 3)
						cells.setPaddingLeft(-0.4f*CONVERT);
					if(j== 4)
						cells.setPaddingLeft(-0.4f*CONVERT);
					/*if(j==5)
						cells.setPaddingRight(0.4f *CONVERT);*/
					if(j== 6)
						cells.setPaddingRight(0.4f *CONVERT);
					if(j== 7)
						cells.setPaddingRight(0.6f *CONVERT);
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
			
			query = sqlIN_CHIETKHAU;
			 System.out.println("---INIT TICH LUY: " + query);
			 double tienVAT = 0;
		     double tienAVAT = 0;
		     double tienBVAT = 0;
		     
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);
				
				if(SoNgay(ngayxuatHD)){
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + Math.round(rs.getDouble("chietkhau"));
						
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
				}
				else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD))
				{
					tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK +=  rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK +=  rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				}
				
				
				String [] arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ," "," ","1"," "," ", DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
						DinhDangTRAPHACO(formatter1.format(tienVAT)) , DinhDangTRAPHACO(formatter1.format(tienAVAT))};
				
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
										

					cells.setFixedHeight(0.6f*CONVERT);
					cells.setPaddingTop(2.5f);
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					if(j== 0)
					    cells.setPaddingLeft(0.2f*CONVERT);
					if(j== 1)
						cells.setPaddingLeft(0.4f*CONVERT);
					if(j== 2)
						cells.setPaddingLeft(0.2f*CONVERT);
					if(j== 3)
						cells.setPaddingLeft(-0.4f*CONVERT);
					if(j== 4)
						cells.setPaddingLeft(-0.4f*CONVERT);
					/*if(j==5)
						cells.setPaddingRight(0.4f *CONVERT);*/
					if(j== 6)
						cells.setPaddingRight(0.4f *CONVERT);
					if(j== 7)
						cells.setPaddingRight(0.6f *CONVERT);
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
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
			
			double truocVAT = 0;
			String sauVat = "";
			String tienVat = "";
			
			if(SoNgay(ngayxuatHD)){
				truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
				tienVat = formatter1.format(totalThueGTGT - totalVatCK);
				sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
			}
			else{
				truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				tienVat = pxkBean.getTongVAT();
				sauVat = pxkBean.getTongtienAVAT();
			}				
		
		String[] arr = new String[] {" "," "," "," "," "," "," "," ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };
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
					cells.setPaddingLeft(0.2f*CONVERT);
				if(j== 3)
					cells.setPaddingLeft(-0.4f*CONVERT);
				if(j== 4)
					cells.setPaddingLeft(-0.4f*CONVERT);
				/*if(j==5)
					cells.setPaddingRight(0.4f *CONVERT);*/
				if(j== 6)
					cells.setPaddingRight(0.4f *CONVERT);
				if(j== 7)
					cells.setPaddingRight(0.6f *CONVERT);
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
				cells.setPaddingTop(-0.3f * CONVERT);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
																			
			document.add(sanpham);
			

			
			//document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
	}
	
	private void CreatePxk_CANTHO(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
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
			String ngayxuatHD= "";
			String chucuahieu= "";
/*			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE  "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/			  
		   
	   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
			
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
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho,(select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"               (select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd,   "+
	   		" 			  ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"               FROM HOADON" +
			"               WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ = (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
 
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
	   }
		   
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		document.setPageSize(PageSize.A4.rotate());
		document.setMargins(1.6f*CONVERT, 0.6f*CONVERT, 2.0f*CONVERT, 2.0f*CONVERT);  // L,R,T,B
		PdfWriter writer = PdfWriter.getInstance(document, outstream);
		
		document.open();
	

		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		
		
		PdfPTable tableheader =new PdfPTable(1);
		tableheader.setWidthPercentage(100);			

		PdfPCell cell = new PdfPCell();
		cell.setBorder(0);
		cell.setPaddingTop(0.6f * CONVERT);
		cell.setPaddingLeft(1.2f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		String [] ngayHD = ngayxuatHD.split("-");
		Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                             " + ngayHD[0].substring(2, 4) , new Font(bf, 9, Font.BOLDITALIC));
		pxk.setAlignment(Element.ALIGN_CENTER);
		pxk.setSpacingAfter(5);
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
		cell88.setPaddingLeft(2.7f * CONVERT);
		cell88.setPaddingTop(-0.1f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
		cell88.addElement(pxk);
		cell88.setBorder(0);						
		table1.addCell(cell88);	
		
		PdfPCell cell88a = new PdfPCell();
		cell88a.setPaddingTop(-0.1f * CONVERT);
		cell88a.setPaddingLeft(4.7f * CONVERT);
		pxk = new Paragraph(chucuahieu, new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(4);
		cell88a.addElement(pxk);
		cell88a.setBorder(0);						
		table1.addCell(cell88a);
		//
		
		//DONG 2
		PdfPCell cell8 = new PdfPCell();	
		cell8.setPaddingLeft(2.7f * CONVERT);
		cell8.setPaddingTop(-0.3f * CONVERT);
		pxk = new Paragraph(" "  , new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(9);
		cell8.addElement(pxk);
		cell8.setBorder(0);						
		table1.addCell(cell8);	
		
		PdfPCell cell8a = new PdfPCell();
		cell8a.setPaddingLeft(3.0f * CONVERT);
		cell8a.setPaddingTop(-0.3f * CONVERT);
		pxk = new Paragraph(Donvi, new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(9);
		cell8a.addElement(pxk);
		cell8a.setBorder(0);						
		table1.addCell(cell8a);
		

		//DONG 3
		PdfPCell cell10 = new PdfPCell();
		cell10.setPaddingTop(0.1f * CONVERT);
		cell10.setPaddingLeft(2.5f * CONVERT);	
		pxk = new Paragraph(" ", new Font(bf, 10, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(12);
		cell10.addElement(pxk);
		cell10.setBorder(0);						
		table1.addCell(cell10);
					
		
		PdfPCell cell14 = new PdfPCell();
		cell14.setPaddingTop(0.1f * CONVERT);
		cell14.setPaddingLeft(2.0f * CONVERT);
		pxk = new Paragraph(kh_Diachi, new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(12);
		cell14.addElement(pxk);
		cell14.setBorder(0);						
		table1.addCell(cell14);						
		
		
		//DOng 4
		PdfPCell cell17 = new PdfPCell();	
		cell17.setPaddingLeft(2.9f * CONVERT);
		cell17.setPaddingTop(10.0f);
		pxk = new Paragraph(khoxuat, new Font(bf, 11, Font.BOLD));
		pxk.setAlignment(Element.ALIGN_LEFT);
		pxk.setSpacingAfter(2);
		cell17.addElement(pxk);
		cell17.setBorder(0);						
		table1.addCell(cell17);	
		
		if(kh_MST.trim().length() <= 0)
		{
			kh_MST = "                              ";
		}

		while(kh_MST.length() < 14 )
			kh_MST = kh_MST + "         ";
		
		PdfPCell cell18 = new PdfPCell();		
		cell18.setPaddingLeft(5.3f * CONVERT); //4.8
		cell18.setPaddingTop(10.0f);
		pxk = new Paragraph( kh_MST + "                                                    " + hinhthucTT, new Font(bf, 10, Font.BOLD));
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
			sanpham.setSpacingBefore(35.8f); //50,13,5
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);

			float[] withsKM = { 7.0f, 15.0f, 69f, 20f, 33f, 19.0f, 14.0f, 30f, 27.0f, 18.0f, 27f, 27f }; //ô 10:22f
			sanpham.setWidths(withsKM);
			

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM ]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;	
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
							
					totalThueGTGT += thueGTGT;
					totalTienTruocVAT+= thanhtien;
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

				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") , rsSP.getString("TEN"),  rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)), DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)), DinhDangTRAPHACO(formatter1.format(sotientt)) };


				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					if (j <= 4 ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{						
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.0f);
					
					if(j <= 1)
						cells.setPaddingLeft(0.3f*CONVERT);
					if(j == 2)
						cells.setPaddingLeft(0.6f*CONVERT);
					if(j == 3)
						cells.setPaddingLeft(1.3f*CONVERT);
					if(j == 4)
						cells.setPaddingLeft(1.2f*CONVERT);
					if(j == 5)
						cells.setPaddingRight(0.5f*CONVERT);
					if(j == 6)
						cells.setPaddingRight(0.1f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(0.7f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(0.6f*CONVERT);
					if(j == 9)
						cells.setPaddingRight(1.2f*CONVERT);
					if(j == 10)
						cells.setPaddingRight(1.2f*CONVERT);
					if(j == 11)
						cells.setPaddingRight(0.5f*CONVERT);
															
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			stt= stt-1;
			
			query = sqlIN_CHIETKHAU;
			
			 System.out.println("---INIT TICH LUY  : " + query);
			 double tienVAT = 0;
		     double tienAVAT = 0;
		     double tienBVAT = 0;
		     
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";

				
				if(SoNgay(ngayxuatHD)&&SoNgayCKQ(ngayxuatHD)){
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + Math.round(rs.getDouble("chietkhau"));
					
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
				}
				else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK += rs.getDouble("chietkhau")  + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK += rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				}
				
				String [] arr5 ;
				if(SoNgay(ngayxuatHD)){
					arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ," "," ","1"," "," ", DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
						DinhDangTRAPHACO(formatter1.format(tienVAT)) , DinhDangTRAPHACO(formatter1.format(tienAVAT))};
				}
				else {
					arr5 = new String[] {Integer.toString(stt+1),maCK ,diengiaiCK ," "," ","1"," "," ", DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
							DinhDangTRAPHACO(formatter1.format(tienVAT)) , DinhDangTRAPHACO(formatter1.format(tienAVAT))};
				}
				
				for (int j = 0; j < arr5.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
					if (j <= 4 ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{						
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setBorder(0);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.0f);
					
					if(j <= 1)
						cells.setPaddingLeft(0.3f*CONVERT);
					if(j == 2)
						cells.setPaddingLeft(0.6f*CONVERT);
					if(j == 3)
						cells.setPaddingLeft(1.3f*CONVERT);
					if(j == 4)
						cells.setPaddingLeft(1.2f*CONVERT);
					if(j == 5)
						cells.setPaddingRight(0.5f*CONVERT);
					if(j == 6)
						cells.setPaddingRight(0.1f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(0.7f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(0.6f*CONVERT);
					if(j == 9)
						cells.setPaddingRight(1.2f*CONVERT);
					if(j == 10)
						cells.setPaddingRight(1.2f*CONVERT);
					if(j == 11)
						cells.setPaddingRight(0.5f*CONVERT);
					

					sanpham.addCell(cells);
				}
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
	}
   }
			
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
			
			double truocVAT = 0;
			String sauVat = "";
			String tienVat = "";
			
			if(SoNgay(ngayxuatHD)){
				truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
				tienVat = formatter1.format(totalThueGTGT - totalVatCK);
				sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
			}
			else{
				truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				tienVat = pxkBean.getTongVAT();
				sauVat = pxkBean.getTongtienAVAT();
			}	

		String[] arr = new String[] {" "," ", " "," "," "," "," "," ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };
		for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j <= 5 ){
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else{						
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				
				if(j <= 1)
					cells.setPaddingLeft(0.3f*CONVERT);
				if(j == 2)
					cells.setPaddingLeft(0.6f*CONVERT);
				if(j == 3)
					cells.setPaddingLeft(1.3f*CONVERT);
				if(j == 4)
					cells.setPaddingLeft(1.2f*CONVERT);
				if(j == 5)
					cells.setPaddingRight(0.5f*CONVERT);
				if(j == 6)
					cells.setPaddingRight(0.1f*CONVERT);
				if(j == 7)
					cells.setPaddingRight(0.7f*CONVERT);
				if(j == 8)
					cells.setPaddingRight(0.6f*CONVERT);
				if(j == 9)
					cells.setPaddingRight(1.2f*CONVERT);
				if(j == 10)
					cells.setPaddingRight(1.2f*CONVERT);
				if(j == 11)
					cells.setPaddingRight(0.5f*CONVERT);
				
				cells.setPaddingTop(-9.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
			
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
			String tien = doctien.docTien(Long.parseLong(sauVat.replaceAll(",", "")));
		  //Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    
			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 11, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(12);
				} 
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setPaddingLeft(0.8f * CONVERT);
				cells.setPaddingTop(0.2f);
				cells.setBorder(0);
				cells.setFixedHeight(0.8f*CONVERT);
				sanpham.addCell(cells);
			}															
			document.add(sanpham);
		
		} 
		catch (Exception e)
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

	private void CreatePxk_DANANG(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU)
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
			String ngayxuatHD= "";
			String chucuahieu = "";
/*			sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";*/				  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
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
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho,(select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"              (select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd ,  "+
	   		" 			  ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"               FROM HOADON" +
			"               WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
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
		cell10.setPaddingLeft(3.0f * CONVERT);	
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
		cell17.setPaddingLeft(2.7f * CONVERT);
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
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;	
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
							
					totalThueGTGT += thanhtien*rsSP.getDouble("thuevat")/100 ;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
				else {
					thanhtien = soLUONG * dongia - chietkhau;	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + (thanhtien*rsSP.getDouble("thuevat")/100);
							
					totalThueGTGT += thanhtien*rsSP.getDouble("thuevat")/100 ;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)), DinhDangTRAPHACO(formatter1.format(thanhtien)), DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)), DinhDangTRAPHACO(formatter1.format(sotientt)) };


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
					//cells.setBorderWidth(1);
					cells.setFixedHeight(0.6f * CONVERT);
					cells.setPaddingTop(2.5f);
				
					
					sanpham.addCell(cells);
				}
							
				
				stt++;
				
			}
			stt= stt-1;
			
			query = sqlIN_CHIETKHAU;
			
			 System.out.println("---INIT TICH LUY: " + query);
			 
			 double tienVAT = 0;
		     double tienAVAT = 0;
		     double tienBVAT = 0;
		     
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);
				
		         System.out.println(ngayxuatHD);
				if(SoNgay(ngayxuatHD)&&SoNgayCKQ(ngayxuatHD)){
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + Math.round(rs.getDouble("chietkhau"));
						
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
					System.out.println("A1");
				}else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
			 		System.out.println("A2");
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK += rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK += rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				}
				
				String [] arr5 = new String[] {Integer.toString(stt+1), diengiaiCK , " "," ", "1", " ", " ", DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
							DinhDangTRAPHACO(formatter1.format(tienVAT)) ,DinhDangTRAPHACO(formatter1.format(tienAVAT))};
				
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
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
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
			
			double truocVAT = 0;
			String sauVat = "";
			String tienVat = "";
			
			if(SoNgay(ngayxuatHD)){
				truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
				tienVat = formatter1.format(totalThueGTGT - totalVatCK);
				sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
			}
			else{
				truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				tienVat = pxkBean.getTongVAT();
				sauVat = pxkBean.getTongtienAVAT();
			}
			
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
		
			
			String[] arr = new String[] {" "," "," "," "," ", " ", " ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };	
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
				//cells.setBorderWidth(1);
				cells.setFixedHeight(0.6f*CONVERT);
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
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 12, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(11);
				} 
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPaddingLeft(0.1f * CONVERT);
				cells.setPaddingTop(-6.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}
															
			document.add(sanpham);

			//document.close();
		
			
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
	}
	
	private void CreatePxk_GIALAI(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU)
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
			String ngayxuatHD= "";
			String chucuahieu = "";
			/*sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/			  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
			
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
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho,(select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"              (select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd ,  "+
	   		" 			  ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"               FROM HOADON" +
			"               WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
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
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;	
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
							
					totalThueGTGT +=  thueGTGT;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
				else{
					thanhtien = soLUONG * dongia - chietkhau;	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + (thanhtien*rsSP.getDouble("thuevat")/100);
							
					totalThueGTGT += thanhtien*rsSP.getDouble("thuevat")/100 ;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") + '-' + rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))),DinhDangTRAPHACO(formatter1.format(thueGTGT)), DinhDangTRAPHACO(formatter1.format(sotientt)) };


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
			
			query = sqlIN_CHIETKHAU;
			
			 System.out.println("---INIT TICH LUY: " + query);
			 
			 double tienVAT = 0;
		     double tienAVAT = 0;
		     double tienBVAT = 0;
		     
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);

				
				if(SoNgay(ngayxuatHD) && SoNgayCKQ(ngayxuatHD)){			
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + Math.round(rs.getDouble("chietkhau"));
						
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
					
				}
				else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					 tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			         tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			         tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK +=  rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK +=  rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				}
				
				
				String [] arr5 = new String[] {Integer.toString(stt+1), diengiaiCK , " "," ", "1", " ", " ", DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
						DinhDangTRAPHACO(formatter1.format(tienVAT)) ,DinhDangTRAPHACO(formatter1.format(tienAVAT)) };
				
				
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
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
			double truocVAT = 0;
			String sauVat = "";
			String tienVat = "";
			
			if(SoNgay(ngayxuatHD)){
				truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
				tienVat = formatter1.format(totalThueGTGT - totalVatCK);
				sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
			}
			else{
				truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				tienVat = pxkBean.getTongVAT();
				sauVat = pxkBean.getTongtienAVAT();
			}	
			
			String[] arr = new String[] {" "," "," "," "," ", " ", " ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };
			
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

			//document.close();
		
			
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
	}
	
	private void CreatePxk_KHANHHOA(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU)
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
			String ngayxuatHD= "";
			String chucuahieu = "";
	/*		sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";				  
		   */
			
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
	   
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
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho,(select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"              (select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd ,  "+
	   		" 			  ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"               FROM HOADON" +
			"               WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
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
		pxk = new Paragraph(chucuahieu, new Font(bf, 11, Font.NORMAL));
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
		pxk = new Paragraph(Donvi, new Font(bf, 12, Font.BOLD));
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
		pxk = new Paragraph(kh_Diachi, new Font(bf, 11, Font.BOLD));
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
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;	
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
					
					totalThueGTGT +=  thueGTGT;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				
				}
				else{
					thanhtien = soLUONG * dongia - chietkhau;	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + (thanhtien*rsSP.getDouble("thuevat")/100);
					
					totalThueGTGT += thanhtien*rsSP.getDouble("thuevat")/100 ;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
						
				
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") + '-' + rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))), DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };


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
			stt= stt-1;
			
			query = sqlIN_CHIETKHAU;
			
			 System.out.println("---INIT TICH LUY: " + query);
			 
			 double tienVAT = 0;
		     double tienAVAT = 0;
		     double tienBVAT = 0;
		     
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);

				
				if(SoNgay(ngayxuatHD)&& SoNgayCKQ(ngayxuatHD)){		
					
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT =  rs.getDouble("chietkhau");
					tienAVAT = tienVAT + Math.round(rs.getDouble("chietkhau"));
					
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
					
				}
				else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					 tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			         tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			         tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK +=  rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK +=  rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				}
				
				String [] arr5 = new String[] {Integer.toString(stt+1), diengiaiCK , " "," ", "1", " ", " ", DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
						DinhDangTRAPHACO(formatter1.format(tienVAT)) ,DinhDangTRAPHACO(formatter1.format(tienAVAT))};
				
				
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
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
			
			double truocVAT = 0;
			String sauVat = "";
			String tienVat = "";
			
			if(SoNgay(ngayxuatHD)){
				truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
				tienVat = formatter1.format(totalThueGTGT - totalVatCK);
				sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
			}
			else{
				truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				tienVat = pxkBean.getTongVAT();
				sauVat = pxkBean.getTongtienAVAT();
			}	
			
		
			
			String[] arr = new String[] {" "," "," "," "," ", " ", " ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };	
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

			//document.close();
		
			
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
	}
	
	private void CreatePxk_QUANGNGAI(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU)
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
			String ngayxuatHD= "";
			String chucuahieu = "";
		/*	sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/			  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
	   
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
	   sql = " select top 1 (select diengiai from KHO where pk_seq= KHO_FK) as kho,(select hinhthuctt from HOADON where PK_SEQ= '"+ pxkBean.getId() +"' ) as HTTT," +
	   		"              (select ngayxuathd from HOADON where pk_seq = '"+ pxkBean.getId() +"') as ngayxuathd ,  "+
	   		" 			  ( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
			"                         else isnull(nguoimua,'') end " +
			"               FROM HOADON" +
			"               WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
	        " from DONHANG " +
	        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
	   
       System.out.println("Kho xuất "+sql);
	   ResultSet rsKho= db.get(sql);
	   if(rsKho.next())
	   {
		   hinhthucTT = rsKho.getString("HTTT");		   
		   ngayxuatHD = rsKho.getString("ngayxuathd");
		   chucuahieu = rsKho.getString("nguoimua");
		   rsKho.close();
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
			
			String query = sqlIN_SANPHAM;
			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			
			double thanhtien = 0;	
			double thueGTGT = 0;
			double sotientt = 0;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				if(SoNgay(ngayxuatHD)){
					thanhtien = Math.round(soLUONG * dongia - chietkhau);	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + thueGTGT;
					
					totalThueGTGT +=  thueGTGT;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
				else{
					thanhtien = soLUONG * dongia - chietkhau;	
					thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
					sotientt = thanhtien + (thanhtien*rsSP.getDouble("thuevat")/100);
					
					totalThueGTGT +=  thanhtien*rsSP.getDouble("thuevat")/100 ;
					totalTienTruocVAT+= thanhtien;
					totalSotienTT += sotientt;
				}
				
				
				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") + '-' + rsSP.getString("TEN"), rsSP.getString("solo"), 
						chuoi, rsSP.getString("DONVI"),
						DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),DinhDangTRAPHACO(formatter1.format(rsSP.getDouble("thuevat"))),DinhDangTRAPHACO(formatter1.format(thueGTGT)),DinhDangTRAPHACO(formatter1.format(sotientt)) };


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
			
			query = sqlIN_CHIETKHAU;
			
			 System.out.println("---INIT TICH LUY: " + query);
			 double tienVAT = 0;
		     double tienAVAT = 0;
		     double tienBVAT = 0;
		     
			 ResultSet rs = db.get(query);
			 if(rs != null)
			 {												
				 try 
				 {
					
		        while(rs.next())
			    {
		        	String maCK = rs.getString("DienGiai");
		        	String diengiaiCK ="";
		        	// LAY TEN CHIET KHAU
		        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
		        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
		        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
		        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
		         System.out.println("Ten dien giai: "+diengiaiCK);

		        			
				if(SoNgay(ngayxuatHD)&& SoNgayCKQ(ngayxuatHD)){		
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + Math.round(rs.getDouble("chietkhau"));
					
					
					totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
					totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
					totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
					
				}
				else if(SoNgay(ngayxuatHD)&&SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
					tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
			        tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
			         
			        totalTienCK += tienAVAT  ;
			 		totalTienCK_ChuaVat += tienBVAT;
			 		totalVatCK += tienVAT;
				}
				else{
					tienVAT = Math.round(rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100);
					tienBVAT = rs.getDouble("chietkhau");
					tienAVAT = tienVAT + rs.getDouble("chietkhau");
					
					totalTienCK +=  rs.getDouble("chietkhau") + (rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100)  ;
					totalTienCK_ChuaVat += rs.getDouble("chietkhau");
					totalVatCK +=  rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
				}
				
				
				String [] arr5 ;
				if(SoNgay(ngayxuatHD)){
					arr5 = new String[] {Integer.toString(stt+1), diengiaiCK , " "," ", "1", " ", " ", DinhDangTRAPHACO(formatter1.format(rs.getDouble("chietkhau"))),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
						DinhDangTRAPHACO(formatter1.format(tienVAT)) ,DinhDangTRAPHACO(formatter1.format((tienVAT + Math.round(rs.getDouble("chietkhau"))) ))};
				}
				else{
					arr5 = new String[] {Integer.toString(stt+1), diengiaiCK , " "," ", "1", " ", " ", DinhDangTRAPHACO(formatter1.format(rs.getDouble("chietkhau"))),DinhDangTRAPHACO(formatter1.format(rs.getDouble("thuevat"))),
							DinhDangTRAPHACO(formatter1.format(tienVAT)) ,DinhDangTRAPHACO(formatter1.format((tienVAT + rs.getDouble("chietkhau")) ))};
				}
				
				
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
				stt ++;
			}
		rs.close();
		
	} 
	catch (Exception e) 
	{
		System.out.println("__EXCEPTION: " + e.getMessage());
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
		//String[] arr = new String[] {"                             ", formatter1.format(totalTienTruocVAT - totalTienCK_ChuaVat),"",formatter1.format(totalThueGTGT - totalVatCK),formatter1.format(Math.round(totalSotienTT-totalTienCK)) };
			double truocVAT = 0;
			String sauVat = "";
			String tienVat = "";
			
			if(SoNgay(ngayxuatHD)){
				truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
				tienVat = formatter1.format(totalThueGTGT - totalVatCK);
				sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
			}
			else{
				truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				tienVat = pxkBean.getTongVAT();
				sauVat = pxkBean.getTongtienAVAT();
			}
						
			String[] arr = new String[] {" "," "," "," "," ", " ", " ", DinhDangTRAPHACO(formatter1.format(truocVAT)), " " , DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat) };	
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

			//document.close();
		
			
		} 
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	
	}
	
	private static void CapNhatHoaDon(String hoadonID, dbutils db) 
	{
		String query = "";
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			query = "delete HOADON_SP where hoadon_fk = '" + hoadonID + "'  ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				
				System.out.println("---1.LOI ROI..." + query);
				return;
			}
			
			query = "insert HOADON_SP( hoadon_fk, sanpham_fk, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme, vat )  " +
					"select '" + hoadonID + "', c.pk_seq, d.DONVI, SUM( f.soluong) as soluong, dhsp.GIAMUA AS dongia, SUM( f.soluong) * dhsp.GIAMUA, sum(dhsp.chietkhau) as chietkhau, '' as scheme, dhsp.thuevat   " +
					"from  DONHANG dh inner join DONHANG_SANPHAM dhsp on dhsp.donhang_fk=dh.PK_SEQ    " +
					"				 inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ                 " +
					"				 left join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk   " +
					"				 left join PHIEUXUATKHO_DONHANG e on dh.pk_seq = e.donhang_fk  " +
					"				 left join PHIEUXUATKHO_SANPHAM_CHITIET f on e.pxk_fk = f.pxk_fk and c.pk_seq = f.sanpham_fk  " +
					"where dh.PK_SEQ in ( select ddh_fk from HOADON_DDH where hoadon_fk =  '" + hoadonID + "' ) and dhsp.soluong > 0   	  " +
					"group by dh.PK_SEQ, c.pk_seq, d.DONVI, dhsp.GIAMUA, dhsp.thuevat ";
			
			System.out.println("---DANG CHAY HOA DON: " + hoadonID );
			if(!db.update(query) )
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				
				query = "insert HOADON_BILOI( hoadon_fk, cap ) values( " + hoadonID + ", 0 ) ";
				db.update(query);
				
				//System.out.println("---2.LOI ROI..." + query);
				return;
			}
			
			//CHEN HOA DON - SAN PHAM SAN PHAM CHI TIET
			query = "delete HOADON_SP_CHITIET where hoadon_fk = '" + hoadonID + "'  ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				
				System.out.println("---3.LOI ROI..." + query);
				return;
			}
			
			query = "insert HOADON_SP_CHITIET( hoadon_fk, MA, TEN, DONVI, DONGIA, SOLO, NGAYHETHAN, THUEVAT, SOLUONG, CHIETKHAU )  " +
					"select '" + hoadonID + "' as hoadon_fk, c.MA, c.TEN, d.DONVI, dhsp.GIAMUA AS dongia,   " +
					"		case f.solo when 'NA' then ' ' else f.solo end as solo,   " +
					"		case f.solo when 'NA' then ' ' else isnull(f.ngayhethan,'') end as ngayhethan, dhsp.thuevat,     " +
					"	SUM( f.soluong) as soluong, '0' as chietkhau   " +
					"from  DONHANG dh inner join DONHANG_SANPHAM dhsp on dhsp.donhang_fk=dh.PK_SEQ    " +
					"				 inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ                 " +
					"				 left join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk   " +
					"				 left join PHIEUXUATKHO_DONHANG e on dh.pk_seq = e.donhang_fk  " +
					"				 left join PHIEUXUATKHO_SANPHAM_CHITIET f on e.pxk_fk = f.pxk_fk and c.pk_seq = f.sanpham_fk  " +
					"where dh.PK_SEQ in (select ddh_fk from HOADON_DDH where hoadon_fk =  '" + hoadonID + "' )  and  dhsp.soluong > 0   	  " +
					"group by  c.MA, c.TEN, d.DONVI, dhsp.GIAMUA, dhsp.thuevat, f.solo, f.ngayhethan  ";
			//System.out.println("---CHAY HOA DON CHI TIET: " + hoadonID );
			if(!db.update(query) )
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				
				query = "insert HOADON_BILOI( hoadon_fk, cap ) values( " + hoadonID + ", 1 ) ";
				db.update(query);
				
				System.out.println("---3.LOI ROI..." + query);
				return;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
			} 
			catch (Exception e1) 
			{
				
				query = "insert HOADON_BILOI( hoadon_fk, cap ) values( " + hoadonID + ", 2 ) ";
				db.update(query);
				
				//System.out.println("---2.LOI ROI..." + query);
				return;
			}
		}

	}
	

	private void CreatePxk_TAYNINH(Document document,ServletOutputStream outstream, IHoadontaichinh pxkBean, String sqlIN_SANPHAM, String sqlIN_CHIETKHAU) 
	{
		try
		{
			dbutils db = new dbutils();
			
			 //LAY THONG TIN KHACHHANG 		  
			String sql ="";
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String ngayxuatHD= "";
			String chucuahieu = "";
			String sotaikhoan= "";
			
			/*sql = " select  TEN ,DIACHI, isnull(MASOTHUE ,' ' ) as MASOTHUE "+
		        " from KHACHHANG " +
		        " where PK_SEQ = (select khachhang_fk from HOADON where pk_seq = '"+ pxkBean.getId() +"') ";	*/			  
		   
			//LẤY THEO DỮ LIỆU MỚI
			sql = " SELECT TENKHACHHANG TEN, DIACHI, ISNULL(MASOTHUE,'') MASOTHUE  " +
			  	  " FROM   HOADON WHERE PK_SEQ ='"+pxkBean.getId()+"'";
			
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
		   		" 				( SELECT case when  nguoimua is null then (select isnull(chucuahieu,'') from khachhang where pk_seq= khachhang_fk ) " +
				"                         else isnull(nguoimua,'') end " +
				"           	  FROM HOADON" +
				"           	  WHERE PK_SEQ= '"+ pxkBean.getId() +"' ) AS nguoimua "  +
		        " from DONHANG " +
		        " where PK_SEQ in (select DDH_FK from HOADON_DDH where HOADON_FK = '"+ pxkBean.getId() +"') ";				  
		   
	       System.out.println("Kho xuất "+sql);
		   ResultSet rsKho= db.get(sql);
		   if(rsKho.next())
		   {
			   hinhthucTT = rsKho.getString("HTTT");		   
			   ngayxuatHD = rsKho.getString("ngayxuathd");
			   chucuahieu = rsKho.getString("nguoimua");
			   rsKho.close();
		   }
		   
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 10, Font.NORMAL);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			
			document.setMargins(0.0f*CONVERT, 1.0f*CONVERT, 2.6f*CONVERT, 2.0f*CONVERT); // L,R, T, B
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			// Dòng 1: ngày tháng
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			//cell.setPaddingTop(2.6f * CONVERT);
			cell.setPaddingLeft(1.0f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayHD = ngayxuatHD.split("-");
			Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                             " + ngayHD[0].substring(2) , new Font(bf, 8, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			
			// DONG TRONG
			PdfPCell cell0 = new PdfPCell();
			cell0.setBorder(0);
			
			pxk = new Paragraph( " ", font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell0.setFixedHeight(3.7f * CONVERT);
			cell0.addElement(pxk);	
			
			tableheader.addCell(cell0);
			
			// Dòng 2: người mua hàng
			
			PdfPCell cell_mh = new PdfPCell();
			cell_mh.setBorder(0);
			cell_mh.setPaddingLeft(3.5f * CONVERT);
			cell_mh.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph( "                   " + chucuahieu , font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_mh.setFixedHeight(0.7f * CONVERT);
			cell_mh.addElement(pxk);
			
			tableheader.addCell(cell_mh);
			
			// Dòng 3: tên đơn vị
			
			PdfPCell cell_dv = new PdfPCell();
			cell_dv.setBorder(0);
			cell_dv.setPaddingLeft(3.0f * CONVERT);
			cell_dv.setFixedHeight(0.8f * CONVERT);
			cell_dv.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph(Donvi , font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_dv.addElement(pxk);
			
			tableheader.addCell(cell_dv);
			
			// Dòng 4 : Mã số thuế
			
			PdfPCell cell_mst = new PdfPCell();
			cell_mst.setBorder(0);
			cell_mst.setPaddingLeft(3.0f * CONVERT);
			cell_mst.setFixedHeight(0.7f * CONVERT);
			cell_mst.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph(kh_MST, font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_mst.addElement(pxk);
			pxk.setSpacingAfter(2);
							
			tableheader.addCell(cell_mst);
			
			// Dòng 5: Địa chỉ
			
			PdfPCell cell_dc = new PdfPCell();
			cell_dc.setBorder(0);
			cell_dc.setPaddingLeft(3.0f * CONVERT);
			cell_dc.setFixedHeight(0.7f * CONVERT);
			cell_dc.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph(kh_Diachi, font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_dc.addElement(pxk);
			pxk.setSpacingAfter(2);
							
			tableheader.addCell(cell_dc);
			
			// Dòng 6 : HTTT + Số tài khoản
			
			PdfPCell cell_httt = new PdfPCell();
			cell_httt.setBorder(0);
			cell_httt.setPaddingLeft(4.5f * CONVERT);
			cell_httt.setFixedHeight(0.8f * CONVERT);
			cell_httt.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pxk = new Paragraph(hinhthucTT  +  "                                       " + sotaikhoan, font);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_httt.addElement(pxk);
			pxk.setSpacingAfter(2);
							
			tableheader.addCell(cell_httt);
			
			document.add(tableheader); 
			
			// BẢNG SẢN PHẨM
			
			// Table Content

			String[] th = new String[]{ " ", " ", " ", "  ", " "," " ," "," "};
			
			PdfPTable sanpham = new PdfPTable(th.length);
			sanpham.setSpacingBefore(56.0f); 
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 7.0f, 70.0f, 21f, 15f, 20.0f, 20.0f, 30f, 7.0f};
			sanpham.setWidths(withsKM);
				

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double totalTienCK=0;
			double totalTienCK_ChuaVat=0;
			double totalVatCK=0;
			
			//KHAC CHI NHANH KHAC XIU
			String query = "select MA, TEN, DONVI, DONGIA, SOLO, NGAYHETHAN, THUEVAT, SOLUONG, CHIETKHAU,  " +
							"case when MA ='3KOOL' then 'JAPAN'  when MA = '5TRIB' then 'AUSTRALIA' else 'TRAPHACO' end as  NoiSX   " +
						   "from HOADON_SP_CHITIET where hoadon_fk = '" + pxkBean.getId() + "'";
			System.out.println("[ERP_DONDATHANG_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			double thuesuatGTGT = 0;
			int stt = 1;
			
			while(rsSP.next())
			{
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				
				double thanhtien = Math.round(soLUONG * dongia );	
				double thueGTGT = Math.round(thanhtien*rsSP.getDouble("thuevat")/100);
				double sotientt = thanhtien + thueGTGT;
				thuesuatGTGT = rsSP.getDouble("thuevat");
				String noiSX = rsSP.getString("NoiSX");
						
				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				totalSotienTT +=sotientt;
				//rsSP.getString("DONVI")
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA") + " - " + rsSP.getString("TEN"), noiSX,  
						                      DinhDangTRAPHACO(formatter1.format(soLUONG)), DinhDangTRAPHACO(formatter.format(dongia)),DinhDangTRAPHACO(formatter1.format(thanhtien)),
						                      rsSP.getString("NGAYHETHAN"), rsSP.getString("Solo") };


				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					// cho stt ra ngoai
					//if(j==0)  cells.setPaddingLeft(-2.0f * CONVERT);
					if ( j<=1){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						if(j <=3 )
						{
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
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
			
			query = sqlIN_CHIETKHAU;
				 System.out.println("---INIT TICH LUY 2: " + query);
				 double tienVAT = 0;
			     double tienAVAT = 0;
			     double tienBVAT = 0;
				 ResultSet rs = db.get(query);
				 
				 if(rs != null)
				 {												
					try 
					{				
				        while(rs.next())
					    {
				        	String maCK = rs.getString("DienGiai");
				        	String diengiaiCK ="";
				        	// LAY TEN CHIET KHAU
				        	if(maCK.equals("CN5")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
				        	if(maCK.equals("CN10")) diengiaiCK ="Giảm trừ (Chiết khấu bán hàng ngay)";
				        	if(maCK.equals("CT5")) diengiaiCK ="Giảm trừ (CK Tháng)";
				        	if(maCK.equals("CT10")) diengiaiCK ="Giảm trừ (CK Tháng)";
				        	if(maCK.equals("CQB5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
				        	if(maCK.equals("CQX5")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
				        	if(maCK.equals("CQB10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng BG-HH)";
				        	if(maCK.equals("CQX10")) diengiaiCK ="Giảm trừ (CK Quý nhóm hàng XANH)";
								
				        	
				        	
							if(SoNgay(ngayxuatHD)&&SoNgayCKQ(ngayxuatHD)){								
								tienVAT = rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
								tienBVAT = rs.getDouble("chietkhau");
								
								totalTienCK_ChuaVat += Math.round(rs.getDouble("chietkhau"));					
								totalVatCK +=  Math.round((Math.round(rs.getDouble("chietkhau"))* rs.getDouble("thuevat")/100));
								totalTienCK = totalTienCK_ChuaVat+totalVatCK ;
							}
							else if(SoNgay(ngayxuatHD) && SoNgayChietKhauQuy_CacTinh(ngayxuatHD)){
								 tienAVAT =  rs.getDouble("chietkhau") * ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
						         tienBVAT =  tienAVAT /  ( 1 + rs.getDouble("thuevat") / 100 ) ;			         
						         tienVAT =  tienBVAT * rs.getDouble("thuevat") / 100 ;
						         
						        totalTienCK += tienAVAT  ;
						 		totalTienCK_ChuaVat += tienBVAT;
						 		totalVatCK += tienVAT;
							}
							else{						
								tienVAT = rs.getDouble("chietkhau")* rs.getDouble("thuevat")/100;
								tienBVAT = rs.getDouble("chietkhau");
								
								totalTienCK += rs.getDouble("chietkhau") + tienVAT  ;
								totalTienCK_ChuaVat +=rs.getDouble("chietkhau");
								totalVatCK += tienVAT;
							}
							
							String [] arr5;
							if(SoNgay(ngayxuatHD)){
								arr5 = new String[] {Integer.toString(stt+1),maCK + " - "+ diengiaiCK ," ","1",DinhDangTRAPHACO(formatter1.format(tienBVAT)),DinhDangTRAPHACO(formatter1.format(tienBVAT)),"",""};
							}
							else{
								arr5 = new String[] {Integer.toString(stt+1),maCK + " - "+ diengiaiCK ," ","1",DinhDangTRAPHACO(formatter1.format(tienBVAT)), DinhDangTRAPHACO(formatter1.format(tienBVAT)),"",""};
							}
							
							for (int j = 0; j < arr5.length; j++)
							{
								cells = new PdfPCell(new Paragraph(arr5[j], new Font(bf, 10 , Font.BOLD)));
								//if(j==0)  cells.setPaddingLeft(-2.0f * CONVERT);
								
									if(j == 1)
									{
										cells.setHorizontalAlignment(Element.ALIGN_LEFT);
									}
									else
									{
										if(j==3 ) 
										{
											cells.setHorizontalAlignment(Element.ALIGN_CENTER);	
										}
										else if(j==0) cells.setHorizontalAlignment(Element.ALIGN_LEFT);	
										else cells.setHorizontalAlignment(Element.ALIGN_RIGHT);								
									}
									
								cells.setFixedHeight(0.6f*CONVERT);
								cells.setPaddingTop(2.5f);
								cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
								cells.setBorder(0);
								sanpham.addCell(cells);
							
						   }
						stt ++;
					  }
			        rs.close();
			
				  }catch (Exception e) 
				  {
					System.out.println("__EXCEPTION: " + e.getMessage());
				  }
			   }
				 
		   // DONG TRONG
			int kk=0;
			while(kk < 14-stt)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "};
	
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
			
			
			// Cộng tiền hàng
			
			double truocVAT = 0;
			String sauVat = "";
			String tienVat = "";
			
			if(SoNgay(ngayxuatHD)){ // dùng chung với 2 trường hợp
				truocVAT = totalTienTruocVAT - totalTienCK_ChuaVat;
				tienVat = formatter1.format(totalThueGTGT - totalVatCK);
				sauVat = formatter1.format(totalSotienTT - totalTienCK) ;
			}
			else{
				truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));
				tienVat = pxkBean.getTongVAT();
				sauVat = pxkBean.getTongtienAVAT();
			}	
			
			/*double truocVAT = Double.parseDouble(pxkBean.getTongtienBVAT().replaceAll(",", ""))	- Double.parseDouble(pxkBean.getTongCK().replaceAll(",", ""));*/
			
			//DÒNG TỔNG CỘNG TIỀN HÀNG
				String[] arr = new String[] {" ",""," ", "", "", DinhDangTRAPHACO(formatter1.format(truocVAT)),"" ," " };
				for (int j = 0; j < arr.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
					
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);					

					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingTop(0.5f * CONVERT);
					cells.setBorder(0);
					cells.setFixedHeight(1.2f*CONVERT);
					sanpham.addCell(cells);
				}
			
			//TIỀN CHIẾT KHẤU
				
				String[] arr6 =new String[] {" " };
				for (int j = 0; j < arr6.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr6[j], new Font(bf, 10, Font.BOLDITALIC)));
					
					if(j==0)
					{
						cells.setColspan(8);
					}
					cells.setHorizontalAlignment(Element.ALIGN_LEFT); 
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setBorder(0);
					cells.setFixedHeight(0.7f*CONVERT);
					sanpham.addCell(cells);
				}
				
				
				
				// Thuế suất + Tiền thuế GTGT
				String[] arr3 = new String[] {"",DinhDangTRAPHACO(formatter1.format(thuesuatGTGT)),"", " ", " ", DinhDangTRAPHACO(tienVat), DinhDangTRAPHACO(sauVat),"" };
				for (int j = 0; j < arr3.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr3[j], new Font(bf, 10, Font.BOLDITALIC)));

					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
					if(j==1)  
					{
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						//cells.setPaddingRight(1.0f * CONVERT);				
					}
					
					if(j==2 )
					{ 
					   cells.setPaddingLeft(0.1f * CONVERT);
					}
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingTop(2.8f);
					cells.setBorder(0);
					cells.setFixedHeight(0.7f*CONVERT);
					sanpham.addCell(cells);
				}
				
				/*// Tổng cộng tiền thanh toán
				String[] arr4 = new String[] {" "," "," ", " ", " ", DinhDangTRAPHACO(sauVat)," " ," " };
				for (int j = 0; j < arr4.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr4[j], new Font(bf, 10, Font.BOLDITALIC)));
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);

					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPaddingTop(2.8f);
					cells.setBorder(0);
					cells.setFixedHeight(0.7f*CONVERT);
					sanpham.addCell(cells);
				}*/
				
				// Tien bang chu
				doctienrachu doctien = new doctienrachu();
			    //String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
				String tien = doctien.docTien(Long.parseLong(sauVat.replaceAll(",", "")));
			  //Viết hoa ký tự đầu tiên
			    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			    
				String[] arr1 = new String[] {"                                         " +  TienIN};
				for (int j = 0; j < arr1.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
					
					if(j==0)
					{
						cells.setColspan(8);
					}
					cells.setHorizontalAlignment(Element.ALIGN_LEFT); 
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setBorder(0);
					cells.setFixedHeight(0.7f*CONVERT);
					sanpham.addCell(cells);
				}
				
																				
				document.add(sanpham);
			
			//document.close();
			
			
		   
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
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

	private boolean SoNgay (String ngayxuathd){
		boolean kt = false;
		int songay = 0;
		//NẾU NGÀY XUẤT HÓA ĐƠN > '2014-12-08' THÌ ĐƯA VỀ ĐỊNH DẠNG MỚI
		dbutils db = new dbutils();
		String layngay = "select datediff(DD,'2014-12-08', '"+ngayxuathd+"') songay";
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
	
	private boolean SoNgayCKQ (String ngayxuathd){
		boolean kt = false;
		int songay = 0;
		//NẾU NGÀY XUẤT HÓA ĐƠN > '2014-12-08' THÌ ĐƯA VỀ ĐỊNH DẠNG MỚI
		dbutils db = new dbutils();
		String layngay = "select datediff(DD,'2015-01-26','"+ngayxuathd+"') songay";// sau - trước
		
		System.out.println(layngay);
		
		ResultSet checkngay = db.get(layngay);
		
		try{
			if(checkngay.next())
			{
				songay = checkngay.getInt("songay");
				checkngay.close();
			}
			if(songay > 0 ) kt = true;
		}
		catch (Exception e){
			e.printStackTrace();
			kt = false;
		}
		
		return kt;		
	}
	
	
	private boolean SoNgayChietKhauQuy(String ngayxuathd){ //HCM
		boolean kt = false;
		int songay = 0;
		//NẾU NGÀY XUẤT HÓA ĐƠN > '2014-12-08' THÌ ĐƯA VỀ ĐỊNH DẠNG MỚI
		dbutils db = new dbutils();
		String layngay = "select datediff(DD,'2015-01-16', '"+ngayxuathd+"') songay";
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
	
	private boolean SoNgayChietKhauQuy_CacTinh(String ngayxuathd){ // CÁC TỈNH
		boolean kt = false;
		int songay = 0;
		//NẾU NGÀY XUẤT HÓA ĐƠN < '2015-01-26' THÌ ĐƯA VỀ ĐỊNH DẠNG MỚI
		dbutils db = new dbutils();
		String layngay = "select datediff(DD, '"+ngayxuathd+"','2015-01-26') songay";
		
		System.out.println(layngay);
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
}
