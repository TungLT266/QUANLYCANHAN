package geso.traphaco.erp.beans.duyetnhacungcap.imp;


import java.sql.ResultSet;


import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.duyetnhacungcap.IErpDuyetNhaCungCapList;



public class ErpDuyetNhaCungCapList  extends Phan_Trang implements IErpDuyetNhaCungCapList{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7098075456466668852L;
	String id;
	String trangthai;
	String tungay;
	String denngay;
	String userid;
	String sanpham_fk;
	String nhacungcap_fk;
	String msg;
	dbutils db;
	ResultSet listsp;
	ResultSet listncc;
	ResultSet listduyetncc;
	public ErpDuyetNhaCungCapList()
	{
		this.id = "";
		this.trangthai = "";
		this.tungay = "";
		this.denngay = "";
		this.sanpham_fk="";
		this.userid = "";
		this.msg = "";
		this.db = new dbutils();
	}

	public ErpDuyetNhaCungCapList(String id)
	{
		this.id = id;
		this.trangthai = "";
		this.userid = "";
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

	public void Init(String search)
	{
		
		String query = "";
		try
		{
			if(search.length()>0)
			{
				query=search;
			}
			else
			{
				query=	"select dncc.PK_SEQ,ncc.TEN as tennhacc,dncc.TUNGAY,"
						+ "dncc.DENNGAY,dncc.TRANGTHAI from ERP_DUYETNHACUNGCAP dncc "
						+ "left join ERP_NHACUNGCAP ncc on ncc.pk_seq=dncc.NHACUNGCAP_fk "
						+ "left join ERP_SANPHAM sp on sp.PK_SEQ=dncc.SANPHAM_FK";
			}
			 this.listduyetncc=createSplittingData(50, 10, "PK_SEQ desc", query); 
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void Create_Sanpham()
	{
		String query;
		query="select PK_SEQ,MA,TEN from ERP_SANPHAM where TRANGTHAI=1";
		this.listsp=db.get(query);
	}

	public boolean Update()
	{
		
		
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

	public void closeDB(){
		
	}
	
	@Override
	public boolean Delete_duyetncc() {
		// TODO Auto-generated method stub
		try{
			db.getConnection().setAutoCommit(false);
			String query="";
				query="update ERP_DUYETNHACUNGCAP set TRANGTHAI=2 where PK_SEQ="+this.id;
				if(!db.update(query)){
					this.msg="Không  thực hiện được câu lệnh: "+query;
					db.getConnection().rollback();
					return false;
				}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}catch(Exception err){
			this.msg="Lỗi :"+err.getMessage();
			db.update("rollback");
			err.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public ResultSet getListSanPham() {
		// TODO Auto-generated method stub
		return this.listsp;
	}

	@Override
	public void setListSanPham(ResultSet listsp) {
		// TODO Auto-generated method stub
		this.listsp=listsp;
	}

	@Override
	public String getTuNgay() {
		// TODO Auto-generated method stub
		return this.tungay;
	}

	@Override
	public String getDenNgay() {
		// TODO Auto-generated method stub
		return this.denngay;
	}

	@Override
	public void setTuNgay(String tungay) {
		// TODO Auto-generated method stub
		this.tungay=tungay;
	}

	@Override
	public void setDenNgay(String denngay) {
		// TODO Auto-generated method stub
		this.denngay=denngay;
	}

	@Override
	public String getSanPham_Fk() {
		// TODO Auto-generated method stub
		return this.sanpham_fk;
	}

	@Override
	public void setSanPham_Fk(String sanpham_fk) {
		// TODO Auto-generated method stub
		this.sanpham_fk=sanpham_fk;
	}

	@Override
	public ResultSet getListDuyetNcc() {
		// TODO Auto-generated method stub
		return this.listduyetncc;
	}

	@Override
	public void setListDuyetNcc(ResultSet listduyetncc) {
		// TODO Auto-generated method stub
		this.listduyetncc=listduyetncc;
	}

	@Override
	public ResultSet getListNhacungcap() {
		// TODO Auto-generated method stub
		return this.listncc;
	}

	@Override
	public void setListNhacungcap(ResultSet listncc) {
		// TODO Auto-generated method stub
		this.listncc=listncc;
	}

	@Override
	public void Create_Nhacungcap() {
		// TODO Auto-generated method stub
		String query;
		query="select PK_SEQ,MA,TEN from ERP_NHACUNGCAP where TRANGTHAI=1";
		this.listncc=db.get(query);
	}

	@Override
	public String getNhacungcap_Fk() {
		// TODO Auto-generated method stub
		return this.nhacungcap_fk;
	}

	@Override
	public void setNhacungcap_Fk(String nhacungcap_fk) {
		// TODO Auto-generated method stub
		this.nhacungcap_fk=nhacungcap_fk;
	}

	@Override
	public boolean Chot_duyetncc() {
		// TODO Auto-generated method stub
		try{
			db.getConnection().setAutoCommit(false);
			String query="";
				query="update ERP_DUYETNHACUNGCAP set TRANGTHAI=1 where PK_SEQ="+this.id;
				if(!db.update(query)){
					this.msg="Không  thực hiện được câu lệnh: "+query;
					db.getConnection().rollback();
					return false;
				}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}catch(Exception err){
			this.msg="Lỗi :"+err.getMessage();
			db.update("rollback");
			err.printStackTrace();
			return false;
		}
		return true;
	}

	
}
