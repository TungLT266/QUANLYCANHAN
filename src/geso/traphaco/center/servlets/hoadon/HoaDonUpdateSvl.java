package geso.traphaco.center.servlets.hoadon;

import geso.traphaco.center.beans.hoadon.IHoaDon;
import geso.traphaco.center.beans.hoadon.imp.HoaDon;
import geso.traphaco.distributor.beans.nhaphang.INhaphang;
import geso.traphaco.distributor.beans.nhaphang.INhaphangList;
import geso.traphaco.distributor.beans.nhaphang.imp.Nhaphang;
import geso.traphaco.distributor.beans.nhaphang.imp.NhaphangList;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/HoaDonUpdateSvl")
public class HoaDonUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public HoaDonUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{

			PrintWriter out = response.getWriter();
			String nextJSP;
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			out.println(id);

			IHoaDon nhaphang = (IHoaDon) new HoaDon();
			nhaphang.setUserId(userId);
			nhaphang.setId(id);
			nhaphang.init();
			nextJSP = "/TraphacoHYERP/pages/Center/HoaDonDisplay.jsp";
			session.setAttribute("hoadon", nhaphang);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{

			PrintWriter out = response.getWriter();
			String nextJSP;
			Utility util = new Utility();

			INhaphang nhaphang = (INhaphang) new Nhaphang();

			userId = util.antiSQLInspection(request.getParameter("userId"));
			nhaphang.setUserId(userId);

			String id = util.antiSQLInspection(request.getParameter("id"));
			nhaphang.setId(id);

			String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
			if (sohoadon == null)
				sohoadon = "";
			nhaphang.setSohoadon(sohoadon);

			String ngayct = util.antiSQLInspection(request.getParameter("tungay"));
			if (ngayct == null)
				ngayct = "";
			nhaphang.setNgaychungtu(ngayct);

			String khoId = util.antiSQLInspection(request.getParameter("khoId"));
			nhaphang.setKhoId(khoId);

			String gsbhid = util.antiSQLInspection(request.getParameter("gsbhId"));
			nhaphang.setGsbhId(gsbhid);

			String action = request.getParameter("action");
			boolean error = false;

			if (sohoadon.length() == 0)
			{
				// nhaphang.setMessage("ChÃƒÆ’Ã‚Âº ÃƒÆ’Ã¯Â¿Â½ : NhÃƒÂ¡Ã‚ÂºÃ‚Â­p SÃƒÂ¡Ã‚Â»Ã¢â‚¬Ëœ HÃƒÆ’Ã‚Â³a Ãƒâ€žÃ¯Â¿Â½Ãƒâ€ Ã‚Â¡n (SÃƒÂ¡Ã‚Â»Ã¢â‚¬Ëœ Building) gÃƒÂ¡Ã‚Â»Ã¢â‚¬Å“m 10 sÃƒÂ¡Ã‚Â»Ã¢â‚¬Ëœ bÃƒÂ¡Ã‚ÂºÃ‚Â¯t Ãƒâ€žÃ¢â‚¬ËœÃƒÂ¡Ã‚ÂºÃ‚Â§u  bÃƒÂ¡Ã‚ÂºÃ‚Â±ng sÃƒÂ¡Ã‚Â»Ã¢â‚¬Ëœ 4");
				nhaphang.setMessage("Chu Y: Nhap So Hoa Don (So  Building) Bao Gom 10 So Bat Dau Bang So 4");
				error = true;
			}

			System.out.print("Kho id:" + khoId);
			if (khoId == null)
			{
				nhaphang.setMessage("Don Nhap Hang Nay  Chua Co Kho De Nhap Hang");
				error = true;
			}

			out.println(id);
			if (!error)
			{
				if (action.equals("nhaphang"))
				{

					if (!(nhaphang.UpdateNhaphangNpp()))
					{
						nhaphang.init();
						nextJSP = "/TraphacoHYERP/pages/Distributor/NhapHangUpdate.jsp";
						session.setAttribute("nhaphang", nhaphang);
						response.sendRedirect(nextJSP);
					} else
					{
						INhaphangList obj = new NhaphangList();
						obj.setUserId(userId);
						obj.createNhaphanglist("");
						session.setAttribute("obj", obj);
						nextJSP = "/TraphacoHYERP/pages/Distributor/NhapHang.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					INhaphangList obj = new NhaphangList();
					obj.setUserId(userId);
					obj.createNhaphanglist("");
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Distributor/NhapHang.jsp";
					response.sendRedirect(nextJSP);
				}
			} else
			{
				nhaphang.init();

				nextJSP = "/TraphacoHYERP/pages/Distributor/NhapHangUpdate.jsp";

				session.setAttribute("nhaphang", nhaphang);

				response.sendRedirect(nextJSP);

			}
		}
	}

}