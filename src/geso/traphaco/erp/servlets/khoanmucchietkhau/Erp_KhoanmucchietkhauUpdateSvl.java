package geso.traphaco.erp.servlets.khoanmucchietkhau;

import geso.traphaco.center.util.*;
/*import geso.traphaco.erp.beans.banggiaban.IBanggia_sp;*/
import geso.traphaco.erp.beans.kho.imp.Erp_KhoTT;
import geso.traphaco.erp.beans.kho.imp.Erp_KhoTTList;
import geso.traphaco.erp.beans.khoanmucchietkhau.*;
import geso.traphaco.erp.beans.khoanmucchietkhau.imp.*;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_KhoanmucchietkhauUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Erp_KhoanmucchietkhauUpdateSvl() {
		super();
	}

	String URL;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Utility util = new Utility();
		HttpSession session = request.getSession();

		String action = request.getParameter("action");
		String mucCK = util.antiSQLInspection(request.getParameter("mucCK"));
		String ctyId = (String) session.getAttribute("congtyId");

		if (action.equals("update")) {
			IKhoanmucchietkhau kmck;

			if (mucCK != null)
				kmck = new Khoanmucchietkhau(mucCK);
			else {
				kmck = new Khoanmucchietkhau();
			}

			kmck.setCongty_fk(ctyId);
			kmck.setPK_SEQ(mucCK);
			kmck.init();

			session.setAttribute("kmck", kmck);
			URL = "../TraphacoHYERP/pages/Erp/Erp_KhoanmucchietkhauUpdate.jsp";
		}
		if (action.equals("display")) {
			IKhoanmucchietkhau kmck;

			if (mucCK != null)
				kmck = new Khoanmucchietkhau(mucCK);
			else {
				kmck = new Khoanmucchietkhau();
			}

			kmck.setCongty_fk(ctyId);
			kmck.setPK_SEQ(mucCK);
			kmck.init();

			session.setAttribute("kmck", kmck);
			URL = "../TraphacoHYERP/pages/Erp/Erp_KhoanmucchietkhauDisplay.jsp";
		}
		response.sendRedirect(URL);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		Utility util = new Utility();
		String id = util.antiSQLInspection(request.getParameter("PK_SEQ"));
		String user = request.getParameter("userId");
		String ctyId = (String) session.getAttribute("congtyId");
		Khoanmucchietkhau kmck;
		if (id == null) {
			kmck = new Khoanmucchietkhau();
			id = "";
		} else
			kmck = new Khoanmucchietkhau(id);

		String MA = util.antiSQLInspection(request.getParameter("maKMCK"));
		if (MA == null)
			MA = "";
		kmck.setMa(MA);

		String TEN = util.antiSQLInspection(request.getParameter("tenKMCK"));
		if (TEN == null)
			TEN = "";
		kmck.setTen(TEN);

		kmck.setNguoitao(user);
		kmck.setNguoisua(user);

		String[] chiTietId = request.getParameterValues("chiTietId");
		String[] kenhBanHang = request.getParameterValues("kenhBanHang");
		String[] taiKhoanId = request.getParameterValues("taiKhoan");

		List<IKhoanmucCK> chiTietList = new ArrayList<IKhoanmucCK>();
		if (kenhBanHang != null) {
			for (int i = 0; i < kenhBanHang.length; i++) {
				IKhoanmucCK km = new KhoanmucCK();
				km.setKenhbhId(kenhBanHang[i]);
				km.setTaikhoanId(taiKhoanId[i]);
				chiTietList.add(km);

			}
		}

		kmck.setChiTietList(chiTietList);

		if (action.equals("save")) {
			if (id.length() > 0) {
				if (kmck.Update()) {
					IKhoanmucchietkhauList kmckL = new KhoanmucchietkhauList();
					kmckL.setCongty_fk(ctyId);
					kmckL.init();
					session.setAttribute("kmckL", kmckL);
					URL = "../TraphacoHYERP/pages/Erp/Erp_Khoanmucchietkhau.jsp";
				} else {
					session.setAttribute("kmck", kmck);
					URL = "../TraphacoHYERP/pages/Erp/Erp_KhoanmucchietkhauUpdate.jsp";
				}
			} else {
				if (kmck.New()) {
					IKhoanmucchietkhauList kmckL = new KhoanmucchietkhauList();
					kmckL.setCongty_fk(ctyId);
					kmckL.init();
					session.setAttribute("kmckL", kmckL);
					URL = "../TraphacoHYERP/pages/Erp/Erp_Khoanmucchietkhau.jsp";
				} else {
					session.setAttribute("kmck", kmck);
					URL = "../TraphacoHYERP/pages/Erp/Erp_KhoanmucchietkhauNew.jsp";
				}
			}

		} else {
			kmck.CreateRs();
			session.setAttribute("userId", user);
			session.setAttribute("kmck", kmck);

			URL = "/TraphacoHYERP/pages/Erp/Erp_KhoanmucchietkhauUpdate.jsp";

			if (id != null) {
				URL = "/TraphacoHYERP/pages/Erp/Erp_KhoanmucchietkhauUpdate.jsp";
			}
		}
		response.sendRedirect(URL);
	}
}
