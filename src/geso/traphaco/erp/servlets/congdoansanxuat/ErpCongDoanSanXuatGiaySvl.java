package geso.traphaco.erp.servlets.congdoansanxuat;

import geso.dms.center.util.Utility;
import geso.dms.db.sql.dbutils;
import geso.traphaco.erp.beans.congdoansanxuatgiay.IErpCongDoanSanXuatGiay;
import geso.traphaco.erp.beans.congdoansanxuatgiay.IErpCongDoanSanXuatGiayList;
import geso.traphaco.erp.beans.congdoansanxuatgiay.imp.ErpCongDoanSanXuatGiay;
import geso.traphaco.erp.beans.congdoansanxuatgiay.imp.ErpCongDoanSanXuatGiayList;
 

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ErpCongDoanSanXuatGiaySvl")
public class ErpCongDoanSanXuatGiaySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public ErpCongDoanSanXuatGiaySvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

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

		String ctyId = (String) session.getAttribute("congtyId");
		IErpCongDoanSanXuatGiayList obj = new ErpCongDoanSanXuatGiayList();
		obj.setCtyId(ctyId);

		obj.setUserId(userId);

		String action = util.getAction(querystring);
		String khlId = util.getId(querystring);
		String msg = "";

		if (action.trim().equals("delete"))
		{
			dbutils db = new dbutils();
			if (!db.update("delete from Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay where CongDoanSanXuat_FK = '" + khlId + "' " +
					" Delete from ERP_CONGDOANSANXUAT_TAISAN where CONGDOAN_FK='"+ khlId + "'"+
					" Delete from Erp_CongDoanSanXuat_Giay where pk_seq = '" + khlId + "'" +
					" Delete from ERP_CONGDOANSANXUAT_HOSOKN where CONGDOANSX_FK = '" + khlId + "'"))
			{
				msg = "Không thể xóa Erp_CongDoanSanXuat_Giay";
			}
			db.shutDown();
		}

		obj.init("");
		obj.setMsg(msg);
		session.setAttribute("cdsxList", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDoanSanXuatGiay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();

		out = response.getWriter();
		Utility util = new Utility();

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		String ctyId = (String) session.getAttribute("congtyId");

		IErpCongDoanSanXuatGiayList obj;

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}
		if (action.equals("new"))
		{
			IErpCongDoanSanXuatGiay csx = new ErpCongDoanSanXuatGiay();
			csx.setCtyId(ctyId);
			csx.createRs();
			csx.setUserId(userId);
			session.setAttribute("cdsxBean", csx);
			session.setAttribute("userId", userId);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpCongDoanSanXuatGiayNew.jsp");
		} else
		{
			obj = (IErpCongDoanSanXuatGiayList) new ErpCongDoanSanXuatGiayList();
			obj.setCtyId(ctyId);
			obj.setUserId(userId);
			String search = getSearchQuery(request, obj);
			obj.setUserId(userId);
			obj.init(search);
			session.setAttribute("cdsxList", obj);
			session.setAttribute("userId", userId);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpCongDoanSanXuatGiay.jsp");
		}
	}

	private String getSearchQuery(HttpServletRequest request, IErpCongDoanSanXuatGiayList obj)
	{
		Utility util = new Utility();

		String ma = util.antiSQLInspection(request.getParameter("macd"));
		if (ma == null)
			ma = "";
		obj.setMa(ma);

		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String sql ="select  a.pk_seq, a.diengiai  "+
			"   , a.sonhancong, a.trangthai, c.ten as nguoitao, a.ngaytao, d.ten as nguoisua, a.ngaysua,nm.tennhamay as nhamay   "+
			"   from Erp_CongDoanSanXuat_Giay a left join erp_nhamay nm on nm.pk_seq=a.nhamay_fk"+       
			"	inner join NhanVien c on a.nguoitao = c.pk_seq "+
			"	inner join nhanvien d on a.nguoisua = d.pk_seq "+       
			"   where a.pk_seq > 0 and  a.nhamay_fk in " +
			"   (select pk_seq from erp_nhamay where congty_fk = " + obj.getCtyId()+ ") and a.congty_fk= "+ obj.getCtyId();

		if (ma.length() > 0)
			sql += " and  cast  ( a.pk_seq as nvarchar(10) ) like '%"+ma+"%'";

		if (diengiai.length() > 0)
			sql += " and dbo.ftBoDau(a.diengiai) like N'%" + util.replaceAEIOU(diengiai) + "%' ";

		if (trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		sql += " order by a.pk_seq desc ";

		return sql;
	}
}
