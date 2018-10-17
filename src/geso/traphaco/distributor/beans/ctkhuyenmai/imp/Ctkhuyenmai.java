package geso.traphaco.distributor.beans.ctkhuyenmai.imp;

import java.util.List;

import geso.traphaco.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.traphaco.distributor.beans.dieukienkhuyenmai.IDieukienkhuyenmai;
import geso.traphaco.distributor.beans.trakhuyenmai.ITrakhuyenmai;
import geso.traphaco.distributor.beans.trakhuyenmai.imp.Trakhuyenmai;

public class Ctkhuyenmai implements ICtkhuyenmai 
{
	private String id;
	private String scheme;
	private String tungay;
	private String denngay;
	private String diengiai;
	private String khoId;
	
	private int loaict;
	private float ngansach;
	private float dasudung;
	private float soxuatKM; //luu so xuat khuyen mai duoc huong theo chuong trinh nay
	private int soxuatKM_TOIDA;
	
	private long tongtientheodkkm;
	
	private boolean confirm; //check ctkm co thoa ngan sach, dong thoi ko dung sp su dung o cac dkkm khac
	private boolean tra_OR;
	
	private String hinhthucPrimary;
	
	private List<IDieukienkhuyenmai> dkkhuyenmai; //dkkhuyenmai cua chuong trinh
	private List<ITrakhuyenmai> trakhuyenmai; //trakhuyenmai theo chuong trinh
	
	private float chietkhau;
	String phanbotheoDH;
	String schemeDungchung;
	String ctkmLoaitruId;
	String giohang;
	float tientoithieu;
	
	public Ctkhuyenmai()
	{
		this.id = "";
		this.scheme = "";
		this.tungay = "";
		this.denngay = "";
		this.khoId = "";
		this.loaict = 1; //binh thuong
		this.ngansach = 0;
		this.dasudung = 0;
		this.soxuatKM = 0;
		this.soxuatKM_TOIDA = 0;
		this.diengiai = "";
		this.tongtientheodkkm = 0;
		this.hinhthucPrimary = "0";
		this.tra_OR = false;
		this.confirm = false;
		phanbotheoDH="0";
		this.chietkhau = 0;
		this.schemeDungchung = "";
		this.ctkmLoaitruId = "";
		this.giohang = "0";
		this.tientoithieu = 0;
	}
	
	public Ctkhuyenmai(String id, String scheme, String diengiai, String tungay, String denngay, int loaict, float ngansach, float dasudung, int soxuatKM)
	{
		this.id = id;
		this.scheme = scheme;
		this.tungay = tungay;
		this.denngay = denngay;
		this.loaict = loaict;
		this.ngansach = ngansach;
		this.dasudung = dasudung;
		this.soxuatKM = soxuatKM;
		this.diengiai = diengiai;
		this.soxuatKM_TOIDA = 0;
		this.khoId = "";
		this.hinhthucPrimary = "0";
		this.phanbotheoDH="0";
		this.tra_OR = false;
		this.confirm = false;
		this.schemeDungchung = "";
		this.ctkmLoaitruId = "";
		this.giohang = "0";
		this.tientoithieu = 0;
	}
	
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getscheme()
	{
		return this.scheme;
	}
	public void setscheme(String scheme)
	{
		this.scheme = scheme;
	}
	public String getTungay()
	{
		return this.tungay;
	}
	public void setTungay(String tungay)
	{
		this.tungay = tungay;
	}
	public String getDenngay()
	{
		return this.denngay;
	}
	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}
	public int getLoaict()
	{
		return this.loaict;
	}
	public void setLoaict(int loaict)
	{
		this.loaict = loaict;
	}
	public float getNgansach()
	{
		return this.ngansach;
	}
	public void setNgansach(float ngansach)
	{
		this.ngansach = ngansach;
	}
	public float getDasudung()
	{
		return this.dasudung;
	}
	public void setDasudung(float dasudung)
	{
		this.dasudung = dasudung;
	}
	public List<IDieukienkhuyenmai> getDkkhuyenmai()
	{
		return this.dkkhuyenmai;
	}
	public void setDkkhuyenmai(List<IDieukienkhuyenmai> dkkm)
	{
		this.dkkhuyenmai = dkkm;
	}
	public List<ITrakhuyenmai> getTrakhuyenmai()
	{
		return this.trakhuyenmai;
	}
	public void setTrakhuyenmai(List<ITrakhuyenmai> trakm)
	{
		this.trakhuyenmai = trakm;
	}

	public float getSoxuatKM() 
	{
		return this.soxuatKM;
	}

	public void setSoxuatKM(float soxuatKM) 
	{
		this.soxuatKM = soxuatKM;
	}

	public boolean getConfirm() 
	{
		return this.confirm;
	}

	public void setConfirm(boolean confirm)
	{
		this.confirm = confirm;
	}

	//kiem tra ngansach thoa duoc bao nhieu xuat
	public float checkCtkm(float tonggiatri) 
	{
		List<ITrakhuyenmai> trakm = this.getTrakhuyenmai();
		float sx = this.soxuatKM;
		
		/*if(this.phanbotheoDH.equals("1"))
		{
			int conlai = (int)( this.ngansach - this.dasudung );
			return Math.min(conlai, sx);
		}*/
		
		//System.out.println("Tra khuyen mai size la: " + trakm.size() + "\n");
		//check ngansach
		/*if(this.phanbotheoDH.equals("0"))
		{
			for(int i = 0; i < trakm.size(); i++)
			{
				Trakhuyenmai tkm = (Trakhuyenmai)trakm.get(i);
				
				if(tkm.getType() == 1) //tra tien
				{ 
					float sum = this.soxuatKM * tkm.getTongtien();
					if(sum > (this.ngansach - this.dasudung))
						sx = (int) ((this.soxuatKM * (this.ngansach - this.dasudung)) / sum);
				}
				if(tkm.getType() == 2) //tra chiet khau
				{
					float sum = tkm.getChietkhau() * tonggiatri / 100;
					if(sum > (this.ngansach - this.dasudung))
						sx = -1;
				}
				
				if(tkm.getType() == 3) //san pham
				{
					if(tkm.getHinhthuc() == 1) //chua chon san pham thi khong can biet tong ngan sach
					{
						float tongGtriKm = tkm.getTongGtriKm();
						float sum = this.soxuatKM * tongGtriKm;
						
						if(sum > (this.ngansach - this.dasudung))
						{
							sx = (int) ((this.soxuatKM * (this.ngansach - this.dasudung)) / sum);	
						}
						//System.out.println("So xuat khuyen mai la: " + sx + "\n");
					}
				}
			}
		}*/
		
		return sx;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public long getTongTienTheoDKKM() 
	{
		return this.tongtientheodkkm;
	}

	public void setTongTienTheoDKKM(long tongtien)
	{
		this.tongtientheodkkm = tongtien;
	}
	
	public String getKhoId() 
	{
		return this.khoId;
	}

	public void setKhoId(String khoId) 
	{
		this.khoId = khoId;
	}

	public String getHinhthucPrimary() 
	{
		return this.hinhthucPrimary;
	}

	public void setHinhthucPrimary(String hinhthucPrimary) 
	{
		this.hinhthucPrimary = hinhthucPrimary;
	}

	public void setTra_OR(boolean tra_OR)
	{
		this.tra_OR = tra_OR;
	}

	public boolean getTra_OR() 
	{
		return this.tra_OR;
	}

	public float getCK() {

		return this.chietkhau;
	}


	public void setCK(float ck) {
	
		this.chietkhau = ck;
	}
	public String getPhanbotheoDH() {

		return this.phanbotheoDH;
	}


	public void setPhanbotheoDH(String phanbotheoDH) {
		
		this.phanbotheoDH = phanbotheoDH;
	}

	
	public int getSoxuatKM_TOIDA() {

		return this.soxuatKM_TOIDA;
	}


	public void setSoxuatKM_TOIDA(int soxuatKM_TOIDA) {
		
		this.soxuatKM_TOIDA = soxuatKM_TOIDA;
	}

	
	public String getSchemeDungChung() {

		return this.schemeDungchung;
	}


	public void setSchemeDungChung(String schemeDungchung) {
		
		this.schemeDungchung = schemeDungchung;
	}

	
	public String getCtkmTLLoaitruId() 
	{
		return this.ctkmLoaitruId;
	}

	public void setCtkmTLLoaitruId(String ctkmLoaitruId)
	{
		this.ctkmLoaitruId = ctkmLoaitruId;
	}

	
	public String getGiohang() 
	{
		return this.giohang;
	}

	public void setGiohang(String giohang) 
	{
		this.giohang = giohang;
	}

	
	public float getTientoithieu() {
		
		return this.tientoithieu;
	}

	
	public void setTientoithieu(float tientoithieu) {
		
		this.tientoithieu = tientoithieu;
	}


	public long getTongGiaTriTraKM() 
	{
		long tongtienTraKM = 0;
		
		for(int count = 0; count < this.getTrakhuyenmai().size(); count++)
		{
			Trakhuyenmai trakmTien = (Trakhuyenmai)this.getTrakhuyenmai().get(count);
			//System.out.print("\nTra Khuyen Mai Value: " + Float.toString(trakmTien.getTongGtriKm()) + "\n" );
			
			if(trakmTien.getType() == 1)
				tongtienTraKM += Math.round(this.getSoxuatKM() * trakmTien.getTongtien());
			else
			{
				if(trakmTien.getType() == 2) //trachietkhau
				{
					System.out.println("::: TRA KM:  " + trakmTien.getId() + " -- SO TIEN THEO DK: " + this.getTongTienTheoDKKM() + " -- CK: " + trakmTien.getChietkhau());
					tongtienTraKM += Math.round(this.getTongTienTheoDKKM() * (trakmTien.getChietkhau() / 100));
				}
				else
				{
					if(this.getKhoId().equals("100001") && trakmTien.getHinhthucPrimary().equals("1")) //primary tra tien
					{
						tongtienTraKM += Math.round(this.getSoxuatKM() * trakmTien.getTongGtriKm());
					}
					else
					{
						if(trakmTien.getHinhthuc() == 1 )  //tra sp co dinh thi tinh dc tong gia tri, khong thi phai doi nguoi dung chon SP
							tongtienTraKM += Math.round(this.getSoxuatKM() * trakmTien.getTongGtriKm());
					}
				}
			}
		}
		
		return tongtienTraKM;
	}


	public long getTongGiaTriTraKM_TheoTRAKM(String tkmId)
	{
		long tongtienTraKM = 0;
		
		for(int count = 0; count < this.getTrakhuyenmai().size(); count++)
		{
			Trakhuyenmai trakmTien = (Trakhuyenmai)this.getTrakhuyenmai().get(count);
			//System.out.print("\nTra Khuyen Mai Value: " + Float.toString(trakmTien.getTongGtriKm()) + "\n" );
			
			if( trakmTien.getId().equals(tkmId) )
			{
				if(trakmTien.getType() == 1)
					tongtienTraKM += Math.round(this.getSoxuatKM() * trakmTien.getTongtien());
				else
				{
					if(trakmTien.getType() == 2) //trachietkhau
					{
						System.out.println("::: TRA KM:  " + trakmTien.getId() + " -- SO TIEN THEO DK: " + this.getTongTienTheoDKKM() + " -- CK: " + trakmTien.getChietkhau());
						tongtienTraKM += Math.round(this.getTongTienTheoDKKM() * (trakmTien.getChietkhau() / 100));
					}
					else
					{
						if(this.getKhoId().equals("100001") && trakmTien.getHinhthucPrimary().equals("1")) //primary tra tien
						{
							tongtienTraKM += Math.round(this.getSoxuatKM() * trakmTien.getTongGtriKm());
						}
						else
						{
							if(trakmTien.getHinhthuc() == 1 )  //tra sp co dinh thi tinh dc tong gia tri, khong thi phai doi nguoi dung chon SP
								tongtienTraKM += Math.round(this.getSoxuatKM() * trakmTien.getTongGtriKm());
						}
					}
				}
			}
		}
		
		return tongtienTraKM;
	}
	
	
	
}
