package geso.traphaco.center.beans.cauhinhinhoadon;

public interface IErpCauHinhInHoaDon {

	public String getCtyId();

	public void setCtyId(String ctyId);

	public float getMarginLeft();

	public void setMarginLeft(float marginLeft);

	public float getMarginRight();

	public void setMarginRight(float marginRight);

	public float getMarginTop();

	public void setMarginTop(float marginTop);

	public float getMarginBottom();

	public void setMarginBottom(float marginBottom);

	public float getPaddingLeft();

	public void setPaddingLeft(float paddingLeft);

	public float getPaddingRight();

	public void setPaddingRight(float paddingRight);

	public float getPaddingTop();

	public void setPaddingTop(float paddingTop);

	public float getPaddingBottom();

	public void setPaddingBottom(float paddingBottom);

	public float getLineSpacing();

	public void setLineSpacing(float lineSpacing);

	public float getFontSize();

	public void setFontSize(float fontSize);

	public float getTableHeight();

	public void setTableHeight(float tableHeigth);

	public int getNumberOfRow();

	public void setNumerOfRow(int numberOfRow);

	public float getNoColumn();

	public void setNoColumn(float no);

	public float getProductColumn();

	public void setProductColumn(float product);

	public float getUnitColumn();

	public void setUnitColumn(float unit);

	public float getQuantityColumn();

	public void setQuantityColumn(float quantity);

	public float getUniPriceColumn();

	public void setUnitPriceColumn(float unitprice);

	public float getAmoutColumn();

	public void setAmountColumn(float amount);

	public float getFooter_1();

	public void setFooter_1(float Footer_1);

	public float getFooter_2();

	public void setFooter_2(float Footer_2);

	public float getFooter_3();

	public void setFooter_3(float Footer_3);

	public float getFooter_4();

	public void setFooter_4(float Footer_4);

	public String getMsg();

	public void setMsg(String msg);

	// Config trang in(kho giay,margin...)
	/*public void init_PageConfig();

	public boolean update_PageConfig();

	public boolean update_SoConfig();

	public boolean update_KhachHangConfig();

	public boolean update_DonHangConfig();

	public boolean update_FooterConfig();*/

	// Config vi tri so So
	/*public void init_SoConfig();

	// Config in thong tin khach hang
	public void init_KhachHang_Config();

	public void init_DonHang_Config();

	public void init_Footer_config();*/
	
	public void initWithName(String name);
	public boolean updateWithName(String name);
	
	public void dbClose();

}
