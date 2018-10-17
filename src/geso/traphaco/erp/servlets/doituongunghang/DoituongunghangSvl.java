package geso.traphaco.erp.servlets.doituongunghang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.doituongunghang.IDoituongunghang;
import geso.traphaco.erp.beans.doituongunghang.IDoituongunghangList;
import geso.traphaco.erp.beans.doituongunghang.imp.Doituongunghang;
import geso.traphaco.erp.beans.doituongunghang.imp.DoituongunghangList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class DoituongunghangSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public DoituongunghangSvl() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    IDoituongunghangList obj;    	    
	    HttpSession session = request.getSession();	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    obj = new DoituongunghangList();
	    session.setAttribute("congtyId",ctyId);
	    System.out.println("congty ID " + ctyId);
	    obj.setCtyId(ctyId);
	    
	    /*String chixem = request.getParameter("chixem");
	    obj.setChixem(chixem);*/
		
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    obj.init("");
	    session.setAttribute("obj",obj);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Doituongunghang.jsp");	    	
	}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
 	    response.setCharacterEncoding("UTF-8");
 	    response.setContentType("text/html; charset=UTF-8");

 	    IDoituongunghangList obj;
 	    obj = new DoituongunghangList();		
 	    
 		HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");
	    session.setAttribute("congtyId",ctyId);
	    System.out.println("congty ID " + ctyId);
	    obj.setCtyId(ctyId);

	    String chixem = request.getParameter("chixem");
	    obj.setChixem(chixem);
	    
 		Utility util = new Utility();
 	    String userId = util.antiSQLInspection(request.getParameter("userId"));

 	    String ma = util.antiSQLInspection(request.getParameter("ma"));
 	    if(ma == null)
 	    	ma = "";
 	    obj.setMa(ma);

 	    String ten = util.antiSQLInspection(request.getParameter("ten"));
 	    if(ten==null)
 	    	ten = "";
 	    obj.setTen(ten);

 	    String Trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
	    if(Trangthai==null)
	    	Trangthai="";
	    obj.setTrangthai(Trangthai);
	    
	    String pbId = util.antiSQLInspection(request.getParameter("pbId"));
	    if(pbId==null)
	    	pbId="";
	    obj.setPbId(pbId);
	    
	    String st ="";
	    	
	    if(ma.length()>0)
	    { 
	    	st = st + " and upper(NVTU.MA) like upper(N'%"+ util.replaceAEIOU(ma) +"%')";
        }
	    
	    
	    if(ten.length()>0)
	    { 
	    	st = st + " and dbo.ftBoDau(upper(NVTU.TEN)) like upper(N'%"+ util.replaceAEIOU(ten) +"%')";
        }

	    if(Trangthai.length()>0)
        { 
	       	st = st + " and NVTU.TRANGTHAI <='"+ Trangthai +"'";
	      
        }
	    
	    if(pbId.length()>0)
        { 
	       	st = st + " and NVTU.DVTH_FK ='"+ pbId +"'";
	      
        }
	            
	    obj.init(st);
         
        String action = request.getParameter("action");
        if(action.equals("xoa"))
        { 
        	obj = new DoituongunghangList();	
        	obj.init("");
        	session.setAttribute("obj",obj);
        	response.sendRedirect("/TraphacoHYERP/pages/Erp/Doituongunghang.jsp");	    	
        }
        else if(action.equals("new"))
        { 
        	IDoituongunghang objnv = new Doituongunghang();
         	objnv.setuserId(userId); 
         	objnv.setCtyId(ctyId);
         	objnv.init();         	
     	
     	    session.setAttribute("obj",objnv);
     		response.sendRedirect("/TraphacoHYERP/pages/Erp/DoituongunghangUpdate.jsp");	  
         }
         else
         {
  	       session.setAttribute("obj",obj);
  		   response.sendRedirect("/TraphacoHYERP/pages/Erp/Doituongunghang.jsp");	  
         }
 	
	}

}
