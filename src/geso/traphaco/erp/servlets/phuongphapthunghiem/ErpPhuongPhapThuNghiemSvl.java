package geso.traphaco.erp.servlets.phuongphapthunghiem;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.phuongphapthunghiem.IPhuongPhapThuNghiem;
import geso.traphaco.erp.beans.phuongphapthunghiem.IPhuongPhapThuNghiemList;
import geso.traphaco.erp.beans.phuongphapthunghiem.imp.PhuongPhapThuNghiem;
import geso.traphaco.erp.beans.phuongphapthunghiem.imp.PhuongPhapThuNghiemList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ErpPhuongPhapThuNghiemSvl
 */
@WebServlet("/ErpPhuongPhapThuNghiemSvl")
public class ErpPhuongPhapThuNghiemSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpPhuongPhapThuNghiemSvl() {
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
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session=request.getSession();
		Utility utility=new Utility();
		String querystring=request.getQueryString();
		String userId=utility.getUserId(querystring);
		
		IPhuongPhapThuNghiemList obj=new PhuongPhapThuNghiemList();
		obj.setUserId(userId);
		
		String action=utility.getAction(querystring);
		if(action==null)action="";
		
		String pk_seq=utility.getId(querystring);
		if(pk_seq==null)pk_seq="";
		
		if(action.equals("delete")){
			IPhuongPhapThuNghiem pptn=new PhuongPhapThuNghiem();
			pptn.setPK_SEQ(pk_seq);
			
			if(!pptn.delete()){
				obj.init();
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhuongPhapThuNghiemList.jsp";
				response.sendRedirect(nextJSP);
			}
			else{
				obj.init();
				obj.setUserId(userId);
				obj.creates();
				session.setAttribute("obj", obj);
				String nextJSP="/TraphacoHYERP/pages/Erp/ErpPhuongPhapThuNghiemList.jsp";
				response.sendRedirect(nextJSP);
			}
			
			
		}
		else if(action.equals("display")){
			IPhuongPhapThuNghiem obj1=new PhuongPhapThuNghiem();
			obj1.setUserId(userId);
			obj1.setPK_SEQ(pk_seq);
			obj1.creates();
			obj1.init();
			session.setAttribute("obj", obj1);
			String nextJSP1="/TraphacoHYERP/pages/Erp/ErpPhuongPhapThuNgiemDisplay.jsp";
			response.sendRedirect(nextJSP1);
		}
		else {
			obj.init();
			obj.setUserId(userId);
			obj.creates();
			session.setAttribute("obj", obj);
			String nextJSP="/TraphacoHYERP/pages/Erp/ErpPhuongPhapThuNghiemList.jsp";
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
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session=request.getSession();
		
		String action=(String)request.getParameter("action");
		String userId=(String)request.getParameter("userId");
		String userName=(String)request.getParameter("userTen");
		if(action==null)action="";
		
		if(action.equals("luumoi"))
		{
			IPhuongPhapThuNghiem obj=new PhuongPhapThuNghiem();
			obj.setUserId(userId);
			obj.creates();
			session.setAttribute("obj", obj);
			String nextJSP="/TraphacoHYERP/pages/Erp/ErpPhuongPhapThuNgiemNew.jsp";
			response.sendRedirect(nextJSP);
		}
		
		else
		{
			String ma=request.getParameter("ma");
			String ten=request.getParameter("tenviettat");
			String maloai=request.getParameter("loaitieuchi");
			String mayeucaukythuat=request.getParameter("yeucaukythuat");
			String trangthai=request.getParameter("trangthai");
			
			IPhuongPhapThuNghiemList obj=new PhuongPhapThuNghiemList();
			obj.creates();
			obj.setUserId(userId);
			obj.setUserName(userName);
			obj.setMaPPTN(ma);
			obj.setTenVT(ten);
			obj.setTrangThai(trangthai);
			obj.setYeuCauKyThuat(mayeucaukythuat);
			obj.setLoaiTieuChi(maloai);
			obj.init();

			session.setAttribute("obj", obj);
			String nextJSP="/TraphacoHYERP/pages/Erp/ErpPhuongPhapThuNghiemList.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}

}
