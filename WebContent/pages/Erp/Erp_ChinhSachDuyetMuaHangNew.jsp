<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.chinhsachduyetmuahang.IChinhsachduyetmuahang" %>
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

<% 	IChinhsachduyetmuahang csBean = (IChinhsachduyetmuahang)session.getAttribute("csBean"); 
	String ctyId = csBean.getCtyId();
	
	ResultSet kcp = csBean.getKcp();
	ResultSet dvth = csBean.getDvth();
	ResultSet ncc = csBean.getNcc();
	ResultSet sp = csBean.getSp();
	
	int[] quyen = new  int[5];
	quyen = util.Getquyen("Erp_ChinhsachduyetmuahangUpdateSvl","16",userId);
	
%>
<%NumberFormat formatter = new DecimalFormat("#,###,###"); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/cdtab.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax-dynamic-list-SP-DCP.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

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
	width: 400px; /* Width of box */
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
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script> 

<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});
		//$(".tabContents").hide();
        //$(".tabContents:first").show(); 

        $(".titleTab a").click(function () { 

            var activeTab = $(this).attr("href"); 
 			//var activeTab =
            $(".titleTab a").removeClass("active"); 
            $(this).addClass("active");
            $(".tabContents").hide();
            $(activeTab).fadeIn();

        });
        $("select").select2();
        
	});	
</script> 


<script>
//perform JavaScript after the document is scriptable.
$(function() {
 // setup ul.tabs to work as tabs for each div directly under div.panes
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<SCRIPT language="javascript" type="text/javascript">

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
			return false;
		}
	}
	 function submitForm()
	{	
		
		document.csForm.action.value='capnhat';
	    document.forms["csForm"].submit();
	}
	
	 function submitDVTH()
	{	
			
			document.csForm.action.value='dvth';
		    document.forms["csForm"].submit();
	}
	 
	function cpsaveform(Id)
	{
		 document.csForm.action.value ='cpluulai';
		 document.csForm.kcpId.value = Id;
	     document.forms["csForm"].submit();
	}
	function nccsaveform(Id)
	{
		 document.csForm.action.value ='nccluulai';
		 document.csForm.kcpId.value = Id;
		 document.csForm.current_tab.value = '2';
	     document.forms["csForm"].submit();
	}
	function spsaveform(Id)
	{
		 document.csForm.action.value ='spluulai';
		 document.csForm.kcpId.value = Id;
		 document.csForm.current_tab.value = '3';
	     document.forms["csForm"].submit();
	}
	function SaveAll()
	{
		 document.csForm.action.value ='SaveAll'; 	 
	    document.forms["csForm"].submit();
	}
	function search()
	{
		document.csForm.action.value ='search'; 	 
	    document.forms["csForm"].submit();
	}
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="csForm" method="post" action="../../Erp_ChinhsachduyetmuahangUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value="1">
<input type="hidden" name="kcpId" value="">
<input type="hidden" name="current_tab" value="1">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Mua hàng &gt; Chính sách duyệt mua hàng &gt; Tạo mới
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../Erp_ChinhsachduyetmuahangSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>						
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: SaveAll()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
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
				
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1" ><%=csBean.getMessage() %></textarea>
						<% csBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
					<TD>

						<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" >
							<TR>
								<TD>																	
									<TABLE width = "100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD>
												<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Chính sách duyệt mua hàng</LEGEND>
												<TABLE border="0" width="100%" cellpadding="4" cellspacing="0">
												<TR>
						  	  				
									  				<TD class="plainlabel" width="30%">Phòng ban </TD>
						  			  				<TD class="plainlabel">
						  	  							<SELECT name= "dvthId" style="width: 200px" onChange = "submitDVTH();">
						  	  								<OPTION value = "0" >&nbsp;</OPTION>
						  	  						<% 
						  	  						try{
						  	  							while(dvth.next()){
						  	  							if(csBean.getDvthId().length() > 0){
						  	  								if (csBean.getDvthId().equals(dvth.getString("dvthId"))){ %>
							  	  								<OPTION value = <%= dvth.getString("dvthId")%> selected><%= dvth.getString("dvth")%> </OPTION>
							  	  							<%}else{ %>
							  	  								<OPTION value = <%= dvth.getString("dvthId")%> ><%= dvth.getString("dvth")%></OPTION>
							  	  							<%} 
							  	  						}else{%>						
							  	  							  <OPTION value = <%= dvth.getString("dvthId")%> ><%= dvth.getString("dvth")%></OPTION>
							  	  						<%} 
						  	  							}
							  	  					}catch(java.sql.SQLException e){}%>	  					
						  	  							</SELECT>
						  	  						</TD>
												</TR>
												<TR>
						  	  				
									  				<TD class="plainlabel" width="120px">Chính sách duyệt </TD>
						  			  				<TD class="plainlabel">
						  	  							<input name="diengiai" type="text" style="width:200px" value="<%= csBean.getDiengiai() %>" >
						  	  						</TD>
												</TR>
												
												<TR>
						  	  				
									  				<TD class="plainlabel" width="120px">Hoạt động </TD>
									  				<%if(csBean.isHoatDong()){ %>
									  				<TD class="plainlabel" align = "left">
						  	  							<input name="hoatdong" type="checkbox" style="width:200px"  checked="checked">
						  	  						</TD>
									  				<%} else{%>
						  			  				<TD class="plainlabel">
						  	  							<input name="hoatdong" type="checkbox" style="width:200px" >
						  	  						</TD>
						  	  						<%} %>
												</TR>
												<!-- ########################### TABS CƠ CHẾ DUYỆT ########################### -->
												<TR>
												 	<TD colspan="3">															
														<div id="tabContaier">
												            <div style="width:100%; float:left;background:#ddd">
												          <% if(csBean.getTab().equals("1")){ %>
												    	        <div class="titleTab"><a  class="active" href="#tab1">Chi phí</a></div>
												    	        <div class="titleTab"><a href="#tab2">Nhà cung cấp</a></div>
												                <div class="titleTab"><a href="#tab3">Sản phẩm</a></div>
												          
												          <% }else if(csBean.getTab().equals("2")){ %>
												    	        <div class="titleTab"><a   href="#tab1">Chi phí</a></div>
												    	        <div class="titleTab"><a class="active" href="#tab2">Nhà cung cấp</a></div>
												                <div class="titleTab"><a href="#tab3">Sản phẩm</a></div>
												          
												          <% }else{ %>
												    	        <div class="titleTab"><a   href="#tab1">Chi phí</a></div>
												    	        <div class="titleTab"><a href="#tab2">Nhà cung cấp</a></div>
												                <div class="titleTab"><a class="active" href="#tab3">Sản phẩm</a></div>											          
												          
												          <% } %>
												            </div>
														
															<div class="tabDetails">
															<% if(csBean.getTab().equals("1")){ %>
	   	        												<div id="tab1" class="tabContents" style="display:block">
	   	        											<% }else{ %>
	   	        												<div id="tab1" class="tabContents" style="display:none">
	   	        											<% } %>
																
															<TABLE  border="0" cellpadding="4"  cellspacing="1" width="100%">
															<TR class="tbheader" >
																<TH width="5%" >Thứ tự </TH>
																<TH width="15%" >Từ </TH>															
																<TH width="5%" > &nbsp; </TH>
																<TH width="15%" >Đến </TH>
																<TH width="45%" >Người duyệt </TH>
																<TH width="15%" >Thiết lập</TH>
															</TR>
												<%	
												if(kcp != null){
													try{
														int i = 1;
//														dbutils db = new dbutils();
														while(kcp.next()){
															if(i%2 == 0){						
																					%>
															<TR class= 'tblightrow'>
														<%	}else{ %>
															<TR class= 'tbdarkrow'>
														<%  } %>	
											  					<TD width="1%" align="center" >
																	<%= i++ %>	
																</TD>
															  	
											  					<TD width="10%" align="center" >
																	<input name="sotientu" type="text" style="width:100%" value=<%= formatter.format(kcp.getDouble("sotientu")) %> readonly="readonly">
																    <input name="kcpIds" type="hidden" style="width:100%" value=<%= kcp.getString("KhoangchiphiId") %> >
																</TD>
																
																<TD  align="center" > < - <= </TD>
																
											  					<TD  align="center" >
											  						<%if (!kcp.getString("sotienden").equals("-1")){ %>
																		<input name="sotienden" type="text" style="width:100%" value=<%= formatter.format(kcp.getDouble("sotienden")) %> readonly="readonly">
																 	<%}else{ %>
																 		<input name="sotienden" type="text" style="width:100%" value="Không giới hạn"   readonly="readonly">
																 	<%} %>
																</TD>
																
											  					<TD  align="left" >&nbsp;</TD>

											  					<TD  align="center" >
								        			                 <a href="" id='<%="duyetId" + kcp.getString("KhoangchiphiId") %>' rel='<%= "duyet" + kcp.getString("KhoangchiphiId") %>'>
	           	 														&nbsp; <img alt="Chi tiết duyệt" src="../images/vitriluu.png"></a>
	           	 		
                          											<DIV id='<%= "duyet" + kcp.getString("KhoangchiphiId") %>' style="position:absolute; visibility: hidden; border: 2px solid #80CB9B;
				                             						background-color: white; width: 450px; height:280px; overflow:auto; padding: 2px;">
				                             													
				                    								<table width="100%" align="center">
				                        								<tr class="tbheader">
				                            								<th width="50%">Chức vụ</th>
				                            								<th width="15%" align="center">Duyệt </th>
				                            								
				                            								<th width="20%" align="center">Quyết định</th>
				                            								
												                        </tr>	
		                            									<%
		                            									
		                            									ResultSet rs2 = csBean.Chucdanhconlai(kcp.getString("KhoangchiphiId"));

		                            									if(rs2 != null)
			                        									{
			                        										try{
			                        											while(rs2.next())
			                        											{  %>
			                        			
									                        			<tr>
			            						            				<td><input style="width: 100%" value="<%= rs2.getString("CHUCDANH") %>"></td>
			            						            				<td align="center"><input type="checkbox" name='<%="chon" + kcp.getString("KhoangchiphiId")%>' style="width: 100%" value="<%= rs2.getString("CDID")%>" ></td>  
			            						            				
			            						            				<td><input type="checkbox" name='<%="quyetdinh" + kcp.getString("KhoangchiphiId")%>' style="width: 100%" value="<%= rs2.getString("CDID")%>" ></td>
			                        			                        </tr>
			                        			
			                        		 								<% } 
			                        											rs2.close(); 
			                        												
			                        										}catch(java.sql.SQLException e){}
			                        									}%>
			                        									
				                    							</table>
				                    							<hr/>
										                     	<div align="right" style="margin-right: 20px" class="legendtitle">
				                     								<a class="button2" href="javascript:dropdowncontent.hidediv('<%= "duyet" + kcp.getString("KhoangchiphiId") %>')" 
				                     								onClick="javascript:cpsaveform(<%= kcp.getString("KhoangchiphiId") %>)">
																	Lưu lại
																	</a>
				                     							</div>
				                     							<script type="text/javascript">
																	dropdowncontent.init('<%="duyetId" + kcp.getString("KhoangchiphiId") %>', "left-bottom", 300, "click");
																</script>
				                     							
				            							</DIV>                        
																	
																				
														</TD>
														</TR>
												<%																
														}
														//db.shutDown(); 		
														}catch(java.sql.SQLException e){}
														  	
												 }%>
														</TABLE>
																</div>
																<!-- KẾT THÚC DUYỆT CHI PHÍ-->
															<% if(csBean.getTab().equals("2")){ %>
																<div id="tab2" class="tabContents" style="display:block">
															<% }else{ %>
																<div id="tab2" class="tabContents" style="display:none">
															<% } %>
																	<TABLE  border="0" cellpadding="3"  cellspacing="1" width="100%">
																			<TR class="tbheader" >
																				<TH width="5%" >Thứ tự </TH>
																				<TH width="20%" >Nhà cung cấp </TH>															
																				<TH width="65%" >Người duyệt </TH>
																				<TH width="10%" >Thiết lập</TH>
																			</TR>
																		<%	
																	String[] ncc_duyet = new String[2];
																	for(int j = 1; j <= 10; j++){
																		if(j%2 == 0){						
																											%>
																			<TR class= 'tblightrow'>
																	<%	}else{ %>
																			<TR class= 'tbdarkrow'>
																	<%  } %>	
																	  			<TD width="1%" align="center" >
																					<%= j %>	
																				</TD>
																		
																	  			<TD  align="left" >																																		
																					<SELECT name="<%= "mancc" + j %>"  style="width: 400px">
								    													<option value=""></option>
																			 <% try{
																			    			ncc.beforeFirst();
																			 		while(ncc.next())
																			 		{ %>
																			 		
																			     		<option value='<%=ncc.getString("PK_SEQ")%>'><%=ncc.getString("TEN") %></option>
																			 <%    	}
																			    				
																			    }catch(java.sql.SQLException e){} %>	  
											                        				</SELECT>
											                        			</TD>
											                        			
																	  			
				  																<TD  align="left" >
				  																&nbsp;
				  																</TD>
						
																	  			<TD  align="center" >
														        			        <a href="" id='<%="duyetNccId" + j %>' rel='<%= "duyetNcc" + j %>'>
							           	 											&nbsp; <img alt="Chi tiết duyệt" src="../images/vitriluu.png"></a>
							           	 		
						                          									<DIV id='<%= "duyetNcc" + j %>' style="position:absolute; visibility: hidden; border: 2px solid #80CB9B;
										                             				background-color: white; width: 450px; height:280px; overflow:auto; padding: 2px;">
				                             													
							                          											
											                    					<table width="98%" align="center">
											                        					<tr class="tbheader">
											                            					<th width="50%">Chức vụ</th>
											                            					<th width="15%" align="center">Duyệt </th>											                            					
											                            					<th width="20%" align="center">Quyết định</th>
											                            								
																			             </tr>	
									                            				<%
									                            									
									                            					ResultSet rs = csBean.ChucdanhconlaiNcc("" + j);
							
									                            					if(rs != null)
										                        					{
										                        						try{
										                        										
										                        							while(rs.next())
										                        							{  %>
										                        			
																                		 <tr>
										            						            		<td><input style="width: 100%" value="<%= rs.getString("CHUCDANH") %>"></td>
										            						            		<%if(!rs.getString("CHON").equals("-1")){ %>
										            						            		<td align="center"><input type="checkbox"  name='<%="nccchon" + j %>' style="width: 100%" value="<%= rs.getString("CDID")%>" checked="checked" ></td>  
										            						            		<%}else {%>
										            						            		<td align="center"><input type="checkbox"  name='<%="nccchon" + j%>' style="width: 100%" value="<%= rs.getString("CDID")%>" ></td>  
										            						            		<%}
										            						            		
										            						            		if(rs.getString("QUYETDINH").equals("1")) {%>
										            						            		<td align="center" ><input type="checkbox" name='<%="nccquyetdinh" + j %>' style="width: 100%" value='<%= rs.getString("CDID")%>' checked="checked" ></td>
										            						            		<%}else {%>
										            						            		<td align="center" ><input type="checkbox" name='<%="nccquyetdinh" + j %>' style="width: 100%" value='<%= rs.getString("CDID")%>' ></td>
										            						            		<%} %>
										                        			              </tr>
										                        			
										                        		 			<% 		} 
										                        							rs.close(); 
										                        												
										                        						}catch(java.sql.SQLException e){}
										                        					}
																	
										                        					%>
										                        									
											                    					</table>
																					<hr/>						  			
																					<div align="right" style="margin-right: 20px" class="legendtitle">
										                     						<a class="button2" href="javascript:dropdowncontent.hidediv('<%="duyetNcc" + j  %>')" 
										                     						 onClick = "javascript:nccsaveform(<%= j %>)" >
																					Lưu lại</a>
											                    					</div>
											                    					<script type="text/javascript">
																					dropdowncontent.init('<%="duyetNccId" + j %>', "left-bottom", 300, "click");
																					</script>
											            						</DIV>  
																			</TD>
																		</TR>
															<%		} %>
																	</TABLE>
																</div>		
															<!-- KẾT THÚC DUYỆT NHÀ CUNG CẤP-->
															<% if(csBean.getTab().equals("3")){ %>
																<div id="tab3" class="tabContents" style="display:block;">
															<% }else{ %>
																<div id="tab3" class="tabContents" style="display:none;">
															<% } %>
																<TABLE  border="0" cellpadding="3"  cellspacing="1" width="100%">
																			<TR class="tbheader" >
																				<TH width="5%" >Thứ tự </TH>
																				<TH width="20%" >Sản phẩm </TH>	
																				<TH width="65%" >Người duyệt </TH>
																				<TH width="10%" >Thiết lập</TH>
																			</TR>
																		<%	
																	String[] sp_duyet = new String[2];
																	for(int j = 1; j <= 10; j++){
																		if(j%2 == 0){						
																											%>
																			<TR class= 'tblightrow'>
																	<%	}else{ %>
																			<TR class= 'tbdarkrow'>
																	<%  } %>	
																	  			<TD width="1%" align="center" >
																					<%= j %>	
																				</TD>
														 
																	  			<TD align="left" >
																	  				<SELECT name="<%= "masp"  + j %>"  style="width: 400px">
								    													<option value=""></option>
																			   		<% try{
																			   				sp.beforeFirst();
																			   				while(sp.next()){ 
																			       		
																			      		 %>
																			     				<option value='<%=sp.getString("PK_SEQ")%>'><%=sp.getString("TEN") %></option>
																			     		<%  	
																			  			    }
																			    				
																			   			}catch(java.sql.SQLException e){} %>	  
											                        					</SELECT>

																	  					
																	  			</TD>
																				
																	  			<TD  align="left" >
																	  			&nbsp;
															  					</TD>
						
															  					<TD  align="center" >
												        			                 <a href="" id='<%="duyetSpId" + j %>' rel='<%= "duyetSp" + j %>'>
					           	 														&nbsp; <img alt="Chi tiết duyệt" src="../images/vitriluu.png"></a>
							           	 		
				                          											<DIV id='<%= "duyetSp" + j %>' style="position:absolute; visibility: hidden; border: 2px solid #80CB9B;
								                             						background-color: white; width: 450px; height:280px; overflow:auto; padding: 2px;">
				                             													
								                    								<table width="98%" align="center">
								                        								<tr class="tbheader">
								                            								<th width="50%">Chức vụ</th>
								                            								<th width="15%" align="center">Duyệt </th>
								                            								
								                            								<th width="20%" align="center">Quyết định</th>
											                            								
																                        </tr>	
																				<%		ResultSet rs = csBean.ChucdanhconlaiSp("" + j );
							
									                            						if(rs != null)
										                        						{
										                        							try{
										                        								while(rs.next())
										                        								{  %>
										                        			
																                        <tr>
																                        	<td><input style="width: 100%" value="<%= rs.getString("CHUCDANH") %>"></td>
										            						            		<%if(!rs.getString("CHON").equals("-1")){ %>
										            						            	<td align="center"><input type="checkbox"  name='<%="spchon" + j%>' style="width: 100%" value="<%= rs.getString("CDID")%>" checked="checked"></td>   
										            						            	<%    }else {%>
										            						            	<td align="center"><input type="checkbox"  name='<%="spchon" + j%>' style="width: 100%" value="<%= rs.getString("CDID")%>" ></td>  
										            						            		<%}
										            													            						            		
										            						            		if(rs.getString("QUYETDINH").equals("1")) {%>
										            						            	<td align="center" ><input type="checkbox" name='<%="spquyetdinh" + j %>' style="width: 100%" value="<%= rs.getString("CDID")%>" checked="checked"></td>
										            						            		<%}else {%>
										            						            	<td align="center" ><input type="checkbox" name='<%="spquyetdinh" + j %>' style="width: 100%" value="<%= rs.getString("CDID")%>" ></td>
										            						            		<%} %>
										                        			              </tr>
										                        			
										                        		 					<%  } 
										                        								rs.close(); 
										                        												
										                        							}catch(java.sql.SQLException e){}
										                        						}%>
																                        
									                    							</table>
									                    							<hr/>
															                     	<div align="right" style="margin-right: 20px" class="legendtitle">
									                     								<a class="button2" href="javascript:dropdowncontent.hidediv('<%= "duyetSp" + j %>')" 
									                     								onClick="javascript:spsaveform(<%=  j %>)">
																						Lưu lại</a>
										                     							</div>
										                     							<script type="text/javascript">
																						dropdowncontent.init('<%="duyetSpId" + j %>', "left-bottom", 300, "click");
																						</script>
											            							</DIV>                        
																				
																										
																				</TD>
																				</TR>
																		<%																
																				}
																	
																		 %>
																		 
																		
																		</TABLE>
																	
																</div>
																<!-- KẾT THÚC DUYỆT SẢN PHẨm-->	
															</div>
														</div>
													</TD>

							 					</TR>
							 					<!-- ########################### KẾT THÚC TABS CƠ CHẾ DUYỆT ########################### -->
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

	if(dvth != null) dvth.close(); 	
	if(ncc != null) ncc.close(); 	
	if(sp != null) sp.close(); 
	if(kcp != null) kcp.close();
	if(csBean!=null){ csBean.DBClose(); csBean=null;}
	session.setAttribute("csBean", null);
}%>