<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.dulieutonghop.IDulieutonghop" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% 	IDulieutonghop dlthBean = (IDulieutonghop)session.getAttribute("dlthBean"); 
	String ctyId = dlthBean.getCtyId();
	String dlthId = dlthBean.getDlthId();
	ResultSet dlth = dlthBean.getDlthList();
	
	ResultSet tk = dlthBean.getTaikhoan();
	ResultSet tk_selected = dlthBean.getTaikhoan_Selected();
	ResultSet ctylist = dlthBean.getCtyList();
	String ltkId = dlthBean.getLtkId();
	ResultSet loaitk = dlthBean.getLoaiTK() ;
	int[] quyen = new  int[5];
	quyen = util.Getquyen("Erp_DulieutonghopSvl","&view=TT&chixem=0",userId);
	
%>
<%NumberFormat formatter = new DecimalFormat("#,###,###"); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100;
}

#ajax_listOfOptions div {
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5;
}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
}
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();})
</script>
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
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-spList.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<SCRIPT language="javascript" type="text/javascript">
 function submitForm()
{	
	
	document.dlthForm.action.value='capnhat';
    document.forms["dlthForm"].submit();
}

 function saveform()
{
	 document.dlthForm.action.value='luulai';
	 
     document.forms["dlthForm"].submit();
}


</SCRIPT>


</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dlthForm" method="post" action="../../Erp_DulieutonghopSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value="1">


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Dữ liệu tổng hợp
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
						    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
			</TABLE>

			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1" ><%= dlthBean.getMessage() %></textarea>
						<% dlthBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
					<TD>

						<TABLE width="80%" border="0" cellpadding="0" cellspacing="1" >
							<TR>
								<TD>
									<FIELDSET>
									<LEGEND class="legendtitle" style="color:black">Chọn dữ liệu tổng hợp</LEGEND>
	
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
						  	  				
							  				<TD class="plainlabel" width="120px">Dữ liệu tổng hợp </TD>
						  	  				<TD class="plainlabel">
						  	  					<SELECT name= "dlthId" onChange = "javascript:submitForm();">
						  	  						<OPTION value = "0" >&nbsp;</OPTION>
						  	  						<% 
						  	  						try{
						  	  							while(dlth.next()){
						  	  							if(dlthId.length() > 0){
						  	  								if (dlthId.equals(dlth.getString("DLTHID"))){ %>
							  	  								<OPTION value = <%= dlth.getString("DLTHID")%> selected><%= dlth.getString("DIENGIAI")%> </OPTION>
							  	  							<%}else{ %>
							  	  								<OPTION value = <%= dlth.getString("DLTHID")%> ><%= dlth.getString("DIENGIAI")%></OPTION>
							  	  							<%} 
							  	  						}else{%>						
							  	  							  <OPTION value = <%= dlth.getString("DLTHID")%> ><%= dlth.getString("DIENGIAI")%></OPTION>
							  	  						<%} 
						  	  							}
							  	  					}catch(java.sql.SQLException e){}%>	  					
						  	  					</SELECT>
						  	  				</TD>
										</TR>

										<TR>
						  	  				
							  				<TD class="plainlabel" width="120px">Loại tài khoản </TD>
						  	  				<TD class="plainlabel">
						  	  					<SELECT name= "ltkId" onChange = "javascript:submitForm();">
						  	  						<OPTION value = "0" >&nbsp;</OPTION>
						  	  						<% 
						  	  						try{
						  	  							while(loaitk.next()){
						  	  							if(ltkId.length() > 0){
						  	  								if (ltkId.equals(loaitk.getString("LTKID"))){ %>
							  	  								<OPTION value = <%= loaitk.getString("LTKID")%> selected><%= loaitk.getString("LOAITK")%> </OPTION>
							  	  							<%}else{ %>
							  	  								<OPTION value = <%= loaitk.getString("LTKID")%> ><%= loaitk.getString("LOAITK")%></OPTION>
							  	  							<%} 
							  	  						}else{%>						
							  	  							  <OPTION value = <%= loaitk.getString("LTKID")%> ><%= loaitk.getString("LOAITK")%></OPTION>
							  	  						<%} 
						  	  							}
							  	  					}catch(java.sql.SQLException e){}%>	  					
						  	  					</SELECT>
						  	  				</TD>
										</TR>
									</TABLE>
									</FIELDSET>
																	
									<TABLE width = "100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD>
												<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Chọn tài khoản kế toán</LEGEND>
												<TABLE  border="0" cellpadding="4"  cellspacing="1" width = "100%" >
												
															<TR class="tbheader" >
																<TH width="5%" >Thứ tự </TH>
																<TH width="15%" >Số hiệu tài khoản </TH>															
																<TH width="45%" >Tên tài khoản </TH>
																<TH width="15%" >Chọn</TH>
															</TR>
												<%	
												int i = 1;
												if(tk_selected != null){
													try{
														
//														dbutils db = new dbutils();
														while(tk_selected.next()){
															if(i%2 == 0){						
																					%>
															<TR class= 'tblightrow'>
														<%	}else{ %>
															<TR class= 'tbdarkrow'>
														<%  } %>	
											  					<TD align="center" >
																	<%= i++ %>	
																</TD>
															  	
											  					<TD align="center" >
																	<%= tk_selected.getString("SOHIEUTAIKHOAN") %>																								
																</TD>
																
													
											  					<TD  align="left" >
											  						<%= tk_selected.getString("TENTAIKHOAN") %>
																</TD>
																														
																<TD align="center" >
																	<INPUT TYPE = "checkbox" NAME = "tkIds" VALUE = "<%= tk_selected.getString("TKID")%>" checked>
																</TD>
															</TR>
												<%		
														
														}
														//db.shutDown(); 		
														}catch(java.sql.SQLException e){}
														  	
												 }%>
	
												<%	
												if(tk != null){
													try{
														while(tk.next()){
															if(i%2 == 0){						
																					%>
															<TR class= 'tblightrow'>
														<%	}else{ %>
															<TR class= 'tbdarkrow'>
														<%  } %>	
											  					<TD align="center" >
																	<%= i++ %>	
																</TD>
															  	
											  					<TD align="center" >
																	<%= tk.getString("SOHIEUTAIKHOAN") %>																								
																</TD>
																
													
											  					<TD  align="left" >
											  						<%= tk.getString("TENTAIKHOAN") %>
																</TD>
																
																<TD align="center" >
																	<INPUT TYPE = "checkbox" NAME = "tkIds" VALUE = "<%= tk.getString("TKID")%>">
																</TD>
															</TR>
												<%		
														
														}
														//db.shutDown(); 		
														}catch(java.sql.SQLException e){}
														  	
												 }%>
	
												</TABLE>
												</FIELDSET>
											</TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
						</TABLE>
						
					</TD>
	      		</TR>
		  	</TABLE>
		</TD>
	
</TABLE>
</form>
</BODY>


</HTML>

<% 
	if(tk != null) tk.close();
	if(loaitk != null) loaitk.close();
	if(ctylist != null) ctylist.close();
	if(dlthBean!=null){ dlthBean.DBClose(); dlthBean=null;}
	session.setAttribute("csBean", null);
}%>