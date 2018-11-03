package geso.traphaco.report.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;

public class DbReportFill
{
	Connection connection;
	private String username = "salesup";
	private String password = "salesup@123";
	private String url = "jdbc:jtds:sqlserver://localhost:1433/Traphaco";
	public void generateReport()
	{
		try
		{
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			JasperFillManager.fillReportToFile("reports/HoaDon_OTC_Mien_Bac.jasper", new HashMap(), connection);
			System.out.println("Done!");
			connection.close();
		} catch (JRException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) throws JRException
	{
		try
    {
	    JasperFillManager.fillReportToFile("reports/FirstReport.jasper",new HashMap(), new JREmptyDataSource());
    } catch (Exception e)
    {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
	}
}