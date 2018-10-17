package geso.traphaco.distributor.beans.reports.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.distributor.beans.reports.IBcNghiepVuHT;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;

public class BcNghiepVuHT extends Phan_Trang implements IBcNghiepVuHT,
		Serializable {
	/**
   * 
   */
	private static final long serialVersionUID = -6878643533846192322L;

	public BcNghiepVuHT() {
		this.spId = "";
		this.nppId = "";
		this.userId = "";
		this.khId = "";
		this.msg = "";
		this.ddkdId = "";
		this.tuNgay = "";
		this.denNgay = "";
		this.kbhId = "";
		this.ttId = "";
		this.nhomId = "";
		this.vungId = "";
		this.loaiHoaDon = "0";
		this.action = "";
		this.khoId = "";
		this.loaiSpId = "";
		db = new dbutils();
	}

	String tuNgay, denNgay, spId, nppId, ddkdId, userId, khId, loaiSpId;
	ResultSet loaiSpRs;

	public String getKhId() {
		return khId;
	}

	public void setKhId(String khId) {
		this.khId = khId;
	}

	public String getTuNgay() {
		return tuNgay;
	}

	public void setTuNgay(String tuNgay) {
		this.tuNgay = tuNgay;
	}

	public String getDenNgay() {
		return denNgay;
	}

	public void setDenNgay(String denNgay) {
		this.denNgay = denNgay;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getDdkdId() {
		return ddkdId;
	}

	public void setDdkdId(String ddkdId) {
		this.ddkdId = ddkdId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ResultSet getSpRs() {
		return spRs;
	}

	public void setSpRs(ResultSet spRs) {
		this.spRs = spRs;
	}

	public ResultSet getDdkdRs() {
		return ddkdRs;
	}

	public void setDdkdRs(ResultSet ddkdRs) {
		this.ddkdRs = ddkdRs;
	}

	ResultSet spRs, ddkdRs, khRs, hoadonRs;

	public ResultSet getHoadonRs() {
		return hoadonRs;
	}

	public void setHoadonRs(ResultSet hoadonRs) {
		this.hoadonRs = hoadonRs;
	}

	public ResultSet getKhRs() {
		return khRs;
	}

	public void setKhRs(ResultSet khRs) {
		this.khRs = khRs;
	}

	public void closeDB() {

	}

	dbutils db = new dbutils();

	public void createRs() {

		try
		{
			Utility util = new Utility(); 
			String query = "select pk_seq, ten + ', ' + diachi as khoTen from erp_khott where trangthai = '1' and pk_seq in "+util.quyen_khott(this.userId);
			System.out.println("::: LAY KHO : "+ query);
			this.khoRs = db.get(query);
			
			query = "SELECT DISTINCT LSP.PK_SEQ, LSP.MA, LSP.MA + ', ' + LSP.TEN AS TEN " +
					"FROM ERP_LOAISANPHAM LSP " +
					"WHERE LSP.TRANGTHAI = 1 ";
			
			if(this.khoId.trim().length() > 0 )
				  query+=" AND LSP.PK_SEQ  in ( select loaisanpham_fk from ERP_KHOTT_LOAISANPHAM where khott_fk in ( " + this.khoId + " ) )";
			
			this.loaiSpRs = this.db.get(query);
			System.out.println("::: LAY LSP : "+ query);
			
			if(this.khoId.length() > 0)
			{
				query = " select pk_seq, ma, ten " +
						" from ERP_SanPham " +
						" where trangthai = '1' and pk_seq in ( select sanpham_fk from ERP_KhoTT_SanPham where khott_fk  in (" + this.khoId + ") )" ;
				
				if(this.loaiSpId.length() > 0)
				{
					query += " and loaisanpham_fk in (" + this.loaiSpId + ")";
				}
				
				if(this.spId.trim().length() > 0)
				{
					query += " and pk_seq in (" + this.spId + ") ";
				}
				
				System.out.println("::: LAY SP : "+ query);
				this.spRs = db.get(query);
				
				/*query = "select loaikho from ERP_KhoTT where pk_seq in (" + this.khoId + ") ";
				ResultSet rs = db.get(query);
				if(rs.next())
				{
					String loaikho = rs.getString("loaikho");
					if(loaikho.trim().equals("12"))
					{
						query = "select pk_seq, ten from NHAPHANPHOI where loaiNPP in ( 1, 2, 3)";
						this.doituongRs = db.get(query);
					}
				}*/
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	String queryHd = "";

	public String getQueryHd() {
		return queryHd;
	}

	public void setQueryHd(String query) {
		this.queryHd = query;
	}

	public void init(String search) {

		String query;
		String condition = "";

		Utility util = new Utility();
		if (action.length() > 0) {
			query = "select bin.ma vitri, kho.ten kho, sp.ma spma, sp.ten spten, solo, ngayhethan, ngaynhapkho, mame, mathung, maphieu, phieudt, phieueo, MARQ, hamam, \n"+
					"hamluong, BOOKED, loaichungtu, sochungtu, ngaychungtu from ufn_BCNghiepVuHT() data \n"+
					"left join ERP_KHOTT kho on data.kho_fk = kho.pk_seq \n"+
					"left join ERP_BIN bin on data.bin_fk = bin.pk_seq \n"+
					"left join ERP_SANPHAM sp on data.sanpham_fk = sp.pk_seq \n"+
					"where 1=1  ";

			condition = " and kho_fk in  " + util.quyen_khott(this.userId);
		    if( this.khoId.trim().length() > 0 )
		    	condition += " and kho_fk in ( " + this.khoId + " ) ";
		    
		    if( this.loaiSpId.trim().length() > 0 )
		    	condition += " and sanpham_fk in ( select PK_SEQ from ERP_SANPHAM where LOAISANPHAM_FK in ( " + this.loaiSpId + " ) ) ";
		    
		    if( this.spId.trim().length() > 0 )
		    	condition += " and sanpham_fk in ( " + this.spId + " ) ";
		    query += condition;
			query += "order by loaichungtu, ngaychungtu desc ";

			System.out.println("______" + query);

			this.queryHd = query;
			this.hoadonRs = this.db.get(query);
		}
		//setTotal_Query("");
		createRs();
	}

	String view, msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	String kbhId;
	ResultSet kbhRs;

	public String getKbhId() {
		return kbhId;
	}

	public void setKbhId(String kbhId) {
		this.kbhId = kbhId;
	}

	public ResultSet getKbhRs() {
		return kbhRs;
	}

	public void setKbhRs(ResultSet kbhRs) {
		this.kbhRs = kbhRs;
	}

	public void setTotal_Query(String searchquery) {
		String query = "";
		this.totalRs = this.db.get(query);
	}

	ResultSet totalRs;

	public ResultSet getTotalRs() {
		return totalRs;
	}

	public void setTotalRs(ResultSet totalRs) {
		this.totalRs = totalRs;
	}

	String vungId, nhomId, ttId;
	ResultSet vungRs, nhomRs, ttRs;

	public String getVungId() {
		return vungId;
	}

	public void setVungId(String vungId) {
		this.vungId = vungId;
	}

	public String getNhomId() {
		return nhomId;
	}

	public void setNhomId(String nhomId) {
		this.nhomId = nhomId;
	}

	public String getTtId() {
		return ttId;
	}

	public void setTtId(String ttId) {
		this.ttId = ttId;
	}

	public ResultSet getVungRs() {
		return vungRs;
	}

	public void setVungRs(ResultSet vungRs) {
		this.vungRs = vungRs;
	}

	public ResultSet getNhomRs() {
		return nhomRs;
	}

	public void setNhomRs(ResultSet nhomRs) {
		this.nhomRs = nhomRs;
	}

	public ResultSet getTtRs() {
		return ttRs;
	}

	public void setTtRs(ResultSet ttRs) {
		this.ttRs = ttRs;
	}

	ResultSet nppRs;

	@Override
	public ResultSet getNppRs() {
		return nppRs;
	}

	@Override
	public void setNppRs(ResultSet nppRs) {
		this.nppRs = nppRs;

	}

	String loaiHoaDon;

	public String getLoaiHoaDon() {
		return loaiHoaDon;
	}

	public void setLoaiHoaDon(String loaiHoaDon) {
		this.loaiHoaDon = loaiHoaDon;
	}

	String action;

	public String getAction() {
		return action;
	}

	public void setAction(String timkiem) {
		this.action = timkiem;
	}

	String[] spNhomId, spNhomTen;

	public String[] getSpNhomId() {
		return spNhomId;
	}

	public void setSpNhomId(String[] spNhomId) {
		this.spNhomId = spNhomId;
	}

	public String[] getSpNhomTen() {
		return spNhomTen;
	}

	public void setSpNhomTen(String[] spNhomTen) {
		this.spNhomTen = spNhomTen;
	}

	String khoId;
	ResultSet khoRs;

	public String getKhoId() {
		return khoId;
	}

	public void setKhoId(String khoId) {
		this.khoId = khoId;
	}

	public ResultSet getKhoRs() {
		return khoRs;
	}

	public void setKhoRs(ResultSet khoRs) {
		this.khoRs = khoRs;
	}

	private void getNppInfo() {
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
	}

	@Override
	public String getLoaiSpId() {
		return this.loaiSpId;
	}

	@Override
	public void setLoaiSpId(String loaiSpId) {
		this.loaiSpId = loaiSpId;
	}

	@Override
	public ResultSet getLoaiSpRs() {
		return this.loaiSpRs;
	}

	@Override
	public void setLoaiSpRs(ResultSet loaispRs) {
		this.loaiSpRs = loaispRs;
	}

}
