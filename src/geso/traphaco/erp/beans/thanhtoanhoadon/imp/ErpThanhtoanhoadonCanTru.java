package geso.traphaco.erp.beans.thanhtoanhoadon.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpThanhtoanhoadonCanTru
{
	String userId;
	String id;
	String ngayghinhan;
	
	String nccId;
	String diachi;
	ResultSet nccRs;
	String htttId;
	ResultSet htttRs;
	String nganhangId;
	ResultSet nganhangRs;
	String chinhanhId;
	ResultSet chinhanhRs;
	
	String sotaikhoan;
	String noidungtt;
	String sotientt;
	String tthdCurrent = "";
	
	String tthdIds = "";
	
	String tienteId;
	String hoadonId;
	List<IHoadon> hoadonList;
	
	String msg;
	dbutils db;
	
	String DoiTuongTamUng;
	String LoaiThanhToan;
	String NhanvienId;
	String TenHienThi="";
	String congtyId = "";
	String nppdangnhap = "";
	
	String isNPP = "";
	
	public ErpThanhtoanhoadonCanTru()
	{
		this.id = "";
		this.ngayghinhan = "";
		this.nccId = "";
		this.diachi = "";
		this.htttId = "";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.sotaikhoan = "";
		this.noidungtt = "";
		this.sotientt = "";
		this.hoadonId = "";
		this.NhanvienId="";
		this.TenHienThi="";
		this.msg = "";
		this.tienteId = "";
		this.tthdIds = "";
		this.congtyId = "";
		this.nppdangnhap = "";
		this.isNPP = "";
		this.hoadonList = new ArrayList<IHoadon>();
		
			DoiTuongTamUng="1";
			LoaiThanhToan="1";
		this.db = new dbutils();
	}
	
	public ErpThanhtoanhoadonCanTru(String id)
	{
		this.id = id;
		this.ngayghinhan = "";
		this.nccId = "";
		this.diachi = "";
		this.htttId = "";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.sotaikhoan = "";
		this.noidungtt = "";
		this.sotientt = "";
		this.hoadonId = "";
		this.msg = "";
		this.tienteId = "";
		DoiTuongTamUng="";
		LoaiThanhToan="";
		this.tthdIds = "";
		this.nppdangnhap = "";
		this.congtyId = "";
		this.isNPP = "";
		this.hoadonList = new ArrayList<IHoadon>();
		
		this.db = new dbutils();
	}
	
	public String getTthdCurrent() {
		return tthdCurrent;
	}

	public void setTthdCurrent(String tthdCurrent) {
		this.tthdCurrent = tthdCurrent;
	}

	public String getTthdIds() {
		return this.tthdIds ;
	}

	public void setTthdIds(String tthdIds) {
		this.tthdIds  = tthdIds;
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

	public String getTienteId() {
		
		return this.tienteId;
	}

	
	public void setTienteId(String ttId) {
		
		this.tienteId = ttId;
	}
	
	public String getNgayghinhan() 
	{
		return this.ngayghinhan;
	}

	public void setNgayghinhan(String ngayghinhan) 
	{
		this.ngayghinhan = ngayghinhan;
	}

	public String getNccId()
	{
		return this.nccId;
	}

	public void setNccId(String nccId)
	{
		this.nccId = nccId;	
	}

	public ResultSet getNccRs()
	{
		return this.nccRs;
	}

	public void setNccRs(ResultSet nccRs) 
	{
		this.nccRs = nccRs;
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
	
	/*public boolean createTTHD() 
	{
		if(this.hoadonList.size() <= 0)
		{
			this.msg = "Không có hóa đơn nào để thanh toán";
			return false;
		}
		
		
		if(this.htttId.length() <= 0)
		{
			this.msg = "Vui lòng chọn hình thức thanh toán";
			return false;
		}
		
		if(this.htttId.equals("100001"))
		{
			if(this.nganhangId.length() <= 0)
			{
				this.msg = "Vui lòng chọn ngân hàng";
				return false;
			}
			
			if(this.chinhanhId.length() <= 0)
			{
				this.msg = "Vui lòng chọn chi nhánh";
				return false;
			}
			
			if(this.sotaikhoan.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn số tài khoản ngân hàng";
				return false;
			}
		}
		
		try 
		{
			String ngaytao = getDateTime();
			
			db.getConnection().setAutoCommit(false);
			
			if(this.nganhangId.trim().length() <= 0)
				this.nganhangId = "NULL";
			if(this.chinhanhId.trim().length() <= 0)
				this.chinhanhId = "NULL";
			if(this.sotaikhoan.trim().length() <= 0)
				this.sotaikhoan = "";
			
			long tongthanhtoan = 0;
			//if(!(this.LoaiThanhToan.equalsIgnoreCase("2"))){ // neu ko phai loai noi bo thi tinh lai tong tien
				//Tinh lai tong tien			
				
				System.out.println("Hoa don lis	t size: " + this.hoadonList.size());
				for(int i = 0; i < this.hoadonList.size(); i++)
				{
					IHoadon hd = this.hoadonList.get(i);
					if(hd.getThanhtoan().length() > 0)
						tongthanhtoan += Long.parseLong(hd.getThanhtoan().replaceAll(",", ""));
				}
			//}
			//else {
			//	tongthanhtoan = Long.parseLong(this.sotientt.replaceAll(",", ""));
			//}
			
			System.out.println("[ErpThanhtoanhoadon.createTTHD] tongthanhtoan = " + tongthanhtoan);
			
		
			String query = "Insert ERP_THANHTOANHOADON(NGAYGHINHAN, NCC_FK ,NHANVIEN_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, SOTIENTT, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA,LOAITHANHTOAN) " +
							"values('" + this.ngayghinhan + "', " + (this.nccId.length()==0?null : this.nccId ) + ","+(this.NhanvienId.length()==0?null : this.NhanvienId) +", '100002', " + this.nganhangId + ", " + this.chinhanhId + ", '" + this.sotaikhoan + "', N'" + this.noidungtt + "', '" + Long.toString(tongthanhtoan) + "', '"  + ngaytao + "', '" + this.userId + "', '" + ngaytao + "', '" + this.userId + "','"+this.LoaiThanhToan+"')";
			
			System.out.println(query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_THANHTOANHOADON: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			
			query = "select IDENT_CURRENT('ERP_THANHTOANHOADON') as tthdId";
			
			ResultSet rsTthd = db.get(query);						
			if(rsTthd.next())
			{
				tthdCurrent = rsTthd.getString("tthdId");
				rsTthd.close();
			}
			
			for(int i = 0; i < this.hoadonList.size(); i++)
			{
				IHoadon hoadon = this.hoadonList.get(i);

				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
				String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				String conlai = hoadon.getConlai().replaceAll(",", "");
				
				if(thanhtoan.length() > 0)
				{
					if(Float.parseFloat(thanhtoan) > 0)
					{
						query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI) " +
								"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim() + "', '" + avat + "', '" + conlai.trim() + "')";
						
						if (this.LoaiThanhToan.equalsIgnoreCase("2")) {
							query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI) " +
							"values('" + tthdCurrent + "', '" + hoadon.getSo() + "', '" + thanhtoan.trim() + "', '" + avat + "', '" + conlai.trim() + "')";
										
						}
						System.out.println(query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADON: " + query;
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
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Loi trong qua trinhf up date : "+e.toString());
			this.msg=e.toString();
			try 
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}*/

	
	public boolean createTTHD() 
	{
//		this.getNppInfo();
		
		if(this.hoadonList.size() <= 0)
		{
			this.msg = "Không có hóa đơn nào để thanh toán";
			return false;
		}
		
		
		if(this.htttId.length() <= 0)
		{
			this.msg = "Vui lòng chọn hình thức thanh toán";
			return false;
		}
		
		if(this.htttId.equals("100001"))
		{
			if(this.nganhangId.length() <= 0)
			{
				this.msg = "Vui lòng chọn ngân hàng";
				return false;
			}
			
			if(this.chinhanhId.length() <= 0)
			{
				this.msg = "Vui lòng chọn chi nhánh";
				return false;
			}
			
			if(this.sotaikhoan.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn số tài khoản ngân hàng";
				return false;
			}
		}
		
		try 
		{
			String ngaytao = getDateTime();
			
			db.getConnection().setAutoCommit(false);
			
			if(this.nganhangId.trim().length() <= 0)
				this.nganhangId = "NULL";
			if(this.chinhanhId.trim().length() <= 0)
				this.chinhanhId = "NULL";
			if(this.sotaikhoan.trim().length() <= 0)
				this.sotaikhoan = "";
			
			long tongthanhtoan = 0;		
				
				System.out.println("Hoa don lis	t size: " + this.hoadonList.size());
				for(int i = 0; i < this.hoadonList.size(); i++)
				{
					IHoadon hd = this.hoadonList.get(i);
					if(hd.getThanhtoan().length() > 0)
						tongthanhtoan += Long.parseLong(hd.getThanhtoan().replaceAll(",", ""));
				}
			System.out.println("[ErpThanhtoanhoadon.createTTHD] tongthanhtoan = " + tongthanhtoan);
			
				// CHEN BANG TONG
								
				String query = "Insert ERP_THANHTOANHOADON(NGAYGHINHAN, NCC_FK ,NHANVIEN_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN," +
						       "                           NOIDUNGTT, SOTIENTT, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK, NPP_FK, isNPP) " +
							   "values('" + this.ngayghinhan + "', " + (this.nccId.length()==0?null : this.nccId ) + ","+(this.NhanvienId.length()==0?null : this.NhanvienId) +", '100002', " + this.nganhangId + ", " + this.chinhanhId + ", '" + this.sotaikhoan + "'," +
							   "      N'" + this.noidungtt + "', '" + Long.toString(tongthanhtoan) + "', '"  + ngaytao + "', '" + this.userId + "', '" + ngaytao + "', '" + this.userId + "', "+this.congtyId+", "+this.nppdangnhap+", "+this.isNPP+")";
			
				System.out.println(query);
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_THANHTOANHOADON: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			
			
				query = "select scope_identity() as tthdId";
			
				ResultSet rsTthd = db.get(query);						
				if(rsTthd.next())
				{
					this.tthdCurrent = rsTthd.getString("tthdId");
					rsTthd.close();
				}
			
				for(int i = 0; i < this.hoadonList.size(); i++)
				{
					IHoadon hoadon = this.hoadonList.get(i);
				// CHEN BANG CHI TIET
				
				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
				String avat = hoadon.getSotienno().replaceAll(",", "");
				String tigia = hoadon.getTigia().replaceAll(",", "");
				String conlai = Double.toString(Double.parseDouble(avat)- Double.parseDouble(thanhtoan));
				String loaihd = hoadon.getLoaihd1();
				
				if(thanhtoan.length() > 0)
				{
					if(Float.parseFloat(thanhtoan) > 0)
					{
						if(this.tienteId.equals("100000"))
						{
							query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD) " +
							        "values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "") + "'," +
									" 0, '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"')";							
						}
						else
						{
							query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD) " +
									"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "'," +
									" '" + Double.parseDouble(avat.replaceAll(",", ""))*Double.parseDouble(tigia.replaceAll(",", "")) + "', " + avat.replaceAll(",", "") + "," +
									" '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"')";																					
				
						}
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADON: " + query;
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
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg=e.toString();
			try 
			{
				db.getConnection().rollback();
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
		this.nppdangnhap=util.getIdNhapp(this.userId);
	}
	
	public boolean updateTTHD() 
	{
		if(this.hoadonList.size() < 0)
		{
			this.msg = "Không có hóa đơn nào để thanh toán";
			return false;
		}else{
			// neu hoa dơn nao bị bỏ chọn thì xóa trong bảng thanhtoanhoadonn_hoadon
			List<String> hdMois = new ArrayList<String>();
			for (IHoadon hd : hoadonList) {
				hdMois.add(hd.getId());
			}
			List<String> listHDHienTai = GetlistHoaDonHienTai();
			for (String hdHienTai : listHDHienTai) {
				if(!hdMois.contains(hdHienTai)){
					XoaThanhToanHoaDon_HoaDon(hdHienTai);
				}
			}
			
		}
		
		if(this.htttId.length() <= 0)
		{
			this.msg = "Vui lòng chọn hình thức thanh toán";
			return false;
		}
		
		if(this.htttId.equals("100001"))
		{
			if(this.nganhangId.length() <= 0)
			{
				this.msg = "Vui lòng chọn ngân hàng";
				return false;
			}
			
			if(this.chinhanhId.length() <= 0)
			{
				this.msg = "Vui lòng chọn chi nhánh";
				return false;
			}
			
			if(this.sotaikhoan.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn số tài khoản ngân hàng";
				return false;
			}
		}
		
		//Tinh lai tong tien
		long tongthanhtoan = 0;
		for(int i = 0; i < this.hoadonList.size(); i++)
		{
			IHoadon hd = this.hoadonList.get(i);
			if(hd.getThanhtoan().length() > 0)
				tongthanhtoan += Long.parseLong(hd.getThanhtoan().replaceAll(",", ""));
			System.out.println("tongthanhtoan" + tongthanhtoan);
		}
		
		try 
		{
			String ngaysua = getDateTime();
			
			db.getConnection().setAutoCommit(false);
			
			if(this.nganhangId.trim().length() <= 0)
				this.nganhangId = "NULL";
			if(this.chinhanhId.trim().length() <= 0)
				this.chinhanhId = "NULL";
			if(this.sotaikhoan.trim().length() <= 0)
				this.sotaikhoan = "NULL";
			
		
	
				
				String query = 
					    "update ERP_THANHTOANHOADON set  NGAYGHINHAN = '" + this.ngayghinhan + "', NCC_FK = " + this.nccId + " , HTTT_FK = '100002', NGANHANG_FK = " + this.nganhangId + ", CHINHANH_FK = " + this.chinhanhId + ", SOTAIKHOAN = " + this.sotaikhoan + "," +
						" NOIDUNGTT = N'" + this.noidungtt + "', SOTIENTT = '" + Long.toString(tongthanhtoan) + "', NGAYSUA = '" + ngaysua + "', NGUOISUA = '" + this.userId + "', isNPP ="+this.isNPP+" where PK_SEQ = '"  + this.id + "'";
			
				System.out.println(query);
				
				if(!db.update(query))
				{
					this.msg = "Khong the cap nhat ERP_THANHTOANHOADON: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			
				query = "delete ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = '" + this.id + "'";
				db.update(query);			
				
				System.out.println("KICH thuoc hoa don " + this.hoadonList.size());
			

			for(int i = 0; i < this.hoadonList.size(); i++)
			{
					IHoadon hoadon = this.hoadonList.get(i);
					
				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
				String avat = hoadon.getSotienno().replaceAll(",", "");
				String conlai = Double.toString(Double.parseDouble(avat)- Double.parseDouble(thanhtoan));
				String tigia = hoadon.getTigia().replaceAll(",", "");
				String loaihd = hoadon.getLoaihd1();

				
				if(thanhtoan.length() > 0)
				{
					if(Float.parseFloat(thanhtoan) > 0)
					{
						if(this.tienteId.equals("100000"))
						{
							query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD) " +
							        "values('" + this.id  + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "") + "'," +
									" 0, '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"')";							
						}
						else
						{
							query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD) " +
									"values('" + this.id  + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "'," +
									" '" + Double.parseDouble(avat.replaceAll(",", ""))*Double.parseDouble(tigia.replaceAll(",", "")) + "', " + avat.replaceAll(",", "") + "," +
									" '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"')";																					
				
						}
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADON: " + query;
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
	
	/*public boolean updateTTHD() 
	{
		if(this.hoadonList.size() < 0)
		{
			this.msg = "Không có hóa đơn nào để thanh toán";
			return false;
		}else{
			// neu hoa dơn nao bị bỏ chọn thì xóa trong bảng thanhtoanhoadonn_hoadon
			List<String> hdMois = new ArrayList<String>();
			for (IHoadon hd : hoadonList) {
				hdMois.add(hd.getId());
			}
			List<String> listHDHienTai = GetlistHoaDonHienTai();
			for (String hdHienTai : listHDHienTai) {
				if(!hdMois.contains(hdHienTai)){
					XoaThanhToanHoaDon_HoaDon(hdHienTai);
				}
			}
			
		}
		
		if(this.htttId.length() <= 0)
		{
			this.msg = "Vui lòng chọn hình thức thanh toán";
			return false;
		}
		
		if(this.htttId.equals("100001"))
		{
			if(this.nganhangId.length() <= 0)
			{
				this.msg = "Vui lòng chọn ngân hàng";
				return false;
			}
			
			if(this.chinhanhId.length() <= 0)
			{
				this.msg = "Vui lòng chọn chi nhánh";
				return false;
			}
			
			if(this.sotaikhoan.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn số tài khoản ngân hàng";
				return false;
			}
		}
		
		//Tinh lai tong tien
		long tongthanhtoan = 0;
		for(int i = 0; i < this.hoadonList.size(); i++)
		{
			IHoadon hd = this.hoadonList.get(i);
			if(hd.getThanhtoan().length() > 0)
				tongthanhtoan += Long.parseLong(hd.getThanhtoan().replaceAll(",", ""));
			System.out.println("tongthanhtoan" + tongthanhtoan);
		}
		
		try 
		{
			String ngaysua = getDateTime();
			
			db.getConnection().setAutoCommit(false);
			
			if(this.nganhangId.trim().length() <= 0)
				this.nganhangId = "NULL";
			if(this.chinhanhId.trim().length() <= 0)
				this.chinhanhId = "NULL";
			if(this.sotaikhoan.trim().length() <= 0)
				this.sotaikhoan = "NULL";
			
			String query = "update ERP_THANHTOANHOADON set LoaithanhToan= "+this.LoaiThanhToan+", NGAYGHINHAN = '" + this.ngayghinhan + "', NCC_FK = " + ( this.nccId.length() >0 ?this.nccId :null)  + ",nhanvien_fk="+( this.NhanvienId.length() >0 ?this.NhanvienId :null)+" , HTTT_FK = '100002', NGANHANG_FK = " + this.nganhangId + ", CHINHANH_FK = " + this.chinhanhId + ", SOTAIKHOAN = " + this.sotaikhoan + "," +
						" NOIDUNGTT = N'" + this.noidungtt + "', SOTIENTT = '" + Long.toString(tongthanhtoan) + "', NGAYSUA = '" + ngaysua + "', NGUOISUA = '" + this.userId + "' where PK_SEQ = '"  + this.id + "'";
			
			System.out.println(query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_THANHTOANHOADON: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = '" + this.id + "'";
			db.update(query);
			
			System.out.println("KICH thuoc hoa don " + this.hoadonList.size());
			for(int i = 0; i < this.hoadonList.size(); i++)
			{
				IHoadon hoadon = this.hoadonList.get(i);

				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
				String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				String conlai = hoadon.getConlai().replaceAll(",", "");
				
				if(thanhtoan.length() > 0)
				{
					if(Float.parseFloat(thanhtoan) > 0)
					{
						query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI) " +
								"values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim() + "', '" + avat + "', '" + conlai.trim() + "')";
						
						
						if (this.LoaiThanhToan.equalsIgnoreCase("2")) {
							query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI) " +
							"values('" +  this.id  + "', '" + hoadon.getSo() + "', '" + thanhtoan.trim() + "', '" + avat + "', '" + conlai.trim() + "')";
										
						}
						System.out.println(" query update " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADON: " + query;
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
			try 
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}*/

	private void XoaThanhToanHoaDon_HoaDon(String hdHienTai) {
		// TODO Auto-generated method stub
		
		String sql ="DELETE FROM ERP_THANHTOANHOADON_HOADON\n" +
		"WHERE ERP_THANHTOANHOADON_HOADON.THANHTOANHD_FK = '" + this.id+
		"' AND ERP_THANHTOANHOADON_HOADON.HOADON_FK = '" +hdHienTai+		"'";
		db.update(sql);
		
	}

	private List<String> GetlistHoaDonHienTai() {
		// TODO Auto-generated method stub
		
		String sql  = " SELECT ERP_THANHTOANHOADON_HOADON.HOADON_FK FROM ERP_THANHTOANHOADON_HOADON\n" +
		" WHERE ERP_THANHTOANHOADON_HOADON.THANHTOANHD_FK = '" +this.id+"'";
		ResultSet resultSet = db.get(sql);
		List<String> list  = new ArrayList<String>();
//		String[] rerult = null;
		try {
//				rerult = (String[])resultSet.getArray("HOADON_FK").getArray();
			
			while (resultSet.next()) {
				list.add(resultSet.getString("HOADON_FK"));
			}
			
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return  list;
	}

	public boolean chotTTHD(String userId)
	{
		try 
		{
			/*
			 * kiem tra thanh toan nay co chua nhung hoa don nam trong thanh toan khac ma chua duoc chot
			 * neu co thi bao loi.
			 */
			
			String query;
//			checkChot();
						
			String ngaysua = getDateTime();
			
			db.getConnection().setAutoCommit(false);
			
			query = "update ERP_THANHTOANHOADON set TRANGTHAI = '1', NGUOISUA = '" + userId + "', NGAYSUA = '" + ngaysua + "' where PK_SEQ = '"  + this.id + "'";
			System.out.println("1.Cap nhat hoadon: " + query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the chot ERP_THANHTOANHOADON: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
				//Neu thanh toan hoa don 
			/*	//Cap nhat trang thai hoa don la hoan tat neu da thanh toan het hoa don
				query = "select thanhtoan.hoadon_fk, hoadon.sotienAVAT, thanhtoan.SOTIENTT " +
					"from ( " +
							"select hoadon_fk, sum(SOTIENTT) as SOTIENTT " +
							"from ERP_THANHTOANHOADON_HOADON " +
							"where hoadon_fk in (select hoadon_fk from ERP_THANHTOANHOADON_HOADON where thanhtoanhd_fk = '" + this.id + "' and LOAIHD = 0) " +
							"group by hoadon_fk " +
						") thanhtoan inner join ERP_HOADONNCC hoadon on thanhtoan.hoadon_fk = hoadon.pk_seq " +
						"where hoadon.sotienAVAT - thanhtoan.SOTIENTT <= 0";
				System.out.println(query);
				
				ResultSet hoadonRs = db.get(query);
				if(hoadonRs != null)
				{
					while(hoadonRs.next())
					{
						String hoadon_fk = hoadonRs.getString("hoadon_fk");
						
						query = "update ERP_HOADONNCC set trangthai = '2' where pk_seq = '" + hoadon_fk + "'";
						System.out.println("Cap nhat ERP_HOADONNCC " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the cap nhat ERP_HOADONNCC: " + query;
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
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			
			this.msg = "Khong the cap nhat ERP_HOADONNCC: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public boolean checkChot() {
		try {
			String query = "SELECT CAST(ERP_THANHTOANHOADON.PREFIX AS VARCHAR) + CAST(ERP_THANHTOANHOADON_HOADON.THANHTOANHD_FK AS VARCHAR) AS SOTHANHTOAN ,ERP_THANHTOANHOADON_HOADON.HOADON_FK , ERP_CANTRUCONGNO.PK_SEQ AS CTCN_FK , ERP_HOADONNCC.SOHOADON  \n" +
			"FROM \n" +
			"ERP_THANHTOANHOADON_HOADON \n" +
			"LEFT JOIN ERP_THANHTOANHOADON ON ERP_THANHTOANHOADON.PK_SEQ = ERP_THANHTOANHOADON_HOADON.THANHTOANHD_FK\n" +
			"LEFT JOIN ERP_CANTRUCONGNO ON ERP_CANTRUCONGNO.THANHTOAN_FK = ERP_THANHTOANHOADON_HOADON.THANHTOANHD_FK\n" +
			"lEFT JOIN ERP_HOADONNCC ON ERP_HOADONNCC.PK_SEQ = ERP_THANHTOANHOADON_HOADON.HOADON_FK\n" +
			"WHERE ERP_THANHTOANHOADON_HOADON.HOADON_FK IN\n" +
			"	(\n" +
			"	SELECT ERP_THANHTOANHOADON_HOADON.HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE ERP_THANHTOANHOADON_HOADON.THANHTOANHD_FK = '" +this.id+
			"'\n" +
			"	)\n" +
			"	AND ERP_THANHTOANHOADON_HOADON.THANHTOANHD_FK <> '" + this.id+
			"'\n" +
			"	AND ERP_THANHTOANHOADON.TRANGTHAI = 0";
			ResultSet resultSet = db.get(query);			
			if(resultSet.next())
				{
					if(resultSet.getString("CTCN_FK")==null)//neu cot CTCN_FK = null => thanhtoan ko thuoc loai cantrucongno.
					{
					this.msg = "Hóa đơn số:" +resultSet.getString("SOHOADON")+" đã tồn tại trong phiếu ủy nhiệm chi số: " +
							resultSet.getString("SOTHANHTOAN") +" ở trạng thái chưa chốt Vui lòng kiểm tra lại.";
					return false;
					}else{
						this.msg = "Hóa đơn số:" +resultSet.getString("SOHOADON")+" đã tồn tại trong cấn trừ công nợ số: " +
						resultSet.getString("CTCN_FK") +" ở trạng thái chưa chốt Vui lòng kiểm tra lại.";
						return false;
					}
				}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean CheckChot2(){
		init();
		
		for (IHoadon hd : this.hoadonList) {
			
			if (hd.getTongtiencoVAT().length()>0&&hd.getThanhtoan().length()>0) {
				double tongVAT = Double.parseDouble(hd.getTongtiencoVAT()
						.replaceAll(",", ""));
				double thanhtoan = Double.parseDouble(hd.getThanhtoan()
						.replaceAll(",", ""));
				if (tongVAT < thanhtoan) {
					this.msg = " Số tiền thanh toán lớn hơn số tiền gốc";
					return false;
				}
			}
			}
		return true;
		}
	
	
	
	public void init()
	{
		this.getNppInfo();
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = " select  isnull(hd.LoaiThanhToan,'1') as LoaiThanhToan,hd.nhanvien_fk,hd.pk_seq " +
						", hd.ngayghinhan, hd.trangthai, hd.ncc_fk, hd.httt_fk, hd.nganhang_fk, hd.chinhanh_fk, hd.sotaikhoan," +
						" hd.noidungtt, hd.sotientt ,ncc.ma as mancc,ncc.ten as tenncc, nv.ma as manv,nv.ten as tennv " +
						" from ERP_THANHTOANHOADON hd  " +
						" left join erp_nhacungcap ncc on ncc.pk_Seq=hd.ncc_fk " +
						" left join erp_nhanvien nv on nv.pk_Seq=hd.nhanvien_fk  where hd.pk_seq = '" + this.id + "' ";
		System.out.println("GET SQL day nek  : "+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngayghinhan = rs.getString("ngayghinhan");
					this.nccId = rs.getString("ncc_fk");
					this.htttId = rs.getString("httt_fk");
					
					if(this.htttId.equals("100001"))
					{
						this.nganhangId = rs.getString("nganhang_fk");
						this.chinhanhId = rs.getString("chinhanh_fk");
						this.sotaikhoan = rs.getString("sotaikhoan");
					}
					this.LoaiThanhToan=rs.getString("LoaiThanhToan");
					this.NhanvienId=rs.getString("nhanvien_fk");
					if(this.NhanvienId==null){
						this.NhanvienId="";
						this.DoiTuongTamUng="1";
						this.TenHienThi=rs.getString("ncc_fk")+" -- "+rs.getString("mancc")+" -- "+rs.getString("tenncc");
					}
					if(this.nccId==null){
						this.nccId="";
						this.DoiTuongTamUng="0";
						this.TenHienThi=rs.getString("nhanvien_fk")+" -- "+rs.getString("manv")+" -- "+rs.getString("tennv");
					}
					this.noidungtt = rs.getString("noidungtt");
					this.sotientt = formatter.format(rs.getDouble("sotientt"));
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.createRS2();
	}

	public void initDisplay()
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = " select  isnull(hd.LoaiThanhToan,'1') as LoaiThanhToan,hd.nhanvien_fk,hd.pk_seq " +
						", hd.ngayghinhan, hd.trangthai, hd.ncc_fk, hd.httt_fk, hd.nganhang_fk, hd.chinhanh_fk, hd.sotaikhoan," +
						" hd.noidungtt, hd.sotientt ,ncc.ma as mancc,ncc.ten as tenncc, nv.ma as manv,nv.ten as tennv " +
						" from ERP_THANHTOANHOADON hd  " +
						" left join erp_nhacungcap ncc on ncc.pk_Seq=hd.ncc_fk " +
						" left join erp_nhanvien nv on nv.pk_Seq=hd.nhanvien_fk  where hd.pk_seq = '" + this.id + "'";
	System.out.println("GET SQL here: "+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngayghinhan = rs.getString("ngayghinhan");
					this.nccId = rs.getString("ncc_fk");
					this.htttId = rs.getString("httt_fk");
					
					if(this.htttId.equals("100001"))
					{
						this.nganhangId = rs.getString("nganhang_fk");
						this.chinhanhId = rs.getString("chinhanh_fk");
						this.sotaikhoan = rs.getString("sotaikhoan");
					}
				
					this.NhanvienId=rs.getString("nhanvien_fk");
					if(this.NhanvienId==null){
						this.NhanvienId="";
						this.DoiTuongTamUng="1";
						this.TenHienThi=rs.getString("ncc_fk")+" -- "+rs.getString("mancc")+" -- "+rs.getString("tenncc");
					}
					if(this.nccId==null){
						this.nccId="";
						this.DoiTuongTamUng="0";
						this.TenHienThi=rs.getString("nhanvien_fk")+" -- "+rs.getString("manv")+" -- "+rs.getString("tennv");
					}
					this.noidungtt = rs.getString("noidungtt");
					this.sotientt = formatter.format(rs.getDouble("sotientt"));
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.nccRs = db.get("select pk_seq, ma + ', ' + ten as nccTen from erp_nhacungcap where trangthai = '1' and duyet = '1'");
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1'");
		this.nganhangRs = db.get("select pk_seq, ma + ', ' + ten as nhTen from erp_nganhang where PK_SEQ in ( select NganHang_FK from ERP_NGANHANG_CONGTY where congty_fk = '100005' ) ");
		
		if(this.htttId.equals("100001"))
		{
			if(this.nganhangId.length() > 0)
			{
				this.chinhanhRs = db.get("select pk_seq, ma + ', ' + ten as cnTen from erp_chinhanh where PK_SEQ in ( select ChiNhanh_FK from ERP_NGANHANG_CONGTY where congty_fk = '100005' )");
			}
			
			if(this.chinhanhId.length() > 0)
			{
				rs = db.get("select sotaikhoan from ERP_NGANHANG_CONGTY where congty_fk = '100005' and chinhanh_fk = '" + this.chinhanhId + "' and nganhang_fk = '" + this.nganhangId + "' ");
				if(rs != null)
				{
					try
					{
						if(rs.next())
						{
							this.sotaikhoan = rs.getString("sotaikhoan");
						}
						rs.close();
					} 
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
			
		if(this.LoaiThanhToan.equals("1"))
		{
			query = "select b.pk_seq, b.kyhieu, b.sohoadon, b.ngayhoadon, a.sotienAVAT, SOTIENTT, " +
						"ISNULL( (  " +
							"Select distinct ( Select cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()]  " +
							"From ERP_HOADONNCC_PHIEUNHAP PO1   " +
							"Where PO1.hoadonncc_fk = b.pk_seq " +
							"For XML PATH ('')) [phongbanChon_fk]  " +
							"From ERP_HOADONNCC_PHIEUNHAP PO  " +
							"where PO.hoadonncc_fk = b.pk_seq ), '' )   as POID " +
					"from ERP_THANHTOANHOADON_HOADON a " +
					"inner join ERP_HOADONNCC b on a.hoadon_fk = b.pk_seq where a.THANHTOANHD_FK = '" + this.id + "'";
		}
		else if (this.LoaiThanhToan.equals("0"))
		{
			query = "select b.pk_seq, N'TẠM ỨNG' AS  kyhieu,B.PK_SEQ AS  sohoadon, B.NGAYTAMUNG AS  ngayhoadon," +
					"B.sotientamung as  sotienAVAT, SOTIENTT, '' as POID " +
					"from ERP_THANHTOANHOADON_HOADON a " +
					"inner join ERP_TAMUNG b on a.hoadon_fk = b.pk_seq where a.THANHTOANHD_FK = '" + this.id + "'";
		}
		else if (this.LoaiThanhToan.equals("2"))
		{
			query = "select mh.PK_SEQ, mh.PK_SEQ as sohoadon,  N'Chi phí nội bộ'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , " +
					" tt.sotienavat as sotienavat, tt.SOTIENTT as sotientt, '' as POID, tt.SOTIENTT as DATHANHTOAN " +
					" from Erp_MuaHang mh " +
					" left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ"+
					" left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
					" where ncc.NOIBO = 1 and  mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" +this.id+ "'";
		}
		System.out.println("Khoi tao hoadon here: " + query);
			
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
					String avat = formatter.format(rsHoadon.getDouble("sotienAVAT"));
				
					String dathanhtoan = "0";
					if(rsHoadon.getDouble("SOTIENTT") > 0)
						dathanhtoan = formatter.format(rsHoadon.getDouble("SOTIENTT"));
						
					hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, dathanhtoan, "");
					hd.setSoPO(rsHoadon.getString("POID"));
					hdList.add(hd);
				}
				rsHoadon.close();
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		this.hoadonList = hdList;
	}
	
	
	
	public void initPdf() 
	{
		String query = 
			" select a.pk_seq, a.ngayghinhan, " +
			"   case when a.NCC_FK IS not null then ncc.TEN when a.NHANVIEN_FK IS not null then nv.TEN	else '' end AS nguoinhan, " +
			" 	case when a.NCC_FK IS not null then ncc.diachi else '' end AS diachi,  " +
			"   a.httt_fk, a.nganhang_fk, a.chinhanh_fk, a.sotaikhoan, a.noidungtt, a.sotientt " +
			" from ERP_THANHTOANHOADON a " +
			" left join ERP_NHACUNGCAP ncc on a.NCC_FK = ncc.PK_SEQ " +
			" left join ERP_NHANVIEN nv on a.NHANVIEN_FK = nv.PK_SEQ " +
			" where a.pk_seq = '" + this.id + "'";
		System.out.println("[ErpThanhtoanhoadon.initPdf] query = " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngayghinhan = rs.getString("ngayghinhan");
					this.nccId = rs.getString("nguoinhan");
					this.diachi = rs.getString("diachi");
					this.htttId = rs.getString("httt_fk");
					if(this.htttId.equals("100001"))
					{
						this.nganhangId = rs.getString("nganhang_fk");
						this.chinhanhId = rs.getString("chinhanh_fk");
						this.sotaikhoan = rs.getString("sotaikhoan");
					}
					this.noidungtt = rs.getString("noidungtt");
					this.sotientt = rs.getString("sotientt");
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		//khoi tao hoa don
		query = "select sohoadon from erp_thanhtoanhoadon_hoadon a inner join ERP_HOADONNCC b on a.hoadon_fk = b.pk_seq where thanhtoanhd_fk = '" + this.id + "'";
		String sohoadon = "";
		ResultSet hoadonRs = db.get(query);
		if(hoadonRs != null)
		{
			try
			{
				while(hoadonRs.next())
				{
					sohoadon += hoadonRs.getString("sohoadon") + ", ";
				}
				hoadonRs.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(sohoadon.length() > 0)
			this.noidungtt += " --- " + sohoadon.substring(0, sohoadon.length() - 2);
	}

	public void initUnc() 
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = "select a.pk_seq, a.ngayghinhan, b.ten as nccTen, b.diachi, a.httt_fk, c.ten as nganhang_fk, a.chinhanh_fk, a.sotaikhoan, a.noidungtt, a.sotientt " +
						"from ERP_THANHTOANHOADON a inner join ERP_NHACUNGCAP b on a.ncc_fk = b.pk_seq left join erp_nganhang c on a.nganhang_fk = c.pk_seq" +
						" where a.pk_seq = '" + this.id + "'";
		System.out.println("Khoi tao Unc: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngayghinhan = rs.getString("ngayghinhan");
					this.nccId = rs.getString("nccTen") + " --- " + rs.getString("diachi");
					this.htttId = rs.getString("httt_fk");
					if(this.htttId.equals("100001"))
					{
						this.nganhangId = rs.getString("nganhang_fk");
						this.chinhanhId = rs.getString("chinhanh_fk");
						this.sotaikhoan = rs.getString("sotaikhoan");
					}
					this.noidungtt = rs.getString("noidungtt");
					this.sotientt = formatter.format(rs.getDouble("sotientt"));
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void createRs() 
	{
		this.nccRs = db.get("select pk_seq, ma + ', ' + ten as nccTen from erp_nhacungcap where trangthai = '1' and duyet = '1'");
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1'");
		this.nganhangRs = db.get("select pk_seq, ma + ', ' + ten as nhTen from erp_nganhang where PK_SEQ in ( select NganHang_FK from ERP_NGANHANG_CONGTY where congty_fk = '100005' ) ");
		
		if(this.htttId.equals("100001"))
		{
			if(this.nganhangId.length() > 0)
			{
				this.chinhanhRs = db.get("select pk_seq, ma + ', ' + ten as cnTen from erp_chinhanh where PK_SEQ in ( select ChiNhanh_FK from ERP_NGANHANG_CONGTY where congty_fk = '100005' )");
				
			}
			
			if(this.chinhanhId.length() > 0)
			{
				String sql = "select sotaikhoan from ERP_NGANHANG_CONGTY where congty_fk = '100005' and chinhanh_fk = '" + this.chinhanhId + "' and nganhang_fk = '" + this.nganhangId + "' ";
				System.out.println("[ErpThanhtoanhoadon.createRs] sotaikhoan sql = " + sql);
				ResultSet rs = db.get(sql);
				if(rs != null)
				{
					try
					{
						if(rs.next())
						{
							this.sotaikhoan = rs.getString("sotaikhoan");
						}
						rs.close();
					} 
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if( (this.nccId.length() > 0 || this.NhanvienId.length() >0) && this.htttId.length() > 0 && this.hoadonList.size() <= 0)
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			
			String query = "";
			if(this.LoaiThanhToan.equals("1"))
			{
				if(this.id.length() > 0)
				{
				query += " select b.pk_seq, b.kyhieu, b.sohoadon, b.ngayhoadon, b.sotienAVAT - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVAT, SOTIENTT, " +
							" ISNULL( (  " +
									"Select distinct ( Select cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()]  " +
									"From ERP_HOADONNCC_PHIEUNHAP PO1   " +
									"Where PO1.hoadonncc_fk = b.pk_seq " +
										"For XML PATH ('')) [phongbanChon_fk]  " +
										"From ERP_HOADONNCC_PHIEUNHAP PO  " +
										"where PO.hoadonncc_fk = b.pk_seq " +
									"), '' )   as POID " +
						"from ERP_THANHTOANHOADON_HOADON a inner join ERP_HOADONNCC b on a.hoadon_fk = b.pk_seq " +
							"left join	" +
							"( select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan " +
							"from ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ " +
							"where TT.trangthai = '1' and TTHD.thanhtoanhd_fk != '" + this.id + "' " +
									" and TTHD.hoadon_fk in (select hoadon_fk " +
													  "from ERP_THANHTOANHOADON_HOADON " +
													  "where thanhtoanhd_fk = '" + this.id + "') " +
							"group by hoadon_fk) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk " +
							"where a.thanhtoanhd_fk = '" + this.id + "' " +
						" union all ";
				}
				
				query += "(select hoadon.pk_seq, hoadon.kyhieu, hoadon.sohoadon, hoadon.ngayhoadon, " +
						" hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') as sotienAVAT, 0 as sotientt ," +
							" ISNULL( (  " +
								"Select distinct ( Select cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()]  " +
								"From ERP_HOADONNCC_PHIEUNHAP PO1   " +
								"Where PO1.hoadonncc_fk = hoadon.pk_seq " +
									"For XML PATH ('')) [phongbanChon_fk]  " +
									"From ERP_HOADONNCC_PHIEUNHAP PO  " +
									"where PO.hoadonncc_fk = hoadon.pk_seq " +
								"), '' )   as POID " +
						"from ( " +
								"select a.pk_seq, a.kyhieu, a.sohoadon, a.ngayhoadon, a.sotienAVAT " +
								"from ERP_HOADONNCC a inner join ERP_PARK b on a.park_fk = b.pk_seq " +
								"where b.ncc_fk = '" + this.nccId + "' and b.trangthai = '2' and a.trangthai = '0' ";
							if(this.id.length() > 0)
							{
								query += "and a.pk_seq not in (select hoadon_fk from ERP_THANHTOANHOADON_HOADON where thanhtoanhd_fk = '" + this.id + "') ";
							}
					query += ") hoadon " +
						"left join ( " +
								"select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  " +
								"from ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ " +
								"where TT.TRANGTHAI = '1' and TTHD.HOADON_FK in (select pk_seq from ERP_HOADONNCC where trangthai = 0)  " +
								"group by HOADON_FK " +
								") dathanhtoan " +
						"on hoadon.pk_seq = dathanhtoan.hoadon_fk " +
						"where hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') > 0 )";
//						" and hoadon.pk_seq not in ( "+
//						"	select distinct td.HOADON_FK from ERP_THANHTOANHOADON_HOADON td "+
//						"	inner join ERP_THANHTOANHOADON t on t.PK_SEQ = td.THANHTOANHD_FK "+
//						"	where t.TRANGTHAI =0) )	";
			}else if(this.LoaiThanhToan.equals("0")){
				if(this.id.length() >0){
					query=" SELECT TU.PK_SEQ , cast(TU.PK_SEQ as nvarchar(50)) as sohoadon ,TU.NGAYTAMUNG AS ngayhoadon,N'TẠM ỨNG'  AS KYHIEU ,TU.NHANVIEN_FK ,TU.NCC_FK,SOTIENTAMUNG "+ 
					" -( SELECT ISNULL(SUM(b.SOTIENTT),0) FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  " +
						" WHERE A.TRANGTHAI <>2 and a.pk_seq <> "+this.id+"  AND HOADON_FK=TU.PK_SEQ) AS sotienAVAT ,0 AS SOTIENTT, '' as POID  "+
					"  FROM ERP_TAMUNG TU WHERE ISNULL(TU.HOANTAT,'0')='0'  and TU.pk_seq not in  " +
					" (select hoadon_fk from ERP_THANHTOANHOADON a inner join  " +
					"  ERP_THANHTOANHOADON_HOADON b on a.pk_seq=b.thanhtoanhd_fk where " +
					"  a.trangthai <>'2' and  b.thanhtoanhd_fk="+this.id+"  )  and  (SOTIENTAMUNG "+ 
					" -( SELECT ISNULL(SUM(b.SOTIENTT),0) FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  " +
						" WHERE A.TRANGTHAI <>2 and a.pk_seq <> "+this.id+"  AND HOADON_FK=TU.PK_SEQ) >0 )"; 
					
					
					if(this.DoiTuongTamUng.equals("0")){
						//ung cho ncc
						query=query +" and   tu.NHANVIEN_FK="+ (this.NhanvienId ==""?"0":this.NhanvienId) ;
					}else{
						query=query +" and   tu.NCC_FK="+ (this.nccId ==""?"0":this.nccId) ;
					}
					
					query=query + " UNION ALL " +
							"select hoadon_fk AS SOCHUNGTU, cast (hoadon_fk as nvarchar(50)) AS SOHOADON ,a.ngaytamung as ngayhoadon , N'TẠM ỨNG ' as   KYHIEU ,a.NHANVIEN_FK ,a.NCC_FK "+
					" ,a.sotientamung -( SELECT ISNULL(SUM(CT.SOTIENTT),0) FROM ERP_THANHTOANHOADON_HOADON CT INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=CT.THANHTOANHD_FK "+ 
					"  WHERE A.TRANGTHAI<>'2'  AND CT.HOADON_FK=B.HOADON_FK and a.pk_seq <>"+this.id+" )  AS sotienAVAT ,sotientt, '' as POID " +
					"from ERP_THANHTOANHOADON_HOADON b "+
					" inner join ERP_TAMUNG a on a.pk_seq=b.hoadon_fk  and ( a.sotientamung -( SELECT ISNULL(SUM(CT.SOTIENTT),0) FROM ERP_THANHTOANHOADON_HOADON CT INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=CT.THANHTOANHD_FK "+ 
					"  WHERE A.TRANGTHAI<>'2'  AND CT.HOADON_FK=B.HOADON_FK and a.pk_seq <>"+this.id+" ) >0)"+
					" where thanhtoanhd_fk="+this.id ;
					/*if(this.NhanvienId.length()==0){
						
					}else{
						
					}*/
					
					
					
				}else{ 
					
					query=" SELECT TU.PK_SEQ , cast(TU.PK_SEQ as nvarchar(50)) as sohoadon ,TU.NGAYTAMUNG AS ngayhoadon,N'TẠM ỨNG'  AS KYHIEU ,TU.NHANVIEN_FK ,TU.NCC_FK,SOTIENTAMUNG   "+ 
						" -( SELECT ISNULL(SUM(b.SOTIENTT),0) FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  " +
						" WHERE A.TRANGTHAI<>'2'  AND HOADON_FK=TU.PK_SEQ) AS sotienAVAT , 0 AS SOTIENTT, '' as POID  "+
						" FROM ERP_TAMUNG TU WHERE ISNULL(TU.HOANTAT,'0')='0'  and  (SOTIENTAMUNG   "+ 
						" -( SELECT ISNULL(SUM(b.SOTIENTT),0) FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  " +
						" WHERE A.TRANGTHAI<>'2'  AND HOADON_FK=TU.PK_SEQ) >0 )";
					if(this.DoiTuongTamUng.equals("0")){
						//ung cho ncc
						query=query +" and   NHANVIEN_FK="+ (this.NhanvienId ==""?"0":this.NhanvienId) ;
					}else{
						query=query +" and   NCC_FK="+ (this.nccId ==""?"0":this.nccId) ;
					}
					
				}
			}
			else{ // loai thanh toan la noi bo 
				if(this.id.length() > 0){
					query = "select mh.PK_SEQ, cast(mh.PK_SEQ as nvarchar(50)) as sohoadon,  N'Chi phí nội bộ'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , tt.sotienavat as sotienavat, tt.SOTIENTT as sotientt, '' as POID , tt.SOTIENTT as DATHANHTOAN"+
					" from Erp_MuaHang mh " +
					" left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ"+
					" left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
					" where ncc.NOIBO = 1 and  mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" +this.id+ "'";
					
					query += " union " ;
					
					query += " select mh.PK_SEQ, cast(mh.PK_SEQ as nvarchar(50)) as sohoadon,  N'Chi phí n?i b?'  AS KYHIEU , mh.NGAYMUA as ngayhoadon "+
					" ,case when (tt.SOTIENAVAT is null OR t.TRANGTHAI=2) then mh.TONGTIENAVAT else tt.CONLAI end as sotienavat,0 as sotientt, '' as POID  " +
					" ,0 as dathanhtoan"+
					" from ERP_MUAHANG mh  "+
					" left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ  " +
					" left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk "+
					" left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
					" where ncc.NOIBO = 1 and mh.NHACUNGCAP_FK = '" + this.nccId + "' and ( tt.CONLAI is null or (tt.CONLAI > 0 "+
					" and tt.HOADON_FK not in "+
					"	(select distinct tt.HOADON_FK "+
					"	from ERP_MUAHANG mh "+
					"	left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ  "+
					"	left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
					"	where mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.CONLAI = 0)" +
					"  and tt.THANHTOANHD_FK in "+
					"	(select MAX(tt.THANHTOANHD_FK) "+
					"	from Erp_MuaHang mh  "+
					"	left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ "+
					" left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk "+
					"	left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
					"	 where ncc.NOIBO = 1 and mh.TRANGTHAI =1    and  mh.NHACUNGCAP_FK =  '" + this.nccId + "' and t.TRANGTHAI<>2	"+
					"	 group by tt.HOADON_FK ) ) )" +
					" and mh.TRANGTHAI = 2 " +
					" and (tt.THANHTOANHD_FK is null or ( tt.THANHTOANHD_FK > 0 and ( t.TRANGTHAI=1))) " ;
					
					
				}else {
										
					query = " select  mh.PK_SEQ, cast(mh.PK_SEQ as nvarchar(50)) as sohoadon,  N'Chi phí n?i b?'  AS KYHIEU , mh.NGAYMUA as ngayhoadon "+
					" ,case when (tt.SOTIENAVAT is null OR t.TRANGTHAI=2) then mh.TONGTIENAVAT else tt.CONLAI end as sotienavat,0 as sotientt, '' as POID  " +
					" ,0 as dathanhtoan"+
					" from ERP_MUAHANG mh  "+
					" left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ  " +
					" left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk "+
					" left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
					" where ncc.NOIBO = 1 and mh.NHACUNGCAP_FK = '" + this.nccId + "' and ( tt.CONLAI is null or (tt.CONLAI > 0 "+
					" and tt.HOADON_FK not in "+
					"	(select distinct tt.HOADON_FK "+
					"	from ERP_MUAHANG mh "+
					"	left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ  "+
					"	left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
					"	where mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.CONLAI = 0)" +
					"  and tt.THANHTOANHD_FK in "+
					"	(select MAX(tt.THANHTOANHD_FK) "+
					"	from Erp_MuaHang mh  "+
					"	left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ "+
					" left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk "+
					"	left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
					"	 where ncc.NOIBO = 1 and mh.TRANGTHAI =1    and  mh.NHACUNGCAP_FK =  '" + this.nccId + "' and t.TRANGTHAI<>2	"+
					"	 group by tt.HOADON_FK ) ) )" +
					" and mh.TRANGTHAI = 2 " +
					" and (tt.THANHTOANHD_FK is null or ( tt.THANHTOANHD_FK > 0 and ( t.TRANGTHAI=1))) " ;
					
				}
					
			}
			System.out.println("Query khoi tao hoa don: " + query);
			
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
						String avat = formatter.format(rsHoadon.getDouble("sotienAVAT"));
						//System.out.println("Query sohoadon: " + sohoadon);
						String dathanhtoan ="0";
						if(this.id.length() > 0)
						{
							dathanhtoan = formatter.format(rsHoadon.getDouble("SOTIENTT"));
						}
						
						hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, dathanhtoan, "");
						hd.setSoPO(rsHoadon.getString("POID"));
						hdList.add(hd);
					}
					rsHoadon.close();
				} 
				catch (Exception e) { 
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

	
	public String getLoaiThanhToan() {
		
		return this.LoaiThanhToan;
	}

	
	public void setLoaiThanhToan(String loaithanhtoan) {
		
		this.LoaiThanhToan=loaithanhtoan;
	}

	
	public String getDoiTuongTamUng() {
		
		return this.DoiTuongTamUng;
	}

	
	public void setDoiTuongTamUng(String DoiTuongTamUng) {
		
		 this.DoiTuongTamUng=DoiTuongTamUng ;
	}

	
	public String getNhanVienId() {
		
		return this.NhanvienId;
	}

	
	public void setNhanVienId(String nvId) {
		
		this.NhanvienId=nvId;
	}

	
	public String Gettenhienthi() {
		
		return this.TenHienThi;
	}

	
	public void settenhienthi(String tenhienthi) {
		
		this.TenHienThi=tenhienthi;
	}


	public String delete() {
		// TODO Auto-generated method stub
		try 
		{
			db.getConnection().setAutoCommit(false);
						
			String query = "DELETE FROM ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return  "Khong the xoa ERP_THANHTOANHOADON_HOADON ";
			}

			query = "DELETE FROM ERP_ThanhToanHoaDon where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return  "Khong the xoa ERP_THANHTOANHOADON ";
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (SQLException e)
		{ 
			e.printStackTrace();
			db.shutDown(); 
			return "Khong the xoa ThanhToanHoaDon"; 
		}
	}
	
	public String delete(String IdThanhToan) {
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			db.update("update ERP_ThanhToanHoaDon set trangthai = '2' where pk_seq = '" + IdThanhToan + "'");
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (SQLException e)
		{ 
			e.printStackTrace();
			db.shutDown(); 
			return "Khong the xoa ThanhToanHoaDon"; 
		}
	}
	
	public void createRS2()
	{
		/*this.congtyId = "100001";
		this.tienteId = "100000";*/
		
		this.getNppInfo();
		this.nccRs = db.get("select pk_seq, ma + ', ' + ten as nccTen from erp_nhacungcap where trangthai = '1' and duyet = '1'");
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1'");
		this.nganhangRs = db.get("select pk_seq, ma + ', ' + ten as nhTen from erp_nganhang where PK_SEQ in ( select NganHang_FK from ERP_NGANHANG_CONGTY where congty_fk = '"+this.congtyId+"' ) ");
		
		if(this.htttId.equals("100001"))
		{
			if(this.nganhangId.length() > 0)
				this.chinhanhRs = db.get("select pk_seq, ma + ', ' + ten as cnTen from erp_chinhanh where PK_SEQ in ( select ChiNhanh_FK from ERP_NGANHANG_CONGTY where congty_fk = '"+this.congtyId+"' )");
			
			if(this.chinhanhId.length() > 0)
			{
				String sql = "select sotaikhoan from ERP_NGANHANG_CONGTY where congty_fk = '"+this.congtyId+"' and chinhanh_fk = '" + this.chinhanhId + "' and nganhang_fk = '" + this.nganhangId + "' ";
				System.out.println("[ErpThanhtoanhoadon.createRs] sotaikhoan sql = " + sql);
				ResultSet rs = db.get(sql);
				if(rs != null)
				{
					try
					{
						if(rs.next())
						{
							this.sotaikhoan = rs.getString("sotaikhoan");
						}
						rs.close();
					} 
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if( this.nccId.length() > 0 && this.htttId.length() > 0 && this.hoadonList.size() <= 0)
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			
			String query = "";
		/*	
			//LOAIHD: 0 - HOADONNCC
			   if(this.id.length() > 0)
			   {
				query +="SELECT 0 as LOAIHD, b.pk_seq, isnull(b.sohoadon, '') as sohoadon,  isnull(b.kyhieu, '') as kyhieu, b.ngayhoadon, \n" +
						"         b.sotienAVAT as SOTIENGOC , b.sotienAVAT - isnull(thanh_toan.tongthanhtoan, '0') as sotienNO, SOTIENTT, \n" +
						" 		  ISNULL( (  \n" +
						"			Select distinct  \n" +
						"               	( Select cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()]  \n" +
						"				  	  From ERP_HOADONNCC_PHIEUNHAP PO1   \n" +
						"			      	  Where PO1.hoadonncc_fk = b.pk_seq \n" +
						"			      	  For XML PATH ('')) [phongbanChon_fk]  \n" +
						"			From ERP_HOADONNCC_PHIEUNHAP PO  \n" +
						"			where PO.hoadonncc_fk = b.pk_seq \n" +
						"            ), '' ) as POID, c.TIENTE_FK AS TTID, c.TIGIA, b.NGAYDENHANTT \n" +
						"FROM ERP_THANHTOANHOADON_HOADON a \n" +
						"INNER JOIN ERP_HOADONNCC b on a.hoadon_fk = b.pk_seq  and a.loaihd = 0  \n" +
						"INNER JOIN ERP_PARK c on b.park_fk = c.pk_seq \n" +
						"LEFT JOIN	\n" +
						"( SELECT TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
						"  FROM   ERP_THANHTOANHOADON_HOADON TTHD INNER JOIN ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
						"  WHERE  TTHD.LOAIHD = 0 AND TT.trangthai !=2 and TTHD.thanhtoanhd_fk != '" + this.id + "' \n" +  //sua trang thai !=2 thanh =1
						"         and TTHD.hoadon_fk in ( SELECT hoadon_fk \n" +
				        "								  FROM ERP_THANHTOANHOADON_HOADON \n" +
						"								  WHERE thanhtoanhd_fk = '" + this.id + "' and loaihd = 0 ) \n" +
						"		  and TT.CONGTY_FK = "+this.congtyId+
						"  GROUP BY hoadon_fk) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
						"WHERE a.thanhtoanhd_fk = '" + this.id + "' and c.TIENTE_FK = " + this.tienteId + " \n" +
						"	   and c.CONGTY_FK = "+this.congtyId +
						
						"UNION ALL \n";
				}
				
				query +="(SELECT 0 as LOAIHD, hoadon.pk_seq, isnull(hoadon.sohoadon, '') as sohoadon, isnull(hoadon.kyhieu, '') as kyhieu, hoadon.ngayhoadon, \n" +
						"         hoadon.sotienAVAT as SOTIENGOC, hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0')  as sotienNO, 0 as sotientt ,\n " +
						" ISNULL( (  " +
						"		SELECT distinct ( SELECT cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()] \n" +
						"						  FROM ERP_HOADONNCC_PHIEUNHAP PO1   \n" +
						"	                      WHERE PO1.hoadonncc_fk = hoadon.pk_seq \n" +
						"                         For XML PATH ('')) [phongbanChon_fk]  \n" +
						"       FROM ERP_HOADONNCC_PHIEUNHAP PO  \n" +
						"       WHERE PO.hoadonncc_fk = hoadon.pk_seq \n" +
						"          ), '' )   as POID, hoadon.ttId, hoadon.tigia,  hoadon.ngaydenhantt \n" +
						" FROM ( \n" +
						"       SELECT a.pk_seq, a.kyhieu, a.sohoadon, a.ngayhoadon, a.sotienAVAT, b.TIENTE_FK as ttId, b.TIGIA, a.SOTIENVIET, a.ngaydenhantt \n" +
						"       FROM ERP_HOADONNCC a INNER JOIN ERP_PARK b on a.park_fk = b.pk_seq \n" +
						"            LEFT JOIN ERP_HOADONNCC_PHIEUNHAP hd_pn on a.pk_seq = hd_pn.hoadonncc_fk \n"+
						"			 LEFT JOIN ERP_YEUCAUKIEMDINH yckd on hd_pn.phieunhan_fk = yckd.NHANHANG_FK \n"+
						"       WHERE b.ncc_fk = '" + this.nccId + "' and b.trangthai = '2' and a.trangthai = '0' \n"+
						"			  and b.CONGTY_FK = "+this.congtyId +
						"			  and ( yckd.PK_SEQ is null OR ( yckd.PK_SEQ is not null and ISNULL(yckd.THIEUHOSO,0) <> 1 ) ) \n";
						if(this.id.length() > 0)
						{
							query += "and a.pk_seq not in (select hoadon_fk from ERP_THANHTOANHOADON_HOADON where thanhtoanhd_fk = '" + this.id + "') \n";
						}
				query += " ) hoadon \n" +
						" LEFT JOIN " +
						" ( \n" +
						"   SELECT TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n" +
						"   FROM ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
						"   WHERE TTHD.LOAIHD = 0 AND TT.TRANGTHAI !=2 and TTHD.HOADON_FK in (select pk_seq from ERP_HOADONNCC where trangthai = 0)  \n" + //sua trang thai !=2 thanh =1
						"			and TT.CONGTY_FK = "+this.congtyId+
						"   GROUP BY HOADON_FK \n" +
						
						" union all \n"
						
						
					   +"SELECT XOANONCC_TU.HOADONNCC_FK , sum(XOANONCC_TU.tienthanhtoan) as DATHANHTOAN \n"
					   +"FROM ERP_XOANONCC XOANONCC \n "
					   +"inner join ERP_XOANONCC_HOADONNCC XOANONCC_TU on XOANONCC.PK_SEQ = XOANONCC_TU.XOANONCC_FK \n"
					   +"WHERE XOANONCC_TU.LOAICT = 0 AND XOANONCC.TRANGTHAI !=2 and XOANONCC_TU.hoadonncc_fk in (select pk_seq from ERP_HOADONNCC where trangthai = 0) \n"  
					   +"and XOANONCC.CONGTY_FK = 100000   GROUP BY HOADONNCC_FK "

						+"  ) dathanhtoan \n" +
						"   on hoadon.pk_seq = dathanhtoan.hoadon_fk \n" +
						"WHERE hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0')  > 0 and hoadon.ttId = " + this.tienteId + " ) \n" ;
											
				// LOAIHD: 3 - CHIPHINHANHANG
					if(this.id.length() > 0)
					{
						query +=" UNION ALL \n";
					
						query +=" SELECT distinct 3 as LOAIHD, cpct.pk_seq , cpct.SOCHUNGTU as sohoadon, cpct.KYHIEUCHUNGTU as kyhieu , cpct.NGAYCHUNGTU as ngayhoadon \n" +
								" 		,(cpct.TIENHANG + (cpct.TIENHANG*(cpct.THUESUAT/100))) as SOTIENGOC,tt.sotienavat as sotienno, tt.SOTIENTT as sotientt  , cast(mh.PK_SEQ as nvarchar(50)) as POID, isnull(cp.TIENTE_FK,'100000') as ttId,  isnull(cp.TIGIA,1) as TIGIA, cp.NGAYDENHANTT \n" +
								" FROM ERP_CHIPHINHAPKHAU_CHITIET cpct \n" +
								" LEFT JOIN ERP_CHIPHINHAPKHAU cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n" +
								" LEFT JOIN ERP_NHANHANG nh on nh.PK_SEQ = cp.nhanhang_fk \n" +
								" LEFT JOIN ERP_MUAHANG mh on mh.PK_SEQ = nh.MUAHANG_FK  \n" +
								" LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ  AND TT.LOAIHD = 3 \n" +
								" LEFT JOIN ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
								" WHERE cp.NCCID_CN = '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and cp.CONGTY_FK = "+this.congtyId+" \n";
					}
					
					query += " UNION ALL \n"; 
					
					query +="SELECT distinct 3 as LOAIHD, cpct.pk_seq ,cpct.SOCHUNGTU as sohoadon,  cpct.KYHIEUCHUNGTU as kyhieu , cpct.NGAYCHUNGTU as ngayhoadon, \n" +
							" 		 (cpct.TIENHANG + (cpct.TIENHANG*(cpct.THUESUAT/100))) as SOTIENGOC," +
							"  		 case when (tt.SOTIENAVAT is null OR t.TRANGTHAI=2) then (cpct.TIENHANG + (cpct.TIENHANG*(cpct.THUESUAT/100))) else tt.CONLAI end as sotienno \n" +
							" 		 ,'0' as sotientt, cast(mh.PK_SEQ as nvarchar(50)) as POID, isnull(cp.TIENTE_FK,'100000') as ttId,  isnull(cp.TIGIA,1) as TIGIA , cp.NGAYDENHANTT  \n" +
							"FROM 	ERP_CHIPHINHAPKHAU_CHITIET cpct \n" +
							"LEFT JOIN (SELECT PK_SEQ, nhanhang_fk, tiente_fk, NCCID_CN, trangthai, tigia, NGAYDENHANTT FROM ERP_CHIPHINHAPKHAU WHERE CONGTY_FK ="+this.congtyId+") cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n" +
							"LEFT JOIN (SELECT PK_SEQ, MUAHANG_FK FROM ERP_NHANHANG WHERE CONGTY_FK = "+this.congtyId+") nh on nh.PK_SEQ = cp.nhanhang_fk \n" +
							"LEFT JOIN (SELECT PK_SEQ FROM ERP_MUAHANG WHERE CONGTY_FK = "+this.congtyId+") mh on mh.PK_SEQ = nh.MUAHANG_FK  \n" +
							"LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ  AND TT.LOAIHD = 3 \n" +
							"LEFT JOIN (SELECT PK_SEQ, TRANGTHAI FROM ERP_THANHTOANHOADON WHERE CONGTY_FK = "+this.congtyId+") t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
							"WHERE  isnull(cp.TIENTE_FK,'100000') = "+ this.tienteId +" and cp.NCCID_CN = '"+this.nccId+"' and ( tt.CONLAI is null or (tt.CONLAI > 0  \n" +
							"		and tt.HOADON_FK not in  \n" +
							"		(SELECT distinct tt.HOADON_FK  \n" +
							"		 FROM 	ERP_CHIPHINHAPKHAU_CHITIET cpct  \n" +
							"		 LEFT JOIN ERP_CHIPHINHAPKHAU cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n" +
							"		 LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ  AND TT.LOAIHD = 3 \n" +
							"		 WHERE 	cp.NCCID_CN = '"+this.nccId+"' and tt.CONLAI = 0 and cp.TIENTE_FK= "+ this.tienteId +" AND cp.CONGTY_FK = "+this.congtyId+") \n" +
							"				and tt.THANHTOANHD_FK in \n" +
							"				(SELECT MAX(tt.THANHTOANHD_FK)  \n" +
							"				 FROM 	ERP_CHIPHINHAPKHAU_CHITIET cpct    \n" +
							"				 LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ AND TT.LOAIHD = 3 \n" +
							"				 LEFT JOIN ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk  \n" +
							"				 LEFT JOIN ERP_CHIPHINHAPKHAU cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n" +
							"				 WHERE 	cp.trangthai = 1 and cp.TIENTE_FK= "+ this.tienteId +"  and  cp.NCCID_CN =  '"+this.nccId+"' and t.TRANGTHAI<>2	 \n" +
							"						and cp.CONGTY_FK = "+this.congtyId+
							"						 		group by tt.HOADON_FK ) ) ) \n" +
							"					 	and cp.trangthai = 1  \n" ;
							if(this.id.length() >0)
							{
								query+= " and cpct.pk_seq not in (SELECT HOADON_FK from ERP_THANHTOANHOADON_HOADON WHERE THANHTOANHD_FK = "+ this.id +" ) \n";
							}
								
				// LOAIHD: 5 - CHIPHIKHAC					
					if(this.id.length() > 0)
					{
					 query +=" UNION ALL \n"; 
					 query +=" SELECT distinct 5 as LOAIHD, cp.pk_seq , cast (cp.PK_SEQ as nvarchar(50)) as sohoadon, cp.DIENGIAI as kyhieu ,  cp.NGAY as ngayhoadon, (cpct.TONGTIENCHUATHUE + cpct.THUE ) as SOTIENGOC \n" +
							"      ,case when cp.TIENTE_FK= '100000' then tt.sotienavat else tt.SOTIENNT end as sotienno, tt.SOTIENTT as sotientt  , '' as POID, cp.TIENTE_FK as ttId, \n" +
							"      isnull(cp.tigia,1) as tigia , isnull(cp.ngaydenhantt,'') as ngaydenhantt \n" +
							" FROM ERP_CHIPHIKHAC_CHITIET cpct \n" +
							" LEFT JOIN ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n" +
							" LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n" +
							" LEFT JOIN ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
							" WHERE cp.DOITUONG = '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and cp.CONGTY_FK ="+this.congtyId;
				
					 
					}
					query += " UNION ALL \n"; 		
					
					query += "SELECT distinct 5 as LOAIHD, cp.pk_seq , cast (cp.PK_SEQ as nvarchar(50))  sohoadon, cp.DIENGIAI as kyhieu,  cp.NGAY as ngayhoadon, (cpct.TONGTIENCHUATHUE + cpct.THUE - isnull(xoatamung.dathanhtoan,0)) as SOTIENGOC \n" +
							"	   ,case when (tt.SOTIENAVAT is null OR t.TRANGTHAI=2) then (cpct.TONGTIENCHUATHUE + cpct.THUE - isnull(xoatamung.dathanhtoan,0)) else tt.CONLAI end as sotienno \n" +
							"	   ,'0' as sotientt, '' as POID , cp.TIENTE_FK as ttId, isnull(cp.TIGIA,1) as TIGIA, \n" +
							"      isnull(cp.ngaydenhantt,'') as ngaydenhantt  \n" +
							"FROM ERP_CHIPHIKHAC_CHITIET cpct \n" +
							"LEFT JOIN ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK\n" +
							"LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n" +
							"LEFT JOIN ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
							"LEFT JOIN (SELECT a.cttt_fk,SUM(a.tienthanhtoan) as dathanhtoan \n" +
							"	        FROM ERP_XOAKHTRATRUOC_CTTT a INNER JOIN ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk=b.PK_SEQ \n" +
							"	        WHERE b.TRANGTHAI=1 and b.TIENTE_FK= "+ this.tienteId +" and b.LOAIXOATRATRUOC ='1' and isnull(b.NCC_FK, b.NHANVIEN_FK)= "+ this.nccId +" \n" +
							"				  and b.CONGTY_FK = "+this.congtyId +
							"	        GROUP BY a.cttt_fk) xoatamung  on xoatamung.cttt_fk = cp.PK_SEQ \n" +
							"WHERE cp.DOITUONG = '"+this.nccId+"' and cp.TIENTE_FK= "+ this.tienteId +" and ( tt.CONLAI is null or (tt.CONLAI > 0 \n" +
							"	   and tt.HOADON_FK not in \n" +
							"	   (SELECT distinct tt.HOADON_FK \n" +
							"		FROM ERP_CHIPHIKHAC_CHITIET cpct \n" +
							"		LEFT JOIN ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n" +
							"		LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n" +
							"		WHERE cp.DOITUONG = '"+this.nccId+"' and tt.CONLAI = 0 and cp.CONGTY_FK = "+this.congtyId+") \n" +
							"			  and tt.THANHTOANHD_FK in \n" +
							"			  (SELECT MAX(tt.THANHTOANHD_FK) \n" +
							"			   FROM ERP_CHIPHIKHAC_CHITIET cpct   \n" +
							"			   LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n" +
							"			   LEFT JOIN ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n" +
							"			   LEFT JOIN ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK\n" +
							"			   WHERE cp.trangthai = 1    and  cp.DOITUONG =  '"+this.nccId+"' and t.TRANGTHAI<>2 and cp.CONGTY_FK = "+this.congtyId+
							"			   GROUP BY tt.HOADON_FK ) ) )\n" +
							"			  and cp.trangthai = 1  and cp.LOAI= '0' \n";
							
							if(this.id.length() > 0)
							{
								query += " and cp.pk_seq not in (select HOADON_FK from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ this.id +"  ) \n";
							}
							query += " and ((cpct.TONGTIENCHUATHUE + cpct.THUE - isnull(xoatamung.dathanhtoan,0) ) > 0 or tt.CONLAI > 0) \n";
					
			// LOAIHD: 6 - DENGHITHANHTOAN							 
				  if(this.id.length() > 0)
				  {
					query += " UNION ALL \n"; 
					
					query += " SELECT 6 as LOAIHD, mh.PK_SEQ, cast (mh.PK_SEQ as nvarchar(50)) sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , \n" +
							"        mh.TONGTIENAVAT as sotiengoc, CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
							"		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA, mh.ngaydenhantt  \n"+
							"  FROM ERP_MUAHANG mh \n" +
							"  INNER JOIN ERP_DUYETMUAHANG dmh on mh.PK_SEQ = dmh.MUAHANG_FK and dmh.CHUCDANH_FK = '100008' and dmh.TRANGTHAI = 1 \n" +
							"  LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ  AND TT.LOAIHD = 6 \n"+
							"  LEFT JOIN ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"+
							"  INNER JOIN ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
							"  WHERE mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" +this.id+ "' and mh.CONGTY_FK = "+this.congtyId+"  \n";
																			
									
				  }
					query += " UNION ALL \n"; 
					
					query += " SELECT 6 as LOAIHD, mh.PK_SEQ, cast ( mh.PK_SEQ as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon \n"+
							" 		,mh.TONGTIENAVAT as sotiengoc, mh.TONGTIENAVAT - isnull(t.SOTIENTT,0) as SOTIENNO ,0 as sotientt, '' as POID  \n"+
							" 		,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA,  mh.ngaydenhantt  \n"+
							"  FROM ERP_MUAHANG mh \n" +
							"       INNER JOIN ERP_DUYETMUAHANG dmh on mh.PK_SEQ = dmh.MUAHANG_FK and dmh.CHUCDANH_FK = '100008' and dmh.TRANGTHAI = 1 \n" +
							" 		LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"+
							" 		LEFT JOIN ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk  \n"+
							" 		INNER JOIN ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
							"  WHERE mh.NHACUNGCAP_FK = '" + this.nccId + "' \n" +
							" 		 and ( tt.CONLAI is null or  \n" +
							"        (tt.CONLAI > 0 \n"+
							"		 and mh.TIENTE_FK = "+ this.tienteId +" \n"+
							" 		 and tt.HOADON_FK not in \n"+
							"		 (SELECT distinct tt.HOADON_FK \n"+
							"		  FROM ERP_MUAHANG mh \n"+
							"         INNER JOIN ERP_DUYETMUAHANG dmh on mh.PK_SEQ = dmh.MUAHANG_FK and dmh.CHUCDANH_FK = '100008' and dmh.TRANGTHAI = 1 \n" +
							"		  LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"+
							"		  LEFT JOIN ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
							"		  WHERE mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.CONLAI = 0 and mh.TIENTE_FK = "+ this.tienteId +" and mh.CONGTY_FK = "+this.congtyId+") \n"+
							"  				and tt.THANHTOANHD_FK in \n"+
							"				(SELECT MAX(tt.THANHTOANHD_FK) \n"+
							"				 FROM Erp_MuaHang mh  \n"+
							"                INNER JOIN ERP_DUYETMUAHANG dmh on mh.PK_SEQ = dmh.MUAHANG_FK and dmh.CHUCDANH_FK = '100008' and dmh.TRANGTHAI = 1 \n" +
							"				 LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"+
							" 				 LEFT JOIN ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n"+
							"				 LEFT JOIN ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
							"				 WHERE mh.LOAIHANGHOA_FK = '2' and TYPE = '0'  and mh.TIENTE_FK = "+ this.tienteId +"  and  mh.NHACUNGCAP_FK =  '" + this.nccId + "' and t.TRANGTHAI<>2	\n"+ //trang thai mh=2 da hoan tat
							"					   and mh.CONGTY_FK = "+this.congtyId +
							"	 			 GROUP BY tt.HOADON_FK ) ) ) \n"+
							" 		and  mh.LOAIHANGHOA_FK = '2' and mh.TYPE ='0' and mh.CONGTY_FK ="+this.congtyId;
					
							if(this.id.length() > 0)
							{
								query+= " and mh.PK_SEQ not in (select HOADON_FK from  ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ this.id +") \n" ;
							}
							
					query+=	"       and  mh.TONGTIENAVAT  - isnull(t.SOTIENTT,0) > 0 \n";				*/		
			
		
			query = "EXEC GET_CANTRUCONGNO_HOADON "
				+ this.nccId + ","
				+ (this.id!=null && this.id.trim().length()==0? "0" : this.id) + " ,"
				+ this.tienteId + " ,"
				+ " 0, 0, " + this.congtyId + ", '"+this.ngayghinhan+"'";
		
							    
			System.out.println("Query khoi tao hoa don NCC: " + query);
			
			ResultSet rsHoadon = db.get(query);
			List<IHoadon> hdList = new ArrayList<IHoadon>();
			 
				try 
				{
					IHoadon hd = null;
					while(rsHoadon.next())
					{
						String id = rsHoadon.getString("pk_seq");
						String kyhieu = rsHoadon.getString("kyhieu");
						String sohoadon = rsHoadon.getString("sohoadon");
						String ngayhd = rsHoadon.getString("ngayhoadon");
//						String sotiengoc = formatter.format(rsHoadon.getDouble("sotiengoc")* rsHoadon.getDouble("tigia"));
//						String sotienno = formatter.format(rsHoadon.getDouble("TIENAVAT")* rsHoadon.getDouble("tigia"));
						String sotiengoc = formatter.format(rsHoadon.getDouble("sotiengoc"));
						String sotienno = formatter.format(rsHoadon.getDouble("TIENAVAT"));
						String loaihd = rsHoadon.getString("loaiCT");
						String dathanhtoan = "0";
						if(this.id.length() > 0)
						{
							dathanhtoan = formatter.format(rsHoadon.getDouble("SOTIENTT"));
						}
						
						hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, sotiengoc, "", dathanhtoan, "");
						hd.setSoPO(rsHoadon.getString("SOHOADON"));
						hd.setTienteId("100000");
						hd.setTigia("1");
						hd.setLoaihd1(loaihd);
						hd.setSotienno(sotienno) ;
						hdList.add(hd);
					}
					rsHoadon.close();
				} 
				catch (Exception e) { 
					e.printStackTrace();
					System.out.println("Error Here : "+e.toString());
				}
			 
			this.hoadonList = hdList;
			System.out.println("So hoa don: " + this.hoadonList.size());
		}
	
	}
	
	public void createRS3()
	{

		System.out.println("AHHFHAFAF");
		this.nccRs = db.get("select pk_seq, ma + ', ' + ten as nccTen from erp_nhacungcap where trangthai = '1' and duyet = '1'");
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1'");
		this.nganhangRs = db.get("select pk_seq, ma + ', ' + ten as nhTen from erp_nganhang where PK_SEQ in ( select NganHang_FK from ERP_NGANHANG_CONGTY where congty_fk = '"+this.congtyId+"' ) ");
		
		if(this.htttId.equals("100001"))
		{
			if(this.nganhangId.length() > 0)
			{
				this.chinhanhRs = db.get("select pk_seq, ma + ', ' + ten as cnTen from erp_chinhanh where PK_SEQ in ( select ChiNhanh_FK from ERP_NGANHANG_CONGTY where congty_fk = '"+this.congtyId+"' )");
			
			}
			
			if(this.chinhanhId.length() > 0)
			{
				String sql = "select sotaikhoan from ERP_NGANHANG_CONGTY where congty_fk = '"+this.congtyId+"' and chinhanh_fk = '" + this.chinhanhId + "' and nganhang_fk = '" + this.nganhangId + "' ";
				
				ResultSet rs = db.get(sql);
				if(rs != null)
				{
					try
					{
						if(rs.next())
						{
							this.sotaikhoan = rs.getString("sotaikhoan");
						}
						rs.close();
					} 
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
				
		if( (this.nccId.length() > 0 || this.NhanvienId.length() >0) && this.htttId.length() > 0 && this.hoadonList.size() <= 0)
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			
			String query = "";
/*
			//LOAIHD: 0 - HOADONNCC
				query +=" SELECT 0 as LOAIHD, b.pk_seq, isnull(b.sohoadon, '') as sohoadon,  isnull(b.kyhieu, '') as kyhieu, b.ngayhoadon, \n" +
						"         b.sotienAVAT as SOTIENGOC , b.sotienAVAT - isnull(thanh_toan.tongthanhtoan, '0') as sotienNO, SOTIENTT, \n" +
						" ISNULL( (  \n" +
						"			SELECT distinct  \n" +
						"               ( SELECT cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()]  \n" +
						"				  FROM ERP_HOADONNCC_PHIEUNHAP PO1   \n" +
						"			      WHERE PO1.hoadonncc_fk = b.pk_seq \n" +
						"			      For XML PATH ('')) [phongbanChon_fk]  \n" +
						"			FROM ERP_HOADONNCC_PHIEUNHAP PO  \n" +
						"			WHERE PO.hoadonncc_fk = b.pk_seq \n" +
						"            ), '' )   as POID, c.TIENTE_FK AS TTID, c.TIGIA, b.NGAYDENHANTT \n" +
						" FROM ERP_THANHTOANHOADON_HOADON a \n" +
						" INNER JOIN ERP_HOADONNCC b on a.hoadon_fk = b.pk_seq  and a.loaihd = 0  \n" +
						" INNER JOIN ERP_PARK c on b.park_fk = c.pk_seq \n" +
						" LEFT JOIN	\n" +
						"   ( SELECT TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
						"     FROM ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
						"     WHERE TTHD.LOAIHD = 0 AND TT.trangthai != 2 and TTHD.thanhtoanhd_fk != '" + this.id + "' \n" +  //sua trang thai !=2 thanh =1
						"           and TTHD.hoadon_fk in ( SELECT hoadon_fk \n" +
				        "									FROM ERP_THANHTOANHOADON_HOADON \n" +
						"									WHERE thanhtoanhd_fk = '" + this.id + "' and loaihd = 0 ) \n" +
						"     GROUP BY hoadon_fk) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
						" WHERE a.thanhtoanhd_fk = '" + this.id + "' and c.TIENTE_FK = " + this.tienteId + " and b.CONGTY_FK = "+this.congtyId+" \n" +
						
						" UNION ALL \n";
			
			// LOAIHD: 3 - CHIPHINHANHANG

				query += "SELECT distinct 3 as LOAIHD, cpct.pk_seq , cpct.SOCHUNGTU as sohoadon, cpct.KYHIEUCHUNGTU as kyhieu , cpct.NGAYCHUNGTU as ngayhoadon \n" +
						" ,(cpct.TIENHANG + (cpct.TIENHANG*(cpct.THUESUAT/100))) as SOTIENGOC,tt.sotienavat as sotienno, tt.SOTIENTT as sotientt  , cast(mh.PK_SEQ as nvarchar(50)) as POID, isnull(cp.TIENTE_FK,'100000') as ttId,  isnull(cp.TIGIA,1) as TIGIA, cp.NGAYDENHANTT \n" +
						" FROM ERP_CHIPHINHAPKHAU_CHITIET cpct \n" +
						" LEFT JOIN ERP_CHIPHINHAPKHAU cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n" +
						" LEFT JOIN ERP_NHANHANG nh on nh.PK_SEQ = cp.nhanhang_fk \n" +
						" LEFT JOIN ERP_MUAHANG mh on mh.PK_SEQ = nh.MUAHANG_FK  \n" +
						" LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ  AND TT.LOAIHD = 3 \n" +
						" LEFT JOIN ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
						" WHERE cp.NCCID_CN = '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and cp.CONGTY_FK ="+this.congtyId+"  \n";
															
				
			// LOAIHD: 5 - CHIPHIKHAC					
				 query +=" UNION ALL \n"; 
				 
				 query +=
					 	"SELECT distinct 5 as LOAIHD, cp.pk_seq ,cp.PK_SEQ as sohoadon, cp.DIENGIAI as kyhieu ,  cp.NGAY as ngayhoadon, (cpct.TONGTIENCHUATHUE + cpct.THUE ) as SOTIENGOC \n" +
						"      ,case when cp.TIENTE_FK= '100000' then tt.sotienavat else tt.SOTIENNT end as sotienno, tt.SOTIENTT as sotientt  , '' as POID, cp.TIENTE_FK as ttId, \n" +
						"      isnull(cp.tigia,1) as tigia , isnull(cp.ngaydenhantt,'') as ngaydenhantt \n" +
						"FROM ERP_CHIPHIKHAC_CHITIET cpct \n" +
						"LEFT JOIN ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n" +
						"LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n" +
						"LEFT JOIN ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
						"WHERE cp.DOITUONG = '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and cp.CONGTY_FK = "+this.congtyId+"  \n";
			
			// LOAIHD: 6 - DENGHITHANHTOAN							 
				query += " UNION ALL \n"; 
				
				query +=" SELECT 6 as LOAIHD, mh.PK_SEQ, mh.PK_SEQ as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , \n" +
						"        mh.TONGTIENAVAT as sotiengoc, CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
						"		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA, mh.ngaydenhantt  \n"+
						" FROM ERP_MUAHANG mh \n" +
						" LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ  AND TT.LOAIHD = 6 \n"+
						" LEFT JOIN ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"+
						" INNER JOIN ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
						" WHERE   mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" +this.id+ "' AND mh.CONGTY_FK = "+this.congtyId+" \n";
											*/	
			
			query = "EXEC GET_CANTRUCONGNO_HOADON "
			    + this.nccId + ","
			    + (this.id!=null && this.id.trim().length()==0? "0" : this.id) + " ,"
			    + this.tienteId + " ,"
			    + " 0, 0, " + this.congtyId + ", '"+this.ngayghinhan+"'";
			  
			System.out.println("Query khoi tao hoa don Display: " + query);
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
//						String sotiengoc = formatter.format(rsHoadon.getDouble("sotiengoc")* rsHoadon.getDouble("tigia"));
//						String sotienno = formatter.format(rsHoadon.getDouble("TIENAVAT")* rsHoadon.getDouble("tigia"));
						String sotiengoc = formatter.format(rsHoadon.getDouble("sotiengoc"));
						String sotienno = formatter.format(rsHoadon.getDouble("TIENAVAT"));
						String loaihd = rsHoadon.getString("loaiCT");
						String dathanhtoan = "0";
						if(this.id.length() > 0)
						{
							dathanhtoan = formatter.format(rsHoadon.getDouble("SOTIENTT"));
						}
						
						hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, sotiengoc, "", dathanhtoan, "");
						hd.setSoPO(rsHoadon.getString("SOHOADON"));
						hd.setTienteId(rsHoadon.getString("TTID"));
//						hd.setTigia(rsHoadon.getString("tigia"));
						hd.setLoaihd1(loaihd);
						hd.setSotienno(sotienno) ;
						hdList.add(hd);
					}
					rsHoadon.close();
				} 
				catch (Exception e) { 
					e.printStackTrace();
				}
			}
			this.hoadonList = hdList;
			System.out.println("So hoa don: " + this.hoadonList.size());
		}
	
	}

	public static void main(String[] args) {
		ErpThanhtoanhoadonCanTru erpThanhtoanhoadon = new ErpThanhtoanhoadonCanTru();
		erpThanhtoanhoadon.setId("100079");
		List<String> hienTai = erpThanhtoanhoadon.GetlistHoaDonHienTai();
		System.out.println(hienTai.toString());
	}

	public void createRS2_BK()
	{

		this.nccRs = db.get("select pk_seq, ma + ', ' + ten as nccTen from erp_nhacungcap where trangthai = '1' and duyet = '1'");
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1'");
		this.nganhangRs = db.get("select pk_seq, ma + ', ' + ten as nhTen from erp_nganhang where PK_SEQ in ( select NganHang_FK from ERP_NGANHANG_CONGTY where congty_fk = '100005' ) ");
		
		if(this.htttId.equals("100001"))
		{
			if(this.nganhangId.length() > 0)
			{
				this.chinhanhRs = db.get("select pk_seq, ma + ', ' + ten as cnTen from erp_chinhanh where PK_SEQ in ( select ChiNhanh_FK from ERP_NGANHANG_CONGTY where congty_fk = '100005' )");
				
			}
			
			if(this.chinhanhId.length() > 0)
			{
				String sql = "select sotaikhoan from ERP_NGANHANG_CONGTY where congty_fk = '100005' and chinhanh_fk = '" + this.chinhanhId + "' and nganhang_fk = '" + this.nganhangId + "' ";
				System.out.println("[ErpThanhtoanhoadon.createRs] sotaikhoan sql = " + sql);
				ResultSet rs = db.get(sql);
				if(rs != null)
				{
					try
					{
						if(rs.next())
						{
							this.sotaikhoan = rs.getString("sotaikhoan");
						}
						rs.close();
					} 
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("Loai thanh toan : "+this.LoaiThanhToan);
		
		if( (this.nccId.length() > 0 || this.NhanvienId.length() >0) && this.htttId.length() > 0 && this.hoadonList.size() <= 0)
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			
			String query = "";
			if(this.LoaiThanhToan.equals("1"))
			{
				// cấn trừ công nợ
				
				if(this.id.length() > 0)
				{
				query += " select isnull(c.TIGIA,0) as tigia, b.pk_seq, b.kyhieu, b.sohoadon, b.ngayhoadon, b.sotienAVAT AS SOTIENGOC, \n" +
						"         b.sotienAVAT - isnull(thanh_toan.tongthanhtoan, '0') as SOTIENNO, SOTIENTT, \n" +
						"         ISNULL( (  \n" +
						"                  Select distinct ( Select cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()] \n" +
						"                                    From ERP_HOADONNCC_PHIEUNHAP PO1   \n" +
						"                                    Where PO1.hoadonncc_fk = b.pk_seq \n" +
						"                                    For XML PATH ('')) [phongbanChon_fk]  \n" +
						"                  From ERP_HOADONNCC_PHIEUNHAP PO  \n" +
						"                  Where PO.hoadonncc_fk = b.pk_seq \n" +
						"               ), '' )   as POID, c.TIENTE_FK AS TTID \n" +
						"from ERP_THANHTOANHOADON_HOADON a \n" +
						"     inner join ERP_HOADONNCC b on a.hoadon_fk = b.pk_seq \n" +
						"     inner join ERP_PARK c on b.park_fk = c.pk_seq \n" +				
						"     left join	\n" +
						"     ( select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
						"	    from ERP_THANHTOANHOADON_HOADON TTHD \n" +
						"	         inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
						"	    where TT.trangthai !=2 and TTHD.thanhtoanhd_fk != '" + this.id + "' \n" +  //sua trang thai !=2 thanh =1
						" 	          and TTHD.hoadon_fk in (select hoadon_fk from ERP_THANHTOANHOADON_HOADON where thanhtoanhd_fk = '" + this.id + "') \n" +
						"	    group by hoadon_fk \n" +
						"     ) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
						"where a.thanhtoanhd_fk = '" + this.id + "' and c.TIENTE_FK = " + this.tienteId + " \n" +
						" union all \n";
				}
				
				query +=" (  " +
						" select isnull(hoadon.TIGIA,1) as tigia , hoadon.pk_seq, ISNULL(hoadon.kyhieu,'') AS KYHIEU , hoadon.sohoadon, hoadon.ngayhoadon, hoadon.sotienAVAT AS SOTIENGOC, \n" +
						"        hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') as SOTIENNO, 0 as sotientt, \n" +
						"        ISNULL( (  \n" +
						"                Select distinct ( Select cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()]  \n" +
						"                                  From ERP_HOADONNCC_PHIEUNHAP PO1   \n" +
						"                                  Where PO1.hoadonncc_fk = hoadon.pk_seq \n" +
						"                                  For XML PATH ('')) [phongbanChon_fk]  \n" +
						"                From ERP_HOADONNCC_PHIEUNHAP PO  \n" +
						"                Where PO.hoadonncc_fk = hoadon.pk_seq \n" +
						"               ), '' )   as POID, hoadon.TTID \n" +
						" from ( \n" +
						"       select a.pk_seq, a.kyhieu, a.sohoadon, a.ngayhoadon, a.sotienAVAT, b.TIENTE_FK as ttId, b.TIGIA, a.SOTIENVIET \n" +
						"       from ERP_HOADONNCC a \n" +
						"            inner join ERP_PARK b on a.park_fk = b.pk_seq \n" +
						"       where b.ncc_fk = '" + this.nccId + "' and b.trangthai = '2' and a.trangthai = '0' \n";
							if(this.id.length() > 0)
							{
								query += "and a.pk_seq not in (select hoadon_fk from ERP_THANHTOANHOADON_HOADON where thanhtoanhd_fk = '" + this.id + "') \n";
							}
					query += ") hoadon \n" +
						"    left join \n" +
						"    ( \n" +
						"     select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n" +
						"     from ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
						"     where TT.TRANGTHAI !=2 and TTHD.HOADON_FK in (select pk_seq from ERP_HOADONNCC where trangthai = 0)  \n" + //sua trang thai !=2 thanh =1
						"     group by HOADON_FK \n" +
						"     ) dathanhtoan \n" +
						"     on hoadon.pk_seq = dathanhtoan.hoadon_fk \n" +
						" where hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') > 0 and hoadon.ttId = " + this.tienteId + " ) \n" ;

					
			}else if(this.LoaiThanhToan.equals("0"))
			{
				if(this.id.length() >0)
				{
					query=
					" SELECT TU.PK_SEQ ,TU.PK_SEQ as sohoadon ,TU.NGAYTAMUNG AS ngayhoadon,N'TẠM ỨNG'  AS KYHIEU ,TU.NHANVIEN_FK ,TU.NCC_FK,SOTIENTAMUNG \n"+ 
					"       -( SELECT ISNULL(SUM(b.SOTIENTT),0) \n"+
					"          FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
					"          WHERE A.TRANGTHAI <>2 and a.pk_seq <> "+this.id+"  AND HOADON_FK=TU.PK_SEQ) AS sotienAVAT ,\n"+
					"        0 AS SOTIENTT, '' as POID, TU.TIENTE_FK AS TTID  \n"+
					"   FROM ERP_TAMUNG TU \n"+
					"	WHERE ISNULL(TU.HOANTAT,'0')='0'  and TU.pk_seq not in  \n"+
					"         (select hoadon_fk \n"+
					"          from ERP_THANHTOANHOADON a \n"+
					"               inner join  ERP_THANHTOANHOADON_HOADON b on a.pk_seq=b.thanhtoanhd_fk \n"+
					"          where a.trangthai <>'2' and  b.thanhtoanhd_fk="+this.id+"  )  and  (SOTIENTAMUNG - \n"+
					"              ( SELECT ISNULL(SUM(b.SOTIENTT),0) \n"+
					"                FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
					"                WHERE A.TRANGTHAI <>2 and a.pk_seq <> "+this.id+"  AND HOADON_FK=TU.PK_SEQ) > 0 ) \n"; 
					

					query=query +" and   tu.NCC_FK="+ (this.nccId ==""?"0":this.nccId) ;

					
					query=query + " UNION ALL " +
					"  select hoadon_fk AS SOCHUNGTU,hoadon_fk AS SOHOADON ,a.ngaytamung as ngayhoadon , N'TẠM ỨNG ' as   KYHIEU, a.NHANVIEN_FK, a.NCC_FK, "+
					"  a.sotientamung -( 	SELECT ISNULL(SUM(CT.SOTIENTT),0) " +
					" 						FROM ERP_THANHTOANHOADON_HOADON CT " +
					" 						INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=CT.THANHTOANHD_FK "+ 
					" 						WHERE A.TRANGTHAI<>'2'  AND CT.HOADON_FK=B.HOADON_FK and a.pk_seq <>"+this.id+" " +
					" )AS sotienAVAT ,sotientt, '' as POID, a.TIENTE_FK AS TTID " +
					" from ERP_THANHTOANHOADON_HOADON b "+
					" inner join ERP_TAMUNG a on a.pk_seq=b.hoadon_fk  " +
					" and ( a.sotientamung -( SELECT ISNULL(SUM(CT.SOTIENTT),0) FROM ERP_THANHTOANHOADON_HOADON CT INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=CT.THANHTOANHD_FK "+ 
					" WHERE A.TRANGTHAI<>'2'  AND CT.HOADON_FK=B.HOADON_FK and a.pk_seq <>"+this.id+" ) >0)"+
					" where thanhtoanhd_fk="+this.id ;
					/*if(this.NhanvienId.length()==0){
						
					}else{
						
					}*/
					
					
					
				}else{ 
					
					query=" SELECT TU.PK_SEQ ,TU.PK_SEQ as sohoadon ,TU.NGAYTAMUNG AS ngayhoadon,N'TẠM ỨNG'  AS KYHIEU ,TU.NHANVIEN_FK ,TU.NCC_FK,SOTIENTAMUNG   "+ 
						" -( SELECT ISNULL(SUM(b.SOTIENTT),0) FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  " +
						" WHERE A.TRANGTHAI<>'2'  AND HOADON_FK=TU.PK_SEQ) AS sotienAVAT , 0 AS SOTIENTT, '' as POID, TU.TIENTE_FK AS TTID  "+
						" FROM ERP_TAMUNG TU " +
						" WHERE ISNULL(TU.HOANTAT,'0')='0'  " +
						" and  (SOTIENTAMUNG   " + 
						" -( SELECT ISNULL(SUM(b.SOTIENTT),0) FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  " +
						" WHERE A.TRANGTHAI<>'2'  AND HOADON_FK=TU.PK_SEQ) >0 )";
					if(this.DoiTuongTamUng.equals("0")){
						//ung cho ncc
						query=query +" and   NHANVIEN_FK="+ (this.NhanvienId ==""?"0":this.NhanvienId) ;
					}else{
						query=query +" and   NCC_FK="+ (this.nccId ==""?"0":this.nccId) ;
					}
					
				}
			}
			else{ // loai thanh toan la noi bo 
				if(this.id.length() > 0){
					query = "select mh.PK_SEQ, mh.PK_SEQ as sohoadon,  N'Chi phí nội bộ'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , " +
							"tt.sotienavat as sotienavat, tt.SOTIENTT as sotientt, '' as POID , tt.SOTIENTT as DATHANHTOAN, mh.TIENTE_FK AS TTID "+
							"from Erp_MuaHang mh " +
							"left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ"+
							"left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
							"where ncc.NOIBO = 1 and  mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" +this.id+ "'";
					
					query += " union " ;
					
					query += " select mh.PK_SEQ, mh.PK_SEQ as sohoadon,  N'Chi phí nội bộ'  AS KYHIEU , mh.NGAYMUA as ngayhoadon "+
					" ,case when (tt.SOTIENAVAT is null OR t.TRANGTHAI=2) then mh.TONGTIENAVAT else tt.CONLAI end as sotienavat,0 as sotientt, '' as POID  " +
					" ,0 as dathanhtoan, " +
					" case when (tt.SOTIENAVAT is null OR t.TRANGTHAI=2) then mh.TIENTE_FK ELSE t.TIENTE_FK END AS TTID " +
					" from ERP_MUAHANG mh  " +
					" left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ  " +
					" left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk "+
					" left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
					" where ncc.NOIBO = 1 and mh.NHACUNGCAP_FK = '" + this.nccId + "' and ( tt.CONLAI is null or (tt.CONLAI > 0 "+
					" and tt.HOADON_FK not in "+
					"	(select distinct tt.HOADON_FK "+
					"	from ERP_MUAHANG mh "+
					"	left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ  "+
					"	left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
					"	where mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.CONLAI = 0)" +
					"  and tt.THANHTOANHD_FK in "+
					"	(select MAX(tt.THANHTOANHD_FK) "+
					"	from Erp_MuaHang mh  "+
					"	left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ "+
					" left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk "+
					"	left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
					"	 where ncc.NOIBO = 1 and mh.TRANGTHAI =1    and  mh.NHACUNGCAP_FK =  '" + this.nccId + "' and t.TRANGTHAI<>2	"+
					"	 group by tt.HOADON_FK ) ) )" +
					" and mh.TRANGTHAI = 2 " +
					" and (tt.THANHTOANHD_FK is null or ( tt.THANHTOANHD_FK > 0 and ( t.TRANGTHAI=1))) " ;
					
					
				}else {
										
					query = " select  mh.PK_SEQ, mh.PK_SEQ as sohoadon,  N'Chi phí n?i b?'  AS KYHIEU , mh.NGAYMUA as ngayhoadon "+
					" ,case when (tt.SOTIENAVAT is null OR t.TRANGTHAI=2) then mh.TONGTIENAVAT else tt.CONLAI end as sotienavat,0 as sotientt, '' as POID  " +
					" ,0 as dathanhtoan, "+
					" case when (tt.SOTIENAVAT is null OR t.TRANGTHAI=2) then mh.TIENTE_FK ELSE t.TIENTE_FK END AS TTID " +
					" from ERP_MUAHANG mh  "+
					" left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ  " +
					" left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk "+
					" left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
					" where ncc.NOIBO = 1 and mh.NHACUNGCAP_FK = '" + this.nccId + "' and ( tt.CONLAI is null or (tt.CONLAI > 0 "+
					" and tt.HOADON_FK not in "+
					"	(select distinct tt.HOADON_FK "+
					"	from ERP_MUAHANG mh "+
					"	left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ  "+
					"	left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
					"	where mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.CONLAI = 0)" +
					"  and tt.THANHTOANHD_FK in "+
					"	(select MAX(tt.THANHTOANHD_FK) "+
					"	from Erp_MuaHang mh  "+
					"	left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ "+
					" left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk "+
					" left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK "+
					" where ncc.NOIBO = 1 and mh.TRANGTHAI =1    and  mh.NHACUNGCAP_FK =  '" + this.nccId + "' and t.TRANGTHAI<>2	"+
					" group by tt.HOADON_FK ) ) )" +
					" and mh.TRANGTHAI = 2 " +
					" and (tt.THANHTOANHD_FK is null or ( tt.THANHTOANHD_FK > 0 and ( t.TRANGTHAI=1))) " ;
					
				}
					
			}
			System.out.println("Query khoi tao hoa don: " + query);
			
			ResultSet rsHoadon = db.get(query);
			List<IHoadon> hdList = new ArrayList<IHoadon>();
			 
				try 
				{
					IHoadon hd = null;
					while(rsHoadon.next())
					{
						String id = rsHoadon.getString("pk_seq");
						String kyhieu = rsHoadon.getString("kyhieu");
						String sohoadon = rsHoadon.getString("sohoadon");
						String ngayhd = rsHoadon.getString("ngayhoadon");
						String sotiengoc = formatter.format(rsHoadon.getDouble("sotiengoc")* rsHoadon.getDouble("tigia"));
						String sotienno = formatter.format(rsHoadon.getDouble("sotienno")* rsHoadon.getDouble("tigia"));
						//System.out.println("Query sohoadon: " + sohoadon);
						String dathanhtoan = "0";
						if(this.id.length() > 0)
						{
							dathanhtoan = formatter.format(rsHoadon.getDouble("SOTIENTT"));
						}
						
						hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, sotiengoc, sotienno, dathanhtoan, "");
						hd.setSoPO(rsHoadon.getString("POID"));
						hd.setTienteId(rsHoadon.getString("TTID"));
						hd.setTigia(rsHoadon.getString("tigia"));
						hdList.add(hd);
					}
					rsHoadon.close();
				} 
				catch (Exception e) { 
					e.printStackTrace();
					System.out.println("Error Here : "+e.toString());
				}
			 
			this.hoadonList = hdList;
			System.out.println("So hoa don: " + this.hoadonList.size());
		}
	
	}
	
	public String getcongtyId()
	{
		return this.congtyId;
	}

	public void setcongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}
	
	public String getnppdangnhap()
	{
		return this.nppdangnhap;
	}

	public void setnppdangnhap(String nppdangnhap) 
	{
		this.nppdangnhap = nppdangnhap;
	}
	
	public String getisNPP() {
		return isNPP;
	}

	public void setisNPP(String isNPP) {
		this.isNPP = isNPP;
	}
}
