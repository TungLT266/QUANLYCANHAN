package geso.traphaco.distributor.servlets.banggiabandoitac;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.banggiabandoitac.IBanggiabandoitac;
import geso.traphaco.distributor.beans.banggiabandoitac.IBanggiabandoitacList;
import geso.traphaco.distributor.beans.banggiabandoitac.imp.Banggiabandoitac;
import geso.traphaco.distributor.beans.banggiabandoitac.imp.BanggiabandoitacList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class BanggiabandoitacSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	public BanggiabandoitacSvl()
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
		System.out.println("User  : " + userId);

		if (userId.length() == 0)
			userId = (String) session.getAttribute("userId");

		String action = util.getAction(querystring);
		out.println(action);

		String bgId = util.getId(querystring);

		IBanggiabandoitacList obj = new BanggiabandoitacList();

		if (action.equals("delete"))
		{
			obj.setMsg(Delete(bgId,userId));
			obj.setUserId(userId);
			obj.init("");
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Distributor/BangGiaBanDoiTac.jsp";
			response.sendRedirect(nextJSP);
		} 
		else
		{
			obj.setUserId(userId);
			obj.init("");
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Distributor/BangGiaBanDoiTac.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		IBanggiabandoitacList obj;
		obj = new BanggiabandoitacList();
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}
		
		String bgTen = util.antiSQLInspection(request.getParameter("bgTen"));
		if (bgTen == null)
			bgTen = "";
		obj.setTen(bgTen);

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		obj.setDvkdId(dvkdId);

		String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
		if (kenhId == null)
			kenhId = "";
		obj.setKenhId(kenhId);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		if (action.equals("new"))
		{
			IBanggiabandoitac bgBean = (IBanggiabandoitac) new Banggiabandoitac();
			bgBean.setUserId(userId);
			bgBean.createRS();
			
			session.setAttribute("bgBean", bgBean);
			String nextJSP = "/TraphacoHYERP/pages/Distributor/BangGiaBanDoiTacNew.jsp";
			response.sendRedirect(nextJSP);
		} 
		else if (action.equals("search"))
		{
			String search = getSearchQuery(request, obj);
			obj.setUserId(userId);
			obj.init(search);
			session.setAttribute("obj", obj);
			response.sendRedirect("/TraphacoHYERP/pages/Distributor/BangGiaBanDoiTac.jsp");
		} 
	}

	private String getSearchQuery(HttpServletRequest request, IBanggiabandoitacList obj)
	{
		String query =  "select a.pk_seq as id, a.ten as ten,b.donvikinhdoanh as dvkd, c.ten as kenh,a.tungay, a.trangthai as trangthai, d.ten as nguoitao, a.ngaytao as ngaytao, a.ngaysua as ngaysua,e.ten as nguoisua,   c.pk_seq as kenhId " +
						"from BANGGIABANDOITAC a inner join donvikinhdoanh b on a.dvkd_fk = b.pk_seq " +
						"	inner join kenhbanhang c on a.kenh_fk = c.pk_seq" +
						"	inner join nhanvien d on a.nguoitao = d.pk_seq" +
						"	inner join nhanvien e on a.nguoisua = e.pk_seq " +
						"where a.npp_fk = '" + obj.getNppId() + "' ";

		Utility util = new Utility();
		if (obj.getTen().length() > 0)
		{
			query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(obj.getTen()) + "%')";
		}

		if (obj.getDvkdId().length() > 0)
		{
			query = query + " and b.pk_seq = '" + obj.getDvkdId() + "'";
		}

		if (obj.getKenhId().length() > 0)
		{
			query = query + " and c.pk_seq = '" + obj.getKenhId() + "'";
		}

		if (obj.getTrangthai().length() > 0)
		{
			query = query + " and a.trangthai = '" + obj.getTrangthai() + "'";
		}
		
		System.out.println("\nSearch Query: "+query);
		return query;
	}

	private String Delete(String id,String userId)
	{
		dbutils db = new dbutils();
		try
		{
			String 
			query = 
				"	 SELECT COUNT(*) AS SoDong "+    
				"	 FROM DONDATHANG DH "+ 
				"	 WHERE EXISTS "+
				"	 ( "+
				"		SELECT * FROM "+
				"		BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
				"		B ON A.PK_SEQ=B.BANGGIAMUANPP_FK  "+
				"		WHERE  B.NPP_FK=DH.NPP_FK "+
				"		AND A.KENH_FK=DH.KBH_FK AND A.DVKD_FK=DH.DVKD_FK "+
				"		AND A.TUNGAY<=DH.NGAYDAT AND DH.TRANGTHAI!=6 "+
				"		AND A.PK_SEQ='"+id+"' "+
				"	 ) " ;
			System.out.println("[DONDATHANG]" + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			if (rs != null)
				rs.close();
			if (sodong > 0)
			{
				return "Bảng giá đã được sử dụng trong đơn đặt hàng,vui lòng kiểm tra lại !";
			}
			query = 
			" SELECT COUNT(*) AS SoDong "+    
			"  FROM DONHANG DH "+
			" WHERE EXISTS "+
			"  ( "+
			"	SELECT * FROM  "+
			"	BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
			"	B ON A.PK_SEQ=B.BANGGIAMUANPP_FK "+
			"	WHERE  B.NPP_FK=DH.NPP_FK "+
			"	AND A.KENH_FK=DH.KBH_FK "+
			"	AND A.TUNGAY<=DH.NGAYNHAP AND DH.TRANGTHAI!=2 "+
			"	AND A.PK_SEQ='"+id+"' "+
			"  ) ";
			
			rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			if (rs != null)
				rs.close();
			if (sodong > 0)
			{
				return "Bảng giá đã được sử dụng trong đơn hàng ,vui lòng kiểm tra lại !";
			}
			
			db.getConnection().setAutoCommit(false);
		
			query=
			"  INSERT INTO BANGGIA_LOG(NGUOISUA,BG_FK,SANPHAM_FK,GIA) "+
			"  SELECT '"+userId+"',BGMUANPP_FK,SANPHAM_FK,GIAMUANPP "+
			" FROM BGMUANPP_SANPHAM "+
			" WHERE BGMUANPP_FK='"+id+"' ";
			if (!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
			}
			query = "delete from bgmuanpp_sanpham where bgmuanpp_fk='" + id + "'";
			if (!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
			}
			query = "delete from BANGGIAMUANPP_NPP where BANGGIAMUANPP_FK='" + id + "'";
			if (!db.update(query))
			{
				System.out.print(query);
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
			}
			query = "delete from banggiamuanpp where pk_seq = '" + id + "'";
			if (!db.update(query))
			{
				System.out.print(query);
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			db.update("rollback");
			return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		return dateFormat.format(date);
	}

}

