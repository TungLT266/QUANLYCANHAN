package geso.traphaco.erp.beans.phuongphapthunghiem.imp;


import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.phuongphapthunghiem.IPhuongPhapThuNghiem;
import geso.traphaco.erp.beans.phuongphapthunghiemthamso.IPhuongPhapThuNghiemTieuDeMau;
import geso.traphaco.erp.beans.phuongphapthunghiemthamso.PhuongPhapThuNghiemThamSo;
import geso.traphaco.erp.beans.phuongphapthunghiemthamso.imp.IPhuongPhapThuNghiemThamSo;
import geso.traphaco.erp.beans.phuongphapthunghiemthamso.imp.PhuongPhapThuNghiemTieuDeMau;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhuongPhapThuNghiem implements IPhuongPhapThuNghiem {
	private String PK_SEQ;
	private String MaPPTN;
	private String TenPPTN;
	private String DienGiai;
	private String MaLoaiTieuChi;
	private String MaYeuCauKyThuat;
	private String UserName;
	private String UserId;
	private String msg;
	private String TrangThai;
	private String SoLuongTs;
	private String SoLuongMau;
	private ResultSet RsLoaiTieuChi;
	private ResultSet RsYeuCauKyThuat;
	private ResultSet RsDonViDoLuong;
	private String masanpham;
	private ResultSet RsSanPham;
	private List<IPhuongPhapThuNghiemThamSo> listThamSo;
	private List<IPhuongPhapThuNghiemTieuDeMau> listTieuDeMau;
	dbutils db;
	
	public PhuongPhapThuNghiem(){
		this.db=new dbutils();
		this.PK_SEQ="";
		this.MaPPTN="";
		this.TenPPTN="";
		this.DienGiai="";
		this.MaLoaiTieuChi="";
		this.masanpham="";
		this.MaYeuCauKyThuat="";
		this.UserName="";
		this.UserId="";
		this.msg="";
		this.TrangThai="1";
		this.SoLuongMau="";
		this.SoLuongTs="";
		this.listThamSo=new ArrayList<IPhuongPhapThuNghiemThamSo>();
		this.listTieuDeMau=new ArrayList<IPhuongPhapThuNghiemTieuDeMau>();
	}
	
	
	public List<IPhuongPhapThuNghiemTieuDeMau> getListTieuDeMau() {
		return listTieuDeMau;
	}

	public void setListTieuDeMau(List<IPhuongPhapThuNghiemTieuDeMau> listTieuDeMau) {
		this.listTieuDeMau = listTieuDeMau;
	}


	public ResultSet getRsSanPham() {
		return RsSanPham;
	}

	public void setRsSanPham(ResultSet rsSanPham) {
		RsSanPham = rsSanPham;
	}

	public String getMasanpham() {
		return masanpham;
	}

	public void setMasanpham(String masanpham) {
		this.masanpham = masanpham;
	}

	public String getPK_SEQ() {
		return PK_SEQ;
	}

	public void setPK_SEQ(String pK_SEQ) {
		PK_SEQ = pK_SEQ;
	}

	public String getMaPPTN() {
		return MaPPTN;
	}

	public void setMaPPTN(String maPPTN) {
		MaPPTN = maPPTN;
	}

	public String getTenPPTN() {
		return TenPPTN;
	}

	public void setTenPPTN(String tenPPTN) {
		TenPPTN = tenPPTN;
	}

	public String getDienGiai() {
		return DienGiai;
	}

	public void setDienGiai(String dienGiai) {
		DienGiai = dienGiai;
	}

	public String getMaLoaiTieuChi() {
		return MaLoaiTieuChi;
	}

	public void setMaLoaiTieuChi(String maLoaiTieuChi) {
		MaLoaiTieuChi = maLoaiTieuChi;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTrangThai() {
		return TrangThai;
	}

	public void setTrangThai(String trangThai) {
		TrangThai = trangThai;
	}

	public ResultSet getRsLoaiTieuChi() {
		return RsLoaiTieuChi;
	}

	public void setRsLoaiTieuChi(ResultSet rsLoaiTieuChi) {
		RsLoaiTieuChi = rsLoaiTieuChi;
	}
	
	public ResultSet getRsYeuCauKyThuat() {
		return RsYeuCauKyThuat;
	}

	public void setRsYeuCauKyThuat(ResultSet rsYeuCauKyThuat) {
		RsYeuCauKyThuat = rsYeuCauKyThuat;
	}
	
	public String getMaYeuCauKyThuat() {
		return MaYeuCauKyThuat;
	}

	public void setMaYeuCauKyThuat(String maYeuCauKyThuat) {
		MaYeuCauKyThuat = maYeuCauKyThuat;
	}

	public List<IPhuongPhapThuNghiemThamSo> getListThamSo() {
		return listThamSo;
	}

	public void setListThamSo(List<IPhuongPhapThuNghiemThamSo> listThamSo) {
		this.listThamSo = listThamSo;
	}
	
	public ResultSet getRsDonViDoLuong() {
		String queryDVDL="select Pk_SEQ, DONVI,DIENGIAI from DONVIDOLUONG WHERE TRANGTHAI=1";
		return this.db.getScrol(queryDVDL);
	}

	public void setRsDonViDoLuong(ResultSet rsDonViDoLuong) {
		RsDonViDoLuong = rsDonViDoLuong;
	}

	public String getSoLuongTs() {
		return SoLuongTs;
	}

	public void setSoLuongTs(String soLuongTs) {
		SoLuongTs = soLuongTs;
	}

	public String getSoLuongMau() {
		return SoLuongMau;
	}

	public void setSoLuongMau(String soLuongMau) {
		SoLuongMau = soLuongMau;
	}

	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	public void creates(){
		String querysp="select pk_seq,ma,ten from erp_sanpham where TRANGTHAI=1";
		this.RsSanPham=this.db.get(querysp);
		String query="select PK_SEQ,TEN,MA from ERP_TIEUCHIKIEMNGHIEM where trangthai=1";
		this.RsLoaiTieuChi=this.db.get(query);
		String queryYCKT="select Pk_SEQ, TEN,MA from ERP_YEUCAUKYTHUAT WHERE TRANGTHAI=1";
		this.RsYeuCauKyThuat=this.db.get(queryYCKT);
		String queryDVDL="select Pk_SEQ, DONVI,DIENGIAI from DONVIDOLUONG WHERE TRANGTHAI=1";
		this.RsDonViDoLuong=this.db.get(queryDVDL);
	}
	public void init(){
		String query="select SANPHAM_FK, MA,TEN,DIENGIAI,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,TRANGTHAI,LOAITIEUCHI,YEUCAUKYTHUAT,SOLUONGMAU from ERP_PHUONGPHAPTHUNGHIEM where pk_seq='"+this.PK_SEQ+"'";
		ResultSet rsPPTN=this.db.get(query);
		if(rsPPTN!=null){
			try {
				while (rsPPTN.next()) {
					this.MaPPTN=rsPPTN.getString("MA");
					this.MaYeuCauKyThuat=rsPPTN.getString("YEUCAUKYTHUAT");
					this.MaLoaiTieuChi=rsPPTN.getString("LOAITIEUCHI");
					this.SoLuongMau=rsPPTN.getString("SOLUONGMAU");
					this.TenPPTN=rsPPTN.getString("TEN");
					this.DienGiai=rsPPTN.getString("DIENGIAI");
					this.TrangThai=rsPPTN.getString("TRANGTHAI");
					this.masanpham= rsPPTN.getString("SanPham_fk");
				}
				rsPPTN.close();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.listThamSo=new ArrayList<IPhuongPhapThuNghiemThamSo>();
			
			String queryTS="select isnull(a.kyhieuthamso,'') as kyhieuthamso, a.PHUONGPHAPTHUNGHIEM_FK,a.DVDL_FK,a.LOAITICK, \n"
					+ " a.POPUPTHAMSO,a.THAMSOGROUP, isnull(a.congthuc, '') as congthuc, isnull(a.min,'') as min, "
					+ "isnull(a.max,'') as max , isnull(a.avg,'') as avg, isnull(a.sum,'') as sum from ERP_PHUONGPHAPTHUNGHIEM_THAMSO a "
					+ "\n where a.PHUONGPHAPTHUNGHIEM_FK='"+this.PK_SEQ+"' order by stt";

			rsPPTN=this.db.get(queryTS);
				if(rsPPTN!=null){
					try {
						while (rsPPTN.next()) {
							IPhuongPhapThuNghiemThamSo ts=new PhuongPhapThuNghiemThamSo();
							ts.setMaPPTN(rsPPTN.getString("PHUONGPHAPTHUNGHIEM_FK"));
							ts.setMaDVDL(rsPPTN.getString("DVDL_FK"));
							ts.setPopupThamSo(rsPPTN.getString("POPUPTHAMSO"));
							ts.setKyhieuThamso(rsPPTN.getString("kyhieuthamso"));
							ts.setThamSoPopup(rsPPTN.getString("THAMSOGROUP"));
							ts.setLoaiTick(rsPPTN.getString("LOAITICK"));
							ts.setCongthuc(rsPPTN.getString("congthuc"));
							ts.setMin(rsPPTN.getString("min"));
							ts.setMax(rsPPTN.getString("max"));
							ts.setAvg(rsPPTN.getString("avg"));
							ts.setSum(rsPPTN.getString("sum"));
							this.listThamSo.add(ts);
						}
						rsPPTN.close();
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
				}
				this.listTieuDeMau=new ArrayList<IPhuongPhapThuNghiemTieuDeMau>();
				
				String queryTDM="select  phuongphapthunghiem_fk, stt, isnull(tieudemau,'') as tieudemau from ERP_PHUONGPHAPTHUNGHIEM_TIEUDEMAU "
						+ "\n where PHUONGPHAPTHUNGHIEM_FK='"+this.PK_SEQ+"' order by stt";

				rsPPTN=this.db.get(queryTDM);
					if(rsPPTN!=null){
						try {
							while (rsPPTN.next()) {
								IPhuongPhapThuNghiemTieuDeMau tdm=new PhuongPhapThuNghiemTieuDeMau();
								tdm.setStt(rsPPTN.getString("stt"));
								tdm.setTieudemau(rsPPTN.getString("tieudemau"));
								tdm.setMaPPTN(rsPPTN.getString("phuongphapthunghiem_fk"));
								this.listTieuDeMau.add(tdm);
							}
							rsPPTN.close();
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						
					}
		
		}
	}
	public void DBClose(){
		try{
			if(this.RsSanPham!=null) this.RsSanPham.close();
			if(this.RsLoaiTieuChi != null) this.RsLoaiTieuChi.close();
			if(this.RsYeuCauKyThuat != null) this.RsYeuCauKyThuat.close();
			if(this.RsDonViDoLuong != null) this.RsDonViDoLuong.close();
			if(this.db!=null)this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}
	public boolean save(){
		
		if(this.masanpham.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập mã sản phẩm";
			return false;
		}
		if(this.MaPPTN.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập mã phương pháp thử nghiệm";
			return false;
		}
		if(this.MaLoaiTieuChi.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn loại tiêu chí";
			return false;
		}
		if(this.MaYeuCauKyThuat.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn yêu cầu kĩ thuật";
			return false;
		}
		if(this.SoLuongMau.trim().length() <= 0)
		{
			this.msg = "Số lượng mẫu phải lớn hơn 0";
			return false;
		}
		if(this.SoLuongTs.trim().length() <= 0)
		{
			this.msg = "Số lượng tham số nhập liệu phải lớn hơn 0";
			return false;
		}
		if(this.getListThamSo().size() <= 0)
		{
			this.msg = "Số lượng tham số nhập liệu phải lớn hơn 0";
			return false;
		}
		if(this.getListTieuDeMau().size() <= 0)
		{
			this.msg = "Số lượng mẫu phải lớn hơn 0";
			return false;
		}
		if(this.SoLuongMau.trim().length() <= 0)
		{
			this.msg = "Số lượng mẫu phải lớn hơn 0";
			return false;
		}
		try {
			String idpptn;
			db.getConnection().setAutoCommit(false);
			String query="insert into ERP_PHUONGPHAPTHUNGHIEM(MA,TEN,DIENGIAI,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,TRANGTHAI,LOAITIEUCHI,YEUCAUKYTHUAT,SOLUONGMAU, sanpham_fk) VALUES('"
					+this.getMaPPTN()+"',N'"+this.getTenPPTN()+"',N'"+this.getDienGiai()+"','"+this.getDateTime()+"','"+this.getDateTime()+"','"
					+this.getUserId()+"','"+this.getUserId()+"','"+this.getTrangThai()+"','"+this.getMaLoaiTieuChi()+"','"+this.getMaYeuCauKyThuat()+"','"+this.getSoLuongMau()+ "', '" + this.getMasanpham() +"')";
			if (!db.update(query)) {
				this.msg="Không thực hiện được câu lệnh: "+query;
				db.getConnection().rollback();
				return false;
			}
			String pptnid="";
			query="select SCOPE_IDENTITY() as pptnid";
			ResultSet rsPPTN=db.get(query);
			if (rsPPTN.next()) {
				pptnid=rsPPTN.getString("pptnid");
			}
			rsPPTN.close();
			for (int j = 0; j < listThamSo.size(); j++) {
				IPhuongPhapThuNghiemThamSo ts=listThamSo.get(j);
				if(ts.getPopupThamSo().trim().length() <= 0)
				{
					this.msg = "Vui lòng nhập tham số popup";
					db.getConnection().rollback();
					return false;
				}
				if(ts.getMaDVDL()!=""){
					query="insert into ERP_PHUONGPHAPTHUNGHIEM_THAMSO (PHUONGPHAPTHUNGHIEM_FK, congthuc,min,max,avg,sum, stt, kyhieuthamso,POPUPTHAMSO,DVDL_FK,LOAITICK,THAMSOGROUP) " +
							" values('"+pptnid + "', '"+ ts.getCongthuc() + "', '"+ ts.getMin()+ "', '"+ ts.getMax()+ "', '"+ ts.getAvg()+ "', '"+ ts.getSum()+  "'," + (j+1) + ",N'" + ts.getKyhieuThamso()+ "',N'" +ts.getPopupThamSo()+"','"+ts.getMaDVDL()+"','"+ts.getLoaiTick()+"','"+ts.getThamSoPopup()+"')";
				}
				else{
					query="insert into ERP_PHUONGPHAPTHUNGHIEM_THAMSO (PHUONGPHAPTHUNGHIEM_FK, congthuc,min,max,avg,sum, stt,kyhieuthamso,POPUPTHAMSO,LOAITICK,THAMSOGROUP) " +
							" values('"+pptnid+"', '"+ ts.getCongthuc() + "', '"+ ts.getMin()+ "', '"+ ts.getMax()+ "', '"+ ts.getAvg()+ "', '"+ ts.getSum()+  "', " +(j+1)+",N'"+ ts.getKyhieuThamso()+ "',N'" +ts.getPopupThamSo()+"','"+ts.getLoaiTick()+"','"+ts.getThamSoPopup()+"')";
				}
				
				if (!db.update(query)) {
					db.getConnection().rollback();
					this.msg="Không thể cập nhật dòng lệnh:"+query;
					return false;
				}
			}
			for (int j = 0; j < listTieuDeMau.size(); j++) {
				
				IPhuongPhapThuNghiemTieuDeMau tdm=listTieuDeMau.get(j);
				
				if(tdm.getTieudemau().trim().length() <= 0 ){
					this.msg="Vui lòng nhập tiêu đề mẫu!";
					db.getConnection().rollback();
					return false;
				}
				
					query="insert into ERP_PHUONGPHAPTHUNGHIEM_TIEUDEMAU (PHUONGPHAPTHUNGHIEM_FK, stt, tieudemau) " +
							" values('"+pptnid+"', '"+ (j+1) + "', N'"+ tdm.getTieudemau()+ "')";
				
				if (!db.update(query)) {
					db.getConnection().rollback();
					this.msg="Không thể cập nhật dòng lệnh:"+query;
					return false;
				}
			}
			
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
		} catch (Exception e) {
			db.update("rollback");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean update(){
		
		System.out.println("masanpham "+masanpham);
		if(this.masanpham.trim().length() == 0)
		{
			this.msg = "Vui lòng nhập mã sản phẩm";
			return false;
		}
		if(this.MaPPTN.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập mã phương pháp thử nghiệm";
			return false;
		}
		if(this.MaLoaiTieuChi.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn loại tiêu chí";
			return false;
		}
		if(this.MaYeuCauKyThuat.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn yêu cầu kĩ thuật";
			return false;
		}
		if(this.SoLuongMau.trim().length() <= 0)
		{
			this.msg = "Số lượng mẫu phải lớn hơn 0";
			return false;
		}
		if(this.listThamSo.size() <= 0)
		{
			this.msg = "Số lượng tham số nhập liệu phải lớn hơn 0";
			return false;
		}
		if(this.SoLuongMau.trim().length() <= 0)
		{
			this.msg = "Số lượng mẫu phải lớn hơn 0";
			return false;
		}
		try {
			db.getConnection().setAutoCommit(false);
			String query="update ERP_PHUONGPHAPTHUNGHIEM set sanpham_fk= '"+ this.getMasanpham() +"', NGUOISUA='"+this.getUserId()+"',NGAYSUA='"+this.getDateTime()+"',MA='"+this.getMaPPTN()+"',"
					+ "\n TEN=N'"+this.getTenPPTN()+"',DIENGIAI=N'"+this.getDienGiai()+"',SOLUONGMAU='"+this.getSoLuongMau()+"',LOAITIEUCHI='"+this.getMaLoaiTieuChi()+"',"
					+ "\n YEUCAUKYTHUAT='"+this.getMaYeuCauKyThuat()+"', TRANGTHAI='"+this.getTrangThai()+"' where PK_SEQ='"+this.getPK_SEQ()+"'";
			if (!db.update(query)) {
				this.msg="Không thực hiện được câu lệnh: "+query;
				db.getConnection().rollback();
				return false;
			}
			String queryDel="delete from ERP_PHUONGPHAPTHUNGHIEM_THAMSO where PHUONGPHAPTHUNGHIEM_FK='"+this.PK_SEQ+"'";
			this.db.update(queryDel);
			for (int j = 0; j < listThamSo.size(); j++) {
				IPhuongPhapThuNghiemThamSo ts=listThamSo.get(j);
				if(ts.getPopupThamSo().trim().length() <= 0)
				{
					this.msg = "Vui lòng nhập tham số popup";
					db.getConnection().rollback();
					return false;
				}
				if(ts.getMaDVDL()!=""){
					query="insert into ERP_PHUONGPHAPTHUNGHIEM_THAMSO (PHUONGPHAPTHUNGHIEM_FK,congthuc,min,max,avg,sum, stt, kyhieuthamso,POPUPTHAMSO,DVDL_FK,LOAITICK,THAMSOGROUP) " +
							" values('"+this.getPK_SEQ()+ "', '"+ ts.getCongthuc() + "', '"+ ts.getMin()+ "', '"+ ts.getMax()+ "', '"+ ts.getAvg()+ "', '"+ ts.getSum()+  "'," + (j+1) +",N'"+ ts.getKyhieuThamso()+ "',N'" +ts.getPopupThamSo()+"','"+ts.getMaDVDL()+"','"+ts.getLoaiTick()+"','"+ts.getThamSoPopup()+"')";
				}
				else{
					query="insert into ERP_PHUONGPHAPTHUNGHIEM_THAMSO (PHUONGPHAPTHUNGHIEM_FK, congthuc,min,max,avg,sum, stt, kyhieuthamso,POPUPTHAMSO,LOAITICK,THAMSOGROUP) " +
							" values('"+this.getPK_SEQ()+ "', '"+ ts.getCongthuc() + "', '"+ ts.getMin()+ "', '"+ ts.getMax()+ "', '"+ ts.getAvg()+ "', '"+ ts.getSum()+  "'," + (j+1) + ",N'"+ ts.getKyhieuThamso()+ "',N'" +ts.getPopupThamSo()+"','"+ts.getLoaiTick()+"','"+ts.getThamSoPopup()+"')";
				}
				
				if (!db.update(query)) {
					db.getConnection().rollback();
					this.msg="Không thể cập nhật dòng lệnh:"+query;
					return false;
				}
			}
			queryDel="delete from ERP_PHUONGPHAPTHUNGHIEM_TIEUDEMAU where PHUONGPHAPTHUNGHIEM_FK='"+this.PK_SEQ+"'";
			this.db.update(queryDel);
			for (int j = 0; j < listTieuDeMau.size(); j++) {
				
				IPhuongPhapThuNghiemTieuDeMau tdm=listTieuDeMau.get(j);
				if(tdm.getTieudemau().trim().length() <= 0 ){
					db.getConnection().rollback();
					this.msg="Vui lòng nhập tiêu đề mẫu!";
					return false;
				}
					query="insert into ERP_PHUONGPHAPTHUNGHIEM_TIEUDEMAU (PHUONGPHAPTHUNGHIEM_FK, stt, tieudemau) " +
							" values('"+this.getPK_SEQ()+"', '"+ (j+1) + "', N'"+ tdm.getTieudemau()+ "')";
				
				if (!db.update(query)) {
					db.getConnection().rollback();
					this.msg="Không thể cập nhật dòng lệnh:"+query;
					return false;
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
		} catch (Exception e) {
			db.update("rollback");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean delete(){
		try {
			db.getConnection().setAutoCommit(false);
			
			String query="update ERP_PHUONGPHAPTHUNGHIEM set TRANGTHAI=2 where PK_SEQ='"+this.PK_SEQ+"'";
			if (!db.update(query)) {
				db.getConnection().rollback();
				this.msg="Không thể cập nhật dòng lệnh:"+query;
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg="Lỗi: "+e.getMessage();
			db.update("rollback");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
