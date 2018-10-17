package geso.traphaco.erp.beans.banggiancc.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.banggiancc.IBangGiaNcc;
import geso.traphaco.erp.beans.banggiancc.IBangGiaNcc_Sp;

public class BangGiaNcc implements IBangGiaNcc
{
	String userId;
	String id;
	
	String ten;
	
	String chietKhau;
	
	String[] nppIdCks = new String[0];
	String[] nppChietKhaus = new String[0];

	public String getChietKhau() 
	{
		return chietKhau;
	}


	public void setChietKhau(String chietKhau) 
	{
		this.chietKhau = chietKhau;
	}

	String nccId;
	ResultSet nccRs;

	String dvkdId;
	ResultSet dvkdRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	String nhomkenhId;
	ResultSet nhomkenhRs;
	
	String nppId;
	ResultSet nppRs;
	
	String trangthai;
	String msg;
	
	List<IBangGiaNcc_Sp> spList;
	dbutils db;
	
	NumberFormat formater = new DecimalFormat("##,###,###");
	NumberFormat formater2 = new DecimalFormat("##,###,###.####");
	public BangGiaNcc()
	{
		this.userId = "";
		this.id = "";
		this.ten = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.nhomkenhId = "";
		this.trangthai = "1";
		this.msg = "";
		this.nppId = "";
		this.nccId = "";
		this.tungay="";
		this.chietKhau ="";
		this.spList = new ArrayList<IBangGiaNcc_Sp>();
		this.db = new dbutils();
	}
	
	
	public BangGiaNcc(String id)
	{
		this.userId = "";
		this.id = id;
		this.kbhId = "";
		this.nhomkenhId = "";
		this.dvkdId = "";
		this.trangthai = "1";
		this.msg = "";
		this.nppId = "";
		this.nccId = "";
		this.tungay ="";
		this.chietKhau ="";
		this.spList = new ArrayList<IBangGiaNcc_Sp>();
		this.db = new dbutils();	
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}


	public void init() 
	{
		String query = "select TEN, NCC_FK, DVKD_FK, NHOMKENH_FK, TRANGTHAI, TuNgay from ERP_BANGGIAMUANCC where PK_SEQ = '" + this.id + "'";
		ResultSet rs = db.get(query);
	
		try 
		{
			while(rs.next())
			{
				this.ten = rs.getString("TEN");
				this.dvkdId = rs.getString("DVKD_FK");
				this.nhomkenhId = rs.getString("NHOMKENH_FK");
				this.trangthai = rs.getString("TRANGTHAI");
				
				this.tungay = rs.getString("TuNgay");
				//this.chietKhau = rs.getFloat("ChietKhau")==0?"": formater.format(rs.getDouble("ChietKhau") ) ;
				
			}
			rs.close();
		
		
			query = "select ncc_fk as nccid"+ 
					" from ERP_BANGGIAMUANCC_NCC where banggia_FK = '" + this.id + "'";
	        
	        rs = db.get(query);
	        System.out.println("NCC ID" + query);
	        this.nccId = "";
	        while (rs.next())
	        {
	        	this.nccId += rs.getString("nccid") + ",";
	        }
	        rs.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		this.createRs();
	}
	
	public boolean createBanggia()
	{	
		try 
		{
			if(checkExits(db))
			{
				this.msg = "Ngày bắt đầu áp dụng bảng giá không được phép nhỏ hơn từ ngày áp dụng của bảng giá đang có";
				return false;
			}
			
			
			if(this.ten.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tên bảng giá";
				return false;
			}
			
			if(this.dvkdId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}
			
			/*if(this.nhomkenhId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nhóm kênh bán hàng";
				return false;
			}*/
			
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong bảng giá";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into ERP_BANGGIAMUANCC(ten, ngaytao, ngaysua, nguoitao, nguoisua, dvkd_fk, NHOMKENH_FK, trangthai, TuNgay, DADUYET) " +
							"values(N'" + this.ten + "','" + getDateTime() + "','" + getDateTime() + "','" + this.userId + "', " +
								"'" + this.userId + "','" + this.dvkdId + "','" + 100001 + "', '" + this.trangthai + "', '"+tungay+"', 0)";
			
			System.out.println("__Tao moi ERP_BANGGIAMUANCC: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_BANGGIAMUANCC " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ResultSet rsBg = this.db.get("select IDENT_CURRENT('ERP_BANGGIAMUANCC') as bgbanId");
			rsBg.next();
			
			String bgbanId = rsBg.getString("bgbanId");		
			
			if(this.nccId.trim().length()>0)
			{
				query = "insert into ERP_BANGGIAMUANCC_NCC(BANGGIA_FK, NCC_FK) select '" + bgbanId + "', pk_seq from ERP_NHACUNGCAP where pk_seq in (" + this.nccId + ")"; 	
				if(!db.update(query)){
					this.msg="Khong the cap nhat ERP_BANGGIAMUANCC_NCC .vui long thuc hien lai,hoac lien he voi admin de sua loi nay . Loi insert: "+query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			for(int i = 0; i < this.spList.size(); i++)
			{
				IBangGiaNcc_Sp sp = this.spList.get(i);
				String gia = sp.getGiaban().trim().length() <= 0 ? "0" : sp.getGiaban().trim().replaceAll(",", "");
				String chonban = sp.getChonban();
				if(gia.equals("0"))
					chonban = "0";
				
				query = "insert into ERP_BANGGIAMUANCC_SANPHAM (BANGGIAMUA_FK, sanpham_fk, giamua, trangthai)" +
						"values('" + bgbanId + "', '" + sp.getIdsp() + "','" +gia + "', '"+chonban+"')";
				if(!this.db.update(query))
				{				
					this.msg = "Không thể tạo mới ERP_BANGGIAMUANCC_SANPHAM " + query;
					db.getConnection().rollback();
					return false;
				}
				query = "insert into ERP_BANGGIAMUANCC_SANPHAM_LOG (BANGGIAMUA_FK, sanpham_fk, giamua, NGAYSUA, NGUOISUA)" +
						"values('" + bgbanId + "', '" + sp.getIdsp() + "','" +gia + "', GETDATE(), '" + this.userId + "')";
				if(!this.db.update(query))
				{				
					this.msg = "Không thể tạo mới ERP_BANGGIAMUANCC_SANPHAM_LOG " + query;
					db.getConnection().rollback();
					return false;
				}
			}		
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		return true;
	}
	
	
	private boolean checkHOPLE()
	{
		String query = " select count(*) as soDong " +
				       " from DONHANG a inner join DONHANG_SANPHAM b on a.pk_seq = b.donhang_fk " +
				       " where a.NGAYNHAP >= '" + this.tungay + "'" +
				       		" and sanpham_fk in ( select SANPHAM_FK from BANGGIAMUA_SANPHAM where BANGGIAMUA_FK = '" + this.id + "' and GIAMUA != 0 ) " +
				       		" and a.npp_fk in ( select a.NPP_FK from BANGGIAMUANPP_NPP a INNER JOIN BANGGIAMUANPP b on a.BANGGIAMUANPP_FK = b.PK_SEQ where b.BANGGIAMUA_FK = '" + this.id + "'  )  ";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
			{
				if(rs.next())
				{
					if( rs.getInt("soDong") >= 1 )
						return false;
				}
				rs.close();
			} 
			catch (Exception e) {e.printStackTrace(); }
		}
		
		return true;
	}
	
	public boolean updateBanggia() 
	{
		try 
		{
			
			if(this.ten.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tên bảng giá";
				return false;
			}
			
			if(this.dvkdId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}
			
			/*if(this.nhomkenhId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nhóm kênh bán hàng";
				return false;
			}*/
			
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong bảng giá";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_BANGGIAMUANCC set TuNgay = '"+this.tungay+"', ten = N'" + this.ten + "', ngaysua = '" + getDateTime() + "', " +
					"nguoisua = '" + this.userId + "', trangthai = '" + this.trangthai + "', DADUYET = 0 where pk_seq = '" + this.id + "'";
			
			System.out.println("1.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_BANGGIAMUANCC: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			String bgbanId=this.id;
			
			query="delete from ERP_BANGGIAMUANCC_SANPHAM where BANGGIAMUA_FK = "+bgbanId +" ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_BANGGIAMUANCC_SANPHAM: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query="delete from ERP_BANGGIAMUANCC_NCC where BANGGIA_FK = "+bgbanId +" ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_BANGGIAMUANCC_NCC: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			if(this.nccId.trim().length()>0)
			{
				query = "insert into ERP_BANGGIAMUANCC_NCC(BANGGIA_FK, NCC_FK) select '" + bgbanId + "', pk_seq from ERP_NHACUNGCAP where pk_seq in (" + this.nccId + ")"; 	
				if(!db.update(query)){
					this.msg="Khong the cap nhat ERP_BANGGIANCC_NCC .vui long thuc hien lai,hoac lien he voi admin de sua loi nay . Loi insert: "+query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			for(int i = 0; i < this.spList.size(); i++)
			{
				IBangGiaNcc_Sp sp = this.spList.get(i);
				String gia = sp.getGiaban().trim().length() <= 0 ? "0" : sp.getGiaban().trim().replaceAll(",", "");
				String chonban = sp.getChonban();
				if(gia.equals("0"))
					chonban = "0";
				
				query = "insert into ERP_BANGGIAMUANCC_SANPHAM(BANGGIAMUA_FK, sanpham_fk, GiaMua, trangthai) " +
						"values('" + bgbanId + "', '" + sp.getIdsp() + "','" +gia + "','"+chonban+"')";
				if(!this.db.update(query))
				{				
					this.msg = "Không thể tạo mới ERP_BANGGIAMUANCC_SANPHAM " + query;
					db.getConnection().rollback();
					return false;
				}
				query = "insert into ERP_BANGGIAMUANCC_SANPHAM_LOG (BANGGIAMUA_FK, sanpham_fk, giamua, NGAYSUA, NGUOISUA)" +
						"values('" + bgbanId + "', '" + sp.getIdsp() + "','" +gia + "', GETDATE(), '" + this.userId + "')";
				if(!this.db.update(query))
				{				
					this.msg = "Không thể tạo mới ERP_BANGGIAMUANCC_SANPHAM_LOG " + query;
					db.getConnection().rollback();
					return false;
				}
			}			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) { e1.printStackTrace();}
			return false;
		}
		return true;
	}
	
	public void DbClose() 
	{
		try 
		{
			this.db.shutDown();
			if(nppRs!=null)nppRs.close();
			if(kbhRs!=null)kbhRs.close();
			if(dvkdRs!=null)dvkdRs.close();
		} 
		catch (Exception e) {e.printStackTrace();}
		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}


	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public String getTen() {
		
		return this.ten;
	}

	
	public void setTen(String ten) {
		
		this.ten = ten;
	}

	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public ResultSet getDvkdRs() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.dvkdRs = dvkdRs;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}

	public List<IBangGiaNcc_Sp> getSpList() {
		
		return this.spList;
	}


	public void setSpList(List<IBangGiaNcc_Sp> spList) {
		
		this.spList = spList;
	}
	
	public void createRs()
	{
		String query = 
			"	select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId  from donvikinhdoanh a,nhacungcap_dvkd b  where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' "+ 
			"	order by a.donvikinhdoanh ";
		this.dvkdRs  =  this.db.get(query);
		
		this.nhomkenhRs = this.db.get("select * from NHOMKENH where TRANGTHAI = 1");
		
		if(this.nhomkenhId.length() > 0)
			this.kbhRs  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1' and pk_seq in (select kbh_fk from NHOMKENH_KENHBANHANG WHERE nk_fk = '"+this.nhomkenhId+"')");
		else
			this.kbhRs  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1'");
		
		this.nccRs = this.db.get("select pk_seq,ma, ten from erp_nhacungcap where trangthai = '1' and duyet = '1'");
		
		String querySP = "";
		if(this.dvkdId.trim().length() > 0 )
		{
			String banggia_fk = this.id.trim().length() <= 0 ? "0" : this.id.trim();
			
			querySP = 
				"	select a.PK_SEQ as spId, a.MA  as spMa, isnull(a.TEN, '') as spTen , ISNULL(b.donvi, 'NA') as donvi, isnull(c.giaMua, 0) as giaban,isnull(c.trangthai, 0) as trangthai "+
				"	from ERP_SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ "+   
				"		left join ERP_BANGGIAMUANCC_SANPHAM c on a.pk_seq = c.sanpham_fk  "+
				"		and c.BANGGIAMUA_FK = '"+banggia_fk+"'     "+
				"	where a.DVKD_FK = '"+this.dvkdId+"' and a.trangthai = '1' "  ;
			querySP +=	" order by spMa asc, spTen asc  ";
			
			System.out.println("--- Lay SP: " + querySP);
			
			
			if(this.spList.size() <= 0)
			{
				ResultSet rsSp = db.get(querySP);
				if(rsSp != null)
				{
					try 
					{
						List<IBangGiaNcc_Sp> spList = new ArrayList<IBangGiaNcc_Sp>();
						
						while(rsSp.next())
						{
							IBangGiaNcc_Sp sp = new BangGiaNcc_Sp();
							sp.setIdsp(rsSp.getString("spId"));
							sp.setMasp(rsSp.getString("spMa"));
							sp.setTensp(rsSp.getString("spTen"));
							sp.setDonvi(rsSp.getString("donvi"));
							sp.setGiaban( rsSp.getDouble("giaban")==0?  "" : formater2.format(rsSp.getDouble("giaban"))  );	
							sp.setChonban(rsSp.getString("trangthai"));
							spList.add(sp);
						}
						rsSp.close();
						
						this.spList = spList;
					} 
					catch (Exception e) 
					{
						System.out.println("Exception Khoi Tao: " + e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private boolean checkExits(dbutils db) 
	{
		String query=
				"select count(*) as sodong from ERP_BANGGIAMUANCC where DVKD_FK="+this.dvkdId+" and KBH_FK = "+this.kbhId+" " +
				" and ncc_fk = "+this.nccId+" AND TUNGAY > '" + this.tungay + "'    ";
		
		if(this.id.length() > 0)
			query+= " and pk_seq != " + this.id +" ";
				
		ResultSet rs = db.get(query);
		int sodong = 0;
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			} catch(Exception e) {e.printStackTrace(); sodong = 0; }
		}
		if(sodong > 0)
			return true;
		return false;
	}


	String tungay;
	@Override
	public String getTuNgay() 
	{
		return tungay;
	}


	@Override
	public void setTuNgay(String tungay) 
	{
		this.tungay =tungay;
	}
	
	@Override
	public ResultSet getNppRs() 
	{
		return nppRs;
	}


	@Override
	public void setNppRs(ResultSet NppRs) 
	{
		this.nppRs	= NppRs;
	}


	@Override
	public String getNppId() 
	{
		return this.nppId;
	}


	@Override
	public void setNppId(String nppId) 
	{
		this.nppId =nppId;
	}


	@Override
	public String[] getNppIdCks() {
		return this.nppIdCks;
	}


	@Override
	public void setNppIdCks(String[] ids) {
		this.nppIdCks = ids;
	}


	@Override
	public String[] getNppChietKhaus() {
		return this.nppChietKhaus;
	}


	@Override
	public void setNppChietKhaus(String[] cks) {
		this.nppChietKhaus = cks;
	}


	@Override
	public String getNhomkenhId() {
		return this.nhomkenhId;
	}


	@Override
	public void setNhomkenhId(String value) {
		this.nhomkenhId = value;
	}


	@Override
	public ResultSet getNhomkenhRs() {
		return this.nhomkenhRs;
	}


	@Override
	public String getNccId() {
		
		return this.nccId;
	}


	@Override
	public void setNccId(String nccId) {
		this.nccId = nccId;
		
	}


	@Override
	public ResultSet getNccRs() {
		
		return this.nccRs;
	}


	@Override
	public void setNccRs(ResultSet nccRs) {
		this.nccRs = nccRs;
		
	}
	
}
