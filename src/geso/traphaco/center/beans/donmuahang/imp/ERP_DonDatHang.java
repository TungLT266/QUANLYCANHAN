package geso.traphaco.center.beans.donmuahang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import geso.traphaco.center.beans.donmuahang.IDonvi;
import geso.traphaco.center.beans.donmuahang.IERP_DonDatHang;
import geso.traphaco.center.beans.donmuahang.IERP_DonDatHang_SP;
import geso.traphaco.center.db.sql.dbutils;

public class ERP_DonDatHang implements IERP_DonDatHang
{

	String Id;
	String NgayGiaoDich;
	String TrangThai;
	String NgayTao;
	String NguoiTao;
	String NgaySua;
	String NguoiSua;
	double ChietKhau;
	double VAT;
	float GiaVanChuyen=0;
	
	String Msg;
	String NppId;
	String NppTen;
	String khoId;
	String KhoTen;
	String so;
	String DvkdId;

	String Diachi;
	String DiaChiXhd;
	String Masothue;
	String NgayDeNghiGH;
	double TongTienTruocVAT = 0;
	double TongTienSauVAT = 0;
	String IDNhaCungCap;
	String TenNhaCungCap;
	String KenhBanHangId;
	String ISKM;

	double ChietKhauThuongMai;
	String TuVanChuyen;
	String donviduyetId;
	float TrongLuong;
	float Tongthung;
	float TongTheTich;
	float TongGoiVC;
	String loaidonhang;
	String hinhthucvanchuyen;
	 Hashtable<String, String> ctkmList = new Hashtable<String, String>();
	 
	 
	 
	public String getHinhthucvanchuyen()
	{
		return hinhthucvanchuyen;
	}

	public void setHinhthucvanchuyen(String hinhthucvanchuyen)
	{
		this.hinhthucvanchuyen = hinhthucvanchuyen;
	}

	// Duoi nha phan phoi nhap thong tin tren tt thay
	String ThongTin;
	/*
	 * loai chietkhau =0 la chiet khau tien,1 la chiet khau%
	 */
	String loaichietkhau = "0";

	String SoSO = "";
	ResultSet rskenh;
	ResultSet rskho;
	ResultSet rsnhapp;
	ResultSet rsnhacc;
	ResultSet rsdvkd;
	String userten;
	String ghichu;
	String noidungchietkhau;

	String[] Scheme;
	String[] Sotien;
	List<IERP_DonDatHang_SP> listsanpham = new ArrayList<IERP_DonDatHang_SP>();
	List<IDonvi> dvList;
	List<IERP_DonDatHang_SP> spKmList = new ArrayList<IERP_DonDatHang_SP>();
	public List<IERP_DonDatHang_SP> getSpKmList()
	{
		return spKmList;
	}

	public void setSpKmList(List<IERP_DonDatHang_SP> spKmList)
	{
		this.spKmList = spKmList;
	}

	Hashtable<String, Integer> spThieuList;
	dbutils db;

	/*
	 * Phuong thuc khoi tao
	 */
	public ERP_DonDatHang(String id)
	{
		db = new dbutils();
		this.Id = id;
		spThieuList = new Hashtable<String, Integer>();
		this.loaichietkhau="1";
		String sql = 
				"select isnull(npp.giavanchuyen,0) as giavanchuyen , ISNULL(CHIETKHAUTHUONGMAI,0) AS CHIETKHAUTHUONGMAI, isnull(ddh.tuvanchuyen ,'0') as tuvanchuyen,isnull(ddh.thongtin,'') as thongtin,npp.khosap,isnull(ddh.ghichu,'') as ghichu,isnull(ddh.noidungchietkhau,'') as noidungchietkhau ,ddh.pk_seq,ngaydat,isnull(ngaydenghigh,'') as ngaydenghigh,iskm,isnull (sotienbvat,0) as sotienbvat,ddh.nguoitao,ddh.nguoisua,ddh.trangthai, "
				+ "npp_fk,ncc_fk, isnull(VAT,0)  as vat ,isnull (sotienavat,0) as sotienavat,dvkd_fk,denghidathang_fk "
				+ " ,kbh_fk,isnull(ddh.chietkhau,0) as chietkhau,isnull(loaidonhang,'0') as loaidonhang ,"
				+ " isnull(loaichietkhau,'0') as loaichietkhau,npp.ten as tennpp,npp.diachixhd,npp.diachi,npp.masothue,isnull(HinhThucVanChuyen,'') as HinhThucVanChuyen,isnull(IsDuyetCKTM,0) as IsDuyetCKTM  from dondathang ddh "
				+ " inner join nhaphanphoi npp on npp.pk_seq=npp_fk   where  ddh.pk_Seq=" + this.Id;
		System.out.println("Get Detail : " + sql);
		ResultSet rs = db.get(sql);
		try
		{
			if (rs.next())
			{
				this.NgayGiaoDich = rs.getString("ngaydat");
				this.TuVanChuyen = rs.getString("tuvanchuyen");
				this.hinhthucvanchuyen=rs.getString("HinhThucVanChuyen");
				if(this.TuVanChuyen.trim().equals("1") && rs.getString("loaidonhang").trim().equals("0") )
				{
					this.hinhthucvanchuyen="KHVC";
				}
				this.GiaVanChuyen=rs.getFloat("giavanchuyen");
				
				this.ChietKhauThuongMai = rs.getLong("CHIETKHAUTHUONGMAI");
				this.NppId = rs.getString("npp_fk");
				this.setIdNhaCungCap(rs.getString("ncc_fk"));
				this.setMessage("");
				try
				{
					this.setChietkhau(rs.getDouble("chietkhau"));
				} catch (Exception er)
				{
					this.setChietkhau(0);
				}
				this.setISKM(rs.getString("iskm"));
				this.setNgaygiaodich(rs.getString("ngaydat"));
				this.setNppId(rs.getString("npp_fk"));
				this.setIDKenhBanHang(rs.getString("kbh_fk"));
				this.setdvkdid(rs.getString("dvkd_fk"));
				this.setNgaydenghigh(rs.getString("ngaydenghigh"));

				this.setThongtin(rs.getString("thongtin"));
				try
				{
					this.setTongtientruocVAT(rs.getDouble("sotienbvat"));
				} catch (Exception er)
				{
					this.setTongtientruocVAT(0);
				}
				try
				{
					this.setTongtiensauVAT(rs.getDouble("sotienavat"));

				} catch (Exception er)
				{
					this.setTongtiensauVAT(0);
				}
				this.VAT = rs.getDouble("Vat");
				this.setLoaidonhang(rs.getString("loaidonhang"));
				this.setloaichietkhau(rs.getString("loaichietkhau"));
				this.setNppTen(rs.getString("tennpp"));
				this.setdiachi(rs.getString("diachi"));
				this.setdiachixhd(rs.getString("diachixhd"));
				this.setmasothue(rs.getString("masothue"));
				this.setGhichu(rs.getString("ghichu"));
				this.setNoidungchietkhau(rs.getString("noidungchietkhau"));
				this.isDuyetCKTM = rs.getString("IsDuyetCKTM");
			}

			 sql =
			" select   isnull(soluongduyet,0) as soluongduyet , \n"+
			" dondathang_fk, ddh_sp.sanpham_fk , a.ma, a.ten, donvi, ddh_sp.soluong as soluong, isnull(scheme,'') as Scheme, isnull(a.trongluong, '0') trongluong, isnull(a.thetich, '0') as thetich, \n" +
			" dongia as dongia,qc.soluong1/qc.soluong2 as quycach ,  \n"+
			" 0 as available, isnull(ddh_sp.chietkhau,0)  as chietkhau,isnull(Dvdl_duyet_FK,100018) as Dvdl_duyet_FK,ddh_sp.giachuan as giachuan, \n" +
			" vc.soluong2/qc.soluong1 as goivc \n"+
			" from dondathang_sp ddh_sp  inner join sanpham a on a.pk_seq=sanpham_fk  inner join dondathang \n" +
			" ddh on ddh.pk_Seq = ddh_sp.dondathang_fk \n"+
			" inner join quycach qc on ddh_sp.sanpham_fk = qc.sanpham_fk  and qc.dvdl2_fk=100018 \n" +
			" left join quycach vc on vc.sanpham_fk=ddh_sp.sanpham_fk and vc.dvdl2_fk=100052   \n" +
			" where (ddh_sp.soluongduyet > 0 or ddh_sp.soluong >0 )  and ddh_sp.dondathang_fk= " + id + 
			" order by ddh_sp.sott  ";
//			System.out.println("Get Detail : " + sql);
			float tongtrongluong = 0;
			float tongsothung = 0;
			float tongoivc=0;
			this.TongTheTich = 0;
			rs = db.get(sql);
			if (rs != null)
			{
				while (rs.next())
				{

					String dvduyetId=rs.getString("Dvdl_duyet_FK");
					float soluongduyet=rs.getFloat("soluongduyet");
					float soluongdat=rs.getFloat("soluong");
					float quycach=rs.getFloat("quycach");
					float dongia=rs.getFloat("dongia");
					float giachuan=rs.getFloat("giachuan");
					float trongluong=rs.getFloat("trongluong");
					float thetich=rs.getFloat("thetich");
					double goivc=rs.getDouble("goivc");
					double thanhtien=soluongduyet* dongia;
					
					soluongdat=soluongdat/quycach;
					if(dvduyetId.equals("100018"))
					{
						soluongduyet=soluongduyet/quycach;
						trongluong=trongluong*quycach;
						thetich=thetich*quycach;
						goivc=goivc*quycach;
					}
					IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
					sanpham.setGiachuan(giachuan);
					sanpham.setDonGia(dongia);
					sanpham.setDonViTinh("Thùng");
					sanpham.setId(rs.getString("dondathang_fk"));
					sanpham.setIdSanPham(rs.getString("sanpham_fk"));
					sanpham.setTenSanPham(rs.getString("ten"));
					sanpham.setMaSanPham(rs.getString("ma"));
					sanpham.setSoLuong(soluongdat);
					sanpham.setSHEME(rs.getString("scheme"));
					sanpham.setChietKhau(rs.getDouble("chietkhau"));
					sanpham.setSoluongduyet(soluongduyet);
					sanpham.setThanhTien(thanhtien);
					
					sanpham.setQuyCach(rs.getDouble("quycach"));
					sanpham.setDonviduyetId(dvduyetId);
					sanpham.setGoiVc(goivc);
					sanpham.setTrongluong(trongluong);
					sanpham.setThetich(thetich);
					tongsothung = tongsothung + soluongduyet;
					tongoivc+=goivc*soluongduyet;
					TongTheTich = TongTheTich + thetich;
					tongtrongluong+=trongluong;
					this.listsanpham.add(sanpham);
				}
			}
			this.TrongLuong = tongtrongluong;
			this.Tongthung = tongsothung;
			this.TongGoiVC=tongoivc;
			if (rs != null)
			{
				rs.close();
			}
		} catch (Exception er)
		{
			er.printStackTrace();
		
		}
	}

	public ERP_DonDatHang()
	{
		db = new dbutils();
		Id = "";
		this.ISKM = "";
		NgayGiaoDich = "";
		TrangThai = "";
		NgayTao = "";
		NguoiTao = "";
		NgaySua = "";
		NguoiSua = "";
		ChietKhau = 0;
		this.TuVanChuyen = "0";
		try
		{
			ResultSet rs = this.db.get("select NGAYTONKHO,MUCTANGTRUONG,isnull(thue,0) as thue from CONGTHUCDNDH ");
			if (rs != null)
			{
				if (rs.next())
				{

					this.VAT = rs.getDouble("thue");
				}
			}
			rs.close();
		} catch (Exception er)
		{

		}
		Msg = "";
		NppId = "";
		NppTen = "";
		khoId = "";
		KhoTen = "";
		so = "";
		DvkdId = "";
		TongTienTruocVAT = 0;
		TongTienSauVAT = 0;
		IDNhaCungCap = "";
		TenNhaCungCap = "";
		KenhBanHangId = "";
		SoSO = "";
		Diachi = "";
		DiaChiXhd = "";
		Masothue = "";
		this.ghichu = "";
		this.noidungchietkhau = "";
		this.NgayDeNghiGH = "";
		this.ThongTin = "";
		this.TongGoiVC = 0;
		this.loaichietkhau="1";
		this.loaidonhang="";
		this.hinhthucvanchuyen="CTVC";
	}

	public String getId()
	{

		return this.Id;
	}

	public void setId(String id)
	{

		this.Id = id;
	}

	public String getNgaygiaodich()
	{

		return this.NgayGiaoDich;
	}

	public void setNgaygiaodich(String ngaygiaodich)
	{

		this.NgayGiaoDich = ngaygiaodich;
	}

	public String getNppTen()
	{

		return this.NppTen;
	}

	public void setNppTen(String _nppTen)
	{

		this.NppTen = _nppTen;
	}

	public String getTrangthai()
	{

		return this.TrangThai;
	}

	public void setTrangthai(String trangthai)
	{

		this.TrangThai = trangthai;
	}

	public String getNgaytao()
	{

		return this.NgayTao;
	}

	public void setNgaytao(String ngaytao)
	{

		this.NgayTao = ngaytao;
	}

	public String getNguoitao()
	{

		return this.NguoiTao;
	}

	public void setNguoitao(String nguoitao)
	{

		this.NguoiTao = nguoitao;
	}

	public String getNgaysua()
	{

		return this.NgaySua;
	}

	public void setNgaysua(String ngaysua)
	{

		this.NgaySua = ngaysua;
	}

	public String getNguoisua()
	{

		return this.NguoiSua;
	}

	public void setNguoisua(String nguoisua)
	{

		this.NguoiSua = nguoisua;
	}

	public double getChietkhau()
	{

		return this.ChietKhau;
	}

	public void setChietkhau(double chietkhau)
	{

		this.ChietKhau = chietkhau;
	}

	public double getVAT()
	{

		return this.VAT;
	}

	public void setVAT(double vat)
	{

		this.VAT = vat;
	}

	public String getMessage()
	{

		return this.Msg;
	}

	public void setMessage(String msg)
	{

		this.Msg = msg;
	}

	public void setrs_nhacc(ResultSet rsncc)
	{

		this.rsnhacc = rsncc;
	}

	public ResultSet GetRsnhacc()
	{

		return this.rsnhacc;
	}

	public String getIdNhaCungCap()
	{

		return this.IDNhaCungCap;
	}

	public void setIdNhaCungCap(String idnhacc)
	{

		this.IDNhaCungCap = idnhacc;
	}

	public String getTenNhaCungCap()
	{

		return this.TenNhaCungCap;
	}

	public void setTenNhaCungCap(String tennhacc)
	{

		this.TenNhaCungCap = tennhacc;
	}

	public void setListSanPham(List<IERP_DonDatHang_SP> list)
	{

		this.listsanpham = list;
	}

	public String getIDKenhBanHang()
	{

		return this.KenhBanHangId;
	}

	public void setIDKenhBanHang(String kenhbanhangid)
	{

		this.KenhBanHangId = kenhbanhangid;
	}

	public void setrs_kbh(ResultSet _rskenh)
	{

		this.rskenh = _rskenh;
	}

	public ResultSet GetRsKbh()
	{

		return this.rskenh;
	}

	public List<IERP_DonDatHang_SP> getListSanPham()
	{

		return this.listsanpham;
	}

	public String getNppId()
	{

		return this.NppId;
	}

	public void setNppId(String nppId)
	{

		this.NppId = nppId;
	}

	public void setrs_nhapp(ResultSet rsnpp)
	{

		this.rsnhapp = rsnpp;
	}

	public ResultSet GetRsnhapp()
	{

		return this.rsnhapp;
	}

	public String getKhottId()
	{

		return this.khoId;
	}

	public void setKhottId(String khottid)
	{

		this.khoId = khottid;
	}

	public void setrs_khott(ResultSet rs_kho)
	{

		this.rskho = rs_kho;
	}

	public ResultSet GetRskhott()
	{

		return this.rskho;
	}

	public String getKhottTen()
	{

		return this.KhoTen;
	}

	public void setKhottTen(String KhottTen)
	{

		this.KhoTen = KhottTen;
	}

	public double getTongtientruocVAT()
	{

		return this.TongTienTruocVAT;
	}

	public void setTongtientruocVAT(double tttvat)
	{

		this.TongTienTruocVAT = tttvat;
	}

	public double getTongtiensauVAT()
	{

		return this.TongTienSauVAT;
	}

	public void setTongtiensauVAT(double ttsvat)
	{

		this.TongTienSauVAT = ttsvat;
	}

	public void Init()
	{

		CreateRs();
	}

	public void CreateRs()
	{
		
		try
		{

			String sql = " select pk_seq,ten,isnull(GiaVanChuyen,0) as GiaVanChuyen from nhaphanphoi  npp " ;
			if(this.KenhBanHangId.length()>0)
				sql+=" inner join NHAPP_KBH kenh on npp.PK_SEQ=kenh.NPP_FK and kenh.kbh_fk='"+this.KenhBanHangId+"' ";
			this.rsnhapp = db.get(sql);
			
			
			
			sql = "select pk_seq,ten from nhacungcap where trangthai!='2'";
			this.rsnhacc = db.get(sql);

			sql = "select kbh.pk_seq,kbh.ten from kenhbanhang kbh  ";
			if(this.NppId.length()>0)
			{
				sql+= " inner join NHAPP_KBH  kenh on kenh.kbh_fk=kbh.pk_seq and kenh.npp_fk='"+this.NppId+"'  ";
			}
			sql+= " where kbh.trangthai!='2'  ";
			sql+=" order by kbh.pk_seq desc ";
				/* 
			    * ResultSet rs=this.db.get(sql);
			
				 while(rs.next())
				 {
					this.KenhBanHangId=rs.getString("pk_Seq");
 
				 }rs.close();
			 	*/
				this.rskenh = db.get(sql);
		
				sql = "select PK_SEQ,DONVIKINHDOANH as Ten ,DIENGIAI from DONVIKINHDOANH ";
				if(this.NppId.length()>0) {
					 sql+=
					" where PK_SEQ in "+ 
					" (	select dvkd_fk from NHACUNGCAP_DVKD where PK_SEQ in "+ 
					"	( "+
					"		select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where  npp_fk in ('"+this.NppId+"') "+ 
					"	) "+
					") and TRANGTHAI=1 ";
					 
					 
				}
				this.rsdvkd = db.get(sql);
		
				this.rskho = db.get("select pk_seq,ten from erp_khott");
				
				this.dvList = new ArrayList<IDonvi>();
				ResultSet donvi = db.get("SELECT PK_SEQ,DONVI,DIENGIAI FROM DONVIDOLUONG WHERE PK_SEQ IN(100018,100047)");
				this.dvList.clear();
				if (donvi != null)
				{
				 
					while (donvi.next())
					{
						this.dvList.add(new Donvi(donvi.getString("pk_seq"), donvi.getString("donvi")));
					}
					donvi.close();
				}
				ResultSet rs=null;
				
				if(this.NppId.trim().length() >0){
					sql="select pk_seq,ten,isnull(GiaVanChuyen,0) as GiaVanChuyen from nhaphanphoi  npp where pk_seq="+this.NppId ;
					  rs=db.get(sql);
						if(rs.next()){
							this.GiaVanChuyen=rs.getFloat("GiaVanChuyen");
						}
					
				}
				if(rs!=null){
					rs.close();
				}
				sql="	select SCHEME+' - '+DIENGIAI as ctkmTen,SCHEME as ctkmId "+
					"	from CTKHUYENMAI where KHO_FK=100001 ";
				rs=db.get(sql);
				if(rs!=null)
				{	
					 while( rs.next())
					 {
						ctkmList.put(rs.getString("ctkmId"),rs.getString("ctkmTen"));
					 }
				}
				
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		 
		 
	}

	public Hashtable<String, Integer> getSpThieuList()
	{

		return this.spThieuList;
	}

	public void setSpThieuList(Hashtable<String, Integer> spThieuList)
	{

		this.spThieuList = spThieuList;
	}

	public boolean Save()
	{

		System.out.println("____save____");
		String sql = "";

		try
		{
			if (this.spThieuList != null)
				if (this.spThieuList.size() > 0)
				{
					this.Msg = "So luong ton kho khong du xuat, vui long nhap lai so luong";
					return false;
				}

			if (this.khoId.equals(""))
			{
				this.Msg = "Kho Ban Hang Khong Duoc Rong";
			}
			if (this.NguoiTao == null || this.NguoiTao.equals(""))
			{
				this.Msg = "User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				return false;
			}

			if (this.NppId == null || this.NppId.equals(""))
			{
				this.Msg = "Nha Phan Phoi Khong Duoc Rong";
			}

			db.getConnection().setAutoCommit(false);
			sql =
			" insert into dondathang(GIAVANCHUYEN,HinhThucVanChuyen,TUVANCHUYEN,NGAYDAT,TRANGTHAI,NGUOITAO,NGUOISUA,NPP_FK,DVKD_FK,NCC_FK,kbh_FK,loaidonhang,iskm,tinhtrang,Nam,Thang,CHIETKHAU,CHIETKHAUTHUONGMAI,VAT) " +
			" values ("+this.GiaVanChuyen+", N'"+this.hinhthucvanchuyen+"','" + this.TuVanChuyen + "','"+ this.NgayGiaoDich + "','1'," + this.NguoiTao + "," + this.NguoiSua + "," + this.NppId + "," + this.DvkdId + "," + this.IDNhaCungCap + "," + this.KenhBanHangId+ " ,'0','0','1','"+this.NgayGiaoDich.split("-")[0]+"','"+this.NgayGiaoDich.split("-")[1]+"','"+this.ChietKhau+"','"+this.ChietKhauThuongMai+"','"+this.VAT+"')  ";
			if (!db.update(sql))
			{
				db.update("rollback");
				this.Msg = "Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			// Save chi tiet don hang

			String query = "select IDENT_CURRENT('dondathang') as dhId";
			ResultSet rsDh = db.get(query);
			try
			{
				rsDh.next();
				this.Id = rsDh.getString("dhId");
				rsDh.close();
			} catch (Exception er)
			{
				db.update("rollback");
				this.setMessage("Loi  - Clasname :Erp_DonMuaHang - line 293 : " + er.toString());
				return false;
			}
			String chuoi_scheme = "";
			int count = 0;
			double tongtien = 0;

			while (count < this.listsanpham.size())
			{
				IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
				sanpham = listsanpham.get(count);
				if (sanpham.getSoLuong() <= 0 || sanpham.getDonGia() <= 0)
				{
					this.Msg = "Khong the luu ma san pham : " + sanpham.getMaSanPham() + " ,Cap Nhat So Luong Va Gia Lon hon 0";
					db.update("rollback");
					return false;
				}
				double thanhtien=sanpham.getSoLuong() * sanpham.getDonGia()*sanpham.getQuyCach();
				tongtien = tongtien + thanhtien -thanhtien / 100 * sanpham.getChietKhau();
				count = count + 1;
			}
			count=0;
			while (count < this.listsanpham.size())
			{
				IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
				sanpham = listsanpham.get(count);
				sanpham.setId(this.Id);
				double thanhtien = sanpham.getSoLuong() * sanpham.getDonGia()*sanpham.getQuyCach();
				double chietkhausp = sanpham.getChietKhau();
				double vatsp = (thanhtien - chietkhausp) * this.VAT / 100;
				double tienavat = (thanhtien - chietkhausp) + vatsp;
				double quycach = sanpham.getQuyCach();

				sql = "insert into dondathang_sp (sott,sanpham_fk,dondathang_fk,soluong,soluongduyet,dongia,donvi,sotienbvat ,chietkhau,vat,sotienavat,scheme,GiaChuan,DVDL_DUYET_FK )values " +
						" ( "+count+", "
						+ sanpham.getIdSanPham() + "," + this.Id + "," + sanpham.getSoLuong() * quycach + "," + sanpham.getSoLuong() * quycach + ","
						+ sanpham.getDonGia() + ",N'" + sanpham.getDonViTinh() + "','" + thanhtien + "','" + chietkhausp + "','" + vatsp + "','" + tienavat + "','"
						+ chuoi_scheme + "','" + sanpham.getGiachuan()  + "','" + sanpham.getDonviduyetId() + "')";
				if (!db.update(sql))
				{
					this.Msg = "Khong the luu ma san pham : " + sanpham.getMaSanPham() + " ,Loi trong dong lenh sau : " + sql;
					db.update("rollback");
					return false;
				}
				System.out.println("[Insert Sp]"+sql);
				count++;
			}
			double tienCKTT=tongtien*this.ChietKhau/100;
			double tiensauck=tongtien-tienCKTT-this.ChietKhauThuongMai;;
			double vat = tiensauck * this.VAT / 100;
			double tongtiencovat = tiensauck + vat;
			if(this.ChietKhauThuongMai>tiensauck)
			{
				if(tongtien-tienCKTT-1000<ChietKhauThuongMai)
					ChietKhauThuongMai=tongtien-tienCKTT-1000;
				vat=(tongtien-tienCKTT-ChietKhauThuongMai)*this.VAT/100;
				tongtiencovat=tongtien-tienCKTT-ChietKhauThuongMai+vat;
			}
			sql = "update dondathang set giavanchuyen="+this.GiaVanChuyen+", chietkhauthuongmai=" + this.ChietKhauThuongMai + ",sotienavat='" + tongtiencovat + "',sotienbvat='" + tongtien+ "',loaichietkhau='" + this.loaichietkhau + "',ghichu = N'" + this.ghichu + "',noidungchietkhau = N'" + this.noidungchietkhau+ "'  where pk_Seq=" + this.Id;
			if (!db.update(sql))
			{
				this.Msg = "Loi Nhap Lieu,Vui Long Xem Lai.Error :" + sql;
				db.update("rollback");
				return false;
			}		
			sql=
			"	update ThanhToanCKTM set sudung=ISNULL(SuDung,0)+ISNULL(ChietKhauThuongMai,0) \n"+
			"	from  "+
			"	( "+
			"		select "+ 
			"		sum  "+
			"		(   "+
			"			(  "+
			"				a.soluongduyet*a.dongia*(1-(isnull(a.chietkhau,0)/100)) "+ 
			"			) *(1-isnull(b.chietkhau,0)/100 )  "+
			"		) as doanhso,b.npp_fk,(select convert(varchar(10),dateadd(month,-1,substring(ngaydat,0,8)+'-01'),20)) as thoigian,ChietKhauThuongMai "+
			"		from dondathang_sp a inner join dondathang b on a.dondathang_fk=b.pk_seq   "+
			"		where a.dondathang_fk='"+this.Id+"'  "+
			"		group by b.npp_fk,ngaydat,ChietKhauThuongMai "+
			"	) as dh inner join ThanhToanCKTM as ck on "+
			"	dh.npp_fk=ck.npp_fk and ck.nam=substring(dh.thoigian,0,5) and ck.thang=substring(dh.thoigian,6,2) ";
			if (!db.update(sql))
			{
				this.Msg = "Lỗi hệ thống " + sql;
				db.update("rollback");
				return false;
			}		
			System.out.println("[Cap Nhat CKTM]"+sql);
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception er)
		{
			db.update("rollback");
			this.setMessage(er.toString() + " Error : " + sql);
			return false;
		}
		return true;
	}

	public boolean SaveKm()
	{
		String sql = "";
		try
		{
			if (this.NguoiTao == null || this.NguoiTao.equals(""))
			{
				this.Msg = "User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				return false;
			}
			if (this.NppId == null || this.NppId.equals(""))
			{
				this.Msg = "Nha Phan Phoi Khong Duoc Rong";
			}
			db.getConnection().setAutoCommit(false);
			this.VAT = 10;
			sql = " insert into dondathang(GiaVanChuyen,HinhThucVanChuyen,NGAYDAT,TRANGTHAI,NGUOITAO,NGUOISUA,NPP_FK,DVKD_FK,NCC_FK,kbh_FK,loaidonhang,ISKM, ghichu,tinhtrang,khott_fk,KmTT) " + " values ('"+this.GiaVanChuyen+"','"+this.hinhthucvanchuyen+"','" + this.NgayGiaoDich
					+ "','1'," + this.NguoiTao + "," + this.NguoiSua + "," + this.NppId + "," + this.DvkdId + "," + this.IDNhaCungCap + "," + this.KenhBanHangId + " ,'1','1', N'" + this.ghichu
					+ "','1','" +100003 + "',1)  ";

			if (!db.update(sql))
			{
				db.update("rollback");
				this.setMessage("Lỗi cập nhật "+sql);
				return false;
			}
			int count = 0;
			double tongtien = 0;

			String query = "select IDENT_CURRENT('dondathang') as dhId";
			ResultSet rsDh = db.get(query);
			try
			{
				rsDh.next();
				this.Id = rsDh.getString("dhId");
				rsDh.close();
			} catch (Exception er)
			{
				er.printStackTrace();
				db.update("rollback");
				this.setMessage("Lỗi cập nhật "+sql);
			}
			while (count < this.listsanpham.size())
			{
				IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
				sanpham = listsanpham.get(count);
				sanpham.setId(this.Id);
				double quy_cach=sanpham.getQuyCach();
				double so_luong=sanpham.getSoLuong()*quy_cach+sanpham.getSoluongle();
				double thanhtien = so_luong * sanpham.getDonGia();
				if(sanpham.getCtkmId().equals(""))
				{
					this.setMessage("Vui lòng chọn Scheme ");
					db.update("rollback");
					return false;
				}
				sql = "insert into dondathang_sp (sanpham_fk,dondathang_fk,soluong,SoLuongLe,dongia,donvi,sotienbvat,vat,sotienavat,scheme,ChietKhau,SOLUONGDUYET,THUCXUAT,DVDL_DUYET_FK,GIACHUAN,SOTT)values (" + sanpham.getIdSanPham() + "," + sanpham.getId() + ","
						+ sanpham.getSoLuong()*quy_cach + ","+sanpham.getSoluongle()+", " + sanpham.getDonGia() + ",N'THÙNG','" + thanhtien + "','" + thanhtien * 0.1 + "','" + thanhtien * 1.1 + "','"
						+ sanpham.getCtkmId() + "',0,'"+so_luong+"',0,100018,"+sanpham.getDonGia()+","+count+")";
				tongtien = tongtien + so_luong * sanpham.getDonGia();
				if (!db.update(sql))
				{
					this.setMessage("Lỗi cập nhật "+sql);
					db.update("rollback");
					return false;
				}
				System.out.println("[Dong]"+count+"[sql]"+sql);
				count++;
			}
	
			double tongtiencovat = tongtien * 1.1;
			sql = "update dondathang set sotienavat='" + tongtiencovat + "',vat='" +this.VAT+ "',sotienbvat='" + tongtien + "',chietkhau='0',loaichietkhau='" + this.loaichietkhau+ "' where pk_Seq=" + this.Id;
			System.out.println("[Dong]"+count+"[sql]"+sql);
			if (!db.update(sql))
			{
				this.setMessage("Lỗi cập nhật "+sql);
				db.update("rollback");
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception er)
		{
			db.update("rollback");
			this.setMessage("Lỗi cập nhật "+sql);
			er.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean Edit(String ischot)
	{
		System.out.println("____edit____");
		try
		{			
			if (this.spThieuList.size() > 0)
			{
				this.Msg = "So luong ton kho khong du xuat, vui long nhap lai so luong";
				return false;
			}

			if (this.NguoiTao.toString().equals("null") || this.NguoiTao.equals(""))
			{
				this.Msg = "Nguoi Dung Dang Nhap Da Bi Log Vi ly Do Bao Mat He Thong, Vui Long Dang Nhap Lai De Thuc Hien Chuong Trinh";
				return false;
			}
			if(this.ChietKhauThuongMai>0&&ischot.equals("1")&& this.isDuyetCKTM.equals("0"))
			{
				this.Msg = "Đơn hàng này thỏa CKTM,bạn phải duyệt CKTKM này trước khi chốt !";
				return false;
			}
			db.getConnection().setAutoCommit(false);
			String 	sql=	
			"	update ThanhToanCKTM set sudung=ISNULL(SuDung,0)-ISNULL(ChietKhauThuongMai,0) \n"+
			"	from  \n"+
			"	( \n"+
			"		select \n"+ 
			"		sum  \n"+
			"		(   "+
			"			(  \n"+
			"				a.soluongduyet*a.dongia*(1-(isnull(a.chietkhau,0)/100)) \n"+ 
			"			) *(1-isnull(b.chietkhau,0)/100 )  \n"+
			"		) as doanhso,b.npp_fk,(select convert(varchar(10),dateadd(month,-1,substring(ngaydat,0,8)+'-01'),20)) as thoigian,ChietKhauThuongMai \n"+
			"		from dondathang_sp a inner join dondathang b on a.dondathang_fk=b.pk_seq   \n"+
			"		where a.dondathang_fk='"+this.Id+"'  \n"+
			"		group by b.npp_fk,ngaydat,ChietKhauThuongMai \n"+
			"	) as dh inner join ThanhToanCKTM as ck on \n"+
			"	dh.npp_fk=ck.npp_fk and ck.nam=substring(dh.thoigian,0,5) and ck.thang=substring(dh.thoigian,6,2) ";
			if (!db.update(sql))
			{
				db.update("rollback");
				this.Msg = "Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			System.out.println("[Cap nhat thanh toan CKTM 1]"+sql);
			
			// xoa het chi tiet cu
			 sql = "delete from dondathang_sp where dondathang_fk=" + this.Id;
			if (!db.update(sql))
			{
				db.update("rollback");
				this.Msg = "Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			// Xoa scheme
			sql = "delete from DONDATHANG_SCHEME where dondathang_fk=" + this.Id;
			if (!db.update(sql))
			{
				db.update("rollback");
				this.Msg = "Khong The Duyet Scheme ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			
			// se luu trong dondathang_sp neu co scheme cua chiet khau
			String chuoi_scheme = "";

			
			// Save chi tiet don hang
			int count = 0;
			double tongtien = 0;

			while (count < this.listsanpham.size())
			{
				IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
				sanpham = listsanpham.get(count);
			/*	if ( sanpham.getsoluongduyet() <= 0)
				{
					this.Msg = "Vui lòng kiểm tra lại số lượng duyệt sản phẩm "+sanpham.getMaSanPham() +"";
					db.update("rollback");
					return false;
				}*/
				if ((sanpham.getSoLuong() <= 0 && sanpham.getsoluongduyet() <= 0) || sanpham.getDonGia() <= 0)
				{
					this.Msg = "Khong the luu ma san pham : " + sanpham.getMaSanPham() + " ,Cap Nhat So Luong Va Gia Lon hon 0";
					db.update("rollback");
					return false;
				}
				double quycach = sanpham.getQuyCach();
				if (sanpham.getChietKhau() > 100)
				{
					this.Msg = "Khong the luu ma san pham : " + sanpham.getMaSanPham() + " ,Chiết khấu không vượt quá 100%";
					db.update("rollback");
					return false;
				}
				double soluongduyet=sanpham.getsoluongduyet();
				if(sanpham.getDonviduyetId().trim().equals("100018"))
				{
					soluongduyet=sanpham.getsoluongduyet()*quycach;				
				}
				tongtien = tongtien + soluongduyet * sanpham.getDonGia() - soluongduyet * sanpham.getDonGia() / 100 * sanpham.getChietKhau();
				count = count + 1;
			}
			count=0;
			while (count < this.listsanpham.size())
			{
				IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
				sanpham = listsanpham.get(count);
				double quycach =sanpham.getQuyCach();				
				double soluongduyet=sanpham.getsoluongduyet();
				if(sanpham.getDonviduyetId().trim().equals("100018"))
				{
					soluongduyet=sanpham.getsoluongduyet()*quycach;					
				}
				double soluongdat=sanpham.getSoLuong()* quycach;
				double thanhtien =soluongduyet * sanpham.getDonGia();
				double chietkhausp = sanpham.getChietKhau();
				double vatsp = (thanhtien - chietkhausp) * this.VAT / 100;
				double tienavat = (thanhtien - chietkhausp) + vatsp;
				sql = "insert into dondathang_sp (sott,sanpham_fk,dondathang_fk,soluong,soluongduyet,dongia,donvi,sotienbvat ,chietkhau,vat,sotienavat,scheme,GiaChuan,DVDL_DUYET_FK )values " +
						" ("+count+","
						+ sanpham.getIdSanPham() + "," + this.Id + "," + soluongdat + "," +soluongduyet + ","
						+ sanpham.getDonGia() + ",N'" + sanpham.getDonViTinh() + "','" + thanhtien + "','" + chietkhausp + "','" + vatsp + "','" + tienavat + "','"
						+ chuoi_scheme + "','" + sanpham.getGiachuan()  + "','" + sanpham.getDonviduyetId() + "')";
				if (!db.update(sql))
				{
					this.Msg = "Khong the luu ma san pham : " + sanpham.getMaSanPham() + " ,Loi trong dong lenh sau : " + sql;
					db.update("rollback");
					return false;
				}
				count++;
			}
			double tienCKTT=tongtien*this.ChietKhau/100;
			double tiensauck=tongtien-tienCKTT-this.ChietKhauThuongMai;
			double vat = tiensauck * this.VAT / 100;
			double tongtiencovat = tiensauck + vat;
			if(this.ChietKhauThuongMai>tiensauck)
			{
				if(tongtien-tienCKTT-1000<ChietKhauThuongMai)
					ChietKhauThuongMai=tongtien-tienCKTT-1000;
				vat=(tongtien-tienCKTT-ChietKhauThuongMai)*this.VAT/100;
				tongtiencovat=tongtien-tienCKTT-ChietKhauThuongMai+vat;
			}			
			if (ischot.equals("1"))
			{
				sql = "  update dondathang set Giavanchuyen="+this.GiaVanChuyen+", HinhThucVanChuyen=N'"+this.hinhthucvanchuyen+"',chietkhauthuongmai=" +this.ChietKhauThuongMai + ",tuvanchuyen='" + this.TuVanChuyen + "',NGUOIDUYET=" + this.NguoiSua
						+ ", DATEDUYET=GETDATE(), trangthai='2', NGUOISUA=" + this.NguoiSua + ",VAT='" + this.VAT + "',SOTIENBVAT='" +tongtien + "' ,SOTIENAVAT='" + tongtiencovat
						+ "', npp_fk='" + this.NppId + "',dvkd_fk='" + this.DvkdId + "',kbh_fk='" + this.KenhBanHangId + "' ,chietkhau='"+this.ChietKhau+"',loaichietkhau='" + this.loaichietkhau
						+ "',ghichu = N'" + this.ghichu + "',noidungchietkhau = N'" + this.noidungchietkhau + "',NGAYDAT='" + this.NgayGiaoDich + "' where pk_seq=" + this.Id;
				System.out.println("SQL ischot=1 :" + sql);
			} else
			{
				sql = "  update dondathang set Giavanchuyen="+this.GiaVanChuyen+", HinhThucVanChuyen=N'"+this.hinhthucvanchuyen+"',chietkhauthuongmai=" + this.ChietKhauThuongMai + ", tuvanchuyen='" + this.TuVanChuyen + "',NGUOIDUYET=" + this.NguoiSua
						+ ", DATEDUYET=GETDATE() , NGUOISUA=" + this.NguoiSua + ",VAT='" + this.VAT + "',SOTIENBVAT='" + tongtien + "' ,SOTIENAVAT='" + tongtiencovat + "' ,npp_fk='"
						+ this.NppId + "',dvkd_fk='" + this.DvkdId + "',kbh_fk='" + this.KenhBanHangId + "' ,chietkhau='" + this.ChietKhau + "',loaichietkhau='" + this.loaichietkhau + "',ghichu = N'"
						+ this.ghichu + "',noidungchietkhau = N'" + this.noidungchietkhau + "',NGAYDAT='" + this.NgayGiaoDich + "' where pk_seq=" + this.Id;
				System.out.println("SQL ischot#1 :" + sql);
			}

			if (!db.update(sql))
			{
				db.update("rollback");
				this.Msg = "Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			sql=
			"	update ThanhToanCKTM set sudung=ISNULL(SuDung,0)+ISNULL(ChietKhauThuongMai,0) \n"+
			"	from  \n"+
			"	( \n"+
			"		select \n"+ 
			"		sum  \n"+
			"		(   \n"+
			"			(  \n"+
			"				a.soluongduyet*a.dongia*(1-(isnull(a.chietkhau,0)/100)) \n"+  
			"			) *(1-isnull(b.chietkhau,0)/100 )  \n"+
			"		) as doanhso,b.npp_fk,(select convert(varchar(10),dateadd(month,-1,substring(ngaydat,0,8)+'-01'),20)) as thoigian,ChietKhauThuongMai \n"+
			"		from dondathang_sp a inner join dondathang b on a.dondathang_fk=b.pk_seq   \n"+
			"		where a.dondathang_fk='"+this.Id+"'  \n"+
			"		group by b.npp_fk,ngaydat,ChietKhauThuongMai \n"+
			"	) as dh inner join ThanhToanCKTM as ck on \n"+
			"	dh.npp_fk=ck.npp_fk and ck.nam=substring(dh.thoigian,0,5) and ck.thang=substring(dh.thoigian,6,2) ";
			if (!db.update(sql))
			{
				db.update("rollback");
				this.Msg = "Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			System.out.println("[Cap nhat thanh toan CKTM]"+sql);
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception er)
		{
			db.update("rollback");
			this.setMessage(er.toString());
			return false;
		}
		return true;
	}

	public boolean Editkm(String ischot)
	{
		try
		{
			if (this.NguoiTao.toString().equals("null") || this.NguoiTao.equals(""))
			{
				this.Msg = "Nguoi Dung Dang Nhap Da Bi Log Vi ly Do Bao Mat He Thong, Vui Long Dang Nhap Lai De Thuc Hien Chuong Trinh";
				return false;
			}
			db.getConnection().setAutoCommit(false);// chu y
			
			String sql = "";
			sql = "delete from dondathang_sp where dondathang_fk=" + this.Id;
			if (!db.update(sql))
			{
				db.update("rollback");
				this.Msg = "Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			int count = 0;
			double tongtien = 0;
			
			while (count < this.listsanpham.size())
			{
				IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
				sanpham = listsanpham.get(count);
				sanpham.setId(this.Id);
				double quy_cach=sanpham.getQuyCach();
				double so_luong=sanpham.getSoLuong()*quy_cach+sanpham.getSoluongle();
				double thanhtien = so_luong * sanpham.getDonGia();
				if(sanpham.getCtkmId().equals(""))
				{
					this.setMessage("Vui lòng chọn Scheme ");
					db.update("rollback");
					return false;
				}
				sql = "insert into dondathang_sp (sanpham_fk,dondathang_fk,soluong,SoLuongLe,dongia,donvi,sotienbvat,vat,sotienavat,scheme,ChietKhau,SOLUONGDUYET,THUCXUAT,DVDL_DUYET_FK,GIACHUAN,SOTT)values (" + sanpham.getIdSanPham() + "," + sanpham.getId() + ","
						+ sanpham.getSoLuong()*quy_cach + ","+sanpham.getSoluongle()+", " + sanpham.getDonGia() + ",N'THÙNG','" + thanhtien + "','" + thanhtien * 0.1 + "','" + thanhtien * 1.1 + "','"
						+ sanpham.getCtkmId() + "',0,'"+so_luong+"',0,100018,"+sanpham.getDonGia()+","+count+")";
				tongtien = tongtien + so_luong * sanpham.getDonGia();
				if (!db.update(sql))
				{
					this.setMessage("Lỗi cập nhật "+sql);
					db.update("rollback");
					return false;
				}
				System.out.println("[Dong]"+count+"[sql]"+sql);
				count++;
			}
			double tongtiencovat = tongtien * 1.1;
			if (ischot.equals("1"))
			{
				sql = "  update dondathang set Giavanchuyen="+this.GiaVanChuyen+", HinhThucVanChuyen=N'"+this.hinhthucvanchuyen+"',chietkhauthuongmai=" +this.ChietKhauThuongMai + ",tuvanchuyen='" + this.TuVanChuyen + "',NGUOIDUYET=" + this.NguoiSua
						+ ", DATEDUYET=GETDATE(), trangthai='2', NGUOISUA=" + this.NguoiSua + ",VAT='" + this.VAT + "',SOTIENBVAT='" +tongtien + "' ,SOTIENAVAT='" + tongtiencovat
						+ "', npp_fk='" + this.NppId + "',dvkd_fk='" + this.DvkdId + "',kbh_fk='" + this.KenhBanHangId + "' ,chietkhau='"+this.ChietKhau+"',loaichietkhau='" + this.loaichietkhau
						+ "',ghichu = N'" + this.ghichu + "',noidungchietkhau = N'" + this.noidungchietkhau + "',NGAYDAT='" + this.NgayGiaoDich + "' where pk_seq=" + this.Id;
				System.out.println("SQL ischot=1 :" + sql);
			} else
			{
				sql = "  update dondathang set Giavanchuyen="+this.GiaVanChuyen+", HinhThucVanChuyen=N'"+this.hinhthucvanchuyen+"',chietkhauthuongmai=" + this.ChietKhauThuongMai + ", tuvanchuyen='" + this.TuVanChuyen + "',NGUOIDUYET=" + this.NguoiSua
						+ ", DATEDUYET=GETDATE() , NGUOISUA=" + this.NguoiSua + ",VAT='" + this.VAT + "',SOTIENBVAT='" + tongtien + "' ,SOTIENAVAT='" + tongtiencovat + "' ,npp_fk='"
						+ this.NppId + "',dvkd_fk='" + this.DvkdId + "',kbh_fk='" + this.KenhBanHangId + "' ,chietkhau='" + this.ChietKhau + "',loaichietkhau='" + this.loaichietkhau + "',ghichu = N'"
						+ this.ghichu + "',noidungchietkhau = N'" + this.noidungchietkhau + "',NGAYDAT='" + this.NgayGiaoDich + "' where pk_seq=" + this.Id;
				System.out.println("SQL ischot#1 :" + sql);
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception er)
		{
			db.update("rollback");
			this.setMessage(er.toString());
			er.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean Delete()
	{

		return false;
	}

	public ResultSet getrsdvkd()
	{

		return this.rsdvkd;
	}

	public void setrsdvkd(ResultSet rsdvkd)
	{

		this.rsdvkd = rsdvkd;
	}

	public void setdvkdid(String dvkdid)
	{

		this.DvkdId = dvkdid;
	}

	public String getdvkdid()
	{

		return DvkdId;
	}

	public void setUserTen(String _Userten)
	{

		this.userten = _Userten;
	}

	public String getUserten()
	{

		return this.userten;
	}

	public void DBClose()
	{
		try
		{

			if (rskenh != null)
			{
				rskenh.close();
			}
			if (rskho != null)
			{
				rskho.close();
			}

			if (rsnhapp != null)
			{
				rsnhapp.close();
			}

			if (rsnhacc != null)
			{
				rsnhacc.close();
			}

			if (rsdvkd != null)
			{
				rsdvkd.close();
			}
			if (db != null)
			{
				db.shutDown();
			}
		} catch (Exception err)
		{

		}
	}

	public String GetLoaidonhang()
	{

		return this.loaidonhang;
	}

	public void setLoaidonhang(String loaidonhang)
	{
		this.loaidonhang=loaidonhang;
	}

	public void setloaichietkhau(String _loaichietkhau)
	{

		loaichietkhau = _loaichietkhau;
	}

	public String getloaichietkhau()
	{

		return this.loaichietkhau;
	}

	public String getdiachi()
	{

		return this.Diachi;
	}

	public String getdiachixhd()
	{

		return this.DiaChiXhd;
	}

	public String getmasothue()
	{

		return this.Masothue;
	}

	public void setdiachi(String diachi)
	{

		this.Diachi = diachi;
	}

	public void setdiachixhd(String diachixhd)
	{

		this.DiaChiXhd = diachixhd;
	}

	public void setmasothue(String mst)
	{

		this.Masothue = mst;
	}

	public String getISKM()
	{

		return this.ISKM;
	}

	public void setISKM(String iskm)
	{
		this.ISKM = iskm;

	}

	public String[] getSotien()
	{
		return this.Sotien;
	}

	public void setSotien(String[] sotien)
	{
		this.Sotien = sotien;
	}

	public String[] getScheme()
	{
		return this.Scheme;
	}

	public void setScheme(String[] scheme)
	{
		this.Scheme = scheme;

	}

	public String getGhichu()
	{
		return this.ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}

	public String getNoidungchietkhau()
	{
		return this.noidungchietkhau;
	}

	public void setNoidungchietkhau(String noidungchietkhau)
	{
		this.noidungchietkhau = noidungchietkhau;
	}

	@Override
	public String getNgaydenghigh()
	{
		
		return this.NgayDeNghiGH;
	}

	@Override
	public void setNgaydenghigh(String ngaydenghigh)
	{
		
		this.NgayDeNghiGH = ngaydenghigh;
	}

	@Override
	public float GetTrongLuong()
	{
		
		return this.TrongLuong;
	}

	@Override
	public void setTrongLuong(float trongluong)
	{
		
		this.TrongLuong = trongluong;
	}

	@Override
	public float GetTongThung()
	{
		
		return this.Tongthung;
	}

	@Override
	public void setTongthung(float tongthung)
	{
		
		this.Tongthung = tongthung;
	}

	@Override
	public String getThongTin()
	{
		
		return this.ThongTin;
	}

	@Override
	public void setThongtin(String thongtin)
	{
		
		this.ThongTin = thongtin;
	}

	@Override
	public float GetTheTich()
	{
		
		return this.TongTheTich;
	}

	@Override
	public void setTheTich(float TheTich)
	{
		
		this.TongTheTich = TheTich;
	}

	@Override
	public void setChietKhauThuongMai(double chietkhautm)
	{
		
		this.ChietKhauThuongMai = chietkhautm;
	}

	@Override
	public double getChietKhauThuongMai()
	{
		
		return this.ChietKhauThuongMai;
	}

	@Override
	public void setTuVanChuyen(String TuVanChuyen)
	{
		
		this.TuVanChuyen = TuVanChuyen;
	}

	@Override
	public String getTuVanChuyen()
	{
		
		return this.TuVanChuyen;
	}

	@Override
	public void load_tudongchuyen()
	{
		
		try
		{
			String sql = "select isnull(tuvanchuyen,0) as tuvanchuyen from nhaphanphoi where pk_seq=" + this.NppId;
			ResultSet rs = db.get(sql);
			if (rs.next())
			{
				this.TuVanChuyen = rs.getString("tuvanchuyen");
				if(this.TuVanChuyen.equals("1")){
					this.hinhthucvanchuyen="KHVC";
				}
				rs.close();
			}

		} catch (Exception er)
		{

		}
	}

	public float getTongGoiVC()
	{
		return TongGoiVC;
	}

	public void setTongGoiVC(float tongGoiVC)
	{
		TongGoiVC = tongGoiVC;
	}

	public String getDonviduyetId()
	{
		return donviduyetId;
	}

	public void setDonviduyetId(String donviduyetId)
	{
		this.donviduyetId = donviduyetId;
	}

	public List<IDonvi> getDvList()
	{
		return this.dvList;
	}

	public void setDvList(List<IDonvi> dvList)
	{
		this.dvList = dvList;
	}

	@Override
	public void initDisplay()
	{
		spThieuList = new Hashtable<String, Integer>();
		this.loaichietkhau="1";
		String sql = 
				"select isnull(ddh.giavanchuyen,0) as giavanchuyen ,ISNULL(CHIETKHAUTHUONGMAI,0) AS CHIETKHAUTHUONGMAI, isnull(ddh.tuvanchuyen ,'0') as tuvanchuyen,isnull(ddh.thongtin,'') as thongtin,npp.khosap,isnull(ddh.ghichu,'') as ghichu,isnull(ddh.noidungchietkhau,'') as noidungchietkhau ,ddh.pk_seq,ngaydat,isnull(ngaydenghigh,'') as ngaydenghigh,iskm,isnull (sotienbvat,0) as sotienbvat,ddh.nguoitao,ddh.nguoisua,ddh.trangthai, "
				+ "npp_fk,ncc_fk, isnull(VAT,0)  as vat ,isnull (sotienavat,0) as sotienavat,dvkd_fk,denghidathang_fk "
				+ " ,kbh_fk,isnull(ddh.chietkhau,0) as chietkhau,isnull(loaidonhang,'0')"
				+ " as loaidonhang, isnull(loaichietkhau,'0') as loaichietkhau,npp.ten as tennpp,npp.diachixhd,npp.diachi,npp.masothue,isnull(HinhThucVanChuyen,'') as HinhThucVanChuyen from dondathang ddh "
				+ " inner join nhaphanphoi npp on npp.pk_seq=npp_fk   where  ddh.pk_Seq=" + this.Id;
		System.out.println("Get Detail : " + sql);
		ResultSet rs = db.get(sql);
		try
		{
			if (rs.next())
			{
				this.NgayGiaoDich = rs.getString("ngaydat");
				this.GiaVanChuyen=rs.getFloat("giavanchuyen");
				
				this.TuVanChuyen = rs.getString("tuvanchuyen");
				this.ChietKhauThuongMai = rs.getLong("CHIETKHAUTHUONGMAI");
				this.NppId = rs.getString("npp_fk");
				this.setIdNhaCungCap(rs.getString("ncc_fk"));
				this.setMessage("");
				try
				{
					this.setChietkhau(rs.getDouble("chietkhau"));
				} catch (Exception er)
				{
					this.setChietkhau(0);
				}
				this.setISKM(rs.getString("iskm"));
				this.setNgaygiaodich(rs.getString("ngaydat"));
				this.setNppId(rs.getString("npp_fk"));
				this.setIDKenhBanHang(rs.getString("kbh_fk"));
				this.setdvkdid(rs.getString("dvkd_fk"));
				this.setNgaydenghigh(rs.getString("ngaydenghigh"));

				this.setThongtin(rs.getString("thongtin"));
				try
				{
					this.setTongtientruocVAT(rs.getDouble("sotienbvat"));
				} catch (Exception er)
				{
					this.setTongtientruocVAT(0);
				}
				try
				{
					this.setTongtiensauVAT(rs.getDouble("sotienavat"));

				} catch (Exception er)
				{
					this.setTongtiensauVAT(0);
				}
				this.VAT = rs.getDouble("Vat");
				this.setLoaidonhang(rs.getString("loaidonhang"));
				this.setloaichietkhau(rs.getString("loaichietkhau"));
				this.setNppTen(rs.getString("tennpp"));
				this.setdiachi(rs.getString("diachi"));
				this.setdiachixhd(rs.getString("diachixhd"));
				this.setmasothue(rs.getString("masothue"));
				this.setGhichu(rs.getString("ghichu"));
				this.setNoidungchietkhau(rs.getString("noidungchietkhau"));
				this.hinhthucvanchuyen=rs.getString("HinhThucVanChuyen");
			}
			// Thuc hien lay tong tin don hang
			 sql =
			"select   isnull(soluongduyet,0) as soluongduyet , \n"+
			" dondathang_fk, ddh_sp.sanpham_fk , a.ma, a.ten, donvi, ddh_sp.soluong as soluong, isnull(scheme,'') as Scheme, isnull(a.trongluong, '0') trongluong, isnull(a.thetich, '0') as thetich, \n" +
			" dongia as dongia,qc.soluong1/qc.soluong2 as quycach ,  \n"+
			" 0 as available, isnull(ddh_sp.chietkhau,0)  as chietkhau,isnull(Dvdl_duyet_FK,100018) as Dvdl_duyet_FK,ddh_sp.giachuan as giachuan, \n" +
			" vc.soluong2/qc.soluong1 as goivc \n"+
			" from dondathang_sp ddh_sp  inner join sanpham a on a.pk_seq=sanpham_fk  inner join dondathang \n" +
			" ddh on ddh.pk_Seq = ddh_sp.dondathang_fk \n"+
			" inner join quycach qc on ddh_sp.sanpham_fk = qc.sanpham_fk  and qc.dvdl2_fk=100018 \n" +
			" left join quycach vc on vc.sanpham_fk=ddh_sp.sanpham_fk and vc.dvdl2_fk=100052   \n" +
			" where (ddh_sp.soluongduyet > 0 or ddh_sp.soluong >0 )  and ddh_sp.dondathang_fk= " + this.Id+  
			" order by ddh_sp.sott ";
			System.out.println("Get Detail : " + sql);
			float tongtrongluong = 0;
			float tongsothung = 0;
			float tongoivc=0;
			this.TongTheTich = 0;
			rs = db.get(sql);
			if (rs != null)
			{
				while (rs.next())
				{

					String dvduyetId=rs.getString("Dvdl_duyet_FK");
					float soluongduyet=rs.getFloat("soluongduyet");
					float soluongdat=rs.getFloat("soluong");
					float quycach=rs.getFloat("quycach");
					float dongia=rs.getFloat("dongia");
					float giachuan=rs.getFloat("giachuan");
					float trongluong=rs.getFloat("trongluong");
					float thetich=rs.getFloat("thetich");
					double goivc=rs.getDouble("goivc");
					double thanhtien=soluongduyet* dongia;
					
					soluongdat=soluongdat/quycach;
					if(dvduyetId.equals("100018"))
					{
						soluongduyet=soluongduyet/quycach;
						trongluong=trongluong*quycach*soluongduyet;
						thetich=thetich*quycach*soluongduyet;
						goivc=goivc*quycach;
					}
					IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
					sanpham.setGiachuan(giachuan);
					sanpham.setDonGia(dongia);
					sanpham.setDonViTinh("Thùng");
					sanpham.setId(rs.getString("dondathang_fk"));
					sanpham.setIdSanPham(rs.getString("sanpham_fk"));
					sanpham.setTenSanPham(rs.getString("ten"));
					sanpham.setMaSanPham(rs.getString("ma"));
					sanpham.setSoLuong(soluongdat);
					sanpham.setSHEME(rs.getString("scheme"));
					sanpham.setChietKhau(rs.getDouble("chietkhau"));
					sanpham.setSoluongduyet(soluongduyet);
					sanpham.setThanhTien(thanhtien);
					
					sanpham.setQuyCach(rs.getDouble("quycach"));
					sanpham.setDonviduyetId(dvduyetId);
					sanpham.setGoiVc(goivc);
					sanpham.setTrongluong(trongluong);
					sanpham.setThetich(thetich);
					tongsothung = tongsothung + soluongduyet;
					tongoivc+=goivc*soluongduyet;
					TongTheTich += thetich/1000000;
					tongtrongluong+=trongluong;
					this.listsanpham.add(sanpham);
				}
				if (rs != null)
				{
					rs.close();
				}
			}
			this.TrongLuong = tongtrongluong/1000;
			this.Tongthung = tongsothung;
			this.TongGoiVC=tongoivc;
			/************** San pham Le thung***********************************/
			sql=
			"select "+  
			"	ISNULL(km.soluong,0) as soluongduyet, \n"+
			"	dondathang_fk,km.SANPHAM_FK,sp.MA as spMa,sp.TEN as spTen,N'Thùng' as DonVi, \n"+
			"	km.SOLUONG  as SoLuong,isnull(km.SCHEME,'') as SCHEME ,isnull(sp.trongluong, '0') trongluong, isnull(sp.thetich, '0') as thetich, \n"+ 
			"	km.DONGIA as DonGia,qc.soluong1/qc.soluong2 as quycach ,   \n"+
			"	0 as available, 0  as chietkhau,100018 as Dvdl_duyet_FK,km.DONGIA as giachuan, \n"+ 
			" vc.soluong2/qc.soluong1 as goivc  \n"+
			"from DONDATHANG_CTKM km  \n"+
			"	left join SANPHAM sp on sp.PK_SEQ=km.SANPHAM_FK \n"+
			"	left join QUYCACH qc on qc.SANPHAM_FK=sp.PK_SEQ and qc.DVDL2_FK=100018 \n"+
			"	left join quycach vc on vc.sanpham_fk=km.sanpham_fk and vc.dvdl2_fk=100052 \n"+   
			"where km.DONDATHANG_FK=" + this.Id+" ";
			rs = db.get(sql);
			while (rs.next())
			{
				String dvduyetId=rs.getString("Dvdl_duyet_FK");
				float soluongduyet=rs.getFloat("soluongduyet");
				float soluongdat=rs.getFloat("soluong");
				float quycach=rs.getFloat("quycach");
				float dongia=rs.getFloat("dongia");
				float giachuan=rs.getFloat("giachuan");
				float trongluong=rs.getFloat("trongluong");
				float thetich=rs.getFloat("thetich");
				double goivc=rs.getDouble("goivc");
				double thanhtien=soluongduyet* dongia;
				soluongdat=soluongdat/quycach;
				if(dvduyetId.equals("100018"))
				{
					soluongduyet=soluongduyet/quycach;
					trongluong=trongluong*quycach*soluongduyet;
					thetich=thetich*quycach*soluongduyet;
					goivc=goivc*quycach;
				}
				IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
				sanpham.setGiachuan(giachuan);
				sanpham.setDonGia(dongia);
				sanpham.setDonViTinh("Thùng");
				sanpham.setId(rs.getString("dondathang_fk"));
				sanpham.setIdSanPham(rs.getString("sanpham_fk"));
				sanpham.setTenSanPham(rs.getString("spTen"));
				sanpham.setMaSanPham(rs.getString("spMa"));
				sanpham.setSoLuong(soluongdat);
				sanpham.setSHEME(rs.getString("scheme"));
				sanpham.setChietKhau(rs.getDouble("chietkhau"));
				sanpham.setSoluongduyet(soluongduyet);
				sanpham.setThanhTien(thanhtien);
				sanpham.setQuyCach(rs.getDouble("quycach"));
				sanpham.setDonviduyetId(dvduyetId);
				sanpham.setGoiVc(goivc);
				sanpham.setTrongluong(trongluong);
				sanpham.setThetich(thetich);
				tongsothung = tongsothung + soluongduyet;
				tongoivc+=goivc*soluongduyet;
				TongTheTich += thetich/1000000;
				tongtrongluong+=trongluong;
				this.spKmList.add(sanpham);
			}
			if (rs != null)
			{
				rs.close();
			}
		} catch (Exception er)
		{
			er.printStackTrace();
		}
		CreateRs();
	}

	@Override
	public void setGiaVanChuyen(float giavc) 
	{
		this.GiaVanChuyen=giavc;
	}

	@Override
	public float getGiaVanChuyen() 
	{
		return this.GiaVanChuyen;
	}

	@Override
	public Hashtable<String, String> getCtkmList()
	{
		return ctkmList;
	}

	@Override
	public void  setCtkmList(Hashtable<String, String> ctkmList)
	{
		this.ctkmList=	 ctkmList;
	}

	@Override
	public void initDisplayKm()
	{
		spThieuList = new Hashtable<String, Integer>();
		this.loaichietkhau="1";
		String sql = 
				"select isnull(ddh.giavanchuyen,0) as giavanchuyen ,ISNULL(CHIETKHAUTHUONGMAI,0) AS CHIETKHAUTHUONGMAI, isnull(ddh.tuvanchuyen ,'0') as tuvanchuyen,isnull(ddh.thongtin,'') as thongtin,npp.khosap,isnull(ddh.ghichu,'') as ghichu,isnull(ddh.noidungchietkhau,'') as noidungchietkhau ,ddh.pk_seq,ngaydat,isnull(ngaydenghigh,'') as ngaydenghigh,iskm,isnull (sotienbvat,0) as sotienbvat,ddh.nguoitao,ddh.nguoisua,ddh.trangthai, "
				+ "npp_fk,ncc_fk, isnull(VAT,0)  as vat ,isnull (sotienavat,0) as sotienavat,dvkd_fk,denghidathang_fk "
				+ " ,kbh_fk,isnull(ddh.chietkhau,0) as chietkhau,isnull(loaidonhang,'0')"
				+ " as loaidonhang, isnull(loaichietkhau,'0') as loaichietkhau,npp.ten as tennpp,npp.diachixhd,npp.diachi,npp.masothue,isnull(HinhThucVanChuyen,'') as HinhThucVanChuyen from dondathang ddh "
				+ " inner join nhaphanphoi npp on npp.pk_seq=npp_fk   where  ddh.pk_Seq=" + this.Id;
		System.out.println("Get Detail : " + sql);
		ResultSet rs = db.get(sql);
		try
		{
			if (rs.next())
			{
				this.NgayGiaoDich = rs.getString("ngaydat");
				this.GiaVanChuyen=rs.getFloat("giavanchuyen");
				
				this.TuVanChuyen = rs.getString("tuvanchuyen");
				this.ChietKhauThuongMai = rs.getLong("CHIETKHAUTHUONGMAI");
				this.NppId = rs.getString("npp_fk");
				this.setIdNhaCungCap(rs.getString("ncc_fk"));
				this.setMessage("");
				try
				{
					this.setChietkhau(rs.getDouble("chietkhau"));
				} catch (Exception er)
				{
					this.setChietkhau(0);
				}
				this.setISKM(rs.getString("iskm"));
				this.setNgaygiaodich(rs.getString("ngaydat"));
				this.setNppId(rs.getString("npp_fk"));
				this.setIDKenhBanHang(rs.getString("kbh_fk"));
				this.setdvkdid(rs.getString("dvkd_fk"));
				this.setNgaydenghigh(rs.getString("ngaydenghigh"));

				this.setThongtin(rs.getString("thongtin"));
				try
				{
					this.setTongtientruocVAT(rs.getDouble("sotienbvat"));
				} catch (Exception er)
				{
					this.setTongtientruocVAT(0);
				}
				try
				{
					this.setTongtiensauVAT(rs.getDouble("sotienavat"));

				} catch (Exception er)
				{
					this.setTongtiensauVAT(0);
				}
				this.VAT = rs.getDouble("Vat");
				this.setLoaidonhang(rs.getString("loaidonhang"));
				this.setloaichietkhau(rs.getString("loaichietkhau"));
				this.setNppTen(rs.getString("tennpp"));
				this.setdiachi(rs.getString("diachi"));
				this.setdiachixhd(rs.getString("diachixhd"));
				this.setmasothue(rs.getString("masothue"));
				this.setGhichu(rs.getString("ghichu"));
				this.setNoidungchietkhau(rs.getString("noidungchietkhau"));
				this.hinhthucvanchuyen=rs.getString("HinhThucVanChuyen");
			}
			// Thuc hien lay tong tin don hang
			 sql =
			"select   isnull(soluongduyet,0) as soluongduyet , \n"+
			" dondathang_fk, ddh_sp.sanpham_fk , a.ma, a.ten, donvi, ddh_sp.soluong as soluong, isnull(scheme,'') as Scheme, isnull(a.trongluong, '0') trongluong, isnull(a.thetich, '0') as thetich, \n" +
			" dongia as dongia,qc.soluong1/qc.soluong2 as quycach ,  \n"+
			" 0 as available, isnull(ddh_sp.chietkhau,0)  as chietkhau,isnull(Dvdl_duyet_FK,100018) as Dvdl_duyet_FK,ddh_sp.giachuan as giachuan, \n" +
			" vc.soluong2/qc.soluong1 as goivc,ISNULL(ddh_sp.SoLuongLe,0) as SoLuongLe,ddh_sp.scheme \n"+
			" from dondathang_sp ddh_sp  inner join sanpham a on a.pk_seq=sanpham_fk  inner join dondathang \n" +
			" ddh on ddh.pk_Seq = ddh_sp.dondathang_fk \n"+
			" inner join quycach qc on ddh_sp.sanpham_fk = qc.sanpham_fk  and qc.dvdl2_fk=100018 \n" +
			" left join quycach vc on vc.sanpham_fk=ddh_sp.sanpham_fk and vc.dvdl2_fk=100052   \n" +
			" where (ddh_sp.soluongduyet > 0 or ddh_sp.soluong >0 )  and ddh_sp.dondathang_fk= " + this.Id+  
			" order by ddh_sp.sott ";
			System.out.println("Get Detail : " + sql);
			float tongtrongluong = 0;
			float tongsothung = 0;
			float tongoivc=0;
			this.TongTheTich = 0;
			rs = db.get(sql);
			if (rs != null)
			{
				while (rs.next())
				{

					String dvduyetId=rs.getString("Dvdl_duyet_FK");
					float soluongduyet=rs.getFloat("soluongduyet");
					float soluongdat=rs.getFloat("soluong");
					float soluongle=rs.getFloat("soluongle");
					float quycach=rs.getFloat("quycach");
					float dongia=rs.getFloat("dongia");
					float giachuan=rs.getFloat("giachuan");
					float trongluong=rs.getFloat("trongluong");
					float thetich=rs.getFloat("thetich");
					double goivc=rs.getDouble("goivc");
					double thanhtien=soluongduyet* dongia;
					soluongdat=soluongdat/quycach;
					if(dvduyetId.equals("100018"))
					{
						soluongduyet=soluongduyet/quycach;
						trongluong=trongluong*quycach*soluongduyet;
						thetich=thetich*quycach*soluongduyet;
						goivc=goivc*quycach;
					}
					IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
					sanpham.setGiachuan(giachuan);
					sanpham.setDonGia(dongia);
					sanpham.setDonViTinh("Thùng");
					sanpham.setId(rs.getString("dondathang_fk"));
					sanpham.setIdSanPham(rs.getString("sanpham_fk"));
					sanpham.setTenSanPham(rs.getString("ten"));
					sanpham.setMaSanPham(rs.getString("ma"));
					sanpham.setSoLuong(soluongdat);
					sanpham.setSHEME(rs.getString("scheme"));
					sanpham.setChietKhau(rs.getDouble("chietkhau"));
					sanpham.setSoluongduyet(soluongduyet);
					sanpham.setThanhTien(thanhtien);
					
					sanpham.setQuyCach(rs.getDouble("quycach"));
					sanpham.setDonviduyetId(dvduyetId);
					sanpham.setGoiVc(goivc);
					sanpham.setTrongluong(trongluong);
					sanpham.setThetich(thetich);
					sanpham.setSoluongle(soluongle   );
					sanpham.setCtkmId(  rs.getString("scheme")   );
					tongsothung = tongsothung + soluongduyet;
					tongoivc+=goivc*soluongduyet;
					TongTheTich += thetich/1000000;
					tongtrongluong+=trongluong;
					this.listsanpham.add(sanpham);
				}
				if (rs != null)
				{
					rs.close();
				}
			}
			this.TrongLuong = tongtrongluong/1000;
			this.Tongthung = tongsothung;
			this.TongGoiVC=tongoivc;
		} catch (Exception er)
		{
			er.printStackTrace();
		}
		CreateRs();
		
	}
	String isDuyetCKTM;

	@Override
	public String getIsDuyetCKTM()
	{
		return this.isDuyetCKTM;
	}

	@Override
	public void setIsDuyetCKTM(String isduyet)
	{
		this.isDuyetCKTM=isduyet;
	}

	@Override
	public boolean   DuyetCKTM()
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			System.out.println("[DuyetCKTM]");
			String query ="UPDATE DONDATHANG SET ISDUYETCKTM =1 WHERE PK_SEQ="+this.Id+" AND CHIETKHAUTHUONGMAI>0 AND ISNULL(ISDUYETCKTM,0)=0";
			if(this.db.updateReturnInt(query) <=0)
			{
				this.db.update("rollback");
				this.Msg =" Vui lòng kiểm tra lại đơn đặt hàng !"+query;
				return false;
			}
			System.out.println("[DuyetCKTM]"+query);
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			e.printStackTrace();
			this.db.update("rollback");
			this.Msg ="Exception,Vui lòng liên hệ trung tâm để giải quyết !";
			return false;
		}
		return false;
	}
	public void initExcel()
	{
		this.loaichietkhau="1";
		String sql = 
			"select isnull(npp.giavanchuyen,0) as giavanchuyen , ISNULL(CHIETKHAUTHUONGMAI,0) AS CHIETKHAUTHUONGMAI, isnull(ddh.tuvanchuyen ,'0') as tuvanchuyen,isnull(ddh.thongtin,'') as thongtin,npp.khosap,isnull(ddh.ghichu,'') as ghichu,isnull(ddh.noidungchietkhau,'') as noidungchietkhau ,ddh.pk_seq,ngaydat,isnull(ngaydenghigh,'') as ngaydenghigh,iskm,isnull (sotienbvat,0) as sotienbvat,ddh.nguoitao,ddh.nguoisua,ddh.trangthai, "
			+ "npp_fk,ncc_fk, isnull(VAT,0)  as vat ,isnull (sotienavat,0) as sotienavat,dvkd_fk,denghidathang_fk "
			+ " ,kbh_fk,isnull(ddh.chietkhau,0) as chietkhau,isnull(loaidonhang,'0') as loaidonhang ,"
			+ " isnull(loaichietkhau,'0') as loaichietkhau,npp.ten as tennpp,npp.diachixhd,npp.diachi,npp.masothue,isnull(HinhThucVanChuyen,'') as HinhThucVanChuyen,isnull(IsDuyetCKTM,0) as IsDuyetCKTM  from dondathang ddh "
			+ " inner join nhaphanphoi npp on npp.pk_seq=npp_fk   where  ddh.pk_Seq=" + this.Id;
		System.out.println("Get Detail : " + sql);
		ResultSet rs = db.get(sql);
		try
		{
			if (rs.next())
			{
				this.NgayGiaoDich = rs.getString("ngaydat");
				this.TuVanChuyen = rs.getString("tuvanchuyen");
				this.hinhthucvanchuyen=rs.getString("HinhThucVanChuyen");
				if(this.TuVanChuyen.trim().equals("1") && rs.getString("loaidonhang").trim().equals("0") )
				{
					this.hinhthucvanchuyen="KHVC";
				}
				this.GiaVanChuyen=rs.getFloat("giavanchuyen");
				
				this.ChietKhauThuongMai = rs.getLong("CHIETKHAUTHUONGMAI");
				this.NppId = rs.getString("npp_fk");
				this.setIdNhaCungCap(rs.getString("ncc_fk"));
				this.setMessage("");
				try
				{
					this.setChietkhau(rs.getDouble("chietkhau"));
				} catch (Exception er)
				{
					this.setChietkhau(0);
				}
				this.setISKM(rs.getString("iskm"));
				this.setNgaygiaodich(rs.getString("ngaydat"));
				this.setNppId(rs.getString("npp_fk"));
				this.setIDKenhBanHang(rs.getString("kbh_fk"));
				this.setdvkdid(rs.getString("dvkd_fk"));
				this.setNgaydenghigh(rs.getString("ngaydenghigh"));

				this.setThongtin(rs.getString("thongtin"));
				try
				{
					this.setTongtientruocVAT(rs.getDouble("sotienbvat"));
				} catch (Exception er)
				{
					this.setTongtientruocVAT(0);
				}
				try
				{
					this.setTongtiensauVAT(rs.getDouble("sotienavat"));

				} catch (Exception er)
				{
					this.setTongtiensauVAT(0);
				}
				this.VAT = rs.getDouble("Vat");
				this.setLoaidonhang(rs.getString("loaidonhang"));
				this.setloaichietkhau(rs.getString("loaichietkhau"));
				this.setNppTen(rs.getString("tennpp"));
				this.setdiachi(rs.getString("diachi"));
				this.setdiachixhd(rs.getString("diachixhd"));
				this.setmasothue(rs.getString("masothue"));
				this.setGhichu(rs.getString("ghichu"));
				this.setNoidungchietkhau(rs.getString("noidungchietkhau"));
				this.isDuyetCKTM = rs.getString("IsDuyetCKTM");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			db.shutDown();
		}
	}
}