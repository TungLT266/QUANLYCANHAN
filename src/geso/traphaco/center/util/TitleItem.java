package geso.traphaco.center.util;


public class TitleItem
{
	private String titleName;
	private String align;
	private int width;
	
	public TitleItem()
	{
		this.titleName = "";
		this.align = "";
		this.width = 10;
	}

	public TitleItem(String titleName, String align, int width)
	{
		this.titleName = titleName;
		this.align = align;
		this.width = width; 
	}
	
	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}