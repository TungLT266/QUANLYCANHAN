<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamchiphi" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamchiphiList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% IErp_trungtamchiphi ttcpBean = (IErp_trungtamchiphi)session.getAttribute("ttcpBean"); 
  
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

function isNumberKey2(evt)
{
   /* var charCode = (evt.which) ? evt.which : event.keyCode;
   if (charCode > 31 && (charCode < 48 || charCode > 57) )
      return false;

   return true; */
   
   var keypressed = null;
	if (window.event)
		keypressed = window.event.keyCode; //IE
	else
		keypressed = evt.which; //NON-IE, Standard
	
	if (keypressed < 48 || keypressed > 57)
	{ 
		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
		{//Phím Delete và Phím Back
			return;
		}
		return false;
	}
   
}


function SetDisabled(Id){
	var ptdsId = "ptds" + Id;
	var ptds = document.getElementsByName(ptdsId);

	var ptId = "pt" + Id;
	var pt = document.getElementsByName(ptId);
	
	if(ptds.item(0).value == '1002' || ptds.item(0).value == '1003' || ptds.item(0).value == '1004' || ptds.item(0).value == '1005'){
		pt.item(0).value = "";
		pt.item(0).disabled=true;
	}else{
		pt.item(0).disabled=false;		
	}
}
</SCRIPT>

<script>
	function goBack() {
	    window.history.back();
	}
	</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="ttcpForm" method="post" action="../../Erp_TrungTamChiPhiUpdateSvl" >
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="ttcpId" value='<%= ttcpBean.getId()  %>'>
<input type="hidden" name="ctyId" value='<%= ttcpBean.getCtyId()  %>'>
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Tài chính  &gt; Trung tâm chi phí &gt; Hiển thị</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:goBack();" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Trung tâm chi phí</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  		
							<TR>
								<TD width="29%" class="plainlabel" >Mã<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"><INPUT type="text" readonly name="ma" style="width:300px" value='<%= ttcpBean.getMa()%>'></TD>
							</TR>
							
							<TR>
							  	<TD class="plainlabel">Diễn giải <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel"><INPUT type="text" readonly name="diengiai" style="width:300px" value='<%= ttcpBean.getDiengiai() %>'></TD>
						  	</TR>

							<TR>
							  	<TD class="plainlabel">Quản lý ngân sách</TD>
							  
							  <% if(ttcpBean.getCongansach().equals("1")){%>
						  	  	<TD class="plainlabel"><INPUT type="checkbox" disabled name="ngansach" value = "1" checked></TD>
						  	  <%}else{ %>
						  	  	<TD class="plainlabel"><INPUT type="checkbox" disabled name="ngansach" value = "1" ></TD>
						  	  <%} %>
						  	  
							</TR>							


							<TR>
							  	<TD class="plainlabel">Phân bổ</TD>
							  
							  <% if(ttcpBean.getPhanbo().equals("1")){%>
						  	  	<TD class="plainlabel"><INPUT type="checkbox" disabled name="pb" value = "1" checked ></TD>
						  	  <%}else{ %>
						  	  	<TD class="plainlabel"><INPUT type="checkbox" disabled name="pb" value = "1" ></TD>
						  	  <%} %>
						  	  
							</TR>							

							<TR>
							  	<TD class="plainlabel">Nhận phân bổ</TD>
							  
							  <% if(ttcpBean.getNhanphanbo().equals("1")){%>							  
						  	  	<TD class="plainlabel">
						  	  	<INPUT type="checkbox" disabled name="npb" value = "1" checked disabled = "disabled">
						  	  	<INPUT type="hidden" name="npbId" value = "1" >
						  	  	</TD>
						  	  <%}else{ %>
						  	  	<TD class="plainlabel">
						  	  	<INPUT type="checkbox" disabled name="npb" value = "1" disabled = "disabled">
						  	  	<INPUT type="hidden" name="npbId" value = "0" >
						  	  	</TD>
						  	  <%} %>
						  	  
							</TR>							

							<TR>
							<% if(ttcpBean.getTrangthai().equals("1")) {%>						
						  		<TD class="plainlabel"><input name="trangthai" type="checkbox" disabled value="1" checked>
						  	<%}else{ %>
						  		<TD class="plainlabel"><input name="trangthai" type="checkbox" disabled value="0" >
						  	<%} %>
							      Hoạt động </TD>
							<TD class="plainlabel">&nbsp;</TD>
							</TR>
						</TABLE>
					</FIELDSET>	
				<% if(ttcpBean.getPhanbo().equals("1") || (ttcpBean.getNhanphanbo().equals("1"))){ %>						
						<TABLE border="0" width="100%" cellpadding="2" cellspacing="0">								 
							<TR>
								<TD colspan="6">
															
									<ul class="tabs">
									
						 
							<% if(ttcpBean.getPhanbo().equals("1")){ %>
																
										<li><a href="#" class= "legendtitle"> Phân bổ</a></li>
																
							<% } %>
							
							<% if(ttcpBean.getNhanphanbo().equals("1")){ %>
																
										<li><a href="#" class= "legendtitle">Nhận phân bổ</a></li>
																
							<% } %>
							
									</ul>
									
									<div class="panes">


							<% if(ttcpBean.getPhanbo().equals("1")){ %>
															
										<div>
											<TABLE style="width: 95%" cellpadding="4px" cellspacing="1px" align="center">
												<TR>
													<TD class= "legendtitle" >Chọn Trung Tâm Chi Phí để phân bổ</TD>
													<TD class= "legendtitle" >Tìm kiếm
														<INPUT TYPE = "text" NAME = "timkiem" VALUE = "<%= ttcpBean.getTimkiem() %>">
													</TD>
													<TD width = "50%">&nbsp;</TD>
												</TR>
											</TABLE>
										
											<TABLE style="width: 95%" cellpadding="4px" cellspacing="1px" align="center">
												<TR class="tbheader">
													<TH width="20%">Mã</TH>
													<TH width="50%">Diễn giải</TH>
													<TH width="10%">Phần trăm (%) </TH>
													<TH width="10%">Phân bổ theo</TH>
													<TH width="10%">Chọn</TH>
												</TR>
										<%
										//String[] pbIds = ttcpBean.getPbIds();
										String pbIdList = ttcpBean.getPbIdsList();
										
										ResultSet ttcpList = ttcpBean.getChophanbo(ttcpBean.getId());
										int i = 0;
													
										if(ttcpList != null)
										{
											try
											{
				                                String lightrow = "tblightrow";
				                                String darkrow = "tbdarkrow";
												
												while(ttcpList.next())
												{ 
																
												  if (i % 2 != 0) { %>
												                       
	                                              <TR class= <%=lightrow%> >
	                                              
	                                     <%		  } else {%>
	                                              
	                                              <TR class= <%= darkrow%> >
	                                               
	                                     <%		  }%>
													<TD>
														<%= ttcpList.getString("MA_NPB") %>
													</TD>
													
													<TD>
														<%= ttcpList.getString("DIENGIAI_NPB") %>
													</TD>
													
													<TD align="center">
												<%		//Cột Phần Trăm chỉ hiện ra khi phần trăm không là 1002, 1003, 1004, 1005
														if(pbIdList.indexOf(ttcpList.getString("TTCPID_NPB")) >= 0){
															String tmp = pbIdList.substring(pbIdList.indexOf(ttcpList.getString("TTCPID_NPB")), pbIdList.indexOf(";", pbIdList.indexOf(ttcpList.getString("TTCPID_NPB"))));
															String[] pt = tmp.split("-");
															//System.out.println("pt[1] = " + Double.parseDouble(pt[1]));
															if(Double.parseDouble(pt[1]) != 1002 & Double.parseDouble(pt[1]) != 1003 & Double.parseDouble(pt[1]) != 1004 & Double.parseDouble(pt[1]) != 1005){
												%>
														<INPUT type="text" readonly name = "<%= "pt" + ttcpList.getString("TTCPID_NPB") %>" style="width: 100%" value="<%= pt[1] %>" onkeypress="return isNumberKey2(event)">
														
												<%			}else{	%>
														<INPUT type="text" readonly name = "<%= "pt" + ttcpList.getString("TTCPID_NPB") %>" style="width: 100%" value="" disabled=disabled>
													
												<%			}
														}else{ 
															System.out.println("pt[1] = " +Double.parseDouble(ttcpList.getString("PHANTRAM_NPB")));
															if(Double.parseDouble(ttcpList.getString("PHANTRAM_NPB")) != 1002 & Double.parseDouble(ttcpList.getString("PHANTRAM_NPB")) != 1003 & Double.parseDouble(ttcpList.getString("PHANTRAM_NPB")) != 1004 & Double.parseDouble(ttcpList.getString("PHANTRAM_NPB")) != 1005){ %>
														<INPUT type="text" readonly name = "<%= "pt" + ttcpList.getString("TTCPID_NPB") %>" style="width: 100%" value="<%= ttcpList.getString("PHANTRAM_NPB") %>" onkeypress="return isNumberKey2(event)">
														
												<%			}else{ %>
														<INPUT type="text" readonly name = "<%= "pt" + ttcpList.getString("TTCPID_NPB") %>" style="width: 100%" value="" disabled=disabled>
																										
												<%			}
														} %>
												
													</TD >
													<TD>
														<select disabled NAME = "<%= "ptds" + ttcpList.getString("TTCPID_NPB") %>"  OnChange = "SetDisabled(<%= ttcpList.getString("TTCPID_NPB") %>)">
															<OPTION VALUE = "0">&nbsp;</OPTION>
												<%	if(pbIdList.indexOf(ttcpList.getString("TTCPID_NPB")) >= 0){
														String tmp = pbIdList.substring(pbIdList.indexOf(ttcpList.getString("TTCPID_NPB")), pbIdList.indexOf(";", pbIdList.indexOf(ttcpList.getString("TTCPID_NPB"))));
														String[] pt = tmp.split("-");
														System.out.println("pt[1] = " + Double.parseDouble(pt[1]));
														
														if(Double.parseDouble(pt[1]) == 1002){
												%>															
															<OPTION VALUE = "1002" SELECTED>Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003">Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004">Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005">Lương công nhân Lõi/Tổng lương công nhân</OPTION>
												<%		}else if (Double.parseDouble(pt[1]) == 1003){%>
												
															<OPTION VALUE = "1002" >Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003" SELECTED>Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004">Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005">Lương công nhân Lõi/Tổng lương công nhân</OPTION>
															
												<%		}else if (Double.parseDouble(pt[1]) == 1004){%>
															<OPTION VALUE = "1002" >Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003" >Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004" SELECTED>Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005">Lương công nhân Lõi/Tổng lương công nhân</OPTION>
															
												<% 		}else if (Double.parseDouble(pt[1]) == 1005){%>
															<OPTION VALUE = "1002" >Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003" >Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004" >Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005" SELECTED>Lương công nhân Lõi/Tổng lương công nhân</OPTION>
												<% 		}else{ %>
															<OPTION VALUE = "1002" >Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003" >Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004" >Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005" >Lương công nhân Lõi/Tổng lương công nhân</OPTION>
											
												<%		}
													}else{ 
														System.out.println("pt[1] = " +Double.parseDouble(ttcpList.getString("PHANTRAM_NPB")));
														if(Double.parseDouble(ttcpList.getString("PHANTRAM_NPB")) == 1002){
												%>															
															<OPTION VALUE = "1002" SELECTED>Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003">Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004">Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005">Lương công nhân Lõi/Tổng lương công nhân</OPTION>
															
												<%		}else if (Double.parseDouble(ttcpList.getString("PHANTRAM_NPB")) == 1003){%>
												
															<OPTION VALUE = "1002" >Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003" SELECTED>Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004">Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005">Lương công nhân Lõi/Tổng lương công nhân</OPTION>

												<%		}else if (Double.parseDouble(ttcpList.getString("PHANTRAM_NPB")) == 1004){%>
															<OPTION VALUE = "1002" >Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003" >Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004" SELECTED>Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005">Lương công nhân Lõi/Tổng lương công nhân</OPTION>
												
												<% 		}else if (Double.parseDouble(ttcpList.getString("PHANTRAM_NPB")) == 1005){%>
															<OPTION VALUE = "1002" >Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003" >Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004" >Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005" SELECTED>Lương công nhân Lõi/Tổng lương công nhân</OPTION>
															
												<% 		}else{ %>
															<OPTION VALUE = "1002" >Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003" >Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004" >Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005" >Lương công nhân Lõi/Tổng lương công nhân</OPTION>
								
												<%		}
													} %>
												
														
														</SELECT>
													</TD>
													<TD align="center">
													
														<input type="checkbox" disabled name="pbIds" value="<%= ttcpList.getString("TTCPID_NPB") %>" checked="checked" >
												
													</TD>
												</TR>	
																				
										<% 		
												i++;
												} 
												ttcpList.close();
											} catch(Exception e ){}
										}
														%>

										<%
										ttcpList = ttcpBean.getChophanbothem(ttcpBean.getId());
													
										if(ttcpList != null)
										{
											try
											{
				                                String lightrow = "tblightrow";
				                                String darkrow = "tbdarkrow";
												
												while(ttcpList.next())
												{ 
																
												  if (i % 2 != 0) { %>
												                       
	                                              <TR class= <%=lightrow%> >
	                                              
	                                     <%		  } else {%>
	                                              
	                                              <TR class= <%= darkrow%> >
	                                               
	                                     <%		  }%>
													<TD>
														<%= ttcpList.getString("MA") %>
													</TD>
													
													<TD>
														<%= ttcpList.getString("DIENGIAI") %>
													</TD>
													
													<TD align="center">
													
												<%		if(pbIdList.indexOf(ttcpList.getString("TTCPID")) >= 0){
															String tmp = pbIdList.substring(pbIdList.indexOf(ttcpList.getString("TTCPID")), pbIdList.indexOf(";", pbIdList.indexOf(ttcpList.getString("TTCPID"))));
															String[] pt = tmp.split("-");
															if(Double.parseDouble(pt[1]) != 1002 & Double.parseDouble(pt[1]) != 1003  & Double.parseDouble(pt[1]) != 1004 & Double.parseDouble(pt[1]) != 1005){
												%>
														<INPUT type="text" readonly name = "<%= "pt" + ttcpList.getString("TTCPID") %>" style="width: 100%" value="<%= pt[1] %>" onkeypress="return isNumberKey2(event)">
														
												<%			}else{ %>
														
														<INPUT type="text" readonly name = "<%= "pt" + ttcpList.getString("TTCPID") %>" style="width: 100%" value="" disabled = disabled>
												
												<%			}
														}else{ %>
												
														<INPUT type="text" readonly name = "<%= "pt" + ttcpList.getString("TTCPID") %>" style="width: 100%" value="" onkeypress="return isNumberKey2(event)">
														
												<%		} %>
													

													</TD >
													<TD>
														<select disabled NAME = "<%= "ptds" + ttcpList.getString("TTCPID") %>"  OnChange = "SetDisabled(<%= ttcpList.getString("TTCPID") %>)" >
															<OPTION VALUE = "0">&nbsp;</OPTION>
												<%	if(pbIdList.indexOf(ttcpList.getString("TTCPID")) >= 0){
														String tmp = pbIdList.substring(pbIdList.indexOf(ttcpList.getString("TTCPID")), pbIdList.indexOf(";", pbIdList.indexOf(ttcpList.getString("TTCPID"))));
														String[] pt = tmp.split("-");
														if(pt[1].equals("1002")){
															%>															
															<OPTION VALUE = "1002" SELECTED>Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003">Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004">Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005">Lương công nhân Lõi/Tổng lương công nhân</OPTION>
												<%		}else if (pt[1].equals("1003")){%>
															
															<OPTION VALUE = "1002" >Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003" SELECTED>Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004">Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005">Lương công nhân Lõi/Tổng lương công nhân</OPTION>
																		
												<%		}else if (pt[1].equals("1004")){%>
															<OPTION VALUE = "1002" >Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003" >Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004" SELECTED>Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005">Lương công nhân Lõi/Tổng lương công nhân</OPTION>
																		
												<% 		}else if (pt[1].equals("1005")){%>
															<OPTION VALUE = "1002" >Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003" >Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004" >Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005" SELECTED>Lương công nhân Lõi/Tổng lương công nhân</OPTION>
												<% 		}else{ %>
															<OPTION VALUE = "1002" >Doanh số Nhôm/Tổng doanh số</OPTION>
															<OPTION VALUE = "1003" >Doanh số Lõi/Tổng doanh số</OPTION>
															<OPTION VALUE = "1004" >Lương công nhân Nhôm/Tổng lương công nhân</OPTION>
															<OPTION VALUE = "1005" >Lương công nhân Lõi/Tổng lương công nhân</OPTION>
														
												<%		}

												}%>
														</SELECT>
													</TD>

													<TD align="center">
												<% if(pbIdList.indexOf(ttcpList.getString("TTCPID")) >= 0){ %>
														<input type="checkbox" disabled name="pbIds" value="<%= ttcpList.getString("TTCPID") %>" checked="checked" >
												<% }else{ %>
														<input type="checkbox" disabled name="pbIds" value="<%= ttcpList.getString("TTCPID") %>"  >
												<% } %>

													</TD>
												</TR>	
																				
										<% 		
												i++;
												} 
												ttcpList.close();
											} catch(Exception e ){}
										}
														%>

												</TABLE>	
										</div>
								<% } %>
								
							<% if(ttcpBean.getNhanphanbo().equals("1")){ %>
															
										<div>
											<TABLE style="width: 95%" cellpadding="4px" cellspacing="1px" align="center">
												<TR><TD class= "legendtitle" >Trung Tâm Chi Phí nhận phân bổ</TD></TR>
											</TABLE>
											
											<TABLE style="width: 95%" cellpadding="4px" cellspacing="1px" align="center">
												<TR class="tbheader">
													<TH width="20%">Mã</TH>
													<TH width="60%">Diễn giải</TH>
													<TH >Phần trăm (%)</TH>
												</TR>
										<%
										ResultSet ttcpList = ttcpBean.getNhanphanbo(ttcpBean.getId());
										if(ttcpList != null)
										{
											try
											{
												int i = 0;
				                                String lightrow = "tblightrow";
				                                String darkrow = "tbdarkrow";
												
												while(ttcpList.next())
												{ 
												  if (i % 2 != 0) { %>
												                       
	                                              <TR class= <%=lightrow%> >
	                                              
	                                     <%		  } else {%>
	                                              
	                                              <TR class= <%= darkrow%> >
	                                               
	                                     <%		  }%>
													
													<TD><%= ttcpList.getString("MA_CPB") %></TD>
													
													
													<TD><%= ttcpList.getString("DIENGIAI_CPB") %></TD>												
													
													
													<TD align = "center"><%= ttcpList.getString("PHANTRAM_CPB") %></TD>

												  </TR>	
																				
										<% 		i++;	
											}
												ttcpList.close();
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
	ttcpBean.DBClose();
}%>