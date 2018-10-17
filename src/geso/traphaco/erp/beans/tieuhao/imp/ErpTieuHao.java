package geso.traphaco.erp.beans.tieuhao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
  
import geso.dms.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.Kho_Lib;
import geso.traphaco.erp.util.Library;
 
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;
import geso.traphaco.erp.beans.tieuhao.*;

public class ErpTieuHao implements IErpTieuHao
{
	String congtyId;
	String userId;
	String id;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String nhanhangId;
	String sanphamId;
	String sanphamMa;
	String sanphamTen;
	String soluong;
	String trangthai;
	String manhanhang;
	String muahangid;
	
	String ghichu;
	
	String NgayChot;
	String ngayTieuHao;
 
	String nccid="";
	
	List<ISanpham> spList;

	String msg;
	
	dbutils db;
	
	String Khotieuhao;
	Utility_Kho util_kho=new Utility_Kho();
	
	String soMuaHang;
	String ghiChuMuaHang;
	
	public ErpTieuHao()
	{
		this.id = "";
		this.congtyId = "";
		this.userId = "";
		this.ngayTieuHao="";
		this.NgayChot="";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		
		this.nhanhangId = "";
		this.sanphamId = "";
		this.sanphamTen = "";
		this.soluong = "0";
		this.manhanhang= "";
		this.muahangid="NULL";
				
		this.trangthai = "";
		
		this.msg = "";
		this.ghichu = "";
		this.soMuaHang ="";
		this.ghiChuMuaHang="";

		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
		
	}
	
	public ErpTieuHao(boolean kokhoitaodb)
	{
		this.id = "";
		this.congtyId = "";
		this.userId = "";
		this.ngayTieuHao=this.getDateTime();
		this.NgayChot=this.getDateTime();
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		
		this.nhanhangId = "";
		this.sanphamId = "";
		this.sanphamTen = "";
		this.soluong = "0";
		this.manhanhang= "";
		
		this.trangthai = "";
		
		this.msg = "";
		this.ghichu = "";
		this.soMuaHang ="";
		this.ghiChuMuaHang="";

		this.spList = new ArrayList<ISanpham>();
		
	}
	
	public ErpTieuHao(String id)
	{
		this.id = id;
		this.congtyId = "";
		this.userId = "";
		this.ngayTieuHao="";
		this.NgayChot="";
		
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.manhanhang= "";
		
		this.nhanhangId = "";
		this.sanphamId = "";
		this.sanphamTen = "";
		this.soluong = "0";
		
		this.trangthai = "";
		
		this.msg = "";
		this.ghichu = "";
		this.soMuaHang ="";
		this.ghiChuMuaHang="";

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
	
	public String getGhichu()
	{
		return this.ghichu;
	}
	
	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getManhanhang()
	{
		return this.manhanhang;
	}
	
	public void setManhanhang(String manhanhang)
	{
		this.manhanhang = manhanhang;
	}
	
	public String getMsg()
	{
		return this.msg;
	}
	
	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	
	public String getCongtyId() {		
		return this.congtyId;
	}

	
	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	
	public String getNgayTao() {
		return this.ngaytao;
	}

	
	public void setNgayTao(String ngaytao) {
		this.ngaytao = ngaytao;
	}

	
	public String getNgaySua() {
		return this.ngaysua;
	}

	
	public void setNgaySua(String ngaysua) {
		this.ngaysua = ngaysua;
	}

	
	public String getNguoiTao() {
		return nguoitao;
	}

	
	public void setNguoiTao(String nguoitao) {
		this.nguoitao = nguoitao;
	}

	
	public String getNguoiSua() {
		return this.nguoisua;
	}

	
	public void setNguoiSua(String nguoisua) {
		this.nguoisua = nguoisua;
	}

	
	public String getNhanHangId() {
		return nhanhangId;
	}

	
	public void setNhanHangId(String nhanhangId) {
		this.nhanhangId = nhanhangId;
	}

	
	public String getSanphamId() {
		return sanphamId;
	}

	
	public void setSanphamId(String spId) {
		this.sanphamId = spId;
	}

	
	public String getSanphamMa() {
		return sanphamMa;
	}

	
	public void setSanphamMa(String sanphamMa) {
		this.sanphamMa = sanphamMa;
	}

	
	public String getSanphamTen() {
		return sanphamTen;
	}

	
	public void setSanphamTen(String sanphamTen) {
		this.sanphamTen = sanphamTen;
	}

	
	public String getSoLuong() {
		return this.soluong;
	}

	
	public void setSoLuong(String soluong) {
		this.soluong = soluong;
	}

	
	public List<ISanpham> getSpList() {
		return this.spList;
	}

	
	public void setSpList(List<ISanpham> spList) {
		this.spList = spList;
	}

	
	public String getTrangthai() {
		return this.trangthai;
	}

	
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}


	public boolean create() {
		
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = 
				"INSERT INTO ERP_TIEUHAO(CONGTY_FK, NHANHANG_FK, SANPHAM_FK, SOLUONG, TRANGTHAI, NGAYSUA, NGAYTAO, NGUOITAO, NGUOISUA) " +
				"SELECT '"+this.congtyId+"', '"+this.nhanhangId+"', '"+this.sanphamId+"', '"+this.soluong+"', '"+this.trangthai+"', '"+this.ngaysua+"', '"+this.ngaytao+"', '"+this.nguoitao+"', '"+this.nguoisua+"'";
			//System.out.println("[ErpTieuHao.create] insert query = " + query);
			
			if(!db.update(query)) {
				//System.out.println("[ErpTieuHao.create] --> That bai");
				db.getConnection().rollback();
				msg = "Không thể tạo phiếu tiêu hao!";
				return false;
			}
			//System.out.println("[ErpTieuHao.create] --> Thanh cong");
			
			query = "SELECT SCOPE_IDENTITY() AS ID";
			ResultSet rs = this.db.get(query);
			rs.next();
			this.id = rs.getString("ID");
			
			//Insert vat tu
			for(int i = 0; i < spList.size(); i++) {
				ISanpham vattu = spList.get(i);
				
				query = " INSERT ERP_TIEUHAO_VATTU(TIEUHAO_FK, VATTU_FK, SOLUONGCHUAN, SOLUONGTHUCTE) " +
						" SELECT '" + this.id + "', '" + vattu.getId() + "', '" + vattu.getSoLuongChuan() + "', '" + vattu.getSoLuongThucTe() + "' ";
				//System.out.println("[ErpTieuHao.create] vat tu ["+i+"] insert query = " + query);

				if(!db.update(query)) {
					//System.out.println("[ErpTieuHao.create] --> That bai");
					db.getConnection().rollback();
					msg = "Không thể lưu vật tư "+vattu.getId()+" cho phiếu tiêu hao!";
					return false;
				}
				//System.out.println("[ErpTieuHao.create] --> Thanh cong");
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Xảy ra lỗi trong quá trình tạo phiếu tiêu hao! (" + e.getMessage() + ")";
			return false;
		}
	}

	
	
	
	
	public boolean create(geso.traphaco.distributor.db.sql.dbutils db) {
		
		try {
			
			String query = 
				" INSERT INTO ERP_TIEUHAO(MUAHANG_FK,CONGTY_FK, NHANHANG_FK, SANPHAM_FK, SOLUONG, TRANGTHAI, NGAYSUA, NGAYTAO, NGUOITAO, NGUOISUA,NGAYTIEUHAO,NGAYCHOT) " +
				" SELECT "+this.muahangid+",'"+this.congtyId+"', '"+this.nhanhangId+"', '"+this.sanphamId+"', '"+this.soluong+"', '"+this.trangthai+"', "
				 + " '"+this.ngaysua+"', '"+this.ngaytao+"', '"+this.nguoitao+"', '"+this.nguoisua+"', '"+this.ngayTieuHao+"','"+this.NgayChot+"'";
			
			if(!db.update(query)) {
				msg = "Không thể tạo phiếu tiêu hao!";
				return false;
			}
			
			query = "SELECT SCOPE_IDENTITY() AS ID";
			ResultSet rs =  db.get(query);
			rs.next();
			this.id = rs.getString("ID");
			
			//Insert vat tu
			for(int i = 0; i < spList.size(); i++) {
				ISanpham vattu = spList.get(i);
				
				query = " INSERT ERP_TIEUHAO_VATTU(TIEUHAO_FK, VATTU_FK, SOLUONGCHUAN, SOLUONGTHUCTE) " +
						" SELECT '" + this.id + "', '" + vattu.getId() + "', " + vattu.getSoLuongChuan() + ", " + vattu.getSoLuongThucTe() + " ";

				if(!db.update(query)) {
					msg = "Không thể lưu vật tư "+vattu.getId()+" cho phiếu tiêu hao!";
					return false;
				}
			}

			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Xảy ra lỗi trong quá trình tạo phiếu tiêu hao! (" + e.getMessage() + ")";
			return false;
		}
	}

	
	public boolean update() {
		try {
			
			if(spList.size() ==0){
				msg = "Chưa có sản phẩm tiêu hao,vui lòng kiểm tra lại ";
				return false;
			}
			if(this.checkkiemtra()){
				return false;
			}
			if(this.checkNgayGhiNhan())
			{
				return false;
			}
			
				
			db.getConnection().setAutoCommit(false);
			
			String query= " select HAMAM,HAMLUONG, NGAYNHAPKHO,NGAYHETHAN ,ISNULL(MARQ,'') AS MARQ  , isnull(cast(bin_fk as nvarchar(12)),'0') as bin_fk  ,isnull(mathung,'') as mathung ,isnull(mame,'') as mame " +
						  "	, isnull(maphieu,'') as maphieu ,isnull(phieueo,'') as phieueo ,isnull(maphieudinhtinh,'') as maphieudinhtinh " +
						  " ,VATTU_FK,SOLO,A.SOLUONG,TH.KHOTT_FK,TH.NCC_FK  ,isnull(cast(A.IDMARQUETTE as varchar(12) ),'') as IDMARQUETTE " +
						  " , isnull(cast(A.KHUVUCKHO_FK as varchar(12) ),'') as KHUVUCKHO_FK "+ 
						  " from  ERP_TIEUHAO_VATTU_CHITIET  a inner join ERP_TIEUHAO th on th.PK_SEQ=a.TIEUHAO_FK "+ 
						  " where th.PK_SEQ="+this.id;
			
			ResultSet rs=db.get(query);
			while (rs.next()) {
				
				double soluonct=0;
				double available=rs.getDouble("soluong");
				double booked=rs.getDouble("soluong")*(-1);
				
				String spid=rs.getString("vattu_fk");
				String ncc_fk=rs.getString("ncc_fk");
				String khott_fk=rs.getString("khott_fk");
				String solo=rs.getString("solo").trim();
				String maphieu=rs.getString("maphieu");
				String mathung=rs.getString("mathung");
				String mame=rs.getString("mame");
				String maphieudinhtinh=rs.getString("maphieudinhtinh");
				String phieueo=rs.getString("phieueo");
				String bin_fk=rs.getString("bin_fk");
				String ngayhethan=rs.getString("ngayhethan");
				String ngaynhapkho=rs.getString("ngaynhapkho");
				String MARQ =rs.getString("MARQ");
				String hamam=rs.getString("hamam");
				String hamluong=rs.getString("hamluong");
				
				Kho_Lib kholib=new Kho_Lib();
				
				kholib.setLoaichungtu("   :  ERP_TIEUHAO gia cong  :  ERP_TIEUHAO"+ this.id);
				kholib.setNgaychungtu(this.getNgaytieuhao());
				kholib.setKhottId(khott_fk);
				kholib.setBinId(bin_fk);
				kholib.setSolo(solo);
				kholib.setSanphamId(spid);
				kholib.setMame(mame);
				kholib.setMathung(mathung);
				kholib.setMaphieu(maphieu);
				kholib.setMaphieudinhtinh(maphieudinhtinh);
				kholib.setPhieuEo(phieueo);
				kholib.setNgayhethan(ngayhethan);
				kholib.setNgaysanxuat("");
				kholib.setNgaynhapkho(ngaynhapkho);
				kholib.setMARQ(MARQ);
				kholib.setDoituongId(ncc_fk);
				kholib.setLoaidoituong("0");
				kholib.setHamluong(""+hamluong);
				kholib.setHamam(""+hamam);
				 
		    	kholib.setBooked(booked);
				kholib.setSoluong(soluonct);
				kholib.setAvailable(available);
			     
				
				
				String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
			    if( msg1.length() >0)
				{
					// this.msg = msg1;
			    	this.msg = msg1;
					db.getConnection().rollback();
					return false;
					
				}
			    
			}
 
			query = " select a.NGAYCHOT, c.LOAINHACUNGCAP_FK, khoNL_Nho_GC, b.nhacungcap_fk  "+
					" from ERP_NHANHANG a left join ERP_MUAHANG b on a.MUAHANG_FK = b.PK_SEQ   "+
					" left join ERP_NHACUNGCAP c on b.NHACUNGCAP_FK = c.PK_SEQ  "+
					" where a.pk_seq = (select NHANHANG_FK from ERP_TIEUHAO where pk_seq= "+this.id+") ";

			System.out.println(query);
			
			String loaiNCC = "";
			String khoNL_Nho_GC = "100055";
			String nccId = "";
		 
			
			ResultSet rskho = db.get(query);
			 	if(rskho.next())
				{
  					 
					nccId = rskho.getString("nhacungcap_fk") == null ? "" : rskho.getString("nhacungcap_fk");
					this.nccid=nccId;
					
					this.Khotieuhao=khoNL_Nho_GC;
				}
			 	rskho.close();
			 	
			
			
			  query = "delete ERP_TIEUHAO_VATTU where tieuhao_fk="+this.id;
			if(!db.update(query)) {
				 
				db.getConnection().rollback();
				msg = " Lỗi câu lệnh : "+query;
				return false;
			}
			
			  query = "delete ERP_TIEUHAO_VATTU_CHITIET where tieuhao_fk="+this.id;
			if(!db.update(query)) {
				 
				db.getConnection().rollback();
				msg = " Lỗi câu lệnh : "+query;
				return false;
			}
			
			
			for(int i = 0; i < spList.size(); i++) {
				ISanpham vattu = spList.get(i);
				
				query = " INSERT INTO ERP_TIEUHAO_VATTU (TIEUHAO_FK,VATTU_FK,SOLUONGCHUAN,SOLUONGTHUCTE) "+
						" VALUES ("+this.id+","+vattu.getId()+","+vattu.getSoLuongChuan()+","+vattu.getSoLuongThucTe()+")";
				if(!db.update(query)) {
				 
					db.getConnection().rollback();
					msg = "Không thể cập nhật số lượng cho vật tư "+vattu.getId()+" cho phiếu tiêu hao này!";
					return false;
				}
				////System.out.println("[ErpTieuHao.create] --> Thanh cong");
				
				List<ISanphamLo> listlo=vattu.getSpList();
				
				for(int k=0;k<listlo.size();k++){
					ISanphamLo splo=listlo.get(k);
					double soluonct=0;
					double available=splo.getSoLuong()*(-1);
					double booked=splo.getSoLuong();
					String spid=vattu.getId();
					String ncc_fk=nccId;
					String khott_fk=Khotieuhao;
					String solo=splo.getsolo();
					 
					query = " INSERT INTO ERP_TIEUHAO_VATTU_CHITIET (TIEUHAO_FK,VATTU_FK,SOLUONG,SOLO,DONGIA,THANHTIEN,IDMARQUETTE,KHUVUCKHO_FK , " +
							" MATHUNG,MAME,MAPHIEU,MAPHIEUDINHTINH,PHIEUEO,BIN_FK,NGAYNHAPKHO,NGAYHETHAN,HAMLUONG,HAMAM,MARQ ) "+
							" VALUES ("+this.id+","+vattu.getId()+","+splo.getSoLuong()+",'"+splo.getsolo()+"',0,0,"+(splo.getIDMARQUETTE().length()>0?splo.getIDMARQUETTE():"NULL")+","+(splo.getKHUVUCKHO_FK().length()>0?splo.getKHUVUCKHO_FK():"NULL")+",'"+splo.getMathung()+"','"+splo.getMame()+"'," +
							" '"+splo.getMaphieu()+"','"+splo.getMAPHIEUDINHTINH()+"','"+splo.getPHIEUEO()+"',"+(splo.getVitriId().length()>0?splo.getVitriId():"0")+",'"+splo.getNgaynhapkho()+"','"+splo.getNgayhethan()+"',"+splo.getHamluong()+","+splo.getHamam()+",'"+splo.getMARQ()+"' )";
					if(!db.update(query)) {
						db.getConnection().rollback();
						msg = "Không thể cập nhật số lượng cho vật tư "+vattu.getId()+" cho phiếu tiêu hao này!";
						return false;
					}
					
					Kho_Lib kholib=new Kho_Lib();
					 
					kholib.setLoaichungtu("   :  ERP_TIEUHAO gia cong  :  ERP_TIEUHAO"+ this.id);
					
					kholib.setNgaychungtu(this.getNgaytieuhao());
					 
					kholib.setKhottId(khott_fk);
					
					kholib.setBinId((splo.getVitriId().length()>0?splo.getVitriId():"0"));
				 
					
					kholib.setSolo(solo);
				 
					kholib.setSanphamId(spid);
					kholib.setMame(splo.getMame());
					kholib.setMathung(splo.getMathung());
					kholib.setMaphieu(splo.getMaphieu());
					kholib.setMaphieudinhtinh(splo.getMAPHIEUDINHTINH());
					kholib.setPhieuEo(splo.getPHIEUEO());
					
					kholib.setNgayhethan(splo.getNgayhethan());
					kholib.setNgaysanxuat("");
					kholib.setNgaynhapkho(splo.getNgaynhapkho());
					kholib.setMARQ(splo.getMARQ());
					kholib.setDoituongId(ncc_fk);
					kholib.setLoaidoituong("0");
					 
					kholib.setHamluong(""+splo.getHamluong());
					kholib.setHamam(""+splo.getHamam());
			    	kholib.setBooked(booked);
					kholib.setSoluong(soluonct);
					kholib.setAvailable(available);
				    
					String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
				    if( msg1.length() >0)
					{
						// this.msg = msg1;
				    	this.msg = msg1;
						db.getConnection().rollback();
						return false;
					}
				  
				}
				 
				
			}
			
			query="Update erp_tieuhao set GHICHU = N'"+ this.ghichu +"', ISBOOKTIEUHAO=1 ,NGAYTIEUHAO='"+this.ngayTieuHao+"',Ngaychot='"+this.NgayChot+"',NGAYHETHONG=getdate(), KHOTT_FK="+this.Khotieuhao+" , NCC_FK= " +this.nccid+",ngaysua='"+this.getDateTime()+"',nguoisua="+this.userId+" where pk_seq="+this.id;
			
			if(!db.update(query)) {
				msg = " Lỗi câu lệnh : "+query;
				db.getConnection().rollback();
				return false;
			}
			 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true); 
			return true;
			
		} catch (Exception e) {
			db.update("rollback");
			e.printStackTrace();
			msg = "Xảy ra lỗi trong quá trình cập nhật phiếu tiêu hao! (" + e.getMessage() + ")";
			return false;
		}
	}
	
	private boolean checkNgayGhiNhan() {
		String[] param = this.NgayChot.split("-");
		int namchot = Integer.parseInt(param[0]);
		int thangchot = Integer.parseInt(param[1]);
		String query =  " select  TOP 1 THANGKS, NAM from ERP_KHOASOTHANG order by NAM desc , THANGKS desc ";
		ResultSet rs= db.get(query);
		int nam = 0;
		int thang = 0;
		try {
			while (rs.next())
			{
				nam = Integer.parseInt(rs.getString("NAM"));
				thang =  Integer.parseInt(rs.getString("THANGKS"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(namchot < nam)
		{
			this.msg="Ngày ghi nhận phải trong tháng chưa khóa sổ";
			return true;
		}
		else if(namchot == nam && thangchot <= thang )
		{
			this.msg="Ngày ghi nhận phải trong tháng chưa khóa sổ";
			return true;
		}

		
		return false;
	}

	private boolean checkkiemtra() {
		// TODO Auto-generated method stub
		for(int i = 0; i < spList.size(); i++) {
			ISanpham vattu = spList.get(i);
 
			double soluongtong= vattu.getSoLuongThucTe();
			
			List<ISanphamLo> listlo=vattu.getSpList();
			
			double soluongtongnew=0;
			
			for(int k=0;k<listlo.size();k++){
				ISanphamLo splo=listlo.get(k);
				  soluongtongnew=soluongtongnew+splo.getSoLuong();
			}
			 
			if(soluongtong-soluongtongnew != 0 ){
				this.msg="Vui lòng kiểm tra lại sản phẩm :"+vattu.getMa()+".Số lượng tiêu hao chi tiết không bằng với số lượng tổng tiêu hao";
				return true;
			}
		}
		return false;
	}

	public String chot() {
		
		if(this.id == null || this.id.trim().length() == 0) {
			msg = "Mã phiếu tiêu hao rỗng";
		}
 
		try 
		{
			 
			db.getConnection().setAutoCommit(false);
			
			String query= " select HAMAM,HAMLUONG, NGAYNHAPKHO,NGAYHETHAN ,MARQ , isnull(cast(bin_fk as nvarchar(12)),'0') as bin_fk "
					+ " ,isnull(mathung,'') as mathung ,isnull(mame,'') as mame " +
						  "	, isnull(maphieu,'') as maphieu ,isnull(phieueo,'') as phieueo ,isnull(maphieudinhtinh,'') as maphieudinhtinh " +
						  " ,VATTU_FK,SOLO,A.SOLUONG,TH.KHOTT_FK,TH.NCC_FK  ,isnull(cast(A.IDMARQUETTE as varchar(12) ),'') as IDMARQUETTE " +
						  " , isnull(cast(A.KHUVUCKHO_FK as varchar(12) ),'') as KHUVUCKHO_FK "+ 
						  " from  ERP_TIEUHAO_VATTU_CHITIET  a inner join ERP_TIEUHAO th on th.PK_SEQ=a.TIEUHAO_FK "+ 
						  " where th.PK_SEQ="+this.id;

				ResultSet rs=db.get(query);
				while (rs.next()) {
				
					double soluonct= rs.getDouble("soluong");
					double available=0;
					double booked= rs.getDouble("soluong")*(-1);
					
					String spid=rs.getString("vattu_fk");
					String ncc_fk=rs.getString("ncc_fk");
					String khott_fk=rs.getString("khott_fk");
					String solo=rs.getString("solo").trim();
					String maphieu=rs.getString("maphieu");
					String mathung=rs.getString("mathung");
					String mame=rs.getString("mame");
					String maphieudinhtinh=rs.getString("maphieudinhtinh");
					String phieueo=rs.getString("phieueo");
					String bin_fk=rs.getString("bin_fk");
					String ngayhethan=rs.getString("ngayhethan");
					String ngaynhapkho=rs.getString("ngaynhapkho");
					String MARQ =rs.getString("MARQ");
					String hamam=rs.getString("hamam");
					String hamluong=rs.getString("hamluong");
					
					Kho_Lib kholib=new Kho_Lib();
					
					kholib.setLoaichungtu("   :  ERP_TIEUHAO gia cong  :  ERP_TIEUHAO"+ this.id);
					kholib.setNgaychungtu(this.getNgaytieuhao());
					kholib.setKhottId(khott_fk);
					kholib.setBinId(bin_fk);
					kholib.setSolo(solo);
					kholib.setSanphamId(spid);
					kholib.setMame(mame);
					kholib.setMathung(mathung);
					kholib.setMaphieu(maphieu);
					kholib.setMaphieudinhtinh(maphieudinhtinh);
					kholib.setPhieuEo(phieueo);
					kholib.setNgayhethan(ngayhethan);
					kholib.setNgaysanxuat("");
					kholib.setNgaynhapkho(ngaynhapkho);
					kholib.setMARQ(MARQ);
					kholib.setDoituongId(ncc_fk);
					kholib.setLoaidoituong("0");
					kholib.setHamluong(""+hamluong);
					kholib.setHamam(""+hamam);
					kholib.setBooked(booked);
					kholib.setSoluong(soluonct);
					kholib.setAvailable(available);
					String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
					if( msg1.length() >0)
					{
			  			this.msg = msg1;
					  	db.getConnection().rollback();
						return "Không thể chốt tiêu hao .Lỗi: "+msg1;
					}
				  
				}
 
			
				query = "update ERP_TIEUHAO set trangthai = '1',ngayhethong=getdate() where pk_seq = '" + this.id + "' and trangthai = 0";
				 
				if(db.updateReturnInt(query)!=1) {
					 
					db.getConnection().rollback();
					return "Không thể cập nhật trạng thái đã chốt cho phiếu " + this.id;
				}
				
				
				  query =	 "  select isnull(b.HOANTAT_NHANHANG,0) as HOANTAT_NHANHANG  ,b.pk_seq as MUAHANG_FK ,A.PK_SEQ AS NHANHANGID, a.NGAYCHOT, c.LOAINHACUNGCAP_FK, khoNL_Nho_GC, b.nhacungcap_fk , d.khachhang_fk " +
							 "  from ERP_NHANHANG a INNER JOIN ERP_NHANHANG_sANPHAM NHSP ON A.PK_SEQ=NHSP.NHANHANG_FK "
							 + " left join ERP_MUAHANG b on NHSP.MUAHANG_FK = b.PK_SEQ " +
							 "	left join ERP_NHACUNGCAP c on b.NHACUNGCAP_FK = c.PK_SEQ" +
							 "  left join DonTraHang d on a.trahang_fk = d.pk_seq "
							 + " inner join erp_tieuhao th on th.nhanhang_fk=nhsp.nhanhang_fk and nhsp.sanpham_fk= th.sanpham_fk   " +
							 "  where th.pk_seq="+this.id;
				  
			//System.out.println("Nha gia cong :"+sql);
			String ngaychotNV = "";
			String loaiNCC = "";
			// có 1 kho gia cONG
			String khoNL_Nho_GC = "100055";
			String nccId = "";
			String khId = "";
			String MUAHANG_FK="";
			String HOANTAT_NHANHANG="0";
			ResultSet rskho = db.get(query);
			 	if(rskho.next())
				{
				 
			 		MUAHANG_FK= rskho.getString("MUAHANG_FK");
			 		
					if( rskho.getString("HOANTAT_NHANHANG").equals("1") && this.checktieuhao_cuoi(MUAHANG_FK)){
						HOANTAT_NHANHANG= rskho.getString("HOANTAT_NHANHANG");
					}
					
				} 
			 	rskho.close();
				
				
				if(HOANTAT_NHANHANG.equals("1")){
					// hoàn tât nhận hàng và tiêu hao xong thì hoàn tất luôn mua hàng
					query="UPDATE ERP_MUAHANG SET TRANGTHAI=2 WHERE PK_SEQ="+MUAHANG_FK;
					if(!db.update(query)){
						db.getConnection().rollback();
						return "Không thể hoàn tất mua hàng: "+query;
						
					}
				}
				
				Library lib =new Library();
				
				String msg1=lib.capnhatketoan_Xuat_Tieuhao_Giacong(this.userId, db, this.id, false,this.congtyId);
				if(msg1.length()>0){
					db.getConnection().rollback();
					return msg1;
				}
			 
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			try{
				db.getConnection().rollback();
			}catch(Exception  err ){
				err.printStackTrace();
			}
			msg = "Xảy ra lỗi khi chốt phiếu tiêu hao!" + e.getMessage();
			return msg;
		}
 
		return msg;
	}
	 
	private String TaoChuyenKho(){
		try{
			
			Utility util=new Utility();
			
			String NgayChotNV="";
			String sql="select pk_seq,ngaykiem from ERP_YEUCAUKIEMDINH where trangthai=1  and nhanhang_fk= (select nhanhang_fk from erp_tieuhao where pk_seq="+this.id+")";
			String ycId="";
			ResultSet rs=db.get(sql);
			if(rs.next()){
				ycId=rs.getString("pk_seq");
				NgayChotNV=rs.getString("ngaykiem");
				
			}else{
				return "Không xác định được yêu cầu kiểm định của tiêu hao này ";
			}
			
			/*String msg2=util.CheckNgayGhiNhanHopLe_Provence(db, NgayChotNV);
			if(msg2.length()>0){
				return msg2;
			}*/
			
			  sql= 		" SELECT DISTINCT NHSP.KHONHAN "+
						" FROM ERP_YEUCAUKIEMDINH_SANPHAM A "+
						" INNER JOIN ERP_YEUCAUKIEMDINH YCKD ON YCKD.PK_SEQ=A.YCKD_FK "+
						" INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ=YCKD.NHANHANG_FK "+
						" INNER JOIN ERP_NHANHANG_SANPHAM NHSP  ON NH.PK_SEQ=NHSP.NHANHANG_FK AND A.SANPHAM_FK=NHSP.SANPHAM_FK "+
						" WHERE YCKD_FK="+ycId;
		 
			  rs=db.get(sql);
			while (rs.next()){
				String khotam_fk=rs.getString("KHONHAN");
				String khott_fk = "";
				 
				String khochoxuly_fk = "";
 
				sql=" SELECT   KHOTT.TRUNGTAMPP_FK AS KHOTONG_FK,  "+
					" (SELECT PK_SEQ FROM ERP_KHOTT WHERE LOAI = 9 AND TRUNGTAMPP_FK = KHOTT.TRUNGTAMPP_FK) AS KHOCHOXULY_FK "+ 
					" FROM ERP_KHOTT KHOTT WHERE KHOTT.PK_SEQ ="+khotam_fk;
				
				ResultSet rskho=db.get(sql);
				 if(rskho.next()){
					 khott_fk=rskho.getString("KHOTONG_FK");
					 khochoxuly_fk=rskho.getString("KHOCHOXULY_FK");
	 
					 if(khochoxuly_fk==null){
						 return "Không thể xác định được kho chờ xử lý của yêu cầu kiểm định: "+ycId;
					 }
					 
				 }else{
					 return "Không thể xác định được kho chuyển chuyển và kho nhận của yêu cầu kiểm định: "+ycId;
				 }
				 
				 /**
				  * sản phẩm đạt
				  */
				 
				   sql=" SELECT   A.SANPHAM_FK,ISNULL(A.SOLUONGDAT, 0) AS SOLUONGDAT, A.solo "+
				   " FROM ERP_YEUCAUKIEMDINH_SANPHAM A  "+
				   " INNER JOIN ERP_YEUCAUKIEMDINH YCKD ON YCKD.PK_SEQ=A.YCKD_FK "+
				   " WHERE YCKD_FK="+ycId+"  AND "+
				   " (  "+
				   " SELECT DISTINCT NHSP.KHONHAN FROM  ERP_NHANHANG NH "+ 
				   " INNER JOIN ERP_NHANHANG_SANPHAM NHSP  ON NH.PK_SEQ=NHSP.NHANHANG_FK AND A.SANPHAM_FK=NHSP.SANPHAM_FK "+
				   " WHERE   NH.PK_SEQ=YCKD.NHANHANG_FK  "+
				   " )   ="+khotam_fk+" AND ISNULL(A.SOLUONGDAT, 0) >0 ";
				   
				   ResultSet rsdetail=db.get(sql);
					List<ISpDetail> spdetail   = new ArrayList<ISpDetail>();
				   while(rsdetail.next()){
					   ISpDetail sp=new SpDetail();
					   sp.setSolo(rsdetail.getString("solo"));
					   sp.setSoluong(rsdetail.getString("SOLUONGDAT"));
					   sp.setId(rsdetail.getString("SANPHAM_FK"));
					   spdetail.add(sp);
				   }
				   rsdetail.close();
				   //đạt sang kho chính
				   if(spdetail.size() >0){
					   String msg1=this.createChuyenkho(NgayChotNV,khotam_fk,khott_fk,spdetail,ycId,"Chuyển kho hàng đạt của kiểm định : "+ycId);
					   if(msg1.length() >0 ){
						   return msg1;
					   }
				   }
				   /**
				    * sản phẩm không đạt
				    */
				   sql=" SELECT   A.SANPHAM_FK, SOLUONG -ISNULL(A.SOLUONGDAT,0) AS SOLUONGKHONGDAT , A.solo "+
				   " FROM ERP_YEUCAUKIEMDINH_SANPHAM A  "+
				   " INNER JOIN ERP_YEUCAUKIEMDINH YCKD ON YCKD.PK_SEQ=A.YCKD_FK "+
				   " WHERE YCKD_FK="+ycId+"  AND "+
				   " (  "+
				   " SELECT DISTINCT NHSP.KHONHAN FROM  ERP_NHANHANG NH "+ 
				   " INNER JOIN ERP_NHANHANG_SANPHAM NHSP  ON NH.PK_SEQ=NHSP.NHANHANG_FK AND A.SANPHAM_FK=NHSP.SANPHAM_FK "+
				   " WHERE   NH.PK_SEQ=YCKD.NHANHANG_FK  "+
				   " )   ="+khotam_fk+" AND (SOLUONG -ISNULL(A.SOLUONGDAT,0)) >0 ";
				   
				     rsdetail=db.get(sql);
				     spdetail.clear();
				   while(rsdetail.next()){
					   ISpDetail sp=new SpDetail();
					   sp.setSolo(rsdetail.getString("solo"));
					   sp.setSoluong(rsdetail.getString("SOLUONGKHONGDAT"));
					   sp.setId(rsdetail.getString("SANPHAM_FK"));
					   spdetail.add(sp);
					   
				   }
				   rsdetail.close();
				   if(spdetail.size() >0){
					   String msg1=this.createChuyenkho(NgayChotNV,khotam_fk,khochoxuly_fk,spdetail,ycId,"Chuyển kho hàng không đạt của kiểm định"+ycId);
					   if(msg1.length() >0 ){
						   return msg1;
					   }
				   }
		 				
			}
			rs.close();
			
		}catch(Exception err){
			
			err.printStackTrace();
			return err.getMessage();
		}
		return "";
	}
	
	
	private String createChuyenkho(String NgayChotNV,String khochuyen, String khonhan,
			List<ISpDetail> spdetail,String ycId ,String Lydo ) {
	 
		 try{
			 //100009	XC06	Chuyển kho nội bộ
			 
			String query = " insert ERP_CHUYENKHO( noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, trangthai, khoxuat_fk, khonhan_fk, trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua, NCC_CHUYEN_FK, NCC_NHAN_FK, LOAICHUYENKYGUI, LOAINHANKYGUI,YCKD_FK ) " +
				" values(100009 , '" + NgayChotNV + "', '" + NgayChotNV + "', '" + NgayChotNV + "', N'"+Lydo+"', '3', '" + khochuyen + "', " + khonhan + ", '1', " +
						" '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', NULL, NULL, NULL, NULL,"+ycId+")";
		 
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
				
				 for(int i = 0; i < spdetail.size(); i++) {
						ISpDetail sp = spdetail.get(i);
						query = "insert ERP_CHUYENKHO_SANPHAM(chuyenkho_fk, SANPHAM_FK, SOLUONGYEUCAU,SOLUONGXUAT,SOLUONGNHAN, vitrixuat) " +
						"values( '" + ckId + "', '" + sp.getId() + "', " + sp.getSoluong()+ "," + sp.getSoluong() + ","+sp.getSoluong()+", '100000' ) ";
				
						if(!db.update(query))
						{
							return  "Khong the tao moi ERP_CHUYENKHO_SANPHAM: " + query;
							 
						}
						query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,VITRI,SOLUONG) " +
						  " VALUES ("+ckId+", "+sp.getId()+",'"+sp.getSolo()+"',100000,'"+sp.getSoluong()+"')";
						if(db.updateReturnInt(query)< 1)
						{
							return "Không thể cập nhật : " + query;
						}
						
						query="UPDATE ERP_KHOTT_SANPHAM SET SOLUONG=SOLUONG-"+sp.getSoluong()+",AVAILABLE=AVAILABLE-"+sp.getSoluong()+"  WHERE KHOTT_FK="+khochuyen+" AND SANPHAM_FK="+sp.getId()+"" ;
						
						if(db.updateReturnInt(query)< 1)
						{
							return "Không thể cập nhật : " + query;
						}
						
						query="UPDATE ERP_KHOTT_SP_CHITIET SET SOLUONG=SOLUONG-"+sp.getSoluong()+",AVAILABLE=AVAILABLE-"+sp.getSoluong()+"  WHERE KHOTT_FK="+khochuyen+" AND SANPHAM_FK="+sp.getId()+" and RTRIM(LTRIM(solo))= '"+sp.getSolo().trim()+"'";
						
						if(db.updateReturnInt(query)< 1)
						{
							return "Không thể cập nhật : " + query;
						}
						
						query=" INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,VITRI,SOLUONG) " +
						  " VALUES ("+ckId+", "+sp.getId()+",'"+sp.getSolo()+"',100000,'"+sp.getSoluong()+"')";
						if(db.updateReturnInt(query)< 1)
						{
							return "Không thể cập nhật : " + query;
						}
						
						query="SELECT * FROM ERP_KHOTT_SANPHAM   WHERE KHOTT_FK="+khonhan+" AND SANPHAM_FK="+sp.getId()+"" ;
						
						ResultSet rscheck=db.get(query);
						if(rscheck.next()){
							query="UPDATE ERP_KHOTT_SANPHAM SET SOLUONG=SOLUONG+ "+sp.getSoluong()+",AVAILABLE=AVAILABLE+"+sp.getSoluong()+"  WHERE KHOTT_FK="+khonhan+" AND SANPHAM_FK="+sp.getId()+"" ;
						}else{
							query = 	" insert into erp_khott_sanpham (khott_fk,sanpham_fk,giaton,soluong,thanhtien,booked,available,masp) " +
							" select "+khonhan+",sanpham_fk,giaton,"+sp.getSoluong()+",giaton*"+sp.getSoluong()+",0,"+sp.getSoluong()+",masp  " +
							" from erp_khott_sanpham where SANPHAM_FK="+sp.getId()+" AND KHOTT_FK= "+khochuyen;
 
						}
						rscheck.close();
						if(db.updateReturnInt(query)< 1)
						{
							return "Không thể cập nhật : " + query;
						}
						
						query=" SELECT * FROM ERP_KHOTT_SP_CHITIET  WHERE KHOTT_FK="+khonhan+" AND SANPHAM_FK="+sp.getId()+" and RTRIM(LTRIM(solo))= '"+sp.getSolo().trim()+"'";
						
						rscheck=db.get(query);
						if(rscheck.next()){
							
							query="UPDATE ERP_KHOTT_SP_CHITIET SET SOLUONG=SOLUONG+"+sp.getSoluong()+",AVAILABLE=AVAILABLE+"+sp.getSoluong()+"  WHERE KHOTT_FK="+khonhan+" AND SANPHAM_FK="+sp.getId()+" and RTRIM(LTRIM(solo))= '"+sp.getSolo().trim()+"'";
							
						}else{
							query=" INSERT INTO ERP_KHOTT_SP_CHITIET ( KHOTT_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,SOLO,NGAYHETHAN,BIN,NGAYSANXUAT,NGAYNHAPKHO,GIACHIPHINL,GIACHIPHIKHAC,GIATHEOLO)"+
							" SELECT "+khonhan+",SANPHAM_FK,"+sp.getSoluong()+",0,"+sp.getSoluong()+",SOLO,NGAYHETHAN,BIN,NGAYSANXUAT,NGAYNHAPKHO,GIACHIPHINL,GIACHIPHIKHAC,GIATHEOLO FROM  ERP_KHOTT_SP_CHITIET " +
							" WHERE rtrim(ltrim(SOLO))='"+sp.getSolo()+"' and SANPHAM_FK="+sp.getId()+" AND KHOTT_FK= "+khochuyen;

						}
						
						if(db.updateReturnInt(query)< 1)
						{
							return "Không thể cập nhật : " + query;
						}
					  
				 }
		 }catch(Exception er){
			 er.printStackTrace();
			 return er.getMessage();
		 }
		 return "";
	}
 
	private boolean checkHoanTatQCVaTieuHao() {
		// TODO Auto-generated method stub
		try{
			// Kiem tra xem da co chuyen kho cua kiem dinh chua?
			String sql="select * from erp_chuyenkho where yckd_fk in ( select pk_seq from erp_yeucaukiemdinh where  " +
						"   nhanhang_fk =(select nhanhang_fk from erp_tieuhao where pk_seq="+this.id+" ))";
			
			ResultSet rs=db.get(sql);
			if(rs.next()){
				System.out.println("\n *************** da chuyen kho rou  *************** \n sql  "+ sql );
				rs.close();
				return false;
			}
			System.out.println("\n *************** Chua chuyen kho    *************** \n sql  "+ sql );
			  sql= " select pk_seq from erp_tieuhao where trangthai=0 and pk_seq <>"+this.id+"  " +
						" and   nhanhang_fk =(select nhanhang_fk from erp_tieuhao where pk_seq="+this.id+" )  " +
						"  union select pk_seq from erp_yeucaukiemdinh where trangthai=0 " +
						" and nhanhang_fk =(select nhanhang_fk from erp_tieuhao where pk_seq="+this.id+" ) "; 
				
			  rs=db.get(sql);
			if(rs.next()){
				//System.out.println("\n *************** Chưa hoan tat *************** \n sql  "+ sql );
				rs.close();
				return false;
			}
			//System.out.println("\n *************** da hoan tat *************** \n sql  "+ sql );
			rs.close();
			return true;
			
		}catch(Exception er){
			this.msg=er.getMessage();
			return false;
			 
			
		}
	}

	public void createRs() {
		
		
	}

	
	public void init() {
		
		NumberFormat formater3le = new DecimalFormat("#,###,###.###");
		NumberFormat formater4le = new DecimalFormat("#,###,###.####");
		
		String query = 
			" SELECT A.MUAHANG_FK , ISNULL( NGAYTIEUHAO, CONVERT(VARCHAR(10),GETDATE(),  126) ) NGAYTIEUHAO, ISNULL( A.NGAYCHOT, CONVERT(VARCHAR(10),GETDATE(),  126) ) NGAYCHOT \n" +
			" ,A.PK_SEQ as PK_SEQ, A.NHANHANG_FK, A.SANPHAM_FK, B.MA AS SANPHAM_MA, B.TEN AS SANPHAM_TEN,  \n" +
			" ISNULL(A.SOLUONG, 0) AS SOLUONG, A.NGUOITAO, A.NGUOISUA, A.NGAYTAO, A.NGAYSUA, ISNULL(A.TRANGTHAI, '0') AS TRANGTHAI \n" +
			" ,A.PREFIX + CAST(A.NHANHANG_FK as varchar(20))as MANHANHANG, ISNULL(A.GHICHU,'') AS GHICHU,  \n" +
			" ERP_KHOTT.TEN AS KHOTT,mh.SOPO,mh.ghichugc as ghiChuMuaHang \n" +
			" FROM ERP_TIEUHAO A " +
			" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ \n" +
			" LEFT JOIN ERP_KHOTT ON ERP_KHOTT.PK_SEQ = A.KHOTT_FK \n" +
			" inner join erp_muahang mh on  A.MUAHANG_FK = mh.PK_SEQ \n" +
			" WHERE A.PK_SEQ = '" + this.id + "' ";
		System.out.println("[ErpTieuHao.init] query = " + query);
		
		ResultSet rs = db.get(query);
		try {
			if(rs != null) {
				rs.next();
				this.id = rs.getString("PK_SEQ");
				this.nhanhangId = rs.getString("NHANHANG_FK");
				this.sanphamId = rs.getString("SANPHAM_FK");
				this.sanphamMa = rs.getString("SANPHAM_MA");
				this.sanphamTen = rs.getString("SANPHAM_TEN");
				this.soluong = rs.getString("SOLUONG");
				double	soluongtieuhao=rs.getDouble("SOLUONG");
				
				this.nguoitao = rs.getString("NGUOITAO");
				this.nguoisua = rs.getString("NGUOISUA");
				this.ngaytao = rs.getString("NGAYTAO");
				this.ngaysua = rs.getString("NGAYSUA");
				this.trangthai = rs.getString("TRANGTHAI");
				this.manhanhang= rs.getString("MANHANHANG");
				System.out.println("ngay tieu hao :  "+this.ngayTieuHao);
				
				if(this.ngayTieuHao==null || this.ngayTieuHao.equals("")){
					this.ngayTieuHao=rs.getString("NGAYTIEUHAO");
				}
				this.NgayChot=rs.getString("NGAYCHOT");
				this.Khotieuhao = rs.getString("KHOTT");
				this.ghichu = rs.getString("GHICHU");
				this.soMuaHang = rs.getString("SOPO");
				this.ghiChuMuaHang=rs.getString("ghiChuMuaHang");
				String MUAHANG_FK= rs.getString("MUAHANG_FK");
				
				rs.close();
				
				this.spList = new ArrayList<ISanpham>();
				
				query="select * from ERP_TIEUHAO_VATTU_chitiet where tieuhao_fk= "+this.id;
				
				ResultSet rscheck=db.get(query);
				boolean flag=false;
				
				if(rscheck.next()){
					flag=true;
				}
				rscheck.close();
				
				String sql = "  select  isnull(b.trangthai,'0') as trangthai  ,isnull(b.HOANTAT_NHANHANG,0) as HOANTAT_NHANHANG  ,b.pk_seq as MUAHANG_FK ,A.PK_SEQ AS NHANHANGID, a.NGAYCHOT, c.LOAINHACUNGCAP_FK, khoNL_Nho_GC, b.nhacungcap_fk , d.khachhang_fk " +
							 "  from ERP_NHANHANG a INNER JOIN ERP_NHANHANG_sANPHAM NHSP ON A.PK_SEQ=NHSP.NHANHANG_FK "
							 + " left join ERP_MUAHANG b on NHSP.MUAHANG_FK = b.PK_SEQ " +
							 "	left join ERP_NHACUNGCAP c on b.NHACUNGCAP_FK = c.PK_SEQ" +
							 "  left join DonTraHang d on a.trahang_fk = d.pk_seq   " +
							 "  where NHSP.MUAHANG_FK ="+MUAHANG_FK+" AND a.pk_seq = '" + this.nhanhangId + "' AND NHSP.SANPHAM_FK="+this.sanphamId;
				 System.out.println("Nha gia cong :"+sql);
				String ngaychotNV = "";
				String loaiNCC = "";
				// có 1 kho gia cONG
				String khoNL_Nho_GC = "100055";
				String nccId = "";
				String khId = "";
				
				String HOANTAT_NHANHANG="0";
				ResultSet rskho = db.get(sql);
				 	if(rskho.next())
					{
						ngaychotNV = rskho.getString("ngaychot");
						loaiNCC = rskho.getString("LOAINHACUNGCAP_FK") == null ? "" : rskho.getString("LOAINHACUNGCAP_FK");
						this.manhanhang= rskho.getString("NHANHANGID");
						nccId = rskho.getString("nhacungcap_fk") == null ? "" : rskho.getString("nhacungcap_fk");
						this.nccid=nccId;
						khId = rskho.getString("khachhang_fk") == null ? "" : rskho.getString("khachhang_fk");
						//MUAHANG_FK= rskho.getString("MUAHANG_FK");
						
						
						if( ( rskho.getString("HOANTAT_NHANHANG").equals("1") ||  rskho.getString("trangthai").equals("2"))  && this.checktieuhao_cuoi(this.nhanhangId)){
							HOANTAT_NHANHANG="1";
							
						}
						
					} 
				 	rskho.close();

				 	
				 	if(!this.trangthai.equals("0")){
						query = " SELECT A.TIEUHAO_FK, A.VATTU_FK, ISNULL(B.MA, '') AS VATTU_MA, ISNULL(B.TEN, '') AS VATTU_TEN,  "+
								"  ISNULL(A.SOLUONGCHUAN, 0)  as SOLUONGXUAT, ISNULL(A.SOLUONGCHUAN, 0) AS SOLUONGCHUAN, ISNULL(A.SOLUONGTHUCTE, 0) AS SOLUONGTHUCTE, ISNULL(C.DONVI, '') AS VATTU_DVT , "+
								" ISNULL((SELECT SUM(THVT1.SOLUONGTHUCTE)  FROM ERP_TIEUHAO_VATTU THVT1 INNER JOIN ERP_TIEUHAO TH1 ON TH1.PK_SEQ=THVT1.TIEUHAO_FK "+  
								" WHERE THVT1.VATTU_FK=A.VATTU_FK AND TH1.NHANHANG_FK=th.NHANHANG_FK AND TH1.PK_SEQ <>"+this.id+" and th1.trangthai <> 2),0) AS SOLUONGDATIEUHAO "+ 
								" FROM ERP_TIEUHAO_VATTU A  " +
								" inner join ERP_TIEUHAO th on th.PK_SEQ=A.TIEUHAO_FK  "+
								" INNER JOIN ERP_SANPHAM B ON A.VATTU_FK = B.PK_SEQ  "+
								" LEFT JOIN DONVIDOLUONG C ON B.DVDL_FK = C.PK_SEQ "+  
								" WHERE TIEUHAO_FK = "+this.id;
				 		
				 	}else{

				
						query=		"SELECT B.SOLUONG AS SOLUONGXUAT , A.* FROM (  " +
								/*+ "  SELECT   MHBOM.SANPHAM_FK AS  VatTu_FK,MHSP.SOLUONG  AS SOLUONGCHUAN  "
				 				+ " ,sp.ma  as  VATTU_MA ,sp.ten as  VATTU_TEN , ISNULL(c.DONVI, '') AS VATTU_DVT ,SUM(MHBOM.SOLUONG) as soluong "+
								" FROM ERP_MUAHANG_BOM_CHITIET  MHBOM    "+
								" INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.SANPHAM_FK=MHBOM.SANPHAM_MUA_FK   AND MHBOM.MUAHANG_FK=MHSP.MUAHANG_FK  "
								+ " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= MHBOM.SANPHAM_FK  "+ 
								" LEFT JOIN DONVIDOLUONG C ON sp.DVDL_FK = C.PK_SEQ "+  
								" WHERE    MHBOM.MUAHANG_FK = "+MUAHANG_FK+"  AND MHBOM.SANPHAM_MUA_FK = "+this.sanphamId+" "+
								" group by  MHBOM.SANPHAM_FK ,MHBOM.SANPHAM_FK,MHSP.SOLUONG,c.DONVI  ,sp.ma,sp.ten "*/
								" 	SELECT   MHBOM.SANPHAM_FK AS  VatTu_FK,MH_1.SOLUONGCHUAN  AS SOLUONGCHUAN ,MHSP.SOLUONG as SOLUONGGIACONG ,sp.ma  as  VATTU_MA ,sp.ten as  VATTU_TEN "+ 
								" 	, ISNULL(c.DONVI, '') AS VATTU_DVT ,SUM(MHBOM.SOLUONG) as soluong  FROM ERP_MUAHANG_BOM_CHITIET  MHBOM     "+
								" 	INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.SANPHAM_FK=MHBOM.SANPHAM_MUA_FK   AND MHBOM.MUAHANG_FK=MHSP.MUAHANG_FK "+ 
								" 	INNER JOIN ERP_MUAHANG_BOM MH_1 ON MH_1.MUAHANG_FK= MHSP.MUAHANG_FK AND    MH_1.VATTU_FK= MHBOM.VATTU_FK "+
								" 	AND MH_1.SANPHAM_FK= MHSP.SANPHAM_FK  "+
								" 	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= MHBOM.SANPHAM_FK   LEFT JOIN DONVIDOLUONG C ON sp.DVDL_FK = C.PK_SEQ "+  
								" 	WHERE    MHBOM.MUAHANG_FK = "+MUAHANG_FK+"  AND MHBOM.SANPHAM_MUA_FK = "+this.sanphamId+" "+
								" 	group by  MHBOM.SANPHAM_FK ,MHBOM.SANPHAM_FK,MH_1.SOLUONGCHUAN,MHSP.SOLUONG,c.DONVI  ,sp.ma,sp.ten "+
								" ) "
								+ " A  LEFT JOIN " +
								  " ( " +
								" 	 SELECT A.KHO_FK  , A.SANPHAM_FK , SUM(SOLUONG) AS SOLUONG FROM  \n"+ 
								" 			(  \n"+
								" 			  \n"+
								" 				SELECT  A.KHONHAN_FK AS KHO_FK, B.SANPHAM_FK,BIN.PK_SEQ  AS BIN_FK, ISNULL(B.SOLO,'') AS SOLO  \n"+ 
								" 				, B.NGAYHETHAN, B.NGAYNHAPKHO, B.MAME, B.MATHUNG ,   \n"+
								" 				B.MAPHIEU, B.PHIEUDT, B.PHIEUEO,  B.MARQ, ISNULL(B.HAMAM, '0') AS HAMAM, ISNULL(B.HAMLUONG, '100') AS HAMLUONG ,  \n"+ 
								" 				 (B.SOLUONG) AS SOLUONG    \n"+
								" 				FROM ERP_CHUYENKHO A INNER JOIN ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG B ON A.PK_SEQ = B.CHUYENKHO_FK  \n"+  
								" 				LEFT JOIN ERP_BIN BIN ON B.BINNHAN_FK = BIN.PK_SEQ     \n"+
								" 				WHERE A.TRANGTHAI = '3'	AND A.MUAHANG_FK= "+MUAHANG_FK+" AND A.NOIDUNGXUAT_FK= 100065 and a.NGAYNHAN <='"+this.ngayTieuHao+"' \n"+
								" 				UNION ALL   \n"+
								" 				SELECT A.KHOXUAT_FK AS KHO_FK, B.SANPHAM_FK,BIN.PK_SEQ  AS BIN_FK, ISNULL(B.SOLO,'') AS SOLO  \n"+ 
								" 				, B.NGAYHETHAN, B.NGAYNHAPKHO, B.MAME, B.MATHUNG ,   \n"+
								" 				B.MAPHIEU, B.PHIEUDT, B.PHIEUEO,  B.MARQ, ISNULL(B.HAMAM, '0') AS HAMAM, ISNULL(B.HAMLUONG, '100') AS HAMLUONG ,  \n"+ 
								" 				(-1)*  (B.SOLUONG) AS SOLUONG    \n"+
								" 				FROM ERP_CHUYENKHO A INNER JOIN ERP_CHUYENKHO_SANPHAM_CHITIET  B ON A.PK_SEQ = B.CHUYENKHO_FK  \n"+  
								" 				LEFT JOIN ERP_BIN BIN ON B.BINNHAN_FK = BIN.PK_SEQ     \n"+
								" 				INNER JOIN ERP_KHOTT KHOXUAT ON KHOXUAT.PK_SEQ= A.KHOXUAT_FK  \n"+
								" 				WHERE A.TRANGTHAI = '3'	  AND A.NOIDUNGXUAT_FK= 100068   \n"+
								" 				AND KHOXUAT.LOAI='7' 	AND A.MUAHANG_FK= "+MUAHANG_FK+" and a.NGAYNHAN <='"+this.ngayTieuHao+"'  \n"+
								" 			 )  A   \n"+
								" 			 GROUP BY  A.KHO_FK  , A.SANPHAM_FK   \n"+
								  ") B ON B.SANPHAM_FK= A.VatTu_FK  ";
						
				 	}
				 	
				 	System.out.println("query: "+query);
				 
				rs = db.get(query);
			 
					while(rs.next()) {
						
						ISanpham vattu = new Sanpham();
						vattu.setTieuHaoId(this.id);
						vattu.setId(rs.getString("VATTU_FK"));
						vattu.setMa(rs.getString("VATTU_MA"));
						vattu.setTen(rs.getString("VATTU_TEN"));
						vattu.setDonViTinh(rs.getString("VATTU_DVT"));
						vattu.setsoluongXuat(rs.getDouble("SOLUONGXUAT"));
						
						double soluongchuan=0;
						double soluongtong=0;
						
						if(!this.trangthai.equals("0")){
							soluongchuan =rs.getDouble("SOLUONGXUAT");
							vattu.setSoLuongDaTieuHao(rs.getDouble("SOLUONGDATIEUHAO"));
							//soluongtong =rs.getDouble("SOLUONGTHUCTE");
						}else{
							
							 soluongchuan= Double.parseDouble(formater4le.format(rs.getDouble("SOLUONGXUAT") * soluongtieuhao / rs.getDouble("SOLUONGGIACONG")).replaceAll(",", "") );
							//LẤY TỪ BOM ĐÃ TÍNH RA BÊN ĐƠN MUA HÀNG( KHÔNG CẦN CHIA LẠI TỈ LỆ)
							// soluongchuan=  rs.getDouble("SOLUONGCHUAN");
							 vattu.setSoLuongDaTieuHao(0);
							 soluongtong=soluongchuan;
						}
						
						
						
						vattu.setSoLuongChuan( rs.getDouble("SOLUONGCHUAN"));
						vattu.setsoluongDMTieuhao(soluongchuan);
						double soluongth_=0;
						
						
						List<ISanphamLo> listlo=new ArrayList<ISanphamLo>();
						if(khoNL_Nho_GC.length() >0)  {
							 
									query=	" SELECT KHO.HAMAM,KHO.HAMLUONG,  KHO.NGAYNHAPKHO,ISNULL(KHO.NGAYHETHAN,'') AS NGAYHETHAN ,ISNULL(BIN.MA,'') AS BIN , ISNULL(CAST(BIN.PK_SEQ AS NVARCHAR(10)),'') AS BIN_FK ,ISNULL(KHO.MATHUNG,'') AS MATHUNG \n " +
											" ,ISNULL(KHO.MAME,'') AS MAME , ISNULL(KHO.MAPHIEU,'') AS MAPHIEU,ISNULL(KHO.PHIEUEO,'') AS PHIEUEO,ISNULL(KHO.MAPHIEUDINHTINH,'') AS MAPHIEUDINHTINH \n" +
											" , ISNULL(MQ.MA,'') AS MARQ,ISNULL(KV.MA,'') AS KHUVUC , \n" +
											" ISNULL(CAST( IDMARQUETTE AS VARCHAR(10)),'')  AS IDMARQUETTE  , \n" +
											"  ISNULL(CAST( KHUVUCKHO_FK AS VARCHAR(10)),'') AS  KHUVUCKHO_FK ,KHO.SOLO,SP.MA,SP.TEN,"
										  + "  ISNULL( (SELECT SUM(SOLUONG) FROM ERP_TIEUHAO_VATTU_CHITIET A \n"+   
											"  WHERE   TIEUHAO_FK="+this.id+" AND A.SOLO=KHO.SOLO AND A.ngaynhapkho=KHO.ngaynhapkho    AND A.NGAYHETHAN=KHO.NGAYHETHAN  AND isnull(A.mathung,'') =isnull(KHO.mathung,'') AND isnull(A.mame,'') =isnull(KHO.mame,'')  \n" +
											"  AND isnull(A.maphieu,'') =isnull(KHO.maphieu,'') AND isnull(A.phieueo,'') =isnull(KHO.phieueo,'')  \n" +
											"  AND isnull(A.maphieudinhtinh,'') =isnull(KHO.maphieudinhtinh,'') and ISNULL(A.BIN_FK,0)=ISNULL(KHO.BIN_FK,0)  \n"+
											" AND  A.VATTU_FK=KHO.SANPHAM_FK AND ISNULL(A.IDMARQUETTE,0)= ISNULL(KHO.IDMARQUETTE,0) \n"+  
											" AND ISNULL(A.bin_FK,0)= ISNULL(KHO.bin_FK,0)  ),0) AS SOLUONG, KHO.AVAILABLE ,ISNULL(BOM.SOLUONG,0)  AS SOLUONGCHUYENKHO ,ISNULL(BOM.SOLUONGTIEUHAO,0) AS SOLUONGTIEUHAO    \n"+
											" FROM ERP_KHOTT_SP_CHITIET  KHO   \n"+
											" INNER JOIN \n"+ 
											" (   \n"+
											" 	 SELECT A.KHO_FK  , A.SANPHAM_FK,A.BIN_FK   ,A.SOLO, A.NGAYHETHAN,A.NGAYNHAPKHO, A.MAME, A.MATHUNG ,  \n"+  
											" 			A.MAPHIEU, A.PHIEUDT, A.PHIEUEO,  A.MARQ, A.HAMAM ,  A.HAMLUONG  ,SUM(SOLUONG) AS SOLUONG ,SUM(SOLUONGTIEUHAO) AS SOLUONGTIEUHAO  FROM  \n"+ 
											" 			(  \n"+
											" 			  \n"+
										 
											"	SELECT  A.KHONHAN_FK AS KHO_FK, B.SANPHAM_FK,BIN.PK_SEQ  AS BIN_FK, ISNULL(B.SOLO,'') AS SOLO    \n"+   
											"	, B.NGAYHETHAN, B.NGAYNHAPKHO, B.MAME, B.MATHUNG ,     \n"+   
											"	B.MAPHIEU, B.PHIEUDT, B.PHIEUEO,  B.MARQ, ISNULL(B.HAMAM, '0') AS HAMAM, ISNULL(B.HAMLUONG, '100') AS HAMLUONG ,    \n"+   
											"	(B.SOLUONG)-isnull((  \n"+   
											" 		SELECT    \n"+   
											"	sum(cksp.SOLUONG) AS SOLUONG     \n"+   
											"	FROM ERP_CHUYENKHO ck INNER JOIN ERP_CHUYENKHO_SANPHAM_CHITIET  cksp ON ck.PK_SEQ =cksp.CHUYENKHO_FK    \n"+   
											"	INNER JOIN ERP_KHOTT KHOXUAT ON KHOXUAT.PK_SEQ= ck.KhoXuat_FK     \n"+   
											"	WHERE ck.TRANGTHAI = '3'	  AND ck.NOIDUNGXUAT_FK= 100068     \n"+   
											"	AND KHOXUAT.LOAI='7' 	AND ck.MUAHANG_FK= "+MUAHANG_FK+"  and ck.NGAYNHAN <='"+this.ngayTieuHao+"'    \n"+   
											"	and isnull(cksp.binnhan_fk,0)= isnull(BIN.PK_SEQ,0) and ISNULL(cksp.SOLO,'')   = ISNULL(B.SOLO,'')    \n"+   
											"	and cksp.ngayhethan= B.NGAYHETHAN and cksp.ngaynhapkho=  B.NGAYNHAPKHO   \n"+   
											"	and cksp.mame= B.MAME and cksp.mathung= B.MATHUNG    \n"+   
											"	and cksp.maphieu=b.MAPHIEU and cksp.phieudt= B.PHIEUDT and cksp.phieueo= B.PHIEUEO   \n"+   
											"	and cksp.marq= B.MARQ and   \n"+   
											"	ISNULL(cksp.HAMAM, '0')= ISNULL(B.HAMAM, '0')   \n"+   
											"	and  ISNULL(cksp.HAMLUONG, '100')=  ISNULL(B.HAMLUONG, '100')    \n"+   
											"	),0) AS SOLUONG ,0 AS SOLUONGTIEUHAO       \n"+   
											"	FROM ERP_CHUYENKHO A INNER JOIN ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG B ON A.PK_SEQ = B.CHUYENKHO_FK    \n"+   
											"	LEFT JOIN ERP_BIN BIN ON B.BINNHAN_FK = BIN.PK_SEQ       \n"+   
											"		WHERE A.TRANGTHAI = '3'	AND A.MUAHANG_FK= "+MUAHANG_FK+" and a.NGAYNHAN <='"+this.ngayTieuHao+"'    \n"+   
												"	  \n"+   
													  "     UNION  ALL  "+
													" SELECT A.KHOTT_FK AS KHO_FK, B.VATTU_FK,BIN.PK_SEQ  AS BIN_FK, ISNULL(B.SOLO,'') AS SOLO  \n"+   
													" , B.NGAYHETHAN, B.NGAYNHAPKHO, B.MAME, B.MATHUNG ,     \n"+
													" B.MAPHIEU, B.MAPHIEUDINHTINH, B.PHIEUEO,  B.MARQ, ISNULL(B.HAMAM, '0') AS HAMAM, ISNULL(B.HAMLUONG, '100') AS HAMLUONG ,  \n"+   
													"  0 AS SOLUONG , (B.SOLUONG)      AS SOLUONGTIEUHAO  \n"+
													" FROM ERP_TIEUHAO  A INNER JOIN ERP_TIEUHAO_VATTU_CHITIET  B ON A.PK_SEQ = B.TIEUHAO_FK   \n"+
													" LEFT JOIN ERP_BIN BIN ON B.BIN_FK  = BIN.PK_SEQ        \n"+
													" INNER JOIN ERP_KHOTT KHOXUAT ON KHOXUAT.PK_SEQ= A.KHOTT_FK   \n"+  
													" WHERE A.TRANGTHAI = '1'	    \n"+
													" AND A.NHANHANG_FK  =  "+MUAHANG_FK+"  and a.nhanhang_fk="+this.nhanhangId+"   \n"+
											" 			 )  A   \n"+
											" 			 GROUP BY  A.KHO_FK  , A.SANPHAM_FK,A.BIN_FK   ,A.SOLO, A.NGAYHETHAN,A.NGAYNHAPKHO, A.MAME, A.MATHUNG ,  \n"+  
											" 			A.MAPHIEU, A.PHIEUDT, A.PHIEUEO,  A.MARQ, A.HAMAM ,  A.HAMLUONG   \n"+
											" ) BOM ON BOM.SANPHAM_FK=KHO.SANPHAM_FK AND BOM.SOLO=KHO.SOLO AND ISNULL( BOM.MATHUNG,'') = ISNULL(KHO.MATHUNG,'') \n"+ 
											" AND ISNULL( BOM.MAME,'') = ISNULL(KHO.MAME,'')   \n"+
											" AND ISNULL( BOM.MAPHIEU,'') = ISNULL(KHO.MAPHIEU,'')   \n"+
											" AND ISNULL( BOM.phieudt,'') = ISNULL(KHO.MAPHIEUDINHTINH,'') \n"+ 
											" AND ISNULL( BOM.PHIEUEO,'') = ISNULL(KHO.PHIEUEO,'')  \n"+
											" and ISNULL(kho.MARQ,'')= ISNULL(BOM.MARQ,'')  " +
											" AND  BOM.NGAYHETHAN  = KHO.NGAYHETHAN \n"+
											" AND  BOM.NGAYNHAPKHO = KHO.NGAYNHAPKHO \n"+
											" LEFT JOIN MARQUETTE MQ ON KHO.IDMARQUETTE=MQ.PK_SEQ \n"+
											" LEFT JOIN ERP_KHUVUCKHO KV ON KV.PK_SEQ=KHO.KHUVUCKHO_FK  \n" +
											" LEFT JOIN ERP_BIN BIN ON BIN.PK_SEQ=KHO.BIN_FK \n"+
											" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=KHO.SANPHAM_FK   \n"+
											" WHERE KHO.LOAIDOITUONG='0' AND KHO.KHOTT_FK="+khoNL_Nho_GC+" AND KHO.DOITUONGID="+nccId+"  AND KHO.NGAYNHAPKHO<='"+this.ngayTieuHao+"'   AND KHO.SANPHAM_FK= "+vattu.getId()+" \n"+
											" AND  ( KHO.AVAILABLE  +ISNULL((SELECT SUM( SOLUONG) FROM ERP_TIEUHAO_VATTU_CHITIET  A  \n"+
											" WHERE A.VATTU_FK=KHO.SANPHAM_FK AND A.SOLO=KHO.SOLO AND ISNULL(A.IDMARQUETTE,0)= ISNULL(KHO.IDMARQUETTE,0) \n"+  
											" AND ISNULL(A.KHUVUCKHO_FK,0)= ISNULL(KHO.KHUVUCKHO_FK,0)   AND TIEUHAO_FK="+this.id+"),0) >0)  \n"+
											" ORDER BY NGAYHETHAN \n";
									
								System.out.println("Du Lieu : "+query);
							 
								
								
								
								 ResultSet rslo=db.get(query);
								 while (rslo.next()){
									 ISanphamLo splo=new SanphamLo();
									 splo.setsolo(rslo.getString("solo"));
									 splo.setMa(rslo.getString("ma"));
									 splo.setTen(rslo.getString("ten"));
									 splo.setIDMARQUETTE(rslo.getString("IDMARQUETTE"));
									 splo.setKHUVUCKHO_FK(rslo.getString("KHUVUCKHO_FK"));
									 splo.setMARQ(rslo.getString("MARQ"));
									 splo.setKHUVUC(rslo.getString("KHUVUC"));
									 splo.setMaphieu(rslo.getString("MAPHIEU"));
									 splo.setPHIEUEO(rslo.getString("PHIEUEO"));
									 splo.setMAPHIEUDINHTINH(rslo.getString("MAPHIEUDINHTINH"));
									 splo.setMathung(rslo.getString("mathung"));
									 splo.setMame(rslo.getString("mame"));
									 splo.setVitri(rslo.getString("bin"));
									 splo.setVitriId(rslo.getString("bin_fk"));
									 splo.setNgayhethan(rslo.getString("NGAYHETHAN"));
									 splo.setngaynhapkho(rslo.getString("NGAYNHAPKHO"));
									 splo.setHamam(rslo.getString("hamam"));
									 splo.setHamluong(rslo.getString("hamluong"));
									 
									 
								 		double avai= rslo.getDouble("SOLUONG")+rslo.getDouble("AVAILABLE");
								 		 
								 		
								 		if(flag==false){
									 		if( soluongtong >0) {
									 			
									 		
									 			double  soluongth_ct=0; 
									 			double SOLUONGCHUYENKHO= rslo.getDouble("SOLUONGCHUYENKHO");
									 			double SOLUONGTIEUHAO= rslo.getDouble("SOLUONGTIEUHAO");
									 			
									 			System.out.println("hoan tat nhan hang : "+HOANTAT_NHANHANG);
									 			if(HOANTAT_NHANHANG.equals("1")){
													// tiêu hao lần cuối thì lấy hết cái đã chuyển kho
													if((SOLUONGCHUYENKHO-SOLUONGTIEUHAO) < avai){
														soluongth_ct=( (SOLUONGCHUYENKHO - SOLUONGTIEUHAO) <0? 0:(SOLUONGCHUYENKHO - SOLUONGTIEUHAO)) ;
													}else{
														soluongth_ct=rslo.getDouble("AVAILABLE");
													}
													
												}else{
													System.out.println("soluongtong: "+ soluongtong);
													 
													// chỉ lấy bằng số lượng lô đã chuyển và lấy những lô khác của lênh sản xuất
													double soluongxuat_=rslo.getDouble("SOLUONGCHUYENKHO") ;
													
													double soluong1=(soluongxuat_ < avai? soluongxuat_: avai) ; 
													
													if(soluongtong < soluong1 )
													{
														soluongth_ct=soluongtong;
														soluongtong=0;
													}else{
														soluongth_ct= soluong1 ;
														soluongtong=soluongtong-soluong1 ;
													}
												}
									 			splo.setSoLuong(soluongth_ct);
											}else{
												splo.setSoLuong(0);
											}
									 		
									 		
								 		} else{
								 			splo.setSoLuong(rslo.getDouble("SOLUONG"));
								 		}
								 		
								 		
								 		soluongth_+=splo.getSoLuong();
								 		splo.setSoLuongton(avai);
									 
									 listlo.add(splo);
									 
								 }
								 rslo.close();
						}
							 vattu.setSpList(listlo);
							  vattu.setSoLuongThucTe(soluongth_);
						
						spList.add(vattu);
						
						
						
					}
					rs.close();
			 
			}
			
		} catch(Exception e) {
			this.msg=e.getMessage();
			e.printStackTrace();
		}
		
	}

	
	private boolean checktieuhao_cuoi(String mUAHANG_FK) {
		// TODO Auto-generated method stub
		try{
			String query="SELECT * FROM ERP_TIEUHAO  TH WHERE  muahang_fk="+mUAHANG_FK+" and nhanhang_fk="+this.nhanhangId+"  AND TRANGTHAI =0 AND TH.PK_SEQ <> "+this.id;
			
			ResultSet rs=db.get(query);
			boolean bien=false;
			if(!rs.next()){
				bien=true;
			}
			rs.close();
			return bien;
		}catch(Exception er){
			er.printStackTrace();
			return false;
		}
		 
	}

	public void initPdf(String spId) {
		
		
	}

	
	public void close() {
		if(db!=null) {
			db.shutDown();
		}
	}
	
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	
	public String getnccid() {
		
		return this.nccid;
	}

	
	public String getKhotieuhao() {
		
		return this.Khotieuhao;
	}

	
	public void setkhotieuhao(String khotieuhao) {
 
		this.Khotieuhao=khotieuhao;
	}

	
	public void setnccid(String nccid_) {
		
		this.nccid=nccid_;
	}

	
	public String getNgaytieuhao() {
		
		return this.ngayTieuHao;
	}

	
	public void setNgaytieuhao(String Ngaytieuhao) {
		
		this.ngayTieuHao=Ngaytieuhao;
	}

	
	public String getNgaychot() {
		
		return this.NgayChot;
	}

	
	public void setNgaychot(String _Ngaychot) {
		
		this.NgayChot=_Ngaychot;
	}

	@Override
	public boolean CreateTieuhaoThem(String _Id) {
		// TODO Auto-generated method stub
	try {
			
			String query = 
				" INSERT INTO ERP_TIEUHAO(CONGTY_FK, NHANHANG_FK, SANPHAM_FK, SOLUONG, TRANGTHAI ,NGUOITAO, NGAYTAO,NGUOISUA ,NGAYSUA ,NCC_FK,KHOTT_FK,NGAYTIEUHAO,NGAYCHOT) " +
				" SELECT CONGTY_FK, NHANHANG_FK, SANPHAM_FK, SOLUONG,0, "+this.userId+", '"+this.getDateTime()+"',  "+this.userId+", '"+this.getDateTime()+"',NCC_FK,KHOTT_FK,NGAYTIEUHAO,NGAYCHOT FROM ERP_TIEUHAO WHERE PK_SEQ="+_Id;
			
			
			if(!db.update(query)) {
				msg = "Không thể tạo phiếu tiêu hao!";
				return false;
			}
			
			query = "SELECT SCOPE_IDENTITY() AS ID";
			ResultSet rs =  db.get(query);
			rs.next();
			String idtieuhao= rs.getString("ID");
	 
				query = " INSERT ERP_TIEUHAO_VATTU(TIEUHAO_FK, VATTU_FK, SOLUONGCHUAN, SOLUONGTHUCTE) " +
						" SELECT "+idtieuhao+", VATTU_FK, SOLUONGCHUAN,0 from ERP_TIEUHAO_VATTU where  TIEUHAO_FK="+_Id;

				if(!db.update(query)) {
					msg = "Không thể cập nhât : "+query;
					return false;
				}
			 
				this.id=idtieuhao;
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Xảy ra lỗi trong quá trình tạo phiếu tiêu hao! (" + e.getMessage() + ")";
			return false;
		}
	}

	public String getSoMuaHang() {
		return soMuaHang;
	}

	public void setSoMuaHang(String soMuaHang) {
		this.soMuaHang = soMuaHang;
	}
	public String getMuahangID() {
		return soMuaHang;
	}

	public void setMuahangID(String IdMuaHang) {
		this.muahangid = IdMuaHang;
	}

	public String getGhiChuMuaHang() {
		return ghiChuMuaHang;
	}

	public void setGhiChuMuaHang(String ghiChuMuaHang) {
		this.ghiChuMuaHang = ghiChuMuaHang;
	}
	
}
