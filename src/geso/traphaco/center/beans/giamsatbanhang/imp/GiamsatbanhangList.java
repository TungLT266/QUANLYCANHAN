package geso.traphaco.center.beans.giamsatbanhang.imp;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.beans.giamsatbanhang.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

public class GiamsatbanhangList extends Phan_Trang implements IGiamsatbanhangList
{
	private static final long serialVersionUID = -9217977546733610214L;

	// Tieu chi tim kiem
	String userId;
	String ten;
	String sodienthoai;
	String trangthai;
	String Msg;
	ResultSet kenhbanhang;
	String kbhId;
	dbutils db;
	Utility Ult;
	HttpServletRequest request;
	
	String maFAST;
	
	private List<IGiamsatbanhang> gsbhlist;


	
	public GiamsatbanhangList(String[] param)
	{
		this.ten = param[1];
		this.sodienthoai = param[2];
		this.kbhId = param[3];
		this.trangthai = param[4];
		this.Msg ="";
		
		this.tungay="";
		this.denngay="";
		this.maFAST = "";
		
		db = new dbutils();
		
		//init("");
	}
	
	public GiamsatbanhangList()
	{   this.Msg ="";
		this.ten = "";
		this.sodienthoai = "";
		this.kbhId = "";
		this.trangthai = "2";
		
		this.tungay="";
		this.denngay="";
		this.maFAST = "";
		
		Ult = new Utility();
		db = new dbutils();


	}
	
	
	public String getMaFAST() {
		return maFAST;
	}

	public void setMaFAST(String maFAST) {
		this.maFAST = maFAST;
	}
	
	
	public void initSplitting(){
		init("");
		setCrrSplitting(getTheLastSplitting()<=15?getTheLastSplitting():15);
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

	public List<IGiamsatbanhang> getGsbhList()
	{
		return this.gsbhlist;
	}
	
	public void setGsbhList(List<IGiamsatbanhang> gsbhlist)
	{
		this.gsbhlist = gsbhlist;
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

	public void createGsbhBeanList(String query)
	{  
				
		ResultSet rs = createSplittingData(50, 10, "id desc", query); //createSplittingData(request, "id desc", query); //db.get(query);
		List<IGiamsatbanhang> gsbhlist = new ArrayList<IGiamsatbanhang>();
		if (rs != null){		
			IGiamsatbanhang gsbhBean;
			String[] param = new String[17];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("ten");
					param[2]= rs.getString("diachi");
					param[3]= rs.getString("sodienthoai");
					param[4]= rs.getString("email");
					param[5]= rs.getString("nccTen");				
					param[6]= rs.getString("trangthai");
					param[7]= rs.getString("ngaytao");
					param[8]= rs.getString("nguoitao");
					param[9]= rs.getString("ngaysua");
					param[10]= rs.getString("nguoisua");
					param[11]= rs.getString("nccId");
					gsbhBean = new Giamsatbanhang(param);
					gsbhBean.setMaNhanVien(rs.getString("manhanvien"));
					gsbhBean.setTrangthai(rs.getString("trangthai"));
					gsbhBean.setMaFAST(rs.getString("mafast"));
					gsbhlist.add(gsbhBean);															
				}
			}catch(Exception e){
				e.printStackTrace();
				e.toString();
			}
		}
		
		this.gsbhlist = gsbhlist;
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = " select isnull(a.maFast,'') as maFast , a.pk_seq as id, a.ten as ten, a.diachi as diachi,  " +
					"  a.dienthoai as sodienthoai, a.email as email, isnull(b.tenviettat,b.ten) as nccTen " +
					" , b.pk_seq as nccId, a.trangthai as trangthai, d.ten as nguoitao," +
					"   a.ngaytao as ngaytao, e.ten as nguoisua, a.ngaysua as ngaysua,  " +
					" a.pk_seq as manhanvien from giamsatbanhang a, nhacungcap b,  " +
					"  nhanvien d, nhanvien e where a.ncc_fk=b.pk_seq  and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq";
		}
		else
		{
			query = search;
		}
		System.out.println("GSBH: " + query);
		createGsbhBeanList(query);  
		//createKbhRS();
	}
   public void setMsg(String Msg) {
		this.Msg =Msg;
		
	}

	public String getMsg() {
		return this.Msg;
	}

	
	public void DBClose() {
	
		if(this.kenhbanhang !=null)
			try {
				this.kenhbanhang.close();
				if(db!=null)
					db.shutDown();
			} catch(Exception e) {
				
				e.printStackTrace();
			}
		
		
	}

	String tungay,denngay;



	public String getTungay()
	{
		return tungay;
	}

	public void setTungay(String tungay)
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return denngay;
	}

	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}
	
	

}
