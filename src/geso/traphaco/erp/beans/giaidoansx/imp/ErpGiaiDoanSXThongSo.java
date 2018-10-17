package geso.traphaco.erp.beans.giaidoansx.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.giaidoansx.IErpGiaiDoanSXThongSo;
import geso.traphaco.erp.beans.giaidoansx.imp.ErpGiaiDoanSX_TS_ChiTiet;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpGiaiDoanSXThongSo implements IErpGiaiDoanSXThongSo {
	private String tscdId;
	private String thietBiSXId;
	private String thongSoChung;
	
	List<ErpGiaiDoanSX_TS_ChiTiet> tbsxThongsoList;
	ResultSet thietBiSXRs;
	dbutils db;
	
	public ErpGiaiDoanSXThongSo() {
		this.tscdId = "";
		this.thietBiSXId = "";
		this.thongSoChung = "";
		
		tbsxThongsoList = new ArrayList<ErpGiaiDoanSX_TS_ChiTiet>();
		this.db = new dbutils();
	}
	
	public void createThietbisxThongso(){
		if(thietBiSXId.trim().length() > 0){
			ErpGiaiDoanSX_TS_ChiTiet tbsxThongso;
			
			String query = "select STT, DIENGIAI, THONGSOYEUCAUTU, THONGSOYEUCAUDEN, DVDL_FK, ISCHECK"
					+ " from ERP_THIETBISX_THONGSO where THIETBI_FK=" + this.thietBiSXId
					+ " order by STT";
			ResultSet tbsxThongsoRs = this.db.get(query);
			
			try{
				if(tbsxThongsoRs != null){
					while(tbsxThongsoRs.next()){
						tbsxThongso = new ErpGiaiDoanSX_TS_ChiTiet();
						
						tbsxThongso.setDienGiai(tbsxThongsoRs.getString("DIENGIAI"));
						
						if(tbsxThongsoRs.getString("THONGSOYEUCAUTU") != null)
							tbsxThongso.setThongsoTu(tbsxThongsoRs.getString("THONGSOYEUCAUTU"));
						
						if(tbsxThongsoRs.getString("THONGSOYEUCAUDEN") != null)
							tbsxThongso.setThongsoDen(tbsxThongsoRs.getString("THONGSOYEUCAUDEN"));
						
						if(tbsxThongsoRs.getString("DVDL_FK") != null)
							tbsxThongso.setDonVi(tbsxThongsoRs.getString("DVDL_FK"));
						
						if(tbsxThongsoRs.getString("ISCHECK") != null)
							tbsxThongso.setTick(tbsxThongsoRs.getString("ISCHECK"));
						
						this.tbsxThongsoList.add(tbsxThongso);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void init(){
		if(this.tscdId.startsWith("TSCD")){
			String tscdId = this.tscdId.replace("TSCD", "");
			String query = "select PK_SEQ, TEN from ERP_THIETBISX where TRANGTHAI=1 and loai_TSCD = '1' and TSCD_FK=" + tscdId;
			this.thietBiSXRs = this.db.get(query);
		} else if(this.tscdId.startsWith("CPTT")){
			String tscdId = this.tscdId.replace("CPTT", "");
			String query = "select PK_SEQ, TEN from ERP_THIETBISX where TRANGTHAI=1 and loai_TSCD = '0' and TSCD_FK=" + tscdId;
			this.thietBiSXRs = this.db.get(query);
		}
	}
	
	public void DbClose() {
		try {
			if(this.thietBiSXRs != null)
				this.thietBiSXRs.close();
			this.db.shutDown();
		} catch (SQLException e) {}
	}
	
	public String getTscdId() {
		return tscdId;
	}
	public void setTscdId(String tscdId) {
		this.tscdId = tscdId;
	}
	public String getThietBiSXId() {
		return thietBiSXId;
	}
	public void setThietBiSXId(String thietBiSXId) {
		this.thietBiSXId = thietBiSXId;
	}
	public String getThongSoChung() {
		return thongSoChung;
	}
	public void setThongSoChung(String thongSoChung) {
		this.thongSoChung = thongSoChung;
	}

	public ResultSet getThietBiSXRs() {
		return thietBiSXRs;
	}

	public void setThietBiSXRs(ResultSet thietBiSXRs) {
		this.thietBiSXRs = thietBiSXRs;
	}

	public List<ErpGiaiDoanSX_TS_ChiTiet> getTbsxThongsoList() {
		return tbsxThongsoList;
	}

	public void setTbsxThongsoList(List<ErpGiaiDoanSX_TS_ChiTiet> tbsxThongsoList) {
		this.tbsxThongsoList = tbsxThongsoList;
	}
}
