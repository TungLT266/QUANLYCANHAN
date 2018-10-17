package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.kho.KhoNewSvl;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Label;

import com.cete.dynamicpdf.text.dd;
import com.oreilly.servlet.MultipartRequest;

public class UploadTuyenBanHangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public UploadTuyenBanHangSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		obj.setuserId(userId);
		obj.init();

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/UploadTuyenBanHang.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();

		String userId = request.getParameter("userId");
		IStockintransit obj = new Stockintransit();
		obj.setuserId(userId);
		Utility util = new Utility();

		obj.setvungId(util.antiSQLInspection(request.getParameter("vungId")) == null ? "" : util.antiSQLInspection(request.getParameter("vungId")));

		obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId")) == null ? "" : util.antiSQLInspection(request.getParameter("khuvucId")));

		obj.setnppId(util.antiSQLInspection(request.getParameter("nppId")) == null ? "" : util.antiSQLInspection(request.getParameter("nppId")));

		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=LOI_UPLOAD.xls");
			WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());

			dbutils db = new dbutils();
			MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");

			userId = util.antiSQLInspection(multi.getParameter("userId"));
			obj.setuserId(userId);
			obj.setvungId(util.antiSQLInspection(multi.getParameter("vungId")) == null ? "" : util.antiSQLInspection(multi.getParameter("vungId")));

			obj.setkhuvucId(util.antiSQLInspection(multi.getParameter("khuvucId")) == null ? "" : util.antiSQLInspection(multi.getParameter("khuvucId")));

			obj.setnppId(util.antiSQLInspection(multi.getParameter("nppId")) == null ? "" : util.antiSQLInspection(multi.getParameter("nppId")));
			System.out.println("NPP: "+obj.getnppId());
			Enumeration files = multi.getFileNames();
			String filenameu = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filenameu = multi.getFilesystemName(name);
				System.out.println("name ___:   " + name);
				;
			}

			String filename = "C:\\java-tomcat\\data\\" + filenameu;
			if (filename.length() > 0)
			{
				// doc file excel
				File file = new File(filename);
				System.out.println("filename  " + file);
				Workbook workbook;

				int indexRow = 5;

				try
				{
					String sott = "";
					String makh = "";
					String tencuahieu = "";
					String chucuahieu = "";
					String diachi = "";
					String sohopdong = "";
					String quanhuyen = "";
					String tinhthanh = "";
					String phuongxa = "";
					String sodienthoai = "";
					String masothue = "";
					String loaicuahang = "";
					String ngaysinh = "";
					String tuyenbanhang = "";
					String tanso = "";
					String kbh_fk = "";
					String vtch_fk = "";
					String hch_fk = "";
					String lch_fk = "";
					String chanhxe = "";
					String phuongthucgiaohang = "";
					String tenxuathd = "";
					String capdogiaohang = "";
					String ghichu = "";
					String tennguoitt = "";
					String dienthoainguoitt = "";
					String nganhang = "";
					String chinhanh = "";
					String matdv = "";
					String nguoidaidien = "";
					String diaban = "";
					String nppXuatHD = "";
					String tenkyHD = "";
					String congtacvien = "";
					String NPPxuatkho = "";
					String thoihanno = "";
					String ghinhancongno = "";
					String hanmucno = "";
					String quocgia = "";
					String insovisa = "";
					String insohd = "";
					String innhannhapkhau = "";
					String innhasx = "";
					String banhangquadienthoai = "";
					String sotknganhang = "";
					String taikhoanketoan = "";
					String chucvu = "";
					String tructhuoc = "";
					String khachhangdacbiet = "";
					String xungquanhbenhvien = "";
					String benhvien = "";
					String taikhoankyquy = "";
					String khodathang = "";
					String hopdong = "";
					String nguoilienhedathang = "";
					String nguoimuahang = "";
					String ngaykyhd = "";
					String Ptthanhtoan = "";
					String songayvanchuyen = "";
					String phantramchietkhau = "";
					String nhanviengiaonhan = "";
					String diachichanhxe = "";
					String sodienthoaichanhxe = "";
					String chietkhauthanhtoan = "";
					String nhomkhachhang = "";
					String diachixuathd = "";
					String diachigiaohang = "";
					String sdtnguoidathang = "";
					String tachhoadontheovat = "";
					String tbh_fk = "";
					String thanhtoan="";
					String	ThanhToanQuy="";
					String khoanmucchiphi  = "";
					String MST_CaNhan="";
					String cmnd = "";
					ResultSet rs;
					System.out.println(file);
					workbook = Workbook.getWorkbook(file);
					Sheet[] sheet1 = workbook.getSheets();

					System.out.println("[SoSheet]"+sheet1.length);

					Hashtable<String, String> htp_hch = gethtpHangcuahang(db);
					Hashtable<String, String> htp_lch = gethtpLoaicuahang(db);
					Hashtable<String, String> htp_vtch = gethtpVitricuahang(db);
					Hashtable<String, String> htp_Tinhthanh = gethtpTinhThanh(db);
					Hashtable<String, String> htp_quanhuyen = gethtpQuanHuyen(db);
					Hashtable<String, String> htp_ploai = getPhanLoai(db);
					Hashtable<String, String> htp_thanhtoan = getThanhToan(db);
					Hashtable<String, String> htp_kbh = getKenhBanHang(db);
					Hashtable<String, String> htp_phuongxa = gethtpPhuongXa(db);
					Hashtable<String, String> htp_diaban = gethtpDiaban(db);
					Hashtable<String, String> htp_Npp = gethtpNPP(db);
					Hashtable<String, String> htp_Nganhang = gethtpNganHang(db);
					Hashtable<String, String> htp_ChiNhanh = gethtpChiNhanh(db);
					Hashtable<String, String> htp_TaiKhoan = gethtpTaikhoanKT(db,obj.getnppId());
					Hashtable<String, String> htp_KhoanMuc = gethtpKhoanMuc(db);
					
					db.getConnection().setAutoCommit(false);
					boolean error = false;
					boolean flag = true;
					int sokh = 0;
					// Kiểm tra các mã nhân viên này có đúng mã và phải của nhà
					// phân phối đã chọn ko?
					if (!error)
					{
						for (int t = 0; t < sheet1.length; t++)
						{
							// TAO RA 1 SHEET LUU THONG TIN UPLOAD KHONG THANH
							// CONG
							WritableSheet sheetwrite = w.createSheet(sheet1[t].getName(), t);
							sheetwrite.addCell(new Label(0, 0, "Thông tin upload lỗi ! Vui lòng kéo xuống dưới để xem !"));
							Sheet sheet = sheet1[t];
								
							String ddkdid = "";
						

							Cell[] cells;
							System.out.println("Ten: "+sheet.getRows());

							indexRow = 5;
							for (int i = indexRow;i <sheet1[t].getRows(); i++)
							{
								cells = sheet.getRow(i);
								if (cells.length > 0)
								{
									if (cells[0].getContents().toString().length() > 0)
									{
										sott = sheet.getCell(0, i).getContents().trim();
										makh = sheet.getCell(1, i).getContents().trim();
										tencuahieu = sheet.getCell(2, i).getContents().trim().replace("'", "''");
										kbh_fk = sheet.getCell(3, i).getContents().trim();
										//kbh_fk = htp_kbh.get(kbh_fk);
										hch_fk = sheet.getCell(4, i).getContents().trim();
										hch_fk = htp_hch.get(hch_fk)==null?"":htp_hch.get(hch_fk);

										lch_fk = sheet.getCell(5, i).getContents().trim();
										lch_fk = htp_lch.get(lch_fk)==null?"":htp_lch.get(lch_fk);
										
										sodienthoai = sheet.getCell(6, i).getContents().trim().trim().replace("'", "''");;
										diachi = sheet.getCell(7, i).getContents().trim().replace("'", "''");
										masothue =sheet.getCell(8, i).getContents().trim().trim().replace("'", "''");
										cmnd =  sheet.getCell(9, i).getContents().trim().trim().replace("'", "''");
										chucuahieu = sheet.getCell(10, i).getContents().trim();


										/*loaicuahang = sheet.getCell(5, i).getContents().trim();
										loaicuahang = htp_ploai.get(loaicuahang.trim());
										 */
										tinhthanh = sheet.getCell(11, i).getContents().trim();
										tinhthanh = htp_Tinhthanh.get(tinhthanh)==null?"":htp_Tinhthanh.get(tinhthanh);

										quanhuyen = sheet.getCell(12, i).getContents().trim();
										quanhuyen = htp_quanhuyen.get(quanhuyen)==null?"":htp_quanhuyen.get(quanhuyen);

										phuongxa = sheet.getCell(13, i).getContents().trim();
										phuongxa = htp_phuongxa.get(phuongxa)==null?"":htp_phuongxa.get(phuongxa);

										chanhxe =  sheet.getCell(14, i).getContents().trim().trim().replace("'", "''");

										phuongthucgiaohang =  sheet.getCell(15, i).getContents().trim().trim().replace("'", "''");

										tenxuathd = sheet.getCell(16, i).getContents().trim().trim().replace("'", "''");
										capdogiaohang =  sheet.getCell(17, i).getContents().trim().trim().replace("'", "''");
										ghichu =  sheet.getCell(18, i).getContents().trim().trim().replace("'", "''");
										tennguoitt	=  sheet.getCell(19, i).getContents().trim().trim().replace("'", "''");
										dienthoainguoitt =   sheet.getCell(20, i).getContents().trim().trim().replace("'", "''");
										nganhang =  sheet.getCell(21, i).getContents().trim().trim().replace("'", "''");
										
										nganhang = htp_Nganhang.get(nganhang)==null?"":htp_Nganhang.get(nganhang);
										
										chinhanh  =  sheet.getCell(22, i).getContents().trim().trim().replace("'", "''");
										chinhanh = htp_ChiNhanh.get(chinhanh)==null?"":htp_ChiNhanh.get(chinhanh);
										matdv =  sheet.getCell(23, i).getContents().trim().trim().replace("'", "''");
										// không đọc tên Tdv
										ngaysinh = sheet.getCell(24, i).getContents().trim().trim().replace("'", "''");
										nguoidaidien = sheet.getCell(25, i).getContents().trim().trim().replace("'", "''");
										diaban =  sheet.getCell(26, i).getContents().trim().trim().replace("'", "''");
										diaban = htp_diaban.get(diaban)==null?"":htp_diaban.get(diaban);
										nppXuatHD = sheet.getCell(27, i).getContents().trim().trim().replace("'", "''");
										nppXuatHD = htp_Npp.get(nppXuatHD)==null?"": htp_Npp.get(nppXuatHD);

										tenkyHD = sheet.getCell(28, i).getContents().trim().trim().replace("'", "''");
										NPPxuatkho =  sheet.getCell(29, i).getContents().trim().trim().replace("'", "''");
										NPPxuatkho = htp_Npp.get(NPPxuatkho)==null?"":htp_Npp.get(NPPxuatkho);

										congtacvien =  sheet.getCell(30, i).getContents().trim().trim().replace("'", "''");

										diachigiaohang =  sheet.getCell(31, i).getContents().trim().trim().replace("'", "''");
										thoihanno =  sheet.getCell(32, i).getContents().trim().trim().replace("'", "''");
										ghinhancongno =  sheet.getCell(33, i).getContents().trim().trim().replace("'", "''");
										hanmucno =  sheet.getCell(34, i).getContents().trim().trim().replace("'", "''");
										tuyenbanhang =  sheet.getCell(35, i).getContents().trim().trim().replace("'", "''");
										tanso =  sheet.getCell(36, i).getContents().trim().trim().replace("'", "''");
										quocgia =  sheet.getCell(37, i).getContents().trim().trim().replace("'", "''");
										insovisa =  sheet.getCell(38, i).getContents().trim().trim().replace("'", "''");
										insohd =  sheet.getCell(39, i).getContents().trim().trim().replace("'", "''");
										innhannhapkhau =  sheet.getCell(40, i).getContents().trim().trim().replace("'", "''");
										innhasx =  sheet.getCell(41, i).getContents().trim().trim().replace("'", "''");
										banhangquadienthoai =  sheet.getCell(42, i).getContents().trim().trim().replace("'", "''");
										sotknganhang =  sheet.getCell(43, i).getContents().trim().trim().replace("'", "''");
										taikhoanketoan =  sheet.getCell(44, i).getContents().trim().trim().replace("'", "''");
										taikhoanketoan = htp_TaiKhoan.get(taikhoanketoan)==null?"":htp_TaiKhoan.get(taikhoanketoan);
										nguoidaidien =  sheet.getCell(45, i).getContents().trim().trim().replace("'", "''");
										chucvu =  sheet.getCell(46, i).getContents().trim().trim().replace("'", "''");
										tructhuoc =  sheet.getCell(47, i).getContents().trim().trim().replace("'", "''");
										MST_CaNhan =  sheet.getCell(48, i).getContents().trim().trim().replace("'", "''");
										khachhangdacbiet =  sheet.getCell(49, i).getContents().trim().trim().replace("'", "''");
										xungquanhbenhvien =  sheet.getCell(50, i).getContents().trim().trim().replace("'", "''");
										benhvien =  sheet.getCell(51, i).getContents().trim().trim().replace("'", "''");
										taikhoankyquy =  sheet.getCell(52, i).getContents().trim().trim().replace("'", "''");
										taikhoankyquy = htp_TaiKhoan.get(taikhoankyquy)==null?"":htp_TaiKhoan.get(taikhoankyquy);
										khodathang =  sheet.getCell(53, i).getContents().trim().trim().replace("'", "''");
										sohopdong =  sheet.getCell(54, i).getContents().trim().trim().replace("'", "''");
										nguoilienhedathang  =  sheet.getCell(55, i).getContents().trim().trim().replace("'", "''");
										nguoimuahang =  sheet.getCell(56, i).getContents().trim().trim().replace("'", "''");
										ngaykyhd =  sheet.getCell(57, i).getContents().trim().trim().replace("'", "''");
										Ptthanhtoan =  sheet.getCell(58, i).getContents().trim().trim().replace("'", "''");
										songayvanchuyen =  sheet.getCell(59, i).getContents().trim().trim().replace("'", "''");
										phantramchietkhau =  sheet.getCell(60, i).getContents().trim().trim().replace("'", "''");
										nhanviengiaonhan =  sheet.getCell(61, i).getContents().trim().trim().replace("'", "''");
										diachichanhxe =  sheet.getCell(62, i).getContents().trim().trim().replace("'", "''");
										sodienthoaichanhxe =  sheet.getCell(63, i).getContents().trim().trim().replace("'", "''");
										chietkhauthanhtoan =  sheet.getCell(64, i).getContents().trim().trim().replace("'", "''");
										nhomkhachhang =  sheet.getCell(65, i).getContents().trim().trim().replace("'", "''");
										diachixuathd =  sheet.getCell(66, i).getContents().trim().trim().replace("'", "''");
										sdtnguoidathang = sheet.getCell(67, i).getContents().trim().trim().replace("'", "''");
										tachhoadontheovat = sheet.getCell(68, i).getContents().trim().trim().replace("'", "''");
										khoanmucchiphi = sheet.getCell(69, i).getContents().trim().trim().replace("'", "''");
										khoanmucchiphi =  htp_KhoanMuc.get(khoanmucchiphi)==null?"":htp_KhoanMuc.get(khoanmucchiphi);
									


										Cell valueCell = sheet.getCell(25, i);
										if (cells[25].getType() == CellType.DATE) 
										{  
											DateCell dCell = (DateCell) valueCell;  
											TimeZone gmtZone = TimeZone.getTimeZone("GMT");  
											DateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd");  
											destFormat.setTimeZone(gmtZone);  
											ngaysinh = destFormat.format(dCell.getDate());  
										}   
										valueCell = sheet.getCell(58, i);
										if (cells[58].getType() == CellType.DATE) 
										{  
											DateCell dCell = (DateCell) valueCell;  
											TimeZone gmtZone = TimeZone.getTimeZone("GMT");  
											DateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd");  
											destFormat.setTimeZone(gmtZone);  
											ngaykyhd = destFormat.format(dCell.getDate());  
										}   
										sokh++;
										/*sohopdong = sheet.getCell(10, i).getContents().trim();


										thanhtoan = sheet.getCell(12, i).getContents().trim();
										thanhtoan = htp_thanhtoan.get(thanhtoan);



										
										tuyenbanhang = sheet.getCell(14, i).getContents().trim();		

										tanso = sheet.getCell(15, i).getContents().trim();*/
										String errror = "";
										if(makh.equals("N0652002"))
											System.out.println("Mafast: "+makh);	
										if (makh.trim().equals(""))
										{
											errror = errror + " Mã khách hàng trong hàng " + (indexRow + 1) + " không hợp lệ ";
										}
										
										if(tuyenbanhang == null)
										{
											errror = errror + "Tuyến bán hàng trong hàng " + (indexRow + 1) + " không hợp lệ ";
										}else
											if(tuyenbanhang != null)
											{
												if(tuyenbanhang.length() <= 0)
												{
													errror = errror + "Tuyến bán hàng trong hàng " + (indexRow + 1) + " không hợp lệ ";
													
												}
											}
											
										
										
										if(tanso == null)
										{
											errror = errror + "Tần số trong hàng " + (indexRow + 1) + " không hợp lệ ";
										}
										else
											if(tanso != null)
											{
												if(tanso.length() <= 0)
												{
													errror = errror + "Tần số trong hàng " + (indexRow + 1) + " không hợp lệ ";
													
												}
											}
										
										
										
										String id  ="";	
										if (!errror.equals(""))
										{
											
											sheetwrite.addCell(new Label(0, i, errror));
											flag = false;
										} else
										{
											
											// kiem tra khach hang,co thi cap nhat.

											String sql = 
													"select kh.pk_seq from khachhang  kh " + 
															" where kh.npp_fk=" + obj.getnppId() + " and MaFAST='" + makh + "'";
											rs = db.get(sql);
											if (rs.next())
											{
												System.out
														.println("1.Update");
												sql = 
														"update khachhang  set MST_CaNhan='"+MST_CaNhan+"',ten=N'" + tencuahieu + "' ,dienthoai=N'"+ sodienthoai + "',diachi=N'" + diachi + "',tinhthanh_fk= " + (tinhthanh.length() <= 0?null:tinhthanh) + ",quanhuyen_fk=" + (quanhuyen.length() <= 0?null:quanhuyen) + " ,phuongxa_fk="+(phuongxa.length() <= 0?null:phuongxa)+",chucuahieu=N'" + chucuahieu + "', "+
																"\n NgaySinh='"+ngaysinh+"',MaHD='"+sohopdong+"',MaSoThue='"+masothue+"',lch_fk = "+(lch_fk.length() <= 0?null:lch_fk)+",hch_fk = "+(hch_fk.length() <= 0?null:hch_fk)+",TenKyHD=N'"+tenkyHD+"',NgaySua='"+getDateTime()+"',NguoiSua='"+userId+"',cmnd = '"+cmnd+"',chanhxe = N'"+chanhxe+"',capdogiaohang = "+(capdogiaohang.length() <= 0?null:capdogiaohang)+",ghichu = N'"+ghichu+"',TENNGUOITT = '"+tennguoitt+"', "
																+ "\n DIENTHOAINGUOITT = '"+dienthoainguoitt+"', nganhang_fk = "+(nganhang.length() <= 0 ?null:nganhang)+",chinhanh_fk = "+(chinhanh.length() <= 0 ?null:chinhanh)+","
																		+ " nguoidaidien ='"+nguoidaidien+"',diaban ="+(diaban.length() <= 0 ? null:diaban)+",NPPXHD_FK ="+(nppXuatHD.length() <= 0 ? null:nppXuatHD)+", NPPXK_FK = "+(NPPxuatkho.length() <= 0 ? null:NPPxuatkho)+", phuongthucgiaohang = '"+phuongthucgiaohang+"', thoihanno = "+(thoihanno.length() <= 0?null:thoihanno)+", ghinhancongno = '"+ghinhancongno+"', hanmucno = "+(hanmucno.length() <= 0?null:hanmucno)+", "
																+ "\n quocgia_fk = "+(quocgia.length() <= 0 ?null:quocgia)+", insoVISA = '"+insovisa+"', insoHOPDONG = '" + insohd + "', innhaNK = '" + innhannhapkhau + "', innhaSX = '" + innhasx + "', inbhDT = '" + banhangquadienthoai + "',sotaikhoan ='"+sotknganhang+"', TaiKhoan_FK = "+(taikhoanketoan.length() <= 0 ?null:taikhoanketoan)+",chucvu  = '"+chucvu+"', ISDACBIET = '"+khachhangdacbiet+"', ISXQBENHVIEN = '"+xungquanhbenhvien+"',  benhvien_fk = "+(benhvien.length() <= 0 ?null:benhvien)+", TAIKHOANKQ_FK = "+(taikhoankyquy.length() <= 0?null:taikhoankyquy)+", "
																+ "\n Kho_Fk =  "+(khodathang.length() <= 0?null:khodathang)+",KhongKyHopDong = '"+(sohopdong.length() >=0 ?"1":"0")+"', NGUOI_LIENHE_DH = '"+nguoilienhedathang+"',NgayKyHD = '"+ngaykyhd+"', HINHTHUCTT_FK = "+(Ptthanhtoan.length() <= 0 ? null:Ptthanhtoan)+",songayvanchuyen = "+(songayvanchuyen.length() <= 0 ?null:songayvanchuyen)+", pt_chietkhau = "+(phantramchietkhau.length() <= 0 ?null:phantramchietkhau)+", diachi_chanhxe = '"+diachichanhxe+"', CKTHANHTOAN ="+(chietkhauthanhtoan.length() <= 0?null:chietkhauthanhtoan)+",diachiGiaohang = N'"+diachigiaohang+"',sdt_nguoidathang = '"+sdtnguoidathang+"',tachhoadonTheoVAT = '"+tachhoadontheovat+"',kmcp_fk ="+(khoanmucchiphi.length() <= 0?null:khoanmucchiphi)+" "+
																" where npp_fk=" + obj.getnppId().trim() + " and maFAST='" + makh.trim() + "'";
												//System.out.println("update :" + sql);
												if(flag)
												if (!db.update(sql))
												{
													System.out.println("Khong thanh cong do :" + sql);
												
													flag = false;
													
													sheetwrite.addCell(new Label(0, i, "Cập nhật khách hàng không thành công kiểm tra thông tin trong file upload  :" + sql));
												}

												sql = "update KHACHHANG set timkiem = dbo.ftBoDau(maFAST),timkiem_ten = dbo.ftBoDau(ten),timkiem_diachi = dbo.ftBoDau(diachi),timkiem_tenhoadon = dbo.ftBoDau(N'"+tenxuathd+"')  where npp_fk=" + obj.getnppId().trim() + " and maFAST='" + makh.trim() + "' ";
												if(flag)
												if(!db.update(sql))
												{
													System.out.println("Khong thanh cong do :" + sql);
													flag = false;
													sheetwrite.addCell(new Label(0, i, "Cập nhật khách hàng không thành công do  :" + sql));
												}			
												if(matdv.length() > 0)
												{
													sql = "delete KhachHang_DaiDienKinhDoanh where khachhang_fk = '"+rs.getString("pk_seq")+"' ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														flag = false;
														sheetwrite.addCell(new Label(0, indexRow, "delete KhachHang_DaiDienKinhDoanh không thành công  :" + sql));
													}
													sql = "insert KhachHang_DaiDienKinhDoanh(KhachHang_FK,ddkd_FK) " + 
															" select '" + rs.getString("pk_seq") + "',b.pK_seq from"
															+ "	( "+
															"		select pk_seq  "+
															"		from DaiDienKinhDoanh "+
															"		where MANHANSU in ( select dbo.trim( data) from dbo.Split(N'"+matdv+"',';'))  "+
															"	 )b ";
													if(flag)
													if(db.updateReturnInt(sql) <= 0)
													{
														System.out.println("Khong thanh cong do :" + sql);
														flag = false;
														sheetwrite.addCell(new Label(0, indexRow, "Cập khách hàng đại diện kinh doanh không thành công, kiểm tra lại thông tin TDV  :" + sql));
													}


												}
												if(tructhuoc.length() > 0)
												{
													sql = "delete KHACHHANG_TRUCTHUOC where khachhang_fk = '"+rs.getString("pk_seq")+"' ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														flag = false;
														sheetwrite.addCell(new Label(0, indexRow, "delete KHACHHANG_TRUCTHUOC không thành công  :" + sql));
													}
													
													sql = "insert KHACHHANG_TRUCTHUOC(TRUCTHUOC_FK, KHACHHANG_FK) " +
															"select pk_seq,'" + rs.getString("pk_seq") + "' from NHAPHANPHOI where mafast in ( select dbo.trim( data) from dbo.Split(N'"+tructhuoc+"',';')) ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														sheetwrite.addCell(new Label(0, i, "Cập nhật khách hàng trực thuộc không thành công  :" + sql));
														flag = false;
													}
												}
												if(kbh_fk.length() > 0)
												{
													
													sql = "delete khachhang_kenhbanhang where khachhang_fk = '"+rs.getString("pk_seq")+"' ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														flag = false;
														sheetwrite.addCell(new Label(0, i, "delete khachhang_kenhbanhang không thành công  :" + sql));
													}
													sql = "insert khachhang_kenhbanhang(KhachHang_FK,KBH_FK) " + 
															" select '" + rs.getString("pk_seq") + "',b.pK_seq from"
															+ "	( "+
															"		select pk_seq  "+
															"		from KENHBANHANG "+
															"		where ten in ( select dbo.trim( data) from dbo.Split(N'"+kbh_fk+"',';'))  "+
															"	 )b ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														flag = false;
														sheetwrite.addCell(new Label(0, i, "Cập nhật khách hàng - kênh bán hàng không thành công  :" + sql));
													}


												}

												if(nguoimuahang.length() > 0 || tenxuathd.length() > 0 || diachixuathd.length() > 0)
												{
													sql = "delete KHACHHANG_THONGTINHOADON where khachhang_fk = '"+rs.getString("pk_seq")+"' ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														flag = false;
														sheetwrite.addCell(new Label(0, i, "delete KHACHHANG_THONGTINHOADON không thành công  :" + sql));
													}
													
													sql = "insert KHACHHANG_THONGTINHOADON( TENNGUOIMUA, DONVI, DIACHI, MASOTHUE, KHACHHANG_FK, ACTIVE )" + 
															" select  N'"+nguoimuahang+"',N'"+tenxuathd+"',N'"+diachixuathd+"','"+masothue+"','" +rs.getString("pk_seq")+ "','1' ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														sheetwrite.addCell(new Label(0, i, "Cập nhật thông tin hóa đơn không thành công, kiểm tra thông tin hóa đơn  :" + sql));
														flag = false;
													}


												}

												if(congtacvien.length() > 0)
												{
													sql = "delete CONGTACVIEN_KHACHHANG where kh_fk = '"+rs.getString("pk_seq")+"' ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														flag = false;
														sheetwrite.addCell(new Label(0, i, "delete CONGTACVIEN_KHACHHANG không thành công  :" + sql));
													}
													sql = "insert CONGTACVIEN_KHACHHANG(CTV_FK, KH_FK) " +
															"select b.pk_seq,'"+rs.getString("pk_seq")+"' from "
															+ "	( "+
															"		select pk_seq  "+
															"		from congtacvien "+
															"		where ma in ( select dbo.trim( data) from dbo.Split(N'"+congtacvien+"',';'))  "+
															"	 )b ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														sheetwrite.addCell(new Label(0, i, "Thêm cộng tác viên không thành công  :" + sql));
														flag = false;
													}
												}

												if( nhanviengiaonhan.length() > 0)
												{
													sql = "delete nvgn_kh where khachhang_fk = '"+rs.getString("pk_seq")+"' ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														flag = false;
														sheetwrite.addCell(new Label(0, i, "delete KhachHang_NVGN không thành công  :" + sql));
													}
													sql = "insert nvgn_kh(nvgn_fk, khachhang_fk) " + 
															"select b.pk_seq,'"+rs.getString("pk_seq")+"' from "
															+ "	( "+
															"		select pk_seq  "+
															"		from nhanviengiaonhan "+
															"		where pk_seq in ( select dbo.trim( data) from dbo.Split(N'"+nhanviengiaonhan+"',';'))  "+
															"	 )b ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														sheetwrite.addCell(new Label(0, i, "Thêm NVGN không thành công ! Kiểm tra lại thông tin NVGN  :" + sql));
														flag = false;
													}
												}

												if( nhomkhachhang.length() > 0)
												{
													sql = "delete khachhang_nkhachhang where khachhang_fk = '"+rs.getString("pk_seq")+"' ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														flag = false;
														sheetwrite.addCell(new Label(0, i, "delete khachhang_nkhachhang không thành công  :" + sql));
													}
													sql = "insert khachhang_nkhachhang(nkh_fk, khachhang_fk) " + 
															"select b.pk_seq,'"+rs.getString("pk_seq")+"' from "
															+ "	( "+
															"		select pk_seq  "+
															"		from nhomkhachhang "+
															"		where diengiai in ( select dbo.trim( data) from dbo.Split(N'"+nhomkhachhang+"',';'))  "+
															"	 )b ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														sheetwrite.addCell(new Label(0, i, "Update khách hàng không thành công  :" + sql));
														flag = false;
													}
												}
												// đua khach hàng này vào đúng tuyến
												if(matdv.length() > 0)
												{
													
													ResultSet	rs1 = db.get("select pk_seq from daidienkinhdoanh where MANHANSU = '"+matdv+"'");
													
													if(rs1 != null)
													{
														if(rs1.next())
														{
															ddkdid = rs1.getString("pk_seq");
															Hashtable<String, String> htbtuyen = Htb_TuyenBH(obj.getnppId(), userId, ddkdid, db,rs.getString("pk_seq"));
															rs1.close();
														}
														else
														{
															sheetwrite.addCell(new Label(1, i, "Không có tdv này trong hệ thống :" + matdv));
															flag = false;
														}
													}else
													{
														sheetwrite.addCell(new Label(1, i, "Không có tdv này trong hệ thống :" + matdv));
														flag = false;
													}
												}else
												{
													sheetwrite.addCell(new Label(1, i, "Không có tdv này trong hệ thống :" + matdv));
													flag = false;
												}
												sql=
														"	delete  a from khachhang_tuyenbh a inner join khachhang b on b.pk_seq=a.khachhang_fk " +
																"        inner join tuyenbanhang c on c.pk_seq=a.tbh_fk          				"+  
																"	where c.ddkd_fk='"+ddkdid+"' and b.npp_fk=" + obj.getnppId() + " and b.mafast = '" + makh.trim() + "'"
																+ " and c.ngayId in ( select dbo.trim( data) from dbo.Split(N'"+tuyenbanhang+"',';') ) ";
												if(flag)
												if (!db.update(sql))
												{
													System.out.println("Khong thanh cong do :" + sql);
													sheetwrite.addCell(new Label(1, i, "Xóa tuyến không thành công  :" + sql));
													

													flag = false;

												}	
												
												if(ddkdid.length() > 0)
												{
													sql = "insert into khachhang_tuyenbh (khachhang_fk,tbh_fk,tanso,sott) " + 
															"	 select a.pk_seq,b.pk_seq,'"+tanso+"','"+sott+"' "+
															"	 from "+
															"	 ( "+
															"		select pk_seq from KhachHang where mafast='"+makh.trim()+"' and npp_fk='"+obj.getnppId()+"'  "+
															"	 )a , "+
															"	 ( "+
															"		select pk_seq  "+
															"		from tuyenbanhang "+
															"		where npp_Fk='"+obj.getnppId()+"' and ddkd_fk='"+ddkdid+"' and NgayId in ( select dbo.trim( data) from dbo.Split(N'"+tuyenbanhang+"',';') )  "+
															"	 )b ";
													System.out.println("Insert tuyen :" + sql);
													if(flag)
													if (db.updateReturnInt(sql) <= 0)
													{
														System.out.println("Khong thanh cong do :" + sql);
														sheetwrite.addCell(new Label(1, i, "Tạo tuyến không thành công  :" + sql));

														flag = false;

													}	
												}else
												{
													
													sheetwrite.addCell(new Label(1, i, "Tạo tuyến không thành công  :" + sql));
													flag = false;

												}

											} else
											{
												// thuc hien tao moi
												System.out
												.println("2.Insert");
												sql = 
														" insert into khachhang (ten,trangthai,ngaytao,ngaysua,nguoitao,nguoisua,npp_fk,dienthoai,diachi,tinhthanh_fk, quanhuyen_fk,phuongxa_FK,chucuahieu,MaHD,MaFast,KhongKyHopDong,TenKyHd,MST_CaNhan,hch_fk,lch_fk,cmnd,chanhxe,phuongthucgiaohang,CAPDOGIAOHANG,ghichu,TENNGUOITT,DIENTHOAINGUOITT,NganHang_FK,ChiNhanh_FK,NgaySinh,nguoidaidien,diaban,NPPXHD_FK,NPPXK_FK,THOIHANNO,ghinhancongno,hanmucno,quocgia_fk, insoVISA, insoHOPDONG, innhaNK, innhaSX,sotaikhoan,taikhoan_fk,chucvu,ISDACBIET,ISXQBENHVIEN,benhvien_fk,TAIKHOANKQ_FK,KHO_FK,NGUOI_LIENHE_DH,NgayKyHD,HINHTHUCTT_FK,SONGAYVANCHUYEN,PT_CHIETKHAU,diachi_chanhxe,sdt_chanhxe,CKTHANHTOAN,diachiGiaohang,sdt_nguoidathang,tachhoadonTheoVAT,kmcp_fk) " +
																"\n select N'"+ tencuahieu+ "','1','"+ this.getDateTime()+ "','"+ this.getDateTime()+ "',"+ userId+ ","+ userId+ " ,"+ obj.getnppId()+ ",'"+ sodienthoai+ "',N'"+ diachi+ "',"+ (tinhthanh.length() <= 0 ?null:tinhthanh)+ " "+ " ,"+ (quanhuyen.length() <= 0 ?null:quanhuyen)+ ","+(phuongxa.length() <= 0 ?null:phuongxa)+",N'" + chucuahieu + "','"+sohopdong+"','"+makh+"','"+(sohopdong.length() >=0 ?"1":"0")+"',N'"+tenkyHD+"','"+MST_CaNhan+"',"+(hch_fk.length() <= 0 ?null:hch_fk)+","+(lch_fk.length() <= 0 ?null:lch_fk)+",'"+cmnd+"','"+chanhxe+"','"+phuongthucgiaohang+"',"+(capdogiaohang.length() <= 0?null:capdogiaohang)+",N'"+ghichu+"','"+tennguoitt+"','"+dienthoainguoitt+"', "
																+ "\n "+(nganhang.length() <= 0 ?null:nganhang)+","+(chinhanh.length() <= 0 ?null:chinhanh)+",'"+ngaysinh+"',N'"+nguoidaidien+"',"+(diaban.length() <= 0 ? null : diaban)+","+(nppXuatHD.length() <= 0 ?null:nppXuatHD)+","+(NPPxuatkho.length() <= 0 ?null:NPPxuatkho)+","+(thoihanno.length() <= 0 ?null:thoihanno)+", '"+ghinhancongno+"',"+(hanmucno.length() <= 0 ?null:hanmucno)+", "+(quocgia.length() <= 0 ?null:quocgia)+", '"+insovisa+"','"+insohd+"','"+innhannhapkhau+"','"+innhasx+"','"+sotknganhang+"', "+(taikhoanketoan.length() <= 0?null:taikhoanketoan)+",'"+chucvu+"','"+khachhangdacbiet+"','"+xungquanhbenhvien+"', "+(benhvien.length() <= 0 ?null:benhvien)+","+(taikhoankyquy.length() <= 0 ?null:taikhoankyquy)+","+(khodathang.length() <= 0 ?null:khodathang)+", '"+nguoilienhedathang+"','"+ngaykyhd+"', "
																+ "\n"+(Ptthanhtoan.length() <= 0 ?null:Ptthanhtoan)+", "+(songayvanchuyen.length() <= 0 ?null:songayvanchuyen)+","+(phantramchietkhau.length() <= 0 ?null:phantramchietkhau)+",N'"+diachichanhxe+"', N'"+sodienthoaichanhxe+"', "+(chietkhauthanhtoan.length() <= 0 ?null:chietkhauthanhtoan)+",N'"+diachigiaohang+"','"+sdtnguoidathang+"','"+tachhoadontheovat+"',"+(khoanmucchiphi.length() <= 0?null:khoanmucchiphi)+" ";
												if(flag)
												if (!db.update(sql))
												{
													System.out.println("Tạo khách hàng không thành công, kiểm tra thông tin trong file Upload :" + sql);
													sheetwrite.addCell(new Label(0, i, "Tạo khách hàng không thành công, kiểm tra thông tin trong file Upload   :" + sql));
													flag = false;
												}

												String query = "select IDENT_CURRENT('khachhang') as khId";
												rs = db.get(query);


												rs.next();
												id = rs.getString("khId");
												rs.close();
												System.out.println("Sheet: "+sheet1[t].getName()+". Dong: "+i);
												// mã khach hang tăng tự động 0000001
												if(id.length() > 0)	
													query = "select isnull(MAX(ma)+1,'1') as ma from khachhang";
												System.out.println("ma kh "+query);
												rs = db.get(query);
												rs.next();
												String makhtd = rs.getString("ma");
												String chuoi = "";
												for (int k = 0; k < (7 - makhtd.length()); k++)
													chuoi += "0";

												String ma = chuoi + makhtd;
												query = "update khachhang  set ma = '"+ma+"' where pk_seq = '"+id+"'";
												if(flag)
												if(!db.update(query))
												{

													sheetwrite.addCell(new Label(0, i, "Update khách hàng không thành công  :" + query));
													flag = false; 
												}
												sql = "update KHACHHANG set timkiem = dbo.ftBoDau(maFAST),timkiem_ten = dbo.ftBoDau(ten),timkiem_diachi = dbo.ftBoDau(diachi),timkiem_tenhoadon = dbo.ftBoDau(N'"+tenxuathd+"')  where npp_fk=" + obj.getnppId().trim() + " and maFAST='" + makh.trim() + "' ";
												if(flag)
												if(!db.update(sql))
												{
													System.out.println("Khong thanh cong do :" + sql);
													sheetwrite.addCell(new Label(0, i, "Update khách hàng không thành công  :" + sql));
													flag = false;
												}				

												if(matdv.length() > 0)
												{

													sql = "insert KhachHang_DaiDienKinhDoanh(KhachHang_FK,ddkd_FK) " + 
															" select '" + id + "',b.pK_seq from"
															+ "	( "+
															"		select pk_seq  "+
															"		from DaiDienKinhDoanh "+
															"		where MANHANSU in ( select dbo.trim( data) from dbo.Split(N'"+matdv+"',';'))  "+
															"	 )b ";
													if(flag)
													if(db.updateReturnInt(sql) <= 0)
													{
														System.out.println("Khong thanh cong do :" + sql);
														flag = false;
														sheetwrite.addCell(new Label(0, i, "Thêm TDV cho khách hàng không thành công, kiểm tra thông tin TDV :" + sql));
													}


												}
												if(kbh_fk.length() > 0)
												{

													sql = "insert khachhang_kenhbanhang(KhachHang_FK,KBH_FK) " + 
															" select '" + id + "',b.pK_seq from"
															+ "	( "+
															"		select pk_seq  "+
															"		from KENHBANHANG "+
															"		where ten in ( select dbo.trim( data) from dbo.Split(N'"+kbh_fk+"',';'))  "+
															"	 )b ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														flag = false;
														sheetwrite.addCell(new Label(0, i, "Thêm KBH cho khách hàng không thành công:" + sql));
													}


												}

												if(nguoimuahang.length() > 0 || tenxuathd.length() > 0 || diachixuathd.length() > 0)
												{

													sql = "insert KHACHHANG_THONGTINHOADON( TENNGUOIMUA, DONVI, DIACHI, MASOTHUE, KHACHHANG_FK, ACTIVE )" + 
															" select  N'"+nguoimuahang+"',N'"+tenxuathd+"',N'"+diachixuathd+"','"+masothue+"','" + id + "','1' ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														sheetwrite.addCell(new Label(0, i, "Thêm thông tin hóa đơn không thành công  :" + sql));
														flag = false;
													}


												}

												if(congtacvien.length() > 0)
												{
													sql = "insert CONGTACVIEN_KHACHHANG(CTV_FK, KH_FK) " +
															"select b.pk_seq,'"+id+"' from "
															+ "	( "+
															"		select pk_seq  "+
															"		from congtacvien "+
															"		where ma in ( select dbo.trim( data) from dbo.Split(N'"+congtacvien+"',';'))  "+
															"	 )b ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														sheetwrite.addCell(new Label(0, i, "Thêm cộng tác viên không thành công  :" + sql));
														flag = false;
													}
												}

												if( nhanviengiaonhan.length() > 0)
												{
													sql = "insert nvgn_kh(nvgn_fk, khachhang_fk) " + 
															"select b.pk_seq,'"+id+"' from "
															+ "	( "+
															"		select pk_seq  "+
															"		from nhanviengiaonhan "+
															"		where pk_seq in ( select dbo.trim( data) from dbo.Split(N'"+nhanviengiaonhan+"',';'))  "+
															"	 )b ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														sheetwrite.addCell(new Label(0, i, "Thêm nhân viên giao nhận không thành công  :" + sql));
														flag = false;
													}
												}

												if( nhomkhachhang.length() > 0)
												{
													sql = "insert khachhang_nkhachhang(nkh_fk, khachhang_fk) " + 
															"select b.pk_seq,'"+id+"' from "
															+ "	( "+
															"		select pk_seq  "+
															"		from nhomkhachhang "+
															"		where diengiai in ( select dbo.trim( data) from dbo.Split(N'"+nhomkhachhang+"',';'))  "+
															"	 )b ";
													if(flag)
													if(!db.update(sql))
													{
														System.out.println("Khong thanh cong do :" + sql);
														sheetwrite.addCell(new Label(0, i, "Thêm nhóm khách hàng không thành công  :" + sql));
														flag = false;
													}
												}
												
												if(tructhuoc.length() > 0)
												{
													query = "insert KHACHHANG_TRUCTHUOC(TRUCTHUOC_FK, KHACHHANG_FK) " +
															"select pk_seq,'" + id + "' from NHAPHANPHOI where mafast in ( select dbo.trim( data) from dbo.Split(N'"+tructhuoc+"',';')) ";
													if(flag)
													if(!db.update(query))
													{
														System.out.println("Khong thanh cong do :" + sql);
														sheetwrite.addCell(new Label(0, i, "Update KHACHHANG_TRUCTHUOC không thành công  :" + sql));
														flag = false;
													}
												}
												// đua khach hàng này vào đúng tuyến
												if(matdv.length() > 0)
												{
													rs = db.get("select pk_seq from daidienkinhdoanh where MANHANSU = '"+matdv+"'");
													if(rs != null)
													{
														if(rs.next())
														{
															ddkdid = rs.getString("pk_seq");
															Hashtable<String, String> htbtuyen = Htb_TuyenBH(obj.getnppId(), userId, ddkdid, db,id);
														}
														else
														{
															sheetwrite.addCell(new Label(1, i, "Không có tdv này trong hệ thống :" + matdv));
															flag = false;
														}
														rs.close();
													}else
													{
														
															sheetwrite.addCell(new Label(1, i, "Không có tdv này trong hệ thống :" + matdv));
															flag = false;
														
													}
												}else
												{
													
													sheetwrite.addCell(new Label(1, i, "Không có tdv này trong hệ thống :" + matdv));
													flag = false;

												}	
												sql=
														"	delete  a from khachhang_tuyenbh a inner join khachhang b on b.pk_seq=a.khachhang_fk " +
																"        inner join tuyenbanhang c on c.pk_seq=a.tbh_fk          				"+  
																"	where c.ddkd_fk='"+ddkdid+"' and b.npp_fk=" + obj.getnppId() + " and b.mafast = '" + makh.trim() + "'"
																+ " and c.ngayId in ( select dbo.trim( data) from dbo.Split(N'"+tuyenbanhang+"',';') ) ";
												if(flag)
												if (!db.update(sql))
												{
													System.out.println("Khong thanh cong do :" + sql);
													sheetwrite.addCell(new Label(1, indexRow, "Xóa tuyến không thành công  :" + sql));
													

													flag = false;

												}	
												
												if(ddkdid.length() > 0 && tanso.length() > 0 && tuyenbanhang.length() > 0)
												{
													sql = "insert into khachhang_tuyenbh (khachhang_fk,tbh_fk,tanso,sott) " + 
															"	 select a.pk_seq,b.pk_seq,'"+tanso+"','"+sott+"' "+
															"	 from "+
															"	 ( "+
															"		select pk_seq from KhachHang where mafast='"+makh.trim()+"' and npp_fk='"+obj.getnppId()+"'  "+
															"	 )a , "+
															"	 ( "+
															"		select pk_seq  "+
															"		from tuyenbanhang "+
															"		where npp_Fk='"+obj.getnppId()+"' and ddkd_fk='"+ddkdid+"' and NgayId in ( select dbo.trim( data) from dbo.Split(N'"+tuyenbanhang+"',';') )  "+
															"	 )b ";
													System.out.println("Insert tuyen :" + sql);
													if(flag)
													if (db.updateReturnInt(sql) <= 0)
													{
														System.out.println("Khong thanh cong do :" + sql);
														sheetwrite.addCell(new Label(1, i, "Tạo tuyến không thành công  :" + sql));

														flag = false;

													}	
											}else
											{
												
												sheetwrite.addCell(new Label(1, i, "Tạo tuyến không thành công  :" + sql));
												flag = false;

											}
											
											}
										}
									}
								}
								indexRow++;
							}
						}
						
					}
					if(flag && sokh  > 0 )
					{
						obj.setMsg("Đã thực hiện xong ! Có "+sokh+" khách hàng được Upload vào hệ thống !");
						db.getConnection().commit();
						db.getConnection().setAutoCommit(true);
						obj.init();
						session.setAttribute("obj", obj);
						session.setAttribute("userId", userId);
						String nextJSP = "/TraphacoHYERP/pages/Center/UploadTuyenBanHang.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						db.getConnection().rollback();
						try
						{
							w.write();
							w.close();
						}catch(Exception e)
						{
							e.printStackTrace();
							obj.setMsg("Lỗi vui lòng kiểm tra file Upload !");
							session.setAttribute("obj", obj);
							session.setAttribute("userId", userId);
							String nextJSP = "/TraphacoHYERP/pages/Center/UploadTuyenBanHang.jsp";
							response.sendRedirect(nextJSP);
							return;
						}
					
						
					}
					db.shutDown();

				

				} catch (Exception er)
				{
					try {
						db.getConnection().rollback();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						obj.init();
						obj.setMsg("Lỗi vui lòng kiểm tra file Upload !");
						session.setAttribute("obj", obj);
						session.setAttribute("userId", userId);
						String nextJSP = "/TraphacoHYERP/pages/Center/UploadTuyenBanHang.jsp";
						response.sendRedirect(nextJSP);
					}
					er.printStackTrace();
					System.out.println("Exception 333." + er.getMessage());
					return;
				}
			}
		}
		else
		{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/UploadTuyenBanHang.jsp";
			response.sendRedirect(nextJSP);
		}
	
	}

	private Hashtable<String, String> gethtpQuanHuyen(dbutils db)
	{

		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = "select pk_seq from quanhuyen";

		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("pk_seq").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
			System.out.println("Loi lay tinhthanh : " + er.toString());
		}
		return htbtuyen;
	}

	private Hashtable<String, String> gethtpTinhThanh(dbutils db)
	{

		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = "select pk_seq from tinhthanh";

		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("pk_seq").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
			System.out.println("Loi lay tinhthanh : " + er.toString());
		}
		return htbtuyen;
	}

	private Hashtable<String, String> gethtpPhuongXa(dbutils db)
	{

		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = "select pk_seq from phuongxa";

		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("pk_seq").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
			System.out.println("Loi lay tinhthanh : " + er.toString());
		}
		return htbtuyen;
	}

	private Hashtable<String, String> gethtpNPP(dbutils db)
	{

		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = "select mafast,pk_seq from nhaphanphoi";

		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("mafast").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
			System.out.println("Loi lay tinhthanh : " + er.toString());
		}
		return htbtuyen;
	}
	
	private Hashtable<String, String> gethtpNganHang(dbutils db)
	{

		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = "select MA,PK_SEQ from ERP_NGANHANG";

		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("MA").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
			System.out.println("Loi lay tinhthanh : " + er.toString());
		}
		return htbtuyen;
	}
	private Hashtable<String, String> gethtpChiNhanh(dbutils db)
	{

		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = "select MA,PK_SEQ from ERP_CHINHANH";

		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("MA").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
			System.out.println("Loi lay tinhthanh : " + er.toString());
		}
		return htbtuyen;
	}
	private Hashtable<String, String> gethtpTaikhoanKT(dbutils db,String npp)
	{

		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = "select SOHIEUTAIKHOAN,PK_SEQ from ERP_TaikhoanKt WHERE TRANGTHAI = 1 and CONGTY_FK = (select congty_fk from nhaphanphoi where pk_seq = "+npp+")";

		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("SOHIEUTAIKHOAN").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
			System.out.println("Loi lay tinhthanh : " + er.toString());
		}
		return htbtuyen;
	}
	
	private Hashtable<String, String> gethtpKhoanMuc(dbutils db)
	{

		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = "select Ten,PK_SEQ from ERP_NHOMCHIPHI where TRANGTHAI = '1' ";

		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("Ten").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
			System.out.println("Loi lay tinhthanh : " + er.toString());
		}
		return htbtuyen;
	}

	private Hashtable<String, String> gethtpDiaban(dbutils db)
	{

		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = "select pk_seq from diaban";

		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("pk_seq").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
			System.out.println("Loi lay tinhthanh : " + er.toString());
		}
		return htbtuyen;
	}

	private Hashtable<String, String> gethtpVitricuahang(dbutils db)
	{

		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = "select vitri,pk_seq from vitricuahang";

		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("vitri").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
			System.out.println("Loi lay vtch : " + er.toString());
		}
		return htbtuyen;
	}


	private Hashtable<String, String> getPhanLoai(dbutils db)
	{

		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = 
				" SELECT data.pLoai,data.ploaiId  "+ 
						" FROM "+
						" (   "+
						"	select 'BL' as pLoai,0 as ploaiId union all  "+
						"	select 'BB' as pLoai,1 as ploaiId union all   "+
						"	select 'BB-BL' as pLoai,2 as ploaiId    "+
						/*"	select 'BL-BB' as pLoai,3 as ploaiId   "+*/
						") as data";

		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("pLoai").trim(), rs.getString("ploaiId").trim());
			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
		}
		return htbtuyen;
	}

	private Hashtable<String, String> getThanhToan(dbutils db)
	{

		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = 
				" SELECT data.pLoai,data.ploaiId  "+ 
						" FROM "+
						" (   "+
						"	select 'TM' as pLoai,0 as ploaiId union all  "+
						"	select 'HD' as pLoai,1 as ploaiId   "+
						") as data";

		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("pLoai").trim(), rs.getString("ploaiId").trim());
			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
		}
		return htbtuyen;
	}


	private Hashtable<String, String> getKenhBanHang(dbutils db)
	{
		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = 
				"		select PK_SEQ,TEN,DIENGIAI from kenhbanhang ";
		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("TEN").trim(), rs.getString("PK_SEQ").trim());
			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
		}
		return htbtuyen;
	}


	private Hashtable<String, String> gethtpLoaicuahang(dbutils db)
	{

		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = "select loai,pk_seq from loaicuahang";

		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("loai").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
			System.out.println("Loi lay loai cuahang : " + er.toString());
		}
		return htbtuyen;
	}

	private Hashtable<String, String> gethtpHangcuahang(dbutils db)
	{
		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		String sql = "select hang,pk_seq from hangcuahang";
		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("hang").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
			System.out.println("Loi lay hangcuahang : " + er.toString());
		}
		return htbtuyen;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		return dateFormat.format(date);
	}

	private Hashtable<String, String> Htb_TuyenBH(String nppId, String userid, String nvbhid, dbutils db,String khid)
	{
		String sql = 
				" insert into tuyenbanhang  (diengiai,ngaylamviec,ngaytao,ngaysua,nguoitao,nguoisua,ddkd_fk,npp_fk,ngayid) " +
						" select N'Thứ Hai','Thu hai','" + this.getDateTime() + "','"+ this.getDateTime() + "'," + userid + "," + userid + ",pk_seq," + nppId + ",2   " + 
						" from  daidienkinhdoanh   "+ 
						" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=2 and ddkd_fk=" + nvbhid + " and npp_fk=" + nppId + ") " + 
						" and pk_seq= " + nvbhid + " union  "+ 
						" select N'Thứ Ba','Thu ba','" + this.getDateTime() + "','" + this.getDateTime() + "'," + userid + "," + userid + ",pk_seq," + nppId + ",3   " + " from  daidienkinhdoanh   "+ 
						" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=3 and ddkd_fk=" + nvbhid + " and npp_fk=" + nppId + ") " + " and pk_seq=" + nvbhid + " union  "+ 
						" select N'Thứ Tư','Thu tu','" + this.getDateTime() + "','" + this.getDateTime() + "'," + userid + "," + userid + ",pk_seq," + nppId + ",4   " + " from  daidienkinhdoanh   "+ 
						" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=4 and ddkd_fk=" + nvbhid + " and npp_fk=" + nppId + ") " + " and pk_seq=" + nvbhid + " union  "+ 
						" select N'Thứ Năm','Thu nam','" + this.getDateTime() + "','" + this.getDateTime() + "'," + userid + "," + userid + ",pk_seq," + nppId + ",5   " + " from  daidienkinhdoanh   "+ 
						" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=5 and ddkd_fk=" + nvbhid + " and npp_fk=" + nppId + ") " + " and pk_seq=" + nvbhid + " union  "+ 
						" select N'Thứ Sáu','Thu sau','" + this.getDateTime() + "','" + this.getDateTime() + "'," + userid + "," + userid + ",pk_seq," + nppId + ",6  " + " from  daidienkinhdoanh   "+ " where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=6 and ddkd_fk=" + nvbhid + " and npp_fk=" + nppId + ") " + " and pk_seq=" + nvbhid + " union  "+ 
						" select N'Thứ Bảy','Thu bay','" + this.getDateTime() + "','" + this.getDateTime() + "'," + userid + "," + userid + ",pk_seq," + nppId + ",7 " + " from  daidienkinhdoanh   "+ " where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=7 and ddkd_fk=" + nvbhid + " and npp_fk=" + nppId + ") " + " and pk_seq=" + nvbhid;
		System.out.println("Tao Tuyen ban hang : " + sql);
		db.update(sql);
		// xoa het tuyen cu cua nhan vien ban hàng này đi
		sql = "delete khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk=" + nvbhid + " and npp_fk=" + nppId + ") and khachhang_fk = '"+khid+"' ";
		if (!db.update(sql))
		{
			System.out.println("xoa Tuyen ban hang : " + sql);
		}

		Hashtable<String, String> htbtuyen = new Hashtable<String, String>();
		sql = "select pk_seq,ngayid,npp_fk,ddkd_fk from tuyenbanhang where ddkd_fk=" + nvbhid + " and npp_fk=" + nppId;
		System.out.println(sql);

		ResultSet rs = db.get(sql);
		try
		{
			while (rs.next())
			{
				htbtuyen.put(rs.getString("npp_fk").trim() + "_" + rs.getString("ddkd_fk").trim() + "_" + rs.getString("ngayid").trim(), rs.getString("pk_seq").trim());
			}
		} catch (Exception er)
		{
			System.out.println("Loi lay tuyen : " + er.toString());
		}
		return htbtuyen;
	}
}
