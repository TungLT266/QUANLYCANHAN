package geso.traphaco.erp.servlets.nhapkho;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhapkho.IErpNhapkho;
import geso.traphaco.erp.beans.nhapkho.IErpNhapkhoList;
import geso.traphaco.erp.beans.nhapkho.imp.ErpNhapkho;
import geso.traphaco.erp.beans.nhapkho.imp.ErpNhapkhoList;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhapkhoSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpNhapkhoSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpNhapkhoList obj;
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

		String nkId = util.getId(querystring);

		obj = new ErpNhapkhoList();
		obj.setCongtyId((String)session.getAttribute("congtyId"));
		
		if (action.equals("delete"))
		{
			String msg = Delete(nkId);
			if (msg.length() > 0)
				obj.setmsg(msg);
			else
			{
				String sonhaphang = request.getParameter("sonhaphang");
				String noidungnhap = request.getParameter("noidungnhap");

				IErpNhapkho nhapkho = new ErpNhapkho(nkId);

				if (noidungnhap.equals("100004"))
					nhapkho.updateDonnhanhang("", sonhaphang, "");
				else
				{
					if (noidungnhap.equals("100005"))
						nhapkho.updateDonnhanhang("", "", sonhaphang);
					else
						nhapkho.updateDonnhanhang(sonhaphang, "", "");
				}
			}
		} else
		{
			if (action.equals("chot"))
			{
				String msg = Chot(nkId, userId);
				if (msg.length() > 0)
					obj.setmsg(msg);
			}
		}

		obj.setUserId(userId);
		obj.init("");

		session.setAttribute("obj", obj);
		session.setAttribute("congtyId", obj.getCongtyId());

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKho.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpNhapkhoList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}

		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		if (action.equals("Tao moi"))
		{
			IErpNhapkho nkBean = new ErpNhapkho();
			nkBean.setCongtyId((String)session.getAttribute("congtyId"));
			nkBean.createRs();

			session.setAttribute("nkBean", nkBean);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNew.jsp";
			response.sendRedirect(nextJSP);
		} 
		else
		{
			if (action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj = new ErpNhapkhoList();
				obj.setCongtyId((String)session.getAttribute("congtyId"));
				
				String search = getSearchQuery(request, obj);

				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				obj.setUserId(userId);
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhapKho.jsp");
			} 
			else
			{
				obj = new ErpNhapkhoList();
				obj.setCongtyId((String)session.getAttribute("congtyId"));

				String search = getSearchQuery(request, obj);
				obj.init(search);
				obj.setUserId(userId);

				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);

				response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhapKho.jsp");
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IErpNhapkhoList obj)
	{
		
		Utility util=new Utility();
		String query = "select a.PK_SEQ as nhId, a.PREFIX + cast(a.PK_SEQ as varchar(20)) as sonhapkho, "
			+ "case "
			+ "when a.SOPHIEUNHAPHANG is not null then (g.PREFIX + f.PREFIX + cast(a.SOPHIEUNHAPHANG as varchar(20)) ) "
			+ "when a.SODONTRAHANG is not null then (h.PREFIX + cast(a.SODONTRAHANG as varchar(20))) "
			+ "else (k.PREFIX + cast(a.SOLENHSANXUAT as varchar(20))) "
			+ "end as SOCHUNGTU, "
			+ "a.NGAYNHAPKHO, a.NOIDUNGNHAP, b.TEN as ndnTen, a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua  "
			+ "from ERP_NHAPKHO a inner join ERP_NOIDUNGNHAP b on a.NOIDUNGNHAP = b.PK_SEQ "
			+ "inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  "
			+ "left join ERP_NHANHANG f on a.SOPHIEUNHAPHANG = f.pk_seq left join ERP_DONVITHUCHIEN g on f.donvithuchien_fk = g.pk_seq "
			+ "left join DONTRAHANG h on a.SODONTRAHANG = h.pk_seq " +
		      " left join erp_lenhsanxuat k on a.solenhsanxuat = k.pk_seq  " +
		      " where a.congty_fk = '" + obj.getCongtyId() + "'  and g.pk_seq in "+ util.quyen_donvithuchien(obj.getUserId());

		String tungay = request.getParameter("tungay");
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = request.getParameter("denngay");
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String sonhanhang = request.getParameter("sonhanhang");
		if (sonhanhang == null)
			sonhanhang = "";
		obj.setSoPnh(sonhanhang);

		String trangthai = request.getParameter("trangthai");
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		if (tungay.length() > 0)
			query += " and a.NGAYNHAPKHO >= '" + tungay + "'";

		if (denngay.length() > 0)
			query += " and a.NGAYNHAPKHO <= '" + denngay + "'";

		if (sonhanhang.length() > 0)
		{
			query += " and (case " +
				"when a.SOPHIEUNHAPHANG is null then (h.PREFIX + cast(a.SODONTRAHANG as varchar(20))) " +
				"else (g.PREFIX + f.PREFIX + cast(a.SOPHIEUNHAPHANG as varchar(20)) ) " + "end) = '" + sonhanhang +
				"' ";
		}

		if (trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";

		// query +=
		// "order by a.NGAYNHAPKHO desc, a.trangthai asc, a.pk_seq desc";
		return query;
	}

	private String Delete(String nhId)
	{
		dbutils db = new dbutils();

		try
		{
			db.getConnection().setAutoCommit(false);

			// db.update("delete ERP_MUAHANG_SP where muahang_fk = '" + dmhId +
			// "'");
			// db.update("delete ERP_MUAHANG where pk_seq = '" + dmhId + "'");

			db.update("update ERP_Nhapkho set trangthai = '3' where pk_seq = '" + nhId + "'");

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} catch (SQLException e)
		{
			db.shutDown();
			return "Khong the xoa nhap kho";
		}
	}

	private String Chot(String nhId, String userId)
	{
		IErpNhapkho nhapkho = new ErpNhapkho(nhId);
		
		return nhapkho.chotNhapKho(userId);
		
	}

}
