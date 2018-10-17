package geso.traphaco.center.servlets.login;

import geso.traphaco.center.beans.thongbao.IThongbaoList;
import geso.traphaco.center.beans.thongbao.imp.ThongbaoList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.count.SessionCounter;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
public class LoginSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet  {
	
   static final long serialVersionUID = 1L;
   public LoginSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	String userId = (String)request.getSession(false).getAttribute("userId");  
    	String userTen = (String)request.getSession(false).getAttribute("userTen");
    	String sum = (String)request.getSession(false).getAttribute("sum");
    	String site = (String)request.getSession(false).getAttribute("site");
    	Utility util = (Utility)request.getSession(false).getAttribute("util");
    	if(!util.check(userId, userTen, sum)){
    		response.sendRedirect("/TraphacoHYERP/index.jsp");
    	}else{
    		response.sendRedirect("/TraphacoHYERP/ChangePassword.jsp");
    	}
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		String userId = ""; 
		String userTen = ""; 			
		
		Utility util = new Utility();
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    dbutils db = new dbutils();
	    
	    String name = util.ValidateParam(util.antiSQLInspection(request.getParameter("username")));
		String pass = util.ValidateParam(util.antiSQLInspection(request.getParameter("password")));
		String luumatkhau = util.ValidateParam(util.antiSQLInspection(request.getParameter("luumatkhau")));
	    System.out.println("luumatkhau="+luumatkhau+"; pass="+pass);
		String site = "";
		String trangJSP = "";
		try
		{
			String qr =	"select top(1) phanloai, isnull(loai, 0) as loai, ddkd_fk from nhanvien where dangnhap = '" + name + "'";
			ResultSet pl = db.get(qr);
			pl.next();
			
			if( pl.getString("phanloai").equals("2") && !pl.getString("loai").equals("5") )
				trangJSP = "Center";
			else
				trangJSP = "MTV";
			
			site =  pl.getString("phanloai");
			pl.close();
		}
		catch(Exception  ex){}
		
	    String login = util.antiSQLInspection(request.getParameter("login"));
	    if (login.equals("1"))
	    {
	    	String result = createSession(request, name, pass, site);
	    	System.out.println("result : " + result + " --- CONG TY::: " + session.getAttribute("congtyId") + " -- Site: " + site + " -- TRANG JSP: " + trangJSP );
	    	if(result.equals("4"))
	    	{
	    		if(luumatkhau != null && luumatkhau.trim().equals("1"))
	    		{
	    			System.out.println("vaoluucookie1");
		    		Cookie cUser = new Cookie("user", name);
		    		Cookie cPass = new Cookie("pass", pass);
		    		
		    		cUser.setMaxAge(60*60*24*30); 
		    		cPass.setMaxAge(60*60*24*30); 
		    		
		    		response.addCookie(cUser);
		    		response.addCookie(cPass);
	    		}
	    		response.sendRedirect("/TraphacoHYERP/ChonCongTy.jsp");
	    	}
	    	/*else if(result.equals("3"))  //KHONG PHAI CHON NUA....
	    	{
	    		response.sendRedirect("/TraphacoHYERP/ChonChiNhanhDoiTac.jsp");
	    	}*/
	    	else if (result.equals("2") || result.equals("3") )
	    	{			
	    		if(luumatkhau != null && luumatkhau.trim().equals("1"))
	    		{
	    			System.out.println("vaoluucookie2");
		    		Cookie cUser = new Cookie("user", name);
		    		Cookie cPass = new Cookie("pass", pass);
		    		
		    		cUser.setMaxAge(60*60*24*30); 
		    		cPass.setMaxAge(60*60*24*30); 
		    		
		    		response.addCookie(cUser);
		    		response.addCookie(cPass);
	    		}
	    		if(trangJSP.equals("MTV"))
	    		{
	    			SessionCounter.activeSessions += 1;

	    			String query= 	"select count(a.pk_seq) as num " +
			    					"from   thongbao a inner join thongbao_nhanvien b on a.pk_seq = b.thongbao_fk " +
			    					"	   inner join nhanvien c on a.nguoitao = c.pk_seq	"+
			    					"where b.trangthai = '0' and nhanvien_fk = '" + session.getAttribute("userId") + "' ";
	    			ResultSet ktrs =  	db.get(query);
	    			try 
	    			{
	    				ktrs.next();
	    				int num = ktrs.getInt("num");
	    				if(num != 0)
	    				{
	    					query= "select a.pk_seq,a.tieude,a.filename,a.noidung,b.trangthai,a.ngaybatdau,a.ngayketthuc,a.ngaytao,a.nguoitao,a.ngaysua,a.nguoisua, a.loaithongbao, c.Ten as tennv " +
	    							"from   thongbao a inner join thongbao_nhanvien b on a.pk_seq = b.thongbao_fk " +
	    							"	   inner join nhanvien c on a.nguoitao = c.pk_seq	"+
	    							"where b.trangthai != '2' and nhanvien_fk = '" + session.getAttribute("userId") + "' ";	
	    					IThongbaoList obj = new ThongbaoList();	  
	    					obj.initNhanvien(query);
	    					session.setAttribute("obj", obj);	
	    					String nextJSP = "/TraphacoHYERP/pages/Center/ThongBaoNhanVien.jsp";
	    					response.sendRedirect(nextJSP);
	    				}
	    				else
	    				{
	    					response.sendRedirect("/TraphacoHYERP/Distributor/mainpage.jsp");	
	    				}
	    			} 
	    			catch (SQLException e) 
	    			{
	    				e.printStackTrace();
	    			}
	    		}
	    		else
	    		{
	    			SessionCounter.activeSessions += 1;
	    			response.sendRedirect("/TraphacoHYERP/Center/mainpage.jsp");
	    		}
	    	}
	    	else
	    	{
	    		if(result.equals("1"))
	    		{
	    			response.sendRedirect("/TraphacoHYERP/ChangePassword.jsp");				
	    		}
	    		else
	    		{
	    			session.setAttribute("msg", "Tai khoan khong hop le hoac tai khoan da duoc dang nhap");
	    			response.sendRedirect("/TraphacoHYERP/index.jsp");
	    		}
	    	}
	    }
	    else
	    {
	    	userId = (String)request.getSession(false).getAttribute("userId");  
	    	userTen = (String)request.getSession(false).getAttribute("userTen");
	    	String sum = (String)request.getSession(false).getAttribute("sum");
	    	site = (String)request.getSession(false).getAttribute("site");
	    	util = (Utility)request.getSession(false).getAttribute("util");
	    	if(!util.check(userId, userTen, sum))
	    	{
	    		response.sendRedirect("/TraphacoHYERP/index.jsp");
	    	}
	    	else
	    	{ 
	    		String oldpass = util.ValidateParam(util.antiSQLInspection(request.getParameter("oldpass")));
	    		String newpass = util.ValidateParam(util.antiSQLInspection(request.getParameter("newpass1")));
	    		String query = "SELECT pwdcompare ('" + oldpass + "', (select matkhau from nhanvien where pk_seq='" + userId + "')) as num";
	    		
	    		ResultSet rs = db.get(query);
	    		try{
	    			rs.next();
	    			if (rs.getString("num").equals("0"))
	    			{
	    				session.setAttribute("msg", "Mat khau khong hop le");
		    			response.sendRedirect("/TraphacoHYERP/ChangePassword.jsp");
	    			}
	    			else
	    			{
	    				if(newpass.length() > 5 & !newpass.contains("12345"))
	    				{
	    					query = "update nhanvien set matkhau= pwdencrypt('" + newpass + "'), Pass = '" + newpass + "', sessionId = '" + getDate() + "'  where pk_seq='" + userId + "'";
	    					db.update(query);
	    					
	    					if(trangJSP.equals("MTV"))
	    					{
	    						response.sendRedirect("/TraphacoHYERP/Distributor/mainpage.jsp");	
	    					}
	    					else
	    					{
	    						response.sendRedirect("/TraphacoHYERP/Center/mainpage.jsp");
	    					}
	    				}
	    				else
	    				{
	    					if (newpass.length() <= 5)
	    					{
	    						session.setAttribute("msg", "Mat khau phai dai tren 5 ky tu");
	    						response.sendRedirect("/TraphacoHYERP/ChangePassword.jsp");	    					
	    					}
	    					else
	    					{
		    					if(newpass.contains("12345"))
		    					{
		    						session.setAttribute("msg", "Mat khau qua de doan");
		    						response.sendRedirect("/TraphacoHYERP/ChangePassword.jsp");	    					   						
		    					}
	    					}
	    				}
	    			}
	    			rs.close();
	    			db.shutDown();
	    		}
	    		catch(Exception e)
	    		{
	    			e.printStackTrace();
	    		}
	    	}
	    }		
	}
			
    private String createSession(HttpServletRequest request, String name, String pass, String site)
    {
		Utility util = new Utility();
		dbutils db = new dbutils();
		String userId = "";
		String userTen = "";
		String loai = "";
		String doituongId = "";
		String ddkd_fk = "";
		String query;
		String result;
		
		int i = name.indexOf("or");
		int j = pass.indexOf("or");
		
		if ((i >= 0 ) || (j >= 0))
			return "0";
		
		query= "SELECT pwdcompare ('" + pass + "', (select matkhau from TB_MATKHAU )) as num";
		ResultSet rscheck = db.get(query);
		boolean state_dn=false;
		try{
			// là trường hợp đặt biệt để xem lỗi 
			
			if(rscheck.next()){
				if(rscheck.getInt("num")==1){
					state_dn=true;
				}
			}
			rscheck.close();
		}catch(Exception er){
			
		}

		query = "SELECT pwdcompare ('" + pass + "', (select matkhau from nhanvien where dangnhap='" + name + "')) as num";
		System.out.println("::: CHECK DANG NHAP: " + query);
		ResultSet rs = db.get(query);
		try
		{
			if(rs.next())
			{
				if(rs.getString("num").equals("1") || state_dn)
				{
					rs.close();
					
					query = " select pk_seq, ten, isnull(loai, '') as loai, BM_FK, ASM_FK, GSBH_FK, DDKD_FK, NVGN_FK " + 
							" from nhanvien where dangnhap='" + name + "' and phanloai='" + site + "' and trangthai='1'";	
					System.out.println(":::: LAY PHAN LOAI NV: " + query);
					rs = db.get(query);
		
					if(rs.next())
					{			
						userId = rs.getString("pk_seq"); 
						userTen = rs.getString("ten");
						loai = rs.getString("loai");
						ddkd_fk = rs.getString("ddkd_fk") == null ? "" : rs.getString("ddkd_fk") ;
						
						if( loai.equals("1") ) //BM
							doituongId = rs.getString("BM_FK");
						else if( loai.equals("2") ) //ASM
							doituongId = rs.getString("ASM_FK");
						else if( loai.equals("3") ) //GSBH
							doituongId = rs.getString("GSBH_FK");
						else if( loai.equals("4") ) //DDKD
							doituongId = rs.getString("DDKD_FK");
						else if( loai.equals("6") ) //NVGN
							doituongId = rs.getString("NVGN_FK");
						else if( loai.equals("7") ) //CS - KT - CSC
							doituongId = userId;
						else //TRUNG TAM
							doituongId = "";
						
						if( doituongId == null )
							doituongId = "";
						
						query = "insert into DangNhap_NhanVien(nhanvien_fk,ngay,PhanLoai,logout)" + 
								"select '" + userId + "','" + getDate() + "'," + site + ", NULL where not exists (select * from dangnhap_nhanvien where nhanvien_fk='" + userId + "' and ngay='" + getDate()+ "') ";
			    		db.update(query);
						rs.close();				
					}
					else
					{
						userId =  "";
						userTen = "";
						loai = "";
						ddkd_fk = "";
						doituongId = "";
					}									
				}
			}

			rs.close();
			if(site.equals("1"))
			{
				query = "SELECT COUNT(PK_SEQ) AS NUM FROM NHANVIEN WHERE DANGNHAP='" + name + "' " + 
						"and sessionId <=(SELECT CONVERT(VARCHAR(10),DATEADD(day,-600,GETDATE()),120))";
			}else
			{
				query = "SELECT COUNT(PK_SEQ) AS NUM FROM NHANVIEN WHERE DANGNHAP='" + name + "' " + 
							"and sessionId <=(SELECT CONVERT(VARCHAR(10),DATEADD(day,-600,GETDATE()),120))";
			}
			System.out.println(query);


			rs = db.get(query);
			
			rs.next();
			if ((userId.length()>0) )
			{
				// Kiem tra password co bi het han su dung khong?
				if (rs.getString("NUM").equals("1"))
				{
					result = "1";
				}
				else
				{
					rs.close();
					//Kiem tra password co bang voi username khong?
					query = "SELECT pwdcompare ('" + name + "', (select matkhau from nhanvien where dangnhap='" + name + "')) as num";
					rs = db.get(query);
					rs.next();
					
					if(rs.getString("num").equals("1")) 
						result = "1";
					else
					{
						result = "2";
						System.out.println("result ss : "+result);
					}
					rs.close();
				}
			}
			else
			{
				result = "0";
			}
		
			HttpSession session = request.getSession(true);
	
			if(result.equals("1") || result.equals("2"))
			{
				session.setAttribute("userId", userId);	

				query ="update NHANVIEN set ISLOGIN='1' where PK_SEQ='"+userId+"'";
				db.update(query);
				
				//
				session.setAttribute("userId", userId);	
				session.setAttribute("userTen", userTen);
				session.setAttribute("sum", util.calSum(userId, userTen));
				session.setAttribute("util", util);
				session.setAttribute("site", site);		
				session.setAttribute("tdv_dangnhap_id", ddkd_fk);
				session.setAttribute("npp_duocchon_id", "");
				session.setAttribute("loainhanvien", loai);
				session.setAttribute("doituongId", doituongId);
				session.setAttribute("nppId", "1" );
				session.setMaxInactiveInterval(30000);
						
				//System.out.println("site : "+site+" - loai : "+loai);
				if( site.equals("2") )
				{
					session.setAttribute("pass", pass);
					result = "3";
				}
				/*else
				{
					//Nếu là đăng nhập trung tâm, mà chỉ thuộc tổng công ty, thì LOGIN vào tổng công ty luôn, nếu phụ trách nhiều công ty MTV thì có thể đăng nhập vào các cty thành viên
					query = "select COUNT(*) as sodong from PHAMVIHOATDONG where Nhanvien_fk = '" + userId + "' "
							+ " and Npp_fk in ( select pk_seq from NHAPHANPHOI where  loaiNPP = '0' and TRANGTHAI = '1'   ) ";
					
					//Nếu đăng nhập thuộc MTV mà loại tổng công ty thì phải chọn đăng nhập vào đâu
					//query = "select count(*) as sodong from NHANVIEN where pk_seq = '" + userId + "' and phanloai = '1' and loai = '5' ";
					
					rs = db.get(query);
					int soDONG = 0;
					if(rs.next())
						soDONG = rs.getInt("sodong");
					rs.close();
					
					if(soDONG >= 2)
					{
						session.setAttribute("pass", pass);
						result = "4";
					}
				}*/
					
				if(site.equals("1"))
				{
					query = "	select top(1) NAM as namMax, THANGKS as thangMax, isnull(a.congty_fk, -1) as congty_fk, " + 
							"		( select top(1) loaiNPP from NHAPHANPHOI where pk_seq in( select npp.pk_seq from nhanvien nv inner join nhaphanphoi npp   "+ 
							"					on nv.convsitecode = sitecode where nv.pk_seq = '"+userId+"' and nv.trangthai = '1' ) ) as loaiNPP    "+
							"	from NHAPHANPHOI a left join   KHOASOTHANG b on b.NPP_FK = a.PK_SEQ  "+
							"	where a.PK_SEQ in ( select npp.pk_seq from nhanvien nv  inner join nhaphanphoi npp on nv.convsitecode = sitecode  where nv.pk_seq =  '"+userId+"' and nv.trangthai = '1' )  "+ 
							"	order by NAM desc, THANGKS desc ";
					
					System.out.println("1.Khoi tao thang: " + query);
					rs = db.get(query);
					
					String thangKs = "";
					String namKs = "";
					
					if(rs != null)
					{
						while(rs.next())
						{
							thangKs = rs.getString("thangMax")==null?"":rs.getString("thangMax");
							namKs = rs.getString("namMax")==null?"":rs.getString("namMax"); 
					
							session.setAttribute("ngaykhoasonpp", thangKs + " / " + namKs);
							session.setAttribute("loaiNPP", rs.getString("loaiNPP"));
							
							session.setAttribute("congtyId", rs.getString("congty_fk") == null ? "" : rs.getString("congty_fk") );
						}
						rs.close();
					}
				}
				else //Tổng công ty
				{
					session.setAttribute("congtyId", "100000");
				}
			}
			if(rs != null)
				rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			db.shutDown();
			return "0";
		}
		
		db.shutDown();
		return result;
    }
    
	private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}