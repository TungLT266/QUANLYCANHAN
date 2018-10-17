package geso.traphaco.erp.servlets.chiphikhac;

import geso.traphaco.erp.beans.chiphikhac.*;
import geso.traphaco.erp.beans.chiphikhac.imp.*;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpChiphikhacSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpChiphikhacSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IErpChiphikhacList obj = new ErpChiphikhacList();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    obj.setnppdangnhap(util.getIdNhapp(userId));
	    
	    String action = util.getAction(querystring);
	    String Id = util.getId(querystring);
	    
	    if(action.trim().equals("delete"))
	    {
	    	obj.Xoa(Id);
	    }
	    
	    if(action.trim().equals("chot"))
	    {
	    	obj.Chot(Id);
	    }
	    
	    obj.init("");
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiKhac.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    String ctyId = (String)session.getAttribute("congtyId");	    
	    
	    IErpChiphikhacList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpChiphikhac cpk = new ErpChiphikhac();
    		cpk.setCongtyId(ctyId);
    		cpk.setUserId(userId);
    		cpk.setnppdangnhap(util.getIdNhapp(userId));

	    	session.setAttribute("cpkBean", cpk);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpChiPhiKhacNew.jsp");
	    }
	    else
	    {
	    	obj = new ErpChiphikhacList();
	    	obj.setCongtyId(ctyId);
		    obj.setUserId(userId);
		    obj.setnppdangnhap(util.getIdNhapp(userId));

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpChiPhiKhac.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpChiphikhacList obj) 
	{
		Utility util = new Utility();
		
		String ma = request.getParameter("ma");
		if(ma == null)
			ma = "";
		obj.setMa(ma);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSoHoaDon(sohoadon);
		
		String diengiai = request.getParameter("diengiai");
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String ngaytao = request.getParameter("ngaytao");
		if(ngaytao == null)
			ngaytao = "";
		obj.setNgayTao(ngaytao);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
 
		String nguoitaoid = request.getParameter("nguoitaoid");
		if(nguoitaoid == null)
			nguoitaoid = "";
		obj.setNguoiTao(nguoitaoid);
		
		String  sql = 	"SELECT	 DISTINCT CPK.PK_SEQ AS CPID, CPK.NGAY, CPK.DIENGIAI, CPK.TRANGTHAI, " + 
						"CASE WHEN LOAI = 1 THEN ISNULL(NV.TEN, ' ') " +
						"ELSE ISNULL(NCC.TEN, ' ') END AS DOITUONG, " +
						"NV1.TEN AS NGUOITAO, NV2.TEN AS NGUOISUA " + 
						"FROM  ERP_CHIPHIKHAC CPK " +
						"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = CPK.NGUOITAO " + 
						"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = CPK.NGUOISUA " +
						"LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = CPK.DOITUONG " +
						"LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = CPK.DOITUONG " +
						"INNER JOIN ERP_HOADONCHIPHIKHAC HDCPK ON CPK.PK_SEQ=HDCPK.CHIPHIKHAC_FK "+
						"WHERE 1 = 1 AND CPK.CONGTY_FK = "+obj.getCongtyId()+" AND CPK.NPP_FK = "+obj.getnppdangnhap();
 

		if(ma.length() > 0)
			sql += " AND CPK.PK_SEQ LIKE N'%" + ma + "%' ";
		
		if(diengiai.length() > 0)
			sql += " AND dbo.ftBoDau(CPK.DIENGIAI) LIKE N'%" + util.replaceAEIOU(diengiai) + "%' ";
		
		if(trangthai.length() > 0)
			sql += " AND CPK.TRANGTHAI = '" + trangthai + "' ";

		if(ngaytao.length() > 0)
			sql += " AND CPK.NGAY LIKE N'%" + ngaytao + "%' ";
		
		if(nguoitaoid.length()>0)
			sql+=" AND CPK.NGUOITAO ='"+nguoitaoid+"' ";
		
		if(sohoadon.length()>0)
			sql += " AND HDCPK.SOHOADON LIKE '%"+sohoadon+"%' ";
		sql += 	"ORDER BY CPK.NGAY DESC ";
		
		System.out.println("search: "+ sql);
		
		return sql;
	}
	
	

}
