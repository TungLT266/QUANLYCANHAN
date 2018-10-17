package geso.traphaco.distributor.beans.donhang.imp;

import java.io.Serializable;
import geso.traphaco.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.traphaco.distributor.beans.ctkhuyenmai.imp.XLkhuyenmai;
import geso.traphaco.distributor.beans.ctkhuyenmai.imp.XLkhuyenmaiDonhangDXK;
import geso.traphaco.distributor.beans.donhang.IDonhang;
import geso.traphaco.distributor.beans.donhang.ISanpham;
import geso.traphaco.distributor.beans.donhang.imp.Sanpham;
import geso.traphaco.distributor.beans.trakhuyenmai.ITrakhuyenmai;
import geso.traphaco.distributor.db.sql.*;
import geso.traphaco.distributor.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Donhang implements IDonhang, Serializable
{	
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id; //ma don hang
	String ngaygiaodich;
	String daidienkd;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String chietkhau;
	String tongchietkhau;
	String VAT;
	String msg;
	String ghichu;

	String donhangKhac;

	String nppId;
	String nppTen;
	String sitecode;
	String muctindung;	
	String loaiNppId;

	String ddkdId;
	String gsbhId;

	String tbhId;
	String smartId;
	String khTen;
	ResultSet khlist;
	String khId;
	String bgstId;

	String khoId;
	ResultSet gsbhs;
	ResultSet kholist;
	ResultSet tbhlist;
	ResultSet ddkdlist;

	List<ISanpham> splist;
	float ttCKKM;
	float ttsauCKKM;
	float ttsauCK;
	String tienCK;
	String tongtientruocVAT;
	String tongtiensauVAT;
    String ingia;
    
    String capduyet;

	Hashtable<String, Integer> spThieuList;

	///trakhuyen mai
	Hashtable<String, Float> scheme_tongtien = new Hashtable<String, Float>();
	Hashtable<String, Float> scheme_chietkhau = new Hashtable<String, Float>();
	List<ISanpham> scheme_sanpham = new ArrayList<ISanpham>();

	boolean aplaikm;
	boolean cokm;
	boolean chuaApkm;
	boolean dacoDh;
	boolean daxuatkhoChuachot;

	String coTrungBay;
	boolean aplaitb;

	String IsDonHangLe;
	String IsSampling;

	//
	String[] tichluy_scheme;
	String[] tichluy_tongtien;
	String[] tichluy_vat;
	String[] tichluy_cothexoa;

	//
	ResultSet ckbsList;
	String chietkhau_ids;
	Hashtable<String, Float> chietkhau_giatri;
	String chietkhau_vat;

	//Doanh so de nghi
	ResultSet dsdnRs;
	String tieuchiID;
	String dstXanh;
	String dstHHBG;
	String dstKHAC;
	String dstXanh_Denghi;
	String dstHHBG_Denghi;

	String ngayks;
	String enterKH;
	String chucuahieu;

	String nvgnId;
	ResultSet nvgnRs;

	String denghitraCKTHANG;

	dbutils db;

	String chietkhaucu;
	String donquatang;
	String sotaikhoan;
	
	ResultSet congnoRs;
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;

	float ptChietkhauBHKM;
	
	ResultSet tructhuocRs;
	String tructhuocId;
	
	String kbhId;
	ResultSet kbhRs;
	
	public Donhang(String[] param)
	{
		this.id = param[0];
		this.khId = param[1];
		this.ngaygiaodich = param[2];
		this.nppTen = param[3];
		this.daidienkd = param[4];
		this.trangthai = param[5];
		this.ngaytao = param[6];
		this.nguoitao = param[7];
		this.ngaysua = param[8];
		this.nguoisua = param[9];	
		this.VAT = param[10];
		this.ddkdId = param[11];
		this.ghichu="";
		this.tbhId = "";
		this.gsbhId = "";
		this.chietkhau = "";
		this.tongchietkhau = "";
		this.bgstId = "0";
		this.khoId = "";
		this.msg = "";
		this.muctindung="0";
		this.spThieuList = new Hashtable<String, Integer>();
		this.aplaikm = false;
		this.cokm = false;
		this.chuaApkm = false;
		this.dacoDh = false;
		this.daxuatkhoChuachot = false;
		this.ngayks = "";
		this.coTrungBay = "";
		this.aplaitb = false;

		this.IsDonHangLe = "0";
		this.IsSampling = "0";
		this.enterKH = "0";

		this.chietkhau_ids = "";
		this.chietkhau_giatri = new Hashtable<String, Float>();
		this.chietkhau_vat = "";
		this.tieuchiID = "";

		this.dstXanh = "";
		this.dstHHBG = "";
		this.dstKHAC = "";
		this.dstXanh_Denghi = "";
		this.dstHHBG_Denghi = "";

		this.donhangKhac ="0";
		this.chucuahieu = "";
		this.denghitraCKTHANG = "0";
		this.chietkhaucu="0";
		this.donquatang = "0";

		this.nvgnId ="";
		this.sotaikhoan = "";
		this.ingia="1";
		this.capduyet = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
		this.ptChietkhauBHKM = 0;
		this.tructhuocId = "";
		this.kbhId = "";
		
		this.db = new dbutils();

	}

	public Donhang(String id)
	{
		this.id = id;
		this.khId = "";
		this.ngaygiaodich = "";
		this.nppTen = "";
		this.daidienkd = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";	
		this.VAT = "";
		this.ddkdId = "";
		this.gsbhId = "";
		this.tbhId = "";
		this.chietkhau = "";
		this.tongchietkhau = "";
		this.tongtiensauVAT = "0";
		this.tongtientruocVAT ="0";
		this.ttsauCKKM = 0;
		this.ttCKKM = 0;
		this.ttsauCK = 0;
		this.bgstId = "0";
		this.khoId = "";
		this.msg = "";
		this.khTen = "";
		this.smartId = "";
		this.muctindung="0";
		this.aplaikm = false;
		this.cokm = false;
		this.chuaApkm = false;
		this.dacoDh = false;
		this.daxuatkhoChuachot = false;
		this.aplaitb = false;
		this.spThieuList = new Hashtable<String, Integer>();
		this.ngayks = "";
		this.coTrungBay = "";
		this.IsDonHangLe = "0";
		this.IsSampling = "0";
		this.enterKH = "0";
		this.ghichu="";
		this.chietkhau_ids = "";
		this.chietkhau_giatri = new Hashtable<String, Float>();
		this.chietkhau_vat = "";
		this.tieuchiID = "";

		this.dstXanh = "";
		this.dstHHBG = "";
		this.dstKHAC = "";
		this.dstXanh_Denghi = "";
		this.dstHHBG_Denghi = "";

		this.donhangKhac ="0";
		this.chucuahieu = "";
		this.nvgnId="";
		this.denghitraCKTHANG = "0";
		this.donquatang = "0";
		this.sotaikhoan = "";
		this.ingia="1";
		this.capduyet = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
		this.ptChietkhauBHKM = 0;
		this.tructhuocId = "";
		this.kbhId = "";
		
		this.db = new dbutils();	
	}

	public Donhang(String id, String nppId)
	{
		this.id = id;
		this.nppId = nppId;
		this.CreateSpList();
	}

	public String getUserId() 
	{		
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;		
	}

	public String getSmartId() 
	{		
		return this.smartId;
	}

	public void setSmartId(String smartId) 
	{
		this.smartId = smartId;		
	}

	public String getKhTen() 
	{		
		return this.khTen;
	}

	public void setKhTen(String khTen) 
	{
		this.khTen = khTen;		
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;		
	}

	public String getKhId() 
	{	
		return this.khId;
	}

	public void setKhId(String khId) 
	{
		this.khId = khId;
	}

	public String getNgaygiaodich() 
	{	
		return this.ngaygiaodich;
	}

	public void setNgaygiaodich(String ngaygiaodich) 
	{
		this.ngaygiaodich = ngaygiaodich;		
	}

	public String getDaidienkd() 
	{	
		return this.daidienkd;
	}

	public void setDaidienkd(String daidienkd) 
	{
		this.daidienkd = daidienkd;		
	}

	public String getTrangthai()
	{	
		if(this.trangthai == null)
			this.trangthai = "0";
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;		
	}

	public String getNgaytao()
	{	
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;		
	}

	public String getNguoitao() 
	{		
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;		
	}

	public String getNgaysua() 
	{		
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;	
	}

	public String getNguoisua() 
	{		
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;	
	}

	public String getChietkhau() 
	{
		if(this.chietkhau.length() <= 0)
			this.chietkhau = "0";
		return this.chietkhau;
	}

	public void setChietkhau(String chietkhau) 
	{
		this.chietkhau = chietkhau;		
	}

	public String getVAT() 
	{
		if(this.VAT == "")
		{
			//this.VAT = "10";
			this.VAT = "0"; //OneOne, gia trong bang gia da co VAT
		}
		return this.VAT;
	}

	public void setVAT(String vat) 
	{
		this.VAT = vat;	
	}

	public String getMessage() 
	{	
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;		
	}

	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}

	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}

	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}

	public ResultSet getDdkdList() 
	{	
		return this.ddkdlist;
	}

	public void setDdkdList(ResultSet ddkdList)
	{
		this.ddkdlist = ddkdList;		
	}

	public String getDdkdId() 
	{		
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;	
	}

	public List<ISanpham> getSpList()
	{	
		return this.splist;
	}

	public void setSpList(List<ISanpham> splist) 
	{
		this.splist = splist;
	}

	public float getTongtiensauCK() 
	{	
		float tongtientruocvat=0;	
		try{
			tongtientruocvat= Float.parseFloat(this.tongtientruocVAT);
		}catch(Exception er)
		{

		}
		float tienck=0;	
		try{
			tienck= Float.parseFloat(this.tongchietkhau);
		}catch(Exception er){

		}

		this.ttsauCK = tongtientruocvat -tienck;

		return this.ttsauCK;
	}

	public void setTongtiensauCK(float ttsck) 
	{
		this.ttsauCK = ttsck;	
	}

	public String getTongtientruocVAT() 
	{		
		return this.tongtientruocVAT;
	}

	public void setTongtientruocVAT(String tttvat) 
	{
		this.tongtientruocVAT = tttvat;		
	}

	public String getTongtiensauVAT()
	{		
		return this.tongtiensauVAT;
	}

	public void setTongtiensauVAT(String ttsvat) 
	{
		this.tongtiensauVAT = ttsvat;		
	}

	public float getTongtienCKKM(){
		return this.ttCKKM;
	}
	public void setTongtienCKKM(float ttckkm){
		this.ttCKKM = ttckkm;

	}

	public float getTongtiensauCKKM()
	{
		if(this.donhangKhac.equals("1"))
		{
			this.ttsauCKKM=0;
		}
		else{
			this.ttsauCKKM = Float.parseFloat(this.tongtiensauVAT) - this.ttCKKM;
		}

		return this.ttsauCKKM;
	}

	public void setTongtiensauCKKM(float ttsckkm){
		this.ttsauCKKM = ttsckkm;
	}

	public ResultSet getTbhList() 
	{
		return this.tbhlist;
	}

	public void setTbhList(ResultSet tbhList) 
	{
		this.tbhlist = tbhList;
	}

	public String getTbhId() 
	{
		return this.tbhId;
	}

	public void setTbhId(String tbhId) 
	{
		this.tbhId = tbhId;
	}

	public String getGsbhId() 
	{
		return this.gsbhId;
	}

	public void setGsbhId(String gsbhId) 
	{
		this.gsbhId = gsbhId;
	}

	public ResultSet getKhList() 
	{
		return this.khlist;
	}

	public void setKhList(ResultSet khlist) 
	{
		this.khlist = khlist;
	}

	public Hashtable<String, Integer> getSpThieuList() 
	{
		return this.spThieuList;
	}

	public void setSpThieuList(Hashtable<String, Integer> spThieuList) 
	{
		this.spThieuList = spThieuList;
	}

	//tra km
	public Hashtable<String, Float> getScheme_Tongtien() 
	{
		return this.scheme_tongtien;
	}

	public void setScheme_Tongtien(Hashtable<String, Float> scheme_tongtien) 
	{
		this.scheme_tongtien = scheme_tongtien;
	}

	public Hashtable<String, Float> getScheme_Chietkhau() 
	{
		return this.scheme_chietkhau;
	}

	public void setScheme_Chietkhau(Hashtable<String, Float> scheme_chietkhau) 
	{
		this.scheme_chietkhau = scheme_chietkhau;
	}

	public List<ISanpham> getScheme_SpList() 
	{
		return this.scheme_sanpham;
	}

	public void setScheme_SpList(List<ISanpham> splist) 
	{
		this.scheme_sanpham = splist;
	}

	public void getTrakhuyenmai()
	{
		if( this.id.trim().length() <= 0 )
			return;
		
		List<ISanpham> scheme_sp = new ArrayList<ISanpham>();

		String query = "select a.ctkmId, a.trakmId, a.soxuat, a.soluong,donvi, a.spMa, a.tonggiatri, b.scheme, c.loai, c.hinhthuc, c.chietkhau,  c.tongluong, c.tongtien, d.ten, d.pk_seq as spId, a.khoNVBH " +
						"from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq inner join trakhuyenmai c on a.trakmid = c.pk_seq left join sanpham d on a.spMa = d.ma " +
						" left join donvidoluong dv on dv.pk_seq=d.dvdl_fk " +
						"where a.donhangId = '" + this.id + "'";

		query += "union ALL " + 
				 "select '1' ctkmId, '1' trakmId, 1 soxuat, NULL soluong, Null donvi, '' spMa, a.chietkhau_KM*a.soluong as tonggiatri, a.schemeCHIETKHAU, 2 as loai, 1 as hinhthuc, 1 as chietkhau,  0 tongluong, 0 tongtien, '' ten, 1 as spId, a.khoNVBH "+
				 "from DONHANG_SANPHAM a "+
				 "where DONHANG_FK = '" + this.id + "' and isnull(a.schemeCHIETKHAU, '') != '' ";
		
		//	System.out.println("1.Khoi tao TKM:"+ query);

		ResultSet rs = db.get(query);
		if( rs != null)
		{
			try 
			{
				while(rs.next())
				{
					String schemeName = rs.getString("scheme");				
					String trakmId = rs.getString("trakmId");
					String soxuat = rs.getString("soxuat");	
					String soluong = rs.getString("soluong");
					String loai = rs.getString("loai");
					String hinhthuc = rs.getString("hinhthuc");
					String tongiatri = rs.getString("tonggiatri");
					String donvi=rs.getString("donvi");
					float tongtien = 0;
					float chietkhau = 0;
					String spMa = "";
					this.ttCKKM = 0;

					if (loai == null)
					{
						if(rs.getString("spMa") == null)
						{
							if(rs.getString("tongtien") != null)
								tongtien = Float.parseFloat(rs.getString("tongtien"));
							this.scheme_tongtien.put(schemeName, tongtien * Float.parseFloat(soxuat));
							this.aplaikm = true; //co ctkm
							this.cokm = true;
						}
					}
					else
					{
						if(loai.equals("1")) //tra tien
						{
							if(rs.getString("tongtien") != null)
								tongtien = Float.parseFloat(rs.getString("tongtien"));

							this.scheme_tongtien.put(schemeName, tongtien *  Float.parseFloat(soxuat));
							this.aplaikm = true;
							this.cokm = true;
						}
						else
						{
							if(loai.equals("2")) //tra chiet khau
							{
								if(rs.getString("chietkhau") != null)
									chietkhau = Float.parseFloat(rs.getString("chietkhau"));
								this.scheme_chietkhau.put(schemeName, Float.parseFloat(tongiatri));
								this.ttCKKM = this.ttCKKM + Float.parseFloat(tongiatri);
								this.aplaikm = true;
								this.cokm = true;
								//	System.out.println("1.Tra chiet khau");

							}
							else //tra sp
							{
								//String sql = "select a.sanpham_fk as spId, a.soluong, b.ma, b.ten from trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where a.trakhuyenmai_fk = '" + trakmId + "'";
								//String sql = "select a.spMa, a.soluong, b.pk_seq as spId, b.ten from donhang_ctkm_trakm a inner join sanpham b on a.spMa = b.ma where ";

								//ResultSet sanphamRs = db.get(sql);					
								String[] param = new String[8];
								ISanpham sp = null;	

								//while(sanphamRs.next())
								//{
								param[0] = rs.getString("spId");
								param[1] = rs.getString("spMa");
								param[2] = rs.getString("ten");
								param[3] = soluong;
								param[4] = donvi;
								param[5] = "0";
								param[6] = schemeName;
								param[7] = "0";

								sp = new Sanpham(param);
								sp.setKhoNVBH(rs.getString("khoNVBH"));
								scheme_sp.add(sp);
								this.aplaikm = true;
								this.cokm = true;
								//}														
								//sanphamRs.close();
							}
						}
					}
				}
				rs.close();
			} 
			catch(Exception e) {}
		}
		this.scheme_sanpham = scheme_sp;

	}

	private void getNppInfo()
	{	
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			this.nppId = util.getIdNhapp(this.userId);
			this.nppTen = util.getTenNhaPP();
			this.sitecode = util.getSitecode();
		}
		else
		{
			this.nppId = this.npp_duocchon_id;
			this.nppTen = "";
			this.sitecode = "";
		}
		
		//lay ngay khoa so
		this.ngayks = util.ngaykhoaso(this.nppId);
	}

	public boolean Muctindung()
	{ 
		this.muctindung = "99999999999"; //gia tri don hang khong the vuot qua
		return false;
	}

	private String kenh()
	{ 
		//	String sql ="select a.kbh_fk from giamsatbanhang a,ddkd_gsbh b where a.pk_seq = b.gsbh_fk and b.ddkd_fk ='"+ this.ddkdId +"'";
		String sql ="select kbh_fk from giamsatbanhang where pk_seq ='"+ this.gsbhId +"'";
		ResultSet rs = db.get(sql);
		if(rs != null)
		{
			try 
			{
				rs.next();
				String kbhfk= rs.getString("kbh_fk");
				rs.close();
				return kbhfk;
			} 
			catch(Exception e) {}
		}
		return "null";
	}

	public boolean CreateDh(List<ISanpham> splist) 
	{
		if(this.khId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn khách hàng";
			return false;
		}
		
		if(this.gsbhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn giám sát bán hàng";
			return false;
		}
		
		if(this.ddkdId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn trình dược viên";
			return false;
		}
		
		if(this.kbhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if(this.splist.size() <= 0)
		{
			this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm";
			return false;
		}

		if(this.spThieuList.size() > 0)
		{
			this.msg = "Trong kho khong du so luong mot so san pham ban chon, vui long chon lai so luong...";
			return false;
		}

		String sqlCHECK = "";
		for(int m = 0; m < splist.size(); m++)
		{
			ISanpham sp = splist.get(m);

			/*System.out.println("---CHIET KHAU: " + sp.getChietkhau() + "  -- %: " + this.chietkhau );*/
			//TINH LAI CHIET KHAU
			double _ck = 0;

			//sanpham += sp.getMasanpham() + "#" + sp.getSoluong() + "#" + sp.getDongia() + "#" + Double.toString(_ck) + "#" + khoNVBH + ";";

			if( sp.getSoluong().trim().length() > 0 && !sp.getSoluong().equals("0") )
			{
				sqlCHECK += " select pk_seq as sanpham_fk, '" + sp.getSoluong() +  "' as soluong, '" + sp.getDongia().replaceAll(",", "") + "' as dongia, '" + _ck + "' as chietkhau , thuexuat as ptVat " +
							" from SANPHAM where ma = '" + sp.getMasanpham() + "'  ";
			}

			sqlCHECK += " union ";
		}
		
		sqlCHECK = sqlCHECK.substring(0, sqlCHECK.length() - 7);

		try
		{
			System.out.println("SQL CHECK TON KHO: " + sqlCHECK);
			
			String query = " select top(1) kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + this.khId + "' ";
			ResultSet rs = this.db.get(query);
			rs.next();
			this.kbhId = rs.getString("kbh_fk");
			rs.close();
			
			
			//DUOI DLPP KHÔNG BIỆT TỒN KHO THEO NHÓM KÊNH NỮA, VÀ CHỈ CÓ 1 KHO HÀNG BÁN
			
			query =  "select b.TEN, b.MA, a.available, a.available as available " +
					"from NHAPP_KHO a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
					"	left join  " +
					"	( " +
					sqlCHECK +
					"	) " +
					"	dh on b.pk_seq = dh.sanpham_fk " +
					"where a.NPP_FK = '" + this.tructhuocId + "' and a.kho_fk = '" + this.khoId + "' " + 
					//" 	and NHOMKENH_FK = ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = '" + this.kbhId + "' ) " +
					" 	and NHOMKENH_FK = ( 100000 ) " +
					"		and a.available  < isnull(dh.soluong, 0) " ;

			System.out.println("---CHECK TON KHO: " + query);
			ResultSet rsChheck = db.get(query);
			String msg = "";
			while(rsChheck.next())
			{
				msg += "Sản phẩm ( " + rsChheck.getString("TEN") + " ) còn tối đa ( " + rsChheck.getString("available") + " ). Vui lòng kiểm tra lại ";
				this.spThieuList.put(rsChheck.getString("MA") , rsChheck.getInt("available"));
			}
			rsChheck.close();
			if(msg.trim().length() > 0)
			{
				this.msg = msg;
				return false;
			}
		}
		catch(Exception er)
		{
			this.msg = "Lỗi khi lưu đơn hàng: " + er.getMessage();
			er.printStackTrace();
			return false;
		}
		
		ResultSet rs;
		try 
		{
			//db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			//db.update("SET LOCK_TIMEOUT 60000;");

			db.getConnection().setAutoCommit(false);
			String kbh_fk = this.kbhId;
			
			String BM_fk = "", ASM_fk = "";

			String query = "select top 1 bm_cn.bm_fk, a.asm_fk from  asm_khuvuc a " +
							"inner join asm on asm.pk_seq=a.asm_fk AND A.NGAYBATDAU <= '"+this.ngaygiaodich+"' AND A.NGAYKETTHUC >= '" + this.ngaygiaodich+"' " +
							"inner join khuvuc kv on kv.pk_seq=a.khuvuc_fk " +
							"inner join bm_chinhanh bm_cn on bm_cn.vung_fk=kv.vung_fk AND bm_cn.NGAYBATDAU <='" + this.ngaygiaodich + "' AND bm_cn.NGAYKETTHUC >= '"+this.ngaygiaodich+"' " +
							"inner join nhaphanphoi npp on npp.khuvuc_fk=kv.pk_seq " +
							"inner join bm on bm.pk_seq=bm_cn.bm_fk and bm.kbh_fk = "+kbh_fk+" and bm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq = "+this.gsbhId+") " +
							"where npp.pk_seq = " + this.tructhuocId + " and asm.kbh_fk = "+kbh_fk+" and asm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq = "+this.gsbhId+" )	";

			System.out.println("__"+query);

			rs = this.db.get(query);
			while(rs.next())
			{
				BM_fk = rs.getString("bm_fk");
				ASM_fk = rs.getString("asm_fk");
			}

			if(BM_fk == null || BM_fk.length() == 0)
				BM_fk = "NULL";
			if(ASM_fk == null || ASM_fk.length() == 0)
				ASM_fk = "NULL";
			
			String ngaykyHd="(select ngaykyHd from khachhang where pk_Seq='"+this.khId+"') ";
			String HCH_FK="(select HCH_FK from khachhang where pk_Seq='"+this.khId+"') ";
			String nvgn_fk = this.nvgnId.trim().length() <= 0 ? "NULL" : this.nvgnId;

			//CHECK NGÀY HIỆN TẠI, XEM CÓ LƠN HƠN NGÀY KHÓA SỔ KINH DOANH KHÔNG
			Utility util = new Utility();
			int checkKS_KINHDOANH = util.CheckKhoaSoKinhDoanh(db, nppId, this.getThoiDiemHienTai(), "", "", "");
			String chuyenSALES = "0";
			if(checkKS_KINHDOANH == 1 )
			{
				chuyenSALES = "1";
			}
			
			query = "insert into DonHang(donhangkhac ,ngaynhap, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, vat, tonggiatri, ddkd_fk, gsbh_fk, khachhang_fk, npp_fk, kho_fk, kbh_fk, tinhtrang,BM,ASM, IsDonHangLe, IsSampling, chietkhau, denghitraCK,ghichu, donquatang, sotaikhoan,ingia, nvgn_fk, HCH_FK, NgayKyHD, chuyenSALES )	" +	
					"select 0 as donhangkhac, '" + this.ngaygiaodich + "' ngaynhap, '0' as trangthai, '" + this.getDateTime() + "' ngaytao, '" + this.getDateTime() + "' ngaysua, '" + this.userId + "' nguoitao, '" + this.userId + "' nguoitao, " + 
					"  '" + this.tongtientruocVAT + "' vat, '" + this.ttsauCKKM + "' tonggiatri, '" + this.ddkdId + "' ddkd_fk, '" + this.gsbhId + "' gsbh_fk, '" + this.khId + "' khachhang_fk, '" + this.tructhuocId + "' npp_fk, '" + this.khoId + "' kho_fk, '" + this.kbhId + "' kbh_fk, " + 
					" '0', " + BM_fk + " BM_fk, " + ASM_fk + " AMS_fk, 0 as dhle, 0 as IsSampling, " + this.chietkhau + " pt_chietkhau, '" + this.denghitraCKTHANG + "' denghitraCK, N'" + this.ghichu + "' ghichu, 0 as donquatang, '" + this.sotaikhoan + "' sotaikhoan, " + this.ingia + " ingia, " + nvgn_fk + " as nvgn_fk, " + HCH_FK + ", " + ngaykyHd + ", '" + chuyenSALES + "'  ";
			
			System.out.println("Tao moi don hang: " + query);
			
			if( db.updateReturnInt(query) != 1 )
			{
				db.getConnection().rollback();
				this.msg = "1.Khong the tao moi DonHang: " + query;
				return false; 
			}
			
			query = " select SCOPE_IDENTITY() as dhId ";
			rs = this.db.get(query);
			rs.next();
			this.id = rs.getString("dhId");
			rs.close();
			
			//Chèn donhang_sanpham tận dung luôn câu SQL bên trên, kể cả hàm cập nhật tồn kho, cũng chỉ chạy 1 lượt
			query = "if exists (select PK_SEQ from DONHANG WHERE PK_SEQ = '" + this.id + "' AND TRANGTHAI = 0)" +
					"insert donhang_sanpham( sanpham_fk, donhang_fk, soluong, kho_fk, giamua, chietkhau, THUEVAT, dongiaGOC ) " +
					"select sp.sanpham_fk, '" + this.id + "', sp.soluong, '" + this.khoId + "', sp.dongia, sp.chietkhau, sp.ptVat, sp.dongia as dongiaGOC " +
					//"	ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = sp.sanpham_fk and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + this.tructhuocId +  "' ) ) ), 0) as dongia	" +
					"from ( " + sqlCHECK + " ) sp ";
			System.out.println(":::: CAP NHAT DON GIA: " + query );
			if(db.updateReturnInt(query) <= 0)
			{
				db.getConnection().rollback(); 
				this.msg = "4.Khong the cap nhat table 'Don Hang San pham' , " + query;
				return false; 
			}

			//Cap nhat kho
			query = "update kho set kho.booked = kho.booked + sp.SOLUONG, kho.available = kho.available - sp.SOLUONG " + 
					"from DONHANG dh inner join DONHANG_SANPHAM sp on dh.PK_SEQ = sp.DONHANG_FK   " + 
					"inner join nhapp_kho kho on sp.SANPHAM_FK = kho.SANPHAM_FK and sp.KHO_FK = kho.KHO_FK and kho.NPP_FK = dh.NPP_FK " + 
					//" 	and kho.NHOMKENH_FK = ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = dh.KBH_FK ) " + 
					" 	and kho.NHOMKENH_FK = ( 100000 ) " + 
					"where dh.PK_SEQ = " + this.id +" and dh.trangthai=0" ;
			if(db.updateReturnInt(query) <= 0)
			{
				db.getConnection().rollback(); 
				this.msg = "Lỗi khi cập nhật tồn kho: " + query;
				return false; 
			}

			query = "if exists (select PK_SEQ from DONHANG WHERE PK_SEQ = '"+this.id+"' AND TRANGTHAI = 0)" +
					"update donhang_sanpham set THANHTIEN = (soluong * giamua - chietkhau) + TIENVAT, TIENVAT = ( soluong * giamua - chietkhau ) * THUEVAT / 100 where donhang_fk = " + this.id;
			if(db.updateReturnInt(query) <= 0)
			{
				db.getConnection().rollback(); 
				this.msg = "6.Khong the cap nhat table 'Don Hang San pham' , " + query;
				return false; 
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			if( checkKS_KINHDOANH == 1 )
			{
				String[] hientai = this.getDateTime().split("-");
				int thangtiep = Integer.parseInt(hientai[1]) + 1;
				if( thangtiep > 12 )
					thangtiep = 1;
				
				this.msg = "Doanh số đơn hàng này được chuyển sales qua tháng " + ( thangtiep );
			}

		} 
		catch (Exception e) 
		{
			try 
			{
				db.getConnection().rollback();
			}
			catch (Exception e1) {  }
			e.printStackTrace();
			
			this.msg = "Lỗi tạo mới đơn hàng: " + e.getMessage();
			return false;
		}
		
		//CAP NHAT LAI CAC COT TRONG DON HANG
		Utility util = new Utility();
		//util.Update_GiaTri_DonHang(this.id, db);

		db.execProceduce2("CapNhatTooltip_DonHang", new String[] { this.id } );
		util.CapNhat_MaChungTu(db, this.id, "DONHANG", "NgayNhap");
		
		return true;

	}

	public boolean UpdateDh(List<ISanpham> splist, String action) 
	{	
		/*if(this.nvgnId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nhân viên giao nhận";
			return false;
		}*/

		if(this.splist.size() <= 0)
		{
			this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm";
			return false;
		}

		if(this.spThieuList.size() > 0)
		{
			this.msg = "Trong kho khong du so luong mot so san pham ban chon, vui long chon lai so luong...";
			return false;
		}

		String sqlCHECK = "";
		for(int m = 0; m < splist.size(); m++)
		{
			ISanpham sp = splist.get(m);

			/*System.out.println("---CHIET KHAU: " + sp.getChietkhau() + "  -- %: " + this.chietkhau );*/
			//TINH LAI CHIET KHAU
			double _ck = 0;

			//sanpham += sp.getMasanpham() + "#" + sp.getSoluong() + "#" + sp.getDongia() + "#" + Double.toString(_ck) + "#" + khoNVBH + ";";

			if( sp.getSoluong().trim().length() > 0 && !sp.getSoluong().equals("0") )
			{
				sqlCHECK += " select pk_seq as sanpham_fk, '" + sp.getSoluong() +  "' as soluong, '" + sp.getDongiaGoc().replaceAll(",", "") + "' as dongia, '" + _ck + "' as chietkhau , thuexuat as ptVat " +
							" from SANPHAM where ma = '" + sp.getMasanpham() + "'  ";
			}
			
			sqlCHECK += " union ";
		}
		
		sqlCHECK = sqlCHECK.substring(0, sqlCHECK.length() - 7);

		try
		{
			String sql = "select kbh_fk as kbhOld, " +
					"	( select top(1) kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + this.khId + "'  ) as kbhNew, " +
					"	( select count(*) from DONHANG_CTKM_TRAKM where DONHANGID = '" + this.id + "' ) as coKM	" +	
					"from donhang where pk_seq = '" + this.id + "'  ";
			System.out.println("_____sql"+sql);
			ResultSet rs = this.db.get(sql);
			String kbhOld = "";
			String kbhNew = "";
			boolean coKM = false;

			if(rs.next())
			{
				kbhOld = rs.getString("kbhOld");
				kbhNew = rs.getString("kbhNew");
				if(rs.getInt("coKM") > 0)
					coKM = true;
			}
			rs.close();

			if(!kbhNew.equals(kbhOld))
			{
				this.msg = "Khong The Luu Don Hang Voi Khach Hang Co Kenh  Khac Kenh Luc Tao Moi Don Hang, Vui Long Chon Lai Khach Hang, Hay Doi Kenh Cua Khach Hang";
				return false;
			}

			//NEU LA CAP NHAT, MA THAY DOI SP HOAC SO LUONG THI PHAI AP LAI KM
			if(action.equals("save") && coKM && this.donhangKhac.equals("0") )
			{			
				//Đối lại check 1 loạt sản phẩm của đơn hàng luôn, không cấn check từng SP
				sql = "select count(dh.sanpham_fk) as sodong  " +
						"from DONHANG_SANPHAM dh " +
						"left join  ( " + sqlCHECK + " ) dh_sp on dh.sanpham_fk = dh_sp.sanpham_fk " +
						"where donhang_fk = '" + this.id + "' and dh.soluong != dh_sp.soluong";
				int soDONG = 0;
				rs = db.get(sql);
				if(rs.next())
				{
					soDONG = rs.getInt("soDONG");
				}
				rs.close();

				if(soDONG > 0)
				{
					this.msg = "Khi thay đổi thông tin sản phẩm, số lượng trong đơn hàng, bạn phải bấm áp lại khuyến mại.";
					return false;
				}
			}

			System.out.println("SQL CHECK TON KHO: " + sqlCHECK);
			String query =  "select b.TEN, b.MA, a.available, a.available + isnull(dh_sp.soluong, 0) as available " +
					"from NHAPP_KHO a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
					"	left join  " +
					"	( " +
					sqlCHECK +
					"	) " +
					"	dh on b.pk_seq = dh.sanpham_fk " +
					"	left join " +
					"	( " +
					"		select sanpham_fk, soluong from DONHANG_SANPHAM where donhang_fk = '" + this.id + "' " +
					"	) " +
					"	dh_sp on b.pk_seq = dh_sp.sanpham_fk " +
					"where a.NPP_FK = '" + this.tructhuocId + "' and a.kho_fk = '" + this.khoId + "' " + 
					//" 	and NHOMKENH_FK = ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = '" + kbhNew + "' ) " +
					" 	and NHOMKENH_FK = ( 100000 ) " +
					"		and a.available + isnull(dh_sp.soluong, 0) < isnull(dh.soluong, 0) " ;

			System.out.println("---CHECK TON KHO: " + query);
			ResultSet rsChheck = db.get(query);
			String msg = "";
			while(rsChheck.next())
			{
				msg += "Sản phẩm ( " + rsChheck.getString("TEN") + " ) còn tối đa ( " + rsChheck.getString("available") + " ). Vui lòng kiểm tra lại ";
				this.spThieuList.put(rsChheck.getString("MA") , rsChheck.getInt("available"));
			}
			rsChheck.close();
			if(msg.trim().length() > 0)
			{
				this.msg = msg;
				return false;
			}
		}
		catch(Exception er)
		{
			this.msg = "Lỗi khi lưu đơn hàng: " + er.getMessage();
			er.printStackTrace();
			return false;
		}
		ResultSet rs;
		try 
		{
			//db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			//db.update("SET LOCK_TIMEOUT 60000;");

			db.getConnection().setAutoCommit(false);
			String kbh_fk = "", BM_fk = "", ASM_fk = "", ngaysua= "convert(varchar(10), getdate() , 126)";
			String query = " select top(1) kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + this.khId + "' ";
			rs = this.db.get(query);
			rs.next();
			kbh_fk = rs.getString("kbh_fk");

			query = "select top 1 bm_cn.bm_fk, a.asm_fk from  asm_khuvuc a " +
					"inner join asm on asm.pk_seq=a.asm_fk AND A.NGAYBATDAU <= '"+this.ngaygiaodich+"' AND A.NGAYKETTHUC >= '" + this.ngaygiaodich+"' " +
					"inner join khuvuc kv on kv.pk_seq=a.khuvuc_fk " +
					"inner join bm_chinhanh bm_cn on bm_cn.vung_fk=kv.vung_fk AND bm_cn.NGAYBATDAU <='" + this.ngaygiaodich + "' AND bm_cn.NGAYKETTHUC >= '"+this.ngaygiaodich+"' " +
					"inner join nhaphanphoi npp on npp.khuvuc_fk=kv.pk_seq " +
					"inner join bm on bm.pk_seq=bm_cn.bm_fk and bm.kbh_fk = "+kbh_fk+" and bm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq = "+this.gsbhId+") " +
					"where npp.pk_seq = " + this.tructhuocId + " and asm.kbh_fk = "+kbh_fk+" and asm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq = "+this.gsbhId+" )	";

			System.out.println("__"+query);

			rs = this.db.get(query);
			while(rs.next())
			{
				BM_fk = rs.getString("bm_fk");
				ASM_fk = rs.getString("asm_fk");
			}

			if(BM_fk == null || BM_fk.length() == 0)
				BM_fk = "NULL";
			if(ASM_fk == null || ASM_fk.length() == 0)
				ASM_fk = "NULL";
			
			
			String ngaykyHd="(select ngaykyHd from khachhang where pk_Seq='"+this.khId+"') ";
			String HCH_FK="(select HCH_FK from khachhang where pk_Seq='"+this.khId+"') ";
			String nvgn_fk = this.nvgnId.trim().length() <= 0 ? "NULL" : this.nvgnId;

			System.out.println(":::: DAI DIEN KINH DOANH LAY DUOC: " + this.ddkdId);
			
			query = "update donhang set HCH_FK="+HCH_FK+",NgayKyHD="+ngaykyHd+",donhangkhac = "+this.donhangKhac+" , ngaynhap = '"+this.ngaygiaodich+"', ddkd_fk = "+this.ddkdId+", " +
					"gsbh_fk = "+this.gsbhId+", khachhang_fk = "+this.khId+", KHO_FK = "+this.khoId+", " +
					"ngaysua = "+ngaysua+", nguoisua = "+this.userId+", tonggiatri = "+ Float.toString(this.ttsauCKKM) + ", vat = "+ this.tongtientruocVAT +", " +
					"kbh_fk = "+kbh_fk+" ,BM = "+BM_fk+", ASM = "+ASM_fk+", IsDonHangLe = "+this.IsDonHangLe +", IsSampling = "+this.IsSampling + ", " +
					"NGAYGIO = GETDATE(), nvgn_fk = " + nvgn_fk + ", chietkhau = "+this.chietkhau+", denghitraCK = "+this.denghitraCKTHANG + ", ghichu = N'"+this.ghichu + "', donquatang = '" + this.donquatang + "', sotaikhoan = N'" + this.sotaikhoan + "' , ingia=" + this.ingia +
					" where pk_seq = " + this.id +"  and TrangThai =0 ";
			
			System.out.println("[DonHang]"+query);
			
			if( db.updateReturnInt(query) != 1 )
			{
				db.getConnection().rollback();
				this.msg = "1.Khong the cap nhat table 'Don Hang'(ĐH đã chốt hoặc xảy ra lỗi) , " + query;
				return false; 
			}


			query = "update kho set kho.booked = kho.booked - sp.SOLUONG, kho.available = kho.available + sp.SOLUONG " + 
					"from DONHANG dh inner join DONHANG_SANPHAM sp on dh.PK_SEQ = sp.DONHANG_FK   " + 
					//"inner join nhapp_kho kho on sp.SANPHAM_FK = kho.SANPHAM_FK and sp.KHO_FK = kho.KHO_FK and kho.NPP_FK = dh.NPP_FK and kho.NHOMKENH_FK = ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = dh.KBH_FK )  " + 
					"inner join nhapp_kho kho on sp.SANPHAM_FK = kho.SANPHAM_FK and sp.KHO_FK = kho.KHO_FK and kho.NPP_FK = dh.NPP_FK and kho.NHOMKENH_FK = ( 100000 )  " + 
					"where dh.PK_SEQ = " + this.id +" and dh.trangthai=0" ;

			if(db.updateReturnInt(query)<=0)
			{
				db.getConnection().rollback(); 
				this.msg = "2.Khong the cap nhat table 'Don Hang San pham' , " + query;
				return false; 
			}

			//Thread.sleep(100000);
			
			//LUU LOG
			/*query = "insert LOG_BOOKED(nppId, pxkId, dhId, spId, bookedTANG, bookedGIAM, chucnang)  " +
					"select '" + this.nppId + "', '', donhang_fk, sanpham_fk, 0, soluong, N'Function/ UpdateDonHang' from DONHANG_SANPHAM where donhang_fk = '" + this.id + "' ";
			if( !db.update(query))
			{
				db.getConnection().rollback(); 
				this.msg = "Loi cap nhat LOG: " + query;
				return false; 
			}*/

			//PHAI LUU LAI NHUNG SP CO CHIET KHAU KM
			query = "insert DONHANG_SANPHAM_TEMP select * from donhang_sanpham where donhang_fk = '" + this.id + "' ";
			if(db.updateReturnInt(query)<=0)
			{
				db.getConnection().rollback(); 
				this.msg = "3.Khong the cap nhat table 'Don Hang San pham' , " + query;
				return false; 
			}
			
			query = "delete from donhang_sanpham where donhang_fk = " + this.id + " " +
					"		and donhang_Fk in (select pk_seq from DonHang where trangthai = 0 and pk_Seq = '" + this.id + "') ";
			if(db.updateReturnInt(query)<=0)
			{
				db.getConnection().rollback(); 
				this.msg = "3.Khong the cap nhat table 'Don Hang San pham' , " + query;
				return false; 
			}

			//Chèn donhang_sanpham tận dung luôn câu SQL bên trên, kể cả hàm cập nhật tồn kho, cũng chỉ chạy 1 lượt
			query = "if exists (select PK_SEQ from DONHANG WHERE PK_SEQ = '"+this.id+"' AND TRANGTHAI = 0)" +
					"insert donhang_sanpham( sanpham_fk, donhang_fk, soluong, kho_fk, giamua, chietkhau, THUEVAT, dongiaGOC ) " +
					"select sp.sanpham_fk, '" + this.id + "', sp.soluong, '" + this.khoId + "', sp.dongia, sp.chietkhau, sp.ptVat, sp.dongia as dongiaGOC " +
					//"	ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = sp.sanpham_fk and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + this.tructhuocId +  "' ) ) ), 0) as dongiaGOC	" +
					"from ( " + sqlCHECK + " ) sp ";
			System.out.println(":::: CAP NHAT DON GIA: " + query );
			if(db.updateReturnInt(query) <= 0)
			{
				db.getConnection().rollback(); 
				this.msg = "4.Khong the cap nhat table 'Don Hang San pham' , " + query;
				return false; 
			}
			
			//CAP NHAT LAI CHIET KHAU KM
			query = "update a set a.schemeCHIETKHAU = b.schemeCHIETKHAU,  "+
					 "			 a.chietkhau_KM = b.chietkhau_KM "+
					 "from DONHANG_SANPHAM a inner join DONHANG_SANPHAM_TEMP b on a.SANPHAM_FK = b.sanpham_fk and a.DONHANG_FK = b.donhang_fk "+
					 "where a.DONHANG_FK = '" + this.id + "' ";
			if(db.updateReturnInt(query)<=0)
			{
				db.getConnection().rollback(); 
				this.msg = "3.Khong the cap nhat table 'Don Hang San pham' , " + query;
				return false; 
			}
			
			query = "delete DONHANG_SANPHAM_TEMP where donhang_fk = '" + this.id + "' ";
			if(db.updateReturnInt(query)<=0)
			{
				db.getConnection().rollback(); 
				this.msg = "3.Khong the cap nhat table 'Don Hang San pham' , " + query;
				return false; 
			}
			

			//Cap nhat kho
			query = "update kho set kho.booked = kho.booked + sp.SOLUONG, kho.available = kho.available - sp.SOLUONG " + 
					"from DONHANG dh inner join DONHANG_SANPHAM sp on dh.PK_SEQ = sp.DONHANG_FK   " + 
					"inner join nhapp_kho kho on sp.SANPHAM_FK = kho.SANPHAM_FK and sp.KHO_FK = kho.KHO_FK and kho.NPP_FK = dh.NPP_FK " + 
					//" 	and kho.NHOMKENH_FK = ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = dh.KBH_FK ) " + 
					" 	and kho.NHOMKENH_FK = ( 100000 ) " + 
					"where dh.PK_SEQ = " + this.id +" and dh.trangthai=0" ;
			if(db.updateReturnInt(query) <= 0)
			{
				db.getConnection().rollback(); 
				this.msg = "Lỗi khi cập nhật tồn kho: " + query;
				return false; 
			}

			//LUU LOG
			/*query = "insert LOG_BOOKED(nppId, pxkId, dhId, spId, bookedTANG, bookedGIAM, chucnang)  " +
					"select '" + this.nppId + "', '', donhang_fk, sanpham_fk, soluong, 0, N'Function/ UpdateDonHang' from DONHANG_SANPHAM where donhang_fk = '" + this.id + "' ";
			if( !db.update(query))
			{
				db.getConnection().rollback(); 
				this.msg = "Loi cap nhat LOG: " + query;
				return false; 
			}*/

			query = "if exists (select PK_SEQ from DONHANG WHERE PK_SEQ = '"+this.id+"' AND TRANGTHAI = 0)" +
					"update donhang_sanpham set THANHTIEN = (soluong * giamua - chietkhau) + TIENVAT, TIENVAT = ( soluong * giamua - chietkhau ) * THUEVAT / 100 where donhang_fk = " + this.id;
			if(db.updateReturnInt(query) <= 0)
			{
				db.getConnection().rollback(); 
				this.msg = "6.Khong the cap nhat table 'Don Hang San pham' , " + query;
				return false; 
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			if(rs != null)
				rs.close();
			
		} 
		catch (Exception e) 
		{
			try 
			{
				db.getConnection().rollback();
			}
			catch (Exception e1) {  }
			e.printStackTrace();
			return false;
		}

		//this.ApChietKhau();
		
		//CAP NHAT LAI CAC COT TRONG DON HANG
		Utility util = new Utility();
		//util.Update_GiaTri_DonHang(this.id, db);
		
		db.execProceduce2("CapNhatTooltip_DonHang", new String[] { this.id } );
		util.CapNhat_MaChungTu(db, this.id, "DONHANG", "NgayNhap");

		return true;

	}

	public boolean UpdateDhXuatKho(List<ISanpham> splist) 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;

		try 
		{
			db.getConnection().setAutoCommit(false);
			long tt = Math.round(Float.parseFloat(this.tongtiensauVAT));
			long ttVAT = Math.round(tt / (1 + Float.parseFloat(this.VAT) / 100));
			//long tonggiatriDh = Math.round(this.ttsauCKKM); da lam trong ben javascript roi, lam tron nua se lech chut it
			String query = "update donhang set ngaynhap = '" + this.ngaygiaodich + "', ddkd_fk = '"+ this.ddkdId + "', gsbh_fk = " + this.gsbhId + ", khachhang_fk = '" + this.khId + "', chietkhau='" + this.tongchietkhau + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "', tonggiatri = '" + Float.toString(this.ttsauCKKM) +"', " +
					"vat = '" + Long.toString(ttVAT) + "',kbh_fk ='"+ kenh() +"' where pk_seq = '" + this.id + "' and TrangThai=3 ";
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				this.msg = "1.Khong the cap nhat table 'Don Hang' , " + query;
				return false; 
			}

			if(splist.size() > 0)
			{	
				query = "delete from donhang_sanpham where donhang_fk = '" + this.id + "'" ;
				System.out.println("2.Cap nhat don hang DXK: " + query);

				if(!db.update(query)){
					db.getConnection().rollback();
					this.msg = "2.Khong the cap nhat table 'donhang_sanpham' , " + query;
					return false; 
				}

				for(int m = 0; m < splist.size(); m++)
				{
					ISanpham sp = splist.get(m);
					String pk_seq = "";
					ResultSet rs = db.get("select pk_seq from sanpham where ma='" + sp.getMasanpham() + "'");
					if(rs != null) 
					{
						rs.next();
						pk_seq = rs.getString("pk_seq");
						rs.close();
					} 
					if(pk_seq != "")
					{
						query = "insert into donhang_sanpham(sanpham_fk, donhang_fk, soluong, kho_fk, giamua, chietkhau) values('" + pk_seq + "','" + this.id + "','" + sp.getSoluong() + "','" + this.khoId + "','" + sp.getDongia() + "', '" + sp.getChietkhau() + "')";
						System.out.println("3.Insert san pham don hang DXK: " + query);
						if(!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "Loi khi them moi bang donhang_sanpham, " + query;
							return false; 
						}
					}
				}

				//tao phieu thu hoi neu co
				if(this.trangthai.equals("3"))
				{
					this.createPxkId();
					String msg = this.createPth(this.pxkId, db);
					System.out.println("Messege Phieu thu hoi: " + msg);
					if(msg.length() > 0)
					{
						db.getConnection().rollback();
						this.msg = msg;
						return false; 
					}
				}

				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}


		} 
		catch(Exception e1) 
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e) {}
			this.msg="Loi :" + e1.toString();

			return false;
		}
		return true;
	}

	private String pxkId = "";
	
	private String ngayxuatkho = "";
	public void createPxkId()
	{
		String query = "select distinct a.pxk_fk, b.ngaylapphieu from phieuxuatkho_donhang a inner join phieuxuatkho b on a.pxk_fk = b.pk_seq where a.donhang_fk = '" + this.id + "'";
		ResultSet rs = db.get(query);		
		System.out.println("Query vao lay Phieu Xuat Kho La: " + query + "\n");
		try 
		{
			rs.next();
			this.pxkId = rs.getString("pxk_fk");
			this.ngayxuatkho = rs.getString("ngaylapphieu");
			rs.close();
		}
		catch(Exception e) {}
	}

	public void createRS() 
	{
		this.getNppInfo();

		//Lấy danh sách NPP trực thuộc MTV này
		String sql = "select pk_seq, ten from NHAPHANPHOI where trangthai = '1' and pk_seq != '" + this.nppId + "' ";
		if( this.tdv_dangnhap_id.trim().length() > 0 )
			sql += " and pk_seq in ( select npp_fk from DAIDIENKINHDOANH_NPP where ddkd_fk = '" + this.tdv_dangnhap_id + "' ) ";
		else
			sql += " and tructhuoc_fk = '" + this.nppId + "' ";
		
		sql += " and pk_seq in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ";
		this.tructhuocRs = db.get(sql);
		
		if( this.tructhuocId.trim().length() <= 0 )
			return;
		
		String query = "select PK_SEQ, TEN from KENHBANHANG where TRANGTHAI = '1' ";
		if( this.khId.trim().length() > 0 )
			query += " and pk_seq in ( select kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + this.khId + "'  ) ";
		this.kbhRs = db.get(query);
		
		this.createDdkd();	
		this.createKho();
		this.CreateNvgnList();
		this.Muctindung();
		
		//CHECK XEM CO TRONG BANG GIA DAC BIET KHONG, NEU CO THI HIEN SAN NHUNG SP CO TON LEN
		if( this.tructhuocId.trim().length() > 0 )
		{
			boolean dacoSP = false;
			if( this.splist != null )
			{
				for( int i = 0; i < this.splist.size(); i++  )
				{
					if( this.splist.get(i).getMasanpham().trim().length() > 0 )
						dacoSP = true;
				}
			}
			
			if( !dacoSP && this.khId.trim().length() > 0 )
			{
				query = "select COUNT(*) as sodong " +
						"	from BANGGIABANKHACHHANG a inner join BANGGIABANKHACHHANG_DLPP b on a.PK_SEQ = b.BGBANKHACHHANG_FK " +
						"	where a.TRANGTHAI = '1' and b.NPP_FK = '" + this.tructhuocId + "' ";
				System.out.println(":: CHECK GIA DAC BIET: " + query);
				boolean cogiaDB = false;
				ResultSet rs = db.get(query);
				if( rs != null )
				{
					try
					{
						if( rs.next() )
						{
							if( rs.getInt("sodong") > 0 )
								cogiaDB = true;
						}
						rs.close();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
				
				if( cogiaDB )
				{
					this.initSANPHAM_BG_DACBIET();
				}
			}
		}
		
	}

	private void initSANPHAM_BG_DACBIET() 
	{
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
		
		String command = " select  b.PK_SEQ as spId, b.MA as spMa, b.TEN as spTen, ISNULL ( (select kho.available from nhapp_kho kho where kho.sanpham_fk = b.pk_seq and kho.KHO_FK = '100000' and NPP_FK = '" + this.tructhuocId + "' and kho.NHOMKENH_FK = '100000' ), 0 ) as hienhuu,"+
						 " 		DV.donvi, 0 as soluong, a.dongia, a.DONGIA as dongiaGOC, 0 as chietkhau, "+
						 " 		b.thuexuat as THUEVAT, 1 as soluong1, 1 as soluong2,  0 as ptChietkhau_KMBH, 0 as khoNVBH  "+
						 " from BANGGIABANKHACHHANG_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ     "+
						 "  		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK       "+
						 " where a.BGBANKHACHHANG_FK in (  select top(1) PK_SEQ "+
						 " 								from BANGGIABANKHACHHANG bg inner join BANGGIABANKHACHHANG_DLPP bg_npp on bg.PK_SEQ = bg_npp.BGBANKHACHHANG_FK"+
						 " 								where bg_npp.NPP_FK = '" + this.tructhuocId + "'"+
						 " 								order by bg.PK_SEQ desc )"+
						 " 	and ISNULL ( (select kho.available from nhapp_kho kho where kho.sanpham_fk = b.pk_seq and kho.KHO_FK = '100000' and NPP_FK = '" + this.tructhuocId + "' and kho.NHOMKENH_FK = '100000' ), 0 ) > 0";

		System.out.println("22.San pham BGDB list:" + command);

		ResultSet splistRs = db.get(command);
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if(splistRs != null)
		{
			String[] param = new String[11];
			ISanpham sp = null;	
			try
			{
				while(splistRs.next())
				{
					param[0] = splistRs.getString("spId");
					param[1] = splistRs.getString("spma");
					param[2] = splistRs.getString("spten");
					param[3] = splistRs.getString("soluong");
					param[4] = splistRs.getString("donvi");
					param[5] = splistRs.getString("dongia");

					param[6] = "";
					param[7] = splistRs.getString("chietkhau");
					param[10]=splistRs.getString("dongia");
					int soluong1 = splistRs.getInt("soluong1");
					int soluong2 = splistRs.getInt("soluong2");

					//System.out.println("------Truoc khi lam tron: " + (splistRs.getDouble("soluong") * soluong2  / soluong1) + "  -- Lam tron 100 lan: " + Math.round( 100 * splistRs.getDouble("soluong") * soluong2  / soluong1 ) );
					double thung = Math.round( 100 * splistRs.getDouble("soluong") * soluong2  / soluong1 ) / 100.0;
					double sothung = Math.round( 10 * thung ) / 10.0;

					//System.out.println("------So luong 1: " + soluong1  + " -- Soluong 2: " + soluong2 + " -- Thung: " + thung + " -- Sluong thung: " + sothung);

					if( splistRs.getFloat("ptChietkhau_KMBH") > 0 )
						this.ptChietkhauBHKM = splistRs.getFloat("ptChietkhau_KMBH");
					
					sp = new Sanpham(param);
					//System.out.println("---Hien huu: " + splistRs.getDouble("hienhuu") + "  -- Formrt: " + df2.format(splistRs.getDouble("hienhuu")));
					sp.setTonhientai(df2.format(splistRs.getDouble("hienhuu")));
					sp.setSoluong1(Integer.toString(soluong1));
					sp.setSoluong2(Integer.toString(soluong2));
					sp.setSoluongThung(Double.toString(sothung));
					sp.setKhoNVBH(splistRs.getString("THUEVAT"));
					//sp.setSolo(splistRs.getString("SOLO"));
					//sp.setNgayHetHan(splistRs.getString("ngayhethan"));
					sp.setDongiaGoc(splistRs.getString("dongiaGOC"));

					splist.add(sp);

				}
				if(splistRs!=null){
					splistRs.close();
				}
			} 
			catch(Exception e) 
			{
				e.printStackTrace();
				System.out.println("___EXCEPTION SAN PHAM: " + e.getMessage());
			}
		}
		this.splist = splist;
		
		
	}

	private void createDdkd()
	{
		Utility util = new Utility();
		
		//tao gsbh
		String sql = "select a.pk_seq,a.ten from giamsatbanhang a inner join NHAPP_GIAMSATBH b on a.pk_seq = b.gsbh_fk " + 
					" where   b.ngaybatdau <='"+this.getDateTime()+"' and b.ngayketthuc >='"+getDateTime()+"' and   a.trangthai = '1' and npp_fk ='"+ this.tructhuocId +"'";
		sql += util.getPhanQuyen_TheoNhanVien("giamsatbanhang", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		if(this.tdv_dangnhap_id.trim().length() > 0)
		{
			this.ddkdId = this.tdv_dangnhap_id;
			sql = "select a.pk_seq,a.ten from giamsatbanhang a where a.pk_Seq in (select gsbh_fk from  ddkd_gsbh where ddkd_fk='"+this.ddkdId+"') ";
		}
		else if(this.ddkdId.length()>0)
		{
			sql += " union select a.pk_seq,a.ten from giamsatbanhang a where a.pk_Seq in (select gsbh_fk from  ddkd_gsbh where ddkd_fk='"+this.ddkdId+"') ";
		}
		
		sql += util.getPhanQuyen_TheoNhanVien("giamsatbanhang", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		this.gsbhs = db.get(sql);

		System.out.println("---GSBH ID: " + this.gsbhId+sql);
		String query=""; 
		if(this.gsbhId.trim().length() > 0 || this.tdv_dangnhap_id.trim().length() > 0 )
		{
			query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh a " +
					"where trangthai = '1' and pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.tructhuocId + "' ) " ;
			/*	"	and pk_seq in ( select ddkd_fk from ddkd_gsbh where gsbh_fk in (select gsbh_fk from  nhapp_giamsatbh where gsbh_fk ='" + this.gsbhId + "') )";*/
			
			if(this.tdv_dangnhap_id.trim().length() > 0)
			{
				this.ddkdId = this.tdv_dangnhap_id;
				query += " and pk_seq = '" + this.tdv_dangnhap_id + "' ";
			}
			
			query += util.getPhanQuyen_TheoNhanVien("daidienkinhdoanh", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		}

		if(this.khId.length() > 0)
		{
			query += "union "+
					" select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh  "+ 
					" where PK_SEQ in   "+
					" (select ddkd_fk from TUYENBANHANG a inner join KHACHHANG_TUYENBH b on b.TBH_FK=a.PK_SEQ where b.KHACHHANG_FK='"+khId+"' ) ";
		}
		
		System.out.println("--INIT DDKD: " + query);
		this.ddkdlist = db.get(query);

	}

	private void createKho()
	{
		//geso.traphaco.center.util.Utility utilCenter = new geso.traphaco.center.util.Utility();
		//String query = "select distinct PK_SEQ as khoId, Ten, Diengiai from KHO where PK_SEQ in (select kho_fk from NHAPP_KHO where npp_fk = '" + this.tructhuocId + "' and kho_fk in ( 100000, 100002 ) and kho_fk in "+utilCenter.quyen_kho(this.userId)+") order by PK_SEQ ASC";
		
		String query = "select distinct PK_SEQ as khoId, N'Hàng bán' as Ten, N'Hàng bán' as Diengiai from KHO where PK_SEQ in ( 100000 ) order by PK_SEQ ASC ";
		System.out.println("::: LAY KHO: " + query);
		this.kholist = db.get(query);
	}

	private void CreateSpList()
	{	
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
		String command = "select DISTINCT b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong,  " +
				"	isnull(c.donvi, 'Chua xac dinh') as donvi, a.giamua as dongia, a.dongiaGOC, " +
				"	case a.giamua when 0 then 0 else a.chietkhau * 100 / ( a.soluong * a.giamua ) end as chietkhau, a.THUEVAT, d.AVAILABLE as hienhuu, 1 as soluong1, 1 as soluong2, a.khoNVBH, " +
				//"	round( ( isnull(a.chietkhau_CSBH, 0) + isnull(a.chietkhau_KM, 0) ) * 100.0 / a.dongiaGOC, 1 ) as ptChietkhau_KMBH	" +
				"	case a.dongiaGOC when 0 then 0 else round( ( isnull(a.chietkhau_CSBH, 0) ) * 100.0 / a.dongiaGOC, 1 ) end as ptChietkhau_KMBH	" +
				"from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq " +
				"left join donvidoluong c on b.dvdl_fk = c.pk_seq  " +
				"inner join NHAPP_KHO d on b.PK_SEQ = d.SANPHAM_FK " +
				"where a.donhang_fk = '" + this.id + "' and d.KHO_FK = '" + this.khoId + "' and d.NPP_FK = '" + this.tructhuocId + "'" +
				//" and d.nhomkenh_fk = ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = '" + this.kbhId + "' )  ";
				" and d.nhomkenh_fk = ( 100000 )  ";

		System.out.println("22.San pham list:" + command);

		ResultSet splistRs = db.get(command);
		float tonggiatri = 0f; 
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if(splistRs != null)
		{
			String[] param = new String[11];
			ISanpham sp = null;	
			try
			{
				while(splistRs.next())
				{
					param[0] = splistRs.getString("spId");
					param[1] = splistRs.getString("spma");
					param[2] = splistRs.getString("spten");
					param[3] = splistRs.getString("soluong");
					param[4] = splistRs.getString("donvi");
					param[5] = splistRs.getString("dongia");

					param[6] = "";
					param[7] = splistRs.getString("chietkhau");
					param[10]=splistRs.getString("dongia");
					int soluong1 = splistRs.getInt("soluong1");
					int soluong2 = splistRs.getInt("soluong2");

					//System.out.println("------Truoc khi lam tron: " + (splistRs.getDouble("soluong") * soluong2  / soluong1) + "  -- Lam tron 100 lan: " + Math.round( 100 * splistRs.getDouble("soluong") * soluong2  / soluong1 ) );
					double thung = Math.round( 100 * splistRs.getDouble("soluong") * soluong2  / soluong1 ) / 100.0;
					double sothung = Math.round( 10 * thung ) / 10.0;

					//System.out.println("------So luong 1: " + soluong1  + " -- Soluong 2: " + soluong2 + " -- Thung: " + thung + " -- Sluong thung: " + sothung);

					if( splistRs.getFloat("ptChietkhau_KMBH") > 0 )
						this.ptChietkhauBHKM = splistRs.getFloat("ptChietkhau_KMBH");
					
					sp = new Sanpham(param);
					//System.out.println("---Hien huu: " + splistRs.getDouble("hienhuu") + "  -- Formrt: " + df2.format(splistRs.getDouble("hienhuu")));
					sp.setTonhientai(df2.format(splistRs.getDouble("hienhuu")));
					sp.setSoluong1(Integer.toString(soluong1));
					sp.setSoluong2(Integer.toString(soluong2));
					sp.setSoluongThung(Double.toString(sothung));
					sp.setKhoNVBH(splistRs.getString("THUEVAT"));
					//sp.setSolo(splistRs.getString("SOLO"));
					//sp.setNgayHetHan(splistRs.getString("ngayhethan"));
					sp.setDongiaGoc(splistRs.getString("dongiaGOC"));

					splist.add(sp);

				}
				if(splistRs!=null){
					splistRs.close();
				}
			} 
			catch(Exception e) 
			{
				e.printStackTrace();
				System.out.println("___EXCEPTION SAN PHAM: " + e.getMessage());
			}
		}
		this.splist = splist;
	}

	public void CreateSpDisplay()
	{		
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );

		String command = "select DISTINCT b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong, isnull(c.donvi, 'Chua xac dinh') as donvi, a.giamua as dongia, " +
				"a.chietkhau, d.AVAILABLE as hienhuu, qc.soluong1, qc.soluong2, " +
				"	case a.dongiaGOC when 0 then 0 else round( ( isnull(a.chietkhau_CSBH, 0) ) * 100.0 / a.dongiaGOC, 1 ) end as ptChietkhau_KMBH	" +
				"from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq " +
				"left join donvidoluong c on b.dvdl_fk = c.pk_seq inner join quycach qc on b.pk_seq = qc.sanpham_fk " +
				"inner join NHAPP_KHO d on b.PK_SEQ = d.SANPHAM_FK " +
				"where a.donhang_fk = '" + this.id + "' and d.KHO_FK = '" + this.khoId + "' and d.NPP_FK = '" + this.tructhuocId + "'" +
				" and d.KBH_FK = '" + this.kbhId + "' and qc.dvdl2_fk = '100018' ";

		System.out.println("2.San pham list:" + command);

		ResultSet splistRs = db.get(command);
		float tonggiatri = 0f; 
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if(splistRs != null)
		{
			String[] param = new String[8];
			ISanpham sp = null;	
			try
			{
				while(splistRs.next())
				{
					param[0] = splistRs.getString("spId");
					param[1] = splistRs.getString("spma");
					param[2] = splistRs.getString("spten");
					param[3] = splistRs.getString("soluong");
					param[4] = splistRs.getString("donvi");
					param[5] = splistRs.getString("dongia");
					param[6] = "";

					if(this.IsSampling.equals("0"))
					{
						float ck = splistRs.getFloat("chietkhau");
						int slg = Integer.parseInt(param[3]);
						float tt = 0f;
						if(slg != 0)
						{	
							tt = (ck / (Integer.parseInt(param[3]) * Float.parseFloat(param[5]))) * 100;
							tt = new Float(df2.format(tt)).floatValue(); //lam tron 2 so
						}
						this.chietkhau = Float.toString(tt);

						param[7] = this.chietkhau;
						tonggiatri += Float.parseFloat(param[5]) * Float.parseFloat(param[3]);
					}
					else
					{
						this.chietkhau = "0";
						tonggiatri = 0;
					}

					param[7] = this.chietkhau;

					int soluong1 = splistRs.getInt("soluong1");
					int soluong2 = splistRs.getInt("soluong2");

					//System.out.println("------Truoc khi lam tron: " + (splistRs.getDouble("soluong") * soluong2  / soluong1) + "  -- Lam tron 100 lan: " + Math.round( 100 * splistRs.getDouble("soluong") * soluong2  / soluong1 ) );
					double thung = Math.round( 100 * splistRs.getDouble("soluong") * soluong2  / soluong1 ) / 100.0;

					double sothung = Math.round( 10 * thung ) / 10.0;

					//System.out.println("------So luong 1: " + soluong1  + " -- Soluong 2: " + soluong2 + " -- Thung: " + thung + " -- Sluong thung: " + sothung);

					if( splistRs.getFloat("ptChietkhau_KMBH") > 0 )
						this.ptChietkhauBHKM = splistRs.getFloat("ptChietkhau_KMBH");
					
					sp = new Sanpham(param);
					sp.setTonhientai(splistRs.getString("hienhuu"));
					sp.setSoluong1(Integer.toString(soluong1));
					sp.setSoluong2(Integer.toString(soluong2));
					sp.setSoluongThung(Double.toString(sothung));

					splist.add(sp);

				}
				if(splistRs!=null){
					splistRs.close();
				}
			} 
			catch(Exception e) {}
		}
		this.splist = splist;
	}

	private void CreateNvgnList()
	{
		String query = "select * from nhanviengiaonhan where NPP_FK = " + this.tructhuocId ;
		if(this.khId.trim().length() > 0)
			query += " and pk_seq in ( select nvgn_fk from NVGN_KH  where khachhang_fk = '" + this.khId + "' ) ";

		this.nvgnRs = db.get(query);
		System.out.println("nvgn:" + query );
	}

	public void init()
	{	
		this.getNppInfo();

		String query = "select a.ingia,a.NVGN_FK,a.gsbh_fk,a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, a.khachhang_fk as khId," +
				" g.ten as khTen, g.diachi, isnull(g.chucuahieu, '') as chucuahieu, isnull(g.maFAST, '') as smartid,  a.kho_fk as khoId, a.tonggiatri, b.ten as nguoitao," +
				" c.ten as nguoisua, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, a.VAT, isnull(a.chietkhau, 0) as chietkhau, " +
				"isnull(a.chietkhau, 0) as ptck, a.kbh_fk, isnull(a.ghichu, '') as ghichu,isnull(dk.dat, 0) as dktrungbay, a.IsDonHangLe, a.IsSampling, g.THANHTOAN, isnull(a.DONHANGKHAC, 0) as DONHANGKHAC, isnull(denghitraCK , 0) as denghitraCK, donquatang, isnull(a.sotaikhoan, '') as sotaikhoan, a.npp_fk  " +
				" from donhang a left join nhanvien b on a.nguoitao = b.pk_seq left join nhanvien c on a.nguoisua = c.pk_seq " +
				" inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq " +
				" inner join khachhang g on a.khachhang_fk=g.pk_seq left join DKTRUNGBAY_DONHANG dk on a.pk_seq = dk.donhang_fk " +
				" where a.pk_seq='" + this.id + "'";

		System.out.println("1.Init don hang:" + query);
		ResultSet rs =  db.get(query);
		if(rs != null)
		{
			try
			{
				rs.next();
				this.id = rs.getString("dhId");

				this.khId = rs.getString("khId");
				this.khTen = rs.getString("khTen") + " - " + rs.getString("diachi");
				this.chucuahieu = rs.getString("chucuahieu");

				this.smartId = rs.getString("smartid"); // rs.getString("smartId").substring(rs.getString("smartId").indexOf("_")+1, rs.getString("smartId").length());							

				this.nvgnId=rs.getString("NVGN_FK");
				this.ngaygiaodich = rs.getString("ngaynhap");
				this.daidienkd = rs.getString("ddkdTen");
				this.trangthai = rs.getString("trangthai");
				this.ngaytao = rs.getString("ngaytao");
				this.nguoitao = rs.getString("nguoitao");
				this.ngaysua = rs.getString("ngaysua");
				this.nguoisua = rs.getString("nguoisua");				
				this.VAT = "0";  //OneOne, gia SP da co thue
				this.ddkdId = rs.getString("ddkdId");
				this.khoId = rs.getString("khoId");
				this.chietkhau = rs.getString("ptck");
				this.tongchietkhau = rs.getString("chietkhau");
				this.chietkhaucu=rs.getString("ptck");
				this.ghichu = rs.getString("ghichu");
				System.out.println("---PTCK: " + rs.getString("ptck") + " -- TONG CK: " + this.tongchietkhau );
				this.tongtiensauVAT = rs.getString("tonggiatri");
				this.ingia=rs.getString("ingia");
				
				this.tongtientruocVAT = rs.getString("VAT");

				this.gsbhId = rs.getString("gsbh_fk");
				this.kbhId = rs.getString("kbh_fk");

				if(rs.getInt("dktrungbay") > 0)
					this.aplaitb = true;

				this.IsDonHangLe = rs.getString("IsDonHangLe");
				this.IsSampling = rs.getString("IsSampling");
				this.loaiNppId = rs.getString("THANHTOAN");
				this.donhangKhac= rs.getString("DONHANGKHAC");
				if(donhangKhac.equals(1))
					this.ttsauCKKM=0;
				this.trangthai = rs.getString("trangthai");
				this.denghitraCKTHANG = rs.getString("denghitraCK");
				this.donquatang = rs.getString("donquatang");
				this.sotaikhoan = rs.getString("sotaikhoan");
				this.tructhuocId = rs.getString("npp_fk");

				rs.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("33.Exception: " + e.getMessage());
			}
		}
		
		//Lấy danh sách NPP trực thuộc MTV này
		String sql = "select pk_seq, ten from NHAPHANPHOI where trangthai = '1' and pk_seq != '" + this.nppId + "' ";
		if( this.tdv_dangnhap_id.trim().length() > 0 )
			sql += " and pk_seq in ( select npp_fk from DAIDIENKINHDOANH_NPP where ddkd_fk = '" + this.tdv_dangnhap_id + "' ) ";
		else
			sql += " and tructhuoc_fk = '" + this.nppId + "' ";
		this.tructhuocRs = db.get(sql);
		
		sql = "select PK_SEQ, TEN from KENHBANHANG where TRANGTHAI = '1' ";
		if( this.khId.trim().length() > 0 )
			sql += " and pk_seq in ( select kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + this.khId + "'  ) ";
		this.kbhRs = db.get(sql);
		
		Utility util = new Utility();
		
		sql = "select a.pk_seq, a.ten from giamsatbanhang a inner join NHAPP_GIAMSATBH b on a.pk_seq = b.gsbh_fk where  ngaybatdau <='"+this.getDateTime()+"' and ngayketthuc >='"+getDateTime()+"' and npp_fk ='" + this.tructhuocId + "' and a.trangthai = '1'";
		sql += util.getPhanQuyen_TheoNhanVien("giamsatbanhang", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		if(this.ddkdId.length()>0)
		{
			sql += " union all " + 
				   " select a.pk_seq, a.ten from giamsatbanhang a where a.pk_Seq in (select gsbh_fk from  ddkd_gsbh where ddkd_fk='"+this.ddkdId+"') and a.trangthai=1 ";
			sql += util.getPhanQuyen_TheoNhanVien("giamsatbanhang", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		}

		System.out.println(":: LAY GSBH: " + sql );
		this.gsbhs = db.get(sql);
		
		sql = "";
		if(this.gsbhId == null)
		{
			sql = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh a where trangthai = '1' and pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.tructhuocId + "' ) ";
		}
		else
		{
			sql = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh a where trangthai = '1' and pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.tructhuocId + "' ) ";
		}
		
		sql += util.getPhanQuyen_TheoNhanVien("daidienkinhdoanh", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		System.out.println(":: LAY DDKD: " + sql );
		this.ddkdlist = db.get(sql);

		this.CreateSpList();
		this.createKho();
		this.CreateNvgnList();

		if(this.donhangKhac.equals("0"))
		{
			this.getTrakhuyenmai();
			this.initTichLuy();
		}
	}

	public void initTichLuy() 
	{
		/*String query = "select N'CN5' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 1 as STT, 0 as tichluyQUY, 0 as cotheXOA from DONHANG_SANPHAM  " +
				"	where donhang_fk = '" + this.id + "' and thueVAT = '5' and chietkhau > 0 group by thueVAT " +
				"union  " +
				"	select N'CN10' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 2 as STT, 0 as tichluyQUY, 0 as cotheXOA  " +
				"	from DONHANG_SANPHAM  " +
				"	where donhang_fk = '" + this.id + "' and thueVAT = '10' and chietkhau > 0 group by thueVAT " +
				"union " +
				"	select a.diengiai, a.thanhtoan / ( 1 + ptTHUE / 100 ) as chietkhau, a.ptTHUE as thueVAT, 3 as STT, tichluyQUY, 1 as cotheXOA  " +
				"	from DUYETTRAKHUYENMAI_DONHANG a inner join TIEUCHITHUONGTL b on a.duyetkm_fk = b.PK_SEQ   " +
				"	where a.donhang_fk = '" + this.id + "' and HIENTHI = '1' order by STT, tichluyQUY ";

		
		System.out.println("---INIT TICH LUY: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			String schemeMa = "";
			String schemeVAT = "";
			String schemeTt = "";
			String schemeCtx = "";

			try 
			{
				NumberFormat format = new DecimalFormat("#,###,###.###");
				while(rs.next())
				{
					schemeMa += rs.getString("diengiai") + "__";
					schemeVAT += rs.getString("thueVAT") + "__";
					schemeCtx += rs.getString("cotheXOA") + "__";
					schemeTt += format.format((-1) * rs.getDouble("chietkhau")) + "__";
				}
				rs.close();

				if(schemeMa.trim().length() > 0)
				{
					schemeMa = schemeMa.substring(0, schemeMa.length() - 2);
					this.tichluy_scheme = schemeMa.split("__");

					schemeVAT = schemeVAT.substring(0, schemeVAT.length() - 2);
					this.tichluy_vat = schemeVAT.split("__");

					schemeTt = schemeTt.substring(0, schemeTt.length() - 2);
					this.tichluy_tongtien = schemeTt.split("__");

					schemeCtx = schemeCtx.substring(0, schemeCtx.length() - 2);
					this.tichluy_cothexoa = schemeCtx.split("__");
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("__EXCEPTION: " + e.getMessage());
			}
		}	*/
	}

	public String getBgstId() 
	{
		return this.bgstId;
	}

	public void setBgstId(String bgstId)
	{
		this.bgstId = bgstId;
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
		return this.kholist;
	}

	public void setKhoList(ResultSet kholist) 
	{
		this.kholist = kholist;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	private String getThoiDiemHienTai() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void DBclose()
	{
		try 
		{
			if(!(this.kholist==null))
				this.kholist =null;

			if(!(this.ddkdlist == null))
				this.ddkdlist.close();
			if(!(this.tbhlist == null))
				this.tbhlist.close();
			if(this.khlist!=null){
				this.khlist.close();
			}
			if(this.gsbhs!=null){
				gsbhs.close();
			}
			splist=null;
			spThieuList=null;
			scheme_tongtien=null;
			scheme_chietkhau=null;
			scheme_sanpham=null;

			this.db.shutDown();
		} 
		catch(Exception e) {}
	}

	public void setMuctindung(String muctindung) {

		this.muctindung = muctindung;
	}

	public String getMuctindung() {

		return this.muctindung;
	}

	public ResultSet getgsbhs()
	{	
		return this.gsbhs;
	}

	public String getTongChietKhau() 
	{
		return this.tongchietkhau;
	}

	public void setTongChietKhau(String tck) 
	{
		this.tongchietkhau = tck;
	}

	public String getKHList()
	{
		String khId = "";
		String khTen = "";
		String khChietKhau = "";
		String khList ="";
		String command = "select b.pk_seq as khId, b.ten as khTen, c.chietkhau " + 
				"from khachhang_tuyenbh a inner join khachhang b on a.khachhang_fk = b.pk_seq "+ 
				"inner join tuyenbanhang d on a.tbh_fk = d.pk_seq " +
				"left join mucchietkhau c on b.chietkhau_fk = c.pk_seq "+ 
				"where b.npp_fk='"+ this.nppId +"' and d.ddkd_fk= '"+ this.ddkdId +"' order by khId, khTen, chietkhau";

		ResultSet kh = db.get(command);
		try{
			if(kh != null)
			{
				while(kh.next())
				{
					khId = khId + kh.getString("khId") + ",";
					khTen = khTen + kh.getString("khTen") + ",";

					if (kh.getString("chietkhau") != null && !kh.wasNull())
						khChietKhau = khChietKhau + kh.getString("chietkhau") + ",";
					else
						khChietKhau = khChietKhau + "0,";

				}

			}
			khId = khId.substring(0, khId.length() - 1);
			khTen = khTen.substring(0, khTen.length() - 1);
			khChietKhau = khChietKhau.substring(0, khChietKhau.length() - 1);

			String[] khIdList = khId.split(",");
			String[] khTenList = khTen.split(",");
			String[] khChietKhauList = khChietKhau.split(",");

			int cnt=1;
			for(int i=0; i<khTenList.length;i++)
			{
				if (i!= khTenList.length-1){
					khList = khList + "\""+ khIdList[i] + "-->[" + khTenList[i] + "][" + khChietKhauList[i] + "]\",";
				}else{
					khList = khList + "\""+ khIdList[i] + "-->[" + khTenList[i] + "][" + khChietKhauList[i] + "]\"";
				}

			}	
			if(kh!=null){
				kh.close();
			}
		}
		catch(Exception e){}
		return khList;
	}

	public boolean isAplaikhuyenmai() 
	{
		return this.aplaikm;
	}

	public void setAplaikhuyenmai(boolean aplaikm) 
	{
		this.aplaikm = aplaikm;
	}

	public String createPth(String pxkId, dbutils db)
	{		
		String msg = "";
		try 
		{
			db.getConnection().setAutoCommit(false);

			List<ISanpham> spThuhoiList = this.getSpthuhoiList(pxkId, db);
			List<ISanpham> spkmThuhoiList = this.getSpkmthuhoiList(pxkId, db);
			if(spThuhoiList.size() > 0 || spkmThuhoiList.size() > 0)
			{	
				//Xoa cac phieu thu hoi cu cua pxk nay co trang thai = 0
				String query = "delete from phieuthuhoi_sanpham where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '" + pxkId + "' and trangthai = 0)";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					msg = "1.Lỗi tạo phiếu thu hồi: " + query;
					return msg;
				}

				query= "delete from phieuthuhoi_spkm where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '" + pxkId + "' and trangthai = 0)";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					msg = "2.Lỗi tạo phiếu thu hồi: " + query;
					return msg;
				}

				query = "select DONHANG_FK as dhId from PHIEUTHUHOI_DONHANG where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '" + pxkId + "' and trangthai = 0) and donhang_fk != '" + this.id + "'";
				ResultSet rsDh = db.get(query);
				String dhs = "";
				if(rsDh != null)
				{
					while(rsDh.next())
					{
						if(rsDh.getString("dhId") != null)
							dhs += rsDh.getString("dhId") + ",";
					}
					rsDh.close();
				}

				query= "delete from phieuthuhoi_donhang where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '" + pxkId + "' and trangthai = 0)";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					msg = "3.Lỗi tạo phiếu thu hồi: " + query;
					return msg;
				}

				query= "delete from phieuthuhoi where phieuxuatkho_fk = '" + pxkId + "' and trangthai = 0";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					msg = "4.Lỗi tạo phiếu thu hồi: " + query;
					return msg;
				}


				query = "insert into phieuthuhoi(phieuxuatkho_fk, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, donhang_fk, npp_fk) ";
				query = query + "values('" + pxkId + "','0','" + this.ngayxuatkho + "','" + this.getDateTime() + "','" + this.userId + "','" + this.userId + "', '" + this.id + "', '" + this.nppId + "')";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					msg = "5.Loi khi them moi bang 'phieuthuhoi', " + query;
					return msg; 
				}

				query = "select IDENT_CURRENT('phieuthuhoi') as pthId";
				String pthId = "";
				ResultSet rsPth = db.get(query);									
				rsPth.next();
				pthId = rsPth.getString("pthId");
				rsPth.close();

				//luu vao bang phieuthuhoi_sp
				for(int i = 0; i < spThuhoiList.size(); i++)
				{
					Sanpham sp  = (Sanpham)spThuhoiList.get(i);

					if(Double.parseDouble(sp.getSoluong()) != 0)
					{
						//DOn vi tinh luu kho_fk, don gia se luu kbh_fk	
						query = "Insert into phieuthuhoi_sanpham(pth_fk, sanpham_fk, soluong, kho_fk, kbh_fk) values(" + pthId + ", '" + sp.getId() + "','" + sp.getSoluong() + "', '" + sp.getDonvitinh() + "', '" + sp.getDongia() + "')";

						if(!db.update(query))
						{
							db.getConnection().rollback();
							msg = "6.Loi khi them moi bang 'phieuthuhoi_sanpham', " + query;
							return msg; 
						}
					}
				}

				//luu vao bang phieuthuhoi_spkm (chi tao khi in phieu thu hoi cuoi cung)
				for(int i = 0; i < spkmThuhoiList.size(); i++)
				{
					Sanpham sp  = (Sanpham)spkmThuhoiList.get(i);

					if(Double.parseDouble(sp.getSoluong()) != 0)
					{
						//DOn vi tinh luu kho_fk, don gia se luu kbh_fk	
						query = "Insert into phieuthuhoi_spkm(pth_fk, sanpham_fk, soluong, kho_fk, kbh_fk) values('" + pthId + "', '" + sp.getId() + "','" + sp.getSoluong() + "', '" + sp.getDonvitinh() + "', '" + sp.getDongia() + "')";
						if(!db.update(query))
						{
							db.getConnection().rollback();
							msg = "7.Loi khi them moi bang 'phieuthuhoi_spkm', " + query;
							return msg; 
						}
					}
				}

				query = "insert PHIEUTHUHOI_DONHANG(pth_fk, pxk_fk, donhang_fk) values('" + pthId + "', '" + this.pxkId + "', '" + this.id + "')";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					msg = "Loi khi them moi bang 'PHIEUTHUHOI_DONHANG', " + query;
					return msg; 
				}

				if(dhs.length() > 0)
				{
					String[] donhangs = dhs.split(",");
					for(int i = 0; i < donhangs.length; i++)
					{
						query = "insert PHIEUTHUHOI_DONHANG(pth_fk, pxk_fk, donhang_fk) values('" + pthId + "', '" + this.pxkId + "', '" + donhangs[i].trim() + "')";
						if(!db.update(query))
						{
							db.getConnection().rollback();
							msg = "8.Loi khi them moi bang 'PHIEUTHUHOI_DONHANG', " + query;
							return msg; 
						}
					}
				}
			} 
		}
		catch(Exception e1) 
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e) {}

			msg = "Loi khi tao moi phieu thu hoi :, " + e1.toString();
			e1.printStackTrace();
			return msg; 
		}
		return msg;
	}

	private List<ISanpham> getSpthuhoiList(String pxkId, dbutils db)
	{
		List<ISanpham> spOldList = new ArrayList<ISanpham>();
		String query = "select sanpham_fk, soluong, kbh_fk, kho_fk from phieuxuatkho_sanpham where pxk_fk = '" + pxkId + "'";

		//System.out.println("Cau lenh lay du lieu la: " + query + "\n");
		ResultSet spThuhoi = db.get(query);
		if(spThuhoi!= null)
		{
			try 
			{
				while(spThuhoi.next())
				{								
					//ISanpham sp = new Sanpham(spThuhoi.getString("spId"), spThuhoi.getString("spMa"), spThuhoi.getString("spTen"), spThuhoi.getString("soluong"), spThuhoi.getString("khoId"), spThuhoi.getString("kbhId"), "", "");
					ISanpham sp = new Sanpham(spThuhoi.getString("sanpham_fk"), "", "", spThuhoi.getString("soluong"), spThuhoi.getString("kho_fk"), spThuhoi.getString("kbh_fk"), "", "");
					spOldList.add(sp);	
				}
				spThuhoi.close();
			} 
			catch(Exception e) {}
		}

		//System.out.println("Size san pham thu hoi buoc 1 la: " + spOldList.size() + "\n");

		//Nhung phieu thu hoi cua phieu xuat kho nay
		query = "select sanpham_fk as spId, soluong, kho_fk as khoId, kbh_fk as kbhId from phieuthuhoi_sanpham where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '" + pxkId + "' and trangthai = '1')";
		query += " union all ";
		query += "select c.sanpham_fk as spId, soluong, b.kho_fk as khoId, b.kbh_fk as kbhId from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq inner join donhang_sanpham c on b.pk_seq = c.donhang_fk where b.trangthai != '2' and a.pxk_fk = '" + pxkId + "'";

		query = "select spId, sum(soluong) as soluong, khoId, kbhId from (" + query + ") kh group by spId, khoId, kbhId";

		//System.out.println("Query lay san pham aaaaa la: " + query + "\n");

		List<ISanpham> spNewList = new ArrayList<ISanpham>();
		ResultSet spThuhoi2 = db.get(query);
		if(spThuhoi2 != null)
		{
			try 
			{
				while(spThuhoi2.next())
				{								
					ISanpham sp = new Sanpham(spThuhoi2.getString("spId"), "", "", spThuhoi2.getString("soluong"), spThuhoi2.getString("khoId"), spThuhoi2.getString("kbhId"), "", "");

					spNewList.add(sp);	
				}
				spThuhoi2.close();
			} 
			catch(Exception e) {}
		}

		//List<ISanpham> spNewList = new ArrayList<ISanpham>();
		//sanpham hien tai
		/*
		query = "select b.kho_fk as khoId, b.kbh_fk as kbhId, c.sanpham_fk as spId, sum(c.soluong) as soluong from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq inner join donhang_sanpham c on b.pk_seq = c.donhang_fk ";
		query += "where b.trangthai != '2' and a.pxk_fk = '" + pxkId + "' group by b.kho_fk, b.kbh_fk, c.sanpham_fk";
		System.out.println("Cau lenh lay du lieu ben duoi: " + query + "\n");
		ResultSet spThuhoi3 = db.get(query);
		if(spThuhoi3 != null)
		{
			try 
			{
				while(spThuhoi3.next())
				{								
					ISanpham sp = new Sanpham(spThuhoi3.getString("spId"), "", "", spThuhoi3.getString("soluong"), spThuhoi3.getString("khoId"), spThuhoi3.getString("kbhId"), "", "");

					spNewList.add(sp);	
				}
				spThuhoi3.close();
			} 
			catch(Exception e) {}
		}
		 */
		//cong don san pham
		//thu hoi sp trakhyenmai (ve kho_fk, kbh_fk)
		List<ISanpham> spkmList = new ArrayList<ISanpham>();

		for(int i = 0; i < spOldList.size(); i++)
		{
			Sanpham spA  = (Sanpham)spOldList.get(i);
			for(int j = 0; j < spNewList.size(); j++)
			{				
				Sanpham spB = (Sanpham)spNewList.get(j);
				if((spB.getId().trim().equals(spA.getId().trim())) && (spB.getDonvitinh().trim().equals(spA.getDonvitinh().trim())) && (spB.getDongia().trim().equals(spA.getDongia().trim())))
				{
					double slg = Math.abs(Double.parseDouble(spA.getSoluong()) - Double.parseDouble(spB.getSoluong()));

					spOldList.get(i).setSoluong( Double.toString(Math.round(slg)) );

					spNewList.remove(j);
					j = 0;
				}
			}
			if(Double.parseDouble(spOldList.get(i).getSoluong()) > 0)
			{
				ISanpham sp = new Sanpham(spOldList.get(i).getId(), "", "", spOldList.get(i).getSoluong(), spOldList.get(i).getDonvitinh(), spOldList.get(i).getDongia(), "", "");
				spkmList.add(sp);

				//System.out.println("So luong luc dau thu hoi co so luong la: " + sp.getSoluong() + "\n");
			}
		}

		//System.out.println("Size san pham thu hoi buoc 2 la: " + spkmList.size() + "\n");
		return spkmList;
	}

	private List<ISanpham> getSpkmthuhoiList(String pxkId, dbutils db)
	{
		//spkm trong phieuxuatkho cu
		List<ISanpham> spkmOldList = new ArrayList<ISanpham>();
		String query = "select kho_fk as khoId, kbh_fk as kbhId, sanpham_fk as spId, sum(soluong) as soluong from phieuxuatkho_spkm where pxk_fk = '" + pxkId + "' group by kho_fk, kbh_fk, sanpham_fk";

		//System.out.println("Query lan 1: " + query + "\n");

		ResultSet spOld = db.get(query);
		if(spOld != null)
		{
			try 
			{
				while(spOld.next())
				{										
					ISanpham sp = new Sanpham(spOld.getString("spId"), "", "", spOld.getString("soluong"), spOld.getString("khoId"), spOld.getString("kbhId"), "", "");
					spkmOldList.add(sp);
				}
				spOld.close();
			} 
			catch(Exception e) {}
		}

		//tinh lai so lg cac spkm phai thu hoi (nhung donhang daxuatkho ma huy thi ko can lay spkm, khi do spkmOldList se thu hoi dung soluong daxuatkho bandau cua nhung don hang da huy nay)
		//List<ISanpham> spkmNewList = new ArrayList<ISanpham>();
		//nhung phieu thu hoi da chot cua phieu xuat kho nay
		query = "select sanpham_fk as spId, soluong, kho_fk as khoId, kbh_fk as kbhId from phieuthuhoi_spkm where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '" + pxkId + "' and trangthai = '1')";
		query += " union all ";
		query += "select d.pk_seq as spId, soluong, b.kho_fk as khoId, e.kbh_fk as kbhId from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq where a.spMa is not null and e.trangthai != '2' and a.donhangId in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + pxkId + "') ";

		query = "select spId, sum(soluong) as soluong, khoId, kbhId from (" + query + ") kh group by spId, khoId, kbhId";

		//System.out.println("Query lay spkm: " + query +  "\n");

		List<ISanpham> spkmNewList = new ArrayList<ISanpham>();
		ResultSet spThuhoi2 = db.get(query);
		if(spThuhoi2 != null)
		{
			try 
			{
				while(spThuhoi2.next())
				{								
					ISanpham sp = new Sanpham(spThuhoi2.getString("spId"), "", "", spThuhoi2.getString("soluong"), spThuhoi2.getString("khoId"), spThuhoi2.getString("kbhId"), "", "");

					spkmNewList.add(sp);	
				}
				spThuhoi2.close();
			} 
			catch(Exception e) {}
		}


		//System.out.println("Query thu hoi khuyen mai ben duoi: " + query + "\n");
		/*
		ResultSet spKhuyenmaiRS = db.get(query);
		if(spKhuyenmaiRS != null)
		{
			try 
			{
				while(spKhuyenmaiRS.next())
				{
					String[] param = new String[8];
					ISanpham sp = null;	

					param[0] = spKhuyenmaiRS.getString("spId");
					param[1] = "";
					param[2] = "";
					param[3] = spKhuyenmaiRS.getString("soluong");

					//luu kho - don vi tinh
					param[4] = "";
					if(spKhuyenmaiRS.getString("khoId") != null)
						param[4] = spKhuyenmaiRS.getString("khoId");

					//luu kenh ban hang - don gia
					param[5] = "";
					if(spKhuyenmaiRS.getString("kbhId") != null)
						param[5] = spKhuyenmaiRS.getString("kbhId");

					param[6] = "";
					param[7] = "";

					sp = new Sanpham(param);
					spkmNewList.add(sp);
				}
				spKhuyenmaiRS.close();
			} 
			catch(Exception e) {}
		}
		 */
		//thu hoi sp trakhyenmai (ve kho_fk, kbh_fk)
		List<ISanpham> spkmList = new ArrayList<ISanpham>();

		for(int i = 0; i < spkmOldList.size(); i++)
		{
			Sanpham spA  = (Sanpham)spkmOldList.get(i);
			for(int j = 0; j < spkmNewList.size(); j++)
			{				
				Sanpham spB = (Sanpham)spkmNewList.get(j);
				if((spB.getId().trim().equals(spA.getId().trim())) && (spB.getDonvitinh().trim().equals(spA.getDonvitinh().trim())) && (spB.getDongia().trim().equals(spA.getDongia().trim())))
				{

					double slg = Math.abs(Double.parseDouble(spA.getSoluong()) - Double.parseDouble(spB.getSoluong()));

					spkmOldList.get(i).setSoluong(Double.toString(Math.round(slg)));
					spkmNewList.remove(j);
					j = 0; //quet lai
				}
			}
			if(Double.parseDouble(spkmOldList.get(i).getSoluong()) > 0)
			{
				ISanpham sp = new Sanpham(spkmOldList.get(i).getId(), "", "", spkmOldList.get(i).getSoluong(), spkmOldList.get(i).getDonvitinh(), spkmOldList.get(i).getDongia(), "", "");
				spkmList.add(sp);
			}
		}
		return spkmList;
	}

	public String getPxkId() 
	{
		return this.pxkId;
	}

	public void setPxkId(String pxkId) 
	{
		this.pxkId = pxkId;
	}

	public String DeleteDonHangDxk() 
	{
		try
		{
			db.getConnection().setAutoCommit(true);

			String query = "update donhang set trangthai='2' where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "1.Khong the xoa don hang: " + this.id + ", " + query;
			}



			//cap nhat phan bo km
			query = "select ctkmId, sum(tonggiatri) as tonggiatri from donhang_ctkm_trakm where donhangid = '" + this.id + "' group by ctkmId";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try
				{
					while(rs.next())
					{
						String ctkmId = rs.getString("ctkmId");
						String tonggiatri = rs.getString("tonggiatri");

						String st ="update Phanbokhuyenmai set DASUDUNG = DASUDUNG - '" + tonggiatri + "' where ctkm_fk='" + ctkmId + "' and npp_fk='" + this.nppId + "'";
						//db.update("update CTKhuyenmai set DASUDUNG = DASUDUNG - '" + tonggiatri + "' where ctkm_fk = '" + ctkmId + "'");
						if(!db.update(st)){
							this.db.getConnection().rollback();
							return "Khong The Cap Nhat :"+ st;
						}

					}
					rs.close();
				} 

				catch(Exception e) {
					this.db.getConnection().rollback();
					return e.toString();
				}

			}

			this.createPxkId();
			if(this.pxkId.length() > 0)
			{
				String msg = this.createPth(this.pxkId, db);
				if(msg.length() > 0)
				{
					db.getConnection().rollback();
					return "4.Khong the tao phieu thu hoi cua don hang: " + this.id + ", " + msg;
				}
			}

			//Xoa trung bay
			//truong hop cap nhat, phai xoa so xuat cu
			query = "select dktrungbay_fk, khachhang_fk, dat from DKTRUNGBAY_DONHANG where donhang_fk = '" + this.id + "'";
			ResultSet rsDelete = db.get(query);
			if(rsDelete != null)
			{
				while(rsDelete.next())
				{
					String dk_fk = rsDelete.getString("dktrungbay_fk");
					String kh_fk = rsDelete.getString("khachhang_fk");
					String dat = rsDelete.getString("dat");

					query = "delete DKTRUNGBAY_DONHANG where dktrungbay_fk = '" + dk_fk + "' and khachhang_fk = '" + kh_fk + "' and donhang_fk = '" + this.id + "'";
					if(!db.update(query))
					{

						db.getConnection().rollback();
						return "5.Không thể cập nhậtDKTRUNGBAY_DONHANG " + this.id + ", " + query;
					}

					query = "update DKTRUNGBAY_KHACHHANG set dat = dat - " + dat + " where dktrungbay_fk = '" + dk_fk + "' and khachhang_fk = '" + kh_fk + "'";
					if(!db.update(query))
					{
						db.getConnection().rollback();
						return "2.Không thể cập DKTRUNGBAY_KHACHHANG "  + this.id + ", " + query;
					}

				}
				rsDelete.close();
			}


			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		}

		catch (Exception e) 
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) {}

			return "5.Khong the xoa don hang: " + this.id + ", " + e.getMessage();
		}

		return "";

	}

	public boolean isCokhuyenmai() 
	{
		return this.cokm;
	}

	public void setCokhuyenmai(boolean cokm) 
	{
		this.cokm = cokm;
	}

	public boolean isChuaApkhuyenmai() 
	{	
		return this.chuaApkm;
	}

	public void setIsChuaApkhuyenmai(boolean chuaApkm) 
	{
		this.chuaApkm = chuaApkm;
	}

	public boolean isDamuahang() 
	{
		return this.dacoDh;
	}

	public void setIsDamuahang(boolean damuahang)
	{
		this.dacoDh = damuahang;
	}

	public boolean isDxkChuaChot()
	{
		return this.daxuatkhoChuachot;
	}

	public void setIsDxkChuaChot(boolean cokm) 
	{
		this.daxuatkhoChuachot = cokm;
	}

	public void setNgayKs(String ngayks) 
	{
		this.ngayks = ngayks;
	}

	public String getNgayKs()
	{
		return this.ngayks;
	}

	public void setCotrungbay(String cotrungbay) 
	{
		this.coTrungBay = cotrungbay;
	}

	public String getCotrungbay() 
	{
		return this.coTrungBay;
	}

	public int ApTrungBay(String dhId, String khId, String nppId, String ngaydh) 
	{
		/*******************       0 - Khong co trung bay,  1 - Co trung bay, -1 - Loi khi cap nhat trung bay                 *********************/
		int flag = 0;

		return flag;
	}

	public boolean isAplaitrungbay() 
	{
		return this.aplaitb;
	}

	public void setAplaitrugnbay(boolean aplaitb) 
	{
		this.aplaitb = aplaitb;
	}

	public void setIsDonHangLe(String IsDonHangLe) 
	{
		this.IsDonHangLe = IsDonHangLe;
	}

	public String getIsDonHangLe() 
	{
		return this.IsDonHangLe;
	}

	public void setIsSampling(String IsSampling)
	{
		this.IsSampling = IsSampling;
	}

	public String getIsSampling() 
	{
		return this.IsSampling;
	}


	public String[] getTichLuy_Scheme() {

		return this.tichluy_scheme;
	}


	public void setTichLuy_Scheme(String[] tichluy_scheme) {

		this.tichluy_scheme = tichluy_scheme;
	}


	public String[] getTichLuy_Tongtien() {

		return this.tichluy_tongtien;
	}


	public void setTichLuy_Tongtien(String[] tichluy_tongtien) {

		this.tichluy_tongtien = tichluy_tongtien;
	}

	boolean thoaThang=false;
	double duno_dauky = 0;

	public void setEnterKH(String enterKH) 
	{
		this.enterKH = enterKH;
	}

	public String getEnterKH() {

		return this.enterKH;
	}

	public String[] getTichLuy_VAT() {

		return this.tichluy_vat;
	}

	public void setTichLuy_TVAT(String[] tichluy_vat) {

		this.tichluy_vat = tichluy_vat;
	}

	public ResultSet getCkbosungList() {

		return this.ckbsList;
	}


	public void setCkbosungList(ResultSet ckbsList) {

		this.ckbsList = ckbsList;
	}


	public String getCkbosungIds() {

		return this.chietkhau_ids;
	}


	public void setCkbosungIds(String ckbsIds) {

		this.chietkhau_ids = ckbsIds;
	}


	public Hashtable<String, Float> getCkbosung_CK() {

		return this.chietkhau_giatri;
	}


	public void setCkbosung_CK(Hashtable<String, Float> ckbs_ck) {

		this.chietkhau_giatri = ckbs_ck;
	}


	public String getCkbosung_VAT() {

		return this.chietkhau_vat;
	}


	public void setCkbosung_TVAT(String ckbs_vat) {

		this.chietkhau_vat = ckbs_vat;
	}


	public void setTieuchi(String tieuchi) {

		this.tieuchiID = tieuchi;
	}


	public String getTieuchi() {

		return this.tieuchiID;
	}


	public ResultSet getDoanhsodenghi() {

		return this.dsdnRs;
	}


	public void setDoanhsodenghi(ResultSet dsDenghi) {

		this.dsdnRs = dsDenghi;
	}


	public void setDsTongNhomXanh(String dstNhomXanh) {

		this.dstXanh = dstNhomXanh;
	}


	public String getDsTongNhomXanh() {

		if(this.dstXanh.trim().length() <= 0)
			return "0";
		return this.dstXanh;
	}


	public void setDsTongNhomHHBG(String dstHHBG) {

		this.dstHHBG = dstHHBG;
	}


	public String getDsTongNhomHHBG() {

		if(this.dstHHBG.trim().length() <= 0)
			return "0";
		return this.dstHHBG;
	}


	public void setDsTongNhomKHAC(String dstKHAC) {

		this.dstKHAC = dstKHAC;
	}


	public String getDsTongNhomKHAC() {

		if(this.dstKHAC.trim().length() <= 0)
			return "0";
		return this.dstKHAC;
	}


	public void setDsTongNhomXanh_DeNghi(String dstDENGHI) {

		this.dstXanh_Denghi = dstDENGHI;
	}


	public String getDsTongNhomXanh_DeNghi() {

		if(this.dstXanh_Denghi.trim().length() <= 0)
			return "0";
		return this.dstXanh_Denghi;
	}


	public void setDsTongNhomHHBG_DeNghi(String dstHHBG_denghi) {

		this.dstHHBG_Denghi = dstHHBG_denghi;
	}


	public String getDsTongNhomHHBG_DeNghi() {

		if(this.dstHHBG_Denghi.trim().length() <= 0)
			return "0";
		return this.dstHHBG_Denghi;
	}


	public String[] getTichLuy_CoTheXoa() {

		return this.tichluy_cothexoa;
	}

	public void setTichLuy_CoTheXoa(String[] tichluy_xoa) {

		this.tichluy_cothexoa = tichluy_xoa;
	}


	public String getLoaiNppId() {

		return this.loaiNppId;
	}


	public void setLoaiNppId(String nppId) {

		this.loaiNppId = nppId;
	}

	public void setDonhangKhac(String donhangKhac) 
	{
		this.donhangKhac = donhangKhac;

	}


	public String getDonhangKhac()
	{
		return this.donhangKhac;
	}


	public String getChucuahieu() 
	{
		return this.chucuahieu;
	}


	public void setChucuahieu(String chucuahieu) 
	{
		this.chucuahieu = chucuahieu;
	}


	public String getnvgnId() 
	{
		return this.nvgnId;
	}


	public void setnvgnId(String nvgnId) 
	{
		this.nvgnId=nvgnId;
	}


	public ResultSet getnvgnRs() 
	{
		return this.nvgnRs;
	}


	public void setnvgnRs(ResultSet nvgnRs) {

		this.nvgnRs = nvgnRs;
	}



	public String DuyetDonHang(String dhId, String vitriBAM, String userId)
	{
		String pxkId = "";
		String loaiNPP = "";
		String tuxuatOTC = "";
		try 
		{
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");

			db.getConnection().setAutoCommit(false);
			
			//BÊN NÀY KHO TRỪ KHI SS VÀ CS DUYỆT
			String query = "Update DONHANG set trangthai = '1', thoidiem_chot = getdate(), userId_chot = '" + userId + "' where pk_seq = '" + dhId + "' ";
			if(db.updateReturnInt(query) <= 0 )
			{
				db.getConnection().rollback();
				return "1.Lỗi khi duyệt đơn hàng: " + query;
			}
			
			//Kiểm tra trong trường hợp 2 user chốt cùng một lúc
			/*String query =  "select trangthai, dh.donquatang, " +
					"	( select count(*) from HOADON_DDH where ddh_fk = dh.pk_seq  " +
					"			and hoadon_fk in ( select pk_seq from HOADON where trangthai in ( 1, 2, 4 ) and npp_fk = ( select npp_fk from DONHANG where pk_seq = '" + dhId + "' ) and loaihoadon = '0' ) ) as soDongHD, " +
					"   isnull(donhangkhac, 0) as donhangkhac, " +
					//"	( select count(*) from DONHANG_CTKM_TRAKM where donhangID = dh.pk_seq and spMA is not null  ) as sodongKM, " +
					"	( select count(*) from DONHANG_CTKM_TRAKM where donhangID = dh.pk_seq and spMA is not null " +
					"			 and ctkmId in ( select pk_seq from CTKHUYENMAI where kho_fk = '100000' ) ) as coKHOHB, " +
					"	( select count(*) from DONHANG_CTKM_TRAKM where donhangID = dh.pk_seq and spMA is not null " +
					"			 and ctkmId in ( select pk_seq from CTKHUYENMAI where kho_fk = '100001' ) ) as coKHOKM, " +
					"   ( select count(pk_seq) from NHAPHANPHOI where pk_seq = dh.npp_fk and TUTAOHOADON = '1' and priandsecond = '1' ) as tuxuatHD   " +
					"from DONHANG dh where pk_seq = '" + dhId + "' ";
			System.out.println("query _______"+query);
			ResultSet checkDH = db.get(query);
			String trangthaiDH = "";
			int isDHK = 0;
			//int isKM = 0;
			//int chicoKHOKM = 0;
			int soHD = 0;
			int tuxuatHD = 0;
			boolean cokoHANGBAN = false;
			boolean cokoHANGKM = false;
			String donquatang = "";
			{
				while(checkDH.next())
				{
					trangthaiDH = checkDH.getString("trangthai");
					soHD = checkDH.getInt("soDongHD");
					isDHK = checkDH.getInt("donhangkhac");
					//isKM = checkDH.getInt("sodongKM");
					//chicoKHOKM = checkDH.getInt("chicoKHOKM");
					tuxuatHD = checkDH.getInt("tuxuatHD");
					donquatang = checkDH.getString("donquatang");
					
					if(checkDH.getInt("coKHOHB") > 0 )
						cokoHANGBAN = true;
					
					if(checkDH.getInt("coKHOKM") > 0 )
						cokoHANGKM = true;
				}
				checkDH.close();
			}

			if(trangthaiDH.equals("1"))
			{
				db.getConnection().rollback();
				return "Đơn hàng này đã chốt rồi." ;
			}

			if(soHD >= 2)
			{
				db.getConnection().rollback();
				return "Đơn hàng này đã có hóa đơn rồi. Vui lòng kiểm tra lại" ;
			}

			//DON HANG KHONG DUOC VUOT QUA 13 DONG
			int sodongHD = 0;
			query = "select ( select count(*) from DONHANG_SANPHAM where donhang_fk = a.pk_seq ) +  " +
					"	   ( select count(*) from DONHANG_CHIETKHAUBOSUNG where donhang_fk = a.pk_seq ) +  " +
					"	   (  " +
					"			select count(*) " +
					"			from " +
					"			( " +
					"				select N'CN5' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 1 as STT, 0 as tichluyQUY, 0 as cotheXOA from DONHANG_SANPHAM   " +
					"				where donhang_fk = '" + dhId + "' and thueVAT = '5' and chietkhau > 0 group by thueVAT  " +
					"			union   " +
					"				select N'CN10' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 2 as STT, 0 as tichluyQUY, 0 as cotheXOA   " +
					"				from DONHANG_SANPHAM   " +
					"				where donhang_fk = '" + dhId + "' and thueVAT = '10' and chietkhau > 0 group by thueVAT  " +
					"			union  " +
					"				select a.diengiai, a.thanhtoan / ( 1 + ptTHUE / 100 ) as chietkhau, a.ptTHUE as thueVAT, 3 as STT, tichluyQUY, 1 as cotheXOA   " +
					"				from DUYETTRAKHUYENMAI_DONHANG a inner join TIEUCHITHUONGTL b on a.duyetkm_fk = b.PK_SEQ    " +
					"				where a.donhang_fk = '" + dhId + "' and a.thanhtoan != 0 and hienthi = '1' " +
					"			) " +
					"			TL " +
					"	   )  " +
					"	   as soDONG " +
					"from DONHANG a where pk_seq = '" + dhId + "' ";
			//		System.out.println("----CHECK 13 DONG: " + query );

			ResultSet rsSODONG = db.get(query);
			if(rsSODONG.next())
			{
				sodongHD = rsSODONG.getInt("soDONG");
				rsSODONG.close();
			}

			if(sodongHD > 13)
			{
				db.getConnection().rollback();
				return "Số dòng sản phẩm và chiết khấu của đơn hàng không được vượt quá 13 dòng ";
			}

			query = "select b.loaiNPP, a.NPP_FK, a.ngaynhap, b.priandsecond as tuchotOTC, a.khachhang_fk, " +
					"	ISNULL( ( select count(*) from PHIEUXUATKHO_DONHANG where donhang_fk = a.pk_seq and PXK_FK in ( select pk_seq from PHIEUXUATKHO where NPP_FK = b.pk_seq and trangthai != '2' ) ), 0) as exitPXK " +
					"from DONHANG a inner join NHAPHANPHOI b on a.NPP_FK = b.pk_seq where a.pk_seq = '" + dhId + "' ";
			//		System.out.println("---DUYET DON HANG: " + query);
			boolean exitPXK = false;
			String nppId = "";
			String ngaynhap = "";
			tuxuatOTC = "";
			String khachhang_fk="";

			ResultSet rs = db.get(query);
			{
				if(rs.next())
				{
					loaiNPP = rs.getString("loaiNPP");
					nppId = rs.getString("NPP_FK");
					ngaynhap = rs.getString("ngaynhap");
					System.out.println("_____________NgayNhap____"+ngaynhap);

					tuxuatOTC = rs.getString("tuchotOTC");
					khachhang_fk =rs.getString("khachhang_fk");

					if(rs.getInt("exitPXK") > 0 )
						exitPXK = true;
					rs.close();
				}
				if(!exitPXK) //Chưa có PXK mới tạo
				{	
					query = "insert Phieuxuatkho(ngaylapphieu, nvgn_fk, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, npp_fk) " +
							"select ngaynhap, isnull(nvgn_fk, ( select top(1) pk_seq from NHANVIENGIAONHAN where NPP_FK = a.NPP_FK ) ), '0', '" + getDateTime() + "', '" + getDateTime() + "', '" + userId + "', '" + userId + "', npp_fk " +
							"from DONHANG a " +
							"where pk_seq = '" + dhId + "' ";
					//	System.out.println("---INSERT PXK; " + query );
					if(db.updateReturnInt(query) <= 0 )
					{
						db.getConnection().rollback();
						return "1.Lỗi khi duyệt đơn hàng: " + query;
					}

					query = "select scope_identity() as pxkId";
					rs = db.get(query);
					rs.next();
					pxkId = rs.getString("pxkId");
					rs.close();

					query = "Insert into phieuxuatkho_donhang(pxk_fk, donhang_fk, tonggiatri) " +
							"values('" + pxkId + "', '" + dhId + "', null)";
					//	System.out.println("---INSERT phieuxuatkho_donhang: " + query );
					if(db.updateReturnInt(query) <= 0 )
					{
						db.getConnection().rollback();
						return "2.Lỗi khi duyệt đơn hàng: " + query;
					}
				}

				if( tuxuatOTC.equals("1") )
				{
					//CHECK VA TU DONG TRU TON KHO NHA PHAN PHOI --> COPY CHO CHOT PXK QUA
					String msg = this.Chotphieuxuat(db, pxkId, dhId, nppId, ngaynhap);
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return "4.Lỗi khi duyệt đơn hàng: " + msg;
					}

					query = "Update DONHANG set trangthai = '1' , nguoisua = "+ userId +",NgaySua='"+getDateTime()+"' where pk_seq = '" + dhId + "' and TrangThai!=1 ";
					if(db.updateReturnInt(query) <= 0)
					{
						db.getConnection().rollback();
						return "Đơn hàng đã chốt rồi ";
					}
				}
			}


			if(tuxuatHD > 0 && tuxuatOTC.equals("1") )
			{
				if(donquatang.equals("0"))
				{
					if(isDHK == 0 )
					{
						// TU DONG TAO HOA DON TAI CHINH
						String msg = this.TaoHoaDonTaiChinh(db, dhId, nppId, loaiNPP, ngaynhap, userId, khachhang_fk);
						if(msg.trim().length() > 0)
						{
							db.getConnection().rollback();
							return "5.Lỗi khi tạo hóa đơn tài chính : " + msg;
						}

						// TU TAO HD KHUYEN MAI (NEU CO) --> NGÀY 07/04/2015 thay đổi chỉ SCHEME có kho hàng bán mới ra hóa đơn ( số hóa đơn NA ), SHCEME kho KM không ra hoa đơn
						if( cokoHANGBAN || cokoHANGKM )
						{			
							//Nếu đơn hàng có 2 scheme KM hàng bán và kho KM thì tạo 2 hóa đơn
							/////// 1 hóa đơn loại = 1 -> vẫn hiển thị số NA để in
							//////  1 hóa đơn loại = 2 -> không hiển thị, nhưng BC NXT vẫn ghi nhận
							if(cokoHANGBAN)
							{
								msg = this.TaoHoaDonTaiChinhKM(db, dhId, nppId, loaiNPP, ngaynhap, userId, khachhang_fk, 0, donquatang, "1");
								if(msg.trim().length() > 0)
								{
									db.getConnection().rollback();
									return "6.Lỗi khi tạo hóa đơn tài chính KM  : " + msg;
								}
							}
							
							if(cokoHANGKM)
							{
								msg = this.TaoHoaDonTaiChinhKM(db, dhId, nppId, loaiNPP, ngaynhap, userId, khachhang_fk, 1, donquatang, "2");
								if(msg.trim().length() > 0)
								{
									db.getConnection().rollback();
									return "7.Lỗi khi tạo hóa đơn tài chính KM  : " + msg;
								}
							}
						}
					}
					else if(isDHK == 1)
					{
						// TU DONG TAO HOA DON TAI CHINH KM	 TU DH KHAC			
						String msg = this.TaoHoaDonTaiChinhKM_DHK(db, dhId, nppId, ngaynhap, userId, khachhang_fk, donquatang);
						if(msg.trim().length() > 0)
						{
							db.getConnection().rollback();
							return "55.Lỗi khi tạo hóa đơn tài chính KM cua DON HANG KHAC : " + msg;
						}
					}
				}
				if(donquatang.equals("1")){
					String msg = this.TaoHoaDonTaiChinhKM_DHK(db, dhId, nppId, ngaynhap, userId, khachhang_fk,donquatang );
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return "55.Lỗi khi tạo hóa đơn tài chính KM cua DON HANG KHAC : " + msg;
					}
				}
			}
			*/

			Utility util = new Utility();

			msg= util.Check_Kho_Tong_VS_KhoCT(nppId, db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}
			util.Update_GiaTri_DonHang(dhId, db);

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			}
			catch (Exception e1) { e1.printStackTrace();}
			if(db!=null)db.shutDown();
			return "Lỗi khi duyệt đơn hàng: " + e.getMessage();
		}
		
		return "Duyệt đơn hàng thành công.";

	}

	public static void main(String[] arg)
	{
		double d=1;
		System.out.println("KQ__"+d);


		try
		{
			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	Date date1 = sdf.parse("2015-11-05");
	    	Date date2 = sdf.parse("2015-01-01");

	    	System.out.println(sdf.format(date1));
	    	System.out.println(sdf.format(date2));

	    	if(date1.compareTo(date2)>0){
	    		System.out.println("Date1 is after Date2");
	    	}else if(date1.compareTo(date2)<0){
	    		System.out.println("Date1 is before Date2");
	    	}else if(date1.compareTo(date2)==0){
	    		System.out.println("Date1 is equal to Date2");
	    	}else{
	    		System.out.println("How to get here?");
	    	}*/

			/*String query="select * from ufLapNgay('2015-04-01') ";

			dbutils db = new dbutils();
			ResultSet rs=db.get(query);
			while(rs.next())
			{
				System.out.println("____"+rs.getString("Ngay"));
			}
			 */



		}
		catch (Exception e) {

		}
	}


	public void setDenghitrackThang(String denghitrackThang) 
	{
		this.denghitraCKTHANG = denghitrackThang;
	}

	public String getDenghitrackThang() 
	{
		return this.denghitraCKTHANG;
	}

	public String getGhiChu() 
	{
		return this.ghichu;
	}

	public void setGhiChu(String ghichu) 
	{
		this.ghichu=ghichu;
	}

	public String getChietkhaucu() 
	{
		return chietkhaucu;
	}

	public void setChietkhaucu(String chietkhaucu) {
		this.chietkhaucu = chietkhaucu;
	}


	public void setIsDonquatang(String IsDonquatang) {

		this.donquatang = IsDonquatang;
	}

	public String getIsDonquatang() {
		return this.donquatang;
	}


	/************** CHUYEN CAC HAM XU LY LUU KHUYEN MAI XUONG BEAN ******************/

	public String CreateKhuyenmai(ICtkhuyenmai ctkm, String id, String nppId, String tungay, long tongGtridh, String khId, Hashtable<String, Float> sp_sl, Hashtable<String, Float> sp_dg, dbutils db) 
	{
		String str = "";
		try 
		{
			db.getConnection().setAutoCommit(false);
			System.out.println("___ ctkm-ss: " + ctkm.getscheme() + "-" + ctkm.getSoxuatKM());
			List<ITrakhuyenmai> trakmList = ctkm.getTrakhuyenmai();
			for (int count = 0; count < trakmList.size(); count++) 
			{
				// ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(0);
				ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(count);

				long tongtienTraKM = 0;
				if (trakm.getType() == 1)
					tongtienTraKM = Math.round(ctkm.getSoxuatKM() * trakm.getTongtien());
				else 
				{
					if (trakm.getType() == 2) // tra chiet khau
					{
						/*System.out.println("___Tong tien tra km: " + ctkm.getTongTienTheoDKKM() + " -- Chiet khau: " + trakm.getChietkhau());*/
						// Tinh tong gia tri tra khuyen mai theo dieu kien (chu
						// khong phai tren tong gia tri don hang)
						long tonggiatriTrakm = ctkm.getTongTienTheoDKKM();
						tongtienTraKM = Math.round(tonggiatriTrakm * (trakm.getChietkhau() / 100));
						// tongtienTraKM = Math.round(tongGtridh *
						// (trakm.getChietkhau() / 100));
					} 
					else 
					{
						if (trakm.getHinhthuc() == 1) 
						{
							tongtienTraKM = Math.round(ctkm.getSoxuatKM() * trakm.getTongGtriKm());
							/*System.out.println("Tong tien trakm co dinh: " + tongtienTraKM + "\n");*/
						}
					}
				}

				/*********************************************************************************/
				if (ctkm.getPhanbotheoDH().equals("0")) 
				{
					String msg = CheckNghanSach(ctkm.getId(), nppId, khId, Long.toString(tongtienTraKM), "0", db);
					if (msg.trim().length() > 0) 
					{
						db.getConnection().rollback();
						return msg;
					}
				}
				/*********************************************************************************/

				if (trakm.getType() == 3) // san pham co so luong co dinh
				{
					if (trakm.getHinhthuc() == 1) 
					{
						String sql = "select f.pk_seq as spId, a.soluong, f.ma as spMa,  "
								+ "	ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = f.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + nppId +  "' ) ) ), 0) as dongia "
								+ "from Trakhuyenmai_sanpham a inner join SANPHAM f on a.SANPHAM_FK = f.PK_SEQ   "
								+ "where a.TRAKHUYENMAI_FK = '" + trakm.getId() + "'  ";

						System.out.println("Query lay gia san pham co dinh la: " + sql + "\n");

						int index = 0;
						ResultSet rsSQl = db.get(sql);
						if (rsSQl != null) 
						{
							while (rsSQl.next()) 
							{
								int slg = Integer.parseInt(rsSQl.getString("soluong")) * (int) ctkm.getSoxuatKM();
								long tonggtri = Math.round(slg * rsSQl.getFloat("dongia"));

								/*********************************************************************************/
								if (ctkm.getPhanbotheoDH().equals("1")) 
								{
									// String msg = CheckNghanSach(ctkm.getId(),
									// nppId, Long.toString(slg), "1", db);
									String msg = CheckNghanSach(ctkm.getId(), nppId, khId, Long.toString(ctkm.getSoxuatKM()), "1", db);
									System.out.println("--------------CHECK NGAN SACH: " + msg);
									if (msg.trim().length() < 10 && msg.trim().length() > 0) 
									{
										ctkm.setSoxuatKM((int) Float.parseFloat(msg));
										slg = Integer.parseInt(rsSQl.getString("soluong")) * (int) ctkm.getSoxuatKM();
									} 
									else 
									{
										if (msg.trim().length() > 10) 
										{
											db.getConnection().rollback();
											return msg;
										}
									}
								}
								/*********************************************************************************/

								String error = checkTonkho(nppId, this.id, ctkm.getId(), khId, rsSQl.getString("spId"), rsSQl.getString("spMa"), slg, db);
								if (error.length() > 0) 
								{
									db.getConnection().rollback();
									return error;
								}

								// luu tong gia tri o moi dong sanpham
								String query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) "
										+ "values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + Long.toString(tonggtri) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "')";
								System.out.println("1.Chen khuyen mai co dinh: " + query);

								if (!db.update(query)) 
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}

								// cap nhat kho
								/*query = "Update nhapp_kho set available = available - '" + Integer.toString(slg) + "', booked = booked + '" + Integer.toString(slg)
										+ "' where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkm.getId() + "') and npp_fk = '" + nppId + "' " +
										" and sanpham_fk = '" + rsSQl.getString("spId") + "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId + "') ";*/
								
								query = "Update nhapp_kho_ddkd set available = available - '" + Integer.toString(slg) + "', booked = booked + '" + Integer.toString(slg) + "' where kho_fk = '100008' " +
										"	and npp_fk = ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' and PK_SEQ = ( select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "' ) ) " + 
										"   and sanpham_fk = " + rsSQl.getString("spId") + " and ddkd_fk = ( select ddkd_fk from DONHANG where pk_seq = '" + this.id + "' ) " + 
									    "   and nhomkenh_fk = ( 100000 )";
								
								System.out.println("2.Cap nhat kho: " + query);
								if (!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}

								/*query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + Long.toString(tongtienTraKM)
										+ "' where ctkm_fk = '" + ctkm.getId() + "' and npp_fk = '" + nppId + "'";
								System.out.println("4.Cap nhat phanbo Phanbokhuyenmai: " + query);
								if (!db.update(query)) 
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}*/

								index++;
							}
						}
						rsSQl.close();
					} 
					else // tinh so luong san pham nhapp da chon, phai check ton kho tung buoc
					{
						if (trakm.getHinhthuc() == 2) 
						{
							String query = "select a.sanpham_fk as spId, c.MA as spMa, isnull(b.TONGLUONG, 0) as tongluong,  "
									+ "	ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = c.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + nppId +  "' ) ) ), 0) as dongia	"
									+ "from TRAKM_NHAPP a inner join TRAKHUYENMAI b on a.trakm_fk = b.PK_SEQ  "
									+ "		inner join SANPHAM c on a.sanpham_fk = c.PK_SEQ "
									+ " where a.ctkm_fk = '" + ctkm.getId() + "' and a.npp_fk = '" + nppId + "' and a.trakm_fk = '" + trakm.getId() + "' " + "order by a.thutuuutien asc";

							System.out.println("5.Query tinh gia km npp chon truoc: " + query);

							ResultSet spkm = db.get(query);

							String sp = "";
							String ma = "";
							String dg = "";
							String tg = "";
							while (spkm.next()) 
							{
								sp += spkm.getString("spId") + ",";
								dg += spkm.getString("dongia") + ",";
								tg += spkm.getString("tongluong") + ",";
								ma += spkm.getString("spMa") + ",";
							}
							spkm.close();

							String[] spId = sp.split(",");
							String[] dongia = dg.split(",");
							String[] tongluong = tg.split(",");
							String[] spMa = ma.split(",");

							// CheckTonKho nhung tra khuyen mai da duoc npp chon truoc
							String[] arr = checkTonKhoTraKm(nppId, this.id, ctkm, khId, spId, dongia, tongluong, spMa, db);
							if (arr == null) // nhung san pham da chon truoc cua tra khuyen mai da het hang trong kho
							{
								db.getConnection().rollback();
								str = "Số lượng những sản phẩm bạn chọn trước trong thiết lập sản phẩm trả khuyến mãi không đủ trong kho";
								System.out.println("Error: " + str + "\n");
								return str;
							} 
							else 
							{
								/*********************************************************************************/
								if (ctkm.getPhanbotheoDH().equals("1")) 
								{
									String msg = CheckNghanSach(ctkm.getId(), nppId, khId, Integer.toString(ctkm.getSoxuatKM()), "1", db);
									if (msg.trim().length() > 0) {
										db.getConnection().rollback();
										return msg;
									}
								}
								/*********************************************************************************/

								// luu tong gia tri o moi dong sanpham
								query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) "
										+ "values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + arr[2].replaceAll(",", "") + "', '" + arr[3] + "', '" + arr[1].replaceAll(",", "") + "')";
								System.out.println("6.Chen khuyen mai Npp chon truoc: " + query);

								if (!db.update(query)) {
									db.getConnection().rollback();
									str = query;
									return str;
								}

								// cap nhat kho
								/*query = "Update nhapp_kho set available = available - '" + arr[1].replaceAll(",", "") + "', booked = booked + '" + arr[1].replaceAll(",", "")
										+ "' where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkm.getId() + "') and npp_fk = '" + nppId + "'" +
										" and sanpham_fk = '" + arr[0] + "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId + "')";*/
								
								query = "Update nhapp_kho_ddkd set available = available - '" + arr[1].replaceAll(",", "") + "', booked = booked + '" + arr[1].replaceAll(",", "") + "' where kho_fk = '100008' " +
										"	and npp_fk = ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' and PK_SEQ = ( select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "' ) ) " + 
										"   and sanpham_fk = " + arr[0] + " and ddkd_fk = ( select ddkd_fk from DONHANG where pk_seq = '" + this.id + "' ) " + 
									    "   and nhomkenh_fk = ( 100000 )";
								
								System.out.println("7.Cap nhat npp_kho: " + query);
								if (!db.update(query)) 
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}

								/*query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + arr[2].replaceAll(",", "") + "' where ctkm_fk = '" + ctkm.getId()+ "' and npp_fk = '" + nppId + "'";
								System.out.println("9.Cap nhat ngan sach Phanbokhuyenmai: " + query);
								if (!db.update(query)) {
									db.getConnection().rollback();
									str = query;
									return str;
								}*/
							}
						}
					}
				} 
				else
				{
					if (trakm.getType() != 3)// tra tien, tra chiet khau
					{
						String query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri) " +
								"values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + tongtienTraKM + "')";
						System.out.println("10.Chen khuyen mai tien / ck: " + query);
						if (!db.update(query)) {
							db.getConnection().rollback();
							str = query;
							return str;
						}

						/*query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + Long.toString(tongtienTraKM)
								+ "' where ctkm_fk ='" + ctkm.getId() + "' and npp_fk='" + nppId + "'";
						System.out.println("12.Cap nhat ngan sach Phanbokhuyenmai: "+ query);
						if (!db.update(query)) {
							db.getConnection().rollback();
							str = query;
							return str;
						}*/
					}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e1) 
		{
			try {
				db.getConnection().rollback();
			} catch (SQLException e) { }

			e1.printStackTrace();
			return "Loi khi tao moi ctkm " + ctkm + ", " + e1.toString();
		}
		return str;
	}

	private String[] checkTonKhoTraKm(String nppId, String dhId, ICtkhuyenmai ctkm, String khId, String[] spId, String[] dongia, String[] tongluong, String[] spma, dbutils db)
	{
		String[] kq = new String[4];

		String msg = "";
		try 
		{
			for (int i = 0; i < spId.length; i++) 
			{
				int slg = Integer.parseInt(tongluong[i]) * ctkm.getSoxuatKM();
				msg = checkTonkho(nppId, dhId, ctkm.getId(), khId, spId[i], "", slg, db);
				if (msg == "") // thoa ton kho
				{
					kq[0] = spId[i];
					kq[1] = Integer.toString(slg);
					kq[2] = Long.toString(Math.round(slg * Float.parseFloat(dongia[i])));
					// System.out.println("Don gia: " + spId[i] + "- dongia: " +
					// dongia[i] + " - Tong gia tri o buoc nay: " + kq[2] +
					// "\n");
					kq[3] = spma[i];

					return kq;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	private String checkTonkho(String nppId, String dhId, String ctkmId, String khId, String spId, String spMa, int slg, dbutils db) 
	{
		// kiem tra trong kho khuyen mai con du san pham hay khong, khong du thi thoat
		///// CHECK KHO THEO TDV
		/*String query = "select available from nhapp_kho where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkmId + "') " +
						"	and npp_fk = '" + nppId + "' and sanpham_fk = " + spId + " and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId + "')";*/
		
		//KHO TDV KO CAN PHAN BIET NHOM KENH
		String query = "select available from NHAPP_KHO_DDKD where kho_fk = '100008' " +
					   "	and npp_fk = ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' and PK_SEQ = ( select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "' ) ) " +  
					   " and sanpham_fk = " + spId + " and ddkd_fk = ( select ddkd_fk from DONHANG where pk_seq = '" + dhId + "' ) " + 
					   //"   and nhomkenh_fk = ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId + "') )";
					   "   and nhomkenh_fk = '100000' ";
		
		System.out.println(" --- CHECK TONKHO::: " + query );
		ResultSet rsCheck = db.get(query);

		int avai = 0;
		if (rsCheck != null) 
		{
			try 
			{
				boolean flag = false;

				while (rsCheck.next()) 
				{
					if (rsCheck.getString("available") != null) 
					{
						flag = true;
						avai = rsCheck.getInt("available");
						if (avai < slg) {
							return "Sản phẩm khuyến mại " + spMa + " - Trong kho trình dược viên còn tối đa " + avai;
						}
					}
					avai = rsCheck.getInt("available");
				}
				rsCheck.close();
				if (flag == false) // khong co dong du lieu nao, Resualset van khac null
				{
					return "San pham khuyen mai " + spMa + " - Trong kho trình dược viên còn tối đa 0";
				}
			} 
			catch (Exception e) 
			{
				return "error";
			}
		}

		if (avai < slg) 
		{
			return "Sản phẩm khuyến mại  " + spMa + " - Trong kho trình dược viên còn tối đa " + avai;
		}

		return "";
	}

	public String capNhatKM(String id, String nppId, String khId, String trangthai, dbutils db) 
	{
		try 
		{
			String query =  "select ctkmId, trakmId, khoNVBH, sum(tonggiatri) as tonggiatri " +
							"from donhang_ctkm_trakm where donhangid = '" + id + "' and khoNVBH = '0' " +
							"group by ctkmId, trakmId, khoNVBH";
			System.out.println("Cau lenh lay du lieu cap nhat: " + query + "\n");
			ResultSet rs = db.get(query);
			if (rs != null) 
			{
				while (rs.next()) 
				{
					String ctkmId = rs.getString("ctkmId");
					String tonggiatri = rs.getString("tonggiatri");
					String trakmId = rs.getString("trakmId");
					/*String st = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG - '" + tonggiatri + "' " +
								"where ctkm_fk='" + ctkmId + "' and npp_fk='" + nppId + "'";
					System.out.println(st);

					if (!db.update(st)) {
						db.getConnection().rollback();
						return "Error :" + st;
					}*/

					// cap nhat lai sanpham trong kho
					if (!trangthai.equals("3")) 
					{
						query = "select spMa, soluong from donhang_ctkm_trakm " +
								"where donhangId = '" + id + "' and ctkmId = '" + ctkmId + "' and trakmId = '" + trakmId + "' and soluong is not null";
						System.out.println(query);
						ResultSet spRs = db.get(query);
						if (spRs != null) 
						{
							while (spRs.next()) 
							{
								String maSp = spRs.getString("spMa");
								String sl = spRs.getString("soluong");
								/*query = "Update nhapp_kho set available = available + '" + sl + "', booked = booked - '" + sl + "' " +
										"where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkmId + "') and npp_fk = '" + nppId + "'" +
										"	 and sanpham_fk in (select pk_seq from sanpham where ma = '" + maSp + "') and kbh_fk in (select kbh_fk from khachhang where pk_seq = '" + khId + "')";*/
								
								query = "Update nhapp_kho_ddkd set available = available + '" + sl + "', booked = booked - '" + sl + "' " +
										"where kho_fk = '100008' " + 
										"	 and npp_fk = ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' and PK_SEQ = ( select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "' ) ) " + 
										"	 and ddkd_fk in ( select ddkd_fk from DONHANG where pk_seq = '" + this.id + "') " + 
										"	 and sanpham_fk in (select pk_seq from sanpham where ma = '" + maSp + "') " + 
										//"	 and nhomkenh_fk in ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = ( select kbh_fk from DONHANG where PK_SEQ = '" + this.id + "' ) )";
										"	 and nhomkenh_fk = '100000' ";
								if (!db.update(query)) {
									db.getConnection().rollback();
									return "Error :" + query;
								}
							}
							spRs.close();
						}
					}
				}
				rs.close();
			}

			// delete neu ton tai, cap nhat lai kho voi so luong tang
			query = "delete from donhang_ctkm_trakm where donhangid = '" + id + "'  ";
			System.out.println(query);
			if (!db.update(query))
			{
				db.getConnection().rollback();

				System.out.println("Error :" + query);
				return "Error :" + query;
			}

			System.out.println("Oke Da xONg");
			return "";
		} 
		catch (Exception e1) 
		{
			try {
				db.getConnection().rollback();
			} catch (Exception e) {
				System.out.println("Error :" + e.toString());
			}
			System.out.println("Error :" + e1.toString());
			return "Error :" + e1.toString();
		}
	}

	private String CheckNghanSach(String ctkmId, String nppId, String khId, String ngansach, String loai, dbutils db) 
	{
		// String sql ="select * from phanbokhuyenmai where npp_fk ='" + nppId +
		// "' and ctkm_fk ='" + ctkmId +"'";

		/*String sql = "";
		if (loai.equals("0")) // PHAN BO KHUYEN MAI THEO SO TIEN
		{
			sql = "select a.CTKM_FK, b.scheme, b.PHANBOTHEODONHANG, a.NGANSACH,   " +
					"	ISNULL( ( select SUM(tonggiatri)  from DONHANG_CTKM_TRAKM where CTKMID = a.CTKM_FK and DONHANGID in ( select PK_SEQ from DONHANG where NPP_FK = a.NPP_FK and TRANGTHAI != 2 ) and DONHANGID not in ( select DONHANG_FK from DONHANGTRAVE where DONHANG_FK is not null and NPP_FK = '" + nppId + "' and TRANGTHAI in  ( 1, 3 ) )  ), 0 ) as DASUDUNG  " +
					"from phanbokhuyenmai a inner join CTKHUYENMAI b on a.CTKM_FK = b.PK_SEQ  " +
					"where npp_fk = '" + nppId + "' and a.ctkm_fk = '" + ctkmId + "'  ";
		} 
		else // PHAN BO KHUYEN MAI THEO SO LUONG
		{
			sql = "select a.CTKM_FK, b.scheme, b.PHANBOTHEODONHANG, a.SOXUATTOIDA as NGANSACH,  "
					+ "	  ISNULL( 	( select SUM(SOXUAT)  "
					+ "	  from DONHANG_CTKM_TRAKM  "
					+ "	  where CTKMID = a.CTKM_FK AND SPMA IS NOT NULL and DONHANGID in ( select PK_SEQ from DONHANG where NPP_FK = a.NPP_FK and TRANGTHAI != 2 and KHACHHANG_FK = '" + khId + "' ) "
					+ "and DONHANGID not in ( select DONHANG_FK from DONHANGTRAVE where DONHANG_FK is not null and NPP_FK = '" + nppId + "' and TRANGTHAI in  ( 1, 3 ) )  ), 0 ) as DASUDUNG  "
					+ "from phanbokhuyenmai a inner join CTKHUYENMAI b on a.CTKM_FK = b.PK_SEQ  "
					+ "where npp_fk = '" + nppId + "' and a.ctkm_fk = '" + ctkmId + "'  ";
		}

		System.out.println("1.Cau lenh check ngan sach: " + sql + " --- Ngan sach de check: " + ngansach);
		ResultSet rs = db.get(sql);
		String scheme = "";

		try 
		{
			float conlai = 0.0f;
			if (rs.next()) 
			{
				scheme = rs.getString("scheme");
				conlai = Float.parseFloat(rs.getString("ngansach")) - Float.parseFloat(rs.getString("DASUDUNG"));
				rs.close();
			}

			// System.out.println("---NGAN SACH ( SOXUAT / TONG GIA TRI ): " +
			// soXUAT + " / " + ngansach + " -- CON LAI: " + conlai);

			if (Float.parseFloat(ngansach) > conlai) 
			{
				Float _conlai = Math.min(Float.parseFloat(ngansach), conlai);
				if (_conlai <= 0)
					return "1.Chương trình khuyến mại: " + scheme + ", đã hết ngân sách phân bổ";
				else
					return Float.toString(Math.round(_conlai));
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("__EXCEPTION CHECK NGAN SACH: " + e.getMessage());
			return "2.Chương trình khuyến mại: " + scheme + ", đã hết ngân sách phân bổ";
		}*/

		return "";
	}

	public boolean CheckDonHangDKM(List<ISanpham> spList, String id, String khId, dbutils db) 
	{
		List<ISanpham> list = new ArrayList<ISanpham>();
		String query = "select a.sanpham_fk as spId, a.soluong, b.ma as spMa " +
				"from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq " +
				"where donhang_fk = '" + id + "'";

		ResultSet rs = db.get(query);

		if(rs != null)
		{
			try 
			{
				ISanpham sp = null;
				while(rs.next())
				{
					sp = new Sanpham("", rs.getString("spMa"), "", rs.getString("soluong"), "", "", "", "");
					list.add(sp);
				}
				rs.close();
			} 
			catch (Exception e) {
				return false;
			}
		}

		for(int i = 0; i < list.size(); i++)
		{
			ISanpham spA = list.get(i);
			for(int j = 0; j < spList.size(); j++)
			{
				ISanpham spB = spList.get(j);
				if(spA.getMasanpham().trim().equals(spB.getMasanpham().trim()))
				{
					int slg = Integer.parseInt(spA.getSoluong()) - Integer.parseInt(spB.getSoluong());
					System.out.println("San pham A: " + spA.getMasanpham() + " - So luong: " + spA.getSoluong() + " -- San pham B: " + spB.getMasanpham() + " -- So luong: " + spB.getSoluong() + "\n");
					list.get(i).setSoluong(Integer.toString(slg));
				}
			}
		}

		for(int i = 0; i < list.size(); i++)
		{
			ISanpham spC = list.get(i);
			//System.out.println("San pham " + spC.getMasanpham() + " tai List la: " + spC.getSoluong() + "\n");
			if(!spC.getSoluong().equals("0"))
			{
				//System.out.println("San pham " + spC.getMasanpham() + " tai List la: " + spC.getSoluong() + "\n");
				return false;
			}
		}

		query = "select count(*) as soDONG from DONHANG where khachhang_fk = '" + khId + "' and pk_Seq = '"+id+"' ";
		rs = db.get(query);
		int count = 0;
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					count = rs.getInt("soDONG");
				}
				rs.close();
			} 
			catch (Exception e) {}
		}

		if(count <= 0)  //DOI KHACH HANG, BAT AP LAI KM
			return false;

		return true;
	}


	public XLkhuyenmai getXLkhuyenmai(String userId, String ngaygd, String khId, String IsDonHangLe, String donhangId)
	{
		return new XLkhuyenmai(userId, ngaygd, khId, IsDonHangLe, donhangId);
	}

	public XLkhuyenmaiDonhangDXK getXLkhuyenmaiDXK(String nppId, String id, String ngaygd, String[] ctkm, String khId) 
	{
		return new XLkhuyenmaiDonhangDXK( nppId, id, ngaygd, ctkm, khId); 
	}


	/** CAC HAM BEN KHUYENMAI SVL **/
	private String CreateKhuyenmai(String ctkmId, String phanbotheoDH, String khoNVBH, String trakmId, String id, String nppId, long soxuat, String khId, dbutils db)
	{
		String str = "";

		try 
		{ 	
			String sql = "select a.sanpham_fk as spId, a.soluong, b.ma as spMa from trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where a.trakhuyenmai_fk = '" + trakmId + "'";
			//String sql = "select a.sanpham_fk as spId, a.soluong, b.ma as spMa, a.dongia from trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where a.trakhuyenmai_fk = (select TRAKHUYENMAI_FK from CTKM_TRAKM where CTKHUYENMAI_FK = '" + ctkmId + "')";
			System.out.println("10.Query truy van san pham khuyen mai: " + sql + "\n");
			ResultSet rsSQl = db.get(sql);
			long tongtien = 0;
			if(rsSQl != null)
			{
				while(rsSQl.next())
				{
					int slg = Integer.parseInt(rsSQl.getString("soluong")) * (int)soxuat;

					//lay don gia
					String query =  "select " +
							/*" 	  isnull( ( select GIAMUANPP * ( 1 - isnull( ( select chietkhau from BANGGIAMUANPP_NPP where banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = '" + nppId + "' ), 0) / 100 ) " +
							" 				from BGMUANPP_SANPHAM bg_sp " +
							"			    where SANPHAM_FK = a.pk_seq  " +
							"					and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK = a.dvkd_fk and bg.KENH_FK = ( select kbh_fk from donhang where pk_seq='" + id + "' ) order by bg.TUNGAY desc ) ), 0) as dongia  " +*/
							"	ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + nppId +  "' ) ) ), 0) as dongia	" +
							"from SANPHAM a  " +
							"where a.PK_SEQ = '" + rsSQl.getString("spId") + "'  ";

					System.out.println("11.Lay don gia: " + query);

					ResultSet rsDg = db.get(query);
					if(rsDg != null)
					{
						if(rsDg.next())  
						{
							if(rsDg.getString("dongia") != null)
								tongtien = Math.round(slg * rsDg.getFloat("dongia"));
							rsDg.close();
						}
					}


					/*********************************************************************************/
					String msg = CheckNghanSach(ctkmId, nppId, khId, String.valueOf(soxuat), "", db);
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return "Chương trình khuyến mại: " + ctkmId + ", đã hết ngân sách phân bổ ";
					}

					System.out.println("soxuat ap dc ------------------" + soxuat);
					/*if(phanbotheoDH.equals("1"))
					{
						String msg = CheckNghanSach(ctkmId, nppId, Integer.toString(slg), "1", db);
						if(msg.trim().length() > 0)
						{
							db.getConnection().rollback();
							return msg;
						}
					}*/


					/*********************************************************************************/

					String error = checkTonkho(nppId, this.id, ctkmId, khId, rsSQl.getString("spId"), rsSQl.getString("spMa"), slg, db);
					if(error.length() > 0)
					{
						db.getConnection().rollback();
						return error;
					}

					//luu tong gia tri o moi dong sanpham
					query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) values('" + id + "','" + ctkmId + "','" + trakmId + "','" + soxuat + "', '" + Long.toString(tongtien) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "')";
					System.out.println("12.Chen khuyen mai: " + query);
					if(!db.update(query))
					{
						db.getConnection().rollback();
						str = query;
						return str;
					}

					//cap nhat kho
					/*query = "Update nhapp_kho set available = available - '" + Integer.toString(slg) + "', booked = booked + '" + Integer.toString(slg) + "' where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkmId + "') and npp_fk = '" + nppId + "' and sanpham_fk = '" + rsSQl.getString("spId") + "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId+ "')";   */							

					query = "Update nhapp_kho_ddkd set available = available - '" + Integer.toString(slg) + "', booked = booked + '" + Integer.toString(slg) + "' where kho_fk = '100008' " +
							"	and npp_fk = ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' and PK_SEQ = ( select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "' ) ) " + 
							"   and sanpham_fk = " + rsSQl.getString("spId") + " and ddkd_fk = ( select ddkd_fk from DONHANG where pk_seq = '" + this.id + "' ) " + 
						    "   and nhomkenh_fk = ( 100000 )";
					if(!db.update(query))
					{
						db.getConnection().rollback(); 
						str = query;
						return str;
					}
				}

				/*String query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + Long.toString(tongtien) + "' where ctkm_fk = '" + ctkmId + "' and npp_fk = '" + nppId + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback(); 
					str = query;
					return str;
				}*/
			}
			rsSQl.close();

		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
			System.out.println("3.Loi KM: " + e1.toString());
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e) {}

			return "Loi khi tao ctkm: " + ctkmId + ", " + e1.toString();
		}
		return str;
	}

	public String saveTRAKHUYENMAI( IDonhang dhBean, HttpServletRequest request )
	{
		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		String khachhang = util.antiSQLInspection(request.getParameter("khachhang"));
		String dhId = util.antiSQLInspection(request.getParameter("dhId"));

		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if(nppId == null)
			nppId = "";

		String ngaygiaodich = util.antiSQLInspection(request.getParameter("ngaygiaodich"));
		dhBean = new Donhang(dhId);
		dhBean.setUserId(userId);

		String loaidonhang = request.getParameter("loaidonhang");
		if(loaidonhang == null)
			loaidonhang = "0";

		String tonggiatridh = util.antiSQLInspection(request.getParameter("tonggiatri"));
		String[] schemeList = request.getParameterValues("schemeList");
		String[] schemeOR = request.getParameterValues("schemeOR");
		String[] soxuatkm = request.getParameterValues("soxuatKM");
		String[] soxuatKM_HIDDEN = request.getParameterValues("soxuatKM_HIDDEN");
		String[] schemePhanBo = request.getParameterValues("schemePhanBo");
		String[] schemeKHONVBH = request.getParameterValues("schemeKHONVBH");
		String[] schemeDiengiai = request.getParameterValues("schemeDiengiai");	

		String[] masp = request.getParameterValues("spMa");
		String[] soluong = request.getParameterValues("spSoluong");
		String[] dongia = request.getParameterValues("spDongia");
		String[] quycach = request.getParameterValues("spQuycach");

		String query = "";
		String kho_fk = "";
		try 
		{
			db.getConnection().setAutoCommit(false);

			int i = 0;
			if(schemeList.length > 0)
			{
				while(i < schemeList.length)
				{
					if(soxuatkm[i].trim().length() > 0 && !soxuatkm[i].equals("0") )
					{
						/*if(Double.parseDouble(soxuatkm[i]) > Double.parseDouble(soxuatKM_HIDDEN[i]) )
						{
							db.getConnection().rollback();
							return "Số xuất KM sau điều chỉnh ( " + soxuatkm[i] + " ) của SCHEME: " + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + " - không được phép vượt quá số xuất tối đa được hưởng ( " + soxuatkm[i] + " ) ";
						}*/
						
						String[] trakm = request.getParameterValues(schemeList[i] + ".trakmId");
						String[] trakmType = request.getParameterValues(schemeList[i] + ".trakmType");
						String[] trakmHinhThuc = request.getParameterValues(schemeList[i] + ".trakmHinhThuc");
						String[] trakmPrimary = request.getParameterValues(schemeList[i] + ".trakmPrimary");
	
						System.out.println("Scheme OR cua CTKM: " + schemeList[i] + ", la: " + schemeOR[i]);
						if(Boolean.parseBoolean(schemeOR[i]))
						{
							String trakmSelected = request.getParameter(schemeList[i] + ".chon");
							System.out.println("1145. Ban chon TKM: " + trakmSelected);
	
							if(trakmSelected == null)
								trakmSelected = trakm[0];
	
							int index_OR = -1;
							for(int kk = 0; kk < trakm.length; kk++)
							{
								if(trakm[kk].trim().equals(trakmSelected))
								{
									index_OR = kk;
									break;
								}
							}
	
							String trakmTypeSelected = trakmType[index_OR];
							String trakmHinhthucSelected = trakmHinhThuc[index_OR];
							String trakmPrimarySelected = trakmPrimary[index_OR];
	
							//thay tra khuyen mai OR duoc chon trong List
							trakm = new String[]{trakmSelected};
							trakmType = new String[]{trakmTypeSelected};
							trakmHinhThuc = new String[]{trakmHinhthucSelected};
							trakmPrimary = new String[]{trakmPrimarySelected};		
						}
	
						/*query = "select kho_fk from ctkhuyenmai where pk_seq = '" + schemeList[i] + "'";
						ResultSet KhoRS = db.get(query);
						if(KhoRS != null)
						{
							KhoRS.next();
							kho_fk = KhoRS.getString("kho_fk");
							KhoRS.close();
						}*/
	
						for(int count = 0; count < trakm.length; count++)
						{
							//System.out.println("i la: " + i + " --Tra Km size:  " + trakm.length + " -- Count la: " + count);    				
							String ttTrakm = request.getParameter(schemeList[i] + "." + trakm[count] + ".tonggiatriTKM");
							String spId = request.getParameter(schemeList[i] + "." + trakm[count] + ".spSelected");
							if(spId == null)
								spId = "";
	
							//System.out.println("San pham Id la: " + schemeList[i] + "." + trakm[count] + ".spSelected" + "  --- " + spId);
							//System.out.println("Tra khuyen mai Type: " + trakmType[count] + ".Hinh thuc" + "  --- " + trakmHinhThuc[count]);
							if( !(trakmType[count].trim().equals("3") && trakmHinhThuc[count].trim().equals("1") ) ) //tra khuyen mai voi san pham tuy chon
							{
								String flag = "";
	
								System.out.println("[Theo Tien]");
								if(schemePhanBo[i].equals("0"))
								{
									flag = CheckNghanSach(schemeList[i], nppId, khachhang, soxuatkm[i], "0", db);
								}
	
								if(flag.length() > 0) //ngan sach khong du
								{
									db.getConnection().rollback();
									return "Ngan sach khong du, vui long dieu chinh ngan sach chuong trinh: " + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + " - Con toi da " + flag;
								}
	
								//Thoa ngan sach
								if(trakmPrimary[count].equals("0"))
								{
									if(spId.length() > 0 ) //masp1-soluong1;masp2-soluong2...
									{
										if(spId.indexOf(";") > 0) //nhieu san pham
										{
											String[] spIds = spId.split(";");
	
											int toalTRA = 0;
											for(int j = 0; j < spIds.length; j++)
											{
												String[] spIdss = spIds[j].split("-");
	
												String msg = this.checkTonkho(nppId, this.id, schemeList[i], khId," (select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "') ", spIdss[0].trim(), Integer.parseInt(spIdss[1].trim().replaceAll(",", "")), db);
	
												//if(schemeKHONVBH[i].equals("1"))
													//msg = checkTonKhoKhuyenMai(dhId, kho_fk, nppId, spIdss, khachhang, db);
	
												if(msg.equals(""))
												{
													long tongtri = 0;
													query = "select ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + nppId +  "' ) ) ), 0) as dongia " +
															"from SANPHAM a  " +
															"where a.MA = '" + spIdss[0].trim() + "'  ";	
	
													ResultSet rsKm = db.get(query);
													if(rsKm != null)
													{
														if(rsKm.next())
															tongtri = Math.round(rsKm.getFloat("dongia"));
														rsKm.close();
													}
													long gtriKm = tongtri * Integer.parseInt(spIdss[1].trim());
	
													query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, spMa, soluong, tonggiatri) "
															+ "values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + spIdss[0].trim() + "','" + spIdss[1].trim().replaceAll(",", "") + "','" + Long.toString(gtriKm) + "')";
													System.out.println("1.Cau lenh chen du lieu: " + query);
													if(!db.update(query))
													{
														db.getConnection().rollback(); 
														return "Loi khi tao moi 'donhang_ctkm_trakm': " + query;
													}
	
													long slg = Math.round(Double.parseDouble(spIdss[1].trim().replaceAll(",", "")));
													//query = "Update nhapp_kho set available = available - '" + slg + "', booked = booked + '" + slg + "' where kho_fk = '" + kho_fk + "' and npp_fk = '" + nppId + "' and sanpham_fk = (select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "') and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khachhang + "')";
													
													query = "Update NHAPP_KHO_DDKD set available = available - '" + slg + "', booked = booked + '" + slg + "' where kho_fk = '100008' " +
														    "	and npp_fk = ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' and PK_SEQ = ( select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "' ) ) " + 
															"   and sanpham_fk = (select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "') and ddkd_fk = ( select ddkd_fk from DONHANG where pk_seq = '" + this.id + "' ) " + 
														    //"   and nhomkenh_fk = ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId + "') )";
														    "   and nhomkenh_fk = '100000' ";
													
													System.out.println("2.Cau lenh cap nhat nhapp_kho: " + query);
													if(!db.update(query))
													{
														db.getConnection().rollback(); 
														return "Loi khi tao moi 'donhang_ctkm_trakm': " + query;
													}
	
													/*String sql = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + ttTrakm + "' where ctkm_fk = '" + schemeList[i] + "' and npp_fk = '" + nppId + "'";
													System.out.println("7.Cau lenh cap nhat Phanbokhuyenmai: " + sql);
													if(!db.update(sql))
													{
														db.getConnection().rollback();
														return "Loi khi tao moi 'Phanbokhuyenmai'";
													}*/
	
													toalTRA += Integer.parseInt(spIdss[1].trim().replaceAll(",", ""));
												}
												else
												{
													db.getConnection().rollback();
													System.out.println("11.RockBack tai day roi....");
													return msg + " nên bạn không được hưởng ctkm '" + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + "'. Vui lòng điều chỉnh lại.\n";
												}
											}
	
											if(toalTRA > 0)
											{
												//NEU PHAN BO THEO SOLUONG THI PHAI CHECK TONG LONG TRA SO VOI TONG LUONG PHAN BO
												flag = "";
												if(schemePhanBo[i].equals("1"))
												{
													System.out.println("[323 Check Ngan Sach ]");
													flag = CheckNghanSach(schemeList[i], nppId, khachhang, soxuatkm[i], "1", db);
	
													if(flag.trim().length() > 0)
													{
														db.getConnection().rollback();
														return "Ngan sach khong du, vui long dieu chinh ngan sach chuong trinh: " + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + " - Con toi da " + flag;
													}
												}
											}
										}
										else  //mot san pham
										{
											String[] spIdss = spId.split("-");
	
											//NEU PHAN BO THEO SOLUONG THI PHAI CHECK TONG LONG TRA SO VOI TONG LUONG PHAN BO
											flag = "";
											System.out.println ("[SpId]"+spIdss[1].trim().replace(",", ""));
											if(schemePhanBo[i].equals("1"))
											{
												flag = CheckNghanSach(schemeList[i], nppId, khachhang, soxuatkm[i], "1", db);
	
												if(flag.trim().length() > 0)
												{
													db.getConnection().rollback();
													return "Ngan sach khong du, vui long dieu chinh ngan sach chuong trinh: " + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + " - Con toi da " + flag;
												}
											}
	
											//kiem tra trong kho khuyen mai con du san pham hay khong, khong du thi thoat
											String msg = checkTonkho(nppId, this.id, schemeList[i], khId, " (select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "') ", spIdss[0].trim(), Integer.parseInt(spIdss[1].trim().replaceAll(",", "")), db);
	
											//msg = checkTonKhoKhuyenMai(dhId, kho_fk, nppId, spIdss, khachhang, db);
											if(msg.equals(""))
											{
												long tongtri = 0;
												query = "select " +
														/*" 	  isnull( ( select GIAMUANPP * ( 1 - isnull( ( select chietkhau from BANGGIAMUANPP_NPP where banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = '" + nppId + "' ), 0) / 100 ) " +
														" 				from BGMUANPP_SANPHAM bg_sp " +
														"			    where SANPHAM_FK = a.pk_seq  " +
														"					and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK = a.dvkd_fk and bg.KENH_FK = ( select kbh_fk from donhang where pk_seq='" + dhId + "' ) order by bg.TUNGAY desc ) ), 0) as dongia  " +*/
														"	ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + nppId +  "' ) ) ), 0) as dongia	" +
														"from SANPHAM a  " +
														"where a.MA = '" + spIdss[0].trim() + "'  ";
	
												ResultSet rsKm = db.get(query);
												if(rsKm != null)
												{
													if(rsKm.next())
														tongtri = Math.round(rsKm.getFloat("dongia"));
													rsKm.close();
												}
												long gtriKm = tongtri * Integer.parseInt(spIdss[1].trim());
	
												query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, spMa, soluong, tonggiatri) "
														+ "values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + spIdss[0].trim() + "','" + spIdss[1].trim().replaceAll(",", "") + "','" + Long.toString(gtriKm) + "')";	
												System.out.println("3.Cau lenh insert du lieu: " + query);
												if(!db.update(query))
												{
													db.getConnection().rollback(); 
													return "Loi khi tao moi 'donhang_ctkm_trakm'";
												}
	
												long slg = Math.round(Double.parseDouble(spIdss[1].trim().replaceAll(",", "")));
												//query = "Update nhapp_kho set available = available - '" + slg + "', booked = booked + '" + slg + "' where kho_fk = '" + kho_fk + "' and npp_fk = '" + nppId + "' and sanpham_fk = (select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "') and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khachhang + "')";
												
												query = "Update NHAPP_KHO_DDKD set available = available - '" + slg + "', booked = booked + '" + slg + "' where kho_fk = '100008' " +
														"	and npp_fk = ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' and PK_SEQ = ( select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "' ) ) " + 
														"   and sanpham_fk = (select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "') and ddkd_fk = ( select ddkd_fk from DONHANG where pk_seq = '" + this.id + "' ) " + 
													    //"   and nhomkenh_fk = ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId + "') )";
														"   and nhomkenh_fk = '100000' ";
												
												System.out.println("4.Cau lenh cap nhat nhapp_kho: " + query);
												if(!db.update(query))
												{
													db.getConnection().rollback();
													return "Loi khi tao moi 'donhang_ctkm_trakm'";
												}
	
												/*String sql = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + ttTrakm + "' where ctkm_fk = '" + schemeList[i] + "' and npp_fk = '" + nppId + "'";
												System.out.println("7.Cau lenh cap nhat Phanbokhuyenmai: " + sql);
												if(!db.update(sql))
												{
													db.getConnection().rollback();
													return "Loi khi tao moi 'Phanbokhuyenmai'";
												}*/
											}
											else
											{
												db.getConnection().rollback();
												System.out.println("10.RockBack tai day roi....");
												return msg + " nên bạn không được hưởng  ctkm '" + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + "'.Vui lòng điều chỉnh lại.\n";
											}
										}
									}
									else
									{
										query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + ttTrakm.replaceAll(",", "") + "')";
										System.out.println("5.Cau lenh tao moi donhang_ctkm_trakm: " + query);
										if(!db.update(query))
										{
											db.getConnection().rollback(); 
											return "Loi khi tao moi 'donhang_ctkm_trakm': " + query;
										}
	
										/*String sql = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + ttTrakm + "' where ctkm_fk = '" + schemeList[i] + "' and npp_fk = '" + nppId + "'";
										System.out.println("7.Cau lenh cap nhat Phanbokhuyenmai: " + sql);
										if(!db.update(sql))
										{
											db.getConnection().rollback();
											return "Loi khi tao moi 'Phanbokhuyenmai'";
										}*/
									}										
								}
								else
								{
									///Chon hinh thuc la tra tien
									System.out.println("123.Hinh thuc la tra tien.....");
									query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + ttTrakm.replaceAll(",", "") + "')";
									if(!db.update(query))
									{
										db.getConnection().rollback();
										return "Lỗi lưu khuyến mại " + query;
									}
	
									/*String sql = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + ttTrakm + "' where ctkm_fk = '" + schemeList[i] + "' and npp_fk = '" + nppId + "'";
									System.out.println("7.Cau lenh cap nhat Phanbokhuyenmai: " + sql);
									if(!db.update(sql))
									{
										db.getConnection().rollback();
										return "Loi khi tao moi 'Phanbokhuyenmai'";
									}*/
								}
							}
							else
							{
								System.out.println("10.Toi da vao trong nay....");
								if(trakmPrimary[count].equals("0"))
								{
									System.out.println("11.Vao trong luu san pham i: =" + i + ", count: " + count);
	
									String msg = CreateKhuyenmai(schemeList[i], schemePhanBo[i], "0", trakm[count], dhId, nppId, Math.round(Double.parseDouble(soxuatkm[i])), khachhang, db);
									if(msg.length() > 0)
									{
										db.getConnection().rollback();
										return "Lỗi tạo mới CTKM: " + msg;
									}
								}
								else
								{
									System.out.println("12.Vao trong luu tien trong san pham");
	
									/*********************************************************************************/
									String msg = CheckNghanSach(schemeList[i], nppId, khachhang, soxuatkm[i], "0", db);
									if(msg.trim().length() > 0)
									{
										db.getConnection().rollback();
										return msg;
									}
									/*********************************************************************************/
	
									query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i].replaceAll(",", ""))) + "','" + ttTrakm.replaceAll(",", "") + "')";
									System.out.println("13.Chen khuyen mai: " + query);
									if(!db.update(query))
									{
										db.getConnection().rollback();
										return query;
									}
	
									/*String sql = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + ttTrakm.replaceAll(",", "") + "' where ctkm_fk = '" + schemeList[count] + "' and npp_fk = '" + nppId + "'";
									if(!db.update(sql))
									{
										db.getConnection().rollback();
										return "12.Lỗi khi tạo mới CTKM: " + sql;
									}*/
								}
							}
						}
					}
					i++;
				}
			}
			
			//CHECK THÊM, 1 ĐƠN HÀNG KHÔNG ĐƯỢC PHÉP CÓ 2 SCHEME PHỤ THUỘC CÙNG 1 LÚC
			/*query = "select ctkm.SCHEME, ctkm2.SCHEME as schemeDUNGCHUNG " + 
					 "from DONHANG_CTKM_TRAKM dh inner join CTKHUYENMAI ctkm on dh.CTKMID = ctkm.PK_SEQ " + 
					 "				inner join CTKHUYENMAI ctkm2 on ctkm.ctkm_fk = ctkm2.PK_SEQ " + 
					 "where dh.DONHANGID = '" + dhId + "' and dh.SOXUAT > 0 " + 
					 "		and dh.CTKMID in (" + 
					 "						select b.ctkm_fk as ctkmDUNGCHUNG " + 
					 "						from DONHANG_CTKM_TRAKM a inner join CTKHUYENMAI b on a.CTKMID = b.PK_SEQ " + 
					 "						where DONHANGID = '" + dhId + "' and SOXUAT > 0 and b.ctkm_fk is not null )";
			
			System.out.println("---1.CHECK KM PHU THUOC:: " + query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					String msg = "Scheme " + rs.getString("SCHEME") + ", và SCHEME " + rs.getString("schemeDUNGCHUNG") + " khai báo phụ thuộc nhau. Bạn chỉ được chọn 1 trong 2 scheme.";
					db.getConnection().rollback();
					rs.close();
					return msg;
				}
				rs.close();
			}*/
			
			//CHECK NẾU CTKM CÓ KHAI PHỤ THUỘC, THÌ CHỈ ĐƯỢC CHỌN SCHEME TRƯỚC ĐÓ ĐÃ ĐƯỢC CHỌN
			/*query = "select b.SCHEME, c.SCHEME as SCHEME_PHUTHUOC,  " + 
					 "	ISNULL( ( select count(*) from DONHANG_CTKM_TRAKM dh " + 
					 "	  where dh.CTKMID = b.ctkm_fk and dh.SOXUAT > 0  " + 
					 "			and dh.donhangId in ( select pk_seq from DONHANG where npp_fk = '" + nppId + "' and trangthai != 2 ) ), 0 ) as daHUONG_SCHEME_PHUTHUOC  " + 
					 "from DONHANG_CTKM_TRAKM a inner join CTKHUYENMAI b on a.CTKMID = b.PK_SEQ " + 
					 "		inner join CTKHUYENMAI c on b.ctkm_fk = c.pk_seq " + 
					 "where DONHANGID = '" + dhId + "' and SOXUAT > 0 and b.ctkm_fk is not null  ";
			
			System.out.println("---2.CHECK KM PHU THUOC:: " + query);
			rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					if(rs.getInt("daHUONG_SCHEME_PHUTHUOC") > 0)
					{
						msg = "Đơn hàng quá khứ đã hưởng KM của scheme phụ thuộc ( " + rs.getString("SCHEME_PHUTHUOC") + " ). Vui lòng bỏ SCHEME ( " + rs.getString("SCHEME") + " ) ra khỏi đơn hàng.";
						rs.close();
						db.getConnection().rollback();
						return msg;
					}
				}
				rs.close();
			}*/
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

			return "";
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
			try
			{
				System.out.println("8.Exception tai day: " + e1.getMessage());
				db.getConnection().rollback();
			}
			catch(Exception err){}

			return "Loi khi tao moi chuong trinh khuyen mai: " + e1.getMessage();
		}
	}

	public String[][] initDATA(HttpServletRequest request, HttpSession session, Utility util) 
	{
		String[][] action_msg = new String[1][2];

		String action = request.getParameter("action") == null ? "" : request.getParameter("action");
		action_msg[0][0] = action;
		action_msg[0][1] = "";

		userId = util.antiSQLInspection(request.getParameter("userId"));
		this.setUserId(userId);

		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		this.setNppId(nppId);
		
		String tructhuocId = request.getParameter("tructhuocId");
		if (tructhuocId == null)
			tructhuocId = "";
		this.setTructhuocId(tructhuocId);
		session.setAttribute("nppId", tructhuocId);

		String nvgnId = util.antiSQLInspection(request.getParameter("nvgnId"));
		if (nvgnId == null)
			nvgnId = "";
		this.setnvgnId(nvgnId);

		String gsbhId = util.antiSQLInspection(request.getParameter("gsbhId"));
		if (gsbhId == null)
			gsbhId = "0";
		this.setGsbhId(gsbhId);

		String ngaygd = util.antiSQLInspection(request.getParameter("ngaygiaodich"));
		if (ngaygd == null || ngaygd == "")
			ngaygd = this.getDateTime();
		this.setNgaygiaodich(ngaygd);
		session.setAttribute("ngaydonhang", ngaygd);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		this.setTrangthai(trangthai);

		String ddkdId = util.antiSQLInspection(request.getParameter("ddkdTen"));
		if (ddkdId == null)
			ddkdId = "";
		this.setDdkdId(ddkdId);
		session.setAttribute("ddkdId", ddkdId);

		String khoId = util.antiSQLInspection(request.getParameter("khoTen"));
		if (khoId == null)
			khoId = "";
		this.setKhoId(khoId);
		session.setAttribute("khoId", this.getKhoId());

		String donhangKhac = util.antiSQLInspection(request.getParameter("donhangKhac"));
		if (donhangKhac == null)
			donhangKhac = "0";
		this.setDonhangKhac(donhangKhac);
		session.setAttribute("donhangKhac", donhangKhac);

		String khId = util.antiSQLInspection(request.getParameter("khId"));
		if (khId == null)
			khId = "";
		this.setKhId(khId);
		session.setAttribute("khId", khId);

		String smartId = util.antiSQLInspection(request.getParameter("smartId"));
		if (smartId == null)
			smartId = "";
		this.setSmartId(smartId);

		String khTen = util.antiSQLInspection(request.getParameter("khTen"));
		if (khTen == null)
			khTen = "";
		this.setKhTen(khTen);

		String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		if (ghichu == null || ghichu.length() == 0)
			ghichu = " ";
		this.setGhiChu(ghichu);

		String muctindung = util.antiSQLInspection(request.getParameter("muctindung"));
		if (muctindung == null)
			muctindung = "0";
		this.setMuctindung(muctindung);

		String chietkhau = util.antiSQLInspection(request.getParameter("ChietKhau"));
		if (chietkhau == null)
			chietkhau = "0";
		else
			chietkhau = chietkhau.replaceAll(",", "");
		this.setChietkhau(chietkhau);

		String chietkhaucu = util.antiSQLInspection(request.getParameter("ChietKhaucu"));
		if (chietkhaucu == null)
			chietkhaucu = "0";
		else
			chietkhaucu = chietkhaucu.replaceAll(",", "");
		this.setChietkhaucu(chietkhaucu);

		String tongtientruocVAT = util.antiSQLInspection(request.getParameter("SoTienChuaVAT"));
		if (tongtientruocVAT == null)
			tongtientruocVAT = "0";
		else
			tongtientruocVAT = tongtientruocVAT.replaceAll(",", "");
		this.setTongtientruocVAT(tongtientruocVAT); 

		String tongtiensauVAT = util.antiSQLInspection(request.getParameter("SoTienCoVAT"));
		if (tongtiensauVAT == null)
			tongtiensauVAT = "0";
		else
			tongtiensauVAT = tongtiensauVAT.replaceAll(",", "");
		this.setTongtiensauVAT(tongtiensauVAT);

		String tongtienDonhang = util.antiSQLInspection(request.getParameter("SoTienSauCKKM"));
		if (tongtienDonhang == null)
			tongtienDonhang = tongtiensauVAT;
		else
			tongtienDonhang = tongtienDonhang.replaceAll(",", "");
		this.setTongtiensauCKKM(Float.parseFloat(tongtienDonhang));
		
		String TienChietKhau = util.antiSQLInspection(request.getParameter("tck"));
		if (TienChietKhau == null)
			TienChietKhau = "0";
		else
			TienChietKhau = TienChietKhau.replaceAll(",", "");
		this.setTongChietKhau(TienChietKhau);

		String VAT = util.antiSQLInspection(request.getParameter("VAT"));
		if (VAT == null)
			VAT = "0"; // OneOne, don gia da co VAT
		else
			VAT = VAT.replaceAll(",", "");
		this.setVAT(VAT);

		String bgstId = util.antiSQLInspection(request.getParameter("bgstId"));
		if (bgstId == null)
			bgstId = "0"; // neu khach hang khong co bang gia sieu thi
		this.setBgstId(bgstId);
		session.setAttribute("bgstId", bgstId);

		String ngayks = util.antiSQLInspection(request.getParameter("ngaykhoaso"));
		if (ngayks == null)
			ngayks = getDateTime();
		this.setNgayKs(ngayks);

		String IsDonHangLe = util.antiSQLInspection(request.getParameter("IsDonHangLe"));
		if (IsDonHangLe == null)
			IsDonHangLe = "0";
		this.setIsDonHangLe(IsDonHangLe);

		String IsSampling = util.antiSQLInspection(request.getParameter("IsSampling"));
		if (IsSampling == null)
			IsSampling = "0";
		this.setIsSampling(IsSampling);

		String donquatang = request.getParameter("donquatang");
		if (donquatang == null)
			donquatang = "0";
		this.setIsDonquatang(donquatang); 

		String sotaikhoan = request.getParameter("sotaikhoan");
		if (sotaikhoan == null)
			sotaikhoan = "";
		this.setSotaikhoan(sotaikhoan);
		
		String capduyet = request.getParameter("capduyet");
		if (capduyet == null)
			capduyet = "";
		this.setCapduyet(capduyet);
		
		String ptChietkhauBH = request.getParameter("txtPTChietkhauBH");
		if( ptChietkhauBH == null )
			ptChietkhauBH = "0";
		this.setPTChietkhauBHKM(Float.parseFloat(ptChietkhauBH));
		
		if(donhangKhac.equals("1"))
		{
			String ingia = request.getParameter("ingia");
			if (ingia == null)
				ingia = "0";
			this.setIngia(ingia);
		}
		else
		{
			this.ingia="1";
			this.setIngia("1");
		}

		String ngaysua = getDateTime();
		this.setNgaysua(ngaysua);

		/*String sql = "select top(1) maFAST as smartId, a.pk_seq as khId, a.ten as khTen, isnull( a.PT_CHIETKHAU, 0) as pt_chietkhau, isnull(a.bgst_fk, '0') as bgstId, c.ddkd_fk,( select top(1) GSBH_FK from ddkd_gsbh where DDKD_FK=c.DDKD_FK )  as gsbh_fk , a.diachi,"+ 
					 " 	a.xuatkhau, ( select loaiNPP from NHAPHANPHOI where pk_seq = a.npp_fk ) as loaiNPP, isnull(a.chucuahieu, '') as chucuahieu, a.kho_fk "+ 
					 "from khachhang a inner join khachhang_tuyenbh b on a.pk_seq = b.khachhang_fk inner join tuyenbanhang c on b.tbh_fk = c.pk_seq inner join ddkd_gsbh e on c.ddkd_fk = e.ddkd_fk  "+ 
					 "where a.trangthai = '1' and ( a.npp_fk = '" + tructhuocId + "' or a.pk_seq in ( select khachhang_fk from KHACHHANG_TRUCTHUOC where tructhuoc_fk = '" + tructhuocId + "' ) ) and a.pk_seq = '"+khId +"'  " +
					 "order by maFAST asc";

		System.out.println("____Lay khach hang: " + sql);
		ResultSet rs = db.get(sql);
		if (rs != null) 
		{
			try 
			{
				if (rs.next()) 
				{
					this.setSmartId(rs.getString("smartId"));
					this.setChucuahieu(rs.getString("chucuahieu"));
					this.setKhId(rs.getString("khId"));
					session.setAttribute("khId", rs.getString("khId"));
					this.setKhTen(rs.getString("khTen") + " - " + rs.getString("diachi"));

					this.setChietkhau(rs.getString("pt_chietkhau"));
					this.setDdkdId(rs.getString("ddkd_fk"));
					session.setAttribute("ddkdId",rs.getString("ddkd_fk"));
					this.setGsbhId(rs.getString("gsbh_fk"));
					String kho_fk = rs.getString("kho_fk") == null ? "" : rs.getString("kho_fk");
					if(kho_fk.length()>0)
					{
						session.setAttribute("khoId", this.getKhoId());
						this.setKhoId(kho_fk);
					}
				}
				rs.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}*/

		String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
		String[] soluong = request.getParameterValues("soluong");

		String[] donvitinh = request.getParameterValues("donvitinh");
		String[] dongia = request.getParameterValues("dongia");
		String[] spchietkhau = request.getParameterValues("spchietkhau");
		String[] tonkho = request.getParameterValues("tonkho");
		String[] ptVAT = request.getParameterValues("ptVAT");
		long tonggiatriKm = Math.round(Float.parseFloat(tongtientruocVAT));

		List<ISanpham> spList = new ArrayList<ISanpham>();
		if (masp != null) 
		{
			ISanpham sanpham = null;
			String[] param = new String[8];
			int m = 0;
			while (m < masp.length) 
			{
				if (masp[m] != "" && ngaygd.trim().length() > 0 && khId.trim().length() > 0) 
				{
					if (soluong[m].length() > 0 && !soluong[m].equals("0") ) // chi them nhung san phamco so luong > 0
					{
						param[0] = "idSP";
						param[1] = masp[m];
						param[2] = tensp[m];
						param[3] = soluong[m].replaceAll(",", "");
						param[4] = donvitinh[m];
						param[5]=dongia[m].replaceAll(",", "");
						param[6] = ""; // khuyen mai
						param[7] = spchietkhau[m].replaceAll(",", "");
						String dongiaGOC = "0";

						/*		System.out.println("---IsSampling: " + IsSampling + " -- MA: " + masp[m] + "  -- DON GIA: " + dongia[m].replaceAll(",", ""));*/

						if (donhangKhac.equals("0") && donquatang.equals("0") && tructhuocId.trim().length() > 0  && khId.trim().length() > 0 )
						{
							String query =  "select a.ma, a.ten, b.donvi, d.AVAILABLE  as hienhuu,  " +
									"	( select THUEXUAT from NGANHHANG where pk_seq = a.nganhhang_fk ) as VAT, " +
									/*"	ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + tructhuocId +  "' ) ) ), 0) as dongia " +*/
									" 	  isnull( ( select GIAMUA_SAUCK    " + 
									 " 				from BGMUANPP_SANPHAM bg_sp  " + 
									 "			    where SANPHAM_FK = a.pk_seq   " + 
									 "					and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + tructhuocId + "' and bg.DVKD_FK = '100001'   order by bg.TUNGAY desc ) ), 0) as dongia " +
									"from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq   " +
									"	inner join NHAPP_KHO d on a.PK_SEQ = d.SANPHAM_FK " +
									"where a.pk_seq > 0  and a.MA='" + masp[m] + "' and a.DVKD_FK = '100001' and d.NPP_FK = '" + tructhuocId + "' and d.kho_fk = '" + khoId + "' and d.nhomkenh_fk = '100000' ";
							
							System.out.println(":::TINH LAI DON GIA: " + query);
							ResultSet rs1 = db.get(query);
							try 
							{
								while (rs1.next())
								{
									param[5]=rs1.getDouble("dongia") + "";
									dongiaGOC = rs1.getString("dongia");
								}
								rs1.close();
							} 
							catch (Exception e1) 
							{
								e1.printStackTrace();
							}
						} 

						sanpham = new Sanpham(param);
						sanpham.setTonhientai(tonkho[m].replaceAll(",", ""));
						sanpham.setSoluong1("1");
						sanpham.setSoluong2("1");
						sanpham.setSoluongThung("1");
						sanpham.setKhoNVBH(ptVAT[m]);
						sanpham.setDongiaGoc(dongiaGOC);
						spList.add(sanpham);
					}
				}
				m++;
			}
		}
		this.splist = spList;

		String hinhthuc = request.getParameter("hinhthuc");
		if (hinhthuc == null)
			hinhthuc = "0";

		String chucuahieu = request.getParameter("chucuahieu");
		if (chucuahieu == null)
			chucuahieu = "";
		this.setChucuahieu(chucuahieu);

		String denghitraCK = request.getParameter("denghitraCK");
		if (denghitraCK == null)
			denghitraCK = "0";
		this.setDenghitrackThang(denghitraCK);
		
		String kbhId = request.getParameter("kbhId");
		if (kbhId == null)
			kbhId = "";
		this.setKbhId(kbhId);
		
		System.out.println("::::2. TRUC THUOC TRUOC KHI AP KM: " + tructhuocId + "  -- THIS: " + this.tructhuocId );
		if(action.equals("submitKh"))
		{
			if( tructhuocId.trim().length() <= 0 )
			{
				this.createRS();
				this.setSpList(spList);
				this.setMessage("Vui lòng chọn nhà phân phối");
				session.setAttribute("dhBean", this);

				return action_msg;
			}
			
			/*String sql =   "select top(1) maFAST as smartId, a.pk_seq as khId, a.ten as khTen, isnull( a.PT_CHIETKHAU, 0) as pt_chietkhau, isnull(a.bgst_fk, '0') as bgstId, c.ddkd_fk, " + 
					" ( select top(1) GSBH_FK from ddkd_gsbh where DDKD_FK=c.DDKD_FK )  as gsbh_fk , a.diachi, " + 
					" ( select top(1) kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = a.pk_seq )  as kbh_fk , " + 
					" 	a.xuatkhau, ( select loaiNPP from NHAPHANPHOI where pk_seq = a.npp_fk ) as loaiNPP, isnull(a.chucuahieu, '') as chucuahieu, a.kho_fk "+ 
					"from khachhang a inner join khachhang_tuyenbh b on a.pk_seq = b.khachhang_fk inner join tuyenbanhang c on b.tbh_fk = c.pk_seq inner join ddkd_gsbh e on c.ddkd_fk = e.ddkd_fk  "+ 
					"where a.trangthai = '1' and ( a.npp_fk = '" + tructhuocId + "' and a.pk_seq in ( select khachhang_fk from KHACHHANG_TRUCTHUOC where tructhuoc_fk = '" + tructhuocId + "' )  ) " + 
						"  and a.pk_seq = '" + khId + "' and len(dbo.trim('" + smartId + "')) >0 ";*/
			
			String sql = "select top(1) maFAST as smartId, a.pk_seq as khId, a.ten as khTen, isnull( a.PT_CHIETKHAU, 0) as pt_chietkhau, isnull(a.bgst_fk, '0') as bgstId, c.ddkd_fk,( select top(1) GSBH_FK from ddkd_gsbh where DDKD_FK=c.DDKD_FK )  as gsbh_fk , a.diachi,"+ 
						 " 	a.xuatkhau, ( select loaiNPP from NHAPHANPHOI where pk_seq = a.npp_fk ) as loaiNPP, isnull(a.chucuahieu, '') as chucuahieu, a.kho_fk, "+ 
						 " ( select top(1) kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = a.pk_seq )  as kbh_fk " + 
						 "from khachhang a inner join khachhang_tuyenbh b on a.pk_seq = b.khachhang_fk inner join tuyenbanhang c on b.tbh_fk = c.pk_seq inner join ddkd_gsbh e on c.ddkd_fk = e.ddkd_fk  "+ 
						 "where a.trangthai = '1' and ( a.npp_fk = '" + tructhuocId + "' or a.pk_seq in ( select khachhang_fk from KHACHHANG_TRUCTHUOC where tructhuoc_fk = '" + tructhuocId + "' ) ) and a.pk_seq = '"+khId +"'  ";
			
			if(this.tdv_dangnhap_id.trim().length() > 0)
			{
				this.ddkdId = this.tdv_dangnhap_id;
				sql += " and c.ddkd_fk = '" + this.ddkdId + "' ";
			}
			
			sql +=	" order by maFAST asc";

			System.out.println("____Lay khach hang: " + sql);
			ResultSet rs = db.get(sql);
			if (rs != null) 
			{
				try 
				{
					if (rs.next()) 
					{
						this.setSmartId(rs.getString("smartId"));
						this.setChucuahieu(rs.getString("chucuahieu"));
						this.setKhId(rs.getString("khId"));
						session.setAttribute("khId", rs.getString("khId"));
						this.setKhTen(rs.getString("khTen") + " - " + rs.getString("diachi"));
						this.setKbhId( rs.getString("kbh_fk") );
						System.out.println("::: KENH BAN HANG: " + rs.getString("kbh_fk") );

						this.setChietkhau(rs.getString("pt_chietkhau"));

						this.setDdkdId(rs.getString("ddkd_fk"));
						session.setAttribute("ddkdId",rs.getString("ddkd_fk"));
						this.setGsbhId(rs.getString("gsbh_fk"));
						
						//String kho_fk = rs.getString("kho_fk") == null ? "" : rs.getString("kho_fk");
						String kho_fk = "100000";
						//rs.close();
						if(kho_fk.length()>0)
						{
							session.setAttribute("khoId", this.getKhoId());
							this.setKhoId(kho_fk);
						}

						if ( id == null || id.trim().length() <= 0 ) 
						{
							this.setSpList(spList);
							this.createRS();
							this.setEnterKH("1");
							session.setAttribute("dhBean", this);

							return action_msg;
						} 
						else 
						{
							this.init();
							this.setSpList(spList);
							this.setSmartId(rs.getString("smartId"));
							this.setKhId(rs.getString("khId"));
							this.setKhTen(rs.getString("khTen") + " - " + rs.getString("diachi"));
							this.setDdkdId(rs.getString("ddkd_fk"));
							this.setGsbhId(rs.getString("gsbh_fk"));
							this.setEnterKH("1");
							session.setAttribute("dhBean", this);

							return action_msg;
						}		
					} 
					else 
					{
						if ( id == null || id.trim().length() <= 0 ) 
						{
							this.createRS();
							this.setSpList(spList);
							this.setMessage("Mã khách hàng không đúng, hoặc khách hàng chưa được phân tuyến vui lòng kiểm tra lại...");
							session.setAttribute("dhBean", this);

							return action_msg;
						} 
						else 
						{
							this.init();
							this.setSpList(spList);
							this.setMessage("Mã khách hàng không đúng, hoặc khách hàng chưa được phân tuyến vui lòng kiểm tra lại...");
							session.setAttribute("dhBean", this);

							return action_msg;
						}
					}	
				} 
				catch (Exception e) { e.printStackTrace(); }
			}

			return action_msg;
		}
		else if(action.equals("save"))
		{
			if (id == null || id.trim().length() <= 0) 
			{
				boolean tao = this.CreateDh(spList);
				String dhId = this.getId();
				if (!tao) 
				{
					this.createRS();
					this.setSpList(this.splist);
					session.setAttribute("dhBean", this);
					this.msg = "Lỗi tạo mới đơn hàng";
				} 
				else 
				{
					//RENEW BEAN
					this.resetDATA(this.id);

					this.setUserId(userId);
					this.createRS();
					this.setNgaygiaodich(ngaygd);
					this.setMessage("Số đơn hàng bạn vừa lưu " + dhId);

					// Save Data into session
					session.setAttribute("dhBean", this);
					session.setAttribute("khId", "");
					session.setAttribute("ddkdId", "");
					
					System.out.println("::: TRUC THUOC: " + tructhuocId );
					session.setAttribute("nppId", tructhuocId);
				}

				return action_msg;
			} 
			else 
			{
				// Kiem tra xem neu don hang do da co khuyen mai, ma
				boolean flag = false;
				boolean cokm = Boolean.parseBoolean(util.antiSQLInspection(request.getParameter("cokm")));
				if(cokm == true)
				{
					flag = this.CheckDonHangDKM(spList, id, khId, db);
					if(flag == false)
					{
						this.init();
						this.setSpList(spList);
						this.setKhId(khId);
						this.setDdkdId(ddkdId);
						this.setMessage("Đơn hàng này đã hưởng khuyến mại, khi thay đổi sản phẩm / khách hàng bạn phải áp lại khuyến mại");
						session.setAttribute("dhBean", this);

						action_msg[0][1] = "0";
						return action_msg;
					}
				}

				boolean temp = false;
				if (trangthai.equals("3")) // da xuat kho
					temp = this.UpdateDhXuatKho(spList);
				else
					temp = this.UpdateDh(spList, action);

				if (temp == false) 
				{
					this.init();
					this.setSpList(spList);
					this.setKhId(khId);
					this.setDdkdId(ddkdId);
					session.setAttribute("dhBean", this);

					action_msg[0][1] = "1";
					return action_msg;
				} 
				else 
				{	
					//dhBean = (IDonhang) new Donhang("");
					this.resetDATA(this.id);
					this.setUserId(userId);
					this.createRS();
					this.setNgaygiaodich(ngaygd);

					// Save Data into session
					session.setAttribute("dhBean", this);
					session.setAttribute("khId", "");
					session.setAttribute("ddkdId", "");
					
					System.out.println("::: TRUC THUOC: " + tructhuocId );
					session.setAttribute("nppId", tructhuocId );

					action_msg[0][1] = "2";
					return action_msg;
				}
			}
		}
		else if(action.equals("chotdonhang"))
		{
			// save donhangDXK truoc
			if (!(this.UpdateDh(spList, "save"))) 
			{
				this.init();
				this.setKhId(khId);
				this.setDdkdId(ddkdId);
				session.setAttribute("dhBean", this);
				//String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";
				//response.sendRedirect(nextJSP);

				action_msg[0][1] = "3";
				return action_msg;
			} 
			else 
			{
				String msg = this.DuyetDonHang(id, "1", userId);
				if (msg.trim().length() > 0) 
				{
					this.init();
					this.setSpList(spList);
					this.setDdkdId(ddkdId);
					this.setKhId(khId);
					this.setMessage(msg);
					session.setAttribute("dhBean", this);
					//String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";
					//response.sendRedirect(nextJSP);

					action_msg[0][1] = "4";
					return action_msg;
				} 
				else //RA LAI TRANG TONG
				{
					action_msg[0][1] = "5";
					return action_msg;
				}
			}
		}
		else if(action.equals("duyetdonhang"))
		{
			String msg = this.CS_SS_DuyetDonHang(id, userId);
			if (msg.trim().length() > 0) 
			{
				this.init();
				this.setSpList(spList);
				this.setDdkdId(ddkdId);
				this.setKhId(khId);
				this.setMessage(msg);
				session.setAttribute("dhBean", this);
				//String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";
				//response.sendRedirect(nextJSP);

				action_msg[0][1] = "4";
				return action_msg;
			} 
			else //RA LAI TRANG TONG
			{
				action_msg[0][1] = "5";
				return action_msg;
			}
		}
		else if(action.equals("apkhuyenmai"))
		{
			System.out.println("::::2. TRUC THUOC TRUOC KHI AP KM: " + tructhuocId );
			action_msg[0][1] = this.ProcessKHUYENMAI(request, session, util, tonggiatriKm);
			return action_msg;
		}
		else
		{
			if (action.equals("xoatichluy")) 
			{
				String schemeTL = request.getParameter("schemeTL_XOA");
				String query = "delete DUYETTRAKHUYENMAI_DONHANG where donhang_fk = '" + id + "' and diengiai = '" + schemeTL + "' ";
				db.update(query);

				//Diễn giải đang để là CQX và CQB nên phải thêm 5 đằng sau
				query = "delete DUYETTRAKHUYENMAI_DUNO_DONHANG_DATRA where donhang_fk = '" + id + "' and diengiai + '5' = '" + schemeTL + "' ";
				db.update(query);

				util.Update_GiaTri_DonHang(id, db);
			}

			String LOAINHAPHANPHOI = request.getParameter("LOAINHAPHANPHOI");
			if (LOAINHAPHANPHOI == null)
				LOAINHAPHANPHOI = "0";
			this.setLoaiNppId(LOAINHAPHANPHOI);

			this.setUserId(userId);
			this.createRS();

			//String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNew.jsp";
			if (id != null) 
			{
				if( id.trim().length() > 0 )
				{
					this.initTichLuy();
					this.getTrakhuyenmai();
					//nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";
				}
			}

			this.setSpList(spList);
			this.setNgaygiaodich(ngaygd);

			session.setAttribute("ddkdId", ddkdId);
			System.out.println("::: TRUC THUOC: " + tructhuocId );
			session.setAttribute("nppId", tructhuocId );
			session.setAttribute("dhBean", this);
			//response.sendRedirect(nextJSP);
		}

		return action_msg;

	}

	public String CS_SS_DuyetDonHang(String id, String userId) 
	{
		System.out.println(":::::: CAP DUYET :::::::::: " + this.capduyet);
		geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			this.id = id;
			
			//Check xem SS đã duyệt chưa
			query = " select SS_DUYET, npp_fk, ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = dh.KBH_FK ) as nhomkenh_fk " + 
					" from DONHANG dh where pk_seq = '" + this.id + "'  ";
			ResultSet rs = db.get(query);
			String SS_DA_DUYET = "0";
			String nhomkenh_fk = "";
			if(rs.next())
			{
				SS_DA_DUYET = rs.getString("SS_DUYET");
				nhomkenh_fk = rs.getString("nhomkenh_fk");
				this.nppId = rs.getString("npp_fk");
				rs.close();
			}
			
			//DLPP KHÔNG PHÂN BIỆT NHÓM KÊNH
			nhomkenh_fk = "100000";
			
			if(SS_DA_DUYET.equals("0")) //GIẢM KHO HÀNG BÁN
			{
				//Tự động đề xuất kho chi tiết
				//B1. CAP NHAT KHO TONG
				query = " select c.kho_fk as khoId, b.pk_seq as spId, b.ten as spTEN, sum(a.soluong) as soluong  " +
						" from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donhang c on a.donhang_fk = c.pk_seq   " +
						" where c.trangthai != 2 and a.donhang_fk in ( " + this.id + " )  " +
						" group by c.kho_fk, b.pk_seq, b.ten ";
				System.out.println("---5.CHEN SP: " + query);
				ResultSet rsKHO = db.get(query);
				{
					while(rsKHO.next())
					{
						String khoId = rsKHO.getString("khoId");
						//String kbhId = rsKHO.getString("kbhId");
						String spId = rsKHO.getString("spId");
						//String spTEN = rsKHO.getString("spTEN");
						double soluong = rsKHO.getDouble("soluong");

						query = "update nhapp_kho set BOOKED = BOOKED - '" + soluong + "', SOLUONG = SOLUONG - '" + soluong + "' " +
								" where sanpham_fk = '" + spId + "' and npp_fk = '" + nppId + "' and kho_fk = '" + khoId + "' and nhomkenh_fk = '" + nhomkenh_fk + "' ";

						//	System.out.println("---UPDATE KHO TONG: " + query);
						if(db.updateReturnInt(query) <= 0 )
						{
							db.getConnection().rollback();
							return "5.Lỗi khi chốt xuất kho: " + query;
						}

						//TU DE XUAT LO --> KHO CHI TIET THI VAN TRU SO LUONG + AVAI
						query = "select AVAILABLE, SOLO, NGAYHETHAN  " +
								"from NHAPP_KHO_CHITIET " +
								"where AVAILABLE > 0 and NPP_FK = '" + nppId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and KHO_FK = '" + khoId + "' and SANPHAM_FK = '" + spId + "' " +
								"order by NGAYHETHAN asc, AVAILABLE asc";

						System.out.println("--TU DE XUAT: " + query);
						rs = db.get(query);
						String NgayHetHan="";
						double tongluongxuatCT = 0;  //PHAI BAT BUOC TONG LUONG XUAT O KHO CHI TIET PHAI BANG TONG LUONG XUAT O KHO TONG
						/*if(rs != null)*/
						{
							double totalLUONG = 0;
							boolean exit = false;
							while(rs.next() && !exit)
							{
								NgayHetHan= rs.getString("NGAYHETHAN");

								totalLUONG += rs.getDouble("AVAILABLE");
								double soluongXUAT = 0;

								if(totalLUONG <= soluong)
								{
									soluongXUAT = rs.getDouble("AVAILABLE");
								}
								else
								{
									soluongXUAT = soluong - ( totalLUONG - rs.getDouble("AVAILABLE") );
									exit = true;
								}

								if(soluongXUAT > 0)
								{
									//CAP NHAT KHO CHI TIET
									query = "update nhapp_kho_chitiet set AVAILABLE = AVAILABLE - '" + soluongXUAT + "', SOLUONG = SOLUONG - '" + soluongXUAT + "' " +
											" where sanpham_fk = '" + spId + "' and npp_fk = '" + nppId + "' and kho_fk = '" + khoId + "' and nhomkenh_fk = '" + nhomkenh_fk + "' and SOLO = '" + rs.getString("SOLO") + "' and NgayHetHan='"+NgayHetHan+"' ";

									int kq = db.updateReturnInt(query);
									//	System.out.println("---UPDATE KHO CHI TIET TOI DAY: " + query + " -- KQ: " + kq);
									if(kq != 1 )
									{
										System.out.println("--LOI TAO DAY ROI: " + query + " -- KQ: " + kq);
										db.getConnection().rollback();
										return "6.Lỗi khi chốt xuất kho: " + query;
									}

									//INSERT DONHANG - CHI TIET
									query = "insert into DONHANG_SANPHAM_CHITIET (DONHANG_FK, NHOMKENH_FK, KHO_FK, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN) " +
											"values( '" + this.id + "', '" + nhomkenh_fk + "', '" + khoId + "', '" + spId + "', '" + soluongXUAT +  "', '" + rs.getString("SOLO") + "', '" + rs.getString("NGAYHETHAN") + "' )";
									System.out.println("---CHEN PXK CHI TIET: " + query);
									if(db.updateReturnInt(query)!=1 )
									{
										db.getConnection().rollback();
										return "7.Lỗi khi chốt đơn hàng: " + query;
									}

									tongluongxuatCT += soluongXUAT;
									if(exit)  //DA XUAT DU
									{
										//rs.close();
										break;
									}
								}
							}
							rs.close();
						}
						System.out.println(tongluongxuatCT +"____________"+soluong);
						if(tongluongxuatCT != soluong)
						{
							db.getConnection().rollback();
							rsKHO.close();
							return "1.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
						}

					}
					rsKHO.close();
				}

				//CHECK TONG KHO CHI TIET PHAI BANG TONG TRONG KHO TONG
				query = "select count(*) as soDONG  " +
						"from DONHANG_SANPHAM tong left join  " +
						"	( " +
						"		select sanpham_fk, kho_fk, sum(soluong) as soluong  " +
						"		from DONHANG_SANPHAM_CHITIET " +
						"		where  DONHANG_FK = '" + this.id + "' " +
						"		group by sanpham_fk, kho_fk " +
						"	) " +
						"	CT on tong.sanpham_fk = CT.sanpham_fk and  tong.kho_fk = CT.kho_fk " +
						"where DONHANG_FK = '" + this.id + "' and tong.soluong != isnull(CT.soluong, 0) ";
				System.out.println("---CHECK DON HANG CHI TIET: " + query);
				ResultSet rsCHECK = db.get(query);
				int soDONG = 0;
				/*if(rsCHECK != null )*/
				{
					if( rsCHECK.next() )
					{
						soDONG = rsCHECK.getInt("soDONG");
					}
					rsCHECK.close();
				}

				if(soDONG > 0)
				{
					db.getConnection().rollback();
					return "11.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
				}
			}
			
			
			if(this.capduyet.equals("CS")) //TRU HANG KHO KM CUA TRINH DUOC VIEN
			{
				geso.traphaco.center.db.sql.dbutils db = new geso.traphaco.center.db.sql.dbutils();
				
				query = "SELECT DH.NGAYNHAP, \n" +
						"CASE WHEN CONVERT(INT, SUBSTRING(DH.NGAYNHAP, 6, 2)) > 1 THEN \n" + 
						"ISNULL(  \n" +
						"(  SELECT AVG(GIATON) FROM ERP_TONKHOTHANG \n" + 
					  	"	WHERE THANG = (CONVERT(INT, SUBSTRING(DH.NGAYNHAP, 6, 2)) - 1) AND NAM = SUBSTRING(DH.NGAYNHAP, 1, 4) \n" + 
					  	"	AND SANPHAM_FK IN(SELECT PK_SEQ FROM SANPHAM WHERE MA = DH_KM.SPMA)  \n" +
					  	"	AND CONGTY_FK IN (SELECT CONGTY_FK FROM NHAPHANPHOI  \n" +
					  	"					  WHERE PK_SEQ IN (SELECT TRUCTHUOC_FK FROM NHAPHANPHOI where PK_SEQ = DH.NPP_FK)) \n" +
						") \n" +
						", 0 ) \n" +
						"ELSE  \n" +
						"ISNULL(  \n" +
						"	( SELECT AVG(GIATON) FROM ERP_TONKHOTHANG \n" + 
					 	"	  WHERE THANG = 12 and NAM = (SUBSTRING(DH.NGAYNHAP, 1, 4) - 1) \n" + 
					 	"	  AND SANPHAM_FK  IN(SELECT PK_SEQ FROM SANPHAM WHERE MA = DH_KM.SPMA) \n" + 
					 	"	  AND CONGTY_FK IN (SELECT CONGTY_FK FROM NHAPHANPHOI  \n" +
					 	"					    WHERE PK_SEQ IN (SELECT TRUCTHUOC_FK FROM NHAPHANPHOI where PK_SEQ = DH.NPP_FK)) \n" +
						") \n" +
						", 0 )  \n" +
						"END AS GIAVON, \n" + 
						"'XK02' AS MAXUATKHO, \n" +
				
						"DH_KM.SPMA, DH_KM.SOLUONG, \n" +
						"( SELECT PK_SEQ FROM SANPHAM WHERE MA = DH_KM.spMA ) AS SPID, \n" + 
						"( SELECT SCHEME FROM CTKHUYENMAI WHERE pk_seq = DH_KM.ctkmId ) AS SCHEME, \n" +
			 	
						"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63220000'  \n" +
						"AND CONGTY_FK IN (SELECT CONGTY_FK FROM NHAPHANPHOI  \n" +
						"				   WHERE PK_SEQ IN (SELECT TRUCTHUOC_FK FROM NHAPHANPHOI where PK_SEQ = DH.NPP_FK))) AS TAIKHOANNO_FK, \n" +

						"( \n" +
						"	SELECT TOP 1 LSP.TAIKHOANKT_FK \n" + 
						"	FROM SANPHAM SP \n" +
						"	INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK \n" +
						"	WHERE SP.PK_SEQ IN(SELECT PK_SEQ FROM SANPHAM WHERE MA = DH_KM.SPMA)  \n" +
						") AS TAIKHOANCO_FK, \n" +
						"(SELECT PK_SEQ FROM ERP_NOIDUNGNHAP WHERE MA = 'XK02') AS NOIDUNGXUAT_FK, \n" +
						"N'Xuất khuyến mại - Kho trình dược viên' AS KHOANMUC \n" +
			 
						"FROM DONHANG_CTKM_TRAKM DH_KM \n" +
						"INNER JOIN DONHANG DH ON DH.PK_SEQ = DH_KM.DONHANGID \n" +
						"WHERE DH_KM.DONHANGID = '" + id + "' AND SOLUONG IS NOT NULL \n" ;

			
/*				query = "select spMa, soluong, " + 
						" 	( select pk_seq from SANPHAM where ma = dh.spMA ) as spId,  " +
						" 	( select scheme from CTKHUYENMAI where pk_seq = dh.ctkmId ) as scheme  " +
						"from donhang_ctkm_trakm dh " +
						"where donhangid='" + id + "' and soluong is not null ";*/
				System.out.println("::: Cau lenh lay du lieu duyet KM: " + query + "\n");
				ResultSet rsKM = db.get(query);
				if (rsKM != null) 
				{
					while (rsKM.next()) 
					{
						String maSp = rsKM.getString("spMa");
						double soluong = rsKM.getDouble("soluong");
						String spId = rsKM.getString("spId");
						String scheme = rsKM.getString("scheme");
						
						//KHO TDV KO OHAN BIET NHOM KENH
						query = "Update nhapp_kho_ddkd set soluong = soluong - '" + soluong + "', booked = booked - '" + soluong + "' " +
								"where kho_fk = '100008' " + 
								//" and npp_fk = '" + nppId + "'" +
								"	 and ddkd_fk in ( select ddkd_fk from DONHANG where pk_seq = '" + this.id + "') " + 
								"	 and sanpham_fk in (select pk_seq from sanpham where ma = '" + maSp + "') " + 
								//"	 and nhomkenh_fk in ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = ( select kbh_fk from DONHANG where PK_SEQ = '" + this.id + "' ) )";
								"	 and nhomkenh_fk in ( 100000 )";
						if (!db.update(query)) {
							db.getConnection().rollback();
							return "Error :" + query;
						}

						// DINH KHOAN KE TOAN						
						String ngaychungtu = rs.getString("NGAYNHAP");
						String nam = ngaychungtu.substring(0, 4);
						String thang = "" + (Integer.parseInt(ngaychungtu.substring(5, 7)));
						String ngayghinhan = ngaychungtu;
						String loaichungtu = rs.getString("MAXUATKHO");
						String sochungtu = id;
						String taikhoanNO_fk =  rs.getString("TAIKHOANNO_FK");
						String taikhoanCO_fk =  rs.getString("TAIKHOANCO_FK");
						String NOIDUNGNHAPXUAT_FK = rs.getString("NOIDUNGXUAT_FK");
						String NO = "" + (rs.getDouble("SOLUONG")*rs.getDouble("GIAVON"));
						String CO = NO;
						String DOITUONG_NO = "Giá vốn hàng khuyến mại";
						String MADOITUONG_NO = "";
						String DOITUONG_CO = "Sản phẩm";
						String MADOITUONG_CO = rs.getString("spId");
						String LOAIDOITUONG = "";
						String SOLUONG = "" + rs.getDouble("SOLUONG");
						String DONGIA = "" + rs.getDouble("GIAVON");
						String TIENTEGOC_FK = "100000";
						String DONGIANT = "0";
						String TIGIA_FK = "1";
						String TONGGIATRI = NO;
						String TONGGIATRINT = TONGGIATRI;
						String khoanmuc = rs.getString("KHOANMUC");
						util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoanNO_fk, taikhoanCO_fk, 
										 	NOIDUNGNHAPXUAT_FK, NO, CO, DOITUONG_NO, MADOITUONG_NO, DOITUONG_CO, MADOITUONG_CO, LOAIDOITUONG, 
										 	SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, khoanmuc);
						
						// KET THUC DINH KHOAN KE TOAN						
						
						//TU DE XUAT LO --> KHO CHI TIET THI VAN TRU SO LUONG + AVAI
						query = "select AVAILABLE, SOLO, NGAYHETHAN  " +
								"from NHAPP_KHO_DDKD_CHITIET " +
								"where AVAILABLE > 0 and NHOMKENH_FK = '100000' and KHO_FK = '100008' and SANPHAM_FK = '" + spId + "' " +
								"	 and ddkd_fk in ( select ddkd_fk from DONHANG where pk_seq = '" + this.id + "') " + 
								"	 and npp_fk = ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' and PK_SEQ = ( select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "' ) )	" +
								"order by NGAYHETHAN asc, AVAILABLE asc";

						System.out.println("--TU DE XUAT: " + query);
						rs = db.get(query);
						String NgayHetHan="";
						double tongluongxuatCT = 0;  //PHAI BAT BUOC TONG LUONG XUAT O KHO CHI TIET PHAI BANG TONG LUONG XUAT O KHO TONG
						/*if(rs != null)*/
						{
							double totalLUONG = 0;
							boolean exit = false;
							while(rs.next() && !exit)
							{
								NgayHetHan= rs.getString("NGAYHETHAN");

								totalLUONG += rs.getDouble("AVAILABLE");
								double soluongXUAT = 0;

								if(totalLUONG <= soluong)
								{
									soluongXUAT = rs.getDouble("AVAILABLE");
								}
								else
								{
									soluongXUAT = soluong - ( totalLUONG - rs.getDouble("AVAILABLE") );
									exit = true;
								}

								if(soluongXUAT > 0)
								{
									//CAP NHAT KHO CHI TIET
									query = "update NHAPP_KHO_DDKD_CHITIET set AVAILABLE = AVAILABLE - '" + soluongXUAT + "', SOLUONG = SOLUONG - '" + soluongXUAT + "' " +
											" where sanpham_fk = '" + spId + "' and  kho_fk = '100008' and nhomkenh_fk = '100000' and SOLO = '" + rs.getString("SOLO") + "' and NgayHetHan='"+NgayHetHan+"' " +
											"	 and npp_fk = ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' and PK_SEQ = ( select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "' ) )	" +
											"	 and ddkd_fk in ( select ddkd_fk from DONHANG where pk_seq = '" + this.id + "') ";

									int kq = db.updateReturnInt(query);
									System.out.println("---UPDATE KHO CHI TIET TOI DAY: " + query + " -- KQ: " + kq);
									if(kq != 1 )
									{
										System.out.println("--LOI TAO DAY ROI: " + query + " -- KQ: " + kq);
										db.getConnection().rollback();
										return "6.Lỗi khi chốt xuất kho: " + query;
									}

									//INSERT DONHANG - CHI TIET
									query = "insert into DONHANG_SANPHAM_CHITIET (DONHANG_FK, NHOMKENH_FK, KHO_FK, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, SCHEME) " +
											"values( '" + this.id + "', '" + nhomkenh_fk + "', '100008', '" + spId + "', '" + soluongXUAT +  "', '" + rs.getString("SOLO") + "', '" + rs.getString("NGAYHETHAN") + "', '" + scheme + "' )";
									System.out.println("---CHEN PXK CHI TIET: " + query);
									if(db.updateReturnInt(query)!=1 )
									{
										db.getConnection().rollback();
										return "7.Lỗi khi chốt đơn hàng: " + query;
									}

									tongluongxuatCT += soluongXUAT;
									if(exit)  //DA XUAT DU
									{
										//rs.close();
										break;
									}
								}
							}
							rs.close();
						}
						System.out.println(tongluongxuatCT +"____________"+soluong);
						if(tongluongxuatCT != soluong)
						{
							db.getConnection().rollback();
							rsKM.close();
							return "2.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
						}
						
					}
					rsKM.close();
				}
			}
			
			if(this.capduyet.equals("CS")) //CS duyệt thì trừ kho KM của NVBH, trường hợp duyệt phủ cấp ( không đợi SS duyệt ) thì trừ kho hàng bán luông
			{
				query = " Update DONHANG set trangthai = '1', CS_DUYET = 1, thoidiem_cs_duyet = getdate(), userId_cs_duyet = '" + userId + "', ghichu = N'" + this.ghichu + "' " +
						"where pk_seq = '" + this.id + "' ";
			}
			else if(this.capduyet.equals("SS")) //SS duyệt thì trừ kho hàng bán 
			{
				query = " Update DONHANG set trangthai = '1', SS_DUYET = 1, thoidiem_ss_duyet = getdate(), userId_ss_duyet = '" + userId + "', ghichu = N'" + this.ghichu + "' " +
						"where pk_seq = '" + this.id + "' ";
			}
			
			System.out.println("---DUYET::: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Lỗi khi duyệt: " + query;
				return this.msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip_DonHang", new String[] { this.id } );
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			db.update("rollback");
			this.msg = ex.getMessage();
		}
			
		return "";
	}

	private String ProcessKHUYENMAI(HttpServletRequest request, HttpSession session, Utility util, long tonggiatriKm) 
	{
		this.setAplaikhuyenmai(false);
		this.setIsChuaApkhuyenmai(true);

		// Save donhang truoc
		if (id.equals("")) 
		{
			boolean tao = this.CreateDh(this.splist);
			if (!tao) 
			{
				this.createRS();
				this.setSpList(this.splist);
				session.setAttribute("dhBean", this);
				//String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNew.jsp";
				//response.sendRedirect(nextJSP);

				return "6.1";
			} 
			else 
			{
				id = this.getId();
			}
		} 
		else 
		{
			boolean temp = this.UpdateDh(this.splist, "apkhuyenmai");

			if (!temp) 
			{
				this.init();
				this.setKhId(khId);
				this.setDdkdId(ddkdId);
				session.setAttribute("dhBean", this);
				//String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";
				//response.sendRedirect(nextJSP);

				return "6.2";
			}
		}

		Hashtable<String, Float> sanpham_soluong = new Hashtable<String, Float>();
		Hashtable<String, Float> sanpham_dongia = new Hashtable<String, Float>();
		Hashtable<String, Float> sanpham_quycach = new Hashtable<String, Float>();

		String sql =  "select ( select ma from SANPHAM where pk_seq = a.SANPHAM_FK ) as spMA, a.DONGIAGOC as GIAMUA, sum(a.SOLUONG) as soluong, "+
				"	isnull(( select SOLUONG1 from QUYCACH qc where SANPHAM_FK = a.SANPHAM_FK and DVDL2_FK = '100018' and b.dvdl_fk=qc.dvdl1_fk ), 1) as quycach, a.thueVAT  "+
				"from DONHANG_SANPHAM a inner join sanpham b on b.pk_seq=a.sanpham_fk "+
				"where a.DONHANG_FK = '" + id + "' "+
				"group by a.SANPHAM_FK, a.DONGIAGOC,b.DVDL_FK, a.thueVAT ";
		System.out.println(":::: LAY SP AP KM: " + sql );
		ResultSet rsInfo = db.get(sql);

		String _masp = "";
		String _soluong = "";
		String _dongia = "";
		String _soluong1 = "";

		try 
		{
			while (rsInfo.next()) 
			{
				float dongiaSAUVAT = rsInfo.getFloat("GIAMUA") * ( 1 + rsInfo.getFloat("thueVAT") / 100 );
				
				sanpham_soluong.put(rsInfo.getString("spMA"),rsInfo.getFloat("soluong"));
				sanpham_dongia.put(rsInfo.getString("spMA"), dongiaSAUVAT );
				sanpham_quycach.put(rsInfo.getString("spMA"),rsInfo.getFloat("quycach"));

				_masp += rsInfo.getString("spMA") + "__";
				_soluong += rsInfo.getString("soluong") + "__";
				_dongia += dongiaSAUVAT + "__";
				_soluong1 += rsInfo.getString("quycach") + "__";
			}
			rsInfo.close();
		} 
		catch (Exception e) {e.printStackTrace();}

		if (_masp.trim().length() > 0) 
		{
			_masp = _masp.substring(0, _masp.length() - 2);
			_soluong = _soluong.substring(0, _soluong.length() - 2);
			_dongia = _dongia.substring(0, _dongia.length() - 2);
			_soluong1 = _soluong1.substring(0, _soluong1.length() - 2);
		}

		if (!trangthai.equals("3")) 
		{
			// truoc khi ap lai khuyen mai
			try 
			{
				db.getConnection().setAutoCommit(false);
				String msg = this.capNhatKM(id, this.tructhuocId, khId, trangthai, db);

				if (msg.length() > 0) 
				{
					this.init();
					this.setKhId(khId);
					this.setDdkdId(ddkdId);

					this.setMessage("3.Khong The Cap Nhat Khuyen Mai Cua Don Hang: "+ msg);
					session.setAttribute("dhBean", this);
					//String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";
					//response.sendRedirect(nextJSP);

					return "6.2";
				} 
				else 
				{
					db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
				}
			} 
			catch (Exception e)
			{
				try 
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
				} 
				catch (Exception e1) {}
			}

			//XLkhuyenmai xlkm = new XLkhuyenmai(userId, ngaygd, khId, IsDonHangLe); ---> LẤY TRONG BEAN
			XLkhuyenmai xlkm = this.getXLkhuyenmai(userId, this.ngaygiaodich, khId, IsDonHangLe, id);
			xlkm.setKhachhang(khId);
			xlkm.setDenghitraCK(denghitraCKTHANG);
			
			xlkm.setMasp(_masp.split("__"));
			xlkm.setSoluong(_soluong.split("__"));
			xlkm.setDongia(_dongia.split("__"));
			xlkm.setQuycach(_soluong1.split("__"));

			xlkm.setIdDonhang(id);
			xlkm.setTonggiatriDh((float) tonggiatriKm);
			xlkm.setNgaygiaodich(this.ngaygiaodich);
			xlkm.setLoaiDonHang(IsDonHangLe);

			xlkm.setHashA(sanpham_soluong);
			xlkm.setHashB(sanpham_dongia);
			xlkm.setHash_QuyCach(sanpham_quycach);

			xlkm.setDieuchinh(false); 
			xlkm.ApKhuyenMai();

			List<ICtkhuyenmai> ctkmResual = xlkm.getCtkmResual();
			System.out.println("+++So xuat khuyen mai duoc huong: "+ ctkmResual.size() + "\n");

			if (xlkm.checkConfirm()) // bi dung --> sang trang lua chon
			{
				System.out.println("Bi dung san pham roi...\n");
				session.setAttribute("xlkm", xlkm);
				//String nextJSP = "/TraphacoHYERP/pages/Distributor/KhuyenMai.jsp";

				//response.sendRedirect(nextJSP);
				return "6.3";
			}

			String msg = ""; // nhung ctkm khong thoa
			for (int i = 0; i < ctkmResual.size(); i++) 
			{
				ICtkhuyenmai ctkhuyenmai = ctkmResual.get(i);

				// System.out.println("ConFi laf: "+ctkhuyenmai.getConfirm());
				if (ctkhuyenmai.getConfirm() == false) 
				{
					msg += this.CreateKhuyenmai(ctkhuyenmai, id, this.tructhuocId, this.ngaygiaodich, tonggiatriKm, khId, sanpham_soluong, sanpham_dongia, db);

					// remove khoi danh sach
					ctkmResual.remove(i);
					i = i - 1;
				}
			}

			if (msg.length() > 0) 
			{
				this.init();
				this.setSpList(this.splist);
				this.setKhId(khId);
				this.setDdkdId(ddkdId);

				this.setAplaikhuyenmai(false);
				xlkm.DBclose();
				this.setMessage("khong the them moi 'donhang_ctkm_trakm' " + msg);
				session.setAttribute("dhBean", this);
				//String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";
				//response.sendRedirect(nextJSP);

				return "6.2";
			}

			String nextJSP = "";
			if (ctkmResual.size() > 0) 
			{
				session.setAttribute("xlkm", xlkm);
				nextJSP = "/TraphacoHYERP/pages/Distributor/KhuyenMai.jsp";
				//response.sendRedirect(nextJSP);
				return "6.3";
			} 
			else 
			{
				xlkm.DBclose();
				
				this.ApChietKhau(this.id);
				
				this.init();
				this.setAplaikhuyenmai(false);
				this.setKhId(khId);
				this.setDdkdId(ddkdId);
				this.setMessage("Số đơn hàng bạn vừa lưu " + this.id);
				
				session.setAttribute("dhBean", this);
				//nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";
				//response.sendRedirect(nextJSP);
				return "6.2";
			}
		}
		
		return "";
	}

	private void resetDATA(String id)
	{
		this.id = id;
		this.khId = "";
		this.ngaygiaodich = "";
		this.nppTen = "";
		this.daidienkd = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";	
		this.VAT = "";
		this.ddkdId = "";
		this.gsbhId = "";
		this.tbhId = "";
		this.chietkhau = "";
		this.tongchietkhau = "";
		this.tongtiensauVAT = "0";
		this.tongtientruocVAT ="0";
		this.ttsauCKKM = 0;
		this.ttCKKM = 0;
		this.ttsauCK = 0;
		this.bgstId = "0";
		this.khoId = "";
		this.msg = "";
		this.khTen = "";
		this.smartId = "";
		this.muctindung="0";
		this.aplaikm = false;
		this.cokm = false;
		this.chuaApkm = false;
		this.dacoDh = false;
		this.daxuatkhoChuachot = false;
		this.aplaitb = false;
		this.splist = new ArrayList<ISanpham>();
		this.spThieuList = new Hashtable<String, Integer>();
		this.ngayks = "";
		this.coTrungBay = "";
		this.IsDonHangLe = "0";
		this.IsSampling = "0";
		this.enterKH = "0";
		this.ghichu="";
		this.chietkhau_ids = "";
		this.chietkhau_giatri = new Hashtable<String, Float>();
		this.chietkhau_vat = "";
		this.tieuchiID = "";

		this.dstXanh = "";
		this.dstHHBG = "";
		this.dstKHAC = "";
		this.dstXanh_Denghi = "";
		this.dstHHBG_Denghi = "";

		this.donhangKhac ="0";
		this.chucuahieu = "";
		this.nvgnId="";
		this.denghitraCKTHANG = "0";
		this.donquatang = "0";

	}

	
	public void Update_GiaTri_DonHang() 
	{
			String query =  
					"update DH set " +
			"		DH.tongtienTRUOCCHIETKHAU = TGT.tongTIEN, " +
			"		DH.tongtienCHIETKHAU = TGT.tongTL, " +
			"		DH.TONGGIATRI = case TGT.thanhtoan when 1 then TGT.tongTIEN - TGT.tongTL else TGT.tongTIEN end, " +
			"		DH.LOAIKHACHHANG = KH.xuatkhau	  " +
			"from DONHANG DH inner join KHACHHANG KH on DH.khachhang_fk = KH.pk_seq " +
			"inner join " +
			"( " +
			"	select d.thanhtoanQUY as thanhtoan, a.pk_seq as donhangID,  " +
			" (isnull( (          "+
			"		select sum( round ( ( ( round ( (soluong * giamua), 0  ) - chietkhau ) * ( 1 + thueVAT / 100 ) ), 0 ) )  as tienBvat       "+   
			"		from donhang_sanpham           "+
			"		where donhang_fk = a.pk_seq  and ( soluong * giamua ) != 0 ) ,0) "+
			"		- isnull( ( select sum(tonggiatri) from donhang_ctkm_trakm where donhangId = a.pk_seq and SPMA is null ), 0 )     " +
			"		- isnull( ( select sum( round(thanhtien, 0) )  from DONHANG_CHIETKHAUBOSUNG where donhang_fk = a.pk_seq )	, 0 )  ) as tongTIEN, " +
			"		isnull( ( select sum( round(thanhtoan, 0) )  from DUYETTRAKHUYENMAI_DONHANG where donhang_fk = a.pk_seq and HIENTHI = '1' )	, 0 ) 	as tongTL					         " +
			"	from donhang a inner join khachhang d on a.khachhang_fk = d.pk_seq         " +
			"	where a.import = '0' and a.trangthai != 2 and a.pk_seq = '" + this.id + "'  " +
			") " +
			"TGT on DH.pk_seq = TGT.donhangID where DH.pk_seq = '" + this.id + "' ";
			
			/*System.out.println("--CAP NHAT TIEN DON HANG: " + query);*/
			db.update(query);
	}

	
	public void setSotaikhoan(String sotaikhoan) {
		
		this.sotaikhoan = sotaikhoan;
	}

	
	public String getSotaikhoan() {
		
		return this.sotaikhoan;
	}
	
	public String getCapduyet() {

		return this.capduyet;
	}


	public void setCapduyet(String capduyet) {

		this.capduyet = capduyet;
	}
	
	public String getIngia() {
		return ingia;
	}

	public void setIngia(String ingia) {
		this.ingia = ingia;
	}
	
	public ResultSet getCongnoRs() {
		
		return this.congnoRs;
	}

	
	public void setCongnoRs(ResultSet congnoRs) {
		
		this.congnoRs = congnoRs;
	}
	
	public void ApChietKhau(String dhId) 
	{
		String query = "update DONHANG_SANPHAM set GIAMUA = dongiaGOC, chietkhau_CSBH = 0, chinhsach_fk = null, chietkhau_KM = 0, schemeCHIETKHAU = '' where donhang_fk = '" + dhId + "' ";
		db.update(query);
		
		//TINH LAI CAC KM CO CHIET KHAU
		query = " select CTKMID, TRAKMID, b.TONGGIATRI, " + 
				" 	( select count(*) from DANGKYKM_TICHLUY_KHACHHANG where KHACHHANG_FK = b.khachhang_fk ) as dangkyTL, " + 
				"	ISNULL( ( select CHIETKHAU from TRAKHUYENMAI where PK_SEQ = a.TRAKMID ), 0) as ptChietkhau,	" +
				"	( select DKKHUYENMAI_FK from CTKM_DKKM where CTKHUYENMAI_FK = a.CTKMID ) as dkkmId,	" +
				"	( select SCHEME from CTKHUYENMAI where pk_seq = a.CTKMID ) as SCHEME	" +
				" from DONHANG_CTKM_TRAKM a inner join DONHANG b on a.donhangId = b.pk_seq " +
				" where donhangId = '" + dhId + "' and a.spMA is NULL and CTKMID in ( select PK_SEQ from CTKHUYENMAI where chiavaodongia = 1 )";
		System.out.println("::::LAY CTKM DANG HUONG: " + query );
		int dangkyTL = 0;
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					//NHỮNG CTKM NÀY PHẢI KHÔNG NẰM TRONG DANH SÁCH ĐĂNG KÝ TÍCH LŨY 
					String ctkmId = rs.getString("CTKMID");
					//String trakmId = rs.getString("TRAKMID");
					//double tongiatri = rs.getDouble("TONGGIATRI");
					double ptChietkhauKM = rs.getDouble("ptChietkhau");
					dangkyTL = rs.getInt("dangkyTL");
					
					System.out.println(":::: CO DK TICH LUY: " + dangkyTL + " -- PT CHIET KHAU KM: " + ptChietkhauKM );
					if( ptChietkhauKM > 0 ) //thỏa điều kiện, chia thẳng vào đơn giá
					{
						//Phân bổ khuyến mại chiết khấu vào đơn giá
						query = "update dh set dh.chietkhau_KM = ( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) * " + ptChietkhauKM + " ) / 100.0) / ( 1 + thueVAT / 100.0 ), " + 
								" 	dh.schemeCHIETKHAU = '" + rs.getString("SCHEME") + "' " +
								"from DONHANG_SANPHAM	dh  " +
								"where donhang_fk = '" + dhId + "' and sanpham_fk in ( select sanpham_fk from DIEUKIENKM_SANPHAM where DIEUKIENKHUYENMAI_FK = '" + rs.getString("dkkmId") + "' )";
						
						System.out.println("::: CAP NHAT CHIET KHAU - KM: " + query );
						db.update(query);
					}
					
					//CHIA THẲNG VÀO GIÁ
					query = "delete DONHANG_CTKM_TRAKM where DONHANGID = '" + dhId + "' and CTKMID = '" + ctkmId + "' ";
					System.out.println("::: XOA CHIET KHAU - KM CHIA GIA: " + query );
					db.update(query);
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
			
		//ÁP TIẾP CHÍNH SÁCH BÁN HÀNG NẾU CÓ
		query = "select khachhang_fk, ngaynhap as NgayDonHang from DONHANG where pk_seq = '" + dhId + "'";
		rs = db.get(query);
		
		String ngaydonhang = "";
		String khId = "";
		try 
		{
			if(rs.next())
			{
				ngaydonhang = rs.getString("NgayDonHang");
				khId = rs.getString("khachhang_fk");
			}
			rs.close();
		} 
		catch (Exception e1) { }
		
		query = "select a.pk_seq, ( select LCH_FK from KHACHHANG where PK_SEQ = '" + khId + "' ) as lch_fk, a.apdungchodaily, a.apdungchohopdong, " +
				"	( select SUM( round( soluong * dongiaGOC * ( 1 + thueVAT / 100.0 ), 0 ) ) from DONHANG_SANPHAM where donhang_fk = '" + dhId + "' and isnull(schemeCHIETKHAU, '') = '' ) as tongtienDONHANG 	" +
				"from CHINHSACHBANHANG a inner join CHINHSACHBANHANG_NPP b on b.chinhsachbanhang_fk = a.pk_seq   " +
				"where a.trangthai = '1' and tungay <= '" + ngaydonhang + "' and '" + ngaydonhang + "' <= denngay and b.npp_fk = '" + nppId + "' " + 
				" and a.pk_seq in ( select chinhsachbanhang_fk from CHINHSACHBANHANG_LOAIKHACHHANG where loaikhachhang_fk in ( select LCH_FK from KHACHHANG where pk_seq = '" + khId + "' ) ) " + 
				" and a.pk_seq in ( select chinhsachbanhang_fk from CHINHSACHBANHANG_KENH where kbh_fk in ( select kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + khId + "' ) ) " + 
				//" and ( a.tichluy_fk is NULL or " + khId + " not in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG where DKKMTICHLUY_FK in ( select pk_seq from DANGKYKM_TICHLUY where NPP_FK = '" + nppId + "' and TIEUCHITL_FK = a.tichluy_fk ) ) ) " + 
				//" and ( " + khId + " not in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG where DKKMTICHLUY_FK in ( select pk_seq from DANGKYKM_TICHLUY where NPP_FK = '" + nppId + "'  ) ) ) " + 
				" and " + khId + " not in ( select khachhang_fk from CHINHSACHBANHANG_KHACHHANG_KHONGAPDUNG where CSBH_FK = 100000 ) " ;
				//" and " + dhId + " not in ( select donhang_fk from DONHANG_SANPHAM where schemeCHIETKHAU in ( select scheme from CTKHUYENMAI where ( chiavaodongia = 1 or loaitrucsbh = 1 ) ) )	";
		
		System.out.println(":::: LAY CK CSBH DANG AP DUNG: " + query);
		rs = db.get(query);
		double chietkhau = 0;
		double ptchietkhau = 0;
		String chinhsachID = "";
		try 
		{
			if(rs.next())
			{
				chinhsachID = rs.getString("pk_seq");
				String lchId = rs.getString("lch_fk") == null ? "" : rs.getString("lch_fk");
				String apdungchodaily = rs.getString("apdungchodaily");
				String apdungchohopdong = rs.getString("apdungchohopdong");
				double tongtienDONHANG = rs.getDouble("tongtienDONHANG");

				if(lchId.equals("100008") && apdungchodaily.equals("0") )
				{
					rs.close();
					return;
				}

				//Tìm mức chiết khấu được hưởng
				query = " select top(1) CHIETKHAU from CHINHSACHBANHANG_TIEUCHI " + 
						" where chinhsachbanhang_fk = '" + chinhsachID + "' and tumuc <= '" + tongtienDONHANG + "' and '" + tongtienDONHANG + "' < denmuc";
				System.out.println("::::: LAY CHIET KHAU THEO CHINH SACH: " + query );
				ResultSet rsCHIETKHAU = db.get(query);
				if(rsCHIETKHAU.next())
				{
					ptchietkhau = rsCHIETKHAU.getDouble("CHIETKHAU");
					rsCHIETKHAU.close();
				}

				if(ptchietkhau > 0)
				{
					chietkhau = ptchietkhau * tongtienDONHANG / 100.0;
				}	
			}
			rs.close();
		} 
		catch (Exception e) {

			e.printStackTrace();
		}
		
		if(chinhsachID.trim().length() > 0 && ptchietkhau > 0 )
		{
			//Nếu khách hàng nằm trong danh sách đăng ký tích lũy, thì những sản phẩm nằm trong tích lũy sẽ không được CSBH
			String condition = "";
			if( dangkyTL > 0 )
			{
				//condition += " and sanpham_fk not in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk in ( select TIEUCHITL_FK from DANGKYKM_TICHLUY where TRANGTHAI = '1' and NPP_FK = '" + nppId + "' ) ) ";
				condition += " and sanpham_fk not in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk in ( 100018, 100021 ) ) ";
			}
			
			//KHÔNG LẤY NHỨNG SẢN NÀO ĐÃ ĐƯỢC KM
			condition += " and dh.sanpham_fk not in ( select sanpham_fk from DONHANG_SANPHAM where donhang_fk = '" + this.id + "' and isnull(schemeCHIETKHAU, '') != '' ) ";
			
			//PHAN BO LAI CHIET KHAU NAY
			query =  "update DH set DH.giamua = DH.dongiaGOC - CK.chietkhauGIAM, " + 
					"			  DH.chietkhau_CSBH = CK.chietkhauGIAM, DH.chinhsach_fk = '" + chinhsachID + "' " + 
					"from DONHANG_SANPHAM DH inner join " + 
					"( " + 
					"	select donhang_fk, sanpham_fk, ( select top(1) htbh_fk from hethongbanhang_kenhbanhang where kbh_fk = b.KBH_FK ) as htbh_fk, " + 
					"			soluong, dongiaGOC, soluong * dongiaGOC as thanhtien, dh.thueVAT, " + 
					"		    ( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) * " + ptchietkhau + " ) / 100.0) / ( 1 + thueVAT / 100.0 )  as chietkhauGIAM  " + 
					"	from DONHANG_SANPHAM dh inner join DONHANG b on dh.donhang_fk = b.PK_SEQ " + 
					"	where donhang_fk = '" + dhId + "' " + condition +
					" 		and dh.sanpham_fk in ( select sanpham_fk from CHINHSACHBANHANG_SANPHAM where chinhsachbanhang_fk = '" + chinhsachID + "' ) " + 
					") " + 
					"CK on DH.donhang_fk = CK.donhang_fk and DH.sanpham_fk = CK.sanpham_fk " + 
					"where DH.sanpham_fk in ( select sanpham_fk from CHINHSACHBANHANG_SANPHAM where chinhsachbanhang_fk = '" + chinhsachID + "' )  " + condition ;
			
			System.out.println("::::: CAP NHAT CHIET KHAU: " + query );
			db.update(query);
		}
		
		
		//CHI GIẢM TIỀN KHUYẾN MẠI VÀ CHIẾT KHẤU CHÍNH SÁCH BÁN HÀNG VÀO ĐƠN HÀNG
		query = "update DH set DH.giamua = DH.dongiaGOC - CK.chietkhauGIAM " + 
				"from DONHANG_SANPHAM DH inner join " + 
				"( " + 
				"	select donhang_fk, sanpham_fk, " + 
				"			soluong, dongiaGOC, soluong * dongiaGOC as thanhtien, a.thueVAT, " + 
				"		    a.chietkhau_KM + a.chietkhau_CSBH  as chietkhauGIAM  " + 
				"	from DONHANG_SANPHAM a inner join DONHANG b on a.donhang_fk = b.PK_SEQ " + 
				"	where donhang_fk = '" + dhId + "' and isnull( chietkhau_KM, 0 ) > 0 " + 
				") " + 
				"CK on DH.donhang_fk = CK.donhang_fk and DH.sanpham_fk = CK.sanpham_fk " + 
				"where DH.donhang_fk = '" + dhId + "'  " ;
		
		System.out.println("::::: CAP NHAT CHIET KHAU KM - CSBH: " + query );
		db.update(query);
	}
	
	public String getNpp_duocchon_id() {
		
		return this.npp_duocchon_id;
	}

	
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		
		this.npp_duocchon_id = npp_duocchon_id;
	}

	
	public String getTdv_dangnhap_id() {
		
		return this.tdv_dangnhap_id;
	}

	
	public void setTdv_dangnhap_id(String tdv_dangnhap_id) {
		
		this.tdv_dangnhap_id = tdv_dangnhap_id;
	}

	public float getPTChietkhauBHKM() {

		return this.ptChietkhauBHKM;
	}

	public void setPTChietkhauBHKM(float ptCK) {
		
		this.ptChietkhauBHKM = ptCK;
	}

	
	public ResultSet getTructhuocRs() {
		
		return this.tructhuocRs;
	}

	
	public void setTructhuocRs(ResultSet tructhuocRs) {
		
		this.tructhuocRs = tructhuocRs;
	}

	
	public String getTructhuocId() {
		
		return this.tructhuocId;
	}

	
	public void setTructhuocId(String tructhuocId) {
		
		this.tructhuocId = tructhuocId;
	}
	
	public String getKbhId() {
		
		return this.kbhId;
	}

	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}
	
	Object loainhanvien;
	Object doituongId;
	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
	}
	
	public void checkKSKD() {
		
		Utility util = new Utility();
		//CHECK DA KHOA SO KINH DOANH CHUA
		int checkKS_KINHDOANH = util.CheckKhoaSoKinhDoanh(db, nppId, this.getThoiDiemHienTai(), "", "", "");
		System.out.println(":: KET QUA CHECK KSKD: " + checkKS_KINHDOANH );
		if(checkKS_KINHDOANH == 1 )
		{
			String[] hientai = this.getDateTime().split("-");
			int thangtiep = Integer.parseInt(hientai[1]) + 1;
			if( thangtiep > 12 )
				thangtiep = 1;
			
			this.msg = "Doanh số đơn hàng này được chuyển sales qua tháng " + ( thangtiep );
		}	
	}
	
	

}
