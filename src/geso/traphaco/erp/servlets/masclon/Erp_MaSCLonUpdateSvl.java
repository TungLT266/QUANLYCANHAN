package geso.traphaco.erp.servlets.masclon;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.masclon.imp.Erp_MaSCLon;
import geso.traphaco.erp.beans.masclon.imp.Erp_MaSCLonList;
import geso.traphaco.erp.beans.masclon.imp.Person;
import geso.traphaco.erp.beans.masclon.imp.PersonComparator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_MaSCLonUpdateSvl extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Erp_MaSCLonUpdateSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("do get update");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		Erp_MaSCLon obj = new Erp_MaSCLon();
		String ctyId = (String) session.getAttribute("congtyId");
		obj.setCongTyId(ctyId);

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String action = util.getAction(querystring);
		System.out.println("\n **************action:" + action);

		String id = util.getId(querystring);

		obj.setId(id);

		obj.init();

		session.setAttribute("action", action);
		session.setAttribute("obj", obj);

		session.setAttribute("userId", userId);

		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_MaSCLonUpdate.jsp");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Person person1 = new Person("Marry", 20);
		Person person2 = new Person("Tom", 21);
		Person person3 = new Person("Daniel", 21);
		Person person4 = new Person("Mischa", 18);
		Person person5 = new Person("Christian", 20);
		Person[] array = new Person[] { person1, person2, person3, person4,
				person5 };
		Arrays.sort(array, new PersonComparator());

		for (Person person : array) {
			System.out.println("Person: " + person.getAge() + " / "
					+ person.getFullName());
		}

		System.out.println("------------------------");

		// For List
		List<Person> list = new ArrayList<Person>();
		list.add(person1);
		list.add(person2);
		list.add(person3);
		list.add(person4);
		list.add(person5);

		// Sort the array, use: <T> Arrays.sort(T[],Comparator<? supers T>)
		// Provide a Comparator.
		Collections.sort(list, new PersonComparator());

		for (Person person : list) {
			System.out.println("Person: " + person.getAge() + " / "
					+ person.getFullName());
		}
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		String userId = request.getParameter("userId");
		String id = request.getParameter("id");

		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}

		Erp_MaSCLon obj = new Erp_MaSCLon();
		obj.setId(id);
		obj.setNguoiSua(userId);
		String congTyId = (String) session.getAttribute("congtyId");
		obj.setCongTyId(congTyId);

		obj.setNguoiTao(userId);
		System.out.println("congTyId la: " + congTyId);

		obj.init();

		String taiKhoanId = request.getParameter("taiKhoanId");
		obj.setTaiKhoanId(taiKhoanId);

		String taiSanId = request.getParameter("taiSanId");
		obj.setTaiSanId(taiSanId);
		System.out.println("aasdsasad" + taiSanId);

		String ma = request.getParameter("ma");
		obj.setMa(ma);

		String ten = request.getParameter("ten");
		obj.setTen(ten);

		String ngayChuyen = request.getParameter("ngayketchuyen");
		obj.setNgayChuyen(ngayChuyen);

		String giaTri = request.getParameter("giaTri");
		obj.setGiaTri(Double.parseDouble(giaTri.replace(",", "")));

		String loaiId = request.getParameter("loaiId");
		System.out.println("laoi :" + loaiId);
		obj.setLoaiId(Integer.parseInt(loaiId));

		System.out.println("action: " + action);
		if (action.equals("new")) {
			if (!obj.newMaSCLon()) {
				session.setAttribute("userId", userId);
				session.setAttribute("obj", obj);

				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MaSCLonUpdate.jsp";
				response.sendRedirect(nextJSP);
				return;
			} else {
				obj.DbClose();

				Erp_MaSCLonList ob = new Erp_MaSCLonList();
				ob.setCongTyId(congTyId);
				ob.init();

				session.setAttribute("userId", userId);
				session.setAttribute("obj", ob);

				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MaSCLon.jsp";
				response.sendRedirect(nextJSP);
				return;
			}
		} else if (action.equals("update")) {
			if (!obj.editMaSCLon()) {
				session.setAttribute("userId", userId);
				session.setAttribute("obj", obj);

				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MaSCLonUpdate.jsp";
				response.sendRedirect(nextJSP);
				return;
			} else {
				obj.DbClose();

				Erp_MaSCLonList ob = new Erp_MaSCLonList();
				ob.setCongTyId(congTyId);
				ob.init();

				session.setAttribute("userId", userId);
				session.setAttribute("obj", ob);

				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MaSCLon.jsp";
				response.sendRedirect(nextJSP);
				return;
			}
		} else if (action.equals("update_ngaykc")) {
			if (!obj.editNgayKetChuyen()) {
				session.setAttribute("userId", userId);
				session.setAttribute("obj", obj);

				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MaSCLonUpdate.jsp";
				response.sendRedirect(nextJSP);
				return;
			} else {
				obj.DbClose();

				Erp_MaSCLonList ob = new Erp_MaSCLonList();
				ob.setCongTyId(congTyId);
				ob.init();

				session.setAttribute("userId", userId);
				session.setAttribute("obj", ob);

				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MaSCLon.jsp";
				response.sendRedirect(nextJSP);
				return;
			}
		}

		else {
			obj.initTaiSanList();
		}
		session.setAttribute("userId", userId);
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_MaSCLonUpdate.jsp";
		response.sendRedirect(nextJSP);
		return;
	}
}