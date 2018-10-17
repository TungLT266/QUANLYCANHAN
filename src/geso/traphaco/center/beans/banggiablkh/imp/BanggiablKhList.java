package geso.traphaco.center.beans.banggiablkh.imp;

import java.sql.ResultSet;
import java.sql.Statement;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.beans.banggiablkh.IBanggiablKhList;

public class BanggiablKhList  extends Phan_Trang implements IBanggiablKhList
{
	private static final long serialVersionUID = -927977546783610214L;
	
	String userId;
	String userTen;
	String spma;
	String spten;
	String msg;
	/*ResultSet dvkdIds;
	String dvkdId;
	ResultSet kenhIds;
	String kenhId;	
	String trangthai;
	String tungay,denngay,msg;
	
	String nppId;
	String nppTen;
	String sitecode;
	String type;*/

	ResultSet bglist;
	dbutils db;
	
	public BanggiablKhList()
	{
		this.spma = "";		
		this.spten = "";
		this.msg = "";
		this.db = new dbutils();
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getUserTen() 
	{
		return this.userTen;
	}
	
	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
		
	}
	
	public String getSpTen() 
	{
		return this.spten;
	}

	public void setSpTen(String ten) 
	{
		this.spten = ten;
	}

	
	public String getSpMa() 
	{
		return this.spma;
	}

	public void setSpMa(String ma) 
	{
		this.spma = ma;
	}
	
	
	public ResultSet getBglist() 
	{
		return this.bglist;
	}

	public void setBglist(ResultSet bglist) 
	{
		this.bglist = bglist;
	}

	public void init(String search)
	{
		String query;
		
		if (search.length() > 0)
		{
			query = search;
		}
		else
		{
			query = "select distinct s.PK_SEQ, s.MA, s.TEN as TENSP, bg.NGAYTAO, bg.NGAYSUA, nv1.TEN as NGUOITAO, nv2.TEN as NGUOISUA from BANGGIABANLEKH_SANPHAM bg inner join SANPHAM s on s.PK_SEQ = bg.SANPHAM_FK " +
					"left join NHANVIEN nv1 on nv1.PK_SEQ = bg.NGUOITAO left join NHANVIEN nv2 on bg.NGUOISUA = nv2.PK_SEQ";
		}		
		
		//System.out.println("Init BG: " + query );
		this.bglist = this.db.get(query);
	}
	
	public void closeDB()
	{
		try
		{
			Statement st;
			if (bglist != null){
				st = bglist.getStatement();
				st.close();				
				bglist.close();
			}
			
			this.db.shutDown();
		}
		catch(Exception e){}

	}
	
	
	public String getMsg() 
	{
		return msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public ResultSet getBgList() {
		
		return this.bglist;
	}

	public void setBgList(ResultSet bgDoitac) {
		
		this.bglist = bgDoitac;
	}

	@Override
	public void DbClose() {
		this.db.shutDown();
	}

}

