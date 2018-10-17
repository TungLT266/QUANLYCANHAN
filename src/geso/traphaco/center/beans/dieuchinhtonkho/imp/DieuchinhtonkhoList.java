package geso.traphaco.center.beans.dieuchinhtonkho.imp;

import geso.traphaco.center.beans.dieuchinhtonkho.IDieuchinhtonkhoList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
public class DieuchinhtonkhoList implements IDieuchinhtonkhoList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	private String userId;
	private String userTen;
	private String nppTen;
	private String nppId;
	
	private String nccId;
	private String dvkdId;
	private String kbhId;
	private String khoId;
	
	private String tungay;
	private String denngay;
	private String trangthai;
	private String msg;
	
	private ResultSet ncc;
	private ResultSet dvkd;
	private ResultSet kbh;
	private ResultSet kho;
	
	private ResultSet dctkList;
	private dbutils db;
	
	public DieuchinhtonkhoList(String[] param)
	{
		this.db = new dbutils();
		this.userId = param[0];
		this.nppTen = param[1];
		this.nppId 	= param[2];
		this.nccId = param[3];
		this.dvkdId = param[4];
		this.kbhId = param[5];
		this.khoId = param[6];
		this.tungay = param[7];
		this.denngay = param[8];
	}
	
	public DieuchinhtonkhoList()
	{
		this.db = new dbutils();
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		this.nccId = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.khoId = "";
		this.tungay = "";
		this.denngay = "";
		
	}
	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getNppId()
	{
		return this.nppId;
	}
	
	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public String getNppTen()
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen)
	{
		this.nppTen = nppTen;
	}
	
	public ResultSet getDctkList()
	{
		return this.dctkList;
	}
	
	public void setDctkList(ResultSet dctkList)
	{
		this.dctkList = dctkList;
	}
	
	public String getDvkdId()
	{
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}
	
	public String getKbhId()
	{
		return this.kbhId;
	}
	
	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}

	public String getKhoId()
	{
		return this.khoId;
	}
	
	public void setKhoId(String khoId)
	{
		this.khoId = khoId;
	}

	public ResultSet getKho()
	{
		return this.kho;
	}
	
	public void setKho(ResultSet kho)
	{
		this.kho = kho;
	}

	public String getTungay()
	{
		return this.tungay;
	}
	
	public void setTungay(String tungay)
	{
		this.tungay = tungay;
	}
	
	public String getDenngay()
	{
		return this.denngay;
	}
	
	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}
	
	public ResultSet getDvkd()
	{
		return this.dvkd;
	}
	
	public void setDvkd(ResultSet dvkd)
	{
		this.dvkd = dvkd;
	}

	public ResultSet getKbh()
	{
		return this.kbh;
	}
	
	public void setKbh(ResultSet kbh)
	{
		this.kbh = kbh;
	}
	
	public void createDctklist(String querystr){
		String query;
		Utility Ult = new Utility();
		if (querystr.length()>0){
			query = querystr;
		}else{
			//query = "select distinct a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd, e.ten as kbh, f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua, g.ten as nguoiduyet from dieuchinhtonkho a, nhanvien b, nhanvien c, donvikinhdoanh d, kenhbanhang e, kho f, nhanvien g where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.nguoiduyet = g.pk_seq and a.dvkd_fk = d.pk_seq and a.kbh_fk = e.pk_seq and f.pk_seq = a.kho_fk union select distinct a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd, e.ten as kbh, f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua,'0' from dieuchinhtonkho a, nhanvien b, nhanvien c, donvikinhdoanh d, kenhbanhang e, kho f where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.dvkd_fk = d.pk_seq and a.kbh_fk = e.pk_seq and f.pk_seq = a.kho_fk and a.pk_seq not in (select distinct a.pk_seq from dieuchinhtonkho a, nhanvien b, nhanvien c, donvikinhdoanh d, kenhbanhang e, kho f, nhanvien g where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.nguoiduyet = g.pk_seq and a.dvkd_fk = d.pk_seq and a.kbh_fk = e.pk_seq and f.pk_seq = a.kho_fk) order by a.trangthai, a.pk_seq ";
			/*
			 * Thay the cau lenh nay bang cau lenh inner ngan hon
			 
			query = "select distinct a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd,a.kbh_fk,a.npp_fk, e.ten as kbh, f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua, g.ten as nguoiduyet" +
					" from dieuchinhtonkho a, nhanvien b, nhanvien c, donvikinhdoanh d, kenhbanhang e, kho f," +
					" nhanvien g where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.nguoiduyet = g.pk_seq" +
					" and a.dvkd_fk = d.pk_seq and a.kbh_fk = e.pk_seq and f.pk_seq = a.kho_fk" +
					" union select distinct a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd, e.ten as kbh, f.ten, a.tongtien, a.trangthai, " +
					" b.ten as nguoitao, c.ten as nguoisua,'0' from dieuchinhtonkho a, nhanvien b, nhanvien c, " +
					" donvikinhdoanh d, kenhbanhang e, kho f where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.dvkd_fk = d.pk_seq " +
					" and a.kbh_fk = e.pk_seq and f.pk_seq = a.kho_fk and a.pk_seq not in (select distinct a.pk_seq from dieuchinhtonkho a, nhanvien b, nhanvien c, donvikinhdoanh d, kenhbanhang e, kho f, nhanvien g where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.nguoiduyet = g.pk_seq and a.dvkd_fk = d.pk_seq and a.kbh_fk = e.pk_seq and f.pk_seq = a.kho_fk) order by a.trangthai,a.ngaydc desc";
					*
					*/
			query=" select distinct a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd,a.kbh_fk,a.npp_fk, "+ 
			" e.ten as kbh, f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua, isnull (g.ten,'') as  "+
			"  nguoiduyet  from dieuchinhtonkho a inner  join nhanvien b on b.pk_Seq=a.nguoitao  "+
			" inner join nhanvien c on c.pk_seq=a.nguoisua inner join  donvikinhdoanh d on d.pk_seq=a.dvkd_fk "+ 
			" inner join  kenhbanhang e on e.pk_seq=a.kbh_fk inner join  kho f "+
			" on f.pk_Seq=a.kho_fk left join  nhanvien g on g.pk_seq=a.nguoiduyet ";
		}
	
		query = "select ab.* from ("+query+") ab where ab.kbh_fk in "+ Ult.quyen_kenh(this.userId) +" and ab.npp_fk in " + Ult.quyen_npp(this.userId);
		this.dctkList =  this.db.get(query);
		//System.out.print("lay Du Lieu Ra Nek :"+ query);
			
	}

	public String createQueryString(){
		//String query = "select distinct a.ngaydc, a.pk_seq as chungtu, d.donvikinhdoanh as dvkd, e.ten as kbh, f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua from dieuchinhtonkho a, nhanvien b, nhanvien c, donvikinhdoanh d, kenhbanhang e, kho f where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.dvkd_fk = d.pk_seq and a.kbh_fk = e.pk_seq and a.kho_fk=f.pk_seq ";
		String query="";
		if (this.dvkdId.length()>0){
			query = query + " and d.pk_seq = '" + this.dvkdId + "'";
		}
		
		if (this.kbhId.length() >0){
			query = query + " and e.pk_seq= '" + this.kbhId + "'";
		}
		
		if (this.khoId.length() > 0){
			query = query + " and f.pk_seq = '" + this.khoId + "'";
		}
		
	

		if (this.tungay.length() > 0){
				query = query + " and a.ngaydc >= '" + this.tungay + "'";
			}
			if (this.denngay.length() > 0){
				query = query + " and a.ngaydc <= '" + this.denngay + "'";
			}
/*
     	String query1 = "select distinct a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd,a.kbh_fk,a.npp_fk, e.ten as kbh, f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua, g.ten as nguoiduyet " 
                     +" from dieuchinhtonkho a, nhanvien b, nhanvien c, donvikinhdoanh d, kenhbanhang e, kho f, nhanvien g "
                     +" where "
                     +" a.nguoitao = b.pk_seq "+ query +"and a.nguoisua = c.pk_seq and a.nguoiduyet = g.pk_seq and a.dvkd_fk = d.pk_seq and a.kbh_fk = e.pk_seq and f.pk_seq = a.kho_fk union select distinct a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd, e.ten as kbh, f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua,'0' from dieuchinhtonkho a, nhanvien b, nhanvien c, donvikinhdoanh d, kenhbanhang e, kho f where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.dvkd_fk = d.pk_seq and a.kbh_fk = e.pk_seq and f.pk_seq = a.kho_fk and a.pk_seq not in (select distinct a.pk_seq from dieuchinhtonkho a, nhanvien b, nhanvien c, donvikinhdoanh d, kenhbanhang e, kho f, nhanvien g where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.nguoiduyet = g.pk_seq and a.dvkd_fk = d.pk_seq and a.kbh_fk = e.pk_seq and f.pk_seq = a.kho_fk) "
                     +" order by a.trangthai,a.ngaydc desc";
                     */
			//Sua lai ket kieu moi va co ket phan quyen
		String	query1=" select distinct a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd,a.kbh_fk,a.npp_fk, "+ 
			" e.ten as kbh, f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua, isnull (g.ten,'') as  "+
			"  nguoiduyet  from dieuchinhtonkho a inner  join nhanvien b on b.pk_Seq=a.nguoitao  "+
			" inner join nhanvien c on c.pk_seq=a.nguoisua inner join  donvikinhdoanh d on d.pk_seq=a.dvkd_fk "+ 
			" inner join  kenhbanhang e on e.pk_seq=a.kbh_fk inner join  kho f "+
			" on f.pk_Seq=a.kho_fk left join  nhanvien g on g.pk_seq=a.nguoiduyet where 1=1  " + query;
		Utility Ult = new Utility();
		query = "select ab.* from ("+query+") ab where ab.kbh_fk in "+ Ult.quyen_kenh(this.userId) +" and ab.npp_fk in " + Ult.quyen_npp(this.userId);
		//System.out.println("lay hang ton kho :"+query) ;
		return query1;
	}
	
	public void init0(){
		Utility  Ult = new Utility();
		//String query = "select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh ";
		String query ="select a.pk_seq as dvkdId, a.donvikinhdoanh as dvkd from donvikinhdoanh a,Nhacungcap_dvkd b where a.pk_seq = b.dvkd_fk and a.trangthai='1' and b.checked ='1'";
		this.dvkd = this.db.get(query);

		query = "select pk_seq as kbhId, diengiai as kbh from kenhbanhang where pk_seq in" + Ult.quyen_kenh(this.userId);
		this.kbh = this.db.get(query);
	
		query = "select distinct pk_seq as khoId, ten,diengiai from kho" ;
		this.kho = this.db.get(query);
	}
	
	
	public void DBclose(){
		if(!(this.db == null)){
			this.db.shutDown();
		}
	}
}