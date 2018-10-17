package geso.traphaco.center.servlets.nhomsanpham;

import geso.traphaco.center.beans.nhomsanpham.INhomsanpham;
import geso.traphaco.center.beans.nhomsanpham.INhomsanphamList;
import geso.traphaco.center.beans.nhomsanpham.imp.Nhomsanpham;
import geso.traphaco.center.beans.nhomsanpham.imp.NhomsanphamList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomsanphamUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;
	private PrintWriter out;

	public NhomsanphamUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		this.out = response.getWriter();
		dbutils db = new dbutils();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);
		String query = "select a.pk_seq, a.ten, a.diengiai,a.loainhom, a.loaithanhvien, a.trangthai, a.nsp_parent_fk as parent,  a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua " + 
					   " from nhomsanpham a, nhanvien b, nhanvien c " + 
					   " where a.type='0' and  a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.pk_seq='" + id + "' order by nsp_parent_fk ";
		
		System.out.println("::: INIT: " + query);
		ResultSet rs = db.get(query);
		try
		{
			rs.next();
			String[] param = new String[11];
			param[0] = id;
			param[1] = rs.getString("ten");
			param[2] = rs.getString("diengiai");
			param[3] = rs.getString("loaithanhvien");
			param[4] = rs.getString("trangthai");
			param[5] = rs.getString("ngaytao");
			param[6] = rs.getString("ngaysua");
			param[7] = rs.getString("nguoitao");
			param[8] = rs.getString("nguoisua");
			param[9] = rs.getString("parent");
			param[10] = rs.getString("loainhom");

			INhomsanpham nspBean = new Nhomsanpham(param);			
			nspBean.UpdateRS();
			
			session.setAttribute("nspBean", nspBean);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/NhomSanPhamUpdate.jsp";
			response.sendRedirect(nextJSP);
			if (rs != null)
			{
				rs.close();
			}
		} 
		catch (Exception e)
		{
			out.println(e.toString());
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		INhomsanpham nspBean = new Nhomsanpham();

		Utility util = new Utility();

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		String ten = util.antiSQLInspection(request.getParameter("ten"));
		nspBean.setTen(ten);

		String id = util.antiSQLInspection(request.getParameter("nspId"));
		nspBean.setId(id);
		System.out.println("_____________________id :" + id);
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		nspBean.setDiengiai(diengiai);

		String thanhvien = util.antiSQLInspection(request.getParameter("thanhvien"));
		nspBean.setThanhvien(thanhvien);

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (!(dvkdId == null))
			nspBean.setDvkdId(dvkdId);

		String nhId = util.antiSQLInspection(request.getParameter("nhId"));
		if (!(nhId == null))
			nspBean.setNhId(nhId);

		String clId = util.antiSQLInspection(request.getParameter("clId"));
		if (!(clId == null))
			nspBean.setClId(clId);

		String ngaytao = getDateTime();
		nspBean.setNgaytao(ngaytao);

		String ngaysua = ngaytao;
		nspBean.setNgaysua(ngaysua);

		String nguoitao = userId;
		nspBean.setNguoitao(userId);

		String nguoisua = nguoitao;
		nspBean.setNguoisua(nguoisua);

		String trangthai;
		if (util.antiSQLInspection(request.getParameter("trangthai")) != null)
			trangthai = "1";
		else
			trangthai = "0";
		nspBean.setTrangthai(trangthai);

		String loainhom = util.antiSQLInspection(request.getParameter("lnhom"));
		nspBean.setLoainhom(loainhom);

		boolean error = false;
		if (ten.trim().length() > 0)
			nspBean.setTen(ten);
		else
		{
			nspBean.setMessage("Vui lòng nhập vào Nhóm sản phẩm");
			error = true;
		}

		String[] nhom = request.getParameterValues("nhom");
		nspBean.setNhomsp(nhom);


		String[] sanpham = request.getParameterValues("sanpham");
		nspBean.setSanpham(sanpham);

		String[] maspcb = request.getParameterValues("");
		nspBean.setSanpham(sanpham);
		String action = request.getParameter("action");
		session.setAttribute("userId", util.antiSQLInspection(request.getParameter("userId")));
		out.println(action);

		if (action.equals("filter") || error)
		{
			nspBean.UpdateRS();
			session.setAttribute("nspBean", nspBean);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/NhomSanPhamUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
		if (action.equals("save")) {
			if (nspBean.saveNhomSP(request))
			{
				INhomsanphamList obj = new NhomsanphamList();
				List<INhomsanpham> nsplist = new ArrayList<INhomsanpham>();
				getNspBeanList("0", nsplist);
				obj.setNspList(nsplist);
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Center/NhomSanPham.jsp";
				response.sendRedirect(nextJSP);
			}

		}
		
		else
		{/*

			session.setAttribute("userId", nguoitao);
			
			if (!nspBean.updateNsp())
			{
				nspBean.UpdateRS();
				session.setAttribute("nspBean", nspBean);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Center/NhomSanPhamUpdate.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				INhomsanphamList obj = new NhomsanphamList();
				List<INhomsanpham> nsplist = new ArrayList<INhomsanpham>();

				getNspBeanList("0", nsplist);

				// Save data into session
				
				obj.setNspList(nsplist);

				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Center/NhomSanPham.jsp";
				response.sendRedirect(nextJSP);

			}

		*/}

	}

	private boolean kiemtra_tontai(String sanphamfk, int mien) {
		// mien bac
		dbutils db = new dbutils();
		Utility util = new Utility();
		if (mien == 1) {
			String query = "	select SP_FK, COUNT(SP_FK) as sl from NHOMSANPHAM_SANPHAM  where  SP_FK in ("
					+ sanphamfk + ") group by SP_FK, NSP_FK";
			ResultSet rs = db.get(query);
			if (rs != null) {
				try {
					while (rs.next()) {
						int sl = rs.getInt("sl");
						if (sl > 1)
							return false;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// mien trung
		if (mien == 2) {
			String query = "	select SP_FK, COUNT(SP_FK) as sl from NHOMSANPHAM_MIENTRUNG_SANPHAM  where  SP_FK in ("
					+ sanphamfk + ") group by SP_FK, NSP_FK";
			ResultSet rs = db.get(query);
			if (rs != null) {
				try {
					while (rs.next()) {
						int sl = rs.getInt("sl");
						if (sl > 1)
							return false;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// mien nam
		if (mien == 3) {
			String query = "	select SP_FK, COUNT(SP_FK) as sl from NHOMSANPHAM_MIENNAM_SANPHAM  where  SP_FK in ("
					+ sanphamfk + ") group by SP_FK, NSP_FK";
			ResultSet rs = db.get(query);
			if (rs != null) {
				try {
					while (rs.next()) {
						int sl = rs.getInt("sl");
						if (sl > 1)
							return false;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private void getNspBeanList(String parent, List<INhomsanpham> nsplist)
	{
		dbutils db = new dbutils();
		String query = "select a.nsp_parent_fk as parent, a.loainhom, a.pk_seq, a.ten, a.diengiai, a.loaithanhvien, a.trangthai," +
	   			" a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomsanpham a, nhanvien b," +
	   			" nhanvien c where a.nguoitao = b.PK_SEQ and " +
	   			" a.nguoisua = c.PK_SEQ and a.nsp_parent_fk = '" + parent + "' and a.type='0' "+
		   		" union all "+
		   		 " select a.nsp_parent_fk as parent, a.loainhom, a.pk_seq, a.ten, a.diengiai, a.loaithanhvien, a.trangthai, a.ngaytao, "+
		   		"  a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from NHOMSANPHAM_NHAPKHAU a, nhanvien b, nhanvien c "+ 
		   		"  where a.nguoitao = b.PK_SEQ and  a.nguoisua = c.PK_SEQ and  a.nsp_parent_fk = '" + parent + "' and a.type='0' "+ 
		   		"  order by pk_seq";
	   	
		ResultSet rs = db.get(query);
		try
		{
			String[] param = new String[11];
			INhomsanpham nspBean;
			while (rs.next())
			{
				param[0] = rs.getString("pk_seq");
				param[1] = rs.getString("ten");
				param[2] = rs.getString("diengiai");
				param[3] = rs.getString("loaithanhvien");
				param[4] = rs.getString("trangthai");
				param[5] = rs.getString("ngaytao");
				param[6] = rs.getString("ngaysua");
				param[7] = rs.getString("nguoitao");
				param[8] = rs.getString("nguoisua");
				param[9] = rs.getString("parent");
				nspBean = new Nhomsanpham(param);
				nsplist.add(nspBean);
				getNspBeanList(param[0], nsplist);
			}
			if (rs != null)
			{
				rs.close();
			}
		} catch (Exception e)
		{
		}
	}

}