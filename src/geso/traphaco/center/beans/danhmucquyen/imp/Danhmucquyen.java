package geso.traphaco.center.beans.danhmucquyen.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.beans.danhmucquyen.IDanhmucquyen;
import geso.traphaco.center.db.sql.dbutils;

public class Danhmucquyen implements IDanhmucquyen
{
	String Ten;
	String DienGiai;
	String UserId;
	String Msg;
	String TrangThai;
	String Id;
	
	dbutils db ;
	
	ResultSet rsnoidungnhap;
	
	
	public Danhmucquyen(String id)
	{
		this.Id=id;
		Ten = "";
		DienGiai = "";
		TrangThai = "0";
		Msg = "";
		this.loaiMenu="0";
		this.cbChot="";
		this.cbHienThi="";
		this.cbHuyChot="";
		this.cbSua="";
		this.cbThem="";
		this.cbXoa="";
		this.cbXem="";
		this.cbChuyen = "";
		this.cbSMS = "";
		this.cbFAX = "";
		this.cbHienThiALL = "";
		this.cbXuatExcel = "";
		this.ungdungIds="";
		db= new dbutils();

		if(this.Id.length()>0)
		{
			init();
		}
		createRs();
		
	}
	
	
	
	public void setTen(String Ten)
	{
		this.Ten = Ten;
		
	}
	
	public String getTen()
	{
		
		return this.Ten;
	}
	
	public void setDiengiai(String DienGiai)
	{
		this.DienGiai = DienGiai;
	}
	
	public String getDiengiai()
	{
		return this.DienGiai;
	}
	
	
	public void init()
	{
		String sql = "select * from DanhMucQuyen where pk_seq ='" + this.Id + "'";
		ResultSet tb = db.get(sql);
		try
		{
			while (tb.next())
			{
				this.Ten = tb.getString("ten");
				this.DienGiai = tb.getString("diengiai");
				this.loaiMenu= 	tb.getString("LoaiMenu");
			}
			if(tb!=null)tb.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		sql =   "  select isnull(hienThi,0) as hienThi, dmq_fk, cast(ungdung_fk  as nvarchar(20)) as ungdung_fk ,isnull(them,'0') as them ,isnull(xoa,'0') as xoa,isnull(sua,'0') as sua, isnull(xem,'0') as xem,isnull(chot,'0') as chot,isnull(HuyChot,'0') as HuyChot, isnull(CHUYEN,'0') as CHUYEN, " +
				" 	isnull(SMS, '0') as SMS, isnull(FAX, '0') as FAX, isnull(hienthiALL, '0') as hienthiALL, isnull(xuatExcel, '0') as xuatExcel	" +
				"  from nhomquyen where DMQ_fk ='" + this.Id + "'"+
				"  union all " +
				"  select isnull(hienThi,0) as hienThi, dmq_fk,nd.ma as ungdung_fk,isnull(them,'0') as them ,isnull(xoa,'0') as xoa,isnull(sua,'0') as sua, isnull(xem,'0') as xem,isnull(chot,'0') as chot,isnull(HuyChot,'0') as HuyChot, isnull(CHUYEN,'0') as CHUYEN, " +
			" 	isnull(SMS, '0') as SMS, isnull(FAX, '0') as FAX, isnull(hienthiALL, '0') as hienthiALL, isnull(xuatExcel, '0') as xuatExcel	" +
			"  	from DANHMUCQUYEN_NOIDUNGNHAP b " +
			" 	inner join erp_noidungnhap nd on nd.pk_seq= b.NOIDUNGNHAP_FK " +
			"   where DMQ_fk ='" + this.Id + "'";

		System.out.println("[SQL__INIT]"+sql);

		tb =db.get(sql);
		int i=0;
		try
		{
			while(tb.next())
			{
				if (i == 0)
				{
					ungdungIds += tb.getString("ungdung_fk");

					if(tb.getInt("Them")==1)
						cbThem += tb.getString("ungdung_fk");

					if(tb.getInt("Xoa")==1)
						cbXoa += tb.getString("ungdung_fk");

					if(tb.getInt("Sua")==1)
						cbSua += tb.getString("ungdung_fk");

					if(tb.getInt("Xem")==1)
						cbXem += tb.getString("ungdung_fk");

					if(tb.getInt("Chot")==1)
						cbChot += tb.getString("ungdung_fk");

					if(tb.getInt("HuyChot")==1)
						cbHuyChot += tb.getString("ungdung_fk");

					if(tb.getInt("HienThi")==1)
						cbHienThi += tb.getString("ungdung_fk");

					if(tb.getInt("CHuyen")==1)
						cbChuyen += tb.getString("ungdung_fk");
					
					if(tb.getInt("SMS")==1)
						cbSMS += tb.getString("SMS");
					
					if(tb.getInt("FAX")==1)
						cbFAX += tb.getString("FAX");
					
					if(tb.getInt("hienthiALL")==1)
						cbHienThiALL += tb.getString("hienthiALL");
					
					if(tb.getInt("xuatExcel")==1)
						cbXuatExcel += tb.getString("xuatExcel");
				}
				else
				{
					ungdungIds += "," + tb.getString("ungdung_fk");

					if(tb.getInt("Them")==1)
						cbThem += "," + tb.getString("ungdung_fk");

					if(tb.getInt("Xoa")==1)
						cbXoa +=  "," +  tb.getString("ungdung_fk");

					if(tb.getInt("Sua")==1)
						cbSua += "," +   tb.getString("ungdung_fk");

					if(tb.getInt("Xem")==1)
						cbXem +=  "," +   tb.getString("ungdung_fk");

					if(tb.getInt("Chot")==1)
						cbChot += "," +   tb.getString("ungdung_fk");

					if(tb.getInt("HuyChot")==1)
						cbHuyChot += "," +  tb.getString("ungdung_fk");

					if(tb.getInt("HienThi")==1)
						cbHienThi +="," +   tb.getString("ungdung_fk");

					if(tb.getInt("CHuyen")==1)
						cbChuyen +="," + tb.getString("ungdung_fk");
					
					if(tb.getInt("SMS")==1)
						cbSMS +="," + tb.getString("ungdung_fk");
					
					if(tb.getInt("FAX")==1)
						cbFAX +="," + tb.getString("ungdung_fk");
					
					if(tb.getInt("hienthiALL")==1)
						cbHienThiALL +="," + tb.getString("ungdung_fk");
					
					if(tb.getInt("xuatExcel")==1)
						cbXuatExcel +="," + tb.getString("ungdung_fk");
				}
				i++;
			}
			if(tb!=null)
				tb.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("[cb SMS]" + this.cbSMS);
		System.out.println("[cb FAX]" + this.cbFAX);
		System.out.println("[cb HIEN THI ALL]" + this.cbHienThiALL);
		
	}
	
	public ResultSet getUngdung()
	{
		return ungdungRs;
	}
	
	public void setUserId(String UserId)
	{
		this.UserId = UserId;
		
	}
	
	public String getUserId()
	{
		return this.UserId;
	}
	
	public boolean update(HttpServletRequest request)
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String sql = "update DanhMucQuyen set LoaiMENU = '" + this.loaiMenu + "', ten = N'" + this.Ten + "', diengiai = N'" + this.DienGiai + "', nguoisua ='" + this.UserId + "', ngaysua ='" + getDateTime() + "', hoatdong ='" + this.TrangThai + "' where pk_seq = '" + this.Id + "'";
			db.update(sql);
			if (!db.update(sql))
			{
				this.Msg = sql;
				db.update("rollback");
				return false;
			}
			
			//XOA UNG DUNG CU
			sql =   "delete from NhanVien_UngDung  "+ 
					"where nhanvien_Fk   "+
					"	in   "+ 
					"	(  "+
					"		select nhanvien_fk from PHANQUYEN where dmq_Fk = '" + this.Id + "' "+
					"	) "; 
					//"  and ungdung_fk in ( select UngDung_fk from nhomquyen where DMQ_fk = '" + this.Id + "' ) ";
			if (!db.update(sql))
			{
				this.Msg = sql;
				db.update("rollback");
				return false;
			}
			
			sql = "delete from nhomquyen where DMQ_fk = '" + this.Id + "'";
			if (!db.update(sql))
			{
				this.Msg = sql;
				db.update("rollback");
				return false;
				
			}
			sql = "delete from DANHMUCQUYEN_NOIDUNGNHAP where DMQ_fk = '" + this.Id + "'";
			if (!db.update(sql))
			{
				this.Msg = sql;
				db.update("rollback");
				return false;
				
			}
			
			String[] ungdungIds = request.getParameterValues("ungdungId");
			if (ungdungIds != null)
			{				
				for (int i = 0; i < ungdungIds.length; i++)
				{
					String ungdung = ungdungIds[i];
					String hienthi = this.cbHienThi.indexOf(ungdung)  <0 ? "0" : "1";
					String them = this.cbThem.indexOf(ungdung)  <0  ? "0" : "1";
					String xoa = this.cbXoa.indexOf(ungdung) <0  ? "0" : "1";
					String sua = this.cbSua.indexOf(ungdung)<0 ? "0" : "1";
					String xem =this.cbXem.indexOf(ungdung)<0 ? "0" : "1";
					String chot = this.cbChot.indexOf(ungdung)<0 ? "0" : "1";
					String huychot = this.cbHuyChot.indexOf(ungdung)<0? "0" : "1";
					String chuyen = this.cbChuyen.indexOf(ungdung)<0? "0" : "1";

					String sms = this.cbSMS.indexOf(ungdung)<0 ? "0" : "1";
					String fax = this.cbFAX.indexOf(ungdung)<0 ? "0" : "1";
					String hienthiALL = this.cbHienThiALL.indexOf(ungdung)<0 ? "0" : "1";
					String xuatExcel = this.cbXuatExcel.indexOf(ungdung)<0 ? "0" : "1";

					sql = "insert into Nhomquyen(dmq_fk, ungdung_fk, them, xoa, sua, xem, chot, huychot, HienThi, Chuyen, SMS, FAX, hienthiALL, xuatExcel)  " +
							" values('" + this.Id + "','" + ungdung + "','" + them + "','" + xoa + "','" + sua + "','" + xem + "','" + chot + "','" + huychot + "','"+hienthi+"', '" + chuyen + "', '" + sms + "', '" + fax + "', '" + hienthiALL + "', '" + xuatExcel + "')";

					System.out.println("[Nhomquyen]" + sql);

					if (!db.update(sql))
					{
						this.Msg = sql;
						db.update("rollback");
						return false;
					}
				}
			}
			
			
			String[] noidungxuatid = request.getParameterValues("noidungxuatid");
			if (noidungxuatid != null)
			{				
				for (int i = 0; i < noidungxuatid.length; i++)
				{
					String ungdung = noidungxuatid[i];
					String hienthi = this.cbHienThi.indexOf(ungdung)  <0 ? "0" : "1";
					String them = this.cbThem.indexOf(ungdung)  <0  ? "0" : "1";
					String xoa = this.cbXoa.indexOf(ungdung) <0  ? "0" : "1";
					String sua = this.cbSua.indexOf(ungdung)<0 ? "0" : "1";
					String xem =this.cbXem.indexOf(ungdung)<0 ? "0" : "1";
					String chot = this.cbChot.indexOf(ungdung)<0 ? "0" : "1";
					String huychot = this.cbHuyChot.indexOf(ungdung)<0? "0" : "1";
					String chuyen = this.cbChuyen.indexOf(ungdung)<0? "0" : "1";

					String sms = this.cbSMS.indexOf(ungdung)<0 ? "0" : "1";
					String fax = this.cbFAX.indexOf(ungdung)<0 ? "0" : "1";
					String hienthiALL = this.cbHienThiALL.indexOf(ungdung)<0 ? "0" : "1";
					String xuatExcel = this.cbXuatExcel.indexOf(ungdung)<0 ? "0" : "1";

					sql = 	" insert into DANHMUCQUYEN_NOIDUNGNHAP(dmq_fk, noidungnhap_fk, them, xoa, sua, xem, chot, huychot, HienThi, Chuyen, SMS, FAX, hienthiALL, xuatExcel)  " +
							" select '" + this.Id + "',pk_Seq,'" + them + "','" + xoa + "','" + sua + "','" + xem + "','" + chot + "','" + huychot + "','"+hienthi+"', '" + chuyen + "', '" + sms + "', '" + fax + "', '" + hienthiALL + "', '" + xuatExcel + "' from erp_noidungnhap where ma='"+ungdung+"'";

					System.out.println("[Nhomquyen]" + sql);

					if (!db.update(sql))
					{
						this.Msg = sql;
						db.update("rollback");
						return false;
					}
				}
			}
			
			sql = "	insert into NhanVien_UngDung( UngDung_fk, NhanVien_fk )  "+
				  " select distinct nq.UngDung_fk, pq.Nhanvien_fk " + 
				  " from NHOMQUYEN nq inner join phanquyen pq on pq.DMQ_fk = nq.DMQ_fk  "+
				  "   inner join UNGDUNG ud on ud.PK_SEQ = nq.UngDung_fk  "+
				  //"  where nq.HienThi = 1 and ud.TRANGTHAI=1 and pq.dmq_Fk = '" + this.Id + "' ";
				  " where nq.HienThi = 1 and ud.TRANGTHAI = 1 and pq.nhanvien_fk in ( select nhanvien_fk from PHANQUYEN where dmq_Fk = '" + this.Id + "' ) ";
			System.out.println(":: NHAN VIEN - UNG DUNG: " + sql);
			if (!db.update(sql))
			{
				this.Msg = sql;
				db.update("rollback");
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception er)
		{
			er.printStackTrace();
			db.update("rollback");
			this.Msg = er.toString();
			return false;
		}
	}
	
	public boolean save(HttpServletRequest request)
	{
		try
		{
			db.getConnection().setAutoCommit(false);

			String sql = "	insert into DANHMUCQUYEN(Ten,DienGiai,NguoiTao,NguoiSua,NgayTao,NgaySua,HoatDong,LoaiMenu)  "+
						 "	values(N'"+this.Ten+"',N'"+this.DienGiai+"','"+this.UserId+"','"+this.UserId+"','"+this.getDateTime()+"','"+this.getDateTime()+"','"+this.TrangThai+"','"+this.loaiMenu+"')  ";
			if (!db.update(sql))
			{
				db.update("rollback");
				return false;
			} 
			sql = "select IDENT_CURRENT('DANHMUCQUYEN') as spId";
			ResultSet rs = this.db.get(sql);

			rs.next();
			this.Id = rs.getString("spId");
			if(rs!=null)rs.close();

			String[] ungdungIds = request.getParameterValues("ungdungId");
			if (ungdungIds != null)
			{				
				for (int i = 0; i < ungdungIds.length; i++)
				{
					String ungdung = ungdungIds[i];
					String hienthi = this.cbHienThi.indexOf(ungdung)  <0 ? "0" : "1";
					String them = this.cbThem.indexOf(ungdung)  <0  ? "0" : "1";
					String xoa = this.cbXoa.indexOf(ungdung) <0  ? "0" : "1";
					String sua = this.cbSua.indexOf(ungdung)<0 ? "0" : "1";
					String xem =this.cbXem.indexOf(ungdung)<0 ? "0" : "1";
					String chot = this.cbChot.indexOf(ungdung)<0 ? "0" : "1";
					String huychot = this.cbHuyChot.indexOf(ungdung)<0 ? "0" : "1";
					String chuyen = this.cbChuyen.indexOf(ungdung)<0 ? "0" : "1";
					
					String sms = this.cbSMS.indexOf(ungdung)<0 ? "0" : "1";
					String fax = this.cbFAX.indexOf(ungdung)<0 ? "0" : "1";
					String hienthiALL = this.cbHienThiALL.indexOf(ungdung)<0 ? "0" : "1";
					String xuatExcel = this.cbXuatExcel.indexOf(ungdung)<0 ? "0" : "1";
					
					sql = "insert into Nhomquyen(dmq_fk, ungdung_fk, them, xoa, sua, xem, chot, huychot, HienThi, Chuyen, SMS, FAX, hienthiALL, xuatExcel)  " +
							" values('" + this.Id + "','" + ungdung + "','" + them + "','" + xoa + "','" + sua + "','" + xem + "','" + chot + "','" + huychot + "','"+hienthi+"', '" + chuyen + "', '" + sms + "', '" + fax + "', '" + hienthiALL + "', '" + xuatExcel + "')";

					System.out.println("[Nhomquyen]" + sql);

					if (!db.update(sql))
					{
						this.Msg = sql;
						db.update("rollback");
						return false;
					}
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception er)
		{
			db.update("rollback");
			er.printStackTrace();
			return false;

		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void setTrangthai(String TrangThai)
	{
		this.TrangThai = TrangThai;
	}
	
	public String getTrangThai()
	{
		return this.TrangThai;
	}
	
	public void setMsg(String Msg)
	{
		this.Msg = Msg;
		
	}
	
	public String getMsg()
	{
		return this.Msg;
	}
	
	public void setId(String Id)
	{
		this.Id = Id;
	}
	
	public String getId()
	{
		return this.Id;
	}
	
	public String getCbChot()
  {
  	return cbChot;
  }

	public void setCbChot(String cbChot)
  {
  	this.cbChot = cbChot;
  }

	public String getCbHienThi()
  {
  	return cbHienThi;
  }

	public void setCbHienThi(String cbHienThi)
  {
  	this.cbHienThi = cbHienThi;
  }

	public ResultSet getNhaphanphoi()
	{
		
		ResultSet rs = db.get("select a.pk_seq,a.ten,b.ten as khuvuc from nhaphanphoi a, khuvuc b where a.khuvuc_fk = b.pk_seq order by a.KHUVUC_FK");
		return rs;
	}
	
	
	public void DBClose()
	{
		
		if (this.db != null)
		{
			db.shutDown();
		}
	}
	
	String cbThem,cbXoa,cbSua,cbChot,cbHienThi,cbXem,cbHuyChot, cbChuyen, cbSMS, cbFAX, cbHienThiALL, cbXuatExcel;
	public String getCbThem()
  {
  	return cbThem;
  }

	public void setCbThem(String cbThem)
  {
  	this.cbThem = cbThem;
  }

	public String getCbXoa()
  {
  	return cbXoa;
  }

	public void setCbXoa(String cbXoa)
  {
  	this.cbXoa = cbXoa;
  }

	public String getCbSua()
  {
  	return cbSua;
  }

	public void setCbSua(String cbSua)
  {
  	this.cbSua = cbSua;
  }

	public String getCbXem()
  {
  	return cbXem;
  }

	public void setCbXem(String cbXem)
  {
  	this.cbXem = cbXem;
  }

	public String getCbHuyChot()
  {
  	return cbHuyChot;
  }

	public void setCbHuyChot(String cbHuyChot)
  {
  	this.cbHuyChot = cbHuyChot;
  }

	String loaiMenu;

	public String getLoaiMenu() {
		return loaiMenu;
	}

	public void setLoaiMenu(String loaiMenu) {
		this.loaiMenu = loaiMenu;
	}
	
	ResultSet ungdungRs;


	public ResultSet getUngdungRs()
  {
  	return ungdungRs;
  }

	public void setUngdungRs(ResultSet ungdungRs)
  {
  	this.ungdungRs = ungdungRs;
  }
	
	
	String ungdungIds;
	
	
	public String getUngdungIds()
	{
		return ungdungIds;
	}
	
	
	public void setUngdungIds(String ungdungIds)
	{
		this.ungdungIds = ungdungIds;
	}



	
  public void createRs()
  {
		String	query = 
				"	select   B.PK_SEQ as DanhMuc_FK,a.PK_SEQ as pk_Seq  , c.ten + ' ___  ' + b.ten as TENDANHMUC, a.ten , a.servlet,  \n"+
				"			a.parameters, c.sott as stt1, b.sott as stt2, a.sott         \n"+
				"from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq \n"+             
				"	inner join ungdung c on b.ungdung_fk = c.pk_seq  \n"+            
				"where a.level = '3' and b.level = '2'  and a.loaiMenu='"+this.loaiMenu+"' and a.TrangThai=1 \n"+          
				"union all           \n"+
				"select c.PK_SEQ as DanhMuc_FK ,a.PK_SEQ ,  c.ten   as TENDANHMUC, a.ten, a.servlet, a.parameters, c.sott as stt1, a.sott as stt2, a.sott \n"+
				"	from ungdung a  inner join ungdung c on a.ungdung_fk = c.pk_seq  \n"+            
				"where a.level = '3' and c.level = '1'  and a.loaiMenu='"+this.loaiMenu+"'  and a.TrangThai=1  \n"+           
				"order by stt1 asc, stt2 asc, sott asc \n" ;	
		
		System.out.println("[UNGDUNG]\n:"+query+"\n============================================");
		
		this.ungdungRs=this.db.get(query);
		
		query="   select  a.PK_SEQ as DanhMuc_FK ,A.MA ,a.ma + '-'+ a.TEN as TENDANHMUC from erp_noidungnhap a ";
		this.rsnoidungnhap=db.get(query);
		
	  
  }



	
	public String getCbChuyen() {

		return this.cbChuyen;
	}



	public void setCbChuyen(String cbChuyen) {

		this.cbChuyen = cbChuyen;
	}



	
	public String getCbSMS() {
		
		return this.cbSMS;
	}



	
	public void setCbSMS(String cbSms) {
		
		this.cbSMS = cbSms;
	}



	
	public String getCbFAX() {
		
		return this.cbFAX;
	}



	
	public void setCbFAX(String cbFAX) {
		
		this.cbFAX = cbFAX;
	}



	
	public String getCbHienThiALL() {
		
		return this.cbHienThiALL;
	}



	
	public void setCbHienThiALL(String cbHienthiALL) {
		
		this.cbHienThiALL = cbHienthiALL;
	}



	
	public String getCbXuatExcel() {

		return this.cbXuatExcel;
	}



	public void setCbXuatExcel(String cbXuatExcel) {

		this.cbXuatExcel = cbXuatExcel;
	}



	@Override
	public ResultSet getnoidungRs() {
		// TODO Auto-generated method stub
		return this.rsnoidungnhap;
	}
	
}
