package geso.traphaco.center.servlets.khachhang;

import geso.traphaco.center.beans.khachhang.IKhachhang;
import geso.traphaco.center.beans.khachhang.IKhachhangList;
import geso.traphaco.center.beans.khachhang.imp.Khachhang;
import geso.traphaco.center.beans.khachhang.imp.KhachhangList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhachhangUpdateTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public KhachhangUpdateTTSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		System.out.println("userId : "+userId+" & userTen : "+userTen);
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		IKhachhang khBean;
		PrintWriter out; 
						
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	

	    khBean = new Khachhang(id);
        khBean.setUserId(userId);
        khBean.init();
         session.setAttribute("khBean", khBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/KhachHangUpdate.jsp";
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
		
		IKhachhang khBean;
		//PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		//out = response.getWriter();
		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	khBean = new Khachhang("");
	    }else{
	    	khBean = new Khachhang(id);
	    }
	    
		userId = util.antiSQLInspection(request.getParameter("userId"));
		khBean.setUserId(userId);
	    
    	String khTen = util.antiSQLInspection(request.getParameter("khTen"));
		if (khTen == null)
			khTen = "";				
    	khBean.setTen(khTen);
    	
    	String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		khBean.setNppId(nppId);
		System.out.println("Npp la; " + nppId);
		
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	khBean.setTrangthai(trangthai);
    	
    	String diachi = request.getParameter("diachi");
		if (diachi == null)
			diachi = "";
		khBean.setDiachi(diachi);
		
    	String tpId = request.getParameter("tpId");
		if (tpId == null)
			tpId = "";
		khBean.setTpId(tpId);

    	String qhId = request.getParameter("qhId");
		if (qhId == null)
			qhId = "";
		khBean.setQhId(qhId);
		
		String ghcnId = request.getParameter("ghcnTen");
		if (ghcnId.length()==0)
			ghcnId = "";
		khBean.setGhcnId(ghcnId);
		
		String mckId = request.getParameter("mckTen");
		if (mckId.length()==0)
			mckId = "";
		khBean.setMckId(mckId);
		
		//System.out.println("chietkhau :" + mckId);
		String dienthoai = request.getParameter("dienthoai");
		if(dienthoai == null)
			dienthoai = "";
		khBean.setSodienthoai(dienthoai);
		
		String masothue = request.getParameter("masothue");
		if(masothue == null)
			masothue = "";
		khBean.setMasothue(masothue);
		
		String kbhId = request.getParameter("kbhTen");
		if (kbhId == null)
			kbhId = "";
		khBean.setKbhId(kbhId);
	
		String nvgnId = request.getParameter("nvgnTen");
		if (nvgnId == null)
			nvgnId = "";
		khBean.setNvgnId(nvgnId);
		
		String lchId = request.getParameter("lchTen");
		if (lchId == null)
			lchId = "";
		khBean.setLchId(lchId);
		
		String hchId = request.getParameter("hchTen");
		if (hchId == null)
			hchId = "";
		khBean.setHchId(hchId);
		
		String vtchId = request.getParameter("vtchTen");
		if (vtchId == null)
			vtchId = "";
		khBean.setVtId(vtchId);
		
		String[] nkh_khIds = request.getParameterValues("nkh_khList");
		khBean.setNkh_KhIds(nkh_khIds);
		
		String ngaysua = getDateTime();
    	khBean.setNgaysua(ngaysua);
		
		boolean error = false;
		
		if (kbhId.equals("")){
			khBean.setMessage("Vui Long Chon Kenh Ban Hang");
			error = true;
		}

		if (diachi.trim().length()== 0){
			khBean.setMessage("Vui Long Nhap Dia Chi");
			error = true;
		}
				
		if (khTen.trim().length()== 0){
			khBean.setMessage("Vui Long Nhap Ten Khach Hang");
			error = true;
		}

 		String action = request.getParameter("action");
	    if (!error){
	    	if(action.equals("save"))
	    	{	
	    		if ( id == null){
	    			if (!(khBean.CreateKh())){	
	    				khBean.createRS();
	    				session.setAttribute("khBean", khBean);			
	    				String nextJSP = "/TraphacoHYERP/pages/Center/KhachHangNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			}else{
	    				IKhachhangList obj = new KhachhangList();
	    				obj.setUserId(userId);
	    				obj.init("");
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/TraphacoHYERP/pages/Center/KhachHang.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
				
	    		}else{
	    			if (!(khBean.UpdateKh()))
	    			{
	    				khBean.init();
	    				session.setAttribute("khBean", khBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/KhachHangUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else{
	    				IKhachhangList obj = new KhachhangList();
	    				obj.setUserId(userId);
	    				obj.init("");
	    				session.setAttribute("obj", obj);
	    				
	    				String nextJSP = "/TraphacoHYERP/pages/Center/KhachHang.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	}
	    	else
	    	{
	    		khBean.setUserId(userId);
	    		khBean.createRS();
			
	    		session.setAttribute("khBean", khBean);
			
	    		String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/TraphacoHYERP/pages/Center/KhachHangNew.jsp";
	    		}else{
	    			nextJSP = "/TraphacoHYERP/pages/Center/KhachHangUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);		
	    	}
	    }
	    else{
    		khBean.setUserId(userId);
    		khBean.createRS();
		
    		session.setAttribute("khBean", khBean);
		
    		String nextJSP;
    		if (id == null){			
    			nextJSP = "/TraphacoHYERP/pages/Center/KhachHangNew.jsp";
    		}else{
    			nextJSP = "/TraphacoHYERP/pages/Center/KhachHangUpdate.jsp";   						
    		}
    		response.sendRedirect(nextJSP);		
	    	
	    }
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
