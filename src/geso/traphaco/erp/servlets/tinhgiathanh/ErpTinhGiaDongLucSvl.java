package geso.traphaco.erp.servlets.tinhgiathanh;
import geso.traphaco.erp.beans.khoasothang.IErpNuocda;
import geso.traphaco.erp.beans.khoasothang.IErptiendien;
import geso.traphaco.erp.beans.khoasothang.IErptiennuoc;
import geso.traphaco.erp.beans.khoasothang.IErptinhgiadongluc;
import geso.traphaco.erp.beans.khoasothang.IErptinhgiadongluclist;
import geso.traphaco.erp.beans.khoasothang.imp.ErpNuocda;
import geso.traphaco.erp.beans.khoasothang.imp.ErpTiendien;
import geso.traphaco.erp.beans.khoasothang.imp.ErpTiennuoc;
import geso.traphaco.erp.beans.khoasothang.imp.Erptinhgiadongluc;
import geso.traphaco.erp.beans.khoasothang.imp.Erptinhgiadongluclist;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class ErpTinhGiaDongLucSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpTinhGiaDongLucSvl() {
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
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    String id=util.getId(querystring);
	    IErptinhgiadongluc obj = new Erptinhgiadongluc();
	    obj.setUserId(userId);
	    obj.setId(id);
	    String msg = "";
	    obj.Init();
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoERP/pages/Erp/ErpTinhgiadongluc.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	  	    
	    HttpSession session = request.getSession();	
	    	
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));     
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    String id = util.antiSQLInspection(request.getParameter("id"));
	    if(id==null){
	    	id="";
	    }
	    String thang =  request.getParameter("thang");
	    if (thang == null || thang.equals("")){
	    	thang = "0";
	    }
	    
	    String nam = request.getParameter("nam");
	    if (nam == null  || nam.equals("")){
	    	nam = "0";
	    }
	    IErptinhgiadongluc obj = new Erptinhgiadongluc();
	    String tongtientrenhoadon = request.getParameter("tongtientrenhoadon");
	    if (tongtientrenhoadon == null  || tongtientrenhoadon.equals("")){
	    	tongtientrenhoadon = "0";
	    }
	    try{
	    	obj.setTongTienDien(Double.parseDouble(tongtientrenhoadon.replace(",","")));
	    }catch(Exception err){}
	    
	    
	    String tiennuoctrenhoadon = request.getParameter("tiennuoctrenhoadon");
	    if (tiennuoctrenhoadon == null  || tiennuoctrenhoadon.equals("")){
	    	tiennuoctrenhoadon = "0";
	    }
	    try{
	    	obj.setTongTienNuoc(Double.parseDouble(tiennuoctrenhoadon.replace(",","")));
	    }catch(Exception err){}
	    
	    obj.setThang( Integer.parseInt(thang));
	    obj.setNam( Integer.parseInt(nam));
	    obj.setUserId(userId);
	    
	    String[] iddonghodien = request.getParameterValues("iddonghodien");
	    String[] tendongho = request.getParameterValues("tendongho");
	    String[] soluongdien = request.getParameterValues("soluongdien");
	    String[] dongiadien = request.getParameterValues("dongiadien");
	    String[] thanhtiendien = request.getParameterValues("thanhtiendien");
	    
	    List<IErptiendien> listtd= new ArrayList<IErptiendien>();
	    if(iddonghodien!=null){
		for(int i=0;i<iddonghodien.length;i++){
				double soluongdien_=0;
				double dongiadien_=0;
				double thanhtiendien_=0;
				try{soluongdien_=Double.parseDouble(soluongdien[i].replaceAll(",","")) ; }catch(Exception er){}
				try{dongiadien_=Double.parseDouble(dongiadien[i].replaceAll(",","")) ; }catch(Exception er){}
				try{thanhtiendien_=Double.parseDouble(thanhtiendien[i].replaceAll(",","")) ; }catch(Exception er){}
				
				IErptiendien tiendien=new ErpTiendien();
				tiendien.setDongia(dongiadien_);
				tiendien.setIdDongHoDien(iddonghodien[i]);
				tiendien.setTenDongHoDien(tendongho[i]);
				tiendien.setSoLuong(soluongdien_);
				tiendien.setThanhtien(thanhtiendien_);
				listtd.add(tiendien);
			}
	    }
	    
	    obj.setListiendien(listtd);
	    
	    // đồng hồ nước
	    
	    String[] iddonghonuoc = request.getParameterValues("iddonghonuoc");
	     
	    String[] tendonghonuoc = request.getParameterValues("tendonghonuoc");
	    String[] soluongnuoc = request.getParameterValues("soluongnuoc");
	    String[] dongianuoc = request.getParameterValues("dongianuoc");
	    String[] thanhtiennuoc = request.getParameterValues("thanhtiennuoc");
	    
	    List<IErptiennuoc> listtd_nuoc= new ArrayList<IErptiennuoc>();
	    if(iddonghonuoc!=null){
		for(int i=0;i<iddonghonuoc.length;i++){
				double soluongnuoc_=0;
				double dongianuoc_=0;
				double thanhtiennuoc_=0;
				try{soluongnuoc_=Double.parseDouble(soluongnuoc[i].replaceAll(",","")) ; }catch(Exception er){}
				try{dongianuoc_=Double.parseDouble(dongianuoc[i].replaceAll(",","")) ; }catch(Exception er){}
				try{thanhtiennuoc_=Double.parseDouble(thanhtiennuoc[i].replaceAll(",","")) ; }catch(Exception er){}
				
				IErptiennuoc tiennuoc=new ErpTiennuoc();
				tiennuoc.setDongia(dongianuoc_);
				tiennuoc.setIdDongHoNuoc(iddonghonuoc[i]);
				tiennuoc.setTenDongHoNuoc(tendonghonuoc[i]);
				tiennuoc.setSoLuong(soluongnuoc_);
				tiennuoc.setThanhtien(thanhtiennuoc_);
				listtd_nuoc.add(tiennuoc);
			}
	    }
	    
	    obj.setListiennuoc(listtd_nuoc);
	    
	    
	    String[] idnhamay = request.getParameterValues("idnhamay");
	    String[] tennhamay = request.getParameterValues("tennhamay");
	    String[] soluongnuocda = request.getParameterValues("soluongnuocda");
	    String[] dongianuocda = request.getParameterValues("dongianuocda");
	    String[] thanhtiennuocda = request.getParameterValues("thanhtiennuocda");
	    
	    List<IErpNuocda> listnuocda= new ArrayList<IErpNuocda>();
	    if(idnhamay!=null){
		for(int i=0;i<idnhamay.length;i++){
				double soluongdien_=0;
				double dongiadien_=0;
				double thanhtiendien_=0;
				try{soluongdien_=Double.parseDouble(soluongnuocda[i].replaceAll(",","")) ; }catch(Exception er){}
				try{dongiadien_=Double.parseDouble(dongianuocda[i].replaceAll(",","")) ; }catch(Exception er){}
				try{thanhtiendien_=Double.parseDouble(thanhtiennuocda[i].replaceAll(",","")) ; }catch(Exception er){}
				
				IErpNuocda nuocda=new ErpNuocda();
				nuocda.setDongia(dongiadien_);
				nuocda.setIdNhamay(idnhamay[i]);
				nuocda.setTenNhamay(tennhamay[i]);
				nuocda.setSoLuong(soluongdien_);
				nuocda.setThanhtien(thanhtiendien_);
				listnuocda.add(nuocda);
			}
	    }
	    
	    obj.setListnuocda(listnuocda);
	    
	    
	    obj.setId(id);
	    if(action.equals("capnhat"))
	    {
	    	if(id.length() ==0){
			    	if(!obj.Save()){
			    		    obj.Init();
			    		    obj.setUserId(userId);
			    		    session.setAttribute("obj", obj);
			    		    String nextJSP = "/TraphacoERP/pages/Erp/ErpTinhgiadongluc.jsp";
			    			response.sendRedirect(nextJSP);
			    	}else{
			    		IErptinhgiadongluclist obj1 = new Erptinhgiadongluclist();
			    	    obj1.setUserId(userId);
			    	    obj1.Init();
			    	    obj.DbClose();
			    		session.setAttribute("obj", obj1);
			    	    String nextJSP = "/TraphacoERP/pages/Erp/ErpTinhGiaDongLucList.jsp";
			    		response.sendRedirect(nextJSP);
			    	}
	    	}else{
		    		if(!obj.Edit()){
		    		    obj.Init();
		    		    obj.setUserId(userId);
		    		    
		    		    session.setAttribute("obj", obj);
		    		    String nextJSP = "/TraphacoERP/pages/Erp/ErpTinhgiadongluc.jsp";
		    			response.sendRedirect(nextJSP);
			    	}else{
			    		IErptinhgiadongluclist obj1 = new Erptinhgiadongluclist();
			    	    obj1.setUserId(userId);
			    	    obj1.Init();
			    	    obj.DbClose();
			    		session.setAttribute("obj", obj1);
			    	    String nextJSP = "/TraphacoERP/pages/Erp/ErpTinhGiaDongLucList.jsp";
			    		response.sendRedirect(nextJSP);
			    	}
	    	}
	    	
	    }else{
	    	obj.Init();
 		    obj.setUserId(userId);
 		    session.setAttribute("obj", obj);
 		    String nextJSP = "/TraphacoERP/pages/Erp/ErpTinhgiadongluc.jsp";
 		     response.sendRedirect(nextJSP);
	    }
	     
	  
	   
	}
 
}
