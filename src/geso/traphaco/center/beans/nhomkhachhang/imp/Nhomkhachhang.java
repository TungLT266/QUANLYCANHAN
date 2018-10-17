package geso.traphaco.center.beans.nhomkhachhang.imp;
import geso.traphaco.center.beans.nhomkhachhang.INhomkhachhang;
import geso.traphaco.center.db.sql.*;
import java.sql.ResultSet;
import java.io.Serializable;

public class Nhomkhachhang implements INhomkhachhang, Serializable
{
	private static final long serialVersionUID = -9217977546733690415L;
	String id;
	String diengiai;
	String trangthai;	
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String msg;
	ResultSet khList;
	ResultSet khSelected;
	
	ResultSet vungList;
	ResultSet kvList;
	ResultSet nppList;
	String vungId;
	String kvId;
	String nppId;
	String[] khachhang;
	boolean search = false;
	dbutils db ;
	
	public Nhomkhachhang(String[] param)
	{
		this.id = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
		this.ngaytao = param[3];
		this.ngaysua = param[4];
		this.nguoitao = param[5];
		this.nguoisua = param[6];	
		this.msg = "";
		this.vungId = "0";
		this.kvId = "0";
		this.nppId = "0";
		this.db = new dbutils();
	}
	
	public Nhomkhachhang()
	{
		this.id = "";
		this.diengiai = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";	
		this.msg = "";
		this.vungId = "0";
		this.kvId = "0";
		this.nppId = "0";
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

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
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
	
	public ResultSet getKhList()
	{
		return this.khList;
	}

	public void setKhList(ResultSet khList)
	{
		this.khList = khList;
	}

	public ResultSet getKhSelected()
	{
		return this.khSelected;
	}

	public void setKhSelected(ResultSet KhSelected)
	{
		this.khSelected = KhSelected;
	}


	public ResultSet getVungList()
	{
		return this.vungList;
	}

	public void setVungList(ResultSet vungList)
	{
		this.vungList = vungList;
	}
	
	public ResultSet getKvList()
	{
		return this.kvList;
	}

	public void setKvList(ResultSet kvList)
	{
		this.kvList = kvList;
	}
	
	public ResultSet getNppList()
	{
		return this.nppList;
	}

	public void setNppList(ResultSet nppList)
	{
		this.nppList = nppList;
	}

	public String getVungId()
	{
		return this.vungId;
	}

	public void setVungId(String vungId)
	{
		this.vungId = vungId;
	}

	public String getKvId()
	{
		return this.kvId;
	}

	public void setKvId(String kvId)
	{
		this.kvId = kvId;
	}

	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}


	public String[] getKhachhang()
	{
		return this.khachhang;
	}

	public void setKhachhang(String[] khachhang)
	{
		this.khachhang = khachhang;
	}
	
	public boolean saveNewNkh(){
		String command;
		command = "insert into nhomkhachhang values(N'" + this.diengiai + "', '" + this.ngaytao + "', '" + this.ngaysua +"', '" + this.nguoitao + "', '" + this.nguoisua + "', '"+ this.trangthai +"')";			
		System.out.print(command);
		this.db.update(command);

		command = "select IDENT_CURRENT('nhomkhachhang') as nkhId";
		ResultSet rs = this.db.get(command);
		try{			
			rs.next();
			this.id = rs.getString("nkhId");

			if(this.khachhang != null){
				String[] khachhangList = this.khachhang; 
				int size = (this.khachhang).length;
				int m = 0;
				
				while(m < size){
					command = "insert into nhomkhachhang_khachhang(kh_fk, nkh_fk) values('" + khachhangList[m] + "', '"+ this.id + "')";
					System.out.print(command);
					this.db.update(command);
					m++ ;
				}
			}
			}catch(Exception e){}

		return true;
	}
	
	public boolean updateNkh(){

		String command;		
	//	command ="update nhomkhachhang set ten ='"+ this.ten +"', diengiai = '"+ this.diengiai +"', trangthai ='" + this.trangthai + "' where pk_seq = '" + this.id + "'";
		command ="update nhomkhachhang set diengiai = N'"+ this.diengiai +"',ngaysua ='"+ this.ngaysua +"', trangthai ='" + this.trangthai + "' where pk_seq = '" + this.id + "'";
		
		this.db.update(command);
				
		command = "delete from nhomkhachhang_khachhang where nkh_fk = '"+ this.id + "'";
		this.db.update(command);

		if(this.khachhang != null){
			String[] khachhangList = this.khachhang; 
			int size = (this.khachhang).length;
			int m = 0;
				
			while(m < size){
				command = "insert into nhomkhachhang_khachhang(kh_fk, nkh_fk) values('" + khachhangList[m] + "','" + this.id + "')";
				this.db.update(command);
				m++ ;
			}
				
		}
			
		return true;
	}

	private ResultSet createVungRS(){  	
		
		ResultSet vungRS =  this.db.get("select pk_seq, diengiai from vung  where trangthai='1'");
		
		return vungRS;
	}
	
	private ResultSet createKvRS(){
		ResultSet kvRS;
		if (!this.vungId.equals("0")){
			kvRS =  this.db.get("select pk_seq, diengiai from khuvuc where trangthai='1' and vung_fk='" + this.vungId + "'");
		}else{
			kvRS =  null;	
		}
		System.out.print("select pk_seq, diengiai from khuvuc where trangthai='1' and vung_fk='" + this.vungId + "'");	
		return kvRS;
		
	}

	private ResultSet createNppRS() {  	
		ResultSet nppRS;
	
		if(!this.kvId.equals("0")){
			nppRS = this.db.get("select distinct pk_seq, ten from nhaphanphoi where trangthai='1' and khuvuc_fk ='" + this.kvId + "'");
		}else{
			nppRS = null;
		}
		return nppRS;
			
	}
	
	
	private void createKhRS(){  	

		String query = "";
		String temp = "";
		if (this.khachhang != null){
			query = "select pk_seq, ten from khachhang where";
			temp =  "select pk_seq from khachhang where";
			for(int i=0; i < this.khachhang.length; i++){
				if (i==0){
					query = query + " pk_seq = '" + this.khachhang[i] + "'";
					temp = temp + " pk_seq = '" + this.khachhang[i] + "'";
				}else{						
					query = query + " or pk_seq = '" + this.khachhang[i] + "'";
					temp = temp + " or pk_seq = '" + this.khachhang[i] + "'";
				}
			}				
			this.khSelected = this.db.get(query);
		}else{
			this.khSelected = null;
		}
		
		
		if (this.id.length() > 0){
			if (this.khachhang == null){
				query = "select a.pk_seq, a.ten from khachhang a, nhomkhachhang_khachhang b where a.pk_seq = b.kh_fk and b.nkh_fk = '" + this.id + "'";
				this.khSelected = this.db.get(query);	
			
				query = "select a.pk_seq, a.ten from khachhang a, nhaphanphoi b, khuvuc c, vung d  where c.vung_fk=d.pk_seq and b.khuvuc_fk=c.pk_seq and a.npp_fk=b.pk_seq and a.trangthai = '1' and a.pk_seq not in (select kh_fk from nhomkhachhang_khachhang where nkh_fk = '" + this.id + "')";
			}else{
				query = "select a.pk_seq, a.ten from khachhang a, nhaphanphoi b, khuvuc c, vung d  where c.vung_fk=d.pk_seq and b.khuvuc_fk=c.pk_seq and a.npp_fk=b.pk_seq and a.trangthai = '1' and a.pk_seq not in (select kh_fk from nhomkhachhang_khachhang where nkh_fk = '" + this.id + "') and a.pk_seq not in(" + temp + ")";

			}
		}else{
			if (this.khachhang != null){
				query = "select a.pk_seq, a.ten from khachhang a, nhaphanphoi b, khuvuc c, vung d  where c.vung_fk=d.pk_seq and b.khuvuc_fk=c.pk_seq and a.npp_fk=b.pk_seq and a.trangthai = '1' and a.pk_seq not in(" + temp + ")";
			}else{
				query = "select a.pk_seq, a.ten from khachhang a, nhaphanphoi b, khuvuc c, vung d  where c.vung_fk=d.pk_seq and b.khuvuc_fk=c.pk_seq and a.npp_fk=b.pk_seq and a.trangthai = '1'" ;
			}
		}
		
		if (!this.vungId.equals("0")){
			query = query + " and d.pk_seq ='" + this.vungId + "'";

		}
		
		if (!this.kvId.equals("0")){
			query = query + " and c.pk_seq = '" + this.kvId + "'";
		}
		
		if (!this.nppId.equals("0")){
			query = query + " and b.pk_seq = '" + this.nppId + "'";

		}
		if (this.khachhang != null){
			query = query +  " and a.pk_seq not in (" + temp + ")";
		}
		query = query + " order by a.ten";
		System.out.print(query);
		this.khList = this.db.get(query);		
		
	}
	
	public void UpdateRS(){
		this.vungList = createVungRS();
		this.kvList = createKvRS();
		this.nppList = createNppRS();
		
		createKhRS();

	}
	
}


