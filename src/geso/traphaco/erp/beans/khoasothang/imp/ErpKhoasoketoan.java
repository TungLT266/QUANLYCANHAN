package geso.traphaco.erp.beans.khoasothang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
  
import geso.traphaco.erp.beans.khoasothang.IErpKhoasoketoan;
import geso.traphaco.erp.beans.khoasothang.IKiemTraDLN;
import geso.traphaco.erp.beans.khoasothang.IKiemTraLechXNT;
import geso.traphaco.erp.util.LibraryKS;

public class ErpKhoasoketoan implements IErpKhoasoketoan 
{
	String userId;
	String thang;
	String nam;
	
	int sonhanhang;
	int sonhapkho;
	int soQc_NH;
	int soQc_LSX;
	int soTh_NH;
	int soTh_LSX;
	int coNhapKho_ChuaTH;
	int soDctk;
	int soKiemkho;
	int soHDNCC;
	int soxuatkho;
	int soxuatkhoCXHD;
	int sochuyenkho;
	int sodctk;
	int sohdtc;
	int solsx;
	int sodqc;
	
	int sophieuthu;
	int sophieuchi;
	int soBTTH;
	
	int sochaykhauhao;
	int sochaychenhlech;
	int sotinhtigia;
	
	ResultSet chungtuRs;
	String ctyId;
	String Id;
	
	int row;
	String msg;
	
	List<IKiemTraDLN> kiemtraDLNlst;
	List<IKiemTraLechXNT> kiemtralechXNTlst;
	ResultSet rs_chuyenkho_khac_NX;
	ResultSet Rs_NhanHang_ChuaCapNhatKetoan;
	ResultSet Rs_NhanHang_khong_giatri;
	ResultSet Rs_HoaDon_Donhang;
	
	ResultSet Rs_Stock_Invalid;
	dbutils db;
	
	String CheckDuLieuNen="";
	String CheckNhanHangGiaTriKhong="";
	String CheckDonhang_Hoadon="";
	String CheckXacNhan;
	
	private boolean checkdh_hd=false;
	private boolean  nhanhang_gt_0=false;
	private boolean  chungtu_chuachot=false;
	private boolean check_nhanhang_chua_butoan=false;
	
	
	String str_checkxacnhan="";
	String mang[] = new String[]{"0","1","2","3","4","5","6","7","8"};
	
	String mangten[] = new String[]{"Kiểm tra số dư đầu kỳ các Tài khoản trên cân đối phát sinh so với cuối kỳ trước (so với file excel lưu hoặc bản hardcopy)",
									"Khóa sổ kinh doanh",
									"Kiểm tra sổ phụ ngân hàng so với số dư Tài khoản",
									"Kết chuyển VAT đầu vào/đầu ra",
									"Trích trước các khoản chi phí",
									"Trích lập các quỹ đầu tư 1 và quỹ đầu tư 2",
									"Phân bổ chi phí trả trước",
									"Khấu hao tài sản cố định",
									"Khóa sổ kho",
			};
	String Nppid="";
	
	ResultSet rsketchuyenketoan;
	public ErpKhoasoketoan()
	{
		this.thang = "";
		this.nam = "";
		this.row = 0;
		
		this.sonhanhang = 0;
		this.sonhapkho = 0;
		this.soQc_NH = 0;
		this.soQc_LSX = 0;
		this.soTh_NH = 0;
		this.soTh_LSX = 0;
		this.coNhapKho_ChuaTH = 0;
		this.soDctk = 0;
		this.soKiemkho = 0;
		this.soHDNCC = 0;
		this.soxuatkho = 0;
		this.soxuatkhoCXHD = 0;
		this.sochuyenkho = 0;
		this.sodctk = 0;
		this.sohdtc = 0;
		this.solsx = 0;
		this.sodqc = 0;
		this.sophieuthu = 0;
		this.sophieuchi = 0;
		this.soBTTH = 0;
		
		this.sochaykhauhao = 0;
		this.sochaychenhlech = 0;
		this.sotinhtigia = 0;
		
		this.msg = "";
		this.Id="";
		
	
		
		db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
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

	public ResultSet getChungtuRs() 
	{
		return this.chungtuRs;
	}

	public void setChungtuRs(ResultSet ctRs) 
	{
		this.chungtuRs = ctRs;
	}

	public int getRow() 
	{
		return this.row;
	}

	public void setRow(int row) 
	{
		this.row = row;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void init()
	{		
		try 
		{
			Utility util=new Utility();
			String nppid=util.getIdNhapp(this.userId);
			
			if(this.Id!=null &&  this.Id.length() >0  ){
				

				String query = "SELECT  THANGKS,NAM FROM ERP_KHOASOKETOAN_LIST WHERE pk_seq="+this.Id;
				
					//System.out.println("1.Khoi tao thang: " + query);
					ResultSet rs = db.get(query);
					
					String thangKsMax = "";
					String namKsMax = "";
			
				 
					if(rs.next())
					{
						this.thang = rs.getString("THANGKS");
						this.nam = rs.getString("NAM"); 
					 
					}
					rs.close();
					
					this.Init_kiemtradulieu();
					
				 
			
			}else{
				String query = " SELECT TOP 1 THANGKS,NAM FROM ERP_KHOASOKETOAN_LIST   WHERE CONGTY_FK="+this.ctyId+"  order by NAM desc, THANGKS desc";
				//System.out.println("1.Khoi tao thang: " + query);
				ResultSet rs = db.get(query);
				
				String thangKsMax = "";
				String namKsMax = "";
			
				 
					while(rs.next())
					{
						thangKsMax = rs.getString("THANGKS");
						namKsMax = rs.getString("NAM"); 
						
						if(thangKsMax.equals("12"))
						{
							this.thang = "1";
							this.nam = Integer.toString(Integer.parseInt(namKsMax) + 1);
	 					}
						else
						{
							this.thang = Integer.toString(Integer.parseInt(thangKsMax) + 1);
							this.nam = namKsMax;
						}
					}
					rs.close();
			
					this.init_rs();
			}
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
		}
	}
	

	public ResultSet ktraChungtu(){
		return chungtuRs;
	}
	
 
	public ResultSet ktraDonhang_Hoadon(){
		 
		 return Rs_HoaDon_Donhang;
		
	}
	
 
	public String KhoaSoThang()
	{ 
		return "";
	}
	
	public String CapNhatDLN()
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "";
			for(int i = 0; i < this.kiemtraDLNlst.size(); i++)
			{
				IKiemTraDLN kt = this.kiemtraDLNlst.get(i);
				query = "update "+kt.getLoai()+" set trangthai =1 where pk_seq = "+kt.getId();
				System.out.println("[DLN] "+query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Khong the khoa so thang: " + query;
				}
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
			catch (SQLException e1) {}
			return "7.Khong the Khoa so Thang: " + e.getMessage();
		}
		return "";
	}
	
	public int getSonhapkho() 
	{
		return this.sonhapkho;
	}

	public void setSonhapkho(int row) 
	{
		this.sonhapkho = row;
	}

	public int getSoxuatkho() 
	{
		return this.soxuatkho;
	}

	public void setSoxuatkho(int row)
	{
		this.soxuatkho = row;
	}

	public int getSochuyenkho() 
	{
		return this.sochuyenkho;
	}

	public void setSochuyenkho(int row) 
	{
		this.sochuyenkho = row;
	}

	public int getSodctk()
	{
		return this.sodctk;
	}

	public void setSodctk(int row) 
	{
		this.sodctk = row;
	}
 
	
	public int getSonhanhang() 
	{
		return this.sonhanhang;
	}

	public void setSonhanhang(int row) 
	{
		this.sonhanhang = row;
	}

	public int getSohdNCC() 
	{
		return this.soHDNCC;
	}

	public void setSohdNCC(int row)
	{
		this.soHDNCC = row;
	}

	public int getSoxuatkhoChuaNhanHD() 
	{
		return this.soxuatkhoCXHD;
	}

	public void setSoxuatkhoChuaNhanHD(int row) 
	{
		this.soxuatkhoCXHD = row;
	}

	public int getSoHdtc() 
	{
		return this.sohdtc;
	}

	public void setSoHdtc(int row) 
	{
		this.sohdtc = row;
	}

	public int getSoLsx() 
	{
		return this.solsx;
	}

	public void setSoLsx(int row) 
	{
		this.solsx = row;
	}

	public int getSoPhieuthu() 
	{
		return this.sophieuthu;
	}
	
	public void setSoPhieuthu(int row)
	{
		this.sophieuthu = row;
	}

	public int getSoPhieuchi() 
	{
		return this.sophieuchi;
	}

	public void setSoPhieuchi(int row) 
	{
		this.sophieuchi = row;
	}

	public int getSoBtth() 
	{
		return this.soBTTH;
	}

	public void setSoBtth(int row) 
	{
		this.soBTTH = row;
	}

	public int getChaykhauhao() 
	{
		return this.sochaykhauhao;
	}

	public void setChaykhauhao(int row) 
	{
		this.sochaykhauhao = row;
	}

	public int getChaychenhlechtigia() 
	{
		return this.sochaychenhlech;
	}

	public void setChaychenhlechtigia(int row)
	{
		this.sochaychenhlech = row;
	}

	public int getTinhgiathanh() 
	{
		return this.sotinhtigia;
	}

	public void setTinhgiathanh(int row)
	{
		this.sotinhtigia = row;
	}
	
	private void kiemtrasanpham(String tungay, String denngay, List<IKiemTraDLN> lst)
	{
		try
		{/*  

			String[] param = new String[3];
			 
			param[0] = tungay;
			param[1] = denngay;
			param[2]= this.ctyId;
			
		 
			ResultSet rs = db.getRsByPro("PRO_CHECK_DULIEUNEN", param);
			String spid = "", spma = "", spten = "", trangthai = "0";
			double toncuoi = 0;
			 
				while(rs.next())
				{
				 
					spma = rs.getString("MA");
					spten = rs.getString("TEN");
					toncuoi = rs.getDouble("SOLUONG");
					if(toncuoi > 0)
					{
					 
						IKiemTraDLN sp = new KiemTraDLN(thang, nam, rs.getString("LOAI") , trangthai);
						sp.setId(spid);
						sp.setMa(spma);
						sp.setTen(spten);
						sp.setTrangthaiNew("0");
						lst.add(sp);
						 
					}
				}
			 
		
		*/}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	 
	public static void main(String[] arg)
	{
		/*IErpKhoasothang kst=new ErpKhoaSoThang();
		kst.setUserId("100336");
		kst.setThang("11");
		kst.setNam("2015");
		kst.setCtyId("100001");
		 kst.KhoaSoKho();
	 */
		
	}
	public  boolean KhoaSoKho(){
		 
		return true;
	}
	
	private int thangks_truoc=0;
	
	
	private boolean checkkiemtra_Thang_nam_KhoaSo_HopLe(String nppid) {
		// TODO Auto-generated method stub
		try{
			String query=" SELECT TOP 1 THANGKS,NAM FROM ERP_KHOASOKETOAN WHERE CONGTY_FK = "+this.ctyId+"  order by NAM desc, THANGKS desc";
			ResultSet rs=db.get(query);
			int thang_kstruoc=10;
			int nam_kstruoc=2015;
			if(rs.next()){
				thang_kstruoc=rs.getInt("THANGKS");
				thangks_truoc=thang_kstruoc;
				nam_kstruoc=rs.getInt("NAM");
			}
			rs.close();
			int thang_kstiep=0;
			int nam_kstiep=0;
			if(thang_kstruoc ==12){
				thang_kstiep=1;
				nam_kstiep=nam_kstruoc +1;
			}else{
				thang_kstiep= thang_kstruoc +1;
				nam_kstiep=nam_kstruoc;
			}
			
			if(! this.thang.equals(""+thang_kstiep) || !this.nam.equals(""+nam_kstiep)){
				this.msg="Vui lòng chỉ được khóa sổ tháng năm kế tiếp tháng đã khóa sổ: "+ nam_kstiep +"-"+thang_kstiep;
				return false;
				
			}
			
			return true;
		}catch(Exception er){
			this.msg=er.getMessage();
			return false;
		}
		
		
	}
 
	 
 
	public String KhoaSoKeToan() 
	{
		return "";
	}

	
	public int getSoQC_NhanHang() {
		
		return this.soQc_NH;
	}

	
	public void setSoQC_NhanHang(int row) {
		
		this.soQc_NH = row;
	}

	
	public int getSoQC_LSX() {
		
		return this.soQc_LSX;
	}

	
	public void setSoQC_LSX(int row) {
		
		this.soQc_LSX = row;
	}

	
	public int getSoTH_NhanHang() {
		
		return this.soTh_NH;
	}

	
	public void setSoTH_NhanHang(int row) {
		
		this.soTh_NH = row;
	}

	
	public int getSoTH_LSX() {
		
		return this.soTh_LSX;
	}

	
	public void setSoTH_LSX(int row) {
		
		this.soTh_LSX = row;
	}

	
	public int getSoDctk() {
		
		return this.soDctk;
	}

	
	public void setSoDctk(int row) {
		
		this.soDctk = row;
	}

	
	public int getSoKiemkho() {
		
		return this.soKiemkho;
	}

	
	public void setSoKiemkho(int row) {
		
		this.soKiemkho = row;
	}

	
	public int getCoNhapKho_ChuaTH() {
		
		return this.coNhapKho_ChuaTH;
	}

	
	public void setCoNhapKho_ChuaTH(int row) {
		
		this.coNhapKho_ChuaTH = row;
	}

	
	public int getSoDoiQC() {
		
		return this.sodqc;
	}

	
	public void setSoDoiQC(int row) {
		
		this.sodqc = row;
	}
	
	@Override
	public List<IKiemTraDLN> getKiemtraDLNList() 
	{
		
		return this.kiemtraDLNlst;
	}

	@Override
	public void setKiemtraDLNList(List<IKiemTraDLN> kiemtraDLNlst) {
		this.kiemtraDLNlst = kiemtraDLNlst;
	} 
	
 
	@Override
	public void Init_kiemtradulieu() {
		
		// kiểm tra chênh lệch xuất nhập tồn,bắt buộc
		
		System.out.println("VÀO DÂY RÔI NEK :");
		if(this.thang!=null && this.thang.length() >0 && this.nam!=null && this.nam.length() >0){
			
				 
				// kiểm tra dữ liệu nền sản phẩm
				
				Init_kiemTraDuLieuNen();
				
				
				 if(this.Id.length() >0){
					init_rsketchuyen(); 
				 }else{
					 init_rs();
				 }
				
		}
	}
 
 
	private void init_rs() {
		// TODO Auto-generated method stub
		String query="";
		Utility util=new Utility();
		this.Nppid=util.getIdNhapp(this.userId);
		
		
		// 
		int thangkstruoc=0;
		int namkstruoc=0;
		
		if(Integer.parseInt(this.thang)==1){
			thangkstruoc=12;
			namkstruoc=Integer.parseInt(this.nam)-1;
		}else{
			thangkstruoc=Integer.parseInt(this.thang)-1;
			namkstruoc=Integer.parseInt(this.nam);
		}
 
	}

	private void init_rsketchuyen() {
		// TODO Auto-generated method stub
		 String  query=	 " SELECT NPP.TEN AS CHINHANH  , TK.SOHIEUTAIKHOAN,NO_.SOHIEUTAIKHOAN AS NO,CO.SOHIEUTAIKHOAN AS CO,CT.GIATRI "+ 
						 " FROM ERP_CHUNGTUKETCHUYENKETOAN  CT  "+
						 " INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ=CT.TAIKHOANKT_FK "+
						 " INNER JOIN ERP_TAIKHOANKT NO_ ON NO_.PK_SEQ= CT.TAIKHOANNO_FK "+
						 " INNER JOIN ERP_TAIKHOANKT CO ON CO.PK_SEQ= CT.TAIKHOANCO_FK "
						 + " INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=CT.NPP_FK "+
						 " where CT.GIATRI<>0  and  KHOASOKETOANLIST_FK ="+this.Id+""
						 		+ " ORDER BY NPP.TEN, CT.SOTT  " ;
		 System.out.println(query);
		 this.rsketchuyenketoan=db.get(query);	
	}

	private void Init_kiemTraDuLieuNen() {
		String tungay = nam + "-" + (Integer.parseInt(thang) > 9?thang:"0" + thang) + "-01";
		String denngay = nam + "-" + (Integer.parseInt(thang) > 9?thang:"0" + thang) + "-31";
		this.kiemtraDLNlst = new ArrayList<IKiemTraDLN>();
		kiemtrasanpham(tungay, denngay, this.kiemtraDLNlst);
	}

 
	@Override
	public ResultSet getRs_ChuyenKho_KhacNhapXuat() {
		// TODO Auto-generated method stub
		return rs_chuyenkho_khac_NX;
	}

	@Override
	public ResultSet getRs_NhanHang_ChuaCapNhatKetoan() {
		// TODO Auto-generated method stub
		return Rs_NhanHang_ChuaCapNhatKetoan;
	}

	@Override
	public List<IKiemTraLechXNT> getKiemtraLechXNTList() {
		// TODO Auto-generated method stub
		return this.kiemtralechXNTlst;
		 
	}

	@Override
	public void setKiemtraLechXNTList(List<IKiemTraLechXNT> list) {
		// TODO Auto-generated method stub
		this.kiemtralechXNTlst =list;
	}

	@Override
	public void initKSKT() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResultSet getRs_NhanHang_KHONG_GIATRI() {
		// TODO Auto-generated method stub
		return this.Rs_NhanHang_khong_giatri;
	}

	@Override
	public String getCheckDuLieuNen() {
		// TODO Auto-generated method stub
		return this.CheckDuLieuNen;
	}

	@Override
	public void setCheckDuLieuNen(String check) {
		// TODO Auto-generated method stub
		this.CheckDuLieuNen=check;
	}

	@Override
	public String getCheckNhanHangGiaTriKhong() {
		// TODO Auto-generated method stub
		return this.CheckNhanHangGiaTriKhong;
	}

	@Override
	public void setCheckNhanHangGiaTriKhong(String check) {
		// TODO Auto-generated method stub
		this.CheckNhanHangGiaTriKhong=check;
	}

	@Override
	public String getCheckDonhang_Hoadon() {
		// TODO Auto-generated method stub
		return this.CheckDonhang_Hoadon;
	}

	@Override
	public void setCheckDonhang_Hoadon(String check) {
		// TODO Auto-generated method stub
		this.CheckDonhang_Hoadon=check;
	}

	@Override
	public ResultSet getRs_Stock_Invalid() {
		// TODO Auto-generated method stub
		return this.Rs_Stock_Invalid;
	}

	@Override
	public void setId(String Id) {
		// TODO Auto-generated method stub
		this.Id=Id;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.Id;
	}

	@Override
	public void setCheckXacnhan(String CheckXacnhan) {
		// TODO Auto-generated method stub
		this.str_checkxacnhan=CheckXacnhan;
	}

	@Override
	public String getCheckXacnhan() {
		// TODO Auto-generated method stub
		return this.str_checkxacnhan;
	}

	@Override
	public boolean KetChuyenDuLieu() {
		// TODO Auto-generated method stub
		try{
			
			
			Utility util=new Utility();
			 
			
			if(!this.checkHopLe()){
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			
			
			String Id_curent="";
			if(this.Id.length()==0 ){
				String	query = " insert ERP_KHOASOKETOAN_LIST(thangksgannhat, thangks, nam, ngaytao, nguoitao,CONGTY_FK ,trangthai) " +
								" values('"+thangks_truoc+"', '" + this.thang + "', '" + this.nam + "', '" + this.getDate() + "', '" + this.userId + "',"+this.ctyId+",'0')";
				System.out.println(query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg= "Khong the khoa so thang: " + query;
					return false;
				}
				
				query = "select SCOPE_IDENTITY() as dhId";
				ResultSet rsDh = db.get(query);
				
				 
					if(rsDh.next())
					{
						Id_curent= rsDh.getString("dhId");
					}
					rsDh.close();
				 
			} 
			
			// 
			int thangkstruoc=0;
			int namkstruoc=0;
			if(Integer.parseInt(this.thang)==1){
				thangkstruoc=12;
				namkstruoc=Integer.parseInt(this.nam)-1;
			}else{
				thangkstruoc=Integer.parseInt(this.thang)-1;
				namkstruoc=Integer.parseInt(this.nam);
			}
			
			String query="SELECT PK_SEQ ,TEN,loaiNPP,TRANGTHAI FROM NHAPHANPHOI  where loaiNPP in ('0','1','2','3')";
			ResultSet rs=db.get(query);
					
			while(rs.next()){
				 String NppId=rs.getString("PK_SEQ");
				 String msg1=  LibraryKS.TinhSoDuKetChuyenCuoiThangKT(Integer.parseInt(this.thang), Integer.parseInt(this.nam),NppId,this.ctyId, (Id.length() >0?Id:Id_curent), db);
				 if(msg1.length()>0){
					 this.msg=msg1;
					 return false;
				 }
			}
			 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			if(this.Id.length()==0){
				this.Id=Id_curent;
				this.init_rsketchuyen();
			}
			
			this.msg="Kết chuyển dữ liệu thành công";
			
			return true;
		}catch(Exception er){
			er.printStackTrace();
			db.update("rollback");
			return false;
		}
		
		
	}

	private boolean checkHopLe() {
		// TODO Auto-generated method stub
		try{
			
			// kiểm tra tháng năm khóa sổ có hợp lệ không? chỉ được khóa sổ tháng kế tiếp,  
			//tháng đã khóa sổ, trong trường hợp chạy lại thì cũng chekc điều kiện như vậy
			if(!checkkiemtra_Thang_nam_KhoaSo_HopLe(this.Nppid)){
				return false;
			}
			 
			this.Init_kiemtradulieu();
			 
			// check dữ liệu nền
			if(this.kiemtraDLNlst.size() >0 ){
				if(!this.CheckDuLieuNen.equals("1")){
					this.setMsg("Vui lòng xác nhận   chấp nhận cho phép dữ nền ngưng hoạt động để thực hiện tiếp tục  khóa sổ.");
					return false;
				}
			}
		 
			for(int i=0;i<mang.length ;i++){
					 //System.out.println(this.getCheckXacnhan());
					 if(this.getCheckXacnhan().indexOf(mang[i]) < 0) { 
						 this.msg="Vui lòng xác nhận dữ liệu kiểm tra ở bước 1:" +mangten[i];
						 return false;
					 }
			}
			
			return true;
			
		}catch(Exception er){
			this.msg=er.getMessage();
			return false;
		}
		
	}

	@Override
	public String[] getGiaTriMangCheck() {
		// TODO Auto-generated method stub
		return this.mang;
	}

	@Override
	public String[] getTenMangCheck() {
		// TODO Auto-generated method stub
		return this.mangten;
	}

	@Override
	public ResultSet getRsKetchuyenketoan() {
		// TODO Auto-generated method stub
		return this.rsketchuyenketoan;
	}

	@Override
	public boolean khoasoketoan() {
		// TODO Auto-generated method stub
		try{
			// kiểm tra đã kết chuyển chưa?
			
			
			
			if(this.Id.length() >0){
				// đã thực hiện kiểm tra dữ liệu
				
				String query="select * from ERP_CHUNGTUKETCHUYENKETOAN where KHOASOKETOANLIST_FK="+this.Id;
				
				ResultSet rs=db.get(query);
				if(!rs.next()){

					this.msg="Vui lòng xác nhận đã kết chuyển kế toán cuối tháng";
					return false;
				}
				rs.close();
				
				Utility util=new Utility();
				this.Nppid=util.getIdNhapp(this.userId);
				
				
				if(!this.checkHopLe()){
					return false;
				}
				// kiểm tra đã check kiểm tra dữ liệu báo cáo chưa?
				if(this.getCheckXacnhan().indexOf("20")>=0){
					this.msg="Vui lòng xác nhận đã kiểm tra báo cáo cuối tháng";
					return false;
				}
				db.getConnection().setAutoCommit(false);
				
				 
					query=" insert into ERP_KHOASOKETOAN ( THANGKSGANNHAT,THANGKS,NAM,NGAYTAO,NGUOITAO,CONGTY_FK ) " +
							" values ("+this.thangks_truoc+","+this.thang+","+this.nam+",'"+this.getDate()+"',"+this.userId+", "+this.ctyId+")";
					 
					if(!db.update(query)){
						this.msg="Lỗi cập nhật dữ liệu,vui lòng kiểm tra lại :"+query;
						db.getConnection().rollback();
						return false;
					}
					  query="SELECT PK_SEQ ,TEN,loaiNPP,TRANGTHAI FROM NHAPHANPHOI  where loaiNPP in ('0','1','2','3')";
					ResultSet rsnpp =db.get(query); 
					while(rsnpp.next()){
						String nppid=rsnpp.getString("PK_SEQ");
						 
						String msg1= LibraryKS.Tinhcuoiky(db, nppid, this.nam, this.thang);
						if( msg1.length() >0){
							db.getConnection().rollback();
							this.msg="Lỗi cập nhật dữ liệu,vui lòng kiểm tra lại :"+query;
							return false;
						}
					}
					
					query="UPDATE ERP_KHOASOKETOAN_LIST SET TRANGTHAI=1 WHERE PK_SEQ="+this.Id;
					if(!db.update(query)){
						db.getConnection().rollback();
						this.msg="Lỗi cập nhật dữ liệu,vui lòng kiểm tra lại :"+query;
						return false;
					}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				
			} else{
				 
				this.msg="Vui lòng xác nhận đã kết chuyển kế toán cuối tháng";
				return false;
			}
			return true;
		}catch(Exception er){
			this.msg=er.getMessage();
		 
			er.printStackTrace();
			return false;
		}
		 
	}

	@Override
	public void init_display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResultSet getKiemtraDLN() {
		// TODO Auto-generated method stub
		return null;
	}

}
