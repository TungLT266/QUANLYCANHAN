<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.erp.beans.loaitaisan.IErp_loaitaisanList" %>
<%@ page  import = "geso.traphaco.erp.beans.loaitaisan.imp.Erp_loaitaisanList" %>

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
 		IErp_loaitaisanList ltsList = (Erp_loaitaisanList)session.getAttribute("ltsList");
 		ResultSet RsTs = ltsList.getRsts();
 		ResultSet RsTk = ltsList.getRsTk();
 		 int[] quyen = new  int[5];
		 quyen = util.Getquyen("Erp_LoaiTaiSanSvl","",userId);

 	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>Best - Project</title>
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
	document.forms['cnForm'].action.value= 'new';
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
<form name="cnForm" method="post" action="../../Erp_LoaiTaiSanSvl">
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
						   <td align="left" colspan="2" class="tbnavigation">Quản lý tài sản > Loại tài sản					   
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
								<tr>
								  	<td class="plainlabel" width="15%">Mã loại tài sản</td>
								    <TD class="plainlabel"><input type="text" name="mats" value="<%=ltsList.getMa() %>" onchange="submitform()"></td>
						      	</tr>
						      	
						      	<tr>
								  	<td class="plainlabel" width="15%">Diễn giải</td>
								    <TD class="plainlabel"><input type="text" name="diengiai" value="<%= ltsList.getTen() %>" onchange="submitform()"></td>
						      	</tr>
																
						      	<tr>
						      		<td class = "plainlabel" width="15%">Thuộc tài khoản kế toán</td>
									<td class = "plainlabel">
										<select name = "tk" style= "width:20% " onchange = "submitform()">
										<option  value=""> </option>
										
										<%
										if(RsTk != null)
										{
										while(RsTk.next())
										{
										if(ltsList.getTkId().equals(RsTk.getString("pk_seq"))){
										%>
											<option selected="selected" value="<%= RsTk.getString("pk_seq") %>"><%= RsTk.getString("SOHIEUTAIKHOAN") %> - <%= RsTk.getString("tentaikhoan") %></option>
										<%
										}else
										{
											%>
											<option value="<%= RsTk.getString("pk_seq") %>"><%= RsTk.getString("SOHIEUTAIKHOAN") %> - <%= RsTk.getString("tentaikhoan") %></option>
											<%
										}
										
										}}
										%>		
										</select>
									</td>
								</tr>
																

							    <tr>
									<td colspan="2" class="plainlabel">
									<a class="button2" href="javascript:xoaform()">
									<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
									</td>									
								</tr>
								
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
						<legend class="legendtitle">&nbsp;Loại tài sản &nbsp;&nbsp;&nbsp;
						
						
<%if(quyen[0]!=0){ %>
						<a class="button3" href="javascript:newform()">
    					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a>
    					<%} %>                            
					 	</legend>
	
		   				<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<tr>
			  	   				<td align="left" colspan="4" class="legendtitle">
									<table class = "tabletitle" width="100%" border="0" cellspacing="1" cellpadding="4">
										<tr class="tbheader">
											<th width="7%">Mã loại tài sản</th>
											<th width="7%">Diễn giải</th>
											<th width="7%">Tài khoản</th>
											<th width="6%">Trạng thái</th>	
											<th width="5%">Ngày tạo</th>
											<th width="5%">Người tạo</th>
											<th width ="5%">Ngày sửa</th>											
											<th width ="5%">Người sửa</th>
														
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
									
										<td align="center"><%= RsTs.getString("ma")%> </td>
										<td align="center"><%= RsTs.getString("diengiai")%> </td>
										<td align="center"><%= RsTs.getString("SOHIEUTAIKHOAN")%> - <%= RsTs.getString("TENTAIKHOAN")%> </td>
										
											<% 
										if(RsTs.getString("trangthai").equals("1")){
										%>	
											<td align="center">Hoạt Động </td>
											<%
										}else{
											%>
											<td align="center">Ngưng Hoạt Động </td>
											<% 
										}
										%>
										
										<td align="center"><%= RsTs.getString("ngaytao") %> </td>
										<td align="center"><%= RsTs.getString("nguoitao")%> </td>
										<td align="center"><%= RsTs.getString("ngaysua")%> </td>
										<td align="center"><%= RsTs.getString("nguoisua")%> </td>										
									
										<td align = "center">
											<table border = 0 cellpadding="0" cellspacing="0">
												<tr>
												<td>
												
													<%if(quyen[2]!=0){ %>
													<a href = "../../Erp_LoaiTaiSanUpdateSvl?userid=<%=userId%>&update=<%=RsTs.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>
													<%} %>
												</td>
												<td>&nbsp;&nbsp;</td>
												<td>
												<%if(quyen[1]!=0){ %>
												<a href = ../../Erp_LoaiTaiSanSvl?userid=<%=userId%>&delete=<%=RsTs.getString("pk_seq")%>><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa loại tài sản này ?')) return false;"></a> 
												<%}	 %>
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
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
<%
try{
	if(RsTk!=null){
		RsTk.close();
	}
	if(RsTs!=null){
		RsTs.close();	
	}
}catch(Exception er){
	er.printStackTrace();	
}finally{
	ltsList.DBClose(); 
	session.setAttribute("ltsList",null); 
}
}%>
</body>
</html>

