package geso.dms.center.util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
public class GPRSMultiModem implements Runnable
{
	String m_ModemAddress ="192.168.1.100";
	int m_Port =4;
	DataInputStream is = null;
	Socket MultiModemSocket = null;
	DataOutputStream os = null;
	boolean bconnected =false;
	boolean bWaitToRecv =true;
	Thread threadObj= new Thread(this,"");
/****************************************
*main method
*@param args
****************************************/
public static void main(String[] args)
{
	try
	{
		GPRSMultiModem objGPRSMultiModem = new
		GPRSMultiModem();
		InetAddress m_ModemAddress =
		InetAddress.getByName(
		objGPRSMultiModem.m_ModemAddress);
		objGPRSMultiModem.Connect(m_ModemAddress,
		objGPRSMultiModem.m_Port);
		Thread.sleep(1000);
		objGPRSMultiModem.threadObj.start();
		objGPRSMultiModem.SendMsg("at+cmgf=1\r\n",false);
		Thread.sleep(2000);
		objGPRSMultiModem.SendMsg(
		"at+cnmi=2,2,0,0,0\r\n",false);
		Thread.sleep(1000);
		objGPRSMultiModem.PrepareSMSMsg("0909759178",
		"Hello Krishna");
	}
	catch(Exception e)
	{
	System.out.println("Modem Error ");
	}
}
/****************************************
*run method of Runnable interface
*@param args
****************************************/
public void run()
{
	ReceiveMsg();
}
/****************************************
*Connect method of Runnable interface
*@param InetAddress, Port
****************************************/
public void Connect(InetAddress ModemAddress, int Port)
{
		try
		{
			MultiModemSocket = new Socket(
			ModemAddress.getHostName(), Port);
			os = new DataOutputStream(
			MultiModemSocket.getOutputStream());
			is = new DataInputStream(
			MultiModemSocket.getInputStream());
			bconnected=true;
		}
		catch (UnknownHostException e)
		{
			bconnected=false;
			System.out.println("Don't know about host - ");
		}
		catch (IOException e)
		{
			bconnected=false;
			System.out.println("Couldn't get I/O for the connection to:hostname - ");
		}
}
/******************************************
* Close the Socket connection
*****************************************/
public void CloseSocket()
{
	try
	{
		os.close();
		is.close();
		MultiModemSocket.close();
		bconnected=false;
		System.out.println("Close Sockets");
	}
	catch (IOException e)
	{
		System.out.println(e.getLocalizedMessage());
	}
}
/******************************************
* Receive Messages from MultiModem.
******************************************/
public void ReceiveMsg()
{
	while(bWaitToRecv)
	{
		String responseLine="";
		SendMsg("\r",false);
		int iter =0;
	try
	{
		if (is != null)
		{
		while (iter<20)
		{
		responseLine = is.readLine();
		Thread.sleep(1000);
		if(!responseLine.isEmpty())
		{
		break;
		}
		iter++;
		}
		}
	}
	catch (UnknownHostException e)
	{
		System.out.println("Trying to connect to unknown host: "
		+ e.getLocalizedMessage());
	}
	catch (IOException e)
	{
		System.out.println(e.getLocalizedMessage());
	}
	catch (Exception e)
	{
	System.out.println(e.getLocalizedMessage());
	}
	if(!responseLine.isEmpty())
	System.out.println("responseLine = "+responseLine);
	}
}
/****************************************
*Connect method of Runnable interface
*@param String, boolean
****************************************/
public void SendMsg(String strMsg, boolean bValue)
{
	if (MultiModemSocket != null && os != null && is != null)
	{
	try
	{
		if(bValue)
		{
		os.writeBytes(strMsg);
		os.writeByte(0x1A);
		}
		else
		os.writeBytes(strMsg);
	}
	catch (UnknownHostException e)
	{
		System.out.println("Trying to connect to unknownhost: " + e.getLocalizedMessage());
	}
	catch (IOException e)
	{
		System.out.println("IOException: " +
		e.getLocalizedMessage());
	}
	}
}
/************************************************
* Prepare SMS Msg Command
*@param String, String
************************************************/
	public void PrepareSMSMsg(String strPhone, String strMsg)
	 {
		try
		{
			String Str ="at+cmgs=\""+strPhone+"\"";
			SendMsg(Str, false);
			Thread.sleep(5000);
			SendMsg(strMsg, true);
		}
		catch (Exception e)
		{
			System.out.println("IOException: " +
				e.getLocalizedMessage());
		} 
	}
}
