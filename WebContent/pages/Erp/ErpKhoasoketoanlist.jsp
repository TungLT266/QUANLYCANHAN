 
<%@page import="geso.traphaco.erp.beans.khoasothang.IErpkhoasoketoanlist"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.util.Calendar"%>
 
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
 

<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");
%> 	

<%


	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
		IErpkhoasoketoanlist ltsList = (IErpkhoasoketoanlist)session.getAttribute("obj");
 		 ResultSet RsTs=ltsList.getRsList();
 		int[] quyen = new  int[5];
		

 	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>Phanam - Project</title>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
      $(document).ready(function() {            
            $( ".days" ).datepicker({                     
                        changeMonth: true,
                        changeYear: true                    
            });            
      });   
</script>


<script language="javascript" type="text/javascript">

function xoaform()
{
    document.cnForm.mats.value = "";
    document.cnForm.diengiai.value = "";
    document.cnForm.tk.value = "";
    submitform();
}

function submitform()
{
	document.forms['cnForm'].action.value= 'search';
	document.forms['cnForm'].submit();
}

function newform()
{
	document.forms['cnForm'].action.value= 'taomoi';
	document.forms['cnForm'].submit();
}

function thongbao()
{
	 var tt = document.forms["cnForm"].msg.value;
	 if(tt.length>0)
    	alert(document.forms["cnForm"].msg.value);
	}

</script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2(); });
	     
	 </script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="cnForm" method="post" action="../../ErpKhoasoketoanListSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%= ltsList.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<table width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<table width="100%" cellpadding="0" cellspacing="1">		
				<tr>
					<td align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <td align="left" colspan="2" class="tbnavigation">
								Quản lý kế toán > Khóa sổ kế toán
						   </td>
   
						   <td align = "right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td>
						  </tr>
 					  </table>
					</td>
				</tr>
				<tr>
					<td>
					<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td width="100%" align="center" >
							<fieldset>
							  <legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>
							<table width="100%" cellpadding="6" cellspacing="0">
								 
						      	
						          <tr class="plainlabel">
								<td width="15%" class="plainlabel" valign="middle">Năm </td>
								<td>
								<select name="nam" id="nam" onchange="submitview()"  style="width :70px" ">
									<option value=0> </option>  
									<%
									  
									 Calendar c2 = Calendar.getInstance();
										int t=c2.get(Calendar.YEAR) +3;
										
									
  										int n;
  										for(n=2010;n<t;n++){
  											if(ltsList.getNam()==n){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 											%>
 											<option value=<%=n%> > <%=n%></option> 
 											<%
 										}
 									
 										
 									 }
 									%>
 									</select>
								</td>
							</tr>
							<tr class="plainlabel">
								<td width="20%" class="plainlabel" valign="middle">Tháng </td>
								<td>
								<select name="thang"  onchange="submitview()" id="thang"  style="width :70px" ">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										
  									  if(ltsList.getThang() ==k){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 											%>
 											<option value=<%=k%> > <%=k%></option> 
 											<%
 										}
 									
 									  }
 									%>
									</select>
								</td>
								</tr>
								
                    
                    <tr>
								
							</table>

							</fieldset>
						</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td width="100%">
						<fieldset>
						<legend class="legendtitle">&nbsp;Khóa sổ &nbsp;&nbsp;&nbsp;
						
						
						
						<a class="button3" href="javascript:newform()">
    					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a>
    					                             
					 	</legend>
	
		   				<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<tr>
			  	   				<td align="left" colspan="4" class="legendtitle">
									<table class = "tabletitle" width="100%" border="0" cellspacing="1" cellpadding="4">
										<tr class="tbheader">
											<th width="7%">ID</th>
											<th width="7%">Tháng</th>
											<th width="7%">Năm</th>
											<th width="6%">Trạng thái</th>	
											<th width="5%">Ngày tạo</th>
											<th width="5%">Người tạo</th>
											 
											<th width="1%" align="center">&nbsp;Tác vụ</th>					
										</tr>
				
									<%
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(RsTs!=null)
									while (RsTs.next())
									{
										if(m % 2 != 0) {%>
										<tr class = <%=lightrow%> >
									<%} else {%>
										<tr class = <%=darkrow%> >
									<%}%>
									
										<td align="center"><%= RsTs.getString("SOPHIEU")%> </td>
										<td align="left"><%= RsTs.getString("thang")%> </td>
										<td align="left"><%= RsTs.getString("nam")%>   </td>
										
											<% 
										if(RsTs.getString("trangthai").equals("1")){
										%>	
											<td align="center">Hoạt Động </td>
											<%
										}else{
											%>
											<td align="center">Đã kết chuyển </td>
											<% 
										}
										%>
										
										<td align="center"><%= RsTs.getString("ngaytao") %> </td>
										<td align="center"><%= RsTs.getString("nguoitao")%> </td>
								 									
									
										<td align = "center">
											<table border = 0 cellpadding="0" cellspacing="0">
												<tr>
												<td>
												 	<% 
													if(RsTs.getString("trangthai").equals("0")){
													%>	
													<a href = "../../ErpKhoasoketoanSvl?userid=<%=userId%>&update=<%=RsTs.getString("SOPHIEU")%>"><img src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>
												 	<%}else {%>
												 	<a href = "../../ErpKhoasoketoanListSvl?userid=<%=userId%>&mokhoaso=<%=RsTs.getString("SOPHIEU")%>"><img src="../images/unChot.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>
												 	<a href = "../../ErpKhoasoketoanSvl?userid=<%=userId%>&display=<%=RsTs.getString("SOPHIEU")%>"><img src="../images/Display20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>
												 	
												 	<%} %>
												</td>
												<td>&nbsp;&nbsp;</td>
												<td>
												 
												</td>
												
												</tr>
											</table>				
										</tr>
									
									<% 	m++;
									}%>									
									
										<tr>
											<td height="26" colspan="11" align="center" class="tbfooter">&nbsp;	</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>

					</fieldset>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
<%
try{
	 ltsList.DbClose();
	session.setAttribute("obj",null);
}catch(Exception er){
	
}
}%>
</body>
</html>

