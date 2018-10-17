package geso.traphaco.erp.servlets.doiquycach;

import geso.traphaco.erp.beans.doiquycach.IDoiquycach;
import geso.traphaco.erp.beans.doiquycach.IDoiquycachList;
import geso.traphaco.erp.beans.doiquycach.ISpDoiquycach;
import geso.traphaco.erp.beans.doiquycach.imp.Doiquycach;
import geso.traphaco.erp.beans.doiquycach.imp.DoiquycachList;
import geso.traphaco.erp.beans.doiquycach.imp.SpDoiquycach;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;
import geso.traphaco.erp.db.sql.dbutils;
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


public class DoiquycachUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
       public DoiquycachUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    String id = util.getId(querystring);  
	    String action = util.getAction(querystring);
	    
	    IDoiquycach obj = new Doiquycach(id); 
	    obj.setCtyId(ctyId);
	    
	    obj.setUserId(userId);
   		
	     
	    if(action.equals("delete"))
	    { 
	    	obj.Xoa();
	    	IDoiquycachList obj1 = new DoiquycachList();
	    	obj1.setCtyId(ctyId);
	        obj1.init("");
		    session.setAttribute("obj",obj1);
		    session.setAttribute("spId1", "");
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Doiquycach.jsp");
	    }
	    else if(action.equals("finish")){
	    	 obj.Hoantat();
	    	 String msg=obj.getmsg();
	    	 obj.DbClose();
	    	 
	    	IDoiquycachList obj1 = new DoiquycachList();
	    	obj1.setCtyId(ctyId);
	        obj1.init("");
	        obj1.setMsg(msg);
	        
		    session.setAttribute("obj",obj1);
		    session.setAttribute("spId1", "");
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Doiquycach.jsp");		    	
	    }else if(action.equals("update")){	
	    	obj.init();
	    	session.setAttribute("obj",obj);
	    	session.setAttribute("spId1", obj.getSpId1());
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/DoiquycachUpdate.jsp");
	    }else{
	    	obj.init();
	    	session.setAttribute("obj",obj);
	    	session.setAttribute("spId1", "");
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/DoiquycachDisplay.jsp");
	    	
	    }
	}
    
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
 	    
 	    IDoiquycach obj = new Doiquycach();		
 		HttpSession session = request.getSession();
 		Utility util = new Utility();
 		
 		String ctyId  = (String)session.getAttribute("congtyId");
 		obj.setCtyId(ctyId);
  
 	    String userId = util.antiSQLInspection(request.getParameter("userId"));
 	    if(userId == null)
 	    	userId = "";
 	    obj.setUserId(userId);
 	    
 	    String Id = util.antiSQLInspection(request.getParameter("Id"));
 	    if(Id ==null)
 		   Id ="";
 	    obj.setId(Id);
 	   
 	    String khoId = util.antiSQLInspection(request.getParameter("khoId"));
 	    if(khoId == null)
 	    	khoId = "";
 	    obj.setKhoId(khoId);
 	    
 	   String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
	    if(ghichu == null)
	    	ghichu = "";
	    obj.setghichu(ghichu);
	    
	    
	    String xuatkho_doiquycach = util.antiSQLInspection(request.getParameter("xuatkho_doiquycach")); // dùng cập nhật trạng thai xuất kho có nội dung = Xuất đổi quy cách     
	    if(xuatkho_doiquycach == null)
	    	xuatkho_doiquycach = "";
	    obj.set_xuatkho_doiquycach(xuatkho_doiquycach);
	    
	    
	    String xuatkhoId_DQC = util.antiSQLInspection(request.getParameter("phieuxuatkho_dqc")); // số phiếu bên nhận hàng để đổi quy cách   
	    if(xuatkhoId_DQC == null)
	    	xuatkhoId_DQC = "";
	    obj.set_xuatkhoId_DQC(xuatkhoId_DQC);
	    
	    
	    
	    String chiphi1 = util.antiSQLInspection(request.getParameter("chiphi1"));
	    if(chiphi1 == null)
	    	chiphi1 = "0";
	    obj.setChiphi1(chiphi1.replace(",", ""));
	    
	    
	    String chiphikho = util.antiSQLInspection(request.getParameter("chiphikho"));
	    if(chiphikho == null)
	    	chiphikho = "0";
	    obj.setChiphikho(chiphikho.replace(",", ""));
	    
   
 	    session.setAttribute("lspId", obj.getLspId());
 	    session.setAttribute("spId1", obj.getSpId1());
 	    
 	    String ngay = util.antiSQLInspection(request.getParameter("ngay"));
 	    if(ngay == null)
 	    	ngay = "";
 	    obj.setNgay(ngay);
 	    
 	    
 	   String[] spId = request.getParameterValues("spId");
 	   String[] spma=request.getParameterValues("spMa");
 	   String[] spTen1=request.getParameterValues("spTen1");
 	   String[] dvt1=request.getParameterValues("dvt1");
 	 
 	   String[] dongia1=request.getParameterValues("dongia1");
 	   
 	  String[] isreload=request.getParameterValues("isreload");
 	  
 	  
 	  
 	  //----------- SP TRƯỚC ĐỔI QUY CÁCH 
 	  
 	  List<ISpDoiquycach> spDoiquycachlist = new ArrayList<ISpDoiquycach>(); 
 	  if(spId !=null){
 	  
 		  for( int i=0;i< spId.length;i++ ){
 			  if(spId[i]!=null && spId[i].length() > 0){
 				ISpDoiquycach spdqc=new SpDoiquycach();
	 			spdqc.setSpId1(spId[i]);
	 			spdqc.setSpTen1(spTen1[i]);
	 			spdqc.setMa(spma[i]);
	 			spdqc.setdonvitinh(dvt1[i]);
	 			spdqc.setreload_sp(isreload[i]);
	 			double dongia_=0;
	 			try{
	 			 dongia_=Double.parseDouble(dongia1[i].replaceAll(",",""));
	 			 
	 			}catch(Exception err){
	 				 
	 			}
	 			spdqc.setDongia1(dongia_);
	 			double soluong_=0;
	 			 
	 			
		 	    String[] khuId = request.getParameterValues("khuidct1"+spId[i]);
				String[] tenkhu = request.getParameterValues("tenkhuct1"+spId[i]);
				String[] solo = request.getParameterValues("soloct1"+spId[i]);
				String[] slg = request.getParameterValues("soluongct1"+spId[i]);
				String[] ngaybatdau = request.getParameterValues("ngaybatdauct1"+spId[i]);
				String[] ngaysanxuat = request.getParameterValues("ngaysanxuatct1"+spId[i]);
				String[] ngayhethan = request.getParameterValues("ngayhethanct1"+spId[i]);
				String[] soluongton = request.getParameterValues("soluongtonct1"+spId[i]);
				
				List<ISpDetail> spConList = new ArrayList<ISpDetail>();
				ISpDetail spCon = null;
				int n = 0;
				double  totalslx=0;
				if(slg != null)
				{
					while(n < slg.length)
					{
								 double sllo=0;
								 try{
									 sllo= Double.parseDouble(slg[n]);
								 }catch(Exception er){
									 
								 }
								 soluong_=soluong_+sllo;
								 spCon = new SpDetail();
								 spCon.setId(spId[i]);
								 spCon.setSoluong(sllo+"");
								 if(khuId!=null){
									 spCon.setKhuId(khuId[n].trim());
									 spCon.setKhu(tenkhu[n].trim());
								 }
								 spCon.setSolo(solo[n].trim());
								 spCon.setNgaybatdau(ngaybatdau[n]);
								 spCon.setNgayhethan(ngayhethan[n]);
								 spCon.setNgayhethan(ngayhethan[n]);
								 spCon.setNgaysanxuat(ngaysanxuat[n]);
								 spCon.setSoluongton(soluongton[n]);
								 spConList.add(spCon);
							 
							 totalslx =totalslx+ sllo;
							n++;
						}
					}
				
				 
				spdqc.setSoluong1(soluong_);
				spdqc.setChiphi1(soluong_*dongia_);
				spdqc.setSpDetailList(spConList);
				spDoiquycachlist.add(spdqc);
 			  }
 		 }
 	  }
 	    obj.setSpDoiquycachlist(spDoiquycachlist);
 	    
 	    
 	   //------ SP NHẬN ĐỔI QUY CÁCH ----// 
 	    obj.setSpNhanDoiQuycach(request);
 	    
 	    
 	    
 	    String khuvuckhoid = util.antiSQLInspection(request.getParameter("khuvuckhoid"));
	    if(khuvuckhoid == null)
	    	khuvuckhoid = "";
	    obj.setKhuvucId(khuvuckhoid); 
 	   
 	    
 	    obj.createRs();
 	    String action = request.getParameter("action");
 	   
	    if(action.equals("save"))
	    {
	    
	    	obj.Tinhgiatri1();
	    	if(obj.getmsg().length() >0)
	    	{
	    		session.setAttribute("obj",obj);
	    	    response.sendRedirect("/TraphacoHYERP/pages/Erp/DoiquycachUpdate.jsp");	
	     	}
	    	else
	    	{
	    		
	    		if(obj.get_xuatkho_doiquycach().equals("1")){
	    			dbutils db = new dbutils();
	    			String query=" update erp_chuyenkho set is_doiquycach=1  where pk_seq="+obj.get_xuatkhoId_DQC();
	    			db.update(query);
	    			db.shutDown();
	    		}
	    		
	    		IDoiquycachList obj1 = new DoiquycachList();
	    		obj1.setCtyId(ctyId);
	    		obj1.init("");
	    		obj.DbClose();
	    		session.setAttribute("obj",obj1);
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Doiquycach.jsp");	    	

	    	}
	    }
	    else if(action.equals("reload_sp"))
	    {
	   	 	obj.Reload_Sp();
	    	session.setAttribute("khoId",khoId);
	    	session.setAttribute("obj",obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/DoiquycachUpdate.jsp");	
	    }else{
	    	
	    	 
	    	obj.ReLoad_DonGia();
	    	session.setAttribute("khoId",khoId);
	    	session.setAttribute("obj",obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/DoiquycachUpdate.jsp");	
	    }
   	
	 }

}
