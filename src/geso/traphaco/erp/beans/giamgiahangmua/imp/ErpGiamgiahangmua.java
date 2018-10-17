package geso.traphaco.erp.beans.giamgiahangmua.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.giamgiahangmua.IErpGiamgiahangmua;


public class ErpGiamgiahangmua implements IErpGiamgiahangmua
{
	String userId;
	String congtyId;
	String id;

	String diengiai;
	String nccId;
	String nccTen;
	String tungay;
	String denngay;
	String DVTiente;
	String ngayghinhan;
	String ngayhoadon;
	String kyhieuhoadon;
	String sohieuhoadon;
	String vat;
	String Bvat;
	String Avat;
	String tienteId;
	
	String poId;
	ResultSet poRs;
	String[] hd;
	String[] hdSanpham;
	String[] idSanpham;
	String[] maSanpham;
	String[] tenSanpham;
	String[] soluong;
	String[] dongia;
	String[] thanhtien;
	String[] sotien;
	String[] loai;
	
	String msg;
	
	dbutils db;
	
	public ErpGiamgiahangmua()
	{
		this.userId = "";
		this.id = "";
		this.diengiai = "";
		this.tungay = "";
		this.denngay = "";
		this.DVTiente  = "";
		this.nccId = "";
		this.nccTen = "";
		this.poId = "";
		this.msg = "";
		
		this.ngayghinhan = "";
		this.ngayhoadon = "";
		this.kyhieuhoadon = "";
		this.sohieuhoadon = "";
		this.vat = "10";
		this.Bvat = "";
		this.Avat = "";
		this.tienteId = "";
		
		this.db = new dbutils();
	}
	
	public ErpGiamgiahangmua(String id)
	{
		this.userId = "";
		this.id = id;
		this.diengiai = "";
		this.tungay = "";
		this.denngay = "";
		this.DVTiente  = "";
		this.nccId = "";
		this.nccTen = "";
		this.poId = "";
		this.msg = "";
		
		this.ngayghinhan = "";
		this.ngayhoadon = "";
		this.kyhieuhoadon = "";
		this.sohieuhoadon = "";
		this.vat = "10";
		this.Bvat = "";
		this.Avat = "";
		this.tienteId = "";
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

	public String getTienteId() {
		
		return this.tienteId;
	}

	
	public void setTienteId(String ttId) {
		
		this.tienteId = ttId;
		String query = "SELECT ISNULL(MA, '') AS MA FROM ERP_TIENTE WHERE PK_SEQ = " + this.tienteId + "";
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				this.DVTiente = rs.getString("MA");
				rs.close();
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public String getDVTiente(){
		return this.DVTiente;
	}
	
	public ResultSet getTienteRs(){
		
		return db.get("select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE ");
	}
	
	public void init() 
	{
		String query = "select a.ngayghinhan, a.ngayhoadon, a.kyhieuhoadon, a.sohoadon, a.vat, a.bvat, a.avat, " +
						"a.ncc_fk, b.ten as nccTen, a.diengiai, a.tungay, a.denngay, ISNULL(a.TIENTE_FK, 0) AS TIENTE_FK " +
						"from erp_giamgiahangmua a " +
						"inner join erp_nhacungcap b on a.ncc_fk = b.pk_seq " +
						"where a.pk_seq = '" + this.id + "'";
		System.out.println(query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngayghinhan = rs.getString("ngayghinhan");
					this.ngayhoadon = rs.getString("ngayhoadon");
					this.kyhieuhoadon = rs.getString("kyhieuhoadon");
					this.sohieuhoadon = rs.getString("sohoadon");
					this.vat = rs.getString("vat");
					this.Bvat = rs.getString("bvat");
					this.Avat = rs.getString("avat");
					
					this.nccId = rs.getString("ncc_fk");
					this.nccTen = rs.getString("nccTen");
					
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.diengiai = rs.getString("diengiai");
					this.setTienteId(rs.getString("TIENTE_FK")) ;
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		
		//Init Hoa don
		query = "select hoadon_fk from ERP_GIAMGIAHANGMUA_HOADON where giamgia_fk = '" + this.id + "'";
		ResultSet rsHoadon = db.get(query);
		if(rsHoadon != null)
		{
			try 
			{
				String hdId = "";
				while(rsHoadon.next())
				{
					hdId += rsHoadon.getString("hoadon_fk") + ",";
				}
				rsHoadon.close();
				
				if(hdId.trim().length() > 0)
				{
					this.poId = hdId.substring(0, hdId.length() - 1);
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("__2.Exception: " + e.getMessage());
			}
		}
		
		this.createRS();
	}
	
	public boolean createGiamgia()
	{	
		try 
		{
			if(this.ngayghinhan.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày ghi nhận.";
				return false;
			}
			
			if(this.ngayhoadon.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày hóa đơn.";
				return false;
			}
			
			if(this.sohieuhoadon.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập số hóa đơn.";
				return false;
			}
			
			if(this.kyhieuhoadon.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập ký hiệu hóa đơn.";
				return false;
			}
			
			if(this.nccId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nhà cung cấp.";
				return false;
			}
			
			if(this.poId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn số hóa đơn xuất cho nhà cung cấp.";
				return false;
			}
			
			if( ( this.idSanpham == null ) && ( this.idSanpham != null && this.idSanpham.length <= 0 )  )
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm";
				return false;
			}
			
			if(this.sotien != null && this.sotien.length > 0)
			{
				//Check co SP nao duoc nhap tien ko??
				boolean flag = false;
				for(int i = 0; i < this.sotien.length; i++)
				{
					if(this.sotien[i].trim().length() > 0)
					{
						flag = true;
						break;
					}
				}
				
				if(!flag)
				{
					this.msg = "Không có sản phẩm nào được nhập số tiền giảm";
					return false;
				}
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = " insert erp_giamgiahangmua(TIENTE_FK, ngayghinhan, ngayhoadon, kyhieuhoadon, sohoadon, bvat, vat, avat, ncc_fk, diengiai, congty_fk, trangthai, nguoitao, ngaytao, nguoisua, ngaysua, tungay, denngay)  " +
							"values(" + this.tienteId + ", '" + this.ngayghinhan + "', '" + this.ngayhoadon + "', '" + this.kyhieuhoadon + "', '" + this.sohieuhoadon + "', '" + this.Bvat.replaceAll(",", "") + "', '" + this.vat.replaceAll(",", "") + "', '" + this.Avat.replaceAll(",", "") + "', '" + this.nccId + "', N'" + this.diengiai + "', '" + this.congtyId + "', '0', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.tungay + "', '" + this.denngay + "') ";
			System.out.println("Giảm giá hàng mua : "+query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới erp_giamgiahangmua " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " select IDENT_CURRENT('erp_giamgiahangmua') as giamgiaId ";
			ResultSet rs = db.get(query);
			if(rs.next())
				this.id = rs.getString("giamgiaId");
			
			for(int i = 0; i < this.idSanpham.length; i++ )
			{
				if( this.sotien[i].trim().length() > 0 )
				{
					query = "insert erp_giamgiahangmua_sanpham(giamgiamua_fk, SoHoaDon, sanpham_fk, sotien, loai, hoadon_fk)\n" +
							"values('" + this.id + "', '" + this.hdSanpham[i] + "', '" + this.idSanpham[i] + "', '" + this.sotien[i].replaceAll(",", "") + "', " + this.loai[i] + "," + this.hd[i] + ") ";
					System.out.println("Sản phẩm: \n" + query + "\n----------------------------------------------------");
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới erp_giamgiahangmua_sanpham \n" + query + "\n------------------------------------";
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			//
			if(this.poId.trim().length() > 0)
			{
				query = "Insert ERP_GIAMGIAHANGMUA_HOADON (giamgia_fk, hoadon_fk ) \n" +
						"select '" + this.id + "', pk_seq from ERP_HOADONNCC where pk_seq in (" + this.poId + ") \n";
				System.out.println("Hóa đơn: \n" + query + "\n-------------------------------------------------------");
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_GIAMGIAHANGMUA_HOADON \n" + query + "\n---------------------------------------";
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
			catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		
		return true;
	}
	
	public boolean updateGiamgia() 
	{
		try 
		{
			if(this.ngayghinhan.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày ghi nhận.";
				return false;
			}
			
			if(this.ngayhoadon.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày hóa đơn.";
				return false;
			}
			
			if(this.sohieuhoadon.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập số hóa đơn.";
				return false;
			}
			
			if(this.kyhieuhoadon.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập ký hiệu hóa đơn.";
				return false;
			}
			
			if(this.nccId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nhà cung cấp.";
				return false;
			}
			
			if(this.poId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn số hóa đơn xuất cho nhà cung cấp.";
				return false;
			}
			
			if( ( this.idSanpham == null ) && ( this.idSanpham != null && this.idSanpham.length <= 0 )  )
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm";
				return false;
			}
			
			if(this.sotien != null && this.sotien.length > 0)
			{
				//Check co SP nao duoc nhap tien ko??
				boolean flag = false;
				for(int i = 0; i < this.sotien.length; i++)
				{
					if(this.sotien[i].trim().length() > 0)
					{
						flag = true;
						break;
					}
				}
				
				if(!flag)
				{
					this.msg = "Không có sản phẩm nào được nhập số tiền giảm";
					return false;
				}
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update erp_giamgiahangmua set " +
							"TIENTE_FK = " + this.tienteId + ", " +
							"ngayghinhan = '" + this.ngayghinhan + "', ngayhoadon = '" + this.ngayhoadon + "', kyhieuhoadon = '" + this.kyhieuhoadon + "', sohoadon = '" + this.sohieuhoadon + "', " +
							"bvat = '" + this.Bvat.replaceAll(",", "") + "', vat = '" + this.vat.replaceAll(",", "") + "', avat = '" + this.Avat.replaceAll(",", "") + "', " +
							"ncc_fk = '" + this.nccId + "', diengiai = N'" + this.diengiai + "', congty_fk = '" + this.congtyId + "', tungay = '" + this.tungay + "', denngay = '" + this.denngay + "', " +
							"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' " + 
							"where pk_seq = '" + this.id + "' ";

			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật erp_giamgiahangmua " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete erp_giamgiahangmua_sanpham where giamgiamua_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật erp_giamgiahangmua_sanpham " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_GIAMGIAHANGMUA_HOADON where giamgia_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_GIAMGIAHANGMUA_HOADON " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < this.idSanpham.length; i++ )
			{
				if( this.sotien[i].trim().length() > 0 )
				{
					query = "insert erp_giamgiahangmua_sanpham(giamgiamua_fk, SoHoaDon, sanpham_fk, sotien, loai, hoadon_fk)  " +
							"values('" + this.id + "', '" + this.hdSanpham[i] + "', '" + this.idSanpham[i] + "', '" + this.sotien[i].replaceAll(",", "") + "', " + this.loai[i] + ", " + this.hd[i] + ") ";
					
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới erp_giamgiahangmua_sanpham " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			//
			if(this.poId.trim().length() > 0)
			{
				query = "Insert ERP_GIAMGIAHANGMUA_HOADON (giamgia_fk, hoadon_fk ) " +
						"select '" + this.id + "', pk_seq from ERP_HOADONNCC where pk_seq in (" + this.poId + ") ";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_GIAMGIAHANGMUA_HOADON " + query;
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
			catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		
		return true;
	}
	
	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	
	public void DbClose() 
	{
		try 
		{
			this.db.shutDown();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getNccId() {
		
		return this.nccId;
	}

	
	public void setNccId(String nccId) {
		
		this.nccId = nccId;
	}

	
	public String getNccTen() {
		
		return this.nccTen;
	}

	
	public void setNccTen(String nccTen) {
		
		this.nccTen = nccTen;
	}

	
	public ResultSet getPORs() {
		
		return this.poRs;
	}

	
	public void setPORs(ResultSet poRs) {
		
		this.poRs = poRs;
	}

	
	public String getPOId() {
		
		return this.poId;
	}

	
	public void setPOId(String poId) {
		
		this.poId = poId;
	}

	
	public String[] getIdsansham() {
		
		return this.idSanpham;
	}

	
	public void setIdsanpham(String[] idsanpham) {
		
		this.idSanpham = idsanpham;
	}

	
	public String[] getMasansham() {
		
		return this.maSanpham;
	}

	
	public void setMasanpham(String[] masanpham) {
		
		this.maSanpham = masanpham;
	}

	
	public String[] getTensansham() {
		
		return this.tenSanpham;
	}

	
	public void setTensanpham(String[] tensanpham) {
		
		this.tenSanpham = tensanpham;
	}

	
	public String[] getSoluong() {
		
		return this.soluong;
	}

	
	public void setSoluong(String[] soluong) {
		
		this.soluong = soluong;
	}

	
	public String[] getSotien() {
		
		return this.sotien;
	}

	
	public void setSotien(String[] sotien) {
		
		this.sotien = sotien;
	}

	public String[] getLoai() {
		
		return this.loai;
	}
	
	public void setLoai(String[] loai) {
		
		this.loai = loai;
	}
	
	public String[] getHoadon() {
		
		return this.hd;
	}
	
	public void setHoadon(String[] hd) {
		
		this.hd = hd;
	}

	public void createRS() 
	{
		String query = "";
		if(this.nccId.trim().length() > 0)
		{
			/*query = " select A.PK_SEQ as poId, A.PREFIX + cast(A.PK_SEQ as nvarchar(10)) + N', Ngày bán ' + A.NGAYDAT as poTen " +
					" from ERP_DONDATHANG A " +
					" where A.isKM = 0 and A.trangthai in ( 5 )  and a.khachhang_fk = '" + this.nccId + "' and a.pk_seq not in ( select dondathang_fk from erp_giamgiahangban where trangthai != 2  ) ";
			
			if(this.tungay.trim().length() > 0)
				query += " and a.ngaydat >= '" + this.tungay.trim() + "' ";
			if(this.denngay.trim().length() > 0)
				query += " and a.ngaydat <= '" + this.denngay.trim() + "' ";
			
			if (this.id.length() > 0)
			{
				query += " union " +
						 " select PK_SEQ as poId, PREFIX + cast(PK_SEQ as nvarchar(10)) + N', Ngày bán ' + NGAYDAT as poTen  " +
						 " from ERP_DONDATHANG where pk_seq in (select dondathang_fk from erp_giamgiahangban where PK_SEQ = '" + this.id + "')";
			}*/
			
			query = " select a.PK_SEQ, ngayhoadon as NGAYXUATHD, KYHIEU, SOHOADON  \n" +
					" from ERP_HOADONNCC  a \n" +
					" inner join ERP_PARK b on a.park_fk = b.pk_seq  \n" +
					" where a.LOAIHD <> 2 and b.ncc_fk = '" + this.nccId + "' and b.trangthai = 2 and b.TIENTE_FK = " + this.tienteId + " \n" +
					" and a.PK_SEQ not in ( select hoadon_fk from ERP_GIAMGIAHANGMUA_HOADON \n" +
					"                       where giamgia_fk in ( select PK_SEQ from ERP_GiamGiaHangMUA where trangthai <> 2 )\n" +
					"                     ) \n";
			
			if(this.tungay.trim().length() > 0)
				query += " and ngayhoadon >= '" + this.tungay.trim() + "' \n";
			
			if(this.denngay.trim().length() > 0)
				query += " and ngayhoadon <= '" + this.denngay.trim() + "' \n";
			
			if (this.id.length() > 0)
			{
				query += "  union \n" +
						 "  select HD.PK_SEQ, HD.ngayhoadon as NGAYXUATHD, HD.KYHIEU, HD.SOHOADON \n" +
						 "  from ERP_HOADONNCC HD\n" +
						 "  inner join ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK \n" +
						 "  where HD.LOAIHD <> 2 and  HD.pk_seq in ( select hoadon_fk from ERP_GIAMGIAHANGMUA_HOADON where giamgia_fk = '" + this.id + "' ) AND PARK.TIENTE_FK = " + this.tienteId + " \n" ;
			}
			
			System.out.println("___KHOI TAO HD: \n" + query + "\n------------------------------------------------------------");
			this.poRs = db.get(query);
		}
		
		if(this.poId.trim().length() > 0 )
		{
			NumberFormat formater = new DecimalFormat("##,###,###");
			if ( (idSanpham == null) || ( idSanpham != null && idSanpham.length > 0 ))
			{
				query = "SELECT DISTINCT a.PK_SEQ, a.SOHOADON \n" +
						", CASE WHEN SP1.PK_SEQ IS NULL THEN SP2.PK_SEQ ELSE SP2.PK_SEQ END AS SPID \n" + 
						", CASE WHEN SP1.MA IS NULL THEN SP2.MA ELSE  SP1.MA END AS SPMA \n" +
						", CASE WHEN SP1.TEN IS NULL THEN SP2.TEN + ' ' + SP2.QUYCACH ELSE SP1.TEN + ' ' + SP1.QUYCACH END AS SPTEN \n" + 
						", CASE WHEN SP1.MA IS NULL THEN NH_SP.SOLUONGNHAN ELSE b.SOLUONG END AS SOLUONG \n" +
						", CASE WHEN SP1.MA IS NULL THEN NH_SP.DONGIA ELSE b.DONGIA END AS DONGIA \n" +
						", ISNULL(d.SOTIEN, 0) AS SOTIEN, PARK.TIENTE_FK, a.THUESUAT \n" +
						", CASE WHEN SP1.PK_SEQ IS NULL THEN 2 ELSE 1 END AS LOAI \n" +
						"FROM ERP_HOADONNCC a \n" +
						"INNER JOIN ERP_PARK PARK on PARK.PK_SEQ = a.PARK_FK \n" + 
						"LEFT JOIN ERP_HOADONNCC_DONMUAHANG b  on a.PK_SEQ = b.HOADONNCC_FK \n" +    
						"LEFT JOIN ERP_SANPHAM SP1 on b.SANPHAM_FK = SP1.PK_SEQ \n" +

						"LEFT JOIN ERP_HOADONNCC_PHIEUNHAP HD_PN ON HD_PN.HOADONNCC_FK = a.PK_SEQ \n" +
						"LEFT JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH_SP.NHANHANG_FK = HD_PN.PHIEUNHAN_FK \n" +
						"LEFT JOIN ERP_SANPHAM SP2 on NH_SP.SANPHAM_FK = SP2.PK_SEQ \n" +

						"LEFT JOIN ERP_GIAMGIAHANGMUA_HOADON gg_hd ON a.PK_SEQ = gg_hd.HOADON_FK \n" + 
						"LEFT JOIN ERP_GIAMGIAHANGMUA_SANPHAM d ON (SP1.PK_SEQ = d.SANPHAM_FK OR SP2.PK_SEQ = d.SANPHAM_FK) AND gg_hd.GIAMGIA_FK = d.GIAMGIAMUA_FK \n" + 
						"AND d.SOHOADON = a.SOHOADON AND d.GIAMGIAMUA_FK = '" + ( this.id.trim().length() <= 0 ? "-1" : this.id  ) + "' AND d.HOADON_FK = a.PK_SEQ \n" + 
						"WHERE a.PK_SEQ IN ( " + this.poId + " ) \n";
				
/*				query = "select a.SOHOADON, c.pk_seq as spId, c.ma as spMa, c.ten + ' ' + c.quycach as spTen, " +
						"b.soluong, b.dongia, isnull(d.sotien, 0) as sotien, PARK.TIENTE_FK, a.thuesuat " +
						"from ERP_HOADONNCC a " +
						"inner join ERP_PARK PARK on PARK.pk_seq = a.park_fk " +
						"inner join ERP_HOADONNCC_DONMUAHANG b  on a.pk_seq = b.HOADONNCC_FK    " +
						"inner join ERP_Sanpham c on b.sanpham_fk = c.pk_seq " +
						"left join ERP_GIAMGIAHANGMUA_HOADON gg_hd on a.pk_seq = gg_hd.hoadon_fk " +
						"left join erp_giamgiahangmua_sanpham d on c.pk_seq = d.sanpham_fk and gg_hd.giamgia_fk = d.giamgiamua_fk and d.sohoadon = a.sohoadon " +
						"and d.giamgiamua_fk = '" + ( this.id.trim().length() <= 0 ? "-1" : this.id  ) + "'   " +
						"where a.pk_seq in ( " + this.poId + " ) "; */
				
				System.out.println("___LAY SP: \n" + query + "\n----------------------------------------------------");
				
				ResultSet rsSp = db.getScrol(query);
				
				int count = 0;
				
				try 
				{
					rsSp.beforeFirst();
					while(rsSp.next())
					{
						count ++;
					}
					hd = new String[count];
					hdSanpham = new String[count];
					idSanpham = new String[count];
					maSanpham = new String[count];
					tenSanpham = new String[count];
					soluong = new String[count];
					dongia = new String[count];
					thanhtien = new String[count];
					sotien = new String[count];
					loai = new String[count];
					
					rsSp.beforeFirst();
					int pos = 0;
					while(rsSp.next())
					{
						hd[pos] = rsSp.getString("PK_SEQ");
						hdSanpham[pos] = rsSp.getString("SOHOADON");
						idSanpham[pos] = rsSp.getString("spId");
						maSanpham[pos] = rsSp.getString("spMa");
						tenSanpham[pos] = rsSp.getString("spTen");
						soluong[pos] = formater.format(rsSp.getDouble("soluong"));
						dongia[pos] = formater.format(rsSp.getDouble("dongia"));
						if(rsSp.getDouble("thuesuat")> 0){
							thanhtien[pos] = "" + (rsSp.getDouble("soluong") * rsSp.getDouble("dongia") * (1 + rsSp.getDouble("thuesuat"))/100);
						}else{
							thanhtien[pos] = "" + (rsSp.getDouble("soluong") * rsSp.getDouble("dongia"));
						}
						sotien[pos] = formater.format(rsSp.getDouble("sotien"));
						loai[pos] = rsSp.getString("LOAI");
						
						System.out.println("Sô tiền giảm" +sotien[pos]);
						
						pos++;
					}
					rsSp.close();
					
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					System.out.println("____EXception SanPham: " + e.getMessage());
				}
			}
		}
		else
		{
			hdSanpham = null;
			idSanpham = null;
			maSanpham = null;
			tenSanpham = null;
			soluong = null;
			dongia = null;
			thanhtien = null;
			sotien = null;
		}
		
	}

	
	public String[] getDongia() {
		
		return this.dongia;
	}

	
	public void setDongia(String[] dongia) {
		
		this.dongia = dongia;
	}

	
	public String[] getTongtien() {
		
		return this.thanhtien;
	}

	
	public void setTongtien(String[] tongtien) {
		
		this.thanhtien = tongtien;
	}
	
public String getNgayghinhan() {
		
		return this.ngayghinhan;
	}

	
	public void setNgayghinhan(String ngayghinhan) {
		
		this.ngayghinhan = ngayghinhan;
	}

	
	public String getNgayhoadon() {
		
		return this.ngayhoadon;
	}

	
	public void setNgayhoadon(String ngayhoadon) {
		
		this.ngayhoadon = ngayhoadon;
	}

	
	public String getKyhieuhoadon() {
		
		return this.kyhieuhoadon;
	}

	
	public void setKyhieuhoadon(String kyhieuhd) {
		
		this.kyhieuhoadon = kyhieuhd;
	}

	
	public String getSohoadon() {
		
		return this.sohieuhoadon;
	}

	
	public void setSohoadon(String sohoadon) {
		
		this.sohieuhoadon = sohoadon;
	}

	
	public String getVat() {
		
		return this.vat;
	}

	
	public void setVat(String vat) {
		
		this.vat = vat;
	}

	
	public String getBvat() {
		
		return this.Bvat;
	}

	
	public void setBvat(String bvat) {
		
		this.Bvat = bvat;
	}

	
	public String getAvat() {
		
		return this.Avat;
	}

	
	public void setAvat(String avat) {
		
		this.Avat = avat;
	}


	public String[] getHoadonTen() {
		
		return this.hdSanpham;
	}

	
	public void setHoadonTen(String[] hoadonTen) {
		
		this.hdSanpham = hoadonTen;
	}
	

}
