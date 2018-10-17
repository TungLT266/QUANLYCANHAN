package geso.traphaco.erp.servlets.timnhacc_nk;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.timnhacc_nk.ITimnhacc;
import geso.traphaco.erp.beans.timnhacc_nk.ITimnhaccList;
import geso.traphaco.erp.beans.timnhacc_nk.imp.Timnhacc;
import geso.traphaco.erp.beans.timnhacc_nk.imp.TimnhaccList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TimnhaccSvl_nk extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public TimnhaccSvl_nk() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ITimnhaccList obj;
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
	    String contyId = (String)session.getAttribute("congtyId");
		session.setAttribute("congtyId", contyId);
	    String timnccId = util.getId(querystring);
	    
	    String type = request.getParameter("type");
	    if(type == null)
	    	type = "";
	    if(type.equals("ajax"))
    	{
    		  GET_DATA_TO_CLIENT( request, response, action );
    	}
	    else
	    {
		    String msg = "";
		    if (action.equals("delete"))
		    {	
		    	msg = Delete(timnccId,userId);
		    }
		    else if(action.equals("chot"))
	    	{
	    		dbutils db = new dbutils();
	    		db.update(" update ERP_TIMNCC set trangthai = '1' where pk_seq = '" + timnccId + "'");
	    		db.shutDown();
	    	}
		    else if(action.equals("unchot"))
		    {
		    	dbutils db = new dbutils();
	    		db.update(" update ERP_TIMNCC set trangthai = '0' where pk_seq = '" + timnccId + "'");
	    		db.shutDown();
		    }
		    
		    
		    
		    obj = new TimnhaccList();
		    obj.setUserId(userId);
		    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    if(msg.length() > 0) obj.setMsg(msg);
		    
		    obj.init("");
		    
			session.setAttribute("obj", obj);
					
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTimNcc_nk.jsp";
			response.sendRedirect(nextJSP);
	    }
	}
    
    private void GET_DATA_TO_CLIENT(HttpServletRequest request, HttpServletResponse response, String action)  throws ServletException, IOException 
	{
    	try
    	{
			PrintWriter out = response.getWriter();
		    HttpSession session = request.getSession();
		    
		    String spId =request.getParameter("spId");
		    String nccId =request.getParameter("nccId");
		    String userId =request.getParameter("userId");	    
		    Utility util = new Utility();
		    dbutils db = new dbutils();
		    
		    String query = "select top 1 a.PK_SEQ from erp_danhgiancc a where a.NCC_FK = '"+nccId+"' and a.SP_FK = '"+spId+"' order by a.pk_seq desc";
			String dgid = "0";
			ResultSet timdg = db.get(query);
			while(timdg.next())
			{
				dgid = timdg.getString("pk_seq");
			} 
			query = "select a.PK_SEQ, a.NCC_FK, a.SP_FK, e.pk_seq as tieuchi, d.diem, d.dat " +  
					"from ERP_DANHGIANCC a inner join ERP_DGNCC_TIEUCHIDG d on a.PK_SEQ = d.DG_FK right join ERP_TIEUCHIDANHGIA e on d.TIEUCHI_FK = e.pk_seq " + 
					"where a.pk_seq = '"+dgid+"' ";
			//System.out.println("lay thong tin danh gia: " + query);
			ResultSet rsdg =  db.get(query);
			String tucach = "", uytin = "", chatluongsp = "", thoigiangiaohang = "", giaca = "", phuongthuctt = "", phuongthucvc = "", haumai = "", chinhsachkhac = "";
	        while(rsdg.next())
			{
	        	String tieuchi = rsdg.getString("tieuchi");
	        	if(tieuchi.equals("100000"))
	        		tucach = rsdg.getString("dat");
	        	if(tieuchi.equals("100001"))
	        		uytin = rsdg.getString("dat");
	        	if(tieuchi.equals("100002"))
	        		chatluongsp = rsdg.getString("dat");
	        	if(tieuchi.equals("100003"))
	        		thoigiangiaohang = rsdg.getString("dat");
	        	if(tieuchi.equals("100004"))
	        		giaca = rsdg.getString("diem");
	        	if(tieuchi.equals("100005"))
	        		phuongthuctt = rsdg.getString("diem");
	        	if(tieuchi.equals("100006"))
	        		phuongthucvc = rsdg.getString("diem");
	        	if(tieuchi.equals("100007"))
	        		haumai = rsdg.getString("diem");
	        	if(tieuchi.equals("100008"))
	        		chinhsachkhac = rsdg.getString("diem");
	        	
			}
	        rsdg.close();
	        
		    String myDATA =
		    "<div align=\"center\" style=\"width: 100%; float: none; clear: left\">"+
			"<fieldset>"+
				"<legend class=\"legendtitle\"> Tiêu chí bắt buộc</legend>"+	
				"<div style=\"float: none; width: 100%\" align=\"center\">"+
					"<TABLE class=\"tabledetail\" width=\"100%\" border=\"0\" cellspacing=\"1px\" cellpadding=\"0px\">"+
						"<TR class=\"tbheader\">"+
						"<TH align=\"center\" width=\"5%\"> STT </TH>"+
						"<TH align=\"center\" width=\"50%\"> Tiêu chí </TH>"+
						"<TH align=\"center\" width=\"10%\"> Đạt</TH>"+
						"</TR>"+	
						"<TR class='tbdarkrow' >"+
						"<TD align=\"center\">1</TD>"+
						"<TD align=\"center\">Tư cách pháp nhân</TD>"+
						"<TD align=\"center\">";
						if(tucach.equals("1")) { 
	      					myDATA += "<input type=\"checkbox\" name=\"tucach\" value=\"1\" checked=\"checked\" >";
	      				} else { 
	      					myDATA += "<input type=\"checkbox\" name=\"tucach\" value=\"0\"  >";
	      				}
						myDATA += "</TD>"+
						"</tr>"+
						"<TR class='tblightrow'  >"+
						"<TD align=\"center\">2</TD>"+
						"<TD align=\"center\">Uy tín</TD>"+
						"<TD align=\"center\">";
						if(uytin.equals("1")) { 
							myDATA += "<input type=\"checkbox\" name=\"uytin\" value=\"1\" checked=\"checked\" >";
	      				} else {
	      					myDATA += "<input type=\"checkbox\" name=\"uytin\" value=\"0\"  >";
	      				}
						myDATA += "</TD>"+
						"</TR>"+
						"<TR class='tbdarkrow'   >"+
						"<TD align=\"center\">3</TD>"+
						"<TD align=\"center\">Chất lượng sản phẩm</TD>"+
						"<TD align=\"center\">";
						if(chatluongsp.equals("1")) {
							myDATA += "<input type=\"checkbox\" name=\"chatluongsp\" value=\"1\" checked=\"checked\" >";
	      				} else {
	      					myDATA += "<input type=\"checkbox\" name=\"chatluongsp\" value=\"0\"  >";
	      				}
						myDATA += "</TD>"+
						"</tr>"+
						"<TR class='tblightrow'  >"+
						"<TD align=\"center\">4</TD>"+
						"<TD align=\"center\">Thời gian giao hàng</TD>"+
						"<TD align=\"center\">";
						if(thoigiangiaohang.equals("1")) {
							myDATA += "<input type=\"checkbox\" name=\"tggiaohang\" value=\"1\" checked=\"checked\" >";
	      				} else {
	      					myDATA += "<input type=\"checkbox\" name=\"tggiaohang\" value=\"0\"  >";
	      				}
						myDATA += "</TD>"+
						"</TR>"+
					"</TABLE>"+
	        	"</div>"+
			"</fieldset>"+
			"</div>"+
			"<div align=\"center\" style=\"width: 100%; float: none; clear: left\">"+
			"<fieldset>"+
				"<legend class=\"legendtitle\"> Tiêu chí không bắt buộc</legend>"+	
				"<div style=\"float: none; width: 100%\" align=\"center\">"+
					"<TABLE class=\"tabledetail\" width=\"100%\" border=\"0\" cellspacing=\"1px\" cellpadding=\"0px\">"+
						"<TR class=\"tbheader\">"+
	            	"<TH align=\"center\" width=\"5%\"> STT</TH>"+
	                "<TH align=\"center\" width=\"50%\"> Tiêu chí</TH>"+
	                "<TH align=\"center\" width=\"10%\"> Điểm </TH>"+
	            "</TR>"+	
	            "<TR class='tbdarkrow'   >"+
	            	"<TD align=\"center\">1</TD>"+
	            	"<TD align=\"center\">Giá cả</TD>"+
	            	"<TD align=\"center\"><INPUT type=\"text\" name=\"giaca\" style=\"width: 97%\" value='"+giaca+"' ></TD>"+
	            "</tr>"+
	            "<TR class='tblightrow'  >"+
	            	"<TD align=\"center\">2</TD>"+
	            	"<TD align=\"center\">Phương tiện thanh toán</TD>"+
	            	"<TD align=\"center\"><INPUT type=\"text\" name=\"ptthanhtoan<%= n %>\" style=\"width: 97%\" value='"+phuongthuctt+"' ></TD>"+
	            "</TR>"+
	            "<TR class='tbdarkrow'   >"+
	            	"<TD align=\"center\">3</TD>"+
	            	"<TD align=\"center\">Phương tiện vận chuyển</TD>"+
	            	"<TD align=\"center\"><INPUT type=\"text\" name=\"ptvanchuyen<%= n %>\" style=\"width: 97%\" value='"+phuongthucvc+"' ></TD>"+
	            "</tr>"+
	            "<TR class='tblightrow'  >"+
	            	"<TD align=\"center\">4</TD>"+
	            	"<TD align=\"center\">Chế độ hậu mãi</TD>"+
	            	"<TD align=\"center\"><INPUT type=\"text\" name=\"haumai\" style=\"width: 97%\" value='"+haumai+"' ></TD>"+
	            "</TR>"+
	            "<TR class='tbdarkrow'   >"+
	            	"<TD align=\"center\">5</TD>"+
	            	"<TD align=\"center\">Chính sách khác</TD>"+
	            	"<TD align=\"center\"><INPUT type=\"text\" name=\"chinhsachkhac\" style=\"width: 97%\" value='"+chinhsachkhac+"' ></TD>"+
	            "</tr>"+
	            "</TABLE>"+
	        "</div>"+
			"</fieldset>"+
			"</div>";
		     
		    out.write(myDATA);
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
	}
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    ITimnhaccList obj;
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    String contyId = (String)session.getAttribute("congtyId");
		session.setAttribute("congtyId", contyId);
		
	    if(action.equals("Tao moi"))
	    {
	    	ITimnhacc timnccBean = new Timnhacc("");
	    	timnccBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	timnccBean.setUserId(userId);
	    	
	    	timnccBean.createRs();
	    	
	    	session.setAttribute("timnccBean", timnccBean);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTimNccNew_nk.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new TimnhaccList();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
	    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

	    		String search = getSearchQuery(request, obj);
		    	
		    	obj.init(search);

		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpTimNcc_nk.jsp");	
		    }
	    	else
	    	{
		    	obj = new TimnhaccList();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpTimNcc_nk.jsp");  
	    	}
	    }
	}
    
    private String getSearchQuery(HttpServletRequest request, ITimnhaccList obj)
	{
		Utility util = new Utility();
		
		geso.traphaco.center.db.sql.dbutils db = new geso.traphaco.center.db.sql.dbutils();
		
		String sodenghi = request.getParameter("sodenghi");
		if(sodenghi == null)
			sodenghi = "";
		obj.setSodenghi(sodenghi);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String soItem = request.getParameter("soItems");
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		obj.setSoItems(soItems);
		
		String query = "select a.pk_seq as id, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, isnull(a.diengiai, 'na') as diengiai, d.pk_seq as muahang_id "
				+ " from erp_timncc a, nhanvien b, nhanvien c, ERP_MUAHANG d "
				+ " where a.congty_fk = "+ obj.getCongtyId() +" and a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.denghimua_fk = d.pk_seq"; 			
		
		if(sodenghi.trim().length() > 0)
			query += " and a.denghimua_fk like '%" + sodenghi + "%'";
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";

		System.out.println("Query la: " + query + "\n");

		return query;
	}

	private String Delete(String timnccId,String userId)
	{
		dbutils db = new dbutils();
		String query="";
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			query = "delete ERP_TIMNCC_NCC WHERE TIMNCC_FK =  '" + timnccId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa ERP_TIMNCC_NCC: " + query;
			}
			
			query = " DELETE ERP_TIMNCC WHERE PK_SEQ = '" + timnccId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa ERP_TIMNCC: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			db.shutDown(); 
			return "Loi-khong the xoa don tim nha cung cap: "+query; 
		}
		
	}
}
