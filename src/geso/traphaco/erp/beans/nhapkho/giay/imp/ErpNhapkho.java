package geso.traphaco.erp.beans.nhapkho.giay.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.nhanhang.ISpDetail;
import geso.traphaco.erp.beans.nhanhang.imp.SpDetail;
import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapkho;
import geso.traphaco.erp.beans.nhapkho.giay.IKhu_Vitri;
import geso.traphaco.erp.beans.nhapkho.giay.ISanpham;
import geso.traphaco.erp.beans.nhapkho.giay.ISanphamCon;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.Kho_Lib;
import geso.traphaco.erp.util.Library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

public class ErpNhapkho implements IErpNhapkho
{
	String congtyId;
	String userId;
	String id;
	String ngaynhapkho;
	String ngaychotNV;
	
	String sophieunhan;
	String sodontrahang;
	String solenhsanxuat;

	String khoId;
	ResultSet khoRs;
	String ndnId;
	ResultSet ndnRs;
	String lhhId;
	ResultSet lhhRs;
	String vtlkId;
	ResultSet vtlkRs;
	String trangthai;
	String Diem;
	boolean quanlybean;
	boolean quanlylo;
	ResultSet rsLenhsanxuat,rsCongdoan;
	String CongDoanId;
	String DvkdId;
	String isQLKV;
	String KHonhanTP;
	
	List<ISanpham> spList;

	List<IKhu_Vitri> khuList;
	List<IKhu_Vitri> vitriList;

	String msg;

	// pdf
	String nguoinhanhang;
	String diachinhan;
	String nhaptaikho;
	String ghichu;
	String Khongkiemdinh;
	String LoaisanphamId;
	String IsLsxCongNghe;
	String Donvitinh;
	String BtpId;
	ResultSet RsBTP;
	private String nguoiTao;
	dbutils db;
	private Utility util;
	private Utility_Kho  util_kho=new Utility_Kho();
	
	
	NumberFormat formatter = new DecimalFormat("#######.###");
	
	
	public ErpNhapkho()
	{
		this.userId = "";
		this.DvkdId="";
		this.id = "";
		this.ngaynhapkho = "";
		this.sophieunhan = "";
		this.khoId = "";
		this.ndnId = "";
		this.lhhId = "0";
		this.vtlkId = "";
		this.trangthai = "";
		this.msg = "";
		this.Diem = "";
		this.Khongkiemdinh="";
		this.quanlybean = true;
		this.quanlylo = true;
		this.spList = new ArrayList<ISanpham>();
		this.khuList = new ArrayList<IKhu_Vitri>();
		this.vitriList = new ArrayList<IKhu_Vitri>();

		this.nguoinhanhang = "";
		this.diachinhan = "";
		this.nhaptaikho = "";
		this.ghichu = "";
		this.sodontrahang = "";
		this.solenhsanxuat = "";
		this.ngaychotNV = "";
		
		this.Khongkiemdinh="";
		this.LoaisanphamId="";
		this.IsLsxCongNghe="";
		this.Donvitinh="";
		
		this.nguoiTao = "";
		
		this.util=new Utility();
		
		this.db = new dbutils();
	}

	public ErpNhapkho(String id)
	{
		this.userId = "";
		this.id = id;
		this.ngaynhapkho = "";
		this.sophieunhan = "";
		this.khoId = "";
		this.ndnId = "";
		this.lhhId = "0";
		this.vtlkId = "";
		this.trangthai = "";
		this.msg = "";
		this.quanlybean = true;
		this.quanlylo = true;
		this.spList = new ArrayList<ISanpham>();
		this.khuList = new ArrayList<IKhu_Vitri>();
		this.vitriList = new ArrayList<IKhu_Vitri>();

		this.nguoinhanhang = "";
		this.diachinhan = "";
		this.nhaptaikho = "";
		this.ghichu = "";
		this.sodontrahang = "";
		this.solenhsanxuat = "";
		this.ngaychotNV = "";
		this.Khongkiemdinh="";
		this.LoaisanphamId="";
		this.IsLsxCongNghe="";
		this.Donvitinh="";
		
		this.nguoiTao = "";
		this.util=new Utility();
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

	public String getNgaynhapkho()
	{
		return this.ngaynhapkho;
	}

	public void setNgaynhapkho(String ngaynhapkho)
	{
		this.ngaynhapkho = ngaynhapkho;
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

	public String getSoPnh()
	{
		return this.sophieunhan;
	}

	public void setSoPnh(String soPnh)
	{
		this.sophieunhan = soPnh;
	}

	public void setmsg(String msg)
	{
		this.msg = msg;
	}

	public String getmsg()
	{
		return this.msg;
	}

	public void setKhoId(String khoId)
	{
		this.khoId = khoId;
	}

	public String getKhoId()
	{
		return this.khoId;
	}

	public ResultSet getKhoList()
	{
		return this.khoRs;
	}

	public void setKhoList(ResultSet khoList)
	{
		this.khoRs = khoList;
	}

	public void setNdnId(String ndnId)
	{
		this.ndnId = ndnId;
	}

	public String getNdnId()
	{
		return this.ndnId;
	}

	public ResultSet getNdnList()
	{
		return this.ndnRs;
	}

	public void setNdnList(ResultSet ndnList)
	{
		this.ndnRs = ndnList;
	}

	public void setLhhId(String lhhId)
	{
		this.lhhId = lhhId;
	}

	public String getLhhId()
	{
		return this.lhhId;
	}

	public ResultSet getLhhList()
	{
		return this.lhhRs;
	}

	public void setLhhList(ResultSet lhhList)
	{
		this.lhhRs = lhhList;
	}

	public void setVtkId(String lhhId)
	{
		this.lhhId = lhhId;
	}

	public String getVtkId()
	{
		return this.vtlkId;
	}

	public ResultSet getVtkList()
	{
		return this.vtlkRs;
	}

	public void setVtkList(ResultSet vtkList)
	{
		this.vtlkRs = vtkList;
	}

	public List<ISanpham> getSpList()
	{
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;
	}

	public List<IKhu_Vitri> getKhuList()
	{
		return this.khuList;
	}

	public void setKhuList(List<IKhu_Vitri> khuList)
	{
		this.khuList = khuList;
	}

	public List<IKhu_Vitri> getVitriList()
	{
		return this.vitriList;
	}

	public void setVitriList(List<IKhu_Vitri> vitriList)
	{
		this.vitriList = vitriList;
	}

	public boolean getQuanLyBean()
	{
		return this.quanlybean;
	}

	public void setQuanLyBean(boolean quanlybean)
	{
		this.quanlybean = quanlybean;
	}

	public void createRs()
	{
		String query="select pk_Seq  from erp_lenhsanxuat_giay where pk_seq='"+this.solenhsanxuat+"'";
		this.rsLenhsanxuat=this.db.get(query);
		query="select pk_Seq,diengiai from erp_congdoansanxuat_Giay where pk_seq='"+this.CongDoanId+"'";
		this.rsCongdoan=this.db.get(query);
		this.ndnRs = db.get("select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP where trangthai = '1' and upper(substring(ma, 0, 3)) = upper('NK') and pk_seq != '100005' ");
		this.khoRs = db.get("select PK_SEQ, TEN, DIACHI, QUANLYBIN from ERP_KHOTT where trangthai = '1' and congty_fk = '" + this.congtyId + "' and pk_seq='"+this.khoId+"'");
		this.lhhRs = db.get("select PK_SEQ, TEN from ERP_LOAISANPHAM");
	}

	public void init()
	{
		String query =  " select isnull(ISLSXCONGNGHE,'0') as ISLSXCONGNGHE, a.PK_SEQ as nhId, a.KHONHAP, a.SOPHIEUNHAPHANG, a.SODONTRAHANG, a.SoLenhSanXuat, \n" +
					    " a.NGAYNHAPKHO, b.pk_seq as ndnId, b.TEN as ndnTen, a.TRANGTHAI, c.QUANLYBIN,ISNULL(a.GhiChu,'')as GhiChu, \n" +
						" k.Prefix+cast(a.SOLENHSANXUAT as varchar(20)) as SOCHUNGTU,a.CongDoan_FK as CongDoanId, \n" +
						" a.NGAYNHAPKHO, isnull(a.NGAYCHOT, '') as NgayChot, a.NOIDUNGNHAP, b.TEN as ndnTen, a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO  , \n" +
						"  isnull(a.khongkiemdinh,0) as khongkiemdinh   \n" +
						" , nv.TEN AS NGUOITAO \n" + 
						" from ERP_NHAPKHO a inner join ERP_NOIDUNGNHAP b on a.NOIDUNGNHAP = b.PK_SEQ \n" +
						" inner join ERP_KHOTT c on a.KHONHAP = c.PK_SEQ \n" +
						" inner join erp_lenhsanxuat_Giay k on a.SOLENHSANXUAT = k.pk_seq \n" +
						" left join NHANVIEN nv on nv.PK_SEQ = a.NGUOITAO \n" +
						" where a.PK_SEQ = '" + this.id + "' \n";
		
		//System.out.println("Init " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
		try 
		{
			while(rs.next())
			{
				this.nguoiTao = rs.getString("NGUOITAO");
				this.IsLsxCongNghe=rs.getString("ISLSXCONGNGHE");
				
				this.ngaynhapkho = rs.getString("NGAYNHAPKHO");
				this.Khongkiemdinh=rs.getString("khongkiemdinh");
				this.ngaychotNV = rs.getString("NgayChot");
				this.ndnId = rs.getString("ndnId");
				this.solenhsanxuat = rs.getString("SoLenhSanXuat");
				this.CongDoanId=rs.getString("CongDoanId");
				this.khoId = rs.getString("KHONHAP");
				this.trangthai = rs.getString("trangthai");
				this.ghichu=rs.getString("GhiChu");
				
				if(rs.getString("QUANLYBIN").equals("0"))
					this.quanlybean = false;
				else
					this.quanlybean = true;
				
				
			}
			rs.close();
			
			//LAY DVTINH 
			query=  " select DVDL.DIENGIAI as dvt from ERP_NHAPKHO nk   "+
					" inner join ERP_LENHSANXUAT_SANPHAM lsx_sp on lsx_sp.LENHSANXUAT_FK=nk.SOLENHSANXUAT "+
					" inner join ERP_DANHMUCVATTU dm on dm.PK_SEQ=lsx_sp.DanhMucVT_FK "+
					" inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ=dm.DVDL_FK "+
					" where nk.PK_SEQ= "+this.id;
			//System.out.println("LAY DVT NHAPKHO: "+query);
			rs=db.get(query);
			 if (rs != null)
			 {
				if (rs.next())
				{
					this.Donvitinh=rs.getString("dvt");
				}rs.close();
			 }
				
			query=	" SELECT BTP.MA + ' - '+BTP.TEN as ten,BTP.PK_SEQ FROM ERP_SANPHAM SP "+
					" INNER JOIN ERP_SANPHAM_BTP SPBTP ON  SP.PK_SEQ=SPBTP.SP_FK "+
					" INNER JOIN ERP_SANPHAM BTP ON BTP.PK_SEQ=SPBTP.BTP_FK "+
					" WHERE SP.PK_SEQ in (select sanpham_fk from ERP_LENHSANXUAT_SANPHAM where lenhsanxuat_fk= "+this.solenhsanxuat+") " ;
			System.out.println("---- 13. RsBTP: "+query);
			
			try{
				this.RsBTP=db.get(query);
			} 
			catch (Exception er) {
				er.printStackTrace();
			}
			
		} 
		catch (SQLException e) {}
		}
		createRs();
		this.initSanPham();
	}

	public void initPdf()
	{
		String query =    " select a.PK_SEQ as nhId, a.SOPHIEUNHAPHANG, a.NGAYNHAPKHO, b.Ma + '; ' + b.Ten as ndnTen, nm.tennhamay as nhamay,"
						+ " a.TRANGTHAI, c.ten as khoNhap, (k.PREFIX + cast(a.SOLENHSANXUAT as varchar(20))) as SOCHUNGTU, a.NGAYTAO as ngaychungtu "
						+ " from ERP_NHAPKHO a "
						+ " inner join ERP_LENHSANXUAT_GIAY k on a.solenhsanxuat = k.pk_seq "
						+ " inner join ERP_NOIDUNGNHAP b on a.NOIDUNGNHAP = b.PK_SEQ " 
						+ " inner join ERP_KHOTT c on a.KHONHAP = c.PK_SEQ "
						+ " inner join Erp_KichBanSanXuat_Giay kbsx on k.KICHBANSANXUAT_FK = kbsx.PK_SEQ "
						+ " inner join ERP_NHAMAY nm on kbsx.NhaMay_FK = nm.pk_seq "
						+ " where a.pk_seq = '" + this.id + "'";
		
		System.out.println("[ErpNhapkho.initPdf] query = " + query);

		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.ngaynhapkho = rs.getString("NGAYNHAPKHO");
					this.sophieunhan = "120" + rs.getString("SOPHIEUNHAPHANG");
					this.solenhsanxuat = rs.getString("SOCHUNGTU");
					this.ndnId = rs.getString("ndnTen");
					this.khoId = rs.getString("khoNhap");
					this.nhaptaikho = rs.getString("nhamay");
					this.ngaychotNV = rs.getString("ngaychungtu"); //Ngày chứng từ
					this.trangthai = rs.getString("trangthai");
					//this.Diem = rs.getString("Diem");
				}
				rs.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}

		this.initSanPhamPdf();
	}

	private void initSanPham()
	{
		NumberFormat formater = new DecimalFormat("#,###,###.#####");
		
		String query = " select b.loaisanpham_fk ,isnull(SOLUONGLAYMAU_TRUOCQD,0) as SOLUONGLAYMAU_TRUOCQD  ,SOLUONGTRUOCQUYDOI,DVDL.DIENGIAI AS DVT,a.DVDL_FK ,a.pk_seq, a.sanpham_fk, b.ma as spMa, b.TEN   as ten , a.soluongnhan, a.soluongnhap, a.solo, a.ngayhethan,a.NgaySanXuat,a.NgayNhapKho, a.dongia, a.dongiaViet, a.tiente_fk " +
					   " from ERP_NHAPKHO_SANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +
					   " INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=A.DVDL_FK "+
					   " where a.SONHAPKHO_FK = '" + this.id + "'";
		
		//System.out.println("__Khoi tao nhap kho: " + query);
		System.out.println("---- 1313. rsSp: " + query);
		ResultSet rsSp = null;
		try{
			rsSp = db.get(query);
		}catch (Exception err) {
			err.printStackTrace();
		}

		if (rsSp != null)
		{
			try
			{
				ISanpham sanpham;
				String[] param = new String[7];
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next())
				{
					if(rsSp.getString("loaisanpham_fk").equals("100041") || rsSp.getString("loaisanpham_fk").equals("100042")){
						this.LoaisanphamId="0";
					}else if(rsSp.getString("loaisanpham_fk").equals("100044")||rsSp.getString("loaisanpham_fk").equals("100045")){
						this.LoaisanphamId="1";
					}
				
					
					String spid=rsSp.getString("sanpham_fk");
					param[0] = spid;
					param[1] = rsSp.getString("spMa");
					param[2] = rsSp.getString("ten");
					param[3] = rsSp.getString("solo");
					param[4] = formater.format(rsSp.getDouble("soluongnhan"));
					param[5] = formater.format(rsSp.getDouble("SOLUONGTRUOCQUYDOI"));
					String dongia = rsSp.getString("dongia");
					
					String pk_seq = rsSp.getString("pk_seq");
					String comand = " select SOLUONG, KHU, cast(KHU as nvarchar(10)) + ' - ' + cast(vitri as nvarchar(10)) as vitri from ERP_NHAPKHO_SP_CHITIET where NHAPKHO_SANPHAM_FK = '"
							+ pk_seq + "'";
					ResultSet spConRs = db.get(comand);
					List<ISanphamCon> spConList = new ArrayList<ISanphamCon>();
					ISanphamCon spCon = null;
					if (spConRs != null)
					{
						while (spConRs.next())
						{
							spCon = new SanphamCon(param[0], spConRs.getString("SOLUONG"), spConRs.getString("KHU"),
									spConRs.getString("vitri"));
							spConList.add(spCon);
						}
						spConRs.close();
					}
					
					sanpham = new Sanpham(param);
					
					sanpham.setNgayhethan(rsSp.getString("ngayhethan"));
					sanpham.setDongia(dongia);
					sanpham.setDongiaViet(rsSp.getString("dongiaViet"));
					sanpham.setTiente(rsSp.getString("tiente_fk"));
					sanpham.setNgayNhapKho(rsSp.getString("NgayNhapKho"));
					sanpham.setNgaySanXuat(rsSp.getString("NgaySanXuat"));
					sanpham.setDVT(rsSp.getString("dvt"));
					sanpham.setSoluonglaymau(rsSp.getString("SOLUONGLAYMAU_TRUOCQD"));
					sanpham.setSpConList(spConList);
					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	

 

	protected boolean xuLyTenList(List<String> _tenList ,String tenSp,  int sokytu1sp) 
	{
		String[] words = new String[0];
		String _ten = "", _ten2 = "";
		
			words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong ten san pham
			_ten = "";
			_ten2 = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xử lý khi 1 từ > số ký tự 1 dòng
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // Thêm dòng cũ
					_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0) {
				_tenList.add(_ten);
			}
			
			return true;
	}
	
	private void initSanPhamPdf()
	{
		

		try 
		{		
		List<ISanpham> spList = new ArrayList<ISanpham>();
		String query = "";
		
		 
		  query=" SELECT distinct isnull(b.chungloai_fk,0) as chungloai_fk ,b.dvkd_fk "+
				" FROM ERP_NHAPKHO_SANPHAM A "+
				" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ "+ 
				" WHERE SONHAPKHO_FK = "+this.id;
		  
		  ResultSet rs=db.get(query);
		  String chungloai="";
		  String dvkd_fk="";
		  if (rs != null)
		  {
			  if(rs.next()){
				  chungloai=rs.getString("chungloai_fk");
				  dvkd_fk=rs.getString("dvkd_fk");
				  this.DvkdId=dvkd_fk;
			  }
			  rs.close();
		  }		  
		 
		
		  query =     " SELECT isnull(b.loaisanpham_fk,0) as loaisanpham_fk , A.SANPHAM_FK AS SPID, B.MA AS SPMA,isnull(b.mauin,'') as mauin , B.TEN as SPTEN, B.QUYCACH,ISNULL(B.DAI,0) AS DAI, "+  
					  " ISNULL(B.DVDL_DAI,'') AS DVDL_DAI,ISNULL(B.DAULON,0) AS DAULON  ,ISNULL(B.DVDL_DAULON,0) AS DVDL_DAULON,   "+
					  " ISNULL(B.DAUNHO,0) AS DAUNHO ,ISNULL(B.DVDL_DAUNHO,0) AS DVDL_DAUNHO,ISNULL(B.DUONGKINHTRONG,0) AS DUONGKINHTRONG "+  
					  " ,ISNULL(B.DVDL_DKTRONG,0) AS DVDL_DKTRONG  "+
					  " ,ISNULL(B.DaiDAY,0) AS DaiDAY ,ISNULL(B.DVDL_daiDAY,0) AS DVDL_DaiDAY , ISNULL(B.rong,0) AS rong ,ISNULL(B.DVDL_rong,0) AS DVDL_rong "+ 
					  " ,ISNULL(B.DODAY,0) AS DODAY ,ISNULL(B.DVDL_DODAY,0) AS DVDL_DODAY,ISNULL(B.dinhluong,0) AS dinhluong    "+
					  " ,ISNULL(B.DVDL_dinhluong,0) AS DVDL_dinhluong,isnull(B.MAU,N'Không màu') as mausac, CASE WHEN ISNULL(QC.SOLUONG1,'0') = '0' THEN "+  
					  " A.SOLUONGNHAP ELSE A.SOLUONGNHAP *  ISNULL(QC.SOLUONG2,'0') /QC.SOLUONG1 END AS TRONGLUONG,    "+
					  " DVDL.DONVI AS DVT, A.SOLUONGNHAP AS TOTALLUONG, A.SOLUONGNHAP AS SOLUONGTHUCXUAT, A.SOLUONGNHAP as SOLUONG ,  '' AS GHICHU "+  
					  " FROM ERP_NHAPKHO_SANPHAM A   "+
					  " INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ "+  
					  " INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=B.DVDL_FK  "+
					  " LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=B.PK_SEQ AND B.DVDL_FK=QC.DVDL1_FK AND QC.DVDL2_FK=100003 "+  
					  " WHERE A.SOLUONGNHAP  >0  AND  SONHAPKHO_FK ="+this.id +
					  " ORDER BY B.MA,ISNULL(B.DAI,0),ISNULL(B.DAULON,0),ISNULL(B.DAUNHO,0),ISNULL(B.DUONGKINHTRONG,0),ISNULL(B.DODAY,0) ";
 
		  System.out.println("[ErpPhieuxuatkho.initSanPhamPdf] query khoi tao san pham = " + query);
		
		  rs = db.get(query);
		
	 			if(chungloai.trim().equals("100040")){
	 				// là loại ống Cone
	 					String ma_and_qc_bk="";
	 					while(rs.next())
						{
	 					  
	 						String qc="";
	 						if(rs.getDouble("daulon") >0){
	 							qc= (qc.length() >0 ? qc+ " x ":"") + rs.getString("daulon")+ rs.getString("dvdl_daulon");
	 						}
	 						if(rs.getDouble("DAI") >0){
	 							qc= (qc.length() >0? qc+ " x ":"") +rs.getString("DAI")+ rs.getString("dvdl_DAI");
	 						}
	 						if(rs.getDouble("daunho") >0){
	 							qc= (qc.length() >0? qc+ " x ":"") + rs.getString("daunho")+ rs.getString("dvdl_daunho");
	 						}
	 						if(rs.getDouble("DUONGKINHTRONG") >0){
	 							qc= (qc.length() >0? qc+ " x ":"") + rs.getString("DUONGKINHTRONG")+ rs.getString("DVDL_DKTRONG");
	 						}
	 						if(rs.getDouble("DODAY") >0){
	 							qc= (qc.length() >0? qc+ " x ":"") + rs.getString("DODAY")+ rs.getString("DVDL_DODAY");
	 						}
	 					 
	 						if(!ma_and_qc_bk.equals(qc)) {
	 							List<String> _tenList = new ArrayList<String>();
	 							xuLyTenList(_tenList,this.ghichu,25);
	 							//System.out.println("Ghi Chú : "+this.ghichu);
	 							int max_len_diengiai=_tenList.size();
	 							//System.out.println("max_len_diengiai : "+max_len_diengiai);
	 							ma_and_qc_bk=qc;
	 							ISanpham sanpham = new Sanpham();
	 							sanpham.setDiengiai(rs.getString("spTen"));
	 							sanpham.setDVT(rs.getString("DVT"));
	 							sanpham.setTrongluong("0");
	 							sanpham.setSoluongnhapkho("0");
	 							
	 							if(max_len_diengiai >0){
	 								sanpham.setDiengiai(_tenList.get(0));
	 							}
	 							
	 							spList.add(sanpham);
	 							sanpham = new Sanpham();
	 							sanpham.setDiengiai(qc);
	 							sanpham.setTrongluong("0");
	 							sanpham.setSoluongnhapkho("0");
	 							if(max_len_diengiai >1){
	 								sanpham.setDiengiai(_tenList.get(1));
	 							
	 							}
	 							spList.add(sanpham);
	 							
	 							int beginIndex =2;
	 							while(_tenList.size() > beginIndex) {
	 									sanpham = new Sanpham();
		 								sanpham.setDiengiai(_tenList.get(beginIndex).trim());
		 								sanpham.setTrongluong("0");
			 							sanpham.setSoluongnhapkho("0");
		 								beginIndex++;
		 								spList.add(sanpham);
	 							}
	 							
	 						} 
	 							ISanpham sanpham = new Sanpham();
	 							List<String> _tenList = new ArrayList<String>();
	 							String mauin=rs.getString("mauin").trim();
	 							if(mauin.trim().equals("Không")){
	 								mauin="";
	 							}
	 							
	 							String chuoi=mauin+ " "+ rs.getString("mausac");
	 							 
	 							/*int chieudaighichu= rs.getString("ghichu").trim().length();
	 							int chieudaimausac= rs.getString("mausac").trim().length();
	 							int chieudaikhoangtrang=40-chieudaighichu-chieudaimausac;
	 							System.out.println(chieudaikhoangtrang);
	 							String khoangtrang="";
	 							if(chieudaikhoangtrang >0){
	 								for(int k=0;k<chieudaikhoangtrang;k++ )
	 								khoangtrang= khoangtrang+"-";
	 							}
	 							chuoi=rs.getString("mausac")+ khoangtrang+ rs.getString("ghichu");*/
	 							
	 							xuLyTenList(_tenList,chuoi,40);
	 							
	 							sanpham.setSoluongnhapkho(rs.getString("soluong"));
	 							System.out.println("Só lượng ............. : "+rs.getString("soluong"));
	 							sanpham.setTrongluong("0");
	 							if(_tenList.size() >0){
	 								sanpham.setDiengiai(_tenList.get(0));
	 							}
	 							spList.add(sanpham);
	 							//sanpham.set(rs.getString("ghichu").trim());
	 							
	 							int beginIndex =1;
	 							while(_tenList.size() > beginIndex) {
	 									sanpham = new Sanpham();
		 								sanpham.setDiengiai(_tenList.get(beginIndex).trim());
		 								sanpham.setTrongluong("0");
			 							sanpham.setSoluongnhapkho("0");
		 								beginIndex++;
		 								spList.add(sanpham);
	 							}
	 							
	 							
	 						 
	 						
						}
	 					rs.close();
	 			} else if( dvkd_fk.equals("100004") && !chungloai.trim().equals("100040") ){ 

	 				// là loại ống Cone
	 					String tensp_bk="";
	 					while(rs.next())
						{
	 						 
	 						String qc="";
	 						
	 						if(rs.getDouble("DUONGKINHTRONG") >0){
	 							qc= (qc.length() >0? qc+ " x ":"") + rs.getString("DUONGKINHTRONG")+ rs.getString("DVDL_DKTRONG");
	 						}
	 						
	 						if(rs.getDouble("daulon") >0){
	 							qc= (qc.length() >0 ? qc+ " x ":"") + rs.getString("daulon")+ rs.getString("dvdl_daulon");
	 						}
	 						if(rs.getDouble("DAI") >0){
	 							qc= (qc.length() >0? qc+ " x ":"") +rs.getString("DAI")+ rs.getString("dvdl_DAI");
	 						}
	 						if(rs.getDouble("daunho") >0){
	 							qc= (qc.length() >0? qc+ " x ":"") + rs.getString("daunho")+ rs.getString("dvdl_daunho");
	 						}
	 						
	 						if(rs.getDouble("DODAY") >0){
	 							qc= (qc.length() >0? qc+ " x ":"") + rs.getString("DODAY")+ rs.getString("DVDL_DODAY");
	 						}
	 						if(!rs.getString("loaisanpham_fk").equals("100005")){
	 							if(rs.getDouble("rong") >0){
		 							qc= (qc.length() >0? qc+ " x ":"") + rs.getString("rong")+ rs.getString("DVDL_rong");
		 						}
		 						if(rs.getDouble("dinhluong") >0){
		 							qc= (qc.length() >0? qc+ " x ":"") + rs.getString("dinhluong")+ rs.getString("DVDL_dinhluong");
		 						}
	 						}
	 						
	 						if(!chungloai.equals("100031")){
	 							qc= (qc.length() >0 ? qc+ " x ":"") + rs.getString("mausac") ;
	 						}
	 						
	 						 
	 						String tensp=rs.getString("spTen");
	 						if(!tensp_bk.equals(tensp)) {
	 							List<String> _tenList = new ArrayList<String>();
	 							xuLyTenList(_tenList,this.ghichu,25);
	 							//System.out.println("Ghi Chú : "+this.ghichu);
	 							
	 							int max_len_diengiai=_tenList.size();
	 							//System.out.println("max_len_diengiai : "+max_len_diengiai);
	 							tensp_bk=tensp;
	 							
	 							ISanpham sanpham = new Sanpham();
	 							sanpham.setDiengiai(rs.getString("spTen"));
	 							sanpham.setDVT("");
	 							sanpham.setTrongluong("0");
	 							sanpham.setSoluongnhapkho("0");
	 							
	 							if(max_len_diengiai >0){
	 								sanpham.setDiengiai(_tenList.get(0));
	 							}
	 							spList.add(sanpham);
	 							int beginIndex =1;
	 							while(_tenList.size() > beginIndex) {
	 									sanpham = new Sanpham();
		 								sanpham.setDiengiai(_tenList.get(beginIndex).trim());
		 								sanpham.setTrongluong("0");
			 							sanpham.setSoluongnhapkho("0");
		 								beginIndex++;
		 								spList.add(sanpham);
	 							}
	 							
	 						} 
	 							ISanpham sanpham = new Sanpham();
	 							List<String> _tenList = new ArrayList<String>();
	 							String chuoi=qc;
	 							xuLyTenList(_tenList,chuoi,40);
	 							sanpham.setDVT(rs.getString("dvt"));
	 							sanpham.setSoluongnhapkho(rs.getString("soluong"));
	 							sanpham.setTrongluong(rs.getString("trongluong"));
	 							System.out.println("Só trongluong ............. : "+rs.getString("trongluong"));
	 							sanpham.setDiengiai(rs.getString("ghichu"));
	 							if(_tenList.size() >0){
	 								sanpham.setDiengiai(_tenList.get(0));
	 							}
	 							
	 							System.out.println("sanpham ............. : "+sanpham.getDiengiai());
	 							spList.add(sanpham);
	 							
	 							
	 							int beginIndex =1;
	 							while(_tenList.size() > beginIndex) {
	 									sanpham = new Sanpham();
		 								sanpham.setDiengiai(_tenList.get(beginIndex).trim());
		 								sanpham.setTrongluong("0");
			 							sanpham.setSoluongnhapkho("0");
		 								beginIndex++;
		 								spList.add(sanpham);
	 							}
	 							
	 						 
	 						
						}
	 					rs.close();
	 			
	 				
				}
	 			
	 			else if(dvkd_fk.equals("100005") ) 
	 			{
	 				// san pham moi 
					while(rs.next())
					{
						ISanpham sanpham = new Sanpham();
						double soluongxuat = 0; try { soluongxuat = rs.getFloat("soluongthucxuat"); } catch (Exception e) { }
						
						
						String qc="";
 						if(rs.getDouble("rong") >0){
 							qc= (qc.length() >0 ? qc+ " x ":"") + rs.getString("rong")+ rs.getString("dvdl_rong");
 						}
 						if(rs.getDouble("dai") >0){
 							qc= (qc.length() >0 ? qc+ " x ":"") + rs.getString("dai")+ rs.getString("dvdl_dai");
 						}
 						if(rs.getDouble("dinhluong") >0){
 							qc= (qc.length() >0 ? qc+ " x ":"") + rs.getString("dinhluong")+ rs.getString("dvdl_dinhluong");
 						}
 						if(rs.getDouble("duongkinhtrong") >0){
 							qc= (qc.length() >0 ? qc+ " x ":"") + rs.getString("duongkinhtrong")+ rs.getString("DVDL_DKTRONG");
 						}
 						if(rs.getDouble("DaiDAY") >0){
 							qc= (qc.length() >0 ? qc+ " x ":"") + rs.getString("DaiDAY")+ rs.getString("DVDL_DaiDAY");
 						}
 						if(rs.getString("mausac").length() >0){
 							qc= (qc.length() >0 ? qc+ " x ":"") + rs.getString("mausac") ;
 						}
 						
						
						sanpham.setId(rs.getString("spId"));
						sanpham.setMa(rs.getString("spMa"));
						sanpham.setDiengiai(rs.getString("spTen")+" "+ qc);
						sanpham.setDVT(rs.getString("DVT"));
						//sanpham.setDiengiai()(rs.getString("ghichu"));
						sanpham.setSoluongnhapkho(Double.toString(soluongxuat));
						//sanpham.setQuycach(Float.toString(quycach));
						//sanpham.setThung(Float.toString(thung));
						//sanpham.setLe(Float.toString(le));
						sanpham.setTrongluong(rs.getString("TRONGLUONG"));
						System.out.println("Srtong luong :"+rs.getString("TRONGLUONG"));
						spList.add(sanpham);
					}
				
					rs.close();
				
	 			}else{
						while(rs.next())
						{
							ISanpham sanpham = new Sanpham();
							double soluongxuat = 0; try { soluongxuat = rs.getFloat("soluongthucxuat"); } catch (Exception e) { }
							//	double trongluongxuat =  tongluong == 0 ? 0 : trongluong * soluongxuat / tongluong;
							/*float quycach = rs.getFloat("quycach");
							float soluong2 = rs.getFloat("SOLUONG2");
							
							float thung = soluong * soluong2 / quycach;
							float le = (soluong * soluong2) % quycach;*/
							
							sanpham.setId(rs.getString("spId"));
							sanpham.setMa(rs.getString("spMa"));
							sanpham.setDiengiai(rs.getString("spTen")+" "+ rs.getString("QUYCACH"));
							sanpham.setDVT(rs.getString("DVT"));
							//sanpham.setGhiChu(rs.getString("ghichu"));
							sanpham.setSoluongnhapkho(Double.toString(soluongxuat));
							//sanpham.setQuycach(Float.toString(quycach));
							//sanpham.setThung(Float.toString(thung));
							//sanpham.setLe(Float.toString(le));
							sanpham.setTrongluong(rs.getString("TRONGLUONG"));
							System.out.println("Srtong luong :"+rs.getString("TRONGLUONG"));
							spList.add(sanpham);
						}
					
						rs.close();
					}
				this.spList = spList;
				
				
			} 
			catch (Exception e) {
				e.printStackTrace();
			 
			}
			
			
		 
	}
	

	public boolean createNhapKho()
	{
		if (this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào để nhập kho, vui lòng kiểm tra lại";
			return false;
		}
		
		boolean flag = false;
		for(int i = 0; i < this.spList.size(); i++)
		{
			ISanpham sp = this.spList.get(i);
			if(sp.getSoluongnhapkho().length() > 0)
			{
				if(Float.parseFloat(sp.getSoluongnhapkho()) > 0)
				{
					flag = true;
					break;
				}
			}
		}
		if(flag == false)
		{
			this.msg = "Không có sản phẩm nào được nhập số lượng để nhập kho, vui lòng kiểm tra lại";
			return false;
		}

		try
		{
			String ngaytao = getDateTime();

			db.getConnection().setAutoCommit(false);

			String query = "";
			String sonhanhang = "";
			
			if(this.ndnId.equals("100004"))
			{
				String sodontrahang = this.sodontrahang.substring(3, this.sodontrahang.length());
				
				query =	"insert ERP_NHAPKHO(ngaynhapkho, ngaychot, SODONTRAHANG, NOIDUNGNHAP, KHONHAP, TRANGTHAI, ngaytao, ngaysua, nguoitao, nguoisua,GhiChu, congty_fk) " +
						"values('" + this.ngaynhapkho + "', '" + this.ngaynhapkho + "', '" + sodontrahang + "', '" + this.ndnId + "', '" + this.khoId + "', " +
								"'0', '" + ngaytao + "', '" + ngaytao + "', '" + this.userId + "', '" + this.userId + "',N'"+this.ghichu+"', '" + this.congtyId + "')";
			}
			else
			{
				sonhanhang = this.sophieunhan.substring(5, this.sophieunhan.length());
				
				//Kiem tra ngay nhap kho co sau ngay nhan hang khong
				query = "select NGAYNHAN from erp_nhanhang where pk_seq = '" + sonhanhang + "'";
				ResultSet gnh = db.get(query);
				String ngaynhan = "";
				if (gnh != null)
				{
					if(gnh.next())
					{
						ngaynhan = gnh.getString("ngaynhan");
					}
					gnh.close();
				}
				Calendar c1 = Calendar.getInstance();

				Calendar c2 = Calendar.getInstance();
				String[] ngaynh = ngaynhan.split("-");
				c1.set(Integer.parseInt(ngaynh[0]),Integer.parseInt(ngaynh[1]), Integer.parseInt(ngaynh[2]));
				String[] ngaynk = this.ngaynhapkho.split("-");
				c2.set(Integer.parseInt(ngaynk[0]),Integer.parseInt(ngaynk[1]), Integer.parseInt(ngaynk[2]));
				boolean kt = c1.after(c2);
				boolean kt2 = c1.equals(c2);
				if(kt == true && kt2 == false)
				{
					this.msg = "Phiếu nhập  " + this.sophieunhan + " có ngày nhận hàng (" + ngaynh[0]+"-"+ngaynh[1]+"-"+ngaynh[2] + ") sau ngày nhập kho (" + ngaynk[0]+"-"+ngaynk[1]+"-"+ngaynk[2] + ")";
					db.getConnection().rollback();
					return false;
				}
				
				query =	"insert ERP_NHAPKHO(ngaynhapkho, ngaychot, SOPHIEUNHAPHANG, NOIDUNGNHAP, KHONHAP, TRANGTHAI, ngaytao, ngaysua, nguoitao, nguoisua,GhiChu) " +
						"values('" + this.ngaynhapkho + "', '" + this.ngaynhapkho + "', '" + sonhanhang + "', '" + this.ndnId + "', '" + this.khoId + "', " +
							"'0', '" + ngaytao + "', '" + ngaytao + "', '" + this.userId + "', '" + this.userId + "',N'"+this.ghichu+"')";
					
			}
		
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_NHAPKHO: " + query;
				db.getConnection().rollback();
				return false;
			}

			String nkCurrent = "";
			query = "select IDENT_CURRENT('Erp_NHAPKHO') as nkId";

			ResultSet rsDmh = db.get(query);
			if (rsDmh != null)
			{
				if (rsDmh.next())
				{
					nkCurrent = rsDmh.getString("nkId");
				}
				rsDmh.close();
			}
			if (this.spList.size() > 0)
			{
				for (int i = 0; i < this.spList.size(); i++)
				{
					ISanpham sp = this.spList.get(i);
					
					if(sp.getSolo().trim().length() <= 0)
					{
						this.msg = "Vui lòng kiểm tra lại sản phẩm " + sp.getMa() + " -- Chưa nhập số lô.";
						db.getConnection().rollback();
						return false;
					}
					
					//
					String ngaysanxuat = "";
					String ngayhethan = "";
					if(this.ndnId.equals("100004"))
					{
						//kiem tra solo tra ve co hop le khong
						query = "select ngaysanxuat, ngayhethan from erp_nhapkho_sanpham " +
								"where solo = '" + sp.getSolo() + "' and sanpham_fk = ( select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "' ) " +
								"order by ngayhethan desc";
						
						System.out.println("111.Check SOLO: " + query);
						ResultSet rsCheck = db.get(query);
						if (rsCheck != null)
						{
							if(rsCheck.next())
							{
								ngaysanxuat = rsCheck.getString("ngaysanxuat");
								ngayhethan = rsCheck.getString("ngayhethan");
							}
							rsCheck.close();
						}
						
						if(ngaysanxuat.trim().length() <= 0 && ngayhethan.trim().length() <= 0)
						{
							this.db.getConnection().rollback();
							this.msg = "Số lô: " + sp.getSolo() + " của sản phẩm trả về: " + sp.getMa() + " không có trong kho. Vui lòng kiểm tra lại";
							return false;
						}
					}
					else
					{
						query = "select ngaysanxuat, ngayhethan  from erp_nhanhang_sp_chitiet " +
								"where nhanhang_fk = '" + sonhanhang + "' and SOLO = '" + sp.getSolo() + "' and sanpham_fk = ( select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "' )  ";
						
						ResultSet rsNsx = db.get(query);
						if(rsNsx != null)
						{
							if(rsNsx.next())
							{
								ngaysanxuat = rsNsx.getString("ngaysanxuat");
								ngayhethan = rsNsx.getString("ngayhethan");
							}
							rsNsx.close();
						}
					}
					
					query = "insert ERP_NHAPKHO_SANPHAM(SONHAPKHO_FK, SANPHAM_FK, SOLUONGNHAN, SOLUONGNHAP, SOLO, NGAYHETHAN, NGAYSANXUAT, DONGIA, THANHTIEN, DONGIAVIET, THANHTIENVIET, TIENTE_FK) " +
							"select '"+ nkCurrent+ "', pk_seq, '" + sp.getSoluongSx().replaceAll(",", "") + "', '" + sp.getSoluongnhapkho().replaceAll(",", "") + "', '" + sp.getSolo() + "', '" + ngayhethan + "', '" + ngaysanxuat + "', " +
									"" + sp.getDongia().replaceAll(",", "") + ", " + sp.getDongia().replaceAll(",", "") + "*" + sp.getSoluongnhapkho().replaceAll(",", "") + ", " + sp.getDongiaViet() + ", " + sp.getDongiaViet() + "*" + sp.getSoluongnhapkho().replaceAll(",", "") + " , '" + sp.getTiente() + "' " +
							"from ERP_SanPham where ma = '" + sp.getMa() + "'";

					if (!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_NHAPKHO_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					} 
					else
					{
						if (this.quanlybean)
						{
							String current = "";
							query = "select IDENT_CURRENT('ERP_NHAPKHO_SANPHAM') as id_nk";

							ResultSet rsCurent = db.get(query);
							if (rsCurent != null)
							{
								if (rsCurent.next())
								{
									current = rsCurent.getString("id_nk");
								}
								rsCurent.close();
							}

							List<ISanphamCon> spCon = sp.getSpConList();
							for (int j = 0; j < spCon.size(); j++)
							{
								ISanphamCon detail = spCon.get(j);
								String vitri = detail.getVitri().substring(detail.getVitri().indexOf(" - ") + 3,
										detail.getVitri().length());

								query = "insert ERP_NHAPKHO_SP_CHITIET(NHAPKHO_SANPHAM_FK, SOLUONG, KHU, VITRI) "
										+ "values('" + current + "', '" + detail.getSoluong().replaceAll(",", "") + "', '"
										+ detail.getKhu() + "', '" + vitri + "')";

								if (!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_NHAPKHO_SP_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
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
			System.out.println("Exception : " + e.getMessage());
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
	}


	public boolean updateNhapKho()
	{
		//Check sanpham
		for(int i = 0; i < this.spList.size(); i++)
		{
			if(this.spList.get(i).getSolo().trim().length() <= 0 || this.spList.get(i).getNgayhethan().trim().length() <= 0 )
			{
				this.msg = "Bạn phải nhập số lô và ngày hết hạn cho sản phẩm nhập kho";
				return false;
			}
		}
				
		String nam = this.ngaynhapkho.substring(0, 4);
		String thang = this.ngaynhapkho.substring(5, 7);
		
		String sql = "select count(pk_seq) as sodong from erp_khoasothang where nam = '" + nam + "' and thangks = '" + Integer.parseInt(thang) + "'";
		
		ResultSet rs  = db.get(sql);
		int count = 0;
		try
		{
			if(rs != null)
			{
				if(rs.next())
				{
					count = rs.getInt("sodong");
				}
				rs.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(count > 0)
		{
			this.msg = "Tháng " + thang + ", năm " + nam + " đã khóa sổ tháng, bạn không thể nhập kho vào ngày này, vui lòng chọn ngày nhập kho ở tháng sau.";
			return false;
		}
		boolean flag = false;
		for(int i = 0; i < this.spList.size(); i++)
		{
			ISanpham sp = this.spList.get(i);
			if(sp.getSoluongnhapkho().length() > 0)
			{
				if(Float.parseFloat(sp.getSoluongnhapkho()) > 0)
				{
					flag = true;
					break;
				}
			}
		}
		if(flag == false)
		{
			this.msg = "Không có sản phẩm nào được nhập số lượng để nhập kho, vui lòng kiểm tra lại";
			return false;
		}

		try
		{
			String ngaysua = getDateTime();

			db.getConnection().setAutoCommit(false);

			if(this.ngaychotNV.length() < 0)
				this.ngaychotNV = this.ngaynhapkho;
			
			String query = "";
				query =	"update ERP_NHAPKHO set ngaynhapkho = '" + this.ngaynhapkho + "', ngaychot = '" + this.ngaychotNV + "', SOLENHSANXUAT = '" + this.solenhsanxuat + "',CongDoan_FK='"+this.CongDoanId+"', SODONTRAHANG = null, SOPHIEUNHAPHANG = null, NOIDUNGNHAP = '" + this.ndnId + "', KHONHAP = '" + this.khoId + "', " +
					" ngaysua = '" + ngaysua + "', nguoisua = '" + this.userId + "',GhiChu=N'"+this.ghichu+"' where pk_seq = '" + this.id + "'";
			System.out.println("1.Update Nhap kho: " + query + "\n");

			if(!db.update(query))
			{
				this.msg = "4.Khong the cap nhat NHAPKHO: " + query;
				db.getConnection().rollback();
				return false;
			}
			

			query = "delete ERP_NHAPKHO_SP_CHITIET where NHAPKHO_SANPHAM_FK in ( select pk_seq from ERP_NHAPKHO_SANPHAM where SONHAPKHO_FK = '" + this.id + "' )";
			System.out.println("1.Delete: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_NHAPKHO_SP_CHITIET: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_NHAPKHO_SANPHAM where SONHAPKHO_FK = '" + this.id + "'";
			System.out.println("2.Delete: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_NHAPKHO_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}

			if (this.spList.size() > 0)
			{
				query = "select top(1) NAM as namMax, THANGKS as thangMax from ERP_KHOASOTHANG order by NAM desc, THANGKS desc ";
				System.out.println("1.Khoi tao thang: " + query);
				rs = db.get(query);
				
				String thangKsMax = "";
				String namKsMax = "";
				
				if(rs != null)
				{
					while(rs.next())
					{
						thangKsMax = rs.getString("thangMax");
						namKsMax = rs.getString("namMax"); 
						
					}
					rs.close();
				}
			for (int i = 0; i < this.spList.size(); i++)
			{
				ISanpham sp = this.spList.get(i);
				
				if(sp.getSolo().trim().length() <= 0)
				{
					this.msg = "Vui lòng kiểm tra lại sản phẩm " + sp.getMa() + " -- Chưa nhập số lô.";
					db.getConnection().rollback();
					return false;
				}
				query = "select giaton from erp_tonkhothang where thang = '" + thangKsMax + "' and nam = '" + namKsMax + "'" +
					" and sanpham_fk =  '"+sp.getId() + "' and khott_fk = '100000'";
			
				System.out.println("1__Lay gia ton: " + query);
				String giaTon = "0";
				ResultSet rsGia = db.get(query);
				if(rsGia != null)
				{
					if(rsGia.next())
					{
						giaTon = rsGia.getString("giaton");
					}
					rsGia.close();
				}
					
				query = "insert ERP_NHAPKHO_SANPHAM(SONHAPKHO_FK, SANPHAM_FK, SOLUONGNHAN, SOLUONGNHAP, SOLO, NGAYSANXUAT, NGAYHETHAN,NgayNhapKho,DONGIA, THANHTIEN, DONGIAVIET, THANHTIENVIET, TIENTE_FK) " +
					" select '" + this.id + "', '"+sp.getId()+"', '" + sp.getSoluongSx().replaceAll(",", "") + "', '" + sp.getSoluongnhapkho().replaceAll(",", "") + "', '" + sp.getSolo() + "', '" + sp.getNgaySanXuat() + "', " +
							" '"+sp.getNgayhethan()+"','"+sp.getNgayNhapKho()+"', " + giaTon + ", " + giaTon + "*" + sp.getSoluongnhapkho().replaceAll(",", "") + ", " +
						giaTon + ", " + giaTon + "*" + sp.getSoluongnhapkho() + " , '" + sp.getTiente() + "' " +
						"from erp_sanpham where pk_seq='"+sp.getId()+"'" ;
					if (!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_NHAPKHO_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					} 
					else
					{
						if (this.quanlybean)
						{
							String current = "";
							query = "select IDENT_CURRENT('ERP_NHAPKHO_SANPHAM') as id_nk";

							ResultSet rsCurent = db.get(query);
							if (rsCurent != null)
							{
								if (rsCurent.next())
								{
									current = rsCurent.getString("id_nk");
								}
								rsCurent.close();
							}								
							List<ISanphamCon> spCon = sp.getSpConList();
							for (int j = 0; j < spCon.size(); j++)
							{
								ISanphamCon detail = spCon.get(j);
								String vitri = detail.getVitri().substring(detail.getVitri().indexOf(" - ") + 3,
										detail.getVitri().length());

								query = " insert ERP_NHAPKHO_SP_CHITIET(NHAPKHO_SANPHAM_FK, SOLUONG, KHU, VITRI) "
										+ " values('" + current + "', '" + detail.getSoluong().replaceAll(",", "") + "', '"
										+ detail.getKhu() + "', '" + vitri + "')";

								if (!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_NHAPKHO_SP_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
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
			 
			
			System.out.println("Exception : " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}

	public String chotNhapKho(String userId)
	{
		try 
		{
			String ngaysua = getDateTime();
			
			//Check khoa so thang
			String sql = "select ngaychot from erp_nhapkho where pk_seq = '" + this.id + "'";
			ResultSet rs = db.get(sql);
			if (rs != null)
			{
				if(rs.next())
				{
					this.ngaychotNV = rs.getString("ngaychot");
				}
				rs.close();
			}
			
			String nam = this.ngaychotNV.substring(0, 4);
			String thang = this.ngaychotNV.substring(5, 7);
			
			sql = "select count(pk_seq) as sodong from erp_khoasothang where nam = '" + nam + "' and thangks = '" + Integer.parseInt(thang) + "'";
			System.out.println("___Cau lenh check: " + sql);
			
			rs  = db.get(sql);
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
				this.msg = "Tháng " + thang + ", năm " + nam + " đã khóa sổ tháng, bạn không thể chốt phiếu nhập này, vui lòng chọn ngày chốt nghiệp vụ ở tháng sau.";
				return this.msg;
			}
			
			
			db.getConnection().setAutoCommit(false);
			
			String query = " select a.KHONHAP, a.SOPHIEUNHAPHANG, a.SODONTRAHANG, a.SOLENHSANXUAT, a.NGAYNHAPKHO, a.NGAYCHOT, a.NOIDUNGNHAP, a.pk_seq as ctnhapkho, " +
						   " b.SANPHAM_FK, b.SOLUONGNHAP, b.DONGIA, b.DONGIAVIET, b.TienTe_FK, isnull(b.SOLO, 0) as solo, d.QUANLYLO, d.LOAISANPHAM_FK, d.nguongoc " +
						   " from ERP_NHAPKHO a inner join ERP_NHAPKHO_SANPHAM b on a.PK_SEQ = b.SONHAPKHO_FK " +
						   " inner join ERP_KHOTT c on a.khonhap = c.PK_SEQ " +
						   " inner join ERP_SANPHAM d on b.SANPHAM_FK = d.PK_SEQ " +
						   " where a.PK_SEQ = '" + this.id + "'";
			
			System.out.println("1.Query chot nhap kho init: " + query);
			
			rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String ctnhapkho = rs.getString("ctnhapkho"); 
					String loaisanpham = rs.getString("LOAISANPHAM_FK");
					String noidungnhap = rs.getString("NOIDUNGNHAP");
					String masanpham = rs.getString("SANPHAM_FK");
					String khonhap = rs.getString("KHONHAP");
					
					double dongia = rs.getDouble("DONGIA");
					double dongiaViet = rs.getDouble("DONGIAVIET");
					String tiente_fk = rs.getString("TienTe_FK");
					
					float soluong =  rs.getFloat("SOLUONGNHAP");
					
					String nguongoc = rs.getString("nguongoc");
					
					//Luu lai gia ton cua SP truoc thoi diem cap nhat -- > sau nay tinh lai neu co chiet khau
					//Phai luu lai don gia xuat ngay tai thoi diem nay, de sau nay chay lai co can cu de cap nhat lai gia
					query = "update ERP_NHAPKHO_SANPHAM set DONGIA_SAUCK = DonGiaViet, " +
								"DONGIA_TONTRUOC = ( select GiaTon from ERP_KhoTT_SanPham where sanpham_fk = '" + masanpham + "' and khott_fk = '" + khonhap + "' ), " +
								"SOLUONG_TRONGKHO_TRUOCCHOT = ( select SoLuong from ERP_KhoTT_SanPham where sanpham_fk = '" + masanpham + "' and khott_fk = '" + khonhap + "' ) " +
							"where SONHAPKHO_FK = '" + this.id + "' and SANPHAM_FK = '" + masanpham + "' ";
					
					if(!db.update(query))
					{
						this.msg = "4.Khong the cap nhat ERP_NHAPKHO_SANPHAM: " + query;
						System.out.println(msg);
						db.getConnection().rollback();
						return this.msg;
					}
					
					if(nguongoc.equals("1"))  //San pham mua ngoai --> Binh quan thoi diem ( Cap nhat gia ton )
					{
						query = "update ERP_KHOTT_SANPHAM set soluong = soluong + " + soluong + ", available = available + " + soluong + ", " +
														"GIATON = (	" + dongiaViet + " * " + soluong + " + giaton * soluong) / (" + soluong + " + SOLUONG) , " +
														"THANHTIEN = (soluong + " + soluong + ") * ( " + dongiaViet + " * " + soluong + " + giaton * soluong) / ( " + soluong + " + SOLUONG ) " +
								" where khott_fk = '" + khonhap + "' and sanpham_fk = '" + masanpham + "'";
					}
					else  // san pham tu san xuat --> Binh quan cuoi ky
					{
						query = "update ERP_KHOTT_SANPHAM set soluong = soluong + " + soluong + ", available = available + " + soluong + ", " +
														"THANHTIEN = (soluong + " + soluong + ") * GiaTon " +
								" where khott_fk = '" + khonhap + "' and sanpham_fk = '" + masanpham + "'";
					}
					
					
					System.out.println("3.Query update kho, gia ton: " + query);
					
					if(!db.update(query))
					{
						this.msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						System.out.println(msg);
						rs.close();
						db.getConnection().rollback();
						return this.msg;
					}
					
					//Cac kho khac gia ton cung bang kho nhap ( GiaTon khong phan biet theo kho, cac chi phi khac tinh vao chi phi ban hang )
					query = " update ERP_KHOTT_SANPHAM " +
							" set GIATON = ( select GiaTon from ERP_KHOTT_SANPHAM where khott_fk = '" + khonhap + "' and sanpham_fk = '" + masanpham + "' ), THANHTIEN = SOLUONG * GIATON " +
							" where SANPHAM_FK = '" + masanpham + "'";
					
					if(!db.update(query))
					{
						this.msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						System.out.println(msg);
						rs.close();
						db.getConnection().rollback();
						return this.msg;
					}
					
					String taikhoanktCo = "";
					String taikhoanktNo = "";
					
					query = "select TAIKHOANKTCO, TAIKHOANKTNO " +
							"from ERP_CAUHINHDINHKHOANKETOAN  " +
							"where NOIDUNGNHAP_FK = '" + noidungnhap + "' and LOAISANPHAM_FK = '" + loaisanpham + "' ";
					
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
							rs.close();
							db.getConnection().rollback();
							return this.msg;
						}
						
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
							query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + dongiaViet + "*" + soluong + ", " +
																" GIATRINGUYENTE = GIATRINGUYENTE + "  + dongia + "*" + soluong + 
									" where taikhoankt_fk = '" + taikhoanktNo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
						}
						else
						{
							query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
									"values('" + taikhoanktNo + "', '0', " + dongiaViet + "*" + soluong + ", '" + tiente_fk + "', " + dongia + "*" + soluong + ", '" + thang + "', '" + nam + "')";
						}
						
						//System.out.println("5.Cap nhat: + query");
						if(!db.update(query))
						{
							rs.close();
							db.getConnection().rollback();
							
							System.out.println("1.Loi: " + query);
							return "6.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
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
							query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + dongiaViet + "*" + soluong + ", " +
															" GIATRINGUYENTE = GIATRINGUYENTE + "  + dongia + "*" + soluong + 
									" where taikhoankt_fk = '" + taikhoanktCo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
						}
						else
						{
							query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
									"select '" + taikhoanktCo + "', " + dongiaViet + "*" + soluong + ", '0', '" + tiente_fk + "', " + dongia + "*" + soluong + ", '" + thang + "', '" + nam + "' ";
						}
						
								
						if(!db.update(query))
						{
							rs.close();
							db.getConnection().rollback();
							
							System.out.println("2.Loi: " + query);
							return "7.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
						}
						
					}
					query =
						"SELECT SP.SANPHAM_FK,SP.SOLO,SP.NGAYHETHAN,SP.NGAYNHAPKHO,100000 AS VITRI,SP.SOLUONGNHAP AS SOLUONG,SP.DONGIA,SP.THANHTIEN,SP.NGAYSANXUAT,SP.NGAYNHAPKHO "+
						"FROM ERP_NHAPKHO_SANPHAM SP "+
						"	INNER JOIN ERP_NHAPKHO NK ON NK.PK_SEQ=SP.SONHAPKHO_FK "+
						" WHERE NK.PK_sEQ='" + ctnhapkho + "'";
						System.out.println("Nhap kho chi tiet: " + query);
						ResultSet beanRs = db.get(query);
					 
							while(beanRs.next())
							{
								String spId = beanRs.getString("SANPHAM_FK");
								String solo = beanRs.getString("SOLO");
								String ngaynhapkho=beanRs.getString("NgayNhapKho");
								String ngaysanxuat = beanRs.getString("NGAYSANXUAT");
								String ngayhethan = beanRs.getString("NGAYHETHAN");
								String soluongct = beanRs.getString("SOLUONG");
								String vitri = beanRs.getString("VITRI");
								query = "select count(*) as sodong from ERP_KHOTT_SP_CHITIET where khott_fk = '" + khonhap + "' and sanpham_fk = '" + spId + "' and solo = '" + solo + "' and bin = '" + vitri + "'";
								ResultSet rsCheck = db.get(query);
								boolean flag = true;
								if(rsCheck != null)
								{
									if(rsCheck.next())
									{
										if(rsCheck.getString("sodong").equals("0"))
											flag = false;
										rsCheck.close();
									}
								}
								
								if (flag) // da ton tau, cap nhat booked, avail
								{
									query = "update ERP_KHOTT_SP_CHITIET set soluong = soluong + '" + soluongct
											+ "', AVAILABLE = AVAILABLE + '" + soluongct + "', NGAYSANXUAT = '" + ngaysanxuat + "', NGAYHETHAN = '" + ngayhethan + "', NGAYNHAPKHO = '" + this.ngaychotNV + "' where KHOTT_FK = '"
											+ khonhap + "' and SANPHAM_FK = '" + spId + "' and SOLO = '" + solo
											+ "' and BIN = '" + vitri + "'";
								} 
								else
								{
									query = "insert ERP_KHOTT_SP_CHITIET(KHOTT_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO, BIN) "
											+ "values('" + khonhap + "', '" + spId + "', '" + soluongct + "', '0', '" + soluongct + "', '" + solo + "', '" + ngaysanxuat + "', '" + ngayhethan + "', '" + ngaynhapkho + "', '" + vitri + "')";
								}
								
								System.out.println("8.Cap nhat kho CHITIET: " + query);
								
								if(!db.update(query))
								{
									this.msg = "8.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
									System.out.println(msg);
									db.getConnection().rollback();
									return this.msg;
								}
								
								
							}
							beanRs.close();
					}
										
				}
				rs.close();
			query = "update ERP_NHAPKHO set trangthai = '1', giochot = '" + getTime() + "',  ngaysua = '" + ngaysua + "', nguoisua = '" + userId + "' where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_NHAPKHO: " + query;
				db.getConnection().rollback();
				return this.msg;
			}
			query=
			"   INSERT INTO ERP_YeuCauKiemDinh(LENHSANXUAT_FK,CONGDOAN_FK,NHAPKHO_FK,SANPHAM_FK,SOLO,NGAYHETHAN,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,SOLUONG,SOLUONGDAT,NGAYSANXUAT,NGAYKIEM,CONGTY_FK,DINHLUONG,DINHTINH) "+
			"	SELECT NK.SOLENHSANXUAT,NK.CONGDOAN_FK,NK.PK_SEQ AS NHAPKHO_FK,SP.SANPHAM_FK,SP.SOLO,SP.NGAYHETHAN,0 AS TRANGTHAI,'"+this.userId+"' AS NGUOITAO,'"+getDateTime()+"','"+this.userId+"' AS NGUOISUA,'"+getDateTime()+"',SP.SOLUONGNHAP AS SOLUONG,SP.SOLUONG,SP.NGAYSANXUAT,'"+getDateTime()+"' AS NGAYKIEM ,'"+this.congtyId+"',ERP_SANPHAM.KIEMTRADINHLUONG,ERP_SANPHAM.KIEMTRADINHTINH"+
				"FROM ERP_NHAPKHO NK INNER JOIN ERP_NHAPKHO_SANPHAM SP INNER JOIN ERP_SANPHAM ON ERP_SANPHAM.PK_SEQ=SP.SANPHAM_FK "+
			"	ON NK.PK_SEQ=SP.SONHAPKHO_FK "+ 
			"	WHERE ERP_SANPHAM.KIEMTRADINHLUONG=1 OR ERP_SANPHAM.KIEMTRADINHTINH=1 AND NK.PK_SEQ='"+this.id+"'";
			System.out.print("Tao phieu kiem dinh_____"+query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_YeuCauKiemDinh: " + query;
				db.getConnection().rollback();
				return this.msg;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return this.msg;
		} 
		catch (Exception e) 
		{ 
			this.msg = "Exception : " + e.getMessage();
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {} 
			
			return this.msg;
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private String getSoLoTuDong()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hhmm");
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

	public void DBclose()
	{
		try{
			if(ndnRs!=null){
				ndnRs.close();
			}
			if(lhhRs!=null){
				lhhRs.close();
			}
			
			if(vtlkRs!=null){
				vtlkRs.close();
			}
			if(khoRs!=null){
				khoRs.close();
			}
			
			if(spList!=null){
				spList.clear();
			}
			if(khuList!=null){
				khuList.clear();
			}
			if(vitriList!=null){
				vitriList.clear();
			}
		}catch(Exception er){
			er.printStackTrace();
		}finally{
			db.shutDown();
		}
	}

	public String getNguoigiaohang()
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

	public String getNhaptaikho()
	{
		return this.nhaptaikho;
	}

	public void setNhaptaikho(String nhaptaikho)
	{
		this.nhaptaikho = nhaptaikho;
	}

	public String getGhichu()
	{
		return this.ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}

	public String getNgaychotNV() 
	{
		return this.ngaychotNV;
	}

	public void setNgaychotNV(String ngaychotNV) 
	{
		this.ngaychotNV = ngaychotNV;
	}

	public String getSoDontrahang()
	{
		return this.sodontrahang;
	}

	public void setSoDontrahang(String soDth)
	{
		this.sodontrahang = soDth;
	}

	public String getSoLenhsx() 
	{
		return this.solenhsanxuat;
	}

	public void setSoLenhsx(String soLenhsx)
	{
		this.solenhsanxuat = soLenhsx;
	}


	public boolean createNhapKhoLSX() 
	{
 
		boolean flag = false;
		for(int i = 0; i < this.spList.size(); i++)
		{
			ISanpham sp = this.spList.get(i);
			if(sp.getSoluongnhapkho().length() > 0)
			{
				if(Float.parseFloat(sp.getSoluongnhapkho()) > 0)
				{
					flag = true;
					break;
				}
			}
		}
		if(this.spList.size()==0){
			flag= false;
		}
		if(this.KHonhanTP.equals("")){
			this.msg = "Vui lòng chọn kho nhận thành phẩm đạt";
			return false;
		}
		if(flag == false)
		{
			this.msg = "Không có sản phẩm nào được nhập số lượng để nhập kho, vui lòng kiểm tra lại";
			return false;
		}
 
		for(int i = 0; i < this.spList.size(); i++)
		{
			if(this.spList.get(i).getSolo().trim().length() <= 0 || this.spList.get(i).getNgayhethan().trim().length() <= 0 )
			{
				this.msg = "Bạn phải nhập số lô và ngày hết hạn cho sản phẩm nhập kho";
				return false;
			}
			if(this.isQLKV.equals("1") && this.spList.get(i).getKhuvucId().trim().length() <= 0)
			{
				this.msg = "Bạn phải chọn khu vực cho kho";
				return false;
			}
		}
	 
		try 
		{
			db.getConnection().setAutoCommit(false);
			this.ndnId="100047";
			String query =	" insert ERP_NHAPKHO (ngaynhapkho, ngaychot, SOLENHSANXUAT,CongDoan_FK, NOIDUNGNHAP, KHONHAP, TRANGTHAI, ngaytao, ngaysua, nguoitao, nguoisua, congty_fk ,KHONGKIEMDINH) " +
							" values('" + this.ngaynhapkho + "', '" + this.ngaynhapkho + "', '" + this.solenhsanxuat + "', '"+this.CongDoanId+"','" + this.ndnId + "','" + this.khoId + "', " +
							" '0', '" + getDateTime() + "', '" + getDateTime() + "', '" + this.userId + "', '" + this.userId + "', '" + this.congtyId + "','"+this.Khongkiemdinh+"')";
			
			if(!db.update(query))
			{
				this.msg = "1.Khong the tao moi ERP_NHAPKHO: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select IDENT_CURRENT('Erp_NHAPKHO') as nkId";
			ResultSet rsDmh = db.get(query);
			if (rsDmh.next())
			{
				this.id = rsDmh.getString("nkId");
			}
			rsDmh.close();
			 			
			query=" update erp_lenhsanxuat_giay set trangthai = 3 where trangthai < 3 and pk_seq= "+this.solenhsanxuat;
			if(!db.update(query))
			{
				this.msg = "1.Khong the cập nhật erp_lenhsanxuat_giay: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " select top(1) NAM as namMax, THANGKS as thangMax from ERP_KHOASOTHANG order by NAM desc, THANGKS desc ";
			ResultSet  rs = db.get(query);
			String thangKsMax = "";
			String namKsMax = "";
			if(rs != null)
			{
				while(rs.next())
				{
					thangKsMax = rs.getString("thangMax");
					namKsMax = rs.getString("namMax"); 
					
				}
				rs.close();
			}
			
			if (this.spList.size() > 0)
			{
				for (int i = 0; i < this.spList.size(); i++)
				{
					ISanpham sp = this.spList.get(i);
					 
					query =		 " select giaton from erp_tonkhothang where thang = '" + thangKsMax + "' and nam = '" + namKsMax + "'" +
								 " and sanpham_fk =  '"+sp.getId() + "' and khott_fk = "+this.khoId;
					
					
					String giaTon = "0";
					ResultSet rsGia = db.get(query);
					if(rsGia != null)
					{
						if(rsGia.next())
						{
							giaTon = rsGia.getString("giaton");
						}
						rsGia.close();
					}
					
					
					
					if(Double.parseDouble(sp.getSoluongnhapkho()) >0){
						 
						query= " select  isnull(hansudung,0) as hansudung   "+
							   " from erp_sanpham where pk_seq='"+sp.getId()+"'" ;
						
						ResultSet rscheck =db.get(query);
						if (rscheck != null)
						{
							if(rscheck.next()){
								if(rscheck.getDouble("hansudung") ==0){
									this.msg = "Vui lòng kiểm lại hạn sử dụng của thành phẩm : "+ sp.getMa();
									db.getConnection().rollback();
									return false;
								}
							}
							rscheck.close();
						}							
						
						//đối với đơn vị đo lường quy đổi
						// kiểm tra đã có đơn vi đo lương KG chưa?
						query=" SELECT  PK_SEQ ,CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE  CASE WHEN QC.DVDL2_FK =100003 THEN 1 ELSE 0 END "+
							  " END  as istrongluong FROM ERP_SANPHAM SP  "+
							  " LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ AND QC.DVDL2_FK=100003 "+
							  " WHERE SP.PK_SEQ="+sp.getId();
						rscheck=db.get(query);
						
//						if(rscheck.next()){
//							if(rscheck.getString("istrongluong").equals("0")){
//								this.msg = "Vui lòng kiểm thiết lập quy đổi ra KG trong dữ liệu nền cho sản phẩm  : "+ sp.getMa();
//								db.getConnection().rollback();
//								return false;
//							}
//						}
						rscheck.close();
						
						double soluongquydoi=0;
						double SOLUONGLAYMAU_=0;
						query= " SELECT SP.DVDL_FK,QC.SOLUONG1,QC.SOLUONG2 FROM ERP_SANPHAM SP "+
							   " LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK="+sp.getDvdlId()+"  WHERE SP.PK_SEQ="+sp.getId();
						  rscheck=db.get(query);
						if(rscheck.next()){
							if(rscheck.getString("DVDL_FK").equals(sp.getDvdlId())){
								soluongquydoi= Double.parseDouble(sp.getSoluongnhapkho());
								 
							}else{
								double soluong2=0;
								double soluong1=0;
								try{
									soluong2 =rscheck.getDouble("SOLUONG2");
								}catch(Exception er){					
								}
								try{
									soluong1 =rscheck.getDouble("SOLUONG1");
								}catch(Exception er){					
								}
								
								if(soluong2==0){
									this.msg = "2.Số lượng  quy đổi [2] của thành phẩm : " + sp.getDiengiai()+  " trong dữ liệu nền không hợp lệ [ = 0], vui lòng cập nhật lại dữ liệu nền";
									db.getConnection().rollback();
									return false;
								}
								
								if(soluong1==0){
									this.msg = "2.Số lượng  quy đổi [1] của thành phẩm : " + sp.getDiengiai()+  " trong dữ liệu nền không hợp lệ [ = 0], vui lòng cập nhật lại dữ liệu nền";
									db.getConnection().rollback();
									return false;
								}
								
								soluongquydoi=  Double.parseDouble(sp.getSoluongnhapkho())*rscheck.getDouble("SOLUONG1")/rscheck.getDouble("SOLUONG2") ;
							
								
							}
						}else{
							this.msg = "2.Không thể xác định đơn vị  quy đổi của thành phẩm : " + sp.getDiengiai();
							db.getConnection().rollback();
							return false;
						}
						
						// đối với lấy mẫu (đổi về chuẩn)
						query=  " SELECT SP.DVDL_FK,QC.SOLUONG1,QC.SOLUONG2 FROM ERP_SANPHAM SP "+
						 		" LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK="+sp.getDvdl_Mau_Id()+"  WHERE SP.PK_SEQ="+sp.getId();
					  rscheck=db.get(query);
					if(rscheck.next()){
						if(rscheck.getString("DVDL_FK").equals(sp.getDvdl_Mau_Id())){
							 
							SOLUONGLAYMAU_=Double.parseDouble(sp.getSoluonglaymau());
						}else{
								
							double soluong2=0;
							double soluong1=0;
							try{
								soluong2 =rscheck.getDouble("SOLUONG2");
							}catch(Exception er){					
							}
							try{
								soluong1 =rscheck.getDouble("SOLUONG1");
							}catch(Exception er){					
							}
							
							if(soluong2==0){
								this.msg = "2.Số lượng  quy đổi [2] của thành phẩm : " + sp.getDiengiai()+  " trong dữ liệu nền không hợp lệ [ = 0], vui lòng cập nhật lại dữ liệu nền";
								db.getConnection().rollback();
								return false;
							}
							
							if(soluong1==0){
								this.msg = "2.Số lượng  quy đổi [1] của thành phẩm : " + sp.getDiengiai()+  " trong dữ liệu nền không hợp lệ [ = 0], vui lòng cập nhật lại dữ liệu nền";
								db.getConnection().rollback();
								return false;
							}
							SOLUONGLAYMAU_=  Double.parseDouble( sp.getSoluonglaymau())*rscheck.getDouble("SOLUONG1")/rscheck.getDouble("SOLUONG2") ;
							
						}
					}else{
						this.msg = "2.Không thể xác định đơn vị  quy đổi của thành phẩm : " + sp.getDiengiai();
						db.getConnection().rollback();
						return false;
					}
					
					if( Double.parseDouble(formatter.format(SOLUONGLAYMAU_))  >Double.parseDouble(formatter.format(soluongquydoi)) ){
						this.msg = "Số lượng lấy mẫu không được nhiều hơn số lượng của phiếu nhập. Đưa về đơn vị chuẩn : Lấy mẫu: "+formatter.format(SOLUONGLAYMAU_)+" Nhập :"+formatter.format(soluongquydoi)+"  ";
						db.getConnection().rollback();
						return false;
					}
					
					query = " insert ERP_NHAPKHO_SANPHAM(SONHAPKHO_FK, SANPHAM_FK, SOLUONGNHAN,SOLUONGTRUOCQUYDOI, SOLUONGNHAP, KHUVUCKHO_FK, SOLO, NGAYSANXUAT, NGAYHETHAN,NgayNhapKho,DONGIA, THANHTIEN, DONGIAVIET, THANHTIENVIET, TIENTE_FK,DVDL_FK,SOLUONGLAYMAU,SOLUONGLAYMAU_TRUOCQD ,DVDL_MAU_FK) " +
							" select '" + this.id + "', '"+sp.getId()+"', '" + sp.getSoluongSx().replaceAll(",", "") + "', '" + sp.getSoluongnhapkho().replaceAll(",", "") + "',"+formatter.format(soluongquydoi) +", " + sp.getKhuvucId() + ", '" + sp.getSolo() + "', '" + sp.getNgaySanXuat() + "',  " +
							"   '"+sp.getNgayhethan()+"' ,'"+sp.getNgayNhapKho()+"', " + giaTon + ", " + giaTon + "*" + sp.getSoluongnhapkho().replaceAll(",", "") + ", " +
							 giaTon + ", " + giaTon + "*" + sp.getSoluongnhapkho() + " , '" + sp.getTiente() + "',"+sp.getDvdlId()+","+formatter.format(SOLUONGLAYMAU_)+","+sp.getSoluonglaymau()+","+sp.getDvdl_Mau_Id()+" " +
							" from erp_sanpham where pk_seq='"+sp.getId()+"'" ;
					
					if (!db.update(query))
					{
						this.msg = "2.Khong the tao moi ERP_NHAPKHO_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					} 
					}
				}
			}
			 
			
			if(chotNhapKhoLSX().trim().length()>0)
			{
				return false;
			}
			
		 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg=e.toString();
			db.update("rollback");
		 
		}
		
		
		return true;
	}

	
	public String chotNhapKhoLSX()
	{
		util=new Utility();
		try 
		{
			String ngaysua = getDateTime();
			String solenhSX="";
			String nam = this.ngaynhapkho.substring(0, 4);
			String thang = this.ngaynhapkho.substring(5, 7);
			String query=" select isnull(a.ISLSXCONGNGHE,'0') as  ISLSXCONGNGHE from ERP_LENHSANXUAT_GIAY a  " +
						 " where a.PK_SEQ =( select solenhsanxuat from ERP_NHAPKHO nk where nk.PK_SEQ ="+this.id+" )";
			ResultSet rs1=db.get(query);
			String islsxcongnghe="0";
			if (rs1 != null)
			{
				if(rs1.next()){
					islsxcongnghe =rs1.getString("ISLSXCONGNGHE");
				}
				rs1.close();
			}
			
			query =     " select isnull(a.KHONGKIEMDINH,'0') as KHONGKIEMDINH , B.DVDL_FK,B.SOLUONGTRUOCQUYDOI ,b.dongia ,  a.pk_seq as ctnhapkho,a.KHONHAP, a.SOLENHSANXUAT, a.NGAYNHAPKHO, a.NGAYCHOT, a.NOIDUNGNHAP, " +
						" b.SANPHAM_FK, b.SOLUONGNHAP, b.DONGIA, b.DONGIAVIET, b.TienTe_FK, isnull(b.SOLO, 0) as solo, ISNULL(b.KHUVUCKHO_FK,0) AS KVKHO, " +
						" isnull(b.NgaySanXuat, '') as ngaysanxuat, isnull(b.NgayHetHan, '') as ngayhethan, " +
						" d.QUANLYLO, d.LOAISANPHAM_FK,  isnull(d.batbuockiemdinh,'0')  as KiemDinhChatLuong , " +
						" isnull(d.KiemTraDinhLuong,0) as DinhLuong,isnull(d.KiemTraDinhTinh,0)as DinhTinh ,isnull(cd.dinhtinh,'0') as dinhtinhcd, isnull(cd.dinhluong,'0') as dinhluongcd, " +
						" ISNULL( ( select top(1) GIATON from ERP_TONKHOTHANG where SANPHAM_FK = d.pk_seq order by NAM desc, THANG desc ) , 0 ) as giaTON , " +
						" isnull(b.SOLUONGLAYMAU_TRUOCQD,0) as SOLUONGLAYMAU_TRUOCQD ,isnull(b.SOLUONGLAYMAU,0) as SOLUONGLAYMAU  " +
						" from ERP_NHAPKHO a left join erp_congdoansanxuat_Giay cd on cd.pk_seq=a.congdoan_fk " +
						" inner join ERP_NHAPKHO_SANPHAM b on a.PK_SEQ = b.SONHAPKHO_FK " +
						" inner join ERP_KHOTT c on a.khonhap = c.PK_SEQ " +
						" inner join ERP_SANPHAM d on b.SANPHAM_FK = d.PK_SEQ " +
						" where a.PK_SEQ = '" + this.id + "'";
			
			//System.out.println("1.Query chot nhap kho init: " + query);
			
			ResultSet rs = db.get(query);
			int i=0;
			 	
			while(rs.next()){
				
				
				
				i++;
				solenhSX = rs.getString("SOLENHSANXUAT"); 
				String loaisanpham = rs.getString("LOAISANPHAM_FK");
				
				 
				 
				String masanpham = rs.getString("SANPHAM_FK");
				String khonhap = rs.getString("KHONHAP");
				String ngaysanxuat = rs.getString("ngaysanxuat");
				
				
				String ngayhethan = rs.getString("ngayhethan");
				
				if(ngayhethan==null || ngayhethan.equals("") ){
					this.msg = "Vui lòng nhập ngày hết hạn ";
					db.getConnection().rollback();
					return this.msg;
				}
					
					
				double dongia=rs.getDouble("dongia");
				double dongiaViet = rs.getDouble("giaTON");
				String tiente_fk = "100000";
				this.Khongkiemdinh=rs.getString("KHONGKIEMDINH");
				double soluong =  rs.getDouble("SOLUONGNHAP");
				String kiemdinhCL = rs.getString("KiemDinhChatLuong");
				String dinhluong=rs.getString("dinhluong");
				String dinhtinh=rs.getString("dinhtinh");
				if(rs.getString("dinhtinhcd").equals("1")){
					dinhtinh="1";
				}
				if(rs.getString("dinhluongcd").equals("1")){
					dinhluong="1";
				}
				 
	  
				String spId = rs.getString("SANPHAM_FK");
				String solo = rs.getString("SOLO").trim();
				String khuvucId = rs.getString("KVKHO").trim();
				String soluongct = rs.getString("SOLUONGNHAP");
				String vitri = "";
				
				Kho_Lib kholib=new Kho_Lib();
				kholib.setNgaychungtu(this.ngaynhapkho);
				kholib.setLoaichungtu("erpnhapkho.java 2199: chotNhapKhoLSX "+this.id  );
				kholib.setKhottId(khonhap);
				 
				kholib.setSolo(solo);
				kholib.setSanphamId(spId);
				kholib.setMame("");
				kholib.setMathung("");
				kholib.setMaphieu("");
				kholib.setMaphieudinhtinh("");
				kholib.setPhieuEo("");
				kholib.setNgayhethan(ngayhethan);
				kholib.setNgaysanxuat(ngaysanxuat);
				kholib.setNgaynhapkho(this.ngaynhapkho);
				kholib.setBooked(0);
				kholib.setSoluong(rs.getFloat("SOLUONGNHAP"));
				kholib.setAvailable(rs.getFloat("SOLUONGNHAP"));
				kholib.setMARQ("");
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				kholib.setBinId("0");
				kholib.setHamluong("100");
				kholib.setHamam("0");
				
				String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
			    if( msg1.length() >0)
				{
					this.msg = msg1;
					db.getConnection().rollback();
					return this.msg;
				}
				
				// TĂNG KHO SẢN XUÁT NGAY
					
			    
				
				query="select * from ERP_LENHSANXUAT_LOHOI  where LENHSANXUAT_FK="+this.solenhsanxuat+" and LOHOI_FK=(select LOHOI_FK from ERP_LENHSANXUAT_GIAY where PK_SEQ= "+this.solenhsanxuat+")";
				
				ResultSet rscheck1=db.get(query);
				if(rscheck1.next()){
					query=" update  ERP_LENHSANXUAT_LOHOI  set SOLUONG=SOLUONG+"+soluong+"  where LENHSANXUAT_FK="+this.solenhsanxuat+" and LOHOI_FK=(select LOHOI_FK from ERP_LENHSANXUAT_GIAY where PK_SEQ= "+this.solenhsanxuat+")";
					
				}else{
					query=	" INSERT INTO ERP_LENHSANXUAT_LOHOI (LENHSANXUAT_FK,LOHOI_FK,SOLUONG) " +
							" SELECT PK_SEQ,LOHOI_FK,"+soluong+" FROM ERP_LENHSANXUAT_GIAY WHERE PK_SEQ= "+this.solenhsanxuat;
					
				}
				rscheck1.close();
				if( db.updateReturnInt(query) !=1)
				{
					this.msg = "Không thể thực hiện cập nhật số lượng nhập cho lò hơi:lỗi dòng lệnh "+query;
					db.getConnection().rollback();
					return this.msg;
				}	
				
				
				/**
				 * Co kiem dinh thi luu san pham o trang thai cho kiem ---0 vao kho chitiet
				 * CÓ KIỂM ĐỊNH THÌ THÊM KIỂM ĐỊNH
				 */
				 //nếu có kiểm định và trên giao diện có check có kiểm định
				double soluonglaymau= rs.getDouble("SOLUONGLAYMAU");
				
				// đổi số lượng lấy mẫu về cùng với đơn vị của nhập kho,sau đó trừ đi lượng lấy mẫu để còn lại lượng ra phiếu kiểm đinh,vì lượng lấy mẫu đã chuyển qua kho mẫu
				// làm bước này vì đơn vị lấy mẫu khác với đơn vị trong phiếu nhập
				double soluonglaymau_dvchuan=soluonglaymau;

				System.out.println("soluonglaymau_dvchuan : "+soluonglaymau_dvchuan);
				
				double soluonglaymau_dv_nhap=0;
				
				query=" SELECT SP.DVDL_FK,QC.SOLUONG1,QC.SOLUONG2 FROM ERP_SANPHAM SP "+
				 	  " LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK="+rs.getString("DVDL_FK")+"  WHERE SP.PK_SEQ="+spId;
				
				ResultSet  rscheck=db.get(query);
				if(rscheck.next()){
					if(rscheck.getString("DVDL_FK").equals(rs.getString("DVDL_FK"))){
						soluonglaymau_dv_nhap=soluonglaymau_dvchuan;
					}else{
						soluonglaymau_dv_nhap= soluonglaymau_dvchuan *rscheck.getDouble("SOLUONG2")/rscheck.getDouble("SOLUONG1") ;
					}
				}else{
					this.msg = "2.Không thể xác định đơn vị  quy đổi của thành phẩm nhập kho ";
					db.getConnection().rollback();
					return this.msg;
				}
				soluonglaymau_dv_nhap =Double.parseDouble(formatter.format(soluonglaymau_dv_nhap).replaceAll(",","" ));
				
				
				System.out.println("kiem dinh : "+kiemdinhCL +" loai sanp ham "+ this.getLoaisanpham().trim());
				
				
				
				
				if(kiemdinhCL.equals("1")   && (soluong-soluonglaymau) >0)
				{
					
					
					query = " insert ERP_YeuCauKiemDinh(nhapkho_fk, sanpham_fk, soluong, SoLuongDat, solo, ngaysanxuat, ngayhethan, ngaykiem, trangthai," +
							" nguoitao, ngaytao,lenhsanxuat_fk,congdoan_fk,DinhLuong,DinhTinh,CongTy_FK,NguoiSua,NgaySua,SOLUONG_DVNHAP,DVDL_NHAP_FK,SOLUONGDAT_DVNHAP,IS_BOOKED,KYHIEUMAU,NGAYNHAPKHO ) " +
							" SELECT '" + this.id + "', '" + masanpham + "', " + (soluong-soluonglaymau) + ","+(soluong-soluonglaymau)+",'" + rs.getString("SoLo") +  
							" ', '" + ngaysanxuat + "', '" + ngayhethan + "', '" + ngaynhapkho + "', '0', '" + this.userId + "', '" + getDateTime() + "','"+solenhSX+"' " +
							" ,'"+this.CongDoanId+"','"+dinhluong+"','"+dinhtinh+"','"+this.congtyId+"','"+this.userId+"','"+getDateTime()+"' " +
							" ,"+(rs.getDouble("SOLUONGTRUOCQUYDOI")- soluonglaymau_dv_nhap) +","+rs.getString("DVDL_FK")+" " +
							" ,"+(rs.getDouble("SOLUONGTRUOCQUYDOI")-  soluonglaymau_dv_nhap) +",'1', " +
							" (SELECT TOP 1 ISNULL(KYHIEUMAU,'') AS KYHIEUMAU   FROM ERP_YEUCAUKIEMDINH WHERE SANPHAM_FK="+masanpham+" ORDER BY PK_SEQ DESC) ,'"+this.ngaynhapkho+"' ";
								
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh: " + query;
						rs.close();
						db.getConnection().rollback();
						return this.msg;
					}
					// set lại số lượng,booked, avai
					
					float  soluong_kd=Float.parseFloat(formatter.format(soluong-soluonglaymau));
					
					kholib.setBooked(soluong_kd);
					kholib.setSoluong(0);
					kholib.setAvailable(-1*soluong_kd);
					// booked ngay lượng hàng này, cả kho tổng và kho chi tiết để giữ cho kiểm định
					msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
				    if( msg1.length() >0)
					{
						this.msg = msg1;
						db.getConnection().rollback();
						return this.msg;
					}
				    
					  
					
				}else if(!kiemdinhCL.equals("1")    && loaisanpham.equals("100002")) {
					// nếu là thành phẩm và không phải kiểm định và không phải kho sản xuất công nghệ.thì chuyển kho về kho thành phẩm.
					// System.out.println("LOAI SNA PHAM  ______ ---------------: "+this.getLoaisanpham());
					// hiện tại không tự động tạo ra phiếu chuyển nữa, mà để người dùng tự chuyển bằng tay. 
					// đã thống nhất lại.
					
					String KhoNhanId=this.KHonhanTP;
					
					if(KhoNhanId.equals("")){
						this.msg = "Vui lòng chọn kho nhận thành phẩm không kiểm định";
						db.getConnection().rollback();
						return this.msg;
					}
					 
					 
				 
					// kho nhập hàng sản xuất giờ sẽ là kho chuyển
					
					//tạo ra phiếu chuyển kho trực tiếp
					   List<ISpDetail> spdetail_nhan = new ArrayList<ISpDetail>();
					 
					   ISpDetail sp=new SpDetail();
					   sp.setSolo(rs.getString("SoLo"));
					   float  soluong_kd=Float.parseFloat(formatter.format(soluong-soluonglaymau));
					   
					   sp.setSoluong((soluong_kd)+"");
					   sp.setMa(masanpham);
					   sp.setkhuid("0");
					   sp.setNgayHethan(ngayhethan);
					   sp.setNgaySx(ngaysanxuat);
					   sp.setNgaynhapkho(ngaynhapkho);
					   sp.setSothung("");
					   sp.setMame("");
					   sp.setMaphieu("");
					   sp.setMaphieudinhtinh("");
					   sp.setPhieuEO("");
					   sp.setMarq("");
					   sp.setHamAm("0");
					   sp.setHamluong("100");
					   
					   sp.setDongia(dongia);
					   sp.setCPLukho(0);
					   sp.setCapdong(0);
					   
					   spdetail_nhan.add(sp);
					   List<ISpDetail> spdetail_xuat = new ArrayList<ISpDetail>();
					   
					   ISpDetail spxuat =new SpDetail();
					   spxuat.setSolo(rs.getString("SoLo"));
					   
					   
					   spxuat.setSoluong((soluong_kd)+"");
					   spxuat.setMa(masanpham);
					  
					   
					   spxuat.setNgaynhapkho(ngaynhapkho);
					   
					   spxuat.setSothung("");
					   spxuat.setMame("");
					   spxuat.setMaphieu("");
					   spxuat.setMaphieudinhtinh("");
					   spxuat.setPhieuEO("");
					   spxuat.setMarq("");
					   spxuat.setHamAm("0");
					   spxuat.setHamluong("100");
					   spxuat.setkhuid("0"); 
					   	
					   spxuat.setNgayHethan(ngayhethan);
					   spxuat.setNgaySx(ngaysanxuat);
					   spxuat.setNgaynhapkho(ngaynhapkho);
					    
					   spdetail_xuat.add(spxuat);
					   
					   
				     msg1=this.createChuyenkho(this.ngaynhapkho , khonhap, KhoNhanId, spdetail_nhan,spdetail_xuat, "Chuyển kho hàng đạt của nhập kho "+this.id+" không qua kiểm định:" ,"1",false);
				     if(msg1.length() >0){
					   db.update("rollback");
					   this.msg=msg1;
					   return this.msg;
				    }
				}
				 
				
			}
			rs.close();
		
			if(i==0){
				this.msg = "Vui lòng chốt lại phiếu,nếu không được thì báo cho Admin để xử lý";
				db.getConnection().rollback();
				return this.msg;
			}
			
			query = "update ERP_NHAPKHO set trangthai = '1', giochot = '" + getTime() + "',  ngaysua = '" + ngaysua + "', nguoisua = '" + userId + "' where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_NHAPKHO: " + query;
				db.getConnection().rollback();
				return this.msg;
			
			}
			Library lib=new Library();
			
			String msg1=lib.capnhatketoan_Nhap_Kho_LSX(this.userId,this.db,this.id, false,this.congtyId);
			if(msg1.length()>0)
			{
				this.msg =msg1;
				db.getConnection().rollback();
				return this.msg;
			
			}
			 
		
		} 
		catch (Exception e) 
		{ 
			
			this.msg = "115.Exception : " + e.getMessage();
			e.printStackTrace();	
			db.update("rollback");
			return this.msg;
		}
		return this.msg;
	}


	private String createChuyenkho(String ngaychotnv,String khochuyen, String khonhan,
			List<ISpDetail> spdetail,List<ISpDetail> spdetail_xuat, String lydo,String trangthaisp, boolean isxacnhan ) {
	 
		 try{
			 //100064	XC06	Chuyển kho nội bộ
			Utility_Kho  util_kho =new Utility_Kho(); 
			 	
			String query = " insert ERP_CHUYENKHO( noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, trangthai, khoxuat_fk, khonhan_fk, trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua, NCC_CHUYEN_FK, NCC_NHAN_FK,NHAPKHO_FK ,ISHANGDIDUONG ) " +
				" values(100064, '" + ngaychotnv + "', '" + ngaychotnv + "', '" + ngaychotnv + "', N'"+lydo+"', 1 , '" + khochuyen + "', " + khonhan + ", '"+trangthaisp+"', " +
						" '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', NULL, NULL,  "+this.id+",'1' )";
			
			if(!db.update(query))
			{
				return  "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: " + query;
			}
		
		 
			String ckId = "";
			query = "select IDENT_CURRENT('ERP_CHUYENKHO') as ckId";
			ResultSet rsCk = db.get(query);
			if (rsCk.next())
			{
				ckId = rsCk.getString("ckId");
			} else {
				return "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: " + query;
			 
			}
			rsCk.close();
		 
			for(int i = 0; i < spdetail_xuat.size(); i++) {
				
				ISpDetail sp = spdetail_xuat.get(i);
				 
				query = " insert ERP_CHUYENKHO_SANPHAM(chuyenkho_fk, SANPHAM_FK, SOLUONGYEUCAU,SOLUONGXUAT,SOLUONGNHAN) " +
				"values( '" + ckId + "', '" + sp.getMa() + "', " + sp.getSoluong()+ "," + sp.getSoluong() + ","+sp.getSoluong()+" ) ";
		
				if(!db.update(query))
				{
					return  "Khong the tao moi ERP_CHUYENKHO_SANPHAM: " + query;
				}
				query=	" insert ERP_CHUYENKHO_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK,  solo, ngayhethan,  " +
						" ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, soluong,BIN_FK,PHIEUEO,MAPHIEUDINHTINH ) " +
						" VALUES ("+ckId+", "+sp.getMa()+",'"+sp.getSolo()+"','"+sp.getNgayHethan()+"','"+sp.getNgaynhapkho()+"','"+sp.getMame()+"' " +
				  		",'"+sp.getSothung()+"','"+sp.getMaphieu()+"' ,'"+sp.getMarq()+"','"+sp.getHamluong()+"','"+sp.getHamAm()+"' " +
				  		" ,"+sp.getSoluong()+",'"+sp.getkhuid()+"','"+sp.getPhieuEO()+"','"+sp.getMaphieudinhtinh()+"')";
				
				if(db.updateReturnInt(query)< 1)
				{
					return "Không thể cập nhật : " + query;
				}
				 
			    query=	"  SELECT AVAILABLE,sp.pk_seq,sp.ma+ ' - ' + sp.ten    as tensp FROM  ERP_KHOTT_SANPHAM  kho inner join erp_sanpham sp on sp.pk_seq=sanpham_fk " +
			    		"  WHERE KHOTT_FK="+khochuyen+" AND SANPHAM_FK="+sp.getMa()+"" ;
			    	
			    ResultSet rscheckkho=db.get(query);
			    double soluongton_=0;
			    String tensp="";
			   
			    if(rscheckkho.next()){
			    	soluongton_=rscheckkho.getDouble("AVAILABLE");
			    	tensp=rscheckkho.getString("pk_seq")+ "-" +rscheckkho.getString("tensp");
			    }
			    rscheckkho.close();
			    
			    if(soluongton_< Double.parseDouble(sp.getSoluong()) ){
			    	return " Không thể tạo chuyển kho cho sản phẩm [ "+tensp+" ]  số lượng chuyển : "+sp.getSoluong()+" ,do hàng ở kho sản xuất : "+soluongton_+" không còn đủ để chuyển, " +
			    		   " vui lòng kiểm tra báo cáo xuất nhập tồn chi tiết để biết rõ hàng đã chuyển từ chức năng nào để theo dõi ";
			    }
			    float soluongct=(-1)*Float.parseFloat(sp.getSoluong());
			    float booked=0;
			    float available=(-1)*Float.parseFloat(sp.getSoluong());
			     
			    
				Kho_Lib kholib=new Kho_Lib();
				kholib.setLoaichungtu("ERPNHAPKHO 2543 ERP_CHUYENKHO : "+ckId);
				kholib.setNgaychungtu(ngaychotnv);
				kholib.setKhottId(khochuyen);
				kholib.setBinId("0");
				kholib.setSolo(sp.getSolo());
				kholib.setSanphamId(sp.getMa());
				kholib.setMame(sp.getMame());
				kholib.setMathung(sp.getSothung());
				kholib.setMaphieu(sp.getMaphieu());
				kholib.setMaphieudinhtinh(sp.getMaphieudinhtinh());
				kholib.setPhieuEo(sp.getPhieuEO());
				kholib.setNgayhethan(sp.getNgayHethan());
				kholib.setNgaysanxuat(sp.getNgaySx());
				kholib.setNgaynhapkho(sp.getNgaynhapkho());
				
				kholib.setBooked(booked);
				kholib.setSoluong(soluongct);
				kholib.setAvailable(available);
				kholib.setMARQ(sp.getMarq());
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				kholib.setBinId(sp.getkhuid());
				kholib.setDongialo(0);
				kholib.setHamluong(sp.getHamluong());
				kholib.setHamam(sp.getHamAm());
				
				String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
			    if( msg1.length() >0)
				{
					this.msg = msg1;
					db.getConnection().rollback();
					return this.msg;
				}
		 
			 }
			
			//GHI NHAN NHAN HANG
			query = "  insert ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG( chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  	MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, soluong		 )  " + 
					"  select chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  		MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, " + 
					"  		case when DT2.col1 = 1 then DT.soluong else 0 end as soluong  " + 
					"  from " + 
					"  ( " + 
					"  	select chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  		MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, SUM(soluong) as soluong " + 
					"  	from ERP_CHUYENKHO_SANPHAM_CHITIET  " + 
					"  	where chuyenkho_fk = '" + ckId + "' and chuyenkho_fk in ( select pk_seq from ERP_CHUYENKHO where pk_seq = '" + ckId + "' and khonhan_fk is not null )  " + 
					"  	group by chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  				MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH " + 
					"  ) " + 
					"  DT, ( select 1 as col1 union select 2 union select 3 union select 4 union select 5 ) DT2 " + 
					"  order by DT.SANPHAM_FK asc, DT.soluong desc ";
			if( !db.update(query) )
			{
				this.msg = "Không thể cập nhật nhận hàng: "+query;
				db.getConnection().rollback();
				return this.msg;
			}
			
			 
		 }catch(Exception er){
			 er.printStackTrace();
			 return er.getMessage();
		 }
		 return "";
	}
 
	

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}


	public void setCongDoanId(String ctidcurrent) {

		this.CongDoanId=ctidcurrent;
	}


	public String GetCongDoanId() {

		return this.CongDoanId;
	}

	
	public ResultSet getRsCongDoan()
	{
	
		return this.rsCongdoan;
	}

	
	public void setRsCongDoan(ResultSet rscongdoan)
	{
		this.rsCongdoan=rscongdoan;
		
	}

	
	public ResultSet getRsLenhSanXuat()
	{
	
		return this.rsLenhsanxuat;
	}

	
	public void setRsLenhSanXuat(ResultSet rsLenhsanxuat)
	{
		this.rsLenhsanxuat=rsLenhsanxuat;
		
	}

	
	public boolean HuyNhapKhoLsx()
	{ 
		return false;
		
	}

	public boolean CheckNghiepVu()
	{
		String query=" select count(*) from erp_lenhsanxuat_congdoan_giay where lenhsanxuat_fk="+this.solenhsanxuat+"  and tinhtrang=1 "+
		" and thutu > (select thutu  from erp_lenhsanxuat_congdoan_giay where lenhsanxuat_fk="+this.solenhsanxuat+" and congdoan_fk="+this.CongDoanId+" ) ";
		ResultSet rs =this.db.get(query);
			try
			{
				if(rs!=null)
				while(rs.next())
				if(rs.getInt(1)>0)
				{
					this.msg="Đã có công đoạn sản xuất hoàn tất trong lệnh sản xuất,bạn phải hủy các nghiệp vụ của công đoạn tiếp theo trước khi xóa nhập kho !";
					return false;
				}rs.close();
				System.out.println("Check cong doan trong lenh san xuat "+query);
			
			} catch (SQLException e)
			{
				e.printStackTrace();
				return false;
			}
			return true;
	}

	@Override
	public String getDvdkId() {
		// TODO Auto-generated method stub
		return this.DvkdId;
	}

	@Override
	public void setDvdkId(String _DvdkId) {
		// TODO Auto-generated method stub
		this.DvkdId=_DvdkId;
	}

	@Override
	public void setIsQLKV(String value) {
		this.isQLKV = value;
	}

	 

	@Override
	public String getIsLsxCongNghe() {
		// TODO Auto-generated method stub
		return this.IsLsxCongNghe;
	}

	@Override
	public void setIsLsxCongNghe(String IsLsxCongNghe) {
		// TODO Auto-generated method stub
		this.IsLsxCongNghe=IsLsxCongNghe;
	}

	@Override
	public String getKhongkiemdinh() {
		// TODO Auto-generated method stub
		return this.Khongkiemdinh;
	}

	@Override
	public void setKhongkiemdinh(String kokiemdinh) {
		// TODO Auto-generated method stub
		this.Khongkiemdinh=kokiemdinh;
	}

	@Override
	public String getLoaisanpham() {
		// TODO Auto-generated method stub
		return this.LoaisanphamId;
	}

	@Override
	public void setLoaisanpham(String loaisanpham) {
		// TODO Auto-generated method stub
		this.LoaisanphamId=loaisanpham;
	}

	@Override
	public ResultSet getRsLoaisanpham() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRsLoaisanpham(ResultSet rs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResultSet getRsBTP() {
		// TODO Auto-generated method stub
		return this.RsBTP;
	}

	@Override
	public void setRsBTP(ResultSet rs) {
		// TODO Auto-generated method stub
		this.RsBTP=rs;
	}

	@Override
	public String getBTPId() {
		// TODO Auto-generated method stub
		return this.BtpId;
	}

	@Override
	public void setBTPId(String BTPId) {
		// TODO Auto-generated method stub
		this.BtpId=BTPId;
	}

	@Override
	public String getDonViTinh() {
		// TODO Auto-generated method stub
		return this.Donvitinh;
	}

	@Override
	public void setDonViTinh(String donvitinh) {
		// TODO Auto-generated method stub
		this.Donvitinh=donvitinh;
	}

	public void setNguoiTao(String nguoiTao) {
		this.nguoiTao = nguoiTao;
	}

	public String getNguoiTao() {
		return nguoiTao;
	}

	@Override
	public void setKhoNhanTP(String khonhanTP) {
		// TODO Auto-generated method stub
		this.KHonhanTP=khonhanTP;
	}

	@Override
	public String getKhoNhanTP() {
		// TODO Auto-generated method stub
		return KHonhanTP;
	}
}
