package geso.traphaco.center.beans.asm;


import java.sql.ResultSet;

public interface IAsmList {
	
	public String getTen();
	
	public void setTen(String asmTen);
	
	public String getDienthoai();
	
	public void setDienthoai(String dienthoai);
	
	public String getEmail();
	
	public void setEmail(String email);

	public String getDiachi();
	
	public void setDiachi(String diachi);

	public String getKbhId();
	
	public void setKbhId(String kbhId);

	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);

	public String getKvId();
	
	public void setKvId(String kvId);

	public String getTrangthai();
	
	public void setTrangthai(String trangthai);

	public String getMsg();
	
	public void setMsg(String msg);

	public ResultSet getKbh();
	
	public void setKbh(ResultSet kbh);

	public ResultSet getDvkd();
	
	public void setDvkd(ResultSet dvkd);

	public ResultSet getKv();
	
	public void setKv(ResultSet kv);

	public ResultSet getAsmlist();
	
	public void setAsmlist(ResultSet asmlist);
	
	public void init();
	
	public void Delete(String asmid);
	
	public void DBClose();
	
	public String getMaFAST();
	public void setMaFAST(String maFAST);
}
