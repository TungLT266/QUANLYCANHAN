package geso.traphaco.distributor.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import geso.traphaco.center.beans.dondathang.imp.XLkhuyenmaiTT_Import;
import geso.traphaco.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.traphaco.distributor.beans.trakhuyenmai.ITrakhuyenmai;
import geso.traphaco.distributor.beans.trakhuyenmai.imp.Trakhuyenmai;
import geso.traphaco.distributor.db.sql.dbutils;

public class Toll 
{
	public static void main(String[] arg) 
	{
		Toll toll = new Toll();
		
		//dbutils db = new dbutils();
		//toll.CapNhatLaiSOLO(db, "2015-07-01", "2015-07-07");
		
		//toll.TachHoaDon();
		
		//toll.CapNhatKhuyenMai();
		
		
		//toll.CapNhatDonGia_INS_2SoLe();
		
		
		//toll.ApKhuyenMai_Import_TuDong();
		
		toll.CapNhat_HoaDonIMPORT();
		
		//toll.CapNhatHopDong_NguyenTac();
		
		
	
	}

	/**************** CAP NHAT LAI KHUYEN MAI *********************/ 
	
	public String CapNhatKhuyenMai()
	{
		dbutils db = new dbutils();
		
		try 
		{
			String query =   "select km.stt, km.machungtu, masanpham as maOLD, scheme, hoadon_fk, dh.PK_SEQ as donhang_fk , ctkm_fk, soluong,  " + 
							 "		( select PK_SEQ from ERP_YCXUATKHONPP where hoadon_fk = km.hoadon_fk ) as ycxk_fk, " + 
							 "		sp.MA as masanpham, sp.PK_SEQ as idsanpham " + 
							 "from KHUYENMAI_UPDATE km left join ERP_DONDATHANGNPP dh on km.machungtu = dh.machungtu " + 
							 "		left join SANPHAM sp on km.masanpham = sp.MA_FAST " + 
							 "where dh.TRANGTHAI != 3 and ctkm_fk is not null ";
			System.out.println("::: INIT: " + query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while( rs.next() )
				{
					String donhang_fk = rs.getString("donhang_fk") == null ? "" : rs.getString("donhang_fk");
					String hoadon_fk = rs.getString("hoadon_fk") == null ? "" : rs.getString("hoadon_fk");
					String ycxk_fk = rs.getString("ycxk_fk") == null ? "" : rs.getString("ycxk_fk");
					
					String scheme = rs.getString("scheme");
					String ctkm_fk = rs.getString("ctkm_fk");
					String masanpham = rs.getString("masanpham");
					String idsanpham = rs.getString("idsanpham");
					
					String msg = this.CapNhatScheme(db, donhang_fk, hoadon_fk, ycxk_fk, scheme, ctkm_fk, masanpham, idsanpham);
					if(msg.trim().length() > 0)
					{
						query = "Insert KHUYENMAI_UPDATE_LOG ( stt, msg ) values ( '" + rs.getString("stt") + "', N'" + msg.replaceAll("'", "''") + "' ) ";
						System.out.println(":::: LOI: " + query);
						db.update(query);
					}
				}
				rs.close();
			}
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return "Lỗi: " + e.getMessage();
			
		}
		
		return "";
		
	}

	private String CapNhatScheme(dbutils db, String donhang_fk, String hoadon_fk, String ycxk_fk, String scheme, String ctkm_fk,
			String masanpham, String idsanpham)
	{
		String query = "";
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			if(donhang_fk.trim().length() > 0)
			{
				query = "update ERP_DONDATHANGNPP_CTKM_TRAKM set CTKMID = '" + ctkm_fk + "' where DONDATHANGID = '" + donhang_fk + "' and SPMA = '" + masanpham + "' ";
				System.out.println("1. " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "1.Lỗi cập nhật: " + query;
				}
				
				query = "update ERP_DONDATHANGNPP_SANPHAM_CHITIET set scheme = '" + scheme + "' where dondathang_fk = '" + donhang_fk + "' and SANPHAM_FK = '" + idsanpham + "' and dbo.Trim( scheme ) != '' ";
				System.out.println("2. " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "2.Lỗi cập nhật: " + query;
				}
			}
			
			if(hoadon_fk.trim().length() > 0)
			{
				query = "update ERP_HOADONNPP_SP set SCHEME = '" + scheme + "' where HOADON_FK = '" + hoadon_fk + "' and SANPHAM_FK = '" + idsanpham + "' and dbo.Trim( scheme ) != '' ";
				System.out.println("3. " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "3.Lỗi cập nhật: " + query;
				}
				
				query = "update ERP_HOADONNPP_SP_CHITIET set scheme = '" + scheme + "' where HOADON_FK = '" + hoadon_fk + "' and MA = '" + masanpham + "' and dbo.Trim( scheme ) != ''";
				System.out.println("4. " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "4.Lỗi cập nhật: " + query;
				}
			}
			
			if(ycxk_fk.trim().length() > 0)
			{
				query = "update ERP_YCXUATKHONPP_SANPHAM set SCHEME = '" + scheme + "' where ycxk_fk = '" + ycxk_fk + "' and sanpham_fk = '" + idsanpham + "' and dbo.Trim( scheme ) != '' ";
				System.out.println("5. " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "5.Lỗi cập nhật: " + query;
				}
				
				query = "update ERP_YCXUATKHONPP_SANPHAM_CHITIET set scheme = '" + scheme + "' where YCXK_FK = '" + ycxk_fk + "' and sanpham_fk = '" + idsanpham + "' and dbo.Trim( scheme ) != ''";
				System.out.println("6. " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "6.Lỗi cập nhật: " + query;
				}
				
				query = "update ERP_YCXUATKHONPP_SANPHAM_THUCGIAO set SCHEME = '" + scheme + "' where ycxk_fk = '" + ycxk_fk + "' and sanpham_fk = '" + idsanpham + "' and dbo.Trim( scheme ) != '' ";
				System.out.println("7. " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "7.Lỗi cập nhật: " + query;
				}
				
				query = "update ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET set scheme = '" + scheme + "' where YCXK_FK = '" + ycxk_fk + "' and sanpham_fk = '" + idsanpham + "' and dbo.Trim( scheme ) != ''";
				System.out.println("8. " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "8.Lỗi cập nhật: " + query;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try {
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
			} 
			catch (Exception e1) { }
			
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	/*************** END CAP NHAT KM ******************************/
	
	
	/*************** ÁP KHUYẾN MẠI TỰ ĐỘNG ************************/
	
	public String ApKhuyenMai_Import_TuDong()
	{
		dbutils db = new dbutils();
		
		String condition = "";
		condition = " and khachhang_fk in ( 101764, 110639 ) ";
		//condition = " and pk_seq in ( 134028 ) ";
		
		String query = " select PK_SEQ, khachhang_fk, NPP_FK, NgayDonHang, machungtu, kho_fk, khachhangKG_FK " + 
					   " from ERP_DONDATHANGNPP dh where LoaiDonHang in ( 1, 2 ) and NOTE = N'Import ngay 30-07' and TRANGTHAI != 3 ";
		/*String query =  " select PK_SEQ, khachhang_fk, NPP_FK, NgayDonHang, machungtu, kho_fk, khachhangKG_FK " + 
				   		" from ERP_DONDATHANGNPP dh where LoaiDonHang in ( 1, 2 )  and TRANGTHAI != 3 ";*/
		if( condition.trim().length() > 0 )
			query += condition;
		
		System.out.println("++ DANH SACH DON HANG: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while( rs.next() )
				{
					String dhId = rs.getString("pk_seq");
					String nppId = rs.getString("npp_fk");
					String khId = rs.getString("khachhang_fk");
					String ngaydonhang = rs.getString("NgayDonHang");
					String machungtu = rs.getString("machungtu");
					
					//String kho_fk = rs.getString("kho_fk") == null ? "" : rs.getString("kho_fk") ;
					//String khachhangKG_FK = rs.getString("khachhangKG_FK") == null ? "" : rs.getString("khachhangKG_FK") ;
					
					//String sanphamCHONTRUOC = " select 100159 as sanpham_fk, 1 as chon union select 100114 as sanpham_fk, 1 as chon ";
					
					String sanphamCHONTRUOC = "";
					query = "select distinct sanpham_fk, soloCHUAN, sohoadonCHUAN from ERP_DONDATHANGNPP_TEMP where thanhtien_TRUOCVAT = 0 and machungtu = '" + machungtu + "'";
					ResultSet rsChontruoc = db.get(query);
					if(rsChontruoc != null)
					{
						while(rsChontruoc.next())
						{
							sanphamCHONTRUOC += "select " + rsChontruoc.getString("sanpham_fk") + " as sanpham_fk, '" + rsChontruoc.getString("soloCHUAN") + "' as solo, '" + rsChontruoc.getString("sohoadonCHUAN") + "' as sohoadon, 1 as chon";
							sanphamCHONTRUOC += " union ";
						}
						rsChontruoc.close();
					}
					
					if(sanphamCHONTRUOC.trim().length() > 0)
						sanphamCHONTRUOC = sanphamCHONTRUOC.substring(0, sanphamCHONTRUOC.length() - 7);
					System.out.println("-- SAN PHAM CHON TRUOC: " + sanphamCHONTRUOC);
					
					Hashtable<String, Integer> sanpham_soluong = new Hashtable<String, Integer>();
					Hashtable<String, Float> sanpham_dongia = new Hashtable<String, Float>();
					Hashtable<String, Float> sanpham_quycach = new Hashtable<String, Float>();
					
					String soluong1 = "";
					String spMaKM = "";
					String spSOLUONGKM = "";
					String spDONGIAKM = "";
					float tongiatriDH = 0;

					query = "  select ( select ma from SANPHAM where PK_SEQ = a.sanpham_fk ) as spMA, a.dvdl_fk, b.DVDL_FK as dvCHUAN, a.sohoadon_import, " +
							" 		case when a.dvdl_fk = b.DVDL_FK then a.soluong  " +
							"  			 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )   end as soluong, " +
							"  		case when a.dvdl_fk = b.DVDL_FK then a.dongiaGOC  " +
							"  			 else  a.dongiaGOC / ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )   end as dongia,  " +
							"  		case when a.dvdl_fk = b.DVDL_FK then 1  " +
							"  			 else  ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )   end as quycach,  isnull( a.thueVAT, 0) as  thueVAT   " +
							"  from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ     " +
							"  where a.dondathang_fk = '" + dhId + "'  " ;
					System.out.println("-- SAN PHAM AP KM: " + query);
					
					ResultSet rsSP = db.get(query);
					
					String sohoadon_import = "";
					if(rsSP != null)
					{
						try 
						{
							while(rsSP.next())
							{
								float dongiaSAUVAT = rsSP.getFloat("dongia") * ( 1 + rsSP.getFloat("thueVAT") / 100 );
								
								sanpham_soluong.put(rsSP.getString("spMA"), rsSP.getInt("soluong"));
								sanpham_dongia.put(rsSP.getString("spMA"), dongiaSAUVAT  );
								sanpham_quycach.put(rsSP.getString("spMA"), rsSP.getFloat("quycach"));
								
								spMaKM += rsSP.getString("spMA") + "__";
								spSOLUONGKM += rsSP.getInt("soluong") + "__";
								spDONGIAKM += dongiaSAUVAT + "__";
								
								soluong1 += rsSP.getString("quycach") + "__";
								tongiatriDH += rsSP.getInt("soluong") * dongiaSAUVAT;
								
								sohoadon_import = rsSP.getString("sohoadon_import");
							}
							rsSP.close();
						} 
						catch (Exception e) {}	
					}
					
					//ÁP KHUYẾN MẠI TRÊN TỪNG ĐƠN HÀNG
					String msg = ""; //nhung ctkm khong thoa
					if(spMaKM.trim().length() > 0 )
					{
						XLkhuyenmaiTT_Import xlkm = new XLkhuyenmaiTT_Import("100002", ngaydonhang, nppId, khId, dhId); //ngay giao dich trong donhang
						
						xlkm.setMasp(spMaKM.substring(0, spMaKM.length() - 2).split("__"));
						xlkm.setSoluong(spSOLUONGKM.substring(0, spSOLUONGKM.length() - 2).split("__"));
						xlkm.setDongia(spDONGIAKM.substring(0, spDONGIAKM.length() - 2).split("__"));
						xlkm.setQuycach(soluong1.substring(0, soluong1.length() - 2).split("__"));
						
						xlkm.setTonggiatriDh(tongiatriDH);
						xlkm.setIdDonhang(dhId);
						xlkm.setNgaygiaodich(ngaydonhang);
						xlkm.setLoaiDonHang("0");
						
						xlkm.setHashA(sanpham_soluong);
						xlkm.setHashB(sanpham_dongia);
						xlkm.setHash_QuyCach(sanpham_quycach);
						
						xlkm.setDieuchinh(true); //Lay truong hop ngau nhien /*****OneOne set lai la True******/
						
						xlkm.ApKhuyenMai();
						
					    List<ICtkhuyenmai> ctkmResual = xlkm.getCtkmResual();
					    System.out.println("+++ Đơn hàng: " + dhId + " - So xuat khuyen mai duoc huong: " + ctkmResual.size() + "\n");
					    
					    //Cứ lưu lại KM trước, rồi xử lý sau
					    msg = "";
						for(int i = 0; i < ctkmResual.size(); i++)
					    {
					    	ICtkhuyenmai ctkhuyenmai = ctkmResual.get(i);
					    	
					    	//System.out.println("ConFi laf: "+ctkhuyenmai.getConfirm());
					    	if(ctkhuyenmai.getConfirm() == false) //khong dung chung san pham
					    	{	
					    		msg += CreateKhuyenmai(db, ctkhuyenmai, dhId, nppId, ngaydonhang, Math.round(tongiatriDH), sanpham_soluong, sanpham_dongia, sanphamCHONTRUOC, sohoadon_import);
					    		
						    	//remove khoi danh sach
					    		ctkmResual.remove(i);	
					    		i = i -1;
					    	}
					    }
					}
					
					//AP CHIET KHAU - CHIA GIA
					ApChietKhau(dhId, nppId, db);
					
					if(msg.length() > 0)
		    		{
		    			query = "insert KHUYENMAI_IMPORT_LOG( ddh_fk, loiIMPORT ) " + 
		    					" values ( '" + dhId + "', N'" + msg.replaceAll("'", "''") + "' ) ";
		    			
		    			System.out.println("::: LOI KHI CHAY KM: " + msg);
		    			db.update(query);
		    		}
					
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		db.shutDown();
		System.out.println(":::: CHAY XONG KHUYEN MAI TU DONG....");
		return "";
	
	}
	
	private String CreateKhuyenmai(dbutils db, ICtkhuyenmai ctkm, String id, String nppId, String tungay, long tongGtridh, Hashtable<String, Integer> sp_sl, Hashtable<String, Float> sp_dg, String sanphamCHONTRUOC, String sohoadon_import )
	{
		String str = "";
		
		try 
		{ 
			db.getConnection().setAutoCommit(false);
		
			String query = "delete ERP_DONDATHANGNPP_CTKM_TRAKM where DONDATHANGID = '" + id + "' and ctkmId = '" + ctkm.getId() + "' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				str = query;
				return str;
			}
			
			//List<ITrakhuyenmai> trakmList = ctkm.getTrakhuyenmai();
			//for(int count = 0; count < trakmList.size(); count++)
			//{		
				//ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(count);		
				ITrakhuyenmai trakm = find_trakhuyenmai( db, ctkm, sanphamCHONTRUOC );
				
				long tongtienTraKM = 0;
				if(trakm.getType() == 1)
					tongtienTraKM = Math.round(ctkm.getSoxuatKM() * trakm.getTongtien());
				else
				{
					if(trakm.getType() == 2) //tra chiet khau
					{
						System.out.println("___Tong tien tra km: " + ctkm.getTongTienTheoDKKM() + " -- Chiet khau: " + trakm.getChietkhau());
						//Tinh tong gia tri tra khuyen mai theo dieu kien (chu khong phai tren tong gia tri don hang)
						long tonggiatriTrakm = ctkm.getTongTienTheoDKKM();
						tongtienTraKM = Math.round(tonggiatriTrakm * (trakm.getChietkhau() / 100));
						//tongtienTraKM = Math.round(tongGtridh * (trakm.getChietkhau() / 100));
					}
					else
					{
						if(trakm.getHinhthuc() == 1)
						{
							tongtienTraKM = Math.round(ctkm.getSoxuatKM() * trakm.getTongGtriKm());
						}
					}
				}
				
				if(trakm.getType() == 3) //san pham co so luong co dinh
				{
					if(trakm.getHinhthuc() == 1)
					{
						String sql = "select f.pk_seq as spId, a.soluong, 0 as dongia, f.ma as spMa  " +
									 "from Trakhuyenmai_sanpham a inner join SANPHAM f on a.SANPHAM_FK = f.PK_SEQ " +
									 "where a.TRAKHUYENMAI_FK = '" + trakm.getId() + "' ";
						
						//System.out.println("Query lay gia san pham co dinh la: " + sql + "\n");
						ResultSet rsSQl = db.get(sql);
						if(rsSQl != null)
						{
							while(rsSQl.next())
							{
								int slg = Integer.parseInt(rsSQl.getString("soluong")) * (int)ctkm.getSoxuatKM();
								long tonggtri = Math.round(slg * rsSQl.getFloat("dongia"));
								
								query = "Insert into ERP_DONDATHANGNPP_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong, sohoadon_import) " + 
										"values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + Long.toString(tonggtri) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "', '" + sohoadon_import + "')";
								System.out.println("1.Chen khuyen mai co dinh: " + query);
								
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}
							}
						}
						rsSQl.close();
					}
					else //Đề xuất 1 sản phẩm ( ưu tiên danh sách sản phẩm đã được chọn trước )
					{
						//Mỗi trả KM đề xuất 1 sản phẩm
						boolean timduocSP_TRA = false;
						if( sanphamCHONTRUOC.trim().length() > 0 )
						{
							String sql = "select top(1) a.SANPHAM_FK, b.MA as spMa, 0 as dongia, isnull( chonSAN.solo, '' ) as solo, isnull( chonSAN.sohoadon, '' ) as sohoadon " + 
										 "from TRAKHUYENMAI_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ  " + 
										 "	  inner join ( " + sanphamCHONTRUOC + " ) chonSAN on b.PK_SEQ = chonSAN.sanpham_fk " + 
										 "where a.TRAKHUYENMAI_FK = '" + trakm.getId() + "' " + 
										 "order by ISNULL(chonSAN.chon, 0) desc  ";

							System.out.println("::: LAY KM TU DONG: " + sql + "\n");
							ResultSet rsSQl = db.get(sql);
							if(rsSQl != null)
							{
								while(rsSQl.next())
								{
									timduocSP_TRA = true;
									int slg = trakm.getTongluong() * (int)ctkm.getSoxuatKM();
									long tonggtri = Math.round(slg * rsSQl.getFloat("dongia"));
									String solo_import = rsSQl.getString("solo");
									if( rsSQl.getString("sohoadon").trim().length() > 0 )
										sohoadon_import = rsSQl.getString("sohoadon").trim();
									
									query = "Insert into ERP_DONDATHANGNPP_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong, sohoadon_import, solo_import) " + 
											"values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + Long.toString(tonggtri) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "', '" + sohoadon_import + "', '" + solo_import + "')";
									System.out.println("1.1.Chen khuyen mai co dinh: " + query);

									if(!db.update(query))
									{
										db.getConnection().rollback();
										str = query;
										return str;
									}
								}
							}
							rsSQl.close();
						}
						
						if( sanphamCHONTRUOC.trim().length() <= 0 || !timduocSP_TRA )
						{
							//Lấy ngẫu nhiên 1 sản phẩm đầu tiên tìm được
							String sql = "select top(1) a.SANPHAM_FK, b.MA as spMa, 0 as dongia, '' as solo " + 
										 "from TRAKHUYENMAI_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ  " + 
										 "where a.TRAKHUYENMAI_FK = '" + trakm.getId() + "' ";
	
							System.out.println("::: LAY KM TU DONG - MAC DINH: " + sql + "\n");
							ResultSet rsSQl = db.get(sql);
							if(rsSQl != null)
							{
								while(rsSQl.next())
								{
									timduocSP_TRA = true;
									int slg = trakm.getTongluong() * (int)ctkm.getSoxuatKM();
									long tonggtri = Math.round(slg * rsSQl.getFloat("dongia"));
	
									query = "Insert into ERP_DONDATHANGNPP_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong, sohoadon_import, solo_import) " + 
											"values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + Long.toString(tonggtri) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "', '" + sohoadon_import + "', '')";
									System.out.println("1.2.Chen khuyen mai co dinh: " + query);
	
									if(!db.update(query))
									{
										db.getConnection().rollback();
										str = query;
										return str;
									}
								}
							}
							rsSQl.close();
						}
						
					}
				}
				else
				{
					query = "Insert into ERP_DONDATHANGNPP_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, sohoadon_import) " + 
							" values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + tongtienTraKM + "', '" + sohoadon_import + "')";
					System.out.println("10.Chen khuyen mai tien / ck: " + query);
					if(!db.update(query))
					{
						db.getConnection().rollback();
						str = query;
						return str;
					}
				}
			//}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e1)
		{
			try 
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
			} 
			catch (SQLException e) {}
			
			e1.printStackTrace();
			return "Loi khi tao moi ctkm " + ctkm + ", " + e1.toString(); 
		}
		
		return str;
		
	}

	private ITrakhuyenmai find_trakhuyenmai(dbutils db, ICtkhuyenmai ctkm, String sanphamCHONTRUOC) 
	{
		ITrakhuyenmai tkm = new Trakhuyenmai();
		
		if( sanphamCHONTRUOC.trim().length() <= 0 )
			tkm = ctkm.getTrakhuyenmai().get(0);
		else
		{
			//Tìm trả KM có sản phẩm này
			String query =  "select top(1) TRAKHUYENMAI_FK from TRAKHUYENMAI_SANPHAM  " + 
					 		"where TRAKHUYENMAI_FK in ( select TRAKHUYENMAI_FK from CTKM_TRAKM where CTKHUYENMAI_FK = '" + ctkm.getId() + "' ) " + 
					 		"	and SANPHAM_FK in ( select SANPHAM_FK from ( " + sanphamCHONTRUOC + " ) CHON ) ";
			System.out.println(":::: TIM TRA KM MAC DINH: " + query );
			ResultSet rs = db.get(query);
			String tkmId = "";
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						tkmId = rs.getString("TRAKHUYENMAI_FK");
					}
					rs.close();
				} 
				catch (Exception e) { }
			}
			
			if(tkmId.trim().length() > 0)
			{
				for(int i = 0; i < ctkm.getTrakhuyenmai().size(); i++ )
				{
					if( ctkm.getTrakhuyenmai().get(i).getId().equals(tkmId) )
					{
						tkm = ctkm.getTrakhuyenmai().get(i);
						break;
					}
				}
			}
			else //Lấy 1 tkm mặc định
			{
				tkm = ctkm.getTrakhuyenmai().get(0);
			}
		}
		
		
		return tkm;
	}
	
	public void ApChietKhau(String ddhId, String nppId, dbutils db) 
	{
		//ÁP LẠI CHIẾT KHẤU CSBH VÀ CÁC DẠNG CHIẾT KHẤU TRỪ THẲNG VÀO ĐƠN GIÁ
		String query = "update ERP_DONDATHANGNPP_SANPHAM set dongia = dongiaGOC, chietkhau_CSBH = 0, chinhsach_fk = null, chietkhau_KM = 0, schemeCHIETKHAU = '' where dondathang_fk = '" + ddhId + "' ";
		db.update(query);
		
		String loaidonhang = "1";
		
		//TINH LAI CAC KM CO CHIET KHAU
		if(loaidonhang.equals("1") || loaidonhang.equals("2") )
		{
			query = " select CTKMID, TRAKMID, TONGGIATRI, " + 
					" 	( select count(*) from DANGKYKM_TICHLUY_KHACHHANG where KHACHHANG_FK = b.khachhang_fk ) as dangkyTL, " + 
					"	ISNULL( ( select CHIETKHAU from TRAKHUYENMAI where PK_SEQ = a.TRAKMID ), 0) as ptChietkhau,	" +
					"	( select DKKHUYENMAI_FK from CTKM_DKKM where CTKHUYENMAI_FK = a.CTKMID ) as dkkmId,	" +
					"	( select SCHEME from CTKHUYENMAI where pk_seq = a.CTKMID ) as SCHEME	" +
					" from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join ERP_DONDATHANGNPP b on a.dondathangId = b.pk_seq " +
					" where DONDATHANGID = '" + ddhId + "' and a.spMA is NULL and CTKMID in ( select PK_SEQ from CTKHUYENMAI where chiavaodongia = 1 )";
			System.out.println("::::LAY CTKM DANG HUONG: " + query );
			int dangkyTL = 0;
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					while(rs.next())
					{
						//NHỮNG CTKM NÀY PHẢI KHÔNG NẰM TRONG DANH SÁCH ĐĂNG KÝ TÍCH LŨY 
						String ctkmId = rs.getString("CTKMID");
						//String trakmId = rs.getString("TRAKMID");
						//double tongiatri = rs.getDouble("TONGGIATRI");
						double ptChietkhauKM = rs.getDouble("ptChietkhau");
						
						System.out.println(":::: CO DK TICH LUY: " + dangkyTL + " -- PT CHIET KHAU KM: " + ptChietkhauKM );
						if( ptChietkhauKM > 0 ) //thỏa điều kiện, chia thẳng vào đơn giá
						{
							//Phân bổ khuyến mại chiết khấu vào đơn giá
							query = "update dh set dh.chietkhau_KM = ( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) * " + ptChietkhauKM + " ) / 100.0) / ( 1 + thueVAT / 100.0 ), " + 
									" 	dh.schemeCHIETKHAU = N'" + rs.getString("SCHEME") + "' " +
									"from ERP_DONDATHANGNPP_SANPHAM	dh  " +
									"where dondathang_fk = '" + ddhId + "' and sanpham_fk in ( select sanpham_fk from DIEUKIENKM_SANPHAM where DIEUKIENKHUYENMAI_FK = '" + rs.getString("dkkmId") + "' )";
							
							System.out.println("::: CAP NHAT CHIET KHAU - KM: " + query );
							db.update(query);
						}
						
						//CHIA THẲNG VÀO GIÁ
						query = "delete ERP_DONDATHANGNPP_CTKM_TRAKM where DONDATHANGID = '" + ddhId + "' and CTKMID = '" + ctkmId + "' ";
						System.out.println("::: XOA CHIET KHAU - KM CHIA GIA: " + query );
						db.update(query);
					}
					rs.close();
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			//ÁP TIẾP CHÍNH SÁCH BÁN HÀNG NẾU CÓ
			if( loaidonhang.equals("1") || loaidonhang.equals("2") )
			{
				query = " select khachhang_fk, NgayDonHang, " +
						//" 	( select count(*) from DANGKYKM_TICHLUY_KHACHHANG where KHACHHANG_FK = a.khachhang_fk ) as dangkyTL " + 
						" 	( select count(*) from DANGKYKM_TICHLUY_KHACHHANG where KHACHHANG_FK = a.khachhang_fk and ngayketthuchd >= NgayDonHang ) as dangkyTL " + 
						" from ERP_DONDATHANGNPP a where pk_seq = '" + ddhId + "'";
				rs = db.get(query);
				
				String ngaydonhang = "";
				String khId = "";
				try 
				{
					if(rs.next())
					{
						ngaydonhang = rs.getString("NgayDonHang");
						khId = rs.getString("khachhang_fk");
						dangkyTL = rs.getInt("dangkyTL");
					}
					rs.close();
				} 
				catch (Exception e1) { }
				
				if( dangkyTL <= 0 )
				{
					query = "select a.pk_seq, ( select LCH_FK from KHACHHANG where PK_SEQ = '" + khId + "' ) as lch_fk, a.apdungchodaily, a.apdungchohopdong, " +
							"	( select SUM( round ( ( round ( soluong * dongiaGOC, 0 ) * ( 1 + thueVAT / 100.0 ) ), 0 ) ) from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + ddhId + "' and sanpham_fk in ( select sanpham_fk from CHINHSACHBANHANG_SANPHAM where chinhsachbanhang_fk = a.pk_seq ) ) as tongtienDONHANG 	" +
							"from CHINHSACHBANHANG a inner join CHINHSACHBANHANG_NPP b on b.chinhsachbanhang_fk = a.pk_seq   " +
							"where a.trangthai = '1' and tungay <= '" + ngaydonhang + "' and '" + ngaydonhang + "' <= denngay and b.npp_fk = '" + nppId + "' " + 
							" and a.pk_seq in ( select chinhsachbanhang_fk from CHINHSACHBANHANG_LOAIKHACHHANG where loaikhachhang_fk in ( select LCH_FK from KHACHHANG where pk_seq = '" + khId + "' ) ) " + 
							" and a.pk_seq in ( select chinhsachbanhang_fk from CHINHSACHBANHANG_KENH where kbh_fk in ( select kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + khId + "' ) ) " + 
							//" and ( a.tichluy_fk is NULL or " + khId + " not in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG where DKKMTICHLUY_FK in ( select pk_seq from DANGKYKM_TICHLUY where NPP_FK = '" + nppId + "' and TIEUCHITL_FK = a.tichluy_fk ) ) ) " + 
							//" and (  " + khId + " not in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG where DKKMTICHLUY_FK in ( select pk_seq from DANGKYKM_TICHLUY where NPP_FK = '" + nppId + "'  ) ) ) " + 
							" and " + khId + " not in ( select khachhang_fk from CHINHSACHBANHANG_KHACHHANG_KHONGAPDUNG where CSBH_FK = 100000 ) " + 
							" and " + ddhId + " not in ( select dondathang_fk from ERP_DONDATHANGNPP_SANPHAM where schemeCHIETKHAU in ( select scheme from CTKHUYENMAI where ( chiavaodongia = 1 or loaitrucsbh = 1 ) ) )	" +
							" and " + ddhId + " not in ( select DONDATHANGID from ERP_DONDATHANGNPP_CTKM_TRAKM where CTKMID in ( select PK_SEQ from CTKHUYENMAI where ( chiavaodongia = 1 or loaitrucsbh = 1 ) ) )	";
				}
				else
				{
					query = "select a.pk_seq, ( select LCH_FK from KHACHHANG where PK_SEQ = '" + khId + "' ) as lch_fk, a.apdungchodaily, a.apdungchohopdong, " +
							"	( select SUM( round ( ( round ( soluong * dongiaGOC, 0 ) * ( 1 + thueVAT / 100.0 ) ), 0 ) ) from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + ddhId + "' and sanpham_fk in ( select sanpham_fk from CHINHSACHBANHANG_SANPHAM where chinhsachbanhang_fk = a.pk_seq ) and sanpham_fk not in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk in ( 100018, 100021 ) ) ) as tongtienDONHANG 	" +
							"from CHINHSACHBANHANG a inner join CHINHSACHBANHANG_NPP b on b.chinhsachbanhang_fk = a.pk_seq   " +
							"where a.trangthai = '1' and tungay <= '" + ngaydonhang + "' and '" + ngaydonhang + "' <= denngay and b.npp_fk = '" + nppId + "' " + 
							" and a.pk_seq in ( select chinhsachbanhang_fk from CHINHSACHBANHANG_LOAIKHACHHANG where loaikhachhang_fk in ( select LCH_FK from KHACHHANG where pk_seq = '" + khId + "' ) ) " + 
							" and a.pk_seq in ( select chinhsachbanhang_fk from CHINHSACHBANHANG_KENH where kbh_fk in ( select kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + khId + "' ) ) " + 
							//" and ( a.tichluy_fk is NULL or " + khId + " not in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG where DKKMTICHLUY_FK in ( select pk_seq from DANGKYKM_TICHLUY where NPP_FK = '" + nppId + "' and TIEUCHITL_FK = a.tichluy_fk ) ) ) " + 
							//" and (  " + khId + " not in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG where DKKMTICHLUY_FK in ( select pk_seq from DANGKYKM_TICHLUY where NPP_FK = '" + nppId + "'  ) ) ) " + 
							" and " + khId + " not in ( select khachhang_fk from CHINHSACHBANHANG_KHACHHANG_KHONGAPDUNG where CSBH_FK = 100000 ) " + 
							" and " + ddhId + " not in ( select dondathang_fk from ERP_DONDATHANGNPP_SANPHAM where schemeCHIETKHAU in ( select scheme from CTKHUYENMAI where ( chiavaodongia = 1 or loaitrucsbh = 1 ) ) )	" +
							" and " + ddhId + " not in ( select DONDATHANGID from ERP_DONDATHANGNPP_CTKM_TRAKM where CTKMID in ( select PK_SEQ from CTKHUYENMAI where ( chiavaodongia = 1 or loaitrucsbh = 1 ) ) )	";
				}
				
				System.out.println(":::: LAY CK CSBH DANG AP DUNG: " + query);
				rs = db.get(query);
				double chietkhau = 0;
				double ptchietkhau = 0;
				String chinhsachID = "";
				try 
				{
					if( rs != null )
					{
						if(rs.next())
						{
							chinhsachID = rs.getString("pk_seq");
							String lchId = rs.getString("lch_fk") == null ? "" : rs.getString("lch_fk");
							String apdungchodaily = rs.getString("apdungchodaily");
							String apdungchohopdong = rs.getString("apdungchohopdong");
							double tongtienDONHANG = rs.getDouble("tongtienDONHANG");
	
							if(lchId.equals("100008") && apdungchodaily.equals("0") )
							{
								rs.close();
								return;
							}
	
							//Tìm mức chiết khấu được hưởng
							query = " select top(1) CHIETKHAU from CHINHSACHBANHANG_TIEUCHI " + 
									" where chinhsachbanhang_fk = '" + chinhsachID + "' and tumuc <= '" + tongtienDONHANG + "' and '" + tongtienDONHANG + "' < denmuc";
							System.out.println("::::: LAY CHIET KHAU THEO CHINH SACH: " + query );
							ResultSet rsCHIETKHAU = db.get(query);
							if(rsCHIETKHAU.next())
							{
								ptchietkhau = rsCHIETKHAU.getDouble("CHIETKHAU");
								rsCHIETKHAU.close();
							}
	
							if(ptchietkhau > 0)
							{
								chietkhau = ptchietkhau * tongtienDONHANG / 100.0;
							}	
						}
						rs.close();
					}
				} 
				catch (Exception e) {

					e.printStackTrace();
				}
				
				if(chinhsachID.trim().length() > 0 && ptchietkhau > 0 )
				{
					//Nếu khách hàng nằm trong danh sách đăng ký tích lũy, thì những sản phẩm nằm trong tích lũy sẽ không được CSBH
					String condition = "";
					String condition1 = "";
					if( dangkyTL > 0 )
					{
						//condition += " and sanpham_fk not in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk in ( select TIEUCHITL_FK from DANGKYKM_TICHLUY where TRANGTHAI = '1' and NPP_FK = '" + nppId + "' ) ) ";
						condition += " and a.sanpham_fk not in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk in ( 100018, 100021 ) ) ";
						condition1 += " and DH.sanpham_fk not in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk in ( 100018, 100021 ) ) ";
					}
					
					//PHAN BO LAI CHIET KHAU NAY
					query =  "update DH set DH.dongia = DH.dongiaGOC - CK.chietkhauGIAM, " + 
							"			  DH.chietkhau_CSBH = CK.chietkhauGIAM, DH.chinhsach_fk = '" + chinhsachID + "' " + 
							"from ERP_DONDATHANGNPP_SANPHAM DH inner join " + 
							"( " + 
							"	select dondathang_fk, sanpham_fk, ( select top(1) htbh_fk from hethongbanhang_kenhbanhang where kbh_fk = b.KBH_FK ) as htbh_fk, " + 
							"			soluong, dongiaGOC, soluong * dongiaGOC as thanhtien, a.thueVAT, " + 
							"		    ( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) * " + ptchietkhau + " ) / 100.0) / ( 1 + thueVAT / 100.0 )  as chietkhauGIAM  " + 
							"	from ERP_DONDATHANGNPP_SANPHAM a inner join ERP_DONDATHANGNPP b on a.dondathang_fk = b.PK_SEQ " + 
							"	where dondathang_fk = '" + ddhId + "' " + condition +
							" 		and a.sanpham_fk in ( select sanpham_fk from CHINHSACHBANHANG_SANPHAM where chinhsachbanhang_fk = '" + chinhsachID + "' ) " + 
							") " + 
							"CK on DH.dondathang_fk = CK.dondathang_fk and DH.sanpham_fk = CK.sanpham_fk " + 
							"where DH.sanpham_fk in ( select sanpham_fk from CHINHSACHBANHANG_SANPHAM where chinhsachbanhang_fk = '" + chinhsachID + "' )  " + condition1 ;
					
					System.out.println("::::: CAP NHAT CHIET KHAU: " + query );
					db.update(query);
				}
				
				
				//CHI GIẢM TIỀN KHUYẾN MẠI VÀ CHIẾT KHẤU CHÍNH SÁCH BÁN HÀNG VÀO ĐƠN HÀNG
				query = "update DH set DH.dongia = DH.dongiaGOC - CK.chietkhauGIAM " + 
						"from ERP_DONDATHANGNPP_SANPHAM DH inner join " + 
						"( " + 
						"	select dondathang_fk, sanpham_fk, " + 
						"			soluong, dongiaGOC, soluong * dongiaGOC as thanhtien, a.thueVAT, " + 
						"		    a.chietkhau_KM + a.chietkhau_CSBH  as chietkhauGIAM  " + 
						"	from ERP_DONDATHANGNPP_SANPHAM a inner join ERP_DONDATHANGNPP b on a.dondathang_fk = b.PK_SEQ " + 
						"	where dondathang_fk = '" + ddhId + "' and isnull( chietkhau_KM, 0 ) > 0 " + 
						") " + 
						"CK on DH.dondathang_fk = CK.dondathang_fk and DH.sanpham_fk = CK.sanpham_fk " + 
						"where DH.dondathang_fk = '" + ddhId + "'  " ;
				
				System.out.println("::::: CAP NHAT CHIET KHAU KM - CSBH: " + query );
				db.update(query);
				
			}
			
		}
		
	}
	
	/*************** END ÁP KHUYẾN MẠI TỰ ĐỘNG ************************/
	
	
	/*************** IMPORT HOADON - PGH ************************************/
	
	public  String CapNhat_HoaDonIMPORT()
	{
		dbutils db = new dbutils();
		
		try 
		{
			String dhIds = "";
			//dhIds = " select pk_seq from ERP_DONDATHANGNPP where khachhang_fk in ( 101764, 110639 ) ";
			//dhIds = "128145";
			
			String query =   "select PK_SEQ, machungtu, TRANGTHAI, dh.machungtu, dh.kho_fk, dh.khachhangKG_FK,  " + 
							 "	    ( select COUNT(*) from ERP_HOADONNPP where TRANGTHAI not in ( 3, 5 ) and NPP_FK = '106313' and NGAYXUATHD >= '2015-07-01'  " + 
							 "			and PK_SEQ in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = dh.pk_seq ) ) as dacoHOADON,  " + 
							 "		( select COUNT(*) from ERP_YCXUATKHONPP where ddh_fk = dh.PK_SEQ and TRANGTHAI != 2 ) as dacoPGH  " + 
							 "from ERP_DONDATHANGNPP dh where machungtu is not null and NOTE = N'Import ngay 12-11-2015'  and dh.loaidonhang in ( 0, 1, 2 )  ";
			
			if(dhIds.trim().length() > 0)
				query += " and pk_seq in ( " + dhIds + " ) ";
			
			System.out.println(":::: DANH SACH DON HANG IMPORT: " + query );
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String ddhId = rs.getString("PK_SEQ");
					String machungtu = rs.getString("machungtu");
					int dacoHOADON = rs.getInt("dacoHOADON");
					//int dacoPGH = rs.getInt("dacoPGH");
					
					String kho_fk = rs.getString("kho_fk");
					String khachhangKG_FK = rs.getString("khachhangKG_FK") == null ? "" : rs.getString("khachhangKG_FK");
					
					if(dacoHOADON <= 0 ) //Chưa có hóa đơn, cạp nhật lại giá trong đơn hàng, xử lý hàng KM, tạo hóa đơn, phiếu giao hàng tự động
					{
						String msg = XuLy_Import_ChuaCoHoaDon( db, ddhId, machungtu, kho_fk, khachhangKG_FK );
						if(msg.trim().length() > 0)
						{
							query = "Insert ERP_DONDATHANGNPP_LOG ( ddh_fk, msg ) " + 
									" values ( '" + ddhId + "', N'" + msg.replaceAll("'", "''") + "' ) ";
							
							System.out.println("::::: LOI IMPORT: " + query );
							db.update(query);
						}
					}
					
					//Có hóa đơn rồi thì không xử lý, tự xử lý bằng tay
					
				}
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		db.shutDown();
		System.out.println(":::: CHAY XONG IMPORT DON HANG....");
		return "";
	}
	
	private static String XuLy_Import_ChuaCoHoaDon(dbutils db, String ddhId, String machungtu, String kho_fk, String khachhangKG_FK) 
	{
		String msg = "";
		String query = "";
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			//Cập nhật lại đơn giá theo file IMPORT
			/*query =  "update a set a.thueVAT = b.thueVAT,  " + 
					 "			 a.dongiaGOC = b.dongiaTRUOCCK, " + 
					 "			 a.dongia = b.dongiaSAUCK, " + 
					 "			 a.chietkhau = b.chietkhau " + 
					 "from ERP_DONDATHANGNPP_SANPHAM a inner join ERP_DONDATHANGNPP_TEMP b on a.STT = b.stt " + 
					 "where a.dondathang_fk = '" + ddhId + "' " ;
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				msg = ":: 1.Lỗi import: " + query;
				return msg;
			}*/
			
			//TIM NHUNG MA CHUNG TU CHUA CO NGAY HET HAN --> TAM THOI KHONG QUAN TAM SO LO
			/*if(!kho_fk.equals("100003"))
			{
				query = "select km.soloCHUAN,  " + 
						 "	   ISNULL(	( select top(1) NGAYHETHAN from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ), '') as ngayhethan	 " + 
						 "from ERP_DONDATHANGNPP_TEMP km   " + 
						 "where km.machungtu = ( select machungtu from ERP_DONDATHANGNPP where PK_SEQ = '" + ddhId + "' ) " + 
						 "		AND ( select top(1) NGAYHETHAN from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ) IS NULL " ;
			}
			else
			{
				query = "select km.soloCHUAN,  " + 
						 "	   ISNULL(	( select top(1) NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '106313' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ), '') as ngayhethan	 " + 
						 "from ERP_DONDATHANGNPP_TEMP km   " + 
						 "where km.machungtu = ( select machungtu from ERP_DONDATHANGNPP where PK_SEQ = '" + ddhId + "' ) " + 
						 "		AND ( select top(1) NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '106313' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ) IS NULL " ;
			}
			
			System.out.println(":::: CHECK SOLO : KHO: " + kho_fk + " - SQL: " + query );
			ResultSet rsCHECK = db.get(query);
			String soloKHONGDUNG = "";
			if(rsCHECK != null)
			{
				while(rsCHECK.next())
				{
					soloKHONGDUNG += rsCHECK.getString("soloCHUAN") +  ",";
				}
				rsCHECK.close();
			}
			
			if(soloKHONGDUNG.trim().length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				msg = ":: 2.Lỗi import: các lô sau không tìm thấy trong hệ thống: " + soloKHONGDUNG;
				return msg;
			}*/
				
			//Chèn lô vào đơn hàng chi tiết
			if(!kho_fk.equals("100003"))
			{
				/*query =  "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, STT, SANPHAM_FK, DVDL_FK, soluong, solo, ngayhethan, LOAI, scheme, sohoadon_import ) " + 
						 "select dh.PK_SEQ, km.stt, c.PK_SEQ as spId, c.DVDL_FK, km.soluong, soloCHUAN,   " + 
						 "		ISNULL ( ( select top(1) NGAYHETHAN from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = c.PK_SEQ  ), '' ) as ngayhethan, " + 
						 "		case when km.dongiaTRUOCCK > 0 then 0 else 1 end as loai, " + 
						 "		case when km.dongiaTRUOCCK > 0 then '' else 'MRMN1504OSS2' end as scheme, km.sohoadonCHUAN " + 
						 "from ERP_DONDATHANGNPP_TEMP km  inner join ERP_DONDATHANGNPP dh on km.machungtu = dh.machungtu " + 
						 "		inner join SANPHAM c on km.maSANPHAM = c.MA_FAST " + 
						 "where dh.PK_SEQ = '" + ddhId + "' ";*/
				
				query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, STT, SANPHAM_FK, DVDL_FK, soluong, solo, ngayhethan, LOAI, scheme, sohoadon_import )   "+
						 "select dh.PK_SEQ, km.stt, c.PK_SEQ as spId, c.DVDL_FK, km.soluong, soloCHUAN,     "+
						 "		ISNULL ( ( select top(1) NGAYHETHAN from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = c.PK_SEQ  ), '' ) as ngayhethan,   "+
						 "		0 loai, '' scheme, km.sohoadonCHUAN   "+
						 "from ERP_DONDATHANGNPP_TEMP km  inner join ERP_DONDATHANGNPP dh on km.machungtu = dh.machungtu   "+
						 "		inner join SANPHAM c on km.maSANPHAM = c.MA_FAST   "+
						 "where dh.PK_SEQ = '" + ddhId + "' and km.thanhtien_TRUOCVAT != 0  "+
						 "union ALL "+
						 "select PK_SEQ, stt, spId, DVDL_FK, SOLUONG, soloCHUAN, '2019-11-10' as ngayhethan, "+
						 //"		 ISNULL ( ( select top(1) NGAYHETHAN from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = soloCHUAN and SANPHAM_FK = spId  ), '' ) as ngayhethan, "+
						 "		 loai, scheme, sohoadonCHUAN "+
						 "from "+
						 "( "+
						 "	select dh.PK_SEQ, 1 as stt, c.PK_SEQ as spId, c.DVDL_FK, km.soluong, dh.kho_fk, "+
						 "			case when ISNULL(km.solo_import, '') != '' then km.solo_import else ISNULL ( ( select top(1) SOLO from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = dh.kho_fk and nhomkenh_fk = '100000' and SANPHAM_FK = c.PK_SEQ  ), '' )  end as soloCHUAN,     "+
						 "			1 as loai, d.scheme, km.sohoadon_import as sohoadonCHUAN   "+
						 "	from ERP_DONDATHANGNPP_CTKM_TRAKM km  inner join ERP_DONDATHANGNPP dh on km.DONDATHANGID = dh.PK_SEQ   "+
						 "			inner join SANPHAM c on km.SPMA = c.MA   "+
						 "			inner join CTKHUYENMAI d on km.ctkmId = d.pk_seq "+
						 "	where dh.PK_SEQ = '" + ddhId + "'   "+
						 ") "+
						 "KM ";
			}
			else
			{
				/*query =  "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, STT, SANPHAM_FK, DVDL_FK, soluong, solo, ngayhethan, LOAI, scheme, sohoadon_import ) " + 
						 "select dh.PK_SEQ, km.stt, c.PK_SEQ as spId, c.DVDL_FK, km.soluong, soloCHUAN,   " + 
						 "		ISNULL ( ( select top(1) NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '106313' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = c.PK_SEQ  ), '' ) as ngayhethan, " + 
						 "		case when km.dongiaTRUOCCK > 0 then 0 else 1 end as loai, " + 
						 "		case when km.dongiaTRUOCCK > 0 then '' else 'MRMN1504OSS2' end as scheme, km.sohoadonCHUAN " + 
						 "from ERP_DONDATHANGNPP_TEMP km  inner join ERP_DONDATHANGNPP dh on km.machungtu = dh.machungtu " + 
						 "		inner join SANPHAM c on km.maSANPHAM = c.MA_FAST " + 
						 "where dh.PK_SEQ = '" + ddhId + "' ";*/
				
				query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, STT, SANPHAM_FK, DVDL_FK, soluong, solo, ngayhethan, LOAI, scheme, sohoadon_import )   "+
						 "select dh.PK_SEQ, km.stt, c.PK_SEQ as spId, c.DVDL_FK, km.soluong, soloCHUAN,     "+
						 "		ISNULL ( ( select top(1) NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '106313' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = c.PK_SEQ  ), '' ) as ngayhethan,   "+
						 "		0 loai, '' scheme, km.sohoadonCHUAN   "+
						 "from ERP_DONDATHANGNPP_TEMP km  inner join ERP_DONDATHANGNPP dh on km.machungtu = dh.machungtu   "+
						 "		inner join SANPHAM c on km.maSANPHAM = c.MA_FAST   "+
						 "where dh.PK_SEQ = '" + ddhId + "' and km.thanhtien_TRUOCVAT != 0  "+
						 "union ALL "+
						 "select PK_SEQ, stt, spId, DVDL_FK, SOLUONG, soloCHUAN, '2019-11-10' as ngayhethan, "+
						 //"		 ISNULL ( ( select top(1) NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '106313' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = soloCHUAN and SANPHAM_FK = spId  ), '' ) as ngayhethan, "+
						 "		 loai, scheme, sohoadonCHUAN "+
						 "from "+
						 "( "+
						 "	select dh.PK_SEQ, 1 as stt, c.PK_SEQ as spId, c.DVDL_FK, km.soluong, dh.kho_fk, "+
						 "			case when ISNULL(km.solo_import, '') != '' then km.solo_import else ISNULL ( ( select top(1) SOLO from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '106313' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = dh.kho_fk and nhomkenh_fk = '100000' and SANPHAM_FK = c.PK_SEQ  ), '' )  end as soloCHUAN,     "+
						 "			1 as loai, d.scheme, km.sohoadon_import as sohoadonCHUAN   "+
						 "	from ERP_DONDATHANGNPP_CTKM_TRAKM km  inner join ERP_DONDATHANGNPP dh on km.DONDATHANGID = dh.PK_SEQ   "+
						 "			inner join SANPHAM c on km.SPMA = c.MA   "+
						 "			inner join CTKHUYENMAI d on km.ctkmId = d.pk_seq "+
						 "	where dh.PK_SEQ = '" + ddhId + "'   "+
						 ") "+
						 "KM ";
			}
			
			System.out.println(":::: CHEN DON HANG CHI TIET: " + query );
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				msg = ":: 3.Lỗi import: " + query;
				return msg;
			}
			
			query = " Update ERP_DondathangNPP set TUTAO_HOADON_PGH = '1', trangthai = '4', " + 
					" CS_DUYET = 1, thoidiem_cs_duyet = getdate(), userId_cs_duyet = '100002', " + 
					" SS_DUYET = 1, thoidiem_ss_duyet = getdate(), userId_ss_duyet = '100002' " +
					" where pk_seq = '" + ddhId + "'   ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				msg = ":: 4.Lỗi import: " + query;
				return msg;
			}
			
			//CHECK XEM CÓ BAO NHIÊU SỐ HÓA ĐƠN
			query = " select distinct sohoadon_import " + 
					" from ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk = '" + ddhId + "' and sohoadon_import is not null ";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String sohoadon = rs.getString("sohoadon_import");
					
					msg = TaoHoaDonTaiChinhNPP( db, ddhId, sohoadon, "100002", "106313", "100001" );
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return msg;
					}
					
					msg = createPHIEUGIAOHANG( db, ddhId, sohoadon, "100002" );
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return msg;
					}
				}
				rs.close();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try {
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
			} 
			catch (Exception e1) { }
			
			e.printStackTrace();
			return e.getMessage();
		}
		
		return "";
	}

	private static String TaoHoaDonTaiChinhNPP(dbutils db, String id, String sohoadon, String userId, String nppId, String congtyId) 
	{
		String msg1 = "";
		try
		{
		 	String query = "";
		 	
			String kyhieuhoadon = "PG/15P";			
			String mau = "1";
			
			double tienck = 0;
			double tienbvat = 0;
			double tienavat = 0;
			String nguoimua = "";
				
			query = " select (case when dh.khachhang_fk is not null then " +
					"                               (select isnull(chucuahieu,'') from KHACHHANG where pk_seq = dh.khachhang_fk ) " +
					"             else '' end ) as nguoimua  ," +
					"        dh_sp.chietkhau, dh_sp.bvat , (dh_sp.bvat + dh.Vat) as AVAT "+
					" from ERP_DONDATHANGNPP dh inner join  "+
					"	( select a.dondathang_fk, SUM(a.chietkhau)as chietkhau , sum(a.soluong * a.dongia - a.chietkhau) as bvat "+
					"	  from ERP_DONDATHANGNPP_SANPHAM a where a.sohoadon_import = '" + sohoadon + "'  "+
					"	  group by  a.dondathang_fk  ) dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
					" where dh.PK_SEQ = "+ id +"   ";
			System.out.println(":::: INIT HOA DON: " + query );
			ResultSet rsLayTien = db.get(query);
			{
				while(rsLayTien.next())
				{
					tienck = rsLayTien.getDouble("chietkhau");
					tienbvat = rsLayTien.getDouble("bvat");
					tienavat = rsLayTien.getDouble("avat");
					nguoimua =  rsLayTien.getString("nguoimua");
					
				}
				rsLayTien.close();
			}
		
			 query =   " insert ERP_HOADONNPP(LOAIHOADON, isKM, DDKD_FK, KBH_fK, KHO_FK,nguoimua ,ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt , \n" +
				       "        chietkhau, tongtienbvat, tongtienavat, vat, ghichu, loaixuathd, npp_fk, khachhang_fk, npp_dat_fk, nhanvien_fk, mauhoadon, TENKHACHHANG, DIACHI, MASOTHUE, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU2, NGAYGHINHAN, KHACHHANGKG_FK ) \n" +
				       " SELECT 0 as LOAIHOADON, DH.isKM, DH.ddkd_Fk, DH.kbh_Fk, DH.kho_fk, N'" + nguoimua + "', DH.ngaydonhang, '2' as trangthai, '2015-07-20', '" + userId + "', '2015-07-20', '" + userId + "', '" + kyhieuhoadon + "', \n" +
					   //"       ( select distinct sohoadonCHUAN from ERP_DONDATHANGNPP_TEMP where machungtu = ( select machungtu from ERP_DONDATHANGNPP where PK_SEQ = DH.pk_seq ) ) as sohoadon, N'TM/CK' , '"+ tienck  +"', '"+ tienbvat +"', '"+ tienavat  +"', \n" +
					   "       '" + sohoadon + "' as sohoadon, N'TM/CK' , '"+ tienck  +"', '"+ tienbvat +"', '"+ tienavat  +"', \n" +
					   "       ( select top(1) thueVAT from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = DH.pk_seq ), N'Phiếu xuất hóa đơn tự động từ đơn hàng IP " + id + " ', DH.loaidonhang, '" + nppId + "', DH.khachhang_fk, DH.npp_dat_fk, DH.nhanvien_fk, " + mau + " \n" +
					   " 		, KH.TEN as tenkh \n" +
					   " 		, ISNULL(KH.DIACHI,'') as diachikh \n" +
					   " 		, ISNULL(KH.MASOTHUE,'')  as mst, " +
					   "			case when ((select COUNT(*) from KHACHHANG WHERE PK_SEQ = KH.PK_SEQ and TENXUATHD like '%,%' ) = 0) AND LEN(isnull(TENXUATHD,'')) = 0 then KH.TEN \n"+  
					   "			when ((select COUNT(*) from KHACHHANG WHERE PK_SEQ = KH.PK_SEQ and TENXUATHD like '%,%' ) = 0) AND LEN(isnull(TENXUATHD,'')) > 0 then \n"+ 
					   "			TENXUATHD else SUBSTRING(TENXUATHD,1, CHARINDEX(',',TENXUATHD) - 1 ) end , '" + congtyId + "', KH.PK_SEQ, isnull(KH.GHICHU, '') GHICHU2, DH.ngaydonhang, DH.KHACHHANGKG_FK \n"+
					   " FROM Erp_DonDatHangNPP DH left join KHACHHANG KH ON DH.KHACHHANG_FK = KH.PK_SEQ \n" +
					   " WHERE DH.PK_SEQ = '"+ id +"' ";
								 
			System.out.println("1.Insert ERP_HOADONNPP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP " + query;
				return msg1;
			}		
			
			String hdId = "";
			query = "select scope_identity() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();
			
			query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk)  values( "+ hdId +",  " + id + "  )";
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_DDH " + query;
				return msg1;
			}
			
			query = "insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SoLuong_Chuan, DonGia_Chuan, SoLuong_DatHang, SCHEME)    " + 
					 "	select '" + hdId + "' as hoadon_fk, a.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,      " + 
					 "		b.soluong,  b.solo, b.NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat,     " + 
					 "		( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdId + "' and sanpham_fk = b.sanpham_fk and ltrim(rtrim(scheme)) = '' ) as dongia,     " + 
					 "		case when d.pk_seq = dhsp.dvdl_fk then b.soluong         " + 
					 "		else b.soluong /      " + 
					 "		( select SOLUONG2 / SOLUONG1     " + 
					 "				from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as SoLuong_Chuan,     " + 
					 "		case when d.pk_seq = dhsp.dvdl_fk then dhsp.DONGIA        " + 
					 "		else dhsp.DONGIA /      " + 
					 "		( select SOLUONG2 / SOLUONG1    " + 
					 "		from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as DonGia_Chuan ,     " + 
					 "		dhsp.soluong as SoLuong_DatHang, b.scheme     " + 
					 "	from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.pk_seq = b.dondathang_fk	  						     " + 
					 "		 left join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk = a.PK_SEQ and dhsp.sanpham_fk = b.sanpham_fk	and dhsp.sohoadon_import = b.sohoadon_import     " + 
					 "		 inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						     " + 
					 "		 left join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	     " + 
					 "	where a.PK_SEQ = '" + id + "' and b.sohoadon_import = '" + sohoadon + "' and b.soluong > 0 and ltrim(rtrim(b.scheme)) = '' and a.TRANGTHAI != '3'  " + 
					 "union ALL " + 
					 "	select '" + hdId + "' as hoadon_fk, a.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, c.TEN, d.DONVI, d.pk_seq as dvCHUAN, d.PK_SEQ  as dvDATHANG,      " + 
					 "		soluong, b.solo, b.NGAYHETHAN, 0 chietkhau, 0 thuevat,  0 as dongia,     " + 
					 "		soluong as SoLuong_Chuan,     " + 
					 "		0 as DonGia_Chuan ,     " + 
					 "		soluong SoLuong_DatHang, b.scheme     " + 
					 "	from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.pk_seq = b.dondathang_fk	  						     " + 
					 "		 inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ  						     " + 
					 "		 inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	     " + 
					 "	where a.PK_SEQ = '" + id + "' and b.sohoadon_import = '" + sohoadon + "' and b.soluong > 0 and ltrim(rtrim(b.scheme)) != '' and a.TRANGTHAI != '3' ";
			
			System.out.println("1.0.Insert ERP_HOADONNPP_SP_CHITIET: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_SP_CHITIET " + query;
				return msg1;
			}
			
			query =  "insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, soluong_chuan, dongia, thanhtien, chietkhau, scheme , vat)  " + 
					 "select DATA.hoadonId, DATA.PK_SEQ as spId, DATA.sanphamTEN, DATA.donvi, SUM(DATA.soluong) as soluong, SUM(DATA.soluong_chuan) as soluongCHUAN,  " + 
					 "	 DATA.dongia, SUM(DATA.soluong) * DATA.dongia as thanhtien, SUM( chietkhau ) as chietkhau, scheme, DATA.vat   " + 
					 "from " + 
					 "( " + 
					 "	select  '" + hdId + "'  as hoadonId, b.PK_SEQ, a.sanphamTEN, DV.donvi, a.soluong, " + 
					 "		case when b.DVDL_FK = a.dvdl_fk then a.soluong           " + 
					 " 			else a.soluong /        " + 
					 " 			( select SOLUONG2 / SOLUONG1       " + 
					 " 					from QUYCACH where sanpham_fk = b.pk_seq and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK ) end  as SoLuong_Chuan,  " + 
					 "		a.dongia, isnull(a.chietkhau, 0) as chietkhau,  " + 
					 "		isnull(scheme, ' ') as scheme, isnull(a.thuevat,0) as vat      " + 
					 "	from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ   	    " + 
					 "		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK     " + 
					 "		inner join  ERP_DONDATHANGNPP c on a.dondathang_fk=c.pk_seq       " + 
					 "	where a.dondathang_fk in (  " + id + "  ) and a.sohoadon_import = '" + sohoadon + "' and a.soluong > 0   " + 
					 ") " + 
					 "DATA " + 
					 "group by DATA.hoadonId, DATA.PK_SEQ, DATA.sanphamTEN, DATA.donvi, DATA.dongia , scheme, DATA.vat " +
					 "union ALL " + 
					 "	select " + hdId + ", b.PK_SEQ, b.TEN, DV.donvi, ISNULL( SUM(a.soluong), 0) as soluong, ISNULL( SUM(a.soluong), 0) as souongCHUAN, 0 as dongia, SUM(a.TONGGIATRI) as thanhtien, 0 as chietkhau, d.SCHEME as scheme, 0 as vat    " + 
					 "	from ERP_DONDATHANGNPP_CTKM_TRAKM a left Join SanPham b on a.SPMA = b.MA   	  " + 
					 "		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK   " + 
					 "		INNER join  ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq  " + 
					 "		INNER join CTKHUYENMAI d on a.CTKMID = d.PK_SEQ    " + 
					 "	where a.DONDATHANGID in ( " + id + " ) and a.sohoadon_import = '" + sohoadon + "' and a.soluong > 0 " + 
					 "	group by b.PK_SEQ , b.TEN, DV.donvi, scheme ";
			
			System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
				return msg1;
			}
			
			//LUU VAO BANG CHI TIET
			
			//CAP NHAT LAI CAC COT TIEN CUA ETC, SAU NAY IN RA THI CHI IN TU DAY
			query = "update hd set hd.tongtienbvat = giatri.bVAT, hd.chietkhau = giatri.chietkhau,   " +
					"			hd.vat = giatri.vat, hd.tongtienavat = giatri.avat  " +
					"from ERP_HOADONNPP hd inner join  " +
					"(  " +
					"	select hoadonnpp_fk, sum(bVAT) as bVAT, sum(chietkhau) as chietkhau, sum(VAT) as VAT, " +
					"			 sum(bVAT) - sum(chietkhau) + sum(VAT) as aVAT  " +
					"	from  " +
					"	(  " +
					"		select a.hoadonnpp_fk,   " +
					"			cast( (b.soluong * b.dongia) as numeric(18, 0) ) as bVAT, isnull(b.chietkhau, 0) as chietkhau,    " +
					"			cast ( (	cast( (b.soluong * b.dongia - isnull(b.chietkhau, 0) ) as numeric(18, 0) ) * thueVAT / 100 ) as numeric(18, 0) ) as VAT " +
					"		from ERP_HOADONNPP_DDH a inner join ERP_DONDATHANGNPP_SANPHAM b on a.ddh_fk = b.dondathang_fk  " +
					"				inner join ERP_DONDATHANGNPP c on a.ddh_fk = c.pk_seq  " +
					"		where a.ddh_fk = '" + id + "'  " +
					"	)  " +
					"	etc group by hoadonnpp_fk  " +
					")  " +
					"giatri on hd.pk_seq = giatri.hoadonnpp_fk  ";
			if( !db.update(query) )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP " + query;
				return msg1;
			}
			
			//CHECK BANG TONG PHAI BANG BANG CHI TIET
			/*query = "select count(*) as sodong  " +
					"from " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
					"	where a.hoadon_fk = '" + hdId + "' " +
					"	group by b.pk_seq " +
					") " +
					"dh left join " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP_CHITIET a inner join SANPHAM b on a.MA = b.MA " +
					"	where a.hoadon_fk = '" + hdId + "' " +
					"	group by b.pk_seq " +
					") " +
					"xk on dh.sanpham_fk = xk.sanpham_fk " +
					"where dh.soluong != isnull(xk.soluong, 0) ";
			System.out.println("---CHECK HOA DON: " + query);
			int soDONG = 0;
			ResultSet rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				if(rsCHECK.next())
				{
					soDONG = rsCHECK.getInt("sodong");
				}
				rsCHECK.close();
			}
			
			if(soDONG > 0)
			{
				msg1 = "3.Số lượng trong đơn hàng không khớp với hóa đơn. Vui lòng liên hệ Admin để xử lý ";
				return msg1;
			}*/
			
			//TỰ ĐỘNG CẬP NHẬT LẠI BẢNG ERP_DONDATHANGNPP_SANPHAM_CHITIET
			query = " UPDATE ERP_DONDATHANGNPP_SANPHAM_CHITIET SET HOADON_FK = " + hdId + " WHERE DONDAThang_fk = " + id;
			System.out.println(query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the cập nhật ERP_DONDATHANGNPP_SANPHAM_CHITIET: " + query;
				return msg1;
			}
		} 
		catch (Exception e) 
		{
			msg1 = "Lỗi tạo mới hóa đơn: " + e.getMessage();
			e.printStackTrace();
			return msg1;
		}
		
		return msg1;
	}

	private static String createPHIEUGIAOHANG(dbutils db, String ddhId, String sohoadon, String userId) 
	{
		String msg = "";
		String query = "";
		
		try
		{
			query = " select a.DDH_FK, b.SOHOADON, b.npp_fk, b.Kho_FK, b.pk_seq " + 
					" from ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK = b.PK_SEQ " +
					" where a.DDH_FK = '" + ddhId + "' and b.sohoadon = '" + sohoadon + "' ";
			ResultSet rs = db.get(query);
			String hdId = "";
			String nppId = "";
			String khoId = "";
			if(rs.next())
			{
				hdId = rs.getString("pk_seq");
				nppId = rs.getString("npp_fk");
				khoId = rs.getString("Kho_FK");
			}
			rs.close();
			
			query = " insert ERP_YCXUATKHONPP(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, xuatcho, npp_dat_fk, khachhang_fk, nhomkenh_fk, ngaytao, nguoitao, ngaysua, nguoisua, hoadon_fk, sohoadon, ddh_fk, loaidonhang, KHACHHANGKG_FK, NHANVIEN_FK) " +
							" select dh.ngaydonhang, N'Phiếu giao hàng tự động từ duyệt hóa đơn " + hdId + "', '1' as trangthai, '" + nppId + "', " + khoId + ", " +
							" loaidonhang as xuatcho, npp_dat_fk, khachhang_fk, nhomkenh_fk, '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + hdId + "', '" + sohoadon + "', '" + ddhId + "', loaidonhang, KHACHHANGKG_FK, NHANVIEN_FK " +
					" from ERP_DONDATHANGNPP dh where pk_seq = '" + ddhId + "' ";
			
			System.out.println("1.Insert YCXUATKHO: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			String ycxkId = "";
			ResultSet rsYCXK = db.get("select IDENT_CURRENT('ERP_YCXUATKHONPP') as ycxkId");
			if(rsYCXK.next())
			{
				ycxkId = rsYCXK.getString("ycxkId");
			}
			rsYCXK.close();
			
			query = "Insert ERP_YCXUATKHONPP_DDH(ycxk_fk, ddh_fk) " +
					"select '" + ycxkId + "', pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + ddhId + " )  ";
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP_DDH " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME, DVDL_FK, soluongCHUAN ) " +
					"select '" + ycxkId + "', b.SANPHAM_FK, b.SOLUONG, 0, 0, b.SOLUONG, case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme,   " +
					"	( select pk_seq from DONVIDOLUONG where donvi = b.donvitinh ), b.soluong_chuan	" +
					"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.hoadon_fk   " +
					"where a.PK_SEQ = '" + hdId + "' " ;
			
			System.out.println("1.1.Insert HD - SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM_THUCGIAO( ycxk_fk, sanpham_fk, soluong, LOAI, SCHEME, DVDL_FK, soluongCHUAN ) " +
					"select '" + ycxkId + "', b.SANPHAM_FK, b.SOLUONG, case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme,   " +
					"	( select pk_seq from DONVIDOLUONG where donvi = b.donvitinh ), b.soluong_chuan	" +
					"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.hoadon_fk   " +
					"where a.PK_SEQ = '" + hdId + "' " ;
			
			System.out.println("1.2.Insert HD - SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, kho_fk, sanpham_fk, solo, ngayhethan, soluong, LOAI, SCHEME, tonkho, dongia ) " +
					"select '" + ycxkId + "', a.Kho_FK, c.PK_SEQ, b.SOLO, b.NGAYHETHAN, b.SOLUONG_CHUAN, " + 
					" 	case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme, '0' as tonkho, '0' as dongia " +
					"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk  " +
					"		inner join SANPHAM c on b.MA = c.MA  " +
					"where a.PK_SEQ = '" + hdId + "' ";
			System.out.println("1.3.Insert HD - SP CT: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET( ycxk_fk, kho_fk, sanpham_fk, solo, ngayhethan, soluong, LOAI, SCHEME, tonkho, dongia ) " +
					"select '" + ycxkId + "', a.Kho_FK, c.PK_SEQ, b.SOLO, b.NGAYHETHAN, b.SOLUONG_CHUAN, " + 
					" case when isnull(b.scheme, ' ') != ' ' then 1 else 0 end, isnull(b.scheme, ' ') as scheme, '0' as tonkho, '0' as dongia " +
					"from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk  " +
					"		inner join SANPHAM c on b.MA = c.MA  " +
					"where a.PK_SEQ = '" + hdId + "' ";
			System.out.println("1.4.Insert HD - SP CT: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "Lỗi khi tạo phiếu giao hàng: " + ex.getMessage();
		}
		
		return msg;
	}
	
	public static String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	/***************** END IMPORT HOADON - PGH ******************************/
	

	public String CapNhatLaiSOLO( dbutils db, String tungay, String denngay )
	{
		try 
		{
			/*String query = "select machungtu, sohoadon, dh.masanpham as maOLD, sp.ma as maSANPHAM, sp.pk_seq as idSANPHAM, soluong, dongiatruocCK, dbo.ftXuLyChuoi( solo ) as soloCHUAN,  " + 
						   "	( select top(1) pk_seq from ERP_HOADONNPP where dbo.ftXuLySo(sohoadon) = dbo.ftXuLySo(dh.sohoadon) and TRANGTHAI not in ( 3, 5 ) ) as hoadon_fk " + 
						  "from ERP_DONDATHANGNPP_TEMP_01_07 dh left join SANPHAM sp on dh.maSANPHAM = sp.ma_fast  ";*/
			
			/*String query =   "insert ERP_DONDATHANGNPP_TEMP_01_07_TT( machungtu, sohoadon, maOLD, maSANPHAM, idSANPHAM, soluong, dongiatruocCK, soloCHUAN, hoadon_fk, ngayIMPORT ) " + 
							 "select machungtu, sohoadon, dh.masanpham as maOLD, sp.ma as maSANPHAM, sp.pk_seq as idSANPHAM, soluong, dongiatruocCK, dbo.ftXuLyChuoi( solo ) as soloCHUAN,   " + 
							 "	( select top(1) pk_seq from ERP_HOADONNPP where dbo.ftXuLySo(sohoadon) = dbo.ftXuLySo(dh.sohoadon) and TRANGTHAI not in ( 3, 5 ) ) as hoadon_fk, dh.ngaylap " + 
							 "from ERP_DONDATHANGNPP_TEMP_01_07 dh left join SANPHAM sp on dh.maSANPHAM = sp.ma_fast   " + 
							 "where dh.ngaylap = '2015-07-24'  ";*/
			
			String query = "select machungtu, sohoadon, maOLD, maSANPHAM, idSANPHAM, soluong, dongiatruocCK, soloCHUAN, hoadon_fk " + 
					  	   "from ERP_DONDATHANGNPP_TEMP_01_07_TT dh left join SANPHAM sp on dh.maSANPHAM = sp.ma_fast " + 
						    " where dh.ngayIMPORT = '2015-07-24'  ";
			
			System.out.println("-- DANH SACH HOA DON: " + query );
			
			ResultSet rs = db.get(query);
			while( rs.next() )
			{
				String machungtu = rs.getString("machungtu");
				String sohoadon = rs.getString("sohoadon");
				String maOLD = rs.getString("maOLD");
				String maSANPHAM = rs.getString("maSANPHAM");
				String idSANPHAM = rs.getString("idSANPHAM");
				String soluong = rs.getString("soluong");
				String dongiatruocCK = rs.getString("dongiatruocCK");
				String soloCHUAN = rs.getString("soloCHUAN");
				String hoadon_fk = rs.getString("hoadon_fk") == null ? "" : rs.getString("hoadon_fk");
				
				String msg = CapNhat(db, hoadon_fk, maSANPHAM, idSANPHAM, soluong, dongiatruocCK, soloCHUAN);
				if( msg.trim().length() > 0 )
				{
					query = "Insert ERP_DONDATHANGNPP_TEMP_01_07_LOG ( sohoadon, machungtu, masanpham, solo, msg ) " + 
							"values ( '" + sohoadon + "', '" + machungtu + "', '" + maOLD + "', '" + soloCHUAN + "', N'" + msg.replaceAll("'", "''") + "' ) "	;
					
					System.out.println(":::: LOI: " + query );
					db.update(query);
				}
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "Lỗi import: " + e.getMessage();
		}
		
		
		return "";
	}
	
	public String CapNhat(dbutils db, String hoadon_fk, String maSANPHAM, String idSANPHAM, String soluong, String dongiatruocCK, String soloCHUAN) 
	{
		String msg = "";
		try 
		{
			if(hoadon_fk.trim().length() <= 0)
			{
				msg = "Không tìm thấy hóa đơn trong hệ thống ";
				return msg;
			}
			
			
			db.getConnection().setAutoCommit(false);
			
			String query =  " select ( select ddh_fk from ERP_HOADONNPP_DDH where hoadonnpp_fk = hd.pk_seq ) as ddh_fk, " + 
							" 	 ( select pk_seq from ERP_YCXUATKHONPP where hoadon_fk = hd.pk_seq and trangthai != 2 ) as ycxk_fk " + 
							" from ERP_HOADONNPP hd where pk_seq = '" + hoadon_fk + "'  ";
			
			System.out.println("-- INIT HOA DON: " + query );
			ResultSet rs = db.get(query);
			if( rs.next() )
			{
				String ddh_fk = rs.getString("ddh_fk") == null ? "" :  rs.getString("ddh_fk") ;
				String ycxk_fk = rs.getString("ycxk_fk") == null ? "" :  rs.getString("ycxk_fk");
				
				if( hoadon_fk.trim().length() > 0 )
				{
					query = " update ERP_HOADONNPP_SP_CHITIET set solo = '" + soloCHUAN + "' " + 
							" where hoadon_fk = '" + hoadon_fk + "' and soluong = '" + soluong + "' and MA = '" + maSANPHAM + "' ";
					
					if( dongiatruocCK.equals("0") )
						query += " and dbo.trim( scheme ) != '' ";
					else
						query += " and dbo.trim( scheme ) = '' ";
					
					System.out.println("-- CAP NHAT HOA DON: " + query );
					if(!db.update(query))
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return "1.Lỗi cập nhật: " + query;
					}
				}
				
				if(ddh_fk.trim().length() > 0)
				{
					query = " update ERP_DONDATHANGNPP_SANPHAM_CHITIET set solo = '" + soloCHUAN + "' " + 
							" where dondathang_fk = '" + ddh_fk + "' and soluong = '" + soluong + "' and SANPHAM_FK = " + idSANPHAM + " ";
					
					if( dongiatruocCK.equals("0") )
						query += " and dbo.trim( scheme ) != '' ";
					else
						query += " and dbo.trim( scheme ) = '' ";
					
					System.out.println("-- CAP NHAT DON HANG: " + query );
					if(!db.update(query))
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return "2.Lỗi cập nhật: " + query;
					}
				}
				else
				{
					/*db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Không tìm thấy đơn hàng....";*/
					
					msg += " Không tìm thấy đơn hàng.... ";
				}
				
				if(ycxk_fk.trim().length() > 0)
				{
					query = " update ERP_YCXUATKHONPP_SANPHAM_CHITIET set solo = '" + soloCHUAN + "' " + 
							" where ycxk_fk = '" + ycxk_fk + "' and soluong = '" + soluong + "' and SANPHAM_FK = " + idSANPHAM + " ";
					
					if( dongiatruocCK.equals("0") )
						query += " and dbo.trim( scheme ) != '' ";
					else
						query += " and dbo.trim( scheme ) = '' ";
					
					System.out.println("-- CAP NHAT PGH: " + query );
					if(!db.update(query))
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return "3.Lỗi cập nhật: " + query;
					}
					
					query = " update ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET set solo = '" + soloCHUAN + "' " + 
							" where ycxk_fk = '" + ycxk_fk + "' and soluong = '" + soluong + "' and SANPHAM_FK = " + idSANPHAM + " ";
					
					if( dongiatruocCK.equals("0") )
						query += " and dbo.trim( scheme ) != '' ";
					else
						query += " and dbo.trim( scheme ) = '' ";
					
					System.out.println("-- CAP NHAT PGH - THUC GIAO: " + query );
					if(!db.update(query))
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return "3.Lỗi cập nhật: " + query;
					}
					
				}
				else
				{
					msg += " Không tìm thấy phiếu giao hàng.... ";
				}
				
			}
			rs.close();
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try {
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
			} 
			catch (Exception e1) { }
			
			e.printStackTrace();
			return "4.Lỗi import: " + e.getMessage();
		}
		
		return msg;
	}
	
	
	public String TachHoaDon()
	{
		dbutils db = new dbutils();
		
		String query = "select distinct sohoadonGOC, sohoadonMOI from TACHHOADON ";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while( rs.next() )
				{
					String sohoadonGOC = rs.getString("sohoadonGOC");
					String sohoadonMOI = rs.getString("sohoadonMOI");
					
					String msg = Tach( db, sohoadonGOC, sohoadonMOI );
					if(msg.trim().length() > 0)
					{
						System.out.println(":::::::::::: HOA DON GOC: " + sohoadonGOC + " LOI IMPORT: " + msg );
					}
					else
						System.out.println(":::::::::::: HOA DON GOC: " + sohoadonGOC + " OKKKKKKKKKKKKKKKK" );
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
		
		return "";
	}

	private String Tach(dbutils db, String sohoadonGOC, String sohoadonMOI) 
	{
		String query = "";
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			//Cập nhật hóa đơn gốc
			query = "update b set b.SOLUONG = d.soluong  " + 
					 "from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.HOADON_FK " + 
					 "		inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " + 
					 "		inner join TACHHOADON d on a.SOHOADON = d.sohoadonGOC and c.MA_FAST = d.masanpham " + 
					 "where a.SOHOADON = '" + sohoadonGOC + "' and b.scheme = '' ";
			System.out.println("1.CAP NHAT: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "1.Lỗi: " + query;
			}
			
			query = "update b set b.SOLUONG = d.soluong  " + 
					 "from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.HOADON_FK " + 
					 "		inner join SANPHAM c on b.MA = c.MA " + 
					 "		inner join TACHHOADON d on a.SOHOADON = d.sohoadonGOC and c.MA_FAST = d.masanpham " + 
					 "where a.SOHOADON = '" + sohoadonGOC + "' and a.trangthai not in ( 3, 5 ) and b.scheme = '' ";
			System.out.println("2.CAP NHAT: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "2.Lỗi: " + query;
			}
			
			
			//TAO HOA DON MOI
			query =   " insert ERP_HOADONNPP( LOAIHOADON, isKM, DDKD_FK, KBH_fK, KHO_FK,nguoimua ,ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt , \n" +
				       "        chietkhau, tongtienbvat, tongtienavat, vat, ghichu, loaixuathd, npp_fk, khachhang_fk, npp_dat_fk, nhanvien_fk, mauhoadon, TENKHACHHANG, DIACHI, MASOTHUE, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU2, NGAYGHINHAN ) \n" +
				       " SELECT LOAIHOADON, isKM, DDKD_FK, KBH_fK, KHO_FK,nguoimua ,ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, '" + sohoadonMOI + "' sohoadon, hinhthuctt , \n" +
				       "        chietkhau, tongtienbvat, tongtienavat, vat, ghichu, loaixuathd, npp_fk, khachhang_fk, npp_dat_fk, nhanvien_fk, mauhoadon, TENKHACHHANG, DIACHI, MASOTHUE, TENXUATHD, CONGTY_FK, KHGHINO, GHICHU2, NGAYGHINHAN " +
					   " FROM Erp_HOADONNPP HD " +
					   " WHERE HD.SOHOADON like '%" + sohoadonGOC + "%' and HD.trangthai not in ( 3, 5 ) ";
			System.out.println("3.CAP NHAT: " + query);
			if(db.updateReturnInt(query) != 1 )
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "3.Lỗi: " + query;
			}
			
			String hdId = "";
			query = "select scope_identity() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();
			
			query = " Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk) " + 
					" select " + hdId + ",  ddh_fk from ERP_HOADONNPP_DDH where hoadonnpp_fk = ( select pk_seq from Erp_HOADONNPP HD WHERE HD.SOHOADON like '%" + sohoadonGOC + "%' and HD.trangthai not in ( 3, 5 ) ) ";
			System.out.println("4.CAP NHAT: " + query);
			if(db.updateReturnInt(query) != 1 )
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "4.Lỗi: " + query;
			}
			
			
			query = "insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SoLuong_Chuan, DonGia_Chuan, SoLuong_DatHang, SCHEME)  " + 
					 "select '" + hdId + "' hoadon_fk, donhang_fk, b.KBH_FK, b.Kho_FK, b.MA, b.TEN, DONVI, DVCHUAN, DVDATHANG, d.soluongMOI as SOLUONG, SOLO, NGAYHETHAN, b.CHIETKHAU, THUEVAT, DONGIA, d.soluongMOI as SoLuong_Chuan, DonGia_Chuan, d.soluongMOI as SoLuong_DatHang, SCHEME " + 
					 "from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.HOADON_FK " + 
					 "		inner join SANPHAM c on b.MA = c.MA  " + 
					 "		inner join TACHHOADON d on a.SOHOADON = d.sohoadonGOC and c.MA_FAST = d.masanpham " + 
					 "where a.SOHOADON like '%" + sohoadonGOC + "%' and a.trangthai not in ( 3, 5 ) and b.scheme = '' ";
			System.out.println("5.CAP NHAT: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "5.Lỗi: " + query;
			}
			
			query = "insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, soluong_chuan, dongia, thanhtien, chietkhau, scheme , vat)  " + 
					 "select '" + hdId + "' hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, d.soluongMOI as soluong, d.soluongMOI as soluong_chuan, dongia, thanhtien, b.chietkhau, scheme , b.vat " + 
					 "from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.HOADON_FK  " + 
					 "		inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ  " + 
					 "		inner join TACHHOADON d on a.SOHOADON = d.sohoadonGOC and c.MA_FAST = d.masanpham   " + 
					 "where a.SOHOADON like '%" + sohoadonGOC + "%' and a.trangthai not in ( 3, 5 ) and b.scheme = '' " ;
			System.out.println("6.CAP NHAT: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "6.Lỗi: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) {
			
			try {
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
			} 
			catch (Exception e1) { }
			
			e.printStackTrace();
			return e.getMessage();
		}
		
		return "";
	
	
	}
	
	//
	private String CapNhatDonGia_INS_2SoLe()
	{
		dbutils db = new dbutils();
		
		String query = "select hopdong_fk, sanpham_fk, isnull(dongia, 0) as dongia  "+
				 "from ERP_HOPDONGNPP_SANPHAM  "+
				 "where hopdong_fk in ( select PK_SEQ from ERP_HOPDONGNPP where KHACHHANG_FK in ( select khachhang_fk from KHACHHANG_KENHBANHANG where KBH_FK = '100056' ) ) " + 
				 " order by hopdong_fk, sanpham_fk ";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					String hopdong_fk = rs.getString("hopdong_fk");
					String sanpham_fk = rs.getString("sanpham_fk");
					String dongia = rs.getString("dongia");
					
					String phannguyen = "";
					String phanle = "";
					
					if(dongia.contains("."))
					{
						String[] _dongia = dongia.split("\\.");
						
						phannguyen = _dongia[0];
						phanle = _dongia[1];
					}
					else
					{
						phannguyen = dongia;
						phanle = "00";
					}
					
					if( phanle.length() > 2  )
						phanle = phanle.substring(0, 2);
					
					System.out.println("HOP DONG: " + hopdong_fk + " SAN PHAM: " + sanpham_fk + " GOC: " + dongia + " GIA DUNG: " + ( phannguyen + "." + phanle ) );
					
					query = "update ERP_HOPDONGNPP_SANPHAM set dongia = '" + ( ( phannguyen + "." + phanle ) ) + "' " + 
							" where hopdong_fk = '" + hopdong_fk + "' and sanpham_fk = '" + sanpham_fk + "' ";
					db.update(query);
				}
				rs.close();
			} 
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		System.out.println(":::: CHAY XONG............. ");
		db.shutDown();
		return "";
	}
	
	//CHÈN NHỮNG SẢN PHẨM CHƯA CÓ
	private String CapNhatHopDong_NguyenTac()
	{
		dbutils db = new dbutils();
		
		String query = "select PK_SEQ, npp_fk, ddkd_fk from ERP_HOPDONGNPP where LoaiDonHang = '2' ";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					String hopdongId = rs.getString("PK_SEQ");
					String nppId = rs.getString("npp_fk");
					String ddkdId = rs.getString("ddkd_fk");
					
					query = "insert ERP_HOPDONGNPP_SANPHAM( hopdong_fk, sanpham_fk, soluong, dongia, dvdl_fk, tungay, denngay, thueVAT, ddkd_fk )  "+
							 "select '" + hopdongId + "', SP.PK_SEQ, 0 as soluong,  "+
							 "	ISNULL( (   "+
							 "		select  	  "+
							 "				ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + nppId + "' ) ) ), 0)  as giamua    "+
							 "		from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq   "+
							 "			inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq   "+
							 "		where a.DVKD_FK = '100001' and a.PK_SEQ = SP.PK_SEQ  "+
							 "	), 0 ) as dongia, SP.dvdl_fk, ' ' tungay, ' ' as denngay, SP.thuexuat as thueVAT, " + ddkdId + " "+
							 "from SanPham SP   "+
							 " 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = SP.DVDL_FK          "+
							 "where SP.LOAISANPHAM_FK in ( 10045 ) and SP.PK_SEQ not in ( select sanpham_fk from ERP_HOPDONGNPP_SANPHAM where hopdong_fk = '" + hopdongId + "' ) ";
					
					System.out.println("::: CHEN SAN PHAM: " + query );
					db.update(query);
				}
				rs.close();
			} 
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		System.out.println(":::: CHAY XONG............. ");
		db.shutDown();
		return "";
	}
	
	
	/******************* TOLL IMPORT HÓA ĐƠN *************************************/
	
	public String XuLy_HoaDonIMPORT(dbutils db, String nppId, String ngayIMPORT, String dhIds)
	{
		try 
		{
			String query =   "select PK_SEQ, machungtu, TRANGTHAI, dh.machungtu, dh.kho_fk, dh.khachhangKG_FK,  " + 
							 "	    ( select COUNT(*) from ERP_HOADONNPP where TRANGTHAI not in ( 3, 5 ) and NPP_FK = '106313' and NGAYXUATHD >= '2015-07-01'  " + 
							 "			and PK_SEQ in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = dh.pk_seq ) ) as dacoHOADON,  " + 
							 "		( select COUNT(*) from ERP_YCXUATKHONPP where ddh_fk = dh.PK_SEQ and TRANGTHAI != 2 ) as dacoPGH  " + 
							 "from ERP_DONDATHANGNPP dh where machungtu is not null and NOTE = N'Import ngay " + ngayIMPORT + "'  and dh.loaidonhang in ( 0, 1, 2 )  ";
			
			if(dhIds.trim().length() > 0)
				query += " and pk_seq in ( " + dhIds + " ) ";
			
			System.out.println(":::: DANH SACH DON HANG IMPORT: " + query );
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String ddhId = rs.getString("PK_SEQ");
					String machungtu = rs.getString("machungtu");
					int dacoHOADON = rs.getInt("dacoHOADON");
					//int dacoPGH = rs.getInt("dacoPGH");
					
					String kho_fk = rs.getString("kho_fk");
					String khachhangKG_FK = rs.getString("khachhangKG_FK") == null ? "" : rs.getString("khachhangKG_FK");
					
					if(dacoHOADON <= 0 ) //Chưa có hóa đơn, cạp nhật lại giá trong đơn hàng, xử lý hàng KM, tạo hóa đơn, phiếu giao hàng tự động
					{
						String msg = XuLy_DonHangImport( db, nppId, ddhId, machungtu, kho_fk, khachhangKG_FK );
						if(msg.trim().length() > 0)
						{
							query = "Insert ERP_DONDATHANGNPP_LOG ( ddh_fk, msg, ngayIMPORT, machungtu ) " + 
									" values ( '" + ddhId + "', N'" + msg.replaceAll("'", "''") + "', '" + ngayIMPORT + "', '" + machungtu + "' ) ";
							
							System.out.println("::::: LOI IMPORT: " + query );
							db.update(query);
						}
					}
					
					//Có hóa đơn rồi thì không xử lý, tự xử lý bằng tay
				}
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		System.out.println(":::: CHAY XONG IMPORT DON HANG....");
		return "";
	}
	
	private static String XuLy_DonHangImport(dbutils db, String nppId, String ddhId, String machungtu, String kho_fk, String khachhangKG_FK) 
	{
		String msg = "";
		String query = "";
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			//Cập nhật lại đơn giá theo file IMPORT
			/*query =  "update a set a.thueVAT = b.thueVAT,  " + 
					 "			 a.dongiaGOC = b.dongiaTRUOCCK, " + 
					 "			 a.dongia = b.dongiaSAUCK, " + 
					 "			 a.chietkhau = b.chietkhau " + 
					 "from ERP_DONDATHANGNPP_SANPHAM a inner join ERP_DONDATHANGNPP_TEMP b on a.STT = b.stt " + 
					 "where a.dondathang_fk = '" + ddhId + "' " ;
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				msg = ":: 1.Lỗi import: " + query;
				return msg;
			}*/
			
			//TIM NHUNG MA CHUNG TU CHUA CO NGAY HET HAN --> TAM THOI KHONG QUAN TAM SO LO
			if(!kho_fk.equals("100003"))
			{
				query = "select km.soluong, km.soloCHUAN,  " + 
						 "	   ISNULL(	( select top(1) NGAYHETHAN from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ), '') as ngayhethan,	 " +
						 "	   ISNULL(	( select top(1) AVAILABLE from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ), 0) as tonkho	 " + 
						 "from ERP_DONDATHANGNPP_TEMP km   " + 
						 "where km.machungtu = ( select machungtu from ERP_DONDATHANGNPP where PK_SEQ = '" + ddhId + "' ) ";
						 //"		AND ( select top(1) NGAYHETHAN from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ) IS NULL " ;
			}
			else
			{
				query = "select km.soluong, km.soloCHUAN,  " + 
						 "	   ISNULL(	( select top(1) NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '106313' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ), '') as ngayhethan,	 " +
						 "	   ISNULL(	( select top(1) AVAILABLE from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '106313' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ), 0) as tonkho	 " +
						 "from ERP_DONDATHANGNPP_TEMP km   " + 
						 "where km.machungtu = ( select machungtu from ERP_DONDATHANGNPP where PK_SEQ = '" + ddhId + "' ) "; 
						 //"		AND ( select top(1) NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '106313' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = ( select pk_seq from SANPHAM where ma_FAST = km.masanpham )  ) IS NULL " ;
			}
			
			System.out.println(":::: CHECK SOLO : KHO: " + kho_fk + " - SQL: " + query );
			ResultSet rsCHECK = db.get(query);
			String soloKHONGDUNG = "";
			String thieuTONKHO = "";
			if(rsCHECK != null)
			{
				while(rsCHECK.next())
				{
					if( rsCHECK.getString("ngayhethan") != null )
					{
						soloKHONGDUNG += rsCHECK.getString("soloCHUAN") +  ",";
						
						double soluong = rsCHECK.getFloat("soluong");
						double tonkho = rsCHECK.getFloat("tonkho");
						
						if( soluong < tonkho )
						{
							thieuTONKHO += rsCHECK.getString("soloCHUAN") + " - xuất ( " + soluong + " ) thiếu tồn kho ( chỉ còn " + tonkho + " ) " +  ",";
						}
					}
				}
				rsCHECK.close();
			}
			
			if(soloKHONGDUNG.trim().length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				//msg = ":: 2.Lỗi import: các lô sau không tìm thấy trong hệ thống: " + soloKHONGDUNG;
				msg = "Các lô sau không tìm thấy trong hệ thống: " + soloKHONGDUNG;
				return msg;
			}
			
			if(thieuTONKHO.trim().length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				//msg = ":: 2.Lỗi import: các lô sau không tìm thấy trong hệ thống: " + soloKHONGDUNG;
				msg = "Các lô sau thiếu tồn kho trong hệ thống: " + thieuTONKHO;
				return msg;
			}
				
			//Chèn lô vào đơn hàng chi tiết
			if(!kho_fk.equals("100003"))
			{
				query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, STT, SANPHAM_FK, DVDL_FK, soluong, solo, ngayhethan, LOAI, scheme, sohoadon_import )   "+
						 "select dh.PK_SEQ, km.stt, c.PK_SEQ as spId, c.DVDL_FK, km.soluong, soloCHUAN,     "+
						 "		ISNULL ( ( select top(1) NGAYHETHAN from NHAPP_KHO_CHITIET where NPP_FK = '" + nppId + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = c.PK_SEQ  ), '' ) as ngayhethan,   "+
						 "		0 loai, '' scheme, km.sohoadonCHUAN   "+
						 "from ERP_DONDATHANGNPP_TEMP km  inner join ERP_DONDATHANGNPP dh on km.machungtu = dh.machungtu   "+
						 "		inner join SANPHAM c on km.maSANPHAM = c.MA_FAST   "+
						 "where dh.PK_SEQ = '" + ddhId + "' and km.thanhtien_TRUOCVAT != 0  "+
						 "union ALL "+
						 "select PK_SEQ, stt, spId, DVDL_FK, SOLUONG, soloCHUAN, "+
						 "		 ISNULL ( ( select top(1) NGAYHETHAN from NHAPP_KHO_CHITIET where NPP_FK = '" + nppId + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = soloCHUAN and SANPHAM_FK = spId  ), '' ) as ngayhethan, "+
						 "		 loai, scheme, sohoadonCHUAN "+
						 "from "+
						 "( "+
						 "	select dh.PK_SEQ, 1 as stt, c.PK_SEQ as spId, c.DVDL_FK, km.soluong, dh.kho_fk, "+
						 "			case when ISNULL(km.solo_import, '') != '' then km.solo_import else ISNULL ( ( select top(1) SOLO from NHAPP_KHO_CHITIET where NPP_FK = '106313' and KHO_FK = dh.kho_fk and nhomkenh_fk = '100000' and SANPHAM_FK = c.PK_SEQ  ), '' )  end as soloCHUAN,     "+
						 "			1 as loai, d.scheme, km.sohoadon_import as sohoadonCHUAN   "+
						 "	from ERP_DONDATHANGNPP_CTKM_TRAKM km  inner join ERP_DONDATHANGNPP dh on km.DONDATHANGID = dh.PK_SEQ   "+
						 "			inner join SANPHAM c on km.SPMA = c.MA   "+
						 "			inner join CTKHUYENMAI d on km.ctkmId = d.pk_seq "+
						 "	where dh.PK_SEQ = '" + ddhId + "'   "+
						 ") "+
						 "KM ";
			}
			else
			{
				query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, STT, SANPHAM_FK, DVDL_FK, soluong, solo, ngayhethan, LOAI, scheme, sohoadon_import )   "+
						 "select dh.PK_SEQ, km.stt, c.PK_SEQ as spId, c.DVDL_FK, km.soluong, soloCHUAN,     "+
						 "		ISNULL ( ( select top(1) NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '" + nppId + "' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = km.soloCHUAN and SANPHAM_FK = c.PK_SEQ  ), '' ) as ngayhethan,   "+
						 "		0 loai, '' scheme, km.sohoadonCHUAN   "+
						 "from ERP_DONDATHANGNPP_TEMP km  inner join ERP_DONDATHANGNPP dh on km.machungtu = dh.machungtu   "+
						 "		inner join SANPHAM c on km.maSANPHAM = c.MA_FAST   "+
						 "where dh.PK_SEQ = '" + ddhId + "' and km.thanhtien_TRUOCVAT != 0  "+
						 "union ALL "+
						 "select PK_SEQ, stt, spId, DVDL_FK, SOLUONG, soloCHUAN, "+
						 "		 ISNULL ( ( select top(1) NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '" + nppId + "' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = km.kho_fk and nhomkenh_fk = '100000' and SOLO = soloCHUAN and SANPHAM_FK = spId  ), '' ) as ngayhethan, "+
						 "		 loai, scheme, sohoadonCHUAN "+
						 "from "+
						 "( "+
						 "	select dh.PK_SEQ, 1 as stt, c.PK_SEQ as spId, c.DVDL_FK, km.soluong, dh.kho_fk, "+
						 "			case when ISNULL(km.solo_import, '') != '' then km.solo_import else ISNULL ( ( select top(1) SOLO from NHAPP_KHO_KYGUI_CHITIET where NPP_FK = '106313' and khachhang_fk = '" + khachhangKG_FK + "' and KHO_FK = dh.kho_fk and nhomkenh_fk = '100000' and SANPHAM_FK = c.PK_SEQ  ), '' )  end as soloCHUAN,     "+
						 "			1 as loai, d.scheme, km.sohoadon_import as sohoadonCHUAN   "+
						 "	from ERP_DONDATHANGNPP_CTKM_TRAKM km  inner join ERP_DONDATHANGNPP dh on km.DONDATHANGID = dh.PK_SEQ   "+
						 "			inner join SANPHAM c on km.SPMA = c.MA   "+
						 "			inner join CTKHUYENMAI d on km.ctkmId = d.pk_seq "+
						 "	where dh.PK_SEQ = '" + ddhId + "'   "+
						 ") "+
						 "KM ";
			}
			
			System.out.println(":::: CHEN DON HANG CHI TIET: " + query );
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				msg = ":: 3.Lỗi import: " + query;
				return msg;
			}
			
			query = " Update ERP_DondathangNPP set TUTAO_HOADON_PGH = '1', trangthai = '4', " + 
					" CS_DUYET = 1, thoidiem_cs_duyet = getdate(), userId_cs_duyet = '100002', " + 
					" SS_DUYET = 1, thoidiem_ss_duyet = getdate(), userId_ss_duyet = '100002' " +
					" where pk_seq = '" + ddhId + "'   ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				msg = ":: 4.Lỗi import: " + query;
				return msg;
			}
			
			//CHECK XEM CÓ BAO NHIÊU SỐ HÓA ĐƠN
			query = " select distinct sohoadon_import " + 
					" from ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk = '" + ddhId + "' and sohoadon_import is not null ";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String sohoadon = rs.getString("sohoadon_import");
					
					msg = TaoHoaDonTaiChinhNPP( db, ddhId, sohoadon, "100002", nppId, "100001" );
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return msg;
					}
				}
				rs.close();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try {
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
			} 
			catch (Exception e1) { }
			
			e.printStackTrace();
			return e.getMessage();
		}
		
		return "";
	}
	
	public String ResetData( dbutils db, String nppId, String ngayIMPORT, String dhIds )
	{
		String query = "";
		
		query = " delete ERP_DONDATHANGNPP_CTKM_TRAKM " + 
				" where DONDATHANGID in ( select pk_seq from ERP_DONDATHANGNPP where LoaiDonHang in ( 0, 1, 2 ) and NOTE = N'Import ngay " + ngayIMPORT + "' ) ";
		if( dhIds.trim().length() > 0 )
				query += "	and DONDATHANGID in ( " + dhIds + " )";
		if( !db.update(query) )
		{
			return "1.RESET Lỗi: " + query;
		}
		
		//TĂNG KHO NGƯỢC LẠI
		
		query = "delete ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk in ( select pk_seq from ERP_DONDATHANGNPP where LoaiDonHang in ( 0, 1, 2 ) and NOTE = N'Import ngay " + ngayIMPORT + "' ) ";
		if( dhIds.trim().length() > 0 )
				query += "	and dondathang_fk in ( " + dhIds + " )";
		if( !db.update(query) )
		{
			return "2.RESET Lỗi: " + query;
		}
		
		query = "delete ERP_HOADONNPP where LOAIXUATHD in ( 0, 1, 2 ) and PK_SEQ in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH " +
				"							where DDH_FK in ( select pk_seq from ERP_DONDATHANGNPP where LoaiDonHang in ( 1, 2 ) and NOTE = N'Import ngay " + ngayIMPORT + "' ) ";
		if( dhIds.trim().length() > 0 )
				query += "	and DDH_FK in ( " + dhIds + " )";
		query += " ) ";
		
		if( !db.update(query) )
		{
			return "3.RESET Lỗi: " + query;
		}
		
		query = "delete ERP_DONDATHANGNPP_SANPHAM where dondathang_fk in ( select pk_seq from ERP_DONDATHANGNPP where LoaiDonHang in ( 1, 2 ) and NOTE = N'Import ngay " + ngayIMPORT + "' ) ";
		if( dhIds.trim().length() > 0 )
			query += "	and dondathang_fk in ( " + dhIds + " )";
		if( !db.update(query) )
		{
			return "4.RESET Lỗi: " + query;
		}
		
		query = "delete  ERP_DONDATHANGNPP where LoaiDonHang in ( 1, 2 ) and NOTE = N'Import ngay " + ngayIMPORT + "' ";
		if( dhIds.trim().length() > 0 )
			query += "	and pk_seq in ( " + dhIds + " )";
		if( !db.update(query) )
		{
			return "5.RESET Lỗi: " + query;
		}
		
		query = "delete ERP_DONDATHANGNPP_TEMP where ngayIMPORT = '" + ngayIMPORT + "' ";
		if( !db.update(query) )
		{
			return "6.RESET Lỗi: " + query;
		}
		
		query = "delete ERP_DONDATHANGNPP_LOG where ngayIMPORT = '" + ngayIMPORT + "' ";
		if( !db.update(query) )
		{
			return "7.RESET Lỗi: " + query;
		}
		
		return "";
	}
	
	/******************* END TOLL IMPORT HÓA ĐƠN *********************************/
	
	
	
}
