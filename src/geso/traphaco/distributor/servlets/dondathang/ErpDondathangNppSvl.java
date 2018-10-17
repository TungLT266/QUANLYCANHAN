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
			
			String nhomkenhId = "";
			if(session.getAttribute("nhomkenhId") != null )
				nhomkenhId = (String) session.getAttribute("nhomkenhId");
			
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
				
				/*String sql = " select tructhuoc_fk, loaiNPP from NHAPHANPHOI where pk_seq = '" + nppId + "' ";
				ResultSet rs = db.get(sql);
				String tructhuocId = "";
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
				}*/
				
				String command = "";
				/*command =  "select a.DVDL_FK as dvCHUAN, ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvt + "' ) as dvNEW,      " +
							"	isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK and DVDL2_FK = ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvt + "' ) ), 0 ) as quydoi,  	  " +
							"	isnull( ( select dongia from BANGGIABANDOITAC_SANPHAM where sanpham_fk = a.pk_seq  " +
							"					and BGBANDOITAC_FK in ( select top(1) BANGGIABANDOITAC_FK from BANGGIABANDOITAC_DOITAC where  NPP_FK = '" + nppId + "' and BANGGIABANDOITAC_FK in ( select pk_seq from BANGGIABANDOITAC where  NPP_FK = '" + tructhuocId + "' and trangthai = '1' and KENH_FK = '" + kbhId + "' ) ) ), 0) as giamua   " +
							"from SANPHAM a where a.MA = '" + spMa + "'  ";*/
				command =  	 "select a.DVDL_FK as dvCHUAN, ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvt + "' ) as dvNEW,       " + 
							 "	isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK and DVDL2_FK = ( select PK_SEQ from DONVIDOLUONG where DONVI = N' dvt + ' ) ), 0 ) as quydoi,  	   " + 
							 "	isnull( ( select GIAMUA_SAUCK from BGMUANPP_SANPHAM where sanpham_fk = a.pk_seq   " + 
							 "					and BGMUANPP_FK in (  select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK = '" + dvkdId + "' and  bg.NHOMKENH_FK = '" + nhomkenhId + "'  order by bg.TUNGAY desc ) ), 0) as giamua    " + 
							 "from SANPHAM a where a.MA = '" + spMa + "' ";
				
				System.out.println("Lay don gia san pham: " + command);
				String kq  = "0";
				ResultSet rs = db.get(command);
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
			
			query = "select isnull(a.LoaiDonHang,0) as LoaiDonHang, b.loaiNPP, a.tructhuoc_fk, a.kho_fk, a.npp_fk, a.ngaydenghi, a.kbh_fk, a.kho_fk, a.dvkd_fk,a.iskm, a.nhomkenh_fk  " +
					"from ERP_Dondathang a inner join NHAPHANPHOI b on a.npp_fk = b.pk_seq " +
					"where a.pk_seq = '" + lsxId + "' ";
			ResultSet rs = db.get(query);
			String loaiNPP = "";
			String tructhuoc_fk = "";
			String kho_fk = "";
			String LoaiDonHang = "";
			String isKm = "0";
			
			if(rs.next())
			{
				loaiNPP = rs.getString("loaiNPP");
				tructhuoc_fk = rs.getString("tructhuoc_fk");
				kho_fk = rs.getString("kho_fk");
				LoaiDonHang = rs.getString("LoaiDonHang");
				isKm = rs.getString("isKm")==null?"0":rs.getString("isKm");
				
				//PHANAM đổi lại, tất cả đều phải qua quy trình duyệt đơn đặt hàng
				if(!LoaiDonHang.equals("4"))
				{				
					if(tructhuoc_fk.trim().length() >= 6 )  
					{
						query = " insert ERP_DonDatHangNPP(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, npp_dat_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, NOTE, NPP_DACHOT, from_dondathang,tructhuoc_fk,isKM, nhomkenh_fk) " +
								" select ngaydenghi, ngaydenghi, 0, ghichu, 0 as trangthai, dvkd_fk, kbh_fk, ( select tructhuoc_fk from NHAPHANPHOI where pk_seq = a.NPP_FK ), npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, N'Convert từ đơn đặt hàng " + lsxId + "', '1', '"+ lsxId+ "','"+tructhuoc_fk+"','"+isKm+"', nhomkenh_fk  "+
								" from ERP_DonDatHang a where pk_seq = '" + lsxId + "' ";
						
						System.out.println("1.Insert DDH: " + query);
						if(!db.update(query))
						{
							msg = "Không thể tạo mới ERP_DonDatHangNPP " + query;
							db.getConnection().rollback();
							return msg;
						} 
						
						query = " select scope_identity() as ddhId, " + 
								" 	( select ten from NHAPHANPHOI where pk_seq = '" + tructhuoc_fk + "' ) as tructhuocTEN, " + 
								"   case when 1= ( select DungChungKenh from NHAPHANPHOI where pk_seq = '" + tructhuoc_fk + "' ) then '100000' else a.nhomkenh_fk end  as nhomkenh_fk " +
								" from ERP_DonDatHang a  where pk_seq = '" + lsxId + "' ";
						System.out.println("2.Info: " + query);
						
						String ddhId = "";
						String tructhuocTEN = "";
						String nhomkenhId = "";
						
						rs = db.get(query);
						{
							if(rs.next())
							{
								ddhId = rs.getString("ddhId");
								nhomkenhId = rs.getString("nhomkenh_fk");
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
						
						
						// CHECK TON KHO CUA CẤP TRÊN CÓ ĐỦ KHÔNG -> CONG TY MTV KHONG PHAN BIET NHOM KENH, CHI CO 1 NHOM DUY NHAT CHAY NGAM
						
						//CHECK BOOKED THEO DV CHUAN
						query =  "select khoxuat_fk as kho_fk, '" + tructhuoc_fk + "' as npp_fk, nhomkenh_fk, sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT,  " +
								"	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = dathang.khoxuat_fk and sanpham_fk = sp.PK_SEQ and nhomkenh_fk = dathang.nhomkenh_fk and npp_fk = dathang.npp_fk ), 0) as tonkho  " +
								"from     " +
								"(     " +
								"	select c.kho_fk as khoxuat_fk, '" + tructhuoc_fk + "' as npp_fk , '" + nhomkenhId + "' as nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
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
								"group by khoxuat_fk, npp_fk, nhomkenh_fk, sp.PK_SEQ, sp.TEN  ";
						
						System.out.println("--CHECK TON KHO: " + query);
						String nppID = "";
						rs = db.get(query);
						{
							while(rs.next())
							{
								String khoID = rs.getString("kho_fk");
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
										"where KHO_FK = '" + khoID + "' and nhomkenh_fk = '" + nhomkenhId + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' ";
								if(db.updateReturnInt(query) != 1)
								{
									msg = "Khong the cap nhat NHAPP_KHO: " + query;
									db.getConnection().rollback();
									rs.close();
									return msg;
								}
							}
							rs.close();
						}
						
						msg = util.Check_Kho_Tong_VS_KhoCT(nppID, db);
						if(msg.length()>0)
						{
							db.getConnection().rollback();
							return msg;
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
			session.setAttribute("nhomkenhId", lsxBean.getNhomkenhId());
			session.setAttribute("nppId", lsxBean.getNppId());
			
			session.setAttribute("lsxBean", lsxBean);
			
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
