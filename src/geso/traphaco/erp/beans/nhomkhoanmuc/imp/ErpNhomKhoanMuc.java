package geso.traphaco.erp.beans.nhomkhoanmuc.imp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.beans.khoasothang.imp.ErpKhoasokinhdoanh;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.nhomkhoanmuc.IErpNhomKhoanMuc;

public class ErpNhomKhoanMuc  extends Phan_Trang implements IErpNhomKhoanMuc{
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
	String action;
	
	//Điều kiện lọc Khoản mục chi phí 
	String tenKhoanMucChiPhi;
	String dvthid ;
	
	ResultSet rsNhomKhoanMuc;
	ResultSet rsDonViThucHien;
	dbutils db;
	List<ErpKhoanMucChiPhi> listKhoanMuc ;
	String chixem;
	
	public ErpNhomKhoanMuc()
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
		this.tenKhoanMucChiPhi = "";
		this.dvthid = "";
		this.action = "";
		this.listKhoanMuc = new ArrayList<ErpKhoanMucChiPhi>();
	}

	public ErpNhomKhoanMuc(String id)
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
		this.tenKhoanMucChiPhi = "";
		this.dvthid = "";
		this.action = "";
		this.listKhoanMuc = new ArrayList<ErpKhoanMucChiPhi>();
		
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
		PreparedStatement prep = null;
		String query = "Select PK_SEQ,Ma,isnull(Ten,'')Ten,NgaySua,isnull(TrangThai,0)TrangThai From ERP_NHOMKHOANMUC Where PK_SEQ=? "
				+ "and CONGTY_FK = " + this.congtyId + " ";//and NPP_FK = " + this.nppId;b
		try
		{
			prep = this.db.getConnection().prepareStatement(query);
			prep.setString(1, this.id);
			this.rsNhomKhoanMuc = prep.executeQuery();
			while (rsNhomKhoanMuc.next())
			{
				this.id = rsNhomKhoanMuc.getString("PK_SEQ");
				this.Ma = rsNhomKhoanMuc.getString("Ma");
				this.Ten = rsNhomKhoanMuc.getString("Ten");
				this.ngaysua = rsNhomKhoanMuc.getString("NgaySua");
				this.trangthai = rsNhomKhoanMuc.getString("TrangThai");
			}
			rsNhomKhoanMuc.close();
			prep.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		this.createRsDonViThucHien();
	}
	public void createListKhoanMucChiPhi(){
		ResultSet  rsKhoanMucChiPhi = this.getRsKhoanMucChiPhi();
		if(rsKhoanMucChiPhi != null){
			try {
				while (rsKhoanMucChiPhi.next()){
					ErpKhoanMucChiPhi nhomKhoanMuc = new ErpKhoanMucChiPhi();
					nhomKhoanMuc.setIdKhoanMuc(rsKhoanMucChiPhi.getString("NCPID"));
					nhomKhoanMuc.setTenKhoanMuc(rsKhoanMucChiPhi.getString("NCPTEN"));
					nhomKhoanMuc.setMaKhoanMuc(rsKhoanMucChiPhi.getString("NCPMA"));
					nhomKhoanMuc.setChecked(rsKhoanMucChiPhi.getString("CHECKED"));
					this.listKhoanMuc.add(nhomKhoanMuc);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.createRsDonViThucHien();
		
	}
	public ResultSet getRsKhoanMucChiPhi(){
//	    PrintWriter out = response.getWriter();
		
		String query ="";
		if(this.id.length() >0 && !this.action.equals("search")){ 
			query = "SELECT NCP.PK_SEQ AS NCPID,NCP.TEN AS NCPMA,NCP.DIENGIAI AS NCPTEN,'1' AS CHECKED \n"+
					" FROM ERP_NHOMKHOANMUC_KHOANMUCCHIPHI NKM_KMCP \n"+
					" INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = NKM_KMCP.KHOANMUCCHIPHI_fk \n"+
					" WHERE NHOMKHOANMUC_FK = "+this.id+" \n" ;
		}
		if((this.id.length() > 0 && !this.action.equals("search")) && (this.tenKhoanMucChiPhi.length() > 0 || this.dvthid.length() > 0)){
			query += " UNION ALL ";
		}
		if (this.tenKhoanMucChiPhi.length() > 0 || this.dvthid.length() > 0) {
			query += "SELECT	NHOMCHIPHI.PK_SEQ AS NCPID, NHOMCHIPHI.TEN AS NCPMA, NHOMCHIPHI.DIENGIAI AS NCPTEN,'0' AS CHECKED "
					+ " FROM ERP_NHOMCHIPHI NHOMCHIPHI  "
					+ " INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = NHOMCHIPHI.CONGTY_FK "
					+ " LEFT JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = NHOMCHIPHI.DONVITHUCHIEN_FK  "
					+ " WHERE NHOMCHIPHI.PK_SEQ NOT IN (SELECT DISTINCT KHOANMUCCHIPHI_FK FROM ERP_NHOMKHOANMUC_KHOANMUCCHIPHI ) ";
			System.out.println(query);

			if (this.tenKhoanMucChiPhi.length() > 0) {
				query = query + " AND ( UPPER(NHOMCHIPHI.DIENGIAI) LIKE UPPER(N'%" + this.tenKhoanMucChiPhi
						+ "%') OR UPPER(NHOMCHIPHI.TEN) LIKE UPPER(N'%" + this.tenKhoanMucChiPhi + "%')) ";
			}

			if (this.dvthid.length() > 0) {
				query = query + " AND DVTH.PK_SEQ = '" + this.dvthid + "'";
			}

			query = query + " ORDER BY NCPID ASC ";
		}
		System.out.println(query);
		return this.db.get(query);
	}

	public void search()
	{
		String query = " Select NKM.PK_SEQ ,NKM.Ma,NKM.Ten"
			+ " From ERP_NHOMKHOANMUC NKM "+
			"Where 1=1 and congty_fk= "+this.congtyId ;
		System.out.println("Search" + query);
		if (this.Ma.length() > 0)
		{
			query += " and  NKM.Ma like N'%" + this.Ma+ "%' ";
		}
		if (this.Ten.length() > 0)
		{
			query += " and NKM.Ten like N'%" + this.Ten + "%'";
		}
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
			this.db.getConnection().setAutoCommit(false);
			query = "Insert into ERP_NHOMKHOANMUC(Ma,Ten,NgayTao,NgaySua,NguoiTao,NguoiSua,TrangThai, Congty_fk) values(" + "N'" +
			this.Ma + "',N'" + this.Ten + "','" + getDateTime() + "','" + getDateTime() + "','" + this.userid + "','" +
			this.userid + "' ,'" + this.trangthai + "' ,'" + this.congtyId +  "')";
			System.out.println("Create Query    " + query); 
			
			if (this.db.update(query))
			{
				System.out.println("Tao duoc ERP_NHOMKHOANMUC ");	
			} else
			{
				this.msg = "Không thể tạo mới nhóm chi phí :" + query;
				this.db.update("rollback");
				return false;
			}
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
			
			if(this.listKhoanMuc != null){
				for(ErpKhoanMucChiPhi kmcp :  this.listKhoanMuc ){
					if(kmcp.getChecked().equals("1")){
						query = "INSERT INTO ERP_NHOMKHOANMUC_KHOANMUCCHIPHI (NHOMKHOANMUC_FK,KHOANMUCCHIPHI_FK) VALUES ("+currentId+","+kmcp.getIdKhoanMuc()+")";
						System.out.println("Câu lệnh insert ERP_NHOMKHOANMUC_KHOANMUCCHIPHI " +query);
						if(!this.db.update(query)){
							this.msg = "Không thể tạo mới nhóm chi phí " + query;
							this.db.update("rollback");
							return false;
						}
					}
				}
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
		String query = "";
		if(!CheckValid())
		{
			return false;
		}
		try
		{
			this.db.getConnection().setAutoCommit(false);
			query = "Update ERP_NHOMKHOANMUC set Ma=N'" + this.Ma + "',Ten=N'" + this.Ten + "',NgaySua='" +
					getDateTime() + "',NguoiSua='" + this.userid + "',TrangThai='" + this.trangthai + "" + "' Where PK_SEQ='" +this.id + "'";
			System.out.println("update Nhom chi phi: " + query);
			if(!this.db.update(query))
			{
				this.msg = "Không thể cập nhật:" + query;
				this.db.update("rollback");
				return false;
			}
			query = "DELETE FROM ERP_NHOMKHOANMUC_KHOANMUCCHIPHI WHERE NHOMKHOANMUC_FK =" +this.id ;
			if(!this.db.update(query))
			{
				this.msg = "Không thể thay đổi khoản mục chi phí trong nhóm chi phí : "+ query;
				this.db.update("rollback");
				return false;
			}
			if(this.listKhoanMuc != null){
				for(ErpKhoanMucChiPhi kmcp :  this.listKhoanMuc ){
					System.out.println("CHECKED :" + kmcp.getChecked());
					if(kmcp.getChecked().equals("1")){
						query = "INSERT INTO ERP_NHOMKHOANMUC_KHOANMUCCHIPHI (NHOMKHOANMUC_FK,KHOANMUCCHIPHI_FK) VALUES ("+this.id+","+kmcp.getIdKhoanMuc()+")";
						System.out.println("Câu lệnh insert ERP_NHOMKHOANMUC_KHOANMUCCHIPHI " +query);
						if(!this.db.update(query)){
							this.msg = "Không thể tạo mới nhóm chi phí " + query;
							this.db.update("rollback");
							return false;
						}
					}
				}
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

	
	public boolean Deletenkm() {
		String query = "DELETE FROM ERP_NHOMKHOANMUC_KHOANMUCCHIPHI WHERE NHOMKHOANMUC_FK = " + this.id;
		if(!this.db.update(query)){
			this.msg = "Không thể xóa nhóm chi phí :" + query;
			return false;
		}
		query = "DELETE ERP_NHOMKHOANMUC WHERE PK_SEQ='" + this.id + "'";
		if (!this.db.update(query)) {
			this.msg = "" + "Nhóm chi phí này  đã có trong khoản mục chi phí rồi, không thể xóa được !";
			return false;
		}

		return true;
	}

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
			if (this.listKhoanMuc != null) this.listKhoanMuc.clear();
			if(this.rsDonViThucHien != null) this.rsDonViThucHien.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
		
	}
	
	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}



	@Override
	public boolean DeleteLncc() {
		// TODO Auto-generated method stub
		return false;
	}

	public ResultSet getRsNhomKhoanMuc() {
		return rsNhomKhoanMuc;
	}

	public void setRsNhomKhoanMuc(ResultSet rsNhomKhoanMuc) {
		this.rsNhomKhoanMuc = rsNhomKhoanMuc;
	}

	public String getTenKhoanMucChiPhi() {
		return tenKhoanMucChiPhi;
	}

	public void setTenKhoanMucChiPhi(String tenKhoanMucChiPhi) {
		this.tenKhoanMucChiPhi = tenKhoanMucChiPhi;
	}

	public String getDvthid() {
		return dvthid;
	}

	public void setDvthid(String dvthid) {
		this.dvthid = dvthid;
	}

	public List<ErpKhoanMucChiPhi> getListKhoanMuc() {
		return listKhoanMuc;
	}

	public void setListKhoanMuc(List<ErpKhoanMucChiPhi> listKhoanMuc) {
		this.listKhoanMuc = listKhoanMuc;
	}

	public void createRsDonViThucHien() {
		this.rsDonViThucHien = this.db.get("SELECT PK_SEQ AS DVTTID, TEN FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI = '1' ");
	}

	public ResultSet getRsDonViThucHien() {
		return rsDonViThucHien;
	}

	public void setRsDonViThucHien(ResultSet rsDonViThucHien) {
		this.rsDonViThucHien = rsDonViThucHien;
	}
	public String getAction(){
		return this.action;
	}
	public void setAction (String action){
		this.action = action;
	}
	
}
