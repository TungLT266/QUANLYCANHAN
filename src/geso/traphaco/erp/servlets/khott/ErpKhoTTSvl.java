package geso.traphaco.erp.servlets.khott;

import geso.traphaco.erp.beans.kho.imp.Erp_KhoTT;
import geso.traphaco.erp.beans.kho.imp.Erp_KhoTTList;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import geso.traphaco.center.util.*;
public class ErpKhoTTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpKhoTTSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//response.setContentType("text/html;charset:UTF-8");
		HttpSession session=request.getSession();
		Utility util = new Utility();
		String ctyId = (String)session.getAttribute("congtyId");
		String task= util.antiSQLInspection(request.getParameter("task"));
		String action=request.getParameter("action");		
		
		Erp_KhoTTList kttL=new Erp_KhoTTList();
		kttL.setCongty_fk(ctyId);
		
		if(action==null){action="";}
		if(action.equals("delete"))
		{
			String khoid= util.antiSQLInspection(request.getParameter("khoId"));
			kttL.setPK_SEQ(khoid);
			kttL.Delete();
		}
		if(task==null)
			task="";
		if(task.equals("pic"))
		{
			response.sendRedirect("/TraphacoHYERP/pages/Erp/SoDoKho.jsp");
			return;
		}

		kttL.init();
		if(kttL.getMa()==null){kttL.setMa("");}
		if(kttL.getTen()==null){kttL.setTen("");}
		if(kttL.getTrangthai()==null){kttL.setTrangthai("");}
		session.setAttribute("kttL", kttL);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_KhoTT.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			//response.setContentType("text/html;charset:UTF-8");
			HttpSession session=request.getSession();
			String action=request.getParameter("action");
			String ctyId = (String)session.getAttribute("congtyId");
			
			if(action.equals("search"))
			{
				Erp_KhoTTList kttL = new Erp_KhoTTList();
				kttL.setCongty_fk(ctyId);
				
				kttL.setMa(request.getParameter("txtMakho"));
				kttL.setTen(request.getParameter("txtTenkho"));
				kttL.setTrangthai(request.getParameter("tt"));
				kttL.Search();
				kttL.CreateRS();
				if(kttL.getMa() == null){kttL.setMa("");}
				if(kttL.getTen() == null){kttL.setTen("");}
				if(kttL.getTrangthai() == null){kttL.setTrangthai("");}
				//System.out.println(kttL.getTrangthai());
				session.setAttribute("kttL", kttL);
				response.sendRedirect("../TraphacoHYERP/pages/Erp/Erp_KhoTT.jsp");
			}

			if(action.equals("new"))
			{
				Erp_KhoTT ktt = new Erp_KhoTT();
				ktt.setCongty_fk(ctyId);
				ktt.CreateRs();
				session.setAttribute("ktt", ktt);
				
				response.sendRedirect("../TraphacoHYERP/pages/Erp/Erp_KhoTTNew.jsp");
			}
			
			if(action.equals("reView"))
			{
				Erp_KhoTTList kttL=new Erp_KhoTTList();
				kttL.setCongty_fk(ctyId);
				kttL.init();
				if(kttL.getMa()==null){kttL.setMa("");}
				if(kttL.getTen()==null){kttL.setTen("");}
				if(kttL.getTrangthai()==null){kttL.setTrangthai("");}
				session.setAttribute("kttL", kttL);
				response.sendRedirect("../TraphacoHYERP/pages/Erp/Erp_KhoTT.jsp");
			}

	}
}

