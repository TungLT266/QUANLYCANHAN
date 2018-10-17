package geso.traphaco.distributor.servlets.donhangtratb;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.donhangtratb.IDonhangtraTB;
import geso.traphaco.distributor.beans.donhangtratb.IDonhangtraTBList;
import geso.traphaco.distributor.beans.donhangtratb.imp.DonhangtraTB;
import geso.traphaco.distributor.beans.donhangtratb.imp.DonhangtraTBList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DonhangtraTBSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
   
    public DonhangtraTBSvl() {
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
	    
	    IDonhangtraTBList obj = new DonhangtraTBList();
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    String ctskuId = util.getId(querystring);
	    
	    if(action.trim().equals("duyet"))
	    {
	    	String nppId = request.getParameter("nppId");
	    	obj.setMsg(ChotDonHang(ctskuId, nppId));
	    	System.out.println("___KET QUA CHOT NHAN HANG: " + obj.getMsg());
	    }
	    
	    if(action.trim().equals("delete"))
	    {
	    	XoaChiTieu(ctskuId);
	    }

	    obj.init("");
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangTraTB.jsp";
		response.sendRedirect(nextJSP);
	}

	private String ChotDonHang(String ctskuId, String nppId) 
	{
		dbutils db = new dbutils();
    	String msg = "";
    	try 
    	{
			db.getConnection().setAutoCommit(false);
			
			String query= "select convert(varchar(10),DATEADD(day,1,(select max(ngayks) from khoasongay where npp_fk='"+nppId+"' )),20) as NgayKs ";
			
			ResultSet rs =db.get(query);
			
			String ngayKsTang1="";
			if(rs==null)
			{
				db.getConnection().rollback();
	    		msg = "Không thể chốt đon hàng: " + query;
				return msg;
			}
			while(rs.next())
			{
				ngayKsTang1 = rs.getString("NgayKs");
			}
			rs.close();	
		
			query = "update DONHANGTRATRUNGBAY set trangthai = '1',NgayChot='"+ngayKsTang1+"' where pk_seq = '" + ctskuId + "' ";
	    	if(!db.update(query))
	    	{
	    		db.getConnection().rollback();
	    		msg = "Không thể chốt đon hàng: " + query;
				return msg;
	    	}
	    	
	    	//Cap nhat kho
	    	query = "select a.khachhang_fk, a.sanpham_fk, a.thanhtoan, b.kbh_fk " +
	    			"from DONHANGTRATRUNGBAY_CHITIET a inner join KHACHHANG b on a.khachhang_fk = b.pk_seq " +
	    			"where donhang_fk = '" + ctskuId + "' and SANPHAM_FK is not null and ThanhToan > 0";
	    	ResultSet rsChitiet = db.get(query);
	    	if(rsChitiet != null)
	    	{
	    		while(rsChitiet.next())
	    		{
	    			String kbh_fk = rsChitiet.getString("kbh_fk");
	    			String sanpham_fk = rsChitiet.getString("sanpham_fk");
	    			String khachhang_fk = rsChitiet.getString("khachhang_fk");
	    			
	    			long soluong = Math.round(rsChitiet.getDouble("thanhtoan"));
	    			
	    			//Tu dong de xuat BEAN / LO
					query = "select SANPHAM_FK, isnull(AVAILABLE, 0) as AVAILABLE, SOLO " +
							"from NHAPP_KHO_CHITIET  " +
							"where NPP_FK = '" + nppId + "' and SANPHAM_FK = '" + sanpham_fk + "' and KBH_FK = '" + kbh_fk + "' and kho_fk = '100000' " +
							"order by ngayhethan asc, AVAILABLE asc";
									
					System.out.println("2.Check soluong san pham: " + query);
									
					ResultSet rsSpDetail = db.get(query);
					if(rsSpDetail != null)
					{
						double tongluong = 0;
						while( rsSpDetail.next() )
						{
							long avaiD = rsSpDetail.getLong("AVAILABLE");
							String soloD = rsSpDetail.getString("SOLO");
							
							if(avaiD > 0)
							{
								tongluong += avaiD;
								if(tongluong <= soluong)
								{
									query = "Update NHAPP_KHO_CHITIET set soluong = soluong - '" + avaiD + "', AVAILABLE = AVAILABLE - '" + avaiD + "'  " +
											"where NPP_FK = '" + nppId + "' and SANPHAM_FK = '" + sanpham_fk + "' and KBH_FK = '" + kbh_fk + "' and solo = '" + soloD + "' and kho_fk = '100000' ";
									if(!db.update(query))
									{
										db.getConnection().rollback();
							    		msg = "1.Không thể chốt đon hàng: " + query;
										return msg;
									}
									
									query = "Update NHAPP_KHO set soluong = soluong - '" + avaiD + "', AVAILABLE = AVAILABLE - '" + avaiD + "'  " +
											"where NPP_FK = '" + nppId + "' and SANPHAM_FK = '" + sanpham_fk + "' and KBH_FK = '" + kbh_fk + "' and kho_fk = '100000'  ";
									if(!db.update(query))
									{
										db.getConnection().rollback();
							    		msg = "2.Không thể chốt đon hàng: " + query;
										return msg;
									}
									
									query=
											"INSERT INTO DONHANGTRATRUNGBAY_CHITIET_SOLO(DONHANG_FK,KHACHHANG_FK,SANPHAM_FK,SOLUONG,SOLO)"+
											"	select  '"+ctskuId+"','"+khachhang_fk+"','"+sanpham_fk+"','"+avaiD+"','"+soloD+"'";
									if(!db.update(query))
									{
										db.getConnection().rollback();
							    		msg = "1.Không thể chốt đon hàng: " + query;
										return msg;
									}
									
								}
								else
								{
									double slg = soluong - (tongluong - avaiD);
									
									query = "Update NHAPP_KHO_CHITIET set soluong = soluong - '" + slg + "', AVAILABLE = AVAILABLE - '" + slg + "'  " +
											"where NPP_FK = '" + nppId + "' and SANPHAM_FK = '" + sanpham_fk + "' and KBH_FK = '" + kbh_fk + "' and solo = '" + soloD + "' and kho_fk = '100000'  ";
									if(!db.update(query))
									{
										db.getConnection().rollback();
							    		msg = "3.Không thể chốt đon hàng: " + query;
										return msg;
									}
									
									query = "Update NHAPP_KHO set soluong = soluong - '" + slg + "', AVAILABLE = AVAILABLE - '" + slg + "'  " +
											"where NPP_FK = '" + nppId + "' and SANPHAM_FK = '" + sanpham_fk + "' and KBH_FK = '" + kbh_fk + "' and kho_fk = '100000'  ";
									if(!db.update(query))
									{
										db.getConnection().rollback();
							    		msg = "4.Không thể chốt đon hàng: " + query;
										return msg;
									}
									
									query=
											"INSERT INTO DONHANGTRATRUNGBAY_CHITIET_SOLO(DONHANG_FK,KHACHHANG_FK,SANPHAM_FK,SOLUONG,SOLO)"+
											"	select  '"+ctskuId+"','"+khachhang_fk+"','"+sanpham_fk+"','"+slg+"','"+soloD+"'";
									if(!db.update(query))
									{
										db.getConnection().rollback();
							    		msg = "1.Không thể chốt đon hàng: " + query;
										return msg;
									}
									
									tongluong = tongluong - avaiD + slg;
							
									break;
								}
							}
						}
						rsSpDetail.close();
						if(tongluong != soluong)
						{
							msg = "5.Tồn kho không đủ. Vui lòng kiểm tra lại tồn kho.";
							db.getConnection().rollback();
							System.out.println("[TongLuong]"+tongluong+"[SoLuong]"+soluong);
							return msg;
						}
					}
	    		}			
	    	}
	    	
	    	db.getConnection().commit();
	    	db.getConnection().setAutoCommit(true);
	    	db.shutDown();
		} 
    	catch (Exception e)
    	{
    		try 
    		{
				db.getConnection().rollback();
			} 
    		catch (SQLException e1) {}
    		
    		msg = "6.Lỗi khi chốt đơn hàng: " + e.getMessage();
    		return msg;
    	}
    	
    	return "";
	}

	private void XoaChiTieu(String ctskuId) 
	{
		dbutils db = new dbutils();
    	
    	try 
    	{
			db.getConnection().setAutoCommit(false);
			
	    	if(!db.update("delete DONHANGTRATRUNGBAY_CHITIET where donhang_fk = '" + ctskuId + "'"))
	    	{
	    		db.getConnection().rollback();
				return;
	    	}
	    	
	    	if(!db.update("delete DONHANGTRATRUNGBAY where pk_seq = '" + ctskuId + "'"))
			{
				db.getConnection().rollback();
				return;
			}
	    	db.getConnection().commit();
	    	db.shutDown();
		} 
    	catch (SQLException e)
    	{
    		try 
    		{
				db.getConnection().rollback();
			} catch (SQLException e1) {}
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
	    IDonhangtraTBList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IDonhangtraTB tctsku = new DonhangtraTB();
    		tctsku.setUserId(userId);
    		tctsku.createRs();
    		
	    	session.setAttribute("tctskuBean", tctsku);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/DonHangTraTBNew.jsp");
	    }
	    else
	    {
	    	obj = new DonhangtraTBList();
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/DonHangTraTB.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IDonhangtraTBList obj) 
	{
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppIds(nppId);
		
		String sql = "select d.scheme, a.pk_seq, a.ngaydonhang, a.lanthanhtoan, a.ghichu, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua   " +
					  "from DONHANGTRATRUNGBAY a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq inner join NHANVIEN c on a.NGUOISUA = c.pk_seq  " +
					  	"inner join CTTRUNGBAY d on a.cttb_fk = d.pk_seq " +
					  "where a.npp_fk = '" + nppId + "' " +
					  "order by a.ngaydonhang desc";
		
		return sql;
	}

}
