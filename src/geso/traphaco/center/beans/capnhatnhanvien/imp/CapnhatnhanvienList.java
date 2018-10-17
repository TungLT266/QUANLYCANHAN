package geso.traphaco.center.beans.capnhatnhanvien.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.beans.capnhatnhanvien.ICapnhatnhanvienList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;

public class CapnhatnhanvienList extends Phan_Trang implements ICapnhatnhanvienList
{
	String Ten;
	String Ngaysinh;
	String Tungay;
	String Denngay;
	ResultSet DSNV;
	String userId;
	String Trangthai;
	String Phanloai;
	String msg;
	String tenQuyen;
	
	String nhomquyenId;
	ResultSet nhomquyenRs;
	
	String phongbanId;
	ResultSet phongbanRs;
	
	dbutils db;

	public CapnhatnhanvienList()
	{   
		this.Ten = "";
		this.Ngaysinh ="";
		this.Tungay = "";
		this.Denngay ="";
		this.Trangthai="";
		this.Phanloai ="";
		this.msg ="";
		this.tenQuyen = "";
		this.nhomquyenId = "";
		this.phongbanId = "";
		db = new dbutils();
	}
	
	
	public String getPhongbanId() {
		return phongbanId;
	}

	public void setPhongbanId(String phongbanId) {
		this.phongbanId = phongbanId;
	}

	public ResultSet getPhongbanRs() {
		return phongbanRs;
	}

	public void setPhongbanRs(ResultSet phongbanRs) {
		this.phongbanRs = phongbanRs;
	}


	public void setTen(String Ten) {
		this.Ten = Ten;

	}

	public String getTen() {
		return this.Ten;
	}


	public void setNgaysinh(String Ngaysinh) {
		this.Ngaysinh = Ngaysinh;

	}

	public String getNgaysinh() {

		return this.Ngaysinh;
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

	public void init(String st) 
	{
		String sql="";
		
		sql = "select a.pk_seq,a.Ten,a.dangnhap,a.email,a.dienthoai,a.trangthai,a.phanloai,b.ten as nguoitao1,c.ten as nguoisua1,a.ngaytao,a.ngaysua, " +
				"	 (	 \n "+
				 "		select dbo.trim(dm.Ten) + ', ' AS [text()]    \n "+
				 "		from PHANQUYEN pq inner join DANHMUCQUYEN dm on pq.DMQ_fk = dm.PK_SEQ  \n "+
				 "		where Nhanvien_fk = a.pk_seq \n "+
				 "		For XML PATH ('') \n "+
				 "	)  \n "+
				 "	as quyenIds, dvth.TEN as tenphongban     \n "+
			  " from nhanvien a inner join nhanvien b on b.pk_seq = a.nguoitao " +
			  " inner join nhanvien c on c.pk_seq = a.nguoisua " +
			  " left join ERP_DONVITHUCHIEN dvth on dvth.PK_SEQ = a.PHONGBAN_FK where 1 = 1 \n "+ st;
		
		System.out.println("::: DANH SACH NV: " + sql);
		this.DSNV = createSplittingData(50, 10, "pk_seq desc, TRANGTHAI asc ", sql);	
		
		this.nhomquyenRs = db.get("select PK_SEQ, Ten from DANHMUCQUYEN order by Ten asc");
		
		this.phongbanRs = db.get(" select PK_SEQ, TEN from ERP_DONVITHUCHIEN order by TEN asc");
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
	public void setPhanloai(String Phanloai) {
		this.Phanloai = Phanloai;
	}

	public String getPhanloai() {

		return this.Phanloai;
	}

	public boolean xoa(String Id) {
		String sql ="select count(*) as num from donhang where nguoitao ='"+ Id +"' or  nguoisua ='"+ Id +"'";
		ResultSet rs = db.get(sql);
		try {
			rs.next();
			if(!rs.getString("num").equals("0"))
			{
				this.msg ="khong the xoa duoc ";
				return false;
			}
			rs.close();
			sql ="select count(*) as num from ctkhuyenmai where nguoitao ='"+ Id +"' or  nguoisua ='"+ Id +"'";
			ResultSet tg = db.get(sql);
			tg.next();
			if(!tg.getString("num").equals("0"))
			{
				this.msg ="khong the xoa duoc ";
				return false;

			}
			if(xoa1(Id))
			{
				sql ="delete from nhanvien where pk_seq ='"+ Id +"'";
				if(!db.update(sql))
				{
					this.msg ="khong the xoa duoc ";
					return false;
				}
			}
			if(tg!=null){
				tg.close();
			}
		} catch(Exception e) {

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


	}

	public String getmsg() {

		return null;
	}
	
	
	public void DbClose() {
		
		try{
			if(DSNV!=null)
				DSNV.close();
			db.shutDown();
		}catch(Exception er){

		}
	}

	
	public void setTenquyen(String tenQuyen) {
		
		this.tenQuyen = tenQuyen;
	}

	
	public String getTenquyen() {
		
		return this.tenQuyen;
	}

	
	public void setNhomquyenId(String nhomquyenId) {
		
		this.nhomquyenId = nhomquyenId;
	}

	
	public String getNhomquyenId() {
		
		return this.nhomquyenId;
	}

	
	public void setNhomquyenRs(ResultSet nhomRs) {
		
		this.nhomquyenRs = nhomRs;
	}

	
	public ResultSet getNhomquyenRs() {
		
		return this.nhomquyenRs;
	}

}
