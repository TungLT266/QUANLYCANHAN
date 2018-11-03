package geso.traphaco.erp.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DinhDang {
	
	public static String dinhdangkho(double number)
	{
		NumberFormat formatter = new DecimalFormat("#,###,###.####");
		return formatter.format(number);
	}

	
	public static double dinhdangso( double number)
	{
		double kq = number;
		
		String heso = "1";
		//so chu so can dinh dang
		int so=4;
		for( int i = 1; i <= so; i++ )
			heso += "0";
		
		double _heso = Double.parseDouble(heso);
		
		//System.out.println("*** HE SO LA: " + _heso);
		kq = Math.round( number * _heso ) / _heso;
		return kq;
	}
	
	public static String dinhdanglamtron(double number)
	{
		NumberFormat formatter = new DecimalFormat("#,###,###");
		return formatter.format(number);
	}
	
	public static  String DINHDANGSOCHUANVN(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");

		return sotien;
	}

}
