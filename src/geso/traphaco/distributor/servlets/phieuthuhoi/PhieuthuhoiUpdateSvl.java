package geso.traphaco.distributor.servlets.phieuthuhoi;



import geso.traphaco.distributor.beans.phieuthuhoi.IPhieuthuhoi;
import geso.traphaco.distributor.beans.phieuthuhoi.IPhieuthuhoiList;
import geso.traphaco.distributor.beans.phieuthuhoi.ISanphamSoLo;
import geso.traphaco.distributor.beans.phieuthuhoi.ISanphamthuhoi;
import geso.traphaco.distributor.beans.phieuthuhoi.imp.Phieuthuhoi;
import geso.traphaco.distributor.beans.phieuthuhoi.imp.PhieuthuhoiList;
import geso.traphaco.distributor.beans.phieuthuhoi.imp.SanphamSolo;
import geso.traphaco.distributor.beans.phieuthuhoi.imp.Sanphamthuhoi;

import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PhieuthuhoiUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	
    public PhieuthuhoiUpdateSvl()
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
		
			IPhieuthuhoi pthBean;
			Utility util = new Utility();
			
			String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    String id = util.getId(querystring);
		    pthBean = new Phieuthuhoi(id);
		    pthBean.setUserId(userId);
		    pthBean.init1();
		    
		    session.setAttribute("obj", pthBean);  	
			session.setAttribute("userId", userId);
	    		
			response.sendRedirect("/TraphacoHYERP/pages/Distributor/PhieuThuHoiUpdate.jsp");
		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
			Utility util = new Utility();
			String Id= util.antiSQLInspection(request.getParameter("id"));
		IPhieuthuhoi obj  = new Phieuthuhoi(Id);
	
		
		    String err="";
		    if(userId==null){
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    	}
		    obj.setUserId(userId);
		    System.out.println(userId);
		    
		    String action= util.antiSQLInspection(request.getParameter("action"));
		    String pxkid= util.antiSQLInspection(request.getParameter("pxkid"));
		    obj.setPhieuxuatkho(pxkid);
		    
	     //Sản phẩm hàng bán 
	     	
	     	String[] idsp = request.getParameterValues("idsp");
	     	String[] masp = request.getParameterValues("masp");
			String[] tensp = request.getParameterValues("tensp");
			String[] soluong = request.getParameterValues("soluong");
	     	String[] kbh_fk = request.getParameterValues("kbh_fk");
			String[] kho_fk = request.getParameterValues("kho_fk");
			String[] donvitinh = request.getParameterValues("donvitinh");
			
			List<ISanphamthuhoi>	spList = new ArrayList<ISanphamthuhoi>();
			if(masp != null)
			{		
				ISanphamthuhoi sanpham = null;
				int m=0;
				while(m < masp.length)
				{
					sanpham= new Sanphamthuhoi();
					sanpham.setSPId(idsp[m]);
					sanpham.setMasanpham(masp[m]);
					sanpham.setTensanpham(tensp[m]);
					
						
					double soluongth=Double.parseDouble(soluong[m]);
					double sumsoluonglo=0;
					sanpham.setSoluong(soluong[m]);
					
					sanpham.setKhoId(kho_fk[m]);
					sanpham.setKenhId(kbh_fk[m]);
					 sanpham.setDonvitinh(donvitinh[m]);
					String[] solo = request.getParameterValues(idsp[m]+".solo");
					String[] soluonglo = request.getParameterValues(idsp[m]+".soluong");
					String[] ngayhethan = request.getParameterValues(idsp[m]+".ngayhethan");
					String[] soluongxk = request.getParameterValues(idsp[m]+".soluongxk");
					List<ISanphamSoLo> listlo=new ArrayList<ISanphamSoLo>();
					
					for(int j=0;j<solo.length;j++){
						ISanphamSoLo lo=new SanphamSolo();
						lo.setSolo(solo[j]);
						
						listlo.add(lo);
						lo.setNgayhethang(ngayhethan[j]);
						double soluonglo1=0;
						try{
							soluonglo1 =Double.parseDouble(soluonglo[j]);
						}catch(Exception er ){
							
						}
						lo.setSoluong(""+Math.round(soluonglo1));
						sumsoluonglo=sumsoluonglo+soluonglo1;
						double soluongxk1=0;
						try{
							soluongxk1 =Double.parseDouble(soluongxk[j]);
						}catch(Exception er ){
							
						}
						lo.setSoluongXK(soluongxk[j]);
					}
					if(soluongth!=sumsoluonglo){
						err=err+ " \n Số lượng trong lô của sản phẩm: "+ tensp[m]+" chưa hợp lệ.";
					}
					sanpham.SetSpLoList(listlo);
					m++;
					spList.add(sanpham);
				}
			}
			obj.setSanphamList(spList);
			//Begin san pham khuyen mai
			List<ISanphamthuhoi>	spkmList = new ArrayList<ISanphamthuhoi>();
			
			String[] kmidsp = request.getParameterValues("kmidsp");
	     	String[] kmmasp = request.getParameterValues("kmmasp");
			String[] kmtensp = request.getParameterValues("kmtensp");
			String[] kmsoluong = request.getParameterValues("kmsoluong");
	     	String[] kmkbh_fk = request.getParameterValues("kmkbh_fk");
			String[] kmkho_fk = request.getParameterValues("kmkho_fk");
			String[] kmdonvitinh = request.getParameterValues("kmdonvitinh");
			
			
			if(kmmasp != null)
			{		
				ISanphamthuhoi sanpham = null;
				int m=0;
				while(m < kmmasp.length)
				{
					sanpham= new Sanphamthuhoi();
					sanpham.setSPId(kmidsp[m]);
					sanpham.setMasanpham(kmmasp[m]);
					sanpham.setTensanpham(kmtensp[m]);
					sanpham.setSoluong(kmsoluong[m]);
					sanpham.setKhoId(kmkho_fk[m]);
					sanpham.setKenhId(kmkbh_fk[m]);
					sanpham.setDonvitinh(kmdonvitinh[m]);
					String[] solo = request.getParameterValues(kmidsp[m]+".kmsolo");
					String[] soluonglo = request.getParameterValues(kmidsp[m]+".kmsoluong");
					String[] ngayhethan = request.getParameterValues(kmidsp[m]+".kmngayhethan");
					String[] kmsoluongxk = request.getParameterValues(kmidsp[m]+".kmsoluongxk");
					List<ISanphamSoLo> listlo=new ArrayList<ISanphamSoLo>();
					double soluongth=Double.parseDouble(kmsoluong[m]);
					double sumsoluonglo=0;
					for(int j=0;j<solo.length;j++){
						ISanphamSoLo lo=new SanphamSolo();
					
						lo.setSolo(solo[j]);
						
						lo.setNgayhethang(ngayhethan[j]);
						listlo.add(lo);
						double soluonglo1=0;
						try{
							soluonglo1 =Double.parseDouble(soluonglo[j]);
						}catch(Exception er ){
							
						}
						lo.setSoluong(""+Math.round(soluonglo1));
						sumsoluonglo=sumsoluonglo+soluonglo1;
						lo.setSoluongXK(kmsoluongxk[j]);
					}
					if(soluongth!=sumsoluonglo){
						err=err+ "  \n Số lượng trong lô của sản phẩm: "+ tensp[m]+" chưa hợp lệ.";
					}
					sanpham.SetSpLoList(listlo);
					m++;
					spkmList.add(sanpham);
				}
			}
			obj.setSpkmList(spkmList);
			
			//END Sna pham khuyen mai
			obj.setMessage(err);
	    if(action.equals("Save")|| action.equals("Chot")){
	    	if(err.length() >0){
	    		//Do Nothing
	    		obj.setMessage(err);
	    		obj.setUserId(userId);

	    		session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);
	    		
				response.sendRedirect("/TraphacoHYERP/pages/Distributor/PhieuThuHoiUpdate.jsp");
	    	}else{
	    		String ischot="0";
	    		if(action.equals("Chot")){
	    			ischot="1";
	    		}
	    		if(!obj.UpdatePth(ischot)){
	    			
		    		obj.setUserId(userId);

		    		session.setAttribute("obj", obj);  	
					session.setAttribute("userId", userId);
		    		
					response.sendRedirect("/TraphacoHYERP/pages/Distributor/PhieuThuHoiUpdate.jsp");
	    		}else{
	    			IPhieuthuhoiList  objlist = new PhieuthuhoiList();
	    			objlist.setUserId(userId);
	    		    int items = 50;
	    		    int splittings = 20;
	    		    objlist.setItems(items);
	    		    objlist.setSplittings(splittings);
	    		    objlist.init("");
	    			session.setAttribute("obj", objlist);	
	    			String nextJSP = "/TraphacoHYERP/pages/Distributor/PhieuThuHoi.jsp";
	    			response.sendRedirect(nextJSP);
	    		}
	    	}
	    }
    		
	    
		}
	}

	

}
