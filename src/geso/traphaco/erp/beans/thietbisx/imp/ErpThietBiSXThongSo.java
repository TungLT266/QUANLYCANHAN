package geso.traphaco.erp.beans.thietbisx.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.beans.thietbisx.IErpThietBiSXThongSo;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpThietBiSXThongSo implements IErpThietBiSXThongSo {
	private String userId;
	private String congtyId;
	private String id;
	private String stt;
	private String ma;
	private String isChangeMa;
	private String ten;
	private String tscdFk;
	private String trangThai;
	private String msg;
	private String[] dienGiai;
	private String[] TsycTu;
	private String[] TsycDen;
	private String[] dvdlFk;
	private String[] check;
	
	ResultSet tscdRs;
	ResultSet dvtRs;
	dbutils db;
	
	public ErpThietBiSXThongSo() {
		this.id = "";
		this.stt = "";
		this.ma = "";
		this.isChangeMa = "";
		this.ten = "";
		this.tscdFk = "";
		this.trangThai = "1";
		this.msg = "";
		
		this.db = new dbutils();
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
	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String[] getTsycTu() {
		return TsycTu;
	}
	public void setTsycTu(String[] tsycTu) {
		TsycTu = tsycTu;
	}
	public String[] getTsycDen() {
		return TsycDen;
	}
	public void setTsycDen(String[] tsycDen) {
		TsycDen = tsycDen;
	}
	public String[] getDvdlFk() {
		return dvdlFk;
	}
	public void setDvdlFk(String[] dvdlFk) {
		this.dvdlFk = dvdlFk;
	}
	public String[] getCheck() {
		return check;
	}
	public void setCheck(String[] check) {
		this.check = check;
	}
	public ResultSet getDvtRs() {
		return dvtRs;
	}
	public void setDvtRs(ResultSet dvtRs) {
		this.dvtRs = dvtRs;
	}
	
	public void checkInGiaiDoanSX(){
		try{
			String query = "select count(*) as count from ERP_GIAIDOANSX_THIETBI where thietbisx_fk = " + this.id;
			
			ResultSet rs = this.db.get(query);
			rs.next();
			int count = rs.getInt("count");
			rs.close();
			if(count > 0){
				this.isChangeMa = "1";
			}
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void init() {
		try {
			checkInGiaiDoanSX();
			
			String query = "select case when loai_TSCD=1 then 'TSCD'+cast(TSCD_FK as varchar) when loai_TSCD=0 then 'CPTT'+cast(TSCD_FK as varchar) else '' end as tscd_fk,"
					+ " ma, ten, trangthai from erp_thietbisx where pk_seq = '" + this.id + "'";
			
			ResultSet rs = this.db.get(query);
			rs.next();
			this.ma =  rs.getString("ma");
			this.ten = rs.getString("ten");
			this.tscdFk = rs.getString("tscd_fk");
			this.trangThai = rs.getString("trangthai");
			
			rs.close();
			
			String _TBDIENGIAI = "";
			String _TBTU = "";
			String _TBDEN = "";
			String _TBDVDL = "";
			String _TBCHECK = "";
			query = "select DIENGIAI, THONGSOYEUCAUTU, THONGSOYEUCAUDEN, DVDL_FK, ISCHECK "
					+ "from ERP_THIETBISX_THONGSO  where THIETBI_FK = " + this.id;
			rs = db.get(query);
			
			//int dem = 0;
			while(rs.next()){
				if(rs.getString("DIENGIAI")==null){
					_TBDIENGIAI += " __";
				} else if(rs.getString("DIENGIAI").trim().length() > 0) {
					_TBDIENGIAI += rs.getString("DIENGIAI") + "__";
				} else {
					_TBDIENGIAI += " __";
				}
				
				if(rs.getString("THONGSOYEUCAUTU")==null){
					_TBTU += " __";
				} else if(rs.getString("THONGSOYEUCAUTU").trim().length() > 0) {
					_TBTU += rs.getString("THONGSOYEUCAUTU") + "__";
				} else {
					_TBTU += " __";
				}
				
				if(rs.getString("THONGSOYEUCAUDEN")==null){
					_TBDEN += " __";
				} else if(rs.getString("THONGSOYEUCAUDEN").trim().length() > 0) {
					_TBDEN += rs.getString("THONGSOYEUCAUDEN") + "__";
				} else {
					_TBDEN += " __";
				}
				
				if(rs.getString("DVDL_FK")==null){
					_TBDVDL += " __";
				} else if(rs.getString("DVDL_FK").trim().length() > 0) {
					_TBDVDL += rs.getString("DVDL_FK") + "__";
				} else {
					_TBDVDL += " __";
				}
				
				if(rs.getString("ISCHECK")==null){
					_TBCHECK += "0__";
				} else if(rs.getString("ISCHECK").trim().length() > 0) {
					_TBCHECK += rs.getString("ISCHECK") + "__";
				} else {
					_TBCHECK += "0__";
				}
			}
			rs.close();
			
			_TBDIENGIAI = _TBDIENGIAI.substring(0, _TBDIENGIAI.length() - 2);
			this.dienGiai = _TBDIENGIAI.split("__");
			
			_TBTU = _TBTU.substring(0, _TBTU.length() - 2);
			this.TsycTu = _TBTU.split("__");
			
			_TBDEN = _TBDEN.substring(0, _TBDEN.length() - 2);
			this.TsycDen = _TBDEN.split("__");
			
			_TBDVDL = _TBDVDL.substring(0, _TBDVDL.length() - 2);
			this.dvdlFk = _TBDVDL.split("__");
			
			_TBCHECK = _TBCHECK.substring(0, _TBCHECK.length() - 2);
			this.check = _TBCHECK.split("__");
		} catch(Exception e) {
			System.out.println("__Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void creaters() {
		String query = "select pk_seq, donvi from donvidoluong";
		this.dvtRs = db.getScrol(query);
		
		query = "select 'TSCD' + cast(pk_seq as varchar) as pk_seq, 'TSCD - ' + ma + ' - ' + diengiai as tscd"
				+ " from ERP_TAISANCODINH as a where trangthai=1 and congty_fk=" + this.congtyId
				+ " union all"
				+ " select 'CPTT' + cast(pk_seq as varchar) as pk_seq, 'CPTT - ' + ma + ' - ' + diengiai as tscd"
				+ " from ERP_CONGCUDUNGCU where TRANGTHAI=1  and CONGTY_FK="+this.congtyId;
		this.tscdRs = db.get(query);
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
	
	// Kiểm tra mã đã được sử dụng
	public boolean CheckUnique()
	{
		try{
			String query = "select count(*) as count from ERP_THIETBISX where MA=N'" + this.ma + "'";
			if (this.id.length() > 0)
				query += " and PK_SEQ !='" + this.id + " '";
			
			ResultSet rs = this.db.get(query);
			rs.next();
			int count = rs.getInt("count");
			rs.close();
			if (count > 0)
				return false;
			
			/*if (rs != null)
				try {
					while (rs.next()) {
						count = rs.getInt("count");
					}
					rs.close();
					if (count > 0)
						return false;
				} catch(SQLException e) {
					e.printStackTrace();
					return false;
				}*/
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
			
			if(this.ma.trim().length() <= 0) {
				this.msg = "Vui lòng nhập mã";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String loaiTSCD = "";
			String idtscd = "";
			if (this.tscdFk.startsWith("TSCD")){
				loaiTSCD = "1";
				idtscd = this.tscdFk.replace("TSCD", "");
			} else {
				loaiTSCD = "0";
				idtscd = this.tscdFk.replace("CPTT", "");
			}
			
			String query = "insert into ERP_THIETBISX(MA,TEN,TSCD_FK,loai_TSCD,TRANGTHAI,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA,CONGTY_FK)"
					+ " values('"+this.ma+"',N'"+this.ten+"','"+idtscd+"','"+loaiTSCD+"',"
					+ "'"+this.trangThai+"','"+this.userId+"','"+this.userId+"','"+this.getDateTime()+"','"+this.getDateTime()+"','"+this.congtyId+"')";
			System.out.println("1.Insert: " + query);
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới ERP_THIETBISX: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ResultSet rs = db.get("select SCOPE_IDENTITY() as ID ");
			String id = "";
			if(rs.next())
				id = rs.getString("ID");
			
			rs.close();
			
			for(int i=0; i<dienGiai.length; i++) {
				if(dienGiai[i].trim().length() > 0) {
					query = "insert into ERP_THIETBISX_THONGSO(THIETBI_FK,DIENGIAI,THONGSOYEUCAUTU,THONGSOYEUCAUDEN,DVDL_FK,ISCHECK) "
							+ "values('"+id+"',N'"+dienGiai[i]+"',"
							+ "case when '"+TsycTu[i].trim()+"'='' then null else '"+TsycTu[i]+"' end,"
							+ "case when '"+TsycDen[i].trim()+"'='' then null else '"+TsycDen[i]+"' end,"
							+ "case when '"+dvdlFk[i].trim()+"'='' then null else '"+dvdlFk[i]+"' end,'"+check[i]+"')";
					
					System.out.println("2.Insert: " + query);
					if(!db.update(query)) {
						this.msg = "Không thể thêm ERP_THIETBISX_THONGSO: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		return true;
	}
	
	public boolean update() {
		try {
//			if(!checkInGiaiDoanSX())
//				return false;
			
			if (!CheckUnique()) {
				this.msg = "Mã này đã được sử dụng, vui lòng nhập mã khác";
				return false;
			}
			
			if(this.ma.trim().length() <= 0) {
				this.msg = "Vui lòng nhập mã cụm sản xuất";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String loaiTSCD = "";
			String idtscd = "";
			if (this.tscdFk.startsWith("TSCD")){
				loaiTSCD = "1";
				idtscd = this.tscdFk.replace("TSCD", "");
			} else {
				loaiTSCD = "0";
				idtscd = this.tscdFk.replace("CPTT", "");
			}
			
			String query = "update ERP_THIETBISX set MA='"+this.ma+"',TEN=N'"+this.ten+"',TSCD_FK='"+idtscd+"',loai_TSCD='"+loaiTSCD+"',"
					+ "TRANGTHAI='"+this.trangThai+"',NGUOISUA='"+this.userId+"',NGAYSUA='"+this.getDateTime()+"' where pk_seq=" + this.id;
			System.out.println("1.Update: " + query);
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_THIETBISX: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_THIETBISX_THONGSO where THIETBI_FK=" + this.id;
			if(!db.update(query)) {
				this.msg = "Không thể xóa cập nhật ERP_THIETBISX_THONGSO: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i=0; i<dienGiai.length; i++) {
				if(dienGiai[i].trim().length() > 0) {
					query = "insert into ERP_THIETBISX_THONGSO(THIETBI_FK,DIENGIAI,THONGSOYEUCAUTU,THONGSOYEUCAUDEN,DVDL_FK,ISCHECK) "
							+ "values('"+this.id+"',N'"+dienGiai[i]+"',"
							+ "case when "+TsycTu[i].trim().length()+">0 then '"+TsycTu[i]+"' else null end,"
							+ "case when "+TsycDen[i].trim().length()+">0 then '"+TsycDen[i]+"' else null end,"
							+ "case when "+dvdlFk[i].trim().length()+">0 then '"+dvdlFk[i]+"' else null end,'"+check[i]+"')";
					
					System.out.println("2.Insert: " + query);
					if(!db.update(query)) {
						this.msg = "Không thể thêm ERP_THIETBISX_THONGSO: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String[] getDienGiai() {
		return dienGiai;
	}

	public void setDienGiai(String[] dienGiai) {
		this.dienGiai = dienGiai;
	}

	public String getTscdFk() {
		return tscdFk;
	}

	public void setTscdFk(String tscdFk) {
		this.tscdFk = tscdFk;
	}

	public ResultSet getTscdRs() {
		return tscdRs;
	}
	public void setTscdRs(ResultSet tscdRs) {
		this.tscdRs = tscdRs;
	}

	public String getStt() {
		return stt;
	}

	public void setStt(String stt) {
		this.stt = stt;
	}

	public String getIsChangeMa() {
		return isChangeMa;
	}
	public void setIsChangeMa(String isChangeMa) {
		this.isChangeMa = isChangeMa;
	}
}
