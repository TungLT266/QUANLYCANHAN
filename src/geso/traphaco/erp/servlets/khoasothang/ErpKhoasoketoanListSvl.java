package geso.traphaco.erp.servlets.khoasothang;
import geso.traphaco.erp.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.khoasothang.IErpKhoasoketoan;
import geso.traphaco.erp.beans.khoasothang.IErpkhoasoketoanlist;
 
import geso.traphaco.erp.beans.khoasothang.imp.ErpKhoasoketoan;
import geso.traphaco.erp.beans.khoasothang.imp.ErpKhoasoketoanlist;
 
 

import java.io.IOException;
import java.sql.ResultSet;
 

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKhoasoketoanListSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public ErpKhoasoketoanListSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpkhoasoketoanlist obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id=util.getId(querystring);
	    
	    obj = new ErpKhoasoketoanlist();
	    obj.setUserId(userId);
	    String action=util.getAction(querystring);
	    
	  String congtyid= (String) session.getAttribute("congtyId");
	    
	     if(action.equals("mokhoaso")){
	    	String msg= this.Mokhoaso(id,userId,congtyid);
	    	  obj.setMsg(msg);
	     }  
	   
	    	
	    String task = request.getParameter("task");
	    if(task == null)
	    	task = "";
	    
	    String nextJSP = "";
	     
	    obj.Init();
		nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhoasoketoanlist.jsp";

	    session.setAttribute("obj", obj);
	    response.sendRedirect(nextJSP);
	}
	private String Mokhoaso(String id, String userId,String congtyid) {
		dbutils db=new dbutils();
		// TODO Auto-generated method stub
		System.out.println("đa vao day : ");
		try{
			
			String query="SELECT PK_SEQ FROM ERP_KHOASOKETOAN_LIST WHERE IS_THANGBATDAU='1' and PK_SEQ="+id;
			System.out.println(query);
			ResultSet rs1=db.get(query);
			if(rs1.next()){
				rs1.close();
				return "KHông thể mở tháng khóa sổ đầu tiên ";
			}
			rs1.close();
			
			 query=" SELECT TOP 1 THANGKS,NAM FROM ERP_KHOASOKETOAN WHERE CONGTY_FK="+congtyid+"  order by NAM desc, THANGKS desc";
			System.out.println("Du lieu : "+query);
			ResultSet rs=db.get(query);
			int thang_kstruoc=0;
			int nam_kstruoc=0;
			if(rs.next()){
				thang_kstruoc=rs.getInt("THANGKS");
				nam_kstruoc=rs.getInt("NAM");
			}
			 
			query="SELECT PK_SEQ FROM ERP_KHOASOKETOAN_LIST WHERE thangks="+thang_kstruoc+" and nam="+nam_kstruoc+" and PK_SEQ="+id;
			System.out.println(query);
			  rs1=db.get(query);
			if(!rs1.next()){
				rs1.close();
				return "Vui lòng chỉ được mở khóa sổ tháng gần nhất: "+thang_kstruoc+"-"+nam_kstruoc;
			}
			rs1.close();
			
			db.getConnection().setAutoCommit(false);
			
			String loaichungtu = "Kết chuyển cuối tháng kế toán";
			if(! Revert_KeToan( loaichungtu,id,"",db,thang_kstruoc,nam_kstruoc )){
				db.getConnection().rollback();
				return "Không thể revert kế toán kết chuyển";
			}
			query=" DELETE ERP_CHUNGTUKETCHUYENKETOAN WHERE KHOASOKETOANLIST_FK ="+id;
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "Không thể cập nhật dòng lệnh : "+query;
			}
			
			//ERP_TAIKHOAN_NOCO_KHOASO ,ERP_KHOASOKETOAN
			  query=" SELECT THANGKS,NAM,congty_fk FROM  ERP_KHOASOKETOAN_LIST  WHERE PK_SEQ="+id;
			  rs=db.get(query);
			  if(rs.next()){
				String thang=rs.getString("THANGKS");
				String NAM=rs.getString("NAM");
				String congty_fk=rs.getString("congty_fk");
				 
				query=" DELETE ERP_TAIKHOAN_NOCO_KHOASO WHERE THANG="+thang+" AND NAM="+NAM+"  ";
				if(!db.update(query)){
					db.getConnection().rollback();
					return "Không thể cập nhật dòng lệnh : "+query;
				}
				
				query="DELETE ERP_KHOASOKETOAN WHERE THANGKS="+thang+" AND nam="+NAM+" AND CONGTY_FK="+congtyid;
				if(!db.update(query)){
					db.getConnection().rollback();
					return "Không thể cập nhật dòng lệnh : "+query;
				}
				
				query=" delete   ERP_KHOASOKETOAN_LIST   WHERE PK_SEQ="+id;
				if(!db.update(query)){
					db.getConnection().rollback();
					return "Không thể cập nhật dòng lệnh : "+query;
				}
				
				
			}
			rs.close();
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			db.update("rollback");
			er.printStackTrace();
			return er.getMessage();
		}
		return "";
	}
	private boolean Revert_KeToan(String loaichungtu, String sochungtu, String idlog, dbutils db, int thang, int nam)
	{
		try 
		{
			
		//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
		String query =  " select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
					    " from ERP_PHATSINHKETOAN " +
					    " where LOAICHUNGTU = N'" + loaichungtu.trim() + "' and SOCHUNGTU = '" + sochungtu + "' ";
		
		 
		ResultSet rsPSKT = db.get(query);
			while(rsPSKT.next())
			{
				String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
				String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
				double NO = rsPSKT.getDouble("NO");
				double CO = rsPSKT.getDouble("CO");
				double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
				
				//NEU LA CO THI BAY GIO GHI GIAM CO LAI
				if( NO > 0 )
				{
					query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
							" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" +thang + "' and NAM = '" + nam + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
				}
				else
				{
					query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
							" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + thang + "' and NAM = '" + nam + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
				}
				
				//System.out.println("1.REVERT NO-CO: " + query);
				
				if(db.updateReturnInt(query)<0)
				{
					 
					return false;
				}
				
				
			}
			rsPSKT.close();
		 		
			query=   "  delete  ERP_PHATSINHKETOAN " +
		    " where LOAICHUNGTU = N'" + loaichungtu.trim() + "' and SOCHUNGTU = '" + sochungtu + "' ";
			if(db.updateReturnInt(query)<0)
			{
				 
				return false;
			}
			return true;
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
			
		}
	 
	
		 
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpkhoasoketoanlist obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	   
	    obj = new  ErpKhoasoketoanlist();
	    obj.setUserId(userId);
	    
	    String thang = util.antiSQLInspection(request.getParameter("thang"));
	    if(thang == null)
	    	thang = "0";
	    obj.setThang(Integer.parseInt(thang));
	    
	    String nam = util.antiSQLInspection(request.getParameter("nam"));
	    if(nam == null)
	    	nam = "0";
	    obj.setNam(Integer.parseInt(nam));
	    
	    
	    
	    String action = request.getParameter("action");
	    if(action == null)
	    	action = "";
	    
	    String msg = "";
	    String nextJSP = "";
	    
	    if(action.equals("kskt"))
	    {
 	    }
	    else if(action.equals("taomoi"))
	    {
	    	IErpKhoasoketoan Obj =new ErpKhoasoketoan();
	    	Obj.setCtyId((String)session.getAttribute("congtyId"));
	    	Obj.setUserId(userId);
	 
	    	Obj.init();
	    	
	    	
	    	nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhoaSoKeToan.jsp";
	 		 session.setAttribute("obj", Obj);
	 	    response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	 
	    }
	    
	   
	   
	}

}
