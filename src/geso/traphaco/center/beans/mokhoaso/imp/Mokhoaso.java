package geso.traphaco.center.beans.mokhoaso.imp;
import geso.traphaco.center.beans.mokhoaso.*;
import geso.traphaco.center.db.sql.dbutils;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;


public class Mokhoaso implements IMokhoaso{
	String vungId;
	String kvId;
	String nppId;
	String msg;
	ResultSet vung;
	ResultSet kv;
	ResultSet npp;
	dbutils db;
	
	public Mokhoaso(){
		this.vungId = "";
		this.kvId = "";
		this.nppId = "";
		this.msg = "";
		this.db = new dbutils();
	}
	public void setVungId(String vungId){
		this.vungId = vungId;
	}
	
	public String getVungId(){
		return this.vungId;
	}
	
	public void setKhuvucId(String kvId){
		this.kvId = kvId;
	}
	
	public String getKhuvucId(){
		return this.kvId;
	}

	public void setNppId(String nppId){
		this.nppId = nppId;
	}
	
	public String getNppId(){
		return this.nppId;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}
	
	public String getMsg(){
		return this.msg;
	}

	public void setVung(ResultSet vung){
		this.vung = vung;
	}
	
	public ResultSet getVung(){
		return this.vung;
	}

	public void setKhuvuc(ResultSet kv){
		this.kv = kv;
	}
	
	public ResultSet getKhuvuc(){
		return this.kv;
	}

	public void setNpp(ResultSet npp){
		this.npp = npp;
	}
	
	public ResultSet getNpp(){
		return this.npp;
	}

	public void init(){
		String vung;
		String kv;
		String npp;
		
		vung = "SELECT PK_SEQ AS VUNGID, TEN FROM VUNG";
		this.vung = this.db.get(vung);
		//System.out.println(vung);
		
		if(this.vungId.length() > 0){
			
			kv = "SELECT PK_SEQ AS KVID, TEN FROM KHUVUC WHERE VUNG_FK = '" + this.vungId + "'";			
			this.kv = db.get(kv);
			
		}else{
			kv ="";
			this.kv = null;
			
		}
		

		if(this.kvId.length() > 0){
			npp = "SELECT PK_SEQ AS NPPID, TEN FROM NHAPHANPHOI WHERE KHUVUC_FK = '" + this.kvId + "'";
			this.npp = db.get(npp);
		}else{
			this.npp = null;
		}
		
	}
	
	String thangks,namks,userId;
	
	public String getUserId()
  {
  	return userId;
  }
	public void setUserId(String userId)
  {
  	this.userId = userId;
  }
	public String getThangks()
  {
  	return thangks;
  }
	public void setThangks(String thangks)
  {
  	this.thangks = thangks;
  }
	public String getNamks()
  {
  	return namks;
  }
	public void setNamks(String namks)
  {
  	this.namks = namks;
  }
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public String MoKhoaSoNgay()
	{
		
		String query = "SELECT TOP(1) ThangKs,nam FROM KhoaSoThang WHERE NPP_FK = '"+ this.nppId +"' ORDER BY nam DESC,thangks desc ";
		ResultSet rs = this.db.get(query);
		try
		{
			
			this.db.getConnection().setAutoCommit(false);
			
			if(rs.next())
			{
				namks = rs.getString("nam");
				thangks = rs.getString("thangks");
			}
			rs.close();
						
			query=
			"insert into MoKhoaSo(Thang,Nam,npp_fk,NguoiTao,NgayTao,NguoiSua,NgaySua) " +
			" select '"+this.thangks+"','"+this.namks+"','"+this.nppId+"','"+this.userId+"','"+this.getDateTime()+"','"+this.userId+"','"+this.getDateTime()+"'   ";
			if(!this.db.update(query))
			{
				db.update("rollback");
				return "Không thể mở khóa sổ tháng: " + query;
			}
			
			 String hdId = "";
			 query = "select SCOPE_IDENTITY() as hdId";
			 ResultSet rs1 = db.get(query);
			 rs1.next();
			 hdId = rs1.getString("hdId");
			
			query=
			"insert into MoKhoaSo_TonKho(MoKhoaSo_fk,npp_fk,kbh_fk,kho_fk,sanpham_fk,soluong,thang,nam) " +
			"select '"+hdId+"','"+this.nppId+"',kbh_fk,kho_fk,sanpham_fk,soluong,thang,nam " +
			"from TonKhoThang where npp_fk='"+this.nppId+"'  and thang='"+this.thangks+"' and nam='"+this.namks+"'  ";
			if(!this.db.update(query))
			{
				db.update("rollback");
				return "Không thể mở khóa sổ tháng: " + query;
			}
			
			query=
			"insert into MoKhoaSo_TonKho_ChiTiet(MoKhoaSo_fk,npp_fk,kbh_fk,kho_fk,sanpham_fk,soluong,thang,nam,SoLo,NgayHetHan) " +
			"select '"+hdId+"','"+this.nppId+"',kbh_fk,kho_fk,sanpham_fk,soluong,thang,nam,SoLo,NgayHetHan " +
			"from TonKhoThang_ChiTiet where npp_fk='"+this.nppId+"'  and thang='"+this.thangks+"' and nam='"+this.namks+"'  ";
			if(!this.db.update(query))
			{
				db.update("rollback");
				return "Không thể mở khóa sổ tháng: " + query;
			}

			
			int SoDong=0;
			query= "select count(*) as SoDong from KhoaSoThang where npp_fk='"+this.nppId+"'";
			rs =db.get(query);
			while(rs.next())
			{
				SoDong = rs.getInt("SoDong");
			}
			if(rs!=null)rs.close();
			if(SoDong==1)
			{
				db.update("rollback");
				return "Không thể mở khóa sổ tháng đầu kỳ ";
			}
			
			/*if(msg.trim().length() <= 0)*/
			{
				query = "DELETE FROM KhoaSoThang WHERE NPP_FK = '" + this.nppId + "' AND thangks = '" + thangks + "' and nam='"+namks+"' ";
				if(!this.db.update(query))
				{
					db.update("rollback");
					return "Không thể mở khóa sổ tháng: " + query;
				}
				
				query = "DELETE FROM TonKhoThang WHERE NPP_FK = '" + this.nppId + "' AND Thang='"+this.thangks+"' and nam='"+namks+"'  ";
				if(!this.db.update(query))
				{
					db.update("rollback");
					return "Không thể mở khóa sổ tháng: " + query;
				}
				query = "DELETE FROM TonKhoThang_CHITIET WHERE NPP_FK = '" + this.nppId + "' AND Thang='"+this.thangks+"' and nam='"+namks+"'  ";
				if(!this.db.update(query))
				{
					db.update("rollback");
					return "Không thể mở khóa sổ tháng: " + query;
				}
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
				return "Mở khóa sổ ("+this.thangks+" -"+this.namks+" )  thành công";
			}
		}
		catch(Exception e)
		{	
			e.printStackTrace();
			this.db.update("rollback");
			return "Không thể mở khóa sổ tháng : " + e.getMessage();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		
	}
	
	private String checkDH(String ngayks){
		
		String query = "SELECT COUNT(PK_SEQ) AS NUM FROM DONHANG " +
					   "WHERE NGAYNHAP > '"+ngayks+"' " +
					   "AND TRANGTHAI IN ('1','3') AND NPP_FK = '" + this.nppId + "'";
		
		System.out.println("1.Check don hang: " + query);	
		ResultSet rs = db.get(query);
		boolean check = false;
		try
		{
			rs.next();
			if (rs.getString("NUM").equals("0"))
			{
				check = true;
			}
			rs.close();
		}
		catch(Exception e){ return e.getMessage(); }
		
		if(!check){
			return "Có đơn hàng đã chốt sau ngày khóa sổ (" + ngayks + "), bạn không thể mở khóa sổ ngày (" + ngayks + ") được";
		}
		
		query="SELECT PK_SEQ FROM NHAPHANG WHERE trangthai='1' and NGAYNHAN > '"+ngayks+"' " +
				" AND NPP_FK = " + this.nppId;
		System.out.println("2.Check nhaphang: "+query);
		
		check = false;
		rs = db.get(query);
		
		try
		{
			if(rs.next())
			{
				check = true;
			}
			rs.close();
		}
		
		catch(Exception e)
		{
			return e.getMessage();
		}
		
		if(check)
		{
			return "Có nhận hàng đã chốt sau ngày khóa sổ (" + ngayks + "), bạn không thể mở khóa sổ ngày (" + ngayks + ") được";
		}
		
		query="SELECT PK_SEQ FROM DIEUCHINHTONKHO WHERE trangthai='1' and NGAYDC >'"+ngayks+"' " +
				" AND NPP_FK = " + this.nppId;
		System.out.println("2.Check DIEU CHINH TON KHO: "+query);
		
		check = false;
		rs = db.get(query);
		
		try
		{
			if(rs.next())
			{
				check = true;
			}
			rs.close();
		}
		
		catch(Exception e)
		{
			return e.getMessage();
		}
		
		if(check)
		{
			return "Có Kiểm kho đã chốt sau ngày khóa sổ (" + ngayks + "), bạn không thể mở khóa sổ ngày (" + ngayks + ") được";
		}
		return "";
		
	}
	
	public void DBClose(){
		try{
			if(this.vung != null) this.vung.close();
			if(this.kv != null) this.kv.close();
			if(this.npp != null) this.npp.close();
			if(this.db!=null){
				this.db.shutDown();
			}
		}catch(Exception e){}
	}
	
	
	public static void main(String[] arg)
	{
		//Create a hashtable
		Hashtable<String, Integer> myhash = new Hashtable<String, Integer>();

		//Put things in Hashtable
		myhash.put("A", 5);
		myhash.put("C", 4);
		myhash.put("B", 1);
		
		ArrayList al = new ArrayList(myhash.keySet()); 
		Collections.sort(al); 
		
		/*Enumeration<String> keys = myhash.keys();
		while(keys.hasMoreElements())
		{
			String key = keys.nextElement();
			
			System.out.println("Key: " + key + " -- Value: " + myhash.get(key));
		}*/
		
		for (Iterator i = al.iterator(); i.hasNext();) { 

			Object obj = myhash.get(i.next());
			System.out.println("Value: " + obj.toString());

			} 
		
		
		
	}
		
	
}
