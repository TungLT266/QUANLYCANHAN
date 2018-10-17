package geso.traphaco.center.beans.bctoadonhanvien.imp;

import geso.traphaco.center.beans.bctoadonhanvien.IBCToadoNhanvien;
import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

public class BCToadoNhanvien implements IBCToadoNhanvien
{ 
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String nhanvienId;
	String vungId, khuvucId, loai;
	private ResultSet nhanvienRs, vungRs, KhuvucRs;
	private String msg;
	dbutils db;
	private String ttId;
	private ResultSet ttRs;
	private ResultSet nppRs;
	private String nppId;
	
	public BCToadoNhanvien()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.nhanvienId = "";
		this.vungId = "";
		this.khuvucId = "";
		this.msg = "";
		this.loai = "";
		this.ttId = "";
		this.db = new dbutils();
		this.nppId = "";
	}

	@Override
	public String getUserId() {
		
		return this.userId;
	}

	@Override
	public void setUserId(String userId) {
		
		this.userId = userId;
	}

	@Override
	public String getTungay() {
		
		return this.tungay;
	}

	@Override
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	@Override
	public String getDenngay() {
		
		return this.denngay;
	}

	@Override
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	@Override
	public void init() {
		Utility Ult = new Utility();
		this.vungRs = db.get("select pk_seq,ten,diengiai from vung ");
		String query = "select PK_SEQ, TEN from tinhthanh where 1=1 ";
		if (this.vungId.length() > 0)
		{
			this.KhuvucRs = db.get("select pk_seq,ten from khuvuc where vung_fk ='" + this.vungId + "'");
			query+=" and vung_fk = '" + this.vungId + "' ";
			
		} 
		else{
			String query_khuvuc=" select PK_SEQ, TEN from KHUVUC "
					+ "	where PK_SEQ in (select KHUVUC_Fk from NHAPHANPHOI "
					+ "	where pk_seq in "+ Ult.quyen_npp(userId)+")"; 			
			this.KhuvucRs= db.get(query_khuvuc);
		}
		this.ttRs= this.db.get(query + "  and pk_seq in  "+Ult.Quyen_TinhThanh(this.userId));
		
		String sql = "select distinct cast(a.PK_SEQ as varchar(18)) + '2' as PK_SEQ, 'ASM ' + a.TEN as TEN, 'ASM' AS CHUCVU, 2 AS LOAI from ASM a " +
					"inner join NHANVIEN nv on a.PK_SEQ = nv.ASM_FK " + 
					"WHERE a.TRANGTHAI = 1 and nv.TRANGTHAI = 1 ";

		if(this.loai.length() > 0)
			sql += " and 2 = "+this.loai;
		if(this.nhanvienId.length() > 0)
			sql += " AND cast(a.PK_SEQ as varchar(18)) + '2' = '"+this.nhanvienId+"' ";
					
		sql += "AND nv.PK_SEQ IN (SELECT NHANVIEN_FK FROM PHAMVIHOATDONG WHERE NPP_FK IN " +
					"( SELECT PK_SEQ FROM NHAPHANPHOI WHERE TRANGTHAI = 1 ";
		if(this.nppId.length() > 0)
			sql += " and PK_SEQ = '"+this.nppId+"' ";
		if(this.vungId.length() > 0)
			sql = sql + " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "')";
		if(this.ttId.length()>0){
			sql+=" and TinhThanh_fk = '"+this.ttId+"'";
		}
		sql += "))";
		sql +=
					"UNION ALL " + 
					"SELECT distinct cast(BM.PK_SEQ as varchar(18)) + '1' as PK_SEQ, 'BM ' + BM.TEN AS TEN, 'BM' AS CHUCVU, 1 AS LOAI FROM BM " +
					"inner join NHANVIEN nv on BM.PK_SEQ = nv.BM_FK " + 
					"WHERE BM.TRANGTHAI = 1 and nv.TRANGTHAI = 1 ";

		if(this.loai.length() > 0)
			sql += " and 1 = "+this.loai;
		if(this.nhanvienId.length() > 0)
			sql += " AND cast(BM.PK_SEQ as varchar(18)) + '1' = '"+this.nhanvienId+"' ";
					
		sql += "AND nv.PK_SEQ IN (SELECT NHANVIEN_FK FROM PHAMVIHOATDONG WHERE NPP_FK IN " +
					"( SELECT PK_SEQ FROM NHAPHANPHOI WHERE TRANGTHAI = 1 ";
		if(this.nppId.length() > 0)
			sql += " and PK_SEQ = '"+this.nppId+"' ";
		if(this.vungId.length() > 0)
			sql = sql + " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "')";
		if(this.ttId.length()>0){
			sql+=" and TinhThanh_fk = '"+this.ttId+"'";
		}
		sql += "))";
		
		sql +=
					"UNION ALL " + 
					"SELECT distinct cast(gs.PK_SEQ as varchar(18)) + '3' as PK_SEQ, 'SSM ' + gs.TEN AS TEN, 'SSM' AS CHUCVU, 3 AS LOAI FROM GIAMSATBANHANG gs " +
					"inner join NHANVIEN nv on gs.PK_SEQ = nv.GSBH_FK " + 
					"WHERE gs.TRANGTHAI = 1 and nv.TRANGTHAI = 1 ";

		if(this.loai.length() > 0)
			sql += " and 3 = "+this.loai;
		if(this.nhanvienId.length() > 0)
			sql += " AND cast(gs.PK_SEQ as varchar(18)) + '3' = '"+this.nhanvienId+"'";
					
		sql += "AND nv.PK_SEQ IN (SELECT NHANVIEN_FK FROM PHAMVIHOATDONG WHERE NPP_FK IN " +
					"( SELECT PK_SEQ FROM NHAPHANPHOI WHERE TRANGTHAI = 1 ";
		if(this.nppId.length() > 0)
			sql += " and PK_SEQ = '"+this.nppId+"' ";
		if(this.vungId.length() > 0)
			sql = sql + " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "')";
		if(this.ttId.length()>0){
			sql+=" and TinhThanh_fk = '"+this.ttId+"'";
		}
		sql += "))";
		
		System.out.println(sql);
		this.nhanvienRs = this.db.get(sql);
		
		sql = "select pk_seq,ten from nhaphanphoi where trangthai ='1' ";		
		if(this.ttId.length()>0){
			sql+=" and TinhThanh_fk='"+this.ttId+"'";
		}
		if (this.vungId.length() > 0)
		{
			sql = sql + " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "')";
		}
		sql += " order by ten ";
		this.nppRs = db.getScrol(sql);
	}

	@Override
	public void DBclose() {
		this.db.shutDown();
		
		try 
		{
			if(this.nhanvienRs != null)
				this.nhanvienRs.close();
			if(this.vungRs != null)
				this.vungRs.close();
			if(this.ttRs != null)
				this.ttRs.close();
			if(this.nppRs != null)
				this.nppRs.close();
		} 
		catch (SQLException e) {}
		
	}

	@Override
	public String getNhanvienId() {
		
		return this.nhanvienId;
	}

	@Override
	public void setNhanvienId(String nvId) {
		
		this.nhanvienId = nvId;
	}

	@Override
	public ResultSet getNhanvienRs() {
		
		return this.nhanvienRs;
	}

	@Override
	public void setMsg(String string) {
		
		this.msg = string;
	}

	@Override
	public String getMsg() {
		
		return this.msg;
	}

	@Override
	public ResultSet getBcRs() {
		String sql = "select nv.VUNG, nv.TINHTHANH, nv.CNDT, vt.NGAY, nv.TEN, vt.STT, CONVERT(varchar(5),THOIDIEM, 108) as THOIDIEM, vt.KHOANGCACH, vt.DIACHI, vt.LAT, vt.LONG " + 
			"from NHANVIEN_VITRI vt inner join ("+
		
		"select distinct cast(a.PK_SEQ as varchar(18)) + '2' as PK_SEQ, 'ASM ' + a.TEN as TEN, 'ASM' AS CHUCVU, 2 AS LOAI, nv.PK_SEQ AS NHANVIEN_FK, v.TEN AS VUNG, tt.TEN AS TINHTHANH, p.TEN AS CNDT " +
				"from ASM a inner join NHANVIEN nv on nv.ASM_FK = a.PK_SEQ " +
				"INNER JOIN PHAMVIHOATDONG pv on pv.NHANVIEN_FK = nv.PK_SEQ INNER JOIN NHAPHANPHOI p on p.PK_SEQ = pv.NPP_FK " +
				"INNER JOIN TINHTHANH tt on tt.PK_SEQ = p.TINHTHANH_FK INNER JOIN VUNG v on tt.VUNG_FK = v.PK_SEQ " + 
		"WHERE a.TRANGTHAI = 1 and nv.TRANGTHAI = 1 ";
		
		if(this.vungId.length() > 0)
			sql += "AND v.PK_SEQ = '"+this.vungId+"'  ";
		if(this.ttId.length() > 0)
			sql += "and tt.PK_SEQ = '"+this.ttId + "'  ";
		if(this.loai.length() > 0)
			sql += " and 2 = "+this.loai;
		if(this.nhanvienId.length() > 0)
			sql += " AND cast(a.PK_SEQ as varchar(18)) + '2' = '"+this.nhanvienId+"' ";
		if(this.nppId.length() > 0)
			sql += " and p.PK_SEQ = '"+this.nppId+"' ";
		
		sql +=
				"UNION ALL " + 
				"SELECT distinct cast(BM.PK_SEQ as varchar(18)) + '1' as PK_SEQ, 'BM ' + BM.TEN AS TEN, 'BM' AS CHUCVU, 1 AS LOAI, nv.PK_SEQ AS NHANVIEN_FK, v.TEN AS VUNG, tt.TEN AS TINHTHANH, p.TEN AS CNDT " +
				"FROM BM inner join NHANVIEN nv on nv.BM_FK = BM.PK_SEQ " +
				"INNER JOIN PHAMVIHOATDONG pv on pv.NHANVIEN_FK = nv.PK_SEQ INNER JOIN NHAPHANPHOI p on p.PK_SEQ = pv.NPP_FK " +
				"INNER JOIN TINHTHANH tt on tt.PK_SEQ = p.TINHTHANH_FK INNER JOIN VUNG v on tt.VUNG_FK = v.PK_SEQ " + 
		"WHERE BM.TRANGTHAI = 1 and nv.TRANGTHAI = 1 ";
		if(this.vungId.length() > 0)
			sql +=  "AND v.PK_SEQ = '"+this.vungId+"'  ";
		if(this.ttId.length() > 0)
			sql +=	"and tt.PK_SEQ = '"+this.ttId + "'  ";
		if(this.loai.length() > 0)
			sql += " and 1 = "+this.loai;
		if(this.nhanvienId.length() > 0)
			sql += " AND cast(BM.PK_SEQ as varchar(18)) + '1' = '"+this.nhanvienId+"' ";
		if(this.nppId.length() > 0)
			sql += " and p.PK_SEQ = '"+this.nppId+"' ";
		sql +=
				"UNION ALL " + 
				"SELECT distinct cast(gs.PK_SEQ as varchar(18)) + '3' as PK_SEQ, 'SSM ' + gs.TEN AS TEN, 'SSM' AS CHUCVU, 3 AS LOAI, nv.PK_SEQ AS NHANVIEN_FK, v.TEN AS VUNG, tt.TEN AS TINHTHANH, p.TEN AS CNDT " +
				"FROM GIAMSATBANHANG gs inner join NHANVIEN nv on nv.GSBH_FK = gs.PK_SEQ " +
				"INNER JOIN PHAMVIHOATDONG pv on pv.NHANVIEN_FK = nv.PK_SEQ INNER JOIN NHAPHANPHOI p on p.PK_SEQ = pv.NPP_FK " +
				"INNER JOIN TINHTHANH tt on tt.PK_SEQ = p.TINHTHANH_FK INNER JOIN VUNG v on tt.VUNG_FK = v.PK_SEQ " + 
		"WHERE gs.TRANGTHAI = 1 and nv.TRANGTHAI = 1 ";
		if(this.vungId.length() > 0)
			sql +=  "AND v.PK_SEQ = '"+this.vungId+"'  ";
		if(this.ttId.length() > 0)
			sql +=	"and tt.PK_SEQ = '"+this.ttId + "'  ";
		if(this.loai.length() > 0)
			sql += " and 3 = "+this.loai;
		if(this.nhanvienId.length() > 0)
			sql += " AND cast(gs.PK_SEQ as varchar(18)) + '3' = '"+this.nhanvienId+"'";
		if(this.nppId.length() > 0)
			sql += " and p.PK_SEQ = '"+this.nppId+"' ";
		
		sql += ") nv on vt.NHANVIEN_FK = nv.NHANVIEN_FK " + 
			"where DATEPART(HH, vt.THOIDIEM) >=7 and DATEPART(HH, vt.THOIDIEM) <= 17 ";
		if(tungay.length() > 0)
			sql += "and vt.NGAY >= '" + this.tungay + "' ";
		if(denngay.length() > 0)
			sql += "and vt.NGAY <= '"+this.denngay+"' ";
			
		sql += "order by NGAY, nv.TEN, vt.STT";
		System.out.println("query bc: " + sql);
		
		return this.db.get(sql);
	}

	@Override
	public ResultSet getVungRs() {
		return this.vungRs;
	}

	@Override
	public ResultSet getKhuvucRs() {
		return this.KhuvucRs;
	}

	@Override
	public void setvungId(String vungid) {
		this.vungId = vungid;
	}

	@Override
	public String getvungId() {
		return this.vungId;
	}

	@Override
	public void setkhuvucId(String kvid) {
		this.khuvucId = kvid;
	}

	@Override
	public String getkhuvucId() {
		return this.khuvucId;
	}

	@Override
	public String getLoai() {
		return this.loai;
	}

	@Override
	public void setLoai(String value) {
		this.loai = value;
	}

	@Override
	public String getTtId() {
		return this.ttId;
	}

	@Override
	public void setTtId(String ttid) {
		this.ttId = ttid;
	}

	@Override
	public ResultSet getttRs() {
		return this.ttRs;
	}

	@Override
	public String getNppId() {
		return this.nppId;
	}

	@Override
	public void setNppId(String nppid) {
		this.nppId = nppid;
	}

	@Override
	public ResultSet getNppRs() {
		return this.nppRs;
	}
}