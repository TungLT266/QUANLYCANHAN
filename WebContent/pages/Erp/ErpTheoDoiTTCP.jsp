<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<% ILapngansachList obj = (ILapngansachList)session.getAttribute("obj"); %>
<% ResultSet ttcpList = (ResultSet)obj.getTtcplist(); %>

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
		 var thang = document.getElementById("thang");
		 var nam = document.getElementById("nam");
		 var ttcpId = document.getElementById("ttcpId");
		 if(thang.value == "")
		 {
		 	alert("Vui lòng chọn tháng !");
		 	return;
		 }
		 
		 if(nam.value == "")
		 {
		 	alert("Vui lòng chọn năm !");
		 	return;
		 }
		 
		 if(ttcpId.value == "")
		 {
		 	alert("Vui lòng chọn trung tâm chi phí !");
		 	return;
		 }
	    document.forms["erpDmhForm"].submit();
	 }
	
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 
	 function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var nccIds = document.getElementsByName("nccIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < nccIds.length; i++)
			 {
				 nccIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < nccIds.length; i++)
			 {
				 nccIds.item(i).checked = false;
			 }
		 }
	 }
	 function sellectNhomAll()
	 {
		 var chkAll = document.getElementById("chkNhAll");
		 var nccIds = document.getElementsByName("nspIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < nccIds.length; i++)
			 {
				 nccIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < nccIds.length; i++)
			 {
				 nccIds.item(i).checked = false;
			 }
		 }
	 }
	</SCRIPT>
</head>
<body>
<form name="erpDmhForm" method="post" action="../../TheodoitrungtamchiphiSvl">
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
        	&#160; Quản lý ngân sách > Theo dõi ngân sách > Theo dõi trung tâm chi phí
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                                              			
				
				<TR>
					<TD width = "150px" class="plainlabel">Tháng </TD> 
					<TD class="plainlabel">
					  	<select name="thang" id="thang">
							<option value="" ></option>
							<% for(int i = 1; i < 12; i++ ) {
								 if( Integer.toString(i).equals(obj.getThang()) ) { %>
								 	<option value="<%= i %>" selected="selected" ><%= "Tháng " + i %></option>
								 <% } else { %>
								 	<option value="<%= i %>" ><%= "Tháng " + i %></option>
								 <% } %>
							<% } %>
						</select>			
					</TD>
				</TR>
				
				<TR>
					<TD class="plainlabel">Năm </TD> 
					<TD class="plainlabel"> 
						<select name="nam" id="nam">
							<option value="" ></option>
							<% for(int i = 2012; i < 2020; i++ ) {
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
                    <TD class="plainlabel" valign="middle" >Trung tâm chi phí </TD>
                    <TD class="plainlabel" valign="middle" >
                        <select name="ttcpId" id="ttcpId">
                         	<option value=""></option>
                            	<%
	                      		if(ttcpList != null)
	                       		{
	                       			while(ttcpList.next())
	                       			{  
	            	           			if( ttcpList.getString("pk_seq").equals(obj.getTtcpId())){ %>
	                        				<option value="<%= ttcpList.getString("pk_seq") %>" selected="selected" ><%= ttcpList.getString("diengiai") %></option>
	                        			<%}else { %>
	                        				<option value="<%= ttcpList.getString("pk_seq") %>" ><%= ttcpList.getString("diengiai") %></option>
	                        		 <% } } ttcpList.close();
	                       		}
	                        	%>
                         </select>
                    </TD>                        
                 </TR>                                       
                                      
                 <tr>
                     <td colspan="2" class="plainlabel">
                         <a class="button" href="javascript:submitform();"> 
                           	<img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo  </a>
                     </td>
                  </tr> 
                    
            </TABLE>                  
       </fieldset>            					                    
    	</div>
    </div>  
</div>
</form>
</body>
</html>
<%obj.DBClose();
session.removeAttribute("obj");
%>