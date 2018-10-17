package geso.traphaco.distributor.servlets.bienbanbutru;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.inchietkhautruckthang.IInKhauTruCKThang;
import geso.traphaco.distributor.beans.inchietkhautruckthang.imp.InKhauTruCKThang;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/BienBanBuTruCongNoPdfSvl")
public class BienBanBuTruCongNoPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public BienBanBuTruCongNoPdfSvl()
	{
		super();
		
	}
	
	float CONVERT = 28.346457f;  // =1cm
	@Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IInKhauTruCKThang obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");	    
		HttpSession session = request.getSession();	    
		Utility util = new Utility();	    	   
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);   
		String userTen = (String) session.getAttribute("userTen");
		String nppId= util.getIdNhapp(userId);
		
		obj = new InKhauTruCKThang(); 
		obj.setUserId(userId);	
		
		String view = request.getParameter("view");
		if(view == null)
			view = "NPP";
		
		String type = request.getParameter("type");
		if(type == null)
			type = "";
		System.out.println("view " + view + "  -- TYPE: " + type);
		
		if(!type.equals("BANGKE_BBBT"))
		{
			if(!type.equals("BTCN"))
			{
				if(view.equals("TT"))
				{
					nppId = util.antiSQLInspection(request.getParameter("nppId"));
					if (nppId == null)
						nppId = "";
					obj.setNppId(nppId);
				}
				else
				{
					nppId=util.getIdNhapp(userId);
					obj.setNppId(nppId);
				}
				
				obj.createRs();
				session.setAttribute("obj", obj);
				
				
				if(!view.equals("TT"))
				{
					String	nextJSP = "/TraphacoHYERP/pages/Distributor/InChietKhauTruCKThang.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					String	nextJSP = "/TraphacoHYERP/pages/Center/InChietKhauTruCKThang.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else
			{
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", " inline; filename=BienBanBuTru.pdf");
				
				try
				{
					Document document = new Document();
					ServletOutputStream outstream = response.getOutputStream();
					
					String id = request.getParameter("id");
					this.CreateBienBanBuTru_HoaDon(document, outstream, id, nppId);
					document.close();
				}
				catch (Exception e) { }
			}
		}
		else
		{
			nppId = util.getIdNhapp(userId);
			obj.setNppId(nppId);
			obj.setUserId(userId);
			
			obj.createRs();
			session.setAttribute("obj", obj);
			
			String	nextJSP = "/TraphacoHYERP/pages/Distributor/BangKeBuTruCK.jsp";
			response.sendRedirect(nextJSP);	
		}
	}
	
	
	@Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		IInKhauTruCKThang obj = new InKhauTruCKThang(); 
		
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		obj.setUserId(userId);
		String nppId= util.getIdNhapp(userId);
		
		String userTen = (String)session.getAttribute("userTen");     
		System.out.println("userTen : "+ userTen );
		OutputStream out = response.getOutputStream();
		
		String action = request.getParameter("action");
		if (action == null)
			action = "";
		
		String tungay = request.getParameter("tuNgay");
		if(tungay == null)
			tungay = ""; 
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denNgay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setKhId(khId);
		
		System.out.println("KH"+ khId);
		
		String ddkdId = request.getParameter("ddkdId");
		if(ddkdId == null)
			ddkdId = "";
		obj.setDdkdId(ddkdId);
		
		String Vung = util.antiSQLInspection(request.getParameter("vungId"));
		if(Vung==null)
			Vung="";
		obj.setvungId(Vung);
		
		String khuvuc = util.antiSQLInspection(request.getParameter("khuvucId"));
		if(khuvuc == null)
			khuvuc = "";
		obj.setkhuvucId(khuvuc);
		
		String nvgnId = request.getParameter("nvgnId");
		if(nvgnId == null)
			nvgnId = "";
		obj.setNvgnId(nvgnId);
		
		String ttId = util.antiSQLInspection(request.getParameter("ttId"));
		System.out.println("ttId "+ Vung);
		if(ttId == null)
			ttId = "";
		obj.setTtId(ttId);
		
		String[] khIds = request.getParameterValues("khId");
		String _scheme_kh = "";
		if (khIds != null)
		{
			for(int i = 0; i < khIds.length; i++)
			{
				_scheme_kh += khIds[i] + ",";
			}
			
			if(_scheme_kh.trim().length() > 0)
			{
				_scheme_kh = _scheme_kh.substring(0, _scheme_kh.length() - 1);
			}
		}
		
		String xemtheo = request.getParameter("xemtheo");
		if(xemtheo == null)
			xemtheo = "";
		obj.setXemtheo(xemtheo);
		
		String view = request.getParameter("view");
		if(view == null)
			view = "NPP";
		if(view.equals("TT"))
		{
			nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			obj.setNppId(nppId);
		}
		else
		{
			nppId=util.getIdNhapp(userId);
			obj.setNppId(nppId);
		}
		
		if(action.equals("excel_butruCK"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BangKeBienBanBuTruCongNo.xlsm");
				
				FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCBangKeCanTruCK.xls");
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				
				CreateStaticHeader(workbook, obj);
				
				if(xemtheo.equals("0"))
					CreateStaticData(workbook, obj);
				else
					CreateStaticData_TheoBangKe(workbook, obj);
				
				workbook.save(out);
				fstream.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				obj.setMsg("Không thể tao báo cáo.");
			}
		}
		else
		{
			if (action.equals("excel") )
			{			
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", " inline; filename=BienBanBuTru.pdf");
				
				try
				{
					Document document = new Document();
					ServletOutputStream outstream = response.getOutputStream();	
					
					this.CreateBienBanBuTru(document, outstream, obj, nppId, khIds, _scheme_kh);
					document.close();
				}
				catch (Exception e) { e.printStackTrace(); }
			}
			else
			{
				obj.createRs();
				session.setAttribute("userId", userId);
				session.setAttribute("obj", obj);
				if(!view.equals("TT"))
				{
					String	nextJSP = "/TraphacoHYERP/pages/Distributor/InChietKhauTruCKThang.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					String	nextJSP = "/TraphacoHYERP/pages/Center/BCBangCanDoiPhatSinhCongNo.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
	}
	
	
	private void CreateBienBanBuTru(Document document, ServletOutputStream outstream, IInKhauTruCKThang obj, String nppId, String[] khIds, String dsKh ) 
	{
		System.out.println("----IN TRONG NAY...");
		int thangTRUOC = Integer.parseInt(obj.getTungay());
		int namTRUOC = Integer.parseInt(obj.getDenngay());
		if(thangTRUOC == 1)
		{
			thangTRUOC = 12;
			namTRUOC = namTRUOC - 1;
		}
		else
		{
			thangTRUOC = thangTRUOC - 1;
		}
		
		int songayTRONGTHANG = Songaytrongthang(thangTRUOC, namTRUOC);
		String dauthang = Integer.toString(namTRUOC) + "-" + ( thangTRUOC < 10 ? "0" + Integer.toString(thangTRUOC) : Integer.toString(thangTRUOC) ) + "-01" ; 
		String cuoithang = Integer.toString(namTRUOC) + "-" + ( thangTRUOC < 10 ? "0" + Integer.toString(thangTRUOC) : Integer.toString(thangTRUOC) ) + "-" + ( songayTRONGTHANG < 10 ? "0" + Integer.toString(songayTRONGTHANG) : Integer.toString(songayTRONGTHANG) ) ; 
		
		try
		{				
			dbutils db = new dbutils();
			String query = "";
			
			if(dsKh.trim().length() <= 0)
			{
				if(obj.getDdkdId().trim().length() > 0)
				{
					query =  " SELECT c.KHACHHANG_FK "+
							" FROM   DAIDIENKINHDOANH a INNER JOIN TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ "+
							"                        INNER JOIN KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ "+
							" WHERE  a.PK_SEQ = '" + obj.getDdkdId() + "' " +
							"		and c.khachhang_fk in ( select khachhang_fk from DUYETBANDUNGGIA_KHACHHANG where duyet_fk in ( select pk_seq from DUYETBANDUNGGIA where trangthai = '1' and loaict = '0' and nam = '" + namTRUOC + "' and thang = '" + thangTRUOC + "' and NPP_FK = '" + obj.getNppId() + "' ) ) ";
					System.out.println("--LAY KHACH HANG: " + query);
					ResultSet rs = db.get(query);
					while(rs.next())
					{
						dsKh += rs.getString("KHACHHANG_FK") + "__";
					}
					rs.close();
					
					if(dsKh.trim().length() > 0)
					{
						dsKh = dsKh.substring(0, dsKh.length() - 2);
						khIds = dsKh.split("__");
					}
				}
			}
			
			if(khIds != null)
			{
				NumberFormat formatter = new DecimalFormat("#,###,###");
				PdfWriter writer = PdfWriter.getInstance(document, outstream);
				document.setMargins(60.0f, 35.0f, 0.7f*CONVERT, 0.7f*CONVERT);
				document.open();
				
				//1.LỌC THEO TỪNG KHÁCH HÀNG
				// LAY THONG TIN CHI NHANH/DOITAC
				for(int i = 0; i < khIds.length; i++)
				{
					//DAU KY
					if( thangTRUOC == 6 && namTRUOC == 2014 )
					{
						query = "select count(*) as soDONG, kh.xuatkhau, isnull(kh.thanhtoan, 0) as thanhtoan, isnull(PT_CHIETKHAU, 0) as PT_CHIETKHAU, ( select loaiNPP from NHAPHANPHOI where pk_seq = '" + obj.getNppId() + "' ) as loaiNPP,  " +
								//"	ISNULL( ( select tongdoanhso from TICHLUYTHANG_DAUKY where khachhang_fk = '" + khIds[i] + "' ) ,0 ) as tongDSThangtruoc,  " +
								"	ISNULL( ( select tongthucthu from TICHLUYTHANG_DAUKY where khachhang_fk = '" + khIds[i] + "' ) ,0 ) as tongTHUVEThangtruoc, " +
								" 	ISNULL( ( select count(*) from DONHANG_SANPHAM where donhang_fk = '" + khIds[i] + "' and thueVAT = '5'  ), 0 ) as exits5Pt  " +
								"from KHACHHANG kh " +
								"where kh.pk_seq = '" + khIds[i] + "' and kh.KhongKyHopDong = '0' and kh.pk_seq  in ( select khachhang_fk from DUYETBANDUNGGIA_KHACHHANG where duyet_fk in ( select pk_seq from DUYETBANDUNGGIA where trangthai = '1' and loaict = '0' and nam = '" + namTRUOC + "' and thang = '" + thangTRUOC + "' and npp_fk = '" + obj.getNppId() + "' ) ) " +
								"group by kh.xuatkhau, kh.thanhtoan, PT_CHIETKHAU  ";
					}
					else
					{
						//PHAI LAY LOAI KHACH HANG TRONG THANG TRUOC
						query = "select count(*) as soDONG, isnull( PT_CHIETKHAU, 0) as PT_CHIETKHAU, isnull(kh.thanhtoan, 0) as thanhtoan, ( select loaiNPP from NHAPHANPHOI where pk_seq = '" + obj.getNppId() + "' ) as loaiNPP,  " +
								/*"	ISNULL( ( select sum( soluong * dongia )  as toTAL  " +
						"			  from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk " +
						"			  where a.ngayxuatHD >= '" + dauthang + "' and a.ngayxuatHD <= '" + cuoithang + "' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0' and a.khachhang_fk = '" + khIds[i] + "' ) ,0 ) as tongDSThangtruoc,  " +*/
						"	ISNULL( (	select sum(tongtien)  " +
						"			from " +
						"			( " +
						/*"				select sum(tongtienAVAT) as tongtien " +
						"				from HOADON a   " +
						"				where a.ngayxuatHD >= '" + dauthang + "' and a.ngayxuatHD <= '" + cuoithang + "' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0' and a.khachhang_fk = '" + khIds[i] + "' " +*/
						"				select sum(tongtienAVAT) - case when '"+dauthang+"'>= '2015-01-01' then 0 else  sum(isnull(b.sotiencantru, 0)) end  as tongtien " +
						"				from HOADON a left join CANTRUCONGNO_HOADON b on a.pk_seq = b.hoadon_fk  " +
						"				where a.ngayxuatHD >= '" + dauthang + "' and a.ngayxuatHD <= '" + cuoithang + "' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0' and a.khachhang_fk = '" + khIds[i] + "' " +
						"			) " +
						"			total ) , 0 ) as tongTHUVEThangtruoc ,  " +
						"	ISNULL( ( select top(1) loaikhachhang from DONHANG where khachhang_fk = '" + khIds[i] + "' and trangthai != 2 and ngaynhap >= '" + dauthang + "' and ngaynhap <= '" + cuoithang + "' ), 0 ) as xuatkhau, " +
						"	ISNULL( ( select sum( tiencothue + bosung ) from DUYETTRAKHUYENMAI_DUNO where tichluyQUY = '0' and khachhang_fk = '" + khIds[i] + "'   ), 0 ) " +
						"	-  ISNULL( ( select sum( thanhtoan ) from DUYETTRAKHUYENMAI_DUNO_DONHANG_DATRA where tichluyQUY = '0' and khachhang_fk = '" + khIds[i] + "' and donhang_fk not in ( select pk_seq from DONHANG where khachhang_fk = '" + khIds[i] + "' and ngaynhap >= '" + dauthang + "' )  ), 0 ) as duno_dauky  " +
						"from KHACHHANG kh "+
						"where kh.pk_seq = '" + khIds[i] + "' and kh.KhongKyHopDong = '0' and kh.pk_seq in ( select khachhang_fk from DUYETBANDUNGGIA_KHACHHANG where duyet_fk in ( select pk_seq from DUYETBANDUNGGIA where trangthai = '1' and loaict = '0' and nam = '" + namTRUOC + "' and thang = '" + thangTRUOC + "' and NPP_FK = '" + obj.getNppId() + "' ) ) " +
						"group by kh.xuatkhau, kh.thanhtoan, PT_CHIETKHAU ";
					}
					
					System.out.println("__1.Lay Tich THANG cua KH [" + khIds[i] + "]: " + query);
					ResultSet rsCHECK = db.get(query);
					
					double tongDSThangtruoc = 0;
					double tongTHUVEThangtruoc = 0;
					double pt_chietkhau = 0;
					double duno_dauky = 0;
					String cotLAY = "chietkhau";
					{
						if(rsCHECK.next())
						{
							if( rsCHECK.getInt("soDONG") > 0 )
							{
								tongTHUVEThangtruoc = rsCHECK.getDouble("tongTHUVEThangtruoc");
								if( rsCHECK.getString("xuatkhau").equals("0") || rsCHECK.getString("xuatkhau").equals("3") )  //Loại bán lẻ và bán lẻ, buôn
									cotLAY = "chietkhau_khle";
								else
									cotLAY = "chietkhau";
								
								query=
										"	select a.thuongtl_fk,c.loainpp,sum(a.chietkhau) as chietkhau,sum(a.chietkhau_khle) as chietkhau_khle  "+
												"	from  TIEUCHITHUONGTL_TIEUCHI a   "+
												"		inner join TIEUCHITHUONGTL b on b.PK_SEQ=a.thuongtl_fk "+
												"		inner join "+  
												"		(  "+
												"			select distinct loainpp,thuongtl_fk "+ 
												"			from TIEUCHITHUONGTL_LOAINPP   "+
												"		)c on c.thuongtl_fk=b.PK_SEQ" +
												"	where b.NAM=(select datepart(year,'"+dauthang+"')) and loaict=0 and c.loainpp=(select loainpp from nhaphanphoi where pk_Seq=(select npp_fk from khachhang where pk_Seq='"+khIds[i]+"'))  "+
												"	group by  a.thuongtl_fk,c.loainpp  ";
								ResultSet rs =db.get(query);
								while(rs.next())
								{
									pt_chietkhau=rs.getDouble(cotLAY);
								}	
								rs.close();
								
								System.out.println("[pc_ck]"+pt_chietkhau+"[cotLay]"+cotLAY+query);
								
								//DA BAO GOM VAT
								duno_dauky = -1 * rsCHECK.getDouble("duno_dauky");
								
								System.out.println("duno_dauky"+ duno_dauky);
							}
						}
						rsCHECK.close();
					}
					
					/*if( tongTHUVEThangtruoc > 0 )*/
					{
						
						String tennpp = "";
						String diachinpp = "";
						String masothuenpp = "";
						String dienthoainpp = "";
						String tennguoiddNpp = "";
						
						query = "select isnull(TENKYHD,'') TEN , DIACHI, MASOTHUE, DIENTHOAI, isnull(TENNGUOIDAIDIEN, '') as TENNGUOIDAIDIEN " +
								"from NHAPHANPHOI WHERE PK_SEQ = '"+ obj.getNppId() +"' ";
						System.out.println("--THONG TIN KH: " + query);
						ResultSet rss = db.get(query) ;
						if(rss!= null)
						{
							while(rss.next())
							{
								tennpp = rss.getString("TEN");
								diachinpp = rss.getString("DIACHI");
								masothuenpp = rss.getString("MASOTHUE");
								dienthoainpp = rss.getString("DIENTHOAI");
								tennguoiddNpp = rss.getString("TENNGUOIDAIDIEN");
								
							}rss.close();
						}
						
						query = " select kh.PK_SEQ khId, ISNULL(kh.TEN,'') TEN, isnull(TENKyHd, '') as chucuahieu, kh.DIACHI, kh.MASOTHUE, kh.DIENTHOAI, isnull(MAHD, '') as MAHD " +
								" from KHACHHANG kh where kh.PK_SEQ = '" + khIds[i] + "'";
						
						System.out.println("--THONG TIN KH: " + query);
						String tenkh="";
						String chucuahieu = "";
						String diachikh ="";
						String masothuekh="";
						String dienthoaikh="";
						String mahopdong = "";
						
						rss = db.get(query) ;
						if(rss!= null)
						{
							while(rss.next())
							{
								tenkh = rss.getString("TEN");
								diachikh = rss.getString("DIACHI");
								masothuekh=rss.getString("MASOTHUE");
								dienthoaikh=rss.getString("DIENTHOAI");
								chucuahieu = rss.getString("chucuahieu");
								mahopdong = rss.getString("MAHD");
							}
							rss.close();
						}
						
						
						BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
						
						PdfPTable tableheader=new PdfPTable(2);//số cột
						tableheader.setWidthPercentage(100);//độ giãn của cột
						float[] withsheader = { 200.0f, 500.0f};
						tableheader.setHorizontalAlignment(Element.ALIGN_CENTER);
						tableheader.setWidths(withsheader); //khai báo			
						
						//BẢNG 1	//DÒNG 1: CỘT 1
						Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path") + "/logobtcn.gif");	//in hình logo
						hinhanh.scalePercent(20);//độ giãn của hình
						hinhanh.setAlignment(Element.ALIGN_CENTER);//canh giữa			
						PdfPCell cellslogo = new PdfPCell(hinhanh); // add vào ô
						cellslogo.setFixedHeight(1.0f*CONVERT);
						cellslogo.setBorder(0);
						tableheader.addCell(cellslogo);
						
						//BẢNG 1	//DÒNG 1: CỘT 2	
						PdfPCell cell = new PdfPCell(); 
						Paragraph td = new Paragraph("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM",  new Font(bf, 11, Font.BOLD));//gia tri text
						td.setAlignment(Element.ALIGN_CENTER);
						cell.setFixedHeight(1.0f*CONVERT);
						cell.addElement(td);
						cell.setBorder(0);
						cell.setPaddingTop(-7.0f);
						tableheader.addCell(cell);
						
						//BẢNG 1    //DÒNG 2: CỘT 1
						PdfPCell cella =  new PdfPCell(); 
						td = new Paragraph(" ",  new Font(bf, 10, Font.BOLD));//gia tri text
						cell.setFixedHeight(0.9f*CONVERT );
						//cell.setPaddingTop(-0.2f*CONVERT -7.0f);
						cella.addElement(td);
						cella.setBorder(0);
						tableheader.addCell(cella);
						
						//BẢNG 1    //DÒNG 2: CỘT 2
						PdfPCell cell1 =  new PdfPCell();
						td = new Paragraph("Độc lập - Tự do - Hạnh phúc",  new Font(bf, 12, Font.BOLD));//gia tri text			
						td.setAlignment(Element.ALIGN_CENTER);
						cell1.setFixedHeight(0.9f*CONVERT);
						cell1.setPaddingTop(-20.0f);
						cell1.addElement(td);
						cell1.setBorder(0);
						tableheader.addCell(cell1);
						
						//BẢNG 1	//DÒNG 3: CỘT 1 	
						PdfPCell cell1a =  new PdfPCell();
						td = new Paragraph("_________________",  new Font(bf, 9, Font.BOLD));//gia tri text
						cell1a.addElement(td);
						cell1a.setPaddingTop(-1.3f*CONVERT);
						cell1a.setPaddingLeft(1.0f*CONVERT);
						cell1a.setBorder(0);
						td.setAlignment(Element.ALIGN_LEFT);
						td.setSpacingAfter(0.2f*CONVERT);
						tableheader.addCell(cell1a);
						
						//BẢNG 1	//DÒNG 3: CỘT 2 	
						PdfPCell cell2 =  new PdfPCell();
						td = new Paragraph("_________________",  new Font(bf, 9, Font.BOLD));//gia tri text
						cell2.addElement(td);
						cell2.setPaddingTop(-1.3f*CONVERT);
						cell2.setBorder(0);
						td.setAlignment(Element.ALIGN_CENTER);
						td.setSpacingAfter(0.2f*CONVERT);
						tableheader.addCell(cell2);
						document.add(tableheader);// dòng cuối cùng thêm cột này
						
						
						// DÒNG BIÊN BẢN BÙ TRỪ CÔNG NỢ	
						
						td = new Paragraph("BIÊN BẢN BÙ TRỪ CÔNG NỢ",  new Font(bf, 20, Font.BOLD));//dòng dữ liệu
						td.setAlignment(Element.ALIGN_CENTER);
						document.add(td);
						
						// DÒNG HÔM NAY, NGÀY ... THÁNG ... NĂM
						
						td = new Paragraph(" ",  new Font(bf, 12, Font.NORMAL));
						td.setAlignment(Element.ALIGN_RIGHT);
						document.add(td);
						
						td = new Paragraph("Hôm nay, ngày ... tháng ... năm ...         ",  new Font(bf, 12, Font.NORMAL));
						td.setAlignment(Element.ALIGN_RIGHT);
						document.add(td);
						
						// BÊN A: CHI NHÁNH CÔNG TY CP TRAPHACO TẠI ...
						PdfPTable tablethongtinnpp =new PdfPTable(1);//số cột
						tablethongtinnpp.setWidthPercentage(100);//độ giãn của cột
						float[] withthongtinnpp = { 100.0f};
						tablethongtinnpp.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tablethongtinnpp.setWidths(withthongtinnpp); //khai báo						
						
						PdfPCell cell51 =  new PdfPCell();
						td = new Paragraph("BÊN A: " + tennpp.toUpperCase().trim(),  new Font(bf, 14, Font.BOLD));				
						//cell51.setPaddingLeft(1.0f*CONVERT);
						cell51.addElement(td);
						cell51.setBorder(0);
						tablethongtinnpp.addCell(cell51);
						document.add(tablethongtinnpp);
						
						
						//THÔNG TIN BÊN A		
						PdfPTable tablethongtin =new PdfPTable(1);//số cột
						tablethongtin.setWidthPercentage(100);//độ giãn của cột
						float[] withthongtin = { 100.0f};
						tablethongtin.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tablethongtin.setWidths(withthongtin); //khai báo		
						
						//BẢNG 2 // CỘT 1 //ĐỊA CHỈ BÊN A
						
						PdfPCell cell3 =  new PdfPCell();
						td = new Paragraph("Địa chỉ: " + diachinpp,  new Font(bf, 12, Font.NORMAL));
						//cell3.setPaddingLeft(1.0f*CONVERT);
						cell3.addElement(td);
						cell3.setBorder(0);
						tablethongtin.addCell(cell3);			
						
						//BẢNG 2 // CỘT 1 //MST BÊN A
						
						PdfPCell cell5 =  new PdfPCell();
						td = new Paragraph("Mã số thuế: " + masothuenpp,  new Font(bf, 12, Font.NORMAL));
						//cell5.setPaddingLeft(1.0f*CONVERT);
						cell5.addElement(td);
						cell5.setBorder(0);
						tablethongtin.addCell(cell5);
						
						//BẢNG 2 // CỘT 1 //Điện thoại BÊN A
						
						PdfPCell cell7 =  new PdfPCell();
						td = new Paragraph("Điện thoại: " + dienthoainpp,  new Font(bf, 12, Font.NORMAL));
						//cell7.setPaddingLeft(1.0f*CONVERT);
						cell7.addElement(td);
						cell7.setBorder(0);
						tablethongtin.addCell(cell7);
						
						//BẢNG 2 // CỘT 1 //ĐẠI DIỆN BÊN A
						
						PdfPCell cell9 =  new PdfPCell();
						td = new Paragraph("Đại diện: " + tennguoiddNpp,  new Font(bf, 12, Font.NORMAL));
						//cell9.setPaddingLeft(1.0f*CONVERT);
						cell9.addElement(td);
						cell9.setBorder(0);
						tablethongtin.addCell(cell9);
						
						document.add(tablethongtin);//thêm cái này
						
						// BÊN B: 
						PdfPTable tablethongtinkh =new PdfPTable(1);//số cột
						tablethongtinkh.setWidthPercentage(100);//độ giãn của cột
						float[] withthongtinkh = {100.0f};
						tablethongtinkh.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tablethongtinkh.setWidths(withthongtinkh); //khai báo		
						
						PdfPCell cell100 =  new PdfPCell();
						if(nppId.equals("106210"))
							td = new Paragraph("BÊN B: " + tenkh.toUpperCase() + " - " + mahopdong,  new Font(bf, 14, Font.BOLD));	
						else
							td = new Paragraph("BÊN B: " + tenkh.toUpperCase(),  new Font(bf, 14, Font.BOLD));				
						//cell100.setPaddingLeft(1.0f*CONVERT);
						cell100.addElement(td);
						cell100.setBorder(0);
						tablethongtinkh.addCell(cell100);
						document.add(tablethongtinkh);
						
						//THÔNG TIN BÊN B		
						
						PdfPTable tablethongtinB =new PdfPTable(1);//số cột
						tablethongtinB.setWidthPercentage(100);//độ giãn của cột
						float[] withthongtinB = {100.0f};
						tablethongtinB.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tablethongtinB.setWidths(withthongtinB); //khai báo				
						
						//BẢNG 2 // CỘT 1 //ĐỊA CHỈ BÊN B
						
						PdfPCell cell11 =  new PdfPCell();
						td = new Paragraph("Địa chỉ: " + diachikh,  new Font(bf, 12, Font.NORMAL));
						//cell11.setPaddingLeft(1.0f*CONVERT);
						cell11.addElement(td);
						cell11.setBorder(0);
						tablethongtinB.addCell(cell11);
						
						//BẢNG 2 // CỘT 1 //MST BÊN B
						
						PdfPCell cell13 =  new PdfPCell();
						td = new Paragraph("Mã số thuế: " + masothuekh,  new Font(bf, 12, Font.NORMAL));
						//cell13.setPaddingLeft(1.0f*CONVERT);
						cell13.addElement(td);
						cell13.setBorder(0);
						tablethongtinB.addCell(cell13);
						
						//BẢNG 2 // CỘT 1 //Điện thoại BÊN B
						
						PdfPCell cell15 =  new PdfPCell();
						td = new Paragraph("Điện thoại: " + dienthoaikh,  new Font(bf, 12, Font.NORMAL));
						//cell15.setPaddingLeft(1.0f*CONVERT);
						cell15.addElement(td);
						cell15.setBorder(0);
						tablethongtinB.addCell(cell15);
						
						//BẢNG 2 // CỘT 1 //ĐẠI DIỆN BÊN B
						
						PdfPCell cell17 =  new PdfPCell();
						td = new Paragraph("Đại diện: " + chucuahieu,  new Font(bf, 12, Font.NORMAL));
						//cell17.setPaddingLeft(1.0f*CONVERT);
						cell17.addElement(td);
						cell17.setBorder(0);
						tablethongtinB.addCell(cell17);
						
						document.add(tablethongtinB);
						
						// DÒNG HAI BÊN THỐNG NHẤT BÙ TRỪ		
						
						PdfPTable tablet1 =new PdfPTable(1);//số cột
						tablet1.setWidthPercentage(100);//độ giãn của cột
						float[] withthongtin1 = {100.0f};
						tablet1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tablet1.setWidths(withthongtin1); //khai báo		
						
						PdfPCell cell101 =  new PdfPCell();
						td = new Paragraph("Hai bên thống nhất bù trừ số tiền Chiết khấu thanh toán Tháng " + thangTRUOC + " năm " + namTRUOC + " Theo số liệu sau:",  new Font(bf, 12, Font.NORMAL));				
						cell101.setPaddingTop(20.0f);
						cell101.addElement(td);
						cell101.setBorder(0);
						tablet1.addCell(cell101);
						document.add(tablet1);	
						
						//BẢNG THÔNG TIN
						
						PdfPTable tablethongtinC =new PdfPTable(4);//số cột
						tablethongtinC.setWidthPercentage(100);//độ giãn của cột
						tablethongtinC.setSpacingBefore(10.0f);
						float[] withthongtinc = { 80.0f, 80.0f, 80.0f, 80.0f};
						tablethongtinC.setWidths(withthongtinc); //khai báo	
						
						PdfPCell cell19 =  new PdfPCell();
						td = new Paragraph("Số tiền thanh toán",  new Font(bf, 12, Font.BOLD));
						td.setAlignment(Element.ALIGN_CENTER);
						cell19.addElement(td);
						tablethongtinC.addCell(cell19);			
						
						PdfPCell cell20 =  new PdfPCell();
						td = new Paragraph("Điều kiện về \n thanh toán",  new Font(bf, 12, Font.BOLD));
						td.setAlignment(Element.ALIGN_CENTER);
						cell20.setPaddingBottom(0.3f*CONVERT);
						cell20.addElement(td);
						tablethongtinC.addCell(cell20);
						
						PdfPCell cell21 =  new PdfPCell();
						td = new Paragraph("% chiết khấu \n tháng",  new Font(bf, 12, Font.BOLD));
						td.setAlignment(Element.ALIGN_CENTER);
						cell21.addElement(td);
						tablethongtinC.addCell(cell21);
						
						PdfPCell cell22 =  new PdfPCell();
						td = new Paragraph("Số tiền CK \n thanh toán",  new Font(bf, 12, Font.BOLD));
						td.setAlignment(Element.ALIGN_CENTER);
						cell22.addElement(td);
						tablethongtinC.addCell(cell22);
						
						PdfPCell cell23 =  new PdfPCell();
						td = new Paragraph(formatter.format(tongTHUVEThangtruoc),  new Font(bf, 12, Font.NORMAL));	
						td.setAlignment(Element.ALIGN_CENTER);
						cell23.setPaddingBottom(0.3f*CONVERT);
						cell23.addElement(td);
						tablethongtinC.addCell(cell23);
						
						PdfPCell cell24 =  new PdfPCell();
						td = new Paragraph("Đạt",  new Font(bf, 12, Font.NORMAL));
						td.setAlignment(Element.ALIGN_CENTER);
						cell24.addElement(td);
						tablethongtinC.addCell(cell24);
						
						PdfPCell cell25 =  new PdfPCell();
						td = new Paragraph(formatter.format(pt_chietkhau),  new Font(bf, 12, Font.NORMAL));
						td.setAlignment(Element.ALIGN_CENTER);
						cell25.addElement(td);
						tablethongtinC.addCell(cell25);
						
						PdfPCell cell26 =  new PdfPCell();
						td = new Paragraph(formatter.format(pt_chietkhau * tongTHUVEThangtruoc / 100 ),  new Font(bf, 12, Font.NORMAL));			
						td.setAlignment(Element.ALIGN_CENTER);
						cell26.addElement(td);
						tablethongtinC.addCell(cell26);
						
						
						if(duno_dauky != 0)
						{
							cell23 =  new PdfPCell();
							td = new Paragraph("Số tiền CK tháng trước chưa thanh toán",  new Font(bf, 12, Font.NORMAL));	
							td.setAlignment(Element.ALIGN_CENTER);
							cell23.setPaddingBottom(0.3f*CONVERT);
							cell23.addElement(td);
							cell23.setColspan(3);
							tablethongtinC.addCell(cell23);
							
							
							cell26 =  new PdfPCell();
							td = new Paragraph(formatter.format(duno_dauky),  new Font(bf, 12, Font.NORMAL));			
							td.setAlignment(Element.ALIGN_CENTER);
							cell26.addElement(td);
							tablethongtinC.addCell(cell26);
						}
						
						
						document.add(tablethongtinC);
						
						
						PdfPTable tablet2 =new PdfPTable(1);//số cột
						tablet2.setWidthPercentage(100);//độ giãn của cột
						float[] withthongtin2 = {100.0f};
						tablet2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tablet2.setWidths(withthongtin2); //khai báo		
						
						PdfPCell cell102 =  new PdfPCell();
						td = new Paragraph("Số tiền chiết khấu thanh toán tháng được hưởng của bên B sẽ được bên A bù trừ vào công nợ của tháng " + obj.getTungay() + " năm " + obj.getDenngay(),  new Font(bf, 12, Font.NORMAL));
						td.setAlignment(Element.ALIGN_JUSTIFIED);
						//cell102.setPaddingLeft(1.0f*CONVERT);
						cell102.addElement(td);
						cell102.setBorder(0);
						tablet2.addCell(cell102);
						document.add(tablet2);	
						
						
						PdfPTable tablet3 =new PdfPTable(1);//số cột
						tablet3.setWidthPercentage(100);//độ giãn của cột
						float[] withthongtin3 = {100.0f};
						tablet2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tablet2.setWidths(withthongtin3); //khai báo		
						
						PdfPCell cell103 =  new PdfPCell();
						td = new Paragraph("Biên bản được lập thành 02 bản, có giá trị như nhau, mỗi bên giữ 01 bản để làm cơ sở hạch toán đối trừ công nợ.",  new Font(bf, 12, Font.NORMAL));
						td.setAlignment(Element.ALIGN_JUSTIFIED);
						//cell103.setPaddingLeft(1.0f*CONVERT);
						cell103.addElement(td);
						cell103.setBorder(0);
						cell103.setPaddingTop(10.0f);
						tablet3.addCell(cell103);
						document.add(tablet3);	
						
						//BẢNG KÝ TÊN		
						PdfPTable tablethongtinD =new PdfPTable(2);//số cột
						tablethongtinD.setWidthPercentage(100);//độ giãn của cột
						float[] withthongtinKT = { 100.0f, 100.0f};
						tablethongtinD.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tablethongtinD.setWidths(withthongtinKT); //khai báo	
						
						PdfPCell cell27 =  new PdfPCell();
						td = new Paragraph("ĐẠI DIỆN BÊN A",  new Font(bf, 14, Font.BOLD));
						/*td.setAlignment(Element.ALIGN_CENTER);*/
						cell27.setPaddingLeft(2.5f*CONVERT);
						cell27.addElement(td);
						cell27.setBorder(0);
						tablethongtinD.addCell(cell27);
						
						PdfPCell cell28 =  new PdfPCell();
						td = new Paragraph("ĐẠI DIỆN BÊN B",  new Font(bf, 14, Font.BOLD));
						/*td.setAlignment(Element.ALIGN_CENTER);*/
						cell28.setPaddingLeft(4.0f*CONVERT);
						cell28.addElement(td);
						cell28.setBorder(0);
						tablethongtinD.addCell(cell28);
						tablethongtinD.setSpacingAfter(20.0f*CONVERT);
						
						Font fontCK = new Font(bf, 12, Font.ITALIC);
						fontCK.setColor(BaseColor.RED);
						
						cell27 =  new PdfPCell();
						td = new Paragraph("Ký ghi rõ họ tên", fontCK);
						/*td.setAlignment(Element.ALIGN_CENTER);*/
						cell27.setPaddingLeft(2.8f*CONVERT);
						cell27.addElement(td);
						cell27.setBorder(0);
						tablethongtinD.addCell(cell27);
						
						cell28 =  new PdfPCell();
						td = new Paragraph("Ký ghi rõ họ tên",  fontCK);
						/*td.setAlignment(Element.ALIGN_CENTER);*/
						cell28.setPaddingLeft(4.5f*CONVERT);
						cell28.addElement(td);
						cell28.setBorder(0);
						tablethongtinD.addCell(cell28);
						tablethongtinD.setSpacingAfter(20.0f*CONVERT);
						
						document.add(tablethongtinD);
						document.newPage();
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception In Phieu Thu: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void CreateBienBanBuTru_HoaDon(Document document, ServletOutputStream outstream, String id, String nppId) 
	{
		dbutils db = new dbutils();
		try
		{	
			String ngayDonHang="";
			String kyhieuHD="";
			String khId = "";
			String ngayxuatHD = "";
			String sohoadon = "";
			String mahopdong = "";
			String dhId= "";
			String tennpp = "";
			String diachinpp = "";
			String masothuenpp = "";
			String dienthoainpp = "";
			String tennguoiddNpp = "";
			String Thang="";	
			double tongcong=0;			
			double HanMucDoanhThu=0;
			String tableNAME = "NHOMSANPHAM";
			String VUNG_FK ="";
			String tenkh="";
			String chucuahieu = "";
			String diachikh ="";
			String masothuekh="";
			String dienthoaikh="";
			String maFAST = "";
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			String query = 
			"		select MaHD,khachhang_fk,ngayxuathd,sohoadon,mahd,donhang_fk as donhang_fk,ten_npp,diachi_npp,masothue_npp,dienthoai_npp,daidien_npp,ten_kh,diachi_kh,masothue_kh,dienthoai_kh,daidien_kh,mafast_KH " +
			" from BienBanBuTruCongNo  hd      																													"+
			"	where hd.PK_SEQ='"+id+"' ";
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				
				khId = rs.getString("khachhang_fk");
				ngayxuatHD = rs.getString("ngayxuatHD");
				sohoadon = rs.getString("sohoadon");
				mahopdong = rs.getString("MAHD");
				dhId=rs.getString("donhang_fk");
				tennpp = rs.getString("ten_npp");
				diachinpp = rs.getString("diachi_npp");
				masothuenpp = rs.getString("masothue_npp");
				dienthoainpp = rs.getString("dienthoai_npp");
				tennguoiddNpp = rs.getString("daidien_npp");
				
				tenkh = rs.getString("ten_kh");
				diachikh = rs.getString("diachi_kh");
				masothuekh=rs.getString("masothue_kh");
				dienthoaikh=rs.getString("DIENTHOAI_kh");
				chucuahieu = rs.getString("daidien_kh");
				maFAST = rs.getString("maFAST_KH");
			}
			rs.close();
		
			
			String loaiNPP = "";
			boolean daDUYET = false;
			double tongDSThangtruoc = 0;
			double tongTHUVEThangtruoc = 0;
			String cotLAY = "chietkhau";
			String thanhTOAN = "0";
			double pt_chietkhau = 0;
			double chietkhau_bosung = 0;
			double dsHHBG=0;
			int daDuyet=0;
			double duno_dauky = 0;
			if(khId.trim().length() > 0 )
			{
				NumberFormat formatter = new DecimalFormat("#,###,###");
				PdfWriter writer = PdfWriter.getInstance(document, outstream);
				document.setMargins(60.0f, 35.0f, 0.7f*CONVERT, 0.7f*CONVERT);
				document.open();
				String NgayDonHang=ngayxuatHD;
				String year = NgayDonHang.substring(0, 4);
				String NgayThang=NgayDonHang.substring(0, 7);
				
				int thang = Integer.parseInt(NgayDonHang.split("-")[1]);
				int nam = Integer.parseInt(NgayDonHang.split("-")[0]);
				if(thang == 1)
				{
					thang = 12;
					nam = nam - 1;
				}
				else
				{
					thang = thang - 1;
				}			
				PdfPTable tablethongtinC =new PdfPTable(4);//số cột
				tablethongtinC.setWidthPercentage(100);//độ giãn của cột
				tablethongtinC.setSpacingBefore(10.0f);
				float[] withthongtinc = { 80.0f, 80.0f, 80.0f, 80.0f};
				tablethongtinC.setWidths(withthongtinc); //khai báo	
				double totalThuVe=0;
				double totalCK=0;
					query=
					"select DoanhThu*ptchietkhau/100.0  as ck,doanhthu as DoanhThu,DuNoDauKy,ptchietkhau,DATEPART(MONTH,DATEADD(month,-1,ngayXuatHD)) as Thang " + 
					"		from BienBanBuTruCongNo_ChiTiet " +
					" where bbbtcn_Fk='"+id+"' ";
					rs=db.get(query);
					int i=0;
					while(rs.next())
					{
						pt_chietkhau = rs.getDouble("ptchietkhau");
						totalCK +=rs.getDouble("ck");
						tongTHUVEThangtruoc = rs.getDouble("DoanhThu");
						duno_dauky =  rs.getDouble("DuNoDauKy");
						Thang =rs.getString("Thang");
						i++;
						
						/*****************************************************************************************************/
						//BẢNG THÔNG TIN CK
						if(i==1)
						{
							PdfPCell cell19 =  new PdfPCell();
							Paragraph			td = new Paragraph("Số tiền thanh toán",  new Font(bf, 12, Font.BOLD));
							td.setAlignment(Element.ALIGN_CENTER);
							cell19.addElement(td);
							tablethongtinC.addCell(cell19);			
							
							PdfPCell cell20 =  new PdfPCell();
							td = new Paragraph("Điều kiện về \n thanh toán",  new Font(bf, 12, Font.BOLD));
							td.setAlignment(Element.ALIGN_CENTER);
							cell20.setPaddingBottom(0.3f*CONVERT);
							cell20.addElement(td);
							tablethongtinC.addCell(cell20);
							
							PdfPCell cell21 =  new PdfPCell();
							td = new Paragraph("% chiết khấu \n tháng",  new Font(bf, 12, Font.BOLD));
							td.setAlignment(Element.ALIGN_CENTER);
							cell21.addElement(td);
							tablethongtinC.addCell(cell21);
							
							PdfPCell cell22 =  new PdfPCell();
							td = new Paragraph("Số tiền CK \n thanh toán",  new Font(bf, 12, Font.BOLD));
							td.setAlignment(Element.ALIGN_CENTER);
							cell22.addElement(td);
							tablethongtinC.addCell(cell22);
						}
						
						if(tongTHUVEThangtruoc>0)
						{
							PdfPCell cell23 =  new PdfPCell();					
							Paragraph	td = new Paragraph(formatter.format(tongTHUVEThangtruoc),  new Font(bf, 12, Font.NORMAL));	
							td.setAlignment(Element.ALIGN_CENTER);
							cell23.setPaddingBottom(0.3f*CONVERT);
							cell23.addElement(td);
							tablethongtinC.addCell(cell23);
							
							PdfPCell cell24 =  new PdfPCell();
							td = new Paragraph("Đạt",  new Font(bf, 12, Font.NORMAL));
							td.setAlignment(Element.ALIGN_CENTER);
							cell24.addElement(td);
							tablethongtinC.addCell(cell24);
							
							PdfPCell cell25 =  new PdfPCell();
							td = new Paragraph(formatter.format(pt_chietkhau),  new Font(bf, 12, Font.NORMAL));
							td.setAlignment(Element.ALIGN_CENTER);
							cell25.addElement(td);
							tablethongtinC.addCell(cell25);
							
							tongcong+=pt_chietkhau * tongTHUVEThangtruoc / 100;
							
							PdfPCell cell26 =  new PdfPCell();
							td = new Paragraph(formatter.format(pt_chietkhau * tongTHUVEThangtruoc / 100 ),  new Font(bf, 12, Font.NORMAL));			
							td.setAlignment(Element.ALIGN_CENTER);
							cell26.addElement(td);
							tablethongtinC.addCell(cell26);
						}
						
						if(duno_dauky != 0)
						{
							PdfPCell		cell23 =  new PdfPCell();
							Paragraph	td = new Paragraph("Số tiền CK tháng trước chưa thanh toán",  new Font(bf, 12, Font.NORMAL));	
							td.setAlignment(Element.ALIGN_CENTER);
							cell23.setPaddingBottom(0.3f*CONVERT);
							cell23.addElement(td);
							cell23.setColspan(3);
							tablethongtinC.addCell(cell23);
							
							tongcong+=duno_dauky;
							
							PdfPCell	cell26 =  new PdfPCell();
							td = new Paragraph(formatter.format(duno_dauky),  new Font(bf, 12, Font.NORMAL));			
							td.setAlignment(Element.ALIGN_CENTER);
							cell26.addElement(td);
							tablethongtinC.addCell(cell26);
						}
						/*****************************************************************************************************/				
						
						
					}
					if(rs!=null)rs.close();
					
									
				PdfPTable tableheader=new PdfPTable(2);//số cột
				tableheader.setWidthPercentage(100);//độ giãn của cột
				float[] withsheader = { 200.0f, 500.0f};
				tableheader.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableheader.setWidths(withsheader); //khai báo			
				
				//BẢNG 1	//DÒNG 1: CỘT 1
				Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path") + "/logobtcn.gif");	//in hình logo
				hinhanh.scalePercent(20);//độ giãn của hình
				hinhanh.setAlignment(Element.ALIGN_CENTER);//canh giữa			
				PdfPCell cellslogo = new PdfPCell(hinhanh); // add vào ô
				cellslogo.setFixedHeight(1.0f*CONVERT);
				cellslogo.setBorder(0);
				tableheader.addCell(cellslogo);

				
				//BẢNG 1	//DÒNG 1: CỘT 2	
				PdfPCell cell = new PdfPCell(); 
				Paragraph td = new Paragraph("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM",  new Font(bf, 11, Font.BOLD));//gia tri text
				td.setAlignment(Element.ALIGN_CENTER);
				cell.setFixedHeight(1.0f*CONVERT);
				cell.addElement(td);
				cell.setBorder(0);
				cell.setPaddingTop(-7.0f);
				tableheader.addCell(cell);
				
				//BẢNG 1    //DÒNG 2: CỘT 1
				PdfPCell cella =  new PdfPCell(); 
				td = new Paragraph(" ",  new Font(bf, 10, Font.BOLD));//gia tri text
				cell.setFixedHeight(0.9f*CONVERT );
				//cell.setPaddingTop(-0.2f*CONVERT -7.0f);
				cella.addElement(td);
				cella.setBorder(0);
				tableheader.addCell(cella);
				
				//BẢNG 1    //DÒNG 2: CỘT 2
				PdfPCell cell1 =  new PdfPCell();
				td = new Paragraph("Độc lập - Tự do - Hạnh phúc",  new Font(bf, 12, Font.BOLD));//gia tri text			
				td.setAlignment(Element.ALIGN_CENTER);
				cell1.setFixedHeight(0.9f*CONVERT);
				cell1.setPaddingTop(-20.0f);
				cell1.addElement(td);
				cell1.setBorder(0);
				tableheader.addCell(cell1);
				
				//BẢNG 1	//DÒNG 3: CỘT 1 	
				PdfPCell cell1a =  new PdfPCell();
				td = new Paragraph("_________________",  new Font(bf, 9, Font.BOLD));//gia tri text
				cell1a.addElement(td);
				cell1a.setPaddingTop(-1.3f*CONVERT);
				cell1a.setPaddingLeft(1.0f*CONVERT);
				cell1a.setBorder(0);
				td.setAlignment(Element.ALIGN_LEFT);
				td.setSpacingAfter(0.2f*CONVERT);
				tableheader.addCell(cell1a);
				
				//BẢNG 1	//DÒNG 3: CỘT 2 	
				PdfPCell cell2 =  new PdfPCell();
				td = new Paragraph("_________________",  new Font(bf, 9, Font.BOLD));//gia tri text
				cell2.addElement(td);
				cell2.setPaddingTop(-1.3f*CONVERT);
				cell2.setBorder(0);
				td.setAlignment(Element.ALIGN_CENTER);
				td.setSpacingAfter(0.2f*CONVERT);
				tableheader.addCell(cell2);
				document.add(tableheader);// dòng cuối cùng thêm cột này
				
				
				// DÒNG BIÊN BẢN BÙ TRỪ CÔNG NỢ	
				
				td = new Paragraph("BIÊN BẢN BÙ TRỪ CÔNG NỢ",  new Font(bf, 20, Font.BOLD));//dòng dữ liệu
				td.setAlignment(Element.ALIGN_CENTER);
				document.add(td);
				
				// DÒNG HÔM NAY, NGÀY ... THÁNG ... NĂM
				
				td = new Paragraph(" ",  new Font(bf, 12, Font.NORMAL));
				td.setAlignment(Element.ALIGN_RIGHT);
				document.add(td);
				
				td = new Paragraph("Hôm nay, ngày " + ngayxuatHD.split("-")[2] + " tháng " + ngayxuatHD.split("-")[1] + " năm " + ngayxuatHD.split("-")[0] + "         ",  new Font(bf, 12, Font.NORMAL));
				td.setAlignment(Element.ALIGN_RIGHT);
				document.add(td);
				
				// BÊN A: CHI NHÁNH CÔNG TY CP TRAPHACO TẠI ...
				PdfPTable tablethongtinnpp =new PdfPTable(1);//số cột
				tablethongtinnpp.setWidthPercentage(100);//độ giãn của cột
				float[] withthongtinnpp = { 100.0f};
				tablethongtinnpp.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tablethongtinnpp.setWidths(withthongtinnpp); //khai báo						
				
				PdfPCell cell51 =  new PdfPCell();
				td = new Paragraph("BÊN A: " + tennpp.toUpperCase().trim(),  new Font(bf, 14, Font.BOLD));				
				//cell51.setPaddingLeft(1.0f*CONVERT);
				cell51.addElement(td);
				cell51.setBorder(0);
				tablethongtinnpp.addCell(cell51);
				document.add(tablethongtinnpp);
				
				
				//THÔNG TIN BÊN A		
				PdfPTable tablethongtin =new PdfPTable(1);//số cột
				tablethongtin.setWidthPercentage(100);//độ giãn của cột
				float[] withthongtin = { 100.0f};
				tablethongtin.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tablethongtin.setWidths(withthongtin); //khai báo		
				
				//BẢNG 2 // CỘT 1 //ĐỊA CHỈ BÊN A
				
				PdfPCell cell3 =  new PdfPCell();
				td = new Paragraph("Địa chỉ: " + diachinpp,  new Font(bf, 12, Font.NORMAL));
				//cell3.setPaddingLeft(1.0f*CONVERT);
				cell3.addElement(td);
				cell3.setBorder(0);
				tablethongtin.addCell(cell3);			
				
				//BẢNG 2 // CỘT 1 //MST BÊN A
				
				PdfPCell cell5 =  new PdfPCell();
				td = new Paragraph("Mã số thuế: " + masothuenpp,  new Font(bf, 12, Font.NORMAL));
				//cell5.setPaddingLeft(1.0f*CONVERT);
				cell5.addElement(td);
				cell5.setBorder(0);
				tablethongtin.addCell(cell5);
				
				//BẢNG 2 // CỘT 1 //Điện thoại BÊN A
				
				PdfPCell cell7 =  new PdfPCell();
				td = new Paragraph("Điện thoại: " + dienthoainpp,  new Font(bf, 12, Font.NORMAL));
				//cell7.setPaddingLeft(1.0f*CONVERT);
				cell7.addElement(td);
				cell7.setBorder(0);
				tablethongtin.addCell(cell7);
				
				//BẢNG 2 // CỘT 1 //ĐẠI DIỆN BÊN A
				
				PdfPCell cell9 =  new PdfPCell();
				td = new Paragraph("Đại diện: " + tennguoiddNpp,  new Font(bf, 12, Font.NORMAL));
				//cell9.setPaddingLeft(1.0f*CONVERT);
				cell9.addElement(td);
				cell9.setBorder(0);
				tablethongtin.addCell(cell9);
				
				document.add(tablethongtin);//thêm cái này
				
				// BÊN B: 
				PdfPTable tablethongtinkh =new PdfPTable(1);//số cột
				tablethongtinkh.setWidthPercentage(100);//độ giãn của cột
				float[] withthongtinkh = {100.0f};
				tablethongtinkh.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tablethongtinkh.setWidths(withthongtinkh); //khai báo		
				
				PdfPCell cell100 =  new PdfPCell();
				if(nppId.equals("106210"))
					td = new Paragraph("BÊN B: " + tenkh.toUpperCase() + " - " + mahopdong + " - " + maFAST,  new Font(bf, 14, Font.BOLD));	
				else
					td = new Paragraph("BÊN B: " + tenkh.toUpperCase(),  new Font(bf, 14, Font.BOLD));	
				
				//cell100.setPaddingLeft(1.0f*CONVERT);
				cell100.addElement(td);
				cell100.setBorder(0);
				tablethongtinkh.addCell(cell100);
				document.add(tablethongtinkh);
				
				//THÔNG TIN BÊN B		
				
				PdfPTable tablethongtinB =new PdfPTable(1);//số cột
				tablethongtinB.setWidthPercentage(100);//độ giãn của cột
				float[] withthongtinB = {100.0f};
				tablethongtinB.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tablethongtinB.setWidths(withthongtinB); //khai báo				
				
				//BẢNG 2 // CỘT 1 //ĐỊA CHỈ BÊN B
				
				PdfPCell cell11 =  new PdfPCell();
				td = new Paragraph("Địa chỉ: " + diachikh,  new Font(bf, 12, Font.NORMAL));
				//cell11.setPaddingLeft(1.0f*CONVERT);
				cell11.addElement(td);
				cell11.setBorder(0);
				tablethongtinB.addCell(cell11);
				
				//BẢNG 2 // CỘT 1 //MST BÊN B
				
				PdfPCell cell13 =  new PdfPCell();
				td = new Paragraph("Mã số thuế: " + masothuekh,  new Font(bf, 12, Font.NORMAL));
				//cell13.setPaddingLeft(1.0f*CONVERT);
				cell13.addElement(td);
				cell13.setBorder(0);
				tablethongtinB.addCell(cell13);
				
				//BẢNG 2 // CỘT 1 //Điện thoại BÊN B
				
				PdfPCell cell15 =  new PdfPCell();
				td = new Paragraph("Điện thoại: " + dienthoaikh,  new Font(bf, 12, Font.NORMAL));
				//cell15.setPaddingLeft(1.0f*CONVERT);
				cell15.addElement(td);
				cell15.setBorder(0);
				tablethongtinB.addCell(cell15);
				
				//BẢNG 2 // CỘT 1 //ĐẠI DIỆN BÊN B
				
				PdfPCell cell17 =  new PdfPCell();
				td = new Paragraph("Đại diện: " + chucuahieu,  new Font(bf, 12, Font.NORMAL));
				//cell17.setPaddingLeft(1.0f*CONVERT);
				cell17.addElement(td);
				cell17.setBorder(0);
				tablethongtinB.addCell(cell17);
				
				document.add(tablethongtinB);
				
				// DÒNG HAI BÊN THỐNG NHẤT BÙ TRỪ		
				
				PdfPTable tablet1 =new PdfPTable(1);//số cột
				tablet1.setWidthPercentage(100);//độ giãn của cột
				float[] withthongtin1 = {100.0f};
				tablet1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tablet1.setWidths(withthongtin1); //khai báo		
				
				PdfPCell cell101 =  new PdfPCell();
				td = new Paragraph("Hai bên thống nhất bù trừ số tiền Chiết khấu thanh toán Tháng " + Thang + " năm " + nam  + " Theo số liệu sau:",  new Font(bf, 12, Font.NORMAL));				
				cell101.setPaddingTop(20.0f);
				cell101.addElement(td);
				cell101.setBorder(0);
				tablet1.addCell(cell101);
				document.add(tablet1);	
				
				
				//document.add(tablethongtinC);
				
				
				PdfPCell cell23 = new PdfPCell();
				td = new Paragraph("Tổng cộng",  new Font(bf, 12, Font.NORMAL));	
				td.setAlignment(Element.ALIGN_CENTER);
				cell23.setPaddingBottom(0.3f*CONVERT);
				cell23.addElement(td);
				cell23.setColspan(3);
				tablethongtinC.addCell(cell23);
				
				PdfPCell cell26 = new PdfPCell();
				td = new Paragraph(formatter.format(Math.round(totalCK) ),  new Font(bf, 12, Font.NORMAL));			
				td.setAlignment(Element.ALIGN_CENTER);
				cell26.addElement(td);
				tablethongtinC.addCell(cell26);
				
				document.add(tablethongtinC);
				PdfPTable tablet2 =new PdfPTable(1);//số cột
				tablet2.setWidthPercentage(100);//độ giãn của cột
				float[] withthongtin2 = {100.0f};
				tablet2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tablet2.setWidths(withthongtin2); //khai báo		
				
				PdfPCell cell102 =  new PdfPCell();
				
				td = new Paragraph("Số tiền chiết khấu thanh toán tháng được hưởng của bên B sẽ được bên A bù trừ vào công nợ vào hóa đơn số " + sohoadon + " ngày " + ngayxuatHD.split("-")[2] + " tháng " + ngayxuatHD.split("-")[1] + " năm " + year,  new Font(bf, 12, Font.NORMAL));
				td.setAlignment(Element.ALIGN_JUSTIFIED);
				//cell102.setPaddingLeft(1.0f*CONVERT);
				cell102.addElement(td);
				cell102.setBorder(0);
				tablet2.addCell(cell102);
				document.add(tablet2);	
				
				
				PdfPTable tablet3 =new PdfPTable(1);//số cột
				tablet3.setWidthPercentage(100);//độ giãn của cột
				float[] withthongtin3 = {100.0f};
				tablet2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tablet2.setWidths(withthongtin3); //khai báo		
				
				PdfPCell cell103 =  new PdfPCell();
				td = new Paragraph("Biên bản được lập thành 02 bản, có giá trị như nhau, mỗi bên giữ 01 bản để làm cơ sở hạch toán đối trừ công nợ.",  new Font(bf, 12, Font.NORMAL));
				td.setAlignment(Element.ALIGN_JUSTIFIED);
				//cell103.setPaddingLeft(1.0f*CONVERT);
				cell103.addElement(td);
				cell103.setBorder(0);
				cell103.setPaddingTop(10.0f);
				tablet3.addCell(cell103);
				document.add(tablet3);	
				
				//BẢNG KÝ TÊN		
				PdfPTable tablethongtinD =new PdfPTable(2);//số cột
				tablethongtinD.setWidthPercentage(100);//độ giãn của cột
				float[] withthongtinKT = { 100.0f, 100.0f};
				tablethongtinD.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tablethongtinD.setWidths(withthongtinKT); //khai báo	
				
				PdfPCell cell27 =  new PdfPCell();
				td = new Paragraph("ĐẠI DIỆN BÊN A",  new Font(bf, 14, Font.BOLD));
				/*td.setAlignment(Element.ALIGN_CENTER);*/
				cell27.setPaddingLeft(2.5f*CONVERT);
				cell27.addElement(td);
				cell27.setBorder(0);
				tablethongtinD.addCell(cell27);
				
				PdfPCell cell28 =  new PdfPCell();
				td = new Paragraph("ĐẠI DIỆN BÊN B",  new Font(bf, 14, Font.BOLD));
				/*td.setAlignment(Element.ALIGN_CENTER);*/
				cell28.setPaddingLeft(4.0f*CONVERT);
				cell28.addElement(td);
				cell28.setBorder(0);
				tablethongtinD.addCell(cell28);
				tablethongtinD.setSpacingAfter(20.0f*CONVERT);
				
				Font fontCK = new Font(bf, 12, Font.ITALIC);
				fontCK.setColor(BaseColor.RED);
				
				cell27 =  new PdfPCell();
				td = new Paragraph("Ký ghi rõ họ tên", fontCK);
				/*td.setAlignment(Element.ALIGN_CENTER);*/
				cell27.setPaddingLeft(2.8f*CONVERT);
				cell27.addElement(td);
				cell27.setBorder(0);
				tablethongtinD.addCell(cell27);
				
				cell28 =  new PdfPCell();
				td = new Paragraph("Ký ghi rõ họ tên",  fontCK);
				/*td.setAlignment(Element.ALIGN_CENTER);*/
				cell28.setPaddingLeft(4.5f*CONVERT);
				cell28.addElement(td);
				cell28.setBorder(0);
				tablethongtinD.addCell(cell28);
				tablethongtinD.setSpacingAfter(20.0f*CONVERT);
				
				
				cell27 =  new PdfPCell();
				td = new Paragraph(tennguoiddNpp, new Font(bf, 13, Font.BOLD));
				//td.setAlignment(Element.ALIGN_CENTER);
				cell27.setFixedHeight(3.0f*CONVERT);
				cell27.setVerticalAlignment(Element.ALIGN_BOTTOM);
				//cell27.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell27.setPaddingLeft(2.8f*CONVERT);
				cell27.addElement(td);
				cell27.setBorder(0);
				tablethongtinD.addCell(cell27);
				
				cell28 =  new PdfPCell();
				td = new Paragraph("",  new Font(bf, 13, Font.BOLD));
				/*td.setAlignment(Element.ALIGN_CENTER);*/
				cell28.setPaddingLeft(4.5f*CONVERT);
				cell28.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cell28.addElement(td);
				cell28.setBorder(0);
				tablethongtinD.addCell(cell28);
				tablethongtinD.setSpacingAfter(20.0f*CONVERT);
				document.add(tablethongtinD);
			}
			db.shutDown();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			db.shutDown();
		}
	}
	
	private static int Songaytrongthang(int month, int year) 
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
	
	private void CreateStaticHeader(Workbook workbook, IInKhauTruCKThang obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();
		
		Cell cell ;  
		
		try
		{
			dbutils db = new dbutils();	  
			
			String sql = " select ten, case when diachi = 'NA' then '' else  isnull(diachi, '') end as diachi, " +
					"        case when dienthoai = 'NA' then '' else  isnull(dienthoai, '') end as dienthoai ," +
					"        case when fax = 'NA' then '' else  isnull(fax, '') end as fax  " +
					" from NHAPHANPHOI  where pk_seq = '"+ obj.getNppId() +"' ";
			
			/*System.out.println("Nhà phân phối : "+sql);	   */ 	
			ResultSet rs = db.get(sql);
			
			String ten = "";
			String diachi = "";
			String dienthoai = "";
			String fax = "";
			
			if(rs!= null)
			{
				while(rs.next())
				{
					ten = rs.getString("ten");
					diachi = rs.getString("diachi");
					dienthoai = rs.getString("dienthoai");
					fax = rs.getString("fax");
				}
				rs.close();	
			}
			
			cell = cells.getCell("C1");
			cell.setValue(ten.toUpperCase());
			
			cell = cells.getCell("C2");
			cell.setValue("Địa chỉ: "+diachi);
			
			cell = cells.getCell("C3");
			cell.setValue("Tel: "+dienthoai +" - Fax: "+fax);
			
			
		}catch(Exception ex){}    
		
		if(!obj.getTungay().equals("") || !obj.getDenngay().equals(""))
		{
			cell = cells.getCell("A5");
			
			cell.setValue("Từ ngày: " + obj.getTungay() + " - Đến ngày: "  + obj.getDenngay());	    
		}
		
		worksheet.setName("BienBanBuTruCongNo");
	}
	
	private void CreateStaticData(Workbook workbook, IInKhauTruCKThang obj ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		NumberFormat format = new DecimalFormat("#,###,###"); 
		
		int thangTRUOC = Integer.parseInt(obj.getTungay().split("-")[1]);
		int namTRUOC = Integer.parseInt(obj.getTungay().split("-")[0]);
		if(thangTRUOC == 1)
		{
			thangTRUOC = 12;
			namTRUOC = namTRUOC - 1;
		}
		else
		{
			thangTRUOC = thangTRUOC - 1;
		}
		
		Cell cell = null;
		
		Style style;
		com.aspose.cells.Font font2 = new com.aspose.cells.Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		
		com.aspose.cells.Font font3 = new com.aspose.cells.Font();
		font3.setName("Times New Roman");
		font3.setSize(12);
		font3.setBold(true);
		
		int i = 8;
		
		String condition = "";
		if(obj.getDdkdId().trim().length() > 0)
		{
			condition += " and a.KHACHHANG_FK in ( select khachhang_fk from KHACHHANG_TUYENBH " +
					"								where TBH_FK in ( select pk_seq from TUYENBANHANG where DDKD_FK = '" + obj.getDdkdId() + "' ) ) ";
		}
		if(obj.getNvgnId().trim().length() > 0)
		{
			condition += " and a.KHACHHANG_FK in ( select KHACHHANG_FK from NVGN_KH where NVGN_FK = '" + obj.getNvgnId() + "' ) ";
		}
		
		//CHI LAY KHACH HANG CO DUYET BAN DUNG GIA  --> XEM THEO HOA DON THI KO CAN
		//condition += " and a.KHACHHANG_FK in ( select khachhang_fk from DUYETBANDUNGGIA a inner join DUYETBANDUNGGIA_KHACHHANG b on a.pk_seq = b.duyet_fk where a.NPP_FK = '" + obj.getNppId() + "' and a.thang = '" + thangTRUOC + "' and a.nam = '" + namTRUOC + "' and a.trangthai = '1'   ) ";
		
		/*System.out.println("---CONDITION: " + condition);*/
		
		//TRƯỜNG HỢP NÀY LẤY HẾT HÓA ĐƠN
		dbutils db = new dbutils();
		/*String query =  "select d.maFAST, d.TEN as khTEN, d.CHUCUAHIEU, c.SOHOADON, c.NGAYXUATHD, c.tongtienavat, b.SOTIENCANTRU,  " +
				"	(	select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ      " +
				"				inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ     " +
				"		where cc.KHACHHANG_FK = d.PK_SEQ " +
				"	) as ddkdTEN " +
				"from CANTRUCONGNO a inner join CANTRUCONGNO_HOADON b on a.PK_SEQ = b.CANTRUCONGNO_FK " +
				"	inner join HOADON c on b.HOADON_FK = c.PK_SEQ " +
				"	inner join KhachHang d on c.KHACHHANG_FK = d.PK_SEQ " +
				"where a.NPP_FK = '" + obj.getNppId() + "' and a.TRANGTHAI = '1' and a.NGAYCHUNGTU >= '" + obj.getTungay() + "' and a.NGAYCHUNGTU <= '" + obj.getDenngay() + "' " + condition +
				"order by a.NGAYCHUNGTU asc ";*/
		
		String query = "select d.maFAST, d.TEN as khTEN, d.CHUCUAHIEU, a.SOHOADON, a.NGAYXUATHD, a.tongtienavat, isnull(b.SOTIENCANTRU, 0) as SOTIENCANTRU,   " +
				"	(	select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ       " +
				"				inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ      " +
				"		where cc.KHACHHANG_FK = d.PK_SEQ  " +
				"	) as ddkdTEN  " +
				"from HOADON a left join CANTRUCONGNO_HOADON b on b.HOADON_FK = a.PK_SEQ  " +
				"	left join CANTRUCONGNO c on c.PK_SEQ = b.CANTRUCONGNO_FK  " +
				"	inner join KhachHang d on a.KHACHHANG_FK = d.PK_SEQ  " +
				"where a.LOAIHOADON = 0 and a.NPP_FK = '" + obj.getNppId() + "' and a.TRANGTHAI not in ( 3, 5 ) and a.NGAYXUATHD >= '" + obj.getTungay() + "' and a.NGAYXUATHD <= '" + obj.getDenngay() + "'   " + condition +
				"order by a.NGAYXUATHD asc  ";
		
		/*System.out.println("---LAY BC1: " + query);*/
		ResultSet hdRs = db.get(query);
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				
				double total_sotienHD = 0;
				double total_chietkhau = 0;
				double total_phaithu = 0;
				
				while(hdRs.next())
				{
					double sotienHD = hdRs.getDouble("tongtienavat");
					double chietkhau = hdRs.getDouble("SOTIENCANTRU");
					double phaithu = sotienHD - chietkhau;
					
					total_sotienHD += sotienHD;
					total_chietkhau += chietkhau;
					total_phaithu += phaithu;
					
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);			 				
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("ddkdTEN")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("MAFAST")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);			
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("khTEN")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("CHUCUAHIEU")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getString("sohoadon")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getString("NGAYXUATHD")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);	
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(sotienHD); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(chietkhau);  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	 
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(phaithu); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					
					i++;
					stt ++;
				}
				hdRs.close();
				
				//THEM DONG TONG CONG
				cells.merge(i - 1, 0, i - 1, 6);
				
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue("TỔNG CỘNG"); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.CENTRED);			 				
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(""); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.LEFT);			
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);			 				
				
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(total_sotienHD); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);	
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(total_chietkhau);  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);	 
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(total_phaithu); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
		
	}
	
	private void CreateStaticData_TheoBangKe(Workbook workbook, IInKhauTruCKThang obj ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		NumberFormat format = new DecimalFormat("#,###,###"); 
		
		int thangTRUOC = Integer.parseInt(obj.getTungay().split("-")[1]);
		int namTRUOC = Integer.parseInt(obj.getTungay().split("-")[0]);
		if(thangTRUOC == 1)
		{
			thangTRUOC = 12;
			namTRUOC = namTRUOC - 1;
		}
		else
		{
			thangTRUOC = thangTRUOC - 1;
		}
		
		Cell cell = null;
		
		Style style;
		com.aspose.cells.Font font2 = new com.aspose.cells.Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		
		com.aspose.cells.Font font3 = new com.aspose.cells.Font();
		font3.setName("Times New Roman");
		font3.setSize(12);
		font3.setBold(true);
		
		int i = 8;
		
		String condition = "";
		if(obj.getDdkdId().trim().length() > 0)
		{
			condition += " and a.KHACHHANG_FK in ( select khachhang_fk from KHACHHANG_TUYENBH " +
					"								where TBH_FK in ( select pk_seq from TUYENBANHANG where DDKD_FK = '" + obj.getDdkdId() + "' ) ) ";
		}
		if(obj.getNvgnId().trim().length() > 0)
		{
			condition += " and a.KHACHHANG_FK in ( select KHACHHANG_FK from NVGN_KH where NVGN_FK = '" + obj.getNvgnId() + "' ) ";
		}
		
		//CHI LAY KHACH HANG CO DUYET BAN DUNG GIA
		//condition += " and a.KHACHHANG_FK in ( select khachhang_fk from DUYETBANDUNGGIA a inner join DUYETBANDUNGGIA_KHACHHANG b on a.pk_seq = b.duyet_fk where a.NPP_FK = '" + obj.getNppId() + "' and a.thang = '" + thangTRUOC + "' and a.nam = '" + namTRUOC + "' and a.trangthai = '1'   ) ";
		
		
		/*System.out.println("---CONDITION: " + condition);*/
		
		//TRƯỜNG HỢP NÀY LẤY HẾT HÓA ĐƠN
		dbutils db = new dbutils();
		/*String query =  "select d.maFAST, d.TEN as khTEN, d.CHUCUAHIEU, c.SOHOADON, c.NGAYXUATHD, c.tongtienavat, b.SOTIENCANTRU,  " +
				"	(	select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ      " +
				"				inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ     " +
				"		where cc.KHACHHANG_FK = d.PK_SEQ " +
				"	) as ddkdTEN " +
				"from CANTRUCONGNO a inner join CANTRUCONGNO_HOADON b on a.PK_SEQ = b.CANTRUCONGNO_FK " +
				"	inner join HOADON c on b.HOADON_FK = c.PK_SEQ " +
				"	inner join KhachHang d on c.KHACHHANG_FK = d.PK_SEQ " +
				"where a.NPP_FK = '" + obj.getNppId() + "' and a.TRANGTHAI = '1' and a.NGAYCHUNGTU >= '" + obj.getTungay() + "' and a.NGAYCHUNGTU <= '" + obj.getDenngay() + "' " + condition +
				"order by a.NGAYCHUNGTU asc ";*/
		
		String query = "select d.maFAST, d.TEN as khTEN, d.CHUCUAHIEU, a.SOHOADON, a.NGAYXUATHD, a.tongtienavat, isnull(b.SOTIENCANTRU, 0) as SOTIENCANTRU,   \n " +
				"	(	select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ     \n  " +
				"				inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ     \n " +
				"		where cc.KHACHHANG_FK = d.PK_SEQ  \n " +
				"	) as ddkdTEN \n  " +
				"from HOADON a inner join CANTRUCONGNO_HOADON b on b.HOADON_FK = a.PK_SEQ \n " +
				"	inner join CANTRUCONGNO c on c.PK_SEQ = b.CANTRUCONGNO_FK \n " +
				"	inner join KhachHang d on a.KHACHHANG_FK = d.PK_SEQ  \n " +
				" where a.LOAIHOADON = 0 and a.NPP_FK = '" + obj.getNppId() + "' and a.TRANGTHAI not in ( 3, 5 ) and a.NGAYXUATHD >= '" + obj.getTungay() + "' and a.NGAYXUATHD <= '" + obj.getDenngay() + "'   " + condition +
				" \n order by a.NGAYXUATHD asc  ";
		
		/*System.out.println("---LAY BC2: " + query);*/
		ResultSet hdRs = db.get(query);
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				
				double total_sotienHD = 0;
				double total_chietkhau = 0;
				double total_phaithu = 0;
				
				while(hdRs.next())
				{
					double sotienHD = hdRs.getDouble("tongtienavat");
					double chietkhau = hdRs.getDouble("SOTIENCANTRU");
					double phaithu = sotienHD - chietkhau;
					
					total_sotienHD += sotienHD;
					total_chietkhau += chietkhau;
					total_phaithu += phaithu;
					
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);			 				
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("ddkdTEN")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("MAFAST")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);			
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("khTEN")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("CHUCUAHIEU")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getString("sohoadon")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getString("NGAYXUATHD")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);	
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(format.format(sotienHD)); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(format.format(chietkhau));  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	 
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(format.format(phaithu)); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
					
					i++;
					stt ++;
				}
				hdRs.close();
				
				//THEM DONG TONG CONG
				cells.merge(i - 1, 0, i - 1, 6);
				
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue("TỔNG CỘNG"); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.CENTRED);			 				
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(""); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.LEFT);			
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);			 				
				
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(total_sotienHD); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);	
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(total_chietkhau);  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);	 
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(total_phaithu); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell, HorizontalAlignmentType.RIGHT);
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
		
		/*Worksheets worksheets = workbook.getWorksheets();
  Worksheet worksheet = worksheets.getSheet(0);
  Cells cells = worksheet.getCells();

NumberFormat format = new DecimalFormat("#,###,###"); 

Cell cell = null;

Style style;
com.aspose.cells.Font font2 = new com.aspose.cells.Font();	
font2.setName("Times New Roman");
font2.setSize(12);

com.aspose.cells.Font font3 = new com.aspose.cells.Font();
font3.setName("Times New Roman");
font3.setSize(12);
font3.setBold(true);

int thangTRUOC = Integer.parseInt(obj.getTungay().split("-")[1]);
int namTRUOC = Integer.parseInt(obj.getTungay().split("-")[0]);
if(thangTRUOC == 1)
{
	thangTRUOC = 12;
	namTRUOC = namTRUOC - 1;
}
else
{
	thangTRUOC = thangTRUOC - 1;
}

int songayTRONGTHANG = this.Songaytrongthang(thangTRUOC, namTRUOC);
String dauthang = Integer.toString(namTRUOC) + "-" + ( thangTRUOC < 10 ? "0" + Integer.toString(thangTRUOC) : Integer.toString(thangTRUOC) ) + "-01" ; 
String cuoithang = Integer.toString(namTRUOC) + "-" + ( thangTRUOC < 10 ? "0" + Integer.toString(thangTRUOC) : Integer.toString(thangTRUOC) ) + "-" + ( songayTRONGTHANG < 10 ? "0" + Integer.toString(songayTRONGTHANG) : Integer.toString(songayTRONGTHANG) ) ; 

try
{				
	dbutils db = new dbutils();
	String query = "";

	String condition = "";
	if(obj.getDdkdId().trim().length() > 0)
	{
		condition += " and a.pk_seq in ( select khachhang_fk from KHACHHANG_TUYENBH " +
					 "								where TBH_FK in ( select pk_seq from TUYENBANHANG where DDKD_FK = '" + obj.getDdkdId() + "' ) ) ";
	}
	if(obj.getNvgnId().trim().length() > 0)
	{
		condition += " and a.pk_seq in ( select KHACHHANG_FK from NVGN_KH where NVGN_FK = '" + obj.getNvgnId() + "' ) ";
	}
	
	query = " select a.PK_SEQ, a.maFAST, a.TEN as khTEN, a.CHUCUAHIEU, '' SOHOADON, '' NGAYXUATHD, 0 as tongtienavat, 0 as SOTIENCANTRU,  " +
			"	(	select top(1) aa.TEN from DAIDIENKINHDOANH aa inner join TUYENBANHANG bb on bb.DDKD_FK = aa.PK_SEQ      " +
			"				inner join KHACHHANG_TUYENBH cc on cc.TBH_FK = bb.PK_SEQ     " +
			"		where cc.KHACHHANG_FK = a.PK_SEQ " +
			"	) as ddkdTEN " +
			" from KHACHHANG a inner join NHAPHANPHOI b on a.npp_fk = b.pk_seq " +
			" where b.pk_seq = '" + obj.getNppId() + "' " +
			"	and a.pk_seq in ( select khachhang_fk from DUYETBANDUNGGIA_KHACHHANG where duyet_fk in ( select pk_seq from DUYETBANDUNGGIA where trangthai = '1' and loaict = '0' and nam = '" + namTRUOC + "' and thang = '" + thangTRUOC + "' and NPP_FK = '" + obj.getNppId() + "' ) )  ";
	
	query += condition;
	query += " order by a.TEN asc ";
	
	ResultSet hdRs = db.get(query);
	
	if(hdRs != null)
	{
		int stt = 1;
		int i = 8;
		while(hdRs.next())
		{
			String khId = hdRs.getString("pk_seq");
			
			//DAU KY
			if( thangTRUOC == 6 && namTRUOC == 2014 )
			{
				query = "select count(*) as soDONG, kh.xuatkhau, isnull(kh.thanhtoan, 0) as thanhtoan, isnull(PT_CHIETKHAU, 0) as PT_CHIETKHAU, ( select loaiNPP from NHAPHANPHOI where pk_seq = '" + obj.getNppId() + "' ) as loaiNPP,  " +
						//"	ISNULL( ( select tongdoanhso from TICHLUYTHANG_DAUKY where khachhang_fk = '" + khIds[i] + "' ) ,0 ) as tongDSThangtruoc,  " +
						"	ISNULL( ( select tongthucthu from TICHLUYTHANG_DAUKY where khachhang_fk = '" + khId + "' ) ,0 ) as tongTHUVEThangtruoc, " +
						" 	ISNULL( ( select count(*) from DONHANG_SANPHAM where donhang_fk = '" + khId + "' and thueVAT = '5'  ), 0 ) as exits5Pt  " +
						"from KHACHHANG kh " +
						"where kh.pk_seq = '" + khId + "' and kh.KhongKyHopDong = '0' and kh.pk_seq  in ( select khachhang_fk from DUYETBANDUNGGIA_KHACHHANG where duyet_fk in ( select pk_seq from DUYETBANDUNGGIA where trangthai = '1' and loaict = '0' and nam = '" + namTRUOC + "' and thang = '" + thangTRUOC + "' and npp_fk = '" + obj.getNppId() + "' ) ) " +
						"group by kh.xuatkhau, kh.thanhtoan, PT_CHIETKHAU  ";
			}
			else
			{
				//PHAI LAY LOAI KHACH HANG TRONG THANG TRUOC
				query = "select count(*) as soDONG, isnull( PT_CHIETKHAU, 0) as PT_CHIETKHAU, isnull(kh.thanhtoan, 0) as thanhtoan, ( select loaiNPP from NHAPHANPHOI where pk_seq = '" + obj.getNppId() + "' ) as loaiNPP,  " +
						"	ISNULL( ( select sum( soluong * dongia )  as toTAL  " +
						"			  from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk " +
						"			  where a.ngayxuatHD >= '" + dauthang + "' and a.ngayxuatHD <= '" + cuoithang + "' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0' and a.khachhang_fk = '" + khIds[i] + "' ) ,0 ) as tongDSThangtruoc,  " +
						"	ISNULL( (	select sum(tongtien)  " +
						"			from " +
						"			( " +
						"				select sum(tongtienAVAT) as tongtien " +
						"				from HOADON a   " +
						"				where a.ngayxuatHD >= '" + dauthang + "' and a.ngayxuatHD <= '" + cuoithang + "' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0' and a.khachhang_fk = '" + khId + "' " +
						"			) " +
						"			total ) , 0 ) as tongTHUVEThangtruoc ,  " +
						"	ISNULL( ( select top(1) loaikhachhang from DONHANG where khachhang_fk = '" + khId + "' and trangthai != 2 and ngaynhap >= '" + dauthang + "' and ngaynhap <= '" + cuoithang + "' ), 0 ) as xuatkhau, " +
						"	ISNULL( ( select sum( tiencothue + bosung ) from DUYETTRAKHUYENMAI_DUNO where khachhang_fk = '" + khId + "'   ), 0 ) " +
							"	-  ISNULL( ( select sum( thanhtoan ) from DUYETTRAKHUYENMAI_DUNO_DONHANG_DATRA where khachhang_fk = '" + khId + "' and donhang_fk not in ( select pk_seq from DONHANG where khachhang_fk = '" + khId + "' and ngaynhap >= '" + dauthang + "' )  ), 0 ) as duno_dauky  " +
						"from KHACHHANG kh " +
						"where kh.pk_seq = '" + khId + "' and kh.KhongKyHopDong = '0' and kh.pk_seq in ( select khachhang_fk from DUYETBANDUNGGIA_KHACHHANG where duyet_fk in ( select pk_seq from DUYETBANDUNGGIA where trangthai = '1' and loaict = '0' and nam = '" + namTRUOC + "' and thang = '" + thangTRUOC + "' and NPP_FK = '" + obj.getNppId() + "' ) ) " +
						"group by kh.xuatkhau, kh.thanhtoan, PT_CHIETKHAU ";
			}
			
			System.out.println("__1.Lay Tich THANG cua KH [" + khId + "]: " + query);
			ResultSet rsCHECK = db.get(query);

			double tongTHUVEThangtruoc = 0;
			double pt_chietkhau = 0;
			double duno_dauky = 0;
			
			if(rsCHECK != null)
			{
				if(rsCHECK.next())
				{
					if( rsCHECK.getInt("soDONG") > 0 )
					{
						//tongDSThangtruoc = rsCHECK.getDouble("tongDSThangtruoc");
						tongTHUVEThangtruoc = rsCHECK.getDouble("tongTHUVEThangtruoc");

						if( rsCHECK.getString("xuatkhau").equals("0") || rsCHECK.getString("xuatkhau").equals("3") )  //Loại bán lẻ và bán lẻ, buôn
							pt_chietkhau = 8;
						else
							pt_chietkhau = 6;
						
						//DA BAO GOM VAT
						duno_dauky = -1 * rsCHECK.getDouble("duno_dauky");
					}
				}
				rsCHECK.close();
			}
			
			if( tongTHUVEThangtruoc > 0 )
			{
				double sotienHD = tongTHUVEThangtruoc;
				double chietkhau = ( tongTHUVEThangtruoc * pt_chietkhau / 100 ) + duno_dauky;
				double phaithu = sotienHD - chietkhau;
				
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);			 				
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("ddkdTEN")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("MAFAST")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);			
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("khTEN")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getString("CHUCUAHIEU")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getString("sohoadon")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(hdRs.getString("NGAYXUATHD")); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);	
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(format.format(sotienHD)); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(format.format(chietkhau));  style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);	 
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(""); 	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);		
				
				stt++;
				i++;
			}
	    }
	}
}
catch(Exception e)
{
	System.out.println("Exception In Phieu Thu: " + e.getMessage());
	e.printStackTrace();
}*/
		
	}
	
	private void setCellBorderStyle(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		//style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setHAlignment(alignment);
		style.setBorderLine(BorderType.TOP, 0);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 0);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	
	private void setCellBorderStyle2(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		//style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setHAlignment(alignment);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	
	private static int CompareDATE(String _date1, String _date2)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//Date date1 = sdf.parse("2014-10-01");
			//Date date2 = sdf.parse("2014-10-01");
			
			Date date1 = sdf.parse(_date1);
			Date date2 = sdf.parse(_date2);
			
			//System.out.println(sdf.format(date1));
			//System.out.println(sdf.format(date2));
			
			return date1.compareTo(date2);
		}
		catch (Exception e) {
			return 0;
		}
		
	}
	
	private static String getngayCUOITHANG(String ngaygiaodich) 
	{
		String[] arr = ngaygiaodich.split("-");
		String ngaycuoithang = "";
		
		switch ( Integer.parseInt(arr[1]) ) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				ngaycuoithang    = "31";
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				ngaycuoithang    = "30";
				break;
			case 2:
				if( Integer.parseInt(arr[0]) % 100 != 0 && Integer.parseInt(arr[0]) % 4 == 0 ) {
					ngaycuoithang    = "29";
				} else {
					ngaycuoithang    = "28";
				}
				break;
			default: ngaycuoithang    = "28";
		} 
		
		return ngaycuoithang;
		
	}
	
	private static String TaoBanIn()
	{
		geso.traphaco.distributor.db.sql.dbutils  db = new geso.traphaco.distributor.db.sql.dbutils();
		String query=
				"select b.NPP_FK,a.hoadon_fk,sum(a.chietkhau*(1+a.thueVAT/100)) as ck "+
						" from HOADON_CHIETKHAU a inner join HOADON b on b.PK_SEQ=a.hoadon_fk   "+
						" where  a.tichluyQUY=0 and a.HIENTHI=0 and ROUND(a.chietkhau,0)>0  "+
						"	and b.NPP_FK=100002  "+
						"group by b.NPP_FK,a.hoadon_fk ";
		ResultSet rs = db.get(query);
		try
		{
			while(rs.next())
			{
				String nppId="";
				String hoadonId="";
				String dhId="";
				String khId="";
				
				double tongcong=0;			
				double HanMucDoanhThu=0;
				String tableNAME = "NHOMSANPHAM";
				String ngayxuatHD="";
				String VUNG_FK ="";
				String loaiNPP = "";
				boolean daDUYET = false;
				double tongDSThangtruoc = 0;
				double tongTHUVEThangtruoc = 0;
				String cotLAY = "chietkhau";
				/*String tiLE = "1.05";*/
				String thanhTOAN = "0";
				double pt_chietkhau = 0;
				double chietkhau_bosung = 0;
				double dsHHBG=0;
				int daDuyet=0;
				double duno_dauky = 0;
				
				String NgayDonHang=ngayxuatHD;
				Utility util = new Utility();
				int		donhang_sau_ngay_01 = util.CompareDATE(NgayDonHang, "2015-01-01");
				int		donhang_01_02_2015 = util.CompareDATE(NgayDonHang, "2015-02-01");
				
				String NgayThang=NgayDonHang.substring(0, 7);
				String year = NgayDonHang.substring(0, 4);
				
				int thang = Integer.parseInt(NgayDonHang.split("-")[1]);
				int nam = Integer.parseInt(NgayDonHang.split("-")[0]);
				if(thang == 1)
				{
					thang = 12;
					nam = nam - 1;
				}
				else
				{
					thang = thang - 1;
				}			
				
				query=
						" select SUM(thanhtoan) as ThanhToan,DIENGIAI,isnull(NgayThang,'"+NgayThang+"') +'-01' as NgayDonHang, DATEPART(MONTH, DATEADD(MONTH,-1 ,isnull(NgayThang,'"+NgayThang+"') +'-01')) as Thang  "+ 
								" from DUYETTRAKHUYENMAI_DONHANG a where donhang_fk='"+dhId+"' and tichluyQUY=0 and round(a.thanhtoan,0)>500 "+
								" group by DIENGIAI ,NgayThang ";
				rs=db.get(query);
				while(rs.next())
				{
					NgayDonHang=rs.getString("NgayDonHang");
					//B1. Áp tích lũy tháng
					int thangTRUOC = Integer.parseInt(NgayDonHang.split("-")[1]);
					int namTRUOC = Integer.parseInt(NgayDonHang.split("-")[0]);
					if(thangTRUOC == 1)
					{
						thangTRUOC = 12;
						namTRUOC = namTRUOC - 1;
					}
					else
					{
						thangTRUOC = thangTRUOC - 1;
					}
					
					String dauthang = Integer.toString(namTRUOC) + "-" + ( thangTRUOC < 10 ? "0" + Integer.toString(thangTRUOC) : Integer.toString(thangTRUOC) ) + "-01" ;
					String cuoithang="";
					if( thangTRUOC == 6 && namTRUOC == 2014 )
					{
						query = "select count(*) as soDONG, kh.xuatkhau, isnull(kh.thanhtoan, 0) as thanhtoan, isnull(PT_CHIETKHAU, 0) as PT_CHIETKHAU, ( select loaiNPP from NHAPHANPHOI where pk_seq = '" + nppId + "' ) as loaiNPP,  " +
								"	ISNULL( ( select tongdoanhso from TICHLUYTHANG_DAUKY where khachhang_fk = '" + khId  + "' ) ,0 ) as tongDSThangtruoc,  " +
								"	ISNULL( ( select tongthucthu from TICHLUYTHANG_DAUKY where khachhang_fk = '" + khId  + "' ) ,0 ) as tongTHUVEThangtruoc, " +
								" 	ISNULL( ( select count(*) from DONHANG_SANPHAM where donhang_fk = '" + dhId + "' and thueVAT = '5'  ), 0 ) as exits5Pt  " +
								"from KHACHHANG kh " +
								"where kh.pk_seq = '" + khId  + "' and kh.KhongKyHopDong = '0' and kh.pk_seq  in ( select khachhang_fk from DUYETBANDUNGGIA_KHACHHANG where duyet_fk in ( select pk_seq from DUYETBANDUNGGIA where trangthai = '1' and loaict = '0' and nam = '" + namTRUOC + "' and thang = '" + thangTRUOC + "' and npp_fk = '" + nppId + "' ) ) " +
								"group by kh.xuatkhau, kh.thanhtoan, PT_CHIETKHAU  ";
					}
					else
					{
						String sql_HHBG="	(0) as DsHHBG ";
						
						if(donhang_01_02_2015>=0)
						{
							sql_HHBG=
									"\n			(	select sum(ROUND( round(b.SOLUONG*b.DONGIA,0) *(1+b.VAT/100.0),0))   "+
											"\n				from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ  "+ 
											"\n					inner join v_NhomSanPham c on c.sp_fk=b.SANPHAM_FK  "+
											"\n					inner join NHAPHANPHOI d on d.PK_SEQ=a.NPP_FK  "+
											"\n					inner join TINHTHANH e on e.PK_SEQ=d.TINHTHANH_FK and e.VUNG_FK=c.vung_Fk  "+
											"\n				where a.LOAIHOADON=0 and a.NGAYXUATHD>='"+dauthang+"' and c.nsp_fk=100003 and a.trangthai not in (1,3,5)  "+						
											"\n					and a.NGAYXUATHD<='"+cuoithang+"' and a.KHACHHANG_FK='"+khId +"' and d.PK_SEQ='"+nppId+"' )as DsHHBG ";
						}
						
						String sql_CANTRU="isnull(b.sotiencantru, 0)";
						if(donhang_01_02_2015>=0)
						{
							sql_CANTRU="0";
						}
						
						query = 
								"\n         select "+sql_HHBG+"  , count(*) as soDONG, isnull( PT_CHIETKHAU, 0) as PT_CHIETKHAU, isnull(kh.thanhtoan, 0) as thanhtoan, ( select loaiNPP from NHAPHANPHOI where pk_seq = '" + nppId + "' ) as loaiNPP,  " +
										"\n        	ISNULL( ( select sum(ROUND( round(b.SOLUONG*b.DONGIA,0) *(1+b.VAT/100.0),0))  as toTAL  " +
										"\n        			  from HOADON a inner join HOADON_SP b on a.pk_seq = b.hoadon_fk " +
										"\n        			  where a.ngayxuatHD >= '" + dauthang + "' and a.ngayxuatHD <= '" + cuoithang + "' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0' and a.khachhang_fk = '" + khId  + "' ) ,0 ) as tongDSThangtruoc,  " +
										"\n        	ISNULL( (	select sum(tongtien)  " +
										"\n        			from " +
										"\n        			( " +
										"\n        				select sum(tongtienAVAT) - sum("+sql_CANTRU+") as tongtien " +
										"\n        				from HOADON a left join CANTRUCONGNO_HOADON b on a.pk_seq = b.hoadon_fk  " +
										"\n        				where a.ngayxuatHD >= '" + dauthang + "' and a.ngayxuatHD <= '" + cuoithang + "' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0' and a.khachhang_fk = '" + khId  + "' " +
										"\n        			) " +
										"\n        			total ) , 0 ) as tongTHUVEThangtruoc ,  " +
										"\n        	ISNULL( ( select count(*) from DONHANG_SANPHAM where donhang_fk = '" + dhId + "' and thueVAT = '5'  ), 0 ) as exits5Pt, " +
										"\n        	ISNULL( ( select top(1) loaikhachhang from DONHANG where khachhang_fk = '" + khId  + "' and trangthai != 2 and ngaynhap >= '" + dauthang + "' and ngaynhap <= '" + cuoithang + "' ), 0 ) as xuatkhau, " +
										"\n        	ISNULL( ( select sum(thanhtien) from DONHANG_CHIETKHAUBOSUNG where maloai like N'%CT%' and donhang_fk != '" + dhId + "' and donhang_fk in ( select pk_seq from DONHANG where ngaynhap >= '" + dauthang + "' and ngaynhap <= '" + cuoithang + "' and trangthai != '2' and khachhang_fk = '" + khId  + "'  ) ), 0 ) as chietkhau_bosung,  " +
										"\n        	ISNULL( ( select sum( tiencothue + bosung ) from DUYETTRAKHUYENMAI_DUNO where khachhang_fk = '" + khId  + "' and tichluyQUY = '0' ), 0 ) " +
										"\n        	-  ISNULL( ( select sum( thanhtoan ) from DUYETTRAKHUYENMAI_DUNO_DONHANG_DATRA where donhang_Fk!='"+dhId+"' and khachhang_fk = '" + khId  + "' and tichluyQUY = '0'   ), 0 ) as duno_dauky ,  " +
										"\n         (	select COUNT(*) as DaDuyet from DUYETBANDUNGGIA_KHACHHANG where duyet_fk in  ( select pk_seq from DUYETBANDUNGGIA where trangthai = '1'  "+
										"\n        																			and loaict = '0' and nam = '"+namTRUOC+"' and thang = '"+thangTRUOC+"' and NPP_FK = '"+nppId+"' ) and khachhang_fk='"+khId +"' ) as DaDuyet "+							
										"\n        from KHACHHANG kh " +
										"\n        where kh.pk_seq = '" + khId  + "' and kh.KhongKyHopDong = '0' " +
										"\n        	and ( kh.pk_seq in ( select khachhang_fk from DUYETBANDUNGGIA_KHACHHANG where duyet_fk in ( select pk_seq from DUYETBANDUNGGIA where trangthai = '1' and loaict = '0' and nam = '" + namTRUOC + "' and thang = '" + thangTRUOC + "' and NPP_FK = '" + nppId + "' ) ) " +
										"\n        	   or (  ROUND (	ISNULL( ( select sum( tiencothue + bosung ) from DUYETTRAKHUYENMAI_DUNO where khachhang_fk = '" + khId  + "' and tichluyQUY = '0' ), 0 ) " +
										"\n        						- ISNULL( ( select sum( thanhtoan ) from DUYETTRAKHUYENMAI_DUNO_DONHANG_DATRA where donhang_fk!='"+dhId+"' and khachhang_fk = '" + khId  + "' and tichluyQUY = '0'   ), 0 ) , 0 ) != 0  ) )			 " ;
						/*	if(donhang_01_02_2015>=0)
										query+=
									"\n         AND	ISNULL( (	select sum(tongtien)  " +
									"			from " +
									"\n        			( " +
									"\n        				select sum(tongtienAVAT) - sum("+sql_CANTRU+") as tongtien " +
									"\n        				from HOADON a left join CANTRUCONGNO_HOADON b on a.pk_seq = b.hoadon_fk  " +
									"\n        				where a.ngayxuatHD >= '" + dauthang + "' and a.ngayxuatHD <= '" + cuoithang + "' and a.trangthai in ( 2, 4 ) and a.loaihoadon = '0' and a.khachhang_fk = '" + khId  + "' " +
									"\n        			) " +
									"\n        			total ) , 0 ) > ( select isnull(HanMucDoanhThu,0) from NhaPhanPhoi where pk_Seq='"+nppId+"' ) ";*/
						query+=
								"\n    group by kh.xuatkhau, kh.thanhtoan, PT_CHIETKHAU ";
					}
					
					/*System.out.println("[InKhauTruCKThangSvl]"+query);*/
					
					ResultSet rsKQ=db.get(query);
					while(rsKQ.next())
					{
						tongDSThangtruoc = rsKQ.getDouble("tongDSThangtruoc");
						tongTHUVEThangtruoc = rsKQ.getDouble("tongTHUVEThangtruoc");
						daDuyet=rsKQ.getInt("daDUYET");
						if(daDuyet==0)
						{
							tongTHUVEThangtruoc=0;
						}								
						loaiNPP = rsKQ.getString("loaiNPP");
						if( rsKQ.getString("xuatkhau").equals("0") || rsKQ.getString("xuatkhau").equals("3") )  //Loại bán lẻ và bán lẻ, buôn
							cotLAY = "chietkhau_khle";
						else
							cotLAY = "chietkhau";
						thanhTOAN = rsKQ.getString("thanhtoan");
						pt_chietkhau = rsKQ.getDouble("PT_CHIETKHAU");
						//DA BAO GOM VAT
						duno_dauky = (-1)*rsKQ.getDouble("duno_dauky");
						//chietkhau_bosung = rsCHECK.getDouble("chietkhau_bosung");
						chietkhau_bosung = 0;  //Không dùng CK bổ sung nữa
						dsHHBG=rsKQ.getDouble("DsHHBG");
					}
					rsKQ.close();
					
					donhang_sau_ngay_01 = CompareDATE(NgayDonHang, "2014-10-01");
					/**
					 * 4. Chiết khấu tháng: Ràng thêm điều kiện nếu Doanh số HH-BG >70% 
					 * thì không được hưởng CK tháng. Đối với Chợ Hapu, Quận 10 điều kiện này là >40%
					 **/
					int soDong=0;
					if(donhang_01_02_2015>=0 && tongDSThangtruoc>0 )
					{
						query=
								"	select COUNT(*) as SoDong from MucDat "+   
										"	where ("+dsHHBG+"/"+tongDSThangtruoc+")*100>=TuMuc and ("+dsHHBG+"/"+tongDSThangtruoc+")*100<=DenMuc  "+
										"	and npp_fk='"+nppId+"'   ";
						/*System.out.println("____Rang_CK___"+query);*/
						rsKQ=db.get(query);
						while(rsKQ.next())
						{
							soDong=rsKQ.getInt("SoDong");
						}
						if(rsKQ!=null)rsKQ.close();
						
						if(soDong<=0)
						{
							tongTHUVEThangtruoc=0;
						}
						//daDUYET=false;
					}
					int tienCONGNO_CONLAI=0;
					int exit5PT=1;
					/*					System.out.println("[daDUYET]"+daDuyet+"[tongDSThangtruoc]"+tongDSThangtruoc+"[pt_chietkhau]"+pt_chietkhau+"[tienCONGNO_CONLAI]"+tienCONGNO_CONLAI+"[tong]"+tongTHUVEThangtruoc);*/
					/*if(daDUYET && ( tongDSThangtruoc >= 0 ) && exit5PT > 0 && ( pt_chietkhau <= 0 ) && ( tienCONGNO_CONLAI <= 0 ) )*/  
					{
						//CHIẾT KHẤU THÁNG TỪ 01-10-2014 không được vượt tổng giá trị đơn hàng -> trả hết trong đơn đầu tiên
						String dauthangHT = NgayDonHang.split("-")[0] + "-" + NgayDonHang.split("-")[1] + "-01";
						String cuoithangHT = NgayDonHang.split("-")[0] + "-" + NgayDonHang.split("-")[1] + "-" + getngayCUOITHANG(NgayDonHang);
						if( thangTRUOC == 6 && namTRUOC == 2014 )
						{
							query = "select tct.pk_seq, tct.scheme, '" + khId + "' as khId,  " +
									"	sum( " + tongTHUVEThangtruoc + " * tc." + cotLAY + " / 100 )  " +
									"	- isnull(( select SUM(thanhtoan) from DUYETTRAKHUYENMAI_DONHANG where khachhang_fk = '" + khId + "' and duyetkm_fk = tct.pk_seq  ), 0)  as conLAI  " +
									"from " +
									"( " +
									"	select pk_seq as NSP_FK, 100 as tongGiatri, 50  as phantram " +
									"	from NHOMSANPHAM " +
									") " +
									"PT_NHOM inner join TIEUCHITHUONGTL_TIEUCHI tc on PT_NHOM.NSP_FK = tc.nhomsanpham_fk " +
									"		and tc.tumuc <= PT_NHOM.phantram and PT_NHOM.phantram < tc.denmuc " +
									"		inner join TIEUCHITHUONGTL tct on tc.thuongtl_fk = tct.pk_seq " +
									"where tct.thang = '" + thangTRUOC + "' and tct.nam = '" + namTRUOC + "' and tct.trangthai = '1' and tct.loaict = '0' " +
									" and tct.pk_seq in ( select thuongtl_fk from TIEUCHITHUONGTL_LOAINPP where loainpp = ( select loaiNPP from NHAPHANPHOI where pk_seq = '" +nppId + "' ) )  " +
									"group by tct.pk_seq, tct.scheme " +
									"having count(NSP_FK) >= 2 " +
									"	and sum( " + tongTHUVEThangtruoc + " * tc.chietkhau / 100 )  " +
									"		- isnull(( select SUM(thanhtoan) from DUYETTRAKHUYENMAI_DONHANG where khachhang_fk = '" + khId + "' and duyetkm_fk = tct.pk_seq ), 0) > 0  ";
						}
						else
						{
							//TAM THOI NHA HAI PHONG + HCM THI KHONG XET THOI GIAN, CO LA CHO RA
							//THANG KHONG CHIA THEO NHOM
							query = 
									"\n        select tct.pk_seq, tct.scheme, '" + khId + "' as khId,sum("+cotLAY+") as pt_chietkhau,  " +
											"\n        	sum( " + tongTHUVEThangtruoc + " * tc." + cotLAY + " / 100 )  " +
											"\n        	- isnull(( select SUM( thanhtoan / ( 1 + ptTHUE / 100 ) ) from DUYETTRAKHUYENMAI_DONHANG where  isnull(NgayThang,(select  SUBSTRING(ngaynhap,0,8) from DONHANG where PK_SEQ = donhang_fk)) ='"+NgayThang+"' and  khachhang_fk = '" + khId + "' and duyetkm_fk = tct.pk_seq and donhang_fk in ( select pk_seq from DONHANG where ngaynhap >= '" + dauthangHT + "'  and trangthai != '2' and khachhang_fk = '" + khId + "' )  ), 0)  " +
											"\n        	- ISNULL( ( select sum( thanhtoan / 1.05 ) from DUYETTRAKHUYENMAI_DUNO_DONHANG_DATRA where   isnull(NgayThang,(select  SUBSTRING(ngaynhap,0,8) from DONHANG where PK_SEQ = donhang_fk)) ='"+NgayThang+"'  and khachhang_fk = '" + khId + "' and tichluyQUY = '0' and donhang_fk in ( select pk_seq from DONHANG where ngaynhap >= '" + dauthangHT + "'  and trangthai != '2' and khachhang_fk = '" + khId + "' )  ), 0 )  as conLAI  " +
											"\n        from " +
											"\n        ( " +
											"\n        	select pk_seq as NSP_FK, 100 as tongGiatri, 50  as phantram " +
											"\n        	from NHOMSANPHAM " +
											"\n        ) " +
											"\n        PT_NHOM inner join TIEUCHITHUONGTL_TIEUCHI tc on PT_NHOM.NSP_FK = tc.nhomsanpham_fk " +
											"\n        		and tc.tumuc <= PT_NHOM.phantram and PT_NHOM.phantram < tc.denmuc " +
											"\n        		inner join TIEUCHITHUONGTL tct on tc.thuongtl_fk = tct.pk_seq " +
											"\n        where tct.nam = '" + namTRUOC + "' and tct.trangthai = '1' and tct.loaict = '0' " +
											"\n         and tct.pk_seq in ( select thuongtl_fk from TIEUCHITHUONGTL_LOAINPP where loainpp = ( select loaiNPP from NHAPHANPHOI where pk_seq = '" + nppId + "' ) )  " +
											"\n        group by tct.pk_seq, tct.scheme " +
											"\n        having count(NSP_FK) >= 2 ";
						}
						rsKQ=db.get(query);
						while(rsKQ.next())
						{
							pt_chietkhau=rsKQ.getDouble("pt_chietkhau");
						}
						if(rsKQ!=null)rsKQ.close();
						
						query=
								"	INSERT INTO  "+
										"	[InBanBuTruCongNo]([HoaDon_FK],[KhachHang_Fk],[Npp_FK],[CotLay],[LoaiThanhToan],[DuNoDauKy],[DoanhSo],[DoanhThu],[DoanhSoBOGA], "+
										"			[DonHang_FK],[PtChietKhau],[HanMucDoanhThu],[BOGA],[DaDuyet],VUNG_FK,[NGAYTHANG],[NgayXuatHD],[NgayDonHang]) " +
										" select '"+hoadonId+"','"+khId+"','"+nppId+"','"+cotLAY+"','"+thanhTOAN+"','"+duno_dauky+"','"+tongDSThangtruoc+"','"+tongTHUVEThangtruoc+"','"+dsHHBG+"','"+dhId+"','"+pt_chietkhau+"'," +
										" '"+HanMucDoanhThu+"' as HanMuc,(select  (select REPLACE((sELECT distinct xx.SP_FK as [data()]  FROM "+tableNAME+"_SANPHAM xx  where xx.NSP_FK=a.PK_SEQ   FOR XML PATH('') ) ,' ',',')) as spNhom    "+
										"									from NHOMSANPHAM a      "+
										"									WHERE A.PK_SEQ=100003  "+")  ,'"+daDuyet+"','"+VUNG_FK+"','"+NgayThang+"','"+ngayxuatHD+"','"+NgayDonHang+"' ";
						db.update(query);
					}
				}
				
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void main(String[] arg)
	{
		TaoBanIn();
	}
	
	
}
