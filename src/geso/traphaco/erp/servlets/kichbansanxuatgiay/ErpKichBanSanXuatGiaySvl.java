package geso.traphaco.erp.servlets.kichbansanxuatgiay;



import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kichbansanxuatgiay.IErpKichBanSanXuatGiay;
import geso.traphaco.erp.beans.kichbansanxuatgiay.IErpKichBanSanXuatGiayList;
import geso.traphaco.erp.beans.kichbansanxuatgiay.imp.ErpKichBanSanXuatGiay;
import geso.traphaco.erp.beans.kichbansanxuatgiay.imp.ErpKichBanSanXuatGiayList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ErpKichBanSanXuatGiaySvl")
public class ErpKichBanSanXuatGiaySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public ErpKichBanSanXuatGiaySvl()
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

		IErpKichBanSanXuatGiayList obj = new ErpKichBanSanXuatGiayList();
		obj.setCtyId(ctyId);
		obj.setUserId(userId);

		String action = util.getAction(querystring);
		String khlId = util.getId(querystring);
		String msg = "";

		if (action.trim().equals("delete")) {
			dbutils db = new dbutils();
			String query = "select KICHBANSANXUAT_FK from ERP_LENHSANXUAT_GIAY where TRANGTHAI != 7 and KICHBANSANXUAT_FK is not null group by KICHBANSANXUAT_FK";
			String kbsxid = "";
			ResultSet rs = db.get(query);
			try {
				if(rs != null){
					while(rs.next()){
						kbsxid += rs.getString("KICHBANSANXUAT_FK") + ",";
					}
					rs.close();
				}
			} catch (Exception e) {}
			
			System.out.println(kbsxid);
			
			if(kbsxid.indexOf(khlId) >= 0){
				msg = "Kịch bản này đang được sử dụng trong lệnh sản xuất.";
				query = "update Erp_KichBanSanXuat_Giay set trangthai = 0 where pk_seq = " + khlId;
			} else {
				query = "delete Erp_KichBanSanXuat_Giay where pk_seq = " + khlId
						+ " delete Erp_KichBanSanXuat_CongDoanSanXuat_Giay where KichBanSanXuat_FK = " + khlId
						+ " delete Erp_kichbansx_cdsx_thongso where kbsx_fk = " + khlId;
			}
			
			if (!db.update(query)) {
				msg = "Xãy ra lỗi hệ thống. Vui lòng đăng nhập lại.";
			}
			db.shutDown();
		}

		obj.init("");
		obj.setMsg(msg);
		session.setAttribute("kbsxList", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatGiay.jsp";
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
		String vattuId = util.antiSQLInspection(request.getParameter("vattuId"));
		String action = request.getParameter("action");
		IErpKichBanSanXuatGiayList obj;

		if (action == null)
			action = "";
		
		if (action.equals("new")) {
			IErpKichBanSanXuatGiay khl = new ErpKichBanSanXuatGiay();
			khl.setCtyId(ctyId);
			khl.createRs();
			khl.setUserId(userId);
			
			session.setAttribute("kbsxBean", khl);
			session.setAttribute("userId", userId);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatGiayNew.jsp");
		} else if (action.equals("ajaxDinhLuong")) {
			if (vattuId != null) {
				dbutils db = new dbutils();
				String query = "SELECT PK_SEQ,DIENGIAI,SANPHAM_FK FROM ERP_DINHLUONG "+
				" WHERE SANPHAM_FK "+ 
				" IN( "+	
				"	SELECT PK_SEQ FROM SANPHAM WHERE PK_SEQ IN "+
				"	( SELECT SANPHAM_FK FROM ERP_DANHMUCVATTU WHERE PK_SEQ ='"+vattuId+"'"+
				"	)) and congty_fk="+ctyId;

				System.out.println("Query lay ERP_Dinhluong la: " +query + "\n");
				ResultSet rs = db.get(query);
				if (rs != null)
				{
					try
					{
						out.write("<option value=''></option>");
						while (rs.next())
						{
							out.write("<option value='" + rs.getString("PK_SEQ") + "'>" + rs.getString("DIENGIAI") +
								"</option>");
						}rs.close();
					} catch (SQLException e)
					{
					}
				}
			}
		}else
		{
			if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
				obj = new ErpKichBanSanXuatGiayList();
				obj.setCtyId(ctyId);
				
		    	String search = getSearchQuery(request, obj);
		    	
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("kbsxList", obj);
		    	session.setAttribute("userId", userId);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatGiay.jsp");
		    }
	    	else
	    	{
			
			obj = new ErpKichBanSanXuatGiayList();
			obj.setCtyId(ctyId);
			obj.setUserId(userId);
			String search = getSearchQuery(request, obj);
			obj.setUserId(userId);
			obj.init(search);
			session.setAttribute("kbsxList", obj);
			session.setAttribute("userId", userId);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatGiay.jsp");
	    	}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IErpKichBanSanXuatGiayList obj)
	{
		Utility util = new Utility();
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		if (ma == null)
			ma = "";
		obj.setMa(ma);

		String sanpham = util.antiSQLInspection(request.getParameter("sanpham"));
		if (sanpham == null)
			sanpham = "";
		obj.setSanpham(sanpham);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nhamayid = util.antiSQLInspection(request.getParameter("nhamayId"));
		if (nhamayid == null)
			nhamayid = "";
		obj.setNhaMayID(nhamayid);

		String sql =    "	select  distinct a.pk_seq, a.DienGiai as DienGiai, d.ma as spMa, isnull(d.ma + ' ' + d.ten , '') as spTen, a.trangthai,'' as NhaMay," +
						"	b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua     " +
						"	from Erp_KichBanSanXuat_Giay a inner join NhanVien b on a.nguoitao = b.pk_seq      " +
						"	inner join nhanvien c on a.nguoisua = c.pk_seq   " +
						"	left join erp_sanpham d on  d.ma = a.masanpham and d.CONGTY_FK = a.CongTy_FK  " +
						//"	inner join ERP_NHAMAY e on e.pk_seq=a.NhaMay_FK " +
						"	where 1 = 1 ";
		if (obj.getCtyId() != null && obj.getCtyId().trim().length() > 0 && obj.getCtyId().trim().equals("null") == false)
			sql += " and a.congty_fk='" + obj.getCtyId() + "' ";

						if (ma.length() > 0)
							sql += " and cast( a.pk_seq as nvarchar(10)) like N'%" + ma + "%' ";
				
						/*if (sanpham.length() > 0)
							sql += " and  timkiem like N'%" + util.replaceAEIOU(sanpham) + "%'    ";*/
						
						if (sanpham.length() > 0)
							sql += " and  a.SanPham_FK  in ( "+ sanpham +") ";
				
						if (trangthai.length() > 0)
							sql += " and a.trangthai = '" + trangthai + "' ";
						
						if (nhamayid.length() > 0)
							sql += " and e.pk_seq = '" + nhamayid + "' ";

		
		
		System.out.println("Get Data : "+sql);
		

		return sql;
	}

}
