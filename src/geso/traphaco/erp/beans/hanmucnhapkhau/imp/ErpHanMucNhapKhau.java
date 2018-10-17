package geso.traphaco.erp.beans.hanmucnhapkhau.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.hanmucnhapkhau.IErpHanMucNhapKhau;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ErpHanMucNhapKhau  extends Phan_Trang implements IErpHanMucNhapKhau{
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
	
	private String soDangKy;
	ResultSet listsp;
	dbutils db;

	
	public ErpHanMucNhapKhau()
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
		this.soluong=0;
		this.soDangKy = "";
		this.msg = "";
		this.db = new dbutils();
	}

	public ErpHanMucNhapKhau(String id)
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
		try
		{
			String query;
			query=	"select hmnk.PK_SEQ, sp.PK_SEQ as masp, sp.TEN, hmnk.TUNGAY\n" +
					", hmnk.DENNGAY, hmnk.SOLUONG, hmnk.TRANGTHAI, isNull(hmnk.soDangKy, '') as soDangKy \n" +
					"from ERP_HANMUCNHAPKHAU hmnk \n" +
					"left join ERP_SANPHAM sp on sp.PK_SEQ=hmnk.SANPHAM_FK \n" +
					"where hmnk.PK_SEQ= " + this.id + "\n";
			
			ResultSet rs =this.db.get(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					this.sanpham_fk=rs.getString("masp");
					this.tungay=rs.getString("TUNGAY");
					this.denngay=rs.getString("DENNGAY");
					this.soluong=rs.getFloat("SOLUONG");
					this.soDangKy = rs.getString("soDangKy");
				}
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}

	public void Create_Sanpham()
	{
		String query;
		query="select PK_SEQ,MA,TEN from ERP_SANPHAM where TRANGTHAI=1";
		this.listsp=db.get(query);
	}

	public boolean edit()
	{
		try{
			db.getConnection().setAutoCommit(false);
			String query="";
//			Date today=new Date(System.currentTimeMillis());
//			SimpleDateFormat timeFormat= new SimpleDateFormat("yyyy-MM-dd");
//			String date =timeFormat.format(today.getTime());
			
			if(id.length()>0)
			{
				
			query=" update ERP_HANMUCNHAPKHAU set SANPHAM_FK="+this.sanpham_fk+",TUNGAY='"+this.tungay+"',\n"
					+ "DENNGAY='"+this.denngay+"',SOLUONG="+this.soluong+", soDangKy = '" + this.soDangKy + "' \n" +
					"where PK_SEQ=  "+ this.id ;
				if(!db.update(query)){
					this.msg="Không  thực hiện được câu lệnh: "+query;
					db.getConnection().rollback();
					return false;
				}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			}
		}catch(Exception err){
			this.msg="Lỗi :"+err.getMessage();
			db.update("rollback");
			err.printStackTrace();
			return false;
		}
		return true;
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
			query="insert into ERP_HANMUCNHAPKHAU(SANPHAM_FK,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA,TUNGAY,DENNGAY,TRANGTHAI,SOLUONG, soDangKy) "
					+ "values("+this.getSanPham_Fk()+","+this.userid+","+this.userid+",'"+s+"','"+s+"',"
							+ "'"+this.getTuNgay()+"','"+this.getDenNgay()+"','1',"+this.getSoluong()+", '" + this.soDangKy + "')";
			if(!db.update(query))
			{
				this.msg="khong thuc hien duoc cau lenh "+query;
				db.getConnection().rollback();
				return false;
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

	public void setSoDangKy(String soDangKy) {
		this.soDangKy = soDangKy;
	}

	public String getSoDangKy() {
		return soDangKy;
	}
}