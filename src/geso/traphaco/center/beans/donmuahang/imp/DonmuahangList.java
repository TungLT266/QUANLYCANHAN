package geso.traphaco.center.beans.donmuahang.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.beans.donmuahang.IDonmuahangList;

public class DonmuahangList extends Phan_Trang implements IDonmuahangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	private String userId;
	private String nppten;
	private String ten;
	private String sku;
	private String tungay;
	private String denngay;
	private String trangthai;
	private String maspstr;
	private String kvId,nppId;	
	private String so;
	private ResultSet kvList;
	private ResultSet dhList;
	private ResultSet dhdoneList;
	private ResultSet dhKTlist,nppRs;
	private String malist;
	private dbutils db;

	String msg="";
	public DonmuahangList(String[] param)
	{
		this.db = new dbutils();
		this.sku = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		this.trangthai = param[3];
		createDdhlist("");
	}
	
	public DonmuahangList()
	{
		this.db = new dbutils();
		this.sku = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.kvId	= "";
		this.so = "";
		this.maspstr = "";
		this.nppten = "";
		this.nppId="";
	}
	
	public ResultSet getDhList()
	{
		return this.dhList;
	}
	
	public void setDhList(ResultSet dhList)
	{
		this.dhList = dhList;
	}

	public ResultSet getDhDoneList()
	{
		return this.dhdoneList;
	}
	
	public void setDhDoneList(ResultSet dhdonelist)
	{
		this.dhdoneList = dhdonelist;
	}

	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getTen()
	{
		return this.ten;
	}
	
	public void setTen(String ten)
	{
		this.ten = ten;
	}

	public String getSKU()
	{
		return this.sku;
	}
	
	public void setSKU(String sku)
	{
		this.sku = sku;
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
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getMalist()
	{
		return this.malist;
	}
	
	public void setMalist(String malist)
	{
		this.malist = malist;
	}
	
	@Override
	public String getSO() 
	{	
		return so;
	}

	@Override
	public void setSO(String so) {
		
		this.so = so;
	}
	
	@Override
	public ResultSet getDdhKTList() 
	{		
		return this.dhKTlist;
	}

	public String getMaspstr() 
	{
		String query = "select pk_seq as id, ma, ten from sanpham order by pk_seq";
		ResultSet rs = this.db.get(query);
		try
		{
			while(rs.next())
			{
				if (this.maspstr.length()==0)
				{
					this.maspstr = "\"" + rs.getString("ma") +  " - " + rs.getString("ten") + "\"";
				}
				else
				{
					this.maspstr = this.maspstr + ",\"" + rs.getString("ma") +  " - " + rs.getString("ten") + "\"";
				}
			}
		}
		catch(java.sql.SQLException e){}		
		return this.maspstr;
	}
	
	public void setMaspstr(String maspstr) 
	{
		this.maspstr = maspstr;
	}
	
	public ResultSet getKvList(){
		
		return this.kvList;
	}
	
	public void setKvList(ResultSet kvList){
		this.kvList = kvList;
	}
	
	public String getKvId(){
		return this.kvId;
	}
	
	public void setKvId(String kvId){
		this.kvId = kvId;
	}
	
	public void createDdhlist(String querystr){
		String query;
		geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();	
		if (querystr.length()>0){
			query = querystr;
		}else{
			query =
			" select isnull(a.loaidonhang,0) as loaidonhang,a.thang,a.nam ,a.ngaydat, a.pk_seq as chungtu, " +
			" f.ten as nppTen, e.donvikinhdoanh,  "+
			" isnull( a.sotienavat,0) as sotienavat, " +
			" b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid, " +
			" isnull (nh.ngaychungtu,'0')as ngayhd, "+
			" isnull(nh.sotienbvat,'0') as tienhd ,isnull(nh.ngaynhan,'') as ngaynhan,isnull(a.huychot,0) as huychot,a.kbh_fk,ISNULL(CHIETKHAUTHUONGMAI,0) AS CKTM,ISNULL(IsDuyetCKTM,0) AS IsDuyetCKTM  from dondathang a left join "+ 
			" nhanvien b on  a.nguoitao = b.pk_seq   "+
			" left join  nhanvien c on a.nguoisua = c.pk_seq  "+
			" left join donvikinhdoanh e on a.dvkd_fk = e.pk_seq  "+
			" left join  nhaphanphoi  f  on a.npp_fk = f.pk_seq  "+ 
			" left join nhaphang nh on nh.SOID=a.SOID  "+
			" where ( a.trangthai <>'0')  and isnull(a.kmTT,0)=0  and f.pk_seq in "+ ut.quyen_npp(userId) ;
		}
		this.dhList =  createSplittingData(50, 10, "ngaydat desc, trangthai asc, chungtu desc", query); //this.db.msget2(query);
	
		
		query = "select ten from nhanvien where pk_seq='" + this.userId + "'";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			this.ten = rs.getString("ten");
			rs.close();
		}catch(java.sql.SQLException e){}
		query="select distinct kv.pk_Seq as kvid, kv.ten as  kv from phamvihoatdong inner join " +
			 " nhaphanphoi npp on npp.pk_seq=npp_fk  "+ 
			 " inner join khuvuc kv on kv.pk_seq=npp.khuvuc_fk " +
			 " where nhanvien_fk= '"+ this.userId +"'";
		this.kvList = this.db.get(query);
		
		
		
		query=
			"SELECT A.PK_SEQ AS NPPID, A.TEN AS NPPTEN,A.MA AS NPPMA, B.TEN AS KVTEN "+ 
			"FROM  "+
			" NHAPHANPHOI A INNER JOIN KHUVUC B ON A.KHUVUC_FK = B.PK_SEQ "+ 
			"WHERE A.TRANGTHAI = '1' AND A.SITECODE = A.CONVSITECODE ";
		if(this.kvId.length()>0)
		{
			query += " and A.khuvuc_fk ='"+this.kvId+"' ";
		}
		query+= "order by b.ten,a.ten asc ";
		this.nppRs=this.db.get(query);

	}
	
	public void DBclose()
	{	
		try
		{
			if(this.kvList!=null)
			this.kvList.close();
			if(nppRs!=null)this.nppRs.close();
			if(!(this.db == null)){
				this.db.shutDown();
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void createDdhKTlist(String querystr) {
		
		String query;
		//geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();	
		if (querystr.length()>0){
			query = querystr;
		}else
		{
					
		
			query = " select distinct a.ngaydat, a.PREFIX, a.pk_seq as chungtu, f.ten as nppTen, e.donvikinhdoanh,"+   
					"  cast(a.sotienavat as float) as sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid, "+ 
			 " isnull (nh.ngaychungtu,'0')as ngayhd,  cast(isnull(nh.sotienbvat,'0') as float) as tienhd ,1 as loai from dondathang a "+ 
			 " inner join  nhanvien b on  a.nguoitao = b.pk_seq    inner join  nhanvien c on a.nguoisua = c.pk_seq "+  
			 " inner join dondathang_sp d on a.pk_seq = d.dondathang_fk  inner join donvikinhdoanh e on "+ 
			 " a.dvkd_fk = e.pk_seq   inner join  nhaphanphoi  f  on a.npp_fk = f.pk_seq    "+
			 " left join nhaphang nh on nh.dathang_fk=a.pk_seq   where ( a.trangthai  > 1  and a.trangthai <=3) "+
			 "  union  "+
			 "  select distinct dth.ngaytra as ngaydat, dth.PREFIX, dth.pk_seq as chungtu,npp.ten as  "+
			 "  nppTen,dvkd.donvikinhdoanh,cast(  dth.sotienavat as float) as sotienavat,  nt.ten as nguoitao,ns.ten as nguoisua,dth.trangthai,'' "+ 
			 "  as soid,'' as ngayhd,cast (0 as float)  as tienhd, 2 as loai  from dontrahang dth inner join nhaphanphoi npp on  "+
			 "   dth.npp_fk=npp.pk_seq  inner join nhanvien nt on nt.pk_seq=dth.nguoitao  inner join nhanvien ns "+
			 "    on ns.pk_seq=dth.nguoisua  inner join donvikinhdoanh dvkd on dvkd.pk_Seq=dth.dvkd_fk  "+
			 "     inner join dontrahang_sp dth_sp on dth.pk_seq=dth_sp.dontrahang_fk where dth.trangthai >=2 "+
			 "      and dth.trangthai <=3 ";
			 
			
		}
		//System.out.println("Khoi tao duyet don hang: " + query);
		
		this.dhKTlist =  createSplittingData(50, 10, "chungtu desc", query); //this.db.msget2(query);
	
		
		query = "select ten from nhanvien where pk_seq='" + this.userId + "'";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			this.ten = rs.getString("ten");
			rs.close();
		}catch(java.sql.SQLException e){}
		query="select distinct kv.pk_Seq as kvid, kv.ten as  kv from phamvihoatdong inner join " +
			 " nhaphanphoi npp on npp.pk_seq=npp_fk  "+ 
			 " inner join khuvuc kv on kv.pk_seq=npp.khuvuc_fk " +
			 " where nhanvien_fk= '"+ this.userId +"'";
		this.kvList = this.db.get(query);	
		
		query=" select pk_Seq as nppId,ten as nppTen,ma as nppMa from nhaphanphoi where trangthai=1";
		this.nppRs=this.db.get(query);
	}



	@Override
	public void SetMsg(String msg) {
		
		this.msg=msg;
	}

	@Override
	public String getMsg() {
		
		return this.msg;
	}

	@Override
	public String getnppTen() {
		
		return this.nppten;
	}

	@Override
	public void setnppTen(String nppten) {
		
		this.nppten = nppten;
	}

	@Override
	public void initDonHangKm(String querystr)
	{
		String query;
		geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();	
		if (querystr.length()>0){
			query = querystr;
		}else
		{
			query = " select isnull(a.loaidonhang,0) as loaidonhang,a.thang,a.nam ,a.ngaydat, a.pk_seq as chungtu, " +
					" f.ten as nppTen, e.donvikinhdoanh,  "+
				" isnull( a.sotienavat,0) as sotienavat, " +
				" b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid, " +
				"isnull (nh.ngaychungtu,'0')as ngayhd, "+
				" isnull(nh.sotienbvat,'0') as tienhd,a.kbh_fk from dondathang a left join "+ 
				" nhanvien b on  a.nguoitao = b.pk_seq   "+
				" left join  nhanvien c on a.nguoisua = c.pk_seq  "+
				" left join donvikinhdoanh e on a.dvkd_fk = e.pk_seq  "+
				" left join  nhaphanphoi  f  on a.npp_fk = f.pk_seq  "+ 
				" left join nhaphang nh on nh.dathang_fk=a.pk_seq  "+
				" where ( a.trangthai <>'0') and a.loaidonhang=1 and a.kmTT=1   and f.pk_seq in "+ ut.quyen_npp(userId) ;
		}
	
		this.dhList =  super.createSplittingData(super.getItems(), super.getSplittings(), "ngaydat desc ", query);
		query = "select ten from nhanvien where pk_seq='" + this.userId + "'";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			this.ten = rs.getString("ten");
			rs.close();
		}catch(java.sql.SQLException e){}
		query="select distinct kv.pk_Seq as kvid, kv.ten as  kv from phamvihoatdong inner join " +
			 " nhaphanphoi npp on npp.pk_seq=npp_fk  "+ 
			 " inner join khuvuc kv on kv.pk_seq=npp.khuvuc_fk " +
			 " where nhanvien_fk= '"+ this.userId +"'";
		this.kvList = this.db.get(query);	
		
		query=
			"SELECT A.PK_SEQ AS NPPID, A.TEN AS NPPTEN,A.MA AS NPPMA, B.TEN AS KVTEN "+ 
			"FROM  "+
			" NHAPHANPHOI A INNER JOIN KHUVUC B ON A.KHUVUC_FK = B.PK_SEQ "+ 
			"WHERE A.TRANGTHAI = '1' AND A.SITECODE = A.CONVSITECODE ";
			if(this.kvId.length()>0)
			{
				query += " and A.khuvuc_fk ='"+this.kvId+"' ";
			}
			query+= "order by b.ten,a.ten asc ";
			this.nppRs=this.db.get(query);
		
	}
	
	public String getNppId()
	{
		return this.nppId;
	}
	
	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}
	
	public ResultSet getNppRs()
	{
		return nppRs;
	}

	public void setNppRs(ResultSet nppRs)
	{
		this.nppRs = nppRs;
	}
	
}