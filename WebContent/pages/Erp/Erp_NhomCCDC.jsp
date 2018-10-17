<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_nhomtaisan.IErp_nhomCCDCList" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_nhomtaisan.imp.Erp_nhomCCDCList" %>

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
 		IErp_nhomCCDCList ntsList = (Erp_nhomCCDCList)session.getAttribute("ntsList");
 		ResultSet RsTs = ntsList.getRsts();
		ResultSet ltsRs = ntsList.getRslts();
		ResultSet cdtsRs = ntsList.getRsCdts();
		
 		int[] quyen = new  int[5];
		quyen = util.Getquyen("Erp_NhomCCDCSvl","",userId);

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
    document.cnForm.ltsId.value = "";
    document.cnForm.cdtsId.value = "";
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
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="cnForm" method="post" action="../../Erp_NhomCCDCSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%= ntsList.getMsg() %>'>
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
						   <td align="left" colspan="2" class="tbnavigation">&nbsp; Quản lý công cụ dụng cụ &gt; Nhóm chi phí trả trước					   
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
								  	<td class="plainlabel" width="15%">Mã </td>
								    <TD class="plainlabel"><input type="text" name="mats" value="<%=ntsList.getMa() %>" onchange="submitform()"></td>
						      	</tr>
						      	
						      	<tr>
								  	<td class="plainlabel" width="15%">Diễn giải</td>
								    <TD class="plainlabel"><input type="text" name="diengiai" value="<%= ntsList.getTen() %>" onchange="submitform()"></td>
						      	</tr>
																																
								<tr>
									<td width = "15%" class = "plainlabel">Loại CCDC </td> 
									<td class = "plainlabel"> 
										<SELECT Id = "ltsId"  NAME = "ltsId" onChange = "submitform();">
											<OPTION value = "" >&nbsp; </OPTION>
									<% while(ltsRs.next()){ 
											if(ltsRs.getString("ltsId").equals(ntsList.getLtsId())){
									
									%>
											<OPTION value = "<%= ltsRs.getString("ltsId") %>" selected><%=  ltsRs.getString("diengiai")%> </OPTION>
									
									<%		}else{ %>
											
											<OPTION value = "<%= ltsRs.getString("ltsId") %>" ><%=  ltsRs.getString("diengiai")%> </OPTION>										
									<%		} 
									
									   }%>
										</SELECT> 
									
									</td>
								</tr>

								<tr style="display: none">
									<td width = "15%" class = "plainlabel">Công dụng công cụ dụng cụ </td> 
									<td class = "plainlabel"> 
										<SELECT Id = "cdtsId"  NAME = "cdtsId" onChange = "submitform();">
											<OPTION value = "" >&nbsp; </OPTION>
									<% while(cdtsRs.next()){ 
											if(cdtsRs.getString("cdtsId").equals(ntsList.getCdtsId())){
									
									%>
											<OPTION value = "<%= cdtsRs.getString("cdtsId") %>" selected><%=  cdtsRs.getString("diengiai")%> </OPTION>
									
									<%		}else{ %>
											
											<OPTION value = "<%= cdtsRs.getString("cdtsId") %>" ><%=  cdtsRs.getString("diengiai")%> </OPTION>										
									<%		} 
									
									   }%>
										</SELECT> 
									
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
						<legend class="legendtitle">&nbsp;Nhóm chi phí trả trước &nbsp;&nbsp;&nbsp;
						
						
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
											<th width="15%">Mã nhóm </th>
											<th width="25%">Diễn giải</th>
											<th width="10%">Trạng thái</th>	
											<th width="20%">Loại </th>
											<th width ="10%">Ngày sửa</th>											
											<th width ="10%">Người sửa</th>
														
											<th width="10%" align="center">&nbsp;Tác vụ</th>					
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
									
										<td align="left"><%= RsTs.getString("ma")%> </td>
										<td align="left"><%= RsTs.getString("diengiai")%> </td>
										
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
										
										<td align="left"><%= RsTs.getString("loaitaisan") %> </td>
										<td align="center"><%= RsTs.getString("ngaysua")%> </td>
										<td align="center"><%= RsTs.getString("nguoisua")%> </td>										
									
										<td align = "center">
											<table border = 0 cellpadding="0" cellspacing="0">
												<tr>
												<td>
												
													<%if(quyen[2]!=0){ %>
													<a href = "../../Erp_NhomCCDCUpdateSvl?userid=<%=userId%>&update=<%=RsTs.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>
													<%} %>
												</td>
												<td>&nbsp;&nbsp;</td>
												<td>
												<%if(quyen[1]!=0){ %>
												<a href = "../../Erp_NhomCCDCSvl?userid=<%=userId%>&delete=<%=RsTs.getString("pk_seq")%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa nhóm tài sản này ?')) return false;"></a> 
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
<%
try{
	if (ltsRs != null)
		ltsRs.close();
	if (cdtsRs != null)
		cdtsRs.close();
	if(RsTs!=null){
		RsTs.close();	
	}
	ntsList.DBClose(); 
	session.setAttribute("ntsList",null);
}catch(Exception er){
	er.printStackTrace();	
}
}%>
</body>
</html>

