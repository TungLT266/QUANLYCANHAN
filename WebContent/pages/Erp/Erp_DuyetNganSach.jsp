<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.ILapngansachList" %>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.imp.LapngansachList" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%  ILapngansachList nsList = (ILapngansachList)session.getAttribute("nsList");
	ResultSet ns = (ResultSet)nsList.getNgansach();
	ResultSet namList = (ResultSet) nsList.getNamlist(); 
	
	int[] quyen = new  int[5];
	quyen = util.Getquyen("DuyetngansachSvl","",userId);


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>Khởi tạo ngân sách</title>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>

<script language="javascript" type="text/javascript">

function xoaform()
{
	document.nsForm.nam.selectedIndex = 0;
    document.nsForm.diengiai.value = "";
    submitform();
}

function submitform()
{
	document.forms['nsForm'].action.value= 'search';
	document.forms['nsForm'].submit();
}

function newform()
{
	document.forms['nsForm'].action.value= 'new';
	document.forms['nsForm'].submit();
}
function thongbao()
{
	 var tt = document.forms["nsForm"].msg.value;
	 if(tt.length>0)
    	alert(document.forms["nsForm"].msg.value);
	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nsForm" method="post" action="../../DuyetngansachSvl">
<input type="hidden" name="userId" value='<%=userId%>'> 
<input type="hidden" name="userTen" value='<%=userTen%>'> 
<input type="hidden" name="action" value='1'>

<input type="hidden" name="msg" value='<%= nsList.getMsg() %>'>
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
						   <td align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý ngân sách &gt; Duyệt ngân sách
						   </td>
   					
						   <td align = "right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;</td>
						  </tr>
 					  </table>
					</td>
				</tr>
				<tr>
					<td>
					<fieldset>
					  <legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>

					<table border="0" width="100%" cellpadding="6" cellspacing="0">
	                 	<TR>
                        	<TD class="plainlabel" valign="middle" >Ngân sách cho năm</TD>
                        	<TD class="plainlabel" valign="middle" >
                            	<SELECT id="nam"  name="nam" onChange="submitform();">
                            		<OPTION value=""></option>
					<% 
						try
						{
							if(namList != null){
								while(namList.next()){
					  				if (nsList.getNam().equals(namList.getString("NAM"))){ %>
									<OPTION value = <%= namList.getString("NAM")%> selected><%= namList.getString("NAM")%> </OPTION>
							<%  	}else{ %>
							  	  	<OPTION value = <%= namList.getString("NAM")%> ><%= namList.getString("NAM")%></OPTION>
						  	 <% 	} 
								}
						  	}
						}catch(java.sql.SQLException e){}%>	  					
                            	
                            	</SELECT>
                            </TD>

                 		</TR>

						<TR>
							<TD width="19%" class="plainlabel">Diễn giải </TD>
					  		<TD class="plainlabel" >
							<INPUT TYPE = "text" NAME = "diengiai" VALUE = "<%= nsList.getDiengiai() %>" >
                          	</TD>
						</TR>

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

			<tr>
				<td width="100%">
					<fieldset>
						<legend class="legendtitle">&nbsp;Ngân sách đã khởi tạo &nbsp;&nbsp;&nbsp;
						<%if(quyen[0]!=0){ %>
						<a class="button3" href="javascript:newform()">
    					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a>
    					<%} %>                            
					 	</legend>
	
		   				<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<tr>
			  	   				<td align="left" colspan="4" class="legendtitle">
									<table width="100%" border="0" cellspacing="1" cellpadding="4">
										<tr class="tbheader">
											<th width="10%">Năm</th>
											<th width="25%">Diễn giải</th>
											<th width="10%">Hiệu lực</th>
											<th width="10%">Trạng thái</th>
											<th width="10%">Ngày tạo</th>			
											<th width="10%" align="center">&nbsp;Tác vụ</th>					
										</tr>
				
									<%
							int m = 0;
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
									
							if(ns!= null){
								
								try{
									while (ns.next())
									{
										if(m % 2 != 0) {%>
										<tr class = <%=lightrow%> >
									<%  } else {%>
										<tr class = <%=darkrow%> >
									<%  }%>
									
										<td align="center"><%= ns.getString("NAM")%> </td>
										<td align="left"><%= ns.getString("DIENGIAI")%> </td>				
									<%if(ns.getString("HIEULUC").equals("0")){ %>	
										<td align="center">Không</td>
									<%}else{ %>
										<td align="center">Có</td>
									<%} %>

									<%if(ns.getString("TRANGTHAI").equals("0")){ %>	
										<td align="center">Chưa duyệt</td>
									<%}else{ %>
										<td align="center">Đã duyệt</td>
									<%} %>

										<td align="center"><%= ns.getString("ngaytao") %> </td>
										
											
										<td align = "center">
											
											<a href = "../../DuyetngansachSvl?userid=<%=userId %>&capnhat=<%=ns.getString("PK_SEQ")%>"><img src="../images/Edit20.png" alt="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0></A>
	
										<% 	if(ns.getString("TRANGTHAI").equals("0")){ %>
											
											<a href = "../../DuyetngansachSvl?userid=<%=userId %>&duyet=<%=ns.getString("PK_SEQ")%>" ><img src="../images/Chot.png" alt="Duyệt" width="20" height="20" longdesc="Duyệt" border=0 "></a>
										
										<%	}%>
										
										</td>
									
									<% 	m++;
									}
								}catch(java.sql.SQLException e){}
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

</form>
</body>
</html>

<%
	
	if(ns != null)	ns.close();
	session.setAttribute("nsList",null);
	nsList.DBClose();	

}%>
