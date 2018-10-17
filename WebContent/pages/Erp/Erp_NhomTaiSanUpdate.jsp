<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.erp_nhomtaisan.IErp_nhomtaisan" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_nhomtaisan.imp.Erp_nhomtaisan" %>
<%@ page  import = "java.sql.ResultSet"%>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

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

	IErp_nhomtaisan ntsBean =(Erp_nhomtaisan)session.getAttribute("ntsBean");
	ResultSet ltsRs = (ResultSet)ntsBean.getRslts();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>SalesUp ERP</TITLE>
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

<script type="text/javascript">
function msg()
{
alert("Lưu dữ liệu thành công!");
}

function submitform(){
	var mats = document.forms['tsForm'].Ma.value;
	if(mats == "")                        
	{
		document.forms['tsForm'].dataerror.value = "Vui lòng nhập mã nhóm tài sản.";
		return;              
	}
	
	var tents = document.forms['tsForm'].Ten.value;
	if(tents == "")
	{
		document.forms['tsForm'].dataerror.value = "Vui lòng nhập diễn giải nhóm tài sản.";
		return;
	}
	
	var ltsId = document.forms['tsForm'].ltsId.value;
	if(ltsId == "")
	{
		document.forms['tsForm'].dataerror.value = "Vui lòng chọn loại tài sản.";
		return;
	}

	document.forms['tsForm'].action.value='save';
	document.forms["tsForm"].submit();
	
}
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tsForm" method="post" action="../../Erp_NhomTaiSanUpdateSvl" charset ="UTF-8">
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<INPUT name="action" type="hidden" value="">
<input type = "hidden" value = "<%=ntsBean.getId() %>" name = "id">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">Quản lý tài sản > Nhóm tài sản > Cập nhật </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp; </td>
					      </tr>
   
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"> <A href="/TraphacoHYERP/Erp_NhomTaiSanSvl" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD>&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
			<TABLE width="100%" cellspacing="0" cellpadding="6">
						<TR>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông báo </LEGEND>
			    				<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1" ><%=ntsBean.getMsg()%></textarea>
								
								</FIELDSET>
						   </TD>
						</TR>	
						<TR>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin nhóm tài sản
								</LEGEND>
								<TABLE  width="100%" cellspacing="0" cellpadding="6">		
									<TR>
									  <TD width="15%" class="plainlabel">Mã <FONT class="erroralert">*</FONT></TD>
									  <TD  class="plainlabel" ><INPUT type = "text" name="Ma" id = "ma" size="40"
										type="text" value="<%=ntsBean.getMa() %>" >  
										
										</TD>
										
										
								    </TR>
	
									<TR>
										<TD width = "15%" class = "plainlabel">Diễn giải <font class = "erroralert">*</font> </TD> 
										<TD class = "plainlabel"> <input type = "text" name = "Ten" id = "ten" value="<%=ntsBean.getTen() %>"> </TD>
									</TR>
									
									<TR>
										<TD width = "15%" class = "plainlabel">Loại tài sản <font class = "erroralert">*</font> </TD> 
										<TD class = "plainlabel"> 
											<SELECT Id = "ltsId"  NAME = "ltsId" >
												<OPTION value = "" >&nbsp; </OPTION>
									<% while(ltsRs.next()){ 
											if(ltsRs.getString("ltsId").equals(ntsBean.getLtsId())){
									
									%>
												<OPTION value = "<%= ltsRs.getString("ltsId") %>" selected><%=  ltsRs.getString("diengiai")%> </OPTION>
									
									<%		}else{ %>
											
												<OPTION value = "<%= ltsRs.getString("ltsId") %>" ><%=  ltsRs.getString("diengiai")%> </OPTION>										
									<%		} 
									
									   }%>
											</SELECT> 
									
										</TD>
									</TR>
									<TR>
								<%if(ntsBean.getTrangthai().equals("1")) {%>						
								  		<TD class="plainlabel"><input name="trangthai" type="checkbox" value="1" checked>
						  		<%}else{ %>
						  				<TD class="plainlabel"><input name="trangthai" type="checkbox" value="1" >
						  		<%} %>
							      		Hoạt động </TD>
										<TD class="plainlabel">&nbsp;</TD>
									</TR>
								</TABLE>
								</FIELDSET>	
							</TD>
						</TR>
						<TR>
								<TD colspan = 2>
															
									<ul class="tabs">
																									
										<li><a href="#" class= "legendtitle"> Công dụng</a></li>
																
										<li><a href="#" class= "legendtitle">Phân bổ</a></li>
																
									</ul>
									
									<div class="panes">
															
										<div align = "left">
											<TABLE style="width: 70%" cellpadding="4px" cellspacing="1px" align="center">
												<TR><TD class= "legendtitle" >Chọn công dụng</TD></TR>
											</TABLE>
										
											<TABLE style="width: 70%" cellpadding="4px" cellspacing="1px" align="center">
												<TR class="tbheader">
													<TH width="50%">Diễn giải</TH>
													<TH width="10%">Chọn</TH>
												</TR>

										<% 
										int i = 0;
		                                String lightrow = "tblightrow";
		                                String darkrow = "tbdarkrow";
										
		                                ResultSet cdtsRs =  ntsBean.getChoncongdung();
		                                
										while(cdtsRs.next()){ 
										  	if (i % 2 != 0) {		%>
												                       
                                              <TR class= <%=lightrow%> >
	                                              
                                   <%	  	} else {%>
	                                              
	                                          <TR class= <%= darkrow%> >
	                                               
	                               <%	  	}%>
												<TD><%= cdtsRs.getString("DIENGIAI") %></TD>																									
													
												<TD align = "center">
													
													<INPUT TYPE = "checkbox" name="cdtsIds" value="<%= cdtsRs.getString("CDTSID") %>" checked="checked" >
												</TD>

																						
											 </TR>
																									
									<%		i++;
										}
										
		                                cdtsRs =  ntsBean.getChoncongdungthem();
		                                String cdtsList = ntsBean.getCdIdsList();
										while(cdtsRs.next()){ 
										  	if (i % 2 != 0) {		%>
												                       
                                              <TR class= <%=lightrow%> >
	                                              
                                   <%	  	} else {%>
	                                              
	                                          <TR class= <%= darkrow%> >
	                                               
	                               <%	  	}%>
												<TD><%= cdtsRs.getString("DIENGIAI") %></TD>																									
													
												<TD align = "center">
												
												<% if(cdtsList.indexOf(cdtsRs.getString("CDTSID")) >= 0){ %>
												
													<INPUT TYPE = "checkbox" name="cdtsIds" value="<%= cdtsRs.getString("CDTSID") %>" checked="checked" >
												
												<%}else{ %>
												
													<INPUT TYPE = "checkbox" name="cdtsIds" value="<%= cdtsRs.getString("CDTSID") %>" >
													
												<%} %>
												
												</TD>

																						
											 </TR>
																									
									<%		i++;
										} %>
										
											</TABLE>	
										</div>
								

										<div>
											<TABLE style="width: 70%" cellpadding="4px" cellspacing="1px" align="center">
												<TR><TD class= "legendtitle" >Chọn trung tâm chi phí để phân bổ khấu hao</TD></TR>
											</TABLE>
										
											<TABLE style="width: 70%" cellpadding="4px" cellspacing="1px" align="center">
												<TR class="tbheader">
													<TH width="50%">Mã</TH>
													<TH width="50%">Diễn giải</TH>
													<TH width="10%">Chọn</TH>
												</TR>

										<% 
										i = 0;
										ResultSet ttcpRs = ntsBean.getChonTTCP();
										while(ttcpRs.next()){ 
										  	if (i % 2 != 0) {		%>
												                       
                                              <TR class= <%=lightrow%> >
	                                              
                                   <%	  	} else {%>
	                                              
	                                          <TR class= <%= darkrow%> >
	                                               
	                               <%	  	}%>
	                               				<TD><%= ttcpRs.getString("MA") %></TD>	
												<TD><%= ttcpRs.getString("DIENGIAI") %></TD>																									
													
												<TD align = "center">
													<INPUT TYPE = "checkbox" name="ttcpIds" value="<%= ttcpRs.getString("TTCPID") %>"  checked="checked">
												</TD>
																						
											 </TR>
																									
									<%		i++;
										} 
										
										ttcpRs = ntsBean.getChonTTCPThem();
										String ttcpList = ntsBean.getTtcpIdsList();
										
										while(ttcpRs.next()){ 
										  	if (i % 2 != 0) {		%>
												                       
                                              <TR class= <%=lightrow%> >
	                                              
                                   <%	  	} else {%>
	                                              
	                                          <TR class= <%= darkrow%> >
	                                               
	                               <%	  	}%>
	                               				<TD><%= ttcpRs.getString("MA") %></TD>	
												<TD><%= ttcpRs.getString("DIENGIAI") %></TD>																									
													
												<TD align = "center">
												
												<% if(ttcpList.indexOf(ttcpRs.getString("TTCPID")) >= 0){ %>
																								
													<INPUT TYPE = "checkbox" name="ttcpIds" value="<%= ttcpRs.getString("TTCPID") %>"  checked="checked">
													
												<%}else{ %>
													
													<INPUT TYPE = "checkbox" name="ttcpIds" value="<%= ttcpRs.getString("TTCPID") %>" >
													
												<%} %>
												
												</TD>
																						
											 </TR>
																									
									<%		i++;
										} %>
										
											</TABLE>	
										</div>
						
								</div>	
							</TD>
						</TR>
				</TABLE>
			</TD> 
		</TR>
</TABLE>
</FORM>

</BODY>
</HTML>

<%
try{
	if(ltsRs != null) ltsRs.close();
	if(ttcpRs != null) ttcpRs.close();
	if(cdtsRs != null) cdtsRs.close();
	ntsBean.DBClose();
	session.setAttribute("ntsBean", null) ; 
}catch (Exception ex)
{
	ex.printStackTrace();
}
}
%>