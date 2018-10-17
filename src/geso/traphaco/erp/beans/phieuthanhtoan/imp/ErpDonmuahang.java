package geso.traphaco.erp.beans.phieuthanhtoan.imp;

import geso.traphaco.center.util.*
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.phieuthanhtoan.IDonvi;
import geso.traphaco.erp.beans.phieuthanhtoan.IErpDonmuahang;
import geso.traphaco.erp.beans.phieuthanhtoan.IKho;
import geso.traphaco.erp.beans.phieuthanhtoan.ISanpham;
import geso.traphaco.erp.beans.phieuthanhtoan.ITiente;
import geso.traphaco.erp.beans.phieuthanhtoan.ITrungTamChiPhi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpDonmuahang implements IErpDonmuahang
{
	String congtyId;
	String userId;
	String ctyId;
	String cty;
	String id;
	// Them cot Nguon Goc Hang Hoa,TienTe_FK,mot don mua hang chi thuoc 1 loai
	// tien te
	String NguonGocHH;
	String MaLoaiHH;
	String TienTe_FK;
	String GhiChu;
	String ThueNhapKhau;
	String PhanTramThue;
	String TrungTamChiPhi_FK;
	float TyGiaQuyDoi;
	// Them cot Nguon Goc Hang Hoa,TienTe_FK
	String ngaymuahang;
	String dvthId;
	ResultSet dvthRs;
	String nccTen;
	String trangthai;
	String BVAT;
	String VAT;
	String AVAT;
	String lhhId;
	String sochungtu;
	String[] duyetIds;
	ResultSet lhhRs;
	List<ISanpham> spList;
	List<IDonvi> dvList;
	List<ITiente> tienteList;
	List<IKho> khoList;
	List<ITrungTamChiPhi> ListTTCP;
	
	
	String msg;
	String dungsai;
	String lspId;
	String isdontrahang;
	String maketoStock;

	dbutils db;
	
	private Utility util;

	public ErpDonmuahang()
	{
		this.userId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = "";
		this.ngaymuahang = "";
		this.dvthId = "";
		this.nccTen = "";
		this.trangthai = "";
		this.BVAT = "";
		this.VAT = "10";
		this.sochungtu = "";
		this.AVAT = "";
		this.lhhId = "0";
		this.msg = "";
		this.dungsai = "0";
		this.NguonGocHH = "";
		this.MaLoaiHH = "";
		this.TienTe_FK = "";
		this.GhiChu = "";
		this.TyGiaQuyDoi = 1;
		this.spList = new ArrayList<ISanpham>();
		this.dvList = new ArrayList<IDonvi>();
		this.tienteList = new ArrayList<ITiente>();
		this.khoList = new ArrayList<IKho>();
		this.ListTTCP = new ArrayList<ITrungTamChiPhi>();
		
		this.lspId = "";
		this.isdontrahang = "0";
		this.maketoStock = "0";
		this.db = new dbutils();
		this.util=new Utility();
	}

	public ErpDonmuahang(String id)
	{
		this.userId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = id;
		this.ngaymuahang = "";
		this.dvthId = "";
		this.nccTen = "";
		this.trangthai = "";
		this.BVAT = "";
		this.sochungtu = "";
		this.VAT = "10";
		this.AVAT = "";
		this.lhhId = "0";
		this.msg = "";
		this.dungsai = "0";
		this.MaLoaiHH = "";
		this.NguonGocHH = "";
		this.TienTe_FK = "";
		this.GhiChu = "";
		this.TyGiaQuyDoi = 1;
		this.spList = new ArrayList<ISanpham>();
		this.dvList = new ArrayList<IDonvi>();
		this.tienteList = new ArrayList<ITiente>();
		this.khoList = new ArrayList<IKho>();
		this.ListTTCP = new ArrayList<ITrungTamChiPhi>();
		
		this.lspId = "";
		this.isdontrahang = "0";
		this.maketoStock = "0";
		this.db = new dbutils();
		this.util=new Utility();
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

	public String getNgaymuahang()
	{
		return this.ngaymuahang;
	}

	public void setNgaymuahang(String ngaymuahang)
	{
		this.ngaymuahang = ngaymuahang;
	}

	public String getDvthId()
	{
		return this.dvthId;
	}

	public void setDvthId(String dvthid)
	{
		this.dvthId = dvthid;
	}

	public ResultSet getDvthList()
	{
		return this.dvthRs;
	}

	public void setDvthList(ResultSet dvthlist)
	{
		this.dvthRs = dvthlist;
	}

	public String getNCC()
	{
		return this.nccTen;
	}

	public void setNCC(String ncc)
	{
		this.nccTen = ncc;
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

	public String getLoaispId()
	{
		return this.lspId;
	}

	public void setLoaispId(String loaispid)
	{
		this.lspId = loaispid;
	}

	public ResultSet getLoaiList()
	{
		return this.lhhRs;
	}

	public void setLoaiList(ResultSet loaihhlist)
	{
		this.lhhRs = loaihhlist;
	}

	public List<ISanpham> getSpList()
	{
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;
	}

	public String[] getDuyetIds()
	{
		return this.duyetIds ;
	}

	public void setDuyetIds(String[] duyetIds)
	{
		this.duyetIds = duyetIds;
	}

	public void createRs()
	{
		String sql=" select pk_seq, ten from ERP_DONVITHUCHIEN " +
				   " where trangthai = '1' and congty_fk = '" + this.congtyId + "' and pk_seq in "+util.quyen_donvithuchien(this.userId);
		System.out.println("Get Dvkd : "+sql);
		this.dvthRs = db.get(sql);
		
		this.lhhRs = db.get("Select pk_seq, ten, ma From Erp_LoaiSanPham ");
		
		ResultSet donvi = db.get("select PK_SEQ, DONVI from DONVIDOLUONG");
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
			catch (SQLException e) { }
		}

		ResultSet khoTT = db.get("select PK_SEQ, TEN from ERP_KHOTT where congty_fk = '" + this.congtyId + "' ");
		this.khoList.clear();
		if (khoTT != null)
		{
			try
			{
				while (khoTT.next())
				{
					this.khoList.add(new Kho(khoTT.getString("pk_seq"), khoTT.getString("ten")));
				}
				khoTT.close();
			}
			catch (SQLException e)
			{
			}
		}

		ResultSet tiente = db.get("select ERP_TIENTE.PK_SEQ, ERP_TIENTE.MA, ERP_TIGIA.TIGIAQUYDOI from ERP_TIENTE inner join ERP_TIGIA on ERP_TIENTE.PK_SEQ = ERP_TIGIA.TIENTE_FK order by ERP_TIENTE.PK_SEQ ASC");
		this.tienteList.clear();
		if (tiente != null)
		{
			try
			{
				while (tiente.next())
				{
					this.tienteList.add(new Tiente(tiente.getString("pk_seq"), tiente.getString("ma"), tiente.getString("TIGIAQUYDOI")));
				}
				tiente.close();
			}
			catch (SQLException e)
			{
			}
		}
	}

	public void init()
	{
		NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
		
		String query = " select a.PK_SEQ as dmhId, isnull(a.NguonGocHH ,'') as NguonGocHH, isnull(a.TienTe_FK, '100000') as TienTe_FK, " +
				" c.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, a.NGAYMUA, isnull(a.GhiChu,'') as GhiChu, " +
				" a.DONVITHUCHIEN_FK as dvthId, a.LOAIHANGHOA_FK, a.LOAISANPHAM_FK, cast(b.pk_seq as nvarchar(10)) + ' - ' + b.ma + ', ' + b.TEN as nccTen, " +
				" isnull(a.TONGTIENAVAT, '0') as TONGTIENAVAT, isnull(a.VAT, '0') as VAT, isnull(a.TONGTIENBVAT, 0) as TONGTIENBVAT, isnull(a.Dungsai, '0') as dungsai, a.TRANGTHAI " +
				"  from ERP_MUAHANG a inner join ERP_NHACUNGCAP b on a.NHACUNGCAP_FK = b.PK_SEQ " +
				" inner join ERP_DONVITHUCHIEN c on c.PK_SEQ = a.DONVITHUCHIEN_FK  " +
				" where a.pk_seq = '" + this.id + "'  and c.pk_seq  in "+util.quyen_donvithuchien(this.userId) ;
		
		System.out.println("Don Tra Hang : " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.id = rs.getString("dmhId");
					this.ngaymuahang = rs.getString("ngaymua");
					this.dvthId = rs.getString("dvthId");
					this.nccTen = rs.getString("nccTen");
					this.lhhId = rs.getString("LOAIHANGHOA_FK");
					this.lspId = rs.getString("LOAISANPHAM_FK");
					this.BVAT = formatter.format(rs.getDouble("TONGTIENBVAT"));
					this.VAT = formatter.format(rs.getDouble("VAT"));
					this.AVAT = formatter.format(rs.getDouble("TONGTIENAVAT"));
					this.trangthai = rs.getString("trangthai");
					this.dungsai = rs.getString("dungsai");
					this.sochungtu = rs.getString("SOCHUNGTU");
					this.NguonGocHH = rs.getString("NguonGocHH");
					this.TienTe_FK = rs.getString("TienTe_FK");
					this.GhiChu = rs.getString("GhiChu");
				}
				rs.close();
			}
			catch (Exception e)
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}

		this.createRs();
		this.createSanpham();
	}

	private void createSanpham()
	{
		String query =      " select  isnull(b.pk_seq,0) as spid, isnull(b.ma, '') as spMa,isnull(b.quycach,'NA') as quycach , isnull(a.diengiai, b.ten ) as spTen, isnull(b.ten1, b.ten) as spTen2, 'NA' as spNh, " +
							" isnull(tscd.pk_seq,0) as tscdid ,isnull(tscd.ma, '') as tscdMa, isnull(a.diengiai, tscd.ten) as tscdTen, isnull(nts.ma, 'NA') as nstNh,  " +
							" isnull(ncp.pk_seq,0) as ncpid,isnull(ncp.ten, '') as ncpMa, isnull(a.diengiai, ncp.diengiai) as ncpTen, isnull(ttcp.diengiai, 'NA') as ncpNh, " +
							" isnull(a.donvi, '') as donvi, a.soluong, isnull(a.dongia, '0') as dongia, " +
							" isnull(a.thanhtien, '0') as thanhtien, isnull(a.phantramthue, '0') as phantramthue, isnull(a.thuenhapkhau, '0') as thuenhapkhau, ngaynhan, a.khott_fk, dungsai,  " +
							" isnull(muanguyenlieudukien_fk, 0) as mnlId " +
							" from erp_muahang_sp a left join ERP_SANPHAM b on a.sanpham_fk = b.pk_seq   " +
							" left join	erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq  " +
							" left join erp_nhomtaisan nts on tscd.NhomTaiSan_fk = nts.pk_seq   " +
							" left join erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq " +
							" left join  erp_trungtamchiphi ttcp on ncp.ttchiphi_fk = ttcp.pk_seq  " +
							" where muahang_fk = '" + this.id + "'";
		
		System.out.println(" San pham init: " + query);
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
					
					if(this.lhhId.equals("0"))
					{
						sp.setPK_SEQ(spRs.getString("spid"));
						sp.setMasanpham(spRs.getString("spMa"));
						sp.setTensanpham(spRs.getString("spTen"));
						sp.setNhomhang(spRs.getString("spNh"));
						sp.setTenXHD(spRs.getString("spTen2"));
						sp.setMNLId(spRs.getString("mnlId"));
					}
					else
					{
						if(this.lhhId.equals("1"))
						{
							sp.setPK_SEQ(spRs.getString("tscdid"));
							sp.setMasanpham(spRs.getString("tscdMa"));
							sp.setTensanpham(spRs.getString("tscdTen"));
							sp.setNhomhang(spRs.getString("nstNh"));
							sp.setTenXHD(spRs.getString("tscdTen"));
						}
						else
						{
							sp.setPK_SEQ(spRs.getString("ncpid"));
							sp.setMasanpham(spRs.getString("ncpMa"));
							sp.setTensanpham(spRs.getString("ncpTen"));
							sp.setNhomhang(spRs.getString("ncpNh"));
							sp.setTenXHD(spRs.getString("ncpTen"));
						}
					}
					
					sp.setSoluong( formatter.format(spRs.getDouble("soluong")));
					sp.setDonvitinh(spRs.getString("donvi"));
					sp.setDongia(formatter.format(spRs.getDouble("dongia")));
					sp.setThanhtien(formatter.format(spRs.getDouble("thanhtien")));
					
					sp.setNgaynhan(spRs.getString("ngaynhan"));
					
					if(spRs.getString("khott_fk") != null)
						sp.setKhonhan(spRs.getString("khott_fk"));
					sp.setThueNhapKhau(formatter.format(spRs.getDouble("thuenhapkhau")));
					sp.setPhanTramThue(formatter.format(spRs.getDouble("phantramthue")));

					spList.add(sp);
				}
				spRs.close();
			}
			catch (SQLException e)
			{
				System.out.println("Khong the tao san Pham" + e.getMessage());
			}
		}

		this.spList = spList;
	}

	public void CreatePOfromPR(ResultSet rs, String mnlId){
		try{
			if(rs.next()){
				this.db.getConnection().setAutoCommit(false);
			
				String query = 	"Insert into Erp_MuaHang(NgayMua, DonViThucHien_FK, NhaCungCap_FK, " +
								"LoaiHangHoa_FK, LoaiSanPham_FK, TongTienAVAT, VAT, TongTienBVAT, DungSai, TrangThai, NgayTao, " +
								"NgaySua, NguoiTao, NguoiSua, congty_fk) " +
								"Values('" + this.getDateTime() + "','100003', null, '0', '100002', '0', '0' , '0' , '0', '0', '" + this.getDateTime() + "', '" + this.getDateTime() + "'," +
								"" + this.userId + "," + this.userId + ", '" + this.congtyId + "')";

				System.out.println("Insert into Erp_MuaHang " + query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi Mua hang: " + query;
					System.out.println("2.Exception tai day: " + query);
					this.db.getConnection().rollback();		
				}

				query = "select SCOPE_IDENTITY() as dmhId";
				ResultSet rsDmh = db.get(query);
				if (rsDmh.next())
				{
					this.id = rsDmh.getString("dmhId");
					rsDmh.close();
				}
			
				query = " insert into ERP_MUAHANG_SP(muahang_fk, sanpham_fk, diengiai, donvi, soluong, muanguyenlieudukien_fk) " +
						" values(" + this.id + ", " + rs.getString("SANPHAM_FK") + ", N'" + rs.getString("TEN") + "', N'" + rs.getString("DONVI") + "', " +
						"'" + rs.getString("SOLUONG") + "', '" + mnlId + "')";

				this.db.update(query);
				
//				query = "update ERP_MUANGUYENLIEUDUKIEN SET DADATHANG = DADATHANG + " + rs.getString("SOLUONG") + " WHERE PK_SEQ = " + mnlId + " ";
				
				query = "UPDATE ERP_MUANGUYENLIEUDUKIEN  " +
						"SET ERP_MUANGUYENLIEUDUKIEN.DADATHANG = ISNULL(A.SOLUONG, 0) " +
						"FROM " + 
						"( " +
						"	SELECT SUM(SOLUONG) AS SOLUONG, MUANGUYENLIEUDUKIEN_FK " +
						"	FROM ERP_MUAHANG_SP WHERE MUANGUYENLIEUDUKIEN_FK = " + mnlId + " " +
						"	GROUP BY MUANGUYENLIEUDUKIEN_FK " +
						")A  WHERE ERP_MUANGUYENLIEUDUKIEN.PK_SEQ = " + mnlId + " " ;
						
				System.out.println(query);
				
				this.db.update(query);
							
				this.init();
			
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
			}
		}catch(java.sql.SQLException e){}
	}
	
	public boolean createDmh()
	{
		// Kiem tra moi them vao
		String query = "";
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Vui lòng chọn sản phẩm";
			return false;
		}
		
		if(this.TienTe_FK.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn tiền tệ của đơn mua hàng";
			return false;
		}
		
		if(this.nccTen.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập tên nhà cung cấp mua hàng";
			return false;
		}
		
		try
		{
			/*for (int i = 0; i < this.spList.size(); i++)
			{
				ISanpham sp = this.spList.get(i);
				if ( this.lhhId.equals("0") )
				{
					if (sp.getMasanpham().length() > 0)
					{
						query = "select count(*) as soluong from erp_muahang_sp where sanpham_fk = ( select pk_seq from ERP_SanPham where MA = '" + sp.getMasanpham().trim() + "' ) " +
															"and ngaynhan='" + sp.getNgaynhan() + "'";
						ResultSet rssl = db.get(query);
						String soluong = "";
						if (rssl != null)
						{
							try
							{
								if (rssl.next())
								{
									soluong = rssl.getString("soluong");
								}
								
								rssl.close();
							}
							catch (SQLException e)
							{
								this.msg = "Khong the lay so luong " + query;
								System.out.println("1.Exception tai day: " + e.getMessage());
								return false;
							}
						}
						if (Float.parseFloat(soluong) > 0)
						{
							this.msg = "San pham co ma " + sp.getMasanpham() + " da co ngay nhan la ngay " + sp.getNgaynhan();
							return false;
						}
					}
				}
			}*/
			
			String ngaytao = getDateTime();
			String[] ncc = this.nccTen.split(" - ");
			db.getConnection().setAutoCommit(false);
			
			String loaisanpham = "NULL";
			//if(this.lhhId.equals("0"))
				loaisanpham = this.lspId;
			
			query = "Insert into Erp_MuaHang(NgayMua, DonViThucHien_FK, NhaCungCap_FK, LoaiHangHoa_FK, LoaiSanPham_FK, TongTienAVAT, VAT, TongTienBVAT, DungSai, TrangThai, NgayTao, NgaySua, NguoiTao, NguoiSua, NguonGocHH, TienTe_FK, GhiChu, TyGiaQuyDoi, type, congty_fk)" +
					" Values('" + this.ngaymuahang + "','" + this.dvthId + "'," + ncc[0] + "," + this.lhhId + ", " + loaisanpham + ", " + this.AVAT + "," + this.VAT + ", " + this.BVAT + ", " + this.dungsai + ", '0', '" + ngaytao + "', '" + ngaytao + "'," + this.userId + "," + this.userId + ",'" + this.NguonGocHH + "'," + this.TienTe_FK + ",'" + this.GhiChu + "'," + this.TyGiaQuyDoi + ", '" + this.isdontrahang + "', '" + this.congtyId + "')";
			
			System.out.println("Insert into Erp_MuaHang " + query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi Mua hang: " + query;
				System.out.println("2.Exception tai day: " + query);
				db.getConnection().rollback();
				return false;
			}

			String dmhCurrent = "";
			query = "select SCOPE_IDENTITY() as dmhId";
			ResultSet rsDmh = db.get(query);
			if (rsDmh.next())
			{
				dmhCurrent = rsDmh.getString("dmhId");
				this.id = dmhCurrent;
				rsDmh.close();
			}
			
			//Neu la chi phi, xem xem co vuot ngan sach khong
			for (int i = 0; i < this.spList.size(); i++)
			{
				ISanpham sp = this.spList.get(i);
				
				String SanPham_FK = "NULL";
				String ChiPhi_FK = "NULL";
				String TaiSan_FK = "NULL";
				
				if (this.lhhId.equals("0"))
				{
					query = "select pk_seq from ERP_SanPham where MA = '" + sp.getMasanpham() + "' and pk_seq=" + sp.getPK_SEQ();
				}
				else
				{
					if(this.lhhId.equals("1"))  //Tai san co dinh
					{
						query = "select pk_seq from erp_taisancodinh where MA = '" + sp.getMasanpham() + "'and pk_seq=" + sp.getPK_SEQ();
					}
					else  //Chi phi dich vu
					{
						query = "select pk_seq from erp_nhomchiphi where Ten = N'" + sp.getMasanpham().trim() + "' and trangthai = '1' and loai = '2' and pk_seq=" + sp.getPK_SEQ();
					}
				}
				
				
				ResultSet rs = db.get(query);
				if (rs != null)
				{
					try
					{
						if (rs.next())
						{
							if (this.lhhId.equals("0"))
							{
								SanPham_FK = rs.getString("pk_seq");
								ChiPhi_FK = "NULL";
								TaiSan_FK = "NULL";
							}
							else
							{
								if(this.lhhId.equals("1"))
								{
									SanPham_FK = "NULL";
									ChiPhi_FK = "NULL";
									TaiSan_FK = rs.getString("pk_seq");
								}
								else
								{
									SanPham_FK = "NULL";
									ChiPhi_FK = rs.getString("pk_seq");
									TaiSan_FK = "NULL";
								}
							}
						}
						rs.close();
					}
					catch (Exception e)
					{
						this.msg = "Loi khong the lay PK_SEQ san Pham : " + query;
						System.out.println("3.Exception tai day: " + query);
						this.db.update("rollback");
						return false;
					}
				}
				
				if(SanPham_FK.equals("NULL") && ChiPhi_FK.equals("NULL") && TaiSan_FK.equals("NULL"))
				{
					this.msg = "Vui lòng kiểm tra lại mã sản phẩm / mã tài sản / mã chi phí trong danh sách sản phẩm bạn nhập.";
					this.db.getConnection().rollback();
					return false;
				}
				
				long dongiaviet = Math.round((Double.parseDouble(sp.getDongia()) * this.TyGiaQuyDoi) );
				long thanhtienviet = Math.round( dongiaviet * Double.parseDouble(sp.getSoluong()) );
				
				String ptThue = "0";
				if(sp.getPhanTramThue().trim().length() > 0)
					ptThue = sp.getPhanTramThue().trim();
				
				String thueNK = "0";
				if(sp.getThueNhapKhau().trim().length() > 0)
					thueNK = sp.getThueNhapKhau();
				
				
				query = " insert into ERP_MUAHANG_SP(muahang_fk, sanpham_fk, taisan_fk, chiphi_fk, diengiai, donvi, soluong, dongia, thanhtien, dongiaviet, thanhtienviet, ngaynhan, khott_fk, dungsai, PhanTramThue, ThueNhapKhau) " +
						" values(" + dmhCurrent + ", " + SanPham_FK + ", " + TaiSan_FK + ", " + ChiPhi_FK + ", N'" + sp.getTensanpham() + "', N'" + sp.getDonvitinh() + "', '" + sp.getSoluong() + "','" + sp.getDongia() + "','" + sp.getThanhtien() + "' ,'" + dongiaviet + "', " +
								"'" + thanhtienviet + "', '" + sp.getNgaynhan() + "', " + (sp.getKhonhan().length() > 0 ? sp.getKhonhan() : null) + ", '" + this.dungsai + "','" + ptThue + "', '" + thueNK + "')";
				
				System.out.println("2.Insert Into Erp_MuaHang_SP :" + query);
				
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi Mua hang - San pham: " + query;
					
					System.out.println("5.Exception tai day: " + query);
					db.getConnection().rollback();
					return false;
				}
				
			}// End Insert For tung dong
			
			
			//
			boolean vuotNganSach = false;
			String nam = this.ngaymuahang.substring(0, 4);
			String thang = this.ngaymuahang.substring(5, 7);
			
			if(this.lhhId.equals("2")) //chi phi, dich vu
			{
				query = " select isnull(ngansachConLai.conLai, 0) - muahang.tongGiaTri as cotheSuDung  " +
						"from " +
						"( " +
							"select CHIPHI_FK, SUM(SOLUONG * DONGIA) as tongGiaTri  " +
							"from ERP_MUAHANG_SP where MUAHANG_FK = '" + this.id + "' group by CHIPHI_FK  " +
						")  " +
						"muahang left join " +
						"( " +
							"select nganSach.CHIPHI_FK, isnull(nganSach.DUTOAN, 0) - ISNULL( dukienChi.tongduKien, 0) as conLai  " +
							"from " +
							"( " +
								"select CHIPHI_FK, DUTOAN, THUCCHI   " +
								"from ERP_LAPNGANSACH_CHIPHI   " +
								"where LAPNGANSACH_FK in ( select pk_seq from ERP_LAPNGANSACH where NAM = '" + nam + "' and TRANGTHAI = '1' )   " +
									"and DONVITHUCHIEN_FK = '" + this.dvthId + "'   " +
							")  " +
							"nganSach left join " +
							"(  " +
								"select CHIPHI_FK, SUM(b.SOLUONG * b.DONGIAVIET) as tongduKien  " +
								"from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK  " +
								"where a.LOAIHANGHOA_FK = '2' and a.DONVITHUCHIEN_FK = '" + this.dvthId + "'   " +
									"and SUBSTRING(ngaymua, 0, 5) = '" + nam + "' and a.pk_seq != '" + this.id + "'  " +
								"group by CHIPHI_FK  " +
							") " +
							"dukienChi on nganSach.CHIPHI_FK = dukienChi.CHIPHI_FK   " +
						")  " +
						"ngansachConLai on muahang.CHIPHI_FK = ngansachConLai.CHIPHI_FK";
				
				System.out.println("___Check ngan sach chi phi: " + query);
				
				ResultSet rsCheckNS = db.get(query);
				while(rsCheckNS.next())
				{
					if(rsCheckNS.getDouble("cotheSuDung") < 0)
					{
						vuotNganSach = true;
					}
				}
				rsCheckNS.close();
			}
			else    //Tai san co dinh
			{
				if(this.lhhId.equals("1"))
				{
					query = "select b.pk_seq as taisan_fk, a.SoLuong, a.DonGia, a.THANHTIEN as tongNganSach, a.SOTHANGKH, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12   " +
							"from ERP_LAPNGANSACH_TAISAN a inner join ERP_TAISANCODINH b on a.PK_SEQ = b.LAPNGANSACH_TAISAN_FK  " +
							"where DONVITHUCHIEN_FK = '" + this.dvthId + "' and b.pk_seq in ( select TAISAN_FK from ERP_MUAHANG_SP where MUAHANG_FK = '" + this.id + "' )";
					
					System.out.println("1.Check ngan sach tai san: " + query);
					
					ResultSet rsNgansach = db.get(query);
					while(rsNgansach.next())
					{
						String taisanId = rsNgansach.getString("taisan_fk");
						double ngansachTong = rsNgansach.getDouble("tongNganSach");
						double tongKhauhao = 0;
						
						int sothangKH = rsNgansach.getInt("SOTHANGKH");
						double dongia = rsNgansach.getDouble("DonGia");
						
						int thangthu = 0;
						for(int i = 1; i <= 12; i++)
						{
							int T1 = rsNgansach.getInt("T" + Integer.toString(i));
							if(T1 > 0)
							{
								double khaukhao_thang = ( ( T1 * dongia ) / sothangKH ) * (12 - thangthu);
								thangthu ++;
								
								tongKhauhao += khaukhao_thang;
							}
						}
						
						
						//Lay tat cac cac mua hang cua tai san nay, tinh tong Ngansach da su dung va tong khau hao (du kien)
						query = "select a.NGAYMUA, b.SOLUONG, b.DONGIAVIET   " +
								"from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK where b.TAISAN_FK = '" + taisanId + "' and a.DONVITHUCHIEN_FK = '" + this.dvthId + "'";
						
						System.out.println("2.Check ngan sach tai san da su dung: " + query);
						ResultSet rsTaisan = db.get(query);
						
						double tongNganSach_Mua = 0;
						double tongKhauHao_Mua = 0;
						
						if(rsTaisan != null)
						{
							while(rsTaisan.next())
							{
								String thangbdKhauHao_DuKien = rsTaisan.getString("NGAYMUA").split("-")[1];
								
								int soluongMua = rsTaisan.getInt("SOLUONG");
								double dongiaMua = rsTaisan.getDouble("DONGIAVIET");
								
								tongNganSach_Mua += soluongMua * dongiaMua;
								
								tongKhauHao_Mua +=  ( soluongMua * dongiaMua / sothangKH ) * ( 12 - Integer.parseInt(thangbdKhauHao_DuKien) );
							}
							rsTaisan.close();
						}
						
						
						if( ( tongNganSach_Mua > ngansachTong ) || ( tongKhauHao_Mua > tongKhauhao ) )
						{
							vuotNganSach = true;
							rsNgansach.close();
							break;
						}
						
					}
					rsNgansach.close();
					
					System.out.println("3.Check tai san vuot ngan sach: " + vuotNganSach);
				}
			}
			
			// insert nguoi duyet PO 
			query = "SELECT	DUYETCHIPHI.CHUCDANH_FK, DUYETCHIPHI.QUYETDINH, DUYETCHIPHI.THUTU " +
					"FROM ERP_MUAHANG MUAHANG " +
					"INNER JOIN ERP_CHINHSACHDUYETCHIPHI DUYETCHIPHI ON DUYETCHIPHI.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +
					"INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = DUYETCHIPHI.KHOANGCHIPHI_FK " +
					"INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETCHIPHI.CHUCDANH_FK " +
					"WHERE KHOANGCHIPHI.SOTIENTU < MUAHANG.TONGTIENBVAT AND (KHOANGCHIPHI.SOTIENDEN >= MUAHANG.TONGTIENBVAT OR KHOANGCHIPHI.SOTIENDEN IS NULL) " +
					"AND MUAHANG.PK_SEQ = '" + this.id +"' ORDER BY DUYETCHIPHI.THUTU" ;

			System.out.println("3.Duyet PO:" + query);
			
			ResultSet rs = db.get(query);
			
			boolean dacoTongGiamDoc = false;
			int thutu = 0;
			
			while (rs.next())
			{
				if(rs.getString("CHUCDANH_FK").equals("100003"))
					dacoTongGiamDoc = true;
				
				thutu = Integer.parseInt(rs.getString("THUTU"));
				
				query = "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH, THUTU) " +
						"VALUES('"+ this.id +"', '" + rs.getString("CHUCDANH_FK") + "', '0','" + rs.getString("QUYETDINH")+ "','" + rs.getString("THUTU") + "') ";
				
				System.out.println("4. Insert Duyet PO:" + query);
				if (!db.update(query))
				{
					this.msg = "Khong the them nguoi duyet cho PO: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
	
			if (rs != null) 
				rs.close();
			
			query = "Update ERP_MUAHANG set VUOTNGANSACH = '" + (vuotNganSach == true ? "1" : "0") + "' where pk_seq = '" + this.id + "' ";
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_MUAHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//Vuot ngan sach, trong co che duyet ma chua co tong giam doc
			if(vuotNganSach == true && dacoTongGiamDoc == false)
			{
				//chi tong giam doc moi co quyen quyet dinh
				query = "update ERP_DUYETMUAHANG set QUYETDINH = '0' where MUAHANG_FK = '" + this.id + "'";
				if (!db.update(query))
				{
					this.msg = "Khong the them nguoi duyet cho PO: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				query = "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH, THUTU) " +
						"VALUES('"+ this.id +"', '100003', '0', '1', '" + ( thutu + 1 ) + "') ";
		
				System.out.println("4.Vuot ngan sach. Tong giam doc duyet: " + query);
				if (!db.update(query))
				{
					this.msg = "Khong the them nguoi duyet cho PO: " + query;
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
			this.db.update("rollback");
			this.msg = "1.Exception " + e.getMessage();
			return false;
		}
		
	}

	public boolean updateDmh()
	{
		// Kiem tra moi them vao
		String query = "";
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Vui lòng chọn sản phẩm";
			return false;
		}
		
		if(this.TienTe_FK.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn tiền tệ của đơn mua hàng";
			return false;
		}
		
		if(this.nccTen.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập tên nhà cung cấp mua hàng";
			return false;
		}
		

		/*for (int i = 0; i < this.spList.size(); i++)
		{
			ISanpham sp = this.spList.get(i);
			if ( this.lhhId.equals("0") )
			{
				if (sp.getMasanpham().length() > 0)
				{
					query = "select count(*) as soluong from erp_muahang_sp where sanpham_fk = ( select pk_seq from ERP_SanPham where MA = '" + sp.getMasanpham().trim() + "' ) " +
														"and ngaynhan='" + sp.getNgaynhan() + "' and muahang_fk = '" + this.id + "' ";
					ResultSet rssl = db.get(query);
					String soluong = "";
					if (rssl != null)
					{
						try
						{
							if (rssl.next())
							{
								soluong = rssl.getString("soluong");
							}
							
							rssl.close();
						}
						catch (SQLException e)
						{
							this.msg = "Khong the lay so luong " + query;
							System.out.println("1.Exception tai day: " + e.getMessage());
							return false;
						}
					}
					if (Float.parseFloat(soluong) > 0)
					{
						this.msg = "San pham co ma " + sp.getMasanpham() + " da co ngay nhan la ngay " + sp.getNgaynhan();
						return false;
					}
				}
			}
		}*/
	
		try
		{
			String ngaysua = getDateTime();
			String[] ncc = this.nccTen.split(" - ");
			db.getConnection().setAutoCommit(false);
			
			String loaisanpham = "NULL";
			if(this.lhhId.equals("0"))
				loaisanpham = this.lspId;
			
			query = "SELECT TONGTIENBVAT FROM ERP_MUAHANG WHERE PK_SEQ = '" + this.id + "' ";
			boolean approve = false;
			
			ResultSet rs = this.db.get(query);
			
			if(rs.next()){
				if(Double.parseDouble(rs.getString("TONGTIENBVAT")) <= Double.parseDouble(this.BVAT)){
					approve = true;
				}
			}
			rs.close();
			
			query = "update erp_muahang set ngaymua = '" + this.ngaymuahang + "', donvithuchien_fk = '" + this.dvthId + "', type = '" + this.isdontrahang + "', " +
					"nhacungcap_fk = '" + ncc[0] + "', loaisanpham_fk = " + loaisanpham + ", loaihanghoa_fk = '" + this.lhhId + "', tongtienBVat = " + this.BVAT + ", " +
					"vat = " + this.VAT + ", tongtienAVat = " + this.AVAT + ", dungsai = '" + this.dungsai + "', ngaysua = '" + ngaysua + "', nguoisua = '" + this.userId + "'," +
					"GhiChu='" + this.GhiChu + "', NguonGocHH = '" + this.NguonGocHH + "' where pk_seq = '" + this.id + "'";

			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat Mua hang: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_MUAHANG_SP where muahang_fk = '" + this.id + "'";
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_MUAHANG_SP: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for (int i = 0; i < this.spList.size(); i++)
			{
				ISanpham sp = this.spList.get(i);
				
				String SanPham_FK = "NULL";
				String ChiPhi_FK = "NULL";
				String TaiSan_FK = "NULL";
				
				if (this.lhhId.equals("0"))
				{
					query = "select pk_seq from ERP_SanPham where MA = '" + sp.getMasanpham() + "' and pk_seq="+ sp.getPK_SEQ();
				}
				else
				{
					if(this.lhhId.equals("1"))  //Tai san co dinh
					{
						query = "select pk_seq from erp_taisancodinh where MA = '" + sp.getMasanpham() + "' and pk_seq="+ sp.getPK_SEQ();
					}
					else  //Chi phi dich vu
					{
						query = "select pk_seq from erp_nhomchiphi where Ten = N'" + sp.getMasanpham().trim() + "' and trangthai = '1' and loai = '2'  and pk_seq="+ sp.getPK_SEQ();
					}
				}
				
				rs = db.get(query);
				if (rs != null)
				{
					try
					{
						if (rs.next())
						{
							if (this.lhhId.equals("0"))
							{
								SanPham_FK = rs.getString("pk_seq");
								ChiPhi_FK = "NULL";
								TaiSan_FK = "NULL";
							}
							else
							{
								if(this.lhhId.equals("1"))
								{
									SanPham_FK = "NULL";
									ChiPhi_FK = "NULL";
									TaiSan_FK = rs.getString("pk_seq");
								}
								else
								{
									SanPham_FK = "NULL";
									ChiPhi_FK = rs.getString("pk_seq");
									TaiSan_FK = "NULL";
								}
							}
						}
						rs.close();
					}
					catch (Exception e)
					{
						this.msg = "Loi khong the lay PK_SEQ san Pham : " + query;
						System.out.println("3.Exception tai day: " + query);
						this.db.update("rollback");
						return false;
					}
				}
				
				if(SanPham_FK.equals("NULL") && ChiPhi_FK.equals("NULL") && TaiSan_FK.equals("NULL"))
				{
					this.msg = "Vui lòng kiểm tra lại mã sản phẩm / mã tài sản / mã chi phí trong danh sách sản phẩm bạn nhập.";
					this.db.getConnection().rollback();
					return false;
				}
				
				long dongiaviet = Math.round((Double.parseDouble(sp.getDongia()) * this.TyGiaQuyDoi) );
				long thanhtienviet = Math.round( dongiaviet * Double.parseDouble(sp.getSoluong()) );
				
				String ptThue = "0";
				if(sp.getPhanTramThue().trim().length() > 0)
					ptThue = sp.getPhanTramThue().trim();
				
				String thueNK = "0";
				if(sp.getThueNhapKhau().trim().length() > 0)
					thueNK = sp.getThueNhapKhau();
				
				
				query = "insert into ERP_MUAHANG_SP(muahang_fk, sanpham_fk, taisan_fk, chiphi_fk, diengiai, donvi, soluong, dongia, thanhtien, dongiaviet, thanhtienviet, ngaynhan, khott_fk, dungsai, PhanTramThue, ThueNhapKhau) " +
						"values(" + this.id + ", " + SanPham_FK + ", " + TaiSan_FK + ", " + ChiPhi_FK + ", N'" + sp.getTensanpham() + "', N'" + sp.getDonvitinh() + "', '" + sp.getSoluong() + "','" + sp.getDongia() + "','" + sp.getThanhtien() + "' ,'" + dongiaviet + "', " +
								"'" + thanhtienviet + "', '" + sp.getNgaynhan() + "', " + (sp.getKhonhan().length() > 0 ? sp.getKhonhan() : null) + ", '" + this.dungsai + "','" + ptThue + "', '" + thueNK + "')";
				//System.out.println("Insert Into Erp_MuaHang_SP :" + query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi Mua hang - San pham: " + query;
					
					System.out.println("5.Exception tai day: " + query);
					db.getConnection().rollback();
					return false;
				}
				
				if(!sp.getMNLId().equals("0")){
//					query = "update ERP_MUANGUYENLIEUDUKIEN SET DADATHANG = " +  sp.getSoluong() + " WHERE PK_SEQ = " + sp.getMNLId() + " ";
					query = "UPDATE ERP_MUANGUYENLIEUDUKIEN  " +
							"SET ERP_MUANGUYENLIEUDUKIEN.DADATHANG = ISNULL(A.SOLUONG, 0) " +
							"FROM " + 
							"( " +
							"	SELECT SUM(SOLUONG) AS SOLUONG, MUANGUYENLIEUDUKIEN_FK " +
							"	FROM ERP_MUAHANG_SP WHERE MUANGUYENLIEUDUKIEN_FK = " + sp.getMNLId()  + " " +
							"	GROUP BY MUANGUYENLIEUDUKIEN_FK " +
							")A  WHERE ERP_MUANGUYENLIEUDUKIEN.PK_SEQ = " + sp.getMNLId() + " " ;
					
					System.out.println(query);
					
					this.db.update(query);					
				}
			}// End Insert For tung dong

			// Cap nhat duyet
			if(approve){
				if(this.duyetIds != null){
					int n = this.duyetIds.length;
					for (int i = 0; i < n; i++){
						query = "UPDATE ERP_DUYETMUAHANG SET TRANGTHAI = '1' " +
								"WHERE CHUCDANH_FK = '" + this.duyetIds[i] + "' AND MUAHANG_FK = '" + this.id + "'";
						db.update(query);
					}
				}else{
					query = "UPDATE ERP_DUYETMUAHANG " + 
							"SET TRANGTHAI = '0' " +
							"FROM ERP_DUYETMUAHANG DUYETMUAHANG " +
							"INNER JOIN ERP_CHUCDANH CHUCDANH ON DUYETMUAHANG.CHUCDANH_FK = CHUCDANH.PK_SEQ " +
							"WHERE DUYETMUAHANG.MUAHANG_FK = '"+ this.id  +"' AND CHUCDANH.NHANVIEN_FK = '"+ this.userId +"' ";
					db.update(query);
				}
			}else{
				query = "DELETE FROM ERP_DUYETMUAHANG WHERE MUAHANG_FK = '" + this.id + "'";
				this.db.update(query);
				
				
				boolean vuotNganSach = false;
				if(this.lhhId.equals("2")) //chi phi, dich vu
				{
					String nam = this.ngaymuahang.substring(0, 4);
					
					query = " select isnull(ngansachConLai.conLai, 0) - muahang.tongGiaTri as cotheSuDung  " +
							"from " +
							"( " +
								"select CHIPHI_FK, SUM(SOLUONG * DONGIA) as tongGiaTri  " +
								"from ERP_MUAHANG_SP where MUAHANG_FK = '" + this.id + "' group by CHIPHI_FK  " +
							")  " +
							"muahang left join " +
							"( " +
								"select nganSach.CHIPHI_FK, isnull(nganSach.DUTOAN, 0) - ISNULL( dukienChi.tongduKien, 0) as conLai  " +
								"from " +
								"( " +
									"select CHIPHI_FK, DUTOAN, THUCCHI   " +
									"from ERP_LAPNGANSACH_CHIPHI   " +
									"where LAPNGANSACH_FK in ( select pk_seq from ERP_LAPNGANSACH where NAM = '" + nam + "' and TRANGTHAI = '1' )   " +
										"and DONVITHUCHIEN_FK = '" + this.dvthId + "'   " +
								")  " +
								"nganSach left join " +
								"(  " +
									"select CHIPHI_FK, SUM(b.SOLUONG * b.DONGIAVIET) as tongduKien  " +
									"from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK  " +
									"where a.LOAIHANGHOA_FK = '2' and a.DONVITHUCHIEN_FK = '" + this.dvthId + "'   " +
										"and SUBSTRING(ngaymua, 0, 5) = '" + nam + "' and a.pk_seq != '" + this.id + "'  " +
									"group by CHIPHI_FK  " +
								") " +
								"dukienChi on nganSach.CHIPHI_FK = dukienChi.CHIPHI_FK   " +
							")  " +
							"ngansachConLai on muahang.CHIPHI_FK = ngansachConLai.CHIPHI_FK";
					
					System.out.println("___Check ngan sach chi phi: " + query);
					
					ResultSet rsCheckNS = db.get(query);
					while(rsCheckNS.next())
					{
						if(rsCheckNS.getDouble("cotheSuDung") < 0)
						{
							vuotNganSach = true;
						}
					}
					rsCheckNS.close();
					
				}
				else
				{
					if(this.lhhId.equals("1"))  //Tai san co dinh
					{
						query = "select b.pk_seq as taisan_fk, a.SoLuong, a.DonGia, a.THANHTIEN as tongNganSach, a.SOTHANGKH, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12   " +
								"from ERP_LAPNGANSACH_TAISAN a inner join ERP_TAISANCODINH b on a.PK_SEQ = b.LAPNGANSACH_TAISAN_FK  " +
								"where DONVITHUCHIEN_FK = '" + this.dvthId + "' and b.pk_seq in ( select TAISAN_FK from ERP_MUAHANG_SP where MUAHANG_FK = '" + this.id + "' )";
						
						System.out.println("1.Check ngan sach tai san: " + query);
						
						ResultSet rsNgansach = db.get(query);
						while(rsNgansach.next())
						{
							String taisanId = rsNgansach.getString("taisan_fk");
							double ngansachTong = rsNgansach.getDouble("tongNganSach");
							double tongKhauhao = 0;
							
							int sothangKH = rsNgansach.getInt("SOTHANGKH");
							double dongia = rsNgansach.getDouble("DonGia");
							
							int thangthu = 0;
							for(int i = 1; i <= 12; i++)
							{
								int T1 = rsNgansach.getInt("T" + Integer.toString(i));
								if(T1 > 0)
								{
									double khaukhao_thang = ( ( T1 * dongia ) / sothangKH ) * (12 - thangthu);
									thangthu ++;
									
									tongKhauhao += khaukhao_thang;
								}
							}
							
							
							//Lay tat cac cac mua hang cua tai san nay, tinh tong Ngansach da su dung va tong khau hao (du kien)
							query = "select a.NGAYMUA, b.SOLUONG, b.DONGIAVIET   " +
									"from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK where b.TAISAN_FK = '" + taisanId + "' and a.DONVITHUCHIEN_FK = '" + this.dvthId + "'";
							
							System.out.println("2.Check ngan sach tai san da su dung: " + query);
							ResultSet rsTaisan = db.get(query);
							
							double tongNganSach_Mua = 0;
							double tongKhauHao_Mua = 0;
							
							if(rsTaisan != null)
							{
								while(rsTaisan.next())
								{
									String thangbdKhauHao_DuKien = rsTaisan.getString("NGAYMUA").split("-")[1];
									
									int soluongMua = rsTaisan.getInt("SOLUONG");
									double dongiaMua = rsTaisan.getDouble("DONGIAVIET");
									
									tongNganSach_Mua += soluongMua * dongiaMua;
									
									tongKhauHao_Mua +=  ( soluongMua * dongiaMua / sothangKH ) * ( 12 - Integer.parseInt(thangbdKhauHao_DuKien) );
								}
								rsTaisan.close();
							}
							
							System.out.println("____Tong ngan sach: " + ngansachTong + "   -- Ngan sach mua: " + tongNganSach_Mua);
							System.out.println("____Tong khau hao: " + tongKhauhao + "   -- Ngan sach mua: " + tongKhauHao_Mua);
							
							if( ( tongNganSach_Mua > ngansachTong ) || ( tongKhauHao_Mua > tongKhauhao ) )
							{
								vuotNganSach = true;
								rsNgansach.close();
								break;
							}
							
						}
						rsNgansach.close();
						
						System.out.println("3.Check tai san vuot ngan sach: " + vuotNganSach);
					}
				}
				
				
				// insert nguoi duyet PO 
				query = "SELECT	DUYETCHIPHI.CHUCDANH_FK, DUYETCHIPHI.QUYETDINH, DUYETCHIPHI.THUTU " +
						"FROM ERP_MUAHANG MUAHANG " +
						"INNER JOIN ERP_CHINHSACHDUYETCHIPHI DUYETCHIPHI ON DUYETCHIPHI.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +
						"INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = DUYETCHIPHI.KHOANGCHIPHI_FK " +
						"INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETCHIPHI.CHUCDANH_FK " +
						"WHERE KHOANGCHIPHI.SOTIENTU < MUAHANG.TONGTIENBVAT AND (KHOANGCHIPHI.SOTIENDEN >= MUAHANG.TONGTIENBVAT OR KHOANGCHIPHI.SOTIENDEN IS NULL) " +
						"AND MUAHANG.PK_SEQ = '" + this.id +"' ORDER BY DUYETCHIPHI.THUTU" ;

				System.out.println("3.Duyet PO:" + query);
				
				rs = db.get(query);
				
				boolean dacoTongGiamDoc = false;
				int thutu = 0;
				
				while (rs.next())
				{
					if(rs.getString("CHUCDANH_FK").equals("100003"))
						dacoTongGiamDoc = true;
					
					thutu = Integer.parseInt(rs.getString("THUTU"));
					
					query = "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH, THUTU) " +
							"VALUES('"+ this.id +"', '" + rs.getString("CHUCDANH_FK") + "', '0','" + rs.getString("QUYETDINH")+ "','" + rs.getString("THUTU") + "') ";
					
					System.out.println("4. Insert Duyet PO:" + query);
					if (!db.update(query))
					{
						this.msg = "Khong the them nguoi duyet cho PO: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
		
				query = "Update ERP_MUAHANG  set VUOTNGANSACH = '" + (vuotNganSach == true ? "1" : "0") + "' where pk_seq = '" + this.id + "' ";
				if (!db.update(query))
				{
					this.msg = "Khong the cap nhat ERP_MUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				//Vuot ngan sach, trong co che duyet ma chua co tong giam doc
				if(dacoTongGiamDoc == false && vuotNganSach == true)
				{
					//chi tong giam doc moi co quyen quyet dinh
					query = "update ERP_DUYETMUAHANG set QUYETDINH = '0' where MUAHANG_FK = '" + this.id + "'";
					if (!db.update(query))
					{
						this.msg = "Khong the them nguoi duyet cho PO: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					query = "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH, THUTU) " +
							"VALUES('"+ this.id +"', '100003', '0', '1', '" + ( thutu + 1 ) + "') ";
			
					System.out.println("4.Vuot ngan sach. Tong giam doc duyet: " + query);
					if (!db.update(query))
					{
						this.msg = "Khong the them nguoi duyet cho PO: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				if (rs != null) 
					rs.close();
				
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
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

	public List<IKho> getKhoList()
	{
		return this.khoList;
	}

	public void setKhoList(List<IKho> khoList)
	{
		this.khoList = khoList;
	}

	public List<ITiente> getTienteList()
	{
		return this.tienteList;
	}

	public void setTienteList(List<ITiente> ttList)
	{
		this.tienteList = ttList;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void close()
	{
		try
		{
			
			if (this.dvthRs != null){
				this.dvthRs.close();
			}
			if (this.lhhRs != null){
				this.lhhRs.close();
			}
			if(spList!=null){
				spList.clear();
			}
			if(dvList!=null){
				dvList.clear();
			}
			
			if(tienteList!=null){
				tienteList.clear();
			}
			if(khoList!=null){
				khoList.clear();
			}
			if(ListTTCP!=null){
				ListTTCP.clear();
			}
			this.db.shutDown();
		}
		catch (SQLException e)
		{
			
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

	public String getDungsai()
	{
		return this.dungsai;
	}

	public void setDungsai(String dungsai)
	{
		this.dungsai = dungsai;
	}

	public String getSochungtu() 
	{
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu)
	{
		this.sochungtu = sochungtu;
	}

	public void setNguonGocHH(String nguongoc)
	{
		this.NguonGocHH = nguongoc;
	}

	public String getNguonGocHH()
	{
		return this.NguonGocHH;
	}

	public void setMaLoaiHH(String maloaihh)
	{
		this.MaLoaiHH = maloaihh;

	}

	public String getMaLoaiHH()
	{

		return this.MaLoaiHH;
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

	public void setTrungTamChiPhi_FK(String trungtamchiphi_fk)
	{
		this.TrungTamChiPhi_FK = trungtamchiphi_fk;
	}

	public String getTrungTamChiPhi_FK()
	{

		return this.TrungTamChiPhi_FK;
	}

	public void CreateListTrungTamChiPhi()
	{
		String query = "Select PK_SEQ,Ma,Ten From Erp_TrungTamChiPhi Where TrangThai=1";
		ResultSet rsTTCP = this.db.get(query);
		try
		{
			while (rsTTCP.next())
			{
				ITrungTamChiPhi o = new TrungTamChiPhi();
				o.setId(rsTTCP.getString("PK_SEQ"));
				o.setMaChiPhi(rsTTCP.getString("Ma"));
				o.setTen(rsTTCP.getString("Ten"));
				this.ListTTCP.add(o);
			}
			rsTTCP.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	public List<ITrungTamChiPhi> getTrungTamCpList()
	{

		return this.ListTTCP;
	}

	public void setTrungTamCpList(List<ITrungTamChiPhi> ttcp)
	{
		this.ListTTCP = ttcp;
	}

	public void setTyGiaQuyDoi(float tygiaquydoi)
	{
		this.TyGiaQuyDoi = tygiaquydoi;
	}

	public Float GetTyGiaQuyDoi()
	{

		return this.TyGiaQuyDoi;
	}

	public ResultSet getDuyet(){
		ResultSet rs;
		String query = "SELECT DUYETMUAHANG.CHUCDANH_FK, CHUCDANH.DIENGIAI, DUYETMUAHANG.TRANGTHAI, " +
					   "CASE WHEN CHUCDANH.NHANVIEN_FK = '"+ this.userId +"' THEN '1' " +
					   "ELSE '0' END AS QUYEN " +
					   "FROM ERP_DUYETMUAHANG DUYETMUAHANG " +
					   "INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETMUAHANG.CHUCDANH_FK " +
					   "WHERE DUYETMUAHANG.MUAHANG_FK = '" + this.id + "' " +
					   "ORDER BY THUTU";
		System.out.println(query);
		rs = this.db.get(query);		
		return rs;
	}
	
	public String getTrangthaiDuyet(){
		String result = "Chờ duyệt";
		
		String query = "SELECT " +
					   "	CASE WHEN SUM(QUYETDINH) > 0 THEN  " +
					   "(" +
					   "	SELECT COUNT(TRANGTHAI) - SUM(TRANGTHAI) " + 
					   "	FROM ERP_DUYETMUAHANG " + 
					   "	WHERE MUAHANG_FK = '" + this.id + "' AND QUYETDINH = 1 " + 
					   ") " +
					   "ELSE " +
					   "	COUNT(TRANGTHAI) - SUM(TRANGTHAI) " +
					   "END AS RESULT " +
					   "FROM ERP_DUYETMUAHANG " +
					   "WHERE MUAHANG_FK = '" + this.id + "'";
		
		System.out.println(query);
		
		ResultSet rs = this.db.get(query);
		try{
			if (rs != null){ 				
				if(rs.next()){
					String tmp = rs.getString("RESULT");
					if(tmp != null){
						if(tmp.equals("0")) result = "Đã duyệt";
					}
					rs.close();
				}
			}
			
		}catch (SQLException e){}
		
		return result;
		
	}

	public String getLoaihanghoa() 
	{
		return this.lhhId;
	}

	public void setLoaihanghoa(String loaihh) 
	{
		this.lhhId = loaihh;
	}

	public String getIsdontrahang() 
	{
		return this.isdontrahang;
	}

	public void setIsdontrahang(String dontrahang) 
	{
		this.isdontrahang = dontrahang;
	}

	public String getMakeToStock()
	{
		return this.maketoStock;
	}

	public void setMakeToStock(String maketoStock)
	{
		this.maketoStock = maketoStock;
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
