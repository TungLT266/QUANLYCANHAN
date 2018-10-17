package geso.traphaco.distributor.servlets.dieuchinhtonkho;

import geso.traphaco.distributor.beans.dieuchinhtonkho.IDieuchinhtonkho;
import geso.traphaco.distributor.beans.dieuchinhtonkho.imp.Dieuchinhtonkho;
import geso.traphaco.distributor.beans.dieuchinhtonkho.IDieuchinhtonkhoList;
import geso.traphaco.distributor.beans.dieuchinhtonkho.imp.DieuchinhtonkhoList;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DieuchinhtonkhoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public DieuchinhtonkhoUpdateSvl()
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
			// PrintWriter out = response.getWriter();
			String nextJSP;
			Utility util = new Utility();

			String querystring = request.getQueryString();
			// String userId = util.getUserId(querystring);

			// if (userId.length()==0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			String action = util.getAction(querystring);

			IDieuchinhtonkho dctkBean = (IDieuchinhtonkho) new Dieuchinhtonkho();
			dctkBean.setUserId(userId);
			dctkBean.setId(id);
			dctkBean.setNppId(util.antiSQLInspection(request.getParameter("nppId")));
			dctkBean.initUpdate();
			session.setAttribute("dctkBean", dctkBean);
			nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhTonKhoUpdate.jsp";
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
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			PrintWriter out = response.getWriter();
			Utility util = new Utility();

			IDieuchinhtonkho dctkBean;
			dctkBean = (IDieuchinhtonkho) new Dieuchinhtonkho();
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id != null)
			{
				dctkBean.setId(id);
			}
			userId = util.antiSQLInspection(request.getParameter("userId"));
			if (userId == null)
			{
				response.sendRedirect("/TraphacoHYERP/index.jsp");
			} else
			{
				dctkBean.setUserId(userId);
			}

			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
			{
				nppId = "";
			}

			if (nppId.length() == 0)
			{
				response.sendRedirect("/TraphacoHYERP/index.jsp");
			} else
			{
				dctkBean.setNppId(nppId);
			}
			boolean error = false;
			String khoId = util.antiSQLInspection(request.getParameter("khoId"));
			if (khoId == null)
			{
				khoId = "";
			}

			if (khoId.length() == 0)
			{
				dctkBean.setMessage("Vui long chon kho dieu chinh ton");
				error = true;
			} else
			{
				dctkBean.setKhoId(khoId);
			}

			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			if (kbhId == null)
			{
				kbhId = "";
			}

			if (kbhId.length() == 0)
			{
				dctkBean.setMessage("Vui long chon kenh ban hang");
				error = true;
			} else
			{
				dctkBean.setKbhId(kbhId);
			}

			String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
			if (dvkdId == null)
				dvkdId = "";

			if (dvkdId.length() == 0)
			{
				dctkBean.setMessage("Vui lòng chọn đơn vị kinh doanh kinh doanh");
				error = true;
			} else
			{
				dctkBean.setDvkdId(dvkdId);
			}

			String ngaydc = util.antiSQLInspection(request.getParameter("ngaydc"));
			if (ngaydc == null)
			{
				ngaydc = "";
			}

			if (ngaydc.length() == 0)
			{
				dctkBean.setMessage("Vui long nhap ngay dieu chinh ton kho");
				error = true;
			}
			dctkBean.setNgaydc(ngaydc);

			String lydodc = util.antiSQLInspection(request.getParameter("lydo"));
			if (lydodc == null)
			{
				lydodc = "";
			}
			dctkBean.setLydodc(lydodc);
			String[] spId = request.getParameterValues("spId");
			String[] masp = request.getParameterValues("masp");
			String[] tensp = request.getParameterValues("tensp");
			String[] quycach = request.getParameterValues("quycach");
			String[] tonkho = request.getParameterValues("tonkho");
			String[] tonthung = request.getParameterValues("tonthung");
			String[] tonle = request.getParameterValues("tonle");
			String[] solo = request.getParameterValues("solo");
			String[] soluongle = request.getParameterValues("soluongle");
			String[] soluongthung = request.getParameterValues("soluongthung");
			String[] dieuchinh=request.getParameterValues("dieuchinh");
			String[] dongia = request.getParameterValues("giaton");
			String[] dongiathung = request.getParameterValues("dongiathung");
			String[] thanhtien = request.getParameterValues("thanhtien");
			String[] donvi = request.getParameterValues("donvi");
			
			String[] spNgayHetHan = request.getParameterValues("spNgayHetHan");
			dctkBean.setSpNgayHetHan(spNgayHetHan);

			String action = request.getParameter("action");
			if (!error)
			{
				if (masp != null)
				{
					int size = masp.length;
					dctkBean.setSize(size);
					for (int i = 0; i < size; i++)
					{
						soluongle[i] = soluongle[i].replace(",", "");
						soluongthung[i] = soluongthung[i].replace(",", "");
						dongiathung[i] = dongiathung[i].replace(",", "");
						dongia[i] = dongia[i].replace(",", "");
						tonkho[i] = tonkho[i].replace(",", "");
						tonthung[i] = tonthung[i].replace(",", "");
						tonle[i] = tonle[i].replace(",", "");
						thanhtien[i] = thanhtien[i].replace(",", "");
						solo[i] = solo[i].trim();
					}
					dctkBean.setSpId(spId);
					dctkBean.setMasp(masp);
					dctkBean.setTenSp(tensp);
					dctkBean.setSoluongle(soluongle);
					dctkBean.setSoluongthung(soluongthung);
					dctkBean.setTonkho(tonkho);
					dctkBean.setTonthung(tonthung);
					dctkBean.setTonle(tonle);
					dctkBean.setSolo(solo);
					dctkBean.setDonvi(donvi);
					dctkBean.setGiamua(dongia);
					dctkBean.setDongiathung(dongiathung);
					dctkBean.setQuycach(quycach);
					dctkBean.setTtien(thanhtien);
					dctkBean.setDc(dieuchinh);
				}
				if (action.equals("save"))
				{
					if (id == null)
					{
						try {
							if (!(dctkBean.CreateDctk(request)))
							{
								dctkBean.init0();
								session.setAttribute("dctkBean", dctkBean);
								String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhTonKhoNew.jsp";
								response.sendRedirect(nextJSP);
							} else
							{
								IDieuchinhtonkhoList obj = new DieuchinhtonkhoList();
								obj.setUserId(userId);
								obj.init0();
								obj.createDctklist("");
								session.setAttribute("obj", obj);

								String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhTonKho.jsp";
								response.sendRedirect(nextJSP);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else
					{
						try {
							if (!(dctkBean.UpdateDctk(request)))
							{
								dctkBean.initUpdate();
								session.setAttribute("dctkBean", dctkBean);
								String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhTonKhoUpdate.jsp";
								response.sendRedirect(nextJSP);
							} else
							{
								IDieuchinhtonkhoList obj = new DieuchinhtonkhoList();
								obj.setUserId(userId);
								obj.init0();
								obj.createDctklist("");
								session.setAttribute("obj", obj);
								String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhTonKho.jsp";
								response.sendRedirect(nextJSP);

							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else
				{
					String nextJSP;
					if (id == null)
					{
						dctkBean.initNew();
						nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhTonKhoNew.jsp";
					} else
					{
						dctkBean.initUpdate();
						nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhTonKhoUpdate.jsp";
					}
					session.setAttribute("dctkBean", dctkBean);
					response.sendRedirect(nextJSP);
				}
			} else
			{
				String nextJSP;
				if (id == null)
				{
					dctkBean.initNew();
					nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhTonKhoNew.jsp";
				} else
				{
					dctkBean.initUpdate();
					nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhTonKhoUpdate.jsp";
				}
				session.setAttribute("dctkBean", dctkBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
}
