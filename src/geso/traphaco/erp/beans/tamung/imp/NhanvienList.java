package geso.traphaco.erp.beans.tamung.imp;

import java.sql.ResultSet;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.tamung.INhanvienList;

public class NhanvienList implements INhanvienList{
   
	String Ma;
	String Ten;
	String Email;
	String TkId;
	String Tungay;
	String Denngay;
	ResultSet DSNV;
	String userId;
	String Trangthai;
	String msg;
	String ctyId;
	String pbId;
	ResultSet pbRs;
	
	String chixem;
	
	dbutils db;
	public NhanvienList()
	{   
		this.Ma = "";
		this.Ten = "";
	    this.Email ="";
	    this.TkId = "";
	    this.Tungay = "";
	    this.Denngay ="";
	    this.Trangthai="";
	    this.ctyId = "";
	    this.pbId = "";
	    this.msg ="";
	    this.chixem = "0";
	    db = new dbutils();
	}

	public void setMa(String Ma) {
		this.Ma = Ma;
	}

	public String getMa() {
		return this.Ma;
	}
	
	public void setTen(String Ten) {
		this.Ten = Ten;
	}

	public String getTen() {
		return this.Ten;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}

	public String getCtyId() {
		return this.ctyId;
	}
	
	public void setEmail(String Email) {
		this.Email = Email;
	}

	public String getEmail() {
		return this.Email;
	}

	public void setTkId(String tkId) {
		this.TkId = tkId;
	}

	public String getTkId() {		
		return this.TkId;
	}

	public void setTungay(String Tungay) {
		this.Tungay = Tungay;
	}

	public String getTungay() {
		return this.Tungay;
	}
	
	public void setDenngay(String Denngay) {
		this.Denngay = Denngay;
	}
	
	public String getDenngay() {
		return this.Denngay;
	}

	public ResultSet getDSNV() {
		return this.DSNV;
	}

	public void init() {		
		String sql =	
				"SELECT	NVTU.PK_SEQ, NVTU.MA, NVTU.TEN, ISNULL(NVTU.EMAIL, '') AS EMAIL, \n" +
				"ISNULL(NVTU.DIACHI, '') AS DIACHI, ISNULL(NVTU.DIENTHOAI, '') AS DIENTHOAI, \n" +
				"NVTU.TRANGTHAI, NV2.TEN AS NGUOISUA, NVTU.NGAYSUA, isnull(DVTH.TEN, isNull(npp.ten, '')) TENPB \n" +
				"FROM ERP_NHANVIEN NVTU \n" +
				"left JOIN NHANVIEN NV1 ON NV1.PK_SEQ = NVTU.NGUOITAO \n" + 
				"left JOIN NHANVIEN NV2 ON NV2.PK_SEQ = NVTU.NGUOISUA \n" +
				"LEFT JOIN ERP_DONVITHUCHIEN DVTH ON NVTU.DVTH_FK = DVTH.PK_SEQ \n"+
				"left JOIN NhaPhanPhoi npp ON NVTU.npp_FK = npp.PK_SEQ \n"+
				"WHERE NVTU.PK_SEQ > 0 \n";

		Utility util = new Utility();
		if (this.Ma.length() > 0) {
			sql = sql + " and upper(NVTU.MA) like upper(N'%" + util.replaceAEIOU(this.Ma) + "%')\n";
		}

		if (this.Ten.length() > 0) {
			sql = sql + " and dbo.ftBoDau(upper(NVTU.TEN)) like upper(N'%" + util.replaceAEIOU(this.Ten) + "%')\n";
		}

		if (Trangthai.length() > 0) {
			sql = sql + " and NVTU.TRANGTHAI <='" + Trangthai + "'\n";
		}

		if (pbId.length() > 0) {
			String [] arr = pbId.split(",");
			if (arr.length > 1)
			{
				if (arr[0].trim().equals("1"))
					sql = sql + " and NVTU.DVTH_FK ='" + arr[1] + "'\n";
				else
					sql = sql + " and npp.pk_seq ='" + arr[1] + "'\n";
			}
			else
				sql = sql + " and NVTU.DVTH_FK ='" + pbId + "'\n";
		}
			
		System.out.println("chuoi: \n" + sql);
		
		if(!this.ctyId.equals("100000")) //TONG CONG TY
			sql += " AND NVTU.CONGTY_FK = " + this.ctyId + "\n";
		
		sql += " ORDER BY NVTU.MA \n";
		
		System.out.println("lay danh sach nhan vien:\n" + sql + "\n------------------------------------------");
		this.DSNV = db.get(sql);
		
		sql = 
				"SELECT '0,' + convert(nvarchar, PK_SEQ) AS PBID, '[CN] ' +  isNull(mafast, '') + ' - ' + isNull(MA, '') + ' - ' + isNull(TEN, '') AS PB \n"
				+ "FROM NHAPHANPHOI WHERE TRANGTHAI = 1 and isKhachHang = 0\n"
				+ " union all\n"
				+ "SELECT '1,' + convert(nvarchar, PK_SEQ) AS PBID, '[PB] ' + isNull(MA, '') + ' - ' + isNull(TEN, '') AS PB \n" 
				+ "  FROM ERP_DONVITHUCHIEN  \n" 
				+ "  WHERE TRANGTHAI = 1\n";
		System.out.println("[Nhanvien.init] phongban sql = \n" + sql + "----------------------------------------------");
		this.pbRs = this.db.get(sql);
	}
	
	public void setuserId(String userId) {
	    this.userId = userId;
	}

	public String getuserId() {
		return this.userId;
	}
	
	public void setTrangthai(String Trangthai) {
		this.Trangthai = Trangthai;
	}
	public String getTrangthai() {
		return this.Trangthai;
	}

	public boolean xoa(String Id) {
		String sql ="select count(*) as num from erp_tamung where nhanvien_fk ='"+ Id +"'";
		ResultSet rs = db.get(sql);
		System.out.print("...."+sql);
		try {
			rs.next();
			if(!rs.getString("num").equals("0"))
			{
			  this.msg ="Bạn không thể xóa vì nhân viên đã chứa dữ liệu! ";
			  return false;
			}			
			else 
			{
				sql ="delete from erp_nhanvien where pk_seq ='"+ Id +"'";
				if(!db.update(sql))
				{
					  this.msg ="khong the xoa duoc ";
					  return false;
				}
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	boolean xoa1(String Id)
	{
		String sql ="delete from phanquyen where nhanvien_fk ='"+Id +"'";
		
		if(!db.update(sql))
			{
			this.msg = sql;
			return false;
			}
		System.out.println(sql);
		sql ="delete from phamvihoatdong where nhanvien_fk ='"+ Id+"'";
		
		if(!db.update(sql))
		{
			this.msg = sql;
			return false;
		}
		System.out.println(sql);
		sql ="delete from nhanvien_kenh where nhanvien_fk ='"+ Id +"'";
		
		if(!db.update(sql))
		{
			this.msg = sql;
			return false;
		}
		System.out.println(sql);
		sql ="delete from nhanvien_sanpham where nhanvien_fk ='"+ Id +"'";
		
		if(!db.update(sql))
		{
			this.msg = sql;
			return false;
		}
		System.out.println(sql);
		return true;
	}
	
	public void setmsg(String msg) {
		this.msg=msg;
	}
	
	public String getmsg() {
		return this.msg;
	}
	
	public void DbClose() {
		try{
			if(DSNV!=null){
				DSNV.close();
			}
		}catch(Exception err){
			err.printStackTrace();
		}
		
		db.shutDown();
	}

	public void setPbRs(ResultSet pbRs) {
		this.pbRs = pbRs;
	}
	
	public ResultSet getPbRs() {
		return this.pbRs;
	}
	
	public void setPbId(String pbId) {
		this.pbId = pbId;
	}

	public String getPbId() {
		return this.pbId;
	}
	
	public void setChixem(String chixem) {
		this.chixem = chixem;
	}

	public String getChixem() {
		return this.chixem;
	}
}