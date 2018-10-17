package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ISpSanxuatChitiet;
 
public class SpSanxuatChitiet  implements ISpSanxuatChitiet{

	private String IdLsx="";
	private String IdSp="";
	private String IdSpThayThe="";
	private String Masp="";
	private String Tensp="";
	private String Soluong="";
    String Idkho="";
    String Tenkho="";
    String BinId;
    String Bin;
    
    String IdKVK="";
    String TenKVK="";
    String IdMq="";
    String TenMq="";
    
    String Mathung="";
    String Mame="";
    String Solo="";
    String Ngaynhapkho="";
    String Ngayhethan="";
    String Hamluong="";
    String Hamam="";
    String soluongton="";
    String soluongtonthucte="";
    String Maphieu="";
    String MaphieuEo="";
    String Maphieudinhtinh="";
    
    String loaidoituong="";
    String DoituongId="";
    String NSX_FK="";
    String MaNSX="";
	@Override
	public void setLSXId(String LsxId) {
		// TODO Auto-generated method stub
		this.IdLsx=LsxId;
	}

	@Override
	public String getLSXId() {
		// TODO Auto-generated method stub
		return IdLsx;
	}

	@Override
	public void setIdSp(String _IdSp) {
		// TODO Auto-generated method stub
		this.IdSp=_IdSp;
	}

	@Override
	public String getIdSp() {
		// TODO Auto-generated method stub
		return this.IdSp;
	}

	@Override
	public void setMaSp(String _masp) {
		// TODO Auto-generated method stub
		this.Masp=_masp;
	}

	@Override
	public String getMaSp() {
		// TODO Auto-generated method stub
		return this.Masp;
	}

	@Override
	public void setSoluong(String soluong) {
		// TODO Auto-generated method stub
		this.Soluong=soluong;
	}

	@Override
	public String getSoluong() {
		// TODO Auto-generated method stub
		return this.Soluong;
	}

	@Override
	public void setTenSp(String _TenSp) {
		// TODO Auto-generated method stub
		this.Tensp=_TenSp;
	}

	@Override
	public String getTenSp() {
		// TODO Auto-generated method stub
		return this.Tensp;
	}

	@Override
	public void setKhoId(String KhoId) {
		// TODO Auto-generated method stub
		this.Idkho=KhoId;
	}

	@Override
	public String getKhoId() {
		// TODO Auto-generated method stub
		return this.Idkho;
	}

	@Override
	public void setKhoTen(String Kho) {
		// TODO Auto-generated method stub
		this.Tenkho=Kho;
	}

	@Override
	public String getKhoTen() {
		// TODO Auto-generated method stub
		return this.Tenkho;
	}

	@Override
	public void setSolo(String Solo) {
		// TODO Auto-generated method stub
		this.Solo=Solo;
	}

	@Override
	public String getSolo() {
		// TODO Auto-generated method stub
		return this.Solo;
	}

	@Override
	public void setkhuvuckhoId(String khuvuckhoId) {
		// TODO Auto-generated method stub
		this.IdKVK=khuvuckhoId;
	}

	@Override
	public String getkhuvuckhoId() {
		// TODO Auto-generated method stub
		return this.IdKVK;
	}

	@Override
	public void setkhuvuckhoTen(String khuvuckhoTen) {
		// TODO Auto-generated method stub
		this.TenKVK=khuvuckhoTen;
	}

	@Override
	public String getkhuvuckhoTen() {
		// TODO Auto-generated method stub
		return this.TenKVK;
	}

	@Override
	public void setMARQUETTE_FK(String MARQUETTE_FK) {
		// TODO Auto-generated method stub
		this.IdMq=MARQUETTE_FK;
	}

	@Override
	public String getMARQUETTE_FK() {
		// TODO Auto-generated method stub
		return  this.IdMq;
	}

	@Override
	public void setMARQUETTE(String MARQUETTE) {
		// TODO Auto-generated method stub
		this.TenMq=MARQUETTE;
	}

	@Override
	public String getMARQUETTE() {
		// TODO Auto-generated method stub
		return this.TenMq;
	}

	@Override
	public void setMATHUNG(String MATHUNG) {
		// TODO Auto-generated method stub
		this.Mathung=MATHUNG;
	}

	@Override
	public String getMATHUNG() {
		// TODO Auto-generated method stub
		return this.Mathung;
	}

	@Override
	public void setMAME(String MAME) {
		// TODO Auto-generated method stub
		this.Mame=MAME;
	}

	@Override
	public String getMAME() {
		// TODO Auto-generated method stub
		return this.Mame;
	}

	@Override
	public void setNGAYNHAPKHO(String NGAYNHAPKHO) {
		// TODO Auto-generated method stub
		this.Ngaynhapkho=NGAYNHAPKHO;
	}

	@Override
	public String getNGAYNHAPKHO() {
		// TODO Auto-generated method stub
		return this.Ngaynhapkho;
	}

	@Override
	public void setHAMLUONG(String HAMLUONG) {
		// TODO Auto-generated method stub
		this.Hamluong =HAMLUONG;
	}

	@Override
	public String getHAMLUONG() {
		// TODO Auto-generated method stub
		return this.Hamluong;
	}

	@Override
	public void setHAMAM(String HAMAM) {
		// TODO Auto-generated method stub
		this.Hamam=HAMAM;
	}

	@Override
	public String getHAMAM() {
		// TODO Auto-generated method stub
		return this.Hamam;
	}

	@Override
	public void setNGAYHETHAN(String NGAYHETHAN) {
		// TODO Auto-generated method stub
		this.Ngayhethan=NGAYHETHAN;
	}

	@Override
	public String getNGAYHETHAN() {
		// TODO Auto-generated method stub
		return this.Ngayhethan;
	}

	@Override
	public void setSoluongton(String Soluongton) {
		// TODO Auto-generated method stub
		this.soluongton=Soluongton;
	}

	@Override
	public String getSoluongton() {
		// TODO Auto-generated method stub
		return this.soluongton;
	}

	@Override
	public void setIdSpThayThe(String IdSpThayThe) {
		// TODO Auto-generated method stub
		this.IdSpThayThe=IdSpThayThe;
	}

	@Override
	public String getIdSpThayThe() {
		// TODO Auto-generated method stub
		return this.IdSpThayThe;
	}

	@Override
	public void setSoluongtonthute(String Soluongtonthute) {
		// TODO Auto-generated method stub
		this.soluongtonthucte=Soluongtonthute;
	}

	@Override
	public String getSoluongtonthute() {
		// TODO Auto-generated method stub
		return this.soluongtonthucte;
	}

	@Override
	public void setMAPHIEU(String MAPHIEU) {
		// TODO Auto-generated method stub
		this.Maphieu=MAPHIEU;
	}

	@Override
	public String getMAPHIEU() {
		// TODO Auto-generated method stub
		return this.Maphieu;
	}

	@Override
	public void setMAPHIEU_EO(String MAPHIEUEO) {
		// TODO Auto-generated method stub
		this.MaphieuEo=MAPHIEUEO;
	}

	@Override
	public String getMAPHIEU_EO() {
		// TODO Auto-generated method stub
		return this.MaphieuEo;
	}

	@Override
	public void setMAPHIEU_DINHTINH(String MAPHIEU_TINHTINH) {
		// TODO Auto-generated method stub
		this.Maphieudinhtinh=MAPHIEU_TINHTINH;
	}

	@Override
	public String getMAPHIEU_TINHTINH() {
		// TODO Auto-generated method stub
		return this.Maphieudinhtinh;
	}

	@Override
	public void setBinId(String BinId) {
		// TODO Auto-generated method stub
		this.BinId=BinId;
	}

	@Override
	public String getBinId() {
		// TODO Auto-generated method stub
		return this.BinId;
	}

	@Override
	public void setBin(String Bin) {
		// TODO Auto-generated method stub
		this.Bin=Bin;
	}

	@Override
	public String getBin() {
		// TODO Auto-generated method stub
		return this.Bin;
	}

	@Override
	public void setloaidoituong(String loaidoituong) {
		// TODO Auto-generated method stub
		this.loaidoituong=loaidoituong;
	}

	@Override
	public String getloaidoituong() {
		// TODO Auto-generated method stub
		return this.loaidoituong;
	}

	@Override
	public void setDoituongId(String doituongId) {
		// TODO Auto-generated method stub
		this.DoituongId=doituongId;
	}

	@Override
	public String getDoituongId() {
		// TODO Auto-generated method stub
		return this.DoituongId;
	}
	@Override
	public void setMANSX(String MANSX) {
		// TODO Auto-generated method stub
		this.MaNSX= MANSX;
	}

	@Override
	public String getMANSX() {
		// TODO Auto-generated method stub
		return this.MaNSX;
	}

	@Override
	public void setNSX_FK(String NSX_FK) {
		// TODO Auto-generated method stub
		this.NSX_FK =NSX_FK;
	}

	@Override
	public String getNSX_FK() {
		// TODO Auto-generated method stub
		return NSX_FK;
	}

}
