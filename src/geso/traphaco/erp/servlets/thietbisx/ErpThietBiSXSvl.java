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
 * Servlet implementation class ErpThietBiSXSvl
 */
@WebServlet("/ErpThietBiSXSvl")
public class ErpThietBiSXSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpThietBiSXSvl() {
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
	    this.out  = response.getWriter();
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.trim().length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IErpThietBiSX obj = new ErpThietBiSX();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    String id = util.getId(querystring);
	    
	    if(action.trim().equals("delete"))
	    	obj.delete(id);
	    
	    obj.init("");
	    obj.createRs();
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThietbiSX.jsp";
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
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    String ctyId = (String)session.getAttribute("congtyId");
	    
		String action = request.getParameter("action");
	    if (action == null)
	    	action = "";
	    
	    if(action.equals("new")){
	    	IErpThietBiSXThongSo obj = new ErpThietBiSXThongSo();
	    	obj.setCongtyId(ctyId);
	    	obj.setUserId(userId);
	    	obj.creaters();
	    	
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThietbiSXNew.jsp");
	    } else {
	    	IErpThietBiSX obj = new ErpThietBiSX();
	    	obj.setCongtyId(ctyId);
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
	    	obj.createRs();
				
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThietbiSX.jsp");	
	    }
	}

	private String getSearchQuery(HttpServletRequest request, IErpThietBiSX obj) {
		Utility util = new Utility();
		
		String matb = request.getParameter("matb");
		if(matb == null)
			matb = "";
		obj.setMa(matb);
		
		String tentb = request.getParameter("tentb");
		if(tentb == null)
			tentb = "";
		obj.setTen(tentb);
		
		String tscd = request.getParameter("taisan");
		if(tscd == null)
			tscd = "";
		obj.setTscdFk(tscd);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sql = "select tb.pk_seq, tb.ma, tb.ten, tb.trangthai, nvt.TEN as nguoitao, nvs.TEN as nguoisua, tb.ngaytao, tb.ngaysua,"
				+ " case when tb.loai_TSCD=1 and len(tb.tscd_fk)>0 then (tscd.ma+' - '+tscd.diengiai)"
				+ " when tb.loai_TSCD=0 and len(tb.tscd_fk)>0 then (ccdc.ma+' - '+ccdc.diengiai) else '' end as tscd"
				+ " from erp_thietbisx as tb"
				+ " left join ERP_TAISANCODINH tscd on tscd.pk_seq = tb.tscd_fk and tscd.trangthai = 1"
				+ " left join ERP_CONGCUDUNGCU ccdc on ccdc.pk_seq = tb.tscd_fk and tb.loai_TSCD = 0"
				+ " inner join NHANVIEN nvt on nvt.PK_SEQ = tb.nguoitao"
				+ " inner join NHANVIEN nvs on nvs.PK_SEQ = tb.nguoisua"
				+ " where tb.congty_fk = " + obj.getCongtyId();
		
		if(matb.length() > 0)
			sql += " and tb.ma like N'%" + matb + "%' ";
		
		if(tentb.length() > 0)
			sql += " and dbo.ftBoDau(tb.ten) like N'%" + util.replaceAEIOU(tentb) + "%' ";
		
		if(tscd.length() > 0){
			String loaiTSCD = "";
			if (tscd.startsWith("TSCD")){
				loaiTSCD = "1";
				tscd = tscd.replace("TSCD", "");
			} else {
				loaiTSCD = "0";
				tscd = tscd.replace("CPTT", "");
			}
			
			sql += " and tb.tscd_fk = '" + tscd + "' and tb.loai_TSCD = '"+loaiTSCD+"'";
		}
		
		if(trangthai.length() > 0)
			sql += " and tb.trangthai = '" + trangthai + "'";
		
		sql += " order by tb.pk_seq desc";
		
		return sql;
	}
}
