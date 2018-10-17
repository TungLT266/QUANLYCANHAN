package geso.erp.servlets.yeucau;

import geso.erp.beans.erp_tempyeucausx.imp.Yeucau;
import geso.erp.beans.erp_tempyeucausx.imp.YeucauList;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class YeuCauUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public YeuCauUpdateSvl() {
        super();
    }
    String URL="";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session=request.getSession();
		Yeucau yc =new Yeucau();
		String action = request.getParameter("action");
		
		String taoyeucau_fk = request.getParameter("taoyeucau_fk");
		if(taoyeucau_fk==null) taoyeucau_fk="";
		yc.setTAOYEUCAU_FK(taoyeucau_fk);
		
		String dubao_fk = request.getParameter("dubao_fk");
		if(dubao_fk==null) dubao_fk="";
		yc.setDUBAO_FK(dubao_fk);
		
		String pk_seq = request.getParameter("yeucau_fk");
		if(pk_seq==null) pk_seq="";
		yc.setPK_SEQ(pk_seq);
		
		if(action.equals("reView"))
		{
			YeucauList ycl=new YeucauList();
			ycl.CreateRSDUBAO();
			ycl.init();
			session.setAttribute("ycl", ycl);
			URL="../TraphacoHYERP/pages/Erp/erp_yeucau.jsp";
		}
		
		if(action.equals("xem"))
		{
			yc.CreateRsNam();
			yc.CreateRsSanphamTheoDubaoThang();
			session.setAttribute("yc", yc);
			URL="../TraphacoHYERP/pages/Erp/erp_yeucauchitiet.jsp";
			
		}
		
		response.sendRedirect(URL);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset:UTF-8");
		HttpSession session=request.getSession();
		String action = request.getParameter("action");
		Yeucau yc =new Yeucau();
		String taoyeucau_fk = request.getParameter("taoyeucau_fk");
		if(taoyeucau_fk==null) taoyeucau_fk="";
		yc.setTAOYEUCAU_FK(taoyeucau_fk);
		
		String dubao_fk = request.getParameter("dubao_fk");
		if(dubao_fk==null) dubao_fk="";
		yc.setDUBAO_FK(dubao_fk);
		
		String pk_seq = request.getParameter("yeucau_fk");
		if(pk_seq==null) pk_seq="";
		yc.setPK_SEQ(pk_seq);
		
		String tuanthang = request.getParameter("theothang");
		if(tuanthang==null)tuanthang="";
		yc.setTUANTHANG(tuanthang);
		
		String timnam = request.getParameter("namYC");
		if(timnam==null)timnam="";
		yc.setTIMNAM(timnam);
		
		if(action.equals("timnam"))
		{	yc.CreateRsNam();
			yc.CreateRsSanphamTheoDubaoThang();
			session.setAttribute("yc", yc);
			URL="../TraphacoHYERP/pages/Erp/erp_yeucauchitiet.jsp";
		}
		if(action.equals("tuanthang"))
		{	yc.CreateRsNam();
			yc.CreateRsSanphamTheoDubaoThang();
			session.setAttribute("yc", yc);
			URL="../TraphacoHYERP/pages/Erp/erp_yeucauchitiet.jsp";
		}
		response.sendRedirect(URL);
	}

}
