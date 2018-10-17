package geso.traphaco.erp.beans.phieuphache.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.phieuphache.IErpPhieuPhaChe_SanPham;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpPhieuPhaChe_SanPham implements IErpPhieuPhaChe_SanPham {
	private String tlpcTtId;
	private String idsp;
	private String masp;
	private String tensp;
	private String dvt;
	private String khoxuat;
	private String soluonglythuyet;
	private String tongxuat;
	
	private List<ErpPhieuPhaChe_SP_ChiTiet> SpChitietList;
	
	dbutils db;
	
	public ErpPhieuPhaChe_SanPham() { 
		this.tlpcTtId = "";
		this.idsp = "";
		this.masp = "";
		this.tensp = "";
		this.dvt = "";
		this.khoxuat = "";
		this.soluonglythuyet = "";
		this.tongxuat = "";
		
		this.SpChitietList = new ArrayList<ErpPhieuPhaChe_SP_ChiTiet>();
		
		this.db = new dbutils();
	}
	
	public void createSpChitietList(){
		try {
			String query = "select kspct.pk_seq, kspct.SOLO, kspct.NGAYHETHAN, kspct.NGAYNHAPKHO, kspct.MAME, kspct.MATHUNG,kspct.bin_fk, isnull(bin.MA,'') as vitri, kspct.MAPHIEU, kspct.MAPHIEUDINHTINH,"
					+ " kspct.PHIEUEO,kspct.marq, kspct.hamluong, kspct.hamam, kspct.loaidoituong, kspct.doituongid,kspct.NSX_FK, isnull(nsx.TEN,'') as nhasanxuat, kspct.AVAILABLE"
					+ " from ERP_KHOTT_SP_CHITIET kspct left join ERP_BIN bin on bin.PK_SEQ = kspct.BIN_FK left join ERP_NHASANXUAT nsx on nsx.PK_SEQ = kspct.NSX_FK"
					+ " where kspct.khott_fk='"+this.khoxuat+"' and kspct.sanpham_fk='"+this.idsp+"' and kspct.available>0";
			
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			if(rs != null){
				ErpPhieuPhaChe_SP_ChiTiet SpChitiet;
				while(rs.next()){
					SpChitiet = new ErpPhieuPhaChe_SP_ChiTiet();
					
					SpChitiet.setKhoSpCtId(rs.getString("pk_seq"));
					
					SpChitiet.setLoaidoituong(rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong"));
					SpChitiet.setDoituongid(rs.getString("doituongid") == null ? "" : rs.getString("doituongid"));
					SpChitiet.setSolo(rs.getString("solo"));
					SpChitiet.setNgayhethan(rs.getString("NGAYHETHAN"));
					SpChitiet.setNgaynhapkho(rs.getString("NGAYNHAPKHO"));
					SpChitiet.setMame(rs.getString("MAME"));
					SpChitiet.setMathung(rs.getString("MATHUNG"));
					SpChitiet.setBinFk(rs.getString("bin_fk"));
					SpChitiet.setMaphieu(rs.getString("MAPHIEU"));
					SpChitiet.setMaphieudinhtinh(rs.getString("MAPHIEUDINHTINH"));
					SpChitiet.setPhieueo(rs.getString("PHIEUEO"));
					SpChitiet.setMarq(rs.getString("marq"));
					SpChitiet.setHamluong(rs.getString("hamluong"));
					SpChitiet.setHamam(rs.getString("hamam"));
					SpChitiet.setNsxId(rs.getString("nsx_fk") == null ? "" : rs.getString("nsx_fk"));
					
					SpChitiet.setVitri(rs.getString("vitri"));
					SpChitiet.setNhasanxuat(rs.getString("nhasanxuat"));
					SpChitiet.setSoluongton(rs.getString("available"));
					
					this.SpChitietList.add(SpChitiet);
				}
			}
		} catch (SQLException e) {}
	}

	public String getIdsp() {
		return idsp;
	}

	public void setIdsp(String idsp) {
		this.idsp = idsp;
	}

	public String getMasp() {
		return masp;
	}

	public void setMasp(String masp) {
		this.masp = masp;
	}

	public String getTensp() {
		return tensp;
	}

	public void setTensp(String tensp) {
		this.tensp = tensp;
	}

	public String getDvt() {
		return dvt;
	}

	public void setDvt(String dvt) {
		this.dvt = dvt;
	}

	public String getKhoxuat() {
		return khoxuat;
	}

	public void setKhoxuat(String khoxuat) {
		this.khoxuat = khoxuat;
	}

	public String getSoluonglythuyet() {
		return soluonglythuyet;
	}

	public void setSoluonglythuyet(String soluonglythuyet) {
		this.soluonglythuyet = soluonglythuyet;
	}

	public List<ErpPhieuPhaChe_SP_ChiTiet> getSpChitietList() {
		return SpChitietList;
	}
	public void setSpChitietList(List<ErpPhieuPhaChe_SP_ChiTiet> spChitietList) {
		SpChitietList = spChitietList;
	}

	public String getTongxuat() {
		return tongxuat;
	}
	public void setTongxuat(String tongxuat) {
		this.tongxuat = tongxuat;
	}

	public String getTlpcTtId() {
		return tlpcTtId;
	}
	public void setTlpcTtId(String tlpcTtId) {
		this.tlpcTtId = tlpcTtId;
	}
}
