<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.baocaotaisancodinh.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<% IBcTaisancodinh obj = (IBcTaisancodinh)session.getAttribute("obj"); %>
<% ResultSet tscdList = (ResultSet)obj.getTscdlist(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>TraphacoHYERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>  	
   
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<link href="../css/mybutton.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>	
	
    <SCRIPT language="javascript" type="text/javascript">
	 function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	 function submitform()
	 {   
		var nam = document.getElementById("nam");

		if(nam.value == "")
		{
		 	alert("Vui lòng chọn năm !");
		 	return;
		}
		document.forms["tscdForm"].action.value = "tao"; 
	    document.forms["tscdForm"].submit();
	 }
	
	 function thongbao()
	 {
		 var tt = document.forms["tscdForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["tscdForm"].msg.value);
	 }
	 
	</SCRIPT>
</head>
<body>
<form name="tscdForm" method="post" action="../../TheodoitaisancodinhSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&#160; Quản lý tài sản > Theo dõi tài sản cố định
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Theo dõi tài sản cố định</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                                              			
								
				<TR>
					<TD class="plainlabel">Năm </TD> 
					<TD class="plainlabel"> 
						<select name="nam" id="nam">
							<option value="" ></option>
							<% for(int i = 2010; i < 2018; i++ ) {
								 if( Integer.toString(i).equals(obj.getNam()) ) { %>
								 	<option value="<%= i %>" selected="selected" ><%= i %></option>
								 <% } else { %>
								 	<option value="<%= i %>" ><%= i %></option>
								 <% } %>
							<% } %>
						</select>
					</TD>
				</TR>
				<TR>
					<TD class="plainlabel">Loại</TD>
								<TD class="plainlabel" colspan=3>
									<select name="loai" id="loai" >
                            	
                        				<option value="1" selected >Tài sản</option>
                        				<option value="2"  >Công cụ dụng cụ</option>
                        		     </select>
									
									
								</TD>
				</TR>
                                     
                 <TR>
                        <td colspan="2" class="plainlabel">
                           <a class="button" href="javascript:submitform();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo  </a>
                        </td>
                 </TR> 
                    
                </TABLE>                  
       </fieldset>            					                    
    	</div>
    </div>  
</div>
</form>
</body>
</html>
<%
	try{
		if(tscdList!=null){
			tscdList.close();
		}
		
		obj.DBClose();
		session.setAttribute("obj", null) ;  ; 
	}catch(Exception err){
		
	}
	
	

%>