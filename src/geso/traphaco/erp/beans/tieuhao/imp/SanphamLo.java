package geso.traphaco.erp.beans.tieuhao.imp;
import geso.traphaco.erp.beans.tieuhao.ISanphamLo;

public class SanphamLo implements ISanphamLo
{
	String stt;
	String id;
	String ma;
	String ten;
	String tieuhaoId;
	double soluongchuan;
    double soluongton;
	String solo="";
	String khuvuc;
	String KHuvucId;
	String IDMARQUETTE;
	String MARQUETTE;
	String Maphieu="";
	String PhieuEO="";
	String MaPhieuDinhTinh="";
	String Vitri;
	String VitriId;
	String Mathung;
	String Mame;
	String Ngayhethan;
	String Ngaynhapkho;
	
	String Hamam;
	String Hamluong;
	
	public SanphamLo()
	{
		this.stt = "";
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.tieuhaoId = "";
		this.soluongchuan = 0;
		this.soluongton=0;
	 
		this.solo="";
		this.khuvuc="";
		this.KHuvucId="";
		this.IDMARQUETTE="";
		this.MARQUETTE="";
		this.Ngayhethan="";
		this.Ngaynhapkho="";
		this.Hamam="";
		this.Hamluong="";
		
	}
	
	public String getStt()
	{
		return this.stt;
	}
	
	public void setStt(String stt)
	{
		this.stt = stt;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getMa()
	{
		return this.ma;
	}
	
	public void setMa(String ma)
	{
		this.ma = ma;
	}
	
	public String getTen()
	{
		return this.ten;
	}
	
	public void setTen(String ten)
	{
		this.ten = ten;
	}
	
	public String getTieuHaoId()
	{
		return this.tieuhaoId;
	}
	
	public void setTieuHaoId(String tieuhaoId)
	{
		this.tieuhaoId = tieuhaoId;
	}
	
	public double getSoLuong()
	{
		return this.soluongchuan;
	}
	
	public void setSoLuong (double soluongchuan)
	{
		this.soluongchuan = soluongchuan;
	}
	
	 

	@Override
	public String getsolo() {
		// TODO Auto-generated method stub
		return this.solo;
	}

	@Override
	public void setsolo(String solo) {
		// TODO Auto-generated method stub
		this.solo=solo;
	}

	@Override
	public double getSoLuongton() {
		// TODO Auto-generated method stub
		return this.soluongton;
	}

	@Override
	public void setSoLuongton(double soluongton) {
		// TODO Auto-generated method stub
		this.soluongton=soluongton;
	}

	@Override
	public String getIDMARQUETTE() {
		// TODO Auto-generated method stub
		return this.IDMARQUETTE;
	}

	@Override
	public void setIDMARQUETTE(String IDMARQUETTE) {
		// TODO Auto-generated method stub
		this.IDMARQUETTE=IDMARQUETTE;
	}

	@Override
	public String getKHUVUCKHO_FK() {
		// TODO Auto-generated method stub
		return this.KHuvucId;
	}

	@Override
	public void setKHUVUCKHO_FK(String KHUVUCKHO_FK) {
		// TODO Auto-generated method stub
		this.KHuvucId=KHUVUCKHO_FK;
	}

	@Override
	public String getMARQ() {
		// TODO Auto-generated method stub
		return this.MARQUETTE;
	}

	@Override
	public void setMARQ(String MARQ) {
		// TODO Auto-generated method stub
		this.MARQUETTE=MARQ;
	}

	@Override
	public String getKHUVUC() {
		// TODO Auto-generated method stub
		return this.khuvuc;
	}

	@Override
	public void setKHUVUC(String KHUVUC) {
		// TODO Auto-generated method stub
		this.khuvuc=KHUVUC;
	}

	@Override
	public void setMaphieu(String maphieu) {
		// TODO Auto-generated method stub
		this.Maphieu=maphieu;
	}

	@Override
	public String getMaphieu() {
		// TODO Auto-generated method stub
		return this.Maphieu ;
	}

	@Override
	public void setPHIEUEO(String PHIEUEO) {
		// TODO Auto-generated method stub
		this.PhieuEO = PHIEUEO;
	}

	@Override
	public String getPHIEUEO() {
		// TODO Auto-generated method stub
		return this.PhieuEO;
	}

	@Override
	public void setMAPHIEUDINHTINH(String MAPHIEUDINHTINH) {
		// TODO Auto-generated method stub
		this.MaPhieuDinhTinh=MAPHIEUDINHTINH;
	}

	@Override
	public String getMAPHIEUDINHTINH() {
		// TODO Auto-generated method stub
		return this.MaPhieuDinhTinh;
	}

	@Override
	public void setMathung(String mathung) {
		// TODO Auto-generated method stub
		this.Mathung=mathung;
	}

	@Override
	public String getMathung() {
		// TODO Auto-generated method stub
		return this.Mathung;
	}

	@Override
	public void setMame(String mame) {
		// TODO Auto-generated method stub
		this.Mame=mame;
	}

	@Override
	public String getMame() {
		// TODO Auto-generated method stub
		return this.Mame;
	}

	@Override
	public void setVitriId(String vitriid) {
		// TODO Auto-generated method stub
		this.VitriId=vitriid;
	}

	@Override
	public String getVitriId() {
		// TODO Auto-generated method stub
		return this.VitriId;
	}

	@Override
	public void setVitri(String vitri) {
		// TODO Auto-generated method stub
		this.Vitri=vitri;
	}

	@Override
	public String getVitri() {
		// TODO Auto-generated method stub
		return this.Vitri;
	}

	@Override
	public void setNgayhethan(String Ngayhethan) {
		// TODO Auto-generated method stub
		this.Ngayhethan=Ngayhethan;
	}

	@Override
	public String getNgayhethan() {
		// TODO Auto-generated method stub
		return 	this.Ngayhethan;
	}

	@Override
	public void setngaynhapkho(String string) {
		// TODO Auto-generated method stub
		this.Ngaynhapkho =string;
	}

	@Override
	public String getNgaynhapkho() {
		// TODO Auto-generated method stub
		return Ngaynhapkho;
	}

	@Override
	public void setHamam(String hamam) {
		// TODO Auto-generated method stub
		this.Hamam=hamam;
	}

	@Override
	public String getHamam() {
		// TODO Auto-generated method stub
		return this.Hamam;
	}

	@Override
	public void setHamluong(String Hamluong) {
		// TODO Auto-generated method stub
		this.Hamluong=Hamluong;
	}

	@Override
	public String getHamluong() {
		// TODO Auto-generated method stub
		return this.Hamluong;
	}
}
