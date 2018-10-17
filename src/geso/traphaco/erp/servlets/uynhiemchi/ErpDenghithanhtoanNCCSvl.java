package geso.traphaco.erp.servlets.uynhiemchi;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.uynhiemchi.IErpDenghithanhtoanNCC;
import geso.traphaco.erp.beans.uynhiemchi.IErpDenghithanhtoanNCCList;
import geso.traphaco.erp.beans.uynhiemchi.IErpUynhiemchi;
import geso.traphaco.erp.beans.uynhiemchi.IErpUynhiemchiList;
import geso.traphaco.erp.beans.uynhiemchi.imp.ErpDenghithanhtoanNCC;
import geso.traphaco.erp.beans.uynhiemchi.imp.ErpDenghithanhtoanNCCList;
import geso.traphaco.erp.beans.uynhiemchi.imp.ErpUynhiemchi;
import geso.traphaco.erp.beans.uynhiemchi.imp.ErpUynhiemchiList;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDenghithanhtoanNCCSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDenghithanhtoanNCCSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ErpDenghithanhtoanNCCList obj;
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
	    
	    String tthdId = util.getId(querystring);
	    
	    obj = new ErpDenghithanhtoanNCCList();
	    
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setUserId(userId);
	    obj.setnppdangnhap(util.getIdNhapp(userId));
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(tthdId, userId);
	    	if(msg.length() > 0)
	    		obj.setmsg(msg);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		ErpDenghithanhtoanNCC tthd = new ErpDenghithanhtoanNCC(tthdId);
	    		tthd.setCtyId((String)session.getAttribute("congtyId"));
	    		tthd.setUserId(userId);
	    		tthd.setnppdangnhap(util.getIdNhapp(userId));
	    		tthd.init();
	    		if(!tthd.chotTTHD(userId))
	    		{
	    			obj.setmsg(tthd.getMsg());
	    		}
	    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setnppdangnhap(util.getIdNhapp(userId));
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCC.jsp";
		response.sendRedirect(nextJSP);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDenghithanhtoanNCCList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpDenghithanhtoanNCC tthdBean = new ErpDenghithanhtoanNCC();
	    	tthdBean.setCtyId(ctyId);
	    	tthdBean.setnppdangnhap(util.getIdNhapp(userId));
	 	    
	    	tthdBean.createRs();
    		
	    	session.setAttribute("doituongkhac", "");
	    	session.setAttribute("doituong", "");
	    	session.setAttribute("loaithanhtoan", "");
	    	session.setAttribute("nhomncccn", "");
	    	
	    	session.setAttribute("tthdBean", tthdBean);

    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCCNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpDenghithanhtoanNCCList();
	    		obj.setUserId(userId);
	    		obj.setCongtyId(ctyId);
	    		obj.setnppdangnhap(util.getIdNhapp(userId));
		    	
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCC.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpDenghithanhtoanNCCList();
		    	
		    	obj.setUserId(userId);
		    	obj.setCongtyId(ctyId);
	    		obj.setnppdangnhap(util.getIdNhapp(userId));

		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDeNghiThanhToanNCC.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDenghithanhtoanNCCList obj)
	{
		String query =  " SELECT DISTINCT A.LOAITHANHTOAN,  A.PK_SEQ AS TTHDID, A.TRANGTHAI, A.NGAYGHINHAN, A.NGAYTAO, A.NGAYSUA, a.phinganhang, a.vat , isnull(a.thanhtoantutienvay,0) as thanhtoantutienvay, " +
						" CASE WHEN A.NCC_FK IS NOT NULL THEN  B.TEN " +
						"      ELSE (CASE WHEN NV.TEN IS NOT NULL THEN NV.TEN "+
						"				  WHEN KH.TEN IS NOT NULL THEN KH.TEN "+
						"                 when dvth.ten is not null then isnull(dvth.ten,'') \n" +
						"				  ELSE F.DIENGIAI END) END  AS NCCTEN," +
						" C.TEN AS HTTTTEN , isnull(a.iskttduyet,0) as iskttduyet,  "+
						" D.TEN AS NGUOITAO, E.TEN AS NGUOISUA, A.PREFIX " +
						"    ,case \n" +
						"   	when a.NCC_FK is not null then \n" +
						"   			(isnull((select cast(isnull(AA.sohoadon,'') as varchar(20)) +', ' as [text()] \n" +
						"   	   		from  \n" +
						"   	   		(  select hd.sohoadon, hd.PK_SEQ, tthd.loaihd " +
						"                  from ERP_HOADONNCC hd inner join erp_thanhtoanhoadon_hoadon tthd on hd.pk_seq = tthd.hoadon_fk " +
						"                  where tthd.loaihd = 0 and tthd.thanhtoanhd_fk = a.pk_seq  AND HD.CONGTY_FK = "+obj.getCongtyId()+"  " +
						"   	   		) AA \n" +
						"   	   		For XML PATH ('')),'')) \n" +
						"   	else '' \n" +
						"   	end	as sohoadon \n" +
						" FROM ERP_THANHTOANHOADON A LEFT  JOIN ERP_NHACUNGCAP B ON A.NCC_FK = B.PK_SEQ  " +
						" LEFT JOIN NHOMNHACUNGCAPCN F ON A.NHOMNCCCN = F.PK_SEQ "+
						" LEFT JOIN NHOMNHACUNGCAPCN_NCC G ON F.PK_SEQ = G.NHOMNHACUNGCAPCN_FK "+
						" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ=  A.NHANVIEN_FK  " +
						" LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=  A.KHACHHANG_FK  " +
						" left join ERP_DONVITHUCHIEN dvth on dvth.pk_seq = a.dvth_fk \n" +
						" INNER JOIN ERP_HINHTHUCTHANHTOAN C ON A.HTTT_FK = C.PK_SEQ " +
						" INNER JOIN NHANVIEN D ON A.NGUOITAO = D.PK_SEQ INNER JOIN NHANVIEN E ON A.NGUOISUA = E.PK_SEQ " +
						" WHERE ((C.PK_SEQ != 100002) OR (C.PK_SEQ = 100002 AND A.TRANGTHAI = 1)) and a.HTTT_FK = '100001' and A.CONGTY_FK = "+obj.getCongtyId()+" ";

 
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String nccId = request.getParameter("nccId");
		if(nccId == null)
			nccId = "";
		obj.setNccId(nccId);
		
		String nvId = request.getParameter("nvId");
		if(nvId == null)
			nvId = "";
		obj.setNvId(nvId);
		
		String[] loaihoadon = request.getParameterValues("loaihoadon");
		if (loaihoadon != null)
		{
			String _scheme = "";
			for(int i = 0; i < loaihoadon.length; i++)
			{
				_scheme += loaihoadon[i] + ",";
			}
			
			if(_scheme.trim().length() > 0)
			{
				_scheme = _scheme.substring(0, _scheme.length() - 1);
				obj.setLoaihoadon(_scheme);
			}
		}
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sochungtu = request.getParameter("sochungtu");
		if(sochungtu == null)
			sochungtu = "";
		obj.setSochungtu(sochungtu);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
		
		if(tungay.length() > 0)
			query += " and a.ngayghinhan >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayghinhan <= '" + denngay + "'";
		
		if(nccId.length() > 0)
			query += " and b.pk_seq = '" + nccId + "'";
		
		if(nvId.length() > 0)
			query += " and nv.pk_seq = '" + nvId + "'";
		
		if(obj.getLoaihoadon().trim().length() > 0)
			query += " and a.pk_seq in ( select thanhtoanhd_fk from ERP_THANHTOANHOADON_HOADON where loaihd in ( " + obj.getLoaihoadon() + " ) ) ";
		
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		if(sochungtu.length() >0){
			query+=" and cast( a.pk_seq as varchar(10)) like '%"+sochungtu+"%' ";
			
		}
		if(sohoadon.length() >0){
			query += " and a.pk_seq in (  select tt_hd.THANHTOANHD_FK  from ERP_THANHTOANHOADON_HOADON tt_hd "+
					 " inner join  ERP_HOADONNCC hd on hd.pk_seq=tt_hd.HOADON_FK "+
					 " where hd.sohoadon like '%"+ sohoadon +"%' )";
		}
		
		
		System.out.println("Cau search : "+ query);
		return query;
	}
	
	private String Delete(String tthdId, String userId)
	{
		dbutils db = new dbutils();
		
		String msg = "";
		

		String query = "UPDATE  ERP_THANHTOANHOADONNCC set TRANGTHAI = '2' , NGUOISUA = "+ userId +" , NGAYSUA = '"+ getDateTime() +"' where PK_SEQ = '" + tthdId + "'  ";
		if(!db.update(query))
		{
			msg = "Không thể xóa ERP_THANHTOANHOADON: " + query;
		}
			
		db.shutDown();
			
		return msg;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
