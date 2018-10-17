package geso.traphaco.center.servlets.congtacvien;

import geso.traphaco.center.beans.congtacvien.imp.*;
import geso.traphaco.center.beans.congtacvien.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class CongtacvienUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;  
    
    public CongtacvienUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		ICongtacvien ddkdBean;
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	

	    ddkdBean = new Congtacvien(id);
        ddkdBean.setUserId(userId);
        session.setAttribute("ddkdBean", ddkdBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/CongTacVienUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		ICongtacvien ddkdBean;
		this.out = response.getWriter();
		Utility util = new Utility();
		
	    String id = util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	ddkdBean = new Congtacvien("");
	    }else{
	    	ddkdBean = new Congtacvien(id);
	    }
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		ddkdBean.setUserId(userId);
		boolean error = false;
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		if (ma == null)
			ma = "";				
    	ddkdBean.setMa(ma);
    	
    	String ten = util.antiSQLInspection(request.getParameter("ten"));
		if (ten == null)
			ten = "";				
    	ddkdBean.setTen(ten);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	ddkdBean.setTrangthai(trangthai);
    	
    	String diachi = util.antiSQLInspection(request.getParameter("DiaChi"));
		if (diachi == null)
			diachi = "";
		ddkdBean.setDiachi(diachi);
				
		String dienthoai = util.antiSQLInspection(request.getParameter("DienThoai"));
		if (dienthoai == null)
			dienthoai = "";
		ddkdBean.setSodt(dienthoai);
		
		String gioitinh = util.antiSQLInspection(request.getParameter("gioitinh"));
		if (gioitinh == null)
			gioitinh = "";
		ddkdBean.setGioitinh(gioitinh);
		
		String chuyenkhoa = util.antiSQLInspection(request.getParameter("chuyenkhoa"));
		if (chuyenkhoa == null)
			chuyenkhoa = "";
		ddkdBean.setChuyenkhoa(chuyenkhoa);
		
		String chucvu = util.antiSQLInspection(request.getParameter("chucvu"));
		if (chucvu == null)
			chucvu = "";
		ddkdBean.setChucvu(chucvu);
		
		String ngaysinh = util.antiSQLInspection(request.getParameter("ngaysinh"));
		if (ngaysinh == null)
			ngaysinh = "";
		ddkdBean.setNgaysinh(ngaysinh);
		String kbhTen =  util.antiSQLInspection(request.getParameter("kbhTen"));
		if(kbhTen == null)
			kbhTen = "";
		ddkdBean.setKbhId(kbhTen);
		String lichkham = util.antiSQLInspection(request.getParameter("lichkham"));
		if (lichkham == null)
			lichkham = "";
		ddkdBean.setLichkham(lichkham);
		
		String sothich = util.antiSQLInspection(request.getParameter("sothich"));
		if (sothich == null)
			sothich = "";
		ddkdBean.setSothich(sothich);
		String[] khma = request.getParameterValues("KhMa");
		String makh = "";
		for(int i = 0; i < khma.length - 1; i++)
		{
			if(khma != null)
				if(khma.length > 0)
					makh += "'"+khma[i]+"'"+",";
			for(int j = i + 1; j < khma.length; j++)
			{
				if(khma[i].trim().length() > 0 && khma[j].trim().length() > 0 )
				{
					if( khma[i].trim().equals(khma[j].trim()) )
					{
						ddkdBean.setMessage("Khách hàng mã: "+khma[i]+" bị trùng !");
						error = true;
						break;	
					}
				}
			}
		}	
		dbutils db = new dbutils();
		if(error == false && makh.length() > 0)
		{
			makh = makh.substring(0, makh.length() - 1);
			String query = "select pk_seq from khachhang where ma in ("+makh+")";
			System.out.println("Query kh theo Ma :"+query);
			ResultSet rs = db.get(query);
			String khId = "";
			if(rs != null)
			{
				try {
					while(rs.next())
					{
						khId+= rs.getString("pk_seq")+",";
					}
					rs.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
					
				}
				
			}
			
			if (khId.length() > 0 )
			{
				if(khId.trim().length() > 0)
					khId = khId.substring(0, khId.length() - 1);
				ddkdBean.setKhachhangId(khId);
			}
			
			System.out.println("---KHACH HANG: " + khId );
		}
		String diabanId = util.antiSQLInspection(request.getParameter("diabanId"));
		if(diabanId==null)
			diabanId="";
		ddkdBean.setDiabanId(diabanId);
		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if(nppId==null)
			nppId="";
		ddkdBean.setNppId(nppId);
		
		String tdvId = util.antiSQLInspection(request.getParameter("tdvId"));
		if(tdvId==null)
			tdvId="";
		ddkdBean.setTDVId(tdvId);
		
		String tinhthanhId = util.antiSQLInspection(request.getParameter("tinhthanhId"));
		if(tinhthanhId==null)
			tinhthanhId="";
		ddkdBean.setTinhthanhId(tinhthanhId);
		
		String quanhuyenId = util.antiSQLInspection(request.getParameter("quanhuyenId"));
		if(quanhuyenId==null)
			quanhuyenId="";
		ddkdBean.setQuanhuyenId(quanhuyenId);
		
		String ngaysua = getDateTime();
    	ddkdBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		ddkdBean.setNguoisua(nguoisua);
    	
		
		
		
		if (ma.trim().length()== 0){
			ddkdBean.setMessage("Vui Long Nhap Ma");
			error = true;
		}
		
		if (ten.trim().length()== 0){
			ddkdBean.setMessage("Vui Long Nhap Ten");
			error = true;
		}
		
		String action = request.getParameter("action");
		
 		String nextJSP = "";
	    if (!error)
	    {
	    	if(action.equals("save"))
	    	{
	    		ICongtacvienList obj = new CongtacvienList();
	    		obj.setItems(10);
			    obj.setCrrSplitting(15);
			    obj.setNxtApprSplitting(1);
	    		if (id == null)
	    		{
	    			if (!(ddkdBean.CreateDdkd()))
	    			{				
	    				session.setAttribute("ddkdBean", ddkdBean);
	    				ddkdBean.setUserId(userId);
	    				nextJSP = "/TraphacoHYERP/pages/Center/CongTacVienNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else
	    			{
	    				obj.setUserId(userId);
	    				obj.init("");	

	    				session.setAttribute("obj", obj);
						
	    				nextJSP = "/TraphacoHYERP/pages/Center/CongTacVien.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    		else{
	    			if (!(ddkdBean.UpdateDdkd()))
	    			{			
	    				session.setAttribute("ddkdBean", ddkdBean);
	    				nextJSP = "/TraphacoHYERP/pages/Center/CongTacVienUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else
	    			{
	    				//IDaidienkinhdoanhList obj = new DaidienkinhdoanhList();
	    				
	    				obj.setUserId(userId);
	    				obj.init("");	
	    				
	    				session.setAttribute("obj", obj);
						
	    				nextJSP = "/TraphacoHYERP/pages/Center/CongTacVien.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	}
	    	else
	    	{
	    		ddkdBean.createRS();
	    		ddkdBean.setUserId(userId);
	    		session.setAttribute("ddkdBean", ddkdBean);
	    		
	    		//String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/TraphacoHYERP/pages/Center/CongTacVienNew.jsp";
	    		}else{
	    			nextJSP = "/TraphacoHYERP/pages/Center/CongTacVienUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	    else
	    {
			ddkdBean.createRS();
			
    		ddkdBean.setUserId(userId);
    		session.setAttribute("ddkdBean", ddkdBean);
		
    		//String nextJSP;
    		if (id == null){			
    			nextJSP = "/TraphacoHYERP/pages/Center/CongTacVienNew.jsp";
    		}else{
    			nextJSP = "/TraphacoHYERP/pages/Center/CongTacVienUpdate.jsp";   						
    		}
    		response.sendRedirect(nextJSP);	
	    }
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
