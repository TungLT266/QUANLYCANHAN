package geso.traphaco.erp.beans.duyetnhacungcap.imp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.duyetnhacungcap.IErpDuyetNhaCungCap;
import geso.traphaco.erp.beans.hanmucnhapkhau.IErpHanMucNhapKhau;


public class ErpDuyetNhaCungCap  extends Phan_Trang implements IErpDuyetNhaCungCap{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7098075456466668852L;
	String id;
	String trangthai;
	String ngaytao;
	String ngaysua;
	String nguoitao,nguoisua;
	String tungay,denngay;
	String userid;
	String msg;
	float soluong;
	String sanpham_fk;
	String nhacungcap_fk;
	ResultSet listsp;
	ResultSet listncc;
	dbutils db;

	
	public ErpDuyetNhaCungCap()
	{
		this.id = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.nguoitao = "";
		this.tungay = "";
		this.denngay = "";
		this.userid = "";
		this.sanpham_fk = "";
		this.nhacungcap_fk = "";
		this.msg = "";
		this.db = new dbutils();
	}

	public ErpDuyetNhaCungCap(String id)
	{
		this.id = id;
		this.trangthai = "";
		this.ngaytao = "";
		this.ngaysua = "";
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

	public void Init()
	{
		
		String query = "";
		try
		{
			query=	"select dncc.PK_SEQ,dncc.nhacungcap_fk as manhacc,dncc.sanpham_fk as masanpham,dncc.TUNGAY,"
					+ "dncc.DENNGAY,dncc.TRANGTHAI "
					+ "from ERP_DUYETNHACUNGCAP dncc "
					+ "left join ERP_NHACUNGCAP ncc on ncc.pk_seq=dncc.NHACUNGCAP_fk "
					+ "left join ERP_SANPHAM sp on sp.PK_SEQ=dncc.SANPHAM_FK "
					+ "where dncc.PK_SEQ="+this.id+"";
			ResultSet rs=db.get(query);
			while(rs.next())
			{
				this.nhacungcap_fk=rs.getString("manhacc");
				this.sanpham_fk=rs.getString("masanpham");
				this.tungay=rs.getString("TUNGAY");
				this.denngay=rs.getString("DENNGAY");
			}
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

	public boolean edit() {
		try {
			db.getConnection().setAutoCommit(false);
			String query = "";
			Date today = new Date(System.currentTimeMillis());
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = timeFormat.format(today.getTime());

			if (id.length() > 0) {

				query = " update ERP_DUYETNHACUNGCAP set NHACUNGCAP_FK=" + this.nhacungcap_fk + ",SANPHAM_FK='" + this.sanpham_fk + "',TUNGAY='"
						+ this.tungay + "'," + "DENNGAY='" + this.denngay + "' where PK_SEQ='" + this.id + "'";
				if (!db.update(query)) {
					this.msg = "Không  thực hiện được câu lệnh: " + query;
					db.getConnection().rollback();
					return false;
				}
				

				// xoá hết sản phẩm cũ trong bảng duyệt
				query = "delete from ERP_DUYETNHACUNGCAP_SANPHAM where DUYETNHACUNGCAP_FK =" + this.id;
				int k = this.db.updateReturnInt(query);
				if( k == 0){
					System.out.println("Không xóa được dòng nào");
				}

				// insert bảng ERP_DUYETNHACUNGCAP_SANPHAM
				String[] arr = this.sanpham_fk.split(",");
				for (int i = 0; i < arr.length; i++) {
					if (arr[i].trim().length() > 0) {
						query = " insert into ERP_DUYETNHACUNGCAP_SANPHAM(DUYETNHACUNGCAP_FK, SANPHAM_FK)" + " values(" + id + "," + arr[i].trim()
								+ ")";

						k = this.db.updateReturnInt(query);
						if (k != 1) {
							this.msg = "cập nhật bảng chi tiết thất bại";
							this.db.getConnection().rollback();
							return false;
						}

					}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception err) {
			this.msg = "Lỗi :" + err.getMessage();
			db.update("rollback");
			err.printStackTrace();
			return false;
		}
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
	public String getNguoiTao() {
		// TODO Auto-generated method stub
		return this.nguoitao;
	}

	@Override
	public String getNguoiSua() {
		// TODO Auto-generated method stub
		return this.nguoisua;
	}

	@Override
	public void setNguoiTao(String nguoitao) {
		// TODO Auto-generated method stub
		this.nguoitao=nguoitao;
	}

	@Override
	public void setNguoiSua(String nguoisua) {
		// TODO Auto-generated method stub
		this.nguoisua=nguoisua;
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
	public ResultSet getListSanPham() {
		// TODO Auto-generated method stub
		return this.listsp;
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
	public void setListSanPham(ResultSet listsp) {
		// TODO Auto-generated method stub
		this.listsp=listsp;
	}

	@Override
	public boolean Save() {
		// TODO Auto-generated method stub
		try
		{
			Date today=new Date(System.currentTimeMillis());
			SimpleDateFormat timeFormat= new SimpleDateFormat("yyyy-MM-dd");
			String s=timeFormat.format(today.getTime());
			String query;
			db.getConnection().setAutoCommit(false);
			query="insert into ERP_DUYETNHACUNGCAP(NHACUNGCAP_FK,SANPHAM_FK,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA,TUNGAY,DENNGAY,TRANGTHAI) "
					+ "values("+this.getNhacungcap_Fk()+",'"+this.getSanPham_Fk()+"',"+this.userid+","+this.userid+",'"+s+"','"+s+"',"
							+ "'"+this.getTuNgay()+"','"+this.getDenNgay()+"','0')";
			if(!db.update(query))
			{
				this.msg="khong thuc hien duoc cau lenh "+query;
				db.getConnection().rollback();
				return false;
			}
			query = "SELECT IDENT_CURRENT('ERP_DUYETNHACUNGCAP') as Id";
			String id= "";
			ResultSet rs = this.db.get(query);
			if(rs !=null){
				if(rs.next()){
					id =rs.getString("Id");
				}
				rs.close();
			}
			
			
			// insert bảng ERP_DUYETNHACUNGCAP_SANPHAM
			String[] arr = this.sanpham_fk.split(",");
			for(int i=0; i< arr.length; i++){
				if(arr[i].trim().length() >0){
					query =" insert into ERP_DUYETNHACUNGCAP_SANPHAM(DUYETNHACUNGCAP_FK, SANPHAM_FK)" +
						   " values("+ id+","+arr[i].trim()+")";
					
					int k = this.db.updateReturnInt(query);
					if(k !=1){
						this.msg ="cập nhật bảng chi tiết thất bại";
						this.db.getConnection().rollback();
						return false;
					}
					
				}
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch(Exception e)
		{
			e.getStackTrace();
			return false;
		}

	}

	@Override
	public float getSoluong() {
		// TODO Auto-generated method stub
		return this.soluong;
	}

	@Override
	public void setSoluong(float sl) {
		// TODO Auto-generated method stub
		this.soluong=sl;
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
	public void Create_nhacungcap() {
		// TODO Auto-generated method stub
		String query;
		query="select PK_SEQ,MA,TEN from ERP_NHACUNGCAP where TRANGTHAI=1";
		this.listncc=db.get(query);
	}
	
}
