package geso.traphaco.erp.servlets.khauhaotaisan;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.khauhaotaisancodinh.IKhauhaotaisan;
import geso.traphaco.erp.beans.khauhaotaisancodinh.IKhauhaotaisanList;
import geso.traphaco.erp.beans.khauhaotaisancodinh.imp.Khauhaotaisan;
import geso.traphaco.erp.beans.khauhaotaisancodinh.imp.KhauhaotaisanList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.security.krb5.internal.tools.Ktab;

public class ErpKhauhaotaisanSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpKhauhaotaisanSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		String ctyId = (String)session.getAttribute("congtyId");
		String nppId=(String)session.getAttribute("nppId");
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String action = util.getAction(querystring);
		if(action == null) action = "";
		
		String Id = util.getId(querystring);
		String msg="";
		if(action.equals("display"))
		{
			IKhauhaotaisan khts = new Khauhaotaisan();
			khts.setCtyId(ctyId);
			khts.setId(Id);
			khts.setNppId(nppId);
			khts.createRs();
			session.setAttribute("khts", khts);
			response.sendRedirect("../TraphacoHYERP/pages/Erp/ErpKhauhaotaisancodinhDisplay.jsp");	
			return ;
		}
		else if(action.equals("delete"))
		{
			IKhauhaotaisan khts = new Khauhaotaisan();
			khts.setCtyId(ctyId);
			khts.setId(Id);
			khts.setNppId(nppId);
			msg = khts.Delete();
			khts.DBClose();
		}
		
		IKhauhaotaisanList obj = new KhauhaotaisanList();
		obj.setCtyId(ctyId);
		obj.setNppId(nppId);
		obj.setUserId(userId);
		obj.createRs();
		obj.setMsg(msg);
		session.setAttribute("obj", obj);
		
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhauhaotaisancodinh.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();

		IKhauhaotaisanList obj = new KhauhaotaisanList();
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		obj.setUserId(userId);
		
		String ctyId = (String)session.getAttribute("congtyId");
		obj.setCtyId(ctyId);
		
		String nppId = (String)session.getAttribute("nppId");
		obj.setNppId(nppId);

			
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if(nam == null) nam = "";
		obj.setNam(nam);
		
		String DVKDID = util.antiSQLInspection(request.getParameter("DVKDID"));
		if(DVKDID == null) DVKDID = "";
		obj.setDVKDID(DVKDID);
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if(thang == null) thang = "";
		obj.setThang(thang);
		

		String tentaisan = util.antiSQLInspection(request.getParameter("taisan"));
		if(tentaisan == null) tentaisan = "";
		obj.setTentaisan(tentaisan);
		
		String action = util.antiSQLInspection(request.getParameter("action"));
		obj.setAction(action);

		
//		String loai = util.antiSQLInspection(request.getParameter("loai"));
//		if(tentaisan == null) tentaisan = "";
		obj.setTentaisan(tentaisan);
		
		System.out.println("Action = " + action);
		
		if (action.equals("new")){
			IKhauhaotaisan khts = new Khauhaotaisan();
			khts.setCtyId(ctyId);
			khts.setNppId(nppId);
			khts.createRs();
			obj.DbClose();
			session.setAttribute("khts", khts);
			response.sendRedirect("../TraphacoHYERP/pages/Erp/ErpKhauhaotaisancodinhNew.jsp");		
		}else if(action.equals("save") || action.equals("submit") ){ 
			IKhauhaotaisan khts = new Khauhaotaisan();
			
			khts.setCtyId(ctyId);
			khts.setNppId(nppId);
			// Tạm thời bỏ qua HO
			khts.setnppdangnhap((String)session.getAttribute("nppId"));
			khts.setNam(nam);
			khts.setThang(thang);
			/// CHECK XEM THÁNG NÀY ĐÃ KHẤU HAO CHƯA, NẾU RỒI BẬT CỜ = TRUE;
	
			khts.setUserId(userId);
			String ntsId = util.antiSQLInspection(request.getParameter("ntsId"));
			if(ntsId == null) 
				ntsId = "";
			khts.setNtsId(ntsId);
			
			String dienGiaiCT = util.antiSQLInspection(request.getParameter("dienGiaiCT"));
			if(dienGiaiCT == null)
				dienGiaiCT = "";
			khts.setDienGiaiCT(dienGiaiCT);
			
			String soChungTu = util.antiSQLInspection(request.getParameter("soChungTu"));
			if(soChungTu == null)
				soChungTu = "";
			khts.setSoChungTu(soChungTu);
			
			
			String flag = util.antiSQLInspection(request.getParameter("flag"));
			if(flag == null||flag =="")
				flag = "0";
			khts.setFlag(flag);
			
			
			khts.createRs();
			if(khts.getFlag().equals("0")){
			khts.CheckKhauHao(nam,thang);
			}
			if(action.equals("save"))
			{
				if(khts.getFlag().equals("0") && khts.isKhauhao())
				{
					khts.setCtyId(ctyId);
					khts.setNppId(nppId);
					khts.setFlag("1");
					khts.setMsg("Tháng này đã có khấu hao, bạn có muốn khấu hao lại không?Nếu có, bấm lưu lại để chạy lại khấu hao!");
					session.setAttribute("khts", khts);
					response.sendRedirect("../TraphacoHYERP/pages/Erp/ErpKhauhaotaisancodinhNew.jsp");		
				}else
				{
					if(!khts.save(request)){
						khts.setCtyId(ctyId);
						khts.setNppId(nppId);
						session.setAttribute("khts", khts);
						response.sendRedirect("../TraphacoHYERP/pages/Erp/ErpKhauhaotaisancodinhNew.jsp");		
					}else{
						khts.setCtyId(ctyId);
						khts.setNppId(nppId);
						obj.createRs();
						khts.DBClose();
						session.setAttribute("obj", obj);
						
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhauhaotaisancodinh.jsp";
						response.sendRedirect(nextJSP);						
					}
					
				}
			}
			else
			{	khts.setCtyId(ctyId);
				khts.setNppId(nppId);
				session.setAttribute("khts", khts);
				response.sendRedirect("../TraphacoHYERP/pages/Erp/ErpKhauhaotaisancodinhNew.jsp");	
			}
		}else{
			
			obj.createRs();
			
			session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhauhaotaisancodinh.jsp";
			response.sendRedirect(nextJSP);						
		}
	}
}