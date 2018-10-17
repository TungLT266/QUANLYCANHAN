package geso.traphaco.erp.beans.tieuchuankiemnghiem.imp;

import geso.traphaco.erp.beans.tieuchuankiemnghiem.IErpTieuChuanKiemNghiem;
import geso.traphaco.erp.beans.tieuchuankiemnghiem.IItemLoader;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpTieuChuanKiemNghiem implements IErpTieuChuanKiemNghiem {
	String congtyId;
	String userId;
	String userTen;
	String id;
	String ma;
	String ten;
	String ngaycap;
	String hosomau;
	String hosokiemnghiem;
	String phieukiemnghiem;
	String ishoatdong;
	String trangthai;
	String msg;
	dbutils db;
	String nppId;
	String item;
	
	String dienGiai;
	String loaimauknId;
	ResultSet loaimauknRs;
	String bieumauhsId;
	ResultSet bieumauhsRs;
	String sanphamId;
	ResultSet sanphamRs;
	List<IItemLoader> yeuCauKNList;
	List<IItemLoader> thietbiList;
	String PPID;
	
	String yeucauIDSS;
	String thietbiIDSS;
	
	public ErpTieuChuanKiemNghiem (){
		 this.id="";
		 this.nppId="";
		 this.congtyId ="";
		 this.userId="";
		 this.userTen="";
		 this. ma="";
		 this.ten="";
		 this.ngaycap= getDateTime();
		 this.sanphamId="";
		 this.hosomau="";
		 this.hosokiemnghiem="";
		 this.phieukiemnghiem="";
		 this.ishoatdong="0";
		 this.msg="";
		 this.loaimauknId="";
		 this.bieumauhsId="";
		 this.dienGiai = "";
		 this.trangthai = "";
		 this.PPID = "";
		 this.item = "";
		 this.yeucauIDSS = "0";
		 this.thietbiIDSS = "0";
		 this.yeuCauKNList = new ArrayList<IItemLoader>();
		 this.thietbiList = new ArrayList<IItemLoader>();
		 this.db = new dbutils();
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ResultSet getSanphamRs() {
		return sanphamRs;
	}
	public void setSanphamRs(ResultSet sanphamRs) {
		this.sanphamRs = sanphamRs;
	}
	public String getBieumauhsId() {
		return bieumauhsId;
	}
	public void setBieumauhsId(String bieumauhsId) {
		this.bieumauhsId = bieumauhsId;
	}
	public ResultSet getBieumauhsRs() {
		return bieumauhsRs;
	}
	public void setBieumauhsRs(ResultSet bieumauhsRs) {
		this.bieumauhsRs = bieumauhsRs;
	}
	public String getLoaimauknId() {
		return loaimauknId;
	}
	public void setLoaimauknId(String loaimauknId) {
		this.loaimauknId = loaimauknId;
	}
	public ResultSet getLoaimauknRs() {
		return loaimauknRs;
	}
	public void setLoaimauknRs(ResultSet loaimauknRs) {
		this.loaimauknRs = loaimauknRs;
	}
	public String getNppId() {
		return nppId;
	}
	public void setNppId(String nppId) {
		this.nppId = nppId;
	}
	public String getCongtyId() {
		return congtyId;
	}
	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getNgaycap() {
		return ngaycap;
	}
	public void setNgaycap(String ngaycap) {
		this.ngaycap = ngaycap;
	}
	
	public String getSanphamId() {
		return sanphamId;
	}
	public void setSanphamId(String sanphamId) {
		this.sanphamId = sanphamId;
	}
	public String getHosomau() {
		return hosomau;
	}
	public void setHosomau(String hosomau) {
		this.hosomau = hosomau;
	}
	public String getHosokiemnghiem() {
		return hosokiemnghiem;
	}
	public void setHosokiemnghiem(String hosokiemnghiem) {
		this.hosokiemnghiem = hosokiemnghiem;
	}
	public String getPhieukiemnghiem() {
		return phieukiemnghiem;
	}
	public void setPhieukiemnghiem(String phieukiemnghiem) {
		this.phieukiemnghiem = phieukiemnghiem;
	}
	public String getIshoatdong() {
		return ishoatdong;
	}
	public void setIshoatdong(String ishoatdong) {
		this.ishoatdong = ishoatdong;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getUserTen() {
		return userTen;
	}
	public void setUserTen(String userTen) {
		this.userTen = userTen;
	}
	@Override
	public void init() {
		
		String query = " select pk_seq TCID, ma as TCMA, trangthai, ngaycap, loaimauKN_fk LOAIMAUKNID, "
				+ " sanpham_fk SAMPHAMID, ishoatdong, congty_fk, diengiai, isnull(bieumau_fk,'') bieumau_fk from ERP_TIEUCHUANKIEMGNGHIEM "
				+ " where pk_seq = "+this.id+" ";
		System.out.println("GET TC: " + query);
		ResultSet rsTemp = this.db.get(query);
		if(rsTemp != null) {
			try {
				while(rsTemp.next()) {
					this.id = rsTemp.getString("TCID");
					this.ma = rsTemp.getString("TCMA");
					this.trangthai = rsTemp.getString("trangthai");
					this.ngaycap = rsTemp.getString("ngaycap");
					this.loaimauknId = rsTemp.getString("LOAIMAUKNID");
					this.sanphamId = rsTemp.getString("SAMPHAMID");
					this.ishoatdong = rsTemp.getString("ishoatdong");
					this.congtyId = rsTemp.getString("congty_fk");
					this.dienGiai = rsTemp.getString("diengiai");
					this.bieumauhsId = rsTemp.getString("bieumau_fk");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		String TCYC_FK = "";
		query = " select YC.pk_seq as YCID, YC.ma as YCMA, YC.ma + ' <> ' + YC.ten as YCTEN, TCYC.pk_seq as TCYC_FK "
				+ " from ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT TCYC inner join ERP_yeucaukythuat yc on TCYC.YEUCAUKT_FK=YC.PK_SEQ "
				+ " where TIEUCHUANKN_FK = "+this.id+" ";
		System.out.println("GET TCYC: " + query);
		ResultSet rsTempYC = this.db.get(query);
		if(rsTempYC != null) {
			try {
				while(rsTempYC.next()) {
					IItemLoader item = new ItemLoader();
					item.setPk_seq(rsTempYC.getString("YCID"));
					item.setMa(rsTempYC.getString("YCMA"));
					item.setTen(rsTempYC.getString("YCTEN"));
					TCYC_FK = rsTempYC.getString("TCYC_FK");
					
					query = " select PP.pk_seq PPID, PP.ma PPMA, PP.ten PPTEN, isnull(YCPP.TIEUCHUAN_YEUCAU_FK, '0') TIEUCHUAN_YEUCAU_FK "
							+ " from ERP_PHUONGPHAPTHUNGHIEM PP left join ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT_PHUONGPHAP YCPP "
							+ " on YCPP.phuongphap_fk=pp.pk_seq "
							+ " where PP.yeucaukythuat = "+item.getPk_seq()+" ";
					System.out.println("GET YCPP: " + query);
					ResultSet rsTempPP = this.db.get(query);
					if(rsTempPP!=null) {
						while(rsTempPP.next()) {
							IItemLoader itemPP = new ItemLoader();
							itemPP.setPk_seq(rsTempPP.getString("PPID"));
							itemPP.setMa(rsTempPP.getString("PPMA"));
							itemPP.setTen(rsTempPP.getString("PPTEN"));
							itemPP.setChon(rsTempPP.getString("TIEUCHUAN_YEUCAU_FK").equals("0")?"0":"1");
							item.getPpThuNghiemList().add(itemPP);
						}
						rsTempPP.close();
					}
					this.yeuCauKNList.add(item);
				}
				rsTemp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		query = " select TB.PK_seq TBID, TB.ma TBMA, TB.ten TBTEN, TB.ghichu "
				+ " from ERP_TIEUCHUANKIEMGNGHIEM_THIETBI TCPP inner join ERP_THIETBIKIEMNGHIEM TB on TCPP.THIETBI_FK=TB.pk_seq where TCPP.TIEUCHUAN_FK = "+this.id+" ";
		System.out.println("GET THIETBI: " + query);
		ResultSet rstempTB = this.db.get(query);
		if(rstempTB != null) {
			try {
				while(rstempTB.next()) {
					IItemLoader item = new ItemLoader();
					item.setPk_seq(rstempTB.getString("TBID"));
					item.setMa(rstempTB.getString("TBMA"));
					item.setTen(rstempTB.getString("TBTEN"));
					item.setGhiChu(rstempTB.getString("ghichu"));
					this.thietbiList.add(item);
				}
				rstempTB.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createRs() {
		//lay loai mau kn
		String query = "select PK_SEQ, MA + ' - ' + TEN as TEN from ERP_LOAIMAUKIEMNGHIEM where TrangThai = '1'";
		query += " and congty_fk = '" + this.congtyId + "' ";
		query += " order by TEN asc ";
		System.out.println("::: LAY LOAI MAU KN: " + query);
		this.loaimauknRs = db.get(query);
		
		//lay bieu mau ho so
		query = "select PK_SEQ, MA + ' - ' + TEN as TEN from ERP_LOAIHOSO where TrangThai = '1'";
		query += " and congty_fk = '" + this.congtyId + "' ";
		query += " order by TEN asc ";
		System.out.println("::: LAY BIEU MAU HS: " + query);
		this.bieumauhsRs = db.get(query);
		
		//lay san pham
		query = "select PK_SEQ, timkiem as TEN from ERP_SANPHAM where TrangThai = '1'";
		query += " and congty_fk = '" + this.congtyId + "' ";
		query += " order by TEN asc ";
		System.out.println("::: LAY SAN PHAM: " + query);
		this.sanphamRs = db.get(query);	
	}
	
	public void DBclose() 
	{
		try
		{
			if(this.bieumauhsRs !=null) this.bieumauhsRs.close();
			if(this.loaimauknRs !=null) this.loaimauknRs.close();
			if(this.sanphamRs !=null) this.sanphamRs.close();
		}
		catch(Exception er){
			er.printStackTrace();
		}
		finally
		{
			if(this.db!=null)
				this.db.shutDown();
		}
	}
	
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	@Override
	public boolean createTCKN() {

		try {
			
			if(this.ma.trim().length()==0) {
				this.msg = "Mã không được để trống!";
				return false;
			}
			
			if(this.sanphamId.trim().length()==0){
				this.msg = "Vui lòng chọn sản phẩm!";
				return false;
			}
			
			if(this.loaimauknId.trim().length()==0){
				this.msg = "Vui lòng chọn loại mẫu KN!";
				return false;
			}
			
			if(this.bieumauhsId.trim().length()==0){
				this.msg = "Vui lòng chọn biểu mẫu KN!";
				return false;
			}
			
			if(this.yeuCauKNList == null || this.yeuCauKNList.size()==0) {
				this.msg = "Vui lòng chọn yêu cầu KN!";
				return false;
			}
			
			if(this.thietbiList == null || this.thietbiList.size()==0) {
				this.msg = "Vui lòng chọn thiết bị KN!";
				return false;
			}
			
			String query = " select count(*) dem from ERP_TIEUCHUANKIEMGNGHIEM where ma like '"+this.ma+"' ";
			System.out.println("Check MA: " + query);
			ResultSet rsCheck = this.db.get(query);
			if(rsCheck != null) {
				while(rsCheck.next()) {
					if(!rsCheck.getString("dem").equals("0")) {
						this.msg = "Mã này đã bị trùng!";
						return false;
					}
				}
				rsCheck.close();
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "insert into ERP_TIEUCHUANKIEMGNGHIEM(ma,ten,trangthai,nguoitao,ngaytao,nguoisua,ngaysua,"
					+ "congty_fk,ngaycap,loaimauKN_fk,sanpham_fk,ishoatdong,DIENGIAI,bieumau_fk)"
					+ " values('"+this.ma+"',N'"+this.ten+"','"+this.ishoatdong+"','"+this.userId+"','"+this.getDateTime()+"',"
					+ "'"+this.userId+"','"+this.getDateTime()+"','"+this.congtyId+"','"+this.ngaycap+"',"+this.loaimauknId+","
							+ ""+this.sanphamId+","+this.ishoatdong+",N'"+this.dienGiai+"','"+this.bieumauhsId+"')";
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_TIEUCHIKIEMNGHIEM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_TIEUCHUANKIEMGNGHIEM') as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			if(this.yeuCauKNList!=null) {
				for(int index = 0; index < this.yeuCauKNList.size(); index++) {
					IItemLoader yeucauItem = this.yeuCauKNList.get(index);
					
					query = " insert into ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT (TIEUCHUANKN_FK, YEUCAUKT_FK) "
							+ " values ("+this.id+", "+yeucauItem.getPk_seq()+") ";
					
					if(!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					if(yeucauItem.getPpThuNghiemList() != null) {
						for(int i = 0; i < yeucauItem.getPpThuNghiemList().size(); i++) {
							IItemLoader ppKN = yeucauItem.getPpThuNghiemList().get(i);
							
							if(ppKN != null && ppKN.getChon().equals("1")) {
								query = " insert into ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT_PHUONGPHAP (TIEUCHUAN_YEUCAU_FK, PHUONGPHAP_FK) "
										+ " values ("+yeucauItem.getPk_seq()+", "+ppKN.getPk_seq()+") ";
								
								if(!db.update(query)) {
									this.msg = "Không thể tạo mới ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT_PHUONGPHAP: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
				}
			}
			
			if(this.thietbiList != null) {
				for(int index = 0; index < this.thietbiList.size(); index++) {
					IItemLoader thietbiItem = this.thietbiList.get(index);
					
					query = " insert into ERP_TIEUCHUANKIEMGNGHIEM_THIETBI (TIEUCHUAN_FK, THIETBI_FK) "
							+ " values ("+this.id+", "+thietbiItem.getPk_seq()+") ";
					
					if(!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_TIEUCHUANKIEMGNGHIEM_THIETBI: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	

	@Override
	public boolean UpdateTCKN() {
		
		try {
			
			if(this.ma.trim().length()==0) {
				this.msg = "Mã không được để trống!";
				return false;
			}
			
			if(this.sanphamId.trim().length()==0){
				this.msg = "Vui lòng chọn sản phẩm!";
				return false;
			}
			
			if(this.loaimauknId.trim().length()==0){
				this.msg = "Vui lòng chọn loại mẫu KN!";
				return false;
			}
			
			if(this.bieumauhsId.trim().length()==0){
				this.msg = "Vui lòng chọn biểu mẫu KN!";
				return false;
			}
			
			if(this.yeuCauKNList == null || this.yeuCauKNList.size()==0) {
				this.msg = "Vui lòng chọn yêu cầu KN!";
				return false;
			}
			
			if(this.thietbiList == null || this.thietbiList.size()==0) {
				this.msg = "Vui lòng chọn thiết bị KN!";
				return false;
			}
			
			String query = " select count(*) dem from ERP_TIEUCHUANKIEMGNGHIEM where ma like '"+this.ma+"' and pk_seq != "+this.id+" ";
			System.out.println("Check MA: " + query);
			ResultSet rsCheck = this.db.get(query);
			if(rsCheck != null) {
				while(rsCheck.next()) {
					if(!rsCheck.getString("dem").equals("0")) {
						this.msg = "Mã sản phẩm này đã bị trùng!";
						return false;
					}
				}
				rsCheck.close();
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "update ERP_TIEUCHUANKIEMGNGHIEM set ma='"+this.ma+"',ten=N'"+this.ten+"',trangthai='"+this.ishoatdong+"',"
					+ "nguoisua='"+this.userId+"',ngaysua='"+this.getDateTime()+"', congty_fk = '"+this.congtyId+"',"
							+ " ngaycap = '"+this.ngaycap+"', loaimauKN_fk = "+this.loaimauknId+", sanpham_fk = "+this.sanphamId+","
									+ " ishoatdong = "+this.ishoatdong+", DIENGIAI = N'"+this.dienGiai+"', bieumau_fk = '"+this.bieumauhsId+"' "
							+ " where pk_seq = " + this.id;
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_TIEUCHIKIEMNGHIEM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " delete ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT where TIEUCHUANKN_FK = "+this.id+" ";
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_TIEUCHIKIEMNGHIEM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.yeuCauKNList!=null) {
				for(int index = 0; index < this.yeuCauKNList.size(); index++) {
					IItemLoader yeucauItem = this.yeuCauKNList.get(index);
					
					query = " insert into ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT (TIEUCHUANKN_FK, YEUCAUKT_FK) "
							+ " values ("+this.id+", "+yeucauItem.getPk_seq()+") ";
					
					if(!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					query = " delete ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT_PHUONGPHAP where TIEUCHUAN_YEUCAU_FK = "+yeucauItem.getPk_seq()+" ";
					
					if(!db.update(query)) {
						this.msg = "Không thể cập nhật ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					if(yeucauItem.getPpThuNghiemList() != null) {
						for(int i = 0; i < yeucauItem.getPpThuNghiemList().size(); i++) {
							IItemLoader ppKN = yeucauItem.getPpThuNghiemList().get(i);
							
							if(ppKN != null && ppKN.getChon().equals("1")) {
								query = " insert into ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT_PHUONGPHAP (TIEUCHUAN_YEUCAU_FK, PHUONGPHAP_FK) "
										+ " values ("+yeucauItem.getPk_seq()+", "+ppKN.getPk_seq()+") ";
								
								if(!db.update(query)) {
									this.msg = "Không thể tạo mới ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT_PHUONGPHAP: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
				}
			}
			
			query = " delete ERP_TIEUCHUANKIEMGNGHIEM_THIETBI where TIEUCHUAN_FK = "+this.id+" ";
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_TIEUCHUANKIEMGNGHIEM_THIETBI: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.thietbiList != null) {
				for(int index = 0; index < this.thietbiList.size(); index++) {
					IItemLoader thietbiItem = this.thietbiList.get(index);
					
					query = " insert into ERP_TIEUCHUANKIEMGNGHIEM_THIETBI (TIEUCHUAN_FK, THIETBI_FK) "
							+ " values ("+this.id+", "+thietbiItem.getPk_seq()+") ";
					
					if(!db.update(query)) {
						this.msg = "Không thể cập nhật ERP_TIEUCHUANKIEMGNGHIEM_THIETBI: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	public List<IItemLoader> getYeuCauKNList() {
		return yeuCauKNList;
	}


	public void setYeuCauKNList(List<IItemLoader> yeuCauKNList) {
		this.yeuCauKNList = yeuCauKNList;
	}


	public List<IItemLoader> getThietbiList() {
		return thietbiList;
	}


	public void setThietbiList(List<IItemLoader> thietbiList) {
		this.thietbiList = thietbiList;
	}


	public String getDienGiai() {
		return dienGiai;
	}


	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}


	public String getTrangthai() {
		return trangthai;
	}


	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}


	public String getPPID() {
		return PPID;
	}


	public void setPPID(String pPID) {
		PPID = pPID;
	}
	
	public void loadPP(String index) {
		
		String query = "";
		if(this.yeuCauKNList != null) {
			
				IItemLoader itemyc = this.yeuCauKNList.get(Integer.parseInt(index));
				if (itemyc.getPk_seq().trim().length()>0){
						
						itemyc.getPpThuNghiemList().clear();
					
						query = " select pk_seq, ma + ' - ' + ten as ten "
								+ " from ERP_PHUONGPHAPTHUNGHIEM "
								+ " where YEUCAUKYTHUAT = "+itemyc.getPk_seq()+" and trangthai = 1 ";
						System.out.println("GET YCPPRS: " + query);
						ResultSet rsTemp = this.db.get(query);
						if(rsTemp != null) {
							try {
								while (rsTemp.next()) {
									IItemLoader item = new ItemLoader();
									item.setPk_seq(rsTemp.getString("pk_seq"));
									item.setTen(rsTemp.getString("ten"));
									itemyc.getPpThuNghiemList().add(item);
									System.out.println("check: " + index);
								}
								rsTemp.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					
				}
			}
		
	}


	public String getItem() {
		return item;
	}


	public void setItem(String item) {
		this.item = item;
	}


	public String getYeucauIDSS() {
		return yeucauIDSS;
	}


	public void setYeucauIDSS(String yeucauIDSS) {
		this.yeucauIDSS = yeucauIDSS;
	}


	public String getThietbiIDSS() {
		return thietbiIDSS;
	}


	public void setThietbiIDSS(String thietbiIDSS) {
		this.thietbiIDSS = thietbiIDSS;
	}



	
}
