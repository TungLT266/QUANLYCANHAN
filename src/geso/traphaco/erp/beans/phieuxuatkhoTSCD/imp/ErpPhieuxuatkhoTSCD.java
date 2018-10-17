package geso.traphaco.erp.beans.phieuxuatkhoTSCD.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.phieuxuatkhoTSCD.IErpPhieuxuatkhoTSCD;
import geso.traphaco.erp.beans.phieuxuatkhoTSCD.ISanpham;
import geso.traphaco.erp.beans.phieuxuatkhoTSCD.ISpDetail;

public class ErpPhieuxuatkhoTSCD implements IErpPhieuxuatkhoTSCD 
{
	String congtyId;
	String userId;
	String id;
	String ngayxuatkho;
	String ngaychotNV;
	
	String khId;
	String khTen;
	
	String nccId;
	String nccTen;
	
	String ddhId;
	ResultSet ddhRs;
	String trahangNccId;
	ResultSet trahangNccRs;
	
	String khoId;
	ResultSet khoRs;
	
	String ndxId="";
	ResultSet ndxRs;
	
	String lydoxuat;
	String ghichu;
	String trangthai;
	boolean quanlybean;
	boolean quanlylo;
	
	List<ISanpham> spList;
	
	//pdf
	String nguoinhanhang;
	String diachinhan;
	String xuattaikho;
	String sochungtu;
	String dungsai;
	
	String msg;

	dbutils db;
	
	public ErpPhieuxuatkhoTSCD()
	{
		this.userId = "";
		this.id = "";
		this.ngayxuatkho = "";
		this.ngaychotNV = "";
		this.khId = "";
		this.khTen = "";
		this.ddhId = "";
		this.ndxId = "";
		this.lydoxuat = "";
		this.ghichu = "";
		this.trangthai = "";
		this.msg = "";
		this.nccId = "";
		this.nccTen = "";
		this.trahangNccId = "";
		this.quanlybean = true;
		this.quanlylo = true;
		
		this.nguoinhanhang = "";
		this.diachinhan = "";
		this.xuattaikho = "";
		this.sochungtu = "";
		this.dungsai = "0";
		
		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
	}
	
	public ErpPhieuxuatkhoTSCD(String id)
	{
		this.userId = "";
		this.id = id;
		this.ngayxuatkho = "";
		this.ngaychotNV = "";
		this.khId = "";
		this.khTen = "";
		this.ddhId = "";
		this.khoId = "";
		this.ndxId = "";
		this.lydoxuat = "";
		this.ghichu = "";
		this.trangthai = "";
		this.msg = "";
		this.quanlybean = true;
		this.quanlylo = true;
		
		this.nguoinhanhang = "";
		this.diachinhan = "";
		this.xuattaikho = "";
		this.nccId = "";
		this.nccTen = "";
		this.trahangNccId = "";

		this.sochungtu = "";
		this.dungsai = "0";
		
		this.spList = new ArrayList<ISanpham>();
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

	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getngayxuatkho()
	{
		return this.ngayxuatkho;
	}

	public void setngayxuatkho(String ngayxuatkho) 
	{
		this.ngayxuatkho = ngayxuatkho;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public String getNgayxuatkho() 
	{
		return this.ngayxuatkho;
	}

	public void setNgayxuatkho(String ngayxuatkho) 
	{
		this.ngayxuatkho = ngayxuatkho;
	}

	public String getDondathangId()
	{
		return this.ddhId;
	}

	public void setDondathangId(String ddhid)
	{
		this.ddhId = ddhid;
	}

	public String getKhoId() 
	{
		return this.khoId;
	}

	public void setKhoId(String khoId) 
	{
		this.khoId = khoId;
	}

	public ResultSet getKhoList()
	{
		return this.khoRs;
	}

	public void setKhoList(ResultSet kholist) 
	{
		this.khoRs = kholist;
	}

	public String getNdxId()
	{
		return this.ndxId;
	}

	public void setNdxId(String ndxId) 
	{
		this.ndxId = ndxId;
	}

	public ResultSet getNdxList()
	{
		return this.ndxRs;
	}

	public void setNdxList(ResultSet ndxList) 
	{
		this.ndxRs = ndxList;	
	}

	public boolean createPxk()
	{
		 
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong phiếu xuất kho: \n" + this.msg;
				System.out.println(this.msg);
				return false;
			}
		
		try 
		{
			String ngaytao = this.getDateTime();
			
			db.getConnection().setAutoCommit(false);
			
			String trahang_fk = this.trahangNccId;
			if(this.nccId.trim().length() <= 0)
				trahang_fk = "null";
			
			String query = "insert ERP_XUATTRATSCD(NGAYXUAT, NGAYCHOT, TRAHANGNCC_FK, NOIDUNGXUAT, GHICHU, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI, CONGTY_FK) " +
						" values('" + this.ngayxuatkho + "', '" + this.ngayxuatkho + "', " + trahang_fk + ", '" + this.ndxId + "', N'" + this.ghichu + "', " +
								"'" + this.userId + "', '" + this.userId + "', '" + ngaytao + "', '" + ngaytao + "', '0', '" + this.congtyId + "')";
			
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi phieu xuat trả TSCD: " + query;
				System.out.println("Loi: "  + query);
				db.getConnection().rollback();
				return false;
			}
			
			String xkCurrent = "";
			query = "select IDENT_CURRENT('ERP_XUATTRATSCD') as xkId";
			
			ResultSet rsPxk = db.get(query);						
			if(rsPxk.next())
			{
				xkCurrent = rsPxk.getString("xkId");
				rsPxk.close();
			}
			
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					ISanpham sp = this.spList.get(i);

					
					query = " insert ERP_XUATTRATSCD_TSCD(XUATTRATSCD_FK, TSCD_FK, SOLUONG, DONGIA, THANHTIEN, DONVI,  GHICHU) " +
							" select '" + xkCurrent + "', '" + sp.getId() + "', '" + sp.getSoluong().replaceAll(",", "") + "', DONGIA, DONGIA * '" + sp.getSoluong().replaceAll(",", "") + "', N'" + sp.getDVT() + "', N'" + sp.getGhiChu() + "' " +
							" from ERP_TAISANCODINH where  PK_SEQ = '" + sp.getId() + "' ";
					
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_XUATTRATSCD_TSCD: " + query;
						db.getConnection().rollback();
						return false;
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
			this.msg = "Lỗi khi tạo xuất kho: " + e.getMessage();
			return false;
		}
	}

	public boolean updatePxk() 
	{
		
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong phiếu xuất kho: \n" + this.msg;
				System.out.println(this.msg);
				return false;
			}
			
		try 
		{	
			db.getConnection().setAutoCommit(false);
			
			String ddh_fk = this.ddhId;
			if(this.khId.trim().length() <= 0)
				ddh_fk = "null";
			
			String trahang_fk = this.trahangNccId;
			if(this.nccId.trim().length() <= 0)
				trahang_fk = "null";
			
			if(this.ngaychotNV.trim().length() <= 0 )
				this.ngaychotNV = this.ngayxuatkho;
			String query = " update ERP_XUATTRATSCD set NGAYXUAT = '" + this.ngayxuatkho + "', NGAYCHOT = '" + this.ngaychotNV + "', TRAHANGNCC_FK = " + trahang_fk + ", NOIDUNGXUAT = '" + this.ndxId + "',  " +
							" GHICHU = N'" + this.ghichu + "', NGUOISUA = '" + this.userId + "', NGAYSUA = '" + this.getDateTime() + "', TRANGTHAI = '0' where pk_seq = '" + this.id + "'";
			
			System.out.println("___CAP NHAT ERP_XUATTRATSCD: " + query );
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat phieu xuat tra TSCD: " + query;
				System.out.println("Loi: "  + query);
				db.getConnection().rollback();
				return false;
			}
						
			
			
			query = "delete ERP_XUATTRATSCD_TSCD where XUATTRATSCD_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat phieu xuat tra TSCD: " + query;
				db.getConnection().rollback();
				return false;
			}

			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					ISanpham sp = this.spList.get(i);
										
					
					query = "insert ERP_XUATTRATSCD_TSCD(XUATTRATSCD_FK, TSCD_FK, SOLUONG, DONGIA, THANHTIEN, DONVI, GHICHU) " +
							"select '" + this.id + "', '" + sp.getId() + "', '" + sp.getSoluong().replaceAll(",", "") + "', DONGIA, DONGIA * '" + sp.getSoluong().replaceAll(",", "") + "', N'" + sp.getDVT() + "', N'" + sp.getGhiChu() + "' " +
							"from ERP_TAISANCODINH where  PK_SEQ = '" + sp.getId() + "' ";
			
					
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_XUATTRATSCD_TSCD: " + query;
						db.getConnection().rollback();
						return false;
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
			this.msg = "Không thể cập nhật PXT_TSCD: " + e.getMessage();
			return false;
		}
	}

	public void createRs()
	{
		String query="";
		this.ndxRs = db.get("select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP where trangthai = '1' AND PK_SEQ=100007");
		
		if(this.nccId.length() > 0)
		{
			query = "select PK_SEQ as dmhId, cast(PK_SEQ as nvarchar(10)) + ' _ ngay tra ' + NGAYMUA as dmhTen " +
					"from ERP_MUAHANG where TYPE = '1' and TRANGTHAI = '1' and NHACUNGCAP_FK = '" + this.nccId + "' and congty_fk = '" + this.congtyId + "' ";
						
			if(this.id.length() > 0)
			{
				query += " and PK_SEQ not in (select trahangncc_fk from ERP_XUATTRATSCD where trahangncc_fk is not null and TRANGTHAI in (0, 1) and pk_seq != '" + this.id + "' )";
				
				query += " union  select PK_SEQ as dmhId, cast(PK_SEQ as nvarchar(10)) + ' _ ngay tra ' + NGAYMUA as dmhTen " +
						 "from ERP_MUAHANG where pk_seq in ( select trahangncc_fk from ERP_XUATTRATSCD where pk_seq = '" + this.id + "' ) ";
			}
			else
				query += " and PK_SEQ not in (select trahangncc_fk from ERP_XUATTRATSCD where trahangncc_fk is not null and TRANGTHAI in (0, 1))";
			
			System.out.println("Khoi tao Don mua hang: " + query);
			this.trahangNccRs = db.get(query);
		}
		
		//System.out.println("____SP LIST SIZE: " + this.spList.size());
		if( ( this.trahangNccId.length() > 0)  && this.spList.size() <= 0 )
		{
			this.createSanpham();
		}
	}

	private void createSanpham()
	{
		String query = "";

			query =	"select a.TAISAN_FK as spId, b.ma as spMa, b.diengiai as spTen, a.DonVi, a.SOLUONG as totalLuong, '1' as makeToStock, '0' as tongXuat, N'' as ghichu " +
					"from ERP_MUAHANG_SP a inner join ERP_TAISANCODINH b on a.TAISAN_FK = b.PK_SEQ " +
					"where a.MUAHANG_FK = '" + this.trahangNccId + "'";
		
		
		System.out.println("123.Khoi tao LIST SP: " + query);
		
		ResultSet spRs = db.get(query);
		
		List<ISanpham> spList = new ArrayList<ISanpham>();
		
		NumberFormat formater = new DecimalFormat("#,###,###.###");
		
		if(spRs != null)
		{
			try 
			{
				ISanpham sp = null;
				while(spRs.next())
				{
					String spId = spRs.getString("spId");
					String spMa = spRs.getString("spMa");
					String spTen = spRs.getString("spTen");
					long soluong = Math.round(spRs.getDouble("totalLuong"));
					//String makeToStock = spRs.getString("makeToStock");
					
					//NewToyo khong chuyen dondathang thanh lenhsanxuat
					
					
					sp = new Sanpham(spId, spMa, spTen, "", Long.toString(soluong));
					sp.setSoluongTotal(formater.format(spRs.getDouble("totalLuong")));
					sp.setSoluongDaXuat(formater.format(spRs.getDouble("tongXuat")));
					sp.setSoluong(formater.format(spRs.getDouble("totalLuong") - spRs.getDouble("tongXuat")));
			
					sp.setDVT(spRs.getString("donvi"));
					//sp.setGhiChu(spRs.getString("ghichu"));

					spList.add(sp);
				}
				spRs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("1.Exception: " + e.getMessage());
			}
			
			this.spList = spList;
		}
			
		
	}
	
	public void changeDdh() 
	{
		if(( this.trahangNccId.length() > 0) && this.ndxId.length() > 0 && this.khoId.length() > 0)
		{
			this.createSanpham();
		}
		else
			this.spList = new ArrayList<ISanpham>();
	}
	
	public void init() 
	{
		String query =  " select a.PK_SEQ as pxkId, a.NGAYXUAT, a.NGAYCHOT, a.TRAHANGNCC_FK, a.GHICHU, a.NOIDUNGXUAT," +
						"   c.nhacungcap_fk as nccId, ncc.ma + ', ' + ncc.Ten as nccTen, a.TRANGTHAI " +
						" from ERP_XUATTRATSCD a " +
						" left join ERP_MUAHANG c on a.TRAHANGNCC_FK = c.PK_SEQ " +
						" left join ERP_NHACUNGCAP ncc on c.nhacungcap_fk = ncc.pk_seq " + 
						" where a.pk_seq = '" + this.id + "'";
		
		System.out.println("PXK: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngayxuatkho = rs.getString("ngayxuat");
					this.ngaychotNV = rs.getString("NGAYCHOT");
				
					this.nccId = "";
					if(rs.getString("nccId") != null)
						this.nccId = rs.getString("nccId");
					
					this.nccTen = "";
					if(rs.getString("nccTen") != null)
						this.nccTen = rs.getString("nccTen");
					
				
					this.trahangNccId = "";
					if(rs.getString("TRAHANGNCC_FK") != null)
						this.trahangNccId = rs.getString("TRAHANGNCC_FK");
					
					this.ndxId = rs.getString("NOIDUNGXUAT");
					this.ghichu = rs.getString("GHICHU");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("1.Exception: " + e.getMessage());
			}
		}
		
		this.initSanPham();
		
		this.createRs();
	}
	
	public void initPdf()
	{
		Utility util = new Utility();
		String query = "select a.PK_SEQ as pxkId, a.NGAYXUAT, a.GHICHU, e.MA + '; ' + e.TEN as NOIDUNGXUAT, " +
						"  g.pk_seq as nccId, a.TRANGTHAI, " +
						" f.prefix as preTH, isnull(a.TRAHANGNCC_FK,0) as TRAHANGNCC_FK, " +
						"g.ma + ', ' + g.ten as nccTen, isnull(g.DIACHI, 'na') as diachiNcc, " +
						"( z.PREFIX + cast(a.TRAHANGNCC_fk as varchar(20)) )  as SOCHUNGTU " +
					"from ERP_XUATTRATSCD  a " +
						" left join ERP_MUAHANG f on a.trahangncc_fk = f.pk_seq " +
						" left join ERP_NHACUNGCAP g on f.nhacungcap_fk = g.pk_seq " +
						" inner join ERP_NOIDUNGNHAP e on a.NOIDUNGXUAT = e.PK_SEQ " +
						" left join ERP_DONVITHUCHIEN z on f.donvithuchien_fk = z.pk_seq and z.pk_seq in  " + util.quyen_donvithuchien(this.userId) +
					"where a.pk_seq = '" + this.id + "'";
	
		
		System.out.println("[ErpPhieuxuatkho.initPdf] query = " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
		try 
		{
			while(rs.next())
			{
				this.ngayxuatkho = rs.getString("ngayxuat");
				this.trahangNccId = rs.getString("TRAHANGNCC_FK");
				
				if(rs.getInt("trahangncc_fk") != 0)
				{
					this.khId = rs.getString("nccId");
					
					//this.ddhId = " - Số trả hàng: " + rs.getString("preTH") + rs.getString("TRAHANGNCC_FK");
					this.diachinhan = rs.getString("diachiNcc");
					this.nguoinhanhang = rs.getString("nccTen");
				}

				
				this.ndxId = rs.getString("NOIDUNGXUAT");
				this.ghichu = rs.getString("GHICHU");
				this.trangthai = rs.getString("trangthai");
				this.sochungtu = rs.getString("SOCHUNGTU");
			}
			rs.close();
		} 
		catch (SQLException e) {}
		}
		
		this.initSanPhamPdf();
	}

	public void initLayHang() 
	{
		String query = "select a.PK_SEQ as pxkId, a.NGAYXUAT, a.DONDATHANG_FK, a.KHO_FK, a.GHICHU, a.NOIDUNGXUAT, " +
						"a.LYDOXUAT, b.MA as ndxId , a.TRANGTHAI " +
					"from ERP_XUATKHO a inner join ERP_NOIDUNGNHAP b on a.NOIDUNGXUAT = b.PK_SEQ where a.pk_seq = '"  + this.id + "'";
		
		System.out.println("Lay hang: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
		try 
		{
			while(rs.next())
			{
				this.ngayxuatkho = rs.getString("ngayxuat");
				this.khoId = rs.getString("kho_fk");
				this.ddhId = "130" + rs.getString("dondathang_fk");
				this.ndxId = rs.getString("ndxId");
				this.lydoxuat = rs.getString("LYDOXUAT");
				this.ghichu = rs.getString("GHICHU");
				this.trangthai = rs.getString("trangthai");
			}
			rs.close();
		} 
		catch (SQLException e) {}
		}
		
		this.initSanPhamLayHang();
		
		System.out.println("So san pham la: " + this.spList.size() + "\n");
	}
	
	private void initSanPham()
	{
		String query = "";

			query = "select a.TSCD_FK as spId, b.MA as spMa, b.DIENGIAI  as spTen,  a.donvi, a.soluong as totalLuong, xuat.totalXuat as tongXuat, a.SOLUONG, isnull(a.ghichu, '') as ghichu " +
					"from ERP_XUATTRATSCD_TSCD a " +
					"	inner join ERP_TAISANCODINH b on a.TSCD_FK = b.PK_SEQ " +
					"   left join "+
                    "( select a.tscd_FK, SUM(a.SOLUONG) as totalXuat  "+
                    " from ERP_XUATTRATSCD_TSCD a inner join ERP_XUATTRATSCD b on a.XUATTRATSCD_FK = b.PK_SEQ "+
                    " where b.TRANGTHAI in (0, 1) AND a.xuattratscd_fk != '"+ this.id +"' group by a.tscd_FK ) xuat on xuat.tscd_fk = a.tscd_fk " +
                    " where XUATTRATSCD_FK = '" + this.id + "'";

		
		System.out.println("___Query khoi tao sp: " + query);
		ResultSet rs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("#,###,###.###");
		if(rs != null)
		{
			List<ISanpham> spList = new ArrayList<ISanpham>();
			
			try 
			{
				ISanpham sanpham;
				String[] param = new String[6];
				
				while(rs.next())
				{
					param[0] = rs.getString("spId");
					param[1] = rs.getString("spMa");
					param[2] = rs.getString("spTen");
					param[3] = rs.getString("SOLUONG");
					param[4] = "";
					
					sanpham = new Sanpham(param);
					sanpham.setSoluongTotal(formater.format(rs.getDouble("totalLuong")));
					sanpham.setSoluongDaXuat(formater.format(rs.getDouble("tongXuat")));
					sanpham.setSoluong(formater.format(rs.getDouble("soluong")));
					System.out.println("___SO LUONG: " + sanpham.getSoluong());
					sanpham.setGhiChu(rs.getString("ghichu"));
					
					sanpham.setDVT(rs.getString("donvi"));
					
					spList.add(sanpham);
				}
			
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("___EXCEPTION KHOI TAO SP: " + e.getMessage());
			}
			
			this.spList = spList;
		}
	}

	private void initSanPhamPdf()
	{
		List<ISanpham> spList = new ArrayList<ISanpham>();
		String query = "";
		
		System.out.println("[ErpPhieuxuatkho.initSanPhamPdf] ddhId = " + this.ddhId);
		
	
			query = " select a.TSCD_FK as spId, b.MA as spMa, b.diengiai  as spTen,  a.donvi as DVT, a.soluong as totalLuong, a.soluong as tongXuat," +
					" a.SOLUONG, isnull(a.ghichu, '') as ghichu " +
					" from ERP_XUATTRATSCD_TSCD a " +
					" inner join ERP_TAISANCODINH b on a.TSCD_FK = b.PK_SEQ where XUATTRATSCD_FK = '" + this.id + "'";

		
		System.out.println("[ErpPhieuxuatkho.initSanPhamPdf] query khoi tao san pham = " + query);
		ResultSet rs = db.get(query);
		
		if(rs != null)
		{
			try 
			{				
				while(rs.next())
				{
					ISanpham sanpham = new Sanpham();
					
					double tongluong = 0; try { tongluong = rs.getFloat("soluong"); } catch (Exception e) { } 
					double soluongxuat = 0; try { soluongxuat = rs.getFloat("tongXuat"); } catch (Exception e) { }
					
					/*float quycach = rs.getFloat("quycach");
					float soluong2 = rs.getFloat("SOLUONG2");
					
					float thung = soluong * soluong2 / quycach;
					float le = (soluong * soluong2) % quycach;*/
					
					sanpham.setId(rs.getString("spId"));
					sanpham.setMa(rs.getString("spMa"));
					sanpham.setDiengiai(rs.getString("spTen"));
					sanpham.setDVT(rs.getString("DVT"));
					sanpham.setGhiChu(rs.getString("ghichu"));
					
					sanpham.setSoluong(Double.toString(soluongxuat));
					//sanpham.setQuycach(Float.toString(quycach));
					//sanpham.setThung(Float.toString(thung));
					//sanpham.setLe(Float.toString(le));
					//sanpham.setTrongluong( Double.toString(trongluongxuat) );
					//sanpham.setThetich( rs.getString("THETICH") == null ? "0" : rs.getString("THETICH") );
					
					spList.add(sanpham);
				}
			
				rs.close();
			} 
			catch (SQLException e) {
				System.out.println("[ErpPhieuxuatkho.initSanPhamPdf] Exception Message = " + e.getMessage());
			}
			
			this.spList = spList;
		} else {
			System.out.println("[ErpPhieuxuatkho.initSanPhamPdf] rs = null");
		}
	}

	private void initSanPhamLayHang()
	{
		String query = 
				" select a.SANPHAM_FK, b.MA as MA, b.TEN + ' ' + b.QUYCACH as Ten, a.SOLUONG, isnull(a.ghichu, '') as ghichu " +
				" from ERP_XUATKHO_SANPHAM a " +
				" inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +
				" where XUATKHO_FK = '" + this.id + "'";
		
		//System.out.println("Query khoi tao sp: " + query);
		ResultSet rs = db.get(query);
		
		if(rs != null)
		{
			List<ISanpham> spList = new ArrayList<ISanpham>();
			
			try 
			{
				ISanpham sanpham;
				String[] param = new String[6];
				
				while(rs.next())
				{
					param[0] = rs.getString("SANPHAM_FK");
					param[1] = rs.getString("MA");
					param[2] = rs.getString("TEN");
					param[3] = rs.getString("SOLUONG");
					param[4] = "";
					
					sanpham = new Sanpham(param);
					sanpham.setGhiChu(ghichu);
			
					query = " SELECT A.SOLO, A.SOLUONG, C.PK_SEQ AS KHUID, C.DIENGIAI AS KHUTEN, " +
								" B.PK_SEQ AS VTID, B.MA AS VITRI, DVDL.DONVI AS DONVIDOLUONG, " +
								" ISNULL(D.SOLUONG, '0') AS TONGLUONG, CONLAI =ISNULL(D.SOLUONG,0)-ISNULL(A.SOLUONG,0) " +
							" FROM ERP_XUATKHO_SP_CHITIET A LEFT JOIN ERP_VITRIKHO B ON A.BEAN = B.PK_SEQ " +
								" INNER JOIN ERP_KHUVUCKHO C ON B.KHU_FK = C.PK_SEQ " +
								" INNER join ERP_SANPHAM SP ON A.SANPHAM_FK = SP.PK_SEQ " +
								" INNER JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK = DVDL.PK_SEQ " +
								" LEFT JOIN ERP_KHOTT_SP_CHITIET D ON A.SANPHAM_FK = D.SANPHAM_FK  AND  A.BEAN = D.BIN AND D.KHOTT_FK = '" + this.khoId + "' AND A.SOLO = D.SOLO " +
							"WHERE A.XUATKHO_FK = '" + this.id + "' " + " AND A.SANPHAM_FK = '" + param[0] + "'  " +
							"ORDER BY B.MA ASC";
					
					System.out.println("1.San pham lay hang Detail: " + query);
					ResultSet rsSpDetail = db.get(query);
					
					List<ISpDetail> spConList = new ArrayList<ISpDetail>();
					ISpDetail spCon = null;
					if(rsSpDetail != null)
					{
						while(rsSpDetail.next())
						{
							String idhangmua = rsSpDetail.getString("donvidoluong"); //luu donvidoluong
							String solo = rsSpDetail.getString("solo");
							String slg = rsSpDetail.getString("soluong") + " - " + rsSpDetail.getString("tongluong") + " - " + rsSpDetail.getString("conlai");
							String khu = rsSpDetail.getString("khuTen");
							/*String vitri = rsSpDetail.getString("vitri");
							String vitriId = rsSpDetail.getString("vtId");*/
							String vitri = "NA";
							String vitriId = "100000";
							
							spCon = new SpDetail(idhangmua, solo, slg, khu, vitri, vitriId);
							spConList.add(spCon);
						}
						rsSpDetail.close();
					}
					
					sanpham.setSpDetailList(spConList);	
					spList.add(sanpham);
				}
			
				rs.close();
			} 
			catch (SQLException e) { System.out.println("Exception: " + e.getMessage() + "\n");}
			
			this.spList = spList;
		}
	}

	
	public void DbClose() 
	{
		try 
		{
			if(this.ddhRs != null)
				this.ddhRs.close();
			/*if(this.nccRs != null)
				this.nccRs.close();*/
			if(this.trahangNccRs != null)
				this.trahangNccRs.close();
			if(this.khoRs != null)
				this.khoRs.close();
			if(this.ndxRs != null)
				this.ndxRs.close();
			db.shutDown();
		} 
		catch (Exception e) {}
		
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

	public List<ISanpham> getSpList()
	{
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;
	}

	public String getLydoxuat() 
	{
		return this.lydoxuat;
	}

	public void setLydoxuat(String lydoxuat) 
	{
		this.lydoxuat = lydoxuat;
	}

	public String getGhichu() 
	{
		return this.ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}

	public ResultSet getDdhList() 
	{
		return this.ddhRs;
	}

	public void setDdhList(ResultSet ddhList)
	{
		this.ddhRs = ddhList;
	}

	public String getNguoinhanhang()
	{
		return this.nguoinhanhang;
	}

	public void setNguoinhanhang(String nguoinhanhang) 
	{
		this.nguoinhanhang = nguoinhanhang;
	}

	public String getDiachi()
	{
		return this.diachinhan;
	}

	public void setDiachi(String diachi)
	{
		this.diachinhan = diachi;
	}

	public String getXuattaikho() 
	{
		return this.xuattaikho;
	}

	public void setXuattaikho(String xuattaikho) 
	{
		this.xuattaikho = xuattaikho;
	}

	public String chotXuatKho(String userId) 
	{
		Utility util = new Utility();
		try 
		{
			String ngaysua = getDateTime();
			
			//Check thang ngay da khoa so chua
			String sql = "select a.ngaychot " +
						 "from erp_xuattratscd a  " +
						 "where  a.trangthai='0' and a.pk_seq = '" + this.id + "'";
			
			ResultSet rs = db.get(sql);
			
			
			if(rs.next())
			{
				this.ngaychotNV = rs.getString("ngaychot");
				rs.close();
			}else{
				this.msg = "0.Không xác định được ngày chốt phiếu xuất kho này";
				return this.msg;
			}
			
			String nam = this.ngaychotNV.substring(0, 4);
			String thang = this.ngaychotNV.substring(5, 7);
			
			
			
			db.getConnection().setAutoCommit(false);
			
				String query = "update erp_muahang set trangthai = '2' where pk_Seq = " + this.trahangNccId;
				if(!this.db.update(query))
				{
					this.msg = "2.Khong the cap nhat erp_muahang: " + query;
					db.getConnection().rollback();
					return this.msg;
				}
			
			
		   query =  " select a.TSCD_FK, a.SOLUONG, b.LOAITAISAN_FK, a.DonGia , b.ma, " +
		   		    " (select sohieutaikhoan from erp_taikhoankt where pk_seq= (select taikhoan_fk from erp_loaitaisan where pk_seq= b.loaitaisan_fk) ) as taikhoanCO_sohieu," +
		   		    " (select sohieutaikhoan from erp_taikhoankt where pk_seq = (select taikhoan_fk from erp_nhacungcap where pk_seq= mh.nhacungcap_fk)) as taikhoanNO_sohieu " +
					" from ERP_XUATTRATSCD_TSCD a inner join ERP_TAISANCODINH b on a.TSCD_FK = b.PK_SEQ  " +
					" inner join ERP_XUATTRATSCD xk on xk.pk_seq=a.xuattratscd_fk  " +
					" left join ERP_MUAHANG mh on mh.pk_seq= xk.trahangncc_fk and mh.type =1 " +
					" where a.XUATTRATSCD_FK = '" + this.id + "' ";
 
			System.out.println("___CHOT XUAT KHO KT: " + query);
			rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String loaitaisan = rs.getString("LOAITAISAN_FK");
					String mats = rs.getString("TSCD_FK");
					double soluong =  rs.getDouble("SOLUONG");
					double dongia =  rs.getDouble("DonGia");
				
					
					
						String taikhoanCO_sohieu = rs.getString("taikhoanCO_sohieu");
						String taikhoanNO_sohieu = rs.getString("taikhoanNO_sohieu");
								
						if(taikhoanCO_sohieu.trim().length() <= 0 || taikhoanNO_sohieu.trim().length() <= 0)
							{
								this.msg = "Loại tài sản và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								rs.close();
								db.getConnection().rollback();
								return this.msg;
							}
								
								//Nguyen te khi xuat kho chinh la VND
								String tiente_fk = "100000";
								double  dongiaViet = dongia;
								//UPDATE NO-CO NEW
								double thanhtien = dongia * soluong;
								msg = util.Update_TaiKhoan_TheoSoHieu(db, thang, nam, this.ngayxuatkho, ngaychotNV, "Xuất trả TSCĐ", this.id, taikhoanNO_sohieu, taikhoanCO_sohieu,
										this.ndxId, Double.toString(thanhtien), Double.toString(thanhtien), "Tài sản", mats, "", Double.toString(soluong), Double.toString(dongiaViet), tiente_fk, Double.toString(dongia), "1", dongiaViet + "*" + soluong, dongia + "*" + soluong, "");
								
								if(msg.trim().length() > 0)
								{
									rs.close();
								    this.msg="Không thể chốt phiếu này";
									return msg;
								}
							
								//Cập nhật nhật lại tscd
								query = "update ERP_TAISANCODINH set soluong = soluong - "+soluong+"  where pk_seq = '" + mats + "'";
								System.out.println("Lệnh UPDATE ERP_TAISANCODINH: " +query);
								if(!db.update(query))
								{
									this.msg = " Khong the cap nhat TAISANCODINH: " + query;
									db.getConnection().rollback();
									return this.msg;
								}	
										
					
				}
				rs.close();
			}
 
		
			query = "update ERP_XUATTRATSCD set trangthai = '1', giochot = '" + getTime() + "', ngaysua = '" + ngaysua + "', nguoisua = '" + userId + "' where pk_seq = '" + this.id + "'";
			System.out.println("Lệnh UPDATE: ERP_XUATTRATSCD " +query);
			if(!db.update(query))
			{
				this.msg = "8.Khong the cap nhat XUATTRATSCD: " + query;
				db.getConnection().rollback();
				return this.msg;
			}

			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return this.msg;
		} 
		catch (Exception e) 
		{ 
			e.printStackTrace();
			db.update("rollback");
			this.msg = "9.Exception : " + e.getMessage();
			return this.msg;
		}
	}


	public boolean isQuanlylo() 
	{
		return this.quanlylo;
	}

	public void setQuanlylo(boolean quanlylo)
	{
		this.quanlylo = quanlylo;
	}

	public boolean isQuanlybean() 
	{
		return this.quanlybean;
	}

	public void setQuanlybean(boolean quanlybean) 
	{
		this.quanlybean = quanlybean;
	}

	
	public String getNccId() 
	{
		return this.nccId;
	}

	
	public void setNccId(String nccid) 
	{
		this.nccId = nccid;	
	}

	public String getTrahangNccId() 
	{
		return this.trahangNccId;
	}
	
	public void setTrahangNccId(String trahangid)
	{
		this.trahangNccId = trahangid;
	}

	public ResultSet getTrahangList()
	{
		return this.trahangNccRs;
	}

	public void setTrahangList(ResultSet trahangList) 
	{
		this.trahangNccRs = trahangList;
	}
	
	public String getNgaychotNV() 
	{
		return this.ngaychotNV;
	}

	public void setNgaychotNV(String ngaychotNV) 
	{
		this.ngaychotNV = ngaychotNV;
	}

	
	public String getKhId() 
	{	
		return this.khId;
	}

	public void setKhId(String khid) 
	{
		this.khId = khid;
	}

	public String getKhachhangTen() 
	{
		return this.khTen;
	}

	public void setKhachhangTen(String khTen) 
	{
		this.khTen = khTen;
	}

	public String getCongtyId()
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId)
	{
		this.congtyId = congtyId;
	}

	public String getNccTen() 
	{
		return this.nccTen;
	}

	public void setNccTen(String nccTen) 
	{
		this.nccTen = nccTen;
	}

	
	public String getSoChungTu() {
		return this.sochungtu;
	}

	
	public void setSoChungTu(String sochungtu) {
		this.sochungtu = sochungtu;
	}

	
	public String getDungsai() {
		
		return this.dungsai;
	}

	
	public void setDungsai(String dungsai) {
		
		this.dungsai = dungsai;
	}


	
}
