package geso.traphaco.erp.beans.thanhlytaisan.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.thanhlytaisan.IDonvi;
import geso.traphaco.erp.beans.thanhlytaisan.IErpThanhlytaisan;
import geso.traphaco.erp.beans.thanhlytaisan.ISanpham;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpThanhlytaisan implements IErpThanhlytaisan
{
	String congtyId;
	String userId;
	String ctyId;
	String cty;
	String id;
	// Them cot Nguon Goc Hang Hoa,TienTe_FK,mot don mua hang chi thuoc 1 loai
	// tien te
	String loai;
	String TienTe_FK;
	String GhiChu;
	String PhanTramThue;
	String TrungTamChiPhi_FK;
	float TyGiaQuyDoi;
	// Them cot Nguon Goc Hang Hoa,TienTe_FK
	String ngay;
	String khId;
	String khTen;
	String trangthai;
	String BVAT;
	String VAT;
	String AVAT;
	String sochungtu;
	List<ISanpham> spList;
	List<IDonvi> dvList;
	
	String nguoimuahang;
	String donvi;
	String diachi;
	String masothue;
	
	String msg;
	String quanlyCN;
	
	String ngayhoadon;
	String kyhieuhoadon;
	String sohieuhoadon;
	
	dbutils db;
	
//	private Utility util;

	public ErpThanhlytaisan()
	{
		this.userId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = "";
		this.ngay = "";
		this.khId = "";
		this.khTen = "";
		this.loai = "";
		this.trangthai = "";
		this.BVAT = "";
		this.VAT = "10";
		this.sochungtu = "";
		this.AVAT = "";
		this.msg = "";
		this.TienTe_FK = "";
		this.GhiChu = "";
		this.TyGiaQuyDoi = 1;
		this.spList = new ArrayList<ISanpham>();		
		this.dvList = new ArrayList<IDonvi>();
		this.quanlyCN = "1";
		this.db = new dbutils();
//		this.util=new Utility();
		this.nguoimuahang="";
		this.donvi="";
		this.masothue="";
		this.diachi="";
		this.ngayhoadon = "";
		this.kyhieuhoadon = "";
		this.sohieuhoadon = "";
	}

	public ErpThanhlytaisan(String id)
	{
		this.userId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = id;
		this.ngay = "";
		this.khId = "";
		this.khTen = "";
		this.loai = "";
		this.trangthai = "";
		this.BVAT = "";
		this.sochungtu = "";
		this.VAT = "10";
		this.AVAT = "";
		this.msg = "";
		this.TienTe_FK = "";
		this.GhiChu = "";
		this.TyGiaQuyDoi = 1;
		this.spList = new ArrayList<ISanpham>();
		this.dvList = new ArrayList<IDonvi>();		
		this.quanlyCN = "1";
		this.db = new dbutils();
//		this.util=new Utility();
		this.nguoimuahang="";
		this.donvi="";
		this.masothue="";
		this.diachi="";
		this.ngayhoadon = "";
		this.kyhieuhoadon = "";
		this.sohieuhoadon = "";
	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}

	public String getCty()
	{
		return this.cty;
	}

	public void setCty(String cty)
	{
		this.cty = cty;
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

	public String getNgay()
	{
		return this.ngay;
	}

	public void setNgay(String ngay)
	{
		this.ngay = ngay;
	}


	public String getKH()
	{
		return this.khId;
	}

	public void setKH(String khId)
	{
		this.khId = khId;
	}

	public String getTongtienchuaVat()
	{
		return this.BVAT;
	}

	public void setTongtienchuaVat(String ttchuavat)
	{
		this.BVAT = ttchuavat;
	}

	public String getVat()
	{
		if (this.VAT.length() == 0)
			this.VAT = "10";
		return this.VAT;
	}

	public void setVat(String vat)
	{
		this.VAT = vat;
	}

	public String getTongtiensauVat()
	{
		return this.AVAT;
	}

	public void setTongtiensauVat(String ttsauvat)
	{
		this.AVAT = ttsauvat;
	}

	public String getLoai()
	{
		return this.loai;
	}

	public void setLoai(String loai)
	{
		this.loai = loai;
	}

	public List<ISanpham> getSpList()
	{
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;
	}


	public void createRs()
	{
		
		ResultSet donvi = db.get("select PK_SEQ, DONVI from DONVIDOLUONG where TRANGTHAI = '1' ");
		this.dvList.clear();
		if (donvi != null)
		{
			try
			{
				while (donvi.next())
				{
					this.dvList.add(new Donvi(donvi.getString("pk_seq"), donvi.getString("donvi")));
				}
				donvi.close();
			}
			catch (SQLException e) { 
				e.printStackTrace();
			}
		}

//		this.createSanpham();
	}

	public void init()
	{
		NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
		
		String query = 	" select a.PK_SEQ as dtltsId, isnull(a.TienTe_FK, '100000') as TienTe_FK, \n" +
						" CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, a.NGAY, isnull(a.GhiChu,'') as GhiChu, \n" +
						" b.pk_seq as khId, b.ma + ', ' + b.TEN as khTen, \n" +
						" isnull(a.TONGTIENAVAT, '0') as TONGTIENAVAT, \n" +
						" isnull(a.VAT, '0') as VAT, isnull(a.TONGTIENBVAT, 0) as TONGTIENBVAT, \n" +
						" a.TRANGTHAI,a.NGUOIMUAHANG,a.DONVI,a.MASOTHUE,a.DIACHI, a.LOAI, isnull(a.ngayhoadon, '') as ngayhoadon, isnull(a.sohoadon, '') as sohoadon, \n" +
						
						" isnull(a.kyhieuhoadon, '') as kyhieuhoadon \n" +
						" from ERP_THANHLYTAISAN a \n" +
						" inner join NHAPHANPHOI b on a.KHACHHANG_FK = b.PK_SEQ \n" +
						" where a.pk_seq = '" + this.id + "' \n"  ;
		
		System.out.println("Thanh ly TS Init : \n" + query + "\n----------------------------------------------------------------");
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.id = rs.getString("dtltsId");
					this.ngay = rs.getString("ngay");
					this.khId = rs.getString("khId");
					this.khTen = rs.getString("khTen");
					this.BVAT = formatter.format(rs.getDouble("TONGTIENBVAT"));
					this.VAT = formatter.format(rs.getDouble("VAT"));
					this.AVAT = formatter.format(rs.getDouble("TONGTIENAVAT"));
					this.trangthai = rs.getString("trangthai");
					this.sochungtu = rs.getString("SOCHUNGTU");
					this.TienTe_FK = rs.getString("TienTe_FK");
					this.GhiChu = rs.getString("GhiChu");
					this.loai = rs.getString("loai");
					this.nguoimuahang = rs.getString("nguoimuahang");
					this.diachi = rs.getString("diachi");
					this.donvi = rs.getString("donvi");
					this.masothue = rs.getString("masothue");
					this.loai = rs.getString("loai");
					this.ngayhoadon = rs.getString("ngayhoadon");
					this.sohieuhoadon = rs.getString("sohoadon");
					this.kyhieuhoadon = rs.getString("kyhieuhoadon");
					
				}
				rs.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("__Exception init: " + e.getMessage());
			}
		}

		this.createRs();
		this.createSanpham();
	}

	private void createSanpham()
	{
		String query="";
		if(	this.loai.equals("1"))
		{
			query =      " select \n " +
							" isnull(tscd.pk_seq,0) as tscdid ,isnull(tscd.ma, '') as tscdMa, isnull(a.diengiai, tscd.diengiai) as tscdTen, \n" +
							" isnull(nts.ma, 'NA') as nstNh,  \n" +
							" isnull(a.donvi, '') as donvi, a.soluong, isnull(a.dongia, '0') as dongia, \n" +
							" isnull(a.thanhtien, '0') as thanhtien, isnull(a.phantramthue, '0') as phantramthue, a.ngaygiao,  a.CHIETKHAU \n" +
							" from erp_thanhlytaisan_taisan a \n" +
							" left join	erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq  \n" +
							" left join erp_nhomtaisan nts on tscd.NhomTaiSan_fk = nts.pk_seq   \n"  +
							" where a.thanhlytaisan_fk = '" + this.id + "'\n";
		}
		else
		{
			 query =      " select  \n" +
			" isnull(CCDC.pk_seq,0) as tscdid ,isnull(CCDC.ma, '') as tscdMa, isnull(a.diengiai, CCDC.diengiai) as tscdTen, \n" +
			" isnull(nts.ma, 'NA') as nstNh,  \n" +
			" isnull(a.donvi, '') as donvi, a.soluong, isnull(a.dongia, '0') as dongia, \n" +
			" isnull(a.thanhtien, '0') as thanhtien, isnull(a.phantramthue, '0') as phantramthue, a.ngaygiao,  a.CHIETKHAU \n" +
			" from erp_thanhlytaisan_taisan a \n" +
			" left join	ERP_CONGCUDUNGCU CCDC on a.ccdc_fk = CCDC.pk_seq  \n" +
			" left join ERP_NHOMCCDC nts on CCDC.NHOMCCDC_FK = nts.pk_seq   \n"  +
			" where a.thanhlytaisan_fk = '" + this.id + "'\n";
		}
		
		System.out.println(" San pham init: \n" + query);
		ResultSet spRs = db.get(query);
		List<ISanpham> spList = new ArrayList<ISanpham>();
		
		NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
		if (spRs != null)
		{
			try
			{
				ISanpham sp = null;
				while (spRs.next())
				{
					sp = new Sanpham();
					
					sp.setPK_SEQ(spRs.getString("tscdid"));
					sp.setMasanpham(spRs.getString("tscdMa"));
					sp.setTensanpham(spRs.getString("tscdTen"));
					sp.setNhomhang(spRs.getString("nstNh"));
					sp.setTenXHD(spRs.getString("tscdTen"));
					
					sp.setSoluong(formatter.format(spRs.getDouble("soluong")));
					sp.setSoluong_bk(formatter.format(spRs.getDouble("soluong")));
					
					sp.setDonvitinh(spRs.getString("donvi"));
					sp.setDongia(formatter.format(spRs.getDouble("dongia")));
					sp.setChietkhau(formatter.format(spRs.getDouble("chietkhau")));
					sp.setThanhtien(formatter.format(spRs.getDouble("thanhtien")));
					
					sp.setNgaynhan(spRs.getString("ngaygiao"));
					
					sp.setPhanTramThue(formatter.format(spRs.getDouble("phantramthue")));

					spList.add(sp);
				}
				spRs.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				System.out.println("Khong the tao san Pham" + e.getMessage());
			}
		}

		this.spList = spList;
	}

	
	public boolean createDtlts()
	{
		int soluong=0;
		// Kiem tra moi them vao
		String query = "";
		
		if(this.khId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn khách hàng.";
			return false;
		}
		
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Vui lòng chọn tài sản";
			return false;
		}
		else
		{
			for(int i = 0; i < this.spList.size(); i++)
			{
				
				ISanpham sp = this.spList.get(i);
				String query1="";
				if(sp.getPK_SEQ().length()>0)
				{
				
				if (loai.equals("2"))
				{
					query1= " select SOLUONG from erp_congcudungcu where pk_seq = "+sp.getPK_SEQ()+" ";
				
				System.out.println("lay so luong :" +query1);
				ResultSet rssl = this.db.get(query1);
				if(rssl!=null)
					try {
						while (rssl.next())
						{
							soluong = rssl.getInt("soluong");
						}
						rssl.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if ((Integer.parseInt(sp.getSoluong())>soluong)||Integer.parseInt(sp.getSoluong())<0)
					{
						this.msg = "Số lượng CCDC cần thanh lý phải nhỏ hơn số lượng CCDC  đang có và phải lớn hơn 0";
						return false;
					}
				}
				}
					
					
				
				System.out.println("Kho Nhan: " + sp.getKhonhan());
				
				if(sp.getNgaynhan().trim().length() <= 0)
				{
					this.msg = "Vui lòng nhập thông tin ngày nhận tài sản.";
					return false;
				}
				
			}
		}
		
		
		if(this.khTen.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập tên khách hàng";
			return false;
		}
		
		try
		{
			String ngaytao = getDateTime();
			db.getConnection().setAutoCommit(false);
		
			query = "INSERT INTO ERP_THANHLYTAISAN(NGAY, KHACHHANG_FK, TONGTIENAVAT, VAT, TONGTIENBVAT, TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, \n" +
					"TIENTE_FK, GHICHU, TYGIAQUYDOI, LOAI, CONGTY_FK ,DONVI, DIACHI, NGUOIMUAHANG, MASOTHUE)\n" +
					" Values('" + this.ngay + "', " + this.khId + ", " + this.AVAT + "," + this.VAT + ", " + this.BVAT + ", '0', '" + ngaytao + "', '" + ngaytao + "'," + this.userId + "," + this.userId + ",\n" +
							"'100000', N'" + this.GhiChu + "', '1', '" + this.loai + "', '" + this.congtyId + "',N'"+this.donvi+"', \n" +
									"N'"+this.diachi+"', N'"+this.nguoimuahang+"',N'"+this.masothue+"')\n";
			
			
			System.out.println("Insert into Erp_Thanhlytaisan \n" + query + "\n-----------------------------------------------------");
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi Thanh ly tai san: \n" + query;
				System.out.println("2.Exception tai day: " + query);
				db.getConnection().rollback();
				return false;
			}

			String dtltsCurrent = "";
			query = "select SCOPE_IDENTITY() as dtltsId";
			ResultSet rsDtlts = db.get(query);
			if (rsDtlts.next())
			{
				dtltsCurrent = rsDtlts.getString("dtltsId");
				this.id = dtltsCurrent;
				rsDtlts.close();
			}
			
			for (int i = 0; i < this.spList.size(); i++)
			{
				ISanpham sp = this.spList.get(i);

				String TaiSan_FK = "NULL";
				
				TaiSan_FK = sp.getPK_SEQ();
								
				if(TaiSan_FK.equals("NULL"))
				{
					this.msg = "Vui lòng kiểm tra lại mã tài sản trong danh sách sản phẩm bạn nhập.";
					this.db.getConnection().rollback();
					return false;
				}
				System.out.println("sp.getDongia():"+ sp.getDongia());
				System.out.println("this.TiGiaQuyDoi"+ this.TyGiaQuyDoi);
				long dongiaviet = Math.round((Double.parseDouble(sp.getDongia()) * this.TyGiaQuyDoi) );
				long chietkhauviet = 0;
				if (sp.getChietkhau().trim().length() > 0)
					Math.round((Double.parseDouble(sp.getChietkhau()) * this.TyGiaQuyDoi) );
				long thanhtienviet = Math.round( dongiaviet * Double.parseDouble(sp.getSoluong()) - chietkhauviet);
//				long thanhtienviet = Math.round( dongiaviet * Double.parseDouble(sp.getSoluong()));
				
//				String ptThue = "0";
//				if(sp.getPhanTramThue().trim().length() > 0)
//					ptThue = sp.getPhanTramThue().trim();
				
				if(loai.equals("1"))
				{
					
				query = " insert into ERP_THANHLYTAISAN_TAISAN(THANHLYTAISAN_FK, TAISAN_FK, DIENGIAI, DONVI, SOLUONG, DONGIA, CHIETKHAU, THANHTIEN, \n" +
						"DONGIAVIET, THANHTIENVIET, NGAYGIAO, TIENTE_FK) \n" +
						" values(" + dtltsCurrent + ",  " + TaiSan_FK + ", N'" + sp.getTensanpham() + "', '" + sp.getDonvitinh() + "', '" + sp.getSoluong() + "',\n" +
								"'" + sp.getDongia() + "', '" + sp.getChietkhau() + "', '" + sp.getThanhtien() + "' ,'" + dongiaviet + "', \n" +
								"'" + thanhtienviet + "', '" + sp.getNgaynhan() + "', '100000' )\n";
				}
				else
				{
					query = " insert into ERP_THANHLYTAISAN_TAISAN(THANHLYTAISAN_FK, CCDC_FK, DIENGIAI, DONVI, SOLUONG, DONGIA, CHIETKHAU, THANHTIEN, \n" +
					"DONGIAVIET, THANHTIENVIET, NGAYGIAO, TIENTE_FK) \n" +
					" values(" + dtltsCurrent + ",  " + TaiSan_FK + ", N'" + sp.getTensanpham() + "', '" + sp.getDonvitinh() + "', '" + sp.getSoluong() + "',\n" +
							"'" + sp.getDongia() + "', '" + sp.getChietkhau() + "', '" + sp.getThanhtien() + "' ,'" + dongiaviet + "', \n" +
							"'" + thanhtienviet + "', '" + sp.getNgaynhan() + "', '100000' )\n";
				}
//				query = " insert into ERP_THANHLYTAISAN_TAISAN(THANHLYTAISAN_FK, TAISAN_FK, DIENGIAI, DONVI, SOLUONG, DONGIA, THANHTIEN, " +
//				"DONGIAVIET, THANHTIENVIET, NGAYGIAO, TIENTE_FK) " +
//				" values(" + dtltsCurrent + ",  " + TaiSan_FK + ", N'" + sp.getTensanpham() + "', '" + sp.getDonvitinh() + "', '" + sp.getSoluong() + "'," +
//						"'" + sp.getDongia() + "', '" + sp.getThanhtien() + "' ,'" + dongiaviet + "', " +
//						"'" + thanhtienviet + "', '" + sp.getNgaynhan() + "', '100000' )";
		

				System.out.println("2.Insert Into ERP_THANHLYTAISAN_TAISAN :" + query);
				
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi THANHLYTAISAN_TAISAN: " + query;
					System.out.println("5.Exception tai day: " + query);
					db.getConnection().rollback();
					return false;
				}
				
//				 CẬP NHẬT LẠI SỐ LƯỢNG TS/CCDC SAU KHI LƯU OR CHỐT -> HỎI LẠI
//				int soluongconlai= soluong-Integer.parseInt(sp.getSoluong());
//				if(loai.equals("1"))
//				{
//				query = " UPDATE ERP_TAISANCODINH SET SOLUONG = " + soluongconlai +" where pk_Seq =" +sp.getSoluong()+"";	
//				}
//				else
//				{
//					query = " UPDATE ERP_CONGCUDUNGCU SET SOLUONG = " + soluongconlai +" where pk_Seq =" +sp.getSoluong()+"";
//				}
//				if (!db.update(query))
//				{
//					this.msg = "Không thể cập nhập số lượng TS/CCDC : " + query;
//					System.out.println("5.Exception tai day: " + query);
//					db.getConnection().rollback();
//					return false;
//				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}catch (Exception e)
		{
			this.db.update("rollback");
			this.msg = "1.Exception " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		
	}

	public boolean updateDtlts()
	{
		// Kiem tra moi them vao
		String query = "";
		
		if(this.khId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nhà cung cấp.";
			return false;
		}
		
			
		if(this.spList.size() <= 0)
		{
			this.msg = "Vui lòng chọn sản phẩm";
			return false;
		}
		else
		{
			for(int i = 0; i < this.spList.size(); i++)
			{
				ISanpham sp = this.spList.get(i);
				
				System.out.println("Kho Nhan: " + sp.getKhonhan());
				
				if(sp.getNgaynhan().trim().length() <= 0)
				{
					this.msg = "Vui lòng nhập thông tin ngày nhận trong danh sách sản phẩm.";
					return false;
				}
				
				
			}
		}
		
		
		if(this.khTen.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập tên nhà cung cấp mua hàng";
			return false;
		}
		
		try
		{
			String ngaysua = getDateTime();
			//String[] ncc = this.nccTen.split(" - ");
			db.getConnection().setAutoCommit(false);
			
//			String loaisanpham = "NULL";
			
			query = "SELECT TONGTIENBVAT FROM ERP_THANHLYTAISAN WHERE PK_SEQ = '" + this.id + "' ";
//			boolean approve = false;
//			
//			ResultSet rs = this.db.get(query);
//			
//			if(rs.next())
//			{
//				System.out.println("Tong tien BVAT: " + rs.getString("TONGTIENBVAT") + " -- BVAT: " + this.BVAT);
//				
//				if(Double.parseDouble(rs.getString("TONGTIENBVAT")) <= Double.parseDouble(this.BVAT))
//				{
//					approve = true;
//				}
//			}
//			rs.close();
//			
			
			query = "update erp_thanhlytaisan set ngay = '" + this.ngay + "', \n" +
			"loai = '" + this.loai + "',  \n" +
			"donvi = '" + this.donvi + "',  \n" +
			"nguoimuahang = '" + this.nguoimuahang + "',  \n" +
			"masothue = '" + this.masothue + "',  \n" +
			"diachi = '" + this.diachi + "',  \n" +
			"khachhang_fk = '" + this.khId + "',  \n" +
			"tongtienBVat = " + this.BVAT + ", \n" +
			"vat = " + this.VAT + ", tongtienAVat = " + this.AVAT + ", ngaysua = '" + ngaysua + "', nguoisua = '" + this.userId + "',\n" +
			"GhiChu= N'" + this.GhiChu + "' where pk_seq = '" + this.id + "' and trangthai=0\n";

			if (db.updateReturnInt(query)!=1)
			{
				this.msg = "Khong the cap nhat Mua hang: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_THANHLYTAISAN_TAISAN where thanhlytaisan_fk = '" + this.id + "'";
			System.out.println("Xoa du lieu: " + query);
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_THANHLYTAISAN_TAISAN: \n" + query;
				db.getConnection().rollback();
				return false;
			}
			
			for (int i = 0; i < this.spList.size(); i++)
			{
				ISanpham sp = this.spList.get(i);
				
				String TaiSan_FK = "NULL";
				
				TaiSan_FK = sp.getPK_SEQ();				
				
				if(TaiSan_FK.equals("NULL"))
				{
					this.msg = "Vui lòng kiểm tra lại mã tài sản trong danh sách tài sản bạn nhập.";
					this.db.getConnection().rollback();
					return false;
				}
				
				long dongiaviet = Math.round((Double.parseDouble(sp.getDongia()) * this.TyGiaQuyDoi) );
				long chietkhauviet = 0;
				if (sp.getChietkhau().trim().length() > 0)
					Math.round((Double.parseDouble(sp.getChietkhau()) * this.TyGiaQuyDoi) );
				long thanhtienviet = Math.round( dongiaviet * Double.parseDouble(sp.getSoluong()) - chietkhauviet);
				
//				String ptThue = "0";
//				if(sp.getPhanTramThue().trim().length() > 0)
//					ptThue = sp.getPhanTramThue().trim();
//				
				
				if(loai.equals("1"))
				{
				query = " insert into ERP_THANHLYTAISAN_TAISAN(THANHLYTAISAN_FK, TAISAN_FK, DIENGIAI, DONVI, SOLUONG, DONGIA, CHIETKHAU, THANHTIEN, " +
						"DONGIAVIET, THANHTIENVIET, NGAYGIAO, TIENTE_FK) " +
						" values(" + this.id + ",  " + TaiSan_FK + ", N'" + sp.getTensanpham() + "', '" + sp.getDonvitinh() + "', '" + sp.getSoluong() + "'," +
								"'" + sp.getDongia() + "', '" + sp.getChietkhau() + "', '" + sp.getThanhtien() + "' ,'" + dongiaviet + "', " +
								"'" + thanhtienviet + "', '" + sp.getNgaynhan() + "', '100000' )";
				}
				else
				{
					query = " insert into ERP_THANHLYTAISAN_TAISAN(THANHLYTAISAN_FK, CCDC_FK, DIENGIAI, DONVI, SOLUONG, DONGIA, CHIETKHAU, THANHTIEN, " +
					"DONGIAVIET, THANHTIENVIET, NGAYGIAO, TIENTE_FK) " +
					" values(" + this.id + ",  " + TaiSan_FK + ", N'" + sp.getTensanpham() + "', '" + sp.getDonvitinh() + "', '" + sp.getSoluong() + "'," +
							"'" + sp.getDongia() + "', '" + sp.getChietkhau() + "', '" + sp.getThanhtien() + "' ,'" + dongiaviet + "', " +
							"'" + thanhtienviet + "', '" + sp.getNgaynhan() + "', '100000' )";
				}
				
				System.out.println("2.Insert Into ERP_THANHLYTAISAN_TAISAN :" + query);
				
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi THANHLYTAISAN_TAISAN: " + query;
					
					System.out.println("5.Exception tai day: " + query);
					db.getConnection().rollback();
					return false;
				}				
							
							
			}// End Insert For tung dong

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.msg = "Khong the cap nhat don hang " + query;
			System.out.println("Exception: " + e.getMessage());
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

	public List<IDonvi> getDvList()
	{
		return this.dvList;
	}

	public void setDvList(List<IDonvi> dvList)
	{
		this.dvList = dvList;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void close()
	{
			if(spList!=null){
				spList.clear();
			}
			if(dvList!=null){
				dvList.clear();
			}
			this.db.shutDown();
		
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getSochungtu() 
	{
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu)
	{
		this.sochungtu = sochungtu;
	}


	public void setTienTe_FK(String tiente_fk)
	{
		this.TienTe_FK = tiente_fk;

	}

	public String getTienTe_FK()
	{

		return this.TienTe_FK;
	}

	public String getGhiChu()
	{

		return this.GhiChu;
	}

	public void setGhiChu(String ghichu)
	{

		this.GhiChu = ghichu;
	}


	public void setTyGiaQuyDoi(float tygiaquydoi)
	{
		this.TyGiaQuyDoi = tygiaquydoi;
	}

	public Float GetTyGiaQuyDoi()
	{

		return this.TyGiaQuyDoi;
	}


	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}


	public String getKhTen() {
	
		return this.khTen;
	}

	public void setKhTen(String khTen) {
		
		this.khTen = khTen;
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

	public boolean hoadonDtlts() 
	{
		String query = "";
		
		System.out.println("__Ngay HD: " + this.ngayhoadon + " ___ Ky hieu HD: " + this.kyhieuhoadon + " ___ So hieu HD: " + this.sohieuhoadon);
		if(this.ngayhoadon.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày hóa đơn.";
			return false;
		}
		
		if(this.kyhieuhoadon.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ký hiệu hóa đơn.";
			return false;
		}	
		
		if(this.sohieuhoadon.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số hóa đơn.";
			return false;
		}
		
		try
		{
			String ngaysua = getDateTime();
			db.getConnection().setAutoCommit(false);

			query = "update erp_thanhlytaisan set trangthai = 3, ngayhoadon = '" + this.ngayhoadon + "', kyhieuhoadon = '" + this.kyhieuhoadon + "', sohoadon = '" + this.sohieuhoadon + "', " +
					"tongtienBVat = " + this.BVAT + ", " +
					"vat = " + this.VAT + ", tongtienAVat = " + this.AVAT + ", ngaysua = '" + ngaysua + "', nguoisua = '" + this.userId + "'," +
					"GhiChu= N'" + this.GhiChu + "' where pk_seq = '" + this.id + "'";

			if (!db.update(query))
			{
				this.msg = "Không thể cập nhật số, ngày hóa đơn: " + query;
				db.getConnection().rollback();
				return false;
			}
			
/*			String nam = this.ngayhoadon.substring(0, 4);
			String thang = this.ngayhoadon.substring(5, 7);
			
			//GHI NHAN TAI KHOAN NO CO
			if(Double.parseDouble(this.BVAT) > 0)
			{
				String taikhoanktCo = "";
				String taikhoanktNo = "";
				
				query = "select a.pk_seq as TAIKHOANKTNO, b.pk_seq as TAIKHOANKTCO  " +
						"from erp_khachhang a, erp_taikhoankt b  " +
						"where a.pk_seq = '" + this.khId + "' and b.sohieutaikhoan = '711800'";

				
				System.out.println("5.Query lay tai khoan: " + query);
				
				ResultSet tkRs = db.get(query);
				if(tkRs != null)
				{
					if(tkRs.next())
					{
						taikhoanktCo = tkRs.getString("TAIKHOANKTCO");
						taikhoanktNo = tkRs.getString("TAIKHOANKTNO");
						tkRs.close();
					}
					
					if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
					{
						this.msg = "Khách hàng và tài khoản không hợp lê. Vui lòng kiểm tra lại dữ liệu nền.";
						db.getConnection().rollback();
						return false;
					}
					
					//Nguyen te khi xuat kho chinh la VND
					String tiente_fk = "100000";
					
					//Kiem tra xem da co tai khoan nay trong thang chua
					query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
							"where taikhoankt_fk = '" + taikhoanktNo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
					ResultSet rsTKNo = db.get(query);
					int sodong = 0;
					if(rsTKNo.next())
					{
						sodong = rsTKNo.getInt("sodong");
					}
					rsTKNo.close();
					
					
					if(sodong > 0) //daco
					{
						query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + this.BVAT + ", " +
															" GIATRINGUYENTE = GIATRINGUYENTE + "  + this.BVAT + 
								" where taikhoankt_fk = '" + taikhoanktNo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					}
					else
					{
						query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
								"values('" + taikhoanktNo + "', '0', " + this.BVAT + ", '" + tiente_fk + "', " + this.BVAT + ", '" + thang + "', '" + nam + "')";
					}
					
					//System.out.println("5.Cap nhat: + query");
					if(!db.update(query))
					{
						db.getConnection().rollback();
						
						System.out.println("1.Loi: " + query);
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
						query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + this.AVAT + ", " +
														" GIATRINGUYENTE = GIATRINGUYENTE + "  + this.AVAT + 
								" where taikhoankt_fk = '" + taikhoanktCo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					}
					else
					{
						query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
								"select '" + taikhoanktCo + "', " + this.AVAT + ", '0', '" + tiente_fk + "', " + this.AVAT + ", '" + thang + "', '" + nam + "' ";
					}
					
							
					if(!db.update(query))
					{
						db.getConnection().rollback();
						
						System.out.println("2.Loi: " + query);
						this.msg = "7.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
						return false;
					}
				
				}
			}
			

			//GHI NHAN KHAU HAO
			query = "select a.taisan_fk, c.taikhoan_fk as taikhoanKTCo, " +
					"	isnull( ( select GiaTriConLaiThucTe  " +
					"			  from erp_taisancodinh_chitiet  " +
					"				where taisan_fk = c.pk_seq and thang = ( select max(thangthu) from ERP_KHAUHAOTAISAN where taisan_fk = c.pk_seq ) ) , 0 ) as GiaTriConLaiThucTe, " +
					" ( select pk_seq from ERP_TaiKhoanKT where sohieutaikhoan = '811000' ) as taikhoanKTNo_GiaTriConLai		 " +
					"from erp_thanhlytaisan_taisan a inner join erp_taisancodinh b on a.taisan_fk = b.pk_seq  " +
					"inner join erp_loaitaisan c on b.loaitaisan_fk = c.pk_seq  " +
					"where thanhlytaisan_fk = '" + this.id + "'";
			
			ResultSet rsTaisan = db.get(query);
			while(rsTaisan.next())
			{				
				String taikhoanktCo = rsTaisan.getString("taikhoanKTCo");
				String taikhoanktNo_GiaTriConLai = rsTaisan.getString("taikhoanKTCo");
				
				if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo_GiaTriConLai.trim().length() <= 0 )
				{
					db.getConnection().rollback();
					this.msg = "Không thể lấy thông tin tài khoản. Vui lòng cập nhật lại";
					return false;
				}
				
				double totalTTKhauHao = 0;
				double totalConLai = rsTaisan.getDouble("GiaTriConLaiThucTe");
				
				String tiente_fk = "100000";
				
				if(totalConLai > 0)
				{
					//Kiem tra xem da co tai khoan nay trong thang chua
					query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
							"where taikhoankt_fk = '" + taikhoanktNo_GiaTriConLai + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
					ResultSet rsTKNo = db.get(query);
					int sodong = 0;
					if(rsTKNo.next())
					{
						sodong = rsTKNo.getInt("sodong");
					}
					rsTKNo.close();
					
					
					if(sodong > 0) //daco
					{
						query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + totalConLai + ", " +
															" GIATRINGUYENTE = GIATRINGUYENTE + "  + totalConLai + 
								" where taikhoankt_fk = '" + taikhoanktNo_GiaTriConLai + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					}
					else
					{
						query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
								"values('" + taikhoanktNo_GiaTriConLai + "', '0', " + totalConLai + ", '" + tiente_fk + "', " + totalConLai + ", '" + thang + "', '" + nam + "')";
					}
					
					//System.out.println("5.Cap nhat: + query");
					if(!db.update(query))
					{
						db.getConnection().rollback();
						
						System.out.println("1.Loi: " + query);
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
						query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + totalConLai + ", " +
														" GIATRINGUYENTE = GIATRINGUYENTE + "  + totalConLai + 
								" where taikhoankt_fk = '" + taikhoanktCo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					}
					else
					{
						query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
								"select '" + taikhoanktCo + "', " + totalConLai + ", '0', '" + tiente_fk + "', " + totalConLai + ", '" + thang + "', '" + nam + "' ";
					}
					
							
					if(!db.update(query))
					{
						db.getConnection().rollback();
						
						System.out.println("2.Loi: " + query);
						this.msg = "7.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
						return false;
					}
				}
				
				
				if(totalTTKhauHao > 0)  //HOI LAI BAC PHUONG TAI KHOAN NO TRONG FILE EXCEL
				{
					//Kiem tra xem da co tai khoan nay trong thang chua
					query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
							"where taikhoankt_fk = '" + taikhoanktNo_GiaTriConLai + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
					ResultSet rsTKNo = db.get(query);
					int sodong = 0;
					if(rsTKNo.next())
					{
						sodong = rsTKNo.getInt("sodong");
					}
					rsTKNo.close();
					
					
					if(sodong > 0) //daco
					{
						query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + totalTTKhauHao + ", " +
															" GIATRINGUYENTE = GIATRINGUYENTE + "  + totalTTKhauHao + 
								" where taikhoankt_fk = '" + taikhoanktNo_GiaTriConLai + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					}
					else
					{
						query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
								"values('" + taikhoanktNo_GiaTriConLai + "', '0', " + totalTTKhauHao + ", '" + tiente_fk + "', " + totalTTKhauHao + ", '" + thang + "', '" + nam + "')";
					}
					
					//System.out.println("5.Cap nhat: + query");
					/*if(!db.update(query))
					{
						db.getConnection().rollback();
						
						System.out.println("1.Loi: " + query);
						this.msg = "6.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
						return false;
					}*/
					
					
					//Tai khoan co
/*					query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO  " +
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
						query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + totalTTKhauHao + ", " +
														" GIATRINGUYENTE = GIATRINGUYENTE + "  + totalTTKhauHao + 
								" where taikhoankt_fk = '" + taikhoanktCo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					}
					else
					{
						query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
								"select '" + taikhoanktCo + "', " + totalTTKhauHao + ", '0', '" + tiente_fk + "', " + totalTTKhauHao + ", '" + thang + "', '" + nam + "' ";
					}
					
							
					if(!db.update(query))
					{
						db.getConnection().rollback();
						
						System.out.println("2.Loi: " + query);
						this.msg = "7.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
						return false;
					}
				}
				
			}
			rsTaisan.close();*/
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.msg = "Khong the cap nhat thanh ly tai san";
			db.update("rollback");
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
	}

	public String getNguoiMuaHang() {
		
		return this.nguoimuahang;
	}

	
	public void setNguoiMuaHang(String nguoimuahang) {
		
		this.nguoimuahang = nguoimuahang;
	}

	
	public String getDonVi() {
		
		return this.donvi;
	}

	
	public void setDonVi(String donvi) {
		this.donvi = donvi;
	}

	
	public String getDiaChi() {
		return this.diachi;
	}

	
	public void setDiaChi(String diachi) {
		
		this.diachi = diachi;
	}

	
	public String getMST() {
		
		return this.masothue;
	}

	
	public void setMST(String masothue) {
		
		this.masothue = masothue;
	}



}
