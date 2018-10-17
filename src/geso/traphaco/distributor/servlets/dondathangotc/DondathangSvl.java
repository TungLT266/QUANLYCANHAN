package geso.traphaco.distributor.servlets.dondathang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.dondathang.IDondathang;
import geso.traphaco.distributor.beans.dondathang.IDondathangList;
import geso.traphaco.distributor.beans.dondathang.imp.Dondathang;
import geso.traphaco.distributor.beans.dondathang.imp.DondathangList;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DondathangSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	public DondathangSvl()
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

			IDondathangList obj;
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			userId = (String) session.getAttribute("userId");
			userTen = (String) session.getAttribute("userTen");
			sum = (String) session.getAttribute("sum");
			Utility util = (Utility) session.getAttribute("util");
			if (!util.check(userId, userTen, sum))
			{
				response.sendRedirect("/index.jsp");
			} else
			{
				session.setMaxInactiveInterval(1200);
				String querystring = request.getQueryString();
				String action = util.getAction(querystring);

				String ddhId = util.getId(querystring);
				if (action.equals("delete"))
				{
					Delete(ddhId, out);
				}
				if (action.equals("chot"))
				{
					Chot(ddhId);
				}
				obj = new DondathangList();
				obj.setUserId(userId);
				obj.createDdhlist("");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHang.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private void Chot(String ddhId)
	{
		dbutils db = new dbutils();
		String sql = "update dondathang set trangthai=1,NgayDat='"+getDateTime()+"',NGAYGIODAT=GETDATE() where pk_Seq=" + ddhId;

		db.update(sql);
		db.update("delete dondathang_sp where soluong <=0 and dondathang_fk=" + ddhId);
		db.shutDown();
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
			IDondathangList obj;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();

			userId = (String) session.getAttribute("userId");
			userTen = (String) session.getAttribute("userTen");
			sum = (String) session.getAttribute("sum");
			Utility util = (Utility) session.getAttribute("util");
			if (!util.check(userId, userTen, sum))
			{
				response.sendRedirect("index.jsp");
			} else
			{
				session.setMaxInactiveInterval(1200);
				obj = new DondathangList();

				String action = request.getParameter("action");
				if (action == null)
				{
					action = "";
				}
				if (action.equals("new"))
				{
					IDondathang ddhBean = (IDondathang) new Dondathang();
					ddhBean.setUserId(userId);
					ddhBean.init0();
					session.setAttribute("ddhBean", ddhBean);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHangParam.jsp";
					response.sendRedirect(nextJSP);
				} else if (action.equals("view") || action.equals("next") || action.equals("prev"))
				{

					String nppId = "";
					geso.traphaco.distributor.util.Utility dutil = new geso.traphaco.distributor.util.Utility();
					nppId = dutil.getIdNhapp(userId);
					String search = getSearchQuery(request, util, nppId, obj);
					obj.setUserId(userId);
					session.setAttribute("userId", userId);
					session.setAttribute("nhappid", "");
					session.setAttribute("dvkdid", "");
					session.setAttribute("kenhid", "");
					session.setAttribute("donhangid", "");
					session.setAttribute("tuvanchuyen", "");
					obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
					obj.createDdhlist(search);
					obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHang.jsp";
					System.out.print(search);
					response.sendRedirect(nextJSP);
				} else
				{
					String nppId = "";
					geso.traphaco.distributor.util.Utility dutil = new geso.traphaco.distributor.util.Utility();
					nppId = dutil.getIdNhapp(userId);
					String search = getSearchQuery(request, util, nppId, obj);
					obj.setUserId(userId);
					session.setAttribute("userId", userId);
					obj.createDdhlist(search);
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHang.jsp";
					System.out.print(search);
					response.sendRedirect(nextJSP);
				}
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, Utility util, String nppId, IDondathangList obj)
	{
		String sku = util.ValidateParam(util.antiSQLInspection(request.getParameter("masp")));
		if (sku == null)
			sku = "";
		obj.setSKU(sku);

		String tungay = util.ValidateParam(util.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = util.ValidateParam(util.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String trangthai = util.ValidateParam(util.antiSQLInspection(request.getParameter("trangthai")));
		if (trangthai == null)
			trangthai = "";

		obj.setTrangthai(trangthai);

		String query = "select distinct a.ngaydat, a.pk_seq as chungtu,e.donvikinhdoanh, a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai from dondathang a, nhanvien b, nhanvien c, dondathang_sp d, donvikinhdoanh e where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.dondathang_fk and a.dvkd_fk = e.pk_seq and a.npp_fk='"
				+ nppId + "'";
		if (sku.length() > 0)
		{
			query = query + " and d.sanpham_fk ='" + sku + "'";
		}

		if (tungay.length() > 0)
		{
			query = query + " and a.ngaydat >= '" + tungay + "'";

		}

		if (denngay.length() > 0)
		{
			query = query + " and a.ngaydat <= '" + denngay + "'";

		}

		if (trangthai.length() > 0)
		{
			query = query + " and a.trangthai = '" + trangthai + "'";

		}
		return query;
	}

	private void Delete(String id, PrintWriter out)
	{

		dbutils db = new dbutils();
		ResultSet rs = db.get("select count (ddh.pk_Seq) as num from dondathang ddh inner join denghidathang dndh on dndh.pk_Seq=ddh.denghidathang_fk  where ddh.pk_seq = '" + id + "'");
		try
		{
			rs.next();

			if (!(rs.getString("num").equals("0")))
			{
				String query = "select denghidathang_fk as dndhId from dondathang where pk_seq = '" + id + "'";
				ResultSet rs2 = db.get(query);
				System.out.println("vao denghidathang_fk :" + query);
				if (rs2.next())
				{

					String dndhId = rs2.getString("dndhId");
					out.println("dndhId = " + dndhId);
					rs2.close();

					rs2 = db.get("select * from dondathang_sp where dondathang_fk ='" + id + "'");
					double total = 0;
					while (rs2.next())
					{
						String masp = rs2.getString("sanpham_fk");
						String sotienbvat = rs2.getString("sotienbvat").trim();

						ResultSet rs3 = db.get("select count(dadathang) as num from denghidathang_sp where denghidathang_fk='" + dndhId + "' and sanpham_fk='" + masp + "'");

						rs3.next();
						if (!(rs3.getString("num").equals("0")))
						{
							ResultSet rs4 = db.get("select dadathang  from denghidathang_sp where denghidathang_fk='" + dndhId + "' and sanpham_fk='" + masp + "'");
							rs4.next();

							total = total + Double.valueOf(sotienbvat).doubleValue();
							String dadathang = rs4.getString("dadathang");

							double tmp = Double.valueOf(dadathang).doubleValue() - Double.valueOf(sotienbvat).doubleValue();

							if (tmp < 0)
								tmp = 0;
							String command = "update denghidathang_sp set dadathang ='" + tmp + "' where sanpham_fk='" + masp + "' and denghidathang_fk='" + dndhId + "'";

							if (!db.update(command))
							{

							}
							rs4.close();
						}
						rs3.close();
					}
					rs2.close();
					rs2 = db.get("select dadathang from denghidathang where pk_seq ='" + dndhId + "'");
					rs2.next();
					total = Double.valueOf(rs2.getString("dadathang")).doubleValue() - Math.round(total * 1.1);
					if (total < 0)
						total = 0;
					if (total == 0)
					{
						db.update("update denghidathang set dadathang='" + total + "', trangthai='1' where pk_seq =" + dndhId + "");
					} else
					{
						db.update("update denghidathang set dadathang='" + total + "' where pk_seq =" + dndhId + "");
					}
				}
				rs.close();
			}

			String sql = "";
			sql=
			"	update ThanhToanCKTM set sudung=ISNULL(SuDung,0)-ISNULL(ChietKhauThuongMai,0) "+
			"	from  \n"+
			"	( \n"+
			"		select \n"+ 
			"		sum  \n"+
			"		(   "+
			"			(  \n"+
			"				a.soluongduyet*a.giachuan*(1-(isnull(a.chietkhau,0)/100)) \n"+ 
			"			) *(1-isnull(b.chietkhau,0)/100 )  \n"+
			"		) as doanhso,b.npp_fk,(select convert(varchar(10),dateadd(month,-1,substring(ngaydat,0,8)+'-01'),20)) as thoigian,ChietKhauThuongMai \n"+
			"		from dondathang_sp a inner join dondathang b on a.dondathang_fk=b.pk_seq   \n"+
			"		where a.dondathang_fk='"+id+"'  \n"+
			"		group by b.npp_fk,ngaydat,ChietKhauThuongMai \n"+
			"	) as dh inner join ThanhToanCKTM as ck on \n"+
			"	dh.npp_fk=ck.npp_fk and ck.nam=substring(dh.thoigian,0,5) and ck.thang=substring(dh.thoigian,6,2) ";		
			if(!db.update(sql))
			{
				db.update("rollback");
				System.out.println("[Cap nhat lai chiet khau ]"+sql);
			}
			sql = "delete from dondathang_sp where dondathang_fk='" + id + "'";
			System.out.println("vao dondathang_sp :" + sql);
			db.update(sql);
			sql = "delete from thieuhang where dondathang_fk='" + id + "'";
			System.out.println("vao thieuhang :" + sql);
			db.update(sql);
			
			sql = "delete from dondathang where pk_seq = " + id + "";
			System.out.println("vao dondathang :" + sql);
			db.update(sql);
			db.shutDown();
		} catch (java.sql.SQLException e)
		{

		}
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}