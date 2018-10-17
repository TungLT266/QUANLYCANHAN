package geso.traphaco.distributor.beans.thutienNPP.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.thutienNPP.IErpThutienNPP;
import geso.traphaco.distributor.beans.thutienNPP.IHoadonNPP;
import geso.traphaco.distributor.beans.thutienNPP.imp.HoadonNPP;

public class ErpThutienNPP implements IErpThutienNPP 
{
	String userId;
	String id;
	String ngaychungtu;
	String ngayghiso;
	String ctyId;
	String lydonop;
	String nppId;
	String add_check;
	ResultSet nppRs;
	
	String hinhthucTT;
	
	String[] hdId_old;
	
	String tungay;
	String denngay;
	
	String hdId;
	ResultSet hdTCRs;
	
	String htttId;
	ResultSet htttRs;
	
	String ndId;
	ResultSet ndRs;
	
	String soin;
	
	int checkDN;  //4: Doi tac
	
	String DoiTuongTamUng;
	
	String chonthutuHD;
	
	String nccId="";
	ResultSet nccRs;
	
	String nvtuId="";
	ResultSet nvtuRs;
	
	String nganhangId;
	ResultSet nganhangRs;
	String chinhanhId;
	ResultSet chinhanhRs;
	ResultSet createRsLoc;
	
	String nguoinoptien;
	String noidungtt;
	String sotientt;
	String sotienttNT;
	
	String sochungtu;
	
	String sotiendanop;
	String sotienthuthem;
	
	String hoadonId;
	List<IHoadonNPP> hoadonList;
	
	Hashtable<String,Double> No_KhachHangList;
	
	String msg;
	String thuduoc;
	String thuduocNT;
	String bpkinhdoanh;
	String cpnganhangNT;
	String chenhlech;
	String chenhlechNT;
	String tongVND;
	String tongNT;
	String tigia;
	String Tigia_hoadon;
	
	String nppIds;
	String hdIds;
	String khIds;
	String noptienIds;
	String nhanvienGNIds;
	String nhanvienBHIds;
	
	String chietkhau;
	String chietkhauNT;
	
	String DoiTuongDinhKhoan;
	String DoituongdinhkhoanId;
	String MaTenDoiTuongDinhKhoan;
	String dinhkhoanco;
	String dinhkhoancoId;
	
	ResultSet KhRs;
	ResultSet NhanvienGNRs;
	ResultSet NhanvienBHRs;
	ResultSet NoptienRs;
	
	ResultSet sotkRs;
	dbutils db;
	Utility util;
	
	String Khid;
	ResultSet Khlist;
		
	//hóa đơn thêm
	String add_makh;
	String add_madt;
	String add_idhd;
	String add_sohoadon;
	String add_ngayhd;
	String add_trangthai;
	String add_tongsotien;
	String add_thanhtoan;
	String add_conlai;
	String add_sotienduadu;
	
	//Hết thêm hóa đơn
	public ErpThutienNPP()
	{
		//
		this.add_check="";
		this.add_makh="";
		this.add_madt="";
		this.add_idhd="";
		this.add_sohoadon="";
		this.add_ngayhd="";
		this.add_trangthai="";
		this.add_tongsotien="";
		this.add_thanhtoan="0";
		this.add_conlai="";
		this.add_sotienduadu="";
		//
		this.tungay= "";
		this.denngay = "";
		this.id = "";
		this.ctyId = "";
		this.ngaychungtu = "";
		this.ngayghiso = "";
		this.nppId = "";
		this.htttId = "";
		this.chonthutuHD = "";
		this.ndId = "100000";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.nguoinoptien = "";
		this.noidungtt = "";
		this.sotientt = "0";
		this.sotienttNT = "0";
		this.hoadonId = "";
		this.msg = "";
		this.sochungtu = "";
		this.sotiendanop = "0";
		this.sotienthuthem = "0";
		this.thuduoc = "0";
		this.thuduocNT = "0";
		this.bpkinhdoanh = "";
		this.cpnganhangNT = "0";
		this.chenhlech = "0";
		this.chenhlechNT = "0";
		this.tongNT = "0";
		this.tongVND = "0";
		this.lydonop= "";
		this.chietkhauNT= "0";
		Tigia_hoadon="";
		this.tigia = "1";
		
		this.checkDN=0;
		this.soin="";
		
		this.nppIds="";
		this.hdIds="";
		this.khIds= "";
		this.nhanvienBHIds= "";
		this.nhanvienGNIds="";
		this.noptienIds = "";
		
		this.dinhkhoanco="";
		this.dinhkhoancoId= "";
		this.DoiTuongDinhKhoan= "";
		
		this.DoiTuongTamUng="";
		this.nccId="";
		this.nvtuId="";
		
		this.hdId= "";
		this.hinhthucTT="Tiền mặt";
		
		this.hoadonList = new ArrayList<IHoadonNPP>();
		this.No_KhachHangList = new Hashtable<String, Double>();
		
		this.Khid ="";
		
		this.db = new dbutils();
		this.util=new Utility();
	}
	
	public ErpThutienNPP(String id)
	{
		this.add_check="";
		this.tungay= "";
		this.denngay = "";
		this.id = id;
		this.ctyId = "";
		this.ngaychungtu = "";
		this.ngayghiso = "";
		this.nppId = "";
		this.htttId = "";
		this.ndId = "100000";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.nguoinoptien = "";
		this.noidungtt = "";
		this.sotientt = "0";
		this.sotienttNT = "0";
		this.hoadonId = "";
		this.msg = "";
		this.sochungtu = "";
		this.sotiendanop = "0";
		this.sotienthuthem = "0";
		this.thuduoc = "0";
		this.thuduocNT = "0";
		this.bpkinhdoanh = "";
		this.cpnganhangNT = "0";
		this.chenhlech = "0";
		this.chenhlechNT = "0";
		this.tongNT = "0";
		this.tongVND = "0";
		this.lydonop= "";
		this.chietkhauNT = "0";
		this.chonthutuHD = "";
		this.soin="";
		this.checkDN=0;
		
		this.nppIds="";
		this.hdIds="";
		this.khIds= "";
		this.nhanvienBHIds= "";
		this.nhanvienGNIds="";
		this.noptienIds = "";
		
		this.dinhkhoanco="";
		this.dinhkhoancoId= "";
		this.DoiTuongDinhKhoan= "";
		
		this.DoiTuongTamUng="";
		this.nccId="";
		this.nvtuId="";
		
		this.hdId= "";
		this.hinhthucTT="Tiền mặt";
		
		this.tigia = "1";
		this.hoadonList = new ArrayList<IHoadonNPP>();
		this.No_KhachHangList = new Hashtable<String, Double>();
		
		this.Khid="";
		
		//
		this.add_makh="";
		this.add_madt="";
		this.add_idhd="";
		this.add_sohoadon="";
		this.add_ngayhd="";
		this.add_trangthai="";
		this.add_tongsotien="";
		this.add_thanhtoan="0";
		this.add_conlai="";
		this.add_sotienduadu="";
		//
		
		this.util=new Utility();
		this.db = new dbutils();
	}
	
	  public Integer getCheckDN() 
	  {			
		return this.checkDN;
	 }
		
	public void setCheckDN(Integer checkDN) 
	{			
		this.checkDN=checkDN;
	}
	
	public String getDoiTuongTamUng() {
		return DoiTuongTamUng;
	}

	public void setDoiTuongTamUng(String DoiTuongTamUng) {
		this.DoiTuongTamUng= DoiTuongTamUng;
		
	}
	
   public String getNccId() {
		
		return this.nccId;
	}

	
	public void setNccId(String nccId) {
		
		this.nccId=nccId;
	}
	
	   public String getSoin() {
			
			return this.soin;
		}

		
		public void setSoin(String soin) {
			
			this.soin=soin;
		}
	
	public void setNccRs(ResultSet nccRs)
	{
		this.nccRs = nccRs;
	}
	public ResultSet getNccRs() 
	{
		return nccRs;
	}
	
    public String getNvtuId() {
		
		return this.nvtuId;
	}

	
	public void setNvtuId(String nvtuId) {
		
		this.nvtuId=nvtuId;
	}
	
	public ResultSet getNvtuRs() 
	{
		return nvtuRs;
	}
	public void setNvtuRs(ResultSet nvtuRs) 
	{
		this.nvtuRs = nvtuRs;
	}
	
	public void setChietkhau(String chietkhau) 
	{
		this.chietkhau = chietkhau;
	}
	public String getChietkhau()
	{
		return chietkhau;
	}
	
	public void setChietkhauNT(String chietkhauNT) 
	{
		this.chietkhauNT = chietkhauNT;
	}
	public String getChietkhauNT()
	{
		return chietkhauNT;
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

	public String getId()
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getNgaychungtu() 
	{
		return this.ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) 
	{
		this.ngaychungtu = ngaychungtu;
	}

	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;	
	}

	public ResultSet getNppRs()
	{
		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) 
	{
		this.nppRs = nppRs;
	}

	public ResultSet getHdTCRs()
	{
		return this.hdTCRs;
	}

	public void setHdTCRs(ResultSet hdTCRs) 
	{
		this.hdTCRs = hdTCRs;
	}
	
	public ResultSet getSotkRs()
	{
		return this.sotkRs;
	}

	public void setSotkRs(ResultSet sotkRs) 
	{
		this.sotkRs = sotkRs;
	}

	public String getHtttId() 
	{
		return this.htttId;
	}

	public void setHtttId(String htttId)
	{
		this.htttId = htttId;
	}

	public ResultSet getHtttRs() 
	{
		return this.htttRs;
	}

	public void setHtttRs(ResultSet htttRs)
	{
		this.htttRs = htttRs;	
	}

	public String getNganhangId()
	{
		return this.nganhangId;
	}

	public void setNganhangId(String nganhangId)
	{
		this.nganhangId = nganhangId;
	}

	public ResultSet getNganhangRs() 
	{
		return this.nganhangRs;
	}

	public void setNganhangRs(ResultSet nganhangRs) 
	{
		this.nganhangRs = nganhangRs;
	}

	public String getChinhanhId() 
	{
		return this.chinhanhId;
	}

	public void setChinhanhId(String cnId)
	{
		this.chinhanhId = cnId;
	}

	public ResultSet getChinhanhRs()
	{
		return this.chinhanhRs;
	}

	public void setChinhanhRs(ResultSet chinhanhRs) 
	{
		this.chinhanhRs = chinhanhRs;
	}

	public String getNguoinoptien() 
	{
		return this.nguoinoptien;
	}

	public void setNguoinoptien(String nguoinoptien)
	{
		this.nguoinoptien = nguoinoptien;
	}

	public String getNoidungtt()
	{
		return this.noidungtt;
	}

	public void setNoidungtt(String ndtt) 
	{
		this.noidungtt = ndtt;
	}

	public String getSotientt() 
	{
		return this.sotientt;
	}

	public void setSotientt(String sotientt) 
	{
		this.sotientt = sotientt;
	}

	public String getSotienttNT() 
	{
		return this.sotienttNT;
	}

	public void setSotienttNT(String sotienttNT) 
	{
		this.sotienttNT = sotienttNT;
	}

	public String getHoadonIds() 
	{
		return this.hoadonId;
	}

	public void setHoadonIds(String hdIds) 
	{
		this.hoadonId = hdIds;
	}

	public List<IHoadonNPP> getHoadonRs() 
	{
		return this.hoadonList;
	}

	public void setHoadonRs(List<IHoadonNPP> hoadonRs)
	{
		this.hoadonList = hoadonRs;
	}

	public String getThuduoc() 
	{
		return this.thuduoc;
	}

	public void setThuduoc(String thuduoc)
	{
		this.thuduoc = thuduoc;
	}

	public String getThuduocNT() 
	{
		return this.thuduocNT;
	}

	public void setThuduocNT(String thuduocNT)
	{
		this.thuduocNT = thuduocNT;
	}

	public String getBpkinhdoanh() 
	{
		return this.bpkinhdoanh;
	}

	public void setBpkinhdoanh(String Bpkinhdoanh)
	{
		this.bpkinhdoanh = Bpkinhdoanh;
	}

	public String getChiphinganhangNT() 
	{
		return this.cpnganhangNT;
	}

	public void setChiphinganhangNT(String cpnganhangNT)
	{
		this.cpnganhangNT = cpnganhangNT;
	}

	public String getChenhlech() 
	{
		return this.chenhlech;
	}

	public void setChenhlech(String chenhlech)
	{
		this.chenhlech = chenhlech;
	}

	public String getChenhlechNT() 
	{
		return this.chenhlechNT;
	}

	public void setChenhlechNT(String chenhlechNT)
	{
		this.chenhlechNT = chenhlechNT;
	}

	public String getTigia() 
	{
		return this.tigia;
	}

	public void setTigia(String tigia)
	{
		this.tigia= tigia;
	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public String getTongNT() 
	{
		return this.tongNT;
	}

	public void setTongNT(String tongNT)
	{
		this.tongNT = tongNT;
	}

	public String getTongVND() 
	{
		return this.tongVND;
	}

	public void setTongVND(String tongVND)
	{
		this.tongVND = tongVND;
	}

	public boolean createTTHD() 
	{
		System.out.println("vào đây rồi"+ this.hoadonList.size());
		this.getNppInfo();
		if(this.hoadonList.size() < 0)
		{
			this.msg = "Vui lòng chọn hóa đơn thu tiền";// KIEM TRA NEU CHUA CHON HOA DON NAO
			return false;
		}

		
		if(this.sotientt.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số tiền thanh toán"; 
			return false;
		}
	/*	if(Double.parseDouble(this.sotientt.replace(",", "")) - Double.parseDouble(this.thuduoc.replace(",", "")) < 0)
	    {
	    	this.msg = "Số tiền hóa đơn không được vượt quá Tổng tiền  ";
			return false;
	    }*/
		
		try 
		{
		
			
			db.getConnection().setAutoCommit(false);

			// LAY SOIN MAX	dùng để thực hiện khi IN PDF		
			String query = "";
			query = " SELECT  ISNULL(MAX(ISNULL(SOIN,0)),0) as SOIN_MAX from ERP_THUTIENNPP where trangthai != '2' " +
					" and npp_fk = '"+ this.nppId +"' " +
					" and MONTH(NGAYCHUNGTU) = MONTH(GETDATE()) and YEAR(NGAYCHUNGTU) = YEAR(GETDATE())";
			
			ResultSet laySOIN = db.get(query);
			String chuoi="";
			if(laySOIN!= null)
			{
				while(laySOIN.next())
				{
					// tạm thời bỏ ràng 3 số 0001 --> chuyển qua bắt đầu từ số 1
					//chuoi = ("000"+ (laySOIN.getLong("SOIN_MAX")>0 ? (laySOIN.getLong("SOIN_MAX")+1) :"1"));
					chuoi = ""+(laySOIN.getLong("SOIN_MAX")>0 ? (laySOIN.getLong("SOIN_MAX")+1) :"1");
					
				}laySOIN.close();
			}
			//chuoi = chuoi.substring(chuoi.length() - 3, chuoi.length());
			
			System.out.println("So In phieu thu:"+ chuoi);
			this.soin =  chuoi;
			
			if(this.nhanvienBHIds.trim().length() <= 0) this.nhanvienBHIds = "NULL";
			if(this.nhanvienGNIds.trim().length() <= 0) this.nhanvienGNIds = "NULL";
			if(this.khIds.trim().length() <= 0) this.khIds = "NULL";
			if(this.nppIds.trim().length() <= 0) this.nppIds = "NULL";
				
			
		    query = "";
		    String tennguoinoptien= "";
		    
		 // LAY TEN NGUOI NOP TIEN CUA CAC PHIEU NOP TIEN
		 // NEU CO CHON PHIEU NOP TIEN DE THUC HIEN THU TIEN
			if(this.noptienIds.trim().length() > 0) // LAY TEN NGUOI NOP TIEN DE HIEN THI VAO TRANG TONG 
			{
				query = 
			     "           SELECT distinct case when  nvgn_fk is not null then  nvgn.ten " +
			     "                        when  nvbh_fk is not null then nvbh.ten" +
			     "                        when  npp_dat_fk is not null then npp.ten" +
			     "                        else  kh.ten end  as nguoint " +
			     "           FROM 	NOPTIEN nt left join NHANVIENGIAONHAN nvgn on nt.nvgn_fk = nvgn.pk_seq" +
			     "                            left join DAIDIENKINHDOANH nvbh on nt.nvbh_fk = nvbh.pk_seq" +
			     "                            left join KHACHHANG kh on nt.khachhang_fk= kh.pk_seq  " +
			     "							  left join NHAPHANPHOI npp on nt.npp_dat_fk= npp.pk_seq  " +
			     "           WHERE  nt.pk_seq in ("+ this.noptienIds +")   ";
				ResultSet rsLayten = db.get(query);
				if(rsLayten!= null)
				{
					while(rsLayten.next())
					{
						tennguoinoptien = tennguoinoptien + rsLayten.getString("nguoint") + ',';
					}
					rsLayten.close();
				}
				
				if(tennguoinoptien.trim().length() > 0) tennguoinoptien= tennguoinoptien.substring(0, tennguoinoptien.length() -1 );
			}
		    
			//sotientt= TỔNG TIỀN, thuduoc = TỔNG THANH TOÁN
			double Conlai= Double.parseDouble(this.sotientt.replaceAll(",", "")) - Double.parseDouble(this.thuduoc.replaceAll(",", ""));
			
			if(Double.parseDouble(this.sotiendanop.replaceAll(",", "")) > 0)//nếu phiếu thu sử dụng phiếu nộp tiền (khi load phiếu nộp tiền, tự động lấy số tiền trong phiếu nộp)
			{				
				
				query = "	INSERT ERP_THUTIENNPP( SOTIENTHU,SOTIENTT ,CONLAI ,NGAYCHUNGTU,TRANGTHAI, GHICHU, NGUOINOPTIEN, " +
						" 	NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA ,BPKINHDOANH, NPP_FK, SOIN, NVBH_FK, NVGN_FK, KHACHHANG_FK, NPP_DAT_FK, NOPTIENIDS, TENNGUOINOP, HINHTHUCTT ) " +
						"	VALUES(" +this.thuduoc.replace(",", "")+ ", "+this.sotientt.replace(",", "") + ", "+Conlai+ ","+
						" '" + this.ngaychungtu + "', '0', N'" + this.noidungtt + "', N'" + this.nguoinoptien + "', " +
						" '" +  getDateTime() + "', '" + this.userId + "', '"+  getDateTime() +"', '"+ this.userId +"', N'"+ this.bpkinhdoanh +"','"+ this.nppId +"', '"+ this.soin +"'," +
						" "+ this.nhanvienBHIds +","+ this.nhanvienGNIds +" , "+ this.khIds +" , "+ this.nppIds +" , '"+ this.noptienIds +"', N'"+ tennguoinoptien  +"', N'"+ this.hinhthucTT +"')";
				System.out.println(query);
				if(!db.update(query))
				{
					this.msg = "KHONG THE TAO MOI ERP_THUTIENNPP: " + query;
					System.out.println("[ErpThutien.createTTHD] error message:" + this.msg);
					db.getConnection().rollback();
					return false;
				}
				
			
				if(Double.parseDouble(this.sotienthuthem.replaceAll(",", "")) > 0)//ô tổng thu tiền trực tiếp, sẽ tự động tạo ra phiếu nộp tiền
				{
					// Insert thêm vào bảng Nộp tiền
					query = "	INSERT INTO NOPTIEN(NGAYNOP, NVBH_FK, NVGN_FK, KHACHHANG_FK, NPP_DAT_FK , ngaytao, ngaysua, nguoitao, nguoisua, TRANGTHAI, SOTIEN, NPP_FK) \n"+
					        " 	VALUES ('"+ this.ngaychungtu +"', "+ this.nhanvienBHIds +","+ this.nhanvienGNIds +" , "+ this.khIds +", "+ this.nppIds +", '"+ getDateTime() +"', '"+  getDateTime() +"', \n" +
							" '"+ this.userId +"', '"+ this.userId +"', '0','"+ this.sotienthuthem.replaceAll(",", "") +"','"+ this.nppId +"'  ) \n";
					
					if (!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Khong the luu vao table 'NOPTIEN' , " + query;
						return false;			
					}
					
					String ntCurrent = "";
					query = "SELECT IDENT_CURRENT('NOPTIEN') as ntId";
					
					ResultSet rsTthd = db.get(query);						
					if(rsTthd.next())
					{
						ntCurrent = rsTthd.getString("ntId");
						rsTthd.close();
					}
				
					
					// LUU VAO BANG ERP_THUTIENNPP_NOPTIEN_HOADON (1 PHIẾU THU CÓ TỐI ĐA 2 PHIẾU NỘP TIỀN)
						// SO TIEN TT > SO TIEN NOP CUA PHIEU CU VÀ CHON THU THEM NOP TIEN
					//thu dược = tổng số tiền thanh toán > = 
					if(Double.parseDouble(this.thuduoc.replace(",", "")) >= Double.parseDouble(this.sotiendanop.replace(",", "")) )
					{
						query =
							" INSERT ERP_THUTIENNPP_NOPTIEN(thutiennpp_fk, noptien_fk, sotiendatt) " +
							" SELECT IDENT_CURRENT('ERP_THUTIENNPP'),  pk_seq, " + this.sotiendanop.replace(",", "") + "  " +
							" FROM 	 NOPTIEN where pk_seq in ( " + this.noptienIds+ " )  ";
						
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_THUTIENNPP_NOPTIEN " + query;
							db.getConnection().rollback();
							return false;
						}
						
						double  sotienconlai = Double.parseDouble(this.thuduoc.replace(",", "")) - Double.parseDouble(this.sotiendanop.replace(",", ""));
						query = 
							" INSERT ERP_THUTIENNPP_NOPTIEN(thutiennpp_fk, noptien_fk, sotiendatt) " +
							" SELECT IDENT_CURRENT('ERP_THUTIENNPP'),  pk_seq, " + sotienconlai + "  " +
							" FROM 	 NOPTIEN where pk_seq in ( " + ntCurrent+ " )  ";
						
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_THUTIENNPP_NOPTIEN " + query;
							db.getConnection().rollback();
							return false;
						}
						
						
					}else
					{
						double  sotienconlai = Double.parseDouble(this.sotiendanop.replace(",", "")) - Double.parseDouble(this.thuduoc.replace(",", ""));
						query =
							" INSERT ERP_THUTIENNPP_NOPTIEN(thutiennpp_fk, noptien_fk, sotiendatt) " +
							" SELECT IDENT_CURRENT('ERP_THUTIENNPP'),  pk_seq, " + sotienconlai + "  " +
							" FROM 	 NOPTIEN where pk_seq in ( " + this.noptienIds+ " )  ";
						
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_THUTIENNPP_NOPTIEN " + query;
							db.getConnection().rollback();
							return false;
						}
						
						query = 
							" INSERT ERP_THUTIENNPP_NOPTIEN(thutiennpp_fk, noptien_fk, sotiendatt) " +
							" SELECT IDENT_CURRENT('ERP_THUTIENNPP'),  pk_seq, 0 " +
							" FROM 	 NOPTIEN where pk_seq in ( " + ntCurrent+ " )  ";
						
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_THUTIENNPP_NOPTIEN " + query;
							db.getConnection().rollback();
							return false;
						}						
						
					}
					
					// Update cột NOPTIENIDS , TEN NGUOI NOP TIEN CUA NOPTIENIDS
					
						 // LAY TEN NGUOI NOP TIEN CUA CAC PHIEU NOP TIEN
						if(ntCurrent.trim().length() > 0)
						{
							query = 
						     "           SELECT 	distinct case when  nvgn_fk is not null then  nvgn.ten " +
						     "                        when  nvbh_fk is not null then nvbh.ten" +
						     "                        when  npp_dat_fk is not null then npp.ten" +
						     "                        else  kh.ten end  as nguoint " +
						     "           FROM 		NOPTIEN nt left join NHANVIENGIAONHAN nvgn on nt.nvgn_fk = nvgn.pk_seq" +
						     "                            left join DAIDIENKINHDOANH nvbh on nt.nvbh_fk = nvbh.pk_seq" +
						     "                            left join KHACHHANG kh on nt.khachhang_fk= kh.pk_seq  " +
						     "							  left join NHAPHANPHOI npp on nt.npp_dat_fk= npp.pk_seq  " +
						     "           WHERE 		nt.pk_seq in ("+ this.noptienIds + ',' + ntCurrent  +")    ";
							ResultSet rsLayten = db.get(query);
							if(rsLayten!= null)
							{
								while(rsLayten.next())
								{
									tennguoinoptien = tennguoinoptien + rsLayten.getString("nguoint") + ',';
								}
								rsLayten.close();
							}
							
							if(tennguoinoptien.trim().length() > 0) tennguoinoptien= tennguoinoptien.substring(0, tennguoinoptien.length() -1 );
					   }
					
					query = " UPDATE 	ERP_THUTIENNPP set NOPTIENIDS = '"+ this.noptienIds + ',' + ntCurrent  +"', TENNGUOINOP = N'" + nguoinoptien + "' " +
							" WHERE 	pk_seq = (select IDENT_CURRENT('ERP_THUTIENNPP')) ";
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_THUTIENNPP_NOPTIEN " + query;
						db.getConnection().rollback();
						return false;
					}

				}
				else
				{
					// LUU VAO BANG ERP_THUTIENNPP_NOPTIEN_HOADON
					
					query =
					" INSERT ERP_THUTIENNPP_NOPTIEN(thutiennpp_fk, noptien_fk, sotiendatt) \n" +
					" SELECT IDENT_CURRENT('ERP_THUTIENNPP'),  pk_seq, "+ this.thuduoc.replace(",", "") +" \n" +
					" FROM 	 NOPTIEN where pk_seq in ( " + this.noptienIds+ " )  \n";
					
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_YCXUATKHO_DDH " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
								
			}else
			{
				query =
				" INSERT ERP_THUTIENNPP( SOTIENTHU,SOTIENTT ,CONLAI ,NGAYCHUNGTU,TRANGTHAI, GHICHU, NGUOINOPTIEN, \n" +
				" 		 NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA ,BPKINHDOANH, NPP_FK, SOIN, HINHTHUCTT ) \n" +
				" VALUES (" +this.thuduoc.replace(",", "")+ ", "+this.sotientt.replace(",", "") + ", "+Conlai+ ", \n"+
				" '" + this.ngaychungtu + "', '0', N'" + this.noidungtt + "', N'" + this.nguoinoptien + "', " +
				" '" +  getDateTime() + "', '" + this.userId + "', '"+  getDateTime() +"', '"+ this.userId +"', N'"+ this.bpkinhdoanh +"','"+ this.nppId +"', '"+ this.soin +"', N'"+ this.hinhthucTT +"')";
				
				System.out.println(query);
				if(!db.update(query))
				{
					this.msg = "KHONG THE TAO MOI ERP_THUTIENNPP: " + query;
					System.out.println("[ErpThutien.createTTHD] error message:" + this.msg);
					db.getConnection().rollback();
					return false;
				}
			}
						
				String tthdCurrent = "";
				query = "SELECT IDENT_CURRENT('ERP_THUTIENNPP') as tthdId \n";
				
				ResultSet rsTthd = db.get(query);		
				if(rsTthd.next())
				{
					tthdCurrent = rsTthd.getString("tthdId");
					rsTthd.close();
				}
				
				System.out.println("Thu tiền:"+ tthdCurrent);
				
				for(int i = 0; i < this.hoadonList.size(); i++)
				{
					IHoadonNPP hoadon = this.hoadonList.get(i);
					
					String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
					
					String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				
					String conlai = hoadon.getConlai().replaceAll(",", "");
					
					if(thanhtoan.length() > 0)
					{
						if(Float.parseFloat(thanhtoan) > 0)
						{							
							String npp_fk= hoadon.getNppId();
							String kh_fk= hoadon.getKhId();
							if(npp_fk.trim().length() == 0)
							{
								npp_fk= "NULL";
							}
							if(kh_fk.trim().length() == 0)
							{
								kh_fk= "NULL";
							}
								query = "INSERT ERP_THUTIENNPP_HOADON(THUTIENNPP_FK, HOADONNPP_FK, SOTIENTT, SOTIENAVAT, CONLAI, NPP_FK,KHACHHANG_FK, LOAIHD) \n" +
										"VALUES ('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim() + "', '" + avat + "', '" + conlai.trim() + "', " + npp_fk + "," + kh_fk +"," +hoadon.getLoaihd()+") \n";
							
								System.out.println("INSERT HOADON: ......"+query);
								
								if(!db.update(query))
								{
									this.msg = "KHONG THE TAO MOI ERP_THUTIENNPP_HOADON: " + query;
									System.out.println("[ErpThutien.createTTHD] error message: " + this.msg);
									db.getConnection().rollback();
									return false;
								}
							
						}
					}
				}
			
				System.out.println("mã kh:"+ this.add_makh);
				//System.out.println("tổng số tiền:"+ this.add_tongsotien);
				System.out.println("mã kh:"+ this.add_makh);
				System.out.println("mã kh:"+ this.add_makh);
				System.out.println("thanhtoan:"+ this.add_thanhtoan);
				//HÓA ĐƠN THU TIỀN THÊM
				/* kiểm tra xem có chọn khách hàng để thu tiền thêm, nếu chọn thì cập nhật vào bảng hóa đơn thêm*/
				if(this.add_check.trim().length()>0)
				{
					
					query =" INSERT INTO ERP_THUTIENNPP_HOADONTHEM " +
						   "(THUTIEN_FK, NGAYCHUNGTU, NGAYTAO, NGAYSUA, NGUOISUA,NGUOITAO ,SOHOADON, SOTIENTT, NPP_FK, KHACHHANG_FK) \n " +
						   " VALUES ('"+tthdCurrent+"', '"+this.add_ngayhd+"','"+getDateTime()+"', '"+getDateTime()+"','"+this.userId+"','"+this.userId+"','"+this.add_sohoadon+"' ,"+this.add_thanhtoan.replaceAll(",", "")+",'"+this.nppId+"', '"+this.add_makh+"')";
					
					System.out.println("CHEN VAO ERP_THUTIENNPP_HOADONTHEM ____"+ query);
					if(!db.update(query))
					{
						this.msg = " KHONG THE TAO MOI ERP_THUTIENNPP_HOADONTHEM: "+ query;
						db.getConnection().rollback();
						return false;
					}					
				}
				
				
				//HÓA ĐƠN THU TIỀN THÊM
				
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (SQLException e) 
		{
			try 
			{
				System.out.println(e.toString());
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	public boolean updateTTHD() 
	{
    this.getNppInfo();
		
			if(this.hoadonList.size() < 0)
			{
				this.msg = "Vui lòng chọn hóa đơn thu tiền";
				return false;
			}

		
		if(this.sotientt.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số tiền thanh toán";
			return false;
		}
		/* if(Double.parseDouble(this.sotientt.replace(",", "")) - Double.parseDouble(this.thuduoc.replace(",", "")) < 0)
		    {
		    	this.msg = "Số tiền hóa đơn không được vượt quá Tổng tiền";
				return false;
		    }*/
		
		try 
		{
			String ngaysua = getDateTime();
			
			db.getConnection().setAutoCommit(false);
						
			if(this.nhanvienBHIds.trim().length() <= 0) this.nhanvienBHIds = "NULL";
			if(this.nhanvienGNIds.trim().length() <= 0) this.nhanvienGNIds = "NULL";
			if(this.khIds.trim().length() <= 0) this.khIds = "NULL";
			if(this.nppIds.trim().length() <= 0) this.nppIds = "NULL";
			
			String query = "";
			String tennguoinoptien= "";
			 // LAY TEN NGUOI NOP TIEN CUA CAC PHIEU NOP TIEN
			if(this.noptienIds.trim().length() > 0)
			{
				query = 
			     "           SELECT distinct case when  nvgn_fk is not null then  nvgn.ten \n" +
			     "                        when  nvbh_fk is not null then nvbh.ten \n" +
			     "                        when  npp_dat_fk is not null then npp.ten  \n" +
			     "                        else  kh.ten end  as nguoint \n" +
			     "           FROM 	NOPTIEN nt 	left join NHANVIENGIAONHAN nvgn on nt.nvgn_fk = nvgn.pk_seq \n" +
			     "                            	left join DAIDIENKINHDOANH nvbh on nt.nvbh_fk = nvbh.pk_seq \n" +
			     "                            	left join KHACHHANG kh on nt.khachhang_fk= kh.pk_seq  \n" +
			     "                            	left join NHAPHANPHOI npp on nt.npp_dat_fk= npp.pk_seq  \n" +
			     "           WHERE  nt.pk_seq in ("+ this.noptienIds +")    ";
				System.out.println(" Câu bshdf......."+query);
				ResultSet rsLayten = db.get(query);
				if(rsLayten!= null)
				{
					while(rsLayten.next())
					{
						tennguoinoptien = tennguoinoptien + rsLayten.getString("nguoint") + ',';
					}
					rsLayten.close();
				}
				
				if(tennguoinoptien.trim().length() > 0) tennguoinoptien= tennguoinoptien.substring(0, tennguoinoptien.length() -1 );
			}	
		
			query = "DELETE ERP_THUTIENNPP_NOPTIEN where THUTIENNPP_FK = '" + this.id + "'";
			db.update(query);	
		if(Double.parseDouble(this.sotiendanop.replaceAll(",", "")) > 0)
		{	
			
			if(Double.parseDouble(this.sotienthuthem.replaceAll(",", "")) > 0) // CHON THU THEM
			{				
				
				// Insert thêm vào bảng Nộp tiền
				query = "INSERT INTO NOPTIEN(NGAYNOP, NVBH_FK, NVGN_FK, KHACHHANG_FK, NPP_DAT_FK, ngaytao, ngaysua, nguoitao, nguoisua, TRANGTHAI, SOTIEN, NPP_FK)"+
				        " VALUES ('"+ this.ngaychungtu +"', "+ this.nhanvienBHIds +","+ this.nhanvienGNIds +" , "+ this.khIds +","+ this.nppIds +" , '"+ getDateTime() +"', '"+  getDateTime() +"'," +
						" '"+ this.userId +"', '"+ this.userId +"', '0','"+ this.sotienthuthem.replaceAll(",", "") +"','"+ this.nppId +"'  )";
				System.out.println("Câu lệnh insert vào: "+ query);
				if (!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "KHONG THE LUU VAO TABLE 'NOPTIEN' , " + query;
					return false;			
				}
				
				String ntCurrent = "";
				query = "SELECT IDENT_CURRENT('NOPTIEN') as ntId";
				
				ResultSet rsTthd = db.get(query);						
				if(rsTthd.next())
				{
					ntCurrent = rsTthd.getString("ntId");
					rsTthd.close();
				}
				
				
					// LUU VAO BANG ERP_THUTIENNPP_NOPTIEN_HOADON (1 PHIẾU THU CÓ TỐI ĐA 2 PHIẾU NỘP TIỀN)
					// SO TIEN TT > SO TIEN NOP CUA PHIEU CU VÀ CHON THU THEM NOP TIEN
				if(Double.parseDouble(this.thuduoc.replace(",", "")) >= Double.parseDouble(this.sotiendanop.replace(",", "")) )
				{
					query = "INSERT ERP_THUTIENNPP_NOPTIEN(thutiennpp_fk, noptien_fk, sotiendatt) " +
					" SELECT '"  + this.id + "',  pk_seq, " + this.sotiendanop.replace(",", "") + "  " +
					" FROM 	 NOPTIEN where pk_seq in ( " + this.noptienIds+ " )  ";
					System.out.println("CAU LENH INSERT VAO: "+ query);
					
					if(!db.update(query))
					{
						this.msg = "KHONG THE TAO MOI ERP_THUTIENNPP_NOPTIEN " + query;
						db.getConnection().rollback();
						return false;
					}
					
					double  sotienconlai = Double.parseDouble(this.thuduoc.replace(",", "")) - Double.parseDouble(this.sotiendanop.replace(",", ""));
					query = "INSERT ERP_THUTIENNPP_NOPTIEN(thutiennpp_fk, noptien_fk, sotiendatt) \n" +
					" SELECT '"  + this.id + "',  pk_seq, " + sotienconlai + "  \n" +
					" FROM 	 NOPTIEN where pk_seq in ( " + ntCurrent+ " )  \n";
					System.out.println("CAU LENH INSERT VAO ERP_THUTIENNPP_NOPTIEN: "+ query);
					
					if(!db.update(query))
					{
						this.msg = "KHONG THE TAO MOI ERP_THUTIENNPP_NOPTIEN " + query;
						db.getConnection().rollback();
						return false;
					}
					
					this.noptienIds = this.noptienIds + ',' + ntCurrent ;
				}else
				{
					double  sotienconlai = Double.parseDouble(this.thuduoc.replace(",", "")) - Double.parseDouble(this.sotiendanop.replace(",", ""));
					query = "INSERT ERP_THUTIENNPP_NOPTIEN(thutiennpp_fk, noptien_fk, sotiendatt) " +
					" SELECT '"  + this.id + "' ,  pk_seq, " + sotienconlai + "  " +
					" FROM 	 NOPTIEN where pk_seq in ( " + this.noptienIds+ " )  ";
					System.out.println("CAU LENH INSERT VAO ERP_THUTIENNPP_NOPTIEN "+ query);
					
					if(!db.update(query))
					{
						this.msg = "KHONG THE TAO MOI ERP_THUTIENNPP_NOPTIEN " + query;
						db.getConnection().rollback();
						return false;
					}
					
					query = "INSERT ERP_THUTIENNPP_NOPTIEN(thutiennpp_fk, noptien_fk, sotiendatt) " +
					" SELECT '"  + this.id + "',  pk_seq, 0 " +
					" FROM 	 NOPTIEN WHERE pk_seq in ( " + ntCurrent+ " )  ";
					
					if(!db.update(query))
					{
						this.msg = "KHONG THE TAO MOI ERP_THUTIENNPP_NOPTIEN " + query;
						db.getConnection().rollback();
						return false;
					}
					
					this.noptienIds = this.noptienIds + ',' + ntCurrent ;
				}
				
				 // LAY TEN NGUOI NOP TIEN CUA CAC PHIEU NOP TIEN
				if(ntCurrent.trim().length() > 0)
				{
					query = 
				     "           SELECT distinct case when  nvgn_fk is not null then  nvgn.ten \n" +
				     "                        when  nvbh_fk is not null then nvbh.ten \n" +
				     "                        when  npp_dat_fk is not null then npp.ten \n" +
				     "                        else  kh.ten end  as nguoint \n" +
				     "           FROM 	NOPTIEN nt left join NHANVIENGIAONHAN nvgn on nt.nvgn_fk = nvgn.pk_seq  \n" +
				     "                            left join DAIDIENKINHDOANH nvbh on nt.nvbh_fk = nvbh.pk_seq \n" +
				     "                            left join KHACHHANG kh on nt.khachhang_fk= kh.pk_seq  \n" +
				     "                            left join NHAPHANPHOI npp on nt.npp_dat_fk= npp.pk_seq  \n" +
				     "           WHERE 	nt.pk_seq in ("+ this.noptienIds + ',' + ntCurrent  +")   \n";
					ResultSet rsLayten = db.get(query);
					if(rsLayten!= null)
					{
						while(rsLayten.next())
						{
							tennguoinoptien = tennguoinoptien + rsLayten.getString("nguoint") + ',';
						}
						rsLayten.close();
					}
					
					if(tennguoinoptien.trim().length() > 0) tennguoinoptien= tennguoinoptien.substring(0, tennguoinoptien.length() -1 );
			   }
				
			}
			else // KHONG CHON THU THEM
			{

				// LUU VAO BANG ERP_THUTIENNPP_NOPTIEN_HOADON
				
				query = "	INSERT ERP_THUTIENNPP_NOPTIEN(thutiennpp_fk, noptien_fk, sotiendatt) \n" +
						" 	SELECT '"  + this.id + "' ,  pk_seq, "+ this.thuduoc.replaceAll(",", "") +" \n" +
						" 	FROM NOPTIEN where pk_seq in ( " + this.noptienIds+ " )  \n";
				
				System.out.println("CAU LENH INSERT VAO ERP_THUTIENNPP_NOPTIEN: "+ query);
				if(!db.update(query))
				{
					this.msg = "KHONG THE TAO MOI ERP_YCXUATKHO_DDH " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			double Conlai= Double.parseDouble(this.sotientt.replace(",", "")) - Double.parseDouble(this.thuduoc.replace(",", ""));

			query = "	UPDATE 	ERP_THUTIENNPP set SOTIENTHU= "+ this.thuduoc.replace(",", "") +" ,SOTIENTT= "+ this.sotientt.replace(",", "") +", CONLAI= "+ Conlai +",  \n"+
					" 			NGAYCHUNGTU = '" + this.ngaychungtu + "', GHICHU = N'" + this.noidungtt + "', HINHTHUCTT = N'"+ this.hinhthucTT +"', \n" +
					"  			NGUOINOPTIEN = N'" + this.nguoinoptien + "' , BPKINHDOANH= N'"+ this.bpkinhdoanh +"' , NOPTIENIDS = '"+ this.noptienIds +"' , TENNGUOINOP = N'"+ tennguoinoptien +"', \n"+
					" 			NGAYSUA = '" +  getDateTime() + "', NGUOISUA = '" + this.userId + "', NPP_FK='"+ this.nppId +"', NVGN_FK= "+ this.nhanvienGNIds +", NVBH_FK= "+ this.nhanvienBHIds +", \n" +
					" 			KHACHHANG_FK= "+ this.khIds +", NPP_DAT_FK= "+ this.nppIds +" \n" +
					" 	WHERE 	PK_SEQ = '"  + this.id + "' \n";
			if(!db.update(query))
			{
				this.msg = "KHONG THE CAP NHAT ERP_THUTIEN: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
		}else
		{
				 double Conlai= Double.parseDouble(this.sotientt.replace(",", "")) - Double.parseDouble(this.thuduoc.replace(",", ""));

				 
				 
					query = "	UPDATE 	ERP_THUTIENNPP set   SOTIENTHU= "+ this.thuduoc.replace(",", "") +" ,SOTIENTT= "+ this.sotientt.replace(",", "") +", CONLAI= "+ Conlai +",  \n"+
							" 			NGAYCHUNGTU = '" + this.ngaychungtu + "', GHICHU = N'" + this.noidungtt + "', HINHTHUCTT = N'"+ this.hinhthucTT +"', \n" +
							"  			NGUOINOPTIEN = N'" + this.nguoinoptien + "' , BPKINHDOANH= N'"+ this.bpkinhdoanh +"' , NOPTIENIDS = '"+ this.noptienIds +"' , TENNGUOINOP = N'"+ tennguoinoptien +"',  \n"+
							" 			NGAYSUA = '" +  getDateTime() + "', NGUOISUA = '" + this.userId + "', NPP_FK='"+ this.nppId +"' \n" +
							"	WHERE 	PK_SEQ = '"  + this.id + "'  \n";
				
					if(!db.update(query))
					{
						this.msg = "KHONG THE CAP NHAT ERP_THUTIEN: " + query;
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
		}
			
			
			System.out.println(query);
			
			//Xóa ERP_ThuTienNPP_HoaDon:
			
			query=" DELETE ERP_THUTIENNPP_HOADON WHERE THUTIENNPP_FK='"+this.id+"'";
			if(!db.update(query))
			{
				this.msg = "KHONG THE XOA ERP_THUTIENNPP_HOADON: " + query;
				System.out.println("[ErpThutien.createTTHD] error message: " + this.msg);
				db.getConnection().rollback();
				return false;
			}	
				

				for(int i = 0; i < this.hoadonList.size(); i++)
				{
					IHoadonNPP hoadon = this.hoadonList.get(i);
	
					String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
					String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
					String sotienNT = hoadon.getSotienNT().replaceAll(",", "");
					String conlai = hoadon.getConlai().replaceAll(",", "");
					
					if(hoadon.getKhId().length() <=0) hoadon.setKhId("NULL");
					if(hoadon.getNppId().length() <=0) hoadon.setNppId("NULL");
					
					if(thanhtoan.length() > 0)
					{
						if(Float.parseFloat(thanhtoan) > 0)
						{							
							String npp_fk= hoadon.getNppId();
							String kh_fk= hoadon.getKhId();
							if(npp_fk.trim().length() == 0)
							{
								npp_fk= "NULL";
							}
							if(kh_fk.trim().length() == 0)
							{
								kh_fk= "NULL";
							}
								
								query = "INSERT ERP_THUTIENNPP_HOADON(THUTIENNPP_FK, HOADONNPP_FK, SOTIENTT, SOTIENAVAT, CONLAI,NPP_FK,KHACHHANG_FK, LOAIHD) \n" +
										"VALUES ('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim() + "', '" + avat + "', '" + conlai.trim() + "', " + npp_fk + "," + kh_fk + ","+hoadon.getLoaihd()+") \n";
							
								System.out.println("ERP_THUTIENNPP_HOADON" + query);
								
								if(!db.update(query))
								{
									this.msg = "KHONG THE TAO MOI ERP_THUTIENNPP_HOADON: " + query;
									System.out.println("[ErpThutien.createTTHD] error message: " + this.msg);
									db.getConnection().rollback();
									return false;
								}
							
						
						}
					}
				}
			
				if(this.add_check.trim().length()>0)
				{	
					/*String ntCurrent = "";
					query = "select IDENT_CURRENT('NOPTIEN') as ntId";
					
					ResultSet rsTthd = db.get(query);						
					if(rsTthd.next())
					{
						ntCurrent = rsTthd.getString("ntId");
						rsTthd.close();
					}*/
					
					query =" SELECT count(PK_SEQ) as count from ERP_THUTIENNPP_HOADONTHEM where THUTIEN_FK='"+this.id+"'";
					System.out.println(query);
					ResultSet kt = db.get(query);
					double k=0;
					if(kt.next())
					{
						k=Double.parseDouble(kt.getString("count"));
						System.out.println("count "+k);
						kt.close();
					}
					if(k==0)
					{
						query =" INSERT INTO ERP_THUTIENNPP_HOADONTHEM " +
						   "(THUTIEN_FK, NGAYCHUNGTU, NGAYTAO, NGAYSUA, NGUOISUA,NGUOITAO ,SOHOADON, SOTIENTT, NPP_FK, KHACHHANG_FK) \n " +
						   " VALUES ('"+this.id+"', '"+this.add_ngayhd+"','"+getDateTime()+"', '"+getDateTime()+"','"+this.userId+"','"+this.userId+"','"+this.add_sohoadon+"' ,"+this.add_thanhtoan.replaceAll(",", "")+",'"+this.nppId+"', '"+this.add_makh+"')";
					}
					else
					{
						query =" UPDATE ERP_THUTIENNPP_HOADONTHEM set NGAYSUA ='"+getDateTime()+"', \n" +
						   " 			SOHOADON = '"+this.add_sohoadon+"' , SOTIENTT ="+this.add_thanhtoan.replaceAll(",", "")+", \n"+
						   " 			KHACHHANG_FK='"+this.add_makh+"' \n"+
						   " 	 WHERE NPP_FK='"+this.nppId+"' and THUTIEN_FK='"+this.id+"' \n";
					}
					System.out.println("CAP NHAT VAO ERP_THUTIENNPP_HOADONTHEM ____"+ query);
					if(!db.update(query))
					{
						this.msg = " KHONG THE CAP NHAT VAO ERP_THUTIENNPP_HOADONTHEM: "+ query;
						db.getConnection().rollback();
						return false;
					}					
				}
				else
				{
					query= " DELETE ERP_THUTIENNPP_HOADONTHEM where THUTIEN_FK='"+this.id+"' ";
					
					System.out.println("DELETE ERP_THUTIENNPP_HOADONTHEM____"+ query);
					
					if(!db.update(query))
					{
						this.msg = " KHONG THE XOA ERP_THUTIENNPP_HOADONTHEM: "+ query;
						db.getConnection().rollback();
						return false;
					}	
				}
				
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (SQLException e) 
		{
			try 
			{
				System.out.println(e.toString());
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	public boolean chotTTHD(String userId)
	{
		try 
		{
			String ngaysua = getDateTime();
			
			db.getConnection().setAutoCommit(false);
			
			
			String query = "	UPDATE ERP_THUTIENNPP set TRANGTHAI = '1', NGUOISUA = '" + userId + "', NGAYSUA = '" + ngaysua + "' where PK_SEQ = '"  + this.id + "'";
			System.out.println("1.CAP NHAT ERP_THUTIENNPP: " + query);
			
			if(!db.update(query))
			{
				this.msg = "	KHONG THE CHOT ERP_THUTIENNPP: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
/*			String ngayghiso="";
			query = "Select ngayghiso from ERP_THUTIENNPP where pk_seq = '"+ this.id +"' ";
			ResultSet rs = db.get(query);
			try {
				if(rs!= null){
					while(rs.next()){
						ngayghiso = rs.getString("ngayghiso");
					}
				rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}*/

			
			//cap nhat trang thai hoa don la hoan tat neu da thanh toan het hoa don
			/*query = "select thanhtoan.hoadonnpp_fk, hoadon.tongtienAVat as sotienAVAT, thanhtoan.SOTIENTT " +
					"from ( " +
						"select hoadonnpp_fk, sum(SOTIENTT) as SOTIENTT " +
						"from ERP_THUTIENNPP_HOADON " +
						"where hoadonnpp_fk in (select hoadonnpp_fk from ERP_THUTIEN_HOADON where thutiennpp_fk = '" + this.id + "') " +
						"group by hoadonnpp_fk " +
					") thanhtoan " +
					"inner join ERP_HOADONNPP hoadon on thanhtoan.hoadonnpp_fk = hoadon.pk_seq " +
					"where hoadon.tongtienAVat - thanhtoan.SOTIENTT <= 0";
			
			System.out.println("__Check trang thai hoa don: " + query);
			ResultSet hoadonRs = db.get(query);
			if(hoadonRs != null)
			{
				while(hoadonRs.next())
				{
					String hoadon_fk = hoadonRs.getString("hoadon_fk");
					
					query = "update ERP_HOADON set trangthai = '4' where pk_seq = '" + hoadon_fk + "'";
					System.out.println("Cap nhat ERP_HOADON " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the cap nhat ERP_HOADON: " + query;
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
				}
				hoadonRs.close();
			}*/
															
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (java.sql.SQLException e) 
		{
			try 
			{
				db.getConnection().rollback();
				this.msg = "Lỗi khi chốt thu tiền: " + e.getMessage();
			}
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
	}
	
	public void init()
	{
		createRsLoc();
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = " SELECT tt.pk_seq, tt.ngaychungtu, tt.trangthai, nvbh_fk, nvgn_fk, khachhang_fk, npp_dat_fk,  "+
					   "        isnull(tt.ghichu, '') as ghichu, tt.bpkinhdoanh,tt.lydonop,tt.tennguoinop nguoinoptien, isnull(hinhthuctt,'Tiền mặt') as hinhthuctt,  "+
					   "        ISNULL(tt.sotientt, 0) AS SOTIENTT,  isnull(tt.sotienthu, 0) as thuduoc, tt.SOIN " +
					   " FROM 	ERP_THUTIENNPP tt  "+
					   " WHERE 	tt.pk_seq = '" + this.id + "'";
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.bpkinhdoanh =rs.getString("bpkinhdoanh");
					this.nguoinoptien =rs.getString("nguoinoptien");
					this.noidungtt = rs.getString("ghichu");
					this.thuduoc = "" + rs.getString("thuduoc");
					this.sotientt = "" + rs.getString("SOTIENTT");
					this.hinhthucTT = rs.getString("hinhthuctt");
					this.lydonop =rs.getString("lydonop");
					this.nhanvienBHIds = rs.getString("nvbh_fk") == null ? "": rs.getString("nvbh_fk");
					this.nhanvienGNIds = rs.getString("nvgn_fk") == null ? "": rs.getString("nvgn_fk");
					this.khIds = rs.getString("khachhang_fk") == null ? "": rs.getString("khachhang_fk");
					this.nppIds = rs.getString("npp_dat_fk") == null ? "": rs.getString("npp_dat_fk");
					this.soin = rs.getString("SOIN")== null ? "": rs.getString("SOIN");
					
					if(this.nhanvienBHIds.trim().length() > 0 || this.nhanvienGNIds.trim().length() > 0 || this.khIds.trim().length() > 0 || this.nppIds.trim().length() > 0  )
					{
						String sql = " SELECT 	NOPTIEN_FK " +
								     " FROM 	ERP_THUTIENNPP_NOPTIEN " +
								     " WHERE 	THUTIENNPP_FK = '" + this.id + "' ";
						ResultSet rsLayNT = db.get(sql);
						String chuoi= "";
						if(rsLayNT!= null)
						{
							while(rsLayNT.next())
							{
								chuoi = chuoi + rsLayNT.getString("NOPTIEN_FK") + "," ;
							}
							rsLayNT.close();
						}
						
						this.noptienIds = chuoi.substring(0, chuoi.length() - 1);
					}
					
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
		
		
		this.createRs();
	}

	
	public void initDisplay()
	{
	 this.getNppInfo();
     String	query = " 	SELECT 	HD.PK_SEQ, HD.NGAYXUATHD ,cast(HD.PK_SEQ as nvarchar(20)) + '-' +HD.NGAYXUATHD as TEN  \n" +
					"	FROM 	ERP_HOADONNPP HD \n" +
					" 	WHERE 	HD.TRANGTHAI=2 AND HD.NPP_FK='"+ this.nppId +"' \n" +
					"\n	UNION ALL \n"+
					" 	SELECT HD1.PK_SEQ, HD1.NGAYXUATHD ,cast(HD1.PK_SEQ as nvarchar(20)) + '-' +HD1.NGAYXUATHD as TEN  \n" +
					" 	FROM HOADON HD1 \n" +
					" 	WHERE HD1.TRANGTHAI=2 AND HD1.NPP_FK='"+ this.nppId +"' \n" ;
		
		this.hdTCRs = db.get(query);
		
		String sql = "SELECT pk_seq, ma + ', ' + ten as nppTen FROM NHAPHANPHOI WHERE trangthai = '1' and loainpp='4' AND TRUCTHUOC_FK ='"+ this.nppId +"' ";
		this.nppRs = db.get(sql);
		
		sql = "SELECT pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as khTen FROM KHACHHANG WHERE trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
		this.KhRs = db.get(sql);
		
		sql = "SELECT pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten FROM NHANVIENGIAONHAN WHERE trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
		this.NhanvienGNRs = db.get(sql);
		
		sql = "SELECT pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten FROM DAIDIENKINHDOANH WHERE trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
		System.out.println("TDV: "+ sql);
		this.NhanvienBHRs = db.get(sql);
		
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		  query =      " SELECT tt.pk_seq, tt.ngaychungtu, tt.trangthai,nvbh_fk, nvgn_fk, khachhang_fk, npp_dat_fk,  \n"+
					   "        isnull(tt.ghichu, '') as ghichu, tt.bpkinhdoanh,tt.lydonop,tt.nguoinoptien, isnull(hinhthuctt,'Tiền mặt') as hinhthuctt, \n"+
					   "        ISNULL(tt.sotientt, 0) AS SOTIENTT,  isnull(tt.sotienthu, 0) as thuduoc \n"+
					   " FROM 	ERP_THUTIENNPP tt  \n"+
					   " WHERE 	tt.pk_seq = '" + this.id + "' \n";
		
		System.out.println("115.Khoi tao thu tien: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.bpkinhdoanh =rs.getString("bpkinhdoanh");
					this.nguoinoptien =rs.getString("nguoinoptien");
					this.noidungtt = rs.getString("ghichu");
					this.thuduoc = "" + rs.getString("thuduoc");
					this.hinhthucTT = rs.getString("hinhthuctt");
					this.sotientt = "" + rs.getString("SOTIENTT");
					this.lydonop =rs.getString("lydonop");
					this.nhanvienBHIds = rs.getString("nvbh_fk") == null ? "": rs.getString("nvbh_fk");
					this.nhanvienGNIds = rs.getString("nvgn_fk") == null ? "": rs.getString("nvgn_fk");
					this.khIds = rs.getString("khachhang_fk") == null ? "": rs.getString("khachhang_fk");
					this.nppIds = rs.getString("npp_dat_fk") == null ? "": rs.getString("npp_dat_fk");


					if(this.nhanvienBHIds.trim().length() > 0 || this.nhanvienGNIds.trim().length() > 0 || this.khIds.trim().length() > 0 || this.nppIds.trim().length() > 0)
					{
						 sql = " select NOPTIEN_FK " +
								     " from ERP_THUTIENNPP_NOPTIEN " +
								     " where THUTIENNPP_FK = '" + this.id + "' ";
						ResultSet rsLayNT = db.get(sql);
						String chuoi= "";
						if(rsLayNT!= null)
						{
							while(rsLayNT.next())
							{
								chuoi = chuoi + rsLayNT.getString("NOPTIEN_FK") + "," ;
							}
							rsLayNT.close();
						}
						
						this.noptienIds = chuoi.substring(0, chuoi.length() - 1);
					}
					
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
			// Lấy những phiếu nộp tiền của NVGN/ NVBH/ KH / ĐT
			if(this.nhanvienBHIds.trim().length() > 0 || this.nhanvienGNIds.trim().length() > 0 || this.khIds.trim().length() > 0 || this.nppIds.trim().length() > 0)
			{
				if(this.nhanvienGNIds.trim().length() > 0 )
				{
					sql = " SELECT 	pk_seq, (cast (pk_seq as nvarchar(20)) + '-' + ngaynop) as ngay \n" +
						  " FROM 	NOPTIEN \n" +
						  " WHERE 	trangthai != 2 and nvgn_fk in ( "+ this.nhanvienGNIds +" ) \n";
				}
				else if (this.nhanvienBHIds.trim().length() > 0 )
				{
					sql = " SELECT 	pk_seq, (cast (pk_seq as nvarchar(20)) + '-' + ngaynop) as ngay \n" +
						  " FROM 	NOPTIEN \n" +
						  " WHERE 	trangthai != 2 and nvbh_fk in ( "+ this.nhanvienBHIds +" ) \n";
					
				}
				else if (this.khIds.trim().length() > 0 )
				{
					sql = " SELECT 	pk_seq, (cast (pk_seq as nvarchar(20)) + '-' + ngaynop) as ngay \n" +
						  " FROM 	NOPTIEN \n" +
					      " WHERE 	trangthai != 2 and khachhang_fk in ( "+ this.khIds +" ) \n";
					
				}
				else if (this.nppIds.trim().length() > 0 )
				{
					sql = " SELECT 	pk_seq, (cast (pk_seq as nvarchar(20)) + '-' + ngaynop) as ngay \n" +
						  " FROM 	NOPTIEN \n" +
					      " WHERE 	trangthai != 2 and npp_dat_fk in ( "+ this.nppIds +" ) \n";
					
				}
			}
			
			this.NoptienRs = db.get(sql);
				
			
			
		// Lấy số tiền đã nộp của các phiếu nộp được chọn
			//---------BEGIN
		
			if(this.noptienIds.trim().length() > 0)
			{
				sql = " SELECT sum(nt.SOTIEN)  as SOTIENNOP , \n" +
					  "        isnull((select SUM(SOTIENDATT) \n" +
					  "                from ERP_THUTIENNPP_NOPTIEN a INNER JOIN ERP_THUTIENNPP b on a.THUTIENNPP_FK = b.PK_SEQ  \n" +
					  "                where b.TRANGTHAI != 2 AND a.NOPTIEN_FK in ("+ this.noptienIds +")  \n" ;
					  if(this.id.trim().length() > 0)
					  {
						  sql += " and b.PK_SEQ != "+ this.id +" \n";
					  }
			   sql += "                ) , 0) \n" +
					  "        as SOTIENDATT \n" +
					  " FROM 	NOPTIEN nt \n" +
					  " WHERE 	nt.pk_seq in ("+ this.noptienIds +") \n";
				ResultSet rsLayST = db.get(sql);
			
				if(rsLayST != null)
				{
					try
					{
						while(rsLayST.next())
						{
							this.sotiendanop = rsLayST.getString("SOTIENNOP");
						}
						rsLayST.close();
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
	       //----------END
				
			
		
		
		// INIT HOADON ETC KHÁCH HÀNG

				query = 	"SELECT 0 AS LOAIHD, KH.PK_SEQ AS KHID,KH.MAFAST + '-' + KH.TEN AS MAKH, NPP.PK_SEQ AS NPPID,NPP.MAFAST + '-' + NPP.TEN AS MANPP, HD.PK_SEQ, HD.KYHIEU AS KYHIEU, HD.SOHOADON, \n" + 
							"		HD.NGAYXUATHD AS NGAYHOADON, \n" +
		 	   				"		( CAST(ISNULL(HD.TONGTIENAVAT,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0) ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) - CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0)) ) AS SOTIENVND, \n" +
		 	   				"		TT_HD.SOTIENTT  , 0 as IS_KHLE, HD.TRANGTHAI, 0 CANTRU \n" +
		 	   				" FROM 	ERP_THUTIENNPP_HOADON TT_HD \n" +
		 	   				" 		INNER JOIN ERP_HOADONNPP HD ON TT_HD.HOADONNPP_FK = HD.PK_SEQ AND TT_HD.LOAIHD = 0 \n" +
		 	   			    " 		LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_DAT_FK AND NPP.LOAINPP=4 and NPP.TRUCTHUOC_FK = '"+ this.nppId +"'  \n" +
		 	   		        " 		LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n" +
			 	   		    " 				AND TT_HD.KHACHHANG_FK in \n"+
					    	" 				(SELECT PK_SEQ from KHACHHANG WHERE KBH_FK=100052 and NPP_FK='" + this.nppId +"') \n"+
							" LEFT JOIN	\n" +
							" ( 	\n" +
							"		SELECT 	TTHD.KHACHHANG_FK,TTHD.HOADONNPP_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n" + 		
							"		FROM 	ERP_THUTIENNPP_HOADON TTHD \n" +
							"				INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n" + 		
							"		WHERE 	TTHD.LOAIHD= 0 AND TT.TRANGTHAI != '2' AND TTHD.THUTIENNPP_FK != '" + this.id + "' AND TTHD.THUTIENNPP_FK < " + this.id + " AND TT.NPP_FK = " +this.nppId+ 		
							"				AND TTHD.HOADONNPP_FK IN \n" +
							"				(SELECT HOADONNPP_FK FROM ERP_THUTIENNPP_HOADON WHERE LOAIHD = 0 AND THUTIENNPP_FK = '" + this.id + "' ) \n" + 
							"		GROUP BY HOADONNPP_FK, TTHD.KHACHHANG_FK \n" +
							" )DATHU ON TT_HD.HOADONNPP_FK = DATHU.HOADONNPP_FK AND DATHU.KHACHHANG_FK = HD.KHACHHANG_FK  \n" +
		//TRỪ SỐ TIỀN CUA HD TRONG XOANOKHACHHANG
							"LEFT JOIN ( \n"+
							"     SELECT XNHD.KHACHHANG_FK, HOADON_FK, SUM(ISNULL(SOTIENXOA,0)) SOTIENXOA \n" +
							"     FROM XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n" +
							"     WHERE  XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100052')   " +
							"            AND XNHD.LOAIHD = 0 AND XN.NPP_FK = "+this.nppId+
							"	  GROUP BY XNHD.KHACHHANG_FK, HOADON_FK "+
							" ) XOANO ON HD.PK_SEQ = XOANO.HOADON_FK AND XOANO.KHACHHANG_FK = HD.KHACHHANG_FK \n"+
		//TRỪ SỐ TIỀN CẤN TRỪ CÔNG NỢ
							"LEFT JOIN ( \n"+
							"     SELECT CT_HD.KHACHHANG_FK ,CT_HD.HOADON_FK, SUM(SOTIENCANTRU) as SOTIENCANTRU \n" +
							"     FROM CANTRUCONGNO CT INNER JOIN CANTRUCONGNO_HOADON CT_HD ON CT.PK_SEQ = CT_HD.CANTRUCONGNO_FK  \n" +
							"     WHERE  CT.TRANGTHAI = 1 AND CT.NPP_FK = '" + this.nppId + "' " +
							"	  group by  CT_HD.HOADON_FK, CT_HD.KHACHHANG_FK  \n"+
							" ) CTCN ON HD.PK_SEQ = CTCN.HOADON_FK AND CTCN.KHACHHANG_FK = HD.KHACHHANG_FK \n"+								
		//TRỪ SỐ TIỀN CỦA BÙ TRỪ CÔNG NỢ TRONG BUTRUCONGNO
							" LEFT JOIN ( \n"+
							"		  select bthd.HOADON_FK,bthd.KHACHHANG_FK, SUM(ISNULL(bthd.GHINO,0)) as GHINO, SUM(ISNULL(bthd.GHICO,0)) as GHICO \n"+
							"		  from BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
							"		  where bt.TRANGTHAI = 1 AND BT.NPP_FK = "+this.nppId+ 
							"		  group by bthd.HOADON_FK, bthd.KHACHHANG_FK \n"+	
							" )bthd on HD.PK_SEQ = bthd.HOADON_FK and bthd.KHACHHANG_FK = HD.KHACHHANG_FK \n"+
							" WHERE ISNULL(HD.LOAIHOADON,0)= 0 AND TT_HD.THUTIENNPP_FK ='" + this.id + "' AND  " +
					    	"      ( CAST(ISNULL(HD.TONGTIENAVAT,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0) ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) - CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0)) ) > 0     \n";  
				
			query += " UNION ALL ";		
			
			// INIT HOADON ETC ĐỐI TÁC

			query = 	"SELECT 0 AS LOAIHD, KH.PK_SEQ AS KHID,KH.MAFAST + '-' + KH.TEN AS MAKH, NPP.PK_SEQ AS NPPID,NPP.MAFAST + '-' + NPP.TEN AS MANPP, HD.PK_SEQ, HD.KYHIEU AS KYHIEU, HD.SOHOADON, \n" + 
						"		HD.NGAYXUATHD AS NGAYHOADON, \n" +
	 	   				"		( CAST(ISNULL(HD.TONGTIENAVAT,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0) ) ) AS SOTIENVND, \n" +
	 	   				"		TT_HD.SOTIENTT  , 0 as IS_KHLE, HD.TRANGTHAI, 0 CANTRU \n" +
	 	   				" FROM 	ERP_THUTIENNPP_HOADON TT_HD \n" +
	 	   				" 		INNER JOIN ERP_HOADONNPP HD ON TT_HD.HOADONNPP_FK = HD.PK_SEQ AND TT_HD.LOAIHD = 0 \n" +
	 	   			    " 		LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_DAT_FK AND NPP.LOAINPP=4 and NPP.TRUCTHUOC_FK = '"+ this.nppId +"'  \n" +
	 	   		        " 		LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n" +
		 	   		    " 				AND TT_HD.KHACHHANG_FK in \n"+
				    	" 				(SELECT PK_SEQ from KHACHHANG WHERE KBH_FK=100052 and NPP_FK='" + this.nppId +"') \n"+
						" LEFT JOIN	\n" +
						" ( 	\n" +
						"		SELECT 	TTHD.NPP_FK,TTHD.HOADONNPP_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n" + 		
						"		FROM 	ERP_THUTIENNPP_HOADON TTHD \n" +
						"				INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n" + 		
						"		WHERE 	TTHD.LOAIHD= 0 AND TT.TRANGTHAI != '2' AND TTHD.THUTIENNPP_FK != '" + this.id + "' AND TTHD.THUTIENNPP_FK < " + this.id + " AND TT.NPP_FK = " +this.nppId+ 		
						"				AND TTHD.HOADONNPP_FK IN \n" +
						"				(SELECT HOADONNPP_FK FROM ERP_THUTIENNPP_HOADON WHERE LOAIHD = 0 AND THUTIENNPP_FK = '" + this.id + "' ) \n" + 
						"		GROUP BY HOADONNPP_FK, TTHD.NPP_FK \n" +
						" )DATHU ON TT_HD.HOADONNPP_FK = DATHU.HOADONNPP_FK AND DATHU.NPP_FK = HD.NPP_DAT_FK  \n" +
/*	//TRỪ SỐ TIỀN CUA HD TRONG XOANOKHACHHANG
						"LEFT JOIN ( \n"+
						"     SELECT XNHD.KHACHHANG_FK, HOADON_FK, SUM(ISNULL(SOTIENXOA,0)) SOTIENXOA \n" +
						"     FROM XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n" +
						"     WHERE  XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100052')   " +
						"            AND XNHD.LOAIHD = 0 AND XN.NPP_FK = "+this.nppId+
						"	  GROUP BY XNHD.KHACHHANG_FK, HOADON_FK "+
						" ) XOANO ON HD.PK_SEQ = XOANO.HOADON_FK AND XOANO.KHACHHANG_FK = HD.KHACHHANG_FK \n"+
	//TRỪ SỐ TIỀN CẤN TRỪ CÔNG NỢ
						"LEFT JOIN ( \n"+
						"     SELECT CT_HD.KHACHHANG_FK ,CT_HD.HOADON_FK, SUM(SOTIENCANTRU) as SOTIENCANTRU \n" +
						"     FROM CANTRUCONGNO CT INNER JOIN CANTRUCONGNO_HOADON CT_HD ON CT.PK_SEQ = CT_HD.CANTRUCONGNO_FK  \n" +
						"     WHERE  CT.TRANGTHAI = 1 AND CT.NPP_FK = '" + this.nppId + "' " +
						"	  group by  CT_HD.HOADON_FK, CT_HD.KHACHHANG_FK  \n"+
						" ) CTCN ON HD.PK_SEQ = CTCN.HOADON_FK AND CTCN.KHACHHANG_FK = HD.KHACHHANG_FK \n"+								
	//TRỪ SỐ TIỀN CỦA BÙ TRỪ CÔNG NỢ TRONG BUTRUCONGNO
						" LEFT JOIN ( \n"+
						"		  select bthd.HOADON_FK,bthd.KHACHHANG_FK, SUM(ISNULL(bthd.GHINO,0)) as GHINO, SUM(ISNULL(bthd.GHICO,0)) as GHICO \n"+
						"		  from BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
						"		  where bt.TRANGTHAI = 1 AND BT.NPP_FK = "+this.nppId+ 
						"		  group by bthd.HOADON_FK, bthd.KHACHHANG_FK \n"+	
						" )bthd on HD.PK_SEQ = bthd.HOADON_FK and bthd.KHACHHANG_FK = HD.KHACHHANG_FK \n"+*/
						" WHERE ISNULL(HD.LOAIHOADON,0)= 0 AND TT_HD.THUTIENNPP_FK ='" + this.id + "' AND  " +
				    	"      ( CAST(ISNULL(HD.TONGTIENAVAT,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0) ) ) > 0     \n";  
			
		query += " UNION ALL ";		
			    query += 
					    	"SELECT 0 AS LOAIHD, KH.PK_SEQ AS KHID ,KH.MAFAST + '-' + KH.TEN AS MAKH, '0' AS NPPID, '0' AS MANPP, HD.PK_SEQ, HD.KYHIEU AS KYHIEU, HD.SOHOADON, \n"+
					    	"	HD.NGAYXUATHD AS NGAYHOADON,  \n"+
					    	"	( CAST( ISNULL(HD.TONGTIENAVAT,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0) ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) - CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0)) ) AS SOTIENVND, \n"+
					    	"	TT_HD.SOTIENTT, 0 as IS_KHLE, HD.TRANGTHAI, 0 CANTRU   \n"+
					    	"FROM 	ERP_THUTIENNPP_HOADON TT_HD \n"+
					    	" 		LEFT JOIN HOADON HD ON TT_HD.HOADONNPP_FK = HD.PK_SEQ AND TT_HD.LOAIHD = 0  AND TT_HD.KHACHHANG_FK in \n"+
					    	"                                                       (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK=100025 and NPP_FK='" + this.nppId +"')  " +		    	
					    	" 		LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n"+
					    	" 		LEFT JOIN	\n"+
					    	" 		( 	\n"+
					    	"			SELECT 	TTHD.KHACHHANG_FK,TTHD.HOADONNPP_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
					    	"			FROM 	ERP_THUTIENNPP_HOADON TTHD \n"+
					    	"					INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+
					    	"			WHERE 	TTHD.LOAIHD = 0 AND TT.TRANGTHAI != '2' AND TTHD.THUTIENNPP_FK != '" + this.id + "' AND TTHD.THUTIENNPP_FK < " + this.id + "  AND TT.NPP_FK = "+this.nppId+
					    	"					AND TTHD.HOADONNPP_FK IN \n"+
					    	"					(SELECT HOADONNPP_FK FROM ERP_THUTIENNPP_HOADON WHERE LOAIHD=0 AND THUTIENNPP_FK = '" + this.id + "' ) \n"+
					    	"			GROUP BY HOADONNPP_FK, TTHD.KHACHHANG_FK \n"+
					    	" )DATHU ON TT_HD.HOADONNPP_FK = DATHU.HOADONNPP_FK AND DATHU.KHACHHANG_FK = HD.KHACHHANG_FK      \n"+
		//TRỪ SỐ TIỀN CUA HD TRONG XOANOKHACHHANG
							"LEFT JOIN ( \n"+
							"     	SELECT 	XNHD.KHACHHANG_FK, HOADON_FK, SUM(ISNULL(SOTIENXOA,0)) SOTIENXOA \n" +
							"    	FROM 	XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n" +
							"     	WHERE  	XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100052')   " +
							"            	AND XNHD.LOAIHD = 0 AND XN.NPP_FK = "+this.nppId+
							"		GROUP BY XNHD.KHACHHANG_FK, HOADON_FK "+	
							" ) XOANO ON HD.PK_SEQ = XOANO.HOADON_FK AND XOANO.KHACHHANG_FK = HD.KHACHHANG_FK \n"+
		//TRỪ SỐ TIỀN CẤN TRỪ CÔNG NỢ
							"LEFT JOIN ( \n"+
							"     SELECT CT_HD.KHACHHANG_FK ,CT_HD.HOADON_FK, SUM(SOTIENCANTRU) as SOTIENCANTRU \n" +
							"     FROM CANTRUCONGNO CT INNER JOIN CANTRUCONGNO_HOADON CT_HD ON CT.PK_SEQ = CT_HD.CANTRUCONGNO_FK  \n" +
							"     WHERE  CT.TRANGTHAI = 1 AND CT.NPP_FK = '" + this.nppId + "' " +
							"	  group by  CT_HD.HOADON_FK,  CT_HD.KHACHHANG_FK    \n"+
							" ) CTCN ON HD.PK_SEQ = CTCN.HOADON_FK AND CTCN.KHACHHANG_FK = HD.KHACHHANG_FK   \n"+		
		//TRỪ SỐ TIỀN CỦA BÙ TRỪ CÔNG NỢ TRONG BUTRUCONGNO
							" LEFT JOIN ( \n"+
							"		  select bthd.HOADON_FK,bthd.KHACHHANG_FK, SUM(ISNULL(bthd.GHINO,0)) as GHINO, SUM(ISNULL(bthd.GHICO,0)) as GHICO \n"+
							"		  from BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
							"		  where bt.TRANGTHAI = 1 AND BT.NPP_FK = "+this.nppId+ 
							"		  group by bthd.HOADON_FK, bthd.KHACHHANG_FK \n"+	
							" )bthd on HD.PK_SEQ = bthd.HOADON_FK and bthd.KHACHHANG_FK = HD.KHACHHANG_FK \n"+
					    	"WHERE ISNULL(HD.LOAIHOADON,0) = 0 AND TT_HD.THUTIENNPP_FK ='" + this.id + "' AND  " +
					    	"      ( CAST( ISNULL(HD.TONGTIENAVAT,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0) ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) - CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0)) ) > 0     \n";
					    	
				query += " UNION ALL ";	
						
			    query += 
					    	"SELECT 2 AS LOAIHD, KH.PK_SEQ AS KHID, KH.MAFAST + '-' + KH.TEN AS MAKH, '0' AS NPPID, '0' AS MANPP, HD.PK_SEQ, 'GESO' AS KYHIEU, '' AS SOHOADON, \n"+
					    	"	 HD.NGAYDUNO AS NGAYHOADON, \n"+
					    	"	( CAST(ISNULL(HD.SONO,0) as numeric(18,0) ) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0)) ) AS SOTIENVND, \n"+
					    	"	TT_HD.SOTIENTT, 0 as IS_KHLE, '2' AS TRANGTHAI, 0 CANTRU  \n"+
					    	" FROM ERP_THUTIENNPP_HOADON TT_HD \n"+
					    	" LEFT JOIN DUNO_KHACHHANG HD ON TT_HD.HOADONNPP_FK = HD.PK_SEQ  AND TT_HD.LOAIHD = 2 \n"+
					    	" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n"+
					    	" LEFT JOIN	\n"+
					    	" ( 	\n"+
					    	"	SELECT 	TTHD.KHACHHANG_FK ,TTHD.HOADONNPP_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
					    	"	FROM 	ERP_THUTIENNPP_HOADON TTHD \n"+
					    	"			INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+
					    	"	WHERE TTHD.LOAIHD = 2 AND TT.TRANGTHAI != '2' AND TTHD.THUTIENNPP_FK != '" + this.id + "' AND TTHD.THUTIENNPP_FK < " + this.id + " AND TT.NPP_FK = "+this.nppId+
					    	"	AND TTHD.HOADONNPP_FK IN \n"+
					    	"		(SELECT HOADONNPP_FK FROM ERP_THUTIENNPP_HOADON WHERE LOAIHD = 2 AND  THUTIENNPP_FK = '" + this.id + "' \n"+
					    	 ") 	\n"+
					    	"	GROUP BY HOADONNPP_FK,  TTHD.KHACHHANG_FK  \n"+
					    	" )DATHU ON TT_HD.HOADONNPP_FK = DATHU.HOADONNPP_FK AND TT_HD.KHACHHANG_FK = DATHU.KHACHHANG_FK \n"+
					    	"WHERE  TT_HD.THUTIENNPP_FK ='" + this.id + "' " +
					    	"       AND ( CAST(ISNULL(HD.SONO,0) as numeric(18,0) ) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0)) ) > 0 \n";
						
						
					  query += " UNION ALL ";
					  
					  //HOA DON KHAC
					  query += 
					    	"SELECT 1 as LOAIHD, KH.PK_SEQ AS KHID ,KH.MAFAST + '-' + KH.TEN AS MAKH, '0' AS NPPID, '0' AS MANPP, HD.PK_SEQ, HD.KYHIEUHOADON AS KYHIEU, HD.SOHOADON, \n"+
					    	"	HD.Ngayhoadon AS NGAYHOADON,  \n"+
					    	"	( CAST( ISNULL(HD.AVAT,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0) ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0))  ) AS SOTIENVND, \n"+
					    	"	TT_HD.SOTIENTT, 0 as IS_KHLE, HD.TRANGTHAI, 0 CANTRU   \n"+
					    	"FROM 	ERP_THUTIENNPP_HOADON TT_HD \n"+
					    	" 		LEFT JOIN ERP_HOADONPHELIEU HD ON TT_HD.HOADONNPP_FK = HD.PK_SEQ AND TT_HD.LOAIHD = 1  \n"+
					    	" 		INNER JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n"+
					    	" 		LEFT JOIN	\n"+
					    	" 		( 	\n"+
					    	"			SELECT 	TTHD.KHACHHANG_FK ,TTHD.HOADONNPP_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
					    	"			FROM 	ERP_THUTIENNPP_HOADON TTHD \n"+
					    	"					INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+
					    	"			WHERE 	TTHD.LOAIHD = 1 AND TT.TRANGTHAI != '2' AND TTHD.THUTIENNPP_FK != '" + this.id + "' AND TTHD.THUTIENNPP_FK < " + this.id + "  AND TT.NPP_FK = "+this.nppId+
					    	"					AND TTHD.HOADONNPP_FK IN \n"+
					    	"			(SELECT HOADONNPP_FK FROM ERP_THUTIENNPP_HOADON WHERE LOAIHD = 1 AND THUTIENNPP_FK = '" + this.id + "' ) \n"+
					    	"			GROUP BY HOADONNPP_FK,TTHD.KHACHHANG_FK  \n"+
					    	" 		)DATHU ON TT_HD.HOADONNPP_FK = DATHU.HOADONNPP_FK AND HD.KHACHHANG_FK = TT_HD.KHACHHANG_FK \n"+
					    	//TRỪ SỐ TIỀN CUA HD TRONG XOANOKHACHHANG
							"LEFT JOIN ( \n"+
							"     SELECT 	XNHD.KHACHHANG_FK ,HOADON_FK, SUM(ISNULL(SOTIENXOA,0)) SOTIENXOA \n" +
							"     FROM 		XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n" +
							"     WHERE  	XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100052')   " +
							"            	AND XNHD.LOAIHD = 1 AND XN.NPP_FK = "+this.nppId+
							"	  GROUP BY XNHD.KHACHHANG_FK ,HOADON_FK "+
							" ) XOANO ON HD.PK_SEQ = XOANO.HOADON_FK AND XOANO.KHACHHANG_FK = HD.KHACHHANG_FK \n"+
							//TRỪ SỐ TIỀN CỦA BÙ TRỪ CÔNG NỢ TRONG BUTRUCONGNO
							" LEFT JOIN ( \n"+
							"		  select bthd.HOADON_FK,bthd.KHACHHANG_FK, SUM(ISNULL(bthd.GHINO,0)) as GHINO, SUM(ISNULL(bthd.GHICO,0)) as GHICO \n"+
							"		  from BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
							"		  where bt.TRANGTHAI = 1 AND BT.NPP_FK = "+this.nppId+ 
							"		  group by bthd.HOADON_FK, bthd.KHACHHANG_FK \n"+	
							" )bthd on HD.PK_SEQ = bthd.HOADON_FK and bthd.KHACHHANG_FK = HD.KHACHHANG_FK \n"+
					    	"WHERE TT_HD.THUTIENNPP_FK ='" + this.id + "' AND  " +
					    	"    (  CAST( ISNULL(HD.avat,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0)) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) ) > 0     \n";
					    			  
		System.out.println("1.QUERY KHOI TAO HOA DON Display1: " + query);
		ResultSet rsHoadon = db.get(query);
		List<IHoadonNPP> hdList = new ArrayList<IHoadonNPP>();
		if(rsHoadon != null)
		{
			try 
			{
				IHoadonNPP hd = null;
				double tongDUNO = 0;
				while(rsHoadon.next())
				{					
					String id = rsHoadon.getString("PK_SEQ");							
					String kyhieu = rsHoadon.getString("KYHIEU");
					String sohoadon = rsHoadon.getString("SOHOADON");
					String ngayhd = rsHoadon.getString("NGAYHOADON")==null ? "":rsHoadon.getString("NGAYHOADON");;
					String avat = "" + rsHoadon.getDouble("SOTIENVND");
					String nppid= rsHoadon.getString("NPPID")==null ? "":rsHoadon.getString("NPPID");
					String manpp= rsHoadon.getString("MANPP")==null ? "":rsHoadon.getString("MANPP");
					String khid= rsHoadon.getString("KHID")==null ? "":rsHoadon.getString("KHID");			
					String makh= rsHoadon.getString("MAKH")==null ? "": rsHoadon.getString("MAKH");
					String isKHLe = rsHoadon.getString("IS_KHLE");
					String loaihd = rsHoadon.getString("LOAIHD");
					
					String tt =  rsHoadon.getString("trangthai");
					String trangthai= "";
					if(tt.equals("1")) trangthai = "Chưa xác nhận";
					else if(tt.equals("2")) trangthai = "Đã xác nhận";
					else if(tt.equals("3")) trangthai = "Đã xóa";
					else if(tt.equals("4")) trangthai = "Đã in";
					else if(tt.equals("5")) trangthai = "Đã hủy";
					
					if(nppid.equals("0")) nppid="";
					if(manpp.equals("0")) manpp="";
					
					
					double dathanhtoan = 0;
					if(this.id.length() > 0)
					{								
							dathanhtoan = rsHoadon.getDouble("SOTIENTT");
					}
					
					double cantru = rsHoadon.getDouble("CANTRU");
					
				// TÍNH DƯ NỢ CỦA KHÁCH HÀNG
					if(khid.trim().length() > 0)
					{
						tongDUNO = 0;
							
							//Tinh tong du no
							
							query =	" SELECT ABS(SUM(a.SOTIENAVAT-a.SOTIENTT)) as TONGDUNO "+
									" FROM 	 ERP_THUTIENNPP_HOADON a inner join ERP_THUTIENNPP b on a.THUTIENNPP_FK=b.PK_SEQ "+
									" WHERE  a.KHACHHANG_FK = '" + khid + "' and b.TRANGTHAI != 2 "+
									" GROUP BY a.KHACHHANG_FK "+
									" HAVING SUM(a.SOTIENAVAT-a.SOTIENTT) < 0 ";
							
							
							ResultSet rsCHECK = db.get(query);
							if(rsCHECK != null)
							{
								if(rsCHECK.next())
									tongDUNO = rsCHECK.getDouble("TONGDUNO");
							}
							rsCHECK.close();
						
					}
				// TÍNH DƯ NỢ CỦA ĐỐI TÁC
					if(nppid.trim().length() > 0)
					{/*
						tongDUNO = 0;
							
							//Tinh tong du no
							
							query =	" SELECT ABS(SUM(a.SOTIENAVAT-a.SOTIENTT)) as TONGDUNO \n"+
									" FROM 	 ERP_THUTIENNPP_HOADON a inner join ERP_THUTIENNPP b on a.THUTIENNPP_FK=b.PK_SEQ \n"+
									" WHERE  a.NPP_DAT_FK  = '" + nppid + "' and b.TRANGTHAI != 2 \n"+
									" GROUP BY a.NPP_DAT_FK \n"+
									" having SUM(a.SOTIENAVAT-a.SOTIENTT) < 0 \n";
							
							
							ResultSet rsCHECK = db.get(query);
							if(rsCHECK != null)
							{
								if(rsCHECK.next())
									tongDUNO = rsCHECK.getDouble("TONGDUNO");
							}
							rsCHECK.close();
						
					*/}	
											
					
					hd = new HoadonNPP(id, "", kyhieu, sohoadon, ngayhd, avat, "", Double.toString(dathanhtoan), "","","");
					hd.setNppId(nppid);
					hd.setNppMa(manpp);
					hd.setKhId(khid);
					hd.setKhMa(makh);
					hd.setIsKHLe(isKHLe);
					hd.setTrangthaihd(trangthai);
					hd.setDuno(Double.toString(tongDUNO));
					hd.setCantru(Double.toString(cantru));
					hd.setLoaihd(loaihd);
					hdList.add(hd);
					
				
					/*
					
					String id = rsHoadon.getString("PK_SEQ");							
					String kyhieu = rsHoadon.getString("KYHIEU");
					String sohoadon = rsHoadon.getString("SOHOADON");
					String ngayhd = rsHoadon.getString("NGAYHOADON");
					String avat = "" +rsHoadon.getDouble("SOTIENVND");
					String nppid= rsHoadon.getString("NPPID")==null ? "":rsHoadon.getString("NPPID");
					String manpp= rsHoadon.getString("MANPP")==null ? "":rsHoadon.getString("MANPP");
					String khid= rsHoadon.getString("KHID")==null ? "":rsHoadon.getString("KHID");
					String makh= rsHoadon.getString("MAKH")==null ? "": rsHoadon.getString("MAKH");
					
					String tt =  rsHoadon.getString("trangthai");
					String trangthai= "";
					if(tt.equals("1")) trangthai = "Chưa xác nhận";
					else if(tt.equals("2")) trangthai = "Đã xác nhận";
					else if(tt.equals("3")) trangthai = "Đã xóa";
					else if(tt.equals("4")) trangthai = "Đã in";
					else if(tt.equals("5")) trangthai = "Đã hủy";
					
					if(nppid.equals("0")) nppid="";
					if(manpp.equals("0")) manpp="";
					String dathanhtoan = "0";
					if(this.id.length() > 0)
					{								
						if(Math.abs(rsHoadon.getDouble("SOTIENTT")) > 0){
							dathanhtoan = "" + rsHoadon.getDouble("SOTIENTT");
						}
					}
					hd = new HoadonNPP(id, "", kyhieu, sohoadon, ngayhd, avat, "", dathanhtoan, "","","" );
					hd.setNppId(nppid);
					hd.setNppMa(manpp);
					hd.setKhId(khid);
					hd.setKhMa(makh);
					hd.setTrangthaihd(trangthai);
					hdList.add(hd);
					
					hd.setNppId(nppid);
					hd.setNppMa(manpp);
					hd.setKhId(khid);
					hd.setKhMa(makh);
					hd.setIsKHLe(isKHLe);
					hd.setTrangthaihd(trangthai);
					hd.setDuno(Double.toString(tongDUNO));
					hd.setCantru(Double.toString(cantru));
					hd.setLoaihd(loaihd);
					hdList.add(hd);
					
				*/}
				rsHoadon.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		this.hoadonList = hdList;
	
					
	
	}
	
	public void initPdf() 
	{
		this.getNppInfo();
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = " SELECT a.pk_seq as tthdId, " +
				       "        (case when a.nguoinoptien is not null and a.nguoinoptien != ''  then a.nguoinoptien \n" +
				       "              else (SELECT 	case when nt.nvgn_fk is not null  then nvgn.TEN \n" +
				       "								when nt.nvbh_fk is not null  then nvbh.TEN \n" +
				       "								when nt.npp_dat_fk is not null  then npp.TEN \n" +
				       " 								else kh.TEN end nguoinoptien \n" +
				       "                    FROM 	noptien nt left join DAIDIENKINHDOANH nvbh on nt.nvbh_fk = nvbh.pk_seq " +
				       "                                    left join NHANVIENGIAONHAN nvgn on nt.nvgn_fk = nvgn.pk_seq" +
				       "									left join KHACHHANG kh on nt.khachhang_fk = kh.pk_seq  " +
				       "									left join NHAPHANPHOI npp on nt.npp_dat_fk = npp.pk_seq  " +
				       "                    WHERE 	nt.pk_seq in (select top 1 noptien_fk from ERP_THUTIENNPP_NOPTIEN where thutiennpp_fk = a .pk_seq )) end) as nguoinoptien ," +
				       "        a.trangthai, a.ngaychungtu, a.soin, " +
					   "  		a.ghichu as ghichu, isnull(a.sotienthu, 0) as thuduoc " +
					   " FROM ERP_THUTIENNPP a  " +
					   " WHERE a.pk_seq = '" + this.id + "' ";
		
		System.out.println("[ErpThutien.initPdf] query = " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.noidungtt = rs.getString("ghichu");
					this.thuduoc =  rs.getString("thuduoc");
					this.nguoinoptien = rs.getString("nguoinoptien");
					this.soin = rs.getString("soin");
					

				}
			} 
			catch (SQLException e) 
			{
				System.out.println("115..Exception: " + e.getMessage());
			}
		}
		
		
		
	}

	public void initUnc() 
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = 	"SELECT a.pk_seq, a.ngaychungtu, b.ten as nppTen, b.diachi, a.httt_fk, c.ten as nganhang_fk, a.chinhanh_fk, a.sotaikhoan, a.noidungtt, a.sotientt \n" +
						"FROM 	ERP_THANHTOANHOADON a INNER JOIN ERP_NHACUNGCAP b on a.npp_fk = b.pk_seq left join erp_nganhang c on a.nganhang_fk = c.pk_seq \n" +
						"WHERE 	a.pk_seq = '" + this.id + "' \n";
		System.out.println("Khoi tao Unc: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.nppId = rs.getString("nppTen") + " --- " + rs.getString("diachi");
					this.htttId = rs.getString("httt_fk");

					this.noidungtt = rs.getString("noidungtt");
					this.sotientt = formatter.format(rs.getDouble("sotientt"));

				}
			} 
			catch (SQLException e) 
			{
				System.out.println("Exception: " + e.getMessage());
			}
		}
		
	}
	
	public void createRs() 
	{
		this.getNppInfo();		
		// Check dang nhap co phai DOI TAC hay khong
		String query = "SELECT LOAINPP FROM NHAPHANPHOI WHERE PK_SEQ='"+ this.nppId +"' ";
		
		ResultSet rss= db.get(query);
		try
		{
			if(rss!=null)
			{
				while (rss.next())
				{
					this.checkDN = rss.getInt("LOAINPP");
				}
			}
		}catch(Exception e){e.printStackTrace();}
		
		String sql = "";
		
/*		 query = 	"SELECT HD.PK_SEQ, HD.NGAYXUATHD ,cast(HD.PK_SEQ as nvarchar(20)) + '-' +HD.NGAYXUATHD as TEN  \n" +
		 			"FROM 	ERP_HOADONNPP HD \n" +
		 			"WHERE 	HD.TRANGTHAI in (2,4) AND HD.NPP_FK='"+ this.nppId +"' \n" +
		 			"UNION ALL \n"+
			 		"SELECT HD1.PK_SEQ, HD1.NGAYXUATHD ,cast(HD1.PK_SEQ as nvarchar(20)) + '-' +HD1.NGAYXUATHD as TEN  \n" +
		 			"FROM 	HOADON HD1 \n" +
		 			"WHERE 	HD1.TRANGTHAI in (2,4) AND HD1.NPP_FK='"+ this.nppId +"' AND ISNULL(HD1.LOAIHOADON,0) = 0 \n" ; // CHỈ LẤY HÓA ĐƠN TC , KO LAY HOA DON KM
		 System.out.println("HDRS: "+query);
		this.hdTCRs = db.get(query);
		
		String sql = "SELECT pk_seq, isnull(maFAST,'') + '-' + ten as nppTen FROM NHAPHANPHOI WHERE trangthai = '1' and loainpp='4' AND TRUCTHUOC_FK ='"+ this.nppId +"' ";
		this.nppRs = db.get(sql);

		sql = "SELECT pk_seq, isnull(maFAST,'') + '-' + ten as khTen FROM KHACHHANG WHERE trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
		this.KhRs = db.get(sql);
		
		sql = "SELECT pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten FROM NHANVIENGIAONHAN WHERE trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
		this.NhanvienGNRs = db.get(sql);
			
		sql = " SELECT 	B.PK_SEQ , CAST(B.pk_seq as nvarchar(20)) + ' - ' + B.ten as Ten " +
			  " FROM 	DAIDIENKINHDOANH_NPP A INNER JOIN DAIDIENKINHDOANH B ON A.DDKD_FK= B.PK_SEQ " +
			  " WHERE  	A.npp_fk ='"+ this.nppId +"' AND B.TRANGTHAI = '1' ";
		this.NhanvienBHRs = db.get(sql);
					
		sql = "SELECT 	pk_seq, isnull(maFAST,'') + '-' + ten as TEN from KHACHHANG where trangthai = '1' and npp_fk = '"+ this.nppId +"' ";
		this.Khlist = db.get(sql);
		
		System.out.println("init KH_________"+sql);*/
		
	// Lấy những phiếu nộp tiền của NVGN/ NVBH/ KH / DT
			if(this.nhanvienBHIds.trim().length() > 0 || this.nhanvienGNIds.trim().length() > 0 || this.khIds.trim().length() > 0 || this.nppIds.trim().length() > 0)
			{
				if(this.nhanvienGNIds.trim().length() > 0 )           // NHÂN VIÊN GIAO NHẬN
				{
					sql = " SELECT 	nt.pk_seq, (cast (nt.pk_seq as nvarchar(20)) + '-' + nt.ngaynop) as ngay \n" +
						  " FROM 	NOPTIEN nt \n" +
						  " WHERE 	nt.trangthai != 2 and nt.nvgn_fk in ( "+ this.nhanvienGNIds +" ) \n" +
						  "       	and nt.sotien - isnull((SELECT SUM(SOTIENDATT) \n" +
						  "                     		  	FROM ERP_THUTIENNPP_NOPTIEN a inner join ERP_THUTIENNPP b on a.THUTIENNPP_FK = b.PK_SEQ  \n" +
						  "                     		  	WHERE b.TRANGTHAI != 2 AND a.NOPTIEN_FK = nt.PK_SEQ \n" +
			              "                    		      	GROUP BY NOPTIEN_FK) , 0) > 0  \n";
				  if(this.id.trim().length() > 0)
				  {
				   sql += "       	and nt.pk_seq not in (select NOPTIEN_FK from ERP_THUTIENNPP_NOPTIEN where THUTIENNPP_FK = "+ this.id +"  ) \n" +
				   		  " UNION ALL  \n" +
					  	  " SELECT 	a.NOPTIEN_FK as pk_seq, (cast ( a.NOPTIEN_FK as nvarchar(20)) + '-' + b.ngaynop) as ngay \n" +
					  	  " FROM 	ERP_THUTIENNPP_NOPTIEN a inner join NOPTIEN b on a.NOPTIEN_FK= b.PK_SEQ \n" +
					   	  " WHERE 	a.thutiennpp_fk = "+ this.id +" \n"	;
				  }
				}
				else if (this.nhanvienBHIds.trim().length() > 0 )     // TRÌNH DƯỢC VIÊN
				{
					sql = " SELECT 	nt.pk_seq, (cast (nt.pk_seq as nvarchar(20)) + '-' + nt.ngaynop) as ngay \n" +
						  " FROM 	NOPTIEN nt \n" +
						  " WHERE 	nt.trangthai != 2 and nt.nvbh_fk in ( "+ this.nhanvienBHIds +" ) \n"+
						  "       	and nt.sotien - isnull((SELECT 	SUM(SOTIENDATT) \n" +
						  "                     		  	FROM 	ERP_THUTIENNPP_NOPTIEN a inner join ERP_THUTIENNPP b on a.THUTIENNPP_FK = b.PK_SEQ  \n" +
						  "                     		  	WHERE 	b.TRANGTHAI != 2 AND a.NOPTIEN_FK = nt.PK_SEQ \n" +
				          "                        		  	GROUP BY NOPTIEN_FK) , 0) > 0  \n";
				   
			   if(this.id.trim().length() > 0) 
				{
				  sql += "    and nt.pk_seq not in (select NOPTIEN_FK from ERP_THUTIENNPP_NOPTIEN where THUTIENNPP_FK = "+ this.id +"  ) \n" +
				  		 " UNION ALL \n" +
				  		 
				  		 " SELECT 	a.NOPTIEN_FK as pk_seq, (cast ( a.NOPTIEN_FK as nvarchar(20)) + '-' + b.ngaynop) as ngay \n" +
				  		 " FROM 	ERP_THUTIENNPP_NOPTIEN a inner join NOPTIEN b on a.NOPTIEN_FK= b.PK_SEQ \n" +
				  		 " WHERE 	a.thutiennpp_fk = "+ this.id +" \n"	;
				}
					
				}
				else if (this.khIds.trim().length() > 0 )              // KHÁCH HÀNG
				{
					sql = " SELECT nt.pk_seq, (cast (nt.pk_seq as nvarchar(20)) + '-' + nt.ngaynop) as ngay \n" +
						  " FROM   NOPTIEN nt \n" +
					      " WHERE  nt.trangthai != 2 and nt.khachhang_fk in ( "+ this.khIds +" ) \n"+
						  "        and nt.sotien - isnull((	SELECT SUM(SOTIENDATT) \n" +
						  "                     		  	FROM ERP_THUTIENNPP_NOPTIEN a inner join ERP_THUTIENNPP b on a.THUTIENNPP_FK = b.PK_SEQ  \n" +
						  "                     		  	WHERE b.TRANGTHAI != 2 AND a.NOPTIEN_FK = nt.PK_SEQ \n" +
				          "                        		  	GROUP BY NOPTIEN_FK) , 0) > 0  \n";
					
				  if(this.id.trim().length() > 0)
					{
					  sql += "    and nt.pk_seq not in (select NOPTIEN_FK from ERP_THUTIENNPP_NOPTIEN where THUTIENNPP_FK = "+ this.id +"  ) \n" +
					  		 " UNION ALL \n" +
					  		 " SELECT 	a.NOPTIEN_FK as pk_seq, (cast ( a.NOPTIEN_FK as nvarchar(20)) + '-' + b.ngaynop) as ngay \n" +
					  		 " FROM 	ERP_THUTIENNPP_NOPTIEN a inner join NOPTIEN b on a.NOPTIEN_FK= b.PK_SEQ \n" +
					  		 " WHERE 	a.thutiennpp_fk = "+ this.id +" \n"	;
					}
				}
				else if (this.nppIds.trim().length() > 0 )             // ĐỐI TÁC
				{
					sql = " SELECT 	nt.pk_seq, (cast (nt.pk_seq as nvarchar(20)) + '-' + nt.ngaynop) as ngay \n" +
						  " FROM 	NOPTIEN nt \n" +
					      " WHERE 	nt.trangthai != 2 and nt.npp_dat_fk in ( "+ this.nppIds +" ) \n"+
						  "       	and nt.sotien - isnull((SELECT 	SUM(SOTIENDATT) \n" +
						  "                     		  	FROM 	ERP_THUTIENNPP_NOPTIEN a inner join ERP_THUTIENNPP b on a.THUTIENNPP_FK = b.PK_SEQ  \n" +
						  "                     		  	WHERE 	b.TRANGTHAI != 2 AND a.NOPTIEN_FK = nt.PK_SEQ \n" +
				          "                        		  	GROUP BY NOPTIEN_FK) , 0) > 0  \n";
					
				  if(this.id.trim().length() > 0)
					{
					  sql += "    and nt.pk_seq not in (select NOPTIEN_FK from ERP_THUTIENNPP_NOPTIEN where THUTIENNPP_FK = "+ this.id +"  ) \n" +
					  		 " UNION ALL \n" +
					  		 " SELECT 	a.NOPTIEN_FK as pk_seq, (cast ( a.NOPTIEN_FK as nvarchar(20)) + '-' + b.ngaynop) as ngay \n" +
					  		 " FROM 	ERP_THUTIENNPP_NOPTIEN a inner join NOPTIEN b on a.NOPTIEN_FK= b.PK_SEQ \n" +
					  		 " WHERE 	a.thutiennpp_fk = "+ this.id +" \n"	;
					}
				}
			}
			
			System.out.println("CAU LAY PHIEU NOP TIEN: \n" + sql);
			this.NoptienRs = db.get(sql);
				
			
			
			// Lấy số tiền đã nộp của các phiếu nộp được chọn
				//---------BEGIN
				NumberFormat formatter = new DecimalFormat("#,###,###");
				if(this.noptienIds.trim().length() > 0)
				{
					sql = " SELECT sum(nt.SOTIEN)  as SOTIENNOP , \n" +
						  "        isnull((select SUM(SOTIENDATT) \n" +
						  " FROM   ERP_THUTIENNPP_NOPTIEN a inner join ERP_THUTIENNPP b on a.THUTIENNPP_FK = b.PK_SEQ  \n" +
						  "                where b.TRANGTHAI != 2 AND a.NOPTIEN_FK in ("+ this.noptienIds +")  \n" ;
							  if(this.id.trim().length() > 0)
							  {
								  sql += " and b.PK_SEQ != "+ this.id +" ";
							  }
					sql +=	  "                ) , 0) " +
							  "        as SOTIENDATT " +
							  " FROM 	NOPTIEN nt \n" +
							  " WHERE 	nt.pk_seq in ("+ this.noptienIds +") ";
					
					System.out.println("CAU LAY SO TIEN NOP  "+sql);
					ResultSet rsLayST = db.get(sql);
				
					if(rsLayST != null)
					{
						try
						{
							while(rsLayST.next())
							{
								this.sotiendanop = formatter.format(rsLayST.getDouble("SOTIENNOP") - rsLayST.getDouble("SOTIENDATT"));
							}
							rsLayST.close();
						}catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				}
	           //----------END
		
				query = "";
				
				String conditionETC = "";
				String conditionOTC = "";
				String conditionDUNO = "";
				String conditionHDKhac = "";
				
			//TÌM KIẾM THEO ĐỐI TÁC
				if(this.nppIds.length() > 0)
				{
					conditionETC += " AND NPP.PK_SEQ in (" + this.nppIds + ") ";
					conditionOTC += " AND HD.PK_SEQ < 0 ";
					conditionDUNO += " AND DN.PK_SEQ < 0 ";
				}
			//TÌM KIẾM THEO KH
				if(this.khIds.length() > 0)
				{
					conditionETC += " AND KH.PK_SEQ  in (" + this.khIds + ") ";
					conditionOTC += " AND KH.PK_SEQ  in (" + this.khIds + ") ";
					conditionDUNO += " AND DN.KHACHHANG_FK  in (" + this.khIds + ") ";
					conditionHDKhac += " AND KH.PK_SEQ  in (" + this.khIds + ") ";
				}
				
			//TÌM KIẾM THEO NVBH
				if(this.nhanvienBHIds.length() > 0)
				{
					conditionETC +=
					"   AND   hd.KHACHHANG_FK  in \n" +
					"   ( \n"+
					"			 						SELECT  c.KHACHHANG_FK \n"+ 
					"									FROM 	DAIDIENKINHDOANH a INNER JOIN TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+   			
					"											inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
					"									WHERE a.PK_SEQ ='"+this.nhanvienBHIds+"'  \n"+
					"		  						) \n";
				 
					conditionOTC +=
						"   AND   hd.KHACHHANG_FK  in \n" +
						"   ( \n"+
						"			 						SELECT  c.KHACHHANG_FK \n"+ 
						"									FROM 	DAIDIENKINHDOANH a INNER JOIN TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+   			
						"											inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
						"									WHERE a.PK_SEQ ='"+this.nhanvienBHIds+"'  \n"+
						"		  						) \n";
				 
					conditionHDKhac +=
						"   AND   hd.KHACHHANG_FK  in \n" +
						"   ( \n"+
						"			 						SELECT  c.KHACHHANG_FK \n"+ 
						"									FROM 	DAIDIENKINHDOANH a INNER JOIN TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+   			
						"											inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
						"									WHERE a.PK_SEQ ='"+this.nhanvienBHIds+"'  \n"+
						"		  						) \n";
					
					conditionDUNO += " AND DN.DDKD_FK in ("+ this.nhanvienBHIds +" ) \n" ;
				}
			// TÌM KIẾM THEO NHANVIENGIAONHAN  
				if(this.nhanvienGNIds.length() > 0) 
				{
					conditionETC += " AND HD.KHACHHANG_FK  in ( SELECT KHACHHANG_FK FROM NVGN_KH WHERE NVGN_FK IN ( "+ this.nhanvienGNIds +" )) \n" ;
					/*conditionOTC += 
					"  AND HD.PK_SEQ  in " +
					"           (SELECT C.PK_SEQ     "+                 
                    "             FROM  PHIEUXUATKHO K INNER JOIN PHIEUXUATKHO_DONHANG A ON K.PK_SEQ= A.PXK_FK   "+                         
                    "                   INNER JOIN HOADON_DDH B ON A.DONHANG_FK = B.DDH_FK             "+              
                    "                   INNER JOIN HOADON C ON B.HOADON_FK= C.PK_SEQ               "+       
                    "             WHERE C.TRANGTHAI in (2,4) AND K.NVGN_FK IN ("+ this.nhanvienGNIds +") ) "; */
					
					conditionOTC += " AND HD.KHACHHANG_FK  in ( SELECT KHACHHANG_FK FROM NVGN_KH WHERE NVGN_FK IN ( "+ this.nhanvienGNIds +" )) \n" ;
					
					conditionHDKhac += " AND HD.KHACHHANG_FK  in ( SELECT KHACHHANG_FK FROM NVGN_KH WHERE NVGN_FK IN ( "+ this.nhanvienGNIds +" )) \n" ;
					
					conditionDUNO += " AND DN.KHACHHANG_FK  in ( SELECT KHACHHANG_FK FROM NVGN_KH WHERE NVGN_FK IN ( "+ this.nhanvienGNIds +" )) " ;
				}
			// TÌM KIẾM THEO SỐ HD TÀI CHÍNH
				if(this.hdIds.length() > 0)
				{
					conditionETC += " AND HD.PK_SEQ  in (" + this.hdIds + ") ";
					conditionOTC += " AND HD.PK_SEQ  in (" + this.hdIds + ") ";
					conditionHDKhac += " AND HD.PK_SEQ  in (" + this.hdIds + ") ";
					conditionDUNO += " AND DN.PK_SEQ  < 0 ";
				}
			
			// TÌM KIẾM THEO NGAY
				if(this.tungay.trim().length() > 0)
				{
					conditionETC += " AND HD.NGAYXUATHD >= '"+ this.tungay +"' ";
					conditionOTC += " AND HD.NGAYXUATHD >= '"+ this.tungay +"' ";
					conditionDUNO += " AND  DN.NGAYDUNO >= '"+ this.tungay +"' ";
					conditionHDKhac += " AND HD.ngayhoadon >= '"+ this.tungay +"' ";
				}
				if(this.denngay.trim().length() > 0)
				{
					conditionETC += " AND HD.NGAYXUATHD <= '"+ this.denngay +"' ";
					conditionOTC += " AND HD.NGAYXUATHD <= '"+ this.denngay +"' ";
				    conditionDUNO += " AND  DN.NGAYDUNO <= '"+ this.denngay +"' ";
				    conditionHDKhac += " AND HD.ngayhoadon >= '"+ this.tungay +"' ";
				}
				
		
		if( this.hoadonList.size() <= 0)
		{
//			NumberFormat formatter = new DecimalFormat("#,###,###");
				if(this.id.length() > 0)
				{
					query += 	"SELECT 0 AS LOAIHD, KH.PK_SEQ AS KHID,KH.MAFAST + '-' + KH.TEN AS MAKH, NPP.PK_SEQ AS NPPID,NPP.MAFAST + '-' + NPP.TEN AS MANPP, HD.PK_SEQ, HD.KYHIEU AS KYHIEU, HD.SOHOADON, \n" + 
								"		HD.NGAYXUATHD AS NGAYHOADON, \n" +
			 	   				"		( CAST(ISNULL(HD.TONGTIENAVAT,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0) ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) - CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0)) ) AS SOTIENVND, \n" +
			 	   				"		TT_HD.SOTIENTT  , 0 as IS_KHLE, HD.TRANGTHAI, 0 CANTRU \n" +
			 	   				" FROM 	ERP_THUTIENNPP_HOADON TT_HD \n" +
			 	   				" 		INNER JOIN ERP_HOADONNPP HD ON TT_HD.HOADONNPP_FK = HD.PK_SEQ AND TT_HD.LOAIHD = 0 \n" +
			 	   			    " 		LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_DAT_FK AND NPP.LOAINPP=4 and NPP.TRUCTHUOC_FK = '"+ this.nppId +"'  \n" +
			 	   		        " 		LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n" +
				 	   		    " 				AND TT_HD.KHACHHANG_FK in \n"+
						    	" 				(SELECT PK_SEQ from KHACHHANG WHERE KBH_FK=100052 and NPP_FK='" + this.nppId +"') \n"+
								" LEFT JOIN	\n" +
								" ( 	\n" +
								"		SELECT  TTHD.KHACHHANG_FK,TTHD.HOADONNPP_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n" + 		
								"		FROM 	ERP_THUTIENNPP_HOADON TTHD \n" +
								"				INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n" + 		
								"		WHERE 	TTHD.LOAIHD= 0 AND TT.TRANGTHAI != '2' AND TTHD.THUTIENNPP_FK != '" + this.id + "' AND TTHD.THUTIENNPP_FK < " + this.id + "  and TT.NPP_FK = " +this.nppId+ 		
								"				AND TTHD.HOADONNPP_FK IN "+
								"				(SELECT HOADONNPP_FK FROM ERP_THUTIENNPP_HOADON WHERE LOAIHD = 0 AND THUTIENNPP_FK = '" + this.id + "' ) \n" + 
								"		GROUP BY TTHD.KHACHHANG_FK,HOADONNPP_FK \n" +
								" )DATHU ON TT_HD.HOADONNPP_FK = DATHU.HOADONNPP_FK  AND DATHU.KHACHHANG_FK = HD.KHACHHANG_FK   \n" +
			//TRỪ SỐ TIỀN CUA HD TRONG XOANOKHACHHANG
								"LEFT JOIN ( \n"+
								"     SELECT XNHD.KHACHHANG_FK, HOADON_FK, SUM(ISNULL(SOTIENXOA,0)) SOTIENXOA  \n" +
								"     FROM 	 XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n" +
								"     WHERE  XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100052')   " +
								"            AND XNHD.LOAIHD = 0 AND XN.NPP_FK = "+this.nppId+
								"	  GROUP BY XNHD.KHACHHANG_FK, HOADON_FK "+	
								" ) XOANO ON HD.PK_SEQ = XOANO.HOADON_FK AND XOANO.KHACHHANG_FK = HD.KHACHHANG_FK \n"+
			//TRỪ SỐ TIỀN CẤN TRỪ CÔNG NỢ
								"LEFT JOIN ( \n"+
								"     SELECT CT_HD.HOADON_FK, CT_HD.KHACHHANG_FK ,SUM(SOTIENCANTRU) as SOTIENCANTRU \n" +
								"     FROM CANTRUCONGNO CT INNER JOIN CANTRUCONGNO_HOADON CT_HD ON CT.PK_SEQ = CT_HD.CANTRUCONGNO_FK  \n" +
								"     WHERE  CT.TRANGTHAI = 1 AND CT.NPP_FK = '" + this.nppId + "' group by  CT_HD.HOADON_FK, CT_HD.KHACHHANG_FK     \n"+
								" ) CTCN ON HD.PK_SEQ = CTCN.HOADON_FK AND  CTCN.KHACHHANG_FK = HD.KHACHHANG_FK  \n"+								
			//TRỪ SỐ TIỀN CỦA BÙ TRỪ CÔNG NỢ TRONG BUTRUCONGNO
								" LEFT JOIN ( \n"+
								"		  select bthd.HOADON_FK,bthd.KHACHHANG_FK, SUM(ISNULL(bthd.GHINO,0)) as GHINO, SUM(ISNULL(bthd.GHICO,0)) as GHICO \n"+
								"		  from BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
								"		  where bt.TRANGTHAI = 1 AND BT.NPP_FK = "+this.nppId+ 
								"		  group by bthd.HOADON_FK, bthd.KHACHHANG_FK \n"+	
								" )bthd on HD.PK_SEQ = bthd.HOADON_FK and bthd.KHACHHANG_FK = HD.KHACHHANG_FK \n"+
								" WHERE ISNULL(HD.LOAIHOADON,0)= 0 AND TT_HD.THUTIENNPP_FK ='" + this.id + "' AND  " +
						    	"      ( CAST(ISNULL(HD.TONGTIENAVAT,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0) ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) - CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0)) ) > 0     \n";  
					
						query += " UNION ALL ";		
						
						query += 	"SELECT 0 AS LOAIHD, KH.PK_SEQ AS KHID,KH.MAFAST + '-' + KH.TEN AS MAKH, NPP.PK_SEQ AS NPPID,NPP.MAFAST + '-' + NPP.TEN AS MANPP, HD.PK_SEQ, HD.KYHIEU AS KYHIEU, HD.SOHOADON, \n" + 
						"		HD.NGAYXUATHD AS NGAYHOADON, \n" +
		   				"		( CAST(ISNULL(HD.TONGTIENAVAT,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0) ) ) AS SOTIENVND, \n" +
		   				"		TT_HD.SOTIENTT  , 0 as IS_KHLE, HD.TRANGTHAI, 0 CANTRU \n" +
		   				" FROM 	ERP_THUTIENNPP_HOADON TT_HD \n" +
		   				" 		INNER JOIN ERP_HOADONNPP HD ON TT_HD.HOADONNPP_FK = HD.PK_SEQ AND TT_HD.LOAIHD = 0 \n" +
		   			    " 		LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_DAT_FK AND NPP.LOAINPP=4 and NPP.TRUCTHUOC_FK = '"+ this.nppId +"'  \n" +
		   		        " 		LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n" +
		 	   		    " 				AND TT_HD.KHACHHANG_FK in \n"+
				    	" 				(SELECT PK_SEQ from KHACHHANG WHERE KBH_FK=100052 and NPP_FK='" + this.nppId +"') \n"+
						" LEFT JOIN	\n" +
						" ( 	\n" +
						"		SELECT  TTHD.NPP_FK,TTHD.HOADONNPP_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n" + 		
						"		FROM 	ERP_THUTIENNPP_HOADON TTHD \n" +
						"				INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n" + 		
						"		WHERE 	TTHD.LOAIHD = 0 AND TT.TRANGTHAI != '2' AND TTHD.THUTIENNPP_FK != '" + this.id + "' AND TTHD.THUTIENNPP_FK < " + this.id + " and TT.NPP_FK = " +this.nppId+ 		
						"				AND TTHD.HOADONNPP_FK IN "+
						"				(SELECT HOADONNPP_FK FROM ERP_THUTIENNPP_HOADON WHERE LOAIHD = 0 AND THUTIENNPP_FK = '" + this.id + "' ) \n" + 
						"		GROUP BY TTHD.NPP_FK,HOADONNPP_FK \n" +
						" )DATHU ON TT_HD.HOADONNPP_FK = DATHU.HOADONNPP_FK  AND DATHU.NPP_FK = HD.NPP_DAT_FK   \n" +
/*		//TRỪ SỐ TIỀN CUA HD TRONG XOANOKHACHHANG
						"LEFT JOIN ( \n"+
						"     SELECT XNHD.KHACHHANG_FK, HOADON_FK, SUM(ISNULL(SOTIENXOA,0)) SOTIENXOA  \n" +
						"     FROM 	 XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n" +
						"     WHERE  XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100052')   " +
						"            AND XNHD.LOAIHD = 0 AND XN.NPP_FK = "+this.nppId+
						"	  GROUP BY XNHD.KHACHHANG_FK, HOADON_FK "+	
						" ) XOANO ON HD.PK_SEQ = XOANO.HOADON_FK AND XOANO.KHACHHANG_FK = HD.KHACHHANG_FK \n"+
		//TRỪ SỐ TIỀN CẤN TRỪ CÔNG NỢ
						"LEFT JOIN ( \n"+
						"     SELECT CT_HD.HOADON_FK, CT_HD.KHACHHANG_FK ,SUM(SOTIENCANTRU) as SOTIENCANTRU \n" +
						"     FROM CANTRUCONGNO CT INNER JOIN CANTRUCONGNO_HOADON CT_HD ON CT.PK_SEQ = CT_HD.CANTRUCONGNO_FK  \n" +
						"     WHERE  CT.TRANGTHAI = 1 AND CT.NPP_FK = '" + this.nppId + "' group by  CT_HD.HOADON_FK, CT_HD.KHACHHANG_FK     \n"+
						" ) CTCN ON HD.PK_SEQ = CTCN.HOADON_FK AND  CTCN.KHACHHANG_FK = HD.KHACHHANG_FK  \n"+								
		//TRỪ SỐ TIỀN CỦA BÙ TRỪ CÔNG NỢ TRONG BUTRUCONGNO
						" LEFT JOIN ( \n"+
						"		  select bthd.HOADON_FK,bthd.KHACHHANG_FK, SUM(ISNULL(bthd.GHINO,0)) as GHINO, SUM(ISNULL(bthd.GHICO,0)) as GHICO \n"+
						"		  from BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
						"		  where bt.TRANGTHAI = 1 AND BT.NPP_FK "+this.nppId+ 
						"		  group by bthd.HOADON_FK, bthd.KHACHHANG_FK \n"+	
						" )bthd on HD.PK_SEQ = bthd.HOADON_FK and bthd.KHACHHANG_FK = HD.KHACHHANG_FK \n"+*/
						" WHERE ISNULL(HD.LOAIHOADON,0)= 0 AND TT_HD.THUTIENNPP_FK ='" + this.id + "' AND  " +
				    	"      ( CAST(ISNULL(HD.TONGTIENAVAT,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0) ) ) > 0     \n";  
			
		query += " UNION ALL ";		

				
				    query += 
						    	"SELECT 0 AS LOAIHD, KH.PK_SEQ AS KHID ,KH.MAFAST + '-' + KH.TEN AS MAKH, '0' AS NPPID, '0' AS MANPP, HD.PK_SEQ, HD.KYHIEU AS KYHIEU, HD.SOHOADON, \n"+
						    	"	HD.NGAYXUATHD AS NGAYHOADON,  \n"+
						    	"	( CAST( ISNULL(HD.TONGTIENAVAT,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0) ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) - CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0)) ) AS SOTIENVND, \n"+
						    	"	TT_HD.SOTIENTT, 0 as IS_KHLE, HD.TRANGTHAI, 0 CANTRU   \n"+
						    	"FROM 	ERP_THUTIENNPP_HOADON TT_HD \n"+
						    	" 		LEFT JOIN HOADON HD ON TT_HD.HOADONNPP_FK = HD.PK_SEQ AND TT_HD.LOAIHD = 0  AND TT_HD.KHACHHANG_FK in \n"+
						    	"                                                       (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK=100025 and NPP_FK='" + this.nppId +"')  " +		    	
						    	" 		LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n"+
						    	" 		LEFT JOIN	\n"+
						    	" 		( 	\n"+
						    	"			SELECT 	TTHD.KHACHHANG_FK, TTHD.HOADONNPP_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
						    	"			FROM 	ERP_THUTIENNPP_HOADON TTHD \n"+
						    	"					INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+
						    	"			WHERE 	TTHD.LOAIHD = 0 AND TT.TRANGTHAI != '2' AND TTHD.THUTIENNPP_FK != '" + this.id + "' AND TTHD.THUTIENNPP_FK < " + this.id + "  AND TT.NPP_FK = "+this.nppId+
						    	"					AND TTHD.HOADONNPP_FK IN \n"+
						    	"		(SELECT HOADONNPP_FK FROM ERP_THUTIENNPP_HOADON WHERE LOAIHD=0 AND THUTIENNPP_FK = '" + this.id + "' ) \n"+
						    	"	GROUP BY HOADONNPP_FK, TTHD.KHACHHANG_FK \n"+
						    	" )DATHU ON TT_HD.HOADONNPP_FK = DATHU.HOADONNPP_FK \n"+
			//TRỪ SỐ TIỀN CUA HD TRONG XOANOKHACHHANG
								"LEFT JOIN ( \n"+
								"     SELECT XNHD.KHACHHANG_FK ,HOADON_FK, SUM(ISNULL(SOTIENXOA,0)) SOTIENXOA \n" +
								"     FROM 	XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n" +
								"     WHERE  XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100052')   " +
								"            AND XNHD.LOAIHD = 0 AND XN.NPP_FK = "+this.nppId+
								"	  GROUP BY  XNHD.KHACHHANG_FK ,HOADON_FK "+
								" ) XOANO ON HD.PK_SEQ = XOANO.HOADON_FK \n"+
			//TRỪ SỐ TIỀN CẤN TRỪ CÔNG NỢ
								"LEFT JOIN ( \n"+
								"     SELECT CT_HD.KHACHHANG_FK,CT_HD.HOADON_FK, SUM(SOTIENCANTRU) as SOTIENCANTRU \n" +
								"     FROM CANTRUCONGNO CT INNER JOIN CANTRUCONGNO_HOADON CT_HD ON CT.PK_SEQ = CT_HD.CANTRUCONGNO_FK  \n" +
								"     WHERE  CT.TRANGTHAI = 1 AND CT.NPP_FK = '" + this.nppId + "' group by  CT_HD.HOADON_FK, CT_HD.KHACHHANG_FK    \n"+
								" ) CTCN ON HD.PK_SEQ = CTCN.HOADON_FK \n"+		
			//TRỪ SỐ TIỀN CỦA BÙ TRỪ CÔNG NỢ TRONG BUTRUCONGNO
								" LEFT JOIN ( \n"+
								"		  select bthd.HOADON_FK,bthd.KHACHHANG_FK, SUM(ISNULL(bthd.GHINO,0)) as GHINO, SUM(ISNULL(bthd.GHICO,0)) as GHICO \n"+
								"		  from BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
								"		  where bt.TRANGTHAI = 1 AND BT.NPP_FK = "+this.nppId+ 
								"		  group by bthd.HOADON_FK, bthd.KHACHHANG_FK \n"+	
								" )bthd on HD.PK_SEQ = bthd.HOADON_FK and bthd.KHACHHANG_FK = HD.KHACHHANG_FK \n"+
						    	"WHERE ISNULL(HD.LOAIHOADON,0) = 0 AND TT_HD.THUTIENNPP_FK ='" + this.id + "' AND  " +
						    	"      ( CAST( ISNULL(HD.TONGTIENAVAT,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0) ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) - CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0)) ) > 0     \n";
						    	
					query += " UNION ALL ";	
							
				    query += 
						    	"SELECT 2 AS LOAIHD, KH.PK_SEQ AS KHID, KH.MAFAST + '-' + KH.TEN AS MAKH, '0' AS NPPID, '0' AS MANPP, HD.PK_SEQ, 'GESO' AS KYHIEU, '' AS SOHOADON, \n"+
						    	"	 HD.NGAYDUNO AS NGAYHOADON, \n"+
						    	"	( CAST(ISNULL(HD.SONO,0) as numeric(18,0) ) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0)) ) AS SOTIENVND, \n"+
						    	"	TT_HD.SOTIENTT, 0 as IS_KHLE, '2' AS TRANGTHAI, 0 CANTRU  \n"+
						    	" FROM ERP_THUTIENNPP_HOADON TT_HD \n"+
						    	" LEFT JOIN DUNO_KHACHHANG HD ON TT_HD.HOADONNPP_FK = HD.PK_SEQ  AND TT_HD.LOAIHD = 2 \n"+
						    	" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n"+
						    	" LEFT JOIN	\n"+
						    	" ( 	\n"+
						    	"	SELECT TTHD.KHACHHANG_FK,TTHD.HOADONNPP_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
						    	"	FROM ERP_THUTIENNPP_HOADON TTHD \n"+
						    	"	INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+
						    	"	WHERE TTHD.LOAIHD = 2 AND TT.TRANGTHAI != '2' AND TTHD.THUTIENNPP_FK != '" + this.id + "' AND TTHD.THUTIENNPP_FK < " + this.id + " AND TT.NPP_FK = "+this.nppId+
						    	"	AND TTHD.HOADONNPP_FK IN \n"+
						    	"		(SELECT HOADONNPP_FK FROM ERP_THUTIENNPP_HOADON WHERE LOAIHD = 2 AND  THUTIENNPP_FK = '" + this.id + "' \n"+
						    	 ") 	\n"+
						    	"	GROUP BY HOADONNPP_FK, TTHD.KHACHHANG_FK \n"+
						    	" )DATHU ON TT_HD.HOADONNPP_FK = DATHU.HOADONNPP_FK AND DATHU.KHACHHANG_FK = HD.KHACHHANG_FK \n"+
						    	"WHERE  TT_HD.THUTIENNPP_FK ='" + this.id + "' " +
						    	"       AND ( CAST(ISNULL(HD.SONO,0) as numeric(18,0) ) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0)) ) > 0 \n";
							
							
						  query += " UNION ALL ";
						  
						  //HOA DON KHAC
						  query += 
						    	"SELECT 1 as LOAIHD, KH.PK_SEQ AS KHID ,KH.MAFAST + '-' + KH.TEN AS MAKH, '0' AS NPPID, '0' AS MANPP, HD.PK_SEQ, HD.KYHIEUHOADON AS KYHIEU, HD.SOHOADON, \n"+
						    	"	HD.Ngayhoadon AS NGAYHOADON,  \n"+
						    	"	( CAST( ISNULL(HD.AVAT,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0) ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0))  ) AS SOTIENVND, \n"+
						    	"	TT_HD.SOTIENTT, 0 as IS_KHLE, HD.TRANGTHAI, 0 CANTRU   \n"+
						    	"FROM 	ERP_THUTIENNPP_HOADON TT_HD \n"+
						    	" 		LEFT JOIN ERP_HOADONPHELIEU HD ON TT_HD.HOADONNPP_FK = HD.PK_SEQ AND TT_HD.LOAIHD = 1  \n"+
						    	" 		INNER JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n"+
						    	" 		LEFT JOIN	\n"+
						    	" 		( 	\n"+
						    	"			SELECT 	TTHD.KHACHHANG_FK,TTHD.HOADONNPP_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
						    	"			FROM 	ERP_THUTIENNPP_HOADON TTHD \n"+
						    	"					INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+
						    	"			WHERE 	TTHD.LOAIHD = 1 AND TT.TRANGTHAI != '2' AND TTHD.THUTIENNPP_FK != '" + this.id + "' AND TTHD.THUTIENNPP_FK < " + this.id + " AND TT.NPP_FK = "+this.nppId+
						    	"					AND TTHD.HOADONNPP_FK IN \n"+
						    	"		(SELECT HOADONNPP_FK FROM ERP_THUTIENNPP_HOADON WHERE LOAIHD = 1 AND THUTIENNPP_FK = '" + this.id + "' ) \n"+
						    	"		GROUP BY HOADONNPP_FK,TTHD.KHACHHANG_FK  \n"+
						    	" )DATHU ON TT_HD.HOADONNPP_FK = DATHU.HOADONNPP_FK AND DATHU.KHACHHANG_FK = HD.KHACHHANG_FK \n"+
						    	//TRỪ SỐ TIỀN CUA HD TRONG XOANOKHACHHANG
								"LEFT JOIN ( \n"+
								"     SELECT XNHD.KHACHHANG_FK, HOADON_FK, SUM(ISNULL(SOTIENXOA,0)) SOTIENXOA \n" +
								"     FROM XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n" +
								"     WHERE  XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100052')   " +
								"            AND XNHD.LOAIHD = 1 AND XN.NPP_FK = "+this.nppId+
								"	  GROUP BY XNHD.KHACHHANG_FK, HOADON_FK "+
								" ) XOANO ON HD.PK_SEQ = XOANO.HOADON_FK AND XOANO.KHACHHANG_FK = HD.KHACHHANG_FK  \n"+
								//TRỪ SỐ TIỀN CỦA BÙ TRỪ CÔNG NỢ TRONG BUTRUCONGNO
								" LEFT JOIN ( \n"+
								"		  select bthd.HOADON_FK,bthd.KHACHHANG_FK, SUM(ISNULL(bthd.GHINO,0)) as GHINO, SUM(ISNULL(bthd.GHICO,0)) as GHICO \n"+
								"		  from BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
								"		  where bt.TRANGTHAI = 1 AND BT.NPP_FK = "+this.nppId+ 
								"		  group by bthd.HOADON_FK, bthd.KHACHHANG_FK \n"+	
								" )bthd on HD.PK_SEQ = bthd.HOADON_FK and bthd.KHACHHANG_FK = HD.KHACHHANG_FK \n"+
						    	"WHERE TT_HD.THUTIENNPP_FK ='" + this.id + "' AND  " +
						    	"    (  CAST( ISNULL(HD.avat,0) as numeric(18,0)) - CAST(ISNULL(DATHU.TONGTHU, '0') as numeric(18,0)) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) ) > 0     \n";
						    	
						    	
					query += " UNION ALL ";	
				
			}
				
		//A- HÓA ĐƠN CỦA KHACHHANG ETC/ DOITAC
				query += 	"(SELECT 0 as LOAIHD,HOADON.KHID, HOADON.MAKH, HOADON.NPPID ,HOADON.MANPP , HOADON.PK_SEQ, HOADON.KYHIEU, HOADON.SOHOADON, HOADON.NGAYHOADON, \n" +	
							"       ( CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) + CAST(ISNULL(bthd.GHINO,0) as numeric(18,0))-CAST(ISNULL(bthd.GHICO,0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)  ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0) ) ) AS SOTIENVND, \n"+
							" 0 AS SOTIENTT, 0 as IS_KHLE, HOADON.TRANGTHAI, 0 CANTRU  \n" +	
							"FROM ( \n" +		 
							"	SELECT KH.PK_SEQ AS KHID,KH.MAFAST + '-' + KH.TEN AS MAKH, NPP.PK_SEQ as NPPID,NPP.MAFAST + '-' + NPP.TEN as MANPP, HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD AS NGAYHOADON, HD.TRANGTHAI , \n" + 
							"	HD.TONGTIENAVAT   AS TONGTIENAVAT \n" +		
							"	FROM ERP_HOADONNPP HD" +
							"        LEFT join NHAPHANPHOI NPP on HD.NPP_DAT_FK= NPP.PK_SEQ AND NPP.LOAINPP = 4 and NPP.TRUCTHUOC_FK = '"+ this.nppId +"'  \n" +
							"        LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n" +
							"	WHERE    HD.TRANGTHAI in (2,4) and HD.NPP_FK = '"+ this.nppId +"' "+ conditionETC +"  \n";
				
				
				if(this.id.length() > 0)
				{
					query += "AND HD.PK_SEQ NOT IN (SELECT HOADONNPP_FK FROM ERP_THUTIENNPP_HOADON WHERE LOAIHD = 0 AND THUTIENNPP_FK = '" + this.id + "') \n";
					
				}
				
				query += 	") HOADON \n" + 
							"LEFT JOIN ( \n" +	
							"	SELECT KHACHHANG_FK,HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
							"	FROM  \n" +	
							"	( 	\n" +					
							"		SELECT TTHD.KHACHHANG_FK,  TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
							"		FROM ERP_THUTIENNPP_HOADON TTHD \n" +
							"		INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n" +   		
							"		WHERE TTHD.LOAIHD = 0 AND TT.NPP_FK= '"+ this.nppId +"' AND  TT.TRANGTHAI NOT IN (2)\n"	; 
				
							
				if(this.id.trim().length() > 0){
					query += " 		AND TT.PK_SEQ  != '" + this.id + "' \n";
				}
			
				query +=	" 		GROUP BY HOADONNPP_FK,TTHD.KHACHHANG_FK  \n" +  	
							"	) HOADONDATT  \n" +
							"	GROUP BY HOADONNPP_FK, KHACHHANG_FK " +   
							")DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADONNPP_FK AND DATHANHTOAN.KHACHHANG_FK =  HOADON.KHID  \n" +							
	   //TRỪ SỐ TIỀN CUA HD TRONG XOANOKHACHHANG
							"LEFT JOIN ( \n"+
							"     SELECT XNHD.KHACHHANG_FK ,HOADON_FK, SUM(ISNULL(SOTIENXOA,0)) SOTIENXOA \n" +
							"     FROM XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n" +
							"     WHERE  XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100052')   " +
							"            AND XNHD.LOAIHD = 0 AND XN.NPP_FK = "+this.nppId+
							"	  GROUP BY XNHD.KHACHHANG_FK ,HOADON_FK "+
							" ) XOANO ON HOADON.PK_SEQ = XOANO.HOADON_FK AND XOANO.KHACHHANG_FK =  HOADON.KHID   \n"+
		//TRỪ SỐ TIỀN CỦA BÙ TRỪ CÔNG NỢ TRONG BUTRUCONGNO
							" LEFT JOIN ( \n"+
							"		  select bthd.HOADON_FK,bthd.KHACHHANG_FK, SUM(ISNULL(bthd.GHINO,0)) as GHINO, SUM(ISNULL(bthd.GHICO,0)) as GHICO \n"+
							"		  from BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
							"		  where bt.TRANGTHAI = 1 AND BT.NPP_FK = "+this.nppId+ 
							"		  group by bthd.HOADON_FK, bthd.KHACHHANG_FK \n"+	
							" )bthd on HOADON.PK_SEQ = bthd.HOADON_FK and bthd.KHACHHANG_FK = HOADON.KHID \n"+
							" WHERE CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) + CAST(ISNULL(bthd.GHINO,0) as numeric(18,0))-CAST(ISNULL(bthd.GHICO,0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)  ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0) )  > 0   \n";
					if(this.hdIds.length() > 0)		
					{
						query += " AND HOADON.PK_SEQ in ( "+ this.hdIds +" )" ;
					}

				query += " )\n"; 
				
				query += " UNION ALL ";
				
		// B- HÓA ĐƠN CỦA KHACH HANG OTC
				// Đối vs khách hàng lẻ khi mới vào Trang tạo mới: mặc định trả hết
				query += 	"(SELECT 0 as LOAIHD, HOADON.KHID, HOADON.MAKH, HOADON.NPPID ,HOADON.MANPP , HOADON.PK_SEQ, HOADON.KYHIEU, HOADON.SOHOADON, HOADON.NGAYHOADON, \n" +	
							"       ( CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) - CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0))) AS SOTIENVND, \n"+
							" 0 AS SOTIENTT, (SELECT COUNT(PK_SEQ) FROM KHACHHANG WHERE XuatKhau = '0' AND PK_SEQ=HOADON.KHID ) AS IS_KHLE, HOADON.TRANGTHAI, CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0)) AS CANTRU \n" +
	
							"FROM ( \n" +		
							"		SELECT KH.PK_SEQ AS KHID,KH.MAFAST + '-' + KH.TEN AS MAKH,'0' as NPPID, '0' as MANPP, HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD AS NGAYHOADON,HD.TRANGTHAI ,  \n"+
							"        (HD.TONGTIENAVAT  ) as TONGTIENAVAT   \n"+
							"        FROM HOADON HD 	  \n " +							
							"             LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +
							"        WHERE  ISNULL(HD.LOAIHOADON,0) = 0 AND HD.TRANGTHAI in (2,4) and HD.NPP_FK = '"+ this.nppId +"' "+ conditionOTC +"   \n";
							
				
				if(this.id.length() > 0)
				{
					query += " AND HD.PK_SEQ NOT IN (SELECT HOADONNPP_FK FROM ERP_THUTIENNPP_HOADON WHERE LOAIHD = 0 AND THUTIENNPP_FK = '" + this.id + "') \n";
					
				}
			
				query += 	") HOADON \n" + 
							"LEFT JOIN ( \n" +	
							"	SELECT KHACHHANG_FK, HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
							"	FROM  \n" +	
							"	( 	\n" +					
							"		SELECT TTHD.KHACHHANG_FK ,TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
							"		FROM ERP_THUTIENNPP_HOADON TTHD \n" +
							"		INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n" +   		
							"		WHERE TTHD.LOAIHD = 0 AND TT.NPP_FK= '"+ this.nppId +"' AND  TT.TRANGTHAI NOT IN (2)\n"	; 
				
							
				if(this.id.trim().length() > 0){
					query += " 		AND TT.PK_SEQ  != '" + this.id + "' \n";
				}
			
				query +=	" 		GROUP BY HOADONNPP_FK, TTHD.KHACHHANG_FK  \n" +  	
							"	) HOADONDATT  \n" +
							"	GROUP BY HOADONNPP_FK, KHACHHANG_FK " +   
							")DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADONNPP_FK AND DATHANHTOAN.KHACHHANG_FK = HOADON.KHID \n" +
			//TRỪ SỐ TIỀN CUA HD TRONG XOANOKHACHHANG
							"LEFT JOIN ( \n"+
							"     SELECT XNHD.KHACHHANG_FK , HOADON_FK, SUM(ISNULL(SOTIENXOA,0)) SOTIENXOA \n" +
							"     FROM XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n" +
							"     WHERE XNHD.LOAIHD = 0 AND XN.NPP_FK = '" + this.nppId + "' and XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100025')     \n"+
							"	  GROUP BY XNHD.KHACHHANG_FK , HOADON_FK "+
							" ) XOANO ON HOADON.PK_SEQ = XOANO.HOADON_FK AND HOADON.KHID = XOANO.KHACHHANG_FK \n"+
			//TRỪ SỐ TIỀN CẤN TRỪ CÔNG NỢ
							"LEFT JOIN ( \n"+
							"     SELECT CT_HD.KHACHHANG_FK ,CT_HD.HOADON_FK, SUM(SOTIENCANTRU) as SOTIENCANTRU \n" +
							"     FROM CANTRUCONGNO CT INNER JOIN CANTRUCONGNO_HOADON CT_HD ON CT.PK_SEQ = CT_HD.CANTRUCONGNO_FK  \n" +
							"     WHERE  CT.TRANGTHAI = 1 AND CT.NPP_FK = '" + this.nppId + "' group by  CT_HD.HOADON_FK,  CT_HD.KHACHHANG_FK    \n"+
							" ) CTCN ON HOADON.PK_SEQ = CTCN.HOADON_FK AND HOADON.KHID = CTCN.KHACHHANG_FK \n"+
			// TRỪ SỐ TIỀN HD TRONG BUTRUCONGNO
							" LEFT JOIN ( \n"+
							"		  select bthd.HOADON_FK,bthd.KHACHHANG_FK, SUM(ISNULL(bthd.GHINO,0)) as GHINO, SUM(ISNULL(bthd.GHICO,0)) as GHICO \n" +							
							"		  from BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
							"		  where bt.NPP_FK = '" + this.nppId + "' and bt.TRANGTHAI = 1 \n"+ 
							"		  group by bthd.HOADON_FK, bthd.KHACHHANG_FK \n"+	
							" )bthd on HOADON.PK_SEQ = bthd.HOADON_FK and bthd.KHACHHANG_FK = HOADON.KHID \n"+
							" WHERE CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) + CAST(ISNULL(bthd.GHINO,0) as numeric(18,0))-CAST(ISNULL(bthd.GHICO,0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)  ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) - CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0))  > 0   \n";
					if(this.hdIds.length() > 0)		
					{
						query += " AND HOADON.PK_SEQ in ( "+ this.hdIds +" )" ;
					}

				query += " )\n"; 			
		
				query += "UNION ALL ";
				
		 //C- DU NO CUA KHACH HANG TINH DEN 30/06/2014
				query += " SELECT 2 as LOAIHD, KH.PK_SEQ AS KHID, KH.MAFAST + '-' + KH.TEN AS MAKH, '0' AS NPPID, '0' AS MANPP, DN.PK_SEQ, 'GESO' AS KYHIEU,'' AS SOHOADON, DN.NGAYDUNO AS NGAYHOADON, \n" +
						 "       ( CAST(DN.SONO AS numeric(18,0) ) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, '0') as numeric(18,0) ) )  AS SOTIENVND ,0 AS SOTIENTT, 0 AS IS_KHLE, '2' AS TRANGTHAI,0 CANTRU \n" +
						 " FROM DUNO_KHACHHANG DN INNER JOIN KHACHHANG KH ON DN.KHACHHANG_FK=KH.PK_SEQ \n" ;
				query += "     LEFT JOIN ( \n" +	
						 "	      SELECT KHACHHANG_FK, HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
						 "	      FROM  \n" +	
						 "	       ( 	\n" +					
						 "		    SELECT TTHD.KHACHHANG_FK ,TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
						 "			FROM ERP_THUTIENNPP_HOADON TTHD \n" +
						 "				INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n" +   		
						 "			WHERE TTHD.LOAIHD = 2 AND TT.NPP_FK= '"+ this.nppId +"' AND  TT.TRANGTHAI NOT IN (2)\n"	; 	
				
				if(this.id.trim().length() > 0){
					query += " 		AND TT.PK_SEQ  != '" + this.id + "' \n";
				}
				
				query +=	" 		GROUP BY TTHD.KHACHHANG_FK,HOADONNPP_FK \n" +  	
							"	   ) HOADONDATT  \n" +
							"	GROUP BY HOADONNPP_FK, KHACHHANG_FK " +   
							")DATHANHTOAN ON DN.PK_SEQ = DATHANHTOAN.HOADONNPP_FK AND DN.KHACHHANG_FK = DATHANHTOAN.KHACHHANG_FK " +
							" WHERE DN.NPP_FK = '"+ this.nppId +"'  " +
							"       AND CAST(DN.SONO AS numeric(18,0) ) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, '0') as numeric(18,0) ) > 0 "+ conditionDUNO +"  \n" ;	
									
				
			    if(this.id.length() >  0)   
				{
					query += " AND DN.PK_SEQ NOT IN (SELECT HOADONNPP_FK FROM ERP_THUTIENNPP_HOADON WHERE THUTIENNPP_FK = '" + this.id + "') \n";
					
				}
		
		//D-HOADONKHAC
			    query += "UNION ALL ";
			    
			    query += "(SELECT 1 as LOAIHD, HOADON.KHID, HOADON.MAKH, HOADON.NPPID ,HOADON.MANPP , HOADON.PK_SEQ, HOADON.KYHIEU KYHIEU, HOADON.SOHOADON, HOADON.NGAYHOADON, \n" +	
				"       ( CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) ) AS SOTIENVND, \n"+
				" 0 AS SOTIENTT, (SELECT COUNT(PK_SEQ) FROM KHACHHANG WHERE XuatKhau = '0' AND PK_SEQ=HOADON.KHID ) AS IS_KHLE, HOADON.TRANGTHAI, 0 AS CANTRU \n" +

				"FROM ( \n" +		
				"		SELECT KH.PK_SEQ AS KHID,KH.MAFAST + '-' + KH.TEN AS MAKH,'0' as NPPID, '0' as MANPP, HD.PK_SEQ, HD.KYHIEUHOADON KYHIEU, HD.SOHOADON, HD.NGAYHOADON AS NGAYHOADON,HD.TRANGTHAI ,  \n"+
				"        (HD.AVAT  ) as TONGTIENAVAT   \n"+
				"        FROM ERP_HoaDonPheLieu HD 	  \n " +							
				"             INNER JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +
				"        WHERE  HD.TRANGTHAI in (1) and HD.NPP_FK = '"+ this.nppId +"' "+ conditionHDKhac +"   \n";
				
	
				if(this.id.length() > 0)
				{
					query += " AND HD.PK_SEQ NOT IN (SELECT HOADONNPP_FK FROM ERP_THUTIENNPP_HOADON WHERE LOAIHD = 1 AND THUTIENNPP_FK = '" + this.id + "') \n";
					
				}

				query += 	") HOADON \n" + 
							"LEFT JOIN ( \n" +	
							"	SELECT KHACHHANG_FK,HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
							"	FROM  \n" +	
							"	( 	\n" +					
							"		SELECT TTHD.KHACHHANG_FK,TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
							"		FROM ERP_THUTIENNPP_HOADON TTHD \n" +
							"		INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n" +   		
							"		WHERE TTHD.LOAIHD = 1 AND TT.NPP_FK= '"+ this.nppId +"' AND  TT.TRANGTHAI NOT IN (2)\n"	; 
				
							
				if(this.id.trim().length() > 0){
					query += " 		AND TT.PK_SEQ  != '" + this.id + "' \n";
				}
			
				query +=	" 		GROUP BY TTHD.KHACHHANG_FK, HOADONNPP_FK \n" +  	
							"	) HOADONDATT  \n" +
							"	GROUP BY HOADONNPP_FK, KHACHHANG_FK " +   
							")DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADONNPP_FK AND DATHANHTOAN.KHACHHANG_FK = HOADON.KHID \n" +
			//TRỪ SỐ TIỀN CUA HD TRONG XOANOKHACHHANG
							"LEFT JOIN ( \n"+
							"     SELECT XNHD.KHACHHANG_FK ,HOADON_FK, SUM(ISNULL(SOTIENXOA,0)) SOTIENXOA \n" +
							"     FROM XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n" +
							"     WHERE XNHD.LOAIHD = 1 AND XN.NPP_FK = '" + this.nppId + "' and XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100025')     \n"+
							"	  GROUP BY XNHD.KHACHHANG_FK ,HOADON_FK "+
							" ) XOANO ON HOADON.PK_SEQ = XOANO.HOADON_FK AND XOANO.KHACHHANG_FK = HOADON.KHID \n"+							
							" WHERE CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0))  - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)  ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0))  > 0   \n";
					if(this.hdIds.length() > 0)		
					{
						query += " AND HOADON.PK_SEQ in ( "+ this.hdIds +" )" ;
					}

				query += " )\n"; 			
			    
			    query += " ORDER BY SOTIENTT DESC, NGAYHOADON DESC, MAKH DESC, SOHOADON  DESC ";
				
			    System.out.println(query);
			    
				ResultSet rsHoadon = db.get(query);
				List<IHoadonNPP> hdList = new ArrayList<IHoadonNPP>();
				if(rsHoadon != null)
				{
					try 
					{
						IHoadonNPP hd = null;
						
						double tongDUNO = 0;
						
						while(rsHoadon.next())
						{							
							String id = rsHoadon.getString("PK_SEQ");							
							String kyhieu = rsHoadon.getString("KYHIEU");
							String sohoadon = rsHoadon.getString("SOHOADON");
							String ngayhd = rsHoadon.getString("NGAYHOADON")==null ? "":rsHoadon.getString("NGAYHOADON");;
							String avat = "" + rsHoadon.getDouble("SOTIENVND");
							String nppid= rsHoadon.getString("NPPID")==null ? "":rsHoadon.getString("NPPID");
							String manpp= rsHoadon.getString("MANPP")==null ? "":rsHoadon.getString("MANPP");
							String khid= rsHoadon.getString("KHID")==null ? "":rsHoadon.getString("KHID");			
							String makh= rsHoadon.getString("MAKH")==null ? "": rsHoadon.getString("MAKH");
							String isKHLe = rsHoadon.getString("IS_KHLE");
							String loaihd = rsHoadon.getString("LOAIHD");
							
							String tt =  rsHoadon.getString("trangthai");
							String trangthai= "";
							if(tt.equals("1")) trangthai = "Chưa xác nhận";
							else if(tt.equals("2")) trangthai = "Đã xác nhận";
							else if(tt.equals("3")) trangthai = "Đã xóa";
							else if(tt.equals("4")) trangthai = "Đã in";
							else if(tt.equals("5")) trangthai = "Đã hủy";
							
							if(nppid.equals("0")) nppid="";
							if(manpp.equals("0")) manpp="";
							
							
							double dathanhtoan = 0;
							if(this.id.length() > 0)
							{								
									dathanhtoan = rsHoadon.getDouble("SOTIENTT");
							}
							
							double cantru = rsHoadon.getDouble("CANTRU");
							
						// TÍNH DƯ NỢ CỦA KHÁCH HÀNG
							if(khid.trim().length() > 0)
							{
								tongDUNO = 0;
									
									//Tinh tong du no
									
									query =	" SELECT ABS(SUM(a.SOTIENAVAT-a.SOTIENTT)) as TONGDUNO "+
											" FROM 	 ERP_THUTIENNPP_HOADON a inner join ERP_THUTIENNPP b on a.THUTIENNPP_FK=b.PK_SEQ "+
											" WHERE  a.KHACHHANG_FK = '" + khid + "' and b.TRANGTHAI != 2 "+
											" GROUP BY a.KHACHHANG_FK "+
											" HAVING SUM(a.SOTIENAVAT-a.SOTIENTT) < 0 ";
									
									
									ResultSet rsCHECK = db.get(query);
									if(rsCHECK != null)
									{
										if(rsCHECK.next())
											tongDUNO = rsCHECK.getDouble("TONGDUNO");
									}
									rsCHECK.close();
								
							}
						// TÍNH DƯ NỢ CỦA ĐỐI TÁC
							if(nppid.trim().length() > 0)
							{/*
								tongDUNO = 0;
									
									//Tinh tong du no
									
									query =	" SELECT ABS(SUM(a.SOTIENAVAT-a.SOTIENTT)) as TONGDUNO \n"+
											" FROM 	 ERP_THUTIENNPP_HOADON a inner join ERP_THUTIENNPP b on a.THUTIENNPP_FK=b.PK_SEQ \n"+
											" WHERE  a.NPP_DAT_FK  = '" + nppid + "' and b.TRANGTHAI != 2 \n"+
											" GROUP BY a.NPP_DAT_FK \n"+
											" having SUM(a.SOTIENAVAT-a.SOTIENTT) < 0 \n";
									
									
									ResultSet rsCHECK = db.get(query);
									if(rsCHECK != null)
									{
										if(rsCHECK.next())
											tongDUNO = rsCHECK.getDouble("TONGDUNO");
									}
									rsCHECK.close();
								
							*/}	
													
							
							hd = new HoadonNPP(id, "", kyhieu, sohoadon, ngayhd, avat, "", Double.toString(dathanhtoan), "","","");
							hd.setNppId(nppid);
							hd.setNppMa(manpp);
							hd.setKhId(khid);
							hd.setKhMa(makh);
							hd.setIsKHLe(isKHLe);
							hd.setTrangthaihd(trangthai);
							hd.setDuno(Double.toString(tongDUNO));
							hd.setCantru(Double.toString(cantru));
							hd.setLoaihd(loaihd);
							hdList.add(hd);
							
						}
						
						rsHoadon.close();
					} 
					catch (Exception e) { e.printStackTrace(); }
				}
				this.hoadonList = hdList;
				
				///
				if(this.id.trim().length()>0)
				{
				query=" SELECT * FROM ERP_THUTIENNPP_HOADONTHEM where THUTIEN_FK='"+this.id+"' \n";
				
				System.out.println("INIT ERP_THUTIENNPP_HOADONTHEM___: " + query);
				ResultSet TT = db.get(query);
				
				if(TT!=null)
				{
					try{
						
						while(TT.next())
						{
							this.add_makh = TT.getString("KHACHHANG_FK");
							this.add_sohoadon = TT.getString("SOHOADON");
							this.add_ngayhd = TT.getString("NGAYCHUNGTU");
							this.add_tongsotien = TT.getString("SOTIENAVAT");
							this.add_thanhtoan = TT.getString("SOTIENTT");
						}
					}
					catch(Exception e)
					{
						System.out.println("115.Exception: " + e.getMessage());
					}
				}
			}
		}				
		
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBclose() 
	{
		
	}

	
	public String getNgayghiso() 
	{
		return this.ngayghiso;
	}

	public void setNgayghiso(String ngayghiso) 
	{
		this.ngayghiso = ngayghiso;
	}

	public String getNoidungId()
	{
		return this.ndId;
	}

	public void setNoidungId(String ndId) 
	{
		this.ndId = ndId;
	}

	public ResultSet getNoidungRs()
	{
		return this.ndRs;
	}

	public void setNoidungRs(ResultSet ndRs) 
	{
		this.ndRs = ndRs;	
	}

	
	public String getSochungtu() {
		
		return this.sochungtu;
	}

	
	public void setSochungtu(String soct) {
		
		this.sochungtu = soct;
	}

	public String getSotiendanop()
	{
		return sotiendanop;
	}

	public void setSotiendanop(String sotiendanop) 
	{
		this.sotiendanop = sotiendanop;
	}
	
	public String getSotienthuthem() 
	{
		return sotienthuthem;
	}
	
	public void setSotienthuthem(String sotienthuthem) 
	{
		this.sotienthuthem = sotienthuthem;
	}
	
	public String getTigia_hoadon() {
		
		return Tigia_hoadon;
	}

	
	public void setTigia_hoadon(String _Tigia_hoadon) {
		
		this.Tigia_hoadon=_Tigia_hoadon;
	}

	public String getDinhkhoanco() {
		return this.dinhkhoanco;
	}

	public void setDinhkhoanco(String dinhkhoanco) {
		this.dinhkhoanco= dinhkhoanco;
		
	}

	public String getDinhkhoancoId() {
		return this.dinhkhoancoId;
	}

	public void setDinhkhoancoId(String dinhkhoancoId) {
		this.dinhkhoancoId = dinhkhoancoId;
		
	}

	public String getDoiTuongDinhKhoan() {
		return this.DoiTuongDinhKhoan;
	}

	public void setDoiTuongDinhKhoan(String DoiTuongDinhKhoan) {
		this.DoiTuongDinhKhoan= DoiTuongDinhKhoan;
		
	}

	public String getDoituongdinhkhoanId() {
		return this.DoituongdinhkhoanId;
	}

	public void setDoituongdinhkhoanId(String DoituongdinhkhoanId) {
		this.DoituongdinhkhoanId = DoituongdinhkhoanId;
		
	}

	public String getMaTenDoiTuongDinhKhoan() {
		return this.MaTenDoiTuongDinhKhoan;
	}

	public void setMaTenDoiTuongDinhKhoan(String MaTenDoiTuongDinhKhoan) {
		this.MaTenDoiTuongDinhKhoan= MaTenDoiTuongDinhKhoan;
		
	}

	
	public String getHdId() {
		
		return this.hdId;
	}

	
	public void setHdId(String hdId) {
		this.hdId =hdId;
		
	}

	
	public String getLydonop() {
		return this.lydonop;
	}

	
	public void setLydonop(String lydonop) {
		this.lydonop = lydonop;
		
	}

	public String getNppIds() {
		
		return this.nppIds;
	}

	
	public void setNppIds(String nppIds ) {
		
		this.nppIds = nppIds ;
	}
	
	public String getHdIds() {
		
		return this.hdIds;
	}

	
	public void setHdIds(String hdIds ) {
		
		this.hdIds = hdIds ;
	}

	
	public String getKhIds() {
		
		return this.khIds;
	}

	
	public void setKhIds(String khIds) {
		this.khIds=khIds;
		
	}

	
	public ResultSet getKhRs() {
		
		return this.KhRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		this.KhRs=khRs;
		
	}

	public String getNhanvienGNIds()
	{
		return this.nhanvienGNIds;
	}
	public void setNhanvienGNIds(String nhanvienGNIds)
	{
		this.nhanvienGNIds = nhanvienGNIds;
	}
	public ResultSet getNhanvienGNRs()
	{
		return this.NhanvienGNRs;
	}
	public void setNhanvienGNRs(ResultSet nhanvienGNRs)
	{
		this.NhanvienGNRs= nhanvienGNRs;
	}
	
	public String getNhanvienBHIds()
	{
		return this.nhanvienBHIds;
	}
	public void setNhanvienBHIds(String nhanvienBHIds)
	{
		this.nhanvienBHIds= nhanvienBHIds;
	}
	public ResultSet getNhanvienBHRs()
	{
		return this.NhanvienBHRs;
	}
	public void setNhanvienBHRs(ResultSet nhanvienBHRs)
	{
		this.NhanvienBHRs= nhanvienBHRs;
	}

	public String getChonthutuHD()
	{
		return this.chonthutuHD;
	}

	public void setChonthutuHD(String chonthutuHD)
	{
		this.chonthutuHD= chonthutuHD;
		
	}

	public String getNoptienIds() 
	{
		return this.noptienIds;
	}

	public void setNoptienIds(String noptienIds) 
	{
		this.noptienIds = noptienIds;
	}

	public ResultSet getNoptienRs() 
	{
		return this.NoptienRs;
	}

	public void setNoptienRs(ResultSet noptienRs) 
	{
		this.NoptienRs = noptienRs;
		
	}
	


	public Hashtable<String,Double> getNo_KhachHangList() 
	{
		return this.No_KhachHangList;
	}


	public void setNo_KhachHangList(Hashtable<String,Double> no_KhachHangList) 
	{
		this.No_KhachHangList = no_KhachHangList;
		
	}
	
	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}
	public String getTungay() 
	{
		return tungay;
	}
	
	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}
	public String getDenngay() 
	{
		return denngay;
	}

	
	public String[] getHoadonId_old() 
	{
		return this.hdId_old;
	}

	
	public void setHoadonId_old(String[] hoadonId_old) {
		this.hdId_old = hoadonId_old;
		
	}

	public String getHinhthucTT() {
		return this.hinhthucTT;
	}

	public void setHinhthucTT(String hinhthucTT) {
		this.hinhthucTT = hinhthucTT;
		
	}

	
	public String getKhId() {
		
		return this.Khid;
	}

	
	public void setKhId(String khid) {
		
		this.Khid=khid;
	}

	
	public ResultSet getKhList() {
		
		return this.Khlist;
	}

	
	public void setKhList(ResultSet Khlist) {
		
		this.Khlist=Khlist;
	}

	
	public String get_add_madt() {
		
		return this.add_madt;
	}

	
	public void set_add_madt(String add_madt) {
		
		this.add_madt = add_madt;
	}

	
	public String get_add_makh() {
		
		return this.add_makh;
	}

	
	public void set_add_makh(String add_makh) {
		
		this.add_makh = add_makh;
	}

	
	public String get_add_idhd() {
		
		return this.add_idhd;
	}

	
	public void set_add_idhd(String add_idhd) {
		
		this.add_idhd = add_idhd;
	}

	
	public String get_add_sohoadon() {
		
		return this.add_sohoadon;
	}

	
	public void set_add_sohoadon(String add_sohoadon) {
		
		this.add_sohoadon= add_sohoadon;
	}

	
	public String get_add_ngayhd() {
		
		return this.add_ngayhd;
	}

	
	public void set_add_ngayhd(String add_ngayhd) {
		
		this.add_ngayhd = add_ngayhd;
	}

	
	public String get_add_tongsotien() {
		
		return this.add_tongsotien;
	}

	
	public void set_add_tongsotien(String add_tongsotien) {
		
		this.add_tongsotien = add_tongsotien;
	}

	
	public String get_add_thanhtoan() {
		
		return this.add_thanhtoan;
	}

	
	public void set_add_thanhtoan(String add_thanhtoan) {
		
		this.add_thanhtoan = add_thanhtoan;
	}

	
	public String get_add_conlai() {
		
		return this.add_conlai;
	}

	
	public void set_add_conlai(String add_conlai) {
		
		this.add_conlai=add_conlai;
	}

	
	public String get_add_check() {
		
		return this.add_check;
	}

	
	public void set_add_check(String add_check) {
		this.add_check = add_check;
		
	}

	
	public void createRsLoc() 
	{
		this.getNppInfo();
		
		// Check dang nhap co phai DOI TAC hay khong
		String query = "SELECT LOAINPP FROM NHAPHANPHOI WHERE PK_SEQ='"+ this.nppId +"' ";
		
		ResultSet rss= db.get(query);
		try
		{
			if(rss!=null)
			{
				while (rss.next())
				{
					this.checkDN = rss.getInt("LOAINPP");
				}
			}
		}catch(Exception e){e.printStackTrace();}
		
		
		 query = 	"SELECT HD.PK_SEQ, HD.NGAYXUATHD ,cast(HD.PK_SEQ as nvarchar(20)) + '-' +HD.NGAYXUATHD as TEN  \n" +
		 			"FROM 	ERP_HOADONNPP HD \n" +
		 			"WHERE 	HD.TRANGTHAI in (2,4) AND HD.NPP_FK='"+ this.nppId +"' \n" +
		 			"UNION ALL \n"+
			 		"SELECT HD1.PK_SEQ, HD1.NGAYXUATHD ,cast(HD1.PK_SEQ as nvarchar(20)) + '-' +HD1.NGAYXUATHD as TEN  \n" +
		 			"FROM 	HOADON HD1 \n" +
		 			"WHERE 	HD1.TRANGTHAI in (2,4) AND HD1.NPP_FK='"+ this.nppId +"' AND ISNULL(HD1.LOAIHOADON,0) = 0 \n" ; // CHỈ LẤY HÓA ĐƠN TC , KO LAY HOA DON KM
	
		this.hdTCRs = db.get(query);
		
		String sql = "SELECT pk_seq, isnull(maFAST,'') + '-' + ten as nppTen FROM NHAPHANPHOI WHERE trangthai = '1' and loainpp='4' AND TRUCTHUOC_FK ='"+ this.nppId +"' ";
		this.nppRs = db.get(sql);

		sql = "SELECT pk_seq, isnull(maFAST,'') + '-' + ten as khTen FROM KHACHHANG WHERE trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
		this.KhRs = db.get(sql);
		
		sql = "SELECT pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten FROM NHANVIENGIAONHAN WHERE trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
		this.NhanvienGNRs = db.get(sql);
			
		sql = " SELECT 	B.PK_SEQ , CAST(B.pk_seq as nvarchar(20)) + ' - ' + B.ten as Ten " +
			  " FROM 	DAIDIENKINHDOANH_NPP A INNER JOIN DAIDIENKINHDOANH B ON A.DDKD_FK= B.PK_SEQ " +
			  " WHERE  	A.npp_fk ='"+ this.nppId +"' AND B.TRANGTHAI = '1' ";
		this.NhanvienBHRs = db.get(sql);
					
		sql = "SELECT 	pk_seq, isnull(maFAST,'') + '-' + ten as TEN from KHACHHANG where trangthai = '1' and npp_fk = '"+ this.nppId +"' ";
		this.Khlist = db.get(sql);
		
		
	// Lấy những phiếu nộp tiền của NVGN/ NVBH/ KH / DT
			if(this.nhanvienBHIds.trim().length() > 0 || this.nhanvienGNIds.trim().length() > 0 || this.khIds.trim().length() > 0 || this.nppIds.trim().length() > 0)
			{
				if(this.nhanvienGNIds.trim().length() > 0 )           // NHÂN VIÊN GIAO NHẬN
				{
					sql = " SELECT 	nt.pk_seq, (cast (nt.pk_seq as nvarchar(20)) + '-' + nt.ngaynop) as ngay \n" +
						  " FROM 	NOPTIEN nt \n" +
						  " WHERE 	nt.trangthai != 2 and nt.nvgn_fk in ( "+ this.nhanvienGNIds +" ) \n" +
						  "       	and nt.sotien - isnull((SELECT SUM(SOTIENDATT) \n" +
						  "                     		  	FROM ERP_THUTIENNPP_NOPTIEN a inner join ERP_THUTIENNPP b on a.THUTIENNPP_FK = b.PK_SEQ  \n" +
						  "                     		  	WHERE b.TRANGTHAI != 2 AND a.NOPTIEN_FK = nt.PK_SEQ \n" +
			              "                    		      	GROUP BY NOPTIEN_FK) , 0) > 0  \n";
				  if(this.id.trim().length() > 0)
				  {
				   sql += "       	and nt.pk_seq not in (select NOPTIEN_FK from ERP_THUTIENNPP_NOPTIEN where THUTIENNPP_FK = "+ this.id +"  ) \n" +
				   		  " UNION ALL  \n" +
					  	  " SELECT 	a.NOPTIEN_FK as pk_seq, (cast ( a.NOPTIEN_FK as nvarchar(20)) + '-' + b.ngaynop) as ngay \n" +
					  	  " FROM 	ERP_THUTIENNPP_NOPTIEN a inner join NOPTIEN b on a.NOPTIEN_FK= b.PK_SEQ \n" +
					   	  " WHERE 	a.thutiennpp_fk = "+ this.id +" \n"	;
				  }
				}
				else if (this.nhanvienBHIds.trim().length() > 0 )     // TRÌNH DƯỢC VIÊN
				{
					sql = " SELECT 	nt.pk_seq, (cast (nt.pk_seq as nvarchar(20)) + '-' + nt.ngaynop) as ngay \n" +
						  " FROM 	NOPTIEN nt \n" +
						  " WHERE 	nt.trangthai != 2 and nt.nvbh_fk in ( "+ this.nhanvienBHIds +" ) \n"+
						  "       	and nt.sotien - isnull((SELECT 	SUM(SOTIENDATT) \n" +
						  "                     		  	FROM 	ERP_THUTIENNPP_NOPTIEN a inner join ERP_THUTIENNPP b on a.THUTIENNPP_FK = b.PK_SEQ  \n" +
						  "                     		  	WHERE 	b.TRANGTHAI != 2 AND a.NOPTIEN_FK = nt.PK_SEQ \n" +
				          "                        		  	GROUP BY NOPTIEN_FK) , 0) > 0  \n";
				   
			   if(this.id.trim().length() > 0) 
				{
				  sql += "    and nt.pk_seq not in (select NOPTIEN_FK from ERP_THUTIENNPP_NOPTIEN where THUTIENNPP_FK = "+ this.id +"  ) \n" +
				  		 " UNION ALL \n" +
				  		 
				  		 " SELECT 	a.NOPTIEN_FK as pk_seq, (cast ( a.NOPTIEN_FK as nvarchar(20)) + '-' + b.ngaynop) as ngay \n" +
				  		 " FROM 	ERP_THUTIENNPP_NOPTIEN a inner join NOPTIEN b on a.NOPTIEN_FK= b.PK_SEQ \n" +
				  		 " WHERE 	a.thutiennpp_fk = "+ this.id +" \n"	;
				}
					
				}
				else if (this.khIds.trim().length() > 0 )              // KHÁCH HÀNG
				{
					sql = " SELECT nt.pk_seq, (cast (nt.pk_seq as nvarchar(20)) + '-' + nt.ngaynop) as ngay \n" +
						  " FROM   NOPTIEN nt \n" +
					      " WHERE  nt.trangthai != 2 and nt.khachhang_fk in ( "+ this.khIds +" ) \n"+
						  "        and nt.sotien - isnull((	SELECT SUM(SOTIENDATT) \n" +
						  "                     		  	FROM ERP_THUTIENNPP_NOPTIEN a inner join ERP_THUTIENNPP b on a.THUTIENNPP_FK = b.PK_SEQ  \n" +
						  "                     		  	WHERE b.TRANGTHAI != 2 AND a.NOPTIEN_FK = nt.PK_SEQ \n" +
				          "                        		  	GROUP BY NOPTIEN_FK) , 0) > 0  \n";
					
				  if(this.id.trim().length() > 0)
					{
					  sql += "    and nt.pk_seq not in (select NOPTIEN_FK from ERP_THUTIENNPP_NOPTIEN where THUTIENNPP_FK = "+ this.id +"  ) \n" +
					  		 " UNION ALL \n" +
					  		 " SELECT 	a.NOPTIEN_FK as pk_seq, (cast ( a.NOPTIEN_FK as nvarchar(20)) + '-' + b.ngaynop) as ngay \n" +
					  		 " FROM 	ERP_THUTIENNPP_NOPTIEN a inner join NOPTIEN b on a.NOPTIEN_FK= b.PK_SEQ \n" +
					  		 " WHERE 	a.thutiennpp_fk = "+ this.id +" \n"	;
					}
				}
				else if (this.nppIds.trim().length() > 0 )             // ĐỐI TÁC
				{
					sql = " SELECT 	nt.pk_seq, (cast (nt.pk_seq as nvarchar(20)) + '-' + nt.ngaynop) as ngay \n" +
						  " FROM 	NOPTIEN nt \n" +
					      " WHERE 	nt.trangthai != 2 and nt.npp_dat_fk in ( "+ this.nppIds +" ) \n"+
						  "       	and nt.sotien - isnull((SELECT 	SUM(SOTIENDATT) \n" +
						  "                     		  	FROM 	ERP_THUTIENNPP_NOPTIEN a inner join ERP_THUTIENNPP b on a.THUTIENNPP_FK = b.PK_SEQ  \n" +
						  "                     		  	WHERE 	b.TRANGTHAI != 2 AND a.NOPTIEN_FK = nt.PK_SEQ \n" +
				          "                        		  	GROUP BY NOPTIEN_FK) , 0) > 0  \n";
					
				  if(this.id.trim().length() > 0)
					{
					  sql += "    and nt.pk_seq not in (select NOPTIEN_FK from ERP_THUTIENNPP_NOPTIEN where THUTIENNPP_FK = "+ this.id +"  ) \n" +
					  		 " UNION ALL \n" +
					  		 " SELECT 	a.NOPTIEN_FK as pk_seq, (cast ( a.NOPTIEN_FK as nvarchar(20)) + '-' + b.ngaynop) as ngay \n" +
					  		 " FROM 	ERP_THUTIENNPP_NOPTIEN a inner join NOPTIEN b on a.NOPTIEN_FK= b.PK_SEQ \n" +
					  		 " WHERE 	a.thutiennpp_fk = "+ this.id +" \n"	;
					}
				}
			}
			
			System.out.println("CAU LAY PHIEU NOP TIEN: \n" + sql);
			this.NoptienRs = db.get(sql);
				
			
			
			// Lấy số tiền đã nộp của các phiếu nộp được chọn
				//---------BEGIN
				NumberFormat formatter = new DecimalFormat("#,###,###");
				if(this.noptienIds.trim().length() > 0)
				{
					sql = " SELECT sum(nt.SOTIEN)  as SOTIENNOP , \n" +
						  "        isnull((select SUM(SOTIENDATT) \n" +
						  " FROM   ERP_THUTIENNPP_NOPTIEN a inner join ERP_THUTIENNPP b on a.THUTIENNPP_FK = b.PK_SEQ  \n" +
						  "                where b.TRANGTHAI != 2 AND a.NOPTIEN_FK in ("+ this.noptienIds +")  \n" ;
							  if(this.id.trim().length() > 0)
							  {
								  sql += " and b.PK_SEQ != "+ this.id +" ";
							  }
					sql +=	  "                ) , 0) " +
							  "        as SOTIENDATT " +
							  " FROM 	NOPTIEN nt \n" +
							  " WHERE 	nt.pk_seq in ("+ this.noptienIds +") ";
					
					System.out.println("CAU LAY SO TIEN NOP  "+sql);
					ResultSet rsLayST = db.get(sql);
				
					if(rsLayST != null)
					{
						try
						{
							while(rsLayST.next())
							{
								this.sotiendanop = formatter.format(rsLayST.getDouble("SOTIENNOP") - rsLayST.getDouble("SOTIENDATT"));
							}
							rsLayST.close();
						}catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				}
	           //----------END
	}
	
}
