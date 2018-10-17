package geso.traphaco.erp.beans.phieuphache.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.phieuphache.IErpPhieuPhaChe;
import geso.traphaco.erp.beans.phieuphache.IErpPhieuPhaChe_SanPham;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpPhieuPhaChe implements IErpPhieuPhaChe {
	private String userId;
	private String congtyId;
	private String id;
	private String ngaychungtu;
	private String thuocloai;
	private String sanphamId;
	private String spIsKqlsl;
	private String sanpham;
	private String kehoach;
	private String kehoachCt;
	private String nguoiphache;
	private String tailieuphache;
	private String congthucphache;
	private String khonhap;
	private String soluongnhap;

	private List<ThongTinNhapKho> ThongTinNhapKhoList;
	
	private String diengiai;
//	private String trangthai;
	private String msg;
	
	private List<IErpPhieuPhaChe_SanPham> SanphamList;
	
//	private ResultSet SanphamRs;
	private ResultSet KehoachRs;
	private ResultSet KehoachChitietRs;
	private ResultSet TailieuphacheRs;
	private ResultSet KhoRs;
	private ResultSet BinRs;
	dbutils db;
	
	public ErpPhieuPhaChe() {
		this.id = "";
		this.ngaychungtu = "";
		this.thuocloai = "0";
		this.sanphamId = "";
		this.spIsKqlsl = "";
		this.sanpham = "";
		this.kehoach = "";
		this.kehoachCt = "";
		this.nguoiphache = "";
		this.tailieuphache = "";
		this.congthucphache = "";
		this.khonhap = "";
		this.soluongnhap = "";

		this.ThongTinNhapKhoList = new ArrayList<ThongTinNhapKho>();
		
		this.diengiai = "";
//		this.trangthai = "1";
		this.msg = "";
		
		this.SanphamList = new ArrayList<IErpPhieuPhaChe_SanPham>();
		
		this.db = new dbutils();
	}
	
	public void init(){
		String query = "select ppc.THUOCLOAI, ppc.ngaychungtu, ppc.sanpham_fk, ppc.kehoachphache_fk, ppc.kehoachphache_ct_fk, ppc.nguoiphache,"
				+ " ppc.tailieuphachethuoc_fk, ppc.khott_fk, ppc.soluongnhap, ppc.diengiai, sp.ma + ' - ' + sp.ten as tensp"
				+ " from ERP_PHIEUPHACHE ppc left join ERP_SANPHAM sp on sp.pk_seq = ppc.sanpham_fk"
				+ " where ppc.pk_seq = " + this.id;
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		
		if(rs != null){
			try {
				rs.next();
				this.ngaychungtu = rs.getString("ngaychungtu");
				this.thuocloai = rs.getString("THUOCLOAI");
				this.sanphamId = rs.getString("sanpham_fk");
				this.sanpham = rs.getString("tensp");
				this.kehoach = rs.getString("kehoachphache_fk");
				this.kehoachCt = rs.getString("kehoachphache_ct_fk");
				this.nguoiphache = rs.getString("nguoiphache");
				this.tailieuphache = rs.getString("tailieuphachethuoc_fk");
				this.khonhap = rs.getString("khott_fk") == null ? "" : rs.getString("khott_fk");
				this.soluongnhap = rs.getString("soluongnhap") == null ? "" : rs.getString("soluongnhap");
				this.diengiai = rs.getString("diengiai");
				rs.close();
			} catch (SQLException e) {}
		}
		
		query = "select solo, isnull('['+cast(bin.PK_SEQ as varchar)+'] '+bin.MA+' - '+bin.TEN,'') as vitri, mathung, mame, SOLUONGCHITIET"
				+ " from ERP_PHIEUPHACHE_NHAPKHO ppc_nk left join ERP_BIN bin on bin.PK_SEQ = ppc_nk.BIN_FK where phieuphache_fk = " + this.id;
		System.out.println(query);
		rs = this.db.get(query);
		if(rs != null){
			try {
				ThongTinNhapKho ThongTinNhapKho;
				while(rs.next()){
					ThongTinNhapKho = new ThongTinNhapKho();
					
					ThongTinNhapKho.setSolo(rs.getString("solo"));
					ThongTinNhapKho.setVitri(rs.getString("vitri"));
					ThongTinNhapKho.setSothung(rs.getString("mathung"));
					ThongTinNhapKho.setSome(rs.getString("mame"));
					ThongTinNhapKho.setSoluongchitiet(rs.getString("SOLUONGCHITIET"));
					
					this.ThongTinNhapKhoList.add(ThongTinNhapKho);
				}
				rs.close();
			} catch (SQLException e) {}
		}
		
		query = "select tlpc_tt.pk_seq as tlpctt_id,isnull(ppc_sp.pk_seq,0) as pk_seq, sp.PK_SEQ as idsp,sp.MA as masp,sp.TEN as tensp,dvdl.DONVI as dvt,isnull(ppc_sp.KHOTT_FK,0) as KHOTT_FK,tlpc_tt.SOLUONG as soluonglythuyet,isnull(ppc_sp.TONGXUAT,'') as TONGXUAT"
				+ " from ERP_TAILIEUPHACHETHUOC_THONGTIN tlpc_tt left join ERP_PHIEUPHACHE_SANPHAM ppc_sp on tlpc_tt.PK_SEQ=ppc_sp.TLPC_TT_FK and ppc_sp.PHIEUPHACHE_FK="+this.id
				+ " inner join ERP_SANPHAM sp on sp.PK_SEQ = tlpc_tt.SANPHAM_FK left join DONVIDOLUONG dvdl on dvdl.PK_SEQ = sp.DVDL_FK where TAILIEUPHACHETHUOC_FK="+this.tailieuphache;
		System.out.println(query);
		rs = this.db.get(query);
		if(rs != null){
			try {
				IErpPhieuPhaChe_SanPham Sanpham;
				List<ErpPhieuPhaChe_SP_ChiTiet> SpChitietList;
				ErpPhieuPhaChe_SP_ChiTiet SpChitiet;
				ResultSet rsChitiet;
				while(rs.next()){
					Sanpham = new ErpPhieuPhaChe_SanPham();
					
					Sanpham.setTlpcTtId(rs.getString("tlpctt_id"));
					Sanpham.setIdsp(rs.getString("idsp"));
					Sanpham.setMasp(rs.getString("masp"));
					Sanpham.setTensp(rs.getString("tensp"));
					Sanpham.setDvt(rs.getString("dvt"));
					Sanpham.setKhoxuat(rs.getString("KHOTT_FK"));
					Sanpham.setSoluonglythuyet(rs.getString("soluonglythuyet"));
					Sanpham.setTongxuat(rs.getString("TONGXUAT"));
					
					SpChitietList = new ArrayList<ErpPhieuPhaChe_SP_ChiTiet>();
					
					if(!rs.getString("pk_seq").equals("0")){
						/*query = "select kspct.pk_seq,kspct.SOLO,kspct.NGAYHETHAN,kspct.NGAYNHAPKHO, kspct.MAME,kspct.MATHUNG,kspct.bin_fk,isnull(bin.MA,'') as vitri,"
								+ "kspct.MAPHIEU,kspct.MAPHIEUDINHTINH, kspct.PHIEUEO,kspct.marq, kspct.hamluong, kspct.hamam, kspct.loaidoituong, kspct.doituongid,"
								+ "kspct.NSX_FK, isnull(nsx.TEN,'') as nhasanxuat, kspct.AVAILABLE, isnull(ppc_spct.SOLUONGCHITIET,'') as soluongchitiet"
								+ " from ERP_KHOTT_SP_CHITIET kspct"
								+ " left join ERP_PHIEUPHACHE_SP_CHITIET ppc_spct on ppc_spct.phieuphache_sp_fk="+rs.getString("pk_seq")
								+ " and ppc_spct.SOLO = kspct.SOLO and ppc_spct.NGAYHETHAN = kspct.NGAYHETHAN and ppc_spct.BIN_FK = kspct.BIN_FK"
								+ " and ppc_spct.NGAYNHAPKHO = kspct.NGAYNHAPKHO and ppc_spct.MARQ = kspct.MARQ and ppc_spct.HAMLUONG = kspct.HAMLUONG"
								+ " and ppc_spct.HAMAM = kspct.HAMAM and ppc_spct.LOAIDOITUONG = kspct.LOAIDOITUONG and ppc_spct.DOITUONGID = kspct.DOITUONGID"
								+ " and ppc_spct.MAME = kspct.MAME and ppc_spct.MATHUNG = kspct.MATHUNG and ppc_spct.MAPHIEU = kspct.MAPHIEU"
								+ " and ppc_spct.MAPHIEUDINHTINH = kspct.MAPHIEUDINHTINH and ppc_spct.PHIEUEO = kspct.PHIEUEO and ppc_spct.NSX_FK = kspct.NSX_FK"
								+ " left join ERP_BIN bin on bin.PK_SEQ = kspct.BIN_FK left join ERP_NHASANXUAT nsx on nsx.PK_SEQ = kspct.NSX_FK"
								+ " where kspct.KHOTT_FK = '"+Sanpham.getKhoxuat()+"' and SANPHAM_FK = '"+Sanpham.getIdsp()+"'";*/
						
						query = "select kspct.pk_seq,kspct.SOLO,kspct.NGAYHETHAN,kspct.NGAYNHAPKHO, kspct.MAME,kspct.MATHUNG,kspct.bin_fk,isnull(bin.MA,'') as vitri,"
								+ "kspct.MAPHIEU,kspct.MAPHIEUDINHTINH, kspct.PHIEUEO,kspct.marq, kspct.hamluong, kspct.hamam, kspct.loaidoituong, kspct.doituongid,"
								+ "kspct.NSX_FK, isnull(nsx.TEN,'') as nhasanxuat, kspct.AVAILABLE, isnull(ppc_spct.SOLUONGCHITIET,'') as soluongchitiet"
								+ " from ERP_KHOTT_SP_CHITIET kspct"
								+ " left join ERP_PHIEUPHACHE_SP_CHITIET ppc_spct on ppc_spct.phieuphache_sp_fk="+rs.getString("pk_seq")+" and ppc_spct.KHOTT_SP_CT_FK=kspct.PK_SEQ"
								+ " left join ERP_BIN bin on bin.PK_SEQ = kspct.BIN_FK left join ERP_NHASANXUAT nsx on nsx.PK_SEQ = kspct.NSX_FK"
								+ " where kspct.KHOTT_FK = '"+Sanpham.getKhoxuat()+"' and SANPHAM_FK = '"+Sanpham.getIdsp()+"'";
						System.out.println(query);
						rsChitiet = this.db.get(query);
						
						if(rsChitiet != null){
							try {
								SpChitietList = new ArrayList<ErpPhieuPhaChe_SP_ChiTiet>();
								while(rsChitiet.next()){
									SpChitiet = new ErpPhieuPhaChe_SP_ChiTiet();
									
									SpChitiet.setKhoSpCtId(rsChitiet.getString("pk_seq"));
									
									SpChitiet.setLoaidoituong(rsChitiet.getString("loaidoituong") == null ? "" : rsChitiet.getString("loaidoituong"));
									SpChitiet.setDoituongid(rsChitiet.getString("doituongid") == null ? "" : rsChitiet.getString("doituongid"));
									SpChitiet.setSolo(rsChitiet.getString("solo"));
									SpChitiet.setNgayhethan(rsChitiet.getString("NGAYHETHAN"));
									SpChitiet.setNgaynhapkho(rsChitiet.getString("NGAYNHAPKHO"));
									SpChitiet.setMame(rsChitiet.getString("MAME"));
									SpChitiet.setMathung(rsChitiet.getString("MATHUNG"));
									SpChitiet.setBinFk(rsChitiet.getString("bin_fk"));
									SpChitiet.setMaphieu(rsChitiet.getString("MAPHIEU"));
									SpChitiet.setMaphieudinhtinh(rsChitiet.getString("MAPHIEUDINHTINH"));
									SpChitiet.setPhieueo(rsChitiet.getString("PHIEUEO"));
									SpChitiet.setMarq(rsChitiet.getString("marq"));
									SpChitiet.setHamluong(rsChitiet.getString("hamluong"));
									SpChitiet.setHamam(rsChitiet.getString("hamam"));
									SpChitiet.setNsxId(rsChitiet.getString("nsx_fk") == null ? "" : rsChitiet.getString("nsx_fk"));
									
									SpChitiet.setVitri(rsChitiet.getString("vitri"));
									SpChitiet.setNhasanxuat(rsChitiet.getString("nhasanxuat"));
									SpChitiet.setSoluongton(rsChitiet.getString("available"));
									SpChitiet.setSoluongchitiet(rsChitiet.getString("soluongchitiet"));
									
									SpChitietList.add(SpChitiet);
								}
								rsChitiet.close();
								
								
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
					Sanpham.setSpChitietList(SpChitietList);
					
					this.SanphamList.add(Sanpham);
				}
				rs.close();
			} catch (SQLException e) {}
		}
		
		createRs();
	}
	
	public void createRs(){
		String query;
		ResultSet rs;
		
//		String query = "select PK_SEQ, MA + ' - ' + TEN as ten from ERP_SANPHAM where trangthai = '1'";
//		this.SanphamRs = this.db.get(query);
		
		if(this.thuocloai.length() > 0 && !this.thuocloai.equals("0")){
			query = "select khpc.PK_SEQ, (cast(khpc.PK_SEQ as varchar) + ' - ' + khpc.ngaykehoach + ' - ' + sp.ten + case when cast(khpc.diengiai as varchar) = '' then '' else ' - ' + cast(khpc.diengiai as nvarchar) end) as ten"
					+ " from ERP_KEHOACHPHACHE khpc"
					+ " left join ERP_SANPHAM sp on sp.pk_seq = khpc.sanpham_fk"
					+ " where khpc.trangthai = '1' and khpc.LOAI = " + this.thuocloai;
			if(this.ngaychungtu.trim().length() > 5){
				query += " and khpc.ngaykehoach <= '" + this.ngaychungtu + "'";
			}
			this.KehoachRs = this.db.get(query);
			
			if(this.kehoach.length() > 0){
				query = "select PK_SEQ, NGAYPHACHE, ghichu from ERP_KEHOACHPHACHE_CHITIET where KEHOACHPHACHE_FK = '"+this.kehoach+"'";
				if(this.ngaychungtu.trim().length() > 5){
					query += " and NGAYPHACHE <= '" + this.ngaychungtu + "'";
				}
				query += " order by STT";
				this.KehoachChitietRs = this.db.get(query);
				
				query = "select khpc.sanpham_fk, sp.ma + ' - ' + sp.ten as ten, sp.khongquanlysl from ERP_KEHOACHPHACHE khpc"
						+ " inner join ERP_SANPHAM sp on sp.pk_seq = khpc.sanpham_fk where khpc.pk_seq = " + this.kehoach;
				rs = this.db.get(query);
				try {
					rs.next();
					this.sanphamId = rs.getString("sanpham_fk");
					this.sanpham = rs.getString("ten");
					this.spIsKqlsl = rs.getString("khongquanlysl");
					rs.close();
				} catch (Exception e) {
					// TODO: handle exception
					this.msg = e.getMessage();
				}
			} else {
				this.sanphamId = "";
				this.sanpham = "";
				this.spIsKqlsl = "";
			}
		}
		
//		if(this.sanpham.length() > 0){
//			query = "select PK_SEQ from ERP_KEHOACHPHACHE where trangthai = '1' and SANPHAM_FK = " + this.sanpham;
//			this.KehoachRs = this.db.get(query);
//			
//			if(this.kehoach.length() > 0){
//				query = "select PK_SEQ, NGAYPHACHE, ghichu from ERP_KEHOACHPHACHE_CHITIET where KEHOACHPHACHE_FK = '"+this.kehoach+"' order by STT";
//				this.KehoachChitietRs = this.db.get(query);
//			}
//		}
		
		query = "select PK_SEQ, MA + ' - ' + NOIDUNG as ten from ERP_TAILIEUPHACHETHUOC where trangthai = '1'";
		this.TailieuphacheRs = this.db.get(query);
		
		if(this.tailieuphache.length() > 0){
			query = "select CONGTHUC from ERP_TAILIEUPHACHETHUOC where PK_SEQ = " + this.tailieuphache;
			rs = this.db.get(query);
			try {
				rs.next();
				this.congthucphache = rs.getString("CONGTHUC");
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				this.msg = e.getMessage();
			}
		}
		
		query = "select PK_SEQ, TEN from ERP_KHOTT where loai = '14' and trangthai = '1'";
//		query = "select PK_SEQ, TEN from ERP_KHOTT";
		this.KhoRs = this.db.getScrol(query);
		
		if(this.khonhap.length() > 0){
			query = "select pk_seq, ma+' - '+ten as ten from ERP_BIN where trangthai='1' and khott_fk="+this.khonhap;
			this.BinRs = this.db.getScrol(query);
		}
	}
	
	public void createSanphamList(){
		try {
			String query = "select tlpct_tt.pk_seq as tlpct_tt_id, sp.pk_seq as idsp, sp.MA as masp, sp.TEN as tensp, dvdl.DONVI as dvt, tlpct_tt.SOLUONG from ERP_TAILIEUPHACHETHUOC_THONGTIN tlpct_tt"
					+ " inner join ERP_SANPHAM sp on sp.PK_SEQ = tlpct_tt.SANPHAM_FK left join DONVIDOLUONG dvdl on dvdl.PK_SEQ = sp.DVDL_FK"
					+ " where tlpct_tt.TAILIEUPHACHETHUOC_FK = " + this.tailieuphache;
			
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			IErpPhieuPhaChe_SanPham Sanpham;
			if(rs != null){
				while(rs.next()){
					Sanpham = new ErpPhieuPhaChe_SanPham();
					
					Sanpham.setTlpcTtId(rs.getString("tlpct_tt_id"));
					Sanpham.setIdsp(rs.getString("idsp"));
					Sanpham.setMasp(rs.getString("masp"));
					Sanpham.setTensp(rs.getString("tensp"));
					Sanpham.setDvt(rs.getString("dvt"));
					Sanpham.setSoluonglythuyet(rs.getString("SOLUONG"));
					
					this.SanphamList.add(Sanpham);
				}
				rs.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public boolean create() {
		try {
			db.getConnection().setAutoCommit(false);
			
			if(this.kehoachCt.length() <= 0){
				this.msg = "Bạn chưa chọn ngày kế hoạch.";
				return false;
			}
			String query;
			
			if(this.spIsKqlsl.equals("1")){
				query = "insert into ERP_PHIEUPHACHE(thuocloai,ngaychungtu,sanpham_fk,kehoachphache_fk,kehoachphache_ct_fk,nguoiphache,"
						+ "tailieuphachethuoc_fk,diengiai,trangthai,nguoitao,ngaytao,nguoisua,ngaysua,congty_fk)"
						+ " values("+this.thuocloai+",'"+this.ngaychungtu+"','"+this.sanphamId+"','"+this.kehoach+"','"+this.kehoachCt+"',N'"+this.nguoiphache+"','"+this.tailieuphache+"',"
						+ "N'"+this.diengiai+"','0','"+this.userId+"','"+this.getDateTime()+"','"+this.userId+"','"+this.getDateTime()+"','"+this.congtyId+"')";
			} else {
				query = "insert into ERP_PHIEUPHACHE(thuocloai,ngaychungtu,sanpham_fk,kehoachphache_fk,kehoachphache_ct_fk,nguoiphache,"
						+ "tailieuphachethuoc_fk,khott_fk,soluongnhap,diengiai,trangthai,nguoitao,ngaytao,nguoisua,ngaysua,congty_fk)"
						+ " values("+this.thuocloai+",'"+this.ngaychungtu+"','"+this.sanphamId+"','"+this.kehoach+"','"+this.kehoachCt+"',N'"+this.nguoiphache+"','"+this.tailieuphache+"','"+this.khonhap+"',"
						+ "'"+this.soluongnhap+"',N'"+this.diengiai+"','0','"+this.userId+"','"+this.getDateTime()+"','"+this.userId+"','"+this.getDateTime()+"','"+this.congtyId+"')";
			}
			
			System.out.println(query);
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới ERP_PHIEUPHACHE: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ResultSet rs = db.get("select SCOPE_IDENTITY() as ID ");
			rs.next();
			String idPhieuphache = rs.getString("ID");
			rs.close();
			
//			if(this.ngaychungtu.length() > 0){
			query = "update ERP_KEHOACHPHACHE_CHITIET set ngaythucte = '"+this.ngaychungtu+"' where pk_seq = " + this.kehoachCt;
			System.out.println(query);
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ngày thực tế ERP_KEHOACHPHACHE_CHITIET: " + query;
				db.getConnection().rollback();
				return false;
			}
//			}
			if(!this.spIsKqlsl.equals("1")){
				query = "select QUANLYBIN from ERP_KHOTT where pk_seq = " + this.khonhap;
				rs = db.get(query);
				rs.next();
				String quanlibin = rs.getString("QUANLYBIN").trim();
				rs.close();
			
				if(quanlibin.equals("1")){
					ThongTinNhapKho ThongTinNhapKho;
					for(int i = 0; i < this.ThongTinNhapKhoList.size(); i++){
						ThongTinNhapKho = this.ThongTinNhapKhoList.get(i);
						if(ThongTinNhapKho.getSoluongchitiet().trim().length() > 0){
							if(ThongTinNhapKho.getVitri().length() <= 0){
								this.msg = "Bạn chưa chọn vị trí cho kho nhập.";
								db.getConnection().rollback();
								return false;
							}
							
							query = "insert into ERP_PHIEUPHACHE_NHAPKHO(phieuphache_fk,solo,bin_fk,mathung,mame,soluongchitiet)"
									+ " values('"+idPhieuphache+"','"+ThongTinNhapKho.getSolo()+"',"+ThongTinNhapKho.getVitri()+",'"+ThongTinNhapKho.getSothung()+"',"
									+ "'"+ThongTinNhapKho.getSome()+"','"+ThongTinNhapKho.getSoluongchitiet()+"')";
							System.out.println(query);
							if(!db.update(query)) {
								this.msg = "Không thể tạo mới ERP_PHIEUPHACHE_NHAPKHO: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				} else {
					ThongTinNhapKho ThongTinNhapKho;
					for(int i = 0; i < this.ThongTinNhapKhoList.size(); i++){
						ThongTinNhapKho = this.ThongTinNhapKhoList.get(i);
						if(ThongTinNhapKho.getSoluongchitiet().trim().length() > 0){
							query = "insert into ERP_PHIEUPHACHE_NHAPKHO(phieuphache_fk,solo,bin_fk,mathung,mame,soluongchitiet)"
									+ " values('"+idPhieuphache+"','"+ThongTinNhapKho.getSolo()+"',null,'"+ThongTinNhapKho.getSothung()+"',"
									+ "'"+ThongTinNhapKho.getSome()+"','"+ThongTinNhapKho.getSoluongchitiet()+"')";
							System.out.println(query);
							if(!db.update(query)) {
								this.msg = "Không thể tạo mới ERP_PHIEUPHACHE_NHAPKHO: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			
			String idnew;
			IErpPhieuPhaChe_SanPham Sanpham;
			for(int i = 0; i < this.SanphamList.size(); i++){
				Sanpham = this.SanphamList.get(i);
				
				if(Sanpham.getKhoxuat().length() > 0){
					query = "insert into ERP_PHIEUPHACHE_SANPHAM(phieuphache_fk,tlpc_tt_fk,khott_fk,tongxuat)"
							+ " values('"+idPhieuphache+"','"+Sanpham.getTlpcTtId()+"','"+Sanpham.getKhoxuat()+"','"+Sanpham.getTongxuat()+"')";
					System.out.println(query);
					if(!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_PHIEUPHACHE_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					rs = db.get("select SCOPE_IDENTITY() as ID ");
					rs.next();
					idnew = rs.getString("ID");
					rs.close();
					
					List<ErpPhieuPhaChe_SP_ChiTiet> SpChitietList = Sanpham.getSpChitietList();
					ErpPhieuPhaChe_SP_ChiTiet SpChitiet;
					for(int j = 0; j < SpChitietList.size(); j++){
						SpChitiet = SpChitietList.get(j);
						if(SpChitiet.getSoluongchitiet().trim().length() > 0){
							query = "insert into ERP_PHIEUPHACHE_SP_CHITIET(phieuphache_fk,phieuphache_sp_fk,khott_sp_ct_fk,soluongchitiet,solo,ngayhethan,"
									+ "bin_fk,ngaynhapkho,marq,hamluong,hamam,loaidoituong,doituongid,mame,mathung,maphieu,maphieudinhtinh,phieueo,nsx_fk)"
									+ " values('"+idPhieuphache+"','"+idnew+"','"+SpChitiet.getKhoSpCtId()+"','"+SpChitiet.getSoluongchitiet()+"','"+SpChitiet.getSolo()+"',"
									+ "'"+SpChitiet.getNgayhethan()+"',case when "+SpChitiet.getBinFk().length()+">0 then '"+SpChitiet.getBinFk()+"' else null end,"
									+ "'"+SpChitiet.getNgaynhapkho()+"','"+SpChitiet.getMarq()+"','"+SpChitiet.getHamluong()+"','"+SpChitiet.getHamam()+"',"
									+ "'"+SpChitiet.getLoaidoituong()+"',case when "+SpChitiet.getDoituongid().length()+">0 then '"+SpChitiet.getDoituongid()+"' else null end,"
									+ "'"+SpChitiet.getMame()+"','"+SpChitiet.getMathung()+"','"+SpChitiet.getMaphieu()+"','"+SpChitiet.getMaphieudinhtinh()+"',"
									+ "'"+SpChitiet.getPhieueo()+"',case when "+SpChitiet.getNsxId().length()+">0 then '"+SpChitiet.getNsxId()+"' else null end)";
							System.out.println(query);
							if(!db.update(query)) {
								this.msg = "Không thể tạo mới ERP_PHIEUPHACHE_SP_CHITIET: " + query;
								db.getConnection().rollback();
								return false;
							}
							
							Utility util = new Utility();
							
							this.msg = util.Update_KhoTT_MOI("","",db,Sanpham.getKhoxuat(),Sanpham.getIdsp(),SpChitiet.getSolo(),SpChitiet.getNgayhethan(),SpChitiet.getNgaynhapkho(), 
									SpChitiet.getMame(),SpChitiet.getMathung(),SpChitiet.getBinFk(),SpChitiet.getMaphieu(),SpChitiet.getMaphieudinhtinh(),SpChitiet.getPhieueo(),
									SpChitiet.getMarq(),SpChitiet.getHamluong(),SpChitiet.getHamam(),SpChitiet.getLoaidoituong(),SpChitiet.getDoituongid(),
									0, Double.parseDouble(SpChitiet.getSoluongchitiet()), -1 * Double.parseDouble(SpChitiet.getSoluongchitiet()),SpChitiet.getNsxId());
							
							if(this.msg.trim().length() > 0){
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean update() {
		try {
			db.getConnection().setAutoCommit(false);
			
			//Cộng lại kho
			String query = "select ppc_sp.KHOTT_FK,tlpc_tt.SANPHAM_FK,ppc_spct.soluongchitiet,ppc_spct.solo,ppc_spct.ngayhethan,"
					+ "ppc_spct.bin_fk,ppc_spct.ngaynhapkho,ppc_spct.marq,ppc_spct.hamluong,ppc_spct.hamam,ppc_spct.loaidoituong,"
					+ "ppc_spct.doituongid,ppc_spct.mame,ppc_spct.mathung,ppc_spct.maphieu,ppc_spct.maphieudinhtinh,ppc_spct.phieueo,ppc_spct.nsx_fk"
					+ " from ERP_PHIEUPHACHE_SP_CHITIET ppc_spct inner join ERP_PHIEUPHACHE_SANPHAM ppc_sp on ppc_sp.PK_SEQ=ppc_spct.PHIEUPHACHE_SP_FK"
					+ " inner join ERP_TAILIEUPHACHETHUOC_THONGTIN tlpc_tt on tlpc_tt.PK_SEQ=ppc_sp.TLPC_TT_FK where ppc_spct.PHIEUPHACHE_FK="+this.id;
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			while(rs.next()){
				String khoId = rs.getString("khott_fk");
				String spId = rs.getString("sanpham_fk");
				String solo = rs.getString("solo");
				String ngayhethan = rs.getString("ngayhethan");
				String ngaynhapkho = rs.getString("ngaynhapkho");
				String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				String doituongId = rs.getString("doituongid") == null ? "" : rs.getString("doituongid");
				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String bin_fk = rs.getString("bin_fk");
				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("maphieudinhtinh");
				String phieueo = rs.getString("phieueo");
				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");
				String nsx_fk = rs.getString("nsx_fk") == null ? "" : rs.getString("nsx_fk");
				double soluong = rs.getDouble("soluongchitiet");
				
				Utility util = new Utility();
				
				this.msg = util.Update_KhoTT_MOI("","",db,khoId,spId,solo,ngayhethan,ngaynhapkho,mame,mathung,bin_fk,maphieu,
						phieudt,phieueo,marq,hamluong,hamam,loaidoituong,doituongId,0,-1 * soluong,soluong, nsx_fk);
				if(this.msg.trim().length() > 0){
					db.getConnection().rollback();
					return false;
				}
			}
			rs.close();
			
			if(this.kehoachCt.length() <= 0){
				this.msg = "Bạn chưa chọn ngày kế hoạch.";
				return false;
			}
			
			if(this.spIsKqlsl.equals("1")){
				query = "update ERP_PHIEUPHACHE set thuocloai = "+this.thuocloai+",ngaychungtu='"+this.ngaychungtu+"',sanpham_fk='"+this.sanphamId+"',kehoachphache_fk='"+this.kehoach+"',"
						+ "kehoachphache_ct_fk='"+this.kehoachCt+"',nguoiphache=N'"+this.nguoiphache+"',tailieuphachethuoc_fk='"+this.tailieuphache+"',"
						+ "khott_fk=null,soluongnhap=null,diengiai=N'"+this.diengiai+"',nguoisua='"+this.userId+"',ngaysua='"+this.getDateTime()+"'"
						+ " where pk_seq = " + this.id;
			} else {
				query = "update ERP_PHIEUPHACHE set thuocloai = "+this.thuocloai+",ngaychungtu='"+this.ngaychungtu+"',sanpham_fk='"+this.sanphamId+"',kehoachphache_fk='"+this.kehoach+"',"
						+ "kehoachphache_ct_fk='"+this.kehoachCt+"',nguoiphache=N'"+this.nguoiphache+"',tailieuphachethuoc_fk='"+this.tailieuphache+"',"
						+ "khott_fk='"+this.khonhap+"',soluongnhap='"+this.soluongnhap+"',diengiai=N'"+this.diengiai+"',nguoisua='"+this.userId+"',ngaysua='"+this.getDateTime()+"'"
						+ " where pk_seq = " + this.id;
			}
			
			System.out.println(query);
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_PHIEUPHACHE: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "update ERP_KEHOACHPHACHE_CHITIET set ngaythucte = '"+this.ngaychungtu+"' where pk_seq = " + this.kehoachCt;
			System.out.println(query);
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ngày thực tế ERP_KEHOACHPHACHE_CHITIET: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_PHIEUPHACHE_NHAPKHO where PHIEUPHACHE_FK = " + this.id;
			if(!db.update(query)) {
				this.msg = "Không thể xóa ERP_PHIEUPHACHE_NHAPKHO: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_PHIEUPHACHE_SANPHAM where PHIEUPHACHE_FK = " + this.id;
			if(!db.update(query)) {
				this.msg = "Không thể xóa ERP_PHIEUPHACHE_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_PHIEUPHACHE_SP_CHITIET where PHIEUPHACHE_FK = " + this.id;
			if(!db.update(query)) {
				this.msg = "Không thể xóa ERP_PHIEUPHACHE_SP_CHITIET: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(!this.spIsKqlsl.equals("1")){
				query = "select QUANLYBIN from ERP_KHOTT where pk_seq = " + this.khonhap;
				rs = db.get(query);
				rs.next();
				String quanlibin = rs.getString("QUANLYBIN").trim();
				rs.close();
				
				if(quanlibin.equals("1")){
					ThongTinNhapKho ThongTinNhapKho;
					for(int i = 0; i < this.ThongTinNhapKhoList.size(); i++){
						ThongTinNhapKho = this.ThongTinNhapKhoList.get(i);
						if(ThongTinNhapKho.getSoluongchitiet().trim().length() > 0){
							if(ThongTinNhapKho.getVitri().length() <= 0){
								this.msg = "Bạn chưa chọn vị trí cho kho nhập.";
								db.getConnection().rollback();
								return false;
							}
							
							query = "insert into ERP_PHIEUPHACHE_NHAPKHO(phieuphache_fk,solo,bin_fk,mathung,mame,soluongchitiet)"
									+ " values('"+this.id+"','"+ThongTinNhapKho.getSolo()+"',"+ThongTinNhapKho.getVitri()+",'"+ThongTinNhapKho.getSothung()+"',"
									+ "'"+ThongTinNhapKho.getSome()+"','"+ThongTinNhapKho.getSoluongchitiet()+"')";
							System.out.println(query);
							if(!db.update(query)) {
								this.msg = "Không thể tạo mới ERP_PHIEUPHACHE_NHAPKHO: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				} else {
					ThongTinNhapKho ThongTinNhapKho;
					for(int i = 0; i < this.ThongTinNhapKhoList.size(); i++){
						ThongTinNhapKho = this.ThongTinNhapKhoList.get(i);
						if(ThongTinNhapKho.getSoluongchitiet().trim().length() > 0){
							query = "insert into ERP_PHIEUPHACHE_NHAPKHO(phieuphache_fk,solo,bin_fk,mathung,mame,soluongchitiet)"
									+ " values('"+this.id+"','"+ThongTinNhapKho.getSolo()+"',null,'"+ThongTinNhapKho.getSothung()+"',"
									+ "'"+ThongTinNhapKho.getSome()+"','"+ThongTinNhapKho.getSoluongchitiet()+"')";
							System.out.println(query);
							if(!db.update(query)) {
								this.msg = "Không thể tạo mới ERP_PHIEUPHACHE_NHAPKHO: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			
			String idnew;
			IErpPhieuPhaChe_SanPham Sanpham;
			for(int i = 0; i < this.SanphamList.size(); i++){
				Sanpham = this.SanphamList.get(i);
				
				if(Sanpham.getKhoxuat().length() > 0){
					query = "insert into ERP_PHIEUPHACHE_SANPHAM(phieuphache_fk,tlpc_tt_fk,khott_fk,tongxuat)"
							+ " values('"+this.id+"','"+Sanpham.getTlpcTtId()+"','"+Sanpham.getKhoxuat()+"','"+Sanpham.getTongxuat()+"')";
					System.out.println(query);
					if(!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_PHIEUPHACHE_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					rs = db.get("select SCOPE_IDENTITY() as ID ");
					rs.next();
					idnew = rs.getString("ID");
					rs.close();
					
					List<ErpPhieuPhaChe_SP_ChiTiet> SpChitietList = Sanpham.getSpChitietList();
					ErpPhieuPhaChe_SP_ChiTiet SpChitiet;
					for(int j = 0; j < SpChitietList.size(); j++){
						SpChitiet = SpChitietList.get(j);
						if(SpChitiet.getSoluongchitiet().trim().length() > 0){
							query = "insert into ERP_PHIEUPHACHE_SP_CHITIET(phieuphache_fk,phieuphache_sp_fk,khott_sp_ct_fk,soluongchitiet,solo,ngayhethan,"
									+ "bin_fk,ngaynhapkho,marq,hamluong,hamam,loaidoituong,doituongid,mame,mathung,maphieu,maphieudinhtinh,phieueo,nsx_fk)"
									+ " values('"+this.id+"','"+idnew+"','"+SpChitiet.getKhoSpCtId()+"','"+SpChitiet.getSoluongchitiet()+"','"+SpChitiet.getSolo()+"',"
									+ "'"+SpChitiet.getNgayhethan()+"',case when "+SpChitiet.getBinFk().length()+">0 then '"+SpChitiet.getBinFk()+"' else null end,"
									+ "'"+SpChitiet.getNgaynhapkho()+"','"+SpChitiet.getMarq()+"','"+SpChitiet.getHamluong()+"','"+SpChitiet.getHamam()+"',"
									+ "'"+SpChitiet.getLoaidoituong()+"',case when "+SpChitiet.getDoituongid().length()+">0 then '"+SpChitiet.getDoituongid()+"' else null end,"
									+ "'"+SpChitiet.getMame()+"','"+SpChitiet.getMathung()+"','"+SpChitiet.getMaphieu()+"','"+SpChitiet.getMaphieudinhtinh()+"',"
									+ "'"+SpChitiet.getPhieueo()+"',case when "+SpChitiet.getNsxId().length()+">0 then '"+SpChitiet.getNsxId()+"' else null end)";
							System.out.println(query);
							if(!db.update(query)) {
								this.msg = "Không thể tạo mới ERP_PHIEUPHACHE_SP_CHITIET: " + query;
								db.getConnection().rollback();
								return false;
							}
							
							Utility util = new Utility();
								
							this.msg = util.Update_KhoTT_MOI("","",db,Sanpham.getKhoxuat(),Sanpham.getIdsp(),SpChitiet.getSolo(),SpChitiet.getNgayhethan(),SpChitiet.getNgaynhapkho(), 
									SpChitiet.getMame(),SpChitiet.getMathung(),SpChitiet.getBinFk(),SpChitiet.getMaphieu(),SpChitiet.getMaphieudinhtinh(),SpChitiet.getPhieueo(),
									SpChitiet.getMarq(),SpChitiet.getHamluong(),SpChitiet.getHamam(),SpChitiet.getLoaidoituong(),SpChitiet.getDoituongid(),
									0, Double.parseDouble(SpChitiet.getSoluongchitiet()), -1 * Double.parseDouble(SpChitiet.getSoluongchitiet()),SpChitiet.getNsxId());
							
							if(this.msg.trim().length() > 0){
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBClose(){
		try {
			if(this.KehoachChitietRs != null)
				this.KehoachChitietRs.close();
//			if(this.SanphamRs != null)
//				this.SanphamRs.close();
			if(this.KehoachRs != null)
				this.KehoachRs.close();
			if(this.TailieuphacheRs != null)
				this.TailieuphacheRs.close();
			if(this.KhoRs != null)
				this.KhoRs.close();
			this.db.shutDown();
		} catch (SQLException e) {}
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCongtyId() {
		return congtyId;
	}
	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	/*public String getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}*/

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDiengiai() {
		return diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}

	public String getSanpham() {
		return sanpham;
	}

	public void setSanpham(String sanpham) {
		this.sanpham = sanpham;
	}

//	public ResultSet getSanphamRs() {
//		return SanphamRs;
//	}
//
//	public void setSanphamRs(ResultSet sanphamRs) {
//		SanphamRs = sanphamRs;
//	}

	public String getNgaychungtu() {
		return ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) {
		this.ngaychungtu = ngaychungtu;
	}

	public String getKehoach() {
		return kehoach;
	}

	public void setKehoach(String kehoach) {
		this.kehoach = kehoach;
	}

	public String getNguoiphache() {
		return nguoiphache;
	}

	public void setNguoiphache(String nguoiphache) {
		this.nguoiphache = nguoiphache;
	}

	public String getTailieuphache() {
		return tailieuphache;
	}

	public void setTailieuphache(String tailieuphache) {
		this.tailieuphache = tailieuphache;
	}

	public String getCongthucphache() {
		return congthucphache;
	}

	public void setCongthucphache(String congthucphache) {
		this.congthucphache = congthucphache;
	}

	public String getKhonhap() {
		return khonhap;
	}

	public void setKhonhap(String khonhap) {
		this.khonhap = khonhap;
	}

	public String getSoluongnhap() {
		return soluongnhap;
	}

	public void setSoluongnhap(String soluongnhap) {
		this.soluongnhap = soluongnhap;
	}

	public List<IErpPhieuPhaChe_SanPham> getSanphamList() {
		return SanphamList;
	}

	public void setSanphamList(List<IErpPhieuPhaChe_SanPham> sanphamList) {
		SanphamList = sanphamList;
	}

	public ResultSet getKehoachRs() {
		return KehoachRs;
	}

	public void setKehoachRs(ResultSet kehoachRs) {
		KehoachRs = kehoachRs;
	}

	public ResultSet getTailieuphacheRs() {
		return TailieuphacheRs;
	}

	public void setTailieuphacheRs(ResultSet tailieuphacheRs) {
		TailieuphacheRs = tailieuphacheRs;
	}

	public ResultSet getKhoRs() {
		return KhoRs;
	}

	public void setKhoRs(ResultSet khoRs) {
		KhoRs = khoRs;
	}

	public List<ThongTinNhapKho> getThongTinNhapKhoList() {
		return ThongTinNhapKhoList;
	}
	public void setThongTinNhapKhoList(List<ThongTinNhapKho> thongTinNhapKhoList) {
		ThongTinNhapKhoList = thongTinNhapKhoList;
	}

	public String getKehoachCt() {
		return kehoachCt;
	}
	public void setKehoachCt(String kehoachCt) {
		this.kehoachCt = kehoachCt;
	}

	public ResultSet getKehoachChitietRs() {
		return KehoachChitietRs;
	}
	public void setKehoachChitietRs(ResultSet kehoachChitietRs) {
		KehoachChitietRs = kehoachChitietRs;
	}

	public ResultSet getBinRs() {
		return BinRs;
	}
	public void setBinRs(ResultSet binRs) {
		BinRs = binRs;
	}

	public String getThuocloai() {
		return thuocloai;
	}

	public void setThuocloai(String thuocloai) {
		this.thuocloai = thuocloai;
	}

	public String getSanphamId() {
		return sanphamId;
	}

	public void setSanphamId(String sanphamId) {
		this.sanphamId = sanphamId;
	}

	public String getSpIsKqlsl() {
		return spIsKqlsl;
	}

	public void setSpIsKqlsl(String spIsKqlsl) {
		this.spIsKqlsl = spIsKqlsl;
	}
}
