package geso.traphaco.erp.servlets.khoasothang;
import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.khoasothang.IErpKhoasothang;
import geso.traphaco.erp.beans.khoasothang.imp.ErpKhoaSoThang;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKhoasothangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public ErpKhoasothangSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpKhoasothang obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();	    

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length()==0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		obj = new ErpKhoaSoThang();
		obj.setUserId(userId);
		obj.setCongtyId((String)session.getAttribute("congtyId"));
		obj.setNppId((String)session.getAttribute("nppId"));

		obj.Init();
		obj.createRs();
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhoaSoThang.jsp";
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpKhoasothang obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();	    

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length()==0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		obj = new ErpKhoaSoThang();
		obj.setUserId(userId);
		obj.setCongtyId((String)session.getAttribute("congtyId"));
		obj.setNppId((String)session.getAttribute("nppId"));

		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if(thang == null)
			thang = "";
		obj.setThang(thang);

		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if(nam == null)
			nam = "";
		obj.setNam(nam);

		String action = request.getParameter("action");
		if(action == null)
			action = "";

		if(action.equals("khoasokho"))
		{ 	 
			if( obj.KhoaSoThang() )
			{
				obj.setMsg("Khóa sổ tháng: " + thang + " năm " + nam + " thành công.");
			}
		}
		else if(action.equals("moKhoasokho"))
		{ 	 
			if( obj.MoKhoaSoThang() )
			{
				obj.setMsg("Mở khóa sổ tháng: " + thang + " năm " + nam + " thành công.");
			}
		}
		
		obj.createRs();
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhoaSoThang.jsp";
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
	
	}

}
