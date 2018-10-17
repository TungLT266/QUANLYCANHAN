package geso.traphaco.center.beans.thongtinsanpham.imp;

import geso.traphaco.center.beans.thongtinsanpham.IThongtinsanpham;
import geso.traphaco.center.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;
import java.util.Hashtable;

public class Thongtinsanpham implements IThongtinsanpham, Serializable {
	private static final long serialVersionUID = -9217977546733690415L;
	String userId;
	String id;
	String masp;
	String maspcu;
	String ten;

	String dvdlId;
	String dvdlTen;
	String dvdlETCId;
	ResultSet dvdl;

	String dvkdId;
	String dvkdTen;
	ResultSet dvkd;

	String nhId;
	String nhTen;
	ResultSet nh;

	String clId;
	String clTen;
	ResultSet cl;

	String trangthai;
	String trangthaiDL;
	String spmoi;
	String spchuluc;
	String giablc;

	String msg;

	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String tenhoatchat = "";
	String dangtrinhbay = "";
	String quycachdonggoi = "";
	String tieuchuanchatluong = "";
	String nguongocnguyenlieu ="";
	String tieuchuannsx = "";
	String nuocsx = "";
	String giabancothue = "";
	String tenviettat = "";
	String giakktheodvt = "";
	String stttheoTT40 = "";
	String nhomsphtotc = "";
	String nguongoc ="";
	String thuesuat = "";
	String hethanvisa = "";
	
	
	
	String Quydoithuong;
	String hansudung;

	String[] nspIds;
	ResultSet nsp;
	ResultSet nspSelected;
	ResultSet RsNganhHang;
	String nganhhangid;
	String[] sl1;
	String[] dvdl1;
	String[] sl2;
	String[] dvdl2;

	String tenxuathoadon;
	String[] tenxuathoadonArr;
	String nguongocnhapkhau;
	String[] nguongocnhapkhauArr;
	String[] ngayArr;
	String[] thongtinArr;
	String[] ghichuArr;
	ResultSet loaispRs;

	String type;
	String[] spIds;
	String[] spStt;
	ResultSet spList;
	ResultSet spSelectedList; // update
	ResultSet rsPacksize;
	dbutils db;
	private String kl = "";
	private String dvkl = "";
	private String dvtt = "";
	private String tt = "";
	String machuan = "";
	String PacksizeId;
	String tenchuan = "";
	String nhanhangIds, chungloaiIds;
	String hamluong, visa, ngaycap, kkg, nsx, nkk, nxb, loaisp;

	public Thongtinsanpham(String[] param) {
		this.db = new dbutils();
		this.id = param[0];
		this.masp = param[1];
		this.ten = param[2];
		this.dvdlTen = param[3];
		this.dvkdTen = param[4];
		this.dvkdId = param[5];
		this.nhTen = param[6];
		this.nhId = param[7];
		this.clId = param[8];
		this.clTen = param[9];
		this.giablc = param[10];
		this.trangthai = param[11];
		this.dvdlId = param[12];
		this.ngaysua = param[13];
		this.nguoisua = param[14];
		this.sl1 = new String[5];
		this.sl2 = new String[5];
		this.dvdl1 = new String[5];
		this.dvdl2 = new String[5];
		this.type = param[15];
		this.msg = "";
		this.PacksizeId = "";
		this.machuan = "";
		this.Quydoithuong = "1";
		this.tenchuan = "";
		this.hansudung = "";
		this.chungloaiIds = "";
		this.nhanhangIds = "";
		this.dvdlETCId = "";
		this.nhomHangId = "";
		this.spchuluc = "";
		this.spmoi = "";
		this.maspcu = "";
		this.hamluong = "";
		this.visa = "";
		this.ngaycap = "";
		this.kkg = "";
		this.nsx = "";
		this.nkk = "";
		this.nxb = "";
		this.loaisp = "";
		this.trangthaiDL = "1";
	}

	public Thongtinsanpham() {
		this.db = new dbutils();
		this.id = "";
		this.masp = "";
		this.ten = "";
		this.dvdlTen = "";
		this.dvkdTen = "";
		this.dvkdId = "";
		this.nhTen = "";
		this.nhId = "";
		this.clId = "";
		this.clTen = "";
		this.giablc = "";
		this.trangthai = "1";
		this.dvdlId = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.type = "";
		this.nganhhangid = "";
		this.machuan = "";
		this.tenchuan = "";

		this.PacksizeId = "";
		this.sl1 = new String[5];
		this.sl2 = new String[5];
		this.dvdl1 = new String[5];
		this.dvdl2 = new String[5];
		this.Quydoithuong = "1";
		this.hansudung = "";
		this.chungloaiIds = "";
		this.nhanhangIds = "";
		this.dvdlETCId = "";
		this.nhomHangId = "";
		this.spchuluc = "";
		this.spmoi = "";
		this.maspcu = "";
		this.hamluong = "";
		this.visa = "";
		this.ngaycap = "";
		this.kkg = "";
		this.nsx = "";
		this.nkk = "";
		this.nxb = "";
		this.loaisp = "";
		this.trangthaiDL = "1";
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMasp() {
		return this.masp;
	}

	public void setMasp(String masp) {
		this.masp = masp;
	}

	public String getTen() {
		return this.ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getDvdlId() {
		return this.dvdlId;
	}

	public void setDvdlId(String dvdlId) {
		this.dvdlId = dvdlId;
	}

	public String getDvdlTen() {
		return this.dvdlTen;
	}

	public void setDvdlTen(String dvdlTen) {
		this.dvdlTen = dvdlTen;
	}

	public ResultSet getDvdl() {
		return this.dvdl;
	}

	public void setDvdl(ResultSet dvdl) {
		this.dvdl = dvdl;
	}

	public String getDvkdId() {
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) {
		this.dvkdId = dvkdId;
	}

	public String getDvkdTen() {
		return this.dvkdTen;
	}

	public void setDvkdTen(String dvkdTen) {
		this.dvkdTen = dvkdTen;
	}

	public String getNhId() {
		return this.nhId;
	}

	public void setNhId(String nhId) {
		this.nhId = nhId;
	}

	public String getNhTen() {
		return this.nhTen;
	}

	public void setNhTen(String nhTen) {
		this.nhTen = nhTen;
	}

	public String getClId() {
		return this.clId;
	}

	public void setClId(String clId) {
		this.clId = clId;
	}

	public String getClTen() {
		return this.clTen;
	}

	public void setClTen(String clTen) {
		this.clTen = clTen;
	}

	public String getNgaytao() {
		return this.ngaytao;

	}

	public void setNgaytao(String ngaytao) {
		this.ngaytao = ngaytao;
	}

	public String getNgaysua() {
		return this.ngaysua;

	}

	public void setNgaysua(String ngaysua) {
		this.ngaysua = ngaysua;
	}

	public String getNguoitao() {
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) {
		this.nguoitao = nguoitao;
	}

	public String getNguoisua() {
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) {
		this.nguoisua = nguoisua;
	}

	public String getMessage() {
		return this.msg;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}

	public void setDvkd(ResultSet dvkd) {
		this.dvkd = dvkd;
	}

	public ResultSet getDvkd() {
		return this.dvkd;
	}

	public void setNh(ResultSet nh) {
		this.nh = nh;
	}

	public ResultSet getNh() {
		return this.nh;
	}

	public void setCl(ResultSet cl) {
		this.cl = cl;
	}

	public ResultSet getCl() {
		return this.cl;
	}

	public void setGiablc(String giablc) {
		this.giablc = giablc;
	}

	public String getGiablc() {
		return this.giablc;
	}

	public void setNsp(ResultSet nsp) {
		this.nsp = nsp;
	}

	public ResultSet getNsp() {
		return this.nsp;
	}

	public Hashtable<Integer, String> getNspIds() {
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if (this.nspIds != null) {
			int size = (this.nspIds).length;
			int m = 0;
			while (m < size) {
				selected.put(new Integer(m), this.nspIds[m]);
				m++;
			}
		} else {
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setNspIds(String[] nspIds) {
		this.nspIds = nspIds;
	}

	public String[] getSl1() {
		return this.sl1;
	}

	public void setSl1(String[] sl1) {
		this.sl1 = sl1;
	}

	public String[] getDvdl1() {
		return this.dvdl1;
	}

	public void setDvdl1(String[] dvdl1) {
		this.dvdl1 = dvdl1;
	}

	public String[] getSl2() {
		return this.sl2;
	}

	public void setSl2(String[] sl2) {
		this.sl2 = sl2;
	}

	public String[] getDvdl2() {
		return this.dvdl2;
	}

	public void setDvdl2(String[] dvdl2) {
		this.dvdl2 = dvdl2;
	}

	public ResultSet createDvdlRS() {
		ResultSet dvdlRS = this.db
				.getScrol("select pk_seq as dvdlId, donvi as dvdlTen from donvidoluong where trangthai='1' order by donvi");
		return dvdlRS;
	}

	public ResultSet createLoaispRS() {
		ResultSet loaispRS = this.db
				.getScrol("select pk_seq, ten from erp_loaisanpham where trangthai = 1");
		return loaispRS;
	}

	private ResultSet createDvkdRS() {
		ResultSet dvkdRS = this.db
				.get("select distinct(a.pk_seq) as dvkdId, a.donvikinhdoanh as dvkdTen from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");
		return dvkdRS;
	}

	private ResultSet createNhRS() {
		ResultSet nhRS;
		if (dvkdId.length() > 0) 
		{
			nhRS = this.db.getScrol("select pk_seq as nhId, ten as nhTen from nhanhang where trangthai='1' and dvkd_fk='"
							+ this.dvkdId + "'");
			System.out.println("A "+"select pk_seq as nhId, ten as nhTen from nhanhang where trangthai='1' and dvkd_fk='"
					+ this.dvkdId + "'");
		} else 
		{
			nhRS = this.db.getScrol("select pk_seq as nhId, ten as nhTen from nhanhang where trangthai='1'");
			System.out.println("select pk_seq as nhId, ten as nhTen from nhanhang where trangthai='1'");
		}
		return nhRS;

	}

	private ResultSet createClRS() {

		String query = "select distinct a.pk_seq as clId, a.ten as clTen from chungloai a, nhanhang_chungloai b where trangthai='1' and a.pk_seq = b.cl_fk ";

		if (this.nhId.length() > 0)
		{
			query = query + "  and b.nh_fk = '" + this.nhId + "'";

		}
		System.out.println("Tao chung loai "+query);
		return this.db.getScrol(query);
	}

	private void createNspRS() {

		if (this.id.length() > 0) {
			this.nspSelected = this.db
					.get("select a.nsp_fk as nspId, b.TEN as nspTen, b.diengiai as diengiai from nhomsanpham_sanpham a inner join nhomsanpham b on a.nsp_fk = b.pk_seq where a.sp_fk = '"
							+ this.id + "'");
			String query = "select pk_seq as nspId, ten as nspTen, diengiai as diengiai from nhomsanpham where trangthai='1' and type = '0' order by ten";
			this.nsp = this.db.get(query);
			System.out.println("khoi tao nsp :" + query);
		} else {
			this.nsp = this.db
					.get("select pk_seq as nspId, ten as nspTen, diengiai as diengiai from nhomsanpham where trangthai='1' and type = '0' order by ten");
		}
	}

	private void createSpIds() {
		ResultSet rs = db
				.get("select sanpham_fk from Bundle_Sanpham where bundle_fk = '"
						+ this.id + "'");
		if (rs != null) {
			try {
				String str = "";
				while (rs.next()) {
					str = str + rs.getString("sanpham_fk") + ",";
				}
				if (str.length() > 0) {
					str = str.substring(0, str.length() - 1);
					this.spIds = str.split(",");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void createNspIds() {
		ResultSet rs = db
				.get("select nsp_fk from nhomsanpham_sanpham where sp_fk = '"
						+ this.id + "'");
		if (rs != null) {
			try {
				String str = "";
				while (rs.next()) {
					str = str + rs.getString("nsp_fk") + ",";
				}
				if (str.length() > 0) {
					str = str.substring(0, str.length() - 1);
					this.nspIds = str.split(",");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void CreateRS() {
		this.dvdl = createDvdlRS();
		this.loaispRs = createLoaispRS();
		this.dvkd = createDvkdRS();
		this.nh = createNhRS();
		this.cl = createClRS();
		String query = "select pk_Seq,ten from packsize";
		this.rsPacksize = db.get(query);
		query = "select pk_seq,ten,diengiai from nganhhang";
		this.RsNganhHang = db.get(query);

		this.createNspRS();

		this.createSpList();

		query = "select pk_seq,ma,ten from nhomhang order by ten";
		this.nhomHangRs = this.db.get(query);

	}

	boolean masanpham() {
		String sql;
		if (this.id.length() == 0) {
			sql = "select count(*) as num from sanpham where ma ='" + this.masp
					+ "'";
		} else {
			sql = "select count(*) as num from sanpham where pk_seq <> '"
					+ this.id + "' and ma ='" + this.masp + "'";
		}
		System.out.println("thong tin sp:" + sql);
		ResultSet rs = db.get(sql);
		if (rs != null)
			try {
				rs.next();
				if (rs.getString("num").equals("0"))
					return false;
			} catch (SQLException e) {
				return false;
			}
		return true;
	}

	public boolean UpdateSp() {
		try {
			// if(masanpham())
			// {
			// this.msg ="Mã sản phẩm đã trùng rồi";
			// return false;
			// }

			this.db.getConnection().setAutoCommit(false);
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;

			String cloai = this.clId.length() > 0 ? this.clId : "null";
			String nhanhang = this.nhId.length() > 0 ? this.nhId : "null";
			if (PacksizeId.equals("")) {
				this.PacksizeId = null;
			}

			String dvdl_etc_fk = this.dvdlETCId.trim().length() <= 0 ? "NULL"
					: this.dvdlETCId;
			String tenxuathoadon = "";
			if (this.tenxuathoadonArr != null) {
				for (int i = 0; i < this.tenxuathoadonArr.length; i++) {
					tenxuathoadon += this.tenxuathoadonArr[i] + "__";
				}
				if (tenxuathoadon.trim().length() > 0) {
					this.tenxuathoadon = tenxuathoadon;
				}
			}
			String nguongocnhapkhau = "";
			if (this.nguongocnhapkhauArr != null) {
				for (int i = 0; i < this.nguongocnhapkhauArr.length; i++) {
					nguongocnhapkhau += this.nguongocnhapkhauArr[i] + "__";
				}
				if (nguongocnhapkhau.trim().length() > 0) {
					this.nguongocnhapkhau = nguongocnhapkhau;
				}
			}
			
			String command = "update sanpham set DVDL_ETC_FK = " + dvdl_etc_fk
					+ ",  HanSuDung=" + (this.hansudung.equals("") ? null : this.hansudung) + ",tenchuan=N'"
					+ this.tenchuan + "',quydoithungthuong="
					+ this.Quydoithuong + ",NGANHHANG_FK="
					+ (this.nganhhangid.equals("") ? null : this.nganhhangid)
					+ ", machuan='" + this.machuan + "', packsize_fk="
					+ this.PacksizeId + ", ten = N'" + this.ten
					+ "', ngaysua = '" + this.ngaysua + "', nguoisua = '"
					+ this.userId + "', dvdl_fk = '" + this.dvdlId
					+ "', trangthai = '" + this.trangthai + "', trangthaiDAILY = '" + this.trangthaiDL + "', dvkd_fk = '"
					+ (this.dvkdId.equals("") ? null : this.dvkdId) + "', nhanhang_fk = " + (nhanhang.equals("") ? null : nhanhang)  
					+ ", chungloai_fk=" + (cloai.equals("") ? null : cloai)    + ", type = '" + this.type
					+ "', trongluong= " + (this.kl .equals("") ? null : this.kl ) + ", thetich= " + (this.tt.equals("") ? null : this.tt)
					+ " , spchuluc='" + this.spchuluc + "',spmoi='"
					+ this.spmoi + "', ma_fast = '" + this.maspcu
					+ "', hamluong = N'" + this.hamluong
					+ "', tenxuathoadon = N'" + this.tenxuathoadon
					+ "', nguongocnhapkhau = N'" + this.nguongocnhapkhau
					+ "', loaisanpham_fk = "+(this.loaisp .equals("") ? null : this.loaisp )+ ", visa = '"
					+ this.visa + "', ngaycap = '" + this.ngaycap
					+ "', kkg = '" + this.kkg + "', nsx = '" + this.nsx
					+ "', nxb='" + this.nxb + "', nkk='" + this.nkk
					+"' , tenhoatchat = N'"+this.tenhoatchat+"' , dangtrinhbay = N'"+this.dangtrinhbay+"', quycachdonggoi = N'"+this.quycachdonggoi+"', tieuchuanchatluong = N'"+this.tieuchuanchatluong+"', nguongocnguyenlieu = N'"+this.nguongocnguyenlieu+"', tieuchuannsx = N'"+this.tieuchuannsx+"', nuocsx = N'"+this.nuocsx+"', giabancothue = '"+this.giabancothue+"', tenviettat = '"+this.tenviettat+"', "
					+ " giatheodvtnhonhat = N'"+this.giakktheodvt+"' , stttheott40 = N'"+this.stttheoTT40+"', nhomsphtotc = N'"+this.nhomsphtotc+"', nguongoc = N'"+this.nguongoc+"', thuexuat = N'"+this.thuesuat+"', hethanvisa = N'"+this.hethanvisa+"' "
					+ " where pk_seq = '" + this.id + "'";
			System.out.println("UPDATE SANPHAM" + command);
			if (!this.db.update(command)) {
				this.msg = command;
				this.db.update("rollback");
				return false;
			}

		/*	command = "update erp_sanpham set DVDL_ETC_FK = " + dvdl_etc_fk
					+ ",  HanSuDung= " +(this.hansudung.equals("") ? null : this.hansudung)+ ",tenchuan=N'"
					+ this.tenchuan + "',quydoithungthuong="
					+ this.Quydoithuong + ",NGANHHANG_FK="
					+ (this.nganhhangid.equals("") ? null : this.nganhhangid)
					+ ", machuan='" + this.machuan + "', packsize_fk="
					+ this.PacksizeId + ", ten = N'" + this.ten
					+ "', ngaysua = '" + this.ngaysua + "', nguoisua = '"
					+ this.userId + "', dvdl_fk = '" + this.dvdlId
					+ "', trangthai = '" + this.trangthai + "', dvkd_fk = '"
					+ (this.dvkdId.equals("") ? null : this.dvkdId) + "', nhanhang_fk = " + (nhanhang.equals("") ? null : nhanhang) 
					+ ", chungloai_fk=" +(cloai.equals("") ? null : cloai)   + ", type = '" + this.type
					+ "', trongluong= " + (this.kl .equals("") ? null : this.kl ) + ", thetich= " + (this.tt.equals("") ? null : this.tt)
					+ " , spchuluc='" + this.spchuluc + "',spmoi='"
					+ this.spmoi + "', ma_fast = '" + this.maspcu
					+ "', hamluong = N'" + this.hamluong
					+ "', tenxuathoadon = N'" + this.tenxuathoadon
					+ "', nguongocnhapkhau = N'" + this.nguongocnhapkhau
					+ "', loaisanpham_fk = " + (this.loaisp .equals("") ? null : this.loaisp ) + ", visa = '"
					+ this.visa + "', ngaycap = '" + this.ngaycap
					+ "', kkg = '" + this.kkg + "', nsx = '" + this.nsx
					+ "', nxb='" + this.nxb + "', nkk='" + this.nkk
					+"' , tenhoatchat = N'"+this.tenhoatchat+"' , dangtrinhbay = N'"+this.dangtrinhbay+"', quycachdonggoi = N'"+this.quycachdonggoi+"', tieuchuanchatluong = N'"+this.tieuchuanchatluong+"', nguongocnguyenlieu = N'"+this.nguongocnguyenlieu+"', tieuchuannsx = N'"+this.tieuchuannsx+"', nuocsx = N'"+this.nuocsx+"', giabancothue = '"+this.giabancothue+"',tenviettat = '"+this.tenviettat+"', "
					+ " giatheodvtnhonhat = N'"+this.giakktheodvt+"' , stttheott40 = N'"+this.stttheoTT40+"', nhomsphtotc = N'"+this.nhomsphtotc+"', nguongoc = N'"+this.nguongoc+"', thuexuat = N'"+this.thuesuat+"', hethanvisa = N'"+this.hethanvisa+"' "
					+ " where pk_seq = '" + this.id + "'";
			System.out.println("UPDATE SANPHAM" + command);
			if (!this.db.update(command)) {
				this.msg = command;
				this.db.update("rollback");
				return false;
			}
			*/
			command = "delete from sanpham_chitiet where sp_fk ='" + this.id
					+ "'";

			System.out.println("UPDATE SANPHAM" + command);
			if (!this.db.update(command)) {
				this.msg = command;
				this.db.update("rollback");
				return false;
			}

			if (this.ngayArr != null) {
				System.out.println("vao roi!!!!!!!!!!");
				for (int i = 0; i < this.ngayArr.length; i++) {
					if (this.thongtinArr[i].trim().length() > 0) {
						command = "insert sanpham_chitiet(sp_FK, ngay, thongtin, ghichu) "
								+ "values( '"
								+ this.id
								+ "', N'"
								+ this.ngayArr[i].trim()
								+ "', '"
								+ this.thongtinArr[i].trim()
								+ "', '"
								+ this.ghichuArr[i].trim() + "' ) ";

						// System.out.println("1.Insert HD - CK: " + query);
						if (!db.update(command)) {
							this.msg = "Khong the tao moi ERP_DONDATHANG_CHIETKHAU: "
									+ command;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
//			command = "delete from BANGGIABLC_SANPHAM where sanpham_fk ='"
//					+ this.id + "'";
//
//			if (!this.db.update(command)) {
//				this.msg = command;
//				this.db.update("rollback");
//				return false;
//			}
//
//			String idbangialc = "";
//			command = "Select Pk_seq as id  from BANGGIABANLECHUAN  ";
//			ResultSet rsid = db.get(command);
//			if(rsid.next())
//			{
//				idbangialc = rsid.getString("id");
//			}
//			if (rsid != null)
//				rsid.close();
//			command = "insert into BANGGIABLC_SANPHAM (sanpham_fk,BGBLC_FK,Giabanlechuan)"
//					+ " values ("+this.id+","+idbangialc+","+this.giablc+")";
//			if(!db.update(command))
//			{
//				this.msg = "Loi :" + command;
//				this.db.getConnection().rollback();
//				return false;
//			}
			command = "delete from nhomsanpham_sanpham where sp_fk ='"
					+ this.id + "'";
			System.out.println("UPDATE SANPHAM" + command);
			if (!this.db.update(command)) {
				this.msg = command;
				this.db.update("rollback");
				return false;
			}

			if (!(this.nspIds == null)) {
				int size = nspIds.length;
				for (int i = 0; i < size; i++) {
					if (this.nspIds[i] != null) {
						command = "insert into nhomsanpham_sanpham(sp_fk, nsp_fk) values('"
								+ this.id + "', '" + nspIds[i] + "')";
						 System.out.println(command);
						if (!this.db.update(command)) {
							this.msg = command;
							this.db.update("rollback");
							return false;
						}
					}
				}

			}

			if (this.type.equals("1")) {
				db.update("delete from Bundle_Sanpham where bundle_fk='"+ this.id + "'");
				
				System.out.println("UPDATE SANPHAM: " + "delete from Bundle_Sanpham where bundle_fk='"+ this.id + "'");
				
				if (!this.db.update(command)) {
					this.msg = "Loi khi xoa Bundle_Sanpham....";
					this.db.update("rollback");
					return false;
				}

				if (this.spIds != null) {
					for (int i = 0; i < this.spIds.length; i++) {
						if (this.spIds[i] != null) {
							String[] arr = this.spIds[i].split("-");
							command = "insert into Bundle_Sanpham(bundle_fk, sanpham_fk, soluong) values('"
									+ this.id
									+ "','"
									+ arr[0]
									+ "', '"
									+ arr[1] + "')";
							System.out.println("Cau lenh insert bundle: "
									+ command + "\n");
							if (!this.db.update(command)) {
								this.msg = "Loi: " + command;
								this.db.update("rollback");
								return false;
							}
						}
					}
				}
			}

			for (int i = 0; i < 5; i++) {
				if ((sl1[i].length() > 0) && (dvdl1[i].length() > 0)
						&& (sl2[i].length() > 0) && (dvdl2[i].length() > 0)) {
					command = "select count(*) as sodong from  quycach where sanpham_fk='"
							+ this.id
							+ "' and dvdl2_fk='"
							+ dvdl2[i]
							+ "' and dvdl1_fk='"
							+ dvdl1[i]
							+ "' and ( soluong1!='"
							+ sl1[i]
							+ "'  or soluong2!='" + sl2[i] + "' ) ";
					System.out.println("[PhatSinhDuLieu]" + command);
					ResultSet rs = db.get(command);
					int sodong = 0;
					while (rs.next()) {
						sodong = rs.getInt("sodong");
					}
					System.out.println("______SoDong___" + sodong);
					if (rs != null)
						rs.close();

					if (sodong > 0) {
						String query = "	select c.DONVI as spDonVi,d.TEN as spTEN,COUNT(*) as SoDong,N'Đơn đặt hàng' as NghiepVu  "
								+ "	from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM b on b.dondathang_fk=a.PK_SEQ  "
								+ "		inner join DONVIDOLUONG c on c.PK_SEQ=b.dvdl_fk  "
								+ "		inner join SANPHAM d on d.PK_SEQ=b.sanpham_fk  "
								+ "	where  b.soluong!=0 and b.dvdl_fk='"
								+ dvdl2[i]
								+ "' and b.sanpham_fk='"
								+ this.id
								+ "'  "
								+ "	group by c.DONVI,d.TEN "
								+ "	union all "
								+ "	select c.DONVI as spDonVi,d.TEN as spTEN,COUNT(*) as SoDong,N'Đơn đặt hàng' as NghiepVu  "
								+ "	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on b.dondathang_fk=a.PK_SEQ  "
								+ "		inner join DONVIDOLUONG c on c.PK_SEQ=b.dvdl_fk   "
								+ "		inner join SANPHAM d on d.PK_SEQ=b.sanpham_fk "
								+ "  where  b.soluong!=0 and b.dvdl_fk='"
								+ dvdl2[i]
								+ "' and b.sanpham_fk='"
								+ this.id
								+ "'  "
								+ "	group by c.DONVI,d.TEN "
								+ "	union all  "
								+ "	select c.DONVI as spDonVi,d.TEN as spTEN,COUNT(*) as SoDong,N'Xuất chuyển nội bộ' as NghiepVu  "
								+ "	from ERP_CHUYENKHO a inner join ERP_CHUYENKHO_SANPHAM b on b.chuyenkho_fk=a.PK_SEQ  "
								+ "		inner join DONVIDOLUONG c on c.PK_SEQ=b.dvdl_fk  "
								+ "		inner join SANPHAM d on d.PK_SEQ=b.sanpham_fk  "
								+ "	where b.soluongchuyen!=0 and b.dvdl_fk='"
								+ dvdl2[i]
								+ "' and b.sanpham_fk='"
								+ this.id
								+ "' "
								+ "	group by c.DONVI,d.TEN  "
								+ "	union all   "
								+ "	select c.DONVI as spDonVi,d.TEN as spTEN,COUNT(*) as SoDong,N'Xuất chuyển nội bộ' as NghiepVu  "
								+ "	from ERP_CHUYENKHONPP a inner join ERP_CHUYENKHONPP_SANPHAM b on b.chuyenkho_fk=a.PK_SEQ  "
								+ "		inner join DONVIDOLUONG c on c.PK_SEQ=b.dvdl_fk  "
								+ "		inner join SANPHAM d on d.PK_SEQ=b.sanpham_fk  "
								+ "	where b.soluongchuyen!=0 and b.dvdl_fk='"
								+ dvdl2[i]
								+ "' and b.sanpham_fk='"
								+ this.id
								+ "' "
								+ "	group by c.DONVI,d.TEN  "
								+ "	union all  "
								+ "	select c.DONVI as spDonVi,d.TEN as spTEN,COUNT(*) as SoDong,N'Chuyển Kênh' as NghiepVu  "
								+ "	from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM b on b.chuyenkenh_fk=a.PK_SEQ   "
								+ "		inner join DONVIDOLUONG c on c.PK_SEQ=b.dvdl_fk   "
								+ "		inner join SANPHAM d on d.PK_SEQ=b.sanpham_fk  "
								+ "	where b.soluongchuyen!=0 and b.dvdl_fk='"
								+ dvdl2[i]
								+ "' and b.sanpham_fk='"
								+ this.id
								+ "' " + "	group by c.DONVI,d.TEN  ";

						System.out.println("Kiem tra nghiep vu phat sinh"
								+ query);

						msg = "";
						rs = db.get(query);
						while (rs.next()) {
							msg += "(" + rs.getString("NghiepVu") + ")\n";
						}
						if (rs != null)
							rs.close();

						if (msg.length() > 0) {
							this.msg = "Sản phẩm đã phát sinh dữ liệu nên không thể thay đổi quy cách  "
									+ msg;
							this.db.getConnection().rollback();
							return false;
						}
					}
					command = "delete quycach where sanpham_fk='" + this.id
							+ "' and dvdl2_fk='" + dvdl2[i]
							+ "' and dvdl1_fk='" + dvdl1[i] + "'   ";
					
					System.out.println("UPDATE SANPHAM" + command);
					if (!this.db.update(command)) {
						this.msg = "Loi: " + command;
						this.db.update("rollback");
						return false;
					}
					command = "insert into quycach(sanpham_fk, dvdl1_fk, soluong1, dvdl2_fk, soluong2) "
							+ "values('"
							+ this.id
							+ "','"
							+ dvdl1[i]
							+ "','"
							+ sl1[i] + "','" + dvdl2[i] + "','" + sl2[i] + "')";
					System.out.println("--CHEN QUY CACH: " + command);
					if (!(this.db.update(command))) {
						this.msg = "Loi insert: " + command;
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
			/*************************************** Ghi lai log sua thong tin san pham ***********************/
		/*	command = "INSERT INTO LOG_SANPHAM(SANPHAM_FK,NGUOISUA,GIA,SOLUONG1,SOLUONG2,DVDL) "
					+ "SELECT SP.PK_SEQ AS SPID,SP.NGUOISUA,BG.GIABANLECHUAN,QC.SOLUONG1,QC.SOLUONG2,DVDL.PK_SEQ AS DVDL_FK "
					+ "FROM SANPHAM SP INsNER JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ "
					+ "INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=QC.DVDL1_FK  "
					+ "INNER JOIN BANGGIABLC_SANPHAM BG ON BG.SANPHAM_FK=SP.PK_SEQ "
					+ "LEFT JOIN LOG_SANPHAM LOGSP ON LOGSP.SANPHAM_FK=SP.PK_SEQ AND LOGSP.DVDL=DVDL.PK_SEQ "
					+ "WHERE SP.PK_SEQ="
					+ this.id
					+ " AND ( ISNULL(LOGSP.SOLUONG1,0) !=ISNULL(QC.SOLUONG1,0)  OR ISNULL(LOGSP.GIA,0) !=ISNULL(BG.GIABANLECHUAN,0)) ";
			System.out.print("[LOG_SANPHAM]" + command);
			db.update(command);*/

			/*
			 * Chen nhung san pham chua co trong kho. Theo don vi kinh doanh va
			 * kenh ban hang cua npp
			 */
//			String query = "	insert into nhapp_kho(npp_fk,kbh_fk,KHO_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE)   "
//					+ "	select  npp.PK_SEQ, kenh.PK_SEQ as kbhId ,kho.PK_SEQ as khoId,sp.PK_SEQ as spId,0 as SoLuong,0 as Booked,0 as avail  from KHO kho,SANPHAM sp,KENHBANHANG kenh ,NHAPHANPHOI npp "
//					+ "	where not exists "
//					+ "	( 	 "
//					+ "		select * from  NHAPP_KHO a 	 "
//					+ "		where a.KHO_FK=kho.PK_SEQ and a.KBH_FK=kenh.PK_SEQ 	 "
//					+ "		and a.SANPHAM_FK=sp.PK_SEQ and a.npp_fk =npp.pk_Seq   "
//					+ "	) and  sp.DVKD_FK in ("
//					+ this.dvkdId
//					+ ") "
//					+ "	and sp.pk_Seq=" + this.id + " ";
//			if (!db.update(query)) {
//				this.msg = "Có lỗi phát sinh trong quá trình cập nhật " + query;
//				db.update("rollback");
//				return false;
//			}
			String query = "	insert into nhapp_kho(npp_fk,nhomkenh_fk,KHO_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE)   "+
					"select  npp.PK_SEQ, kenh.PK_SEQ as kbhId, kho.PK_SEQ as khoId, sp.PK_SEQ as spId, 0 as SoLuong, 0 as Booked, 0 as avail " + 
					"from KHO kho, SANPHAM sp, NHOMKENH kenh, NHAPHANPHOI npp "+  
					"where not exists "+  
					"	( 	 "+
					"		select * from  NHAPP_KHO a 	 "+
					"		where a.KHO_FK = kho.PK_SEQ and a.NHOMKENH_FK = kenh.PK_SEQ 	 "+
					"		and a.SANPHAM_FK = sp.PK_SEQ and a.npp_fk = npp.pk_Seq   "+
					"	)  "+ 
					"	and sp.pk_Seq=" + this.id + " ";
			
			 System.out.println("UPDATE SANPHAM" + query);
					if (!db.update(query)) 
					{
						this.msg = "Có lỗi phát sinh trong quá trình cập nhật " + query;
						db.update("rollback");
						return false;
					}
			 query = "UPDATE SP SET  TIMKIEM = "
					+ "	upper(dbo.ftBoDau(sp.MA)) +   '-' +upper(dbo.ftBoDau(isnull(SP.BARCODE,''))) +'-'+ upper(dbo.ftBoDau(isnull(SP.ten,''))) "
					+ "	+ '-' +upper(dbo.ftBoDau(isnull(DVDL.DONVI,'')))  + '-' +upper(dbo.ftBoDau(isnull(SP.ma_fast,'')))      "
					+ " FROM SANPHAM SP INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK "
					+ " where SP.PK_SEQ='" + this.id + "' ";
			 System.out.println("UPDATE SANPHAM" + query);
			if (!db.update(query)) {
				this.msg = query;
				this.db.update("rollback");
				return false;
			}
			query = "INSERT INTO ERP_KHOTT_SANPHAM(KHOTT_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,GIATON) "
					+ "  SELECT PK_SEQ AS KHOID,'"
					+ this.id
					+ "' AS SPID,0 AS SL,0 AS BOOKED,0 AS AVAI,0 AS GIA "
					+ " FROM ERP_KHOTT KHO "
					+ " WHERE NOT EXISTS ( SELECT * FROM ERP_KHOTT_SANPHAM WHERE SANPHAM_FK='"
					+ this.id + "' AND KHO.PK_SEQ=KHOTT_FK) ";
			System.out.println("UPDATE SANPHAM" + command);
			if (!db.update(query)) {
				this.msg = query;
				this.db.update("rollback");
				return false;
			}

			query = "delete from nhomhang_sanpham where sanpham_fk='" + this.id
					+ "'";
			System.out.println(":::" + query);
			if (!db.update(query)) {
				this.msg = query;
				this.db.update("rollback");
				return false;
			}

			query = " INSERT INTO ERP_KHOTT_SP_CHITIET(KHOTT_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,SOLO,NGAYHETHAN,NGAYSANXUAT)  "
					+ "  SELECT KHO.PK_SEQ AS KHOID,SP.PK_SEQ AS SPID,0 AS SL,0 AS BOOKED,0 AS AVAI ,'NA','2030-12-31','2030-12-31'  "
					+ " FROM ERP_KHOTT KHO ,SANPHAM SP   "
					+ " WHERE NOT EXISTS ( SELECT * FROM ERP_KHOTT_SP_CHITIET WHERE SANPHAM_FK=SP.PK_SEQ AND KHO.PK_SEQ=KHOTT_FK AND SOLO='NA') ";
			 System.out.println("UPDATE SANPHAM" + query);
			if (!db.update(query)) {
				this.msg = query;
				this.db.update("rollback");
				return false;
			}

			if (this.nhomHangId.length() > 0) {
				query = "insert into nhomhang_sanpham(nhomhang_fk,sanpham_fk)"
						+ " select pk_seq,'" + this.id + "'  "
						+ " from NhomHang where pk_seq in (" + this.nhomHangId
						+ ")  ";
				System.out.println(":::" + query);
				if (!this.db.update(query)) {
					this.msg = "Lỗi hệ thống " + query;
					return false;
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(false);
		}

		catch (Exception e) {
			e.printStackTrace();
			this.db.update("rollback");
			this.msg = "Vui Long Kiem Tra Lai Du Lieu .Xay Ra Loi Sau :"
					+ e.toString();
			return false;
		}
		return true;
	}

	public boolean CreateSp() 
	{
		String command = "";

		try 
		{
			// if(masanpham())
			// {
			// this.msg = "Ma san pham da trung roi";
			// return false;
			// }

			this.db.getConnection().setAutoCommit(false);
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;

			String cloai = this.clId.length() > 0 ? this.clId : "null";
			String nhanhang = this.nhId.length() > 0 ? this.nhId : "null";
			if (this.PacksizeId.equals("")) {
				this.PacksizeId = null;
			}
			String dvdl_etc_fk = this.dvdlETCId.trim().length() <= 0 ? "NULL"
					: this.dvdlETCId;

			String tenxuathoadon = "";
			if (this.tenxuathoadonArr != null) {
				for (int i = 0; i < this.tenxuathoadonArr.length; i++) {
					tenxuathoadon += this.tenxuathoadonArr[i] + "__";
				}
				if (tenxuathoadon.trim().length() > 0) {
					this.tenxuathoadon = tenxuathoadon;
				}
			}
			String nguongocnhapkhau = "";
			if (this.nguongocnhapkhauArr != null) {
				for (int i = 0; i < this.nguongocnhapkhauArr.length; i++) {
					nguongocnhapkhau += this.nguongocnhapkhauArr[i] + "__";
				}
				if (nguongocnhapkhau.trim().length() > 0) {
					this.nguongocnhapkhau = nguongocnhapkhau;
				}
			}

			command = "select top 1 ma from sanpham order by PK_SEQ desc";
			ResultSet rs = this.db.get(command);
			int ma=0;
			this.masp="";
			if(rs.next())
			{
				this.masp = rs.getString("ma");
			}
			if(this.masp.equals(""))
				this.masp="00000000";
			else
			{
				ma = Integer.parseInt(this.masp) + 1;
				this.masp = Integer.toString(ma);
			}
			String strma = "";
			for(int i = 0; i < 8-this.masp.length();i++)
				strma+="0";
			strma+=this.masp;
			this.masp = strma;
			System.out.println("!!!!!!!!!!masp " + this.masp);
		
			command = "insert into sanpham(DVDL_ETC_FK, hansudung,tenchuan,quydoithungthuong,nganhhang_fk,machuan,packsize_fk,ma,ten,ngaytao,ngaysua,nguoitao,nguoisua,dvdl_fk,trangthai, trangthaiDAILY, dvkd_fk,nhanhang_fk,chungloai_fk,type,trongluong,thetich,spchuluc,spmoi, ma_fast, hamluong, tenxuathoadon, nguongocnhapkhau,loaisanpham_fk,visa,ngaycap,kkg,nsx,nxb,nkk,tenhoatchat ,dangtrinhbay ,quycachdonggoi , tieuchuanchatluong ,nguongocnguyenlieu ,tieuchuannsx ,nuocsx ,giabancothue,tenviettat, giatheodvtnhonhat , stttheott40 , nhomsphtotc ,nguongoc , thuexuat , hethanvisa) "
					+ "  values("
					+ dvdl_etc_fk
					+ ",'"
					+ (this.hansudung.equals("") ? null : this.hansudung)
					+ "',N'"
					+ this.tenchuan
					+ "',"
					+ this.Quydoithuong
					+ ","
					+ (this.nganhhangid.equals("") ? null : this.nganhhangid)
					+ ",'"
					+ this.machuan
					+ "',"
					+ this.PacksizeId
					+ ",'"+this.masp+"', N'"
					+ this.ten
					+ "', '"
					+ this.ngaysua
					+ "','"
					+ this.ngaysua
					+ "','"
					+ this.userId
					+ "','"
					+ this.userId
					+ "', '"
					+ this.dvdlId
					+ "','" + this.trangthai + "', '" + this.trangthaiDL + "', '"
					+ (this.dvkdId.equals("") ? null : this.dvkdId)  
					+ "',"
					+ (nhanhang.equals("") ? null : nhanhang)  
					+ ","
					+ (cloai.equals("") ? null : cloai)
					+ ",'"
					+ this.type
					+ "',"
					+  (this.kl.equals("") ? null : this.kl)
					+ ","
					+  (this.tt.equals("") ? null : this.tt)
					+ ",'"
					+ this.spchuluc
					+ "','"
					+ this.spmoi
					+ "', '"
					+ this.maspcu
					+ "', N'"
					+ this.hamluong
					+ "', N'"
					+ this.tenxuathoadon
					+ "', N'"
					+ this.nguongocnhapkhau
					+ "', "
					+ (this.loaisp.equals("") ? null : this.loaisp)
					+ ", '"
					+ this.visa
					+ "', '"
					+ this.ngaycap
					+ "','"
					+ this.kkg
					+ "', '"
					+ this.nsx
					+ "', '"
					+ this.nxb
					+ "','"
					+ this.nkk + "',N'"+this.tenhoatchat+"' , N'"+this.dangtrinhbay+"', N'"+this.quycachdonggoi+"' , N'"+this.tieuchuanchatluong+"' , N'"+this.nguongocnguyenlieu+"', N'"+this.tieuchuannsx+"', N'"+this.nuocsx+"' , N'"+this.giabancothue+"',N'"+this.tenviettat+"',N'"+this.giakktheodvt+"',N'"+this.stttheoTT40+"',N'"+this.nhomsphtotc+"',N'"+this.nguongoc+"',N'"+this.thuesuat+"',N'"+this.hethanvisa+"')";

			System.out.println("-----1.INSERT SANPHAM: " + command);
			if (!this.db.update(command)) {
				this.msg = "Loi :" + command;
				this.db.getConnection().rollback();
				return false;
			}
			
			command = "select IDENT_CURRENT('sanpham')as spId";
			rs = this.db.get(command);

			rs.next();
			this.id = rs.getString("spId");
			if (rs != null)
				rs.close();
			
			String idbangialc = "";
			command = "Select Pk_seq as id  from BANGGIABANLECHUAN  ";
			ResultSet rsid = db.get(command);
			if(rsid.next())
			{
				idbangialc = rsid.getString("id");
			}
			if (rsid != null)
				rsid.close();
//			command = "insert into BANGGIABLC_SANPHAM (sanpham_fk,BGBLC_FK,Giabanlechuan)"
//					+ " values ("+this.id+","+idbangialc+","+this.giablc+")";
//			if(!db.update(command))
//			{
//				this.msg = "Loi :" + command;
//				this.db.getConnection().rollback();
//				return false;
//			}
			
			
			if (rs != null)
				rs.close();

			// COPY 1 SAN PHAM CHO ERP
			/*command = "insert into erp_sanpham(pk_seq, DVDL_ETC_FK, hansudung,tenchuan,quydoithungthuong,nganhhang_fk,machuan,packsize_fk,ma,ten,ngaytao,ngaysua,nguoitao,nguoisua,dvdl_fk,trangthai,dvkd_fk,nhanhang_fk,chungloai_fk,type,trongluong,thetich,spchuluc,spmoi, ma_fast, hamluong, tenxuathoadon, nguongocnhapkhau, loaisanpham_fk,visa,ngaycap,kkg,nsx,nxb,nkk, tenhoatchat ,dangtrinhbay ,quycachdonggoi , tieuchuanchatluong ,nguongocnguyenlieu ,tieuchuannsx ,nuocsx ,giabancothue,tenviettat,giatheodvtnhonhat , stttheott40 , nhomsphtotc ,nguongoc , thuexuat , hethanvisa   ) "
					+ "  values('"
					+ this.id
					+ "', "
					+ dvdl_etc_fk
					+ ",'"
					+ (this.hansudung.equals("") ? null : this.hansudung)
					+ "',N'"
					+ this.tenchuan
					+ "',"
					+ this.Quydoithuong
					+ ","
					+ (this.nganhhangid.equals("") ? null : this.nganhhangid)
					+ ",'"
					+ this.machuan
					+ "',"
					+ this.PacksizeId
					+ ",'', N'"
					+ this.ten
					+ "', '"
					+ this.ngaysua
					+ "','"
					+ this.ngaysua
					+ "','"
					+ this.userId
					+ "','"
					+ this.userId
					+ "', '"
					+ this.dvdlId
					+ "','"
					+ this.trangthai
					+ "', '"
					+ (this.dvkdId.equals("") ? null : this.dvkdId)
					+ "',"
					+ (nhanhang.equals("") ? null : nhanhang)
					+ ","
					+ (cloai.equals("") ? null : cloai)
					+ ",'"
					+ this.type
					+ "',"
					+ (this.kl.equals("") ? null : this.kl)
					+ ","
					+ (this.tt.equals("") ? null :this.tt)
					+ ",'"
					+ this.spchuluc
					+ "','"
					+ this.spmoi
					+ "', '"
					+ this.maspcu
					+ "', N'"
					+ this.hamluong
					+ "', N'"
					+ this.tenxuathoadon
					+ "', N'"
					+ this.nguongocnhapkhau
					+ "', "
					+ (this.loaisp.equals("") ? null :this.loaisp)
					+ ", '"
					+ this.visa
					+ "', '"
					+ this.ngaycap
					+ "','"
					+ this.kkg
					+ "', '"
					+ this.nsx
					+ "', '"
					+ this.nxb
					+ "','"
					+ this.nkk + "' ,  N'"+this.tenhoatchat+"' , N'"+this.dangtrinhbay+"', N'"+this.quycachdonggoi+"' , N'"+this.tieuchuanchatluong+"' , N'"+this.nguongocnguyenlieu+"', N'"+this.tieuchuannsx+"', N'"+this.nuocsx+"' , N'"+this.giabancothue+"',N'"+this.tenviettat+"',N'"+this.giakktheodvt+"',N'"+this.stttheoTT40+"',N'"+this.nhomsphtotc+"',N'"+this.nguongoc+"',N'"+this.thuesuat+"',N'"+this.hethanvisa+"' )";

			System.out.println("-----1.INSERT SANPHAM: " + command);
			if (!this.db.update(command)) {
				this.msg = "Loi :" + command;
				this.db.getConnection().rollback();
				return false;
			}
			*/
			if (this.ngayArr != null) {
				for (int i = 0; i < this.ngayArr.length; i++) {
					if (this.thongtinArr[i].trim().length() > 0) {
						command = "insert sanpham_chitiet(sp_FK, ngay, thongtin, ghichu) "
								+ "values( '"
								+ this.id
								+ "', N'"
								+ this.ngayArr[i].trim()
								+ "', '"
								+ this.thongtinArr[i].trim()
								+ "', '"
								+ this.ghichuArr[i].trim() + "' ) ";

						// System.out.println("1.Insert HD - CK: " + query);
						if (!db.update(command)) {
							this.msg = "Khong the tao moi ERP_DONDATHANG_CHIETKHAU: "
									+ command;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}

			boolean error = true;
			for (int i = 0; i < 5; i++) {
				if (!(sl1[i] == null)) 
				{
					if ((sl1[i].length() > 0) & (dvdl1[i].length() > 0)
							& (sl2[i].length() > 0) & (dvdl2[i].length() > 0)) {
						command = "insert into quycach(sanpham_fk, dvdl1_fk, soluong1, dvdl2_fk, soluong2) "
								+ "	values('"
								+ this.id
								+ "','"
								+ this.dvdl1[i]
								+ "','"
								+ this.sl1[i]
								+ "','"
								+ this.dvdl2[i]
								+ "','" + this.sl2[i] + "')";

						System.out.println("-----2.INSERT SANPHAM: " + command);
						if (!(this.db.update(command))) {
							this.msg = "Loi :" + command;
							this.db.update("rollback");
							return false;
						}
					}
			
				}
				
			}

			if (error == false) {
				this.db.getConnection().rollback();
				this.msg = "Vui long nhap thong tin quy cach san pham";
				return false;
			}

			if (!(this.nspIds == null)) {
				int size = nspIds.length;
				for (int i = 0; i < size; i++) {
					if (this.nspIds[i] != null) {
						command = "insert into nhomsanpham_sanpham(sp_fk, nsp_fk) values('"
								+ this.id + "','" + nspIds[i] + "')";
						System.out.println("-----3.INSERT SANPHAM: " + command);

						if (!this.db.update(command)) {
							this.msg = "Loi :" + command;
							this.db.update("rollback");

							return false;
						}
					}
				}
			}

			if (this.type.equals("1")) {
				if (this.spIds != null) {
					for (int i = 0; i < this.spIds.length; i++) {
						if (this.spIds[i] != null) {
							String[] arr = this.spIds[i].split("-");
							command = "insert into Bundle_Sanpham(bundle_fk, sanpham_fk, soluong) values('"
									+ this.id
									+ "','"
									+ arr[0]
									+ "', '"
									+ arr[1] + "')";

							System.out.print("4.insett: " + command);

							if (!this.db.update(command)) {
								this.msg = "Loi :" + command;
								System.out.print("insert khong duoc: "
										+ command);
								this.db.update("rollback");

								return false;
							}

						}
					}
				}
			}

			// command =
			// "insert into bgmuanpp_sanpham ( BGMUANPP_FK, SANPHAM_FK, GIAMUANPP, TRANGTHAI, giamuanpp_tuvc ) "
			// +
//			command = "insert into bgmuanpp_sanpham ( BGMUANPP_FK, SANPHAM_FK, TRANGTHAI, giamuanpp_tuvc, GIAMUA_TRUOCCK, GIAMUA_SAUCK, CHIETKHAU ) "
//					+ "select pk_seq,'"
//					+ this.id
//					+ "', 0, '0', 0, 0, 0 from banggiamuanpp  ";
//			// System.out.println("-----5.INSERT SANPHAM: " + command);
//			if (!this.db.update(command)) {
//				this.msg = "Loi :" + command;
//				this.db.update("rollback");
//				return false;
//			}

//			command = "insert into BANGGIAMUA_SANPHAM ( BANGGIAMUA_FK, SANPHAM_FK, TRANGTHAI, GIAMUA ) "
//					+ "select pk_seq,'" + this.id + "', 0, 0 from banggiamua  ";
//			// System.out.println("-----5.INSERT SANPHAM: " + command);
//			if (!this.db.update(command)) {
//				this.msg = "Loi :" + command;
//				this.db.update("rollback");
//				return false;
//			}

//			rs = this.db
//					.get("select pk_seq from banggiasieuthi where dvkd_fk='"
//							+ this.dvkdId + "'");
//			while (rs.next()) {
//				command = "insert into banggiast_sanpham values('"
//						+ rs.getString("pk_seq") + "','" + this.id + "', '0')";
//				if (!this.db.update(command)) {
//					this.msg = "Loi :" + command;
//					this.db.update("rollback");
//					return false;
//				}
//			}
			if (rs != null)
				rs.close();

			command = "  insert into nhapp_kho(kho_fk,npp_fk,sanpham_fk,soluong,booked,available,giamua,nhomkenh_fk)  "
					+ " select distinct kho_fk, npp_fk,"
					+ this.id
					+ ",0,0,0,0, nhomkenh_fk "
					+ " from nhapp_kho where npp_fk in (select distinct a.pk_seq as nppId from nhaphanphoi a, nhapp_nhacc_donvikd b,  nhacungcap_dvkd c where a.pk_seq=b.npp_fk and b.ncc_dvkd_fk= c.pk_seq and c.dvkd_fk = '"
					+ this.dvkdId
					+ "') "
					+ " and nhomkenh_fk in (select pk_seq from nhomkenh where trangthai = '1')";
			System.out.println("-----6.INSERT SANPHAM: " + command);
			if (!this.db.update(command)) {
				this.msg = "Loi :" + command;
				this.db.update("rollback");
				return false;
			}

			// Them vao bangphanquyen sanpham
			command = "	insert into nhanvien_Sanpham(nhanvien_Fk,Sanpham_fk)  "
					+ "	select pk_Seq,'" + this.id + "'  as spId  "
					+ "	from nhanvien   "
					+ "	where PHANLOAI=2 and trangthai=1 ";
			System.out.println("-----7.INSERT SANPHAM: " + command);
			if (!db.update(command)) {
				this.msg = command;
				this.db.update("rollback");
				return false;
			}

			/*
			 * Chen nhung san pham chua co trong kho. Theo don vi kinh doanh va
			 * kenh ban hang cua npp
			 */
			System.out.println("id san pham moi tao "+this.id);
			String query = "	insert into nhapp_kho(npp_fk,nhomkenh_fk,KHO_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE)   "
					+ "	select  npp.PK_SEQ, kenh.PK_SEQ as kbhId ,kho.PK_SEQ as khoId,sp.PK_SEQ as spId,0 as SoLuong,0 as Booked,0 as avail  from KHO kho,SANPHAM sp,NHOMKENH kenh ,NHAPHANPHOI npp "
					+ "	where not exists "
					+ "	( 	 "
					+ "		select * from  NHAPP_KHO a 	 "
					+ "		where a.KHO_FK=kho.PK_SEQ and a.NHOMKENH_FK=kenh.PK_SEQ 	 "
					+ "		and a.SANPHAM_FK=sp.PK_SEQ and a.npp_fk =npp.pk_Seq   "
					+ "	) AND sp.DVKD_FK in ("
					+ this.dvkdId
					+ ") "
					+ "	and sp.pk_Seq=" + this.id + " ";
			if (!db.update(query)) {
				this.msg = "Có lỗi phát sinh trong quá trình cập nhật " + query;
				db.update("rollback");
				return false;
			}

			query = "UPDATE SP SET  TIMKIEM = "
					+ "	upper(dbo.ftBoDau(sp.MA)) +   '-' +upper(dbo.ftBoDau(isnull(SP.BARCODE,''))) +'-'+ upper(dbo.ftBoDau(isnull(SP.ten,''))) "
					+ "	+ '-' +upper(dbo.ftBoDau(isnull(DVDL.DONVI,'')))  + '-' +upper(dbo.ftBoDau(isnull(SP.ma_fast,'')))    "
					+ " FROM SANPHAM SP INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK "
					+ " where SP.PK_SEQ='" + this.id + "' ";

			if (!db.update(query)) {
				this.msg = query;
				this.db.update("rollback");
				return false;
			}
			
			
			query = "INSERT INTO ERP_KHOTT_SANPHAM(KHOTT_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,GIATON) "
					+ "  SELECT PK_SEQ AS KHOID,'"
					+ this.id
					+ "' AS SPID,0 AS SL,0 AS BOOKED,0 AS AVAI,0 AS GIA "
					+ " FROM ERP_KHOTT KHO "
					+ " WHERE NOT EXISTS ( SELECT * FROM ERP_KHOTT_SANPHAM WHERE SANPHAM_FK='"
					+ this.id + "' AND KHO.PK_SEQ=KHOTT_FK) ";
			if (!db.update(query)) {
				this.msg = query;
				this.db.update("rollback");
				return false;
			}

			query = " INSERT INTO ERP_KHOTT_SP_CHITIET(KHOTT_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,SOLO,NGAYHETHAN,NGAYSANXUAT)  "
					+ "  SELECT KHO.PK_SEQ AS KHOID,SP.PK_SEQ AS SPID,0 AS SL,0 AS BOOKED,0 AS AVAI ,'NA','2030-12-31','2030-12-31'  "
					+ " FROM ERP_KHOTT KHO ,SANPHAM SP   "
					+ " WHERE NOT EXISTS ( SELECT * FROM ERP_KHOTT_SP_CHITIET WHERE SANPHAM_FK=SP.PK_SEQ AND KHO.PK_SEQ=KHOTT_FK AND SOLO='NA') ";
			if (!db.update(query)) {
				this.msg = query;
				this.db.update("rollback");
				return false;
			}

			if (this.nhomHangId.length() > 0) {
				query = "insert into nhomhang_sanpham(nhomhang_fk,sanpham_fk)"
						+ " select pk_seq,'" + this.id + "'  "
						+ " from NhomHang where pk_seq in (" + this.nhomHangId
						+ ")  ";
				if (!this.db.update(query)) {
					this.msg = "Lỗi hệ thống " + query;
					return false;
				}
			}

			query = "		update Sp set TKVT=  "
					+ "		case  "
					+ "			when data.dauso IN ('3','5','6','9') then '156' else '155' end ,  "
					+ "	tkdt=	case  "
					+ "			when data.dauso IN ('3','5') then '51113' "
					+ "			when data.dauso IN ('6') then '51116'   "
					+ "			when data.dauso IN ('9') then '51119' else  '5112' end ,  "
					+ "	tkgv=	case  "
					+ "			when data.dauso IN ('3','5') then '63213'   "
					+ "			when data.dauso IN ('6') then '63216'   "
					+ "			when data.dauso IN ('9') then '63219' else  '6322' end ,  "
					+ "	TKCK=case   "
					+ "			when data.dauso IN ('3','5') then '51113'  "
					+ "			when data.dauso IN ('6') then '51116'   "
					+ "			when data.dauso IN ('9') then '51119' else  '5112' end  "
					+ "	from  " + "	(  "
					+ "		select PK_SEQ as spId,MA,SUBSTRING(ma,1,1) as DauSo  "
					+ "		from SANPHAM"
					+ "	)as data inner join SANPHAM sp on sp.PK_SEQ=data.spId "
					+ "  where sp.pk_seq='" + this.id + "'  ";
			if (!this.db.update(query)) {
				this.msg = "Lỗi hệ thống " + query;
				return false;
			}

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = e.getMessage();
			System.out.println("----EXCEPTION TAO MOI: " + e.getMessage());
			this.db.update("rollback");
			return false;

		}

		return true;
	}

	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void init() {
		try {
			String query = "select trangthaiDAILY, isnull(a.hansudung,'') as HanSuDung,isnull(tenchuan,'') as tenchuan ,isnull(quydoithungthuong,1) as quydoithungthuong ,isnull(nganhhang_fk,0) as nganhhang_fk,isnull(machuan,'') as machuan ,isnull(a.packsize_fk ,0) as packsize_fk,a.pk_seq as id, a.ma as masp, a.ten as tensp, b.donvikinhdoanh as dvkd, b.pk_seq as dvkdId, c.pk_seq as clId, c.ten as chungloai, e.pk_seq as nhId, d.donvi, e.ten as nhanhang, isnull(d.pk_seq,0) as dvdlId, a.trangthai,f.giabanlechuan as giablc, isnull(a.type,'' ) as type, a.trongluong, a.thetich, a.loaisanpham_fk, a.DVDL_ETC_FK,a.spmoi,a.spchuluc, a.ma_fast, a.hamluong, a.tenxuathoadon, a.nguongocnhapkhau, a.loaisanpham_fk, a.visa, a.ngaycap, a.kkg, a.nsx, a.nxb, a.nkk, isnull(a.tenhoatchat,'') as tenhoatchat  , isnull( a.dangtrinhbay, '') as dangtrinhbay , isnull( a.quycachdonggoi, '') as quycachdonggoi , isnull (a.tieuchuanchatluong, '') as tieuchuanchatluong  , isnull(a.nguongocnguyenlieu,'') as nguongocnguyenlieu ,isnull(a.tieuchuannsx, '') as tieuchuannsx , isnull(a.nuocsx,'') as nuocsx , isnull(a.giabancothue,'0') as  giabancothue "
					+", isnull(a.giatheodvtnhonhat,'') as giatheodvtnhonhat, isnull(a.stttheott40,'') as stttheott40,isnull(a.nhomsphtotc,'') as nhomsphtotc , isnull(a.nguongoc,'') as nguongoc, isnull(a.thuexuat,'') as thuexuat , isnull(a.hethanvisa,'') as hethanvisa ,isnull(a.tenviettat,'') as tenviettat   ";
			query = query
					+ " from sanpham a left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq left join chungloai c on a.chungloai_fk = c.pk_seq left join donvidoluong d on a.dvdl_fk = d.pk_seq left join nhanhang e on a.nhanhang_fk = e.pk_seq ";
			query = query
					+ " left join banggiablc_sanpham f on a.pk_seq = f.sanpham_fk left join banggiabanlechuan g on f.bgblc_fk = g.pk_seq ";

			query = query + " where a.pk_seq = '" + this.id + "'";
			System.out.print("\n Khoi tao sp update : " + query + "\n");
			ResultSet rs = this.db.get(query);

			rs.next();
			this.id = rs.getString("id");
			this.trangthaiDL = rs.getString("trangthaiDAILY");
			this.masp = rs.getString("masp");
			this.maspcu = rs.getString("ma_fast") == null ? "" : rs
					.getString("ma_fast");
			this.tenchuan = rs.getString("tenchuan");
			this.Quydoithuong = rs.getString("quydoithungthuong");
			this.ten = rs.getString("tensp");
			this.machuan = rs.getString("machuan");
			this.nganhhangid = rs.getString("nganhhang_fk");

			this.PacksizeId = rs.getString("packsize_fk");
			this.hansudung = rs.getString("HanSuDung");
			this.hamluong = rs.getString("hamluong") == null ? "" : rs
					.getString("hamluong");
			this.loaisp = rs.getString("loaisanpham_fk") == null ? "" : rs
					.getString("loaisanpham_fk");
			this.visa = rs.getString("visa") == null ? "" : rs
					.getString("visa");
			this.ngaycap = rs.getString("ngaycap") == null ? "" : rs
					.getString("ngaycap");
			this.kkg = rs.getString("kkg") == null ? "" : rs.getString("kkg");
			this.nsx = rs.getString("nsx") == null ? "" : rs.getString("nsx");
			this.nxb = rs.getString("nxb") == null ? "" : rs.getString("nxb");
			this.nkk = rs.getString("nkk") == null ? "" : rs.getString("nkk");
			
			this.tenhoatchat = rs.getString("tenhoatchat");
			this.dangtrinhbay = rs.getString("dangtrinhbay");
			this.quycachdonggoi = rs.getString("quycachdonggoi");
			this.tieuchuanchatluong = rs.getString("tieuchuanchatluong");
			
			this.nguongocnguyenlieu = rs.getString("nguongocnguyenlieu");
			this.tieuchuannsx = rs.getString("tieuchuannsx");
			this.nuocsx = rs.getString("nuocsx");
			this.giabancothue = rs.getString("giabancothue");
			this.giakktheodvt = rs.getString("giatheodvtnhonhat");
			this.stttheoTT40 = rs.getString("stttheott40");
			this.nhomsphtotc = rs.getString("nhomsphtotc");
			this.nguongoc = rs.getString("nguongoc");
			this.thuesuat = rs.getString("thuexuat");
			this.hethanvisa = rs.getString("hethanvisa");
			this.tenviettat = rs.getString("tenviettat");
			this.tenxuathoadon = rs.getString("tenxuathoadon") == null ? ""
					: rs.getString("tenxuathoadon");
			System.out.println("TENXUATHOADON " + this.tenxuathoadon);
			if (this.tenxuathoadon.trim().length() > 0) {
				this.tenxuathoadonArr = this.tenxuathoadon.split("__");
			}
			this.nguongocnhapkhau = rs.getString("nguongocnhapkhau") == null ? ""
					: rs.getString("nguongocnhapkhau");
			System.out.println("NGUONGOCNHAPKHAU " + this.nguongocnhapkhau);
			if (this.nguongocnhapkhau.trim().length() > 0) {
				this.nguongocnhapkhauArr = this.nguongocnhapkhau.split("__");
			}

			this.dvkdId = "";
			if (rs.getString("dvkdId") != null) {
				this.dvkdId = rs.getString("dvkdId");
			}

			this.nhId = "";
			if (rs.getString("nhId") != null) {
				this.nhId = rs.getString("nhId");
			}

			this.clId = "";
			if (rs.getString("clId") != null) {
				this.clId = rs.getString("clId");
			}

			this.giablc = "";
			if (rs.getString("giablc") != null)
				this.giablc = rs.getString("giablc");

			this.trangthai = rs.getString("trangthai");
			this.type = rs.getString("type");

			this.dvdlId = "";
			if (rs.getString("dvdlId") != null) {
				this.dvdlId = rs.getString("dvdlId");
			}

			// -----------------
			if (rs.getString("trongluong") != null)
				this.kl = rs.getString("trongluong");

			if (rs.getString("thetich") != null)
				this.tt = rs.getString("thetich");

			if (rs.getString("DVDL_ETC_FK") != null)
				this.dvdlETCId = rs.getString("DVDL_ETC_FK");
			this.spchuluc = rs.getString("spchuluc") == null ? "" : rs
					.getString("spchuluc");
			this.spmoi = rs.getString("spmoi") == null ? "" : rs
					.getString("spmoi");
			rs.close();

			query = " select soluong1 as sl1, dvdl1_fk as dvdl1, soluong2 as sl2, dvdl2_fk as dvdl2 from quycach where sanpham_fk = '"
					+ id
					+ "' and dvdl1_fk="
					+ this.dvdlId
					+ " and dvdl2_fk=100018";

			System.out.println(query);
			rs = this.db.get(query);
			if (rs != null) {
				if (rs.next()) {

					this.sl1[0] = rs.getString("sl1");
					this.dvdl1[0] = rs.getString("dvdl1");
					this.sl2[0] = rs.getString("sl2");
					this.dvdl2[0] = rs.getString("dvdl2");
				}
				rs.close();
			}

			query = " select soluong1 as sl1, dvdl1_fk as dvdl1, soluong2 as sl2, dvdl2_fk as dvdl2 from quycach where sanpham_fk = '"
					+ id
					+ "' and ( dvdl1_fk <> "
					+ this.dvdlId
					+ " or dvdl2_fk <> 100018 )";
			System.out.println(query);
			rs = this.db.get(query);
			int i = 0;
			if (rs != null) {
				while (rs.next()) {

					this.sl1[i] = rs.getString("sl1");
					this.dvdl1[i] = rs.getString("dvdl1");

					System.out.print("Don vi do luong la: " + this.sl1[i]
							+ "\n");

					this.sl2[i] = rs.getString("sl2");
					this.dvdl2[i] = rs.getString("dvdl2");
					i++;
				}
				rs.close();
			}

			if (this.id != null && this.id.length() > 0) {
				query = "select nhomhang_fk,sanpham_fk from nhomhang_sanpham where  sanpham_fk='"
						+ this.id + "'";
				rs = this.db.get(query);
				try {
					while (rs.next()) {
						this.nhomHangId += rs.getString("nhomhang_fk") + ",";
					}
					if (rs != null)
						rs.close();
					if (nhomHangId.length() > 0) {
						nhomHangId = nhomHangId.substring(0,
								nhomHangId.length() - 1);
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			query = "select ngay, thongtin, ghichu from sanpham_chitiet where sp_FK = '"
					+ this.id + "'";

			rs = db.get(query);
			if (rs != null) {
				String _ngay = "";
				String _thongtin = "";
				String _ghichu = "";

				while (rs.next()) {
					_ngay += rs.getString("ngay") + "__";
					_thongtin += rs.getString("thongtin") + "__";
					_ghichu += rs.getString("ghichu") + "__";
				}
				rs.close();

				if (_ngay.trim().length() > 0) {
					_ngay = _ngay.substring(0, _ngay.length() - 2);
					this.ngayArr = _ngay.split("__");

					_thongtin = _thongtin.substring(0, _thongtin.length() - 2);
					this.thongtinArr = _thongtin.split("__");

					_ghichu = _ghichu.substring(0, _ghichu.length() - 2);
					this.ghichuArr = _ghichu.split("__");
				}
			}

			this.loaispRs = createLoaispRS();
			CreateRS();

			createNspIds();
			createSpIds();

		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}

	public void DBClose() {

		try {
			if (this.nh != null)
				this.nh.close();
			if (this.cl != null)
				this.cl.close();
			if (this.dvdl != null)
				this.dvdl.close();
			if (this.dvkd != null)
				this.dvkd.close();
			if (this.nsp != null)
				this.nsp.close();
			if (rsPacksize != null) {
				rsPacksize.close();
			}
			this.db.shutDown();
		} catch (java.sql.SQLException e) {
		}
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public ResultSet getSanphamRs() {
		return this.spList;
	}

	public void setSanphamRs(ResultSet spRs) {
		this.spList = spRs;
	}

	public Hashtable<Integer, String> getSpIds() {
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if (this.spIds != null) {
			int size = (this.spIds).length;
			int m = 0;
			while (m < size) {
				selected.put(new Integer(m), this.spIds[m]);
				m++;
			}
		} else {
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setSpIds(String[] spIds) {
		this.spIds = spIds;
	}

	private void createSpList() {
		if (this.type.equals("1")) {
			String query = "select pk_seq, ma as spMa, ten as spTen, isnull(bdsp.soluong,'0') as soluong ";
			if (this.nhanhangIds.length() > 0)
				query = query + " and nhanhang_fk  in (" + this.nhanhangIds
						+ ")";
			if (this.chungloaiIds.length() > 0)
				query = query + " and chungloai_fk in(  " + this.chungloaiIds
						+ " ) ";
			System.out.println("Id : " + this.id);
			query += "from sanpham sp left join " + "( "
					+ "	select bundle_fk,sanpham_fk,soluong " + "	from  "
					+ "    bundle_sanpham " + "   where 1=1   ";
			if (this.id.length() > 0) {
				// this.spSelectedList = this.db.get(query +
				// " and pk_seq in(select sanpham_fk from Bundle_Sanpham where bundle_fk='"
				// + this.id + "')");
				query += "   and bundle_fk='" + this.id
						+ "' and sanpham_fk !='" + this.id + "' ";
			}
			query += ")bdsp on sp.pk_seq = bdsp.sanpham_fk "
					+ " where dvkd_fk = '" + this.dvkdId + "'"
					+ " order by soluong desc ";
			System.out.print("SP List : " + query);
			this.spList = db.get(query);
		}
	}

	public void setNspSelected(ResultSet nsp) {
		this.nspSelected = nsp;
	}

	public ResultSet getNspSelected() {
		return this.nspSelected;
	}

	public ResultSet getSanphamSelectedRs() {
		return this.spSelectedList;
	}

	public void setSanphamSelectedRs(ResultSet spRs) {
		this.spSelectedList = spRs;
	}

	public void init2() {
		try {
			String query = "select trangthaiDAILY, isnull(a.hansudung,180) as HanSuDung,a.pk_seq as id, a.ma as masp, a.ten as tensp, b.donvikinhdoanh as dvkd, b.pk_seq as dvkdId, c.pk_seq as clId, c.ten as chungloai, e.pk_seq as nhId, d.donvi, e.ten as nhanhang, d.pk_seq as dvdlId, a.trangthai,f.giabanlechuan as giablc, a.type, a.DVDL_ETC_FK,a.spchuluc,a.spmoi, a.ma_fast, a.hamluong, a.tenxuathoadon, a.nguongocnhapkhau, a.loaisanpham_fk, a.visa, a.ngaycap, a.kkg, a.nsx, a.nxb, a.nkk ";
			query = query
					+ " from sanpham a inner join donvikinhdoanh b on a.dvkd_fk = b.pk_seq left join chungloai c on a.chungloai_fk = c.pk_seq left join donvidoluong d on a.dvdl_fk = d.pk_seq left join nhanhang e on a.nhanhang_fk = e.pk_seq ";
			query = query
					+ " inner join banggiablc_sanpham f on a.pk_seq = f.sanpham_fk inner join banggiabanlechuan g on f.bgblc_fk = g.pk_seq ";

			query = query + " where a.pk_seq = '" + this.id + "'";

			ResultSet rs = this.db.get(query);

			rs.next();
			this.id = rs.getString("id");
			this.trangthaiDL = rs.getString("trangthaiDAILY");
			this.masp = rs.getString("masp");
			this.maspcu = rs.getString("ma_fast") == null ? "" : rs
					.getString("ma_fast");
			this.ten = rs.getString("tensp");
			this.hansudung = rs.getString("HanSuDung");
			this.hamluong = rs.getString("hamluong") == null ? "" : rs
					.getString("hamluong");
			this.loaisp = rs.getString("loaisanpham_fk") == null ? "" : rs
					.getString("loaisanpham_fk");
			this.visa = rs.getString("visa") == null ? "" : rs
					.getString("visa");
			this.ngaycap = rs.getString("ngaycap") == null ? "" : rs
					.getString("ngaycap");
			this.kkg = rs.getString("kkg") == null ? "" : rs.getString("kkg");
			this.nsx = rs.getString("nsx") == null ? "" : rs.getString("nsx");
			this.nxb = rs.getString("nxb") == null ? "" : rs.getString("nxb");
			this.nkk = rs.getString("nkk") == null ? "" : rs.getString("nkk");

			this.tenxuathoadon = rs.getString("tenxuathoadon") == null ? ""
					: rs.getString("tenxuathoadon");
			if (this.tenxuathoadon.trim().length() > 0) {
				this.tenxuathoadonArr = this.tenxuathoadon.split("__");
			}

			this.nguongocnhapkhau = rs.getString("nguongocnhapkhau") == null ? ""
					: rs.getString("nguongocnhapkhau");
			if (this.nguongocnhapkhau.trim().length() > 0) {
				this.nguongocnhapkhauArr = this.nguongocnhapkhau.split("__");
			}
			if (rs.getString("dvkdId") == null) {
				this.dvkdId = "";
			} else {
				this.dvkdId = rs.getString("dvkdId");
			}
			if (rs.getString("nhId") == null) {
				this.nhId = "";
			} else {
				this.nhId = rs.getString("nhId");
			}

			if (rs.getString("clId") == null) {
				this.clId = "";
			} else {
				this.clId = rs.getString("clId");
			}

			this.giablc = rs.getString("giablc");
			this.trangthai = rs.getString("trangthai");
			this.type = rs.getString("type");

			if (rs.getString("dvdlId") == null) {
				this.dvdlId = "";
			} else {
				this.dvdlId = rs.getString("dvdlId");
			}

			if (rs.getString("DVDL_ETC_FK") != null)
				this.dvdlETCId = rs.getString("DVDL_ETC_FK");
			this.spchuluc = rs.getString("spchuluc");
			this.spmoi = rs.getString("spmoi");
			rs.close();

			query = "select soluong1 as sl1, dvdl1_fk as dvdl1, soluong2 as sl2, dvdl2_fk as dvdl2 from quycach where sanpham_fk = '"
					+ id + "'";
			// this.msg =query;
			rs = this.db.get(query);
			int i = 0;
			while (rs.next()) {
				this.sl1[i] = rs.getString("sl1");
				this.dvdl1[i] = rs.getString("dvdl1");
				this.sl2[i] = rs.getString("sl2");
				this.dvdl2[i] = rs.getString("dvdl2");
				i++;
			}
			rs.close();

			query = "select ngay, thongtin, ghichu from sanpham_chitiet where sp_FK = '"
					+ this.id + "'";

			rs = db.get(query);
			if (rs != null) {
				String _ngay = "";
				String _thongtin = "";
				String _ghichu = "";

				while (rs.next()) {
					_ngay += rs.getString("ngay") + "__";
					_thongtin += rs.getString("thongtin") + "__";
					_ghichu += rs.getString("ghichu") + "__";
				}
				rs.close();

				if (_ngay.trim().length() > 0) {
					_ngay = _ngay.substring(0, _ngay.length() - 2);
					this.ngayArr = _ngay.split("__");

					_thongtin = _thongtin.substring(0, _thongtin.length() - 2);
					this.thongtinArr = _thongtin.split("__");

					_ghichu = _ghichu.substring(0, _ghichu.length() - 2);
					this.ghichuArr = _ghichu.split("__");
				}
			}

			this.dvdl = createDvdlRS();

			this.dvkd = createDvkdRS();
			this.nh = createNhRS();
			this.cl = createClRS();
			this.loaispRs = createLoaispRS();
			this.createNspRS2();
			this.createSpList2();

			createNspIds();
			createSpIds();

		} catch (java.sql.SQLException e) {
		}
	}

	private void createSpList2() {
		String query = "select pk_seq, ma as spMa, ten as spTen from sanpham where pk_seq in(select sanpham_fk from Bundle_Sanpham where bundle_fk='"
				+ this.id + "')";
		this.spSelectedList = this.db.get(query);
	}

	private void createNspRS2() {
		this.nspSelected = this.db
				.get("select a.nsp_fk as nspId, b.TEN as nspTen, b.diengiai as diengiai from nhomsanpham_sanpham a inner join nhomsanpham b on a.nsp_fk = b.pk_seq where a.sp_fk = '"
						+ this.id + "'");
	}

	public void setKL(String kl) {
		this.kl = kl;
	}

	public void setDVKL(String dvkl) {
		this.dvkl = dvkl;
	}

	public void setDVTT(String dvtt) {
		this.dvtt = dvtt;
	}

	public void setTT(String tt) {
		this.tt = tt;
	}

	public String getKL() {
		return this.kl;
	}

	public String getDVKL() {
		return this.dvkl;
	}

	public String getDVTT() {
		return this.dvtt;
	}

	public String getTT() {
		return this.tt;
	}


	public ResultSet getPacksizeRs() {

		return this.rsPacksize;
	}


	public void setPacksizeRs(ResultSet rs) {

		this.rsPacksize = rs;
	}


	public String getPacksizeId() {

		return this.PacksizeId;
	}


	public void setPacksizeId(String packsizeid) {

		this.PacksizeId = packsizeid;
	}


	public String getMachuan() {

		return this.machuan;
	}


	public void setMachuan(String _machuan) {

		this.machuan = _machuan;
	}


	public String getNganhhangid() {

		return this.nganhhangid;
	}


	public void setNganhhangid(String nganhhangid) {

		this.nganhhangid = nganhhangid;
	}


	public ResultSet getRsNganhHang() {

		return this.RsNganhHang;
	}


	public void setRsNganhhang(ResultSet rs) {

		this.RsNganhHang = rs;
	}


	public String getquydoithuong() {

		return this.Quydoithuong;
	}


	public void setquydoithuong(String quydoithuong) {

		this.Quydoithuong = quydoithuong;
	}


	public String getTenchuan() {

		return this.tenchuan;
	}


	public void setTenchuan(String Tenchuan) {
		this.tenchuan = Tenchuan;
	}

	public String getHansudung() {
		return hansudung;
	}

	public void setHansudung(String hansudung) {
		this.hansudung = hansudung;
	}

	public String getNhanhangIds() {
		return nhanhangIds;
	}

	public void setNhanhangIds(String nhanhangIds) {
		this.nhanhangIds = nhanhangIds;
	}

	public String getChungloaiIds() {
		return chungloaiIds;
	}

	public void setChungloaiIds(String chungloaiIds) {
		this.chungloaiIds = chungloaiIds;
	}

	public String getDvdlETCId() {
		return this.dvdlETCId;
	}

	public void setDvdlETCId(String dvdlETCId) {
		this.dvdlETCId = dvdlETCId;
	}

	String nhomHangId;
	ResultSet nhomHangRs;

	public String getNhomHangId() {
		return nhomHangId;
	}

	public void setNhomHangId(String nhomHangId) {
		this.nhomHangId = nhomHangId;
	}

	public ResultSet getNhomHangRs() {
		return nhomHangRs;
	}

	public void setNhomHangRs(ResultSet nhomHangRs) {
		this.nhomHangRs = nhomHangRs;
	}

	public String getSpMoi() {
		return this.spmoi;
	}

	public void setSpMoi(String spmoi) {
		this.spmoi = spmoi;
	}

	public String getSpChuLuc() {
		return this.spchuluc;
	}

	public void setSpChuLuc(String spchuluc) {
		this.spchuluc = spchuluc;
	}


	public String getMaspcu() {
		
		return this.maspcu;
	}


	public void setMaspcu(String maspcu) {
		
		this.maspcu = maspcu;
	}


	public String getHamluong() {
		
		return this.hamluong;
	}


	public void setHamluong(String hamluong) {
		
		this.hamluong = hamluong;
	}


	public String[] getTenxuathoadonArr() {
		
		return this.tenxuathoadonArr;
	}


	public void setTenxuathoadonArr(String[] tenxuathoadonArr) {
		
		this.tenxuathoadonArr = tenxuathoadonArr;
	}


	public String[] getNguongocnhapkhauArr() {
		
		return this.nguongocnhapkhauArr;
	}


	public void setNguongocnhapkhauArr(String[] nguongocnhapkhauArr) {
		
		this.nguongocnhapkhauArr = nguongocnhapkhauArr;
	}


	public String getTenxuathoadon() {
		
		return this.tenxuathoadon;
	}


	public void setTenxuathoadon(String tenxuathoadon) {
		
		this.tenxuathoadon = tenxuathoadon;
	}


	public String getNguongocnhapkhau() {
		
		return this.nguongocnhapkhau;
	}


	public void setNguongocnhapkhau(String nguongocnhapkhau) {
		
		this.nguongocnhapkhau = nguongocnhapkhau;
	}


	public String[] getNgayArr() {
		
		return this.ngayArr;
	}


	public void setNgayArr(String[] ngayArr) {
		
		this.ngayArr = ngayArr;
	}


	public String[] getThongtinArr() {
		
		return this.thongtinArr;
	}


	public void setThongtinArr(String[] thongtinArr) {
		
		this.thongtinArr = thongtinArr;
	}


	public String[] getGhichuArr() {
		
		return this.ghichuArr;
	}


	public void setGhichuArr(String[] ghichuArr) {
		
		this.ghichuArr = ghichuArr;
	}


	public ResultSet getLoaispRs() {
		
		return this.loaispRs;
	}


	public void setLoaispRs(ResultSet loaispRs) {
		
		this.loaispRs = loaispRs;
	}


	public String getVisa() {
		
		return this.visa;
	}


	public void setVisa(String visa) {
		
		this.visa = visa;
	}


	public String getNgaycap() {
		
		return this.ngaycap;
	}


	public void setNgaycap(String ngaycap) {
		
		this.ngaycap = ngaycap;
	}


	public String getKkg() {
		
		return this.kkg;
	}


	public void setKkg(String kkg) {
		
		this.kkg = kkg;
	}


	public String getNsx() {
		
		return this.nsx;
	}


	public void setNsx(String nsx) {
		
		this.nsx = nsx;
	}


	public String getNkk() {
		
		return this.nkk;
	}


	public void setNkk(String nkk) {
		
		this.nkk = nkk;
	}


	public String getNxb() {
		
		return this.nxb;
	}


	public void setNxb(String nxb) {
		
		this.nxb = nxb;
	}


	public String getLoaisp() {
		
		return this.loaisp;
	}


	public void setLoaisp(String loaisp) {
		
		this.loaisp = loaisp;
	}


	public String getTenhoatchat() {
		
		return this.tenhoatchat;
	}


	public void setTenhoatchat(String tenhoatchat) {
		this.tenhoatchat = tenhoatchat;
		
	}


	public String getDangtrinhbay() {
		
		return this.dangtrinhbay;
	}


	public void setDangtrinhbay(String dangtrinhbay) {
		
		this.dangtrinhbay = dangtrinhbay;
	}


	public String getQuycachdonggoi() {
		
		return this.quycachdonggoi;
	}


	public void setQuycachdonggoi(String quycachdonggoi) {
		
		this.quycachdonggoi = quycachdonggoi;
	}


	public String getTieuchuanchatluong() {
		
		return this.tieuchuanchatluong;
	}


	public void setTieuchuanchatluong(String tieuchuanchatluong) {
		
		this.tieuchuanchatluong = tieuchuanchatluong;
	}


	public String getNguongocnguyenlieu() {
		
		return this.nguongocnguyenlieu;
	}


	public void setNguongocnguyenlieu(String nguongocnguyenlieu) {
		
		this.nguongocnguyenlieu = nguongocnguyenlieu;
	}


	public String getGiabancothue() {
		
		return this.giabancothue;
	}


	public void setGiabancothue(String giabancothue) {
		
		this.giabancothue = giabancothue;
	}


	public String getNuocsx() {
		
		return this.nuocsx;
	}


	public void setNuocsx(String nuocsx) {
		
		this.nuocsx = nuocsx;
	}


	public String getTieuchuannsx() {
		
		return this.tieuchuannsx;
	}


	public void setTieuchuannsx(String tieuchuannsx) {
		
		this.tieuchuannsx = tieuchuannsx;
	}


	public String getTenviettat() {
		
		return this.tenviettat;
	}


	public void setTenviettat(String tenviettat) {
		
		this.tenviettat = tenviettat;
	}


	public String getGiakktheodvt() {
		
		return this.giakktheodvt;
	}


	public void setGiakktheodvt(String giakk) {
		
		this.giakktheodvt = giakk;
	}


	public String getNhomsanphamHtotc() {
		
		return this.nhomsphtotc;
	}


	public void setNhomsanphamHtotc(String nhomsphtotc) {
		
		this.nhomsphtotc = nhomsphtotc;
	}


	public String getStttheoTT40() {
		
		return this.stttheoTT40;
	}


	public void setStttheoTT40(String StttheoTT40) {
		
		this.stttheoTT40 = StttheoTT40;
	}


	public String getNguongoc() {
		
		return this.nguongoc;
	}


	public void setNguongoc(String nguongoc) {
		this.nguongoc = nguongoc;
		
	}


	public String getThuexuat() {
		
		return this.thuesuat;
	}


	public void setThuetxuat(String thuexuat) {
		
		this.thuesuat = thuexuat;
		System.out.println("Thue xuat trong bean "+thuesuat);
	}


	public String getHethanvisa() {
		
		return this.hethanvisa;
	}


	public void setHethanvisa(String hethanvisa) {
		
		this.hethanvisa = hethanvisa;
	}


	public String getTrangthaiDaily() {

		return this.trangthaiDL;
	}

	public void setTrangthaiDaily(String trangthaiDL) {
		
		this.trangthaiDL = trangthaiDL;
	}
}
