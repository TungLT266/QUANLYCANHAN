package geso.traphaco.erp.servlets.phongbansx;

import geso.traphaco.erp.beans.phongbansx.IErpPhongbanSX;
import geso.traphaco.erp.beans.phongbansx.IErpPhongbanSXList;
import geso.traphaco.erp.beans.phongbansx.imp.ErpPhongbanSX;
import geso.traphaco.erp.beans.phongbansx.imp.ErpPhongbanSXList;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpPhongbanSXSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public ErpPhongbanSXSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		IErpPhongbanSXList obj = new ErpPhongbanSXList();
		String ctyId = (String) session.getAttribute("congtyId");
		obj.setUserId(userId);
		obj.setCongtyId(ctyId);

		String action = util.getAction(querystring);
		String khlId = util.getId(querystring);
		String msg = "";

		if (action.trim().equals("delete")) {
			dbutils db = new dbutils();
			String query = "delete Erp_phongbansx where pk_seq = " + khlId
					+ " delete ERP_phongbansx_chitiet where phongban_fk = " + khlId;
			if (!db.update(query)) {
				msg = "Không thể xóa ERP_PHONGBANSX";
			}
			db.shutDown();
		}

		obj.init("");
		obj.setMsg(msg);
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhongBanSX.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		String ctyId = (String) session.getAttribute("congtyId");
		out.println(userId);

		IErpPhongbanSXList obj;

		String action = request.getParameter("action");
		if (action == null)
			action = "";

		if (action.equals("new")) {
			IErpPhongbanSX khl = new ErpPhongbanSX();
			khl.setCongtyId(ctyId);
			khl.setUserId(userId);
			khl.creaters();
			session.setAttribute("csxBean", khl);
			session.setAttribute("userId", userId);

			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpPhongBanSXNew.jsp");
		} else {
			obj = new ErpPhongbanSXList();
			obj.setCongtyId(ctyId);
			obj.setUserId(userId);

			String search = getSearchQuery(request, obj);
			
			System.out.println("Tungtestdii: "+search+" :Tunghetdjieif");

			obj.setUserId(userId);
			obj.init(search);

			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);

			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpPhongBanSX.jsp");
		}
	}

	private String getSearchQuery(HttpServletRequest request, IErpPhongbanSXList obj) {
		Utility util = new Utility();

		String ma = request.getParameter("ma");
		if (ma == null)
			ma = "";
		obj.setMa(ma);

		String diengiai = request.getParameter("diengiai");
		if (diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);

		String trangthai = request.getParameter("trangthai");
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String sql = "select a.pk_seq, a.ma, a.ten, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua "
				+ "from Erp_phongbansx a inner join NhanVien b on a.nguoitao = b.pk_seq "
				+ "inner join nhanvien c on a.nguoisua = c.pk_seq where a.congty_fk = "
				+ obj.getCongtyId();

		if (ma.length() > 0)
			sql += " and a.ma like N'%" + ma + "%' ";

		if (diengiai.length() > 0)
			sql += " and dbo.ftBoDau(a.ten) like N'%"
					+ util.replaceAEIOU(diengiai) + "%' ";

		if (trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";

		sql += " order by a.pk_seq desc ";

		return sql;
	}

	/*private static String insertPhongban(String userId, String congtyId, String nppId) {
		try {
			dbutils db = new dbutils();
			db.getConnection().setAutoCommit(false);

			String query = "";
			query = "insert Erp_phongbansx(ma, ten, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, congty_fk, npp_fk) \n"
					+ "select ma, ten, 1, "
					+ getDateTime()
					+ ", "
					+ userId
					+ ", "
					+ getDateTime()
					+ ", "
					+ userId
					+ ", "
					+ congtyId
					+ ", "
					+ nppId
					+ " \n"
					+ "from erp_phongbansx_temp group by ma, ten";
			System.out.println("1.PB: " + query);
			if (!db.update(query)) {
				db.getConnection().rollback();
				return "Không thể tạo mới Erp_phongbansx " + query;
			}

			query = "insert ERP_phongbansx_chitiet( phongban_fk, diengiai, dinhmuctu, dinhmucden, dvdl_fk ) \n"
					+ "select (select pk_seq from erp_phongbansx where ma = a.ma), a.thongso, dinhmuctu, dinhmucden, pk_seq \n"
					+ "from erp_phongbansx_temp a left join donvidoluong b on a.dvt = b.donvi";

			System.out.println("2.PB: " + query);
			if (!db.update(query)) {
				db.getConnection().rollback();
				return "Không thể chèn ERP_phongbansx_chitiet: " + query;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}*/

	/*private static String insertGiaidoan(String userId, String congtyId, String nppId) {
		try {
			dbutils db = new dbutils();
			db.getConnection().setAutoCommit(false);

			String query = "";
			query = "insert ERP_giaidoansx(ma, ten, huongdanquytrinh, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, congty_fk, npp_fk) "
					+ "select ma, ten, huongdanquytrinh, 1, "
					+ getDateTime()
					+ ", "
					+ userId
					+ ", "
					+ getDateTime()
					+ ", "
					+ userId
					+ ", "
					+ congtyId
					+ ", "
					+ nppId
					+ " \n"
					+ "from erp_giaidoansx_temp group by ma, ten, huongdanquytrinh ";
			System.out.println("1.GD: " + query);
			if (!db.update(query)) {
				db.getConnection().rollback();
				return "Không thể tạo mới ERP_giaidoansx " + query;
			}

			query = "insert ERP_giaidoansx_thietbi( giaidoan_fk, thietbi, hieusuat, honhop, loai ) \n"
					+ "select (select pk_seq from erp_giaidoansx where ma = a.ma), \n"
					+ "case loai when 0 then (select pk_seq from erp_taisancodinh where ma = a.thietbi and congty_fk = "
					+ congtyId
					+ ") \n"
					+ "else (select pk_seq from erp_congcudungcu where ma = a.thietbi and congty_fk = "
					+ congtyId
					+ ") end, hieusuat, honhop, loai \n"
					+ "from erp_giaidoansx_temp a group by a.ma, a.thietbi, hieusuat, honhop, loai";
			System.out.println("2.GD: " + query);
			if (!db.update(query)) {
				db.getConnection().rollback();
				return "Không thể chèn ERP_giaidoansx_thietbi: " + query;
			}

			query = "insert ERP_giaidoansx_thietbi_chitiet( giaidoan_fk, thietbi_fk, stt, thongsokythuat, sanpham_fk, dinhmuctu, dinhmucden, dvdl_fk ) \n"
					+ "select (select pk_seq from erp_giaidoansx where ma = a.ma), \n"
					+ "(select tb.pk_seq from ERP_giaidoansx_thietbi tb left join erp_taisancodinh ts on tb.thietbi = ts.pk_seq left join erp_congcudungcu cc on tb.thietbi = cc.pk_seq \n"
					+ "where giaidoan_fk = (select pk_seq from erp_giaidoansx where ma = a.ma) and (case tb.loai when 0 then ts.ma else cc.ma end) = a.thietbi), \n"
					+ "stt, thongsokythuat, sp.pk_seq, dinhmuctu, dinhmucden, dv.pk_seq \n"
					+ "from erp_giaidoansx_temp a \n"
					+ "left join donvidoluong dv on a.dvt = dv.donvi \n"
					+ "left join erp_sanpham sp on a.sanpham = sp.ma and sp.congty_fk = "
					+ congtyId;
			System.out.println("3.GD: " + query);
			if (!db.update(query)) {
				db.getConnection().rollback();
				return "Không thể chèn ERP_giaidoansx_thietbi_chitiet: "
						+ query;
			}

			query = "insert ERP_giaidoansx_thongsokhac( giaidoan_fk, thongso ) "
					+ "select (select pk_seq from erp_giaidoansx where ma = a.ma), thongsokhac \n"
					+ "from erp_giaidoansx_temp a group by a.ma, thongsokhac";
			System.out.println("4.GD: " + query);
			if (!db.update(query)) {
				db.getConnection().rollback();
				return "Không thể chèn ERP_giaidoansx_thongsokhac: " + query;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}*/

	/*private static String insertCongdoan(String userId, String congtyId, String nppId) {
		try {
			dbutils db = new dbutils();
			db.getConnection().setAutoCommit(false);

			String query = "";
			query = "insert Erp_CongDoanSanXuat_Giay(DinhLuong, DinhTinh, DienGiai, NhaMay_FK, SoNhanCong, TrangThai, ngaytao, nguoitao, ngaysua, nguoisua, CongTy_FK, npp_fk) \n"
					+ "select kiemdinhdinhluong, kiemdinhdinhtinh, a.diengiai, nm.pk_seq, sonhancong, 1, "
					+ getDateTime()
					+ ", "
					+ userId
					+ ", "
					+ getDateTime()
					+ ", "
					+ userId
					+ ", "
					+ congtyId
					+ ", "
					+ nppId
					+ " \n"
					+ "from Erp_CongDoanSanXuat_Giay_temp a inner join erp_nhamay nm on a.nhamay = nm.manhamay group by kiemdinhdinhluong, kiemdinhdinhtinh, a.diengiai, nm.pk_seq, sonhancong";
			System.out.println("1.CD: " + query);
			if (!db.update(query)) {
				db.getConnection().rollback();
				return "Không thể tạo mới Erp_CongDoanSanXuat_Giay " + query;
			}

			query = "insert into ERP_CONGDOANSANXUAT_GIAIDOAN_GIAY(CONGDOANSANXUAT_FK, PHONGBAN_FK, GIAIDOAN_FK, STT)"
					+ "select (select pk_seq from Erp_CongDoanSanXuat_Giay where diengiai = a.diengiai), pb.pk_seq, gd.pk_seq, a.stt \n"
					+ "from Erp_CongDoanSanXuat_Giay_temp a \n"
					+ "inner join erp_phongbansx pb on a.phongban = pb.ma \n"
					+ "inner join erp_giaidoansx gd on a.giaidoan = gd.ma";
			System.out.println("2.CD: " + query);
			if (!db.update(query)) {
				db.getConnection().rollback();
				return "Không thể tạo mới ERP_CONGDOANSANXUAT_GIAIDOAN_GIAY "
						+ query;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}*/

	/*private static String insertKichban(String userId, String congtyId, String nppId) {
		try {
			dbutils db = new dbutils();
			db.getConnection().setAutoCommit(false);

			String query = "";
			query = "INSERT INTO Erp_KichBanSanXuat_Giay(SanPham_FK, MaSanPham, DienGiai, SoLuongChuan, SONGAYSANXUAT, TrangThai, NgayTao, NguoiTao, NgaySua, NguoiSua, CongTy_FK, NPP_FK) "
					+ "select b.pk_seq, masanpham, a.diengiai, soluongchuan, songaysanxuat, 1, "
					+ getDateTime()
					+ ", "
					+ userId
					+ ", "
					+ getDateTime()
					+ ", "
					+ userId
					+ ", "
					+ congtyId
					+ ", "
					+ nppId
					+ " \n"
					+ "from Erp_KichBanSanXuat_Giay_temp a \n"
					+ "inner join erp_sanpham b on a.masanpham = b.ma and b.CONGTY_FK = "
					+ congtyId
					+ " \n"
					+ "group by b.pk_seq, masanpham, a.diengiai, soluongchuan, songaysanxuat";
			System.out.println("1.KB: " + query);
			if (!db.update(query)) {
				db.getConnection().rollback();
				return "Không thể tạo mới Erp_CongDoanSanXuat_Giay " + query;
			}

			query = "insert into Erp_KichBanSanXuat_CongDoanSanXuat_Giay(CongDoanSanXuat_FK, KichBanSanXuat_FK, DanhMucVattu_FK, ThoiGian, ThuTu, soluongdm, nhapkho) "
					+ "select b.pk_seq, (select pk_seq from Erp_KichBanSanXuat_Giay where diengiai = a.diengiai), c.pk_seq, thoigian, thutu, a.soluong, nhapkho \n"
					+ "from Erp_KichBanSanXuat_Giay_temp a \n"
					+ "inner join Erp_CongDoanSanXuat_Giay b on a.congdoan = b.diengiai \n"
					+ "inner join ERP_DANHMUCVATTU c on a.mabom = tenbom";
			System.out.println("2.KB: " + query);
			if (!db.update(query)) {
				db.getConnection().rollback();
				return "Không thể tạo mới Erp_KichBanSanXuat_CongDoanSanXuat_Giay "
						+ query;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}*/

	/*public static void main(String[] args) {
		try {
			String msg = "";
			
			 * msg = insertPhongban("100002", "100027", "100002"); //user,
			 * congty, npp System.out.println("insertPhongban" + msg);
			 * if(msg.trim().length() <= 0) { msg = insertGiaidoan("100002",
			 * "100027", "100002"); } System.out.println("insertGiaidoan" +
			 * msg); if(msg.trim().length() <= 0) { msg =
			 * insertCongdoan("100002", "100027", "100002"); }
			 * System.out.println("insertCongdoan" + msg);
			 
			if (msg.trim().length() <= 0) {
				msg = insertKichban("100002", "100027", "100002");
			}
			System.out.println("insertKichban" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	private static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
