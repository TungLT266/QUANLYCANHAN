package geso.traphaco.erp.servlets.thietbisx;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.thietbisx.IErpThietBiSX;
import geso.traphaco.erp.beans.thietbisx.IErpThietBiSXThongSo;
import geso.traphaco.erp.beans.thietbisx.imp.ErpThietBiSX;
import geso.traphaco.erp.beans.thietbisx.imp.ErpThietBiSXThongSo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ErpThietBiSXUpdateSvl
 */
@WebServlet("/ErpThietBiSXUpdateSvl")
public class ErpThietBiSXUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpThietBiSXUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpThietBiSXThongSo obj;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);
	    
	    obj = new ErpThietBiSXThongSo();
	    obj.setId(id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setCongtyId(ctyId);
	    obj.setUserId(userId);
	    
	    obj.init();
	    obj.creaters();
        session.setAttribute("obj", obj);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThietbiSXNew.jsp";
        if(querystring.indexOf("display") >= 0) {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpThietbiSXDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpThietBiSXThongSo obj = new ErpThietBiSXThongSo();
		
		Utility util = new Utility();
		String id = util.antiSQLInspection(request.getParameter("id"));
		if(id == null)
			id = "";
		if(id.trim().length() > 0)
			obj.setId(id);
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
		obj.setUserId(userId);
		
		String ctyId = (String)session.getAttribute("congtyId");
		obj.setCongtyId(ctyId);
		
		String ischangema = util.antiSQLInspection(request.getParameter("ischangema"));
		if (ischangema == null)
			ischangema = "";
		obj.setIsChangeMa(ischangema);
		
		String ma = util.antiSQLInspection(request.getParameter("matb"));
		if (ma == null)
			ma = "";
		obj.setMa(ma);
		
		String ten = util.antiSQLInspection(request.getParameter("tentb"));
		if (ten == null)
			ten = "";
		obj.setTen(ten);
		
		String tscd = util.antiSQLInspection(request.getParameter("taisan"));
		if (tscd == null)
			tscd = "";
		obj.setTscdFk(tscd);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "0";
		obj.setTrangThai(trangthai);
		
		String[] dienGiai = request.getParameterValues("thongsokythuat");
		obj.setDienGiai(dienGiai);
		
		String[] TsycTu = request.getParameterValues("tsyctu");
		obj.setTsycTu(TsycTu);
		
		String[] TsycDen = request.getParameterValues("tsycden");
		obj.setTsycDen(TsycDen);
		
		String[] dvdlFk = request.getParameterValues("donvitinh");
		obj.setDvdlFk(dvdlFk);
		
		String[] check = new String[dienGiai.length];
		for(int i=0; i<dienGiai.length; i++){
			check[i] = util.antiSQLInspection(request.getParameter("check"+i));
			if (check[i] == null)
				check[i] = "0";
		}
		obj.setCheck(check);
		
		String action = request.getParameter("action");
		if(action.equals("save")){
			if(id == null || id.trim().equals("")){
				if (!(obj.create())) {
					obj.creaters();
					
	 				session.setAttribute("obj", obj);  	
	 	    		session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThietbiSXNew.jsp";
					response.sendRedirect(nextJSP);
				} else {
					IErpThietBiSX obj2 = new ErpThietBiSX();
					obj2.setCongtyId(ctyId);
					obj2.setUserId(userId);
					obj2.init("");
					obj2.createRs();
					
					session.setAttribute("obj", obj2);
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThietbiSX.jsp";
					response.sendRedirect(nextJSP);
				}
			} else {
				if (!(obj.update())) {
					obj.creaters();
					
	 				session.setAttribute("obj", obj);  	
	 	    		session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThietbiSXNew.jsp";
					response.sendRedirect(nextJSP);
				} else {
					IErpThietBiSX obj2 = new ErpThietBiSX();
					obj2.setCongtyId(ctyId);
					obj2.setUserId(userId);
					obj2.init("");
					obj2.createRs();
					
					session.setAttribute("obj", obj2);
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThietbiSX.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
	}

}
