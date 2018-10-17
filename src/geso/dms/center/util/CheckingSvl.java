package geso.dms.center.util;

import geso.traphaco.erp.db.sql.dbutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CheckingSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	public CheckingSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//HttpSession session = request.getSession();
		String query = "";
		dbutils db = new dbutils();
		File path = new File("D:\\Project\\ViFon\\WebContent\\pages\\Center");

		File[] files = path.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				CheckingJSP(files[i].getAbsolutePath(), files[i].getName(), db);
			}
		}
		
		VerifyingBean("", db);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private static void CheckingJSP(String filePath, String fileName, dbutils db) {
		try {
			String str = "";
			String line;
			String[] arr;
			String tmp;
			int pos = 0;
			
			String fileStr = "";
			String openningResource = "";
			
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			System.out.println(filePath);
			
			while ((line = br.readLine()) != null) {
				fileStr = fileStr + line;
				
				if(line.indexOf("<%@ page") < 0 & line.indexOf("<%@page") < 0){
					
					if(line.indexOf("ResultSet") >= 0){
					
						pos = line.indexOf("=", line.indexOf("ResultSet") + 10 ) >= 0?line.indexOf("=", line.indexOf("ResultSet") + 10):(line.indexOf(";", line.indexOf("ResultSet") + 10) >= 0?line.indexOf(";", line.indexOf("ResultSet") + 10):0);
						if(pos >= 0){
							tmp = line.substring(line.indexOf("ResultSet") + 10, pos).trim();
							
							str = str + tmp + ";";
						}
					}else if(line.indexOf("List<") >= 0){
						int n = line.indexOf("List<");
						if(n >= 0){
							n = line.indexOf(">", n);
							pos = line.indexOf("=", n + 2) >= 0?line.indexOf("=", n + 2):(line.indexOf(";", n + 2) >= 0?line.indexOf(";", n + 2):0);
							if(pos >= 0){
								tmp = line.substring(n + 2, pos).trim();
								
								str = str + tmp + ";";
							}
						}

					}else if(line.indexOf("session.getAttribute(") >= 0){
						int n = line.indexOf("session.getAttribute(") + 22;

						pos = line.indexOf(")", n) - 1;
						
						if(pos >= 0){
							tmp = line.substring(n, pos).trim();
							if(!tmp.equals("userId") & !tmp.equals("userTen") & !tmp.equals("sum") & !tmp.equals("util")){
								str = str + "session:" + tmp + ";";
								
							}
						}
						
					}
				}
			}
			
			if(str.length() > 0){
				str = str.substring(0, (str.length() - 1));
				arr = str.split(";");
			
				for(int i = 0; i < arr.length; i++){
					if(fileStr.indexOf((arr[i].trim() + ".close"), 0) < 0 
					   & fileStr.indexOf((arr[i].trim() + ".clear"), 0) < 0 & arr[i].indexOf("session:") < 0){
						openningResource += arr[i].trim() + "	;	";
					
					}else if(arr[i].indexOf("session:") >= 0){
						if(fileStr.indexOf("session.setAttribute(\"" + arr[i].split(":")[1].trim() + "\", null)", 0) < 0){
							if(fileStr.indexOf("session.setAttribute(\"" + arr[i].split(":")[1].trim() + "\",null)", 0) < 0)
								if(fileStr.indexOf("session.removeAttribute(\"" + arr[i].split(":")[1].trim() + "\")", 0) < 0)
							openningResource +=  "session.setAttribute(\"" + arr[i].split(":")[1].trim() + "\", null)	;	";
						}
					}
				}
			}
			
			if(fileStr.indexOf("dbutils db") >= 0){
				if(fileStr.indexOf("db.shutDown()") < 0){
					openningResource += "db;" ;
				}
			}
			
			if(fileStr.indexOf("DBClose()") < 0 & fileStr.indexOf("DBclose()") < 0 
			   & fileStr.indexOf("DbClose()") < 0 & fileStr.indexOf("Dbclose()") < 0 
			   & fileStr.indexOf("Close()") < 0 & fileStr.indexOf("close()") < 0 
			   & fileStr.indexOf("closeDB()") < 0){
				
				openningResource += "DBClose()";
				//System.out.println("DBClose()");
			}
			
			if(openningResource.length() > 0){
				String query = "INSERT INTO CHECKING_LOG(PATH, FILENAME, VARIABLE) VALUES('" + filePath + "', '" + fileName + "', '" + openningResource + "') ";
				System.out.println(query);
				db.update(query);

			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	public static void main ( String args [  ]  )   {
			try{
			dbutils db = new dbutils();
//			VerifyingBean("D:\\Project\\Vifon\\src\\geso\\vifon\\center\\beans\\",db);
			File path = new File("D:\\Project\\Vifon\\WebContent\\pages\\Center");
						File[] files = path.listFiles();
						
						for (int i = 0; i < files.length; i++) {
							if (files[i].isFile()) {
								CheckingJSP(files[i].getAbsolutePath(), files[i].getName(), db);
							}
						}
			
			}catch(Exception er){
				er.printStackTrace();
			}
	    }
	

	  
	private static void VerifyingBean(String p, dbutils db){
	
		String path = "";
		
		if(p.length() == 0) {
			path = 	"D:\\Project\\Vifon\\src\\geso\\vifon\\center\\beans\\";
		}else{
			path = p;
		}
		
		File folderBeans = new File(path);
		
		File[] fList = folderBeans.listFiles();
		for (File file : fList) {

//		for (int i = 0; i < fList.length; i++) {
		    if (file.isFile()) {
		    	
		    	CheckingBean(file.getAbsolutePath(), file.getName(), db);
		    	
		    }else if (file.isDirectory()) {
		    	
		    	VerifyingBean(file.getAbsolutePath() + "\\imp", db);
	            //System.out.println(file.getAbsolutePath());
	            
	        }
			
		}


	}
	
	private static void CheckingBean(String filePath, String fileName, dbutils db) {
		try {
			String str = "";
			String line;
			String[] arr;
			String tmp;
			int pos = 0;
			
			String fileStr = "";
			String openningResource = "";
			
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			System.out.println(filePath);
			boolean isDBClose = false;
			while ((line = br.readLine()) != null) {
				fileStr = fileStr + line;
				
				if(line.indexOf("import") < 0 ){
					
					
					if(line.indexOf("ResultSet", 0) >= 0 & (line.indexOf("ResultSet", 0) < line.indexOf("(", 0))){
						System.out.println("line:" + line);
						if(line.indexOf("(", line.indexOf("ResultSet") + 10 ) < 0 || (line.indexOf("=", line.indexOf("ResultSet") + 10) >=0 & line.indexOf("(", line.indexOf("ResultSet") + 10 ) > line.indexOf("=", line.indexOf("ResultSet") + 10))){
							pos = line.indexOf("=", line.indexOf("ResultSet") + 10 ) >= 0?line.indexOf("=", line.indexOf("ResultSet") + 10):(line.indexOf(";", line.indexOf("ResultSet") + 10) >= 0?line.indexOf(";", line.indexOf("ResultSet") + 10):0);
							if(pos >= 0){
								isDBClose = true;
								tmp = line.substring(line.indexOf("ResultSet") + 10, pos).trim();
								System.out.println("Biến: " + tmp);
								str = str + tmp + ";";
							}
						}
					}else if(line.indexOf("List<") >= 0){
						System.out.println("line:" + line);
						int n = line.indexOf("List<");
						if(n >= 0){
							if(line.indexOf(")", line.indexOf("List<") + 2 ) < 0 || (line.indexOf("=", line.indexOf("List<") + 2) >=0 & line.indexOf("(", line.indexOf("List<") + 2 ) > line.indexOf("=", line.indexOf("List<") + 2))){

								n = line.indexOf(">", n);
								pos = line.indexOf("=", n + 2) >= 0?line.indexOf("=", n + 2):(line.indexOf(";", n + 2) >= 0?line.indexOf(";", n + 2):0);
								if(pos >= 0){
									try {
										isDBClose = true;
										tmp = line.substring(n + 2, pos).trim();
										System.out.println("Biến: " + tmp);
										str = str + tmp + ";";
									} catch (Exception e) {
										// TODO: handle exception
										e.printStackTrace();
									}
									
								}
							}
						}

					}else if(line.indexOf("Hashtable<") >= 0){
						System.out.println("line:" + line);
						int n = line.indexOf("Hashtable<");
						if(n >= 0){
							if(line.indexOf(")", line.indexOf("Hashtable<") + 2 ) < 0 || (line.indexOf("=", line.indexOf("Hashtable<") + 2) >=0 & line.indexOf("(", line.indexOf("Hashtable<") + 2 ) > line.indexOf("=", line.indexOf("Hashtable<") + 2))){

								n = line.indexOf(">", n);
								pos = line.indexOf("=", n + 2) >= 0?line.indexOf("=", n + 2):(line.indexOf(";", n + 2) >= 0?line.indexOf(";", n + 2):0);
								if(pos >= 0){
									isDBClose = true;
									tmp = line.substring(n + 2, pos).trim();
									System.out.println("Biến: " + tmp);
									str = str + tmp + ";";
								}
							}
						}

					}
				}
			}
			
			if(str.length() > 0){
				str = str.substring(0, (str.length() - 1));
				arr = str.split(";");
			
				for(int i = 0; i < arr.length; i++){
					if(fileStr.indexOf((arr[i].trim() + ".close"), 0) < 0 
					   & fileStr.indexOf((arr[i].trim() + ".clear"), 0) < 0 ){
						openningResource += arr[i].trim() + "	;	";
					
					}
				}
			}
			
			if(fileStr.indexOf("dbutils db") >= 0){
				isDBClose = true;
				if(fileStr.indexOf("db.shutDown()") < 0){
					openningResource += "db;" ;
				}
			}
			
			if(fileStr.indexOf("DBClose()") < 0 & fileStr.indexOf("DBclose()") < 0 
			   & fileStr.indexOf("DbClose()") < 0 & fileStr.indexOf("Dbclose()") < 0 
			   & fileStr.indexOf("Close()") < 0 & fileStr.indexOf("close()") < 0){
				if (isDBClose == true)
					openningResource += "DBClose()";
				//System.out.println("DBClose()");
			}
			
			if(openningResource.length() > 0){
				String query = "INSERT INTO CHECKING_LOG(PATH, FILENAME, VARIABLE) VALUES('" + filePath + "', '" + fileName + "', '" + openningResource + "') ";
				System.out.println(query);
				db.update(query);
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
