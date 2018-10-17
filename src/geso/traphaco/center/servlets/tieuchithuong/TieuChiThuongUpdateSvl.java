package geso.traphaco.center.servlets.tieuchithuong;

import geso.traphaco.center.beans.tieuchithuong.ITieuchithuong;
import geso.traphaco.center.beans.tieuchithuong.imp.Tieuchithuong;
import geso.traphaco.center.db.sql.dbutils;

import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class TieuChiThuongUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public TieuChiThuongUpdateSvl() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();	    
	    Utility util = new Utility();
	    	    
	    
	    ITieuchithuong tctBean = new Tieuchithuong();
	    String querystring = request.getQueryString();
	    System.out.println("querystring : "+querystring);
	    
	    String userId = util.antiSQLInspection(util.getUserId(querystring));
	    tctBean.setUserId(userId);
	    
	    String tctId = util.antiSQLInspection(util.getId(querystring));

    	String action = util.antiSQLInspection(util.getAction(querystring));
    	
    	System.out.println("action : "+action);
    	
	    if(action.equals("copy")){
	    	tctBean.setAction(action);
	    } 
	    
	    if(action.equals("xoatieuchi"))
 	    {
	    	tctBean.setTctId(tctId.split(";")[9]);
 	    	System.out.println("vo day ne : "+tctId.split(";")[9]);
	    	
	    	String loaiTC = tctId.split(";")[1];
	    	System.out.println("loaiTC : "+loaiTC);
	    	
	    	String tcfk = tctId.split(";")[0];
	    	System.out.println("tcfk : "+tcfk);
	    	
	    	// Diengiai();Kbh();Dvkd();Thang;Nam();TongThuong();TileDStoithieu()
		    
		    String diengiai = tctId.split(";")[2];
		    if(diengiai == null)
		    	diengiai = "";
		    
			String kbhId = tctId.split(";")[3];
			if(kbhId == null)
				kbhId = "";
			
			String dvkdId = tctId.split(";")[4];
			if(dvkdId == null)
				dvkdId = "";
			
			String nam = tctId.split(";")[6];
			if(nam == null)
				nam = "";
			
			String thang = tctId.split(";")[5];
			if(thang == null)
				thang = "";
			
			String loai = util.antiSQLInspection(request.getParameter("loai"));
			if (loai == null)
				loai = "0";
	    	else
	    		loai = "1";
			System.out.println("Loai : "+loai);
			tctBean.setLoai(loai);
			
			String htbh = util.antiSQLInspection(request.getParameter("htbhId"));
			if (htbh == null)
				htbh = "100000";
			tctBean.sethethongBH(htbh);

			String tiledstoithieu = tctId.split(";")[8];
			if(tiledstoithieu == null)
				tiledstoithieu = "";
			
			String tongthuong = tctId.split(";")[7];
			if(tongthuong == null)
				tongthuong = "";
			
			/*String tungay = tctId.split(";")[10];
			if(tungay == null)
				tungay = "";
			
			String denngay = tctId.split(";")[11];
			if(denngay == null)
				denngay = "";*/

	    	if(loaiTC.length() > 0)
		    	tctBean.SetLoaiTieuChi(loaiTC);
	    	
	    	if(tcfk.length() > 0)
		    	tctBean.setTieuchiFK(tcfk);

		    if(diengiai.length() > 0)
		    	tctBean.setDiengiai(diengiai);
		    
			if(kbhId.length() > 0)
				tctBean.setKbh(kbhId);
			
			if(dvkdId.length() > 0)
				tctBean.setDvkd(dvkdId);
			
			if(nam.length() > 0)
				tctBean.setNam(nam);
			
			if(thang.length() > 0)
				tctBean.setThang(thang);

			if(tiledstoithieu.replaceAll(",", "").length() > 0)
			tctBean.setTileDStoithieu(tiledstoithieu.replaceAll(",", ""));
			
			if(tongthuong.replaceAll(",", "").length() > 0)
			tctBean.setTongThuong(tongthuong.replaceAll(",", ""));
			
			if(loai.length() > 0)
				tctBean.setLoai(loai);
			
			tctBean.xoaTC();
 	    }
	    else
	    {
	    	tctBean.setTctId(tctId);	    	
	    }
	    
	    tctBean.init();

    	session.setAttribute("tctBean", tctBean);
    	
    	session.setAttribute("userId", userId);
    	
		
    	if(action.equals("hienthi")){
    		response.sendRedirect("/TraphacoHYERP/pages/Center/TieuChiThuongDisplay.jsp");
    	}else{
    		System.out.println("vo day update ");
    		response.sendRedirect("/TraphacoHYERP/pages/Center/TieuChiThuongUpdate.jsp");
    	}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();	    
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    String tctId = util.antiSQLInspection(request.getParameter("id"));
	    //String tc =  util.antiSQLInspection(request.getParameter("tc"));
	    String tcfk = util.antiSQLInspection(request.getParameter("tc"));
	    String action = util.antiSQLInspection(request.getParameter("action"));
	    
	    ITieuchithuong tctBean = new Tieuchithuong();

	    tctBean.setUserId(userId);
	    tctBean.setTctId(tctId);
	    
	    String tc = "";
	    try{
		    dbutils db = new dbutils();
		    String query = "select tieuchi from tieuchithuong_chitiet where pk_seq = '"+ tcfk +"'";
		    ResultSet rs = db.get(query);
		    rs.next();
		    tc = rs.getString("tieuchi");
		    rs.close();
	    }catch(Exception ex){}
	    
	    tctBean.setTieuchi(tc);
	    tctBean.setTieuchiFK(tcfk);
	    
	    String loaiTC = util.antiSQLInspection(request.getParameter("loaiTC"));
	    System.out.println("loaiTC : "+loaiTC);
	    
	    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	    if(diengiai == null)
	    	diengiai = "";
	    
		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if(kbhId == null)
			kbhId = "";
		
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if(dvkdId == null)
			dvkdId = "";
		
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if(nam == null)
			nam = "";
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if(thang == null)
			thang = "";
		
		String loai = util.antiSQLInspection(request.getParameter("loai"));
		if (loai == null)
			loai = "0";
    	else
    		loai = "1";
		System.out.println("Loai : "+loai);
		tctBean.setLoai(loai);
		
		String htbh = util.antiSQLInspection(request.getParameter("htbhId"));
		if (htbh == null)
			htbh = "100000";
		System.out.println("htbh : "+htbh);
		tctBean.sethethongBH(htbh);
		
		String cty = util.antiSQLInspection(request.getParameter("ctyId"));
		if (cty == null)
			cty = "100000";
		System.out.println("cty : "+cty);
		tctBean.SetCongty(cty);
		
		String tungay = "";
		String denngay = "";
		if(tc.equals("2") || tc.equals("5") || tc.equals("6"))
		{
			tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if(tungay == null)
				tungay = "";
			denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if(denngay == null)
				denngay = "";
		}

		String min = util.antiSQLInspection(request.getParameter("min"));
		if(min == null)
			min = "";
		
		String max = util.antiSQLInspection(request.getParameter("max"));
		if(max == null)
			max = "";
		
		String thuongnsp = util.antiSQLInspection(request.getParameter("thuongnsp"));
		if(thuongnsp == null)
			thuongnsp = "";
		
		String dstoithieudh = util.antiSQLInspection(request.getParameter("dstoithieudh"));
		if(dstoithieudh == null)
			dstoithieudh = "";
		
		String tiledstoithieu = util.antiSQLInspection(request.getParameter("tldstoithieu"));
		if(tiledstoithieu == null)
			tiledstoithieu = "";
		
		String tongthuong = util.antiSQLInspection(request.getParameter("tongthuong"));
		if(tongthuong == null)
			tongthuong = "";
		
		String loaisales = util.antiSQLInspection(request.getParameter("loaisales"));
		if(loaisales == null)
			loaisales = "0";
		System.out.println("loaisales : "+loaisales);
		
				
		String nhomsp  = util.antiSQLInspection(request.getParameter("nhomsp"));
		if(nhomsp == null)
			nhomsp = "";
		System.out.println("nhom sp : "+nhomsp);
		
		String[] tcct = request.getParameterValues("tcct");
		String[] stt = request.getParameterValues("stt");
		String[] tu = request.getParameterValues("tu");
		String[] den = request.getParameterValues("den");
		String[] thuong = request.getParameterValues("thuong");
    	tctBean.setData(tcct, stt, tu, den, thuong);
    	
    	
    	String diengiaitc = util.antiSQLInspection(request.getParameter("diengiaitc"));
		if(diengiaitc == null)
			diengiaitc = "";
		System.out.println("diengiaitc : "+diengiaitc);
		tctBean.setTCDiengiai(diengiaitc);
		
		String nhomtc = util.antiSQLInspection(request.getParameter("nhomtc"));
		if(nhomtc == null)
			nhomtc = "";
		System.out.println("nhomtc : "+nhomtc);
		tctBean.setTCNhomId(nhomtc);
    	
    	if(action.equals("luulai"))
    	{
    		tctBean.SetLoaiTieuChi(loaiTC);
    		tctBean.setDiengiai(diengiai);
    		tctBean.setKbh(kbhId);
    		tctBean.setDvkd(dvkdId);
    		tctBean.setNam(nam);
    		tctBean.setThang(thang);
    		tctBean.setToithieu(min.replaceAll(",", ""));
    		tctBean.setToida(max.replaceAll(",", ""));
    		tctBean.setThuongnsp(thuongnsp.replaceAll(",", ""));
    		tctBean.setDstoithieuDH(dstoithieudh.replaceAll(",", ""));
    		
    		tctBean.setTileDStoithieu(tiledstoithieu.replaceAll(",", ""));
    		tctBean.setTongThuong(tongthuong.replaceAll(",", ""));
    		
    		tctBean.setNhomsp(nhomsp);
    	
    		tctBean.setLoaiDS(loaisales);
    		
    		tctBean.setTungay(tungay);
    		tctBean.setDenngay(denngay);
    		
    		tctBean.setData(tcct, stt, tu, den, thuong);
    		
    		tctBean.Save();	
    	}
   
	    tctBean.init();
	    
	    
	    if(loaiTC.length() > 0)
	    	tctBean.SetLoaiTieuChi(loaiTC);
	    
	    if(diengiai.length() > 0)
	    	tctBean.setDiengiai(diengiai);
		if(kbhId.length() > 0)
			tctBean.setKbh(kbhId);
		if(dvkdId.length() > 0)
			tctBean.setDvkd(dvkdId);
		if(nam.length() > 0)
			tctBean.setNam(nam);
		if(thang.length() > 0)
			tctBean.setThang(thang);

		if(min.replaceAll(",", "").length() > 0)
			tctBean.setToithieu(min.replaceAll(",", ""));
		if(max.replaceAll(",", "").length() > 0)
			tctBean.setToida(max.replaceAll(",", ""));
		if(thuongnsp.replaceAll(",", "").length() > 0)
			tctBean.setThuongnsp(thuongnsp.replaceAll(",", ""));
		
		if(dstoithieudh.replaceAll(",", "").length() > 0)
			tctBean.setDstoithieuDH(dstoithieudh.replaceAll(",", ""));
		
		if(tiledstoithieu.replaceAll(",", "").length() > 0)
		tctBean.setTileDStoithieu(tiledstoithieu.replaceAll(",", ""));
		
		if(tongthuong.replaceAll(",", "").length() > 0)
		tctBean.setTongThuong(tongthuong.replaceAll(",", ""));
		
		if(nhomsp.length() > 0)
			tctBean.setNhomsp(nhomsp);
		
		if(loai.length() > 0)
			tctBean.setLoai(loai);
		
		if(htbh.length() > 0)
			tctBean.sethethongBH(htbh);
		
		if(cty.length() > 0)
		{
			System.out.println("Congty = " + cty);
			tctBean.SetCongty(cty);
		}
		
		if(loaisales.length() > 0)
			tctBean.setLoaiDS(loaisales);
		
		if(tungay.length() > 0)
			tctBean.setTungay(tungay);
		
		if(denngay.length() > 0)
			tctBean.setDenngay(denngay);
			
    	session.setAttribute("tctBean", tctBean);
    	
    	session.setAttribute("userId", userId);
	
    	if(action.equals("hienthi")){
    		response.sendRedirect("/TraphacoHYERP/pages/Center/TieuChiThuongDisplay.jsp");
    	}
    	else
    	{
    		response.sendRedirect("/TraphacoHYERP/pages/Center/TieuChiThuongUpdate.jsp");
    	}

	}

}
