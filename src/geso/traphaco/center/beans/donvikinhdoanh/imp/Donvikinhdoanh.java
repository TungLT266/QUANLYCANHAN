package geso.traphaco.center.beans.donvikinhdoanh.imp;
import java.sql.ResultSet;
import java.util.Hashtable;
import geso.traphaco.center.beans.donvikinhdoanh.IDonvikinhdoanh;
import geso.traphaco.center.db.sql.*;


public class Donvikinhdoanh implements IDonvikinhdoanh
{
	private static final long serialVersionUID = -9217977546733610215L;
	String id;
	String dvkd;
	String diengiai;
	String ncc;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String trangthai;
	String nccId;
	String[] nccSelected;
	String msg;
	dbutils db;
	
	public Donvikinhdoanh(String[] param)
	{
		this.id = param[0];
		this.dvkd = param[1];	
		this.ncc  = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.ngaysua = param[5];
		this.nguoitao = param[6];
		this.nguoisua = param[7];	
		this.nccId = param[8];
		this.diengiai = param[9];
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Donvikinhdoanh()
	{
		this.id = "";
		this.dvkd = "";
		this.ncc  = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.trangthai = "";
		this.nccId = "";
		this.diengiai = "";
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

	public String getDvkd()
	{
		return this.dvkd;
	}

	public void setDvkd(String dvkd)
	{
		this.dvkd = dvkd;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	public String  getNcc(){
		return this.ncc;
	}
	
	public void setNcc(String ncc){
		this.ncc = ncc;
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
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}


	public String  getNccId(){
		return this.nccId;
	}
	
	public void setNccId(String nccId){
		this.nccId = nccId;
	}
	
	public Hashtable<Integer, String>  getNccSelected(){
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.nccSelected != null){
			int size = (this.nccSelected).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), nccSelected[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}
	
	
	public void setNccSelected(String[] nccSelected){
		this.nccSelected = nccSelected;
	}

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}
	
	public void init(){
		String query = "select donvikinhdoanh, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, diengiai from donvikinhdoanh where pk_seq='" + this.id + "'";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			this.dvkd = rs.getString("donvikinhdoanh");
			this.trangthai = rs.getString("trangthai");
			this.ngaytao = rs.getString("ngaytao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoitao = rs.getString("nguoitao");
			this.nguoisua = rs.getString("nguoisua");
			this.diengiai = rs.getString("diengiai");
			rs.close();
		}catch(Exception e){}
		
	}

	public ResultSet getNccList(boolean all){
		
		String command;
		if (all)
			command = "select pk_seq, ten from nhacungcap";
		else
			command = "select pk_seq, ten from nhacungcap where trangthai = '1'";
		
		return this.db.get(command);
		
	}

	public ResultSet getNewNcc(String dvkdId){
		
		String command;
		command = "select pk_seq, ten from nhacungcap where trangthai='1' and pk_seq not in (select a.pk_seq from nhacungcap a, nhacungcap_dvkd b where a.pk_seq = b.ncc_fk and b.dvkd_fk='" + dvkdId + "')";
		
		return this.db.get(command);
		
	}
		boolean tenDVKD()
		{String sql =" select * from donvikinhdoanh where donvikinhdoanh = N'" + this.dvkd + "'";
		System.out.print(sql);
		ResultSet rs = db.get(sql);
		try {
			while(rs.next())
				 return true;
			rs.close();		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			
			return false;
		}
	public boolean saveNewDvkd(){
		try{
			db.getConnection().setAutoCommit(false);
		if(tenDVKD()) return false;
			
		
		// Insert data set into table "donvikinhdoanh"
		String query ="insert into donvikinhdoanh( donvikinhdoanh,ngaytao,ngaysua,nguoitao,nguoisua,trangthai,diengiai) " +
		" values(N'" + this.dvkd + "','"+ this.ngaytao + "','" + this.ngaysua + "','" + this.nguoitao + "','" + this.nguoisua + "','" + this.trangthai + "',N'" + this.diengiai + "')";
		
		if (!db.update(query)){
			db.update("rollback");
			this.msg = "Khong the luu vao table 'Donvikinhdoanh'";
			return false;			
		}

		 query = "select IDENT_CURRENT('donvikinhdoanh')as dvkdId";
		 ResultSet rs = this.db.get(query);
		 
			rs.next();
			this.id = rs.getString("dvkdId");			
			try{
				String sql_update_smartid="update donvikinhdoanh set smartid='"+this.id+"' where pk_seq=" + this.id;
				if(!db.update(sql_update_smartid)){
					db.update("rollback");
					System.out.println("Khong The Thuc Hien Luu Bang Nay,Vui Long Lien He Voi Admin De Sua Loi Nay");
					return false;
				}

			}catch(Exception er)
			{
				db.update("rollback");
				//System.out.println("Khong The Thuc Hien Luu Bang Nay,Vui Long Lien He Voi Admin De Sua Loi Nay");
				return false;
			}
			rs.close();
		// Insert data set into table "nhacungcap_dvkd"
			ResultSet ncclist = getNccList(false);
			Hashtable<Integer, String> selected;

			if (this.nccSelected != null){
				selected = this.getNccSelected();	
			}else{
				selected = new Hashtable <Integer, String>();
						
			}
		
			while (ncclist.next() ){
				String nccId = ncclist.getString("pk_seq");
				if (selected.contains(nccId)){
					query = "insert into nhacungcap_dvkd values('" + nccId + " ' , '"+ this.id +"', '1')";
				}else{
					query = "insert into nhacungcap_dvkd values('" + nccId + " ' , '"+ this.id +"', '0')";
				}
                System.out.println("nha cung cap:"+query);
				if (!db.update(query)){
					db.update("rollback");
					this.msg = this.msg + " Khong the luu vao Nhacungcap_dvkd";
					return false;			
				}
				

			}
			ncclist.close();
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}catch(Exception e){
			db.update("rollback");
			return false;
		}
		
		return true;
	}
	
	public boolean UpdateDvkd(){
	//	if(tenDVKD())
	//	{this.msg = "Bi trung ten don vi kinh doanh";
	//	return false;
		//	}
		Hashtable<Integer, String> selected;
		
		// Update table "donvikinhdoanh"
		String query = "update donvikinhdoanh set donvikinhdoanh=N'" + this.dvkd + "',  ngaysua = '" + this.ngaysua + "',  nguoisua = '" + this.nguoisua + "', trangthai = '" + this.trangthai + "', diengiai = N'" + this.diengiai + "' where pk_seq = '" + this.id + "'" ;

		if(!db.update(query)){
			db.update("rollback");
			this.msg = "Khong the cap nhat table 'Donvikinhdoanh'";
			return false; 
		}
		
		// Update table "nhacungcap_dvkd"
		if (this.nccSelected != null){
			selected = this.getNccSelected();
		}else{
			
			selected = new Hashtable <Integer, String>();
		}
		
		ResultSet ncclist = getNccList(false);
		try{
			while (ncclist.next() ){
				String nccId = ncclist.getString("pk_seq");
				if (selected.contains(nccId)){		
					query = "update nhacungcap_dvkd set checked= '1' where ncc_fk = '"+ nccId + "' and dvkd_fk = '" + this.id + "'";
				}else{
					query = "update nhacungcap_dvkd set checked= '0' where ncc_fk = '"+ nccId + "' and dvkd_fk = '" + this.id + "'";
				}

				if (!db.update(query)){
					db.update("rollback");
					this.msg = "Khong the cap nhat table 'Nhacungcap_dvkd'";
					return false;
				}
				
			}ncclist.close();
		}catch(Exception e){
			this.msg="Loi "+e.toString();
			db.update("rollback");
			return false;
		
		}
		return true;
	}
	
	public ResultSet getNccListByDvkd(String dvkdId){
		
		String command; 
		command = "select a.pk_seq, a.ten, b.checked from nhacungcap a, nhacungcap_dvkd b where a.pk_seq = b.ncc_fk and b.dvkd_fk='" + dvkdId + "'";
		System.out.println("List DVKD  : "+ command);
		return  (db.get(command));
	}

	public void DBClose(){
		this.db.shutDown();
		nccSelected = null;
	}
}


