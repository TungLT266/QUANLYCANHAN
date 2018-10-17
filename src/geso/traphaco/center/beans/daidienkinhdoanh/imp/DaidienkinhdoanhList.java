package geso.traphaco.center.beans.daidienkinhdoanh.imp;
 
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.beans.daidienkinhdoanh.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.*;


public class DaidienkinhdoanhList extends Phan_Trang implements IDaidienkinhdoanhList
{
	private static final long serialVersionUID = -9217977546733610214L;

	// Tieu chi tim kiem
	String userId;
	String ten;
	String sodienthoai;
	String trangthai;
	
	ResultSet kenhbanhang;
	String kbhId;
	ResultSet gsbanhang;
	String gsbhId;
	ResultSet nhaphanphoi;
	String nppId;
    String Msg;
	dbutils db;
	
	private ResultSet ddkdlist;

	private HttpServletRequest request;

	String maFAST;
	
	geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();
	
	public DaidienkinhdoanhList(String[] param)
	{
		this.ten = param[1];
		this.sodienthoai = param[2];
		this.trangthai = param[3];
		this.kbhId = param[4];
		this.gsbhId = param[5];
		this.nppId = param[6];
		this.db = new dbutils();
		this.Msg = "";
		this.maFAST = "";
		//init("");
	}
	
	public DaidienkinhdoanhList()
	{
		this.ten = "";
		this.sodienthoai = "";
		this.trangthai = "2";
		this.kbhId = "";
		this.gsbhId = "";
		this.nppId = "";
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
	
	public ResultSet getKenhbanhang() 
	{
		return this.kenhbanhang;
	}

	public void setKenhbanhang(ResultSet kenhbanhang)
	{
		this.kenhbanhang = kenhbanhang;
	}

	public String getKbhId() 
	{
		return this.kbhId;
	}

	public void setKbhId(String kbhId) 
	{
		this.kbhId = kbhId;
	}
	
	public ResultSet getGsbanhang() 
	{
		return this.gsbanhang;
	}

	public void setGsbanhang(ResultSet gsbanhang)
	{
		this.gsbanhang = gsbanhang;
	}

	public String getGsbhId() 
	{
		return this.gsbhId;
	}

	public void setGsbhId(String gsbhId) 
	{
		this.gsbhId = gsbhId;
	}
	public ResultSet getNhaphanphoi() 
	{
		return this.nhaphanphoi;
	}

	public void setNhaphanphoi(ResultSet nhaphanphoi)
	{
		this.nhaphanphoi = nhaphanphoi;
	}

	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}

	private void createKbhRS()
	{  				
		this.kenhbanhang =  this.db.get("select diengiai as kbhTen, pk_seq as kbhId from kenhbanhang where trangthai='1' ");
	}
	private void createGsbhRS()
	{
		String query = "select ten+ ' ('+cast(pk_seq as varchar)+')' as gsbhTen, pk_seq as gsbhId from giamsatbanhang where trangthai = '1' order by gsbhTen";
		System.out.println("GSBH :" + query);
		this.gsbanhang =  this.db.get(query); 
	}

	private void createNppRS()
	{  	
		//String query = "select ten as nppTen, pk_seq as nppId from nhaphanphoi where trangthai='1' order by nppTen";
		//this.nhaphanphoi =  this.db.get(query); 
		String query = "select a.pk_seq as nppId, a.ten as nppTen, 'Khu vuc ' + b.ten as kvTen ";
		query += "from nhaphanphoi a inner join khuvuc b on a.khuvuc_fk = b.pk_seq "
				+ "where a.trangthai = '1' and a.pk_seq in "+ util.quyen_npp(this.userId)+ " and a.sitecode = a.convsitecode order by b.pk_seq asc ";
						
		System.out.println("Query NPP: " + query);
		this.nhaphanphoi = this.db.get(query);
	}

	public void createDdkdBeanList(String query)
	{  	  

			this.ddkdlist =  createSplittingData(50, 10, "id desc", query);//  createSplittingData(request, "id desc", query);//this.db.get(query);
		
	
		/*
		List<IDaidienkinhdoanh> ddkdlist = new ArrayList<IDaidienkinhdoanh>();
		if(rs != null)
		{
			String[] param = new String[20];
			IDaidienkinhdoanh ddkdBean = null;
			try {
				while(rs.next())
				{
					param[0]= rs.getString("id");
					param[1]= rs.getString("ddkdten");
					param[2]= "";
					if(rs.getString("dienthoai") != null)
						param[2] = rs.getString("dienthoai");
					param[3]= "";
					if(rs.getString("diachi") != null)
						param[3] = rs.getString("diachi");
					param[4]= rs.getString("trangthai");
					param[5]= rs.getString("ngaytao");
					param[6]= rs.getString("nguoitao");
					param[7]= rs.getString("ngaysua");
					param[8]= rs.getString("nguoisua");		
					param[9]= rs.getString("kbhTen");
					param[10]= rs.getString("gsbhTen");
					param[11]= rs.getString("nppTen");		
					
					ddkdBean = new Daidienkinhdoanh(param);
					
					ResultSet dhrs = this.db.get("select count(ddkd_fk) as num from donhang where ddkd_fk='"+ param[0] + "'");
					dhrs.next();
					if (!dhrs.getString("num").equals("0")){
						ddkdBean.setIsDelete(false);
					}
					ddkdlist.add(ddkdBean);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}	
		}
		
		this.ddkdlist = ddkdlist;
		*/
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
			
			query = "select distinct isnull(a.mafast,'') as mafast, a.pk_seq  as id, a.ten , a.dienthoai, a.diachi, a.trangthai, a.ngaytao,   " +
					"	b.ten as nguoitao, a.ngaysua, c.ten as nguoisua,  f.ten as gsbhTen,   " +
					"	g.ten as kbhTen, a.manhanvien AS manhanvien ,isnull(tn.TEN,'') as TienNhiem,  " +
					"	isnull((select REPLACE(  " +
					"				(	SELECT npp.TEN AS [data()]  " +
					"					FROM  NHAPHANPHOI npp inner join DAIDIENKINHDOANH_NPP ddkd_npp on npp.pk_seq = ddkd_npp.npp_fk and  ddkd_npp.DDKD_FK = a.PK_SEQ  " +
					"					FOR XML PATH('p')   " +
					"			),' ',' ')  ), 'Traphaco') as nppTEN " +
					"from daidienkinhdoanh a inner join nhanvien b on a.nguoitao = b.pk_seq   " +
					"	 left join DAIDIENKINHDOANH tn on tn.PK_SEQ=a.DDKDTIENNHIEM  " +
					" left join DAIDIENKINHDOANH_NPP ddkd_npp on a.PK_SEQ=ddkd_npp.ddkd_fk "+ 
					"	 inner join  nhanvien c on   a.nguoisua = c.pk_seq  " +
					"	 left join ddkd_gsbh e on a.pk_seq = e.ddkd_fk   " +
					"	 left join giamsatbanhang f on e.gsbh_fk = f.pk_seq     " +
					"	 left join  kenhbanhang g on f.kbh_fk=g.pk_seq   " +
					"where 1=1  and f.pk_seq in (select gsbh_fk from nhapp_giamsatbh where NPP_FK in (ddkd_npp.NPP_FK ))   ";
			
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
		
		createKbhRS();
		createGsbhRS();
		createNppRS();
		
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
			if(kenhbanhang!=null){
				kenhbanhang.close();
			}
			if(gsbanhang!=null){
				gsbanhang.close();
			}
			if(nhaphanphoi!=null){
				nhaphanphoi.close();
			}
			if(db!=null){
				db.shutDown();
			}
			
		}catch(Exception er){
			
		}
		
	}



}
