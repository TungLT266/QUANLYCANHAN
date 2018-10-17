package geso.traphaco.erp.beans.khovung.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.khovung.IKhoVung;
import geso.traphaco.erp.beans.khovung.IKhoVungList;
import geso.traphaco.erp.db.sql.dbutils;

public class KhoVungList implements IKhoVungList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String ctyId;
	String userId;
	String ma;
	String ten;
	String trangthai;
	String Msg;
	List<IKhoVung> kvlist; 
	ResultSet kvRs;
	dbutils db;
	
	public KhoVungList(String[] param)
	{
		this.db = new dbutils();
		this.ma = param[0];
		this.ten = param[1];
		this.trangthai = param[2];
		
		//init("");
	}
	
	public KhoVungList()
	{
		this.db = new dbutils();
		this.ctyId = "";
		this.ma = "";
		this.ten = "";
		this.trangthai = "2";
		this.Msg = "";
//		init("");
	}
	
	public List<IKhoVung> getKVList() 
	{
		return this.kvlist;
	}

	public void setKVList(List<IKhoVung> kvlist)
	{
		this.kvlist = kvlist;
	}

	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public void createKVBeanList(String query)
	{  	  
		ResultSet rs =  db.get(query);
		List<IKhoVung> kvlist = new ArrayList<IKhoVung>();
		if (rs != null){		
			IKhoVung kvBean;
			String[] param = new String[10];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("ten");
					param[2]= rs.getString("diengiai");
					param[3]= rs.getString("trangthai");
					param[4]= rs.getDate("ngaytao").toString();
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getDate("ngaysua").toString();
					param[7]= rs.getString("nguoisua");
					kvBean = (IKhoVung)new KhoVung(param);
					kvlist.add(kvBean);															
				}
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}finally{
				try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
		
		this.kvlist = kvlist;
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "SELECT KV.PK_SEQ as ID, KV.MA, KV.TEN, KV.TRANGTHAI, KV.NGAYTAO, NV1.TEN as NGUOITAO, KV.NGAYSUA, NV2.TEN as NGUOISUA \n " +
					"FROM ERP_KHOVUNG KV \n " +
					"INNER JOIN NHANVIEN NV1 on nv1.pk_seq = KV.NGUOITAO \n " +
					"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = KV.NGUOISUA \n " +
					"WHERE KV.CONGTY_FK = " + this.ctyId + " ORDER BY KV.TEN ";
			System.out.println(query);
		}
		else
		{
			query = search;
		}
		
		this.kvRs = this.db.get(query);
	}

	public void closeDB(){
		try {
			if (this.kvlist != null)
				this.kvlist.clear();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if (this.db != null)
				this.db.shutDown();
		}
	}

	public void setMsg(String Msg) {
		this.Msg = Msg;		
	}

	public String getMsg() {
		return this.Msg;
	}

	@Override
	public void setKVRs(ResultSet rs) {
		// TODO Auto-generated method stub
		this.kvRs = rs;
	}

	@Override
	public ResultSet getKVRs() {
		// TODO Auto-generated method stub
		return this.kvRs;
	}

}

