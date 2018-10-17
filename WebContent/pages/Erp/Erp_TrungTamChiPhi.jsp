<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamchiphi" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamchiphiList" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IErp_trungtamchiphiList obj = (IErp_trungtamchiphiList)session.getAttribute("obj"); %>
<% 	ResultSet ttcplist = (ResultSet)obj.getTtcpList(); 
	
 	int[] quyen = new  int[5];
	quyen = util.Getquyen("Erp_TrungTamChiPhiSvl","",userId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
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
	 document.ncpForm.action.value = "new";
   	 document.forms["ncpForm"].submit();
}

function searchform()
{
	 document.ncpForm.action.value = "search";
   	 document.forms["ncpForm"].submit();
}

function clearform()
{
	document.ncpForm.diengiai.value = "";
    document.ncpForm.trangthai.selectedIndex = 0;
    document.ncpForm.action.value = "search";
    document.forms["ncpForm"].submit();
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
<form name="ncpForm" method="post" action="../../Erp_TrungTamChiPhiSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="1">
<input type="hidden" name="chixem" value='<%= obj.getChixem() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
				   		<table width="100%" border="0" cellpadding="0" cellspacing="0">
					  		<tr height="22">
						  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán  &gt; Trung tâm chi phí</TD> 
						  		<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  	</tr>
						</table></TD>
			  	</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding="6">
								<TR>
									<TD width="19%" class="plainlabel">Mã / Diễn giải </TD>
								    <TD width="81%" class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<INPUT name="diengiai" type="text" size="40" value='<%=obj.getDiengiai()%>' onChange ="searchform();">
											</TD></TR>
										</TABLE>								
								</TR>

								<TR>
									<TD class="plainlabel">Trạng thái </TD>
								  	<TD  class="plainlabel"><SELECT name = "trangthai"  onChange = "searchform();"  style="width:200px"  class="select2">
								  	<% if (obj.getTrangthai().equals("0")){ %>
								    	<option value=""> </option>
								    	<option value="1">Hoạt động</option>
								    	<option value="0" selected>Ngưng hoạt động</option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>
								    	<option value=""> </option>
								    	<option value="1" selected>Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
									<%}else{ %>
								    	<option value="" selected> </option>
								    	<option value="1" >Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
									<%}} %>

										</SELECT></TD>
									
								<TR>
									<TD class="plainlabel">
									<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;

                                    </TD>
                                    								
									<TD class="plainlabel">&nbsp;</TD>										
								</TR>
								
							</TABLE>
					  </FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			
			<TABLE width="100%" border = "0" cellpading = "0" cellspacing = "0">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Trung tâm chi phí&nbsp;&nbsp;&nbsp;
					<%if(quyen[0]!=0  ){ %>
						<a class="button3" href="javascript:submitform()">
				    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>   
				    	<%} %>                         		
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="20%" >Mã</TH>
									<TH width="30%">Diễn giải</TH>
									<TH width="10%">Trạng thái</TH>
									<TH width="15%">Quản lý ngân sách  </TH>
									<TH width="10%">Nhận phân bổ </TH>
									<TH width="10%%">Phân bổ đi</TH>
									<TH width="5%">Tác vụ</TH>
								</TR>
					<% 		if(ttcplist != null){ 
							    String Id = "";
                                String Id_old = "";
                                String lightrow = "tblightrow";
                                String darkrow = "tbdarkrow";
								ResultSet rs;
								
								int m = 0;
								while(ttcplist.next()){
                            		Id = ttcplist.getString("TTCPID");
                            		if(!Id.equals(Id_old)){
                                		if (m % 2 != 0) {%>                     
                                    		<TR class= <%=lightrow%> >
                               			<%} else {%>
                                    		<TR class= <%= darkrow%> >
                               			<%}%>
												<TD align="left"><%= ttcplist.getString("MA")%> </TD>
												
												<TD align="left"><%= ttcplist.getString("DIENGIAI")%></TD>

											<% 	if(ttcplist.getString("TRANGTHAI").equals("0")){	%>
												<TD align="center">Ngưng hoạt động</TD>
											<%	}else{ %>
												<TD align="center">Hoạt động</TD>
											<%	} %>
												
											<% 	if(ttcplist.getString("CONGANSACH").equals("0")){	%>
												<TD align="center">Không</TD>
											<%	}else{ %>
												<TD align="center">Có</TD>
											<%	} %>
											
											<% 	if(ttcplist.getString("NHANPHANBO").equals("0")){%>
												<TD align="center">Không</TD>
											<%	}else{ %>
							  					<TD  align="center" >
				        			                 <a href="" id='<%="npbId" + ttcplist.getString("TTCPID")  %>' rel= '<%="npb" + ttcplist.getString("TTCPID") %>'>
       	 														&nbsp; <img alt="Chi tiết" src="../images/vitriluu.png"></a>
       	 		
                  										<DIV id='<%= "npb" + ttcplist.getString("TTCPID") %>' style="position:absolute; visibility: hidden; border: 2px solid #80CB9B;
		                             						background-color: white; width: 700px; height:250px; overflow:auto; padding: 2px;">
		                             						
		                    								<TABLE width="100%" align="center" cellpadding="4">
		                        								<TR class="tbheader">
		                            								<TH width="200px" align="center">Mã</TH>
		                            								<TH width="400px" align="center">Diễn giải</TH>
		                            								<TH width="100px" align="center">Phần trăm</TH>				                            								
										                        </TR>	
                            									<%
                            									
                            							rs = obj.getNhanphanbo(ttcplist.getString("TTCPID"));
                            									
                            					if(rs != null)
	                        					{
	                        						try{
	                        											
	                        							int i = 0;
	                        							while(rs.next())
	                        							{ 
	                        			
                        	                            	if (i % 2 != 0) {%>                     
	                                                    		<TR class= <%=lightrow%> >
	                                                 <%		} else {%>
	                                                            <TR class= <%= darkrow%> >
	                                                 <%		}%>

	            						            				<TD align="left"><%= rs.getString("MA_CPB") %></td>			            						            				
	            						            				<TD align="left"><%= rs.getString("DIENGIAI_CPB")%></TD>
	            						            				
	            						            <% if(!rs.getString("PHANTRAM_CPB").equals("1002") & !rs.getString("PHANTRAM_CPB").equals("1003")){ %>
	  		                        				    			<TD align="center"><%= rs.getString("PHANTRAM_CPB")%> %</TD>
	  		                        				    			  
	                        						<% }else if(rs.getString("PHANTRAM_CPB").equals("1002")){ %>
	                        										<TD align="center">DS Nhôm/Tổng DS</TD>
	                        										
	                        						<% }else if(rs.getString("PHANTRAM_CPB").equals("1003")){ %>
	                        						
	                        										<TD align="center">DS Lõi/Tổng DS</TD>
	                        										
	                        						<%} %>				
	                        			                        </TR>
	                        			
	                        		 				<%  	i++;
	                        		 					} 
	                        							rs.close(); 
	                        												
	                        						}catch(java.sql.SQLException e){}
	                        					}%>

	                        									
	                    								</TABLE>
		                    					
		                     							<div align="left" class="legendtitle">
		                     								<label  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
		                     								
		                     								<a href="javascript:dropdowncontent.hidediv('<%= "npb" + ttcplist.getString("TTCPID") %>')" ></a>
		                     							</div>
		                     							<script type="text/javascript">
															dropdowncontent.init('<%="npbId" + ttcplist.getString("TTCPID") %>', "left-bottom", 300, "click");
														</script>
		            							</DIV>                        
																		
												</TD>
                                        	<%} %>
                                        	
											<% 	if(ttcplist.getString("CHOPHANBO").equals("0")){%>
												<TD align="center">Không</TD>
											<%	}else{ %>
							  					<TD  align="center" >
				        			                 <a href="" id='<%="cpbId" + ttcplist.getString("TTCPID")  %>' rel= '<%="cpb" + ttcplist.getString("TTCPID") %>'>
       	 														&nbsp; <img alt="Chi tiết" src="../images/vitriluu.png"></a>
       	 		
                  										<DIV id='<%= "cpb" + ttcplist.getString("TTCPID") %>' style="position:absolute; visibility: hidden; border: 2px solid #80CB9B;
		                             						background-color: white; width: 700px; height:250px; overflow:auto; padding: 2px;">
		                    								<TABLE width="100%" align="center" cellpadding="4">
		                        								<TR class="tbheader">
		                            								<TH width="200px" align="center">Mã</TH>
		                            								<TH width="400px" align="center">Diễn giải</TH>
		                            								<TH width="100px" align="center">Phần trăm</TH>				                            								
										                        </TR>	
                            									<%
                            									
                            									rs = obj.getChophanbo(ttcplist.getString("TTCPID"));
                            									
                            					if(rs != null)
	                        					{
	                        						try{
	                        							int i = 0;
	                        							while(rs.next())
	                        							{  
	                        			
                        	                            	if (i % 2 != 0) {%>                     
	                                                    		<TR class= <%=lightrow%> >
	                                                 <%		} else {%>
	                                                            <TR class= <%= darkrow%> >
	                                                 <%		}%>

	            						            				<TD align="left"><%= rs.getString("MA_NPB") %></td>			            						            				
	            						            				<TD align="left"><%= rs.getString("DIENGIAI_NPB")%></TD>

	            						            <% if(!rs.getString("PHANTRAM_NPB").equals("1002") & !rs.getString("PHANTRAM_NPB").equals("1003")){ %>
	  		                        				    			<TD align="center"><%= rs.getString("PHANTRAM_NPB")%> %</TD>
	  		                        				    			  
	                        						<% }else if(rs.getString("PHANTRAM_NPB").equals("1002")){ %>
	                        										<TD align="center">DS Nhôm/Tổng DS</TD>
	                        										
	                        						<% }else if(rs.getString("PHANTRAM_NPB").equals("1003")){ %>
	                        						
	                        										<TD align="center">DS Lõi/Tổng DS</TD>
	                        										
	                        						<%} %>				
	                        					
	                        			                        </TR>
	                        			
	                        		 				<% 		i++;
	                        		 					} 
	                        											rs.close(); 
	                        												
	                        						}catch(java.sql.SQLException e){}
	                        					}%>

	                        									
		                    								</TABLE>
		                    					
		                     							<div align="left" class="legendtitle">
		                     								<label  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
		                     								
		                     								<a href="javascript:dropdowncontent.hidediv('<%= "cpb" + ttcplist.getString("TTCPID") %>')" ></a>
		                     							</div>
		                     							<script type="text/javascript">
															dropdowncontent.init('<%="cpbId" + ttcplist.getString("TTCPID") %>', "left-bottom", 300, "click");
														</script>
		            							</DIV>                        
																		
												</TD>
												
                                        	<%} %>

												<TD align="center">
													<TABLE>
														<TR>
														<TD>
														<A href = "../../Erp_TrungTamChiPhiUpdateSvl?userId=<%=userId%>&update=<%=ttcplist.getString("TTCPID")%>"><img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
														
														</TD>
														<TD>
														<A href = "../../Erp_TrungTamChiPhiSvl?userId=<%=userId%>&delete=<%= ttcplist.getString("TTCPID") %>"><img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa tổng hợp chi phí <%=ttcplist.getString("MA")%> này không?')) return false;"></A>&nbsp;
													
														</TD>
														<TD>
														<!-- QUYEN VIEW DLN -->
														<A href = "../../Erp_TrungTamChiPhiUpdateSvl?userId=<%=userId%>&display=<%=ttcplist.getString("TTCPID")%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
														<!-- END QUYEN VIEW DLN --> 
														</TD>
														</TR>												
													</TABLE>									
												</TD>
                                        	
                                        	
                                    	</TR>
                               	<% 
                               			m++;
                            		}
                            		Id_old = Id;
                                }
					}							
                            %>  
						<TR>
							<TD align="center" colspan="12" class="tbfooter">&nbsp;</TD>
						</TR>
					</TABLE>
				</TD>
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
<% 
 	if( ttcplist != null) ttcplist.close(); 
 	obj.DBClose();
	} %>
	