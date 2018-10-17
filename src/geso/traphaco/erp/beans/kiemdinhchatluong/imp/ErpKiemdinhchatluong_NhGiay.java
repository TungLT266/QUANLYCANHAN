package geso.traphaco.erp.beans.kiemdinhchatluong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.beans.thongtinsanpham.ITieuchikiemdinh;
import geso.traphaco.center.beans.thongtinsanpham.imp.Tieuchikiemdinh;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.kiemdinhchatluong.IErpKiemdinhchatluong_NhGiay;
import geso.traphaco.erp.beans.kiemdinhchatluong.IErpSanphamduyet;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.IErpHoso;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.imp.ErpHoso;
import geso.traphaco.erp.util.Library;

public class ErpKiemdinhchatluong_NhGiay implements IErpKiemdinhchatluong_NhGiay
{
	String userId;
	String ycId;

	String spId;
	String spTen;

	String nhId;
	String nccId;
	String solo;
	String sohoadon;
	
	
	String SoLuongKiemDinh, soluongDat, datCl;
	String Tongsoluongnhap;
	String Soluongchuakiem;
	String congtyId, dinhluong, dinhtinh, trangthai, ngaykiem,ngaynhanhang,nhacungcap;
	String[] ketqua_dinhtinh, ghinhan_dinhtinh, tieuchi_dinhtinh, nguoisua_dinhtinh;
	String msg;
	String Iscapdong;
	ResultSet rsYeuCauKiemDinh, soloRs, khoRs, rsSanPham, rsLoaiSanPham;
	List<ITieuchikiemdinh> tckdList;

	List<IErpHoso> listhoso;
	List<IErpSanphamduyet> SpDuyetList;

	String denghixuly = "";
	String Soluongmau="0";
	dbutils db;

	String Thieuhoso;
	String ngaysanxuat, ngayhethan, ghichu, khoId, LOAISPID, loai, kyhieukd;
	// tao them 2 resultset lay thong tin hoa cgat san pham va may moc san pham
	ResultSet rshcsp, rsmmsp;
	// lấy các thùng để hiển thị ra kiểm định
	ResultSet rsSoThung;
	// dùng để lưu lại các đối tượng thùng
	String[] listSoThung;
	String[] listSoLuongThung;
	String[] listLayMauThung;
	String[] listDatThung;
	String[] listKhongDatThung;
	String[] listHamAm;
	String[] listHamLuong;

	// Thêm hàm ẩm và hàm lượng 15/06/2016
	double hamAm;
	double hamLuong;

	String loaimh;

	String maPhieu;
	public ErpKiemdinhchatluong_NhGiay()
	{
		this.userId = "";
		this.ycId = "";
		this.spId = "";
		this.spTen = "";
		this.nhId = "";
		this.nccId="";
		this.solo = "";
		this.SoLuongKiemDinh = "";
		this.soluongDat="";
		this.datCl="";
		this.msg = "";
		this.congtyId="";
		this.trangthai="";
		this.ngaykiem="";
		this.ngaysanxuat="";
		this.ngaynhanhang="";
		this.dinhluong="";
		this.dinhtinh="";
		this.Soluongmau="0";
		this.nhacungcap="";
		Tongsoluongnhap="0";
		Soluongchuakiem="0";
		this.Thieuhoso="0";
		this.tckdList = new ArrayList<ITieuchikiemdinh>();
		this.listhoso= new ArrayList<IErpHoso>();

		SpDuyetList= new ArrayList<IErpSanphamduyet>();
		this.db = new dbutils();
		Iscapdong="";

		this.tieuchi_dinhtinh = new String[0];
		this.ketqua_dinhtinh = new String[0];
		this.nguoisua_dinhtinh = new String[0];
		this.ghinhan_dinhtinh = new String[0];
		this.ngayhethan = "";
		this.khoId = "";
		this.ghichu = "";
		this.LOAISPID = "";
		this.loai = "";
		this.kyhieukd = "";
		this.loaimh = "";
		this.hamAm = 0;
		this.hamLuong = 0;
		this.maPhieu = "";
	}

	public ErpKiemdinhchatluong_NhGiay(String id)
	{
		this.ycId = id;
		this.userId = "";
		this.spId = "";
		this.spTen = "";
		this.nhId = "";
		this.nccId="";
		Tongsoluongnhap="0";
		Soluongchuakiem="0";
		this.solo = "";
		this.SoLuongKiemDinh = "";
		this.soluongDat="";
		this.datCl="";
		this.msg = "";
		this.congtyId="";
		this.trangthai="";
		this.ngaykiem="";
		this.ngaysanxuat="";
		this.ngaynhanhang="";
		this.dinhluong="";
		this.dinhtinh="";
		this.Soluongmau="0";
		this.nhacungcap="";
		this.Thieuhoso="0";
		this.ngayhethan = "";
		this.tckdList = new ArrayList<ITieuchikiemdinh>();
		this.tieuchi_dinhtinh = new String[0];
		this.ketqua_dinhtinh = new String[0];
		this.nguoisua_dinhtinh = new String[0];
		this.ghinhan_dinhtinh = new String[0];

		this.listhoso= new ArrayList<IErpHoso>();
		this.db = new dbutils();
		Iscapdong="";
		SpDuyetList= new ArrayList<IErpSanphamduyet>();
		this.khoId = "";
		this.ghichu = "";
		this.LOAISPID = "";
		this.loai = "";
		this.kyhieukd = "";
		this.loaimh = "";
		this.hamAm = 0;
		this.hamLuong = 0;
		this.maPhieu = "";
	}

	public String[] getListHamAm() {
		return listHamAm;
	}

	public void setListHamAm(String[] listHamAm) {
		this.listHamAm = listHamAm;
	}

	public String[] getListHamLuong() {
		return listHamLuong;
	}

	public void setListHamLuong(String[] listHamLuong) {
		this.listHamLuong = listHamLuong;
	}

	public String getMaPhieu() {
		return maPhieu;
	}

	public void setMaPhieu(String maPhieu) {
		this.maPhieu = maPhieu;
	}

	public double getHamAm() {
		return hamAm;
	}

	public void setHamAm(double hamAm) {
		this.hamAm = hamAm;
	}

	public double getHamLuong() {
		return hamLuong;
	}

	public void setHamLuong(double hamLuong) {
		this.hamLuong = hamLuong;
	}

	public String[] getListSoThung() {
		return listSoThung;
	}

	public void setListSoThung(String[] listSoThung) {
		this.listSoThung = listSoThung;
	}

	public String[] getListSoLuongThung() {
		return listSoLuongThung;
	}

	public void setListSoLuongThung(String[] listSoLuongThung) {
		this.listSoLuongThung = listSoLuongThung;
	}

	public String[] getListLayMauThung() {
		return listLayMauThung;
	}

	public void setListLayMauThung(String[] listLayMauThung) {
		this.listLayMauThung = listLayMauThung;
	}

	public String[] getListDatThung() {
		return listDatThung;
	}

	public void setListDatThung(String[] listDatThung) {
		this.listDatThung = listDatThung;
	}

	public String[] getListKhongDatThung() {
		return listKhongDatThung;
	}

	public void setListKhongDatThung(String[] listKhongDatThung) {
		this.listKhongDatThung = listKhongDatThung;
	}

	public ResultSet getRsSoThung() {
		return rsSoThung;
	}

	public void setRsSoThung(ResultSet rsSoThung) {
		this.rsSoThung = rsSoThung;
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getYcId() 
	{
		return this.ycId;
	}

	public void setYcId(String ycId) 
	{
		this.ycId = ycId;
	}

	public String getSpId() 
	{
		return this.spId;
	}

	public void setSpId(String spId) 
	{
		this.spId = spId;
	}

	public String getSpTen() 
	{
		return this.spTen;
	}

	public void setSpTen(String spTen)
	{
		this.spTen = spTen;
	}

	public String getSolo() 
	{
		return this.solo;
	}

	public void setSolo(String solo) 
	{
		this.solo = solo;
	}


	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getKhoId() 
	{
		return this.khoId;
	}

	public void setKhoId(String khoId)
	{
		this.khoId = khoId;
	}

	public String getGhichu() 
	{
		return this.ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}

	public String getLoai() 
	{
		return this.loai;
	}

	public void setLoai(String loai)
	{
		this.loai = loai;
	}

	public void setTieuchikiemdinhList(List<ITieuchikiemdinh> list) 
	{
		this.tckdList = list;
	}

	public List<ITieuchikiemdinh> getTieuchikiemdinhList() 
	{
		return this.tckdList;
	}
	
	
	public String getSohoadon() {
		return sohoadon;
	}

	public void setSohoadon(String sohoadon) {
		this.sohoadon = sohoadon;
	}


	private boolean updateSoLuongThung(){
		try{
			for(int i=0; i< listSoThung.length; i++){
				String query =	" update ERP_YEUCAUKIEMDINH_THUNG set SOLUONGDAT= "+this.listDatThung[i]+
				", SOLUONGKHONGDAT = "+this.listKhongDatThung[i]+", SOLUONGMAU = "+this.listLayMauThung[i]+" " +
				", HAMAM= "+ this.listHamAm[i] +", HAMLUONG= "+this.listHamLuong[i] +
				" where YEUCAUKIEMDINH_FK = "+this.ycId+" and MATHUNG= '"+this.listSoThung[i]+"'";

				int k = this.db.updateReturnInt(query);
				if( k !=1){
					this.db.getConnection().rollback();
					return false;
				}

			}
			return true;
		} catch (Exception e){
			e.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}

	}
	public boolean updateKiemdinh(HttpServletRequest request)
	{


		if(!checkValid())
		{
			return false;
		}		
		try 
		{
			db.getConnection().setAutoCommit(false);
			//Truoc khi ghi log-- xoa log cu trong ngay cua nguoi su dung va ghi log moi
			String query = "Delete from Erp_YeuCauKiemDinh_TieuChi_Log where YeuCauKiemDinh_FK="+this.ycId+" AND NguoiSua = '" + this.userId + "' And NgaySua = '" + getDateTime() + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật Erp_YeuCauKiemDinh_TieuChi_Log " + query;
				db.getConnection().rollback();
				return false;
			}

			query=  " insert into Erp_YeuCauKiemDinh_TieuChi_Log(yeucaukiemdinh_fk, tieuchi, pheptoan, giatrichuan, GhiNhan, Dat, QuyetDinh, NguoiSua, NgaySua, DinhLuong, DinhTinh)" +
			" select t.yeucaukiemdinh_fk, t.tieuchi, t.pheptoan, t.giatrichuan, t.GhiNhan, t.Dat, t.Dat, '" + this.userId + "','"+getDateTime()+"',t.DinhLuong,t.DinhTinh " +
			" from ERP_YeuCauKiemDinh k "+
			" inner join ERP_YeuCauKiemDinh_TieuChi t on t.yeucaukiemdinh_fk=k.pk_seq "+
			" where k.pk_seq='" + this.ycId + "'";

			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh_TieuChi " + query;
				db.getConnection().rollback();
				return false;
			}

			//System.out.println("::::::::::::::>Chen thong tin kiem dinh cu vao bang log ::"+query);
			query="DELETE FROM ERP_YeuCauKiemDinh_TieuChi WHERE YEUCAUKIEMDINH_FK = '" + this.ycId + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh_TieuChi " + query;
				db.getConnection().rollback();
				return false;
			}

			//System.out.println("::::::::::::::>Xoa Tieu Chi kiem dinh ::"+query);
			for(int i = 0; i < this.tckdList.size(); i++)
			{
				ITieuchikiemdinh tckd = this.tckdList.get(i);

				query =        " insert ERP_YeuCauKiemDinh_TieuChi(yeucaukiemdinh_fk, tieuchi, pheptoan, giatrichuan, diemdat, dat, trangthai, DinhLuong) " +
				" values('" + this.ycId + "', N'" + tckd.getTieuchi() + "', '" + tckd.getToantu() + "', '" + tckd.getGiatrichuan() + "', '" + tckd.getDiemdat() + "', '" + tckd.getQuyetdinh() + "', N'" + tckd.getTrangthai() + "',1)";

				//System.out.println("Chen YEUCAU: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh_TieuChi " + query;
					db.getConnection().rollback();
					return false;
				}
				//System.out.println("::::::::::::::>Dinh Luong::"+query);

				query = " update Erp_YeuCauKiemDinh_TieuChi_Log set QuyetDinh = '" + tckd.getQuyetdinh() + "' " +
				" where yeucaukiemdinh_fk = '" + this.ycId + "' and  tieuchi = N'" + tckd.getTieuchi() + "' " +
				" and NgaySua = '" + getDateTime() + "'  and NguoiSua = '" + this.userId + "' And DinhLuong=1";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh_TieuChi " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			// chỗ này cập nhật lại số lượng/ số lượng đạt/ hỏng của từng thùng.

			/*boolean check =  UpdateMaPhieuKhoChiTiet();
			if(check == false){
				this.msg = "Không thể cập nhật mã phiếu kho ChiTiet";
				this.db.getConnection().rollback();
				return false;
			}*/
			boolean check = this.updateSoLuongThung();
			if(check == false){
				this.msg = "Không thể cập nhật so luong theo thung " + query;
				this.db.getConnection().rollback();
				return false;
			}


			if(this.tieuchi_dinhtinh != null && this.tieuchi_dinhtinh.length > 0)
			{
				for(int i=0; i < this.tieuchi_dinhtinh.length; i++)
				{	
					query = " insert ERP_YeuCauKiemDinh_TieuChi(yeucaukiemdinh_fk, tieuchi, GhiNhan, Dat, trangthai, DinhTinh) " +
					" select '" + this.ycId + "', N'" + this.tieuchi_dinhtinh[i] + "', N'" + this.ghinhan_dinhtinh[i]+"','" + this.ketqua_dinhtinh[i]+"',Case when '"+this.ketqua_dinhtinh[i]+"'='1' then N'Đạt'else N'Không đạt' END, 1"; 

					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh_TieuChi " + query;
						db.getConnection().rollback();
						return false;
					}
					//System.out.println("::::::::::::::>Dinh Tinh::"+query);
					query="update Erp_YeuCauKiemDinh_TieuChi_Log set QuyetDinh='"+this.ketqua_dinhtinh[i]+"' where yeucaukiemdinh_fk='"+this.ycId+"' and  tieuchi=N'"+this.tieuchi_dinhtinh[i]+"' and NgaySua='"+getDateTime()+"' and NguoiSua='"+this.userId+"' and DinhTinh=1";
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh_TieuChi " + query;
						db.getConnection().rollback();
						return false;
					}
					//System.out.println(":::::::::::::>Dinh Tinh Log::"+query);
				}
			}

			String dat = this.soluongDat.trim().length() <= 0 ? "0" : this.soluongDat.trim();

			// trước khi cập nhật phiếu kiểm định, thì cập nhật xuống tồn kho chitiet trước.


			query = " update ERP_YeuCauKiemDinh set  THIEUHOSO='"+this.Thieuhoso+"',nguoisua = '" + this.userId + "', ngaysua = '" + getDateTime() 
			+ "' , soluongDat = '" + dat + "', soluongmau="+this.Soluongmau+" " +
			" , denghixuly = N'" + denghixuly + "', KYHIEUMAU =N'" +this.kyhieukd+"', MAPHIEU =N'"+this.maPhieu+"' " +
			" where pk_seq = '" + this.ycId + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
				db.getConnection().rollback();
				return false;
			}

			query=" update ERP_YeuCauKiemDinh set ngaykiem='"+this.ngaykiem+"' where trangthai='0'  and pk_seq="+this.ycId;
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
				db.getConnection().rollback();
				return false;
			}

			query=" delete  ERP_YEUCAUKIEMDINH_HOSO  where YEUCAUKIEMDINH_FK ="+this.ycId ;
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
				db.getConnection().rollback();
				return false;
			}

			 
			for(int i=0;i<listhoso.size();i++){
				IErpHoso hs=listhoso.get(i);
				query=" insert into ERP_YEUCAUKIEMDINH_HOSO (YEUCAUKIEMDINH_FK,HOSO) values ("+this.ycId+",N'"+hs.getHoso()+"') ";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
					db.getConnection().rollback();
					return false;
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception e) 
		{
			//System.out.println("5.Exception: " + e.getMessage());
			e.printStackTrace();
			db.update("rollback");
			return false;
		}
	}

 
	
	public void init() 
	{
		String query=	" SELECT isnull(hd.sohoadon,'')  as spSohoadon, isnull(a.iscapdong,'0') as iscapdong,	ISNULL(THIEUHOSO,'0')  AS THIEUHOSO  ,ISNULL(A.SOLUONGMAU,0) AS SOLUONGMAU  ,A.PK_SEQ, D.PREFIX + '' + B.PREFIX + CAST(A.NHANHANG_FK AS VARCHAR(10) )  AS SONHAHANG, "+ 
						"\n C.PK_SEQ AS SPID, ISNULL(C.MA, ISNULL(C.MA, '')) + ' - ' + C.TEN +  '(' + ISNULL(DVDL.DONVI, '') + ')' AS SPTEN, "+ 
						"\n ISNULL(A.SOLUONGDAT, 0) AS SOLUONGDAT, A.SOLO, A.TRANGTHAI,  "+
						"\n ISNULL((SELECT SUM(SOLUONGDUYET) FROM ERP_KIEMDINHCHATLUONG_LANDUYET D WHERE D.YEUCAUKIEMDINH_FK=A.PK_SEQ),0) AS SOLUONGDADUYET, "+
						"\n A.SOLUONG AS TONGSOLUONGNHAP , ISNULL(A.DATCHATLUONG, 0) AS DATCHATLUONG,  "+
						"\n ISNULL(A.DINHLUONG, 0) AS DINHLUONG, ISNULL(A.DINHTINH, 0) AS DINHTINH, ISNULL(A.NGAYKIEM,'') AS NGAYKIEM,  isnull(A.MAPHIEU,'') as MAPHIEU, " +
						"\n ISNULL(kiemtradinhluong,'0') as kiemtradinhluong ,isnull(kiemtradinhtinh,'0') as kiemtradinhtinh, " +
						"\n ISNULL(B.NGAYNHAN,'') AS NGAYNHAN,ISNULL(A.NGAYSANXUAT,'') AS NGAYSANXUAT,ISNULL(A.NGAYHETHAN,'') AS NGAYHETHAN," +
						"\n ISNULL(NCC.TEN,KH.TEN) AS nhacungcap,NCC.PK_SEQ as ncc_fk, ISNULL(A.DENGHIXULY, '') AS DENGHIXULY, isnull(A.KYHIEUMAU,'') KYHIEUMAU,A.HAMAM, A.HAMLUONG " +
						"\n FROM ERP_YEUCAUKIEMDINH A   " +
						"\n INNER JOIN ERP_NHANHANG B ON A.NHANHANG_FK = B.PK_SEQ "+   
						"\n left JOIN ERP_NHACUNGCAP NCC ON b.NCC_KH_FK = NCC.PK_SEQ " +
						"\n left join DONTRAHANG dth on dth.PK_SEQ=B.trahang_fk "+
						"\n left join ERP_KHACHHANG kh on kh.PK_SEQ=KHACHHANG_FK	 " +
						"\n INNER JOIN ERP_SANPHAM C ON A.SANPHAM_FK = C.PK_SEQ   "+
						"\n INNER JOIN ERP_DONVITHUCHIEN D ON B.DONVITHUCHIEN_FK = D.PK_SEQ "+   
						"\n LEFT JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = C.DVDL_FK "+ 
						"\n left join ERP_HOADONNCC hd on hd.pk_seq= b.hdncc_fk " +
						" WHERE A.PK_SEQ ="+ this.ycId ;

		ResultSet rs = db.get(query);
		System.out.println("Vào đây query : "+ query);
		try 
		{
			if(rs.next())
			{

				System.out.println("Vào đây "+ rs.getString("spTen"));
				this.nhId = rs.getString("sonhahang");
				this.sohoadon= rs.getString("spSohoadon");
				this.spTen = rs.getString("spTen");
				this.spId = rs.getString("spId");
				this.solo = rs.getString("solo").trim();
				this.nccId = rs.getString("ncc_fk");
				this.Thieuhoso=rs.getString("THIEUHOSO");
				this.datCl = rs.getString("DatChatLuong");
				this.trangthai = rs.getString("TrangThai").trim();
				this.ngaykiem = rs.getString("NgayKiem").trim().length() <= 0 ? getDateTime() : rs.getString("NgayKiem").trim();
				this.ngaysanxuat = rs.getString("NGAYSANXUAT");
				this.ngayhethan = rs.getString("NGAYHETHAN");
				this.ngaynhanhang = rs.getString("NGAYNHAN");
				this.kyhieukd = rs.getString("KYHIEUMAU");
				if(this.trangthai.equals("0")){
					this.dinhluong = rs.getString("kiemtradinhluong");
					this.dinhtinh = rs.getString("kiemtradinhtinh");
				}else{
					this.dinhluong = rs.getString("DinhLuong");
					this.dinhtinh = rs.getString("DinhTinh");
				}
				this.denghixuly = rs.getString("denghixuly");
				this.nhacungcap = rs.getString("nhacungcap");
				this.Tongsoluongnhap=rs.getString("TONGSOLUONGNHAP");
				this.Soluongchuakiem=""+( rs.getDouble("TONGSOLUONGNHAP")-rs.getDouble("SOLUONGDADUYET"));
				this.SoLuongKiemDinh=this.Soluongchuakiem;
				if(this.trangthai.equals("0")){
					this.Soluongmau=rs.getString("SOLUONGMAU");
					this.soluongDat =rs.getString("SOLUONGDAT");
				}else{
					this.Soluongmau="0";
					this.soluongDat ="0";
				}

				this.Iscapdong=rs.getString("iscapdong");

				// Thêm hàm ẩm, hàm lượng
				this.maPhieu = rs.getString("MAPHIEU");

			}
			rs.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}


		if(this.listhoso==null || this.listhoso.size()==0){


			query=" select hoso from  ERP_YEUCAUKIEMDINH_HOSO where YEUCAUKIEMDINH_FK ="+this.ycId;
			try{
				rs =db.get(query);
				while(rs.next()){
					IErpHoso  hoso=new ErpHoso();
					hoso.setHoso(rs.getString("hoso"));
					hoso.setYcId(this.ycId);
					this.listhoso.add(hoso);
				}
				rs.close();

			}catch(Exception er){
				er.printStackTrace();
			}

		}



		createRs();

		if(this.dinhluong.equals("1"))
		{

			System.out.println("nha cc id :"+ nccId);


			query = "select count(tieuchi) as sodong from ERP_YeuCauKiemDinh_TieuChi where yeucaukiemdinh_fk = '" + this.ycId + "' and DinhLuong = 1";
			System.out.println("1.Check: " + query);

			ResultSet rsCheck = db.get(query);
			boolean flag = false;	// dung de kiem tra xem da kiem dinh xong chua
			if (rsCheck != null)
			{
				try 
				{
					if (rsCheck.next())
					{
						if (rsCheck.getString("sodong").equals("0"))
							flag = true;
						rsCheck.close();
					}
				} 
				catch (Exception e) {e.printStackTrace();}
			}




			String sql = "";

			if(flag)
			{
				// kiem tra tieu chi kiem dinh theo nha cung cap
				query=" select *  from SANPHAM_TIEUCHIKIEMDINH where loai=0 and sanpham_fk="+spId+" and NCC_FK  ="+this.nccId;
				ResultSet rs1=db.get(query);
				System.out.println("cau ne :" + query);
				try{
					if(rs1.next()){
						sql = " select TieuChi, PhepToan, GiaTriChuan, '' as DiemDat, '0' as Dat, '0' as QuyetDinh, '' as TrangThai, ''  as NguoiSua " +
						" from  SanPham_TieuChiKiemDinh cd where cd.sanpham_fk = '" + this.spId + "' and loai = '0' and ncc_fk="+this.nccId;
					}else{

						sql = " select TieuChi, PhepToan, GiaTriChuan, '' as DiemDat, '0' as Dat, '0' as QuyetDinh, '' as TrangThai, ''  as NguoiSua " +
						" from  SanPham_TieuChiKiemDinh cd where cd.sanpham_fk = '" + this.spId + "' and loai = '0' and ncc_fk is null";
					}
				}catch(Exception er){
					System.out.println("116.Loi.....: " + er.toString());
				}
			}

			else
			{
				sql =" select a.TieuChi, a.PhepToan, a.GiaTriChuan, a.DiemDat, a.Dat, isnull(b.QuyetDinh, a.dat) as QuyetDinh, isnull(a.TrangThai,'') as TrangThai, " +
				"c.TEN + '--' + b.NgaySua  as NguoiSua " +
				"from erp_yeucaukiemdinh_tieuchi a "+
				"left join "+ 
				"( "+
				"	select * "+
				"	from Erp_YeuCauKiemDinh_TieuChi_Log "+
				"	where NgayGio = (select max(NgayGio) from Erp_YeuCauKiemDinh_TieuChi_Log where YeuCauKiemDinh_FK = '" + this.ycId + "' and DinhLuong = '1') and YeuCauKiemDinh_FK = '" + this.ycId + "' and DinhLuong = '1' "+
				") "+
				"b on b.YeuCauKiemDinh_FK = a.yeucaukiemdinh_fk and a.tieuchi = b.TieuChi " +
				"left join NHANVIEN c on c.PK_SEQ=b.NguoiSua where a.YeuCauKiemDinh_FK = '" + this.ycId + "' and a.DinhLuong=1";
			}
			System.out.println("2.Khoi tao tieu chi dinh luong: " + sql);

			List<ITieuchikiemdinh> tckdList = new ArrayList<ITieuchikiemdinh>();
			rs = db.get(sql);

			if (rs != null)
			{
				try
				{
					while (rs.next())
					{
						ITieuchikiemdinh tckd = new Tieuchikiemdinh();

						tckd.setTieuchi(rs.getString("tieuchi"));
						tckd.setToantu(rs.getString("pheptoan"));
						tckd.setGiatrichuan(rs.getString("giatrichuan"));
						//if(rs.getString("diemdat").length() <= 0 )
						//	tckd.setDiemdat(rs.getString("giatrichuan"));
						//else
						tckd.setDiemdat(rs.getString("diemdat"));
						tckd.setDat(rs.getString("dat"));
						tckd.setTrangthai(rs.getString("trangthai"));
						tckd.setQuyetdinh(rs.getString("QuyetDinh"));
						tckd.setNguoiSua(rs.getString("nguoisua"));
						tckdList.add(tckd);
					}
					rs.close();
				}
				catch (Exception e)
				{
					System.out.println("116.Loi.....: " + e.toString());
				}

				this.tckdList = tckdList;
			}
			if(this.tckdList.size() <= 0 && flag == true)
			{
				sql = " select TieuChi, PhepToan, GiaTriChuan, '' as DiemDat, '0' as Dat, '0' as QuyetDinh, '' as TrangThai, ''  as NguoiSua " +
				"from  SanPham_TieuChiKiemDinh cd where cd.sanpham_fk = '" + this.spId + "' and loai = '0' ";
				rs = db.get(sql);

				if (rs != null)
				{
					try
					{
						while (rs.next())
						{
							ITieuchikiemdinh tckd = new Tieuchikiemdinh();

							tckd.setTieuchi(rs.getString("tieuchi"));
							tckd.setToantu(rs.getString("pheptoan"));
							tckd.setGiatrichuan(rs.getString("giatrichuan"));
							/*if(rs.getString("diemdat").length() <= 0 )
								tckd.setDiemdat(rs.getString("giatrichuan"));
							else*/
							tckd.setDiemdat(rs.getString("diemdat"));
							tckd.setDat(rs.getString("dat"));
							tckd.setTrangthai(rs.getString("trangthai"));
							tckd.setQuyetdinh(rs.getString("QuyetDinh"));
							tckd.setNguoiSua(rs.getString("nguoisua"));
							tckdList.add(tckd);
						}
						rs.close();
					}
					catch (Exception e)
					{
						System.out.println("116.Loi.....: " + e.toString());
					}

					this.tckdList = tckdList;
				}

			}

			/*if(this.tckdList.size() <= 0)
			{
				this.msg = "Sản phẩm ( " + spTen + " ) chưa thiết lập tiêu chí kiểm định. Vui lòng kiểm tra lại dừ liệu nền." ;
			}*/
		}

		/************************************************************************
		 * 	Dinh tinh
		 * 
		 * 
		 * 
		 * 
		 **************************************************************************/

		if(this.dinhtinh.equals("1"))
		{
			// xy ly them nha cung cho nay
			query = "select count(c.TieuChi) as sodong from  ERP_YeuCauKiemDinh_TieuChi c where c.YeuCauKiemDinh_FK = '" + this.ycId + "' and c.DinhTinh = 1 ";
			System.out.println("1.Check dinh tinh: " + query);
			ResultSet rsCheck = db.get(query);
			boolean flag = false;
			int numb = 0;
			if (rsCheck != null)
			{
				try 
				{
					if (rsCheck.next())
					{
						numb = rsCheck.getInt("sodong");
						if (numb == 0)
							flag = true;
						rsCheck.close();
					}
				} 
				catch (Exception e) {e.printStackTrace();}
			}

			if(flag ==true){
				query = " select count(*) as sodong " +
				" from  SanPham_TieuChiKiemDinh cd where cd.sanpham_fk = " + this.spId + " and loai = '1' and ncc_fk="+this.nccId+"";


				rsCheck = db.get(query);
				if (rsCheck != null)
				{
					try 
					{
						if (rsCheck.next())
						{
							numb = rsCheck.getInt("sodong");
							rsCheck.close();

							if(numb==0){
								query = " select count(*) as sodong " +
								" from  SanPham_TieuChiKiemDinh cd where cd.sanpham_fk = '" + this.spId + "' and loai = '1' and ncc_fk is null ";
								ResultSet rs1=db.get(query);
								if(rs1.next()){
									numb= rs1.getInt("sodong");
								}
								rs1.close();
							}
						}
					} 
					catch (Exception e) { e.printStackTrace(); }
				}
			}
			this.tieuchi_dinhtinh = new String[numb];
			this.ketqua_dinhtinh = new String[numb];
			this.nguoisua_dinhtinh = new String[numb];
			this.ghinhan_dinhtinh = new String[numb];

			String sql = "";
			if(flag)
			{

				query=" select *  from SANPHAM_TIEUCHIKIEMDINH where loai=1 and sanpham_fk="+spId+" and NCC_FK  ="+this.nccId;
				ResultSet rs1=db.get(query);
				try{
					if(rs1.next()){
						// kiem tra tieu chi kiem dinh theo nha cung cap
						sql = " select TieuChi,'' as GhiNhan, '0' as QuyetDinh, '' as TrangThai, '' as NguoiSua " +
						" from  SanPham_TieuChiKiemDinh cd where cd.sanpham_fk = '" + this.spId + "' and loai = 1  and ncc_fk="+this.nccId;
					}else{
						sql = " select TieuChi,'' as GhiNhan, '0' as QuyetDinh, '' as TrangThai, '' as NguoiSua " +
						" from  SanPham_TieuChiKiemDinh cd where cd.sanpham_fk = '" + this.spId + "' and loai = 1  and ncc_fk is null " ;
					}
				}catch(Exception er){

				}
			}
			else
			{
				sql =" select a.TieuChi, isnull(a.GhiNhan,'') as GhiNhan, isnull(b.QuyetDinh, a.dat) as QuyetDinh, isnull(a.TrangThai,'') as TrangThai, c.TEN + '--' +b.NgaySua  as NguoiSua " +
				"from erp_yeucaukiemdinh_tieuchi a "+
				"left join "+ 
				"( "+
				"	select * "+
				"	from Erp_YeuCauKiemDinh_TieuChi_Log "+
				"	where NgayGio = (select max(NgayGio) from Erp_YeuCauKiemDinh_TieuChi_Log where YeuCauKiemDinh_FK ='"+this.ycId+"') and YeuCauKiemDinh_FK='"+this.ycId+"' "+
				") "+
				"b on b.YeuCauKiemDinh_FK = a.yeucaukiemdinh_fk and a.tieuchi=b.TieuChi and b.DinhTinh=a.DinhTinh  " +
				"left join NHANVIEN c on c.PK_SEQ=b.NguoiSua where a.YeuCauKiemDinh_FK = '" + this.ycId + "' and a.DinhTinh = 1 ";
			}

			System.out.println("::Tieu chi:::: " + sql);
			rs = db.get(sql);
			int i=0;   	    
			if (rs != null&&numb>0)
			{
				try
				{
					while (rs.next())
					{
						this.tieuchi_dinhtinh[i]=rs.getString("TieuChi");
						this.ketqua_dinhtinh[i]=rs.getString("QuyetDinh");
						this.ghinhan_dinhtinh[i]=rs.getString("GhiNhan");
						this.nguoisua_dinhtinh[i]=rs.getString("NguoiSua");
						i++;
					}rs.close();
				}
				catch (Exception e)
				{
					System.out.println("116.Loi.....: " + e.toString());
					e.printStackTrace();
				}

			}

		}
		// start hoa chat and may moc
		//String query = "";
		// them cau lenh select du lieu hoa chat san pham va may moc san pham
		// hoa chat san pham 
		// load hoa chat san pham cua nha cung cap
		/*
		System.out.println("ma ncc :"+this.nccId);
		query="select  count(*) as m,hcsp.SANPHAM_FK,hc.ten as tenhc,dvdl.donvi as tendv, HOACHAT,SOCHATDUOCKIEMDINH as soluongchuan, "
				+" SOCHATKIEMDINH as soluong, NCC_FK from HOACHAT_SANPHAM  hcsp  left join ERP_SANPHAM sp on sp.PK_SEQ=hcsp.SANPHAM_FK   "
				+" left join ERP_SANPHAM hc  on hc.PK_SEQ=hcsp.HOACHAT left join donvidoluong dvdl on dvdl.pk_seq=sp.DVDL_FK "
				+" inner join ( select SANPHAM_FK  from ERP_YEUCAUKIEMDINH where PK_SEQ ="+ycId+") as b on b.SANPHAM_FK = hcsp.SANPHAM_FK "
				+" where NCC_FK ="+nccId+" group by hcsp.SANPHAM_FK, HOACHAT,SOCHATDUOCKIEMDINH, SOCHATKIEMDINH, NCC_FK,hc.ten,dvdl.donvi ";
		ResultSet rstemphc= this.db.get(query);
		boolean check=false;
		if(rstemphc!=null)
		{
			try{
				if(rstemphc.next()){
					check=true;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

		if(check == false){
			// load hoa chat san pham khong co nha cung cap
			query="select count(*) as m, hcsp.SANPHAM_FK,hc.ten as tenhc,dvdl.donvi as tendv, HOACHAT,SOCHATDUOCKIEMDINH as soluongchuan,"
					 +" SOCHATKIEMDINH as soluong, NCC_FK from HOACHAT_SANPHAM  hcsp  left join ERP_SANPHAM sp on sp.PK_SEQ=hcsp.SANPHAM_FK  "
					+"  left join ERP_SANPHAM hc on hc.PK_SEQ=hcsp.HOACHAT left join donvidoluong dvdl on dvdl.pk_seq=sp.DVDL_FK "
					+"  inner join ( select SANPHAM_FK  from ERP_YEUCAUKIEMDINH where PK_SEQ ="+ycId+") as b on b.SANPHAM_FK = hcsp.SANPHAM_FK "
					 +" where NCC_FK is null  group by hcsp.SANPHAM_FK, HOACHAT,SOCHATDUOCKIEMDINH,SOCHATKIEMDINH, NCC_FK,hc.ten,dvdl.donvi ";
		}
		this.rshcsp= this.db.get(query);
		System.out.println("load hoa chat "+query);
		// may moc san pham
			// load nhung may moc co nha cung
				query="select  count(*) as m,tscd.ma,tscd.ten,mmsp.sanpham_fk,mmsp.NCC_FK "
						 +" from MayMoc_SanPham  mmsp "
						 +" left join ERP_TAISANCODINH tscd on mmsp.taisancodinh_fk=tscd.pk_seq"
						 +" inner join  ( select SANPHAM_FK  from ERP_YEUCAUKIEMDINH "
						+" where PK_SEQ = "+ycId+") as b on b.SANPHAM_FK = mmsp.SANPHAM_FK "
							+" where NCC_FK ="+nccId+" "
						 +" group by tscd.ma,tscd.ten,mmsp.sanpham_fk,mmsp.NCC_FK";
				this.rsmmsp= this.db.get(query);

				ResultSet rstempmm= this.db.get(query);
				boolean check1=false;
				if(rstempmm!=null)
				{
					try{
						if(rstempmm.next()){
							check1=true;
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}

				if(check1 == false){
					query=" select count(*) as m,tscd.ma,tscd.ten,mmsp.sanpham_fk,mmsp.NCC_FK" 
							+" from MayMoc_SanPham  mmsp  left join ERP_TAISANCODINH tscd on mmsp.taisancodinh_fk=tscd.pk_seq"
							+" inner join  ( select SANPHAM_FK  from ERP_YEUCAUKIEMDINH "
							+" where PK_SEQ = 100037) as b on b.SANPHAM_FK = mmsp.SANPHAM_FK "
							+" where NCC_FK is null  "
							+" group by tscd.ma,tscd.ten,mmsp.sanpham_fk,mmsp.NCC_FK ";
				}
				this.rsmmsp= this.db.get(query);
				System.out.println("load may moc "+query);
		 */
		// end
		SpDuyetList= new ArrayList<IErpSanphamduyet>();
		query=  " SELECT DUYET.PK_SEQ,DUYET.YEUCAUKIEMDINH_FK,SOLUONGDUYET,SOLUONGMAU,SOLUONGHONG, "+
		" SOLUONGDUYET-SOLUONGMAU-SOLUONGHONG AS SOLUONGDAT ,NGAYDUYET ,nv.ten as nguoiduyet "+
		" FROM ERP_KIEMDINHCHATLUONG_LANDUYET DUYET INNER JOIN NHANVIEN NV ON NV.PK_SEQ=DUYET.NGUOIDUYET " +
		" where DUYET.YEUCAUKIEMDINH_FK="+this.ycId+
		" ORDER BY DUYET.PK_SEQ   ";
		rs=db.get(query);
		try{
			int i=1;
			while(rs.next()){
				IErpSanphamduyet sp=new ErpSanphamduyet();
				sp.setLanDuyet(""+i);
				sp.setNgayDuyet(rs.getString("NGAYDUYET"));
				sp.setNguoiDuyet(rs.getString("nguoiduyet"));
				sp.setSoluongdat(rs.getString("soluongdat"));
				sp.setSoluongduyet(rs.getString("soluongduyet"));
				sp.setSoluongmau(rs.getString("soluongmau"));
				sp.setSoluonghong(rs.getString("soluonghong"));
				i++;

				SpDuyetList.add(sp);

			}
			rs.close();
		}catch(Exception er){
			er.printStackTrace();
		}

	}

	public void DbClose()
	{
		try
		{
			if(rsYeuCauKiemDinh!=null)
				this.rsYeuCauKiemDinh.close();
			if(db!=null)
				db.shutDown();
		} catch (SQLException e)
		{

			e.printStackTrace();
		}


	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getCongtyId()
	{

		return this.congtyId;
	}


	public void setCongtyId(String congtyId)
	{
		this.congtyId=congtyId;

	}

	// Hàm kiểm tra dữ liệu đã có hoá chất cần thiết chưa để kiểm định
	private boolean CheckPhieuHoaChat() {
		try {
			String query = " select TRANGTHAI from ERP_YEUCAUCHUYENKHO where PK_SEQ in(select CHUYENKHO_HOACHAT_FK  "
				+ " from ERP_NHANHANG where PK_SEQ in (select nhanhang_fk from ERP_YEUCAUKIEMDINH where PK_SEQ = " + this.ycId + "))";
			ResultSet rs = this.db.get(query);
			System.out.println("Truy van hoa chat"+query);
			String trangthai = "0";
			if (rs != null) {
				if (rs.next()) {
					trangthai = rs.getString("TRANGTHAI");
				}
			}
			if (trangthai.equals("1") || trangthai.equals("3")) {
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}
	// HÀM KHO
	public String Update_Kho_Sp(dbutils db, String khott_fk, String spId,double soluong ,double booked,double available , double dongia) {
		// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
		try{
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIATON,0) AS GIATON   from erp_khott_sanpham kho " +
			"  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where khott_fk="+khott_fk+" and sanpham_fk= "+spId;

			double available_ton=0;
			double giaton=0;
			double soluongton=0;

			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(giaton >0){
					if( giaton- dongia !=0) {

						query=" insert into log_sql_khott(ngay,khott_fk,sanpham_fk ,ghichu ) " +
						" values( GETDATE(),"+khott_fk+","+spId+",N'đơn giá khác nhau giữa 2 kho :Giá cũ :"+giaton+". Giá mới :"+dongia+"' )";

						db.update(query);

					}
				} 

				query = " Update ERP_KHOTT_SANPHAM set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
				" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ",giaton="+(giaton >0?giaton:dongia)+"  "+
				" where khott_fk =" + khott_fk + " and sanpham_fk = " +   spId + "";
			}else{
				query=  " INSERT INTO ERP_KHOTT_SANPHAM ( KHOTT_FK,SANPHAM_FK,GIATON,SOLUONG,BOOKED,AVAILABLE,THANHTIEN ) VALUES  " +
				" ("+khott_fk+","+ spId+","+dongia+","+soluong+","+booked+","+available+","+soluong+"*"+dongia+")";

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();
			int resultInt = db.updateReturnInt(query);

			if(resultInt != 1)
			{
				return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
			}
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}
	public String Update_Kho_Sp_Chitiet(dbutils db, String khott_fk,String SANPHAM_FK, double soluongnhap,double booked,double available , 
			double DONGIAMUA,String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau,String NgayNhapKho ,String NgaySanXuat,
			String NGAYHETHAN, String idmarquette, String hamAm, String hamLuong, String mamarquette, String maMe, String maThung, String maphieu) {
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET ,cả đạt chất lượng và không đạt chất lượng.
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		try{

			vitri="100000";
			String	query = " select count(*) as sodong from ERP_KHOTT_SP_CHITIET " +
			" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " + SANPHAM_FK + " " +
			" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "' "+ (KHUVUCKHO_FK.length() >0?" and  " +
					"bin_fk ="+KHUVUCKHO_FK:"") +" and NGAYBATDAU='"+NgayBatDau+"'"+" and MAME='"+maMe+"' and MATHUNG='"+maThung+"'"
					+" and NGAYSANXUAT= '"+NgaySanXuat+"' and NGAYHETHAN = '"+ NGAYHETHAN+"' and MAPHIEU ='"+maphieu+"'";

			if(idmarquette == null){
				query += " and IDMARQUETTE is null";
			} else {
				query += " and IDMARQUETTE = "+ idmarquette;
			}
			System.out.println(query);

			ResultSet	rsCheck = db.get(query);
			boolean flag = false;
			if(rsCheck.next())
			{
				if(rsCheck.getInt("sodong") > 0) {
					flag = true;
				}

			}
			rsCheck.close();

			if(flag)
			{
				query = " update ERP_KHOTT_SP_CHITIET set booked=isnull(booked,0) + "+booked+" ,soluong = soluong + " + soluongnhap 
				+ ", AVAILABLE = AVAILABLE + " + available + ", HAMAM = " + hamAm + ",HAMLUONG = "+ hamLuong 
				+ ", MARQ='"+mamarquette+"', MAPHIEU ='"+maphieu+"' " +
				" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
				" and  ltrim(rtrim(SOLO)) = '" + solo.trim()+
				(KHUVUCKHO_FK.length() >0?"' and  bin_fk ="+KHUVUCKHO_FK:"")+" and NGAYBATDAU='"+NgayBatDau+"'"
				+" and MAME='"+maMe+"' and MATHUNG='"+maThung+"'"
				+" and NGAYSANXUAT= '"+NgaySanXuat+"' and NGAYHETHAN = '"+ NGAYHETHAN+"' and MAPHIEU = '"+maphieu+"'";
				if(idmarquette == null){
					query += " and IDMARQUETTE is null";
				} else {
					query += " and IDMARQUETTE = "+ idmarquette;
				}
				System.out.println("update:KD"+query);
			}
			else
			{
				/*query = "  insert ERP_KHOTT_SP_CHITIET(KHOTT_FK, SANPHAM_FK, SOLUONG , BOOKED, AVAILABLE, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO,NGAYBATDAU " +
						"  ,BIN_FK ,DONGIAMUA,IDMARQUETTE, HAMAM, HAMLUONG, MARQ,MAME, MATHUNG,MAPHIEU ) " +
						"  VALUES ( " + khott_fk + " ,  " + SANPHAM_FK + " , " +soluongnhap+ ", "+booked+", " +available + ", '" 
						 + solo.trim() + "', '"+NgaySanXuat+"', '"+NGAYHETHAN+"'," +
						"  '"  + NgayNhapKho + "','"+NgayBatDau +"'," +(KHUVUCKHO_FK.length() >0?KHUVUCKHO_FK:"NULL")+", " +
						+DONGIAMUA+","+idmarquette+","+hamAm+","+hamLuong+",'"+mamarquette+"','"+maMe+"','"+maThung+"','"+maphieu+"') " ;*/


				System.out.println("[SAI KIEMDINH]: query kho chi tiết"+ query);

			}	

			if(db.updateReturnInt(query)!=1)
			{
				return "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
			}
			return "";
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet : "+er.getMessage();
		}
	}

	// Tạo phiếu chuyển kho để ghi log sau này
	private boolean TaoPhieuChuyenKho(String khoNhan, String khoChoXuLy, List<String> sanPham, 
			List<Double> soLuongDatX, String Ngaybatdau_cxl, String ndxId, double donGia, List<String> soLoX, String lydo, String ghichu,
			List<String> ngayHetHan,List<String> ngaySanXuat,List<String> idmarquette, List<String> hamAm, List<String> hamLuong, 
			List<String> mamarquette, List<String> maMe, List<String> maThung, String maphieu, List<String> khuVuc, String loaimuahang,boolean isck_hanghong,List<String> nsx_fk){

		try
		{

			//xac dinh so chung tu cho phieu chuyen kho: loaimuhang =0 nhapkhau thi so chung tu= so to khai, loaimuhang =1 trongnuoc thi la so chung tu=sohoadon 
			String sochungtu="";
			String qr_sct=" select NH.DOITUONGNHAN_FK   as DOITUONGNHAN_FK, nh.congdoan_fk,nh.doituong_fk,nh.LOAIDOITUONG ,nh.lenhsanxuat_fk , "
					+ "   isnull( HD.sohoadon,'') as sohoadon,isnull( tnk.SOCHUNGTU,'') as sotokhai "+    
						"\n from ERP_YeuCauKiemDinh kd inner join ERP_NHANHANG NH ON KD.nhanhang_fk=NH.PK_SEQ "+    
						"\n left JOIN ERP_HOADONNCC HD ON HD.pk_seq= NH.hdNCC_fk "+
						"\n left join ERP_THUENHAPKHAU_HOADONNCC tnk_hd on tnk_hd.HOADONNCC_FK= hd.pk_seq "+
						"\n left join ERP_THUENHAPKHAU tnk on tnk.PK_SEQ = tnk_hd.THUENHAPKHAU_FK "+    
						"\n where KD.pk_seq="+this.ycId  ;
			
			System.out.println(" qr lay so chung tu: "+qr_sct);
			ResultSet rs_SCT=db.get(qr_sct);
			String lenhsanxuat_fk="0";
			String congdoan_fk="NULLL";
			String doituong_fk="NULL";
			String LOAIDOITUONG="NULL";
			String DOITUONGNHAN_FK="NULL";
			 
					while(rs_SCT.next()){

						DOITUONGNHAN_FK=( rs_SCT.getString("DOITUONGNHAN_FK") == null? "":rs_SCT.getString("DOITUONGNHAN_FK") );
						lenhsanxuat_fk= ( rs_SCT.getString("lenhsanxuat_fk") == null? "0":rs_SCT.getString("lenhsanxuat_fk") );
						sochungtu = lenhsanxuat_fk;
						congdoan_fk=( rs_SCT.getString("congdoan_fk") == null? "NULL":rs_SCT.getString("congdoan_fk") );
						  doituong_fk=( rs_SCT.getString("doituong_fk") == null? "NULL":rs_SCT.getString("doituong_fk") );
						  LOAIDOITUONG=( rs_SCT.getString("LOAIDOITUONG") == null? "NULL":rs_SCT.getString("LOAIDOITUONG") );
						if(loaimuahang.equals("0") ){
							sochungtu=rs_SCT.getString("sotokhai");
						}

						if(loaimuahang.equals("1") ){
							sochungtu=rs_SCT.getString("sohoadon");
						}

					}
					rs_SCT.close();
			 
			System.out.println("so chung tu: "+sochungtu);

			
			String trangthai="0";
			if(loaimuahang.equals("2") || isck_hanghong ){
				trangthai="1";
			}
			
			String doituongnhan_fk="";
			String LOAIDOITUONGnhan="";
			String KH_XUAT_FK="";
			String KhGiaCongId="";
			
			if(Library.getLoaiKho(khoNhan,db).equals("13")){
			 	
				if(KhGiaCongId.equals("")){
					this.msg=  "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: kho nhận là kho gia công nhưng không xác định được đối tượng để chuyển kho ";
					return false;
				}
				doituongnhan_fk=KhGiaCongId;
				LOAIDOITUONGnhan="1";	
				KH_XUAT_FK=KhGiaCongId;
			}else if(Library.getLoaiKho(khoNhan,db).equals("10")){
				doituongnhan_fk=DOITUONGNHAN_FK;
				LOAIDOITUONGnhan="5";	
			}
			else{
				 
				doituongnhan_fk="NULL";
				LOAIDOITUONGnhan="NULL";
				KH_XUAT_FK="NULL";
			}
			
								
										
			// Tạo phiếu chuyển kho 
			/*String  query = " insert ERP_CHUYENKHO( noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, ghichu, trangthai, khoxuat_fk," +
							" khonhan_fk, trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua,YCKD_FK,ISHANGDIDUONG,SoChungTu) " +
							" values( '" +  ndxId + "', '" + Ngaybatdau_cxl+ "', '" + Ngaybatdau_cxl + "', '" + Ngaybatdau_cxl 
							+ "', N'"+ lydo +this.ycId +" SP "+ this.spTen + ": "+ this.solo+" ', N'" + this.ycId + "', '"+trangthai+"', " + khoChoXuLy + ", " + khoNhan + ", '1', '" + getDateTime() + "', '" + this.userId + "', '" 
							+ getDateTime() + "', '" + this.userId + "',"+this.ycId+",1,'" +sochungtu+"'  )";
							int k = this.db.updateReturnInt(query);
							if( k !=1){
								return false;
							}*/
			  String query = " insert ERP_CHUYENKHO(doituong_fk,loaidoituong ,lenhsanxuat_fk,congdoan_fk,doituongNHAN_fk,LOAIDOITUONGNHAN, CONGTY_fk,  noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, ghichu, trangthai, khoxuat_fk," +
						" khonhan_fk, trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua,YCKD_FK,ISHANGDIDUONG,SoChungTu) " +
						" values("+doituong_fk+","+LOAIDOITUONG+","+lenhsanxuat_fk+","+congdoan_fk+", "+(doituongnhan_fk.length()>0?doituongnhan_fk:"NULL")+"  ,"+LOAIDOITUONGnhan+", "+this.congtyId+",   '" +  ndxId + "', '" + Ngaybatdau_cxl+ "', '" + Ngaybatdau_cxl + "', '" + Ngaybatdau_cxl 
						+ "', N'"+ lydo +this.ycId +" SP "+ this.spTen + ": "+ this.solo+" ', N'" + this.ycId + "', '"+trangthai+"', " + khoChoXuLy + ", " + khoNhan + ", '1', '" + getDateTime() + "', '" + this.userId + "', '" 
						+ getDateTime() + "', '" + this.userId + "',"+this.ycId+",1,'" +sochungtu+"'  )";
						int k = this.db.updateReturnInt(query);
						if( k !=1){
							this.msg="Lỗi dòng lệnh: "+query;
							return false;
						}
						
			
			System.out.println("insert ERP_CHUYENKHO: "+ query +"\n");
			

			String ycnlCurrent = "";
			query = "select SCOPE_IDENTITY() as nhId";

			ResultSet rs1 = db.get(query);

			 
				if (rs1.next()) {
					ycnlCurrent = rs1.getString("nhId");
				}
				rs1.close();

			String ngaynhapkho = "";

			// lấy ngày tạo của phiếu kiểm định
			String sql = "select NGAYKIEM from ErP_YEUCAUKIEMDINH where PK_SEQ="+ this.ycId;
			 
				ResultSet rs = this.db.get(sql);
				 
					if(rs.next()){
						ngaynhapkho = rs.getString("NGAYKIEM");
					}
					rs.close();
 

			double soluongtong = 0;
			// tạo phiếu chuyển kho đến số lô, mã mẻ, thùng
			for(int i=0; i< sanPham.size(); i++){
				String khuvuc = khuVuc.get(i);
				if(khuVuc.get(i).trim().length() == 0){
					khuvuc = "NULL";
				}
				// xem xét số lô nào có số lượng >0 thì mới insert vào.
				if(soLuongDatX.get(i) > 0){
					
					String hamam_=hamAm.get(i);
					String hamluong_=hamLuong.get(i);
					if(isck_hanghong){
						hamam_="0";
						hamluong_="100";
					}
					query = "   insert ERP_CHUYENKHO_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, " +
							"	MARQ, HAMLUONG, HAMAM, soluong, BIN_FK ,NSX_FK) values(" + ycnlCurrent + ", "+this.spId+",'" + soLoX.get(i) + "','" + ngayHetHan.get(i) 
					+ "', '" + ngaynhapkho + "', '" + maMe.get(i)+ "', '" + maThung.get(i) + "', '" + maphieu + "', '" + mamarquette.get(i) 
					+ "'," + hamluong_+ ", " + hamam_+ "," +  soLuongDatX.get(i)+ ","+khuvuc+","+(nsx_fk.get(i).trim().length()==0?"NULL":nsx_fk.get(i))+")";
					System.out.println(query);
					k = this.db.updateReturnInt(query);
					soluongtong += soLuongDatX.get(i);
					if (k != 1) {
						return false;
					}
				}

			}
			// Tạo phiếu chuyển kho 
			query = " INSERT ERP_CHUYENKHO_SANPHAM( CHUYENKHO_FK, SANPHAM_FK ,SOLUONGYEUCAU , SOLUONGXUAT ,dongia, ghichu_chuyenkho) " +
			" values( '" + ycnlCurrent + "', '" + this.spId + "',"+soluongtong+" , " + soluongtong +","+donGia+",N'"+ghichu+"' ) ";
			k = this.db.updateReturnInt(query);
			if( k <=0){
				return false;
			}
			
			if(loaimuahang.equals("2") || isck_hanghong){
				query = "  insert ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG( chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
				"  	MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, soluong	,NSX_FK	 )  " + 
				"  select chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
				"  		MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, " + 
				"  		  DT.soluong  as soluong,NSX_FK  " + 
				"  from " + 
				"  ( " + 
				"  	select chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
				"  		MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, SUM(soluong) as soluong,NSX_FK " + 
				"  	from ERP_CHUYENKHO_SANPHAM_CHITIET  " + 
				"  	where chuyenkho_fk = '" + ycnlCurrent + "' and chuyenkho_fk in ( select pk_seq from ERP_CHUYENKHO where pk_seq = '" + ycnlCurrent + "' and khonhan_fk is not null )  " + 
				"  	group by chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
				"  				MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH ,NSX_FK" + 
				"  ) " + 
				"  DT " +
				//", ( select 1 as col1 union select 2 union select 3 union select 4 union select 5 ) DT2 " + 
				"  order by DT.SANPHAM_FK asc, DT.soluong desc ";
				if( !db.update(query) )
				{
					msg = "Lỗi khi chốt: " + query;
					 
					return false;
				}
			}
			
			
			
			// tao tooltip
			String msg1= db.execProceduce2("CapNhatTooltip_CK", new String[] { ycnlCurrent} );
			if(msg1.length() >0){
				return false;
			}
			
			return true;
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}

	}

	// Update Kho theo sản phẩm và lô
	private boolean CapNhatKho(String khoId, List<String> sanPham, List<String> maLo, List<Double> soLuongDatX,
			List<String> khuVuc, List<String> ngaySanXuat, List<String> ngayHetHan, List<Double> donGia, double index, double book, List<String> idmarquette,
			List<String> hamAm, List<String> hamLuong, List<String> mamarquette, List<String> maMe, List<String> maThung, String maphieu,String loaimuahang,boolean isck_hanghong,List<String> nsx_fk) {

		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");

		String NGAYKIEM = "";
		String NGAYNHAPKHO_KHONGMAPHIEU = "";

		// lấy ngày tạo của phiếu kiểm định
		 String sql = " select ISNULL(cast(NH.DOITUONG_FK AS VARCHAR(18)),'') AS  DOITUONG_FK ,  " +
				 " ISNULL(NH.LOAIDOITUONG,'') AS LOAIDOITUONG  ,A.NGAYTAO, A.NGAYKIEM, " +
				 " ( select top 1 NGAYNHAPKHO  from ERP_NHANHANG_SP_CHITIET where NHANHANG_FK=A.NHANHANG_FK) as NGAYNHAPKHO_KHONGMAPHIEU  " + 
				 " from ErP_YEUCAUKIEMDINH a  " +
				 " INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ=A.NHANHANG_FK  where A.PK_SEQ="+ this.ycId;
	System.out.println("thongtinphieukiemdinh="+sql);
	String DOITUONG_FK="",LOAIDOITUONG="";
	
	
		try{
			ResultSet rs = this.db.get(sql);
			 
				if(rs.next()){
					NGAYKIEM = rs.getString("NGAYKIEM");
					NGAYNHAPKHO_KHONGMAPHIEU = rs.getString("NGAYNHAPKHO_KHONGMAPHIEU");
					if(Library.getLoaiKho(khoId,db).equals("12") || Library.getLoaiKho(khoId,db).equals("10")){
						DOITUONG_FK= rs.getString("DOITUONG_FK");
						LOAIDOITUONG= rs.getString("LOAIDOITUONG");
						if(DOITUONG_FK.equals("")){
							this.msg="Vui lòng kiểm lại kho chờ xử lý,kho chi nhánh không có xác định đối tượng";
							return false;
						}
					}
					
				}
				rs.close();

			 
		}catch(Exception ex){			
			ex.printStackTrace();
			return  false;
		}


		double soluongtong =0;
		double soluongtongbooked = 0;
		// giảm kho chi tiết
		for (int i = 0; i < sanPham.size(); i++) {
			String sanpham_fk = sanPham.get(i);
			String solo = maLo.get(i);
			String khu_fk = khuVuc.get(i);

			// tuỳ thuộc vào giá trị index để tăng hay giảm kho
			// số lượng phải là số âm
			double soluong = soLuongDatX.get(i);
			String ngayhethan = ngayHetHan.get(i);

			String ngaysanxuat = ngaySanXuat.get(i);
			double dongia = donGia.get(i);
			String idmarquette_temp = idmarquette.get(i);
			
		
			String hamAm_temp = hamAm.get(i);
			String hamLuong_temp = hamLuong.get(i);
			if(isck_hanghong){
				hamAm_temp="0";
				hamLuong_temp="100";
			}
	
			String mamarquette_temp = mamarquette.get(i);
			String maMe_temp = maMe.get(i);
			String maThung_temp = maThung.get(i);
			String nsxid = nsx_fk.get(i);
			Utility util = new Utility();

			/*double soluong, double booked, double available*/
			/* mục tiêu hàm này là giữ booked kho chuyển thôi*/
			String kq = "";
			String ngaynhapkho = "";
			if(soluong !=0){

				// giảm kho không mã phiếu.
				ngaynhapkho = NGAYNHAPKHO_KHONGMAPHIEU;
				double soluongct_cn=-1 * soluong;
				double availbale_cn=-1 * soluong;
				double booked_cn=0;
				
				 
				
				kq = util.Update_KhoTT_NEW(this.getDateTime(), "Cập nhật KD - giảm kho dòng không mã phiếu  Id : "+this.ycId, db, khoId, sanpham_fk, solo, 
						ngayhethan, ngaynhapkho, maMe_temp, maThung_temp, khu_fk, "", "", "", mamarquette_temp, "100", "0",
						LOAIDOITUONG, DOITUONG_FK, soluongct_cn, booked_cn ,availbale_cn , dongia,"",nsxid);

				System.out.println("[KDCL] Giảm kho không có mã phiếu"+kq);

				if (kq.trim().length() > 0) {
					this.msg="[KDCL] Giảm kho không có mã phiếu: "+kq;
					return false;
				}

				// tạo ra phiếu có mã phiếu, booked lại cho chuyển kho
				ngaynhapkho = NGAYKIEM;
					
				if(loaimuahang.equals("2")){
					soluong=0;
					//nếu loại nhận hàng là nhập kho từ lệnh sản xuất thì chuyển kho tạo tự động đã chốt, thì kho không có tăng.
				}
				 // nếu là chuyển kho hàng hỏng, thì  không có mã phiếu 
				if(!isck_hanghong){
					kq = util.Update_KhoTT_NEW(this.getDateTime(), "Cập nhật KD - tăng dòng kho có mã phiếu ID:  "+this.ycId, db, khoId, sanpham_fk, solo, 
							ngayhethan, ngaynhapkho, maMe_temp, maThung_temp, khu_fk, maphieu, "", "", mamarquette_temp, hamLuong_temp, hamAm_temp,
							LOAIDOITUONG, DOITUONG_FK, soluong,soluong, 0, dongia,"",nsxid);
					 
					System.out.println("[KDCL] Tăng kho, booked có mã phiếu"+kq);
					if (kq.trim().length() > 0) {
						this.msg="[KDCL] Tăng kho, booked có mã phiếu: "+kq;
						return false;
					}
				}
			}
		}
		return true;
	}

	private String getNoiDungNhap(){
		try{
			String query = " select PK_SEQ from ERP_NOIDUNGNHAP where MA ='XK08'";
			ResultSet rs = this.db.get(query);
			String id = "";
			if(rs!=null){
				if(rs.next()){
					id = rs.getString("PK_SEQ");
				}
				rs.close();
			}
			return id;
		} catch(Exception ex){
			ex.printStackTrace();
			return "";
		}
	}

	// Tăng kho nhận, giảm kho chờ xử lý
	private boolean DieuChinhKhoTheoThung(String loaimuahang) {
		try {

			 
			// Lấy thông tin Kho nhận và kho chờ xử lý
			String query = " select e.KHOCHOXULY_FK, e.KHONHAN_FK ,isnull(e.KHONHANTP_DAT_FK,'0') as KHONHANTP_DAT_FK   from ERP_NHANHANG e  where e.PK_SEQ in "
						 + " (select nhanhang_fk from ERP_YEUCAUKIEMDINH where PK_SEQ = " + this.ycId + ")";

			String khoNhan = "";
			String khoChoXuLy = "";
			ResultSet rs = this.db.get(query);
			String KHONHANTP_DAT_FK="";
			if (rs.next()) {
				khoNhan = rs.getString("KHONHAN_FK");
				khoChoXuLy = rs.getString("KHOCHOXULY_FK");
				KHONHANTP_DAT_FK = rs.getString("KHONHANTP_DAT_FK");
			}
			rs.close();

			// lấy thông tin để cập nhật
			query = " select YC.sanpham_fk, thung.MATHUNG, thung.MALO, yc.MAME ,thung.SOLUONGDAT, thung.SOLUONGKHONGDAT, thung.SOLUONGMAU, yc.MAPHIEU, " +
					" thung.BIN_FK, yc.NGAYSANXUAT, yc.NGAYHETHAN, yc.DONGIAVIET, yc.IDMARQUETTE, isnull(thung.HAMAM,0) as HAMAM" +
					" , isnull(thung.HAMLUONG,100) as HAMLUONG, yc.MAMARQUETTE,yc.NSX_FK " +
					" from ERP_YEUCAUKIEMDINH yc inner join ERP_YEUCAUKIEMDINH_THUNG thung " +
					" on yc.pk_seq = thung.YEUCAUKIEMDINH_FK where yc.pk_seq ="+ this.ycId;

			List<String> sanPham = new ArrayList<String>();
			List<String> maLo = new ArrayList<String>();
			List<Double> soLuongDatX = new ArrayList<Double>();
			List<Double> soluongKhongDatX = new ArrayList<Double>();
			List<String> khuVuc = new ArrayList<String>();
			List<String> ngaySanXuat = new ArrayList<String>();
			List<String> ngayHetHan = new ArrayList<String>();
			List<Double> donGia = new ArrayList<Double>();
			List<String> idmarquette = new ArrayList<String>();
			List<String> mamarquette = new ArrayList<String>();
			// Thêm hàm ẩm và hàm lượng
			List<String> hamAm = new ArrayList<String>();
			List<String> hamLuong = new ArrayList<String>();
			// Thêm số lượng mẫu
			List<Double> soLuongMauX = new ArrayList<Double>();
			List<String> maThung = new ArrayList<String>();
			List<String> maMe = new ArrayList<String>();
			List<String> nsx_fk = new ArrayList<String>();
			String maphieu ="";

			rs = this.db.get(query);

			while (rs.next()){
				sanPham.add(rs.getString("sanpham_fk"));
				maLo.add(rs.getString("MALO"));
				soLuongDatX.add(rs.getDouble("SOLUONGDAT"));
				soluongKhongDatX.add(rs.getDouble("SOLUONGKHONGDAT"));
				khuVuc.add(rs.getString("BIN_FK"));
				ngaySanXuat.add(rs.getString("NGAYSANXUAT"));
				ngayHetHan.add(rs.getString("NGAYHETHAN"));
				donGia.add(rs.getDouble("DONGIAVIET"));
				idmarquette.add(rs.getString("IDMARQUETTE"));
				mamarquette.add(rs.getString("MAMARQUETTE"));
				hamAm.add(rs.getString("HAMAM"));
				hamLuong.add(rs.getString("HAMLUONG"));
				soLuongMauX.add(rs.getDouble("SOLUONGMAU"));

				maThung.add(rs.getString("MATHUNG"));
				maMe.add(rs.getString("MAME"));
				nsx_fk.add(rs.getString("NSX_FK")==null?"":rs.getString("NSX_FK"));
				maphieu = rs.getString("MAPHIEU");
			}

			// 15/06/2016: CÁC SẢN PHẨM ĐẠT SẼ ĐƯỢC CHUYỂN ĐI
			// 28/06/2016: Tạo phiếu chuyển kho thôi và sau đó booked kho chuyển
			
			String ndxId= this.getNoiDungNhap();

			/*Date today = new Date(System.currentTimeMillis());
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = timeFormat.format(today.getTime());*/
			String date=this.ngaykiem;
			double donGiaX = donGia.get(0);
			String lydo = "Chuyển hàng đạt từ kho CXL.Số phiếu:";
			String ghichu = "chuyển hàng đạt từ kho CXL PYC:"+this.ycId;

			// tạo phiếu chuyển kho
			// chưa biết kho nhận là kho nào
			
			 
			if(loaimuahang.equals("2")){
				khoNhan= KHONHANTP_DAT_FK;
			}
			boolean check = this.TaoPhieuChuyenKho(khoNhan, khoChoXuLy, sanPham, soLuongDatX, date, ndxId, donGiaX, maLo, lydo, 
					ghichu,ngayHetHan,ngaySanXuat,idmarquette, hamAm, hamLuong, mamarquette, maMe, maThung,maphieu, khuVuc, loaimuahang,false,nsx_fk);

			if(check == false){
				System.out.println("kho hàng 1");
				return false;
			}
			// booked kho thật
			// cập nhật kho chờ xử lý : giảm kho chờ xử lý đúng bằng lượng chuyển đi: số lượng hàng mẫu

			double index = -1.0;
			double booked = 1.0;

			check = this.CapNhatKho( khoChoXuLy, sanPham,  maLo, soLuongDatX,khuVuc,  ngaySanXuat, ngayHetHan,  donGia, 
					index,booked, idmarquette, hamAm, hamLuong,mamarquette,maMe,maThung,maphieu,loaimuahang,false,nsx_fk);
			if(check == false){
				System.out.println("Cập nhật kho hàng 2");
				return false;
			}
			

			// 15/06/2016: CÁC SẢN PHẨM LÀ HÀNG MẪU SẼ ĐƯỢC CHUYỂN ĐI
			// 28/06/2016: Tạo phiếu chuyển kho thôi và sau đó booked kho chuyển
			
			// tạo phiếu chuyển kho
			// chưa biết kho nhận là kho nào
			lydo = "Chuyển hàng mẫu từ kho CXL. Số phiếu:";
			ghichu = "chuyển hàng mẫu từ kho CXL  PYC:"+this.ycId;
			  
			// tính lại số lượng mẫu.
			double tongSoLuongMau = 0;
			for(int t=0; t<soLuongMauX.size(); t++){
				tongSoLuongMau += soLuongMauX.get(t);
			}
			
			// tổng số lượng mẫu phải lớn hơn 0 thì mới phát sinh phiếu.
			if(tongSoLuongMau >0)
			{
				check = this.TaoPhieuChuyenKho(khoNhan, khoChoXuLy, sanPham, soLuongMauX, date, ndxId, donGiaX, maLo, lydo, 
						ghichu,ngayHetHan,ngaySanXuat,idmarquette, hamAm, hamLuong, mamarquette, maMe, maThung,maphieu, khuVuc, loaimuahang,false,nsx_fk);

				if(check == false){
					//System.out.println("Chuyển kho kho hàng 2");
					return false;
				}
				// booked kho mẫu.
				// cập nhật kho chờ xử lý : giảm kho chờ xử lý đúng bằng lượng chuyển đi: số lượng hàng mẫu

				index = -1.0;
				booked = 1.0;

				check = this.CapNhatKho( khoChoXuLy, sanPham, maLo, soLuongMauX,khuVuc,  ngaySanXuat, ngayHetHan,  donGia, 
						index,booked, idmarquette, hamAm, hamLuong,mamarquette,maMe,maThung,maphieu,loaimuahang,false,nsx_fk);
				if(check == false){
					//System.out.println("Cập nhật kho hàng 2");
					return false;
				}	
			}
			double tongSoLuongHONG = 0;
			for(int t=0; t<soluongKhongDatX.size(); t++){
				tongSoLuongHONG += soluongKhongDatX.get(t);
			}
			
			if(tongSoLuongHONG >0)
			{
				lydo = "Chuyển hàng hỏng từ kho CXL. Số PYC:"+this.ycId;
				ghichu = "chuyển hàng hòng từ kho CXL .Số  PYC:"+this.ycId;
				// fix cứng kho nhận 
				khoNhan="(select PK_SEQ from ERP_KHOTT where TEN like N'Kho biệt trữ%' )";
				// hàng không đạt không có mã phiếu 
				
				
				check = this.TaoPhieuChuyenKho(khoNhan, khoChoXuLy, sanPham, soluongKhongDatX, date, ndxId, donGiaX, maLo, lydo, 
						ghichu,ngayHetHan,ngaySanXuat,idmarquette, hamAm, hamLuong, mamarquette, maMe, maThung,"", khuVuc, loaimuahang,true,nsx_fk);

				if(check == false){
					//System.out.println("Chuyển kho kho hàng 2");
					return false;
				}
				// booked kho mẫu.
				// cập nhật kho chờ xử lý : giảm kho chờ xử lý đúng bằng lượng chuyển đi: số lượng hàng mẫu
				
				index = -1.0;
				booked = 1.0;

				check = this.CapNhatKho( khoChoXuLy, sanPham, maLo, soluongKhongDatX,khuVuc,  ngaySanXuat, ngayHetHan,  donGia, 
						index,booked, idmarquette, hamAm, hamLuong,mamarquette,maMe,maThung,"",loaimuahang,true,nsx_fk);
				if(check == false){
					//System.out.println("Cập nhật kho hàng 2");
					return false;
				}	
			}
			
			

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
  
	public boolean duyetKiemDinhNhapKhau(String loaimuahang) {

		try {

			this.solo = this.solo.trim();
			Utility util = new Utility();
			String query = "";
			this.db.getConnection().setAutoCommit(false);

			double dongia = 0;
			
			
			
			// cập nhật lại bảng YEUCAUKIEMDINH
			double soluong_khongdat = Double.parseDouble(this.SoLuongKiemDinh) - Double.parseDouble(this.soluongDat)
			- Double.parseDouble(this.Soluongmau.replaceAll(",", ""));

			String dat = this.soluongDat.trim().length() <= 0 ? "0" : this.soluongDat.trim();
			query = " update ERP_YeuCauKiemDinh  set  DatChatLuong = '" + this.datCl + "', soluongDat = " + dat + ", " + " nguoisua = '"
					+ this.userId + "',NguoiDuyet='" + this.userId + "', ngaysua = '" + getDateTime() + "', " + 
					" NgayKiem = '" + this.ngaykiem + "' ,NGAYHETHONG=GETDATE(),GIOCHOT='" + util.getTime() + "'  where pk_seq = '" + this.ycId + "'";

			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
				db.update("rollback");
				return false;
			}

			query = " insert into ERP_KIEMDINHCHATLUONG_LANDUYET (YEUCAUKIEMDINH_FK,SOLUONGDUYET,SOLUONGMAU,SOLUONGHONG,NGAYDUYET,NGUOIDUYET,TRANGTHAI)  "
				+ " values ("
				+ this.ycId
				+ ","
				+ this.SoLuongKiemDinh
				+ ","
				+ this.Soluongmau
				+ ","
				+ soluong_khongdat
				+ ",getdate(),"
				+ this.userId + ",'1')";
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
				db.update("rollback");
				return false;
			}
			// mỗi lần duyệt đạt thì lưu lại log cho mỗi lần duyệt
/*
			query = "select IDENT_CURRENT('ERP_KIEMDINHCHATLUONG_LANDUYET') AS ID";
			ResultSet rs = this.db.get(query);
			rs.next();
			String landuyet_id = rs.getString("ID");
			rs.close();

			query = " INSERT INTO ERP_YEUCAUKIEMDINH_TIEUCHI_LANDUYET(LANDUYET_FK,YEUCAUKIEMDINH_FK,TIEUCHI,PHEPTOAN,GIATRICHUAN,DIEMDAT,DAT,TRANGTHAI,DINHLUONG,DINHTINH,GHINHAN) "
				+ " SELECT  "
				+ landuyet_id
				+ ",YEUCAUKIEMDINH_FK,TIEUCHI,PHEPTOAN,GIATRICHUAN,DIEMDAT,DAT,TRANGTHAI,DINHLUONG,DINHTINH,GHINHAN FROM ERP_YEUCAUKIEMDINH_TIEUCHI WHERE YEUCAUKIEMDINH_FK="
				+ this.ycId;
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
				db.update("rollback");
				return false;
			}*/



			//GHI NHẬN LẠI NXT, dòng không có mã phiếu thì lúc nào cũng hamam = 0, hamluong = 100
			query = "\ninsert ERP_YEUCAUKIEMDINH_THUNG_CHITIET( YEUCAUKIEMDINH_FK, SANPHAM_FK, MATHUNG, SOLUONG, SOLUONGMAU, SOLUONGDAT, SOLUONGKHONGDAT, MALO, BIN_FK, HAMAM, HAMLUONG," + 
			"\n		MAPHIEU, SOLO, NGAYHETHAN, NGAYNHAPKHO, MAME, MAMARQUETTE	 )" + 
			"\n	select a.pk_seq, a.SANPHAM_FK, b.MATHUNG, b.SOLUONG, b.SOLUONGMAU, b.SOLUONGDAT, b.SOLUONGKHONGDAT, b.MALO, b.BIN_FK, b.HAMAM, b.HAMLUONG," + 
			"\n			isnull(a.MAPHIEU, '') as MAPHIEU, a.SOLO, a.NGAYHETHAN, a.ngaykiem as NGAYNHAPKHO, a.MAME, isnull(a.MAMARQUETTE, '') as MERQ" + 
			"\n	from ERP_YEUCAUKIEMDINH a inner join ERP_YEUCAUKIEMDINH_THUNG b on a.pk_seq = b.YEUCAUKIEMDINH_FK" + 
			"\n			left join ERP_BIN bin on b.bin_fk = bin.pk_seq" + 
			"\n	where a.pk_seq = '" + this.ycId + "' and b.SOLUONGDAT > 0 " + 
			"\n union ALL" + 
			"\n	select a.pk_seq, a.SANPHAM_FK, b.MATHUNG, b.SOLUONG, b.SOLUONGMAU, -1 * b.SOLUONGDAT, b.SOLUONGKHONGDAT, b.MALO, b.BIN_FK, '0' as HAMAM, '100' HAMLUONG," + 
			"\n			'' as MAPHIEU, a.SOLO, a.NGAYHETHAN, ( select distinct  NGAYNHAPKHO from ERP_NHANHANG_sp_chitiet ct where ct.NHANHANG_FK=a.nhanhang_fk  ) as NGAYNHAPKHO, a.MAME, isnull(a.MAMARQUETTE, '') as MERQ" + 
			"\n	from ERP_YEUCAUKIEMDINH a inner join ERP_YEUCAUKIEMDINH_THUNG b on a.pk_seq = b.YEUCAUKIEMDINH_FK" + 
			"\n			left join ERP_BIN bin on b.bin_fk = bin.pk_seq" + 
			"\n	where a.pk_seq = '" + this.ycId + "' and b.SOLUONGDAT > 0";
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
				db.update("rollback");
				return false;
			}

			// chuyen trang thai hoan tat
			query = " update ERP_YeuCauKiemDinh  set trangthai=2 where pk_seq = '" + this.ycId + "'";
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
				db.update("rollback");
				return false;
			}
			// kiem tra nue nhan hàng là sản xuât thì tạo ra chuyển kho ở trạng thái đã chốt
			
			boolean check = this.DieuChinhKhoTheoThung(loaimuahang);
			if( check == false){
				 
					this.msg =this.msg.length()>0? this.msg:  "Lỗi: Không tăng giảm được kho chờ xử lý và kho nhận";
					this.db.getConnection().rollback();
					return false;
			}
 
			
			query=" SELECT CK.PK_SEQ FROM ERP_CHUYENKHO CK WHERE CK.YCKD_FK =  "+this.ycId+
				  " AND TRANGTHAI=0  AND EXISTS( "+
				  " SELECT NH.LENHSANXUAT_FK FROM ERP_YEUCAUKIEMDINH KD  "+
				  " INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ= KD.nhanhang_fk "+
				  " WHERE KD.PK_SEQ IN ("+this.ycId+") AND NH.LENHSANXUAT_FK IS NOT NULL )";
			ResultSet rscheck=db.get(query);
			boolean check_=false;
			if(rscheck.next()){
				check_=true;
			}
			rscheck.close();
			if(check_){
				this.msg = "Lỗi : Chuyển kho tự động của kiểm định hàng sản xuất đang ở trạng thái chưa chốt, vui lòng thử lại. ";
				db.update("rollback");
				return false;
				
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + e.getMessage();
			e.printStackTrace();
			return false;
		}
	}

	// hàm này không sử dụng
	public boolean duyetKiemDinh()
	{
		return false;
	}

	public String getTrangThai()
	{

		return this.trangthai;
	}


	public void setTrangThai(String trangthai)
	{
		this.trangthai=trangthai;
	}


	public String getDatCl()
	{
		return this.datCl;
	}


	public void setDatCl(String datcl)
	{

		this.datCl = datcl;
	}

	public void createRs()
	{
		String query = "";
		// them cau lenh select du lieu hoa chat san pham va may moc san pham
		// hoa chat san pham 
		// load hoa chat san pham cua nha cung cap

		// thêm select số thùng vào.
		if(this.ycId.trim().length() >0){
			query =" select  isnull(MATHUNG,'') as MATHUNG, SOLUONG, SOLUONGDAT, SOLUONGKHONGDAT, SOLUONGMAU, isnull(HAMAM,0) as HAMAM, isnull(HAMLUONG,0) as HAMLUONG " +
			" from ERP_YEUCAUKIEMDINH_THUNG where YEUCAUKIEMDINH_FK ="+this.ycId +"  " +
					" order by  case when ISNUMERIC(MATHUNG)=1 then CAST(MATHUNG as float)  else 100000    end asc ";


			System.out.println(" rs thung: "+query);
			this.rsSoThung = this.db.get(query);

		}

		System.out.println("ma ncc :"+this.nccId);
		query="select  count(*) as m,hcsp.SANPHAM_FK,hc.ten as tenhc,dvdl.donvi as tendv, HOACHAT,SOCHATDUOCKIEMDINH as soluongchuan, "
			+" SOCHATKIEMDINH as soluong, NCC_FK from HOACHAT_SANPHAM  hcsp  left join ERP_SANPHAM sp on sp.PK_SEQ=hcsp.SANPHAM_FK   "
			+" left join ERP_SANPHAM hc  on hc.PK_SEQ=hcsp.HOACHAT left join donvidoluong dvdl on dvdl.pk_seq=sp.DVDL_FK "
			+" inner join ( select SANPHAM_FK  from ERP_YEUCAUKIEMDINH where PK_SEQ ="+ycId+") as b on b.SANPHAM_FK = hcsp.SANPHAM_FK "
			+" where NCC_FK ="+nccId+" group by hcsp.SANPHAM_FK, HOACHAT,SOCHATDUOCKIEMDINH, SOCHATKIEMDINH, NCC_FK,hc.ten,dvdl.donvi ";
		ResultSet rstemphc= this.db.get(query);
		boolean check=false;
		if(rstemphc!=null)
		{
			try{
				if(rstemphc.next()){
					check=true;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

		if(check == false){
			// load hoa chat san pham khong co nha cung cap
			query="select count(*) as m, hcsp.SANPHAM_FK,hc.ten as tenhc,dvdl.donvi as tendv, HOACHAT,SOCHATDUOCKIEMDINH as soluongchuan,"
				+" SOCHATKIEMDINH as soluong, NCC_FK from HOACHAT_SANPHAM  hcsp  left join ERP_SANPHAM sp on sp.PK_SEQ=hcsp.SANPHAM_FK  "
				+"  left join ERP_SANPHAM hc on hc.PK_SEQ=hcsp.HOACHAT left join donvidoluong dvdl on dvdl.pk_seq=sp.DVDL_FK "
				+"  inner join ( select SANPHAM_FK  from ERP_YEUCAUKIEMDINH where PK_SEQ ="+ycId+") as b on b.SANPHAM_FK = hcsp.SANPHAM_FK "
				+" where NCC_FK is null  group by hcsp.SANPHAM_FK, HOACHAT,SOCHATDUOCKIEMDINH,SOCHATKIEMDINH, NCC_FK,hc.ten,dvdl.donvi ";
		}
		this.rshcsp= this.db.get(query);
		System.out.println("load hoa chat "+query);
		// may moc san pham
		// load nhung may moc co nha cung
		query="select  count(*) as m,tscd.ma,tscd.ten,mmsp.sanpham_fk,mmsp.NCC_FK "
			+" from MayMoc_SanPham  mmsp "
			+" left join ERP_TAISANCODINH tscd on mmsp.taisancodinh_fk=tscd.pk_seq"
			+" inner join  ( select SANPHAM_FK  from ERP_YEUCAUKIEMDINH "
			+" where PK_SEQ = "+ycId+") as b on b.SANPHAM_FK = mmsp.SANPHAM_FK "
			+" where NCC_FK ="+nccId+" "
			+" group by tscd.ma,tscd.ten,mmsp.sanpham_fk,mmsp.NCC_FK";
		this.rsmmsp= this.db.get(query);

		ResultSet rstempmm= this.db.get(query);
		boolean check1=false;
		if(rstempmm!=null)
		{
			try{
				if(rstempmm.next()){
					check1=true;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

		if(check1 == false){
			query=" select count(*) as m,tscd.ma,tscd.ten,mmsp.sanpham_fk,mmsp.NCC_FK" 
				+" from MayMoc_SanPham  mmsp  left join ERP_TAISANCODINH tscd on mmsp.taisancodinh_fk=tscd.pk_seq"
				+" inner join  ( select SANPHAM_FK  from ERP_YEUCAUKIEMDINH "
				+" where PK_SEQ = 100037) as b on b.SANPHAM_FK = mmsp.SANPHAM_FK "
				+" where NCC_FK is null  "
				+" group by tscd.ma,tscd.ten,mmsp.sanpham_fk,mmsp.NCC_FK ";
		}
		this.rsmmsp= this.db.get(query);
		System.out.println("load may moc "+query);

		// end

		if(!this.loai.equals("2")){
			query = "select a.pk_seq, a.solo, cast(a.pk_seq as varchar(20)) + '-' + a.solo  as KiemDinh, " +
			"sp.ten + ' ' + sp.quycach as spTen, sp.pk_seq as spId " +
			"from Erp_YeuCauKiemDinh a " +
			"inner join erp_Sanpham sp on sp.pk_seq = a.sanpham_fk " +
			"where a.pk_seq = '" + this.ycId + "' ";

			this.rsYeuCauKiemDinh = this.db.getScrol(query);
		}else{

			this.rsLoaiSanPham = this.db.get("SELECT PK_SEQ AS LOAISPID, TEN FROM ERP_LOAISANPHAM WHERE TRANGTHAI = 1");

			if(this.LOAISPID.trim().length() > 0){
				query = "SELECT PK_SEQ AS SPID, MA + ' - ' + TEN AS TEN " +
				"FROM ERP_SANPHAM " +
				"WHERE TRANGTHAI = 1 AND LOAISANPHAM_FK = " + this.LOAISPID + "";
				query += "ORDER BY MA + ' - ' + TEN ";

				this.rsSanPham = this.db.get(query);
			}

			query = "SELECT PK_SEQ AS KHOID, TEN " +
			"FROM KHO WHERE LOAI IN (1,2,3) ";
			this.khoRs = this.db.get(query);

			if(this.spId.length() > 0 & this.khoId.length() > 0){
				query = "SELECT DISTINCT A.SANPHAM_FK, E.MA AS MASP, E.TEN AS TENSP, E.LOAISANPHAM_FK, A.SOLO, A.KHO_FK, A.AVAILABLE " +
				"FROM NHAPP_KHO A  " +
				"LEFT JOIN SANPHAM E ON E.PK_SEQ = A.SANPHAM_FK " + 
				"LEFT JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = E.LOAISANPHAM_FK " + 
				"WHERE A.SANPHAM_FK = '" + this.spId + "' AND A.AVAILABLE > 0 AND A.KHOTT_FK = " + this.khoId + " " +
				"ORDER BY A.SOLO ";
				System.out.println(query);
				this.soloRs = this.db.get(query);
			}
		}
	}


	public String getSoLuongKiemDinh()
	{
		return this.SoLuongKiemDinh;
	}

	public void setSoLuongKiemDinh(String soluong)
	{
		this.SoLuongKiemDinh = soluong;

	}

	public String getsoluongDat()
	{
		return this.soluongDat;
	}


	public void setsoluongDat(String soluongDat)
	{
		this.soluongDat = soluongDat;
	}


	public String getNgayKiem()
	{
		return this.ngaykiem;
	}

	public void setNgayKiem(String ngaykiem)
	{
		this.ngaykiem=ngaykiem;
	}

	public void setRsYeuCauKiemDinh(ResultSet rsYc)
	{
		this.rsYeuCauKiemDinh=rsYc;
	}


	public ResultSet getRsYeuCauKiemDinh()
	{
		return this.rsYeuCauKiemDinh;
	}

	public void setRsSolo(ResultSet soloRs)
	{
		this.soloRs = soloRs;
	}


	public ResultSet getRsSolo()
	{
		return this.soloRs;
	}

	public void setRsKho(ResultSet khoRs)
	{
		this.khoRs = khoRs;
	}


	public ResultSet getRsKho()
	{
		return this.khoRs;
	}

	public String getDinhluong()
	{

		return this.dinhluong;
	}


	public void setDinhluong(String dinhluong)
	{
		this.dinhluong=dinhluong;
	}


	public String getDinhtinh()
	{

		return this.dinhtinh;
	}


	public void setDinhtinh(String dinhtinh)
	{

		this.dinhtinh=dinhtinh;
	}


	public String[] getTieuchi_dinhtinh()
	{

		return this.tieuchi_dinhtinh;
	}


	public void setTieuchi_dinhtinh(String[] tieuchi_dinhtinh)
	{
		this.tieuchi_dinhtinh=tieuchi_dinhtinh;
	}


	public String[] getGhinhan_dinhtinh()
	{
		return this.ghinhan_dinhtinh;
	}


	public void setGhinhan_dinhtinh(String[] ghinhan_dinhtinh)
	{
		this.ghinhan_dinhtinh=ghinhan_dinhtinh;
	}


	public String[] getKetqua_dinhtinh()
	{
		return this.ketqua_dinhtinh;
	}


	public void setKetqua_dinhtinh(String[] ketqua_dinhtinh)
	{
		this.ketqua_dinhtinh=ketqua_dinhtinh;
	}


	public String[] getNguoiSua_dinhtinh()
	{
		return this.nguoisua_dinhtinh;
	}


	public void setNguoiSua_dinhtinh(String[] nguoisua_dinhtinh)
	{
		this.nguoisua_dinhtinh=nguoisua_dinhtinh;
	}

	public boolean checkValid()
	{
		if(Double.parseDouble(soluongDat) > Double.parseDouble(SoLuongKiemDinh))
		{
			this.msg = "Số lượng đạt không được phép lớn hơn số lượng kiểm định";
			return false;
		}
		if(this.ngaykiem.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày kiểm định";
			return false;
		}

		return true;
	}

	public String getNhanhangId() 
	{
		return this.nhId;
	}

	public void setNhanhangId(String nkId)
	{
		this.nhId = nkId;
	}


	public String getDeNghiXuLy() {
		return this.denghixuly;
	}


	public void setDeNghiXuLy(String denghixuly) {
		this.denghixuly = denghixuly;
	}


	public String getSoLuongmau() {

		return this.Soluongmau;
	}


	public void setSoLuongmau(String soluongmau) {

		this.Soluongmau=soluongmau;
	}


	public String getTongsoluongnhap() {

		return this.Tongsoluongnhap;
	}


	public void setTongsoluongnhap(String soluong) {

		this.Tongsoluongnhap=soluong;
	}


	public String getsoluongchuakiem() {

		return this.Soluongchuakiem;
	}


	public void setsoluongchuakiem(String soluong) {

		this.Soluongchuakiem=soluong;
	}


	public String getNgaySanXuat() {

		return this.ngaysanxuat;
	}


	public void setNgaySanXuat(String ngaysanxuat) {

		this.ngaysanxuat=ngaysanxuat;
	}
	public String getNgayNhanHang() {
		return this.ngaynhanhang;
	}

	public void setNgayNhanHang(String ngaynhanhang) {

		this.ngaynhanhang=ngaynhanhang;
	}


	public String getNhaCungCap() {

		return this.nhacungcap;
	}

	public void setNhaCungCap(String nhacungcap) {
		this.nhacungcap=nhacungcap;

	}


	public String getNccId() {

		return this.nccId;
	}


	public void setNccId(String nccid) {
		this.nccId = nccid;
	}


	public void setListHoso(List<IErpHoso> ListHoso) {

		this.listhoso=ListHoso;
	}


	public List<IErpHoso> getListHoso() {

		return this.listhoso;
	}


	public String getThieuhoso() {

		return this.Thieuhoso;
	}


	public void setThieuhoso(String Thieuhoso) {

		this.Thieuhoso=Thieuhoso;
	}

	public ResultSet getRsSanPham()
	{
		return this.rsSanPham;
	}

	public void setRsSanPham(ResultSet rsSanPham)
	{
		this.rsSanPham = rsSanPham;
	}

	public ResultSet getRsLoaiSanPham()
	{
		return this.rsLoaiSanPham;
	}

	public void setRsLoaiSanPham(ResultSet rsLoaiSanPham)
	{
		this.rsLoaiSanPham = rsLoaiSanPham;
	}

	public String getLOAISPID()
	{
		return this.LOAISPID;
	}

	public void setLOAISPID(String loaispId)
	{
		this.LOAISPID = loaispId;
	}


	public boolean CapNhatHoSo() {




		try 
		{
			db.getConnection().setAutoCommit(false);
			//Truoc khi ghi log-- xoa log cu trong ngay cua nguoi su dung va ghi log moi
			String query ="";

			query = " update ERP_YeuCauKiemDinh set  THIEUHOSO='"+this.Thieuhoso+"'"+
			" where pk_seq = '" + this.ycId + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
				db.getConnection().commit();
				return false;
			}
			query=" delete  ERP_YEUCAUKIEMDINH_HOSO  where YEUCAUKIEMDINH_FK ="+this.ycId ;
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
				db.getConnection().commit();
				return false;
			}


			for(int i=0;i<listhoso.size();i++){
				IErpHoso hs=listhoso.get(i);
				query=" insert into ERP_YEUCAUKIEMDINH_HOSO (YEUCAUKIEMDINH_FK,HOSO) values ("+this.ycId+",N'"+hs.getHoso()+"') ";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + query;
					db.getConnection().commit();
					return false;
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(false);
		} 
		catch (Exception e) 
		{
			//System.out.println("5.Exception: " + e.getMessage());
			e.printStackTrace();
			db.update("rollback");
			return false;
		}

		return true;

	}


	public void setIsCapDong(String iscapdong) {

		this.Iscapdong=iscapdong;
	}


	public String getIsCapDong() {

		return this.Iscapdong;
	}


	public void setListSanPhamDuyet(List<IErpSanphamduyet> list) {

		SpDuyetList=list;
	}


	public List<IErpSanphamduyet> getListSanPhamDuyet() {

		return this.SpDuyetList;
	}


	public String getNgayhethan() {

		return this.ngayhethan;
	}


	public void setNgayhethan(String ngayhethan) {

		this.ngayhethan = ngayhethan;
	}


	public String getKyhieukd() {

		return this.kyhieukd;
	}


	public void setKyhieukd(String kyhieukd) {
		this.kyhieukd = kyhieukd;

	}

	@Override
	public ResultSet getRshoachatsp() {
		// TODO Auto-generated method stub
		return this.rshcsp;
	}

	@Override
	public void setRshoachatsp(ResultSet rshcsp) {
		// TODO Auto-generated method stub
		this.rshcsp=rshcsp;
	}

	@Override
	public ResultSet getRsmaymocsp() {
		// TODO Auto-generated method stub
		return this.rsmmsp;
	}

	@Override
	public void setRsmaymocsp(ResultSet rsmmsp) {
		// TODO Auto-generated method stub
		this.rsmmsp=rsmmsp;
	}

	@Override
	public String getloaimuahang() {
		// TODO Auto-generated method stub
		return this.loaimh;
	}

	@Override
	public void setloaimuahang(String loaimh) {
		this.loaimh = loaimh;

	}


}
