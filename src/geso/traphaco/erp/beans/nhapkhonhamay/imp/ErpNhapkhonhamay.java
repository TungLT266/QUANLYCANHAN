package geso.traphaco.erp.beans.nhapkhonhamay.imp;

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
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.nhapkhonhamay.*;

public class ErpNhapkhonhamay implements IErpNhapkhonhamay
{
	String congtyId;
	String userId;
	String id;
	String ngaynhanhang;
	String ngaychot;
	String dvthId;
	ResultSet dvthRs;
	String nppId;
	String poId;
	ResultSet poRs;
	String ncc;
	String diengiai;
	String trangthai;
	String soluongPO;
	String soluongDaNhan;
	List<ISanpham> spList;
	
	String msg;
	
	dbutils db;
	private Utility util;
	public ErpNhapkhonhamay()
	{
		this.userId = "";
		this.id = "";
		this.ngaynhanhang = "";
		this.ngaychot = "";
		this.diengiai = "";
		this.dvthId = "";
		this.poId = "";
		this.diengiai = "";
		this.trangthai = "";
		this.soluongPO = "";
		this.soluongDaNhan = "0";
		this.ncc = "";
		this.msg = "";
		
		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
		this.util=new Utility();
		
	}
	
	public ErpNhapkhonhamay(String id)
	{
		this.userId = "";
		this.id = id;
		this.ngaynhanhang = "";
		this.ngaychot = "";
		this.dvthId = "";
		this.poId = "";
		this.diengiai = "";
		this.trangthai = "";
		this.soluongPO = "";
		this.soluongDaNhan = "0";
		this.ncc = "";
		this.msg = "";
		
		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
		this.util=new Utility();
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
	
	public String getNgaynhanhang()
	{
		return this.ngaynhanhang;
	}
	
	public void setNgaynhanhang(String ngaynhanhang)
	{
		this.ngaynhanhang = ngaynhanhang;
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
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public String getDonmuahangId()
	{
		return this.poId;
	}
	
	public void setDonmuahangId(String dmhid)
	{
		this.poId = dmhid;
	}
	
	public ResultSet getDmhList()
	{
		return this.poRs;
	}
	
	public void setDmhList(ResultSet dmhlist)
	{
		this.poRs = dmhlist;
	}
	
	public String getDiengiai()
	{
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}
	
	public List<ISanpham> getSpList()
	{
		return this.spList;
	}
	
	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;
	}
	
	public String getMsg()
	{
		return this.msg;
	}
	
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public void createRs()
	{
		String sql="select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1'";// and congty_fk = '" + this.congtyId + "' ";
		
				//" and pk_seq in " + this.util.quyen_donvithuchien(this.userId);
		this.dvthRs = db.get(sql);
		
		String query = "";
		if(this.dvthId.length() > 0)
		{
			query = " select A.PK_SEQ as poId, " +
					"	a.SOPO as poTen " +
					" from ERP_MUAHANG A " +
					" left join( " +
					"	SELECT 	MUAHANG_FK AS DMHID, " +
					"			CASE WHEN SUM(QUYETDINH) > 0 " + 
					"			THEN  ( " +
					"				SELECT COUNT(TRANGTHAI) - SUM(TRANGTHAI) " + 
					"				FROM ERP_DUYETMUAHANG  " +
					"				WHERE MUAHANG_FK = DUYETMUAHANG.MUAHANG_FK AND QUYETDINH = 1 " +
					"			) 	" +
					"			ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	END AS DUYET " + 
					"	FROM ERP_DUYETMUAHANG DUYETMUAHANG " +
					"	GROUP BY MUAHANG_FK " +
					" ) DUYET ON DUYET.DMHID = A.PK_SEQ " +
					" where A.trangthai in (1, 2) and ISNULL(DUYET.DUYET, 0) = 0 and a.loai = 1 and a.PK_SEQ not in (select PK_SEQ from ERP_MUAHANG where ISDUOCPHANBO = '1') "+
					"and donvithuchien_fk = '" + this.dvthId + "' and loaihanghoa_fk = '0'  "+
					"and A.PK_SEQ not in (select PK_SEQ from ERP_MUAHANG where ISNHAPKHONM = '1') and pk_seq not in (select MUAHANG_FK from ERP_NHApkhonhamay)";
			if (this.id.length() > 0)
			{
				query += " union " +
						 " select PK_SEQ as poId, " +
						 "	a.SOPO as poTen " +
						 " from ERP_MUAHANG a where loaihanghoa_fk = '0' and a.loai = 1 and a.PK_SEQ not in (select PK_SEQ from ERP_MUAHANG where ISDUOCPHANBO = '1') and pk_seq in (select MUAHANG_FK from ERP_NHApkhonhamay where PK_SEQ = '" + this.id + "')";
			}
			System.out.println("danh sach don hang: " + query);
			this.poRs = db.get(query);
		}
		
		if (this.poId.length() > 0 && this.dvthId.length() > 0 && this.spList.size() <= 0 )
		{
			
			NumberFormat formater = new DecimalFormat("#,###,###.##");
			
			if(this.id.trim().length() <= 0)
				query = "select sp.ma as spma ,sp.TEN as spten, sp.pk_seq as spId, dvdl.DIENGIAI as dvdlTen,mh.nhacungcap_fk,mhsp.DONGIA, mhsp.nhomkenh_fk,isnull(mhsp.thuexuat, '0') as thuexuat,"
						+ "(select mhsp.SOLUONG from ERP_MUAHANG_SP mhsp where mhsp.MUAHANG_FK=mh.PK_SEQ and mhsp.SANPHAM_FK= sp.PK_SEQ ) as soluongdat, isnull(mhsp.dungsai, 0) as dungsai, "
						+ "mhsp.ngaynhan as ngaynhan, isnull(sp.hansudung, 0) as hansudung from ERP_SANPHAM  sp inner join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ"
						+ " inner join ERP_MUAHANG_SP mhsp on mhsp.SANPHAM_FK = sp.PK_SEQ inner join ERP_MUAHANG mh on mhsp.MUAHANG_FK= mh.pk_seq " +
						  " WHERE mh.pk_seq = '" + this.poId + "'  ";
			else
				query = "select sp.ma as spma ,sp.TEN as spten, sp.pk_seq as spId, dvdl.DIENGIAI as dvdlTen,nk.ncc_fk as nhacungcap_fk,nksp.DONGIA,nksp.nhomkenh_fk, nksp.thuexuat,"
						+ "nksp.soluongdat, nksp.soluongnhan, isnull(nksp.dungsai, 0) as dungsai, isnull(sp.hansudung, 0) as hansudung, "
						+ "nksp.ngaynhandukien as ngaynhan from ERP_SANPHAM  sp inner join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ"
						+ " inner join ERP_NHAPKHONHAMAY_SP nksp on nksp.SANPHAM_FK = sp.PK_SEQ inner join erp_nhapkhonhamay nk on nksp.nhamay_fk = nk.pk_Seq " +
						  " WHERE nk.pk_seq = '" + this.id + "'  ";
			
			System.out.println("1.Init san pham PO: " + query);
			ResultSet spRs = db.get(query);
			
			List<ISanpham> spList = new ArrayList<ISanpham>();
			 
			if (spRs != null)
			{
				try
				{
					ISanpham sp = null;
					while (spRs.next())
					{
						this.ncc = spRs.getString("nhacungcap_fk");
						String spId = spRs.getString("spId");
						String spMa = spRs.getString("spMa");
						String spTen = spRs.getString("spTen");
						String soluongDat = formater.format(spRs.getDouble("soluongdat"));
						String soluongNhan = "";
						if(this.id.trim().length() <= 0)
							soluongNhan = "0";
						else
							soluongNhan = formater.format(spRs.getDouble("soluongnhan"));
						String ngaynhandukien = spRs.getString("NGAYNHAN");
						String dvdl = spRs.getString("dvdlTen");
						String gia = spRs.getString("dongia");
						String nhomkenh = spRs.getString("nhomkenh_fk");
						String thuexuat = spRs.getString("thuexuat");
						String dungsai = spRs.getString("dungsai");
						String hansudung = spRs.getString("hansudung");
						sp = new Sanpham(spId, spMa, spTen, soluongNhan, soluongDat, dvdl, ngaynhandukien, gia);
						sp.setNhomkenh(nhomkenh);
						sp.setDungsai(dungsai);
						sp.setThuexuat(thuexuat);
						sp.setHansudung(hansudung);
						
						List<ISpDetail> lstSPdt = new ArrayList<ISpDetail>();
						if(this.id.trim().length() > 0)
						{
							query = "select nksp.solo, nksp.soluong, nksp.ngaysanxuat, nksp.ngayhethan from ERP_NHAPKHONHAMAY_SP_chitiet nksp " +
									  " WHERE nksp.nhamay_fk = '" + this.id + "' and nksp.SANPHAM_FK = " + spId;
							ResultSet spdtRs = db.get(query);
							if (spdtRs != null)
								while (spdtRs.next())
								{
									ISpDetail spdt = new SpDetail();
									spdt.setSolo(spdtRs.getString("solo"));
									spdt.setSoluong(spdtRs.getString("soluong"));
									spdt.setNgaySx(spdtRs.getString("ngaysanxuat"));
									spdt.setNgayHethan(spdtRs.getString("ngayhethan"));
									lstSPdt.add(spdt);
								}
						}
						sp.setSpDetail(lstSPdt);
						spList.add(sp);
					}
					spRs.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
			
				}
				
				this.spList = spList;
			}
		}
	}
	
	public void init()
	{
		String query =  " select a.PK_SEQ, a.DIENGIAI, a.DONVITHUCHIEN_FK as dvthId, a.muahang_fk, a.ncc_fk, a.TRANGTHAI, a.ngaynhan, a.ngaynhap from ERP_NHAPKHONHAMAY a"+
				" where a.pk_seq = '" + this.id + "' ";
		
		System.out.println("Init : " + query);
		
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.ngaynhanhang = rs.getString("ngaynhan");
					this.ngaychot = rs.getString("ngaynhap");
					this.dvthId = rs.getString("dvthId");
					this.poId = rs.getString("muahang_fk");
					this.diengiai = rs.getString("diengiai")==null?"":rs.getString("diengiai");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			}
			catch (Exception e)
			{
				System.out.println("1.Exception: " + e.getMessage());
			}
		}
		
		this.createRs();
	}
	
	public boolean createNhanHang()
	{
		String ngaytao = this.getDateTime();
		
		if (this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào để nhận hàng, vui lòng kiểm tra lại";
			return false;
		}
		
		String cmd = "";
		cmd = "select ngaymua from erp_muahang where pk_seq = '" + this.poId + "' and ngaymua > '" + this.ngaynhanhang + "'";
		ResultSet rsCheck = db.get(cmd);
		
		String ngaymua = "";
		if (rsCheck != null)
		{
			try
			{
				if (rsCheck.next())
				{
					ngaymua = rsCheck.getString("ngaymua");
				}
				rsCheck.close();
			}
			catch (SQLException e){}
		}
		
		String query = "";
		
		if (ngaymua.length() > 0)
		{
				this.msg = "Ngày nhận hàng(" + this.ngaynhanhang + ") phải lớn hơn ngày đặt hàng (" + ngaymua + ")";
			return false;
		}
		this.nppId = util.getIdNhapp(userId);
		try
		{
			db.getConnection().setAutoCommit(false);
			
			query = "insert into erp_nhapkhonhamay (muahang_fk, Ngaynhan, DonViThucHien_FK, LoaiHangHoa_FK,TrangThai, NgayTao, NgaySua, NguoiTao, NguoiSua, congty_fk, NHAPHANPHOI_FK, ncc_fk, diengiai)"
					+ "Values('"+this.poId+"', '" + this.ngaynhanhang + "','" + this.dvthId + "', '0', '0', '" + ngaytao + "', '" + ngaytao + "'," + this.userId + "," + this.userId + ", '" + this.congtyId + "', '"+this.nppId+"', '"+this.ncc+"', '"+this.diengiai+"')";
			
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi nhap kho ky gui tai nha may: " + query;
				System.out.println("2.Exception tai day: " + query);
				db.getConnection().rollback();
				return false;
			}
			
			String id = "";
			query = "select SCOPE_IDENTITY() as Id";
			ResultSet rs = db.get(query);
			if (rs.next())
			{
				id = rs.getString("Id");
				this.id = id;
				rs.close();
			}
			
			if(this.spList != null)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					ISanpham sp = this.spList.get(i);
					
					query = "insert into ERP_NHAPKHONHAMAY_SP (nhamay_fk, sanpham_fk, soluongdat, ngaynhandukien, donvi, dongia, khonhan, nhomkenh_fk, thuexuat, dungsai)"
							+ "Values('"+this.id+"', '" + sp.getId() + "','" + sp.getSoluongdat().replace(",", "") + "', '"+this.ngaynhanhang+"', N'" + sp.getDvdl() + "', '"+sp.getGia()+"', (select pk_seq from kho where loai = 7), "+sp.getNhomkenh()+", "+sp.getThuexuat()+", "+sp.getDungsai().replace(",", "")+")";
					System.out.println("insert erp_nhapkhonhamay_sp "+query);
					if (!db.update(query))
					{
						this.msg = "Khong the tao moi nhap kho ky gui tai nha may: " + query;
						System.out.println("2.Exception tai day: " + query);
						db.getConnection().rollback();
						return false;
					}
					
					double soluongdat = Double.parseDouble(sp.getSoluongdat().replace(",", ""));
					double dungsai = Double.parseDouble(sp.getDungsai().replace(",", ""));
					double soluongchitiet = 0;
					List<ISpDetail> lstspdt = sp.getSpDetail();
					for(int j = 0; j < lstspdt.size(); j++)
					{
						ISpDetail spdt = lstspdt.get(j);
						if(spdt.getSolo().trim().length() > 0)
						{
							query = "insert into ERP_NHAPKHONHAMAY_SP_chitiet (nhamay_fk, sanpham_fk, solo, soluong, ngaysanxuat, ngayhethan, ngaynhandukien, donvi, gia, kho_fk, nhomkenh_fk, thuexuat)"
									+ "Values('"+this.id+"', '" + sp.getId() + "','" + spdt.getSolo().replace(",", "") + "','" + spdt.getSoluong().replace(",", "") + "','" + spdt.getNgaySx() + "','" + spdt.getNgayHethan() + "', '"+this.ngaynhanhang+"', N'" + sp.getDvdl() + "', '"+sp.getGia()+"', (select pk_seq from kho where loai = 7), "+sp.getNhomkenh()+", "+sp.getThuexuat()+")";
							System.out.println("insert ERP_NHAPKHONHAMAY_SP_chitiet "+query);
							if (!db.update(query))
							{
								this.msg = "Khong the tao moi nhap kho ky gui tai nha may: " + query;
								System.out.println("3.Exception tai day: " + query);
								db.getConnection().rollback();
								return false;
							}
							
							/*query = "select count(ngayhethan) as soluong from NHAPP_KHO_CHITIET_NCC where sanpham_fk="+sp.getId()+" and solo = " + spdt.getSolo().replace(",", "") + " and kho_fk = (select pk_seq from kho where loai = 7) and nhomkenh_fk = "+sp.getNhomkenh()+" and npp_fk = "+this.nppId;
							System.out.println("insert ERP_NHAPKHONHAMAY_SP_chitiet "+query);
							ResultSet rskt = db.get(query);
							if(rskt != null)
							{
								rskt.next();
								int sl = rskt.getInt("soluong");
								if(sl > 1)
								{
									this.msg = "Khong the tao moi nhap kho ky gui tai nha may: khong the co 2 ngay het han cung so lo";
									System.out.println("3.Exception tai day: " + query);
									db.getConnection().rollback();
									return false;
								}
							}*/
							
							soluongchitiet += Double.parseDouble(spdt.getSoluong().replace(",", ""));
						}
					}
					if ((soluongdat + soluongdat * dungsai / 100) < soluongchitiet)
					{
						this.msg = "Khong the tao moi nhap kho ky gui tai nha may: so luong chi tiet khong duoc lon hon so luong tong";
						System.out.println("3.Exception tai day: " + query);
						db.getConnection().rollback();
						return false;
					}
					else
					{
						query = "update ERP_NHAPKHONHAMAY_SP set soluongnhan = "+soluongchitiet+" where nhamay_fk = '"+this.id+"' and sanpham_fk = '" + sp.getId() + "'";
						
						if (!db.update(query))
						{
							this.msg = "Khong the tao moi nhap kho ky gui tai nha may: " + query;
							System.out.println("3.Exception tai day: " + query);
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
			this.msg="Lỗi tạo nhận hàng: "+e.getMessage();
			e.printStackTrace();
			db.update("rollback");
			 
			return false;
		}
		
	}
	
	public boolean updateNhanHang()
	{
		String ngaysua = this.getDateTime();
		
		if (this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào để nhận hàng, vui lòng kiểm tra lại";
			return false;
		}
		
		String cmd = "";
		cmd = "select ngaymua from erp_muahang where pk_seq = '" + this.poId + "' and ngaymua > '" + this.ngaynhanhang + "'";
		ResultSet rsCheck = db.get(cmd);
		
		String ngaymua = "";
		if (rsCheck != null)
		{
			try
			{
				if (rsCheck.next())
				{
					ngaymua = rsCheck.getString("ngaymua");
				}
				rsCheck.close();
			}
			catch (SQLException e){}
		}
		
		String query = "";
		
		if (ngaymua.length() > 0)
		{
				this.msg = "Ngày nhận hàng(" + this.ngaynhanhang + ") phải lớn hơn ngày đặt hàng (" + ngaymua + ")";
			return false;
		}
		this.nppId = util.getIdNhapp(userId);
		try
		{
			db.getConnection().setAutoCommit(false);
		
			query = " update erp_nhapkhonhamay set muahang_fk = '"+this.poId+"', ngaynhan = '" + this.ngaynhanhang + "', donvithuchien_fk = '" + this.dvthId + "', " +
					" ngaysua = '" + ngaysua + "', nguoisua = '" + this.userId + "', nhaphanphoi_fk='"+this.nppId+"', diengiai = '"+this.diengiai+"'";
										
			query=query+	"  where pk_seq = '" + this.id + "'";

			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat nhap kho ky gui tai nha may: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_NHAPKHONHAMAY_SP where nhamay_fk = '"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat nhap kho ky gui tai nha may: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_NHAPKHONHAMAY_SP_CHITIET where nhamay_fk = '"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat nhap kho ky gui tai nha may: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.spList != null)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					ISanpham sp = this.spList.get(i);
					
					query = "insert into ERP_NHAPKHONHAMAY_SP (nhamay_fk, sanpham_fk, soluongdat, ngaynhandukien, donvi, dongia, khonhan, nhomkenh_fk, thuexuat, dungsai)"
							+ "Values('"+this.id+"', '" + sp.getId() + "','" + sp.getSoluongdat().replace(",", "") + "', '"+this.ngaynhanhang+"', N'" + sp.getDvdl() + "', '"+sp.getGia()+"', (select pk_seq from kho where loai = 7), "+sp.getNhomkenh()+", "+sp.getThuexuat()+", " + sp.getDungsai().replace(",", "") + ")";
					
					if (!db.update(query))
					{
						this.msg = "Khong the tao moi nhap kho ky gui tai nha may: " + query;
						System.out.println("2.Exception tai day: " + query);
						db.getConnection().rollback();
						return false;
					}
					
					double soluongdat = Double.parseDouble(sp.getSoluongdat().replace(",", ""));
					double dungsai = Double.parseDouble(sp.getDungsai().replace(",", ""));
					double soluongchitiet = 0;
					List<ISpDetail> lstspdt = sp.getSpDetail();
					for(int j = 0; j < lstspdt.size(); j++)
					{
						ISpDetail spdt = lstspdt.get(j);
						if(spdt.getSolo().trim().length() > 0)
						{
							query = "insert into ERP_NHAPKHONHAMAY_SP_chitiet (nhamay_fk, sanpham_fk, solo, soluong, ngaysanxuat, ngayhethan, ngaynhandukien, donvi, gia, kho_fk, nhomkenh_fk, thuexuat)"
									+ "Values('"+this.id+"', '" + sp.getId() + "','" + spdt.getSolo().replace(",", "") + "','" + spdt.getSoluong().replace(",", "") + "','" + spdt.getNgaySx() + "','" + spdt.getNgayHethan() + "', '"+this.ngaynhanhang+"', N'" + sp.getDvdl() + "', '"+sp.getGia()+"', (select pk_seq from kho where loai = 7), "+sp.getNhomkenh()+", "+sp.getThuexuat()+")";
							
							if (!db.update(query))
							{
								this.msg = "Khong the tao moi nhap kho ky gui tai nha may: " + query;
								System.out.println("3.Exception tai day: " + query);
								db.getConnection().rollback();
								return false;
							}
							
							/*query = "select count(ngayhethan) as soluong from NHAPP_KHO_CHITIET_NCC where sanpham_fk="+sp.getId()+" and solo = " + spdt.getSolo().replace(",", "") + " and kho_fk = (select pk_seq from kho where loai = 7) and nhomkenh_fk = "+sp.getNhomkenh()+" and npp_fk = "+this.nppId;
							System.out.println("kiem tra ngayhethan "+query);
							ResultSet rskt = db.get(query);
							if(rskt != null)
							{
								rskt.next();
								int sl = rskt.getInt("soluong");
								if(sl > 1)
								{
									this.msg = "Khong the tao moi nhap kho ky gui tai nha may: khong the co 2 ngay het han cung so lo";
									System.out.println("3.Exception tai day: " + query);
									db.getConnection().rollback();
									return false;
								}
							}*/
							
							soluongchitiet += Double.parseDouble(spdt.getSoluong().replace(",", ""));
						}
					}
					if ((soluongdat + soluongdat * dungsai / 100) < soluongchitiet)
					{
						this.msg = "Khong the tao moi nhap kho ky gui tai nha may: so luong chi tiet khong duoc lon hon so luong tong";
						System.out.println("3.Exception tai day: " + query);
						db.getConnection().rollback();
						return false;
					}
					else
					{
						query = "update ERP_NHAPKHONHAMAY_SP set soluongnhan = "+soluongchitiet+" where nhamay_fk = '"+this.id+"' and sanpham_fk = '" + sp.getId() + "'";
						
						if (!db.update(query))
						{
							this.msg = "Khong the tao moi nhap kho ky gui tai nha may: " + query;
							System.out.println("3.Exception tai day: " + query);
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
			this.msg="Lỗi không thể cập nhật nhận hàng: "+e.getMessage();
			e.printStackTrace();
			db.update("rollback");
			return false;
		}
	}
	
	public void close()
	{
		try{
		
			if(spList!=null)
			{
				spList.clear();
			}
			if(dvthRs!=null){
				dvthRs.close();
			}
			if(poRs!=null){
				poRs.close();
			}
			db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public String getNcc() 
	{
		return this.ncc;
	}

	public void setNcc(String ncc)
	{
		this.ncc = ncc;
	}
	
	public String getNgaychot() 
	{
		return this.ngaychot;
	}

	public void setNgaychot(String ngaychot)
	{
		this.ngaychot = ngaychot;
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

