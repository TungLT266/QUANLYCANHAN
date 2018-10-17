package geso.traphaco.erp.servlets.yeucau;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.erp_yeucausx.imp.YeuCauSXList;
import geso.traphaco.erp.beans.erp_yeucausx.IYeuCauSXList;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class YeuCauSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public YeuCauSvl() {
        super();
    }
    String URL;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		IYeuCauSXList yc = (IYeuCauSXList) new YeuCauSXList();
		yc.setCtyId(ctyId);
		
		yc.createRs();
		session.setAttribute("yc", yc);
		URL="../TraphacoHYERP/pages/Erp/ERP_Yeucaucungung.jsp";
		response.sendRedirect(URL);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session=request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		Utility util = new Utility();
		
		IYeuCauSXList yc = (IYeuCauSXList) new YeuCauSXList();
		yc.setCtyId(ctyId);
		
		String khoId = util.antiSQLInspection(request.getParameter("khoId"));
		yc.setKhoId(khoId);
		
		String nam =  util.antiSQLInspection(request.getParameter("nam"));
		yc.setNam(nam);
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if(thang == null) thang = "";
		yc.setThang(thang);
		
		String clId = util.antiSQLInspection(request.getParameter("chungloai"));
		if(clId == null) clId = "";
		yc.setClId(clId);
		
		String LOAI = util.antiSQLInspection(request.getParameter("loai"));
		if(LOAI == null) LOAI = "";
		yc.setLoai(LOAI);

		yc.createRs();

		session.setAttribute("yc", yc);
		URL="../TraphacoHYERP/pages/Erp/ERP_Yeucaucungung.jsp";
		response.sendRedirect(URL);
		
	}

}
