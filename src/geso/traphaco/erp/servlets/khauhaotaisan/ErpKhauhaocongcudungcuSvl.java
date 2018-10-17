package geso.traphaco.erp.servlets.khauhaotaisan;

import geso.dms.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.khauhaotaisancodinh.IKhauhaoCCDC;
import geso.traphaco.erp.beans.khauhaotaisancodinh.IKhauhaoCCDCList;
import geso.traphaco.erp.beans.khauhaotaisancodinh.IKhauhaotaisan;
import geso.traphaco.erp.beans.khauhaotaisancodinh.imp.KhauhaoCCDC;
import geso.traphaco.erp.beans.khauhaotaisancodinh.imp.KhauhaoCCDCList;
import geso.traphaco.erp.beans.khauhaotaisancodinh.imp.Khauhaotaisan;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKhauhaocongcudungcuSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpKhauhaocongcudungcuSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String) session.getAttribute("congtyId");
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);
		if (action == null)
			action = "";


		String Id = util.getId(querystring);

		if(action.equals("display"))
		{
			IKhauhaoCCDC khccdc = new KhauhaoCCDC();
			khccdc.setCtyId(ctyId);
			khccdc.setId(Id);
			khccdc.createRs();
			session.setAttribute("khccdc", khccdc);
			response.sendRedirect("../TraphacoHYERP/pages/Erp/ErpKhauhaocongcudungcuDisplay.jsp");	
			return ;
		}
		else if(action.equals("delete"))
		{
			IKhauhaoCCDCList obj = new KhauhaoCCDCList();
			obj.setCtyId(ctyId);
			//obj.setNppId(nppId);
			obj.setUserId(userId);
			String msg = Delete(Id, userId,ctyId);
			if(msg.length() > 0)
				obj.setMsg(msg);
			obj.createRs();
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhauhaocongcudungcu.jsp";
			response.sendRedirect(nextJSP);
			//			return ;
		}
		else{

			IKhauhaoCCDCList obj = new KhauhaoCCDCList();
			obj.setCtyId(ctyId);
			obj.setUserId(userId);
			obj.createRs();

			session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhauhaocongcudungcu.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();

		IKhauhaoCCDCList obj = new KhauhaoCCDCList();
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		obj.setUserId(userId);

		String ctyId = (String) session.getAttribute("congtyId");
		String nppId = (String) session.getAttribute("nppId");

		obj.setCtyId(ctyId);

		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		obj.setNam(nam);

		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = "";
		obj.setThang(thang);

		String tenCCDC = util.antiSQLInspection(request.getParameter("CCDC"));

		if (tenCCDC == null)
			tenCCDC = "";
		obj.setTenCCDC(tenCCDC);

		String action = util.antiSQLInspection(request.getParameter("action"));
		obj.setAction(action);

		System.out.println("Action = " + action);

		if (action.equals("new")) {
			IKhauhaoCCDC khccdc = new KhauhaoCCDC();
			khccdc.setCtyId(ctyId);
			khccdc.setNppId(nppId);
			khccdc.createRs();
			session.setAttribute("khccdc", khccdc);
			response.sendRedirect("../TraphacoHYERP/pages/Erp/ErpKhauhaocongcudungcuNew.jsp");
		} else if (action.equals("save") || action.equals("submit")) {

			IKhauhaoCCDC khccdc = new KhauhaoCCDC();

			khccdc.setCtyId(ctyId);

			khccdc.setNppId(nppId);

			khccdc.setNam(nam);

			khccdc.setThang(thang);

			khccdc.setUserId(userId);

			String nccdcId = util.antiSQLInspection(request
					.getParameter("nccdcId"));
			if (nccdcId == null)
				nccdcId = "";
			khccdc.setNccdcId(nccdcId);

			String dienGiaiCT = util.antiSQLInspection(request.getParameter("dienGiaiCT"));
			if(dienGiaiCT == null)
				dienGiaiCT = "";
			khccdc.setDienGiaiCT(dienGiaiCT);


			String soChungTu = util.antiSQLInspection(request.getParameter("soChungTu"));
			if(soChungTu == null)
				soChungTu = "";
			khccdc.setSoChungTu(soChungTu);

			String flag = util.antiSQLInspection(request.getParameter("flag"));
			if(flag == null || flag=="")
				flag = "0";
			khccdc.setFlag(flag);


			if(khccdc.getFlag().equals("0")){
				khccdc.CheckKhauHao(nam,thang);
			}

			khccdc.createRs();

			if (action.equals("save")) {

				if(khccdc.getFlag().equals("0") && khccdc.isKhauhao() )
				{
					khccdc.setFlag("1");
					khccdc.setMsg("Tháng này đã có khấu hao, bạn có muốn khấu hao lại không?Nếu có, bấm lưu lại để chạy lại khấu hao!");
					session.setAttribute("khccdc", khccdc);
					response.sendRedirect("../TraphacoHYERP/pages/Erp/ErpKhauhaocongcudungcuNew.jsp");	
				}
				else
				{
					if (!khccdc.save(request)) {
						session.setAttribute("khccdc", khccdc);
						response.sendRedirect("../TraphacoHYERP/pages/Erp/ErpKhauhaocongcudungcuNew.jsp");
					} else {
						obj.createRs();

						session.setAttribute("obj", obj);
						khccdc.DBClose();
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhauhaocongcudungcu.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else {
				session.setAttribute("khccdc", khccdc);
				response.sendRedirect("../TraphacoHYERP/pages/Erp/ErpKhauhaocongcudungcuNew.jsp");
			}
		} else {
			obj.createRs();

			session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhauhaocongcudungcu.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private String Delete(String Id, String userId,String ctyId)
	{
		dbutils db = new dbutils();
		String msg = "";
		String thang="";
		String nam="";
		
		System.out.println("Cong ty nay ne:"+ctyId);
		try{
		
		db.getConnection().setAutoCommit(false);
		String query="SELECT THANG,NAM FROM CHUNGTUKHAUHAOCCDC WHERE PK_sEQ="+Id+" ";
		System.out.println("Cau lay ra thang nam"+query);
		ResultSet rs= db.get(query);

		if(rs!=null)
			try 
		{
				while (rs.next())
				{
					thang=rs.getString("THANG");
					nam=rs.getString("NAM");

				}rs.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		query="select top(1) thang,nam from CHUNGTUKHAUHAOCCDC where  isnull(isDaXoa,0)!=1 order by nam desc, thang desc";
		System.out.println("haha: "+query);
		String thanggannhat="",namgannhat="";
		rs= db.get(query);
		if(rs!=null)
			try 
		{
				while (rs.next())
				{
					thanggannhat=rs.getString("THANG");
					namgannhat=rs.getString("NAM");

				}rs.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		if(!(thanggannhat.equals(thang) && namgannhat.equals(nam)))
		{
			System.out.println("1234: ");
			msg = "Vui lòng xóa Phẩn bổ tháng "+thanggannhat + " năm "+nam;
			db.getConnection().rollback();
			return msg;
		}
		

		String sql = " select count(pk_seq) as sodong " + 
				" from ERP_KHOASOTHANG where congty_fk = '" + ctyId + "' and  nam = '" + nam + "' and thangks = '" + thang + "'";
		System.out.println("___Cau lenh check ERP_KHOASOTHANG : " + sql);

		ResultSet rsCheck  = db.get(sql);
		int count = 0;
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					count = rsCheck.getInt("sodong");
				}
				rsCheck.close();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		if(count > 0)
		{

			msg = "Tháng " + thang + ", năm " + nam + " đã khóa sổ tháng, bạn không thể xóa khấu hao";
			db.getConnection().rollback();
			return msg;
		}



		sql = " select count(pk_seq) as sodong " + 
				" from ERP_KHOASOKETOAN where congty_fk = '" + ctyId + "' and  nam = '" + nam + "' and thangks = '" + thang + "'";
		System.out.println("___Cau lenh check ERP_KHOASOKETOAN: " + sql);

		ResultSet rsCheckKhoaSoKt  = db.get(sql);
		int dem = 0;
		if(rsCheckKhoaSoKt != null)
		{
			try 
			{
				if(rsCheckKhoaSoKt.next())
				{
					dem = rsCheckKhoaSoKt.getInt("sodong");
				}
				rsCheckKhoaSoKt.close();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		if(dem > 0)
		{
			msg = "Tháng " + thang + ", năm " + nam + " đã khóa sổ kế toán, bạn không thể xóa khấu hao";
			db.getConnection().rollback();
			return msg;
		}


		query = "UPDATE  CHUNGTUKHAUHAOCCDC set isDaXoa = '1' , NGUOIXOA = "+ userId +" , NGAYXOA = getdate() where PK_SEQ = '" + Id + "'  ";
		if(!db.update(query))
		{
			msg = "Không thể cập nhật CHUNGTUKHAUHAOCCDC: " + query;
			db.getConnection().rollback();
			return msg;

		}

		System.out.println(" UPDATE   CHUNGTUKHAUHAOCCDC"+query);


		query = " DELETE ERP_KHAUHAOCCDC  WHERE CTKHAUHAO_FK IN (SELECT PK_SEQ FROM CHUNGTUKHAUHAOCCDC WHERE PK_SEQ = "+Id+" ) ";

		if(!db.update(query))
		{
			msg = "Không thể xóa  ERP_KHAUHAOCCDC : " + query;
			db.getConnection().rollback();
			return msg;

		}

		System.out.println(" DELETE  ERP_KHAUHAOCCDC"+query);

		query = " DELETE ERP_PHATSINHKETOAN  WHERE SOCHUNGTU IN (SELECT PK_SEQ FROM CHUNGTUKHAUHAOCCDC WHERE PK_SEQ = "+Id+" ) AND MONTH(NGAYCHUNGTU)='"+thang+"' AND YEAR(NGAYCHUNGTU)='"+nam+"' AND LOAICHUNGTU=N'Phân bổ chi phí trả trước'";

		if(!db.update(query))
		{
			msg = "Không thể xóa  ERP_PHATSINHKETOAN : " + query;
			db.getConnection().rollback();
			return msg;

		}
		System.out.println(" DELETE  ERP_PHATSINHKETOAN"+query);

		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		
		}
		catch(Exception e)
		{
			db.update("rollback");
			msg = "Không thể thực hiện khấu hao. " + e.getMessage();
			return msg;
		}
		db.shutDown();
		return msg;
	}
}