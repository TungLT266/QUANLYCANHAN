package geso.traphaco.distributor.beans.tuyenbanhang.imp;



import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.distributor.beans.tuyenbanhang.ITuyenbanhangList;
import geso.traphaco.distributor.db.sql.*;

import java.io.Serializable;
import java.sql.ResultSet;



public class TuyenbanhangList extends Phan_Trang implements ITuyenbanhangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String tuyenbh;
	ResultSet ddkd,tbhRs;
	String mafast;
	String makh;


	String ddkdId;
	
	String nppId;
	String nppTen;
	String sitecode;
		

	dbutils db;

	
	public TuyenbanhangList(String[] param)
	{
		this.tuyenbh = param[0];
		this.ddkdId = param[1];
		
	}
	
	public TuyenbanhangList()
	{
		this.tuyenbh = "";
		this.ddkdId = "";
		this.mafast="";
		this.makh="";
		db = new dbutils();
	}
	
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getTuyenbh() 
	{
		return this.tuyenbh;
	}

	public void setTuyenbh(String tuyenbh) 
	{
		this.tuyenbh = tuyenbh;
	}

	public ResultSet getDdkd() 
	{
		return this.ddkd;
	}

	public void setDdkd(ResultSet ddkd) 
	{
		this.ddkd = ddkd;
	}

	public String getDdkdId() 
	{
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId)
	{
		this.ddkdId = ddkdId;
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
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{		
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				this.sitecode = rs.getString("sitecode");				
			}else
			{
				this.nppId = "";
				this.nppTen = "";
				this.sitecode = "";				
			}
			
		}catch(Exception e){}
		*/
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}


	
	public void createDdkdRS()
	{
		this.getNppInfo();
		String sql ="select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where pk_seq in (select ddkd_fk from daidienkinhdoanh_npp where npp_fk='"+this.nppId+"') order by ten ASC";
	//	System.out.println("chuoi thu:" +  sql);
		this.ddkd = db.get(sql);
		
	}
	
	public void createTbhBeanList(String query)
	{
		
		
	}
		
	public void init(String search) 
	{
		this.getNppInfo();
		String query="";	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as tbhId, a.diengiai as tbhTen, a.ngaytao, a.ngaysua, a.ngaylamviec, b.ten as nguoitao, c.ten as nguoisua,";
			query = query +	" d.pk_seq as ddkdId, d.ten as ddkdTen, e.ten as nppTen, e.pk_seq as nppId,a.NGAYID ";
			query = query + " from tuyenbanhang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq inner join daidienkinhdoanh d on a.ddkd_fk = d.pk_seq"; 
		    query = query + " inner join nhaphanphoi e on a.npp_fk = e.pk_seq where a.npp_fk = '" + this.nppId  + "'";
		}
		else
		{
			query = search;
		}
		
		this.tbhRs=super.createSplittingData(super.getItems(), super.getSplittings(), "NGAYID asc ", query);
		this.createDdkdRS();
	}
	
	@Override
	public void DBclose() 
	{
		
		
		try {
		
			if(this.ddkd != null)
				this.ddkd.close();
			if(this.db != null)
				this.db.shutDown();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}		
	public ResultSet getTbhRs()
	{
		return tbhRs;
	}

	public void setTbhRs(ResultSet tbhRs)
	{
		this.tbhRs = tbhRs;
	}

	@Override
	public String getMafast() {
		return this.mafast;
	}

	@Override
	public void setMafast(String mafast) {
		this.mafast=mafast;
	}

	@Override
	public String getMakh() {
		return this.makh;
	}

	@Override
	public void setMakh(String makh) {
		this.makh=makh;
	}
	
	
}
