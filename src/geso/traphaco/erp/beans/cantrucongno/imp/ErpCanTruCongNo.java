package geso.traphaco.erp.beans.cantrucongno.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.ErpThanhtoanhoadonCanTru;
import geso.traphaco.erp.beans.thutien.IHoadon;
import geso.traphaco.erp.beans.thutien.imp.ErpThutienCanTru;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ErpCanTruCongNo 
{
	String userId = "";
	String congtyId = "";
	String nppdangnhap = "";
	String trangthai = "";
	String soChungTu = "";
	String tienteId = "";
	ResultSet tienteRs;
	String dienGiaiCT ="";
	String loaithanhtoan = "";

	String nccId = "";
	String khId = "";
	String nccTen = "";
	String khTen = "";
	ResultSet rsCongNoNcc;
	ResultSet rsCongNoKh;
	dbutils db;
	ErpThutienCanTru thutienBean;
	ErpThanhtoanhoadonCanTru thanhtoanhoadonBean;

	ResultSet thanhtoan;
	String[] thanhtoanIds;

	ResultSet thutien;
	String[] thutienIds;

	private String sotienTT = "0";

	private String id = "";

	private String thanhToanId = "";

	private String thanhToanIds = "";

	private String thuTienId = "";

	private String ngayTao = "";

	String isNpp = "";
	
	public String getSoChungTu() {
		return soChungTu;
	}

	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}

	public String getTienteId() {

		return this.tienteId;
	}

	public void setTienteId(String ttId) {

		this.tienteId = ttId;
		this.thutienBean.setTienteId(ttId);
		this.thanhtoanhoadonBean.setTienteId(ttId);
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getLoaiThanhToan() {
		return loaithanhtoan;
	}

	public void setLoaiThanhToan(String loaithanhtoan) {
		this.loaithanhtoan = loaithanhtoan;
	}

	String msg = "";

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getNgayTao() {
		if ("".equals(ngayTao)) {
			ngayTao = getDateTime();
		}
		return ngayTao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		init();
	}

	public String getThanhToanId() {
		return thanhToanId;
	}

	public void setThanhToanId(String thanhToanId) {
		this.thanhToanId = thanhToanId;
	}

	public String getThanhToanIds() {
		return thanhToanIds;
	}

	public void setThanhToanIds(String thanhToanIds) {
		this.thanhToanIds = thanhToanIds;
	}

	public String getThuTienId() {
		return thuTienId;
	}

	public void setThuTienId(String thuTienId) {
		this.thuTienId = thuTienId;
	}

	public void setNgayTao(String ngayTao) {
		this.ngayTao = ngayTao;
	}

	public ResultSet getTienteRs() {

		return this.tienteRs;
	}

	public void setTienteRs(ResultSet tienteRs) {

		this.tienteRs = tienteRs;
	}

	public ErpCanTruCongNo() {
		// TODO Auto-generated constructor stub
		db = new dbutils();
		thutienBean = new ErpThutienCanTru();
		thutienBean.setTienteId(this.tienteId);

		thanhtoanhoadonBean = new ErpThanhtoanhoadonCanTru();
		thanhtoanhoadonBean.setTienteId(this.tienteId);
		thanhtoanhoadonBean.setLoaiThanhToan(this.loaithanhtoan);
		thanhtoanhoadonBean.setcongtyId(this.congtyId);
		thanhtoanhoadonBean.setnppdangnhap(this.nppdangnhap);
		thanhtoanhoadonBean.setTienteId(this.tienteId);

		thutienBean.setHtttId("100000");// set hinh thuc thanh toan = tien mat
		thutienBean.setNoidungId("100000");// set noi dung = thu tien ban hang
		thutienBean.setNgayghiso(getDateTime());// set ngay ghi so
		thutienBean.setNgaychungtu(getDateTime());
		thutienBean.setcongtyId(this.congtyId);
		
		thanhtoanhoadonBean.setHtttId("100000");// set hinh thuc thanh toan =
												// tien mat
		thanhtoanhoadonBean.setNoidungtt("");
		thanhtoanhoadonBean.setNgayghinhan(getDateTime());// setNgay ghi nhan*/

		this.tienteRs = db.get("select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE ");
		this.isNpp = "";
		this.dienGiaiCT ="";
	}

	public ErpCanTruCongNo(String Id) {
		// TODO Auto-generated constructor stub
		this.id = Id;
		this.isNpp = "";
		this.dienGiaiCT ="";
		db = new dbutils();
		// init();
	}

	public void init() {

		this.getNppInfo();
		this.tienteRs = db.get("select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE ");
		
		
		// isnpp = 0 => Khách hàng; isnpp = 1 => npp; isnpp = 2 => nhân viên
		String query = "SELECT CTCN.*, NCC.TEN AS NCC, "
				+ "case when isNPP = 0 then (KH.TEN + ' -- 0' ) "
				+ "when isNPP = 1 then (NPP.TEN + ' -- 1') "
				+ "when isNPP = 2 then (NV.TEN + ' -- 2') end AS KH, "
				+ "isnull(CTCN.isnpp,0) isnpp ,ISNULL(CTCN.DIENGIAI,'') AS DIENGIAI \n"
						+ "FROM ERP_CANTRUCONGNO CTCN \n"
						+ "		LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = CTCN.NCC_FK \n"
						+ "		LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = CTCN.KH_FK \n"
						+ "     LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = CTCN.KH_FK \n"
						+ "     LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = CTCN.KH_FK \n"
						+ "WHERE CTCN.PK_SEQ = '"+this.id+"' AND CTCN.CONGTY_FK = "+ this.congtyId;

		System.out.println("Init display CTCN: " + query);

		ResultSet resultSet = db.get(query);
		try {

			if (resultSet != null) {
				while (resultSet.next()) {

					this.khId = resultSet.getString("KH_FK");
					this.id = resultSet.getString("PK_SEQ");
					this.nccId = resultSet.getString("NCC_FK");
					this.thanhToanId = resultSet.getString("THANHTOAN_FK");
					this.thuTienId = resultSet.getString("THUTIEN_FK");
					this.sotienTT = ""+ Float.parseFloat(resultSet.getString("TONGTIEN"));
					this.ngayTao = resultSet.getString("NGAYCANTRU");
					this.nccTen = resultSet.getString("NCC");
					this.khTen = resultSet.getString("KH");
					this.trangthai = resultSet.getString("TRANGTHAI");
					this.soChungTu = resultSet.getString("SOCHUNGTU");
					this.tienteId = resultSet.getString("TIENTE_FK");
					this.isNpp =  resultSet.getString("isnpp");
					this.dienGiaiCT = resultSet.getString("DIENGIAI");

				}
			}

			this.thutienBean = new ErpThutienCanTru(this.thuTienId);
			thutienBean.setUserId(this.userId);
			thutienBean.setTienteId(this.tienteId);
			thutienBean.setcongtyId(this.congtyId);
			thutienBean.setIsNPP(this.isNpp);
			this.thutienBean.init();

			this.thanhtoanhoadonBean = new ErpThanhtoanhoadonCanTru(this.thanhToanId);
			thanhtoanhoadonBean.setUserId(this.userId);
			thanhtoanhoadonBean.setNhanVienId(this.userId);
			thanhtoanhoadonBean.setTienteId(this.tienteId);
			thanhtoanhoadonBean.setcongtyId(this.congtyId);
			thanhtoanhoadonBean.setisNPP(this.isNpp);

			this.thanhtoanhoadonBean.init();

		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}

	public ResultSet getCanTruCongNoList() {

		String query = "SELECT ERP_CANTRUCONGNO.PK_SEQ, ERP_NHACUNGCAP.TEN AS NCC, ERP_KHACHHANG.Ten AS KH, "
				+ "ERP_CANTRUCONGNO.NGAYCANTRU , ERP_CANTRUCONGNO.TONGTIEN, ERP_CANTRUCONGNO.TRANGTHAI \n"
				+ " from ERP_CANTRUCONGNO\n"
				+ " LEFT JOIN ERP_NHACUNGCAP ON ERP_NHACUNGCAP.PK_SEQ = ERP_CANTRUCONGNO.NCC_FK\n"
				+ " LEFT JOIN ERP_KHACHHANG ON ERP_KHACHHANG.PK_SEQ = ERP_CANTRUCONGNO.KH_FK"
				+ " ORDER BY  ERP_CANTRUCONGNO.NGAYCANTRU DESC";

		System.out.println("query can tru cong no: " + query);
		return db.get(query);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
		if (thanhtoanhoadonBean == null) {
			thanhtoanhoadonBean = new ErpThanhtoanhoadonCanTru();
		}
		if (thutienBean == null) {
			thutienBean = new ErpThutienCanTru();
		}
		thanhtoanhoadonBean.setUserId(userId);
		thutienBean.setUserId(userId);
	}

	public List<IHoadon> getListHoaDonKhachHang() 
	{
		thutienBean.getHoadonRs().clear();
		if (thutienBean.getId() == null)
			System.out.println("Id is null");
		if (thutienBean.getTrangthai() == null)
			System.out.println("Trangthai is null");

		if (thutienBean.getId().length() > 0 && !thutienBean.getTrangthai().equals("0")) {
			thutienBean.init();
		} else {
			thutienBean.setNppId(this.khId);
			thutienBean.setIsNPP(this.isNpp);
			thutienBean.createRs();
		}

		return thutienBean.getHoadonRs();
	}

	public List<IHoadon> getListHoaDonKhachHang2() {
		thutienBean.getHoadonRs().clear();
		thutienBean.init2();
		return thutienBean.getHoadonRs();
	}

	public void setListHoaDonKhachHang(List<IHoadon> listHoaDonKH) {
		thutienBean.setHoadonRs(listHoaDonKH);
	}

	public List<geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon> getListHoaDonNCC() {

		thanhtoanhoadonBean.getHoadonRs().clear();		
		
		if (thanhtoanhoadonBean.getId().length() == 0 || this.trangthai.equals("0")) {
			thanhtoanhoadonBean.setNccId(this.nccId);			
			thanhtoanhoadonBean.setLoaiThanhToan(loaithanhtoan);
			thanhtoanhoadonBean.setcongtyId(this.congtyId);
			thanhtoanhoadonBean.createRS2();
		}
		
else {
			thanhtoanhoadonBean.setNccId(this.nccId);
			thanhtoanhoadonBean.setLoaiThanhToan(loaithanhtoan);
			thanhtoanhoadonBean.setcongtyId(this.congtyId);
//			thanhtoanhoadonBean.createRS3();
			thanhtoanhoadonBean.createRS2();
		}
		return thanhtoanhoadonBean.getHoadonRs();
	}

	public String getNccTen() {
		return nccTen;
	}

	public void setNccTen(String nccTen) {
		this.nccTen = nccTen;
	}

	public String getKhTen() {
		return khTen;
	}

	public void setKhTen(String khTen) {
		this.khTen = khTen;
	}

	public String getNccId() {
		return nccId;
	}

	public void setNccId(String nccId) {
		this.nccId = nccId;
	}

	public String getKhId() {
		return khId;
	}

	public void setKhId(String khId) {
		this.khId = khId;

	}

	public String getcongtyId() {
		return congtyId;
	}

	public void setcongtyId(String congtyId) {
		this.congtyId = congtyId;

		if (thanhtoanhoadonBean == null) {
			thanhtoanhoadonBean = new ErpThanhtoanhoadonCanTru();
		}
		if (thutienBean == null) {
			thutienBean = new ErpThutienCanTru();
		}
		thanhtoanhoadonBean.setUserId(userId);
		thutienBean.setUserId(userId);

		thanhtoanhoadonBean.setcongtyId(this.congtyId);
		thutienBean.setcongtyId(this.congtyId);
	}

	public String getnppdangnhap() {
		return nppdangnhap;
	}

	public void setnppdangnhap(String nppdangnhap) {
		this.nppdangnhap = nppdangnhap;
	}

	public ResultSet getRsCongNoNcc() {
		CreateRsCongNoNcc(this.nccId);
		return rsCongNoNcc;
	}

	private void CreateRsCongNoNcc(String nccId) {

		System.out.println("NCC ID IN BEAN: " + this.nccId);
		if (nccId == null | nccId.equals("")) {
			this.rsCongNoNcc = null;
			return;
		}
		String query = "(\n"
				+ "	SELECT\n"
				+ "		hoadon.pk_seq,\n"
				+ "		hoadon.kyhieu,\n"
				+ "		hoadon.sohoadon,\n"
				+ "		hoadon.ngayhoadon,\n"
				+ "		hoadon.sotienAVAT,\n"
				+ "		isnull(dathanhtoan.DATHANHTOAN, '') AS DATHANHTOAN,\n"
				+ "    hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0')  AS conlai\n"
				+ "		\n" + "	FROM\n" + "		(\n" + "			SELECT\n"
				+ "				a.pk_seq,\n" + "				a.kyhieu,\n" + "				a.sohoadon,\n"
				+ "				a.ngayhoadon,\n" + "				a.sotienAVAT\n" + "			FROM\n"
				+ "				ERP_HOADONNCC a\n"
				+ "			INNER JOIN ERP_PARK b ON a.park_fk = b.pk_seq\n"
				+ "			WHERE\n" + "				b.ncc_fk = '"
				+ this.nccId
				+ "'\n"
				+ "			AND b.trangthai = '2'\n"
				+ "			AND a.trangthai = '0'\n"
				+ "		) hoadon\n"
				+ "	LEFT JOIN (\n"
				+ "		SELECT\n"
				+ "			TTHD.HOADON_FK,\n"
				+ "			SUM (TTHD.SOTIENTT) AS DATHANHTOAN\n"
				+ "		FROM\n"
				+ "			ERP_THANHTOANHOADON_HOADON TTHD\n"
				+ "		INNER JOIN ERP_THANHTOANHOADON TT ON TTHD.THANHTOANHD_FK = TT.PK_SEQ\n"
				+ "		WHERE\n"
				+ "			TT.TRANGTHAI != '2'\n"
				+ "		AND TTHD.HOADON_FK IN (\n"
				+ "			SELECT\n"
				+ "				pk_seq\n"
				+ "			FROM\n"
				+ "				ERP_HOADONNCC\n"
				+ "			WHERE\n"
				+ "				trangthai = 0\n"
				+ "		)\n"
				+ "		GROUP BY\n"
				+ "			HOADON_FK\n"
				+ "	) dathanhtoan ON hoadon.pk_seq = dathanhtoan.hoadon_fk\n"
				+ "	WHERE\n"
				+ "		hoadon.sotienAVAT - isnull(\n"
				+ "			dathanhtoan.DATHANHTOAN,\n"
				+ "			'0'\n"
				+ "		) > 0\n"
				+ ")";
		System.out.println("SELECT CONG NO NCC: " + query);
		this.rsCongNoNcc = db.get(query);

	}

	public void setRsCongNoNcc(ResultSet rsCongNoNcc) {
		this.rsCongNoNcc = rsCongNoNcc;
	}

	public ResultSet getRsCongNoKh() {
		CreateRsCongNoKH();
		return rsCongNoKh;
	}

	private void CreateRsCongNoKH() {

		System.out.println("KH ID IN BEAN: " + this.khId);
		if (this.khId == null || this.khId.equals("")) {
			this.rsCongNoKh = null;
			return;
		}
		String query = "( select hoadon.pk_seq, hoadon.kyhieu, hoadon.sohoadon,"
				+ " hoadon.ngayhoadon,hoadon.sotienAVAT, hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') as conlai,"
				+ " isnull(dathanhtoan.DATHANHTOAN, '') as DATHANHTOAN"
				+ " from ( select pk_seq, pk_seq as kyhieu, sohoadon, ngayxuathd as ngayhoadon, tongtienAVAT as sotienAVAT"
				+ " from ERP_HOADON where NPP_FK = '"
				+

				this.khId
				+

				"' and trangthai = '1'  ) hoadon "
				+ " left join (  select HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) as DATHANHTOAN  "
				+ " from   (  select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN"
				+ " from ERP_XOAKHTRATRUOC_HOADON TTHD inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ"
				+ " where TT.TRANGTHAI not in (2)"
				+ " group by HOADON_FK "
				+ " union all"
				+ " select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN "
				+ " from ERP_THUTIEN_HOADON TTHD"
				+ " inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ "
				+ " where TT.TRANGTHAI not in (2) "
				+ " group by HOADON_FK  )  HOADONDATT "
				+ " group by HOADON_FK   )  dathanhtoan on hoadon.pk_seq = dathanhtoan.hoadon_fk"
				+ " where hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') > 0 )"
				+ " order by ngayhoadon asc ";
		System.out.println("SELECT CONG NO KH: " + query);
		this.rsCongNoKh = db.get(query);
	}

	public void setRsCongNoKh(ResultSet rsCongNoKh) {
		this.rsCongNoKh = rsCongNoKh;
	}

	public boolean save() {
		
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa"+this.nppdangnhap);

		thanhtoanhoadonBean.setNccId(this.nccId);
		thanhtoanhoadonBean.setUserId(this.userId);
		thanhtoanhoadonBean.setnppdangnhap(this.nppdangnhap);
		thanhtoanhoadonBean.setisNPP(this.isNpp);

		thutienBean.setNppId(this.khId);
		thutienBean.setUserId(this.userId);
		thutienBean.setnppdangnhap(this.nppdangnhap);
		thutienBean.setIsNPP(this.isNpp);

		if (thanhtoanhoadonBean.createTTHD()) {

			if (thutienBean.createTTHD()) {

				return this.createCTCN();

			} else {
				this.msg = thutienBean.getMsg();

			}
		} else {
			this.msg = thanhtoanhoadonBean.getMsg();
		}

		return false;

	}

	private boolean createCTCN() {

		String query = String.format("INSERT INTO [ERP_CANTRUCONGNO] ([CANTRUCONGNO_FK] ,[NCC_FK] ,[KH_FK] ,[TRANGTHAI] \n"
						+ "           ,[THANHTOAN_FK] \n"
						+ "           ,[THUTIEN_FK] \n"
						+ "           ,[NGAYCANTRU] \n"
						+ "           ,[NGUOISUA] \n"
						+ "           ,[NGUOITAO] \n"
						+ "           ,[TONGTIEN] \n"
						+ "           ,[SOCHUNGTU] \n"
						+ "			,[TIENTE_FK] \n"
						+ "			,[CONGTY_FK] \n"
						+ "			,[NPP_FK], [isNPP],[DIENGIAI] )\n"
						+ "     VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s', '%s','%s', '%s', '%s',N'%s')\n","ID", this.nccId, this.khId, "0",
						this.thanhtoanhoadonBean.getTthdCurrent(),
						this.thutienBean.getId(), getDateTime(), this.userId,
						this.userId, this.sotienTT, this.soChungTu,
						this.tienteId, this.congtyId, this.nppdangnhap, this.isNpp,this.dienGiaiCT);
		System.out.println("INSERT CAN TRU CONG NO: " + query);
		if (db.update(query)) 
		{
			this.msg = "Không thể cập nhật ";
		}
		query = "select scope_identity() as tthdId";
		
		String cantrucongno_fk="";
		ResultSet rsTthd = db.get(query);			
		try
		{
			if(rsTthd.next())
			{
				cantrucongno_fk = rsTthd.getString("tthdId");
			
			}	rsTthd.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		query="UPDATE ERP_THUTIEN SET CANTRUCONGNO_FK="+cantrucongno_fk+" where pk_seq IN (SELECT THUTIEN_FK FROM ERP_CANTRUCONGNO WHERE PK_SEQ="+cantrucongno_fk+") ";
		if (db.update(query)) 
		{
			this.msg = "Không thể cập nhật ";
		}
		
		query="UPDATE ERP_THANHTOANHOADON SET CANTRUCONGNO_FK="+cantrucongno_fk+" where pk_seq IN (SELECT THANHTOAN_FK FROM ERP_CANTRUCONGNO WHERE PK_SEQ="+cantrucongno_fk+") ";
		if (db.update(query)) 
		{
			this.msg = "Không thể cập nhật ";
		}
		return true;
	}

	public void setListHoaDonNCC(
			List<geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon> hdList) {

		thanhtoanhoadonBean.setHoadonRs(hdList);
	}

	public void setSoTienThanhToan(String sotienthanhtoan) {

		this.sotienTT = sotienthanhtoan;
		thutienBean.setSotientt(sotienthanhtoan);
		thanhtoanhoadonBean.setSotientt(sotienthanhtoan);
	}

	public String getSoTienThanhToan() {
		if (sotienTT == null || "".equals(sotienTT.trim()))
			return "0";
		return this.sotienTT;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// ErpCanTruCongNo canTruCongNo = new ErpCanTruCongNo();
		// canTruCongNo.setUserId("100211");
		// canTruCongNo.setKhId("106535");
		// canTruCongNo.setNccId("100004");

		// List<IHoadon> hoadons = canTruCongNo.getListHoaDonKhachHang();
		// for (IHoadon iHoadon : hoadons) {
		// System.out.println(iHoadon.getSo());
		// }

		// List<geso.salesup.erp.beans.thanhtoanhoadon.IHoadon> hoadons =
		// canTruCongNo.getListHoaDonNCC();
		// for (geso.salesup.erp.beans.thanhtoanhoadon.IHoadon iHoadon :
		// hoadons) {
		// System.out.println(iHoadon.getSo());
		// }
		//
		// ResultSet resultSet = new ErpCanTruCongNo().getCanTruCongNoList();
		// try {
		// while (resultSet.next()) {
		// System.out.println(resultSet.getString("PK_SEQ"));
		// System.out.println(resultSet.getString("NCC"));
		// System.out.println(resultSet.getString("KH"));
		// System.out.println(resultSet.getString("TONGTIEN"));
		// System.out.println(resultSet.getString("NGAYCANTRU"));
		// }
		// } catch (SQLException e) {
		// 
		// e.printStackTrace();
		// }

		String a = "asndas";
		String[] result = a.split("-");
		for (int i = 0; i < result.length; i++) {
			System.out.println("" + i + ":" + result[i]);
		}

	}

	public String delete() {

		String result = thutienBean.delete();

		if (result.trim().equals("")) {
			result += thanhtoanhoadonBean.delete();

			if (result.trim().equals("")) {
				try {
					db.getConnection().setAutoCommit(false);

					db.update("delete ERP_CANTRUCONGNO where pk_seq = '"
							+ this.id + "' AND TRANGTHAI = 0");

					db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
					db.shutDown();

					return "";
				} catch (SQLException e) {
					db.shutDown();
					result+= "Khong the xoa Can tru cong no";
				}
			}
		}
		return result;

	}

	public boolean chot() {

		boolean result = false;
		if (thutienBean.checkChot2() && thanhtoanhoadonBean.CheckChot2()) {

			result = thutienBean.chotTTHD(userId);

			if (result) {
				result = thanhtoanhoadonBean.chotTTHD(this.userId);

				if (result) {
					try {
						db.getConnection().setAutoCommit(false);

						Utility util = new Utility();

						// CÀI KẾ TOÁN
						String query = 
								  "select ct.PK_SEQ, ct.NGAYCANTRU, ct.TIENTE_FK, ct.TONGTIEN, case when ct.isNPP = 0 then kh.PK_SEQ else npp.PK_SEQ end as khId, ncc.PK_SEQ as nccId, "
								+ "       case when ct.isNPP = 0 then  kh.TAIKHOAN_FK  else (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN= npp.TAIKHOAN_FK) end as taikhoanKH, ncc.TAIKHOAN_FK as taikhoanNCC "
								+ "		from ERP_CANTRUCONGNO ct"
								+ "     inner join ERP_NHACUNGCAP ncc on ct.NCC_FK = ncc.PK_SEQ "
								+ "     left join KHACHHANG kh on ct.KH_FK = kh.PK_SEQ "
								+ "		left join NHAPHANPHOI npp on ct.KH_FK = npp.PK_SEQ "
								+ " where ct.PK_SEQ = " + this.id + " ";
						
						System.out.println(query);
						ResultSet rs = db.get(query);

						if (rs != null) {
							while (rs.next()) {
								String tienteId = rs.getString("TIENTE_FK");
								String ngayghinhan = rs.getString("NGAYCANTRU");
								String nam = ngayghinhan.substring(0, 4);
								String thang = ngayghinhan.substring(5, 7);
								String khId = rs.getString("khId");
								String nccId = rs.getString("nccId");

								String taikhoanNO = rs.getString("taikhoanNCC") == null ? "": rs.getString("taikhoanNCC");
								String taikhoanCO = rs.getString("taikhoanKH") == null ? "": rs.getString("taikhoanKH");

								double tongtien = rs.getDouble("TONGTIEN");

								if (tongtien > 0) {
									if (taikhoanNO.trim().length() <= 0	|| taikhoanCO.trim().length() <= 0) {
										this.msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
										db.getConnection().rollback();
										return false;
									}

									this.msg = util.Update_TaiKhoan(db, thang,nam, ngayghinhan, ngayghinhan,"Cấn trừ công nợ", this.id,taikhoanNO, taikhoanCO, "",
											Double.toString(tongtien),Double.toString(tongtien),"Nhà cung cấp", nccId,"Khách hàng", khId, "0", "", "",
											tienteId, "", "1",Double.toString(tongtien),Double.toString(tongtien), "");

									if (this.msg.trim().length() > 0) {
										rs.close();
										db.getConnection().rollback();
										return false;
									}
								}

							}
							rs.close();
						}

						db.update("update ERP_CANTRUCONGNO set trangthai = '1' where pk_seq = '"+ this.id + "'");

						db.getConnection().commit();
						db.getConnection().setAutoCommit(true);
						db.shutDown();

						result = true;
					} catch (SQLException e) {
						db.shutDown();
						result = false;
					}
				}
			}

		} else {
			this.msg = "Không thể chốt - ";
		}
		this.msg += thutienBean.getMsg() + "   " + thanhtoanhoadonBean.getMsg();
		return result;
	}

	public void update() {
		
		this.thutienBean.setIsNPP(this.isNpp);		
		this.thanhtoanhoadonBean.setisNPP(this.isNpp);
		
		
		this.thutienBean.updateTTHD();
		// ĐỐI VỚI NHÀ CUNG CẤP => TẠO 1 PHIẾU CHI TƯƠNG ĐƯƠNG
		
		this.thanhtoanhoadonBean.updateTTHD();
		
		String query = String.format("UPDATE ERP_CANTRUCONGNO SET"
				+ " SOCHUNGTU = '%s'," + " NGAYCANTRU = '%s',"
				+ " NCC_FK = %s," + " KH_FK = %s," + " THUTIEN_FK = %s,"
				+ " THANHTOAN_FK = %s," + " TONGTIEN = %s ,"
				+ " NGUOISUA = %s," + " TIENTE_FK = %s," +"isNPP = %s, " +"DIENGIAI = N'%s' "
				+ " WHERE PK_SEQ = '%s'", this.soChungTu, this.ngayTao,
				this.nccId, this.khId, this.thuTienId, this.thanhToanId,
				this.sotienTT, this.userId, this.tienteId,this.isNpp,this.dienGiaiCT,this.id);
		System.out.println("UPDATE CAN TRU CONG NO: " + query);
		db.update(query);
		
		
		query="UPDATE ERP_THUTIEN SET CANTRUCONGNO_FK="+this.id+" where pk_seq IN (SELECT THUTIEN_FK FROM ERP_CANTRUCONGNO WHERE PK_SEQ="+this.id+") ";
		if (db.update(query)) 
		{
			this.msg = "Không thể cập nhật ";
		}
		
		query="UPDATE ERP_THANHTOANHOADON SET CANTRUCONGNO_FK="+this.id+" where pk_seq IN (SELECT THANHTOAN_FK FROM ERP_CANTRUCONGNO WHERE PK_SEQ="+this.id+") ";
		if (db.update(query)) 
		{
			this.msg = "Không thể cập nhật ";
		}
	}

	private void getNppInfo() {
		// Phien ban moi
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap = util.getIdNhapp(this.userId);
	}
	
	public String getisNPP() {
		return isNpp;
	}

	public void setisNPP(String isNpp) {
		this.isNpp = isNpp;
	}
	public void DBClose()
	{
		this.db.shutDown();
	}
	public String getDienGiaiCT(){
		return this.dienGiaiCT;
	}
	public void setDienGiaiCT(String dienGiaiCT){
		this.dienGiaiCT = dienGiaiCT;
	}
}
