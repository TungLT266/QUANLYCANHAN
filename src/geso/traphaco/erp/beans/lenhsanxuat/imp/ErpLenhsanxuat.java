package geso.traphaco.erp.beans.lenhsanxuat.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.danhmucvattu.imp.Danhmucvattu_SP;
import geso.traphaco.erp.beans.lenhsanxuat.IErpLenhsanxuat;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ErpLenhsanxuat implements IErpLenhsanxuat 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String id;
	String ngaytao;
	String ngaydukien;

	String soluongsx;
	String PO;
	String msg;
	String trangthai;
	
	String khoId;
	ResultSet khoRs;
	
	ResultSet spRs;
	String spId;
	
	ResultSet kbsxRs;
	String kbsxId;
	
	ResultSet nhamayRs;
	String nhamayId;
	
	String viewBom;
	String soluongchuan;
	String cpTT;
	List<IDanhmucvattu_SP> dmvtList;
	
	ResultSet chitietNlRs;
	
	dbutils db;
	
	public ErpLenhsanxuat()
	{
		this.id = "";
		this.ngaytao = "";
		this.ngaydukien = "";
		this.soluongsx = "";
		this.msg = "";
		this.khoId = "";
		this.PO = "";
		
		this.spId = "";
		this.kbsxId = "";
		this.nhamayId = "";
		
		this.viewBom = "0";
		this.soluongchuan = "";
		this.cpTT = "0";
		this.trangthai = "0";
		
		this.dmvtList = new ArrayList<IDanhmucvattu_SP>();
		
		this.db = new dbutils();
	}
	
	public ErpLenhsanxuat(String id)
	{
		this.id = id;
		this.ngaytao = "";
		this.ngaydukien = "";
		this.soluongsx = "";
		this.msg = "";
		this.khoId = "";
		this.PO = "";
	
		this.spId = "";
		this.kbsxId = "";
		this.nhamayId = "";
		
		this.viewBom = "0";
		this.soluongchuan = "";
		this.cpTT = "0";
		this.trangthai = "0";
		
		this.dmvtList = new ArrayList<IDanhmucvattu_SP>();
		
		this.db = new dbutils();
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getNgaytao() 
	{
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;
	}

	public String getNgaydukien() 
	{
		return this.ngaydukien;
	}

	public void setNgaydukien(String ngaydk)
	{
		this.ngaydukien = ngaydk;
	}

	public String getSoluong() 
	{
		return this.soluongsx;
	}

	public void setSoluong(String soluong)
	{
		this.soluongsx  =soluong;
	}

	public String getKhottId()
	{
		return this.khoId;
	}

	public void setKhottId(String khott) 
	{
		this.khoId = khott;
	}

	public ResultSet getKhoTTRs()
	{
		return this.khoRs;
	}

	public void setKhoTTRs(ResultSet khottRs)
	{
		this.khoRs = khottRs;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public void setmsg(String msg) 
	{
		this.msg = msg;	
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getTime()
	{
		Date date = new Date();
	    SimpleDateFormat simpDate;

	    //format 24h
	    simpDate = new SimpleDateFormat("kk:mm:ss");
	    
	    return simpDate.format(date);
	}
	
	public boolean createLsx() 
	{
		if(this.ngaytao.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày bắt đầu lệnh sản xuất";
			return false;
		}
		
		if(this.spId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn sản phẩm";
			return false;
		}
		
		if(this.kbsxId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kịch bản sản xuất";
			return false;
		}
		
		if(this.soluongsx.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số lượng sản xuất";
			return false;
		}
		
		/*if(this.dmvtList.size() <= 0)
		{
			this.msg = "Không thể tải thông tin danh mục vật tư. Vui lòng kiểm tra lại kịch bản sản xuất.";
			return false;
		}*/
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "insert ERP_LENHSANXUAT(kichbansanxuat_fk, sanpham_fk, ngaybatdau, ngaydukienHT, soluong, nhamay_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
							"values('" + this.kbsxId + "', '" + this.spId + "', '" + this.ngaytao + "', '" + this.ngaydukien + "', '" +  this.soluongsx+ "', '" + this.nhamayId + "', '0', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "') "; 
							
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới lệnh sản xuất: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//Cho nao cho phep chinh sua Bom ---> Tao table ERP_LENHSANXUAT_BOM
			if(this.dmvtList.size() > 0)
			{
				for(int i = 0; i < this.dmvtList.size(); i++)
				{
					IDanhmucvattu_SP vattu = this.dmvtList.get(i);
					
					if(vattu.getMaVatTu().trim().length() > 0 && vattu.getSoLuong().trim().length() > 0)
					{
						if(this.cpTT.equals("0"))
						{
							query = "insert Erp_LenhSanXuat_Bom(lenhsanxuat_fk, soluongchuan, chophepthaythe, VATTU_FK, SOLUONG)  " +
									"select IDENT_CURRENT('Erp_LenhSanXuat'), '" + this.soluongchuan + "', '" + this.cpTT + "', pk_seq, '" + vattu.getSoLuong() + "' " +
									"from ERP_SanPham where ten = N'" + vattu.getTenVatTu().trim() + "' ";
						}
						else
						{
							if(vattu.getMaVatTuThayThe().trim().length() <= 0  || vattu.getTile().trim().length() <= 0 || vattu.getSoluongTT().trim().length() <= 0 )
							{
								this.msg = "Vui lòng kiểm tra lại thông tin của vật tư thay thể vật tư (" + vattu.getMaVatTu() + ") ";
								db.getConnection().rollback();
								return false;
							}
							
							query = "insert Erp_LenhSanXuat_Bom(lenhsanxuat_fk, soluongchuan, chophepthaythe, VATTU_FK, SOLUONG, VATTUTT_FK, TiLe, SoLuongTT)  " +
									"select IDENT_CURRENT('Erp_LenhSanXuat'), '" + this.soluongchuan + "', '" + this.cpTT + "', a.pk_seq , '" + vattu.getSoLuong() + "', b.PK_SEQ, '" + vattu.getTile() + "', '" + vattu.getSoluongTT() + "' " +
									"from ERP_SanPham a, ERP_SanPham b where a.ten = N'" + vattu.getTenVatTu().trim() + "' and b.ten = N'" + vattu.getTenVatTuThayThe().trim() + "' ";
						}
						
						System.out.println("___ Insert Erp_LenhSanXuat_Bom: " + query);
						
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới Kichbansanxuat: " + query;
							db.getConnection().rollback();
							return false;
						}
						
					}
				}
			}
			else
			{
				//
				query = "insert ERP_LENHSANXUAT_BOM(LENHSANXUAT_FK, SOLUONGCHUAN, CHOPHEPTHAYTHE, VATTU_FK, SOLUONG, VATTUTT_FK, SOLUONGTT, TILE)  " +
		  				"select IDENT_CURRENT('ERP_LENHSANXUAT'), a.SOLUONGCHUAN, a.CHOPHEPTT, b.VATTU_FK, b.SOLUONG * " + this.soluongsx + " / a.SOLUONGCHUAN, b.VATTUTT_FK, b.SOLUONGTT, b.TILE   " +
		  				"from ERP_DANHMUCVATTU a inner join ERP_DANHMUCVATTU_VATTU b on a.PK_SEQ = b.DANHMUCVT_FK  " +
		  				"where a.PK_SEQ = ( select bom_fk from ERP_KICHBANSANXUAT where pk_seq = '" + this.kbsxId + "' ) ";
		  		
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_LENHSANXUAT_BOM: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Không thể tạo mới lệnh sản xuất: " + e.getMessage();
			return false;
		}
		
	}

	public boolean updateLsx() 
	{
		if(this.ngaytao.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày bắt đầu lệnh sản xuất";
			return false;
		}
		
		if(this.spId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn sản phẩm";
			return false;
		}
		
		if(this.kbsxId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kịch bản sản xuất";
			return false;
		}
		
		if(this.nhamayId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nhà máy sản xuất";
			return false;
		}
		
		if(this.soluongsx.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số lượng sản xuất";
			return false;
		}
		
		/*if(this.dmvtList.size() <= 0)
		{
			this.msg = "Không thể tải thông tin danh mục vật tư. Vui lòng kiểm tra lại kịch bản sản xuất.";
			return false;
		}	*/
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "Update ERP_LENHSANXUAT set kichbansanxuat_fk = '" + this.kbsxId + "', sanpham_fk = '" + this.spId + "', ngaybatdau = '" + this.ngaytao + "', ngaydukienHT = '" + this.ngaydukien + "', " +
					"soluong = '" + this.soluongsx + "', nhamay_fk = '" + this.nhamayId + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";
							 			
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật lệnh sản xuất: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.dmvtList.size() > 0)
			{
				query = "delete Erp_LenhSanXuat_Bom where lenhsanxuat_fk = '" + this.id + "'";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật Kichbansanxuat: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				for(int i = 0; i < this.dmvtList.size(); i++)
				{
					IDanhmucvattu_SP vattu = this.dmvtList.get(i);
					
					if(vattu.getMaVatTu().trim().length() > 0 && vattu.getSoLuong().trim().length() > 0)
					{
						if(this.cpTT.equals("0"))
						{
							query = "insert Erp_LenhSanXuat_Bom(lenhsanxuat_fk, soluongchuan, chophepthaythe, VATTU_FK, SOLUONG)  " +
									"select IDENT_CURRENT('Erp_LenhSanXuat'), '" + this.soluongchuan + "', '" + this.cpTT + "', pk_seq, '" + vattu.getSoLuong() + "' " +
									"from ERP_SanPham where ten = N'" + vattu.getTenVatTu().trim() + "' ";
						}
						else
						{
							if(vattu.getMaVatTuThayThe().trim().length() <= 0  || vattu.getTile().trim().length() <= 0 || vattu.getSoluongTT().trim().length() <= 0 )
							{
								this.msg = "Vui lòng kiểm tra lại thông tin của vật tư thay thể vật tư (" + vattu.getMaVatTu() + ") ";
								db.getConnection().rollback();
								return false;
							}
							
							query = "insert Erp_LenhSanXuat_Bom(lenhsanxuat_fk, soluongchuan, chophepthaythe, VATTU_FK, SOLUONG, VATTUTT_FK, TiLe, SoLuongTT)  " +
									"select IDENT_CURRENT('Erp_LenhSanXuat'), '" + this.soluongchuan + "', '" + this.cpTT + "', a.pk_seq , '" + vattu.getSoLuong() + "', b.PK_SEQ, '" + vattu.getTile() + "', '" + vattu.getSoluongTT() + "' " +
									"from ERP_SanPham a, ERP_SanPham b where a.ten = N'" + vattu.getTenVatTu().trim() + "' and b.ten = N'" + vattu.getTenVatTuThayThe().trim() + "' ";
						}
						
						System.out.println("___ Insert Erp_LenhSanXuat_Bom: " + query);
						
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới Kichbansanxuat: " + query;
							db.getConnection().rollback();
							return false;
						}
						
					}
				}
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Không thể tạo mới lệnh sản xuất: " + e.getMessage();
			return false;
		}
	}

	
	public void createRs()
	{
		//this.khoRs = db.get("select PK_SEQ, TEN from ERP_KHOTT");
		this.nhamayRs = db.get("select pk_seq, manhamay + ', ' + tennhamay as nhamayTen from ERP_NHAMAY");
		
		this.spRs = db.get("select pk_seq, ten from ERP_SanPham where trangthai = '1' and loaisanpham_fk in ( 100005, 100011 ) ");
	
		if(this.spId.trim().length() > 0)
		{
			this.kbsxRs = db.get("select pk_seq, ma from Erp_KichBanSanXuat where trangthai = '1' and sanpham_fk = '" + this.spId + "'");
		}
		
		this.dmvtList.clear();
		
		/*if(this.kbsxId.trim().length() > 0)
		{
			//Tinh ngay ket thuc du kien
			String query = "select thoigian + ( select isnull(SUM(thoigian), 0) from ERP_ChiPhiThoiGian where nhamay_fk = '100000' ) as tongthoigian " +
							"from erp_daychuyensanxuat " +
						    "where pk_seq in (  select daychuyen_fk from erp_kichbansanxuat where pk_seq = '" + this.kbsxId + "' )";
			
			System.out.println("__Lay tong thoi gian: " + query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						this.ngaydukien = getNgayDuKien(this.ngaytao, rs.getFloat("tongthoigian"));
					}
					rs.close();
				}
				catch (Exception e) {}
			}
		}*/
		
		/*if(this.PO.trim().length() > 0)  //lenh san xuat chuyen tu Plain ---> sinh ra ke hoach nguyen lieu
		{
			String query = "select a.pk_seq, c.TEN as spTen, a.ngay as ngaybatdau, a.ngaydukienHT, a.soluong, a.tonkho,  " +
								"case a.loai when 0 then 'D' + CAST(a.pk_seq as varchar(10)) when 1 then 'C' + CAST(a.pk_seq as varchar(10)) else 'P' + CAST(a.pk_seq as varchar(10)) end as maso , " +
								"case a.loai when 0 then 'Demand' when '1' then 'Consume' else 'PurchaseRequest' end as loaiTen,  " +
								"case when a.phuthuoc is NULL then '' else 'Pro' + CAST(a.phuthuoc as varchar(10)) end as phuthuoc, 0 as type  " +
							"from ERP_LENHSANXUATDUKIEN_SP_NGUYENLIEU_CHITIET a left join ERP_LENHSANXUAT b on a.phuthuoc = b.pk_seq   " +
								"inner Join ERP_SanPham c on a.nguyenlieu_fk = c.PK_SEQ   " +
							"where  a.lenhsanxuat_fk = '" + this.id + "'  " +
						"union all   " +
							"select '-1' as pk_seq, b.TEN as spTen, a.ngay as ngaybatdau, '', '0', a.tonkho, '', '', '', -1 as type  " +
							"from ERP_LENHSANXUATDUKIEN_SP_NGUYENLIEU a inner Join ERP_SanPham b on a.nguyenlieu_fk = b.PK_SEQ  " +
							"where  a.lenhsanxuat_fk = '" + this.id + "' " +
						"union all  " +
							"select max(a.pk_seq + 1) as pk_seq, b.TEN as spTen,  a.ngay as ngaybatdau, '', COUNT(a.pk_seq) as soPR, SUM(a.soluong) as tongdatPR, '', '', '', 1 as type   " +
							"from ERP_LENHSANXUATDUKIEN_SP_NGUYENLIEU_CHITIET a   inner Join ERP_SanPham b on a.nguyenlieu_fk = b.PK_SEQ  " +
							"where a.loai = '2' and a.lenhsanxuat_fk  = '" + this.id + "' " +
							"group by b.TEN, a.ngay   " +
						"order by spTen asc, pk_seq asc";
			
			System.out.println("__NVL CT: " + query);
			
			this.chitietNlRs = db.get(query);
		}*/
		
	}

	
	private String getNgayDuKien(String ngaybatdau, float sogio) 
	{
		//ngay lam 8h, bat dau tinh tu 8h AM
		int ngay = Math.round( sogio / 8 );
		Calendar c1 = Calendar.getInstance();
		
		String[] arr = ngaybatdau.split("-");
		c1.set(Integer.parseInt( arr[0]), Integer.parseInt( arr[1]) - 1, Integer.parseInt( arr[2]) );
		
		//System.out.println("___Date ngaybatdau: " + c1.get(Calendar.DAY_OF_WEEK) );
		
		c1.add(Calendar.DATE, ngay);
		
		Calendar c2 = Calendar.getInstance();
		c2.set(Integer.parseInt( arr[0]), Integer.parseInt( arr[1]) - 1, Integer.parseInt( arr[2]) );
		
		while( c2.getTime().compareTo(c1.getTime()) < 0 )
		{
			//neu la ngay chu nhat thi phai tang 1 len 1 ngay
			if(c2.get(Calendar.DAY_OF_WEEK) == 8)
				c1.add(Calendar.DATE, 1);
			
			c2.add(Calendar.DATE, 1);
		}
		
		//System.out.println("___Date ngaydukienHT: " + c1.get(Calendar.DAY_OF_WEEK) );
		
		String ngaykt = Integer.toString(c1.get(Calendar.DATE));
		if(ngaykt.trim().length() < 2)
			ngaykt = "0" + ngaykt;
		
		String thangkt = Integer.toString(c1.get(Calendar.MONTH) + 1);
		if(thangkt.trim().length() < 2)
			thangkt = "0" + thangkt;
		
		System.out.println("Ngay du kien tinh duoc: " + Integer.toString(c1.get(Calendar.YEAR)) + "-" + thangkt + "-" + ngaykt);
		return Integer.toString(c1.get(Calendar.YEAR)) + "-" + thangkt + "-" + ngaykt;
	}
	
	private String getNgayBatDau(String ngayhoanthanh, float sogio) 
	{
		//ngay lam 8h, bat dau tinh tu 8h AM
		int ngay = Math.round( sogio / 8 );
		Calendar c1 = Calendar.getInstance();
		
		String[] arr = ngayhoanthanh.split("-");
		c1.set(Integer.parseInt( arr[0]), Integer.parseInt( arr[1]) - 1, Integer.parseInt( arr[2]) );
		
		c1.add(Calendar.DATE, (-1) * ngay);
		

		Calendar c2 = Calendar.getInstance();
		c2.set(Integer.parseInt( arr[0]), Integer.parseInt( arr[1]) - 1, Integer.parseInt( arr[2]) );
	
		while( c2.getTime().compareTo(c1.getTime()) > 0 )
		{
			//neu la ngay chu nhat thi phai tang 1 len 1 ngay
			if(c2.get(Calendar.DAY_OF_WEEK) == 8 || c2.get(Calendar.DAY_OF_WEEK) == 1)
			{
				c1.add(Calendar.DATE, -1);
			}
			
			c2.add(Calendar.DATE, -1);
		}
		
		String ngaykt = Integer.toString(c1.get(Calendar.DATE));
		if(ngaykt.trim().length() < 2)
			ngaykt = "0" + ngaykt;
		
		String thangkt = Integer.toString(c1.get(Calendar.MONTH) + 1);
		if(thangkt.trim().length() < 2)
			thangkt = "0" + thangkt;
		
		//System.out.println("___Date ngay bat dau: " + c1.get(Calendar.DAY_OF_WEEK) );
		System.out.println("1.Ngay bat dau tinh duoc: " + Integer.toString(c1.get(Calendar.YEAR)) + "-" + thangkt + "-" + ngaykt);
		
		return Integer.toString(c1.get(Calendar.YEAR)) + "-" + thangkt + "-" + ngaykt;
	}
	
	public static void main(String[] arg)
	{
		ErpLenhsanxuat lsx = new ErpLenhsanxuat();
		lsx.getNgayDuKien("2013-01-13", 32);
	}

	public void init()
	{
		String query = "select trangthai, ISNULL(lenhsanxuatdukien_fk, '-1') as PO, kichbansanxuat_fk, sanpham_fk, ngaybatdau, isnull(ngaydukienHT, '') as ngaydukienHT, soluong, nhamay_fk " +
						"from ERP_LENHSANXUAT where pk_seq = '" + this.id + "'";
		
		System.out.println("1.Init: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					if(!rs.getString("PO").equals("-1"))
					{
						this.PO = "Pln" + rs.getString("PO");
					}
					
					if(rs.getString("kichbansanxuat_fk") != null)
					{
						this.kbsxId = rs.getString("kichbansanxuat_fk");
					}
					this.spId = rs.getString("sanpham_fk");
					this.nhamayId = rs.getString("nhamay_fk");
					this.ngaytao = rs.getString("ngaybatdau");
					this.ngaydukien = rs.getString("ngaydukienHT");
					this.soluongsx = rs.getString("soluong");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
				
		this.createRs();
	}

	
	public void DBclose()
	{
		try 
		{
			if(this.spRs != null)
				this.spRs.close();
			if(this.nhamayRs != null)
				this.nhamayRs.close();
			if(this.kbsxRs != null)
				this.kbsxRs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.db.shutDown();
		}
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg=msg;
	}

	
	public String getSpId() 
	{
		return this.spId;
	}

	public void setSpId(String spId) 
	{
		this.spId = spId;
	}

	public ResultSet getSpRs()
	{
		return this.spRs;
	}

	public void setSpRs(ResultSet spRs)
	{
		this.spRs = spRs;
	}

	public String getKbsxId() 
	{
		return this.kbsxId;
	}

	public void setKbsxId(String kbsxId)
	{
		this.kbsxId = kbsxId;
	}

	public ResultSet getKbsxRs() 
	{
		return this.kbsxRs;
	}

	public void setKbsxRs(ResultSet kbsxRs) 
	{
		this.kbsxRs = kbsxRs;
	}

	
	public String getNhamayId()
	{
		return this.id;
	}

	public void setNhamayId(String nhamayId) 
	{
		this.nhamayId = nhamayId;
	}

	public ResultSet getNhamayRs()
	{
		return this.nhamayRs;
	}

	public void setNhamayRs(ResultSet nhamayRs) 
	{
		this.nhamayRs = nhamayRs;
	}

	public String getPlaintOrder() 
	{
		return this.PO;
	}

	public void setPlaintOrder(String PO) 
	{
		this.PO = PO;
	}

	public ResultSet getChitietNLRs() 
	{
		return this.chitietNlRs;
	}

	public void setChitietNLRs(ResultSet nlRs) 
	{
		this.chitietNlRs = nlRs;
	}

	public String getTuan(String ngay) 
	{
		String[] ngayArr = ngay.split("-");
		
		int ngayTT = Integer.parseInt(ngayArr[2]);
		
		if( 1 <= ngayTT && ngayTT <= 7)
		{
			return "Tuần 1";
		}
		
		if( 8 <= ngayTT && ngayTT <= 14)
		{
			return "Tuần 2";
		}
		
		if( 15 <= ngayTT && ngayTT <= 21)
		{
			return "Tuần 3";
		}
		
		return "Tuần 4";
	}

	public void setListDanhMuc(List<IDanhmucvattu_SP> list) 
	{
		this.dmvtList = list;
	}

	public List<IDanhmucvattu_SP> getListDanhMuc() 
	{
		return this.dmvtList;
	}

	public String getSoluongchuan() 
	{
		return this.soluongchuan;
	}

	public void setSoluongchuan(String slgchuan) 
	{
		this.soluongchuan = slgchuan;
	}

	public String getChophepTT() 
	{
		return this.cpTT;
	}

	public void setChophepTT(String chophepTT) 
	{
		this.cpTT = chophepTT;
	}

	public void changeKbsx() 
	{
		this.nhamayRs = db.get("select pk_seq, manhamay + ', ' + tennhamay as nhamayTen from ERP_NHAMAY");
		
		this.spRs = db.get("select pk_seq, ten from ERP_SanPham where trangthai = '1' and loaisanpham_fk in ( 100005, 100011 ) ");
	
		if(this.spId.trim().length() > 0)
		{
			this.kbsxRs = db.get("select pk_seq, ma from Erp_KichBanSanXuat where trangthai = '1' and sanpham_fk = '" + this.spId + "'");
		}
		
		if(this.kbsxId.trim().length() > 0)
		{
			//Tinh ngay ket thuc du kien
			String query = "select thoigian + ( select isnull(SUM(thoigian), 0) from ERP_ChiPhiThoiGian where nhamay_fk = '100000' ) as tongthoigian " +
							"from erp_daychuyensanxuat " +
						    "where pk_seq in (  select daychuyen_fk from erp_kichbansanxuat where pk_seq = '" + this.kbsxId + "' )";
			
			System.out.println("___Tong thoi gian: " + query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						if(this.ngaydukien.trim().length() > 0)
						{
							this.ngaytao = getNgayBatDau(this.ngaydukien, rs.getFloat("tongthoigian"));
						}
						else
						{
							this.ngaydukien = getNgayDuKien(this.ngaytao, rs.getFloat("tongthoigian"));
						}
					}
					rs.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		if(this.kbsxId.trim().length() > 0 && this.dmvtList.size() <= 0 && this.soluongsx.trim().length() > 0 )
		{
			String query = "";
			
			query = "select kichbansanxuat_fk from Erp_LenhSanXuat where pk_seq = '" + this.id + "'";
			ResultSet rsCheck = db.get(query);
			
			String kichbansanxuat_fk = "";
			try
			{
				if(rsCheck.next())
				{
					if(rsCheck.getString("kichbansanxuat_fk") != null )
					{
						kichbansanxuat_fk = rsCheck.getString("kichbansanxuat_fk");
					}
				}
				rsCheck.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			
			if( this.id.trim().length() <= 0 || kichbansanxuat_fk.trim().length() <= 0 )
			{
				query = "SELECT SP1.MA AS vtMa, SP1.ten AS vtTen, DVDL1.DONVI AS vtDonvi, VATTU.SOLUONG,  " +
							"ISNULL(SP2.MA, '') AS vtTTMa, SP2.ten AS vtTTTen, ISNULL(DVDL2.donvi, '') AS vtTTDonvi, " + 
							"VATTU.tile, isnull(VATTU.SoLuongTT, '') as SoLuongTT, DANHMUC.SOLUONGCHUAN, DANHMUC.CHOPHEPTT    " +	
						"FROM ERP_DANHMUCVATTU DANHMUC  " +
							"INNER JOIN ERP_DANHMUCVATTU_VATTU VATTU ON VATTU.DANHMUCVT_FK = DANHMUC.PK_SEQ " +
							"INNER Join ERP_SanPham SP1 ON VATTU.VATTU_FK = SP1.PK_SEQ " +
							"LEFT  Join ERP_SanPham SP2 ON VATTU.VATTUTT_FK = SP2.PK_SEQ  " +
							"INNER JOIN DONVIDOLUONG DVDL1 ON SP1.DVDL_FK = DVDL1.PK_SEQ " +
							"LEFT  JOIN DONVIDOLUONG DVDL2 ON SP2.DVDL_FK = DVDL2.PK_SEQ " +
						"WHERE DANHMUC.PK_SEQ = ( select bom_fk from Erp_KichBanSanXuat where pk_seq = '" + this.kbsxId + "' ) ";
			}
			else
			{
				query = "select b.MA as vtMa, b.TEN as vtTen, d.DONVI as vtDonvi, a.SOLUONG,  ISNULL(c.MA, '') as vtTTMa, ISNULL(c.TEN, '') as vtTTTen, ISNULL(e.donvi, '') as vtTTDonvi, " +
							"a.tile, a.soluongTT, a.soluongchuan, a.chophepthaythe as chopheptt  " +
						"from Erp_LenhSanXuat_Bom a inner Join ERP_SanPham b on a.vattu_fk = b.PK_SEQ left Join ERP_SanPham c on a.vattutt_fk = c.pk_seq  " +
						"inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ left join DONVIDOLUONG e on c.DVDL_FK = e.PK_SEQ   " +
						"where a.lenhsanxuat_fk = '" + this.id + "'";
			}
			
			System.out.println("Change BOM: " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				List<IDanhmucvattu_SP> dmvtList = new ArrayList<IDanhmucvattu_SP>();
				
				try 
				{
					IDanhmucvattu_SP vt = null;
					while(rs.next())
					{
						vt = new Danhmucvattu_SP();
						
						vt.setMaVatTu(rs.getString("vtMa"));
						vt.setTenVatTu(rs.getString("vtTen"));
						vt.setDvtVT(rs.getString("vtDonvi"));
						
						if(this.soluongchuan.trim().length() <= 0)
							this.soluongchuan = rs.getString("soluongchuan");
						
						if(this.id.trim().length() <= 0)
						{
							float dinhmuc = Float.parseFloat(this.soluongsx) * rs.getFloat("SOLUONG") / Float.parseFloat(this.soluongchuan);
							vt.setSoLuong(Float.toString(dinhmuc));
						}
						else
						{
							vt.setSoLuong(Float.toString(rs.getFloat("SOLUONG")));
						}
						
						vt.setMaVatTuThayThe(rs.getString("vtTTMa"));
						vt.setTenVatTuThayThe(rs.getString("vtTTTen"));
						vt.setDvtThayThe(rs.getString("vtTTDonvi"));
						vt.setTile(rs.getString("tile"));
						
						if(rs.getString("soluongTT") != null )
						{
							if(this.id.trim().length() <= 0)
							{
								if(rs.getString("soluongTT").trim().length() > 0 )
								{
									float dinhmucTT = Float.parseFloat(this.soluongsx) * rs.getFloat("soluongTT") / Float.parseFloat(this.soluongchuan);
									vt.setSoluongTT(Float.toString(dinhmucTT));
								}
							}
							else
							{
								vt.setSoluongTT(Float.toString(rs.getFloat("soluongTT")));
							}
						}
						
						this.cpTT = rs.getString("chopheptt");
						
						dmvtList.add(vt);
						
					}
					rs.close();
					
					this.dmvtList = dmvtList;
				} 
				catch (Exception e) 
				{
					System.out.println("__Loi khi khoi tao SP cua BOM: " + e.getMessage());
				}
			}
		}
	}
	

	public boolean tieuhaoLsx(String khoTieuhao_fk) 
	{
		if(this.msg.trim().length() > 0)
		{
			this.msg = "Vui lòng kiểm tra lại các thông tin: \n" + this.msg;
			return false;
		}
		
		//Kiem tra xem lenh san xuat da co yeu cau nguyen lieu chua
		String sql = "select yeucaunguyenlieu_fk from ERP_YEUCAUNGUYENLIEU_LSX  " +
					"where lenhsanxuat_fk = '" + this.id + "' and yeucaunguyenlieu_fk in (select pk_seq from ERP_YEUCAUNGUYENLIEU where  trangthai in (1, 2) )";
		ResultSet rsCheck = db.get(sql);
		
		String ycnl_fk = "";
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					ycnl_fk = rsCheck.getString("yeucaunguyenlieu_fk");
				}
				rsCheck.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(ycnl_fk.trim().length() <= 0)
		{
			this.msg = "Lệnh sản xuất này chưa có yêu cầu nguyên liệu, hoặc yêu cầu nguyên liệu chưa chốt. Vui lòng kiểm tra lại.";
			return false;
		}
		
		
		//Check vattu List
		if(this.dmvtList.size() <= 0)
		{
			this.msg = "Không có danh mục vật tư nào để tiêu hao.";
			return false;
		}
		
		for(int i = 0; i < this.dmvtList.size(); i++)
		{
			IDanhmucvattu_SP vattu = this.dmvtList.get(i);
			if(vattu.getSoLuongTHThucTe().trim().length() <= 0 || vattu.getSoLuongTHThucTe().equals("0"))
			{
				this.msg = "Vui lòng nhập số lượng tiêu hao thực tế của vật tư: " + vattu.getMaVatTu() + ", " + vattu.getTenVatTu();
				return false;
			}
			
			List<ISpDetail> spDetail = vattu.getSpDetailList();
			if(spDetail.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại Bean / Lô tiêu hao của vật tư: " + vattu.getMaVatTu() + ", " + vattu.getTenVatTu();
				return false;
			}
			
		}
		
		String nam = this.getDateTime().substring(0, 4);
		String thang = this.getDateTime().substring(5, 7);
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			for(int i = 0; i < this.dmvtList.size(); i++)
			{
				IDanhmucvattu_SP vattu = this.dmvtList.get(i);
				
				String query =  "select b.LOAISANPHAM_FK, GIATON from ERP_KHOTT_SANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ  " +
								"where SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + vattu.getMaVatTu() + "' ) and KHOTT_FK = '" + khoTieuhao_fk + "'";
				ResultSet rsGia = db.get(query);
				float giaton = 0;
				String loaisanpham = "";
				
				if(rsGia != null)
				{
					if(rsGia.next())
					{
						giaton = rsGia.getFloat("GIATON");
						loaisanpham = rsGia.getString("LOAISANPHAM_FK");
					}
					rsGia.close();
				}
				
				query = " Insert ERP_LENHSANXUAT_TIEUHAO (lenhsanxuat_fk, sanpham_fk, soluong, dongia, thanhtien) " +
						" select '" + this.id + "', pk_seq, '" + vattu.getSoLuongTHThucTe() + "', " + giaton + ", " + vattu.getSoLuongTHThucTe() + " * " + giaton +
						" from ERP_SanPham where ma = '" + vattu.getMaVatTu() + "' ";
				
				System.out.println("1.Insert ERP_LENHSANXUAT_TIEUHAO: " + query);
				if(!db.update(query))
				{
					this.msg = "1.Khong the cap nhat ERP_LENHSANXUAT_TIEUHAO: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				
				//Ghi nhan but toan ( NVL theo PP binh quan thoi diem )
				String taikhoanktCo = "";
				String taikhoanktNo = "";
				
				query = "select TAIKHOANKTCO, TAIKHOANKTNO " +
						"from ERP_CAUHINHDINHKHOANKETOAN  " +
						"where NOIDUNGNHAP_FK = '100012' and LOAISANPHAM_FK = '" + loaisanpham + "' ";
				
				System.out.println("5.Query lay tai khoan: " + query);
				
				ResultSet tkRs = db.get(query);
				if(tkRs != null)
				{
					if(tkRs.next())
					{
						taikhoanktCo = tkRs.getString("TAIKHOANKTCO");
						taikhoanktNo = tkRs.getString("TAIKHOANKTNO");
					}
					tkRs.close();
					if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
					{
						this.msg = "Loại sản phẩm và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
						db.getConnection().rollback();
						return false;
					}
					
					//Nguyen te khi tieu hao chinh la VND
					String tiente_fk = "100000";
					double  dongiaViet = giaton;
					
					//Kiem tra xem da cao tai khoan nay trong thang chua
					query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
							"where taikhoankt_fk = '" + taikhoanktNo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
					ResultSet rsTKNo = db.get(query);
					int sodong = 0;
					if (rsTKNo != null)
					{
						if(rsTKNo.next())
						{
							sodong = rsTKNo.getInt("sodong");
						}
						rsTKNo.close();
					}					
					
					if(sodong > 0) //daco
					{
						query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + dongiaViet + "*" + vattu.getSoLuongTHThucTe() + ", " +
															" GIATRINGUYENTE = GIATRINGUYENTE + "  + dongiaViet + "*" + vattu.getSoLuongTHThucTe() + 
								" where taikhoankt_fk = '" + taikhoanktNo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					}
					else
					{
						query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
								"values('" + taikhoanktNo + "', '0', " + dongiaViet + "*" + vattu.getSoLuongTHThucTe() + ", '" + tiente_fk + "', " + dongiaViet + "*" + vattu.getSoLuongTHThucTe() + ", '" + thang + "', '" + nam + "')";
					}
					
					//System.out.println("5.Cap nhat: + query");
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "6.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
						return false;
					}
					
					
					//Tai khoan co
					query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO  " +
							"where taikhoankt_fk = '" + taikhoanktCo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
					rsTKNo = db.get(query);
					
					sodong = 0;
					if(rsTKNo.next())
					{
						sodong = rsTKNo.getInt("sodong");
					}
					rsTKNo.close();
					
					if(sodong > 0) //daco
					{
						query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + dongiaViet + "*" + vattu.getSoLuongTHThucTe() + ", " +
														" GIATRINGUYENTE = GIATRINGUYENTE + "  + dongiaViet + "*" + vattu.getSoLuongTHThucTe() + 
								" where taikhoankt_fk = '" + taikhoanktCo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					}
					else
					{
						query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
								"select '" + taikhoanktCo + "', " + dongiaViet + "*" + vattu.getSoLuongTHThucTe() + ", '0', '" + tiente_fk + "', " + dongiaViet + "*" + vattu.getSoLuongTHThucTe() + ", '" + thang + "', '" + nam + "' ";
					}
					
							
					if(!db.update(query))
					{
						db.getConnection().rollback();
						
						this.msg = "7.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
						return false;
					}
					
				}
				
				
				if(vattu.getSoLuongTHThucTe().trim().length() > 0)
				{
					List<ISpDetail> spDetail = vattu.getSpDetailList();
					for(int j = 0; j < spDetail.size(); j++)
					{
						ISpDetail detail = spDetail.get(j);
						
						String solo = detail.getSolo();
						String soluongct = detail.getSoluong();
						String vitriId = detail.getVitriId();
						
						//
						query = "Insert ERP_LENHSANXUAT_TIEUHAO_CHITIET (lenhsanxuat_fk, sanpham_fk, solo, ngayhethan, soluong, bean) " +
								"select '" + this.id + "', pk_seq, '" + detail.getSolo() + "', '', '" + detail.getSoluong() + "', '" + vitriId + "' " +
								"from ERP_SanPham where ma = '" + vattu.getMaVatTu() + "' ";
						
						System.out.println("2.Insert ERP_LENHSANXUAT_TIEUHAO_CHITIET: " + query);
						if(!db.update(query))
						{
							this.msg = "1.Khong the cap nhat ERP_LENHSANXUAT_TIEUHAO_CHITIET: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						query = "update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + soluongct + "', AVAILABLE = AVAILABLE - '" + soluongct + "' " +
								"where KHOTT_FK = ( select KhoTT_fk from ERP_VITRIKHO where pk_seq = '" + vitriId + "' ) " +
									   "and SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + vattu.getMaVatTu() + "' ) " +
									   	"and SOLO = '" + solo + "' and BIN = '" + vitriId + "'";
						
						System.out.println("3.Update ERP_KHOTT_SP_CHITIET: " + query);
						if(!db.update(query))
						{
							this.msg = "3.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						
						query = "update ERP_KHOTT_SANPHAM set soluong = soluong - '" + soluongct + "', AVAILABLE = AVAILABLE - '" + soluongct + "' " +
								"where KHOTT_FK = ( select KhoTT_fk from ERP_VITRIKHO where pk_seq = '" + vitriId + "' ) " +
									   	"and SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + vattu.getMaVatTu() + "' ) ";
						
						System.out.println("4.Update ERP_KHOTT_SANPHAM: " + query);
						if(!db.update(query))
						{
							this.msg = "4.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
					}
					
				}
			}
			
			String query = "update ERP_LENHSANXUAT set ngaytieuhao = '" + getDateTime() + "', giotieuhao = '" + getTime() + "', trangthai = '3' where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "4.Khong the cap nhat ERP_LENHSANXUAT: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("roolback");
			System.out.println("Ecception: " + e.getMessage());
			
			this.msg = "Ecception: " + e.getMessage();
			return false;
		}

		return true;
	}

	public boolean checkTieuHaoLsx() 
	{
		String query = "select COUNT(*) as sodong from ERP_LENHSANXUAT_TIEUHAO where lenhsanxuat_fk = '" + this.id + "'";
		ResultSet rsCheck = db.get(query);
		
		int sodong = 0;
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					sodong = rsCheck.getInt("sodong");
				}
				rsCheck.close();
			} 
			catch (Exception e) 
			{ 
				sodong = 0; 
				this.msg = "114.Exception: " + e.getMessage();
				return false;
			}
		}
		
		if(sodong > 0) //Da tieu hao
		{
			this.initLsx_TieuHao();
		}
		else
		{
			//Chua tieu hao, co the thay doi danh muc vat tu, phai luu lai truoc
			if(this.dmvtList.size() > 0)
			{
				try
				{
					db.getConnection().setAutoCommit(false);
					
					query = "delete Erp_LenhSanXuat_Bom where lenhsanxuat_fk = '" + this.id + "'";
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật Erp_LenhSanXuat_Bom: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					for(int i = 0; i < this.dmvtList.size(); i++)
					{
						IDanhmucvattu_SP vattu = this.dmvtList.get(i);
						
						if(vattu.getTenVatTu().trim().length() > 0 && vattu.getSoLuong().trim().length() > 0)
						{
							if(this.cpTT.equals("0"))
							{
								query = "insert Erp_LenhSanXuat_Bom(lenhsanxuat_fk, soluongchuan, chophepthaythe, VATTU_FK, SOLUONG)  " +
										"select '" + this.id + "', '" + this.soluongchuan + "', '" + this.cpTT + "', pk_seq, '" + vattu.getSoLuong() + "' " +
										"from ERP_SanPham where ten = N'" + vattu.getTenVatTu().trim() + "' ";
							}
							else
							{
								if(vattu.getMaVatTuThayThe().trim().length() <= 0  || vattu.getTile().trim().length() <= 0 || vattu.getSoluongTT().trim().length() <= 0 )
								{
									this.msg = "Vui lòng kiểm tra lại thông tin của vật tư thay thể vật tư (" + vattu.getMaVatTu() + ") ";
									db.getConnection().rollback();
									return false;
								}
								
								query = "insert Erp_LenhSanXuat_Bom(lenhsanxuat_fk, soluongchuan, chophepthaythe, VATTU_FK, SOLUONG, VATTUTT_FK, TiLe, SoLuongTT)  " +
										"select IDENT_CURRENT('Erp_LenhSanXuat'), '" + this.soluongchuan + "', '" + this.cpTT + "', a.pk_seq , '" + vattu.getSoLuong() + "', b.PK_SEQ, '" + vattu.getTile() + "', '" + vattu.getSoluongTT() + "' " +
										"from ERP_SanPham a, Erp_SANPHAM b where a.ten = N'" + vattu.getTenVatTu() + "' and b.ten = N'" + vattu.getTenVatTuThayThe() + "' ";
							}
							
							if(!db.update(query))
							{
								this.msg = "Không thể cập nhật Erp_LenhSanXuat_Bom: " + query;
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
					db.update("rollback");
					this.msg = "Không thể cập nhật Erp_LenhSanXuat_Bom " + e.getMessage();
					return false;
				}
			}
			
			this.initTieuHao();
		}
		
		return true;
	}

	private void initTieuHao() 
	{
		String query = "select trangthai, ISNULL(lenhsanxuatdukien_fk, '-1') as PO, kichbansanxuat_fk, sanpham_fk, ngaybatdau, ngaydukienHT, soluong, nhamay_fk " +
						"from ERP_LENHSANXUAT where pk_seq = '" + this.id + "'";
		
		System.out.println("1.Init tieu hao: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					if(!rs.getString("PO").equals("-1"))
					{
						this.PO = "Pln" + rs.getString("PO");
					}
					this.kbsxId = rs.getString("kichbansanxuat_fk");
					this.spId = rs.getString("sanpham_fk");
					this.nhamayId = rs.getString("nhamay_fk");
					this.ngaytao = rs.getString("ngaybatdau");
					this.ngaydukien = rs.getString("ngaydukienHT");
					this.soluongsx = rs.getString("soluong");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		query = "select b.MA as vtMa, b.Ten as vtTen, d.DONVI as vtDonvi, a.SOLUONG,  ISNULL(c.MA, '') as vtTTMa, ISNULL(c.TEN, '') as vtTTTen, ISNULL(e.donvi, '') as vtTTDonvi, " +
					"a.tile, a.soluongTT, a.soluongchuan, a.chophepthaythe   " +
				"from Erp_LenhSanXuat_Bom a inner Join ERP_SanPham b on a.vattu_fk = b.PK_SEQ left Join ERP_SanPham c on a.vattutt_fk = c.pk_seq  " +
				"inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ left join DONVIDOLUONG e on c.DVDL_FK = e.PK_SEQ   " +
				"where a.lenhsanxuat_fk = '" + this.id + "'";
		
		System.out.println("__Khoi tao BOM: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			List<IDanhmucvattu_SP> dmvtList = new ArrayList<IDanhmucvattu_SP>();
		
			try 
			{
				IDanhmucvattu_SP vt = null;
				while(rs.next())
				{
					vt = new Danhmucvattu_SP();
					
					vt.setMaVatTu(rs.getString("vtMa"));
					vt.setTenVatTu(rs.getString("vtTen"));
					vt.setDvtVT(rs.getString("vtDonvi"));
					
					vt.setSoLuong(Float.toString(rs.getFloat("SOLUONG")));
					
					vt.setMaVatTuThayThe(rs.getString("vtTTMa"));
					vt.setTenVatTuThayThe(rs.getString("vtTTTen"));
					vt.setDvtThayThe(rs.getString("vtTTDonvi"));
					
					if( rs.getString("tile") != null )
					{
						vt.setTile(rs.getString("tile"));
					}
					
					if(rs.getString("soluongTT") != null )
					{
						if(rs.getString("soluongTT").trim().length() > 0 )
						{
							vt.setSoluongTT(Float.toString(rs.getFloat("soluongTT")));
						}
					}
					
					this.cpTT = rs.getString("chophepthaythe");
					
					dmvtList.add(vt);
					
				}
				rs.close();
				
				this.dmvtList = dmvtList;
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
	}

	private void initLsx_TieuHao() 
	{
		String query = "select trangthai, ISNULL(lenhsanxuatdukien_fk, '-1') as PO, kichbansanxuat_fk, sanpham_fk, ngaybatdau, ngaydukienHT, soluong, nhamay_fk " +
						"from ERP_LENHSANXUAT where pk_seq = '" + this.id + "'";
		
		System.out.println("1.Init: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					if(!rs.getString("PO").equals("-1"))
					{
						this.PO = "Pln" + rs.getString("PO");
					}
					
					this.kbsxId = rs.getString("kichbansanxuat_fk");
					this.spId = rs.getString("sanpham_fk");
					this.nhamayId = rs.getString("nhamay_fk");
					this.ngaytao = rs.getString("ngaybatdau");
					this.ngaydukien = rs.getString("ngaydukienHT");
					this.soluongsx = rs.getString("soluong");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		query = "select b.pk_seq as spId, b.MA as vtMa, b.Ten as vtTen, d.DONVI as vtDonvi, a.SOLUONG,  ISNULL(c.MA, '') as vtTTMa, " +
					"ISNULL(c.TEN, '') as vtTTTen, ISNULL(e.donvi, '') as vtTTDonvi, a.tile, a.soluongchuan, a.soluongTT, a.chophepthaythe, f.soluong as tieuhao   " +
				"from Erp_LenhSanXuat_Bom a inner Join ERP_SanPham b on a.vattu_fk = b.PK_SEQ left Join ERP_SanPham c on a.vattutt_fk = c.pk_seq  " +
				"inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ left join DONVIDOLUONG e on c.DVDL_FK = e.PK_SEQ " +
				"inner join ERP_LENHSANXUAT_TIEUHAO  f on a.lenhsanxuat_fk = f.lenhsanxuat_fk and a.vattu_fk = f.sanpham_fk  " +
				"where a.lenhsanxuat_fk = '" + this.id + "'";
		
		System.out.println("2__Khoi tao BOM da tieu hao: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			List<IDanhmucvattu_SP> dmvtList = new ArrayList<IDanhmucvattu_SP>();
			
			try 
			{
				IDanhmucvattu_SP vt = null;
				while(rs.next())
				{
					vt = new Danhmucvattu_SP();
					
					vt.setMaVatTu(rs.getString("vtMa"));
					vt.setTenVatTu(rs.getString("vtTen"));
					vt.setDvtVT(rs.getString("vtDonvi"));
					vt.setSoLuong(rs.getString("SOLUONG"));
					vt.setSoLuongTHThucTe(rs.getString("tieuhao"));
					
					vt.setMaVatTuThayThe(rs.getString("vtTTMa"));
					vt.setTenVatTuThayThe(rs.getString("vtTTTen"));
					vt.setDvtThayThe(rs.getString("vtTTDonvi"));
					vt.setTile(rs.getString("tile"));
					
					if(this.soluongchuan.trim().length() <= 0)
						this.soluongchuan = rs.getString("soluongchuan");
					
					this.cpTT = rs.getString("chophepthaythe");
					
					if(rs.getString("soluongTT") != null)
					{
						float dinhmucTT = Float.parseFloat(this.soluongsx) * rs.getFloat("soluongTT") / Float.parseFloat(this.soluongchuan);
						vt.setSoluongTT(Float.toString(dinhmucTT));
					}
					
					
					//Them chi tiet da tieu hao
					query = "select a.SOLO, a.SOLUONG, c.pk_seq as khuId, c.diengiai as khuTen, b.PK_SEQ as vtId, b.MA as vitri " +
							"from ERP_LENHSANXUAT_TIEUHAO_CHITIET a inner join ERP_VITRIKHO b on a.BEAN = b.PK_SEQ " +
									"inner join ERP_KHUVUCKHO c on b.KHU_FK = c.pk_seq " +
							"where a.lenhsanxuat_fk = '" + this.id + "' and a.SANPHAM_FK = '" + rs.getString("spId") + "'";
					
					System.out.println("3.San pham tieu hao Detail: " + query);
					ResultSet rsSpDetail = db.get(query);
					
					List<ISpDetail> spConList = new ArrayList<ISpDetail>();
					ISpDetail spCon = null;
					if(rsSpDetail != null)
					{
						while(rsSpDetail.next())
						{
							String idhangmua = rs.getString("spId");
							String solo = rsSpDetail.getString("solo");
							String slg = rsSpDetail.getString("soluong");
							String khu = rsSpDetail.getString("khuTen");
							String vitri = rsSpDetail.getString("vitri");
							String vitriId = rsSpDetail.getString("vtId");
							
							spCon = new SpDetail(idhangmua, solo, slg, khu, vitri, vitriId);
							spConList.add(spCon);
						}
						rsSpDetail.close();
					}
					
					vt.setSpDetailList(spConList);	
					
					dmvtList.add(vt);
					
				}
				rs.close();
				
				this.dmvtList = dmvtList;
			} 
			catch (Exception e) 
			{
				System.out.println("4__Exception: " + e.getMessage());
			}
		}
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}


	public void initDisplay() 
	{
		String query = "select trangthai, ISNULL(lenhsanxuatdukien_fk, '-1') as PO, kichbansanxuat_fk, sanpham_fk, ngaybatdau, ngaydukienHT, soluong, nhamay_fk " +
				"from ERP_LENHSANXUAT where pk_seq = '" + this.id + "'";
		
		System.out.println("1.Init display: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					if(!rs.getString("PO").equals("-1"))
					{
						this.PO = "Pln" + rs.getString("PO");
					}
					this.kbsxId = rs.getString("kichbansanxuat_fk");
					this.spId = rs.getString("sanpham_fk");
					this.nhamayId = rs.getString("nhamay_fk");
					this.ngaytao = rs.getString("ngaybatdau");
					this.ngaydukien = rs.getString("ngaydukienHT");
					this.soluongsx = rs.getString("soluong");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			} 
			catch (Exception e) {}
		}
		
		query = "select b.MA as vtMa, b.Ten as vtTen, d.DONVI as vtDonvi, a.SOLUONG,  ISNULL(c.MA, '') as vtTTMa, ISNULL(c.TEN, '') as vtTTTen, ISNULL(e.donvi, '') as vtTTDonvi, " +
					"a.tile, a.soluongTT, a.soluongchuan, a.chophepthaythe   " +
				"from Erp_LenhSanXuat_Bom a inner Join ERP_SanPham b on a.vattu_fk = b.PK_SEQ left Join ERP_SanPham c on a.vattutt_fk = c.pk_seq  " +
				"inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ left join DONVIDOLUONG e on c.DVDL_FK = e.PK_SEQ   " +
				"where a.lenhsanxuat_fk = '" + this.id + "'";
		
		System.out.println("__Khoi tao BOM: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			List<IDanhmucvattu_SP> dmvtList = new ArrayList<IDanhmucvattu_SP>();
		
			try 
			{
				IDanhmucvattu_SP vt = null;
				while(rs.next())
				{
					vt = new Danhmucvattu_SP();
					
					vt.setMaVatTu(rs.getString("vtMa"));
					vt.setTenVatTu(rs.getString("vtTen"));
					vt.setDvtVT(rs.getString("vtDonvi"));
					
					/*if(this.soluongchuan.trim().length() <= 0)
						this.soluongchuan = rs.getString("soluongchuan");
					
					float dinhmuc = Float.parseFloat(this.soluongsx) * rs.getFloat("SOLUONG") / Float.parseFloat(this.soluongchuan);
					//System.out.println("__Dinh muc: " + dinhmuc);
					vt.setSoLuong(Float.toString(dinhmuc));*/
					
					vt.setSoLuong(Float.toString(rs.getFloat("SOLUONG")));
					
					vt.setMaVatTuThayThe(rs.getString("vtTTMa"));
					vt.setTenVatTuThayThe(rs.getString("vtTTTen"));
					vt.setDvtThayThe(rs.getString("vtTTDonvi"));
					
					if( rs.getString("tile") != null )
					{
						vt.setTile(rs.getString("tile"));
					}
					
					if(rs.getString("soluongTT") != null )
					{
						if(rs.getString("soluongTT").trim().length() > 0 )
						{
							/*float dinhmucTT = Float.parseFloat(this.soluongsx) * rs.getFloat("soluongTT") / Float.parseFloat(this.soluongchuan);
							vt.setSoluongTT(Float.toString(dinhmucTT));*/
							
							vt.setSoluongTT(Float.toString(rs.getFloat("soluongTT")));
						}
					}
					
					this.cpTT = rs.getString("chophepthaythe");
					
					dmvtList.add(vt);
					
				}
				rs.close();
				
				this.dmvtList = dmvtList;
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		
		this.createRs();	
	}

	public String getViewBom() 
	{
		return this.viewBom;
	}

	public void setViewBom(String viewBom) 
	{
		this.viewBom = viewBom;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}
	
}
