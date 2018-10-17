<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.chinhsachduyetmuahang.IChinhsachduyetmuahangList" %>
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

<% 	IChinhsachduyetmuahangList csBean = (IChinhsachduyetmuahangList)session.getAttribute("csBean"); 
	String ctyId = csBean.getCtyId();
	String dvthId = csBean.getDvthId();
	ResultSet cs = csBean.getCS();
	ResultSet dvthlist = csBean.getDvthList() ;
 	int[] quyen = new  int[5];
	quyen = util.Getquyen("Erp_ChinhsachduyetmuahangSvl","&view=TT&chixem=0",userId);
	
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
	
    document.forms["csForm"].submit();
}

 function newForm()
 {	
 	
	 document.csForm.action.value = "new";
	 document.forms["csForm"].submit();
 }

 function xoaform()
 {
    document.csForm.dvthId.value = "";
    submitForm();
 }
</SCRIPT>


</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="csForm" method="post" action="../../Erp_ChinhsachduyetmuahangSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value="1">
<input type="hidden" name="chixem" value='<%= csBean.getChixem() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Mua hàng &gt; Chính sách duyệt mua hàng
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>

			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" >
				<TR>
					<TD>

						<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" >
							<TR>
								<TD>																	
									<TABLE width = "100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD>
												<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm</LEGEND>
												<TABLE border="0" width="100%" cellpadding="1" cellspacing="0">
												<TR>
						  	  				
									  				<TD class="plainlabel" width="120px">Đơn vị thực hiện </TD>
						  			  				<TD class="plainlabel" width = "80%">
						  	  							<SELECT name= "dvthId" onChange = "javascript:submitForm();">
						  	  								<OPTION value = "" >&nbsp;</OPTION>
						  	  						<% 
						  	  						try{
						  	  							while(dvthlist.next()){
						  	  							if(dvthId.length() > 0){
						  	  								if (dvthId.equals(dvthlist.getString("dvthId"))){ %>
							  	  								<OPTION value = <%= dvthlist.getString("dvthId")%> selected><%= dvthlist.getString("dvthTen")%> </OPTION>
							  	  							<%}else{ %>
							  	  								<OPTION value = <%= dvthlist.getString("dvthId")%> ><%= dvthlist.getString("dvthTen")%></OPTION>
							  	  							<%} 
							  	  						}else{%>						
							  	  							  <OPTION value = <%= dvthlist.getString("dvthId")%> ><%= dvthlist.getString("dvthTen")%></OPTION>
							  	  						<%} 
						  	  							}
							  	  					}catch(java.sql.SQLException e){}%>	  					
						  	  							</SELECT>
						  	  						</TD>
												</TR>
												<TR>	
													<TD colspan="2" class="plainlabel">
														&nbsp;
													</TD>
												</TR>

												<TR>	
													<TD colspan="2" class="plainlabel">
													<A class="button2" href="javascript:xoaform()">
													<IMG style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
													</TD>
												</TR>
											</TABLE>
										</FIELDSET>
									</TD>
								</TR>
							</TABLE>
								
							<TABLE width = "100%" cellpadding="0" cellspacing="0">	
												<TR>
													<TD colspan = "2">
													<FIELDSET>
													<LEGEND class="legendtitle">&nbsp;Chính sách duyệt mua hàng &nbsp;&nbsp;&nbsp;
						
													<%if(quyen[0]!=0 && csBean.getChixem().equals("0") ){ %>
													<A class="button3" href="javascript:newForm()">
    												<IMG style="top: -4px;" src="../images/New.png" alt="">Tạo mới</A>  
    												<%} %>                          
					 								</LEGEND>
													
													<TABLE  border="0" cellpadding="3"  cellspacing="1" width="100%">
															<TR class="tbheader" >
																<TH width="20%" >Phòng ban </TH>
																<TH width="20%" >Chính sách duyệt </TH>	
																<TH width="8%" >Trạng thái </TH>														
																<TH width="15%" >Người tạo </TH>
																<TH width="7%" >Ngày tạo </TH>
																<TH width="15%" >Người sửa </TH>
																<TH width="7%" >Ngày sửa</TH>
																<TH width="10%" >Tác vụ</TH>
															</TR>
												<%	
												if(cs != null){
													try{
														int i = 1;
														while(cs.next()){
															if(i%2 == 0){						
																					%>
															<TR class= 'tblightrow'>
														<%	}else{ %>
															<TR class= 'tbdarkrow'>
														<%  } %>	
											  					<TD width="1%" align = "left" >
											  						<%= cs.getString("DVTH") %>
																</TD>
															  	
											  					<TD width="10%" align = "left" >
																	<%= cs.getString("DCP") %>							
																</TD>
																
																<TD  align="center" > 
																<% if(cs.getString("TRANGTHAI").equals("1")){ %>
																		Hoạt động
																<% }else{ %>																		
																		Ngưng hoạt động
																<% } %>
																</TD>
																
											  					<TD  align="center" >
											  						<%= cs.getString("NGUOITAO") %>	
																</TD>
																
											  					<TD  align="center" >
											  						<%= cs.getString("NGAYTAO") %>	
																</TD>

											  					<TD  align="center" >
				                    								<%= cs.getString("NGUOISUA") %>	
																</TD>
																
											  					<TD  align="center" >
				                    								<%= cs.getString("NGAYSUA") %>	
																</TD>

											  					<TD  align="center" >
																	<TABLE border = 0 cellpadding="0" cellspacing="0">
																	<TR>												
																		<TD>
																	<%if(quyen[2]!=0 && csBean.getChixem().equals("0") ){ %>
																		<A href = "../../Erp_ChinhsachduyetmuahangUpdateSvl?userid=<%=userId %>&update=<%=cs.getString("ID")%>"><img title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>&nbsp;
																	<%} %>
																		</TD>
																		<TD>
																	<%if(quyen[1]!=0 && csBean.getChixem().equals("0") ){ %>
																		<A href = "../../Erp_ChinhsachduyetmuahangSvl?userid=<%=userId %>&delete=<%=cs.getString("ID")%>&chixem=0"><img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa chính sách duyệt này?')) return false;"></a>&nbsp;
											 						<%} %>
											 							</td>
											 						<!-- QUYEN VIEW DLN -->
											 							<TD>
																		<A href = "../../Erp_ChinhsachduyetmuahangUpdateSvl?userid=<%=userId %>&display=<%=cs.getString("ID")%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
																		</TD>
																	<!-- END QUYEN VIEW DLN --> 
																	</TR>
																	</TABLE>				
				                    					
																</TD>
																
														</TR>
												<%		
													i++;
														}

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
	if(cs != null) cs.close();
	if(dvthlist != null) dvthlist.close();
	if(csBean!=null){ csBean.DBClose(); csBean=null;}
	session.setAttribute("csBean", null);
}%>