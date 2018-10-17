package geso.traphaco.erp.beans.khoasothang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.khoasothang.*;

public class ErpCapnhatgianhap implements IErpCapnhatgianhap
{
	String userId;
	String id;
	String tungay;
	String denngay;
	String thang;
	String nam;
	ResultSet loaiSpRs;
	String loaiSpId;
	ResultSet pnkRs;
	String pnkIds;
	ResultSet SpRs;
	String spIds;
	String msg;
	String ghichu;
	
	List<ISanpham> spList;
	dbutils db;
	
	public ErpCapnhatgianhap()
	{
		this.id = ""; 
		this.tungay = getDauthang();
		this.denngay = getDateTime();
		this.thang = "";
		this.nam = "";
		this.loaiSpId = "";
		this.pnkIds = "";
		this.spIds = "";
		this.ghichu = "";
		this.msg = "";
		
		this.spList = new ArrayList<ISanpham>();
		
		this.db = new dbutils();
	}
	
	public ErpCapnhatgianhap(String id)
	{
		this.id = id;
		this.tungay = getDauthang();
		this.denngay = getDateTime();
		this.thang = "";
		this.nam = "";
		this.loaiSpId = "";
		this.pnkIds = "";
		this.spIds = "";
		this.ghichu = "";
		this.msg = "";
		
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

	public String getThang() 
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;
	}

	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam)
	{
		this.nam = nam;
	}

	public ResultSet getLoaiSanPhamRs()
	{
		return this.loaiSpRs;
	}

	public void setLoaiSanPhamRs(ResultSet loaisp) 
	{
		this.loaiSpRs = loaisp;
	}

	public String getLoaiSanPhamIds() 
	{
		return this.loaiSpId;
	}

	public void setLoaiSanPhamIds(String loaispIds) 
	{
		this.loaiSpId = loaispIds;
	}

	public ResultSet getPnkRs() 
	{
		return this.pnkRs;
	}

	public void setPnkRs(ResultSet pnkRs)
	{
		this.pnkRs = pnkRs;
	}

	public String getPnkIds() 
	{
		return this.pnkIds;
	}

	public void setPnkIds(String pnkIds) 
	{
		this.pnkIds = pnkIds;
	}

	public ResultSet getSanphamRs()
	{
		return this.SpRs;
	}

	public void setSanphamRs(ResultSet spRs) 
	{
		this.SpRs = spRs;
	}

	public String getSpIds() 
	{
		return this.spIds;
	}

	public void setSpIds(String spIds) 
	{
		this.spIds = spIds;
	}

	public void createRs() 
	{
		this.loaiSpRs = db.get("select pk_seq, ma + ', ' + ten as ten from erp_loaisanpham");
		
		String query = "";
		if(this.tungay.length() > 0 && this.denngay.length() > 0)
		{
			query = "select PK_SEQ as nkId, PREFIX + cast(PK_SEQ as varchar(20)) as sonhapkho, ngaynhapkho, ngaychot " +
					"from ERP_NHAPKHO where ngaychot >= '" + this.tungay + "' and ngaychot <= '" + this.denngay + "' and trangthai = '1' ";
			
			System.out.println("____1. Khoi tao nhap kho: " + query);
			this.pnkRs = db.get(query);
		}
		
		if(this.pnkIds.length() > 0)
		{
			query = "select a.PK_SEQ as nkId, a.PREFIX + cast(a.PK_SEQ as varchar(20)) as sonhapkho, a.ngaynhapkho, a.ngaychot, c.pk_seq as spId, c.ma as spMa, c.ten as spTen, " +
						"isnull(d.donvi, 'na') as donvidoluong, b.soluongNHAP, cast(b.dongiaViet as numeric(18, 3)) as dongiaNHap, " +
						"cast(b.soluongNHAP * b.dongiaViet as numeric(18, 0)) as thanhtienNhap " +
					"from ERP_NHAPKHO a inner join ERP_NHAPKHO_SANPHAM b on a.PK_SEQ = b.SONHAPKHO_FK inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
					"left join DONVIDOLUONG d on c.dvdl_fk = d.PK_SEQ " +
					"where '" + this.tungay + "' <= a.ngaychot and a.ngaychot <= '" + this.denngay + "' and a.trangthai = '1' ";
			
			if(this.loaiSpId.trim().length() > 0)
				query += " and c.loaisanpham_fk = '" + this.loaiSpId + "' ";
			
			System.out.println("___Khoi tao nhap kho: " + query);
			List<ISanpham> spList = new ArrayList<ISanpham>();
			ResultSet sanphamRs = db.get(query);
			if(sanphamRs != null)
			{
				try 
				{
					ISanpham sp = null;
					while(sanphamRs.next())
					{
						String nkId = sanphamRs.getString("nkId");
						String sonhapkho = sanphamRs.getString("sonhapkho");
						String ngaynhapkho = sanphamRs.getString("ngaynhapkho");
						String ngaychot = sanphamRs.getString("ngaychot");
						String spId = sanphamRs.getString("spId");
						String spMa = sanphamRs.getString("spMa");
						String spTen = sanphamRs.getString("spTen");
						String soluong = sanphamRs.getString("soluongNHAP");
						String dongia = sanphamRs.getString("dongiaNHap");
						
						sp = new Sanpham();
						sp.setPnkId(nkId);
						sp.setSonhapkho(sonhapkho);
						sp.setNgaynhap(ngaynhapkho);
						sp.setNgaychot(ngaychot);
						sp.setSpId(spId);
						sp.setSpMa(spMa);
						sp.setSpTen(spTen);
						sp.setSoluong(soluong);
						sp.setGiaOld(dongia);
						sp.setGiaNew(dongia);
						
						spList.add(sp);
					}
					sanphamRs.close();
				} 
				catch (SQLException e) 
				{
					System.out.println("Error: " + e.getMessage());
				}
			}
			this.spList = spList;
		}
		
	}

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay() 
	{
		return this.denngay;
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}
	
	private String getDauthang()
	{
		return this.getDateTime().substring(0, this.getDateTime().length() - 2) + "01";
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public void init() 
	{
		String query = "select ghichu, tungay, denngay from erp_capnhatgianhap where pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.denngay = rs.getString("denngay");
					this.tungay = rs.getString("tungay");
					this.ghichu = rs.getString("ghichu");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("___Exception: " + e.getMessage());
			}
		}
		
		//Khoi tao sanpham
		List<ISanpham> spList =  new ArrayList<ISanpham>();
		
		query = "select phieunhap_fk, gianhapcu, gianhapmoi, c.soluongnhap, b.pk_seq as spId, b.ma, b.ten, d.ngaynhapkho, d.PREFIX + cast(d.PK_SEQ as varchar(20)) as sonhapkho  " +
				"from erp_capnhatgianhap_phieunhap a inner join sanpham b on a.sanpham_fk = b.pk_seq " +
				"inner join erp_nhapkho_sanpham c on c.sonhapkho_fk = a.phieunhap_fk and a.sanpham_fk = c.sanpham_fk " +
				"inner join erp_nhapkho d on c.sonhapkho_fk = d.pk_seq " +
				"where capnhatgianhap_fk = '" + this.id + "'";
		System.out.println("__Khoi tao sanpham: " + query);
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				ISanpham sanpham = null;
				while(rs.next())
				{
					String pn_fk = rs.getString("phieunhap_fk");
					String spId = rs.getString("spId");
					String spMa = rs.getString("ma");
					String spTen = rs.getString("ten");
					String giaOld = rs.getString("gianhapcu");
					String giaNew = rs.getString("gianhapmoi");
					String soluong = rs.getString("soluongnhap");
					
					sanpham = new Sanpham(pn_fk, spId, spMa, spTen, soluong, giaOld, giaNew);
					
					sanpham.setPnkId(pn_fk);
					sanpham.setSonhapkho(rs.getString("sonhapkho"));
					sanpham.setNgaynhap(rs.getString("ngaynhapkho"));
					
					spList.add(sanpham);
				}
				rs.close();
			}
			catch (SQLException e)
			{
				System.out.println("___Exception 2: " + e.getMessage());
			}
		}
		this.spList = spList;
		
	}

	public List<ISanpham> getSanphamList() 
	{
		return this.spList;
	}

	public void setSanphamList(List<ISanpham> spList) 
	{
		this.spList = spList;
	}

	public boolean createCngn() 
	{
		if(tungay.trim().length() <= 0 || denngay.trim().length() <= 0)
		{
			this.msg = "Thời gian bạn chọn không hợp lý.";
			return false;
		}
		
		if(!tungay.substring(0, 4).equals(denngay.substring(0, 4)))
		{
			this.msg = "Bạn phải chọn thời gian trong cùng 1 tháng của 1 năm";
			return false;
		}
		
		if(!tungay.substring(5, 7).equals(denngay.substring(5, 7)))
		{
			this.msg = "Bạn phải chọn thời gian trong cùng 1 tháng của 1 năm";
			return false;
		}
		
		try
		{
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui long chon san pham cua phieu nhap muon cap nhat gia";
				return false;
			}
			
			//Check thay doi gia
			boolean flag = false;
			for(int i = 0; i < this.spList.size() - 1; i++)
			{
				if(!this.spList.get(i).getGiaOld().equals(this.spList.get(i).getGiaNew()))
				{
					flag = true;
				}
			}
			
			if(!flag)
			{
				this.msg = "Khong co san pham nao thay doi gia";
				return false;
			}
			
			
			//Check thang ngay da khoa so chua
			String nam = tungay.substring(0, 4);
			String thang = tungay.substring(5, 7);
			if(thang.startsWith("0"))
				thang = thang.replaceAll("0", "");
			
			String sql = "select count(pk_seq) as sodong from erp_khoasothang where nam = '" + nam + "' and thangks = '" + Integer.parseInt(tungay.substring(5, 7)) + "'";
			System.out.println("___Cau lenh check: " + sql);
			ResultSet rs  = db.get(sql);
			int count = 0;
			if(rs != null)
			{
				if(rs.next())
				{
					count = rs.getInt("sodong");
				}
				rs.close();
			}
			
			if(count > 0)
			{
				this.msg = "Tháng " + thang + ", năm " + nam + " đã khóa sổ tháng, bạn không thể cập nhật giá nhập";
				return false;
			}
			
			
			this.db.getConnection().setAutoCommit(false);
			
			String query = "insert erp_capnhatgianhap(ghichu, tungay, denngay, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
					"values(N'" + this.ghichu + "', '" + this.tungay + "', '" + this.denngay + "', '0', '" + this.getDateTime() + "'," +
							" '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "')";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the cap nhat gia nhap: " + query;
				return false;
			}
			
			String CngnCurrent = "";
			query = "select IDENT_CURRENT('erp_capnhatgianhap') as nkId";
			
			ResultSet rsDmh = db.get(query);						
			if(rsDmh.next())
			{
				CngnCurrent = rsDmh.getString("nkId");
				rsDmh.close();
			}
			
			for(int i = 0; i < this.spList.size(); i++)
			{
				ISanpham sp = this.spList.get(i);
				
				if(!sp.getGiaOld().trim().equals(sp.getGiaNew().trim()))
				{
					query = "insert erp_capnhatgianhap_phieunhap(capnhatgianhap_fk, phieunhap_fk, sanpham_fk, gianhapcu, gianhapmoi) " +
							"values('" + CngnCurrent + "', '" + sp.getPnkId() + "', '" + sp.getSpId() + "', '" + sp.getGiaOld() + "', '" + sp.getGiaNew() + "')";
					System.out.println("1.erp_capnhatgianhap_phieunhap: " + query);
					
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Khong the cap nhat gia nhap: " + query;
						return false;
					}
					
					query = "update erp_nhapkho_sanpham set thanhtienViet = soluongnhap * " + sp.getGiaNew() + ", dongiaViet = " + sp.getGiaNew() + " " +
							"where sonhapkho_fk = '" + sp.getPnkId() + "' and sanpham_fk = '" + sp.getSpId() + "'";
					System.out.println("2.erp_capnhatgianhap_phieunhap: " + query);
					
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Khong the cap nhat gia nhap: " + query;
						return false;
					}
					/*
					//Cap nhat Nhap_Xuat_Ton theo kho
					query = "update erp_nhap_xuat_ton set dongiaNhap = '" + sp.getGiaNew() + "', thanhtienNhap = soluongNhap * '" + sp.getGiaNew() + "' " +
							"where sanpham_fk = '" + sp.getSpId() + "' and soct = '" + sp.getPnkId() + "' and soluongNhap is not null";
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Khong the cap nhat erp_nhap_xuat_ton: " + query;
						return false;
					}
					
					//Nhap_Xuat_Ton_Tong
					query = "update erp_nhap_xuat_ton_tong set dongiaNhap = '" + sp.getGiaNew() + "', thanhtienNhap = soluongNhap * '" + sp.getGiaNew() + "' " +
							"where sanpham_fk = '" + sp.getSpId() + "' and soct = '" + sp.getPnkId() + "' and soluongNhap is not null";
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Khong the cap nhat erp_nhap_xuat_ton_tong: " + query;
						return false;
					}*/
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			IErpTinhgianhap tinhgianhap = new ErpTinhgianhap();
			this.msg = tinhgianhap.updateGiaNhap2(tungay, denngay);
			if(this.msg.trim().length() > 0)
			{
				db.getConnection().rollback();
				return false;
			}
			
		} 
		catch (Exception e) 
		{
			this.msg = "Khong the cap nhat gia nhap: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
		}
		
		return true;
	}
	

	public boolean updateCngn() 
	{
		//Check thay doi gia
		boolean flag = false;
		for(int i = 0; i < this.spList.size() - 1; i++)
		{
			for(int j = i; j < this.spList.size(); j++)
			{
				if(!this.spList.get(i).getGiaOld().equals(this.spList.get(j).getGiaNew()))
				{
					flag = true;
				}
			}
		}
		
		if(!flag)
		{
			this.msg = "Khong co san pham nao thay doi gia";
			return false;
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

	public String getGhichu() 
	{
		return this.ghichu;
	}

	public void setGhichu(String ghichu) 
	{
		this.ghichu = ghichu;
	}

	@Override
	public void DbClose() {
		// TODO Auto-generated method stub
		try{
			if (this.spList != null)
				this.spList.clear(); 
			if(loaiSpRs!=null){
				loaiSpRs.close();
			}
			
			if(pnkRs!=null){
				pnkRs.close();
			}
			
			if(SpRs!=null){
				SpRs.close();
			}
		}catch(Exception er){
			er.printStackTrace();
		}finally{
			this.db.shutDown();
		}
	}
}
