package geso.traphaco.erp.servlets.cantrucongno;


import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.cantrucongno.IErpCanTruCongNoList;
import geso.traphaco.erp.beans.cantrucongno.imp.ErpCanTruCongNo;
import geso.traphaco.erp.beans.cantrucongno.imp.ErpCanTruCongNoList;
//import geso.traphaco.erp.beans.tamung.IErpTamUng;
//import geso.traphaco.erp.beans.tamung.IErpTamUngList;
//import geso.traphaco.erp.beans.tamung.imp.ErpTamUng;
//import geso.traphaco.erp.beans.tamung.imp.ErpTamUngList;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpCanTruCongNoSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpCanTruCongNoSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String userId;
		
		String ServerletName = this.getServletName();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		out.println(action);

		userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));		
		
		IErpCanTruCongNoList canTruCongNoList = new ErpCanTruCongNoList();
		canTruCongNoList.setCongtyId((String)session.getAttribute("congtyId"));
		canTruCongNoList.setnppdangnhap((String)session.getAttribute("nppId"));

		canTruCongNoList.setUserId(userId);
		
		
		
		
		
		String nextJSP = "";
		String msg = "";
		
		if("chot".equals(action))
		{			
			String id = request.getParameter("selectedItem");
			ErpCanTruCongNo bean = new ErpCanTruCongNo(id);		
			bean.setUserId(userId);
			bean.setcongtyId((String)session.getAttribute("congtyId"));					
			
			if(!bean.chot())
			{
				msg = bean.getMsg();
				canTruCongNoList.setMsg(msg);				  	
			}
			

			System.out.println("ancga:"+msg);
			canTruCongNoList.init();
			session.setAttribute("obj", canTruCongNoList);
			
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanTruCongNo.jsp";
			response.sendRedirect(nextJSP);
			
		}
		else{
		canTruCongNoList.init();
		util.setSearchToHM(userId, session, ServerletName, "");

		session.setAttribute("obj", canTruCongNoList);
		
		nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanTruCongNo.jsp";
		response.sendRedirect(nextJSP);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();
		String nextJSP = "index.jsp";
		HttpSession session = request.getSession();
		// khai báo ServerletName
		String ServerletName = this.getServletName();
		
		
		String querystring = request.getQueryString();
		String userId = (String)request.getSession().getAttribute("userId");
		String action = request.getParameter("action");
		if("new".equals(action))
		{
			ErpCanTruCongNo obj = new ErpCanTruCongNo();
			obj.setUserId(userId);
			obj.setcongtyId((String)session.getAttribute("congtyId"));
			obj.setnppdangnhap((String)session.getAttribute("nppId"));
			obj.setnppdangnhap(util.getIdNhapp(userId));
	        
			request.getSession().setAttribute("obj", obj);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanTruCongNoNew.jsp";
			System.out.println("NEW CAN TRU");
						
		}else if("search".equals(action))
		{

			ErpCanTruCongNoList obj = (ErpCanTruCongNoList) request.getSession().getAttribute("obj");
			if(obj==null)
			{
				obj = new ErpCanTruCongNoList();
			}
			
			String kh = request.getParameter("kh");
			kh = kh!=null?kh:"";
			
			String khId = "";
			if (kh.contains(" - ")) {
				khId = kh.substring(0, kh.indexOf(" - "));
			}
			String ncc = request.getParameter("ncc");
			ncc = ncc!=null?ncc:"";
			String nccId = "" ;
			if (ncc.contains(" - ")) {
				nccId = ncc.substring(0, ncc.indexOf(" - "));
			}
			String trangthai = request.getParameter("trangthai");
			trangthai = trangthai!=null?trangthai:"";
			String id = request.getParameter("ctId");
			
			obj.setId(id);
			obj.setKh(kh);
			obj.setKhId(khId);
			obj.setNcc(ncc);
			obj.setNccId(nccId);
			obj.setTrangthai(trangthai);	
			obj.setUserId(userId);
			obj.setCongtyId((String)session.getAttribute("congtyId"));
			obj.setnppdangnhap((String)session.getAttribute("nppId"));
			String querySearch = GiuDieuKienLoc.createParams(obj);
			util.setSearchToHM(userId, session,ServerletName, querySearch);
			// Set câu query
		
			obj.init();
			request.getSession().setAttribute("obj",obj);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanTruCongNo.jsp";
			
		}else if("update".equals(action))
		{
			String id = request.getParameter("selectedItem");
			ErpCanTruCongNo bean = new ErpCanTruCongNo(id);
			bean.setUserId(userId);
			bean.setcongtyId((String)session.getAttribute("congtyId"));
			bean.setnppdangnhap((String)session.getAttribute("nppId"));
		
			
			
			bean.init();
			request.getSession().setAttribute("obj", bean);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanTruCongNoNew.jsp";
			
		}else if("delete".equals(action))
		{
//			System.out.println("DELETE CAN TRU");
//			String id = request.getParameter("selectedItem");
//			ErpCanTruCongNo bean = new ErpCanTruCongNo(id);
//			bean.setUserId(userId);
//			bean.setcongtyId((String)session.getAttribute("congtyId"));
//			//// CÒN GET QUERY Ở CÁC CHỖ DOGET:svl
//			// Lưu ý: Trước INIT
//			
//			String querySearch = GiuDieuKienLoc.createParams(bean);
//			util.setSearchToHM(userId, session,ServerletName, querySearch);
//			
//			bean.init();
//			bean.delete();
//			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanTruCongNo.jsp";
			
			
			IErpCanTruCongNoList canTruCongNoList = new ErpCanTruCongNoList();
			canTruCongNoList.setCongtyId((String)session.getAttribute("congtyId"));
			canTruCongNoList.setnppdangnhap((String)session.getAttribute("nppId"));

			canTruCongNoList.setUserId(userId);
			
			String id = request.getParameter("selectedItem");
			ErpCanTruCongNo bean = new ErpCanTruCongNo(id);		
			bean.setUserId(userId);
			bean.setcongtyId((String)session.getAttribute("congtyId"));
			bean.setnppdangnhap((String)session.getAttribute("nppId"));
			

		
		
			
			
			
			bean.init();
		
			
			String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(canTruCongNoList, searchQuery);
			
			String msg=bean.delete();
			canTruCongNoList.setMsg(msg);
				
	
			canTruCongNoList.init();
			session.setAttribute("obj", canTruCongNoList);
			
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanTruCongNo.jsp";
//			response.sendRedirect(nextJSP);	
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}else if("chot".equals(action))
		{
			IErpCanTruCongNoList canTruCongNoList = new ErpCanTruCongNoList();
			canTruCongNoList.setCongtyId((String)session.getAttribute("congtyId"));
			canTruCongNoList.setnppdangnhap((String)session.getAttribute("nppId"));

			canTruCongNoList.setUserId(userId);
			String id = request.getParameter("selectedItem");
			ErpCanTruCongNo bean = new ErpCanTruCongNo(id);		
			bean.setUserId(userId);
			bean.setcongtyId((String)session.getAttribute("congtyId"));
			bean.setnppdangnhap((String)session.getAttribute("nppId"));
			//// CÒN GET QUERY Ở CÁC CHỖ DOGET:svl
			// Lưu ý: Trước INIT
			String querySearch = GiuDieuKienLoc.createParams(bean);
			util.setSearchToHM(userId, session,ServerletName, querySearch);
			
			
			
			bean.init();
			if(!bean.chot())
			{
				canTruCongNoList.setMsg(bean.getMsg());				
			}
			
			canTruCongNoList.init();
	
			request.getSession().setAttribute("obj",canTruCongNoList);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanTruCongNo.jsp";			
		}
			
		response.sendRedirect(nextJSP);
	}

}
