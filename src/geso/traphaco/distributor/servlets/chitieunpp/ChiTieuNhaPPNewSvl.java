package geso.traphaco.distributor.servlets.chitieunpp;
import geso.traphaco.distributor.beans.chitieunpp.imp.ChiTieuDDKD;
import geso.traphaco.distributor.beans.chitieunpp.imp.ChiTieuNhaPP;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChiTieuNhaPPNewSvl extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChiTieuNhaPPNewSvl() {
        super();
        // TODO Auto-generated constructor stub
    }


	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
      
        return dateFormat.format(date);	
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
			Utility util=new Utility(); 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
 
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring); 
	 
		if (userId.length()==0)
		userId = util.antiSQLInspection(request.getParameter("userId"));
		String id=util.getId(querystring);
		ChiTieuNhaPP obj = new ChiTieuNhaPP(id);
		session.setAttribute("userId", userId);
 
		obj.setUserId(userId);
		String nhappid=util.getIdNhapp(userId);
		String tennhapp=util.getTenNhaPP();
		obj.init();
		obj.setNhappId(nhappid);
	 
		session.setAttribute("tennhapp", tennhapp);
		session.setAttribute("obj",obj);
		String action = util.getAction(querystring);
	 
		if(action.equals("update")){
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ChiTieuNppUpdate.jsp";//default
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("display")){
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ChiTieuNppDisplay.jsp";//default
			response.sendRedirect(nextJSP);
		}


		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
			Utility util=new Utility(); 
		int loi=0;
		//khai bao ket noi
		  dbutils db=new dbutils();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8"); 
		
		userId = util.antiSQLInspection(request.getParameter("userId"));
	 
		String nhappid=util.getIdNhapp(userId);
		String tennhapp=util.getTenNhaPP();
		
		
		String madh=util.antiSQLInspection(request.getParameter("id"));//truong truong hop don hang sua thi moi co id,nguoc lai th� khong co
		String action=request.getParameter("action");//action =new : che do them, nguoc lai la che do sua;
		if(action==null){
			action="";
		}
		//System.out.println("Action trong truong hop nay la  : " + action);
		ChiTieuNhaPP ctbean=new ChiTieuNhaPP();
		ctbean.setUserId(userId);
		try{
		ctbean.setID(Double.parseDouble(madh));
		}catch(Exception er){
			
		}
	 
		ctbean.setNguoiSua(userId);
		String ngaysua = getDateTime();
		ctbean.setNgaySua(ngaysua);
    	String ngaytao=getDateTime();
    	ctbean.setNguoiTao(userId);
    	ctbean.setNgayTao(ngaytao);   	

		  int thang=0;
		  try {
			  thang=Integer.parseInt(util.antiSQLInspection(request.getParameter("thang")));
		  }catch(Exception ex){
		  }
		  ctbean.setQuy(thang);
		  int nam=0;
		  try{
			  nam=Integer.parseInt(util.antiSQLInspection(request.getParameter("nam")));
		  }catch(Exception ex ){ 
		  }
		  
		  ctbean.setNam(nam);
		  String ngaykethuc=util.antiSQLInspection(request.getParameter("ngayketthuc"));
		  ctbean.setNgayKetThuc(ngaykethuc);
		  double tongchitieu= 0;
		  try{
			 tongchitieu= Double.parseDouble(util.antiSQLInspection(request.getParameter("tongchitieu").replace(",","")));
		  }catch(Exception ex){
			  
		  }
		  if(tongchitieu<=0)
		  {
			 loi=loi+1;
		  }
		 
		  ctbean.setChitieu(tongchitieu);
		  String diengiai=util.antiSQLInspection(request.getParameter("diengiai"));
		 
		  ctbean.setDienGiai(diengiai);
		  String songaylamviec=util.antiSQLInspection(request.getParameter("songaylamviec"));
		  ctbean.setSoNgayLamViec(songaylamviec);
		  String dvkd_fk=util.antiSQLInspection(request.getParameter("selectdvkd"));
		  ctbean.setDVDKId(dvkd_fk);
		  String kenhid=util.antiSQLInspection(request.getParameter("kenhid"));
		  ctbean.setKenhId(kenhid);
		 

		  String	nextJSP;
		  String tenform=util.antiSQLInspection(request.getParameter("tenform"));
		  
		  String sodonhang_="";
		  String sku_="";
		  if(tenform==null){
			  tenform="";
		  }
		  if(action.equals("update")){
			  nextJSP= "/TraphacoHYERP/pages/Distributor/ChiTieuNppUpdate.jsp";
		  }else{
			  nextJSP= "/TraphacoHYERP/pages/Distributor/ChiTieuNppNew.jsp";
		  }	
		  
		  if(action.equals("loadchitieu")){
			 //load chi tieu secondary cua nha phan phoi da duoc dat do trung tam
			 String sql_loadchitieu="select b.sodonhang,b.sku, b.chitieu,a.ngayketthuc,a.songaylamviec from chitieu_sec a inner join chitieu_nhapp_sec b on a.pk_seq=b.chitieu_sec_fk "+
			                        " where thang="+thang+" and nam="+nam+" and nhapp_fk="+nhappid +" and kenh_fk="+kenhid+" and  trangthai<>'2' and  dvkd_fk=" +dvkd_fk;
			 ResultSet rs_loadchitieu =db.get(sql_loadchitieu);
				System.out.println(" khong co du lieu : "+ sql_loadchitieu);
				try{
					if (!(rs_loadchitieu == null)){
						if(rs_loadchitieu.next())
						{
							ctbean.setChitieu(rs_loadchitieu.getDouble("chitieu"));
							ctbean.setNgayKetThuc(rs_loadchitieu.getString("ngayketthuc"));
							ctbean.setSoNgayLamViec(rs_loadchitieu.getString("songaylamviec"));
							sodonhang_= rs_loadchitieu.getString("sodonhang");
							sku_=rs_loadchitieu.getString("sku");
						}
						rs_loadchitieu.close();
					}
				}catch(Exception e){ 
					System.out.println("ERROR : class ChiTieuNhaPPNewSvl : line 219 - "+ e.toString() );
				}
		 }
		  //load danh sach nha phan phoi va cac chi tieu ,so don hang, sosku
	    	String[] maddkd = request.getParameterValues("maddkd");//ma nha pp
	    	String[] tenddkd =request.getParameterValues("tenddkd");//ten nha pp
			String[] chitieu = request.getParameterValues("chitieu");//
		    List<ChiTieuDDKD>  spList = new ArrayList<ChiTieuDDKD>();
			  if(maddkd != null){					
				int m = 0;
				while(m < maddkd.length){
					ChiTieuDDKD ddkd = new ChiTieuDDKD();
					if(maddkd[m] != ""){
						if(maddkd[m] != ""){ //chi them nhung san pham co so luong > 0
							ddkd.setDDKDId(maddkd[m]);
							ddkd.setDDKDTen(tenddkd[m]);
						   double ctvung=0;
						   try{
							   ctvung=Double.parseDouble(chitieu[m]);
						   }catch(Exception er){
							
						   }
						    ddkd.setChiTieu(ctvung);
						   
							spList.add(ddkd);
						}
					}
					m++;
				}	
			  }
			  ctbean.setListChiTieuDDKD(spList);//add cac chi tiet chi tieu npp vao 
			  
			  session.setAttribute("userId",userId);  
			  if(action.equals("new")){ // mode them moi
					 if(ctbean.SaveChiTieu(request)){//truong hop nay them duoc,quay tro  lai trang mai
							session.setAttribute("nam",0);
							session.setAttribute("thang",0);
						 
							ctbean.setListChiTieu("");
							session.setAttribute("obj", ctbean);
						    nextJSP= "/TraphacoHYERP/pages/Distributor/ChiTieuNPP.jsp"; 	 
					 }
				 }else if(action.equals("update")){//truong hop update
					 if(ctbean.EditChiTieu(request)){//truong hop nay them duoc,quay tro  lai trang main
							session.setAttribute("nam",0);
							session.setAttribute("thang",0);
						 
							ctbean.setListChiTieu("");
							session.setAttribute("obj", ctbean);
						    nextJSP= "/TraphacoHYERP/pages/Distributor/ChiTieuNPP.jsp"; 	 
					 }else{
						 
						 String msg=ctbean.getMessage();
						 ctbean=new ChiTieuNhaPP(madh);
						 ctbean.setMessage(msg);
						 
						 ctbean.setUserId(userId);
						 nhappid=util.getIdNhapp(userId);
						 tennhapp=util.getTenNhaPP();
						 ctbean.setNhappId(nhappid);
						 
					 }
				 }	  
		  
		  session.setAttribute("obj", ctbean);
		  response.sendRedirect(nextJSP);
	}}

}
