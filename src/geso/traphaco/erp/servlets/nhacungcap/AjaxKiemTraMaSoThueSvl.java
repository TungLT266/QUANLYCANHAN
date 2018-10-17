package geso.traphaco.erp.servlets.nhacungcap;


import geso.traphaco.erp.beans.nhacungcap.imp.ErpNhaCungCap;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxKiemTraMaSoThueSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public AjaxKiemTraMaSoThueSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		String maSoThue = request.getParameter("maSoThue") == null ? "" : request.getParameter("maSoThue").replace(" ", "");
		String id = request.getParameter("id") == null ? "" : request.getParameter("id").replace(" ", "");
		PrintWriter out = response.getWriter();	
		
		ErpNhaCungCap obj = new ErpNhaCungCap();
		obj.setId(id);
		obj.setMST(maSoThue);
		
		String tenNhaCungCap = obj.kiemTraMaSoThue();
		obj.close();
		out.write(tenNhaCungCap);
	}
}