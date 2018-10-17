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
	ResultSet ngansachcopy = (ResultSet)nsList.getNgansachCopy(); 
	
	int[] quyen = new  int[5];
	quyen = util.Getquyen("KhoitaongansachSvl","",userId);


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>Khởi tạo ngân sách</title>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>    
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>

<script language="javascript" type="text/javascript">

function xoaform()
{
	//document.nsForm.ctyId.selectedIndex = 0;
    document.nsForm.diengiai.value = "";
    submitform();
}

function submitform()
{
	document.forms['nsForm'].action.value= 'search';
	document.forms['nsForm'].submit();
}

function copyform()
{
	document.forms['nsForm'].action.value= 'copy';
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
<form name="nsForm" method="post" action="../../KhoitaongansachSvl">
<input type="hidden" name="userId" value='<%=userId%>'> 
<input type="hidden" name="userTen" value='<%=userTen%>'> 
<input type="hidden" name="action" value='1'>

<input type="hidden" name="msg" value='<%= nsList.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<table width="100%" border="0" cellspacing="1" cellpadding="1"
	height="100%">
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<table width="100%" cellpadding="0" cellspacing="1">		
				<tr>
					<td align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <td align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý ngân sách &gt; Khởi tạo ngân sách
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
							<TD width="19%" class="plainlabel">Diễn giải </TD>
					  		<TD class="plainlabel" >
							<INPUT TYPE = "text" NAME = "diengiai" VALUE = "<%= nsList.getDiengiai() %>"  onChange = "submitform();" >
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
						<legend class="legendtitle">&nbsp;Khởi tạo ngân sách&nbsp;&nbsp;&nbsp;
						<%if(quyen[0]!=0){ %>
						<a class="button3" href="javascript:newform()">
    					<img style="top: -4px;" src="../images/add.png" alt="">Tạo mới</a>
    					<%} %>                            
    					
                        &nbsp;&nbsp;&nbsp;&nbsp;
    					<%if(quyen[0]!=0){ %>
					 	
						<a class="button3" href="" id="copyId"  rel= "copy">
	           	 			&nbsp; <img style="top: -4px;" src="../images/New.png" alt="">Sao chép</a> </a>
	           	 		
                          	<DIV id="copy" style="position:absolute; visibility: hidden; border: 2px solid #80CB9B;
				                 background-color: white; width: 300px; height:60px; overflow:none; padding: 2px;">
				                 <TABLE width="100%" align="center">
				                     <TR class="tbheader">
										<TD>
											<SELECT NAME  = "copyId" style="width:295px;">
												<OPTION VALUE = "">&nbsp</OPTION>
											<%if(ngansachcopy != null) {
												while(ngansachcopy.next()){
											
											%>		
													<OPTION VALUE = "<%= ngansachcopy.getString("LNSID") %>" > <%=  ngansachcopy.getString("DIENGIAI") %></OPTION>
											
											<%	}
												ngansachcopy.close();
											} %>
											</SELECT>
										</TD>	
									 </TR>
			                     </TABLE>
				                    					
				                 <div align="left" class="legendtitle">
				                 	<label  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				                     								
				                 	<a href="javascript:dropdowncontent.hidediv('copy')" ></a>
				                 </div>
				                 
				                <div align="right">
									<label style="color: red"></label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
									href="javascript:copyform()">Thực hiện</a>
								</div>
				                 
				                 <script type="text/javascript">
										dropdowncontent.init('copyId', "right-bottom", 300, "click");
								 </script>
				            </DIV>                        
						<%} %>
    					
					 	</legend>
	
		   				<table width="100%" border="0" cellpadding="0" cellspacing="2">
			  				<tr>
			  	   				<td align="left" colspan="4" class="legendtitle">
									<table width="100%" border="0" cellspacing="2" cellpadding="4">
										<tr class="tbheader">
											<th width="10%">Năm</th>
											<th width="25%">Diễn giải</th>
											<th width="10%">Hiệu lực</th>
											<th width="15%">Người tạo</th>
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
									<%if(!ns.getString("HIEULUC").equals("0")){ %>	
										<td align="center">Có</td>
									<%}else{ %>
										<td align="center">Không</td>
									<%} %>
										<td align="center"><%= ns.getString("nguoitao")%> </td>
										<td align="center"><%= ns.getString("ngaytao") %> </td>
										
											
										<td align = "center">
											<%if(quyen[2]!=0){ %>
											<a href = "../../KhoitaongansachSvl?userid=<%=userId %>&update=<%=ns.getString("NSID")%>"><img src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>
											<%} %>
											<%if(quyen[1]!=0){ %>
											<a href = "../../KhoitaongansachSvl?userid=<%=userId %>&delete=<%=ns.getString("NSID")%>" ><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa kế hoạch ngân sách hiện này?')) return false;"></a>
											<%} %>
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
