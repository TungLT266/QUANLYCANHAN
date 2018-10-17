package geso.traphaco.erp.servlets.khoanmucchietkhau;

import geso.traphaco.erp.beans.khoanmucchietkhau.*;
import geso.traphaco.erp.beans.khoanmucchietkhau.imp.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import geso.traphaco.center.util.*;
public class Erp_KhoanmucchietkhauSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Erp_KhoanmucchietkhauSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//response.setContentType("text/html;charset:UTF-8");
		HttpSession session=request.getSession();
		Utility util = new Utility();
		String ctyId = (String)session.getAttribute("congtyId");
		String action=request.getParameter("action");
		
		
		IKhoanmucchietkhauList khoanMucCKList=new KhoanmucchietkhauList();
		khoanMucCKList.setCongty_fk(ctyId);
		
		if(action==null){action="";}
		if(action.equals("delete"))
		{	
			String mucCK= util.antiSQLInspection(request.getParameter("mucCK"));
			khoanMucCKList.setPK_SEQ(mucCK);
			khoanMucCKList.Delete();
		}

		khoanMucCKList.init();
		if(khoanMucCKList.getMa()==null){khoanMucCKList.setMa("");}
		if(khoanMucCKList.getTen()==null){khoanMucCKList.setTen("");}
		if(khoanMucCKList.getTrangthai()==null){khoanMucCKList.setTrangthai("");}
		session.setAttribute("kmckL", khoanMucCKList);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_Khoanmucchietkhau.jsp");
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
				KhoanmucchietkhauList kmckL = new KhoanmucchietkhauList();
				kmckL.setCongty_fk(ctyId);
				
				kmckL.setMa(request.getParameter("maCK"));
				kmckL.setTen(request.getParameter("tenCK"));
				kmckL.setTrangthai(request.getParameter("tt"));
				System.out.println("kmcL.getTrangThai "+ kmckL.getTrangthai());
				kmckL.init();
				if(kmckL.getMa() == null){kmckL.setMa("");}
				if(kmckL.getTen() == null){kmckL.setTen("");}
				if(kmckL.getTrangthai() == null){kmckL.setTrangthai("");}
				//System.out.println(kttL.getTrangthai());
				session.setAttribute("kmckL", kmckL);
				response.sendRedirect("../TraphacoHYERP/pages/Erp/Erp_Khoanmucchietkhau.jsp");
			}

			if(action.equals("new"))
			{
				Khoanmucchietkhau kmck = new Khoanmucchietkhau();
				kmck.setCongty_fk(ctyId);
				kmck.CreateRs();
				session.setAttribute("kmck", kmck);
				response.sendRedirect("../TraphacoHYERP/pages/Erp/Erp_KhoanmucchietkhauNew.jsp");
			}

	}
}

