package geso.traphaco.center.util;


public class ViewItem
{
	private String value;
	private String align;
	private String color;
	
	public ViewItem()
	{
		this.value = "";
		this.align = "";
		this.color = "";
	}

	public ViewItem(String value, String align, String color)
	{
		this.value = value;
		this.align = align;
		this.color = color; 
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}