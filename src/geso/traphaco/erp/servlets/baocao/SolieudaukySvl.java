package geso.traphaco.erp.servlets.baocao;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.solieudauky.imp.Solieudauky;

public class SolieudaukySvl extends HttpServlet {
		     
    /**
	 * 
	 */
	private static final long serialVersionUID = 6360799135906280521L;

	public SolieudaukySvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
//		dbutils db = new dbutils();
		Solieudauky obj= new Solieudauky();
		HttpSession session = request.getSession();	    
	      
	    Utility util = new Utility();
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_Solieudauky.jsp");
		/*for (int i = 4; i <= 12; i++){
			Tinhcuoiky_2(db, "2011", "" + i);
		}
		
		for (int i = 1; i <= 12; i++){
			Tinhcuoiky_2(db, "2012", "" + i);
		}
		
		for (int i = 1; i <= 12; i++){
			Tinhcuoiky_2(db, "2013", "" + i);
		}

		for (int i = 1; i <= 12; i++){
			Tinhcuoiky_2(db, "2014", "" + i);
		}

		for (int i = 1; i <= 12; i++){
			Tinhcuoiky_2(db, "2015", "" + i);
		}

		for (int i = 1; i <= 12; i++){
			Tinhcuoiky_2(db, "2016", "" + i);
		}
		*/
		/*Tinhcuoiky_3(db,"2016","6");
		Tinhcuoiky_3(db,"2016","7");
		Tinhcuoiky_3(db,"2016","8");
		Tinhcuoiky_3(db,"2016","9");
		Tinhcuoiky_3(db,"2016","10");
		Tinhcuoiky_3(db,"2016","11");
		Tinhcuoiky_3(db,"2016","12");*/
		//Tinhcuoiky_3(db,"1","2016","7");
		//Tinhcuoiky_3(db,"1","2016","8");
		/*for (int i = 6; i <= 8; i++){
			Tinhcuoiky_3(db,"1","2016",""+i);
		}*/
		/*db.shutDown();*/
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();
		String querystring = request.getQueryString();
		HttpSession session = request.getSession();
		Solieudauky obj = new Solieudauky();
		String action = request.getParameter("action");
	    if (action == null){
	    	action = util.getAction(querystring);
	    	if (action == null)
	    		action = "";
	    }
	    String userId = request.getParameter("userId");
//	    String congTyId = (String)session.getAttribute("congtyId");
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    String check = request.getParameter("check");
	    if(check == null) check = "";
	    obj.setCheck(check);
	    if(action.equals("tao")){
	    	if(obj.getCheck().equals("OK")){
	    		obj.init();
	    	}
	    	else{
	    		obj.setMsg("Vui lòng nhập OK để tạo số liệu đầu kỳ");
	    	}
	    	String nextJSP ="/TraphacoHYERP/pages/Erp/Erp_Solieudauky.jsp";
    		session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		response.sendRedirect(nextJSP);
	    }
	    
	}
	
	protected boolean Tinhcuoiky(dbutils db, String nam, String thang){
		String lastmonth = "";
		String lastyear = "";
		
		if(thang.equals("1")){
			lastmonth = "12";
			lastyear = "" + (Integer.parseInt(nam) - 1);
		}else{
			lastmonth = "" + (Integer.parseInt(thang) - 1);
			lastyear = nam;
		}
		try{
			db.getConnection().setAutoCommit(false);
			String query = "DELETE ERP_TAIKHOAN_NOCO_KHOASO WHERE THANG = " + thang + " AND NAM = " + nam + "";
			db.update(query);
			query = "INSERT INTO ERP_TAIKHOAN_NOCO_KHOASO(TAIKHOANKT_FK, MADOITUONG, DOITUONG, NGUYENTE_FK,ISNPP,GIATRINONGUYENTE,GIATRICONGUYENTE, GIATRINOVND, GIATRICOVND,THANG, NAM) " +
					" SELECT TKID, MADOITUONG, DOITUONG,NTID,ISNPP, \n " + 
					"CASE WHEN SUM(GIATRINONT) - SUM(GIATRICONT) > 0 AND   \n " + 
					"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%'	OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
					"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%') \n " + 
					"THEN 	SUM(GIATRINONT) - SUM(GIATRICONT)  \n " + 
					"WHEN SUM(GIATRINONT) - SUM(GIATRICONT) <= 0  AND  \n " + 
					"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
					"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%')  \n " + 
					"THEN 0  ELSE SUM(GIATRINONT) END AS GIATRINONT,  \n " + 
					"CASE WHEN SUM(GIATRINONT) - SUM(GIATRICONT) < 0 AND  \n " + 
					"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%'	OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
					"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%' ) \n " + 
					"THEN (SUM(GIATRINONT) - SUM(GIATRICONT))*(-1)  \n " + 
					"WHEN SUM(GIATRINONT) - SUM(GIATRICONT) >= 0  AND \n " + 
					"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
					"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%') \n " + 
					"THEN 0 ELSE	SUM(GIATRICONT) END AS GIATRICONT, \n " + 
					" \n " + 
					" \n " + 
					"CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) > 0 AND   \n " + 
					"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%'	OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
					"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%') \n " + 
					"THEN 	SUM(GIATRINOVND) - SUM(GIATRICOVND)  \n " + 
					"WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) <= 0  AND  \n " + 
					"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
					"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%')  \n " + 
					"THEN 0  ELSE SUM(GIATRINOVND) END AS GIATRINOVND,  \n " + 
					"CASE WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) < 0 AND  \n " + 
					"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%'	OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
					"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%' ) \n " + 
					"THEN (SUM(GIATRINOVND) - SUM(GIATRICOVND))*(-1)  \n " + 
					"WHEN SUM(GIATRINOVND) - SUM(GIATRICOVND) >= 0  AND \n " + 
					"(SOHIEUTAIKHOAN LIKE '131%' OR SOHIEUTAIKHOAN LIKE '136%' OR SOHIEUTAIKHOAN LIKE '138%' \n " + 
					"OR SOHIEUTAIKHOAN LIKE '331%' OR SOHIEUTAIKHOAN LIKE '336%' OR SOHIEUTAIKHOAN LIKE '338%') \n " + 
					"THEN 0 ELSE	SUM(GIATRICOVND) END AS GIATRICOVND , " + thang + "," + nam +"   \n " + 
					"		FROM (  \n " + 
					" 			SELECT KS.TAIKHOANKT_FK AS TKID, TK.SOHIEUTAIKHOAN, KS.MADOITUONG, KS.DOITUONG, \n " + 
					"				KS.NGUYENTE_FK AS NTID,KS.ISNPP, SUM(KS.GIATRINONGUYENTE) AS GIATRINONT,SUM(KS.GIATRICONGUYENTE) AS GIATRICONT, \n " + 
					" 			SUM(KS.GIATRINOVND) AS GIATRINOVND, \n " + 
					"  			SUM(KS.GIATRICOVND) AS GIATRICOVND   \n " + 
					" 			FROM ERP_TAIKHOAN_NOCO_KHOASO KS  \n " + 
					"            INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK  \n " + 
					" 			WHERE KS.THANG = "+lastmonth+" AND KS.NAM = "+lastyear+"   \n " + 
					/*"           -- AND TK.NPP_FK IN (" +npp_fk+" )  \n " + */
					" 			GROUP BY KS.TAIKHOANKT_FK, TK.SOHIEUTAIKHOAN, KS.MADOITUONG, KS.DOITUONG,KS.NGUYENTE_FK,KS.ISNPP \n " + 
					"  			UNION ALL  \n " + 
					"  			SELECT PS.TAIKHOAN_FK AS TKID, TK.SOHIEUTAIKHOAN, PS.MADOITUONG, PS.DOITUONG, \n " + 
					"				PS.TIENTEGOC_FK AS NTID,PS.ISNPP, \n " + 
					"				CASE WHEN SUM(ISNULL(PS.NO,0)) >0 THEN SUM(TONGGIATRINT) ELSE 0 END AS GIATRINONT, \n " + 
					"				CASE WHEN SUM(ISNULL(PS.CO,0)) >0 THEN SUM(TONGGIATRINT) ELSE 0 END AS GIATRICONT, \n " + 
					" 			SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND, SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n " + 
					"				FROM ERP_PHATSINHKETOAN PS    \n " + 
					"   			INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n " + 
					"        INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n " + 
					" 			WHERE month(PS.NGAYGHINHAN) = "+thang+" and YEAR(PS.NGAYGHINHAN) = "+ nam +"  \n " + 
					/*"            -- AND TK.NPP_FK IN (" +npp_fk+" )  \n " + */
					" 			GROUP BY PS.TAIKHOAN_FK, TK.SOHIEUTAIKHOAN, PS.MADOITUONG, PS.DOITUONG,PS.TIENTEGOC_FK,PS.ISNPP  \n " + 
					"   			UNION ALL  \n " + 
					"  			SELECT PS.TAIKHOAN_FK AS TKID, TK.SOHIEUTAIKHOAN, PS.MADOITUONG, PS.DOITUONG, \n " + 
					"				PS.TIENTEGOC_FK AS NTID,PS.ISNPP, \n " + 
					"				CASE WHEN SUM(ISNULL(PS.NO,0)) >0 THEN SUM(TONGGIATRINT) ELSE 0 END AS GIATRINONT, \n " + 
					"				CASE WHEN SUM(ISNULL(PS.CO,0)) >0 THEN SUM(TONGGIATRINT) ELSE 0 END AS GIATRICONT, \n " + 
					" 			SUM(ROUND(ISNULL(PS.NO, 0), 0)) AS GIATRINOVND, SUM(ROUND(ISNULL(PS.CO, 0), 0)) AS GIATRICOVND \n " + 
					"				FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN PS    \n " + 
					"   			INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK  \n " + 
					"        INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK  \n " + 
					" 			WHERE month(PS.NGAYGHINHAN) = "+thang+" and YEAR(PS.NGAYGHINHAN) = "+ nam +"  \n " + 
					/*"            -- AND TK.NPP_FK IN (" +npp_fk+" )  \n " + */
					" 			GROUP BY PS.TAIKHOAN_FK, TK.SOHIEUTAIKHOAN, PS.MADOITUONG, PS.DOITUONG,PS.TIENTEGOC_FK,PS.ISNPP \n " + 
					"  		)DAUKY_PHATSINH_THEODOITUONG  \n " + 
					"GROUP BY DAUKY_PHATSINH_THEODOITUONG.TKID,DAUKY_PHATSINH_THEODOITUONG.MADOITUONG, \n " + 
					"DAUKY_PHATSINH_THEODOITUONG.DOITUONG,DAUKY_PHATSINH_THEODOITUONG.SOHIEUTAIKHOAN, \n " + 
					"DAUKY_PHATSINH_THEODOITUONG.NTID,DAUKY_PHATSINH_THEODOITUONG.ISNPP";
			System.out.println("-----Chạy lại số liệu : " + thang + " - " + nam + " ------------");
			System.out.println(query);
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception ex){
			ex.printStackTrace();
			try{
				db.getConnection().rollback();
				
			}catch(SQLException ex1){
				ex1.printStackTrace();
			}
			
			return false;
		}
		return true;
	}


	
}
