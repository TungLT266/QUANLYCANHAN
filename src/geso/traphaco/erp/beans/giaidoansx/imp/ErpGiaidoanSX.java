package geso.traphaco.erp.beans.giaidoansx.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.erp.beans.giaidoansx.IErpGiaiDoanSXThongSo;
import geso.traphaco.erp.beans.giaidoansx.IErpGiaidoanSX;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpGiaidoanSX implements IErpGiaidoanSX {
	private String userId;
	private String congtyId;
	private String id;
	private String ma;
	private String diengiai;
	private String solannhap;
	private String giaodiennhap;
	private String loaisanpham;
	private String isAllBom;
	private String soluongmau;
	private String loaimauinId;
	private String trangthai;
	private String msg;
	
	private List<IErpGiaiDoanSXThongSo> thongSoList;
	
	private ResultSet LoaisanphamRs;
	private ResultSet loaimauinSxRs;
	private ResultSet tscdRs;
	private ResultSet dvtRs;
	dbutils db;
	
	public ErpGiaidoanSX() {
		this.userId = "";
		this.id = "";
		this.ma = "";
		this.diengiai = "";
		this.solannhap = "";
		this.giaodiennhap = "";
		this.loaisanpham = "";
		this.isAllBom = "";
		this.soluongmau = "";
		this.loaimauinId = "";
		this.trangthai = "1";
		this.msg = "";
		
		this.thongSoList = new ArrayList<IErpGiaiDoanSXThongSo>();
		this.db = new dbutils();
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(){
		try {
			String query = "select ma, ten, giaodiennhap,isnull(isallbomlsx,0) as isallbomlsx, isnull(SOLUONGMAU,'') as SOLUONGMAU, trangthai, LOAIMAUINSX_FK from ERP_GIAIDOANSX where PK_SEQ = '" + this.id + "'";
			
			ResultSet rs = db.get(query);
			while(rs.next()) {
				this.ma =  rs.getString("ma");
				this.diengiai = rs.getString("ten");
				this.giaodiennhap = rs.getString("giaodiennhap");
				this.isAllBom = rs.getString("isallbomlsx");
				
				if(this.giaodiennhap.equals("0")){
					this.solannhap = rs.getString("SOLUONGMAU");
				} else if(this.giaodiennhap.equals("2")){
					this.soluongmau = rs.getString("SOLUONGMAU");
				}
				this.trangthai = rs.getString("trangthai");
				this.loaimauinId = rs.getString("LOAIMAUINSX_FK");
			}
			rs.close();
			
			query = "select LOAISANPHAM_FK from ERP_GIAIDOANSX_LOAISANPHAM where GIAIDOANSX_FK=" + this.id;
			rs = this.db.get(query);
			
			while(rs.next()){
				this.loaisanpham += rs.getString("LOAISANPHAM_FK") + ",";
			}
			if(this.loaisanpham.length() > 0)
				this.loaisanpham = this.loaisanpham.substring(0, this.loaisanpham.length() - 1);
			
			rs.close();
			
			query = "select PK_SEQ, (case when loai=0 then 'TSCD'+cast(TSCD_CCDC_FK as varchar) when loai=1 then 'CPTT'+cast(TSCD_CCDC_FK as varchar) else '0' end) as tscd,"
					+ " isnull(ThietBiSX_FK,'0') as thietbi, isnull(ThongSoChung,'') as thongsochung from ERP_GIAIDOANSX_THIETBI where GIAIDOAN_FK='"+this.id+"' order by STT";
			rs = db.get(query);
			IErpGiaiDoanSXThongSo thongSo;
			List<ErpGiaiDoanSX_TS_ChiTiet> tbsxThongsoList;
			ErpGiaiDoanSX_TS_ChiTiet tbsxThongso;
			ResultSet rsthongso;
			
			while(rs.next()){
				thongSo = new ErpGiaiDoanSXThongSo();
				
				thongSo.setTscdId(rs.getString("tscd"));
				thongSo.setThietBiSXId(rs.getString("thietbi"));
				thongSo.setThongSoChung(rs.getString("thongsochung"));
				
				query = "select THONGSOKYTHUAT, yeucau, thongsotu, thongsoden, isnull(DVDL_FK,0) as dvt, ischeck"
						+ " from ERP_GIAIDOANSX_THIETBI_CHITIET where GIAIDOAN_TB_FK='"+rs.getString("PK_SEQ")+"' order by stt";
				
				rsthongso = db.get(query);
				tbsxThongsoList = new ArrayList<ErpGiaiDoanSX_TS_ChiTiet>();
				while(rsthongso.next()){
					tbsxThongso = new ErpGiaiDoanSX_TS_ChiTiet();
					
					tbsxThongso.setDienGiai(rsthongso.getString("THONGSOKYTHUAT"));
					tbsxThongso.setYeucau(rsthongso.getString("yeucau"));
					tbsxThongso.setThongsoTu(rsthongso.getString("thongsotu"));
					tbsxThongso.setThongsoDen(rsthongso.getString("thongsoden"));
					tbsxThongso.setDonVi(rsthongso.getString("dvt"));
					tbsxThongso.setTick(rsthongso.getString("ischeck"));
					
					tbsxThongsoList.add(tbsxThongso);
				}
				rsthongso.close();
				thongSo.setTbsxThongsoList(tbsxThongsoList);
				
				thongSo.init();
				
				this.thongSoList.add(thongSo);
			}
			rs.close();
		} catch (Exception e) {
			System.out.println("__Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		createRs();
	}
	
	public boolean CheckUnique() {
		try{
			String query = "select count(*) as count from ERP_GIAIDOANSX where MA='" + this.ma + "'";
			if (this.id.length() > 0)
				query += " and PK_SEQ !='" + this.id + "'";
			
			ResultSet rs = this.db.get(query);
			rs.next();
			int count = rs.getInt("count");
			rs.close();
			if (count > 0)
				return false;
			
			return true;
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean create() {
		try {
			if (!CheckUnique()) {
				this.msg = "Mã này đã được sử dụng, vui lòng nhập mã khác";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			if(!this.giaodiennhap.equals("0")){
				this.isAllBom = null;
			}
			
			String soluongmaunhap = "";
			if(this.giaodiennhap.equals("0")){
				soluongmaunhap = this.solannhap;
			} else if(this.giaodiennhap.equals("2")){
				soluongmaunhap = this.soluongmau;
			}
			
			String query = "insert into ERP_GIAIDOANSX(MA, TEN, GIAODIENNHAP, isallbomlsx, SOLUONGMAU, TRANGTHAI, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, CONGTY_FK, LOAIMAUINSX_FK)"
					+ " values('"+this.ma+"', N'"+this.diengiai+"', '"+this.giaodiennhap+"', "+this.isAllBom+", '"+soluongmaunhap+"', '"+this.trangthai+"', '"+this.userId+"',"
					+ " '"+this.userId+"', '"+this.getDateTime()+"', '"+this.getDateTime()+"', '"+this.congtyId+"',"
					+ " case when '"+this.loaimauinId.length()+"' > 0 then '"+this.loaimauinId+"' else null end)";
			
			System.out.println("Create: " + query);
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới ERP_GIAIDOANSX: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ResultSet rs = db.get("select SCOPE_IDENTITY() as ID ");
			rs.next();
			this.id = rs.getString("ID");
			rs.close();
			
			if(this.loaisanpham.length() > 0){
				String[] chon = this.loaisanpham.split(",");
				for(int i = 0; i < chon.length; i++){
					query = "insert into ERP_GIAIDOANSX_LOAISANPHAM(GIAIDOANSX_FK,LOAISANPHAM_FK) values('"+this.id+"','"+chon[i]+"')";
					System.out.println(query);
					if(!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_GIAIDOANSX_LOAISANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			IErpGiaiDoanSXThongSo thongSo;
			ErpGiaiDoanSX_TS_ChiTiet tbsxThongso;
			String loai;
			String idTscd;
			String idmoi;
			for(int i = 0; i < this.thongSoList.size(); i++){
				thongSo = thongSoList.get(i);
				
				if(thongSo.getTscdId().length() > 0){
					if (thongSo.getTscdId().startsWith("TSCD")){
						loai = "0";
						idTscd = thongSo.getTscdId().replace("TSCD", "");
					} else if (thongSo.getTscdId().startsWith("CPTT")){
						loai = "1";
						idTscd = thongSo.getTscdId().replace("CPTT", "");
					} else {
						loai = "2";
						idTscd = "0";
					}
					
					query = "insert into ERP_GIAIDOANSX_THIETBI(GIAIDOAN_FK, loai, tscd_ccdc_fk, ThietBiSX_FK, ThongSoChung, STT)"
							+ " values('"+this.id+"','"+loai+"','"+idTscd+"',case when '"+thongSo.getThietBiSXId()+"' = '' then null else '"+thongSo.getThietBiSXId()+"' end,N'"+thongSo.getThongSoChung()+"','"+(i+1)+"')";
					if(!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_GIAIDOANSX_THIETBI: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					rs = db.get("select SCOPE_IDENTITY() as ID ");
					rs.next();
					idmoi = rs.getString("ID");
					rs.close();
					
					for(int k = 0; k < thongSo.getTbsxThongsoList().size(); k++){
						tbsxThongso = thongSo.getTbsxThongsoList().get(k);
						
						if(tbsxThongso.getDienGiai().trim().length() > 0){
							query = "insert into ERP_GIAIDOANSX_THIETBI_CHITIET(GIAIDOAN_FK,GIAIDOAN_TB_FK,STT,THONGSOKYTHUAT,YEUCAU,THONGSOTU,THONGSODEN,DVDL_FK,ISCHECK)"
									+ " values('"+this.id+"','"+idmoi+"','"+(k+1)+"',N'"+tbsxThongso.getDienGiai()+"',N'"+tbsxThongso.getYeucau()+"',"
									+ "'"+tbsxThongso.getThongsoTu()+"','"+tbsxThongso.getThongsoDen()+"',case when "+tbsxThongso.getDonVi().length()+">0 then '"+tbsxThongso.getDonVi()+"' else null end,'"+tbsxThongso.getTick()+"')";
							
							if(!db.update(query)) {
								this.msg = "Không thể tạo mới ERP_GIAIDOANSX_THIETBI_CHITIET: " + query;
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
			if (!CheckUnique()) {
				this.msg = "Mã này đã được sử dụng, vui lòng nhập mã khác";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			if(!this.giaodiennhap.equals("0")){
				this.isAllBom = null;
			}
			
			String soluongmaunhap = "";
			if(this.giaodiennhap.equals("0")){
				soluongmaunhap = this.solannhap;
			} else if(this.giaodiennhap.equals("2")){
				soluongmaunhap = this.soluongmau;
			}
			
			String query = "update ERP_GIAIDOANSX set MA='"+this.ma+"',TEN=N'"+this.diengiai+"',GIAODIENNHAP='"+this.giaodiennhap+"',isallbomlsx="+this.isAllBom+","
					+ "SOLUONGMAU='"+soluongmaunhap+"',TRANGTHAI='"+this.trangthai+"',NGUOISUA='"+this.userId+"',NGAYSUA='"+this.getDateTime()+"',CONGTY_FK='"+this.congtyId+"',"
					+ " LOAIMAUINSX_FK = case when '"+this.loaimauinId.length()+"' > 0 then '"+this.loaimauinId+"' else null end where PK_SEQ = " + this.id;
			
			System.out.println("Update: " + query);
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_GIAIDOANSX: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ResultSet rs;
			IErpGiaiDoanSXThongSo thongSo;
			ErpGiaiDoanSX_TS_ChiTiet tbsxThongso;
			String loai;
			String idTscd;
			String idmoi;
			
			query = "delete ERP_GIAIDOANSX_LOAISANPHAM where GIAIDOANSX_FK = " + this.id;
			if(!db.update(query)){
				this.msg = "Không thể xóa cập nhật ERP_GIAIDOANSX_LOAISANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_GIAIDOANSX_THIETBI where GIAIDOAN_FK = " + this.id;
			if(!db.update(query)){
				this.msg = "Không thể xóa cập nhật ERP_GIAIDOANSX_THIETBI " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_GIAIDOANSX_THIETBI_CHITIET where GIAIDOAN_FK = " + this.id;
			if(!db.update(query)){
				this.msg = "Không thể xóa cập nhật ERP_GIAIDOANSX_THIETBI_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.loaisanpham.length() > 0){
				String[] chon = this.loaisanpham.split(",");
				for(int i = 0; i < chon.length; i++){
					query = "insert into ERP_GIAIDOANSX_LOAISANPHAM(GIAIDOANSX_FK,LOAISANPHAM_FK) values('"+this.id+"','"+chon[i]+"')";
					System.out.println(query);
					if(!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_GIAIDOANSX_LOAISANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			for(int i = 0; i < this.thongSoList.size(); i++){
				thongSo = thongSoList.get(i);
				
				if(thongSo.getTscdId().length() > 0){
					if (thongSo.getTscdId().startsWith("TSCD")){
						loai = "0";
						idTscd = thongSo.getTscdId().replace("TSCD", "");
					} else if (thongSo.getTscdId().startsWith("CPTT")){
						loai = "1";
						idTscd = thongSo.getTscdId().replace("CPTT", "");
					} else {
						loai = "2";
						idTscd = "0";
					}
					
					query = "insert into ERP_GIAIDOANSX_THIETBI(GIAIDOAN_FK, loai, tscd_ccdc_fk, ThietBiSX_FK, ThongSoChung, STT)"
							+ " values('"+this.id+"','"+loai+"','"+idTscd+"',case when '"+thongSo.getThietBiSXId()+"' = '' then null else '"+thongSo.getThietBiSXId()+"' end,N'"+thongSo.getThongSoChung()+"','"+(i+1)+"')";
					if(!db.update(query)) {
						this.msg = "Không thể cập nhật ERP_GIAIDOANSX_THIETBI: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					rs = db.get("select SCOPE_IDENTITY() as ID ");
					rs.next();
					idmoi = rs.getString("ID");
					rs.close();
					
					for(int k = 0; k < thongSo.getTbsxThongsoList().size(); k++){
						tbsxThongso = thongSo.getTbsxThongsoList().get(k);
						
						if(tbsxThongso.getDienGiai().trim().length() > 0){
							query = "insert into ERP_GIAIDOANSX_THIETBI_CHITIET(GIAIDOAN_FK,GIAIDOAN_TB_FK,STT,THONGSOKYTHUAT,YEUCAU,THONGSOTU,THONGSODEN,DVDL_FK,ISCHECK)"
									+ " values('"+this.id+"','"+idmoi+"','"+(k+1)+"',N'"+tbsxThongso.getDienGiai()+"',N'"+tbsxThongso.getYeucau()+"',"
									+ "'"+tbsxThongso.getThongsoTu()+"','"+tbsxThongso.getThongsoDen()+"',case when "+tbsxThongso.getDonVi().length()+">0 then '"+tbsxThongso.getDonVi()+"' else null end,'"+tbsxThongso.getTick()+"')";
							
							if(!db.update(query)) {
								this.msg = "Không thể tạo mới ERP_GIAIDOANSX_THIETBI_CHITIET: " + query;
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
	
	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	public void DbClose() {
		try {
			if(this.tscdRs != null)
				this.tscdRs.close();
			if(this.dvtRs != null)
				this.dvtRs.close();
			this.db.shutDown();
		} catch (SQLException e) {}
	}
	
	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public void createRs() {
		String query = "select '0' as pk_seq, N'Không thiết bị' as ten"
				+ "	union all"
				+ " select 'TSCD' + cast(pk_seq as varchar) as pk_seq, 'TSCD - ' + ma + ' - ' + diengiai as ten"
				+ " from ERP_TAISANCODINH as a where trangthai=1  and congty_fk="+this.congtyId
				+ " union all"
				+ " select 'CPTT' + cast(pk_seq as varchar) as pk_seq, 'CPTT - ' + ma + ' - ' + diengiai as ten"
				+ " from ERP_CONGCUDUNGCU where TRANGTHAI=1  and CONGTY_FK="+this.congtyId;
		
		/*query = "select '0' as pk_seq, N'Không thiết bị' as ten"
				+ " union all"
				+ " select 'TSCD' + cast(pk_seq as varchar) pk_seq, 'TSCĐ' + ' - ' + ma + ' - ' + a.diengiai ten from erp_taisancodinh a"
				+ " where a.trangthai = 1 and pk_seq in (select taisancodinh_fk from erp_taisancodinh_khoanmucchiphi a"
				+ " inner join erp_nhomchiphi b on a.khoanmucchiphi_fk = b.pk_seq"
				+ " inner join erp_taikhoankt c on b.taikhoan_fk = c.pk_seq"
				+ " where c.SOHIEUTAIKHOAN like '627%') and congty_fk = " + this.congtyId
				+ " union all"
				+ " select 'CPTT' + cast(pk_seq as varchar) pk_seq, 'CPTT' + ' - ' + ma + ' - ' + a.diengiai ten from erp_congcudungcu a"
				+ " where a.trangthai = 1 and pk_seq in (select ccdc_fk from erp_congcudungcu_khoanmucchiphi a"
				+ " inner join erp_nhomchiphi b on a.nhomchiphi_fk = b.pk_seq"
				+ " inner join erp_taikhoankt c on b.taikhoan_fk = c.pk_seq"
				+ " where c.SOHIEUTAIKHOAN like '627%') and a.congty_fk = " + this.congtyId;*/
		
		this.tscdRs = db.getScrol(query);
		
		query = "select pk_seq, maloai + ' - ' + tenloai as Ten from LOAIMAUIN_SANXUAT where trangthai = 1";
		this.loaimauinSxRs = db.get(query);
		
		if(thongSoList.size() > 0){
			query = "select pk_seq, donvi from donvidoluong";
			this.dvtRs = db.getScrol(query);
		}
		
		query = "select PK_SEQ,MA+' - '+TEN as ten from ERP_LOAISANPHAM where TRANGTHAI='1' and isBom='1'";
		this.LoaisanphamRs = this.db.get(query);
	}

	@Override
	public ResultSet getDvtRs() {
		return this.dvtRs;
	}

	@Override
	public void setDvtRs(ResultSet dvtRs) {
		this.dvtRs = dvtRs;
	}

	public ResultSet getTscdRs() {
		return tscdRs;
	}

	public void setTscdRs(ResultSet tscdRs) {
		this.tscdRs = tscdRs;
	}

	public List<IErpGiaiDoanSXThongSo> getThongSoList() {
		return thongSoList;
	}

	public void setThongSoList(List<IErpGiaiDoanSXThongSo> thongSoList) {
		this.thongSoList = thongSoList;
	}

	public String getGiaodiennhap() {
		return giaodiennhap;
	}
	public void setGiaodiennhap(String giaodiennhap) {
		this.giaodiennhap = giaodiennhap;
	}

	public ResultSet getLoaimauinSxRs() {
		return loaimauinSxRs;
	}
	public void setLoaimauinSxRs(ResultSet loaimauinSxRs) {
		this.loaimauinSxRs = loaimauinSxRs;
	}

	public String getLoaimauinId() {
		return loaimauinId;
	}
	public void setLoaimauinId(String loaimauinId) {
		this.loaimauinId = loaimauinId;
	}

	public String getLoaisanpham() {
		return loaisanpham;
	}

	public void setLoaisanpham(String loaisanpham) {
		this.loaisanpham = loaisanpham;
	}

	public String getIsAllBom() {
		return isAllBom;
	}

	public void setIsAllBom(String isAllBom) {
		this.isAllBom = isAllBom;
	}

	public ResultSet getLoaisanphamRs() {
		return LoaisanphamRs;
	}

	public void setLoaisanphamRs(ResultSet loaisanphamRs) {
		LoaisanphamRs = loaisanphamRs;
	}

	public String getSoluongmau() {
		return soluongmau;
	}
	public void setSoluongmau(String soluongmau) {
		this.soluongmau = soluongmau;
	}

	public String getSolannhap() {
		return solannhap;
	}

	public void setSolannhap(String solannhap) {
		this.solannhap = solannhap;
	}
}
