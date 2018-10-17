package geso.traphaco.erp.beans.hoadontrahang.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadontrahang.IErpHdTraHang;
import geso.traphaco.erp.beans.hoadontrahang.IErpHdTraHang_SanPham;
import geso.traphaco.center.db.sql.dbutils;

public class ErpHdTraHang implements IErpHdTraHang 
{
	String congtyId, isNPP, kbhId;
	String Id,UserId,Msg,NgayXuatHD,KhTen,KhId,KyHieu,SoHoaDon,HinhThucTT,GhiChu,POMT,poInfo,NguoiMuaHang, KhongHD, Nppdangnhap, HoadonCanTruId, KhachhangId;
	String[]DonTraHang;
	double TienAVAT,TienCK,TienSauCK,TienSauVAT,VAT, TienVAT, TienBVAT;
	ResultSet RsDthChuaXuatHD, khRs, HoadonCanTruRs;
	dbutils db;
	List<IErpHdTraHang_SanPham> listsanpham ; 
	
	public ErpHdTraHang()
	{
		this.Id="";
		this.UserId="";
		this.Msg="";
		this.NgayXuatHD=this.getDateTime();
		this.KhTen="";
		this.KhId="";
		this.KyHieu="";
		this.SoHoaDon="";
		this.HinhThucTT="";
		this.GhiChu="";
		this.TienAVAT = 0;
		this.TienCK = 0;
		this.TienSauCK = 0;
		this.TienSauVAT = 0;
		this.VAT = 0;
		this.TienVAT = 0;
		this.TienBVAT = 0;
		this.KhongHD = "1";
		this.Nppdangnhap = "";
		this.HoadonCanTruId = "";	
		this.KhachhangId = "";
		this.isNPP =  "";
		this.kbhId = "";
		this.DonTraHang=new String[0];
		this.listsanpham=new ArrayList<IErpHdTraHang_SanPham>(); 
		db=new dbutils();
	}
	
	public ErpHdTraHang(String Id)
	{
		this.Id=Id;
		this.UserId="";
		this.Msg="";
		this.NgayXuatHD=this.getDateTime();
		this.KhTen="";
		this.KhId="";
		this.KyHieu="";
		this.SoHoaDon="";
		this.HinhThucTT="";
		this.GhiChu="";
		this.TienAVAT = 0;
		this.TienCK = 0;
		this.TienSauCK = 0;
		this.TienSauVAT = 0;
		this.VAT = 0;
		this.TienVAT = 0;
		this.TienBVAT = 0;
		this.KhongHD = "1";
		this.Nppdangnhap = "";
		this.HoadonCanTruId = "";	
		this.KhachhangId = "";
		this.isNPP = "";
		this.kbhId = "";
		this.DonTraHang=new String[0];
		this.listsanpham=new ArrayList<IErpHdTraHang_SanPham>(); 
		db=new dbutils();
	}
	
	
	
	public String getId() 
	{
		return this.Id;
	}

	
	public void setId(String id) 
	{
		
	this.Id=id;	
		
	}

	
	public String getNgayXuatHD() {
		
		return this.NgayXuatHD;
	}

	
	public void setNgayXuatHD(String NgayXuatHD) 
	{
		this.NgayXuatHD=NgayXuatHD;	
	}

	
	public String getKhTen() 
	{
		
		return this.KhTen;
	}

	
	public void setKhTen(String KhTen) 
	{
		this.KhTen=KhTen;	
		
	}

	public void setVAT(double vat) {
		
		this.VAT=vat;
	}

	
	public double getTienAVAT() {
		
		return this.TienAVAT;
	}

	
	public void setTienAVAT(double TienAVAT) 
	{
		
		this.TienAVAT=TienAVAT;
	}

	
	public String getMsg() {
		
		return this.Msg;
	}

	
	public void setMsg(String msg) 
	{
	this.Msg= msg;	
		
	}

	
	public void SetKyHieu(String kyhieu) 
	{
		this.KyHieu=kyhieu;	
		
	}

	
	public String getKyHieu() 
	{
		return this.KyHieu;
	}

	
	public void SetSoHoaDon(String sohoadon) 
	{
	this.SoHoaDon=sohoadon;	
		
	}

	
	public String getSoHoaDon() 
	{
		return this.SoHoaDon;
	}

	
	public String getHinhThucTT() 
	{
		
		return this.HinhThucTT;
	}

	
	public void setHinhThucTT(String HinhThucTT) 
	{
		this.HinhThucTT=HinhThucTT;
		
	}

	
	public double getTienCK() {
		
		return this.TienCK;
	}

	
	public void setTienCK(double tienck) {
		
		this.TienCK=tienck;
	}

	
	public double getTienSauCK()
	{
		
		return this.TienSauCK;
	}

	
	public void getTienSauCK(double tiensauCK) 
	{
	this.TienSauCK=tiensauCK;	
		
	}

	
	public double getVAT() 
	{
		
		return this.VAT;
	}

	
	public void getVAT(double VAT) 
	{
		this.VAT=VAT;	
		
	}

	
	public double getTienSauVAT()
	{
		
		return this.TienSauVAT;
	}

	
	public void getTienSauVAT(double tienSauVAT)
	{
		this.TienSauVAT=tienSauVAT;
		
	}

	
	public String[] getDonTraHang()
	{
		
		return this.DonTraHang;
	}

	
	public void setDonTraHang(String[] DonTraHang) 
	{
		this.DonTraHang=DonTraHang;
		
	}
	
	public void CreateRs() // 1 ĐƠN TRẢ HÀNG - N NHẬN HÀNG - 1 HÓA ĐƠN HÀNG TRẢ VỀ NCC 
	{
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		System.out.println("usq123:"+this.UserId);
		this.Nppdangnhap = util.getIdNhapp(this.UserId);
		
		this.khRs = db.get("select PK_SEQ, TEN from KHACHHANG where TRANGTHAI = 1 and npp_fk in ( select pk_seq from NHAPHANPHOI where congty_fk = '" + this.congtyId + "' ) ");
		

		//Lay nhung don tra hang da nhap kho
		// va khong nam trong nhung don tra hang ma Hoa don da xoa hoac da huy.
		//LẤY NHỮNG ĐƠN TRẢ HÀNG ĐÃ NHẬP KHO - TRẠNG THÁI = 4
				
		String query = " SELECT DTH.PK_SEQ AS ID, CASE WHEN DTH.KHACHHANG_FK IS NOT NULL THEN KH.TEN WHEN DTH.NPP_DAT_FK IS NOT NULL THEN NPP.TEN " +
					   "		WHEN DTH.NPP_TRA_FK IS NOT NULL THEN NPP1.TEN END TEN, DTH.NGAYTRA  \n " +
					   " FROM 	DONTRAHANG DTH LEFT JOIN KHACHHANG KH ON DTH.KHACHHANG_FK =  KH.PK_SEQ \n "+
					   " LEFT JOIN NHAPHANPHOI NPP ON DTH.NPP_DAT_FK  = NPP.PK_SEQ \n"+
					   " LEFT JOIN NHAPHANPHOI NPP1 ON DTH.NPP_TRA_FK = NPP1.PK_SEQ \n"+
					   " WHERE  DTH.TRANGTHAI in ( 1, 6 ) AND DTH.NPP_FK =  "+this.Nppdangnhap+" ";

		if(this.Id==null||this.Id.trim().length()<=0 )
			query+=" AND DTH.PK_SEQ NOT IN (SELECT DTH.DTH_FK FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DTH DTH ON DTH.HOADON_FK=HD.PK_SEQ WHERE HD.TRANGTHAI NOT IN(2) ) \n ";		
		else 
			query+=" AND DTH.PK_SEQ NOT IN (SELECT DTH.DTH_FK FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DTH DTH ON DTH.HOADON_FK=HD.PK_SEQ WHERE HD.TRANGTHAI NOT IN(2) AND HD.PK_SEQ <>'"+this.Id+"') \n " ;
		

		System.out.println("__LAY NHUNG DON TRA HANG CHUA XUAT HOA DON__ "+query);

		this.RsDthChuaXuatHD = db.get(query);

		String chuoi = "";
		int i = 0;
		if(this.DonTraHang != null)
		{
			while( i < this.DonTraHang.length)
			{
				if( i == 0 )
				{
					chuoi = DonTraHang[i];
				}
				else
				{
					chuoi += "," + DonTraHang[i];
				}
				i++;
			}
		}

		System.out.println("DON HANG DA CHON:"+chuoi);
		/*	if(!this.KhId.equals(""))
		{*/
				
		ResultSet rs;
		if(chuoi.length()>0){
			query =	"SELECT ISNULL ( CAST(SOTIENBVAT AS FLOAT) ,0) AS TIENBVAT, ISNULL(CAST(VAT AS FLOAT),0) AS VAT, "+  
					"	 	ISNULL(DTH.CHIETKHAU,0) AS CHIETKHAU,"+  
					"	 	ISNULL(CAST (SOTIENAVAT AS FLOAT),0)  AS TIENAVAT , "+   
					"	 	ISNULL(GHICHU,'') AS GHICHU, ISNULL(NOIDUNGCHIETKHAU,'') AS NOIDUNGCHIETKHAU "+
					"FROM DONTRAHANG DTH "+  
					"WHERE  DTH.TRANGTHAI in ( 1, 6 )  AND DTH.PK_SEQ IN( "+chuoi+") ";

			rs = db.get(query);
			
			try
			{
				if(rs != null)
				{
					if(	rs.next())
					{
						this.TienCK = rs.getDouble("CHIETKHAU");
						this.TienVAT = (rs.getDouble("TIENAVAT")-rs.getDouble("TIENBVAT")) ;
						this.TienAVAT = rs.getDouble("TIENAVAT");
						this.TienBVAT = rs.getDouble("TIENBVAT");
						this.VAT = rs.getDouble("VAT");
						
					}
					rs.close();
				}
			}
			catch(Exception er)
			{
				er.printStackTrace();
				System.out.println("Exception__"+query);
			}

			//Lay nhung san pham thuoc don tra hang 
			//ma da nhap kho,tru cho nhung don tra hang ma nam trong nhung hoa don khac voi trang thai <> xoa(2)

			if(chuoi.trim().length() > 0)
			{
				query =  "SELECT DTH_SP.SANPHAM_FK,SP.MA,SP.TEN AS TENSP,DTH_SP.DONGIA, SP.DVDL_FK, SOLUONG, DVDL.PK_SEQ AS DVDL_FK ,DTH_SP.DONVI      \n" +
						 " , isnull(isnull(DTH.KHACHHANG_FK,DTH.NPP_TRA_FK), DTH.NPP_DAT_FK) KHACHHANG_FK,  \n"+
						 " CASE WHEN DTH.KHACHHANG_FK IS NOT NULL THEN 0 WHEN DTH.NPP_TRA_FK IS NOT NULL THEN 1 WHEN DTH.NPP_DAT_FK IS NOT NULL THEN 1 ELSE 2 END LOAI, DTH_SP.ptVAT  \n"+
						 "FROM 	DONTRAHANG DTH   \n" + 
						 "INNER JOIN DONTRAHANG_SP DTH_SP on DTH.PK_SEQ = DTH_SP.DONTRAHANG_FK \n" + 
						 "INNER JOIN SANPHAM SP ON SP.PK_SEQ = DTH_SP.SANPHAM_FK  \n" + 
						 "LEFT JOIN DONVIDOLUONG DVDL ON  DTH_SP.DONVI = DVDL.DONVI  \n" + 
						 "WHERE DTH_SP.SOLUONG > 0 AND 	DTH.PK_SEQ IN ( " +chuoi + " ) ";
	
				System.out.println("LAY SP reload_CReateRs: " + query);
	
				rs = this.db.get(query);
				this.listsanpham.clear();
				
				int loai = 0;
				String khachhang_fk = "";
				
				if(rs != null)
				{
					try
					{
						while(rs.next())
						{
							IErpHdTraHang_SanPham sanpham= new ErpHdTraHang_SanPham();
							sanpham.setId(rs.getString("SANPHAM_FK"));
							sanpham.setMa(rs.getString("MA"));
							sanpham.setTen(rs.getString("TENSP"));
							sanpham.setSoLuong(rs.getInt("SOLUONG"));
							sanpham.setDonGia(rs.getDouble("dongia"));
							sanpham.setThanhTien(rs.getDouble("SOLUONG")* rs.getDouble("dongia"));
							sanpham.setDonViTinh(rs.getString("DONVI"));
							sanpham.setDVDL(rs.getString("DVDL_FK"));
							sanpham.setptVat(rs.getDouble("ptVat"));
							loai = rs.getInt("LOAI");
							khachhang_fk = rs.getString("KHACHHANG_FK");
							this.listsanpham.add(sanpham);
						}rs.close();
					}
					catch(Exception er)
					{
						er.printStackTrace();
						System.out.println("Error ErpHoaDon.java 492 : "+er);
					}
				}
				
				
				if(loai == 0) // KHÁCH HÀNG
				{					
					query = 
					"	SELECT  a.PK_SEQ , a.SOHOADON + ' , ' + a.NGAYXUATHD  SOHOADON, a.NGAYXUATHD, \n"+		
					"	a.tongtienavat - isnull(bk.dathanhtoan,0) tongtienavat \n"+
					"	FROM ERP_HOADONNPP a \n"+
					"	INNER JOIN KENHBANHANG e on a.KBH_FK = e.PK_SEQ \n"+
					"	INNER JOIN KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ \n"+
					"	LEFT JOIN \n"+
					"	( \n"+
					"		SELECT  THU.HOADON_FK, SUM(ISNULL(THU.dathanhtoan,0)) dathanhtoan \n"+
					"		FROM ( \n"+
					"				SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS dathanhtoan \n"+
					"				FROM ERP_THUTIEN_HOADON TTHD \n"+
					"				INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
					"				WHERE TT.TRANGTHAI = 1 AND TTHD.LOAIHOADON = 0 AND isnull(TTHD.XOACHENHLECH,0) = 0 AND TT.BANGKE_FK IS NOT NULL \n"+
					"				GROUP BY HOADON_FK \n"+
		
					"  			UNION ALL \n"+
		
					"				SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS dathanhtoan \n"+
					"				FROM ERP_THUTIEN_HOADON TTHD \n"+
					"				INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
					"				WHERE TT.TRANGTHAI = 1 AND TTHD.LOAIHOADON = 0 AND isnull(TTHD.XOACHENHLECH,0) = 0 AND TT.BANGKE_FK IS NULL \n"+
					"				GROUP BY HOADON_FK \n"+
		
					"  			UNION ALL \n"+
		
					"				SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
					"				FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
					"				INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n"+
					"				WHERE TT.TRANGTHAI = 1 AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '0' \n"+
					"				GROUP BY HOADON_FK \n"+
		
					"  			UNION ALL \n"+
		
					"				SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS DATHANHTOAN \n"+
					"				FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n"+
					"				WHERE  BT.TRANGTHAI = 1 AND BT_KH.LOAIHD = 0 \n"+
					"				GROUP BY BT_KH.HOADON_FK \n"+
		
					"			) THU  \n"+
					"		 GROUP BY  THU.HOADON_FK \n"+
		
					"	) bk on a.PK_SEQ = bk.hoadon_fk \n"+
		
					"	WHERE a.trangthai in ( 2, 4 ) and (a.tongtienavat - ISNULL(bk.dathanhtoan, 0)) >0 \n"+
					"	AND a.pk_seq not in ( select tthd.HOADON_FK from ERP_THUTIEN tt inner join ERP_THUTIEN_HOADON tthd on tt.pk_seq = tthd.THUTIEN_FK  where tt.trangthai != 2 and tthd.xoachenhlech = 1 ) \n"+
					"   AND a.KHACHHANG_FK = "+khachhang_fk;
											
					this.HoadonCanTruRs = db.get(query);
					System.out.println(query);
					
				}
				else if(loai == 1) //NHÀ PHÂN PHỐI
				{
					query = 
						"	SELECT  a.PK_SEQ , a.SOHOADON + ' , ' + a.NGAYXUATHD  SOHOADON, a.NGAYXUATHD, \n"+		
						"	a.tongtienavat - isnull(bk.dathanhtoan,0) tongtienavat \n"+
						"	FROM ERP_HOADONNPP a \n"+
						"	INNER JOIN KENHBANHANG e on a.KBH_FK = e.PK_SEQ \n"+
						"	INNER JOIN NHAPHANPHOI b on a.NPP_DAT_FK = b.PK_SEQ \n"+
						"	LEFT JOIN \n"+
						"	( \n"+
						"		SELECT  THU.HOADON_FK, SUM(ISNULL(THU.dathanhtoan,0)) dathanhtoan \n"+
						"		FROM ( \n"+
						"				SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS dathanhtoan \n"+
						"				FROM ERP_THUTIEN_HOADON TTHD \n"+
						"				INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
						"				WHERE TT.TRANGTHAI = 1 AND TTHD.LOAIHOADON = 0 AND isnull(TTHD.XOACHENHLECH,0) = 0 AND TT.BANGKE_FK IS NOT NULL \n"+
						"				GROUP BY HOADON_FK \n"+
			
						"  			UNION ALL \n"+
			
						"				SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS dathanhtoan \n"+
						"				FROM ERP_THUTIEN_HOADON TTHD \n"+
						"				INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
						"				WHERE TT.TRANGTHAI = 1 AND TTHD.LOAIHOADON = 0 AND isnull(TTHD.XOACHENHLECH,0) = 0 AND TT.BANGKE_FK IS NULL \n"+
						"				GROUP BY HOADON_FK \n"+
			
						"  			UNION ALL \n"+
			
						"				SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
						"				FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
						"				INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n"+
						"				WHERE TT.TRANGTHAI = 1 AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '0' \n"+
						"				GROUP BY HOADON_FK \n"+
			
						"  			UNION ALL \n"+
			
						"				SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS DATHANHTOAN \n"+
						"				FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n"+
						"				WHERE  BT.TRANGTHAI = 1 AND BT_KH.LOAIHD = 0 \n"+
						"				GROUP BY BT_KH.HOADON_FK \n"+
			
						"			) THU  \n"+
						"		 GROUP BY  THU.HOADON_FK \n"+
			
						"	) bk on a.PK_SEQ = bk.hoadon_fk \n"+
			
						"	WHERE a.trangthai in ( 2, 4 ) and (a.tongtienavat - ISNULL(bk.dathanhtoan, 0)) >0 \n"+
						"	AND a.pk_seq not in ( select tthd.HOADON_FK from ERP_THUTIEN tt inner join ERP_THUTIEN_HOADON tthd on tt.pk_seq = tthd.THUTIEN_FK  where tt.trangthai != 2 and tthd.xoachenhlech = 1 ) \n"+
						"   AND a.NPP_DAT_FK = "+khachhang_fk;
					
					this.HoadonCanTruRs =db.get(query);
					System.out.println(query);
				}
					

			}
		}
		/*}*/
	}
	
	public void CreateRsDisplay() 
	{
		this.khRs = db.get("select PK_SEQ, TEN from khachhang where TRANGTHAI = 1 and npp_fk in ( select pk_seq from NHAPHANPHOI where congty_fk = '" + this.congtyId + "' ) ");
		
		if(!this.KhId.equals(""))
		{
			//Lay nhung don tra hang da nhap kho
			// va khong nam trong nhung don tra hang ma Hoa don da xoa hoac da huy.
			String query = "	select ddh.pk_seq as id,ddh.NgayTra from DonTraHang ddh where ddh.pk_seq in (select dth_fk from erp_hoadon_dth where hoadon_fk='"+Id+"')";
			System.out.println("__Don tra hang chua xuat hoa don__ "+query);
			this.RsDthChuaXuatHD = db.get(query);
			
			String chuoi = "";
			int i = 0;
			if(this.DonTraHang != null)
			{
				while( i < this.DonTraHang.length)
				{
					if( i == 0 )
					{
						chuoi = DonTraHang[i];
					}
					else
					{
						chuoi += "," + DonTraHang[i];
					}
					i++;
				}
			}
		if(chuoi.length()>0)
			query=
		"SELECT ISNULL ( CAST(SOTIENBVAT AS FLOAT) ,0) AS TIENBVAT, ISNULL(CAST(VAT AS FLOAT),0) AS VAT, \n"+  
		"	 ISNULL(CHIETKHAU,0) AS CHIETKHAU, \n"+  
		"	 ISNULL(CAST (SOTIENAVAT AS FLOAT),0)  AS TIENAVAT , \n"+   
		"	 ISNULL(GHICHU,'') AS GHICHU, ISNULL(NOIDUNGCHIETKHAU,'') AS NOIDUNGCHIETKHAU \n"+
		" , isnull(DTH.KHACHHANG_FK,DTH.NPP_TRA_FK) KHACHHANG_FK,  \n"+
		"    CASE WHEN DTH.KHACHHANG_FK IS NOT NULL THEN 0 WHEN DTH.NPP_TRA_FK IS NOT NULL THEN 1 ELSE 2 END LOAI \n"+
		"FROM DONTRAHANG DTH \n"+  
		" WHERE  KHACHHANG_FK='"+this.KhId+"' AND DTH.PK_SEQ IN( "+chuoi+") " ;
	
		System.out.println(query);
		int loai = 0;
			ResultSet rs = db.get(query);
			try
			{
				if(rs != null)
				{
					if(	rs.next())
					{
						this.TienCK = rs.getDouble("CHIETKHAU");
						this.TienSauCK=rs.getDouble("CHIETKHAU");
						this.TienAVAT = rs.getDouble("TIENAVAT");
						this.TienBVAT = rs.getDouble("TienBVAT");
						this.GhiChu = rs.getString("ghichu");
						loai = rs.getInt("LOAI");
					}rs.close();
				}
			}
			catch(Exception er)
			{
				System.out.println("Exception__"+query);

				
			}
			query =	"  SELECT DDH_SP.SANPHAM_FK,SP.MA,SP.TEN as tensp,DONGIA,SOLUONG AS SOLUONG,SOLUONG*DONGIA AS THANHTIEN , SP.DVDL_FK, "+
					" DONVITINH AS DONVI FROM ERP_HOADON_SP DDH_SP   "+
					" INNER JOIN SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK "+
					" WHERE DDH_SP.SOLUONG >0 AND HOADON_FK="+this.Id +" order by (soluong* dongia) desc ";
 
			System.out.println("AAAAAAAA"+query);
			rs = this.db.get(query);
			this.listsanpham.clear();
			
			
			
			if(rs != null)
			{
				try
				{
					while(rs.next())
					{
						IErpHdTraHang_SanPham sanpham= new ErpHdTraHang_SanPham();
						sanpham.setId(rs.getString("SanPham_FK"));
						sanpham.setMa(rs.getString("ma"));
						sanpham.setTen(rs.getString("tensp"));
						sanpham.setSoLuong(rs.getInt("soluong"));
						sanpham.setDonGia(rs.getDouble("dongia"));
						sanpham.setThanhTien(rs.getDouble("ThanhTien"));
						sanpham.setDonViTinh(rs.getString("DONVI"));
						sanpham.setDVDL(rs.getString("DVDL_FK"));
						
						this.listsanpham.add(sanpham);				
					}rs.close();
				}
				catch(Exception er)
				{
					er.printStackTrace();
				}
			}
			
			if(loai == 0) // KHÁCH HÀNG
			{
				this.HoadonCanTruRs =db.get("SELECT PK_SEQ, SOHOADON + ' , ' + NGAYXUATHD  SOHOADON FROM ERP_HOADON WHERE KHACHHANG_FK ="+this.KhId +" AND TRANGTHAI NOT IN (3,5)");
				System.out.println("SELECT PK_SEQ, SOHOADON FROM ERP_HOADON WHERE KHACHHANG_FK ="+this.KhId +" AND TRANGTHAI NOT IN (3,5)");
			}
			else if(loai == 1) //NHÀ PHÂN PHỐI
			{
				this.HoadonCanTruRs =db.get("SELECT PK_SEQ, SOHOADON + ' , ' + NGAYXUATHD SOHOADON FROM ERP_HOADON WHERE NPP_DAT_FK ="+this.KhId +" AND TRANGTHAI NOT IN (3,5)");
				System.out.println("SELECT PK_SEQ, SOHOADON FROM ERP_HOADON WHERE NPP_DAT_FK ="+this.KhId +" AND TRANGTHAI NOT IN (3,5)");
			}
			
	}
}
	
	public boolean Save() 
	{	
		String sql = "";	
		String ngaytao=getDateTime();
		try
		{
			System.out.println("leng"+ listsanpham.size());
			if(this.listsanpham == null)
			{
				this.setMsg("Sản phẩm xuất hóa đơn chưa có ,vui lòng chọn lại");
				return false;
			}
			
			if(this.listsanpham.size()==0)
			{
				this.setMsg("Sản phẩm xuất hóa đơn chưa có ,vui lòng chọn lại");
				return false;
			}
			
			/*try
			{
				Double.parseDouble(this.SoHoaDon);
			}
			catch(Exception er)
			{
				this.Msg = "So Hoa Don Khong Hop Le";
				return false;
			}*/
			
			if(this.SoHoaDon.length() >0)
			{
				if(checkSoHoaDon()){
					this.Msg = "Số hóa đơn : "+this.SoHoaDon+" đã có, vui lòng chọn lại.";
					System.out.println(this.Msg);
					return false;
				}
			}
			
			if(this.UserId == null || this.UserId.equals(""))
			{
				this.Msg="User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				return false;
			}
			
			if(this.NgayXuatHD==null ||this.NgayXuatHD.trim().length()<=0)
			{
				this.Msg = "Vui long nhap ngay xuat hoa don";
			}
				
			if(this.KhongHD.equals("1")){
				if(this.KyHieu == null||this.KyHieu.length()<=0)
					this.Msg = "Vui lòng nhập ký hiệu hóa đơn";
			}
			
			db.getConnection().setAutoCommit(false);
			 //CreateRs();	 
		 	if(this.TienCK < 0 )
		 	{
		 		this.Msg = "Vui Lòng Kiểm Tra Lại,Tiền chiết khấu Không Được Nhỏ Hơn 0";
		 		return false;
		 	}
		 	
		 	
		 	if(this.listsanpham.size()<= 0)
		 	{
		 		this.Msg="Khong The Tao Hoa Don Nay,Khong Co San Pham Trong Hoa Don";
		 		return false;
		 	}
		 	
		 	String chuoi = "";
			int i = 0;
			if(this.DonTraHang != null)
			{
				while( i < this.DonTraHang.length)
				{
					if( i == 0 )
					{
						chuoi = DonTraHang[i];
					}
					else
					{
						chuoi += "," + DonTraHang[i];
					}
					i++;
				}
			}
			
		 	System.out.println("1.dontrahang: " + this.DonTraHang);
		 	
		 	if(chuoi.trim().length() > 0)
		 	{		 	
			 	sql =  " SELECT DTH.PK_SEQ AS ID, ISNULL( ISNULL( DTH.KHACHHANG_FK, DTH.NPP_DAT_FK ), DTH.NPP_TRA_FK ) KHACHHANG_FK, DTH.NGAYTRA ,  \n " +
					   " CASE WHEN DTH.KHACHHANG_FK IS NOT NULL THEN 0 WHEN DTH.NPP_TRA_FK IS NOT NULL THEN 1 WHEN DTH.NPP_DAT_FK IS NOT NULL THEN 1 ELSE 2 END isNPP, DTH.KBH_FK \n"+
					   " FROM 	DONTRAHANG DTH LEFT JOIN KHACHHANG KH ON DTH.KHACHHANG_FK =  KH.PK_SEQ \n "+
					   " LEFT JOIN NHAPHANPHOI NPP ON DTH.NPP_DAT_FK  = NPP.PK_SEQ \n"+
					   " LEFT JOIN NHAPHANPHOI NPP1 ON DTH.NPP_TRA_FK = NPP1.PK_SEQ \n"+
					   " WHERE  DTH.TRANGTHAI in ( 1, 6 ) AND DTH.PK_SEQ = "+chuoi;

				ResultSet kh_dhRs = db.get(sql);
				
				if(kh_dhRs!=null)
				{
					try
					{
						while(kh_dhRs.next())
						{
							this.KhachhangId = kh_dhRs.getString("KHACHHANG_FK");
							this.isNPP = kh_dhRs.getString("isNPP");
							this.kbhId = kh_dhRs.getString("KBH_FK");
						}
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}

		 	}
		 	
		 	sql = "INSERT INTO ERP_HOADON( NGAYXUATHD, TRANGTHAI, NGAYTAO, NGAYSUA, KYHIEU, SOHOADON, HINHTHUCTT, NGUOITAO, NGUOISUA, KHACHHANG_FK, NGUOIMUA, PO_MT, tienckthuongmai, vat, tongTienAVAT,ghichu, noidungchietkhau,LOAIHOADON, CONGTY_FK, XUATTHEO, tongtienBvat, KHONGHD, isNPP, HdCanTru_fk, KBH_FK) "+
			 	  " VALUES ('" + this.NgayXuatHD + "', '0', '" + ngaytao + "', '" +ngaytao + "', '" + this.KyHieu + "', '" + this.SoHoaDon + "', N'" + this.HinhThucTT + "', '" + this.UserId + "', " +
			 	  "'" + this.UserId + "', "+this.KhachhangId+" , " + this.NguoiMuaHang + ", '" + this.POMT + "', '" + this.TienCK + "','" + this.TienVAT + "', '" + this.TienAVAT + "', " +
			 	  "N'" + this.GhiChu + "', NULL, 2, '"+ this.congtyId +"', '0',"+this.TienBVAT+",  "+this.KhongHD+", "+this.isNPP+" , "+(this.HoadonCanTruId == "" ? "null": this.HoadonCanTruId)+", "+this.kbhId+")";
		 
		 	System.out.println("1.Insert: " + sql);
		
			if(!db.update(sql))
			{
				db.update("rollback");
				this.Msg="Khong The Cap Nhat Hoa Don, Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
		 
			String query = "select SCOPE_IDENTITY() as dhId";
			ResultSet rsDh = db.get(query);	
			
			rsDh.next();
		    this.Id = rsDh.getString("dhId");
		    rsDh.close();
			
			
			if(chuoi.trim().length()>0)
			{
				sql = "insert into ERP_HOADON_DTH (HOADON_FK, DTH_FK) values('" + this.Id + "', '" + chuoi + "') ";
				System.out.println("2.chen don dat hang: " + sql);
				if(!db.update(sql))
				{
					db.update("rollback");
					this.Msg = "Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
					return false;
				}	
			}
	
			int count = 0;
			while(count < this.listsanpham.size())
			{
				IErpHdTraHang_SanPham sanpham = new ErpHdTraHang_SanPham();
				sanpham =listsanpham.get(count);
				double thanhtien = sanpham.getSoLuong() * sanpham.getDonGia();
				double thanhtiensauck = sanpham.getSoLuong() * sanpham.getDonGia();
				sql = "insert into erp_hoadon_sp (SANPHAM_FK, HOADON_FK, SOLUONG, DONGIA, CHIETKHAU, TIENBVAT, VAT, TienAVAT, DONVITINH, SCHEME, PTVAT, DVDL_FK) " +
					  "values (" + sanpham.getId() + ", " + this.Id + ", " + sanpham.getSoLuong() + ", " + sanpham.getDonGia() + ", " +
					  "NULL, '" + thanhtien + "', '" + Math.round((thanhtien * sanpham.getptVat()/100 )) + "', '" + (thanhtien + Math.round((thanhtien * sanpham.getptVat()/100 )) ) + "', N'" + sanpham.getDonViTinh()+ "', NULL, "+sanpham.getptVat()+", "+sanpham.getDVDL()+")";
	
				System.out.println("3.Insert hoa don - san pham: " + sql);
				
				if(!db.update(sql))
				{
					System.out.println("Kho :"+sql);
					this.Msg = "Khong the luu ma san pham : "+ sanpham.getMa()+" ,Loi trong dong lenh sau : " + sql;
					db.update("rollback");
					return false;
				}
				
				count++;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			db.update("rollback");
			this.setMsg( er.toString() + " Error : " +  sql);
			return false;
		}
		
		return true;
	}
	
	public boolean Edit() 
	{
		String sql="";
		String ngaysua=getDateTime();
		try
		{
			
			if(this.UserId==null || this.UserId.equals(""))
			{
				this.Msg="User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				return false;
			}
			
			
			if(this.NgayXuatHD==null ||this.NgayXuatHD.trim().length()<=0)
			{
				this.Msg = "Vui long nhap ngay xuat hoa don";
			}
			
			if(this.SoHoaDon.length() > 0)
			{
				if(checkSoHoaDon()){
					this.Msg = "Số hóa đơn : "+this.SoHoaDon+" đã có, vui lòng chọn lại.";
					System.out.println(this.Msg);
					return false;
				}
			}
			db.getConnection().setAutoCommit(false);
			  
			// CreateRs();	 
			if(this.listsanpham.size()<=0)
			{
		 		this.Msg="Khong The Tao Hoa Don Nay,Khong Co San Pham Trong Hoa Don";
		 		return false;
		 	}		  
			
			String chuoi = "";
			int i = 0;
			if(this.DonTraHang != null)
			{
				while( i < this.DonTraHang.length)
				{
					if( i == 0 )
					{
						chuoi = DonTraHang[i];
					}
					else
					{
						chuoi += "," + DonTraHang[i];
					}
					i++;
				}
			}
			
			if(chuoi.trim().length() > 0)
		 	{		 	
			 	sql =  " SELECT DTH.PK_SEQ AS ID, ISNULL( ISNULL( DTH.KHACHHANG_FK, DTH.NPP_DAT_FK ), DTH.NPP_TRA_FK ) KHACHHANG_FK, DTH.NGAYTRA ,  \n " +
					   " CASE WHEN DTH.KHACHHANG_FK IS NOT NULL THEN 0 WHEN DTH.NPP_TRA_FK IS NOT NULL THEN 1 WHEN DTH.NPP_DAT_FK IS NOT NULL THEN 1 ELSE 2 END isNPP, DTH.KBH_FK \n"+
					   " FROM 	DONTRAHANG DTH LEFT JOIN KHACHHANG KH ON DTH.KHACHHANG_FK =  KH.PK_SEQ \n "+
					   " LEFT JOIN NHAPHANPHOI NPP ON DTH.NPP_DAT_FK  = NPP.PK_SEQ \n"+
					   " LEFT JOIN NHAPHANPHOI NPP1 ON DTH.NPP_TRA_FK = NPP1.PK_SEQ \n"+
					   " WHERE  DTH.TRANGTHAI in ( 1, 6 ) AND DTH.PK_SEQ = "+chuoi;

				ResultSet kh_dhRs = db.get(sql);
				
				if(kh_dhRs!=null)
				{
					try
					{
						while(kh_dhRs.next())
						{
							this.KhachhangId = kh_dhRs.getString("KHACHHANG_FK");
							this.isNPP = kh_dhRs.getString("isNPP");
							this.kbhId = kh_dhRs.getString("KBH_FK");
						}
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}

		 	}
			
			sql = "UPDATE ERP_HOADON  SET ghichu=N'"+this.GhiChu+"', NGAYXUATHD ='"+this.NgayXuatHD+"',NGAYSUA='"+ngaysua+"',KYHIEU ='"+this.KyHieu+"'," +
		 		  "SOHOADON='"+this.SoHoaDon+"',HINHTHUCTT=N'"+this.HinhThucTT+"',NGUOISUA='"+this.UserId+"',KHACHHANG_FK = "+this.KhachhangId+",tongtienbvat='"+this.TienBVAT+"',vat='"+this.TienVAT+"',tongTienAVAT='"+this.TienAVAT+"',tienckthuongmai= '"+this.TienCK+"', LOAIHOADON = '2', KHONGHD = "+this.KhongHD+", isNPP = "+this.isNPP+", HdCanTru_fk = "+(this.HoadonCanTruId == "" ? "null": this.HoadonCanTruId)+", kbh_fk = "+this.kbhId+" where pk_seq="+this.Id;
			
			System.out.println("UPDATE HD: " + sql);
			if(!db.update(sql))
			{
				db.update("rollback");
				this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}	
		 
			sql="delete ERP_HOADON_DTH where hoadon_fk="+this.Id;
			if(!db.update(sql))
			{
				db.update("rollback");
				this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			
			
			if(chuoi.trim().length()>0)
			{
					sql="insert into ERP_HOADON_DTH (HOADON_FK,DTH_FK) values('"+this.Id+"','"+chuoi+"') ";
					if(!db.update(sql)){
						db.update("rollback");
						this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
						return false;
					}	
					i=i+1;
				
			}
			
			
			sql="delete ERP_HOADON_SP where hoadon_fk="+this.Id;
			if(!db.update(sql))
			{
				db.update("rollback");
				this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}	
      		
			int count = 0;
			while(count < this.listsanpham.size())
			{
				IErpHdTraHang_SanPham sanpham = new ErpHdTraHang_SanPham();
				sanpham =listsanpham.get(count);
				double thanhtien = sanpham.getSoLuong() * sanpham.getDonGia();
				sql = 	"insert into erp_hoadon_sp (SANPHAM_FK, HOADON_FK, SOLUONG, DONGIA, CHIETKHAU, TIENBVAT, VAT, TienAVAT, DONVITINH, SCHEME, PTVAT, DVDL_FK ) " +
				  		"values (" + sanpham.getId() + ", " + this.Id + ", " + sanpham.getSoLuong() + ", " + sanpham.getDonGia() + ", " +
				  		"NULL, '" + thanhtien + "', '" + Math.round((thanhtien * sanpham.getptVat()/100 )) + "', '" + (thanhtien + Math.round((thanhtien * sanpham.getptVat()/100 )) ) + "', N'" + sanpham.getDonViTinh()+ "', NULL, "+sanpham.getptVat()+", "+sanpham.getDVDL()+")";

				System.out.println("3.Insert hoa don - san pham: " + sql);
				
				if(!db.update(sql))
				{
					System.out.println("Kho :"+sql);
					this.Msg = "Khong the luu ma san pham : "+ sanpham.getMa()+" ,Loi trong dong lenh sau : " + sql;
					db.update("rollback");
					return false;
				}
				
				count++;
			}	
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			db.update("rollback");
			this.setMsg( er.toString() + " Error : " +  sql);
			return false;
		}
		
		return true;
	}
	
	public ResultSet getRsDthChuaXuatHD() 
	{
		
		return RsDthChuaXuatHD;
	}

	
	public void SetNguoiMuaHang(String nguoimuahang) 
	{
		
		this.NguoiMuaHang=nguoimuahang;
	}
	
	public String getNguoiMuaHang() 
	{
		
		return this.NguoiMuaHang;
	}
	
	public String getKhId()
	{
		
		return this.KhId;
	}
	public void SetKhId(String Khid)
	{
		this.KhId=Khid;
		
	}

	public void SetPOMT(String PoMt) 
	{
		
		this.POMT=PoMt;
	}
	
	public String GetPOMT() 
	{
		
		return this.POMT;
	}
	
	public String getPOInfo() 
	{
		return this.poInfo;
	}
	
	public void setPOInfor(String poInfo) 
	{
		this.poInfo = poInfo;
	}
	
	public List<IErpHdTraHang_SanPham> GetListSanPham() 
	{	
		return this.listsanpham;
	}
	
	public void initdisplay() 
	{
		String query="";
		try
		{
			this.khRs = db.get("select PK_SEQ, TEN from erp_khachhang where TRANGTHAI = 1");
			 query = 
				 " SELECT HD.PK_SEQ,HD.NGAYXUATHD,HD.GHICHU,HD.NOIDUNGCHIETKHAU,HD.PO_MT,HD.NGUOIMUA, \n"+
					"		HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON,HD.HINHTHUCTT, \n"+ 
					"		CASE WHEN HD.isNPP = 0 THEN KH.TEN WHEN HD.isNPP = 1 THEN NPP.TEN WHEN HD.isNPP = 2 THEN NV.TEN END AS TENKH,HD.KHACHHANG_FK, HD.CHIETKHAU,VAT,TONGTIENAVAT,TONGTIENBVAT,HD.HOANTAT, ISNULL(HD.KHONGHD,1) KHONGHD, HdCanTru_fk HdCanTru_fk \n"+ 
					" FROM ERP_HOADON HD LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KhachHang_FK " +
					" LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.KhachHang_FK 	 "+
					" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = HD.KhachHang_FK  "+
					" WHERE HD.PK_SEQ="+this.Id;
						 
				 System.out.println(query);
				db = new dbutils();
				ResultSet rs = db.get(query);
				if(rs != null)
				{	
					while (rs.next())
					{
						this.NgayXuatHD = rs.getString("NGAYXUATHD");
						this.KhId = rs.getString("KHACHHANG_FK");
						this.KhTen = rs.getString("TENKH");
						this.SoHoaDon = rs.getString("SOHOADON");
						this.KyHieu = rs.getString("KYHIEU");
						this.HinhThucTT = rs.getString("HinhThucTT");
						this.TienCK = rs.getDouble("chietkhau");
						this.TienVAT= rs.getDouble("VAT");
						this.TienBVAT = rs.getDouble("TONGTIENBVAT");
						this.TienAVAT=rs.getDouble("TONGTIENAVAT");
						this.Msg = "";
						this.GhiChu = rs.getString("ghichu");
						this.KhongHD = rs.getString("KHONGHD");
						this.HoadonCanTruId = rs.getString("HdCanTru_fk") ;
					}rs.close();
				}
			
			query="SELECT HOADON_FK,DTH_FK FROM ERP_HOADON_DTH WHERE HOADON_FK="+this.Id;
			System.out.println("2.Khoi tao DDH: " + query);
			
			rs = this.db.get(query);
			if(rs != null)
			{
				this.DonTraHang=new String[10];
				int i= 0;
				while(rs.next())
				{
					this.DonTraHang[i] = rs.getString("DTH_FK");
					i++;
				}rs.close();
			}
			else
			{
				this.setMsg("Khong Lay Duoc Gia tri Don hang");
				return;
			}
			CreateRsDisplay();
		}
		catch(Exception er)
		{
			this.Msg="Init Error_"+er.getMessage();
			System.out.println("4.Exception: ");
			 er.printStackTrace();
		}	
		
		
	}


	
	public void SetGhiChu(String ghichu) 
	{
		this.GhiChu=ghichu;	
		
	}

	
	public String getGhiChu() 
	{
		
		return this.GhiChu;
	}

	
	public void Init() 
	{
		String query ="";
		try
		{
			 query = 
				"SELECT HD.PK_SEQ,HD.NGAYXUATHD,HD.GHICHU,ISNULL(HD.NOIDUNGCHIETKHAU,'') NOIDUNGCHIETKHAU, ISNULL(HD.PO_MT,'') PO_MT, ISNULL(HD.NGUOIMUA,'')NGUOIMUA, "+
				"		HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON,HD.HINHTHUCTT,"+ 
				"		KH.TEN AS TENKH,HD.KHACHHANG_FK,CHIETKHAU,VAT,TONGTIENAVAT,TONGTIENBVAT,HD.HOANTAT "+ 
				"FROM ERP_HOADON HD INNER JOIN KHACHHANG KH ON KH.PK_SEQ=HD.KHACHHANG_FK WHERE HD.PK_SEQ="+this.Id;
			
			//System.out.println("1.init: " + query);
			
			db = new dbutils();
			ResultSet rs = db.get(query);
			if(rs != null)
			{	
				while (rs.next())
				{
					this.NgayXuatHD = rs.getString("NGAYXUATHD");
					this.KhId = rs.getString("KHACHHANG_FK");
					this.KhTen = rs.getString("TENKH");
					this.SoHoaDon = rs.getString("SOHOADON");
					this.KyHieu = rs.getString("KYHIEU");
					this.HinhThucTT = rs.getString("HinhThucTT");
					this.TienCK = rs.getDouble("chietkhau");
					this.TienSauCK=rs.getDouble("chietkhau");
					this.TienVAT= rs.getDouble("VAT");
					this.TienBVAT=rs.getDouble("TONGTIENBVAT");
					this.TienAVAT = rs.getDouble("TONGTIENAVAT");
					this.Msg = "";
					this.GhiChu = rs.getString("ghichu");
				}rs.close();
			}
			
			query="SELECT HOADON_FK,DTH_FK FROM ERP_HOADON_DTH WHERE HOADON_FK="+this.Id;
		//	System.out.println("2.Khoi tao DDH: " + query);
			
			rs = this.db.get(query);
			if(rs != null)
			{
				this.DonTraHang=new String[20];
				int i= 0;
				while(rs.next())
				{
					this.DonTraHang[i] = rs.getString("DTH_FK");
					i++;
				}rs.close();
			}
			else
			{
				this.setMsg("Khong Lay Duoc Gia tri Don hang");
				return;
			}
			
		}
		catch(Exception er)
		{
			this.Msg="Init Error_"+er.getMessage();
			System.out.println("4.Exception: ");
			er.printStackTrace();
		}
		
		CreateRs();
	}

	
	public String getUserId()
	{
		
		return this.UserId;
	}

	
	public void setUserId(String UserId)
	{
		this.UserId=UserId;
		
	}

	
	public void setRsDthChuaXuatHD(ResultSet RsDthChuaXuatHD)
	{
		this.RsDthChuaXuatHD=RsDthChuaXuatHD;
		
	}
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	public String XoaHd() 
	{
		dbutils db = new dbutils();
		String query = "update  Erp_HoaDon set trangthai='2', nguoisua='" + this.UserId + "' where pk_Seq = '" + this.Id + "'";
		if(!db.update(query))
		{
			db.update("rollback");
			return "Khong The Huy Hoa Don ,Loi Dong Lenh : "+ query;
		}
		db.shutDown();
		return "";
	}
	
	public String ChotHd()
	{
		//this.Init();
		try
		{
			db.getConnection().setAutoCommit(false);
			Utility util = new Utility();
			
			String query = "update  Erp_HoaDon set trangthai = '1', nguoisua = '" + this.UserId + "' where pk_Seq = '" + this.Id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong The Chot Hoa Don ,Loi Dong Lenh : " + query;
			}
			
			query = "update DONTRAHANG set trangthai = '5' where pk_seq in (select dth_fk from erp_hoadon_dth where hoadon_fk = '" + this.Id + "')";
			System.out.println("___1. CHOT: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong The Chot Hoa Don ,Loi Dong Lenh : " + query;
			}
		
			String taikhoanktCo_KH = "";
			String taikhoanktNo_THUE = "";		
						
		/*	" SELECT HD.PK_SEQ,HD.NGAYXUATHD,HD.GHICHU,HD.NOIDUNGCHIETKHAU,HD.PO_MT,HD.NGUOIMUA, \n"+
			"		HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON,HD.HINHTHUCTT, \n"+ 
			"		CASE WHEN HD.isNPP = 0 THEN KH.TEN WHEN HD.isNPP = 1 THEN NPP.TEN WHEN HD.isNPP = 2 THEN NV.TEN END AS TENKH,HD.KHACHHANG_FK, HD.CHIETKHAU,VAT,TONGTIENAVAT,TONGTIENBVAT,HD.HOANTAT, ISNULL(HD.KHONGHD,1) KHONGHD, HdCanTru_fk HdCanTru_fk \n"+ 
			" FROM ERP_HOADON HD LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KhachHang_FK " +
			" LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.KhachHang_FK 	 "+
			" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = HD.KhachHang_FK  "+
			" WHERE HD.PK_SEQ="+this.Id;*/
			
			query = " select b.ngayxuathd ,(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13100000'  and CONGTY_FK = b.CONGTY_FK ) as TAIKHOANKTCO_KH,(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' and CONGTY_FK = b.CONGTY_FK ) as TAIKHOANNO_THUE, " +					
					"		 b.pk_seq khachhang_fk, b.VAT, isnull(b.isNPP,0) isNPP, isnull(b.ghichu, '') ghichu, b.kbh_fk "+
					" from 	ERP_HOADON b left join khachhang KH  on b.khachhang_fk = KH.pk_seq \n" +
					" LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = b.KhachHang_FK \n"+
					" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = b.KhachHang_FK  \n"+
					" where b.pk_seq = "+ this.Id +" ";
			
			System.out.println("5.Query lay tai khoan: " + query);
			
			ResultSet tkRs = db.get(query);
			String khachhang_fk = "";
			String ngayxuathd =  "";
			String diengiai = "";
			String isNPP = "";
			String kbh_fk = "";
			
			double tienVat = 0;
			
			
			if(tkRs != null)
			{
				if(tkRs.next())
				{
					taikhoanktCo_KH = tkRs.getString("TAIKHOANKTCO_KH") == null ? "" : tkRs.getString("TAIKHOANKTCO_KH");
					taikhoanktNo_THUE = tkRs.getString("TAIKHOANNO_THUE") == null ? "" : tkRs.getString("TAIKHOANNO_THUE");
					khachhang_fk = tkRs.getString("khachhang_fk");
					tienVat = tkRs.getDouble("VAT");					
					ngayxuathd = tkRs.getString("ngayxuathd");
					isNPP = tkRs.getString("isNPP");
					diengiai = tkRs.getString("ghichu");
					kbh_fk = tkRs.getString("kbh_fk"); 
				}
				tkRs.close();
			}
			
			String nam = ngayxuathd.substring(0, 4);
			String thang = ngayxuathd.substring(5, 7);
			
			if(tienVat > 0) // TIỀN THUẾ
			{					
					if(taikhoanktNo_THUE.trim().length() <= 0 || taikhoanktCo_KH.trim().length() <= 0)
					{
						Msg = "6.Khoản mục VAT và nội dung nhận chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
						db.getConnection().rollback();
						return Msg;
					}
					
					//Nguyen te khi xuat kho chinh la VND
					String tiente_fk = "100000";
					
					//NEW KE TOAN
					
					String msg = util.Update_TaiKhoan_Vat_DienGiai_KBH( db, thang, nam, ngayxuathd, ngayxuathd, "Hóa đơn trả hàng khách hàng", this.Id, taikhoanktNo_THUE, taikhoanktCo_KH, "", 
							Double.toString(tienVat), Double.toString(tienVat), "", "", "Khách hàng", khachhang_fk, "0", "", "", tiente_fk, "", "1", Double.toString(tienVat), Double.toString(tienVat), "Hóa đơn - VAT", "0" , diengiai , this.Id, isNPP, kbh_fk);
					
					
				/*	String msg = util.Update_TaiKhoan( db, thang, nam, ngayxuathd,ngayxuathd, "Hóa đơn trả hàng khách hàng", this.Id, taikhoanktNo_THUE, taikhoanktCo_KH, "", 
												Double.toString(tienVat), Double.toString(tienVat), "", "", "Khách hàng",khachhang_fk , "0", "", "", tiente_fk, "0", "1", Double.toString(tienVat), Double.toString(tienVat), "Hóa đơn - VAT" );*/
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return msg;
					}
					
			}					
			
			
						
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			er.printStackTrace();
			
			db.update("rollback");
			er.printStackTrace();
			return "12.Loi Khong The Cap nhat :"+ er.getMessage();
			
		}
		
		System.out.println("__LOI: " + this.Msg);
		return "";
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public void InitDisplay() {

		String query ="";
		try
		{
			
			 query = 
				" SELECT HD.PK_SEQ,HD.NGAYXUATHD,HD.GHICHU,HD.NOIDUNGCHIETKHAU,HD.PO_MT,HD.NGUOIMUA, \n"+
				"		HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON,HD.HINHTHUCTT, \n"+ 
				"		CASE WHEN HD.isNPP = 0 THEN KH.TEN WHEN HD.isNPP = 1 THEN NPP.TEN WHEN HD.isNPP = 2 THEN NV.TEN END AS TENKH,HD.KHACHHANG_FK, HD.CHIETKHAU,VAT,TONGTIENAVAT,TONGTIENBVAT,HD.HOANTAT, ISNULL(HD.KHONGHD,1) KHONGHD, HdCanTru_fk HdCanTru_fk \n"+ 
				" FROM ERP_HOADON HD LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KhachHang_FK " +
				" LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.KhachHang_FK 	 "+
				" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = HD.KhachHang_FK  "+
				" WHERE HD.PK_SEQ="+this.Id;
					 
			 System.out.println(query);
			db = new dbutils();
			ResultSet rs = db.get(query);
			if(rs != null)
			{	
				while (rs.next())
				{
					this.NgayXuatHD = rs.getString("NGAYXUATHD");
					this.KhId = rs.getString("KHACHHANG_FK");
					this.KhTen = rs.getString("TENKH");
					this.SoHoaDon = rs.getString("SOHOADON");
					this.KyHieu = rs.getString("KYHIEU");
					this.HinhThucTT = rs.getString("HinhThucTT");
					this.TienCK = rs.getDouble("chietkhau");
					this.TienVAT= rs.getDouble("VAT");
					this.TienBVAT = rs.getDouble("TONGTIENBVAT");
					this.TienAVAT=rs.getDouble("TONGTIENAVAT");
					this.Msg = "";
					this.GhiChu = rs.getString("ghichu");
					this.KhongHD = rs.getString("KHONGHD");
					this.HoadonCanTruId = rs.getString("HdCanTru_fk") ;
					
				}rs.close();
			}
			
			query="SELECT HOADON_FK,DTH_FK FROM ERP_HOADON_DTH WHERE HOADON_FK = "+this.Id;
	 
			rs = this.db.get(query);
			if(rs != null)
			{
				this.DonTraHang=new String[20];
				int i= 0;
				while(rs.next())
				{
					this.DonTraHang[i] = rs.getString("DTH_FK");
					i++;
				}rs.close();
			}
			else
			{
				this.setMsg("Khong Lay Duoc Gia tri Don hang");
				return;
			}
			
		}
		catch(Exception er)
		{
			this.Msg="Init Error_"+er.getMessage();
		 
			er.printStackTrace();
		}
		
		//CreateRsDisplay();
		CreateRs();
		
		}


	
	public ResultSet getkhRs() {
	
		return this.khRs;
	}

	
	public void setkhRs(ResultSet khRs) {
	
		this.khRs = khRs;
	}
	
	private boolean checkSoHoaDon() {
		
		try{
			String sql= 
			" SELECT COUNT (*) NUM" +
			" FROM ("+
			"		SELECT * FROM ERP_HOADON WHERE SOHOADON = '"+this.SoHoaDon+"' and TRANGTHAI NOT IN (2) ";
					if(this.Id.length() > 0){
						sql=sql+" and pk_seq NOT IN ("+this.Id+")";
					}
			sql+=
			"		UNION ALL \n"+
			"		SELECT * FROM ERP_HOADON WHERE SOHOADONDK = '"+this.SoHoaDon+"' and TRANGTHAI NOT IN (2)";
					if(this.Id.length() > 0){
						sql=sql+" and pk_seq NOT IN ("+this.Id+")";
					}
			sql+= ") a";
						
			ResultSet rs = db.get(sql);
			try {
					rs.next();
					if(rs.getInt("NUM") > 0) {
						return true;
					}
					rs.close();
			} catch(Exception e) { }
			
			rs.close();
		
		}catch(Exception er){
			return false;
		}
		return false;
	}

	
	public double getTienVAT() {
		
		return this.TienVAT;
	}

	
	public void setTienVAT(double TienVAT) {
		
		this.TienVAT = TienVAT;
	}

	
	public double getTienBVAT() {
		
		return this.TienBVAT;
	}

	
	public void setTienBVAT(double TienBVAT) {
		
		this.TienBVAT = TienBVAT;
	}

	
	public double setVAT() {
	
		return this.VAT;
	}

	
	public void SetKhongHD(String KhongHD) {
		this.KhongHD = KhongHD;
		
	}

	
	public String getKhongHD() {
		
		return this.KhongHD;
	}

	
	public String getNppdangnhap() {
	
		return this.Nppdangnhap;
	}

	
	public void setNppdangnhap(String nppdangnhap) {
	
		this.Nppdangnhap  = nppdangnhap;
	}

	
	public ResultSet getHoadonCanTruRs() {
		
		return this.HoadonCanTruRs;
	}

	
	public void setHoadonCanTruRs(ResultSet HoadonCanTruRs) {
		
		this.HoadonCanTruRs = HoadonCanTruRs;
	}

	
	public void SetHoadonCanTruId(String HoadonCanTruId) {
		
		this.HoadonCanTruId = HoadonCanTruId;
	}

	
	public String getHoadonCanTruId() {
		
		return this.HoadonCanTruId;
	}

	
	public String getKhachhangId() {
	
		return this.KhachhangId;
	}

	
	public void setKhachhangId(String khachhangId) {
	
		this.KhachhangId = khachhangId;
	}
	
	
	public String getisNPP() {
		
		return this.isNPP;
	}

	
	public void setisNPP(String isNPP) {
		
		this.isNPP = isNPP;
	}

	
	public String getkbhId() {
	
		return this.kbhId;
	}

	
	public void setkbhId(String kbhId) {
	
		this.kbhId = kbhId;
	}
	
}
	
