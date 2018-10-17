package geso.traphaco.erp.beans.uynhiemchi.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.util.*;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.erp.beans.uynhiemchi.IErpDenghithanhtoanNCC;
import geso.traphaco.erp.beans.uynhiemchi.IErpUynhiemchi;
import geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.Hoadon;

public class ErpDenghithanhtoanNCC implements IErpDenghithanhtoanNCC 
{
	String userId;
	String id;
	String ngayghinhan;
	String ctyId;
	String nccId;
	String diachi;
	ResultSet nccRs;
	String htttId;
	ResultSet htttRs;
	String nganhangId;
	ResultSet nganhangRs;
	String chinhanhId;
	ResultSet chinhanhRs;
	
	String nganhang_tpId;
	String chinhanh_tpId;
	String nppdangnhap;
	
	ResultSet sotkRs;	
	ResultSet sotkRs_tp;
	
	String sotaikhoan;
	String sotaikhoan_tp;
	String noidungtt;
	
	String tthdCurrent = "";
	String tigia;
	String dinhkhoanno = "";
	String dinhkhoannoId = "";
	String trichphi;
	
	String hoadonId;
	List<IHoadon> hoadonList;
	
	String tienteId;
	ResultSet tienteRs;
	
	String msg;
	dbutils db;
	
	String DoiTuongTamUng;
	String LoaiThanhToan;
	
	String TenHienThi="";
	
	String NhomNCCCN;
	
	String nhomNCCCNId;
	ResultSet nhomNCCRs;
	
	String DoiTuongChiPhiKhac;
	
	String prefix;
	
	String DoiTuongDinhKhoan = "";
	
	String MaTenDoiTuongDinhKhoan = "";
	
	String doituongdinhkhoanId;
	String sotientt;
	String sotienHD;
	String pnganhang;
	String thueVAT;
	String chenhlech;
	
	String sotienttNT;
	String sotienHDNT;
	String pnganhangNT;
	String thueVATNT;
	String chenhlechVND;
	
	String mahoadon;
	String mauhoadon;
	String kyhieu;
	String sohoadon;
	String ngayhoadon;
	String tenNCC;
	String mst;
	String tienhang;
	String thuesuat;
	String tienthue;
	String nhId_VAT;
	String cnId_VAT;
	String tenNCC_VAT;
	
	String checkThanhtoantuTV;
	
	String khachhangId ;
	ResultSet khachhangRs;
	
	String NhanvienId;
	ResultSet NhanvienRs;
	
	String bophanId ;
	String bophanTen;
	
	public ErpDenghithanhtoanNCC()
	{
		this.id = "";
		this.ctyId = "";
		this.ngayghinhan = this.getDateTime();
		this.nccId = "";
		this.diachi = "";
		this.htttId = "100001";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.sotaikhoan = "";
		this.nganhang_tpId = "";
		this.chinhanh_tpId = "";
		this.sotaikhoan_tp = "";
		this.noidungtt = "";
		this.hoadonId = "";
		this.NhanvienId="";
		this.TenHienThi="";
		this.msg = "";
		this.trichphi = "1";
		this.nppdangnhap = "";
		this.hoadonList = new ArrayList<IHoadon>();
		
		this.nhomNCCCNId = "";
		this.NhomNCCCN = "";
			DoiTuongTamUng="1";
			DoiTuongChiPhiKhac="1";
			LoaiThanhToan="1";
		this.db = new dbutils();
		
		this.dinhkhoanno = "";
		this.dinhkhoannoId = "";

		this.sotientt = "0";
		this.sotienHD = "0";
		this.thueVAT = "0";
		this.pnganhang = "0";

		this.sotienttNT = "0";
		this.sotienHDNT = "0";
		this.thueVATNT = "0";
		this.pnganhangNT = "0";
		
		this.tienteId = "100000"; 
		this.tigia = "1";
		this.chenhlechVND = "0";
		
		this.mahoadon = "";
		this.mauhoadon = "";
		this.kyhieu = "";
		this.sohoadon = "";
		this.ngayhoadon = "";
		this.tenNCC = "";
		this.mst = "";
		this.tienhang = "0";
		this.thuesuat = "0";
		this.tienthue = "0";
		this.nhId_VAT = "";
		this.cnId_VAT = "";
		this.tenNCC_VAT = "";
		this.checkThanhtoantuTV="0";
		this.khachhangId = "";
		
		this.bophanId = "";
		this.bophanTen = "";
		
	}
	
	public ErpDenghithanhtoanNCC(String id)
	{
		this.id = id;
		this.ctyId = "";
		this.ngayghinhan = this.getDateTime();
		this.nccId = "";
		this.diachi = "";
		this.htttId = "100001";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.sotaikhoan = "";
		this.sotaikhoan_tp = "";
		this.noidungtt = "";
		this.sotientt = "0";
		this.hoadonId = "";
		this.trichphi = "1";
		this.msg = "";
		DoiTuongTamUng="";
		DoiTuongChiPhiKhac="";
		LoaiThanhToan="";
		this.NhomNCCCN = "";
		this.nhomNCCCNId = "";
		
		this.hoadonList = new ArrayList<IHoadon>();
		
		this.db = new dbutils();
		
		this.dinhkhoanno = "";
		this.dinhkhoannoId = "";
		
		this.sotienHD = "0";
		this.thueVAT = "0";
		this.pnganhang = "0";

		this.sotienttNT = "0";
		this.sotienHDNT = "0";
		this.thueVATNT = "0";
		this.pnganhangNT = "0";

		this.tienteId = "100000";
		this.tigia = "1";
		this.chenhlechVND = "0";
		
		this.mahoadon = "";
		this.mauhoadon = "";
		this.kyhieu = "";
		this.sohoadon = "";
		this.ngayhoadon = "";
		this.tenNCC = "";
		this.mst = "";
		this.tienhang = "0";
		this.thuesuat = "0";
		this.tienthue = "0";
		this.nhId_VAT = "";
		this.cnId_VAT = "";
		this.tenNCC_VAT = "";
		this.checkThanhtoantuTV="";
		this.khachhangId = "";
		this.nppdangnhap="";
		
		this.bophanId = "";
		this.bophanTen = "";
		
	}
	
	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
	}

	public String getTienteId() {
		
		return this.tienteId;
	}

	
	public void setTienteId(String ttId) {
		
		this.tienteId = ttId;
	}

	public String getTrichphi() {
		
		return this.trichphi;
	}

	
	public void setTrichphi(String trichphi) {
		
		this.trichphi = trichphi;
	}
	
	public ResultSet getTienteRs() {
		
		return this.tienteRs;
	}

	
	public void setTienteRs(ResultSet tienteRs) {
		
		this.tienteRs = tienteRs;
	}

	public String getTigia() 
	{
		return this.tigia;
	}

	public void setTigia(String tigia)
	{
		if(tigia.length() == 0){
			String query = "SELECT TIGIAQUYDOI FROM ERP_TIGIA WHERE TUNGAY <= '" + this.ngayghinhan + "' AND DENNGAY >= '" + this.ngayghinhan + "' AND TIENTE_FK = " + this.tienteId + "";
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			try{
				if(rs != null){
					rs.next();
					this.tigia = rs.getString("TIGIAQUYDOI");
					rs.close();
				}
			}catch(java.sql.SQLException e){
				System.out.println(e.toString());
			}
		}else{
			this.tigia = tigia;
		}
	}

	public String getChenhlech() 
	{
		return this.chenhlechVND;
	}

	public void setChenhlech(String chenhlechVND)
	{
		this.chenhlechVND = chenhlechVND;
	}

	public String getDoituongdinhkhoanId() {
		return doituongdinhkhoanId;
	}

	public void setDoituongdinhkhoanId(String doituongdinhkhoanId) {
		this.doituongdinhkhoanId = doituongdinhkhoanId;
	}

	public String getMaTenDoiTuongDinhKhoan() {
		return MaTenDoiTuongDinhKhoan;
	}

	public void setMaTenDoiTuongDinhKhoan(String maTenDoiTuongDinhKhoan) {
		MaTenDoiTuongDinhKhoan = maTenDoiTuongDinhKhoan;
	}

	public String getDoiTuongDinhKhoan() {
		return DoiTuongDinhKhoan;
	}

	public void setDoiTuongDinhKhoan(String doiTuongDinhKhoan) {
		DoiTuongDinhKhoan = doiTuongDinhKhoan;
	}

	public String getDinhkhoannoId() {
		return dinhkhoannoId;
	}

	public void setDinhkhoannoId(String dinhkhoannoId) {
		this.dinhkhoannoId = dinhkhoannoId;
	}

	public String getDinhkhoanno() {
		return dinhkhoanno;
	}

	public void setDinhkhoanno(String dinhkhoanno) {
		this.dinhkhoanno = dinhkhoanno;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getDoiTuongChiPhiKhac() {
		return DoiTuongChiPhiKhac;
	}

	public void setDoiTuongChiPhiKhac(String doiTuongChiPhiKhac) {
		DoiTuongChiPhiKhac = doiTuongChiPhiKhac;
	}

	public String getNhomNCCCNId() {
		return nhomNCCCNId;
	}

	public void setNhomNCCCNId(String nhomNCCCNId) {
		this.nhomNCCCNId = nhomNCCCNId;
	}

	public String getNhomNCCCN() {
		return NhomNCCCN;
	}

	public void setNhomNCCCN(String nhomNCCCN) {
		NhomNCCCN = nhomNCCCN;
	}
	
	public ResultSet getSotkRs()
	{
		return this.sotkRs;
	}

	public void setSotkRs(ResultSet sotkRs) 
	{
		this.sotkRs = sotkRs;
	}

	public ResultSet getSotkRs_tp()
	{
		return this.sotkRs_tp;
	}

	public void setSotkRs_tp(ResultSet sotkRs_tp) 
	{
		this.sotkRs_tp = sotkRs_tp;
	}

	public String getTthdCurrent() {
		return tthdCurrent;
	}

	public void setTthdCurrent(String tthdCurrent) {
		this.tthdCurrent = tthdCurrent;
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

	public String getSotaikhoan_tp() 
	{
		return this.sotaikhoan_tp;
	}

	public void setSotaikhoan_tp(String sotk_tp)
	{
		this.sotaikhoan_tp = sotk_tp;
	}

	public String getNoidungtt()
	{
		return this.noidungtt;
	}

	public void setNoidungtt(String ndtt) 
	{
		this.noidungtt = ndtt;
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
	
	public String getSotientt() 
	{
		return this.sotientt;
	}

	public void setSotientt(String sotientt) 
	{
		this.sotientt = sotientt;
	}

	public String getSotienHD() 
	{
		return this.sotienHD;
	}

	public void setSotienHD(String sotienHD)
	{
		this.sotienHD = sotienHD;
	}

	public String getPhinganhang() 
	{
		return this.pnganhang;
	}

	public void setPhinganhang(String pnganhang)
	{
		this.pnganhang = pnganhang;
	}

	public String getThueVAT() 
	{
		return this.thueVAT;
	}

	public void setThueVAT(String thueVAT)
	{
		this.thueVAT = thueVAT;
	}

	public String getSotienttNT() 
	{
		return this.sotienttNT;
	}

	public void setSotienttNT(String sotienttNT) 
	{
		this.sotienttNT = sotienttNT;
	}

	public String getSotienHDNT() 
	{
		return this.sotienHDNT;
	}

	public void setSotienHDNT(String sotienHDNT)
	{
		this.sotienHDNT = sotienHDNT;
	}

	public String getPhinganhangNT() 
	{
		return this.pnganhangNT;
	}

	public void setPhinganhangNT(String pnganhangNT)
	{
		this.pnganhangNT = pnganhangNT;
	}

	public String getThueVATNT() 
	{
		return this.thueVATNT;
	}

	public void setThueVATNT(String thueVATNT)
	{
		this.thueVATNT = thueVATNT;
	}

	public String getMahoadon() 
	{
		return this.mahoadon;
	}

	public void setMahoadon(String mahoadon)
	{
		this.mahoadon = mahoadon;
	}

	public String getMauhoadon() 
	{
		return this.mauhoadon;
	}

	public void setMauhoadon(String mauhoadon)
	{
		this.mauhoadon = mauhoadon;
	}

	public String getKyhieu() 
	{
		return this.kyhieu;
	}

	public void setKyhieu(String kyhieu)
	{
		this.kyhieu = kyhieu;
	}

	public String getSohoadon() 
	{
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon)
	{
		this.sohoadon = sohoadon;
	}

	public String getNgayhoadon() 
	{
		return this.ngayhoadon;
	}

	public void setNgayhoadon(String ngayhoadon)
	{
		this.ngayhoadon = ngayhoadon;
	}

	public String getTenNCC() 
	{
		return this.tenNCC;
	}

	public void setTenNCC(String tenNCC)
	{
		this.tenNCC = tenNCC;
	}

	public String getMST() 
	{
		return this.mst;
	}

	public void setMST(String mst)
	{
		this.mst = mst;
	}

	public String getTienhang() 
	{
		return this.tienhang;
	}

	public void setTienhang(String tienhang)
	{
		this.tienhang = tienhang;
	}

	public String getThuesuat() 
	{
		return this.thuesuat;
	}

	public void setThuesuat(String thuesuat)
	{
		this.thuesuat = thuesuat;
	}

	public String getTienthue() 
	{
		return this.tienthue;
	}

	public void setTienthue(String tienthue)
	{
		this.tienthue = tienthue;
	}

	public String getNhId_VAT() 
	{
		return this.nhId_VAT;
	}

	public void setNhId_VAT(String nhId_VAT)
	{
		this.nhId_VAT = nhId_VAT;
	}

	public String getCnId_VAT() 
	{
		return this.cnId_VAT;
	}

	public void setCnId_VAT(String cnId_VAT)
	{
		this.cnId_VAT = cnId_VAT;
	}
	
	public String getTenNCC_VAT() 
	{
		String tenNCC = "";
		String query = "";
		ResultSet rs;
		try{
			if(!this.tienteId.equals("100000")){ // NGOAI TE
			
				if(this.trichphi.equals("0")){ // NGOAI TE, TRICH PHI BANG NGOAI TE, NGAN HANG TRICH PHI SE BANG NGAN HANG CHUYEN KHOAN
					if(this.sotaikhoan.length() > 0){
						rs = this.db.get("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '" + this.sotaikhoan + "'");
						System.out.println("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '" + this.sotaikhoan + "'");
						if(rs != null){
							rs.next();
							this.nganhangId = rs.getString("NGANHANG_FK");
							this.chinhanhId = rs.getString("CHINHANH_FK");
							rs.close();
						}
					}else{
						this.nganhangId = "";
						this.chinhanhId = "";

					}
					
					if(this.nganhangId.length() > 0){
						query= 	"SELECT NH.PK_SEQ AS NHID, CN.PK_SEQ AS CNID, NH.TEN + ' - ' + CN.TEN AS NHTEN " +
								"FROM ERP_NGANHANG_CONGTY NHCTY " +
								"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NHCTY.NGANHANG_FK " +
								"INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NHCTY.CHINHANH_FK " +
								"WHERE NHCTY.TIENTE_FK = " + this.tienteId + " AND NH.TRANGTHAI = 1 AND NH.PK_SEQ = " + this.nganhangId + " " +
								"AND CN.PK_SEQ = " + this.chinhanhId + " ";
						rs = this.db.get(query);
						if(rs != null){
							try{
								rs.next();
								tenNCC = rs.getString("NHTEN");
								this.nhId_VAT = this.nganhangId;
								this.cnId_VAT = this.chinhanhId;
								rs.close();
							}catch(java.sql.SQLException e){}
						}
					}else{
						tenNCC = "";
						this.nhId_VAT = "";
						this.cnId_VAT = "";				
					}	
			
				}else{ // NGOAI TE, TRICH PHI BANG VND, NGAN HANG TRICH PHI SE BANG NGAN HANG VND
					if(this.sotaikhoan_tp.length() > 0){
						rs = this.db.get("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '" + this.sotaikhoan_tp + "'");
						System.out.println("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '" + this.sotaikhoan_tp + "'");
						if(rs != null){
							rs.next();
							this.nganhang_tpId = rs.getString("NGANHANG_FK");
							this.chinhanh_tpId = rs.getString("CHINHANH_FK");
							rs.close();
						}
					}else{
						this.nganhang_tpId = "";
						this.chinhanh_tpId = "";
					
					}
				
				
					if(this.nganhang_tpId.length() > 0){
						query= 	"SELECT NH.PK_SEQ AS NHID, CN.PK_SEQ AS CNID, NH.TEN + ' - ' + CN.TEN AS NHTEN " +
								"FROM ERP_NGANHANG_CONGTY NHCTY " +
								"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NHCTY.NGANHANG_FK " +
								"INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NHCTY.CHINHANH_FK " +
								"WHERE NHCTY.TIENTE_FK = " + this.tienteId + " AND NH.TRANGTHAI = 1 AND NH.PK_SEQ = " + this.nganhang_tpId + " " +
								"AND CN.PK_SEQ = " + this.chinhanh_tpId + " ";
						rs = this.db.get(query);
						if(rs != null){
							try{
								rs.next();
								tenNCC = rs.getString("NHTEN");
								this.nhId_VAT = this.nganhang_tpId;
								this.cnId_VAT = this.chinhanh_tpId;

								rs.close();
							}catch(java.sql.SQLException e){}
						}
					}else{
						tenNCC = "";
						this.nhId_VAT = "";
						this.cnId_VAT = "";				
				
					}
			
				}
			}else{ // TIEN VND
				if(this.sotaikhoan.length() > 0){
					rs = this.db.get("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '" + this.sotaikhoan + "'");
					System.out.println("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '" + this.sotaikhoan + "'");
					if(rs != null){
						rs.next();
						this.nganhangId = rs.getString("NGANHANG_FK");
						this.chinhanhId = rs.getString("CHINHANH_FK");
						rs.close();
					}
				}else{
					this.nganhangId = "";
					this.chinhanhId = "";

				}

				if(this.nganhangId.length() > 0){
					query	= 	"SELECT NH.PK_SEQ AS NHID, CN.PK_SEQ AS CNID, NH.TEN + ' - ' + CN.TEN AS NHTEN " +
								"FROM ERP_NGANHANG_CONGTY NHCTY " +
								"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NHCTY.NGANHANG_FK " +
								"INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NHCTY.CHINHANH_FK " +
								"WHERE NHCTY.TIENTE_FK = " + this.tienteId + " AND NH.TRANGTHAI = 1 AND NH.PK_SEQ = " + this.nganhangId + " " +
								"AND CN.PK_SEQ = " + this.chinhanhId + " ";
					rs = this.db.get(query);
					if(rs != null){
						try{
							rs.next();
							tenNCC = rs.getString("NHTEN");
							this.nhId_VAT = this.nganhangId;
							this.cnId_VAT = this.chinhanhId;
							rs.close();
						}catch(java.sql.SQLException e){}
					}
				}else{
					tenNCC = "";
					this.nhId_VAT = "";
					this.cnId_VAT = "";				
				}	
			}
		}catch(java.sql.SQLException e){}
		
		System.out.println("TEN NCC:" + query);
		System.out.println(tenNCC);
		return tenNCC;
	}

	public void setTenNCC_VAT(String tenNCC_VAT)
	{
		this.tenNCC_VAT = tenNCC_VAT;
	}

	public boolean createTTHD() 
	{
		if(!this.DoiTuongChiPhiKhac.equals("3")){
			if(this.hoadonList.size() <= 0)
			{
				this.msg = "Không có hóa đơn nào để thanh toán";
				return false;
			}
		}
				
		if(this.htttId.length() <= 0)
		{
			this.msg = "Vui lòng chọn hình thức thanh toán";
			return false;
		}
	
		try 
		{
			String ngaytao = getDateTime();
			
			db.getConnection().setAutoCommit(false);
			
			if(this.sotaikhoan.trim().length() <= 0)
				this.sotaikhoan = "";
			
			this.DoiTuongDinhKhoan = "NULL";
				
			this.doituongdinhkhoanId = "NULL";
			
			
			double tongthanhtoan = 0;
			//Tinh lai tong tien		
				this.nganhangId = "";
				this.chinhanhId = "";
				
				this.nganhang_tpId = "";
				this.chinhanh_tpId = "";

			System.out.println("Hoa don lis	t size: " + this.hoadonList.size());
			for(int i = 0; i < this.hoadonList.size(); i++)
			{
				IHoadon hd = this.hoadonList.get(i);
				if(hd.getThanhtoan().length() > 0)
					tongthanhtoan += Double.parseDouble(hd.getThanhtoan().replaceAll(",", ""));
			}
			
			//CHI THEO NHÓM NHÀ CUNG CẤP
			if(nhomNCCCNId.length() > 0)
			{
				// LƯU VÀO BẢNG ERP_THANHTOANHOADONNCC
				String query = 
				"Insert ERP_THANHTOANHOADONNCC" +
				"(DVTH_FK, NGAYGHINHAN, NHOMNCCCN ,NHANVIEN_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, " +
				" SOTIENTT, SOTIENTTNT, SOTIENHD, SOTIENHDNT, " +
				" PHINGANHANG, PHINGANHANGNT, VAT, VATNT, CHENHLECHVND, " +
				" TRICHPHI, SOTAIKHOAN_TP, NGANHANG_TP_FK, CHINHANH_TP_FK, " +
				" NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAITHANHTOAN, TIENTE_FK, TIGIA, THANHTOANTUTIENVAY, TRANGTHAI  " +
				") " +
				"values("+(this.bophanId.length()==0?null : this.bophanId) +", '" + this.ngayghinhan + "', " + (this.nhomNCCCNId.length()==0?null : this.nhomNCCCNId ) + "," +
						""+(this.NhanvienId.length()==0?null : this.NhanvienId) +", '" + this.htttId + "', " +
						"" + (this.nganhangId.length() == 0?null : this.nganhangId) + ", " + (this.chinhanhId.length() == 0?null : this.chinhanhId) + ", '" + this.sotaikhoan + "', N'" + this.noidungtt + "', " +
						"" + ("" + this.sotientt).replaceAll(",", "") + ", " + this.sotienttNT.replaceAll(",", "") + ", " + this.sotienHD.replaceAll(",", "") + ", " + this.sotienHDNT.replaceAll(",", "") + " , " +
						"" + this.pnganhang.replaceAll(",", "") + ", " + this.pnganhangNT.replaceAll(",", "") + ", " + this.thueVAT.replaceAll(",", "") + ", " + this.thueVATNT.replaceAll(",", "") + ", " + this.chenhlechVND.replaceAll(",", "") + ", " +
						"" + this.trichphi + ", " + this.sotaikhoan_tp + ", " + (this.nganhang_tpId.length() == 0?null : this.nganhang_tpId) + ", " +
						"" + (this.chinhanh_tpId.length() == 0?null : this.chinhanh_tpId) + ", " +
						"'"  + ngaytao + "', '" + this.userId + "', '" + ngaytao + "', '" + this.userId + "','1', " + this.tienteId + ", " + this.tigia.replaceAll(",", "") + " , '"+ this.checkThanhtoantuTV +"',0)";


				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_THANHTOANHOADONNCC: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			}
			else //CHI THEO TỪNG NHÀ CUNG CẤP
			{
				String query = 
				"INSERT ERP_THANHTOANHOADONNCC " +
				"		(DVTH_FK, NGAYGHINHAN, NCC_FK ,NHANVIEN_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, " +
				" 		SOTIENTT, SOTIENTTNT, SOTIENHD, SOTIENHDNT, " +
				" 		PHINGANHANG, PHINGANHANGNT, VAT, VATNT, CHENHLECHVND, " +
				" 		TRICHPHI, SOTAIKHOAN_TP, NGANHANG_TP_FK, CHINHANH_TP_FK, " +
				" 		NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAITHANHTOAN, TIENTE_FK, TIGIA , THANHTOANTUTIENVAY, KHACHHANG_FK, TRANGTHAI " +
				"		) " +
				"VALUES("+(this.bophanId.length()==0?null : this.bophanId) +", '" + this.ngayghinhan + "', " + (this.nccId.length()==0?null : this.nccId ) + "," +
						""+(this.NhanvienId.length()==0?null : this.NhanvienId) +", '" + this.htttId + "', " +
						"" + (this.nganhangId.length() == 0?null : this.nganhangId) + ", " + (this.chinhanhId.length() == 0?null : this.chinhanhId) + ", '" + this.sotaikhoan + "', N'" + this.noidungtt + "', " +
						"" + ("" + this.sotientt).replaceAll(",", "") + ", " + this.sotienttNT.replaceAll(",", "") + ", " + this.sotienHD.replaceAll(",", "") + ", " + this.sotienHDNT.replaceAll(",", "") + " , " +
						"" + this.pnganhang.replaceAll(",", "") + ", " + this.pnganhangNT.replaceAll(",", "") + ", " + this.thueVAT.replaceAll(",", "") + ", " + this.thueVATNT.replaceAll(",", "") + ", " + this.chenhlechVND.replaceAll(",", "") + ", " +
						"" + this.trichphi + ", '" + this.sotaikhoan_tp + "', " + (this.nganhang_tpId.length() == 0?null : this.nganhang_tpId) + ", " + (this.chinhanh_tpId.length() == 0?null : this.chinhanh_tpId) + ", " +
						"'"  + ngaytao + "', '" + this.userId + "', '" + ngaytao + "', '" + this.userId + "','1', " + this.tienteId + ", " + this.tigia.replaceAll(",", "") + ", '"+ this.checkThanhtoantuTV +"', " + (this.khachhangId.length()==0?null : this.khachhangId ) + ",0 )";

				System.out.println(query);
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_THANHTOANHOADONNCC: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			}
			
			String query = "select IDENT_CURRENT('ERP_THANHTOANHOADONNCC') as tthdId";
			
			ResultSet rsTthd = db.get(query);						
			if(rsTthd.next())
			{
				tthdCurrent = rsTthd.getString("tthdId");
				rsTthd.close();
			}
			
			//CHÈN HÓA ĐƠN VÀO BẢNG CHI TIẾT
			for(int i = 0; i < this.hoadonList.size(); i++)
			{
				IHoadon hoadon = this.hoadonList.get(i);

				String thanhtoan = (hoadon.getThanhtoan().replaceAll(",", ""));
				String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				String sotienNT = hoadon.getSotienNT().replaceAll(",", "");
				String conlai = hoadon.getConlai().replaceAll(",", "");
				String loaihd = hoadon.getLoaihd1();
				String sohoadon = hoadon.getSo();
			
				if(thanhtoan.length() > 0)
				{
					if(Float.parseFloat(thanhtoan) != 0)
					{
						if(this.tienteId.equals("100000")){
							query = "Insert ERP_THANHTOANHOADONNCC_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON) " +
									"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "") + "'," +
											" 0, '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"', '"+sohoadon+"')";
							if(loaihd.equals("4"))
							{
								query = "Insert ERP_THANHTOANHOADONNCC_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, LOAITHUE, SOHOADON) " +
								"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "") + "'," +
										" 0, '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"', N'"+ hoadon.getKyhieu() +"', '"+sohoadon+"')";
							}
						
						}else{
							query = "Insert ERP_THANHTOANHOADONNCC_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON) " +
									"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "") + "', " + sotienNT.replaceAll(",", "") + "," +
											" '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"', '"+sohoadon+"')";																					
							if(loaihd.equals("4"))
							{
								query = "Insert ERP_THANHTOANHOADONNCC_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, LOAITHUE, SOHOADON) " +
								"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "") + "', " + sotienNT.replaceAll(",", "") + "," +
										" '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"', N'"+ hoadon.getKyhieu() +"', '"+sohoadon+"')";																					
						
							}
						}					
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_THANHTOANHOADONNCC_HOADON: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			query = "INSERT INTO ERP_THANHTOANHOADONNCC_PHINGANHANG(THANHTOANHD_FK, MAHOADON, MAUHOADON, KYHIEU, SOHOADON, " +
					"NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NGANHANG_FK, CHINHANH_FK) " +
					"VALUES(" + tthdCurrent + ", N'" + this.mahoadon + "', N'" + this.mauhoadon + "', N'" + this.kyhieu + "', '" + this.sohoadon + "'," +
					"'" + this.ngayghinhan + "', N'" + this.tenNCC + "', '" + this.mst + "', " + this.tienhang.replaceAll(",", "") + ", " + this.thuesuat.replaceAll(",", "") + ", " + this.tienthue.replaceAll(",", "") + ", " +
					"" + (this.nhId_VAT.length() == 0?null : this.nhId_VAT) + ", " + (this.cnId_VAT.length() == 0?null : this.cnId_VAT) + ")";
			
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_THANHTOANHOADON_PHINGANHANG: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			System.out.println("Loi trong qua trinh update : "+e.toString());
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
	
			
	//Tinh lai tong tien
	double tongthanhtoan = 0;
	for(int i = 0; i < this.hoadonList.size(); i++)
	{
		IHoadon hd = this.hoadonList.get(i);
		if(hd.getThanhtoan().length() > 0)
			tongthanhtoan += Double.parseDouble(hd.getThanhtoan().replaceAll(",", ""));
	}
	
	
	try 
	{
		String ngaysua = getDateTime();
		
		db.getConnection().setAutoCommit(false);
		
			this.DoiTuongDinhKhoan = "NULL";
		
			this.doituongdinhkhoanId = "NULL";
		
		
		this.sotaikhoan = "NULL";
		this.sotaikhoan_tp = "NULL";
		
		String query = "";
		
  	    query = "update ERP_THANHTOANHOADONNCC " +
				"set NGAYGHINHAN = '" + this.ngayghinhan + "', " +
				"DVTH_FK="+( this.bophanId.length() >0 ?this.bophanId :null)+" , " +
				"NCC_FK = " + ( this.nccId.length() >0 ?this.nccId :null)  + ", " +
				"nhanvien_fk="+( this.NhanvienId.length() >0 ?this.NhanvienId :null)+" , " +
				"HTTT_FK = '" + this.htttId + "', NGANHANG_FK = " + (this.nganhangId.length() == 0?null : this.nganhangId) + ", " +
				"CHINHANH_FK = " + (this.chinhanhId.length() == 0?null : this.chinhanhId) + ", " +
				"SOTAIKHOAN = '" + this.sotaikhoan + "'," +
				" NOIDUNGTT = N'" + this.noidungtt + "', " +
				"SOTIENTT = " + ("" + this.sotientt).replaceAll(",", "") + ", SOTIENTTNT = " + this.sotienttNT.replaceAll(",", "") + ", " +
				"SOTIENHD = " + this.sotienHD.replaceAll(",", "") + ", " +
				"SOTIENHDNT = " + this.sotienHDNT.replaceAll(",", "") + ", PHINGANHANG = " + this.pnganhang.replaceAll(",", "") + ", " +
				"PHINGANHANGNT = " + this.pnganhangNT.replaceAll(",", "") + ", " +
				"VAT = " + this.thueVAT.replaceAll(",", "") + ", VATNT = " + this.thueVATNT.replaceAll(",", "") + ", " +
				"CHENHLECHVND = " + this.chenhlechVND.replaceAll(",", "") + ", " +
				"TRICHPHI = " + this.trichphi + ", SOTAIKHOAN_TP = '" + this.sotaikhoan_tp + "', " +
				"NGANHANG_TP_FK = " + this.nganhang_tpId + ", CHINHANH_TP_FK = " + this.chinhanh_tpId + ", " +
				"NGAYSUA = '" + ngaysua + "', NGUOISUA = '" + this.userId + "', TIENTE_FK = " + this.tienteId + "," +
				" TIGIA = " + this.tigia.replaceAll(",", "") + ", THANHTOANTUTIENVAY = '"+	this.checkThanhtoantuTV +"', khachhang_fk="+( this.khachhangId.length() >0 ?this.khachhangId :null)+"  " +
				" where PK_SEQ = '"  + this.id + "'";
						
		// NẾU CÓ CHECK THANH TOÁN TỪ TIỀN VAY : KHI CẬP NHẬT CHỈ ĐƯỢC SỬA PHÍ NGÂN HÀNG + THUE CÒN LẠI KHÔNG CHO SỬA
		if(Double.parseDouble(this.checkThanhtoantuTV) == 1)
		{
			query = "update ERP_THANHTOANHOADONNCC " +
					"set SOTIENTT = " + ("" + this.sotientt).replaceAll(",", "") + ", SOTIENTTNT = " + this.sotienttNT.replaceAll(",", "") + ", " +
					"PHINGANHANG = " + this.pnganhang.replaceAll(",", "") + ", PHINGANHANGNT = " + this.pnganhangNT.replaceAll(",", "") + ", " +
					"VAT = " + this.thueVAT.replaceAll(",", "") + ", VATNT = " + this.thueVATNT.replaceAll(",", "") + ", " +
					"CHENHLECHVND = " + this.chenhlechVND.replaceAll(",", "") + ", " +
					"NGAYSUA = '" + ngaysua + "', NGUOISUA = '" + this.userId + "' " +
					" where PK_SEQ = '"  + this.id + "'";
		}
		
		if(!db.update(query))
		{
			this.msg = "Khong the cap nhat ERP_THANHTOANHOADON: " + query;
			System.out.println(this.msg);
			db.getConnection().rollback();
			return false;
		}

		
		if(Double.parseDouble(this.checkThanhtoantuTV) == 0)
		{
			query = "delete ERP_THANHTOANHOADONNCC_HOADON where THANHTOANHD_FK = '" + this.id + "'";
			db.update(query);
		}
					
		query = "delete ERP_THANHTOANHOADONNCC_PHINGANHANG where THANHTOANHD_FK = '" + this.id + "'";
		db.update(query);
		
		System.out.println("KICH thuoc hoa don " + this.hoadonList.size());
					
			for(int i = 0; i < this.hoadonList.size(); i++)
			{
				IHoadon hoadon = this.hoadonList.get(i);

				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
				String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				String sotienNT = hoadon.getSotienNT().replaceAll(",", "");
				String conlai = hoadon.getConlai().replaceAll(",", "");
				String loaihd = hoadon.getLoaihd1();
				String sohoadon = hoadon.getSo();
				
				if(thanhtoan.length() > 0)
				{
					if(Float.parseFloat(thanhtoan) != 0)
					{
						if(this.tienteId.equals("100000")){
							query = "Insert ERP_THANHTOANHOADONNCC_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON) " +
									"values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "") + "'," +
											" 0, '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"', '"+sohoadon+"')";
							if(loaihd.equals("4"))
							{
								query = "Insert ERP_THANHTOANHOADONNCC_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, LOAITHUE, SOHOADON) " +
								"values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "") + "'," +
										" 0, '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"', N'"+ hoadon.getKyhieu() +"', '"+sohoadon+"')";
							}
						
						}else{
							query = "Insert ERP_THANHTOANHOADONNCC_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON) " +
									"values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "") + "', " + sotienNT.replaceAll(",", "") + "," +
											" '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"', '"+sohoadon+"')";																					
							if(loaihd.equals("4"))
							{
								query = "Insert ERP_THANHTOANHOADONNCC_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, LOAITHUE, SOHOADON) " +
								"values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "") + "', " + sotienNT.replaceAll(",", "") + "," +
										" '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"', N'"+ hoadon.getKyhieu() +"', '"+sohoadon+"')";																					
						
							}
						}					
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_THANHTOANHOADONNCC_HOADON: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}

			query = "INSERT INTO ERP_THANHTOANHOADONNCC_PHINGANHANG(THANHTOANHD_FK, MAHOADON, MAUHOADON, KYHIEU, SOHOADON, " +
			"NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NGANHANG_FK, CHINHANH_FK) " +
			"VALUES(" + this.id + ", N'" + this.mahoadon + "', N'" + this.mauhoadon + "', N'" + this.kyhieu + "', '" + this.sohoadon + "'," +
			"'" + this.ngayghinhan + "', N'" + this.tenNCC + "', '" + this.mst + "', " + this.tienhang.replaceAll(",", "") + ", " + this.thuesuat.replaceAll(",", "") + ", " + this.tienthue.replaceAll(",", "") + ", " +
			"" + (this.nhId_VAT.length() == 0?null : this.nhId_VAT) + ", " + (this.cnId_VAT.length() == 0?null : this.cnId_VAT) + ")";
	
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_THANHTOANHOADONNCC_PHINGANHANG: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
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
}

	private void XoaThanhToanHoaDon_HoaDon(String hdHienTai) {
		
		
		String sql ="DELETE FROM ERP_THANHTOANHOADON_HOADON\n" +
		"WHERE ERP_THANHTOANHOADON_HOADON.THANHTOANHD_FK = '" + this.id+
		"' AND ERP_THANHTOANHOADON_HOADON.HOADON_FK = '" +hdHienTai+		"'";
		db.update(sql);
		
	}

	private List<String> GetlistHoaDonHienTai() {
		
		
		String sql  = " SELECT ERP_THANHTOANHOADON_HOADON.HOADON_FK FROM ERP_THANHTOANHOADON_HOADON\n" +
		" WHERE ERP_THANHTOANHOADON_HOADON.THANHTOANHD_FK = '" +this.id+"'";
		ResultSet resultSet = db.get(sql);
		List<String> list  = new ArrayList<String>();
		String[] rerult = null;
		try {
//				rerult = (String[])resultSet.getArray("HOADON_FK").getArray();
			
			while (resultSet.next()) {
				list.add(resultSet.getString("HOADON_FK"));
			}
			
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return  list;
		
		
	}
	
	public boolean checkNhomNCC()
	{
		String check = "0";
		String query = "select case when NHOMNCCCN IS null then 0 else 1 end as checkNhom from ERP_THANHTOANHOADON "+
						"where PK_SEQ = '" + this.id + "'" ;
		ResultSet checkNhomRs = db.get(query);
		try {
			while(checkNhomRs.next())
			{
				check = checkNhomRs.getString("checkNhom");
			}
			checkNhomRs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (check.equalsIgnoreCase("1"))
			return true;
		else
			return false;
		
	}
	

	public boolean chotTTHD(String userId)
	{				
		try 
		{
			String ngaysua = getDateTime();
			String ngaytao = getDateTime();
			
			db.getConnection().setAutoCommit(false);
			
			//CẬP NHẬT TRẠNG THÁI
			String query = "update ERP_THANHTOANHOADONNCC set TRANGTHAI = '1', NGUOISUA = '" + userId + "', NGAYSUA = '" + ngaysua + "' where PK_SEQ = '"  + this.id + "'";
			
			System.out.println("1.Cap nhat hoadon: " + query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the chot ERP_THANHTOANHOADONNCC: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			//COPY 1 BẢNG SANG CHỨC NĂNG ỦY NHIỆM CHI ĐỂ KẾ TOÁN ĐIỀN SỐ TÀI KHOẢN VÀ DUYỆT
			
			System.out.println("NHOMNCC:"+nhomNCCCNId);
			if(nhomNCCCNId.equals("1"))
			{	// LƯU VÀO BẢNG ERP_THANHTOANHOADON
				query = 
					"Insert ERP_THANHTOANHOADON" +
					"(DVTH_FK, NGAYGHINHAN, NHOMNCCCN ,NHANVIEN_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, " +
					" SOTIENTT, SOTIENTTNT, SOTIENHD, SOTIENHDNT, " +
					" PHINGANHANG, PHINGANHANGNT, VAT, VATNT, CHENHLECHVND, " +
					" TRICHPHI, SOTAIKHOAN_TP, NGANHANG_TP_FK, CHINHANH_TP_FK, " +
					" NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAITHANHTOAN, TIENTE_FK, TIGIA, THANHTOANTUTIENVAY, ISKTTDUYET, tthdncc_fk  " +
					") " +
					"	SELECT  DVTH_FK, NGAYGHINHAN, NHOMNCCCN, NHANVIEN_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, \n"+
					"			SOTIENTT, SOTIENTTNT, SOTIENHD, SOTIENHDNT, PHINGANHANG, PHINGANHANGNT, VAT, VATNT, CHENHLECHVND, TRICHPHI, SOTAIKHOAN_TP, NGANHANG_TP_FK, \n"+
					"			CHINHANH_TP_FK, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAITHANHTOAN, TIENTE_FK, TIGIA, THANHTOANTUTIENVAY, 1, "+this.id+" \n"+
					"	FROM 	ERP_THANHTOANHOADONNCC WHERE PK_SEQ = "+this.id+" \n";

					System.out.println(query);
					
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_THANHTOANHOADON: " + query;
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
			}
			else //CHI THEO TỪNG NHÀ CUNG CẤP
			{
				query = 
				"Insert ERP_THANHTOANHOADON " +
				"(DVTH_FK, NGAYGHINHAN, NCC_FK ,NHANVIEN_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, " +
				" SOTIENTT, SOTIENTTNT, SOTIENHD, SOTIENHDNT, " +
				" PHINGANHANG, PHINGANHANGNT, VAT, VATNT, CHENHLECHVND, " +
				" TRICHPHI, SOTAIKHOAN_TP, NGANHANG_TP_FK, CHINHANH_TP_FK, " +
				" NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAITHANHTOAN, TIENTE_FK, TIGIA , THANHTOANTUTIENVAY, KHACHHANG_FK, ISKTTDUYET, tthdncc_fk " +
				") " +
				"	SELECT  DVTH_FK, NGAYGHINHAN, NCC_FK, NHANVIEN_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, \n"+
				"			SOTIENTT, SOTIENTTNT, SOTIENHD, SOTIENHDNT, PHINGANHANG, PHINGANHANGNT, VAT, VATNT, CHENHLECHVND, TRICHPHI, SOTAIKHOAN_TP, NGANHANG_TP_FK, \n"+
				"			CHINHANH_TP_FK, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAITHANHTOAN, TIENTE_FK, TIGIA, THANHTOANTUTIENVAY, KHACHHANG_FK, 1, "+this.id+" \n"+
				"	FROM 	ERP_THANHTOANHOADONNCC WHERE PK_SEQ = "+this.id+" \n";

				System.out.println(query);
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_THANHTOANHOADON: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			}
			
			query = "select IDENT_CURRENT('ERP_THANHTOANHOADON') as tthdId";
			
			ResultSet rsTthd = db.get(query);						
			if(rsTthd.next())
			{
				tthdCurrent = rsTthd.getString("tthdId");
				rsTthd.close();
			}
			
			//CHÈN HÓA ĐƠN VÀO BẢNG CHI TIẾT
			for(int i = 0; i < this.hoadonList.size(); i++)
			{
					IHoadon hoadon = this.hoadonList.get(i);

					String thanhtoan = (hoadon.getThanhtoan().replaceAll(",", ""));
					String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
					String sotienNT = hoadon.getSotienNT().replaceAll(",", "");
					String conlai = hoadon.getConlai().replaceAll(",", "");
					String loaihd = hoadon.getLoaihd1();
					String sohoadon = hoadon.getSo();
				
					if(thanhtoan.length() > 0)
					{
						if(Float.parseFloat(thanhtoan) != 0)
						{
							if(this.tienteId.equals("100000")){
								query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON) " +
										"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "") + "'," +
												" 0, '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"', '"+sohoadon+"')";
								if(loaihd.equals("4"))
								{
									query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, LOAITHUE, SOHOADON) " +
									"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "") + "'," +
											" 0, '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"', N'"+ hoadon.getKyhieu() +"', '"+sohoadon+"')";
								}
							
							}else{
								query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON) " +
										"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "") + "', " + sotienNT.replaceAll(",", "") + "," +
												" '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"', '"+sohoadon+"')";																					
								if(loaihd.equals("4"))
								{
									query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, LOAITHUE, SOHOADON) " +
									"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "") + "', " + sotienNT.replaceAll(",", "") + "," +
											" '" + conlai.trim().replaceAll(",", "") + "', '"+ loaihd +"', N'"+ hoadon.getKyhieu() +"', '"+sohoadon+"')";																					
							
								}
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
			
			query = 
			"INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAHOADON, MAUHOADON, KYHIEU, SOHOADON, " +
			"NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NGANHANG_FK, CHINHANH_FK) " +
			"VALUES(" + tthdCurrent + ", N'" + this.mahoadon + "', N'" + this.mauhoadon + "', N'" + this.kyhieu + "', '" + this.sohoadon + "'," +
			"'" + this.ngayghinhan + "', N'" + this.tenNCC + "', '" + this.mst + "', " + this.tienhang.replaceAll(",", "") + ", " + this.thuesuat.replaceAll(",", "") + ", " + this.tienthue.replaceAll(",", "") + ", " +
			"" + (this.nhId_VAT.length() == 0?null : this.nhId_VAT) + ", " + (this.cnId_VAT.length() == 0?null : this.cnId_VAT) + ")";
			
			System.out.println(query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_THANHTOANHOADON_PHINGANHANG: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
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
			
			this.msg = "Khong the cap nhat ERP_HOADONNCC ABC: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public void init()
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = " select hd.prefix,  isnull(hd.LoaiThanhToan,'1') as LoaiThanhToan,hd.nhanvien_fk,hd.pk_seq " +
						", hd.ngayghinhan, hd.trangthai, hd.ncc_fk , hd.httt_fk, hd.nganhang_fk, hd.chinhanh_fk, hd.sotaikhoan," +
						" hd.noidungtt, hd.sotientt ,ncc.ma as mancc,ncc.ten as tenncc, nv.ma as manv,nv.ten as tennv," +
						" hd.khachhang_fk, kh.ma as makh, kh.ten as tenkh, " +
						" isnull(hd.NHOMNCCCN,'0') nhomncccn , nc.DIENGIAI as tennhomncc , tk.SOHIEUTAIKHOAN +' -- '+ tk.TENTAIKHOAN as dinhkhoanno , " +
						" hd.doituongdinhkhoan , hd.MADOITUONGDINHKHOAN,isnull(hd.THANHTOANTUTIENVAY,0) as THANHTOANTUTIENVAY , " +
						"	hd.ghichu, hd.loaithanhtoan, hd.sotienHD, hd.sotienHDNT, hd.sotientt, hd.sotienttNT, hd.phinganhang, hd.phinganhangNT, " +
						"   hd.vat, hd.vatNT, hd.sotienttNT, isnull(hd.chenhlechVND,0) as chenhlechVND , trichphi, sotaikhoan_tp, nganhang_tp_fk, chinhanh_tp_fk, " +
						"   PNH.mahoadon , PNH.mauhoadon, PNH.KYHIEU, PNH.SOHOADON, PNH.NGAYHOADON, PNH.TENNCC, PNH.MST, isnull(PNH.TIENHANG,0) as TIENHANG, isnull(PNH.THUESUAT,0) as THUESUAT, " +
						"   isnull(PNH.TIENTHUE,0) as TIENTHUE, PNH.NGANHANG_FK AS NGANHANG_PNH_FK, PNH.CHINHANH_FK AS CHINHANH_PNH_FK, hd.TIENTE_FK, hd.TIGIA ,  " +
						"   hd.dvth_fk, dvth.pk_seq as dvthId, dvth.ma as dvthMa, dvth.Ten as dvthTen \n"+
						
						" from ERP_THANHTOANHOADONNCC hd  " +
						" left join ERP_THANHTOANHOADONNCC_PHINGANHANG PNH on PNH.THANHTOANHD_FK = hd.PK_SEQ " +
						
						" left join erp_nhacungcap ncc on ncc.pk_Seq=hd.ncc_fk " +						
						" left join erp_nhanvien nv on nv.pk_Seq=hd.nhanvien_fk  " +
						" left join erp_khachhang kh on kh.pk_seq = hd.khachhang_fk " +
						" left join erp_donvithuchien dvth on dvth.pk_seq = hd.dvth_fk " +
						"   left join NHOMNHACUNGCAPCN nc on nc.PK_SEQ = hd.Nhomncccn " +
						" left join ERP_TAIKHOANKT tk on tk.SOHIEUTAIKHOAN = hd.dinhkhoanno " +
						"where hd.pk_seq = '" + this.id + "'";
		System.out.println("GET SQL : "+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngayghinhan = rs.getString("ngayghinhan");
					this.nccId = rs.getString("ncc_fk")== null ? "": rs.getString("ncc_fk") ;
					this.htttId = rs.getString("httt_fk");
					this.nhomNCCCNId = rs.getString("NHOMNCCCN");
					System.out.println("NhomNCC:"+ nhomNCCCNId);
					this.prefix = rs.getString("prefix");
					this.checkThanhtoantuTV= rs.getString("THANHTOANTUTIENVAY");
					
					if(this.htttId.equals("100001"))
					{
						this.nganhangId = rs.getString("nganhang_fk")==null ? "":rs.getString("nganhang_fk"); 
						this.chinhanhId = rs.getString("chinhanh_fk")==null ? "":rs.getString("chinhanh_fk");
						this.sotaikhoan = rs.getString("sotaikhoan")==null ? "":rs.getString("sotaikhoan");
					}

					this.NhanvienId=rs.getString("nhanvien_fk") ;
					if(this.NhanvienId==null){
						this.NhanvienId="";
						if(this.nccId.length() > 1)
						{
							this.DoiTuongChiPhiKhac = "1";
							this.TenHienThi=rs.getString("ncc_fk")+" -- "+rs.getString("mancc")+" -- "+rs.getString("tenncc");
						}
						else
						{
							this.TenHienThi=rs.getString("NHOMNCCCN")+" -- "+rs.getString("tennhomncc");
						}
					}
					if(this.nccId.length() <=1 && this.NhanvienId.length() >1){
						this.nccId="";
						this.DoiTuongChiPhiKhac = "0";
						this.TenHienThi=rs.getString("nhanvien_fk")+" -- "+rs.getString("manv")+" -- "+rs.getString("tennv");
					}
					
					this.khachhangId = rs.getString("khachhang_fk")== null ? "": rs.getString("khachhang_fk") ;
					if(this.khachhangId.length() > 0){
						this.DoiTuongChiPhiKhac = "2";
						this.TenHienThi=rs.getString("khachhang_fk")+" -- "+rs.getString("makh")+","+rs.getString("tenkh");
					}
					
					this.bophanId = rs.getString("dvth_fk")== null ? "": rs.getString("dvth_fk") ;
					if(this.bophanId.length() > 0){
						this.DoiTuongChiPhiKhac = "4";
						this.bophanTen =rs.getString("dvthId")+" - "+rs.getString("dvthMa")+","+rs.getString("dvthTen");
					}
					
					this.noidungtt = rs.getString("noidungtt");
					this.sotientt = formatter.format(rs.getDouble("sotientt"));
					
					if(this.bophanId.trim().length() <= 0 && this.nccId.trim().length() <= 0 && this.NhanvienId.trim().length() <= 0 && this.khachhangId.trim().length() <= 0 ) // tra Khac
					{
						this.DoiTuongChiPhiKhac = "3";
						this.dinhkhoanno = rs.getString("dinhkhoanno");
						this.DoiTuongDinhKhoan = rs.getString("doituongdinhkhoan")== null ? "": rs.getString("doituongdinhkhoan") ;
						this.doituongdinhkhoanId = rs.getString("MADOITUONGDINHKHOAN")== null ? "": rs.getString("MADOITUONGDINHKHOAN") ;
						
					}
					
					this.sotientt  = rs.getString("sotientt").replaceAll(",", "");
					this.sotienttNT = rs.getString("sotienttNT").replaceAll(",", "");
					this.sotienHD  = rs.getString("sotienHD").replaceAll(",", "");
					this.sotienHDNT = rs.getString("sotienHDNT").replaceAll(",", "");
					this.pnganhang = rs.getString("phinganhang").replaceAll(",", "");
					this.pnganhangNT = rs.getString("phinganhangNT").replaceAll(",", "");
					this.thueVAT = rs.getString("vat").replaceAll(",", "");
					this.thueVATNT = rs.getString("vatNT").replaceAll(",", "");
					this.chenhlechVND = rs.getString("chenhlechVND").replaceAll(",", "");
					this.trichphi = rs.getString("trichphi");					
					this.sotaikhoan_tp = rs.getString("sotaikhoan_tp") == null ? "" :rs.getString("sotaikhoan_tp") ;
					this.nganhang_tpId = rs.getString("nganhang_tp_fk")== null ? "" :rs.getString("nganhang_tp_fk");					
					this.chinhanh_tpId = rs.getString("chinhanh_tp_fk")== null ? "" :rs.getString("chinhanh_tp_fk");					
					this.mahoadon = rs.getString("mahoadon")== null ? "":rs.getString("mahoadon");
					this.mauhoadon = rs.getString("mauhoadon")== null ?"":rs.getString("mauhoadon");
					this.kyhieu = rs.getString("KYHIEU")== null ?"":rs.getString("KYHIEU");
					this.sohoadon = rs.getString("SOHOADON")== null ?"":rs.getString("SOHOADON");
					this.ngayhoadon = rs.getString("NGAYHOADON");
					
					this.tenNCC = rs.getString("TENNCC")== null ? "" : rs.getString("TENNCC");
					this.mst = rs.getString("MST")== null ?"":rs.getString("MST");
					this.tienhang = rs.getString("TIENHANG").replaceAll(",", "");
					
					this.thuesuat = rs.getString("THUESUAT").replaceAll(",", "");
					this.tienthue = rs.getString("TIENTHUE").replaceAll(",", "");

					this.nhId_VAT = rs.getString("NGANHANG_PNH_FK")==null?"":rs.getString("NGANHANG_PNH_FK");
					this.cnId_VAT = rs.getString("CHINHANH_PNH_FK")==null?"":rs.getString("CHINHANH_PNH_FK");
					this.tienteId = rs.getString("TIENTE_FK");
					
					this.tigia = rs.getString("TIGIA").replaceAll(",", "");
					


				}
				rs.close();
			} 
			catch (Exception e) {
				System.out.println(e.toString());
				
			}
		}
		
		//khoi tao them loai KHAC
		if(this.DoiTuongDinhKhoan != null && !(this.DoiTuongDinhKhoan.equals("")))
		{
			createDoiTuongDinhKhoan();
		}
		
		this.createRs();
	}

	public void createDoiTuongDinhKhoan()
	{
		String query = "";
		if(this.DoiTuongDinhKhoan.equals("1"))
		{
			query = "select cast(PK_SEQ as varchar) + ' -- ' + MA+ ',' + TEN  as maten from SANPHAM where PK_SEQ = "+ this.doituongdinhkhoanId ;
		}
		else if (this.DoiTuongDinhKhoan.equals("2"))
		{
			query = "select cast(PK_SEQ as varchar) + ' -- ' + MA+ ',' + TEN  as maten from ERP_NGANHANG where PK_SEQ = "+ this.doituongdinhkhoanId ;
		}
		else if (this.DoiTuongDinhKhoan.equals("3"))
		{
			query = "select cast(PK_SEQ as varchar) + ' -- ' + MA+ ',' + TEN  as maten from ERP_NHACUNGCAP where PK_SEQ = "+ this.doituongdinhkhoanId ;
		}
		else if (this.DoiTuongDinhKhoan.equals("4"))
		{
			query = "select cast(PK_SEQ as varchar) + ' -- ' + MA+ ',' + TEN  as maten from ERP_TAISANCODINH where PK_SEQ = "+ this.doituongdinhkhoanId ;
		}
		else if (this.DoiTuongDinhKhoan.equals("5"))
		{
			query = "select cast(PK_SEQ as varchar) + ' -- ' + MA+ ',' + TEN  as maten from NHAPHANPHOI where PK_SEQ = "+ this.doituongdinhkhoanId ;
		}
		else if (this.DoiTuongDinhKhoan.equals("6"))
		{
			query = "select cast(PK_SEQ as varchar) + ' -- ' + MA+ ',' + TEN  as maten from ERP_NHANVIEN where PK_SEQ = "+ this.doituongdinhkhoanId ;
		}
		
		System.out.println("GET doi tuong : "+query);
		ResultSet dtrs = db.get(query);
		if(dtrs != null)
			try {
				while(dtrs.next())
				{
					this.MaTenDoiTuongDinhKhoan = dtrs.getString("maten");
				}
				dtrs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public void initDisplay()
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query =  " select hd.prefix,  isnull(hd.LoaiThanhToan,'1') as LoaiThanhToan,hd.nhanvien_fk,hd.pk_seq " +
						", hd.ngayghinhan, hd.trangthai, hd.ncc_fk , hd.httt_fk, hd.nganhang_fk, hd.chinhanh_fk, hd.sotaikhoan," +
						" hd.noidungtt, hd.sotientt ,ncc.ma as mancc,ncc.ten as tenncc, nv.ma as manv,nv.ten as tennv," +
						" hd.khachhang_fk, kh.ma as makh, kh.ten as tenkh, " +
						" isnull(hd.NHOMNCCCN,'0') nhomncccn , nc.DIENGIAI as tennhomncc , tk.SOHIEUTAIKHOAN +' -- '+ tk.TENTAIKHOAN as dinhkhoanno , " +
						" hd.doituongdinhkhoan , hd.MADOITUONGDINHKHOAN,isnull(hd.THANHTOANTUTIENVAY,0) as THANHTOANTUTIENVAY , " +
						"	hd.ghichu, hd.loaithanhtoan, hd.sotienHD, hd.sotienHDNT, hd.sotientt, hd.sotienttNT, hd.phinganhang, hd.phinganhangNT, " +
						"   hd.vat, hd.vatNT, hd.sotienttNT, isnull(hd.chenhlechVND,0) as chenhlechVND , trichphi, sotaikhoan_tp, nganhang_tp_fk, chinhanh_tp_fk, " +
						"   PNH.mahoadon , PNH.mauhoadon, PNH.KYHIEU, PNH.SOHOADON, PNH.NGAYHOADON, PNH.TENNCC, PNH.MST, isnull(PNH.TIENHANG,0) as TIENHANG, isnull(PNH.THUESUAT,0) as THUESUAT, " +
						"   isnull(PNH.TIENTHUE,0) as TIENTHUE, PNH.NGANHANG_FK AS NGANHANG_PNH_FK, PNH.CHINHANH_FK AS CHINHANH_PNH_FK, hd.TIENTE_FK, hd.TIGIA ,  " +
						"   hd.dvth_fk, dvth.pk_seq as dvthId, dvth.ma as dvthMa, dvth.Ten as dvthTen \n"+						
						" from ERP_THANHTOANHOADONNCC hd  " +
						" left join ERP_THANHTOANHOADONNCC_PHINGANHANG PNH on PNH.THANHTOANHD_FK = hd.PK_SEQ " +						
						" left join erp_nhacungcap ncc on ncc.pk_Seq=hd.ncc_fk " +						
						" left join erp_nhanvien nv on nv.pk_Seq=hd.nhanvien_fk  " +
						" left join erp_khachhang kh on kh.pk_seq = hd.khachhang_fk " +
						" left join erp_donvithuchien dvth on dvth.pk_seq = hd.dvth_fk " +
						" left join NHOMNHACUNGCAPCN nc on nc.PK_SEQ = hd.Nhomncccn " +
						" left join ERP_TAIKHOANKT tk on tk.SOHIEUTAIKHOAN = hd.dinhkhoanno " +
						"where hd.pk_seq = '" + this.id + "'";
		System.out.println("GET SQL : "+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngayghinhan = rs.getString("ngayghinhan");
					this.nccId = rs.getString("ncc_fk")== null ? "": rs.getString("ncc_fk") ;
					this.htttId = rs.getString("httt_fk");
					this.nhomNCCCNId = rs.getString("NHOMNCCCN");
					this.prefix = rs.getString("prefix");
					this.checkThanhtoantuTV= rs.getString("THANHTOANTUTIENVAY");
					
					if(this.htttId.equals("100001"))
					{
						this.nganhangId = rs.getString("nganhang_fk");
						this.chinhanhId = rs.getString("chinhanh_fk");
						this.sotaikhoan = rs.getString("sotaikhoan");
					}

					this.NhanvienId=rs.getString("nhanvien_fk") ;
					if(this.NhanvienId==null){
						this.NhanvienId="";
						if(this.nccId.length() > 1)
						{
							this.DoiTuongChiPhiKhac = "1";
							this.TenHienThi=rs.getString("ncc_fk")+" -- "+rs.getString("mancc")+" -- "+rs.getString("tenncc");
						}
						else
						{
							this.TenHienThi=rs.getString("NHOMNCCCN")+" -- "+rs.getString("tennhomncc");
						}
					}
					if(this.nccId.length() <=1 && this.NhanvienId.length() >1){
						this.nccId="";
						this.DoiTuongChiPhiKhac = "0";
						this.TenHienThi=rs.getString("nhanvien_fk")+" -- "+rs.getString("manv")+" -- "+rs.getString("tennv");
					}
					
					this.khachhangId = rs.getString("khachhang_fk")== null ? "": rs.getString("khachhang_fk") ;
					if(this.khachhangId.length() > 0){
						this.DoiTuongChiPhiKhac = "2";
						this.TenHienThi=rs.getString("khachhang_fk")+" -- "+rs.getString("makh")+","+rs.getString("tenkh");
					}
					
					this.bophanId = rs.getString("dvth_fk")== null ? "": rs.getString("dvth_fk") ;
					if(this.bophanId.length() > 0){
						this.DoiTuongChiPhiKhac = "4";
						this.bophanTen =rs.getString("dvthId")+" - "+rs.getString("dvthMa")+","+rs.getString("dvthTen");
					}
					
					this.noidungtt = rs.getString("noidungtt");
					this.sotientt = formatter.format(rs.getDouble("sotientt"));
					
					if(this.bophanId.trim().length() <= 0 && this.nccId.trim().length() <= 0 && this.NhanvienId.trim().length() <= 0 && this.khachhangId.trim().length() <= 0 ) // tra Khac
					{
						this.DoiTuongChiPhiKhac = "3";
						this.dinhkhoanno = rs.getString("dinhkhoanno");
						this.DoiTuongDinhKhoan = rs.getString("doituongdinhkhoan")== null ? "": rs.getString("doituongdinhkhoan") ;
						this.doituongdinhkhoanId = rs.getString("MADOITUONGDINHKHOAN")== null ? "": rs.getString("MADOITUONGDINHKHOAN") ;
						
					}
					
					this.sotientt  = rs.getString("sotientt").replaceAll(",", "");
					this.sotienttNT = rs.getString("sotienttNT").replaceAll(",", "");
					this.sotienHD  = rs.getString("sotienHD").replaceAll(",", "");
					this.sotienHDNT = rs.getString("sotienHDNT").replaceAll(",", "");
					this.pnganhang = rs.getString("phinganhang").replaceAll(",", "");
					this.pnganhangNT = rs.getString("phinganhangNT").replaceAll(",", "");
					this.thueVAT = rs.getString("vat").replaceAll(",", "");
					this.thueVATNT = rs.getString("vatNT").replaceAll(",", "");
					this.chenhlechVND = rs.getString("chenhlechVND").replaceAll(",", "");
					this.trichphi = rs.getString("trichphi");					
					this.sotaikhoan_tp = rs.getString("sotaikhoan_tp") == null ? "" :rs.getString("sotaikhoan_tp") ;
					this.nganhang_tpId = rs.getString("nganhang_tp_fk")== null ? "" :rs.getString("nganhang_tp_fk");					
					this.chinhanh_tpId = rs.getString("chinhanh_tp_fk");
					this.mahoadon = rs.getString("mahoadon")== null ? "":rs.getString("mahoadon");
					this.mauhoadon = rs.getString("mauhoadon")== null ?"":rs.getString("mauhoadon");
					this.kyhieu = rs.getString("KYHIEU")== null ?"":rs.getString("KYHIEU");
					this.sohoadon = rs.getString("SOHOADON")== null ?"":rs.getString("SOHOADON");
					this.ngayhoadon = rs.getString("NGAYHOADON");
					
					this.tenNCC = rs.getString("TENNCC")== null ? "" : rs.getString("TENNCC");
					this.mst = rs.getString("MST")== null ?"":rs.getString("MST");
					this.tienhang = rs.getString("TIENHANG").replaceAll(",", "");
					
					this.thuesuat = rs.getString("THUESUAT").replaceAll(",", "");
					this.tienthue = rs.getString("TIENTHUE").replaceAll(",", "");

					this.nhId_VAT = rs.getString("NGANHANG_PNH_FK")==null?"":rs.getString("NGANHANG_PNH_FK");
					this.cnId_VAT = rs.getString("CHINHANH_PNH_FK")==null?"":rs.getString("CHINHANH_PNH_FK");
					this.tienteId = rs.getString("TIENTE_FK");
					
					this.tigia = rs.getString("TIGIA").replaceAll(",", "");
					
				}
				rs.close();
			} 
			catch (Exception e) {
				System.out.println(e.toString());
				
			}
		}
		
		//khoi tao them loai KHAC
		if(this.DoiTuongDinhKhoan != null && !(this.DoiTuongDinhKhoan.equals("")))
		{
			createDoiTuongDinhKhoan();
		}
		

		Utility util = new Utility();
		
		this.sotkRs = db.get("SELECT NH_CTY.SOTAIKHOAN, NH_CTY.SOTAIKHOAN + ' - ' + NH.TEN + ' - ' + CN.TEN + ', ' + TT.MA  AS TAIKHOAN " +
				 			 "FROM ERP_NGANHANG_CONGTY NH_CTY " +
				 			 "INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK " + 
				 			 "INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK " +
				 			 "INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK " +
				 			 "WHERE NH_CTY.TRANGTHAI = 1 AND NH_CTY.CONGTY_FK = '" + this.ctyId + "'  " );
      
		this.sotkRs_tp = db.get("SELECT NH_CTY.SOTAIKHOAN, NH_CTY.SOTAIKHOAN + ' - ' + NH.TEN + ' - ' + CN.TEN + ', ' + TT.MA  AS TAIKHOAN " +
	 			 				"FROM ERP_NGANHANG_CONGTY NH_CTY " +
	 			 				"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK " + 
	 			 				"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK " +
	 			 				"INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK " +
	 			 				"WHERE NH_CTY.TRANGTHAI = 1 AND NH_CTY.CONGTY_FK = '" + this.ctyId + "' AND TT.PK_SEQ = 100000 " );
    
		this.tienteRs = db.get("select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE ");
		

		this.nccRs = db.get("select pk_seq, ma + ', ' + ten as TEN from erp_nhacungcap where trangthai = '1' and duyet= '1' ");
		this.NhanvienRs = db.get("SELECT PK_SEQ ,MA+','+TEN AS TEN   FROM ERP_NHANVIEN WHERE TRANGTHAI=1 ");
		this.nhomNCCRs = db.get("SELECT PK_SEQ, DIENGIAI AS TEN FROM NHOMNHACUNGCAPCN where TRANGTHAI = 1") ;
		this.khachhangRs = db.get("select pk_seq, ma + ', ' + ten as TEN from KHACHHANG where trangthai = '1'" );
		
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1' and pk_seq = '100001' ");
		this.nganhangRs = db.get("select pk_seq, ma + ', ' + ten as nhTen from erp_nganhang where PK_SEQ in ( select NganHang_FK from ERP_NGANHANG_CONGTY where congty_fk = '"+this.ctyId+"' ) ");
		 
		if(!this.DoiTuongChiPhiKhac.equals("3")) // neu khac loai  (KHAC) 
		{			
			if(  this.htttId.length() > 0 && this.hoadonList.size() <= 0)
			{				
				// LOAD NHỮNG HD CỦA NHÀ CUNG CẤP
				if(this.nccId.trim().length() > 0) 
				{
				   //LOAIHD: 0 - HOADONNCC
					query = " select 0 as LOAIHD, b.pk_seq, isnull(b.sohoadon, '') as sohoadon,  isnull(b.kyhieu, '') as kyhieu, b.ngayhoadon, \n" +
							"         b.sotienAVAT - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVAT, SOTIENTT, \n" +
							" ISNULL( (  \n" +
							"			Select distinct  \n" +
							"               ( Select cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()]  \n" +
							"				  From ERP_HOADONNCC_PHIEUNHAP PO1   \n" +
							"			      Where PO1.hoadonncc_fk = b.pk_seq \n" +
							"			      For XML PATH ('')) [phongbanChon_fk]  \n" +
							"			From ERP_HOADONNCC_PHIEUNHAP PO  \n" +
							"			where PO.hoadonncc_fk = b.pk_seq \n" +
							"            ), '' )   as POID, c.TIENTE_FK AS TTID, c.TIGIA, b.NGAYDENHANTT \n" +
							" from ERP_THANHTOANHOADON_HOADON a \n" +
							" inner join ERP_HOADONNCC b on a.hoadon_fk = b.pk_seq  and a.loaihd = 0  \n" +
							" inner join ERP_PARK c on b.park_fk = c.pk_seq \n" +
							" left join	\n" +
							"   ( select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
							"     from ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
							"     where TTHD.LOAIHD = 0 AND TT.trangthai !=2 and TTHD.thanhtoanhd_fk != '" + this.id + "' \n" +  //sua trang thai !=2 thanh =1
							"           and TTHD.hoadon_fk in ( select hoadon_fk \n" +
					        "									from ERP_THANHTOANHOADON_HOADON \n" +
							"									where thanhtoanhd_fk = '" + this.id + "' and loaihd = 0 ) \n" +
							"     group by hoadon_fk) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
							" where a.thanhtoanhd_fk = '" + this.id + "' and c.TIENTE_FK = " + this.tienteId + " \n" +
							" UNION ALL \n";
					
					
					// LOAIHD: 1 - TAMUNG(NCC)		 				
				   			query= query + "  \n" +
							" select 1 as LOAIHD, HOADON_FK AS PK_SEQ, CAST(HOADON_FK as nvarchar(50)) AS SOHOADON , N'TẠM ỨNG ' as   KYHIEU ,a.ngaytamung as ngayhoadon \n" +
							"        ,a.sotientamung -( SELECT ISNULL(SUM(CT.SOTIENTT),0) \n" +
							"                           FROM ERP_THANHTOANHOADON_HOADON CT INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=CT.THANHTOANHD_FK \n" + 
							"                           WHERE ct.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND CT.HOADON_FK = B.HOADON_FK and a.pk_seq <>"+this.id+" )  AS sotienAVAT , \n" +
							"         sotientt, '' as POID, a.TIENTE_FK as TTID ,"+this.tigia.replace(",", "")+" AS TIGIA , isnull(a.ngaydenhantt,'') as ngaydenhantt \n" +
							" from ERP_THANHTOANHOADON_HOADON b inner join ERP_TAMUNG a on a.pk_seq=b.HOADON_FK  AND b.LOAIHD = 1 \n" +
							"      and ( a.sotientamung -( SELECT ISNULL(SUM(CT.SOTIENTT),0) \n" +
							"                              FROM ERP_THANHTOANHOADON_HOADON CT INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=CT.THANHTOANHD_FK \n" +
							"                              WHERE ct.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND CT.HOADON_FK=B.HOADON_FK and a.pk_seq <>"+this.id+" ) >0) \n" +
							" where thanhtoanhd_fk="+this.id+" and b.LOAIHD = 1 \n" ;
					  
					
				  // LOAIHD: 2 - CHIPHINOIBO						 

					query += " UNION ALL \n"; 
					query += " select 2 as LOAIHD, mh.PK_SEQ, cast(mh.PK_SEQ as nvarchar(50)) as sohoadon,  N'Chi phí nội bộ'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , \n" +
							"        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
							"		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA , mh.ngaydenhantt  \n"+
							" from ERP_MUAHANG mh \n" +
							" 	left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 2  \n"+
							" 	left join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ \n"+
							" 	left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
							" where ncc.NOIBO = 1 and  mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" +this.id+ "' \n";

				// LOAIHD: 3 - CHIPHINHANHANG

					query += " UNION ALL \n"; 
					
					query += "select distinct 3 as LOAIHD, cpct.pk_seq , cpct.SOCHUNGTU as sohoadon, cpct.KYHIEUCHUNGTU as kyhieu , cpct.NGAYCHUNGTU as ngayhoadon \n" +
							" ,tt.sotienavat as sotienavat, tt.SOTIENTT as sotientt  , cast(mh.PK_SEQ as nvarchar(50)) as POID, isnull(cp.TIENTE_FK,'100000') as ttId,  isnull(cp.TIGIA,1) as TIGIA, cp.NGAYDENHANTT \n" +
							" from ERP_CHIPHINHAPKHAU_CHITIET cpct \n" +
							" left join ERP_CHIPHINHAPKHAU cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n" +
							" left join ERP_NHANHANG nh on nh.PK_SEQ = cp.nhanhang_fk \n" +
							" left join ERP_MUAHANG mh on mh.PK_SEQ = nh.MUAHANG_FK  \n" +
							" left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ  AND TT.LOAIHD = 3 \n" +
							" left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
							" where cp.NCCID_CN = '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"'  \n";
					
					// LOAIHD: 4 - THUENHAPKHAU						

						query += "UNION ALL \n"; 
						query +="select distinct 4 as LOAIHD, tnk.pk_seq , tnk.SOCHUNGTU as sohoadon, N'Thuế nhập khẩu' as kyhieu , tnk.NGAYCHUNGTU as ngayhoadon \n" +
							" 		,tt.sotienavat, tt.SOTIENTT as sotientt  ,'' as POID  \n" +
							" 		,'100000' as ttId, '1' as TIGIA, tnk.ngaydenhantt \n" +
							"from ERP_THUENHAPKHAU tnk \n" +
							"		left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND TT.LOAIHD = 4 \n" +
							"		left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
							" where tnk.NCC_FK= '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and tt.LOAITHUE = N'Thuế nhập khẩu' \n" +
							
							" union all \n" +
							
							"select distinct  4 as LOAIHD, tnk.pk_seq , tnk.SOCHUNGTU as sohoadon, N'VAT nhập khẩu' as kyhieu , tnk.NGAYCHUNGTU as ngayhoadon \n" +
							" 		,tt.sotienavat as sotienavat, tt.SOTIENTT as sotientt  , '' as POID \n" +
							" 		,'100000' as ttId, '1' as TIGIA,  tnk.ngaydenhantt \n" +
							"from ERP_THUENHAPKHAU tnk \n" +
							"		left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND TT.LOAIHD = 4 \n" +
							"		left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
							" where tnk.NCC_FK= '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and tt.LOAITHUE = N'VAT nhập khẩu' \n";
					
					// LOAIHD: 5 - CHIPHIKHAC

					 query +=" UNION ALL \n"; 
					 query +="select distinct 5 as LOAIHD, cp.pk_seq , cast(cp.PK_SEQ as nvarchar(50)) as sohoadon, cp.DIENGIAI as kyhieu ,  cp.NGAY as ngayhoadon \n" +
							"      ,case when cp.TIENTE_FK= '100000' then tt.sotienavat else tt.SOTIENNT end as sotienavat, tt.SOTIENTT as sotientt  , '' as POID, cp.TIENTE_FK as ttId, \n" +
							"      isnull(cp.tigia,1) as tigia , isnull(cp.ngaydenhantt,'') as ngaydenhantt \n" +
							"from ERP_CHIPHIKHAC_CHITIET cpct \n" +
							"     left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n" +
							"     left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n" +
							"     left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
							"where cp.DOITUONG = '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and cp.LOAI= '0' \n";
					
				// LOAIHD: 6 - DENGHITHANHTOAN							 
					query += " UNION ALL \n"; 
					query += " select 6 as LOAIHD, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , \n" +
							"        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
							"		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA, mh.ngaydenhantt  \n"+
							" from ERP_MUAHANG mh \n" +
							" 	inner join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ  AND TT.LOAIHD = 6 \n"+
							" 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"+
							" 	inner join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
							" where   mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" +this.id+ "'  \n";

					
				// LOAD NHỮNG PHIẾU CHI (ĐỀ NGHỊ TT ĐÃ CHỐT)

						query += " UNION ALL \n" ;
						query += " select 8 as LOAIHD, tt.HOADON_FK as PK_SEQ, cast(tt.HOADON_FK as nvarchar(50)) as sohoadon,  N'Chi đề nghị thanh toán'  AS KYHIEU , \n"+
								"       (select a.NGAYGHINHAN from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as ngayhoadon , \n" +
								"       ( CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END) as SOTIENAVAT, \n"+
								"		 tt.SOTIENTT as SOTIENTT , '' as POID , tthd.TIENTE_FK as ttId,  (select a.TIGIA from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as TIGIA,  '' ngaydenhantt  \n"+
								" from  ERP_THANHTOANHOADON_HOADON tt  \n"+
								" 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  and tt.LOAIHD = 8 \n"+
								" 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tthd.NHANVIEN_FK \n"+
								" where   tthd.NCC_FK = '" + this.nccId + "' and tthd.PK_SEQ = "+ this.id +" \n";
											
					
							
				// LOAD NHỮNG PHIẾU CHI (ĐỀ NGHỊ TẠM ỨNG ĐÃ CHỐT)
					query += " UNION ALL \n" ;
					query += " select 9 as LOAIHD, tt.HOADON_FK as PK_SEQ, cast(tt.HOADON_FK as nvarchar(50)) as sohoadon,  N'Chi đề nghị tạm ứng'  AS KYHIEU , \n"+
							"       (select a.NGAYGHINHAN from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as ngayhoadon , \n" +
							"       ( CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END) as SOTIENAVAT, \n"+
							"		 tt.SOTIENTT as SOTIENTT , '' as POID , tthd.TIENTE_FK as ttId,  (select a.TIGIA from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as TIGIA,  '' ngaydenhantt  \n"+
							" from  ERP_THANHTOANHOADON_HOADON tt  \n"+
							" 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  and tt.LOAIHD = 9 \n"+
							" 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tthd.NHANVIEN_FK \n"+
							" where   tthd.NCC_FK = '" + this.nccId + "' and tthd.PK_SEQ = "+ this.id +" \n";

				}
				
				// LOAD NHỮNG HD CỦA NHÂN VIÊN
				if(this.NhanvienId.trim().length() > 0)
				{
					// LOAIHD: 1 - TAMUNG
						query =
							" select 1 AS LOAIHD, B.HOADON_FK AS PK_SEQ, cast(B.HOADON_FK as nvarchar(50)) AS SOHOADON , N'TẠM ỨNG ' as   KYHIEU ,a.ngaytamung as ngayhoadon  \n" +
							"       ,a.sotientamung -( SELECT ISNULL(SUM(CT.SOTIENTT),0) \n" +
							"                          FROM ERP_THANHTOANHOADON_HOADON CT INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=CT.THANHTOANHD_FK \n" +
							"                          WHERE  CT.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND CT.HOADON_FK=B.HOADON_FK and a.pk_seq <>"+this.id+" )  AS sotienAVAT , \n" +
							"  sotientt, '' as POID,  a.TIENTE_FK as TTID ,"+this.tigia.replace(",", "")+" AS TIGIA ,isnull(a.ngaydenhantt,'') as ngaydenhantt \n" +
							" from ERP_THANHTOANHOADON_HOADON b \n" +
							"      inner join ERP_TAMUNG a on a.pk_seq=b.HOADON_fk  and b.loaihd = 1  \n" +
							"      and ( a.sotientamung -( SELECT ISNULL(SUM(CT.SOTIENTT),0) \n" +
							"                              FROM ERP_THANHTOANHOADON_HOADON CT INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=CT.THANHTOANHD_FK \n" + 
							"                              WHERE CT.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND CT.HOADON_FK=B.HOADON_FK and a.pk_seq <>"+this.id+" ) >0) \n" +
							" where   thanhtoanhd_fk="+this.id+"  \n"  ;						

					// LOAIHD: 5 - CHIPHIKHAC

					 query +=" UNION ALL \n"; 
					 query +="select distinct 5 as LOAIHD, cp.pk_seq , cast(cp.PK_SEQ as nvarchar(50)) as sohoadon, cp.DIENGIAI as kyhieu ,  cp.NGAY as ngayhoadon \n" +
							"      ,case when cp.TIENTE_FK= '100000' then tt.sotienavat else tt.SOTIENNT end as sotienavat, tt.SOTIENTT as sotientt  , '' as POID, cp.TIENTE_FK as ttId, \n" +
							"      isnull(cp.tigia,1) as tigia , isnull(cp.ngaydenhantt,'') as ngaydenhantt \n" +
							"from ERP_CHIPHIKHAC_CHITIET cpct \n" +
							"     left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n" +
							"     left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n" +
							"     left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
							"where cp.DOITUONG = '"+this.NhanvienId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and cp.LOAI= '1'  \n";

					
				// LOAIHD: 6 - DENGHITHANHTOAN
					query += " UNION ALL \n" ;
					query += " select 6 as LOAIHD, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , \n" +
							"        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
							"		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA,  mh.ngaydenhantt  \n"+
							" from ERP_MUAHANG mh \n" +
							" 	left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ and TT.LOAIHD = 6 \n"+
							" 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"+
							" 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = mh.NHANVIEN_FK \n"+
							" where   mh.NHANVIEN_FK = '" + this.NhanvienId + "' and tt.THANHTOANHD_FK = '" +this.id+ "' \n";

					// LOAD NHỮNG PHIẾU CHI (ĐỀ NGHỊ TT ĐÃ CHỐT)
						query += " UNION ALL \n" ;
						query += " select 8 as LOAIHD, tt.HOADON_FK as PK_SEQ, cast(tt.HOADON_FK as nvarchar(50)) as sohoadon,  N'Chi đề nghị thanh toán'  AS KYHIEU , \n"+
								"       (select a.NGAYGHINHAN from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as ngayhoadon , \n" +
								"       ( CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END) as SOTIENAVAT, \n"+
								"		 tt.SOTIENTT as SOTIENTT , '' as POID , tthd.TIENTE_FK as ttId,  (select a.TIGIA from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as TIGIA,  '' ngaydenhantt  \n"+
								" from  ERP_THANHTOANHOADON_HOADON tt  \n"+
								" 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  and tt.LOAIHD = 8 \n"+
								" 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tthd.NHANVIEN_FK \n"+
								" where   tthd.NHANVIEN_FK = '" + this.NhanvienId + "' and tthd.PK_SEQ = "+ this.id +" \n";
					
							
				// LOAD NHỮNG PHIẾU CHI (ĐỀ NGHỊ TẠM ỨNG ĐÃ CHỐT)
					query += " UNION ALL \n" ;
					query += " select 9 as LOAIHD, tt.HOADON_FK as PK_SEQ, cast(tt.HOADON_FK as nvarchar(50)) as sohoadon,  N'Chi đề nghị tạm ứng'  AS KYHIEU , \n"+
							"       (select a.NGAYGHINHAN from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as ngayhoadon , \n" +
							"       ( CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END) as SOTIENAVAT, \n"+
							"		 tt.SOTIENTT as SOTIENTT , '' as POID , tthd.TIENTE_FK as ttId,  (select a.TIGIA from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as TIGIA,  '' ngaydenhantt  \n"+
							" from  ERP_THANHTOANHOADON_HOADON tt  \n"+
							" 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  and tt.LOAIHD = 9 \n"+
							" 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tthd.NHANVIEN_FK \n"+
							" where   tthd.NHANVIEN_FK = '" + this.NhanvienId + "' and tthd.PK_SEQ = "+ this.id +" \n";
										
				}
				
				
				
				// LOAD NHỮNG HD CỦA KHÁCH HÀNG
				if(this.khachhangId.trim().length() > 0)
				{
					// LOAIHD: 7 - KHACHHANGTRATRUOC
				    query = "select 7 as LOAIHD, tt.PK_SEQ, tt.SOCHUNGTU as SOHOADON, N'Khách hàng trả trước' as KYHIEU, tt.NGAYCHUNGTU AS NGAYHOADON  , \n" +
				    		"       CASE WHEN tt.TIENTE_FK <> '100000' THEN ttct.SOTIENNT ELSE ttct.SOTIENAVAT END AS SOTIENAVAT , \n" +
				    		"       ttct.SOTIENTT, '' AS POID, tt.TIENTE_FK AS TTID, ISNULL(tt.TIGIA,1) as TIGIA, '' as NGAYDENHANTT  \n" +
				    		"from ERP_THUTIEN tt  inner join ERP_THANHTOANHOADON_HOADON ttct on ttct.HOADON_FK = tt.PK_SEQ AND ttct.LOAIHD = 7 \n"+
				    		"where tt.KHACHHANG_FK = '"+ this.khachhangId +"' and ttct.THANHTOANHD_FK = "+ this.id +" \n";							        				
				}
				
				if(this.bophanId.trim().length() > 0)
				{					
					// LOAIHD: 6 - DENGHITHANHTOAN (NV)

						query = " select  '0' as DOITUONG ,nv.PK_SEQ as DOITUONG_FK, nv.MA as MADOITUONG , 6 as LOAIHD, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , \n" +
								"        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
								"		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA,  mh.ngaydenhantt  \n"+
								" from ERP_MUAHANG mh \n" +
								" 	inner join ERP_THANHTOANHOADON_HOADONBOPHAN tt on tt.HOADON_FK = mh.PK_SEQ and TT.LOAIHD = 6 \n"+
								" 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"+
								" 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = mh.NHANVIEN_FK \n"+
								" where   tt.NHANVIEN_FK is not null and tt.THANHTOANHD_FK = '" +this.id+ "' \n";					    												
				}
			
				
				System.out.println("Query khoi tao hoa don 111112434643: " + query);
				
				ResultSet rsHoadon = db.get(query);
				List<IHoadon> hdList = new ArrayList<IHoadon>();
				if(rsHoadon != null)
				{
					try 
					{
						IHoadon hd = null;
						while(rsHoadon.next())
						{
							String doituong = "";
							String doituongId = "";
							String madoituong ="";
							
							String id = rsHoadon.getString("pk_seq");
							String kyhieu = rsHoadon.getString("kyhieu");
							String sohoadon = rsHoadon.getString("sohoadon");
							String ngayhd = rsHoadon.getString("ngayhoadon");
							String ngaydenhantt = rsHoadon.getString("ngaydenhantt");
							String tienteId = rsHoadon.getString("TTID");
							String avat = "";
							String loaihd = rsHoadon.getString("LOAIHD");
							
							if(tienteId.equals("100000")){
								avat = ("" + rsHoadon.getDouble("sotienAVAT")).replaceAll(",", "");
							}else{
								avat = ("" + rsHoadon.getDouble("sotienAVAT")*rsHoadon.getDouble("TIGIA")).replaceAll(",", "");
							}
							
							String sotienNT = ("" + rsHoadon.getDouble("sotienAVAT")).replaceAll(",", "");							
							String dathanhtoan = ("" + rsHoadon.getDouble("SOTIENTT")).replaceAll(",", "");
							String tigia = rsHoadon.getString("TIGIA").replaceAll(",", "");
							
							if(this.bophanId.trim().length() > 0)
							{
								doituong = rsHoadon.getString("DOITUONG");
								doituongId = rsHoadon.getString("DOITUONG_FK");
								madoituong = rsHoadon.getString("MADOITUONG");
							}
							
							hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, sotienNT, dathanhtoan,  tienteId);
							hd.setSoPO(rsHoadon.getString("POID"));
							hd.setTigia(tigia);
							hd.setNgaydenhanTT(ngaydenhantt);
							hd.setLoaihd1(loaihd);
							
							if(this.bophanId.trim().length() > 0)
							{
							  hd.setDoituong(doituong);
							  hd.setDoituongId(doituongId);
							  hd.setMadoituong(madoituong);
							}
							
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
	}
	
	public void initPdf() 
	{
		String query = 
			" select a.pk_seq, a.ngayghinhan, " +
			"   case when a.NCC_FK IS not null then ncc.TEN when a.NHANVIEN_FK IS not null then nv.TEN	else '' end AS nguoinhan, " +
			" 	case when a.NCC_FK IS not null then ncc.diachi else '' end AS diachi,  " +
			"   a.httt_fk, a.nganhang_fk, a.chinhanh_fk, a.sotaikhoan, a.noidungtt, a.sotientt, " +
			"	a.ghichu, a.loaithanhtoan, a.sotienHD, a.sotienHDNT, a.phinganhang, a.phinganhangNT, " +
			"   a.vat, a.vatNT, a.sotienttNT, a.chenhlechVND, trichphi, sotaikhoan_tp, nganhang_tp_fk, chinhanh_tp_fk, " +
			"   PNH.mahoadon, PNH.mauhoadon, PNH.KYHIEU, PNH.SOHOADON, PNH.NGAYHOADON, PNH.TENNCC, PNH.MST, PNH.TIENHANG, PNH.THUESUAT, " +
			"   PNH.TIENTHUE, PNH.NGANHANG_FK AS NGANHANG_PNH_FK, PNH.CHINHANH_FK AS CHINHANH_PNH_FK " +
			" from ERP_THANHTOANHOADON a " +
			" left join ERP_THANHTOANHOADON_PHINGANHANG PNH on PNH.THANHTOANHD_FK = a.PK_SEQ " +
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
					this.sotienttNT = rs.getString("sotienttNT").replaceAll(",", "");
					this.sotientt = rs.getString("sotientt").replaceAll(",", "");
					this.LoaiThanhToan = rs.getString("loaithanhtoan");
					this.sotienHD  = rs.getString("sotienHD").replaceAll(",", "");
					this.sotienHDNT = rs.getString("sotienHDNT").replaceAll(",", "");
					this.pnganhang = rs.getString("phinganhang").replaceAll(",", "");
					this.pnganhangNT = rs.getString("phinganhangNT").replaceAll(",", "");
					this.thueVAT = rs.getString("vat").replaceAll(",", "");
					this.thueVATNT = rs.getString("vatNT").replaceAll(",", "");
					this.chenhlechVND = rs.getString("chenhlechVND").replaceAll(",", "");
					this.trichphi = rs.getString("trichphi");
					this.sotaikhoan_tp = rs.getString("sotaikhoan_tp");
					this.nganhang_tpId = rs.getString("nganhang_tp_fk");
					this.chinhanh_tpId = rs.getString("chinhanh_tp_fk");
					this.mahoadon = rs.getString("mahoadon");
					this.mauhoadon = rs.getString("mauhoadon");
					this.kyhieu = rs.getString("KYHIEU");
					this.sohoadon = rs.getString("SOHOADON");
					this.ngayhoadon = rs.getString("NGAYHOADON");
					this.tenNCC = rs.getString("TENNCC");
					this.mst = rs.getString("MST");
					this.tienhang = rs.getString("TIENHANG").replaceAll(",", "");
					this.thuesuat = rs.getString("THUESUAT").replaceAll(",", "");
					this.tienthue = rs.getString("TIENTHUE").replaceAll(",", "");
					this.nhId_VAT = rs.getString("NGANHANG_PNH_FK");
					this.cnId_VAT = rs.getString("CHINHANH_PNH_FK");
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
//		Utility util = new Utility();
		
		this.sotkRs = db.get("SELECT NH_CTY.SOTAIKHOAN, NH_CTY.SOTAIKHOAN + ' - ' + NH.TEN + ' - ' + CN.TEN + ', ' + TT.MA  AS TAIKHOAN " +
				 			 "FROM ERP_NGANHANG_CONGTY NH_CTY " +
				 			 "INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK " + 
				 			 "INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK " +
				 			 "INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK " +
				 			 "WHERE NH_CTY.TRANGTHAI = 1  " );
      
		this.sotkRs_tp = db.get("SELECT NH_CTY.SOTAIKHOAN, NH_CTY.SOTAIKHOAN + ' - ' + NH.TEN + ' - ' + CN.TEN + ', ' + TT.MA  AS TAIKHOAN " +
	 			 				"FROM ERP_NGANHANG_CONGTY NH_CTY " +
	 			 				"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK " + 
	 			 				"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK " +
	 			 				"INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK " +
	 			 				"WHERE NH_CTY.TRANGTHAI = 1  AND TT.PK_SEQ = 100000 " );
    
		this.tienteRs = db.get("select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE ");
		

		this.nccRs = db.get("select pk_seq, ma + ', ' + ten as TEN from erp_nhacungcap where trangthai = '1' and duyet= '1' and CONGTY_FK = "+this.ctyId+" ");
		this.NhanvienRs = db.get("SELECT PK_SEQ ,MA+','+TEN AS TEN   FROM ERP_NHANVIEN WHERE TRANGTHAI=1 and CONGTY_FK = "+this.ctyId);
		this.nhomNCCRs = db.get("SELECT PK_SEQ, DIENGIAI AS TEN FROM NHOMNHACUNGCAPCN where TRANGTHAI = 1 and CONGTY_FK = "+this.ctyId) ;
		//this.khachhangRs = db.get("select pk_seq, ma + ', ' + ten as TEN from Erp_KhachHang where trangthai = '1' and pk_seq in " + util.quyen_npp(userId) + "");
		
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1' and pk_seq = '100001' ");
		this.nganhangRs = db.get("select pk_seq, ma + ', ' + ten as nhTen from erp_nganhang  ");
		 
				
			if(  this.htttId.length() > 0 && this.hoadonList.size() <= 0)
			{
				NumberFormat formatter = new DecimalFormat("#,###,###");
				
				String query = "";
				// LOAD NHỮNG HD CỦA NHÀ CUNG CẤP
				if(this.nccId.trim().length() > 0) 
				{
					//LOAIHD: 0 - HOADONNCC
				   if(this.id.length() > 0)
				   {
					query += " select distinct 0 as LOAIHD, b.pk_seq, isnull(b.sohoadon, '') as sohoadon,  isnull(b.kyhieu, '') as kyhieu, b.ngayhoadon, \n" +
							"         b.sotienAVAT as sotiengoc, a.sotienAVAT as sotienAVAT, SOTIENTT, \n" +
							 " ISNULL( (  \n" +
							"			Select distinct  \n" +
							"               ( Select cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()]  \n" +
							"				  From ERP_HOADONNCC_PHIEUNHAP PO1   \n" +
							"			      Where PO1.hoadonncc_fk = b.pk_seq \n" +
							"			      For XML PATH ('')) [phongbanChon_fk]  \n" +
							"			From ERP_HOADONNCC_PHIEUNHAP PO  \n" +
							"			where PO.hoadonncc_fk = b.pk_seq \n" +
							"            ), '' )   as POID, c.TIENTE_FK AS TTID, c.TIGIA, b.NGAYDENHANTT \n" +
							" from ERP_THANHTOANHOADONNCC_HOADON a \n" +
							" inner join ERP_HOADONNCC b on a.hoadon_fk = b.pk_seq  and a.loaihd = 0  \n" +
							" inner join ERP_PARK c on b.park_fk = c.pk_seq \n" +
							" left join	\n" +
							" ( select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
							"   from 	 ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
							"   where  TTHD.LOAIHD = 0 AND TT.trangthai !=2 and TT.CONGTY_FK = "+this.ctyId+" \n" +  //sua trang thai !=2 thanh =1							
							"   group by hoadon_fk) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
							" left join	\n" +
							" ( select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
							"   from 	 ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
							"   where  TTHD.LOAIHD = 0 AND TT.trangthai = 0 and TTHD.thanhtoanhd_fk != '" + this.id + "' \n" +  //sua trang thai !=2 thanh =1
							"          and TTHD.hoadon_fk in ( select hoadon_fk \n" +
					        "									from ERP_THANHTOANHOADONNCC_HOADON \n" +
							"									where thanhtoanhd_fk = '" + this.id + "' and loaihd = 0 ) \n" +
							"   group by hoadon_fk) thanh_toanNCC on a.hoadon_fk = thanh_toanNCC.hoadon_fk \n" +
							" where a.thanhtoanhd_fk = '" + this.id + "' and c.TIENTE_FK = " + this.tienteId + " and b.CONGTY_FK = "+this.ctyId+" \n" +
							" UNION ALL \n";
				}
				   
				   query += 
						"(select distinct 0 as LOAIHD, hoadon.pk_seq, isnull(hoadon.sohoadon, '') as sohoadon, isnull(hoadon.kyhieu, '') as kyhieu, hoadon.ngayhoadon, \n" +
						"         hoadon.sotienAVAT as sotiengoc, hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') - isnull(dathanhtoanNCC.DATHANHTOAN, '0')  as sotienAVAT, 0 as sotientt ,\n " +
						" ISNULL( (  " +
						"		Select distinct ( Select cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()] \n" +
						"						  From ERP_HOADONNCC_PHIEUNHAP PO1   \n" +
						"	                      Where PO1.hoadonncc_fk = hoadon.pk_seq \n" +
						"                         For XML PATH ('')) [phongbanChon_fk]  \n" +
						"       From ERP_HOADONNCC_PHIEUNHAP PO  \n" +
						"       where PO.hoadonncc_fk = hoadon.pk_seq \n" +
						"          ), '' )   as POID, hoadon.ttId, hoadon.tigia,  hoadon.ngaydenhantt \n" +
						" from ( \n" +
						"       select a.pk_seq, a.kyhieu, a.sohoadon, a.ngayhoadon, a.sotienAVAT, b.TIENTE_FK as ttId, b.TIGIA, a.SOTIENVIET, isnull(a.ngaydenhantt,'') as  ngaydenhantt \n" +
						"       from ERP_HOADONNCC a inner join ERP_PARK b on a.park_fk = b.pk_seq \n" +
						"       left join ERP_HOADONNCC_PHIEUNHAP hd_pn on a.pk_seq = hd_pn.hoadonncc_fk \n"+
						"		left join ERP_YEUCAUKIEMDINH yckd on hd_pn.phieunhan_fk = yckd.NHANHANG_FK \n"+
						"       where b.ncc_fk = '" + this.nccId + "' and b.trangthai = '2' and a.trangthai = '0' and a.CONGTY_FK = "+this.ctyId+" \n"+
						"			  and ( yckd.PK_SEQ is null OR ( yckd.PK_SEQ is not null and ISNULL(yckd.THIEUHOSO,0) <> 1 ) ) \n";
						if(this.id.length() > 0)
						{
							query += "and a.pk_seq not in (select hoadon_fk from ERP_THANHTOANHOADONNCC_HOADON where thanhtoanhd_fk = '" + this.id + "') \n";
						}
						query += " ) hoadon \n" +
							"      left join " +
							"   ( \n" +
							"     select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n" +
							"     from ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
							"     where TT.NCC_FK is not null AND TT.CONGTY_FK = "+this.ctyId+" AND TTHD.LOAIHD = 0 AND TT.TRANGTHAI !=2 and TTHD.HOADON_FK in (select pk_seq from ERP_HOADONNCC where trangthai = 0 and CONGTY = "+this.ctyId+" )  \n" + //sua trang thai !	=2 thanh =1
							"     group by HOADON_FK \n" +
							"    ) dathanhtoan  on hoadon.pk_seq = dathanhtoan.hoadon_fk \n" +
							"      left join " +
							"   ( \n" +
							"     select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n" +
							"     from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
							"     where TT.CONGTY_FK = "+this.ctyId+" AND  TTHD.LOAIHD = 0 AND TT.TRANGTHAI in (0) and TTHD.HOADON_FK in (select pk_seq from ERP_HOADONNCC where trangthai = 0 and CONGTY_FK ="+this.ctyId+" )  \n" + //sua trang thai !=2 thanh =1
							"     group by HOADON_FK \n" +
							"    ) dathanhtoanNCC  on hoadon.pk_seq = dathanhtoanNCC.hoadon_fk \n" +
							"where hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') - isnull(dathanhtoanNCC.DATHANHTOAN, '0')  > 0 and hoadon.ttId = " + this.tienteId + " ) \n" ;
			
				// LOAIHD: 1 - TAMUNG(NCC)		 				
				if(this.id.length() >0)
				{
					query += "UNION ALL \n"; 
					query+="SELECT distinct 1 as LOAIHD, TU.PK_SEQ , CAST(TU.PK_SEQ as nvarchar(50)) as sohoadon ,N'TẠM ỨNG'  AS KYHIEU ,TU.NGAYTAMUNG AS ngayhoadon, SOTIENTAMUNG as sotiengoc, SOTIENTAMUNG \n" +
					"              -(SELECT ISNULL(SUM(b.SOTIENTT),0) \n" +
					"                FROM ERP_THANHTOANHOADONNCC_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n" +
					"                WHERE A.CONGTY_FK= "+this.ctyId+"  AND A.NCC_FK = "+ (this.nccId ==""?"0":this.nccId) +" AND B.LOAIHD = 1 AND A.TRANGTHAI in (0)  and a.pk_seq <> "+this.id+"  AND B.HOADON_FK=TU.PK_SEQ) \n" +
					"              -(SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n" +
					"     	         FROM ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
					"     	         WHERE TT.CONGTY_FK = "+this.ctyId+"  AND TT.NCC_FK = "+ (this.nccId ==""?"0":this.nccId) +" and TTHD.LOAIHD = 1 AND TT.TRANGTHAI !=2   \n" + 
					"    	         )  AS sotienAVAT , \n" +
					"                0 AS SOTIENTT, '' as POID, TU.TIENTE_FK as TTID, "+this.tigia.replace(",", "")+" AS TIGIA, \n" +
					"                isnull(TU.ngaydenhantt,'') as ngaydenhantt  \n" +
					"      FROM 	ERP_TAMUNG TU \n" +
					"      WHERE 	ISNULL(TU.HOANTAT,'0')='0' and TU.TIENTE_FK = (select tiente_fk from ERP_THANHTOANHOADON where pk_seq="+this.id+" ) and TU.CONGTY_FK = "+this.ctyId+" and TU.NPP_FK = "+this.nppdangnhap+" \n" +
					"            	and TU.pk_seq not in  \n" +
					"           	(select HOADON_FK \n" +
					"            	from ERP_THANHTOANHOADONNCC a inner join ERP_THANHTOANHOADONNCC_HOADON b on a.pk_seq=b.thanhtoanhd_fk \n" +
					"           	where b.LOAIHD = 1 AND  b.thanhtoanhd_fk="+this.id+"  )  \n" +
					"           	and  (SOTIENTAMUNG \n" + 
					"              		-( SELECT ISNULL(SUM(b.SOTIENTT),0) \n" +
					"                 	   FROM ERP_THANHTOANHOADONNCC_HOADON B INNER JOIN ERP_THANHTOANHOADONNCC A ON A.PK_SEQ=B.THANHTOANHD_FK  \n" +
					"                 	   WHERE A.CONGTY_FK = "+this.ctyId+" AND  A.NCC_FK = "+ (this.nccId ==""?"0":this.nccId) +" AND b.LOAIHD = 1 AND A.TRANGTHAI = 0 and a.pk_seq <> "+this.id+"  AND b.HOADON_FK = TU.PK_SEQ) \n"+
					"             		- (SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n" +
					"     	         	   FROM ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
					"     	         	   WHERE TT.CONGTY_FK = "+this.ctyId+" AND TT.NCC_FK = "+ (this.nccId ==""?"0":this.nccId) +" AND TTHD.LOAIHD = 1 AND TT.TRANGTHAI !=2   \n" + 
					"    	        )  \n" +
					"              >0 ) \n" +
					"           and   tu.NCC_FK="+ (this.nccId ==""?"0":this.nccId)  ;

					
					query+= 
					" UNION ALL \n" +
					" SELECT distinct 1 as LOAIHD, HOADON_FK AS PK_SEQ, CAST(HOADON_FK as nvarchar(50)) AS SOHOADON , N'TẠM ỨNG ' as   KYHIEU ,a.ngaytamung as ngayhoadon, SOTIENTAMUNG as sotiengoc \n" +
					"        ,a.sotientamung -( SELECT ISNULL(SUM(CT.SOTIENTT),0) \n" +
					"                           FROM ERP_THANHTOANHOADONNCC_HOADON CT INNER JOIN ERP_THANHTOANHOADONNCC A ON A.PK_SEQ=CT.THANHTOANHD_FK \n" + 
					"                           WHERE A.CONGTY_FK = "+this.ctyId+" AND  A.NCC_FK = "+ (this.nccId ==""?"0":this.nccId) +" AND ct.LOAIHD = 1 AND A.TRANGTHAI = 0  AND CT.HOADON_FK = B.HOADON_FK and a.pk_seq <>"+this.id+" )  \n" +
					"                        -(SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n" +
					"     	      			   FROM ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
					"     	     			   WHERE TT.CONGTY_FK = "+this.ctyId+"  AND TT.NCC_FK = "+ (this.nccId ==""?"0":this.nccId) +" AND TTHD.LOAIHD = 1   \n" + 
					"    	     			   )  AS sotienAVAT , \n" +
					"         sotientt, '' as POID, a.TIENTE_FK as TTID ,"+this.tigia.replace(",", "")+" AS TIGIA , isnull(a.ngaydenhantt,'') as ngaydenhantt \n" +
					" FROM ERP_THANHTOANHOADONNCC_HOADON b inner join ERP_TAMUNG a on a.pk_seq=b.HOADON_FK  AND b.LOAIHD = 1 \n" +
					"      and ( a.sotientamung -( SELECT ISNULL(SUM(CT.SOTIENTT),0) \n" +
					"                              FROM ERP_THANHTOANHOADONNCC_HOADON CT INNER JOIN ERP_THANHTOANHOADONNCC A ON A.PK_SEQ=CT.THANHTOANHD_FK \n" +
					"                              WHERE A.CONGTY_FK  = "+this.ctyId+"  AND  A.NCC_FK = "+ (this.nccId ==""?"0":this.nccId) +" AND ct.LOAIHD = 1 AND A.TRANGTHAI = 0  AND CT.HOADON_FK=B.HOADON_FK and a.pk_seq <>"+this.id+" ) \n"+
					"     						-(SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n" +
					"     						  FROM ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
					"     						  WHERE TT.CONGTY_FK = "+this.ctyId+" AND TT.NCC_FK = "+ (this.nccId ==""?"0":this.nccId) +" AND TTHD.LOAIHD = 1 AND TT.TRANGTHAI !=2   \n" + 
					"    						  ) \n"+
					"                            > 0) \n" +
					" WHERE thanhtoanhd_fk="+this.id+" and b.LOAIHD = 1 and a.CONGTY_FK = "+this.ctyId+" and a.NPP_FK = "+this.nppdangnhap+" \n" ;
			   }
				
				query +=" UNION ALL \n"; 
				query+=
					" SELECT distinct 1 as LOAIHD, TU.PK_SEQ , CAST(TU.PK_SEQ as nvarchar(50)) as sohoadon ,N'TẠM ỨNG'  AS KYHIEU ,TU.NGAYTAMUNG AS ngayhoadon, SOTIENTAMUNG as sotiengoc, SOTIENTAMUNG   \n" +
					"           -( SELECT ISNULL(SUM(b.SOTIENTT),0)   \n" +
					"              FROM ERP_THANHTOANHOADONNCC_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n" +
					"              WHERE  A.CONGTY_FK = "+this.ctyId+" AND  A.NCC_FK = "+ (this.nccId ==""?"0":this.nccId) +" AND b.LOAIHD = 1 AND A.TRANGTHAI = 0  AND B.HOADON_FK=TU.PK_SEQ ) "+
					"           -(SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n" +
					"     	      FROM ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
					"     	      WHERE  A.CONGTY_FK = "+this.ctyId+" AND  TT.NCC_FK = "+ (this.nccId ==""?"0":this.nccId) +" AND TTHD.LOAIHD = 1 AND TT.TRANGTHAI !=2   \n" + 
					"    	     )  AS sotienAVAT , \n" +
					"            0 AS SOTIENTT, '' as POID, TU.TIENTE_FK as TTID,"+this.tigia.replace(",", "")+" AS TIGIA, isnull(TU.ngaydenhantt,'') as ngaydenhantt  \n" +
					" FROM ERP_TAMUNG TU \n" +
					" WHERE TU.CONGTY_FK = "+this.ctyId+"  AND TU.TIENTE_FK = '"+this.tienteId+"'  and ISNULL(TU.HOANTAT,'0')='0'  and  (SOTIENTAMUNG   \n" + 
					"     -( SELECT ISNULL(SUM(b.SOTIENTT),0) \n" +
					"        FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n" +
					"        WHERE A.CONGTY_FK = "+this.ctyId+" AND A.NCC_FK = "+ (this.nccId ==""?"0":this.nccId) +" AND b.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND B.HOADON_FK=TU.PK_SEQ) \n"+
					"     -(SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n" +
					"     	FROM ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
					"     	WHERE A.CONGTY_FK = "+this.ctyId+"  AND TT.NCC_FK = "+ (this.nccId ==""?"0":this.nccId) +" AND TTHD.LOAIHD = 1 AND TT.TRANGTHAI = 0  \n" + 
					"    	) \n"+
					"   >0 ) and TU.TRANGTHAI = 1 \n" +
				    " and   NCC_FK="+ (this.nccId ==""?"0":this.nccId) ;
				
				// LOAIHD: 2 - CHIPHINOIBO						 
				if(this.id.length() > 0)
				{
					query += " UNION ALL \n"; 
					query += " select distinct 2 as LOAIHD, mh.PK_SEQ, cast(mh.PK_SEQ as nvarchar(50)) as sohoadon,  N'Chi phí nội bộ'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , \n" +
							"        mhsp.SOTIENPO as sotiengoc, CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
							"		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA , mh.ngaydenhantt  \n"+
							" from ERP_MUAHANG mh \n" +
							"       inner join \n" +
							"       (select a.PK_SEQ, SUM(a.TONGTIENAVAT)as SOTIENPO \n" +
							"		from  ERP_MUAHANG a \n" +
							"			  inner join ERP_NHACUNGCAP c on c.PK_SEQ = a.NHACUNGCAP_FK  \n" +
							"		where c.NOIBO = 1 and a.TIENTE_FK= "+ this.tienteId +"  and a.TRANGTHAI= 2 AND a.CONGTY_FK = "+this.ctyId+"  \n" +
							"       group by a.PK_SEQ )as mhsp on mh.PK_SEQ= mhsp.PK_SEQ  \n"+
							" 	left join ERP_THANHTOANHOADONNCC_HOADON tt on tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 2  \n"+
							" 	left join ERP_THANHTOANHOADONNCC tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ \n"+
							" 	left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
							" where ncc.NOIBO = 1 and  mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" +this.id+ "' \n";																				
						
				  }
				
				query += " UNION ALL \n"; 							
				query += 
						" SELECT distinct 2 as LOAIHD,  mh.PK_SEQ, cast(mh.PK_SEQ as nvarchar(50)) as sohoadon,  N'Chi phí nội bộ'  AS KYHIEU , mh.NGAYMUA as ngayhoadon \n"+
						" 		,mhsp.SOTIENPO as sotiengoc , mhsp.SOTIENPO - isnull(t.SOTIENTT,0) - isnull(dathanhtoanNCC.DATHANHTOAN,0) as SOTIENAVAT ,0 as sotientt, '' as POID  \n"+
						" 		,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA,  mh.ngaydenhantt  \n"+
						" FROM  ERP_MUAHANG mh \n" +
						"       inner join \n" +
						"       (select a.PK_SEQ, SUM(a.TONGTIENAVAT)as SOTIENPO \n" +
						"		from  ERP_MUAHANG a \n" +
						"			  inner join ERP_NHACUNGCAP c on c.PK_SEQ = a.NHACUNGCAP_FK  \n" +
						"		where c.NOIBO = 1 and a.TIENTE_FK= "+ this.tienteId +"  and a.TRANGTHAI= 2 and a.CONGTY_FK = "+this.ctyId+" \n" +
						"       group by a.PK_SEQ )as mhsp on mh.PK_SEQ= mhsp.PK_SEQ  \n"+
						" 		left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ  AND TT.LOAIHD = 2 \n"+
						" 		left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk  \n"+
						"      	left join " +
						"   	( \n" +
						"       select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n" +
						"       from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
						"       where TTHD.LOAIHD = 2 AND TT.TRANGTHAI = 0 and TT.CONGTY_FK = "+this.ctyId+"  \n" + //sua trang thai !=2 thanh =1
						"       group by HOADON_FK \n" +
						"       ) dathanhtoanNCC  on mh.PK_SEQ = dathanhtoanNCC.hoadon_fk \n" +
						" 		left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
						" where MH.CONGTY_FK = "+this.ctyId+" and mh.NPP_FK = "+this.nppdangnhap+" and ncc.NOIBO = 1 and mh.NHACUNGCAP_FK = '" + this.nccId + "' \n" +
						" 		and ( tt.CONLAI is null or (tt.CONLAI > 0 \n"+
						"			and mh.TIENTE_FK = "+ this.tienteId +" \n"+
						" 			and tt.HOADON_FK not in \n"+
						"				(select distinct tt.HOADON_FK \n"+
						"				from ERP_MUAHANG mh \n"+
						"				left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ  AND TT.LOAIHD = 2 \n"+
						"				left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
						"				where mh.CONGTY_FK = "+this.ctyId+" and mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.CONLAI = 0 and mh.TIENTE_FK = "+ this.tienteId +") \n"+
						"  					  and tt.THANHTOANHD_FK in \n"+
						"				(select MAX(tt.THANHTOANHD_FK) \n"+
						"				from Erp_MuaHang mh  \n"+
						"				left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ   AND TT.LOAIHD = 2 \n"+
						" 				left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n"+
						"				left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
						"				where ncc.NOIBO = 1 and mh.TRANGTHAI =2 and mh.TIENTE_FK = "+ this.tienteId +"  and  mh.NHACUNGCAP_FK =  '" + this.nccId + "' and t.TRANGTHAI<>2 AND mh.CONGTY_FK = "+this.ctyId+" \n"+ //trang thai mh=2 da hoan tat
						"	 			group by tt.HOADON_FK ) ) ) \n"+
						" 		and mh.TRANGTHAI = 2 \n";
						if(this.id.length() > 0)
						{
							query+= " and mh.PK_SEQ not in (select HOADON_FK from  ERP_THANHTOANHOADONNCC_HOADON where THANHTOANHD_FK = "+ this.id +") \n" ;
						}
				query+=	"       and  mhsp.SOTIENPO - isnull(t.SOTIENTT,0) - isnull(dathanhtoanNCC.DATHANHTOAN,0) > 0 \n";
				
				// LOAIHD: 3 - CHIPHINHANHANG
				if(this.id.length() > 0)
				{
					query += " UNION ALL \n"; 
					query += "select distinct 3 as LOAIHD, cpct.pk_seq , cpct.SOCHUNGTU as sohoadon, cpct.KYHIEUCHUNGTU as kyhieu , cpct.NGAYCHUNGTU as ngayhoadon, (cpct.TIENHANG + (cpct.TIENHANG*(cpct.THUESUAT/100))) as sotiengoc \n" +
							" 		,tt.sotienavat as sotienavat, tt.SOTIENTT as sotientt  , cast(mh.PK_SEQ as nvarchar(50)) as POID, isnull(cp.TIENTE_FK,'100000') as ttId,  isnull(cp.TIGIA,1) as TIGIA, cp.NGAYDENHANTT \n" +
							" from 	ERP_CHIPHINHAPKHAU_CHITIET cpct \n" +
							" 		left join ERP_CHIPHINHAPKHAU cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n" +
							" 		left join ERP_NHANHANG nh on nh.PK_SEQ = cp.nhanhang_fk \n" +
							" 		left join ERP_MUAHANG mh on mh.PK_SEQ = nh.MUAHANG_FK  \n" +
							" 		left join ERP_THANHTOANHOADONNCC_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ  AND TT.LOAIHD = 3 \n" +
							" 		left join ERP_THANHTOANHOADONNCC t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
							" where cp.NCCID_CN = '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and cp.CONGTY_FK = "+this.ctyId+"  \n";
															

				}
				query += " UNION ALL \n"; 
				query += "select distinct 3 as LOAIHD, cpct.pk_seq ,cpct.SOCHUNGTU as sohoadon,  cpct.KYHIEUCHUNGTU as kyhieu , cpct.NGAYCHUNGTU as ngayhoadon, (cpct.TIENHANG + (cpct.TIENHANG*(cpct.THUESUAT/100))) as sotiengoc  \n" +
						",case when (tt.SOTIENAVAT is null OR t.TRANGTHAI=2) then (cpct.TIENHANG + (cpct.TIENHANG*(cpct.THUESUAT/100)) - isnull(dathanhtoanNCC.DATHANHTOAN,0) ) else tt.CONLAI end as sotienavat \n" +
						" ,'0' as sotientt, cast(mh.PK_SEQ as nvarchar(50)) as POID, isnull(cp.TIENTE_FK,'100000') as ttId,  isnull(cp.TIGIA,1) as TIGIA , cp.NGAYDENHANTT  \n" +
						"from ERP_CHIPHINHAPKHAU_CHITIET cpct \n" +
						"left join ERP_CHIPHINHAPKHAU cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n" +
						"left join ERP_NHANHANG nh on nh.PK_SEQ = cp.nhanhang_fk \n" +
						"left join ERP_MUAHANG mh on mh.PK_SEQ = nh.MUAHANG_FK  \n" +
						"left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ  AND TT.LOAIHD = 3 \n" +
						"left join " +
						"   	( \n" +
						"       select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n" +
						"       from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
						"       where TTHD.LOAIHD = 3 AND TT.TRANGTHAI = 0 AND TT.CONGTY_FK  = "+this.ctyId+" \n" + 
						"       group by HOADON_FK \n" +
						"       ) dathanhtoanNCC  on cpct.PK_SEQ = dathanhtoanNCC.hoadon_fk \n" +
						"left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
						"where cp.CONGTY_FK = "+this.ctyId+" AND cp.NPP_FK = "+this.nppdangnhap+" AND isnull(cp.TIENTE_FK,'100000') = "+ this.tienteId +" and cp.NCCID_CN = '"+this.nccId+"' and ( tt.CONLAI is null or (tt.CONLAI > 0  \n" +
						"					 and tt.HOADON_FK not in  \n" +
						"						(select distinct tt.HOADON_FK  \n" +
						"						from ERP_CHIPHINHAPKHAU_CHITIET cpct  \n" +
						"							left join ERP_CHIPHINHAPKHAU cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n" +
						"							left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ  AND TT.LOAIHD = 3 \n" +
						"						where cp.CONGTY_FK = "+this.ctyId+"  cp.NCCID_CN = '"+this.nccId+"' and tt.CONLAI = 0 and cp.TIENTE_FK= "+ this.tienteId +") \n" +
						"					  and tt.THANHTOANHD_FK in \n" +
						"						(select MAX(tt.THANHTOANHD_FK)  \n" +
						"						from ERP_CHIPHINHAPKHAU_CHITIET cpct    \n" +
						"							left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ AND TT.LOAIHD = 3 \n" +
						"					 		left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk  \n" +
						"							left join ERP_CHIPHINHAPKHAU cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n" +
						"						 where cp.trangthai = 1 and cp.TIENTE_FK= "+ this.tienteId +"  and  cp.NCCID_CN =  '"+this.nccId+"' and t.TRANGTHAI<>2 and t.CONGTY_FK = "+this.ctyId+"  \n" +
						"						 group by tt.HOADON_FK ) ) ) \n" +
						"					 and cp.trangthai = 1  \n" ;
						if(this.id.length() >0)
						{
							query+= " and cpct.pk_seq not in (select HOADON_FK from ERP_THANHTOANHOADONNCC_HOADON where THANHTOANHD_FK = "+ this.id +" ) \n";
						}
						
						if(this.tienteId.equals("100000"))
						   {
							// LOAIHD: 4 - THUENHAPKHAU						
							 if(this.id.length() > 0)
							 {
								query += "UNION ALL \n"; 
								query +="select distinct 4 as LOAIHD, tnk.pk_seq , tnk.SOCHUNGTU as sohoadon, N'Thuế nhập khẩu' as kyhieu , tnk.NGAYCHUNGTU as ngayhoadon, tnk.THUENK as sotiengoc \n" +
									" 		,tt.sotienavat, tt.SOTIENTT as sotientt  ,'' as POID  \n" +
									" 		,'100000' as ttId, '1' as TIGIA, tnk.ngaydenhantt \n" +
									"from ERP_THUENHAPKHAU tnk \n" +
									"		left join ERP_THANHTOANHOADONNCC_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND TT.LOAIHD = 4 \n" +
									"		left join ERP_THANHTOANHOADONNCC t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
									" where tnk.NCC_FK= '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and tt.LOAITHUE = N'Thuế nhập khẩu' AND tnk.CONGTY_FK = "+this.ctyId+"  \n" +
									
									" union all \n" +
									
									"select distinct  4 as LOAIHD, tnk.pk_seq , tnk.SOCHUNGTU as sohoadon, N'VAT nhập khẩu' as kyhieu , tnk.NGAYCHUNGTU as ngayhoadon, tnk.VAT as sotiengoc \n" +
									" 		,tt.sotienavat as sotienavat, tt.SOTIENTT as sotientt  , '' as POID \n" +
									" 		,'100000' as ttId, '1' as TIGIA,  tnk.ngaydenhantt \n" +
									"from ERP_THUENHAPKHAU tnk \n" +
									"		left join ERP_THANHTOANHOADONNCC_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND TT.LOAIHD = 4 \n" +
									"		left join ERP_THANHTOANHOADONNCC t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
									" where tnk.NCC_FK= '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and tt.LOAITHUE = N'VAT nhập khẩu' and tnk.CONGTY_FK = "+this.ctyId+"  \n";
							
							 				
							}
							query += " UNION ALL \n"; 
							query +=" select distinct  4 as LOAIHD, tnk.pk_seq, tnk.SOCHUNGTU as sohoadon, N'Thuế nhập khẩu' as kyhieu ,  tnk.NGAYCHUNGTU as ngayhoadon, tnk.THUENK as sotiengoc \n" +
									"		,tnk.THUENK - isnull(tt.sotientt,0)- isnull(dathanhtoanNCC.DATHANHTOAN,0) as sotienavat, '0' as sotientt, '' as POID \n" +
									" 		,'100000' as ttId, '1' as TIGIA, tnk.ngaydenhantt \n" +
									" from ERP_THUENHAPKHAU tnk \n" +
									"		left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ and tt.LOAITHUE =  N'Thuế nhập khẩu' AND TT.LOAIHD = 4 \n" +
									"		left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
									"       left join " +
									"   	( \n" +
									"       select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n" +
									"       from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
									"       where TTHD.LOAIHD = 4 AND tthd.LOAITHUE =  N'Thuế nhập khẩu' AND TT.TRANGTHAI = 0 and TT.CONGTY_FK = "+this.ctyId+"  \n" + 
									"       group by HOADON_FK \n" +
									"       ) dathanhtoanNCC  on tnk.PK_SEQ  = dathanhtoanNCC.hoadon_fk \n" +
									" where tnk.CONGTY_FK = "+this.ctyId+"  AND tnk.NCC_FK = '"+this.nccId+"' and ( tt.CONLAI is null or (tt.CONLAI > 0 \n" +
									"					 and tt.HOADON_FK not in \n" +
									"						(select distinct tt.HOADON_FK \n" +
									"						from ERP_THUENHAPKHAU tnk \n" +
									"						left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND TT.LOAIHD = 4 \n" +
									"						where tnk.NCC_FK = '"+this.nccId+"' and tt.CONLAI = 0 and tnk.CONGTY_FK = "+this.ctyId+" ) \n" +
									"					  and tt.THANHTOANHD_FK in\n" +
									"						(select MAX(tt.THANHTOANHD_FK) \n" +
									"						from ERP_THUENHAPKHAU tnk  \n" +
									"						left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND TT.LOAIHD = 4 \n" +
									"						left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n" +
									"						 where tnk.trangthai = 2    and tnk.NCC_FK = '"+this.nccId+"' and t.TRANGTHAI<>2 and t.CONGTY_FK = "+this.ctyId+"	\n" +
									"						 group by tt.HOADON_FK ) ) )\n" +
									"					 and tnk.trangthai in (1,2)  and tnk.THUENK > 0 \n" ;
									if(this.id.length() > 0)
									{
										query+= " and tnk.PK_SEQ not in (select HOADON_FK from ERP_THANHTOANHOADONNCC_HOADON where THANHTOANHD_FK= "+ this.id +" AND LOAIHD = 4 and LOAITHUE= N'Thuế nhập khẩu') \n" ;
									}
				
							query+=	" UNION ALL \n" +
									"select distinct 4 AS LOAIHD, tnk.pk_seq ,tnk.SOCHUNGTU as sohoadon, N'VAT nhập khẩu' as kyhieu ,  tnk.NGAYCHUNGTU as ngayhoadon, tnk.VAT as sotiengoc \n" +
									"		,tnk.VAT - isnull(tt.sotientt,0)- isnull(dathanhtoanNCC.DATHANHTOAN,0) as sotienavat, '0' as sotientt, '' as POID \n" +
									" 		,'100000' as ttId, '1' as TIGIA, tnk.ngaydenhantt \n" +
									"from ERP_THUENHAPKHAU tnk \n" +
									"left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ and tt.LOAITHUE =  N'VAT nhập khẩu' AND TT.LOAIHD = 4 \n" +
									"left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
									"       left join " +
									"   	( \n" +
									"       select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n" +
									"       from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
									"       where TTHD.LOAIHD = 4 AND tthd.LOAITHUE =  N'VAT nhập khẩu' AND TT.TRANGTHAI in (0) and TT.CONGTY_FK = "+this.ctyId+" \n" + 
									"       group by HOADON_FK \n" +
									"       ) dathanhtoanNCC  on tnk.PK_SEQ  = dathanhtoanNCC.hoadon_fk \n" +
									"where tnk.NCC_FK = '"+this.nccId+"' and ( tt.CONLAI is null or (tt.CONLAI > 0 \n" +
									"					 and tt.HOADON_FK not in \n" +
									"						(select distinct tt.HOADON_FK \n" +
									"						from ERP_THUENHAPKHAU tnk \n" +
									"						left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND TT.LOAIHD = 4 \n" +
									"						where tnk.NCC_FK = '"+this.nccId+"' and tt.CONLAI = 0 and tnk.CONGTY_FK = "+this.ctyId+" ) \n" +
									"					  and tt.THANHTOANHD_FK in \n" +
									"						(select MAX(tt.THANHTOANHD_FK) \n" +
									"						from ERP_THUENHAPKHAU tnk  \n" +
									"						left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND LOAIHD = 4 \n" +
									"						left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n" +
									"						 where tnk.trangthai = 2    and tnk.NCC_FK = '"+this.nccId+"' and t.TRANGTHAI<>2 and tnk.CONGTY_FK = "+this.ctyId+" \n" +
									"						 group by tt.HOADON_FK ) ) )\n" +
									"					 and tnk.trangthai = 2  and tnk.VAT > 0 \n " ;
									if(this.id.length() > 0)
									{
									  query+= " and tnk.PK_SEQ not in (select HOADON_FK from ERP_THANHTOANHOADONNCC_HOADON where THANHTOANHD_FK= "+ this.id +" AND LOAIHD = 4  and LOAITHUE= N'VAT nhập khẩu' ) \n" ;
									}
							
						   }
						
						// LOAIHD: 5 - CHIPHIKHAC
						
						if(this.id.length() > 0)
						{
							 query +=" UNION ALL \n"; 
							 query +="select distinct 5 as LOAIHD, cp.pk_seq , cast(cp.PK_SEQ as nvarchar(50)) as sohoadon, cp.DIENGIAI as kyhieu ,  cp.NGAY as ngayhoadon, (cpct.TONGTIENCHUATHUE + cpct.THUE ) as sotiengoc \n" +
									"      ,case when cp.TIENTE_FK= '100000' then tt.sotienavat else tt.SOTIENNT end as sotienavat, tt.SOTIENTT as sotientt  , '' as POID, cp.TIENTE_FK as ttId, \n" +
									"      isnull(cp.tigia,1) as tigia , isnull(cp.ngaydenhantt,'') as ngaydenhantt \n" +
									"from ERP_CHIPHIKHAC_CHITIET cpct \n" +
									"     left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n" +
									"     left join ERP_THANHTOANHOADONNCC_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n" +
									"     left join ERP_THANHTOANHOADONNCC t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
									"where cp.DOITUONG = '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and cp.LOAI= '0' and cp.CONGTY_FK = "+this.ctyId+" \n";
							 
						}
						query += " UNION ALL \n"; 		
						query += "select distinct 5 as LOAIHD, cp.pk_seq , cast(cp.PK_SEQ as nvarchar(50)) as sohoadon, cp.DIENGIAI as kyhieu,  cp.NGAY as ngayhoadon, (cpct.TONGTIENCHUATHUE + cpct.THUE ) as sotiengoc \n" +
								"	   ,case when (tt.SOTIENAVAT is null OR t.TRANGTHAI=2) then (cpct.TONGTIENCHUATHUE + cpct.THUE - isnull(xoatamung.dathanhtoan,0) - isnull(dathanhtoanNCC.DATHANHTOAN,0) ) else tt.CONLAI end as sotienavat \n" +
								"	   ,'0' as sotientt, '' as POID , cp.TIENTE_FK as ttId, isnull(cp.TIGIA,1) as TIGIA, \n" +
								"      isnull(cp.ngaydenhantt,'') as ngaydenhantt  \n" +
								"from ERP_CHIPHIKHAC_CHITIET cpct \n" +
								"     left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK\n" +
								"     left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n" +
								"     left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
								"     left join (select a.cttt_fk,SUM(a.tienthanhtoan) as dathanhtoan \n" +
								"	         from ERP_XOAKHTRATRUOC_CTTT a inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk=b.PK_SEQ \n" +
								"	         where b.TRANGTHAI=1 and b.TIENTE_FK= "+ this.tienteId +" and b.LOAIXOATRATRUOC ='1' and isnull(b.NCC_FK, b.NHANVIEN_FK)= "+ this.nccId +" and b.CONGTY_FK = "+this.ctyId+" \n" +
								"	         group by a.cttt_fk) xoatamung  on xoatamung.cttt_fk = cp.PK_SEQ \n" +
								"     left join " +
								"   	( \n" +
								"       select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n" +
								"       from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
								"       where TTHD.LOAIHD = 5 AND TT.TRANGTHAI = 0 and TT.CONGTY_FK = "+this.ctyId+" \n" + 
								"       group by HOADON_FK \n" +
								"       ) dathanhtoanNCC  on cp.PK_SEQ  = dathanhtoanNCC.hoadon_fk \n" +
								"where CP.congty_fk = "+this.ctyId+"  and cp.DOITUONG = '"+this.nccId+"' and cp.TIENTE_FK= "+ this.tienteId +" and ( tt.CONLAI is null or (tt.CONLAI > 0 \n" +
								"	   and tt.HOADON_FK not in \n" +
								"						(select distinct tt.HOADON_FK \n" +
								"						from 	ERP_CHIPHIKHAC_CHITIET cpct \n" +
								"								left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n" +
								"								left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n" +
								"						where 	cp.DOITUONG = '"+this.nccId+"' and tt.CONLAI = 0 and cp.CONGTY_FK = "+this.ctyId+" ) \n" +
								"					  			and tt.THANHTOANHD_FK in \n" +
								"								(select MAX(tt.THANHTOANHD_FK) \n" +
								"								from ERP_CHIPHIKHAC_CHITIET cpct   \n" +
								"									 left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n" +
								"					 				 left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n" +
								"									 left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK\n" +
								"						 where cp.trangthai = 1    and  cp.DOITUONG =  '"+this.nccId+"' and t.TRANGTHAI<>2 and t.CONGTY_FK = "+this.ctyId+" 	\n" +
								"						 group by tt.HOADON_FK ) ) )\n" +
								"					 and cp.trangthai = 1 \n ";
								
								if(this.DoiTuongChiPhiKhac.equals("1")){ // Neu la NCC 
									query += " and cp.LOAI= '0' \n";
								}
								if(this.id.length() > 0)
								{
									query += " and cp.pk_seq not in (select HOADON_FK from ERP_THANHTOANHOADONNCC_HOADON where THANHTOANHD_FK = "+ this.id +"  ) \n";
								}
								query += " and ((cpct.TONGTIENCHUATHUE + cpct.THUE - isnull(xoatamung.dathanhtoan,0) - isnull(dathanhtoanNCC.dathanhtoan,0) ) > 0 or tt.CONLAI > 0) \n";
						
					// LOAIHD: 6 - DENGHITHANHTOAN							 
					  if(this.id.length() > 0)
					  {
						
						query += " UNION ALL \n"; 
						query += " select distinct 6 as LOAIHD, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , mh.TONGTIENCONLAI as sotiengoc, \n" +
								"        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
								"		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA, mh.ngaydenhantt  \n"+
								" from ERP_MUAHANG mh \n" +
								" 	inner join ERP_THANHTOANHOADONNCC_HOADON tt on tt.HOADON_FK = mh.PK_SEQ  AND TT.LOAIHD = 6 \n"+
								" 	inner join ERP_THANHTOANHOADONNCC tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"+
								" 	inner join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
								" where   mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" +this.id+ "' and mh.CONGTY_FK = "+this.ctyId+" \n";													
										
					  }	
					  query += " UNION ALL \n"; 									
						query += " select distinct 6 as LOAIHD, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon, mh.TONGTIENCONLAI as sotiengoc \n"+
								" 		, mh.TONGTIENCONLAI- isnull(t.SOTIENTT,0) - isnull(DATT.SOTIENTT,0) - isnull(DATHANHTOANNCC.DATHANHTOAN,0) as SOTIENAVAT ,0 as sotientt, '' as POID  \n"+
								" 		,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA,  mh.ngaydenhantt  \n"+
								" from ERP_MUAHANG mh \n" +
								" 		LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"+
								" 		LEFT JOIN ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk  \n"+
								" 		INNER JOIN ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
								" 		LEFT JOIN "+
								"       ( \n"+
								"         SELECT tt.HOADON_FK , SUM(tt.SOTIENTT) as SOTIENTT "+
								"         FROM ERP_THANHTOANHOADON_HOADONBOPHAN tt \n"+
								"              INNER JOIN ERP_THANHTOANHOADON t on tt.THANHTOANHD_FK = t.PK_SEQ "+
								"         WHERE tt.LOAIHD = 6 AND t.TRANGTHAI != 2  AND tt.NCC_FK = "+ this.nccId +" AND t.CONGTY_FK = "+this.ctyId+"  "+
								"         GROUP BY tt.HOADON_FK "+
								"        ) DATT ON DATT.HOADON_FK = mh.PK_SEQ \n"+
								"       LEFT JOIN  " +
								"   	( \n" +
								"       select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n" +
								"       from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
								"       where TTHD.LOAIHD = 6 AND TT.TRANGTHAI in (0) AND TT.CONGTY_FK = "+this.ctyId+" \n" + 
								"       group by HOADON_FK \n" +
								"       ) dathanhtoanNCC  on mh.PK_SEQ  = dathanhtoanNCC.hoadon_fk \n" +
								" where  mh.TRANGTHAI = 1 and mh.NHACUNGCAP_FK = '" + this.nccId + "' \n" +
								" 		and ( tt.CONLAI is null or  \n" +
								"           (tt.CONLAI > 0 \n"+
								"			and mh.TIENTE_FK = "+ this.tienteId +" \n"+
								" 			and tt.HOADON_FK not in \n"+
								"				(select distinct tt.HOADON_FK \n"+
								"				 from ERP_MUAHANG mh \n"+
								"				      left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"+
								"				      left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
								"				where mh.TRANGTHAI = 1 and mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.CONLAI = 0 and mh.TIENTE_FK = "+ this.tienteId +") and mh.CONGTY_FK = "+this.ctyId+" \n"+
								"  			and tt.THANHTOANHD_FK in \n"+
								"				(select MAX(tt.THANHTOANHD_FK) \n"+
								"				from Erp_MuaHang mh  \n"+
								"				    left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"+
								" 				    left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n"+
								"				    left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
								"				where mh.CONGTY_FK = "+this.ctyId+" and mh.TRANGTHAI = 1 and mh.LOAIHANGHOA_FK = '2' and TYPE = '1' and mh.TIENTE_FK = "+ this.tienteId +"  and  mh.NHACUNGCAP_FK =  '" + this.nccId + "' and t.TRANGTHAI<>2	\n"+ //trang thai mh=2 da hoan tat
								"	 			group by tt.HOADON_FK ) ) ) \n"+
								" 		and  mh.LOAIHANGHOA_FK = '2' and mh.TYPE ='1' \n";
								if(this.id.length() > 0)
								{
									query+= " and mh.PK_SEQ not in (select HOADON_FK from  ERP_THANHTOANHOADONNCC_HOADON where THANHTOANHD_FK = "+ this.id +") \n" ;
								}
						query+=	"       and  (mh.TONGTIENAVAT - isnull(t.SOTIENTT,0) - isnull(DATT.SOTIENTT,0) - isnull(DATHANHTOANNCC.DATHANHTOAN,0) ) > 0 \n";
			
						// LOAD NHỮNG PHIẾU CHI (ĐỀ NGHỊ TT ĐÃ CHỐT)
						if(this.id.length() > 0 )
						{
							query += " UNION ALL \n" ;
							query += " select distinct  8 as LOAIHD, tt.HOADON_FK as PK_SEQ, cast(tt.HOADON_FK as nvarchar(50)) as sohoadon,  N'Chi đề nghị thanh toán'  AS KYHIEU , \n"+
									"       (select a.NGAYGHINHAN from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as ngayhoadon , \n" +
									"       ISNULL((select SUM(a.SOTIENTT) as SOTIENTT  from ERP_THANHTOANHOADON_HOADON a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ where a.LOAIHD = 6 AND b.NCC_FK = '" + this.nccId + "' ),0)*(-1) as sotiengoc, "+
									"       ( CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END) as SOTIENAVAT, \n"+
									"		 tt.SOTIENTT as SOTIENTT , '' as POID , tthd.TIENTE_FK as ttId,  (select a.TIGIA from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as TIGIA,  '' ngaydenhantt  \n"+
									" from  ERP_THANHTOANHOADONNCC_HOADON tt  \n"+
									" 	inner join ERP_THANHTOANHOADONNCC tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  and tt.LOAIHD = 8 \n"+
									" 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tthd.NHANVIEN_FK \n"+
									" where   tthd.NCC_FK = '" + this.nccId + "' and tthd.PK_SEQ = "+ this.id +"  \n";
							
						}
						

						query += " UNION ALL \n" ;
						query += " select distinct 8 as LOAIHD, tthd.PK_SEQ, cast(tthd.PK_SEQ as nvarchar(50)) as sohoadon,  N'Chi đề nghị thanh toán'  AS KYHIEU , tthd.NGAYGHINHAN as ngayhoadon ,ISNULL(GOC.SOTIENTT,0)*(-1) as sotiengoc, \n" +
								"       ISNULL(GOC.SOTIENTT,0)*(-1) - ISNULL(DATT.DATHANHTOAN,0) - ISNULL(DATHANHTOANNCC.DATHANHTOAN,0) as SOTIENAVAT, \n"+
								"		 0 as SOTIENTT , '' as POID ,tthd.TIENTE_FK as ttId, tthd.TIGIA,  '' ngaydenhantt  \n"+
								" from  ERP_THANHTOANHOADON tthd  \n"+
								"   inner join   "+
								"   (select THANHTOANHD_FK, SUM(SOTIENTT) as SOTIENTT "+
								"    from ERP_THANHTOANHOADON_HOADON "+
								"    where LOAIHD = 6 "+
								"    group by THANHTOANHD_FK "+
								") GOC on tthd.PK_SEQ = GOC.THANHTOANHD_FK "+
								" 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tthd.NHANVIEN_FK \n"+
								"   left join "+
								"   ( select a.HOADON_FK , SUM(a.SOTIENTT) as DATHANHTOAN "+
								"     from ERP_THANHTOANHOADON_HOADON a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ  "+
								"     where a.LOAIHD = 8 and b.TRANGTHAI != '2'  "+
								"     group by a.HOADON_FK "+
								") DATT on DATT.HOADON_FK = tthd.PK_SEQ  "+
								"    left join " +
								"   	( \n" +
								"       select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n" +
								"       from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
								"       where TTHD.LOAIHD = 8 AND TT.TRANGTHAI = 0   \n" + 
								"       group by HOADON_FK \n" +
								"       ) dathanhtoanNCC  on  tthd.PK_SEQ = dathanhtoanNCC.hoadon_fk \n" +
								" where  ABS(ISNULL(GOC.SOTIENTT,0)*(-1) - ISNULL(DATT.DATHANHTOAN,0) - ISNULL(DATHANHTOANNCC.DATHANHTOAN,0) ) > 0 AND  tthd.NCC_FK = '" + this.nccId + "' and tthd.TRANGTHAI = 1 AND tthd.CONGTY_FK = "+this.ctyId+"  \n";
						
								if(this.id.length() > 0)
								{
									query+= " and tthd.PK_SEQ not in (select HOADON_FK from  ERP_THANHTOANHOADONNCC_HOADON where THANHTOANHD_FK = "+ this.id +" ) \n" ;
								}
							
								// LOAD NHỮNG PHIẾU CHI (ĐỀ NGHỊ TẠM ỨNG ĐÃ CHỐT)
								if(this.id.length() > 0 )
								{
									query += " UNION ALL \n" ;
									query += 
											" select distinct 9 as LOAIHD, tt.HOADON_FK as PK_SEQ, cast(tt.HOADON_FK as nvarchar(50)) as sohoadon,  N'Chi đề nghị tạm ứng'  AS KYHIEU , \n"+
											"       (select a.NGAYGHINHAN from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as ngayhoadon , \n" +
											"      ISNULL( (select SUM(a.SOTIENTT) as SOTIENTT  from ERP_THANHTOANHOADON_HOADON  a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ where a.LOAIHD = 1 and b.NCC_FK = '" + this.nccId + "' ),0)*(-1) as sotiengoc,  \n"+
											"       ( CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END) as SOTIENAVAT, \n"+
											"		 tt.SOTIENTT as SOTIENTT , '' as POID , tthd.TIENTE_FK as ttId,  (select a.TIGIA from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as TIGIA,  '' ngaydenhantt  \n"+
											" from  ERP_THANHTOANHOADONNCC_HOADON tt  \n"+
											" 	inner join ERP_THANHTOANHOADONNCC tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  and tt.LOAIHD = 9 \n"+
											" 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tthd.NHANVIEN_FK \n"+
											" where   tthd.NCC_FK = '" + this.nccId + "' and tthd.PK_SEQ = "+ this.id +" \n";
									
								}
								
								query += " UNION ALL \n" ;
								query += " select distinct 9 as LOAIHD, tthd.PK_SEQ, cast(tthd.PK_SEQ as nvarchar(50)) as sohoadon,  N'Chi đề nghị tạm ứng'  AS KYHIEU , tthd.NGAYGHINHAN as ngayhoadon , ISNULL(GOC.SOTIENTT,0)*(-1) as sotiengoc,  \n" +
										"       ISNULL(GOC.SOTIENTT,0)*(-1) - ISNULL(DATT.DATHANHTOAN,0) - ISNULL(DATHANHTOANNCC.DATHANHTOAN,0) as SOTIENAVAT, \n"+
										"		 0 as SOTIENTT , '' as POID ,tthd.TIENTE_FK as ttId, tthd.TIGIA,  '' ngaydenhantt  \n"+
										" from  ERP_THANHTOANHOADON tthd  \n"+
										"   inner join   "+
										"   (select THANHTOANHD_FK, SUM(SOTIENTT) as SOTIENTT "+
										"    from ERP_THANHTOANHOADON_HOADON "+
										"    where LOAIHD = 1 "+
										"    group by THANHTOANHD_FK "+
										") GOC on tthd.PK_SEQ = GOC.THANHTOANHD_FK "+
										" 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tthd.NHANVIEN_FK \n"+
										"   left join "+
										"   ( select a.HOADON_FK , SUM(a.SOTIENTT) as DATHANHTOAN "+
										"     from ERP_THANHTOANHOADON_HOADON a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ  "+
										"     where a.LOAIHD = 9 and b.TRANGTHAI != '2' "+
										"     group by a.HOADON_FK "+
										") DATT on DATT.HOADON_FK = tthd.PK_SEQ  "+
										"    left join " +
										"   	( \n" +
										"       select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n" +
										"       from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
										"       where TTHD.LOAIHD = 9 AND TT.TRANGTHAI = 0   \n" + 
										"       group by HOADON_FK \n" +
										"       ) dathanhtoanNCC  on  tthd.PK_SEQ = dathanhtoanNCC.hoadon_fk \n" +
										" where ABS(ISNULL(GOC.SOTIENTT,0)*(-1) - ISNULL(DATT.DATHANHTOAN,0) - ISNULL(DATHANHTOANNCC.DATHANHTOAN,0)) > 0 AND    tthd.NCC_FK = '" + this.nccId + "' and tthd.TRANGTHAI = 1 and tthd.CONGTY_FK = "+this.ctyId+"  \n";
								
										if(this.id.length() > 0)
										{
											query+= " and tthd.PK_SEQ not in (select HOADON_FK from  ERP_THANHTOANHOADONNCC_HOADON where THANHTOANHD_FK = "+ this.id +" ) \n" ;
										}
			
				}				
			
				
				System.out.println("Query khoi tao hoa don 111112ABC: " + query);
				
				ResultSet rsHoadon = db.get(query);
				List<IHoadon> hdList = new ArrayList<IHoadon>();
				if(rsHoadon != null)
				{
					try 
					{
						IHoadon hd = null;
						while(rsHoadon.next())
						{
							String doituong = "";
							String doituongId = "";
							String madoituong ="";
							
							String id = rsHoadon.getString("pk_seq");
							String kyhieu = rsHoadon.getString("kyhieu");
							String sohoadon = rsHoadon.getString("sohoadon");
							String ngayhd = rsHoadon.getString("ngayhoadon");
							String ngaydenhantt = rsHoadon.getString("ngaydenhantt");
							String tienteId = rsHoadon.getString("TTID");
							String avat = "";
							String loaihd = rsHoadon.getString("LOAIHD");
							
							if(tienteId.equals("100000")){
								avat = ("" + rsHoadon.getDouble("sotienAVAT")).replaceAll(",", "");
							}else{
								avat = ("" + rsHoadon.getDouble("sotienAVAT")*rsHoadon.getDouble("TIGIA")).replaceAll(",", "");
							}
							
							String sotienNT = ("" + rsHoadon.getDouble("sotienAVAT")).replaceAll(",", "");							
							String dathanhtoan = ("" + rsHoadon.getDouble("SOTIENTT")).replaceAll(",", "");
							String tigia = rsHoadon.getString("TIGIA").replaceAll(",", "");
							
							if(this.bophanId.trim().length() > 0)
							{
								doituong = rsHoadon.getString("DOITUONG");
								doituongId = rsHoadon.getString("DOITUONG_FK");
								madoituong = rsHoadon.getString("MADOITUONG");
							}
							
							hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, sotienNT, dathanhtoan,  tienteId);
							hd.setSoPO(rsHoadon.getString("POID"));
							hd.setTigia(tigia);
							hd.setNgaydenhanTT(ngaydenhantt);
							hd.setLoaihd1(loaihd);
							
							if(this.bophanId.trim().length() > 0)
							{
							  hd.setDoituong(doituong);
							  hd.setDoituongId(doituongId);
							  hd.setMadoituong(madoituong);
							}
							
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
			
			
			if( (this.nhomNCCCNId.length() > 1 ) && this.htttId.length() > 0 && this.hoadonList.size() <= 0)
			{
				NumberFormat formatter = new DecimalFormat("#,###,###");
				
				String query = "";
				if(this.LoaiThanhToan.equals("1")) // HÓA ĐƠN
				{
					if(this.id.length() > 0)
					{
					query += " select p.ncc_fk , b.pk_seq, b.kyhieu, b.sohoadon, b.ngayhoadon, b.sotienAVAT - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVAT, SOTIENTT, " +
								" ISNULL( (  " +
										"Select distinct ( Select cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()]  " +
										"From ERP_HOADONNCC_PHIEUNHAP PO1   " +
										"Where PO1.hoadonncc_fk = b.pk_seq " +
											"For XML PATH ('')) [phongbanChon_fk]  " +
											"From ERP_HOADONNCC_PHIEUNHAP PO  " +
											"where PO.hoadonncc_fk = b.pk_seq " +
										"), '' )   as POID " +
							"from ERP_THANHTOANHOADON_HOADON a " +
							"inner join ERP_HOADONNCC b on a.hoadon_fk = b.pk_seq " +
							"    inner join ERP_PARK p on b.park_fk = p.pk_seq  " + // them join voi ERP_PARK de lay ma ncc
								"left join	" +
								"( select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan " +
								"from ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ " +
								"where TT.trangthai !=2 and TTHD.thanhtoanhd_fk != '" + this.id + "' " +  //sua trang thai !=2 thanh =1
										" and TTHD.hoadon_fk in (select hoadon_fk " +
														  "from ERP_THANHTOANHOADON_HOADON " +
														  "where thanhtoanhd_fk = '" + this.id + "') " +
								"group by hoadon_fk) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk " +
								"where a.thanhtoanhd_fk = '" + this.id + "' " +
							" union all ";
					}
					
					query += "(select hoadon.ncc_fk , hoadon.pk_seq, hoadon.kyhieu, hoadon.sohoadon, hoadon.ngayhoadon, " +
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
									"select b.ncc_fk , a.pk_seq, a.kyhieu, a.sohoadon, a.ngayhoadon, a.sotienAVAT " +
									"from ERP_HOADONNCC a inner join ERP_PARK b on a.park_fk = b.pk_seq " +
									"where b.ncc_fk in ( select NCC_FK from NHOMNHACUNGCAPCN_NCC where NHOMNHACUNGCAPCN_FK = '"+ this.nhomNCCCNId  +"')" +
									" and b.trangthai = '2' and a.trangthai = '0' ";
								if(this.id.length() > 0)
								{
									query += "and a.pk_seq not in (select hoadon_fk from ERP_THANHTOANHOADON_HOADON where thanhtoanhd_fk = '" + this.id + "') ";
								}
						query += ") hoadon " +
							"left join ( " +
									"select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  " +
									"from ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ " +
									"where TT.TRANGTHAI !=2 and TTHD.HOADON_FK in (select pk_seq from ERP_HOADONNCC where trangthai = 0)  " + //sua trang thai !=2 thanh =1
									"group by HOADON_FK " +
									") dathanhtoan " +
							"on hoadon.pk_seq = dathanhtoan.hoadon_fk " +
							"where hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') > 0 " +
							" and hoadon.pk_seq not in ( "+
							"	select distinct td.HOADON_FK from ERP_THANHTOANHOADON_HOADON td "+
							"	inner join ERP_THANHTOANHOADON t on t.PK_SEQ = td.THANHTOANHD_FK "+
							"	where t.TRANGTHAI =0) )	";
				}
				System.out.println("Query khoi tao hoa don35352: " + query);
				
				ResultSet rsHoadon = db.get(query);
				List<IHoadon> hdList = new ArrayList<IHoadon>();
				if(rsHoadon != null)
				{
					try 
					{
						IHoadon hd = null;
						while(rsHoadon.next())
						{
							String ncc_fk =  rsHoadon.getString("ncc_fk");
							String id = rsHoadon.getString("pk_seq");
							String kyhieu = rsHoadon.getString("kyhieu");
							String sohoadon = rsHoadon.getString("sohoadon");
							String ngayhd = rsHoadon.getString("ngayhoadon");
							String avat = formatter.format(rsHoadon.getDouble("sotienAVAT"));
							//System.out.println("Query sohoadon: " + sohoadon);
							String dathanhtoan ="";
							if(this.id.length() > 0)
							{
								dathanhtoan = formatter.format(rsHoadon.getDouble("SOTIENTT"));
							}
							
							hd = new Hoadon(ncc_fk, id, kyhieu, sohoadon, ngayhd, avat, "0", dathanhtoan, "" , "");
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
				System.out.println("So hoa don33333: " + this.hoadonList.size());
				
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

	
	public String getDiachi() {
		return this.diachi;
	}

	
	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public String delete() {
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			db.update("update ERP_ThanhToanHoaDon set trangthai = '2' where pk_seq = '" + this.id + "'");
			
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
			db.shutDown(); 
			return "Khong the xoa ThanhToanHoaDon"; 
		}
	}
	

	
	public static void main(String[] args) {
		ErpDenghithanhtoanNCC erpThanhtoanhoadon = new ErpDenghithanhtoanNCC();
		erpThanhtoanhoadon.setId("100079");
		List<String> hienTai = erpThanhtoanhoadon.GetlistHoaDonHienTai();
		System.out.println(hienTai.toString());
	}

	public void setCheckThanhtoantuTV(String checkThanhtoantuTV) {
		this.checkThanhtoantuTV= checkThanhtoantuTV;
		
	}

	public String getCheckThanhtoantuTV() {
		return this.checkThanhtoantuTV;
	}
	
	public String getKhachhangId() {
		
		return this.khachhangId;
	}

	
	public void setKhachhangId(String khachhangId) {
		
		this.khachhangId=khachhangId;
	}


	public ResultSet getNhanvienRs() {

		return this.NhanvienRs;
	}


	public void setNhanvienRs(ResultSet NhanvienRs) 
	{

		this.NhanvienRs = NhanvienRs;
	}


	public ResultSet getKhachhangRs() 
	{
		return this.khachhangRs;
	}


	public void setKhachhangRs(ResultSet khachhangRs) 
	{
		this.khachhangRs = khachhangRs;
		
	}


	public ResultSet getNhomNCCRs() 
	{
		return this.nhomNCCRs;
	}


	public void setNhomNCCRs(ResultSet nhomNCCRs) 
	{

		this.nhomNCCRs = nhomNCCRs;
	}
	
	public String getBophanTen()
    {
		return this.bophanTen ;
	}

	public void setBophanTen(String bophanTen) 
	{
		this.bophanTen = bophanTen;
	}

	public String getBophanId() 
	{
		return this.bophanId;
	}

	public void setBophanId(String bophanId) 
	{
		this.bophanId = bophanId;
	}

	
	public String getnppdangnhap() {
		
		return this.nppdangnhap;
	}

	
	public void setnppdangnhap(String nppdangnhap) {
		
		this.nppdangnhap = nppdangnhap;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.userId);
	}
	
}
