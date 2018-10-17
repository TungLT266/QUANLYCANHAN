package geso.traphaco.center.beans.phanbotrungbay.imp;

import geso.traphaco.center.beans.phanbotrungbay.IPhanbotrungbay;
import geso.traphaco.center.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public class Phanbotrungbay implements IPhanbotrungbay, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -248730237623651433L;
	String schemeId;
	String scheme;
	String msg;
	ResultSet schemeRS;
	ResultSet kv;
	String kvId;
	ResultSet vung;
	String vungId;
	ResultSet nppRS;
	dbutils db ;
	Hashtable<String, String> usedPro;
	public Phanbotrungbay()
	{
		this.schemeId = "0";
		this.scheme = "";
		this.msg = "";
		this.kvId = "0";
		this.vungId = "0";
		this.nppRS = null;
		this.db = new dbutils();
	}

	public String getSchemeId()
	{
		return this.schemeId;
	}

	public void setSchemeId(String schemeId)
	{
		this.schemeId = schemeId;
	}
		
	public Hashtable<String, String> getusedPro()
	{
		return this.usedPro;
	}

	public void setusedPro(Hashtable<String, String> usedPro)
	{
		this.usedPro = usedPro;
	}

	public String getScheme()
	{
		ResultSet rs = this.db.get("select scheme from cttrungbay where pk_seq='" + this.schemeId + "'");
		try{
			rs.next();
			this.scheme = rs.getString("scheme");
		}catch(Exception e){}
		return this.scheme;
	}

	public void setScheme(String scheme)
	{
		this.scheme = scheme;
	}

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public ResultSet getSchemeRS() 
	{
		return this.schemeRS;
	}

	public void setSchemeRS(ResultSet schemeRS) 
	{
		this.schemeRS = schemeRS;
	}

	public ResultSet getVung() 
	{
		return this.vung;
	}

	public void setVung(ResultSet vung) 
	{
		this.vung = vung;
	}

	public String getVungId() 
	{
		return this.vungId;
	}

	public void setVungId(String vungId) 
	{
		this.vungId = vungId;
	}
	
	public ResultSet getKv() 
	{
		return this.kv;
	}

	public void setKv(ResultSet khuvuc) 
	{
		this.kv = khuvuc;
	}

	public String getKvId() 
	{
		return this.kvId;
	}

	public void setKvId(String kvId) 
	{
		this.kvId = kvId;
	}

	public ResultSet getNpp() 
	{
		return this.nppRS;
	}

	public void setNpp(ResultSet nppRS) 
	{
		this.nppRS = nppRS;
	}
	
	private ResultSet createSchemeRS()
	{
		ResultSet schemeRS = this.db.get("select * from NHOMCTTRUNGBAY  order by ngaytao DESC");
		return schemeRS;
	}
	
	private ResultSet createVungRS()
	{  	
		ResultSet vungRS =  this.db.get("select pk_seq, diengiai from vung  where trangthai='1'");
		return vungRS;
	}
	
	private ResultSet createKvRS(){
		ResultSet kvRS;
		if (!this.vungId.equals("0")){
			kvRS =  this.db.get("select pk_seq, diengiai from khuvuc where trangthai='1' and vung_fk='" + this.vungId + "'");
		}else{
			kvRS =  this.db.get("select pk_seq, diengiai from khuvuc where trangthai='1'");
		}
//		System.out.print("select pk_seq, diengiai from khuvuc where trangthai='1' and vung_fk='" + this.vungId + "'");	
		return kvRS;
		
	}

	private ResultSet createNppRS(){
		ResultSet nppRS = null;
		String query = "";
		if(!this.schemeId.equals("0"))
		{
			query = "select b.ma, b.pk_seq, b.ten, a.ngansach from NHOMCTTRUNGBAY_NPP a, nhaphanphoi b where NHOMCTTRUNGBAY_FK='" + this.schemeId + "' and b.pk_seq=a.npp_fk ";
			
			if(!this.kvId.equals("0"))
				query+="and b.khuvuc_fk = '" + this.kvId + "'";
			
			if(!this.vungId.equals("0"))
				query +=" and b.khuvuc_fk in (select pk_seq from khuvuc where vung_fk="+this.vungId+") ";

			nppRS = this.db.get(query);
		}
		return nppRS;
	}
	
	public void init(){
		this.schemeRS = this.createSchemeRS();
		this.vung = this.createVungRS();
		this.kv = this.createKvRS();
		this.nppRS = this.createNppRS();
	}
	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	@Override
	public boolean save(HttpServletRequest request)
	{
		String[] phanbo = request.getParameterValues("phanbo");
		String[] sudung = request.getParameterValues("sudung");
		String [] nppId= request.getParameterValues("nppId");
		String [] nppTen= request.getParameterValues("nppTen");
		try
		{
			this.db.getConnection().setAutoCommit(false);
			int sodong=phanbo.length;
			for(int i=0;i<sodong;i++)
			{
				String sql = "INSERT INTO NHOMCTTRUNGBAY_NPP(NHOMCTTRUNGBAY_FK,NPP_FK,NGANSACH,dasudung) values('" + this.schemeId + "','" + nppId[i] + "','" + phanbo[i].replace(",", "") + "', 0)";
				if (!db.update(sql))
				{
					sql = "update NHOMCTTRUNGBAY_NPP set ngansach='" + phanbo[i].replace(",", "") + "' where NHOMCTTRUNGBAY_FK='" + this.schemeId + "' and npp_fk='" + nppId[i]+ "' and "+phanbo[i].replace(",", "")+">="+sudung[i].replace(",", "")+" ";
					//System.out.println("[NHOMCTTRUNGBAY_NPP]"+sql);
					if(db.updateReturnInt(sql) <=0)
					{
						this.msg="Số xuất phân bổ cuả  "+nppTen[i]+ " bé hơn số xuất đã sử dụng !";
						this.db.update("rollback");
						return false;
					}						
				}
				
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			this.msg="Lỗi hệ thống "+e.getMessage();
			e.printStackTrace();
			this.db.update("rollback");
			return false;
		}
		return true;
	}
}
