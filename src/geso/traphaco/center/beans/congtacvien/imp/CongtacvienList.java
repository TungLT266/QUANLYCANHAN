package geso.traphaco.center.beans.congtacvien.imp;
 
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.beans.congtacvien.ICongtacvienList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.*;


public class CongtacvienList extends Phan_Trang implements ICongtacvienList
{
	private static final long serialVersionUID = -9217977546733610214L;

	// Tieu chi tim kiem
	String userId;
	String ma;
	String ten;
	String sodienthoai;
	String trangthai;
	
    String Msg;
	dbutils db;
	
	private ResultSet ddkdlist;

	private HttpServletRequest request;

	String maFAST;
	
	geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();
	
	public CongtacvienList(String[] param)
	{
		this.ten = param[1];
		this.sodienthoai = param[2];
		this.trangthai = param[3];
		this.db = new dbutils();
		this.Msg = "";
		this.maFAST = "";
		//init("");
	}
	
	public CongtacvienList()
	{
		this.ma="";
		this.ten = "";
		this.sodienthoai = "";
		this.trangthai = "2";
		this.Msg="";
		this.maFAST = "";

		this.db = new dbutils();
		
	}
	
	public String getMaFAST() {
		return maFAST;
	}


	public void setMaFAST(String maFAST) {
		this.maFAST = maFAST;
	}
	
	
	public HttpServletRequest getRequestObj() 
	{
		return this.request;
	}

	public void setRequestObj(HttpServletRequest request) 
	{
		this.request = request;
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

	public String getSodienthoai()
	{
		return this.sodienthoai;
	}

	public void setSodienthoai(String sodienthoai) 
	{
		this.sodienthoai = sodienthoai;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public ResultSet getDdkdList()
	{
		return this.ddkdlist;
	}
	
	public void setDdkdList(ResultSet ddkdlist)
	{
		this.ddkdlist = ddkdlist;
	}

	public void createDdkdBeanList(String query)
	{  	  

			this.ddkdlist =  createSplittingData(50, 10, "id desc", query);//  createSplittingData(request, "id desc", query);//this.db.get(query);
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			/*query = "	select isnull(a.mafast,'') as mafast,  a.pk_seq  as id, a.ten , a.dienthoai, a.diachi, a.trangthai, a.ngaytao,  "+
					"		b.ten as nguoitao, a.ngaysua, c.ten as nguoisua,  f.ten as gsbhTen, "+ 
					"		g.ten as kbhTen, a.manhanvien AS manhanvien ,isnull(tn.TEN,'') as TienNhiem, " +
					"		() "+
					"	 from daidienkinhdoanh a inner join nhanvien b on a.nguoitao = b.pk_seq "+ 
					"		 left join DAIDIENKINHDOANH tn on tn.PK_SEQ=a.DDKDTIENNHIEM "+
					"		 inner join  nhanvien c on   a.nguoisua = c.pk_seq "+
					"		 left join ddkd_gsbh e on a.pk_seq = e.ddkd_fk  "+
					"		 left join giamsatbanhang f on e.gsbh_fk = f.pk_seq "+ 
					"		 left join  kenhbanhang g on f.kbh_fk=g.pk_seq "+ 
					"	 where 1=1 "; */
			
			query = "select a.ma, a.pk_seq  as id, a.ten , a.dienthoai, a.diachi, a.trangthai, a.ngaytao,   " +
					"	b.ten as nguoitao, a.ngaysua, c.ten as nguoisua " +
					"from congtacvien a inner join nhanvien b on a.nguoitao = b.pk_seq   " +
					
					"	 inner join  nhanvien c on   a.nguoisua = c.pk_seq  " +
					
					"where 1=1  ";
			
			System.out.println("Danh sách lúc đầu:" + query);
		
		}
		else
		{
			query = search;
		}
		
		//phanquyen
		/*Utility ut = new Utility();
		query += " and d.pk_seq in "+ ut.quyen_npp(userId) + " and isnull(g.pk_seq,100025) in " + ut.quyen_kenh(userId);*/
		System.out.println("Get Dai Dien Kinh Doanh : "+query);
		createDdkdBeanList(query);
		
		
	}

	public void setMsg(String Msg) {
		this.Msg = Msg;
		
	}

	
	public String getMsg() {
		return this.Msg;
	}

	@Override
	public void DbClose() {
		// TODO Auto-generated method stub
	
		try{
			
			if(db!=null){
				db.shutDown();
			}
			
		}catch(Exception er){
			
		}
		
	}

	@Override
	public String getMa() {
		// TODO Auto-generated method stub
		return this.ma;
	}

	@Override
	public void setMa(String ma) {
		// TODO Auto-generated method stub
		this.ma= ma;
	}



}
