package geso.traphaco.erp.servlets.NhomNhanVien;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.NhomNhanVien.IErpNhomNhanVien;
import geso.traphaco.erp.beans.NhomNhanVien.IErpNhomNhanVienList;
import geso.traphaco.erp.beans.NhomNhanVien.imp.ErpNhomNhanVien;
import geso.traphaco.erp.beans.NhomNhanVien.imp.ErpNhomNhanVienList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class ErpNhomNVUpdateSvl
 */
@WebServlet("/ErpNhomNVUpdateSvl")
public class ErpNhomNVUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
	//private dbutils db;
    public ErpNhomNVUpdateSvl() {
        super();

    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		dbutils db = new dbutils();
		Utility util = new Utility();
	
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

	    String ctyId = (String)session.getAttribute("congtyId"); 
	    String ctyTen = (String)session.getAttribute("congtyTen");
	    
		out.println(userId);
    
		if (userId.length()==0)
			userId = request.getParameter("userId");
    	    
		String id = util.getId(querystring);
      	
		String query = "select a.pk_seq,isnull(a.TEN,'') as ten , a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from ErpNhomNV a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.pk_seq='" + id + "'";
	
		System.out.println("SYS OUT :"+query);
		ResultSet rs =  db.get(query);
		try{
			rs.next();
			String[] param = new String[10];
			param[0]= id;
			param[1]= rs.getString("diengiai");	
			param[2]= rs.getString("trangthai");
			param[3]= rs.getString("ngaytao");
			param[4]= rs.getString("ngaysua");
			param[5]= rs.getString("nguoitao");
			param[6]= rs.getString("nguoisua");
			param[7]= rs.getString("ten");
    	
    	
			IErpNhomNhanVien nNccBean = new ErpNhomNhanVien(param);
			nNccBean.setCtyId(ctyId);
			nNccBean.setCtyTen(ctyTen);
	    
			nNccBean.UpdateRS();
			session.setAttribute("nNccBean", nNccBean);
   		
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomNVUpdate.jsp";
			if(querystring.contains("display"))
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomNVDisplay.jsp";
			response.sendRedirect(nextJSP);
   		
		}catch (java.sql.SQLException e){
			out.println(e.toString());
		}
   
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		IErpNhomNhanVien nNccBean = new ErpNhomNhanVien();
		//this.db = new dbutils();
		//Get data from NhacungcapUpdate.jsp	
		String userId = request.getParameter("userId");
	    String ctyId = (String)session.getAttribute("congtyId"); 
	    String ctyTen = (String)session.getAttribute("congtyTen");
	
		String id = request.getParameter("nkhId");
		if(id == null){
			id = "";
		}
		nNccBean.setId(id);
		nNccBean.setCtyId(ctyId);
		nNccBean.setCtyTen(ctyTen);
		
		String ten = request.getParameter("ten");
		nNccBean.setTen(ten);

		String diengiai = request.getParameter("diengiai");
		nNccBean.setDiengiai(diengiai);

		String vungId = request.getParameter("vungId");
		nNccBean.setVungId(vungId);
	
		String kvId = request.getParameter("kvId");	
		nNccBean.setKvId(kvId);

		String nppId= request.getParameter("nppId");
		nNccBean.setNppId(nppId);
	
		String ngaytao = getDateTime();
		nNccBean.setNgaytao(ngaytao);
	
		String ngaysua = ngaytao;
		nNccBean.setNgaysua(ngaysua);
	
		String nguoitao = userId;
		nNccBean.setNguoitao(userId);
	
		String nguoisua = nguoitao;
		nNccBean.setNguoisua(nguoisua);
	
		String trangthai;
		if(request.getParameter("trangthai")!= null)
			trangthai = "1";
		else
			trangthai = "0";
		nNccBean.setTrangthai(trangthai);
		
		boolean error = false;
		if (ten.trim().length()> 0)
			nNccBean.setTen(ten);
		else{
			nNccBean.setMessage("Vui lòng nhập vào tên Nhóm ");
			error = true;
		}

		String[] ncc = request.getParameterValues("ncc");
		System.out.println("ncc" + ncc);
		nNccBean.setNcc(ncc);
	
		String action = request.getParameter("action");

		out.println(action);
	
		if (action.equals("filter") || error){		
			nNccBean.UpdateRS();
			session.setAttribute("nNccBean", nNccBean);
			session.setAttribute("userId", userId);
			
			String nextJSP;
			if (id.length()>0){
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomNVUpdate.jsp";
			}else{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomNVNew.jsp";
			}
			response.sendRedirect(nextJSP);    		
		}else{
			session.setAttribute("userId", nguoitao);

			if (action.equals("new")){
				//Check có cùng TKKT không
			   /* if(nNccBean.CheckTKKT()== false)
			    {
				     nNccBean.UpdateRS();    
				     session.setAttribute("nNccBean", nNccBean);
				      
				     String nextJSP = "/TraphacoERP/pages/Erp/NhomNhaCungCapCNNew.jsp";
				     response.sendRedirect(nextJSP);
			    }
			    else
			    {*/
					if (!nNccBean.saveNewNNcc()){
						nNccBean.UpdateRS();				
						session.setAttribute("nNccBean", nNccBean);
	    		
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomNVNew.jsp";
						response.sendRedirect(nextJSP);
					}
					else{
						IErpNhomNhanVienList obj = new ErpNhomNhanVienList();
						List<IErpNhomNhanVien> ncclist = new ArrayList<IErpNhomNhanVien>(); 
			    
						getNccBeanList(ncclist,"");
			    
					// 	Save data into session
						obj.setNccList(ncclist);
			    
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomNV.jsp";
						response.sendRedirect(nextJSP);
				
					}
			    //}
				
			}else{
				//Check có cùng TKKT không
//			    if(nNccBean.CheckTKKT()== false)
//			    {
//			    	nNccBean.UpdateRS();    
//				     session.setAttribute("nNccBean", nNccBean);
//				      
//				     String nextJSP = "/TraphacoERP/pages/Erp/NhomNhaCungCapCNUpdate.jsp";
//				     response.sendRedirect(nextJSP);
//			    }
//			    else
//			    {
					if (!nNccBean.updateNNcc()){
						nNccBean.UpdateRS();				
						session.setAttribute("nNccBean", nNccBean);
	    		
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomNVUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else{
						System.out.println("ncc HERE");
						IErpNhomNhanVienList obj = new ErpNhomNhanVienList();
						List<IErpNhomNhanVien> ncclist = new ArrayList<IErpNhomNhanVien>(); 
			    
						getNccBeanList(ncclist,"");
			    
					// 	Save data into session
						obj.setNccList(ncclist);
			    
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomNV.jsp";
						response.sendRedirect(nextJSP);
				
					}
			    //}
			}
		}
	
	}   	  	    
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	private void  getNccBeanList(List<IErpNhomNhanVien> nkhlist, String search){	
		String query;
    
		if (search.length() > 0){
			query = search;
		}else{
			query = "select a.pk_seq,isnull(a.ten,'') as ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from ErpNhomNV a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ order by diengiai";
		}
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		try{	
			String[] param = new String[11];
			IErpNhomNhanVien nNccBean;
			while (rs.next()){	    			
				param[0]= rs.getString("pk_seq");
				param[1]= rs.getString("diengiai");				
				param[2]= rs.getString("trangthai");
				param[3]= rs.getString("ngaytao");
				param[4]= rs.getString("ngaysua");
				param[5]= rs.getString("nguoitao");
				param[6]= rs.getString("nguoisua");	
				param[7]= rs.getString("ten");	
			
				nNccBean = new ErpNhomNhanVien(param);					
				nkhlist.add(nNccBean);
			}
			rs.close();
		}catch(java.sql.SQLException e){
			
		}
		db.shutDown();
	}

}
