package geso.traphaco.erp.beans.nhomkhoanmuc.imp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.nhomkhoanmuc.IErpNhomKhoanMuc;
import geso.traphaco.erp.beans.nhomkhoanmuc.IErpNhomKhoanMucList;

public class ErpNhomKhoanMucList  extends Phan_Trang implements IErpNhomKhoanMucList{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7098075456466668852L;
	String id;
	String Ten;
	String trangthai;
	String Ma;
	String ngaytao;
	String ngaysua;
	String userid;
	String msg;
	String congtyId;
	String nppId;
	ResultSet rsNhomKhoanMuc;
	dbutils db;

	String chixem;
	
	public ErpNhomKhoanMucList()
	{
		this.id = "";
		this.Ten = "";
		this.trangthai = "";
		this.Ma = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.userid = "";
		this.msg = "";
		this.congtyId = "";
		this.nppId = "";
		this.db = new dbutils();
		this.chixem = "0";
	}

	public ErpNhomKhoanMucList(String id)
	{
		this.id = id;
		this.Ten = "";
		this.trangthai = "";
		this.Ma = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.userid = "";
		this.msg = "";
		this.congtyId = "";
		this.nppId = "";
		this.db = new dbutils();
		this.chixem = "0";
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
		return this.Ten;
	}

	public void setTen(String Ten)
	{
		this.Ten = Ten;
	}

	public void setMa(String Ma)
	{
		this.Ma = Ma;
	}

	public String getMa()
	{
		return this.Ma;
	}

	public String getNgayTao()
	{
		return this.ngaytao;
	}

	public String getNgaySua()
	{
		return this.ngaysua;
	}

	public void setNgayTao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}

	public void setNgaySua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}

	public String getUserId()
	{
		return this.userid;
	}

	public void setUserId(String userid)
	{
		this.userid = userid;
	}

	public String getTrangThai()
	{
		return trangthai;
	}

	public void setTrangThai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public String getCongtyId() {
		return congtyId;
	}

	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public void Init()
	{
		this.rsNhomKhoanMuc = null;
		String query = " Select NKM.PK_SEQ ,NKM.Ma,NKM.Ten,NKM.TrangThai,NKM.NgayTao,NKM.NgaySua,NKM.NguoiTao,NKM.NguoiSua"
			+ " From ERP_NHOMKHOANMUC NKM "
			+" Where 1=1  and congty_fk="+this.congtyId;
		System.out.println("Search" + query);
		if (this.Ma.length() > 0)
		{
			query += " and  NKM.Ma like N'%" + this.Ma + "%' ";
		}
		if (this.Ten.length() > 0)
		{
			query += " and NKM.Ten like N'%" + this.Ten + "%'";
		}
		this.rsNhomKhoanMuc=this.db.get(query);
		System.out.println("sssssssss"+query);
		
//		if (this.congtyId.length() > 0)
//		{
//			query += " and CONGTY_FK = " + this.congtyId ;
//		}
		/*if (this.nppId.length() > 0)
		{
			query += " and NPP_FK = " + this.nppId ;
		}*/
//		this.rsNhomKhoanMuc =createSplittingData( 50, 10, " PK_SEQ DESC,TRANGTHAI  ", query); 
		System.out.println("Search LoaiTaiKhoan : " + query);
		
		
	}

	

	public boolean Create()
	{
		if(!CheckValid())
		{
			return false;
		}
		String query = "";
		try
		{
			query = "Insert into ERP_NHOMKHOANMUC(Ma,Ten,NgayTao,NgaySua,NguoiTao,NguoiSua,TrangThai, Congty_fk, NPP_fk) values(" + "N'" +
				this.Ma + "',N'" + this.Ten + "','" + getDateTime() + "','" + getDateTime() + "','" + this.userid + "','" +
				this.userid + "' ,'" + this.trangthai + "' ,'" + this.congtyId +  "' ,'" + this.nppId + "')";
		
			System.out.println("Create Query " + query);
			this.db.getConnection().setAutoCommit(false);
			if (this.db.update(query))
			{
				System.out.println("Tao duoc ERP_NHOMKHOANMUC ");

				String currentId = "";
				query = "Select IDENT_CURRENT('ERP_NHOMKHOANMUC') as currentId";
				ResultSet rsId = this.db.get(query);
				if (rsId != null)
				{
					while (rsId.next())
					{
						currentId = rsId.getString("currentId");
						System.out.println("ID" + currentId);
					}
					rsId.close();
				}
			} else
			{
				this.msg = "Không thể tạo mới nhóm chi phí :" + query;
				this.db.update("rollback");
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} catch (SQLException e)
		{
			this.db.update("rollback");
			this.msg = "Không thể tạo mới nhóm chi phí :" + query;
			this.db.shutDown();
			System.out.println("Exception");
			return false;
		}
	}

	public boolean Update()
	{
		
		if(!CheckValid())
		{
			return false;
		}
		String query = "Update ERP_NHOMKHOANMUC set Ma=N'" + this.Ma + "',Ten=N'" + this.Ten + "',NgaySua='" +
			getDateTime() + "',NguoiSua='" + this.userid + "',TrangThai='" + this.trangthai + "" + "' Where PK_SEQ='" +this.id + "'";
		
		System.out.println("update Nhom chi phi: " + query);
		try
		{
			this.db.getConnection().setAutoCommit(false);
			if(!this.db.update(query))
			{
				this.msg = "Không thể cập nhật:" + query;
				this.db.update("rollback");
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} catch (SQLException e)
		{
			this.msg = "Không thể cập nhật:" + query;
			this.db.update("rollback");
			System.out.println(e.getMessage());
			this.db.shutDown();
		}
		return false;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public ResultSet rsNhomKhoanMuc()
	{
		return this.rsNhomKhoanMuc;
	}

	public void rsNhomKhoanMuc(ResultSet rsNhomKhoanMuc)
	{
		this.rsNhomKhoanMuc = rsNhomKhoanMuc;
	}

	
//	public boolean Deletencp()
//	{
//	   	ResultSet rs = db.get("select distinct COUNT(b.Loainhacungcap_fk) as num from ERP_LOAINHACUNGCAP a, ERP_NHACUNGCAP b " +
//	   								"where a.pk_seq = b.Loainhacungcap_fk and a.PK_SEQ ='" + this.id + "'");
//	   	
//	   	try{
//	   	    while(rs.next())
//	   		if(rs.getString("num").equals("0")){	   			
//	   			String query="DELETE FROM ERP_LOAINHACUNGCAP WHERE PK_SEQ='"+this.id+"'";
//	   			if (!this.db.update(query)) {
//	   				this.msg="Không thể xóa loại nhà cung cấp này !"+query;
//					return false;
//	   			}
//	   		}
//	   		else
//	   			this.msg="Loại nhà cung cấp này đã có nhà cung cấp rồi, không thể xóa được !";
//	   	}catch(java.sql.SQLException e){}
//	  
//		return false;
//	}
	
	public boolean CheckValid()
	{
		String query="";
		
		if(this.id.length() > 0)
			query="Select count(*) sodong FROM ERP_NHOMKHOANMUC WHERE Ma=N'"+this.Ma+"' and pk_seq <> '" + this.id 
			+"' and congty_fk ='" + this.congtyId +"' ";//and npp_fk ='" + this.nppId + "'";
		else
			query="Select count(*) sodong FROM ERP_NHOMKHOANMUC WHERE Ma=N'"+this.Ma+"'";
		
		try
		{
			System.out.println("CheckValid ERP_NHOMKHOANMUC : " + query);
			int sodong = 0;
			ResultSet rs = this.db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
				System.out.println("So dong la: " + sodong + "\n");
				if(sodong > 0)
				{
					this.msg="Mã loại tài khoản này đã có vui lòng chọn mã khác";
					return false;
				}
			}else
				return false;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public void closeDB(){
		try{
			if(this.rsNhomKhoanMuc != null) this.rsNhomKhoanMuc.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
		
	}
	
	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}



	
	public ResultSet getRsNhomKhoanMuc() {
		return rsNhomKhoanMuc;
	}

	public void setRsNhomKhoanMuc(ResultSet rsNhomKhoanMuc) {
		this.rsNhomKhoanMuc = rsNhomKhoanMuc;
	}
	
}
