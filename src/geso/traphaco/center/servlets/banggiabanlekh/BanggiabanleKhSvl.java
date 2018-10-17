package geso.traphaco.center.servlets.banggiabanlekh;

import geso.traphaco.center.beans.banggiablc.*;
import geso.traphaco.center.beans.banggiablc.imp.*;
import geso.traphaco.center.beans.banggiablkh.IBanggiablKh;
import geso.traphaco.center.beans.banggiablkh.IBanggiablKhList;
import geso.traphaco.center.beans.banggiablkh.imp.BanggiablKh;
import geso.traphaco.center.beans.banggiablkh.imp.BanggiablKhList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BanggiabanleKhSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	public BanggiabanleKhSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);
		out.println(action);

		IBanggiablKhList obj = new BanggiablKhList();
		obj.init("");
		obj.setUserId(userId);
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeKh.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		IBanggiablKhList obj;
		obj = new BanggiablKhList();
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}
		out.println(action);

		if (action.equals("new"))
		{
			IBanggiablKh bgBean = new BanggiablKh();
		    
	        bgBean.setUserId(userId);
	        session.setAttribute("bgblcBean", bgBean);
	        String nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeKhNew.jsp";
	        response.sendRedirect(nextJSP);

		}
		if (action.equals("search"))
		{
			String search = getSearchQuery(request, obj);

			obj.init(search);

			obj.setUserId(userId);

			session.setAttribute("obj", obj);

			response.sendRedirect("/TraphacoHYERP/pages/Center/BangGiaBanLeKh.jsp");

		}
	}

	private String getSearchQuery(HttpServletRequest request, IBanggiablKhList obj)
	{
		// PrintWriter out = response.getWriter();

		// IBanggiablcList obj = new BanggiablcList();

		Utility util = new Utility();

		String spma = util.antiSQLInspection(request.getParameter("spma"));
		if (spma == null)
			spma = "";
		obj.setSpMa(spma);

		String spten = util.antiSQLInspection(request.getParameter("spten"));
		if (spten == null)
			spten = "";
		obj.setSpTen(spten);

		String query = "select distinct s.PK_SEQ, s.MA, s.TEN as TENSP, bg.NGAYTAO, bg.NGAYSUA, nv1.TEN as NGUOITAO, nv2.TEN as NGUOISUA from BANGGIABANLEKH_SANPHAM bg inner join SANPHAM s on s.PK_SEQ = bg.SANPHAM_FK " +
		"left join NHANVIEN nv1 on nv1.PK_SEQ = bg.NGUOITAO left join NHANVIEN nv2 on bg.NGUOISUA = nv2.PK_SEQ where 1 =1";
		
		if (spten.length() > 0)
		{
			query = query + " and upper(dbo.ftBoDau(s.ten)) like upper(N'%" + util.replaceAEIOU(spten) + "%')";
		}

		if (spma.length() > 0)
		{
			query = query + " and s.ma like '%" + spma + "%'";

		}
		query = query + "  order by s.ten";
		System.out.println(query);
		return query;
	}

	private void Delete(String id)
	{
		dbutils db = new dbutils();
		db.update("delete from banggiablc_sanpham where bgblc_fk='" + id + "'");
		db.update("delete from banggiabanlechuan where pk_seq = '" + id + "'");
		db.shutDown();

	}
	private String Duyet(String id, String userId)
	{
		dbutils db = new dbutils();
		String query = "update sp set sp.DONGIA_TRUOC_CK = CASE WHEN sp.CHIETKHAU != 0 THEN b.GIABANLECHUAN else sp.DONGIA_TRUOC_CK end, sp.DONGIA = CASE WHEN sp.CHIETKHAU != 0 THEN b.GIABANLECHUAN * (1.0 - sp.CHIETKHAU/100) else sp.DONGIA END \n" +
						"from BANGGIABANLENPP npp inner join BANGGIABANLENPP_SANPHAM sp on sp.BANGGIABLNPP_FK = npp.PK_SEQ \n" +
						"inner join BANGGIABLC_SANPHAM b on b.BGBLC_FK = npp.BANGGIABANLECHUAN_FK and b.SANPHAM_FK = sp.SANPHAM_FK \n" +
						"where npp.BANGGIABANLECHUAN_FK = '"+id+"'";
		//System.out.println(query);
		if(!db.update(query)){
			db.shutDown();
			return "Lỗi duyệt bảng giá (1)";
		}
		query = "update BANGGIABANLECHUAN SET DADUYET = 1, NGUOIDUYET = "+userId+" WHERE PK_SEQ = " + id;
		if(!db.update(query)){
			db.shutDown();
			return "Lỗi duyệt bảng giá (2)";
		}
		db.shutDown();
		return "";
	}

}
