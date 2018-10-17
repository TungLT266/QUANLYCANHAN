package geso.traphaco.center.servlets.capnhatnhanvien;

import geso.traphaco.center.db.sql.dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/CapNhatNhanVienAjax")
public class CapNhatNhanVienAjax extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public CapNhatNhanVienAjax()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String action = request.getParameter("action") == null ? "" : request.getParameter("action");
		String KvId = request.getParameter("kvId") == null ? "" : request.getParameter("kvId");
		String vungId = request.getParameter("vungId") == null ? "" : request.getParameter("vungId");
		String kenhId = request.getParameter("kenhId") == null ? "" : request.getParameter("kenhId");
		String nppSelected = request.getParameter("nppSelected") == null ? "" : request.getParameter("nppSelected");
		String chucvu = request.getParameter("chucvu") == null ? "" : request.getParameter("chucvu");
		System.out.println("____action_" + action + "____KvId____" + KvId + "___nppSelected_" + nppSelected + "__ChucVu_" + chucvu+"KenhId"+kenhId);
		String query = "";
		if (action.equals("ajaxNpp"))
		{
			dbutils db = new dbutils();
			query = "SELECT PK_SEQ as nppId ,TEN as nppTen,ma as nppMa,dienthoai as nppDienthoai FROM NHAPHANPHOI WHERE TRANGTHAI=1 ";
			if (KvId.length() > 0)
			{
				query += " and khuvuc_fk in (" + KvId + ")";
			}
			if (vungId.length() > 0)
			{
				query += " and khuvuc_fk in ( select pk_seq from khuvuc where vung_fk in (" + vungId + ")   )";
			}
			if (kenhId.length() > 0)
			{
				query += " and pk_Seq in ( select npp_fk from nhapp_kbh where kbh_fk in (" + kenhId + ")  )";
			}
			if (nppSelected.length() > 0)
			{
				query += " and pk_Seq   not in ( " + nppSelected + "  )";
				query += " union all " + " SELECT PK_SEQ as nppId ,TEN as nppTen,ma as nppMa,dienthoai as nppDienthoai FROM NHAPHANPHOI WHERE TRANGTHAI=1 and pk_seq in (" + nppSelected + ")  ";
			}
			System.out.println("__JSON NPP__" + query);
			ResultSet rs = db.get(query);
			List<INhaPhanPhoi> nppList = new ArrayList<INhaPhanPhoi>();
			if (rs != null)
			{
				try
				{
					INhaPhanPhoi npp = null;
					while (rs.next())
					{
						npp = new NhaPhanPhoi();
						npp.setNppTen(rs.getString("nppTen"));
						npp.setNppId(rs.getString("nppId"));
						npp.setNppMa(rs.getString("nppMa"));
						npp.setNppDienThoai(rs.getString("nppDienthoai") == null ? "" : rs.getString("nppDienthoai"));

						nppList.add(npp);
					}
					rs.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			db.shutDown();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(gson.toJson(nppList));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}

	interface INhaPhanPhoi
	{
		String getNppId();

		void setNppId(String nppId);

		String getNppMa();

		void setNppMa(String nppMa);

		String getNppTen();

		void setNppTen(String npTen);

		String getNppDienThoai();

		void setNppDienThoai(String nppDienThoai);

	}

	class NhaPhanPhoi implements INhaPhanPhoi
	{

		String nppId, nppMa, nppTen, nppDienThoai;

		public String getNppDienThoai()
		{
			return nppDienThoai;
		}

		public void setNppDienThoai(String nppDienThoai)
		{
			this.nppDienThoai = nppDienThoai;
		}

		public String getNppId()
		{
			return nppId;
		}

		public void setNppId(String nppId)
		{
			this.nppId = nppId;
		}

		public String getNppMa()
		{
			return nppMa;
		}

		public void setNppMa(String nppMa)
		{
			this.nppMa = nppMa;
		}

		public String getNppTen()
		{
			return nppTen;
		}

		public void setNppTen(String nppTen)
		{
			this.nppTen = nppTen;
		}

	}

}
