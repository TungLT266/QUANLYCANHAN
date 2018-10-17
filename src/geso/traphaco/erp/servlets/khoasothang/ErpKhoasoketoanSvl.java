package geso.traphaco.erp.servlets.khoasothang;

import geso.traphaco.erp.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.khoasothang.IErpKhoasoketoan;
import geso.traphaco.erp.beans.khoasothang.IErpkhoasoketoanlist;
import geso.traphaco.erp.beans.khoasothang.IKiemTraDLN;
import geso.traphaco.erp.beans.khoasothang.imp.ErpKhoasoketoan;
import geso.traphaco.erp.beans.khoasothang.imp.ErpKhoasoketoanlist;
import geso.traphaco.erp.beans.khoasothang.imp.KiemTraDLN;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpKhoasoketoanSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpKhoasoketoanSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IErpKhoasoketoan obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		obj = new ErpKhoasoketoan();
		obj.setUserId(userId);
		obj.setCtyId((String) session.getAttribute("congtyId"));

		String task = request.getParameter("task");
		if (task == null)
			task = "";

		String nextJSP = "";
		String id = util.getId(querystring);
		obj.setId(id);
		String action = util.getAction(querystring);

		if (action.equals("mokhoaso")) {
			 

		} else if (action.equals("display")) {
			obj.init();
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhoaSoKeToanDisplay.jsp";
		}else if(action.equals("update")){
			obj.init();
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhoaSoKeToan.jsp";
		}

		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
	}
 
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IErpKhoasoketoan obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		obj = new ErpKhoasoketoan();
		obj.setCtyId((String) session.getAttribute("congtyId"));

		obj.setUserId(userId);

		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = "";
		obj.setThang(thang);

		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		obj.setNam(nam);

		String id_ = util.antiSQLInspection(request.getParameter("id_"));
		if (id_ == null)
			id_ = "";
		obj.setId(id_);

		String xacnhan_dulieunen = util.antiSQLInspection(request.getParameter("xacnhan_dulieunen"));
		if (xacnhan_dulieunen == null)
			xacnhan_dulieunen = "0";
		obj.setCheckDuLieuNen(xacnhan_dulieunen);

		String xacnhan_nhanhang_dongia0 = util.antiSQLInspection(request.getParameter("xacnhan_nhanhang_dongia0"));
		if (xacnhan_nhanhang_dongia0 == null)
			xacnhan_nhanhang_dongia0 = "0";
		obj.setCheckNhanHangGiaTriKhong(xacnhan_nhanhang_dongia0);

		String xacnhan_soluong_khac_trendonhang_hoadon = util
				.antiSQLInspection(request.getParameter("xacnhan_soluong_khac_trendonhang_hoadon"));
		if (xacnhan_soluong_khac_trendonhang_hoadon == null)
			xacnhan_soluong_khac_trendonhang_hoadon = "0";
		obj.setCheckDonhang_Hoadon(xacnhan_soluong_khac_trendonhang_hoadon);

		String[] loai = request.getParameterValues("loai");
		String[] id = request.getParameterValues("id");
		String[] ma = request.getParameterValues("ma");
		String[] ten = request.getParameterValues("ten");
		String[] trangthai = request.getParameterValues("trangthai");
		// String[] trangthainew = request.getParameterValues("trangthainew");
		List<IKiemTraDLN> lst = new ArrayList<IKiemTraDLN>();
		if (id != null) {
			for (int i = 0; i < id.length; i++) {
				IKiemTraDLN kt = new KiemTraDLN(thang, nam, loai[i], trangthai[i]);
				kt.setId(id[i]);
				kt.setMa(ma[i]);
				kt.setTen(ten[i]);
				if (request.getParameter("trangthainew" + i) == null) {
					kt.setTrangthai("0");
				} else {
					kt.setTrangthai("1");
				}
				lst.add(kt);
			}
		}
		obj.setKiemtraDLNList(lst);

		String action = request.getParameter("action");
		if (action == null)
			action = "";

		String msg = "";
		String nextJSP = "";
		String[] check_xacnhan = request.getParameterValues("check_xacnhan");
		String str_checkxacnhan = "";
		if (check_xacnhan != null) {
			for (int i = 0; i < check_xacnhan.length; i++) {
				str_checkxacnhan += "," + check_xacnhan[i];
			}
		}

		// kiểm tra báo cao value 20
		String xacnhan_kiemtrabaocao = util.antiSQLInspection(request.getParameter("xacnhan_kiemtrabaocao"));
		if (xacnhan_kiemtrabaocao == null)
			xacnhan_kiemtrabaocao = "";
		if (xacnhan_kiemtrabaocao.length() > 0) {

			str_checkxacnhan += xacnhan_kiemtrabaocao;
		}
		obj.setCheckXacnhan(str_checkxacnhan);

		System.out.println("action : " + action);

		if (action.equals("dln")) {
			obj.setMsg("");
			msg = obj.CapNhatDLN();

			obj.Init_kiemtradulieu();
			obj.setMsg(msg);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhoaSoKeToan.jsp";
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);

		} else if (action.equals("ketchuyendulieu")) {
			obj.KetChuyenDuLieu();
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhoaSoKeToan.jsp";
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		} else if (action.equals("khoasoketoan")) {
			if (!obj.khoasoketoan()) {

				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhoaSoKeToan.jsp";
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);

			} else {
				IErpkhoasoketoanlist obj1 = new ErpKhoasoketoanlist();
				obj1.setUserId(userId);

				String task = request.getParameter("task");
				if (task == null)
					task = "";

				obj1.Init();

				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhoasoketoanlist.jsp";
				session.setAttribute("obj", obj1);
				response.sendRedirect(nextJSP);
			}
		} else {
			obj.Init_kiemtradulieu();
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhoaSoKeToan.jsp";
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}

	}

}
