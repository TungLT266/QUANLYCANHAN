package geso.traphaco.center.servlets.capnhatnhanvien;

import geso.traphaco.center.beans.capnhatnhanvien.ICapnhatnhanvien;
import geso.traphaco.center.beans.capnhatnhanvien.ICapnhatnhanvienList;
import geso.traphaco.center.beans.capnhatnhanvien.imp.Capnhatnhanvien;
import geso.traphaco.center.beans.capnhatnhanvien.imp.CapnhatnhanvienList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CapnhatnhanviennewSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public CapnhatnhanviennewSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		String id = util.getId(querystring);

		String action = util.getAction(querystring);
		ICapnhatnhanvien obj = new Capnhatnhanvien(id);
		obj.setuserId(userId);

		obj.CreateQuyen(null);
		obj.CreateKenh(null);
		obj.CreateNpp(null);
		obj.CreateSanpham(null);
		obj.CreateKho(null);
		obj.init();
		obj.CreateRS();

		if (action.equals("delete"))
		{
			ICapnhatnhanvienList obj1 = new CapnhatnhanvienList();
			obj1.xoa(id);
			obj1.init("");
			session.setAttribute("obj", obj1);
			response.sendRedirect("/TraphacoHYERP/pages/Center/CapNhatNhanVien.jsp");
		} 
		else
		{
			session.setAttribute("obj", obj);
			response.sendRedirect("/TraphacoHYERP/pages/Center/CapNhatNhanVienUpdate.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		ICapnhatnhanvien obj = new Capnhatnhanvien();
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		if (userId == null)
			userId = "";
		obj.setuserId(userId);

		String Id = util.antiSQLInspection(request.getParameter("Id"));
		if (Id == null)
			Id = "";
		obj.setId(Id);

		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);

		String Ten = util.antiSQLInspection(request.getParameter("ten"));
		if (Ten == null)
			Ten = "";
		obj.setTen(Ten);

		String Ngaysinh = util.antiSQLInspection(request.getParameter("ngaysinh"));
		if (Ngaysinh == null)
			Ngaysinh = "";
		obj.setngaysinh(Ngaysinh);

		String Dienthoai = util.antiSQLInspection(request.getParameter("dienthoai"));
		if (Dienthoai == null)
			Dienthoai = "";
		obj.setdienthoai(Dienthoai);

		String Trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (Trangthai == null)
			Trangthai = "0";
		obj.settrangthai(Trangthai);

		String Phanloai = util.antiSQLInspection(request.getParameter("phanloai"));
		if (Phanloai == null)
			Phanloai = "";
		obj.setphanloai(Phanloai);
		
		String xuathoadon = util.antiSQLInspection(request.getParameter("xuathoadon"));
		if (xuathoadon == null)
			xuathoadon = "0";
		obj.setXuathoadon(xuathoadon);

		String Email = util.antiSQLInspection(request.getParameter("email"));
		if (Email == null)
			Email = "";
		obj.setemail(Email);

		String Tendangnhap = util.antiSQLInspection(request.getParameter("tendangnhap"));
		if (Tendangnhap == null)
			Tendangnhap = "";
		obj.settendangnhap(Tendangnhap);

		String matkhau = util.antiSQLInspection(request.getParameter("matkhau"));
		if (matkhau == null)
			matkhau = "";
		obj.setmatkhau(matkhau);

		String vungId = util.antiSQLInspection(request.getParameter("vungId"));
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);

		String khuvucId = util.antiSQLInspection(request.getParameter("khuvucId"));
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);

		String nhanhangId = util.antiSQLInspection(request.getParameter("nhanhangId"));
		if (nhanhangId == null)
			nhanhangId = "";
		obj.setnhanhangId(nhanhangId);
		
		String sohoadontu = util.antiSQLInspection(request.getParameter("sohoadontu"));
		if (sohoadontu == null)
			sohoadontu = "";
		obj.setSohoadontu(sohoadontu);
		
		String sohoadonden = util.antiSQLInspection(request.getParameter("sohoadonden"));
		if (sohoadonden == null)
			sohoadonden = "";
		obj.setSohoadonden(sohoadonden);

		String chungloaiId = util.antiSQLInspection(request.getParameter("chungloaiId"));
		if (chungloaiId == null)
			chungloaiId = "";
		obj.setchungloaiId(chungloaiId);

		String loai = util.antiSQLInspection(request.getParameter("loai"));
		if (loai == null) loai = "";
		obj.setLoai(loai);

		String loaiId = util.antiSQLInspection(request.getParameter("loaiId"));
		if (loaiId == null) loaiId = "";
		obj.setLoaiId(loaiId);
		
		String dmquyenId = util.antiSQLInspection(request.getParameter("dmquyenId"));
		if (dmquyenId == null) dmquyenId = "";
		obj.setdmquyenId(dmquyenId);
		
		String phongbanId = util.antiSQLInspection(request.getParameter("phongbanId"));
		if( phongbanId == null )
			phongbanId = "";
		obj.setphongbanId(phongbanId);		

		String quyen[] = request.getParameterValues("quyen");
		obj.CreateQuyen(quyen);

		String chon = util.antiSQLInspection(request.getParameter("chon"));
		if (chon == null)
			chon = "1";
		obj.setchon(chon);
		
		String activeTab = util.antiSQLInspection(request.getParameter("activeTab"));
		if (activeTab == null)
			activeTab = "";
		obj.setActiveTab(activeTab);
		
		String kenh[] = request.getParameterValues("kenh");
		obj.CreateKenh(kenh);

		String npp[] = request.getParameterValues("npp");
		obj.CreateNpp(npp);
		String nppIds="";
		if(npp!=null)
		{
			for(int i=0;i<npp.length;i++)
			{
				nppIds+=npp[i]+",";	
			}
			if(nppIds.length()>0)
			{
				nppIds=nppIds.substring(0,nppIds.length()-1);
			}
		}
		obj.setNppIds(nppIds);
		
		
		String ttIds="";
		
		String[] ttId = request.getParameterValues("ttId");
		if (ttId != null)
		{
			int size = ttId.length;
			for (int i = 0; i < size; i++)
			{
				ttIds += ttId[i] + ",";
			}
			if (ttIds.length() > 0)
			{
				ttIds = ttIds.substring(0, ttIds.length() - 1);
			}
		}
	
		obj.setTtId(ttIds);
		
		String qhIds="";
		String[] qhId = request.getParameterValues("qhId");
		if (qhId != null)
		{
			int size = qhId.length;
			for (int i = 0; i < size; i++)
			{
				qhIds += qhId[i] + ",";
			}
			if (qhIds.length() > 0)
			{
				qhIds = qhIds.substring(0, qhIds.length() - 1);
			}
		}
		obj.setTtId(ttIds);
		

		String sanpham[] = request.getParameterValues("sanpham");
		obj.CreateSanpham(sanpham);
		
		String kho[] = request.getParameterValues("kho");
		obj.CreateKho(kho);
		
		String diabanIds[] = request.getParameterValues("diabanIds");
		if( diabanIds != null )
		{
			String _diaban = "";
			for(int i = 0; i < diabanIds.length; i++ )
			{
				_diaban += diabanIds[i];
				if( i < diabanIds.length - 1 )
					_diaban += ",";	
			}
			obj.setDiabanId(_diaban);
		}
		
		String tinhthanhIds[] = request.getParameterValues("tinhthanhIds");
		if( tinhthanhIds != null )
		{
			String _tinhthanh = "";
			for(int i = 0; i < tinhthanhIds.length; i++ )
			{
				_tinhthanh += tinhthanhIds[i];
				if( i < tinhthanhIds.length - 1 )
					_tinhthanh += ",";	
			}
			obj.setTinhthanhId(_tinhthanh);
		}
		
		
		String action = request.getParameter("action");
		if (action.equals("save"))
		{
			if (!obj.save( quyen ))
			{
				obj.CreateRS();
				obj.CreateKenh(kenh);
				obj.CreateQuyen(quyen);
				obj.CreateSanpham(sanpham);
				obj.CreateKho(kho);
				obj.CreateNpp(npp);
				session.setAttribute("obj", obj);
				response.sendRedirect("/TraphacoHYERP/pages/Center/CapNhatNhanVienUpdate.jsp");
			} 
			else
			{
				ICapnhatnhanvienList obj1 = new CapnhatnhanvienList();
				obj1.setuserId(userId);
				obj1.init("");
				session.setAttribute("obj", obj1);
				response.sendRedirect("/TraphacoHYERP/pages/Center/CapNhatNhanVien.jsp");
			}
		} 
		else
		{
			try
			{
				obj.CreateRS();
				//obj.init();
				obj.CreateKenh(kenh);
				obj.CreateQuyen(quyen);
				obj.CreateSanpham(sanpham);
				obj.CreateKho(kho);
				obj.CreateNpp(npp);
				session.setAttribute("obj", obj);
				response.sendRedirect("/TraphacoHYERP/pages/Center/CapNhatNhanVienUpdate.jsp");
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.out.println("loi !!!!!!!!!!!!!!!!");
			}
		}

	}

}
