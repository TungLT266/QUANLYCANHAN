package geso.traphaco.erp.beans.kehoachsanxuatvamuahang;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.kehoachsanxuatvamuahang.imp.*;
import geso.traphaco.erp.beans.nhanhangnhapkhau.ISanpham;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KeHoachSanXuatVaMuaHang implements IKeHoachSanXuatVaMuaHang {

	private String nhaMay = "";
	private String queryDK = "";
	private String loaihangHoa;
	private String sanPham;
	private ResultSet rs;
	private dbutils db;
	private List<ISanPham> listSP;
	private List<ISanPham> listSPGiaCong;
	private List<ISanPham> listSPMuaHang;
	private String thang;
	private String nam;
	private ResultSet rsGiaCong;
	private ResultSet rsMuaHang;
	
	
	
	public List<ISanPham> getListSPGiaCong() {
		return listSPGiaCong;
	}
	public void setListSPGiaCong(List<ISanPham> listSPGiaCong) {
		this.listSPGiaCong = listSPGiaCong;
	}
	public List<ISanPham> getListSPMuaHang() {
		return listSPMuaHang;
	}
	public void setListSPMuaHang(List<ISanPham> listSPMuaHang) {
		this.listSPMuaHang = listSPMuaHang;
	}
	public String getNhaMay() {
		return nhaMay;
	}
	public void setNhaMay(String nhaMay) {
		this.nhaMay = nhaMay;
	}
	public String getQueryDK() {
		return queryDK;
	}
	public void setQueryDK(String queryDK) {
		this.queryDK = queryDK;
	}
	public String getLoaihangHoa() {
		return loaihangHoa;
	}
	public void setLoaihangHoa(String loaihangHoa) {
		this.loaihangHoa = loaihangHoa;
	}
	public String getSanPham() {
		return sanPham;
	}
	public void setSanPham(String sanPham) {
		this.sanPham = sanPham;
	}
	public ResultSet getRsGiaCong() {
		return rsGiaCong;
	}
	public void setRsGiaCong(ResultSet rsGiaCong) {
		this.rsGiaCong = rsGiaCong;
	}
	public ResultSet getRsMuaHang() {
		return rsMuaHang;
	}
	public void setRsMuaHang(ResultSet rsMuaHang) {
		this.rsMuaHang = rsMuaHang;
	}
	public ResultSet getRs() {
		return rs;
	}
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	public dbutils getDb() {
		return db;
	}
	public void setDb(dbutils db) {
		this.db = db;
	}
	public List<ISanPham> getListSP() {
		return listSP;
	}
	public void setListSP(List<ISanPham> listSP) {
		this.listSP = listSP;
	}
	public String getThang() {
		return thang;
	}
	public void setThang(String thang) {
		this.thang = thang;
	}
	public String getNam() {
		return nam;
	}
	public void setNam(String nam) {
		this.nam = nam;
	}
	public KeHoachSanXuatVaMuaHang(ResultSet rs, dbutils db,
			List<ISanPham> listSP, String thang, String nam) {
		super();
		this.rs = rs;
		this.db = db;
		this.listSP = listSP;
		this.thang = thang;
		this.nam = nam;
	}
	
	public KeHoachSanXuatVaMuaHang() {
		super();
		
		this.db = new dbutils();
		this.listSP = new ArrayList<ISanPham>();
		this.listSPGiaCong = new ArrayList<ISanPham>();
		this.listSPMuaHang = new ArrayList<ISanPham>();
		this.thang = "";
		this.nam = "";
		this.loaihangHoa = "";
		this.sanPham = "";
		this.queryDK = "";
		this.nhaMay = "";
	}
	//tạo danh sách sản phẩm sản xuất tại Traphaco
	public void setDieuKien(){
		if(!this.thang.equals("") && !this.nam.equals("")){
			queryDK = queryDK + " and MONTH(lsxdk.NGAYKETTHUC) = " + this.thang + " and YEAR(lsxdk.NGAYKETTHUC) = " + this.nam;
			
		}
		if(!this.nhaMay.equals("")){
			queryDK = queryDK + " and lsxdk.NHAMAY_FK = " + this.nhaMay;
		}
		if(!this.sanPham.equals("")){
			queryDK = queryDK + " and lsxdk.sanpham_fk = " + this.sanPham;
		}
	
	}
	
	public void createRS(){
		// tạo ds sản xuất
		String query = "select distinct  nm.tennhamay as TENSANPHAM, '' AS DONVI, 0 SOLUONG, '' AS SOLO, " + 
 "  '' AS GHICHU, '0' as loai, lsxdk.NHAMAY_FK as STT" + 
 "  from ERP_LENHSANXUATDUKIEN lsxdk" + 
 "  left join ERP_NHAMAY nm on nm.PK_SEQ = lsxdk.NHAMAY_FK " +
 " where lsxdk.loaihanghoa = '1' " + queryDK + 
 "  union " + 
 "  (select sp.TEN as TENSANPHAM, dvdl.DONVI AS DONVI, " + 
 "  SUM(distinct( lsxdk.SOLUONG)) SOLUONG, lsxdk.SOLO AS SOLO, " + 
 "  '' AS GHICHU, '1' as loai, lsxdk.NHAMAY_FK as STT" + 
 "  from ERP_LENHSANXUATDUKIEN lsxdk" + 
 "  left join ERP_SANPHAM sp on sp.PK_SEQ = lsxdk.SANPHAM_FK" + 
 "  left join DONVIDOLUONG dvdl on dvdl.PK_SEQ = sp.DVDL_FK" + 
 "  where lsxdk.loaihanghoa = '1'" + queryDK + 
 "  GROUP BY sp.TEN, lsxdk.SOLO , lsxdk.NHAMAY_FK, DVDL.DONVI)" + 
 "  order by STT,loai";
		
		System.out.println("---sanxuat: " + query);
		rs = db.get(query);
		xuly(listSP, rs);
		
		
	}
	
	//hàm lọc dữ liệu các sản phẩm về việc gom theo số lô cho 
	//cho quá trình sản xuất.
	public void xuly(List<ISanPham> listSp, ResultSet rs){
		List<ISanPham> list = new ArrayList<ISanPham>();
		String soloDau = "";
		String soloCuoi = "";
		int moc = 0;
		try {
			if(rs!= null){
				while(rs.next()){
					String loai = rs.getString("loai");
					
					if(loai.equals("0")){
						//khi các nhà máy có sản phẩm
						if(list.size() > 0){
							int[] flat = new int[list.size()];
							danhdau(flat);
							int kt = 0;
							long sl = 0;
							for(int i = 0; i< list.size() ; i++){
								if(flat[i] == 0){
									ISanPham sp = list.get(i);
									sl = Long.parseLong(list.get(i).getSoLuong());
									soloDau = list.get(i).getSoLo();
									soloCuoi = list.get(i).getSoLo();
									kt = 0;
									for(int j = i + 1; j< list.size(); j ++){
										if(list.get(i).getTenSanPham().equals(list.get(j).getTenSanPham())){
											soloCuoi = list.get(j).getSoLo();
											sl = sl + Long.parseLong(list.get(j).getSoLuong());
											kt = 1;
											flat[j] = 1;
										}
									}
									if(kt == 0)//không có sản phẩm trùng tên
									{
										String[] tachlocuoi = soloCuoi.split("-");
										String solo = "";
										if(tachlocuoi.length > 0){
											solo = tachlocuoi[0] + "/" + tachlocuoi[1];
										}
										else{
											solo = soloCuoi;
										}
										sp.setSoLo(solo);
										listSp.add(sp);
									}
									else{
										//có trường hợp trùng
										String[] tachlodau = soloDau.split("-");
										String[] tachlocuoi = soloCuoi.split("-");
										String solo = "";
										if(tachlodau.length > 0 && tachlocuoi.length > 0){
										 solo= tachlodau[0] + "-" + tachlocuoi[0] + "/" + tachlocuoi[1];
										}
										else{
											solo = soloDau + "-" + soloCuoi;
					
										}
										sp.setSoLo(solo);
										sp.setSoLuong(String.valueOf(sl));
										listSp.add(sp);
									
									}
								}
							}
						}
							
						ISanPham sp = new SanPham();
						sp.setTenSanPham(rs.getString("TENSANPHAM"));
						sp.setMaSanPham("");
						sp.setSoLuong("0");
						sp.setDonVi("");
						sp.setSoLo("");
						sp.setGhiChu("");
						sp.setLoai("0");
						listSp.add(sp);
						moc = 0;
						
						
						//tính từ móc 0, xét danh sách các sản phẩm của nhà máy
						
							list = new ArrayList<ISanPham>();
					}
					else{
						ISanPham sp = new SanPham();
						sp.setTenSanPham(rs.getString("TENSANPHAM"));
						sp.setMaSanPham("");
						sp.setLoai("1");
						sp.setSoLuong(rs.getString("SOLUONG"));
						sp.setDonVi(rs.getString("DONVI"));
						sp.setGhiChu("");
						sp.setSoLo(rs.getString("SOLO"));
						moc = 1;
						list.add(sp);
						
						
						
					}
					
				}
			
					//khi các nhà máy có sản phẩm
					if(list.size() > 0){
						int[] flat = new int[list.size()];
						danhdau(flat);
						int kt = 0;
						long sl = 0;
						for(int i = 0; i< list.size() ; i++){
							if(flat[i] == 0){
								ISanPham sp = list.get(i);
								sl = Long.parseLong(list.get(i).getSoLuong());
								soloDau = list.get(i).getSoLo();
								soloCuoi = list.get(i).getSoLo();
								kt = 0;
								for(int j = i + 1; j< list.size(); j ++){
									if(list.get(i).getTenSanPham().equals(list.get(j).getTenSanPham())){
										soloCuoi = list.get(j).getSoLo();
										sl = sl + Long.parseLong(list.get(j).getSoLuong());
										kt = 1;
										flat[j] = 1;
									}
								}
								if(kt == 0)//không có sản phẩm trùng tên
								{
									String[] tachlocuoi = soloCuoi.split("-");
									String solo = "";
									if(tachlocuoi.length > 0){
										solo = tachlocuoi[0] + "/" + tachlocuoi[1];
									}
									else{
										solo = soloCuoi;
									}
									sp.setSoLo(solo);
									listSp.add(sp);
								}
								else{
									//có trường hợp trùng
									String[] tachlodau = soloDau.split("-");
									String[] tachlocuoi = soloCuoi.split("-");
									String solo = "";
									if(tachlodau.length > 0 && tachlocuoi.length > 0){
									 solo= tachlodau[0] + "-" + tachlocuoi[0] + "/" + tachlocuoi[1];
									}
									else{
										solo = soloDau + "-" + soloCuoi;
				
									}
									sp.setSoLo(solo);
									sp.setSoLuong(String.valueOf(sl));
									listSp.add(sp);
								
								}
							}
						}
					}
						
				}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
	}
	
	//hàm xử lý gom lô cho gia công vs mua hàng
	public void xuly1(List<ISanPham> listSp, ResultSet rs){
		List<ISanPham> list = new ArrayList<ISanPham>();
		String soloDau = "";
		String soloCuoi = "";
		int moc = 0;
		try {
			if(rs!= null){
				while(rs.next()){
					
					String loai = rs.getString("loai");
					ISanPham sp = new SanPham();
					sp.setTenSanPham(rs.getString("TENSANPHAM"));
					sp.setMaSanPham("");
					sp.setLoai("1");
					sp.setSoLuong(rs.getString("SOLUONG"));
					sp.setDonVi(rs.getString("DONVI"));
					sp.setGhiChu("");
					sp.setSoLo(rs.getString("SOLO"));
					moc = 1;
					list.add(sp);
				}
				//tính từ móc 0, xét danh sách các sản phẩm của nhà máy
				//khi các nhà máy có sản phẩm
				if(list.size() > 0){
					int[] flat = new int[list.size()];
					danhdau(flat);
					int kt = 0;
					long sl = 0;
					for(int i = 0; i< list.size() ; i++){
						if(flat[i] == 0){
							ISanPham sp = list.get(i);
							sl = Long.parseLong(list.get(i).getSoLuong());
							soloDau = list.get(i).getSoLo();
							soloCuoi = list.get(i).getSoLo();
							kt = 0;
							for(int j = i + 1; j< list.size(); j ++){
								if(list.get(i).getTenSanPham().equals(list.get(j).getTenSanPham())){
									soloCuoi = list.get(j).getSoLo();
									sl = sl + Long.parseLong(list.get(j).getSoLuong());
									kt = 1;
									flat[j] = 1;
								}
							}
							if(kt == 0)//không có sản phẩm trùng tên
							{
								String[] tachlocuoi = soloCuoi.split("-");
								String solo = "";
								if(tachlocuoi.length > 0){
									solo = tachlocuoi[0] + "/" + tachlocuoi[1];
								}
								else{
									solo = soloCuoi;
								}
								sp.setSoLo(solo);
								listSp.add(sp);
							}
							else{
								//có trường hợp trùng
								String[] tachlodau = soloDau.split("-");
								String[] tachlocuoi = soloCuoi.split("-");
								String solo = "";
								if(tachlodau.length > 0 && tachlocuoi.length > 0){
								 solo= tachlodau[0] + "-" + tachlocuoi[0] + "/" + tachlocuoi[1];
								}
								else{
									solo = soloDau + "-" + soloCuoi;
			
								}
								sp.setSoLo(solo);
								sp.setSoLuong(String.valueOf(sl));
								listSp.add(sp);
							
							}
						}
					}
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
	}
	
	//gán cờ
	public void danhdau(int[] flat){
		for(int i = 0; i < flat.length; i++){
			flat[i] = 0;
		}
	}
	public boolean kttrung(List<ISanPham> list, ISanPham sp){
		for( int i = 0; i < list.size() ; i++){
			if(list.get(i).getTenSanPham().equals(sp.getTenSanPham())){
				return true;
			}
		}
		return false;
	}
	//tạo danh sách sản phẩm gia công
	public void createRSGiaCong(){
		// tạo ds sản xuất
		String query = " select sp.TEN as TENSANPHAM, dvdl.DONVI AS DONVI, " + 
 "   SUM(isnull( lsxdk.SOLUONG,0)) SOLUONG, lsxdk.SOLO AS SOLO," + 
 "  '' AS GHICHU, '1' as loai, lsxdk.NHAMAY_FK as STT" + 
 "   from ERP_LENHSANXUATDUKIEN lsxdk" + 
 "  left join ERP_SANPHAM sp on sp.PK_SEQ = lsxdk.SANPHAM_FK" + 
 "   left join DONVIDOLUONG dvdl on dvdl.PK_SEQ = sp.DVDL_FK" + 
 "  where lsxdk.loaihanghoa = '2' " + queryDK +  
 "   GROUP BY sp.TEN, lsxdk.SOLO , lsxdk.NHAMAY_FK, DVDL.DONVI";
		
		System.out.println("---gia công: " + query);
		this.rsGiaCong = db.get(query);
		xuly1(listSPGiaCong, rsGiaCong);
		
	}
	
	//tạo danh sách sản phẩm sản xuất mua hàng
	public void createRSMuaHang(){
		// tạo ds sản xuất
		String query = "select sp.TEN as TENSANPHAM, dvdl.DONVI AS DONVI, " + 
 "   SUM(isnull( lsxdk.SOLUONG,0)) SOLUONG, lsxdk.SOLO AS SOLO," + 
 "  '' AS GHICHU, '1' as loai, lsxdk.NHAMAY_FK as STT" + 
 "   from ERP_LENHSANXUATDUKIEN lsxdk" + 
 "  left join ERP_SANPHAM sp on sp.PK_SEQ = lsxdk.SANPHAM_FK" + 
 "   left join DONVIDOLUONG dvdl on dvdl.PK_SEQ = sp.DVDL_FK" + 
 "  where lsxdk.loaihanghoa = '0' " + queryDK +  
 "   GROUP BY sp.TEN, lsxdk.SOLO , lsxdk.NHAMAY_FK, DVDL.DONVI";
		
		System.out.println("---mua hang: " + query);
		this.rsMuaHang = db.get(query);
		xuly1(listSPMuaHang, rsMuaHang);

	}
	
}
