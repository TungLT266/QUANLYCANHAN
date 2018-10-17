package geso.traphaco.center.beans.huynhaphangtt.imp;

import geso.traphaco.center.beans.huynhaphangtt.IHuynhaphangList;
 
import geso.traphaco.center.db.sql.*;
import geso.traphaco.center.util.Utility;
 

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
 

public class HuynhaphangList implements IHuynhaphangList
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; //nppId
	String tungay;
	String denngay;
	String trangthai;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	ResultSet daidienkd;
	String ddkdId;
	
	ResultSet dhtvlist;
	String Msg;
	dbutils db;
		
	//Constructor
	public HuynhaphangList(String[] param)
	{
		this.tungay = param[0];
		this.denngay = param[1];
		this.trangthai = param[2];
		this.ddkdId = param[3];
		db = new dbutils();
		this.Msg="";
	}
	
	public HuynhaphangList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.ddkdId = "";
	this.Msg="";	
		//init("");
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
	
	public ResultSet getDaidienkd() 
	{	
		return this.daidienkd;
	}
	
	public void setDaidienkd(ResultSet daidienkd) 
	{
		this.daidienkd = daidienkd;		
	}
	
	public String getDdkdId()
	{	
		return this.ddkdId;
	}
	
	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;		
	}

	public String getTrangthai() 
	{	
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;	
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
	public ResultSet getDhtvList() 
	{	
		return this.dhtvlist;
	}
	public void setDhtvList(ResultSet dhtvlist) 
	{
		this.dhtvlist = dhtvlist;		
	}
 
	public void createDhtvBeanList(String query)
	{
		this.dhtvlist =  db.get(query);
	}
	public void init(String search) 
	{
		db = new dbutils();
		String query;	
		Utility util=new Utility();
		
		if (search.length() == 0)
		{
			query = " select a.sochungtu, a.pk_seq  , a.ngaychot , a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " + 
			 " from huynhaphang a inner join nhanvien b on a.nguoitao = b.pk_seq " +
			 "  inner join nhanvien c on a.nguoisua = c.pk_seq " +
			 " inner join nhaphanphoi f on a.npp_fk = f.pk_seq "+
			 " where a.npp_fk in "+util.quyen_npp(this.userId)+" order by a.trangthai,a.pk_seq " ;
		}
		else
		{
			query = search;
		}
 
		this.createDhtvBeanList(query);  
	 
	}
	@Override
	public void DBclose()
	{
		try 
		{
			if(!(this.daidienkd == null))
				this.daidienkd.close();
			if(this.dhtvlist==null){
				this.dhtvlist.close();
			}
			if(this.db != null)
				this.db.shutDown();
		} 
		catch(Exception e) {}
	}

	@Override
	public boolean HuyNhaphang(String dhtvId) {
		try{

			db.getConnection().setAutoCommit(false);		
			
			String sql = " select Replace(convert(char(10), DATEADD(day, 1, cast(max(ngayks) as datetime)) , 102) , '.', '-' ) as ngay from khoasongay " +
						 " where npp_fk= (SELECT NPP_FK FROM HUYNHAPHANG WHERE PK_SEQ="+dhtvId+") ";
			System.out.println(sql);
			String ngaygiaodich="";
			ResultSet rs=db.get(sql);
			if(rs.next()){
				   ngaygiaodich=rs.getString("ngay");
			}
			rs.close();
			if(	 ngaygiaodich.equals("")){
				this.Msg ="Chưa xác định ngày khóa sổ ,vui lòng kiểm tra lại";
				db.update("rollback");
				return false;
			}
			
			sql=" SELECT A.KBH_FK,A.KHO_FK,A.NPP_FK,B.SANPHAM_FK,B.SOLO,B.SOLUONG "+
				" FROM HUYNHAPHANG A  INNER JOIN HUYNHAPHANG_SP  B  "+
				" ON A.PK_SEQ=B.HUYNHAPHANG_FK  "+
				" WHERE A.PK_SEQ="+dhtvId+" AND A.TRANGTHAI =0 AND B.ISXUATKHO= 0 ";
			System.out.println(sql);
			rs=db.get(sql);
				while (rs.next()){
 
						sql=" Update nhapp_kho set booked=booked-"+rs.getDouble("soluong")+" ,available=available + "+rs.getDouble("soluong")+"  where  " +
						" sanpham_fk="+rs.getString("SANPHAM_FK")+" and kho_fk="+rs.getString("KHO_FK")+" and kbh_fk="+rs.getString("KBH_FK")+"  and npp_fk="+rs.getString("NPP_FK")+"";
				 
						if(!db.update(sql)){
							db.update("rollback");
							this.Msg = "Loi khi cap nhat bang "+sql;
							return false; 
						}
						
						sql=" Update nhapp_kho_chitiet  set booked=booked-"+rs.getDouble("soluong")+" ,available=available + "+rs.getDouble("soluong")+"  where  " +
						" sanpham_fk="+rs.getString("SANPHAM_FK")+" and kho_fk="+rs.getString("KHO_FK")+" and kbh_fk="+rs.getString("KBH_FK")+"  and npp_fk="+rs.getString("NPP_FK")+" and solo='"+rs.getString("SOLO")+"'";
				 
						if(!db.update(sql)){
							db.update("rollback");
							this.Msg = "Loi khi cap nhat bang "+sql;
							return false; 
						}
						
				}
				rs.close();
				
				sql="UPDATE HUYNHAPHANG SET TRANGTHAI=2 ,NGUOIDUYET="+this.userId+" , NGAYSUA='"+this.getDateTime()+"'  where  PK_SEQ="+dhtvId;
				if(!db.update(sql)){
					db.update("rollback");
					this.Msg = "Loi khi cap nhat bang "+sql;
					return false; 
				}
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
 	
		}catch( Exception  er){
			er.printStackTrace();
			db.update("rollback");
			return false;
			
		}
		return true;
	}
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return this.Msg;
	}

	@Override
	public void setMsg(String msg) {
		// TODO Auto-generated method stub
		this.Msg=msg;
	}

	@Override
	public boolean ChotNhaphang(String dhtvId) {
		try{

			db.getConnection().setAutoCommit(false);		
			
			String sql = "select Replace(convert(char(10), DATEADD(day, 1, cast(max(ngayks) as datetime)) , 102) , '.', '-' ) as ngay from khoasongay " +
					" where npp_fk= (SELECT NPP_FK FROM HUYNHAPHANG WHERE PK_SEQ="+dhtvId+") ";
			System.out.println(sql);
			String ngaygiaodich="";
			ResultSet rs=db.get(sql);
			if(rs.next()){
				   ngaygiaodich=rs.getString("ngay");
			}
			rs.close();
			if(	 ngaygiaodich.equals("")){
				this.Msg ="Chưa xác định ngày khóa sổ ,vui lòng kiểm tra lại";
				db.update("rollback");
				return false;
			}
			
			sql=" SELECT A.KBH_FK,A.KHO_FK,A.NPP_FK,B.SANPHAM_FK,B.SOLO,B.SOLUONG "+
				" FROM HUYNHAPHANG A  INNER JOIN HUYNHAPHANG_SP  B  "+
				" ON A.PK_SEQ=B.HUYNHAPHANG_FK  "+
				" WHERE A.PK_SEQ="+dhtvId+" AND A.TRANGTHAI =0 AND B.ISXUATKHO= 0 ";
			System.out.println(sql);
			rs=db.get(sql);
			
				while (rs.next()){
 
						sql=" Update nhapp_kho set available=available-"+rs.getDouble("soluong")+" ,soluong=soluong - "+rs.getDouble("soluong")+"  where  " +
						" sanpham_fk="+rs.getString("SANPHAM_FK")+" and kho_fk="+rs.getString("KHO_FK")+" and kbh_fk="+rs.getString("KBH_FK")+"  and npp_fk="+rs.getString("NPP_FK")+"";
				 
						if(!db.update(sql)){
							db.update("rollback");
							this.Msg = "Loi khi cap nhat bang "+sql;
							return false; 
						}
						sql=" Update nhapp_kho_chitiet set available=available-"+rs.getDouble("soluong")+" ,soluong=soluong - "+rs.getDouble("soluong")+" where  " +
						" sanpham_fk="+rs.getString("SANPHAM_FK")+" and kho_fk="+rs.getString("KHO_FK")+" and kbh_fk="+rs.getString("KBH_FK")+"  and npp_fk="+rs.getString("NPP_FK")+" AND SOLO='"+rs.getString("solo")+"'";
						if(!db.update(sql)){
							db.update("rollback");
							this.Msg = "Loi khi cap nhat bang "+sql;
							return false; 
						}
				}
				rs.close();
				
				sql="UPDATE HUYNHAPHANG SET NGAYCHOT='"+ngaygiaodich+"',NGUOIDUYET="+this.userId+", TRANGTHAI=1 WHERE PK_SEQ="+dhtvId;
				if(!db.update(sql)){
					db.update("rollback");
					this.Msg = "Loi khi cap nhat bang "+sql;
					return false; 
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
 	
		}catch( Exception  er){
			er.printStackTrace();
			db.update("rollback");
			return false;
		}
		return true;
	}
	
			
}

