package geso.traphaco.center.beans.quocgia.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.beans.quocgia.Iquocgia;
import geso.traphaco.center.db.sql.dbutils;

public class QuocGia  implements Iquocgia{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId = "";
	String id = "";
	String ten = "";
	String diengiai = "";
	String trangthai = "";
	String ngaytao = "";
	String nguoitao = "";
	String ngaysua = "";
	String nguoisua = "";
	String msg = "";
	ResultSet qg = null;
	List<Iquocgia> qglist;
	dbutils db = new dbutils();
	public QuocGia()
	{
		 userId = "";
		 id = "";
		 ten = "";
		 diengiai = "";
		 trangthai = "";
		 ngaytao = "";
		 nguoitao = "";
		 ngaysua = "";
		 nguoisua = "";
		 msg = "";
	}
	public QuocGia(String[] param)
	{
		this.ten = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
		this.ngaytao = param[3];
		this.nguoitao = param[4];
		this.ngaysua = param[5];
		this.nguoisua = param[6];
		this.id = param[7];
		this.msg = "";
		this.db = new dbutils();
	}
	public void createQGList(String query)
	{  	  
		
		ResultSet rs =  db.get(query);
		List<Iquocgia> qglist = new ArrayList<Iquocgia>();
	
			Iquocgia qgBean;
			String[] param = new String[8];
			if(rs != null)
			{
			try{
				while(rs.next())
				{
					param[0]= rs.getString("tenqg");
					param[1] = rs.getString("diengiai");
					param[2]= rs.getString("trangthai");
					param[3]= rs.getString("ngaytao");
					param[4]= rs.getString("nguoitao");
					param[5]= rs.getString("ngaysua");
					param[6]= rs.getString("nguoisua");
					param[7]= rs.getString("pk_seq");
					qgBean = new QuocGia(param);
					qglist.add(qgBean);													
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			}
		this.qglist = qglist;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getDiengiai() {
		return diengiai;
	}
	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}
	public String getTrangthai() {
		if (this.trangthai.equals("1")){
			return "Hoat dong";
		}
		return "Ngung hoat dong";
	}
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
	public String getNgaytao() {
		return ngaytao;
	}
	public void setNgaytao(String ngaytao) {
		this.ngaytao = ngaytao;
	}
	public String getNguoitao() {
		return nguoitao;
	}
	public void setNguoitao(String nguoitao) {
		this.nguoitao = nguoitao;
	}
	public String getNgaysua() {
		return ngaysua;
	}
	public void setNgaysua(String ngaysua) {
		this.ngaysua = ngaysua;
	}
	public String getNguoisua() {
		return nguoisua;
	}
	public void setNguoisua(String nguoisua) {
		this.nguoisua = nguoisua;
	}
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		 	return msg;
	}
	@Override
	public void setMessage(String msg) {
		this.msg = msg;
		
	}
	@Override
	public void closeDB() {
	
	}
	@Override
	public void init(String query) 
	{
		System.out.println("Init");
		if(query.length() == 0)
		{
			System.out.println("Creat");
			query = "select quocgia.pk_seq,tenqg,diengiai,QuocGia.TRANGTHAI,QuocGia.NGAYTAO,nhanvien.ten as nguoitao,QuocGia.NGAYSUA,nhanvien.ten as nguoisua from QuocGia,nhanvien"+
						" where quocgia.nguoitao = nhanvien.PK_SEQ and quocgia.NGUOISUA = nhanvien.PK_SEQ";
			System.out.println("in "+query);
			createQGList(query);
		}
		else
			if(query == "update")
			{
				System.out.println("Update");
				query = "select tenqg,diengiai,trangthai from QuocGia where pk_seq = "+this.id;
				System.out.println("query "+query);
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					try {
						while(rs.next())
						{
						this.ten = rs.getString("tenqg");
						this.diengiai = rs.getString("diengiai");
						this.trangthai = rs.getString("trangthai");
						}
					} catch (SQLException e) {
					
						e.printStackTrace();
					}
					
				}
			}else
				if(query.length() > 0)
				{
					createQGList(query);
				}
		
	}
	@Override
	public ResultSet GetQuocgiars() {
		// TODO Auto-generated method stub
		return this.qg;
	}
	@Override
	public void SetQuocgiars(ResultSet rs) {
		this.qg = rs;
		
	}
	@Override
	public List<Iquocgia> getQgList() {
		// TODO Auto-generated method stub
		return this.qglist;
	}
	@Override
	public void setQgList(List<Iquocgia> quocgialist) {
		this.qglist = quocgialist;
		
	}
	@Override
	public boolean update() {
		String sql = "Update quocgia set tenqg = N'"+this.ten+"',diengiai = N'"+this.diengiai+"', trangthai = '"+this.trangthai+"' where pk_seq = "+this.id;
		if(db.update(sql))
			return true;
		return false;
	}
	

}
