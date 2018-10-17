package geso.traphaco.erp.servlets.lenhsanxuatgiay;

import geso.dms.distributor.util.Utility;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpYeucaunguyenlieu;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpYeucaunguyenlieuList;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IYeucau;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpYeucaunguyenlieu;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpYeucaunguyenlieuList;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.Yeucau;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.Sanpham;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.startup.SetAllPropertiesRule;

public class ErpYeucaunguyenlieugiayUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	PrintWriter out;
    public ErpYeucaunguyenlieugiayUpdateSvl() 
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
		}
		else
		{
			session.setMaxInactiveInterval(30000);
			this.out = response.getWriter();
			Utility util = new Utility();
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 
		    String id = util.getId(querystring);  
		    
		    
		    //Chỉnh sửa
		    IErpYeucaunguyenlieuList obj = new ErpYeucaunguyenlieuList();
		    
		    String tungay ="";
		    try{
		    tungay = util.getCat(querystring, 2, 1);
		    // System.out.println("tu ngay: "+ tungay);
		    }
		    catch(Exception er)
		    {
		    	tungay="";
		    }
		    
		    String denngay="";
		    try{
		    	denngay = util.getCat(querystring, 3, 1);
			    System.out.println("den ngay: "+ denngay);
			}
			catch(Exception er)
			{
			    	denngay="";
			}
			
			String trangthai="";
			 try{
			    	trangthai = util.getCat(querystring, 4, 1);
				    System.out.println("trangthai: "+ trangthai);
			 }
			 catch(Exception er)
			 {
					trangthai="";
			 }
			 
			String sochungtu="";
			 try{
				 	sochungtu = util.getCat(querystring, 5, 1);
				    System.out.println("trangthai: "+ sochungtu);
			 }
			 catch(Exception er)
			 {
				 sochungtu="";
			}
			 
			 String sophieuyeucau="";
			 try{
				 sophieuyeucau = util.getCat(querystring, 6, 1);
				    System.out.println("sophieuyeucau: "+ sophieuyeucau);
			 }
			 catch(Exception er)
			 {
				 sophieuyeucau="";
			}
			 
			if(tungay!="")
				obj.setTungayTao(tungay);
			
			if(denngay!="")
				obj.setDenngayTao(denngay);
			
			if(trangthai!="")
				obj.setTrangthai(trangthai);
			
			if(sochungtu!="")
				obj.setSochungtu(sochungtu);
			
			if(sophieuyeucau!="")
				obj.setSoyeucau(sophieuyeucau);
			
			session.setAttribute("obj", obj);
			
		    //Hết chỉnh sửa
		    
		    IErpYeucaunguyenlieu lsxBean = new ErpYeucaunguyenlieu(id);
		    lsxBean.setUserId(userId); 
		    lsxBean.SetCtyId((String)session.getAttribute("congtyId"));
		    String task = request.getParameter("task");
			if(task == null)
				task = "";
			if(task.equals("chuyenNL"))
				lsxBean.setIschuyenNL("1");

			lsxBean.init();
		
	        String nextJSP = "";
	        if(task.equals("chuyenNL"))
	        {
		        if(request.getQueryString().indexOf("display") >= 0 ) 
		        {
		        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenNguyenLieuGiayDisplay.jsp";
		        }
		        else
		        {
		        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenNguyenLieuGiayUpdate.jsp";
		        }
	        }
	        else
	        {
	        	if(request.getQueryString().indexOf("display") >= 0 ) 
		        {
		        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiayDisplay.jsp";
		        }
		        else
		        {
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiayUpdate.jsp";
		        }
	        }
	        
	        session.setAttribute("lsxBean", lsxBean);
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
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpYeucaunguyenlieu lsxBean;
				
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpYeucaunguyenlieu("");
		    }
		    else
		    {
		    	lsxBean = new ErpYeucaunguyenlieu(id);
		    }
	
		    lsxBean.setUserId(userId);
		    lsxBean.SetCtyId((String)session.getAttribute("congtyId"));
		    String task = request.getParameter("task");
		    System.out.println("task: "+ task);
			if(task == null)//cai nay dung cho tao moi IsChuyen=0--------------------------
				task = "";
			if(task.equals("chuyenNL"))// cai nay dung cho update--------------------------
				lsxBean.setIschuyenNL("1");
		    
			 String action = request.getParameter("action");
			 
			
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngayyeucau"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String lydo = util.antiSQLInspection(request.getParameter("lydo"));
		    if(lydo == null)
		    	lydo = "";
		    lsxBean.setLydoyeucau(lydo);
		    
		    String nhamayId = util.antiSQLInspection(request.getParameter("nhamayId"));
			if (nhamayId == null)
				nhamayId = "";				
			lsxBean.setKhoXuatId(nhamayId);
			
		    String khoxuatId = util.antiSQLInspection(request.getParameter("khoxuatId"));
			if (khoxuatId == null)
				khoxuatId = "";				
			lsxBean.setKhoXuatId(khoxuatId);
	    	
			String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "";				
			lsxBean.setTrangthai(trangthai);
			 
	    	String[] lsxIds = request.getParameterValues("lsxIds");
	    	if(lsxIds != null)
	    	{
	    		String lsx = "";
				for(int i = 0; i < lsxIds.length; i++)
				{
					lsx += lsxIds[i] + ",";
				}
				
				if(lsx.trim().length() > 0)
				{
					lsx = lsx.substring(0, lsx.length() - 1);
				}
	    		
				lsxBean.setLsxIds(lsx);
	    	}
	    	String[] idnguyenlieu=request.getParameterValues("idnguyenlieu");
			String[] manguyenlieu = request.getParameterValues("manguyenlieu");
			String[] tennguyenlieu = request.getParameterValues("tennguyenlieu");
			String[] soluongyeucau = request.getParameterValues("soluongyeucau");
			String[] soluongnhan = request.getParameterValues("soluongnhan"); 
			String[] ghichuct = request.getParameterValues("ghichuct"); 
			
			List<IYeucau> spList = new ArrayList<IYeucau>();
			if(tennguyenlieu != null)
			{	
				IYeucau yeucau = null;
				for(int m = 0; m < tennguyenlieu.length; m++)
				{	
					if(tennguyenlieu[m] != "")
					{	
						yeucau = new Yeucau();
						yeucau.setId(idnguyenlieu[m]);
						yeucau.setMa(manguyenlieu[m]);
						yeucau.setTen(tennguyenlieu[m]);
						yeucau.setSoluongYC(soluongyeucau[m]);
						yeucau.setSoluongNhan(soluongnhan[m]);
						yeucau.setGhiChu(ghichuct[m]);
						//----pop up ----//
						
						List<ISpDetail> spConList = new ArrayList<ISpDetail>();
						ISpDetail spCon =  null;
						String[] soluongchuyen= request.getParameterValues(idnguyenlieu[m]+".soluong");
						String[] ghichu= request.getParameterValues(idnguyenlieu[m]+".ghichu");
						String[] maso= request.getParameterValues(idnguyenlieu[m]+".maso");
						
						if(soluongchuyen!=null && ghichu !=null){
							for( int k = 0; k < soluongchuyen.length; k++){
								if(soluongchuyen[k]!="")
								{
								 
									spCon = new SpDetail(soluongchuyen[k], ghichu[k]);
									spCon.setSoluong(soluongchuyen[k]);
									spCon.setGhiChu(ghichu[k]);
									spCon.setMaso(maso[k]);
									spConList.add(spCon);
								}
							}
						}
						yeucau.setSpDetailList(spConList);
						
						//---- hết pop- up----/
						
						spList.add(yeucau);		
					}				
				}
			
				lsxBean.setSpList(spList);
			}	
			
			
		   
		    

		 
		    
			if(action.equals("save"))
			{	
				//System.out.println("IschuyenNL" +lsxBean.getIschuyenNL());
				if(id == null)// cai nay dung cho tao moi
				{
					
					if(!lsxBean.createYcnl())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiayNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpYeucaunguyenlieuList obj = new ErpYeucaunguyenlieuList();
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
				
			    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiay.jsp");
					}
				}
				else// cai nay dung cho cap nhat
				{	
				/*	lsxBean.setIschuyenNL("0");*/
				
					if(lsxBean.getIschuyenNL().equals("0"))//khong bao gio vao dday vi neu if(task.equals("chuyenNL")) lsxBean.setIschuyenNL("1");// cai nay dung cho update--------------------------						
					{ 	
				
					
						if(!lsxBean.updateYcnl())
						{
						
							lsxBean.createRs();
							session.setAttribute("lsxBean", lsxBean);
		    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiayUpdate.jsp";
		    				response.sendRedirect(nextJSP);
						}
						else
						{
							
							
							IErpYeucaunguyenlieuList obj = new ErpYeucaunguyenlieuList();
							
						    obj.setUserId(userId);
						    
						    obj.init("");
							session.setAttribute("obj", obj);							
							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiay.jsp";
							response.sendRedirect(nextJSP);
						}
					}
					else
					{
					//	System.out.println("vào đây 5:" + lsxBean.getIschuyenNL());
						
						if(!lsxBean.chuyenNL())
						{
							lsxBean.createRs();
							session.setAttribute("lsxBean", lsxBean);
		    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenNguyenLieuGiayUpdate.jsp";
		    				response.sendRedirect(nextJSP);
						}
						else
						{
							IErpYeucaunguyenlieuList obj = new ErpYeucaunguyenlieuList();
							obj.setIschuyenNL("1");
						    obj.setUserId(userId);
						    obj.init("");
						    
							session.setAttribute("obj", obj);							
							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenNguyenLieuGiay.jsp";
							response.sendRedirect(nextJSP);
						}
					}
				}
			}
			else if(action.equals("chotyeucau")){
				 
				System.out.println("vào đây rồi nè: "+ action);
				System.out.println("getIschuyenNL() "+ lsxBean.getIschuyenNL());
				
				
				    
				if(lsxBean.getIschuyenNL().equals("0"))
				{
					if(!lsxBean.chotYcnl())
					{
						lsxBean.init();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiayUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						//System.out.println("vào đây rồi nè1 : "+ action);
						//Chỉnh sửa
						IErpYeucaunguyenlieuList obj = new ErpYeucaunguyenlieuList();					
						
						String trangtthai1=request.getParameter("trangthai1");	
						obj.setTrangthai(trangtthai1);				 
						
						String tungay = util.antiSQLInspection(request.getParameter("tungay"));
						obj.setTungayTao(tungay);
						    						    
						String denngay=util.antiSQLInspection(request.getParameter("denngay"));
						obj.setDenngayTao(denngay);
						 						
						String sochungtu=util.antiSQLInspection(request.getParameter("sochungtu"));
						obj.setSochungtu(sochungtu);
						
						String sophieuyeucau=util.antiSQLInspection(request.getParameter("sophieuyeucau")); 
						obj.setSoyeucau(sophieuyeucau);
							//session.setAttribute("obj", j);
						   
						//hết chỉnh sửa
					    obj.setUserId(userId);
					    
					    //viết câu search
					    
					    String  query = "select  isnull( phieuyeucau_fk,0) as phieuyeucau, ISNULL(A.DUYET,'0') AS DUYET , a.PK_SEQ, a.trangthai, a.ngayyeucau, a.lydo, NV.TEN as nguoitao, " +
						" a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
						 " from ERP_YEUCAUNGUYENLIEU a   " +
						 " inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						 " inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ  " +
						 " where 1=1   ";
					    
					    if(trangtthai1.length()>0)
					    	query += " and a.TrangThai = '" + trangtthai1 + "'";
					    if(tungay.length()>0)
					    	query += " and a.Ngayyeucau >= '" + tungay + "'";
					    if(denngay.length()>0)
					    	query += " and a.Ngayyeucau <= '" + denngay + "'";
					    if(sochungtu.length()>0)
					    	query += " and  cast( a.pk_seq as nvarchar(10))  like '%" + sochungtu + "%'";
					    if(sophieuyeucau.length()>0)
					    	query += " and  cast( a.phieuyeucau_fk as nvarchar(10))  like '%" + sophieuyeucau + "%'";
					    
					    obj.init(query);
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiay.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("chotForm"))
				{
				  
					if(!lsxBean.chotYcnl())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiayUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpYeucaunguyenlieuList obj = new ErpYeucaunguyenlieuList();
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiay.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					lsxBean.createRs();
					
					session.setAttribute("lsxBean", lsxBean);
					
					String nextJSP = "";
					
					if(lsxBean.getIschuyenNL().equals("0"))
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiayNew.jsp";
						if(id != null)
							nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiayUpdate.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenNguyenLieuGiayUpdate.jsp";
					}
					
					response.sendRedirect(nextJSP);
				}
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
