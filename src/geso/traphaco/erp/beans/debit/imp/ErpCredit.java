package geso.traphaco.erp.beans.debit.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.debit.IErpCredit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpCredit  implements IErpCredit{
	private String id;
	private String ngayGhiNhan;
	private String doiTuong;
	private String loaiDoiTuong;
	private String dienGiai;
	private double tongTienNguyenTe;
	private double tongTienVND;
	private String nguoiTao;
	private String nguoiSua;
	private String ngayTao;
	private String ngaySua;
	private dbutils db;
	private String userId;
	private String msg;
	private double tigia;
	private String tienTe;
	
	private ResultSet rsDoiTuong;
	private List<TaiKhoan> listTaiKhoan;
	private List<TrungTamChiPhi> listTrungTamChiPhi;
	private List<String> taiKhoans;
	private List<Double> noNguyenTes;
	private List<Double> noVNDs;
	private List<String> trungTamChiPhis;
	private List<String> khoanMucChiPhis;
	private List<String> moTas;
	private List<Double> donGias;
	private List<Integer> soLuongs;
	private ResultSet rsTienTe;
	
	
	public ErpCredit(String id, String ngayGhiNhan, String doiTuong,
			String loaiDoiTuong, String dienGiai, double tongTienNguyenTe,
			double tongTienVND, String nguoiTao, String nguoiSua,
			String ngayTao, String ngaySua, dbutils db, String userId) {
		super();
		this.id = id;
		this.ngayGhiNhan = ngayGhiNhan;
		this.doiTuong = doiTuong;
		this.loaiDoiTuong = loaiDoiTuong;
		this.dienGiai = dienGiai;
		this.tongTienNguyenTe = tongTienNguyenTe;
		this.tongTienVND = tongTienVND;
		this.nguoiTao = nguoiTao;
		this.nguoiSua = nguoiSua;
		this.ngayTao = ngayTao;
		this.ngaySua = ngaySua;
		this.db = db;
		this.userId = userId;
		this.tigia =0;
		this.tienTe = "";
	}
	public ErpCredit() {
		super();
		this.id = "";
		this.ngayGhiNhan = "";
		this.doiTuong = "";
		this.loaiDoiTuong = "";
		this.dienGiai = "";
		this.tongTienNguyenTe = 0;
		this.tongTienVND = 0;
		this.nguoiTao = "";
		this.nguoiSua = "";
		this.ngayTao = "";
		this.ngaySua = "";
		this.db = new dbutils();
		this.userId = "";
		this.msg = "";
		this.listTaiKhoan = new ArrayList<TaiKhoan>();
		this.listTrungTamChiPhi = new ArrayList<TrungTamChiPhi>();
		this.tigia =0;
		this.taiKhoans = new ArrayList<String>();
		this.noNguyenTes = new ArrayList<Double>();
		this.noVNDs = new ArrayList<Double>();
		this.trungTamChiPhis = new ArrayList<String>();
		this.khoanMucChiPhis = new ArrayList<String>();
		this.moTas = new ArrayList<String>();
		this.donGias = new ArrayList<Double>();
		this.soLuongs = new ArrayList<Integer>();
		
	}
	
	public List<Double> getDonGias() {
		return donGias;
	}
	public void setDonGias(List<Double> donGias) {
		this.donGias = donGias;
	}
	public List<Integer> getSoLuongs() {
		return soLuongs;
	}
	public void setSoLuongs(List<Integer> soLuongs) {
		this.soLuongs = soLuongs;
	}
	public String getTienTe() {
		return tienTe;
	}
	public void setTienTe(String tienTe) {
		this.tienTe = tienTe;
	}
	public ResultSet getRsTienTe() {
		return rsTienTe;
	}
	public void setRsTienTe(ResultSet rsTienTe) {
		this.rsTienTe = rsTienTe;
	}
	public List<String> getTaiKhoans() {
		return taiKhoans;
	}
	public void setTaiKhoans(List<String> taiKhoans) {
		this.taiKhoans = taiKhoans;
	}
	public List<Double> getNoNguyenTes() {
		return noNguyenTes;
	}
	public void setNoNguyenTes(List<Double> noNguyenTes) {
		this.noNguyenTes = noNguyenTes;
	}
	public List<Double> getNoVNDs() {
		return noVNDs;
	}
	public void setNoVNDs(List<Double> noVNDs) {
		this.noVNDs = noVNDs;
	}
	public List<String> getTrungTamChiPhis() {
		return trungTamChiPhis;
	}
	public void setTrungTamChiPhis(List<String> trungTamChiPhis) {
		this.trungTamChiPhis = trungTamChiPhis;
	}
	public List<String> getKhoanMucChiPhis() {
		return khoanMucChiPhis;
	}
	public void setKhoanMucChiPhis(List<String> khoanMucChiPhis) {
		this.khoanMucChiPhis = khoanMucChiPhis;
	}
	public List<String> getMoTas() {
		return moTas;
	}
	public void setMoTas(List<String> moTas) {
		this.moTas = moTas;
	}
	public double getTigia() {
		return tigia;
	}
	public void setTigia(double tigia) {
		this.tigia = tigia;
	}
	public ResultSet getRsDoiTuong() {
		return rsDoiTuong;
	}
	public void setRsDoiTuong(ResultSet rsDoiTuong) {
		this.rsDoiTuong = rsDoiTuong;
	}
	public List<TaiKhoan> getListTaiKhoan() {
		return listTaiKhoan;
	}
	public void setListTaiKhoan(List<TaiKhoan> listTaiKhoan) {
		this.listTaiKhoan = listTaiKhoan;
	}
	public List<TrungTamChiPhi> getListTrungTamChiPhi() {
		return listTrungTamChiPhi;
	}
	public void setListTrungTamChiPhi(List<TrungTamChiPhi> listTrungTamChiPhi) {
		this.listTrungTamChiPhi = listTrungTamChiPhi;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNgayGhiNhan() {
		return ngayGhiNhan;
	}
	public void setNgayGhiNhan(String ngayGhiNhan) {
		this.ngayGhiNhan = ngayGhiNhan;
	}
	public String getDoiTuong() {
		return doiTuong;
	}
	public void setDoiTuong(String doiTuong) {
		this.doiTuong = doiTuong;
	}
	public String getLoaiDoiTuong() {
		return loaiDoiTuong;
	}
	public void setLoaiDoiTuong(String loaiDoiTuong) {
		this.loaiDoiTuong = loaiDoiTuong;
	}
	public String getDienGiai() {
		return dienGiai;
	}
	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}
	public double getTongTienNguyenTe() {
		return tongTienNguyenTe;
	}
	public void setTongTienNguyenTe(double tongTienNguyenTe) {
		this.tongTienNguyenTe = tongTienNguyenTe;
	}
	public double getTongTienVND() {
		return tongTienVND;
	}
	public void setTongTienVND(double tongTienVND) {
		this.tongTienVND = tongTienVND;
	}
	public String getNguoiTao() {
		return nguoiTao;
	}
	public void setNguoiTao(String nguoiTao) {
		this.nguoiTao = nguoiTao;
	}
	public String getNguoiSua() {
		return nguoiSua;
	}
	public void setNguoiSua(String nguoiSua) {
		this.nguoiSua = nguoiSua;
	}
	public String getNgayTao() {
		return ngayTao;
	}
	public void setNgayTao(String ngayTao) {
		this.ngayTao = ngayTao;
	}
	public String getNgaySua() {
		return ngaySua;
	}
	public void setNgaySua(String ngaySua) {
		this.ngaySua = ngaySua;
	}
	public dbutils getDb() {
		return db;
	}
	public void setDb(dbutils db) {
		this.db = db;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void initRsTaiKhoan(){
		String query = "SELECT PK_SEQ,SOHIEUTAIKHOAN +'-'+TENTAIKHOAN AS TEN FROM " +
				       " ERP_TAIKHOANKT  where TRANGTHAI='1' and npp_fk ='1'";
		ResultSet rs = this.db.get(query);
		List<TaiKhoan> list = new ArrayList<TaiKhoan>();
		try{
		if( rs!=null){
			while (rs.next()){
				TaiKhoan tk = new TaiKhoan();
				tk.setTaiKhoanId(rs.getString("PK_SEQ"));
				tk.setDienTa(rs.getString("TEN"));
				list.add(tk);
			}
			rs.close();
		}
		this.listTaiKhoan = list;
		} catch( Exception ex){
			ex.printStackTrace();
		}
	}
	public void initRsTrungTamChiPhi(){
		String query = "SELECT PK_SEQ,MA +'-'+DIENGIAI AS TEN FROM " +
	       " ERP_TRUNGTAMCHIPHI where TRANGTHAI='1'";
		ResultSet rs = this.db.get(query);
		List<TrungTamChiPhi> list = new ArrayList<TrungTamChiPhi>();
		try{
		if( rs!=null){
			while (rs.next()){
				TrungTamChiPhi tk = new TrungTamChiPhi();
				tk.setTrungTamChiPhiId(rs.getString("PK_SEQ"));
				tk.setDienGiai(rs.getString("TEN"));
				list.add(tk);
			}
			rs.close();
		}
		this.listTrungTamChiPhi = list;
		} catch( Exception ex){
			ex.printStackTrace();
		}
	}
	public void initRsDoiTuong(){
		String query = " select PK_SEQ as MA, kh.Ten as TEN, 'KH' as loai from ERP_KHACHHANG kh " +
				"where kh.TrangThai='1' \n" +
				"union all \n" +
				"select PK_SEQ as MA, ncc.TEN as TEN, 'NCC' as loai from ERP_NHACUNGCAP ncc \n" +
				"where ncc.TrangThai='1'  and duyet = '1'\n";
		this.rsDoiTuong = this.db.get(query);
		
	}
	public void initRsTienTe() {
		this.rsTienTe = db.get("select PK_SEQ as PK_SEQ, MA+ '-' + TEN as TEN  from ERP_TIENTE where TRANGTHAI ='1'");
	}
	@Override
	public void init(String tiente, boolean check) {
		// TODO Auto-generated method stub
		// khởi tạo các rs
		this.initRsDoiTuong();
		this.initRsTaiKhoan();
		this.initRsTienTe();
		//this.initRsTrungTamChiPhi();
		
		// get tigiá hiện thời
		getTiGiaUSD(tiente, check);
		
	}
	
	@Override
	public void DbClose() {
		this.db.shutDown();
	}
	@Override
	public boolean create() {
		try{
			this.db.getConnection().setAutoCommit(false);
			// insert erp_debit
			String sql = "insert into ERP_CREDIT(DOITUONG, LOAIDOITUONG, NGAYGHINHAN, DIENGIAI, TONGTIENNGUYENTE, " +
					   "  TONGTIENVND, NGAYTAO, NGAYSUA,NGUOITAO, NGUOISUA, TRANGTHAI, TIENTE_FK, TIGIA) " +
					   "  values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement pre = db.getConnection().prepareStatement(sql);
			if(this.doiTuong.indexOf("NCC")>=0){
				this.loaiDoiTuong="1";
				this.doiTuong = this.doiTuong.replace("-NCC", "");
			} else{
				this.loaiDoiTuong = "2";
				this.doiTuong = this.doiTuong.replace("-KH", "");
			}
			pre.setInt(1, Integer.parseInt(this.doiTuong));
			

			pre.setInt(2,Integer.parseInt(this.loaiDoiTuong));
			pre.setString(3, this.ngayGhiNhan);
			pre.setString(4, this.dienGiai);
			pre.setDouble(5, this.tongTienNguyenTe);
			pre.setDouble(6, this.tongTienVND);
			pre.setString(7, this.ngayTao);
			pre.setString(8, this.ngaySua);
			pre.setInt(9, Integer.parseInt(this.nguoiTao) );
			pre.setInt(10, Integer.parseInt(this.nguoiSua));
			pre.setString(11,"0");
			pre.setString(12,this.tienTe);
			pre.setDouble(13,this.tigia);
			
			int k = pre.executeUpdate();
			if( k !=1){
				this.db.getConnection().rollback();
				return false;
			}
			pre.close();
			
			String ckCurrent = "";
			sql = "select IDENT_CURRENT('ERP_CREDIT') as ckId";
			PreparedStatement pre2= this.db.getConnection().prepareStatement(sql);
			ResultSet rsCk = pre2.executeQuery();
			if(rsCk !=null){
				if(rsCk.next()){
					ckCurrent = rsCk.getString("ckId");
					rsCk.close();
				}
			}

			
			// insert erp-debit_details
			if( this.taiKhoans !=null && this.noNguyenTes !=null && this.noVNDs !=null ){
				for(int i=0; i< this.taiKhoans.size(); i++){
					// insert từng dòng
					if( taiKhoans.get(i).trim().length()> 0){
						sql = " insert into ERP_CREDIT_DETAILS (CREDIT_FK, TAIKHOAN_FK, DIENGIAI, NONGUYENTE, " +
								" NOVND, TRUNGTAMCHIPHI_FK, KHOANMUCCHIPHI_FK, DONGIA, SOLUONG) " +
								" values(?,?,?,?,?,?,?,?,?)";
						PreparedStatement prek = this.db.getConnection().prepareStatement(sql);
						prek.setInt(1, Integer.parseInt(ckCurrent));
						prek.setInt(2, Integer.parseInt(this.taiKhoans.get(i)));
						prek.setString(3, this.moTas.get(i));
						
						// chuyển dữ liệu
						double temp = this.noNguyenTes.get(i);
						prek.setDouble(4, temp);
						temp = this.noVNDs.get(i);
						prek.setDouble(5, temp);
						
						prek.setString(6, null);
						prek.setString(7, null);
						prek.setDouble(8,this.donGias.get(i));
						prek.setInt(9, this.soLuongs.get(i));
						
						k = prek.executeUpdate();
						if(  k !=1){
							this.db.getConnection().rollback();
							return false;
						}
						prek.close();
					}
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} catch( Exception ex){
			ex.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
	}
	@Override
	public boolean update() {
		try{
			this.db.getConnection().setAutoCommit(false);
			// insert erp_debit
			String sql = " update ERP_CREDIT set DOITUONG = ?, NGAYGHINHAN =?, DIENGIAI = ?, " +
					     " TONGTIENNGUYENTE= ?, TONGTIENVND = ?, NGAYSUA= ?, NGUOISUA =?, TIENTE_FK = ?" +
					     " ,LOAIDOITUONG =?, TIGIA = ? where PK_SEQ  =?";
			PreparedStatement pre = db.getConnection().prepareStatement(sql);
			if(this.doiTuong.indexOf("NCC")>=0){
				this.loaiDoiTuong="1";
				this.doiTuong= this.doiTuong.replace("-NCC", "");
			} else{
				this.loaiDoiTuong = "2";
				this.doiTuong= this.doiTuong.replace("-KH", "");
			}
			
			pre.setInt(1, Integer.parseInt(this.doiTuong));
			pre.setString(2, this.ngayGhiNhan);
			pre.setString(3, this.dienGiai);
			pre.setDouble(4, this.tongTienNguyenTe);
			pre.setDouble(5, this.tongTienVND);
			pre.setString(6, this.ngaySua);
			pre.setString(7, this.nguoiSua);
			pre.setString(8, this.tienTe);
			pre.setString(9, this.loaiDoiTuong);
			pre.setDouble(10, this.tigia);
			pre.setInt(11, Integer.parseInt(this.id) );
			
			
			int k = pre.executeUpdate();
			if( k !=1){
				this.db.getConnection().rollback();
				return false;
			}
			pre.close();
			
			// xoá tất cả erp-debits details 
			sql ="delete from ERP_CREDIT_DETAILS where CREDIT_FK = "+ this.id;
			
			k= this.db.updateReturnInt(sql);
			if( k <0){
				this.db.getConnection().rollback();
				return false;
			}
			
			// insert erp-debit_details
			if( this.taiKhoans !=null && this.noNguyenTes !=null && this.noVNDs !=null){
				for(int i=0; i< this.taiKhoans.size(); i++){
					// insert từng dòng
					if( taiKhoans.get(i).trim().length()> 0){
						sql = " insert into ERP_CREDIT_DETAILS (CREDIT_FK, TAIKHOAN_FK, DIENGIAI, NONGUYENTE, " +
							  " NOVND, TRUNGTAMCHIPHI_FK, KHOANMUCCHIPHI_FK, DONGIA, SOLUONG) " +
							  " values(?,?,?,?,?,?,?,?,?)";
						PreparedStatement prek = this.db.getConnection().prepareStatement(sql);
						prek.setInt(1, Integer.parseInt(this.id));
						prek.setInt(2, Integer.parseInt(this.taiKhoans.get(i)));
						prek.setString(3, this.moTas.get(i));
						
						// chuyển dữ liệu
						double temp = this.noNguyenTes.get(i);
						prek.setDouble(4, temp);
						temp = this.noVNDs.get(i);
						prek.setDouble(5, temp);
						prek.setString(6, null);
						prek.setString(7, null);
						prek.setDouble(8, this.donGias.get(i));
						prek.setInt(9, this.soLuongs.get(i));
						
						k = prek.executeUpdate();
						if(  k !=1){
							this.db.getConnection().rollback();
							return false;
						}
						prek.close();
					}
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} catch( Exception ex){
			ex.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}
	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	@Override
	public void getView() {
		//
		try{
			// get erp_debit
			String sql =" select PK_SEQ, DOITUONG, LOAIDOITUONG, NGAYGHINHAN, DIENGIAI, " +
					    " TONGTIENNGUYENTE,TONGTIENVND, TIENTE_FK, TIGIA from ERP_CREDIT where PK_SEQ = ?";
			PreparedStatement pre = this.db.getConnection().prepareStatement(sql);
			pre.setInt(1, Integer.parseInt(this.id));
			ResultSet rs = pre.executeQuery();
			if( rs!= null){
				while ( rs.next()){
					this.id = rs.getString("PK_SEQ");
					this.doiTuong = rs.getString("DOITUONG");
					this.loaiDoiTuong = rs.getString("LOAIDOITUONG");
					this.ngayGhiNhan = rs.getString("NGAYGHINHAN");
					this.dienGiai = rs.getString("DIENGIAI");
					this.tongTienNguyenTe = rs.getDouble("TONGTIENNGUYENTE");
					this.tongTienVND = rs.getDouble("TONGTIENVND");
					this.tienTe = rs.getString("TIENTE_FK");
				    if (rs.wasNull()) {
				        this.tienTe = "";
				    }
				    // lam tron 2 chu so
				    this.tigia = this.round(rs.getDouble("TIGIA"),2);
				}
				rs.close();
			}
			if(this.loaiDoiTuong.equals("1")){
				this.doiTuong+= "-NCC";
			} else{
				this.doiTuong+= "-KH";
			}
			
			System.out.println("đối tượng:"+ this.doiTuong);
			
			// get erp_debit_details
			sql =" select CREDIT_FK, DIENGIAI, NONGUYENTE, NOVND, KHOANMUCCHIPHI_FK, TRUNGTAMCHIPHI_FK," +
				 " TAIKHOAN_FK, SOLUONG, DONGIA from ERP_CREDIT_DETAILS where CREDIT_FK= ?";
			PreparedStatement prek = this.db.getConnection().prepareStatement(sql);
			prek.setInt(1, Integer.parseInt(this.id));
			ResultSet rs1 = prek.executeQuery();
			List<String> listMoTa = new ArrayList<String>();
			List<Double> listNoNguyenTe = new ArrayList<Double>();
			List<Double> listNoVND = new ArrayList<Double>();
			List<String> listTaiKhoan = new ArrayList<String>();
			List<Double> listDonGia = new ArrayList<Double>();
			List<Integer> listSoLuong = new ArrayList<Integer>();
			
			/*List<String> listKhoanMucChiPhi = new ArrayList<String>();
			List<String> listTrungTamChiPhi = new ArrayList<String>();*/

			if(rs1!=null){
				while(rs1.next()){
					listTaiKhoan.add(rs1.getString("TAIKHOAN_FK"));
					listMoTa.add(rs1.getString("DIENGIAI"));
					listNoNguyenTe.add(rs1.getDouble("NONGUYENTE"));
					listNoVND.add(rs1.getDouble("NOVND"));
					listDonGia.add(rs1.getDouble("DONGIA"));
					listSoLuong.add(rs1.getInt("SOLUONG"));
				}
				rs1.close();
			}
			this.moTas = listMoTa;
			this.noNguyenTes = listNoNguyenTe;
			this.noVNDs = listNoVND;
			this.taiKhoans = listTaiKhoan;
			this.donGias = listDonGia;
			this.soLuongs = listSoLuong; 
			
			System.out.println("list taikhoan size:"+ listTaiKhoan.size()); 
			System.out.println("list taikhoan size:"+ this.taiKhoans.size());
			// get rs
			// bang true la khong thay doi theo ti gia
			this.init(this.tienTe, true);
			
		} catch ( Exception ex){
			ex.printStackTrace();
		}
	}
	@Override
	public void getTiGiaUSD(String tiente, boolean check) {
		// TODO Auto-generated method stub
		try{
			if(tiente.trim().length() ==0 ){
				tiente="100001";
				this.tienTe ="100001";
			}
			if( this.tigia <=0 || check == false){
				String sql =" select isnull(TIGIAQUYDOI,0) as TIGIAQUYDOI from ERP_TIGIA tg inner " +
							"	join ERP_TIENTE tt on tg.TIENTE_FK = tt.PK_SEQ " +
						    " where tt.PK_SEQ = '"+tiente+"' and tg.TuNgay like '"+getMonthPresent()+"' ";
				
				ResultSet rs = this.db.get(sql);
				if( rs!= null){
					if(rs.next()){
						this.tigia = this.round(rs.getDouble("TIGIAQUYDOI"),2);
					}
					rs.close();
				}
			}
		} catch ( Exception ex){
			ex.printStackTrace();
		}
	}
	
	private String getMonthPresent(){
			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String s = formatter.format(date);
			s= s.substring(0,7)+ "%";
			return s;
	}

}
