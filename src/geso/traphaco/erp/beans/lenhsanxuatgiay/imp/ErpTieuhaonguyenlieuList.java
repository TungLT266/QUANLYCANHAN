package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.Kho_Lib;
 
import geso.traphaco.erp.util.Library;
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpTieuhaonguyenlieulist;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;

public class ErpTieuhaonguyenlieuList extends Phan_Trang implements IErpTieuhaonguyenlieulist {

	private String UserId;
	private String LsxId;
	private String CongdoanId;
	private String Congdoandiengiai;
	private String trangthai;
	private String CtyId;
	private String Masanpham;
	private String Tensanpham;
	private String Ngaybatdau;
	private String Ngayketthuc;
	private String Nhamay;
	private String IdTieuHao;
	private String sochungtu="", xuongId;
	
	private dbutils db;
	private int num;
	private int[] listPages;
	private int currentPages;
	
	private String msg;
	private String nppId;
	
	
	private ResultSet rslist, xuongrs;
	public ErpTieuhaonguyenlieuList()
	{
			this.trangthai="";
			currentPages = 1;
			num = 1;
		 db = new dbutils();
		 CongdoanId="";
		 CtyId="";
		 Congdoandiengiai="";
		 Ngaybatdau="";
		 Ngayketthuc="";
		 Nhamay="";
		 this.sochungtu="";
		 this.xuongId =""; 
			 
		 this.LsxId="";
		 this.nppId = "";
	}
	
	
	public void setSochungtu(String sochungtu) {
		this.sochungtu = sochungtu;
	}
	public String getSochungtu() {
		return sochungtu;
	}
	
	
	public String getUserId() {
		
		return this.UserId;
	}

	
	public void setUserId(String _userId) {
		
		this.UserId=_userId;
	}

	
	public String getLsxId() {
		
		return this.LsxId;
	}

	
	public void setLsxId(String _LsxId) {
		
		this.LsxId=_LsxId;
	}

	
	public String getCongDoanId() {
		
		return this.CongdoanId;
	}

	
	public void SetCongDoanId(String cdid) {
		
		this.CongdoanId=cdid;
	}

	
	public String getCtyId() {
		
		return this.CtyId;
	}

	
	public void SetCtyId(String ctyid) {
		
		this.CtyId=ctyid;
	}

	
	public String getSanPhamMa() {
		
		return this.Masanpham;
	}

	
	public void SetSanPhamMa(String SanPhamMa) {
		
		this.Masanpham=SanPhamMa;
	}

	
	public String getSanPhamTen() {
		
		return this.Tensanpham;
	}

	
	public void SetSanPhamTen(String SanPhamTen) {
		
		this.Tensanpham=SanPhamTen;
	}

	
	public String getNgayBanDau() {
		
		return this.Ngaybatdau;
	}

	
	public void SetNgayBanDau(String ngaybd) {
		
		this.Ngaybatdau=ngaybd;
	}

	
	public String getNgayKetThuc() {
		
		return this.Ngayketthuc;
	}

	
	public void SetNgayKetThuc(String NgayKt) {
		
		this.Ngayketthuc=NgayKt;
	}

	
	public String getNhamay() {
		
		return this.Nhamay;
	}

	
	public void SetNhamay(String _Nhamay) {
		
		this.Nhamay=_Nhamay;
	}

	
	public String getTrangthai() {
		
		return this.trangthai;
	}

	
	public void setTrangthai(String tt) {
		
		this.trangthai=tt;
	}

	public int getNum()
	{
		return this.num;
	}
	
	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}

	public int getCurrentPage()
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current) 
	{
		this.currentPages = current;
	}

	public int[] getListPages() 
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages) 
	{
		this.listPages = listPages;
	}

	public int getLastPage() 
	{
		ResultSet rs = db.get("select count(*) as c from ERP_LENHSANXUAT_TIEUHAO");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	
	public void Init(String sql) {
		String query="";
		System.out.println("npp : "+this.nppId);
		if(sql.equals("")){
			
			query=	
				" SELECT DISTINCT A.PK_SEQ AS CHUNGTU, CD.PK_SEQ AS CDID, LSX.PK_SEQ, "+
				" A.DIENGIAI AS DIENGIAITH,CD.DIENGIAI AS DIENGIAI ,NM.TENNHAMAY, "+ 
				" LSX.NGAYBATDAU,LSX.NGAYDUKIENHT,A.TRANGTHAI,A.NGAYTAO,A.NGAYSUA,   "+
				" NT.TEN  AS NGUOITAO,NS.TEN AS NGUOISUA,A.ngaytieuhao   "+
				" FROM ERP_TIEUHAONGUYENLIEU A    "+
				" INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON A.LENHSANXUAT_FK=LSX.PK_SEQ "+ 
				" left JOIN ERP_CONGDOANSANXUAT_GIAY CD ON CD.PK_SEQ=A.CONGDOAN_FK  "+
				" left JOIN ERP_NHAMAY NM ON NM.PK_SEQ=CD.NHAMAY_FK  "+
				" INNER JOIN NHANVIEN NT ON NT.PK_SEQ=A.NGUOITAO  "+
				 " INNER JOIN NHANVIEN  NS ON NS.PK_SEQ=A.NGUOISUA "
				 + " WHERE  1=1 ";
				if(this.trangthai.length() >0){
					query=query + " and a.trangthai='"+this.trangthai+"'";
				}
				
				if(this.Ngaybatdau.length()  > 0){
					query=query + " and A.ngaytieuhao  >= '"+this.Ngaybatdau+"'";
				}
				if(this.Ngayketthuc.length()  > 0){
					query=query + " and A.ngaytieuhao  <= '"+this.Ngayketthuc+"'";
				}
				if( this.sochungtu.length() >0)
					query=query + " and isnull(cast(A.PK_SEQ as nvarchar(30)),'') like  N'%"+this.sochungtu+"%'";
				if( this.LsxId.length() >0)
					query=query + " and isnull(cast(LSX.PK_SEQ as nvarchar(30)),'') like  N'%"+this.LsxId+"%'"
							+ " or A.LENHSANXUAT_FK in (select pk_seq from ERP_LENHSANXUAT_giay where SOLenhsanxuat like '%"+this.LsxId+"%')";
				if(this.xuongId.length()>0)
					query = query + " and NM.pk_seq = '"+this.xuongId+"'";
			
		}else{
			query=sql;
		}
		System.out.println("init ccc: "+query);
		//System.out.println("Get data : "+query);
		//rslist=createSplittingData(50, 10, "pk_seq desc, trangthai asc ", query);
		rslist=createSplittingData(50, 10, "ngaytieuhao desc, CHUNGTU desc ", query);
		
		this.xuongrs = db.get("SELECT PK_SEQ, tennhamay FROM ERP_NHAMAY WHERE TRANGTHAI = 1");
	}

	
	public ResultSet GetRsListTieuHao() {
		
		return rslist;
	}


	
	public void DBclose() {
		
		try{
			if(rslist!=null)
			rslist.close();
			
			if(xuongrs!=null)
				xuongrs.close();
			
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
	

	
	public String DeleteTieuHao() {
		
		try{
		 
			 
			/*******END ****************************************/
			db.getConnection().setAutoCommit(false);
			boolean bien_=this.revert_kho_tieuhao(this.IdTieuHao,false);
			if(!bien_){
				db.getConnection().rollback();
				return this.msg;
			}
			
			
			
			String	query="update ERP_TIEUHAONGUYENLIEU set trangthai='2'  where pk_seq="+this.IdTieuHao;
			System.out.println("Get Lenh San xuat: "+query);
			if(!db.update(query))
			{
				this.msg = "4.Khong the cap nhat ERP_LENHSANXUAT_TIEUHAO_CHITIET: " + query;
				db.getConnection().rollback();
				return this.msg;
			}
			
		 
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		
		}catch(Exception er){
			
			db.update("rollback");
			return er.toString();
		}
		return "";
	}


	
	public void setIdTieuHao(String idtieuhao) {
		
		this.IdTieuHao=idtieuhao;
	}


	
	public String getIdTieuhao() {
		
		return this.IdTieuHao;
	}


	
	public String getXuongId() {
		
		return this.xuongId;
	}


	
	public void setXuongId(String xuongId) {
		this.xuongId = xuongId;
		
	}


	
	public ResultSet getXuongRs() {
		
		return this.xuongrs;
	}


	
	public void setXuongRs(ResultSet xuongRs) {
		
		this.xuongrs = xuongRs;
	}


	public String getNppId() {
		return nppId;
	}


	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	private boolean revert_kho_tieuhao(String sochungtu,boolean ischot ) {
		// TODO Auto-generated method stub
		try{
			db.getConnection().setAutoCommit(false);
			
			Utility_Kho util_kho =new Utility_Kho();
			
			String query=" select b.nsx_fk, b.doituongid ,  isnull( bin.PK_SEQ ,0)  as bin_fk  ,a.NGAYTIEUHAO ,b.KHOTT_FK as kho_fk, b.SANPHAM_FK, N'Tiêu hao lệnh sản xuất ' as loaichungtu, cast(a.PK_SEQ as varchar(10)) as machungtu, a.NGAYTIEUHAO as ngaychungtu, a.NGAYTIEUHAO, "+
					 " isnull(bin.ma,'') as vitri, b.solo, b.ngayhethan, b.ngaynhapkho, ISNULL(b.mathung,'') as mathung, ISNULL(b.mame,'') as mame,    " +
					 " isnull(b.maphieu,'') as maphieu, isnull(b.maphieudinhtinh,'')  as phieudt, isnull(b.phieueo,'') as phieueo , " +
					 " isnull(b.MArq,'')  as MARQ, b.hamam as hamam, b.hamluong as hamluong, 0 as NHAP ,	b.SOLUONG as XUAT "+
					 " from ERP_TIEUHAONGUYENLIEU a "
					 + " INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=A.LENHSANXUAT_FK   "+
					 " inner join  ERP_LENHSANXUAT_TIEUHAO_CHITIET b on a.pk_seq = b.TIEUHAONGUYENLIEU_FK "+
					 " inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+ 
					 " left join ERP_BIN bin on b.bin_fk = bin.pk_seq  " +
					 " where  a.trangthai = '0' and a.pk_seq="+sochungtu;
		ResultSet rs=db.get(query);
		while(rs.next()){
			
			Kho_Lib kholib=new Kho_Lib();
			 
			kholib.setLoaichungtu("erpHuylenhsanxuat.java 1091 :  ERP_TIEUHAONGUYENLIEU"+ sochungtu);
			
			kholib.setNgaychungtu(rs.getString("NGAYTIEUHAO"));
			 
			kholib.setKhottId(rs.getString("kho_fk"));
			
			kholib.setBinId( rs.getString("bin_fk") );
		 
			
			kholib.setSolo( rs.getString("solo"));
			String spid=  rs.getString("SANPHAM_FK");
			kholib.setSanphamId(spid);
			
			
			kholib.setMame( rs.getString("mame"));
			kholib.setMathung(rs.getString("mathung"));
			kholib.setMaphieu(rs.getString("maphieu"));
			
			kholib.setMaphieudinhtinh(rs.getString("phieudt"));
			kholib.setPhieuEo(rs.getString("phieueo"));
			
			kholib.setNgayhethan(rs.getString("ngayhethan") );
			kholib.setNgaysanxuat("");
			
			
			kholib.setNgaynhapkho(rs.getString("ngaynhapkho"));
			 
			kholib.setMARQ(rs.getString("MARQ"));
			kholib.setDoituongId(rs.getString("doituongid"));
			kholib.setLoaidoituong("5");
		 
			
			kholib.setHamluong(rs.getString("hamluong"));
			kholib.setHamam(rs.getString("hamam"));
			 kholib.setNsxId(rs.getString("nsx_fk"));
			double soluongct= rs.getDouble("XUAT");
			if(ischot){
	    	kholib.setBooked((-1)*soluongct);
			kholib.setSoluong((-1)* soluongct);
			kholib.setAvailable(0);
			} else{
				kholib.setBooked((-1)*soluongct);
				kholib.setSoluong(0);
				kholib.setAvailable(soluongct);
			}
			String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
		    if( msg1.length() >0)
			{
				// this.msg = msg1;
		    	this.msg = msg1;
				db.getConnection().rollback();
				return false;
				
			}
		}
		rs.close();
		}catch(Exception er){
			this.msg=er.getMessage();
			return false;
		}
		return true;
	}
		
		 

	@Override
	public String Chottieuhao(String chungtuid) {
		// TODO Auto-generated method stub
		
		try{

			db.getConnection().setAutoCommit(false);
  
			boolean bien_=this.revert_kho_tieuhao(chungtuid,true);
			if(!bien_){
				db.getConnection().rollback();
				return this.msg;
			}
			
			
			 
			 
	 
			String query=" UPDATE ERP_TIEUHAONGUYENLIEU SET TRANGTHAI=1 WHERE PK_SEQ="+chungtuid;
			 if(db.updateReturnInt(query)!=1)
				{
					this.msg = "4.Không thể cập nhật ERP_KHOTT_SANPHAM: " + query;
					db.getConnection().rollback();
					return this.msg;
			}
			 Library lib=new Library();
			 
			 String msg1=lib.capnhatketoan_Xuat_Tieuhaolsx(this.UserId, db, chungtuid, false, this.CtyId);
			 
			 if(msg1.length() >0){
			 	this.msg = msg1;
				db.getConnection().rollback();
				return this.msg;
			 }
			 
			 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		
		}catch(Exception er){
			er.printStackTrace();
			return er.getMessage();
			
		}
		return "";
	}
	
	
	
}
