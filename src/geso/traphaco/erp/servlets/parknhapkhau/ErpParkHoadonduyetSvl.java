package geso.traphaco.erp.servlets.park;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.park.IErpPark;
import geso.traphaco.erp.beans.park.IErpParkList;
import geso.traphaco.erp.beans.park.imp.ErpPark;
import geso.traphaco.erp.beans.park.imp.ErpParkList;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpParkHoadonduyetSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ErpParkHoadonduyetSvl() {
        super();
    }

    int tranghientai=1;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpParkList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();	    
	    Utility util = new Utility();	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    
	    
	    String action = util.getAction(querystring);
	   
	    obj = new ErpParkList();
	    
	    
	    if(action.equals("chot"))
    	{
	    	String id = util.getId(querystring);  	
			IErpPark pBean = new ErpPark(id);
			pBean.setCtyId((String)session.getAttribute("congtyId"));
			pBean.setUserId(userId);
			pBean.setnppdangnhap(util.getIdNhapp(userId));
    	
			// KIỂM TRA SỐ HÓA ĐƠN
			String msg = obj.checkSoHoaDon(id);			
			
			if(msg.trim().length()>0)
				obj.setMsg(msg);
			else
			{			
	    		if(!pBean.Duyet())
	    		{
	    			obj.setMsg(pBean.getMsg());
	    		}
			}
    		obj.setNxtApprSplitting(tranghientai);
    	}
	    
	    obj.setUserId(userId);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setnppdangnhap(util.getIdNhapp(userId));
	    
		String search = this.getSearchQuery(request, obj);	  
	    
		obj.setDuyet(1);
	    obj.init(search);	    
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpParkList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpPark pBean = new ErpPark();
	    	pBean.createRs();
	    	session.setAttribute("pBean", pBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyetNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpParkList();
		    	
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		     	tranghientai = Integer.parseInt(request.getParameter("nxtApprSplitting"));
		     	
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
			    obj.setnppdangnhap(util.getIdNhapp(userId));
			    
			    String search = getSearchQuery(request, obj);
			    
		    	obj.init(search);
		    	obj.setDuyet(1);		    	
		    	session.setAttribute("obj", obj);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp");	
		    }
	    	else if(action.equals("chot"))
	    	{
	    		String id = request.getParameter("parkid");
	        	obj = new ErpParkList();
	        	
				IErpPark pBean = new ErpPark(id);
	    	
	    		if(!pBean.Duyet())
	    		{
	    			obj.setMsg(pBean.getMsg());
	    		}
	    		obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
			    obj.setnppdangnhap(util.getIdNhapp(userId));
			    
	    		String search = getSearchQuery(request, obj);		    	
	    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		     	tranghientai = Integer.parseInt(request.getParameter("nxtApprSplitting"));
		    	
			    
		    	obj.init(search);
		    	obj.setDuyet(1);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp");	
	    	}
	    	else
	    	{
		    	obj = new ErpParkList();

				obj.setUserId(userId);
				obj.setCongtyId((String)session.getAttribute("congtyId"));
			    obj.setnppdangnhap(util.getIdNhapp(userId));
			    
		    	String search = getSearchQuery(request, obj);
		    	obj.setDuyet(1);
		    	obj.init(search);
			    
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp");
	    	}
	    }
	}
	private String getSearchQuery(HttpServletRequest request, IErpParkList obj)
	{
		String query =   
				" SELECT A.PK_SEQ , A.NGAYGHINHAN, NCC.TEN AS TENNHACUNGCAP, A.TRANGTHAI ,     	NV.TEN AS TENNV,A.NGAYSUA,A.NGAYTAO,NV.PK_SEQ AS MANV,NV2.TEN AS " +  
				"  		TENNVS,NV2.PK_SEQ AS MANVS, A.PREFIX     	, HOANTAT.SODEM  AS DAHOANTAT , CASE WHEN THANHTOAN.PARK_FK IS NULL THEN 0 ELSE 1 END AS DACOTHANHTOAN, ISNULL(HDNCC.SOHOADON,'') AS SOHOADON	 " +  
				" FROM ERP_PARK A   	INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = A.NCC_FK   	INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   " +  
				"  INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ	     " +  
				"  LEFT JOIN 	(   " +  
				"   			SELECT A.PARK_FK , (DEMA - ISNULL (DEMB,0)) AS SODEM " +
				"				FROM 	(SELECT PARK_FK , COUNT(PARK_FK) AS DEMA  	" +
				"						FROM ERP_HOADONNCC   " + 
				"						WHERE CONGTY_FK = "+obj.getCongtyId()+" AND NPP_FK = "+obj.getnppdangnhap()+
				"  			 			GROUP BY PARK_FK) AS A  	" +
				"  	LEFT JOIN   	(SELECT PARK_FK , COUNT(PARK_FK) AS DEMB  	" +
				"					FROM ERP_HOADONNCC  	 " +  
				"  	 				WHERE TRANGTHAI = 2 AND CONGTY_FK =" +obj.getCongtyId()+ " AND NPP_FK = "+obj.getnppdangnhap()+
				"					GROUP BY PARK_FK) AS B ON B.PARK_FK = A.PARK_FK  " +  
				"  ) HOANTAT ON HOANTAT.PARK_FK = A.PK_SEQ   	 " +  
				"  LEFT JOIN 		(  " +  
				"   SELECT DISTINCT HD.PARK_FK 		FROM ERP_THANHTOANHOADON_HOADON TT		 " +  
				"   INNER JOIN ERP_HOADONNCC HD ON HD.PK_SEQ = TT.HOADON_FK  " +  
				"	WHERE HD.CONGTY_FK = "+obj.getCongtyId()+" AND HD.NPP_FK = "+obj.getnppdangnhap()+
				"  ) THANHTOAN ON THANHTOAN.PARK_FK = A.PK_SEQ " +
				" INNER JOIN ERP_HOADONNCC HDNCC ON A.PK_SEQ=HDNCC.PARK_FK "+
				" where a.pk_seq > 0  and a.trangthai >= 1 AND HDNCC.CONGTY_FK ="+obj.getCongtyId()+" AND HDNCC.NPP_FK = "+obj.getnppdangnhap()+ " ";
		
		String ngayghinhan = request.getParameter("ngayghinhan");
		if(ngayghinhan == null)
			ngayghinhan = "";
		obj.setNgayghinhan(ngayghinhan);
		
		String nhacungcap = request.getParameter("nhacungcap");
		if(nhacungcap == null)
			nhacungcap = "";
		obj.setNcc(nhacungcap);
		
		String loaihang = request.getParameter("loaihang");
		if(loaihang == null)
			loaihang = "";
		obj.setLoaihang(loaihang);
		
		String sopark = request.getParameter("sopark");
		if(sopark == null)
			sopark = "";
		obj.setId(sopark);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null) sohoadon = ""; else sohoadon = sohoadon.trim();
		obj.setSOHOADON(sohoadon);
		
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null) 
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		if(ngayghinhan.length() > 0)
			query += " and a.ngayghinhan = '" + ngayghinhan + "'";
		
		if(nhacungcap.length() > 0)
			query += " and a.ncc_fk = '" + nhacungcap + "'";
		
		if(loaihang.length() > 0)
			query += " and a.loaihanghoa_fk = '" + loaihang + "'";
		if(sopark.length() > 0)
			query += " and a.pk_seq  like '%" + sopark + "%'";
		if(sohoadon.length() > 0) {
			query += " and a.pk_seq in (select park_fk from ERP_HOADONNCC where sohoadon like N'%" + sohoadon + "%') ";
		}
		if(trangthai.length()>0)
		{
			query+= " and a.trangthai = "+trangthai;
		}

		return query;
	}
	
	
}
