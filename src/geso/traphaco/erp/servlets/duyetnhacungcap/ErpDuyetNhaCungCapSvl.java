package geso.traphaco.erp.servlets.duyetnhacungcap;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.duyetnhacungcap.IErpDuyetNhaCungCap;
import geso.traphaco.erp.beans.duyetnhacungcap.IErpDuyetNhaCungCapList;
import geso.traphaco.erp.beans.duyetnhacungcap.imp.ErpDuyetNhaCungCap;
import geso.traphaco.erp.beans.duyetnhacungcap.imp.ErpDuyetNhaCungCapList;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ErpDuyetNhaCungCapSvl")
public class ErpDuyetNhaCungCapSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	

	public ErpDuyetNhaCungCapSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
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
		String Id = util.getId(querystring);
		
		IErpDuyetNhaCungCapList listduyetncc= new ErpDuyetNhaCungCapList();
		
		
		if (action.equals("delete"))
		{
			listduyetncc.setId(Id);
			listduyetncc.Delete_duyetncc();
		}else if(action.equals("chot"))
		{
			listduyetncc.setId(Id);
			listduyetncc.Chot_duyetncc();
		}
		listduyetncc.Create_Sanpham();
		listduyetncc.Create_Nhacungcap();
		listduyetncc.Init("");
		session.setAttribute("obj", listduyetncc);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetNhaCungCapList.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String nextJSP = "";
		String listsanpham = util.antiSQLInspection(request.getParameter("listsanpham"));
		String listnhacungcap = util.antiSQLInspection(request.getParameter("listnhacungcap"));
		String action = request.getParameter("action");
		
		IErpDuyetNhaCungCapList listduyetncc= new ErpDuyetNhaCungCapList();
		listduyetncc.setSanPham_Fk(listsanpham);
		listduyetncc.setNhacungcap_Fk(listnhacungcap);
		System.out.println("Action : " + action);
		if (action == null)
		{
			action = "";
		}
		if (action.equals("New"))
		{
			System.out.println("Vao trang tao moi");
			IErpDuyetNhaCungCap listdnccBean = new ErpDuyetNhaCungCap();
			listdnccBean.Create_nhacungcap();
			listdnccBean.Create_Sanpham();
			session.setAttribute("obj", listdnccBean);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetNhaCungCapNew.jsp";
		}
		if (action.equals("Search"))
		{
			String search = getSearchQuery(request,listduyetncc);
			listduyetncc.Create_Nhacungcap();
			listduyetncc.Create_Sanpham();
			listduyetncc.Init(search);
			session.setAttribute("obj", listduyetncc);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetNhaCungCapList.jsp";
		}
		response.sendRedirect(nextJSP);
	}
	private String getSearchQuery(HttpServletRequest request, IErpDuyetNhaCungCapList listduyetncc) {
		String query;
		query=	"select dncc.PK_SEQ,ncc.TEN as tennhacc,dncc.TUNGAY,"
				+ "dncc.DENNGAY,dncc.TRANGTHAI from ERP_DUYETNHACUNGCAP dncc "
				+ "left join ERP_NHACUNGCAP ncc on ncc.pk_seq=dncc.NHACUNGCAP_fk "
				+ "left join ERP_SANPHAM sp on sp.PK_SEQ=dncc.SANPHAM_FK where 1=1";
	 		
		if (listduyetncc.getSanPham_Fk().length() > 0) {
			query += " AND  dncc.SANPHAM_FK LIKE '%" + listduyetncc.getSanPham_Fk()+ "%'";
		}
		if (listduyetncc.getNhacungcap_Fk().length() > 0) {
			query += " AND dncc.NHACUNGCAP_FK LIKE '%" + listduyetncc.getNhacungcap_Fk() + "%'";
		}
		return query;
	}
}
