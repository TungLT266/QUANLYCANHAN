package geso.traphaco.erp.servlets.khott;

import geso.traphaco.erp.beans.kho.imp.Erp_KhoTT;
import geso.traphaco.erp.beans.kho.imp.Erp_KhoTTList;
import geso.traphaco.center.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKhoTTUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public ErpKhoTTUpdateSvl() {
        super();
    }
    
    String URL;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Utility util = new Utility();
		HttpSession session=request.getSession();
		
		String action = request.getParameter("action");
		String khoid = util.antiSQLInspection(request.getParameter("khoId"));
		String ctyId = (String)session.getAttribute("congtyId");
		
		if(action.equals("update"))
		{
			Erp_KhoTT khott;

			if(khoid!=null)
				khott=new Erp_KhoTT(khoid);
			else
			{
				khott=new Erp_KhoTT();
			}
			
			khott.setCongty_fk(ctyId);
			khott.setPK_SEQ(khoid);
			khott.init();
			
			session.setAttribute("ktt", khott);
			URL = "../TraphacoHYERP/pages/Erp/Erp_KhoTTUpdate.jsp";
		}
		
		if(action.equals("review"))
		{
			Erp_KhoTTList khottList=new Erp_KhoTTList();
			khottList.setCongty_fk(ctyId);
			khottList.init();
			khottList.CreateRS();
			if(khottList.getMa()== null){khottList.setMa("");}
			if(khottList.getTen()== null){khottList.setTen("");}
			if(khottList.getTrangthai()== null){khottList.setTrangthai("");}
			
			session.setAttribute("kttL", khottList);
			URL = "../TraphacoHYERP/pages/Erp/Erp_KhoTT.jsp";
		}
		
		if(action.equals("display"))
		{
			Erp_KhoTT khott;

			if(khoid!=null)
				khott=new Erp_KhoTT(khoid);
			else
			{
				khott=new Erp_KhoTT();
			}
			
			khott.setCongty_fk(ctyId);
			khott.setPK_SEQ(khoid);
			khott.init();
			
			session.setAttribute("ktt", khott);
			URL = "../TraphacoHYERP/pages/Erp/Erp_KhoTTDisplay.jsp";
		}
		response.sendRedirect(URL);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session=request.getSession();
		String action=request.getParameter("action");
		
		String id = request.getParameter("id");
		String user = request.getParameter("userId");
		String ctyId = (String)session.getAttribute("congtyId");
		Utility util = new Utility();
		
		Erp_KhoTT ktt;
		if(id == null) 
			ktt = new Erp_KhoTT();
		else
			ktt = new Erp_KhoTT(id);
		
		ktt.setCongty_fk(ctyId);
		
		String MA = util.antiSQLInspection(request.getParameter("txtMakho"));
		if(MA == null)
			MA = "";
		ktt.setMa(MA);
		
		String TEN = util.antiSQLInspection(request.getParameter("txtTenkho"));
		if(TEN == null)
			TEN = "";
		ktt.setTen(TEN);
		
		String DIACHI = util.antiSQLInspection(request.getParameter("txtDiachi"));
		if(DIACHI == null)
			DIACHI = "";
		ktt.setDiachi(DIACHI);
		
		String LOAI = util.antiSQLInspection(request.getParameter("loai"));
		if(LOAI == null)
			LOAI = "";
		ktt.setLoai(LOAI);
		
		String TRANGTHAI = util.antiSQLInspection(request.getParameter("hoatdong"));
		if(TRANGTHAI == null)
			TRANGTHAI = "0";
		ktt.setTrangthai(TRANGTHAI);

		String MOHINH = util.antiSQLInspection(request.getParameter("mohinh"));
		if (MOHINH == null) 
			MOHINH = "0";
		ktt.setQuanlybin(MOHINH);
		
		String nhamayId = util.antiSQLInspection(request.getParameter("nhamayId"));
		if (nhamayId == null) 
			nhamayId = "";
		ktt.setNhamayId(nhamayId);
		
		String[] khottId =  request.getParameterValues("khottId");  
		 
		if (khottId != null) 
		{
			String lsp = "";
			for(int i = 0; i < khottId.length; i++)
			{
				lsp += khottId[i] + ",";
			}
			
			if(lsp.length() > 0)
				lsp = lsp.substring(0, lsp.length() - 1);
			
			 
			ktt.setKhoTTIds(lsp);
		}
		
		String[] lspIds = request.getParameterValues("lspIds");
		if (lspIds != null) 
		{
			String lsp = "";
			for(int i = 0; i < lspIds.length; i++)
			{
				lsp += lspIds[i] + ",";
			}
			
			if(lsp.length() > 0)
				lsp = lsp.substring(0, lsp.length() - 1);
			
			ktt.setLoaispIds(lsp);
		}
		
		String[] spIds = request.getParameterValues("spIds");
		if (spIds != null) 
		{
			String sp = "";
			for(int i = 0; i < spIds.length; i++)
			{
				sp += spIds[i] + ",";
			}
			
			if(sp.length() > 0)
				sp = sp.substring(0, sp.length() - 1);
			
			 
			ktt.setSpIds(sp);
		}
		
		ktt.setNguoitao(user);
		ktt.setNguoisua(user);
		
		if(action.equals("save"))
		{
			if(id == null)
			{
				if(ktt.Create())
				{
					Erp_KhoTTList kttl=new Erp_KhoTTList();
					kttl.setCongty_fk(ctyId);
					kttl.init();
					kttl.CreateRS();
					
					if(kttl.getMa() == null)
						kttl.setMa("");
					if(kttl.getTen() == null)
						kttl.setTen("");
					if(kttl.getCongty_fk() == null)
						kttl.setCongty_fk("");
					if(kttl.getTrangthai() == null)
						kttl.setTrangthai("");
					ktt.DBClose();
					session.setAttribute("kttL", kttl);
					URL = "../TraphacoHYERP/pages/Erp/Erp_KhoTT.jsp";
				}
				else
				{
					ktt.CreateRs();
					session.setAttribute("ktt", ktt);
					URL = "../TraphacoHYERP/pages/Erp/Erp_KhoTTNew.jsp";
				}
			}
			else
			{
				if(ktt.Update())
				{
					Erp_KhoTTList kttl = new Erp_KhoTTList();
					kttl.setCongty_fk(ctyId);
					kttl.init();
					kttl.CreateRS();
					
					if(kttl.getMa() == null)
						kttl.setMa("");
					if(kttl.getTen() == null)
						kttl.setTen("");
					if(kttl.getCongty_fk() == null)
						kttl.setCongty_fk("");
					if(kttl.getTrangthai() == null)
						kttl.setTrangthai("");
					ktt.DBClose();
					session.setAttribute("kttL", kttl);
					URL = "../TraphacoHYERP/pages/Erp/Erp_KhoTT.jsp";
				}
				else
				{
					ktt.CreateRs();
					session.setAttribute("ktt", ktt);
					URL = "../TraphacoHYERP/pages/Erp/Erp_KhoTTUpdate.jsp";
				}
			}
		}
		else
		{
			ktt.CreateRs();
			
			session.setAttribute("userId", user);
			session.setAttribute("ktt", ktt);
			
			URL = "/TraphacoHYERP/pages/Erp/Erp_KhoTTNew.jsp";
			
			if( id != null )
			{
				URL = "/TraphacoHYERP/pages/Erp/Erp_KhoTTUpdate.jsp";
			}
		}
		
		response.sendRedirect(URL);
	}
}