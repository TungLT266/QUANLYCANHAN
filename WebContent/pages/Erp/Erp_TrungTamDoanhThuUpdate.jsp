<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamdoanhthu" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamdoanhthuList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% IErp_trungtamdoanhthu TTDTBean = (IErp_trungtamdoanhthu)session.getAttribute("TTDTBean"); 
  
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/ajax_erp_loadspvattu.js"></script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
	#dhtmltooltip
	{
		position: absolute;
		left: -300px;
		width: 150px;
		border: 1px solid black;
		padding: 2px;
		background-color: lightyellow;
		visibility: hidden;
		z-index: 100;
		/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
		filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
	}	
	#dhtmlpointer
	{
		position:absolute;
		left: -300px;
		z-index: 101;
		visibility: hidden;
	}
	
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script>
//perform JavaScript after the document is scriptable.
$(function() {
 // setup ul.tabs to work as tabs for each div directly under div.panes
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["TTDTForm"].submit();
}

function saveform()
{
	document.forms["TTDTForm"].action.value = "save";
    document.forms["TTDTForm"].submit();
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="TTDTForm" method="post" action="../../Erp_TrungTamDoanhThuUpdateSvl" >
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="TTDTId" value='<%= TTDTBean.getId()  %>'>
<input type="hidden" name="ctyId" value='<%= TTDTBean.getCtyId()  %>'>
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Trung tâm doanh thu &gt; Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../Erp_TrungTamDoanhThuSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
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
				
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1"><%=TTDTBean.getMsg() %></textarea>
						<% TTDTBean.setMsg("") ; %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Trung tâm doanh thu</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  		
							<TR>
								<TD width="29%" class="plainlabel" >Mã<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"><INPUT type="text" name="ma" style="width:300px" value='<%= TTDTBean.getMa()%>'></TD>
							</TR>
							
							<TR>
							  	<TD class="plainlabel">Diễn giải <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="diengiai" style="width:300px" value='<%= TTDTBean.getDiengiai() %>'></TD>
						  	</TR>


							<TR>
							  	<TD class="plainlabel">Phân bổ</TD>
							  
							  <% if(TTDTBean.getPhanbo().equals("1")){%>
						  	  	<TD class="plainlabel"><INPUT type="checkbox" name="pb" value = "1" checked onChange = "submitform();"></TD>
						  	  <%}else{ %>
						  	  	<TD class="plainlabel"><INPUT type="checkbox" name="pb" value = "1" onChange = "submitform();"></TD>
						  	  <%} %>
						  	  
							</TR>							

							<TR>
							  	<TD class="plainlabel">Nhận phân bổ</TD>
							  
							  <% if(TTDTBean.getNhanphanbo().equals("1")){%>
						  	  	<TD class="plainlabel"><INPUT type="checkbox" name="pb" value = "1" checked disabled = "disabled"></TD>
						  	  <%}else{ %>
						  	  	<TD class="plainlabel"><INPUT type="checkbox" name="pb" value = "1" disabled = "disabled"></TD>
						  	  <%} %>
						  	  
							</TR>							

							<TR>
							<% if(TTDTBean.getTrangthai().equals("1")) {%>						
						  		<TD class="plainlabel"><input name="trangthai" type="checkbox" value="1" checked>
						  	<%}else{ %>
						  		<TD class="plainlabel"><input name="trangthai" type="checkbox" value="0" >
						  	<%} %>
							      Hoạt động </TD>
							<TD class="plainlabel">&nbsp;</TD>
							</TR>
						</TABLE>
					</FIELDSET>	
				<% if(TTDTBean.getPhanbo().equals("1") || (TTDTBean.getNhanphanbo().equals("1"))){ %>						
						<TABLE border="0" width="100%" cellpadding="2" cellspacing="0">								 
							<TR>
								<TD colspan="6">
															
									<ul class="tabs">
									
						 
							<% if(TTDTBean.getPhanbo().equals("1")){ %>
																
										<li><a href="#" class= "legendtitle"> Phân bổ</a></li>
																
							<% } %>
							
							<% if(TTDTBean.getNhanphanbo().equals("1")){ %>
																
										<li><a href="#" class= "legendtitle">Nhận phân bổ</a></li>
																
							<% } %>
							
									</ul>
									
									<div class="panes">


							<% if(TTDTBean.getPhanbo().equals("1")){ %>
															
										<div>
											<TABLE style="width: 95%" cellpadding="4px" cellspacing="1px" align="center">
												<TR><TD class= "legendtitle" >Chọn Trung Tâm Doanh Thu để phân bổ</TD></TR>
											</TABLE>
										
											<TABLE style="width: 95%" cellpadding="4px" cellspacing="1px" align="center">
												<TR class="tbheader">
													<TH width="20%">Mã</TH>
													<TH width="50%">Diễn giải</TH>
													<TH width="10%">Phần trăm (%) </TH>
													<TH width="10%">Chọn</TH>
												</TR>
										<%
										//String[] pbIds = TTDTBean.getPbIds();
										String pbIdList = TTDTBean.getPbIdsList();
										
										ResultSet TTDTList = TTDTBean.getChophanbo(TTDTBean.getId());
										int i = 0;
													
										if(TTDTList != null)
										{
											try
											{
				                                String lightrow = "tblightrow";
				                                String darkrow = "tbdarkrow";
												
												while(TTDTList.next())
												{ 
																
												  if (i % 2 != 0) { %>
												                       
	                                              <TR class= <%=lightrow%> >
	                                              
	                                     <%		  } else {%>
	                                              
	                                              <TR class= <%= darkrow%> >
	                                               
	                                     <%		  }%>
													<TD>
														<%= TTDTList.getString("MA_NPB") %>
													</TD>
													
													<TD>
														<%= TTDTList.getString("DIENGIAI_NPB") %>
													</TD>
													
													<TD align="center">
												<%		if(pbIdList.indexOf(TTDTList.getString("TTDTID_NPB")) >= 0){
															String tmp = pbIdList.substring(pbIdList.indexOf(TTDTList.getString("TTDTID_NPB")), pbIdList.indexOf(";", pbIdList.indexOf(TTDTList.getString("TTDTID_NPB"))));
															String[] pt = tmp.split("-");
												%>
														<INPUT type="text" name = "<%= "pt" + TTDTList.getString("TTDTID_NPB") %>" style="width: 100%" value="<%= pt[1] %>" >
														
												<%		}else{ %>
												
														<INPUT type="text" name = "<%= "pt" + TTDTList.getString("TTDTID_NPB") %>" style="width: 100%" value="<%= TTDTList.getString("PHANTRAM_NPB") %>" >
														
												<%		} %>
												
													</TD >

													<TD align="center">
													
														<input type="checkbox" name="pbIds" value="<%= TTDTList.getString("TTDTID_NPB") %>" checked="checked" >
												
													</TD>
												</TR>	
																				
										<% 		
												i++;
												} 
												TTDTList.close();
											} catch(Exception e ){}
										}
														%>

										<%
										TTDTList = TTDTBean.getChophanbothem(TTDTBean.getId());
													
										if(TTDTList != null)
										{
											try
											{
				                                String lightrow = "tblightrow";
				                                String darkrow = "tbdarkrow";
												
												while(TTDTList.next())
												{ 
																
												  if (i % 2 != 0) { %>
												                       
	                                              <TR class= <%=lightrow%> >
	                                              
	                                     <%		  } else {%>
	                                              
	                                              <TR class= <%= darkrow%> >
	                                               
	                                     <%		  }%>
													<TD>
														<%= TTDTList.getString("MA") %>
													</TD>
													
													<TD>
														<%= TTDTList.getString("DIENGIAI") %>
													</TD>
													
													<TD align="center">
													
												<%		if(pbIdList.indexOf(TTDTList.getString("TTDTID")) >= 0){
															String tmp = pbIdList.substring(pbIdList.indexOf(TTDTList.getString("TTDTID")), pbIdList.indexOf(";", pbIdList.indexOf(TTDTList.getString("TTDTID"))));
															String[] pt = tmp.split("-");
												%>
														<INPUT type="text" name = "<%= "pt" + TTDTList.getString("TTDTID") %>" style="width: 100%" value="<%= pt[1] %>" >
														
												<%		}else{ %>
												
														<INPUT type="text" name = "<%= "pt" + TTDTList.getString("TTDTID") %>" style="width: 100%" value="" >
														
												<%		} %>
													

													</TD >

													<TD align="center">
												<% if(pbIdList.indexOf(TTDTList.getString("TTDTID")) >= 0){ %>
														<input type="checkbox" name="pbIds" value="<%= TTDTList.getString("TTDTID") %>" checked="checked" >
												<% }else{ %>
														<input type="checkbox" name="pbIds" value="<%= TTDTList.getString("TTDTID") %>"  >
												<% } %>

													</TD>
												</TR>	
																				
										<% 		
												i++;
												} 
												TTDTList.close();
											} catch(Exception e ){}
										}
														%>

												</TABLE>	
										</div>
								<% } %>
								
							<% if(TTDTBean.getNhanphanbo().equals("1")){ %>
															
										<div>
											<TABLE style="width: 95%" cellpadding="4px" cellspacing="1px" align="center">
												<TR><TD class= "legendtitle" >Trung Tâm Doanh Thu nhận phân bổ</TD></TR>
											</TABLE>
											
											<TABLE style="width: 95%" cellpadding="4px" cellspacing="1px" align="center">
												<TR class="tbheader">
													<TH width="20%">Mã</TH>
													<TH width="60%">Diễn giải</TH>
													<TH >Phần trăm (%)</TH>
												</TR>
										<%
										ResultSet TTDTList = TTDTBean.getNhanphanbo(TTDTBean.getId());
										if(TTDTList != null)
										{
											try
											{
												int i = 0;
				                                String lightrow = "tblightrow";
				                                String darkrow = "tbdarkrow";
												
												while(TTDTList.next())
												{ 
												  if (i % 2 != 0) { %>
												                       
	                                              <TR class= <%=lightrow%> >
	                                              
	                                     <%		  } else {%>
	                                              
	                                              <TR class= <%= darkrow%> >
	                                               
	                                     <%		  }%>
													
													<TD><%= TTDTList.getString("MA_CPB") %></TD>
													
													
													<TD><%= TTDTList.getString("DIENGIAI_CPB") %></TD>												
													
													
													<TD align = "center"><%= TTDTList.getString("PHANTRAM_CPB") %></TD>

												  </TR>	
																				
										<% 		i++;	
											}
												TTDTList.close();
											} catch(Exception e ){}
										}
														%>
												</TABLE>	
										</div>
							<% } %>
								
							</div>
							</TD>
							</TR>
						</TABLE>
					<%} %>
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>
</BODY>
</HTML>
<%
	TTDTBean.DBClose();
}%>