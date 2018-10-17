package geso.traphaco.distributor.servlets.dieuchinhtonkho;

import geso.traphaco.distributor.beans.dieuchinhtonkho.IDieuchinhtonkho;
import geso.traphaco.distributor.beans.dieuchinhtonkho.imp.Dieuchinhtonkho;
import geso.traphaco.distributor.beans.dieuchinhtonkho.IDieuchinhtonkhoList;
import geso.traphaco.distributor.beans.dieuchinhtonkho.imp.DieuchinhtonkhoList;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.distributor.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DieuchinhtonkhoSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
		public DieuchinhtonkhoSvl() {
			super();
		}   	
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");  
			String userTen = (String) session.getAttribute("userTen");  	
			String sum = (String) session.getAttribute("sum");
			geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
			if(!cutil.check(userId, userTen, sum)){
				response.sendRedirect("/TraphacoHYERP/index.jsp");
			}else{
			IDieuchinhtonkhoList obj;
			   
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    	    
		    
		    Utility util = new Utility();
		    out = response.getWriter();
		    
		    String querystring = request.getQueryString();
		    String action = util.getAction(querystring);
//		    out.println(action);
		    
		    String dctkId = util.getId(querystring);
		    
		    userId = util.getUserId(querystring);
		    
		    if (action.equals("delete")){	   		  	    	
		    	try {
					Delete(dctkId,request);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    }
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    obj = new DieuchinhtonkhoList();
		    obj.setUserId(userId);
		    obj.init0();
		    obj.createDctklist("");
			session.setAttribute("obj", obj);
					
			String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhTonKho.jsp";
			response.sendRedirect(nextJSP);
			}
		}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		IDieuchinhtonkhoList obj;
		 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    Utility util = new Utility();
		
		IDieuchinhtonkho dctkBean = (IDieuchinhtonkho) new Dieuchinhtonkho();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    out.print(action);
	    if (action.equals("new")){	    	
	    	
	    	dctkBean.setUserId(userId);
	    	dctkBean.setNppId(util.antiSQLInspection(request.getParameter("nppId")));
	    	dctkBean.setNgaydc(getDateTime());
	    	dctkBean.initNew();
	    	session.setAttribute("dctkBean", dctkBean);
    		out.println(dctkBean.getMessage());
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhTonKhoNew.jsp";
    		response.sendRedirect(nextJSP);    		

	    }
	    else  if (action.equals("search")){
	    	obj = new DieuchinhtonkhoList();
	    	obj.setUserId(util.antiSQLInspection(request.getParameter("userId")));
			String query = getSearchQuery(request, obj);		    
		    obj.init0();
		    obj.createDctklist(query);
			session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhTonKho.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	 obj = new DieuchinhtonkhoList();
			    obj.setUserId(userId);
			    obj.init0();
			    obj.createDctklist("");
				session.setAttribute("obj", obj);
						
				String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhTonKho.jsp";
				response.sendRedirect(nextJSP);
	    }
		}
	    	
	  
	}

	private String getSearchQuery(HttpServletRequest request, IDieuchinhtonkhoList obj){
//	    PrintWriter out = response.getWriter();
		Utility util = new Utility();
		geso.traphaco.center.util.Utility utilCenter = new geso.traphaco.center.util.Utility();
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));		
    	if (nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
    	if (dvkdId == null)
    		dvkdId = "";
    	obj.setDvkdId(dvkdId);
    	
		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
    	if (kbhId == null)
    		kbhId = "";
    	obj.setKbhId(kbhId);

		String khoId = util.antiSQLInspection(request.getParameter("khoId"));
    	if (khoId == null)
    		khoId = "";
    	obj.setKhoId(khoId);

    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);

    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	//Sua ket theo kieu moi de lay bang cac phieu dieu chinh ton kho,dung cau lenh cu,khong lay duoc nguoi duyet.
    	
		//String query = "select distinct a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd, e.ten as kbh, f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua from dieuchinhtonkho a, nhanvien b, nhanvien c, donvikinhdoanh d, kenhbanhang e,                        kho f              where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.dvkd_fk = d.pk_seq and a.kbh_fk = e.pk_seq and f.pk_seq = a.kho_fk and a.npp_fk='"+ nppId +"'";    	   	   	
    //	String	query = "select distinct a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd, e.ten as kbh, f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua, g.ten as nguoiduyet from dieuchinhtonkho a, nhanvien b, nhanvien c, donvikinhdoanh d, kenhbanhang e, kho f, nhanvien g where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.nguoiduyet = g.pk_seq and a.dvkd_fk = d.pk_seq and a.kbh_fk = e.pk_seq and f.pk_seq = a.kho_fk and a.npp_fk='"+ nppId +"'";
    String query=	"select distinct a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd, e.ten as kbh, "+
    	" f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua, isnull(g.ten,'0') as nguoiduyet "+ 
    	" from dieuchinhtonkho a inner join  nhanvien b on b.pk_Seq=a.nguoitao inner join nhanvien c on a.nguoisua=c.pk_Seq "+ 
    	" inner join donvikinhdoanh d on d.pk_Seq=a.dvkd_fk  "+
    	" inner join  kenhbanhang e on e.pk_seq=a.kbh_fk inner join  kho f  on f.pk_Seq= a.kho_fk "+ 
    	" left join  nhanvien g  on g.pk_seq=a.nguoiduyet "+
    	" where  a.npp_fk='"+nppId+"' and a.kho_fk in "+utilCenter.quyen_kho(obj.getUserId());
    	if (dvkdId.length()>0){
			query = query + " and a.dvkd_fk = '" + dvkdId + "'";
			
    	}
    	
    	if (kbhId.length()>0){
			query = query + " and a.kbh_fk = '" + kbhId + "'";
			
    	}

    	if (khoId.length()>0){
			query = query + " and a.kho_fk = '" + khoId + "'";
			
    	}

    	if (tungay.length()>0){
			query = query + " and a.ngaydc >= '" + tungay+ "'";
    		
    	}

    	if (denngay.length()>0){
			query = query + " and a.ngaydc <= '" + denngay+ "'";
    		
    	}

    	query = query + " order by a.trangthai, a.pk_seq";
    	System.out.println("1.Query dieu chinh ton kho: " + query);
    	return query;
    	
	}	
	
	private void Delete(String dctkId,HttpServletRequest request) throws SQLException
	{		
		dbutils db = new dbutils();
		String idnpp="";
		
		Utility util = new Utility();
	    String msg = util.Check_Huy_NghiepVu_KhoaSo("Dieuchinhtonkho", dctkId, "NgayDC", db);
	    if(msg.length()>0)
		{
		    db.shutDown();
		    return ;
		}

		db.getConnection().setAutoCommit(false);
		
		String query = "select count(pk_seq) as num,npp_fk from dieuchinhtonkho where pk_seq='" + dctkId + "' and trangthai = '0' group by NPP_FK ";
		System.out.println(query);
		ResultSet rs = db.get(query);
		try
		{
			rs.next();
			if(!rs.getString("num").equals("0"))
			{
				idnpp=rs.getString("npp_Fk");
				rs.close();
				
				String sql="select k.npp_fk,sp.dieuchinh,sp.sanpham_fk,sp.solo,k.kho_fk,k.kbh_fk,sp.NgayHetHan from dieuchinhtonkho_sp sp,dieuchinhtonkho k where k.PK_SEQ=sp.DIEUCHINHTONKHO_FK and k.pk_seq = '" + dctkId + "'";
				ResultSet rs1=db.get(sql);
				while(rs1.next())
				{
					System.out.println("____");
					
					int dieuchinh=rs1.getInt("dieuchinh");
					String masp=rs1.getString("sanpham_fk");
					String solo=rs1.getString("solo");
					String kho_fk=rs1.getString("kho_fk");
					String kbh_fk=rs1.getString("kbh_fk");
					String npp=rs1.getString("npp_fk");
					String ngayhethan=rs1.getString("ngayhethan");
					
					idnpp=npp;
					if(dieuchinh<0)
					{
						String qr="update NHAPP_KHO_CHITIET set booked=booked + ("+dieuchinh+") , available=available-("+dieuchinh;
						qr+=") where kho_fk="+kho_fk+" and npp_fk="+npp +" and sanpham_fk="+masp +" and kbh_fk="+kbh_fk+" and solo='"+solo+"' and NgayHetHan='"+ngayhethan+"'";
					
					if(db.updateReturnInt(qr)!=1)
					{
						db.update("rollback");
					}
						
					qr="update nhapp_kho  set booked=booked + ("+dieuchinh+") , available=available-("+dieuchinh;
					qr+=") where kho_fk="+kho_fk+" and npp_fk="+npp +" and sanpham_fk="+masp +" and kbh_Fk="+kbh_fk+" ";
					if(db.updateReturnInt(qr)!=1)
					{
						db.update("rollback");
					}
								
				}
				
				query = "delete from dieuchinhtonkho_sp where dieuchinhtonkho_fk = '" + dctkId + "'";
				System.out.println(query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
				}
				
				query = "delete from dieuchinhtonkho where pk_seq='" + dctkId + "' and trangthai = '0'";
				System.out.println(query);
				if(db.updateReturnInt(query)!=1)
				{
					db.getConnection().rollback();
				}
			}
						   
				msg= util.Check_Kho_Tong_VS_KhoCT(idnpp, db);
		    	if(msg.length()>0)
		    	{
		    		db.getConnection().rollback();
		    		return ;
		  		}
			}
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		}
		catch(Exception e)
		{
			System.out.println("Error  : "+e.toString());
			e.printStackTrace();
		}
		finally
		{
			db.shutDown();
		}
		
	}
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
