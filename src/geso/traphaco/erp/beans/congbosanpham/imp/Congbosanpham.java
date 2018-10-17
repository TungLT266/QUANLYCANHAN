package geso.traphaco.erp.beans.congbosanpham.imp;

import geso.traphaco.erp.beans.congbosanpham.ICongbosanpham;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Congbosanpham extends Phan_Trang implements ICongbosanpham{
	String userId;
	String id;

	String tensp;
	String masp;
	String tenncc;
	String mancc;
	String hancongbo;
	String hinhcongbo;
	String filepath;

	String trangthai;
	String msg;

	dbutils db;
	
	
	String sodangki;
	String dangbaocheId;
	ResultSet dangbaocheRs;
	String ngaybatdauhieuluc;
	String ngayketthuchieuluc;
	

	public String getSodangki() {
		return sodangki;
	}

	public void setSodangki(String sodangki) {
		this.sodangki = sodangki;
	}

	public String getDangbaocheId() {
		return dangbaocheId;
	}

	public void setDangbaocheId(String dangbaocheId) {
		this.dangbaocheId = dangbaocheId;
	}

	public ResultSet getDangbaocheRs() {
		return dangbaocheRs;
	}

	public void setDangbaocheRs(ResultSet dangbaocheRs) {
		this.dangbaocheRs = dangbaocheRs;
	}

	public String getNgaybatdauhieuluc() {
		return ngaybatdauhieuluc;
	}

	public void setNgaybatdauhieuluc(String ngaybatdauhieuluc) {
		this.ngaybatdauhieuluc = ngaybatdauhieuluc;
	}

	public String getNgayketthuchieuluc() {
		return ngayketthuchieuluc;
	}

	public void setNgayketthuchieuluc(String ngayketthuchieuluc) {
		this.ngayketthuchieuluc = ngayketthuchieuluc;
	}

	public Congbosanpham()
	{
		this.userId = "";
		this.id = "";
		
		this.tensp = "";
		this.masp = "";
		this.tenncc = "";
		this.mancc = "";
		this.hancongbo = "";
		this.hinhcongbo = "";
		this.filepath = "";

		this.trangthai = "";

		this.msg = "";

		this.db = new dbutils();
	}

	public Congbosanpham(String id)
	{
		this.userId = "";
		this.id = id;

		this.tensp = "";
		this.masp = "";
		this.tenncc = "";
		this.mancc = "";
		this.hancongbo = "";
		this.hinhcongbo = "";
		this.filepath = "";

		this.trangthai = "";

		this.msg = "";

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

	public String getMasp()
	{
		return this.masp;
	}

	public void setMasp(String masp)
	{
		this.masp = masp;
	}
	
	public String getTensp()
	{
		return this.tensp;
	}

	public void setTensp(String tensp)
	{
		this.tensp = tensp;
	}
	
	public String getMancc()
	{
		return this.mancc;
	}

	public void setMancc(String mancc)
	{
		this.mancc = mancc;
	}
	
	public String getTenncc()
	{
		return this.tenncc;
	}

	public void setTenncc(String tenncc)
	{
		this.tenncc = tenncc;
	}
	
	public String getHancongbo()
	{
		return this.hancongbo;
	}

	public void setHancongbo(String hancongbo)
	{
		this.hancongbo = hancongbo;
	}

	public String getHinhcongbo()
	{
		return this.hinhcongbo;
	}

	public void setHinhcongbo(String hinhcongbo)
	{
		this.hinhcongbo = hinhcongbo;
	}
	
	public String getFilepath()
	{
		return this.filepath;
	}

	public void setFilepath(String filepath)
	{
		this.filepath = filepath;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public boolean createCongbosanpham()
	{
		try
		{
			if(this.hancongbo.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập Ngày hết hạn công bố";
				return false;
			}

			/*if(this.hinhcongbo.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập Hình ảnh công bố";
				return false;
			}*/

			db.getConnection().setAutoCommit(false);

			String query = "insert into erp_sanpham_nhacungcap(sanpham_fk,nhacungcap_fk,hancongbo, hinhcongbo, sodangki, ngaybatdauhieuluc, ngayketthuchieuluc) " +
							"values("+this.masp+","+this.mancc+",'" + this.hancongbo + "', '" + this.hinhcongbo + "',N'"+ this.sodangki +"','"+ this.ngaybatdauhieuluc+"','"+ this.ngayketthuchieuluc +"' )";

			
			
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới Công bố sản phẩm: " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "select as congbosanphamId";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					this.id = rs.getString("congbosanphamId");
				}
				rs.close();
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}

		catch (Exception e)
		{
			this.msg = "Lỗi: " + e.getMessage();
			try
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}

		return true;
	}

	public boolean updateCongbosanpham()
	{
		try
		{
			if(this.hancongbo.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập Ngày hết hạn công bố";
				return false;
			}

			/*if(this.hinhcongbo.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập Hình ảnh công bố";
				return false;
			}*/

			db.getConnection().setAutoCommit(false);

			String query = "Update erp_sanpham_nhacungcap set hancongbo = N'" + this.hancongbo + "', hinhcongbo = N'" + this.filepath + 
							"', filename = '" + this.hinhcongbo.trim() + "', sodangki=N'"+ this.sodangki.trim() +" ', ngaybatdauhieuluc='"+ this.ngaybatdauhieuluc +"', ngayketthuchieuluc='" + this.ngayketthuchieuluc +"' where pk_seq = '" + this.id + "' ";
			System.out.println(" [update cong bo sp] : "+query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật Công bố sản phẩm: " + query;
				db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}

		catch (Exception e)
		{
			this.msg = "Lỗi: " + e.getMessage();
			try
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}

		return true;
	}

	public void init()
	{
		String query = "select sn.pk_seq as id, sp.ma as masanpham, sp.TEN as sanpham, lsp.MA as loaisanpham, isnull(ncc.ma,ct.ma) as manhacungcap, " +
		"\n isnull(ncc.TEN,ct.TEN) as nhacungcap, sn.hancongbo, sn.filename, sn.hinhcongbo , isnull( sn.sodangki,'') as sodangki,isnull( sp.DANGBAOCHE,'') AS DANGBAOCHE,isnull( sn.ngaybatdauhieuluc,'')  as ngaybatdauhieuluc, isnull( sn.ngayketthuchieuluc,'')  as ngayketthuchieuluc " + 
		"\n from erp_sanpham_nhacungcap sn left join erp_SANPHAM sp on sn.sanpham_fk = sp.PK_SEQ " +
		"\n left join ERP_LOAISANPHAM lsp on lsp.PK_SEQ = sp.LOAISANPHAM_FK left join ERP_CONGTY ct on ct.PK_SEQ = sp.CONGTY_FK " +
		"\n left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = sn.nhacungcap_fk " + 
		"\n where (ncc.TRANGTHAI='1' or (sn.nhacungcap_fk is null and lsp.MA like '%TP%')) and sp.trangthai='1' and sn.pk_seq = '" + this.id + "'";
		
		System.out.println("[init cong bo sp+]: "+ query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
			{
				while(rs.next())
				{
					this.masp = rs.getString("masanpham"); 
					this.tensp = rs.getString("sanpham");
					this.mancc = rs.getString("manhacungcap");
					this.tenncc = rs.getString("nhacungcap");
					this.hancongbo = rs.getString("hancongbo");
					this.hinhcongbo = rs.getString("filename");
					this.filepath = rs.getString("hinhcongbo");
					this.sodangki=rs.getString("sodangki");
					this.dangbaocheId=rs.getString("DANGBAOCHE");
					this.ngaybatdauhieuluc=rs.getString("ngaybatdauhieuluc");
					this.ngayketthuchieuluc= rs.getString("ngayketthuchieuluc");
				}
				rs.close();
			}
			catch (Exception e)
			{
				System.out.println("__Exception Init: " + e.getMessage());
			}
		}

		this.createRs();
	}

	public void createRs()
	{
		this.dangbaocheRs = db.get(" select PK_SEQ, TEN from DANGBAOCHE where trangthai =1");
	}
	
	public String convertDate(String date)
	{
		if (!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if (arr[0].length() > arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}

	public void DbClose()
	{
		try
		{
			this.db.shutDown();
		}
		catch (Exception e) {}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
