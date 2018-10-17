package geso.traphaco.distributor.servlets.chitieunpp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.chitieunpp.IChitieuSKUInList;
import geso.traphaco.distributor.beans.chitieunpp.IChitieuSKUInTT;
import geso.traphaco.distributor.beans.chitieunpp.imp.ChitieuSKUInList;
import geso.traphaco.distributor.beans.chitieunpp.imp.ChitieuSKUInTT;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChiTieuSKUInSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
       
    public ChiTieuSKUInSvl() {
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
  	    out = response.getWriter();
  	    	    
  	    String querystring = request.getQueryString();
  	    String userId = util.getUserId(querystring);
  	    out.println(userId);
  	    
  	    if (userId.length() == 0)
  	    	userId = util.antiSQLInspection(request.getParameter("userId"));
  	    
  	    IChitieuSKUInList obj = new ChitieuSKUInList();
  	    obj.setUserId(userId);
  	    
  	    String view = request.getParameter("view");
  	    if(view == null)
  	    	view = "";
  	    System.out.println("______View la: " + view);
  	    
  	    if(view.equals("TT"))
  	    {
  	    	String action = util.getAction(querystring);
  	    	String ctId = util.getId(querystring);
  	    	
  	    	System.out.println("___Action: " + action + " ___ Chi tieu Id: " + ctId);
  	    	
  	    	if(action.equals("delete"))
  	    	{
  	    		DeleChiTieuTT(ctId);
  	    	}
  	    	
  	    	if(action.equals("duyet"))
  	    	{
  	    		dbutils db = new dbutils();
  	    		
  	    		db.update("update chitieuskuin_tt set trangthai = '1' where pk_seq = '" + ctId + "'");
  	    		db.shutDown();
  	    	}
  	    	
  	    	obj.initTT("");
  			session.setAttribute("obj", obj);
  		    String nextJSP = "/TraphacoHYERP/pages/Distributor/ChiTieuSKUIn.jsp";
  			response.sendRedirect(nextJSP);
  	    }
  	    else
  	    {
  	    	 String action = util.getAction(querystring);
  	    	 String nhId = util.getId(querystring);
  	    	 System.out.print(action);
  	    	 System.out.print(nhId);
  	    	 if (action.equals("delete"))
  	 	    {	
  	    		System.out.println("vao delete");
  	 	    	String msg = Delete(nhId);
  	 	    	if(msg.length() > 0)
  	 	    		obj.setMsg(msg);
  	 	    }
  	    	 if(action.equals("chot"))
  	    	 {
  	    		 System.out.print("vao chot");
  	    		 String msg = Chot(nhId,userId);
  		 	    	if(msg.length() > 0)
  		 	    		obj.setMsg(msg); 
  	    	 }
  		    obj.init("");
  			session.setAttribute("obj", obj);
  			String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuSKUInNpp.jsp";
  		    /*String nextJSP = "/TraphacoHYERP/pages/Distributor/ChiTieuSKUIn.jsp";*/
  			response.sendRedirect(nextJSP);
  	    }
  	}

  	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
  	{
  		request.setCharacterEncoding("UTF-8");
  	    response.setCharacterEncoding("UTF-8");
  	    response.setContentType("text/html; charset=UTF-8");
  	    this.out  = response.getWriter();
  	    
  	    HttpSession session = request.getSession();	
  	    
  	    out = response.getWriter();
  	    Utility util = new Utility();
  	    
  	    String userId = util.antiSQLInspection(request.getParameter("userId"));     
  	    IChitieuSKUInList obj;
  	    
  		String action = request.getParameter("action");
  	    if (action == null){
  	    	action = "";
  	    }
  	    
  	    String task = request.getParameter("task");
  	    if (task == null){
  	    	task = "";
  	    }
  	    System.out.println("____Task la: " + task);
  	    
  	    if(action.equals("new"))
  	    {
  	    	if(task.equals("TT"))
  	    	{
  		    	IChitieuSKUInTT ctskuBean = new ChitieuSKUInTT();
  		    	ctskuBean.setUserId(userId);
  		    	ctskuBean.init();
  		    	session.setAttribute("ctskuBean", ctskuBean);  	
  	    		session.setAttribute("userId", userId);
  			
  	    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/ChiTieuSKUInNew.jsp");	
  	    	}
  	    	else
  	    	{
  	    		IChitieuSKUInTT ctskuBean = new ChitieuSKUInTT();
  	    		ctskuBean.setUserId(userId);
  	    		ctskuBean.initnpp();
  	    	    session.setAttribute("obj", ctskuBean);  	
  	    		session.setAttribute("userId", userId);
  	    		response.sendRedirect("/TraphacoHYERP/pages/Center/ChiTieuSKUInNewNpp.jsp");	
  	    		/*response.sendRedirect("/TraphacoHYERP/pages/Distributor/ChiTieuSKUInNew.jsp");	*/
  	    	}
  	    }
  	    else
  	    {
  	    	if(task.equals("TT"))
  	    	{
  	    		if(action.equals("view") || action.equals("next") || action.equals("prev"))
  		    	{
  	    			obj = new ChitieuSKUInList();
  		    		String search = getSearchQuery2(request, obj,userId);
  			    	System.out.println("nxtApprSplitting "+Integer.parseInt(request.getParameter("nxtApprSplitting")));
  			    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
  			    	obj.setUserId(userId);
  			    	obj.initTT(search);
  			    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
  			    	session.setAttribute("obj", obj);
  			    	response.sendRedirect("/TraphacoHYERP/pages/Distributor/ChiTieuSKUIn.jsp");	
  			    }
  		    	else
  		    	{
  		    		obj = new ChitieuSKUInList();
  				    obj.setUserId(userId);
  		
  			    	String search = getSearchQuery2(request, obj,userId);
  			    	
  			    	obj.setUserId(userId);
  			    	System.out.println(search);
  			    	obj.initTT(search);
  						
  			    	session.setAttribute("obj", obj);  	
  		    		session.setAttribute("userId", userId);
  				
  		    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/ChiTieuSKUIn.jsp");
  		    	}

  	    	}
  	    	else
  	    	{
  	    		if(action.equals("view") || action.equals("next") || action.equals("prev"))
  		    	{
  	    			obj = new ChitieuSKUInList();
  		    		String search = getSearchQuery2(request, obj,userId);
  			    	System.out.println("nxtApprSplitting "+Integer.parseInt(request.getParameter("nxtApprSplitting")));
  			    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
  			    	obj.setUserId(userId);
  			    	obj.init(search);
  			    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
  			    	session.setAttribute("obj", obj);
  			    	response.sendRedirect("/TraphacoHYERP/pages/Center/ChiTieuSKUInNpp.jsp");
  			    }
  		    	else
  		    	{
  		    		obj = new ChitieuSKUInList();
  				    obj.setUserId(userId);
  		
  			    	String search = getSearchQuery2(request, obj,userId);
  			    	
  			    	obj.setUserId(userId);
  			    	System.out.println(search);
  			    	obj.initTT(search);
  						
  			    	session.setAttribute("obj", obj);  	
  		    		session.setAttribute("userId", userId);
  				
  		    		response.sendRedirect("/TraphacoHYERP/pages/Center/ChiTieuSKUInNpp.jsp");
  		    	}
  	    	}
  	    }
  	}

  	private String getSearchQuery(HttpServletRequest request, IChitieuSKUInList obj,String userId) 
  	{
  		String query="";
  		Utility util = new Utility();
  		String nppid=util.getIdNhapp(userId);
  		System.out.println("nppid "+nppid);
      	if ( userId == null)
      		userId = "";
      	obj.setNppId(util.getIdNhapp(userId));
     
  		String nam = util.antiSQLInspection(request.getParameter("nam"));
  		if ( nam.trim().equals("0"))
  			nam = "";
      	System.out.println("nam "+nam);
      	obj.setNam(nam);
      	
      	String thang=util.antiSQLInspection(request.getParameter("thang"));
      	if ( thang.trim().equals("0"))
      		thang = "";
      	System.out.println("thang "+thang);
      	obj.setThang(thang);
      	
      	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
      	if ( trangthai == null)
      		trangthai = "";
      	obj.setTrangthai(trangthai);
      	    	   
      	query =  "select a.thang,a.pk_seq, a.nam, a.diengiai, a.trangthai, a.nhapp_fk, a.ngaysua, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua " +
  		  "from CHITIEUSKUIN a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq " +
  		  "where  1=1 ";
      		
      	if (nam.length() > 0)
      	{
  			query = query + " and a.nam ='" + nam + "'";			
      	}
      	
      	if (trangthai.length() > 0)
      	{
  			query = query + " and a.trangthai ='" + trangthai + "'";			
      	}
      	
      	if (thang.length() > 0)
      	{
      		query = query + " and a.thang  = '" + thang + "'"; 
      	}
      	query = query + " order by a.nam desc, a.thang desc";
      	return query;
  	}
  	
  	private String getSearchQuery2(HttpServletRequest request, IChitieuSKUInList obj,String userId) 
  	{
  		String query="";
  		Utility util = new Utility();
  		
  		String nppid=util.getIdNhapp(userId);
      	if ( userId == null)
      		userId = "";
      	obj.setNppId(util.getIdNhapp(userId));
     
  		String nam = util.antiSQLInspection(request.getParameter("nam"));
  		if ( nam.trim().equals("0"))
  			nam = "";
      	obj.setNam(nam);
      	
      	String thang=util.antiSQLInspection(request.getParameter("thang"));
      	if ( thang.trim().equals("0"))
      		thang = "";
      	System.out.println("thang "+thang);
      	obj.setThang(thang);
      	
      	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
      	if ( trangthai == null)
      		trangthai = "";
      	obj.setTrangthai(trangthai);
      	    	   
      	query = "select a.pk_seq, a.thang, a.nam, a.diengiai, a.trangthai, a.ngaysua, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua " +
  		  "  from CHITIEUSKUIN a inner join CHITIEUSKUIN_SKU sub on a.PK_SEQ = sub.CHITIEUSKUIN_FK  inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq " +
  		  " where sub.NPP_FK="+ nppid;
      		
      	if (nam.length() > 0)
      	{
  			query = query + " and a.nam ='" + nam + "'";			
      	}
      	
      	if (trangthai.length() > 0)
      	{
  			query = query + " and a.trangthai ='" + trangthai + "'";			
      	}
      	
      	if (thang.length() > 0)
      	{
      		query = query + " and a.thang  = '" + thang + "'"; 
      	}
      	//query = query + " order by a.nam desc, a.thang desc";
      	return query;
  	}
  	
  	private String Delete(String nhId)
  	{
  		dbutils db = new dbutils();
  		
  		try 
  		{
  			db.getConnection().setAutoCommit(false);
  			if(!db.update("delete CHITIEUSKUIN_SKU where CHITIEUSKUIN_FK ='"+ nhId+"'"))
  			{
  				System.out.println("delete CHITIEUSKUIN_SKU where pk_seq ='"+ nhId+"'");
  				db.getConnection().rollback();
  				return "Lỗi "+ "delete CHITIEUSKUIN where pk_seq ='"+ nhId+"'";
  			}
  			if(!db.update("delete CHITIEUSKUIN where pk_seq ='"+ nhId+"'"))
  			{
  				System.out.println("delete CHITIEUSKUIN where pk_seq ='"+ nhId+"'");
  				db.getConnection().rollback();
  				return "Lỗi "+ "delete CHITIEUSKUIN where pk_seq ='"+ nhId+"'";
  			}
  			db.getConnection().commit();
  			db.getConnection().setAutoCommit(true);
  			db.shutDown();
  			return "";
  		} 
  		catch (Exception e)
  		{ 
  			db.update("rollback");
  			return "Không thể xóa! Lỗi: " + e.getMessage(); 
  		}
  	}
  	private String getDateTime() 
  	{
          DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          Date date = new Date();
          return dateFormat.format(date);	
  	}
  	private String Chot(String nhId,String userid)
  	{
  		dbutils db = new dbutils();
  		
  		try 
  		{
  			db.getConnection().setAutoCommit(false);
  			if(!db.update("update CHITIEUSKUIN set trangthai='1'  where pk_seq ='"+ nhId+"'"))
  			{
  				db.getConnection().rollback();
  				return "Lỗi "+ "update CHITIEUSKUIN set trangthai='1'  where pk_seq ='"+ nhId+"'";
  			}
  			
  		String 	sql="select thang,nam,dvkd_fk,kbh_fk from CHITIEUSKUIN   where pk_seq="+nhId ;
  			
  		System.out.println(sql);
  		ResultSet rs=db.get(sql);
  			int thang=0;
  			int nam=0;
  			String dvkd_fk="";
  			String kbh_fk="";
  			if(rs!=null){
  				if(rs.next()){
  				thang=rs.getInt("thang");
  				nam=rs.getInt("nam");
  				dvkd_fk=rs.getString("dvkd_fk");
  				kbh_fk=rs.getString("kbh_fk");
  				}
  			}
  			
  			sql=" delete chitieu_nhapp where chitieu_fk in (select pk_seq from chitieu where thang="+thang+" and nam="+nam+" and dvkd_fk="+dvkd_fk+" and kenh_fk="+ kbh_fk +")";
  			
  			if(!db.update(sql)){
  				db.getConnection().rollback();
  				System.out.println(sql);
  				return "Không thể cập nhật " + sql;	
  			}
  			
  			sql="delete chitieu where thang="+thang+" and nam="+nam+" and dvkd_fk="+dvkd_fk+" and kenh_fk="+ kbh_fk;
  			
  			if(!db.update(sql)){
  				db.getConnection().rollback();
  				System.out.println(sql);
  				return "Không thể cập nhật " + sql;			
  			}
  			

  			sql="insert into chitieu (thang,nam,ngaytao,ngaysua,nguoitao,nguoisua,trangthai,dvkd_fk,kenh_fk,chitieu,ngayketthuc) values " +
  					"("+thang+","+nam+",'"+this.getDateTime()+"','"+this.getDateTime()+"','"+userid+"','"+userid+"','1','"+dvkd_fk+"','"+kbh_fk+"','0','"+this.getDateTime()+"')";
  			
  			if(!db.update(sql)){
  				db.getConnection().rollback();
  				System.out.println(sql);
  				return "Không thể cập nhật " + sql;
  				
  			}
  			
  			String id="";
  			String query = "select IDENT_CURRENT('CHITIEU') as dhId";
  			
  			ResultSet rsDh = db.get(query);	
  			try
  			{
  				rsDh.next();
  				id=rsDh.getString("dhId");
  				rsDh.close();
  			}catch(Exception ex){
  				db.getConnection().rollback();
  				System.out.println(sql);
  				return "Không thể cập nhật " + sql;
  			}
  			
  			
  			sql=" INSERT INTO CHITIEU_NHAPP  SELECT  "+id+",CT_SKU.NPP_FK ,SUM( ISNULL(BG.GIAMUANPP,0) * CT_SKU.CHITIEU) AS CHITIEUPRI "+
  			" FROM CHITIEUSKUIN CT INNER JOIN CHITIEUSKUIN_SKU CT_SKU "+
  			" ON CT.PK_SEQ=CT_SKU.CHITIEUSKUIN_FK  "+
  			" LEFT JOIN  "+
  			" (  "+
  			" SELECT DISTINCT BGM.KENH_FK AS KBH_FK,BGM_SP.SANPHAM_FK,BGMNPP.NPP_FK,BGM_SP.GIAMUANPP AS GIAMUANPP"+ 
  			" FROM BANGGIAMUANPP_NPP BGMNPP INNER JOIN BANGGIAMUANPP BGM ON BGM.PK_SEQ = BGMNPP.BANGGIAMUANPP_FK "+
  			" INNER JOIN BGMUANPP_SANPHAM BGM_SP ON BGM_SP.BGMUANPP_FK = BGM.PK_SEQ "+
  			" ) AS BG "+
  			" ON BG.SANPHAM_FK=CT_SKU.SKU AND BG.KBH_FK=CT.KBH_FK AND BG.NPP_FK=CT_SKU.NPP_FK"+ 
  			"  WHERE  ct.pk_seq="+nhId+
  			 " GROUP BY  CT_SKU.NPP_FK";
  			if(!db.update(sql)){
  				db.getConnection().rollback();
  				System.out.println(sql);
  				return "Không thể cập nhật " + sql;
  				
  			}
  			
  			 sql=" INSERT INTO CHITIEUPRI " +
  			" SELECT CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK,'SS' ,GSBH.PK_SEQ ,SUM( ISNULL(BG.GIAMUANPP,0) * CT_SKU.CHITIEU) AS CHITIEUPRI, NULL AS KHUVUC "+
  			" FROM CHITIEUSKUIN CT INNER JOIN CHITIEUSKUIN_SKU CT_SKU "+
  			" ON CT.PK_SEQ=CT_SKU.CHITIEUSKUIN_FK "+
  			" INNER JOIN NHAPP_GIAMSATBH NPP_GS ON NPP_GS.NPP_FK=CT_SKU.NPP_FK "+
  			" INNER JOIN GIAMSATBANHANG GSBH ON GSBH.PK_SEQ=NPP_GS.GSBH_FK "+
  			" LEFT JOIN "+
  			" ( "+
  			" SELECT DISTINCT BGM.KENH_FK AS KBH_FK,BGM_SP.SANPHAM_FK,BGMNPP.NPP_FK,BGM_SP.GIAMUANPP AS GIAMUANPP "+
  			" FROM BANGGIAMUANPP_NPP BGMNPP INNER JOIN BANGGIAMUANPP BGM ON BGM.PK_SEQ = BGMNPP.BANGGIAMUANPP_FK "+
  			" INNER JOIN BGMUANPP_SANPHAM BGM_SP ON BGM_SP.BGMUANPP_FK = BGM.PK_SEQ "+
  			" ) AS BG "+
  			" ON BG.SANPHAM_FK=CT_SKU.SKU AND BG.KBH_FK=CT.KBH_FK AND BG.NPP_FK=CT_SKU.NPP_FK "+
  			" WHERE NPP_GS.NGAYBATDAU <='"+nam+"-"+thang+"-01' AND SUBSTRING(NPP_GS.NGAYKETTHUC,1,4)='2100' AND CT.pk_seq="+nhId+
  			" GROUP BY CT.KBH_FK,CT.THANG,CT.NAM,CT.DVKD_FK,GSBH.PK_SEQ";
  			if(!db.update(sql)){
  				db.getConnection().rollback();
  				System.out.println(sql);
  				return "Không thể cập nhật " + sql;
  			}

  			sql="INSERT INTO CHITIEUPRI "+
  			" SELECT CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK,'ASM' ,ASM.PK_SEQ ,SUM( ISNULL(BG.GIAMUANPP,0) * CT_SKU.CHITIEU) AS CHITIEUPRI "+
  			" ,ASM_KV.KHUVUC_FK FROM CHITIEUSKUIN CT INNER JOIN CHITIEUSKUIN_SKU CT_SKU "+
  			" ON CT.PK_SEQ=CT_SKU.CHITIEUSKUIN_FK "+
  			" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=CT_SKU.NPP_FK "+
  			" INNER JOIN ASM_KHUVUC ASM_KV ON NPP.KHUVUC_FK=ASM_KV.KHUVUC_FK "+
  			" INNER JOIN ASM ON ASM.PK_SEQ=ASM_KV.ASM_FK "+
  			" LEFT JOIN "+
  			" ( "+
  			" SELECT DISTINCT BGM.KENH_FK AS KBH_FK,BGM_SP.SANPHAM_FK,BGMNPP.NPP_FK,BGM_SP.GIAMUANPP AS GIAMUANPP "+
  			" FROM BANGGIAMUANPP_NPP BGMNPP INNER JOIN BANGGIAMUANPP BGM ON BGM.PK_SEQ = BGMNPP.BANGGIAMUANPP_FK "+
  			" INNER JOIN BGMUANPP_SANPHAM BGM_SP ON BGM_SP.BGMUANPP_FK = BGM.PK_SEQ "+
  			" ) AS BG "+
  			" ON BG.SANPHAM_FK=CT_SKU.SKU AND BG.KBH_FK=CT.KBH_FK AND BG.NPP_FK=CT_SKU.NPP_FK "+
  			" WHERE ASM_KV.NGAYBATDAU <='"+nam+"-"+thang+"-01' AND SUBSTRING(ASM_KV.NGAYKETTHUC,1,4)='2100'   AND CT.pk_seq="+nhId+
  			" GROUP BY CT.THANG,CT.NAM,CT.DVKD_FK,CT.KBH_FK ,ASM.PK_SEQ,ASM_KV.KHUVUC_FK ";

  			if(!db.update(sql))
  			{
  				System.out.println(sql);
  				db.getConnection().rollback();
  				return   sql;
  			}
  			db.getConnection().commit();
  			db.getConnection().setAutoCommit(true);
  			db.shutDown();
  			return "";
  		} 
  		catch (Exception e)
  		{ 
  			db.update("rollback");
  			return "Không thể chốt! Lỗi: " + e.getMessage(); 
  		}
  		
  	}
  	
  	private String DeleChiTieuTT(String ctId)
  	{
  		dbutils db = new dbutils();
  		
  		try 
  		{
  			db.getConnection().setAutoCommit(false);
  			if(!db.update("delete chitieuskuin_tt  where pk_seq ='" + ctId + "'"))
  			{
  				
  				db.getConnection().rollback();
  				return "Lỗi "+ "delete chitieuskuin_tt  where pk_seq ='" + ctId + "'";
  			}
  			db.getConnection().commit();
  			db.getConnection().setAutoCommit(true);
  			db.shutDown();
  			return "";
  		} 
  		catch (Exception e)
  		{ 
  			db.update("rollback");
  			return "Không thể xóa! Lỗi: " + e.getMessage(); 
  		}
  	}
  	
	
}
