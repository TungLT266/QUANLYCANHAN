<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.sohoadon.ISohoadon" %>
<%@ page  import = "geso.traphaco.erp.beans.sohoadon.imp.Sohoadon" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% 	ISohoadon shdBean = (ISohoadon)session.getAttribute("shdBean"); 
	ResultSet kholist = shdBean.getKhoList();
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["shdForm"].submit();
}

function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
{    
	var keypressed = null;
	if (window.event)
		keypressed = window.event.keyCode; //IE
	else
		keypressed = e.which; //NON-IE, Standard
	
	if (keypressed < 48 || keypressed > 57)
	{ 
		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
		{//Phím Delete và Phím Back
			return;
		}
		return false;}
		
}	

function saveform()
{
	var tuso = document.forms["shdForm"]["tuso"].value;
    if (tuso == null || tuso == "") {
        alert("Vui lòng nhập Từ số");
        return;
    }
    
	var denso = document.forms["shdForm"]["denso"].value;
    if (denso == null || denso == "") {
        alert("Vui lòng nhập Đến số");
        return;
    }
    
	document.forms["shdForm"].action.value = "save";
	document.forms["shdForm"].submit();
}

</SCRIPT>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2();  });
	     
	</script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="shdForm" method="post" action="../../Erp_SohoadonUpdateSvl" >
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="Id" value='<%= shdBean.getId()  %>'>
<input type="hidden" name="action" value="new">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Số hóa đơn &gt; Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../Erp_SohoadonSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1"><%= shdBean.getMsg() %></textarea>
						<% shdBean.setMsg("") ; %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Số hóa đơn</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

							<TR>
								<TD width="29%" class="plainlabel" >Từ số<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"><INPUT type="text" name="tuso" style="width:300px" value='<%= shdBean.getTuso()%>' onkeypress="return keypress(event);"></TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Đến số<FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="denso" style="width:300px" value='<%= shdBean.getDenso() %>' onkeypress="return keypress(event);"></TD>
						  </TR>
						  <TR>
							  	<TD class="plainlabel">Ký hiệu<FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="kyhieu" style="width:300px" value='<%= shdBean.getKyhieu() %>' ></TD>
						  </TR>

						  <TR>
						   		<TD class="plainlabel">Kho vùng</TD>
						  	  	<TD class="plainlabel">
						  			<SELECT  name="khoId" style=" width: 250px" >
						  					<option value=""  ></option>
						  				<%
						  					if(shdBean.getCtyId().length()>0 & kholist != null){
											try{%>
												
											<% 	while (kholist.next()){%>
													
													<%	if(kholist.getString("KHOID").equals(shdBean.getKhoId())){ %>											
															
															<option value= <%= kholist.getString("KHOID")%> selected><%= kholist.getString("TEN") %></option>															
														<%}else{%>
															<option value= <%= kholist.getString("KHOID")%> ><%= kholist.getString("TEN") %></option>																																		
														<%	}
												}
															
											}catch(java.sql.SQLException e){ }
						  					}
											%>													
																											                                           
                           			</SELECT>
						  	  	</TD>
						  	</TR>
						  
						  <TR>
						   		<TD class="plainlabel">Loại</TD>
								<TD class="plainlabel" >
									<TABLE cellpadding="0" cellspacing="0" border="0">
										<TR>
									  		<TD><SELECT  name="loai"  style=" width: 250px">												
														<option value=""  ></option>												
											<% 	
												if(shdBean.getLoai().equals("1")){ %>											
														<option value= "1" selected>Hóa đơn</option>															
													  
														<option value= "2" >Xuất kho</option>																																		
											  <%}else{ %>
														<option value= "1" >Hóa đơn</option>															
													  
														<option value= "2" selected >Xuất kho</option>																																		
													
											  <%} 
												
													%>														                                           
                                            	</SELECT>
                                         	 </TD>
											</TR>
										</TABLE>									
									</TD>
						  	  	</TD>
						  </TR>
						 
						<TR>
							<TD class="plainlabel"><input name="trangthai" type="checkbox" value="1" checked>
							      Hoạt động </TD>
							<TD class="plainlabel">&nbsp;</TD>
						</TR>
						 
						</TABLE>
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>
</BODY>
</HTML>
<%  if(kholist != null) kholist.close();

	shdBean.DBClose();
	session.setAttribute("shdBean", null) ; 
	
}%>