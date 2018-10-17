package geso.traphaco.erp.beans.denghimuahang.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.donmuahang.IDonvi;
import geso.traphaco.erp.beans.donmuahang.IKho;
import geso.traphaco.erp.beans.donmuahang.INgaynhan;
import geso.traphaco.erp.beans.donmuahang.ISanpham;
import geso.traphaco.erp.beans.donmuahang.ITiente;
import geso.traphaco.erp.beans.donmuahang.ITrungTamChiPhi;
import geso.traphaco.erp.beans.donmuahang.imp.*;
import geso.traphaco.erp.beans.denghimuahang.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rp.util.DateTime;

public class ErpDenghimuahang implements IErpDenghimuahang
{
	String congtyId;
	String nppId;
	String userId;
	String ctyId;
	String cty;
	String id;
	String hinhThucTT = "", diadiemgiaohang = "";
	public String getHinhThucTT() {
		return hinhThucTT;
	}

	public void setHinhThucTT(String hinhThucTT) {
		this.hinhThucTT = hinhThucTT;
	}

	// Them cot Nguon Goc Hang Hoa,TienTe_FK,mot don mua hang chi thuoc 1 loai
	// tien te
	String NguonGocHH;
	String MaLoaiHH;
	String TienTe_FK;
	String GhiChu;
	String Congvan;
	String ThueNhapKhau;
	String PhanTramThue;
	String TrungTamChiPhi_FK;
	float TyGiaQuyDoi;
	// Them cot Nguon Goc Hang Hoa,TienTe_FK
	String ngaydenghi;
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
	String maDMH;
	
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
	String khoId;
	String canduyet;
	String quanlyCN;
	
	String sothamchieu;
	String[] ghichuArr;
	
	String[] cpkDiengiai;
	String[] cpkSotien;
	
	String checkedNoiBo;
	
	String Duyetdnmh;
	

	public String getCheckedNoiBo() {
		return checkedNoiBo;
	}

	public void setCheckedNoiBo(String checkedNoiBo) {
		this.checkedNoiBo = checkedNoiBo;
	}

	
	dbutils db;
	
	private Utility util;

	public ErpDenghimuahang()
	{
		this.userId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = "";
		this.ngaydenghi = "";
		this.dvthId = "";
		this.nccId = "";
		this.nccTen = "";
		this.nccLoai = "";
		this.trangthai = "";
		this.BVAT = "";
		this.VAT = "10";
		this.sochungtu = "";
		this.AVAT = "";
		this.lhhId = "";
		this.msg = "";
		this.dungsai = "10";
		this.NguonGocHH = "";
		this.MaLoaiHH = "";
		this.TienTe_FK = "";
		this.GhiChu = "";
		this.Congvan = "";
		this.maDMH="";
		
		this.TyGiaQuyDoi = 1;
		this.spList = new ArrayList<ISanpham>();
		this.dvList = new ArrayList<IDonvi>();
		this.tienteList = new ArrayList<ITiente>();
		this.khoList = new ArrayList<IKho>();
		this.ListTTCP = new ArrayList<ITrungTamChiPhi>();
		
		this.checkedNoiBo = "0";
		this.lspId = "";
		//0 Phiếu thanh toán - 1 ĐƠN MUA HÀNG
		this.isdontrahang = "1";
		this.maketoStock = "0";
		this.khoId = "";
		this.canduyet = "1";
		this.quanlyCN = "1";
		this.sothamchieu = "";
		this.Duyetdnmh="";
		this.db = new dbutils();
		this.util=new Utility();
	}

	public ErpDenghimuahang(String id)
	{
		this.userId = "";
		this.nppId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = id;
		this.ngaydenghi = "";
		this.dvthId = "";
		this.nccId = "";
		this.nccTen = "";
		this.nccLoai = "";
		this.trangthai = "";
		this.BVAT = "";
		this.sochungtu = "";
		this.VAT = "10";
		this.AVAT = "";
		this.lhhId = "";
		this.msg = "";
		this.dungsai = "10";
		this.MaLoaiHH = "";
		this.NguonGocHH = "";
		this.TienTe_FK = "";
		this.GhiChu = "";
		this.Congvan = "";
		this.maDMH="";
		
		this.TyGiaQuyDoi = 1;
		this.spList = new ArrayList<ISanpham>();
		this.dvList = new ArrayList<IDonvi>();
		this.tienteList = new ArrayList<ITiente>();
		this.khoList = new ArrayList<IKho>();
		this.ListTTCP = new ArrayList<ITrungTamChiPhi>();
		
		this.checkedNoiBo = "0";
		this.lspId = "";
		this.isdontrahang = "0";
		this.maketoStock = "0";
		this.khoId = "";
		this.canduyet = "1";
		this.quanlyCN = "1";
		this.sothamchieu = "";
		this.Duyetdnmh="";
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

	public String getNgaydenghi()
	{
		return this.ngaydenghi;
	}

	public void setNgaydenghi(String ngaydenghi)
	{
		this.ngaydenghi = ngaydenghi;
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
		/*if (this.VAT.length() == 0)
			this.VAT = "10";*/
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
		String sql="";
		if(this.userId.equals("100002")){
			 sql=" select distinct A.pk_seq AS PK_SEQ, A.ten AS TEN   "+    
			 "\n  from ERP_DONVITHUCHIEN  A inner join NHANVIEN NV ON NV.PHONGBAN_FK=A.PK_SEQ  "+    
			 "\n  where A.trangthai = '1' ";
		}
		else
		{
			 sql=" select distinct A.pk_seq AS PK_SEQ, A.ten AS TEN   "+    
			 "\n  from ERP_DONVITHUCHIEN  A inner join NHANVIEN NV ON NV.PHONGBAN_FK=A.PK_SEQ  "+    
			 "\n  where A.trangthai = '1' and "+
			 "\n  NV.PHONGBAN_FK in ( select  distinct PHONGBAN_FK from NHANVIEN where PK_SEQ ="+this.userId+" )";
		}
		
				   
		// and congty_fk = '" + this.congtyId + "' 
		
		System.out.println("Get Dvkd : "+sql);
		this.dvthRs = db.get(sql);
		
		this.lhhRs = db.get("Select pk_seq, ten, ma From Erp_LoaiSanPham where TRANGTHAI = '1' and congty_fk = '" + this.congtyId + "' ");
		
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
		
		/*String query = " select ERP_TIENTE.PK_SEQ, ERP_TIENTE.MA, ERP_TIGIA.TIGIAQUYDOI " +
				" from ERP_TIENTE inner join ERP_TIGIA on ERP_TIENTE.PK_SEQ = ERP_TIGIA.TIENTE_FK " +
				" where ERP_TIENTE.Trangthai = '1' and ERP_TIGIA.Trangthai = '1' " +
				" order by ERP_TIENTE.PK_SEQ ASC";*/
		String query ="";
		if( this.NguonGocHH.equals("TN")){ // lay tien viet nam
			query = " select ERP_TIENTE.PK_SEQ, ERP_TIENTE.MA " +
			" from ERP_TIENTE  " +
			" where ERP_TIENTE.Trangthai = '1' " +
			" and ERP_TIENTE.pk_seq=100000 ";
		}
		else //lay tien ngoai
		{
			query = " select ERP_TIENTE.PK_SEQ, ERP_TIENTE.MA " +
			" from ERP_TIENTE  " +
			" where ERP_TIENTE.Trangthai = '1' " +
			" and ERP_TIENTE.pk_seq <> 100000 ";
		}
		
	
		System.out.println("tien te " + query);
		ResultSet tiente = db.get(query);
		this.tienteList.clear();
		if (tiente != null)
		{
			try
			{
				while (tiente.next())
				{
					this.tienteList.add(new Tiente(tiente.getString("pk_seq"), tiente.getString("ma"), ""));
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
		
		System.out.println(" vao day 2");
		NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
		
		String query = " select a.PK_SEQ as dmhId, isnull(a.NguonGocHH ,'') as NguonGocHH, isnull(a.TienTe_FK, '100000') as TienTe_FK, " +
				" a.NGAYdenghi, isnull(a.GhiChu,'') as GhiChu, isnull(a.congvan,'') as congvan, " +
				" a.DONVITHUCHIEN_FK as dvthId, a.LOAIHANGHOA_FK, a.LOAISANPHAM_FK, a.TRANGTHAI, a.sodenghi " +
				"  from ERP_denghiMUAHANG a " +
				"  inner join ERP_DONVITHUCHIEN c on c.PK_SEQ = a.DONVITHUCHIEN_FK  " +
				" where a.pk_seq = '" + this.id + "' " ;
		
		System.out.println("Don Mua Hang : " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.maDMH = rs.getString("sodenghi");
					this.id = rs.getString("dmhId");
					this.ngaydenghi = rs.getString("ngaydenghi");
					this.dvthId = rs.getString("dvthId");
					
					this.lhhId = rs.getString("LOAIHANGHOA_FK")== null ? "" : rs.getString("LOAIHANGHOA_FK");
					this.lspId = rs.getString("LOAISANPHAM_FK")== null ? "" : rs.getString("LOAISANPHAM_FK");
					
					this.trangthai = rs.getString("trangthai");
					
					this.NguonGocHH = rs.getString("NguonGocHH");
					this.TienTe_FK = rs.getString("TienTe_FK");
					this.GhiChu = rs.getString("GhiChu");
					this.Congvan = rs.getString("congvan");
					
					
					if(this.GhiChu.trim().length() > 0)
					{
						this.ghichuArr = this.GhiChu.split("__");
					}
					
					
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

		String query =  
				  "select  	isnull(b.pk_seq,0) as spid,  isnull(b.ma, '' ) as spMa,  isnull(a.diengiai, b.ten )   as spTen,   \n"
				+ "			b.ten    as spTen2, 'NA' as spNh,  isnull(tscd.pk_seq,0) as tscdid, isnull(tscd.ma, '') as tscdMa, isnull(a.diengiai, tscd.ten) as tscdTen, \n"
				+ "			'NA' as nstNh,   isnull(ccdc.pk_seq,0) as ccdcid, isnull(ccdc.ma, '') as ccdcMa, isnull(a.diengiai, ccdc.DIENGIAI) as ccdcTen,   \n"
				+ "			isnull(ncp.pk_seq,0) as ncpid,isnull(ncp.ten, '') as ncpMa, isnull(a.diengiai, ncp.diengiai) as ncpTen, isnull(ttcp.diengiai, 'NA') as ncpNh,  \n"
				+ "			isnull(a.donvi, '') as donvi, a.soluong, a.soluongduyet "
				+ "from 	(select AVG(PK_SEQ) AS PK_SEQ, denghi_FK, SANPHAM_FK, CHIPHI_FK, \n"
				+ "					TAISAN_FK, DIENGIAI, SUM(SOLUONG) AS SOLUONG, SUM(SOLUONGDUYET) AS SOLUONGDUYET, DONVI, CCDC_FK	\n"
				+ "			from 	ERP_denghiMUAHANG_SP WHERE denghi_FK = '"+this.id+"' 	\n"
				+ "			GROUP BY denghi_FK, SANPHAM_FK, CHIPHI_FK, TAISAN_FK, DIENGIAI, DONVI, CCDC_FK \n"
				+ "			) a left join  ERP_SANPHAM b on a.sanpham_fk = b.pk_seq    \n"
				+ "			left join	ERP_MASCLON tscd on a.taisan_fk = tscd.pk_seq   \n"
				+ "			left join	erp_congcudungcu ccdc on a.ccdc_fk = ccdc.pk_seq   \n"
				+ "			left join erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq  \n"
				+ "			left join  erp_trungtamchiphi ttcp on ncp.ttchiphi_fk = ttcp.pk_seq   \n"
				+ "where denghi_fk = '"+this.id+"' order by a.pk_seq asc ";
		
		System.out.println(" San pham init: " + query);
		ResultSet spRs = db.get(query);
		List<ISanpham> spList = new ArrayList<ISanpham>();
		
		NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
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
						sp.setTensanpham(spRs.getString("spTen2"));
						sp.setNhomhang(spRs.getString("spNh"));
						sp.setTenXHD(spRs.getString("spTen2"));
						
						
					}
					else
					{
						if(this.lhhId.equals("1")) //Tai san co dinh
						{
							sp.setPK_SEQ(spRs.getString("tscdid"));
							sp.setMasanpham(spRs.getString("tscdMa"));
							sp.setTensanpham(spRs.getString("tscdTen"));
							sp.setNhomhang(spRs.getString("nstNh"));
							sp.setTenXHD(spRs.getString("tscdTen"));
							
							
						}
						else
						{
							if(this.lhhId.equals("3")) //Cong cu dung cu
							{
								sp.setPK_SEQ(spRs.getString("ccdcId"));
								sp.setMasanpham(spRs.getString("ccdcMa"));
								sp.setTensanpham(spRs.getString("ccdcTen"));
								sp.setNhomhang("");
								sp.setTenXHD(spRs.getString("ccdcTen"));
								
								
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
					}
					
					sp.setSoluong(formatter.format(spRs.getDouble("soluong")));
					sp.setSoluongduyet(formatter.format(spRs.getDouble("soluong")));
					sp.setSoluong_bk(formatter.format(spRs.getDouble("soluong")));
					
					sp.setDonvitinh(spRs.getString("donvi"));

					spList.add(sp);
				}
				spRs.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("Khong the tao san Pham " + e.getMessage());
			}
		}

		this.spList = spList;
		System.out.println("size : "+this.spList.size());
	}
	
	
	private String kiemtratrungma(List <ISanpham> spId){
		String matrung="";
		List <String> trung= new ArrayList<String>();
		try {
			for (int i = 0; i < spId.size()-1; i++) {
				for (int j = i+1; j < spId.size(); j++) {
					ISanpham sp= spId.get(i);
					ISanpham sp1= spId.get(j);
					
					System.out.println(" trung: "+ sp.getId() + "/ "+ sp1.getId());
					
					if(  sp.getPK_SEQ().length()>0 && (sp.getPK_SEQ().equals(  sp1.getPK_SEQ())  ) ){
						if(!trung.contains( sp.getPK_SEQ())){
							trung.add(  sp.getPK_SEQ());
							matrung+=  sp.getMasanpham() +" ; ";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return matrung;
	}
	
	
	public boolean createDmh()
	{
		// Kiem tra moi them vao
		String query = "";
		this.nppId = util.getIdNhapp(userId);
		
		if(this.spList.size() <= 0 && !this.lhhId.equals("1"))
		{
			this.msg = "Vui lòng chọn sản phẩm";
			return false;
		}
		
		if(this.TienTe_FK.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn tiền tệ của đơn mua hàng";
			return false;
		}
		
		 if (this.getNguonGocHH().equals("TN")){
			 
			 if(!this.TienTe_FK.equals("100000")){

					this.msg = "Vui lòng chọn tiền VND đối với mua hàng trong nước";
					return false;
			 }

		 }else if(this.getNguonGocHH().equals("NN")){
			 if( this.TienTe_FK.equals("100000")){

					this.msg = "Vui lòng chọn tiền khác VND đối với mua hàng ngoài nước";
					return false;
			 }
		 }
		 
		if(this.GhiChu.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ghi chú";
			return false;
		}
		
		if(this.Congvan.trim().length() <= 0)
		{
			this.Congvan = "";
		}
		
		
		// kiem tra trung dong
		String sms= this.kiemtratrungma(this.spList);
		System.out.println( "kiem tra trung dong "+sms);
		if( sms.length()>0 ){
			this.msg = "Vui lòng kiểm tra trùng dòng hàng tại :"+ sms;
			return false;
		}
		
		
		try
		{
			String ngaytao = getDateTime();
			db.getConnection().setAutoCommit(false);
			
			String loaisanpham = "NULL";
			
			query = " Insert into Erp_DeNghiMuaHang(Ngaydenghi, DonViThucHien_FK, LoaiHangHoa_FK, LoaiSanPham_FK," +
					" TrangThai, NgayTao, NgaySua, NguoiTao, NguoiSua, NguonGocHH, TienTe_FK, GhiChu, " +
					" congty_fk, npp_fk, dachot, istruongbpduyet, congvan)" +
					" Values('" + this.ngaydenghi + "','" + this.dvthId + "'," + this.lhhId + ", " + loaisanpham + 
					", '0', '" + ngaytao + "', '" + ngaytao + "'," + this.userId + "," + this.userId + 
					",'" + this.NguonGocHH + "'," + this.TienTe_FK + ",N'" + this.GhiChu.replaceAll("'", "''") + "'," +
					"'" + this.congtyId + "',null, 0, 0, N'"+this.Congvan+"')";
			
			System.out.println("Insert into Erp_MuaHang " + query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi de nghi mua hang: " + query;
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
			
			String tooltip = "NULL";
			String table = "";
			double tongtien = 0;
			double tongtienck = 0;
			double tongvat = 0;
			double tongsauck = 0;
			double tongsauvat = 0;
			tooltip = "Số đề nghị: "+ this.id +"  </br> Ngày đề nghị: " + this.ngaydenghi;
			table = "</br><table><tr><th style=width: 10% >Mã SP</th><th style=width: 10% >Tên SP</th><th style=width: 10% >Số lượng</th></tr>";
			
			query = "update Erp_DeNghiMuaHang set sodenghi = '"+this.id+"' where pk_seq = '"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi de nghi mua hang: " + query;
				System.out.println("2.Exception tai day: " + query);
				db.getConnection().rollback();
				return false;
			}
			
			//Neu la chi phi, xem xem co vuot ngan sach khong
			for (int i = 0; i < this.spList.size(); i++)
			{
				ISanpham sp = this.spList.get(i);

				String SanPham_FK = "NULL";
				String ChiPhi_FK = "NULL";
				String TaiSan_FK = "NULL";
				String CCDC_FK = "NULL";
				
				if (this.lhhId.equals("0"))
				{
					//query = "select pk_seq from ERP_SanPham where MA = '" + sp.getMasanpham() + "' and pk_seq=" + sp.getPK_SEQ();
					SanPham_FK = sp.getPK_SEQ();					
					if(SanPham_FK == null || SanPham_FK.trim().length() == 0) {
						SanPham_FK = "NULL";
					}
				}
				else
				{
					if(this.lhhId.equals("1"))  //Tai san co dinh
					{
					
						TaiSan_FK = sp.getPK_SEQ();
						if(TaiSan_FK == null || TaiSan_FK.trim().length() == 0 || TaiSan_FK.equals("null") ) {
							TaiSan_FK = "0";
						}
						
					}
					else
					{
						if(this.lhhId.equals("3"))  //CONG CU DUNG CU
						{
							CCDC_FK = sp.getPK_SEQ();							
							if(CCDC_FK == null || CCDC_FK.trim().length() == 0 || CCDC_FK.equals("null")) {
								CCDC_FK = "0";
							}
						}
						else  //Chi phi dich vu
						{
							
							ChiPhi_FK = sp.getPK_SEQ();
							if(ChiPhi_FK == null || ChiPhi_FK.trim().length() == 0 || ChiPhi_FK.equals("null")) {
								ChiPhi_FK = "0";
							}
						}
					}
				}
				
				if( this.lhhId.equals("0") && SanPham_FK.equals("NULL") && TaiSan_FK.equals("NULL") && CCDC_FK.equals("NULL") ) // && ChiPhi_FK.equals("NULL")
				{
					this.msg = "Vui lòng kiểm tra lại mã sản phẩm / mã tài sản / mã công cụ dụng cụ / mã chi phí trong danh sách sản phẩm bạn nhập.";
					this.db.getConnection().rollback();
					return false;
				}
				
				if(this.lhhId.equals("0")){
					if(SanPham_FK=="NULL" &&  TaiSan_FK=="NULL"  && CCDC_FK =="NULL" && ChiPhi_FK=="NULL" ){
								this.msg = "Vui lòng nhập mã sản phẩm hoặc chi phí của đề nghị";
								db.getConnection().rollback();
								return false;
					}
				}
				
				query = " insert into ERP_DENGHIMUAHANG_SP(denghi_fk, sanpham_fk, taisan_fk, ccdc_fk, chiphi_fk, diengiai, donvi, soluong, soluongduyet) " +
						" values(" + dmhCurrent + ", " + SanPham_FK + ", " + TaiSan_FK + ", " + CCDC_FK + ", " + ChiPhi_FK + ", N'" + sp.getTensanpham() + "', N'" + sp.getDonvitinh() + "', " + sp.getSoluong().trim() + ", " + sp.getSoluong().trim() + ")";
				
				System.out.println("2.Insert Into Erp_DeNghiMuaHang_SP :" + query);
				
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi Mua hang - San pham: " + query;
					
					//System.out.println("5.Exception tai day: " + query);
					db.getConnection().rollback();
					return false;
				}
				table += "<tr><td>"+sp.getMasanpham().trim()+"</td><td>"+sp.getTensanpham()+"</td><td>"+sp.getSoluong()+"</td></tr>"; 
			}
			table += "</table>";
			tooltip += table;
			
			query = "update ERP_Denghimuahang set tooltip = N'"+ tooltip + "' where pk_seq = " + this.id ;
			System.out.println("-- update ERP_Denghimuahang: " + query );
			if(!db.update(query))
			{
				msg = "Lỗi khi cập nhật đơn hàng: " + query;
				db.getConnection().rollback();
				return false;
			}	
			System.out.println("Loại hh "+this.lhhId);
			
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

	public boolean updateDmh()
	{		
		// Kiem tra moi them vao
		String query = "";
		
		 if (this.getNguonGocHH().equals("TN")){
				 if(!this.TienTe_FK.equals("100000")){
	
						this.msg = "Vui lòng chọn tiền VND đối với mua hàng trong nước";
						return false;
				 }
		 }else if(this.getNguonGocHH().equals("NN")){
			 if( this.TienTe_FK.equals("100000")){

					this.msg = "Vui lòng chọn tiền khác VND đối với mua hàng ngoài nước";
					return false;
			 }
		 }
		
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
		
		if(this.TienTe_FK.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn tiền tệ của đơn mua hàng";
			return false;
		}
		if(this.GhiChu.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ghi chú";
			return false;
		}
		if(this.Congvan.trim().length() <= 0)
		{
			this.Congvan = "";
		}
		
		// kiem tra trung dong
		String sms= this.kiemtratrungma(this.spList);
		System.out.println( "kiem tra trung dong "+sms);
		if( sms.length()>0 ){
			this.msg = "Vui lòng kiểm tra trùng dòng hàng tại :"+ sms;
			return false;
		}
		
		
		try
		{
			String ngaysua = getDateTime();
			//String[] ncc = this.nccTen.split(" - ");
			db.getConnection().setAutoCommit(false);
			this.nppId = util.getIdNhapp(userId);
			
			String loaisanpham = "NULL";
			
			System.out.println("Loai hang hoa o day : "+this.lhhId);			
			
			if(this.lhhId.trim().equals("0"))
				loaisanpham = this.lspId;
			
			String ghichu = "";
			if(this.ghichuArr != null)
			{
				for(int i = 0; i < this.ghichuArr.length; i++)
				{
					ghichu += this.ghichuArr[i] + "__";
				}
				if(ghichu.trim().length() > 0)
				{
					this.GhiChu = ghichu;
				}
			}
			if(loaisanpham==null || loaisanpham.length() <=0){
				loaisanpham="NULL";
			}
				
			query = " update erp_denghimuahang set ngaydenghi = '" + this.ngaydenghi + "', donvithuchien_fk = '" + this.dvthId + "', " +
					" loaisanpham_fk = " + loaisanpham + ", loaihanghoa_fk = '" + this.lhhId + "', tiente_fk = '" + this.TienTe_FK + "', ngaysua = '" + ngaysua + "', nguoisua = '" + this.userId + "'," +
					" GhiChu=N'" + this.GhiChu + "', NguonGocHH = '" + this.NguonGocHH +"', Congvan = N'" + this.Congvan + "'";
										
				query=query+	"  where pk_seq = '" + this.id + "'";

			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat Mua hang: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DENGHIMUAHANG_SP where denghi_fk = '" + this.id + "'";
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_DENGHIMUAHANG_SP: " + query;
				db.getConnection().rollback();
				return false;
			}
		
			System.out.println("Loai HH "+this.lhhId);
			
			
			for (int i = 0; i < this.spList.size(); i++)
			{
				ISanpham sp = this.spList.get(i);
				
				String SanPham_FK = "NULL";
				String ChiPhi_FK = "NULL";
				String TaiSan_FK = "NULL";
				String CCDC_FK = "NULL";
				

				if (this.lhhId.equals("0"))
				{
					//query = "select pk_seq from ERP_SanPham where MA = '" + sp.getMasanpham() + "' and pk_seq=" + sp.getPK_SEQ();
					SanPham_FK = sp.getPK_SEQ();					
					if(SanPham_FK == null || SanPham_FK.trim().length() == 0) {
						SanPham_FK = "NULL";
					}
				}
				else
				{
					if(this.lhhId.equals("1"))  //Tai san co dinh
					{
					
						TaiSan_FK = sp.getPK_SEQ();
						if(TaiSan_FK == null || TaiSan_FK.trim().length() == 0) {
							TaiSan_FK = "NULL";
						}
						
					}
					else
					{
						if(this.lhhId.equals("3"))  //CONG CU DUNG CU
						{
							CCDC_FK = sp.getPK_SEQ();							
							if(CCDC_FK == null || CCDC_FK.trim().length() == 0) {
								CCDC_FK = "NULL";
							}
						}
						else  //Chi phi dich vu
						{
							
							ChiPhi_FK = sp.getPK_SEQ();
							if(ChiPhi_FK == null || ChiPhi_FK.trim().length() == 0) {
								ChiPhi_FK = "NULL";
							}
						}
					}
				}
				
				if( this.lhhId.equals("0") && SanPham_FK.equals("NULL") && TaiSan_FK.equals("NULL") && CCDC_FK.equals("NULL") ) // && ChiPhi_FK.equals("NULL")
				{
					this.msg = "Vui lòng kiểm tra lại mã sản phẩm / mã tài sản / mã công cụ dụng cụ / mã chi phí trong danh sách sản phẩm bạn nhập.";
					this.db.getConnection().rollback();
					return false;
				}
				
				if(this.lhhId.equals("0")){
					if(SanPham_FK=="NULL" &&  TaiSan_FK=="NULL"  && CCDC_FK =="NULL" && ChiPhi_FK=="NULL" ){
						this.msg = "Vui lòng nhập mã sản phẩm hoặc chi phí của đề nghị";
						db.getConnection().rollback();
						return false;
					}
				}
				
				String muaNLdukien_fk =  ( ( sp.getMNLId().trim().length() <= 0 || sp.getMNLId() == null ) ? "null" : sp.getMNLId() );
				
				query = " insert into ERP_DENGHIMUAHANG_SP(denghi_fk, sanpham_fk, taisan_fk, ccdc_fk, chiphi_fk, diengiai, donvi, soluong, soluongduyet) " +
						" values(" + this.id + ", " + SanPham_FK + ", " + TaiSan_FK + ", " + CCDC_FK + ", " + ChiPhi_FK + ", N'" + sp.getTensanpham() + "', N'" + sp.getDonvitinh() + "', '" + sp.getSoluong().trim() + "', '" + sp.getSoluongduyet().trim() + "')";
				
				System.out.println("2.Insert Into Erp_MuaHang_SP :" + query);
				
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi Mua hang - San pham: " + query;
					
					//System.out.println("5.Exception tai day: " + query);
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
			e.printStackTrace();
			this.msg = "Khong the cap nhat don hang " + query;
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
					   "WHERE DUYETMUAHANG.MUAHANG_FK = '" + this.id + "'";
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
		
		System.out.println("Trang thai duyet"  + query);
		
		ResultSet rs = this.db.get(query);
		try{
			if (rs != null){ 				
				if(rs.next()){
					String tmp = rs.getString("RESULT");
					if(tmp != null){
						if(tmp.equals("0")) result = "Đã duyệt";
					}else{
						result = "Không cần duyệt";
					}
					rs.close();
				}else{
					result = "Không cần duyệt";
				}
			}else{
				result = "Không cần duyệt";
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

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}
	
	public String getCanDuyet() {
		if(this.id!=null && this.id.length()>0){
		String sql="select DACHOT from  ERP_MUAHANG  where pk_seq ="+this.id;
		ResultSet rs=db.get(sql);
		try{
			if(rs.next()){
				this.canduyet=rs.getString("DACHOT");
			}
			rs.close();
		}catch(Exception er){
			er.printStackTrace();
		}
		
		}
		return this.canduyet;
	}

	
	public void setCanDuyet(String canduyet) {
		
		this.canduyet = canduyet;
	}

	public String[] getGhiChuArr() {
		return this.ghichuArr;
	}

	public void setGhiChuArr(String[] ghichuArr) {
		this.ghichuArr = ghichuArr;
	}
	
	public String[] getCpkDienGiai() {
		
		return this.cpkDiengiai;
	}

	
	public void setCpkDiengiai(String[] cpkDD) {
		
		this.cpkDiengiai = cpkDD;
	}

	
	public String[] getCpkSoTien() {
		
		return this.cpkSotien;
	}

	
	public void setCpkSoTien(String[] cpkST) {
		
		this.cpkSotien = cpkST;
	}

	public String getmaDMH() {
		
		return this.maDMH;
	}


	public void setmaDMH(String maDMH) {
		this.maDMH= maDMH;
		
	}

	@Override
	public String getCongvan() {
		// TODO Auto-generated method stub
		return this.Congvan;
	}

	@Override
	public void setCongvan(String congvan) {
		// TODO Auto-generated method stub
		this.Congvan = congvan;
	}

	public String getDuyetdnmh() {
		return Duyetdnmh;
	}

	public void setDuyetdnmh(String duyetdnmh) {
		Duyetdnmh = duyetdnmh;
	}
	
}
