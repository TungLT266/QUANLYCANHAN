package geso.traphaco.center.servlets.chuongtrinhtrungbay;

import geso.traphaco.center.beans.cttrungbay.*;
import geso.traphaco.center.beans.cttrungbay.imp.*;
import geso.traphaco.center.util.Utility;
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

public class CttrungbayUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out; 
	
       
    public CttrungbayUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);  	

	    ICttrungbay cttbBean = new Cttrungbay(id);
	    cttbBean.setUserId(userId);
        cttbBean.init();
        
        session.setAttribute("cttbBean", cttbBean);
        session.setAttribute("nsptbDien_giai", "");
    	session.setAttribute("nsptbTungay", "");
    	session.setAttribute("nsptbDenngay", "");
    	
        String nextJSP = "/TraphacoHYERP/pages/Center/ChuongTrinhTrungBayUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    ICttrungbay cttbBean;
	    if(id == null){  	
	    	cttbBean = new Cttrungbay("");
	    }else{
	    	cttbBean = new Cttrungbay(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		cttbBean.setUserId(userId);	       
    			
		String nhomtbId = util.antiSQLInspection(request.getParameter("nhomtbId"));
		if (nhomtbId == null)
			nhomtbId = "";
		cttbBean.setNhomTbId(nhomtbId);
		
		
		String scheme = util.antiSQLInspection(request.getParameter("scheme"));
		if (scheme == null)
			scheme = "";
		cttbBean.setScheme(scheme);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		cttbBean.setDiengiai(diengiai);
		
		String ngayTds = util.antiSQLInspection(request.getParameter("ngayTds"));
		if (ngayTds == null)
			ngayTds = "";
		cttbBean.setNgayTds(ngayTds);
		
		String ngayKtTds = util.antiSQLInspection(request.getParameter("ngayKtTds"));
		if (ngayKtTds == null)
			ngayKtTds = "";
		cttbBean.setNgayktTds(ngayKtTds);
		
		String ngayTb = util.antiSQLInspection(request.getParameter("ngayTb"));
		if (ngayTb == null)
			ngayTb = "";
		cttbBean.setNgayTb(ngayTb);
		
		String ngayKtTb = util.antiSQLInspection(request.getParameter("ngayKtTb"));
		if (ngayKtTb == null)
			ngayKtTb = "";
		cttbBean.setNgayktTb(ngayKtTb);
		
		String ngay1 = util.antiSQLInspection(request.getParameter("ngay1"));
		if (ngay1 == null)
			ngay1 = "";
		cttbBean.setNgayBddk(ngay1);
		
		String ngay2 = util.antiSQLInspection(request.getParameter("ngay2"));
		if (ngay2 == null)
			ngay2 = "";
		cttbBean.setNgayKtdk(ngay2);
		
		String type = util.antiSQLInspection(request.getParameter("type"));
		if (type == null)
			type = "1";
		cttbBean.setType(type);
		
		String solantt = util.antiSQLInspection(request.getParameter("solantt"));
		if (solantt == null)
			solantt = "";
		cttbBean.setSolantt(solantt);
		
		String ngansach = util.antiSQLInspection(request.getParameter("ngansach"));
		if (ngansach == null)
			ngansach = "";
		cttbBean.setNgansach(ngansach);
		
		String istinhds = util.antiSQLInspection(request.getParameter("isTinhds"));
		if (istinhds == null)
			istinhds = "0";
		cttbBean.setTinhdoanhso(istinhds);
				
		String ttb_diengiai = util.antiSQLInspection(request.getParameter("ttbDiengiai"));
		if (ttb_diengiai == null)
			ttb_diengiai = "";
		cttbBean.setTtb_diengiai(ttb_diengiai);
		
		String ttb_tungay = util.antiSQLInspection(request.getParameter("ttbTungay"));
		if (ttb_tungay == null)
			ttb_tungay = "";
		cttbBean.setTtb_tungay(ttb_tungay);
				
		String ttb_denngay = util.antiSQLInspection(request.getParameter("ttbDenngay"));
		if (ttb_denngay == null)
			ttb_denngay = "";
		cttbBean.setTtb_denngay(ttb_denngay);
		
		String nsptb_diengiai = util.antiSQLInspection(request.getParameter("nsptbDien_giai"));
		if (nsptb_diengiai == null)
			nsptb_diengiai = "";
		cttbBean.setNsptb_diengiai(nsptb_diengiai);
		session.setAttribute("nsptbDiengiai", nsptb_diengiai);
		
		String nsptb_tungay = util.antiSQLInspection(request.getParameter("nsptbTungay"));
		if (nsptb_tungay == null)
			nsptb_tungay = "";
		cttbBean.setNsptb_tungay(nsptb_tungay);
		session.setAttribute("dkkmTungay", convertDate(nsptb_tungay));
				
		String nsptb_denngay = util.antiSQLInspection(request.getParameter("nsptbDenngay"));
		if (nsptb_denngay == null)
			nsptb_denngay = "";
		cttbBean.setNsptb_denngay(nsptb_denngay);
		session.setAttribute("nsptbDenngay", convertDate(nsptb_denngay));
		
		/*
		String tratbIds = request.getParameter("tratbIds");
		if (tratbIds == null)
			tratbIds = "";
		cttbBean.setTratbId(tratbIds);
		*/
		String[] tratbIds = request.getParameterValues("tratbIds");
		String[] pheptoanIds = request.getParameterValues("tratbPheptoan");
		
		String ttb = "";
		String pt = "";
		if (tratbIds != null)
		{
			for(int i = 0; i < tratbIds.length; i++)
			{
				if(tratbIds[i] != "")
				{
					//System.out.println("Dong tra trung bay la: " + tratbIds[i] + "\n");
					
					String[] tratbId = tratbIds[i].split(","); //tra ve tratbId + vi tri cua phep toan duoc chon
					ttb += tratbId[0] + ",";
					pt += pheptoanIds[Integer.parseInt(tratbId[1])] + ",";
				}
			}
			cttbBean.setTratbId(ttb.split(","), pt.split(","));
		}
		//System.out.println("Phep toan la: " + pt + "\n");

		String ngaysua = getDateTime();
    	cttbBean.setNgaysua(ngaysua);
    	
    	String active = util.antiSQLInspection(request.getParameter("active"));
    	cttbBean.setActive(active);
    	
    	String isDkthem = util.antiSQLInspection(request.getParameter("isDkthem"));
    	if (isDkthem == null)
    		cttbBean.setDangkythem("0");
    	else
    		cttbBean.setDangkythem("1");
    	
    	String[] vungIds = request.getParameterValues("vungIds");
		String str = "";
		if(vungIds != null)
		{
			for(int i = 0; i < vungIds.length; i++)
				str += vungIds[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		cttbBean.setVungIds(str);
	
		String[] khuvucIds = request.getParameterValues("kvIds");
		String str2 = "";
		if(khuvucIds != null)
		{
			for(int i = 0; i < khuvucIds.length; i++)
				str2 += khuvucIds[i] + ",";
			if(str2.length() > 0)
				str2 = str2.substring(0, str2.length() - 1);
		}
		cttbBean.setKhuvucIds(str2);
		
		String[] kbhIds = request.getParameterValues("kbhIds");
		String str3 = "";
		if(kbhIds != null)
		{
			for(int i = 0; i < kbhIds.length; i++)
				str3 += kbhIds[i] + ",";
			if(str3.length() > 0)
				str3 = str3.substring(0, str3.length() - 1);
		}
		cttbBean.setKbhIds(str3);
		
		String[] nppIds = request.getParameterValues("nppIds");
		
		String str4 = "";
		if(nppIds != null)
		{
			for(int i = 0; i < nppIds.length; i++)
				str4 += nppIds[i] + ",";
			if(str4.length() > 0)
				str4 = str4.substring(0, str4.length() - 1);
		}
		cttbBean.setNppIds(str4);
    	
    	String[] nsptbId = request.getParameterValues("nsptbId");
		String[] nsptbDiengiai = request.getParameterValues("nsptbDiengiai");
		String[] nsptbTongluong = request.getParameterValues("nsptbTongluong");
		String[] nsptbTongtien = request.getParameterValues("nsptbTongtien");
		String[] nsptbPheptoan = request.getParameterValues("nsptbPheptoan");
				
		List<INhomsptrungbay> nsptblist = new ArrayList<INhomsptrungbay>();
		
		if(nsptbId.length > 0)
		{			
			for(int i = 0; i < nsptbId.length; i++)
			{
				if(nsptbId[i].length() > 0)
				{
					Nhomsptrungbay nsptb = new Nhomsptrungbay(nsptbId[i], nsptbDiengiai[i], nsptbTongluong[i], nsptbTongtien[i]);
					nsptb.setPheptoan(nsptbPheptoan[i]);
					nsptblist.add(nsptb);
				}
			}
		}
		cttbBean.setNsptbList(nsptblist);
	
		
		//Thuong Sales
		String[] diengiaiMuc = request.getParameterValues("diengiaiMuc");
    	cttbBean.setDiengiaiMuc(diengiaiMuc);
    	
		String[] tumuc = request.getParameterValues("tumuc");
		cttbBean.setTumuc(tumuc);
		
		String[] denmuc = request.getParameterValues("denmuc");
		cttbBean.setDenmuc(denmuc);
		
		String[] thuongSR = request.getParameterValues("thuongSR");
		cttbBean.setThuongSR(thuongSR);
		
		String[] thuongTDSR = request.getParameterValues("thuongTDSR");
		cttbBean.setThuongTDSR(thuongTDSR);
		
		String[] thuongSS = request.getParameterValues("thuongSS");
		cttbBean.setThuongSS(thuongSS);
		
		String[] thuongTDSS = request.getParameterValues("thuongTDSS");
		cttbBean.setThuongTDSS(thuongTDSS);
		
		String[] thuongASM = request.getParameterValues("thuongASM");
		cttbBean.setThuongASM(thuongASM);
		
		String[] thuongTDASM = request.getParameterValues("thuongTDASM");
		cttbBean.setThuongTDASM(thuongTDASM);
		
		String thuongdhDauTien = request.getParameter("thuongdhDauTien");
		if(thuongdhDauTien == null)
			thuongdhDauTien = "";
		cttbBean.setThuongDhDautien(thuongdhDauTien);
		
		String donviThuong = request.getParameter("donviThuong");
		if(donviThuong == null)
			donviThuong = "0";
		cttbBean.setDonvithuong(donviThuong);
		
		String[] _hangkhId = request.getParameterValues("hangkhId");
		String _hCHString = "";
		if(_hangkhId != null)
		{			
			for(int i = 0; i < _hangkhId.length; i++)
			{
				if(!_hangkhId[i].equals("0"))			
				{
					_hCHString+= _hangkhId[i]+ ",";
				}
					
			}
			if(_hCHString.length()>0)
				_hCHString = _hCHString.substring(0, _hCHString.length()-1);
		}
		System.out.println("---HANG CUA HANG: " + _hCHString);
		cttbBean.setHangkhIds(_hCHString);
		
		
		String[] _vitrikhId = request.getParameterValues("vitrikhId");
		String _VTkhId = "";
		if(_vitrikhId != null)
		{			
			for(int i = 0; i < _vitrikhId.length; i++)
			{
				if(!_vitrikhId[i].equals("0"))			
				{
					_VTkhId += _vitrikhId[i]+ ",";
				}
					
			}
			if(_VTkhId.length()>0)
				_VTkhId = _VTkhId.substring(0, _VTkhId.length() - 1 );
		}
		System.out.println("---VI TRI CUA HANG: " + _VTkhId);
		cttbBean.setVitrikhIds(_VTkhId);
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
    		if (id == null )
    		{
    			if (!cttbBean.CreateCttb()){
    		    	cttbBean.setUserId(userId);
    		    	cttbBean.createRS();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("cttbBean", cttbBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/ChuongTrinhTrungBayNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else{
    				ICttrungbayList obj = new CttrungbayList();
    				obj.setUserId(userId);
    				obj.init("");
				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/TraphacoHYERP/pages/Center/ChuongTrinhTrungBay.jsp");	    	
    			}		
    		}else{
    			if (!(cttbBean.UpdateCttb())){			
    		    	cttbBean.setUserId(userId);
    		    	cttbBean.createRS();
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("cttbBean", cttbBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/ChuongTrinhTrungBayUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else{
    				ICttrungbayList obj = new CttrungbayList();
    				obj.setUserId(userId);
    				obj.init("");
				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/TraphacoHYERP/pages/Center/ChuongTrinhTrungBay.jsp");
    			}
    		}
	    }
		else
		{
			cttbBean.createRS();		
			//if(id != null && nhomspId.length() == 0)
				//cttbBean.createDkkmSpList();
			session.setAttribute("userId", userId);
			session.setAttribute("cttbBean", cttbBean);
			String nextJSP;
			if (id == null){
				nextJSP = "/TraphacoHYERP/pages/Center/ChuongTrinhTrungBayNew.jsp";
			}
			else{
				nextJSP = "/TraphacoHYERP/pages/Center/ChuongTrinhTrungBayUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}	
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	private String convertDate(String date) 
	{
		//chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if(!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if(arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}

}
