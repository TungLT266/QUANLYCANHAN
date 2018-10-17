package geso.traphaco.center.servlets.dontrahang;

import geso.traphaco.center.beans.dontrahang.IDontrahang;
import geso.traphaco.center.beans.dontrahang.IDontrahangList;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import geso.traphaco.center.beans.dontrahang.imp.Dontrahang;
import geso.traphaco.center.beans.dontrahang.imp.DontrahangList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.http.HttpSession;

public class Duyetdontrahang extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Duyetdontrahang()
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
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if (!util.check(userId, userTen, sum))
		{
			response.sendRedirect("/index.jsp");
		} else
		{
			session.setMaxInactiveInterval(1200);
			String querystring = request.getQueryString();
			String action = util.getAction(querystring);
			if (action == null)
			{
				action = "";
			}
			String id = util.getId(querystring);
			if (id == null)
			{
				id = "";
			}
			if (action.equals("display"))
			{
				IDontrahang dthBean = (IDontrahang) new Dontrahang();
				dthBean.setUserId(userId);
				dthBean.setId(id);
				dthBean.initUpdate();
				String nextJSP = "/TraphacoHYERP/pages/Center/DonTraHangDisplay.jsp";
				session.setAttribute("dthBean", dthBean);
				response.sendRedirect(nextJSP);
			} else if (action.equals("delete"))
			{
				IDontrahangList obj = new DontrahangList();
				obj.setUserId(userId);
				this.DeleteDth(id, obj);
				obj.createDthlist("");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				IDontrahangList obj = new DontrahangList();
				obj.setUserId(userId);
				obj.createDthlist("");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
			}
		}

	}

	private boolean DeleteDth(String id, IDontrahangList obj)
	{
		dbutils db = new dbutils();
		ResultSet rs = db.get("select count(pk_seq) as num from dontrahang where pk_seq='" + id + "' and trangthai = '0'");
		try
		{
			db.getConnection().setAutoCommit(false);
			rs.next();
			if(!rs.getString("num").equals("0"))
			{
				rs.close();
				String sql="select sanpham_fk,sum(isnull(soluong,0)) as soluong,npp_fk,kbh_fk,kho_fk from dontrahang_sp dth_sp inner join dontrahang dth "+  
							" on dth.pk_Seq=dth_sp.dontrahang_fk where dth.pk_seq='"+id+"'" +
							" group by sanpham_fk,npp_fk,kbh_fk,kho_fk ";
					rs=db.get(sql);
					while(rs.next())
					{
						sql="update nhapp_kho set booked = booked - " + rs.getString("soluong") + ", available = available + " + rs.getString("soluong") + " where npp_fk = '" + rs.getString("npp_fk") + "' and sanpham_fk = '" + rs.getString("sanpham_fk") + "' and kbh_fk = '" + rs.getString("kbh_fk") + "' and kho_fk='"+rs.getString("kho_fk") + "'";
						if(db.updateReturnInt(sql)!=1)
						{
							db.update("rollback");
							return false;
						}
					}
				rs.close();
				sql="select sanpham_fk,dth_sp.soluong as soluong,npp_fk,kbh_fk,kho_fk,dth_sp.solo,dth_sp.NgayHetHan from dontrahang_sp dth_sp inner join dontrahang dth "+  
					" on dth.pk_Seq=dth_sp.dontrahang_fk where dth.pk_seq='"+id+"'" ;
				rs=db.get(sql);
				while(rs.next())
				{
					sql="update nhapp_kho_chitiet set booked = booked - " + rs.getString("soluong") + ", available = available + " + rs.getString("soluong") + " where npp_fk = '" + rs.getString("npp_fk") + "' and sanpham_fk = '" + rs.getString("sanpham_fk") + "' and kbh_fk = '" + rs.getString("kbh_fk") + "' and kho_fk='"+rs.getString("kho_fk") + "' and solo='"+rs.getString("solo") + "' and NgayHetHan='"+rs.getString("NgayHetHan")+"'  ";
					if(db.updateReturnInt(sql)!=1)
					{
						db.update("rollback");
						return false;
					}
				}
				rs.close();
				sql="update dontrahang set trangthai='3' where pk_seq="+id;
				if(!db.update(sql))
				{
					db.update("rollback");
					return false;
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
			}
		} catch (Exception e)
		{
			db.update("rollback");
			obj.setMesage("Khong The Thu Hien Huy Don Tra Hang,Vui Long Kiem Tra Lai.Loi :" + e.toString());
			return false;
		}
		return true;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		IDontrahangList obj;
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if (!util.check(userId, userTen, sum))
		{
			response.sendRedirect("/index.jsp");
		} else
		{
			session.setMaxInactiveInterval(1200);

			String action = request.getParameter("action");

			if (action.equals("search"))
			{
				obj = new DontrahangList();
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);
				obj.createDthlist(search);
				session.setAttribute("obj", obj);

				String nextJSP = "/TraphacoHYERP/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
			} else if (action.equals("clean"))
			{
				obj = new DontrahangList();
				obj.setUserId(userId);
				obj.createDthlist("");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
			}else if (action.equals("new"))
			{
				obj = new DontrahangList();
				obj.setUserId(userId);
				obj.createDthlist("");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Center/DonTraHangNew.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				if (action.equals("duyet"))
				{
					String id = util.antiSQLInspection(request.getParameter("id"));
					IDontrahang dthBean = (IDontrahang) new Dontrahang();
					dthBean.setUserId(userId);
					dthBean.setId(id);
					dthBean.DuyetDthnpp();
				}
				obj = new DontrahangList();
				obj.setUserId(userId);
				obj.createDthlist("");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Center/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IDontrahangList obj)
	{
		// PrintWriter out = response.getWriter();

		Utility util = new Utility();
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";

		obj.setTrangthai(trangthai);

		String query = "select distinct a.ngaytra, a.pk_seq as chungtu,e.donvikinhdoanh, a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai from dontrahang a, nhanvien b, nhanvien c, dontrahang_sp d, donvikinhdoanh e where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.dontrahang_fk and a.dvkd_fk = e.pk_seq  ";

		if (tungay.length() > 0)
		{
			query = query + " and a.ngaytra >= '" + tungay + "'";

		}

		if (denngay.length() > 0)
		{
			query = query + " and a.ngaytra <= '" + denngay + "'";

		}

		if (trangthai.length() > 0)
		{
			query = query + " and a.trangthai = '" + trangthai + "'";

		}

		System.out.println("chuoi svl:" + query);
		return query;
	}

}
