<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.doanhthu.IMucdoanhthu" %>
<%@ page  import = "geso.traphaco.erp.beans.doanhthu.imp.Mucdoanhthu" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% 	IMucdoanhthu DtBean = (IMucdoanhthu)session.getAttribute("DtBean");
	ResultSet dvttlist = DtBean.getDvttList();
   	ResultSet tklist = DtBean.getTkList();
   	ResultSet Ttdtlist = DtBean.getTtdtList();	

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

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

<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["DtForm"].submit();
}

function saveform()
{
	document.forms["DtForm"].action.value = "save";
    document.forms["DtForm"].submit();
}

</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="DtForm" method="post" action="../../Erp_MucdoanhthuUpdateSvl" >
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="DtId" value='<%= DtBean.getId()  %>'>

<input type="hidden" name="action" value="0">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Khoản mục doanh thu &gt; Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../Erp_MucdoanhthuSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
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
				
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1"><%=DtBean.getMsg() %></textarea>
						<% DtBean.setMsg("") ; %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin khoản mục doanh thu</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

							<TR>
								<TD width="29%" class="plainlabel" >Tên khoản mục doanh thu<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"><INPUT type="text" name="ten" style="width:300px" value='<%= DtBean.getTen()%>'></TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="diengiai" style="width:300px" value='<%= DtBean.getDiengiai() %>'></TD>
						  </TR>
						  	<TR>
						   		<TD class="plainlabel">Đơn vị / Phòng ban thực hiện</TD>
						  	  	<TD class="plainlabel">
						  			<SELECT  name="dvttId" style="width:200px" class="select2" >
						  					<option value=""  ></option>
						  				<%
						  					if(DtBean.getCtyId().length()>0 & dvttlist != null){
											try{%>
												
											<% 	while (dvttlist.next()){%>
													
													<%	if(dvttlist.getString("DVTTID").equals(DtBean.getDvttId())){ %>											
															
															<option value= "<%=dvttlist.getString("DVTTID")%>" selected><%= dvttlist.getString("DVTTTEN") %></option>															
														<%}else{%>
															<option value= "<%=dvttlist.getString("DVTTID")%>" ><%= dvttlist.getString("DVTTTEN") %></option>																																		
														<%	}
												}
															
											}catch(java.sql.SQLException e){ }
						  					}
											%>													
																											                                           
                           			</SELECT>
						  	  	</TD>
					  		</TR>
						  
						  	<TR>
						   		<TD class="plainlabel">Tài khoản kế toán</TD>
								<TD class="plainlabel" >
									<TABLE cellpadding="0" cellspacing="0" border="0">
										<TR>
									  		<TD><SELECT  name="tkId" style="width:200px" class="select2">												
														<option value=""  ></option>												
											<% 	if(tklist != null){
												try{
													while (tklist.next()){%>
																
													<%	if(tklist.getString("MA").equals(DtBean.getTkId())){ %>											
														<option value= "<%=tklist.getString("MA")%>" selected><%= tklist.getString("MA") + " - " + tklist.getString("TEN") %></option>															
													  <%}else{%>
														<option value= "<%=tklist.getString("MA")%>" ><%= tklist.getString("MA")  + " - " +  tklist.getString("TEN") %></option>																																		
													  <%}
													}
															
													}catch(java.sql.SQLException e){
													}				
												}
													%>														                                           
                                            	</SELECT>
                                         	 </TD>
											</TR>
										</TABLE>									
									</TD>
						  	  	</TD>
						  </TR>
						  <TR>
						   		<TD class="plainlabel">Trung tâm doanh thu</TD>
								<TD class="plainlabel" >
									<TABLE cellpadding="0" cellspacing="0" border="0">
										<TR>
									  		<TD><SELECT  name="TtdtId" style="width:200px" class="select2">												
														<option value=""  ></option>												
											<% 	try{
													while (Ttdtlist.next()){%>
																
													<%	if(Ttdtlist.getString("TTDTID").equals(DtBean.getTtdtId())){ %>											
														<option value= "<%=Ttdtlist.getString("TTDTID")%>" selected><%= Ttdtlist.getString("MA") + " - " + Ttdtlist.getString("DIENGIAI") %></option>															
													  <%}else{%>
														<option value= "<%=Ttdtlist.getString("TtdtID")%>" ><%= Ttdtlist.getString("MA")  + " - " +  Ttdtlist.getString("DIENGIAI") %></option>																																		
													  <%}
													}
															
													}catch(java.sql.SQLException e){
													}														
													%>														                                           
                                            	</SELECT>
                                         	 </TD>
											</TR>
										</TABLE>									
									</TD>
						  	  	</TD>
						  </TR>
						  
						 
						<TR>
							<% if(DtBean.getTrangthai().equals("1")) {%>						
						  		<TD class="plainlabel"><input name="trangthai" type="checkbox" value="1" checked>
						  	<%}else{ %>
						  		<TD class="plainlabel"><input name="trangthai" type="checkbox" value="0" >
						  	<%} %>
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
<% 	if(dvttlist != null) dvttlist.close();
	if(tklist != null) tklist.close(); 
	if(Ttdtlist != null) Ttdtlist.close();
	DtBean.DBClose();
}%>