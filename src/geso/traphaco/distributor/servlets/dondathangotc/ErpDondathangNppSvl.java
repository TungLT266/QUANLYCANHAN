package geso.traphaco.distributor.servlets.dondathang;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.dondathang.IErpDondathangNpp;
import geso.traphaco.distributor.beans.dondathang.IErpDondathangNppList;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDondathangNpp;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDondathangNppList;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDondathangNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	public ErpDondathangNppSvl() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDondathangNppList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();	    
		
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		if (userId.length()==0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String action = util.getAction(querystring);
		
		String locgiaQUYDOI = request.getParameter("locgiaQUYDOI");
		if(locgiaQUYDOI == null)
			locgiaQUYDOI = "0";
		
		System.out.println("ACTION LA: " + action );
		if(locgiaQUYDOI.equals("1"))
		{
			String spMa = request.getParameter("spMa");
			if(spMa == null)
				spMa = "";
			
			String dvt = request.getParameter("dvt");
			if(dvt == null)
				dvt = "";
			
			String dvkdId = "";
			if(session.getAttribute("dvkdId") != null )
				dvkdId = (String) session.getAttribute("dvkdId");
			
			String kbhId = "";
			if(session.getAttribute("kbhId") != null )
				kbhId = (String) session.getAttribute("kbhId");
			
			String nppId = "";
			if(session.getAttribute("nppId") != null )
				nppId = (String) session.getAttribute("nppId");
			
			String query = (String)request.getQueryString();
			spMa = new String(query.substring(query.indexOf("&spMa=") + 6, query.indexOf("&dvt=")).getBytes("UTF-8"), "UTF-8");
			spMa = URLDecoder.decode(spMa, "UTF-8").replace("+", " ");
			
			dvt = new String(query.substring(query.indexOf("&dvt=") + 5, query.indexOf("&locgiaQUYDOI")).getBytes("UTF-8"), "UTF-8");
			dvt = URLDecoder.decode(dvt, "UTF-8").replace("+", " ");
			
			//System.out.println(" -- MA SP: " + spMa + " -- DVT: " + dvt );
			//spMa = URLDecoder.decode(spMa, "UTF-8").replace("+", " ");
			//dvt = URLDecoder.decode(dvt, "UTF-8").replace("+", " ");
			
			if(spMa.trim().length() > 0 && dvt.trim().length() > 0 )
			{
				dbutils db = new dbutils();
				
				String sql = " select tructhuoc_fk, loaiNPP from NHAPHANPHOI where pk_seq = '" + nppId + "' ";
				ResultSet rs = db.get(sql);
				String tructhuocId = "";
				String loaiNPP = "";
				if(rs != null)
				{
					try 
					{
						if(rs.next())
						{
							loaiNPP = rs.getString("loaiNPP");
							tructhuocId = rs.getString("tructhuoc_fk");
						}
						rs.close();
					} 
					catch (Exception e) { }
				}
				
				String command = "";
				
				if(!loaiNPP.equals("4") && !loaiNPP.equals("5") )
				{
					command = " select a.DVDL_FK as dvCHUAN, ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvt + "' ) as dvNEW, " + 
							"    isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK and DVDL2_FK = '100018' ), 0 ) as quydoi, " +  
							" isnull( ( select GIAMUANPP * ( 1 - isnull( ( select chietkhau from BANGGIAMUANPP_NPP where banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = '"+nppId+"' ), 0) / 100 )  				from BGMUANPP_SANPHAM bg_sp 			    where SANPHAM_FK = a.pk_seq  					and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"' order by bg.TUNGAY desc ) ), 0) as GiaMua "+ 
							" from SANPHAM a where a.MA = '" + spMa + "'  ";
				}
				else
				{
					command =  "select a.DVDL_FK as dvCHUAN, ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvt + "' ) as dvNEW,      " +
							"	isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK and DVDL2_FK = ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvt + "' ) ), 0 ) as quydoi,  	  " +
							"	isnull( ( select dongia from BANGGIABANDOITAC_SANPHAM where sanpham_fk = a.pk_seq  " +
							"					and BGBANDOITAC_FK in ( select top(1) BANGGIABANDOITAC_FK from BANGGIABANDOITAC_DOITAC where  NPP_FK = '" + nppId + "' and BANGGIABANDOITAC_FK in ( select pk_seq from BANGGIABANDOITAC where  NPP_FK = '" + tructhuocId + "' and trangthai = '1' and KENH_FK = '" + kbhId + "' ) ) ), 0) as giamua   " +
							"from SANPHAM a where a.MA = '" + spMa + "'  ";
				}
				
				System.out.println("Lay don gia san pham: " + command);
				String kq  = "0";
				rs = db.get(command);
				try
				{
					if(rs.next())
					{
						String dvCHUAN = rs.getString("dvCHUAN");
						String dvNEW = rs.getString("dvNEW");
						double quydoi = rs.getDouble("quydoi");
						double dongia = rs.getDouble("giamua");
						
						//System.out.println("DON VI NEW: " + dvNEW);
						if(dvCHUAN.equals(dvNEW))
							kq = Double.toString(dongia);
						else
							kq = Double.toString(dongia * quydoi );
						
					}
					rs.close();
				} 
				catch (Exception e) { kq = "0"; }
				
				db.shutDown();
				
				System.out.println("GIA: " + kq);
				out.write(kq);
			}
			else
			{
				out.write("0");
			}
			
			return;
		}
		else
		{
			String lsxId = util.getId(querystring);
			obj = new ErpDondathangNppList();
			
			String loaidonhang = request.getParameter("loaidonhang");
			if(loaidonhang == null)
				loaidonhang = "0";
			obj.setLoaidonhang(loaidonhang);
			System.out.println("---LOAI DON HANG: " + loaidonhang);
			
			if (action.equals("delete") )
			{	
				String msg = this.DeleteChuyenKho(lsxId);
				obj.setMsg(msg);
			}
			else if(action.equals("chot"))
			{
				String msg = this.Chot(lsxId, userId);
				obj.setMsg(msg);
			}
			
			obj.setUserId(userId);
			obj.init("");
			
			session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNpp.jsp";
			if(loaidonhang.equals("4"))
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangKhacNpp.jsp";
			else if(loaidonhang.equals("2"))
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMTichLuyNpp.jsp";
			else if(loaidonhang.equals("1"))
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMUngHangNpp.jsp";
			else if(loaidonhang.equals("3"))
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangTrungBayNpp.jsp";
			
			response.sendRedirect(nextJSP);
		}
	}
	
	private String Chot(String lsxId, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		Utility util = new Utility();
		
		msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_Dondathang", lsxId, "NgayDonHang", db);
		if(msg.length()>0)
			return msg;
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_Dondathang set trangthai = '0', NPP_DACHOT = '1' where pk_seq = '" + lsxId + "' ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//TU DONG TAO XUAT CHUYEN NOI BO TREN TRUNG TAM
			//TH1. TRUC THUOC HO
			// --> NEU LA CHI NHANH THI LA XUAT CHUYEN NOI BO TREN TRUNG TAM (ERP_CHUYENKHO)
			// --> NEU LA DOI TAC THI CHAY THEO QUY TRINH BINH THUONG (ERP_DONDATHANG) (LEN TREN TRUNG TAM DUYET) --> KO CAN CHINH 
			//TH2. TRUC THUOC CHI NHANH
			// --> NEU LA CHI NHANH THI LA XUAT CHUYEN NOI BO TREN TRUNG TAM (ERP_CHUYENKHONPP)
			// --> NEU LA DOI TAC THI CHAY THEO QUY TRINH BINH THUONG (ERP_DONDATHANGNPP) 
			
			query = "select isnull(a.LoaiDonHang,0) as LoaiDonHang,b.loaiNPP, a.tructhuoc_fk, a.kho_fk, a.npp_fk, a.ngaydenghi, a.kbh_fk, a.kho_fk, a.dvkd_fk,a.iskm  " +
					"from ERP_Dondathang a inner join NHAPHANPHOI b on a.npp_fk = b.pk_seq " +
					"where a.pk_seq = '" + lsxId + "' ";
			ResultSet rs = db.get(query);
			String loaiNPP = "";
			String tructhuoc_fk = "";
			String kho_fk = "";
			String LoaiDonHang = "";
			String isKm ="0";
			if(rs.next())
			{
				loaiNPP = rs.getString("loaiNPP");
				tructhuoc_fk = rs.getString("tructhuoc_fk");
				kho_fk = rs.getString("kho_fk");
				LoaiDonHang= rs.getString("LoaiDonHang");
				isKm = rs.getString("isKm")==null?"0":rs.getString("isKm");
				
				if(!LoaiDonHang.equals("4"))
				{				
					if( !loaiNPP.equals("4") && !loaiNPP.equals("5") )  //--> LOAI DOITAC THI PHAI QUA QUY TRINH DUYET
					{
						if(tructhuoc_fk.trim().length() <= 5 )  //--> XUAT CHUYEN NOI BO DO HO XU LY
						{
							//CHECK TON KHO
							query = "select sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT, ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + kho_fk + "' and sanpham_fk = sp.PK_SEQ ), 0) as tonkho " +
									"from    " +
									"(    " +
									"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
									"				case when a.dvdl_fk IS null then a.soluong     " +
									"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
									"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
									"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
									"		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
									"		where a.dondathang_fk in ( '" + lsxId + "' )   " +
									"	union ALL  " +
									"		select b.PK_SEQ, b.DVDL_FK as dvCHUAN, a.soluong, 1 as loai, c.SCHEME   " +
									"		from ERP_DONDATHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA   " +
									"				inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ   " +
									"		where a.DONDATHANGID in ( '" + lsxId + "' )    " +
									")    " +
									"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ  " +
									"group by sp.PK_SEQ, sp.TEN " +
									"having  SUM(dathang.soluong) > ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + kho_fk + "' and sanpham_fk = sp.PK_SEQ ), 0) " ;
							
							System.out.println("--CHECK TON KHO: " + query);
							
							ResultSet rsCHECK = db.get(query);
							{
								while(rsCHECK.next())
								{
									msg = "Sản phẩm ( " + rsCHECK.getString("TEN") + " ) với số lượng yêu cầu ( "+ rsCHECK.getString("soluongXUAT")+ " ) không đủ tồn kho ( "+ rsCHECK.getString("tonkho")+ " ). Vui lòng kiểm tra lại.";
									db.getConnection().rollback();
									rsCHECK.close();
									return msg;
								}
								rsCHECK.close();
							}
							
							query = 
									" insert ERP_CHUYENKHO(ngaychuyen, ghichu, trangthai, khoxuat_fk, kbh_fk, npp_fk, ddh_fk, ngaytao, nguoitao, ngaysua, nguoisua,tructhuoc_fk,LoaiDonHang,isKm) " +
									" values('"+ rs.getString("ngaydenghi")+ "', N'Chi nhánh đặt hàng', '0', '"+ rs.getString("kho_fk")+ "', '"+ rs.getString("kbh_fk")+ "', "+ rs.getString("npp_fk")+ ", '"+ lsxId+ "', '"+ getDateTime()+ "', '"+ userId+ "', '"+ getDateTime()+ "', '"+ userId+ "','"+tructhuoc_fk+"','"+LoaiDonHang+"','"+isKm+"' )";
							
							System.out.println("1.Insert CK: " + query);
							if(!db.update(query))
							{
								msg = "Lỗi khi chốt: " + query;
								db.getConnection().rollback();
								rs.close();
								return msg;
							}
							
							query = "insert ERP_CHUYENKHO_SANPHAM( chuyenkho_fk, SANPHAM_FK, soluongchuyen, dongia, dvdl_fk ) " +
									"select ident_current('ERP_CHUYENKHO'), sanpham_fk, soluong, dongia, DVDL_FK " +
									"from ERP_DONDATHANG_SANPHAM where dondathang_fk = '" + lsxId + "' ";
							
							System.out.println("3.Insert CK - SP: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM: " + query;
								db.getConnection().rollback();
								rs.close();
								return msg;
							}
							
							
							query = "select sp.PK_SEQ, sp.TEN, LOAI, SCHEME, SUM(dathang.soluong) as soluongXUAT " +
									"from    " +
									"(    " +
									"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
									"				case when a.dvdl_fk IS null then a.soluong     " +
									"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
									"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
									"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
									"		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
									"		where a.dondathang_fk in ( '" + lsxId + "' )   " +
									"	union ALL  " +
									"		select b.PK_SEQ, b.DVDL_FK as dvCHUAN, a.soluong, 1 as loai, c.SCHEME   " +
									"		from ERP_DONDATHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA   " +
									"				inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ   " +
									"		where a.DONDATHANGID in ( '" + lsxId + "' )    " +
									")    " +
									"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ  " +
									"group by sp.PK_SEQ, sp.TEN, LOAI, SCHEME ";
							System.out.println("--CHECK KHO CHI TIET: " + query);
							rsCHECK = db.get(query);
							if(rsCHECK != null)
							{
								while(rsCHECK.next())
								{
									String sanpham_fk = rsCHECK.getString("PK_SEQ");
									String LOAI = rsCHECK.getString("LOAI");
									String SCHEME = rsCHECK.getString("SCHEME");
									double soLUONG = rsCHECK.getDouble("soluongXUAT");
									
									query = "Update ERP_KHOTT_SANPHAM set booked = booked + '" + soLUONG + "', AVAILABLE = AVAILABLE - '" + soLUONG + "' " +
											"where KHOTT_FK = '" + kho_fk + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
									if(!db.update(query))
									{
										msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
										db.getConnection().rollback();
										return msg;
									}
									
									//CAP NHAT KHO CHI TIET
									query = "select AVAILABLE, SOLO from ERP_KHOTT_SP_CHITIET " +
											"where KHOTT_FK = '" + kho_fk + "'  and SANPHAM_FK = '" + sanpham_fk + "' order by ngayhethan asc ";
									
									ResultSet rsTK = db.get(query);
									double avai = 0;
									double totalXUAT = 0;
									while(rsTK.next())
									{
										double soluongCT = 0;
										String solo = rsTK.getString("SOLO");
										
										avai = rsTK.getDouble("AVAILABLE");
										totalXUAT += avai;
										
										if(totalXUAT <= soLUONG)
										{
											soluongCT = avai;
											
											query = "insert ERP_CHUYENKHO_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK, solo, soluong, loai, scheme ) " +
													"select IDENT_CURRENT('ERP_CHUYENKHO'), '" + sanpham_fk + "', N'" + solo + "', '" + soluongCT + "', '" + LOAI + "', '" + SCHEME + "' ";
											
											System.out.println("1.2.Insert CK - SP - CT: " + query);
											if(!db.update(query))
											{
												msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM_CHITIET: " + query;
												db.getConnection().rollback();
												return msg;
											}
											
											
											query = "Update ERP_KHOTT_SP_CHITIET set booked = booked + '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
													"where KHOTT_FK = '" + kho_fk + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
											if(!db.update(query))
											{
												msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
												db.getConnection().rollback();
												return msg;
											}
											
										}
										else
										{
											soluongCT = soLUONG - ( totalXUAT - avai );
											
											query = "insert ERP_CHUYENKHO_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK, solo, soluong, loai, scheme ) " +
													"select IDENT_CURRENT('ERP_CHUYENKHO'), '" + sanpham_fk + "', N'" + solo + "', '" + soluongCT + "', '" + LOAI + "', '" + SCHEME + "' ";
											System.out.println("1.2.Insert CK - SP - CT: " + query);
											if(!db.update(query))
											{
												msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM_CHITIET: " + query;
												db.getConnection().rollback();
												return msg;
											}
											
											query = "Update ERP_KHOTT_SP_CHITIET set booked = booked + '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
													"where KHOTT_FK = '" + kho_fk + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
											if(!db.update(query))
											{
												msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
												db.getConnection().rollback();
												return msg;
											}
											
											break;
										}
									}
									rsTK.close();
									
								}
								rsCHECK.close();
							}	
						}
						else	//XUAT CHUYEN NOI BO DO CN1, CN2, DOITAC XU LY
						{
							query = " insert ERP_CHUYENKHONPP(ngaychuyen, ghichu, trangthai, khoxuat_fk, kbh_fk, npp_fk, npp_dat_fk, ddh_fk, ngaytao, nguoitao, ngaysua, nguoisua, from_dondathang,tructhuoc_fk,iskm) " +
									" values('"+ rs.getString("ngaydenghi")+ "', N'Chi nhánh đặt hàng', '0', '"+ rs.getString("kho_fk")+ "', '"+ rs.getString("kbh_fk")+ "', '"+ tructhuoc_fk+ "', "+ rs.getString("npp_fk")+ ", '"+ lsxId+ "', '"+ getDateTime()+ "', '"+ userId+ "', '"+ getDateTime()+ "', '" + userId + "', '" + lsxId + "','"+tructhuoc_fk+"','"+isKm+"' )";
							
							System.out.println("1.Insert CK: " + query);
							if(!db.update(query))
							{
								msg = "Lỗi khi chốt: " + query;
								db.getConnection().rollback();
								rs.close();
								return msg;
							}
							
							query = 
									" select scope_identity() as ckId,( select case  when  1=(select dungchungkenh from nhaphanphoi where PK_SEQ='" + tructhuoc_fk + "') then 100025 else a.kbh_fk end ) as KBH_fK  , ( select ten from NHAPHANPHOI where pk_seq = '" + tructhuoc_fk + "' ) as tructhuocTEN " +
											" from ERP_DonDatHang a where pk_seq = '" + lsxId + "' ";
							System.out.println("2.Info: " + query);
							
							String ckId = "";
							String kbhId = "";
							String tructhuocTEN = "";
							
							rs = db.get(query);
							{
								if(rs.next())
								{
									ckId = rs.getString("ckId");
									kbhId = rs.getString("kbh_fk");
									tructhuocTEN = rs.getString("tructhuocTEN");
								}
								rs.close();
							}
							
							query = "insert ERP_CHUYENKHONPP_SANPHAM( chuyenkho_fk, SANPHAM_FK, soluongchuyen, dongia, dvdl_fk ) " +
									"select '" + ckId + "', sanpham_fk, soluong, dongia, DVDL_FK " +
									"from ERP_DONDATHANG_SANPHAM where dondathang_fk = '" + lsxId + "' ";
							
							System.out.println("3.Insert CK - SP: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_CHUYENKHONPP_SANPHAM: " + query;
								db.getConnection().rollback();
								rs.close();
								return msg;
							}
							
							
							// Bổ sung BOOKED kho của trực thuộc cấp trên
							query =  "select khoxuat_fk as kho_fk, '" + tructhuoc_fk + "' as npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT,  " +
									"	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = dathang.khoxuat_fk and sanpham_fk = sp.PK_SEQ and kbh_fk = dathang.kbh_fk and npp_fk = dathang.npp_fk ), 0) as tonkho  " +
									"from     " +
									"(     " +
									"	select c.kho_fk as khoxuat_fk, '" + tructhuoc_fk + "' as npp_fk, '" + kbhId + "' kbh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
									"			case when a.dvdl_fk IS null then a.soluong      " +
									"				 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
									"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )      " +
									"								 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )	 end as soluong   " +
									"	from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
									"			inner join ERP_DONDATHANG c on a.dondathang_fk = c.pk_seq    " +
									"	where a.dondathang_fk in ( " + lsxId + " )     " +
									")     " +
									"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
									"group by khoxuat_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN  ";
							
							System.out.println("--CHECK KHO CHI TIET: " + query);
							String nppID = "";
							ResultSet rsCHECK = db.get(query);
							{
								while(rsCHECK.next())
								{
									String khoID = rsCHECK.getString("kho_fk");
									String kbhID = rsCHECK.getString("kbh_fk");
									nppID = rsCHECK.getString("npp_fk");
									String spID = rsCHECK.getString("PK_SEQ");
									String LOAI = "0";
									String SCHEME = "";
									
									double soluong = rsCHECK.getDouble("soluongXUAT");
									double tonkho = rsCHECK.getDouble("tonkho");
									
									if(soluong > tonkho)
									{
										msg = "Sản phẩm ( "+ rsCHECK.getString("TEN")+ " ) với số lượng yêu cầu ( "+ rsCHECK.getString("soluongXUAT")+ " ) không đủ tồn kho ( "+ rsCHECK.getString("tonkho")+ " ) của chi nhánh ( "+ tructhuocTEN+ " ). Vui lòng kiểm tra lại.";
										db.getConnection().rollback();
										rsCHECK.close();
										return msg;
									}
									
									query = "Update NHAPP_KHO set booked = booked + '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
											"where KHO_FK = '" + khoID + "' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' ";
									if(db.updateReturnInt(query) != 1)
									{
										msg = "Khong the cap nhat NHAPP_KHO: " + query;
										db.getConnection().rollback();
										rs.close();
										return msg;
									}
									
									//CAP NHAT KHO CHI TIET
									query = "select AVAILABLE, SOLO, NGAYHETHAN " +
											"from NHAPP_KHO_CHITIET " +
											"where KHO_FK = '" + kho_fk + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' and KBH_FK = '" + kbhID + "' and AVAILABLE > 0 " +
											"order by ngayhethan asc ";
									
									System.out.println("---LAY KHO CHI TIET: " + query );
									
									ResultSet rsTK = db.get(query);
									double avai = 0;
									double totalXUAT = 0;
									while(rsTK.next())
									{
										double soluongCT = 0;
										String solo = rsTK.getString("SOLO");
										String ngayhethan = rsTK.getString("NGAYHETHAN");
										
										avai = rsTK.getDouble("AVAILABLE");
										totalXUAT += avai;
										
										if(totalXUAT <= soluong)
										{
											soluongCT = avai;
											
											query = "insert ERP_CHUYENKHONPP_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, soluong, loai, scheme ) " +
													"select '" + ckId + "', '" + spID + "', N'" + solo + "', '" + ngayhethan + "', '" + soluongCT + "', '" + LOAI + "', '" + SCHEME + "' ";
											
											System.out.println("1.2.Insert CK - SP - CT: " + query);
											if(!db.update(query))
											{
												msg = "Khong the tao moi ERP_CHUYENKHONPP_SANPHAM_CHITIET: " + query;
												db.getConnection().rollback();
												return msg;
											}
											
											query = "Update NHAPP_KHO_CHITIET set booked = booked + '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
													"where KHO_FK = '" + kho_fk + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' and KBH_FK = '" + kbhID + "' and SOLO = '" + solo + "' and NGAYHETHAN = '" + ngayhethan + "' ";
											
											if(db.updateReturnInt(query)!=1)
											{
												msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
												db.getConnection().rollback();
												return msg;
											}
										}
										else
										{
											soluongCT = soluong - ( totalXUAT - avai );
											
											query = "insert ERP_CHUYENKHONPP_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, soluong, loai, scheme ) " +
													"select '" + ckId + "', '" + spID + "', N'" + solo + "', '" + ngayhethan + "', '" + soluongCT + "', '" + LOAI + "', '" + SCHEME + "' ";
											
											System.out.println("1.2.Insert CK - SP - CT: " + query);
											if(!db.update(query))
											{
												msg = "Khong the tao moi ERP_CHUYENKHONPP_SANPHAM_CHITIET: " + query;
												db.getConnection().rollback();
												return msg;
											}
											
											query = "Update NHAPP_KHO_CHITIET set booked = booked + '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
													"where KHO_FK = '" + kho_fk + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' and KBH_FK = '" + kbhID + "' and SOLO = '" + solo + "' and NGAYHETHAN = '" + ngayhethan + "' ";
											
											if(db.updateReturnInt(query)!=1)
											{
												msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
												db.getConnection().rollback();
												return msg;
											}
											
											break;
										}
									}
									rsTK.close();
									
								}
								rsCHECK.close();
							}
							
							query=
									"	SELECT CK.SOLUONG AS TOTAL,CK_DE.SOLUONG AS DETAIL,SP.MA AS SPMA,SP.TEN AS spTen "+
											"	FROM "+ 
											"	( "+
											"		SELECT SUM(SOLUONGCHUYEN) AS SOLUONG,CHUYENKHO_FK,A.SANPHAM_FK "+
											"		FROM ERP_CHUYENKHONPP_SANPHAM A  "+
											"		WHERE A.CHUYENKHO_FK='"+ckId+"'  "+
											"		GROUP BY CHUYENKHO_FK,A.SANPHAM_FK "+
											"	)AS CK LEFT JOIN "+ 
											"	( "+
											"		SELECT SUM(SOLUONG) AS SOLUONG,CHUYENKHO_FK,A.SANPHAM_FK "+
											"		FROM ERP_CHUYENKHONPP_SANPHAM_CHITIET A  "+
											"		WHERE A.CHUYENKHO_FK='"+ckId+"'  "+
											"		GROUP BY CHUYENKHO_FK,A.SANPHAM_fK  "+
											"	)AS CK_DE ON CK_DE.CHUYENKHO_FK=CK.CHUYENKHO_FK AND CK.SANPHAM_FK=CK_dE.SANPHAM_FK " +
											" FULL OUTER JOIN SANPHAM SP ON SP.PK_SEQ=ISNULL(CK.SANPHAM_FK,CK_DE.SANPHAM_FK)  "+
											"	WHERE ISNULL(CK.SOLUONG,0)!=ISNULL(CK_DE.SOLUONG,0) ";
							
							System.out.println("__Check TOTAL "+query);
							rs =db.get(query);
							while(rs.next())
							{
								msg += "Sản phẩm "+rs.getString("spMa") +" - "+rs.getString("spTen") +" để xuất theo Lô không đúng,vui lòng liên hệ TT \n";
							}
							if(rs!=null)rs.close();
							
							if(msg.length()>0)
							{
								db.getConnection().rollback();
								return msg;
							}
							
							msg= util.Check_Kho_Tong_VS_KhoCT(nppID, db);
							if(msg.length()>0)
							{
								db.getConnection().rollback();
								return msg;
							}
						}
					}
					else // NẾU KHÔNG PHẢI LÀ ĐẶT HÀNG TRỰC THUỘC HO THÌ PHẢI TẠO
						// 1 ĐƠN ĐẶT HÀNG CHO ĐỐI TÁC Ở LEVEL CN1, CN2
					{
						if(tructhuoc_fk.trim().length() >= 6 )  
						{
							query = " insert ERP_DonDatHangNPP(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, npp_dat_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, NOTE, NPP_DACHOT, from_dondathang,tructhuoc_fk,isKM) " +
									" select ngaydenghi, ngaydenghi, 0, ghichu, 0 as trangthai, dvkd_fk, kbh_fk, ( select tructhuoc_fk from NHAPHANPHOI where pk_seq = a.NPP_FK ), npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, N'Convert từ đơn đặt hàng dưới đối tác  "+ lsxId+ "', '1', '"+ lsxId+ "','"+tructhuoc_fk+"','"+isKm+"'  "+
									" from ERP_DonDatHang a where pk_seq = '" + lsxId + "' ";
							
							System.out.println("1.Insert DDH: " + query);
							if(!db.update(query))
							{
								msg = "Không thể tạo mới ERP_DonDatHangNPP " + query;
								db.getConnection().rollback();
								return msg;
							} 
							
							query = " select scope_identity() as ddhId, ( select ten from NHAPHANPHOI where pk_seq = '" + tructhuoc_fk + "' ) as tructhuocTEN,case when 1= ( select DungChungKenh from NHAPHANPHOI where pk_seq = '" + tructhuoc_fk + "' ) then '100025'  else a.kbh_fk end  as kbh_fk " +
									" from ERP_DonDatHang a  where pk_seq = '" + lsxId + "' ";
							System.out.println("2.Info: " + query);
							
							String ddhId = "";
							String kbhId = "";
							String tructhuocTEN = "";
							
							rs = db.get(query);
							{
								if(rs.next())
								{
									ddhId = rs.getString("ddhId");
									kbhId = rs.getString("kbh_fk");
									tructhuocTEN = rs.getString("tructhuocTEN");
								}
								rs.close();
							}
							
							query = "insert ERP_DONDATHANGNPP_SANPHAM(dondathang_fk, sanpham_fk, soluong, dongia, dvdl_fk, thueVAT, dongiagoc)  " +
									"select '" + ddhId + "', sanpham_fk, soluong, dongia, dvdl_fk, thueVAT ,dongia  " +
									"from ERP_DonDatHang_SANPHAM  " +
									"where dondathang_fk = '" + lsxId + "' ";
							System.out.println("1.Insert DDH - SP: " + query);
							if(!db.update(query))
							{
								msg = "Không thể tạo mới ERP_DONDATHANGNPP_SANPHAM " + query;
								db.getConnection().rollback();
								return msg;
							}
							
							
							// CHECK TON KHO CUA CẤP TRÊN CÓ ĐỦ KHÔNG
							
							//CHECK BOOKED THEO DV CHUAN
							query =  "select khoxuat_fk as kho_fk, '" + tructhuoc_fk + "' as npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT,  " +
									"	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = dathang.khoxuat_fk and sanpham_fk = sp.PK_SEQ and kbh_fk = dathang.kbh_fk and npp_fk = dathang.npp_fk ), 0) as tonkho  " +
									"from     " +
									"(     " +
									"	select c.kho_fk as khoxuat_fk, '"+tructhuoc_fk+"' as npp_fk , '" + kbhId + "' kbh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
									"			case when a.dvdl_fk IS null then a.soluong      " +
									"				 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
									"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )      " +
									"								 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )	 end as soluong   " +
									"	from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
									"			inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq " +
									"                                                                                " +
									"	where a.dondathang_fk in ( " + ddhId + " )     " +
									")     " +
									"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
									"group by khoxuat_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN  ";
							
							System.out.println("--CHECK TON KHO: " + query);
							String nppID = "";
							rs = db.get(query);
							{
								while(rs.next())
								{
									String khoID = rs.getString("kho_fk");
									String kbhID = rs.getString("kbh_fk");
									nppID = rs.getString("npp_fk");
									String spID = rs.getString("PK_SEQ");
									
									double soluong = rs.getDouble("soluongXUAT");
									double tonkho = rs.getDouble("tonkho");
									
									if(soluong > tonkho)
									{
										msg = "Sản phẩm ( " + rs.getString("TEN") + " ) với số lượng yêu cầu ( "+ rs.getString("soluongXUAT")+ " ) không đủ tồn kho ( "+ rs.getString("tonkho")+ " ) của chi nhánh ( "+ tructhuocTEN+ " ). Vui lòng kiểm tra lại.";
										db.getConnection().rollback();
										rs.close();
										return msg;
									}
									
									//CAP NHAT KHO XUAT TONG
									query = "Update NHAPP_KHO set booked = booked + '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
											"where KHO_FK = '" + khoID + "' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' ";
									if(db.updateReturnInt(query)!=1)
									{
										msg = "Khong the cap nhat NHAPP_KHO: " + query;
										db.getConnection().rollback();
										rs.close();
										return msg;
									}
								}
								rs.close();
							}
							
							msg= util.Check_Kho_Tong_VS_KhoCT(nppID, db);
							if(msg.length()>0)
							{
								db.getConnection().rollback();
								return msg;
							}
						}
					}
				}
				rs.close();
				
				db.getConnection().commit();
				db.shutDown();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		
		return "";
	}
	
	private String DeleteChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_Dondathang set trangthai = '3' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "1.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}
		
		String loaidonhang = request.getParameter("loaidonhang");
		if(loaidonhang == null)
			loaidonhang = "0";
		
		
		IErpDondathangNppList obj = new ErpDondathangNppList();
		obj.setLoaidonhang(loaidonhang);
		
		Utility util = new Utility();
		
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		String nppId = "";
		
		if(session.getAttribute("nppId") != null )
			nppId = (String) session.getAttribute("nppId");
		obj.setNppId(nppId);
		
		if(action.equals("Tao moi"))
		{
			IErpDondathangNpp lsxBean = new ErpDondathangNpp();
			lsxBean.setLoaidonhang(loaidonhang);
			lsxBean.setUserId(userId);
			
			lsxBean.createRs();
			session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
			
			session.setAttribute("lsxBean", lsxBean);
			
			//String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppNew.jsp";
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppNew.jsp";
			if(loaidonhang.equals("4"))
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangKhacNppNew.jsp";
			else if(loaidonhang.equals("2"))
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMTichLuyNppNew.jsp";
			else if(loaidonhang.equals("1"))
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMUngHangNppNew.jsp";
			else if(loaidonhang.equals("3"))
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangTrungBayNppNew.jsp";
			
			
			
			
			response.sendRedirect(nextJSP);
		}
		else
		{
			if(action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				obj.setUserId(userId);
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				
				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNpp.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String search = getSearchQuery(request, obj);
				obj.setUserId(userId);
				obj.init(search);
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);
				
				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNpp.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDondathangNppList obj)
	{
		Utility util = new Utility();
		
		String query = 
				"select a.PK_SEQ, a.trangthai, a.ngaydonhang, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.NPP_DACHOT,isnull(a.isKm,0) as isKm  " +
						"from ERP_Dondathang a inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq " +
						"	left join ERP_KHOTT b on a.kho_fk = b.pk_seq  " +
						"	left join KHO d on a.kho_fk = d.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		String maddh = request.getParameter("ddh");
		if (maddh == null)
			maddh = "";
		obj.setMaDDH(maddh);
		
		String iskm = util.antiSQLInspection(request.getParameter("iskm")==null?"0":request.getParameter("iskm"));
		obj.setIsKm(iskm);

		
		if(tungay.length() > 0)
			query += " and a.ngaydonhang >= '" + tungay + "'";
		
		
		if(iskm.length() > 0)
			query += " and a.iskm = '" + iskm + "' ";
		
		if(denngay.length() > 0)
			query += " and a.ngaydonhang <= '" + denngay + "'";
		
		if(trangthai.length() > 0){
			if(trangthai.equals("0"))
				query += " and (a.TrangThai = '0' and a.NPP_DACHOT = '0')";
			else if(trangthai.equals("1"))
				query += " and ((a.TrangThai = '1' and a.NPP_DACHOT = '1') or (a.TrangThai = '0' and a.NPP_DACHOT = '1'))";
			else
				query += " and a.TrangThai = '"+trangthai+"'";
		}
		
		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}
		if (maddh.length() > 0) {
			query += " and a.PK_SEQ like  '%" + maddh + "%'";
		}
		
		System.out.println("Query: "+query);
		System.out.println("\n"+trangthai+"\n");
		System.out.println("NPP: "+nppId);
		return query;
	}
	
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	
}
