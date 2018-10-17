package geso.traphaco.center.beans.cauhinhinhoadon.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.beans.cauhinhinhoadon.IErpCauHinhInHoaDon;
import geso.traphaco.center.db.sql.dbutils;

public class ErpCauHinhInHoaDon implements IErpCauHinhInHoaDon
{

	float convert = 36;
	float marginLeft, marginRight, marginTop, marginBottom, paddingLeft,
			paddingRight, paddingTop, paddingBottom, lineSpacing, fontSize,
			tableHeight;
	float noColumn, productColumn, unitColumn, quantityColumn, unipriceColumn,
			amountColumn, footer_1, footer_2, footer_3, footer_4;
	int numberRow;

	String msg, ctyId;
	dbutils db;

	public ErpCauHinhInHoaDon()
	{
		this.marginLeft = 0;
		this.marginRight = 0;
		this.marginTop = 0;
		this.marginBottom = 0;
		this.paddingLeft = 0;
		this.paddingRight = 0;
		this.paddingTop = 0;
		this.paddingBottom = 0;
		this.lineSpacing = 0;
		this.fontSize = 0;
		this.tableHeight = 0;
		this.numberRow = 0;
		this.noColumn = 0;
		this.productColumn = 0;
		this.unipriceColumn = 0;
		this.unitColumn = 0;
		this.amountColumn = 0;
		this.msg = "";
		this.ctyId="";
		this.db = new dbutils();
	}

	public float getMarginLeft()
	{
		return this.marginLeft;
	}

	public void setMarginLeft(float marginLeft)
	{
		this.marginLeft = marginLeft;
	}

	public float getMarginRight()
	{
		return this.marginRight;
	}

	public void setMarginRight(float marginRight)
	{
		this.marginRight = marginRight;
	}

	public float getMarginTop()
	{

		return this.marginTop;
	}

	public void setMarginTop(float marginTop)
	{
		this.marginTop = marginTop;
	}

	public float getMarginBottom()
	{

		return this.marginBottom;
	}

	public void setMarginBottom(float marginBottom)
	{
		this.marginBottom = marginBottom;

	}

	public float getPaddingLeft()
	{

		return this.paddingLeft;
	}

	public void setPaddingLeft(float paddingLeft)
	{
		this.paddingLeft = paddingLeft;
	}

	public float getPaddingRight()
	{

		return this.paddingRight;
	}

	public void setPaddingRight(float paddingRight)
	{
		this.paddingRight = paddingRight;
	}

	public float getPaddingTop()
	{

		return this.paddingTop;
	}

	public void setPaddingTop(float paddingTop)
	{
		this.paddingTop = paddingTop;
	}

	public float getPaddingBottom()
	{

		return this.paddingBottom;
	}

	public void setPaddingBottom(float paddingBottom)
	{
		this.paddingBottom = paddingBottom;
	}

	public float getLineSpacing()
	{

		return this.lineSpacing;
	}

	public void setLineSpacing(float lineSpacing)
	{
		this.lineSpacing = lineSpacing;
	}

	public float getFontSize()
	{

		return this.fontSize;
	}

	public void setFontSize(float fontSize)
	{
		this.fontSize = fontSize;
	}

	public float getTableHeight()
	{

		return this.tableHeight;
	}

	public void setTableHeight(float tableHeight)
	{
		this.tableHeight = tableHeight;

	}

	public int getNumberOfRow()
	{

		return this.numberRow;
	}

	public void setNumerOfRow(int numberOfRow)
	{
		this.numberRow = numberOfRow;

	}
	public void init_PageConfig()
	{
		String query = "select  MARGIN_TOP,MARGIN_LEFT,FONT_SIZE,MARGIN_RIGHT,MARGIN_BOTTOM  from Erp_CauHinhInHoaDon where name='PAGE'";
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.marginTop = rs.getFloat("MARGIN_TOP");
					this.marginLeft = rs.getFloat("MARGIN_LEFT");
					this.marginRight=rs.getFloat("MARGIN_RIGHT");
					this.marginBottom=rs.getFloat("MARGIN_RIGHT");
					this.fontSize = rs.getFloat("FONT_SIZE");
				}
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void init_SoConfig()
	{

		String query = "select MARGIN_TOP,MARGIN_LEFT,FONT_SIZE,MARGIN_RIGHT,MARGIN_BOTTOM  from Erp_CauHinhInHoaDon where name='SO'";
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.marginTop = rs.getFloat("MARGIN_TOP");
					this.marginLeft = rs.getFloat("MARGIN_LEFT");
					this.marginRight=rs.getFloat("MARGIN_RIGHT");
					this.marginBottom=rs.getFloat("MARGIN_RIGHT");
					this.fontSize = rs.getFloat("FONT_SIZE");
				}
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void init_KhachHang_Config()
	{
		String query = "select  MARGIN_TOP,MARGIN_LEFT,FONT_SIZE,MARGIN_RIGHT,MARGIN_BOTTOM,LINE_SPACING  from Erp_CauHinhInHoaDon where name='CUSTOMMER'";
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.marginTop = rs.getFloat("MARGIN_TOP");
					this.marginLeft = rs.getFloat("MARGIN_LEFT");
					this.marginRight=rs.getFloat("MARGIN_RIGHT");
					this.marginBottom=rs.getFloat("MARGIN_RIGHT");
					this.fontSize = rs.getFloat("FONT_SIZE");
					this.lineSpacing=rs.getFloat("LINE_SPACING");
				}
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void init_DonHang_Config()
	{

		String query = "select TABLE_HEIGHT,NO_WIDTH,DESCRIPTION_WIDTH,UNIT_WIDTH,QUANTITY_WIDTH,UNIT_PRICE_WIDTH,AMOUNT_WIDTH,NUMBER_ROW,LINE_SPACING  from Erp_CauHinhInHoaDon where name='DETAILS'";
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.tableHeight = rs.getFloat("TABLE_HEIGHT");
					this.noColumn = rs.getFloat("NO_WIDTH");
					this.productColumn = rs.getFloat("DESCRIPTION_WIDTH");
					this.unitColumn = rs.getFloat("UNIT_WIDTH");
					this.quantityColumn = rs.getFloat("QUANTITY_WIDTH");
					this.unipriceColumn = rs.getFloat("UNIT_PRICE_WIDTH");
					this.amountColumn = rs.getFloat("AMOUNT_WIDTH");
					this.numberRow = rs.getInt("NUMBER_ROW");
					this.lineSpacing = rs.getFloat("LINE_SPACING");
				}
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

	}

	public String getMsg()
	{

		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;

	}

	public void init_Footer_config()
	{
		String query = "select TABLE_HEIGHT,NO_WIDTH,DESCRIPTION_WIDTH,UNIT_WIDTH,QUANTITY_WIDTH,UNIT_PRICE_WIDTH,AMOUNT_WIDTH,NUMBER_ROW,LINE_SPACING  from Erp_CauHinhInHoaDon where name='FOOTER'";
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.tableHeight = rs.getFloat("TABLE_HEIGHT");
					this.noColumn = rs.getFloat("NO_WIDTH");
					this.productColumn = rs.getFloat("DESCRIPTION_WIDTH");
					this.unitColumn = rs.getFloat("UNIT_WIDTH");
					this.quantityColumn = rs.getFloat("QUANTITY_WIDTH");
					this.unipriceColumn = rs.getFloat("UNIT_PRICE_WIDTH");
					this.amountColumn = rs.getFloat("AMOUNT_WIDTH");
					this.numberRow = rs.getInt("NUMBER_ROW");
					this.lineSpacing = rs.getFloat("LINE_SPACING");
				}
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}

	public float getNoColumn()
	{

		return this.noColumn;
	}

	public void setNoColumn(float no)
	{
		this.noColumn = no;
	}

	public float getProductColumn()
	{

		return this.productColumn;
	}

	public void setProductColumn(float product)
	{
		this.productColumn = product;
	}

	public float getUnitColumn()
	{

		return this.unitColumn;
	}

	public void setUnitColumn(float unit)
	{
		this.unitColumn = unit;
	}

	public float getQuantityColumn()
	{

		return this.quantityColumn;
	}

	public void setQuantityColumn(float quantity)
	{
		this.quantityColumn = quantity;
	}

	public float getUniPriceColumn()
	{

		return this.unipriceColumn;
	}

	public void setUnitPriceColumn(float unitprice)
	{
		this.unipriceColumn = unitprice;
	}

	public float getAmoutColumn()
	{

		return this.amountColumn;
	}

	public void setAmountColumn(float amount)
	{
		this.amountColumn = amount;
	}

	public float getFooter_1()
	{

		return this.footer_1;
	}

	public void setFooter_1(float Footer_1)
	{
		this.footer_1 = Footer_1;
	}

	public float getFooter_2()
	{

		return this.footer_2;
	}

	public void setFooter_2(float Footer_2)
	{
		this.footer_2 = Footer_2;
	}

	public float getFooter_3()
	{

		return this.footer_3;
	}

	public void setFooter_3(float Footer_3)
	{
		this.footer_3 = Footer_3;
	}

	public float getFooter_4()
	{

		return this.footer_4;
	}

	public void setFooter_4(float Footer_4)
	{
		this.footer_4 = Footer_4;
	}/*

	public boolean update_PageConfig()
	{
		String query="UPDATE ERP_CAUHINHINHOADON SET MARGIN_LEFT='"+this.marginLeft+"',MARGIN_RIGHT='"+this.marginRight+"'," +
				"MARGIN_TOP='"+this.marginTop+"',MARGIN_BOTTOM='"+this.marginBottom+"' WHERE NAME='PAGE'";
		try
		{
			this.db.getConnection().setAutoCommit(false);
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Loi "+query;
				System.out.println("In hoa don "+query);
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	public boolean update_SoConfig()
	{
		String query="UPDATE ERP_CAUHINHINHOADON SET MARGIN_LEFT='"+this.marginLeft+"',MARGIN_RIGHT='"+this.marginRight+"'," +
				"MARGIN_TOP='"+this.marginTop+"',MARGIN_BOTTOM='"+this.marginBottom+"' WHERE NAME='SO'";
		try
		{
			this.db.getConnection().setAutoCommit(false);
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Loi "+query;
				System.out.println("In hoa don "+query);
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	public boolean update_KhachHangConfig()
	{
		String query="UPDATE ERP_CAUHINHINHOADON SET MARGIN_LEFT='"+this.marginLeft+"',MARGIN_RIGHT='"+this.marginRight+"'," +
				"MARGIN_TOP='"+this.marginTop+"',MARGIN_BOTTOM='"+this.marginBottom+"',LINE_SPACING='"+this.lineSpacing+"' WHERE NAME='CUSTOMMER'";
		try
		{
			this.db.getConnection().setAutoCommit(false);
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Loi "+query;
				System.out.println("In hoa don "+query);
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	public boolean update_DonHangConfig()
	{

		String query="UPDATE ERP_CAUHINHINHOADON SET MARGIN_LEFT='"+this.marginLeft+"',MARGIN_RIGHT='"+this.marginRight+"'," +
				"MARGIN_TOP='"+this.marginTop+"',MARGIN_BOTTOM='"+this.marginBottom+"',LINE_SPACING='"+this.lineSpacing+"'," +
				"NUMBER_ROW='"+this.numberRow+"',NO_WIDTH='"+this.noColumn+"',DESCRIPTION_WIDTH='"+this.productColumn+"',UNIT_WIDTH='"+this.unitColumn+"'," +
				"QUANTITY_WIDTH='"+this.quantityColumn+"',AMOUNT_WIDTH='"+this.amountColumn+"',UNIT_PRICE_WIDTH='"+this.unipriceColumn+"',TABLE_HEIGHT='"+this.tableHeight+"'  WHERE NAME='DETAILS'";
		
		try
		{
			this.db.getConnection().setAutoCommit(false);
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Loi "+query;
				System.out.println("In hoa don "+query);
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	public boolean update_FooterConfig()
	{
		String query="UPDATE ERP_CAUHINHINHOADON SET MARGIN_LEFT='"+this.marginLeft+"',MARGIN_RIGHT='"+this.marginRight+"'," +
				"MARGIN_TOP='"+this.marginTop+"',MARGIN_BOTTOM='"+this.marginBottom+"',LINE_SPACING='"+this.lineSpacing+"',FOOTER_1='"+this.footer_1+"'," +
				"FOOTER_2='"+this.footer_2+"',FOOTER_3='"+this.footer_3+"',FOOTER_4='"+this.footer_4+"' WHERE NAME='FOOTER'";
		try
		{
			this.db.getConnection().setAutoCommit(false);
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Loi "+query;
				System.out.println("In hoa don "+query);
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return true;
	}*/

	@Override
	public void initWithName(String name) {
		String query = "select * from Erp_CauHinhInHoaDon where name='"+name+"'";
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.marginLeft = rs.getFloat("MARGIN_LEFT");
					this.marginTop = rs.getFloat("MARGIN_TOP");
					this.marginRight = rs.getFloat("MARGIN_RIGHT");
					this.marginBottom = rs.getFloat("MARGIN_BOTTOM");
					this.paddingLeft = rs.getFloat("PADDING_LEFT");
					this.paddingRight = rs.getFloat("PADDING_RIGHT");
					this.paddingTop = rs.getFloat("PADDING_TOP");
					this.paddingBottom = rs.getFloat("PADDING_BOTTOM");
					this.fontSize = rs.getFloat("FONT_SIZE");
					this.lineSpacing = rs.getFloat("LINE_SPACING");
					this.noColumn = rs.getFloat("NO_WIDTH");
					this.tableHeight = rs.getFloat("TABLE_HEIGHT");
					this.productColumn = rs.getFloat("DESCRIPTION_WIDTH");
					this.unitColumn = rs.getFloat("UNIT_WIDTH");
					this.quantityColumn = rs.getFloat("QUANTITY_WIDTH");
					this.unipriceColumn = rs.getFloat("UNIT_PRICE_WIDTH");
					this.amountColumn = rs.getFloat("AMOUNT_WIDTH");
					this.tableHeight = rs.getFloat("TABLE_HEIGHT");
					this.numberRow = rs.getInt("NUMBER_ROW");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public boolean updateWithName(String name)
	{
		boolean existed = false;
		String query = "SELECT NAME FROM ERP_CAUHINHINHOADON WHERE NAME = '"+name+"'";
		System.out.println("[ErpCauHinhInHoaDon.updateWithName] query = " + query);
		ResultSet rs = this.db.get(query);
		if (rs != null) {
			try
			{
				if(rs.next()) {
					existed = true;
				}
				
			} catch (Exception e) {
				System.out.println("[ErpCauHinhInHoaDon.updateWithName] Exception message = " + e.getMessage() );
			}
		}
		
		if(existed) {
			query="UPDATE ERP_CAUHINHINHOADON SET " +
					  "MARGIN_LEFT='"+this.marginLeft +
					"',MARGIN_RIGHT='"+this.marginRight+
					"',MARGIN_TOP='"+this.marginTop+
					"',MARGIN_BOTTOM='"+this.marginBottom+
	
					"',PADDING_LEFT='"+this.paddingLeft+
					"',PADDING_RIGHT='"+this.paddingRight+
					"',PADDING_TOP='"+this.paddingTop+
					"',PADDING_BOTTOM='"+this.paddingBottom+
					
					"',FONT_SIZE='"+this.fontSize+
					"',LINE_SPACING='"+this.lineSpacing+
					
					"',NO_WIDTH='"+this.noColumn+
					"',DESCRIPTION_WIDTH='"+this.productColumn+
					"',UNIT_WIDTH='"+this.unitColumn+
					"',QUANTITY_WIDTH='"+this.quantityColumn+
					"',UNIT_PRICE_WIDTH='"+this.unipriceColumn+
					"',AMOUNT_WIDTH='"+this.amountColumn+
					"',TABLE_HEIGHT='"+this.tableHeight+
					"',NUMBER_ROW='"+this.numberRow+
					
					"' WHERE NAME='"+name+"'";
			System.out.println("[ErpCauHinhInHoaDon.updateWithName] query = " + query );
			try
			{
				this.db.getConnection().setAutoCommit(false);
				if(!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg="Lỗi (" + query + ")";
					System.out.println("[ErpCauHinhInHoaDon.updateWithName] query = " + query);
					return false;
				}
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		else 
		{
			//INSERT
			
			query =	" INSERT ERP_CAUHINHINHOADON (NAME,MARGIN_LEFT,MARGIN_RIGHT,MARGIN_TOP,MARGIN_BOTTOM,PADDING_LEFT,PADDING_RIGHT,PADDING_TOP,PADDING_BOTTOM,FONT_SIZE,LINE_SPACING,NO_WIDTH,DESCRIPTION_WIDTH,UNIT_WIDTH,QUANTITY_WIDTH,UNIT_PRICE_WIDTH,AMOUNT_WIDTH,TABLE_HEIGHT,NUMBER_ROW) " +
					" VALUES('"+name+"','"+this.marginLeft +"','"+this.marginRight+"','"+this.marginTop+"','"+this.marginBottom+"','"+this.paddingLeft+"','"+this.paddingRight+"','"+this.paddingTop+"','"+this.paddingBottom+"','"+this.fontSize+"','"+this.lineSpacing+"','"+this.noColumn+"','"+this.productColumn+"','"+this.unitColumn+"','"+this.quantityColumn+"','"+this.unipriceColumn+"','"+this.amountColumn+"','"+this.tableHeight+"','"+this.numberRow+"') ";
			System.out.println("[ErpCauHinhInHoaDon.updateWithName] query = " + query );
			try
			{
				this.db.getConnection().setAutoCommit(false);
				if(!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg="Lỗi (" + query + ")";
					System.out.println("[ErpCauHinhInHoaDon.updateWithName] query = " + query);
					return false;
				}
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public void dbClose() {
		if(db != null) {
			db.shutDown();
		}
	}
	
	
}
