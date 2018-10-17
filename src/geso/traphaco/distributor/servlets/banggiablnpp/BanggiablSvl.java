package geso.traphaco.distributor.servlets.banggiablnpp;

import geso.traphaco.distributor.beans.banggiablnpp.*;
import geso.traphaco.distributor.beans.banggiablnpp.imp.*;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BanggiablSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out; 
	String userId;   
   
    public BanggiablSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
			IBanggiablnppList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	       

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String bgblId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(bgblId);
	    	out.print(bgblId);
	    }
	   	    
	    obj = new BanggiablnppList();
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/BangGiaBanLeNpp.jsp";
		response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
			IBanggiablnppList obj  = new BanggiablnppList();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    Utility util = new Utility();
		
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if (action.equals("new"))
	    {
	    	// Empty Bean for distributor
	    	IBanggiablnpp bgblBean = (IBanggiablnpp) new Banggiablnpp("");
	    	bgblBean.setUserId(userId);
	    	bgblBean.createRS();
	    	// Save Data into session
	    	session.setAttribute("bgblBean", bgblBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/BgBanLeNppNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search")){
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/BangGiaBanLeNpp.jsp");	    		    	
	    }
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IBanggiablnppList obj)
	{
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String bgblTen = util.antiSQLInspection(request.getParameter("bgblTen"));
    	if ( bgblTen == null)
    		bgblTen = "";
    	obj.setTenbanggia(bgblTen);
    	
    	String nccId = util.antiSQLInspection(request.getParameter("nccTen"));
    	if ( nccId == null)
    		nccId = "";
    	obj.setNccId(nccId);
    	
    	String dvkdId = util.antiSQLInspection(request.getParameter("dvkdTen"));
    	if (dvkdId == null)
    		dvkdId = "";    	
    	obj.setDvkdId(dvkdId);
    		
    String	query = "SELECT     bgbl.PK_SEQ AS BGBLID, bgbl.TEN AS BGBLTEN,  "+
   " bgbl.NGAYTAO, bgbl.NGAYSUA, nv.TEN AS NGUOITAO, nv2.TEN AS NGUOISUA ,"+
   " npp.PK_SEQ AS NPPID, npp.TEN AS NPPTEN, dvkd.DONVIKINHDOANH AS DVKDTENVIETTAT,  "+
  "dvkd.DIENGIAI AS DVKDTEN,  dvkd.PK_SEQ AS DVKDID FROM dbo.BANGGIABANLENPP AS bgbl  "+
 " INNER JOIN  dbo.NHANVIEN AS nv ON bgbl.NGUOITAO = nv.PK_SEQ INNER JOIN  dbo.NHANVIEN AS nv2 ON bgbl.NGUOISUA = "+
  " nv2.PK_SEQ LEFT OUTER JOIN  dbo.NHAPHANPHOI AS npp ON bgbl.NPP_FK = npp.PK_SEQ INNER JOIN  dbo.DONVIKINHDOANH AS "+
  " dvkd ON bgbl.DVKD_FK = dvkd.PK_SEQ  	where dvkd.PK_SEQ in (select b.dvkd_fk from nhapp_nhacc_donvikd a inner join  "+
  "nhacungcap_dvkd b on a.ncc_dvkd_fk = b.pk_seq inner join donvikinhdoanh c on b.dvkd_fk = c.pk_seq inner  "+
 " join nhacungcap ncc on ncc.pk_Seq=b.ncc_fk where a.npp_fk='"+nppId+"') and   npp.pk_Seq='"+nppId+"'";
    	
    	if (bgblTen.length()>0)
    	{
			query = query + " and upper(dbo.ftBoDau(bgbl.TEN )) like upper(N'%" + util.replaceAEIOU(bgblTen) + "%')";			
    	}
    	
    	if (nccId.length()>0){
			query = query + " and npp.PK_SEQ ='" + nccId + "'";			
    	}
    	
    	if (dvkdId.length()>0){
			query = query + " and dvkd.PK_SEQ ='" + dvkdId + "'";			
    	}
    	
    	query = query + "  order by bgbl.pk_seq";
    	return query;
	}
	

	private void Delete(String bgblId) 
	{
		dbutils db = new dbutils();
		db.update("delete from bgbanlenpp_sanpham where bgbanlenpp_fk='" + bgblId + "'");
		//Xoa Bang Cha
		db.update("delete from banggiabanlenpp where pk_seq = '" + bgblId + "'");
		db.shutDown();
	}

}
