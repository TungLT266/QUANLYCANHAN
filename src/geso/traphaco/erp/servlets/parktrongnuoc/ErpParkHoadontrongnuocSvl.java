package geso.traphaco.erp.servlets.parktrongnuoc;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;

import geso.traphaco.erp.beans.parktrongnuoc.IErpPark;
import geso.traphaco.erp.beans.parktrongnuoc.IErpParkList;
import geso.traphaco.erp.beans.parktrongnuoc.imp.ErpPark;
import geso.traphaco.erp.beans.parktrongnuoc.imp.ErpParkList;

import geso.traphaco.erp.beans.parktrongnuoc.IErpHoadon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpParkHoadontrongnuocSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpParkHoadontrongnuocSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IErpParkList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);

		String nhId = util.getId(querystring);

		obj = new ErpParkList();

		if (action.equals("delete")) {
			String msg = Delete(nhId);
		
			obj.setUserId(userId);
			obj.setCongtyId((String) session.getAttribute("congtyId"));
			obj.setnppdangnhap(util.getIdNhapp(userId));
			GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			obj.init("");
			obj.setMsg(msg);
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuoc.jsp";
			response.sendRedirect(nextJSP);
			
			
			
		} else if (action.equals("chot")) {
				dbutils db = new dbutils();
			/*	String msg = obj.checkSoHoaDon(nhId);*/
				obj.setUserId(userId);
				obj.setCongtyId((String) session.getAttribute("congtyId"));
				obj.setnppdangnhap(util.getIdNhapp(userId));
				GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
				
				/*if (msg.length() > 0)
					obj.setMsg(msg);
				else*/
				if(db.updateReturnInt("update ERP_PARK set trangthai = '1', nguoisua =" + userId + "  where pk_seq = '" + nhId +"' and trangthai=0")!=1){
					obj.setMsg("Lỗi không chốt, vui lòng kiểm tra lại");
				}
				obj.init("");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuoc.jsp";
				response.sendRedirect(nextJSP);

				db.shutDown();
				
			} else if (action.equals("unchot")) {

				String msg = obj.Unchot(nhId, userId);
				
				
				obj.setUserId(userId);
				obj.setCongtyId((String) session.getAttribute("congtyId"));
				obj.setnppdangnhap(util.getIdNhapp(userId));
				GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
				
				obj.init("");
				if (msg.length() > 0)
					obj.setMsg(msg);
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuoc.jsp";
				response.sendRedirect(nextJSP);
			} else if (action.equals("convert")) {
				IErpPark pBean = new ErpPark();

				pBean.setCtyId((String) session.getAttribute("congtyId"));
				pBean.setUserId(userId);
				pBean.setnppdangnhap(util.getIdNhapp(userId));

				String nccId = util.antiSQLInspection(request.getParameter("nccId"));
				pBean.setNccId(nccId);

				pBean.setLoaidonmh("1"); // MẶC ĐỊNH LÀ LOẠI HD TRONG NƯỚC

				pBean.setNgayghinhan("");

				pBean.setTigia("1");

				String poId = util.antiSQLInspection(request.getParameter("poId"));
				pBean.setNccId(nccId);
				pBean.setPoNkIds(poId);
				pBean.setTtId("100000"); // Mặc định VND

				pBean.setTongsoluong("0");

				List<IErpHoadon> hdList = new ArrayList<IErpHoadon>();

				pBean.setHdList(hdList);

				pBean.createRs();
				session.setAttribute("pBean", pBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuocNew.jsp";

				response.sendRedirect(nextJSP);

				return;
			}

			else {

		obj.setUserId(userId);
		obj.setCongtyId((String) session.getAttribute("congtyId"));
		obj.setnppdangnhap(util.getIdNhapp(userId));

		obj.init("");
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuoc.jsp";
		response.sendRedirect(nextJSP);
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IErpParkList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}

		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		if (action.equals("Tao moi")) {
			IErpPark pBean = new ErpPark();
			pBean.setCtyId((String) session.getAttribute("congtyId"));
			pBean.setUserId(userId);
			pBean.setnppdangnhap(util.getIdNhapp(userId));

			pBean.createRs();
			session.setAttribute("pBean", pBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuocNew.jsp";
			response.sendRedirect(nextJSP);
		} else {
			if (action.equals("view") || action.equals("next") || action.equals("prev")) {
				obj = new ErpParkList();
				obj.setUserId(userId);
				obj.setnppdangnhap(util.getIdNhapp(userId));
				
				obj.setCongtyId((String) session.getAttribute("congtyId"));
				this.getSearchQuery(request, obj);

				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				

				obj.init("");
				GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuoc.jsp");
			} else {
				obj = new ErpParkList();

				obj.setCongtyId((String) session.getAttribute("congtyId"));
				obj.setUserId(userId);
				obj.setnppdangnhap(util.getIdNhapp(userId));

				this.getSearchQuery(request, obj);
		
				obj.init("");
				GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);

				response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpParkHoaDonTrongNuoc.jsp");
			}
		}
	}

	private void getSearchQuery(HttpServletRequest request, IErpParkList obj) {

		/*
		 * String query =
		 * "select a.PK_SEQ , a.NGAYGHINHAN, ncc.TEN as TENNHACUNGCAP, " +
		 * "case a.loaihanghoa_fk when 0 then N'Sản phẩm nhập kho' when 1 then N'Tài sản cố định' "
		 * + "else N'Chi phí dịch vụ' end as LOAIHANG, a.TRANGTHAI , " +
		 * " NV.TEN as TENNV,a.NGAYSUA,a.NGAYTAO,NV.PK_SEQ as MANV,NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS, a.PREFIX "
		 * +
		 * " from ERP_PARK a inner join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = a.NCC_FK "
		 * +
		 * " inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 "
		 * ;
		 */

//		String query =
//
//				" SELECT distinct A.PK_SEQ , A.NGAYGHINHAN, NCC.TEN AS TENNHACUNGCAP, A.TRANGTHAI ,     	NV.TEN AS TENNV,A.NGAYSUA,A.NGAYTAO,NV.PK_SEQ AS MANV,NV2.TEN AS \n"
//						+ "  		 TENNVS,NV2.PK_SEQ AS MANVS, A.PREFIX     	, HOANTAT.SODEM  AS DAHOANTAT , CASE WHEN THANHTOAN.PARK_FK IS NULL THEN 0 ELSE 1 END AS DACOTHANHTOAN, ISNULL(HDNCC.SOHOADON,'') AS SOHOADON, NCC.PK_SEQ NCCID,	 \n"
//						+ "        HDNCC. PK_SEQ HDNCCID, \n"
//						+ "		 (SELECT COUNT(PK_SEQ) FROM ERP_NHANHANG WHERE HDNCC_FK = HDNCC.PK_SEQ  AND TRANGTHAI NOT IN (3) ) DANHANHANG, A.LOAIHD, \n"
//						+ " ( SELECT CASE WHEN HDMH1.SANPHAM_FK IS NOT NULL THEN 0 WHEN HDMH1.TAISAN_FK IS NOT NULL THEN 1 WHEN HDMH1.CCDC_FK IS NOT NULL THEN 3 ELSE 2 END LOAIHH \n"
//						+ " FROM \n" + " (SELECT TOP (1) * " + "  FROM ERP_HOADONNCC_DONMUAHANG HDMH "
//						+ "  WHERE HDMH.HOADONNCC_FK = HDNCC.PK_SEQ ) HDMH1 ) LOAIHH \n" +
//
//						" FROM 	 ERP_PARK A   	\n" + " INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = A.NCC_FK   	\n"
//						+ " INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   \n"
//						+ " INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ	     \n" + " LEFT JOIN 	(   \n"
//						+ "   				 SELECT A.PARK_FK , (DEMA - ISNULL (DEMB,0)) AS SODEM \n"
//						+ "					 FROM 	(SELECT PARK_FK , COUNT(PARK_FK) AS DEMA  	\n"
//						+ "							 FROM ERP_HOADONNCC   \n"
//						+ "							 WHERE CONGTY_FK = " + obj.getCongtyId()
//						+ "   				 		 GROUP BY PARK_FK) AS A  	\n"
//						+ "							 LEFT JOIN   	\n"
//						+ "							(SELECT PARK_FK , COUNT(PARK_FK) AS DEMB  	\n"
//						+ "							 FROM ERP_HOADONNCC  	 \n"
//						+ "   				 		 WHERE TRANGTHAI = 2 AND CONGTY_FK = " + obj.getCongtyId()
//						+ "							 GROUP BY PARK_FK) AS B ON B.PARK_FK = A.PARK_FK  \n"
//						+ "  					) HOANTAT ON HOANTAT.PARK_FK = A.PK_SEQ   	 \n"
//						+ "  		 LEFT JOIN 	(  \n" + "   				SELECT 	DISTINCT HD.PARK_FK 		\n"
//						+ "   				FROM 	ERP_THANHTOANHOADON_HOADON TT		\n"
//						+ "   					 	INNER JOIN ERP_HOADONNCC HD ON HD.PK_SEQ = TT.HOADON_FK AND TT.LOAIHD = 0 \n"
//						+ "   				WHERE 	TT.THANHTOANHD_FK IN (SELECT PK_SEQ FROM ERP_THANHTOANHOADON WHERE TRANGTHAI != 2 AND CONGTY_FK = "
//						+ obj.getCongtyId() + "  )  \n" + "							AND HD.CONGTY_FK = "
//						+ obj.getCongtyId() + "  					) THANHTOAN ON THANHTOAN.PARK_FK = A.PK_SEQ "
//						+ " INNER JOIN ERP_HOADONNCC HDNCC ON A.PK_SEQ=HDNCC.PARK_FK  "
//						+ " INNER JOIN ERP_HOADONNCC_DONMUAHANG HDNCC_DMH ON HDNCC.PK_SEQ=HDNCC_DMH.HOADONNCC_FK " 
//						+ "WHERE 1=1 and A.LOAIHD = 1  ";

		String ngayghinhan = request.getParameter("ngayghinhan");
		if (ngayghinhan == null)
			ngayghinhan = "";
		obj.setNgayghinhan(ngayghinhan);
		
		String thanhpham = request.getParameter("thanhpham");
		if (thanhpham == null)
			thanhpham = "";
		obj.setThanhphamId(thanhpham);
		
		String soloId = request.getParameter("solo");
		if (soloId == null)
			soloId = "";
		obj.setSoloId(soloId);
		
		String tungay = request.getParameter("tungay");
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String khoId = request.getParameter("kho");
		if (khoId == null)
			khoId = "";
		obj.setKhoId(khoId);

		String nccId = request.getParameter("nccId");
		if (nccId == null)
			nccId = "";
		obj.setNccId(nccId);

		String ncc = request.getParameter("ncc");
		if (ncc == null)
			ncc = "";
		obj.setNcc(ncc);

		String loaihang = request.getParameter("loaihang");
		if (loaihang == null)
			loaihang = "";
		obj.setLoaihang(loaihang);

		String sopark = request.getParameter("sopark");
		if (sopark == null)
			sopark = "";
		obj.setId(sopark);

		String trangthai = request.getParameter("trangthai");
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String sohoadon = request.getParameter("sohoadon");
		if (sohoadon == null)
			sohoadon = "";
		obj.setSOHOADON(sohoadon);

		String sonhanhang = request.getParameter("sonhanhang");
		if (sonhanhang == null)
			sonhanhang = "";
		obj.setSonhanhang(sonhanhang);
		System.out.println("Số nhận hàng: " + sonhanhang);

		String nguoitao = request.getParameter("nguoitao");
		if (nguoitao == null)
			nguoitao = "";
		obj.setNGUOITAO(nguoitao);
		
		String donMuaHang = request.getParameter("donmuahang");
		if(donMuaHang == null){
			donMuaHang = "";
		}
		obj.setDonMuaHang(donMuaHang.trim());
		
		String ngaytao = request.getParameter("ngaytao");
		if(ngaytao == null){
			ngaytao = "";
		}
		obj.setNgaytao(ngaytao);
		
//
//		if (trangthai.length() > 0) {
//			if (trangthai.equals("0") || trangthai.equals("1")) {
//				query += " and a.trangthai = '" + trangthai + "'";
//			} else if (trangthai.equals("2")) {
//				query += " and a.trangthai = '" + trangthai + "' and hoantat.sodem != 0  and thanhtoan.park_fk is null";
//			} else if (trangthai.equals("3")) {
//				query += " and a.trangthai = 2 and hoantat.sodem != 0  and thanhtoan.park_fk is not null";
//			} else if (trangthai.equals("4")) {
//				query += " and ( (a.trangthai = 2 and hoantat.sodem = 0 ) or a.trangthai = 3 )";
//			} else if (trangthai.equals("5")) {
//				query += " and a.trangthai = 4 ";
//			}
//		}
//
//		if (ngayghinhan.length() > 0)
//			query += " and a.ngayghinhan = '" + ngayghinhan + "'";
//
//		if (nccId.length() > 0)
//			query += " and a.ncc_fk = '" + nccId + "'";
//
//		if (loaihang.length() > 0)
//			query += " and a.loaihanghoa_fk = '" + loaihang + "'";
//		if (sopark.length() > 0)
//			query += " and cast(a.PREFIX as varchar(10))+ cast(a.pk_seq as varchar(10))  like '%" + sopark + "%'";
//
//		if (sohoadon.length() > 0)
//			query += " and (a.pk_seq in (select park_fk from ERP_HOADONNCC  where sohoadon like N'%" + sohoadon
//					+ "%'))";
//
//		if (nguoitao.length() > 0)
//			query += " and NV.TEN like N'%" + nguoitao + "%'";
//
//		if (sonhanhang.length() > 0) {
//			query += "  and  HDNCC.pk_seq in ( select a.pk_seq from ERP_HOADONNCC a inner join ERP_HOADONNCC_DONMUAHANG  b on b.hoadonncc_fk= a.pk_seq"
//					+ "	inner join ERP_NHAPKHONHAPKHAU c on b.MUAHANG_FK = c.PK_SEQ " + "  where c.PK_SEQ = N'"
//					+ sonhanhang + "' and a.CONGTY_FK = " + obj.getCongtyId() + " ) ";
//		}
//		
//		if(donMuaHang.length() >0){
//			query += " and HDNCC_DMH.muahang_fk in( select PK_SEQ from ERP_MUAHANG where sopo like '%"+donMuaHang+"%') ";
//		}
//		
//		System.out.println("\n cau tim kiem: "+ query +"\n");
//		return query;
	}

	private String Delete(String nhId) {
		dbutils db = new dbutils();

		try {
			db.getConnection().setAutoCommit(false);

			// xoá thì cập nhật lại trạng thái mua hàng = 1 để get PO này ra.
			String sql = " update ERP_MUAHANG set TRANGTHAI = 1 where PK_SEQ in (select distinct a.MUAHANG_FK from ERP_HOADONNCC_DONMUAHANG  a "
					+ " inner join ERP_HOADONNCC b on a.HOADONNCC_FK = b.pk_seq "
					+ " inner join ERP_PARK c on c.pk_seq = b.park_fk where c.pk_seq = '" + nhId + "')";
			if (!db.update(sql)) {
				db.getConnection().rollback();
				return "Lỗi Cập nhật trạng thái đơn mua hàng";
			}

			if (!db.update(
					"delete from erp_hoadonncc_phieunhap where hoadonncc_fk in (select pk_seq from erp_hoadonncc where park_fk = '"
							+ nhId + "')")) {
				db.getConnection().rollback();
				return "Lỗi "
						+ "delete from erp_hoadonncc_phieunhap where hoadonncc_fk in (select pk_seq from erp_hoadonncc where park_fk = '"
						+ nhId + "')";
			}

			if (!db.update(
					"delete from erp_hoadonncc_phieunhap where hoadonncc_fk in (select pk_seq from erp_hoadonncc where park_fk = '"
							+ nhId + "')")) {
				db.getConnection().rollback();
				return "Lỗi "
						+ "delete from erp_hoadonncc_phieunhap where hoadonncc_fk in (select pk_seq from erp_hoadonncc where park_fk = '"
						+ nhId + "')";
			}

			if (!db.update(
					"delete from erp_hoadonncc_donmuahang where hoadonncc_fk in (select pk_seq from erp_hoadonncc where park_fk = '"
							+ nhId + "')")) {
				db.getConnection().rollback();
				return "Lỗi "
						+ "delete from erp_hoadonncc_donmuahang where hoadonncc_fk in (select pk_seq from erp_hoadonncc where park_fk = '"
						+ nhId + "')";
			}

			if (!db.update(
					"delete from ERP_hoadonncc where pk_seq in (select pk_seq from erp_hoadonncc where park_fk = '"
							+ nhId + "')")) {
				db.getConnection().rollback();
				return "Lỗi "
						+ "delete from ERP_hoadonncc where pk_seq in (select pk_seq from erp_hoadonncc where park_fk = '"
						+ nhId + "')";
			}

			if (db.updateReturnInt("delete from ERP_PARK  where pk_seq = '" + nhId + "' and trangthai=0")!=1) {
				db.getConnection().rollback();
				return "Lỗi " + "delete from ERP_PARK  where pk_seq = '" + nhId + "'";
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} catch (Exception e) {
			db.update("rollback");
			return "Không thể xóa! Lỗi: " + e.getMessage();
		}

	}

}
