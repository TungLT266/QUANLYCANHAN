package geso.traphaco.erp.beans.giamgiahangban.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.giamgiahangban.IErpGiamgiahangban;

public class ErpGiamgiahangban implements IErpGiamgiahangban
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

	String tienteId;
	String ngayghinhan;
	String ngayhoadon;
	String kyhieuhoadon;
	String sohieuhoadon;
	String vat;
	String Bvat;
	String Avat;
	
	String poId;
	ResultSet poRs;
	
	String[] hoadonId;
	
	String[] hdSanpham;
	String[] kyhieuhd;
	String[] ghichuhd;
	String[] ngayhd;
	
	String[] idSanpham;
	String[] maSanpham;
	String[] tenSanpham;
	String[] soluong;
	String[] dongia;
	String[] thanhtien;
	String[] sotien;
	String[] Loaihaodon;
	
	//Dành cho phần in hóa đơn
	public String nccDiaChi = "";
	public String nccMaSoThue = "";
	public String nccNguoiLienHe = "";
	
	String msg;
	
	dbutils db;
	
	public ErpGiamgiahangban()
	{
		this.userId = "";
		this.id = "";
		this.diengiai = "";
		this.tungay = "";
		this.denngay = "";
		this.nccId = "";
		this.nccTen = "";
		this.poId = "";
		this.msg = "";
		this.tienteId = "";
		this.ngayghinhan = "";
		this.ngayhoadon = "";
		this.kyhieuhoadon = "";
		this.sohieuhoadon = "";
		this.vat = "10";
		this.Bvat = "";
		this.Avat = "";
		this.DVTiente = "";
		this.tienteId = "";
		this.db = new dbutils();
	}
	
	public ErpGiamgiahangban(String id)
	{
		this.userId = "";
		this.id = id;
		this.diengiai = "";
		this.tungay = "";
		this.denngay = "";
		this.nccId = "";
		this.nccTen = "";
		this.poId = "";
		this.msg = "";
		this.tienteId = "";
		this.ngayghinhan = "";
		this.ngayhoadon = "";
		this.kyhieuhoadon = "";
		this.sohieuhoadon = "";
		this.vat = "10";
		this.Bvat = "";
		this.Avat = "";
		this.DVTiente = "";
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
		System.out.println("Câu lấy tiền tệ "+query);
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				this.DVTiente = rs.getString("MA");
				rs.close();
			}catch(java.sql.SQLException e){}
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
		String query = "select a.ngayghinhan, a.ngayhoadon, a.kyhieuhoadon, a.sohoadon, a.vat, a.bvat, a.avat " +
						"	, a.khachhang_fk, b.ten as khTen, a.diengiai, a.tungay, a.denngay, ISNULL(a.TIENTE_FK, 0) AS TIENTE_FK " +
						" 	, isnull(b.MST, '') as KhMST, isnull(b.DiaChi, '') as KhDiaChi, isnull(NguoiLienhe, '') as KhNguoiLienHe " +
						"from erp_giamgiahangban a " +
						"inner join erp_khachhang b on a.khachhang_fk = b.pk_seq " +
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
					
					this.nccId = rs.getString("khachhang_fk");
					this.nccTen = rs.getString("khTen");
					
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.diengiai = rs.getString("diengiai");
					this.setTienteId(rs.getString("TIENTE_FK")) ;
					
					this.nccDiaChi = rs.getString("KhDiaChi");
					this.nccNguoiLienHe = rs.getString("KhNguoiLienHe");
					this.nccMaSoThue = rs.getString("KhMST");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		
		//Init Hoa don
		query = "select hoadon_fk from ERP_GIAMGIAHANGBAN_HOADON where giamgia_fk = '" + this.id + "'";
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
				this.msg = "Vui lòng chọn khách hàng.";
				return false;
			}
		 
			 
			
			if(this.sotien != null && this.sotien.length > 0)
			{
				//Check co SP nao duoc nhap tien ko??
				boolean flag = false;
				for(int i = 0; i < this.sotien.length; i++)
				{
					
					try{
					double sotien_ =Double.parseDouble(this.sotien[i].replaceAll(",", "") );
					if(sotien_ != 0 )
					{
						flag = true;
						break;
					}
					}catch(Exception er){
						this.sotien[i]="0";
					}
					
				}
				
				if(!flag)
				{
					this.msg = "Không có sản phẩm nào được nhập số tiền giảm";
					return false;
				}
			}
			
			db.getConnection().setAutoCommit(false);
			
			String tmp="0000000"+this.sohieuhoadon;
			this.sohieuhoadon = ( tmp).substring(tmp.length()-7 ,tmp.length());
			
			String query = " insert erp_giamgiahangban(TIENTE_FK, ngayghinhan, ngayhoadon, kyhieuhoadon, sohoadon, bvat, vat \n" +
						   ", avat, khachhang_fk, diengiai, congty_fk, trangthai, nguoitao, ngaytao, nguoisua, ngaysua, tungay, denngay) \n" +
							"values(" + this.tienteId + ", '" + this.ngayghinhan + "', '" + this.ngayhoadon + "', '" + this.kyhieuhoadon + "' \n" +
							", '" + this.sohieuhoadon + "', '" + this.Bvat.replaceAll(",", "") + "', '" + this.vat.replaceAll(",", "") + "' \n" +
							", '" + this.Avat.replaceAll(",", "") + "', '" + this.nccId + "', N'" + this.diengiai + "', '" + this.congtyId + "' \n" +
							", '0', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "' \n" +
							", '" + this.tungay + "', '" + this.denngay + "') \n";
			System.out.println("query create gia gia: \n" + query + "\n==========================================");
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới erp_giamgiahangban " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " select IDENT_CURRENT('erp_giamgiahangban') as giamgiaId ";
			ResultSet rs = db.get(query);
			if(rs.next())
				this.id = rs.getString("giamgiaId");
			
			for(int i = 0; i < this.hoadonId.length; i++ )
			{
				if(Double.parseDouble(this.sotien[i].replaceAll(",", "") ) !=  0 )
				{
					query = "insert ERP_GIAMGIAHANGBAN_HOADON(giamgia_fk, hoadon_fk, SOTIENTANGGIAM,GHICHU,LOAIHOADON)  " +
							"values('" + this.id + "', '" + this.hoadonId[i] + "',  " + this.sotien[i].replaceAll(",", "") + ",N'"+ghichuhd[i]+"','"+Loaihaodon[i]+"' ) ";
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_GIAMGIAHANGBAN_HOADON " + query;
						db.getConnection().rollback();
						return false;
					}
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
			catch (SQLException e1) {}
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
				this.msg = "Vui lòng chọn khách hàng.";
				return false;
			}
			
			 
			
			if(this.sotien != null && this.sotien.length > 0)
			{
				//Check co SP nao duoc nhap tien ko??
				boolean flag = false;
				for(int i = 0; i < this.sotien.length; i++)
				{
					
					try{
					double sotien_ =Double.parseDouble(this.sotien[i].replaceAll(",", "") );
					if(sotien_ != 0 )
					{
						flag = true;
						break;
					}
					}catch(Exception er){
						this.sotien[i]="0";
					}
					
				}
				
				if(!flag)
				{
					this.msg = "Không có sản phẩm nào được nhập số tiền giảm";
					return false;
				}
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update erp_giamgiahangban set TIENTE_FK = " + this.tienteId + ", ngayghinhan = '" + this.ngayghinhan + "', ngayhoadon = '" + this.ngayhoadon + "', kyhieuhoadon = '" + this.kyhieuhoadon + "', sohoadon = '" + this.sohieuhoadon + "', " +
							"bvat = '" + this.Bvat.replaceAll(",", "") + "', vat = '" + this.vat.replaceAll(",", "") + "', avat = '" + this.Avat.replaceAll(",", "") + "', " +
							"khachhang_fk = '" + this.nccId + "', diengiai = N'" + this.diengiai + "', congty_fk = '" + this.congtyId + "', tungay = '" + this.tungay + "', denngay = '" + this.denngay + "', " +
							"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";

			System.out.println("Câu query1 "+query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật erp_giamgiahangban " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete erp_giamgiahangban_sanpham where giamgiaban_fk = '" + this.id + "' ";
			System.out.println("Câu query2 "+query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật erp_giamgiahangban_sanpham " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_GIAMGIAHANGBAN_HOADON where giamgia_fk = '" + this.id + "' ";
			System.out.println("Câu query3 "+query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_GIAMGIAHANGBAN_HOADON " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < this.hoadonId.length; i++ )
			{
				if(Double.parseDouble(this.sotien[i].replaceAll(",", "") ) != 0 )
				{
					
					query = "insert ERP_GIAMGIAHANGBAN_HOADON(giamgia_fk, hoadon_fk, SOTIENTANGGIAM,GHICHU,LOAIHOADON)  " +
					"values('" + this.id + "', '" + this.hoadonId[i] + "',  " + this.sotien[i].replaceAll(",", "") + ",N'"+ghichuhd[i]+"','"+Loaihaodon[i]+"' ) ";
					System.out.println("Câu query4 "+query);
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_GIAMGIAHANGBAN_HOADON " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
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
		catch (Exception e) {}
		
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

	
	public void createRS() 
	{
		String query = "";
		if(this.nccId.trim().length() > 0)
		{
		 
			if(this.tienteId != null &&  this.tienteId.length() >0){
					query = " select PK_SEQ, NGAYXUATHD, KYHIEU, SOHOADON,  ''  as ghichu,0 AS SOTIENTANGGIAM , TONGTIENAVAT,'HDTC' AS LOAIHOADON  \n" +
							" from ERP_HOADON \n" +
							" where KHACHHANG_FK = '" + this.nccId + "' and TRANGTHAI = '1' and TIENTE_FK = " + this.tienteId + " \n" + 
							" and PK_SEQ not in ( select hoadon_fk from ERP_GIAMGIAHANGBAN_HOADON WHERE LOAIHOADON='HDTC' \n" +
							"					  AND giamgia_fk in ( select PK_SEQ from ERP_GiamGiaHangBan where trangthai <> 2 ) ) \n";
					
					if(this.tungay.trim().length() > 0)
						query += " and NGAYXUATHD >= '" + this.tungay.trim() + "' \n";
					if(this.denngay.trim().length() > 0)
						query += " and NGAYXUATHD <= '" + this.denngay.trim() + "' \n";
					
					query= query+ 			
					" union all  select PK_SEQ, NGAYHOADON, KYHIEUHOADON, SOHOADON,  ''  AS GHICHU,0 AS SOTIENTANGGIAM , AVAT,'HDPL' AS LOAIHOADON  \n" +
					" from ERP_HOADONPHELIEU \n" +
					" where KHACHHANG_FK = '" + this.nccId + "' and TRANGTHAI = '1' \n" + 
					" and PK_SEQ not in ( select hoadon_fk from ERP_GIAMGIAHANGBAN_HOADON where LOAIHOADON='HDPL' AND \n" +
					"					   giamgia_fk in ( select PK_SEQ from ERP_GiamGiaHangBan where trangthai <> 2 ) ) \n";
			
					if(this.tungay.trim().length() > 0)
						query += " and NGAYHOADON >= '" + this.tungay.trim() + "' \n";
					if(this.denngay.trim().length() > 0)
						query += " and NGAYHOADON <= '" + this.denngay.trim() + "' \n";
			
					if (this.id.length() > 0 )
					{
						query += " union  \n" +
						 "  select PK_SEQ, NGAYXUATHD, KYHIEU, SOHOADON ,isnull(B.ghichu,'') as ghichu ,ISNULL( B.SOTIENTANGGIAM ,0) AS SOTIENTANGGIAM ,HD.TONGTIENAVAT,'HDTC' AS LOAIHOADON \n" +
						 "  from ERP_HOADON HD \n" +
						 "  INNER JOIN ERP_GIAMGIAHANGBAN_HOADON B ON HD.PK_SEQ =B.HOADON_FK \n" +
						 "    where B.LOAIHOADON ='HDTC' AND B.giamgia_fk = '" + this.id + "' and HD.TIENTE_FK = " + this.tienteId + "\n" + 
						 "  union \n" +
						 "  select  PK_SEQ, NGAYHOADON, KYHIEUHOADON, SOHOADON,  ''  AS GHICHU, ISNULL( B.SOTIENTANGGIAM ,0) AS SOTIENTANGGIAM , AVAT,'HDPL' AS LOAIHOADON  \n" +
						 "  from ERP_HOADONPHELIEU HD \n" +
						 "  INNER JOIN ERP_GIAMGIAHANGBAN_HOADON B ON HD.PK_SEQ =B.HOADON_FK \n" +
						 "    where B.LOAIHOADON ='HDPL' AND B.giamgia_fk = '" + this.id + "'  \n";
					}
			}
			
			NumberFormat formater = new DecimalFormat("##,###,###");
			System.out.println("___KHOI TAO HD: \n" + query + "\n----------------------------------------------------------");
			this.poRs =db.getScrol(query);
			try 
			{
				int count=0;
				poRs.beforeFirst();
				while(poRs.next())
				{
					count ++;
				}
				
				hdSanpham = new String[count];
				hoadonId = new String[count];
				ghichuhd = new String[count];
				ngayhd = new String[count];
				kyhieuhd = new String[count];
				Loaihaodon = new String[count];
			 
				thanhtien = new String[count];
				sotien = new String[count];
				
				poRs.beforeFirst();
				int pos = 0;
				while(poRs.next())
				{
					hoadonId[pos]=poRs.getString("PK_SEQ");
					hdSanpham[pos] = poRs.getString("SOHOADON");
					ghichuhd[pos] = poRs.getString("ghichu");
					ngayhd[pos] = poRs.getString("NGAYXUATHD");
					kyhieuhd[pos] = poRs.getString("KYHIEU");
					thanhtien[pos] = "" + formater.format(poRs.getDouble("TONGTIENAVAT"));
					sotien[pos] = "" +formater.format (poRs.getDouble("SOTIENTANGGIAM"));
					
					Loaihaodon[pos] = poRs.getString("Loaihoadon");
					pos++;
				}
				poRs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
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

	@Override
	public String[] getKyhieu() {
		// TODO Auto-generated method stub
		return this.kyhieuhd;
	}

	@Override
	public void setKyhieu(String[] Kyhieu) {
		// TODO Auto-generated method stub
		this.kyhieuhd=Kyhieu;
	}

	@Override
	public String[] getNgayHoaDon() {
		// TODO Auto-generated method stub
		return this.ngayhd;
	}

	@Override
	public void setNgayHoaDon(String[] NgayHoaDon) {
		// TODO Auto-generated method stub
		this.ngayhd=NgayHoaDon;
	}

	@Override
	public String[] getGhichu() {
		// TODO Auto-generated method stub
		return this.ghichuhd;
	}

	@Override
	public void setGhichu(String[] Ghichu) {
		// TODO Auto-generated method stub
		this.ghichuhd=Ghichu;
	}

	@Override
	public String[] getHoadonId() {
		// TODO Auto-generated method stub
		return hoadonId;
	}

	@Override
	public void setHoadonId(String[] hoadonId) {
		// TODO Auto-generated method stub
		this.hoadonId=hoadonId;
	}

	@Override
	public String getNccDiaChi() {
		return this.nccDiaChi;
	}

	@Override
	public String getNccMaSoThue() {
		return this.nccMaSoThue;
	}

	@Override
	public String getNccNguoiLienHe() {
		return this.nccNguoiLienHe;
	}

	@Override
	public String[] getLoaihoadon() {
		// TODO Auto-generated method stub
		return this.Loaihaodon;
	}

	@Override
	public void setLoaihoadon(String[] Loaihoadon) {
		// TODO Auto-generated method stub
		this.Loaihaodon=Loaihoadon;
	}
	

}
