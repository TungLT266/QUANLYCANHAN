package geso.traphaco.erp.servlets.butrucongno;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.butrucongno.IButrucongno;
import geso.traphaco.erp.beans.butrucongno.IButrucongnoList;
import geso.traphaco.erp.beans.butrucongno.imp.Butrucongno;
import geso.traphaco.erp.beans.butrucongno.imp.ButrucongnoList;

public class ButrucongnoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
    public ButrucongnoSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String userId;

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
//	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	    
	    Utility util = new Utility();
//	    out = response.getWriter();
	    String querystring = request.getQueryString();
	    String action = util.getAction(querystring);	    
		
		IButrucongnoList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
//	    out  = response.getWriter();
    
	    userId = util.getUserId(querystring);
	    util.setSearchToHM(userId, session, this.getServletName(), "");
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	   
	    String xnkhId = util.getId(querystring);
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj = new ButrucongnoList();
	    obj.setUserId(userId);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setnppdangnhap(util.getIdNhapp(userId));
	    
	    //XÓA PHIẾU
	    if (action.equals("delete"))
	    {	   		  	    	
	    	Delete(xnkhId, userId);	    	
	    }
	    //CHỐT PHIẾU
	    else if (action.equals("chot"))
	    {
	    	String msg = Chot(xnkhId, userId, ctyId);
	    	if(msg.trim().length() > 0)
	    	{
	    		  obj.setMgs(msg);
	    	}
	    }
	    
	    obj.init("");
	    
	    session.setAttribute("userId", userId);
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/BuTruCongNoKH.jsp";
		response.sendRedirect(nextJSP);
	}

	private String Chot(String id, String userId, String ctyId) //THỰC HIỆN CHỐT KẾ TOÁN
	{
	    Utility util = new Utility();
		dbutils db = new dbutils();
		String msg ="";
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			db.update("update ERP_BUTRUKHACHHANG set trangthai = '1', nguoisua = "+userId+" where pk_seq='" + id + "' and trangthai=0");
			String query = " select bt.PK_SEQ, bt.TIENTE_FK , ISNULL(TIGIA,1) AS TIGIA,isnull(diengiai,'') as diengiai,isnpp as isnppchuyenno,isnppnhanno as isnppnhanno, \n" +
						   "         bt.KH_CHUYENNO , bt.KH_NHANNO, bt.NGAYBUTRU , bt.TONGTIEN ,bt.sochungtu ,\n"+
					       "        ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13111000' AND CONGTY_FK = "+ctyId+") as taikhoanNO,  \n" +
					       "        ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13111000' AND CONGTY_FK = "+ctyId+") as taikhoanCO  \n" +
					       " from ERP_BUTRUKHACHHANG bt \n" +
					       " where bt.PK_SEQ = "+ id +" ";
			System.out.println("CAI KT "+query);
			ResultSet rsKT = db.get(query);
			if(rsKT!= null)
			{
				while(rsKT.next())
				{
					String nam =  rsKT.getString("NGAYBUTRU").substring(0, 4);
					String thang =  rsKT.getString("NGAYBUTRU").substring(5, 7);
					
					String madoituongNO = rsKT.getString("KH_NHANNO");
					String madoituongCO = rsKT.getString("KH_CHUYENNO");
					String dienGiai = rsKT.getString("DIENGIAI");
					String isnppno = rsKT.getString("isnppnhanno");
					String isnppco = rsKT.getString("isnppchuyenno");
					String tienteId = rsKT.getString("TIENTE_FK");
					String tigia = rsKT.getString("TIGIA");
					String maChungTu = rsKT.getString("SOCHUNGTU");
					String taikhoanNO = rsKT.getString("taikhoanNO") == null ? "":  rsKT.getString("taikhoanNO");
					String taikhoanCO = rsKT.getString("taikhoanCO") == null ? "":  rsKT.getString("taikhoanCO");
					
					double tongtien =  rsKT.getDouble("TONGTIEN");
					
					if(tongtien > 0)
					{
						if(taikhoanNO.trim().length() <= 0 || taikhoanCO.trim().length() <= 0 )
						{
							msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							rsKT.close();
							db.getConnection().rollback();
							return msg;
						}
												
						/*msg = util.Update_TaiKhoan(db, thang, nam,  rsKT.getString("NGAYBUTRU"),  rsKT.getString("NGAYBUTRU"), "Bù trừ công nợ",  id , taikhoanNO, taikhoanCO, "",
								Double.toString(tongtien), Double.toString(tongtien), "Khách hàng", madoituongNO, "Khách hàng", madoituongCO, "0", "", "", tienteId, "", tigia, Double.toString(tongtien), Double.toString(tongtien), "");*/
						msg = util.Update_TaiKhoan_Vat_DienGiai_CHIKHAC(db, thang, nam, rsKT.getString("NGAYBUTRU"), rsKT.getString("NGAYBUTRU"), "Bù trừ công nợ", id, taikhoanNO, taikhoanCO, "", Double.toString(tongtien), Double.toString(tongtien),
								"Khách hàng", madoituongNO, "Khách hàng", madoituongCO, "0", "", "", tienteId, "", 
								tigia, Double.toString(tongtien), Double.toString(tongtien), "", "", dienGiai, maChungTu, isnppno, isnppco, "", "",
								"", "", "", "", "", "", "", "", "", "", "", "",
								"", "", "", "", "", "", "");
												
						if(msg.trim().length() > 0)
						{
							rsKT.close();
							msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							db.getConnection().rollback();
							return msg;
						}
					}
						
				}
				rsKT.close();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception e)
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
				msg = "Lỗi khi chốt thu tiền: " + e.getMessage();
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally
		{
			db.shutDown();
		}
		
		return msg;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
//	    PrintWriter out = response.getWriter();
	    
	    IButrucongnoList obj  = new ButrucongnoList();
	    
	    String userId;
	    Utility util = new Utility();
		
	    HttpSession session = request.getSession();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setnppdangnhap(util.getIdNhapp(userId));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }        
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String chungtu=util.antiSQLInspection(request.getParameter("chungtu"));
		if (chungtu == null)
			chungtu = "";
	          
	    if (action.equals("new"))
	    {
	    	IButrucongno nvgnBean = (IButrucongno) new Butrucongno("");
	    	nvgnBean.setUserId(userId);
	    	nvgnBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	nvgnBean.setnppdangnhap(util.getIdNhapp(userId));
	    	nvgnBean.createRS();
	    	
	    	session.setAttribute("nvgnBean", nvgnBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Erp/BuTruCongNoKHNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else if (action.equals("delete"))
	    {	   		  	    	
	    	Delete(chungtu, userId);
	    	
	    	String search = this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	
	    	String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    	geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    				
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/BuTruCongNoKH.jsp");	   
	    }
	    //CHỐT PHIẾU
	    else if (action.equals("chot"))
	    {
	    	String msg = Chot(chungtu, userId, ctyId);
	    	if(msg.trim().length() > 0)
	    	{
	    		  obj.setMgs(msg);
	    	}
	    	
	    	String search = this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	
	    	String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    	geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    				
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/BuTruCongNoKH.jsp");	   
	    }
	    else
	    {
	    	String search = this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	
	    	String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    	geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    				
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/BuTruCongNoKH.jsp");	    		    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IButrucongnoList obj) 
	{	
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if ( tungay == null)
    		tungay = "";
    	obj.setTungay(tungay);
    	
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if ( denngay == null)
    		denngay = "";
    	obj.setDenngay(denngay);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai")); 	
    	if (trangthai == null)
    		trangthai = "";    			
    	obj.setTrangthai(trangthai);
    	
    	String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu")); 	
    	if (sochungtu == null)
    		sochungtu = "";    			
    	obj.setSochungtu(sochungtu);    	
    	
    	String khchuyenno = util.antiSQLInspection(request.getParameter("khchuyenno")); 	
    	if (khchuyenno == null)
    		khchuyenno = "";    			
    	obj.setKhChuyenNo(khchuyenno);
    	
    	String khnhanno = util.antiSQLInspection(request.getParameter("khnhanno")); 	
    	if (khnhanno == null)
    		khnhanno = "";    			
    	obj.setKhNhanNo(khnhanno);
    	
    	String sotien = util.antiSQLInspection(request.getParameter("sotientt").replaceAll(",", "")); 	
    	if (sotien == null)
    		sotien = "";    			
    	obj.setSotien(sotien);
    	
    	//CÂU SEARCH
    	
    	String query =
    		
		" SELECT 	a.PK_SEQ, a.NGAYBUTRU, a.TRANGTHAI, CASE a.isNPP WHEN 0 then a_kh.TEN WHEN 1 THEN a_npp.TEN WHEN 2 THEN a_nv.TEN END as KH_CHUYENNO, " +
		"			CASE a.isNPPNHANNO WHEN 0 then b_kh.TEN WHEN 1 THEN b_npp.TEN WHEN 2 THEN b_nv.TEN END as KH_NHANNO, a.TONGTIEN \n"+
		" FROM 	ERP_BUTRUKHACHHANG a \n"+
		" LEFT JOIN KHACHHANG a_kh on a.KH_CHUYENNO = a_kh.PK_SEQ \n"+
		" LEFT JOIN NHAPHANPHOI a_npp on a.KH_CHUYENNO = a_npp.PK_SEQ \n"+
		" LEFT JOIN ERP_NHANVIEN a_nv on a.KH_CHUYENNO = a_nv.PK_SEQ \n"+
		" LEFT JOIN KHACHHANG b_kh on a.KH_NHANNO = b_kh.PK_SEQ \n"+
		" LEFT JOIN NHAPHANPHOI b_npp on a.KH_NHANNO = b_npp.PK_SEQ \n"+
		" LEFT JOIN ERP_NHANVIEN b_nv on a.KH_NHANNO = b_nv.PK_SEQ \n"+		 				
		" WHERE 1=1 and a.CONGTY_FK = "+obj.getCongtyId()+" ";
	
    	if (trangthai.trim().length() > 0)
    	{
    		query = query + " and a.trangthai='" + trangthai + "'";
    	}
    	if (tungay.trim().length() > 0)
    	{
    		query = query + " and a.NGAYBUTRU >= '" + tungay + "'";
    	}
    	if (denngay.trim().length() > 0)
    	{
    		query = query + " and a.NGAYBUTRU <= '" + denngay + "'";
    	} 
    	if(khchuyenno.trim().length()>0){
    		query = query + " and b.Ten  like N'%"+khchuyenno+"%'";
    	}
    	if(khnhanno.trim().length()>0){
    		query = query + " and c.Ten  like N'%"+khnhanno+"%'";
    	}
    	if(sochungtu.trim().length()>0){
    		query = query + " and a.PK_SEQ ='"+sochungtu+"'";
    	}
    	if(sotien.trim().length()>0)
    	{
    		query = query + " and a.TONGTIEN ='"+sotien+"'";
    	}
    	query+=" order by a.pk_seq desc ";
    	
    	System.out.println("\n SEARCH ________: "+query);		
    	
    	return query;
	}

	private void Delete(String id, String userId) 
	{
		dbutils db = new dbutils(); 
		
		db.update("update ERP_BUTRUKHACHHANG set trangthai = '2', NGUOISUA = "+userId+"  where pk_seq='" + id + "' and trangthai=0");		
		
		db.update("commit");
		db.shutDown();
	}
}