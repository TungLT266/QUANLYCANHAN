package geso.traphaco.center.beans.nhacungcap.imp;
import geso.traphaco.center.beans.nhacungcap.INhacungcap;
import geso.traphaco.center.db.sql.*;
import java.sql.ResultSet;

public class Nhacungcap implements INhacungcap
{
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String ten;
	String diachi;
	String masothue;
	String tenviettat;
	String trangthai;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String msg;
	
	String kyhieuhoadon;
	String sohoadontu;
	String sohoadonden;
	dbutils db;
	
	public Nhacungcap(String[] param)
	{
		this.id = param[0];
		this.ten = param[1];
		this.diachi = param[2];
		this.masothue = param[3];
		this.tenviettat = param[4];
		this.trangthai = param[5];
		this.ngaytao = param[6];
		this.nguoitao = param[7];
		this.ngaysua = param[8];
		this.nguoisua = param[9];
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Nhacungcap()
	{
		this.id = "";
		this.ten = "";
		this.tenviettat = "";
		this.diachi = "";
		this.masothue = "";
		this.tenviettat = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.sohoadontu="";
		this.sohoadonden="";
		this.kyhieuhoadon="";
		this.msg = "";
		this.db = new dbutils();
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTen()
	{
		return this.ten;
	}

	public void setTen(String ten)
	{
		this.ten = ten;
	}
	
	public String getDiachi()
	{
		return this.diachi;
	}
	
	public void setDiachi(String diachi)
	{
		this.diachi = diachi;
	}
	
	public String getMasothue()
	{
		return this.masothue;
	}
	
	public void setMasothue(String masothue)
	{
		this.masothue = masothue;
	}
	
	public String getTenviettat()
	{
		return this.tenviettat;
	}

	public void setTenviettat(String tenviettat)
	{
		this.tenviettat = tenviettat;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
		
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
		
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}
	boolean kiemtraten()
	{ String sql ="select * from nhacungcap where ten =N'"+ this.ten +"'";
	  ResultSet rs = db.get(sql);
	  try {
		while(rs.next())
			  return true;
	if(rs!=null) rs.close();    	
	} catch(Exception e) {		
		e.printStackTrace();
	}
	 	return false;
	}
	public boolean saveNewNcc(){
		String query;
		//  System.out.print("-----------------------chuoi:" + kiemtraten());
			
		if(kiemtraten()) return false;
		
		query ="insert into nhacungcap values(N'" + this.ten + "',N'"+ this.diachi + "','" + this.masothue + "','" + this.ngaytao + "','" + this.ngaysua + "','" + this.nguoitao + "','" + this.nguoisua  + "',N'" + this.tenviettat  + "','" + this.trangthai + "','"+ sohoadontu +"','"+ sohoadonden +"','"+ kyhieuhoadon +"' )";
		System.out.print(" chen them "+query);
		if (!db.update(query)){
		//	System.out.print(query);
			db.update("rollback");
			return false;			
		}
		String mancc_dvkd = "select  max(pk_seq)   as  ma from nhacungcap";
		System.out.print(" chen them "+mancc_dvkd);
		ResultSet rs = db.get(mancc_dvkd);
		
		try {
			if (rs != null)
			{
				String mancc = "";
				while (rs.next()){
					mancc = rs.getString("ma");
				}
				if(rs!=null) rs.close();	        	
				String dvkd = "select dvkd_fk from nhacungcap_dvkd group by  dvkd_fk";
				ResultSet rs1 = db.get(dvkd);
				try {
					if (rs1 != null)
					{
						while (rs1.next())
						{
							
						
							String madvkd= rs1.getString("dvkd_fk");
							query = "insert into nhacungcap_dvkd values('" + mancc + " ' , '"+ madvkd +"', '0')";
							System.out.print(" chen them "+query);
							if (!db.update(query)){
							//	System.out.print(query);
								db.update("rollback");
								return false;			
							}
						}if(rs!=null) rs.close();
			        	if(db!=null) db.shutDown();
					}
						  
				} catch(Exception e) {
					
					e.printStackTrace();
				}
			}
				  
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public boolean UpdateNcc(){
		String query;
	//	if(kiemtraten()) return false;
		
		query = "update nhacungcap set ten=N'" + this.ten + "', diachi= N'" + this.diachi + "', masothue = '" + this.masothue + "', tenviettat = N'"+ this.tenviettat + "', trangthai = '" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua  + "' ," +
				" kyhieuhoadon = '"+ this.kyhieuhoadon +"', sohoadontu = '"+ this.sohoadontu +"', sohoadonden= '"+ this.sohoadonden +"' " +
				"where pk_seq = '" + this.id + "'" ;
		System.out.print(query);
		if(db.update(query)){
			return true;	
		}else{
			db.update("rollback");		
			return false;
		}	    
		
		
	}
	
	public void init(){
		String query = "select a.pk_seq, a.kyhieuhoadon, a.sohoadontu, a.sohoadonden , a.ten, a.diachi, a.masothue, a.tenviettat, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhacungcap a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq="+ this.id +" order by ngaytao";
   		ResultSet ncc = this.db.get(query);
   		
   		try{
   			ncc.next();
   			this.ten = ncc.getString("ten");
   			this.diachi = ncc.getString("diachi");
   			this.masothue = ncc.getString("masothue");
   			this.tenviettat = ncc.getString("tenviettat");
   			this.trangthai = ncc.getString("trangthai");
   			this.ngaytao = ncc.getString("ngaytao");
   			this.ngaysua = ncc.getString("ngaysua");
   			this.nguoitao = ncc.getString("nguoitao");
   			this.nguoisua = ncc.getString("nguoisua");
   			this.sohoadontu =  ncc.getString("sohoadontu");
   			this.sohoadonden =  ncc.getString("sohoadonden");
   			this.kyhieuhoadon =  ncc.getString("kyhieuhoadon");
   			if(ncc!=null) ncc.close();   			        	       
   		}catch(Exception e){}
	}
	
	public void DBClose(){				
		this.db.shutDown();
	}

	@Override
	public String getSohoadontu() {
		// TODO Auto-generated method stub
		return this.sohoadontu;
	}

	@Override
	public void setSohoadontu(String sohoadontu) {
		this.sohoadontu= sohoadontu;
		
	}

	@Override
	public String getSohoadonden() {
		// TODO Auto-generated method stub
		return this.sohoadonden;
	}

	@Override
	public void setSohoadonden(String sohoadonden) {
		this.sohoadonden= sohoadonden;
		
	}

	@Override
	public String getKyhieuhoadon() {
		// TODO Auto-generated method stub
		return this.kyhieuhoadon;
	}

	@Override
	public void setKyhieuhoadon(String kyhieuhoadon) {
		this.kyhieuhoadon = kyhieuhoadon;
		
	}
	
}
