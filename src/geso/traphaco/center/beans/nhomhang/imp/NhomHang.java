package geso.traphaco.center.beans.nhomhang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.beans.nhomhang.INhomHang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.util.UtilitySyn;

public class NhomHang implements INhomHang
{
	
	public NhomHang(String id)
	{
		super();
		this.id = id;
		this.userId = "";
		this.ma = "";
		this.ten = "";
		this.loainhom = "";
		this.msg = "";
		this.spId="";
		this.db = new dbutils();
	}

	public NhomHang()
	{
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.userId = "";
		this.loainhom = "2";
		this.msg = "";
		this.spId="";
		this.db = new dbutils();
	}

	

	String dvdlId,id, userId, ma, ten, loainhom, msg,nganhHangId,nhanHangId;
	ResultSet nganhHangRs,nhanhangRs,dvdlRs,rsnhomhang;
	
	public ResultSet getRsnhomhang() {
		return rsnhomhang;
	}

	public void setRsnhomhang(ResultSet rsnhomhang) {
		this.rsnhomhang = rsnhomhang;
	}

	public String getDvdlId()
  {
  	return dvdlId;
  }

	public void setDvdlId(String dvdlId)
  {
  	this.dvdlId = dvdlId;
  }

	public ResultSet getDvdlRs()
  {
  	return dvdlRs;
  }

	public void setDvdlRs(ResultSet dvdlRs)
  {
  	this.dvdlRs = dvdlRs;
  }

	public String getNganhHangId()
  {
  	return nganhHangId;
  }

	public void setNganhHangId(String nganhHangId)
  {
  	this.nganhHangId = nganhHangId;
  }







	dbutils db;
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}



	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getMa()
	{
		return ma;
	}

	public void setMa(String ma)
	{
		this.ma = ma;
	}

	public String getTen()
	{
		return ten;
	}

	public void setTen(String ten)
	{
		this.ten = ten;
	}

	@Override
	public String getMsg()
	{

		return this.msg;
	}

	@Override
	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	@Override
	public boolean save()
	{
		String query=
				"insert into NhomHang(ma,ten,trangthai,nguoitao,ngaytao,nguoisua,ngaysua)" +
				" select N'"+this.ma+"',N'"+this.ten+"',0,'"+this.userId+"','"+this.getDateTime()+"','"+this.userId+"','"+this.getDateTime()+"'  ";
		try
		{
			
			ResultSet rsCheck = db.get("select COUNT(PK_SEQ) as sodong from NhomHang where  upper(ma) = upper('" + this.ma + "')");
			boolean chk = true;
			if(rsCheck != null)
			{
				if(rsCheck.next())
				{
					if(rsCheck.getInt("sodong") > 0)
					{
						chk = false;
					}
					rsCheck.close();
				}
			}
			if(!chk)
			{
				this.msg = "Mã (" + this.ma + ") bạn muốn cập nhật đã tồn tại trong hệ thống, vui lòng nhập mã khác.";
				return false;
			}
			this.db.getConnection().setAutoCommit(false);
			if(!this.db.update(query))
			{
				this.msg="Lỗi hệ thống "+query;
				this.db.getConnection().rollback();
				return false;
			}
			query = "select SCOPE_IDENTITY() as nhomId";
			ResultSet rs=this.db.get(query);
			while(rs.next())
				this.id=rs.getString("nhomId");
			rs.close();
			
			if(this.spId.length()>0)
			{
				System.out.println("this.spId: "+this.spId);
				query="insert into nhomhang_sanpham(nhomhang_fk,sanpham_fk)" +
						" select '"+this.id+"',pk_seq" +
						" from erp_sanpham where pk_seq in ("+this.spId+")  ";
				System.out.println("query: "+query);
				if(!this.db.update(query))
				{
					this.msg="Lỗi hệ thống "+query;
					this.db.getConnection().rollback();
					return false;
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "NhomHang", "NhomHang", "PK_SEQ", id, "0", false);
			UtilitySyn.SynData(db, "nhomhang_sanpham", "nhomhang_sanpham", "nhomhang_fk", id, "0", false);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		return true;
	}

	@Override
	public boolean edit()
	{
		
		String query="update NhomHang  set Ma=N'"+this.ma+"', Ten=N'"+this.ten+"',NguoiSua='"+this.userId+"',NgaySua='"+getDateTime()+"' where pk_Seq='"+this.id+"'" ;
		try
		{
			ResultSet rsCheck = db.get("select COUNT(PK_SEQ) as sodong from NhomHang where pk_seq != '" + this.id + "' and upper(ma) = upper('" + this.ma + "')");
			boolean chk = true;
			if(rsCheck != null)
			{
				if(rsCheck.next())
				{
					if(rsCheck.getInt("sodong") > 0)
					{
						chk = false;
					}
					rsCheck.close();
				}
			}
			if(!chk)
			{
				this.msg = "Mã (" + this.ma + ") bạn muốn cập nhật đã tồn tại trong hệ thống, vui lòng nhập mã khác.";
				return false;
			}
			this.db.getConnection().setAutoCommit(false);
			if(!this.db.update(query))
			{
				this.msg="Lỗi hệ thống "+query;
				this.db.getConnection().rollback();
				return false;
			}
			query="delete from nhomhang_sanpham where nhomhang_fk='"+this.id+"'";
			if(!this.db.update(query))
			{
				this.msg="Lỗi hệ thống "+query;
				this.db.getConnection().rollback();
				return false;
			}
			System.out.println("spId.length" + spId.length()); 
			System.out.println("san pham :" +spId);
			if(this.spId.length()>0)
			{
				query="insert into nhomhang_sanpham(nhomhang_fk,sanpham_fk)" +
						" select '"+this.id+"',pk_seq" +
						" from erp_sanpham where pk_seq in ("+this.spId+")  ";
				System.out.println("query insert " + query); 
				if(!this.db.update(query)) 
				{
					this.msg="Lỗi hệ thống "+query;
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "NhomHang", "NhomHang", "PK_SEQ", id, "1", false);
			UtilitySyn.SynData(db, "nhomhang_sanpham", "nhomhang_sanpham", "nhomhang_fk", id, "2", true);
			UtilitySyn.SynData(db, "nhomhang_sanpham", "nhomhang_sanpham", "nhomhang_fk", id, "0", false);

		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void closeDB()
	{
		try{
			if(this.spRs != null) this.spRs.close();
			if(this.rsnhomhang != null) this.rsnhomhang.close();
			if(this.nhanhangRs != null) this.nhanhangRs.close();
			if(this.nganhHangRs != null) this.nganhHangRs.close();
			if(this.dvdlRs != null) this.dvdlRs.close();
		}catch(Exception er){
			er.printStackTrace();
		}
		db.shutDown();
	}

	@Override
	public void init()
	{
		String query="select ma,ten,trangthai from NhomHang where pk_seq='"+this.id+"'";
		ResultSet rs=this.db.get(query);
		try
		{
			while(rs.next())
			{
				this.ma=rs.getString("ma");
				this.ten=rs.getString("ten");
				this.loainhom="2";
			}rs.close();
			//createRs();
			query="select nhomhang_fk,sanpham_fk from nhomhang_sanpham where  nhomhang_fk='"+this.id+"'";
			
			rs=this.db.get(query);
			while(rs.next())
			{
				this.spId+=rs.getString("sanpham_fk")+",";
			}
			rs.close();
			if(spId.length()>0)
			{
				spId=spId.substring(0,spId.length()-1);
			}
			query = "select a.PK_SEQ as spId ,a.MA as spMA,a.TEN as spTEN,c.DONVI as spDonVi \n"
					+"from nhomhang_sanpham b \n"
					+"left join erp_sanpham a on a.PK_SEQ = b.SanPham_FK  \n"
					+"left join DONVIDOLUONG c on c.PK_SEQ=a.DVDL_FK  \n"
					+"where NhomHang_FK = '"+this.id+"'  \n"
					+" order by a.MA asc \n";
			System.out.println("rsnhomhang: \n"+query);
			this.rsnhomhang = db.get(query);
			query="select a.PK_SEQ as spId,a.MA as spMA,a.TEN as spTEN,b.DONVI as spDonVi from erp_sanpham a \n"+
					" left join DONVIDOLUONG b on b.PK_SEQ=a.DVDL_FK  \n"+
					//" where a.PK_SEQ not in (select sanpham_fk from nhomhang_sanpham where  nhomhang_fk='"+this.id+"') \n"+
					" where a.PK_SEQ not in (select sanpham_fk from nhomhang_sanpham) \n"+
					" order by a.MA asc \n";
			System.out.println("sprs: \n"+query);
			this.spRs=this.db.get(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
	}

	public void createRs()
	{

		String query="select a.PK_SEQ as spId,a.MA as spMA,a.TEN as spTEN,b.DONVI as spDonVi from erp_sanpham a left join DONVIDOLUONG b on b.PK_SEQ=a.DVDL_FK where a.PK_SEQ not in (select sanpham_fk from nhomhang_sanpham) order by a.MA asc ";
		this.spRs=this.db.get(query);
		

	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public String getLoainhom()
  {
  	return loainhom;
  }

	public void setLoainhom(String loainhom)
  {
  	this.loainhom = loainhom;
  }

	
	public String getNhanHangId()
  {
  	return nhanHangId;
  }

	public void setNhanHangId(String nhanHangId)
  {
  	this.nhanHangId = nhanHangId;
  }

	public ResultSet getNganhHangRs()
  {
  	return nganhHangRs;
  }

	public void setNganhHangRs(ResultSet nganhHangRs)
  {
  	this.nganhHangRs = nganhHangRs;
  }

	public ResultSet getNhanhangRs()
  {
  	return nhanhangRs;
  }

	public void setNhanhangRs(ResultSet nhanhangRs)
  {
  	this.nhanhangRs = nhanhangRs;
  }
	
	String spId;

	public String getSpId()
  {
  	return spId;
  }

	public void setSpId(String spId)
  {
  	this.spId = spId;
  }
	
	ResultSet spRs;

	public ResultSet getSpRs()
  {
  	return spRs;
  }

	public void setSpRs(ResultSet spRs)
  {
  	this.spRs = spRs;
  }
	
}
