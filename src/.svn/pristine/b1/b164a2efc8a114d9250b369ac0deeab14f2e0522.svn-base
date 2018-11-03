package app;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Taophieuchuyenkhotudong {
	public static void main(String[] args)
	{
		try 
		{
			String msg = "";
			dbutils db = new dbutils();
			Utility util=new Utility();
			db.getConnection().setAutoCommit(false);
			// cap nhat solokygui, ngaysanxuat
			/*String query = "select * from erp_nhanhang where istudong = 1";
			try
			{
				db.getConnection().setAutoCommit(false);
				
				ResultSet rs = db.get(query);
				while(rs.next())
				{
					String ngaytao = rs.getString("ngaytao");
					query = "update ERP_NHANHANG_SANPHAM set SOLOKYGUI = '"+ngaytao.replace("-","")+"', NGAYSANXUAT = '" + rs.getString("ngaytao")+"' where nhanhang_fk = " + rs.getString("pk_seq");
					System.out.println(query);
					if (!db.update(query)) {
						System.out.println("loi update ERP_NHANHANG_SANPHAM: "+query);
						db.getConnection().rollback();
					}
				}
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(false);
				System.out.println("Hoan tat!");
			}
			catch(Exception ex)
			{
				System.out.println("Vui Long Kiem Tra Lai Du Lieu .Xay Ra Loi Sau :" + ex.toString());
				ex.printStackTrace();
			}*/
			String hoadonid = "";
			String loaimh = "0";//0: nhập khẩu - 1: trong nước
			String sql="  select pk_seq from erp_hoadonncc where pk_seq IN (100215)";
			ResultSet rshd = db.get(sql);
		
			
			while(rshd.next())
			{
				hoadonid = rshd.getString("pk_seq");
				String query = " SELECT isnull( nh.MUAHANG_FK ,0 ) AS MUAHANG_FK, nh.pk_seq, nh.congty_fk, nh.nguoisua, nh.KHACHHANGKYGUI_FK, nh.NHAPHANPHOI_FK, "
						+ "b.nhacungcap_fk, (select pk_seq from KHO where LOAI = 7) KHONCC, ISNULL(nh.ISTUDONG,0) AS ISTUDONG, "
						+ "(select pk_seq from KHO where LOAI = 2) KHOKYGUINCC, nh.ngaytao, b.LOAI "
						+ "from erp_nhanhang nh "
						+ "INNER JOIN ERP_MUAHANG B ON nh.MUAHANG_FK = B.PK_SEQ "
						+ "where nh.hdncc_fk = '"+hoadonid+"' and B.LOAI != 2 and nh.TRANGTHAI in ('1', '2')";
				System.out.println("NHAN HANG " + query);
				ResultSet rsnh = db.get(query);
				
				int dem = 0;
				while(rsnh.next())
				{
					loaimh = rsnh.getString("loai");
					String ctyId = rsnh.getString("congty_fk");
					String userId = rsnh.getString("nguoisua");
					String NHAPHANPHOI_FK = rsnh.getString("NHAPHANPHOI_FK");
					String khachhang_fk= rsnh.getString("KHACHHANGKYGUI_FK")== null?"":rsnh.getString("KHACHHANGKYGUI_FK");
					String nccId = rsnh.getString("nhacungcap_fk") == null ? "" : rsnh.getString("nhacungcap_fk");
					String khoNCC = rsnh.getString("KHONCC");
					String khokyguiNCC = rsnh.getString("KHOKYGUINCC");
					int istudong = rsnh.getInt("ISTUDONG");
					String ngaychot = rsnh.getString("ngaytao");
					String nhId = rsnh.getString("pk_seq");
					
					String khoxuat = "NULL";
					String khonhan = "NULL";
					 
					String nccXuatId= "NULL";
					String nccNhanId= "NULL";
					String khXuatId= "NULL";
					String khNhanId= "NULL";
					
					query = " SELECT DISTINCT isnull( B.MUAHANG_FK ,0 ) AS MUAHANG_FK, b.KHONHAN, ( select loai from KHO where PK_SEQ = b.KHONHAN) loaikho "+
							" FROM ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM b on nh.PK_SEQ = b.NHANHANG_FK  " +
							"      inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
							" WHERE b.NHANHANG_FK = '" + nhId + "'  ";
					ResultSet rssp = db.get(query);
					while(rssp.next())
					{
						String khonhap = rssp.getString("KHONHAN");
						String loaikho = rssp.getString("loaikho");
						if(istudong <= 0) // NHẬN HÀNG BÌNH THƯỜNG
						{
							if(loaikho.equals("8")) // Kho ký gửi KH
							{
								khonhan = khonhap;
								khNhanId = khachhang_fk; 
							}else{
								khonhan = khonhap;
							}
							if(!loaimh.equals("2"))  // PO nhập khẩu && PO trong nước mới dùng nhập kho của nhà cung cấp
							{
								khoxuat = khoNCC;
								nccXuatId = nccId;
							}
							msg = TaoChuyenKhoTuDong(db, userId, ctyId, NHAPHANPHOI_FK, khoxuat, khonhan, nhId, nccXuatId, nccNhanId, khXuatId, khNhanId, ngaychot, "0");
							if(msg.trim().length() > 0)
							{
								System.out.println("loi tao ck tu dong: "+msg);
								db.getConnection().rollback();
							}
						}
						else // NHẬN HÀNG TỰ ĐỘNG
						{
							khoxuat = khoNCC;
							khonhan = khokyguiNCC;
							nccXuatId = nccId;
							nccNhanId = nccId;
							msg = TaoChuyenKhoTuDong(db, userId, ctyId, NHAPHANPHOI_FK, khoxuat, khonhan, nhId, nccXuatId, nccNhanId, khXuatId, khNhanId, ngaychot, "2");
							if(msg.trim().length() > 0)
							{
								System.out.println("loi tao ck tu dong: "+msg);
								db.getConnection().rollback();
							}
							
							if(loaikho.equals("5") || loaikho.equals("8")) // Kho ký gửi KH
							{
								khonhan = khonhap;
								khNhanId = khachhang_fk; 
							}else{
								khonhan = khonhap;
							}
							if(!loaimh.equals("2"))  // PO nhập khẩu && PO trong nước mới dùng nhập kho của nhà cung cấp
							{
								khoxuat = khokyguiNCC;
								nccXuatId = nccId;
							}
							msg = TaoChuyenKhoTuDong(db, userId, ctyId, NHAPHANPHOI_FK, khoxuat, khonhan, nhId, nccXuatId, nccNhanId, khXuatId, khNhanId, ngaychot, "1");
							if(msg.trim().length() > 0)
							{
								System.out.println("loi tao ck tu dong: "+msg);
								db.getConnection().rollback();
							}
						}
					}
					dem++;
				}
			}
			rshd.close();
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(false);
			System.out.println("Hoan tat!");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}			
	}
	private static String TaoChuyenKhoTuDong(dbutils db, String userId, String ctyId, String nppId, String khoxuat, String khonhan, String nhId, String nccXuatId, String nccNhanId, String khXuatId, String khNhanId, String ngaychot, String loaiNH) 
	{
		String msg = "";
		// Nội dung xuất: Chuyển kho bên ngoài 100024
		// Đã trừ cập nhật kho ở phía trên
		
		try 
		{
			String query = "select count(*) as kt from ERP_CHUYENKHO where khoxuat_fk = '"+khoxuat+"' and khonhan_fk = '"+khonhan+"' and nhanhang_fk = '"+nhId+"' and trangthai = 3";
			ResultSet rskt = db.get(query);
			String kt = "0";
			if(rskt != null)
			{
				rskt.next();
				kt = rskt.getString("kt");
			}
			System.out.println("Nhận hàng " + nhId + " đã có chuyển kho");
			if(kt.equals("0"))
			{
				query = " insert ERP_CHUYENKHO(CONGTY_FK, NGUOIDENGHI,NHOMKENH_FK,NPP_FK,NGUOINHAN ,noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, ghichu, trangthai,"+
						  " khoxuat_fk, khonhan_fk, trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua, NCC_CHUYEN_FK, NCC_NHAN_FK ,KYHIEU, SOCHUNGTU, LENHDIEUDONG, NGAYDIEUDONG,"+
						  " NGUOIDIEUDONG, VEVIEC, NGUOIVANCHUYEN, PHUONGTIEN, HOPDONG,KH_XUAT_FK,KH_NHAN_FK , NHANHANG_FK) " +
						  " values("+ ctyId +",N'','100000',"+nppId+", N'','100024', '" + ngaychot + "', '" + ngaychot + "', '" + ngaychot + "', N'', N'Chuyển kho tự động từ nhận hàng' + '"+ nhId +"' , '3',  " +
						  "        '" + khoxuat + "', " + khonhan + ", '1', '" + ngaychot + "', '" + userId + "', '" + ngaychot + "', '" + userId + "'," + nccXuatId + ", " + nccNhanId + ", "+
						  "        '','','','',N'',N'',N'',N'',N'',"+khXuatId+","+khNhanId+", "+ nhId +")";
				
				System.out.println("query " + query);
				if(!db.update(query))
				{
					msg = "Không thể tạo mới ERP_CHUYENKHO " + query;
					return msg;
				}
				
				String ycnlCurrent = "";
				query = " select IDENT_CURRENT('ERP_CHUYENKHO') as ckId";
				
				ResultSet rsPxk = db.get(query);						
				if(rsPxk.next())
				{
					ycnlCurrent = rsPxk.getString("ckId");
					rsPxk.close();
				}
				
				query = " SELECT DISTINCT c.ma as masp , SANPHAM_FK, DONGIA, b.ngaynhandukien, b.SOLUONGDAT, b.SOLUONGNHAN, b.SOLOKYGUI, b.NGAYSANXUAT, b.NGAYHETHAN, " +  
						"        (select distinct LOAI from ERP_MUAHANG where nh.MUAHANG_FK = PK_SEQ) loaimh, nh.MUAHANG_FK \n"+
						" FROM ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM b on nh.PK_SEQ = b.NHANHANG_FK  " +
						"      inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
						" WHERE b.NHANHANG_FK = '" + nhId + "'  ";
				
				ResultSet rs = db.get(query);
				String loaimh = "";
				String muahang_fk = "";
				if(rs!=null)
				{
					while(rs.next())
					{
						loaimh = rs.getString("loaimh") ;
						muahang_fk = rs.getString("MUAHANG_FK") ;
						
						String spId =  rs.getString("SANPHAM_FK") ;
						double soluong = rs.getDouble("SOLUONGNHAN");
						double dongia = rs.getDouble("DONGIA");		
						
						String soloNM_NK = "";
						String ngaynhapNM_NK = "";
						
								
						query = " INSERT ERP_CHUYENKHO_SANPHAM( CHUYENKHO_FK, SANPHAM_FK ,SOLUONGYEUCAU , SOLUONGXUAT , SOLUONGNHAN,DONGIA ) " +
								" values( '" + ycnlCurrent + "', '" + spId + "',"+soluong+" ,  " + soluong + " ,  " + soluong + ", "+dongia+"  ) ";
						if(!db.update(query))
						{
							msg = "Không thể tạo mới ERP_CHUYENKHO_SANPHAM : " + query;
							return msg;
						}
						
						
						// Lấy lo kho NM/NK
						if(loaimh.equals("0")){
							 query = "SELECT ct.SOLO, ct.NGAYNHAP "
								   + "FROM ERP_NHAPKHONHAPKHAU nk inner join ERP_NHAPKHONHAPKHAU_SP_CHITIET ct on nk.PK_SEQ = ct.NHAPKHO_FK "
								   + "WHERE nk.MUAHANG_FK = "+ muahang_fk +" and ct.SANPHAM_FK =  "+ spId +" "; 
						 }
						else if(loaimh.equals("1")){
							 query = "SELECT ct.SOLO, nk.NGAYNHAP "
								   + "FROM ERP_NHAPKHONHAMAY nk inner join ERP_NHAPKHONHAMAY_SP_CHITIET ct on nk.PK_SEQ = ct.NHAMAY_FK "
								   + "WHERE nk.MUAHANG_FK in (select distinct b.MUAHANG_FK "
								   + "                        from ERP_PHANBOMUAHANG_PO a inner join ERP_PHANBOMUAHANG b on a.PHANBO_FK = b.PK_SEQ "
								   + "                        where a.PODUOCPB =  "+ muahang_fk +")  and ct.SANPHAM_FK =  "+ spId +" ";  
						 }						 
						ResultSet rsCT = db.get(query);
						if(rsCT!= null)
						{
							while(rsCT.next())
							{																
								soloNM_NK = rsCT.getString("SOLO")	;
								ngaynhapNM_NK = rsCT.getString("NGAYNHAP")	;
								
							}rsCT.close();
						}
						
						// 0 Nhận hàng BT: loxuat : Nhập kho NM/ NK; lô nhận : Nhận hàng - chi tiết (solo)
						// 1 Nhận hàng tự động - chốt : loxuat : Nhận hàng - chi tiết (lokygui), lô nhận : Nhận hàng - chi tiết (solo)
						// 2 Tạo Nhận hàng tự động : loxuat : Nhập kho NM/ NK; lô nhận kho ký gửi
						
						if(loaiNH.equals("0"))
						{
							if(khoxuat != "NULL")
							{																	
								query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) " +
									  " VALUES ("+ycnlCurrent+","+ spId+",'"+ soloNM_NK +"' , NULL ,'"+ ngaynhapNM_NK +"',"+soluong+","+dongia+",'')";
									
								if(!db.update(query))
								{
									msg = "0.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
									return msg;
								}
							
							}
							
							query = " SELECT DISTINCT c.ma as masp , b.SANPHAM_FK, ISNULL(b.GIATHEOLO,0) DONGIA, b.SOLUONG, b.SOLO, b.NGAYSANXUAT, b.NGAYHETHAN " +  				
									" FROM ERP_NHANHANG_SP_CHITIET b inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
									" WHERE b.NHANHANG_FK = '" + nhId + "' and b.SANPHAM_FK = "+ spId +" ";
							ResultSet rsCKNhan = db.get(query);
							if(rsCKNhan!= null)
							{
								while(rsCKNhan.next())
								{								
									query=" INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK, CK_SP_XH_FK, SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) " +
											  " VALUES ("+ycnlCurrent+", NULL, "+ rsCKNhan.getString("SANPHAM_FK")+",'"+rsCKNhan.getString("SOLO")+"' , NULL " +
											  " ,'"+rsCKNhan.getString("NGAYSANXUAT")+"',"+rsCKNhan.getString("SOLUONG")+","+rsCKNhan.getString("DONGIA")+",'"+rsCKNhan.getString("NGAYHETHAN")+"')";
											
										if(!db.update(query))
										{
											msg = "1.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
											return msg;
										}
									
								}rsCKNhan.close();
							}
							
						}
						else if(loaiNH.equals("1"))
						{
							String lokygui = rs.getString("SOLOKYGUI");
							String ngaysx = rs.getString("NGAYSANXUAT");
							String ngayhh = rs.getString("NGAYHETHAN");
							
							
							query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) " +
									  " VALUES ("+ycnlCurrent+","+ spId +",'"+ lokygui +"' , NULL ,'"+ ngaysx +"',"+soluong+","+dongia+", '"+ ngayhh +"')";
									
							if(!db.update(query))
							{
								msg = "2.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
								return msg;
							}
							
							query = " SELECT DISTINCT c.ma as masp , b.SANPHAM_FK, ISNULL(b.GIATHEOLO,0) DONGIA, b.SOLUONG, b.SOLO, b.NGAYSANXUAT, b.NGAYHETHAN " +  				
									" FROM ERP_NHANHANG_SP_CHITIET b inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
									" WHERE b.NHANHANG_FK = '" + nhId + "' and b.SANPHAM_FK = "+ spId +" ";
							ResultSet rsCKNhan = db.get(query);
							if(rsCKNhan!= null)
							{
								while(rsCKNhan.next())
								{								
									query=" INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK, CK_SP_XH_FK, SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) " +
											  " VALUES ("+ycnlCurrent+", NULL, "+ rsCKNhan.getString("SANPHAM_FK")+",'"+rsCKNhan.getString("SOLO")+"' , NULL " +
											  " ,'"+rsCKNhan.getString("NGAYSANXUAT")+"',"+rsCKNhan.getString("SOLUONG")+","+rsCKNhan.getString("DONGIA")+",'"+rsCKNhan.getString("NGAYHETHAN")+"')";
											
									if(!db.update(query))
									{
										msg = "3.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
										return msg;
									}
									
								}rsCKNhan.close();
							}
						}
						else
						{
							if(khoxuat != "NULL")
							{
								query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) " +
										  " VALUES ("+ycnlCurrent+","+ spId+",'"+ soloNM_NK +"' , NULL ,'"+ ngaynhapNM_NK +"',"+soluong+","+dongia+",'')";
										
								if(!db.update(query))
								{
									msg = "4.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
									return msg;
								}
							}
							
							query=" INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK, CK_SP_XH_FK, SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) " +
									  " VALUES ("+ycnlCurrent+", NULL, "+ spId +",'"+ rs.getString("SOLOKYGUI") +"' , NULL " +
									  " ,'"+ rs.getString("NGAYSANXUAT")+"',"+ soluong +","+ dongia +",'"+ rs.getString("NGAYHETHAN") +"')";
									
							if(!db.update(query))
							{
								msg = "5.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
								return msg;
							}
						}
						
					}rs.close();
				}
			}
		}
		catch(Exception e)
		{
			return "Lỗi trong quá trình tạo phiếu Chuyển kho tự động";
		}
		
		return msg;
	}
}
