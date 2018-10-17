package geso.traphaco.erp.beans.xoakhachhangtt.imp;

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
import geso.traphaco.erp.beans.xoakhachhangtt.IErpXoakhachhangtt;
import geso.traphaco.erp.beans.xoakhachhangtt.IHoadon;

public class ErpXoakhachhangtt implements IErpXoakhachhangtt 
{
	String userId;
	String id;
	String ngaychungtu, nppdangnhap;
	String ngayghiso;
	
	String nppId;
	ResultSet nppRs;
	
	String htttId;
	ResultSet htttRs;
	
	String ndId;
	ResultSet ndRs;
	
	String nganhangId;
	ResultSet nganhangRs;
	String chinhanhId;
	ResultSet chinhanhRs;
	
	String sotaikhoan;
	String noidungtt;
	String sotientt;
	
	String ctttId;
	ResultSet ctttRs;
	
	String hoadonId;
	List<IHoadon> hoadonList;
	
	String ctttIds;
	List<IHoadon> ctttList;
	
	String loaixnId; //loại xóa nợ: Xóa nợ KH trả trước/ Xóa tạm ứng
	String DoiTuongTamUng="";
	String nccId="";
	ResultSet nccRs;
	
	String nvtuId="";
	ResultSet nvtuRs;
	
	String tongthanhtoan;
	String chenhlech;
	
	String tongthanhtoanVND;
	String chenhlechVND;
	String sotienttVND;
	
	String msg;
	dbutils db;
	ResultSet tienteRs;
	String tienteId;
	
	String tigia;
	String tungay;
	String denngay;
	
	String tungaytk;
	String denngaytk;
	String maphieutk;
	String khachhangtk;
	String kenhbanhangtk;
	String nhomkhachhangtk;
	String sotientk;
	String trangthaitk;
	
	String congtyId;
	
	String loaikh;
	
	String khTen;
	
	String isNPP;
	
	String nppIdgoc;
	
	public ErpXoakhachhangtt(String tungay, String denngay, String maphieu, String khachhang, String kenhbanhang, String nhomkhachhang, String sotien, String trangthai)
	{
		this.id = "";
		this.tungaytk = tungay;
		this.denngaytk = denngay;
		this.maphieutk = maphieu;
		this.khachhangtk = khachhang;
		this.kenhbanhangtk = kenhbanhang;
		this.nhomkhachhangtk = nhomkhachhang;
		this.sotientk = sotien;
		this.trangthaitk = trangthai;
		
		this.ngaychungtu = "";
		this.ngayghiso = "";
		this.nppId = "";
		this.htttId = "";
		this.ndId = "";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.sotaikhoan = "";
		this.noidungtt = "";
		this.sotientt = "";
		this.tongthanhtoan = "";
		this.chenhlech = "";
		this.hoadonId = "";
		this.ctttId = "";
		this.ctttIds = "";
		
		this.loaixnId="0";
		this.DoiTuongTamUng="0";
		this.nccId="";
		this.nvtuId="";
		this.tienteId = "100000";
		
		this.msg = "";
		this.hoadonList = new ArrayList<IHoadon>();
		this.ctttList = new ArrayList<IHoadon>();
		
		this.db = new dbutils();
		
		this.tigia = "";
		
		this.sotienttVND = "";
		this.tongthanhtoanVND = "";
		this.chenhlechVND = "";
		
		this.tungay = "";
		this.denngay = "";
		this.loaikh = "";
		this.khTen = "";
		
		this.isNPP = "";
	}
	
	public ErpXoakhachhangtt(String id,String tungay, String denngay, String maphieu, String khachhang, String kenhbanhang, String nhomkhachhang, String sotien, String trangthai)
	{
		this.id = id;
		this.tungay = "";
		this.denngay = "";
		this.ngaychungtu = "";
		this.ngayghiso = "";
		this.nppId = "";
		this.htttId = "";
		this.ndId = "";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.sotaikhoan = "";
		this.noidungtt = "";
		this.sotientt = "";
		this.tongthanhtoan = "";
		this.chenhlech = "";
		this.tienteId = "";
		
		this.hoadonId = "";
		this.ctttId = "";
		this.ctttIds = "";
		
		this.loaixnId="";
		this.DoiTuongTamUng="";
		
		this.msg = "";
		this.hoadonList = new ArrayList<IHoadon>();
		this.ctttList = new ArrayList<IHoadon>();
		
		this.db = new dbutils();
		
		this.tigia = "";
		
		this.sotienttVND = "";
		this.tongthanhtoanVND = "";
		this.chenhlechVND = "";
		
		this.tungaytk = tungay;
		this.denngaytk = denngay;
		this.maphieutk = maphieu;
		this.khachhangtk = khachhang;
		this.kenhbanhangtk = kenhbanhang;
		this.nhomkhachhangtk = nhomkhachhang;
		this.sotientk = sotien;
		this.trangthaitk = trangthai;
		
		this.khTen = "";
		this.isNPP = "";
	}
	
	public void setDoiTuongTamUng(String doiTuongTamUng) {
		DoiTuongTamUng = doiTuongTamUng;
	}
	public String getDoiTuongTamUng() {
		return DoiTuongTamUng;
	}
	
	public void setNccId(String nccId) {
		this.nccId = nccId;
	}
	
	public String getNccId() {
		return nccId;
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
		return nvtuId;
	}
	public void setNvtuId(String nvtuId) {
		this.nvtuId = nvtuId;
	}
	
	public ResultSet getNvtuRs() 
	{
		return nvtuRs;
	}
	public void setNvtuRs(ResultSet nvtuRs) 
	{
		this.nvtuRs = nvtuRs;
	}
	
	public String getLoaixnId() {
		return loaixnId;
	}
	public void setLoaixnId(String loaixnId) {
		this.loaixnId = loaixnId;
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

	public String getSotaikhoan() 
	{
		return this.sotaikhoan;
	}

	public void setSotaikhoan(String sotk)
	{
		this.sotaikhoan = sotk;
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

	public String getHoadonIds() 
	{
		return this.hoadonId;
	}

	public void setHoadonIds(String hdIds) 
	{
		this.hoadonId = hdIds;
	}

	public List<IHoadon> getHoadonRs() 
	{
		return this.hoadonList;
	}

	public void setHoadonRs(List<IHoadon> hoadonRs)
	{
		this.hoadonList = hoadonRs;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public String getTongthanhtoan() 
	{
		return this.tongthanhtoan;
	}

	public void setTongthanhtoan(String tongthanhtoan) 
	{
		this.tongthanhtoan = tongthanhtoan;
	}

	public String getChenhlech() 
	{
		return this.chenhlech;
	}

	public void setChenhlech(String chenhlech) 
	{
		this.chenhlech = chenhlech;
	}

	public ResultSet getTienteRs() {
		return this.tienteRs;
	}
	
	public void setTienteRs(ResultSet tienteRs) {
		this.tienteRs = tienteRs;
	}

	public String getTienteId() {
		return this.tienteId;
	}

	public void setTienteId(String ttId) {
		this.tienteId = ttId;
	}
	
	public boolean createTTHD() 
	{
		if(this.ctttList.size() <= 0)
		{
			this.msg = "Vui lòng chọn chứng từ trả trước";
			return false;
		}
		
		if(this.sotientt.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số tiền thanh toán";
			return false;
		}
		
		if(Double.parseDouble(this.chenhlech) != 0)
		{
			this.msg = "Tổng tiền ứng trước và Tổng tiền thanh toán phải bằng nhau. Vui lòng kiểm tra lại.";
			return false;
		}
		
		//Tinh lai tong tien
		double tongthanhtoan = 0;
		for(int i = 0; i < this.hoadonList.size(); i++)
		{
			IHoadon hd = this.hoadonList.get(i);
			if(hd.getThanhtoan().length() > 0)
				tongthanhtoan += Double.parseDouble(hd.getThanhtoan().replaceAll(",", ""));
		}
		
		if(tongthanhtoan <= 0)
		{
			this.msg = "Vui lòng chọn hóa đơn để thanh toán";
			return false;
		}
		
		try 
		{
			String ngaytao = getDateTime();
			
			db.getConnection().setAutoCommit(false);
			String query="";
			//String tientt = this.sotientt;
			if(this.nppId.trim().length() >0) // XÓA NỢ KHÁCH HÀNG TRẢ TRƯỚC
			{
				if(this.tienteId.equals("100000"))
				{
					this.sotienttVND = "0";
					this.tongthanhtoanVND = "0";
					this.chenhlechVND = "0";
				}
				 query = "Insert ERP_XOAKHTRATRUOC(TIENTE_FK, TONGTHANHTOAN, CHENHLECH, NGAYCHUNGTU, NGAYGHISO, TRANGTHAI, LOAIXOATRATRUOC, " +
				 		 "KHACHHANG_FK, TONGTIENTRATRUOC, TONGTIENHOADON, GHICHU, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, TONGTIENTRATRUOCVND, TONGTHANHTOANVND, CHENHLECHVND , isNPP, CONGTY_FK ) " +
						 "values(" + this.tienteId + ", " + this.tongthanhtoan + ", " + this.chenhlech + ", '" + this.ngaychungtu + "', '" + this.ngayghiso + "', '0', " +
						 "'" + this.loaixnId + "',  '" + this.nppId + "', '" + this.sotientt.replaceAll(",", "") + "', '" + tongthanhtoan + "', " +
						 "N'" + this.noidungtt + "', '"  + ngaytao + "', '" + this.userId + "', '" + ngaytao + "', '" + this.userId + "', " + this.sotienttVND + ", " + this.tongthanhtoanVND + ", " + this.chenhlechVND + " , 1 , "+this.congtyId+" )";
				
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_XOAKHTRATRUOC: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.nvtuId.trim().length() >0) // XÓA TẠM ỨNG - NHÂN VIÊN
			{

				 query = "Insert ERP_XOAKHTRATRUOC(TIENTE_FK, TONGTHANHTOAN, CHENHLECH, NGAYCHUNGTU, NGAYGHISO, TRANGTHAI,LOAIXOATRATRUOC , " +
				 		 "NHANVIEN_FK, TONGTIENTRATRUOC, TONGTIENHOADON, GHICHU, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK) " +
				 		 "values(" + this.tienteId + "," + this.tongthanhtoan + ", " + this.chenhlech + ", '" + this.ngaychungtu + "', '" + this.ngayghiso + "', " +
				 		 "'0','" + this.loaixnId + "', '" + this.nvtuId + "', '" + this.sotientt.replaceAll(",", "") + "', '" + tongthanhtoan + "', " +
				 		 "N'" + this.noidungtt + "', '"  + ngaytao + "', '" + this.userId + "', '" + ngaytao + "', '" + this.userId + "', "+this.congtyId+")";
	
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_XOAKHTRATRUOC: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.nccId.trim().length() >0) // XÓA TẠM ỨNG - NHÀ CUNG CẤP
			{
				 query = "Insert ERP_XOAKHTRATRUOC(TIENTE_FK, TONGTHANHTOAN, CHENHLECH, NGAYCHUNGTU, NGAYGHISO, TRANGTHAI,LOAIXOATRATRUOC, " +
				 		 "NCC_FK, TONGTIENTRATRUOC, TONGTIENHOADON, GHICHU, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK) " +
				 		 "values(" + this.tienteId + ", " + this.tongthanhtoan + ", " + this.chenhlech + ",'" + this.ngaychungtu + "', '" + this.ngayghiso + "', " +
				 		 "'0','" + this.loaixnId + "', '" + this.nccId + "', '" + this.sotientt.replaceAll(",", "") + "', '" + tongthanhtoan + "', " +
				 		 "N'" + this.noidungtt + "', '"  + ngaytao + "', '" + this.userId + "', '" + ngaytao + "', '" + this.userId + "', "+this.congtyId+")";
	
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_XOAKHTRATRUOC: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			}
			String tthdCurrent = "";
			query = "select IDENT_CURRENT('ERP_XOAKHTRATRUOC') as tthdId";
			
			ResultSet rsTthd = db.get(query);						
			if(rsTthd.next())
			{
				tthdCurrent = rsTthd.getString("tthdId");
				rsTthd.close();
			}
			
			
			for(int i = 0; i < this.ctttList.size(); i++)
			{
				IHoadon hoadon = this.ctttList.get(i);

				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
			
				String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				String conlai = hoadon.getConlai().replaceAll(",", "");
				
				if(thanhtoan.length() != 0)
				{
					if(Float.parseFloat(thanhtoan) != 0)
					{
						query = "Insert ERP_XOAKHTRATRUOC_CTTT( xoakhtratruoc_fk, CTTT_FK, TIENTHANHTOAN, TIENCHUNGTU, CONLAI, TIGIA, loaict ) " +
								"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim() + "', '" + avat.trim() + "', '" + conlai.trim() + "', '"+ hoadon.getTigiaHD() +"', '"+hoadon.getLoaict()+"')";
						System.out.println(query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_XOAKHTRATRUOC_CTTT: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			for(int i = 0; i < this.hoadonList.size(); i++)
			{
				IHoadon hoadon = this.hoadonList.get(i);

				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
				String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				String conlai = hoadon.getConlai().replaceAll(",", "");
				
				if(thanhtoan.length() != 0)
				{
					if(Float.parseFloat(thanhtoan) != 0)
					{
						if(hoadon.getLoaihd().equals("7")) // HÓA ĐƠN HÀNG TRẢ VỀ
						{
							thanhtoan = Float.parseFloat(thanhtoan)*(-1)+"";
							avat = Float.parseFloat(avat)*(-1)+"";
						}
						
						query = "Insert ERP_XOAKHTRATRUOC_HOADON(xoakhtratruoc_fk, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI, LOAIHD, TIGIA, isNPP ) " +
								"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim() + "', '" + avat + "', '" + conlai.trim() + "' , '"+ hoadon.getLoaihd() +"', '"+ hoadon.getTigiaHD() +"', "+(this.isNPP ==""?null:this.isNPP )+")";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_XOAKHTRATRUOC_HOADON: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}										
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	public boolean updateTTHD() 
	{
		if(this.ctttList.size() <= 0)
		{
			this.msg = "Vui lòng chọn chứng từ trả trước";
			return false;
		}
		
		if(this.sotientt.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số tiền thanh toán";
			return false;
		}
		
		if(Double.parseDouble(this.chenhlech) != 0)
		{
			this.msg = "Tổng tiền ứng trước và Tổng tiền thanh toán phải bằng nhau. Vui lòng kiểm tra lại.";
			return false;
		}
		
		//Tinh lai tong tien
		double tongthanhtoan = 0;
		for(int i = 0; i < this.hoadonList.size(); i++)
		{
			IHoadon hd = this.hoadonList.get(i);
			if(hd.getThanhtoan().length() > 0)
				tongthanhtoan += Double.parseDouble(hd.getThanhtoan().replaceAll(",", ""));
		}
		
		if(tongthanhtoan <= 0)
		{
			this.msg = "Vui lòng chọn hóa đơn để thanh toán";
			return false;
		}
		
		try 
		{
			String ngaysua = getDateTime();
			
			db.getConnection().setAutoCommit(false);
			String query="";
			if(this.loaixnId.equals("0"))//Xóa nợ khách hàng trả trươc
			{
				if(this.tienteId.equals("100000"))
				{
					this.tongthanhtoanVND = "0";
					this.sotienttVND = "0";
					this.chenhlechVND = "0";
				}
				
				 query = "update ERP_XOAKHTRATRUOC set TIENTE_FK = " + this.tienteId + ", TONGTHANHTOAN = " + this.tongthanhtoan + ", " +
				 		 " CHENHLECH = " + this.chenhlech + ", NGAYCHUNGTU = '" + this.ngaychungtu + "', NGAYGHISO = '" + this.ngayghiso + "', " +
				 		 " KHACHHANG_FK = '" + this.nppId + "', GHICHU = N'" + this.noidungtt + "', " +
				 		 " TONGTIENTRATRUOC = '" + this.sotientt.replaceAll(",", "") + "', TONGTIENHOADON = '" + tongthanhtoan + "',  " +
				 		 " NGAYSUA = '" + ngaysua + "', NGUOISUA = '" + this.userId + "'," +
				 		 " TONGTIENTRATRUOCVND = " + this.sotienttVND + ", TONGTHANHTOANVND = " + this.tongthanhtoanVND + ", CHENHLECHVND = " + this.chenhlechVND + ", isNPP = 1 " +
				 		 " where PK_SEQ = '"  + this.id + "' and trangthai=0";

				if(db.updateReturnInt(query)!=1)
				{
				this.msg = "Khong the cap nhat ERP_XOAKHTHANHTOAN: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
				}
			}
			else
			{
				if(this.nvtuId.trim().length() > 0)
				{
				 query = "update ERP_XOAKHTRATRUOC set TIENTE_FK = " + this.tienteId + ", TONGTHANHTOAN = " + this.tongthanhtoan + ", CHENHLECH = " + this.chenhlech + ", " +
				 		 "NGAYCHUNGTU = '" + this.ngaychungtu + "', NGAYGHISO = '" + this.ngayghiso + "', " +
				 		 "NHANVIEN_FK = '" + this.nvtuId + "', GHICHU = N'" + this.noidungtt + "', " +
				 		 "TONGTIENTRATRUOC = '" + this.sotientt.replaceAll(",", "") + "', TONGTIENHOADON = '" + tongthanhtoan + "',  " +
				 		 "NGAYSUA = '" + ngaysua + "', NGUOISUA = '" + this.userId + "' where PK_SEQ = '"  + this.id + "' and trangthai=0";

					if(db.updateReturnInt(query)!=1)
					{
					this.msg = "Khong the cap nhat ERP_XOANVTHANHTOAN: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
					}
				}
				else 
				{
				 query = "update ERP_XOAKHTRATRUOC set  TIENTE_FK = " + this.tienteId + ", TONGTHANHTOAN = " + this.tongthanhtoan + ", CHENHLECH = " + this.chenhlech + ", NGAYCHUNGTU = '" + this.ngaychungtu + "', NGAYGHISO = '" + this.ngayghiso + "', " +
						 "NCC_FK = '" + this.nccId + "', GHICHU = N'" + this.noidungtt + "', " +
						 "TONGTIENTRATRUOC = '" + this.sotientt.replaceAll(",", "") + "', TONGTIENHOADON = '" + tongthanhtoan + "',  " +
						 "NGAYSUA = '" + ngaysua + "', NGUOISUA = '" + this.userId + "' where PK_SEQ = '"  + this.id + "' and trangthai=0";

					if(db.updateReturnInt(query)!=1)
					{
					this.msg = "Khong the cap nhat ERP_XOANCCTHANHTOAN: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
					}
				}
			}
			
		
			
			query = "delete ERP_XOAKHTRATRUOC_CTTT where XOAKHTRATRUOC_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_XOAKHTRATRUOC_CTTT: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_XOAKHTRATRUOC_HOADON where XOAKHTRATRUOC_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_XOAKHTRATRUOC_HOADON: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			
			for(int i = 0; i < this.ctttList.size(); i++)
			{
				IHoadon hoadon = this.ctttList.get(i);

				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
				String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				String conlai = hoadon.getConlai().replaceAll(",", "");
				
				if(thanhtoan.length() > 0)
				{
					if(Float.parseFloat(thanhtoan) > 0)
					{
						query = "Insert ERP_XOAKHTRATRUOC_CTTT(xoakhtratruoc_fk, CTTT_FK, TIENTHANHTOAN, TIENCHUNGTU, CONLAI, TIGIA, loaict) " +
								"values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim() + "', '" + avat.trim() + "', '" + conlai.trim() + "', '"+ hoadon.getTigiaHD() +"', '"+hoadon.getLoaict()+"')";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_XOAKHTRATRUOC_CTTT: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}
						
					}
				}
			}
			
			for(int i = 0; i < this.hoadonList.size(); i++)
			{
				IHoadon hoadon = this.hoadonList.get(i);

				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
				String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				String conlai = hoadon.getConlai().replaceAll(",", "");
				
				if(thanhtoan.length() != 0)
				{
					if(Float.parseFloat(thanhtoan) != 0)
					{
						if(hoadon.getLoaihd().equals("7")) // HÓA ĐƠN HÀNG TRẢ VỀ
						{
							thanhtoan = Float.parseFloat(thanhtoan)*(-1)+"";
							avat = Float.parseFloat(avat)*(-1)+"";
						}
						
						query = "Insert ERP_XOAKHTRATRUOC_HOADON(xoakhtratruoc_fk, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI, LOAIHD, TIGIA, isNPP) " +
								"values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim() + "', '" + avat + "', '" + conlai.trim() + "' , '"+ hoadon.getLoaihd() +"', '"+ hoadon.getTigiaHD() +"', "+(this.isNPP ==""?null:this.isNPP )+")";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_XOAKHTRATRUOC_HOADON: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}
						
					}
				}
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	public boolean iw(String userId)
	{
		try 
		{
			String ngaysua = getDateTime();
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_XOAKHTRATRUOC set TRANGTHAI = '1', NGUOISUA = '" + userId + "', NGAYSUA = '" + ngaysua + "' where PK_SEQ = '"  + this.id + "'";
			System.out.println("1.Cap nhat ERP_XOAKHTRATRUOC: " + query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the chot ERP_XOAKHTRATRUOC: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	public void init()
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = "select pk_seq, tongthanhtoan, chenhlech, ngaychungtu, ngayghiso, trangthai, loaixoatratruoc, \n" +
				       "       khachhang_fk, nhanvien_fk, ncc_fk, ghichu, TONGTIENTRATRUOC, TONGTIENHOADON, TIENTE_FK, \n" +
				       "       ISNULL(TONGTIENTRATRUOCVND,0) TONGTIENTRATRUOCVND , ISNULL(TONGTHANHTOANVND,0) TONGTHANHTOANVND, ISNULL(CHENHLECHVND,0) as CHENHLECHVND,  isnull(isNPP,0) isNPP \n" +
					   "from ERP_XOAKHTRATRUOC \n" +
					   "where pk_seq = '" + this.id + "' ";
		
		System.out.println(query);
		ResultSet rs = db.get(query);
		System.out.println(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.ngayghiso = rs.getString("ngayghiso");
					this.loaixnId= rs.getString("loaixoatratruoc");
					this.nppId = rs.getString("khachhang_fk");
					this.nvtuId= rs.getString("nhanvien_fk");
					this.nccId= rs.getString("ncc_fk");
					this.tienteId = rs.getString("TIENTE_FK");
					
					this.noidungtt = rs.getString("ghichu");
					this.sotientt = formatter.format(rs.getDouble("TONGTIENTRATRUOC"));
					this.tongthanhtoan = formatter.format(rs.getDouble("TONGTHANHTOAN"));
					this.chenhlech = formatter.format(rs.getDouble("CHENHLECH"));
					
					this.isNPP =  rs.getString("isNPP");
					
					if(!this.tienteId.equals("100000"))
					{
						this.sotienttVND = formatter.format(rs.getDouble("TONGTIENTRATRUOCVND"));
						this.tongthanhtoanVND = formatter.format(rs.getDouble("TONGTHANHTOANVND"));
						this.chenhlechVND = formatter.format(rs.getDouble("CHENHLECHVND"));
					}
					
					if(this.nppId == null) this.nppId =""; 
					if(this.nccId == null) this.nccId ="";
					if(this.nvtuId == null) this.nvtuId =""; 
					
														
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	
		if(this.nccId.trim().length() >0 ) this.DoiTuongTamUng="1";
		if(this.nvtuId.trim().length() >0 ) this.DoiTuongTamUng="0";
		
		this.createRs();
	}

	public void initDisplay()
	{
//		Utility util = new Utility();
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		NumberFormat formatterNT = new DecimalFormat("#,###,###.##");
		
		String query = 	" select pk_seq, ngaychungtu, tongthanhtoan, chenhlech, ngayghiso, trangthai, tiente_fk," +
						"        nhanvien_fk, ncc_fk, khachhang_fk, ghichu, TONGTIENTRATRUOC, TONGTIENHOADON , loaixoatratruoc, TIENTE_FK," +
						"        ISNULL(TONGTIENTRATRUOCVND,0) TONGTIENTRATRUOCVND , ISNULL(TONGTHANHTOANVND,0) TONGTHANHTOANVND, ISNULL(CHENHLECHVND,0) as CHENHLECHVND, isnull(isNPP,0) isnpp " +
						" from ERP_XOAKHTRATRUOC where pk_seq = '" + this.id + "'";
		System.out.println(query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.ngayghiso = rs.getString("ngayghiso");
					this.nppId = rs.getString("khachhang_fk");
					this.tienteId = rs.getString("TIENTE_FK");
					this.loaixnId=rs.getString("loaixoatratruoc");
					this.nvtuId= rs.getString("nhanvien_fk");
					this.nccId=rs.getString("ncc_fk");
					this.tienteId= rs.getString("tiente_fk");
					this.noidungtt = rs.getString("ghichu");
					this.sotientt = formatter.format(rs.getDouble("TONGTIENTRATRUOC"));
					this.tongthanhtoan = formatter.format(rs.getDouble("TONGTHANHTOAN"));
					this.chenhlech = formatter.format(rs.getDouble("CHENHLECH"));
					this.isNPP =  rs.getString("isNPP");
					
					if(!this.tienteId.equals("100000"))
					{
						this.sotienttVND = formatter.format(rs.getDouble("TONGTIENTRATRUOCVND"));
						this.tongthanhtoanVND = formatter.format(rs.getDouble("TONGTHANHTOANVND"));
						this.chenhlechVND = formatter.format(rs.getDouble("CHENHLECHVND"));
					}

				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		if(this.nppId == null) this.nppId =""; 
		if(this.nccId == null) this.nccId ="";
		if(this.nvtuId == null) this.nvtuId =""; 
		
		if(this.nccId.trim().length() >0 ) this.DoiTuongTamUng="1";
		if(this.nvtuId.trim().length() >0 ) this.DoiTuongTamUng="0";
		
		//PHAN QUYEN
//		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		createRs_Display();
	}
	
	public void createRs() 
	{
//		Utility util = new Utility();
		
		NumberFormat formatter = new DecimalFormat("#,###,###");
		NumberFormat formatterNT = new DecimalFormat("#,###,###.##");
		
		System.out.println("this.getLoainhanvien():"+this.getLoainhanvien());
		String sql1 =  "  select PK_SEQ, MA , A.TEN nppTen from NHAPHANPHOI a where trangthai = '1' and PK_SEQ!=1  \n";	
					 
		this.nppRs = db.get(sql1);
		
		this.nccRs= db.get(" select pk_seq, ma, ten from erp_nhacungcap where trangthai=1 and duyet = '1'");
		this.nvtuRs= db.get(" select pk_seq, ma, ten from erp_nhanvien where trangthai=1 ");
		
		this.tienteRs = db.get("select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE ");
		
		if(this.ngaychungtu.trim().length()>0)
			this.ngayghiso = this.ngaychungtu;
		
		// isNPP = 0 => KHÁCH HÀNG, isNPP = 1 => NHÀ PHÂN PHỐI, isNPP = 2 => NHÂN VIÊN 
		// Khach hang tra truoc	
		//1.CHỨNG TỪ TRẢ TRƯỚC
		if(this.nppId.trim().length() > 0 && this.ctttList.size() <= 0 )
		{
			String query = "";				
			
			if(this.id.length() > 0)
			{
				// loaict = 0 - thu tiền được dùng để xóa nợ
				// loaict = 1 - bút toán tổng hợp được
				
				// LẤY RA NHỮNG THU TIỀN ĐÃ ĐƯỢC DÙNG XÓA NỢ 
				query = 	"SELECT b.pk_seq, CAST(b.pk_seq as varchar(10)) as sochungtu, b.ngaychungtu, isnull(b.tigia,1) as tigia,  \n" +
						 	"       case when '"+this.tienteId+"' = '100000' then isnull(b.sotientt,0) - isnull(thanh_toan.tongthanhtoan, '0') \n" +
						 	"            else isnull(b.sotienttNT,0) - isnull(thanh_toan.tongthanhtoan, '0') end as tienAvat, \n" +
						 	"       isnull(b.sotientt,0) - (isnull(thanh_toan.tongthanhtoan, '0')* isnull(b.tigia,1) ) as tienAvatVND, \n" +
						 	"       a.tienthanhtoan as SOTIENTT, 0 as loaict \n" +
						 	
						 	"FROM 	ERP_XOAKHTRATRUOC_CTTT a \n" +
						 	"		inner join ERP_THUTIEN b on a.cttt_fk = b.pk_seq \n" +
						 	"		left join	\n" +
						 	"		( 	" +
						 	"         select a.cttt_fk, SUM(tienthanhtoan) tongthanhtoan from ( \n"+
						 	"			select 	a.cttt_fk, sum(a.tienthanhtoan) as tienthanhtoan \n" +
							"			from 	ERP_XOAKHTRATRUOC_CTTT a \n" +
							"			inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk = b.pk_seq  \n" +
							"			where 	b.trangthai != 2 and a.loaict = 0 and b.khachhang_fk = "+this.nppId+" and b.CONGTY_FK = "+this.congtyId+" \n";				
				query += 	" 			and b.pk_seq != '" + (this.id == "" ? "0": this.id) + "' \n";			
				query +=	"   		group by a.cttt_fk \n"+
				
					        "   		UNION ALL \n"+
					        
							"			select 	c.HOADON_FK cttt_fk, sum(c.SOTIENTT) as tienthanhtoan \n" +
							"			from 	ERP_THANHTOANHOADON_HOADON c \n" +
							"			inner join ERP_THANHTOANHOADON d on c.THANHTOANHD_FK = d.PK_SEQ  \n" +
							"			where 	d.trangthai not in (2,3) and c.LOAIHD = 7  and d.KHACHHANG_FK = "+this.nppId+" and d.CONGTY_FK = "+this.congtyId+" \n"+
							"   		group by c.HOADON_FK "+
							
							"			union all \n"+
							"			SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU  \n"+
							"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n"+
							"			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 3 AND BT.KH_CHUYENNO = "+this.nppId+" \n"+
							"			GROUP BY BT_KH.HOADON_FK \n"+
							"       ) a group by a.cttt_fk \n"+
							"		)thanh_toan on a.cttt_fk = thanh_toan.cttt_fk \n" +
							
							"WHERE a.XOAKHTRATRUOC_FK = '" + this.id + "' \n" +
				
				// LẤY RA NHỮNG XÓA NỢ KHÁCH HÀNG BÚT TOÁN TỔNG HỢP
							"UNION ALL \n"+
							
							" SELECT b.pk_seq, CAST(b.pk_seq as varchar(10)) as sochungtu, b.ngaychungtu ngaychungtu, 1 as tigia,  \n"+
						    "   isnull(b.CO,0) - isnull(thanh_toan.tienthanhtoan, '0') as tienAvat, \n"+
						    "   isnull(b.CO,0) - (isnull(thanh_toan.tienthanhtoan, '0') ) as tienAvatVND, \n"+
						    "   a.tienthanhtoan as SOTIENTT, 1 as loaict \n"+
						 	
							"FROM  ERP_XOAKHTRATRUOC_CTTT a " +
							"INNER JOIN \n"+							
							"( \n"+ 
							"	select	a.pk_seq, b.KHACHHANG_FK , CAST(a.pk_seq as varchar(10)) as sochungtu, NGAYBUTTOAN ngaychungtu, sum(isnull(CO,0)) as SOTIENTTNT, sum(CO) CO, 1 as tigia \n"+  
							"	from	ERP_BUTTOANTONGHOP a " +
							"	INNER JOIN ERP_BUTTOANTONGHOP_CHITIET b on a.PK_SEQ = b.BUTTOANTONGHOP_FK \n"+   
							"	where	trangthai = '1' and a.CONGTY_FK = "+this.congtyId+" \n"+			
							"			and khachhang_fk = '"+this.nppId+"' and b.CO > 0 and TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13111000' and CONGTY_FK = "+this.congtyId+") \n"+						
							"    and isnull(b.isNPP,0) = "+this.isNPP+" \n"+
							"	group by a.pk_seq, khachhang_fk, TAIKHOANKT_FK, ngaybuttoan  \n"+
							" )b ON a.cttt_fk = b.pk_seq   \n"+ 
							" left join \n"+  
							" ( \n"+  
							"	select 	a.cttt_fk, sum(a.tienthanhtoan) as tienthanhtoan \n"+ 
							"	from 	ERP_XOAKHTRATRUOC_CTTT a \n"+ 
							"	inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk = b.pk_seq \n"+  
							"	where 	b.trangthai != 2 and a.loaict = 1 and a.XOAKHTRATRUOC_FK != '" + this.id + "' \n"+
							"	and a.cttt_fk IN (	select cttt_fk from ERP_XOAKHTRATRUOC_CTTT \n" +
							"						where XOAKHTRATRUOC_FK = '" + this.id + "' and loaict = 1) and b.KHACHHANG_FK = "+this.nppId+" and isnull(b.isNPP,0) = "+this.isNPP+" \n" +
							"	group by a.cttt_fk \n"+
							" )thanh_toan on a.cttt_fk = thanh_toan.cttt_fk \n"+ 
							
							"where a.XOAKHTRATRUOC_FK = '" + this.id + "' and b.CO >0 and b.khachhang_fk = "+this.nppId+"  \n" +
							
							"union all \n";
			}
			
			// LẤY RA THU TIỀN TRẢ TRƯỚC
			
			query += 		
			
							"select chungtu.pk_seq, chungtu.sochungtu, chungtu.ngaychungtu, isnull(chungtu.tigia,1) as tigia,  \n" +
							"       case when '"+this.tienteId+"' = '100000' then isnull(chungtu.sotientt,0) - ISNULL(dathanhtoan.tienthanhtoan, 0) \n" +
							"       else isnull(chungtu.sotienttNT,0) - ISNULL(dathanhtoan.tienthanhtoan, 0) end as tienAvat,  \n" +
							"       isnull(chungtu.sotientt,0) - (ISNULL(dathanhtoan.tienthanhtoan, 0) * chungtu.tigia) as tienAvatVND, \n" +
						 	"       0 as SOTIENTT, 0 as loaict  \n" +
							
						 	"from \n" +
						 	"( \n" +						 
						 	
						 	// LẤY NHỮNG PHIẾU THU TIỀN - HÓA ĐƠN CHƯA TICK XÓA NỢ 						 	
							"	select 	a.pk_seq, CAST(a.pk_seq as varchar(10)) as sochungtu, a.ngaychungtu, sum(isnull(a.SOTIENTT,0)) as SOTIENTTNT, sum(isnull(a.SOTIENTT,0)) SOTIENTT, isnull(tigia,1) as tigia  \n" +						 	
							"	from 	erp_thutien a  \n" +
							"	where 	a.trangthai = '1' and (a.noidungtt_fk = '100001') and a.sotientt >= '0' and a.CONGTY_FK = "+this.congtyId+" \n" +
							"			and a.khachhang_fk = '" + this.nppId + "' and isnull(a.tiente_fk,100000) = " + this.tienteId + " \n"+
							"			and pk_seq not in (select cttt_fk from ERP_XOAKHTRATRUOC_CTTT where xoakhtratruoc_fk = '" + (this.id == "" ? "0": this.id)+ "' and loaict = 0 ) \n"+							
							"   group by a.pk_seq, a.ngaychungtu, a.tigia \n"+							
							" )chungtu \n" +
							"left join  \n" +
							"(  \n" +
							"  select cttt_fk, SUM (tienthanhtoan) tienthanhtoan  from ( \n"+
							"	select 	a.cttt_fk, sum(a.tienthanhtoan) as tienthanhtoan \n" +
							"	from 	ERP_XOAKHTRATRUOC_CTTT a \n" +
							"			inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk = b.pk_seq  \n" +
							"	where 	b.trangthai != 2 and a.loaict = 0 and b.khachhang_fk = "+this.nppId+" and b.CONGTY_FK = "+this.congtyId+" \n";				
				query += 		" 			and b.pk_seq != '" + (this.id == "" ? "0": this.id) + "' \n";			
				query +=		"   group by a.cttt_fk \n"+
				
					        "   UNION ALL \n"+
					        
							"	select 	c.HOADON_FK cttt_fk, sum(c.SOTIENTT) as tienthanhtoan \n" +
							"	from 	ERP_THANHTOANHOADON_HOADON c \n" +
							"			inner join ERP_THANHTOANHOADON d on c.THANHTOANHD_FK = d.PK_SEQ  \n" +
							"	where 	d.trangthai not in (2,3) and c.LOAIHD = 7  and d.KHACHHANG_FK = "+this.nppId+" and d.CONGTY_FK = "+this.congtyId+" \n"+
							"   group by c.HOADON_FK "+
							
							"	union all \n"+
							"	SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU  \n"+
							"	FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n"+
							"	WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 3 AND BT.KH_CHUYENNO = "+this.nppId+" \n"+
							"	GROUP BY BT_KH.HOADON_FK \n"+
							
					        "  )  a " +
					        " GROUP BY cttt_fk \n" +						
							")dathanhtoan on chungtu.pk_seq = dathanhtoan.cttt_fk \n" +		
					        
							"where chungtu.sotientt - ISNULL(dathanhtoan.tienthanhtoan, 0) > 0 \n";
			
			// LẤY RA NHỮNG XÓA NỢ KHÁCH HÀNG BÚT TOÁN TỔNG HỢP
			
			query+=
							" UNION ALL \n"+
							
							" select chungtu.pk_seq, chungtu.sochungtu, chungtu.ngaychungtu, isnull(chungtu.tigia,1) as tigia,  \n"+
						    "  		 case when '100000' = '100000' then isnull(chungtu.sotientt,0) - ISNULL(dathanhtoan.tienthanhtoan, 0) \n"+ 
						    "        else isnull(chungtu.sotienttNT,0) - ISNULL(dathanhtoan.tienthanhtoan, 0) end as tienAvat, \n"+  
						    "        isnull(chungtu.sotientt,0) - (ISNULL(dathanhtoan.tienthanhtoan, 0) * chungtu.tigia) as tienAvatVND, \n"+ 
						    "        0 as SOTIENTT, 1 as loaict \n"+  
						    
							" from \n"+ 
							"( \n"+ 
							"	select	a.pk_seq, CAST(a.pk_seq as varchar(10)) as sochungtu, NGAYBUTTOAN ngaychungtu, sum(isnull(CO,0)) as SOTIENTTNT, sum(CO) sotientt, 1 as tigia \n"+  
							"	from	ERP_BUTTOANTONGHOP a INNER JOIN ERP_BUTTOANTONGHOP_CHITIET b on a.PK_SEQ = b.BUTTOANTONGHOP_FK \n"+   
							"	where	trangthai = '1' and a.CONGTY_FK = "+this.congtyId+" \n"+			
							"			and khachhang_fk = '"+this.nppId+"' and b.CO > 0 and TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13111000' and CONGTY_FK = "+this.congtyId+")";			
			query += 		"	and pk_seq not in (select cttt_fk from ERP_XOAKHTRATRUOC_CTTT where xoakhtratruoc_fk = '" + (this.id == "" ? "0": this.id)+ "' and loaict = 1 ) \n";						
			query+=
							"	group by a.pk_seq, khachhang_fk, TAIKHOANKT_FK, ngaybuttoan \n"+
							" )chungtu \n"+ 
							" left join \n"+  
							" ( \n"+  
							"	select 	a.cttt_fk, sum(a.tienthanhtoan) as tienthanhtoan \n"+ 
							"	from 	ERP_XOAKHTRATRUOC_CTTT a \n"+ 
							"			inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk = b.pk_seq \n"+  
							"	where 	b.trangthai != 2 and a.loaict = 1 and b.khachhang_fk = "+this.nppId+"  and b.CONGTY_FK = "+this.congtyId+" \n";				
			query += 		" and b.pk_seq != '" + (this.id == "" ? "0": this.id) + "' \n";				
			query +=
							"	group by a.cttt_fk \n"+
							" )dathanhtoan on chungtu.pk_seq = dathanhtoan.cttt_fk \n"+ 
							"where chungtu.sotientt - ISNULL(dathanhtoan.tienthanhtoan, 0) > 0 \n";	
				query +=    " order by ngaychungtu ";
			
			System.out.println("1.Khoi tao phieu thu: " + query);
			
			ResultSet rsHoadon = db.get(query);
			List<IHoadon> cttList = new ArrayList<IHoadon>();
			if(rsHoadon != null)
			{
				try 
				{
					IHoadon hd = null;
					while(rsHoadon.next())
					{
						String id = rsHoadon.getString("pk_seq");
						String kyhieu = rsHoadon.getString("sochungtu");
						String sohoadon = rsHoadon.getString("sochungtu");
						String ngayhd = rsHoadon.getString("ngaychungtu");
						String avat = formatter.format(rsHoadon.getDouble("tienAvat"));
						String avatVND = formatter.format(rsHoadon.getDouble("tienAvatVND"));
						
						String tigiahd = Double.toString(rsHoadon.getDouble("tigia"));
						
						if(!this.tienteId.equals("100000"))
							 avat = formatterNT.format(rsHoadon.getDouble("tienAvat"));
						
						String dathanhtoan = "";
						
						String loaict = rsHoadon.getString("loaict");
						
						if(this.id.length() > 0)
						{
							if(rsHoadon.getDouble("SOTIENTT") > 0)
							{
								if(this.tienteId.equals("100000"))
									dathanhtoan = formatter.format(rsHoadon.getDouble("SOTIENTT"));
								else
									dathanhtoan = formatterNT.format(rsHoadon.getDouble("SOTIENTT"));
							}
						}
						
						hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, dathanhtoan, "");
						hd.setTongtiencoVATVND(avatVND);
						hd.setTigiaHD(tigiahd);
						hd.setLoaict(loaict);
						cttList.add(hd);
					}
					rsHoadon.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			this.ctttList = cttList;
		}
		

		// LẤY DANH SÁCH HÓA ĐƠN - KHÁCH HÀNG
		if(this.nppId.length() > 0 && this.hoadonList.size() <= 0)
		{
			String query = "";
			// ERP_XOAKHTRATRUOC_HOADON : 0 - hd tài chính, 1 - hd khác, 2 - buttoantonghop
			if(this.id.length() > 0)
			{
				query = 	"SELECT b.pk_seq, cast(b.pk_seq as nvarchar(50)) as kyhieu, b.sohoadon, b.ngayxuathd as ngayhoadon, '0' as loaihd, isnull(b.TYGIA,1) as TYGIA,  \n" +
						 	"       b.tongtienAVAT - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVAT, " +
						 	"       (b.tongtienAVAT - isnull(thanh_toan.tongthanhtoan, '0'))* isnull(b.TYGIA,1) as sotienAVATVND, " +
						 	"       a.sotientt as DaThanhToan \n" +
						 	
						 	"FROM ERP_XOAKHTRATRUOC_HOADON a \n" +
						 	"     left join ERP_HOADON b on a.hoadon_fk = b.pk_seq \n"+
						 	"     left join	\n" +
						 	"	  ( 	\n"+
						 	"		 SELECT THU.HOADON_FK, SUM(THU.tongthanhtoan) tongthanhtoan \n"+
						 	"		 FROM (\n"+
						 	// XÓA KHÁCH HÀNG TRẢ TRƯỚC
						 	"			select 	TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
						 	"			from 	ERP_XOAKHTRATRUOC_HOADON TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +
							"			where 	TT.trangthai != '2' and TTHD.XOAKHTRATRUOC_FK != '" + this.id + "' and TTHD.LOAIHD = '0' and TT.KHACHHANG_FK ="+this.nppId+" and isnull(TT.isnpp,0) = "+this.isNPP+" \n" +
							" 					and TTHD.hoadon_fk in (select hoadon_fk from ERP_XOAKHTRATRUOC_HOADON where XOAKHTRATRUOC_FK = '" + this.id + "') \n" +
							"			group by hoadon_fk \n" +
							
							// THU TIỀN HÓA ĐƠN
							"       UNION ALL \n"+
							"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as tongthanhtoan   \n" +
							"			from ERP_THUTIEN_HOADON TTHD " +
							"			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '0' and isnull(TTHD.isNPP,0) = "+this.isNPP+" AND TT.BANGKE_FK IS NULL \n" +
							"			group by HOADON_FK  \n" +						
							
							// THU TIỀN THEO BẢNG KÊ
							"       UNION ALL \n"+
							"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as tongthanhtoan   \n" +
							"			from ERP_THUTIEN_HOADON TTHD " +
							"			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '0' and isnull(TTHD.isNPP,0) = "+this.isNPP+" AND TT.BANGKE_FK IS NOT NULL \n" +
							"			group by HOADON_FK  \n" +		
							
							//BÙ TRỪ KHÁCH HÀNG
							"		union all \n"+							
							"			select TTHD.HOADON_FK , sum(TTHD.XOANO) as DATHANHTOAN \n"+   
							"			from ERP_BUTRUKHACHHANG_CHITIET TTHD inner join ERP_BUTRUKHACHHANG TT on TTHD.BTKH_FK = TT.PK_SEQ \n"+  
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '0' and isnull(TT.isNPP,0) = "+this.isNPP+" \n"+ 
							"			group by HOADON_FK \n"+ 
							"			) THU \n"+
							"		 GROUP BY THU.HOADON_FK \n"+
							"	  )thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
							
							"where a.XOAKHTRATRUOC_FK = '" + this.id + "' and a.LOAIHD= '0' and b.LOAIHOADON = 0 \n";
				
					// HÓA ĐƠN KHÁC		
					query +=" union all \n"+
				
							"SELECT b.pk_seq, b.kyhieuhoadon as kyhieu, b.sohoadon, b.ngayhoadon, '1' as loaihd, 1 as TYGIA,  \n" +
						 	"       PLSP.SOTIENVND - isnull(thanh_toan.DATHANHTOAN, '0') as sotienAVAT, " +
						 	"       PLSP.SOTIENVND - isnull(thanh_toan.DATHANHTOAN, '0') as sotienAVATVND," +
						 	"       a.sotientt as DaThanhToan \n" +
						 	
						 	"FROM ERP_XOAKHTRATRUOC_HOADON a \n" +
						 	"LEFT JOIN ERP_HOADONPHELIEU b on a.hoadon_fk = b.pk_seq \n" +
							"INNER JOIN ( SELECT HOADONPHELIEU_FK, SUM(THANHTIEN)AS SOTIENVND  \n" +
							"             FROM ERP_HOADONPHELIEU_SANPHAM \n" +
							"             GROUP BY HOADONPHELIEU_FK ) AS PLSP  ON b.PK_SEQ= PLSP.HOADONPHELIEU_FK \n" +
						 	"LEFT JOIN (	\n" +
						 	" 		SELECT HOADON_FK, SUM( ISNULL(DATHANHTOAN, 0) ) as DATHANHTOAN  \n" +
							"		FROM  \n" +
							"		(  \n" +
							// THU TIỀN KHÔNG THEO BẢNG KÊ
							"			SELECT TTHD.HOADON_FK , sum( TTHD.SOTIENTT ) as DATHANHTOAN   \n" +
							"			FROM ERP_THUTIEN_HOADON TTHD \n" +
							"			INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			WHERE  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON= '1' \n"+
							"			GROUP BY HOADON_FK  \n" +	
							
							// TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
							"           UNION ALL \n"+
							
							"			SELECT TTHD.HOADON_FK , sum( TTHD.SOTIENTT ) as DATHANHTOAN    \n" +
							"			FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
							"			INNER JOIN ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n" +
							"			WHERE TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '1' \n"+							
			 				" 			AND TT.pk_seq != '" + (this.id == "" ? "0": this.id) + "' \n"+			
							"			GROUP BY HOADON_FK  \n" +
							
							// BÙ TRỪ KHÁCH HÀNG
							"           UNION ALL \n"+
							
							"			SELECT BT_KH.HOADON_FK, SUM( BT_KH.XOANO ) AS DATHANHTOAN  \n"+
							"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n "+
							"			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 1 \n"+
							"			GROUP BY BT_KH.HOADON_FK \n"+		
							
							"		)HOADONDATT  group by HOADON_FK  \n" +	
							")thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
							
							"WHERE a.XOAKHTRATRUOC_FK = '" + this.id + "' and a.LOAIHD= '1' \n";
							
					// BÚT TOÁN TỔNG HỢP
					query +=" union all \n"+
				
							" select 	b.pk_seq, '' as kyhieu, cast(b.pk_seq as nvarchar(50)) sohoadon, b.NGAYBUTTOAN, '2' as loaihd, 1 as TYGIA, \n"+  
						    "   		PLSP.SOTIENVND - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVAT, \n"+ 
						    "   		PLSP.SOTIENVND  - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVATVND, \n"+
						    "    		a.sotientt as DaThanhToan \n"+ 
						    
							" from 		ERP_XOAKHTRATRUOC_HOADON a \n"+ 
							"     		inner join ERP_BUTTOANTONGHOP b on a.hoadon_fk = b.PK_SEQ \n"+ 
							"     		inner join   (	SELECT BUTTOANTONGHOP_FK,KHACHHANG_FK,SUM(NO)AS SOTIENVND \n"+  
							"                   		FROM   ERP_BUTTOANTONGHOP_CHITIET \n"+ 
							"                   		WHERE  TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13111000' and CONGTY_FK = "+this.congtyId+") \n"+
							"						  			AND KHACHHANG_FK IS NOT NULL AND NO>0 AND KHACHHANG_FK = '"+this.nppId+"' AND isnull(isNPP,0) = "+this.isNPP+" \n"+
							"                   		GROUP BY BUTTOANTONGHOP_FK, KHACHHANG_FK)AS PLSP  ON b.PK_SEQ= PLSP.BUTTOANTONGHOP_FK \n"+ 
							"     left join \n"+	
							"		( 	select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n"+ 
							"			from ERP_XOAKHTRATRUOC_HOADON TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n"+ 
							"			where TT.trangthai != '2' and TTHD.XOAKHTRATRUOC_FK != '" +this.id + "' and TTHD.LOAIHD = '2' and TT.KHACHHANG_FK = "+this.nppId+" AND isnull(TT.isNPP,0) = "+this.isNPP+" \n"+ 
							" 			and TTHD.hoadon_fk in (select hoadon_fk from ERP_XOAKHTRATRUOC_HOADON where XOAKHTRATRUOC_FK = '"+ this.id + "') \n"+ 
							"			group by hoadon_fk \n"+
							"		)thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n"+ 
							
							"	where a.XOAKHTRATRUOC_FK = '" +this.id + "' and a.LOAIHD = '2' \n";
							
					query+=	" union all ";
					
					// HÓA ĐƠN HÀNG TRẢ VỀ
					
					query += "select b.pk_seq, b.KYHIEU as kyhieu, b.sohoadon, b.ngayxuathd as ngayhoadon, '7' as loaihd, isnull(b.TYGIA,1) as TYGIA,  \n" +
						 	"       (b.tongtienAVAT - isnull(thanh_toan.tongthanhtoan, '0'))*(-1) as sotienAVAT, " +
						 	"       ((b.tongtienAVAT - isnull(thanh_toan.tongthanhtoan, '0'))* isnull(b.TYGIA,1))*(-1) as sotienAVATVND, " +
						 	"       a.sotientt*(-1) as DaThanhToan \n" +
						 	
						 	"from ERP_XOAKHTRATRUOC_HOADON a \n" +
						 	"     left join ERP_HOADON b on a.hoadon_fk = b.pk_seq \n" +
						 	"     left join	\n" +
						 	"	  ( 	\n"+
						 	"		 SELECT THU.HOADON_FK, SUM(THU.tongthanhtoan) tongthanhtoan \n"+
						 	"		 FROM (\n"+
						 	// XÓA KHÁCH HÀNG TRẢ TRƯỚC
						 	"			select 	TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
						 	"			from 	ERP_XOAKHTRATRUOC_HOADON TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +
							"			where 	TT.trangthai != '2' and TTHD.XOAKHTRATRUOC_FK != '" + this.id + "' and TTHD.LOAIHD = '7' and TT.KHACHHANG_FK = "+this.nppId+" and isnull(TT.isnpp,0) = "+this.isNPP+" \n" +
							" 					and TTHD.hoadon_fk in (select hoadon_fk from ERP_XOAKHTRATRUOC_HOADON where XOAKHTRATRUOC_FK = '" + this.id + "') \n" +
							"			group by hoadon_fk \n" +
							
							// THU TIỀN HÓA ĐƠN
							"       UNION ALL \n"+
							"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as tongthanhtoan   \n" +
							"			from ERP_THUTIEN_HOADON TTHD " +
							"			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7' and isnull(TTHD.isNPP,0) = "+this.isNPP+" AND TT.BANGKE_FK IS NULL \n" +
							"			group by HOADON_FK  \n" +						
							
							// THU TIỀN THEO BẢNG KÊ
							"       UNION ALL \n"+
							"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as tongthanhtoan   \n" +
							"			from ERP_THUTIEN_HOADON TTHD " +
							"			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7' and isnull(TTHD.isNPP,0) = "+this.isNPP+" AND TT.BANGKE_FK IS NOT NULL \n" +
							"			group by HOADON_FK  \n" +		
							
							//BÙ TRỪ KHÁCH HÀNG
							"		UNION ALL \n"+
							
							"			select TTHD.HOADON_FK , sum(TTHD.XOANO) as DATHANHTOAN \n"+   
							"			from ERP_BUTRUKHACHHANG_CHITIET TTHD inner join ERP_BUTRUKHACHHANG TT on TTHD.BTKH_FK = TT.PK_SEQ \n"+  
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7' and isnull(TT.isNPP,0) = "+this.isNPP+" \n"+ 
							"			group by HOADON_FK \n"+ 
							
							"		UNION ALL \n"+		
							
							// THANH TOÁN HÓA ĐƠN
							"			SELECT TT_HD.HOADON_FK,SUM(TT_HD.THANHTOAN) AS THANHTOAN  \n"+
							"			FROM   ERP_THANHTOANHOADON TT INNER JOIN ERP_THANHTOANHOADON_HOADON_PHANBO TT_HD ON TT.PK_SEQ = TT_HD.TTHD_FK \n "+
							"			WHERE  TT.TRANGTHAI NOT IN (2) AND TT_HD.LOAIHD = 8 AND TT_HD.KHACHHANG_FK = "+this.nppId+" \n"+
							"			GROUP BY TT_HD.HOADON_FK \n"+
							
							"			) THU \n"+
							"		 GROUP BY THU.HOADON_FK \n"+
							"	  )thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
							
							"where a.XOAKHTRATRUOC_FK = '" + this.id + "' and a.LOAIHD= '7' and b.LOAIHOADON = 2 \n";
					
					query+= " UNION ALL \n";
					
					query+= 
							" (SELECT bt.PK_SEQ, '' KYHIEU ,  CAST(bt.PK_SEQ as nvarchar(50)) SOHOADON , BT.NGAYBUTRU , '4' AS LOAIHD, bt.tigia as TYGIA, \n"+
						    "    (BT.TONGTIEN - ISNULL(DATHANHTOAN.DATHANHTOAN,0))*(BT.TIGIA) AS SOTIENGOC, \n"+
						    "    (BT.TONGTIEN - ISNULL(DATHANHTOAN.DATHANHTOAN,0))*(BT.TIGIA) AS SOTIENVND,  BTHD.SOTIENTT AS DATHANHTOAN \n"+
						    " FROM ERP_BUTRUKHACHHANG BT  \n"+
						    " INNER JOIN NHAPHANPHOI KH ON BT.KH_NHANNO = KH.PK_SEQ \n"+
						    " INNER JOIN ERP_XOAKHTRATRUOC_HOADON BTHD ON BT.PK_SEQ = BTHD.HOADON_FK AND BTHD.LOAIHD = '4' \n"+
						    " LEFT JOIN ( \n"+
							"				SELECT DATHANHTOAN.HOADON_FK, SUM(DATHANHTOAN.DATHANHTOAN) DATHANHTOAN \n"+
							"				FROM \n"+
							"				( \n"+
							"					SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
							"					FROM 	ERP_THUTIEN_HOADON TTHD \n"+
							"							INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
							"					WHERE TTHD.LOAIHOADON= '4' AND TT.TRANGTHAI NOT IN (2) \n"+
							"					GROUP BY TTHD.HOADON_FK \n"+
												
							"					UNION ALL \n"+
												
							"					SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
							"					FROM 	ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
							"							INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.xoakhtratruoc_fk = TT.PK_SEQ \n"+
							"					WHERE TTHD.LOAIHD= '4' AND TT.TRANGTHAI NOT IN (2) \n"+
							" 					AND TT.pk_seq != '" + (this.id == "" ? "0": this.id) + "' \n"+			
							"					GROUP BY TTHD.HOADON_FK \n"+
							"				) DATHANHTOAN \n"+
							"				GROUP BY HOADON_FK \n"+
											
							"			  ) DATHANHTOAN ON BT.pk_seq = DATHANHTOAN.HOADON_FK \n"+
							" WHERE BT.TRANGTHAI = 1 \n"+ 
							" AND (BT.TONGTIEN - ISNULL(DATHANHTOAN.DATHANHTOAN,0))*(BT.TIGIA) > 0 \n"+
							" AND BT.KH_NHANNO  = '" + this.nppId + "'  "+	
							" AND BTHD.xoakhtratruoc_fk = "+(this.id == "" ? "0": this.id) + " and LOAIHD= '4'   "+
							" ) \n";
					
					query+= " UNION ALL \n";
			}
				
			// HÓA ĐƠN TÀI CHÍNH
			query += 		"( " +
							"	SELECT hoadon.pk_seq, hoadon.kyhieu, hoadon.sohoadon, hoadon.ngayhoadon, '0' as loaihd, hoadon.TYGIA,   \n" +
							"	hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') as sotienAVAT, " +
							"   (hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'))* isnull(hoadon.TYGIA,0) as sotienAVATVND, " +
							"   0 as DATHANHTOAN \n" +
							
							"	FROM ( \n" +
							"		select HD.pk_seq, KYHIEU as kyhieu, sohoadon, ngayxuathd as ngayhoadon, tongtienAVAT as sotienAVAT, ISNULL(TYGIA,1) as TYGIA \n" +
							"		from  ERP_HOADON HD \n" +
							"       INNER JOIN NHAPHANPHOI KH ON HD.NPP_FK = KH.PK_SEQ \n"+
							"		where 1 = 1 and HD.trangthai in (2,4) AND isnull(HD.TIENTE_FK,100000) = " + this.tienteId + " AND HD.LOAIHOADON = 0 \n"+
							//"       AND HD.PK_SEQ NOT IN (SELECT HOADON_FK FROM ERP_XOAKHTRATRUOC_HOADON WHERE xoakhtratruoc_fk ='" + (this.id == "" ? "0": this.id)+ "'  AND LOAIHD = 0 )  \n"+
							"		and HD.npp_fk = '" + this.nppId + "'  \n";
					
			query += 		"	)hoadon \n" +
							"	left join ( \n" +
							" 		select HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) as DATHANHTOAN  \n" +
							"		from  \n" +
							"		(  \n" +
							// XÓA KHÁCH HÀNG TRẢ TRƯỚC
							"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN    \n" +
							"			from ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
							"			inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n" +
							"			where TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '0' and TT.KHACHHANG_FK = "+this.nppId+" ";
			query += 		" 			and TT.pk_seq != '" + (this.id == "" ? "0": this.id)+ "' ";
			query += 		"			group by HOADON_FK  \n" +
								
							"			union all   \n" +
							// THU TIỀN HÓA ĐƠN
							"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n" +
							"			from ERP_THUTIEN_HOADON TTHD " +
							"			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '0' AND TT.BANGKE_FK IS NULL \n" +
							"			group by HOADON_FK  \n" +
							
							// THU TIỀN BẢNG KÊ
							"       	union all \n"+
							
							"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n" +
							"			from ERP_THUTIEN_HOADON TTHD " +
							"			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '0' AND TT.BANGKE_FK IS NOT NULL \n" +
							"			group by HOADON_FK  \n" +
							
							// BÙ TRỪ KHÁCH HÀNG
							"			union all \n"+							
							"			select TTHD.HOADON_FK , sum(TTHD.XOANO) as DATHANHTOAN \n"+   
							"			from ERP_BUTRUKHACHHANG_CHITIET TTHD inner join ERP_BUTRUKHACHHANG TT on TTHD.BTKH_FK = TT.PK_SEQ \n"+  
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '0' \n"+ 
							"			group by HOADON_FK \n"+
							
							"		)HOADONDATT  group by HOADON_FK  \n" +								
							"	)dathanhtoan on hoadon.pk_seq = dathanhtoan.hoadon_fk \n" +
							
							"where round(hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'), 0) > 0 ";
					query+= " )";
						
			query += 		"UNION ALL \n"+
			
			// ERP_BUTTOANTONGHOP
			
							 "( \n" +
							 "	select hoadon.pk_seq, hoadon.kyhieu, hoadon.sohoadon, hoadon.ngayhoadon, '2' as loaihd, hoadon.TYGIA, \n" +
							 "		   hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') as sotienAVAT, \n" +
							 "   	  (hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'))* isnull(hoadon.TYGIA,0) as sotienAVATVND, \n" +
							 "   	   0 as DATHANHTOAN \n" +
							 
							 "	from ( \n" +
							 "			select 	a.pk_seq, '' as kyhieu, cast(a.pk_seq as nvarchar(50)) sohoadon, ngaybuttoan as ngayhoadon, SUM(isnull(NO,0)) as sotienAVAT, 1 as TYGIA \n" +
							 "			from 	ERP_BUTTOANTONGHOP a " +
							 "			inner join ERP_BUTTOANTONGHOP_CHITIET b on a.PK_SEQ = b.BUTTOANTONGHOP_FK  \n" +
							 "			where 	b.khachhang_fk = '" + this.nppId + "' and trangthai in (1) and b.NO > 0 " +
							 "			and b.TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13111000' and CONGTY_FK = "+this.congtyId+") "+
							 " 			AND a.CONGTY_FK = "+this.congtyId;
			
			query += 		 " 			and pk_seq not in (select hoadon_fk from ERP_XoakhTraTruoc_HoaDon where xoakhtratruoc_fk = '" +(this.id == "" ? "0": this.id) + "' )  ";
						
			query += 		 "			group by a.pk_seq, ngaybuttoan \n"+
							 " 		)hoadon \n" +
							 "		left join ( \n" +
							 " 		select HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) as DATHANHTOAN  \n" +
							 "		from  \n" +
							 "		(  \n" +
							 "			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN    \n" +
							 "			from ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
							 "			inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n" +
							 "			where TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '2' and TT.KHACHHANG_FK ="+this.nppId+" AND TT.CONGTY_FK = "+this.congtyId;
								
			query += 		 " 			and TT.pk_seq != '" + (this.id == "" ? "0": this.id) + "' ";
			
			query += 		 "			group by HOADON_FK  \n" +
								
					 		 "			union all   \n" +
					
							 "			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n" +
							 "			from ERP_THUTIEN_HOADON TTHD " +
							 "			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							 "			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '2'  AND TT.CONGTY_FK = " +this.congtyId+
							 "			group by HOADON_FK  \n" +					
					 
							 "		)HOADONDATT  group by HOADON_FK  \n" +								
							 "	)dathanhtoan on hoadon.pk_seq = dathanhtoan.hoadon_fk \n" +
							 
							 " where round(hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'), 0) > 0 ";
							if(this.tungay.trim().length()>0)
							{
								query += " and hoadon.ngayhoadon >= '"+this.tungay+"'";
							}
							
							if(this.denngay.trim().length()>0)
							{
								query += " and hoadon.ngayhoadon <= '"+this.denngay+"'";
							}
							query+= " ) ";
			
			// HÓA ĐƠN PHẾ LIỆU : tiền việt
			
		 /* if(this.tienteId.equals("100000")) // HOA DON KHAC
		  {*/
			query +=        " UNION ALL ";  
			query += 		"( " +
							"	SELECT hoadon.pk_seq, hoadon.kyhieu, hoadon.sohoadon, hoadon.ngayhoadon, '1' as loaihd, 1 as TYGIA, \n" +
							"	       hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') as sotienAVAT, " +
							"          hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') as sotienAVATVND, " +
							"          isnull(dathanhtoan.DATHANHTOAN, '') as DATHANHTOAN \n" +
							
							"	FROM ( \n" +
							"			SELECT pk_seq, kyhieuhoadon as kyhieu, sohoadon, ngayhoadon, PLSP.SOTIENVND  as sotienAVAT \n" +
							"			FROM ERP_HOADONPHELIEU HD INNER JOIN \n" +
							"    		(SELECT HOADONPHELIEU_FK, SUM(round(thanhtien,0))AS SOTIENVND \n" +
							"     		FROM ERP_HOADONPHELIEU_SANPHAM \n" +
							"     		GROUP BY HOADONPHELIEU_FK)AS PLSP  ON HD.PK_SEQ= PLSP.HOADONPHELIEU_FK \n" +
							"			WHERE khachhang_fk = '" + this.nppId + "' and trangthai = '1'  and HD.CONGTY_FK = "+this.congtyId;			
			query += 		" 			AND PK_SEQ NOT IN (select hoadon_fk from ERP_XoakhTraTruoc_HoaDon where xoakhtratruoc_fk = '" + (this.id == "" ? "0": this.id) + "')  ";					
			query += 		"		)hoadon \n" +
							"	LEFT JOIN ( \n" +
							" 		SELECT HOADON_FK, SUM( ISNULL(DATHANHTOAN, 0) ) as DATHANHTOAN  \n" +
							"		FROM  \n" +
							"		(  \n" +
							// THU TIỀN KHÔNG THEO BẢNG KÊ
							"			SELECT TTHD.HOADON_FK , sum( TTHD.SOTIENTT ) as DATHANHTOAN   \n" +
							"			FROM ERP_THUTIEN_HOADON TTHD \n" +
							"			INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			WHERE  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON= '1' \n"+
							"			GROUP BY HOADON_FK  \n" +	
							
							// TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
							"           UNION ALL \n"+
							
							"			SELECT TTHD.HOADON_FK , sum( TTHD.SOTIENTT ) as DATHANHTOAN    \n" +
							"			FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
							"			INNER JOIN ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n" +
							"			WHERE TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '1' \n"+							
			 				" 			AND TT.pk_seq != '" + (this.id == "" ? "0": this.id) + "' \n"+			
							"			GROUP BY HOADON_FK  \n" +
							
							// BÙ TRỪ KHÁCH HÀNG
							"           UNION ALL \n"+
							
							"			SELECT BT_KH.HOADON_FK, SUM( BT_KH.XOANO ) AS DATHANHTOAN  \n"+
							"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n "+
							"			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 1 \n"+
							"			GROUP BY BT_KH.HOADON_FK \n"+		
							
							"		)HOADONDATT  group by HOADON_FK  \n" +								
							"	)dathanhtoan on hoadon.pk_seq = dathanhtoan.hoadon_fk \n" +
							
							"WHERE round(hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'), 0) != 0 ";			
		 /* }
		  */
			//	 BÙ TRỪ CÔNG NỢ
			query +=        " UNION ALL \n";  
			
			query += 		" (SELECT bt.PK_SEQ, '' KYHIEU ,  CAST(bt.PK_SEQ as nvarchar(50)) SOHOADON , BT.NGAYBUTRU , '4' AS LOAIHD, bt.tigia as TYGIA, \n"+
						    "    (BT.TONGTIEN - ISNULL(DATHANHTOAN.DATHANHTOAN,0))*(BT.TIGIA) AS SOTIENGOC, \n"+
						    "    (BT.TONGTIEN - ISNULL(DATHANHTOAN.DATHANHTOAN,0))*(BT.TIGIA) AS SOTIENVND,  0 AS DATHANHTOAN \n"+
						    " FROM ERP_BUTRUKHACHHANG BT  \n"+
						    " INNER JOIN NHAPHANPHOI KH ON BT.KH_NHANNO = KH.PK_SEQ \n"+
						    " LEFT JOIN ( \n"+
							"				SELECT DATHANHTOAN.HOADON_FK, SUM(DATHANHTOAN.DATHANHTOAN) DATHANHTOAN \n"+
							"				FROM \n"+
							"				( \n"+
							"					SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
							"					FROM 	ERP_THUTIEN_HOADON TTHD \n"+
							"							INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
							"					WHERE TTHD.LOAIHOADON= '4' AND TT.TRANGTHAI NOT IN (2) \n"+
							"					GROUP BY TTHD.HOADON_FK \n"+
												
							"					UNION ALL \n"+
												
							"					SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
							"					FROM 	ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
							"							INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.xoakhtratruoc_fk = TT.PK_SEQ \n"+
							"					WHERE TTHD.LOAIHD= '4' AND TT.TRANGTHAI NOT IN (2) \n"+
							" 					AND TT.pk_seq != '" + (this.id == "" ? "0": this.id) + "' \n"+			
							"					GROUP BY TTHD.HOADON_FK \n"+
							"				) DATHANHTOAN \n"+
							"				GROUP BY HOADON_FK \n"+
											
							"			  ) DATHANHTOAN ON BT.pk_seq = DATHANHTOAN.HOADON_FK \n"+
							" WHERE BT.TRANGTHAI = 1 \n"+ 
							" AND (BT.TONGTIEN - ISNULL(DATHANHTOAN.DATHANHTOAN,0))*(BT.TIGIA) > 0 \n"+
							" AND BT.KH_NHANNO  = '" + this.nppId + "'  "+	
							" AND BT.PK_SEQ NOT IN (select hoadon_fk from ERP_XoakhTraTruoc_HoaDon where xoakhtratruoc_fk = '" + (this.id == "" ? "0": this.id) + "' and LOAIHD= '4' )  "+
							" ) \n";
			
			
			query +=        " UNION ALL \n";  
		  // HÓA ĐƠN HÀNG TRẢ VỀ
			query += 		"( \n" +
							"	select hoadon.pk_seq, hoadon.kyhieu, hoadon.sohoadon, hoadon.ngayhoadon, '7' as loaihd, hoadon.TYGIA,   \n" +
							"	(hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'))*(-1) as sotienAVAT, " +
							"   ((hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'))* isnull(hoadon.TYGIA,0))*(-1) as sotienAVATVND, " +
							"   0 as DATHANHTOAN \n" +
							
							"	FROM ( \n" +
							
							"		select pk_seq, KYHIEU as kyhieu, sohoadon, ngayxuathd as ngayhoadon, tongtienAVAT as sotienAVAT, ISNULL(TYGIA,1) as TYGIA \n" +
							"		from ERP_HOADON \n" +
							"		where 1 = 1 and trangthai in (1) AND isnull(TIENTE_FK,100000) = " + this.tienteId + " AND LOAIHOADON = 2 and CONGTY_FK = "+this.congtyId+										
							"		and khachhang_fk = '" + this.nppId + "'  "+				
							" 		AND pk_seq not in (select hoadon_fk from ERP_XoakhTraTruoc_HoaDon where xoakhtratruoc_fk = '" + (this.id == "" ? "0": this.id) + "') AND TONGTIENAVAT > 0 " +
					
							"	)hoadon \n" +
							"	left join ( \n" +
							" 		select HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) as DATHANHTOAN  \n" +
							"		from  \n" +
							"		(  \n" +
							// XÓA KHÁCH HÀNG TRẢ TRƯỚC
							"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN    \n" +
							"			from ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
							"			inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n" +
							"			where TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7' and TT.KHACHHANG_FK = "+this.nppId+" ";
			query += 		" 			and TT.pk_seq != '" + (this.id == "" ? "0": this.id)+ "' ";
			query += 		"			group by HOADON_FK  \n" +
								
							"			union all   \n" +
							// THU TIỀN HÓA ĐƠN
							"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n" +
							"			from ERP_THUTIEN_HOADON TTHD " +
							"			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7' AND TT.BANGKE_FK IS NULL \n" +
							"			group by HOADON_FK  \n" +
							
							// THU TIỀN BẢNG KÊ
							"       	UNION ALL \n"+
							
							"			SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) as DATHANHTOAN   \n" +
							"			FROM ERP_THUTIEN_HOADON TTHD " +
							"			INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7'  AND TT.BANGKE_FK IS NOT NULL \n" +
							"			group by HOADON_FK  \n" +
							
							// BÙ TRỪ KHÁCH HÀNG
							"			UNION ALL \n"+		
							
							"			SELECT TTHD.HOADON_FK , SUM(TTHD.XOANO) as DATHANHTOAN \n"+   
							"			FROM ERP_BUTRUKHACHHANG_CHITIET TTHD inner join ERP_BUTRUKHACHHANG TT on TTHD.BTKH_FK = TT.PK_SEQ \n"+  
							"			WHERE  TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7' \n"+ 
							"			GROUP BY HOADON_FK \n"+
							
							"  			UNION ALL \n"+
							
							// THANH TOÁN HÓA ĐƠN
							"			SELECT TT_HD.HOADON_FK, SUM(TT_HD.THANHTOAN) AS THANHTOAN  \n"+
							"			FROM   ERP_THANHTOANHOADON TT INNER JOIN ERP_THANHTOANHOADON_HOADON_PHANBO TT_HD ON TT.PK_SEQ = TT_HD.TTHD_FK \n "+
							"			WHERE  TT.TRANGTHAI NOT IN (2) AND TT_HD.LOAIHD = 8 AND TT_HD.KHACHHANG_FK = "+this.nppId+" \n"+
							"			GROUP BY TT_HD.HOADON_FK \n"+
							
							"		)HOADONDATT  group by HOADON_FK  \n" +								
							"	)dathanhtoan on hoadon.pk_seq = dathanhtoan.hoadon_fk \n" +
							
							"where round(hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'), 0) > 0 \n";
			query+= 		" ) \n";			
		  
		    query += 		") order by ngayhoadon asc ";
		    
			
						
			System.out.println("  1.Query khoi tao hoa don: " + query);
			
			ResultSet rsHoadon = db.get(query);
			List<IHoadon> hdList = new ArrayList<IHoadon>();
			if(rsHoadon != null)
			{
				try 
				{
					IHoadon hd = null;
					while(rsHoadon.next())
					{
						String id = rsHoadon.getString("pk_seq");
						String kyhieu = rsHoadon.getString("kyhieu");
						String sohoadon = rsHoadon.getString("sohoadon");
						String ngayhd = rsHoadon.getString("ngayhoadon");
						String loaihd = rsHoadon.getString("loaihd");
						String tigiahd = rsHoadon.getString("TYGIA");
						String avat = formatter.format(rsHoadon.getDouble("sotienAVAT"));
						String avatVND = formatter.format(rsHoadon.getDouble("sotienAVATVND"));
						
						if(!this.tienteId.equals("100000"))
							avat = formatterNT.format(rsHoadon.getDouble("sotienAVAT"));
						
						String dathanhtoan = "";
						if(this.id.length() > 0)
						{
							if(rsHoadon.getDouble("DaThanhToan") != 0)
							{
								if(!this.tienteId.equals("100000"))
								   dathanhtoan = formatterNT.format(rsHoadon.getDouble("DaThanhToan"));
								else
							       dathanhtoan = formatter.format(rsHoadon.getDouble("DaThanhToan"));
							}
						}
						

						System.out.println("BACNH:"+dathanhtoan);
						
						hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, dathanhtoan, "");
						hd.setTigiaHD(tigiahd);
						hd.setTienteId(this.tienteId);
						hd.setLoaihd(loaihd);
						hd.setTongtiencoVATVND(avatVND);
						hdList.add(hd);
					}
					rsHoadon.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			this.hoadonList = hdList;
		
		}
		
// Xóa tạm ứng
// Lấy chúng từ trả trước
	  if(nppId.trim().length() <=0 &&  this.ctttList.size() <= 0 &&  this.nvtuId.trim().length() >0 || this.nccId.trim().length() >0) // Load chứng từ trả trước: chi phí khác
	  {
		  //Nhân viên
		  String query="";
		  if(this.nvtuId.trim().length() >0)
		  {
			  
			  query = "GET_XOANONHANVIEN_CTTT "+this.nvtuId+","+((this.id == null || this.id.trim().length()==0)? "0": this.id)+","+this.tienteId+",0,0,"+this.congtyId+",'"+this.ngaychungtu+"', 0";
//			  if(this.id.length() >0)
//			  {
//				// loaict = 3 - Chi phí khác
//				// loaict = 4 - bút toán tổng hợp được xóa nợ
//				  
//				  query = 	"select b.pk_seq, b.pk_seq  as sochungtu, b.ngay as ngaychungtu, isnull(b.TIGIA,1) as tigia, \n " +
//							"      (c.THUE + c.TONGTIENCHUATHUE) - isnull(thanh_toan.tongthanhtoan, '0') as tienAvat," +
//							"      a. tienthanhtoan as SOTIENTT, 3 as loaict \n " +
//							
//							"from ERP_XOAKHTRATRUOC_CTTT a " +
//							"    inner join ERP_CHIPHIKHAC b on a.cttt_fk = b.pk_seq \n " +
//							"    inner join ERP_CHIPHIKHAC_CHITIET c on b.PK_SEQ = c.CHIPHIKHAC_FK \n "+
//							"    left join	\n " +
//							"	( 	select 	TTHD.cttt_fk, sum(TTHD.TienThanhToan) as tongthanhtoan \n " +
//							"		from 	ERP_XOAKHTRATRUOC_CTTT TTHD " +
//							"   		 	inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n " +
//							"		where 	TT.trangthai != '2' and TTHD.loaict = 3 and TTHD.XOAKHTRATRUOC_FK != '" + this.id + "' and TT.nhanvien_fk ="+this.nvtuId+" \n " +
//							"		      	and TTHD.cttt_fk in (select cttt_fk from ERP_XOAKHTRATRUOC_CTTT where XOAKHTRATRUOC_FK = '" + this.id + "' and loaict = 3 ) \n " +
//							"		group by cttt_fk " +
//							"	)thanh_toan on a.cttt_fk = thanh_toan.cttt_fk \n " +
//							
//							"where a.XOAKHTRATRUOC_FK = '" + this.id + "'   \n ";
//				  
//				  query +=  " union all "+
//						"       select b.THUTIEN_FK pk_seq, CAST(c.pk_seq as varchar(10)) as sochungtu, c.ngaychungtu ngaychungtu, isnull(c.tigia,1) as tigia,  \r\n" + 
//				  		"       isnull(b.SOTIEN,0) - isnull(thanh_toan.tienthanhtoan, '0') as tienAvat, \r\n" + 
//				  		"       a.tienthanhtoan as SOTIENTT, 0 as loaict \r\n" + 
//				  		" from  ERP_XOAKHTRATRUOC_CTTT a INNER JOIN \r\n" + 
//				  		"		ERP_THUTIEN_DINHKHOANCO b ON a.cttt_fk = b.THUTIEN_FK \r\n" + 
//				  		"		INNER JOIN ERP_THUTIEN c ON b.THUTIEN_FK = c.pk_seq \r\n" + 
//				  		" 		left join \r\n" + 
//				  		" 		( \r\n" + 
//				  		"			select 	a.cttt_fk, sum(a.tienthanhtoan) as tienthanhtoan \r\n" + 
//				  		"			from 	ERP_XOAKHTRATRUOC_CTTT a \r\n" + 
//				  		"					inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk = b.pk_seq \r\n" + 
//				  		"			where 	b.trangthai != 2 and a.loaict =0 and a.XOAKHTRATRUOC_FK != '"+this.id+"' and b.nhanvien_fk = "+this.nvtuId+" \r\n" + 
//				  		"					and a.cttt_fk IN (	select cttt_fk from ERP_XOAKHTRATRUOC_CTTT \r\n" + 
//				  		"   									where XOAKHTRATRUOC_FK = '"+this.id+"' and loaict = 0) \r\n" + 
//				  		"			group by a.cttt_fk \r\n" + 
//				  		" 		)thanh_toan on a.cttt_fk = thanh_toan.cttt_fk \r\n" + 
//				  		"where a.XOAKHTRATRUOC_FK = '"+this.id+"' \r\n" + 
//				  		"and b.DOITUONGDINHKHOAN=6\r\n" ;
//							
//							query+=	"union all \n "+					
//					
//							"select b.BUTTOANTONGHOP_FK pk_seq, CAST(c.pk_seq as varchar(10)) as sochungtu, c.ngaybuttoan ngaychungtu, 1 as tigia,  \n" +
//						 	"       isnull(b.CO,0) - isnull(thanh_toan.tienthanhtoan, '0') as tienAvat, \n" +
//						 	"       a.tienthanhtoan as SOTIENTT, 4 as loaict \n" +
//							" from  ERP_XOAKHTRATRUOC_CTTT a INNER JOIN \n"+
//							"		ERP_BUTTOANTONGHOP_CHITIET b ON a.cttt_fk = b.buttoantonghop_fk \n"+ 
//							"		INNER JOIN ERP_BUTTOANTONGHOP c ON b.buttoantonghop_fk = c.pk_seq \n"+
//							" 		left join \n"+  
//							" 		( \n"+  
//							"			select 	a.cttt_fk, sum(a.tienthanhtoan) as tienthanhtoan \n"+ 
//							"			from 	ERP_XOAKHTRATRUOC_CTTT a \n"+ 
//							"					inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk = b.pk_seq \n"+  
//							"			where 	b.trangthai != 2 and a.loaict = 4 and a.XOAKHTRATRUOC_FK != '" + this.id + "' and b.nhanvien_fk = "+this.nvtuId+" \n"+
//							"					and a.cttt_fk IN (	select cttt_fk from ERP_XOAKHTRATRUOC_CTTT \n" +
//							"   									where XOAKHTRATRUOC_FK = '" + this.id + "' and loaict = 4) \n" +
//							"			group by a.cttt_fk \n"+
//							" 		)thanh_toan on a.cttt_fk = thanh_toan.cttt_fk \n"+ 
//							"where a.XOAKHTRATRUOC_FK = '" + this.id + "' and b.CO > 0 and b.nhanvien_fk = "+this.nvtuId+" and b.TAIKHOANKT_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '14100000' AND CONGTY_FK = "+this.congtyId+") \n";
//					
//				query += 	"union all \n";
//				  
//			  }
//			  
//			  query	+= 		"select chungtu.pk_seq, chungtu.sochungtu, chungtu.ngaychungtu, chungtu.tigia, " +
//			  				"       chungtu.tienAvat - ISNULL(dathanhtoan.tienthanhtoan, 0) as tienAvat, \n " +
//				  			"       ISNULL(dathanhtoan.tienthanhtoan, 0) as SOTIENTT, 3 as loaict \n " +
//				  			"from 	( " +
//				  			"			select 	a.PK_SEQ, a.PK_SEQ as sochungtu, a.NGAY as ngaychungtu, isnull(a.TIGIA,1) as tigia,  (b.TONGTIENCHUATHUE + b.THUE) as tienAvat \n "+
//				  			"   		from 	ERP_CHIPHIKHAC a " +
//				  			"   				inner join ERP_CHIPHIKHAC_CHITIET b on a.PK_SEQ=b.CHIPHIKHAC_FK \n "+
//				  			"   		where 	a.TRANGTHAI=1 and  a.LOAI=1 and a.DOITUONG= '"+this.nvtuId+"' AND a.TIENTE_FK = " + this.tienteId + " \n " +
//				  			"         			and a.PK_SEQ not in \n "+
//				  			" 					(select b.cttt_fk" +
//				  			"   	 			 from  ERP_XOAKHTRATRUOC a inner join ERP_XOAKHTRATRUOC_CTTT b on a.PK_SEQ=b.xoakhtratruoc_fk" +
//				  			"    	 			 where a.TRANGTHAI=0 and b.LOAICT = 3 and a.nhanvien_fk = "+this.nvtuId+") \n ";
//			  	
//			  if(this.id.length() > 0){
//					query += "and a.pk_seq not in (select cttt_fk from ERP_XOAKHTRATRUOC_CTTT where xoakhtratruoc_fk = '" + this.id + "' and LOAICT = 3) ";
//			  }
//			  
//			  query += 		" )chungtu " +
//			  				"left join  " +							
//							"(  " +
//							"	select a.cttt_fk, sum(a.tienthanhtoan) as tienthanhtoan \n" +
//							"	from   ERP_XOAKHTRATRUOC_CTTT a inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk = b.pk_seq  \n" +
//							"	where  b.trangthai != 2 and b.nhanvien_fk ='"+this.nvtuId+"' and b.tiente_fk = '"+ this.tienteId +"' and a.LOAICT = 3 and b.NHANVIEN_FK = "+this.nvtuId+" \n";
//				
//			  if(this.id.trim().length() > 0)
//					query += " 	and b.pk_seq != '" + this.id + "' \n";
//			  
//			  query +=		"	group by a.cttt_fk \n " +
//							")dathanhtoan on chungtu.pk_seq = dathanhtoan.cttt_fk \n" +
//							
//							"where round(chungtu.tienAvat - ISNULL(dathanhtoan.tienthanhtoan, 0), 0) > 0 ";
//			 
//			  if(this.tungay.trim().length()>0)
//				{
//					query += " and chungtu.ngaychungtu >= '"+this.tungay+"'";
//				}
//				
//				if(this.denngay.trim().length()>0)
//				{
//					query += " and chungtu.ngaychungtu <= '"+this.denngay+"'";
//				}
//				
//				
//			  query +=     "UNION ALL\r\n" + 
//			  		"\r\n" + 
//			  		" select chungtu.pk_seq, chungtu.sochungtu, chungtu.ngaychungtu, isnull(chungtu.tigia,1) as tigia,  \r\n" + 
//			  		"  		 case when tiente_fk = '100000' then isnull(chungtu.sotientt,0) - ISNULL(dathanhtoan.tienthanhtoan, 0) \r\n" + 
//			  		"        else isnull(chungtu.sotienttNT,0) - ISNULL(dathanhtoan.tienthanhtoan, 0) end as tienAvat, \r\n" + 
//			  		"        ISNULL(dathanhtoan.tienthanhtoan, 0) as SOTIENTT, 0 as loaict \r\n" + 
//			  		" from \r\n" + 
//			  		"( \r\n" + 
//			  		"	select	a.pk_seq, CAST(a.pk_seq as varchar(10)) as sochungtu, NGAYCHUNGTU ngaychungtu, case when isnull(tiente_fk,100000)=100000 then sum(isnull(b.SOTIEN,0)) else sum(isnull(b.SOTIENNT,0)) end as SOTIENTTNT, sum(isnull(b.SOTIEN,0)) sotientt, isnull(a.tigia,1) as tigia \r\n" + 
//			  		"	, tiente_fk\r\n" + 
//			  		"	from	ERP_THUTIEN a    \r\n" + 
//			  		"	INNER JOIN ERP_THUTIEN_DINHKHOANCO b on a.PK_SEQ = b.THUTIEN_FK \r\n" + 
//			  		"	where	trangthai = '1' \r\n" + 
//			  		"	and b.DOITUONGDINHKHOAN=6\r\n";
//			  if(this.id.length() > 0){
//					query += " and a.pk_seq not in (select cttt_fk from ERP_XOAKHTRATRUOC_CTTT where xoakhtratruoc_fk = '" + this.id + "' and LOAICT = 0) ";
//			  }
//			  		query += "	and b.DOITUONG_FK = '"+this.nvtuId+"' and TAIKHOAN_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '14100000' AND CONGTY_FK = "+this.congtyId+") \r\n" + 
//			  		"	group by a.pk_seq, khachhang_fk, b.DOITUONG_FK, NGAYCHUNGTU,tiente_fk ,tigia\r\n" + 
//			  		" )chungtu \r\n" + 
//			  		" left join \r\n" + 
//			  		" ( \r\n" + 
//			  		"	select 	a.cttt_fk, sum(a.tienthanhtoan) as tienthanhtoan \r\n" + 
//			  		"	from 	ERP_XOAKHTRATRUOC_CTTT a \r\n" + 
//			  		"			inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk = b.pk_seq \r\n" + 
//			  		"	where 	b.trangthai != 2 and a.loaict = 0 and b.nhanvien_fk = "+this.nvtuId+" \r\n" ;
//			  if(this.id.trim().length() > 0)
//					query += " 	and b.pk_seq != '" + this.id + "' \n";
//			  		query += "	group by a.cttt_fk \r\n" + 
//			  		" )dathanhtoan on chungtu.pk_seq = dathanhtoan.cttt_fk \r\n" + 
//			  		"where chungtu.sotientt - ISNULL(dathanhtoan.tienthanhtoan, 0) > 0 ";	
//			  
//			  if(this.tungay.trim().length()>0)
//				{
//					query += " and chungtu.ngaychungtu >= '"+this.tungay+"'";
//				}
//				
//				if(this.denngay.trim().length()>0)
//				{
//					query += " and chungtu.ngaychungtu <= '"+this.denngay+"'";
//				}
//				
//				
//			  query+=
//							" UNION ALL \n"+
//					
//							" select chungtu.pk_seq, chungtu.sochungtu, chungtu.ngaychungtu, isnull(chungtu.tigia,1) as tigia,  \n"+
//						    "  		 case when '100000' = '100000' then isnull(chungtu.sotientt,0) - ISNULL(dathanhtoan.tienthanhtoan, 0) \n"+ 
//						    "        else isnull(chungtu.sotienttNT,0) - ISNULL(dathanhtoan.tienthanhtoan, 0) end as tienAvat, \n"+  
//						    "        ISNULL(dathanhtoan.tienthanhtoan, 0) as SOTIENTT, 4 as loaict \n"+  
//						    
//							" from \n"+ 
//							"( \n"+ 
//							"	select	a.pk_seq, CAST(a.pk_seq as varchar(10)) as sochungtu, NGAYBUTTOAN ngaychungtu, sum(isnull(CO,0)) as SOTIENTTNT, sum(CO) sotientt, 1 as tigia \n"+  
//							"	from	ERP_BUTTOANTONGHOP a " +
//							"   INNER JOIN ERP_BUTTOANTONGHOP_CHITIET b on a.PK_SEQ = b.BUTTOANTONGHOP_FK \n"+   
//							"	where	trangthai = '1' \n"+ 
//							"			and nhanvien_fk = '"+this.nvtuId+"' and b.CO > 0 and TAIKHOANKT_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '14100000' AND CONGTY_FK = "+this.congtyId+") \n";
//							if(this.id.length() > 0){
//								query += "	and pk_seq not in (select cttt_fk from ERP_XOAKHTRATRUOC_CTTT where xoakhtratruoc_fk = '" + this.id + "' and loaict = 4 ) \n";
//							}
//							query+=
//								"	group by a.pk_seq, khachhang_fk, TAIKHOANKT_FK, ngaybuttoan \n"+
//							" )chungtu \n"+ 
//							" left join \n"+  
//							" ( \n"+  
//							"	select 	a.cttt_fk, sum(a.tienthanhtoan) as tienthanhtoan \n"+ 
//							"	from 	ERP_XOAKHTRATRUOC_CTTT a \n"+ 
//							"			inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk = b.pk_seq \n"+  
//							"	where 	b.trangthai != 2 and a.loaict = 4 and b.nhanvien_fk = "+this.nvtuId+" \n";
//					if(this.id.trim().length() > 0){
//						query += 	" and b.pk_seq != '" + this.id + "' \n";
//					}
//					query +=
//					"	group by a.cttt_fk \n"+
//					" )dathanhtoan on chungtu.pk_seq = dathanhtoan.cttt_fk \n"+ 
//					
//					"where chungtu.sotientt - ISNULL(dathanhtoan.tienthanhtoan, 0) > 0 \n";					
		  }
		  // Nha cung cap
		  if(this.nccId.trim().length() >0)
		  {
			  if(this.id.length() >0)
			  {
				// loaict = 3 - Chi phí khác
				// loaict = 4 - bút toán tổng hợp được xóa nợ
				  
				  query += 	" select b.pk_seq, b.pk_seq  as sochungtu, b.ngay as ngaychungtu, isnull(b.tigia,1) as tigia, \n" +
							" ( c.THUE + c.TONGTIENCHUATHUE) - isnull(thanh_toan.tongthanhtoan, '0') as tienAvat, a. tienthanhtoan as SOTIENTT, 3 as loaict \n" +
							" from ERP_XOAKHTRATRUOC_CTTT a inner join ERP_CHIPHIKHAC b on a.cttt_fk = b.pk_seq \n" +
							" inner join ERP_CHIPHIKHAC_CHITIET c on b.PK_SEQ=c.CHIPHIKHAC_FK \n"+
							" left join	\n" +
							" (	select 	TTHD.cttt_fk, sum(TTHD.TienThanhToan) as tongthanhtoan \n" +
							"	from 	ERP_XOAKHTRATRUOC_CTTT TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +
							"	where 	TT.trangthai != '2' and TTHD.XOAKHTRATRUOC_FK != '" + this.id + "' and TT.NCC_FK = "+this.nccId+" \n" +
							"			and TTHD.cttt_fk in (select cttt_fk from ERP_XOAKHTRATRUOC_CTTT where XOAKHTRATRUOC_FK = '" + this.id + "') AND TTHD.LOAICT = 3 \n" +
							"	group by cttt_fk" +
							"  )thanh_toan on a.cttt_fk = thanh_toan.cttt_fk \n" +
							"  where a.XOAKHTRATRUOC_FK = '" + this.id + "' AND A.LOAICT = 3  \n";
				  
				query +=    " union all \n";
					
			  }
			  
			  query += 		"select chungtu.pk_seq, chungtu.sochungtu, chungtu.ngaychungtu, chungtu.tigia, " +
			  		        "       chungtu.tienAvat - ISNULL(dathanhtoan.tienthanhtoan, 0) as tienAvat, \n " +
				  			"       ISNULL(dathanhtoan.tienthanhtoan, 0) as SOTIENTT, 3 as loaict \n " +
				  			
				  			"from ( " +
				  			"	select a.PK_SEQ, a.PK_SEQ as sochungtu, a.NGAY as ngaychungtu, isnull(a.tigia,1) as tigia, (b.TONGTIENCHUATHUE + b.THUE)as tienAvat \n"+
				  			"   from ERP_CHIPHIKHAC a inner join ERP_CHIPHIKHAC_CHITIET b on a.PK_SEQ=b.CHIPHIKHAC_FK \n"+
				  			"   where a.TRANGTHAI=1 and  a.LOAI=0 and a.DOITUONG= '"+this.nccId +"' AND a.TIENTE_FK = " + this.tienteId + " \n" +
				  			" 	and a.PK_SEQ not in \n"+
				  			" 	(select b.cttt_fk from  ERP_XOAKHTRATRUOC a inner join ERP_XOAKHTRATRUOC_CTTT b on a.PK_SEQ=b.xoakhtratruoc_fk where a.TRANGTHAI=0) \n";
			  if(this.id.length() > 0){
					query += " and a.pk_seq not in (select cttt_fk from ERP_XOAKHTRATRUOC_CTTT where xoakhtratruoc_fk = '" + this.id + "') ";
			  }
			  query += 		")chungtu " +
			  				"left join  " +						   
							"(  " +
							"	select a.cttt_fk, sum(a.tienthanhtoan) as tienthanhtoan \n" +
							"	from ERP_XOAKHTRATRUOC_CTTT a inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk = b.pk_seq  \n" +
							"	where b.trangthai != 2 and b.ncc_fk ='"+this.nccId +"' and b.tiente_fk = '"+ this.tienteId +"' \n";
			 
			  if(this.id.trim().length() > 0)
					query += " and b.pk_seq != '" + this.id + "' \n";
				
			  query +=		"	group by a.cttt_fk \n" +
							" ) dathanhtoan on chungtu.pk_seq = dathanhtoan.cttt_fk \n " +
							
							" where round(chungtu.tienAvat - ISNULL(dathanhtoan.tienthanhtoan, 0), 0) > 0 ";						
		  }
		  System.out.println("1.Khoi tao chi phí khác: " + query);
			
			ResultSet rsHoadon = db.get(query);
			List<IHoadon> cttList = new ArrayList<IHoadon>();
			if(rsHoadon != null)
			{
				try 
				{
					IHoadon hd = null;
					while(rsHoadon.next())
					{
						String id = rsHoadon.getString("pk_seq");
						String kyhieu = rsHoadon.getString("sochungtu");
						String sohoadon = rsHoadon.getString("sochungtu");
						String ngayhd = rsHoadon.getString("ngaychungtu");
						String tigiact = rsHoadon.getString("tigia");
						String avat = formatter.format(rsHoadon.getDouble("tienAvat"));
						String loaict = rsHoadon.getString("loaict");
						String dathanhtoan = "";
						if(this.id.length() > 0)
						{
							if(rsHoadon.getDouble("SOTIENTT") > 0)
								dathanhtoan = formatter.format(rsHoadon.getDouble("SOTIENTT"));
						}
						
						hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, dathanhtoan, "");
						hd.setLoaict(loaict);
						hd.setTigiaHD(tigiact);
						cttList.add(hd);
					}
					rsHoadon.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			this.ctttList = cttList;
	  }
	  
	  // Lấy hóa đơn Xóa tạm ứng
	  if( this.nccId.trim().length()>0 || this.nvtuId.trim().length() >0 && this.hoadonList.size() <= 0) // Khởi tạo hóa đơn
	  {
		String query="";
		if(this.nvtuId.trim().length() > 0) // Nhân viên
		{
			query = "GET_XOANONHANVIEN_HOADON "+this.nvtuId+","+((this.id == null || this.id.trim().length()==0)? "0": this.id)+","+this.tienteId+",0,0,"+this.congtyId+",'"+this.ngaychungtu+"',0 ";
//		  if(this.id.length() >0)
//		  {
//			// ERP_XOAKHTRATRUOC_HOADON : 0 - TẠM ỨNG, 2 - BÚT TOÁN TỔNG HỢP
//			  
//			  //TẠM ỨNG
//			  query += 	"select b.pk_seq, 'Tam Ung' as kyhieu, b.pk_seq as sohoadon, b.NGAYGHINHAN as ngayhoadon, isnull(b.tigia,1) as tigia , '0' as loaihd, \n" +
//						"       a.sotienTT - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVAT, a.sotientt as DaThanhToan \n" +
//						
//						"from ERP_XOAKHTRATRUOC_HOADON a inner join ERP_THANHTOANHOADON b on a.hoadon_fk = b.pk_seq \n" +
//						"     left join	\n" +
//						"	 ( 	\n" +
//						"	  select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
//						"	  from ERP_XOAKHTRATRUOC_HOADON TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +
//						"	  where TT.trangthai != '2' and TTHD.XOAKHTRATRUOC_FK != '" + this.id + "' AND TTHD.LOAIHD = 0 and TT.NHANVIEN_FK = "+this.nvtuId+" \n" +
//						"		    and TTHD.hoadon_fk in (select hoadon_fk from ERP_XOAKHTRATRUOC_HOADON where XOAKHTRATRUOC_FK = '" + this.id + "') and TT.nhanvien_fk is not null \n" +
//						" 	  group by hoadon_fk) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
//						"where a.XOAKHTRATRUOC_FK = '" + this.id + "' and a.LOAIHD = 0  \n";
//						
//				query+=	" union all \n";
//			  
//			  //BÚT TOÁN TỔNG HỢP
//			  query += 	"select b.pk_seq, '' as kyhieu, cast(b.pk_seq as nvarchar(50)) as sohoadon, b.ngaybuttoan as ngayhoadon, 1 as tigia , '2' as loaihd, \n" +
//						"       a.sotienTT - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVAT, a.sotientt as DaThanhToan \n" +
//						
//						"from 	ERP_XOAKHTRATRUOC_HOADON a inner join ERP_BUTTOANTONGHOP b on a.hoadon_fk = b.PK_SEQ \n" +
//						"     	inner join   (	SELECT BUTTOANTONGHOP_FK,NHANVIEN_FK,SUM(NO)AS SOTIENVND \n"+  
//						"                   	FROM   ERP_BUTTOANTONGHOP_CHITIET \n"+ 
//						"                   	WHERE  TAIKHOANKT_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '14100000' AND CONGTY_FK = "+this.congtyId+") \n"+
//						"						  	   AND NHANVIEN_FK IS NOT NULL AND NO > 0 AND NHANVIEN_FK = '"+this.nvtuId+"' \n"+
//						"                   	GROUP BY BUTTOANTONGHOP_FK, NHANVIEN_FK )AS PLSP  ON b.PK_SEQ= PLSP.BUTTOANTONGHOP_FK \n"+ 
//						"      left join \n"+	
//						"	   ( 	\n" +
//						"	  	select 	TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
//						"	  	from 	ERP_XOAKHTRATRUOC_HOADON TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +
//						"	  	where 	TT.trangthai != '2' and TTHD.XOAKHTRATRUOC_FK != '" + this.id + "' and TT.NHANVIEN_FK is not null and TTHD.LOAIHD = '2' and TT.NHANVIEN_FK = "+this.nvtuId+" \n" +
//						"		    	and TTHD.hoadon_fk in (select hoadon_fk from ERP_XOAKHTRATRUOC_HOADON where XOAKHTRATRUOC_FK = '" + this.id + "') \n" +
//						" 	  	group by hoadon_fk ) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
//						
//						"where a.XOAKHTRATRUOC_FK = '" + this.id + "'  \n";
//						
//						query+= " union all \n"; 
//		  }
//		  
//		  query += 		" ( select hoadon.pk_seq, 'Tam Ung' as kyhieu, hoadon.sohoadon, hoadon.ngayhoadon, hoadon.tigia, '0' as loaihd, \n" +
//						"          hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') as sotienAVAT, \n" +
//						"          isnull(dathanhtoan.DATHANHTOAN, '') as DATHANHTOAN \n" +
//						
//						" from ( \n" +
//						"      select tt.PK_SEQ, tt.PK_SEQ as sohoadon, tt.NGAYGHINHAN as ngayhoadon , isnull(tt.tigia,1) as tigia , SUM(hd.SOTIENTT) as sotienAVAT \n " +
//						"      from ERP_THANHTOANHOADON tt " +
//						"	   inner join ERP_THANHTOANHOADON_HOADON hd on tt.PK_SEQ= hd.THANHTOANHD_FK \n " +
//						"      where hd.LOAIHD = '1' AND TRANGTHAI=1 and tt.NHANVIEN_FK='" + this.nvtuId + "' and tt.TIENTE_FK = " + this.tienteId + "" +
//						"            and tt.PK_SEQ not in \n " +
//						"           (select a.hoadon_fk \n" +
//						"            from ERP_XOAKHTRATRUOC_HOADON a inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk=b.PK_SEQ \n" +
//						"            where b.TRANGTHAI=0 and b.nhanvien_fk = "+this.nvtuId+" ) \n ";
//
//		  if(this.id.length() > 0){
//		  		query += 	" and tt.pk_seq not in (select hoadon_fk from ERP_XoakhTraTruoc_HoaDon where xoakhtratruoc_fk = '" + this.id + "')  \n";
//		  }
//		  
//		  query += 		" 	  group by  tt.PK_SEQ, tt.PK_SEQ , tt.NGAYGHINHAN, isnull(tt.tigia,1) \n";
//		  query += 		" )hoadon \n"+
//			  			" left join ( \n" +
//			  			" 	select HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) as DATHANHTOAN  \n" +
//			  			"	from  \n" +
//			  			"	(  \n" +
//			  			"		select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n " +
//			  			"		from ERP_XOAKHTRATRUOC_HOADON TTHD " +
//			  			"		inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n" +
//			  			"		where TT.TRANGTHAI != 2 AND NHANVIEN_FK = "+ this.nvtuId + " AND  TTHD.LOAIHD = 0 \n";
//				
//		if(this.id.trim().length() > 0)
//			query += 	" 		and TT.pk_seq != '" + this.id + "' \n";
//			
//		 query += 		"		group by HOADON_FK  \n" +
//				
//						"		union all   \n" +
//						"		select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n " +
//						"		from ERP_THUTIEN_HOADON TTHD inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ \n " +
//						"		where  TT.TRANGTHAI != 2 AND TT.NHANVIEN_FK = "+ this.nvtuId +" AND TT.NOIDUNGTT_FK =  100003 \n" +
//						"		group by HOADON_FK  \n" +
//						"	) \n" +
//						"	HOADONDATT  group by HOADON_FK  \n" +
//						") dathanhtoan \n" +
//						"on hoadon.pk_seq = dathanhtoan.hoadon_fk \n" +
//						
//						"where round(hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'), 0) > 0 ";
//			query+=" ) \n";
//		 
//		// ERP_BUTTOANTONGHOP
//		
//		 query+=	 " union all \n"+
//					 "( \n" +
//					 "	select hoadon.pk_seq, hoadon.kyhieu, hoadon.sohoadon, hoadon.ngayhoadon, hoadon.TYGIA,  '2' as loaihd, \n" +
//					 "		   hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') as sotienAVAT, \n" +
//					 "   	   isnull(dathanhtoan.DATHANHTOAN, '') as DATHANHTOAN \n" +
//					 "	from ( \n" +
//					 "			select 	a.pk_seq, '' as kyhieu, cast(a.pk_seq as nvarchar(50)) sohoadon, ngaybuttoan as ngayhoadon, " +
//					 "			SUM(isnull(NO,0)) as sotienAVAT, 1 as TYGIA \n" +
//					 "			from 	ERP_BUTTOANTONGHOP a inner join ERP_BUTTOANTONGHOP_CHITIET b on a.PK_SEQ = b.BUTTOANTONGHOP_FK  \n" +
//					 "			where 	b.nhanvien_fk = '" + this.nvtuId + "' " +
//					 "			and trangthai in (1) and b.NO >0 and b.TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '14100000' AND CONGTY_FK = "+this.congtyId+") ";
//				
//			if(this.id.length() > 0){
//					query += " 		and pk_seq not in (select hoadon_fk from ERP_XoakhTraTruoc_HoaDon where xoakhtratruoc_fk = '" + this.id + "' )  ";
//			}
//			
//			query += 		"	group by a.pk_seq, ngaybuttoan \n"+
//							" )hoadon \n" +
//							"	left join ( \n" +
//							" 		select HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) as DATHANHTOAN  \n" +
//							"		from  \n" +
//							"		(  \n" +
//							"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN    \n" +
//							"			from ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
//							"			inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n" +
//							"			where TT.TRANGTHAI != 2 AND NHANVIEN_FK = "+ this.nvtuId + " AND  TTHD.LOAIHD = 2  ";
//								
//			if(this.id.trim().length() > 0)
//				query += 	" 			and TT.pk_seq != '" + this.id + "' ";
//			
//			query += 		"			group by HOADON_FK  \n" +
//							"		)HOADONDATT  group by HOADON_FK  \n" +
//								
//							"	)dathanhtoan on hoadon.pk_seq = dathanhtoan.hoadon_fk \n" +
//							"where round(hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'), 0) > 0 ";
//							
//						query+=	" )";
	  }  
		
	  if(this.nccId.trim().length() >0) // Nha cung cấp
	  {	
		 if(this.id.length() >0)
		 {
		   query += "select b.pk_seq, 'Tam Ung' as kyhieu, b.pk_seq as sohoadon, b.NGAYGHINHAN as ngayhoadon, isnull(b.tigia,1) as tigia , '0' as loaihd, " +
					"      b.sotienTT - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVAT, a.sotientt as DaThanhToan " +
					"from ERP_XOAKHTRATRUOC_HOADON a inner join ERP_THANHTOANHOADON b on a.hoadon_fk = b.pk_seq " +
					"     left join	" +
					"    (select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan " +
					"     from ERP_XOAKHTRATRUOC_HOADON TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ " +
					"     where TT.trangthai != '2' and TTHD.XOAKHTRATRUOC_FK != '" + this.id + "' and TT.NCC_FK = "+this.nccId+" " +
					"           and TTHD.hoadon_fk in (select hoadon_fk " +
					"                                  from ERP_XOAKHTRATRUOC_HOADON " +
					"                                  where XOAKHTRATRUOC_FK = '" + this.id + "') " +
					"     group by hoadon_fk) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk " +
					"where a.XOAKHTRATRUOC_FK = '" + this.id + "' ";
					
					query+=" union all "; 
								
		 }
			  query+= 	" ( select hoadon.pk_seq, 'Tam Ung' as kyhieu, hoadon.sohoadon, hoadon.ngayhoadon, hoadon.tigia, '0' as loaihd, " +
						"          hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') as sotienAVAT," +
						"          isnull(dathanhtoan.DATHANHTOAN, '') as DATHANHTOAN " +
						
						" from ( "+
					 	" 	   select tt.PK_SEQ, tt.PK_SEQ as sohoadon, tt.NGAYGHINHAN as ngayhoadon, isnull(tt.tigia,1) as tigia ,SUM(hd.SOTIENTT) as sotienAVAT \n"+
					 	" 	   from ERP_THANHTOANHOADON tt inner join ERP_THANHTOANHOADON_HOADON hd on tt.PK_SEQ= hd.THANHTOANHD_FK \n"+
					 	" 	   where hd.LOAIHD = '1' AND TRANGTHAI=1 and tt.NCC_FK='" + this.nccId + "' and tt.TIENTE_FK = " + this.tienteId + "" +
					 	"            and tt.PK_SEQ not in \n"+
					 	" 	        (select a.hoadon_fk" +
					 	"            from ERP_XOAKHTRATRUOC_HOADON a inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk=b.PK_SEQ" +
					 	"            where b.TRANGTHAI=0 and b.NCC_FK ="+this.nccId+" ) \n";
			  if(this.id.length() > 0){
			  	query += " 	and tt.pk_seq not in (select hoadon_fk from ERP_XoakhTraTruoc_HoaDon where xoakhtratruoc_fk = '" + this.id + "')  ";
			  }
			  query += 	" 	group by  tt.PK_SEQ, tt.PK_SEQ , tt.NGAYGHINHAN, isnull(tt.tigia,1) ";
			  query	+= 	" ) hoadon \n"+
				  		" left join ( \n" +
				  		" 	select HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) as DATHANHTOAN  \n" +
				  		"	from  \n" +
				  		"	(  \n" +
						"		select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n " +
						"		from ERP_XOAKHTRATRUOC_HOADON TTHD " +
						"		inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n" +
						"		where TT.TRANGTHAI != 2 AND TT.NCC_FK = "+ this.nccId +" \n";
					
			if(this.id.trim().length() > 0)
				query += " 		and TT.pk_seq != '" + this.id + "' \n";
					
			query += 	"		group by HOADON_FK  \n" +
					
						"		union all   \n" +
						"		select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n " +
						"		from ERP_THUTIEN_HOADON TTHD " +
						"		inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ \n " +
						"		where  TT.TRANGTHAI != 2 AND TT.NCC_FK = "+ this.nccId +" AND TT.NOIDUNGTT_FK =  100003 \n" +
						"		group by HOADON_FK  \n" +
						"	) \n" +
						"	HOADONDATT  group by HOADON_FK  \n" +
						") dathanhtoan \n" +
						"on hoadon.pk_seq = dathanhtoan.hoadon_fk \n" +
						
						"where round(hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'), 0) > 0 ";
						
					query+=	") \n";									
		  	
		  }
	     
	  	System.out.println("1.Query khoi tao hoa don (Xóa tạm ứng) : " + query);
			
		ResultSet rsHoadon = db.get(query);
		List<IHoadon> hdList = new ArrayList<IHoadon>();
		if(rsHoadon != null)
		{
				try 
				{
					IHoadon hd = null;
					while(rsHoadon.next())
					{
						String id = rsHoadon.getString("pk_seq");
						String kyhieu = rsHoadon.getString("kyhieu");
						String sohoadon = rsHoadon.getString("sohoadon");
						String ngayhd = rsHoadon.getString("ngayhoadon");
						String loaihd = rsHoadon.getString("loaihd");
						String tigiahd = rsHoadon.getString("tigia");
						String avat = formatter.format(rsHoadon.getDouble("sotienAVAT"));
						
						String dathanhtoan = "";
						if(this.id.length() > 0)
						{
							if(rsHoadon.getDouble("DaThanhToan") > 0)
								dathanhtoan = formatter.format(rsHoadon.getDouble("DaThanhToan"));
						}
						
						hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, dathanhtoan, "");
						hd.setLoaihd(loaihd);
						hd.setTigiaHD(tigiahd);
						hdList.add(hd);
					}
					rsHoadon.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			this.hoadonList = hdList;
	  }
	}
	
	
	
	public void createRs_Display() 
	{
//		Utility util = new Utility();
		
		NumberFormat formatter = new DecimalFormat("#,###,###");
		NumberFormat formatterNT = new DecimalFormat("#,###,###.##");
		
		System.out.println("this.getLoainhanvien():"+this.getLoainhanvien());
		String sql1 =  "  select PK_SEQ, MA , A.TEN nppTen from NHAPHANPHOI a where trangthai = '1' and PK_SEQ!=1  \n";	
					 
		this.nppRs = db.get(sql1);
		
		this.nccRs= db.get(" select pk_seq, ma, ten from erp_nhacungcap where trangthai=1 and duyet = '1'");
		this.nvtuRs= db.get(" select pk_seq, ma, ten from erp_nhanvien where trangthai=1 ");
		
		this.tienteRs = db.get("select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE ");
		
		if(this.ngaychungtu.trim().length()>0)
			this.ngayghiso = this.ngaychungtu;
		
		// isNPP = 0 => KHÁCH HÀNG, isNPP = 1 => NHÀ PHÂN PHỐI, isNPP = 2 => NHÂN VIÊN 
		// Khach hang tra truoc	
		//1.CHỨNG TỪ TRẢ TRƯỚC
		if(this.nppId.trim().length() > 0 && this.ctttList.size() <= 0 )
		{
			String query = "";				
			
			if(this.id.length() > 0)
			{
				// loaict = 0 - thu tiền được dùng để xóa nợ
				// loaict = 1 - bút toán tổng hợp được
				
				// LẤY RA NHỮNG THU TIỀN ĐÃ ĐƯỢC DÙNG XÓA NỢ 
				query = 	"SELECT b.pk_seq, CAST(b.pk_seq as varchar(10)) as sochungtu, b.ngaychungtu, isnull(b.tigia,1) as tigia,  \n" +
						 	"       case when '"+this.tienteId+"' = '100000' then isnull(b.sotientt,0) - isnull(thanh_toan.tongthanhtoan, '0') \n" +
						 	"            else isnull(b.sotienttNT,0) - isnull(thanh_toan.tongthanhtoan, '0') end as tienAvat, \n" +
						 	"       isnull(b.sotientt,0) - (isnull(thanh_toan.tongthanhtoan, '0')* isnull(b.tigia,1) ) as tienAvatVND, \n" +
						 	"       a.tienthanhtoan as SOTIENTT, 0 as loaict \n" +
						 	
						 	"FROM 	ERP_XOAKHTRATRUOC_CTTT a \n" +
						 	"		inner join ERP_THUTIEN b on a.cttt_fk = b.pk_seq \n" +
						 	"		left join	\n" +
						 	"		( 	" +
						 	"         select a.cttt_fk, SUM(tienthanhtoan) tongthanhtoan from ( \n"+
						 	"			select 	a.cttt_fk, sum(a.tienthanhtoan) as tienthanhtoan \n" +
							"			from 	ERP_XOAKHTRATRUOC_CTTT a \n" +
							"			inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk = b.pk_seq  \n" +
							"			where 	b.trangthai != 2 and a.loaict = 0 and b.khachhang_fk = "+this.nppId+" and b.CONGTY_FK = "+this.congtyId+" \n";				
				query += 	" 			and b.pk_seq != '" + (this.id == "" ? "0": this.id) + "' \n";			
				query +=	"   		group by a.cttt_fk \n"+
				
					        "   		UNION ALL \n"+
					        
					        
							"			select 	c.HOADON_FK cttt_fk, sum(c.SOTIENTT) as tienthanhtoan \n" +
							"			from 	ERP_THANHTOANHOADON_HOADON c \n" +
							"			inner join ERP_THANHTOANHOADON d on c.THANHTOANHD_FK = d.PK_SEQ  \n" +
							"			where 	d.trangthai not in (2,3) and c.LOAIHD = 7  and d.KHACHHANG_FK = "+this.nppId+" and d.CONGTY_FK = "+this.congtyId+" \n"+
							"   		group by c.HOADON_FK "+
							
							"			union all \n"+
							"			SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU  \n"+
							"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n"+
							"			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 3 AND BT.KH_CHUYENNO = "+this.nppId+" \n"+
							"			GROUP BY BT_KH.HOADON_FK \n"+
							"       ) a group by a.cttt_fk \n"+
							"		)thanh_toan on a.cttt_fk = thanh_toan.cttt_fk \n" +
							
							"WHERE a.XOAKHTRATRUOC_FK = '" + this.id + "' \n" +
				
				// LẤY RA NHỮNG XÓA NỢ KHÁCH HÀNG BÚT TOÁN TỔNG HỢP
							"UNION ALL \n"+
							
							" SELECT b.pk_seq, CAST(b.pk_seq as varchar(10)) as sochungtu, b.ngaychungtu ngaychungtu, 1 as tigia,  \n"+
						    "   isnull(b.CO,0) - isnull(thanh_toan.tienthanhtoan, '0') as tienAvat, \n"+
						    "   isnull(b.CO,0) - (isnull(thanh_toan.tienthanhtoan, '0') ) as tienAvatVND, \n"+
						    "   a.tienthanhtoan as SOTIENTT, 1 as loaict \n"+
						 	
							"FROM  ERP_XOAKHTRATRUOC_CTTT a " +
							"INNER JOIN \n"+							
							"( \n"+ 
							"	select	a.pk_seq, b.KHACHHANG_FK , CAST(a.pk_seq as varchar(10)) as sochungtu, NGAYBUTTOAN ngaychungtu, sum(isnull(CO,0)) as SOTIENTTNT, sum(CO) CO, 1 as tigia \n"+  
							"	from	ERP_BUTTOANTONGHOP a " +
							"	INNER JOIN ERP_BUTTOANTONGHOP_CHITIET b on a.PK_SEQ = b.BUTTOANTONGHOP_FK \n"+   
							"	where	trangthai = '1' and a.CONGTY_FK = "+this.congtyId+" \n"+			
							"			and khachhang_fk = '"+this.nppId+"' and b.CO > 0 and TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13111000' and CONGTY_FK = "+this.congtyId+") \n"+						
							"    and isnull(b.isNPP,0) = "+this.isNPP+" \n"+
							"	group by a.pk_seq, khachhang_fk, TAIKHOANKT_FK, ngaybuttoan  \n"+
							" )b ON a.cttt_fk = b.pk_seq   \n"+ 
							" left join \n"+  
							" ( \n"+  
							"	select 	a.cttt_fk, sum(a.tienthanhtoan) as tienthanhtoan \n"+ 
							"	from 	ERP_XOAKHTRATRUOC_CTTT a \n"+ 
							"	inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk = b.pk_seq \n"+  
							"	where 	b.trangthai != 2 and a.loaict = 1 and a.XOAKHTRATRUOC_FK != '" + this.id + "' \n"+
							"	and a.cttt_fk IN (	select cttt_fk from ERP_XOAKHTRATRUOC_CTTT \n" +
							"						where XOAKHTRATRUOC_FK = '" + this.id + "' and loaict = 1) and b.KHACHHANG_FK = "+this.nppId+" and isnull(b.isNPP,0) = "+this.isNPP+" \n" +
							"	group by a.cttt_fk \n"+
							" )thanh_toan on a.cttt_fk = thanh_toan.cttt_fk \n"+ 
							
							"where a.XOAKHTRATRUOC_FK = '" + this.id + "' and b.CO >0 and b.khachhang_fk = "+this.nppId+"  \n" ;
							
			}
			
			// LẤY RA THU TIỀN TRẢ TRƯỚC
			
		
			
			System.out.println("1.Khoi tao phieu thu: " + query);
			
			ResultSet rsHoadon = db.get(query);
			List<IHoadon> cttList = new ArrayList<IHoadon>();
			if(rsHoadon != null)
			{
				try 
				{
					IHoadon hd = null;
					while(rsHoadon.next())
					{
						String id = rsHoadon.getString("pk_seq");
						String kyhieu = rsHoadon.getString("sochungtu");
						String sohoadon = rsHoadon.getString("sochungtu");
						String ngayhd = rsHoadon.getString("ngaychungtu");
						String avat = formatter.format(rsHoadon.getDouble("tienAvat"));
						String avatVND = formatter.format(rsHoadon.getDouble("tienAvatVND"));
						
						String tigiahd = Double.toString(rsHoadon.getDouble("tigia"));
						
						if(!this.tienteId.equals("100000"))
							 avat = formatterNT.format(rsHoadon.getDouble("tienAvat"));
						
						String dathanhtoan = "";
						
						String loaict = rsHoadon.getString("loaict");
						
						if(this.id.length() > 0)
						{
							if(rsHoadon.getDouble("SOTIENTT") > 0)
							{
								if(this.tienteId.equals("100000"))
									dathanhtoan = formatter.format(rsHoadon.getDouble("SOTIENTT"));
								else
									dathanhtoan = formatterNT.format(rsHoadon.getDouble("SOTIENTT"));
							}
						}
						
						hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, dathanhtoan, "");
						hd.setTongtiencoVATVND(avatVND);
						hd.setTigiaHD(tigiahd);
						hd.setLoaict(loaict);
						cttList.add(hd);
					}
					rsHoadon.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			this.ctttList = cttList;
		}
		

		// LẤY DANH SÁCH HÓA ĐƠN - KHÁCH HÀNG
		if(this.nppId.length() > 0 && this.hoadonList.size() <= 0)
		{
			String query = "";
			// ERP_XOAKHTRATRUOC_HOADON : 0 - hd tài chính, 1 - hd khác, 2 - buttoantonghop
			if(this.id.length() > 0)
			{
				query = 	"SELECT b.pk_seq, cast(b.pk_seq as nvarchar(50)) as kyhieu, b.sohoadon, b.ngayxuathd as ngayhoadon, '0' as loaihd, isnull(b.TYGIA,1) as TYGIA,  \n" +
						 	"       a.SotienAVAT as sotienAVAT, " +
						 	"       (b.tongtienAVAT - isnull(thanh_toan.tongthanhtoan, '0'))* isnull(b.TYGIA,1) as sotienAVATVND, " +
						 	"       a.sotientt as DaThanhToan \n" +
						 	
						 	"FROM ERP_XOAKHTRATRUOC_HOADON a \n" +
						 	"     left join  ERP_HOADON b on a.hoadon_fk = b.pk_seq \n"+
						 	"     left join	\n" +
						 	"	  ( 	\n"+
						 	"		 SELECT THU.HOADON_FK, SUM(THU.tongthanhtoan) tongthanhtoan \n"+
						 	"		 FROM (\n"+
						 	// XÓA KHÁCH HÀNG TRẢ TRƯỚC
						 	"			select 	TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
						 	"			from 	ERP_XOAKHTRATRUOC_HOADON TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +
							"			where 	TT.trangthai != '2' and TTHD.XOAKHTRATRUOC_FK != '" + this.id + "' and TTHD.LOAIHD = '0' and TT.KHACHHANG_FK ="+this.nppId+" and isnull(TT.isnpp,0) = "+this.isNPP+" \n" +
							" 					and TTHD.hoadon_fk in (select hoadon_fk from ERP_XOAKHTRATRUOC_HOADON where XOAKHTRATRUOC_FK = '" + this.id + "') \n" +
							"			group by hoadon_fk \n" +
							
							// THU TIỀN HÓA ĐƠN
							"       UNION ALL \n"+
							"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as tongthanhtoan   \n" +
							"			from ERP_THUTIEN_HOADON TTHD " +
							"			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '0' and isnull(TTHD.isNPP,0) = "+this.isNPP+" AND TT.BANGKE_FK IS NULL \n" +
							"			group by HOADON_FK  \n" +						
							
							// THU TIỀN THEO BẢNG KÊ
							"       UNION ALL \n"+
							"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as tongthanhtoan   \n" +
							"			from ERP_THUTIEN_HOADON TTHD " +
							"			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '0' and isnull(TTHD.isNPP,0) = "+this.isNPP+" AND TT.BANGKE_FK IS NOT NULL \n" +
							"			group by HOADON_FK  \n" +		
							
							//BÙ TRỪ KHÁCH HÀNG
							"		union all \n"+							
							"			select TTHD.HOADON_FK , sum(TTHD.XOANO) as DATHANHTOAN \n"+   
							"			from ERP_BUTRUKHACHHANG_CHITIET TTHD inner join ERP_BUTRUKHACHHANG TT on TTHD.BTKH_FK = TT.PK_SEQ \n"+  
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '0' and isnull(TT.isNPP,0) = "+this.isNPP+" \n"+ 
							"			group by HOADON_FK \n"+ 
							"			) THU \n"+
							"		 GROUP BY THU.HOADON_FK \n"+
							"	  )thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
							
							"where a.XOAKHTRATRUOC_FK = '" + this.id + "' and a.LOAIHD= '0' and b.LOAIHOADON = 0 \n";
				
					// HÓA ĐƠN KHÁC		
					query +=" union all \n"+
				
							"SELECT b.pk_seq, b.kyhieuhoadon as kyhieu, b.sohoadon, b.ngayhoadon, '1' as loaihd, 1 as TYGIA,  \n" +
						 	"       a.sotienavat as sotienAVAT, " +
						 	"       PLSP.SOTIENVND - isnull(thanh_toan.DATHANHTOAN, '0') as sotienAVATVND," +
						 	"       a.sotientt as DaThanhToan \n" +
						 	
						 	"FROM ERP_XOAKHTRATRUOC_HOADON a \n" +
						 	"LEFT JOIN ERP_HOADONPHELIEU b on a.hoadon_fk = b.pk_seq \n" +
							"INNER JOIN ( SELECT HOADONPHELIEU_FK, SUM(THANHTIEN)AS SOTIENVND  \n" +
							"             FROM ERP_HOADONPHELIEU_SANPHAM \n" +
							"             GROUP BY HOADONPHELIEU_FK ) AS PLSP  ON b.PK_SEQ= PLSP.HOADONPHELIEU_FK \n" +
						 	"LEFT JOIN (	\n" +
						 	" 		SELECT HOADON_FK, SUM( ISNULL(DATHANHTOAN, 0) ) as DATHANHTOAN  \n" +
							"		FROM  \n" +
							"		(  \n" +
							// THU TIỀN KHÔNG THEO BẢNG KÊ
							"			SELECT TTHD.HOADON_FK , sum( TTHD.SOTIENTT ) as DATHANHTOAN   \n" +
							"			FROM ERP_THUTIEN_HOADON TTHD \n" +
							"			INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			WHERE  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON= '1' \n"+
							"			GROUP BY HOADON_FK  \n" +	
							
							// TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
							"           UNION ALL \n"+
							
							"			SELECT TTHD.HOADON_FK , sum( TTHD.SOTIENTT ) as DATHANHTOAN    \n" +
							"			FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
							"			INNER JOIN ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n" +
							"			WHERE TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '1' \n"+							
			 				" 			AND TT.pk_seq != '" + (this.id == "" ? "0": this.id) + "' \n"+			
							"			GROUP BY HOADON_FK  \n" +
							
							// BÙ TRỪ KHÁCH HÀNG
							"           UNION ALL \n"+
							
							"			SELECT BT_KH.HOADON_FK, SUM( BT_KH.XOANO ) AS DATHANHTOAN  \n"+
							"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n "+
							"			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 1 \n"+
							"			GROUP BY BT_KH.HOADON_FK \n"+		
							
							"		)HOADONDATT  group by HOADON_FK  \n" +	
							")thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
							
							"WHERE a.XOAKHTRATRUOC_FK = '" + this.id + "' and a.LOAIHD= '1' \n";
							
					// BÚT TOÁN TỔNG HỢP
					query +=" union all \n"+
				
							" select 	b.pk_seq, '' as kyhieu, cast(b.pk_seq as nvarchar(50)) sohoadon, b.NGAYBUTTOAN, '2' as loaihd, 1 as TYGIA, \n"+  
						    "   		a.sotienAvat as sotienAVAT, \n"+ 
						    "   		PLSP.SOTIENVND  - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVATVND, \n"+
						    "    		a.sotientt as DaThanhToan \n"+ 
						    
							" from 		ERP_XOAKHTRATRUOC_HOADON a \n"+ 
							"     		inner join ERP_BUTTOANTONGHOP b on a.hoadon_fk = b.PK_SEQ \n"+ 
							"     		inner join   (	SELECT BUTTOANTONGHOP_FK,KHACHHANG_FK,SUM(NO)AS SOTIENVND \n"+  
							"                   		FROM   ERP_BUTTOANTONGHOP_CHITIET \n"+ 
							"                   		WHERE  TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13111000' and CONGTY_FK = "+this.congtyId+") \n"+
							"						  			AND KHACHHANG_FK IS NOT NULL AND NO>0 AND KHACHHANG_FK = '"+this.nppId+"' AND isnull(isNPP,0) = "+this.isNPP+" \n"+
							"                   		GROUP BY BUTTOANTONGHOP_FK, KHACHHANG_FK)AS PLSP  ON b.PK_SEQ= PLSP.BUTTOANTONGHOP_FK \n"+ 
							"     left join \n"+	
							"		( 	select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n"+ 
							"			from ERP_XOAKHTRATRUOC_HOADON TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n"+ 
							"			where TT.trangthai != '2' and TTHD.XOAKHTRATRUOC_FK != '" +this.id + "' and TTHD.LOAIHD = '2' and TT.KHACHHANG_FK = "+this.nppId+" AND isnull(TT.isNPP,0) = "+this.isNPP+" \n"+ 
							" 			and TTHD.hoadon_fk in (select hoadon_fk from ERP_XOAKHTRATRUOC_HOADON where XOAKHTRATRUOC_FK = '"+ this.id + "') \n"+ 
							"			group by hoadon_fk \n"+
							"		)thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n"+ 
							
							"	where a.XOAKHTRATRUOC_FK = '" +this.id + "' and a.LOAIHD = '2' \n";
							
					query+=	" union all ";
					
					// HÓA ĐƠN HÀNG TRẢ VỀ
					
					query += "select b.pk_seq, b.KYHIEU as kyhieu, b.sohoadon, b.ngayxuathd as ngayhoadon, '7' as loaihd, isnull(b.TYGIA,1) as TYGIA,  \n" +
						 	"      a.SotienAVAT as sotienAVAT, " +
						 	"       ((b.tongtienAVAT - isnull(thanh_toan.tongthanhtoan, '0'))* isnull(b.TYGIA,1))*(-1) as sotienAVATVND, " +
						 	"       a.sotientt*(-1) as DaThanhToan \n" +
						 	
						 	"from ERP_XOAKHTRATRUOC_HOADON a \n" +
						 	"     left join ERP_HOADON b on a.hoadon_fk = b.pk_seq \n" +
						 	"     left join	\n" +
						 	"	  ( 	\n"+
						 	"		 SELECT THU.HOADON_FK, SUM(THU.tongthanhtoan) tongthanhtoan \n"+
						 	"		 FROM (\n"+
						 	// XÓA KHÁCH HÀNG TRẢ TRƯỚC
						 	"			select 	TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
						 	"			from 	ERP_XOAKHTRATRUOC_HOADON TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +
							"			where 	TT.trangthai != '2' and TTHD.XOAKHTRATRUOC_FK != '" + this.id + "' and TTHD.LOAIHD = '7' and TT.KHACHHANG_FK = "+this.nppId+" and isnull(TT.isnpp,0) = "+this.isNPP+" \n" +
							" 					and TTHD.hoadon_fk in (select hoadon_fk from ERP_XOAKHTRATRUOC_HOADON where XOAKHTRATRUOC_FK = '" + this.id + "') \n" +
							"			group by hoadon_fk \n" +
							
							// THU TIỀN HÓA ĐƠN
							"       UNION ALL \n"+
							"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as tongthanhtoan   \n" +
							"			from ERP_THUTIEN_HOADON TTHD " +
							"			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7' and isnull(TTHD.isNPP,0) = "+this.isNPP+" AND TT.BANGKE_FK IS NULL \n" +
							"			group by HOADON_FK  \n" +						
							
							// THU TIỀN THEO BẢNG KÊ
							"       UNION ALL \n"+
							"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as tongthanhtoan   \n" +
							"			from ERP_THUTIEN_HOADON TTHD " +
							"			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7' and isnull(TTHD.isNPP,0) = "+this.isNPP+" AND TT.BANGKE_FK IS NOT NULL \n" +
							"			group by HOADON_FK  \n" +		
							
							//BÙ TRỪ KHÁCH HÀNG
							"		UNION ALL \n"+
							
							"			select TTHD.HOADON_FK , sum(TTHD.XOANO) as DATHANHTOAN \n"+   
							"			from ERP_BUTRUKHACHHANG_CHITIET TTHD inner join ERP_BUTRUKHACHHANG TT on TTHD.BTKH_FK = TT.PK_SEQ \n"+  
							"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7' and isnull(TT.isNPP,0) = "+this.isNPP+" \n"+ 
							"			group by HOADON_FK \n"+ 
							
							"		UNION ALL \n"+		
							
							// THANH TOÁN HÓA ĐƠN
							"			SELECT TT_HD.HOADON_FK,SUM(TT_HD.THANHTOAN) AS THANHTOAN  \n"+
							"			FROM   ERP_THANHTOANHOADON TT INNER JOIN ERP_THANHTOANHOADON_HOADON_PHANBO TT_HD ON TT.PK_SEQ = TT_HD.TTHD_FK \n "+
							"			WHERE  TT.TRANGTHAI NOT IN (2) AND TT_HD.LOAIHD = 8 AND TT_HD.KHACHHANG_FK = "+this.nppId+" \n"+
							"			GROUP BY TT_HD.HOADON_FK \n"+
							
							"			) THU \n"+
							"		 GROUP BY THU.HOADON_FK \n"+
							"	  )thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
							
							"where a.XOAKHTRATRUOC_FK = '" + this.id + "' and a.LOAIHD= '7' and b.LOAIHOADON = 2 \n";
					
					query+= " UNION ALL \n";
					
					query+= 
							" (SELECT bt.PK_SEQ, '' KYHIEU ,  CAST(bt.PK_SEQ as nvarchar(50)) SOHOADON , BT.NGAYBUTRU , '4' AS LOAIHD, bt.tigia as TYGIA, \n"+
						    "    BTHD.SOTIENAVAT AS SOTIENGOC, \n"+
						    "    (BT.TONGTIEN - ISNULL(DATHANHTOAN.DATHANHTOAN,0))*(BT.TIGIA) AS SOTIENVND,  BTHD.SOTIENTT AS DATHANHTOAN \n"+
						    " FROM ERP_BUTRUKHACHHANG BT  \n"+
						    " INNER JOIN NHAPHANPHOI KH ON BT.KH_NHANNO = KH.PK_SEQ \n"+
						    " INNER JOIN ERP_XOAKHTRATRUOC_HOADON BTHD ON BT.PK_SEQ = BTHD.HOADON_FK AND BTHD.LOAIHD = '4' \n"+
						    " LEFT JOIN ( \n"+
							"				SELECT DATHANHTOAN.HOADON_FK, SUM(DATHANHTOAN.DATHANHTOAN) DATHANHTOAN \n"+
							"				FROM \n"+
							"				( \n"+
							"					SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
							"					FROM 	ERP_THUTIEN_HOADON TTHD \n"+
							"							INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
							"					WHERE TTHD.LOAIHOADON= '4' AND TT.TRANGTHAI NOT IN (2) \n"+
							"					GROUP BY TTHD.HOADON_FK \n"+
												
							"					UNION ALL \n"+
												
							"					SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
							"					FROM 	ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
							"							INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.xoakhtratruoc_fk = TT.PK_SEQ \n"+
							"					WHERE TTHD.LOAIHD= '4' AND TT.TRANGTHAI NOT IN (2) \n"+
							" 					AND TT.pk_seq != '" + (this.id == "" ? "0": this.id) + "' \n"+			
							"					GROUP BY TTHD.HOADON_FK \n"+
							"				) DATHANHTOAN \n"+
							"				GROUP BY HOADON_FK \n"+
											
							"			  ) DATHANHTOAN ON BT.pk_seq = DATHANHTOAN.HOADON_FK \n"+
							" WHERE BT.TRANGTHAI = 1 \n"+ 
							" AND (BT.TONGTIEN - ISNULL(DATHANHTOAN.DATHANHTOAN,0))*(BT.TIGIA) > 0 \n"+
							" AND BT.KH_NHANNO  = '" + this.nppId + "'  "+	
							" AND BTHD.xoakhtratruoc_fk = "+(this.id == "" ? "0": this.id) + " and LOAIHD= '4'   "+
							" ) \n";
					

			}
				
		
						
			System.out.println("  1.Query khoi tao hoa don: " + query);
			
			ResultSet rsHoadon = db.get(query);
			List<IHoadon> hdList = new ArrayList<IHoadon>();
			if(rsHoadon != null)
			{
				try 
				{
					IHoadon hd = null;
					while(rsHoadon.next())
					{
						String id = rsHoadon.getString("pk_seq");
						String kyhieu = rsHoadon.getString("kyhieu");
						String sohoadon = rsHoadon.getString("sohoadon");
						String ngayhd = rsHoadon.getString("ngayhoadon");
						String loaihd = rsHoadon.getString("loaihd");
						String tigiahd = rsHoadon.getString("TYGIA");
						String avat = formatter.format(rsHoadon.getDouble("sotienAVAT"));
						String avatVND = formatter.format(rsHoadon.getDouble("sotienAVATVND"));
						
						if(!this.tienteId.equals("100000"))
							avat = formatterNT.format(rsHoadon.getDouble("sotienAVAT"));
						
						String dathanhtoan = "";
						if(this.id.length() > 0)
						{
							if(rsHoadon.getDouble("DaThanhToan") != 0)
							{
								if(!this.tienteId.equals("100000"))
								   dathanhtoan = formatterNT.format(rsHoadon.getDouble("DaThanhToan"));
								else
							       dathanhtoan = formatter.format(rsHoadon.getDouble("DaThanhToan"));
							}
						}
						

						System.out.println("BACNH:"+dathanhtoan);
						
						hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, dathanhtoan, "");
						hd.setTigiaHD(tigiahd);
						hd.setTienteId(this.tienteId);
						hd.setLoaihd(loaihd);
						hd.setTongtiencoVATVND(avatVND);
						hdList.add(hd);
					}
					rsHoadon.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			this.hoadonList = hdList;
		
		}
		
// Xóa tạm ứng
// Lấy chúng từ trả trước
	  if(nppId.trim().length() <=0 &&  this.ctttList.size() <= 0 &&  this.nvtuId.trim().length() >0 || this.nccId.trim().length() >0) // Load chứng từ trả trước: chi phí khác
	  {
		  //Nhân viên
		  String query="";
		  if(this.nvtuId.trim().length() >0)
		  {
			  query = "GET_XOANONHANVIEN_CTTT "+this.nvtuId+","+((this.id == null || this.id.trim().length()==0)? "0": this.id)+","+this.tienteId+",0,0,"+this.congtyId+",'"+this.ngaychungtu+"', 1";
//			  if(this.id.length() >0)
//			  {
//				// loaict = 3 - Chi phí khác
//				// loaict = 4 - bút toán tổng hợp được xóa nợ
//				  
//				  query = 	"select b.pk_seq, b.pk_seq  as sochungtu, b.ngay as ngaychungtu, isnull(b.TIGIA,1) as tigia, \n " +
//							"      a.tienchungtu as tienAvat," +
//							"      a. tienthanhtoan as SOTIENTT, 3 as loaict \n " +
//							
//							"from ERP_XOAKHTRATRUOC_CTTT a " +
//							"    inner join ERP_CHIPHIKHAC b on a.cttt_fk = b.pk_seq \n " +
//							"    inner join ERP_CHIPHIKHAC_CHITIET c on b.PK_SEQ = c.CHIPHIKHAC_FK \n "+
//							"    left join	\n " +
//							"	( 	select 	TTHD.cttt_fk, sum(TTHD.TienThanhToan) as tongthanhtoan \n " +
//							"		from 	ERP_XOAKHTRATRUOC_CTTT TTHD " +
//							"   		 	inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n " +
//							"		where 	TT.trangthai != '2' and TTHD.loaict = 3 and TTHD.XOAKHTRATRUOC_FK != '" + this.id + "' and TT.nhanvien_fk ="+this.nvtuId+" \n " +
//							"		      	and TTHD.cttt_fk in (select cttt_fk from ERP_XOAKHTRATRUOC_CTTT where XOAKHTRATRUOC_FK = '" + this.id + "' and loaict = 3 ) \n " +
//							"		group by cttt_fk " +
//							"	)thanh_toan on a.cttt_fk = thanh_toan.cttt_fk \n " +
//							
//							"where a.XOAKHTRATRUOC_FK = '" + this.id + "'   \n ";
//							
//							query+=	"union all \n "+					
//					
//							"select b.BUTTOANTONGHOP_FK pk_seq, CAST(c.pk_seq as varchar(10)) as sochungtu, c.ngaybuttoan ngaychungtu, 1 as tigia,  \n" +
//						 	"      a.tienchungtu as tienAvat, \n" +
//						 	"       a.tienthanhtoan as SOTIENTT, 4 as loaict \n" +
//							" from  ERP_XOAKHTRATRUOC_CTTT a INNER JOIN \n"+
//							"		ERP_BUTTOANTONGHOP_CHITIET b ON a.cttt_fk = b.buttoantonghop_fk \n"+ 
//							"		INNER JOIN ERP_BUTTOANTONGHOP c ON b.buttoantonghop_fk = c.pk_seq \n"+
//							" 		left join \n"+  
//							" 		( \n"+  
//							"			select 	a.cttt_fk, sum(a.tienthanhtoan) as tienthanhtoan \n"+ 
//							"			from 	ERP_XOAKHTRATRUOC_CTTT a \n"+ 
//							"					inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk = b.pk_seq \n"+  
//							"			where 	b.trangthai != 2 and a.loaict = 4 and a.XOAKHTRATRUOC_FK != '" + this.id + "' and b.nhanvien_fk = "+this.nvtuId+" \n"+
//							"					and a.cttt_fk IN (	select cttt_fk from ERP_XOAKHTRATRUOC_CTTT \n" +
//							"   									where XOAKHTRATRUOC_FK = '" + this.id + "' and loaict = 4) \n" +
//							"			group by a.cttt_fk \n"+
//							" 		)thanh_toan on a.cttt_fk = thanh_toan.cttt_fk \n"+ 
//							"where a.XOAKHTRATRUOC_FK = '" + this.id + "' and b.CO > 0 and b.nhanvien_fk = "+this.nvtuId+" and b.TAIKHOANKT_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '14100000' AND CONGTY_FK = "+this.congtyId+") \n";
//					
//				  
//			  }
			  
				
		  }
		  // Nha cung cap
		  if(this.nccId.trim().length() >0)
		  {
			  if(this.id.length() >0)
			  {
				// loaict = 3 - Chi phí khác
				// loaict = 4 - bút toán tổng hợp được xóa nợ
				  
				  query += 	" select b.pk_seq, b.pk_seq  as sochungtu, b.ngay as ngaychungtu, isnull(b.tigia,1) as tigia, \n" +
							" ( c.THUE + c.TONGTIENCHUATHUE) - isnull(thanh_toan.tongthanhtoan, '0') as tienAvat, a. tienthanhtoan as SOTIENTT, 3 as loaict \n" +
							" from ERP_XOAKHTRATRUOC_CTTT a inner join ERP_CHIPHIKHAC b on a.cttt_fk = b.pk_seq \n" +
							" inner join ERP_CHIPHIKHAC_CHITIET c on b.PK_SEQ=c.CHIPHIKHAC_FK \n"+
							" left join	\n" +
							" (	select 	TTHD.cttt_fk, sum(TTHD.TienThanhToan) as tongthanhtoan \n" +
							"	from 	ERP_XOAKHTRATRUOC_CTTT TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +
							"	where 	TT.trangthai != '2' and TTHD.XOAKHTRATRUOC_FK != '" + this.id + "' and TT.NCC_FK = "+this.nccId+" \n" +
							"			and TTHD.cttt_fk in (select cttt_fk from ERP_XOAKHTRATRUOC_CTTT where XOAKHTRATRUOC_FK = '" + this.id + "') AND TTHD.LOAICT = 3 \n" +
							"	group by cttt_fk" +
							"  )thanh_toan on a.cttt_fk = thanh_toan.cttt_fk \n" +
							"  where a.XOAKHTRATRUOC_FK = '" + this.id + "' AND A.LOAICT = 3  \n";
				  
					
			  }
					
		  }
		  System.out.println("1.Khoi tao chi phí khác: " + query);
			
			ResultSet rsHoadon = db.get(query);
			List<IHoadon> cttList = new ArrayList<IHoadon>();
			if(rsHoadon != null)
			{
				try 
				{
					IHoadon hd = null;
					while(rsHoadon.next())
					{
						String id = rsHoadon.getString("pk_seq");
						String kyhieu = rsHoadon.getString("sochungtu");
						String sohoadon = rsHoadon.getString("sochungtu");
						String ngayhd = rsHoadon.getString("ngaychungtu");
						String tigiact = rsHoadon.getString("tigia");
						String avat = formatter.format(rsHoadon.getDouble("tienAvat"));
						String loaict = rsHoadon.getString("loaict");
						String dathanhtoan = "";
						if(this.id.length() > 0)
						{
							if(rsHoadon.getDouble("SOTIENTT") > 0)
								dathanhtoan = formatter.format(rsHoadon.getDouble("SOTIENTT"));
						}
						
						hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, dathanhtoan, "");
						hd.setLoaict(loaict);
						hd.setTigiaHD(tigiact);
						cttList.add(hd);
					}
					rsHoadon.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			this.ctttList = cttList;
	  }
	  
	  // Lấy hóa đơn Xóa tạm ứng
	  if( this.nccId.trim().length()>0 || this.nvtuId.trim().length() >0 && this.hoadonList.size() <= 0) // Khởi tạo hóa đơn
	  {
		String query="";
		if(this.nvtuId.trim().length() > 0) // Nhân viên
		{
			  query = "GET_XOANONHANVIEN_HOADON "+this.nvtuId+","+((this.id == null || this.id.trim().length()==0)? "0": this.id)+","+this.tienteId+",0,0,"+this.congtyId+",'"+this.ngaychungtu+"', 1";
//		  if(this.id.length() >0)
//		  {
//			// ERP_XOAKHTRATRUOC_HOADON : 0 - TẠM ỨNG, 2 - BÚT TOÁN TỔNG HỢP
//			  
//			  //TẠM ỨNG
//			  query += 	"select b.pk_seq, 'Tam Ung' as kyhieu, b.pk_seq as sohoadon, b.NGAYGHINHAN as ngayhoadon, isnull(b.tigia,1) as tigia , '0' as loaihd, \n" +
//						"       a.sotienAVAT as sotienAVAT, a.sotientt as DaThanhToan \n" +
//						
//						"from ERP_XOAKHTRATRUOC_HOADON a inner join ERP_THANHTOANHOADON b on a.hoadon_fk = b.pk_seq \n" +
//						"     left join	\n" +
//						"	 ( 	\n" +
//						"	  select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
//						"	  from ERP_XOAKHTRATRUOC_HOADON TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +
//						"	  where  XOAKHTRATRUOC_FK = '" + this.id + "' and TT.nhanvien_fk is not null \n" +
//						" 	  group by hoadon_fk" +
//						"    ) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
//						"where a.XOAKHTRATRUOC_FK = '" + this.id + "' and a.LOAIHD = 0  \n";
//						
//				query+=	" union all \n";
//			  
//			  //BÚT TOÁN TỔNG HỢP
//			  query += 	"select b.pk_seq, '' as kyhieu, cast(b.pk_seq as nvarchar(50)) as sohoadon, b.ngaybuttoan as ngayhoadon, 1 as tigia , '2' as loaihd, \n" +
//						"       a.sotienAVAT as sotienAVAT, a.sotientt as DaThanhToan \n" +
//						
//						"from 	ERP_XOAKHTRATRUOC_HOADON a inner join ERP_BUTTOANTONGHOP b on a.hoadon_fk = b.PK_SEQ \n" +
//						"     	inner join   (	SELECT BUTTOANTONGHOP_FK,NHANVIEN_FK,SUM(CO)AS SOTIENVND \n"+  
//						"                   	FROM   ERP_BUTTOANTONGHOP_CHITIET \n"+ 
//						"                   	WHERE  TAIKHOANKT_FK = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '14100000' AND CONGTY_FK = "+this.congtyId+") \n"+
//						"						  	   AND NHANVIEN_FK IS NOT NULL AND CO > 0 AND NHANVIEN_FK = '"+this.nvtuId+"' \n"+
//						"                   	GROUP BY BUTTOANTONGHOP_FK, NHANVIEN_FK )AS PLSP  ON b.PK_SEQ= PLSP.BUTTOANTONGHOP_FK \n"+ 
//						"      left join \n"+	
//						"	   ( 	\n" +
//						"	  	select 	TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
//						"	  	from 	ERP_XOAKHTRATRUOC_HOADON TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +
//						"	  	where 	 XOAKHTRATRUOC_FK = '" + this.id + "'" +
//						" 	  	group by hoadon_fk ) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
//						
//						"where a.XOAKHTRATRUOC_FK = '" + this.id + "'  \n";
//						
//
//		  }
		  
		
	  }  
		
	  if(this.nccId.trim().length() >0) // Nha cung cấp
	  {	
		 if(this.id.length() >0)
		 {
		   query += "select b.pk_seq, 'Tam Ung' as kyhieu, b.pk_seq as sohoadon, b.NGAYGHINHAN as ngayhoadon, isnull(b.tigia,1) as tigia , '0' as loaihd, " +
					"      b.sotienTT - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVAT, a.sotientt as DaThanhToan " +
					"from ERP_XOAKHTRATRUOC_HOADON a inner join ERP_THANHTOANHOADON b on a.hoadon_fk = b.pk_seq " +
					"     left join	" +
					"    (select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan " +
					"     from ERP_XOAKHTRATRUOC_HOADON TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ " +
					"     where TT.trangthai != '2' and TTHD.XOAKHTRATRUOC_FK != '" + this.id + "' and TT.NCC_FK = "+this.nccId+" " +
					"           and TTHD.hoadon_fk in (select hoadon_fk " +
					"                                  from ERP_XOAKHTRATRUOC_HOADON " +
					"                                  where XOAKHTRATRUOC_FK = '" + this.id + "') " +
					"     group by hoadon_fk) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk " +
					"where a.XOAKHTRATRUOC_FK = '" + this.id + "' ";
					
					
		 }
			
		  }
	     
	  	System.out.println("1.Query khoi tao hoa don (Xóa tạm ứng) : " + query);
			
		ResultSet rsHoadon = db.get(query);
		List<IHoadon> hdList = new ArrayList<IHoadon>();
		if(rsHoadon != null)
		{
				try 
				{
					IHoadon hd = null;
					while(rsHoadon.next())
					{
						String id = rsHoadon.getString("pk_seq");
						String kyhieu = rsHoadon.getString("kyhieu");
						String sohoadon = rsHoadon.getString("sohoadon");
						String ngayhd = rsHoadon.getString("ngayhoadon");
						String loaihd = rsHoadon.getString("loaihd");
						String tigiahd = rsHoadon.getString("tigia");
						String avat = formatter.format(rsHoadon.getDouble("sotienAVAT"));
						
						String dathanhtoan = "";
						if(this.id.length() > 0)
						{
							if(rsHoadon.getDouble("DaThanhToan") > 0)
								dathanhtoan = formatter.format(rsHoadon.getDouble("DaThanhToan"));
						}
						
						hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, dathanhtoan, "");
						hd.setLoaihd(loaihd);
						hd.setTigiaHD(tigiahd);
						hdList.add(hd);
					}
					rsHoadon.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			this.hoadonList = hdList;
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

	
	public String getCttratruocId() 
	{
		return this.ctttId;
	}

	public void setCttratruocId(String ctttId) 
	{
		this.ctttId = ctttId;
	}

	public ResultSet getCttratruocRs() 
	{
		return this.ctttRs;
	}

	public void setCttratruocRs(ResultSet ctttRs)
	{
		this.ctttRs = ctttRs;
	}

	public String getCtttIds() 
	{
		return this.ctttIds;
	}

	public void setCtttIds(String ctttIds) 
	{
		this.ctttIds = ctttIds;
	}

	public List<IHoadon> getCtttList() 
	{
		return this.ctttList;
	}

	public void setCtttList(List<IHoadon> hoadonRs) 
	{
		this.ctttList = hoadonRs;
	}

	public String getTigia()
	{
		return this.tigia ;
	}

	public void setTigia(String tigia) 
	{
		if(tigia.length() == 0){
			String query = "SELECT TIGIAQUYDOI FROM ERP_TIGIA WHERE TUNGAY <= '" + this.ngaychungtu + "' AND DENNGAY >= '" + this.ngaychungtu + "' AND TIENTE_FK = " + this.tienteId + "";
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			try{
				if(rs != null){
					rs.next();
					this.tigia = rs.getString("TIGIAQUYDOI");
					rs.close();
				}
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		}else{
			this.tigia = tigia;
		}
	}

	public String getSotienttVND() 
	{
		return this.sotienttVND;
	}

	public void setSotienttVND(String sotienttVND) 
	{
		this.sotienttVND = sotienttVND ;
	}

	public String getTongthanhtoanVND() 
	{
		return this.tongthanhtoanVND ;
	}

	public void setTongthanhtoanVND(String tongthanhtoanVND) 
	{
		this.tongthanhtoanVND = tongthanhtoanVND ;
	}

	public String getChenhlechVND() 
	{
		return this.chenhlechVND;
	}

	public void setChenhlechVND(String chenhlechVND) 
	{
		this.chenhlechVND = chenhlechVND ;
	}

	public boolean chotTTHD(String userId, String id) 
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			// KT HỎI LẠI A PHƯƠNG SAU
			
			// KT CHENH LECH > 0 THÌ KHÔNG CHO CHOT
			String sql = "select count(*) kt from ERP_XOAKHTRATRUOC where ISNULL(CHENHLECH,0) != 0 AND PK_SEQ =  "+ id;
			ResultSet rs = db.get(sql);
			int count = 0;
			if(rs!= null)
			{
				while(rs.next())
				{
					count = rs.getInt("kt");
				}
			}
			
			if(count > 0)
			{
				this.msg = "Tổng số tiền ứng trước và Tổng tiền thanh toán phải bằng nhau. Vui lòng kiểm tra lại";
				db.getConnection().rollback();
				return false;
			}
			
			db.update("update ERP_XOAKHTRATRUOC set trangthai = '1' where pk_seq = '" + id + "' and trangthai=0");
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		} 
		catch (SQLException e)
		{ 
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			db.shutDown(); 
			return false; 
		}
	}

	public String getTungay() {
		return this.tungay;
	}

	public void setTungay(String Tungay) {
		this.tungay = Tungay;
	}

	public String getDenngay() {
		return this.denngay;
	}

	public void setDenngay(String Denngay) {
		this.denngay = Denngay;
	}
	
	public String getTungaytk() {
		return this.tungaytk;
	}

	public void setTungaytk(String Tungaytk) {
		this.tungaytk = Tungaytk;
	}

	public String getDenngaytk() {
		return this.denngaytk;
	}
	
	public void setDenngaytk(String Denngaytk) {
		this.denngaytk = Denngaytk;
	}

	public String getmaphieutk() {
		return this.maphieutk;
	}

	public void setmaphieutk(String maphieutk) {
		this.maphieutk = maphieutk;
	}

	public String getkhachhangtk() {
		return this.khachhangtk;
	}

	public void setkhachhangtk(String khachhangtk) {
		this.khachhangtk = khachhangtk;
	}

	public String getkenhbanhangtk() {
		return this.kenhbanhangtk;
	}

	public void setkenhbanhangtk(String kenhbanhangtk) {
		this.kenhbanhangtk = kenhbanhangtk;
	}

	public String getnhomkhachhangtk() {
		return this.nhomkhachhangtk;
	}
	
	public void setnhomkhachhangtk(String nhomkhachhangtk) {
		this.nhomkhachhangtk = nhomkhachhangtk;
	}

	public String getsotientk() {
		return this.sotientk;
	}

	public void setsotientk(String sotientk) {
		this.sotientk = sotientk;
	}
	
	public String gettrangthaitk() {
		return this.trangthaitk;
	}

	public void settrangthaitk(String trangthaitk) {
		this.trangthaitk = trangthaitk;
	}
	
	public String getCongtyId() {
		return this.congtyId;
	}

	public void setCongtyId(String CongtyId) {
		this.congtyId = CongtyId;
	}
	
	public String getLoaikh() {
		return this.loaikh;
	}
	
	public void setLoaikh(String Loaikh) {
		this.loaikh = Loaikh;
	}
	
	public String getKHten() {
		return this.khTen;
	}
	
	public void setKHten(String KHten) {
		this.khTen = KHten;
	}
	
	public String getisNPP() {
		return this.isNPP;
	}

	public void setisNPP(String isNPP) {
		this.isNPP = isNPP;
	}

	public String getNppIdGoc() {
		return this.nppIdgoc;
	}
	
	public void setNppIdGoc(String nppIdGoc) {
		this.nppIdgoc = nppIdGoc;
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
	
	public String getnppdangnhap() {
		return this.nppdangnhap;
	}

	public void setnppdangnhap(String nppdangnhap) {
		this.nppdangnhap = nppdangnhap;
	}
}