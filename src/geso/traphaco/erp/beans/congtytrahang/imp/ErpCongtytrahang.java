package geso.traphaco.erp.beans.congtytrahang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import geso.traphaco.center.util.Utility;
 
import geso.traphaco.erp.beans.congtytrahang.IErpCongtytrahang;
import geso.traphaco.erp.beans.donmuahang.IDonvi;
import geso.traphaco.erp.beans.donmuahang.IKho;
import geso.traphaco.erp.beans.donmuahang.ISanpham;
import geso.traphaco.erp.beans.donmuahang.ITiente;
import geso.traphaco.erp.beans.donmuahang.ITrungTamChiPhi;
import geso.traphaco.erp.beans.donmuahang.imp.Donvi;
import geso.traphaco.erp.beans.donmuahang.imp.Kho;
import geso.traphaco.erp.beans.donmuahang.imp.Sanpham;
import geso.traphaco.erp.beans.donmuahang.imp.Tiente;
import geso.traphaco.erp.beans.donmuahang.imp.TrungTamChiPhi;
import geso.traphaco.erp.util.DinhDang;
import geso.traphaco.erp.util.Kho_Lib;
import geso.traphaco.center.beans.dondathang.imp.ErpThongtinkho;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;

public class ErpCongtytrahang   implements IErpCongtytrahang
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
	String nccId;
	String nccTen;
	String nccLoai;
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
	
	List<ISanpham> spListCone = new ArrayList<ISanpham>();
	
	Hashtable<String, String> sanpham_soluong;
	Hashtable<String, String> sanpham_soluongDAXUAT;
	String BVATNGUYENTE;
	String VATNGUYENTE;
	String AVATNGUYENTE;
	
	
	ResultSet nguoiDuyetRs;
	
	public ResultSet getNguoiDuyetRs() {
		return nguoiDuyetRs;
	}

	public void setNguoiDuyetRs(ResultSet nguoiDuyetRs) {
		this.nguoiDuyetRs = nguoiDuyetRs;
	}

	public String getBVATNGUYENTE() {
		return BVATNGUYENTE;
	}

	public void setBVATNGUYENTE(String bVATNGUYENTE) {
		BVATNGUYENTE = bVATNGUYENTE;
	}

	public String getVATNGUYENTE() {
		return VATNGUYENTE;
	}

	public void setVATNGUYENTE(String vATNGUYENTE) {
		VATNGUYENTE = vATNGUYENTE;
	}

	public String getAVATNGUYENTE() {
		return AVATNGUYENTE;
	}

	public void setAVATNGUYENTE(String aVATNGUYENTE) {
		AVATNGUYENTE = aVATNGUYENTE;
	}



	
	String msg;
	String dungsai;
	String lspId;
	String isdontrahang;
	String maketoStock;
	String khoId;
	String canduyet;
	
	String lydotrahang;
	
	String quydoiStr;
	
	dbutils db;
	
	private Utility util;
	private Utility_Kho util_kho=new Utility_Kho();
	

	public ErpCongtytrahang()
	{
		this.userId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = "";
		this.ngaymuahang = "";
		this.dvthId = "";
		this.nccId = "";
		this.nccTen = "";
		this.nccLoai = "";
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
		this.sanpham_soluong = new Hashtable<String, String>();
		this.sanpham_soluongDAXUAT = new Hashtable<String, String>();
		this.lspId = "";
		this.isdontrahang = "2";
		this.maketoStock = "0";
		this.khoId = "";
		this.canduyet = "1";
		this.lydotrahang = "";
		this.db = new dbutils();
		this.util=new Utility();
		
		this.quydoiStr = "";
	}

	public ErpCongtytrahang(String id)
	{
		this.userId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = id;
		this.ngaymuahang = "";
		this.dvthId = "";
		this.nccId = "";
		this.nccTen = "";
		this.nccLoai = "";
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
		this.sanpham_soluong = new Hashtable<String, String>();
		this.sanpham_soluongDAXUAT = new Hashtable<String, String>();
		this.lspId = "";
		this.isdontrahang = "2";
		this.maketoStock = "0";
		this.khoId = "";
		this.canduyet = "1";
		this.db = new dbutils();
		this.util=new Utility();
	}

	public List<ISanpham> GetListSanPhamCone() {
		
		return this.spListCone;
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
		return this.nccId;
	}

	public void setNCC(String ncc)
	{
		this.nccId = ncc;
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
		String sql=" select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1'  ";
				   //and pk_seq in " + util.quyen_donvithuchien(this.userId)
		
		System.out.println("Get Dvkd : "+sql);
		this.dvthRs = db.get(sql);
		
		this.lhhRs = db.get("Select pk_seq, ten, ma From Erp_LoaiSanPham where TRANGTHAI = '1' AND CONGTY_FK = "+ this.congtyId +" ");
		
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
			catch (SQLException e) { }
		}

		String query = "select PK_SEQ, TEN from ERP_KHOTT where TRANGTHAI = '1'";
		
		ResultSet khoTT = db.get(query);
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
			catch (SQLException e){ }
		}

		query = "select ERP_TIENTE.PK_SEQ, ERP_TIENTE.MA, ERP_TIGIA.TIGIAQUYDOI " +
				"from ERP_TIENTE inner join ERP_TIGIA on ERP_TIENTE.PK_SEQ = ERP_TIGIA.TIENTE_FK " +
				"where ERP_TIENTE.Trangthai = '1' and ERP_TIGIA.Trangthai = '1' " +
				"order by ERP_TIENTE.PK_SEQ ASC";
		
		if( this.NguonGocHH.equals("TN")){
			// chi load tien viet
			query = "select ERP_TIENTE.PK_SEQ, ERP_TIENTE.MA, ERP_TIGIA.TIGIAQUYDOI " +
					"from ERP_TIENTE inner join ERP_TIGIA on ERP_TIENTE.PK_SEQ = ERP_TIGIA.TIENTE_FK " +
					"where ERP_TIENTE.Trangthai = '1' and ERP_TIGIA.Trangthai = '1' and  ERP_TIENTE.PK_SEQ='100000'" +
					"order by ERP_TIENTE.PK_SEQ ASC";
		}
		else
		{
			// nuoc ngoai , ko load vnd
			query = "select ERP_TIENTE.PK_SEQ, ERP_TIENTE.MA, ERP_TIGIA.TIGIAQUYDOI " +
					"from ERP_TIENTE inner join ERP_TIGIA on ERP_TIENTE.PK_SEQ = ERP_TIGIA.TIENTE_FK " +
					"where ERP_TIENTE.Trangthai = '1' and ERP_TIGIA.Trangthai = '1' and  ERP_TIENTE.PK_SEQ<>'100000'" +
					"order by ERP_TIENTE.PK_SEQ ASC";
		}
		System.out.println(" lay tien te/ti gia: "+ query);
		ResultSet tiente = db.get(query);
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
		
		String query = 
			    " select a.PK_SEQ as dmhId, isnull(a.NguonGocHH ,'') as NguonGocHH, isnull(a.TienTe_FK, '100000') as TienTe_FK, " +
				"        c.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, a.NGAYMUA, isnull(a.GhiChu,'') as GhiChu, " +
				"        a.DONVITHUCHIEN_FK as dvthId, a.LOAIHANGHOA_FK, a.LOAISANPHAM_FK,  isnull(b.loainhacungcap_fk,0) as nccLoai, b.pk_seq as nccId, b.ma + ', ' + b.TEN as nccTen, " +
				"        isnull(a.TONGTIENAVAT, '0') as TONGTIENAVAT, isnull(a.VAT, '0') as VAT, isnull(a.TONGTIENBVAT, 0) as TONGTIENBVAT, isnull(a.Dungsai, '0') as dungsai, a.TRANGTHAI, isnull(b.loainhacungcap_fk,0) as loainhacungcap_fk, b.khoNL_Nhan_GC, isnull(a.lydotrahang,'') lydotrahang ," +
				"        isnull(a.TONGTIENBVATNGUYENTE, '0') as TONGTIENBVATNGUYENTE, isnull(a.TONGTIENVATNGUYENTE, '0') as TONGTIENVATNGUYENTE, isnull(a.TONGTIENAVATNGUYENTE, 0) as TONGTIENAVATNGUYENTE"+
				" 			from ERP_MUAHANG a inner join ERP_NHACUNGCAP b on a.NHACUNGCAP_FK = b.PK_SEQ " +
				"                    inner join ERP_DONVITHUCHIEN c on c.PK_SEQ = a.DONVITHUCHIEN_FK  " +
				" where a.pk_seq = '" + this.id + "'  " ;
		//and c.pk_seq  in "+ util.quyen_donvithuchien(this.userId)
		
		System.out.println("Don Mua Hang : " + query);
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
					this.nccId = rs.getString("nccId");
					this.nccTen = rs.getString("nccTen");
					this.nccLoai = rs.getString("nccLoai");
					this.lhhId = rs.getString("LOAIHANGHOA_FK");
					this.lspId = rs.getString("LOAISANPHAM_FK")== null ? "" : rs.getString("LOAISANPHAM_FK");
					this.BVAT = formatter.format(rs.getDouble("TONGTIENBVAT"));
					this.VAT = formatter.format(rs.getDouble("VAT"));
					this.AVAT = formatter.format(rs.getDouble("TONGTIENAVAT"));
					this.trangthai = rs.getString("trangthai");
					this.dungsai = rs.getString("dungsai");
					this.sochungtu = rs.getString("SOCHUNGTU");
					this.NguonGocHH = rs.getString("NguonGocHH");
					this.TienTe_FK = rs.getString("TienTe_FK");
					this.GhiChu = rs.getString("GhiChu");
					this.lydotrahang = rs.getString("lydotrahang");


				}
				rs.close();
				query = "select (select MA from ERP_SANPHAM where pk_seq = a.sanpham_fk ) as spMA, ISNULL(a.marq,'') as marq,isnull(a.nsx_fk,0) as nsx_fk, solo, ngayhethan, mathung, mame,maphieu, isnull( bin.ma, '' ) as vitri, '' as scheme, sum(soluong)  as soluong,isnull(hamluong,'100') as hamluong,isnull(hamam,'0') as hamam ,NGAYNHAPKHO " +
						"from ERP_MUAHANG_SP_CHITIET a left join ERP_BIN bin on a.bin_fk = bin.pk_seq " + 
						"where MUAHANG_FK = '" + this.id + "' " + 
						"group by a.sanpham_fk, solo, ngayhethan, mathung, mame,maphieu, bin.ma,isnull(hamluong,'100') ,isnull(hamam,'0'),NGAYNHAPKHO, ISNULL(a.marq,''),isnull(a.nsx_fk,0)  ";
				System.out.println("---INIT SP CHI TIET: " + query);
				rs = db.get(query);
				if(rs != null)
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					while(rs.next())
					{
						
						
						String key = rs.getString("spMA")+ "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("vitri") + "__" + rs.getString("mathung") + "__" + rs.getString("mame") + "__" + rs.getString("maphieu")+ "__" + rs.getString("hamluong")+ "__" + rs.getString("hamam")+ "__" + rs.getString("NGAYNHAPKHO")+ "__" + rs.getString("marq")+ "__" + rs.getString("nsx_fk"); 
						
						System.out.println( "::: KEY BEAN:  " + key + " -- SLG: " + rs.getString("soluong") );
						sp_soluong.put( key , rs.getString("soluong") );
					}
					rs.close();
					
					this.sanpham_soluong = sp_soluong;
					this.sanpham_soluongDAXUAT = this.sanpham_soluong;
				}
			}
			catch (Exception e)
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}

		this.createRs();
		this.createSanpham();
		
		this.createPopupNguoiDuyet();
		
		
	}
	private void createPopupNguoiDuyet() {
		String sql = " select cd.DIENGIAI, d.TRANGTHAI, cd.NHANVIEN_FK, nv.TEN " + " from ERP_DUYETMUAHANG d "
				+ " inner join ERP_MUAHANG mh on mh.PK_SEQ = d.MUAHANG_FK "
				+ " left join ERP_CHUCDANH cd on cd.PK_SEQ = d.CHUCDANH_FK"
				+ " left join NHANVIEN nv on nv.PK_SEQ = cd.NHANVIEN_FK " + " where mh.PK_SEQ = " + this.id;
		
		System.out.println(" danh sach nguoi duyet : " +sql +"\n");
		this.nguoiDuyetRs = this.db.get(sql);

	}
	private void createSanpham()
	{
		String query =      " select  isnull(b.pk_seq,0) as spid, isnull(b.ma, '') as spMa, isnull(b.dvkd_fk,0) as spDvkd,  \n" +
					 
							"         isnull(b.ten1, b.ten)  as spTen, \n" +
							"         isnull(b.ten1, b.ten) as spTen2, 'NA' as spNh, \n" +
							"         isnull(tscd.pk_seq,0) as tscdid ,isnull(tscd.ma, '') as tscdMa, isnull(a.diengiai, tscd.ten) as tscdTen, isnull(nts.ma, 'NA') as nstNh,  \n" +
							"         isnull(ncp.pk_seq,0) as ncpid,isnull(ncp.ten, '') as ncpMa, isnull(a.diengiai, ncp.diengiai) as ncpTen, isnull(ttcp.diengiai, 'NA') as ncpNh, \n" +
							"		  isnull(a.donvi, '') as donvi, a.soluong, isnull(a.dongia, '0') as dongia, \n" +
							" 		  isnull(a.thanhtien, '0') as thanhtien, isnull(a.phantramthue, '0') as phantramthue, isnull(a.thuenhapkhau, '0') as thuenhapkhau, ngaynhan, a.khott_fk, dungsai,  \n" +
							" 	      isnull(muanguyenlieudukien_fk, 0) as mnlId , '1' as inraHd \n, " +
							" 		  isnull(a.thuexuat, '0') as thuexuat ," +
							" 		  isnull(a.tenhd, '') as tenhd ," +
							" 		  isnull(a.vat, '') as tienvat " +
							" from erp_muahang_sp a left join erp_SANPHAM b on a.sanpham_fk = b.pk_seq   \n" +
							"      left join erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq  \n" +
							"      left join erp_nhomtaisan nts on tscd.NhomTaiSan_fk = nts.pk_seq   \n" +
							"      left join erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq \n" +
							"      left join erp_trungtamchiphi ttcp on ncp.ttchiphi_fk = ttcp.pk_seq  \n" +
							" where muahang_fk = '" + this.id + "'";
		
		System.out.println(" San pham init: " + query);
		ResultSet spRs = db.get(query);
		List<ISanpham> spList = new ArrayList<ISanpham>();
		
		NumberFormat formatter = new DecimalFormat("#,###,###.####"); 
		if (spRs != null)
		{
			try
			{
				NumberFormat format = new DecimalFormat("#,###,###.###");
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
						sp.setTenXHD(spRs.getString("spTen2") );
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
					sp.setTenHD(spRs.getString("tenhd"));
					sp.setQuycachsanpham("") ;
					
					sp.setSoluong(formatter.format(spRs.getDouble("soluong")));
					sp.setSoluong_bk(formatter.format(spRs.getDouble("soluong")));
					
					sp.setDonvitinh(spRs.getString("donvi"));
					sp.setDongia(formatter.format(spRs.getDouble("dongia")));
					sp.setThanhtien(formatter.format(spRs.getDouble("thanhtien")));
					
					sp.setNgaynhanstr(spRs.getString("ngaynhan"));
					
					
					sp.setInraHd(spRs.getString("inraHd")) ;
					
					if(spRs.getString("khott_fk") != null)
						sp.setKhonhan(spRs.getString("khott_fk"));
					sp.setThueNhapKhau(formatter.format(spRs.getDouble("thuenhapkhau")));
					sp.setPhanTramThue(formatter.format(spRs.getDouble("phantramthue")));
					
						
					sp.setThuexuat(formatter.format(spRs.getDouble("thuexuat")));
					
					sp.setTienVAT(formatter.format(spRs.getDouble("tienvat")));

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

	
	public static String formatVN(String so) {
		
		String result = so.replaceAll(",", "@");
		result = result.replaceAll("[.]", ",");
		result = result.replaceAll("@", ".");
		
		return result;
		
	}
	
	private void capNhatSoluongChoListSanPhamCone(ISanpham sp, String qcG) 
	{

		String key = sp.getMasanpham() + qcG;
		//Tìm để thêm vào sanpham.listsanphamCone
		ISanpham _spCone = null, _spTemp; 
		for(int _z = 0; _z < spListCone.size(); _z++) {
			_spTemp = spListCone.get(_z);
			if(_spTemp != null && _spTemp.getId().equals(key) && sp.getDongia() == _spTemp.getDongia()) {
				_spCone = _spTemp;
				break;
			}
		}
		if(_spCone == null) {
			_spCone = new Sanpham();
			_spCone.setId(key);
			_spCone.setDongia(sp.getDongia());
			_spCone.setSoluong("0");
			spListCone.add(_spCone);
		}
		double sl = Double.parseDouble(_spCone.getSoluong()) + Double.parseDouble(sp.getSoluong()) ;
		_spCone.setSoluong(Double.toString(sl));
	
		
	}

	public void CreatePOfromPR(ResultSet rs, String mnlId){
		try{
			if(rs.next()){
				this.db.getConnection().setAutoCommit(false);
			
				String query = 	"Insert into Erp_MuaHang(NgayMua, DonViThucHien_FK, NhaCungCap_FK, " +
								"LoaiHangHoa_FK, LoaiSanPham_FK, TongTienAVAT, VAT, TongTienBVAT, DungSai, TrangThai, NgayTao, " +
								"NgaySua, NguoiTao, NguoiSua, congty_fk) " +
								"Values('" + this.getDateTime() + "','100003', null, '0', '100002', '0', '0' , '0' , '0', '0', '" + this.getDateTime() + "', " +
								"'" + this.getDateTime() + "'," + this.userId + "," + this.userId + ", '" + this.congtyId + "')";

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
			
				query = " insert into ERP_MUAHANG_SP(muahang_fk, sanpham_fk, diengiai, donvi, soluong, muanguyenlieudukien_fk, tenHD) " +
						" values(" + this.id + ", " + rs.getString("SANPHAM_FK") + ", N'" + rs.getString("TEN") + "', N'" + rs.getString("DONVI") + "', " +
						"'" + rs.getString("SOLUONG") + "', '" + mnlId + "')";

				this.db.update(query);
				
//				query = "update ERP_MUANGUYENLIEUDUKIEN SET DADATHANG = DADATHANG + " + rs.getString("SOLUONG") + " WHERE PK_SEQ = " + mnlId + " ";
				
/*				query = "UPDATE ERP_MUANGUYENLIEUDUKIEN  " +
						"SET ERP_MUANGUYENLIEUDUKIEN.DADATHANG = ISNULL(A.SOLUONG, 0) " +
						"FROM " + 
						"( " +
						"	SELECT SUM(SOLUONG) AS SOLUONG, MUANGUYENLIEUDUKIEN_FK " +
						"	FROM ERP_MUAHANG_SP WHERE MUANGUYENLIEUDUKIEN_FK = " + mnlId + " " +
						"	GROUP BY MUANGUYENLIEUDUKIEN_FK " +
						")A  WHERE ERP_MUANGUYENLIEUDUKIEN.PK_SEQ = " + mnlId + " " ;*/
				
				//Cap nhat DADATHANG trong ERP_MUANGUYENLIEUDUKIEN
				query = "UPDATE ERP_MUANGUYENLIEUDUKIEN	" +  
						"SET ERP_MUANGUYENLIEUDUKIEN.DADATHANG = ISNULL(A.SOLUONG, 0) " + 
						"FROM " +
						"( " +
						"	SELECT SUM(SOLUONG) AS SOLUONG, SANPHAM_FK, SUBSTRING(NGAYNHAN, 1, 4) AS NAM,	" + 
						"	CONVERT(INT, SUBSTRING(NGAYNHAN, 6,2)) AS THANG	" +
						"	FROM ERP_MUAHANG_SP " +
						"	WHERE SANPHAM_FK IS NOT NULL	" +
						"	GROUP BY SANPHAM_FK, SUBSTRING(NGAYNHAN, 1, 4),CONVERT(INT, SUBSTRING(NGAYNHAN, 6,2))	" +
						")A  " +
						"WHERE ERP_MUANGUYENLIEUDUKIEN.NAM = A.NAM	" + 
						"AND ERP_MUANGUYENLIEUDUKIEN.THANG = A.THANG	" +
						"AND ERP_MUANGUYENLIEUDUKIEN.SANPHAM_FK = A.SANPHAM_FK ";
				
						
				System.out.println(query);
				
				this.db.update(query);
						
				
				/*boolean vuotNganSach = false;
				//Chen co che duyet
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
					}
				}
		
				if (rs != null) 
					rs.close();
				
				query = "Update ERP_MUAHANG set VUOTNGANSACH = '" + (vuotNganSach == true ? "1" : "0") + "' where pk_seq = '" + this.id + "' ";
				if (!db.update(query))
				{
					this.msg = "Khong the cap nhat ERP_MUAHANG: " + query;
					db.getConnection().rollback();
				}
				*/
				
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
		else 
		{
			if (this.lhhId.equals("0")) //Check cac kho trong SP phai giong nhau
			  {
				String khonhan = "";
				for(int i = 0; i < this.spList.size(); i++)
				{
					ISanpham sp = this.spList.get(i);
					
					if(sp.getMasanpham().trim().length() > 0)
					{
						if(sp.getSoluong().trim().length() <= 0)
						{
							this.msg = "Vui lòng nhập số lượng trả của sản phẩm ( " + sp.getTensanpham() + " )";
							return false;
						}
						
						if( sp.getKhonhan().trim().length() <= 0 )
						{
							this.msg = "Vui lòng chọn kho trả của sản phẩm ( " + sp.getTensanpham() + " )";
							return false;
						}
						
						if(sp.getKhonhan().trim().length() > 0 && khonhan.trim().length() <= 0 )
							khonhan = sp.getKhonhan();
						
						//Trong don tra hang, tat ca SP phai cung kho nhan
						if(khonhan.trim().length() > 0)
						{
							if(!khonhan.trim().equals(sp.getKhonhan().trim()))
							{
								this.msg = "Trong đơn trả hàng, các sản phẩm phải cùng 1 kho trả.";
								return false;
							}
						}
					}
				}
			 }
		}
		
		if(this.TienTe_FK.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn tiền tệ của đơn trả hàng";
			return false;
		}
		
		if(this.nccTen.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập tên nhà cung cấp mua hàng";
			return false;
		}
		
		try
		{
			String ngaytao = getDateTime();
			db.getConnection().setAutoCommit(false);
			 
			String loaisanpham = "NULL";
			if(this.lhhId.equals("0"))
				loaisanpham = this.lspId;
			
			
			System.out.println("Loai hang hoa:"+this.lhhId);
			 String nam = this.ngaymuahang.substring(0, 4);
			 String thang = this.ngaymuahang.substring(5, 7);
			query=	" SELECT ISNULL( MAX(SOTUTANG_THEONAM), 0) AS MAXSTT, " +
					" (SELECT PREFIX FROM ERP_DONVITHUCHIEN  "+
					" WHERE PK_SEQ ="+this.dvthId+" ) AS PREFIX   "+
					" FROM ERP_MUAHANG  DMH WHERE SUBSTRING(NGAYMUA, 0, 5) = "+nam+ 
					" AND DMH.DONVITHUCHIEN_FK="+this.dvthId;
			//System.out.println("Du lieu po sai  :"+query);
			
			String soPO = "";
			int sotutang_theonam = 0;
			ResultSet rsPO = db.get(query);  
			if(rsPO.next())
			{
				sotutang_theonam =  (rsPO.getInt("maxSTT") + 1 );
				String prefix = rsPO.getString("PREFIX");
				String namPO = this.ngaymuahang.substring(2, 4);
				String chuoiso= ("0000"+ Integer.toString(sotutang_theonam)).substring(("0000"+ Integer.toString(sotutang_theonam)).length()-4,("0000"+ Integer.toString(sotutang_theonam)).length());
				
				soPO = prefix + "-" +   chuoiso+ "/" + thang + "/" + namPO;
		 
			}
			rsPO.close();
			System.out.println("---SO PO: " + soPO);
			
			query = " Insert into Erp_MuaHang(NgayMua, DonViThucHien_FK, NhaCungCap_FK, LoaiHangHoa_FK, TongTienAVAT, VAT, TongTienBVAT, DungSai, TrangThai, NgayTao, NgaySua, NguoiTao, NguoiSua, NguonGocHH, TienTe_FK, GhiChu, TyGiaQuyDoi, type, congty_fk,SOPO, SOTUTANG_THEONAM, LYDOTRAHANG, TONGTIENBVATNGUYENTE, TONGTIENVATNGUYENTE, TONGTIENAVATNGUYENTE)" +
					" Values('" + this.ngaymuahang + "','" + this.dvthId + "'," + this.nccId + "," + this.lhhId + " , " + this.AVAT + "," + this.VAT + ", " + this.BVAT + ", " + this.dungsai + ", '0', '" + ngaytao + "', '" + ngaytao + "'," + this.userId + "," + this.userId +  
					" , '" + this.NguonGocHH + "'," + this.TienTe_FK + ",N'" + this.GhiChu + "'," + this.TyGiaQuyDoi + ", '2', '" + this.congtyId + "','" + soPO + "', '" + sotutang_theonam + "', N'"+this.lydotrahang+"',"+ this.BVATNGUYENTE +","+ this.VATNGUYENTE+","+ this.AVATNGUYENTE+")";
			System.out.println("---query: " + query);
			 
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi Mua hang: " + query;
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
					//query = "select pk_seq from ERP_SanPham where MA = '" + sp.getMasanpham() + "' and pk_seq=" + sp.getPK_SEQ();
					SanPham_FK = sp.getPK_SEQ();
				}
				else
				{
					if(this.lhhId.equals("1"))  //Tai san co dinh
					{
						//query = "select pk_seq from erp_taisancodinh where MA = '" + sp.getMasanpham() + "'and pk_seq=" + sp.getPK_SEQ();
						TaiSan_FK = sp.getPK_SEQ();
					}
					else  //Chi phi dich vu
					{
						//query = "select pk_seq from erp_nhomchiphi where Ten = N'" + sp.getMasanpham().trim() + "' and trangthai = '1' and loai = '2' and pk_seq=" + sp.getPK_SEQ();
						ChiPhi_FK = sp.getPK_SEQ();
					}
				}
			 
				if(SanPham_FK.equals("NULL") && ChiPhi_FK.equals("NULL") && TaiSan_FK.equals("NULL"))
				{
					this.msg = "Vui lòng kiểm tra lại mã sản phẩm / mã tài sản / mã chi phí trong danh sách sản phẩm bạn nhập.";
					this.db.getConnection().rollback();
					return false;
				}
				
				/*long dongiaviet = Math.round((Double.parseDouble(sp.getDongia()) * this.TyGiaQuyDoi) );*/
				
				String dongiaviet = "0";
				if(sp.getDongiaViet().trim().length() > 0)
					dongiaviet =sp.getDongiaViet().trim();
				long thanhtienviet = Math.round( Double.parseDouble(dongiaviet.replaceAll(",", ""))  * Double.parseDouble(sp.getSoluong()) );
				
				String ptThue = "0";
				if(sp.getPhanTramThue().trim().length() > 0)
					ptThue = sp.getPhanTramThue().trim();
				
				String thueNK = "0";
				if(sp.getThueNhapKhau().trim().length() > 0)
					thueNK = sp.getThueNhapKhau();
				
				String tenHD = "";
				if(sp.getTenHD().length() > 0){
					tenHD = sp.getTenHD();
				}
				
				String thuexuat = "0";
				if(sp.getThuexuat().trim().length() > 0)
					thuexuat = sp.getThuexuat();
				
				String tienvat = "0";
				if(sp.getTienVAT().trim().length() > 0)
					tienvat = sp.getTienVAT();
				
				
				
				query = " insert into ERP_MUAHANG_SP(muahang_fk, sanpham_fk, taisan_fk, chiphi_fk, diengiai, donvi, soluong, dongia, thanhtien, " +
						" dongiaviet, thanhtienviet, ngaynhan, khott_fk, dungsai, PhanTramThue, ThueNhapKhau, TENHD , thuexuat, vat) " +
						" values(" + dmhCurrent + ", " + SanPham_FK + ", " + TaiSan_FK + ", " + ChiPhi_FK + ", N'" + sp.getTensanpham() + "', " +
						" N'" + sp.getDonvitinh() + "', '" + sp.getSoluong() + "','" + sp.getDongia() + "','" + sp.getThanhtien() + "' ,'" + dongiaviet + "', " +
						" '" + thanhtienviet + "', '" + sp.getNgaynhanstr() + "', " + (sp.getKhonhan().length() > 0 ? sp.getKhonhan() : null) + ", " +
						" '" + this.dungsai + "','" + ptThue + "', '" + thueNK + "', N'" + tenHD + "','"+ thuexuat+"','"+ tienvat+"')";
				
				 
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi Tra hang - San pham: " + query;
 
					db.getConnection().rollback();
					return false;
				}
				
				
				if(this.sanpham_soluong != null)
				{
					Enumeration<String> keys = this.sanpham_soluong.keys();
					double totalCT = 0;
					
					while(keys.hasMoreElements())
					{
						String key = keys.nextElement();
						
						if(key.startsWith( sp.getMasanpham()  ) )
						{
							String[] _sp = this.mySplit( key, "__" );
							
							String _soluongCT = "0"; 
							if(this.sanpham_soluong.get(key) != null)
							{
								_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
							}
							
							if(_soluongCT.trim().length() >0 &&  Double.parseDouble(_soluongCT.replaceAll(",", "")) !=0)
							{
								//QUY VỀ SỐ LƯỢNG CHUẨN LƯU KHO
								
								double soluongCHUAN = Double.parseDouble(_soluongCT.replaceAll(",", ""));
								
								
								//Bên đầu bán, lúc nhập chỉ có số lô, ngày hết hạn, vị trí, các thông số khác sẽ đề xuất chỗ này
								
								List<ErpThongtinkho> chitiet = util.DeXuatKho_TRAHANG_NEW(db, this.ngaymuahang, "1", sp.getKhonhan(), sp.getMasanpham(), _sp[1],  _sp[2], _sp[3], _sp[4], _sp[5], _sp[6], _sp[7], _sp[8], _sp[9], _sp[10], _sp[11], soluongCHUAN );
								if( chitiet == null )
								{
									this.msg = "Lỗi đề xuất sản phẩm, vui lòng liên hệ admin để xử lý.";
									db.getConnection().rollback();
									return false;
								}
								
								totalCT += Double.parseDouble(_soluongCT);
								
								for( int j = 0; j < chitiet.size(); j++  )
								{
									ErpThongtinkho tt = chitiet.get(j);
									
									
									query = "insert ERP_MUAHANG_SP_CHITIET( KHOTT_SP_CT_FK,MUAHANG_FK, SANPHAM_FK, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, phieudt, phieueo, soluong,TIENHANG,TIENVAT,TIENHANGSAUVAT,NSX_FK ) " +
											"select "+sp.getKhonhan()+",'" + this.id + "', '" + tt.spId + "', N'" + tt.solo + "', '" + tt.ngayhethan + "', '" + tt.ngaynhapkho + "', '" + tt.mame + "', '" + tt.mathung + "', N'" + tt.maphieu + "', '" + tt.MARQ + "', '" + tt.hamluong + "', '" + tt.hamam + "', '" + tt.vitriId + "', '" + tt.phieudt + "', '" + tt.phieueo + "', '" + tt.soluong + "' "+
											" ,ROUND(("+ tt.soluong+" * "+sp.getDongia().replaceAll(",", "")+"),0) AS TIENHANG, ROUND(((ROUND(("+ tt.soluong+" * "+sp.getDongia().replaceAll(",", "")+"),0))*("+thuexuat+")/100),0) AS TIENVAT"+
											", (ROUND(("+ tt.soluong+" * "+sp.getDongia().replaceAll(",", "")+"),0) +ROUND(((ROUND(("+ tt.soluong+" * "+sp.getDongia().replaceAll(",", "")+"),0))*("+thuexuat+")/100),0) )AS TIENHANGSAUVAT,"+tt.nsxId+"  ";
									
									System.out.println("1.2.Insert ERP_DONDATHANG_SANPHAM_CHITIET: " + query);
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi ERP_DONDATHANG_SANPHAM_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
									
								}
							}
						}
					}
					
					//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
					if(DinhDang.dinhdangso(totalCT) != DinhDang.dinhdangso(Double.parseDouble(sp.getSoluong().replaceAll(",", "").trim())) )
					{
						
						this.msg = "Tổng xuất theo lô của sản phẩm ( " + sp.getTensanpham() + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " +sp.getSoluong()+ " ) ";
						db.getConnection().rollback();
						return false;
					}
				}
				
			}
			
			//cap nhat kho
			if(this.lhhId.trim().equals("0"))
			{
				//số lượng giữ nguyên, tăng booked, giảm avai
				query="   SELECT A.NSX_FK,A.MARQ,A.SANPHAM_FK, A.SOLO,B.KHOTT_FK as KHO_FK,A.SOLUONG,A.MARQ,A.MATHUNG,A.MAME,A.NGAYHETHAN,A.NGAYNHAPKHO ,ISNULL(A.MAPHIEU,'') AS MAPHIEU,ISNULL(A.PHIEUDT,'') AS MAPHIEUDINHTINH, "
						 +"\nISNULL(A.PHIEUEO,'') AS PHIEUEO , ISNULL(A.HAMLUONG,100) AS HAMLUONG ,ISNULL(A.HAMAM,0) AS HAMAM ,ISNULL(A.BIN_FK,0) AS BIN_FK"
						 +"\nfrom ERP_MUAHANG_SP_CHITIET A inner join ERP_MUAHANG_SP B on A.MUAHANG_FK=B.MUAHANG_FK and A.SANPHAM_FK=B.SANPHAM_FK"
						 +"\ninner join ERP_MUAHANG C on C.PK_SEQ=B.MUAHANG_FK"
						 +"\nwhere C.PK_SEQ='"+dmhCurrent+"' and C.TRANGTHAI=0";
				
				
					ResultSet rskho=db.get(query);
					while(rskho.next()){
						double booked =rskho.getDouble("SOLUONG");
						double avai =(-1) *  rskho.getDouble("SOLUONG");;
							
						Kho_Lib kholib=new Kho_Lib();
						kholib.setNgaychungtu(this.ngaymuahang);
						kholib.setLoaichungtu("Tao moi book kho ErpCongtytrahang  :"+this.id); 
						kholib.setKhottId(rskho.getString("KHO_FK"));
						kholib.setBinId(rskho.getString("BIN_FK"));
						kholib.setSolo(rskho.getString("SOLO"));
						kholib.setSanphamId(rskho.getString("SANPHAM_FK"));
						kholib.setNsxId(rskho.getString("NSX_FK")==null?"":rskho.getString("NSX_FK"));
						kholib.setMame(rskho.getString("MAME"));
						kholib.setMathung(rskho.getString("MATHUNG"));
						
						kholib.setMaphieu(rskho.getString("MAPHIEU"));
						kholib.setMaphieudinhtinh(rskho.getString("MAPHIEUDINHTINH"));
						kholib.setPhieuEo(rskho.getString("PHIEUEO"));
						kholib.setNgayhethan(rskho.getString("NGAYHETHAN"));
					 
						kholib.setNgaynhapkho(rskho.getString("NGAYNHAPKHO"));
						kholib.setBooked( booked);
						
						kholib.setSoluong(0);
						kholib.setAvailable(avai);
						
						kholib.setMARQ(rskho.getString("MARQ"));
						kholib.setDoituongId("");
						kholib.setLoaidoituong("");
						 
						kholib.setHamluong(rskho.getString("HAMLUONG"));
						kholib.setHamam(rskho.getString("HAMAM"));
						kholib.setNgaysanxuat("");
						
						String msg1= util_kho.Update_Kho_Sp_Tra_NEW(db,kholib);
					    if( msg1.length() >0)
						{
					    	this.msg = msg1;
					    	System.out.println(" cau loi kho : "+ this.msg);
					    	db.getConnection().rollback();
							return false;
						}
					   
					
				}
			rskho.close();
			
			
			}
			
			
			// CAI DUYET PO
			System.out.println("loai don " + this.lhhId );
			// PO nhập khẩu && PO trong nước (PO tổng dùng để phân bổ)
			if (this.lhhId.equals("0") || (this.lhhId.equals("1"))) {
				// NẾU CÓ CHÍNH SÁCH DUYỆT DÀNH CHO NCC CỦA ĐƠN MUA HÀNG, LẤY CHÍNH SÁCH DUYỆT ĐÓ
				query = CreateChinhSachDuyet();
			}
			// insert nguoi duyet PO : Mua chi phí/TSCD/CCDC lấy trong cơ chế// duyệt
			else {
				query = CreateChinhSachDuyet();
			}

			System.out.println("3.Duyet PO....:" + query);
			ResultSet rs = db.get(query);
			while (rs.next()) {
				query = "if not exists (select * from ERP_DUYETMUAHANG where CHUCDANH_FK = "
						+ rs.getString("CHUCDANH_FK") + " AND MUAHANG_FK = " + this.id + ")"
						+ "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH) " + "VALUES('"
						+ this.id + "', '" + rs.getString("CHUCDANH_FK") + "', '0','" + rs.getString("QUYETDINH")
						+ "') ";

				System.out.println("4. Insert Duyet PO:" + query);
				if (!db.update(query)) {
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
			e.printStackTrace();
			this.db.update("rollback");
			this.msg = "1.Exception " + e.getMessage();
			return false;
		}
		
	}
	
	

	private String CreateChinhSachDuyet() {
		String query = "SELECT	NCC.CHUCDANH_FK, NCC.QUYETDINH " + "FROM ERP_MUAHANG MUAHANG  "
				+ " INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
				+ " INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
				+ " WHERE MUAHANG.PK_SEQ = '" + this.id + "' " +
				  " UNION ALL " +
				// NẾU KO CÓ CHÍNH SÁCH DUYỆT DÀNH CHO SẢN PHẨM CỦA ĐƠN
				// MUA HÀNG VÀ KO CÓ CHÍNH SÁCH DUYỆT CHO NCC CỦA ĐƠN
				// MUA HÀNG
				  " SELECT	SP.CHUCDANH_FK, SP.QUYETDINH " + "FROM ERP_MUAHANG MUAHANG  "
				+ " INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
				+ " INNER JOIN ERP_CHINHSACHDUYET_SANPHAM SP ON SP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
				+ " AND SP.SANPHAM_FK IN (SELECT SANPHAM_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + this.id + "') "
				+ " LEFT JOIN " + "(  " + "	SELECT	COUNT(*) AS NUM " + "	FROM ERP_MUAHANG MUAHANG   "
				+ "	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
				+ "	INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ " +
			      " AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
				+ "	AND NCC.NCC_FK IN (SELECT NHACUNGCAP_FK FROM ERP_MUAHANG WHERE PK_SEQ = '" + this.id + "')  "
				+ "	WHERE MUAHANG.PK_SEQ = '" + this.id + "'   " + ")DUYET_NCC ON 1 = 1 " + "LEFT JOIN " + "( "
				+ "	SELECT COUNT(MH_SP.SANPHAM_FK) AS NUM " + "	FROM ERP_MUAHANG_SP MH_SP  "
				+ "	INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK "
				+ "	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MH.DONVITHUCHIEN_FK AND DUYET.TRANGTHAI = 1 "
				+ "	WHERE MH.PK_SEQ = '" + this.id + "'  AND MH_SP.SANPHAM_FK NOT IN  "
				+ "	(SELECT SANPHAM_FK FROM ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = DUYET.PK_SEQ) "
				+ " )KTRA_SP ON 1 = 1 " + "WHERE MUAHANG.PK_SEQ = '" + this.id
				+ "'  AND DUYET_NCC.NUM = 0 AND KTRA_SP.NUM = 0 " +

				  " UNION ALL " + "SELECT	CP.CHUCDANH_FK, CP.QUYETDINH " + "FROM ERP_MUAHANG MUAHANG   "
				+ " INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
				+ " INNER JOIN ERP_CHINHSACHDUYET_CHIPHI CP ON CP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
				+ " INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = CP.KHOANGCHIPHI_FK  "
				
				 +" INNER JOIN ERP_CHUCDANH CD ON CD.PK_SEQ=CP.CHUCDANH_FK AND CD.TRANGTHAI=1 "   
				
				+ " LEFT JOIN( " + "	SELECT	COUNT(SP.CHUCDANH_FK) AS NUM " + "	FROM ERP_MUAHANG MUAHANG   "
				+ "	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
				+ "	INNER JOIN ERP_CHINHSACHDUYET_SANPHAM SP ON SP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
				+ "	AND SP.SANPHAM_FK IN (SELECT SANPHAM_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + this.id + "') "
				+ "	LEFT JOIN " + "	( " + "		SELECT	COUNT(*) AS NUM " + "		FROM ERP_MUAHANG MUAHANG   "
				+ "		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
				+ "		INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
				+ "		AND NCC.NCC_FK IN (SELECT NCC_FK FROM ERP_MUAHANG WHERE PK_SEQ = '" + this.id + "')  "
				+ "		WHERE MUAHANG.PK_SEQ = '" + this.id + "'   " + "	)DUYET_NCC ON 1 = 1 " + "	LEFT JOIN "
				+ "	( " + "		SELECT COUNT(MH_SP.SANPHAM_FK) AS NUM " + "		FROM ERP_MUAHANG_SP MH_SP  "
				+ "		INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK "
				+ "		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MH.DONVITHUCHIEN_FK AND DUYET.TRANGTHAI = 1 "
				+ "		WHERE MH.PK_SEQ = '" + this.id + "'  AND MH_SP.SANPHAM_FK NOT IN  "
				+ "		(SELECT SANPHAM_FK FROM ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = DUYET.PK_SEQ) "
				+ "	)KTRA_SP ON 1 = 1 " + "	WHERE MUAHANG.PK_SEQ = '" + this.id
				+ "'  AND DUYET_NCC.NUM = 0 AND KTRA_SP.NUM = 0 " +
 
				")DUYET_SP ON 1 = 1 " + "LEFT JOIN( " + "	SELECT	COUNT(NCC.CHUCDANH_FK) AS NUM "
				+ "	FROM ERP_MUAHANG MUAHANG   "
				+ "	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
				+ "	INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
				+ "	WHERE MUAHANG.PK_SEQ = '" + this.id + "'" + ")DUYET_NCC ON 1 = 1 "
				+ "WHERE (case when KHOANGCHIPHI.SOTIENTU =0  then  KHOANGCHIPHI.SOTIENTU-1 else  KHOANGCHIPHI.SOTIENTU end )   < MUAHANG.TONGTIENBVAT "
				+ "AND (KHOANGCHIPHI.SOTIENDEN >= MUAHANG.TONGTIENBVAT OR KHOANGCHIPHI.SOTIENDEN IS NULL) "
				+ "AND MUAHANG.PK_SEQ = '" + this.id + "' AND DUYET_SP.NUM = 0 AND DUYET_NCC.NUM = 0 ";
		
		
		 
		// Lưu ý: không tính tỉ giá quy đổi tại đây vì đã quy đổi rồi.
		// Khi tạo tính lại tiền thì vui lòng chia ngược lại với tỉ giá để biết
		// được đúng số nguyên tệ
		// MUAHANG.TONGTIENBVAT hiện tại đang lưu tiền VND đã được quy đổi sang
		// USD

		return query;
	}


	
	

	public boolean updateDmh()
	{
		// Kiem tra moi them vao
		String query = "";
		
		//Check kho
	
		if(this.TienTe_FK.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn tiền tệ của đơn mua hàng";
			return false;
		}
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Vui lòng chọn sản phẩm";
			return false;
		}
		else
		{
		  if(this.lhhId.equals("0")) //Check cac kho trong SP phai giong nhau
			  {
				String khonhan = "";
				for(int i = 0; i < this.spList.size(); i++)
				{
					ISanpham sp = this.spList.get(i);
					
					if(sp.getMasanpham().trim().length() > 0)
					{
						if(sp.getSoluong().trim().length() <= 0)
						{
							this.msg = "Vui lòng nhập số lượng trả của sản phẩm ( " + sp.getTensanpham() + " )";
							return false;
						}
						
						if( sp.getKhonhan().trim().length() <= 0 )
						{
							this.msg = "Vui lòng chọn kho trả của sản phẩm ( " + sp.getTensanpham() + " )";
							return false;
						}
						
						if(sp.getKhonhan().trim().length() > 0 && khonhan.trim().length() <= 0 )
							khonhan = sp.getKhonhan();
						
						//Trong don tra hang, tat ca SP phai cung kho nhan
						if(khonhan.trim().length() > 0)
						{
							if(!khonhan.trim().equals(sp.getKhonhan().trim()))
							{
								this.msg = "Trong đơn trả hàng, các sản phẩm phải cùng 1 kho trả.";
								return false;
							}
						}
					}
				}
			 }	
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
			String ngaysua = getDateTime();
			db.getConnection().setAutoCommit(false);
			
			String loaisanpham = "NULL";
	 
				//truoc khi delete
			//số lượng giữ nguyên, tăng booked, giảm avai
			if(this.lhhId.trim().equals("0"))
			{
				query="   SELECT A.NSX_FK,A.MARQ,A.SANPHAM_FK, A.SOLO,B.KHOTT_FK as KHO_FK,A.SOLUONG,A.MARQ,A.MATHUNG,A.MAME,A.NGAYHETHAN,A.NGAYNHAPKHO ,ISNULL(A.MAPHIEU,'') AS MAPHIEU,ISNULL(A.PHIEUDT,'') AS MAPHIEUDINHTINH, "
						 +"\nISNULL(A.PHIEUEO,'') AS PHIEUEO , ISNULL(A.HAMLUONG,100) AS HAMLUONG ,ISNULL(A.HAMAM,0) AS HAMAM ,ISNULL(A.BIN_FK,0) AS BIN_FK"
						 +"\nfrom ERP_MUAHANG_SP_CHITIET A inner join ERP_MUAHANG_SP B on A.MUAHANG_FK=B.MUAHANG_FK and A.SANPHAM_FK=B.SANPHAM_FK"
						 +"\ninner join ERP_MUAHANG C on C.PK_SEQ=B.MUAHANG_FK"
						 +"\nwhere C.PK_SEQ='"+this.id+"' and C.TRANGTHAI=0";
				
						ResultSet rs=db.get(query);
						while(rs.next()){
							double booked = (-1)*DinhDang.dinhdangso(rs.getDouble("SOLUONG"));
							double avai =DinhDang.dinhdangso(rs.getDouble("SOLUONG"));
								
							Kho_Lib kholib=new Kho_Lib();
							kholib.setNgaychungtu(this.ngaymuahang);
							kholib.setLoaichungtu("Update tăng kho nguoc lại ErpCongtytrahang  :"+this.id);
							kholib.setKhottId(rs.getString("KHO_FK"));
							kholib.setBinId(rs.getString("BIN_FK"));
							kholib.setSolo(rs.getString("SOLO"));
							kholib.setSanphamId(rs.getString("SANPHAM_FK"));
							kholib.setNsxId(rs.getString("NSX_FK")==null?"":rs.getString("NSX_FK"));
							kholib.setMame(rs.getString("MAME"));
							kholib.setMathung(rs.getString("MATHUNG"));
							
							kholib.setMaphieu(rs.getString("MAPHIEU"));
							kholib.setMaphieudinhtinh(rs.getString("MAPHIEUDINHTINH"));
							kholib.setPhieuEo(rs.getString("PHIEUEO"));
							kholib.setNgayhethan(rs.getString("NGAYHETHAN"));
						 
							kholib.setNgaynhapkho(rs.getString("NGAYNHAPKHO"));
							kholib.setBooked( booked);
							
							kholib.setSoluong(0);
							kholib.setAvailable(avai);
							
							kholib.setMARQ(rs.getString("MARQ"));
							kholib.setDoituongId("");
							kholib.setLoaidoituong("");
							 
							kholib.setHamluong(rs.getString("HAMLUONG"));
							kholib.setHamam(rs.getString("HAMAM"));
							kholib.setNgaysanxuat("");
							
							String msg1= util_kho.Update_Kho_Sp_Tra_NEW(db,kholib);
						    if( msg1.length() >0)
							{
						    	this.msg = msg1;
						    	System.out.println(" cau loi kho : "+ this.msg);
						    	db.getConnection().rollback();
								return false;
							}
						 
						
					}
						
				   rs.close();
			}
			
			/*
				query = " select isnull(MH.LOAIHANGHOA_FK,'1') as LOAIHANGHOA_FK , MHSP.sanpham_fk, MHSP.soluong, MHSP.khott_fk " +
						" from ERP_MUAHANG_SP MHSP " +
						" INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ=MHSP.MUAHANG_FK  where muahang_fk = '" + this.id + "'";
				 
				ResultSet spRs = db.get(query);
			 
					while(spRs.next())
					{
						String sp_fk = spRs.getString("sanpham_fk");
						 
						String khott_fk = spRs.getString("khott_fk");
						
						if(spRs.getString("LOAIHANGHOA_FK").trim().equals("0")){
						
							 
							double available_= spRs.getDouble("soluong");
							double booked_=(-1)*spRs.getDouble("soluong");
							 
							String msg1 = util_kho.Update_Kho_Sp(this.db,  khott_fk, sp_fk,0, booked_,available_, 0);
							
							if(msg1.length()>0)
							{
								this.msg =msg1;
								db.getConnection().rollback();
								return false;
							}
							
						}
					}
					spRs.close();
					query = "  select b.NgayMUA, a.KHOTT_SP_CT_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
							" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
							"  from ERP_MUAHANG_SP_CHITIET a inner join ERP_MUAHANG b on a.MUAHANG_FK = b.PK_SEQ " + 
							"  where b.PK_SEQ = '" + this.id + "' " + 
							"  group by b.NgayMUA, a.KHOTT_SP_CT_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";
					
					System.out.println("::: CAP NHAT KHO: " + query);
					ResultSet rscn = db.get(query);
					
						while( rscn.next() )
						{
							String khoId = rscn.getString("KHOTT_SP_CT_FK");
							String spId = rscn.getString("SanPham_fk");
							String solo = rscn.getString("SoLo");
							String ngayhethan = rscn.getString("NgayHetHan");
							String ngaynhapkho = rscn.getString("ngaynhapkho");
							
							String loaidoituong = "";
							String doituongId = "";
							
							String mame = rscn.getString("mame");
							String mathung = rscn.getString("mathung");
							String bin_fk = rscn.getString("bin_fk");
							
							String maphieu = rscn.getString("maphieu");
							String phieudt = rscn.getString("phieudt");
							String phieueo = rscn.getString("phieueo");
							
							String marq = rscn.getString("marq");
							String hamluong = rscn.getString("hamluong");
							String hamam = rscn.getString("hamam");

							double soluong = rscn.getDouble("soluong");
							
							this.msg =util.Update_KhoTT_Chitiet(rscn.getString("NgayMUA"), "update tăng kho ngược lại Đơn trả hàng ncc", db,khoId, spId,
									solo, ngayhethan, ngaynhapkho,mame,mathung,  bin_fk,maphieu,phieudt, phieueo,marq, hamluong, hamam,"", "", 0, -1* soluong,  soluong, 0, "");
							
							
							if( msg.trim().length() > 0 )
							{
								db.getConnection().rollback();
								return false;
							}
						}
						rscn.close();*/
					
			
			
			if(this.lhhId.equals("0"))
				loaisanpham = this.lspId;
			
			
			//CAP NHAT SO PO, MA TU DONG TANG
			 String nam = this.ngaymuahang.substring(0, 4);
			 String thang = this.ngaymuahang.substring(5, 7);
				String soPO = "";
				int sotutang_theonam = 0;
				
				boolean cothaydoi_dvth=false;
				
			  query=" select  donvithuchien_fk from erp_muahang mh " +
			  		" where mh.pk_seq="+this.id +" and ( donvithuchien_fk <>  "+this.dvthId +" or  SUBSTRING(NGAYMUA, 0, 5) <> "+nam +" ) ";
			  ResultSet rscheckdv=db.get(query);
			  if(rscheckdv.next()){
				  cothaydoi_dvth=true;
				  // Có thay đổi đơn vị thực hiện.phải thay đổi lại số PO và số thứ tự,hoặc năm bị thay đổi
						query=	" SELECT ISNULL( MAX(SOTUTANG_THEONAM), 0) AS MAXSTT, (SELECT PREFIX FROM ERP_DONVITHUCHIEN  "+
						" WHERE PK_SEQ ="+this.dvthId+" ) AS PREFIX   "+
						" FROM ERP_MUAHANG  DMH WHERE SUBSTRING(NGAYMUA, 0, 5) = "+nam+ 
						"  and dmh.type  = '1'  AND DMH.DONVITHUCHIEN_FK="+this.dvthId;
						System.out.println("Du lieu po sai  :"+query);
						
					
						ResultSet rsPO = db.get(query);  //MẤY NỮA BỔ SUNG THÊM, QUA NĂM MỚI SỐ TỰ TĂNG RESET VỀ BẰNG 1
						if(rsPO.next())
						{
							sotutang_theonam =  (rsPO.getInt("maxSTT") + 1 );
							String prefix = rsPO.getString("PREFIX");
							String namPO = this.ngaymuahang.substring(2, 4);
							String chuoiso= ("0000"+ Integer.toString(sotutang_theonam)).substring(("0000"+ Integer.toString(sotutang_theonam)).length()-4,("0000"+ Integer.toString(sotutang_theonam)).length());
							
							soPO = prefix + "-" +   chuoiso+ "/" + thang + "/" + namPO;
					 
						}
						rsPO.close();
						  
			  }
			  rscheckdv.close();
			  
			query = " update erp_muahang set ngaymua = '" + this.ngaymuahang + "', donvithuchien_fk = '" + this.dvthId + "', type = '2', " +
					" nhacungcap_fk = '" + this.nccId + "',   loaihanghoa_fk = '" + this.lhhId + "', tiente_fk = '" + this.TienTe_FK + "', tygiaquydoi = '" + this.TyGiaQuyDoi + "', tongtienBVat = " + this.BVAT + ", " +
					" vat = " + this.VAT + ", tongtienAVat = " + this.AVAT + ", dungsai = '" + this.dungsai + "', ngaysua = '" + ngaysua + "', nguoisua = '" + this.userId + "'," +
					" GhiChu=N'" + this.GhiChu + "', NguonGocHH = '" + this.NguonGocHH + "', LYDOTRAHANG = N'"+this.lydotrahang+"', TONGTIENBVATNGUYENTE='"+ this.BVATNGUYENTE+"',TONGTIENVATNGUYENTE='"+ this.VATNGUYENTE+ "',TONGTIENAVATNGUYENTE='"+ this.AVATNGUYENTE+"'" ;
 
					if(cothaydoi_dvth){
						query=query+ " ,SOTUTANG_THEONAM ='"+sotutang_theonam+"',SOPO='"+soPO+"' ";
					}
			
			query=query+  " where pk_seq = '" + this.id + "'";

			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat Tra hang: " + query;
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
			query = "delete ERP_MUAHANG_SP_CHITIET where muahang_fk = '" + this.id + "'";
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
					//query = "select pk_seq from ERP_SanPham where MA = '" + sp.getMasanpham() + "' and pk_seq = "+ sp.getPK_SEQ();
					SanPham_FK =  sp.getPK_SEQ();
				}
				else
				{
					if(this.lhhId.equals("1"))  //Tai san co dinh
					{
						//query = "select pk_seq from erp_taisancodinh where MA = '" + sp.getMasanpham() + "' and pk_seq="+ sp.getPK_SEQ();
						TaiSan_FK=  sp.getPK_SEQ();
					}
					else  //Chi phi dich vu
					{
						//query = "select pk_seq from erp_nhomchiphi where Ten = N'" + sp.getMasanpham().trim() + "' and trangthai = '1' and loai = '2'  and pk_seq="+ sp.getPK_SEQ();
						ChiPhi_FK= sp.getPK_SEQ();
					}
				}
			 
				
				if(SanPham_FK.equals("NULL") && ChiPhi_FK.equals("NULL") && TaiSan_FK.equals("NULL"))
				{
					this.msg = "Vui lòng kiểm tra lại mã sản phẩm / mã tài sản / mã chi phí trong danh sách sản phẩm bạn nhập.";
					this.db.getConnection().rollback();
					return false;
				}
				
			/*	long dongiaviet = Math.round((Double.parseDouble(sp.getDongia()) * this.TyGiaQuyDoi) );
				long thanhtienviet = Math.round( dongiaviet * Double.parseDouble(sp.getSoluong()) );*/
				
				
				String dongiaviet = "0";
				if(sp.getDongiaViet().trim().length() > 0)
					dongiaviet =sp.getDongiaViet().trim();
				long thanhtienviet = Math.round( Double.parseDouble(dongiaviet.replaceAll(",", ""))  * Double.parseDouble(sp.getSoluong()) );
			
				
				
				String ptThue = "0";
				if(sp.getPhanTramThue().trim().length() > 0)
					ptThue = sp.getPhanTramThue().trim();
				
				String thueNK = "0";
				if(sp.getThueNhapKhau().trim().length() > 0)
					thueNK = sp.getThueNhapKhau();
				
				
				String thuexuat = "0";
				if(sp.getThuexuat().trim().length() > 0)
					thuexuat = sp.getThuexuat();
				
				String tienvat = "0";
				if(sp.getTienVAT().trim().length() > 0)
					tienvat = sp.getTienVAT();
				
				if(this.lhhId.trim().equals("0"))//Check ton kho
				{
					query = "select count(*) as sodong from ERP_KHOTT_SANPHAM where khott_fk = '" + sp.getKhonhan() + "' " +
							" and sanpham_fk = '" + SanPham_FK + "' and cast(available as numeric(18,4)) >= cast(" + sp.getSoluong().replaceAll(",", "") +  " as numeric(18,4)) ";
					
					
					ResultSet rsCheck = db.get(query);
					System.out.println("cau check ton "+ query);
					if(rsCheck != null)
					{
						if(rsCheck.next())
						{
							if(rsCheck.getInt("sodong") <= 0)
							{
								db.getConnection().rollback();
								rsCheck.close();
								this.msg = "Sản phẩm ( " + sp.getTensanpham() + " ) không đủ tồn kho để trả. Vui lòng kiểm tra lại. ";
								return false;
							}
						}
						rsCheck.close();
					}
				}
				query = "insert into ERP_MUAHANG_SP(muahang_fk, sanpham_fk, taisan_fk, chiphi_fk, diengiai, donvi, soluong, dongia, thanhtien, dongiaviet, thanhtienviet, ngaynhan, khott_fk, dungsai, PhanTramThue, ThueNhapKhau, Muanguyenlieudukien_fk, tenHD, thuexuat, vat) " +
						"values(" + this.id + ", " + SanPham_FK + ", " + TaiSan_FK + ", " + ChiPhi_FK + ", N'" + sp.getTensanpham() + "', N'" + sp.getDonvitinh() + "', '" + sp.getSoluong() + "','" + sp.getDongia() + "','" + sp.getThanhtien() + "' ,'" + dongiaviet + "', " +
								"'" + thanhtienviet + "', '" + sp.getNgaynhanstr() + "', " + (sp.getKhonhan().length() > 0 ? sp.getKhonhan() : null) + ", '" + this.dungsai + "','" + ptThue + "', '" + thueNK + "', null, N'" + sp.getTenHD() + "', '"+ thuexuat+"','"+tienvat+"')";

				if (!db.update(query))
				{
					this.msg = "Khong the tao moi Tra hang - San pham: " + query;
					
					System.out.println("5.Exception tai day: " + query);
					db.getConnection().rollback();
					return false;
				}		
				
				
			/*	if(this.lhhId.trim().equals("0"))
				{
					// số lượng giữ nguyên, tăng booked, giảm avai
					 
					double vailablect= (-1)* Double.parseDouble(sp.getSoluong().replaceAll(",",""));
					double booked= Double.parseDouble(sp.getSoluong().replaceAll(",",""));
					
					String msg1 = util_kho.Update_Kho_Sp(this.db,  sp.getKhonhan(), SanPham_FK,0, booked,vailablect, 0);
					
					if(msg1.length()>0)
					{
						this.msg =msg1;
						db.getConnection().rollback();
						return false;
					}
				}*/
				if(this.sanpham_soluong != null)
				{
					Enumeration<String> keys = this.sanpham_soluong.keys();
					double totalCT = 0;
					
					while(keys.hasMoreElements())
					{
						String key = keys.nextElement();
						
						if(key.startsWith( sp.getMasanpham()  ) )
						{
							String[] _sp = this.mySplit( key, "__" );
							
							String _soluongCT = "0"; 
							if(this.sanpham_soluong.get(key) != null)
							{
								_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
							}
							
							if(_soluongCT.trim().length() >0 &&  Double.parseDouble(_soluongCT.replaceAll(",", "")) !=0)
							{
								//QUY VỀ SỐ LƯỢNG CHUẨN LƯU KHO
								
								double soluongCHUAN = Double.parseDouble(_soluongCT.replaceAll(",", ""));
								
								
								//Bên đầu bán, lúc nhập chỉ có số lô, ngày hết hạn, vị trí, các thông số khác sẽ đề xuất chỗ này
								List<ErpThongtinkho> chitiet = util.DeXuatKho_TRAHANG_NEW(db, this.ngaymuahang, "1", sp.getKhonhan(), sp.getMasanpham(), _sp[1],  _sp[2], _sp[3], _sp[4], _sp[5], _sp[6], _sp[7], _sp[8], _sp[9], _sp[10], _sp[11], soluongCHUAN );
								if( chitiet == null )
								{
									this.msg = "Lỗi đề xuất sản phẩm, vui lòng liên hệ admin để xử lý.";
									db.getConnection().rollback();
									return false;
								}
								
								totalCT += Double.parseDouble(_soluongCT);
								
								for( int j = 0; j < chitiet.size(); j++  )
								{
									ErpThongtinkho tt = chitiet.get(j);
									
									
									query = "insert ERP_MUAHANG_SP_CHITIET( KHOTT_SP_CT_FK,MUAHANG_FK, SANPHAM_FK, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, phieudt, phieueo, soluong,TIENHANG,TIENVAT,TIENHANGSAUVAT ,NSX_FK) " +
											"select "+sp.getKhonhan()+",'" + this.id + "', '" + tt.spId + "', N'" + tt.solo + "', '" + tt.ngayhethan + "', '" + tt.ngaynhapkho + "', '" + tt.mame + "', '" + tt.mathung + "', N'" + tt.maphieu + "', '" + tt.MARQ + "', '" + tt.hamluong + "', '" + tt.hamam + "', '" + tt.vitriId + "', '" + tt.phieudt + "', '" + tt.phieueo + "' , '" + tt.soluong + "' "+
											" ,ROUND(("+ tt.soluong+" * "+sp.getDongia().replaceAll(",", "")+"),0) AS TIENHANG, ROUND(((ROUND(("+ tt.soluong+" * "+sp.getDongia().replaceAll(",", "")+"),0))*("+thuexuat+")/100),0) AS TIENVAT"+
											", (ROUND(("+ tt.soluong+" * "+sp.getDongia().replaceAll(",", "")+"),0) +ROUND(((ROUND(("+ tt.soluong+" * "+sp.getDongia().replaceAll(",", "")+"),0))*("+thuexuat+")/100),0) )AS TIENHANGSAUVAT ,"+tt.nsxId+" ";
									
									System.out.println("1.2.Insert ERP_DONDATHANG_SANPHAM_CHITIET: " + query);
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi ERP_DONDATHANG_SANPHAM_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									//CẬP NHẬT KHO
									
									
									/*this.msg =util.Update_KhoTT_Chitiet(this.ngaymuahang, "Đơn trả hàng ncc", db,  tt.khoId, tt.spId,
											tt.solo, tt.ngayhethan, tt.ngaynhapkho, tt.mame, tt.mathung,  tt.vitriId, tt.maphieu, tt.phieudt, tt.phieueo, tt.MARQ, tt.hamluong,  tt.hamam,"", "", 0,  tt.soluong,  -1 * tt.soluong, 0, "");
									if( this.msg.trim().length() > 0 )
									{
										db.getConnection().rollback();
										return false;
									}
									String querycheck = "SELECT ISNULL(BOOKED,0) as BOOKED FROM ERP_KHOTT_SP_CHITIET WHERE KHOTT_FK = '"+ tt.khoId+"' and SANPHAM_FK ='"+ tt.spId+"' and isnull(SOLO,'') = '"+ tt.solo+"' \n"+
									"   and NGAYNHAPKHO = '"+ tt.ngaynhapkho+"' and NGAYHETHAN = '"+ tt.ngayhethan+"' and isnull(MAME,'') = '"+ tt.mame+"'\n"+
									"   and isnull(MAPHIEU,'') = '"+ tt.maphieu+"' and isnull(MATHUNG,'') = '"+ tt.mathung+"' and isnull(MAPHIEUDINHTINH,'') = '"+ tt.phieudt+"' \n"+
									"   and isnull(PHIEUEO,'') = '"+ tt.phieueo+"' and isnull(HAMAM,0) ='"+ tt.hamam+"' and isnull(HAMLUONG,100)  ='"+ tt.hamluong+"' and isnull(BIN_FK,0) = '"+ tt.vitriId+"' \n"+
									" \n";
									ResultSet rscheck = db.get(querycheck);
									if(rscheck.next())
									{
										if(rscheck.getDouble("BOOKED") <tt.soluong)
										{
											this.msg ="Lỗi trong quá trình booked kho. Vui lòng kiểm tra lại hoặc liên hệ admin để xửa lý";
											db.getConnection().rollback();
											return false;
										}
									}
									rscheck.close();*/
								}
							}
						}
					}
					
					//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
					if(DinhDang.dinhdangso(totalCT) != DinhDang.dinhdangso(Double.parseDouble(sp.getSoluong().replaceAll(",", "").trim())) )
					{
						
						this.msg = "Tổng xuất theo lô của sản phẩm ( " + sp.getTensanpham() + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " +sp.getSoluong()+ " ) ";
						db.getConnection().rollback();
						return false;
					}
				}
				
			}
			if(this.lhhId.trim().equals("0"))
			{
				//số lượng giữ nguyên, tăng booked, giảm avai
				query="   SELECT A.NSX_FK,A.MARQ,A.SANPHAM_FK, A.SOLO,B.KHOTT_FK as KHO_FK,A.SOLUONG,A.MARQ,A.MATHUNG,A.MAME,A.NGAYHETHAN,A.NGAYNHAPKHO ,ISNULL(A.MAPHIEU,'') AS MAPHIEU,ISNULL(A.PHIEUDT,'') AS MAPHIEUDINHTINH, "
						 +"\nISNULL(A.PHIEUEO,'') AS PHIEUEO , ISNULL(A.HAMLUONG,100) AS HAMLUONG ,ISNULL(A.HAMAM,0) AS HAMAM ,ISNULL(A.BIN_FK,0) AS BIN_FK"
						 +"\nfrom ERP_MUAHANG_SP_CHITIET A inner join ERP_MUAHANG_SP B on A.MUAHANG_FK=B.MUAHANG_FK and A.SANPHAM_FK=B.SANPHAM_FK"
						 +"\ninner join ERP_MUAHANG C on C.PK_SEQ=B.MUAHANG_FK"
						 +"\nwhere C.PK_SEQ='"+this.id+"' and C.TRANGTHAI=0";
				
				ResultSet rs=db.get(query);
				while(rs.next()){
					double booked =rs.getDouble("SOLUONG");
					double avai =(-1) *  rs.getDouble("SOLUONG");;
						
					Kho_Lib kholib=new Kho_Lib();
					kholib.setNgaychungtu(this.ngaymuahang);
					kholib.setLoaichungtu("Tao moi book kho ErpCongtytrahang  :"+this.id); 
					kholib.setKhottId(rs.getString("KHO_FK"));
					kholib.setBinId(rs.getString("BIN_FK"));
					kholib.setSolo(rs.getString("SOLO"));
					kholib.setSanphamId(rs.getString("SANPHAM_FK"));
					
					kholib.setMame(rs.getString("MAME"));
					kholib.setMathung(rs.getString("MATHUNG"));
					kholib.setNsxId(rs.getString("NSX_FK")==null?"":rs.getString("NSX_FK"));
					kholib.setMaphieu(rs.getString("MAPHIEU"));
					kholib.setMaphieudinhtinh(rs.getString("MAPHIEUDINHTINH"));
					kholib.setPhieuEo(rs.getString("PHIEUEO"));
					kholib.setNgayhethan(rs.getString("NGAYHETHAN"));
				 
					kholib.setNgaynhapkho(rs.getString("NGAYNHAPKHO"));
					kholib.setBooked( booked);
					
					kholib.setSoluong(0);
					kholib.setAvailable(avai);
					
					kholib.setMARQ(rs.getString("MARQ"));
					kholib.setDoituongId("");
					kholib.setLoaidoituong("");
					 
					kholib.setHamluong(rs.getString("HAMLUONG"));
					kholib.setHamam(rs.getString("HAMAM"));
					kholib.setNgaysanxuat("");
					
					String msg1= util_kho.Update_Kho_Sp_Tra_NEW(db,kholib);
				    if( msg1.length() >0)
					{
				    	this.msg = msg1;
				    	System.out.println(" cau loi kho : "+ this.msg);
				    	db.getConnection().rollback();
						return false;
					}
				
				
			}
				
			    rs.close();
			}
			
			
			
			// CAI DUYET PO
						System.out.println("loai don " + this.lhhId );
						// PO nhập khẩu && PO trong nước (PO tổng dùng để phân bổ)
						if (this.lhhId.equals("0") || (this.lhhId.equals("1"))) {
							// NẾU CÓ CHÍNH SÁCH DUYỆT DÀNH CHO NCC CỦA ĐƠN MUA HÀNG, LẤY CHÍNH SÁCH DUYỆT ĐÓ
							query = CreateChinhSachDuyet();
						}
						// insert nguoi duyet PO : Mua chi phí/TSCD/CCDC lấy trong cơ chế// duyệt
						else {
							query = CreateChinhSachDuyet();
						}

						System.out.println("3.Duyet PO....:" + query);
						ResultSet rs = db.get(query);
						int bien_dem=0;
						while (rs.next()) {
							bien_dem++;
							query = "if not exists (select * from ERP_DUYETMUAHANG where CHUCDANH_FK = "
									+ rs.getString("CHUCDANH_FK") + " AND MUAHANG_FK = " + this.id + ")"
									+ "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH) " + "VALUES('"
									+ this.id + "', '" + rs.getString("CHUCDANH_FK") + "', '0','" + rs.getString("QUYETDINH")
									+ "') ";

							System.out.println("4. Insert Duyet PO:" + query);
							if (!db.update(query)) {
								this.msg = "Khong the them nguoi duyet cho PO: " + query;
								db.getConnection().rollback();
								return false;
							}

						} 
						
						/*if(bien_dem==0){
							query = "update ERP_MUAHANG set trangthai = 1 where pk_seq = '" + this.id + "'";
							if (!db.update(query)) {
								this.msg = "Khong the cap nhat ERP_MUAHANG: " + query;
								db.getConnection().rollback();
								return false;
							}
							rs.close();
							
						}*/
						// CAI DUYET PO
			
 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.db.update("rollback");
			this.msg = "Khong the cap nhat don hang " + query;
			e.printStackTrace();
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

	public String getKhoId() 
	{
		return this.khoId;
	}

	public void setKhoId(String khoId)
	{
		this.khoId = khoId;
	}

	public String getNccTen() {
	
		return this.nccTen;
	}

	public void setNccTen(String nccTen) {
		
		this.nccTen = nccTen;
	}

	public String getNccLoai() {
		
		return this.nccLoai;
	}

	public void setNccLOai(String nccLoai) {
		
		this.nccLoai = nccLoai;
	}

	
	public String getCanDuyet() {
		
		return this.canduyet;
	}

	
	public void setCanDuyet(String canduyet) {
		
		this.canduyet = canduyet;
	}

	
	public void initLSIN(String inHd) {
		
		
	}

	
	public String CreateLSIN(String dthId) {
		
		return null;
	}
	
	public String getQuyDoiStr() {
		return this.quydoiStr;
	}

	
	public void setQuyDoiStr(String quydoi) {
		this.quydoiStr  = quydoi;
	}

	
	public String getLydotrahang() {
		
		return this.lydotrahang;
	}

	
	public void setLydotrahang(String lydotrahang) {
		
		this.lydotrahang = lydotrahang;
	}
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}
	
	public Hashtable<String, String> getSanpham_SoluongDAXUAT_THEODN() 
	{
		return this.sanpham_soluongDAXUAT;
	}


	public void setSanpham_SoluongDAXUAT_THEODN(Hashtable<String, String> sp_soluong) 
	{
		this.sanpham_soluongDAXUAT = sp_soluong;
	}
	private String[] mySplit(String key, String operator) 
	{
		boolean exit = false;
		if( key.endsWith(operator) )
		{
			key = key + " ";
			exit = true;
		}
		
		String[] arr = key.split(operator);
		if( arr.length > 0 && exit )
			arr[ arr.length - 1 ] = arr[ arr.length - 1 ].trim();
		
		return arr;
	}
	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong,String khoid)
	{
		tongluong = tongluong.replaceAll(",", "");
		double sum = Double.parseDouble(tongluong);
		System.out.println("---TONG LUONG: " + tongluong );//5.8
		
		String soloDACHON = "";
		if( this.sanpham_soluongDAXUAT != null )
		{
			//KEY: MA - SOLO; 
			Enumeration<String> keys = this.sanpham_soluongDAXUAT.keys();
			while( keys.hasMoreElements() )
			{
				String key = keys.nextElement();
				System.out.println("kEY======: " + key );
				if(key.startsWith(spMa))
				{
					String[] arr = this.mySplit(key, "__" );
					
					String solo = arr[1];
					String ngayhethan = arr[2];
					String vitri = arr[3];
					String mathung = arr[4];
					String mame = arr[5];
					String maphieu = arr[6];
					String hamluong = arr[7];
					String hamam = arr[8];
					String ngaynhapkho = arr[9];
					String marq = arr[10];
					String nsx_fk = arr[11];
					String daxuat =  this.sanpham_soluongDAXUAT.get(key);
					sum = sum - Double.parseDouble(daxuat);
					if( daxuat.trim().length() > 0 && Double.parseDouble(daxuat.replaceAll(",", "")) !=0)
						soloDACHON += "select '" + solo + "' as soloCHON, '" + ngayhethan + "' as ngayhethanCHON, N'" + vitri + "' as vitriCHON, '" + mathung + "' as mathungCHON, '" + mame + "' as mameCHON,'" + maphieu + "' as maphieuCHON, " + daxuat + " as soluongDACHON, " + hamluong + " as hamluong, " + hamam + " as hamam,'"+ngaynhapkho+"' as ngaynhapkho, '" + marq + "' as marq,'"+nsx_fk+"' as nsx_fk union ";
				}
			}
		}
		tongluong = sum + "";
		if( soloDACHON.trim().length() > 0 )
		{
			soloDACHON = soloDACHON.substring(0, soloDACHON.length() - 7 );
			soloDACHON = " select soloCHON, ngayhethanCHON, vitriCHON, mathungCHON, mameCHON,maphieuCHON, SUM( soluongDACHON ) as soluongDACHON,hamluong,hamam,ngaynhapkho,marq,nsx_fk " +
						 " from ( " + soloDACHON + " ) DT group by soloCHON, ngayhethanCHON, vitriCHON, mathungCHON, mameCHON,maphieuCHON,hamluong,hamam,ngaynhapkho,marq,nsx_fk ";
		}
		else
			soloDACHON = " select '1' as soloCHON, '' as ngayhethanCHON, '' as vitriCHON, '' mathungCHON, '' mameCHON, '' maphieuCHON,0 as soluongDACHON,'100' hamluong,'0' hamam,'' as ngaynhapkho,'' as marq,'0' as nsx_fk ";
		
		
		String sqlDONHANG = "";
		if( this.id.trim().length() > 0 )
			sqlDONHANG = " select SUM(soluong) from ERP_MUAHANG_SP_CHITIET where MUAHANG_FK = '" + this.id + "' and SANPHAM_FK = DT.sanpham_fk and solo = DT.solo and ngayhethan = DT.ngayhethan and isnull( bin_fk, 0 ) = isnull( DT.bin_fk, 0 ) and mathung = DT.mathung and mame = DT.mame and maphieu = DT.maphieu  AND HAMLUONG = DT.HAMLUONG AND HAMAM = DT.HAMAM AND ngaynhapkho = DT.ngaynhapkho AND ISNULL(MARQ,'') = ISNULL(DT.MARQ,'') AND ISNULL(NSX_FK,0) = ISNULL(DT.nSX_FK,0) ";
		else
			sqlDONHANG = " select SUM(soluong) from ERP_MUAHANG_SP_CHITIET where MUAHANG_FK = '1' and SANPHAM_FK = DT.sanpham_fk and solo = DT.solo and ngayhethan = DT.ngayhethan and isnull( bin_fk, 0 ) = isnull( DT.bin_fk, 0 ) and mathung = DT.mathung and mame = DT.mame and maphieu = DT.maphieu  AND HAMLUONG = DT.HAMLUONG AND HAMAM = DT.HAMAM AND ngaynhapkho = DT.ngaynhapkho AND ISNULL(MARQ,'') = ISNULL(DT.MARQ,'') AND ISNULL(NSX_FK,0) = ISNULL(DT.nSX_FK,0) ";
		
	/*	String conditionKHONGDUOCPB = " select b.solo from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk and b.sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) " +
							  		  " where a.TRANGTHAI = '1' and a.tungay <= '" + this.ngaymuahang + "' and '" + this.ngaymuahang + "' <= a.denngay  ";
		
		String conditionDUOCPB = "  select b.solo from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk and b.sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) " +
				  				 " 	where a.TRANGTHAI = '1' and a.tungay <= '" + this.ngaymuahang + "' and '" + this.ngaymuahang + "' <= a.denngay  ";
		*/
		String query = "";
		
		query =  "\n select  distinct 0 vt, DT.SOLO, DT.NGAYHETHAN, DT.MATHUNG, DT.MAME,DT.HAMLUONG,DT.HAMAM,DT.ngaynhapkho, DT.MAPHIEU,isnull(bin.MA, '') as vitri, " + 
				 "\n ISNULL(DT.MARQ,'') AS MARQ,ISNULL(DT.NSX_FK,0) AS NSX_FK,ISNULL(NSX.MA+'-'+NSX.TEN,'') AS NSXTEN, "+
				 "\n DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) as AVAILABLE, '' as tudexuat  "+
				 "\n from "+
				 "\n ( "+
				 "\n 	select ct.sanpham_fk, ct.SOLO, ct.NGAYHETHAN, ct.mathung, ct.mame,isnull(ct.hamluong,'100') as hamluong,isnull(ct.hamam,0) as hamam,ct.ngaynhapkho, ct.maphieu, isnull( ct.bin_fk, 0 ) as bin_fk, sum(ct.AVAILABLE) as AVAILABLE, ISNULL(ct.MARQ,'') as MARQ,  ISNULL(ct.NSX_FK,0) AS  NSX_FK   "+
				 "\n 	from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
				 "\n 	where KHOTT_FK = '" + khoid + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
				 		"  and ngaynhapkho <= '" + this.ngaymuahang + "' "+
				 		"  and ( isnull(sp.batbuockiemdinh, 0) = 0 or ct.KHOTT_FK in (100074, 100078) or ( sp.batbuockiemdinh = 1 and isnull( maphieu, '' ) != ''  ) )		" +
				 "\n	group by ct.sanpham_fk, ct.SOLO, ct.NGAYHETHAN,  ct.mathung, ct.mame,ct.ngaynhapkho,ct.hamluong,ct.hamam, ct.maphieu, isnull( ct.bin_fk, 0 ), ISNULL(ct.MARQ,'') ,  ISNULL(ct.NSX_FK,0)	" +
				 "\n ) "+
				 "\n DT left join ERP_BIN bin on DT.bin_fk = bin.pk_seq    "+
				 "\n  left join ERP_NHASANXUAT NSX on DT.NSX_FK = NSX.pk_seq left join  "+
				 "\n ( "+
				 	soloDACHON +
				 "\n ) "+
				 "\n DAXUAT on DT.SOLO = DAXUAT.soloCHON  and DT.NGAYHETHAN = DAXUAT.ngayhethanCHON and isnull( bin.ma, '' ) = DAXUAT.vitriCHON  "+
				 "\n		and DT.MATHUNG = DAXUAT.mathungCHON and DT.MAME = DAXUAT.mameCHON	and DT.MAPHIEU = DAXUAT.maphieuCHON and DT.HAMLUONG = DAXUAT.HAMLUONG AND DAXUAT.HAMAM = DT.HAMAM AND DAXUAT.NGAYNHAPKHO = DT.NGAYNHAPKHO AND ISNULL(DAXUAT.MARQ,'') = ISNULL(DT.MARQ,'') AND ISNULL(DAXUAT.NSX_FK,0) = ISNULL(DT.nSX_FK,0) " +
				 
				 //"\n where DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - isnull(DAXUAT.soluongDACHON, 0) > 0 "+
				 "\n where DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) > 0 "+
				 "\n			and DT.SOLO not in ( select solo from ERP_HANGCHOPHANBO where sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) )	" +
				// "\n 			and ( DT.SOLO not in ( " + conditionKHONGDUOCPB + " ) or DT.SOLO in ( " + conditionDUOCPB + " ) )	" +
				 "\n order by NGAYHETHAN asc  ";
		
		System.out.println("----LAY SO LO ( " + spMa + " ): " + query );
		System.out.println("tongluong "+tongluong);
		String query2 = "";
		ResultSet rs = db.get(query);
		try 
		{
			double total = 0;
			while(rs.next())
			{
				double slg = 0;
				double avai = rs.getDouble("AVAILABLE");
				
				total += avai;
				
				if(total < Double.parseDouble(tongluong))
				{
					slg = avai;
				}
				else
				{
					slg =  Double.parseDouble(tongluong) - ( total - avai );
				}
				System.out.println("so luong de xuat "+ slg);
					
				if(slg >= 0)
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '" + slg + "' as tuDEXUAT,'" + rs.getString("MATHUNG") + "' as MATHUNG,'" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MAPHIEU") + "' as maphieu,'" + rs.getString("HAMLUONG") + "' as HAMLUONG,'" + rs.getString("HAMAM") + "' as HAMAM, '" + rs.getString("NGAYNHAPKHO") + "' as NGAYNHAPKHO,'" + rs.getString("MARQ") + "' as MARQ, '" + rs.getString("NSX_FK") + "' as NSX_FK, N'" + rs.getString("NSXTEN") + "' as NSXTEN union ALL ";
					 
				}
				else
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '' as tuDEXUAT,'" + rs.getString("MATHUNG") + "' as MATHUNG,'" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MAPHIEU") + "' as maphieu,'" + rs.getString("HAMLUONG") + "' as HAMLUONG,'" + rs.getString("HAMAM") + "' as HAMAM, '" + rs.getString("NGAYNHAPKHO") + "' as NGAYNHAPKHO,'" + rs.getString("MARQ") + "' as MARQ, '" + rs.getString("NSX_FK") + "' as NSX_FK, N'" + rs.getString("NSXTEN") + "' as NSXTEN  union ALL ";
				}
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
		}
		
	
		if(query2.trim().length() > 0)
		{
			query2 = query2.substring(0, query2.length() - 10);
			System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
			return db.get(query2);
		}
		
		return null;
	}
	
}
